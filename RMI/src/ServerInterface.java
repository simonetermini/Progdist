import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public String getDate() throws RemoteException;
    public double getSum(double a, double b) throws RemoteException;

}
