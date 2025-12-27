import javax.ejb.EJBObject;
import java.rmi.*;

public interface account extends EJBObject
{

public void deposit(double amount)throws RemoteException;

public void withdraw(double amt)throws RemoteException;

public double getBalance()throws RemoteException;



}