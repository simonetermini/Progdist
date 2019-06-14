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

    public void setValue(int value, int i) {
        this.value[i] = value;
    }

    public String[] getSensType() {
        return sensType;
    }

    public void setSensType(String sensType, int i) {
        this.sensType[i] = sensType;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String data, int i) {
        this.data[i] = data;
    }

    private int[] value;
    private String[] sensType;
    private String[] data;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"), Locale.ITALY);
    Date today = calendar.getTime();

    public static void main(String[] args) {




        if (args.length<1){
            System.out.println("Insert port value");
            System.out.println("Start server with: >>>java SensorServer <portNumber>");
            System.exit(-1);
        }


        int port = Integer.parseInt(args[0]);
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



            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public class SensorManager implements Runnable{
        Socket myClient;


        public void run(){

            System.out.println("Starting Sensor Manager");
            System.out.println(" WAITING FOR CLIENT TYPE");
            try {
                var input = new Scanner(myClient.getInputStream());
                var output = new PrintWriter((myClient.getOutputStream()));
                output.println("whoareyou");
                output.flush();
                String mess=input.nextLine();
                System.out.println("Recived message: " + mess);
                if (mess.equals("sensor")){
                    while (input.hasNextLine()){
                   // while (!mess.equalsIgnoreCase("quit")){
                        mess=input.nextLine();
                        if (!mess.equalsIgnoreCase("quit")) {
                            setSensType(mess.substring(0, 20), getI());
                            setValue(Integer.parseInt(mess.substring(21, 30)), getI());
                            setData(today.toString(), getI());
                            setI(getI() + 1);
                            System.out.println("Recived message: " + mess);
                        } else {
                            output.println(">>>Thread: Sensor-client closed");
                            output.flush();
                        }


                    }

                } else if (mess.equals("normalclient")){

                }


            } catch (IOException e) {
                e.printStackTrace();
            }




        }


    }
}
