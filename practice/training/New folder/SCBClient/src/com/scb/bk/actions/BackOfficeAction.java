package com.scb.bk.actions;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import masterObject.backOffice.VoucherDataObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.UserVerifier;
import masterObject.loans.LoanTransactionObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositTransactionObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.bk.forms.ChequeRegisterPrintingForm;
import com.scb.bk.forms.ClosingBalanceReportForm;
import com.scb.bk.forms.ClosingBalanceSummaryForm;
import com.scb.bk.forms.OdccbalForm;
import com.scb.bk.forms.OpenedClosedAccountsPrintingReportForm;
import com.scb.bk.forms.PaymentVoucherForm;
import com.scb.bk.forms.SBCALedgerForm;
import com.scb.bk.forms.SIDeleteForm;
import com.scb.bk.forms.SIDueDoneForm;
import com.scb.bk.forms.SIEntryForm;
import com.scb.bk.forms.SIExecutionForm;
import com.scb.bk.forms.SIParametersForm;
import com.scb.bk.forms.SIQueryForm;
import com.scb.bk.forms.SIRegisterForm;
import com.scb.bk.forms.TransactionSummaryForm;
import com.scb.bk.forms.TransferVoucherForm;
import com.scb.bk.forms.UnPresentedChequeForm;
import com.scb.bk.forms.VoucherScheduleForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.BackOfficeDelegate;
import com.scb.designPatterns.CashDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.props.MenuNameReader;

import exceptions.DateFormatException;
import general.Validations;
/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 11, 2007 
 * Time: 12:08:37 PM
 * To change this template use File | Settings | File Templates.
 */            
public class BackOfficeAction extends Action {
 private BackOfficeDelegate backOfficeDelegate;
 int accnoexist = 0;
    AccountObject accountObject;
	ShareMasterObject sharemasterobject;
	AdminObject array_adminobject_table[]=null;
	ModuleObject array_moduleobject_mod[]=null;
	ModuleObject[] moduleobject;
	String string_locker_type;
	String[] lock_types;
	int share_cat = 0;
	String[] Lockdesc;
	double double_locker_rent;
	masterObject.general.AccountObject accountobject = null;
	masterObject.loans.LoanMasterObject loanmasterobject = null;
	LoanTransactionObject loantransactionobject = null;
	LoanReportObject loanreportobject = null;
	masterObject.cashier.VoucherDataObject voucherobject = null;
	VoucherDataObject voucherDataObject=null;
	VoucherDataObject[] vch_object=null;
	
 private String path;
 ShareCategoryObject[] sharecategoryobject;
 private String string_duedate=null;
 private ModuleObject[] array_module_obj;
 ModuleObject[] module_obj_array = null;
 final Logger logger=LogDetails.getInstance().getLoggerObject("BackOfficeDelegate");
 List execution_list=new ArrayList(); 
 
 String user,tml;
 private HttpSession session=null,bksession=null;
 public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
     
	 	
     int Instruction_num;
     // logger.info(map.getPath());
     System.out.println("Path------()()()()"+map.getPath());
     AdministratorDelegate admDelegate;
	 Map user_role;
	 FrontCounterDelegate fcDelegatenew;
	 String user,tml;
     bksession=req.getSession();
     user=(String)bksession.getAttribute("UserName");
     tml=(String)bksession.getAttribute("UserTml") ;
     try{
 		synchronized(req) {
				
			
 		if(user!=null){
 			System.out.println("m in if====>>"+user);
 			admDelegate=new AdministratorDelegate();
 			user_role=admDelegate.getUserLoginAccessRights(user,"BK");
 			if(user_role!=null){
 			req.setAttribute("user_role",user_role);
 			System.out.println("pageId======>>>"+req.getParameter("pageId"));
 			if(req.getParameter("pageId")!=null){
 				System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageId"));
 				req.setAttribute("accesspageId",req.getParameter("pageId").trim());
 			}
				
 			}
 			else
 			{
 				//to error page for display
 				path=MenuNameReader.getScreenProperties("0000");
 		        
 		           setErrorPageElements("Sorry, You do not have access to this page",req,path);
 		           return map.findForward(ResultHelp.getError());
 			}
 		}
 		fcDelegatenew=new FrontCounterDelegate();
 		req.setAttribute("acccat",fcDelegatenew.getAccCategories());
 		req.setAttribute("accsubcat",fcDelegatenew.getSubCategories());
 		}
 	}
 	catch(Exception ex){ex.printStackTrace();}
     
     if(map.getPath().equalsIgnoreCase("/BackOffice/SIEntryMenu")){
         try{
         	
                 SIEntryForm siEntryform=(SIEntryForm)form;
                 req.setAttribute("pageNum",siEntryform.getPageId());
                 siEntryform.setInstnum(0);
                 siEntryform.setInvalid_ins("null");
                 siEntryform.setPriority_num(0);
                 
                 System.out.println("going to focus111");
                 System.out.println("page********************"+siEntryform.getPageId());
                 System.out.println(siEntryform.getPageId());
             
             if(MenuNameReader.containsKeyScreen(siEntryform.getPageId())){
                 //path=MenuNameReader.getScreenProperties(siEntryform.getPageidform().getPageId());
             	path=MenuNameReader.getScreenProperties(siEntryform.getPageId());
             	System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQ");
                 logger.info("insideExecutre path"+path);
                 System.out.println("Delegate--------"+backOfficeDelegate);
                 backOfficeDelegate=new BackOfficeDelegate();
                 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrr");
            	   
             	ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
             	 req.setAttribute("Module_Abbr",mod);
                 
                 commonBackOfficeInitParameters(req,backOfficeDelegate); 
                 setSIEntryInitParams(req,path,backOfficeDelegate);
                 clear();
                 //inst_num=siEntryform.getInstruction_num();
                 System.out.println("going to focus222");
                 
                 req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                 req.setAttribute("TabNum","0");	
                 
                 setTabbedPaneInitParamsforSIEntry(req,path,siEntryform);
                 return map.findForward(ResultHelp.getSuccess());
             }
             else{
                  path=MenuNameReader.getScreenProperties("0000");
                  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                  return map.findForward(ResultHelp.getError());
             }
            }catch(Exception e){
                  path=MenuNameReader.getScreenProperties("0000");
                  e.printStackTrace();
                  setErrorPageElements(""+e,req,path);
                  return map.findForward(ResultHelp.getError());
         }
                 
     }
                         
     else if(map.getPath().trim().equals("/BackOffice/SIEntry")){
         try{
              SIEntryForm siEntryForm=(SIEntryForm)form;
              CashDelegate csDelegate=new CashDelegate();
              siEntryForm.setInstnum(0);
            
              siEntryForm.setInvalid_ins("null");
              req.setAttribute("pageNum",siEntryForm.getPageId());
              siEntryForm.setAccnovalid("null"); 
              siEntryForm.setVerifyfun("null");
              if(MenuNameReader.containsKeyScreen(siEntryForm.getPageId())){
              	path=MenuNameReader.getScreenProperties(siEntryForm.getPageId());
              	logger.info("insideExecutre path"+path);
              	backOfficeDelegate=new BackOfficeDelegate();
              	commonBackOfficeInitParameters(req,backOfficeDelegate);
              	setSIEntryInitParams(req,path,backOfficeDelegate);
              	 req.setAttribute("priority","priority_num");
              	 System.out.println("Priority Number------"+siEntryForm.getPriority_num());
                   
              	SIEntryObject siEntryObj=new SIEntryObject();
              	ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
                  req.setAttribute("Module_Abbr",mod);
              	
              	                	  	
              	System.out.println("*************** ********************************");
              	System.out.println("*********Submitting values:"+siEntryForm.getForward());    	
              	System.out.println("*********Submitting values:"+siEntryForm.getInstruction_num());  
              	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+siEntryForm.getPageId());
              	 System.out.println("Priority Number Now is ------"+siEntryForm.getPriority_num());
              	 req.setAttribute("priority", siEntryForm.getPriority_num());
              	if(siEntryForm.getPriority_num()!=0)
              	{
              		System.out.println("Pripority Number-------------"+siEntryForm.getPriority_num());
              	}
              	
              	if(siEntryForm.getInstruction_num().trim()!=null && siEntryForm.getInstruction_num().trim().length()>0)
              	if(Integer.parseInt(siEntryForm.getInstruction_num().trim())==0)
              	{
              		String[][] siEntryData=null;
              		 array_adminobject_table=backOfficeDelegate.retrieveData();
              		 array_moduleobject_mod =backOfficeDelegate.getSIMainMods();
              		 
              		System.out.println("arraymasterobject==="+array_adminobject_table);
              		System.out.println(array_adminobject_table[0].getPriorityNo());
              		siEntryData=new String[array_adminobject_table.length][3];
              		System.out.println(""+array_adminobject_table);
              		//code added for change
              		for (int j = 0; j < array_adminobject_table.length; j++)
          			{
          				
              			siEntryData[j][0] =String.valueOf(array_adminobject_table[j].getPriorityNo());
          				System.out.println(array_adminobject_table[j].getPriorityNo());
          				for (int index = 0; index < array_moduleobject_mod.length; index++)
          				{
          					System.out.println(array_moduleobject_mod[index].getModuleCode());
          					System.out.println(array_adminobject_table[j].getFromType());
          					System.out.println(array_adminobject_table[j].getToType());
          					if (Integer.parseInt(array_moduleobject_mod[index].getModuleCode())== Integer.parseInt(array_adminobject_table[j].getFromType()))
          					{
          						
          						siEntryData[j][1] =array_moduleobject_mod[index].getModuleAbbrv();
          						
          					}
          					if (Integer.parseInt(array_moduleobject_mod[index].getModuleCode())== Integer.parseInt(array_adminobject_table[j].getToType()))
          					{
          						siEntryData[j][2] =	array_moduleobject_mod[index].getModuleAbbrv();
          						
          					}
          				}
          			}
              		for(int k=0;k<siEntryData.length;k++){
              			System.out.println(siEntryData[k][0]+" "+siEntryData[k][1]+" "+siEntryData[k][2]);
              		}
              		//code added for change
              		req.setAttribute("arraymastertable",array_adminobject_table);
              		req.setAttribute("String_adminobject", siEntryData);
              	
              	}
              	else if(Integer.parseInt(siEntryForm.getInstruction_num())!=0 ){
              		
              		System.out.println("Instruction number for fetching details==="+siEntryForm.getInstruction_num());
              		req.setAttribute("instr_no",siEntryForm.getInstruction_num());
              	 System.out.println("%%%%%%%%%%%%%%%%%%%#########------"+siEntryForm.getInstruction_num());
              	 SIEntryObject siretriveObj=backOfficeDelegate.getInfoThruSiNo(Integer.parseInt(siEntryForm.getInstruction_num()),0);
              	 System.out.println("Object VAlue========"+siretriveObj);
              	 if(siretriveObj.obj_userverifier.getVerTml()!=null)
              	 {
              		req.setAttribute("displaymsg","Already Verified!!!!!!!!!!");
              		 System.out.println("Already Verified!!!!!!!!!!");
              	 }
              	 else if(siretriveObj==null)
              	 {
              		req.setAttribute("displaymsg","Invalid Instruction Number!!!!!!!!");
              		 System.out.println("Invalid Instruction Number!!!!!!!!");
              		 
              	 }
              	 else{
                   System.out.println("inside update");
                   System.out.println("setting values for update");
                   System.out.println("Priprity no in  instrociton is--------"+siEntryForm.getPriority_num());
                      String perPath=MenuNameReader.getScreenProperties("0001");
                        AccountObject fromaccObj=backOfficeDelegate.getAccount(siretriveObj.getFromType(),siretriveObj.getFromAccNo());
                        if(fromaccObj!=null){
                           System.out.println("AccountObject.getCid()=="+fromaccObj.getCid());
                           req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(fromaccObj.getCid()));
                        }
                        else{
                            req.setAttribute("personalInfo",new CustomerMasterObject());
                        }
                        
                        req.setAttribute("flag",perPath);
                        req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                  			
            		      AccountObject toaccObj=backOfficeDelegate.getAccount(siretriveObj.getToType(),siretriveObj.getToAccNo());
                        if(toaccObj!=null){
                           System.out.println("AccountObject.getCid()=="+toaccObj.getCid());
                           
                           req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(toaccObj.getCid()));
                        }
                        else{
                            req.setAttribute("personalInfo",new CustomerMasterObject());
                        }
                        
                        req.setAttribute("flag",perPath);
                        req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                   	           	
                   
                        req.setAttribute("TabNum","0");
                        req.setAttribute("SIUpdate",siretriveObj);
                        req.setAttribute("updatebutton","UPDATE"); 
                        req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                     
                        setTabbedPaneInitParamsforSIEntry(req,path,siEntryForm);                                 	 
                   
              	 }
              	}
             	System.out.println("Number is---------"+siEntryForm.getPriority_num());
              	if(siEntryForm.getPriority_num()>0)
              	{
              		AdminObject array_adminobject_table[]=null;
              		ModuleObject array_moduleobject_mod[]=null;
              		SIEntryObject sienrtyobject_receive;
                  	System.out.println("inside retreive data ");
                  	
                  	array_adminobject_table=backOfficeDelegate.retrieveData();
                  	array_moduleobject_mod=backOfficeDelegate.getMainModules(1);
                  	
                  	for (int j = 0; j < array_adminobject_table.length; j++)
						{
//array_object_data_table[0] =new Integer(array_adminobject_table[j].getPriorityNo());
                  		System.out.println(array_adminobject_table[j].getPriorityNo());
for ( int i = 0; i < array_moduleobject_mod.length; i++){
	System.out.println(array_moduleobject_mod[i].getModuleCode());
	System.out.println(array_adminobject_table[j].getFromType());
	System.out.println(array_adminobject_table[j].getToType());
if (Integer.parseInt(array_moduleobject_mod[i].getModuleCode())== Integer.parseInt(array_adminobject_table[j].getFromType()))
		{
		if(Integer.parseInt(siEntryForm.getInstruction_num())==0){
			System.out.println("Array Module Object From ------"+array_moduleobject_mod[i].getModuleAbbrv());
			req.setAttribute("fromacctype", array_moduleobject_mod);
		}
					
	}
if (Integer.parseInt(array_moduleobject_mod[i].getModuleCode())== Integer.parseInt(array_adminobject_table[j].getToType()))
{
	if(Integer.parseInt(siEntryForm.getInstruction_num())==0){
		System.out.println("Array Module To---------"+array_moduleobject_mod[i].getModuleAbbrv());
	}		req.setAttribute("toacctype", array_moduleobject_mod);	
}
}

}
                  	
              	}
          		
  		
              	if(siEntryForm.getDue_date()!=null)
              	{
              		
              		String todays = siEntryForm.getDue_date();
              		String sysdate= backOfficeDelegate.getSysDate();
              		if(todays.equals(sysdate))
              		{
              			req.setAttribute("emsg","Date Should Be Greater Than Today's Date");
              		}
              		req.setAttribute("date",todays);
              	}
              	
              	
              	if(siEntryForm.getNo_of_times_exec()!=null)
              	{
              		//System.out.println("Before calculation up" + int_flag_update);
              		System.out.println("_____________________________");
              		int count = 0;
              		System.out.println("Due Date value"+string_duedate);
              		string_duedate=siEntryForm.getDue_date();
              		if (siEntryForm.getDue_date().trim().equals(""))
              		{
              			siEntryForm.setLast_exec_date("");
              		} 
              		else if (siEntryForm.getNo_of_times_exec().trim().length() != 0)
              		{
              			int flag_calc = 0;
              			if (siEntryForm.getPeriod_mon().trim().length() == 0)
              			{
              				System.out.println("Enter No of Months");
              				
              				flag_calc = 1;
              			}
              			if (siEntryForm.getDays().trim().length() == 0)
              			{
              				System.out.println("Enter No of Days");
              				
              				flag_calc = 1;
              			}
              			if (flag_calc == 0)
              			{
              				
              				//file_logger.info("No of execution " + txt_no_exec.getText().trim());
              				
              				String string_date = string_duedate;
              				while (count < Integer.parseInt(siEntryForm.getNo_of_times_exec().trim())-1)
              				{
              					string_date =general.Validations.DateAdd(string_date,(siEntryForm.getPeriod_mon().trim().length() != 0)	? Integer.parseInt(siEntryForm.getPeriod_mon().trim())	: 0);
              					System.out.println("After Adding Months:" + string_date);
              					System.out.println(	"Period days:" + siEntryForm.getDays().trim());
              					try {
              						string_date =	general.Validations.addDays(string_date,	(siEntryForm.getDays().trim().length() != 0)	? Integer.parseInt(siEntryForm.getDays().trim())	: 0);
              					} catch (NumberFormatException e) {
              						// TODO Auto-generated catch block
              						e.printStackTrace();
              					} catch (DateFormatException e) {
              						// TODO Auto-generated catch block
              						e.printStackTrace();
              					}
              					System.out.println("After adding days calculation " + string_date);
              					count++;
              				}
              				siEntryForm.setLast_exec_date(string_date);
              			}
              			flag_calc = 0;
              		}
              	}
         
              	//for submit
              	req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
              	req.setAttribute("TabNum","0");
              	System.out.println("*********************************>>>>>>>>>**************"+siEntryForm.getTabPaneHeading());
              	if(siEntryForm.getTabPaneHeading()!=null){  
              		System.out.println("*********************************>>>>>>>>>"+siEntryForm.getTabPaneHeading());
                  	 if(siEntryForm.getTabPaneHeading().equalsIgnoreCase("ToAccountNumber")){
                  		System.out.println("-----------------------------testing123******************");
                       	req.setAttribute("TabNum","1");
                       }   
                  	 if(siEntryForm.getTabPaneHeading().equalsIgnoreCase("FromAccountNumber")){
                  		System.out.println("-----------------------------testing123******************");
                       	req.setAttribute("TabNum","0");
                       }  
                  	   
              	}
              	
              	
              	setTabbedPaneInitParamsforSIEntry(req,path,siEntryForm);
             	    
              	             
                  System.out.println("from acc no:"+siEntryForm.getFrom_ac_no());
                   if( !siEntryForm.getFrom_ac_no().equalsIgnoreCase("") && siEntryForm.getFrom_ac_no()!= null && Integer.parseInt(siEntryForm.getFrom_ac_no())!=0 )
                   {
           			String from_ac_type=null;
           			int i=0;
                      String perPath=MenuNameReader.getScreenProperties("0001");
                      //req.setAttribute("TabNum","0"); 
                      
                      System.out.println("From Account Type1 :"+ siEntryForm.getFrom_ac_ty());
                      System.out.println("From Account Number1 :"+Integer.parseInt(siEntryForm.getFrom_ac_no()));
                     
                      System.out.println("Si entry-----"+siEntryForm.getFrom_ac_ty());
                      for(i=0;i<mod.length;i++){
                      	
                       	if(mod[i].getModuleCode().equalsIgnoreCase(siEntryForm.getFrom_ac_ty())){
                               from_ac_type=siEntryForm.getFrom_ac_ty();
                               System.out.println("From Account Type from shreya---------"+from_ac_type);
                       	}
                       
                       }
                      
                      System.out.println("from_ac_type"+from_ac_type);
                      System.out.println("from_ac_no"+Integer.parseInt(siEntryForm.getFrom_ac_no()));
                      AccountObject fromaccObj=backOfficeDelegate.getAccount(from_ac_type,Integer.parseInt(siEntryForm.getFrom_ac_no()));
                      
                       if(fromaccObj!=null){
                      	System.out.println("AccountObject.getCid()=="+fromaccObj.getCid());
                      	for(i=0;i<mod.length;i++){
                      	if(mod[i].getModuleCode().startsWith("1002") ||mod[i].getModuleCode().startsWith("1007"))
			               	{
								if(fromaccObj.getFreezeInd().equals("T"))
				               	{
				               	   	
									req.setAttribute("displaymsg","This Account Is Freezed");
				                }
				               	
				               	else if(fromaccObj.getVerified()==null)
				               	{
				               		
				               		req.setAttribute("displaymsg","Account is not verified!!Please verify account first");
				               	}
				               	
				               	else if(fromaccObj.getDefaultInd().equals("T"))
				               	{
				               	   	
				               		req.setAttribute("displaymsg","This Account Is Default");
				               	}
				               	   
				               	else if(fromaccObj.getAccStatus().equals("I"))
				               	{
				               		req.setAttribute("displaymsg","Account Is  Inoperative");
				               	}   
				               	else if(fromaccObj.getAccStatus().equals("C"))
				               	{
				               	   	
				               		req.setAttribute("displaymsg","Account Is Already Closed");
				               	}
			               	}
                      	}	
                      	req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(fromaccObj.getCid()));
                      	req.setAttribute("TabNum","0");
                       }
                     else{
                  	   
                    	 req.setAttribute("displaymsg","Given Account Number Not Found");
                     	 req.setAttribute("personalInfo",new CustomerMasterObject());
                         }
                       req.setAttribute("flag",perPath);
                       req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                   }                   
                   
                   if(!siEntryForm.getTo_ac_no().equalsIgnoreCase("") && siEntryForm.getTo_ac_no()!= null &&   Integer.parseInt(siEntryForm.getTo_ac_no())!=0){
           			String to_ac_type=null;
           			//req.setAttribute("TabNum","1");
           			
           			String perPath=MenuNameReader.getScreenProperties("0001");
                      System.out.println("To Account Type1 :"+ siEntryForm.getTo_ac_ty());
                      System.out.println("To Account Number1 :"+siEntryForm.getTo_ac_no());
                      for(int i=0;i<mod.length;i++){
                       	if(mod[i].getModuleCode().equalsIgnoreCase(siEntryForm.getTo_ac_ty())){
                               to_ac_type=siEntryForm.getTo_ac_ty();                      	
                       	}
                       	
                       }
                       System.out.println("to_ac_type"+to_ac_type);
                       System.out.println("to_ac_no"+Integer.parseInt(siEntryForm.getTo_ac_no()));
                       AccountObject toaccObj=backOfficeDelegate.getAccount(to_ac_type,Integer.parseInt(siEntryForm.getTo_ac_no()));
                       if(toaccObj!=null){
                      	  System.out.println("AccountObject.getCid()=="+toaccObj.getCid());
                      	  for(int i=0;i<mod.length;i++){
                            	if(mod[i].getModuleCode().startsWith("1002") ||mod[i].getModuleCode().startsWith("1007"))
    			               	{
    								if(toaccObj.getFreezeInd().equals("T"))
    				               	{
    				               	   	
    									req.setAttribute("displaymsg","This Account Is Freezed");
    				                }
    				               	
    				               	else if(toaccObj.getVerified()==null)
    				               	{
    				               		
    				               		req.setAttribute("displaymsg","Account is not verified!!Please verify account first");
    				               	}
    				               	
    				               	else if(toaccObj.getDefaultInd().equals("T"))
    				               	{
    				               	   	
    				               		req.setAttribute("displaymsg","This Account Is Default");
    				               	}
    				               	   
    				               	else if(toaccObj.getAccStatus().equals("I"))
    				               	{
    				               		req.setAttribute("displaymsg","Account Is  Inoperative");
    				               	}   
    				               	else if(toaccObj.getAccStatus().equals("C"))
    				               	{
    				               	   	
    				               		req.setAttribute("displaymsg","Account Is Closed");
    				               	}
    			               	}
                            	else if(mod[i].getModuleCode().startsWith("1004"))
				               	{
									if(toaccObj.getVerified()==null)
					               	{
										req.setAttribute("displaymsg","Account is not verified!!Please verify account first");
					               	}
									
									else if(toaccObj.getClose_ind() != 0 && toaccObj.getClose_ind() <6)
				                	{
				                	   	
										req.setAttribute("displaymsg","Account Is Closed");
				                	}
				               	}
								else if(mod[i].getModuleCode().startsWith("1008")||mod[i].getModuleCode().startsWith("1010"))
				               	{
									if(toaccObj.getVerified()==null)
					               	{
					               		
										req.setAttribute("displaymsg","Account is not verified!!Please verify account first");
					               	}
									
									else if(toaccObj.getAccStatus().equals(null))
				                	{
				                	   	
										req.setAttribute("displaymsg","Account Is Closed");
				                	}
				               	}
							
                            	}
                      	  
                      	  
                      	  req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(toaccObj.getCid()));
                            req.setAttribute("TabNum","1");
                           }
                       else{
                    	   req.setAttribute("displaymsg","Given Account Number Not Found");
                           req.setAttribute("personalInfo",new CustomerMasterObject());
                           }
                       req.setAttribute("flag",perPath);
                       req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                   }         	
                  
                    System.out.println("TabHeading"+siEntryForm.getTabPaneHeading());
                   /*if(siEntryform.getTabPaneHeading()!=null){ 
               	 if(siEntryform.getTabPaneHeading().equalsIgnoreCase("ToAccountNumber")){
                    	req.setAttribute("TabNum","1");
                    }   
                   }*/
                 	if(siEntryForm.getForward()!=null){
                  if(siEntryForm.getForward().trim().equalsIgnoreCase("SUBMIT")){
                                           	
                     	//submitting values
                  	System.out.println("pri_no:"+siEntryForm.getPriority_num());
              		siEntryObj.setPriorityNo(siEntryForm.getPriority_num());
              		
              		for(int i=0;i<mod.length;i++)
						{
							if(siEntryForm.getFrom_ac_ty().equals(mod[i].getModuleCode()))
							{
								System.out.println("FromAcc_type:"+mod[i].getModuleCode());
								siEntryObj.setFromType(mod[i].getModuleCode());
							}
						}
              	
              		System.out.println("FromAcc_No:"+Integer.parseInt(siEntryForm.getFrom_ac_no()));
              		siEntryObj.setFromAccNo(Integer.parseInt(siEntryForm.getFrom_ac_no()));
              		
              		for(int i=0;i<mod.length;i++)
						{
							if(siEntryForm.getTo_ac_ty().equals(mod[i].getModuleCode()))
							{
								siEntryObj.setToType(mod[i].getModuleCode());
								System.out.println("To Account Type :"+mod[i].getModuleCode());
							}
						}
              	                		
              		System.out.println("*********************"+Integer.parseInt(siEntryForm.getTo_ac_no()));
              		System.out.println("ToAcc_No:"+Integer.parseInt(siEntryForm.getTo_ac_no()));
              		System.out.println("*********************11"+Integer.parseInt(siEntryForm.getTo_ac_no()));
              		siEntryObj.setToAccNo(Integer.parseInt(siEntryForm.getTo_ac_no()));
              		System.out.println("Loan_Option:"+Integer.parseInt(siEntryForm.getLoan_option()));
              		siEntryObj.setLoanOpt(Integer.parseInt(siEntryForm.getLoan_option()));
              		System.out.println("Due Date:"+siEntryForm.getDue_date());	
              		siEntryObj.setDueDt(siEntryForm.getDue_date());
              		System.out.println("Period in months:"+Integer.parseInt(siEntryForm.getPeriod_mon()));
              		siEntryObj.setPeriodMonths(Integer.parseInt(siEntryForm.getPeriod_mon()));
              		System.out.println("Days:"+Integer.parseInt(siEntryForm.getDays()));
              		siEntryObj.setPeriodDays(Integer.parseInt(siEntryForm.getDays()));
              		System.out.println("Amount:"+Double.parseDouble(siEntryForm.getAmount()));
              		siEntryObj.setAmount(Double.parseDouble(siEntryForm.getAmount()));
              		System.out.println("No of times exec:"+Integer.parseInt(siEntryForm.getNo_of_times_exec()));
              		siEntryObj.setNoExec(Integer.parseInt(siEntryForm.getNo_of_times_exec()));
              		System.out.println("Last_exec_date :"+siEntryForm.getLast_exec_date());
              		siEntryObj.setLastExec(siEntryForm.getLast_exec_date());
              		System.out.println("Expiry days :"+Integer.parseInt(siEntryForm.getExpiry_days()));
              		siEntryObj.setExpiryDays(Integer.parseInt(siEntryForm.getExpiry_days()));
              		siEntryObj.obj_userverifier.setUserTml("CA99");
              		siEntryObj.obj_userverifier.setUserId("SHIP");
              		siEntryObj.obj_userverifier.setUserDate(backOfficeDelegate.getSysDateTime());
              		Instruction_num=backOfficeDelegate.storeInfo(siEntryObj);
              		 System.out.println("+++++++Succesfully Inserted+++++"+siEntryForm.getInsvalue());
              		
              		 if(siEntryForm.getInsvalue().equalsIgnoreCase("true")){
              			System.out.println("Generated Instruction Number**********:"+Instruction_num);
              			req.setAttribute("displaymsg","Note Down Standing Instruction Number:"+Instruction_num);
                          System.out.println("=====Succesfully Inserted======="); 
                        }
              	
              		
                  }
                
                
               else if(siEntryForm.getForward().equalsIgnoreCase("UPDATE")){ 
              	 SIEntryObject siEntryUpdate=new SIEntryObject();
              	 
              	 System.out.println("inside update block");
              //	 System.out.println("Instruction:"+Integer.parseInt(siEntryForm.getInstruction_num()));
              	 siEntryUpdate.setInstNo(Integer.parseInt(siEntryForm.getInstruction_num()));
              	 System.out.println(siEntryForm.getPriority_num());                	
              	 siEntryUpdate.setPriorityNo(siEntryForm.getPriority_num());
              	
              	 for(int i=0;i<mod.length;i++)
						{
							if(siEntryForm.getFrom_ac_ty().equals(mod[i].getModuleCode()))
							{
								System.out.println("FromAcc_type:"+mod[i].getModuleCode());
							siEntryUpdate.setFromType(mod[i].getModuleCode());
							}
						}
              	 
              	 //siEntryUpdate.setFrommodAbbrv(siEntryForm.getFrom_ac_ty());
              	 siEntryUpdate.setFromAccNo(Integer.parseInt(siEntryForm.getFrom_ac_no()));
              	 for(int i=0;i<mod.length;i++)
						{
							if(siEntryForm.getTo_ac_ty().equals(mod[i].getModuleCode()))
							{
								siEntryUpdate.setToType(mod[i].getModuleCode());
							}
						}
              	 //siEntryUpdate.setTomodAbbrv(siEntryForm.getTo_ac_ty());
              	 siEntryUpdate.setToAccNo(Integer.parseInt(siEntryForm.getTo_ac_no()));
              	 siEntryUpdate.setLoanOpt(Integer.parseInt(siEntryForm.getLoan_option()));
              	 siEntryUpdate.setDueDt(siEntryForm.getDue_date());
              	 siEntryUpdate.setPeriodMonths(Integer.parseInt(siEntryForm.getPeriod_mon()));
              	 siEntryUpdate.setPeriodDays(Integer.parseInt(siEntryForm.getDays()));
              	 siEntryUpdate.setAmount(Double.parseDouble(siEntryForm.getAmount()));
              	 siEntryUpdate.setNoExec(Integer.parseInt(siEntryForm.getNo_of_times_exec()));
              	 siEntryUpdate.setLastExec(siEntryForm.getLast_exec_date());
              	 siEntryUpdate.setExpiryDays(Integer.parseInt(siEntryForm.getExpiry_days()));
              	 
              	 int update_func=backOfficeDelegate.update(siEntryUpdate);
               	 if(siEntryForm.getInsvalue().equalsIgnoreCase("true")){
              	 System.out.println("*******Instruction Number"+update_func+" is Successfully Updated*********");   
              	req.setAttribute("displaymsg"," Instruction Number:"+update_func+"is Successfully Updated");
               	 System.out.println("outside update"); 
               	 }
               	 
               }
               if(siEntryForm.getForward().equalsIgnoreCase("VERIFY")){
            		 SIEntryObject siretriveObj=backOfficeDelegate.getInfoThruSiNo(Integer.parseInt(siEntryForm.getInstruction_num()),0);
                  	 System.out.println("Object VAlue========"+siretriveObj);
                  	 if(siretriveObj.obj_userverifier.getVerTml()!=null)
                  	 {
                  		req.setAttribute("displaymsg","Instruction Number Is Already Verified!!!!!!!!!!");
                  		 System.out.println("Already Verified!!!!!!!!!!");
                  	 }
            	   else{
              	 backOfficeDelegate.verify(Integer.parseInt(siEntryForm.getInstruction_num()),"CA99","SHIP",backOfficeDelegate.getSysDateTime());
              	 if(siEntryForm.getInsvalue().equalsIgnoreCase("true")){
                  	 System.out.println("*******Instruction Number"+siEntryForm.getInstruction_num()+" is Succesfully Verified*********");
                  	 
                  	req.setAttribute("displaymsg","Instruction Number Verified Sucessfully");
                  	 System.out.println("Verificetion-----------"+siEntryForm.getVerifyfun());
                  	 System.out.println("Succesfully Verified"); 
              	 }
               }
               }
             /*else if(siEntryform.getForward().equalsIgnoreCase("CLEAR")){
            	   System.out.println("clearing form........");
            	    
            	   siEntryform.setPriority_num("");
            	   siEntryform.setFrom_ac_ty("");
            	   siEntryform.setFrom_ac_no("");
            	   siEntryform.setTo_ac_ty("");
            	   siEntryform.setTo_ac_no("");
            	   siEntryform.setLoan_option("");
            	   siEntryform.setDue_date("");
            	   siEntryform.setPeriod_mon("");
            	   siEntryform.setDays("");
            	   siEntryform.setAmount("");
            	   siEntryform.setNo_of_times_exec("");
            	   siEntryform.setLast_exec_date("");
            	   siEntryform.setExpiry_days("");
                  
             }*/  
             }    
                 
            	return map.findForward(ResultHelp.getSuccess());
              }
         
              else{
              		path=MenuNameReader.getScreenProperties("0000");
              		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
              		return map.findForward(ResultHelp.getError());
                }
         
             }
         catch(Exception e){
     	    	 e.printStackTrace();
         		 path=MenuNameReader.getScreenProperties("0000");
               setErrorPageElements(""+e,req,path);
               logger.info(e.getMessage());
               return map.findForward(ResultHelp.getError());
         		  
         	  }
         	  
     	}
     else if(map.getPath().equalsIgnoreCase("/BackOffice/SIDeleteMenu")){
         logger.info("##############"+map.getPath());
            try{
          	 SIDeleteForm siDeleteform=(SIDeleteForm) form;
          	 req.setAttribute("pageNum",siDeleteform.getPageId());
          	  siDeleteform.setDeleteinstnum("null");
          	 siDeleteform.setSucess_del("null");
          	 if(MenuNameReader.containsKeyScreen(siDeleteform.getPageId())){
                   path=MenuNameReader.getScreenProperties(siDeleteform.getPageId());
                   logger.info("insideExecutre path"+path);
                   backOfficeDelegate=new BackOfficeDelegate();
                   setSIDeleteInitParams(req,path,backOfficeDelegate);
                   req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                   req.setAttribute("TabNum","0");
                   setTabbedPaneInitParamsforSIDelete(req,path,siDeleteform);                 
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }
              }catch(Exception e){
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements(""+e,req,path);
                    logger.error(e.getMessage());
                    e.printStackTrace();
                    return map.findForward(ResultHelp.getError());
                                   
           }
              
          
      }
     
    else if(map.getPath().trim().equals("/BackOffice/SIDelete")){
        try{
     	   
     	   System.out.println("HIiiiiiiiiiiiiiiiiiiiiiiiiiii");
      	 SIDeleteForm siDeleteform=(SIDeleteForm) form;
      	 req.setAttribute("pageNum",siDeleteform.getPageidform().getPageId());
      	 siDeleteform.setDeleteinstnum("null");
     	 siDeleteform.setSucess_del("null");
      	 if(MenuNameReader.containsKeyScreen(siDeleteform.getPageId())){
               path=MenuNameReader.getScreenProperties(siDeleteform.getPageId());
               String page=siDeleteform.getPageId();
               logger.info("insideExecutre path"+path);
               backOfficeDelegate=new BackOfficeDelegate();
               SIEntryObject sidelObj=backOfficeDelegate.getInfoThruSiNo(siDeleteform.getInstruction_num(),0 );
            
               setSIDeleteInitParams(req,path,backOfficeDelegate);
               System.out.println("Deleting values:"+siDeleteform.getForward());
             
               if(siDeleteform.getInstruction_num()!=0){
             	  System.out.println("Are You Here");
             	  
             	  if(sidelObj!=null){
             	  if(sidelObj.getDelInd().equalsIgnoreCase("T"))
             	  {
             		   req.setAttribute("displaymsg","ALREADY DELETED!!!!!!!!!!!!!");
             		  System.out.println("ALREADY DELETED!!!!!!!!!!!!!");
             		  
             	  }
             	  
             	  
             	  else if(sidelObj.getAltVeTml()!=null)
             	  {
             		   req.setAttribute("displaymsg","ALREADY Verified!!!!!!!!!!!!!"); 
             		  System.out.println("ALREADY Verified!!!!!!!!!!!!!");
             		  
             	  }
             	
             	  else{
  
                   req.setAttribute("SIDel",sidelObj);
             	  }
             	  }
             	  else
             	  {
             		   req.setAttribute("displaymsg","Invalid Instruction"); 
             		  
             	  }
             	 
             	  
               }
               if(siDeleteform.getForward()!=null){
                  
                   //System.out.println("Deleting values1:"+siDeleteform.getForward()); 
                if(siDeleteform.getForward().equalsIgnoreCase("DELETE")){        
                 	 
                 	 
                         logger.info("Details**************"+siDeleteform.getInstruction_num());
                         String perPath=MenuNameReader.getScreenProperties("0001");
                         
                         AccountObject accObj=backOfficeDelegate.getAccount(sidelObj.getFromType(),sidelObj.getFromAccNo());
                         if(accObj!=null){
                           logger.info("AccountObject.getCid()=="+accObj.getCid());
                           req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(accObj.getCid()));
                         }
                        else{
                           req.setAttribute("personalInfo",new CustomerMasterObject());
                         }
                 
                        req.setAttribute("flag",perPath);
                        req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                 
                        AccountObject accObj1=backOfficeDelegate.getAccount(sidelObj.getToType(),sidelObj.getToAccNo());
                        if(accObj1!=null){
                          logger.info("AccountObject.getCid()=="+accObj1.getCid());
                          req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(accObj1.getCid()));
                         }
                        else{
                          req.setAttribute("personalInfo",new CustomerMasterObject());
                         }
                          req.setAttribute("flag",perPath);
                          req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                          
                          req.setAttribute("TabNum","0");
                          req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                          
                          setTabbedPaneInitParamsforSIDelete(req,path,siDeleteform);    
                          
                          int loan_option_val=sidelObj.getLoanOpt();
                          System.out.println("loan--------"+loan_option_val);
   						String loan_option=null;
   						if(loan_option_val==1)
   							loan_option="Total Amt";
   							
   						else if(loan_option_val==2)
   							loan_option="Principal Amt";
   						else if(loan_option_val==3)
   							loan_option="Due Amt";
   						
   						if(loan_option_val==0)
   						   req.setAttribute("loanoption","------");	
   						else
   							req.setAttribute("loanoption",loan_option);
   					  if(sidelObj!=null){
   		             	  if(sidelObj.getDelInd().equalsIgnoreCase("T"))
   		             	  {
   		             		   req.setAttribute("displaymsg","ALREADY DELETED!!!!!!!!!!!!!");
   		             		  System.out.println("ALREADY DELETED!!!!!!!!!!!!!");
   		             		  
   		             	  }
   		             	  
   		             	  
   		             	  else if(sidelObj.getAltVeTml()!=null)
   		             	  {
   		             		   req.setAttribute("displaymsg","ALREADY Verified!!!!!!!!!!!!!"); 
   		             		  System.out.println("ALREADY Verified!!!!!!!!!!!!!");
   		             		  
   		             	  }
   		             	  
   		             	  
   		             	  else if(siDeleteform.getDelete_ins().equalsIgnoreCase("true")){
                      	   
                      	   System.out.println("RRRRRRRRRRRRRRRRRRUUUUUUUUUUUUUUUUUUHHHHHHHHHH");
                      	 backOfficeDelegate.delete(siDeleteform.getInstruction_num(),tml,user,"userdate",0);
                        	 System.out.println("*******Instruction Number"+siDeleteform.getInstruction_num()+" is Succesfully Deleted*********");   
                        	  req.setAttribute("displaymsg","Succesfully Deleted");
                           System.out.println("Succesfully Deleted"); 
                         }
                      
                                
   					  }       
                        
                       
                     
                 }
                 else if(siDeleteform.getForward().equalsIgnoreCase("VERIFY")){
                 	 
                       logger.info("Details**************"+siDeleteform.getInstruction_num());
                       String perPath=MenuNameReader.getScreenProperties("0001");
                       logger.info("From Account Type from SiObject:"+sidelObj.getFromType());
                       logger.info("From Account Number from SiObject:"+sidelObj.getFromAccNo());
                       AccountObject accObj=backOfficeDelegate.getAccount(sidelObj.getFromType(),sidelObj.getFromAccNo());
                       if(accObj!=null){
                         logger.info("AccountObject.getCid()=="+accObj.getCid());
                         req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(accObj.getCid()));
                       }
                      else{
                         req.setAttribute("personalInfo",new CustomerMasterObject());
                       }
               
                      req.setAttribute("flag",perPath);
                      req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
               
                      AccountObject accObj1=backOfficeDelegate.getAccount(sidelObj.getToType(),sidelObj.getToAccNo());
                      if(accObj1!=null){
                        logger.info("AccountObject.getCid()=="+accObj1.getCid());
                        req.setAttribute("personalInfo",backOfficeDelegate.getCustomer(accObj1.getCid()));
                       }
                      else{
                        req.setAttribute("personalInfo",new CustomerMasterObject());
                       }
                        req.setAttribute("flag",perPath);
                        req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                        
                        req.setAttribute("TabNum","0");
                        req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                        
                        setTabbedPaneInitParamsforSIDelete(req,path,siDeleteform);    
                        
                        int loan_option_val=sidelObj.getLoanOpt();
                        System.out.println("loan--------"+loan_option_val);
 						String loan_option=null;
 						if(loan_option_val==1)
 							loan_option="Total Amt";
 							
 						else if(loan_option_val==2)
 							loan_option="Principal Amt";
 						else if(loan_option_val==3)
 							loan_option="Due Amt";
 						
 						if(loan_option_val==0)
 						   req.setAttribute("loanoption","------");	
 						else
 							req.setAttribute("loanoption",loan_option);

                                 	  
                 	System.out.println("Verified-------------"+siDeleteform.getSucess_verify());
                  	 if(siDeleteform.getSucess_verify().equalsIgnoreCase("true")){
                  		 
                  		 backOfficeDelegate.delete(siDeleteform.getInstruction_num(),tml," uid", "userdate", 1);
                      	System.out.println("*******Instruction Number"+siDeleteform.getInstruction_num()+" is Succesfully Verified*********");   
                      	req.setAttribute("displaymsg","Succesfully Verified");
                      	System.out.println("Succesfully Verified");
                       }
                        
                   }
                 /*else if(siDeleteform.getForward().equalsIgnoreCase("CLEAR")){
               	   siDeleteform=null; 
               	 }*/
                  }
                                         
               else{
                  req.setAttribute("InstNo",new Boolean(true));   	
                } 
               
             return map.findForward(ResultHelp.getSuccess());
      	    }
      else{
    		path=MenuNameReader.getScreenProperties("0000");
    		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
    		return map.findForward(ResultHelp.getError());
    	    } 
        } catch(Exception e){
    		path=MenuNameReader.getScreenProperties("0000");
    		setErrorPageElements(""+e,req,path);
    		logger.error(e);
    		e.printStackTrace();
    		return map.findForward(ResultHelp.getError());
     	}
     }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/UnPresentedChequesMenu")){  // added by parul//
       	try{
       		 
       		
       		String modcode=null,string_query=null;
       		UnPresentedChequeForm chregprintrep=(UnPresentedChequeForm) form;
       		chregprintrep.setValid("null");
       		System.out.println("Get Page ID!!!!!!!!!!"+chregprintrep.getPageId());
       		if(MenuNameReader.containsKeyScreen(chregprintrep.getPageId())){
       			   
       			backOfficeDelegate=new BackOfficeDelegate();
       		 req.setAttribute("AccountType",backOfficeDelegate.getMainModules(2));
       			 commonBackOfficeInitParameters(req,backOfficeDelegate);
       			 req.setAttribute("pageId",MenuNameReader.getScreenProperties(chregprintrep.getPageId()));
       			 return map.findForward(ResultHelp.getSuccess());
       		}
       		else{
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
           		return map.findForward(ResultHelp.getError());
           	    } 
               } catch(Exception e){
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements(""+e,req,path);
           		logger.error(e);
           		e.printStackTrace();
           		return map.findForward(ResultHelp.getError());
            	}
       }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/UnPresentedCheques")){
    	try{
    		 System.out.println("HEEEEEEEEEEEEEELLLLLLLLLLOOOOOOOOO");
    		ChequeNoObject[] array_chequenoobject=null;
    		String modcode=null,string_query=null;
    		UnPresentedChequeForm upcform=(UnPresentedChequeForm) form;
    		upcform.setValid("null");
    		System.out.println("PAge ID%%%%%%%%"+upcform.getPageId());
    		if(MenuNameReader.containsKeyScreen(upcform.getPageId())){
    			
    			backOfficeDelegate=new BackOfficeDelegate();     //added by parul//
          		 req.setAttribute("AccountType",backOfficeDelegate.getMainModules(2));
    			   
    			backOfficeDelegate=new BackOfficeDelegate();
    		    int i=0; 
    			System.out.println("hiiiiiiiiiiiiiiiiiiiiii");
    			array_module_obj=backOfficeDelegate.getMainModules(2);
    			System.out.println("heloooooooooooooooooo");
             if(upcform.getForward().equalsIgnoreCase("view")){	
             	
         String max_ac_no=backOfficeDelegate.Max_AccountNo(upcform.getAcctype());
            
        String ac_no=upcform.getAccno();
          
          System.out.println("maximum account number in action---------"+max_ac_no);
            if(ac_no.length()>max_ac_no.length())
            {
         	   System.out.println("Number is Exceeding Maximum Number");
            }
         	   
            else{
              array_chequenoobject= backOfficeDelegate.getUnpresentedCheque(Integer.parseInt(upcform.getAccno()),upcform.getAcctype(),string_query);
              	
              
              
              
		   	    if(array_chequenoobject!=null){
		   	    	
 				 req.setAttribute("ChequeDetails",array_chequenoobject);
 			}
 			else{
 				System.out.println("R u coming here?????");
 				
 				req.setAttribute("displaymsg","Records Not Found");
 				
 			}	
            }
             }
             if(upcform.getForward().equalsIgnoreCase("download")) {
            	 String ac_no=upcform.getAccno();
            	 String max_ac_no=upcform.getAccno();
             	  if(ac_no.length()>max_ac_no.length())
                   {
                	   System.out.println("Number is Exceeding Maximum Number");
                   }
                	   
                   else{
                     array_chequenoobject= backOfficeDelegate.getUnpresentedCheque(Integer.parseInt(upcform.getAccno()),upcform.getAcctype(),string_query);
                     	
                     
                   }
   				System.out.println("I am in download=======");
   				
   			//	chequeNoObjects = chequeNoObjects= backOfficeDelegate.getUnpresentedCheque(Integer.parseInt(upcform.getAccno()),upcform.getAcctype(),string_query);
   				if (array_chequenoobject== null) {
   					upcform.setTesting("Cannot Print");
   				} else {
   					System.out.println(" i am inside down load");

   					// TO save to an excel Sheet
   					res.setContentType("application/.csv");
   					res.setHeader("Content-disposition","attachment;filename=UnPresentedCheques.csv");

             java.io.PrintWriter out = res.getWriter();
   			out.print("\n");
   			out.print("\n");
   			out.print("\n");
   			out.print(",");
   			out.print(",");
   			out.print(",");
   			out.print("ChequeDetails for A/C Type: "
   					+ array_chequenoobject[0].getAccountType());
   			out.print("\n");
   			out.print("\n");

   			out.print("AccountType");
   			out.print(",");
   			out.print("accountNo");
   			out.print(",");
   			out.print("accountName");
   			out.print(",");
   			out.print("chequeNo");
   			out.print(",");
   			out.print("nextChequeDate");
   			out.print(",");
   			out.print("Remarks");
   			out.print(",");
   			out.print("\n");

   			for (int k = 0; k <array_chequenoobject.length; k++) {
   				
   				out.print(array_chequenoobject[k].getAccountType());
   				out.print(",");
   				out.print(array_chequenoobject[k].getAccountNo());
   				out.print(",");
   				out.print(array_chequenoobject[k].getAccountName());
   				out.print(",");
   				out.print(array_chequenoobject[k].getChequeNo());
   				out.print(",");
   				out.print(array_chequenoobject[k].getNextChequeDate());
   				out.print(",");
   				out.print(array_chequenoobject[k].getModAbbrv());
   				out.print(",");
   				out.print("\n");
   			}

   			req.setAttribute("msg","Saved to excel file in C:");
   			return null;

   		}
   	}

             
             //if(upcform.getAcctype().equalsIgnoreCase("SB"))  		 
             
    		 
		   	   //req.setAttribute("AccountType",backOfficeDelegate.getMainModules(2));
		   	    req.setAttribute("pageId",MenuNameReader.getScreenProperties(upcform.getPageId()));
    			return map.findForward(ResultHelp.getSuccess());
    		}
    		else{
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
        		return map.findForward(ResultHelp.getError());
        	    } 
            } catch(Exception e){
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements(""+e,req,path);
        		logger.error(e);
        		e.printStackTrace();
        		return map.findForward(ResultHelp.getError());
         	}
    }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ChequeRegisterPrintingReportMenu")){
       	try{
       		 
       		
       		String modcode=null,string_query=null;
       		ChequeRegisterPrintingForm chregprintrep=(ChequeRegisterPrintingForm) form;
       		chregprintrep.setValid("null");
       		System.out.println("Get Page ID!!!!!!!!!!"+chregprintrep.getPageId());
       		if(MenuNameReader.containsKeyScreen(chregprintrep.getPageId())){
       			   
       			
       			 commonBackOfficeInitParameters(req,backOfficeDelegate);
       			chregprintrep.setSysdate(BackOfficeDelegate.getSysDate());
       			 req.setAttribute("pageId",MenuNameReader.getScreenProperties(chregprintrep.getPageId()));
       			 return map.findForward(ResultHelp.getSuccess());
       		}
       		else{
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
           		return map.findForward(ResultHelp.getError());
           	    } 
               } catch(Exception e){
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements(""+e,req,path);
           		logger.error(e);
           		e.printStackTrace();
           		return map.findForward(ResultHelp.getError());
            	}
       }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ChequeRegisterPrintingReport")){
       	try{
       		 
       		
       		String modcode=null,string_query=null;
       		int flag=0;
       		
       		
       		ChequeRegisterPrintingForm chregprintrep=(ChequeRegisterPrintingForm) form;
       		chregprintrep.setValid("null");
       		System.out.println("Get Page ID!!!!!!!11111"+chregprintrep.getPageId());
       		if(MenuNameReader.containsKeyScreen(chregprintrep.getPageId())){
       			ChequeBookObject[] chqbookobjj=null; 
       			backOfficeDelegate=new BackOfficeDelegate();
       			chregprintrep.setSysdate(BackOfficeDelegate.getSysDate());
       			commonBackOfficeInitParameters(req,backOfficeDelegate);
       			if(chregprintrep.getForward().equalsIgnoreCase("view"))
       			{
       			chqbookobjj=backOfficeDelegate.getChequeBook(flag, string_query,chregprintrep.getFromdate(),chregprintrep.getTodate());
       			if(chqbookobjj!=null){
       			
       		    req.setAttribute("ChequeBookDetails",chqbookobjj);
       			}
       			else{
       				req.setAttribute("displaymsg","RecordsNotfound");
       				System.out.println("Records Not found");
       			}
       			}
       			if(chregprintrep.getForward().equalsIgnoreCase("save"))
       			{
       				UserVerifier obj_userverifer = new UserVerifier();
       				System.out.println("Inside save ");
       				ChequeBookObject[] chqbookobj=null;
           		 //TO save to an excel Sheet
       				
       				
       				res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("cheque Register Printing Details: ");
           	        out.print("\n");
           	        out.print("\n");
              		   /*HSSFCell cell = row.createCell((short)0);
              		   cell.setCellValue(1);*/
           	       
              		out.print("Date"); out.print(",");
              		out.print("Cheque book No"); out.print(",");
              		out.print("Leaves From"); out.print(",");
              		out.print("Leaves To"); out.print(",");
              		out.print("Account Type"); out.print(",");
              		out.print("Account No"); out.print(",");
              		out.print("Name"); out.print(",");
              		out.print("De User"); out.print(",");
              		out.print("De Tml"); out.print(",");
              		out.print("Ve User"); out.print(",");
              		out.print("Ve Tml"); out.print("\n");
              		
              		
              		System.out.println("flag0----->"+flag);
       				System.out.println("string_query===> "+string_query);
       				System.out.println("chregprintrep===>"+chregprintrep.getFromdate());
       				System.out.println("chregprintrep.getTodate()----->"+chregprintrep.getTodate());
       				
       				
       				chqbookobj=backOfficeDelegate.getChequeBook(flag, string_query,chregprintrep.getFromdate(),chregprintrep.getTodate());
       				//System.out.println("the length of master===>"+chqbookobj.length);
              		if(chqbookobj!=null){
           		 for(int k=0;k<chqbookobj.length;k++){
           			out.print(chqbookobj[k].getRequestedDate());
           			out.print(",");
           			out.print(chqbookobj[k].getChequeBookNo());
           			out.print(",");
           			out.print(chqbookobj[k].getFirstChequeNo());
           			out.print(",");
           			out.print(chqbookobj[k].getLastChequeNo());
           			out.print(",");
           			out.print(chqbookobj[k].getAccountType());
           			out.print(",");
           			out.print(chqbookobj[k].getAccountNo());
           			out.print(",");
           			out.print(chqbookobj[k].getAccountName());
           			out.print(",");
           			out.print(chqbookobj[k].getObj_userverifer().getUserId());
           			out.print(",");
           			out.print(chqbookobj[k].getObj_userverifer().getUserTml());
           			out.print(",");
           			out.print(chqbookobj[k].getObj_userverifer().getVerId());
           			out.print(",");
           			out.print(chqbookobj[k].getObj_userverifer().getVerTml());
           			out.print(",");
           			
           			
           			out.print("\n");  
           		 }
           			}	
           		 req.setAttribute("msg","Saved to excel file in C:");
     		    return null;
     		 
       			}
       			
       			req.setAttribute("pageId",MenuNameReader.getScreenProperties(chregprintrep.getPageId()));
       			return map.findForward(ResultHelp.getSuccess());
       		}
       		else{
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
           		return map.findForward(ResultHelp.getError());
           	    } 
               } catch(Exception e){
           		path=MenuNameReader.getScreenProperties("0000");
           		setErrorPageElements(""+e,req,path);
           		logger.error(e);
           		e.printStackTrace();
           		return map.findForward(ResultHelp.getError());
            	}
       }
     
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/OpenedClosedAccountsPrintingReportMenu")){
      	try{
      	        		
      		String modcode=null,string_query=null;
      		int flag=0;
      		OpenedClosedAccountsPrintingReportForm opencloserep=(OpenedClosedAccountsPrintingReportForm) form;
      		System.out.println("Get Page ID=======1111111"+opencloserep.getPageId());
      		if(MenuNameReader.containsKeyScreen(opencloserep.getPageId())){
      			   
      			backOfficeDelegate=new BackOfficeDelegate();
      			commonBackOfficeInitParameters(req,backOfficeDelegate);
      			opencloserep.setSysdate(BackOfficeDelegate.getSysDate());


      			
      			req.setAttribute("AccStatus",backOfficeDelegate.getAccountStatus());
      			req.setAttribute("AccTypes",backOfficeDelegate.getAccountTypesForOpenCloseReport(1));
      			      			
      			
      			req.setAttribute("pageId",MenuNameReader.getScreenProperties(opencloserep.getPageId()));
      			return map.findForward(ResultHelp.getSuccess());
      		}
      		else{
          		path=MenuNameReader.getScreenProperties("0000");
          		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
          		return map.findForward(ResultHelp.getError());
          	    } 
              } catch(Exception e){
          		path=MenuNameReader.getScreenProperties("0000");
          		setErrorPageElements(""+e,req,path);
          		logger.error(e);
          		e.printStackTrace();
          		return map.findForward(ResultHelp.getError());
           	}
      }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/OpenedClosedAccountsPrintingReport")){
      	try{
      		 
      		
      		String modcode=null,string_query=null;
      		ModuleObject[] array_moduleobject=backOfficeDelegate.getAccountTypesForOpenCloseReport(1);
      		int flag=0;
      		OpenedClosedInoperatedAccountObject[] opencloseinoperatedaccounts=null;
      		OpenedClosedAccountsPrintingReportForm opencloserep=(OpenedClosedAccountsPrintingReportForm) form;
      		System.out.println("Get Page ID========12333333"+opencloserep.getPageId());
      		if(MenuNameReader.containsKeyScreen(opencloserep.getPageId())){
      			  
      			backOfficeDelegate=new BackOfficeDelegate();
      			commonBackOfficeInitParameters(req,backOfficeDelegate);
      			opencloserep.setSysdate(BackOfficeDelegate.getSysDate());
      			req.setAttribute("AccStatus",backOfficeDelegate.getAccountStatus());
      			System.out.println("opencloserep.getAcc_status()==========>"+opencloserep.getAcc_status());
      			if(opencloserep.getAcc_status().equalsIgnoreCase("Opened")||opencloserep.getAcc_status().equalsIgnoreCase("Closed"))
      				req.setAttribute("AccTypes",backOfficeDelegate.getAccountTypesForOpenCloseReport(1));
      			else if(opencloserep.getAcc_status().equalsIgnoreCase("InOperated"))
      				req.setAttribute("AccTypes",backOfficeDelegate.getAccountTypesForOpenCloseReport(2));
      			else if(opencloserep.getAcc_status().equalsIgnoreCase("Freezed"))	
      				req.setAttribute("AccTypes",backOfficeDelegate.getAccountTypesForOpenCloseReport(3));
      			
      			         			
      			
      			System.out.println("get acc_type@@@@@@@@@@@"+modcode);
      			System.out.println("get fromdat============="+opencloserep.getFromdate());
      			System.out.println("get todate^^^^^^^^^^^^^^"+opencloserep.getTodate());
      			System.out.println("get accstatus-----------"+opencloserep.getAcc_status());
      			if(opencloserep.getForward().equalsIgnoreCase("view")){
      				try{
      					System.out.println("Are yu in trrrrrrrrrrrry");
      				 opencloseinoperatedaccounts=backOfficeDelegate.getOpenedClosedInoperatedAccounts(flag,opencloserep.getAcc_type(),opencloserep.getFromdate(),opencloserep.getTodate(),opencloserep.getAcc_status(),string_query);
      				}
      				catch(Exception e)
      				{
      					e.printStackTrace();
      				}
                      if(opencloseinoperatedaccounts!=null){
                     	 opencloserep.setRecords("null");
                     		 
                     	 req.setAttribute("OpenedClosedAccounts",opencloseinoperatedaccounts);
      			}
                      else{
                     	req.setAttribute("displaymsg", "Records Not Found");
                     	 System.out.println("Records Not Found");
                      }
      			}
      			
      			if(opencloserep.getForward().equalsIgnoreCase("save")){
      				OpenedClosedInoperatedAccountObject[] opencloseinoperatedaccounts1=null;
      				System.out.println("Inside save ");
           		 //TO save to an excel Sheet
           		 res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("Open/Close Account Details: ");
           	        out.print("\n");
           	        out.print("\n");
              		   /*HSSFCell cell = row.createCell((short)0);
              		   cell.setCellValue(1);*/
           	       
              		out.print("Account Type"); out.print(",");
              		out.print("Account No"); out.print(",");
              		out.print("Name"); out.print(",");
              		out.print("Account Category"); out.print(",");
              		out.print("Opening Date"); out.print(",");
              		out.print("Closing Date"); out.print("\n");
              		
              		 opencloseinoperatedaccounts1=backOfficeDelegate.getOpenedClosedInoperatedAccounts(flag,opencloserep.getAcc_type(),opencloserep.getFromdate(),opencloserep.getTodate(),opencloserep.getAcc_status(),string_query);
              		 for(int k=0;k<opencloseinoperatedaccounts1.length;k++){
              			  
                			out.print(opencloseinoperatedaccounts1[k].getAccountType());
                			out.print(",");
                			out.print(opencloseinoperatedaccounts1[k].getAccountNo());
                			out.print(",");
                			out.print(opencloseinoperatedaccounts1[k].getAccountName());
                			out.print(",");
                			out.print(opencloseinoperatedaccounts1[k].getAccountCategory());
                			out.print(",");
                			out.print(opencloseinoperatedaccounts1[k].getOpenDate());
                			out.print(",");
                			out.print(opencloseinoperatedaccounts1[k].getCloseDate());
                			out.print(",");
                			
                			out.print("\n"); 
      			}
              		 
              		 req.setAttribute("msg","Saved to excel file in C:");
         		    return null;
      			}
      			req.setAttribute("pageId",MenuNameReader.getScreenProperties(opencloserep.getPageId()));
      			
      			return map.findForward(ResultHelp.getSuccess());
      		}
      		else{
          		path=MenuNameReader.getScreenProperties("0000");
          		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
          		return map.findForward(ResultHelp.getError());
          	    } 
              } catch(Exception e){
          		path=MenuNameReader.getScreenProperties("0000");
          		setErrorPageElements(""+e,req,path);
          		logger.error(e);
          		e.printStackTrace();
          		return map.findForward(ResultHelp.getError());
           	}
      }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIQueryMenu")){
     	try{
     		
     		 
     		
     		String modcode=null,string_query=null;
     		int flag=0;
     		
     		SIQueryForm siquery=(SIQueryForm) form;
     		System.out.println("Get Page ID========12333333"+siquery.getPageId());
     		if(MenuNameReader.containsKeyScreen(siquery.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			ModuleObject array_moduleobject_mod[]=backOfficeDelegate.getMainModules(6);
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			req.setAttribute("Acctypes",backOfficeDelegate.getMainModules(6));
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siquery.getPageId()));
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIQuery")){
     	try{
     		 
     		String modcode=null,string_query=null,loanoption=null;
     		int flag=0,loan_option=0;
     	
     		SIQueryForm siquery=(SIQueryForm) form;
     		//SIEntryObject[] array_sientryobject_receive=backOfficeDelegate.getInfo(siquery.getAcctype(),Integer.parseInt(siquery.getAccno()),string_query);
     		System.out.println("Get Page ID========12333333"+siquery.getPageId());
     		if(MenuNameReader.containsKeyScreen(siquery.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			
     			
     		       			
     			if(siquery.getAccno().trim().length()!=0){
     				System.out.println("Are u getting object");
     		        System.out.println(siquery.getAcctype());
     		        System.out.println(Integer.parseInt(siquery.getAccno()));
     			AccountObject fromaccno=backOfficeDelegate.getAccount(siquery.getAcctype(),Integer.parseInt(siquery.getAccno()));
     			System.out.println("Object value========="+fromaccno);
     		   if(fromaccno!=null){
     			   System.out.println("********");
     			   siquery.setValid("");
     			   req.setAttribute("frmaccname",fromaccno);
     		   }
     		   else{
     			   System.out.println("--->In else");
     			   req.setAttribute("displaymsg","Acnt Doesn't exist");
     		   }
     			} 
     			
     		       			
     			req.setAttribute("Acctypes",backOfficeDelegate.getMainModules(6));
     			if(siquery.getForward().equalsIgnoreCase("view")){
     			SIEntryObject[] siEntryObj=backOfficeDelegate.getInfo(siquery.getAcctype(),Integer.parseInt(siquery.getAccno()),string_query);	
     			
     			req.setAttribute("QueryDetails",siEntryObj);
     			
      		   }
     			
     			if(siquery.getForward().equalsIgnoreCase("save")){
     				 System.out.println("Inside save ");
            		 //TO save to an excel Sheet
            		 res.setContentType("application/.csv");
            	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
            	        
            	        java.io.PrintWriter out = res.getWriter();
            	        out.print("\n");
            	        out.print("\n");
            	        out.print("\n");
            	        out.print(",");out.print(",");out.print(",");
            	        out.print("SIQuery Details: ");
            	        out.print("\n");
            	        out.print("\n");
               		   /*HSSFCell cell = row.createCell((short)0);
               		   cell.setCellValue(1);*/
            	        out.print("InstNo"); out.print(",");
                   		out.print("Due Date"); out.print(",");
                   		out.print("priority No"); out.print(",");
                   		out.print("Amount"); out.print(",");
                   		out.print("Trf to Name"); out.print(",");
                   		out.print("Receipt Type"); out.print(",");
                   		out.print("No of times"); out.print(",");
                   		out.print("Last Date"); out.print(",");
                   		out.print("Deleted on"); out.print(",");
                   		out.print("To AccType"); out.print(",");
                   		out.print("To AccNo"); out.print("\n");
                   		SIEntryObject[] siEntryObj=backOfficeDelegate.getInfo(siquery.getAcctype(),Integer.parseInt(siquery.getAccno()),string_query);
                   		for(int k=0;k<siEntryObj.length;k++){
              			  
                			out.print(siEntryObj[k].getInstNo());
                			out.print(",");
                			out.print(siEntryObj[k].getDueDt());
                			out.print(",");
                			out.print(siEntryObj[k].getPriorityNo());
                			out.print(",");
                			out.print(siEntryObj[k].getAmount());
                			out.print(",");
                			out.print(siEntryObj[k].getToaccholdername());
                			out.print(",");
                			out.print(siEntryObj[k].getStrloanopt());
                			out.print(",");
                			out.print(siEntryObj[k].getNoExec());
                			out.print(",");
                			out.print(siEntryObj[k].getLastExec());
                			out.print(",");
                			out.print(siEntryObj[k].getDelDate());
                			out.print(",");
                			out.print(siEntryObj[k].getTomodAbbrv());
                			out.print(",");
                			out.print(siEntryObj[k].getToAccNo());
                			out.print(",");
                			out.print("\n");
                			
                			
                   	 }
                   		req.setAttribute("msg","Saved to excel file in C:");
            		    return null;	
     			}
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siquery.getPageId()));
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIRegisterMenu")){
    	try{
    		 
    		
    		String modcode=null,string_query=null,loanoption=null;
    		int flag=0,loan_option=0;
    		
    		SIRegisterForm sireg=(SIRegisterForm) form; 
    		
    		System.out.println("Get Page ID========12333333"+sireg.getPageId());
    		if(MenuNameReader.containsKeyScreen(sireg.getPageId())){
    			  
    			backOfficeDelegate=new BackOfficeDelegate();
    			commonBackOfficeInitParameters(req,backOfficeDelegate);
    			sireg.setSysdate(BackOfficeDelegate.getSysDate());
    			      			
    			
    			req.setAttribute("InstructionType",backOfficeDelegate.getInstructionTypes());   			
    			req.setAttribute("pageId",MenuNameReader.getScreenProperties(sireg.getPageId()));
    			return map.findForward(ResultHelp.getSuccess());
    		}
    		else{
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
        		return map.findForward(ResultHelp.getError());
        	    } 
            } catch(Exception e){
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements(""+e,req,path);
        		logger.error(e);
        		e.printStackTrace();
        		return map.findForward(ResultHelp.getError());
         	}
    }
    else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIRegister")){
    	try{
    		 
    		
    		String modcode=null,string_query=null,loanoption=null;
    		int flag=0,loan_option=0;
    		
    		SIRegisterForm sireg=(SIRegisterForm) form;
    		//sireg.setRecord("null");
    		SIEntryObject[] regdetailsforactive=null,regdetailsfordeleted=null;
    		System.out.println("Get Page ID========12333333"+sireg.getPageId());
    		if(MenuNameReader.containsKeyScreen(sireg.getPageId())){
    			  
    			backOfficeDelegate=new BackOfficeDelegate();
    			commonBackOfficeInitParameters(req,backOfficeDelegate);
    			sireg.setSysdate(BackOfficeDelegate.getSysDate());
    			   			
    			
    			if(sireg.getForward().equalsIgnoreCase("view")){
    			if(sireg.getInst_type().equalsIgnoreCase("ACTIVE"))	{
    				regdetailsforactive=backOfficeDelegate.getInstInfo(1,sireg.getFromdate(),sireg.getTodate() , string_query);
    				System.out.println("Active SI--------->"+regdetailsforactive);
    				if(regdetailsforactive!=null){
    				 sireg.setRecord("");
    			 	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
    				req.setAttribute("RegDetails",regdetailsforactive);
    				}
    				else{
    					req.setAttribute("displaymsg","Records Not Found");
    					System.out.println("Records Not Found");
    				}
    					
    			}
    			else if(sireg.getInst_type().equalsIgnoreCase("DELETED")){
    				regdetailsfordeleted=backOfficeDelegate.getInstInfo(0,sireg.getFromdate(),sireg.getTodate() , string_query);
    				System.out.println("Deleted SI--------->"+regdetailsfordeleted);
    				if(regdetailsfordeleted!=null){
    					req.setAttribute("displaymsg","Records Not Found");
    					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");   				
    			    req.setAttribute("RegDetails",regdetailsfordeleted);
    				}
    				else{
    					req.setAttribute("displaymsg","Records Not Found");
    					System.out.println("Records Not Found");	
    				}
    				
    			}
    			}
    			
    			if(sireg.getForward().equalsIgnoreCase("save")){
    				 System.out.println("Inside save ");
            		 //TO save to an excel Sheet
            		 res.setContentType("application/.csv");
            	      res.setHeader("Content-disposition", "attachment;filename=output.csv");
            	        
            	        java.io.PrintWriter out = res.getWriter();
            	        out.print("\n");
            	        out.print("\n");
            	        out.print("\n");
            	        out.print(",");out.print(",");out.print(",");
            	        out.print("Standard Instruction Register Details: ");
            	        out.print("\n");
            	        out.print("\n");
               		   /*HSSFCell cell = row.createCell((short)0);
               		   cell.setCellValue(1);*/
            	        
            	        out.print("InstNo"); out.print(",");
                   		out.print("Frm A/C"); out.print(",");
                   		out.print("Frm A/C No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("To A/C"); out.print(",");
                   		out.print("To A/C No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("Due Date"); out.print(",");
                   		out.print("Period_months"); out.print(",");
                   		out.print("Days"); out.print(",");
                   		out.print("Amount"); out.print(",");
                   		out.print("priority No"); out.print(",");
                   		out.print("Loan Option"); out.print(",");
                   		out.print("No of times"); out.print(",");
                   		out.print("Execd Times"); out.print(",");
                   		out.print("Last Date"); out.print("\n");
                   		
                   		
            	        if(sireg.getInst_type().equalsIgnoreCase("ACTIVE"))	{
            				regdetailsforactive=backOfficeDelegate.getInstInfo(1,sireg.getFromdate(),sireg.getTodate() , string_query);
            				System.out.println("Active SI--------->"+regdetailsforactive);
            				if(regdetailsforactive!=null){
            				 sireg.setRecord("");
            			 	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
            				req.setAttribute("RegDetails",regdetailsforactive);
            				}
            				 for(int k=0;k<regdetailsforactive.length;k++){
                      			  
                        			out.print(regdetailsforactive[k].getInstNo());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getFrommodAbbrv());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getFromAccNo());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getFromaccholdername());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getTomodAbbrv());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getToAccNo());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getToaccholdername());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getDueDt());
                        			out.print(",");
                        			
                        			out.print(regdetailsforactive[k].getPeriodMonths());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getPeriodDays());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getAmount());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getPriorityNo());
                        			out.print(",");
                        			
                        			out.print(regdetailsforactive[k].getStrloanopt());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getNoExec());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getExecTime());
                        			out.print(",");
                        			out.print(regdetailsforactive[k].getLastExec());
                        			out.print(",");
                        			out.print("\n");
            				 }
            				
            					
            			}
            			else if(sireg.getInst_type().equalsIgnoreCase("DELETED")){
            				regdetailsfordeleted=backOfficeDelegate.getInstInfo(0,sireg.getFromdate(),sireg.getTodate() , string_query);
            				System.out.println("Deleted SI--------->"+regdetailsfordeleted);
            				if(regdetailsfordeleted!=null){
            					req.setAttribute("displaymsg","Records Not Found");
            					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");   				
            			    req.setAttribute("RegDetails",regdetailsfordeleted);
            				}
            				for(int k=0;k<regdetailsfordeleted.length;k++){
                    			  
                    			out.print(regdetailsfordeleted[k].getInstNo());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getFrommodAbbrv());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getFromAccNo());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getFromaccholdername());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getTomodAbbrv());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getToAccNo());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getToaccholdername());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getDueDt());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getPeriodMonths());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getPeriodDays());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getAmount());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getPriorityNo());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getStrloanopt());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getNoExec());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getExecTime());
                    			out.print(",");
                    			out.print(regdetailsfordeleted[k].getLastExec());
                    			out.print(",");
                    			out.print("\n");
        				 }
        				
            				
            			}
            	        req.setAttribute("msg","Saved to excel file in C:");
            		    return null;
    			}
    			
    			
    			req.setAttribute("InstructionType",backOfficeDelegate.getInstructionTypes());
    			req.setAttribute("pageId",MenuNameReader.getScreenProperties(sireg.getPageId()));
    			return map.findForward(ResultHelp.getSuccess());
    		}
    		else{
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
        		return map.findForward(ResultHelp.getError());
        	    } 
            } catch(Exception e){
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements(""+e,req,path);
        		logger.error(e);
        		e.printStackTrace();
        		return map.findForward(ResultHelp.getError());
         	}
    }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIDueDoneMenu")){
    	try{
    		 
    		String modcode=null,string_query=null;
    		int flag=0;
    		
    		SIDueDoneForm siduedone=(SIDueDoneForm) form;
    		System.out.println("Get Page ID========12333333"+siduedone.getPageId());
    		if(MenuNameReader.containsKeyScreen(siduedone.getPageId())){
    			  
    			backOfficeDelegate=new BackOfficeDelegate();
    			commonBackOfficeInitParameters(req,backOfficeDelegate);
    			siduedone.setSysdate( BackOfficeDelegate.getSysDate());
    			req.setAttribute("InstType",backOfficeDelegate.getInstructionTypesForDueDoneReport());
    			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siduedone.getPageId()));
    			return map.findForward(ResultHelp.getSuccess());
    		}
    		else{
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
        		return map.findForward(ResultHelp.getError());
        	    } 
            } catch(Exception e){
        		path=MenuNameReader.getScreenProperties("0000");
        		setErrorPageElements(""+e,req,path);
        		logger.error(e);
        		e.printStackTrace();
        		return map.findForward(ResultHelp.getError());
         	}
    }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIDueDone")){
   	try{
   		 
   		
   		String modcode=null,string_query=null;
   		String item1="To Be executed",item2="Already executed";
   		
   		SIDueDoneForm siduedone=(SIDueDoneForm) form;
   		System.out.println("Get Page ID========12333333"+siduedone.getPageId());
   		if(MenuNameReader.containsKeyScreen(siduedone.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			siduedone.setSysdate( BackOfficeDelegate.getSysDate());
   			SIEntryObject[] sientrydetailsfordue=null;
   			SIDoneObject[] sidoneobj=null;
   			if(siduedone.getForward().equalsIgnoreCase("view")){
   			if(siduedone.getInst_type().equalsIgnoreCase("To Be executed")){
   				req.setAttribute("flag",item1);
   				sientrydetailsfordue=backOfficeDelegate.getInstInfoForDue(siduedone.getFromdate(),siduedone.getTodate(), string_query);
   				if(sientrydetailsfordue!=null){
   					
   					
   				req.setAttribute("DueReport",sientrydetailsfordue);
   				}
   				else{
   					req.setAttribute("displaymsg","Records Not Found");
   					System.out.println("Records Not Found");
   				}
   			}
   			else if(siduedone.getInst_type().equalsIgnoreCase("Already executed")){
   				req.setAttribute("flag",item2);
   				sidoneobj=backOfficeDelegate.getInstInfoForDone(siduedone.getFromdate(), siduedone.getTodate(), string_query);
   				System.out.println("Object Due done ----------"+sidoneobj);
   				if(sidoneobj!=null){
   				   req.setAttribute("DoneReport",sidoneobj);
   				}
   				else{
   					req.setAttribute("displaymsg","Records Not Found");
   					System.out.println("Records Not Found");
   				}
   			}
   			}
   			
   			if(siduedone.getForward().equalsIgnoreCase("save")){
   			 System.out.println("Inside save ");
    		 //TO save to an excel Sheet
    		 res.setContentType("application/.csv");
    	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
    	        
    	        java.io.PrintWriter out = res.getWriter();
    	        out.print("\n");
    	        out.print("\n");
    	        out.print("\n");
    	        out.print(",");out.print(",");out.print(",");
    	        out.print("Standard Instruction Due/Done Report Details: ");
    	        out.print("\n");
    	        out.print("\n");
   				
   				if(siduedone.getInst_type().equalsIgnoreCase("To Be executed")){
   					

               		out.print("Frm A/C"); out.print(",");
               		out.print("Frm A/C No"); out.print(",");
               		out.print("Name"); out.print(",");
               		out.print("InstNo"); out.print(",");
               		out.print("priority No"); out.print(",");
               		out.print("Amount"); out.print(",");
               		out.print("Loan Option"); out.print(",");
               		out.print("To A/C"); out.print(",");
               		out.print("To A/C No "); out.print(",");
               		out.print("Name"); out.print("\n");
   	   				req.setAttribute("flag",item1);
   	   				sientrydetailsfordue=backOfficeDelegate.getInstInfoForDue(siduedone.getFromdate(),siduedone.getTodate(), string_query);
   	   				if(sientrydetailsfordue!=null){
   	   				req.setAttribute("DueReport",sientrydetailsfordue);
   	   				}
   	   			 for(int k=0;k<sientrydetailsfordue.length;k++){
          			  
            			out.print(sientrydetailsfordue[k].getFrommodAbbrv());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getFromAccNo());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getFromaccholdername());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getInstNo());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getPriorityNo());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getAmount());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getStrloanopt());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getTomodAbbrv());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getToAccNo());
            			out.print(",");
            			out.print(sientrydetailsfordue[k].getToaccholdername());
            			out.print(",");
            			out.print("\n"); 
   	   			 }
   	   				
   	   			}
   	   			else if(siduedone.getInst_type().equalsIgnoreCase("Already executed")){
   	   			out.print("InstNo"); out.print(",");
   	   			out.print("Frm A/C"); out.print(",");
           		out.print("Frm A/C No"); out.print(",");
           		out.print("Name"); out.print(",");
           		out.print("To A/C"); out.print(",");
           		out.print("To A/C No"); out.print(",");
           		out.print("date "); out.print(",");
           		out.print("Due Date"); out.print(",");
           		out.print("Amount"); out.print(",");
           		out.print("Prin"); out.print(",");
           		out.print("Int"); out.print(",");
           		out.print("Penal"); out.print(",");
           		out.print("Other"); out.print(",");
           		out.print("Up to Dat"); out.print("\n");
           		
           		
   	   				req.setAttribute("flag",item2);
   	   				sidoneobj=backOfficeDelegate.getInstInfoForDone(siduedone.getFromdate(), siduedone.getTodate(), string_query);
   	   				System.out.println("Object Due done ----------"+sidoneobj);
   	   				if(sidoneobj!=null){
   	   				   req.setAttribute("DoneReport",sidoneobj);
   	   				}
   	   			 for(int k=0;k<sidoneobj.length;k++){
          			  
            			out.print(sidoneobj[k].getSiNo());
            			out.print(",");
            			out.print(sidoneobj[k].getFrommodAbbrv());
            			out.print(",");
            			out.print(sidoneobj[k].getFromAccNo());
            			out.print(",");
            			out.print(sidoneobj[k].getFromaccholdername());
            			out.print(",");
            			out.print(sidoneobj[k].getTomodAbbrv());
            			out.print(",");
            			out.print(sidoneobj[k].getToAccNo());
            			out.print(",");
            			out.print(sidoneobj[k].getToaccholdername());
            			out.print(",");
            			out.print(sidoneobj[k].getDueDt());
            			out.print(",");
            			out.print(sidoneobj[k].getTrnAmt());
            			out.print(",");
            			out.print(sidoneobj[k].getPrinAmt());
            			out.print(",");
            			out.print(sidoneobj[k].getIntAmt());
            			out.print(",");
            			out.print(sidoneobj[k].getPenalAmt());
            			out.print(",");
            			out.print(sidoneobj[k].getOtherAmt());
            			out.print(",");
            			out.print(sidoneobj[k].getIntDt());
            			out.print(",");
            			out.print("\n");   
            			
   	   			 }
   	   			}
   				req.setAttribute("msg","Saved to excel file in C:");
    		    return null;
   			}
   			req.setAttribute("InstType",backOfficeDelegate.getInstructionTypesForDueDoneReport());
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siduedone.getPageId()));
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/VoucherScheduleMenu")){
     	try{
     		 
     		String string_vchty=null,string_query=null;
     		
     		VoucherScheduleForm vchform=(VoucherScheduleForm) form;
     		System.out.println("Get Page ID========12333333"+vchform.getPageId());
     		if(MenuNameReader.containsKeyScreen(vchform.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			vchform.setSysdate(BackOfficeDelegate.getSysDate());
     			//req.setAttribute("Receipt",backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query));
     			req.setAttribute("VchTypes",backOfficeDelegate.getVoucherTypes());
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(vchform.getPageId()));
     			
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/VoucherSchedule")){
     	try{
     		 
     		
     		String string_vchty=null,string_query=null;
     		String item1="Sch of Misc Rec",item2="Sch of Pay Vch",item3="Sch of Trf Vch",item4="Sch of Csh Pay Vch";
     		
     		VoucherScheduleForm vchform=(VoucherScheduleForm) form;
     		System.out.println("Get Page ID========12333333"+vchform.getPageId());
     		if(MenuNameReader.containsKeyScreen(vchform.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			vchform.setSysdate(BackOfficeDelegate.getSysDate());
     			masterObject.backOffice.VoucherDataObject[] voucherdataobj=null;
     			if(vchform.getForward().equalsIgnoreCase("view")){
     			if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Misc Rec")){
     				string_vchty="R";
     				System.out.println("inside action class in Misc rec");
     				System.out.println("from date*****************"+vchform.getFromdate());
     				System.out.println("to date*****************"+vchform.getTodate());
     				System.out.println("vch_type********************"+string_vchty);
     				
     				       				
     				//req.setAttribute("Receipt",backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query));
     				System.out.println("item1********************"+item1);
     				req.setAttribute("flag",item1);
     				
     			}
     			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Pay Vch")){
     				string_vchty="P";
     				System.out.println("item2********************"+item2);
     				req.setAttribute("flag",item2);
     				
     			}
     			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Trf Vch")){
     				string_vchty="T";
     				req.setAttribute("flag",item3);
     				
     			}
     			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Csh Pay Vch")){
     				string_vchty="C";
     				req.setAttribute("flag",item4);
     				
     			}
     			}
     			
     			if(vchform.getForward().equalsIgnoreCase("save")){
     				 System.out.println("Inside save ");
            		 //TO save to an excel Sheet
            		 res.setContentType("application/.csv");
            	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
            	        
            	        java.io.PrintWriter out = res.getWriter();
            	        out.print("\n");
            	        out.print("\n");
            	        out.print("\n");
            	        out.print(",");out.print(",");out.print(",");
            	        out.print("Voucher Shedule Details: ");
            	        out.print("\n");
            	        out.print("\n");
               		   /*HSSFCell cell = row.createCell((short)0);
               		   cell.setCellValue(1);*/
               		
               		if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Misc Rec")){
             	       
               			out.print("Trans Date"); out.print(",");
                   		out.print("Voucher No"); out.print(",");
                   		out.print("Debit Code"); out.print(",");
                   		out.print("Credit Code"); out.print(",");
                   		out.print("Credit Name"); out.print(",");
                   		out.print("Amount"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
         				string_vchty="R";
         				System.out.println("inside action class in Misc rec");
         				System.out.println("from date*****************"+vchform.getFromdate());
         				System.out.println("to date*****************"+vchform.getTodate());
         				System.out.println("vch_type********************"+string_vchty);
         				
         				       				
         				//req.setAttribute("Receipt",backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query));
         				System.out.println("item1********************"+item1);
         				req.setAttribute("flag",item1);
         				voucherdataobj=backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query);
             			if(voucherdataobj!=null){
             		    vchform.setValid("");
             			
             			req.setAttribute("VoucherData",voucherdataobj);
             			}
             			 for(int k=0;k<voucherdataobj.length;k++){
                  			  
                    			out.print(voucherdataobj[k].getTransactionDate());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getVoucherNo());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getGlCode());
                    			out.print(",");
                    			out.print("201001");
                    			out.print(",");
                    			out.print("Cash on hand");
                    			out.print(",");
                    			out.print(voucherdataobj[k].getTransactionAmount());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getNarration());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getObj_userverifier().getUserId());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getObj_userverifier().getVerId());
                    			out.print(",");
                    			out.print("\n");   
             			 }
         				
         			}
         			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Pay Vch")){
         				out.print("Trans Date"); out.print(",");
                   		out.print("Voucher No"); out.print(",");
                   		out.print("Debit Code"); out.print(",");
                   		out.print("Debit Name"); out.print(",");
                   		out.print("Amount"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
         				string_vchty="P";
         				System.out.println("item2********************"+item2);
         				req.setAttribute("flag",item2);
         				voucherdataobj=backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query);
             			
             			 for(int k=0;k<voucherdataobj.length;k++){
                  			  
                    			out.print(voucherdataobj[k].getTransactionDate());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getVoucherNo());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getGlCode());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getGlname());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getTransactionAmount());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getNarration());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getObj_userverifier().getUserId());
                    			out.print(",");
                    			out.print(voucherdataobj[k].getObj_userverifier().getVerId());
                    			out.print(",");
                    			out.print("\n");   
             			 }
         				
         			}
         			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Trf Vch")){
         				out.print("Trans Date"); out.print(",");
                   		out.print("Voucher No"); out.print(",");
                   		out.print("GL Type"); out.print(",");
                   		out.print("Account No"); out.print(",");
                   		out.print("GL Code"); out.print(",");
                   		out.print("GL Name"); out.print(",");
                   		out.print("Debit Amt"); out.print(",");
                   		out.print("Credit Amt"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
         				string_vchty="T";
         				req.setAttribute("flag",item3);
         				voucherdataobj=backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query);
             			
            			 for(int k=0;k<voucherdataobj.length;k++){
                 			  
                   			out.print(voucherdataobj[k].getTransactionDate());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getVoucherNo());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getModAbbr());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getModuleAccountNo());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getGlCode());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getGlname());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getNarration());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n");   
            			 }
        				
         				
         			}
         			else if(vchform.getVoucher_type().equalsIgnoreCase("Sch of Csh Pay Vch")){
         				out.print("Trans Date"); out.print(",");
                   		out.print("Voucher No"); out.print(",");
                   		out.print("A/c Type"); out.print(",");
                   		out.print("A/c No"); out.print(",");
                   		out.print("Amount"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
         				string_vchty="C";
         				req.setAttribute("flag",item4);
         				voucherdataobj=backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query);
             			
            			 for(int k=0;k<voucherdataobj.length;k++){
                 			  
                   			out.print(voucherdataobj[k].getTransactionDate());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getVoucherNo());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getModAbbr());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getNarration());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(voucherdataobj[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n");   
            			 }
        				
         				
         			}
               	req.setAttribute("msg","Saved to excel file in C:");
     		    return null;
     			}
     			req.setAttribute("VchTypes",backOfficeDelegate.getVoucherTypes());
     			voucherdataobj=backOfficeDelegate.getFaData(vchform.getFromdate(),vchform.getTodate(),string_vchty, string_query);
     			if(voucherdataobj!=null){
     		    vchform.setValid("");
     			
     			req.setAttribute("VoucherData",voucherdataobj);
     			}
     			else{
     				req.setAttribute("displaymsg","RecordsNotFound");
     				System.out.println("Records Not Found");
     			}
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(vchform.getPageId()));
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/TransactionSummaryMenu")){
 	  
 	  try{
     		 
     	
     		String modcode=null,string_query=null;
     		TransactionSummaryForm tranSummaryForm=(TransactionSummaryForm) form;
             tranSummaryForm.setValid("null");
     		System.out.println("Get Page ID========menu"+tranSummaryForm.getPageId());
     		if(MenuNameReader.containsKeyScreen(tranSummaryForm.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			tranSummaryForm.setSysdate(BackOfficeDelegate.getSysDate());
     			//req.setAttribute("AccTypes",backOfficeDelegate.getMainModules(3));
     			req.setAttribute("shcat",backOfficeDelegate.getShareCategory());
     			req.setAttribute("AccTypes",backOfficeDelegate.getMainModules(3));
     			req.setAttribute("Select",backOfficeDelegate.getSelect());
     			
     			
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(tranSummaryForm.getPageId()));
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/TransactionSummary")){
 	  TransactionSummaryForm tranSummaryForm=(TransactionSummaryForm) form;
 	  try{
     	        		
     		String modcode=null,string_query=null;
     		int sharecat=0;
     		System.out.println("Top of the Summary");
     		ShareObject[] shobj=null;
     		DepositTransactionObject[] depositTransactionObject=null;
     		masterObject.loansOnDeposit.LoanTransactionObject[] lnobj=null;
     		masterObject.loans.LoanTransactionObject[] array_jointloanobject=null;
     		PygmyObject[] array_pygmyobject=null;
     		AccountTransObject[] acctranobj=null;
     		
     		tranSummaryForm.setValid("null");
     		System.out.println("---------"+tranSummaryForm.getForward());
     		System.out.println("Get Page ID========12333333"+tranSummaryForm.getPageId()+"^^^^^^^^^^"+tranSummaryForm.getForward()+"aaaaaaaaa"+tranSummaryForm.getSelect());
     		if(MenuNameReader.containsKeyScreen(tranSummaryForm.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			tranSummaryForm.setSysdate(BackOfficeDelegate.getSysDate());
     			/*//enable sharecategory combo
     			if(tranSummaryForm.getAccType()!=null){
     			if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
     				 req.setAttribute("Disable",false);
     			}
     			}*/
     			//System.out.println("sharecategory--------"+tranSummaryForm.getCategory());
     			/*if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
     				req.setAttribute("Disable",false);*/
     			
     			if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){	
     				System.out.println("sharecategory--------"+tranSummaryForm.getCategory());
     			if(tranSummaryForm.getCategory().equalsIgnoreCase("Regular")){
     				sharecat=1;
     			}
     			else if(tranSummaryForm.getCategory().equalsIgnoreCase("Associate")){
     				sharecat=2;
     			}
     			else if(tranSummaryForm.getCategory().equalsIgnoreCase("Nominal")){
     				sharecat=3;
     	 		}
     			else if(tranSummaryForm.getCategory().equalsIgnoreCase("Socity")){
     				sharecat=4;
     			}
     			}
     					
     			System.out.println("FORWARD------------"+tranSummaryForm.getForward());
     			if(tranSummaryForm.getForward().equalsIgnoreCase("view")){
     			if(tranSummaryForm.getSelect().equalsIgnoreCase("All")){	
     				System.out.println("form date"+tranSummaryForm.getFromdate());
     				System.out.println("to date"+tranSummaryForm.getTodate());
     				System.out.println("AccountType"+tranSummaryForm.getAccType());
     				System.out.println("category"+tranSummaryForm.getCategory());
     			shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat, 0, string_query);
     				if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
     					if(shobj!=null){
     						
     					
     					req.setAttribute("Share",shobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
     						System.out.println("Records Not Found");
     					}
     				
     				}
     				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
     					 depositTransactionObject= backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(), 0, string_query);  
     					if(depositTransactionObject!=null){      				 
     						
     				          System.out.println("DEP ****"+depositTransactionObject);
     				          req.setAttribute("Deposits",depositTransactionObject);
                            }
                            else{
                            	req.setAttribute("displaymsg","RecordsNotFound");
                         	   System.out.println("Records Not Found");
                            }
     				}
                            
     				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
     					array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
     					if(array_pygmyobject!=null){
     					
     					req.setAttribute("Pygmy",array_pygmyobject);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
     						System.out.println("Records Not Found");
     					}
         			}
     				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
     					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 0, string_query);
     					if(acctranobj!=null){
     					      		 
     					      req.setAttribute("AccTran",acctranobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}
     				
     				//req.setAttribute("AccTran",backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 0, string_query));
         			
     				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006"))
         			{
     					lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
     					if(lnobj!=null){
     						
     					    req.setAttribute("LoansOnDep",lnobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
     						System.out.println("Records Not Found");
     					}
         			}  
         			else{
         				array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
         				if(array_jointloanobject!=null){
         				
         				req.setAttribute("Loans",array_jointloanobject);
         				}
         				else{
         					req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
         				}
     			}
     			}	
     			else if(tranSummaryForm.getSelect().equalsIgnoreCase("Individual")){
     				if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
     					shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat,10, string_query);
     					if(shobj!=null){
     					
     					req.setAttribute("Share",shobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
     					depositTransactionObject=backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
     					if(depositTransactionObject!=null){
     						
     					req.setAttribute("Deposits",depositTransactionObject);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
     					array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
     					if(array_pygmyobject!=null){
     					
     				    req.setAttribute("Pygmy",array_pygmyobject);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
     					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 10, string_query);
     					if(acctranobj!=null){
     					      		 
     					      req.setAttribute("AccTran",acctranobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}
     				
     				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006"))
     					{
     					lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
     					if(lnobj!=null){
     					
     					req.setAttribute("LoansOnDep",backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query));
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}else{
     					array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
     					if(array_jointloanobject!=null){
     					
     					req.setAttribute("Loans",array_jointloanobject);
     				}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
         			}
     			}
     		    else if(tranSummaryForm.getSelect().equalsIgnoreCase("Institute")){
     		    	if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){	
     		    		shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat,11, string_query);
     		    		if(shobj!=null){
     		    		
     		    	    req.setAttribute("Share",shobj);
     		    		}
     		    		else{
     		    			req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     		    		}
     		    	}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
     		    		depositTransactionObject=backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);  
     		    		if(depositTransactionObject!=null){        		    		
     		    		
     		    		req.setAttribute("Deposits",depositTransactionObject);
                           }
                           else{
                        	   req.setAttribute("displaymsg","RecordsNotFound");
           					  System.out.println("Records Not Found");
                           }
     		    	}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
     		    		array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
     		    		if(array_pygmyobject!=null){
     		    		
     		    		req.setAttribute("Pygmy",array_pygmyobject);
     		    		}
     		    		else{
     		    			req.setAttribute("displaymsg","RecordsNotFound");
          					 System.out.println("Records Not Found");
     		    		}
     		    	}
     		    	else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
     					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 11, string_query);
     					if(acctranobj!=null){
     					      		 
     					      req.setAttribute("AccTran",acctranobj);
     					}
     					else{
     						req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}
     		       	//req.setAttribute("AccTran",backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 11, string_query));
     		       	else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006")){
     		       		lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
     		       		if(lnobj!=null){
     		       		
     		       		req.setAttribute("LoansOnDep",lnobj);
     		       		}
     		       		else{
     		       		req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     		       		}
     		    }else{
     		    	array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
     		    	if(array_jointloanobject!=null){
     		    	
     		       	req.setAttribute("Loans",array_jointloanobject);
     		        }
     		    	else{
     		    		req.setAttribute("displaymsg","RecordsNotFound");
     					System.out.println("Records Not Found");
     		    	}
     		    }
     			}
     			}
     			
     			if(tranSummaryForm.getForward().equalsIgnoreCase("save")){
     				System.out.println("Inside save ");
           		 //TO save to an excel Sheet
           		 res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("Transaction Summary Statement Details: ");
           	        out.print("\n");
           	        out.print("\n");
              		   /*HSSFCell cell = row.createCell((short)0);
              		   cell.setCellValue(1);*/
           	     if(tranSummaryForm.getSelect().equalsIgnoreCase("All")){	
      				System.out.println("form date"+tranSummaryForm.getFromdate());
      				System.out.println("to date"+tranSummaryForm.getTodate());
      				System.out.println("AccountType"+tranSummaryForm.getAccType());
      				System.out.println("category"+tranSummaryForm.getCategory());
      				if(tranSummaryForm.getAccType()!=null)
      				shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat, 0, string_query);
      				if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
      					if(shobj==null){
      					req.setAttribute("Share",shobj);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Recp Trnf"); out.print(",");
                   		out.print("Recp Csh"); out.print(",");
                   		out.print("Pymt Trnf"); out.print(",");
                   		out.print("Pymt Csh"); out.print(",");
                   		out.print("Frst/Addl./Part/Act.Clsr"); out.print(",");
                   		out.print("Direct/Suspense"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   	 for(int k=0;k<shobj.length;k++){
              			  
                			out.print(shobj[k].getShNo());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print("First");
                			out.print(",");
                			out.print("Direct");
                			out.print(",");
                			out.print(shobj[k].getTrnNarr());
                			out.print(",");
                			out.print(shobj[k].getUid());
                			out.print(",");
                			out.print(shobj[k].getVid());
                			out.print(",");
                			out.print("\n");
                   	 }
                   		
                  
      				
      				}
      				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
      					 depositTransactionObject= backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(), 0, string_query);  
      					if(depositTransactionObject!=null){      				 
      						
      				          System.out.println("DEP ****"+depositTransactionObject);
      				          req.setAttribute("Deposits",depositTransactionObject);
                             }
      					out.print("A/C No"); out.print(",");
                   		out.print("Cash"); out.print(",");
                   		out.print("Clearing"); out.print(",");
                   		out.print("Transfer"); out.print(",");
                   		out.print("Dep Repymt"); out.print(",");
                   		out.print("Int Accrual"); out.print(",");
                   		out.print("Int Pymt"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		for(int k=0;k<depositTransactionObject.length;k++){
                 			  
                   			out.print(depositTransactionObject[k].getAccNo());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getTranNarration());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n"); 
                   		}
      				}
                             
      				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
      					array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
      					if(array_pygmyobject!=null){
      					
      					req.setAttribute("Pygmy",array_pygmyobject);
      					}
      					out.print("Acc No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   		for(int k=0;k<array_pygmyobject.length;k++){
                 			  
                   			out.print(array_pygmyobject[k].getAccountNo());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getNarration());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n");   
                   		}
          			}
      				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
      					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 0, string_query);
      					if(acctranobj!=null){
      					      		 
      					      req.setAttribute("AccTran",acctranobj);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Verfd By"); out.print("\n");
                   		
                   	 for(int k=0;k<acctranobj.length;k++){
              			  
                			out.print(acctranobj[k].getAccNo());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransNarr());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getUserId());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getVerId());
                			out.print(",");
                			out.print("\n"); 
                   	 }
      				}
      				
      				//req.setAttribute("AccTran",backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 0, string_query));
          			
      				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006"))
          			{
      					lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
      					if(lnobj!=null){
      						
      					    req.setAttribute("LoansOnDep",lnobj);
      					}
      					out.print("Acc No"); out.print(",");
                   		out.print("Trn Amt"); out.print(",");
                   		out.print("Cash pr_amt"); out.print(",");
                   		out.print("cash Intamt"); out.print(",");
                   		out.print("Tran pramt"); out.print(",");
                   		out.print("Tran Intamt"); out.print(",");
                   		out.print("Other amt"); out.print(",");
                   		out.print("extra_int"); out.print(",");
                   		out.print("PrBalance"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Verfd By"); out.print("\n");
                   		
                   	 for(int k=0;k<lnobj.length;k++){
              			  
                			out.print(lnobj[k].getAccNo());
                			out.print(",");
                			out.print(lnobj[k].getTransactionAmount());
                			out.print(",");
                			out.print(lnobj[k].getPrincipalPaid());
                			out.print(",");
                			out.print(lnobj[k].getInterestPaid());
                			out.print(",");
                			out.print(lnobj[k].getPrincipalPaid());
                			out.print(",");
                			out.print(lnobj[k].getInterestPaid());
                			out.print(",");
                			out.print(lnobj[k].getOtherAmount());
                			out.print(",");
                			out.print(lnobj[k].getExtraIntPaid());
                			out.print(",");
                			out.print(lnobj[k].getLoanBalance());
                			out.print(",");
                			out.print(lnobj[k].getTranNarration());
                			out.print(",");
                			out.print(lnobj[k].getUv().getUserId());
                			out.print(",");
                			out.print(lnobj[k].getUv().getVerId());
                			out.print(",");
                			out.print("\n");  
                   	 }
          			}  
          			else{
          				array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),0, string_query);
          				if(array_jointloanobject!=null){
          				
          				req.setAttribute("Loans",array_jointloanobject);
          				}
          				out.print("Acc No"); out.print(",");
                   		out.print("Csh Prnpl"); out.print(",");
                   		out.print("Csh Int"); out.print(",");
                   		out.print("Csh Penl"); out.print(",");
                   		out.print("Csh Oth"); out.print(",");
                   		out.print("Csh Tot"); out.print(",");
                   		out.print("Clrng Prnpl"); out.print(",");
                   		out.print("Clrng Int"); out.print(",");
                   		out.print("Clrng Penl"); out.print(",");
                   		out.print("Clrng Oth "); out.print(",");
                   		out.print("Clrng Tot"); out.print(",");
                   		out.print("Trn Prnpl "); out.print(",");
                   		out.print("Trn Int"); out.print(",");
                   		out.print("Trn Penl"); out.print(",");
                   		out.print("Trn Oth"); out.print(",");
                   		out.print("Trn Tot"); out.print(",");
                   		out.print("Dis Csh"); out.print(",");
                   		out.print("Dis Clrng"); out.print(",");
                   		out.print("Dis Trn"); out.print(",");
                   		out.print("Oth Chrgs "); out.print(",");
                   		out.print("Trn Narr "); out.print(",");
                   		out.print("Ref No. "); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Vrfd By"); out.print("\n");
                   		for(int k=0;k<array_jointloanobject.length;k++){
                 			  
                   			out.print(array_jointloanobject[k].getAccNo());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getTranNarration());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getReferenceNo());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getUv().getUserId());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getUv().getVerId());
                   			out.print(",");
                   			out.print("\n");
                   			
                   		}
      			}
      			}	
      			else if(tranSummaryForm.getSelect().equalsIgnoreCase("Individual")){
      				if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){
      					shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat,10, string_query);
      					if(shobj!=null){
      					
      					req.setAttribute("Share",shobj);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Recp Trnf"); out.print(",");
                   		out.print("Recp Csh"); out.print(",");
                   		out.print("Pymt Trnf"); out.print(",");
                   		out.print("Pymt Csh"); out.print(",");
                   		out.print("Frst/Addl./Part/Act.Clsr"); out.print(",");
                   		out.print("Direct/Suspense"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   	 for(int k=0;k<shobj.length;k++){
              			  
                			out.print(shobj[k].getShNo());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print("First");
                			out.print(",");
                			out.print("Direct");
                			out.print(",");
                			out.print(shobj[k].getTrnNarr());
                			out.print(",");
                			out.print(shobj[k].getUid());
                			out.print(",");
                			out.print(shobj[k].getVid());
                			out.print(",");
                			out.print("\n"); 
                   	 }
      				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
      					depositTransactionObject=backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
      					if(depositTransactionObject!=null){
      						
      					req.setAttribute("Deposits",depositTransactionObject);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Cash"); out.print(",");
                   		out.print("Clearing"); out.print(",");
                   		out.print("Transfer"); out.print(",");
                   		out.print("Dep Repymt"); out.print(",");
                   		out.print("Int Accrual"); out.print(",");
                   		out.print("Int Pymt"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		for(int k=0;k<depositTransactionObject.length;k++){
                 			  
                   			out.print(depositTransactionObject[k].getAccNo());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getTranNarration());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n"); 
                   		}
      				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
      					array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
      					if(array_pygmyobject!=null){
      					
      				    req.setAttribute("Pygmy",array_pygmyobject);
      					}
      					out.print("Acc No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   		for(int k=0;k<array_pygmyobject.length;k++){
                 			  
                   			out.print(array_pygmyobject[k].getAccountNo());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getNarration());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n");   
                   		}
      				}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
      					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 10, string_query);
      					if(acctranobj!=null){
      					      		 
      					      req.setAttribute("AccTran",acctranobj);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Verfd By"); out.print("\n");
                   		
                   	 for(int k=0;k<acctranobj.length;k++){
              			  
                			out.print(acctranobj[k].getAccNo());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransNarr());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getUserId());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getVerId());
                			out.print(",");
                			out.print("\n"); 
                   	 }
      				}
      				
      				else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006"))
      					{
      					lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
      					if(lnobj!=null){
      					
      					req.setAttribute("LoansOnDep",backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query));
      					}
      					out.print("Acc No"); out.print(",");
                   		out.print("Trn Amt"); out.print(",");
                   		out.print("Cash pr_amt"); out.print(",");
                   		out.print("cash Intamt"); out.print(",");
                   		out.print("Tran pramt"); out.print(",");
                   		out.print("Tran Intamt"); out.print(",");
                   		out.print("Other amt"); out.print(",");
                   		out.print("extra_int"); out.print(",");
                   		out.print("PrBalance"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Verfd By"); out.print("\n");
                   		
                   	 for(int k=0;k<lnobj.length;k++){
              			  
                			out.print(lnobj[k].getAccNo());
                			out.print(",");
                			out.print(lnobj[k].getTransactionAmount());
                			out.print(",");
                			out.print(lnobj[k].getPrincipalPaid());
                			out.print(",");
                			out.print(lnobj[k].getInterestPaid());
                			out.print(",");
                			out.print(lnobj[k].getPrincipalPaid());
                			out.print(",");
                			out.print(lnobj[k].getInterestPaid());
                			out.print(",");
                			out.print(lnobj[k].getOtherAmount());
                			out.print(",");
                			out.print(lnobj[k].getExtraIntPaid());
                			out.print(",");
                			out.print(lnobj[k].getLoanBalance());
                			out.print(",");
                			out.print(lnobj[k].getTranNarration());
                			out.print(",");
                			out.print(lnobj[k].getUv().getUserId());
                			out.print(",");
                			out.print(lnobj[k].getUv().getVerId());
                			out.print(",");
                			out.print("\n");  
                   	 }
      				}else{
      					array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),10, string_query);
      					if(array_jointloanobject!=null){
      					
      					req.setAttribute("Loans",array_jointloanobject);
      				}
      					out.print("Acc No"); out.print(",");
                   		out.print("Csh Prnpl"); out.print(",");
                   		out.print("Csh Int"); out.print(",");
                   		out.print("Csh Penl"); out.print(",");
                   		out.print("Csh Oth"); out.print(",");
                   		out.print("Csh Tot"); out.print(",");
                   		out.print("Clrng Prnpl"); out.print(",");
                   		out.print("Clrng Int"); out.print(",");
                   		out.print("Clrng Penl"); out.print(",");
                   		out.print("Clrng Oth "); out.print(",");
                   		out.print("Clrng Tot"); out.print(",");
                   		out.print("Trn Prnpl "); out.print(",");
                   		out.print("Trn Int"); out.print(",");
                   		out.print("Trn Penl"); out.print(",");
                   		out.print("Trn Oth"); out.print(",");
                   		out.print("Trn Tot"); out.print(",");
                   		out.print("Dis Csh"); out.print(",");
                   		out.print("Dis Clrng"); out.print(",");
                   		out.print("Dis Trn"); out.print(",");
                   		out.print("Oth Chrgs "); out.print(",");
                   		out.print("Trn Narr "); out.print(",");
                   		out.print("Ref No. "); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Vrfd By"); out.print("\n");
                   		for(int k=0;k<array_jointloanobject.length;k++){
                 			  
                   			out.print(array_jointloanobject[k].getAccNo());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print("");
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPrincipalPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getPenaltyAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getOtherAmount());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getTranNarration());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getReferenceNo());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getUv().getUserId());
                   			out.print(",");
                   			out.print(array_jointloanobject[k].getUv().getVerId());
                   			out.print(",");
                   			out.print("\n");
                   			
                   		}
          			}
      			}
      		    else if(tranSummaryForm.getSelect().equalsIgnoreCase("Institute")){
      		    	if(tranSummaryForm.getAccType().equalsIgnoreCase("1001001")){	
      		    		shobj=backOfficeDelegate.getShareTranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),sharecat,11, string_query);
      		    		if(shobj!=null){
      		    		
      		    	    req.setAttribute("Share",shobj);
      		    		}
      		    		out.print("A/C No"); out.print(",");
                   		out.print("Recp Trnf"); out.print(",");
                   		out.print("Recp Csh"); out.print(",");
                   		out.print("Pymt Trnf"); out.print(",");
                   		out.print("Pymt Csh"); out.print(",");
                   		out.print("Frst/Addl./Part/Act.Clsr"); out.print(",");
                   		out.print("Direct/Suspense"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   	 for(int k=0;k<shobj.length;k++){
              			  
                			out.print(shobj[k].getShNo());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print(shobj[k].getTrnAmt());
                			out.print(",");
                			out.print("First");
                			out.print(",");
                			out.print("Direct");
                			out.print(",");
                			out.print(shobj[k].getTrnNarr());
                			out.print(",");
                			out.print(shobj[k].getUid());
                			out.print(",");
                			out.print(shobj[k].getVid());
                			out.print(",");
                   	 }
      		    	}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1003001")||tranSummaryForm.getAccType().equalsIgnoreCase("1004001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005001")||tranSummaryForm.getAccType().equalsIgnoreCase("1005002")||tranSummaryForm.getAccType().equalsIgnoreCase("1005003")){
      		    		depositTransactionObject=backOfficeDelegate.getDepositReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);  
      		    		if(depositTransactionObject!=null){        		    		
      		    		
      		    		req.setAttribute("Deposits",depositTransactionObject);
                            }
      		    		out.print("A/C No"); out.print(",");
                   		out.print("Cash"); out.print(",");
                   		out.print("Clearing"); out.print(",");
                   		out.print("Transfer"); out.print(",");
                   		out.print("Dep Repymt"); out.print(",");
                   		out.print("Int Accrual"); out.print(",");
                   		out.print("Int Pymt"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		for(int k=0;k<depositTransactionObject.length;k++){
                 			  
                   			out.print(depositTransactionObject[k].getAccNo());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getDepositPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestAmt());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getInterestPaid());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getTranNarration());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(depositTransactionObject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n"); 
                   		}
      		    	}else if(tranSummaryForm.getAccType().equalsIgnoreCase("1006001")){
      		    		array_pygmyobject=backOfficeDelegate.getPygmy(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
      		    		if(array_pygmyobject!=null){
      		    		
      		    		req.setAttribute("Pygmy",array_pygmyobject);
      		    		}
      		    		out.print("Acc No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entered By"); out.print(",");
                   		out.print("Verified By"); out.print("\n");
                   		
                   		for(int k=0;k<array_pygmyobject.length;k++){
                 			  
                   			out.print(array_pygmyobject[k].getAccountNo());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getTransactionAmount());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getNarration());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getUserId());
                   			out.print(",");
                   			out.print(array_pygmyobject[k].getObj_userverifier().getVerId());
                   			out.print(",");
                   			out.print("\n");   
                   		}
      		    	}
      		    	else if(tranSummaryForm.getAccType().equalsIgnoreCase("1002001")||tranSummaryForm.getAccType().equalsIgnoreCase("1007001")||tranSummaryForm.getAccType().equalsIgnoreCase("1015001")||tranSummaryForm.getAccType().equalsIgnoreCase("1014001")){        	
      					acctranobj=backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 11, string_query);
      					if(acctranobj!=null){
      					      		 
      					      req.setAttribute("AccTran",acctranobj);
      					}
      					out.print("A/C No"); out.print(",");
                   		out.print("Rec Csh"); out.print(",");
                   		out.print("Rec Clearg"); out.print(",");
                   		out.print("Rec Trnf"); out.print(",");
                   		out.print("Pay Csh"); out.print(",");
                   		out.print("Pay Clearg"); out.print(",");
                   		out.print("Pay Trnf"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Entd By"); out.print(",");
                   		out.print("Verfd By"); out.print("\n");
                   		
                   	 for(int k=0;k<acctranobj.length;k++){
              			  
                			out.print(acctranobj[k].getAccNo());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransAmount());
                			out.print(",");
                			out.print(acctranobj[k].getTransNarr());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getUserId());
                			out.print(",");
                			out.print(acctranobj[k].getUv().getVerId());
                			out.print(",");
                			out.print("\n"); 
                   	 }
      				}
      		       	//req.setAttribute("AccTran",backOfficeDelegate.getSBCATranSummary(tranSummaryForm.getAccType(), tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(), 11, string_query));
      		       	else if(tranSummaryForm.getAccType().equalsIgnoreCase("1008001") || tranSummaryForm.getAccType().equalsIgnoreCase("1008002") || tranSummaryForm.getAccType().equalsIgnoreCase("1008003") || tranSummaryForm.getAccType().equalsIgnoreCase("1008004") || tranSummaryForm.getAccType().equalsIgnoreCase("1008005") || tranSummaryForm.getAccType().equalsIgnoreCase("1008006")){
      		       		lnobj=backOfficeDelegate.getLoanTransactionReport(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
      		       		if(lnobj!=null){
      		       		
      		       		req.setAttribute("LoansOnDep",lnobj);
      		       		}
      		       	out.print("Acc No"); out.print(",");
               		out.print("Trn Amt"); out.print(",");
               		out.print("Cash pr_amt"); out.print(",");
               		out.print("cash Intamt"); out.print(",");
               		out.print("Tran pramt"); out.print(",");
               		out.print("Tran Intamt"); out.print(",");
               		out.print("Other amt"); out.print(",");
               		out.print("extra_int"); out.print(",");
               		out.print("PrBalance"); out.print(",");
               		out.print("Narration"); out.print(",");
               		out.print("Entd By"); out.print(",");
               		out.print("Verfd By"); out.print("\n");
               		
               	 for(int k=0;k<lnobj.length;k++){
          			  
            			out.print(lnobj[k].getAccNo());
            			out.print(",");
            			out.print(lnobj[k].getTransactionAmount());
            			out.print(",");
            			out.print(lnobj[k].getPrincipalPaid());
            			out.print(",");
            			out.print(lnobj[k].getInterestPaid());
            			out.print(",");
            			out.print(lnobj[k].getPrincipalPaid());
            			out.print(",");
            			out.print(lnobj[k].getInterestPaid());
            			out.print(",");
            			out.print(lnobj[k].getOtherAmount());
            			out.print(",");
            			out.print(lnobj[k].getExtraIntPaid());
            			out.print(",");
            			out.print(lnobj[k].getLoanBalance());
            			out.print(",");
            			out.print(lnobj[k].getTranNarration());
            			out.print(",");
            			out.print(lnobj[k].getUv().getUserId());
            			out.print(",");
            			out.print(lnobj[k].getUv().getVerId());
            			out.print(",");
            			out.print("\n");  
               	 }
      		    }else{
      		    	array_jointloanobject=backOfficeDelegate.getLoanTranSummary(tranSummaryForm.getFromdate(),tranSummaryForm.getTodate(),tranSummaryForm.getAccType(),11, string_query);
      		    	if(array_jointloanobject!=null){
      		    	
      		       	req.setAttribute("Loans",array_jointloanobject);
      		        }
      		    	out.print("Acc No"); out.print(",");
               		out.print("Csh Prnpl"); out.print(",");
               		out.print("Csh Int"); out.print(",");
               		out.print("Csh Penl"); out.print(",");
               		out.print("Csh Oth"); out.print(",");
               		out.print("Csh Tot"); out.print(",");
               		out.print("Clrng Prnpl"); out.print(",");
               		out.print("Clrng Int"); out.print(",");
               		out.print("Clrng Penl"); out.print(",");
               		out.print("Clrng Oth "); out.print(",");
               		out.print("Clrng Tot"); out.print(",");
               		out.print("Trn Prnpl "); out.print(",");
               		out.print("Trn Int"); out.print(",");
               		out.print("Trn Penl"); out.print(",");
               		out.print("Trn Oth"); out.print(",");
               		out.print("Trn Tot"); out.print(",");
               		out.print("Dis Csh"); out.print(",");
               		out.print("Dis Clrng"); out.print(",");
               		out.print("Dis Trn"); out.print(",");
               		out.print("Oth Chrgs "); out.print(",");
               		out.print("Trn Narr "); out.print(",");
               		out.print("Ref No. "); out.print(",");
               		out.print("Entd By"); out.print(",");
               		out.print("Vrfd By"); out.print("\n");
               		for(int k=0;k<array_jointloanobject.length;k++){
             			  
               			out.print(array_jointloanobject[k].getAccNo());
               			out.print(",");
               			out.print(array_jointloanobject[k].getPrincipalPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getInterestPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getPenaltyAmount());
               			out.print(",");
               			out.print(array_jointloanobject[k].getOtherAmount());
               			out.print(",");
               			out.print("");
               			out.print(",");
               			out.print(array_jointloanobject[k].getPrincipalPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getInterestPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getPenaltyAmount());
               			out.print(",");
               			out.print(array_jointloanobject[k].getOtherAmount());
               			out.print(",");
               			out.print("");
               			out.print(",");
               			out.print(array_jointloanobject[k].getPrincipalPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getInterestPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getPenaltyAmount());
               			out.print(",");
               			out.print(array_jointloanobject[k].getOtherAmount());
               			out.print(",");
               			out.print("");
               			out.print(",");
               			out.print(array_jointloanobject[k].getPrincipalPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getInterestPaid());
               			out.print(",");
               			out.print(array_jointloanobject[k].getPenaltyAmount());
               			out.print(",");
               			out.print(array_jointloanobject[k].getOtherAmount());
               			out.print(",");
               			out.print(array_jointloanobject[k].getTranNarration());
               			out.print(",");
               			out.print(array_jointloanobject[k].getReferenceNo());
               			out.print(",");
               			out.print(array_jointloanobject[k].getUv().getUserId());
               			out.print(",");
               			out.print(array_jointloanobject[k].getUv().getVerId());
               			out.print(",");
               			out.print("\n");
               			
               		}
      		    }
     				
      		    }
           	  req.setAttribute("msg","Saved to excel file in C:");
           	  return null;
     			}
     			
     			req.setAttribute("shcat",backOfficeDelegate.getShareCategory());
     			req.setAttribute("AccTypes",backOfficeDelegate.getMainModules(3));
     			req.setAttribute("Select",backOfficeDelegate.getSelect());
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(tranSummaryForm.getPageId()));
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		System.out.println("inside try in else");
         		tranSummaryForm.setValid("error1");
         		return map.findForward(ResultHelp.getSuccess());
          	
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		System.out.println("inside catch");
         		tranSummaryForm.setValid("error2");
         		return map.findForward(ResultHelp.getSuccess());
          	}
     }
   
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SBCALedgerMenu")){
   	try{    
   		 
   		String string_vchty=null,string_query=null;
   		
   		SBCALedgerForm sbcaLedgform=(SBCALedgerForm) form;
   		System.out.println("Get Page ID========12333333"+sbcaLedgform.getPageId());
   		if(MenuNameReader.containsKeyScreen(sbcaLedgform.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(sbcaLedgform.getPageId()));
   			req.setAttribute("AccTypes",backOfficeDelegate.getMainModules(2));
   			req.setAttribute("select",backOfficeDelegate.getSelected());
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SBCALedger")){
   	try{
   		 
   		String string_vchty=null,string_query=null;
   		
   		SBCALedgerForm sbcaLedgform=(SBCALedgerForm) form;
   		System.out.println("Get Page ID========12333333"+sbcaLedgform.getPageId());
   		if(MenuNameReader.containsKeyScreen(sbcaLedgform.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			AccountTransObject[] accounttransobject_view=null;
   			AccountObject[] array_accountobject_view=null;     			
   			//if(array_accountobject_view!=null){
   			if(sbcaLedgform.getToacno()!=0){
   				 accounttransobject_view=backOfficeDelegate.getAccountTransaction(sbcaLedgform.getFromacno(),sbcaLedgform.getAccType());
       			 array_accountobject_view=backOfficeDelegate.getAccount(sbcaLedgform.getAccType(),sbcaLedgform.getFromacno(),sbcaLedgform.getToacno(),sbcaLedgform.getFromdate(),sbcaLedgform.getTodate());     			
   				if(array_accountobject_view!=null){
   				
    				for(int i=0;i<array_accountobject_view.length;i++){
   				sbcaLedgform.setAccno(array_accountobject_view[i].getAccno());
   				sbcaLedgform.setAccname(String.valueOf(array_accountobject_view[i].getAccname()));
   				sbcaLedgform.setCustid(String.valueOf(array_accountobject_view[i].getCid()));
   				sbcaLedgform.setChq_bk_issue(array_accountobject_view[i].getChBkIssue());
   				sbcaLedgform.setOpenedon(array_accountobject_view[i].getAcOpenDate());
   				sbcaLedgform.setAccategory(String.valueOf(array_accountobject_view[i].getAcCategory()));
   				
   				if(sbcaLedgform.getAccType().equals("SB")|| sbcaLedgform.getAccType().equals("CA"))
   					sbcaLedgform.setInt_pd_upto(array_accountobject_view[i].getIntPblDate());
   				else			
   					sbcaLedgform.setInt_revd_upto(array_accountobject_view[i].getIntPblDate());
    				
    				     int nomreg=array_accountobject_view[i].getNomRegno();
    				     NomineeObject nomineeobject_view=backOfficeDelegate.getNominee(nomreg);
                       
    				     sbcaLedgform.setNominee_name(nomineeobject_view.getNomineeName());
    				     sbcaLedgform.setRelationship((String.valueOf(nomineeobject_view.getNomineeRelation())));
    				  
    				  if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("C"));
    				  sbcaLedgform.setAccstatus("CLOSED");
   			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("O"));
   			   sbcaLedgform.setAccstatus("ACTIVE");
   			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("I"));
   			   sbcaLedgform.setAccstatus("INOPERATIVE");
    				}
    				}
    				else{
        				System.out.println("Records Not Found");
        			}
   			          req.setAttribute("AccTranDetails",accounttransobject_view);
    			}
    			
    			System.out.println("Account Num===>"+sbcaLedgform.getAccno()); 
    			if(sbcaLedgform.getTodate()!=null){
    				accounttransobject_view=backOfficeDelegate.getAccountTransaction(sbcaLedgform.getToacno(),sbcaLedgform.getAccType());
    				
    				
    				
      		    array_accountobject_view=backOfficeDelegate.getAccounts(sbcaLedgform.getAccType(), sbcaLedgform.getToacno());
  			
    				   if(array_accountobject_view!=null){
    				for(int i=0;i<array_accountobject_view.length;i++){
       				sbcaLedgform.setAccno(array_accountobject_view[i].getAccno());
       				sbcaLedgform.setAccname(String.valueOf(array_accountobject_view[i].getAccname()));
       				sbcaLedgform.setCustid(String.valueOf(array_accountobject_view[i].getCid()));
       				sbcaLedgform.setChq_bk_issue(array_accountobject_view[i].getChBkIssue());
       				sbcaLedgform.setOpenedon(array_accountobject_view[i].getAcOpenDate());
       				sbcaLedgform.setAccategory(String.valueOf(array_accountobject_view[i].getAcCategory()));
       				
       				if(sbcaLedgform.getAccType().equals("SB")|| sbcaLedgform.getAccType().equals("CA"))
       					sbcaLedgform.setInt_pd_upto(array_accountobject_view[i].getIntPblDate());
       				else			
       					sbcaLedgform.setInt_revd_upto(array_accountobject_view[i].getIntPblDate());
        				
        				     int nomreg=array_accountobject_view[i].getNomRegno();
        				     NomineeObject nomineeobject_view=backOfficeDelegate.getNominee(nomreg);
                           
        				     sbcaLedgform.setNominee_name(nomineeobject_view.getNomineeName());
        				     sbcaLedgform.setRelationship((String.valueOf(nomineeobject_view.getNomineeRelation())));
        				  
        				  if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("C"));
        				  sbcaLedgform.setAccstatus("CLOSED");
       			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("O"));
       			   sbcaLedgform.setAccstatus("ACTIVE");
       			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("I"));
       			   sbcaLedgform.setAccstatus("INOPERATIVE");
        				}
    				   }
    				else{
        				req.setAttribute("displaymsg","Records Not Found");
        			}
    				req.setAttribute("AccTranDetails",accounttransobject_view);
        		}
    				if(sbcaLedgform.getAccno()!=0){
    				accounttransobject_view=backOfficeDelegate.getAccountTransaction(sbcaLedgform.getToacno(),sbcaLedgform.getAccType());
      			array_accountobject_view=backOfficeDelegate.getAccounts(sbcaLedgform.getAccType(),sbcaLedgform.getToacno());     			
  			
    				if(array_accountobject_view!=null){
    				for(int i=0;i<array_accountobject_view.length;i++){
       				sbcaLedgform.setAccno(array_accountobject_view[i].getAccno());
       				sbcaLedgform.setAccname(String.valueOf(array_accountobject_view[i].getAccname()));
       				sbcaLedgform.setCustid(String.valueOf(array_accountobject_view[i].getCid()));
       				sbcaLedgform.setChq_bk_issue(array_accountobject_view[i].getChBkIssue());
       				sbcaLedgform.setOpenedon(array_accountobject_view[i].getAcOpenDate());
       				sbcaLedgform.setAccategory(String.valueOf(array_accountobject_view[i].getAcCategory()));
       				
       				if(sbcaLedgform.getAccType().equals("SB")|| sbcaLedgform.getAccType().equals("CA"))
       					sbcaLedgform.setInt_pd_upto(array_accountobject_view[i].getIntPblDate());
       				else			
       					sbcaLedgform.setInt_revd_upto(array_accountobject_view[i].getIntPblDate());
        				
        				     int nomreg=array_accountobject_view[i].getNomRegno();
        				     NomineeObject nomineeobject_view=backOfficeDelegate.getNominee(nomreg);
                           
        				     sbcaLedgform.setNominee_name(nomineeobject_view.getNomineeName());
        				     sbcaLedgform.setRelationship((String.valueOf(nomineeobject_view.getNomineeRelation())));
        				     
        				  if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("C"));
        				sbcaLedgform.setAccstatus("CLOSED");
       			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("O"));
       			   sbcaLedgform.setAccstatus("ACTIVE");
       			      if(array_accountobject_view[i].getAccStatus().trim().equalsIgnoreCase("I"));
       			   sbcaLedgform.setAccstatus("INOPERATIVE");
        				}
    				}
    				else{
        				req.setAttribute("displaymsg","Records Not Found");
        			}
        			
    				req.setAttribute("AccTranDetails",accounttransobject_view);
        			      				
    			}
    			if(sbcaLedgform.getForward().equalsIgnoreCase("download")) {
						System.out.println("I am in download=======");
						if(sbcaLedgform.getAccno()!=0){
		    				accounttransobject_view=backOfficeDelegate.getAccountTransaction(sbcaLedgform.getToacno(),sbcaLedgform.getAccType());
		      			array_accountobject_view=backOfficeDelegate.getAccounts(sbcaLedgform.getAccType(),sbcaLedgform.getToacno());     			
			   				if(array_accountobject_view==null){
							sbcaLedgform.setTesting("Cannot Print");
						} else {
							System.out.println(" i am inside down load");

							// TO save to an excel Sheet
							res.setContentType("application/.csv");
							res.setHeader("Content-disposition","attachment;filename=SBCALedger.csv");

							java.io.PrintWriter out = res.getWriter();
							out.print("\n");
							out.print("\n");
							out.print("\n");
							out.print(",");
							out.print(",");
							out.print(",");
							out.print("SBCALedger Details for A/C Type: "+ array_accountobject_view[0].getAcctype());
							out.print("\n");
							out.print("\n");
							out.print("Date");
    						out.print(",");
    						out.print("Cheque No");
    						out.print(",");
    						out.print("Narration/PayeeNAme");
    						out.print(",");
    						out.print("Narration/PayeeNAme");
    						out.print(",");
    						out.print("Closing Balance");
    						out.print(",");
    						out.print("Debit");
    						out.print(",");
    						out.print("Interest Rate");
    						out.print(",");
    						out.print("Credit");
    						out.print(",");
    						out.print("\n");
                              if(accounttransobject_view!=null){
    						for (int k = 0; k < accounttransobject_view.length; k++) {
    							out.print(k);
    							out.print(",");
    							out.print(accounttransobject_view[k].getTransDate());
    							out.print(",");
    							out.print(accounttransobject_view[k].getChqDDNo());
    							out.print(",");
    							out.print(accounttransobject_view[k].getTransNarr());
    							out.print(",");
    							out.print(accounttransobject_view[k].getPayeeName());
    							out.print(",");
    							out.print(accounttransobject_view[k].getCloseBal());
    							out.print(",");
    							out.print(accounttransobject_view[k].getTransAmount());
    							out.print(",");
    							out.print(accounttransobject_view[k].getTransAmount());
    							out.print(",");
    							out.print("\n");
    						}
                              }
    						req.setAttribute("msg","Saved to excel file in C:");
    						return null;
                      }
					}

				}

    
    				
   			
   			/*}
   			else{
   				System.out.println("Records Not Found");
   			}
    			*/
    			
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(sbcaLedgform.getPageId()));
   			req.setAttribute("AccTypes",backOfficeDelegate.getMainModules(2));
   			req.setAttribute("select",backOfficeDelegate.getSelected());
   			return map.findForward(ResultHelp.getSuccess());
   		
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
   		}
   		catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
     
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ClosingBalanceReportMenu")){
   	try{
   		 
   		String string_vchty=null,string_query=null;
   		ClosingBalObject[] closeBalObjSB=null,closeBalObjSH=null,closeBalObjLn=null,closeBalObjDep=null,closeBalObjLnD=null,closeBalObjPD=null;
   		ClosingBalanceReportForm closebalrep=(ClosingBalanceReportForm) form;
   		System.out.println("Get Page ID========12333333"+closebalrep.getPageId());
   		if(MenuNameReader.containsKeyScreen(closebalrep.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			closebalrep.setSysdate(BackOfficeDelegate.getSysDate());
   			/*if(closebalrep.getForward()!=null){
   			
   			if(closebalrep.getForward().equalsIgnoreCase("view")){
   				//String string_date=closebalrep.getDate();
   				String string_modcode=closebalrep.getAcctype();
   				String string_cpymodcode=string_modcode.substring(1,4);
   				System.out.println("------^^^^^^^^modCode---------"+string_cpymodcode);
   				
   				   				
   				
   				
   				if(string_cpymodcode.equals("001")){
   					closeBalObjSH=backOfficeDelegate.getReportSH(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate(), closebalrep.getShcategory());	
   				if(closeBalObjSH!=null){
                      req.setAttribute("CloseBalSH", closeBalObjSH);
   				}
   				else{
   					closebalrep.setValid("RecordsNotFound");
   					System.out.println("Records Not Found");
   				}
   				}
   				else if(string_cpymodcode.equals("002") || string_cpymodcode.equals("007") || string_cpymodcode.equals("018") || string_cpymodcode.equals("014")|| string_cpymodcode.equals("015"))
   				    {
   					closeBalObjSB=backOfficeDelegate.getReportSBNew(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate(),Integer.parseInt(closebalrep.getAcccategory()));
   					if(closeBalObjSB!=null){
   					req.setAttribute("CloseBalSB", closeBalObjSB);
   					}
   					else{
   						closebalrep.setValid("RecordsNotFound");
       					System.out.println("Records Not Found");
   				    }
   				    }
   				else if(string_cpymodcode.equals("003")||string_cpymodcode.equals("004")||string_cpymodcode.equals("005")){
   					closeBalObjDep=backOfficeDelegate.getReportTD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
   					if(closeBalObjDep!=null){
   					req.setAttribute("CloseBalDEP", closeBalObjDep);
   					}
   					else{
   						closebalrep.setValid("RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}			
   				else if(string_cpymodcode.equals("006")){
   					closeBalObjPD=backOfficeDelegate.getReportPD(string_modcode,closebalrep.getFromdate(),closebalrep.getTodate());
   					if(closeBalObjPD!=null){
   					req.setAttribute("CloseBalPDACCWISE", closeBalObjPD);
   					}
   					else{
   						closebalrep.setValid("RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}
   				else if(string_cpymodcode.equals("008")){
   					closeBalObjLnD=backOfficeDelegate.getReportLnD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
   					if(closeBalObjLnD!=null){
   					req.setAttribute("CloseBalLND", closeBalObjLnD);
   					}
   					else{
   						closebalrep.setValid("RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}
   				else if(string_cpymodcode.equals("010")){
   					closeBalObjLn=backOfficeDelegate.getReportLn(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate());
   					if(closeBalObjLn!=null){
   					System.out.println("---action inside ln---$");
   					req.setAttribute("CloseBalLN", closeBalObjLn);
   					}
   					else{
   						closebalrep.setValid("RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   			}
   		}
   			}
   		*/	
   			req.setAttribute("acctype",backOfficeDelegate.getMainModules(3));   			
   			req.setAttribute("sharecate",backOfficeDelegate.getShareCategory());
   			req.setAttribute("Acccategory",backOfficeDelegate.getSelected());
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(closebalrep.getPageId()));
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
     
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ClosingBalanceReport")){
     	try{
     		 
     		String string_vchty=null,string_query=null;
     		
     		ClosingBalanceReportForm closebalrep=(ClosingBalanceReportForm) form;
     		System.out.println("Get Page ID========12333333"+closebalrep.getPageId());
     		if(MenuNameReader.containsKeyScreen(closebalrep.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			closebalrep.setSysdate(BackOfficeDelegate.getSysDate());
     			if(closebalrep.getForward()!=null){
     			
     			if(closebalrep.getForward().equalsIgnoreCase("view")){
     				ClosingBalObject[] closeBalObjSB=null,closeBalObjSH=null,closeBalObjLn=null,closeBalObjDep=null,closeBalObjLnD=null,closeBalObjPD=null;
     				//String string_date=closebalrep.getDate();
     				String string_modcode=closebalrep.getAcctype();
     				String string_cpymodcode=string_modcode.substring(1,4);
     				System.out.println("------^^^^^^^^modCode---------"+string_cpymodcode);
     				
     				   				
     				
     				
     				if(string_cpymodcode.equals("001")){
     					if(closeBalObjSH!=null){
     					closeBalObjSH=backOfficeDelegate.getReportSH(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate(), closebalrep.getShcategory());	
     					}else{
     						req.setAttribute("displaymsg", "Records Not Found");
     					}
     						
     				}
     				else if(string_cpymodcode.equals("002") || string_cpymodcode.equals("007") || string_cpymodcode.equals("018") || string_cpymodcode.equals("014")|| string_cpymodcode.equals("015"))
     				    {
     					int i=0;
     					if(closebalrep.getAcccategory().equalsIgnoreCase("selected")){
     						i=1;
     					}
     					else if(closebalrep.getAcccategory().equalsIgnoreCase("all")){
     						i=2;
     					}
     					else{
     						i=-1;
     					}
     					closeBalObjSB=backOfficeDelegate.getReportSBNew(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate(),i);
     					if(closeBalObjSB!=null){
     					req.setAttribute("CloseBalSB", closeBalObjSB);
     					}
     					else{
     							req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     				    }
     				    }
     				else if(string_cpymodcode.equals("003")||string_cpymodcode.equals("004")||string_cpymodcode.equals("005")){
     					closeBalObjDep=backOfficeDelegate.getReportTD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjDep!=null){
     					req.setAttribute("CloseBalDEP", closeBalObjDep);
     					}
     					else{
     							req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}			
     				else if(string_cpymodcode.equals("006")){
     					closeBalObjPD=backOfficeDelegate.getReportPD(string_modcode,closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjPD!=null){
     					req.setAttribute("CloseBalPDACCWISE", closeBalObjPD);
     					}
     					else{
     							req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}
     				else if(string_cpymodcode.equals("008")){
     					closeBalObjLnD=backOfficeDelegate.getReportLnD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjLnD!=null){
     					req.setAttribute("CloseBalLND", closeBalObjLnD);
     					}
     					else{
     							req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     				}
     				else if(string_cpymodcode.equals("010")){
     					closeBalObjLn=backOfficeDelegate.getReportLn(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate());
     					if(closeBalObjLn!=null){
     					System.out.println("---action inside ln---$");
     					req.setAttribute("CloseBalLN", closeBalObjLn);
     					}
     					else{
     							req.setAttribute("displaymsg","RecordsNotFound");
         					System.out.println("Records Not Found");
     					}
     			}
     		}
     			}
     			
     			if(closebalrep.getForward().equalsIgnoreCase("save")){
     				ClosingBalObject[] closeBalObjSB=null,closeBalObjSH=null,closeBalObjLn=null,closeBalObjDep=null,closeBalObjLnD=null,closeBalObjPD=null;
     				 System.out.println("Inside save ");
            		 //TO save to an excel Sheet
            		 res.setContentType("application/.csv");
            	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
            	        
            	        java.io.PrintWriter out = res.getWriter();
            	        out.print("\n");
            	        out.print("\n");
            	        out.print("\n");
            	        out.print(",");out.print(",");out.print(",");
            	        out.print("Closing Balance Details: ");
            	        out.print("\n");
            	        out.print("\n");
               		   /*HSSFCell cell = row.createCell((short)0);
               		   cell.setCellValue(1);*/
            	       
               		/*out.print("Account No"); out.print(",");
               		out.print("Name"); out.print(",");
               		out.print("O/B"); out.print(",");
               		out.print("Received"); out.print(",");
               		out.print("RePayment"); out.print(",");
               		out.print("C/B"); out.print(",");
               		out.print("O/B"); out.print(",");
               		out.print("Accrued"); out.print(",");
               		out.print("Paid"); out.print(",");
               		out.print("C/B(int)"); out.print(",");
               		out.print("NetCB"); out.print("\n");*/
               		
               		String string_modcode=closebalrep.getAcctype();
     				String string_cpymodcode=string_modcode.substring(1,4);
     				System.out.println("------^^^^^^^^modCode---------"+string_cpymodcode);
     				if(string_cpymodcode.equals("001")){
     					closeBalObjSH=backOfficeDelegate.getReportSH(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate(), closebalrep.getShcategory());	
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("Share Type"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Allots"); out.print(",");
                   		out.print("Withdrawal"); out.print(",");
                   		out.print("Permanent (C/B)"); out.print(",");
                   		out.print("Permanent (C/B)"); out.print("\n");
     				
     					if(closeBalObjSH!=null){
     						req.setAttribute("CloseBalSH",closeBalObjSH);
     				      }     					for(int k=0;k<closeBalObjSH.length;k++){
     						if(closeBalObjSH[k]!=null){
     						out.print(closeBalObjSH[k].getAcNo());
                			out.print(",");
                			out.print(closeBalObjSH[k].getName());
                			out.print(",");
                			out.print(closeBalObjSH[k].getShareInd());
                			out.print(",");
                			out.print(closeBalObjSH[k].getOpenBal());
                			out.print(",");
                			out.print(closeBalObjSH[k].getCreditAmt());
                			out.print(",");
                			out.print(closeBalObjSH[k].getDebitAmt());
                			out.print(",");
                			out.print(closeBalObjSH[k].getClBal());
                			out.print(",");
                			out.print(closeBalObjSH[k].getInterest());
                			out.print(",");
                			out.print("\n");   
     						}
     						}
     				}
     				else if(string_cpymodcode.equals("002") || string_cpymodcode.equals("007") || string_cpymodcode.equals("018") || string_cpymodcode.equals("014")|| string_cpymodcode.equals("015"))
     				    {
     					int i=0;
     					if(closebalrep.getAcccategory().equalsIgnoreCase("selected")){
     						i=1;
     					}
     					else if(closebalrep.getAcccategory().equalsIgnoreCase("all")){
     						i=2;
     					}
     					else{
     						i=-1;
     					}
     					closeBalObjSB=backOfficeDelegate.getReportSBNew(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate(),i);
     					if(closeBalObjSB!=null){
     					req.setAttribute("CloseBalSB", closeBalObjSB);
     					}
     					
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Received"); out.print(",");
                   		out.print("Payments"); out.print(",");
                   		out.print("C/B"); out.print(",");
                   		out.print("Interest"); out.print("\n");
                   		
     					
     					 for(int k=0;k<closeBalObjSB.length;k++){
     						out.print(closeBalObjSB[k].getAcNo());
                			out.print(",");
                			out.print(closeBalObjSB[k].getName());
                			out.print(",");
                			out.print(closeBalObjSB[k].getOpenBal());
                			out.print(",");
                			out.print(closeBalObjSB[k].getCreditAmt());
                			out.print(",");
                			out.print(closeBalObjSB[k].getDebitAmt());
                			out.print(",");
                			out.print(closeBalObjSB[k].getClBal());
                			out.print(",");
                			out.print(closeBalObjSB[k].getInterest());
                			out.print(",");
                			out.print("\n");   
         					 
         				 }
     				    }
     				else if(string_cpymodcode.equals("003")||string_cpymodcode.equals("004")||string_cpymodcode.equals("005")){
     					closeBalObjDep=backOfficeDelegate.getReportTD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjDep!=null){
     					req.setAttribute("CloseBalDEP", closeBalObjDep);
     					}
     					
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Received"); out.print(",");
                   		out.print("RePayment"); out.print(",");
                   		out.print("C/B"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Accrued"); out.print(",");
                   		out.print("Paid"); out.print(",");
                   		out.print("C/B(int)"); out.print(",");
                   		out.print("NetCB"); out.print("\n");
     					 for(int k=0;k<closeBalObjDep.length;k++){
     						out.print(closeBalObjDep[k].getAcNo());
                			out.print(",");
                			out.print(closeBalObjDep[k].getName());
                			out.print(",");
                			out.print(closeBalObjDep[k].getShareInd());
                			out.print(",");
                			out.print(closeBalObjDep[k].getOpenBal());
                			out.print(",");
                			out.print(closeBalObjDep[k].getCreditAmt());
                			out.print(",");
                			out.print(closeBalObjDep[k].getDebitAmt());
                			out.print(",");
                			out.print(closeBalObjDep[k].getClBal());
                			out.print(",");
                			out.print(closeBalObjDep[k].getInterest());
                			out.print(",");
                			out.print("\n"); 
     					 }
     					
     				}			
     				else if(string_cpymodcode.equals("006")){
     					closeBalObjPD=backOfficeDelegate.getReportPD(string_modcode,closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjPD!=null){
     					req.setAttribute("CloseBalPDACCWISE", closeBalObjPD);
     					}
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Received"); out.print(",");
                   		out.print("RePayment"); out.print(",");
                   		out.print("C/B"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Accrued"); out.print(",");
                   		out.print("Paid"); out.print(",");
                   		out.print("C/B(int)"); out.print("\n");
                   		
     					 for(int k=0;k<closeBalObjPD.length;k++){
      						out.print(closeBalObjPD[k].getAcNo());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getName());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getOpenBal());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getCreditAmt());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getDebitAmt());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getClBal());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getIntOpenBal());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getIntAcd());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getIntPaid());
                 			out.print(",");
                 			out.print(closeBalObjPD[k].getIntClBal());
                 			out.print(",");
                 			out.print("\n");
                 			
     					 }
     				}
     				else if(string_cpymodcode.equals("008")){
     					closeBalObjLnD=backOfficeDelegate.getReportLnD(string_modcode, closebalrep.getFromdate(),closebalrep.getTodate());
     					if(closeBalObjLnD!=null){
     					req.setAttribute("CloseBalLND", closeBalObjLnD);
     					}
     					
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Disp Amt"); out.print(",");
                   		out.print("Recy Pr Amt"); out.print(",");
                   		out.print("Recy Int Amt"); out.print(",");
                   		out.print("C/B"); out.print("\n");
                   		
     					 for(int k=0;k<closeBalObjLnD.length;k++){
       						out.print(closeBalObjLnD[k].getAcNo());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getName());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getOpenBal());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getCreditAmt());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getDebitAmt());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getIntRcd());
                  			out.print(",");
                  			out.print(closeBalObjLnD[k].getClBal());
                  			out.print(",");
                  			out.print("\n");
     					 }
     				}
     				else if(string_cpymodcode.equals("010")){
     					closeBalObjLn=backOfficeDelegate.getReportLn(string_modcode, closebalrep.getFromdate(), closebalrep.getTodate());
     					if(closeBalObjLn!=null){
     					System.out.println("---action inside ln---$");
     					req.setAttribute("CloseBalLN", closeBalObjLn);
     					}
     					out.print("Account No"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("O/B"); out.print(",");
                   		out.print("Disp Amt"); out.print(",");
                   		out.print("Recovery Amt"); out.print(",");
                   		out.print("C/B"); out.print(",");
                   		out.print("Int Recd"); out.print(",");
                   		out.print("Int Date"); out.print(",");
                   		out.print("P-Int Recd"); out.print(",");
                   		out.print("Dr Amt"); out.print(",");
                   		out.print("Recy"); out.print("\n");
                   		
     					 for(int k=0;k<closeBalObjLn.length;k++){
        					out.print(closeBalObjLn[k].getAcNo());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getName());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getOpenBal());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getCreditAmt());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getDebitAmt());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getClBal());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getIntRcd());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getIntDate());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getOtherAmt());
                   			out.print(",");
                   			out.print(closeBalObjLn[k].getOtherRecoveryAmt());
                   			out.print(",");
                   			out.print("\n");
                   			
     					 }
     				}
     				
     				 
     				
     				 req.setAttribute("msg","Saved to excel file in C:");
         		    return null;
     			}
     			
     			
     			
     			
     			req.setAttribute("acctype",backOfficeDelegate.getMainModules(3));   			
     			req.setAttribute("sharecate",backOfficeDelegate.getShareCategory());
     			req.setAttribute("Acccategory",backOfficeDelegate.getSelected());
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(closebalrep.getPageId()));
     			
     			
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
   
 
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ClosingBalanceSummaryMenu")){
     	try{
     		 
     		String string_vchty=null,string_query=null;
     		
     		ClosingBalanceSummaryForm closebalsum=(ClosingBalanceSummaryForm) form;
     		System.out.println("Get Page ID========12333333"+closebalsum.getPageId());
     		if(MenuNameReader.containsKeyScreen(closebalsum.getPageId())){
     			  
     			backOfficeDelegate=new BackOfficeDelegate();
     			commonBackOfficeInitParameters(req,backOfficeDelegate);
     			closebalsum.setSysdate(BackOfficeDelegate.getSysDate());
     			req.setAttribute("acctype",backOfficeDelegate.getMainModules(3));   			
     			req.setAttribute("pageId",MenuNameReader.getScreenProperties(closebalsum.getPageId()));
     			
     			
     			return map.findForward(ResultHelp.getSuccess());
     		}
     		else{
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
         		return map.findForward(ResultHelp.getError());
         	    } 
             } catch(Exception e){
         		path=MenuNameReader.getScreenProperties("0000");
         		setErrorPageElements(""+e,req,path);
         		logger.error(e);
         		e.printStackTrace();
         		return map.findForward(ResultHelp.getError());
          	}
     }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/ClosingBalanceSummary")){
   	try{
   		 
   		String modabbr=null,string_query=null;
   		ClosingBalObject[] closeBalObjSB=null,closeBalObjSH=null,closeBalObjLn=null,closeBalObjDep=null,closeBalObjLnD=null,closeBalObjPD=null;
   		ClosingBalanceSummaryForm closebalsum=(ClosingBalanceSummaryForm) form;
   		System.out.println("Get Page ID========12333333"+closebalsum.getPageId());
   		if(MenuNameReader.containsKeyScreen(closebalsum.getPageId())){
   			ModuleObject[] mod=backOfficeDelegate.getMainModules(3);  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			closebalsum.setSysdate(BackOfficeDelegate.getSysDate());
   			if(closebalsum.getAcctype()!=null){
   				for(int i=0;i<mod.length;i++){
   					if(closebalsum.getAcctype().equals(mod[i].getModuleCode())){
   						modabbr=mod[i].getModuleDesc().toUpperCase();
   					}
   					req.setAttribute("moddesc",modabbr);
   					req.setAttribute("vis","visible");
   				}
   			}		     	                  					
   					
   	  		if(closebalsum.getForward().equalsIgnoreCase("view")){
   				String string_date=closebalsum.getDate();
   				String string_modcode=closebalsum.getAcctype();
   				String string_cpymodcode=string_modcode.substring(1,4);
   				System.out.println("------^^^^^^^^modCode---------"+string_cpymodcode);
   				
   				   				
   				
   				
   				if(string_cpymodcode.equals("001")){
   					closeBalObjSH=backOfficeDelegate.getReportSHSum(string_modcode,string_date);	
   				if(closeBalObjSH!=null){
                      req.setAttribute("SHrep", closeBalObjSH);
   				}
   				else{
   						req.setAttribute("displaymsg","RecordsNotFound");
   					System.out.println("Records Not Found");
   				}
   				}
   				else if(string_cpymodcode.equals("002") || string_cpymodcode.equals("007") || string_cpymodcode.equals("018") || string_cpymodcode.equals("014")|| string_cpymodcode.equals("015"))
   				    {
   					closeBalObjSB=backOfficeDelegate.getReportSBSum(string_modcode,string_date);
   					if(closeBalObjSB!=null){
   					req.setAttribute("SBrep", closeBalObjSB);
   					}
   					else{
   							req.setAttribute("displaymsg","RecordsNotFound");
       					System.out.println("Records Not Found");
   				    }
   				    }
   				else if(string_cpymodcode.equals("003")||string_cpymodcode.equals("004")||string_cpymodcode.equals("005")){
   					closeBalObjDep=backOfficeDelegate.getReportTDSum(string_modcode,string_date);
   					if(closeBalObjDep!=null){
   					req.setAttribute("TDrep", closeBalObjDep);
   					}
   					else{
   							req.setAttribute("displaymsg","RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}			
   				else if(string_cpymodcode.equals("006")){
   					closeBalObjPD=backOfficeDelegate.getReportPDSum(string_modcode,string_date);
   					if(closeBalObjPD!=null){
   					req.setAttribute("PDrep", closeBalObjPD);
   					}
   					else{
   							req.setAttribute("displaymsg","RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}
   				else if(string_cpymodcode.equals("008")){
   					closeBalObjLnD=backOfficeDelegate.getReportLnDSum(string_modcode,string_date);
   					if(closeBalObjLnD!=null){
   					req.setAttribute("LnDrep", closeBalObjLnD);
   					}
   					else{
   							req.setAttribute("displaymsg","RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   				}
   				else if(string_cpymodcode.equals("010")){
   					closeBalObjLn=backOfficeDelegate.getReportLnSum(string_modcode,string_date);
   					if(closeBalObjLn!=null){
   					System.out.println("---action inside ln---$");
   					req.setAttribute("Lnrep", closeBalObjLn);
   					}
   					else{
   							req.setAttribute("displaymsg","RecordsNotFound");
       					System.out.println("Records Not Found");
   					}
   			}
   		}
   	  		
   	  	if(closebalsum.getForward().equalsIgnoreCase("save")){
   	  	 System.out.println("Inside save ");
		 //TO save to an excel Sheet
		 res.setContentType("application/.csv");
	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
	        	String string_date=closebalsum.getDate();
				String string_modcode=closebalsum.getAcctype();
				String string_cpymodcode=string_modcode.substring(1,4);
				System.out.println("------^^^^^^^^modCode---------"+string_cpymodcode);
	        java.io.PrintWriter out = res.getWriter();
	        out.print("\n");
	        out.print("\n");
	        out.print("\n");
	        out.print(",");out.print(",");out.print(",");
	        out.print("Closing Balance Summary Details ");
	        out.print("\n");
	        out.print("\n");
   		   /*HSSFCell cell = row.createCell((short)0);
   		   cell.setCellValue(1);*/
	        out.print("A/C No"); out.print(",");
   	   		out.print("C/B"); out.print("\n");
   		
   		
   		if(string_cpymodcode.equals("001")){
   			
   	   		
			closeBalObjSH=backOfficeDelegate.getReportSHSum(string_modcode,string_date);	
			if(closeBalObjSH!=null){
              req.setAttribute("SHrep", closeBalObjSH);
			}
			 for(int k=0;k<closeBalObjSH.length;k++){
      			  
        			out.print(closeBalObjSH[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjSH[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
			 }
			
			
			}
			else if(string_cpymodcode.equals("002") || string_cpymodcode.equals("007") || string_cpymodcode.equals("018") || string_cpymodcode.equals("014")|| string_cpymodcode.equals("015"))
			    {
				closeBalObjSB=backOfficeDelegate.getReportSBSum(string_modcode,string_date);
				if(closeBalObjSB!=null){
				req.setAttribute("SBrep", closeBalObjSB);
				}
				for(int k=0;k<closeBalObjSB.length;k++){
	      			  
        			out.print(closeBalObjSB[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjSB[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
				}
				
			    }
			else if(string_cpymodcode.equals("003")||string_cpymodcode.equals("004")||string_cpymodcode.equals("005")){
				closeBalObjDep=backOfficeDelegate.getReportTDSum(string_modcode,string_date);
				if(closeBalObjDep!=null){
				req.setAttribute("TDrep", closeBalObjDep);
				}
				if(closeBalObjDep.length!=0){
				for(int k=0;k<closeBalObjDep.length;k++){
	      			  
        			out.print(closeBalObjDep[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjDep[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
				}
				}
			}			
			else if(string_cpymodcode.equals("006")){
				closeBalObjPD=backOfficeDelegate.getReportPDSum(string_modcode,string_date);
				if(closeBalObjPD!=null){
				req.setAttribute("PDrep", closeBalObjPD);
				}
				for(int k=0;k<closeBalObjPD.length;k++){
	      			  
        			out.print(closeBalObjPD[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjPD[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
				}
			}
			else if(string_cpymodcode.equals("008")){
				closeBalObjLnD=backOfficeDelegate.getReportLnDSum(string_modcode,string_date);
				if(closeBalObjLnD!=null){
				req.setAttribute("LnDrep", closeBalObjLnD);
				}
				for(int k=0;k<closeBalObjLnD.length;k++){
	      			  
        			out.print(closeBalObjLnD[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjLnD[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
				}
				
			}
			else if(string_cpymodcode.equals("010")){
				closeBalObjLn=backOfficeDelegate.getReportLnSum(string_modcode,string_date);
				if(closeBalObjLn!=null){
				System.out.println("---action inside ln---$");
				req.setAttribute("Lnrep", closeBalObjLn);
				}
				for(int k=0;k<closeBalObjLn.length;k++){
	      			  
        			out.print(closeBalObjLn[k].getAcNo());
        			out.print(",");
        			out.print(closeBalObjLn[k].getClBal());
        			out.print(",");
        			out.print("\n"); 
				}
		}
   		req.setAttribute("msg","Saved to excel file in C:");
	    return null;	
   	  	}
   			
   			req.setAttribute("acctype",mod);
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(closebalsum.getPageId()));
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    }
   		
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   } else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIParametersMenu")){
   	try{
		 
 		String string_vchty=null,string_query=null;
 		
 		SIParametersForm siparam=(SIParametersForm) form;
 		System.out.println("Get Page ID========12333333"+siparam.getPageId());
 		if(MenuNameReader.containsKeyScreen(siparam.getPageId())){
 			  
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			setSIParameters(backOfficeDelegate, req);
 			
 			
 			
 			req.setAttribute("fromacctype",backOfficeDelegate.getMainModules(8));   
 			req.setAttribute("toacctype",backOfficeDelegate.getMainModules(9));
 			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siparam.getPageId()));
 			
 			
 			return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIParameters")){
     	try{
  		 
   		String string_vchty=null,string_query=null;
   		
   		SIParametersForm siparam=(SIParametersForm) form;
   		System.out.println("Get Page ID========12333333"+siparam.getPageId());
   		if(MenuNameReader.containsKeyScreen(siparam.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			setSIParameters(backOfficeDelegate, req);
   			String method=req.getParameter("method");
				String use_id= req.getParameter("id");
   			req.setAttribute("id", req.getParameter("id"));
				req.setAttribute("method", req.getParameter("method"));
			    List parameter_list=(ArrayList)session.getAttribute("Details");
				
				
				if(method!=null ){
					
					if(method.equalsIgnoreCase("Update")){
						/*AdminObject adminObject= new AdminObject();
						ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
						for(int i=0;i<parameter_list.size();i++){
							Map m=(TreeMap)parameter_list.get(i);
							System.out.println("size----"+parameter_list.get(i));
							String id=m.get("id").toString();
							System.out.println("id---->"+id);
							System.out.println("use_id--->"+use_id);
							if(use_id!=null){
								if(use_id.equals(id)){
									System.out.println("id=="+(i+1));
									System.out.println("-----inside save----");
									m.put("id",req.getParameter("id"));
									m.put("prioritynum",req.getParameter("Priority_No"));
								    m.put("frm_acc_type",req.getParameter("From_Type"));
								    m.put("fromDescription",req.getParameter("fromdescrip"));
								    m.put("to_acc_type",req.getParameter("To_Type"));
								    m.put("toDescription",req.getParameter("todescrip"));
								    parameter_list.set(i,m);
								    
								      String from_acc_type=req.getParameter("From_Type");
								      System.out.println("from account type:"+from_acc_type);
								      String to_acc_type=req.getParameter("To_Type");
								      System.out.println("to account type:"+to_acc_type);
								      System.out.println("-------above update----------");
								      int pri_no=Integer.parseInt(req.getParameter("Priority_No").toString());
								      adminObject.setPriorityNo(pri_no);
								      for(int j=0;j<mod.length;j++){
								    	  if(from_acc_type.equalsIgnoreCase(mod[j].getModuleAbbrv())){
								      adminObject.setFromType(mod[j].getModuleCode());
								      }
								    	  if(to_acc_type.equalsIgnoreCase(mod[j].getModuleAbbrv())){ 
								           adminObject.setToType(mod[j].getModuleCode());
								      }
								      }
								      adminObject.obj_userverifier.setUserTml(tml);
									  adminObject.obj_userverifier.setUserId(user);
									  
									  System.out.println("--------inside update-----------");
									  backOfficeDelegate.update(adminObject, pri_no,"","");
									  System.out.println("--------end of save-----------");				
									  break;
								}
							}
						}*/
						
						//siparam.setPrior_num(req.getParameter("Priority_No"));
                     System.out.println("------inside update-----");
                     String id=null;
                     use_id=req.getParameter("id");
                     System.out.println("use__id**"+use_id);
 				    for(int j=0;j<parameter_list.size();j++){
 						Map m=(TreeMap)parameter_list.get(j);
 						System.out.println("size----"+parameter_list.get(j));
 				       	System.out.println("The checked value is ");
                         id=m.get("id").toString();
 				   			    	
 				    if(use_id!=null){
 				    	System.out.println("id---->"+id);
 						if(use_id.equals(id)){
 					//System.out.println("---------------------->"+req.getParameter("Priority_No"));
 							AdminObject adminObject= new AdminObject();
 							
 						//String  pri_no=req.getParameter("Priority_No");
 							String  pri_no=m.get("prioritynum").toString();
 								
 						 System.out.println("prino****"+pri_no);
 					     req.setAttribute("pri_no",pri_no );
 						
 						}
 				    }
 				    
 				    }
	      				req.setAttribute("vis","visible");
	      				req.setAttribute("Disable", true);
	      				req.setAttribute("update_insert","SUBMIT");
					}
	      				
	    					
					
                
				else if(method.equalsIgnoreCase("Add")){
   				
					req.setAttribute("vis","visible");
	      			req.setAttribute("Disable", false);
	      			req.setAttribute("update_insert","INSERT");
					
   				
   			}	
				else if(method.equalsIgnoreCase("Edit")){
				
				}
					else if(method.equalsIgnoreCase("Reset")){
					parameter_list=setSIParameters(backOfficeDelegate, req);
				}
 				
				}	
										
				session.setAttribute("Details",parameter_list);
				req.setAttribute("Details",parameter_list);
				req.setAttribute("pageId",path);
   			
   			//submitting values
				if(siparam.getForward()!=null){
					
	      			if(siparam.getForward().equalsIgnoreCase("INSERT")){
				    Map m=new TreeMap();
				    AdminObject adminObject= new AdminObject();
				    ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
   				System.out.println("column 1:----"+parameter_list.get(0));
					//Map map_prinum=(TreeMap)parameter_list.get(0);
					//Random generator=new Random();
					String id=""+(parameter_list.size()+1);
					System.out.println("ID--------"+id);
					m.put("id",id);
					m.put("prioritynum",siparam.getPrior_num());
					System.out.println("fromaccc***"+siparam.getFromacc());
					   for(int i=0;i<mod.length;i++){
							if(mod[i].getModuleCode().equals(siparam.getFromacc())){
								m.put("frm_acc_type",mod[i].getModuleAbbrv());
								m.put("fromDescription",mod[i].getModuleDesc());
							}
					   }
					
				    
				    System.out.println("toaccc***"+siparam.getToacc());
				    for(int i=0;i<mod.length;i++){
						if(mod[i].getModuleCode().equals(siparam.getToacc())){
							m.put("to_acc_type",mod[i].getModuleAbbrv());
							m.put("toDescription",mod[i].getModuleDesc());
						}
				   }  
				   parameter_list.add(m);
				   
				      System.out.println("------above setting values--------");
				      adminObject.setPriorityNo(Integer.parseInt(siparam.getPrior_num()));
				      adminObject.setFromType(siparam.getFromacc());
				      adminObject.setToType(siparam.getToacc());
				      adminObject.obj_userverifier.setUserTml(tml);
					  adminObject.obj_userverifier.setUserId(user);
						
				      System.out.println("------inside setting values--------");
				      int insert_val= backOfficeDelegate.insertDetails(adminObject); 
				   
	      			}
	      			
	      			
	      			
				if(siparam.getForward().equalsIgnoreCase("SUBMIT")){
					AdminObject adminObject= new AdminObject();
				    ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
   				System.out.println("column 1:----"+parameter_list.get(0));
					String frommodcode="",tomodcode="";
					
				    String from_acc_type=siparam.getFromacc();
				    String to_acc_type=siparam.getToacc();
				    int pri_no=Integer.parseInt(siparam.getPrior_num());
				    /*for(int j=0;j<parameter_list.size();j++){
						Map m=(TreeMap)parameter_list.get(j);
						System.out.println("size----"+parameter_list.get(j));
   				
			    		String id=m.get("id").toString();
				    	int pri_no=Integer.parseInt(m.get("prioritynum").toString()); 
				    	System.out.println("id---->"+id);
					    System.out.println("use_id--->"+use_id);
   				     
					    
				   // if(String use_id1!=null){
				    	 String use_id1=req.getParameter("Priority_No");
				    	 System.out.println("The id from the GUI is------------------------->"+use_id1);
				    	 System.out.println("The id from the map is------------------------->"+id);
				    	 
				    	 if(use_id1.equals(id)){
					       System.out.println("pri_no---->"+req.getParameter("Priority_No"));
				           //siparam.setPrior_num(req.getParameter("Priority_No"));		
					         m.put("id",id);
				
					         m.put("prioritynum", pri_no);
					        //System.out.println("fromaccc------>"+from_acc_type);
					         for(int i=0;i<mod.length;i++){
					        	 if(mod[i].getModuleAbbrv().equals(from_acc_type)){
					        		 frommodcode=mod[i].getModuleCode();
					        		 m.put("frm_acc_type",frommodcode);
					        		 m.put("fromDescription",mod[i].getModuleDesc());
							}
					   }
					
				    
				             //System.out.println("toaccc----->"+to_acc_type);
					         for(int i=0;i<mod.length;i++){
					        	 if(mod[i].getModuleAbbrv().equals(to_acc_type)){
					        		 tomodcode=mod[i].getModuleCode();
					        		 m.put("to_acc_type",tomodcode);
					        		 m.put("toDescription",mod[i].getModuleDesc());
					        	 }
					         }
						}
				    
   				
		    		   parameter_list.add(m);
   				
   		*/			
   				
					
				   
				      System.out.println("------above setting values--------");
				      System.out.println("priority==="+pri_no);
				      System.out.println("frommodcode==="+from_acc_type);
				      System.out.println("tomodcode==="+to_acc_type);
				      adminObject.setPriorityNo(pri_no);
				      adminObject.setFromType(from_acc_type);
				      adminObject.setToType(to_acc_type);
				      adminObject.obj_userverifier.setUserTml(tml);
					  adminObject.obj_userverifier.setUserId(user);
					  
					  backOfficeDelegate.update(adminObject,pri_no, "",""); 
						
				
				
					 
					   }
				
				
				else if(siparam.getForward().equalsIgnoreCase("Delete")){
					AdminObject adminObject= new AdminObject();
				    ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
					String[] frommodcode=new String[parameter_list.size()];
					String[] tomodcode=new String[parameter_list.size()];
					int[] priorityno=new int[parameter_list.size()];
					use_id=req.getParameter("id");
					System.out.println("use_id==="+use_id);
					
					//System.out.println("prino------>"+pri_no);
				    
				   // System.out.println("fromaccc------>"+from_acc_type);
				  //  System.out.println("toaccc------>"+to_acc_type);
				    
					for(int i=0;i<parameter_list.size();i++){
						Map m=(TreeMap)parameter_list.get(i);
						String id=m.get("id").toString();
						String pri_no=m.get("prioritynum").toString();
						String from_acc_type=m.get("frm_acc_type").toString();
					    String to_acc_type=m.get("to_acc_type").toString();
						
					    if(use_id!=null){
						if(use_id.equals(id)){
							 
							
						
					
						priorityno[i]=Integer.parseInt(pri_no);			
				     
							
						    
						    
					   for(int j=0;j<mod.length;j++){
							if(mod[j].getModuleAbbrv().equals(from_acc_type))
								frommodcode[i]=mod[j].getModuleCode();
								
					   
					   }
					   
					   
					    for(int k=0;k<mod.length;k++){
						
					    	if(mod[i].getModuleAbbrv().equals(to_acc_type))
								tomodcode[i]=mod[k].getModuleCode();
							}
					  
					    parameter_list.remove(m); 
						}
					    }
					    
					}
					
					backOfficeDelegate.delete(priorityno, frommodcode, tomodcode);
					    	
					      System.out.println("------end of deleting values--------");
					      
					     
					   
					
					
					
					
				}
			
				
				}
				
				//}
			
				
				
				
				
				
   			
   			req.setAttribute("fromacctype",backOfficeDelegate.getMainModules(8));   
   			req.setAttribute("toacctype",backOfficeDelegate.getMainModules(9));
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siparam.getPageId()));
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
   
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIExecutionMenu")){
     	try{
  		 
   		String string_vchty=null,string_query=null;
   		
   		SIExecutionForm siexec=(SIExecutionForm) form;
   		System.out.println("Get Page ID========12333333"+siexec.getPageId());
   		if(MenuNameReader.containsKeyScreen(siexec.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			//setSIExecutionDetails(backOfficeDelegate, req);
   			System.out.println("Hiiiiiiiiii si_no is----------");
   		   int[] si_no=backOfficeDelegate.getInstnsForExec(backOfficeDelegate.getSysDate());
   		   if(si_no==null)
   		   {
   			   req.setAttribute("displaymsg","There are no standing instructions to be executed"); 
   		   }
   			if(si_no!=null&&si_no.length>0){
 				System.out.println("Before calling getStdInstRecords "+si_no);
 			    setSIExecutionDetails(backOfficeDelegate, req);
   			}
   		
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siexec.getPageId()));
   			
   			
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/SIExecution")){
   	try{
		 
 		String string_vchty=null,string_query=null;
 		
 		SIExecutionForm siexec=(SIExecutionForm) form;
 		System.out.println("Get Page ID========12333333"+siexec.getPageId());
 		if(MenuNameReader.containsKeyScreen(siexec.getPageId())){
 			  
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			
 			if(siexec.getForward().equalsIgnoreCase("EXECUTE")){
 			System.out.println("********************************");	
 			setSIExecutionDoneDetails(backOfficeDelegate, req);
 			int[] si_no=backOfficeDelegate.getInstnsForExec(backOfficeDelegate.getSysDate());
   			if(si_no!=null&&si_no.length>0){
 				System.out.println("Before calling getStdInstRecords "+si_no);
 			    setSIExecutionDetails(backOfficeDelegate, req);
   			}
 			}
 			
 			req.setAttribute("pageId",MenuNameReader.getScreenProperties(siexec.getPageId()));
 			
 			
 			return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
  
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/PaymentVoucherMenu")){
   	try{
		 
 		String string_vchty=null,string_query=null;
 		
 		PaymentVoucherForm paymentvch=(PaymentVoucherForm) form;
 		paymentvch.setPaym_vch(0);
 		paymentvch.setTotalcreditamt(0.0);
 		paymentvch.setTotalcreditamt(0.0);
 		
 		 System.out.println("###########glcode"+paymentvch.getGlcode()+"Get Page ID========12333333"+paymentvch.getPageId());
 		if(MenuNameReader.containsKeyScreen(paymentvch.getPageId())){
 			  
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			
 			ModuleObject[] mod=backOfficeDelegate.getMainModules(10);
 			req.setAttribute("gltype", mod);
 			
 			Hashtable hash_glcode=backOfficeDelegate.getGLCodes(BackOfficeDelegate.getSysDate());
 			
 			Vector vec_gl_code=null;
 			vec_gl_code = (Vector)(hash_glcode.get("ALL"));
 			req.setAttribute("glcode",vec_gl_code);
 			
 	        req.setAttribute("vchno",paymentvch.getVchnum());		
   			
 			Hashtable hash_gldesc=backOfficeDelegate.getGLCodesDesc(BackOfficeDelegate.getSysDate());
 			req.setAttribute("gldesc",hash_gldesc.get(vec_gl_code.get(0))); 
 			CashDelegate msDelegate=new CashDelegate();
 			req.setAttribute("glnmcd", msDelegate.getGlCodeNames());
				String glname[][]=msDelegate.getGlCodeNames();
				
             
 			
 			    req.setAttribute("credit","");
					req.setAttribute("debit",""); 
					
 			    
					req.setAttribute("pageId",MenuNameReader.getScreenProperties(paymentvch.getPageId()));
 			    
					req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                 req.setAttribute("TabNum","0");	
                 setTabbedPaneInitParamsforPaymentVoucher(req, path, paymentvch);
 			
 			return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/PaymentVoucher")){
     	try{
  		 
   		String string_vchty=null,string_query=null;
   		
   		PaymentVoucherForm paymentvch=(PaymentVoucherForm) form;
   		paymentvch.setPaym_vch(0);
   		paymentvch.setTotalcreditamt(0.0);
 		paymentvch.setTotaldebitamt(0.0);

	        req.setAttribute("vchno",paymentvch.getVchnum());
   	   	req.setAttribute("displaymsg","");
   		System.out.println("Get Page ID========12333333"+paymentvch.getPageId());
   		if(MenuNameReader.containsKeyScreen(paymentvch.getPageId())){
   			  
   			backOfficeDelegate=new BackOfficeDelegate();
   			CashDelegate msDelegate = new CashDelegate();
   			commonBackOfficeInitParameters(req,backOfficeDelegate);
   			
   			
   			int vchno= paymentvch.getVchnum();
   			System.out.println("Vchno is----------"+vchno);
   		
   			ModuleObject[] mod=backOfficeDelegate.getMainModules(10);
   			req.setAttribute("gltype", mod);
 			req.setAttribute("glnmcd", msDelegate.getGlCodeNames());
 			Hashtable hash_glcode=backOfficeDelegate.getGLCodes(backOfficeDelegate.getSysDate());
 			
 			Vector vec_gl_code=null;
 			vec_gl_code = (Vector)(hash_glcode.get("ALL"));
 			req.setAttribute("glcode",vec_gl_code);
 			
 			Hashtable hash_gldesc=backOfficeDelegate.getGLCodesDesc(backOfficeDelegate.getSysDate());
 			Vector vec_gl_desc=null;
 			if(hash_gldesc.containsKey(paymentvch.getGlcode())) {
 			req.setAttribute("gldesc",(hash_gldesc.get(paymentvch.getGlcode())));
 				
 			} else {
 			 	req.setAttribute("gldesc",hash_gldesc.get(vec_gl_code.get(0)));
 			} 
 			
 			req.setAttribute("credit",paymentvch.getCreditamt());
				req.setAttribute("debit", ""); 
			    
 			
 			double credit_amt=0.0;
				double debit_amt=0.0;
				VoucherDataObject[] focback=backOfficeDelegate.VoucherData("P",paymentvch.getVchnum());
				req.setAttribute("vchdataobject",focback);
                     			
				String method=req.getParameter("method");
				String use_id= req.getParameter("id");
   			req.setAttribute("id", req.getParameter("id"));
				req.setAttribute("method", req.getParameter("method"));
				setPaymentVoucherDetails(backOfficeDelegate,req);
				//List paymentvoucherlist=(ArrayList)session.getAttribute("PaymentVoucherDetails");
				Double creditamt=paymentvch.getCreditamt();
				Double debitamt=paymentvch.getDebitamt();
				
				
				if(paymentvch.getVchnum()!=0){
         	    if(focback==null){
         	    System.out.println("Invalid Scroll");	
         	    	req.setAttribute("displaymsg","Invalid Voucher Number");
         	    System.out.println("Scr-----");
         	    }
         	
         	    else{
 	            for(int i=0;i<focback.length;i++)
 	                {
 	            	
 	            	req.setAttribute("focback", focback);
 	            	
 	      			System.out.println("Length of focback======="+focback.length);
         	    	 paymentvch.setDate(focback[0].getTransactionDate());
         	    	 System.out.println("credit-----------"+(focback[focback.length-1].getTransactionAmount()));
 	            	 paymentvch.setTotalcreditamt(focback[focback.length-1].getTransactionAmount());
 	            	 System.out.println("debit-----------"+(focback[focback.length-1].getTransactionAmount()));
 	            	 paymentvch.setTotaldebitamt(Double.valueOf(focback[focback.length-1].getTransactionAmount()));
 		     
         	    	 req.setAttribute("credit",focback[focback.length-1].getTransactionAmount());
  					 req.setAttribute("debit", focback[focback.length-1].getTransactionAmount()); 
         	    	 
 	            	// paymentvch.setNarration(focback[0].getNarration());*/
 	            	 
 	            
 		      
 	                }
         	    }
				}
				
				if(method!=null){
				if(method.equalsIgnoreCase("delete")){
						List paymentvoucherlist=(ArrayList)session.getAttribute("PaymentVoucherDetails");
						for(int i=0;i<paymentvoucherlist.size();i++){
							Map m=(TreeMap)paymentvoucherlist.get(i);
							String id=m.get("id").toString();
							if(use_id.equals(id)){
								paymentvoucherlist.remove(m);
							}
						}
						
						
					}
					
				}
				
				
				if(paymentvch.getForward()!=null){
					//System.out.println("size*****"+transfervoucherlist.size());
					System.out.println("*******inside forward********");
					System.out.println("HIiiiiiiii Shreyaaaaaaaaaaaa2222");
					System.out.println("forward********"+paymentvch.getForward());
					System.out.println("HIiiiiiiii Shreyaaaaaaaaaaaa222233333333");
					
				if(paymentvch.getForward().equalsIgnoreCase("SUBMIT")){
					System.out.println("sssssssssssssssssssss");
					System.out.println("**********inside submit*******");
					
					
					System.out.println("sssssssssssssssssssss");
					System.out.println("**********inside submit*******");
					
					
					  double totsum=0.0,tamt;
					    
			            String[] cbox = req.getParameterValues("cbox");
			            String[] gltype=req.getParameterValues("gltype");
			            String[] glcode=req.getParameterValues("glcode");
						String[] glamount=(req.getParameterValues("amount"));
				        VoucherDataObject[] vchdataObj = null;
				        if(cbox!=null){
				        vchdataObj = new VoucherDataObject[cbox.length+1];
				         for(int k=0;k<cbox.length;k++){
								int x=Integer.parseInt(cbox[k].trim());	
								 totsum+=Double.parseDouble(glamount[x]);
								 System.out.println("Total  amount is-------->"+totsum);
								}
				         req.setAttribute("sum",totsum);
						 System.out.println("-----inside inserting data------");
						 Double totalcredit=paymentvch.getCreditamt();
						if(totalcredit!=totsum)
								   {
									  req.setAttribute("msg","Debit And Credit Don't Match" );
									   System.out.println("total credit"+totalcredit);
									   System.out.println("Debit Credit Don't Match");
									 
								   }
								  else
								  {
									  	
									  for(int k=0;k<cbox.length;k++)
										 {
												int x=Integer.parseInt(cbox[k].trim());
												 vchdataObj[k]=new VoucherDataObject();								                	
												 
												vchdataObj[k].setGlType(gltype[x]);
												vchdataObj[k].setGlCode(Integer.parseInt(glcode[x]));
												vchdataObj[k].setModuleAccountType("0");
												vchdataObj[k].setModuleAccountNo(0);
					                	 
												vchdataObj[k].setCdIndicator("D");
												vchdataObj[k].setVoucherType("P");
												System.out.println("Inside for lopp");
												vchdataObj[k].setTransactionAmount(Double.parseDouble(glamount[x]));
												vchdataObj[k].setNarration(paymentvch.getNarration());
												vchdataObj[k].setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
												vchdataObj[k].obj_userverifier.setUserTml("SHIP");
												
												vchdataObj[k].obj_userverifier.setUserId("CA99");
												
												vchdataObj[k].obj_userverifier.setUserDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
					                              
										    
												System.out.println("Again In Loop-----");
					            
					                }
									  
									  
									  
									  
							             	  String[] cash_gl = backOfficeDelegate.getCashGLCode();
							             	 System.out.println("Voucher object"+vchdataObj);
							             	 int j=cbox.length;
							             	 vchdataObj[j]=new VoucherDataObject();
							             	 System.out.println("GLType :"+cash_gl[0]);
										     vchdataObj[j].setGlType(cash_gl[0]);
										     System.out.println("GLCode :"+Integer.parseInt(cash_gl[1]));
								             vchdataObj[j].setGlCode(Integer.parseInt(cash_gl[1]));
								             System.out.println("Transaction Amt :"+creditamt);
							                 vchdataObj[j].setTransactionAmount(creditamt);
							                 vchdataObj[j].setCdIndicator("C");
							                 vchdataObj[j].setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
							                 System.out.println("Voucher Number"+paymentvch.getVchnum());
							                 vchdataObj[j].setVoucherNo(paymentvch.getVchnum());
							                 System.out.println("Narration"+paymentvch.getNarration());
										     vchdataObj[j].setNarration(paymentvch.getNarration());
										     vchdataObj[j].setModuleAccountType("1002001");
						                	 vchdataObj[j].setModuleAccountNo(0);
											 vchdataObj[j].setVoucherType("P");
											 vchdataObj[j].obj_userverifier.setUserTml("SHIP");
											 vchdataObj[j].obj_userverifier.setUserId("CA99");
											 vchdataObj[j].obj_userverifier.setUserDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
					                              
								int vch_no= backOfficeDelegate.storeTransferVoucherData(vchdataObj);
								System.out.println("Voucher Number:******"+vch_no);
							     //String voucher=Integer.parseInt(arg0) 
								//if(paymentvch.getVouchervalue().equalsIgnoreCase("true")){
		                			System.out.println("Generated Voucher Number**********:"+vch_no);
		                		
		                			
		                			if(vch_no!=0)
		                			{
		                				paymentvch.setPaym_vch(vch_no);
		                				req.setAttribute("displaymsg", "Note Down Voucher Number :"+vch_no);
		                				System.out.println("voucher value in payment----"+paymentvch.getPaym_vch());
		                			}
		                            System.out.println("=====Succesfully Inserted!!!!!!!!!======="); 
		                          
							              
								  }	}
				        else 
				        {
				        	req.setAttribute("msg", "Please select the Rows to be inserted");
				        }
									  
									  
									  
							             
			
					
				}
			
					
				else if(paymentvch.getForward().equalsIgnoreCase("UPDATE")){
					
						System.out.println("sssssssssssssssssssss");
						System.out.println("**********inside update*******");
						
						
						System.out.println("sssssssssssssssssssss");
						System.out.println("**********inside update*******");
						
						
						  double totsum=0.0,tamt;
						    
				            String[] cbox = req.getParameterValues("cbox");
				           
				            String[] gltype=req.getParameterValues("gltype");
				            String[] glcode=req.getParameterValues("glcode");
							
				             String[] glamount=(req.getParameterValues("amount"));
					        	
					            VoucherDataObject[] vchdataObj = null;
					            vchdataObj = new VoucherDataObject[cbox.length+1];
					         for(int k=0;k<cbox.length;k++){
									int x=Integer.parseInt(cbox[k].trim());

	  								 totsum+=Double.parseDouble(glamount[x]);
		                             System.out.println("Total  amount is-------->"+totsum);
									
									}
					         req.setAttribute("sum",totsum);
						
								   System.out.println("-----inside inserting data------");
									
									  Double totalcredit=paymentvch.getCreditamt();
									  if(totalcredit!=totsum)
									   {
										   req.setAttribute("msg","Debit And Credit Don't Match" );
										   System.out.println("total credit"+totalcredit);
										   System.out.println("Debit Credit Don't Match");
										 
									   }
									  else
									  {
										  	
										  for(int k=0;k<cbox.length;k++)
											 {
													int x=Integer.parseInt(cbox[k].trim());
													 vchdataObj[k]=new VoucherDataObject();								                	
													 
													vchdataObj[k].setGlType(gltype[x]);
													vchdataObj[k].setGlCode(Integer.parseInt(glcode[x]));
													vchdataObj[k].setModuleAccountType("0");
													vchdataObj[k].setModuleAccountNo(0);
						                	 
													vchdataObj[k].setCdIndicator("D");
													vchdataObj[k].setVoucherType("P");
													System.out.println("Inside for lopp");
													vchdataObj[k].setTransactionAmount(Double.parseDouble(glamount[x]));
													vchdataObj[k].setNarration(paymentvch.getNarration());
													vchdataObj[k].setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
													vchdataObj[k].obj_userverifier.setUserTml("SHIP");
													
													vchdataObj[k].obj_userverifier.setUserId("CA99");
													
													vchdataObj[k].obj_userverifier.setUserDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
						                              
											    
													System.out.println("Again In Loop-----");
						            
						                }
										  
										  
										  
										  
								             	  String[] cash_gl = backOfficeDelegate.getCashGLCode();
								             	 System.out.println("Voucher object"+vchdataObj);
								             	 int j=cbox.length;
								             	 vchdataObj[j]=new VoucherDataObject();
								             	 System.out.println("GLType :"+cash_gl[0]);
											     vchdataObj[j].setGlType(cash_gl[0]);
											     System.out.println("GLCode :"+Integer.parseInt(cash_gl[1]));
									             vchdataObj[j].setGlCode(Integer.parseInt(cash_gl[1]));
									             System.out.println("Transaction Amt :"+creditamt);
								                 vchdataObj[j].setTransactionAmount(creditamt);
								                 vchdataObj[j].setCdIndicator("C");
								                 vchdataObj[j].setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
								                 System.out.println("Voucher Number"+paymentvch.getVchnum());
								                 vchdataObj[j].setVoucherNo(paymentvch.getVchnum());
								                 System.out.println("Narration"+paymentvch.getNarration());
											     vchdataObj[j].setNarration(paymentvch.getNarration());
											     vchdataObj[j].setModuleAccountType("1002001");
							                	 vchdataObj[j].setModuleAccountNo(0);
												 vchdataObj[j].setVoucherType("P");
												 vchdataObj[j].obj_userverifier.setUserTml("SHIP");
												 vchdataObj[j].obj_userverifier.setUserId("CA99");
								
												 vchdataObj[j].obj_userverifier.setUserDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
												 vchdataObj[j].setVoucherNo(paymentvch.getVchnum());     
													int vch_no = backOfficeDelegate.updateTransferVoucher(vchdataObj);
											           System.out.println("Updated VoucherNo:"+vch_no );
									  }	
										  
										  
										  
								             
				
						
					
						
						/*String[] cash_gl=backOfficeDelegate.getCashGLCode();
						
						VoucherDataObject[] vchdataObj = new VoucherDataObject[1];
					    if(paymentvch.getVchnum()!=0){
							
							System.out.println("-----inside updating data------");
							vchdataObj[0]=new VoucherDataObject();
						    
							
							vchdataObj[0].setGlType(cash_gl[0]);
							
							vchdataObj[0].setGlCode(Integer.parseInt(cash_gl[1])); 
						    
													    
						    vchdataObj[0].setTransactionAmount(debitamt);
						    
						    vchdataObj[0].setCdIndicator("C");
						    
						    vchdataObj[0].setTransactionDate(paymentvch.getDate());
							
							vchdataObj[0].setVoucherNo(paymentvch.getVchnum());
							
							vchdataObj[0].setNarration(paymentvch.getNarration());
							
							vchdataObj[0].setVoucherType("P");
							
							vchdataObj[0].obj_userverifier.setUserTml("503");
							
							vchdataObj[0].obj_userverifier.setUserId("CA99");
							
							vchdataObj[0].obj_userverifier.setUserDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
                           
					    
							
						}*/
					}
					
				else if(paymentvch.getForward().equalsIgnoreCase("DELETE")){
					System.out.println("Are you going to delete ");
					   focback=backOfficeDelegate.VoucherData("P",paymentvch.getVchnum());
					   
						if(paymentvch.getVchnum()!=0){
							if(focback!=null){
					           int del_vchno=backOfficeDelegate.deleteTransferDataVoucher(paymentvch.getVchnum(),"P",backOfficeDelegate.getSysDate() );
					           System.out.println("Deleted VoucherNo:"+del_vchno);
					           	req.setAttribute("displaymsg","Voucher Number Deleted Sucessfully!!!!");
							}
							else
							{
								req.setAttribute("displaymsg","Please Check !!!Voucher number is already verified or deleted" );
								System.out.println("Voucher No IS Deleted!!!!!!!");								
							}
					}
					
				}
				else if(paymentvch.getForward().equalsIgnoreCase("VERIFY")){
						         
					      System.out.println("Payment voucher number---"+paymentvch.getVchnum());
					      focback=backOfficeDelegate.VoucherData("P",paymentvch.getVchnum());
							System.out.println("******Inside Verify12345678**********");
							
							System.out.println("Focback value---------"+focback);
							if(focback!=null)
							{
								VoucherDataObject voucherdata=new VoucherDataObject();
								
								voucherdata.obj_userverifier.setVerId("1044");
    	                    
		    	             voucherdata.obj_userverifier.setVerTml("CA99");
                
		    	             voucherdata.obj_userverifier.setVerDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
			    
		    	             voucherdata.setVoucherNo(paymentvch.getVchnum());
		    	             String date=backOfficeDelegate.getSysDate();
		    	             System.out.println("date************"+date);
		    	             voucherdata.setTransactionDate(date);
		    	             boolean verify_vch=backOfficeDelegate.verifyFadataVoucher(voucherdata);
				
		    	              System.out.println("^^^^^^^^^^Successfully Verified^^^^^^^^^");
		    	              req.setAttribute("displaymsg","Voucher Number Verified Sucessfully");
								
							}
												
							else{
								System.out.println("Are U Here!!!!!!!!");
								
								req.setAttribute("displaymsg","Please Check!!!! Voucher number is alerady verified or deleted ");
								
					}
							
				}			
				}
				
				
   			req.setAttribute("pageId",MenuNameReader.getScreenProperties(paymentvch.getPageId()));
   			req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
             req.setAttribute("TabNum","0");	
             setTabbedPaneInitParamsforPaymentVoucher(req, path, paymentvch);
			
   			
   			return map.findForward(ResultHelp.getSuccess());
   		}
   		else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
   }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/OdccbalMenu")){
   	try{
		 
 		String string_vchty=null,string_query=null;
 		
 		OdccbalForm odccform=(OdccbalForm) form;
 		System.out.println("Get Page ID========12333333"+odccform.getPageId());
 		if(MenuNameReader.containsKeyScreen(odccform.getPageId())){
 			  
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			
 			req.setAttribute("accType",backOfficeDelegate.getMainModules(11));
 			
 			
 			odccform.setValid("");
 			req.setAttribute("pageId",MenuNameReader.getScreenProperties(odccform.getPageId()));
 			
 			
 			return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/Odccbal")){
   	try{
		 
 		String string_vchty=null,string_query=null;
 		
 		OdccbalForm odccform=(OdccbalForm) form;
 		System.out.println("Get Page ID========12333333"+odccform.getPageId());
 		if(MenuNameReader.containsKeyScreen(odccform.getPageId())){
 			  
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			
 			 if(odccform.getForward()!=null){
 				 System.out.println("Forward--->"+odccform.getForward());
 			if(odccform.getForward().equalsIgnoreCase("VIEW")){
 				String date=backOfficeDelegate.getSysDate();
 				System.out.println("date.......>"+date);
 				System.out.println("Acctype.......>"+odccform.getAccountType());
 				OdccObject[] odccObj=backOfficeDelegate.getOdccMaster(0,date,Integer.parseInt(odccform.getAccountType()),string_query );
 				//if(odccObj!=null){
 				System.out.println("odccdetails>>>>>>>>>>>"+odccObj);
 				req.setAttribute("odcc", odccObj);
 				    				//}
 				//else{
 					//odccform.setValid("RecordsNotfound");
 				//}
 			}
 			
 			
 			 }
 			 
 			if(odccform.getForward().equalsIgnoreCase("save")){
 				System.out.println("Inside save ");
       		 //TO save to an excel Sheet
       		 res.setContentType("application/.csv");
       	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
       	        
       	        java.io.PrintWriter out = res.getWriter();
       	        out.print("\n");
       	        out.print("\n");
       	        out.print("\n");
       	        out.print(",");out.print(",");out.print(",");
       	        out.print("ODCC Balance Details: ");
       	        out.print("\n");
       	        out.print("\n");
          		   /*HSSFCell cell = row.createCell((short)0);
          		   cell.setCellValue(1);*/
       	        out.print("A/c Type"); out.print(",");
        		out.print("A/c No"); out.print(",");
        		out.print("Name"); out.print(",");
        		out.print("Balance"); out.print(",");
        		out.print("Date of A/c Opened"); out.print(",");
        		out.print("Credit Limit"); out.print("\n");
        		String date=backOfficeDelegate.getSysDate();
 				System.out.println("date.......>"+date);
 				System.out.println("Acctype.......>"+odccform.getAccountType());
 				OdccObject[] odccObj=backOfficeDelegate.getOdccMaster(0,date,Integer.parseInt(odccform.getAccountType()),string_query );
 				System.out.println("odccdetails>>>>>>>>>>>"+odccObj);
 				req.setAttribute("odcc", odccObj);
 			
        		 for(int k=0;k<odccObj.length;k++){
          			  
            			out.print(odccObj[k].getAccountType());
            			out.print(",");
            			out.print(odccObj[k].getAccountNo());
            			out.print(",");
            			out.print(odccObj[k].getAccountName());
            			out.print(",");
            			out.print(odccObj[k].getAccountBalance());
            			out.print(",");
            			out.print(odccObj[k].getAccountOpenDate());
            			out.print(",");
            			out.print(odccObj[k].getCreditLimit());
            			out.print(",");
            			out.print("\n");   
            			
            			
        		 }
        		 req.setAttribute("msg","Saved to excel file in C:");
     		    return null;
 				
 			}
 			req.setAttribute("accType",backOfficeDelegate.getMainModules(11));
 			
 			req.setAttribute("pageId",MenuNameReader.getScreenProperties(odccform.getPageId()));
 			   			
 			return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/TransferVoucherMenu")){
   	try{
		 
 		String modcode=null,string_query=null;
 		
 		TransferVoucherForm transfervchform=(TransferVoucherForm) form;
 		transfervchform.setSysdate(backOfficeDelegate.getSysDate());
 		if(MenuNameReader.containsKeyScreen(transfervchform.getPageId())){
 			 System.out.println("At 3552 -------->>>"+transfervchform.getVoucherno()); 
 			backOfficeDelegate=new BackOfficeDelegate();
 			commonBackOfficeInitParameters(req,backOfficeDelegate);
 			 req.setAttribute("vchno",transfervchform.getVchnum());	
 				req.setAttribute("displaymsg","");
 			transfervchform.setNewscrverify("");
 			transfervchform.setVchnum(0);      //added by parul//
 			transfervchform.setNarration("");
 			transfervchform.setGltype("");
 			transfervchform.setGlcode("");
 			transfervchform.setAcctype("");
 			transfervchform.setAccno(0);
 			transfervchform.setTrn_tpe1("");
 			transfervchform.setCd_ind("");
 			transfervchform.setAmount("");
 			ModuleObject[] mod1=backOfficeDelegate.getMainModules(12);
             req.setAttribute("acctype",mod1);
           //System.out.println("acctype*******>"+transfervchform.getAcctype()); 
			   for(int i=0;i<mod1.length;i++){
		         modcode=mod1[i].getModuleCode();
			   }
			    req.setAttribute("accType",backOfficeDelegate.getMainModules(11));
    			ModuleObject[] mod=backOfficeDelegate.getMainModules(10);
 			req.setAttribute("gltype", mod);
    			req.setAttribute("glnmcd", backOfficeDelegate.getGlCodeNames());
				String glname[][]=backOfficeDelegate.getGlCodeNames();
	    		Hashtable hash_glcode=backOfficeDelegate.getGLCodes(backOfficeDelegate.getSysDate());
			    
	    		Vector vec_gl_code=null;
			    vec_gl_code = (Vector)(hash_glcode.get("ALL"));
			    req.setAttribute("glcode",vec_gl_code);
			    Hashtable hash_gldesc=backOfficeDelegate.getGLCodesDesc(backOfficeDelegate.getSysDate());
			    req.setAttribute("gldesc",hash_gldesc.get(vec_gl_code.get(0))); 
             Hashtable hash_trantypes=backOfficeDelegate.getTransactionTypes(); 
			
			    Vector vec_trn_type=null;
			    vec_trn_type=(Vector)(hash_trantypes.get(modcode));
				req.setAttribute("trntype",vec_trn_type);
				
			     String[] cd_inds=backOfficeDelegate.getCDIndicator();
			     System.out.println("CDIndicators***>"+cd_inds);
			     req.setAttribute("cdind",cd_inds);
			     req.setAttribute("credit","");
				 req.setAttribute("debit",""); 
 			 req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
              req.setAttribute("TabNum","0");	
              setTabbedPaneInitParamsforTransferVoucher(req, path, transfervchform);
              req.setAttribute("pageId",MenuNameReader.getScreenProperties(transfervchform.getPageId()));
 			  return map.findForward(ResultHelp.getSuccess());
 		}
 		else{
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
     		return map.findForward(ResultHelp.getError());
     	    } 
         } catch(Exception e){
     		path=MenuNameReader.getScreenProperties("0000");
     		setErrorPageElements(""+e,req,path);
     		logger.error(e);
     		e.printStackTrace();
     		return map.findForward(ResultHelp.getError());
      	}
 }
   else if(map.getPath().trim().equalsIgnoreCase("/BackOffice/TransferVoucher"))
   {
     int cr_amt=0;
  	int dr_amt=0;
  
   	try{
   		TransferVoucherForm transfervchform=(TransferVoucherForm) form;
   	 transfervchform.setSysdate(backOfficeDelegate.getSysDate());
   		transfervchform.setVoucherno("");
   		transfervchform.setNewscrverify("");
   		 req.setAttribute("vchno",transfervchform.getVchnum());	
   		System.out.println("_____________________"+req.getParameter("cdind1"));

	
   		
    		  if(!req.getParameter("cdind1").equalsIgnoreCase("")){
    			 System.out.println("_____________________"+req.getParameter("check_amt1").substring(0, 3));
    			  if(req.getParameter("cdind1").equalsIgnoreCase("C"))
    					  
    			     cr_amt+=Integer.parseInt((req.getParameter("check_amt1")));
    			  else
    				dr_amt+=Integer.parseInt((req.getParameter("check_amt1"))); 
    		}
             if(!req.getParameter("cdind2").equalsIgnoreCase(""))
             {
  			  if(req.getParameter("cdind2").equalsIgnoreCase("C"))
  			     cr_amt+=Integer.parseInt((req.getParameter("check_amt2")));
  			  else
  				dr_amt+=Integer.parseInt((req.getParameter("check_amt2"))); 
  			  } 
    		if(!req.getParameter("cdind3").equalsIgnoreCase("")){
  			  if(req.getParameter("cdind3").equalsIgnoreCase("C"))
  			     cr_amt+=Integer.parseInt((req.getParameter("check_amt3")));
  			  else
  				dr_amt+=Integer.parseInt((req.getParameter("check_amt3"))); 
  			  
  		  } 
    		if(!req.getParameter("cdind4").equalsIgnoreCase("")){
  			  if(req.getParameter("cdind4").equalsIgnoreCase("C"))
  			     cr_amt+=Integer.parseInt((req.getParameter("check_amt4")));
  			  else
  				dr_amt+=Integer.parseInt((req.getParameter("check_amt4")));; 
  			  
  		  } 
    		if(!req.getParameter("cdind5").equalsIgnoreCase("")){
  			  if(req.getParameter("cdind5").equalsIgnoreCase("C"))
  			     cr_amt+=Integer.parseInt((req.getParameter("check_amt5")));
  			  else
  				dr_amt+=Integer.parseInt((req.getParameter("check_amt5"))); 
  			  
  		  } 
    		transfervchform.setCreditamt(String.valueOf(cr_amt));
    		transfervchform.setDebitamt(String.valueOf(dr_amt));
    		//25.08.2009
   		
   		CashDelegate recDelegate = new CashDelegate();
   		
   	
   	  req.setAttribute("date", backOfficeDelegate.getSysDate());
   	  System.out.println(" Action Acount type-->"+transfervchform.getAcctype());
   	  
   	 // module_obj_array  = backOfficeDelegate.getMainModules(3);
  		  System.out.println("Before arrayindex in action--->");
  		  System.out.println("-----0------"+transfervchform.getAccno());
  		  
  		  System.out.println("-----2----"+transfervchform.getAcctype());
  			
   	  AccountObject accountObject= recDelegate.getAccount(null,transfervchform.getAcctype(),transfervchform.getAccno(),recDelegate.getSysDate());
   	  System.out.println("AccType in Account Object -------------"+transfervchform.getAcctype());
   	  System.out.println("AccNo in accountObject-------"+transfervchform.getAccno());
   	  System.out.println("Date is-----"+recDelegate.getSysDate());
   	  System.out.println("Account Object ==="+accountObject);
   	  String string_code = null;
   	  System.out.println("Error in Main Module");
   	  System.out.println("Errrrrrrrr in sunbank!!!!!!!!!!");
   	  System.out.println("Building properly???");
   	  System.out.println("Are You Here?????");
   	  if(transfervchform.getVchnum()==0 ){
   
   	  if(transfervchform.getGltype()!=("Select")&& transfervchform.getAcctype()!=("Select"))
   	  {
   		  System.out.println("Shhhhhhrrrrraaaaaaannnnkkkkks");
   		  System.out.println("Transfer Account Type------"+transfervchform.getAcctype());
   		  System.out.println("GLTYPE========"+transfervchform.getGltype());
   		  System.out.println("GLCODE========"+transfervchform.getAccno());
   		  System.out.println("Date========"+recDelegate.getSysDate()); 
   		  try{
   			String code="";
   	     String glcode[]=backOfficeDelegate.getAccountGlcodes1(transfervchform.getAcctype(),transfervchform.getGltype(),transfervchform.getAccno(),recDelegate.getSysDate());
   	     System.out.println("Length of Glcode====="+glcode.length);
  			 if(glcode!=null)
  				{
  					req.setAttribute("code",glcode);
  				}	
   		  }
   		  catch(Exception e)
   		  {
   			  e.printStackTrace();
   		  }    	   
          }
   	  if(transfervchform.getGlcode()!=("Select")&&transfervchform.getAcctype()!=("Select"))
   	  {
   		  System.out.println("GLCode-------------->"+transfervchform.getGlcode());
   		  System.out.println("Acctype-------------->"+transfervchform.getAcctype());
   		  try
   		  {
   			  String trn_typ[]=backOfficeDelegate.getAccountTransferTypes(transfervchform.getAcctype(),Integer.parseInt(transfervchform.getGlcode()));
   			  System.out.println("Length of trn_type==="+trn_typ.length);
   			  if(trn_typ!=null)
   			  {
   				  req.setAttribute("trn_typ", trn_typ);
   			  }
   		  }
   		  catch(Exception e)
   		  {
   			  e.printStackTrace();
   		  }
   	  }
   	  if(transfervchform.getGlcode()!=("Select")&&transfervchform.getAcctype()!=("Select")&&transfervchform.getT_type()!=("Select"))
   	  {
   		  System.out.println("GLCode-------------->"+transfervchform.getGlcode());
   		  System.out.println("Acctype-------------->"+transfervchform.getAcctype());
   		  System.out.println("Trntype-------------->"+transfervchform.getT_type());
   		  try
   		  {
   			  String cd_ind=backOfficeDelegate.getCDIndicator(transfervchform.getAcctype(),Integer.parseInt(transfervchform.getGlcode()),transfervchform.getT_type());
   			 
   			  if(cd_ind!=null)
   			  {
   				  req.setAttribute("cd_ind", cd_ind);
   			  }
   		  }
   		  catch(Exception e)
   		  {
   			  e.printStackTrace();
   		  }
   	  }
   	  }
   	 if(transfervchform.getAccno()!=0)
   	 {
   		
  	  if(accountObject!=null) 
   	  {
  		
  		     System.out.println("r u cuming here!11");
  		     System.out.println("Account Name before  Modules---------"+accountObject.getAccname());
  		     req.setAttribute("ac_holder", accountObject.getAccname());
   		 module_obj_array  = backOfficeDelegate.getMainModules(12);
  		     req.setAttribute("MainModules", module_obj_array);//GENERAL module
            String module="";
             for(int k=0;k<module_obj_array.length;k++)
                {
     	           if(module_obj_array[k].getModuleCode().equalsIgnoreCase(transfervchform.getAcctype()))
     	            {
     	               module=module_obj_array[k].getModuleAbbrv();
     	            }
     	           }
                   req.setAttribute("getAbbrv", module);
                    if(module!=null)
                      {
                         for(int i=0;i<module_obj_array.length;i++)
     	                     	{
     	                                String mod=module_obj_array[i].getModuleAbbrv();
     	                     	        if(mod.equalsIgnoreCase(module))
     		                         	{
     	                     	            String string_desc=module_obj_array[i].getModuleDesc();
     			                            req.setAttribute("module",string_desc);
                                           string_code =String.valueOf(module_obj_array[i].getModuleCode());
     		  	                        }
     	   	                       }
                           	}
       System.out.println("module is nullll"+module);
       req.setAttribute("getAbbrv", module);
         if(transfervchform.getAccno()!=0)	       
   	  {
  	    	req.setAttribute("accno",String.valueOf(transfervchform.getAccno()));
  		    if(string_code.startsWith("1009"))// LK
  	        	{
  	        	try 
  	                {
  	        			string_locker_type=recDelegate.getLockerType(module_obj_array[Integer.valueOf(transfervchform.getAcctype())].getModuleCode(),transfervchform.getAccno());
  	        			lock_types = recDelegate.getLockersTypes(); 
  	        				
  	                    for(int i=0;i<lock_types.length;i++)
  	                     	{
  	                     		System.out.println("locker types before for loop==="+lock_types[i]);
  	                     	}
  	                     if(string_locker_type==null)
  	                     	{
  	                     		accnoexist = 1;
  	                     		System.out.println("Invalid Account No");
  	                     		transfervchform.setAccexist("invalidaccount");
  	                     		req.setAttribute("error", "Invalid Account No");
  	                     	}
  	                     else
  	                      {
  	                         for(int i=0;i<lock_types.length;i++)
  	                          {

	                          {
	                            if(lock_types[i].equals(string_locker_type))
	                            {
	                                 
	                            	String locdesc = Lockdesc[i];
	                                req.setAttribute("lockerdescpn", locdesc); 
	                                req.setAttribute("lockerindex",String.valueOf(i));
	                                req.setAttribute("locktype",null);
	                           }
	                       }
	               }
           double lk_rent = 0.0;  
	          double_locker_rent=recDelegate.getLockerRent(module_obj_array[Integer.valueOf(transfervchform.getAcctype())].getModuleCode(),transfervchform.getAccno(),recDelegate.getSysDate());
	          System.out.println("total rent in client...."+double_locker_rent);
	         if(double_locker_rent==0.0)
	            {
	        	 accnoexist = 1;
	             System.out.println("Locker Rent not defined");
	             req.setAttribute("error", "Locker Rent not defined");
	             transfervchform.setLk_amount("null");
	            }
	          else
	            {
	             lk_rent = double_locker_rent;
	             transfervchform.setLk_amount(String.valueOf(lk_rent));
	             System.out.println("locker rent for particular type"+lk_rent);
	            }       
	         } 
  	                }
	        catch (NumberFormatException e1) 
	        {
	        	e1.printStackTrace();
	        }
	        catch (RemoteException e1) 
	        {
	        	e1.printStackTrace();
	        }
	        catch (SQLException e1) 
	        {
	        	e1.printStackTrace();
	        }
	     }
			
	      else if(string_code.startsWith("1001"))// SH
	        {
	    	  try 
	        	{
	    		  transfervchform.setAccexist("null");
	        			System.out.println("share no..."+transfervchform.getAccno());
	        			
	        			sharemasterobject=recDelegate.getShare(string_code,transfervchform.getAccno());
	        			System.out.println("share masater object"+sharemasterobject);
	        			
	        			if(sharemasterobject!=null)
	        			share_cat = sharemasterobject.getShareType();   
	                 
	        			if(share_cat==0)
	        				{ 
	        				accnoexist = 1;
	        				transfervchform.setClosed("invalidacct");
	        				}
	        			else
	        				{
	        				if(sharecategoryobject!=null){
	        					for(int i=0;i<sharecategoryobject.length;i++)
	        					{
	        							if(sharecategoryobject[i].getShCat()==share_cat)
	        								{
	        								String sharecategory =sharecategoryobject[i].getCatName();
	        								if(sharecategoryobject[i].getCatName()!=null)
	        								req.setAttribute("sharecategoryname",sharecategory);
	        								req.setAttribute("shareindex",String.valueOf(i));
	        								req.setAttribute("sharecategory", null);
	        								}
	        						}
	                     
	        				}
	        				}
	        			} 
	        			catch (NumberFormatException e1) 
	        				{
	        				e1.printStackTrace();
	        				}
	        			catch (RemoteException e1) 
	        				{
	        				e1.printStackTrace();
	        				}
	        	}
	        		

		System.out.println("accnoexist == "+accnoexist);
		   
		
		if(accnoexist==0)
  {
	    	System.out.println("At last i am here====");
			try
				{	
  			
				if(accountObject!=null)
					{ 
					 transfervchform.setAccexist("null");
					 if(accountObject.getAccStatus().equals("C"))
					 	{
						 transfervchform.setAccountobject("accountobject");
						 System.out.println("Given account is Closed");
						 req.setAttribute("error", "Given Account Is Closed");
					 	}
					 else if(accountObject.getAccStatus().equals("I"))
					 	{
						 transfervchform.setAccountobject("accountobject1");
						 System.out.println("InOperative Account");
						 req.setAttribute("error", "InOperative Account");
					 	}
					 else if(accountObject.getVerified()==null)
					 	{
						 transfervchform.setAccountobject("accountobject2");
						 System.out.println("Given account is not yet Verified");
						 req.setAttribute("error", "Given account is not yet Verified");
					 	}
					 else if((accountObject.getDefaultInd()!=null)?accountObject.getDefaultInd().equals("T"):false)
					 	{  
						 transfervchform.setAccountobject("accountobject3");
						 System.out.println("Default account");
						 req.setAttribute("error", "Default account");
					 	}
					 else if((accountObject.getFreezeInd()!=null)? accountObject.getFreezeInd().equals("T"):false)
					 	{
						 transfervchform.setAccountobject("accountobject4");
						 System.out.println("Freezed account");
						 req.setAttribute("error", "Freezed account");
					 	}
					 else
				 	{
					 req.setAttribute("accname",accountObject.getAccname());
					 int cid = accountObject.getCid();
					 req.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
					 req.setAttribute("address",recDelegate.getCustomerAddress(cid));
					 req.setAttribute("addr_type",recDelegate.getCustomerAddType(cid));
					 
					 req.setAttribute("panelName", CommonPanelHeading.getPersonal());
					 if(string_code.startsWith("1008"))//Loans On Deposits
                {
                    loanreportobject = recDelegate.getLoanDetails(string_code,recDelegate.getSysDate(),transfervchform.getAccno());
                    
                    if(loanreportobject!=null)
                    {
                        req.setAttribute("loan_bal",doubleToString(loanreportobject.getLoanBalance(),2));
                        req.setAttribute("laon_ap","NA*");
                        req.setAttribute("loan_po","NA*");
                        req.setAttribute("loan_ci","NA*");
                        req.setAttribute("loan_iat",doubleToString((loanreportobject.getInterestPaid()+loanreportobject.getExtraIntPaid()),2));
                        req.setAttribute("loan_ipu",loanreportobject.getIntUptoDate());
                        req.setAttribute("loan_ia",doubleToString((loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid()),2));
                        req.setAttribute("loan_ir",doubleToString(loanreportobject.getLoanIntRate(),2));
                        req.setAttribute("loan_pi","NA*");
                        req.setAttribute("loan_pir","NA*");
                        req.setAttribute("loan_oc","NA*");
                        req.setAttribute("loan_ei","NA*");
                       double mx_amt=recDelegate.getMaxPayableAmtonCurrentDay(moduleobject[Integer.parseInt(transfervchform.getAcctype())].getModuleCode(),transfervchform.getAccno(),recDelegate.getSysDate());
                        req.setAttribute("loan_aca",doubleToString(mx_amt,2));
                    }
                }
                ///////////
                //ship......21/07/2007
                else if(string_code.startsWith("1010"))//Loans
                {
                    loanmasterobject = recDelegate.getQueryOnLoanStatus(string_code,transfervchform.getAccno(),recDelegate.getSysDate());
                    loantransactionobject = recDelegate.getQueryLoanStatus(string_code,transfervchform.getAccno(),recDelegate.getSysDate(),"SHIP","CA99");
                     
                    if(loanmasterobject!=null && loantransactionobject!=null)
                    { 
                   	 req.setAttribute("loan_bal",doubleToString(loantransactionobject.getLoanBalance(),2));
                   	 req.setAttribute("laon_ap",doubleToString(loantransactionobject.getPrincipalPaid(),2));
                   	req.setAttribute("loan_po",doubleToString(loantransactionobject.getPrincipalBalance(),2));
                   	req.setAttribute("loan_ci",doubleToString(loantransactionobject.getPrincipalPayable(),2));
                   	req.setAttribute("loan_iat","NA*");
                   	req.setAttribute("loan_ipu","NA*");
                   	req.setAttribute("loan_ia",doubleToString(loantransactionobject.getInterestPayable(),2));
                   	req.setAttribute("loan_ir",doubleToString(loanmasterobject.getInterestRate(),2));
                   	req.setAttribute("loan_pi",doubleToString(loantransactionobject.getPenaltyAmount(),2));
                   	req.setAttribute("loan_pir",doubleToString(loanmasterobject.getPenalRate(),2));
                   	req.setAttribute("loan_oc",doubleToString(loantransactionobject.getOtherAmount(),2));
                   	req.setAttribute("loan_ei",doubleToString(loantransactionobject.getExtraIntAmount(),2));
                   	req.setAttribute("loan_aca",doubleToString((loantransactionobject.getLoanBalance()+loantransactionobject.getPenaltyAmount()+loantransactionobject.getInterestPayable()+loantransactionobject.getOtherAmount()-loantransactionobject.getExtraIntAmount()),2));
                    }
                }
				 	}
				}
				else
			       {
			           accnoexist = 0;
			       }
			 }
		catch(Exception exception)
			{
				exception.printStackTrace();
			}
}
}
		
     }	
	 else
			{
			System.out.println("At last i am here account not found====");
			transfervchform.setAccexist("accexist");
			req.setAttribute("error", "Given Account Number Not Found.....");
			System.out.println("Given account number not found.....1");
			}
 	 }
 	 VoucherDataObject[] array_voucherdataobject=null;
		 String selectedmodcode=null,string_query=null;	
	  	 System.out.println("vchno-------->"+transfervchform.getVchnum());
		 System.out.println("Get Page ID========12333333"+transfervchform.getPageId());
		if(MenuNameReader.containsKeyScreen(transfervchform.getPageId())){
			  
			backOfficeDelegate=new BackOfficeDelegate();
			commonBackOfficeInitParameters(req,backOfficeDelegate);
			setTransferVoucherDetails(backOfficeDelegate, req);
			
			req.setAttribute("glnmcd", backOfficeDelegate.getGlCodeNames());	
			ModuleObject[] mod1=backOfficeDelegate.getMainModules(12);
			    System.out.println("Acctype***>"+transfervchform.getAcctype());
			    req.setAttribute("acctype",mod1);
			    String amt=req.getParameter("amount");
			    System.out.println("dbm**"+transfervchform.getDebitamt()+"tdbm**"+transfervchform.getTotaldebitamt()+"rowm**"+amt);
			    System.out.println("acctype*******>"+transfervchform.getAcctype()); 
			  for(int i=0;i<mod1.length;i++){
		      if(transfervchform.getAcctype().equalsIgnoreCase(mod1[i].getModuleCode())){
			   selectedmodcode=mod1[i].getModuleCode();
			   }
			 }
			ModuleObject[] mod=backOfficeDelegate.getMainModules(10);
			    req.setAttribute("gltype", mod);
			  	req.setAttribute("acctype",backOfficeDelegate.getMainModules(12));
	           String[] cd_inds=backOfficeDelegate.getCDIndicator();
 		   req.setAttribute("cdind",cd_inds); 
			   String method=req.getParameter("method");
			   String use_id= req.getParameter("id");
			   req.setAttribute("id", req.getParameter("id"));
			   req.setAttribute("method", req.getParameter("method"));
			   List transfervoucherlist=(ArrayList)session.getAttribute("TransferVoucherDetails");
			   System.out.println("lis222222222222==="+transfervoucherlist.size());
			   String creditamt=transfervchform.getCreditamt();
			   String debitamt=transfervchform.getDebitamt();
				req.setAttribute("credit", creditamt);
				req.setAttribute("debit", debitamt);
				VoucherDataObject[] focback=backOfficeDelegate.VoucherData("T",transfervchform.getVchnum());
				
				if(transfervchform.getVchnum()!=0 && transfervchform.getVchnum()>0){
					 
						{	
							System.out.println("i am inside loop.....");
							MiscellUVScrollNos(transfervchform, backOfficeDelegate,req);//sowmya====14/05/2008
			               
						}
	             	if(focback!=null){
       	    	transfervchform.setDate(focback[0].getTransactionDate()); 	    	
       	    	 req.setAttribute("credit",focback[0].getTransactionAmount());
					 req.setAttribute("debit", focback[0].getTransactionAmount()); 
       	    	 
								 
					  transfervchform.setNarration(focback[0].getNarration());
	            	 
	             	}
        }
           if(transfervchform.getForward()!=null){

					System.out.println("*******inside forward********");
					System.out.println("Hellooooooooo");
					System.out.println("forward********"+transfervchform.getForward());
				if(transfervchform.getForward().equalsIgnoreCase("SUBMIT")){
					double cd_c_amt=0.00;
					double cd_d_amt=0.00;
					System.out.println("____++++++++++++++++"+req.getParameterValues("cbox"));
					String checking[]=req.getParameterValues("cbox");
					if(checking!=null){
					for(int cks=0;cks<checking.length;cks++)
						System.out.println("Checkings___________"+checking[cks]);
					
					for(int u=0;u<checking.length;u++){
						if(checking[u].equalsIgnoreCase("1")){
					if(req.getParameter("cdind1")!=null){
						if(req.getParameter("cdind1").toString().equalsIgnoreCase("C"))
							cd_c_amt+=Double.parseDouble(req.getParameter("check_amt1"));
						else
							cd_d_amt+=Double.parseDouble(req.getParameter("check_amt1"));
					}
						}
						if(checking[u].equalsIgnoreCase("2")){
					if(req.getParameter("cdind2")!=null){
						if(req.getParameter("cdind2").toString().equalsIgnoreCase("C"))
							cd_c_amt+=Double.parseDouble(req.getParameter("check_amt2"));
						else
							cd_d_amt+=Double.parseDouble(req.getParameter("check_amt2"));
					}
						}
						if(checking[u].equalsIgnoreCase("3")){
					if(req.getParameter("cdind3")!=null){
						if(req.getParameter("cdind3").toString().equalsIgnoreCase("C"))
							cd_c_amt+=Double.parseDouble(req.getParameter("check_amt3"));
						else
							cd_d_amt+=Double.parseDouble(req.getParameter("check_amt3"));
					}
						}
						if(checking[u].equalsIgnoreCase("4")){
					if(req.getParameter("cdind4")!=null){
						if(req.getParameter("cdind4").toString().equalsIgnoreCase("C"))
							cd_c_amt+=Double.parseDouble(req.getParameter("check_amt4"));
						else
							cd_d_amt+=Double.parseDouble(req.getParameter("check_amt4"));
					}
						}
						if(checking[u].equalsIgnoreCase("5")){
					if(req.getParameter("cdind5")!=null){
						if(req.getParameter("cdind5").toString().equalsIgnoreCase("C"))
							cd_c_amt+=Double.parseDouble(req.getParameter("check_amt5"));
						else
							cd_d_amt+=Double.parseDouble(req.getParameter("check_amt5"));
					}
						}
					}
					
				
					/*boolean chk=false;
					int count=0;
					String checking[]=req.getParameterValues("cbox");
					for(int u=0;u<checking.length;u++){
					if(checking[u].equalsIgnoreCase("1")){
						System.out.println("__________C_________"+req.getParameter("check_gltype1"));
						if(req.getParameter("check_gltype1")!=null)
							count++;
						
					}
					else if(checking[u].equalsIgnoreCase("2")){
						System.out.println("__________C_________"+req.getParameter("check_gltype2"));
						if(req.getParameter("check_gltype2")!=null)
							count++;
					}
					else if(checking[u].equalsIgnoreCase("3")){
						System.out.println("__________C_________"+req.getParameter("check_gltype3"));
						if(req.getParameter("check_gltype3")!=null)
							count++;
					}
					else if(checking[u].equalsIgnoreCase("4")){
						System.out.println("__________C_________"+req.getParameter("check_gltype4"));
						if(req.getParameter("check_gltype4")!=null)
							count++;
					}
					else if(checking[u].equalsIgnoreCase("5")){
						System.out.println("__________C_________"+req.getParameter("check_gltype5"));
						if(req.getParameter("check_gltype5")!=null)
							count++;
					}
					}*/
					if(cd_c_amt==cd_d_amt){
					System.out.println("**********inside submit*******");
					transfervoucherlist=(ArrayList)session.getAttribute("TransferVoucherDetails");
					System.out.println("list0000000000000000000000==="+transfervoucherlist.size());
				    String acctype[]=req.getParameterValues("acctype");
					System.out.println("Account Type"+acctype.length);
					String accno[]=req.getParameterValues("accno");
					System.out.println("Account Type"+accno.length);
					System.out.println("The Account Number is "+accno[0]);
					String gltype[]=req.getParameterValues("gltype");
					System.out.println("Thegltype is====="+gltype[0]);
					System.out.println("Error is here");
					BackOfficeDelegate bd= new BackOfficeDelegate();
					String[] cboxs=req.getParameterValues("cbox");
					System.out.println("Cbox Length====________________"+cboxs.length);
				    String amount1=req.getParameter("check_amt1");
				    String amount2=req.getParameter("check_amt2");
				    String amount3=req.getParameter("check_amt3");
				    String amount4=req.getParameter("check_amt4");
				    String amount5=req.getParameter("check_amt5");
				    
					VoucherDataObject[] vchdataObj = new VoucherDataObject[cboxs.length];
					//String[] cdind=req.getParameterValues("trntype");
					double sum=0.00,cred=0.00;
					//double sum=0.00;
					//26.08.2009
					for(int z=0;z<cboxs.length;z++){
						System.out.println("cboxz array values are__________"+cboxs[z]);
					}
					
				System.out.println("==========================At 3579=====CREDIT AND DEBIT ARE SAME===");		
					
				
				int v=0;
				for(int k=0;k<cboxs.length;k++){
					if(cboxs[k].equalsIgnoreCase("1")){
						vchdataObj[k]=new VoucherDataObject();
	        		    vchdataObj[k].setModuleAccountType(transfervchform.getCheck_acctype1());
	        		    System.out.println("The acc type valueafetr inserting==="+transfervchform.getCheck_acctype1());
						
					    vchdataObj[k].setModuleAccountNo(transfervchform.getCheck_accno1());
					    System.out.println("The accno valueafetr inserting==="+transfervchform.getCheck_accno1());
						
						vchdataObj[k].setGlType(transfervchform.getCheck_gltype1());
						 System.out.println("The gltype valueafetr inserting==="+transfervchform.getCheck_gltype1());
							
						
						vchdataObj[k].setGlCode(Integer.parseInt(transfervchform.getCheck_glcode1())); 
						System.out.println("The glcode value afetr inserting==="+transfervchform.getCheck_glcode1());
						
						vchdataObj[k].setTransactionType(transfervchform.getTrn_tpe1());
						System.out.println("The Trn type after inserting--------"+transfervchform.getTrn_tpe1());
					    
					    vchdataObj[k].setTransactionAmount(Double.parseDouble(transfervchform.getCheck_amt1()));
					    System.out.println("The amount after inserting--------"+transfervchform.getCheck_amt1());
					    vchdataObj[k].setCdIndicator(transfervchform.getCdind1());
					    System.out.println("The cd indicator after inserting--------"+transfervchform.getCdind1());
					    vchdataObj[k].setTransactionDate(transfervchform.getDate());
					    
						
						vchdataObj[k].setVoucherNo(transfervchform.getVchnum());
						
						vchdataObj[k].setNarration(transfervchform.getNarration());
						
						vchdataObj[k].setVoucherType("T");
						
						vchdataObj[k].obj_userverifier.setUserTml("tml");
						
						vchdataObj[k].obj_userverifier.setUserId("uid");
						
						vchdataObj[k].obj_userverifier.setUserDate("date");
		
					}else if(cboxs[k].equalsIgnoreCase("2")){
						vchdataObj[k]=new VoucherDataObject();
	        		    vchdataObj[k].setModuleAccountType(transfervchform.getCheck_acctype2());
	        		    System.out.println("The acc type valueafetr inserting==="+transfervchform.getCheck_acctype2());
						
					    vchdataObj[k].setModuleAccountNo(transfervchform.getCheck_accno2());
					    System.out.println("The accno valueafetr inserting==="+transfervchform.getCheck_accno2());
						
						vchdataObj[k].setGlType(transfervchform.getCheck_gltype2());
						 System.out.println("The gltype valueafetr inserting==="+transfervchform.getCheck_gltype2());
							
						
						vchdataObj[k].setGlCode(Integer.parseInt(transfervchform.getCheck_glcode2())); 
						System.out.println("The glcode valueafetr inserting==="+transfervchform.getCheck_glcode2());
						
						vchdataObj[k].setTransactionType(transfervchform.getTrn_tpe2());
						System.out.println("The Trn type after inserting--------"+transfervchform.getTrn_tpe2());
					    
					    vchdataObj[k].setTransactionAmount(Double.parseDouble(transfervchform.getCheck_amt2()));
					    System.out.println("The amount after inserting--------"+transfervchform.getCheck_amt2());
					    vchdataObj[k].setCdIndicator(transfervchform.getCdind2());
					    System.out.println("The cd indicator after inserting--------"+transfervchform.getCdind2());  
					    vchdataObj[k].setTransactionDate(transfervchform.getDate());
						
						vchdataObj[k].setVoucherNo(transfervchform.getVchnum());
						
						vchdataObj[k].setNarration(transfervchform.getNarration());
						
						vchdataObj[k].setVoucherType("T");
						
						vchdataObj[k].obj_userverifier.setUserTml("tml");
						
						vchdataObj[k].obj_userverifier.setUserId("uid");
						
						vchdataObj[k].obj_userverifier.setUserDate("date");
		
			
					}else if(cboxs[k].equalsIgnoreCase("3")){
						vchdataObj[k]=new VoucherDataObject();
	        		    vchdataObj[k].setModuleAccountType(transfervchform.getCheck_acctype3());
	        		    System.out.println("The acc type valueafetr inserting==="+transfervchform.getCheck_acctype3());
						
					    vchdataObj[k].setModuleAccountNo(transfervchform.getCheck_accno3());
					    System.out.println("The accno valueafetr inserting==="+transfervchform.getCheck_accno3());
						
						vchdataObj[k].setGlType(transfervchform.getCheck_gltype3());
						 System.out.println("The gltype valueafetr inserting==="+transfervchform.getCheck_gltype3());
							
						
						vchdataObj[k].setGlCode(Integer.parseInt(transfervchform.getCheck_glcode3())); 
						System.out.println("The glcode valueafetr inserting==="+transfervchform.getCheck_glcode3());
						
						vchdataObj[k].setTransactionType(transfervchform.getTrn_tpe3());
						System.out.println("The Trn type after inserting--------"+transfervchform.getTrn_tpe3());
					    
					    vchdataObj[k].setTransactionAmount(Double.parseDouble(transfervchform.getCheck_amt3()));
					    System.out.println("The amount after inserting--------"+transfervchform.getCheck_amt3());
					    vchdataObj[k].setCdIndicator(transfervchform.getCdind3());
					    System.out.println("The cd indicator after inserting--------"+transfervchform.getCdind3());
					 
					    vchdataObj[k].setTransactionDate(transfervchform.getDate());
						
						vchdataObj[k].setVoucherNo(transfervchform.getVchnum());
						
						vchdataObj[k].setNarration(transfervchform.getNarration());
						
						vchdataObj[k].setVoucherType("T");
						
						vchdataObj[k].obj_userverifier.setUserTml("tml");
						
						vchdataObj[k].obj_userverifier.setUserId("uid");
						
						vchdataObj[k].obj_userverifier.setUserDate("date");
		
			
					}else if(cboxs[k].equalsIgnoreCase("4")){
						vchdataObj[k]=new VoucherDataObject();
	        		    vchdataObj[k].setModuleAccountType(transfervchform.getCheck_acctype4());
	        		    System.out.println("The acc type valueafetr inserting==="+transfervchform.getCheck_acctype4());
						
					    vchdataObj[k].setModuleAccountNo(transfervchform.getCheck_accno4());
					    System.out.println("The accno valueafetr inserting==="+transfervchform.getCheck_accno4());
						
						vchdataObj[k].setGlType(transfervchform.getCheck_gltype4());
						 System.out.println("The gltype valueafetr inserting==="+transfervchform.getCheck_gltype4());
							
						
						vchdataObj[k].setGlCode(Integer.parseInt(transfervchform.getCheck_glcode4())); 
						System.out.println("The glcode valueafetr inserting==="+transfervchform.getCheck_glcode4());
						
						vchdataObj[k].setTransactionType(transfervchform.getTrn_tpe4());
						System.out.println("The Trn type after inserting--------"+transfervchform.getTrn_tpe4());
					    
					    vchdataObj[k].setTransactionAmount(Double.parseDouble(transfervchform.getCheck_amt4()));
					    System.out.println("The amount after inserting--------"+transfervchform.getCheck_amt4());
					    	vchdataObj[k].setCdIndicator(transfervchform.getCdind4());
					    	System.out.println("The cd indicator after inserting--------"+transfervchform.getCdind4());
				   
					    vchdataObj[k].setTransactionDate(transfervchform.getDate());
						
						vchdataObj[k].setVoucherNo(transfervchform.getVchnum());
						
						vchdataObj[k].setNarration(transfervchform.getNarration());
						
						vchdataObj[k].setVoucherType("T");
						
						vchdataObj[k].obj_userverifier.setUserTml("tml");
						
						vchdataObj[k].obj_userverifier.setUserId("uid");
						
						vchdataObj[k].obj_userverifier.setUserDate("date");
		
			
					}else if(cboxs[k].equalsIgnoreCase("5")){
						vchdataObj[k]=new VoucherDataObject();
	        		    vchdataObj[k].setModuleAccountType(transfervchform.getCheck_acctype5());
					    vchdataObj[k].setModuleAccountNo(transfervchform.getCheck_accno5());
					    vchdataObj[k].setGlType(transfervchform.getCheck_gltype5());
						vchdataObj[k].setGlCode(Integer.parseInt(transfervchform.getCheck_glcode5())); 
						vchdataObj[k].setTransactionType(transfervchform.getTrn_tpe5());
						vchdataObj[k].setTransactionAmount(Double.parseDouble(transfervchform.getCheck_amt5()));
					    vchdataObj[k].setCdIndicator(transfervchform.getCdind5());
					    vchdataObj[k].setTransactionDate(transfervchform.getDate());
						vchdataObj[k].setVoucherNo(transfervchform.getVchnum());
						vchdataObj[k].setNarration(transfervchform.getNarration());
						vchdataObj[k].setVoucherType("T");
						vchdataObj[k].obj_userverifier.setUserTml("tml");	
						vchdataObj[k].obj_userverifier.setUserId("uid");
						vchdataObj[k].obj_userverifier.setUserDate("date");
		
		
					}
					
				}
					System.out.println("Insertin when Credit and Debit are equal");
					int vch_no=backOfficeDelegate.storeTransferVoucherData(vchdataObj);
					System.out.println("Voucher Number:******"+vch_no);
					if(transfervchform.getVouchervalue().equalsIgnoreCase("true")){
       			System.out.println("Generated Voucher Number**********:"+vch_no);
       			
       			req.setAttribute("displaymsg","Note Down Voucher Number :"+vch_no);
       			System.out.println("=====Succesfully Inserted=======");
              }
				else{
				      req.setAttribute("msg","credit and Debit Amount Dont match");
					}
				}else{
					req.setAttribute("msg","Please check the rows you selected...!");
				}
				}else{
					req.setAttribute("msg", "Please select the Rows to be inserted");
				}
					
				}
 			if(transfervchform.getForward().equalsIgnoreCase("VERIFY")){
			         
					VoucherDataObject voucherdata=new VoucherDataObject();
					System.out.println("******Inside Verify**********");
					 
					voucherdata=backOfficeDelegate.getFaDataTransferDetails(transfervchform.getVchnum());
					if(voucherdata==null)
					{					
						req.setAttribute("displaymsg","You can't verify, this number is already deleted");
					}
						if(voucherdata.obj_userverifier.getVerId()==null )
		    	            {
		    	             voucherdata.obj_userverifier.setVerId("verid");
		    	             voucherdata.obj_userverifier.setVerTml("vertml");
		    	             voucherdata.obj_userverifier.setVerDate("verdate");
		    	            
		    	            boolean verify_vch=backOfficeDelegate.verifyTransferFadata(voucherdata);
				 
		    	              System.out.println("^^^^^^^^^^Successfully Verified^^^^^^^^^");
		    	              	req.setAttribute("displaymsg","Sucessfully Verified !!!!!");
		    	            }else
		    	            {
		    	            	req.setAttribute("displaymsg","Voucher Number Is Already Verrified!!");
		    	            }
				}
 		    if(transfervchform.getForward().equalsIgnoreCase("DELETE"))
 	        {
 		    	VoucherDataObject voucherdata=new VoucherDataObject();
 		    	voucherdata=backOfficeDelegate.getFaDataTransferDetails(transfervchform.getVchnum());
 		    	
 		    	if(voucherdata==null)
 		    	{
 		    		req.setAttribute("displaymsg", "Voucher Number is Already Deleted");
 		    	}
 		    	else 
 		    	{
 		    	if(voucherdata.obj_userverifier.getVerDate()!=null)
 		    	{
 		    		 req.setAttribute("displaymsg","You can't delete !!!!!!This voucher number  is already verified");
 		    	}
 		    	else
 		    	{
 		    		System.out.println("DELETE VOUCHER NUMBER!!!!!!!!!!!!!!");
 		            if(transfervchform.getVchnum()!=0){
				           int del_vchno=backOfficeDelegate.deleteTransferDataVoucher(transfervchform.getVchnum(),"T",backOfficeDelegate.getSysDate() );
				           System.out.println("Deleted VoucherNo:"+del_vchno);
				           System.out.println("Deleted Sucessfully!!!!!");
				           	req.setAttribute("displaymsg","Deleted Sucessfully!!!!!!!");
 		       }
 		    	}
 		      
 		    	}
 	        
 		    	/*String[] cboxs=req.getParameterValues("cbox");
					System.out.println("Cbox Length====________________"+cboxs.length);
 	        	
 	        	VoucherDataObject vo = new VoucherDataObject();
 			
 				for(int i=0; i<cboxs.length; i++)
 				{
 					vo[i]=new VoucherDataObject();
 			    	vo[i].setVoucherNo(transfervchform.getVchnum());
 				}
 				
 				System.out.println("Calling Delete Details function");
 				
 				try{
 					int result = backOfficeDelegate.deleteTransferVoucher(vo);
 					
 				
 					{
 						if(result==1)
 						System.out.println("Records Deleted !");
 					else 
 						System.out.println("Records Not Deleted !");
 						
 					}
 				}
 				catch(Exception e){
 					e.printStackTrace();
 					
 				}*/
 				
 			}
 		    
		}
	}
		else{
   		path=MenuNameReader.getScreenProperties("0000");
   		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
   		return map.findForward(ResultHelp.getError());
   	    } 
		
		    ModuleObject[] mod=backOfficeDelegate.getMainModules(10);
			req.setAttribute("gltype", mod);
			req.setAttribute("acctype",backOfficeDelegate.getMainModules(12));
			req.setAttribute("glnmcd", backOfficeDelegate.getGlCodeNames());
		    String glname[][]=backOfficeDelegate.getGlCodeNames();
			req.setAttribute("pageId",MenuNameReader.getScreenProperties(transfervchform.getPageId()));
			req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
         req.setAttribute("TabNum","0");	
         setTabbedPaneInitParamsforTransferVoucher(req, path, transfervchform);
			return map.findForward(ResultHelp.getSuccess());
		
       }
      catch(Exception e)
	  {
 		path=MenuNameReader.getScreenProperties("0000");
 		setErrorPageElements(""+e,req,path);
 		logger.error(e);
 		e.printStackTrace();
 		return map.findForward(ResultHelp.getError());
   }
 }
    else{
  		path=MenuNameReader.getScreenProperties("0000");
  		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
  		return map.findForward(ResultHelp.getError());
  	    } 
}
   private void setErrorPageElements(String exception,HttpServletRequest req,String path){
    req.setAttribute("exception",exception);
    commonBackOfficeInitParameters(req,backOfficeDelegate);
   	}
   private void setSIEntryInitParams(HttpServletRequest req,String path, BackOfficeDelegate backOfficeDelegate)throws Exception{
    try{
    	commonBackOfficeInitParameters(req,backOfficeDelegate);
        req.setAttribute("loanoption",backOfficeDelegate.getComboLoanOptions());
       
    }catch(Exception e){
        throw e;
    }
  }
  
   private void setTabbedPaneInitParamsforSIEntry(HttpServletRequest req,String path,SIEntryForm siForm)throws Exception{
	   System.out.println(path);
	   
	   String pageActions[]={"/BackOffice/SIEntry?tabPaneHeading=FromAccountNumber&pageId="+siForm.getPageId(),"/BackOffice/SIEntry?tabPaneHeading=ToAccountNumber&pageId="+siForm.getPageId()};
	   
	   String tabHeading[]={"FromAccountNumber","ToAccountNumber"};
	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim()};
	   
	   req.setAttribute("TabHeading",tabHeading);
	   req.setAttribute("PageActions", pageActions);
	   req.setAttribute("PagePath", pagePath);
	   req.setAttribute("pageNum","3003");
	   
   }
   

   private void setSIDeleteInitParams(HttpServletRequest req,String path, BackOfficeDelegate backOfficeDelegate)throws Exception{
      try{
    	  commonBackOfficeInitParameters(req,backOfficeDelegate);
         
         
      }catch(Exception e){
          throw e;
      }
  }

   private void setTabbedPaneInitParamsforSIDelete(HttpServletRequest req,String path,SIDeleteForm sidelForm)throws Exception{
  	   System.out.println(path);
  	   
  	   String pageActions[]={"/BackOffice/SIDelete?tabPaneHeading=PersonalDetails&pageId="+sidelForm.getPageId(),"/BackOffice/SIDelete?tabPaneHeading=PersonalDetails&pageId="+sidelForm.getPageId()};
  	   
  	   String tabHeading[]={"FromAccountNumber","ToAccountNumber"};
  	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim()};
  	   
  	   req.setAttribute("TabHeading",tabHeading);
  	   req.setAttribute("PageActions", pageActions);
  	   req.setAttribute("PagePath", pagePath);
  	   req.setAttribute("pageNum","3003");
  	   
     }
   
   private void setTabbedPaneInitParamsforPaymentVoucher(HttpServletRequest req,String path,PaymentVoucherForm paymentvchform)throws Exception{
  	   System.out.println(path);
  	   
  	   String pageActions[]={"/BackOffice/PaymentVoucher?tabPaneHeading=PersonalDetails&pageId="+paymentvchform.getPageId(),"/BackOffice/PaymentVoucher?tabPaneHeading=SignatureDetails&pageId="+paymentvchform.getPageId()};
  	   
  	   String tabHeading[]={"PersonalDetails","Signatur" +
  	   		"eDetails"};
  	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim()};
  	   
  	   req.setAttribute("TabHeading",tabHeading);
  	   req.setAttribute("PageActions", pageActions);
  	   req.setAttribute("PagePath", pagePath);
  	   req.setAttribute("pageNum","3003");
  	   
     }     
   
   public static String doubleToString(double value,int scale){
		try {
			BigDecimal dec = new BigDecimal(value);
			return (dec.movePointRight(scale+1).setScale(1,0).movePointLeft(3).setScale(scale,1).toString());
		} catch (Exception exe) {
			return "0";
		}
	}

   
   private void setTabbedPaneInitParamsforTransferVoucher(HttpServletRequest req,String path,TransferVoucherForm transfervchform)throws Exception{
  	   System.out.println(path);
  	   
  	   String pageActions[]={"/BackOffice/PaymentVoucher?tabPaneHeading=PersonalDetails&pageId="+transfervchform.getPageId(),"/BackOffice/PaymentVoucher?tabPaneHeading=SignatureDetails&pageId="+transfervchform.getPageId()};
  	   
  	   String tabHeading[]={"PersonalDetails","SignatureDetails"};
  	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim()};
  	   
  	   req.setAttribute("TabHeading",tabHeading);
  	   req.setAttribute("PageActions", pageActions);
  	   req.setAttribute("PagePath", pagePath);
  	   req.setAttribute("pageNum","3003");
  	   
     }     
   
   private void commonBackOfficeInitParameters(HttpServletRequest req,BackOfficeDelegate backOfficeDelegate){
	   req.setAttribute("pageId",path);
	  
       req.setAttribute("date", backOfficeDelegate.getSysDate());
      
       req.setAttribute("Disable", true);
     
       req.setAttribute("vis","hidden");
       try{
    	   
       req.setAttribute("acctype",backOfficeDelegate.getMainModules(12));
     
       }
       catch(Exception e)
       {
    	   e.printStackTrace();
       }
      
   }
 
   public void clear(){
	   SIEntryForm siEntryform= new SIEntryForm();
	   siEntryform.setPriority_num(0);
  	   siEntryform.setFrom_ac_ty("");
  	   siEntryform.setFrom_ac_no("");
  	   siEntryform.setTo_ac_ty("");
  	   siEntryform.setTo_ac_no("");
  	   siEntryform.setLoan_option("");
  	   siEntryform.setDue_date("");
  	   siEntryform.setPeriod_mon("");
  	   siEntryform.setDays("");
  	   siEntryform.setAmount("");
  	   siEntryform.setNo_of_times_exec("");
  	   siEntryform.setLast_exec_date("");
  	   siEntryform.setExpiry_days("");
   }
   
   private List setSIParameters(BackOfficeDelegate backOfficeDelegate,HttpServletRequest req)
   {
	   List parameter_list=new ArrayList();
	   try{
	   	      
	   AdminObject[] adminObject=backOfficeDelegate.getDetails();
	   
	   for(int i=0;i<adminObject.length;i++){
		   Map map= new TreeMap(); 
		   map.put("id",i+1);
		   map.put("prioritynum",adminObject[i].getPriorityNo());
		   map.put("frm_acc_type",adminObject[i].getFromType());
		   map.put("fromDescription",adminObject[i].getFrTypeDesc());
		   map.put("to_acc_type",adminObject[i].getToType());
		   map.put("toDescription",adminObject[i].getToTypeDesc());
		  
		   parameter_list.add(map);   
	   }
	    
	    session=req.getSession(true); 
		session.setAttribute("Details",parameter_list);
		req.setAttribute("Details",parameter_list);
	   
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return parameter_list;
	   
   }
   
  private List setSIExecutionDetails(BackOfficeDelegate backOfficeDelegate,HttpServletRequest req)throws RemoteException,SQLException
  {
	  	int[] si_no=backOfficeDelegate.getInstnsForExec(backOfficeDelegate.getSysDate());
		req.setAttribute("SINO", si_no);
		String[][] execdetails=backOfficeDelegate.getStdInstRecords(si_no);
		System.out.println("The execution detils in action class is ==="+execdetails);
		ModuleObject[] mod=backOfficeDelegate.getMainModules(1);
         for(int i=0;i<si_no.length;i++){
			   Map map= new TreeMap(); 
			   map.put("id",i+1);
			   map.put("Instn_no",execdetails[i][0]);
			   System.out.println("fromaccc***"+execdetails[i][1]);
			   System.out.println("Module Object-------------"+mod);
			   if(mod!=null)
			   {
				System.out.println("Are you Here!!!!!!!!!!");
			   for(int j=0;j<mod.length;j++){
				   System.out.println("Value Of Mod Today Is--------"+mod[j]);
				   System.out.println("EXECDETAILS ---------"+execdetails[i][1]);
					if(execdetails[i][1]!=null && mod[j].getModuleCode().equals(execdetails[i][1].toString())){
						map.put("FromAcc",mod[j].getModuleAbbrv());
					}
			   }
			 
			   System.out.println("ToAcc***"+execdetails[i][4]);
			   for(int j=0;j<mod.length;j++){
					if(execdetails[i][4]!=null && mod[j].getModuleCode().equals(execdetails[i][4].toString())&&execdetails[i][4]!=null){
						map.put("ToAcc",mod[j].getModuleAbbrv());
					}
			   }
			   map.put("to_Ac_No",execdetails[i][5]);
			   map.put("to_Name",execdetails[i][6]);
			   map.put("Amount",execdetails[i][7]);
			   //map.put("Reason","About to execute");
			   execution_list.add(map);   
		   }
         }
		    session=req.getSession(true);
			session.setAttribute("ExecutionDetails",execution_list);
			req.setAttribute("executionDetails",execdetails);
		    return execution_list;
}
 private List setSIExecutionDoneDetails(BackOfficeDelegate backOfficeDelegate,HttpServletRequest req) throws RemoteException,SQLException
 {
	 execution_list.clear();
	 String use_id= req.getParameter("id");
	 req.setAttribute("id", req.getParameter("id"));
			
	 List executed_list=new ArrayList();
	 SIDoneObject[] sidoneobject=backOfficeDelegate.getInstInfoForDone(backOfficeDelegate.getSysDate(), backOfficeDelegate.getSysDate(), null);
	 //req.setAttribute("executeddetails",sidoneobject );
	 if(sidoneobject!=null)
	 {
	 for(int i=0;i<sidoneobject.length;i++){
		   Map map= new TreeMap(); 
		   map.put("id",i+1);
		   map.put("Instn_no",sidoneobject[i].getSiNo());
		   System.out.println("fromaccc***"+sidoneobject[i].getFrommodAbbrv());
		   map.put("FromAcc",sidoneobject[i].getFrommodAbbrv());
		   map.put("from_Ac_No",sidoneobject[i].getFromAccNo());
		   map.put("from_Name",sidoneobject[i].getFromaccholdername());
		   System.out.println("ToAcc***"+sidoneobject[i].getTomodAbbrv());
		   map.put("ToAcc",sidoneobject[i].getTomodAbbrv());
		   map.put("to_Ac_No",sidoneobject[i].getToAccNo());
		   map.put("to_Name",sidoneobject[i].getToaccholdername());
		   map.put("Amount",sidoneobject[i].getTrnAmt());
		   map.put("Reason","Executed");
		   executed_list.add(map);   
	   }
	 }else
	 {
		 req.setAttribute("displaymsg", "There Are No Standing Instructions to be executed!!!");
	 }
	    session=req.getSession(true);
		session.setAttribute("ExecutionDetails",executed_list);
		req.setAttribute("ExecutionDetails",executed_list);
	    return executed_list;

 }
private List setPaymentVoucherDetails(BackOfficeDelegate backOfficeDelegate,HttpServletRequest req) {
	   
	 List paymentvoucherlist=new ArrayList();
	 PaymentVoucherForm paymentvoucherform=new PaymentVoucherForm();
	 
	 for(int i=0;i<paymentvoucherlist.size();i++){
		 Map map= new TreeMap(); 
		   map.put("id",i+1);
		   map.put("acc_type","");
		   map.put("acc_no","");
		   map.put("name","");
		   map.put("gltype",paymentvoucherform.getGltype());
		   map.put("glcode",paymentvoucherform.getGlcode());
		   map.put("glname",paymentvoucherform.getGldesc());
		   map.put("trn_type","");
		   map.put("amount",paymentvoucherform.getDebitamt());
	       paymentvoucherlist.add(map);
		 
	 }
     session=req.getSession(true);
	 session.setAttribute("PaymentVoucherDetails",paymentvoucherlist);
	 req.setAttribute("PaymentVoucherDetails",paymentvoucherlist);
	 
	 return paymentvoucherlist;
}
private List setTransferVoucherDetails(BackOfficeDelegate backOfficeDelegate,HttpServletRequest req) {
     List transfervoucherlist=new ArrayList();
	 TransferVoucherForm transfervoucherform=new TransferVoucherForm();
	 	 for(int i=0;i<transfervoucherlist.size();i++){
		 Map map= new TreeMap(); 
		   map.put("id",i+1);
		   map.put("acc_type","");
		   map.put("acc_no","");
		   map.put("name","");
		   map.put("gltype","");
		   map.put("glcode","");
		   map.put("glname","");
		   map.put("trn_type","");
		   map.put("amount","");
		   transfervoucherlist.add(map);
		}
	 session=req.getSession(true);
	 session.setAttribute("TransferVoucherDetails",transfervoucherlist);
	 req.setAttribute("TransferVoucherDetails",transfervoucherlist);
     return transfervoucherlist;
}
public void MiscellUVScrollNos(TransferVoucherForm transfervchform,BackOfficeDelegate bkDelegate,HttpServletRequest request)
{
	try{
	VoucherDataObject[] voucherDataDetails;
	voucherDataDetails = bkDelegate.VoucherData("T",transfervchform.getVchnum());
	System.out.println("The voucher detail value is------"+voucherDataDetails);
	if(voucherDataDetails!=null){
	System.out.println("The Account Type is========"+voucherDataDetails[0].getModuleAccountType());
	request.setAttribute("vchdataobject", voucherDataDetails);
	}
	else 
	{
		 request.setAttribute("displaymsg","Invalid Voucher Number");	

		System.out.println("Invalid Voucher Number");
	    	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	

	
  
}

}
  
