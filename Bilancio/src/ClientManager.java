import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.*;

public class  ClientManager implements Runnable {
    Socket assigned_client;
    Server pr = new Server();

    @Override
    public void run() {
        System.out.println("Starting thread ClientManager");

        try {
            var br = new BufferedReader(new InputStreamReader(assigned_client.getInputStream()));
            boolean cont = true;
            while (cont){
                String message = br.readLine();
                String command=null;
                if (message.length()>3) {
                    command = message.substring(0, 3);
                    System.out.println("prova stringa " + command);
                }
                int increm=0;
                try {

                    if (command.equals("INC")) {


                        increm = Integer.parseInt(message.substring(4));
                        System.out.println("il bilancio sarà incrementato di: " + increm);
                        pr.setBalance(increm);
                        System.out.println("bilancio attuale: " + pr.getBalance());


                    } else if (command.equals("DEC")) {
                        increm = Integer.parseInt(message.substring(4));
                        System.out.println("il bilancio sarà decrementato di: " + increm);
                        pr.setBalance(-increm);
                        System.out.println("bilancio attuale: " + pr.getBalance());
                    }
                }catch (NumberFormatException r)
                {
                    System.out.println("Dopo INC o DEC inserire un valore valido");
                    r.printStackTrace();

                }
                System.out.println("Received message: "+message);

                if (message.equalsIgnoreCase("quit")){
                    System.out.println("Terminating ClientManager");
                    cont = false;

                    assigned_client.close();
                }
            }

        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }



    public ClientManager(Socket s){
        this.assigned_client = s;
    }



}
