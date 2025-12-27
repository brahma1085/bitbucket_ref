package loansOnDepositServer;



import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Hashtable;

import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;

import masterObject.loansOnDeposit.LoanMasterObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.loansOnDeposit.LoanTransactionObject;
import masterObject.termDeposit.DepositMasterObject;


public interface LoansOnDepositRemote extends javax.ejb.EJBObject
{
	public LoanReportObject[] getAccruedInterest(String currentdate,String ac_type) throws RemoteException,SQLException;
	public long getInterestDue(String date,String current,String lntype,int lnno,String deptype,int depno) throws RemoteException,SQLException;;
	public LoanReportObject[] getLDMaturityList(String date1, String date2,String actype,int type) throws RemoteException,SQLException;
	public LoanReportObject[] getLDExceedingMaturity(String date1, String date2,String actype,int type) throws RemoteException,SQLException;
	public LoanTransactionObject getLoanTransaction(int trnno) throws RemoteException,SQLException;
	public double getCurrentIntRate(String actype,int acno) throws RemoteException,Exception,SQLException;
	//public LoanReportObject getLoanDetails(String actype,int acno) throws SQLException,RemoteException;
	public int loanRecovery(String lnactype,int lnacno,String actype,int acno,double amt,String uid,String utml)throws RemoteException,SQLException;
	//public int verifyLoanTransaction(int trnno,String vid,String vtml) throws RemoteException,SQLException;
	public Hashtable getLoanTransaction(String actype,int no) throws SQLException,RemoteException;
	public int verifyLoanTransaction(int trnno,String vid,String vtml,String clientdate,String clientdatetime) throws RemoteException,SQLException;
	
	
	public LoanMasterObject  getLoanMaster(String actype,int no) throws RemoteException,SQLException;
	public DepositMasterObject getDepositMaster(String actype,int no) throws RemoteException,SQLException;
	public double getLoanPercentage(String actype) throws RemoteException,SQLException;
	public int storeLoanMaster(LoanMasterObject ln,int type) throws RemoteException,SQLException;
	public int  verifyLoansOnDeposit(LoanMasterObject obj,String clientdate) throws RemoteException,SQLException;
	public LoanPurposeObject[] getLoanPurposes() throws RemoteException,SQLException;
	public LoanReportObject[] getOpenedAccounts(String from,String to,String actype,int type) throws RemoteException,SQLException;
	public LoanReportObject[] getPassBook(String actype,int acno) throws RemoteException,SQLException;
	//public int deleteAccount(int ac_no,String ac_type) throws RemoteException,SQLException;
	public int deleteAccount(int ac_no,String ac_type,int depacno,String depactype) throws RemoteException,SQLException;
	public double getPygmyCurrentIntRate(String actype,String date,double amt_req,int ac_no) throws RemoteException,SQLException;
	public LoanReportObject getIntInfo(String ac_type,int ac_no) throws RemoteException,SQLException;
	
	public LoanReportObject[] getClosedAccounts(String from,String to,String actype,int type) throws RemoteException,SQLException;
	public LoanReportObject getLastPaymentDetails(String ln_ac_type,int ln_ac_no) throws RemoteException,SQLException;
	public DepositMasterObject[] getAllowedDepositAccounts(String ac_type) throws RemoteException,RecordsNotFoundException;
	public LoanMasterObject[] getLoanVerificationAccounts(String ac_type) throws RemoteException,RecordsNotFoundException;
	public LoanMasterObject[] getVerificationScrollNumbers() throws RemoteException;
	public LoanMasterObject[] getRecoveryAccounts(String ac_type) throws RemoteException,RecordsNotFoundException,SQLException;
	public LoanMasterObject additionalLoanDetails(String ac_type,int ac_no) throws RemoteException,SQLException,RecordsNotFoundException;
	public int verifyAdditionalLoan(LoanMasterObject ln_object,String clientdate) throws RecordNotUpdatedException,SQLException,RemoteException;
//	 Code added by sanjeet..
	public double getMaxPayableAmtonCurrentDay(String actype,int acno,String date) throws SQLException,RemoteException;
	public double getCurrentIntPay(String actype,int acno,String date) throws SQLException,RemoteException;
	public Object recoverLDAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException,RemoteException;
	public double getCurrentPrBal(String actype,int acno,String date) throws SQLException,RemoteException;
	public double getOtherAmt(String actype,int acno,String date) throws SQLException,RemoteException;
	public double getPrincipalOutstandings(String ac_type,int ac_no,String date)throws SQLException,RemoteException;
	//code added by puspa
	public int getUpdateData(LoanTransactionObject lnobj,String acc_type,int ac_no,double pramt,double intamt,double otheramt )throws SQLException,RemoteException;
	public DepositMasterObject[] RdBalancecalc(String ac_type,int acno) throws RecordsNotFoundException,RemoteException,SQLException;
	public double getPygmyAmt(String ac_type,int ac_no,String curdate )throws SQLException,RemoteException;
	public int  getUpdateData(LoanTransactionObject lnobj,String trnmode,String date)throws SQLException,RemoteException;
	public double autoLoanRecovery(String ac_type ,int ac_no , double amount , String gltype,int glcode,String date,String uid,String tml,String sysdatetime ,int mult)throws RecordNotInsertedException,RemoteException;
	public LoanReportObject getLoanDetails(String actype,int acno) throws SQLException,RemoteException;
	 public LoanReportObject getLoanDetails(String actype, String date,int acno) throws SQLException,RemoteException;
	
 
}