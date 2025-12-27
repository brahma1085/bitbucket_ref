/*
 * Created on 22 May, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package loanServer;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Vector;

import javax.ejb.EJBLocalObject;

import masterObject.loans.DCBObject;
import masterObject.loans.DocumentsObject;
import masterObject.loans.LedgerObject;
import masterObject.loans.LoanIntRateObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanStageObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.PSWSObject;
import masterObject.loansOnDeposit.LoanPurposeObject;

import commonServer.GLTransObject;
import exceptions.CustomerNotFoundException;
import exceptions.GLCodeNotDefinedException;
import exceptions.GLKeyCodeNotFound;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotDeletedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.RecordsNotVerifiedException;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface LoanLocal extends EJBLocalObject{
	//Select the interest rate between the given date for the given account type,category,period,amt
	public double  getIntRate(String ln_type,String fdate,int category,int period,double amt,int ac_no);

	//Select the Penal interest rate between the given date for the given account type
	public abstract LoanIntRateObject[] getPenalIntRate(String ln_type,String fdate, String tdate, int category) ;

	public abstract Vector getIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no);

	public abstract double getPenalIntRate(String ln_type, String fdate,int category) ;

	/*
	 * Method returns Loantransactionobject for given acctype,acno which
	 * disbursement details are not yet verified 
	 */public abstract LoanTransactionObject getUnVerifiedDisbursement(String ln_type, int acno) ;

	/*
	 * Get loan details for given account type and account number
	 */public abstract LoanReportObject getLoanDetails(String ln_type, int acno,String date);

	/*
	 * Select transaction records for given account type and account number
	 * String str will be either "Schedule"/"Recovery"
	 * "Schedule"--> will return only "S" records
	 * "Recovery"-->will return only "R" records
	 */public abstract LoanTransactionObject[] getLoanTransaction(String ln_type, int acno, String str) ;

	//Select the minimum share percentage should be maintained for this loan account type
	public abstract double getSharePercentage(String ln_type);

	//Verify the Loan Disbursement  for given transaction number,account type,account number
	public int verifyLoanDisbursement(int trn,String ac_type,int ac_no,String uid,String tml,String sbtype,int sbno,double disbursement,LoanMasterObject loan_object,LoanTransactionObject loan_trn_object,String date_time);

	//Store loan master
	public abstract int storeLoanMaster(LoanMasterObject ln, int type,String datetime);

	//Get loan master details for given account type and account number
	public abstract LoanMasterObject getLoanMaster(int ln_acno, String ln_acty);

	//Sanction the loan for given account type,account for this amount and this installment and set priorty, weaker section code
	//public abstract int sanctionLoan(int actype, int acno, double amt,int install, boolean priority, boolean weak, double rate); 
	
	public abstract int sanctionLoan(int actype, int acno, double amt,int install, int priority, boolean weak, double rate,int int_type, int int_rate_type, double install_amt, double holiday_months, String trn_date);// code changed by Murugesh on 23/12/2005

	public abstract int verifyLoanMaster(int actype, int acno, String uid,String tml) ;

	//Verify the loan sanction make sanc_ver as 'Y'
	public abstract int verifySanction(int actype, int acno);

	/*Disburse the loan for given account type and account number
	 * disbursement will contain the amount disbursed 
	 * object[][] will contain the schedule records
	 * amtsanct will contain the amount sanctioned
	 */public int disburseLoan(int actype,int acno,double disbursement,Object data[][],double amtsanct,String uid,String tml,String paymode,String narr,String trn_date,String date_time) ;

	/*Reschedule the loan	for the given account type and account number
	 * Vector will contain new schedule records
	 */public int reScheduleLoan(String actype,int acno,int install,double amt,Vector vec,String effdate,String uid, String tml,String date_time);

	//Automatic loan recovery	
	 public int loanRecovery(String uid,String utml,String date,String date_time);

	//Loan status 
	//public abstract LoanTransactionObject getLoanStatus(String actype, int no);

	//Get all jewel details between given dates
	public abstract Vector getJewelReport(String from, String to);

	//public abstract int storeGLTransaction(GLTransObject trnobj);
	
	//Athul's
	public int storeLoanAmount(LoanTransactionObject lnobj,int type,String trn_date) throws RecordsNotInsertedException;

	public LoanMasterObject getQueryOnLoanStatus(String ln_acty,int ln_acno,String trn_date) ;

	public int verifyLoanRecovery(LoanTransactionObject lnobj) throws RecordsNotVerifiedException;

	//public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no);

	public int deleteLoanTransactionRecovery(int trf_voucher_no,String actype,int acno) throws RecordsNotDeletedException;

	//public abstract void startNPAProcessing(String pro_date, int pr_int_od,int days,int loan_type, int loan_start_no, int loan_end_no,String uid,String utml) throws RecordsNotFoundException;

	public abstract String[][] getNPACode() ;

	public abstract Vector getNPAProcessedDate(int days) ;

	public abstract int deleteNPAProcessedDate(String pro_date,int days);

	public abstract LoanReportObject[][] getNPASummary(String pro_date,int days) throws RecordsNotFoundException;

	public LoanReportObject[][] getNPAScheduleStatus(String pro_date,int asset_type,int asset_days,int int_npatype,String string_loan_type) throws RecordsNotFoundException;

	//public abstract LoanTransactionObject getLoanRecoveryStatus(String actype,int no) ;

	//public LoanReportObject[] getLoanOverDueReport(String fromdate,String todate,String trn_date) ;
	
	 public double calculatePenalInt(String actype,String fdate,String tdate,double amt,int category);
	    
	    public double calculateInterest(String actype,String fdate,String tdate,double amt,int category,int period,double disbamt,int ac_no);

	public abstract Connection getConnection();

	public abstract String getSysDate();

	public abstract String getSysTime();

	//Vasu's
	public abstract LoanMasterObject getLoanMaster(String actype, int acno, String date);

	//method to Store Loan Transaction
	public abstract int storeLoanTransaction(LoanTransactionObject lto)
			;

	public abstract LoanTransactionObject getOtherChargeDetails(String actype,
			int acno, int tseq) ;

	public int verifyOtherCharges(String actype, int acno, int ref_no, double trn_amt, String date, String vetml, String veuser, String ve_date) throws GLKeyCodeNotFound ;

	//method to get customer Names and cid's from some part of fname
	public abstract String[] getNames(String fname) ;

	//to get Cid of a given A/C
	public abstract int getCid(String actype, int no)throws CustomerNotFoundException ;

	public abstract Vector queryOnCustomer(int cid) ;

	public abstract LedgerObject[] getLedger(int fromac, int toac,
			int acstatus, String fdate, String tdate) ;

	//public abstract DocumentsObject[] getLoanDocs(String acty, int acno);

	//public abstract int updateDocs(String acty, int acno, DocumentsObject[] dt);

	public abstract boolean checkDCBReport(int month, int year)
			;

	public abstract int dcbProcess(int month, int year, int type, String acty,
			int fromno, int tono, String tml, String user, boolean del)
			;

	public abstract DCBObject[] dcbSchedule(int month, int year, int type,
			String acty, int fromno, int tono) ;

	public abstract DocumentsObject[] getActionDue(String date,
			String lnactype, int stagecode) ;

	public abstract String[] dcbDelete(int type, String m[])
			;

	public abstract DCBObject[] dcbSummary(String month) ;

	public abstract String[] stageType(int stagecode, String lnactype)
			;

	//Kamals
	public abstract int storeSpecialInt(String str[]) ;

	public abstract LoanReportObject[] getLoanDetails(String ln_type, int acno,
			String fdate, String tdate, int type,String query) ;

	public abstract LoanReportObject[] getLoanRemainderSummary(String ac_type,
			int stage_no) ;
	
	 public boolean insertTransferVoucherEntry(LoanTransactionObject loan_trn);
	

	public abstract Object[][] getLoanRemainderReport(String lnacty,
			String ac_no) ;

	public abstract boolean updateLoanRemainderSummary(LoanReportObject ln[])
			;

	public abstract File getFile(String modcode, int st) ;

	public abstract boolean storeFile(int modcode, int stage, File f)
			;

	public abstract String[] getColumns(String lnacty) ;

	public abstract PSWSObject[] getPSWSDetails(String prdate, int pr_code,
			int sel,String query) ;

	public abstract String[] getProcessedDates() ;

	public abstract int processPSWS(String pdate, String utml, String uname,
			int ty) ;

	public abstract PSWSObject getPSDetails() ;

	public abstract LoanStageObject[] getLoanStageDetails(String acty,
			int acno, String fdate, String todate, int ty)
			;

	public abstract int storeLoanRemainderSummary(String pro_date, double famt,
			double tamt, String loan_type, int loan_start_no, int loan_end_no,
			int ty, String utml, String uname) ;

	/*public abstract LoanReportObject[] getAccruedInterest(String todate,String query)
			;*/

	public abstract LoanPurposeObject[] getLoanPurposes()
			;

	public abstract String getSysDateTime();
	
	// Murugesh's
	 public Object recoverLoanAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException;
	 
	 public int postRecoveryDetails(String ac_type,int ac_no,String date,int category,int flag,String user_id,String user_tml);
	 
	 public LoanTransactionObject getPrincipalOutstandings(String ac_type,int ac_no,String date) throws RecordNotUpdatedException;
	 
	 public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no,String date);
    
    }