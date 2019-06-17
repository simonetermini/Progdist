import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SensorServer {

    private int i=0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int[] getValue() {
        return value;
    }

    public void setValue(int value, int k) {
        this.value[k] = value;
    }

    public String[] getSensType() {
        return sensType;
    }

    public void setSensType(String sensType, int k) {
        this.sensType[k] = sensType;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String data, int k) {
        this.data[k] = data;
    }

    private int[] value;
    private String[] sensType;
    private String[] data;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    Date today = calendar.getTime();
    int port ;

    public SensorServer(int port){
        this.port = port;
    }
    public void go() {




        ServerSocket server = null;
        String[] array;
        int i;


        try {
            System.out.println("Starting server on port: " + port);
            server = new ServerSocket(port);
            System.out.println("Server Started!");

        } catch (IOException e) {
            System.out.println("Cannot start server on port: " + port);
            e.printStackTrace();
            System.exit(-1);
        }


        while (true){

            try {
                System.out.println(">>>Waiting for connection...");
                Socket client = server.accept();
                System.out.println("Connection: OK");
                System.out.println("Client data: "+ client.getRemoteSocketAddress());
                SensorAndClientManager myClient = new SensorAndClientManager(client);
                Thread t = new Thread(myClient);
                t.start();




            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public class SensorAndClientManager implements Runnable{
        Socket myClient;



        public void run(){

            System.out.println("Starting Sensor Manager");
            System.out.println(" WAITING FOR CLIENT TYPE");
            try {
                var input = new Scanner(myClient.getInputStream());
                var output = new PrintWriter((myClient.getOutputStream()));
                output.println("whoareyou");
                output.flush();
                Thread.sleep(3000);
                String mess=input.nextLine();
                System.out.println("Recived message: " + mess);
                if (mess.equals("imasensor")){

                    while (input.hasNextLine()){
                        System.out.println("Waiting for sensor data...");
                   // while (!mess.equalsIgnoreCase("quit")){
                        mess=input.nextLine();
                        if (!mess.equalsIgnoreCase("quit")) {
                            int endstring=mess.indexOf("***");
                            int endvalue=mess.indexOf("///");
                            int i=getI();
                            String type=mess.substring(0, endstring);
                            System.out.println("Received type: "+type);
                            setSensType(type, i);
                            setValue(Integer.parseInt((mess.substring(endstring+1, endvalue))), i);
                            setData(today.toString(), i);
                            setI(i+ 1);
                            System.out.println("Recived message: " + mess);
                        } else {
                            output.println(">>>Thread: Sensor-client closed");
                            output.flush();
                        }


                    }

                } else if (mess.equals("normalclient")){
                    output.println(">>>Thread: Hi normal client!");
                    output.flush();

                }


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }




        }
        public SensorAndClientManager(Socket client){
            this.myClient=client;
        }


    }
    class Saver implements Runnable{

        public void run(){
            System.out.println("saving file periodically");
            File f = new File("SensorData.txt");
            try {
                FileWriter fw = new FileWriter(f, true);
                while (true) {
                    Thread.sleep(10000);
                    int i=0;
                    while (i<getI())
                    {
                        String toSave="()" + getSensType()[i] + "***" + getValue()[i] + "***" + getData()[i] + " ";
                        fw.write(toSave);
                        fw.flush();
                        i++;
                    }
                    fw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }


}
