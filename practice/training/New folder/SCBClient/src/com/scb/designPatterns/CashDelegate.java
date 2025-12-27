package com.scb.designPatterns;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.CreateException;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
import lockerServer.LockersHome;
import lockerServer.LockersRemote;
import masterObject.cashier.CashObject;
import masterObject.cashier.CurrencyStockObject;
import masterObject.cashier.TerminalObject;
import masterObject.cashier.VoucherDataObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;
import shareServer.ShareHome;
import shareServer.ShareRemote;
import cashServer.CashHome;
import cashServer.CashRemote;

import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.ServiceLocatorException;
import com.scb.designPatterns.exceptions.SystemException;
import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.InsufficientAmountException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.TerminalNotFoundException;
import frontCounterServer.FrontCounterHome;
import frontCounterServer.FrontCounterRemote;
import general.Validations;

public class CashDelegate
{
	

	private CashHome cshHome;
	private CashRemote cshRemote;
	private CommonRemote commRemote;
	private CommonHome commHome;
	private LockersHome lockerHome;
	private LockersRemote lockerRemote;
	private ShareRemote shareRemote;
	private ShareHome shareHome;
	private FrontCounterHome fcHome;
	private FrontCounterRemote fcRemote;
	private LoansOnDepositHome ldHome;
	private LoansOnDepositRemote ldRemote;
	private CustomerHome custHome;
	private CustomerRemote custRemote;
	private CommonLocalHome comlkHome;
	private CommonLocal comlkRemote;
	private LoanHome lnHome;
	private LoanRemote lnRemote;
	
	
	
	
	public CashDelegate () throws ApplicationException,SystemException, RemoteException {
		
		cshHome =(CashHome)ServiceLocator.getInstance().getRemoteHome("CASHWEB",CashHome.class);
	    commHome = (CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
		lockerHome =(LockersHome)ServiceLocator.getInstance().getRemoteHome("LOCKERSWEB", LockersHome.class);
	    shareHome = (ShareHome)ServiceLocator.getInstance().getRemoteHome("SHAREWEB", ShareHome.class);
		fcHome =(FrontCounterHome)ServiceLocator.getInstance().getRemoteHome("FRONTCOUNTERWEB",FrontCounterHome.class);
	    ldHome =(LoansOnDepositHome)ServiceLocator.getInstance().getRemoteHome("LOANSONDEPOSITWEB", LoansOnDepositHome.class);
		custHome=(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB", CustomerHome.class);
		comlkHome = (CommonLocalHome)ServiceLocator.getInstance().getLocalHome("COMMONLOCALWEB",CommonLocalHome.class);
	    lnHome=(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB", LoanHome.class);
		try {
	        if(cshHome !=null){
	        	cshRemote = cshHome.create();
	            System.out.println("cRemote=="+cshRemote);
	        }
	        else
	            System.out.println("Ya its null here");
	    } catch (RemoteException e) {
	        throw new SystemException("SystemException:NamingException Occured"+e);
	    } catch (CreateException ex) {
	        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
	    }
	
	    try {
	        if(lnHome !=null){
	        	lnRemote = lnHome.create();
	            System.out.println("lnRemote=="+lnRemote);
	        }
	        else
	            System.out.println("Ya its null here");
	    } catch (RemoteException e) {
	        throw new SystemException("SystemException:NamingException Occured"+e);
	    } catch (CreateException ex) {
	        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
	    }
	    
	try {
        if(commHome !=null){
        	commRemote = commHome.create();
            System.out.println("common Remote=="+commRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
    
	try {
        if(lockerHome !=null){
        	lockerRemote = lockerHome.create();
            System.out.println("lockerHome Remotemm=="+lockerRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
    
    try {
        if(shareHome !=null){
        	shareRemote = shareHome.create();
            System.out.println("lockerHome Remotemm=="+shareRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
	
	try {
        if(fcHome !=null){
        	fcRemote = fcHome.create();
            System.out.println("FCHome Remotemm=="+fcRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
	
	try {
        if(ldHome !=null){
        	ldRemote = ldHome.create();
            System.out.println("ldHome Remotemm=="+ldRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
    try {
        if(custHome !=null){
        	custRemote = custHome.create();
            System.out.println("ldHome Remotemm=="+custRemote);
        }
        else
            System.out.println("Ya its null here");
    } catch (RemoteException e) {
        throw new SystemException("SystemException:NamingException Occured"+e);
    } catch (CreateException ex) {
        throw new ApplicationException("EJBCreateException:NamingException Occured"+ex);
    }
    
	}
	public CashDelegate(String id)throws ApplicationException {
	    // reconnect to the session bean for the given id
	    reconnect(id);
	}

	public String getID() throws ApplicationException {
	    try {
	        return ServiceLocator.getId(cshRemote);
	    } catch (ServiceLocatorException e) {
	        throw new ApplicationException("ApplicationException unable to get Session Bean"+e);
	    }
	}
	
	
	public CashRemote getCRemote(){
	    if(cshRemote!=null){
	        return cshRemote;
	    }
	    else{
	        return null;
	    }
	}
	
	public CommonRemote getcommRemote(){
	    if(cshRemote!=null){
	        return commRemote;
	    }
	    else{
	        return null;
	    }
	}
	
	
	
	public LockersRemote getLockersRemote(){
	    if(lockerRemote!=null){
	        return lockerRemote;
	    }
	    else{
	        return null;
	    }
	}
	public ShareRemote getShareRemote(){
	    if(shareRemote!=null){
	        return shareRemote;
	    }
	    else{
	        return null;
	    }
	}
	public FrontCounterRemote getFrontCounterRemote(){
	    if(fcRemote!=null){
	        return fcRemote;
	    }
	    else{
	        return null;
	    }
	}
	public LoansOnDepositRemote geLoansOnDepositRemote(){
	    if(ldRemote!=null){
	        return ldRemote;
	    }
	    else{
	        return null;
	    }
	}
	public CustomerRemote getCustomerRemote(){
	    if(custRemote!=null){
	        return custRemote;
	    }
	    else{
	        return null;
	    }
	}
	public void reconnect(String id) throws ApplicationException {
	    try {
	    	cshRemote = (CashRemote) ServiceLocator.getService(id);
	    } catch(ServiceLocatorException ex){
	        throw new ApplicationException("ApplicationException unable to get ServiceLocator"+ex);
	    }

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
	
	//***********************REBALANCE SCRLL************************
	public int rebalancingScroll(String date,String tml) throws SQLException,RemoteException
    {
		int rebalancescroll=cshRemote.rebalancingScroll(date, tml);
	    System.out.println("date in delegate"+rebalancescroll);
	   return rebalancescroll;
	

	}
	public int checkTmlOpenClose(String tmlno,String date) throws SQLException,RemoteException
	{
		int tmlopenclose = cshRemote.checkTmlOpenClose(tmlno, date);
		System.out.println("terminal Open Close"+tmlopenclose);
		return tmlopenclose;
	}
	 public double getCashTmlRunningBalance(String utml,String date) throws SQLException,RemoteException
	 {
		double getterminalbalance =cshRemote.getCashTmlRunningBalance(utml,date); 
		System.out.println("terminal balance"+getterminalbalance);
		return getterminalbalance;
	 }
	 public CurrencyStockObject getCurrencyStockObject(String tml,String date,int flag) throws SQLException,RemoteException
		{
		 CurrencyStockObject currencystockobject=new CurrencyStockObject();
		 currencystockobject=cshRemote.getCurrencyStockObject(tml,date,1);
		 return currencystockobject;
		}
	 public TerminalObject[] getTerminalObject() throws SQLException,RemoteException
	 {
	 TerminalObject[] terminalobject_view;
	 terminalobject_view=cshRemote.getTerminalObject();
	 return terminalobject_view;	 
	 }
	 public CashObject[] getDayCashData(String curdate,String tmlno,int flag,String query) throws SQLException,RecordsNotFoundException,RemoteException
	 {
		 CashObject cashobject_withdenom[] = null;
             cashobject_withdenom=cshRemote.getDayCashData(curdate,tmlno,flag,query); 
		 return cashobject_withdenom;
	 
	 }
	    public CashObject getDayCashSummary(String curdate,String tmlno) throws SQLException,RemoteException
	    {
	    	CashObject cashobject = null;;
	    	try {
				cashobject =cshRemote.getDayCashSummary(curdate, tmlno);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return cashobject;
	    
	    }
	    public ModuleObject[] getMainModules(int a,String str) throws SQLException,RemoteException {
	    	
	    		ModuleObject[] module_obj_array = null;
	    		try{
	    			
	    			 module_obj_array = commRemote.getMainModules(a,str); 
	    			
	    		} catch (Exception e) {

	    			e.printStackTrace();
	    		}
	    		
	    		return module_obj_array;
	    		
	    	}
	    public AccSubCategoryObject[] getAccSubCategories(int no) throws SQLException,RemoteException {
	    	AccSubCategoryObject[] acc=null;
	    	   acc=commRemote.getAccSubCategories(no);
	    	   
	    	   return acc;
	  
	    }
	    public String[] getLockersDesc()throws SQLException,RemoteException {
	    	
	    	String[] lockdesc = lockerRemote.getLockersDesc();
	    	return lockdesc;
	    }
	    
 public String[] getLockersTypes()throws SQLException,RemoteException {
	    	
	    	String[] locktype = lockerRemote.getLockersTypes();
	    	return locktype;
	    }
	    public ShareCategoryObject[] getShareCategories(int no,String actype) throws SQLException,RemoteException {
	    	ShareCategoryObject[] array_sharecategoryobject=null; 
	    	try
              {
                  array_sharecategoryobject=shareRemote.getShareCategories(0,actype);
              }
              catch(Exception ex)
              {
                  ex.printStackTrace();
              }
	    	
	    	return array_sharecategoryobject;
	    }	
	  public String getLockerType(String string_locker_ac_type,int int_locker_ac_no)throws SQLException,RemoteException,RecordsNotFoundException{
		  String string_locker_type =null;
		  try {
			string_locker_type=cshRemote.getLockerType(string_locker_ac_type,int_locker_ac_no);
		} catch (RecordsNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return string_locker_type;
	   
	    }
	  public double getLockerRent(String string_locker_ac_type,int int_locker_ac_no,String date) throws SQLException,RemoteException {
		  double double_locker_rent=0.0;
		try {
			double_locker_rent = cshRemote.getLockerRent(string_locker_ac_type,int_locker_ac_no,date);
		} catch (RecordsNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return double_locker_rent;
		  
	  }
	  public ShareMasterObject getShare(String actype,int shareno) throws RemoteException,SQLException{
		  ShareMasterObject sharemasterobject;
		  sharemasterobject=shareRemote.getShare(actype,shareno);
		  return sharemasterobject;
	  }
	  public String getCustmerName(String acc_type,int acc_no) throws SQLException,RemoteException
	  {
	  	String Cname=commRemote.getAccountHolderName(acc_type, acc_no);
	  	System.out.println("Cname-----> "+Cname);
	  	return Cname;
	  }
	  public AccountObject getAccount(String trn_mode,String acctype,int accno,String date) throws RemoteException,SQLException{
		  AccountObject accountobject;
		  System.out.println("I am here sumanth");
		  accountobject=commRemote.getAccount(null,acctype,accno,date);
		  if(accountobject!=null){
			  System.out.println("The account name in dligate ==="+accountobject.getAccname());
			  System.out.println("Account Number in delegate ==="+accountobject.getAccno());
		  System.out.println("the account type in deligte---> "+accountobject.getAcctype());
		  }
		  return accountobject;
		  
	  }
	  public double getCommission(String actype,int subcategory,double amt,String po_date)throws RemoteException,SQLException{
		  double comm_amt = 0.00;
		  comm_amt = fcRemote.getCommission(actype, subcategory,amt,po_date);
		  return comm_amt;
		  
	  }
	  
	  public String getCustSubCat(String cust_acctype,String cust_accno) throws SQLException,RemoteException{
		  String cust_subcat="";
		  cust_subcat = cshRemote.getCustSubCat(cust_acctype,cust_accno);
		  return  cust_subcat;
		  
	  }
	  public double getRent(String string_locker_type,int int_days,int int_category,String date) throws RemoteException,SQLException{
		  double rent = lockerRemote.getRent(string_locker_type,365,101,date);
		  return rent;
		  
	  }
	  public double getMaxPayableAmtonCurrentDay(String actype,int acno,String date) throws SQLException,RemoteException{
		double max_amt =  ldRemote.getMaxPayableAmtonCurrentDay(actype,acno,date);
		  return max_amt;
		  
	  }
	  public double checkLNAmount(String ln_ac_type,int ln_ac_no,String date,String de_user,String de_tml) throws SQLException,RemoteException{
		 double max_amt = cshRemote.checkLNAmount(ln_ac_type,ln_ac_no,date,"ship","CA99");
		 return max_amt;
	  }
	  public int checkDailyStatus(String date,int type)throws RemoteException,SQLException{
		  int proceed = commRemote.checkDailyStatus(date,0);
		  return proceed;
	  }
	  public CashObject getData(int sc_no,int type,String date) throws SQLException,ScrollNotFoundException,RemoteException{
		  CashObject cashobject=null;
		  cashobject = cshRemote.getData(sc_no,type,date);
		  System.out.println("hi cashobject from delegate"+cashobject);
		  return cashobject;
		  
	  }
	  public int storeDayCash(CashObject co)throws RemoteException,SQLException,InsufficientAmountException{
		  int scroll_no = 0;
		try {
			scroll_no = cshRemote.storeDayCash(co);
		} catch (InsufficientAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TerminalNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("scroll no in delegate"+scroll_no);
		 return scroll_no; 
	  }
	  
	  
		public String getCustomerAddress(int cid)throws RemoteException,CustomerNotFoundException,SQLException
		{
			String addr=custRemote.getCustomerAddress(cid);
			System.out.println("Adress in delegate=========="+addr);
		
			return addr;
		}
	  
		public String getCustomerAddType(int cid)throws RemoteException,CustomerNotFoundException,SQLException
		{
			String add_type = custRemote.getCustomerType(cid);
			System.out.println("Type in Delegate======="+add_type);
			return add_type;
		}
		
	  public CustomerMasterObject getCustomerdetails(int cid)throws CustomerNotFoundException,RemoteException
	     {
		 System.out.println("cid in deligate======================>"+cid); 
	     CustomerMasterObject mastobj=custRemote.getCustomer(cid);	
	         
	     return mastobj; 
	     } 
	  public static String getSysTime() 
	    { 
			return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());
		}

		class timethread extends Thread 
	    {
			timethread() 
	        {
				start();
			}

	    }	
		public String getSysDateTime()
		{
		    return(getSysDate()+" "+getSysTime());
		}
		public ChequeObject getCashWithdraw(int tokenno,int type,String date)throws RemoteException,SQLException
		{
			ChequeObject chequeobject=null;
			chequeobject=fcRemote.getCashWithdraw(tokenno,1,date);
			return chequeobject;
		
		}
		public CashObject getPaymentDetails(int vch_no,String date) throws SQLException,RecordsNotFoundException,RemoteException{
			CashObject cashObject =null;
			System.out.println("The vch no is ******"+vch_no);
			System.out.println("The date is"+date);
			cashObject = cshRemote.getPaymentDetails(vch_no,date);
			System.out.println("The csh object in the dlegate is************************* "+cashObject);
			return cashObject;
			
		}
		 public VoucherDataObject getPaymentData(int int_vch_no,String vch_type,String date)throws SQLException,RecordsNotFoundException,RemoteException
		    {
			 VoucherDataObject voucherobject = null;	
			 voucherobject = cshRemote.getPaymentData(int_vch_no,"P",date);
			 return voucherobject;
		    }
		 public VoucherDataObject[] getArrayVoucherData(int int_vch_no,String vch_type,String date)throws SQLException,RecordsNotFoundException,RemoteException
		 {
			 System.out.println("Error in delegate..........");
			 VoucherDataObject[] array_VoucherDataObject = null;
			 array_VoucherDataObject = cshRemote.getArrayVoucherData(int_vch_no,"P",date);
			 System.out.println("The array voucher in delegate-------->"+array_VoucherDataObject);
			 return array_VoucherDataObject;
			 
		 }
		 public String getGLName(String s1) throws SQLException,RemoteException
		 {
			 
			 String glname= cshRemote.getGLName(s1);
			 return glname;
			 
		 }
		 
		 public int storeVoucher(CashObject co) throws SQLException,RemoteException
		 {
			 System.out.println("After That You should go to delegate  !!!!!!!!!!!!!!!!!!!");
			 int int_scroll_no=cshRemote.storeVoucher(co);
			 System.out.println("Scrollllllll No here is----------"+int_scroll_no);
			 return int_scroll_no;
		 } 
		 public int storeToken(CashObject co) throws SQLException,RemoteException
		 {
			 System.out.println("cashobject in store token ...."+co);
			 int int_scroll_no=cshRemote.storeToken(co);
			 return int_scroll_no;		 
		} 
		 public int isScrollVerified(int scroll_no,String trndate) throws SQLException,RemoteException
		 {
			 int scroll_verified = 0;
			 System.out.println("Scroll in Deligate-0--> "+scroll_no);
			 System.out.println("Date in Deligate-0--> "+trndate);
			 scroll_verified = cshRemote.isScrollVerified(scroll_no,trndate);
			 
			 System.out.println("scroll_verified-----> "+scroll_verified);
			 return scroll_verified; 
		 }
		 public boolean verifyDayCash(CashObject co) throws SQLException,DateFormatException,RemoteException
		 {
			 System.out.println("Code in Deligate----<> "+co.getGLRefCode());
			cshRemote.verifyDayCash(co);
			 return true;
		
		 }
		 public int verifyDayCashPO(CashObject co) throws SQLException, DateFormatException,RemoteException
		 {
			 int po_srno = 0;
             po_srno = cshRemote.verifyDayCashPO(co);
             return  po_srno;
		 }
		 public CashObject[] forVerify(int type,String date) throws SQLException,ScrollNotFoundException,RemoteException
		 {
			 CashObject[] array_cashobject=null;
			 array_cashobject = cshRemote.forVerify(type,date);
			 return array_cashobject;
		 }
		 public double getAllCashTmlRunningBalance(String date) throws SQLException,RemoteException
		 {
			 double total_runbal = cshRemote.getAllCashTmlRunningBalance(date);
			 return total_runbal;
		 }
		 public int updateCash(CashObject co) throws SQLException,RemoteException
		 {
			 int flag = cshRemote.updateCash(co);
			 return flag;
		 }
		 public int checkReceiptPending(String sub_tml_no,String date) throws SQLException,RemoteException
		 {
			 int flag1=0;
			 flag1 = cshRemote.checkReceiptPending(sub_tml_no,date);
			 return flag1;
		 }
		 public int checkPaymentPending(String sub_tml_no,String date) throws SQLException,RemoteException
		 {
			 int flag1=0;
			 flag1 = cshRemote.checkPaymentPending(sub_tml_no,date);
			 return flag1;
		 }
		 public boolean checkClosingBalance(String main_tml_no,String date) throws SQLException,RemoteException
		 {
			 boolean close = false;
			 close = cshRemote.checkClosingBalance(main_tml_no,date);
			 return close;
		 }
		 public int closeCash(String maintml,String date) throws SQLException,RemoteException
		 {
			 int sc__no=0;
			 sc__no = cshRemote.closeCash(maintml,date);
			 return  sc__no; 
		 }
		 public int[] getGLCodes(String date,int type) throws SQLException,RemoteException
		 {
			 int[] array_int_glcodes= cshRemote.getGLCodes(date,1);
			 return array_int_glcodes;
		 }
		 public void updateCurrency(CashObject co) throws SQLException,RemoteException
		 {
			 cshRemote.updateCurrency(co);
			 System.out.println("i am in delegate updatecurrency"+co);
			
		 }
		 public void deleteVoucherDataTable(int int_vchno,String string_vch_type,String string_tml,String date) throws SQLException,RemoteException
		 {
			 cshRemote.deleteVoucherDataTable(int_vchno,string_vch_type, string_tml,date);
		 }
		 public void storeVoucherData(VoucherDataObject vdo) throws SQLException,RemoteException
		 {
			cshRemote.storeVoucherData(vdo);
		 }
		 public int[] getGLParam() throws SQLException,RemoteException
		 {
			 int[] gl_param = cshRemote.getGLParam();
			 return gl_param; 
		 }
		 public VoucherDataObject getVoucherData(int vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException,RemoteException
		 {
			
			 VoucherDataObject voucherDataObject=null;
			 
			 voucherDataObject=cshRemote.getVoucherData(vch_no,vch_type,date); 
			 return voucherDataObject;
		 }
		    /**
		     * Miscellaneous Receipt - Verification
		     */
		    public boolean verifyVoucherReceipts(CashObject co) throws SQLException,RemoteException
		    {
		    	cshRemote.verifyVoucherReceipts(co);
		    	return true;
		    }
		    public void deleteVoucherData(int int_vchno,String string_vch_type,String string_tml,String date,String time)throws SQLException,RemoteException
		    {
		    	cshRemote.deleteVoucherData(int_vchno,string_vch_type,string_tml,date, time);
		    }
		    public boolean deleteData(int scroll,int type,String date,String time) throws SQLException,ScrollNotFoundException,RemoteException  
		    {
		    	 cshRemote.deleteData(scroll,type,date,time);
		    	return true;
		    }
		    public boolean checkPAG(int sc_no,String date) throws SQLException,RemoteException
		    {
		    	boolean pag = cshRemote.checkPAG(sc_no,date);
		    	return true;
		    }
		    public int closeTerminal(String subtml,String maintml,CashObject co) throws SQLException,RemoteException
		    {
		    	int sc__no;
		    	sc__no = cshRemote.closeTerminal(subtml,maintml,co);
		    	return sc__no;
		    }
		    public int updateTerminal(CashObject co) throws SQLException,RemoteException
		    {
		    	int flag = cshRemote.updateTerminal(co);
		    	return flag;
		    	
		    }
		    public int checkTerminal(String tmlno) throws SQLException,RemoteException
		    {
		    	int flag;
		    	flag = cshRemote.checkTerminal("LK99");
		    	return flag;
		    }
		    public int AcceptMoney(CashObject co,String utml,String scroll) throws SQLException,RemoteException
		    {
		    	int accept = cshRemote.AcceptMoney( co,utml, scroll);
		    	return accept;
		    }
		  
			


		  public LoanReportObject getLoanDetails(String actype,String date,int acno) throws SQLException,RemoteException
		    {
		    	LoanReportObject loanreportobject = null;
		    	loanreportobject = ldRemote.getLoanDetails(actype,date,acno);
		    	
		    	return loanreportobject;
		    	
		    }
		    public LoanTransactionObject getQueryLoanStatus(String ln_acty,int ln_acno,String trn_date,String userid,String terminal)throws RemoteException,SQLException 
			{
		    	LoanTransactionObject loantransactionobject = null;
		    	loantransactionobject =lnRemote.getQueryLoanStatus(ln_acty,ln_acno,trn_date,userid,terminal,getSysDate());
		    	return loantransactionobject;
		    }
		    public LoanMasterObject getQueryOnLoanStatus(String ln_acty,int ln_acno,String trn_date) throws SQLException,RemoteException
			{
		    	masterObject.loans.LoanMasterObject loanmasterobject = null;
		    	loanmasterobject = lnRemote.getQueryOnLoanStatus(ln_acty,ln_acno,trn_date);
		    	return loanmasterobject;
		    	
			}
		    
		    public String[][] getGlCodeNames() throws SQLException,RemoteException
			 {
				String[][] glname=null;
				try{
					
					glname=cshRemote.getGlCodesNames();
				}catch(Exception e){
					e.printStackTrace();
				}
                return glname;
				 
			 }
		    public VoucherDataObject[] getVoucherDetails(int vch_no,String vch_type,String date) throws SQLException,RecordsNotFoundException,RemoteException
			 {
				 System.out.println("vch_no in delegate"+vch_no);
				 System.out.println("vch_type in delegate "+vch_type);
				 System.out.println("date in delegate "+date);
				 VoucherDataObject[] voucherDataDetails = cshRemote.voucherDetails(vch_no, vch_type, date);
				 return voucherDataDetails;
			 }
		    
		    
		    public String getBOD(int cid) throws RemoteException,CustomerNotFoundException,SQLException
		    {
		    	String bod = custRemote.getBOD(cid);
		    	System.out.println("Date Of Birth======="+bod);
		    	return bod;
		    }
		   //Added By shreya
		 /*   public String[] getGlNameCode() throws SQLException,RemoteException
			 {
				 
				String[] glname = cshRemote.getGlNameCode();
				 return glname;
				 
			 }
		    */
		  
		    //
		    
		   
		    public String checkDailyStatus2() 
		    {
		    	try{
		                         String cash_close = cshRemote.checkDailyStatus(getSysDate(),0);
		                            
		                            if(cash_close.equalsIgnoreCase("T"))
		                            {
		                            }
		                            else
		                            	return "Cash not Closed on    "+cash_close;
		    }
		    catch(Exception ex){ex.printStackTrace();
		    return null;
		    }
		    return null;                   
		    
		    }

	  }




