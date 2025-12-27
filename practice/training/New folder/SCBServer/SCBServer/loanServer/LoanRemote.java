package loanServer;
import java.io.File;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.EJBObject;

import exceptions.CustomerNotFoundException;
import exceptions.GLCodeNotDefinedException;
import exceptions.GLKeyCodeNotFound;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotDeletedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.RecordsNotVerifiedException;

import masterObject.frontCounter.AccountTransObject;
import masterObject.general.SignatureInstructionObject;

import masterObject.loans.*;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.pygmyDeposit.SimpleMasterObject;

                 
public interface LoanRemote extends EJBObject 
{

	public Hashtable getOtherChargeDetails(int tseq) throws RemoteException;
	
	public int getUpdateOtherCharges(LoanTransactionObject lto,int refno) throws RemoteException;
	
	public LoanIntRateObject[] getPenalIntRate(String ln_type,String fdate,String tdate,int category) throws RemoteException;

	public double  getIntRate(String ln_type,String fdate,int category,int period,double amt,int ac_no) throws RemoteException;

	public double getPenalIntRate(String ln_type,String fdate,int category) throws RemoteException;

	 public boolean isNominal(String ac_type, int ac_no)  throws RemoteException;
		
	
	public Vector getIntRate(String ln_type,String fdate,String tdate,int category,int period,double amt,int ac_no) throws RemoteException;

	public Object penalIntFix(LoanTransactionObject loan_trn) throws RemoteException;

	public int storeLoanMaster(LoanMasterObject ln,int type,String clientdatetime) throws RemoteException;
	
	//public int storeLoanMaster1(LoanMasterObject ln,int type,String clientdatetime) throws RemoteException;

	 public int deleteLoanSanction(String acctype, int accno) throws RemoteException;
	 
     public int deleteLoanDisbursement(String acctype,int accno) throws RemoteException;
	
	
	public LoanMasterObject getLoanMaster(int ln_acno,String ln_acty) throws RemoteException;
	
	public LoanMasterObject getLoanModuleDesc(String accType) throws RemoteException;

	public int getCheakLoanNum(int acno) throws RemoteException,SQLException;

	public double getSharePercentage(String ln_type) throws RemoteException;

	//public int sanctionLoan(int actype,int acno,double amt,int install,boolean priority,boolean weak,double rate)throws RemoteException; 
	
	//public int sanctionLoan(int actype,int acno,double amt,int install,boolean priority,boolean weak,double rate, int int_type, int int_rate_type, double install_amt, double holiday_months, String trn_date)throws RemoteException; // Code added by Murugesh on 23/12/2005	
	public int sanctionLoan(int actype,int acno,double amt,int install,int priority,boolean weak,double rate,int int_type, int int_rate_type, double install_amt,double holiday_months, String trn_date)throws RemoteException;
	
	public int verifyLoanMaster(int actype,int acno,String uid,String tml)throws RemoteException;

	public int verifySanction(int actype,int acno) throws RemoteException;



	public int disburseLoan(int actype,int acno,double disbursement,Object data[][],double amtsanct,String uid,String tml,String paymode,String narr,String trn_date,String date_time) throws RemoteException;

	public LoanTransactionObject getUnVerifiedDisbursement(String ln_type,int acno) throws RemoteException;

	public int verifyLoanDisbursement(int trn,String ac_type,int ac_no,String uid,String tml,String sbtype,int sbno,double disbursement,LoanMasterObject loan_object,LoanTransactionObject loan_trn_object,String date_time) throws RemoteException;

	public int reScheduleLoan(String actype,int acno,int install,double amt,Vector vec,String effdate,String uid, String tml,String date_time) throws RemoteException;
	
	public int loanRecovery(String uid,String utml,String date,String date_time)throws RemoteException;



	//public LoanTransactionObject getLoanStatus(String type,int no) throws RemoteException;

	public LoanTransactionObject[] getLoanTransaction(String ln_type,int acno,String str) throws RemoteException;

	public LoanReportObject getLoanDetails(String ln_type,int acno,String date) throws RemoteException;
	
	public Vector getJewelReport(String from,String to ) throws RemoteException;
	
	
	
	// Vasu
	public LoanMasterObject getLoanMaster(String actype,int acno, String date) throws RemoteException;

	public int storeLoanTransaction(LoanTransactionObject lto)throws RemoteException;

	public LoanTransactionObject getOtherChargeDetails(String actype,int acno,int tseq)throws RemoteException;

	public int verifyOtherCharges(String actype, int acno, int ref_no, double trn_amt, String date, String vetml, String veuser, String ve_date)  throws  GLKeyCodeNotFound,RemoteException;

	public String[] getNames(String fname)throws RemoteException;

	public int getCid(String actype,int acno)throws RemoteException,CustomerNotFoundException;

	public Vector queryOnCustomer(int cid)throws RemoteException;

	public LedgerObject[] getLedger(int fromac,int toac,int acstatus,String fdate,String tdate)throws RemoteException;

	//public DocumentsObject[] getLoanDocs(String acty,int acno)throws RemoteException;

	//public int updateDocs(String acty,int acno,DocumentsObject[] dt)throws RemoteException;
	
	public boolean checkDCBReport(int month,int year)throws RemoteException;
	
	public int dcbProcess(int month,int year,int type,String acty,int fromno,int tono,String tml,String user,boolean delete)throws RemoteException;
	
	public DCBObject[] dcbSchedule(int month,int year,int type,String acty,int fromno,int tono)throws RemoteException;
	
	public DocumentsObject[] getActionDue(String date,String lnactype,int stagecode)throws RemoteException;
	
	public String[] stageType(int stagecode,String lnactype)throws RemoteException;
	
	public String[] dcbDelete(int type,String month[])throws RemoteException;
	
	public DCBObject[] dcbSummary(String month)throws RemoteException;
	
	public LoanReportObject getLoanOverdueReport1(String ac_type,String stage_type,String report_date,String int_upto_date,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt)throws RemoteException;
	
	
	//Kamal's
	public int storeSpecialInt(String str[]) throws RemoteException;
	
	public int storeLoanRemainderSummary(String pro_date,double famt,double tamt,String loan_type,int loan_start_no,int loan_end_no,int ty,String utml,String uname) throws RemoteException;
	
	// public LoanReportObject[] getAccruedInterest(String todate,String query) throws RemoteException;
	
	public LoanReportObject[] getLoanDetails(String ln_type,int acno,String fdate,String tdate,int type,String query) throws RemoteException;
	
	
	public Object[][] getLoanRemainderReport(String lnacty,String ac_no) throws RemoteException;
	
	public LoanReportObject[] getLoanRemainderSummary(String ac_ty,int stage_no) throws RemoteException;
	
	public boolean updateLoanRemainderSummary(LoanReportObject ln[]) throws RemoteException;
	
	public String[] getColumns(String lnacty) throws RemoteException;
	
	public boolean storeFile(int modcode,int stage,File f) throws RemoteException;
	
	public File getFile(String modcode,int st) throws RemoteException;

	public PSWSObject[] getPSWSDetails(String prdate,int pr_code,int sel,String query) throws RemoteException;
	
	public String[] getProcessedDates() throws RemoteException;
	
	public PSWSObject getPSDetails() throws RemoteException;
	
	public int processPSWS(String pdate,String utml,String uname,int ty) throws RemoteException;
	
	public LoanStageObject[] getLoanStageDetails(String acty,int acno,String fdate,String todate,int ty) throws RemoteException;
	
	
	//Athul's
	
	public LoanReportObject[][] getNPAScheduleStatus(String pro_date,int asset_type,int asset_days,int int_npatype,String string_loan_type) throws RemoteException,RecordsNotFoundException;
	 
	public LoanReportObject[][] getNPASummary(String pro_date,int days) throws RemoteException,RecordsNotFoundException;
	
	public int deleteLoanTransactionRecovery(int trf_voucher_no,String actype,int acno) throws RecordsNotDeletedException,RemoteException;
	
	//public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no) throws RemoteException;
	
	public int verifyLoanRecovery(LoanTransactionObject lnobj) throws RecordsNotVerifiedException,RemoteException;
	
	public int storeLoanAmount(LoanTransactionObject lnobj,int type,String trn_date) throws RemoteException,RecordsNotInsertedException;
	
	//public LoanTransactionObject getLoanRecoveryStatus(String actype,int no) throws RemoteException;
	
	public LoanMasterObject getQueryOnLoanStatus(String ln_acty,int ln_acno,String trn_date) throws RemoteException;

	//public LoanReportObject[] getLoanOverDueReport(String fromdate,String todate,String trn_date)throws RemoteException;
	
	//public void startNPAProcessing(String pro_date,int pr_int_od,int days,int loan_type,int loan_start_no,int loan_end_no,String uid,String utml) throws RemoteException,RecordsNotFoundException;	
	
	public int deleteNPAProcessedDate(String pro_date,int days) throws RemoteException;
	
	public Vector getNPAProcessedDate(int days) throws RemoteException;
	
	public String[][] getNPACode() throws RemoteException;

	public LoanPurposeObject[] getLoanPurposes() throws RemoteException;	
	
	public ArrayList getLastLoanTransactionDate(String ac_type,int ac_no) throws RemoteException;
	
	public ArrayList getSurityCoBorrowerDetails(String ac_type,int ac_no) throws RemoteException;
	
	public ArrayList getLoanNPAStatus(String actype,int acno) throws RemoteException;
	
	public ArrayList getLoanHistory(String actype,int acno) throws RemoteException;
	
	public LoanTransactionObject getQueryLoanStatus(String ln_acty,int ln_acno,String trn_date,String user,String terminal,String datetime) throws RemoteException;

    public String getAppraiserName(int code) throws RemoteException;
    
    public double calculatePenalInt(String actype,String fdate,String tdate,double amt,int category) throws RemoteException;
    
    public double calculateInterest(String actype,String fdate,String tdate,double amt,int category,int period,double disbamt,int ac_no) throws RemoteException;

    // Murugesh's
    public String[] getReleventDetails(String modulecode) throws RemoteException;
    
    public double getDisbursementLeft(String ac_type,int ac_no) throws RemoteException;
    
    public int getPreviousDisbDet(String ac_type,int ac_no,String date) throws RemoteException;
    
    public int getNumPreviousDisb(String ac_type,int ac_no,int flag) throws RemoteException;
    
    public String getLastDisbDate(String ac_type,int ac_no) throws RemoteException;
    
    public int getLastTransactionSeq(String ac_type,int ac_no,boolean flag) throws RemoteException;
    
   // public double getRemainingPrincpleAmount(String ac_type,int ac_no) throws RemoteException;
    
    public Object[][] getPreviousDisbursement(String ac_type,int ac_no,boolean flag) throws RemoteException;
    
    public LoanTransactionObject[][] getOtherChargesDue(String ac_type,int ac_no_from,int ac_no_to,int flag,String date) throws RemoteException;
    
    public LoanTransactionObject[][] getOtherChargesCollected(String ac_type,String from_date,String to_date,int ac_no_from,int ac_no_to,int flag) throws RemoteException;
    
    public double calculatePenalInterestForRecovery(String ac_type,int ac_no,String date,int category) throws RemoteException;
    
    public double calculateInterestForDailyPosting(String ac_type,int ac_no,String date,int category) throws RemoteException;
    
    public LoanTransactionObject calculateInterestForRecovery(String ac_type,int ac_no,String date,double amount,int category) throws RemoteException;
    
    public double getOtherChargesForRecovery(String ac_type,int ac_no,String date) throws RemoteException;
    
    public int postRecoveryDetails(String ac_type,int ac_no,String date,int category,int flag,String user_id,String user_tml) throws RemoteException;
    
    public int doDailyRecoveryPosting(String date,String user_id,String user_tml) throws RemoteException;
    
    public Object recoverLoanAccount(LoanTransactionObject loan_object) throws RecordNotUpdatedException,RemoteException;
    
    public LoanTransactionObject getPrincipalOutstandings(String ac_type,int ac_no,String date) throws RecordNotUpdatedException,RemoteException;
    
    public Object[][] getDocs(String ac_type,int ac_no) throws RemoteException;
    
    public DocumentsObject[] getLoanDocuments(String ac_type,int ac_no) throws RemoteException;
    
    public boolean submitLoanDocuments(String ac_type,int ac_no,DocumentsObject[] loan_docs) throws RemoteException;
    
    public boolean insertTransferVoucherEntry(LoanTransactionObject loan_trn) throws RemoteException;
    
    public double getCurrentDayClosingBalance(String ac_type,int ac_no,String date) throws RemoteException;
    
	public String[][] getLoanAdminTable(String table_name) throws RemoteException;
	 
	public double getModuleTableValue(String data)  throws RemoteException;
	
	public String transferVoucherValidation(String ac_type,int ac_no) throws RemoteException;
	
	public String getIntUpToDateOnCurrentDate(String ac_type,int ac_no,String date) throws RemoteException;
	
	public String[] getPrnDueDateAmountForCurrentDate(String ac_type,int ac_no,String date) throws RemoteException;
	
	public void newstartNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml ) throws RemoteException;
	
	public LoanReportObject[] getNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno) throws RemoteException;
	
	public void setLoanAdminTable(String table_name,String table_data[][],String query)  throws RemoteException;
	
	public String[][] getStageCode()  throws RemoteException;
	
	public Vector getLoanOverdueReport(String ac_type,String stage_type,String report_date,String int_upto_date,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt) throws RemoteException;
	
	public Object reverseRecovery(LoanTransactionObject loan_trn,int process, AccountTransObject acc_trn, int gl_code, String gl_type) throws RecordNotUpdatedException, RemoteException;
	
	public Vector getIntAccuredReport(String date,String ac_type,int from_ac_no,int to_ac_no,double from_int_amt,double to_int_amt) throws RemoteException;
	
	public void loanDefaulterProcessing(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String process_due_towards,String date) throws RemoteException;
	
	public Object remainderTemplates(int process_no,String ac_type,int template_no,int stage_no,String text,String de_user,String de_tml,String de_date) throws RecordNotUpdatedException,RecordsNotDeletedException,RemoteException;
	
	public Vector getLoanDefaulters(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String default_towards,int stage_no,String date) throws RemoteException;
	
	public int setLoanHistory(Vector history_values,String user,String tml,String date_time) throws RemoteException,RecordsNotInsertedException;
	
	 public int getPriority(String tml) throws RemoteException; 
	 
	 public Object[][] getLoanAndShareDetails(String sh_type,int sh_no,String date) throws RemoteException;
	 
	 public LoanTransactionObject getReverseLoanDetails(int voucher_number) throws RemoteException;
	 
	 public int deleteReverseLoanDetails(int voucher_number,int ind) throws RemoteException;
	 
	 public TreeMap pendingTrayList(String tray_no, String date)  throws RemoteException;
	 
	 
	 public NPAObject[] getNPAAdminValues(String ac_type, int table_type) throws RemoteException;
	 
	 public boolean setNPAAdminValues(NPAObject[] npa_values, int table_type) throws RemoteException;
	 //public NPAObject[] getNPAAdminValues(String ac_type, int table_type) throws RemoteException;
	 
	 public LoanTransactionObject deleteOtherCharges(int tseq,String actype,int acno,int ind) throws RemoteException;
	 
	 public PriorityMasterObject[] getPriorityDesc() throws RemoteException;
	 
	 public LoanMasterObject getLoanNoDetails(int accNo) throws RemoteException;
	 
	 //public int getLoanerNameinRemote(int accNum) throws RemoteException;
	 
	 public SimpleMasterObject getAccDetails(String accno) throws CustomerNotFoundException,RemoteException;
	 
	 public String[][] getDirectorDetails(String date)throws SQLException,RemoteException;
	 
	 public String[] getDirectorRelations()throws SQLException,RemoteException;
	 
	 public SignatureInstructionObject[] getSignatureDetails(int accno,String type) throws RemoteException;
	 
	 public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no,String cliendate) throws RemoteException;
	 
	 public SurityCoBorrowerObject[] getSurityCoBorrowerDetailsTwo(String ac_type,int ac_no) throws RemoteException;
	 
	 public String[] getQuaterlydates() throws RemoteException;
	 
	 public LoanTransactionObject[] calc_int(String today_date)throws RemoteException;
	 
	 public LoanTransactionObject[] getQuaterlyIntDetails(String date)throws RemoteException;
	 
	 public LoanTransactionObject getLoanTran(String acctype,int accno)throws RemoteException;
	 
	 public int updateLoanMaster(LoanMasterObject lnobj,String actype,int acno,String datetime) throws RemoteException;
}   

