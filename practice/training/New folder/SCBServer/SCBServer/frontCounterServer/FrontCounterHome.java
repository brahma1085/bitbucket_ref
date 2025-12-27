/*
 * Created on Mar 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package frontCounterServer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FrontCounterHome extends javax.ejb.EJBHome
{
    public FrontCounterRemote create() throws RemoteException,CreateException;
}
