import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SensorServer {


    public class Sensor{

        public String toString(){
            return(" \n >Sensor Type= " +sType+
                    "\n >Sensor value= " +sValue +
                    "\n >Data= "+ sData)+" \n ";
        }

        String sType;
        int sValue;
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
                Thread t = new Thread(myClient);
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

            //System.out.println("Thread:"+ Thread.currentThread().getName()+ "Starting Sensor Manager");
            System.out.println("WAITING FOR CLIENT TYPE");
            try {
                Scanner input = new Scanner(myClient.getInputStream());
                PrintWriter output = new PrintWriter((myClient.getOutputStream()));
                output.println("whoareyou");
                output.flush();
                Thread.sleep(3000);
                String mess=input.nextLine();
                //System.out.println("Recived message: " + mess);
                if (mess.equals("imasensor")){
                    System.out.println("The client is a Sensor Client");
                    while (input.hasNextLine()){
                        System.out.println("Waiting for sensor data...");
                        mess=input.nextLine();
                        if (!mess.equalsIgnoreCase("quit")) {
                            int endstring=mess.indexOf("***");
                            int endvalue=mess.indexOf("///");
                            String type=mess.substring(0, endstring);
                            System.out.println("Thread:"+ Thread.currentThread().getName()+ "Received type: "+type);
                            Sensor T= new Sensor();
                            T.sData=today.toString();
                            T.sType=type;
                            T.sValue=Integer.parseInt((mess.substring(endstring+3, endvalue)));
                            setSensor(T);
                            System.out.print("Sending confirm to client...");

                            output.println("OK");
                            output.flush();
                            Thread.sleep(1000);
                            System.out.println(" >>>SENT!");

                        } else {
                            System.out.println("Thread:"+ Thread.currentThread().getName()+ "Sensor-client closed");

                            break;
                        }


                    }

                } else if (mess.equals("imanormalclient")){
                    /*output.println(">>>Thread: Hi normal client!");
                    output.flush();*/
                    System.out.println("The client is a Normal Client");
                    while (true) {

                        System.out.println("Waiting for command from NormalCLient: " + myClient.getRemoteSocketAddress());
                        Thread.sleep(1500);
                        String command=input.nextLine();
                        if (command.equalsIgnoreCase("send")) {
                            FileReader fr = new FileReader("SensorData.txt");
                            BufferedReader br = new BufferedReader(fr);
                            while (br.read()!=-1) {
                                String prova = br.readLine();
                                if (prova.equalsIgnoreCase("]")) {
                                System.out.println("finish sending");
                                break;
                                }
                                output.println(prova);
                                output.flush();
                        }
                        //fr.close();
                        } else if (command.equalsIgnoreCase("quit")) {
                            System.out.println("Thread:"+ Thread.currentThread().getName()+ "Thread: Closing connection with client");
                            break;
                        }else{
                            System.out.println("command not accepted: "+ input);

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
            //File f =new File("SensorData.txt");
            //FileWriter fw=new FileWriter(f, false);

            try {
                while(true){
                    Thread.sleep(10000);
                    realSaver sv =new realSaver();
                    Thread th =new Thread(sv);
                    th.run();
                    //fw.write(getSensor().toString());
                    //fw.flush();
                }
            //}catch (IOException e) {
              //  e.printStackTrace();
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
