import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main (String[] args){

        String ipaddress = args[0];

        int port = Integer.parseInt(args[1]);

        Socket socket;

        try {
            socket = new Socket(ipaddress, port);
        } catch (IOException e) {
            System.out.println("error connecting to "+ ipaddress + " on port: "+ port);
            e.printStackTrace();
            return;
        }

        //stiamo ora scrivendo su socket


        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println("hello server!");
            pw.flush();
            //esercizio (inizio)
            InputStreamReader is = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(is);

            String s= br.readLine();
            System.out.println("received message: "+ s + " from server");
            //esercizio (fine)
            //Socket prova = new Socket(ipaddress, port);
            //var s = new InputStreamReader(prova.getInputStream());
            //System.out.println("Il server risponde: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
