package commonServer;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.naming.NamingException;

import masterObject.administrator.UserActivityMasterObject;
import masterObject.clearing.ClearingObject;
import masterObject.cashier.CashObject;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.InsufficientBalanceException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.SignatureNotInsertedException;
import exceptions.UserNotExistException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccCategoryObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.AddressTypesObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;

public interface CommonRemote extends javax.ejb.EJBObject
{	
	
//	public UserActivityMasterObject[] getUserActivity(String tml_no,String uid,String from_date, String to_date,String ip_address,String queryNum)throws UserNotExistException,RemoteException;
	//public boolean getVerifiedCustomer(int custid) throws RecordsNotFoundException,RemoteException;
	public int getCidofAccountNo(String ac_ty,int ac_no)throws RemoteException;
	//public int storeUserActivity(UserActivityMasterObject user_activ)throws RemoteException;
	public String getModulecode(String abbr)throws RemoteException;
	public SignatureInstructionObject[] getSignatureDetails(int cid)throws RemoteException; 
	public boolean getVerifiedCustomer(int custid) throws RecordsNotFoundException,RemoteException;
	public String getJointSBAccountName(String agt_acctype,int agt_accno,String pers_acc_type,int pers_acc_no,String jtacctype,int jtaccno,int id) throws RemoteException,SQLException;
	public UserActivityMasterObject[] getUserActivity(String tml_no,String uid,String from_date, String to_date,String ip_address,String queryNum)throws UserNotExistException,RemoteException;
	//ship.....09/06/2006
	//public boolean check(String uid,String pwd,String tml) throws RemoteException;
	public String checkLogin(String uid,String pwd,String tml,int flag,String date,String time) throws RemoteException;
	
	public String checkLogin(String uid,String tml,String date,String time) throws RemoteException;
	
	public boolean checkForHoliday(String date) throws RemoteException;
	/////////////
	
	//To check Customer ID
	public boolean checkCid(int cid,int flag)throws RemoteException;
	
	public boolean checkQuarter() throws RemoteException;
	
	public Object[] checkScrollAttached(int scno,String date,String actype,String acno,int type) throws ScrollNotFoundException,RemoteException;
	
	public AccountObject getAccount(String trn_mode,String acctype,int accno,String date) throws RemoteException;
	
	public AccountObject[] getAccounts(String acctype,int accno,String date)throws RemoteException;
	
	public AccSubCategoryObject[] getAccSubCategories(int no) throws RemoteException;
	
	public AccSubCategoryObject[] getAccSubCategories() throws RemoteException;
	
	public AccCategoryObject[] getAccCategories() throws RemoteException;
	
	public Address getAddress(int cid,int addr_type) throws RemoteException;
	
	public AddressTypesObject[] getAddressTypes() throws RemoteException;
	
	public BranchObject[] getBranchDetails(int bcode)throws RemoteException;
	
	public String[] getColumns(String tabstr) throws RemoteException;

	//public Object[][] getRows(String str,String column1,String column2,int no,String acc_type,int type) throws RemoteException,RecordsNotFoundException;
	public String getDateTime() throws RemoteException;
	
	public Object getGenParam(String column)throws RemoteException;
	
	public String getHeading() throws RemoteException,NamingException;
	
	public ModuleObject[] getMainModules(int a,String str) throws RemoteException;
	
	public NomineeObject[] getNominee(int reg_no)throws RemoteException;
	
	public int[] getPageDetails() throws RemoteException;
	
	public Object[][] getRows(String str,String column,int no) throws RemoteException;
	
	public Object[][] getRows(String str,String column1,String column2,int no,String acc_type,int type) throws RemoteException,RecordsNotFoundException;
	
	public SignatureInstructionObject[] getSignatureDetails(int accno,String type) throws RemoteException;
	
	/*public String getSysDate() throws RemoteException;
	
	public String getSysDateTime() throws RemoteException;
	
	public String getSysTime() throws RemoteException;*/
	
	public boolean isHoliday(String date) throws RemoteException;
	
	public String nextWorkingDay(String date) throws RemoteException;
	
	public String prevWorkingDay(String date) throws RemoteException;
	
  //public int storeAccountTransaction(AccountTransObject am) throws RemoteException,SQLException, AccountClosedException, InOperativeAccountException, FreezedAccountException;
	public int storeAccountTransaction(AccountTransObject am) throws RemoteException,SQLException;
	
	
	public int storeNominee(NomineeObject nom[],String actype,int acno,String date) throws RemoteException;
	
	public int storePayOrder(PayOrderObject po) throws RemoteException,SQLException;
	
	public boolean storeSignatureDetails(SignatureInstructionObject a[],int acno) throws RemoteException;
	
	public int storeTrfVoucher(TrfVoucherObject trf,String date,String datetime) throws RemoteException,SQLException;
	
	public boolean UpdateGenParam(String column) throws RemoteException;
	
	public boolean UpdateGenParam(String column,int i)throws RemoteException;
	
	public String getName(String ac_type,int acno) throws CustomerNotFoundException,RemoteException;
	
	public CashObject[] getScrollNumbers(String acctype,int type,String date) throws RecordsNotFoundException,RemoteException;
	
	public String getFutureMonthDate(String cur_date,int month) throws RemoteException;
	
	public String getFutureDayDate(String cur_date,int days) throws RemoteException; 
	 
	public int getDaysFromTwoDate(String first_date,String second_date) throws RemoteException;
	
	public ClearingObject[] getControlNumbers(String acctype,int type) throws RecordsNotFoundException,RemoteException;
	
	public AccountObject[] getNotverifiedNumbers(String acctype,int type) throws RecordsNotFoundException,RemoteException;
	
	public int getModulesColumn(String column_name,String acc_type) throws SQLException,RemoteException; 
	
	public int getNoOfMonths(String date_first,String date_second) throws RemoteException;
	
	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no)throws RemoteException;
	
	public int DeleteTemplate(String ac_type,int int_template_no)throws RemoteException;
	
	public String[] getTemplate(int stage,String acty)throws RemoteException,RecordsNotFoundException;
	
	public Object[][] getHelpDetails(String tablename,String colnames[],String condition)throws RemoteException;
	
	public String getAccountHolderName(String acc_type,int acc_no) throws RemoteException,SQLException;
	
	public int getNo0fAccountTransactions(String actype,int acno) throws RemoteException,AccountNotFoundException,SQLException;
	
	public int storeNo0fAccountTransactions(String actype,int acno,int type) throws RemoteException,AccountNotFoundException,SQLException;
	
	//ship.....06/09/2006
	public int checkDailyStatus(String date,int type) throws RemoteException;
	/////////////
	
	//swapna......06/09/2006
	public boolean updatePostInd(String br_location,String date) throws RemoteException;
	
	public ModuleAdminObject getGlkeyParamForAcctype(String ac_type,String date)throws RemoteException;
	//////////
	
	public String[] getBankAddress(int br_code) throws RemoteException;//Karthi-->15/09/2006
	
	public int moduleAdmin(ModuleAdminObject mod_admin,String date)throws RemoteException;
	
	public int moduleAdminUpdate(ModuleAdminObject mod_admin,String date)throws RemoteException;
	
	public String getCustomerName(String custid)throws RemoteException;
	
	public int getPassword(String uid,String tml,String pwd)throws RemoteException;
	
	public String getSelfAccountNoForCid(int cid,String ac_type)throws RemoteException;
	
	public int storeGLTransaction(GLTransObject trnobj) throws SQLException,RemoteException;
	
	public String[] getRelations() throws SQLException,RemoteException;
	
	public String[][] getDirectorDetails(String date)throws SQLException,RemoteException;
	
	public String getDirectorName(int director_code,String date)throws SQLException,RemoteException;
	
	public String[] getDirectorRelations()throws SQLException,RemoteException;
	//code added by Amzad
	public int storeUserActivity(UserActivityMasterObject user_activ)throws RemoteException;
}
