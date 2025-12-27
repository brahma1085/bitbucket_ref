import java.util.*;
import javax.ejb.*;
import java.rmi.*;

public interface Book extends EJBObject
{
  public void addBook(String title) throws RemoteException;
  public void removeBook(String title) throws RemoteException,BookException;
  public Vector getContents() throws RemoteException;
}