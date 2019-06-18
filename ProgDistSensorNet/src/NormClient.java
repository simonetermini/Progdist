import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NormClient {
    public static void main (String[] args){
        if (args.length<2){
            System.out.println("Insert address and port of the server");
            System.out.println("Protocol: \"java NormClient address port\" ");
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
            Thread.sleep(2000);
            String question= in.nextLine();

            if (question.equals("whoareyou"))
            {
                pw.println("imanormalclient");
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
            try {
                    System.out.println("insert 'send' to ask sensors information from the server or 'quit' to close connection with server");
                    String command = scan.nextLine();
                    if (command.equalsIgnoreCase("quit")) {
                        System.out.println("Quitting connection with server at address: " + address + " and port: " + port);
                        System.out.println("Quitting client...");
                        pw.println("quit");
                        pw.flush();
                        client.close();
                        break;
                    }else if (command.equalsIgnoreCase("send")){
                        pw.println(command);
                        pw.flush();
                        //Thread.sleep(1000);
                        FileWriter fw = new FileWriter("ReceivedFile.txt", false);
                        while (in.hasNext()  &&  !in.nextLine().equals("]")){
                            String answer = in.nextLine();
                            System.out.println("Received Data" + answer);
                            fw.write(answer+"\n");
                            fw.flush();
                        }
                        //fw.close();
                    }else{
                        System.out.println("insert a valid command");
                    }
            }catch (IOException  e ){// InterruptedException e) {
                e.printStackTrace();
            }





        }







    }
}

