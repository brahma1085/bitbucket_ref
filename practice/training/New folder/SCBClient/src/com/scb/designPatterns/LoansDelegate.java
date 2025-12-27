package com.scb.designPatterns;
   
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.CreateException;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.loans.DCBObject;
import masterObject.loans.DocumentsObject;
import masterObject.loans.LedgerObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.NPAObject;
import masterObject.loans.PSWSObject;
import masterObject.loans.PriorityMasterObject;
import masterObject.loans.SurityCoBorrowerObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.pygmyDeposit.SimpleMasterObject;
import masterObject.share.ShareMasterObject;
import shareServer.ShareHome;
import shareServer.ShareRemote;
import backOfficeServer.BackOfficeHome;
import backOfficeServer.BackOfficeRemote;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.GLKeyCodeNotFound;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import frontCounterServer.FrontCounterHome;
import frontCounterServer.FrontCounterRemote;
import general.Validations;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 1, 2007                 
 * Time: 11:52:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoansDelegate {
	private ServiceLocator serviceloc;
	private LoanHome lnhome;
	private LoanRemote lnremote;
	private CommonRemote cremote;
	private CommonHome chome;
	private BackOfficeHome backhome;
	private BackOfficeRemote backremote;
	private CustomerHome custhome;
	private CustomerRemote custremote;
	private ShareHome shhome;
	private ShareRemote shremote;
	private FrontCounterRemote fcremote;
	private FrontCounterHome fchome;
	
	private Hashtable hash_glcode;
	public LoansDelegate() throws RemoteException,CreateException,ServiceLocatorException{
		try{
			System.out.println("this is loans delegate constructor()");
			this.lnhome=(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB", LoanHome.class);
			this.lnremote=lnhome.create();
			
			this.chome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
			this.cremote=chome.create();
			
			this.backhome=(BackOfficeHome)ServiceLocator.getInstance().getRemoteHome("BACKOFFICEWEB",BackOfficeHome.class);
			this.backremote=backhome.create();
			
			this.shhome = (ShareHome)ServiceLocator.getInstance().getRemoteHome("SHAREWEB", ShareHome.class);
			this.shremote=shhome.create();
			
			this.custhome = (CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB", CustomerHome.class);
			this.custremote=custhome.create();
			
			this.fchome = (FrontCounterHome)ServiceLocator.getInstance().getRemoteHome("FRONTCOUNTERWEB", FrontCounterHome.class);
			this.fcremote = fchome.create();
			System.out.println("this Lons delegate Constructor() end");
			}
		catch (RemoteException e) {
            throw e;
        }
        catch (CreateException ex) {
            throw ex;
        }
        catch(ServiceLocatorException se){
            throw se;
        }
	}
	
	public LoansDelegate(String id) throws RemoteException,CreateException,ServiceLocatorException{
		reconnect(id);
	}
	
	public String getID()throws RemoteException,CreateException,ServiceLocatorException {
        try {
            return ServiceLocator.getId(lnremote);
        } catch (ServiceLocatorException e) {
            throw e;
        }
    }

	public void reconnect(String id) throws ServiceLocatorException {
        try {
            lnremote = (LoanRemote) ServiceLocator.getService(id);
        } catch(ServiceLocatorException ex){
            throw ex;
        }
    }
	
	public ModuleObject[] getLoanmodulecode(int type)throws RemoteException{
		ModuleObject[] lnmod=null;
		System.out.println("this is loan delegate obj =========== 1 >");
		lnmod = cremote.getMainModules(type,"1010000");
		System.out.println("this is loan delegate obj =========== 2 >");
		return lnmod;
	}
	public LoanMasterObject getModName(String accType) throws RemoteException
	{
		System.out.println("the Acc type is ===== "+accType);
		LoanMasterObject lnobj=null;
		lnobj = lnremote.getLoanModuleDesc(accType);
		System.out.println("the Acc type is ===== "+accType);
		System.out.println("Loan type Description---->"+ lnobj.getName());
		return lnobj;
	}
	public LoanMasterObject getLoanNoDetails(int accNo) throws RemoteException
	{
		System.out.println("the ac no is -----"+accNo);
		LoanMasterObject lnObj=null;
		lnObj = lnremote.getLoanNoDetails(accNo);
		return lnObj;
	}
	public ModuleObject[] getSharemodulecode(int type)throws RemoteException{
		ModuleObject[] lnmod=null;
		lnmod = cremote.getMainModules(type,"1001000");
		return lnmod;
	}
	
	public LoanPurposeObject[] getLoanDesc()throws RemoteException{
		LoanPurposeObject[] lnpurp=null;
		lnpurp=lnremote.getLoanPurposes();
		return lnpurp;
	}
	public LoanMasterObject getLoanMastervalues(String acctype,int acno,String date){
		LoanMasterObject loanmast = null;
		try{
		loanmast = lnremote.getLoanMaster(acctype,acno,date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return loanmast;
	}
	
	public int deleteReverseDetails(int voucher,int type){
		int a=0;
		try{
			System.out.println("In LoansDelegate");
		a = lnremote.deleteReverseLoanDetails(voucher,1);  
		}catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	public LoanTransactionObject getRevDetails(int vch){
		LoanTransactionObject lntrn=null;
		try{
		lntrn = lnremote.getReverseLoanDetails(vch);
		}
		catch(Exception e){e.printStackTrace();}
		return lntrn;
	}
	
	public int storeOtherCharges(LoanTransactionObject ltrn){
		int return_value=0;
		try{
		return_value = lnremote.storeLoanTransaction(ltrn);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return return_value;
	}
	
	public Hashtable getOtherChargeDetails(int tseq)throws RemoteException
	{ 
		Hashtable hash_tab=lnremote.getOtherChargeDetails(tseq);
		return hash_tab;
	}
	public String[] getDetails(){
		String[] str={"Personal","Relative","Employment","Application","Loan and Share Details","Signature Ins","Properpty ","CoBorrower","Surities ","Vehicle","Gold"};
		return str;
	}
	public String[] getPageIdDetails(){
		String[] strPageId={"0001","5093","5005","5002","Loan and Share Details","Signature Ins","5004","5032","5027","5022","Surities","Vehicle","Gold"};
		return strPageId;
	}
	public String[] getPageIdDetailsAmmend(){
		String[] strPageId={"0001","Relative","5005","Application","Loan and Share Details","Signature Ins","Properpty","5027","Surities","Vehicle","Gold","CoBorrower"};
		return strPageId;
	}
	public Vector[] getRelevantDetails(String code)throws RemoteException{
		String[] strYON=null,strdetails=null,strrevdetails=null;
		strYON=lnremote.getReleventDetails(code);
		Vector vec_final[]=new Vector[2];
	    Vector vec=new Vector();
	    Vector vec_pageID=new Vector();
	     String[] pageId=getPageIdDetailsAmmend();
	     System.out.println("8888888888888"+strYON); 
	      strdetails=getDetails();
	      System.out.println("99999999"+strdetails);
		  for(int i=0;i<strYON.length;i++){
		  if(strYON[i].equalsIgnoreCase("Y")){
			      /*strrevdetails[i]=strdetails[i];
			      strrevdetails[i]=strrevdetails[i]+"***";*/
			       vec.addElement(strdetails[i]+" ****");
			       vec_pageID.add(pageId[i]);
			      System.out.println("In if" +strYON[i]);
				  System.out.println("StrDeta=============="+strdetails[i]);
		}
		  if(strYON[i].equalsIgnoreCase("O")){
		      /*strrevdetails[i]=strdetails[i];
		      strrevdetails[i]=strrevdetails[i]+"***";*/
		      
		      vec.addElement(strdetails[i]);
		      vec_pageID.add(pageId[i]);
		  }
		  /*for(int i=0;i<strYON.length;i++){
			  System.out.println("Full"+strdetails[i]);
		  }
		  for(int j=0;j<strrevdetails.length;j++){
			  System.out.println("Full"+strrevdetails[j]);
		  }*/
		  }     
	    vec_final[0]=vec;
	    vec_final[1]=vec_pageID;
		return vec_final;
	}
	
	public Vector[] getRelevantDet(String code)throws RemoteException
	{
		String[] strYON=null,strdetails=null,strrevdetails=null;
		strYON=lnremote.getReleventDetails(code);
	    Vector vec_final[]=new Vector[2];
	    Vector vec=new Vector();
	    Vector vec_pageID=new Vector();
	    
	    String[] pageId=getPageIdDetails();
	     strdetails=getDetails();
	
	     for(int i=0;i<strYON.length;i++)
	     {
	    	 if(strYON[i].equalsIgnoreCase("Y"))
	    	 {
			       vec.addElement(strdetails[i]+" ****");
			       vec_pageID.add(pageId[i]);
			       System.out.println("In if" +strYON[i]);
			       System.out.println("StrDeta=============="+strdetails[i]);
			  }
	    	 if(strYON[i].equalsIgnoreCase("O"))
	    	 {
	    		 vec.addElement(strdetails[i]);
	    		 vec_pageID.add(pageId[i]);
	    	 }
		 }     
	     vec_final[0]=vec;
	     vec_final[1]=vec_pageID;
	     return vec_final;
	}
	public LoanMasterObject getQueryOnLoanStatus(String acctype,int accno,String date) throws RemoteException {
		System.out.println("In the delegate of getQueryOnLoanStatus");
		LoanMasterObject lmobj = lnremote.getQueryOnLoanStatus(acctype,accno,date);
		System.out.println("In the delegate of getQueryOnLoanStatus after return" + lmobj.getShareAccNo());
		return lmobj;
	}
	
	public String[] getLoanQueryDetails(){
		
		String str[] = {"Loan Status","Personal","Loan Transaction","Surity/Co-Borrower","Loan History"};
		return str;
	}
	
	public int loandailypostingforAllTypes(String date,String uid,String tml)  throws RemoteException{
		int return_value=0;
	    return_value = lnremote.doDailyRecoveryPosting(date,"Vinay","LN02");
	    return return_value; 
	}
	
	public int loandailypostingforSpecificTypes(String modulecode,int accno,String date,int category,int one,String uid,String tml)  throws RemoteException{
		int return_value=0;
		try{
			System.out.println("In LoanDelegate");
			return_value = lnremote.postRecoveryDetails(modulecode,accno,date,201,1,"Vinay","LN02");
		}
		 catch(Exception e){e.printStackTrace();}
	    return return_value; 
	}
	
	public static String getSysDate() {
        Calendar c = Calendar.getInstance();
        try {
            return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
            
        } catch (DateFormatException e) {
                e.printStackTrace();
        }
        return null; 
}        
	
	public String[] getGLTypes(){
		String[] types={"GL1","GL2"};
		return types;
	}
	
	public Hashtable getGLcodes() throws RemoteException {
		Hashtable gl_mod=null;
	    gl_mod = backremote.getGLCodes(getSysDate());
		return gl_mod;  		
	}
	
	public Object reverserecovery(LoanTransactionObject lntrn,int process, AccountTransObject acc_trn, int gl_code, String gl_type) throws RecordNotUpdatedException, RemoteException {
		Object obj=null;
	    obj = lnremote.reverseRecovery(lntrn, process, acc_trn, gl_code, gl_type);
		return obj;
		}
	
	public LoanReportObject getLoanDetails(String acctype,int accno,String date) throws RemoteException {
		LoanReportObject reportobj= null;
		reportobj = lnremote.getLoanDetails(acctype,accno, date);
		
		return reportobj;
	}
	
	public LoanReportObject[] getLoanDetails(String acctype,int accno,String fromdate,String todate,int type,String query) throws RemoteException {
		LoanReportObject reportobj[]= null;
		System.out.println("before calling");
		reportobj = lnremote.getLoanDetails(acctype,accno, fromdate,todate,type,query);
		return reportobj;
	}
	
	public LoanTransactionObject[] getLoanTransaction(String acctype,int accno,String str) throws RemoteException {
		LoanTransactionObject lntrnobj[]=null;
		lntrnobj = lnremote.getLoanTransaction(acctype,accno,str);
		return lntrnobj;
	}
	
	public int getDayCompare(String date1,String date2) throws RemoteException{
		int a=0;
		a=Validations.dayCompare(date1, date2);
		return a;
	}
	
	public Vector getIntAccuredReport(String reportdate,String acctype,int fromaccno,int toaccno,int fromintamt,int tointamt) throws RemoteException {
		Vector vec = new Vector();
		//LoanReportObject[] vec=null;
		System.out.println("In the Delagate of getIntAccuredReport ");
		vec = lnremote.getIntAccuredReport(reportdate,acctype,fromaccno,toaccno,fromintamt, tointamt);
		System.out.println(" After getIntAccuredReport Delegate");
		return vec;
	}
	
	public LoanTransactionObject getQueryLoanStatus(String ln_acty,int ln_acno,String trn_date,String user,String terminal,String datetime) throws RemoteException
	{
		LoanTransactionObject loantran=null;
		System.out.println("System date==>"+ getSysDate()); 
		loantran = lnremote.getQueryLoanStatus(ln_acty, ln_acno,trn_date ,"VINAY","LN01",getSysDate());
		return loantran;
	}
	
	public CustomerMasterObject getCustomer(int cid) throws RemoteException,CustomerNotFoundException{
		CustomerMasterObject cmobj=null;
		cmobj=custremote.getCustomer(cid);
		return cmobj;
	}
	
	public ArrayList getLoanNPAStatus(String acctype,int accno) throws RemoteException{
		ArrayList list = null;
		System.out.println("In delegate"+acctype+"Accno"+accno);
		list = lnremote.getLoanNPAStatus(acctype, accno);
		return list;
	}
	
	public ArrayList getLoanHistory(String acctype,int accno) throws RemoteException{
		ArrayList list = null;
		list = lnremote.getLoanHistory(acctype, accno);
		return list;
	}

	public SurityCoBorrowerObject[] getSurityCoBorrowerDetailsTwo(String acctype,int accno) throws RemoteException{
		SurityCoBorrowerObject[] surity = null;
		surity = lnremote.getSurityCoBorrowerDetailsTwo(acctype, accno);
		return surity;
	}

	public HashMap getAddress(int cid) throws RemoteException,CustomerNotFoundException {
		HashMap map=null;
		map=custremote.getAddress(cid);
		return map;
	}
	
	public ModuleObject[] getMainModules(int a,String trf_acctype) throws RemoteException{
		ModuleObject trf_module[] = null;
		trf_module = cremote.getMainModules(a,trf_acctype);
		return trf_module;
	}
	
	public AccountObject getAccount(String trn_mode,String acctype,int accno) throws RemoteException {
		AccountObject am = null;
		am = cremote.getAccount(trn_mode,acctype, accno,getSysDate());
		System.out.println("Account Details in deligate===>"+am); 
		return am;
	}
	
	
	public LoanTransactionObject calculateInterestForRecovery(String ac_type,int ac_no,double amount) throws RemoteException{
		LoanTransactionObject lntrn=null;
		lntrn = lnremote.calculateInterestForRecovery(ac_type, ac_no, getSysDate(), amount,-1);
		return lntrn;
	}
	
	
	public ArrayList getLastLoanTransactionDate(String acctype,int acno) throws RemoteException{
		ArrayList arrlist = null;
		arrlist = lnremote.getLastLoanTransactionDate(acctype,acno);
		return arrlist;
	}
	
	public ArrayList getSurityCoBorrowerDetails(String acctype,int accno) throws RemoteException{
		ArrayList arrlist = null;
		arrlist = lnremote.getSurityCoBorrowerDetails(acctype, accno);
		return arrlist;
	}
	public LoanTransactionObject[][] getOtherChargesDue(String acctype,int from_accno,int to_accno,int a,String date) throws RemoteException {
		LoanTransactionObject lntrn[][]=null;
		lntrn = lnremote.getOtherChargesDue(acctype,from_accno,to_accno,1,date);
		return lntrn;
	}
	
	public LoanTransactionObject[][] getOtherChargesCollected(String acctype,String fromdate,String todate,int from_acno,int to_acno,int flag) throws RemoteException{
		LoanTransactionObject lntrn[][]=null;
		lntrn = lnremote.getOtherChargesCollected(acctype,fromdate,todate,from_acno,to_acno,flag);
		return lntrn;
	}
	
	public String[][] getStageCode() throws RemoteException{
		String[][] stagecode=null;
		stagecode = lnremote.getStageCode();
		return stagecode;
	}
	
	public Vector getLoanOverdueReport(String ac_type, String stage_type,String report_date,String int_upto_date,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt) throws RemoteException
	{
		Vector lnrep=null;
		lnrep = lnremote.getLoanOverdueReport(ac_type, stage_type, report_date, int_upto_date, from_ac_no, to_ac_no, prn_from_amt, prn_to_amt, int_from_amt, int_to_amt);
		
		return lnrep;
	}
	
	public Vector getNPAProcessedDate(int days) throws RemoteException
	{
		Vector vector_npa_prodate;
		vector_npa_prodate = lnremote.getNPAProcessedDate(days);
		return vector_npa_prodate;
	}
	public Object[][] getDocs(String ac_type,int ac_no)throws RemoteException
	{ 	
	 Object[][] obj=lnremote.getDocs(ac_type, ac_no);
	 
	 return obj;
	}
	public DocumentsObject[] getLoanDocuments(String ac_type,int ac_no) throws RemoteException
	{
		DocumentsObject[] doc_obj=lnremote.getLoanDocuments(ac_type, ac_no);
		return doc_obj;
	}
	
	public ShareMasterObject getShareDetails(String acctype,int accno) throws RemoteException {
		
		ShareMasterObject shobj=null;
		shobj = shremote.getShare(acctype,accno);
		
		return null;
		
	}
	
	public Object[][] getLoanAndShareDetails(String shtype,int shno) throws RemoteException {
		Object[][] obj;
		obj = lnremote.getLoanAndShareDetails(shtype,shno,getSysDate());
		return obj;
	}
	public TreeMap pendingTrayList(String tray_no, String date) throws RemoteException
	{
		TreeMap tree_map=null;
		 tree_map=lnremote.pendingTrayList(tray_no, date);
		
		return tree_map;
	}
	public int storeSpecialInt(String str[]) throws RemoteException
	{
		int storesplint=lnremote.storeSpecialInt(str);
		
		return storesplint;
	}
	public int processPSWS(String pdate,String utml,String uname,int ty) throws RemoteException
	{
		int process=lnremote.processPSWS(pdate, utml, uname, ty);
		return process;
	}
	public PSWSObject[] getPSWSDetails(String prdate,int pr_code,int sel,String query) throws RemoteException
	{
		PSWSObject[] psobj=lnremote.getPSWSDetails(prdate, pr_code, sel, query);
		
		return psobj;
	}
	public PSWSObject getPSDetails() throws RemoteException
	{
		PSWSObject psws=lnremote.getPSDetails();
		return psws;
	}
	public String[] getProcessedDates() throws RemoteException
	{
		String[] processdate=lnremote.getProcessedDates();
		return processdate;
	}
	public Vector getJewelReport(String from,String to ) throws RemoteException
	{
		Vector jwel_rep=lnremote.getJewelReport(from, to);
		
		return jwel_rep;
	}
	public DCBObject[] dcbSchedule(int month,int year,int type,String acty,int fromno,int tono)throws RemoteException
	{
		DCBObject[] dcbschedule=lnremote.dcbSchedule(month, year, type, acty, fromno, tono);
		return dcbschedule;
	}
	
	public String[] dcbDelete(int type,String month[])throws RemoteException
	{
		String[] str_dcb=lnremote.dcbDelete(type, month);
		
		return str_dcb;
	}
	public DCBObject[] dcbSummary(String month)throws RemoteException
	{
		DCBObject[] dcbsummary=lnremote.dcbSummary(month);
		return dcbsummary;
	}
	
	public String[][] getNPACode() throws RemoteException
	{
	  String[][] str = null;
	  str = lnremote.getNPACode();
	  return str;
	}
	
	public LoanReportObject[] getNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno) throws RemoteException
	{
		LoanReportObject[] rep = null;
		rep = lnremote.getNPAReport(process_date, table_type, asset_code, ac_type, from_acno, to_acno);
		return rep;
	}
	
	public LoanReportObject[] getODCCNPAReport(String process_date,String table_type,String asset_code,String ac_type,int from_acno,int to_acno) throws RemoteException
	{
		LoanReportObject[] rep = null;
		rep = fcremote.getODCCNPAReport(process_date, table_type, asset_code, ac_type, from_acno, to_acno);
		return rep;
	}
	public boolean checkDCBReport(int month,int year)throws RemoteException
	 {
		 boolean checkdcb=lnremote.checkDCBReport(month, year);
		 return checkdcb;
	 }
	public void ODCCNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml )throws RemoteException,RecordsNotFoundException
	 {
		 fcremote.ODCCNPAProcessing(process_date, npa_towards, table, ac_type, start_ac_no, end_ac_no, uid, utml);
	 }
	 public void newstartNPAProcessing(String process_date,String npa_towards,int table,int ac_type,int start_ac_no,int end_ac_no,String uid,String utml ) throws RemoteException
	 {
		 lnremote.newstartNPAProcessing(process_date, npa_towards, table, ac_type, start_ac_no, end_ac_no, uid, utml);
	 }
	 public boolean setNPAAdminValues(NPAObject[] npa_values, int table_type) throws RemoteException
	 {
		 boolean val=lnremote.setNPAAdminValues(npa_values, table_type);
		 return val;
	 }
	 public LoanReportObject[][] getNPASummary(String pro_date,int days) throws RemoteException,RecordsNotFoundException
	 {  
		 LoanReportObject[][] repobj=lnremote.getNPASummary(pro_date, days);
		 return repobj;
	 }
	 public String[] getMonths()
		{
			String str[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
			return str;
		}
	 public int dcbProcess(int month,int year,int type,String acty,int fromno,int tono,String tml,String user,boolean delete)throws RemoteException
	 {
		 int dcbprocess=lnremote.dcbProcess(month, year, type, acty, fromno, tono, tml, user, delete);
		 return dcbprocess;
	 }
	 public NPAObject[] getNPAAdminValues(String ac_type, int table_type) throws RemoteException
	 {
		 NPAObject[] npaobj=lnremote.getNPAAdminValues(ac_type, table_type);
		 return npaobj;
	 }
	
	 public LoanMasterObject getLoanMaster(int ln_acno,String ln_acty) throws RemoteException
	{
		 LoanMasterObject lmobj=null;
		 lmobj = lnremote.getLoanMaster(ln_acno, ln_acty);
		 return lmobj;
	}
	 public int cheakLoanNum(int ln_acno) throws RemoteException,SQLException
	 {
		 System.out.println("Acc number is ====="+ln_acno);
		 int i = lnremote.getCheakLoanNum(ln_acno);
		 System.out.println("i value in delegate is "+i);
		 return i;
	 }
	 public int deleteLoanSanction(String acctype, int accno) throws RemoteException
	 {
		 int i=lnremote.deleteLoanSanction(acctype, accno);
		 
		 return i;
	 }	
	 
	 public int deleteLoanDisbursement(String acctype,int accno) throws RemoteException
	 {
		 int i=lnremote.deleteLoanDisbursement(acctype, accno);
		 
		 return i;
		 
	 }
	 public boolean isNominal(String ac_type, int ac_no)  throws RemoteException
	 {
		 boolean nom=lnremote.isNominal(ac_type, ac_no);
		 
		 return nom;
	 }
	 
	 public double getSharePercentage(String ln_type) throws RemoteException
	 {
		 double per= lnremote.getSharePercentage(ln_type);
		 
		 return per;
	 }
	 
	 public int reScheduleLoan(String actype,int acno,int install,double amt,Vector vec,String effdate) throws RemoteException
	 {
		 int res=0;
		 res = lnremote.reScheduleLoan(actype, acno, install, amt, vec, effdate,"VINAY","LN01","10:10:10 10:10");
		 return res;
	 }
	public Object penalIntFix(LoanTransactionObject lntrnonbj) throws RemoteException
	{
		Object obj=null;
		obj = lnremote.penalIntFix(lntrnonbj);
		return obj;
	}
	//ActionDueReport View
	public DocumentsObject[] getActionDue(String processDate,String acctype,int stagecode) throws RemoteException
	{
		DocumentsObject[] doc = null;
		doc = lnremote.getActionDue(processDate,acctype,stagecode);
		return doc;
	}
	 public String[] getTables()
	 {
		 String[] str = {"LoanEntryIns","LoanCategoryRate","LoanIntRate","LoanPeriodRate","PenalIntRate","LoanPurposes"};
		 return str;
	 }
	 
	 public String[][] getLoanAdminTable(String table_name) throws RemoteException
	 {
		 String[][] str=null;
		 System.out.println("Before Calling"+table_name);
		 str = lnremote.getLoanAdminTable(table_name);
		 return str;
	 }
	 
	 public void setLoanAdminTable(String table_name,String table_data[][],String query) throws RemoteException
	 {
        lnremote.setLoanAdminTable(table_name, table_data, query);		 
	 }
	 public void loanDefaulterProcessing(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String process_due_towards) throws RemoteException
	 {
		 lnremote.loanDefaulterProcessing(ac_type,from_ac_no,to_ac_no,prn_from_amt,prn_to_amt,int_from_amt,int_to_amt,bal_from_amt,bal_to_amt,process_due_towards,getSysDate());
	 }
	 
	 public Vector getLoanDefaulters(String ac_type,int from_ac_no,int to_ac_no,double prn_from_amt,double prn_to_amt,double int_from_amt,double int_to_amt,double bal_from_amt,double bal_to_amt,String default_towards,int stage_no) throws RemoteException
	 {
		 Vector vec_obj=null;
		 lnremote.getLoanDefaulters(ac_type, from_ac_no, to_ac_no, prn_from_amt, prn_to_amt, int_from_amt, int_to_amt, bal_from_amt, bal_to_amt, default_towards, stage_no,getSysDate());
		 return vec_obj;
		 
	 }
	 public String[] getRemainderSet()
	 {
		 String[] str = {"Process","Notice"};
		 return str;
	 }
	public SignatureInstructionObject[] getSignatureDetails(int accno,String type) throws RemoteException
	{
			SignatureInstructionObject[] sign_inst_obj=cremote.getSignatureDetails(accno, type);
			
			return sign_inst_obj;
	}	
	public double getModuleTableValue(String value) throws RemoteException
	{
		System.out.println("Value in Delegate"+value);
		double d = lnremote.getModuleTableValue(value);
		System.out.println("D====>"+d);
		return d;
	}
	public String[] getReleventDetails_str(String modulecode) throws RemoteException
	 {
		 String relaventdet[]=lnremote.getReleventDetails(modulecode);
		 return relaventdet;
	 }
	public int storeLoanMaster(LoanMasterObject lmobj,int type) throws RemoteException
	{
		int result;
		System.out.println("In Delegate=699===>"+lmobj.getCustomerId());
		result = lnremote.storeLoanMaster(lmobj, type,getSysDateTime());
		System.out.println("In Delegate====>"+result);
		return result;
	}
	public String getSysDateTime() 
	{
		Calendar c=Calendar.getInstance();
		
		String str=String.valueOf(c.get(Calendar.SECOND));
		if(str.length()==1)
			str="0"+str;
		String str1=String.valueOf(c.get(Calendar.MINUTE));
		if(str1.length()==1)
			str1="0"+str1;
		String str2=String.valueOf(c.get(Calendar.HOUR));
		if(str2.length()==1)
			str2="0"+str2;
		
		try {
			return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR))+" "+str2+":"+str1+":"+str+"  ");
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	public int getUpdateOtherCharges(LoanTransactionObject lto,int seq) throws RemoteException
	{
		int updated_ref=lnremote.getUpdateOtherCharges(lto, seq);
		return updated_ref;
		
	}
	public LoanTransactionObject deleteOtherCharges(int tseq,String actype,int acno,int ind) throws RemoteException
	{
		LoanTransactionObject delete_val=lnremote.deleteOtherCharges(tseq, actype, acno, ind);
		return delete_val;
	}
	public int getCid(String actype,int no) throws CustomerNotFoundException,RemoteException
	{
		int cid=lnremote.getCid(actype,no);
		return cid;
	}
	public int storeLoanAmount(LoanTransactionObject lnobj,int type,String trn_date) throws RemoteException,RecordsNotInsertedException
	{
		int storeaccnum=lnremote.storeLoanAmount(lnobj, type, trn_date);
		return storeaccnum;
	}
	public PriorityMasterObject[] getPriorityDesc() throws RemoteException{
		
		PriorityMasterObject mastobj[]=lnremote.getPriorityDesc();
		return mastobj;
	}
	public double  getIntRate(String ln_type,String fdate,int category,int period,double amt,int ac_no) throws RemoteException
	{
		double intrate=lnremote.getIntRate(ln_type, fdate, category, period, amt, ac_no);
		return intrate;
		
	}
	public double getPenalIntRate(String ln_type,String fdate,int category) throws RemoteException
	{
		double penalintrate=lnremote.getPenalIntRate(ln_type, fdate, category);
		return penalintrate;
	}
	public int sanctionLoan(int actype,int acno,double amt,int install,int priority,boolean weak,double rate, int int_type, int int_rate_type, double install_amt, double holiday_months, String trn_date)throws RemoteException
	{
		int san=lnremote.sanctionLoan(actype, acno, amt, install, priority, weak, rate, int_type, int_rate_type, install_amt, holiday_months, trn_date);
		return san;
	}
	public ModuleObject[] getAccountType(int type) throws RemoteException{
		ModuleObject[] lnmod=null;
		lnmod=cremote.getMainModules(2,"'1002000','1007000','1015000','1014000','1017000','1018000'");
		return lnmod;
	}
	/*public int getLoanerName(int accountnumber) throws RemoteException
	{
		System.out.println("this is delegate class======== 2");
		int nameIs= lnremote.getLoanerNameinRemote(accountnumber);
		return nameIs;
	}*/
	
	public SimpleMasterObject getAccountDetails(String accno) throws CustomerNotFoundException,RemoteException
	{
		System.out.println("the accno is ================ >" +accno);
		SimpleMasterObject sm =lnremote.getAccDetails(accno);
		System.out.println("Step 2======== 2");
		return sm;
	}
	public LedgerObject[] getLedger(int fromac,int toac,int acstatus,String fdate,String tdate)throws RemoteException
	 {
		 
		 LedgerObject[] ledger_obj=null;
		 System.out.println("In the loans Delegate");
		 ledger_obj=lnremote.getLedger(fromac, toac, acstatus, fdate, tdate);
		 System.out.println("***********ledger_obj************" + ledger_obj.length);
		 return ledger_obj;
		 
	 }
	public int disburseLoan(int actype,int acno,double disbursement,Object data[][],double amtsanct,String uid,String tml,String paymode,String narr,String trn_date,String date_time) throws RemoteException
	{   int result;
	    result=lnremote.disburseLoan(actype, acno, disbursement, data, amtsanct, uid, tml, paymode, narr, trn_date, date_time);
		return result;
	}
	public int verifyLoanDisbursement(int trn,String ac_type,int ac_no,String uid,String tml,String sbtype,int sbno,double disbursement,LoanMasterObject loan_object,LoanTransactionObject loan_trn_object,String date_time) throws RemoteException
	{
		int verify_dis=lnremote.verifyLoanDisbursement(trn, ac_type, ac_no, uid, tml, sbtype, sbno, disbursement, loan_object, loan_trn_object, date_time);
		System.out.println("*************Result in delegate***********" + verify_dis );
		return verify_dis;
	}
	
	public LoanTransactionObject getUnVerifiedDisbursement(String ln_type,int acno) throws RemoteException
	{
		LoanTransactionObject loantranobj=null;
		loantranobj=lnremote.getUnVerifiedDisbursement(ln_type, acno);
		return loantranobj;
	}
	public int verifyLoanMaster(int actype,int acno,String uid,String tml)throws RemoteException
	{
		int loanmaster=lnremote.verifyLoanMaster(actype, acno, uid, tml);
		return loanmaster;
	}
	public int verifySanction(int actype,int acno) throws RemoteException
	{
		int verify_sanction=lnremote.verifySanction(actype, acno);
		return verify_sanction;
	}
	public int verifyOtherCharges(String actype, int acno, int ref_no, double trn_amt, String date, String vetml, String veuser, String ve_date)  throws  GLKeyCodeNotFound,RemoteException
	{
		int verify_othercharges=lnremote.verifyOtherCharges(actype, acno, ref_no, trn_amt, date, vetml, veuser, ve_date);
		return verify_othercharges;
	}
	public String[] getPayMode(){
    	String[] paycombo={"Cash","Transfer","PayOrder"};
    	return paycombo;
    }
	public String[][] getDirectorDetails(String date)throws SQLException,RemoteException
	{
		System.out.println("*********** AM in Loans Delegate*************");
		String[][] dirdetails=lnremote.getDirectorDetails(date);
		return dirdetails;
	}
	public String[] getDirectorRelations()throws SQLException,RemoteException
	{
		String[] dir_relations=lnremote.getDirectorRelations();
		return dir_relations;
	}
	public ModuleObject[] getpayAcctype() throws RemoteException{
		ModuleObject[] module_object=cremote.getMainModules(4,"'1002000','1007000','1018000'");
		for(ModuleObject mo:module_object){
			System.out.println("**********module code is***********"+mo.getModuleAbbrv());
		}
		return module_object;


	}
	//code added by amzad on 16/11/2009
	public LoanTransactionObject getTransferVoucherNo(int trf_scroll_no,String date) 
	{
		System.out.println("Loan Recovery inside Delegate Voucher NUmber--------->"+trf_scroll_no);
		LoanTransactionObject loanTransactionObject = null;
		try{
			loanTransactionObject=lnremote.getTransferVoucherNo(trf_scroll_no,date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return loanTransactionObject;
	}
	
	//code added by amzad on 14/11/2009
	public int recoverLoanAmount(LoanTransactionObject lnobj) throws RemoteException,RecordsNotInsertedException
	{
		int storeaccnum=0;
		try{
		storeaccnum=lnremote.verifyLoanRecovery(lnobj);
		}catch(Exception e){
			e.printStackTrace();
		}
		return storeaccnum;
	}
	
	public Object[][] getPreviousDisbursement(String ac_type,int ac_no,boolean flag) throws RemoteException
	{
		Object[][] values=null;
		values=lnremote.getPreviousDisbursement(ac_type, ac_no, false);
		return values;
	}
	
	public int getPreviousDisbDet(String ac_type,int ac_no,String date) throws RemoteException
	{
		int dis_det;
		dis_det=lnremote.getPreviousDisbDet(ac_type, ac_no, date);
		return dis_det;
	}
	
	public int getNumPreviousDisb(String ac_type,int ac_no,int flag) throws RemoteException
	{	
		
		int no_pre_dis=lnremote.getNumPreviousDisb(ac_type, ac_no, 1);
		return no_pre_dis;
	}
	public double getDisbursementLeft(String ac_type,int ac_no) throws RemoteException
	{
		double dis_left=lnremote.getDisbursementLeft(ac_type, ac_no);
		return dis_left;
	}
	
	public int loanRecovery(String uid,String utml,String date,String date_time)throws RemoteException
	{
		int auto_result=lnremote.loanRecovery(uid, utml, date, date_time);
		return auto_result;
	}
	public String[] getQuaterlydates() throws RemoteException
	{	String[] dates=null;
		dates=lnremote.getQuaterlydates();
		return dates;
	}
	public LoanTransactionObject[] calc_int(String today_date)throws RemoteException
	{	
		LoanTransactionObject[] ltrn=null;
		ltrn=lnremote.calc_int(today_date);
		return ltrn;
	}
	public LoanTransactionObject[] getQuaterlyIntDetails(String date)throws RemoteException
	{
		LoanTransactionObject[] ltrn=null;
		ltrn=lnremote.getQuaterlyIntDetails(date);
		return ltrn;
	}
	public LoanTransactionObject getLoanTran(String acctype,int accno) throws RemoteException
	{
		LoanTransactionObject ltrn=null;
		ltrn=lnremote.getLoanTran(acctype, accno);
		return ltrn;
	}
	public int updateLoanMaster(LoanMasterObject lnobj,String actype,int acno,String datetime) throws RemoteException
	{
		int res=lnremote.updateLoanMaster(lnobj, actype, acno, datetime);
		return res;
	}
}