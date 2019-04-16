import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        Socket socket;
        ServerSocket server;

        try {
            int port = Integer.parseInt(args[0]);
            server= new ServerSocket(port);

            System.out.println("server listening");

            socket = server.accept();//questa è una chiamata bloccante... blocca il server


            System.out.println("accepted connection from client");


        } catch (IOException e) {
            System.out.println("Error creating socket");
            e.printStackTrace();
            return;
        }
        String s= null;

        try {

            while (s!="quit server")
            {
                InputStreamReader is = new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(is);

                s = br.readLine();
                System.out.println("received message: " + s + " from client!");
                // esercizio (inizio)
                OutputStreamWriter sw = new OutputStreamWriter((socket.getOutputStream()));
                BufferedWriter bw = new BufferedWriter(sw);
                bw.write("Hi client");
                bw.flush();


                //esercizio (fine)

                if (s!="quit server"){
                    System.out.println("Server is listening");
                } else System.out.println("Server is closing...");

                socket = server.accept();//questa è una chiamata bloccante... blocca il server

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.close();
        } catch (IOException e) {
            System.out.println("error with closing");
            e.printStackTrace();
        }


    }
}
