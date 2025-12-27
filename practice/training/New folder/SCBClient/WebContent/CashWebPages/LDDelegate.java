package com.scb.designPatterns;

import exceptions.AccountNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.ejb.CreateException;

import client.HomeFactory;

import com.scb.designPatterns.exceptions.ServiceLocatorException;

import termDepositServer.TermDepositHome;
import termDepositServer.TermDepositRemote;
import loanServer.LoanHome;
import loanServer.LoanRemote;
import masterObject.loansOnDeposit.LoanReportObject;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.loansOnDeposit.LoanMasterObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.loansOnDeposit.LoanTransactionObject;
import masterObject.termDeposit.DepositMasterObject;
import backOfficeServer.BackOfficeHome;
import backOfficeServer.BackOfficeRemote;

import commonServer.CommonHome;
import commonServer.CommonRemote;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 1, 2007
 * Time: 11:52:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class LDDelegate {
	private CommonHome commonhome;
	private CommonRemote commonremote;
	private LoansOnDepositHome loansondeposithome;
	private LoansOnDepositRemote loansondepositremote;
	private TermDepositRemote termdepositremote;
	private TermDepositHome tdhome; 
	private ServiceLocator serviceloc;
	private LoanHome lnhome;
	private LoanRemote lnremote;
	//private LoansOnDepositRemote LDremote;
	public LDDelegate()throws RemoteException,CreateException,ServiceLocatorException
	{
		
		try{

			commonhome = (CommonHome) ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
			commonremote = commonhome.create();
		
			tdhome=(TermDepositHome)ServiceLocator.getInstance().getRemoteHome("TERMDEPOSITWEB",TermDepositHome.class);
			termdepositremote=tdhome.create();
		
			lnhome =(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB",LoanHome.class);
			lnremote = lnhome.create();
			
			loansondeposithome =(LoansOnDepositHome)ServiceLocator.getInstance().getRemoteHome("LOANSONDEPOSITWEB",LoansOnDepositHome.class);
			loansondepositremote = loansondeposithome.create();
			}catch (RemoteException e) {
	            throw e;
	        }
	        catch (CreateException ex) {
	            throw ex;
	        }
	        catch(ServiceLocatorException se){
	            throw se;
	        }
	        
	} 
	public LDDelegate(String id) throws RemoteException,CreateException,ServiceLocatorException
	{
  		reconnect(id);
   	}
	    	
   	public String getID()throws RemoteException,CreateException,ServiceLocatorException 
   	{
     try {
	     return ServiceLocator.getId(loansondepositremote);
	     } catch (ServiceLocatorException e) {
	       throw e;
	       }
        }

   	public void reconnect(String id) throws ServiceLocatorException 
   	{
	try {
		loansondepositremote = (LoansOnDepositRemote) ServiceLocator.getService(id);
	    } catch(ServiceLocatorException ex){
	      throw ex;
	      }
       }
   	
   	public ModuleObject[] getMainModules(int a,String str)throws RemoteException
   	{
   		ModuleObject[] modobj=commonremote.getMainModules(a, str);
   		return modobj;
   		
    }
   	public LoanPurposeObject[] getLoanPurposes() throws RemoteException,SQLException
   	{
   		LoanPurposeObject[] loanpurobj=loansondepositremote.getLoanPurposes(); 
   		System.out.println("LDremote.getLoanPurposes()========="+loansondepositremote.getLoanPurposes());
   		return loanpurobj;
   	}
    
   	public String[] getPayMode(){
   		String[] str={"Cash","Transfer","PayOrder"};
   		return str;
   	}
   	public DepositMasterObject getDepositMaster(String actype,int no)throws RemoteException,SQLException
   	{
   		DepositMasterObject depositmasterobject=loansondepositremote.getDepositMaster(actype, no);
   		return depositmasterobject;
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
   	
   	public static String getSysTime()
   	{
   		return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());

   	}
   	
   	public String getSysDateTime()
   	{
   		return (getSysDate()+"  "+getSysTime());
   	}	
   	public int getDaysFromTwoDate(String first_date,String second_date)throws RemoteException
   	{
   		System.out.println("first_date=="+first_date+"second_date===="+second_date); 
   		int days=commonremote.getDaysFromTwoDate(first_date, second_date);
   		
   		return days;
   	}
   	
   	public double[] getDepositInterestRate(String ac_type,int dp_type,int cat_type,String date ,int days,double amount)throws RemoteException
   	{
   		double[] deposit_int_rate=termdepositremote.getDepositInterestRate(ac_type, dp_type, cat_type, date, days, amount);
   		
   		return deposit_int_rate;
   	}
   	public DepositMasterObject getFDClosureData(String acctype,int accno,int int_type) throws AccountNotFoundException,RecordsNotFoundException,RemoteException
   	{
   		DepositMasterObject dep_mast_obj=termdepositremote.getFDClosureData(acctype, accno, int_type);
   		
   		return dep_mast_obj;
   	}
   	
   	public double getLoanPercentage(String actype) throws SQLException,RemoteException
   	{
   		double loan_percentage=loansondepositremote.getLoanPercentage(actype);
   		return loan_percentage;
   	}
   	public LoanMasterObject getLoanMaster(String actype,int no) throws SQLException,RemoteException
   	{
   		LoanMasterObject LoanMastObj=loansondepositremote.getLoanMaster(actype, no);
   		System.out.println("getloan master detail==============>"+LoanMastObj);
   		return LoanMastObj;
   	} 
   	public double getCurrentIntRate(String actype,int acno) throws SQLException,Exception
   	{ 
   		double current_int_rate=loansondepositremote.getCurrentIntRate(actype, acno);
   		return current_int_rate;
    }
   	public DepositMasterObject[] RdBalancecalc(String ac_type,int acno) throws RecordsNotFoundException,RemoteException,SQLException
    {
   		DepositMasterObject[] deposit=loansondepositremote.RdBalancecalc(ac_type, acno);
   		return deposit;  
    }
   	public double getPygmyAmt(String ac_type,int ac_no,String curdate )throws SQLException,RemoteException
    {
   		double pygmyamt=loansondepositremote.getPygmyAmt(ac_type, ac_no, curdate);
   		return pygmyamt;
    }
   	public AccountObject getAccount(String trn_mode,String acctype,int accno,String date) throws RemoteException
	{
   		AccountObject accobj=commonremote.getAccount(trn_mode, acctype, accno, date);
   		return accobj;
   		
	}
   	public int storeLoanMaster(LoanMasterObject ln,int type) throws RemoteException,SQLException
   	{
   		int LM=loansondepositremote.storeLoanMaster(ln, type);
   		return LM;
   	}
   	public LoanReportObject getLoanDetails(String actype,int acno) throws SQLException,RemoteException
   	{   
   		LoanReportObject lro=loansondepositremote.getLoanDetails(actype,  getSysDate(), acno);
   		return lro; 
   	}
   	public LoanReportObject getLastPaymentDetails(String ln_ac_type,int ln_ac_no) throws RemoteException,SQLException
   	{
   		LoanReportObject lro1=loansondepositremote.getLastPaymentDetails(ln_ac_type, ln_ac_no);
   		return lro1;
   		
   	}
    public int loanRecovery(String lnactype,int lnacno,String actype,int acno,double amt,String uid,String utml)throws RemoteException,SQLException
    {
    	int ln_rec=loansondepositremote.loanRecovery(lnactype, lnacno, actype, acno, amt, uid, utml);
    	
    	return ln_rec;
    }
	
    public String getFutureMonthDate(String cur_date,int month) throws RemoteException
	{
    	String str=commonremote.getFutureMonthDate(cur_date, month);
    	
    	return str;
	}
    public LoanReportObject[] getPassBook(String actype,int acno) throws RemoteException,SQLException
    {
    	LoanReportObject[] repobj=loansondepositremote.getPassBook(actype, acno);
    	return repobj;
    }
    public LoanReportObject[] getOpenedAccounts(String from,String to,String actype,int type) throws RemoteException,SQLException
    {
    	LoanReportObject[] repobj=loansondepositremote.getOpenedAccounts(from, to, actype, type);
    	return repobj;
    }
    public LoanReportObject[] getClosedAccounts(String from,String to,String actype,int type) throws RemoteException,SQLException
    {
    	LoanReportObject[] repobj=loansondepositremote.getClosedAccounts(from, to, actype, type);
    	return repobj;
    }
	
    public LoanReportObject[] getAccruedInterest(String currentdate,String ac_type) throws RemoteException,SQLException
    {
    	LoanReportObject[] repobj=loansondepositremote.getAccruedInterest(currentdate,ac_type);
    	return repobj;
    }
    
    public int date_validation(String date1,String date2)
    {
    	int valid_date=Validations.validDate(date1,date2);
    	return valid_date; 
    	
    }
    public LoanReportObject[] getLDMaturityList(String date1, String date2,String actype,int type) throws RemoteException,SQLException
    {
    	LoanReportObject[] repobj=loansondepositremote.getLDMaturityList(date1, date2, actype, type);
    	System.out.println("Rep Obj=====>"+repobj); 
    	return repobj;
    }
    public LoanReportObject[] getLDExceedingMaturity(String date1, String date2,String actype,int type) throws RemoteException,SQLException
    {
    	
    	LoanReportObject[] repobj=loansondepositremote.getLDExceedingMaturity(date1, date2, actype, type);
    	return repobj;
    
    }
    
    public Hashtable getLoanTransaction(String actype,int no) throws SQLException,RemoteException
    {
    	Hashtable hash_valu=loansondepositremote.getLoanTransaction(actype, no);
    	return hash_valu;
    }
    
    public int verifyLoansOnDeposit(LoanMasterObject obj,String clientdate) throws SQLException,RemoteException
    {
    	int verification=loansondepositremote.verifyLoansOnDeposit(obj, clientdate);
    	return verification;
    }
    public LoanTransactionObject getLoanTransaction(int trnno) throws SQLException,RemoteException
    {
    	LoanTransactionObject loan_tran_obj=loansondepositremote.getLoanTransaction(trnno);
    	return loan_tran_obj;
    }
    public int verifyLoanTransaction(int trnno,String vid,String vtml,String clientdate,String clientdatetime) throws SQLException,RemoteException
    {
    	int ver_tran=loansondepositremote.verifyLoanTransaction(trnno, vid, vtml, clientdate, clientdatetime);
    	return ver_tran;
    }
    public LoanMasterObject additionalLoanDetails(String ac_type,int ac_no) throws SQLException,RecordsNotFoundException,RemoteException
    {
    	LoanMasterObject add_loan_det=loansondepositremote.additionalLoanDetails(ac_type, ac_no);
    	
    	return add_loan_det;
    }
    public int verifyAdditionalLoan(LoanMasterObject ln_object,String clientdate) throws RecordNotUpdatedException,SQLException,RemoteException
    {
    	int addloan_ver =loansondepositremote.verifyAdditionalLoan(ln_object, clientdate);
    	
    	return addloan_ver;
    	
    }
    
    public double getPygmyCurrentIntRate(String actype,String date,double amt_req,int ac_no) throws RemoteException,SQLException
    {
    	double pygmy_intrate=loansondepositremote.getPygmyCurrentIntRate(actype, date, amt_req, ac_no);
    	
    	return pygmy_intrate;
    	
    	
    }	
    
    public int deleteAccount(int ac_no,String ac_type,int depacno,String depactype) throws RemoteException,SQLException
    {
    	int dele_acc=loansondepositremote.deleteAccount(ac_no, ac_type,depacno,depactype);
    	
    	return dele_acc;
    	
    }
    public masterObject.loans.LoanTransactionObject calculateInterestForRecovery(String ac_type,int ac_no,String date,double amount,int category) throws RemoteException
    {
    	masterObject.loans.LoanTransactionObject loantrnobj=null;
    	loantrnobj=lnremote.calculateInterestForRecovery(ac_type,ac_no,date,amount,category);
    	return loantrnobj;
    }
    
} 
