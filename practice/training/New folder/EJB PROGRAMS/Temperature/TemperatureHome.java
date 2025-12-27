import javax.ejb.*;
import java.rmi.*;

public interface TemperatureHome extends EJBHome
{
  public Temperature create() throws CreateException,RemoteException;
}