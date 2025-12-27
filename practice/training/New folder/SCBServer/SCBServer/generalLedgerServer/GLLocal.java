package generalLedgerServer;

import java.rmi.RemoteException;

import javax.ejb.EJBLocalObject;

public interface GLLocal extends EJBLocalObject{
	
	public String[][] getBranchCode(String br_location) ;

}
