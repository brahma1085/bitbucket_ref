package com.scb.designPatterns;


import java.rmi.RemoteException;
import java.security.SignatureException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.ejb.CreateException;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.AddressTypesObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.pygmyDeposit.AgentMasterObject;

import masterObject.pygmyDeposit.PygmyMasterObject;
import masterObject.pygmyDeposit.PygmyRateObject;
import masterObject.pygmyDeposit.PygmyTransactionObject;
import masterObject.pygmyDeposit.QuarterInterestObject;
import pygmyServer.PygmyHome;
import pygmyServer.PygmyRemote;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;
import com.scb.common.help.Date;
import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.ServiceLocatorException;
import com.scb.designPatterns.exceptions.SystemException;
import commonServer.CommonHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;
import exceptions.AccountClosedException;
import exceptions.AccountNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.FreezedAccountException;
import exceptions.InOperativeAccountException;
import exceptions.NomineeNotCreatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import general.Validations;

public class PygmyDelegate {
	
		private static PygmyDelegate pygmyinstance = null ; 

        private ServiceLocator _instance;
        private PygmyHome pygmyHome;
        private PygmyRemote pygmyRemote;
        private LoanHome loanHome;
        private LoanRemote loanRemote;
        private LoansOnDepositHome loansondeposithome; 
        private LoansOnDepositRemote loansondepositremote;
        private CustomerHome custHome;
        private CustomerRemote custRemote;
        private CommonHome comHome;
        private CommonRemote comRemote;
        private AgentMasterObject agentmasterobject;
        private PygmyMasterObject pygmymasterobject;
        private ModuleObject[] comboElements=null;
        private AccountObject accountObject;
        int retvalue;

         public CommonRemote getComRemote() {
                 return this.comRemote;
         }
        
        public static PygmyDelegate getPygmyDelegateInstance() throws ApplicationException, SystemException {
        	
        	if ( pygmyinstance != null )
        		return pygmyinstance;
        	else {
        		try {
        			
        			pygmyinstance = new PygmyDelegate();
        			return pygmyinstance;
        			
        		}catch (ApplicationException e) {

        			throw e;
        			
        		} catch (SystemException e) {

        			throw e;    
        		}
        		
        	}
        	
        }   
                     
          PygmyDelegate() throws ApplicationException, SystemException {

            try {
                pygmyHome =(PygmyHome)ServiceLocator.getInstance().getRemoteHome("PYGMYWEB",PygmyHome.class);
                System.out.println("pygmyHome=="+pygmyHome);
  
                if(pygmyHome !=null){
                    pygmyRemote = pygmyHome.create();
                    System.out.println("pygmyRemote=="+pygmyRemote);
                }

                this.custHome=(CustomerHome)ServiceLocator.getInstance().getRemoteHome("CUSTOMERWEB",CustomerHome.class);
                this.custRemote=custHome.create();
                
                this.loanHome=(LoanHome)ServiceLocator.getInstance().getRemoteHome("LOANSWEB", LoanHome.class);
                this.loanRemote=loanHome.create();
                
                this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB", CommonHome.class);
                this.comRemote=comHome.create();
                
                this.loansondeposithome=(LoansOnDepositHome)ServiceLocator.getInstance().getRemoteHome("LOANSONDEPOSITWEB", LoansOnDepositHome.class);
                this.loansondepositremote=loansondeposithome.create();
                

                getCommonRemote();
            } catch (RemoteException e) {
                throw new SystemException("SystemException:NamingException Occured"+e);
            } catch (CreateException e1) {
                throw new ApplicationException("EJBCreateException:NamingException Occured"+e1);
            }
        }

        public PygmyDelegate(String id)throws ApplicationException {
            // reconnect to the session bean for the given id
            reconnect(id);
        }
     

        public String getID() throws ApplicationException {
            try {
                return ServiceLocator.getId(pygmyRemote);
            } catch (ServiceLocatorException e) {
                throw new ApplicationException("ApplicationException unable to get Session Bean"+e);
            }
        }

         public void reconnect(String id) throws ApplicationException {
        try {
            pygmyRemote = (PygmyRemote) ServiceLocator.getService(id);
        } catch(ServiceLocatorException ex){
            throw new ApplicationException("ApplicationException unable to get ServiceLocator"+ex);
        }
     }

         //****************************** Agent Opening Methods *********************
         
         public ModuleObject[] getComboElements(int type)throws RemoteException{
        /*
         type 0 for Self account type
         type 1 for introducer account type
         type 2 for joint account type
         type 3 for agent account type
         */
        if(type==0){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000");
        }
        else if(type==1){
            comboElements= getComRemote().getMainModules(2,"1001000,1002000,1007000");
        }
        else if(type==2){
            comboElements=getComRemote().getMainModules(2,"1002000,1007000");
            for(int i=0;i<comboElements.length;i++)
                System.out.println(comboElements[i].getModuleAbbrv());
        }
        else if(type==3){
             comboElements=getComRemote().getMainModules(2,"1013000");
            for(int i=0;i<comboElements.length;i++)
                System.out.println(comboElements[i].getModuleAbbrv());
        }
        return comboElements;
        }
         
         
       
         

        public String[] getDetailsComboElements(){
            String[] combonames={"Personal","Introducer Type","Joint Holders"};
             return combonames;
        }
        
        public String[] getAgUpdateDetailsComboElements(){
            String[] combonames={"Personal","Introducer Type","Joint Holders"};
             return combonames;
        }
        
        public String[] getSignCombo(){
        	String[] signCombo={"Sign1","Sign2"};
        	return signCombo;
        }
        
       

        public AgentMasterObject getAccountMaster(String mod_code,int ac_num,String Date)throws AccountNotFoundException,RemoteException,CreateException{
        	ModuleObject[] mod_array;
        	mod_array=getComboElements(3);
        	String date=com.scb.common.help.Date.getSysDate();
        	System.out.println(" mod_code"+mod_code);
        	System.out.println("ac_num "+ ac_num);
        	System.out.println("date in delagate--->"+date);
        	agentmasterobject=pygmyRemote.getAgentDetails(mod_code.trim(),ac_num,date);
        	System.out.println("In Delegate1----------------------"+agentmasterobject);
              System.out.println("In Delegate2---------------------"+agentmasterobject.getAccType());
           
        	return agentmasterobject; 
        }
        
        public AccountObject getIntroducerAcntDetails(String acType,int acNo)throws RemoteException{
            AccountObject introObject=null;
            String date=com.scb.common.help.Date.getSysDate();
           
            introObject=getComRemote().getAccount(null,acType,acNo,date);
            return introObject;
        }
            
       public AgentMasterObject getPAGJointAccountCids(String jt_acc_ty,int jt_acc_no)throws RemoteException
       {
          	AgentMasterObject jointObject=null;
          	System.out.println("AcType===="+jt_acc_ty+"\t"+jt_acc_no);
            String date=com.scb.common.help.Date.getSysDate();
             //accountObject=getComRemote().getAccount(null,jt_acc_ty,jt_acc_no,date);
             //System.out.println("accountObject=======>>"+accountObject);
              ModuleObject[] array;
              array=getComboElements(2);
              String actype=null;
              if(jt_acc_ty.startsWith("100")){
            	  System.out.println("after startsWith---->");
              	for(int i=0;i<array.length;i++){
              		System.out.println("In the for of startswith");
              		System.out.println("Array====="+array[i]);
              		System.out.println("array[i].getModuleAbbrv())"+ array[i].getModuleAbbrv());
                		if(jt_acc_ty.equals((array[i].getModuleCode())))
                		{
                			System.out.println(" i think its here");
                			actype=array[i].getModuleCode();
                			System.out.println("M In jtAcc==========="+actype);
                		}
                	}
                }
               
                System.out.println("%%%%%%%%%%%%%%%@@@@@@@@@@@@@@@@   "+actype);
               if(actype!=null)
               {
                	jointObject=pygmyRemote.getPAGJointAccountCids(actype,jt_acc_no);
                	System.out.println("Joint Acc Details==="+actype);
                	System.out.println("Joint Acc Details==="+jt_acc_no);
                	//System.out.println("jointObject1111======>>>"+jointObject.getCid());
                }
                //System.out.println("jointObject======>>>"+jointObject.getCid());
                return jointObject;
       }
       
       public AgentMasterObject getPAGJointAccount(String jt_acc_ty,int jt_acc_no)throws RemoteException
       {
    	   AgentMasterObject jointObject=null;
    	   jointObject=pygmyRemote.getPAGJointAccountCids(jt_acc_ty,jt_acc_no);
    	   System.out.println("Joint Acc Details==="+jointObject);
       	System.out.println("Joint Acc Details==="+jt_acc_ty);
       	System.out.println("Joint Acc Details==="+jt_acc_no);
    	   return jointObject;
       }
       
       public AccountObject getSelfAcntDetails(String trn_mode,String acctype,int accno,String date)throws RemoteException{
      	 AccountObject selfObject=null;
       	
       	System.out.println("!!!!!!! ACType===="+acctype+"-------acNo====="+accno);
       	selfObject=getComRemote().getAccount(null,acctype,accno,date);
       	
       	/*System.out.println("Acctype-------"+selfObject.getAcctype());
       	System.out.println("AccNo-------"+selfObject.getAccno());*/
       
       //	selfObject.setAccno(accno);
       	System.out.println("The acc no is "+accno);
       	return selfObject;
       }
       
       public String getJointDetails(String agt_acctype,int agt_accno,String pers_acc_type,int pers_acc_no,String jtacctype,int jtaccno,int id){
      	System.out.println("m inside getJointDetails");
    	   String name="";
      	 try{
      		 ModuleObject[] array;
               array=getComboElements(2);
               String actype=null;
               System.out.println("jtacctype----------------"+jtacctype);
               if(jtacctype!=null){
               if(jtacctype.startsWith("100"))
               {     System.out.println("In if");
                	for(int i=0;i<array.length;i++)
                	{   System.out.println("in for");
                		if(jtacctype.equals(array[i].getModuleAbbrv()))
                		{
                			actype=array[i].getModuleCode();
                			System.out.println(actype);
                		}
                	}
                }
               
               if(agentmasterobject!=null){
         		 	System.out.println(pers_acc_type+"\n"+pers_acc_no+"\n"+jtacctype+"\n"+jtaccno);
         		 	System.out.println("actype===============1111111"+actype);
         		 	name=getCommonRemote().getJointSBAccountName(null,0, pers_acc_type, pers_acc_no, jtacctype, jtaccno, id);
         		 	System.out.println("name==========111111"+name);
         		 
                  }else {
                 	 System.out.println("jtacctype=========1111"+jtacctype);
          		 	name=getCommonRemote().getJointSBAccountName(null,0, pers_acc_type, pers_acc_no,jtacctype, jtaccno, id);
          		 	System.out.println("name============1111"+name);
                  }	
               
               }
                
               
      	 }catch(Exception e){
               e.printStackTrace();
      	 }   	
      	 return name;
       }

       //Insert
       public int AgentInsert(AgentMasterObject amo,int int_verify,String curdatetime)throws RemoteException{
      	 int agDetails=pygmyRemote.getAgentUpdate(amo,1,Date.getSysDate());
      	 System.out.println("agDetails=======Delegate==insert====>>>"+agDetails);
      	 return agDetails;
       }
       //verify
       public int AgentVerify(AgentMasterObject amo,int int_verify,String curdatetime)throws RemoteException{
        	 int agDetails=pygmyRemote.getAgentUpdate(amo,2,Date.getSysDate());
        	 System.out.println("agDetails=======Delegate=verify=====>>>"+agDetails);
        	 return agDetails;
        }
       //Update
       public int AgentUpdate(String ac_type,int ac_no,int id)throws RemoteException{
      	 int agDetailsUpdate=pygmyRemote.getPAGRefIndUpdate(ac_type, ac_no, id);
      	 return agDetailsUpdate;
       }
       //Delete
       public int AgentDelete(String agenttype,int agentno)throws RemoteException{
      	 int deleted = pygmyRemote.deleteAgent(agenttype,agentno);
      	 return deleted;
       }
       
      
     //############################### End Of Agent################################
        
        //***************** Pygmy Opening Methods *********************
        
        public ModuleObject[] getPgComboElements(int type)throws RemoteException{
            /*
             type 0 for Self account type
             type 1 for Pygmy account type
             type 2 for Agent account type
           
             */
            if(type==0){
                comboElements=getComRemote().getMainModules(2,"1002000,1007000");
            }
            else if(type==1){
                comboElements= getComRemote().getMainModules(2,"1006000");
            }
            else if(type==2){
                comboElements=getComRemote().getMainModules(2,"1013000");
                
            }
            return comboElements;
        }
        
        //20.09.2011
        public ModuleObject[] getNomComboElements(int type)throws RemoteException{
            /*
             type 0 for Self account type
             type 1 for Pygmy account type
             type 2 for Agent account type
           
             */
            if(type==0){
                comboElements=getComRemote().getMainModules(2,"1002000,1007000");
            }
            else if(type==1){
            	System.out.println("im mr. type--1====================>");
                comboElements= getComRemote().getMainModules(2,"1001000");
            }
            else if(type==2){
                comboElements=getComRemote().getMainModules(2,"1013000");
                
            }
            return comboElements;
        }
        
        public String[] getPygDetails(){
        	String[] pgcombo={"Personal","Nominee","Agent Details"};
        	return pgcombo;
        }
        
        public String[] getPygUpdateDetails(){
        	String[] pgcombo={"Personal","Nominee","Agent Details"};
        	return pgcombo;
        }
        
        public String[] getPayMode(){
        	String[] paycombo={"Cash","Transfer","PayOrder"};
        	return paycombo;
        }
        
        
        
        public PygmyMasterObject getAccMaster(int pg_num,String mod_code)throws AccountNotFoundException,RemoteException,CreateException{
        	ModuleObject[] mod_array;
        	mod_array=getPgComboElements(2);
        	
        	pygmymasterobject=pygmyRemote.getPygmyDetails(mod_code.trim(),pg_num);
        	
        	return pygmymasterobject;
        }
        
        public String getAgentName(String actype,int acno)throws RemoteException{
        	String name=null ;
        	try{
        		name=pygmyRemote.getAgentName(actype, acno);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return name;
        }
        
        public AccountObject getPayAccount(String trn_mode,String acctype,int accno,String date)throws RemoteException{
         	 AccountObject payObject=new AccountObject();
      
         	
          	payObject=getComRemote().getAccount(null, acctype,accno, date);
          	
        	
          	
         	 
          	return payObject;
          }
        
        //Insert
        public int pygmyAccountOpen(PygmyMasterObject pygmymasterobject,String detml,String deuser,String curdatetime,String date,int int_verify) throws NomineeNotCreatedException, SignatureException,RemoteException
        	{
        		int pgDetails=pygmyRemote.pygmyAccountOpen(pygmymasterobject, detml, deuser, curdatetime, 0);
        		System.out.println("pg num====>"+pgDetails); 
        		return pgDetails;
        	}
        	
        	/*public int PygNewNumber(PygmyMasterObject pygmymasterobject) throws RemoteException{
        		int pgDetails=0;
        		try{	
        			pgDetails=pygmyRemote.pygmyAccountOpen(pygmymasterobject,"PY02","SANG",Date.getSysDate(),0);
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        		return pgDetails;
        	}*/
        	
        	
        //verify
        	public int pygmyAccountVerify(PygmyMasterObject pygmymasterobject,String detml,String deuser,String curdatetime,String date,int int_verify) throws NomineeNotCreatedException, SignatureException,RemoteException{
        		int pgDetails=0;
        		try{	
        			pgDetails=pygmyRemote.pygmyAccountOpen(pygmymasterobject, detml, deuser, curdatetime, 1);
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        		return pgDetails;
        	}
        	
        //Delete
        	public int pygmyAccountDeletion(String actype,int acno)throws RemoteException{
        		int del=pygmyRemote.pygmyAccountDeletion(actype, acno);
        		System.out.println("$$$$$$$$$$$$$$$$$====="+del);
        		return del;
        	}
        
        public AgentMasterObject getAccountMaster2(int ac_num,String mod_code,String Date)throws AccountNotFoundException,RemoteException,CreateException{
        	ModuleObject[] mod_array;
        	mod_array=getComboElements(3);
        	String date=com.scb.common.help.Date.getSysDate();
        	agentmasterobject=pygmyRemote.getAgentDetails(mod_code.trim(),ac_num,date);
        
        	return agentmasterobject;
        }
        
        public boolean checkPygCid(int cid,int flag)throws RemoteException{
        	boolean custid;
        	custid=comRemote.checkCid(cid, 1);
        	System.out.println("########### CID ^^^^^^^^^^^^^^^"+cid);
        	return false;
        }
        
        public CustomerMasterObject getCustomers(int cid)throws RemoteException, CustomerNotFoundException {
            CustomerMasterObject cmObj=null;
            System.out.println("CID==="+cid);
            cmObj=this.custRemote.getCustomer(cid);
            return cmObj;
         }
        
        //###########################  End Of Pygmy  #########################
        
        //************************** Agent Closing ****************************
        
        public ModuleObject[] getAgentCloseComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
            return comboElements;
        }
        
        public AgentMasterObject[] getAgentNos(String agttype,int close_open_ind )throws RemoteException{
        	AgentMasterObject[] agentmasterobject=null ;
        	
        	agentmasterobject=pygmyRemote.getAgentNos(agttype, 0);
        	for(int i=0;i<agentmasterobject.length;i++){
        		
        	if(agentmasterobject[i].getAgentNumber()!=0){
        						
        		        		System.out.println("%%%%%%%%%Agent No&&&&&&&&&&&&&&&"+agentmasterobject[i].getAgentNumber());
        					}
        	}
        	return agentmasterobject;
        }
        
        public PygmyMasterObject[] getAccountsUnderAgent(String agttype,int agentno){
        	PygmyMasterObject[] pmobj=null;
        	System.out.println("--->>.--"+agttype);
        	System.out.println("--->>.--"+agentno);
        	try{
        		pmobj=pygmyRemote.getAccountsUnderAgent(agttype, agentno);
        		System.out.println("=======111111112222222223333333"+pmobj);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return pmobj;
        }
        
        public AgentMasterObject getAccountMaster1(int ac_num,String mod_code,String Date)throws AccountNotFoundException,RemoteException,CreateException{
        	
        	agentmasterobject=pygmyRemote.getAgentDetails(mod_code.trim(),ac_num,Date);
        
        	return agentmasterobject;
        }
        
        public int agentUpdate(String agttype,int agentno,String curdate,int type)throws RemoteException
        {
        	int exec=0;
        	if(type==0){
        		exec=pygmyRemote.agentUpdate(agttype, agentno, curdate, 0);
        		System.out.println("-----submit------"+exec);
        	}else if(type==1){
        		exec=pygmyRemote.agentUpdate(agttype, agentno, curdate, 1);
        		System.out.println("-----Verify------"+exec);
        	}
        	return exec;
        }
        
        public AgentMasterObject[] getAgentCloseToVerify(String agttype)throws RemoteException{
        	AgentMasterObject[] agnum=null;
        	agnum=pygmyRemote.getAgentCloseToVerify(agttype);
        	System.out.println("-------->>>>>>>>------<<<<<<<<<<-------"+agnum);
        	return agnum;
        }
        //########################### END Agent Closing #######################
        
        //************************* Pygmy Withdrawal **************************
        
        
        public ModuleObject[] getPgWithComboElements(int type)throws RemoteException{
            /*
             type 0 for Self account type
             type 1 for Pygmy account type
             type 2 for Agent account type
           
             */
            if(type==0){
                comboElements=getComRemote().getMainModules(2,"1002000,1007000");
            }
            else if(type==1){
                comboElements= getComRemote().getMainModules(2,"1006000");
            }
            else if(type==2){
                comboElements=getComRemote().getMainModules(2,"1013000");
                
            }
            return comboElements;
        }

        
        
        public String[] getTOP(){
        	String[] typecombo={"Closure","PartialWithdrawal"};
        	return typecombo;
        }
        
        
        public PygmyMasterObject getPyAccMaster(int pg_num,String mod_code)throws AccountNotFoundException,RemoteException,CreateException{
        	ModuleObject[] mod_array=null;
        	mod_array=getPgComboElements(2);
        	pygmymasterobject=pygmyRemote.getPygmyDetails(mod_code.trim(),pg_num);
        	System.out.println("pygmymasterobject=======>>>>"+pygmymasterobject);
        	return pygmymasterobject;
        }
        
        public double getPenaltyAmount(double withAmt)throws RemoteException{
        	double penaltyAmt=0.0;
        	try{
        		penaltyAmt=pygmyRemote.getPenaltyAmount(withAmt);
        		System.out.println("----PENALTY AMT-----"+penaltyAmt);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return penaltyAmt;
        }
        
        public double getClosingBal(String ac_type,int ac_no){
        	ModuleObject[] mod_array;
        	
        	double bal=0.0;
        	double max_amt,fine_amount;
        	try{
        		mod_array=getPgComboElements(1);
        		bal=pygmyRemote.getClosingBalance(ac_type,ac_no);
        		for(int i=0;i<mod_array.length;i++){
        			max_amt=Math.round(bal*(mod_array[i].getMaxAmount()/100));
        			
        		}    
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return bal;
        }
        
        public double getPartialWith(String ac_type,int ac_no){
        	ModuleObject[] mod_array;
        	
        	double bal=0.0;
        	double max_amt=0.0,fine_amount;
        	try{
        		mod_array=getPgComboElements(1);
        		bal=pygmyRemote.getClosingBalance(ac_type,ac_no);
        		System.out.println("m in del.....value of bal===========>>"+bal);
        		for(int i=0;i<mod_array.length;i++){
        			System.out.println("mod_array[i].getMaxAmount()====>>"+mod_array[i].getMaxAmount());
        			max_amt=Math.round(bal*(mod_array[i].getMaxAmount()/100));
        			System.out.println("-----BalAmount$$$$$$$$$$$"+max_amt);
        		}    
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return max_amt;
        }
        
        public double fineAmt(String ac_type,int ac_no){
        	ModuleObject[] mod_array;
        	
        	double bal=0.0;
        	double max_amt,fine_amount=0.0;
        	try{
        		mod_array=getPgComboElements(1);
        		bal=pygmyRemote.getClosingBalance(ac_type,ac_no);
        		System.out.println("----Bal Amt-----"+bal);
        		for(int i=0;i<mod_array.length;i++){
        			fine_amount=Math.round(bal * mod_array[i].getStdInst()/100);
        		    System.out.println("-----FineAmount======"+fine_amount);
        		}    
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return fine_amount;
        }
        /*public double calInt(PygmyTransactionObject[] ptObj,String interest,String fine_amount,String loan_balance,String loan_interest){
        	System.out.println("---PrevBal----"+pygmymasterobject.getPrevBalance());
        	double max_with=0.0;
        	
        	if(ptObj!=null){
        		for(int i=0;i<ptObj.length;i++){
        			 max_with=pygmymasterobject.getPrevBalance()+ptObj[i].getInterestPaid()-ptObj[i].getTranAmt()-ptObj[i].getTranAmt()+ptObj[i].getTranAmt();
        			 System.out.println("---Interest----"+ptObj[i].getInterestPaid());
                 	System.out.println("---fine_amount----"+ptObj[i].getTranAmt());
                 	System.out.println("---loan_balance----"+ptObj[i].getTranAmt());
                 	System.out.println("---loan_interest----"+ptObj[i].getTranAmt());
        		}
        		
        	}
       	     return max_with;
       	
        }*/
        
        public double calculateInterest(String pyg_acc_type,int pyg_acc_no,String curdate)throws RemoteException{
        	double calint=0.0;
        	  calint=pygmyRemote.calculateInterest(pyg_acc_type, pyg_acc_no,Date.getSysDate());
        	  System.out.println("----->>>>>.------"+calint);
        	return calint;
        }
        
        public PygmyRateObject getIntDetails(int no_of_months,String curdate){
        	PygmyRateObject prObj=null;
        	try{
        	   prObj=pygmyRemote.getInterestDetails(0, curdate);
        	   System.out.println("------prObj Delegete-----"+prObj);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return prObj;
        }
        
        
        /*public String getName(String ac_type,int acno) throws RemoteException{
        	String str="trn_narr";
        	String payname=null;
        	try{
        	StringTokenizer st=new StringTokenizer(str,"-");
        	
        	String payac_type=st.nextToken();
          	int payac_no=Integer.parseInt(st.nextToken());
          	System.out.println("------->>>>>>>-----"+payac_no);
        	 payname=comRemote.getName(payac_type,payac_no);
        	 System.out.println("------->>>>>>>-----"+payname); 
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return payname;
        }*/
        
        public PygmyTransactionObject[] getPyWithClosureDetails(String ac_type,int ac_no){
        	PygmyTransactionObject[] ptran=null;
        	try{
        		
        		System.out.println("<<<<<<<<<<<--------->>>>>>>>>-------");
        		ptran=pygmyRemote.getClosureDetails(ac_type, ac_no);
        		System.out.println("ptran.length=="+ptran);
        		System.out.println("ptran.length=="+ptran.length);
        		
        		if(ptran!=null){
        			for(int i=0;i<ptran.length;i++){
        				System.out.println("Narr is"+ptran[i].getTranNarration());
        				if(ptran[i].getTranNarration().equalsIgnoreCase("Intr")){
        					ptran[i].getTranAmt();
        					System.out.println("----------------ptran----intr---"+ptran[i].getTranAmt());
        				}
        				
        				if(ptran[i].getTranNarration().equalsIgnoreCase("Loan")){
        					ptran[i].getTranAmt();
        					System.out.println("----------------ptran---loan----"+ptran[i].getTranAmt());
        				}
        				if(ptran[i].getTranNarration().equalsIgnoreCase("Penalty")){
        					ptran[i].getTranAmt();
        					System.out.println("----------------ptran--penalty-----"+ptran[i].getTranAmt());
        				}
        				if(ptran[i].getTranNarration().equalsIgnoreCase("Closure-Cash")){
        					ptran[i].getTranAmt();
        					System.out.println("----------------ptran--closure-----"+ptran[i].getTranAmt());
        				}
        			}
        		}else{
        			return ptran;
        		}
        		
        		
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	return ptran;
        }
        
        public String prevWorkingDay(String date)throws RemoteException{
        	String coll_date;
        	coll_date=comRemote.prevWorkingDay(date);
        	return coll_date;
        }
        
        public boolean checkPygTran(String ac_type,int ac_no,int id)throws RemoteException{
        	boolean chk=pygmyRemote.checkPygTran(ac_type, ac_no, id);
        	System.out.println("M IN DEL"+chk);
        	return chk;
        }
        
        public double getCurrentDayClosingBalance(String ac_type,int ac_no,String date) throws RemoteException
    	{
        	
        	double lnTrn=loanRemote.getCurrentDayClosingBalance(ac_type, ac_no, Date.getSysDate());
        	System.out.println("-----LNTRAN-----"+lnTrn);
        	return lnTrn;
        	
        	
    	}
        
        public double getCurrentIntPay(String actype,int acno,String date) throws RemoteException
        {
        	double lnIntr=0.0;
        	
        	System.out.println("---ac_Type---"+actype);
        	System.out.println("---acno---"+acno);
        	System.out.println("---date---"+date);
        	try{
        		 lnIntr=loansondepositremote.getCurrentIntPay(actype, acno,Date.getSysDate());
        		System.out.println("-----lnIntr-----"+lnIntr);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return lnIntr;
        }
        
        //delete
        
        public int pygmyWithdrwalDeletion(String actype,int accno,int id) throws RemoteException
        {
        	int pygWithDel=0;
        	try{
        		pygWithDel=pygmyRemote.pygmyWithdrwalDeletion(actype, accno, id);
        		System.out.println("pygWithDel=============>>>>>"+pygWithDel);
        	}	
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	return pygWithDel;
        }
        
        //insert
        
        public int closure(double interest,double fine,double loan_balance,double loan_interest,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime,int flag,String g_acctype,int g_acno,String de_tml,String de_user)throws RemoteException
        {
        	int executed=0;
        		executed=pygmyRemote.closure(interest, fine, loan_balance, loan_interest, pay_mode, pay_ac_type, pay_ac_no, curdate, curdatetime, 1, g_acctype, g_acno, "PY02", "SANG");
        		System.out.println("%%%%%%%%%%%$$$$$$$$$$$$$$$#################"+executed);
        	return executed;
        }
        
        
        public int partialWithdraw(double amount,double penalty,String pay_mode,String pay_ac_type,int pay_ac_no,String curdate,String curdatetime)throws RemoteException{
        	int executed=0;
        	System.out.println("-------->>>"+curdate);
        	executed=pygmyRemote.partialWithdraw(amount, penalty, pay_mode, pay_ac_type, pay_ac_no, curdate, curdatetime);
        	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!------------");
        	return executed;
        }
        
        public int verifyClosure(double loan_bal,double loan_interest,String curdate,String curdatetime,String g_acctype,int g_acno,String g_tml,String g_user)throws RemoteException
        {
        	int exec=0;
        	System.out.println("------Loan_BAl-----"+loan_bal);
        	System.out.println("------Loan_Int-----"+loan_interest);
        	System.out.println("------CURDATE-----"+curdate);
        	System.out.println("------CurDateTime-----"+curdatetime);
        	System.out.println("------g_acctype-----"+g_acctype);
        	System.out.println("------g_acno-----"+g_acno);
        	try{
        		exec=pygmyRemote.verifyClosure(loan_bal, loan_interest, curdate, curdatetime, g_acctype, g_acno,g_tml,g_user);
        		System.out.println("--------->>>>>>--------"+exec);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return exec;
        }
        //######################## END OF PWITH ################################
        
        //********************** Open/Close start *********************
        
        public String[] getComboActive(){
        	String[] comboActive={"Closed A/C","Active A/C","All A/C"};
        	return comboActive;
        }
        
        public PygmyMasterObject[] getOpenClose(String from_date,String to_date,int type){
        	PygmyMasterObject[] pm_array=null;
        	try{
        		System.out.println("The option is "+type);
        		pm_array=pygmyRemote.getOpenClosedReport(from_date, to_date, type);
        		System.out.println("the arr is"+pm_array);
        		for(int i=0;i<pm_array.length;i++){
        		System.out.println("The acc no"+pm_array[i].getAccNo());
        		System.out.println("The date us "+pm_array[i].getAccCloseDate());
        		}
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return pm_array;
        	
        }
        
      //######################## END OF Open ################################
        
        //********************* Agent Remi Report ******************
        public ModuleObject[] getAgentRemiComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
            return comboElements;
        }
        
        public PygmyTransactionObject[] getAgtRemittanceReport(int agentno,String agent_type,String date){
        	PygmyTransactionObject[] pTran=null;
        	try{
        		System.out.println("AgentNo-----"+agentno);
        		System.out.println("AgentType-----"+agent_type);
        		pTran=pygmyRemote.getAgtWiseRemittanceReport(agentno, agent_type, date);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return pTran;
        }
        //code added by amzad on 18/11/2009
        public ModuleObject[] getPymentTypes()throws RemoteException{
        	ModuleObject[] mod_obj=null;
        	try{
        		mod_obj=comRemote.getMainModules(2,"'1002000','1007000','1017000','1018000','1014000','1015000'");
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return mod_obj;
        }
        public PygmyRateObject getIntDetails(){
        	PygmyRateObject pr=null;
        	try{
        		pr=pygmyRemote.getInterestDetails(0, Date.getSysDate());
        	}catch(Exception e){
        		
        	}
        	return pr;
        }
        
        
        //####################### End Remi ##########################
        
        //******************** Interest Regi Form **********************
        
        public QuarterInterestObject[] retrieveInterestRegister(String fdate,String tdate,String query){
        	QuarterInterestObject[] qTran=null;
        	
    		
        	try{
        		System.out.println("-----------------=====+++"+fdate);
        		System.out.println("-----------------=====+++"+tdate);
        		qTran=pygmyRemote.retrieveInterestRegister(fdate, tdate, null);
        		//System.out.println("===== m in delegate 00000===="+qTran.length);
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	return qTran;
        }
        
        //###################### End Regi Form ########################
        
        //********************** Monthly Remi Report *******************
        
        
        public String[] getComboAgents(){
        	String[] comboAgents={"All","Individual"};
        	return comboAgents;
        }
        
        public ModuleObject[] getMonthRemiComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
            return comboElements;
        }
        
        public PygmyTransactionObject[] retrieveMonthlyRemit(String agent_type,int agent_no,String fromdate,String todate){
        	PygmyTransactionObject[] ptran=null;
        	String str=fromdate;
        	String str1=todate;
        	
        	StringTokenizer st=new StringTokenizer(str,"/");
    		String dd=st.nextToken();
    		String mm=st.nextToken();
    		String yy=st.nextToken();
    		str=yy+"-"+mm+"-"+dd;
    		
    		StringTokenizer st1=new StringTokenizer(str1,"/");
    		String dd1=st1.nextToken();
    		String mm1=st1.nextToken();
    		String yy1=st1.nextToken();
    		str1=yy1+"-"+mm1+"-"+dd1;
        	try{
        		
        		System.out.println("^^^^^^^^^^^^^^^"+agent_no);
        		if(agent_no==0){
        			
        			ptran=pygmyRemote.retrieveMonthlyRemit("",0, str, str1);
        		}
        		else{
        			
        			ptran=pygmyRemote.retrieveMonthlyRemit(agent_type, agent_no, str, str1);
        		}
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	return ptran;
        }
        
        
        //###################### End Monthly rep ###########################
        
        //********************** Passbook **********************
        
        public ModuleObject[] getPassBkComboElements(int type)throws RemoteException{
            /*
             type 1 for Pygmy account type
            */
            if(type==1){
                comboElements= getComRemote().getMainModules(2,"1006000");
            }
           return comboElements;
        }
        
        public String[] getPassPayMode(){
        	String[] paycombo={"Cash","Transfer","PayOrder"};
        	return paycombo;
        }
        
        public PygmyTransactionObject[] getPygmyTransaction(String accType,int acno,String fdate,String tdate){
        	PygmyTransactionObject[] pgtran=null;
        	
        	try{
        		
        		pgtran=pygmyRemote.getPygmyTransaction(accType,acno,fdate,tdate);
        		
        		System.out.println("%%%%%%%%%%%%%%"+fdate);
        		System.out.println("%%%%%%%%%%%%%%"+tdate);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return pgtran;
        }

        
        //##################### end passbk #######################
        
        //**************************Register printing*******************
        
        public String[] getRecType(){
        	String[] recType={"Opened Accounts","Closed Accounts"};
        	return recType;
        }
        
        public QuarterInterestObject[] retrieveRegister(String from_date,String to_date,int type){
        	QuarterInterestObject[] qtran=null;
        	String str=from_date;
        	String str1=to_date;
        	
        	StringTokenizer st=new StringTokenizer(str,"/");
    		String dd=st.nextToken();
    		String mm=st.nextToken();
    		String yy=st.nextToken();
    		str=yy+"-"+mm+"-"+dd;
    		
    		StringTokenizer st1=new StringTokenizer(str1,"/");
    		String dd1=st1.nextToken();
    		String mm1=st1.nextToken();
    		String yy1=st1.nextToken();
    		str1=yy1+"-"+mm1+"-"+dd1;
    		
    		
        	
        	try{
        		System.out.println("-------"+str+"\t"+str1+"\t"+type);
        		qtran=pygmyRemote.retrieveRegister(str, str1, type);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return qtran;
        }
        
        //######################## End Register print######################
        
        //******************* IntCal ******************************
        
        public int pigmyCalendarQuerterInterestCalc(String uid,String tml,String curdate,String curdatetime,String br_location){
        	int cal=0;
        	System.out.println("uid----"+uid);
        	System.out.println("tml----"+tml);
        	System.out.println("curdate----"+curdate);
        	System.out.println("curdatetime----"+curdatetime);
        	System.out.println("br_location----"+br_location);
        	try{
        		cal=pygmyRemote.pigmyCalendarQuerterInterestCalc(uid, tml, curdate, curdatetime, br_location);
        		System.out.println("cal------"+cal);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return cal;
        }
        
        //###################### End cal #####################################
        
        
        //**************************** Ledger Printing *****************************
        
        public String[] getLedgerType(){
        	String[] recType={"Active Accounts","Closed Accounts","All Accounts"};
        	
        	return recType;
        }
        
        public PygmyMasterObject[] getPygmyLedgerReport(int type,String from_date,String to_date,int start_accno,int end_accno)throws RemoteException{
        	PygmyMasterObject[] pm=null;
        	
        	pm=pygmyRemote.getPygmyLedgerReport(type, from_date, to_date, start_accno, end_accno);
        	System.out.println("-----Sang-----"+pm.length);
        	return pm;
        }
        
        public PygmyTransactionObject[] getPygmyLedgerTransaction(int type,String from_date,String to_date,int start_accno,int end_accno)throws RemoteException{
        	PygmyTransactionObject[] ptran=null;
        	ptran=pygmyRemote.getPygmyLedgerTransaction(type, from_date, to_date, start_accno, end_accno);
        	System.out.println("------ M In PTRAN ----");
        	return ptran;
        }
        //added by sunitha on 25/11/2009
        public PygmyMasterObject[] getPygmyLedgerReportNew(String from_date,String to_date,int start_accno)throws RemoteException
        {
        	PygmyMasterObject[] pm=null;
        	pm=pygmyRemote.getPygmyLedgerReportNew(from_date, to_date, start_accno);
        	System.out.println("pm===m in delegete=>>>>"+pm.length);
        	return pm;
        	
        }
        
        public PygmyTransactionObject[] getPygmyLedgerTransactionNew(String from_date,String to_date,int start_accno)throws RemoteException
        {
        	PygmyTransactionObject[] ptran=null;
        	ptran=pygmyRemote.getPygmyLedgerTransactionNew(from_date, to_date, start_accno);
        	System.out.println("------ M In PTRAN --delegate--"+ptran.length);
        	return ptran;
        }
        
        //########################## End LP ##########################################
        
        
        //************************* Ack ***********************************
        
        public ModuleObject[] getAckComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
            return comboElements;
        }
        
        public String getAgentName1(String actype,int acno)throws RemoteException{
        	String agnum;
        	
        	agnum=pygmyRemote.getAgentName(actype, acno);
        	return agnum;
        	
        }
        
        public AgentMasterObject getAgentNum(int ac_num,String mod_code,String Date)throws AccountNotFoundException,RemoteException,CreateException{
        	ModuleObject[] mod_array;
        	mod_array=getComboElements(3);
        	String date=com.scb.common.help.Date.getSysDate();
        	agentmasterobject=pygmyRemote.getAgentDetails(mod_code.trim(),ac_num,date);
        
        	return agentmasterobject;
        }
        
       /* public AgentMasterObject getAgentNumber(String agtype,int agno)throws RemoteException{
        	AgentMasterObject agm;
        	agm=pygmyRemote.getAgentNumber(agtype, agno);
        	return agm;
        }*/
        
        public PygmyTransactionObject[] printAcknowledgementSlips(String agenttype,int agentno,String frm_date,String to_date)throws RemoteException{
        	System.out.println("----- agtype---"+agenttype);
        	System.out.println("----- agentno---"+agentno);
        	System.out.println("-----frm_date---"+frm_date);
        	System.out.println("----- to_date---"+to_date);
        	PygmyTransactionObject[] ptrn;
        	
        	ptrn=pygmyRemote.printAcknowledgementSlips(agenttype, agentno, frm_date, to_date);
        	System.out.println("--------7654-----"+ptrn);
        	return ptrn;
        }
        
       /* public AgentMasterObject[] getAgentNo(String agttype,int close_open_ind)throws RemoteException{
        	AgentMasterObject[] agmaster=null;
        	agmaster=pygmyRemote.getAgentNos(agttype, close_open_ind);
        	return agmaster;
        	
        }*/
        
        public String getAgentNames(String actype,int acno)throws RemoteException{
        	String name=null ;
        	try{
        		name=pygmyRemote.getAgentName(actype, acno);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return name;
        }  
        
        public PygmyTransactionObject[] getAgentRemittance(String agenttype,int agentno,String collection_date,boolean flag)throws RemoteException
        {
       	 PygmyTransactionObject[] ptran=null;
       	 
       	 System.out.println("!!>>>>>>>>>>>PTRAN>>>>>>>>>"+agenttype);
       	 System.out.println(">>>>>>>>>>>PTRAN>>>>>>>>>"+agentno);
       	 System.out.println(">>>>>>>>>>>PTRAN>>>>>>>>>"+collection_date);
       	 System.out.println(">>>>>>>>>>>PTRAN-------------"+flag);
       	 ptran=pygmyRemote.getAgentRemittance(agenttype, agentno, collection_date, flag);
       	 
       	 return ptran;
        }
        
        
        public PygmyTransactionObject[] getAgentInformation(String agenttype,int agentno)throws RemoteException{
        	PygmyTransactionObject[] ptr=null;
        	try{
        		ptr=pygmyRemote.getAgentInformation(agenttype, agentno);
        		System.out.println(">>>>>>>>>>>PTRAN>>>>>>>>>"+ptr);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return ptr;
        }
        
        public Object[] getScrollDetails(String ac_type, int agentno,String cur_date,int value) throws RemoteException{
        	HashMap val=new HashMap();
        	Object array_object[]=null;
        	//boolean flag=true;
        	try{
        		if(value==0){
        			val=pygmyRemote.getScrollDetails(ac_type, agentno, cur_date, 0);
        			System.out.println("----- Value issss -----"+val);
        		}
        		else{
        			val=pygmyRemote.getScrollDetails(ac_type, agentno, cur_date, 1);
        			System.out.println("----- Value is 11111111222-----"+val);
        		}	
        		 
        		 array_object=new Object[5];
        			if(val!=null){
            			java.util.Iterator i= val.keySet().iterator(); 
            		while(i.hasNext()){
            			array_object[0]=i.next();
            			array_object[1]=val.get(array_object[0]);
            			StringTokenizer st=new StringTokenizer(array_object[1].toString(),"/");
            			while(st.hasMoreTokens()){
            				array_object[1]=st.nextToken();
            				System.out.println("--1--"+array_object[1]);
            				array_object[2]=st.nextToken();
            				array_object[3]=st.nextToken();
            				array_object[4]=st.nextToken();
            			}
            		}
            		
            	}
        		
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return array_object;
        }
        
        public int storeDailyRemittance(String agttype,int agtno,Object[][] obj,String colldt,int scrlno,int oldscrl,String detml,String deuser,String curdate,String curdatetime,byte indicator)throws RemoteException
        {
        	int r=0;
        	oldscrl=scrlno-1;
        	System.out.println("scrlno"+scrlno);
        	System.out.println("scrlno"+oldscrl);
        	System.out.println("scrlno"+detml);
        	System.out.println("scrlno"+deuser);
        	System.out.println("scrlno"+curdate);
        	System.out.println("scrlno"+curdatetime);
        	System.out.println("scrlno"+indicator);
        	r=pygmyRemote.storeDailyRemittance(agttype, agtno, obj, colldt, scrlno, oldscrl, detml, deuser, curdate, curdatetime,indicator);
        	System.out.println("----M In Daily Remi Submit----"+r);
        	return r;
        }
             
        //######################### End Ack ################################
        
        //*************************** Daily Remittance ********************
        public ModuleObject[] getDrComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
            return comboElements;
        }
        
        //########################## end dr ################################
        
        //********************** Agent Changing ******************************
        
        public ModuleObject[] getAgentChangeComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
        	 
            return comboElements;
        }
        //commented by sunitha
       /* public AgentMasterObject[] getAgentNo(String agttype,int close_open_ind )throws RemoteException{
        	AgentMasterObject[] agentmasterobject=null ;
        	
        	agentmasterobject=pygmyRemote.getAgentNos(agttype, 0);
        	for(int i=0;i<agentmasterobject.length;i++){
        		
        	if(agentmasterobject[i].getAgentNumber()!=0){
        						
        		        		System.out.println("%%%%%%%%%Agent No&&&&&&&&&&&&&&&"+agentmasterobject[i].getAgentNumber());
        					}
        	}
        	return agentmasterobject;
        }
        */
        
        public PygmyMasterObject[] getUnverifiedAgtChange(String frm_agttype,int frm_agtno,String to_agttype,int to_agtno)throws RemoteException
        {
        	System.out.println("Hi M In AgentChange111111--------->>>>>>>>");
        	PygmyMasterObject[] pmaster;
        	pmaster=pygmyRemote.getUnverifiedAgtChange(frm_agttype, frm_agtno, to_agttype, to_agtno);
        	System.out.println("Hi M In AgentChange--------->>>>>>>>");
        	return pmaster;
        }
        
        public int storeAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String de_tml,String de_user,String date,int indicator)throws RemoteException
        {
        	int value=0;
        	System.out.println("--pyg_obj--"+pyg_obj.length);
        	try{
        		value=pygmyRemote.storeAgentChange( pyg_obj, frm_agtty, frm_agtno, to_agtty, to_agtno, de_tml, de_user, date, 1);
        		System.out.println("---- M in submit mode----"+value);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return value;
        }
        
        
        public int VerifyAgentChange(PygmyMasterObject[] pyg_obj,String frm_agtty,int frm_agtno,String to_agtty,int to_agtno,String ve_tml,String ve_user,String date)throws RemoteException
        {
        	int result=0;
        	System.out.println("m in VerifyAgentChange===========");
        	try{
        		result=pygmyRemote.VerifyAgentChange( pyg_obj, frm_agtty, frm_agtno, to_agtty, to_agtno, ve_tml, ve_user, date);
        		System.out.println("---- M in verify mode----"+result);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return result;
        	
        }
        
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& End Of Agent Change &&&&&&&&&&&&&&&&&&&&&&&&&
        //********************** Agent Update *****************************
        
        public ModuleObject[] getAgentMasterComboElements(int type)throws RemoteException{
            /*
             type  for Agent type
             */
        	 if(type==2){
                 comboElements=getComRemote().getMainModules(2,"1013000");
                 
             }
        	 else if(type==0){
                 comboElements=getComRemote().getMainModules(2,"1002000,1007000");
             }
             else if(type==1){
                 comboElements= getComRemote().getMainModules(2,"1001000,1002000,1007000");
             }
             else if(type==3){
                 comboElements=getComRemote().getMainModules(2,"1002000,1007000");
                 for(int i=0;i<comboElements.length;i++)
                     System.out.println(comboElements[i].getModuleAbbrv());
             }
            return comboElements;
        }
        
        public AgentMasterObject getAgentDetails(String agttype,int agtno,String date)throws RemoteException{
        		AgentMasterObject amo=null;
        		System.out.println("---- agttype ---->>>>"+agttype);
        		System.out.println("---- agtno ---->>>>"+agtno);
        		System.out.println("---- date ---->>>>"+date);
        		try{
        			amo=pygmyRemote.getAgentDetails(agttype, agtno, date);
        			System.out.println("inside delegate cid--->"+amo.getCloseInd());
        		}catch(Exception e){
            		e.printStackTrace();
            	}
        	return amo;
        }
        
        public String[] getDetailsElements(){
            String[] combonames={"Personal","Introducer Type","Joint Holders"};
             return combonames;
        }
        
        
        public AgentMasterObject getAgtDetailsForMasterUpdation(String agttype,int agtno,String date)
        {
        	AgentMasterObject agm=null;
        	try{
        		agm=pygmyRemote.getAgtDetailsForMasterUpdation(agttype, agtno, date);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return agm;
        }
     
        public int updateAgentMaster(AgentMasterObject amo)throws RemoteException{
        	int up=0;
        	System.out.println("amo.getAccType()--------->>>>>>>>>>>>>>"+amo.getAccType());
        	up=pygmyRemote.updateAgentMaster(amo);
        	return up;
        }
        
        public int checkAgCid(String cid)throws RemoteException{
        	int custid;
        	custid=pygmyRemote.checkCid(cid);
        	System.out.println("########### CID ^^^^^^^^^^^^^^^"+cid);
        	return custid;
        }
        
        
        public String getCustomerName(String custid)throws RemoteException{
        	String name;
        	name=comRemote.getCustomerName(custid);
        	System.out.println("---------======-------"+name);
        	return name;
        }
        
        public AccountObject getSelfAcnt(String trn_mode,String acType,int acNo,String date)throws RemoteException{
         	 AccountObject selfObject=null;
          	
          	System.out.println("!!!!!!! ACType===="+acType+"########acNo====="+acNo);
          	selfObject=getComRemote().getAccount(null,acType,acNo,date);
          	return selfObject;
        }
        
        public String getJointDetail(String agt_acctype,int agt_accno,String pers_acc_type,int pers_acc_no,String jtacctype,int jtaccno,int id)throws RemoteException{
        	String jtAcno=null;
        	try{
        		jtAcno=comRemote.getJointSBAccountName(agt_acctype, agt_accno, pers_acc_type, pers_acc_no, jtacctype, jtaccno, id);
        		System.out.println("joint Account Name=========>>>"+jtAcno);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return jtAcno;
        }
        //######################## end of agent update ###########################
        
        
        
        
        //********************* Signature Details***********************************
        
        public ModuleObject[] getSignComboElements(int type)throws RemoteException{
            /*
             type 0 for Self account type
             */
            if(type==0){
                comboElements=getComRemote().getMainModules(2,"1002000,1007000");
            }
            
            return comboElements;
        }
        
       
     public SignatureInstructionObject[] getSignature(int accno,String type){
    	 SignatureInstructionObject am[]=null;
    	 try{
    		 System.out.println("--- 1 ----"+accno);
    		 System.out.println("--- 1 ----"+type);
    		am=comRemote.getSignatureDetails(accno,type);
    		if(am!=null){
    		for(int i=0;i<am.length;i++){
    		System.out.println("--- 2 ----"+am[i].getSAccType());
   		 	System.out.println("--- 2 ----"+am[i].getSAccNo());
    		}}
    	 }catch(Exception e){
             e.printStackTrace();
    	 }  
    	 return am;
     }
     
     public int pygGetNoOfMonths(String date_first,String date_second){
    	 int months=0;
    	 try{
    		 	months=comRemote.getNoOfMonths(date_first,date_second);
    		 	System.out.println("------MONTHS------"+months);
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return months;
     }
     
    
     
  //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  COMMON METHODS $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$   
     public CustomerMasterObject getCustomer(int cid)throws RemoteException, CustomerNotFoundException {
         CustomerMasterObject cmObj=null;
         System.out.println("CID==="+cid);
         cmObj=this.custRemote.getCustomer(cid);
         System.out.println("CID=================="+cid);
         return cmObj;
      }
     
     public ModuleObject[] getModules(int type,String code)throws RemoteException
     {
    	 comboElements=getComRemote().getMainModules(type,code);
    	 return comboElements; 
     }
     
     private CommonRemote getCommonRemote()throws RemoteException,CreateException,ServiceLocatorException {
         this.comHome=(CommonHome)ServiceLocator.getInstance().getRemoteHome("COMMONWEB",CommonHome.class);
         this.comRemote=comHome.create();
         return comRemote;
     }
     
     public HashMap getInterest(String string_today,PygmyMasterObject pygmymasterobject,PygmyTransactionObject pTranObject[]){
    	 double balance = 0.0,fine_amount=0.0,interest=0.0,loan_bal=0.0;
    	 HashMap map=null;
    	 balance = pygmymasterobject.getPrevBalance();
    	 int noof_days = Validations.dayCompare(pygmymasterobject.getAccOpenDate(),string_today);
    	 int no_of_month=0;
    	 PygmyRateObject pygmyObj=null;
    	 PygmyRateObject pro;
    	 ModuleObject[] array_moduleobject_pygmy=null;
    	 
		try{
			    no_of_month= comRemote.getNoOfMonths(pygmymasterobject.getAccOpenDate(),string_today);
			    pygmyObj=pygmyRemote.getInterestDetails(no_of_month,string_today);
			    array_moduleobject_pygmy=getPgWithComboElements(1);
			    map=new HashMap();
			    System.out.println("pTranObject >> "+pTranObject);
			    //loan_bal=pTranObject[0].getTranAmt();
			 
			    if(pygmyObj!=null && pygmyObj.getIntRate()==0){
			    	fine_amount=Math.round(balance*array_moduleobject_pygmy[0].getStdInst()/100);
			    	interest=0;
			    	
			    }else{
			    	fine_amount = 0;
			    	interest = pygmyRemote.calculateInterest(pygmymasterobject.getAccType(),pygmymasterobject.getAccNo(),Date.getSysDate());
			    	pro=pygmyRemote.getInterestDetails(0,Date.getSysDate());
			    	
			    }
			    map.put("FineAmt",fine_amount);
		    	map.put("Interest",interest);
		    	map.put("CloseBal",(pygmymasterobject.getPrevBalance()+interest-fine_amount-loan_bal));
		    	map.put("MaxWithdraw",(balance+interest-fine_amount-loan_bal));
		    	map.put("WithdrawAmt",(balance+interest-fine_amount-loan_bal));
		    	
		    	
		} catch(RemoteException re){
            re.printStackTrace();
            return map;
        }
		 catch(SQLException se){
             se.printStackTrace();
             return map;
         }
		 return map;
    }

	
     //------------------------- Pygmy Update ------------------
     
     public ModuleObject[] getPygmyMasterComboElements(int type)throws RemoteException{
         /*
          type 1 for Agent type
          type 0 for PayAcc type
          type 2 for Pygmy type
          type 3 for Loan type
          */ 
     	 if(type==1){
              comboElements=getComRemote().getMainModules(2,"1013000");
              
          }
     	 else if(type==0){
              comboElements=getComRemote().getMainModules(2,"1002000,1007000");
          }
          
     	 
          else if(type==2){
              comboElements= getComRemote().getMainModules(2,"1006000");
          }
          else if(type==3){
              comboElements= getComRemote().getMainModules(3,"1008006");
          }
          
         return comboElements;
     }
     
     public String[] getPygmyMasterPayMode(){
     	String[] paycombo={"Cash","Transfer","PayOrder"};
     	return paycombo;
     } 
	
     public String[] getAddressType(){
    	 String[] addr={"Residential","Communication","Office","Permanent"};
    	 return addr;
     }
     
     public String[] getPygMasterDetails(){
     	String[] pgcombo={"Personal","Nominee","JointHolders","Signature Instruction","Introducer"};
     	return pgcombo;
     }
     
     public PygmyMasterObject getPyAccMasterDetails(int pg_num,String mod_code)throws AccountNotFoundException,RemoteException,CreateException
     {
    	
     	pygmymasterobject=pygmyRemote.getPygmyDetails(mod_code.trim(),pg_num);
     	System.out.println("The account status is  ---------------------->"+pygmymasterobject.getAccStatus());
     	System.out.println("------1111------");
     	
     	/*AddressTypesObject[] addr_obj=comRemote.getAddressTypes();
     	for(int i=0;i<addr_obj.length;i++){
     			pygmymasterobject.getAddrDesc();
     	}*/
     			
     		
     	if(pygmymasterobject.getAccStatus()!=null){
     	if(pygmymasterobject.getLnAvailed().equalsIgnoreCase("T")){
     		comboElements= getComRemote().getMainModules(3,pygmymasterobject.getLnAccType());
     		String lnactype=pygmymasterobject.getLnAccType();
     		int lnacno=pygmymasterobject.getLnAccNo();
     		pygmymasterobject.setLnAccType(lnactype);
     		pygmymasterobject.setLnAccNo(lnacno);
     		
		}else{
			pygmymasterobject.setLnAccType(null);
     		pygmymasterobject.setLnAccNo(0);
		}
     	}
     	
     	return pygmymasterobject;
     }
     
     
     public AddressTypesObject[] getAddressTypes()throws RemoteException{
    	 AddressTypesObject[] addr_obj=null;
    	 addr_obj=comRemote.getAddressTypes();
    	 System.out.println("**************"+addr_obj);
    	 return addr_obj;
     }
     
     public AccountObject getPayAccounts(String trn_mode,String acctype,int accno,String date)throws RemoteException{
     	 AccountObject payObject=new AccountObject();
  
     	
      	payObject=getComRemote().getAccount(null, acctype,accno, date);
      	
    	System.out.println("!!!!!!! ACType===="+acctype+"#######Acno==="+accno);
    	if(payObject!=null)
    		System.out.println("--- Name----"+payObject.getAccname());
      	
     	 
      	return payObject;
      }
     
     public int updatePygmyMaster(PygmyMasterObject amo)throws RemoteException{
    	 int upPyg=0;
    	 System.out.println("----- AMO-----"+amo);
    	 upPyg=pygmyRemote.updatePygmyMaster(amo);
    	 
    	 return upPyg;
     }
     
     //**************** End of PygmyMaster*********************
     
     public PygmyRateObject[] commissionRatesChange()throws RemoteException{
    	 PygmyRateObject[] comRate=null;
    	 comRate=pygmyRemote.commissionRatesChange();
    	 return comRate;
     }
     
     public int insertCommissionRt(PygmyRateObject comm_object,String tml,String uid,String curdate,String curdatetime)throws RemoteException{
    	 int insert_val;
    	 System.out.println("---CurDate----"+curdate);
    	 System.out.println("---CurDate----"+curdatetime);
    	 System.out.println("---comm_Obj---"+comm_object.getAgentType());
    	 System.out.println("---tml---"+tml);
    	 System.out.println("---uid---"+uid);
    	 comm_object.setAgentType("1013001");
    	 //System.out.println("From date in comm object in daelegate"+comm_object.getFromDate());
    	 insert_val=pygmyRemote.insertCommissionRt(comm_object, tml, uid, curdate,curdatetime);
    	 
    	 System.out.println("----insert_val----"+insert_val);
    	 return insert_val;
     }
     
     public PygmyRateObject[] getPygmyRate()throws RemoteException{
    	 PygmyRateObject[] pygRate=null;
    	 pygRate=pygmyRemote.getPygmyRate();
    	 System.out.println("======23424234232*^(^*^^^**^======="+pygRate.length);
    	 return pygRate;
    	 
     }
     
     public int insert_updatePygmyRate(PygmyRateObject[] po,String tml,String user,String datetime,int update_delete_indicator)throws RemoteException{
    	 int insertPygInt=0;
    	 System.out.println("---pygrate---"+po.length);
    	 System.out.println("---tml---"+tml);
    	 System.out.println("---user---"+user);
    	 System.out.println("---datetime---"+datetime);
    	 System.out.println("---update_delete_indicator---"+update_delete_indicator);
    	 
    	 //PygmyRateObject[] po1=new PygmyRateObject[]{po[0]};
    	 try{
    		 System.out.println("At 1569n in delegate po.length  "+po.length);
    		 
    	     insertPygInt=pygmyRemote.insert_updatePygmyRate(po, tml, user,datetime, update_delete_indicator);
    	     System.out.println("value inserted insertPygInt  "+insertPygInt);
    	     if(insertPygInt==0){System.out.println("success"+insertPygInt);}
    		 }catch(Exception e){
    		 e.printStackTrace();
    	 }	 
    	
    	 return insertPygInt;
     }
     
     public int storeNominee(NomineeObject nom[],String actype,int acno,String date){
    	 int nomi=0;
    	 try{
    		 nomi=comRemote.storeNominee(nom, actype, acno, date);
    		 System.out.println("----->>>><<<<<<<<-----"+nomi);
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	 return nomi;
     }
     
     public NomineeObject[] getNominee(int reg_no)throws RemoteException{
    	 NomineeObject[] nomi;
    	 
    	 nomi=comRemote.getNominee(reg_no);
    	 
    	 return nomi;
    	 
    	 
    	 
     }
     public PygmyMasterObject getPygmyDetails(String pgtype,int pgno)throws RemoteException,AccountNotFoundException
     {
    	 PygmyMasterObject masterObject;
    	 
    	 masterObject = pygmyRemote.getPygmyDetails(pgtype,pgno);
    	 
    	 return masterObject;
    	 
     }
     public PygmyTransactionObject[] getAgentRemittanceForUpdate(String agenttype,int agentno,int scroll_no,String collection_date,String remit_date)throws RemoteException
     {
    	 PygmyTransactionObject[] masterObject;
    	 masterObject = pygmyRemote.getAgentRemittanceForUpdate(agenttype, agentno, scroll_no, collection_date, remit_date);
    	 
    	 return masterObject;
     }
    
     public Object[] checkScrollAttached(int scno,String date,String actype,String acno,int type) throws ScrollNotFoundException
     {
    	 Object[] objects = null;
    	 try
    	 {
    		  objects = comRemote.checkScrollAttached(scno, date, actype, acno, type);
    		 
    	 }
    	 catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	 return objects;
     }
     
     public int verifyRemittance(String agenttype,int agentno,String collection_date,int scrollno,String vetml,String veuser,String curdate,String curdatetime) throws DateFormatException
     {
    	 int i=0;
    	 try {
    		 System.out.println("Inside Delegate----->");
			i = pygmyRemote.verifyRemittance(agenttype,agentno,collection_date,scrollno,vetml,veuser,curdate,curdatetime);
			System.out.println("i value in Delegate========>>"+i);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InOperativeAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FreezedAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return i;
     }
     
     public int getRefInd(String agt_type,int agt_no,String coll_dt,int scrl_no) throws RemoteException
     {
    	 int trn_status=0;
    	 trn_status=pygmyRemote.getRefInd(agt_type,agt_no,coll_dt,scrl_no);
		return trn_status;
    	 
     }
   //Added by Mohsin on 18/11/2009
     public static String getSysDate() {
         Calendar c = Calendar.getInstance();
         try {
             return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
         } catch (DateFormatException e) {
                 e.printStackTrace();
         }
         return null;
     }
  
     public int CommissionRtUpdate(PygmyRateObject comm_object,int value){
    	 try{
    		 String time=com.scb.common.help.Date.getSysTime();
    		 retvalue=pygmyRemote.CommissionRtUpdate(comm_object,getSysDate()+" "+time,value);
    		 
    	 }
    	 catch(Exception ex){ex.printStackTrace();}
    	 return retvalue;
     }
    
}

