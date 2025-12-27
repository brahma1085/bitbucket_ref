import java.rmi.*;
import javax.ejb.*;

public interface BookHome extends EJBHome
{
 public Book create(String person) throws CreateException,RemoteException;
 public Book create(String person,String id) throws CreateException,RemoteException;
}