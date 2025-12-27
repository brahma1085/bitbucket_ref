/*
 * Created on Mar 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cashServer;
import java.rmi.RemoteException;

import javax.ejb.CreateException;


/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface CashHome extends javax.ejb.EJBHome
{
    public CashRemote create() throws RemoteException,CreateException;
}
