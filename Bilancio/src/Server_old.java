/*
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int balance=0;
    private int port;
    ServerSocket server;
    Support sp;
    SupportBalancr spb;



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
        spb= new SupportBalancr();
        sp = new Support();
        Balancer balancer = new Balancer(spb);
        Thread bal = new Thread(balancer);
        bal.start();

        while (true){
            try {
                setBalance(sp.getProva());
                System.out.println("Ready to accept connections...");
                System.out.println(getBalance());
                System.out.println(spb.getBal());
                Socket client = server.accept();

                ClientManager cm = new ClientManager(client,sp, spb);
                Thread t = new Thread(cm);
                t.start();



            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    public int getBalance() {

        return balance;
    }

    public synchronized void setBalance(int bal) {
        balance =bal;
        spb.setBal(balance);

    }
    public Server(int port){
        this.port = port;
    }
}
*/