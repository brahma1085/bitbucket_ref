package termDepositServer;


import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.naming.NamingException;

import masterObject.general.AccountObject;
import masterObject.general.NomineeObject;
import masterObject.general.TrfVoucherObject;
import masterObject.termDeposit.DepositIntRate;
import masterObject.termDeposit.DepositIntRepObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositReportObject;
import masterObject.termDeposit.DepositTransactionObject;
import exceptions.AccountNotFoundException;
import exceptions.ControlNumberAttached;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.ScrollNumberAttached;
import exceptions.Verified;

/**
 * @author user
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TermDepositRemote extends EJBObject
{
    public int storeGenparam() throws RemoteException; 
    
    public int firstMethod() throws RemoteException;
    
    public int secondMethod() throws RemoteException; 
    
    public DepositMasterObject mailSystem(String ac_type,String nxtpaydate)throws RemoteException,RecordsNotFoundException; 
    
    public int storeDepositMaster(DepositMasterObject depmast,int type) throws  DateFormatException, ScrollNumberAttached, ControlNumberAttached, RemoteException, NamingException, CreateException;
    
    public int deleteTermDeposit(DepositMasterObject depositMasterObject,int type) throws RemoteException;
    
    public int deleteTermDeposit(DepositMasterObject depositMasterObject,String date,int type) throws RemoteException,RecordNotInsertedException;
    
    public DepositMasterObject getDepositMaster(String acctype,int fr_acno) throws AccountNotFoundException,RemoteException; 
    
    public DepositMasterObject[] getDepositMaster(String acctype,int type,int facno,int tacno,String fdate,String tdate) throws RemoteException,AccountNotFoundException;
    
    public DepositTransactionObject[] getDepositTransaction(int type,String acctype,int accno,String fr_date,String to_date) throws AccountNotFoundException,RemoteException;	
    
    public int verifyDepositMaster(DepositMasterObject depmast,int type) throws CreateException,Verified,RemoteException;
    
    public DepositIntRepObject[] RDInterestCalc(int type,String deuser,String did,String ddate)throws RemoteException,RecordsNotFoundException;
    
    public DepositIntRepObject[] RDquarterlyInterest(int type,String uid,String utml,String udate,String combo_date) throws RecordsNotFoundException,RemoteException;
    
    public DepositIntRepObject[] ReInvestmentInterestCalc(int type,String uid,String utml,String udate) throws RemoteException,RecordsNotFoundException,DateFormatException;
    
    public DepositIntRepObject[] FDInterestCalc(int type,String uid,String utml,String udate) throws RemoteException,RecordsNotFoundException;
	
    public DepositIntRepObject[] ReInvestmentQuarterlyIntCalc(int type,String uid,String utml,String udate,String combo_date) throws RecordsNotFoundException,RemoteException;
	
    public DepositIntRepObject[] FDQuarterlyIntCalc(int type,String uid,String utml,String udate,String combo_date) throws RecordsNotFoundException,RemoteException;  

	public boolean checkQuarter(String code) throws RemoteException;

	public boolean checkDayCalc(String code) throws RemoteException;
	
	public int deleteRD(int acno,String acty) throws RecordsNotInsertedException,RemoteException;
	
	public int submitRD(DepositMasterObject depositMasterObject) throws RecordsNotInsertedException,RemoteException;
	
	public DepositMasterObject[] getClosureDetails(int acno,String type,boolean flag) throws RecordsNotFoundException,AccountNotFoundException,RemoteException;
	
	public int verifiedRD(DepositMasterObject dm) throws RecordsNotInsertedException,RemoteException;
	
	public double[] getDepositInterestRate(String ac_type,int dp_type,int cat_type,String date ,int days,double amount) throws RemoteException;
	
	public DepositMasterObject[] getReInvestmentClosureDetails(int acno,String type,boolean flag) throws RemoteException,RecordsNotFoundException,AccountNotFoundException;
	 
	public int submitReInvestment(DepositMasterObject depositMasterObject) throws RemoteException,RecordsNotInsertedException;
	
	public int verifiedReInvestment(DepositMasterObject dm) throws RecordsNotInsertedException,RemoteException;
	 
	public double getReInvestmentInterestAmount(String depdate,double amt,double intrate) throws RemoteException;
	
	public DepositMasterObject getFDClosureData(String acctype,int accno,int int_type) throws AccountNotFoundException,RecordsNotFoundException,RemoteException; 
	
	public DepositMasterObject getRenewalInformation(String acctype,int accno,int int_type) throws AccountNotFoundException,RecordsNotFoundException,RemoteException;
	 
	public int closeFDAccount(DepositMasterObject depositMasterObject) throws CreateException,RemoteException,DateFormatException;
	
	public int verifiedFD(DepositMasterObject dm) throws RecordsNotInsertedException,RemoteException;  
	
	//public int deletetdclosure(String ac_type,int ac_no) throws RemoteException;
	
	public int verifyDepositRenewal(DepositMasterObject depmast,int type) throws RecordsNotInsertedException,RemoteException,Verified;
	
	public DepositTransactionObject[] getAutoRenewalList(String ac_type,String date_fr,String date_to,int type,int flag,String query) throws RecordsNotFoundException,RemoteException;
	
	public DepositTransactionObject[] getAutoRenewalInfo() throws CreateException,RecordsNotFoundException,RemoteException; 
	
	public int[] storeAutoRenewalInfo(DepositTransactionObject[] deptrnobj) throws RecordsNotInsertedException,RemoteException;
	
	public DepositIntRate[] getViewDetailes(String deptype,String table_name) throws RecordsNotFoundException,RemoteException;
	
	public int insertRow(DepositIntRate depositintrate,String table_name) throws CreateException,RemoteException,DateFormatException;
	
	public int deleteRow(DepositIntRate depositintrate,String table_name) throws CreateException,RemoteException,DateFormatException;
	
	public int updateRow(DepositIntRate depint,int rowcount,String table_name) throws CreateException,RemoteException,DateFormatException;
	
	public TrfVoucherObject[] getDepositTransferVoucher(String string_acc_type,int int_acc_no,int type) throws CreateException,RecordsNotFoundException,RemoteException;
	
	public int storeDepositTransferVoucher(TrfVoucherObject[] array_trfvoucherobject) throws CreateException,RemoteException;
	
	public int storeDepositTransferVoucher(TrfVoucherObject[] array_trfvoucherobject,String date) throws CreateException,RecordNotInsertedException,RemoteException;
	
	public int SetNomineeDetails(NomineeObject[] nom)throws RemoteException;
	
	public int getNomineePercent(int accno,String ac_type)throws RecordsNotFoundException,RemoteException;
	
	public DepositTransactionObject[] getPrintRecords(String acctype,int accno,int type,String trn_date) throws RecordsNotFoundException,RemoteException;
	
	public void updatePrintSequence(long lst_trn_seq,int lst_pr_seq,String acc_type,int acc_no,int type) throws RemoteException;
	
	public AccountObject[] RetrieveRenewalNotice(String fdate,String tdate,int p1,int p2) throws RecordsNotFoundException,RemoteException,DateFormatException;
	
	public DepositMasterObject[] getFDReceiptPrinting(int startno,int endno,int type,String acc_type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] RetrievePeriod(String date_to,String acc_type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] RetrieveFD(String date_fr,String date_to,int open) throws RecordsNotFoundException,RemoteException;
	
	public DepositReportObject[] RetrieveRenewalNotice(String date1,String date2,String ac_ty,int type,int flag,String query) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getOpenAccounts(String string_account_type, String string_from_date, String string_to_date,int type,String query) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getClosedAccounts(String string_account_type, String string_from_date, String string_to_date,int type,String query) throws RecordsNotFoundException,RemoteException;
	
	//public DepositMasterObject[] RetrievePeriodWiseReport(int srlno,String acc_type,String string_date,int type) throws RecordsNotFoundException,RemoteException;
	
	//public DepositMasterObject[] RetrieveQuantumWiseReport(int srlno,String acc_type,String string_date,int type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getPeriodLimit() throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getPeriodDetails(int srlno, String string_account_type,String string_date,int type) throws RecordsNotFoundException,RemoteException;
	
	public DepositReportObject[] getLimit(String acc_type,int type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getQuantumDetails(int srlno, String string_account_type,String string_date,int type) throws RecordsNotFoundException,RemoteException;
	
	public DepositReportObject[] getQuantumLimit(String acc_type,int type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getDepositMaster(String acctype,int type,int facno,int tacno,String fdate,String tdate,int flag,String query) throws RecordsNotFoundException,RemoteException;
	
	public DepositTransactionObject[] getDepositTransaction(int type,String acctype,int no,String fdate,String tdate,String query,int flag) throws RecordsNotFoundException,RemoteException;
	
	public ArrayList getQuaterlyInterestVector() throws RemoteException;
	
	public boolean updateReceiptPrinting(DepositMasterObject[] array_dep_object ) throws RecordNotUpdatedException,RemoteException;
	
	public boolean updateReceiptNumber(DepositMasterObject[] dm) throws RecordNotUpdatedException,RemoteException;
	
	public DepositMasterObject[] getFDReceiptVerification(int start_no,int end_no,String ac_type) throws RecordsNotFoundException,RemoteException;
	
	public DepositMasterObject[] getFDReceiptUpdationDetails(int start_no,int end_no,String ac_type) throws RecordsNotFoundException,RemoteException;
	
	public int verifyReceipts(DepositMasterObject[] object) throws RemoteException,RecordNotUpdatedException;
	
	//public float InsertTermDepTran(String ac_type,int ac_no,float amt,String date,String de_user,String de_tml,String de_date,String ve_user,String ve_tml,String ve_date);
	
	public boolean checkIntPaid(String ac_type, int ac_no) throws RemoteException; 
	
	public double RDInterestClosure(String actype,int accno,String date,double int_rate,String dep_date) throws SQLException,RemoteException;
	
	public DepositTransactionObject[] getRDReBalance(String ac_ty,int ac_no) throws RemoteException;
	
	//public String getamendmat_date(String amdate,int period_of_days)throws RemoteException,DateFormatException;
	
	public String[] getQtrIntCalDate(String ac_type)throws RemoteException;
	
	public DepositMasterObject[] getAllAccounts(String string_account_type, String string_from_date, String string_to_date,int type) throws RecordsNotFoundException,RemoteException;
	
	public int validatefields(int type,String ac_type) throws SQLException,RemoteException;
	
	public double getNormalclosure(DepositMasterObject depmast_obj,String ac_type,int ac_no,int close_ind) throws SQLException,RemoteException;
	
	public double getWithPenalty(DepositMasterObject dep_mast_obj,String ac_type,int ac_no,String date,int close_ind)throws SQLException,RemoteException;
	
	public double getWithoutPenalty(DepositMasterObject dep_mast_obj,String ac_type,int ac_no,String date,int close_ind) throws SQLException,RemoteException;
	
	public String getBOD(int cid) throws RemoteException,CustomerNotFoundException,SQLException;
	
	public byte[] getCustomerPhoto(int cid)throws RemoteException,SQLException;
	
	public String getCustomerAddress(int cid)throws RemoteException,CustomerNotFoundException,SQLException;
	
	public DepositMasterObject getTranDetails(String acctype,int accno,int int_type) throws AccountNotFoundException,RecordsNotFoundException,RemoteException;
}



