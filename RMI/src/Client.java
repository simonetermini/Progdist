import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String args[]){

        try {
            ServerInterface si = (ServerInterface) Naming.lookup("rmi/127.0.0.1/myservice");
            System.out.print("Ora gli chiedo l'orario ");
            System.out.println(si.getDate());
            System.out.println("Ora sommo due numeri");
            System.out.println("il risultato e': "+ si.getSum(3.14, 1.2));

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
