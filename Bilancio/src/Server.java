import java.io.*;
import java.net.Socket;


import java.net.ServerSocket;
import java.util.Date;
import java.util.Scanner;

public class Server {
    private int balance = 0;
    private int port;
    ServerSocket server;
    int nAcceptedRequest;

    public Server(int port) {
        this.port = port;
    }

    public void go() {

        try {
            server = new ServerSocket(port);
            System.out.println("Starting server on port " + port);
            new Thread(new periodicSave()).start();
        } catch (IOException e) {
            System.out.println("Cannot start server on port " + port);
            e.printStackTrace();
            System.exit(-1);

        }

        while (true) {
            try {
                System.out.println("Ready to accept connections...");
                Socket client = server.accept();
                nAcceptedRequest++;
                System.out.println("Accepted connection request n." + nAcceptedRequest + " from:" + client.getRemoteSocketAddress());

                InnerClientManager cm = new InnerClientManager(client);
                Thread t = new Thread(cm);
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public class InnerClientManager implements Runnable {
        Socket assigned_client;

        String message=null;

        @Override
        public void run() {
            System.out.println("Starting thread ClientManager");

            try {
                Scanner in =new Scanner(assigned_client.getInputStream());
                PrintWriter out =new PrintWriter(assigned_client.getOutputStream());

                //var br = new BufferedReader(new InputStreamReader(assigned_client.getInputStream()));
                boolean cont = true;

                while (in.hasNextLine()) {
                    message = in.nextLine();
                    System.out.println("Received message: " + message);
                    if(message.startsWith("INC")){
                        int incVal =Integer.parseInt(message.substring(4));
                        setBalance(getBalance()+incVal);

                        System.out.println("increasing value of " + incVal + "new balance " + getBalance());
                        out.println("ok");  //ok di risposta dal server al client
                        out.flush();

                    }

                    //analogo per dec

                    if (message.equals("QUIT")) {
                        System.out.println("Terminating ClientManager");

                        assigned_client.close();

                        break;

                    }else {
                        System.out.println("not a protocol message " + message);
                        out.println("ok");  //ok di risposta dal server al client
                        out.flush();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        public InnerClientManager(Socket s) {
            this.assigned_client = s;
        }
    }


        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

    class periodicSave implements Runnable{

        public void run(){
            System.out.println("periodic saver started");
            var f = new File ("balancelog.txt");
            while(true) {


                try {
                    var fw = new FileWriter(f, false);//in questo caso si elimina il contenuto del file
                    //var fw= new FileWriter(f);    //così non sovrascrive

                    fw.write(new Date().toString() + "Balance= " +  String.valueOf(getBalance()));
                    fw.flush();
                    Thread.sleep(10000);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }



    }
}

