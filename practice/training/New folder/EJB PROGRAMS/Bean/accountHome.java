import javax.ejb.*;
import java.rmi.*;
import java.util.*;

public interface accountHome extends EJBHome
{

public account create(String id,String name)throws CreateException,RemoteException;

public account findByPrimaryKey(accountPK key)throws FinderException,RemoteException;

}



