package backOfficeServer;

import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.VoucherdataObjectNotFoundException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.GLMasterObject;
import masterObject.general.NomineeObject;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.ejb.EJBObject;

import masterObject.parameters.BranchesObject;

import masterObject.loans.LoanTransactionObject;

import masterObject.termDeposit.DepositTransactionObject;
import masterObject.backOffice.ChequeBookObject;
import masterObject.backOffice.ChequeNoObject;
import masterObject.backOffice.ClosingBalObject;
import masterObject.backOffice.OdccObject;
import masterObject.backOffice.OpenedClosedInoperatedAccountObject;
import masterObject.backOffice.PygmyObject;
import masterObject.backOffice.SIEntryObject;
import masterObject.backOffice.AdminObject;
import masterObject.backOffice.SIDoneObject;
import masterObject.backOffice.ShareObject;
import masterObject.backOffice.UserObject;
import masterObject.backOffice.VoucherDataObject;


public interface BackOfficeRemote extends EJBObject
{
	public int getCategoryCode(String cat)throws RemoteException,SQLException;
	
	public Object[][] getcategoryDetails() throws RemoteException,SQLException;

	public void insertdata(int pri,String fr_acc,String to_acc,String tml,String user)throws RemoteException,SQLException;
	
	public AdminObject[] retrieveData()throws RemoteException,SQLException,RecordsNotFoundException;
	
	public int storeInfo(SIEntryObject obj1)throws RemoteException,SQLException;
	
	public SIEntryObject[] getInfo(String acc_ty,int acc_no,String string_qry)throws RemoteException,SQLException;
	
	public SIEntryObject getInfoThruSiNo(int si_no,int delete_verify_flag)throws RemoteException,SQLException;
	
	public int getSINo()throws RemoteException,SQLException;
	public int[] getGlCodes(String date) throws RemoteException,SQLException;
	
	public int update(SIEntryObject obj)throws RemoteException,SQLException;
	
	public SIEntryObject[] getInstInfo(int choice,String from_date,String to_date,String string_qry)throws RemoteException,SQLException;
	
	public SIEntryObject[] getInstInfoForDue(String from_date,String to_date,String string_qry)throws RemoteException,SQLException;
	
	public SIDoneObject[] getInstInfoForDone( String from_date,String to_date,String string_qry)throws RemoteException,SQLException;
	
	//public void transferToLoan(String AccType,int AccNo, double amtpaid,int type,int si_no,String fr_type,String fr_ac_no,String user,String terminal)throws RemoteException,SQLException;
	
	public String[][] getStdInstRecords(int array_int_si_no[])throws RemoteException;
	
	public String[] stdExec(int n,String usr,String tml,String date,String datetime)throws RemoteException,SQLException;
	
	public int getNoOfActiveInst()throws RemoteException,SQLException;
	
	public int[] getInstnsForExec(String date)throws RemoteException,SQLException;
	
	public void verify(int si_no,String tml,String user,String date)throws RemoteException,SQLException;
	
	public void delete(int si_no,String tml,String uid,String userdate,int verify_flag)throws RemoteException,SQLException;	
	
	public String[] getAcType() throws RemoteException,SQLException;
	
	public int insertDetails(AdminObject obj) throws RemoteException,SQLException;
	
	public AdminObject[] getDetails() throws RemoteException,SQLException;
	
	public int validate(int p_no,String fromtype,String totype) throws RemoteException,SQLException;
	
	public int delete(int[] xx, String[] yy, String[] zz) throws RemoteException,SQLException;
	
	public void update(AdminObject aobj1,int prno,String a,String b) throws RemoteException,SQLException;
	
	public int getMaxRowNo() throws RemoteException,SQLException;
	
	public void setNull(int prno_table, String a_table, String b_table) throws RemoteException,SQLException;

	public VoucherDataObject[] getFaDataDetails(int vch_no) throws RemoteException,SQLException;
	
	public VoucherDataObject[] getFaData(String fdate,String todate,String vchtyp,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public  GLMasterObject getGLMasterDetails(int glcd,String date) throws RemoteException,SQLException;
	
	public int deleteVoucherData(int vch_no,String[] gltype,int[] glcode,double[] amount) throws RemoteException,SQLException;
	
	public UserObject getUserObject(String user) throws RemoteException,SQLException;
	
	public AccountTransObject[] getSBCATranSummary(String modcode,String fromdate,String todate,int accno,String string_query) throws RemoteException,RecordsNotFoundException;

	//	SBCA Ledger Printing
	
	public AccountTransObject[] getAccountTransaction(int accno,String acctype) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public AccountObject[] getAccount(String acctype,int fromacno,int toacno,String fromdate,String todate) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public DepositTransactionObject[] getDepositReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public String[] getShareCategory(String shtype) throws RemoteException,SQLException;
	
	public ShareObject[] getShareTranSummary(String modecode,String fdate,String tdate,int cate,int shno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public NomineeObject getNominee(int reg_no) throws RemoteException;
	
	public PygmyObject[] getPygmy(String fromdate,String todate,String modecode,int accno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public LoanTransactionObject[] getLoanTranSummary(String fromdate,String todate,String modcode,int accno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	public masterObject.loansOnDeposit.LoanTransactionObject[] getLoanTransactionReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException;
	
	//Misicellaneous Reports
	
	public void updateVoucherData(String string_vchtype,int int_vchno,String[] array_string_gltype,int[] array_int_glcode,double[] array_double_amount)throws RemoteException,SQLException;
	
	public VoucherDataObject[] getVoucherData(String vch_type,int int_vch_no,String trn_date) throws RemoteException,SQLException;
	
	public void storeVoucherData(VoucherDataObject[] fd) throws RemoteException,SQLException;
	
	public int getVchNo(String date) throws RemoteException,SQLException; 
	

	public String[] getGlTypes(String date) throws RemoteException,SQLException;
	
	public String getGlName(int int_glcode,String date) throws RemoteException,SQLException;		
	
	public boolean verifyFadata(VoucherDataObject voucherdataobject) throws RemoteException,SQLException;
	
	public int[] getChequeNos(int int_acc_no,String int_acc_type) throws RemoteException,SQLException;

	//public ChequeNoObject[] getChequeNo(int flag,int acc_no,String acc_type,String string_query) throws RemoteException,SQLException;
	
	public ChequeNoObject[] getUnpresentedCheque(int acc_no,String acc_type,String string_query) throws RemoteException,SQLException;//Deepa 11/08/06
	
	public String[] getChequeType() throws RemoteException,SQLException;
	
	//public void storeChequeNo(ChequeNoObject chequenoobject) throws RemoteException,SQLException;
	
	public String getName(int int_account_no,String account_type) throws RemoteException,SQLException;

	public ChequeBookObject[] getChequeBook(int flag,String query,String fdate,String ldate) throws RemoteException,SQLException;
	
	public OpenedClosedInoperatedAccountObject[] getOpenedClosedInoperatedAccounts(int flag,String string_account_type,String string_from_date,String string_to_date,String string_account_list,String query) throws RemoteException,SQLException;
	
	public OdccObject[] getOdccMaster(int flag,String date,int int_account_type,String query) throws RemoteException,SQLException;
	
	public double getAccountBalance(int int_account_no) throws RemoteException,SQLException;
	
	public double getCreditLimit(String string_account_type,int int_account_no) throws RemoteException,SQLException;

	//		Closing Balance with R&P
	
//public ClosingBalObject[] getReportSB(String string_modcode,String string_fromdate,String string_todate) throws RemoteException,SQLException;//Deepa 19/04/06
	
	public ClosingBalObject[] getReportSB(String string_modcode,String string_fromdate,String string_todate,int ac_catgry) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportSBNew(String string_modcode,String string_fromdate,String string_todate,int ac_catgry) throws RemoteException,SQLException;
	public ClosingBalObject[] getReportSBNew(String string_modcode,String string_fromdate, String string_todate, int ac_catgry) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate) throws RemoteException,SQLException;//karthi==>06/04/2006
	
	public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate,String shr_cat) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportPD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportReD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportTD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportRD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportLn(String string_modcode,String string_fromdate, String string_todate) throws RemoteException;
	
	public ClosingBalObject[] getReportLnD(String string_modcode,String string_fromdate,String string_todate) throws RemoteException;

	
	/*//public ClosingBalObject[] getReportSB(String string_modcode,String string_fromdate,String string_todate) throws RemoteException,SQLException;//Deepa 19/04/06
	
	public ClosingBalObject[] getReportSB(String string_modcode,String string_fromdate,String string_todate,int ac_catgry) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportSBNew(String string_modcode,String string_fromdate,String string_todate,int ac_catgry) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate) throws RemoteException,SQLException;//karthi==>06/04/2006
	
	public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate,String shr_cat) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportPD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportReD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportTD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	//public ClosingBalObject[] getReportRD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportLn(String string_modcode,String string_fromdate, String string_todate) throws RemoteException;
	
	public ClosingBalObject[] getReportLnD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException;
	
*/	//	Closing Balance Summary

	public ClosingBalObject[] getReportSBSum(String string_modcode,String string_date) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportSHSum(String string_modcode,String string_date) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportPDSum(String string_modcode,String string_date) throws RemoteException,SQLException;  
	
	//public ClosingBalObject[] getReportReDSum(String string_modcode,String string_date) throws RemoteException,SQLException;  
	
	public ClosingBalObject[] getReportTDSum(String string_modcode,String string_date) throws RemoteException,SQLException;  
	
	//public ClosingBalObject[] getReportRDSum(String string_modcode,String string_date) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getReportLnSum(String string_modcode,String string_date) throws RemoteException,SQLException;  
	
	public ClosingBalObject[] getReportLnDSum(String string_modcode,String string_date) throws RemoteException,SQLException;

	//	GL Schedule Report
	
	public ClosingBalObject[] getReqGLReport(String string_glcode,String string_fromdate,String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getGLReport(String string_fromdate,String string_todate) throws RemoteException,SQLException;
	
	public ClosingBalObject[] getGLInfo(String date) throws RemoteException,SQLException;

	// General Activities

	public ClosingBalObject[] getInOperativeAccounts(String string_modcode,String string_date,int int_from_ac_no,int int_to_ac_no) throws RemoteException,SQLException;
	
	public int makeInOperativeAccounts(String string_modcode,ClosingBalObject[] array_obj_ac_no) throws RemoteException,SQLException;

	///////////Transfer Voucher//////////////////
    
	public int[] getAccountGlcodes(String ac_type,String gl_type,int ac_no,String date) throws RemoteException;
    
	//when account type and gl type is passed the corresponding acoount's gl code can be obtained
    
	public String[] getAccountTransferTypes(String ac_type,int glcode) throws RemoteException;
    
	//when ac type and the gl code is been passed the corresponding transfer type of 'T' type is obtained,,
    
	public String getCDIndicator(String ac_type,int glcode,String string_trn_type) throws RemoteException;
    
	//here cd indicator can be obtained by passing the ac type, gl code and transfer type
    
	public int storeTransferVoucherData(VoucherDataObject[] array_VoucherDataObject) throws RemoteException;
    
	//the entries made in the form of transfer voucher gets stored in the 
    
	//VoucherData table by use of this method 
    
	public int updateTransferVoucher(VoucherDataObject[] array_VoucherDataObject) throws RemoteException;
	
	//the entries in the transfer voucher form can be updated as per needs and the 
    
	//updation operation is performed using this method 
    
	public int deleteTransferVoucher(VoucherDataObject[] vobj) throws RemoteException,SQLException;
	
	//any unwanted transfer voucher can be deleted permanently from the database by making use of this method
    
	public VoucherDataObject getFaDataTransferDetails(int vch_no) throws RemoteException,SQLException;
    
	public boolean verifyTransferFadata(VoucherDataObject co1) throws RemoteException,SQLException;
    
	public int[] getGLCode(String gl_type,String date) throws RemoteException,SQLException;
    
	public String[] getCDIndicator() throws RemoteException,SQLException;
    
	public ClosingBalObject[] getReportPDAgentwise(String string_modcode,String string_fromdate, String string_todate,String agentno) throws RemoteException,SQLException;
    
	//neetha for transaction summary of SB CA... on 01/jul/06
    
	
	public AccountTransObject[] getSBCAReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,RecordsNotFoundException;
    
	public SIEntryObject[] displayUnVerifiedSINo(int type) throws RemoteException;//Karthi-->10/08/2006
	public VoucherDataObject[] VoucherVerfifyTransfer(int type,String date) throws RemoteException,SQLException,VoucherdataObjectNotFoundException;
	public VoucherDataObject[] VoucherVerfifyPayment(int type,String Date) throws RemoteException,SQLException,VoucherdataObjectNotFoundException;
	
	 public Hashtable getGLCodes(String date) throws RemoteException;
	
	//public Hashtable getGLCodesDesc(String date)  throws RemoteException;
	
	//public Hashtable getTransactionTypes() throws RemoteException;
	public int getVchNoPayment(String date) throws RemoteException,SQLException;
	public int deleteVoucherDataPayment(int vch_no)throws RemoteException,SQLException;
	public int deleteVoucherDataVoucher(int vchno) throws RemoteException,SQLException;
	
	public int deleteVoucherDataTable(int int_vchno,String string_vch_type,String date) throws SQLException,RemoteException;
	/////////
	public void updateVoucherDataPayment(int vch_no) throws RemoteException,SQLException;
	public Hashtable getTransactionTypes()throws RemoteException;
	public Hashtable getGLCodesDesc(String date) throws RemoteException;
	public int deleteTransferDataVoucher(int int_vchno,String vch_type,String date) throws RemoteException,SQLException;
	public int[] glcodes(String date)throws RemoteException,SQLException;
	public VoucherDataObject[] VoucherData(String vch_type,int vch_no)throws RemoteException,SQLException;
	public int VchNo(String date) throws RemoteException,SQLException;
	public boolean verifyFadataVoucher(VoucherDataObject co1) throws RemoteException,SQLException;
	public String getAccNo(String acc_type) throws SQLException,RemoteException;
	public String[] getAccountGlcodes1(String ac_type,String gl_type,int ac_no,String date)throws RemoteException,SQLException;
	public String[] getCashGLCode() throws RemoteException;
	public String[][] getGlCodesNames() throws SQLException,RemoteException;
	public String Max_AccountNo(String ac_type)throws SQLException,RemoteException;
}



