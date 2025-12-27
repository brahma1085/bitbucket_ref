package lockerServer;

import javax.ejb.EJBLocalObject;
import java.rmi.RemoteException;
import masterObject.lockers.LockerMasterObject;
import masterObject.lockers.LockerDetailsObject;
import masterObject.lockers.LockerTransObject;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordNotUpdatedException;
import exceptions.SignatureNotInsertedException;
import exceptions.RecordsNotFoundException;
import masterObject.general.AccountObject;


public interface LockersLocal extends EJBLocalObject
{
    public int storeLockerMaster(LockerMasterObject lockerMasterObject,int int_verify)throws NomineeNotCreatedException, SignatureNotInsertedException;
	
	public LockerMasterObject getLockerMaster(String string_locker_type,int int_locker_no,int int_type,String date);
	
	public boolean updateLocker(LockerMasterObject lockerMasterObject,int int_type);
	
	//ship......25/07/2006
	public boolean updateLockerMaster(LockerMasterObject lockerMasterObject);
	///////////////
	
	
	public LockerDetailsObject[] getLockers(int int_cab_no); 
	
	public LockerDetailsObject[] getLockers(String string_locker_type);
	
	//public int rentCollectByTransfer(LockerMasterObject lockerMasterObject,int int_type);
	
	public boolean storeLockerTransaction(LockerTransObject lockerTransObject,int int_type);
	
	public LockerDetailsObject[] getLockerTypes();
    
    //public String[] getLockersTypes() throws SQLException;
    
    //public String[] getLockersDesc() throws SQLException;
    
    //Locker Parameters
    
    public LockerDetailsObject getLockerDetails(String string_locker_type);
    
   //Locker Admin
    
    public boolean addLockerTypeParameter(LockerDetailsObject lockerDetailsObject);
    
    public boolean addLockerRateParameter(LockerDetailsObject[] lockerDetailsObject,int type) throws RecordNotUpdatedException;
    
    //public int deleteLockerRateParameter(LockerDetailsObject[] lockerDetailsObject);
    
    public LockerDetailsObject[] getLockerRateParameter();
    
    public int deleteLockerTypeParameter(String string_locker_type);
    
    //public boolean checkLockerType(String string_locker_type);
    
    public LockerDetailsObject[] getCabinStructure(String string_locker_type);
	
	public int storeLockers(LockerDetailsObject lockedetailsobject_cabstructure[],LockerDetailsObject lockerDetailsObject_lockers[]);
	
	public double getRent(String string_locker_type,int int_days,int int_category,String date);
	
	//public String getShareHolderName(String string_account_type,int int_share_no);
        
	//public HashMap getLockerTrans1(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type);
    
    public void deleteLockerAccount(LockerMasterObject lockerMasterObject,int type) throws RecordNotUpdatedException;//ship.....added int type....03/04/2006
        
   // Report Methods 
        
    public LockerMasterObject[] getLockerReport(String string_from_date,String string_to_date,int int_type,String string_query);
	
    public LockerTransObject[] getLockerTransaction(String string_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query);
    
    //ship......06/06/2006
    //public RentTransObject[] getRentTransaction(int int_account_no,String string_account_type,String string_from_date,String string_to_date,int int_type,String string_query);
    ////////
    
 	public double getSecurityDeposit(String string_account_type,int int_account_no);
	
 	public double getLockerRate(String string_account_type,int int_account_no);
 	
	public int getCabinNo(String string_account_type,int int_account_no);
    
    public String[] getCustomerAddress(int int_cid);
    
    //For Remainders Report
    
    public int getDeleteTemplate(String ac_type,int int_template_no);
    
    public int getDeleteTemplate(String ac_type,int[] int_template_no);
    
    public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no,String date_time);
    
    public String[] getTemplate(String acty);
    
    public int getRemainderNotification(String ac_type,int ac_no,String email_flag,String sms_flag,String date);
    
    public String[] getOperatedBy();
    
    //Examples for user Transaction
    
    public int[] getAccountNumbers();
    
    //public double[] getCreditAmount() throws SQLException;
    
    public void getDeleteRecord(int ac_no);
    
    public int getUpdateRecord(int ac_no,double credit_amount);
    
    //public LockerMasterObject[] getRentDueAccountHolders(int int_type,String date);
    
    //public int getPayLockerRent(LockerMasterObject lockerMasterObject,int int_type) throws InOperativeAccountException,AccountClosedException,FreezedAccountException;
    
    //ship.....20/03/2006
    public boolean checkPwd(String lk_actype,int lk_acno,String lk_pwd);
    //////////
    
    //public String getSysTime() throws ;//ship....23/03/2006
    
    public boolean deleteLockerTransaction(LockerTransObject lockerTransObject,int type) throws RecordNotUpdatedException;//ship.......24/03/2006....31/03/2006
    
    public LockerMasterObject[] getAutoExtnLockers(String date) throws RecordsNotFoundException;//ship......04/04/2006
    
    //ship......29/04/2006
    public LockerMasterObject[] getRentDueReport(String string_to_date,int int_type,String string_query);
    /////////////
    
    //ship......11/08/2006
    public boolean checkReminderNotice(String ac_type,int ac_no,String date);
    
    public int storeReminderNotice(String ac_type,int ac_no,String date,String notice_type);
    /////////////
    
    //ship.....14/02/2007
    public AccountObject getShareAccount(int cid);
    ///////////
    
    //ship.....07/03/2007
    public LockerDetailsObject getLockerDetails(String locker_type,int locker_no);
    ///////////
}