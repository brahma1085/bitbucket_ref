/*
 * Created on 8 Mar, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package shareServer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface ShareHome extends EJBHome
{
	ShareRemote create() throws RemoteException,CreateException;
	

}
