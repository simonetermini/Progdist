import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.lang.*;
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
            String scelta;
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println("Seleziona messaggio da inviare al server");
                System.out.println("  1-Messaggio Qualsiasi");
                System.out.println("  2-Esci dal server");
                Scanner scan = new Scanner(System.in);

                scelta = scan.nextLine();
                if (scelta == "1") {
                    pw.println("hello server!");
                    pw.flush();
                } else if (scelta == "2") {
                    pw.println("quit server");
                    pw.flush();
                }
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
