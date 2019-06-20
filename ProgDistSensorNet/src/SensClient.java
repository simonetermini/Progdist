
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SensClient {
    public static void main (String[] args){
        if (args.length<2){
            System.out.println("Insert address and port of the server");
            System.out.println("Protocol: \"java Server address port\" ");
            System.exit(-1);
        }
        String address=args[0];
        int port=Integer.parseInt(args[1]);
        Socket client=null;
        Scanner scan = new Scanner(System.in);
        PrintWriter pw = null;
        Scanner in=null;
        try {
            client = new Socket(address, port);
            in = new Scanner(client.getInputStream());
            pw = new PrintWriter(client.getOutputStream());
            Thread.sleep(500);
            String question= in.nextLine();

            if (question.equals("whoareyou"))
            {
                pw.println("imasensor");
                pw.flush();
            }else{
                System.out.println(">>>Server not corresponding to communication protocol...");
                System.out.println(">>>>>Please check address and port");
                System.exit(-1);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Host not accessible");
            e.printStackTrace();
            System.exit(-1);
        }

        while(true){
            String type=null;
            String toSend=null;
            double value=0;
            try {
                System.out.println(">>>Make your choice");
                System.out.println(">(1) To insert a 'Type' and a 'Value' for a sensor");
                System.out.println(">(2) To quit the connection with the server: "+ client.getRemoteSocketAddress());
                System.out.print(">>>(1/2): ");
                int c=Integer.parseInt(scan.nextLine());
                switch(c){
                    case (2):

                        System.out.println("Quitting connection with server at address: "+ address + " and port: " + port);
                        System.out.println("Quitting client...");
                        pw.println("quit");
                        pw.flush();
                        client.close();
                        System.exit(1);
                    case (1):
                        System.out.println("insert sensor type");
                        type=scan.nextLine();
                        System.out.print("insert value for sensor " + type + ": ");
                        value=Double.parseDouble(scan.nextLine());
                        toSend=type+"***"+String.valueOf(value)+"///";
                        pw.println(toSend);
                        pw.flush();
                        Thread.sleep(2000);
                        String answer = in.nextLine();

                        if (answer.equals("OK")){
                            System.out.println("confirmed input on system. Type:"+type+ " Value:"+ value);
                        }



                }



            }catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }








    }
}
