import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server extends UnicastRemoteObject implements ServerInterface {
    @Override
    public String getDate() throws RemoteException {
        return new Date().toString();
    }

    @Override
    public double getSum(double a, double b) throws RemoteException {
        return a+b;
    }

    //BIsogna gestire l'eccezione con il costruttore (vedi sotto)

    public Server() throws RemoteException{
        super(1101);


    }

    public static void main(String args[]){
        try {
            System.setProperty("java.rmi.server.hostname","whitelodge.ns0.it");
            Registry registry = LocateRegistry.getRegistry();
            ServerInterface service = new Server();
            registry.bind("myservice", service);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }


    }



}
