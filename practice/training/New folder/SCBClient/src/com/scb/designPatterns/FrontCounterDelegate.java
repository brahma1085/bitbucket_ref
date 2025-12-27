package com.scb.designPatterns;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import termDepositServer.TermDepositHome;
import termDepositServer.TermDepositRemote;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
import masterObject.administrator.UserActivityMasterObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.AdminObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.frontCounter.IntPayObject;
import masterObject.frontCounter.ODCCMasterObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.frontCounter.StockDetailsObject;
import masterObject.general.AccCategoryObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.AddressTypesObject;
import masterObject.general.DoubleFormat;
import masterObject.general.GLMasterObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.termDeposit.DepositMasterObject;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import frontCounterServer.FrontCounterHome;
import frontCounterServer.FrontCounterRemote;
import general.Validations;

/**
 
 * User: Mohsin
 *
 * 
 * To change this template use File | Settings | File Templates.
 */
public class FrontCounterDelegate {
    private ServiceLocator serviceLoactor;
    private FrontCounterHome fcHome;
    private FrontCounterRemote fcRemote;
    private CommonHome comHome;
    private AccountMasterObject accountmasterobject;
    private CustomerHome custHome;
    private TermDepositHome termHome;
    private TermDepositRemote termRemote;
    private CustomerRemote custRemote;
    private ChequeObject chequeobject,chq;
    private ODCCMasterObject odccmasterobject,odccMaster;
    StockDetailsObject stockdetailsobject;
    GLMasterObject[] array_glmasterobject;
    AddressTypesObject[] array_addresstypesobject;
    PayOrderObject payorderobj;
    PayOrderObject[] payorderobject_view,poreport;
    AccountTransObject array_accounttransobject[] = null;
    AdminObject[] admin_object;
    AccountObject accountobject;
    AccountObject[] arrayAccountObj;
    Object[][] loanDetail,unverifiedpo,unverifiedtokens,odccintDetail;
    //PO ADMIN
    AccCategoryObject[] acc_cat;
    AccSubCategoryObject[] acc_subcat;
    //POADMIN ENDS
    SignatureInstructionObject[] nsignatureobject;
    AccountObject[] accountobjectarray;
    int posub,odccverify;
    ModuleObject array_moduleobject[]=null;
    Object object_tabledata[][]=null;
    AccountInfoObject accountinfoobject;
    Vector vector_chequeobject;
    TreeMap pendinglist;
    Vector vector_chqno_exists;
    String[] chqdetail;
    HttpSession session;
    ChequeObject[] chqIObject;
    PayOrderObject[] payorderobject;
    PayOrderObject poObject;
    AccCategoryObject[] accat;
    LoansOnDepositHome LDHome;
    LoansOnDepositRemote LDRemote;
    AccountObject accountObj;
    String name;
    String[][] unverified;
    String chqinst,chbkno,actyp,acno;
    DepositMasterObject tdMaster,dpMaster;
    Double sharepercentage;
    String limit;
    Vector v;
    AccSubCategoryObject[] subcat;
    String user,tml;
    IntPayObject[] array_intpayobject;
    String chi1,chi2,chi3,chi4,chi5,chi6,chi7,chi8;
    Double loan_percentage;
    int acmatch;
    private AccountObject[] array_accountobject=null;
    AccountMasterObject[] array_account_master;
    String acname,actypnum,chbookno,paystop,postdated,expiry,cancel,deleted;

    
    
    public CommonRemote getComRemote() {
        return this.comRemote;
    }

    private CommonRemote comRemote;
    private ModuleObject[] comboElements=null;
    private LoanRemote loanRemote;
    private LoanHome loanHome;
    LoanPurposeObject[] loanpurposeobject;
   // session=req.getSession();
    /*user=(String)session.getServletContext().getAttribute("UserName");
    tml=(String)session.getServletContext().getAttribute("UserTml");*/
    
    public FrontCounterDelegate()throws RemoteException,CreateException,ServiceLocatorException  {
        try{
           this.fcHome =(FrontCounterHome)ServiceLocator.getInstance().getRemoteHome("FRONTCOUNTERWEB",FrontCounterHome.class);
           this.fcRemote = fcHome.create();

           getCommonRemote();
           this.custHome=(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
           this.custRemote=custHome.create();
           
           //for Loan Purpose
           this.loanHome =(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB",LoanHome.class);
           this.loanRemote = loanHome.create();
           //For ter Deposit
           this.termHome =(TermDepositHome)ServiceLocator.getInstance().getRemoteHome("TERMDEPOSITWEB",TermDepositHome.class);
           this.termRemote = termHome.create();
           
           //for LD
           this.LDHome =(LoansOnDepositHome)ServiceLocator.getInstance().getRemoteHome("LOANSONDEPOSITWEB",LoansOnDepositHome.class);
           this.LDRemote = LDHome.create();
           
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

    public FrontCounterDelegate(String id)throws RemoteException,CreateException,ServiceLocatorException {
        // reconnect to the session bean for the given id
        reconnect(id);
    }

    public String getID()throws RemoteException,CreateException,ServiceLocatorException {
        try {
            return ServiceLocator.getId(fcRemote);
        } catch (ServiceLocatorException e) {
            throw e;
        }
    }

    public void reconnect(String id) throws ServiceLocatorException {
        try {
            fcRemote = (FrontCounterRemote) ServiceLocator.getService(id);
        } catch(ServiceLocatorException ex){
            throw ex;
        }
    }
    private CommonRemote getCommonRemote()throws RemoteException,CreateException,ServiceLocatorException {
        this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
        this.comRemote=comHome.create();
        return comRemote;
    }
    public ModuleObject[] getComboElements(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1017000,1018000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000,1014001,1001001");
        }
        return comboElements;
    }
    
    public ModuleObject[] getComboElementsInt(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1014000,1015000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000,1014001,1001001");
        }
        return comboElements;
    }
    
    
    public ModuleObject[] getodccModule(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1015000,1014000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1015001,1014001");
        }
        return comboElements;
    }
    
    
    //Module Code for CC
    public ModuleObject[] getCCModule(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1014000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1014001");
            
        }
        return comboElements;
    }
    
    //for max Amount in od Account
    public ModuleObject[] getODModule(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1015000");
        }
        else if(type==1){
            
        }
        return comboElements;
    }
    public ModuleObject[] getComboElementsAll(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1017000,1018000,1014000,1015001");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000");
        }
        return comboElements;
    }
    public ModuleObject[] getSBCA(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1017000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000");
        }
        return comboElements;
    }
    
    public ModuleObject[] getSBCAODCC(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000,1014000,1015000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1014000,1018000,1014001");
        }
        return comboElements;
    }
    public ModuleObject[] getSHModule(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1001000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1001001");
        }
        return comboElements;
    }
    
    //To get deposit Account types
    public ModuleObject[] getDepositAccountType(int type)throws RemoteException{
        /*
         type 0 for SB and CA account type
         type 1 for introducer account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1003000,1004000,1005000,1006000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1002000,1007000,1001000,1017000,1018000");
        }
        return comboElements;
    }
    //SB opening
    public AccountObject getAccountObj(String actyp,String acnum)
    {
    	try{
    		 accountObj=comRemote.getAccount(null,actyp,Integer.parseInt(acnum),getSysDate());
    		 
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return accountObj;
    }
    
    //to get Add
    public AddressTypesObject[] getAddresstype(){
    	try{
    		array_addresstypesobject=comRemote.getAddressTypes();
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return array_addresstypesobject;
    }
    
    public String[] getDetailsComboElements(){
    	TreeSet details=new TreeSet();
    	details.add("Personal");
    	details.add("Cash");
    	details.add("Nominee");
    	details.add("JointHolders");
    	details.add("Signature Ins");
    	details.add("Introducer Type");
    	
        String[] combonames={"Personal","Cash","Nominee","JointHolders","Signature Ins","Introducer Type"};
        return combonames;
    }
    public AccountMasterObject getAccountMaster(int ac_num,String mod_code)throws AccountNotFoundException,RemoteException,CreateException{
        ModuleObject[] mod_array;
        accountmasterobject=fcRemote.getAccountMaster(ac_num,mod_code.trim());
        return accountmasterobject;
    }
    public AccountObject getIntroducerAcntDetails(String acType,int acNo)throws RemoteException{
        AccountObject introObject=null;
        String date=getSysDate();
        introObject=getComRemote().getAccount(null,acType,acNo,date);
        return introObject; 
    }
    public AccountObject getOdccAccount(String sharetype,String shno){
    	try{
    		System.out.println("At 340 in Delegate=====> Account type====>"+sharetype);
    		System.out.println("At 341 in Delegate=====> Account No====>"+shno);
    		   accountObj=comRemote.getAccount(null,sharetype,Integer.parseInt(shno),getSysDate());	
    		
    		   System.out.println("");
    		   
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return accountObj;
    }
    
    public AccountObject getOdccAccountReceipt(String sharetype,String shno){
    	try{
    		System.out.println("At 340 in Delegate=====> Account type====>"+sharetype);
    		System.out.println("At 341 in Delegate=====> Account No====>"+shno);
    		   accountObj=comRemote.getAccount("C",sharetype,Integer.parseInt(shno),getSysDate());	
    		
    		   System.out.println("");
    		   
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return accountObj;
    }
    
    public AccountObject[] getArrayAccountObj(String sharetype,String shno){
    	try{
 		   arrayAccountObj=comRemote.getAccounts(sharetype,Integer.parseInt(shno),getSysDate());	
 		
 	}
 	catch(Exception ex){ex.printStackTrace();}
 	return arrayAccountObj;
 }
    
    
    public CustomerMasterObject getCustomer(int cid)throws RemoteException, CustomerNotFoundException {
       CustomerMasterObject cmObj=null;
       cmObj=this.custRemote.getCustomer(cid); 
       return cmObj;
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

    public static String getSysTime() {
            return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds());
    }
    public SignatureInstructionObject[] getSignature(int accno,String type)throws Exception{
   	 SignatureInstructionObject siObj[]=null;
   	 try{
   		siObj=getComRemote().getSignatureDetails(accno,type);
   	 }catch(Exception e){
            throw e;
   	 }  
   	 return siObj;
    }
    //ODCC Application DataEntry
    
    //to get Loan Percentage
    public double getLoanPercentage(String actype){
    	try{
    		loan_percentage=LDRemote.getLoanPercentage(actype);
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return loan_percentage;
    }
    
    //to get DepositAccounts for OD
    public AccountMasterObject[] getODDeposit(String shno){
    	try{
    		
    		array_account_master=fcRemote.getODDeposit(Integer.parseInt(shno));
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	return array_account_master;
    }
    
    
    public ODCCMasterObject odccData(String actyp,String acno){
    	try{
    		System.out.println("Inside delegate for ODCCMaster Object");
    odccmasterobject=fcRemote.getODCCMaster(Integer.parseInt(acno),String.valueOf(actyp.trim()));
    
    	}
    	catch(Exception e){e.printStackTrace();
    	return null;
    	}
    	return odccmasterobject;
    	}
    //TO GET DEPOSIT DETAILS====
    public DepositMasterObject getDepositMaster(String actype,String acno){
    	try{
    		tdMaster=fcRemote.getDepositMaster(actype,Integer.parseInt(acno),getSysDate());
    		//tdMaster=termRemote.getDepositMaster(actype,Integer.parseInt(acno));
    		
    	}
    	catch(Exception ex){ex.printStackTrace();
    	return null;}
    	
    	return tdMaster;
    }
    
    //ODCC Application DataEntry Ends
    //Methods for ViewLog.jsp Tables 
    public String[] sbcaColumnHeading()throws RemoteException,CreateException,ServiceLocatorException{
    	String columnheadings[]=getCommonRemote().getColumns("AccountMasterLog");
    	return columnheadings;
    }
    public String[] odccColumnHeading()throws RemoteException,CreateException,ServiceLocatorException{
    	String odcolumnheadings[]=getCommonRemote().getColumns("ODCCMasterLog");
    	return odcolumnheadings;
    }
public Object[][] viewLogSBCAData(int no,String modcode){
	/** get Rows return the row for specified cid */
    try {
		object_tabledata=getCommonRemote().getRows("AccountMasterLog","ac_no","ac_type",no,modcode,0);
		if(object_tabledata==null){System.out.println("At 207 in delegate viewLog");}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return object_tabledata; 
}

//for ODCC Log
public Object[][] viewLogODCCData(int no,String modcode){
	/** get Rows return the row for specified cid */
    try {
		object_tabledata=getCommonRemote().getRows("ODCCMasterLog","ac_no","ac_type",no,modcode,0);
		if(object_tabledata==null){System.out.println("At 207 in delegate viewLog");}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return object_tabledata; 
}
//ChequeWithdraw Start HERE------------------

public String chkTokenno(String tno) {
	try {
		if(!fcRemote.checkToken(Integer.parseInt(tno),getSysDate(),2))
		{		
		    System.out.println("token no"+Integer.parseInt(tno));
		    return "Token No. Does Not Exist";
		                                    
		}
		
		if(!fcRemote.checkToken(Integer.parseInt(tno),getSysDate(),1))
		{		
		    System.out.println("token no"+Integer.parseInt(tno));
		    return "Token No is already in use ";
		                                    
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
return "no";	
}

public String chkTokennoAgain(String tno) {
	try {
		if(!fcRemote.checkToken(Integer.parseInt(tno),getSysDate(),2))
		{		
		    System.out.println("token no"+Integer.parseInt(tno));
		    return "Token No. Does Not Exist";
		                                    
		}
		
		/*if(!fcRemote.checkToken(Integer.parseInt(tno),getSysDate(),1))
		{		
		    System.out.println("token no"+Integer.parseInt(tno));
		    return "Token No is already in use ";
		                                    
		}*/
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
return "no";	
}

//FOR PayOrder Withdrawal

public PayOrderObject getPOWithdrawal(String ponum){
	try{
		poObject=fcRemote.getPayOrderInstrn(Integer.parseInt(ponum));
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return poObject;
	
}

//---------------for updation and verification ----to get details for token nos.----

public ChequeObject gettokendetails(String tokenno){
try{
	chequeobject=fcRemote.getCashWithdraw(Integer.parseInt(tokenno),0,getSysDate());
}
catch(Exception ex){ex.printStackTrace();}
	return chequeobject;
}



public String[] chqWithdraw(String tno,String actyp,String acno,String combo_chq_slip_po_no)
{
	
	try {
		if(!fcRemote.checkChequeWithdrawal(actyp,Integer.parseInt(acno),Integer.parseInt(tno)))
		{							
		    accountinfoobject=fcRemote.getAccountInfo(actyp,Integer.parseInt(acno));
		
		    if(accountinfoobject!=null)
		    {
		    	System.out.println("here in delegate at 605--->acinfo not null");
		        if(accountinfoobject.getAccStatus().equals("O") && (accountinfoobject.getDefaultInd().equals("F") && accountinfoobject.getFreezeInd().equals("F")) )
		        	
		        { //&& accountinfoobject.getVerified()!=null
		         
		        	System.out.println("here in delegate at 610");
		        	if(actyp.startsWith("1014"))
		            {
		        		
		            	String date=fcRemote.checkInspectionDate(actyp,Integer.parseInt(acno));
		            	if(date!=null)
		            	{
		            		if(Validations.dayCompare(date,getSysDate())>=0)
		            		{
		            			return new String[]{"Inspection Not Done You can't continue "};
		            			
		            			
		            		}
		            	}
		            }
		        	if(accountinfoobject.getChequeIssued().trim().equals("T") && combo_chq_slip_po_no.equals("Slip"))
		            {
		        		System.out.println("here in delegate at 628");
		        		Vector vc=fcRemote.getChequeNoDet(actyp,Integer.parseInt(acno));
		        		if(vc!=null)
		        		{
		        			
		        			
		        			//return new String[]{"Cheque Issued Do you want continue with Slip ?"};
		        			return new String[]{accountinfoobject.getAccname(), String.valueOf(accountinfoobject.getAccno()),accountinfoobject.getAcctype()};
		        			
		        		}
		        		
		            }
		        	else{
		        		return new String[]{accountinfoobject.getAccname(), String.valueOf(accountinfoobject.getAccno()),accountinfoobject.getAcctype()};
		        		
		        		
		        		
		        	}
		        	if(actyp.startsWith("1014"))
		            {
		        		
		            	String date=fcRemote.checkInspectionDate(actyp,Integer.parseInt(acno));
		            	if(date!=null)
		            	{
		            		if(Validations.dayCompare(date,getSysDate())>=0)
		            		{
		            			return new String[]{"Inspection Not Done You can't continue "};
		            			
		            			
		            		}
		            	}
		            }
		        	String prev_bal=DoubleFormat.doubleToString(accountinfoobject.getAmount(),2);
		            /*lbl_current_balance.setText("");	                                    
		            lbl_shadow_balance.setText("");*/
		            String prev_trn_date=accountinfoobject.getLastTrnDate();
		            String acc_holder_name=accountinfoobject.getAccname();
				
		            if(actyp.startsWith("1014")||actyp.startsWith("1015"))
		            {
		                String credit_limit=DoubleFormat.doubleToString(accountinfoobject.getCreditLimit(),2);
		                String valid_upto=accountinfoobject.getLimitUpto();										
		            }	
				
		            /*if(jsp_signaturepanel.isVisible())
		                jsp_signaturepanel.setVisible(false);
		            
		            if(obj_signaturedetails.getTabCount()>0)
		                obj_signaturedetails.clear();
		            if(!actyp.equals("CA Br"))
		            	obj_signaturedetails.addCoBorrower(accountinfoobject.getSignatureInstruction(),accountinfoobject.getCategory());*/
		            
		            								
		        }
		        else if(accountinfoobject.getAccStatus().equals("C"))
		        {	
		           return new String[] {"Given Account is Closed"};
		            	                                    
		        }
		        else if(accountinfoobject.getAccStatus().equals("I"))
		        {	
		            return new String[]{"Given Account is InOperativeAccount"};
		            	                                    
		        }
		        else if(accountinfoobject.getVerified()==null)
		        {	
		            return new String[] {"Given Account is Not yet Verified"};
		            	                                    
		        }
		        else if(accountinfoobject.getDefaultInd().equals("T"))
		        {	
		            return new String[]{"Given Account is DefaultAccount"};
		          	                                    
		        }
		        else if(accountinfoobject.getFreezeInd().equals("T"))
		        {	
		           return new String[] {"Given Account is FreezedAccount"};
		            	                                    
		        }
		        
		    }
		    else if(!acno.equals("0") && acno.length()>0) 
		    {	
		        return new String[]{"Given AccountNumber Not Found"};
		        
		        //break here;
		    }
		    			
		
		
		
		}
		else
	    {
	        return new String[] {"Last Withdrawal is Pending"};	                            
	        
	        //break here;
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
return new String[]{"Values not correct"};
}

public String submitCheque(ChequeObject chequeobject,int del){
	try{
		
		int int_res=fcRemote.storeCashWithdraw(chequeobject,del);
		System.out.println("int_res----------->"+int_res);
		/*UserActivityMasterObject user_activ=new UserActivityMasterObject();
		user_activ.setUser_id(request.getAttribute("uid"));
    	user_activ.setTml_no(MainScreen.head.getTml());
    	user_activ.setPage_visit(MainScreen.ji.getTitle());
    	user_activ.setActivity(UserActivityHeading.getActivity(5));
    	user_activ.setAc_no(chequeobject.getAccNo());
    	user_activ.setCid(chequeobject.getCustomerId());
    	user_activ.setActivity_date(MainScreen.head.getSysDate());
    	user_activ.setActivity_time(MainScreen.head.getSysTime());
    	commonremote.storeUserActivity(user_activ);*/
		if(int_res==1){
			if(del==1)
				return "Withdrawal deleted successfully";
			else	
			return "Transaction Successful please verify the withdrawal";
			
		}
		else{
			if(del==1)
				return "Withdrawal deleted";
			else	
			return "Transaction Failed";
		}
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Operation Failed";
}

//to get UnPresented Cheques
public ChequeObject[] getUnpresentedCheques(String actyp,String acno)
{
try{
	chqIObject=fcRemote.showChequeNos(actyp,Integer.parseInt(acno));
}
catch(Exception ex){ex.printStackTrace();}
return chqIObject;
}



//for Verification
public String verifyChequeWithdraw(AccountTransObject accounttransobject){
	try{
int	int_res=fcRemote.verifyChequeWithDrawal(accounttransobject);
System.out.println("verifyChequeWithdrawal===========> "+int_res);
	 if(int_res==1)
	    {
	       return "ChequeWithdrawal is Verified Successfully";
	        
	    }
	    else if(int_res==0)					    
     	return "Main Cashier is Closed can't do any Transaction";        	           
	    else
	        return "ChequeWithdrawal Could  NOT BE VERIFIED";
	}
	catch(Exception ex){ex.printStackTrace();}
	return "OPERATION FAILED";
}
//to getCheque Object for Verification 
public ChequeObject getVerifyChqObject(String tokenno){
	
	try{
		chequeobject=fcRemote.getCashWithdraw(Integer.parseInt(tokenno),0,getSysDate());
		
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return chequeobject;
}

//to getInformation depending on token No. for verification
public void getDetailsfroVerification(String tokenno){
	try{
		chequeobject=fcRemote.getCashWithdraw(Integer.parseInt(tokenno),0,getSysDate());
		
	}
	catch(Exception ex){ex.printStackTrace();}
	
	
}

public void storeUserActivity(UserActivityMasterObject userActivity){
	try{
		//comRemote.store(userActivity);
		
	}
	catch(Exception ex){ex.printStackTrace();}
}
/*public void storeUserActivity(UserActivityMasterObject user_activ){
	try{
		getCommonRemote().storeUserActivity(user_activ);
	}
	catch(Exception ex){ex.printStackTrace();}
	
}*/

public int getCashStatus(){
	int int_daycash =0;
	try{
		int_daycash = comRemote.checkDailyStatus(getSysDate(),0);
	}
	catch(Exception ex){ex.printStackTrace();}
	
	
	return int_daycash ;
	
}

//to get Cheque details for Individual Accounts
public Vector getindividualChequeDet(String actype,String acno){
try{
	 vector_chqno_exists=fcRemote.CheckChqNo(0,Integer.parseInt(acno),actype,getSysDate());
}
catch(Exception ex){ex.printStackTrace();}
	return vector_chqno_exists;
}



//ChequeWithdraw End



//Cheque Issue Start

//to get Cheque Details
public String getAccountChequeDet(String actype,String acno,String chqno){
	
	try{
		 Vector vector_chequeobject=fcRemote.getChequeNoDet(actype,Integer.parseInt(acno),Integer.parseInt(chqno));
		
		    
			if(vector_chequeobject!=null)
			{
				Enumeration enumeration=vector_chequeobject.elements();
				if(enumeration.hasMoreElements())
				{
				    ChequeObject chequeobject_display=(ChequeObject)enumeration.nextElement();
					
				    if(chequeobject_display.getStop_payment().equals("T"))										
					{
						return "Cheque is Stopped cannot withdraw the amount";
						
					}
					else if(chequeobject_display.getCheque_Cancelled().equals("T"))
					{
						return "Cheque is Cancelled cannot withdraw the amount";
						
					}
					
					else if(chequeobject_display.getCheque_Del().equals("T"))
					{
						return "Cheque is already used cannot withdraw the amount";											
													
					}
					else if(chequeobject_display.getPost_Dated().equals("T"))
					{
						return "Cheque is PostDated cannot withdraw the amount";
													
					}
					else if(chequeobject_display.getPost_Dated().equals("T"))
					{
						return "Cheque is PostDated cannot withdraw the amount";
						
					}
					else{
						return "Alright";
					}
				    
				    /*else if(chequeobject_display.getVe_tml()==null || chequeobject_display.getVe_user()==null)
					{
						return "Cheque Not Verified";
						
					}*/
						
				}	//enumeration.hasMoreElements()																
			} //vector_chequeobject!=null
			else
			{								   
			    return "ChequeNo does not belong to this account.Cheque Details can be seen using Detals option";
			    
			}
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Exception Occured";
}





public String[] chqissue(String actyp,String acnum){
	
	try{
	AccountObject accountobject=comRemote.getAccount(null,actyp,Integer.parseInt(acnum),getSysDate());
	 
	 
		if(accountobject!=null)
		{
          // sd.addCoBorrower(accountobject.getSignatureInstruction(),accountobject.getCategory());
           
			if(accountobject.getAccStatus().equals("O") )
			{	
				
				name=accountobject.getAccname();
				int int_lstchqno=fcRemote.getLastChequeNo(1,String.valueOf(actyp),Integer.parseInt(acnum));
				//System.out.println("At 228 in delegate int_lstchqno "+int_lstchqno);
				if(int_lstchqno!=-1){
					chequeobject=fcRemote.getChqDetails(String.valueOf(actyp),Integer.parseInt(acnum));		
				
			
			if(chequeobject!=null){
				
			
			
			 chi1=accountobject.getAccname();
			 System.out.println("At 229 in Delegate chqIssue"+chi1);
			chi2=String.valueOf(chequeobject.getF_Chq_Prev());
			System.out.println("At 234 in Delegate chqIssue"+"chi2"+chequeobject.getF_Chq_Prev());
			chi3=String.valueOf(chequeobject.getBookNo());
			System.out.println("At 236 in Delegate chqIssue"+chi3);
			chi4=chequeobject.getRequestDate();
			System.out.println("At 238 in Delegate chqIssue"+chi4);
			chi5=String.valueOf(chequeobject.getNumLeaf());
			System.out.println("At 240 in Delegate chqIssue"+chi5);
			chi6=String.valueOf(chequeobject.getBookNo());
			System.out.println("At 242 in Delegate chqIssue"+chi6);
			chi7=String.valueOf(chequeobject.getFirstChequeNo());
			System.out.println("At 244 in Delegate chqIssue"+chi7);
			chi8=String.valueOf(chequeobject.getLastChequeNo());
			System.out.println("At 246 in Delegate chqIssue"+chi8);
			return new String[]{chi1,chi2,chi3,chi4,chi5,chi6,chi7,chi8};
			}
			//else return new String[]{"Cheques for this account number not found"};
			else return new String[]{"Cheques may have been issued! please check"};
			}
				else{
					//if chqint!=-1
					
					chequeobject=fcRemote.getChqDetails(String.valueOf(actyp),Integer.parseInt(acnum));		
					
					
					if(chequeobject!=null){
						
					
					
					 chi1=accountobject.getAccname();
					 System.out.println("At 229 in Delegate chqIssue"+chi1);
					chi2=String.valueOf(chequeobject.getF_Chq_Prev());
					System.out.println("At 234 in Delegate chqIssue"+"chi2"+chequeobject.getF_Chq_Prev());
					chi3=String.valueOf(chequeobject.getBookNo());
					System.out.println("At 236 in Delegate chqIssue"+chi3);
					chi4=chequeobject.getRequestDate();
					System.out.println("At 238 in Delegate chqIssue"+chi4);
					chi5=String.valueOf(chequeobject.getNumLeaf());
					System.out.println("At 240 in Delegate chqIssue"+chi5);
					chi6=String.valueOf(chequeobject.getBookNo());
					System.out.println("At 242 in Delegate chqIssue"+chi6);
					chi7=String.valueOf(chequeobject.getFirstChequeNo());
					System.out.println("At 244 in Delegate chqIssue"+chi7);
					chi8=String.valueOf(chequeobject.getLastChequeNo());
					System.out.println("At 246 in Delegate chqIssue"+chi8);
					return new String[]{chi1,chi2,chi3,chi4,chi5,chi6,chi7,chi8};
					}
					else return new String[]{"Cheques for this account number not found"};
					
					
					
				}	
				}
			else if(accountobject.getAccStatus().equals("C"))
			{	
				return new String[]{"Given account is Closed"};
				
			}
			else if(accountobject.getAccStatus().equals("I"))
			{	
				return new String[]{"InOperative Account"};
				
			}
			else if(accountobject.getVerified()==null)
			{	
				return new String[]{"Given account is not yet Verified"};
				
			}
			else if(accountobject.getDefaultInd().equals("T"))
			{	
				return new String[]{"Default account"};
				
			}
			else if(accountobject.getFreezeInd().equals("T"))
			{	
				return new String[]{"Freezed account"};
				
			}
		}
		else 
		{	
			return new String[]{"Given account number not found"};
			
		}
		
			}
	
	catch(Exception e){e.printStackTrace();}
	
	return new String[]{"Given account number not found"};
	}
	
		
//to store

public String storechqIssue(ChequeObject chequeobject,String val){
	try{
	if(val.equals("submit"))
        if(fcRemote.storeChqDetails(chequeobject,2)==1)
           return "Cheque details are stored ";
        else
            return "Cheque details are not stored ";
    
    if(val.equals("delete"))
        if(fcRemote.storeChqDetails(chequeobject,1)==1)
            return "Deleted ";
        else
            return "Not Deleted";
	}
	catch(Exception e){e.printStackTrace();}
	
return "cannot enter values";	
}
//Verification for cheque issue
public void verifyChequeIssue(String actyp,String chbookno,String chqleafno){
	try{
		String res=fcRemote.verifyChequeNo(actyp,Integer.parseInt(chbookno),Integer.parseInt(chqleafno));
	}
	catch(Exception ex){ex.printStackTrace();}
	
}


public String chequeIssueVerification(ChequeObject chqobj,String actype,String acno,String chqbookno,String fromChqno,String toChqno){
try{
	int reslt=fcRemote.verifyCheque(Integer.parseInt(acno),actype,Integer.parseInt(chqbookno),Integer.parseInt(fromChqno),Integer.parseInt(toChqno),chqobj.uv.getUserId(),chqobj.uv.getUserTml(),getSysDate()+""+getSysTime());
	if(reslt==1)
	return "Cheque Issue Successfully Verified";
	else
		return "Cheque Issue could not be verified";
}
catch(Exception ex){ex.printStackTrace();}
return "Cheque Issue could not be verified due to error";
}


//Cheque Issue End


//Query on Cheque



public String[] getChqDetails(String chqno){
	
	
	AccountObject array_accountobject[];
	String	account_no;

		try{
			array_moduleobject=comRemote.getMainModules(2,"1002000,1007000,1014000,1015000,1017000,1018000");
			array_accountobject=fcRemote.getChequeDet(Integer.parseInt(chqno));

			System.out.println("At 220 array_accountobject CID"+array_accountobject[0].getAccno());
			if(array_accountobject!=null)
			{	
				for(int i=0;i<array_accountobject.length;i++)
				{
					
					System.out.println(array_accountobject[i].getAccname()+"<---------->"+array_accountobject[i].getAccno());
					for(int j=0;j<array_moduleobject.length;j++)
						if(array_moduleobject[j].getModuleCode().equals(array_accountobject[i].getAcctype()))
						{	
							
						account_no=array_moduleobject[j].getModuleAbbrv()+"  "+array_accountobject[i].getAccno();
						System.out.println("At 1021 acno is "+account_no);
						if(account_no!=null){
							System.out.println("AcType"+String.valueOf(array_accountobject[i].getAcctype())+"Acno"+array_accountobject[i].getAccno()+"Chqno"+Integer.parseInt(chqno));
							vector_chequeobject = fcRemote.getChequeNoDet(String.valueOf(array_accountobject[i].getAcctype()),array_accountobject[i].getAccno(),Integer.parseInt(chqno));
							System.out.println("At 1025 in delegate");
							//Changed
							
							if(vector_chequeobject!=null)
							{
								System.out.println("vector object is not null");
								Enumeration enumeration=vector_chequeobject.elements();
								int k=0;
								
								
							while(enumeration.hasMoreElements())
								{	System.out.println("247 enumeration.hasMoreElements()");
									ChequeObject chequeobject=(ChequeObject)enumeration.nextElement();					
									
									System.out.println("A t 251"+array_accountobject[i].getAccname());
									System.out.println("At 249 in delegate");
									String string_data="";
									System.out.println("At 253");
									//chqdetail[0]="Mohsin";
									System.out.println("At 254");
										actypnum=chequeobject.getModuleType()+" "+chequeobject.getAccNo();
									
									//string_data+="\n\n  Cheque Book No	:  "+chequeobject.getBookNo();//Passbook number
									chbookno=String.valueOf(chequeobject.getBookNo());
									paystop="  "+(chequeobject.getStop_payment().equals("T")?"Yes	On :"+chequeobject.getCheque_IssueDate():"No");
									//chqdetail[3]=chequeobject.getStop_payment().equals("T")?"Yes	On :"+chequeobject.getCheque_IssueDate():"No";
									postdated=""+(chequeobject.getPost_Dated().equals("T")?"Yes	On :"+chequeobject.getCheque_IssueDate():"No");
									//chqdetail[4]=chequeobject.getPost_Dated().equals("T")?"Yes	On :"+chequeobject.getCheque_IssueDate():"No";
									expiry=""+chequeobject.getExpDate();
									
									//chqdetail[5]=chequeobject.getExpDate()==null?"":chequeobject.getExpDate();
									
									cancel=""+(chequeobject.getCheque_Cancelled().equals("T") ?"Yes	On :"+chequeobject.getCheque_IssueDate():"No");
									//chqdetail[6]=chequeobject.getCheque_Cancelled().equals("T") ?"Yes	On :"+chequeobject.getCheque_IssueDate():"No";
									deleted=""+(chequeobject.getCheque_Del().equals("T") ?"Yes	On :"+chequeobject.getCheque_IssueDate():"No");
									//chqdetail[7]=chequeobject.getCheque_Del().equals("T") ?"Yes	On :"+chequeobject.getCheque_IssueDate():"No";
									acname=array_accountobject[i].getAccname();
									
								}
							return new String[]{acname,actypnum,chbookno,paystop,postdated,expiry,cancel,deleted};
								/*jsp.setVisible(true);*/
								//System.out.println("At 278 chqdetail.length is"+chqdetail.length);
							}
							
							
							//changing
							//System.out.println("At 278 chqdetail.length is"+chqdetail.length);
							
						}
							
				}
			}
			System.out.println("At 284 chqdetail.length is"+chqdetail.length);	
			}
			else
			{	
				return new String[]{"Mohsin"};
				
			}		

		}catch(Exception exception_select){exception_select.printStackTrace();}
		

		//Vector vector_chequeobject;
	
	return chqdetail;
}

//Cheque Instruction Start

public String[] select(String chqno)
{
	System.out.println("At 578 in delegate"+chqno);
	try{array_moduleobject=comRemote.getMainModules(2,"1002000,1007000,1007001,1014000,1015000,1017000,1018000");
		array_accountobject=fcRemote.getChequeDet(Integer.parseInt(chqno));

		
		if(array_accountobject!=null)
		{	System.out.println("At 584 in delegate");
			for(int i=0;i<array_accountobject.length;i++)
			{
				for(int j=0;j<array_moduleobject.length;j++)
					if(array_moduleobject[j].getModuleCode().equals(array_accountobject[i].getAcctype()))
					{	
						
						chqinst=array_moduleobject[j].getModuleAbbrv()+" "+array_accountobject[i].getAccno();
						System.out.println("At 592 in delegate"+chqinst);
						 vector_chequeobject=fcRemote.getChequeNoDet(array_moduleobject[j].getModuleCode(),array_accountobject[i].getAccno(),Integer.parseInt(chqno));
						if(vector_chequeobject!=null)
						{
							Enumeration enumeration=vector_chequeobject.elements();
							while(enumeration.hasMoreElements())
							{
								chq=(ChequeObject)enumeration.nextElement();
								if(!chq.getCheque_Del().equals("T")){
									chbkno=String.valueOf(chq.getBookNo());
									System.out.println("At 602 in delegate"+chbkno);
									System.out.println("At 603 in delegate"+String.valueOf(chq.getAccNo()));
									return new String[] {chq.getModuleType(),String.valueOf(chq.getAccNo()),chbkno,chqno,chq.getExpDate(),chq.getCheque_IssueDate(),chq.getCheque_Payee(),DoubleFormat.doubleToString(chq.getChequeAmount(),2),chq.getCheque_Cancelled(),chq.getPost_Dated(),chq.getStop_payment()};
								}
								
							}
						}
						
					}
			}
		}
		else
		{	
			return new String[]{"Cheque No not Found"};
		}
		

	}catch(Exception exception_select){exception_select.printStackTrace();}
	return new String[]{"Cheque No not Found"};
}

public String storeIntoChequeObject(String chno,int chqInststop,int chqInstpost,int chqInstcancel)
{
	try{array_moduleobject=comRemote.getMainModules(2,"1002000,1007000,1007001,1014000,1015000,1017000,1018000");
	array_accountobject=fcRemote.getChequeDet(Integer.parseInt(chno));

	
	if(array_accountobject!=null)
	{	System.out.println("At 584 in delegate");
		for(int i=0;i<array_accountobject.length;i++)
		{
			for(int j=0;j<array_moduleobject.length;j++)
				if(array_moduleobject[j].getModuleCode().equals(array_accountobject[i].getAcctype()))
				{	
					
					chqinst=array_moduleobject[j].getModuleAbbrv()+" "+array_accountobject[i].getAccno();
					System.out.println("At 592 in delegate"+chqinst);
					 vector_chequeobject=fcRemote.getChequeNoDet(array_moduleobject[j].getModuleCode(),array_accountobject[i].getAccno(),Integer.parseInt(chno));
					if(vector_chequeobject!=null)
					{
						Enumeration enumeration=vector_chequeobject.elements();
						while(enumeration.hasMoreElements())
						{
							chq=(ChequeObject)enumeration.nextElement();
							if(!chq.getCheque_Del().equals("T")){
								chbkno=String.valueOf(chq.getBookNo());
								
								//Modified HERE
								chq.setPost_Dated(chqInstpost==0?chq.getPost_Dated():"T");
								chq.setStop_payment(chqInststop==0?chq.getStop_payment():"T");
								chq.setCheque_Cancelled(chqInstcancel==0?chq.getCheque_Cancelled():"T");
								
								chq.setExpDate(chq.getExpDate());
								//chq.setStop_payment(chq.getStop_payment());
//								//ps.setString(4,ch.getNext_ChequeDate());
								
								chq.setCheque_IssueDate(chq.getCheque_IssueDate());
								chq.setCheque_Payee(chq.getCheque_Payee());
								chq.setChequeAmount(chq.getChequeAmount());
								//chq.getStop_User();
								chq.setStop_User("Mohsin");
								//chq.getCheque_Cancelled();
								chq.setAlt_De_User("Mohsin");
								chq.setAlt_De_Date(getSysDate());
								System.out.println("At 666 in delegate");
								chq.setChqNo(chq.getChqNo());
								chq.setAccType(chq.getAccType());
								chq.setAccNo(chq.getAccNo());
								chq.setBookNo(chq.getBookNo());
								
								System.out.println("At 672 in delegate");
								System.out.println("At 673 in delegate"+String.valueOf(chq.getAccNo()));
								int k=fcRemote.updateChequeDet(chq);
								System.out.println("At 645 in Delegate K is"+k);
								return "Payment Stoped";
							}
							
						}
					}
					
				}
		}
	}
	else
	{	
		System.out.println("At 661 hi");
	}
	

}catch(Exception exception_select){exception_select.printStackTrace();}
	
	return "good";
	
	//HERE r Modifications
	/*try
{
		chq.getPost_Dated();
		chq.getExpDate();
		chq.getStop_payment();
//		ps.setString(4,ch.getNext_ChequeDate());
		
		chq.getCheque_IssueDate();
		chq.getCheque_Payee();
		chq.getChequeAmount();
		chq.getStop_User();
		chq.getCheque_Cancelled();
		chq.getAlt_De_User();
		chq.getAlt_De_Date();
		
		chq.getChqNo();
		chq.getAccType();
		chq.getAccNo();
		chq.getBookNo();
		
		
		
		
		
		
		
		
		//Code starts here
		array_accountobject=fcRemote.getChequeDet(Integer.parseInt(chno));
	//String[] selection=select(chno);
    if(selection!=null)
	{

		//chequeobject.setNext_ChequeDate((txt_cheque_next_date.getText().length()>0)?txt_cheque_next_date.getText():null);
		//chequeobject.setCheque_IssueDate((.length()>0)?selection[5]:null);

		//chequeobject.setChqDate((txt_cheque_date.getText().length()>0)?txt_cheque_date.getText():null);
		//chequeobject.setChqNo((chno.length()>0)?Integer.parseInt(chno):0);
		//chequeobject.setExpDate((selection[4].length()>0)?selection[4]:null );
		//chequeobject.setCheque_Payee((selection[6].length()>0)?selection[6]:null);
		System.out.println("At 636 in Delegate ");
		if(chqInstpost==1){
			chequeobject.setPost_Dated("T");
		}
		if(chqInststop==1){
			System.out.println("At 643 in Delegate");
			 ChequeObject chequeobject1=new ChequeObject();
			chequeobject1.setStop_payment("T");
			int k=fcRemote.updateChequeDet(chequeobject);
			System.out.println("At 645 in Delegate K is"+k);
			return "Payment Stoped";
		}
		if(chqInstcancel==1){chequeobject.setCheque_Cancelled("T");}
			chequeobject.setStop_User("Mohsin");//UID has to be updated

		chequeobject.setAlt_De_User("Mohsin");
		chequeobject.setAlt_De_Date(getSysDate());
		//chequeobject.setChequeAmount(Double.valueOf(selection[7]));
		//chequeobject.setAccNo(Integer.parseInt(selection[1]));
		//chequeobject.setAccType(chq.getAccType());
		//chequeobject.setBookNo(Integer.parseInt(selection[2]));
		int int_updateDet;

		
			int_updateDet=fcRemote.updateChequeDet(chequeobject);
			if(int_updateDet==1){
				return "Updated";
			}
		}catch(Exception exception_updateChequeDetails){exception_updateChequeDetails.printStackTrace();}
	//}
    return "Not Updated";*/
}



//Cheque Instruction End

//Passbook Starts here
public AccountInfoObject getAccountInfo(String acType,int acNo)throws Exception{
	AccountInfoObject acInfo=null;
	try{
		acInfo=fcRemote.getAccountInfo(acType, acNo);
	}catch(Exception e){
		throw e;
	}
	return acInfo;
}

public AccountTransObject[] sendpass(String actyp,String acno,String seqno,String fdate,String tdate){
	
	try{
		System.out.println("Inside Delegate==========Passbook");
		System.out.println("from date is "+fdate);
		System.out.println("To date is "+tdate);
	array_accounttransobject = fcRemote.PrintBook(actyp.trim(),Integer.parseInt(acno.trim()),fdate,tdate,Integer.parseInt(seqno.trim()),1);
}
catch(Exception e){	
e.printStackTrace();}

return array_accounttransobject;
}

//Passbook

//PassSheet Starts here
public AccountTransObject[] getPassSheet(String actyp,String acno,String fdate,String tdate){
	try{
		System.out.println("Inside getPassSheet in Delegate");
		
		array_accounttransobject = fcRemote.retrieveParseBook(1,actyp,Integer.parseInt(acno),fdate,tdate);
		
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return array_accounttransobject;
}


//PassSheet

//Interest Claculation Starts

public String Interestcal(String actyp,String acno){
	try{
		System.out.println("Inside delegate Interestcal Method");
	String int_date=fcRemote.getLastIntPaidDate(actyp,Integer.parseInt(acno));
	return int_date;
	}
	catch(Exception e){e.printStackTrace();}
	
	return null;}

public String calclulateInterest(String actyp,String acno,String todate,String nxtdate,String minibal,String user,String tml){
	String string_interest_calculated=null;
	try{
	FrontCounterDelegate fcDelegate=new FrontCounterDelegate();
	
	if(actyp.startsWith("1002")|| actyp.startsWith("1007"))

		string_interest_calculated=fcRemote.savingsInterestCalculation(actyp,Integer.parseInt(acno),Integer.parseInt(actyp),Double.parseDouble(minibal),nxtdate,user,tml,fcDelegate.getSysTime(),1);
	else
		string_interest_calculated=fcRemote.CalculateInterestAmountForODCC(actyp,Integer.parseInt(acno),Double.parseDouble(minibal.trim()),todate.trim(),nxtdate.trim(),1);
	
	/*if(actyp.startsWith("1002")|| actyp.startsWith("1007"))

		string_interest_calculated=fcRemote.savingsInterestCalculation(actyp,Integer.parseInt(acno),Integer.parseInt(actyp),Double.parseDouble(minibal),nxtdate,user,tml,fcDelegate.getSysTime(),1);
	else
		string_interest_calculated=fcRemote.ODCCInterestCalc(actyp,Integer.parseInt(acno),Double.parseDouble(minibal.trim()),"503","CA99",nxtdate.trim(),1);
	*/
	}
	catch(Exception e){e.printStackTrace();}
return string_interest_calculated;}
//for Recalculation
public String recalclulateInterest(String actyp,String acno,String todate,String nxtdate,String minibal,String user,String tml){
	String string_interest_calculated=null;
	try{
	FrontCounterDelegate fcDelegate=new FrontCounterDelegate();
	
	if(actyp.startsWith("1002")|| actyp.startsWith("1007"))

		string_interest_calculated=fcRemote.savingsInterestCalculation(actyp,Integer.parseInt(acno),Integer.parseInt(actyp),Double.parseDouble(minibal),nxtdate,user,tml,fcDelegate.getSysTime(),2);
	else
		string_interest_calculated=fcRemote.CalculateInterestAmountForODCC(actyp,Integer.parseInt(acno),Double.parseDouble(minibal.trim()),todate.trim(),nxtdate.trim(),2);
	}
	catch(Exception e){e.printStackTrace();}
return string_interest_calculated;}


public String checkIntDate(String actype,String preintdate){
try{
	if(actype.startsWith("1002") || actype.startsWith("1007"))
	{
		if(Validations.dayCompare(Validations.addNoOfMonths(preintdate,1),getSysDate())>=1){
			String [] ret_date=fcRemote.getNextIntDate(actype,getSysDate(),"H%");
			if(ret_date!=null){
				if(ret_date[0].equals("-1"))
					return "Interest Calcution Done only at half year end ";
					
				}
				else if(ret_date[0].equals("1")){
					//txt_next_interest_date.setText(ret_date[1]);
			}
		}
		else{
			//file_logger.info("txt prev int"+txt_interest_date.getText());
			return "Interest Calcution Done only at half year end ";
			
		}
	}
}
catch(Exception ex){ex.printStackTrace();}
	return null;
}

//Interest Calculation Ends


//Interest Posting Starts here
public String intPost(String actyp,String acno,String user,String tml){
try
{
	System.out.println("Account type in Delegate====> "+actyp);
	System.out.println("Account Number in Delegate====> "+acno);
	boolean status=false;
	Object[][] table_data=null;
	
	if(actyp.startsWith("1002") ||actyp.startsWith("1007"))
	{
		if((acno.trim().equals("") || acno.trim().equals("0")))
		{
			if(fcRemote.getIntPay(actyp)!=null)
			{
				
			}
			else
				return "Interest Not Calculated";
		}
		else
		{
			if(fcRemote.getIntPay("ip.ac_type='"+actyp+"' and ip.ac_no="+acno.trim()+" and ip.trn_date='"+getSysDate()+"'")!=null)
			{
				
			}
			else
				return "Interest Calculation not done ";
				
		}
	}
	else if((actyp.startsWith("1014") ||actyp.startsWith("1015")) )
	{
		odccintDetail=fcRemote.ODCCInterestDetails(getSysDate());
		if(odccintDetail!=null)
		{
			
		}
		else
			return "Interest Not Calculated .Details dont exist";
	}
	else{
		
		
	}
	
	
	
	
	//end
	if((actyp.startsWith("1014"))   || actyp.startsWith("1015") )
	{   
		odccintDetail=fcRemote.ODCCInterestDetails(getSysDate());
		
		System.out.println("At 1570 in Delegate--->"+odccintDetail.length);
		if(odccintDetail!=null){
		for(int c=0;c<odccintDetail.length;c++){
			if(acno.equals(String.valueOf(odccintDetail[c][1]))){
				acmatch=c;
			}
		}
		table_data=new Object[1][10];
		System.out.println("At 1577 in Delegate--->"+acmatch);
		table_data[0][0]=odccintDetail[acmatch][0];
		table_data[0][1]=odccintDetail[acmatch][1];
		table_data[0][2]=odccintDetail[acmatch][2];
		table_data[0][3]=odccintDetail[acmatch][3];
		table_data[0][4]=odccintDetail[acmatch][4];
		table_data[0][5]=odccintDetail[acmatch][5];
		table_data[0][6]=odccintDetail[acmatch][6];
		table_data[0][7]=true;
		table_data[0][8]=false;
		table_data[0][9]=odccintDetail[acmatch][9];
		}
		
		
		if(!acno.equals("0"))
			status=fcRemote.InterestPosting(tml,user,getSysDate()+" "+getSysTime(),"",Integer.parseInt(actyp),Integer.parseInt(acno),0,table_data);
		else
			status=fcRemote.InterestPosting(tml,user,getSysDate()+" "+getSysTime(),"",Integer.parseInt(actyp),0,0,table_data);
	}
	else{
		if(!acno.equals("0")){
			status=fcRemote.InterestPosting(tml,user,getSysDate(),"",Integer.parseInt(actyp),Integer.parseInt(acno),0,table_data);
		}
		else{
			System.out.println("At 1178 in Delegate");
			status=fcRemote.InterestPosting(tml,user,getSysDate(),"",Integer.parseInt(actyp),0,0,table_data);
			System.out.println("Status is=====> "+status);
		}
	}
	
	if(status)
		return "Interest Posted Successfully";
	else
		return "Interest Not Posted";
	
	
	}catch(Exception ex){JOptionPane.showMessageDialog(null,"Interest Post"+ex); ex.printStackTrace();}				
return "cannot be posted";
}
//Interest Posting Ends here



//AccountClosing


public AccountInfoObject toGetAccountInfo(String actype,String acno){
	try{
		accountinfoobject=fcRemote.getAccountInfo(actype,Integer.parseInt(acno));
	}
	catch(Exception ex){ex.printStackTrace();}
	return accountinfoobject;
}


public String checkCondition(String actyp,String acnum)
{
	try
	{
		System.out.println("Inside Check Conditionnnnnnnnnnnnnnn");
		
			String string_modulecode="";
			if(!actyp.equals(""))
				
			System.out.println("AC TYPE="+actyp);
			System.out.println("AC NO="+Integer.parseInt(acnum));
			accountinfoobject=fcRemote.getAccountInfo(actyp,Integer.parseInt(acnum));
			
			if(accountinfoobject!=null)
			{
				System.out.println(Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())+" "+accountinfoobject.getAmount());
                
				System.out.println("accountinfoobject.getAccStatus()"+accountinfoobject.getAccStatus());
				System.out.println(accountinfoobject.getDefaultInd().equals("F"));
				System.out.println(accountinfoobject.getFreezeInd().equals("F"));
				System.out.println("verified"+accountinfoobject.getVerified());
				System.out.println(Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate()));
				System.out.println(Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate()));
				System.out.println( accountinfoobject.getAmount());
				System.out.println(accountinfoobject.getAccStatus().equals("O") && 
						   accountinfoobject.getDefaultInd().equals("F")&&
						   accountinfoobject.getFreezeInd().equals("F") &&
						   accountinfoobject.getVerified()!=null &&
						   (Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())==-1 || Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())<=1) &&
						   accountinfoobject.getAmount()==0);
				System.out.println(accountinfoobject.getAmount()==0);
				System.out.println("**** end***");
				
				
				if(accountinfoobject.getAccStatus().equals("O") && 
				   accountinfoobject.getDefaultInd().equals("F")&&
				   accountinfoobject.getFreezeInd().equals("F") &&
				   accountinfoobject.getVerified()!=null &&
				   (Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())==-1 || Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())<=1) ||
				   accountinfoobject.getAmount()==0)
				{System.out.println("At 232 in delegate name is "+accountinfoobject.getAccname());
					String string_address="";

					string_address+="Name   : "+accountinfoobject.getAccname();
					string_address+="\nAddress:";
					if(accountinfoobject.addr.getAddress()!=null)
						string_address+="\n"+accountinfoobject.addr.getAddress();
					if(accountinfoobject.addr.getCity()!=null)
						string_address+="\n"+accountinfoobject.addr.getCity();
					if(accountinfoobject.addr.getState()!=null)
						string_address+="\n"+accountinfoobject.addr.getState();
					string_address+="\n"+accountinfoobject.addr.getPin();
					
                    
					if(accountinfoobject.getChequeIssued().equals("T"))
					{
						vector_chqno_exists=null;
						vector_chqno_exists=fcRemote.getChequeNoDet(actyp,Integer.parseInt(acnum));
                        System.out.println("i m here");
						if(vector_chqno_exists!=null && vector_chqno_exists.size()>0)
						{
							System.out.println("NEWWWW Value=========="+vector_chqno_exists.size());							
							Enumeration enumeration=vector_chqno_exists.elements();							
							ChequeObject chequeobject_display=(ChequeObject)enumeration.nextElement();
							System.out.println("Cheque Cancel=="+chequeobject_display.getCheque_Cancelled());
                            
							if(chequeobject_display.getCheque_Cancelled().equals("F"))
							{
								//int int_chq_confirmation=JOptionPane.showConfirmDialog(null,"Cheques Not Surrendered.Do U want to Continue?");
                                
								return "Cheques Not Surrendered.Do U want to Continue? if yes click on close button";
								
							
						}
						else{
							return "Cheque book issued but there are no details of cheques in the db\n Do you want to close still ?if yes click on close button";
							/*jsp_table.setVisible(true);*/
							/*lbl_ensure.setVisible(true);*/
							
						}
					}
					
					}	
					
				}
				else if(accountinfoobject.getAccStatus().equals("C"))
				{
							
					return "Given account is Closed";
				}
				else if(accountinfoobject.getAccStatus().equals("I"))
				{
							
					return "InOperative Account";
				}
				else if(accountinfoobject.getVerified()==null)
				{
									
					return "Given account is not yet Verified";
				}
				else if(accountinfoobject.getDefaultInd().equals("T"))
				{
					
					return "Default account";
				}
				else if(accountinfoobject.getFreezeInd().equals("T"))
				{
					
					return "Freezed account";
				}
                
				if(Validations.dayCompare(accountinfoobject.getInterestPayableDate(),getSysDate())>1)
				{
					
                    
					if(accountinfoobject.getAcctype().startsWith("1002") ||accountinfoobject.getAcctype().startsWith("1007")|| accountinfoobject.getAcctype().startsWith("1017")||accountinfoobject.getAcctype().startsWith("1018"))
						return "Interest is Paid only till "+accountinfoobject.getInterestPayableDate();
					else if(accountinfoobject.getAcctype().startsWith("1014") ||accountinfoobject.getAcctype().startsWith("1015"))
						return "Interest Collected only till "+accountinfoobject.getInterestPayableDate();
				}
                
				if(accountinfoobject.getAmount()!=0)
				{
					
                    
					if(accountinfoobject.getAcctype().startsWith("1002") ||accountinfoobject.getAcctype().startsWith("1007")|| accountinfoobject.getAcctype().startsWith("1017")||accountinfoobject.getAcctype().startsWith("1018"))
						return accountinfoobject.getAccname()+" is having Rs. "+accountinfoobject.getAmount()+" in his/her Account Please withdraw the Amount then Close";
					else
						return accountinfoobject.getAccname()+" is having Rs. "+accountinfoobject.getAmount()+" in his/her Account";
				}
			}
			else //if(accountinfoobject==null)
			{
				return "Given account not Found";
				
            
			
	}
    
}
	catch(Exception exp){exp.printStackTrace();}
	return "good";
}

public String accountClose(String actyp,String acnum){
	try{
	if(fcRemote.accountClose(actyp,Integer.parseInt(acnum),getSysDate()))
	{	
		return "Account Closed Successfully";
		
	}
	else
		return "Unable to close the Account";
	}
	catch(Exception e){
		
		e.printStackTrace();
	}
	
return "Unable to process";
}
//**************end of Account Closing

//SBCS ADMIN STRATS HERE
public AdminObject[] getColnames(String selected){
	
	try{
		admin_object=fcRemote.getData(selected.trim());
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
return admin_object;	
}

public String insertData(AdminObject[] admin_object,String selected,int up){
	int insert=0;String str="NOT UPDATED";
	
	try
	{
		if(up==0){
			 insert =fcRemote.insertRecords(admin_object,selected.trim());
			if(insert==-2)
				return "Can't insert Row from date is less than other entry ";
			else 
			{
				
				return "  Rows Inserted Successfully";
				
			}}
		else{
		
		
			int update =fcRemote.updateRecords(admin_object,selected.trim());
			if(update==-2)
				return "Can't Update to date falls between other entries";
			else
			{
				
				return "Data updated successfully";
				
			
		}}
			
	} catch(Exception ae){ae.printStackTrace(); JOptionPane.showMessageDialog(null,"No of  Rows Inserted "+insert);}
	return str;
	
}

//SBCA ADMIN ENDS HERE


//*************CCPERIODIC INS STARTS HERE************

public StockDetailsObject getCCPeriodicDetails(String actyp,String acno){
	try{
	stockdetailsobject=fcRemote.getCashCreditDetails(Integer.parseInt(acno),actyp);
	}
	catch(Exception e){e.printStackTrace();}
	return stockdetailsobject;
}
public String[] onStockvalue(String actyp,String stockval,String acno){
	try{
	Double lnkshares=fcRemote.getPercentage(actyp,getSysDate());
    Double amtsanc=Double.parseDouble(stockval);
    Double credit_lmt=amtsanc*lnkshares/100;
    if(credit_lmt>=stockdetailsobject.getSanctionedLimit())
       return new String[] {String.valueOf(getCCPeriodicDetails(actyp,acno).getSanctionedLimit())};
    else
        return new String[] {String.valueOf(credit_lmt),Validations.addNoOfMonths(getSysDate(),stockdetailsobject.getPeriod())};
	//lbl_nxt_insp_date.setText(Validations.addNoOfMonths(MainScreen.head.getSysDate(),stockdetailsobject.getPeriod()));
	}
	catch(Exception e){e.printStackTrace();}
	return new String[] {"Cannot calculate"};
}

public String onSubmitting(StockDetailsObject stockobj){
	try{
	if(fcRemote.storeStockDetails(stockdetailsobject)){
		return "Details Stored successfully"; 
	}
	else {return "Details Could not be stored";}
	}
	catch(Exception e){e.printStackTrace();}
	return "Details Not Stored";
}
//************************CCPERIODIC ENDS HERE***********

//********************Inoperative Process-Strats---------------------

public AccountMasterObject[] getInopdata(String actyp){
	
	
	try{
		
		array_account_master=fcRemote.getInoperativeRecords(actyp,getSysDate());
		
	}
	catch(Exception e){e.printStackTrace();}
	return array_account_master;
}

public int makeInoperative(AccountMasterObject[] accountmobj){
	try{
		int int_inoperative_acno=fcRemote.storeInoperativeAccounts(accountmobj);	
		return int_inoperative_acno;
	}
	catch(Exception e){e.printStackTrace();}
	return 0;
}
//--------------------------Inoperative process ends here--------------

//PO ADMIN starts here open HERE************************

//Module code for CC

public ModuleObject[] getPoModule(int type)throws RemoteException{
    /*
     type 0 for SB and CA account type
     type 1 for introducer account type
     */
    if(type==0){
        comboElements=getComRemote().getMainModules(2,"1016000");
    }
    else if(type==1){
        comboElements= getComRemote().getMainModules(2,"1016001");
    }
    return comboElements;
}




public AccSubCategoryObject[] subCat(String cat){
	
	try
    { 
        acc_subcat=comRemote.getAccSubCategories(Integer.parseInt(cat));//Sub categories 
        
        /*for(int i=0;i<acc_subcat.length;i++)
        {
            combo_subcat.addItem(acc_subcat[i].getSubCategoryDesc());
        }*/
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
	return acc_subcat;
}
public AccCategoryObject[] accCat(){//AccountCATAGORIES
	
	try
    {
        acc_cat=comRemote.getAccCategories();//Main Categories like 0,1,2
        
      for(int i=0;i<acc_cat.length;i++)
        {
            System.out.println(acc_cat[i].getCategoryDesc());
        }
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return acc_cat;
} 
   public PayOrderObject[] getPOdetails(String actyp,String cat,String subcat){
    	
//payorderobject = null;
        
        try
        {
        	System.out.println("actype==>"+actyp);
        	System.out.println("cat==>"+cat);
        	System.out.println("subcat==>"+subcat);
          payorderobject = fcRemote.getPOCommissionDetails(actyp,Integer.parseInt(cat.trim()),Integer.parseInt(subcat.trim()));
          System.out.println("Inside delegate getPOdetails");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    	return payorderobject;
    }
public String storePODetails(int insert_flag ,PayOrderObject[] payorderobject){
   try
   {
       //To Store the details
       if(insert_flag==1 || insert_flag==3)
       {                                      
           if(fcRemote.storePOCommissionDetails(payorderobject,1))
           {
               insert_flag=0;
               return "Rows Inserted Successfully";
              
           } 
           else
           {
               return "Error : Can't insert";
           }
       }
       //To update the details
       else if(insert_flag==2)
       {
           if(fcRemote.storePOCommissionDetails(payorderobject,2))
           {
               insert_flag=0;
               return "Rows Updated ";
               
           } 
           else
           {
              return "Error : Can't update";
           }
       }
   } 
   catch(Exception ae)
   {
       ae.printStackTrace(); 
       return "Error : Can't insert/update";
   }
   
   return "Operation cannot be performed";
}
//----------------PO ADMIN ENDS HERE--------------------
//PO Data Entry Starts Here======================
/*public void custType(){
	
	accsubcategoryobject=commonremote.getAccSubCategories(0);
	
	
}*/

//to get PO Details

public PayOrderObject getPOStoreDetails(String pono){
	try{
		System.out.println("inside delegate ==>method===>getPOStoreDetails");
		payorderobj=fcRemote.getPayOrder(Integer.parseInt(pono));
	}
	catch(Exception ex){ex.printStackTrace();}
	return payorderobj;
}



public GLMasterObject[] getGL(){
	try{
	array_glmasterobject=fcRemote.getGLMasterDetails(getSysDate());
	}
	catch(Exception e){e.printStackTrace();}
	return array_glmasterobject;
}
public int setPODetails(PayOrderObject payoorderobject,int del){
	
	try{
     int int_po_stored=fcRemote.storePayOrder(payoorderobject,del);
     return int_po_stored;
    /*else
        int_po_stored=frontcounterremote.storePayOrder(payoorderobject,1);*/
	}
	catch(Exception ex){ex.printStackTrace();}
	return 0;
}

public String verifyPayOrder(PayOrderObject payoorderobject){
	try{
		int i=fcRemote.verifyPOEntry(payoorderobject);
		if(i==1){
			return "PO Voucher created and verified successfully";
		}
			
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return "PO could not be verified";
}
/*public  int[] getFcGLCodes(String date,int type)
{	
	
	System.out.println("--->>>>--->>>"+date);
	System.out.println("--->>>>--->>>"+type);
	try
	{ 
	   array_int_glcodes = cashRemote.getGLCodes(date, type);
	   
	   System.out.println("--->>>>--->>>*********"+array_int_glcodes);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}  
	return array_int_glcodes;      
}*/


//PO COMISSIOM
public double poComission(String actyp,String subcat,String amount){
	try{
double comm = fcRemote.getCommission(actyp,Integer.parseInt(subcat),Double.parseDouble(amount),getSysDate());
System.out.println("comm >> "+comm);
return comm;
/*if(comm<0)
{
    JOptionPane.showMessageDialog(null,"PO Commission not defined");
   // txt_comission_amt.setText("0.00");
    //return -1; 
}
else
{
   // txt_comission_amt.setText(String.valueOf(comm));
} */ 
	}
	catch(Exception ex){ex.printStackTrace();}
	return 0.0;
}
/*
 * 
double double_amt_to_pay=Double.parseDouble(txt_pay_amount.getText())+Double.parseDouble(txt_comission_amt.getText());

lbl_current_balance.setText(DoubleFormat.doubleToString( (accountinfoobject.getAmount()-Double.parseDouble(txt_pay_amount.getText())+Double.parseDouble(txt_comission_amt.getText())),2));

string_modulecode=array_moduleobject[combo_account_type.getSelectedIndex()-1].getModuleCode();//ship......28/06/2007
    
if((string_modulecode.startsWith("1002") || string_modulecode.startsWith("1007") || string_modulecode.startsWith("1017") || string_modulecode.startsWith("1018"))?
   (accountinfoobject.getAmount()-double_amt_to_pay)<array_moduleobject[int_index_of_accounttype].getMinBal():
   ((accountinfoobject.getCreditLimit()+accountinfoobject.getAmount())-double_amt_to_pay)<0)
{
    if( string_modulecode.startsWith("1007") || string_modulecode.startsWith("1002") || string_modulecode.startsWith("1017") || string_modulecode.startsWith("1018"))
    {                             
        JOptionPane.showMessageDialog(null,"Required Amount should be greater than Minimum Balance Rs. "+array_moduleobject[int_index_of_accounttype].getMinBal());
        return -1;                  
    }
    else if(((accountinfoobject.getCreditLimit()+accountinfoobject.getAmount())-double_amt_to_pay)<0)
    {
        JOptionPane.showMessageDialog(null,"Required Amount should be greater than Credit Limit");                             
        return -1;
    }
}
else if(( string_modulecode.startsWith("1014") || string_modulecode.startsWith("1015"))?
        Validations.validDate(accountinfoobject.getLimitUpto(),MainScreen.head.getSysDate())==-1:false)
{
    JOptionPane.showMessageDialog(null,"Yours Limit Upto was only till"+accountinfoobject.getLimitUpto());
    return -1;
}
else                    
{   
    //txt_comission_amt.setText(String.valueOf(frontcounterremote.getCommission(array_moduleobject[int_index_payorder].getModuleCode(),accsubcategoryobject[combo_customer_type.getSelectedIndex()-1].getSubCategoryCode(),Double.parseDouble(txt_pay_amount.getText()),MainScreen.head.getSysDate())));
    lbl_current_balance.setText(DoubleFormat.doubleToString( (accountinfoobject.getAmount()-Double.parseDouble(txt_pay_amount.getText())+Double.parseDouble(txt_comission_amt.getText())),2));
    lbl_shadow_balance.setText("-"+DoubleFormat.doubleToString( Double.parseDouble(txt_pay_amount.getText())+Double.parseDouble(txt_comission_amt.getText()),2));
}
}
else
{
combo_customer_type.requestFocus();
JOptionPane.showMessageDialog(null,"Select Customer Type");
return 0;
}
}
else
{
txt_comission_amt.setText("0.00");
}*/
////////////
//------------------------------------to submit payordervalues
/*
if(actionevent.getSource()==button_submit)
    int_po_stored=frontcounterremote.storePayOrder(payoorderobject,0);
else
    int_po_stored=frontcounterremote.storePayOrder(payoorderobject,1);*/
//PO Data Entry Ends here



//PO Instruction Starts here
public PayOrderObject poInstruction(String chqno){
	try{
	 payorderobj=fcRemote.getPayOrderInstrn(Integer.parseInt(chqno.trim()));
	}
	catch(Exception ex){ex.printStackTrace();}
	return payorderobj;
}
public String poSubmit(int inst,String chno,String valid,String instruction){
	try{
	if(inst==1){
		//instruction=stop
		posub=fcRemote.setPayOrderInstrn(1,instruction.toString().equals("Yes")?"T":"F",Integer.parseInt(chno.trim()),valid.trim(),null,null);
		if(posub==1){
			return "PayOrder Stopped";
		}
		else{
			
			return "not stopped";
		}
	}
	if(inst==2){
		//instruction=cancel
		posub=fcRemote.setPayOrderInstrn(2,instruction.toString().equals("Yes")?"T":"F",Integer.parseInt(chno.trim()),valid.trim(),"tml","uid");
if(posub==1){
			return "PayOrder Canceled";
		}
		else{
			return "PayOrder could not be Canceled";
			
		}
	}
	if(inst==3){
		
		//instruction=stop
		posub=fcRemote.setPayOrderInstrn(3,instruction.toString().equals("Yes")?"T":"F",Integer.parseInt(chno.trim()),Validations.checkDate(valid.trim()),null,null);
if(posub==1){
	return "PayOrder Revalidated";
		}
		else{
			
			return "Not Revalidated";
		}
	}
	}
	catch(Exception ea){ea.printStackTrace();}
	return "Operation Failed";
}
//PO Instruction ends here

//PO Consolidation
//to check po cheque no

public String checkPOChequeno(String pochqno){
	try{
		if(fcRemote.checkPayOrderChequeNo(Integer.parseInt(pochqno.trim())))
		{
			return "Pay ChequeNo already exists";
			
		}
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return null;
}
//To get pomake Details
public PayOrderObject[] getPOMake(String pono){
	try{
		payorderobject_view=fcRemote.getPOMake(Integer.parseInt(pono));
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return payorderobject_view;
}

//TO getUnverified PO numbers

public Object[][] getUnverifiedPOConsol(){
	try{
		String colname[]=new String[4];
        colname[0]="pay.payord_no";
        colname[1]="payee_nm";
        colname[2]="po_chq_no";
        colname[3]="trn_amt";
        unverifiedpo=comRemote.getHelpDetails("PayOrder pay,POLink po",colname,"pay.ve_user is null and pay.ve_tml is null and pay.ve_date is null and pay.payord_no=po.payord_no and pay.payord_dt='"+getSysDate()+"'");
	}
	
	catch(Exception ex){ex.printStackTrace();}
	return unverifiedpo;
}

//for verification of poconsolidation

public PayOrderObject[] poConsolDetails(String pono)
{
try{
	payorderobject_view=fcRemote.getPOMake(Integer.parseInt(pono));
	
}
catch(Exception ex){ex.printStackTrace();}
return payorderobject_view;
}
//===============for verification of consolidated PayOrder===========
public String verifyConsolidatedPO(PayOrderObject poObj){
try{
	int int_payorder_no=fcRemote.verifyPayOrder(poObj);
	if(int_payorder_no>0)
        return "PayOrder is Verified";
    else
        return "PayOrder Not Verified";
}
catch(Exception ex){ex.printStackTrace();}
	
return "Operation failed";
}

public PayOrderObject[] poConsol(){
	try{
	payorderobject_view=fcRemote.getPOMake(0);
	}
	catch(Exception ex){ex.printStackTrace();}
	return payorderobject_view;
}
public String storePOConsolidation(PayOrderObject[] poObj,int updt){
	//if PO no. is not ZERO
	try{
		if(updt==1){
       int int_payorder_no=fcRemote.storePOLink(poObj,1,0);
        if(int_payorder_no>0)
            return "Pay Order No"+int_payorder_no+"is Updated";
        else
            return "PayOrder No Not Updated";
		}
		else if(updt==0){
		int	int_payorder_no=fcRemote.storePOLink(poObj,0,0);
	        if(int_payorder_no>0)
	            return "PayOrder Successfully created and Pay Order No is "+int_payorder_no;
	        else
	            return "PayOrder No Not Created";
			
			
		}
	}
	catch(Exception ex){ex.printStackTrace();}
   return "operation failed";
}
	
	//ifPO no is ZERO
	/*if(int_reslt==JOptionPane.YES_OPTION)
    {
        int_payorder_no=frontcounterremote.storePOLink(array_payorderobject_consolidate,0,0);
        if(int_payorder_no>0)
            JOptionPane.showMessageDialog(null,"Pay Order No is "+int_payorder_no);
        else
            JOptionPane.showMessageDialog(null,"PayOrder No Not Created");
        clearForm();
    }*/
	

//PO Consolidation ends here



//ODCC SANCTION STARTS HERE
/*******odccdata method is called for master object*********/
public void loanDetails(String shtyp,String shacno){
	try{
		Object obj[][]=fcRemote.getLoanDetails(shtyp,Integer.parseInt(shacno));
	}
	catch(Exception ex){ex.printStackTrace();}
}

public String storeodccSanction(ODCCMasterObject odccmasterobject,boolean priority,boolean weak){
	try{
	int  res=fcRemote.sanctionODCC(odccmasterobject,priority,weak,getSysDate(),0);
	if(res==1){
		return "Loan Sanctioned Successfully";
	}
	return "Unable to sanction Loan";
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Operation Failed";
	}


public String verifyODCCSanction(ODCCMasterObject odccmasterobject,int ver){
	
	//ver=1=============>cancel
	//ver=0=====>for verification 
	try{
		
		int re=fcRemote.verifyODCCSanction(odccmasterobject,ver);
		if(ver==0){
			if(re!=0){
				return "Loan Sanction Successfully Verified";
				
			}
			else{
				return " sorry !! Unable to verify";
			}
		}
		else if(ver==1){
			if(re!=0){
				return "Loan Sanction Successfully Canceled";
				
			}
			else{
				return "Unable to cancel";
			}
		}
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Operation failed";
}


//to get getpercentage i.e linking shares
public Double getPercentageShare(String actype){
	try{
		sharepercentage=loanRemote.getSharePercentage(actype);
	}
	catch(Exception ex){ex.printStackTrace();}
	return sharepercentage;
}

public String getDepositMaturity(String actype,String acno,String limit){
	
	try{
	//	getDepositMaster()
		odccMaster=odccData(actype,acno);
		if(odccMaster!=null){
			limit=Validations.addNoOfMonths(getSysDate(),Integer.parseInt(limit));
			v=odccMaster.getDeposits();
			if(v!=null){
			for(int i=0;i<v.size();i++){
			System.out.println("Account type----->   "+v.get(i).toString().substring(0,7));
			
			System.out.println("Account No----->   "+v.get(i).toString().substring(8));
			dpMaster=getDepositMaster(v.get(i).toString().substring(0,7),v.get(i).toString().substring(8));
			if(dpMaster!=null){
				if(Validations.checkDateValid(dpMaster.getMaturityDate(),limit)==-1)
                {
                	return "Deposit Maturity Date "+dpMaster.getMaturityDate()+" is less than Limit Upto";
                   
                }
				
			}
			}
			}
		}
		
	}
	catch(Exception ex)
	{ex.printStackTrace();}
	return null;
}

public String getODCCIntRate(String actype,String acno,String limit,String credit,String subcat){
try{
	String str=String.valueOf(fcRemote.getODCCIntRate(actype,getSysDate(),getSysDate(),Integer.parseInt(subcat),Integer.parseInt(limit),Double.parseDouble(credit.trim()),Integer.parseInt(acno)));
	return str;
}
catch(Exception ex){ex.printStackTrace();}
return null;
}

//ODCC SANCTION ENDS HERE
//ODCC Application--------Starts her------------------

public int verifyODCCMaster(AccountTransObject accounttransobject){
	try{
		odccverify=fcRemote.verifyODCCMaster(accounttransobject);
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return odccverify;
}




public Object[][] odccLoanDetail(String sh,String acno){
	try{
	Object[][] loanDetail=fcRemote.getLoanDetails(sh,Integer.parseInt(acno));
	
	}
	catch(Exception ex){ex.printStackTrace();}
	return loanDetail;
}

//To get Loan Purpose
public LoanPurposeObject[] loanpurpose(){
	try{
		loanpurposeobject=loanRemote.getLoanPurposes();
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return loanpurposeobject;
}

//to get ODCC AccountObject
/*public AccountObject getAccountObject(String sharetype,String shno){
	try{
		accountobjectarray=new AccountObject[1];
		accountobjectarray[0]=comRemote.getAccount(null,sharetype,Integer.parseInt(shno),getSysDate());
	}
	catch(Exception ex){ex.printStackTrace();}
	accountobjectarray[0];
}
*/
public ModuleObject[] getLoanMod(int type)throws RemoteException{
    /*
     type 0 account type
     type 1 for introducer account type
     */
    if(type==0){
        comboElements=getComRemote().getMainModules(3,"1010000");
    }
    else if(type==1){
        comboElements= getComRemote().getMainModules(2,"1015001,1014001");
    }
    return comboElements;
}


//to submit values into Master
public String createOdccAc(ODCCMasterObject odccmasterobject,int a){
	
	try{
	int int_stored=fcRemote.storeODCCMaster(odccmasterobject,a);
	if(a==0){
		if(int_stored!=0)
			return "Account Succesfully Created/updated and account No. is "+int_stored;
		else
			return "Account could not be created/updated ";
	}
	if(a==1){
		if(int_stored!=0)
			return "Account No.  "+int_stored+"  successfully deleted";
		else
			return "Account could not be deleted";
	}
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Account could not be created==============At End";
}
//to get Recript details on giving scroll no.
public AccountObject receiptDetailsOnScroll(String actype,String scrollno)
{
try{
	accountobject=comRemote.getAccount("C",String.valueOf(actype),Integer.parseInt(scrollno),getSysDate());
}	
catch(Exception ex){ex.printStackTrace();}

return accountobject;
}

//to get Minimum balance
public void getminbal(){
	
	try{
		comboElements=getComRemote().getMainModules(3,"1010000");
		
	}
	catch(Exception ex){ex.printStackTrace();}
}
//to check for Relevent details
public void getRevelantDetails(String actype){
	try{
		fcRemote.getReleventDetails(actype);
	}
	catch(Exception ex){ex.printStackTrace();}
	
}
//ODCC Application Ends

//EXTRAS

public NomineeObject[] getNomineeDeatils(AccountMasterObject accountmasterobject)
{
	  
	  System.out.println("-------Inside Nominee Delegate----------");
	  NomineeObject noObj[]=null;
	  
	  try{
		  if(noObj!=null){
		  noObj =  accountmasterobject.getNomineeDetails();
		  System.out.println("The value of Nominee==="+noObj);
		  }
	  }
	  catch(Exception e)
	  {
		   e.printStackTrace();
	  }
	  System.out.println("-------OutSide Nominee Delegate");
	  return noObj;
}
//Signature

//SB Opening Form
public SignatureInstructionObject[] getSigDetails(int accno,String type) 
{
	System.out.println("-------Inside Signature Delegate1--------");
	try{
		System.out.println("-------Inside Signature Delegate2--------");
	nsignatureobject = getComRemote().getSignatureDetails(accno,type);
	System.out.println("The Value of sign"+nsignatureobject);
	}
	catch(Exception ex){ex.printStackTrace();}
	return nsignatureobject;
}
/*public void storeUserActivity(){
try{
	
	commonremote.storeUserActivity(user_activ);
}
catch(Exception ex){ex.printStackTrace();}	
	
}*/

public int storeAccountMaster(AccountMasterObject am,int updated)
{
System.out.println("----------Inside Store Account Master Delegate---------");

int insert=0;
try
{
  insert = fcRemote.storeAccountMaster(am,updated);
}
catch(Exception e)
{
	e.printStackTrace();
}
return insert;
}

public int sbVerify(AccountTransObject accounttransobject){
	try{
	int result=fcRemote.verifySB(accounttransobject);
		return result;
	}
	catch(Exception ex){ex.printStackTrace();}
	return 0;
}
//to get unverified Accounts
public String[][] getunverified(String mod_code){
	try{
		unverified=fcRemote.getUnverifiedAccounts(mod_code,getSysDate());
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return unverified;
}


//FOR INTEREST REGISTER======

public IntPayObject[] fcInterestRegister(){
	try{
		
		array_intpayobject=fcRemote.getIntPay(null);
		
	}
	catch(Exception ex){ex.printStackTrace();}
	return array_intpayobject;
}

//FOR UNCASHED PO REPORTS============
public PayOrderObject[] RetrieveCashUncash(String tdate,String fdate){
	try{
		poreport=fcRemote.RetrieveCashUncash(fdate, tdate,0,null);
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return poreport;
}
//FOR CASHED PO REPORT

public PayOrderObject[] RetrieveCashed(String tdate,String fdate){
	try{
		poreport=fcRemote.RetrieveCashUncash(fdate, tdate,1,null);
	}
	catch(Exception ex){ex.printStackTrace();}
	
	return poreport;
}
//FOR PO MAKE REPORT

public PayOrderObject[] RetrievePOMadeInfo(String fdate,String tdate)
{
try{
	poreport=fcRemote.RetrievePOMadeInfo("'"+Validations.convertYMD(Validations.checkDate(fdate))+"' and '"+Validations.convertYMD(Validations.checkDate(tdate))+"'",2,null);
}
catch(Exception ex){ex.printStackTrace();}
return poreport;
}

public PayOrderObject[] RetrivePOConsolidatedReport(String fdate,String tdate){
	try{
		poreport=fcRemote.RetrievePOMadeInfo("'"+Validations.convertYMD(Validations.checkDate(fdate))+"' and '"+Validations.convertYMD(Validations.checkDate(tdate))+"'",1,null);
	}
	catch(Exception ex){ex.printStackTrace();}
	return poreport;
}

//FOR Master UPDATION============================

public int updateODCCMaster(ODCCMasterObject odccMasterObj, int type)throws RemoteException{
	System.out.println("Inside delegate the type value is========"+type);
    int res=0;
     try{
    	 res=fcRemote.updateODCCMaster(odccMasterObj, type);
     }
     catch(Exception e){
     	e.printStackTrace();
     }
     return res;
 }
public int updateAccountMaster(AccountMasterObject acMasterObj)throws AccountNotFoundException,RemoteException,CreateException{
    int res=0;
    res=fcRemote.updateAccountMaster(acMasterObj);
    return res;
}
public ODCCMasterObject getODCCMaster(int acno, String actype)throws AccountNotFoundException,RemoteException,CreateException{
    ODCCMasterObject odccmasterObj=null;
    odccmasterObj=fcRemote.getODCCMaster(acno,actype);
    return odccmasterObj;
}

public Object[][] getLoanDetails(String shtype, int shno)throws RemoteException{
    Object[][] obj=null;
     try{
     	obj=fcRemote.getLoanDetails(shtype, shno);
     }
     catch(Exception e){
     	e.printStackTrace();
     }
     return obj;
 }

public String storeJointHolderUpdation(AccountMasterObject accountmasterobject){
	try{
		int int_result=fcRemote.updateJointHolder(accountmasterobject);
		if(int_result!=0)
		{
		    /** -----------Account Updated----------*/
			return "Account No "+int_result+" is Updated Successfully";
				
		}
		else
			return "Account No "+int_result+" is not Updated";
	
	}
	catch(Exception ex){ex.printStackTrace();}
	return "Cannot Update";
}


//to get Subcategories

public AccSubCategoryObject[] getSubCategories(){
	try{
	 subcat=comRemote.getAccSubCategories();
	}
	catch(Exception ex){ex.printStackTrace();}
	return subcat;
}

//to get categories
public AccCategoryObject[] getAccCategories(){
	
	try{
		accat=comRemote.getAccCategories();
	}
	catch(Exception ex){ex.printStackTrace();}
	return accat;
}


public Object[][] getunverifiedtokens(String tablename,String[] colnames,String condition){
	try{
		unverifiedtokens=comRemote.getHelpDetails(tablename,colnames,condition);
	}
	catch(Exception ex){ex.printStackTrace();}
	return unverifiedtokens;
}

public Object[][] ODCCInterestDetails(){
	try{
		odccintDetail=fcRemote.ODCCInterestDetails("");
		return fcRemote.ODCCInterestDetails("");
	}
	catch(Exception ex){ex.printStackTrace();}
	return null;
}

public TreeMap getPendingTrayList(String date){
	try{
		pendinglist=loanRemote.pendingTrayList(null,date);
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	return pendinglist;
}


}

