package commonServer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
/**
 * @author user
 * 
 * 
 * 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CommonHome extends javax.ejb.EJBHome
{
    public CommonRemote create() throws RemoteException,CreateException;
}
