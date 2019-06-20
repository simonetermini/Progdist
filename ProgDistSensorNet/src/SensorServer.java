import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SensorServer {


    public class Sensor{

        public String toString(){
            return(" \n >Sensor Type= " +sType+
                    "\n >Sensor value= " +sValue +
                    "\n >Data= "+ sData)+"\n";
        }

        String sType;
        double sValue;
        String sData;
    }
    private ArrayList<Sensor> sensor = new ArrayList<Sensor>();

    public  synchronized ArrayList<Sensor> getSensor() {
        return sensor;
    }

    public synchronized void setSensor(Sensor T) {
        this.sensor.add(T);
    }

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    Date today = calendar.getTime();
    int port ;


    public SensorServer(int port){
        this.port = port;
    }
    public void go() {




        ServerSocket server = null;


        try {
            System.out.println("Starting server on port: " + port);
            server = new ServerSocket(port);
            System.out.println("Server Started!");

        } catch (IOException e) {
            System.out.println("Cannot start server on port: " + port);
            e.printStackTrace();
            System.exit(-1);
        }
        Saver savingFile = new Saver();
        Thread S= new Thread(savingFile);
        S.start();
        int threadNumber=0;


        while (true){

            try {
                System.out.println("\n>>>SERVER:Waiting for connection...");
                Socket client = server.accept();
                System.out.println("Connection: OK");
                System.out.println("Client info: "+ client.getRemoteSocketAddress());
                SensorAndClientManager myClient = new SensorAndClientManager(client);
                Thread t = new Thread(myClient, String.valueOf(threadNumber));
                t.start();
                threadNumber++;




            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public class SensorAndClientManager implements Runnable{
        Socket myClient;



        public void run(){

            System.out.println("Thread: "+ Thread.currentThread().getName()+ " Starting Sensor Manager");
            System.out.println("Thread: "+ Thread.currentThread().getName()+ " WAITING FOR CLIENT TYPE");
            try {
                Scanner input = new Scanner(myClient.getInputStream());
                PrintWriter output = new PrintWriter((myClient.getOutputStream()));
                output.println("whoareyou");
                output.flush();
                Thread.sleep(1500);
                String mess=input.nextLine();
                if (mess.equals("imasensor")){
                    System.out.println("Thread: "+ Thread.currentThread().getName()+ " The client is a Sensor Client");
                    while (true){
                        System.out.println("Thread: "+ Thread.currentThread().getName()+ " Waiting for sensor data...");
                        mess=input.nextLine();
                        if (!mess.equalsIgnoreCase("quit")) {
                            int endstring=mess.indexOf("***");
                            int endvalue=mess.indexOf("///");
                            String type=mess.substring(0, endstring);
                            System.out.println("Thread:"+ Thread.currentThread().getName()+ "Received type: "+type);
                            Sensor T= new Sensor();
                            T.sData=today.toString();
                            T.sType=type;
                            T.sValue=Double.parseDouble((mess.substring(endstring+3, endvalue)));
                            //T.sValue=Integer.parseInt((mess.substring(endstring+3, endvalue)));
                            setSensor(T);
                            System.out.print("Thread: "+ Thread.currentThread().getName()+ " Sending confirm to client...");

                            output.println("OK");
                            output.flush();
                            Thread.sleep(1000);
                            System.out.println(" >>>SENT!");

                        } else {
                            System.out.println("Thread: "+ Thread.currentThread().getName()+ " Sensor-client closed");

                            break;
                        }


                    }

                } else if (mess.equals("imanormalclient")){
                    System.out.println("Thread: "+ Thread.currentThread().getName()+ " The client is a Normal Client");
                    while(true) {
                        System.out.println("Thread: "+ Thread.currentThread().getName()+ " Waiting for Command");
                        String command = input.nextLine();
                        System.out.println("Thread: "+ Thread.currentThread().getName()+ " Received command: " + command);
                        if (command.equalsIgnoreCase("send")) {
                            Scanner scan = new Scanner(new FileReader("SensorData.txt"));
                            String toSend;
                            while (scan.hasNextLine()) {
                                toSend = scan.nextLine();
                                if (toSend == null)
                                    break;
                                else {

                                    output.println(toSend);
                                    output.flush();
                                    System.out.println("SENT: " + toSend);
                                }
                            }
                            output.println("***EOF***");
                            output.flush();

                        } else if (command.equalsIgnoreCase("quit")) {
                            System.out.println("Thread:" + Thread.currentThread().getName() + " Closing connection with client");
                            break;
                        }
                    }

                }


            } catch (IOException | InterruptedException | NoSuchElementException e) {
                e.printStackTrace();
            }




        }
        public SensorAndClientManager(Socket client){
            this.myClient=client;
        }


    }

    class  Saver implements Runnable{

        public void run(){
            System.out.println(">>>Thread SAVER: saving file periodically");

            try {
                while(true){
                    Thread.sleep(10000);
                    realSaver sv =new realSaver();
                    Thread th =new Thread(sv);
                    th.run();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





        }
    }
    class realSaver implements Runnable{
        public void run(){
            File f =new File("SensorData.txt");
            FileWriter fw= null;
            try {
                fw = new FileWriter(f, false);
                fw.write(getSensor().toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
