package customerServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.ejb.EJBObject;

import masterObject.customer.CustomerMasterObject;
import exceptions.CustomerNotFoundException;


public interface CustomerRemote extends EJBObject {
	
	public String[] getStateNames(int index)throws RemoteException;
	
	public String[] getCustomerParams(String str1) throws RemoteException;
	
	public String[] getCustomerIds(String cum_name) throws RemoteException;
	
	public int confirmCustomerId(String cid) throws RemoteException;
	
	//added by Brahma
	public int[] getCustomerAddrType(int cid) throws RemoteException,SQLException;
	
	public String getCustomerType(int cid) throws RemoteException,SQLException,CustomerNotFoundException;
	
    public String getBOD(int cid) throws RemoteException,CustomerNotFoundException,SQLException ;
	
	public int storeCustomerParams(String str,String arr) throws RemoteException;
	
	public String getCustomerName(String cid) throws RemoteException;

	public boolean checkAvailability(String table,String col,String value) throws RemoteException;

	public int removeData(String table,String col,String value) throws RemoteException;
	
	public int storeCustomer(CustomerMasterObject cm)throws RemoteException;
	
	public int verifyCustomer(int cid,String uid,String tml) throws RemoteException;
	
	public CustomerMasterObject getCustomer(int cid)  throws RemoteException,CustomerNotFoundException;
	
	public String getCustomerAddress(int cid)throws RemoteException,CustomerNotFoundException,SQLException;
	
    public java.util.HashMap getAddress(int cid) throws CustomerNotFoundException,RemoteException;
    
	public int deleteCustomer(int cid) throws RemoteException;
	
	public String[] getIndivisual_Institute(String str1)throws RemoteException;

	public CustomerMasterObject customer_verify(int cid)throws RemoteException;
}
