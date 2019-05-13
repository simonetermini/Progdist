import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){

        if (args.length<2){
            System.out.println("Please specify addresss and port as arguments!");
            System.exit(-1);
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        Socket socket = null;


        try {
            socket = new Socket(address,port);
        } catch (IOException e) {
            System.out.println("Unreachable host!");
            e.printStackTrace();
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);

        while (true){
            String message;
            System.out.print("Insert message for server->");
            message = scanner.nextLine();

            System.out.println("Sending message \""+message+"\" to "+socket.getRemoteSocketAddress());

            try {
                var pw = new PrintWriter(socket.getOutputStream());

                pw.println(message);
                pw.flush();

                if (message.equalsIgnoreCase("quit")){
                    System.out.println("Quitting client...");
                    socket.close();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    }
}
