package com.scb.designPatterns;

  
import backOfficeServer.BackOfficeHome;
import backOfficeServer.BackOfficeRemote;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;


import cashServer.CashRemote;

import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.exceptions.ServiceLocatorException;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;

import shareServer.ShareHome;
import shareServer.ShareRemote;


import loanServer.LoanHome;
import loanServer.LoanRemote;
import masterObject.backOffice.AdminObject;
import masterObject.backOffice.ChequeBookObject;
import masterObject.backOffice.ChequeNoObject;
import masterObject.backOffice.ClosingBalObject;
import masterObject.backOffice.OdccObject;
import masterObject.backOffice.OpenedClosedInoperatedAccountObject;
import masterObject.backOffice.PygmyObject;
import masterObject.backOffice.SIDoneObject;
import masterObject.backOffice.SIEntryObject;
import masterObject.backOffice.ShareObject;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.GLMasterObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.UserVerifier;
import masterObject.loansOnDeposit.LoanTransactionObject;
import masterObject.share.ShareCategoryObject;
import masterObject.termDeposit.DepositTransactionObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.backOffice.VoucherDataObject;
import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import commonServer.CommonHome;
import commonServer.CommonRemote;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.AccountNotFoundException;
import exceptions.RecordsNotFoundException;
import general.Validations;


/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 1, 2007
 * Time: 11:53:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class BackOfficeDelegate {
    
    private BackOfficeHome bckHome;
    private BackOfficeRemote bckRemote;
    private CommonHome cHome;
    private CashRemote cshremote;
    private CommonRemote cRemote;
    private CustomerHome custHome;
    private ShareHome sh_home;
	private ShareRemote sh_remote;
    private CustomerRemote custRemote;
    private LoanHome ln_home;
    private LoanRemote ln_remote;
    private AccountObject acObj;
    private ModuleObject[] Acc,array_moduleobject;
    private SIEntryObject siDelObj;
    private ChequeNoObject[] array_chequenoobject;
    private ChequeBookObject[] array_chequebookobject;
    private ShareCategoryObject sharecatobj[];
    private OpenedClosedInoperatedAccountObject[] array_opencloseaccountobject;
    
    final Logger logger=LogDetails.getInstance().getLoggerObject("BackOfficeDelegate");

    public BackOfficeDelegate() throws RemoteException, CreateException, ServiceLocatorException {
            try {

           System.out.println("************Inside Delegate Constructor**************");	 
           this.bckHome =(BackOfficeHome)ServiceLocator.getInstance().getRemoteHome("BACKOFFICEWEB",BackOfficeHome.class);
           this.bckRemote= bckHome.create();
      
           this.cHome =(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
           this.cRemote= cHome.create();

           this.custHome =(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
           this.custRemote= custHome.create();
                
           this.sh_home=(ShareHome)ServiceLocator.getInstance().getRemoteHome("SHAREWEB", ShareHome.class);
		   this.sh_remote=sh_home.create();
	      
		   this.ln_home=(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB", LoanHome.class);
           this.ln_remote=ln_home.create(); 
            }
           catch(RemoteException e){
                throw e;
            }
           catch(CreateException e){
               throw e;
            }
           catch(ServiceLocatorException e){
               throw e;
            }


    }

    /* public CustomerMasterObject getSI(){
         CustomerMasterObject ccobj=null;
          try{
              int cid=6496;
              String acNo="1002001";
              ccobj = custRemote.getCustomer(cid);

          }
          catch(Exception e){
              e.printStackTrace();
          }
         return ccobj;
    }*/

//************loan option for SIEntry*******************
   
 public Hashtable getComboLoanOptions(){
    	Hashtable hash=new Hashtable();
    	hash.put("3","3-Due Amt");
    	hash.put("2","2-Pr Amt");
    	hash.put("1","1-Tot Amt" );
      	Enumeration en=hash.keys();
    	while(en.hasMoreElements()){
    		Object obj=en.nextElement();
    	}
    	return hash;
    }
   
 
 //********************gives customer details*******************
 public CustomerMasterObject getCustomer(int cid)throws RemoteException, CustomerNotFoundException {
       CustomerMasterObject custObj=null;
       System.out.println("'m gettin custIODD");
       custObj=this.custRemote.getCustomer(cid);
       System.out.println("after gettin");
       return custObj;
      }

 //******************gives Account details*********************
 public AccountObject getAccount(String acctype,int accno){
	 try{
           String date=com.scb.common.help.Date.getSysDate(); 
           
           logger.info("@@@@" + date+"***AC tye***"+acctype+"***ac num**"+accno);
           AccountObject acObj = cRemote.getAccount(null,acctype,accno,date);
           if(acObj!=null){
        	   logger.info("Accobject="+acObj);
        	   return acObj;
           }
           
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 return null;
       }

   //*******************gives modulecodes********************
 public ModuleObject[] getMainModules(int type) throws RemoteException {
	 ModuleObject[] mod=null;
	 System.out.println("Type====> "+type);
	   //acctypes for SIEntry , SIDeletion,SIReg
	      if(type==1)
	    	
    	 mod=cRemote.getMainModules(2, "'1002000','1007000','1008000','1010000','1004000'");
	      
	   //acctypes for UnpresentedCheques ,SBCALedger  
	      else if(type==2)
	     mod=cRemote.getMainModules(2,"'1002000','1007000','1014000','1015000'");	  
	      
	     //acctypes for OpenCloseReport,VoucherSchedule  
	      else if(type==3)		
		 mod=cRemote.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1014000,1015000,1017000,1018000");
	   
	      //acctypes for OpenCloseReport 
	      else if(type==4)
		 mod=cRemote.getMainModules(2,"1002000,1007000,1014000,1015000,1017000,1018000");
		  
	    //acctypes for OpenCloseReport
	      else if(type==5)
		 mod=cRemote.getMainModules(2,"1002000,1007000,1009000,1014000,1015000,1017000,1018000");
			
		  //acctypes for SIQuery
	      else if(type==6)	
		 mod=cRemote.getMainModules(2,"'1002000','1007000'");
		  
	      else if(type==7)
		  mod=cRemote.getMainModules(1,"");  
		  
		   //from ac_types for SIParameters
	      else if(type==8)
		   mod=cRemote.getMainModules(2, "'1002000','1007000','1015000','1014000','1017000'");  
	 
	     
		   //to ac_types for SIParameters
	      else if(type==9)
		   mod=cRemote.getMainModules(2, "'1002000','1007000','1008000','101000','1004000','1015000','1014000','1017000','1010000'");  
				  
		  
		  //gltype (payment voucher,transfer voucher)
	      else if(type==10)
			 mod=cRemote.getMainModules(2,"'1012000'");  
		   		   
		 //ODCC Balance
	      else if(type==11)
			   mod=cRemote.getMainModules(2,"'1014000','1015000'");
		   
		  //transfer voucher acctypes 
	      else if(type==12)
	      {
	    	  System.out.println("Are you going out of delegate123456!!!!!!!!!");
			   mod=cRemote.getMainModules(2,"'1002000','1007000','1014000','1015000','1017000','1018000','1001000','1003000','1004000','1005000','1006000'");
			   System.out.println("Are you going out of delegate11111!!!!!!!!!");
	      }
		   return mod;
     }
     
 //************************fetching sientrydetails on si_no*********************
 public SIEntryObject getInfoThruSiNo(int si_no,int flag) throws RemoteException,SQLException
 {
	   String from_ac_type=null,to_ac_type=null;
       ModuleObject[] mod=getMainModules(1);
       siDelObj=bckRemote.getInfoThruSiNo(si_no ,flag);
       System.out.println("Building agi iillaaaaa");
       if(siDelObj!=null){
       System.out.println("Si del Obj------------"+siDelObj);
       System.out.println("&&&&&&&&&&&====="+siDelObj.getDelInd());
       System.out.println("&&&&&&&&&&&====="+siDelObj.getAltDeDtTime());
       System.out.println("&&&&&&&&&&&====="+siDelObj.getAltDeTml());
       System.out.println("&&&&&&&&&&&====="+siDelObj.getAltDeUser());
       
       System.out.println("&&&&&&&&&&&====="+siDelObj.toString());
     
       logger.info("From Account type"+siDelObj.getFromType());
       for(int i=0;i<mod.length;i++){
    	   logger.info("Module Abbr"+mod[i].getModuleAbbrv());
    	   logger.info("Module Code"+mod[i].getModuleCode());
    	   
        if(siDelObj.getFromType().equalsIgnoreCase(mod[i].getModuleCode())){
        	from_ac_type=mod[i].getModuleAbbrv();
        
           }
          }
       siDelObj.setFrommodAbbrv(from_ac_type);
       logger.info("From Acc Type:"+from_ac_type);
      
      for(int i=0;i<mod.length;i++){
    	  logger.info("Module Obj"+mod[i].getModuleAbbrv());
   	      logger.info("To Account type"+siDelObj.getToType());
    	  if(siDelObj.getToType().equalsIgnoreCase(mod[i].getModuleCode())){
    		  to_ac_type=mod[i].getModuleAbbrv();
    	  }
         }
      siDelObj.setTomodAbbrv(to_ac_type);
      logger.info("To Acc Type:"+to_ac_type); 
     
	   return siDelObj;
       }
       else{
    	   return null;
       }
   }
     
 
 
 //**************************inserting sientry values****************************    
 public int storeInfo(SIEntryObject siEntryObj) throws RemoteException,SQLException
     {
    	int storeinfo_func =bckRemote.storeInfo(siEntryObj);
    	System.out.println("Instruction Number in Delegate -----"+storeinfo_func);
    	return storeinfo_func;
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
 
//*****************deleting si values*****************************************
 public void delete(int si_no,String tml,String uid,String userdate,int flag)throws RemoteException,SQLException  
 {
   bckRemote.delete(si_no,tml,uid,userdate,flag);
 }
 
 
	
 //*****************updating si values******************************************
 public int update(SIEntryObject siupdate) throws RemoteException,SQLException{
	  System.out.println("--inside delegate in update function--");
      int update_func =bckRemote.update(siupdate);
      System.out.println("--retutnin safely from deligatre--");
      return update_func;
 }
 

 //*****************fetching details of UnPresented cheques********************* 
 public ChequeNoObject[] getUnpresentedCheque(int acc_no,String acc_type,String string_query) throws RemoteException,SQLException
 {    
	 String ac_type=null;
	 ModuleObject[] array_module_obj=getMainModules(2);
	 array_chequenoobject=bckRemote.getUnpresentedCheque(acc_no,acc_type,string_query);
	 if(array_chequenoobject!=null)
	 {	 
	 for(int j=0;j<array_chequenoobject.length;j++){
	 
	 for(int i=0;i<array_module_obj.length;i++){
   	   System.out.println("Module Abbr"+array_module_obj[i].getModuleAbbrv());
   	   System.out.println("Module Code"+array_module_obj[i].getModuleCode());
   	   System.out.println("checkobject Acc_type"+array_chequenoobject[j].getAccountType());
       if(array_chequenoobject[j].getAccountType().equalsIgnoreCase(array_module_obj[i].getModuleCode())){
       ac_type=array_module_obj[i].getModuleAbbrv();
       System.out.println("Mod Object Acc_type"+ac_type);
        }
	 }
       array_chequenoobject[j].setModAbbrv(ac_type);
       }
	    
	 } 
	  return array_chequenoobject;
	 
 }
 
 
 //*************************fetching ChequeBook details***********************************
 public ChequeBookObject[] getChequeBook(int flag,String query,String fdate,String ldate) throws RemoteException,SQLException
 {
	 array_chequebookobject=bckRemote.getChequeBook(flag,query,fdate,ldate);
	 return array_chequebookobject;
 }
 
 
 //***************getting Accountstatus values********************
 public String[] getAccountStatus(){
	 String acc_status[]={"Opened","Closed","InOperated","Freezed"};
	 return acc_status;
 }
 
//***************************getting Account types for open/close report***************************
 public ModuleObject[] getAccountTypesForOpenCloseReport(int type) throws RemoteException,SQLException{
	 ModuleObject[] arr_mod=null;
	 if(type==1) 
		 arr_mod=getMainModules(3);	
	 else if(type==2)
		 arr_mod=getMainModules(4);
	 else if(type==3)
		 arr_mod=getMainModules(5);
	 
	 return arr_mod;
 }
 

//*************************fetching details of opened/closed/Inoperated accounts******************* 
public OpenedClosedInoperatedAccountObject[] getOpenedClosedInoperatedAccounts(int flag,String account_type,String from_date,String to_date,String account_list,String query) throws RemoteException,SQLException
{    
	String ac_type=null;
	ModuleObject[] array_module_obj=getAccountTypesForOpenCloseReport(1);
	System.out.println("Inside delegate");
	System.out.println("AccType="+account_type);
	System.out.println("from date="+from_date);
	System.out.println("To date="+to_date);
	System.out.println("AccStatus="+account_list);
	array_opencloseaccountobject=bckRemote.getOpenedClosedInoperatedAccounts(flag,account_type,from_date,to_date,account_list,query);
	System.out.println("openclose*************"+array_opencloseaccountobject);
	
	if(array_opencloseaccountobject!=null){
	
	for(int j=0;j<array_opencloseaccountobject.length;j++){
		 
		 for(int i=0;i<array_module_obj.length;i++){
	   	   System.out.println("Module Abbr"+array_module_obj[i].getModuleAbbrv());
	   	   System.out.println("Module Code"+array_module_obj[i].getModuleCode());
	   	   System.out.println("Opendcloseaccobject Acc_type"+array_opencloseaccountobject[j].getAccountType());
	       if(array_opencloseaccountobject[j].getAccountType().equalsIgnoreCase(array_module_obj[i].getModuleCode())){
	       ac_type=array_module_obj[i].getModuleAbbrv();
	       System.out.println("Mod Object Acc_type"+ac_type);
	        }
		 }
		 array_opencloseaccountobject[j].setModAbrr(ac_type);
	       }
	}
	System.out.println("hi i am in delegate open closed+++"+array_opencloseaccountobject);
	return array_opencloseaccountobject;

}
 

//******************getting system date*******************************
 public static String getSysDate() {
     Calendar c = Calendar.getInstance();
     try {
         return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
     } catch (DateFormatException e) {
             e.printStackTrace();
     }
     return null;
 }
 

 //*********************getting si Information***************************************
 public SIEntryObject[] getInfo(String acc_ty,int acc_no,String string_qry)throws RemoteException,SQLException
 {   
	 AccountObject accObj=getAccount(acc_ty, acc_no);
	 SIEntryObject[] array_sientryobject_receive=bckRemote.getInfo(acc_ty,acc_no,string_qry);
	 ModuleObject[] mod=getMainModules(7);
	 String to_ac_type=null;
	 
	 //converting loanoption
	 for(int i=0;i<array_sientryobject_receive.length;i++){
	 System.out.println(array_sientryobject_receive[i].getLoanOpt());
	
	 if(array_sientryobject_receive[i].getLoanOpt()==1){
		 array_sientryobject_receive[i].setStrloanopt("Total Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==2){
		 array_sientryobject_receive[i].setStrloanopt("Principal Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==3){
		 array_sientryobject_receive[i].setStrloanopt("Due Amt");
	 }
	 else
		 array_sientryobject_receive[i].setStrloanopt("Nil");

	 }
	 
	 //converting to acctype
	 for(int j=0;j<array_sientryobject_receive.length;j++){
    
	   for(int i=0;i<mod.length;i++){
   	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
  	      System.out.println("To Account type"+array_sientryobject_receive[j].getToType());
   	  if(array_sientryobject_receive[j].getToType().equalsIgnoreCase(mod[i].getModuleCode())){
   		  to_ac_type=mod[i].getModuleAbbrv();
   		 
   	  }
        }
     array_sientryobject_receive[j].setTomodAbbrv(to_ac_type);
	 }
     System.out.println("To Acc Type:"+to_ac_type); 
     
     
     //setting toaccholdername property
     for(int i=0;i<array_sientryobject_receive.length;i++){
    	 array_sientryobject_receive[i].setToaccholdername(accObj.getAccname()); 
     }
			
	 return array_sientryobject_receive;
		
 }
 
 
 //***************************getting Instruction Types*********************************
 
 public String[] getInstructionTypes(){
	 String Inst_Type[]={"ACTIVE","DELETED"};
	 return Inst_Type;
 }
	

//**************************getting Instruction Information************************** 
public SIEntryObject[] getInstInfo(int choice,String from_date,String to_date,String string_qry)throws RemoteException,SQLException
{
	
	SIEntryObject[] array_sientryobject_receive=bckRemote.getInstInfo(choice,from_date,to_date,string_qry);
	System.out.println("==================>"+array_sientryobject_receive.toString());
	ModuleObject[] mod=getMainModules(1);
	AccountObject toacObj=new AccountObject();
	AccountObject fromacObj=new AccountObject();
	String to_ac_type=null,from_ac_type=null;
	
	//converting loanoption
	 for(int i=0;i<array_sientryobject_receive.length;i++){
	 System.out.println(array_sientryobject_receive[i].getLoanOpt());
	
	 if(array_sientryobject_receive[i].getLoanOpt()==1){
		 array_sientryobject_receive[i].setStrloanopt("Total Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==2){
		 array_sientryobject_receive[i].setStrloanopt("Principal Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==3){
		 array_sientryobject_receive[i].setStrloanopt("Due Amt");
	 }
	 else
		 array_sientryobject_receive[i].setStrloanopt("Nil");

	 }

	
	//converting to acctype
	 for(int j=0;j<array_sientryobject_receive.length;j++){
		    
		   for(int i=0;i<mod.length;i++){
	   	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
	  	      System.out.println("To Account type"+array_sientryobject_receive[j].getToType());
	   	  if((array_sientryobject_receive[j].getToType()).equalsIgnoreCase(mod[i].getModuleCode())){
	   		  to_ac_type=mod[i].getModuleAbbrv();
	   		  break;
	   	  }
	        }
	     array_sientryobject_receive[j].setTomodAbbrv(to_ac_type);
		 }
	     System.out.println("To Acc Type:"+to_ac_type); 
	     
	   //converting from acctype
		 for(int j=0;j<array_sientryobject_receive.length;j++){
			    
			   for(int i=0;i<mod.length;i++){
		   	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
		  	      System.out.println("From Account type"+array_sientryobject_receive[j].getFromType());
		   	  if(array_sientryobject_receive[j].getFromType().equalsIgnoreCase(mod[i].getModuleCode())){
		   		  from_ac_type=mod[i].getModuleAbbrv();
		   	  }
		        }
		     array_sientryobject_receive[j].setFrommodAbbrv(from_ac_type);
			 }
		     System.out.println("From Acc Type:"+from_ac_type); 
		        
		  
		 //setting toaccholdername property
		     System.out.println("Inside to AccountObj ");
		     for(int i=0;i<array_sientryobject_receive.length;i++){
		    	System.out.println("To Mod code**********"+array_sientryobject_receive[i]);
		    	System.out.println("To Acc No**********"+array_sientryobject_receive[i].getToAccNo());
		    	toacObj=getAccount(array_sientryobject_receive[i].getToType(),array_sientryobject_receive[i].getToAccNo());
		    	if(toacObj!=null){
		    	System.out.println("to Acc HolderName:"+toacObj.getAccname());   
		        array_sientryobject_receive[i].setToaccholdername(toacObj.getAccname());
		    	}
		       }
		     
		     
		  //setting fromaccholdername property
		     System.out.println("Inside from AccountObj ");
		     for(int i=0;i<array_sientryobject_receive.length;i++){
		      System.out.println("fromMod code**********"+array_sientryobject_receive[i].getFromType());
		      System.out.println("fromAcc No**********"+array_sientryobject_receive[i].getFromAccNo());
		      fromacObj=getAccount(array_sientryobject_receive[i].getFromType(),array_sientryobject_receive[i].getFromAccNo());
		      if(fromacObj!=null){
		      
		      System.out.println("from Acc HolderName:"+fromacObj.getAccname());   
		      array_sientryobject_receive[i].setFromaccholdername(fromacObj.getAccname());
		      }
		      }		
		    
	 return array_sientryobject_receive;
}


//*****************getting Instruction Types for DueDone Report***********************
public String[]getInstructionTypesForDueDoneReport(){
	String[] InstType={"Select","To Be executed","Already executed"};
	return InstType;
}


//*****************getting Instruction Types for Due Report***********************
public SIEntryObject[] getInstInfoForDue(String from_date,String to_date,String string_qry)throws RemoteException,SQLException
{
	
	ModuleObject[] mod=getMainModules(1);
	AccountObject toacObj=new AccountObject();
	AccountObject fromacObj=new AccountObject();
	String to_ac_type=null,from_ac_type=null;
    SIEntryObject[] array_sientryobject_receive=bckRemote.getInstInfoForDue(from_date,to_date,string_qry);
    
  //converting loanoption
	 for(int i=0;i<array_sientryobject_receive.length;i++){
	 System.out.println(array_sientryobject_receive[i].getLoanOpt());
	
	 if(array_sientryobject_receive[i].getLoanOpt()==1){
		 array_sientryobject_receive[i].setStrloanopt("Total Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==2){
		 array_sientryobject_receive[i].setStrloanopt("Principal Amt");
	 }
	 else if(array_sientryobject_receive[i].getLoanOpt()==3){
		 array_sientryobject_receive[i].setStrloanopt("Due Amt");
	 }
	 else
		 array_sientryobject_receive[i].setStrloanopt("Nil");

	 }

    //converting to acctype
	 for(int j=0;j<array_sientryobject_receive.length;j++){
		    
		   for(int i=0;i<mod.length;i++){
	   	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
	  	      System.out.println("To Account type"+array_sientryobject_receive[j].getToType());
	   	  if((array_sientryobject_receive[j].getToType()).equalsIgnoreCase(mod[i].getModuleCode())){
	   		  to_ac_type=mod[i].getModuleAbbrv();
	   		  break;
	   	  }
	        }
	     array_sientryobject_receive[j].setTomodAbbrv(to_ac_type);
		 }
	     System.out.println("To Acc Type:"+to_ac_type); 
	     
	   //converting from acctype
		 for(int j=0;j<array_sientryobject_receive.length;j++){
			    
			   for(int i=0;i<mod.length;i++){
		   	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
		  	      System.out.println("From Account type"+array_sientryobject_receive[j].getFromType());
		   	  if(array_sientryobject_receive[j].getFromType().equalsIgnoreCase(mod[i].getModuleCode())){
		   		  from_ac_type=mod[i].getModuleAbbrv();
		   	  }
		        }
		     array_sientryobject_receive[j].setFrommodAbbrv(from_ac_type);
			 }
		     System.out.println("From Acc Type:"+from_ac_type); 
		        
		  
		 //setting toaccholdername property
		     System.out.println("Inside to AccountObj ");
		     for(int i=0;i<array_sientryobject_receive.length;i++){
		    	System.out.println("To Mod code**********"+array_sientryobject_receive[i]);
		    	System.out.println("To Acc No**********"+array_sientryobject_receive[i].getToAccNo());
		    	toacObj=getAccount(array_sientryobject_receive[i].getToType(),array_sientryobject_receive[i].getToAccNo());
		    	if(toacObj!=null){
		    	System.out.println("to Acc HolderName:"+toacObj.getAccname());   
		        array_sientryobject_receive[i].setToaccholdername(toacObj.getAccname());
		    	}
		       }
		     
		     
		  //setting fromaccholdername property
		     System.out.println("Inside from AccountObj ");
		     for(int i=0;i<array_sientryobject_receive.length;i++){
		      System.out.println("fromMod code**********"+array_sientryobject_receive[i].getFromType());
		      System.out.println("fromAcc No**********"+array_sientryobject_receive[i].getFromAccNo());
		      fromacObj=getAccount(array_sientryobject_receive[i].getFromType(),array_sientryobject_receive[i].getFromAccNo());
		      if(fromacObj!=null){
		      System.out.println("from Acc HolderName:"+fromacObj.getAccname());   
		      array_sientryobject_receive[i].setFromaccholdername(fromacObj.getAccname());
		      }		
		     }

  return array_sientryobject_receive;
}


//*****************getting Instruction Types for Done Report***********************
//*************and also for getting executed records***********************
public SIDoneObject[] getInstInfoForDone( String from_date,String to_date,String string_qry)throws RemoteException,SQLException
{
	ModuleObject[] mod=getMainModules(1);
	AccountObject toacObj=new AccountObject();
	AccountObject fromacObj=new AccountObject();
	SIEntryObject sientryobject_receive=null;
	String to_ac_type=null,from_ac_type=null;
	
	SIDoneObject[] sidoneobject=bckRemote.getInstInfoForDone(from_date,to_date,string_qry);
	if(sidoneobject!=null)
	{
	for(int i=0;i<sidoneobject.length;i++){
	 sientryobject_receive=bckRemote.getInfoThruSiNo(sidoneobject[i].getSiNo(),0);
	 }
	
	
	//converting to acctype
	 if(sientryobject_receive!=null){
	  for(int i=0;i<mod.length;i++){
	  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
	 
	  System.out.println("To Account type"+sientryobject_receive.getToType());
	  if((sientryobject_receive.getToType()).equalsIgnoreCase(mod[i].getModuleCode())){
	  to_ac_type=mod[i].getModuleAbbrv();
	   break;
	    }
	      }
	    System.out.println("To Acc Type:"+to_ac_type);
	 }
	     
	   //converting from acctype
	 if(sientryobject_receive!=null){
		 for(int i=0;i<mod.length;i++){
		  System.out.println("Module Obj"+mod[i].getModuleAbbrv());
		  System.out.println("From Account type"+sientryobject_receive.getFromType());
		  if(sientryobject_receive.getFromType().equalsIgnoreCase(mod[i].getModuleCode())){
		   		  from_ac_type=mod[i].getModuleAbbrv();
		   	  }
		        }
	     System.out.println("From Acc Type:"+from_ac_type); 
	 }
        
        
      //setting toaccholdername property
	 if(sientryobject_receive!=null){
	     System.out.println("Inside to AccountObj ");
	     System.out.println("To Mod code**********"+sientryobject_receive.getToType());
	     toacObj=getAccount(sientryobject_receive.getToType(),sientryobject_receive.getToAccNo());
	     if(toacObj!=null){
	     System.out.println("to Acc HolderName:"+toacObj.getAccname());   
	     } 
	 }
	 
	 
	  //setting fromaccholdername property
	 if(sientryobject_receive!=null){
	     System.out.println("Inside from AccountObj ");
	     System.out.println("fromMod code**********"+sientryobject_receive.getFromType());
	     fromacObj=getAccount(sientryobject_receive.getFromType(),sientryobject_receive.getFromAccNo());
	      if(fromacObj!=null){
	     System.out.println("from Acc HolderName:"+fromacObj.getAccname());   
	      }
	 }

	     for(int i=0;i<sidoneobject.length;i++){
	        	sidoneobject[i].setTomodAbbrv(from_ac_type);
	        	sidoneobject[i].setFrommodAbbrv(from_ac_type);
	        	if(toacObj!=null){
	        	sidoneobject[i].setToaccholdername(toacObj.getAccname());
	        	}
	        	if(fromacObj!=null){
	        	sidoneobject[i].setFromaccholdername(fromacObj.getAccname());
	        	}
	        	System.out.println("fromAcc No**********"+sientryobject_receive.getFromAccNo());
	        	sidoneobject[i].setFromAccNo(sientryobject_receive.getFromAccNo());
	        	System.out.println("To Acc No**********"+sientryobject_receive.getToAccNo());
	        	sidoneobject[i].setToAccNo(sientryobject_receive.getToAccNo());
	        }
 
	}
	     return sidoneobject;
 }



//*********************getting Voucher Types**********************************
public String[]getVoucherTypes(){
	String[] VchType={"S E L E C T","Sch of Misc Rec","Sch of Pay Vch","Sch of Trf Vch","Sch of Csh Pay Vch"};
	return VchType;
}


//*************************fetching details for Voucher Types****************
public masterObject.backOffice.VoucherDataObject[] getFaData(String fdate,String todate,String vchtyp,String string_query) throws RemoteException,SQLException,RecordsNotFoundException
{
	 ModuleObject[] array_moduleobject=getMainModules(3);
	UserVerifier obj_userverifier=new UserVerifier();
	System.out.println("fromdate============"+fdate);
	System.out.println("todate============"+todate);
	System.out.println("query============"+string_query);
	masterObject.backOffice.VoucherDataObject[] array_vchdataobject=bckRemote.getFaData(fdate,todate,vchtyp,string_query);
	System.out.println("vchdataobj============"+array_vchdataobject);
	
	//conversion of acc_type
	if(array_vchdataobject!=null){
	for(int c=0;c<array_vchdataobject.length;c++){
	for(int d=0;d<array_moduleobject.length;d++)
    {
		if(array_vchdataobject[c].getModuleAccountType()!=null){
		System.out.println("ModAccType============"+array_vchdataobject[c].getModuleAccountType());
		System.out.println("Modcode============"+array_moduleobject[d].getModuleCode());
		System.out.println("ModAbbr============"+array_moduleobject[d].getModuleAbbrv());
        if(array_vchdataobject[c].getModuleAccountType().equals(array_moduleobject[d].getModuleCode()))
        {
        	array_vchdataobject[c].setModAbbr(array_moduleobject[d].getModuleAbbrv());
            break;
        }
    }
	else{
		
    }
	}
	}
	}
	else{
		System.out.println("No records");
	}

	return array_vchdataobject;
}


//***************fetching details of GLMaster*************************
public  GLMasterObject getGLMasterDetails(int glcd,String date) throws RemoteException,SQLException
{
	GLMasterObject glmasterobject=bckRemote.getGLMasterDetails(glcd,date);
	return glmasterobject;
}


//*******************getting glname***************************
public String getGlName(int int_glcode,String date) throws RemoteException,SQLException
{
	String glcode=bckRemote.getGlName(int_glcode,date);
	return glcode;
}


//******************getting options for Account types**********************
public String[]getSelect(){
	String[] select={"All","Individual","Institute"};
	return select;
}


//*******************getting share categories******************************
/*public String[] getShareCategory(String shtype) throws RemoteException,SQLException,RecordsNotFoundException
{
	String[] sh_cat=bckRemote.getShareCategory(shtype);
	return sh_cat;
}
*/
public ShareCategoryObject[] getShareCategory()throws RemoteException{
	System.out.println("In the delegate");
	sharecatobj=sh_remote.getShareCategories(0,"1001001");
	for(int i=0;i<sharecatobj.length;i++){
		sharecatobj[i].getCatName();
		//System.out.println("The cats are"+sharecatobj[i].getShCat());
		
}
	System.out.println("Out of delegate");
	return sharecatobj;
}


//**************fetching deposit tran details*******************************
public DepositTransactionObject[] getDepositReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException
{
	DepositTransactionObject[] array_deposit_transactionobject=bckRemote.getDepositReport(fdate,tdate,modcode,0,string_query);
    return array_deposit_transactionobject;
}


//****************feching share tran details**********************************
public ShareObject[] getShareTranSummary(String modecode,String fdate,String tdate,int cate,int shno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException
{
	ShareObject[] array_shareobject=bckRemote.getShareTranSummary(modecode,fdate,tdate,cate,0,string_query);
	return array_shareobject;
}

//****************feching loans on deposit tran details**********************************
public masterObject.loansOnDeposit.LoanTransactionObject[] getLoanTransactionReport(String fdate,String tdate,String modcode,int acno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException{
	masterObject.loansOnDeposit.LoanTransactionObject[] lnobj=null;
	lnobj = bckRemote.getLoanTransactionReport(fdate, tdate, modcode, acno, string_query);
	return lnobj;
}

//****************feching pygmy tran details**********************************
public PygmyObject[] getPygmy(String fromdate,String todate,String modecode,int accno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException
{
	PygmyObject[] array_pygmyobject=bckRemote.getPygmy(fromdate,todate,modecode,accno,string_query);
    return array_pygmyobject;
}	


//****************feching Account tran details**********************************
public AccountTransObject[] getSBCATranSummary(String modcode,String fromdate,String todate,int accno,String string_query) throws RemoteException,RecordsNotFoundException
{
	AccountTransObject[] array_acc_tranobj=bckRemote.getSBCATranSummary(modcode, fromdate, todate, accno, string_query);
	return array_acc_tranobj;
	
}

////****************feching loan tran details**********************************
public masterObject.loans.LoanTransactionObject[] getLoanTranSummary(String fromdate,String todate,String modcode,int accno,String string_query) throws RemoteException,SQLException,RecordsNotFoundException{
	masterObject.loans.LoanTransactionObject[] array_jointloanobject=bckRemote.getLoanTranSummary(fromdate,todate,modcode,accno,string_query);
	return array_jointloanobject;
}


//******************options for Account details****************************
public String[]getSelected(){
	String[] select={"Select","Selected","All"};
	return select;
}

//****************verification for sientry details**********************************
public void verify(int si_no,String tml,String user,String date)throws RemoteException,SQLException{
	bckRemote.verify(si_no,tml,user,date);
}


//closing balance summary
//************************closing balance report for SB****************************
public ClosingBalObject[] getReportSBSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportSBSum(string_modcode,string_date);
	return array_closingbalobject_receive;
}

//************************closing balance report for SH****************************
public ClosingBalObject[] getReportSHSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportSHSum(string_modcode,string_date);
    return array_closingbalobject_receive;
}

//************************closing balance report for PD****************************
public ClosingBalObject[] getReportPDSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportPDSum(string_modcode,string_date);
	return array_closingbalobject_receive;
}

//************************closing balance report for TD****************************

public ClosingBalObject[] getReportTDSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportTDSum(string_modcode,string_date);
	return array_closingbalobject_receive;
}


//************************closing balance report for LN****************************
public ClosingBalObject[] getReportLnSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportLnSum(string_modcode,string_date);
	return array_closingbalobject_receive; 
}

//************************closing balance report for LND****************************
public ClosingBalObject[] getReportLnDSum(String string_modcode,String string_date) throws RemoteException,SQLException{
	System.out.println("modcode%%%%%%%%%%%%%%"+string_modcode);
	System.out.println("string_date%%%%%%%%%%%%%%"+string_date);
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportLnDSum(string_modcode,string_date);
	return array_closingbalobject_receive; 
}

//********************fetching details for SIParameters******************

public AdminObject[] getDetails() throws RemoteException,SQLException
{
	AdminObject[] adminobject_receive =bckRemote.getDetails();
	return adminobject_receive;
	
}

//***********************getting max rows in SIParameter******************
public int getMaxRowNo() throws RemoteException,SQLException{
	int maximumrows = bckRemote.getMaxRowNo();
	return maximumrows;
}

//***********************validating details in SIParameter**************
public int validate(int p_no,String fromtype,String totype) throws RemoteException,SQLException{
	int int_verified =bckRemote.validate(p_no,fromtype,totype);
	return int_verified;
}


//****************inserting details in SIParameter************************
public int insertDetails(AdminObject obj) throws RemoteException,SQLException{
	int int_flag = bckRemote.insertDetails(obj);
	return int_flag;
}

//*******************upadating details in SIParameter*******************
public void update(AdminObject aobj1,int prno,String a,String b) throws RemoteException,SQLException
{
	bckRemote.update(aobj1 ,prno, a, b);
}

//**************************************deleting details in SIParameter*************
public int delete(int[] xx, String[] yy, String[] zz) throws RemoteException,SQLException{
	int int_result_delete_func = bckRemote.delete(xx,yy,zz);
	return int_result_delete_func;
}

//******************siexec (getting sino for execution)*****************
public int[] getInstnsForExec(String date)throws RemoteException,SQLException{
	int[] si_no=bckRemote.getInstnsForExec(date);
	return si_no;
}

//***************getting records for siexecution************************
public String[][] getStdInstRecords(int si_no[])throws RemoteException{
	String[][]	records=bckRemote.getStdInstRecords(si_no);
	return records;
}

//*********************************
public String[] stdExec(int n,String usr,String tml,String date,String datetime)throws RemoteException,SQLException{
	String[] array_string_details=bckRemote.stdExec(n, usr, tml,date, datetime);
    return array_string_details; 
}

//**************getting glcodes returns hastable****************
public Hashtable getGLCodes(String date) throws RemoteException{
	Hashtable hash_glcode = bckRemote.getGLCodes(date);
	
	System.out.println("hashglcode-------"+hash_glcode.get("ALL")+"-------");
	return hash_glcode;
}

//**************getting glcodes returns int****************
public int[] getGlCodes(String date) throws RemoteException,SQLException
{
	int[] array_int_glcodes=bckRemote.getGlCodes(date);
	return array_int_glcodes;
}


//***************getting gldesc***************
public Hashtable getGLCodesDesc(String date) throws RemoteException{
	Hashtable hash_desc = bckRemote.getGLCodesDesc(date);
	System.out.println("gldescription*******"+hash_desc);
	return hash_desc;
}

//*****************gettting ODCC report***********************
public OdccObject[] getOdccMaster(int flag,String date,int account_type,String query) throws RemoteException,SQLException{
	OdccObject[] array_odccobject=bckRemote.getOdccMaster(flag,date,account_type,query);
	System.out.println("ODCCDetails---->"+array_odccobject);
	return array_odccobject;
}

//*****************getting trntypes returns string*******************
public String[] getAccountTransferTypes(String ac_type,int glcode) throws RemoteException{
	System.out.println("*****actype----->****"+ac_type+"****glcode------>*****"+glcode);
	String[] transfer_types=bckRemote.getAccountTransferTypes(ac_type,glcode);
	return transfer_types;
}

//********************getting trntypes******************
public Hashtable getTransactionTypes()throws RemoteException{
	Hashtable hash_trntype = bckRemote.getTransactionTypes();
	return hash_trntype;
}

//******************getting cdIndicator(adding initial)*********************
public String[] getCDIndicator(){
  String[] cdind={"C","D"};
  return cdind;
}

//*****************getting selected cdindicator*********************
public String getCDIndicator(String ac_type,int glcode,String string_trn_type) throws RemoteException{
	String cd_ind = bckRemote.getCDIndicator(ac_type,glcode,string_trn_type);
	return cd_ind;
}

//for both payment and transfer voucher(inserting data)
public int storeTransferVoucherData(masterObject.backOffice.VoucherDataObject[] array_VoucherDataObject) throws RemoteException
{
	int vch_no = bckRemote.storeTransferVoucherData(array_VoucherDataObject);
	return vch_no;
}


//for payment voucher
public String[] getCashGLCode() throws RemoteException
{
	String[] cash_gl = bckRemote.getCashGLCode();
	return cash_gl;
}

//for both payment and transfer voucher(deleting data)
public int deleteTransferDataVoucher(int vchno,String vch_type,String date) throws RemoteException,SQLException
{	int delete_voucher=0;
	if(vch_type=="P")
	{
		delete_voucher=bckRemote.deleteTransferDataVoucher(vchno,"P",date);
		return delete_voucher;
	}
	else{
		delete_voucher=bckRemote.deleteTransferDataVoucher(vchno,"T",date);
		return delete_voucher;
	   }
	
}
public AdminObject[] retrieveData() throws RemoteException, SQLException,RecordsNotFoundException
{
	AdminObject[] admin = bckRemote.retrieveData();
	return admin;
}
//for both payment and transfer voucher(updating data)
public int updateTransferVoucher(masterObject.backOffice.VoucherDataObject[] array_VoucherDataObject) throws RemoteException{
	int vch_no = bckRemote.updateTransferVoucher(array_VoucherDataObject);
	return vch_no; 
}


public AccountTransObject[] getAccountTransaction(int accno,String acctype) throws RemoteException,SQLException,RecordsNotFoundException
{
	AccountTransObject[] accounttransobject_view=bckRemote.getAccountTransaction(accno,acctype);
	return accounttransobject_view;
}

public AccountObject[] getAccount(String acctype,int fromacno,int toacno,String fromdate,String todate) throws RemoteException,SQLException,RecordsNotFoundException
{
	AccountObject[] array_accountobject_view=bckRemote.getAccount(acctype,fromacno,toacno,null,null);	
	return array_accountobject_view;
}


public AccountObject[] getAccounts(String acctype,int toacno) throws RemoteException,SQLException,RecordsNotFoundException
{
	AccountObject[] array_accountobject_view=bckRemote.getAccount(acctype, 0, toacno, null, null);
	return array_accountobject_view;
}


public NomineeObject getNominee(int reg_no) throws RemoteException
{
	NomineeObject nomineeobject_view=bckRemote.getNominee(reg_no);
	return nomineeobject_view;
}  




public ClosingBalObject[] getReportSBNew(String string_modcode,String string_fromdate, String string_todate, int ac_catgry) throws RemoteException,SQLException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportSBNew(string_modcode,string_fromdate,string_todate,ac_catgry);	
	return array_closingbalobject_receive;
}



public ClosingBalObject[] getReportSH(String string_modcode,String string_fromdate,String string_todate,String shr_cat) throws RemoteException,SQLException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportSH(string_modcode,string_fromdate,string_todate,shr_cat);
	return array_closingbalobject_receive;  
}

public ClosingBalObject[] getReportPD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportPD(string_modcode,string_fromdate,string_todate);	
	return array_closingbalobject_receive;
}



public ClosingBalObject[] getReportTD(String string_modcode,String string_fromdate, String string_todate) throws RemoteException,SQLException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportTD(string_modcode,string_fromdate,string_todate);
	return array_closingbalobject_receive;
	
	
	
}



public ClosingBalObject[] getReportLn(String string_modcode,String string_fromdate, String string_todate) throws RemoteException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportLn(string_modcode,string_fromdate,string_todate);
	return array_closingbalobject_receive;
}

public ClosingBalObject[] getReportLnD(String string_modcode,String string_fromdate,String string_todate) throws RemoteException{
	ClosingBalObject[] array_closingbalobject_receive=bckRemote.getReportLnD(string_modcode,string_fromdate,string_todate);
    return array_closingbalobject_receive;
}

public boolean verifyFadataVoucher(masterObject.backOffice.VoucherDataObject co1) throws RemoteException,SQLException{
	 boolean voucher_data=bckRemote.verifyFadataVoucher(co1);
	 return voucher_data;
}

public boolean verifyTransferFadata(masterObject.backOffice.VoucherDataObject co1) throws RemoteException,SQLException{
	 boolean voucher_data=bckRemote.verifyTransferFadata(co1);
	 return voucher_data;
}
     
public masterObject.backOffice.VoucherDataObject getFaDataTransferDetails(int vch_no) throws RemoteException,SQLException{
	masterObject.backOffice.VoucherDataObject voucherdataobject=bckRemote.getFaDataTransferDetails(vch_no);
	return voucherdataobject;
}


public masterObject.backOffice.VoucherDataObject[] VoucherData(String c,int vch_no)throws RemoteException,SQLException{
	masterObject.backOffice.VoucherDataObject[] focback=bckRemote.VoucherData(c,vch_no);
	return focback;
}

public String[][] getGlCodeNames() throws SQLException,RemoteException
{
	String[][] glname=null;
	try{
		
		glname=bckRemote.getGlCodesNames();
	}catch(Exception e){
		e.printStackTrace();
	}
   return glname;


   
}

public ModuleObject[] getSIMainMods(){
	ModuleObject[] m=null;
	try{
		m=cRemote.getMainModules(2, "'1002000','1007000','1008000','1010000','1004000'");
	}catch(Exception e){
		e.printStackTrace();
	}
	return m;
}
public String Max_AccountNo(String ac_type)throws SQLException,RemoteException
{

	String ac_no=null;
	try{
		
		ac_no=bckRemote.Max_AccountNo(ac_type);
		System.out.println("Maximum Account Number------------------->"+ac_no);
	}
	catch(Exception e){
		e.printStackTrace();
	}
   return ac_no;

}

public String getAccNo(String acc_type) throws SQLException,RemoteException
{
	String ac_no=null;
	try{
		
		ac_no=bckRemote.getAccNo(acc_type);
		System.out.println(" Account Number------------------->"+ac_no);
	}
	catch(Exception e){
		e.printStackTrace();
	}
   return ac_no;

}
/*public String[] getAccountTransferTypes(String ac_type,int glcode)throws RemoteException
{
	System.out.println("R u Here?????????");
	  String trn_typ[]=null;
	  try
	  {
		  System.out.println("ac-type in delegate========"+ac_type);
		  System.out.println("gltype in delegate========"+glcode);
		 
		  
		  trn_typ=bckRemote.getAccountTransferTypes(ac_type, glcode);
		  
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	  return trn_typ;
	}
*/
public int deleteTransferVoucher(VoucherDataObject[] vobj) throws RemoteException,SQLException
{
	int del_no=0;
	try{
		
		del_no=bckRemote.deleteTransferVoucher(vobj);
		System.out.println(" Delete Number------------------->"+del_no);
	}
	catch(Exception e){
		e.printStackTrace();
	}
   return del_no;

	
}
public String[] getAccountGlcodes1(String ac_type,String gl_type,int ac_no,String date) throws RemoteException
{
	System.out.println("R u Here?????????");
  String glcode[]=null;
  try
  {
	  System.out.println("ac-type in delegate========"+ac_type);
	  System.out.println("gltype in delegate========"+gl_type);
	  System.out.println("ac_no in delegate========"+ac_no);
	  System.out.println("date in delegate========"+date);
	  
	  glcode=bckRemote.getAccountGlcodes1(ac_type, gl_type, ac_no, date);
	  
  }
  catch(Exception e)
  {
	  e.printStackTrace();
  }
  System.out.println("GLCODE IN DELEGATE----------"+glcode);
  return glcode;
}
}

   
  


