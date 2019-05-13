import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int balance=0;
    private int port;
    ServerSocket server;



    public Server (){

    }
    public void go(){

        try {
            server = new ServerSocket(port);
            System.out.println("Starting server on port "+port);

        } catch (IOException e) {
            System.out.println("Cannot start server on port "+port);
            e.printStackTrace();
            System.exit(-1);

        }
        Balancer balancer = new Balancer();
        Thread bal = new Thread(balancer);
        bal.start();

        while (true){
            try {
                System.out.println("Ready to accept connections...");
                Socket client = server.accept();

                ClientManager cm = new ClientManager(client);
                Thread t = new Thread(cm);
                t.start();

                /*Balancer balancer = new Balancer();
                Thread bal = new Thread(balancer);
                bal.start();*/


            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    public int getBalance() {
        return balance;
    }

    public synchronized void setBalance(int balance) {
        this.balance = this.balance+balance;

    }
    public Server(int port){
        this.port = port;
    }
}
