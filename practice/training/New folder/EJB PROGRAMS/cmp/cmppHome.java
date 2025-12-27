import javax.ejb.*;
import java.rmi.*;

public interface cmppHome extends EJBHome
{

public cmpp create(String accountid,String ownername,double balance)throws RemoteException,CreateException;
public cmpp findByPrimaryKey(String primaryKey) 
    throws FinderException, RemoteException;

}
