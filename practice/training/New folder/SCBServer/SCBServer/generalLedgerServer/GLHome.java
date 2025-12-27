package generalLedgerServer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * @author J.ShivaKumaar
 */
public interface GLHome extends javax.ejb.EJBHome
{
	GLRemote create() throws CreateException,RemoteException;
}
