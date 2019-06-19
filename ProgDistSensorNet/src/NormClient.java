
import java.io.IOException;

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
            Thread.sleep(500);
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
                    pw.println(command);
                    pw.flush();
                    if (command.equalsIgnoreCase("quit")) {
                        System.out.println("Quitting connection with server at address: " + address + " and port: " + port);
                        client.close();
                        break;
                    }else if (command.equalsIgnoreCase("send")){
                        System.out.print("Insert name for the output file: ");
                        String nomefile = scan.nextLine();
                        PrintWriter ppw=new PrintWriter(nomefile+".txt");
                        System.out.println("File created!");
                        while (true){
                            String answer = in.nextLine();
                            if (answer.equalsIgnoreCase("***EOF***"))
                                break;

                            else {
                                System.out.println("Received Data" + answer);
                                ppw.write(answer + "\n");
                                ppw.flush();
                            }
                        }
                    }else{
                    }
            }catch (IOException  e ){
                e.printStackTrace();
            }

        }
    }
}

