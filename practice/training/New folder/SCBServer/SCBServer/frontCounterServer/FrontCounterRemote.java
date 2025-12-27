/*
 * Created on Mar 8, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package frontCounterServer;
import java.rmi.RemoteException;
import java.sql.SQLException;

import masterObject.termDeposit.DepositMasterObject;
import exceptions.AccountNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordsNotFoundException;
import masterObject.customer.CustomerMasterObject;
import exceptions.CustomerNotFoundException;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.AdminObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.frontCounter.StockDetailsObject;
import exceptions.GLCodeNotDefinedException;
import masterObject.frontCounter.IntPayObject;
import masterObject.frontCounter.ODCCMasterObject;
import masterObject.frontCounter.PayOrderObject;
import exceptions.QtrDefinitionNotDefinedException;
import masterObject.general.AccCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.GLMasterObject;

import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;

import masterObject.loans.LoanReportObject;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FrontCounterRemote extends javax.ejb.EJBObject
{	//public String getDepositAc(int no)throws RemoteException;
	public String verifyChequeBookNo(String actype,int bookno)throws RemoteException;
	public AccountMasterObject[] getODDeposit(int shno)throws RemoteException;
	/*public GLMasterObject[] getGLMasterDetails() throws RemoteException;*/
	
	public GLMasterObject[] getGLMasterDetails(String date) throws RemoteException;
	
	public int storePOGL(PayOrderObject po)throws RemoteException;

	public int storeAccountMaster(AccountMasterObject am,int updated) throws RemoteException,CreateException,DateFormatException;

	public Vector CheckChqNo(int chq_no,int ac_no,String ac_type,String date) throws RemoteException;

	public int storeCashWithdraw(ChequeObject ch,int type) throws RemoteException;

	public ChequeObject getCashWithdraw(int tokenno,int type,String date) throws RemoteException;
	
	public int updateAccountMaster(AccountMasterObject am) throws RemoteException;
	
	public int updateJointHolder(AccountMasterObject am) throws RemoteException;

	public int verifySB(AccountTransObject at)throws RemoteException,CreateException;

	public int[] getCid(int accno,String type) throws RemoteException;

	public AccountMasterObject getAccountMaster(int accno,String type) throws RemoteException,AccountNotFoundException,CreateException;

	public ChequeObject[] showChequeNos(String acctype,int accno)throws RemoteException;
	
	public boolean checkToken(int tokenno,String date,int type) throws RemoteException;

	public AccountObject[] getChequeDet(int chqno) throws RemoteException;

	public Vector getChequeNoDet(String acctype,int accno,int chqno) throws RemoteException;
	
	public Vector getChequeNoDet(String acctype,int accno) throws RemoteException;

	public int updateChequeDet(ChequeObject ch) throws RemoteException;

	public int storePayOrder(PayOrderObject po,int type) throws RemoteException;

	public PayOrderObject getPayOrder(int srno) throws RemoteException;

	public AccCategoryObject[] getCustomerPayTypes() throws RemoteException;

	//public double getCommission(String actype,int custtype,double amt) throws RemoteException;
	
	public double getCommission(String actype,int custtype,double amt,String date) throws RemoteException;

	public int  verifyPOEntry(PayOrderObject po) throws RemoteException;

	public int verifyCheque(int acno,String type,int bno,int from,int to,String vid,String tml,String vdate)throws RemoteException;


	public boolean accountClose(String actype,int acno,String date) throws RemoteException;
	
	public int getStdInstInfo(String acc_ty, int acc_no) throws RemoteException;
	
	public PayOrderObject getPayOrderInstrn(int chqno) throws RemoteException;

	public int storePOLink(PayOrderObject[] po,int updated,int delete) throws RemoteException;

	public PayOrderObject[] getPODetails(int po_no) throws RemoteException;

	public int verifyPayOrder(PayOrderObject po) throws RemoteException;




	public PayOrderObject[] getPOMake(int pono) throws RemoteException;

	public int  getLastChequeNo(int j,String acctype,int accno) throws RemoteException;

	public int storeChqDetails(ChequeObject ch,int type)throws RemoteException;

	public ChequeObject getChqDetails(String actype,int acno)throws RemoteException;



	public int setPayOrderInstrn(int type,String no,int chqno,String date,String tml,String uid) throws RemoteException;

	public CustomerMasterObject[] retrieveinfo(String cum_name,String cum_acctype) throws RemoteException;

	public AccountTransObject[] retrievePassBook(int type,String actype,int acno,String from,String to) throws RemoteException;
	
	public AccountTransObject[] retrieveParseBook(int type,String actype,int acno,String from,String to)throws RemoteException;

	public PayOrderObject[] RetrievePOMadeInfo(String date,int no,String query) throws RemoteException;

	public PayOrderObject[] RetrieveCashUncash(String fromdate,String todate,int num,String query)throws RemoteException;

   	public PayOrderObject RetrievePrintInfo(String date,int pono,int flag) throws RemoteException;

   	public PayOrderObject[] RetrievePrintInfo(int flag) throws RemoteException; 

	public int getMaxRows(String modulecode)throws RemoteException;

	public AccountTransObject[] PrintBook(String acty,int acno,String frdate,String todate,int ps_bk_seq,int a) throws RemoteException;
	
	public String verifyChequeNo(String actype,int ch_bk_no,int leaf)throws RemoteException;
	
	public boolean checkPayOrderChequeNo(int chqno) throws RemoteException;
	
	public int verifyChequeWithDrawal(AccountTransObject at)throws RemoteException;
	
	public AccountInfoObject getAccountInfo(String acctype,int accno) throws RemoteException,AccountNotFoundException;
	
	public boolean checkChequeWithdrawal(String actype,int acno,int token_no) throws RemoteException;

	public AccountMasterObject[] getInoperativeRecords(String ac_type,String date) throws RemoteException;
	
	public int storeInoperativeAccounts(AccountMasterObject[] at) throws RemoteException;
	
//-----------Interest

	public String getLastIntDate(int code,int type)throws RemoteException, QtrDefinitionNotDefinedException;
	
	public String savingsInterestCalculation(String type,int acno,int modcode,double minbal,String todate,String Uid,String Utml,String Udate,int no)throws RemoteException,DateFormatException,CreateException;
	
	public IntPayObject[] getIntPay(String query)throws RemoteException;
	
	public boolean InterestPosting(String UTml,String Uid,String Udate,String modname,int modcode,int acno,double amt,Object[][] obj) throws RemoteException,CreateException;
	
//--------------ODCC	
	
	public int storeODCCMaster(ODCCMasterObject ln,int type) throws RemoteException,CreateException,DateFormatException;
	
	public int updateODCCMaster(ODCCMasterObject ln,int type ) throws RemoteException, CreateException, DateFormatException;

	public ODCCMasterObject getODCCMaster(int ln_acno,String ln_acty) throws RemoteException,AccountNotFoundException,CreateException;

	public int verifyODCCMaster(AccountTransObject at)throws RemoteException,GLCodeNotDefinedException,CreateException;
	
	public int verifyODCCSanction(ODCCMasterObject lnobj,int type) throws RemoteException;
	
	public int sanctionODCC(ODCCMasterObject lnobj,boolean priority,boolean weaker,String sysdate,int type)throws RemoteException,DateFormatException;
	
	//public double getODCCIntRate(String ln_type,int category,int period,double amt) throws RemoteException;
	public double getODCCIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) throws RemoteException;
	
	//public double getODCCPenalRate(String ln_type) throws RemoteException;
	
	public String ODCCInterestCalc(String ac_type,int acno,double minimumbal,String Uid,String Utml,String Udate,int calc_ind)throws RemoteException;
	
	//public DepositMasterObject getDepositMaster(String actype,int no) throws RemoteException,SQLException;
	
	public DepositMasterObject getDepositMaster(String actype,int no,String date) throws RemoteException,SQLException;
	
	public int confirmCustomerId(String cid) throws RemoteException, CustomerNotFoundException;
	
	//public double getPygmyCurrentIntRate(String actype,String date,double amt_req) throws SQLException,RemoteException;
	public double getPygmyCurrentIntRate(String actype,int acno,String date,double amt_req) throws SQLException,RemoteException;
	
	public String[] getReleventDetails(String modulecode) throws RemoteException;
//---------Admin
	public int insertIntoTable(String table_name,int no_of_columns,Vector vec) throws RemoteException;
	
	public int deleteFromTable(String table_name,String conditions) throws RemoteException;
	
	//public int updateTable(String table_name,String update,String condition) throws RemoteException;
	public int updateTable(String table_name,String update,String update_condition,String condition) throws RemoteException;
	
	public void removeBean(EJBObject obj) throws RemoteException;
	
	public StockDetailsObject getCashCreditDetails(int ln_acno,String ln_acty) throws AccountNotFoundException,RemoteException;
	
	public boolean storeStockDetails(StockDetailsObject stk) throws RemoteException;
//---------Loans
	
	public Object[][] getLoanDetails(String shtype,int shno) throws RemoteException;
	
	public Vector getIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) throws RemoteException;
	
	public Object[][] getInterestDetails(String ac_type,int ac_no) throws RemoteException;
	
	//public double calculateODInterestRate(String ac_type,int ac_no,String fdate,int category,int period,double amount,int int_calc_type) throws RemoteException;
	public double calculateODInterestRate(String ac_type,int ac_no,String fdate,String tdate,int category,int period,double amount,int int_type) throws RemoteException;
	
	public void ODCCNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml )throws RemoteException,RecordsNotFoundException;
	
	
	public LoanReportObject[] getODCCNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno) throws RemoteException;
	
	public String getLastIntPaidDate(String ac_type,int ac_no)throws RemoteException;
	
	public String[] getNextIntDate(String ac_type,String today_date,String br_location)throws RemoteException ;
	
	public String[][] getUnverifiedAccounts(String ac_type,String date)throws RemoteException ;
	
	public String getInterest(String ac_type,int ac_no,String date)throws RemoteException ;
	
	public String[][] getLoanNotSancationedAccs(String ac_type,boolean flag)throws RemoteException ;
	 
	public AdminObject[] getData(String tablename)throws RecordsNotFoundException,RemoteException;
	
	String CalculateInterestAmountForODCC(String ac_type,int acno,double min_amt,String from_date,String to_date,int calc_ind)throws RemoteException;
	
	public Object[][] ODCCInterestDetails(String query)throws RemoteException;
	
	public int insertRecords(AdminObject obj[],String tablename)throws RemoteException;
	
	public int updateRecords(AdminObject obj[],String tablename)throws RemoteException;
	
	public String checkInspectionDate(String ac_type,int ac_no)throws RemoteException;
	
	//public void Check(ODCCMasterObject od)throws RemoteException;
	
	public double getPercentage(String ac_type,String date)throws RemoteException;
	
	public String[] getSecurityCodes()throws RemoteException;
	//COMIssion
	
	 public PayOrderObject[] getPOCommissionDetails(String po_type,int acc_cat,int sub_cat) throws RemoteException;
	    
	    public boolean storePOCommissionDetails(PayOrderObject[] payorderobject,int type) throws RemoteException;
	    

}
