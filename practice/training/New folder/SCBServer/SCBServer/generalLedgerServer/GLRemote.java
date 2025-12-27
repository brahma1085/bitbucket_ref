package generalLedgerServer;

import java.rmi.RemoteException;
import java.sql.SQLException;

import masterObject.generalLedger.DlyConsObject;
import masterObject.generalLedger.GLObject;
import masterObject.generalLedger.GLReportObject;
import masterObject.generalLedger.MonthlyConsObject;
import javax.ejb.EJBObject;
import masterObject.generalLedger.DayBookObject;
import masterObject.generalLedger.MonthObject;
import masterObject.generalLedger.TransferScroll;
import masterObject.generalLedger.Form1Object;

/*import client.MainScreen;*/

import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotDeletedException;
import exceptions.RecordsNotFoundException;

/**
 * @author Riswan Babu.H
 */
public interface GLRemote extends EJBObject
{
	public GLObject getTableInformation(String table_name) throws SQLException,RemoteException;
	
	public GLObject[] getTable(String table_name,String date,boolean selected) throws RemoteException,SQLException,RecordsNotFoundException ;
	
	public int updateTable(String table_name,GLObject[] gl_object) throws RecordNotUpdatedException,SQLException,RemoteException;
	
	public int insertRecords(String table_name,GLObject[] gl_object) throws SQLException,RemoteException;
	
	public int deleteRecords(String table_name,GLObject gl_object[]) throws RecordsNotDeletedException,SQLException,RemoteException;
    
	public String dailyPosting(String date,String de_user,String de_tml,String datetime,String br_location)throws RemoteException;
   
    public String[][] checkDailyStatus(String date,String lastdate) throws RemoteException;
    
    public String[][] checkDailyStatusForMonth(String date,String lastdate) throws RemoteException;
    
    public String[][] checkMonthlyStatus(String frm_mth,String to_mth)throws RemoteException;
    
    public String[][] checkDlyConStatus(String fromdate,String todate,String br_location) throws RemoteException;
    
    public String[][] checkDlyConStatusForMonth(String fromdate,String todate,String br_location) throws RemoteException;
    
    public String[][] checkMthConStatus(String from_mth,String to_mth,String br_location)throws RemoteException;
    
    public int checkDlyStatusForReport(String fromdate,String todate)throws RemoteException;
    
    public int checkMthStatusForReport(String fromdate,String todate)throws RemoteException;
    
    public int checkDlyConStatusForReport(String fromdate,String todate,String br_location)throws RemoteException;
    
    public int checkMthConStatusForReport(String fromdate,String todate,String br_location)throws RemoteException;
    
	public int DailyConPosting(String frm_dt,String to_dt,String branch,String user,String tml,String datetime) throws RemoteException;
	
	public int doDailyConsolidationForBranch(GLObject globject[],String frm_dt,String to_dt,String br_location,String uname,String utml,String datetime)throws RemoteException;
    
    public int doDayClose(String date,String de_user,String tml)throws RemoteException;
    
    public int doMonthlyPosting(int year,int frm_month,String br_location,String tml,String user,String datetime,String rec_type)throws RemoteException;

    public int closeMonth(int year,int month)throws RemoteException;
    
    public String[][] checkForMonthClose(String yr_mth)throws RemoteException;
    
    public String startingDateForBranch(int br_code)throws RemoteException;
    
    public int doMonthlyConsolidation(String from_yrmth,String to_yrmth,String br_location,String uname,String utml,String datetime,String rec_type)throws RemoteException;
    
    public int doMonthlyConsolidationForBranch(GLObject globject[],String from_yrmth,String to_yrmth,String br_location,String uname,String utml,String datetime,String rec_type)throws RemoteException;
    
    public int getLastYearMonth()throws RemoteException;
    
    public int checkYearPosting(String from_mth,String to_mth,String br_location)throws RemoteException;
    
    public String[][] checkYearStatus(String yr_mth,String br_location)throws RemoteException;
    
    public double getPLProfit(String to_yrmth,String br_location ,boolean branch)throws RemoteException;
    
    public int PLPosting(String from_yrmth,String to_yrmth,String tml,String user,String datetime,String br_location,boolean branch)throws RemoteException;
    
    public int closeYear(String yr_mth,String br_location)throws RemoteException;
    
    public String[][] getNonComputersiedBranchs()throws RemoteException;
    
    public String[][] checkYearStatusBranch(String yr_mth,String br_location)throws RemoteException;
    
    public String getYearMonth() throws RemoteException;
    
    public String getStartingDate()throws RemoteException;
 
    public Object[][] getGLHeads(String cur_date)throws RemoteException;
    
    public Object[][] getDatesForAMonth(String date)throws RemoteException;
    
    public GLObject[] getDetails() throws RemoteException,RecordsNotFoundException ;
    
    public String[][] getCreateFileData()throws RemoteException;
    
    public GLObject[] getVouchingSlip(String slip_date,String gl_type,int gl_no1,int gl_no2,int type) throws RemoteException,RecordsNotFoundException ;
    
    public GLObject[] getReceiptAndPayment(int from_date,int to_date,String srtring_qry) throws RemoteException,RecordsNotFoundException ;
    
    public String[][] getBranchDetails() throws RemoteException;
    
    public String[][] getBranchCode(String br_location) throws RemoteException;
    
    public String[][] getBranchName(int br_code)throws RemoteException;
    
    public GLObject[] getConsolidatedReceiptAndPayment(int from_date,int to_date,String query_str) throws RemoteException,RecordsNotFoundException;
    
    public  DlyConsObject[] getPostedData(String frm_dt,String to_dt,String br_location)throws RemoteException;
    
    public MonthlyConsObject[] getMonthlyConsData(String frm_yrmth,String to_yrmth,String br_location)throws RemoteException;
    
     public String[][] getGLDetails(String date) throws RemoteException;
     
     public double OpeningBalance(String gl_type,int gl_code,String date) throws RemoteException;
     
     public double ClosingBalance(String gl_type,int gl_code,String date) throws RemoteException;
     
     public String[][] getGLDetailsForTwoDates(String date1,String date2) throws RemoteException;
     
     public DayBookObject getDailySummary(String gl_type,int gl_code,String date) throws RemoteException;
     
     public String[][] getGLDetailsForMonth(int year_month) throws RemoteException;
     
     public String[][] getGLDetailsFromMonthToMonth(int from_year_month,int to_year_month) throws RemoteException;
     
     public String[] getCashGLTypeCode() throws RemoteException;
     
     public int checkForHoliday(String date,String todate,String br_location,String user,String tml,String datetime)throws RemoteException;
     
     public double ConsolidatedOpeningBalance(String gl_type,int gl_code,String date) throws RemoteException;
     
     public double ConsolidatedClosingBalance(String gl_type,int gl_code,String date) throws RemoteException;
     
     public double OpeningBalanceForBranch(String gl_type,int gl_code,int branch_code,String date) throws RemoteException;
     
     public double ClosingBalanceForBranch(String gl_type,int gl_code,int branch_code,String date) throws RemoteException;
 
     public DayBookObject getDailySummaryForBranch(String gl_type,int gl_code,int branch_code,String date) throws RemoteException;
     
     public String[][] getParticularGLTypeCodes(String gldata[][],String starts_with) throws RemoteException;
     
     public DayBookObject[] getDayBookDetails(String date,String string_qry) throws RemoteException;
     
     public DayBookObject[] getBranchConsolidatedDayBook(String date,String query_string,int value) throws RemoteException;
     
     //public GLReportObject[] getBalance(String date,int gl_values) throws RemoteException;
     
     public GLReportObject[] getBalanceTwoDates(String date1,String date2,int gl_values,String str) throws RemoteException;
     
     //public String[][] getIncomeDetails(String date1,String date2)throws RemoteException;
     
     //public String[][] getExpensesDetails(String date1,String date2)throws RemoteException;
     
     public GLReportObject[] getBalanceForBranch(String date,int gl_values) throws RemoteException;
     
     public DayBookObject[] getDetails(String date,String string_qry)throws RemoteException;
     
     public String convertDateToString(String date)throws RemoteException;
     
     public DayBookObject[] ConsolDayBookOpeningClosingBal(String date)throws RemoteException;
     
     public GLReportObject[] getBalanceBranchTwoDates(String date1,String date2,int gl_values,int flag_branch,String str)throws RemoteException; 
     
     public GLReportObject[] getMonthlyConsolidationDetailsForMonth(String mth,String year,int from_glcode,String from_gltype,int to_glcode,String to_gltype,int flag_branch,String str)throws RemoteException;;
     
     public GLReportObject[] getMonthlyDetailsForMonth(String mth,String year,int from_glcode,String from_gltype,int to_glcode,String to_gltype,String str) throws RemoteException;
     
     public TransferScroll[] transferScrollPrint(String date1,String date2,String ac_type,String str) throws RemoteException;
     
     public String[][] getForm1Details(int num)throws RemoteException;
     
     public String[][] getForm1LinkDetails(String date,int num)throws RemoteException;
     
     public int form1InsertRow(Form1Object form1_object,int num) throws RemoteException;
     
     public int form1LinkInsertRow(Form1Object form1_object,int num) throws RemoteException;
     
     public int form1UpdateRow(Form1Object form1_object,int num)throws RemoteException;
     
     public int form1LinkUpdateRow(Form1Object form1_object,int num)throws RemoteException;
     
 	 public int form1DeleteRow(Form1Object form1_object,int num) throws RemoteException;
 	 
 	 public int form1LinkDeleteRow(Form1Object form1_object,int num) throws RemoteException;
 	
 	 public String[][] getCodeDetails(String date,int num)throws RemoteException;
 	
 	 public String[][] getGLDetails(String gltype,String date)throws RemoteException;
 	 
 	 public String[][] getMarkingDateDetails()throws RemoteException;
 	 
 	 public int MarkingDateInsertRow(Form1Object form1_object)throws RemoteException;
 	 
 	 public int MarkingDateDeleteRow(Form1Object form1_object)throws RemoteException;
 	 
 	 public int MarkingDateUpdateRow(Form1Object form1_object)throws RemoteException;
 	 
 	public String[][] getSelfDetails(String date,int num)throws RemoteException;
 	
 	public String[] checkRBITransaction(Form1Object form1_object) throws RemoteException;
 	
 	public int Form1Posting(Form1Object form1_object) throws RemoteException;
 	
 	public String[][] getCRSLRReport(String year,String mth,int code) throws RemoteException;
 	
 	public String[][] getSubSchedules(String year,String month,int num) throws RemoteException;
	
 	public String[][] getForm1LinkBCDetails(String date)throws RemoteException;
 	
	public int updateFormLinkdetails(String link[][],int num)throws RemoteException;
 	
 	public String[][]  getForm1BCReport(String year,String mth,int num)throws RemoteException;
 	
 	public String [] getMarkingFridays(String  year,String mth)throws RemoteException;
 	
 	public String[][] getForm1AReport(String year,String mth)throws RemoteException;
 	
 	public GLReportObject[] getGLSchduleDetailsForTwoDates(String date1,String date2,String from_gltype,int from_glcode,String to_gltype,int to_glcode) throws RemoteException;
 	
 	public String[][] getGLDetailsForTwoDatesForGLType(String date1,String date2,String gltype)throws RemoteException;
 	
 	public boolean Form9Posting(String lastfriday,String de_user,String de_tml,String datetime,String amount[][])throws RemoteException;
 	
 	public String[][] getForm9Report(String year,String mth,int code,String qry)throws RemoteException;
 	
 	public String startingDate()throws RemoteException;
 	
 	public int storeMonthlySummary(String year_mnth,GLObject globject[],String tml,String user,String date_time)throws RemoteException,SQLException;
 	
 	public GLObject[] getMonthlySummary()throws RemoteException;
 	
 	public int verifyMonthlySummary(String yearmonth,String ve_tml,String ve_user,String ve_date_time)throws RemoteException,SQLException;
 	
 	public String[][] checkPostings(String date,String todate)throws RemoteException;
 	
 	public int  checkForm9Transaction(String lastfriday)throws RemoteException;
 	
 	public String[] getKeyParam(String table_name)throws RemoteException;
 	
 	public int  getModuleCode(String table_name)throws RemoteException;
 	
 	public String[][] getForm9InputCodes(String date)throws RemoteException;
 	
 	public String[][] getForm9LinkCodes(String date)throws RemoteException;
 	
 	
 }
