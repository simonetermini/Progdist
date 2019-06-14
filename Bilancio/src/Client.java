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
                var in = new Scanner(socket.getInputStream());

                pw.println(message);
                pw.flush();

                if (message.equals("QUIT")){
                    System.out.println("Quitting client...");
                    socket.close();
                    break;
                }
                String answer =in.nextLine();
                if (answer.equals("OK")){
                    System.out.println("confirmed command " + message);
                }
                else
                {
                    System.out.println("Unexpected answer from server: " + answer);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    }
}
