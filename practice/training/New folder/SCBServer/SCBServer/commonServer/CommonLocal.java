
package commonServer;

import java.sql.SQLException;
import javax.ejb.EJBLocalObject;

import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.SignatureNotInsertedException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.AddressTypesObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;

public interface CommonLocal extends EJBLocalObject
{
	
	public String getJointSBAccountName(String agt_acctype,int agt_accno,String pers_acc_type,int pers_acc_no,String jtacctype,int jtaccno,int id) throws SQLException;
	
	//ship.....09/06/2006
	//public boolean check(String uid,String pwd,String tml);
	public String checkLogin(String uid,String pwd,String tml,int flag,String date,String time);
	
	public boolean checkForHoliday(String date);
	/////////////
	
	public boolean checkQuarter() ;
	
	public Object[] checkScrollAttached(int scno,String date,String actype,String acno,int type) throws ScrollNotFoundException;
	
	public AccountObject getAccount(String trn_mode,String acctype,int accno,String date);
	
	public AccountObject[] getAccounts(String acctype,int accno,String date);
	
	public AccSubCategoryObject[] getAccSubCategories(int no) ;
	
	public Address getAddress(int cid,int addr_type);
	
	public AddressTypesObject[] getAddressTypes() ;
	
	public BranchObject[] getBranchDetails(int bcode);
	
	public String[] getColumns(String tabstr) ;
	
	public String getDateTime();
	
	public Object getGenParam(String column);
	
	public String getHeading();
	
	public ModuleObject[] getMainModules(int a,String str) ;
	
	public NomineeObject[] getNominee(int reg_no);
	
	public int[] getPageDetails() ;
	
	public Object[][] getRows(String str,String column,int no) ;
	
	public Object[][] getRows(String str,String column1,String column2,int no,String acc_type,int type) throws RecordsNotFoundException;
	
	public SignatureInstructionObject[] getSignatureDetails(int accno,String type) ;
	
	/*public String getSysDate() ;
	*/
	public String getSysDateTime() ;
	/*
	public String getSysTime() ;*/
	
	public boolean isHoliday(String date);
	
	public String nextWorkingDay(String date);
	
	public String prevWorkingDay(String date);
	
	public int storeAccountTransaction(AccountTransObject am) throws SQLException;

	public int storeGLTransaction(GLTransObject[] trnobj) throws SQLException;
	
	public int storeGLTransaction(GLTransObject trnobj) throws SQLException;
	
	public int storeNominee(NomineeObject nom[],String actype,int acno,String date) throws NomineeNotCreatedException;
	
	public int storePayOrder(PayOrderObject po)throws SQLException;
	
	public boolean storeSignatureDetails(SignatureInstructionObject a[],int acno) throws SignatureNotInsertedException;
	
	public int storeTrfVoucher(TrfVoucherObject trf,String date,String datetime) throws SQLException;
	
	public boolean UpdateGenParam(String column) ;
	
	public boolean UpdateGenParam(String column,int i);
	public String getName(String ac_type,int acno) throws CustomerNotFoundException;
	
	public String getFutureMonthDate(String cur_date,int month) ;
	
	public String getFutureDayDate(String cur_date,int days) ;  
	 
	public int getDaysFromTwoDate(String first_date,String second_date) ;
	
	public int getNoOfMonths(String date_first,String date_second) ;

	public int getModulesColumn(String column_name,String acc_type) throws SQLException;

	public int getGenParamUpdated(String column_name) throws SQLException;
	
	public String getAccountHolderName(String acctype,int accno) throws SQLException;
	 
	public int getNo0fAccountTransactions(String actype,int acno) throws AccountNotFoundException,SQLException;
		
	public int storeNo0fAccountTransactions(String actype,int acno,int type) throws AccountNotFoundException,SQLException;
	
	//ship......06/09/2006
	public int checkDailyStatus(String date,int type);
	////////
	
	//swapna.....06/09/2006
	public boolean updatePostInd(String br_location,String date);
	//////////
	 
	public String[] getBankAddress(int br_code);//Karthi-->15/09/2006
}
