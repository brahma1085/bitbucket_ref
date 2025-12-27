import javax.ejb.*;
import java.rmi.*;

public interface Temperature extends EJBObject
{
 public double FahrenheitToCelcius(double degree) throws RemoteException;
}