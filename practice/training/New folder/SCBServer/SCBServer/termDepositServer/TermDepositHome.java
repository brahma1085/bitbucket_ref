
package termDepositServer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TermDepositHome extends EJBHome {
    public TermDepositRemote create() throws RemoteException,CreateException;
}
