package lockerServer;

     

import java.rmi.RemoteException;

import masterObject.lockers.LockerMasterObject;
import masterObject.lockers.LockerDetailsObject;
import masterObject.lockers.LockerTransObject;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordNotUpdatedException;
import exceptions.SignatureNotInsertedException;
import exceptions.RecordsNotFoundException;
import masterObject.general.AccountObject;

   
public interface LockersRemote extends javax.ejb.EJBObject
{                	
	
	public int storeLockerMaster(LockerMasterObject lockerMasterObject,int int_verify)throws RemoteException,NomineeNotCreatedException, SignatureNotInsertedException;
	
	public LockerMasterObject getLockerMaster(String string_locker_type,int int_locker_no,int int_type,String date) throws RemoteException;
	
	public LockerDetailsObject[] getLockerDetails()throws RemoteException;
    
	public boolean updateLocker(LockerMasterObject lockerMasterObject,int int_type) throws RemoteException;
	
	//ship......25/07/2006
	public boolean updateLockerMaster(LockerMasterObject lockerMasterObject) throws RemoteException;
	///////////////
	
	public LockerDetailsObject[] getLockers(int int_cab_no) throws RemoteException; 
	
	public LockerDetailsObject[] getLockers(String string_locker_type) throws RemoteException;
	
	//public int rentCollectByTransfer(LockerMasterObject lockerMasterObject,int int_type) throws RemoteException;
	
	public boolean storeLockerTransaction(LockerTransObject lockerTransObject,int int_type) throws RemoteException;
	
	public LockerDetailsObject[] getLockerTypes() throws RemoteException;
    
    public String[] getLockersTypes() throws RemoteException;
    
    public String[] getLockersDesc() throws RemoteException;
    
    //Locker Parameters
    
    public LockerDetailsObject getLockerDetails(String string_locker_type) throws RemoteException;
    
   //Locker Admin
    
    public boolean addLockerTypeParameter(LockerDetailsObject lockerDetailsObject) throws RemoteException;
    
    public boolean addLockerRateParameter(LockerDetailsObject[] lockerDetailsObject,int type) throws RemoteException,RecordNotUpdatedException;
    
    //public int deleteLockerRateParameter(LockerDetailsObject[] lockerDetailsObject) throws RemoteException;
    
    public LockerDetailsObject[] getLockerRateParameter() throws RemoteException;
    
    public int deleteLockerTypeParameter(String string_locker_type) throws RemoteException;
    
    //public boolean checkLockerType(String string_locker_type) throws RemoteException;
    
    public LockerDetailsObject[] getCabinStructure(String string_locker_type) throws RemoteException;
	
	public int storeLockers(LockerDetailsObject lockedetailsobject_cabstructure[],LockerDetailsObject lockerDetailsObject_lockers[]) throws RemoteException;
	
	public double getRent(String string_locker_type,int int_days,int int_category,String date) throws RemoteException;
	
	//public String getShareHolderName(String string_account_type,int int_share_no) throws RemoteException;
        
	//public HashMap getLockerTrans1(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type) throws RemoteException;
    
    public void deleteLockerAccount(LockerMasterObject lockerMasterObject,int type) throws RemoteException,RecordNotUpdatedException;//ship.....added int type....03/04/2006
        
   // Report Methods 
        
    public LockerMasterObject[] getLockerReport(String string_from_date,String string_to_date,int int_type,String string_query) throws RemoteException;
	
    public LockerTransObject[] getLockerTransaction(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query) throws RemoteException;
    
    //ship......06/06/2006
    //public RentTransObject[] getRentTransaction(int int_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query) throws RemoteException;
    ////////
    
 	public double getSecurityDeposit(String string_account_type,int int_account_no) throws RemoteException;
	
 	public double getLockerRate(String string_account_type,int int_account_no) throws RemoteException;
 	
	public int getCabinNo(String string_account_type,int int_account_no) throws RemoteException;
    
    public String[] getCustomerAddress(int int_cid) throws RemoteException;
    
    //For Remainders Report
    
    public int getDeleteTemplate(String ac_type,int int_template_no) throws RemoteException;
    
    public int getDeleteTemplate(String ac_type,int[] int_template_no) throws RemoteException;
    
    public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no,String date_time) throws RemoteException;
    
    public String[] getTemplate(String acty) throws RemoteException;
    
    public int getRemainderNotification(String ac_type,int ac_no,String email_flag,String sms_flag,String date) throws RemoteException;
    
    public String[] getOperatedBy() throws RemoteException;
    
    //Examples for user Transaction
    
    public int[] getAccountNumbers() throws RemoteException;
    
    public double[] getCreditAmount() throws RemoteException;
    
    public void getDeleteRecord(int ac_no) throws RemoteException;
    
    public int getUpdateRecord(int ac_no,double credit_amount) throws RemoteException;
    
    //public LockerMasterObject[] getRentDueAccountHolders(int int_type,String date) throws RemoteException;
    
    //public int getPayLockerRent(LockerMasterObject lockerMasterObject,int int_type) throws RemoteException,InOperativeAccountException,AccountClosedException,FreezedAccountException;
    
    //ship.....20/03/2006
    public boolean checkPwd(String lk_actype,int lk_acno,String lk_pwd) throws RemoteException;
    //////////
    
    //public String getSysTime() throws RemoteException;//ship....23/03/2006
    
    public boolean deleteLockerTransaction(LockerTransObject lockerTransObject,int type) throws RemoteException,RecordNotUpdatedException;//ship.......24/03/2006....31/03/2006
    
    public LockerMasterObject[] getAutoExtnLockers(String date) throws RecordsNotFoundException,RemoteException;//ship......04/04/2006
    
    //ship......29/04/2006
    public LockerMasterObject[] getRentDueReport(String string_to_date,int int_type,String string_query) throws RemoteException;
    /////////////
     
    //ship......11/08/2006
    public boolean checkReminderNotice(String ac_type,int ac_no,String date) throws RemoteException;
    
    public int storeReminderNotice(String ac_type,int ac_no,String date,String notice_type) throws RemoteException;
    ////////////
    
    //ship.....14/02/2007
    public AccountObject getShareAccount(int cid) throws RemoteException;
    ///////////
    
    //ship....07/03/2007
    public LockerDetailsObject getLockerDetails(String locker_type,int locker_no) throws RemoteException;
    ///////////////

	public int[] getDistinctCabs(String lkTypes) throws RemoteException;

	public LockerDetailsObject[] getLockerCabStructure(int cabNum)throws RemoteException;
}