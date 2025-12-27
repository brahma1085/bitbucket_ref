import javax.ejb.EJBObject;
import java.rmi.*;

public interface cmpp extends EJBObject
{
public void deposit(double amount)throws RemoteException;

public void withdraw(double amount)throws RemoteException;

public double getBalance()throws RemoteException;

}
