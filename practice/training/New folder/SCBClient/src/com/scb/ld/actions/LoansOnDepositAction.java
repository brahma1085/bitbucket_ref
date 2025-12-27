package com.scb.ld.actions;
import exceptions.InterestCalculation;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.loansOnDeposit.LoanMasterObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.loansOnDeposit.LoanTransactionObject;
import masterObject.pygmyDeposit.PygmyTransactionObject;
import masterObject.termDeposit.DepositMasterObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.CustomerDelegate;
import com.scb.designPatterns.LDDelegate;
import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.designPatterns.exceptions.ServiceLocatorException;
import com.scb.designPatterns.exceptions.SystemException;
import com.scb.ld.forms.IntrestAccruedReportForm;
import com.scb.ld.forms.LDExcedingMatListForm;
import com.scb.ld.forms.LDMaturityListForm;
import com.scb.ld.forms.LDOpenClosedForm;
import com.scb.ld.forms.LDOpenClosedMenuForm;
import com.scb.ld.forms.LDPassbookForm;
import com.scb.ld.forms.LDPassbookMenuForm;
import com.scb.ld.forms.LDRecoveryForm;
import com.scb.ld.forms.LDRecoveryMenuForm;
import com.scb.ld.forms.LoanApplicationDE;
import com.scb.props.MenuNameReader;
    
public class LoansOnDepositAction extends Action {
	private String path;
	private ModuleObject array_transfer_moduleobject[],array_deposit_moduleobject[],array_ln_moduleobject[];
	private LoanPurposeObject lnpurobj[];
	private String[] str;
	DepositMasterObject ac;                
	private String sub_modcode;
	private HttpSession session,sessions; 
	String lduser,ldtml;
	AdministratorDelegate admDelegate;
	Map user_role;
	double array_double_interest_rate[],double_interest_rate=0;
	private double double_ln_percentage;
	LoanMasterObject loanmasterobject,loanmastobj=null;
	LoanTransactionObject loantranobj=null;
	LoanReportObject loanreportobject=null,ln_last_details=null;
	LoanReportObject repobj_exce2[]=null;
	LoanReportObject reportobject[];
	Hashtable hash_obj=new Hashtable();
	          
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception
	{ 
		sessions=req.getSession();
	    lduser=(String)sessions.getAttribute("UserName");
    	ldtml=(String)sessions.getAttribute("UserTml") ;
    	try{
    		synchronized(req) {
				if(lduser!=null){
					admDelegate=new AdministratorDelegate();
					user_role=admDelegate.getUserLoginAccessRights(lduser,"LD");
					if(user_role!=null){
						req.setAttribute("user_role",user_role);
						if(req.getParameter("pageId")!=null){
							System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageId"));
							req.setAttribute("accesspageId",req.getParameter("pageId").trim());
						}
					}else{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Sorry, You do not have access to this page",req,path);
						return map.findForward(ResultHelp.getError());
					}
				}
    			}
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    		           
		System.out.println("==================Princess======================");
	System.out.println("map.getPath()<=====><====><=======>"+map.getPath()); 

	if(map.getPath().equalsIgnoreCase("/LoansOnDepositMenuLink"))
	{
		System.out.println("******Inside LoansOnDeposit Menulink********");
		try{
			LDDelegate lddeligate=new LDDelegate(); 
			LoanApplicationDE applicationform=(LoanApplicationDE)form;
			String value=applicationform.getValue();
			req.setAttribute("PageValue",value);
			getdate(lddeligate, req);
			applicationform.setLoanacnum("null");
			applicationform.setLoanacc_notfound("null");
			applicationform.setLoanacc_closed("null");
			applicationform.setLoan_acc_notver("null");
			applicationform.setAcc_notfound("null");
			applicationform.setMatdate_elapsed("null");  
			applicationform.setLoan_acc_num(0);
			applicationform.setLoan_acno_update(0);
			applicationform.setAdditional_loan(false);
			if(MenuNameReader.containsKeyScreen(applicationform.getPageId()))
			{
				InitialParameter(lddeligate,req);	
				path=MenuNameReader.getScreenProperties(applicationform.getPageId());
				req.setAttribute("pageId",path);
				//req.setAttribute("Description","null");//attributes are set just to avoid null pointer exception
				req.setAttribute("DepositAmmount",0.0 );
				req.setAttribute("CurrentIntRate",0.0);
				req.setAttribute("AmmountEligable",0.0);
				req.setAttribute("Pygmyamount",0.0);
				req.setAttribute("Maturityamount",0.0);
				req.setAttribute("applsrlnum",0);//int
				req.setAttribute("reqamounts",0.0);//double
				req.setAttribute("depaccnum",0);//int
				req.setAttribute("sanctionamt",0.0);//double
				req.setAttribute("principlebal",0.0);//double
				

				req.setAttribute("Combo_PayMode","false");
				
				
				return map.findForward(ResultHelp.getSuccess()); 
			}
			else
			{
				
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
	    	
			}
			}catch(Exception e)
			{
				//e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());	
			}
		
		}
	   
	else if(map.getPath().equalsIgnoreCase("/LoansOnDeposit/ApplicationDE")){
		int ref_no=0;
		LoanApplicationDE applicationDe=(LoanApplicationDE)form;
		String button_value=applicationDe.getForward();
		String value=applicationDe.getValue(); 
		req.setAttribute("PageValue",value);
		applicationDe.setLoan_acno_update(0);    
		LDDelegate lddeligate=new LDDelegate();
		String addition_value=applicationDe.getAdd_val();
		String update_value=applicationDe.getUpdate_value();
		applicationDe.setLoanacc_notfound("null"); 
		applicationDe.setLoanacc_closed("null");
		applicationDe.setLoan_acc_notver("null");
		applicationDe.setLoan_acc_notver("null");
		applicationDe.setAcc_notfound("null");
		applicationDe.setMatdate_elapsed("null");
		applicationDe.setLoanacnum("null");
		applicationDe.setLoan_acc_num(0);
		applicationDe.setLoan_acno_update(0);
		applicationDe.setAdditional_loan(false);
		if(applicationDe.getTxt_reftype()!=null)
		  ref_no=Integer.valueOf(applicationDe.getTxt_reftype());
		String ac_type=array_deposit_moduleobject[ref_no].getModuleCode();
   	  	int ac_num=applicationDe.getTxt_refno(); 
		req.setAttribute("TabNum", "0");
		int Ref_deposit_num=applicationDe.getTxt_refno();
		getdate(lddeligate,req);
		getTabbedpane(req,applicationDe);
		req.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
		String paymode=applicationDe.getTxt_paymode();
		if(paymode!=null){
			if(paymode.equalsIgnoreCase("Cash")){
				req.setAttribute("Combo_PayMode","Cash");
		    }else if(paymode.equalsIgnoreCase("Transfer")||(paymode.equalsIgnoreCase("PayOrder"))){
		    	req.setAttribute("Combo_PayMode","Transfer");
		    	req.setAttribute("Combo_PayMode","PayOrder");
		    }   
		}
		showloantype(applicationDe,lddeligate,req);
		AccountDetail(lddeligate,applicationDe,req);
		ac=lddeligate.getDepositMaster(ac_type,ac_num); 
		if(ac!=null){	    
			if(ac.getClosedt()!=null) 
				applicationDe.setAccountclosed("accountclosed");
			if(ac.getVerified()==null)
				applicationDe.setLoan_acc_notver("LoanAccNotVerified");
			if(ac_type!=null){
				if(!ac_type.startsWith("1006")){
					int date=Validations.dayCompare(ac.getMaturityDate(),lddeligate.getSysDate());
					if(date>=0){
						applicationDe.setMatdate_elapsed("MatedateElapsed");
					}
				}
				if(!ac_type.startsWith("1006") && ac.getMaturityDate().equals("Elapsed")){
					req.setAttribute("Acc_closed","Elapsed");
					req.setAttribute("Loanacnum",0);	
					req.setAttribute("recov_num",0);
					req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
					return map.findForward(ResultHelp.getSuccess()); 
				}
			}
			if(ac.getLoanAvailed()!=null)
			if(ac.getLoanAvailed().equals("T")){
				if(ac.getLoanAccno()==0 && applicationDe.getValue().equalsIgnoreCase("1")){
					applicationDe.setLoan_acc_notver("LoanAccNotVerified");
				}else if(applicationDe.getFlag_num() == 1 && applicationDe.getTxt_refno()!=0){
					applicationDe.setAdditional_loan(true);  
					applicationDe.setFlag_num(0);
				}
			}
		}
		if(applicationDe.getTxt_refno()!=0 && applicationDe.getTxt_loanaccno()==0){      
			DepositMasterObject dep_mast=getModuleCode(lddeligate,req,applicationDe,applicationDe.getTxt_refno());
			AccountDetail(lddeligate,applicationDe,req);
			if(dep_mast==null){// for setting Amount Eligible for pygmy accounts
				applicationDe.setAcc_notfound("AccountNotFound");
				req.setAttribute("AccountNumber",0);
				req.setAttribute("Maturityamount",0.0);
				req.setAttribute("Intrestrate",0.0);
				req.setAttribute("deposit_days",0);
				req.setAttribute("DepositAmmount",0.0);
				req.setAttribute("CurrentIntRate",0.0);
				req.setAttribute("AmmountEligable",0.0);
				req.setAttribute("Pygmyamount",0.0);
				req.setAttribute("reqamounts",0.0);
				req.setAttribute("depaccnum",0);//int
				req.setAttribute("applsrlnum",0);//int
			}else{	
				if(ac_type!=null){	
					if(ac_type.startsWith("1006")){
						getModuleCode(lddeligate,req,applicationDe,applicationDe.getTxt_refno());
						double pygamount=getPygmyAmountDrawable(applicationDe,lddeligate);
						req.setAttribute("AmmountEligable",pygamount); 
						if(applicationDe.getTxt_reqammount()!=0){	
							double pygmy_rate=lddeligate.getPygmyCurrentIntRate(array_ln_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode(),lddeligate.getSysDate(),applicationDe.getTxt_reqammount(),ac_num);
							req.setAttribute("CurrentIntRate",pygmy_rate);
						}	 
						req.setAttribute("button_update","SUBMIT");
					}else{	 
						req.setAttribute("button_update","SUBMIT"); 
						applicationDe.setLoanacc_notfound("null");
						applicationDe.setLoanacc_closed("null");
						if(applicationDe.getAdd_val().equalsIgnoreCase("yes")){
							double deposit_ammount=getDepositAmountDrawable(applicationDe,lddeligate,applicationDe.getTxt_refno(),req);
							req.setAttribute("AmmountEligable",deposit_ammount); 
						}else{
							double deposit_ammount=getDepositAmountDrawable(applicationDe,lddeligate,applicationDe.getTxt_refno(),req);
							if(loanmasterobject!=null)
								deposit_ammount+=loanmasterobject.getSanctionedAmount();
							req.setAttribute("AmmountEligable",deposit_ammount);
						}
						req.setAttribute("reqamounts",0.0);
						req.setAttribute("depaccnum",0);//int
						req.setAttribute("applsrlnum",0);//int
					}
				}	
			}
		}else if(applicationDe.getTxt_refno()==0 && applicationDe.getTxt_loanaccno()==0){
			req.setAttribute("AccountNumber",0);
			req.setAttribute("Maturityamount",0.0);
			req.setAttribute("Intrestrate",0.0);
			req.setAttribute("deposit_days",0);
			req.setAttribute("DepositAmmount",0.0);
			req.setAttribute("CurrentIntRate",0.0);
			req.setAttribute("AmmountEligable",0.0);
			req.setAttribute("Pygmyamount",0.0);
			req.setAttribute("reqamounts",0.0);
			req.setAttribute("depaccnum",0);//int
			req.setAttribute("applsrlnum",0);//int
		}
		if(applicationDe.getTxt_loanaccno()!=0 && value.equalsIgnoreCase("1")){	
			loanAccountdetail(applicationDe,req,lddeligate,map);
			req.setAttribute("button_update","UPDATE");
			loanmasterobject=lddeligate.getLoanMaster(applicationDe.getTxt_LoanActype(),applicationDe.getTxt_loanaccno());
			if(loanmasterobject!=null){	  
				showDetail(applicationDe,loanmasterobject.getAccType(),loanmasterobject.getAccNo(),lddeligate,req,1,map);
			}
		}else if(applicationDe.getTxt_loanaccno()!=0 && value.equalsIgnoreCase("2")){ 
			try{
				req.setAttribute("button_update","VERIFY");
				loanmasterobject=lddeligate.getLoanMaster(applicationDe.getTxt_LoanActype(),applicationDe.getTxt_loanaccno());
				if(loanmasterobject!=null){	   
					showDetail(applicationDe,loanmasterobject.getAccType(),loanmasterobject.getAccNo(),lddeligate,req,0,map);
				}else{	
					applicationDe.setLoanacc_notfound("NotFound");
					req.setAttribute("reqamounts",0.0);//double
					req.setAttribute("depaccnum",0);//int
					req.setAttribute("applsrlnum",0);//int
					req.setAttribute("sanctionamt",0.0);//double
					req.setAttribute("principlebal",0.0);//double
					req.setAttribute("CurrentIntRate",0.0);//double
					req.setAttribute("AmmountEligable",0.0);
				}
			}catch(Exception e){
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e,req,path);
				return map.findForward(ResultHelp.getError());
			}
		}else if(applicationDe.getTxt_loanaccno()==0 && applicationDe.getValue()!=null && applicationDe.getValue().equalsIgnoreCase("2")){
			req.setAttribute("reqamounts",0.0);
			req.setAttribute("depaccnum",0);//int
			req.setAttribute("applsrlnum",0);//int
		}
		if(applicationDe.getTabPaneHeading()!=null){
			if(applicationDe.getTabPaneHeading().trim().equalsIgnoreCase("DepositDetail")){
				req.setAttribute("TabNum", "1");
			}
		}
		if(addition_value!=null){	
			if(applicationDe.getTxt_refno()!=0 && addition_value.equalsIgnoreCase("yes") && update_value.equalsIgnoreCase("Update_LoanDet")){  
				ac=lddeligate.getDepositMaster(ac_type,ac_num);
				loanmasterobject=lddeligate.getLoanMaster(ac.getLoanAcType(),ac.getLoanAccno());
				if(ac!=null){   
					req.setAttribute("PageValue","2");
					applicationDe.setTxt_loanaccno(ac.getLoanAccno());
					showDetail(applicationDe,ac.getLoanAcType(),ac.getLoanAccno(),lddeligate,req,0,map);
					req.setAttribute("button_update","SUBMIT");
					if(button_value!=null){	
						if(button_value.equalsIgnoreCase("submit")){
							getscreendetail_updation(applicationDe,loanmasterobject);
							loanmasterobject.setCustomerId(ac.getCustomerId());
							loanmasterobject.uv.setUserDate(applicationDe.getTxt_appdate());
							int storeLM=lddeligate.storeLoanMaster(loanmasterobject,0);
							applicationDe.setLoan_acc_num(storeLM);
						}
					}
				}
			}
		}
		if(button_value!=null){//code for submitting the values
			if(button_value.equalsIgnoreCase("submit")){
				if(applicationDe.getTxt_loanaccno()==0){
					try{	
						getScreenDetails(applicationDe);
						loanmasterobject.setCustomerId(ac.getCustomerId());
						loanmasterobject.uv.setUserDate(applicationDe.getTxt_appdate());
						int storeLM=lddeligate.storeLoanMaster(loanmasterobject,0);
						applicationDe.setLoan_acc_num(storeLM);
						applicationDe.setLoan_acno_update(0);
						req.setAttribute("Loanacnum",storeLM);
						req.setAttribute("recov_num",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
						
						return map.findForward(ResultHelp.getSuccess()); 
					}catch(Exception e){
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements(""+e,req,path);
						e.printStackTrace();
						return map.findForward(ResultHelp.getError());
					}
				}
			}else if(button_value.equalsIgnoreCase("Verify")){  
				try{  
					if(loanmasterobject.getPayMode().equalsIgnoreCase("T"))
						loanmasterobject.setPayMode("T"); 
			        loanmasterobject.setDepositAccType(array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode());
			        loanmasterobject.setAccType(applicationDe.getTxt_LoanActype());
			        //loanmasterobject.setPaymentAcctype(acctype)
				    loanmasterobject.uv.setVerId(lduser);
					loanmasterobject.uv.setVerTml(ldtml);
					loanmasterobject.uv.setVerDate(LDDelegate.getSysDate());
					int d=lddeligate.verifyLoansOnDeposit(loanmasterobject,LDDelegate.getSysDate());	
					if(d>=1){
						if(loanmasterobject.getPayMode().equals("C")){	
							req.setAttribute("Verification",String.valueOf(d));
							req.setAttribute("Loanacnum",0);
							req.setAttribute("recov_num",0);
							req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
							return map.findForward(ResultHelp.getSuccess());
						}else if(loanmasterobject.getPayMode().equals("P")){
							req.setAttribute("Verification",String.valueOf(d));
							req.setAttribute("Loanacnum",0);
							req.setAttribute("recov_num",0);
							req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
							return map.findForward(ResultHelp.getSuccess());
						}else{
							req.setAttribute("Verification",String.valueOf(d));
							req.setAttribute("Loanacnum",0);
							req.setAttribute("recov_num",0);
							req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
							return map.findForward(ResultHelp.getSuccess());
						}
					}
				}catch (Exception e){
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(""+e,req,path);
					return map.findForward(ResultHelp.getError());
				}		
			}else if(button_value.equalsIgnoreCase("UPDATE")){
				try{
					getScreenDetails(applicationDe);
					loanmasterobject.setCustomerId(ac.getCustomerId());
					loanmasterobject.uv.setUserDate(applicationDe.getTxt_appdate());
					int storeLM=lddeligate.storeLoanMaster(loanmasterobject,0);
					applicationDe.setLoan_acno_update(storeLM);
					applicationDe.setLoanacnum("null");
					applicationDe.setLoan_acc_notver("null");
				}catch (Exception e) {
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(""+e,req,path);
					return map.findForward(ResultHelp.getError());
				}
			}else if(button_value.equalsIgnoreCase("delete")){	
				if(applicationDe.getTxt_loanaccno()!=0 && applicationDe.getTxt_LoanActype()!=null){	
					int dele_loanac=lddeligate.deleteAccount(applicationDe.getTxt_loanaccno(),applicationDe.getTxt_LoanActype(),applicationDe.getTxt_refno(),array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode());
					if(dele_loanac==1){	
						req.setAttribute("DeleteSucess","DeleteSucess");
						req.setAttribute("Loanacnum",0);
						req.setAttribute("recov_num",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
						return map.findForward(ResultHelp.getSuccess());
					}else{	
						req.setAttribute("DeleteSucess","DeleteUNSucess");
						req.setAttribute("Loanacnum",0);
						req.setAttribute("recov_num",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
						return map.findForward(ResultHelp.getSuccess());
					}	
				}
			}
		}
		if(MenuNameReader.containsKeyScreen(applicationDe.getPageId())){ 
			InitialParameter(lddeligate,req);
			path=MenuNameReader.getScreenProperties(applicationDe.getPageId());
			req.setAttribute("pageId",path);
			return map.findForward(ResultHelp.getSuccess()); 
		}else{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}//END OF LOAN APPLICATION-DE PATH 
	else if(map.getPath().equalsIgnoreCase("/LDRecoveryMenuLink"))
	{
		LDRecoveryMenuForm LDmenuform=(LDRecoveryMenuForm)form;
		LDDelegate lddeligate=new LDDelegate();
		String Page_value=LDmenuform.getValue();
		System.out.println("Valllls===>"+LDmenuform.getValue());
		req.setAttribute("Page_value",Page_value);
	
		//attributes set just to avoid null pointer exception
		req.setAttribute("depamt",0.0);
		req.setAttribute("matammount",0.0);
		req.setAttribute("sancamt",0.0);
		req.setAttribute("reptnum",0);

		if(MenuNameReader.containsKeyScreen(LDmenuform.getPageId()))
		{
			InitialParameter(lddeligate,req);	
			path=MenuNameReader.getScreenProperties(LDmenuform.getPageId());
			req.setAttribute("pageId",path);
			req.setAttribute("depamt",0.0);
			req.setAttribute("matammount",0.0);
			req.setAttribute("sancamt",0.0);
			req.setAttribute("reptnum",0);
			req.setAttribute("LoanBalance",0.0);
			req.setAttribute("Interestpaid",0.0);
			req.setAttribute("loanbalance",0.0);
			req.setAttribute("amtpaid",0.0);
			req.setAttribute("intamt",0.0);
			req.setAttribute("intrate",0.0);
			req.setAttribute("cashprinciple",0.0);
			req.setAttribute("cashtotalamt",0.0);
			req.setAttribute("cashint_rate",0.0);
		
			//LDmenuform.setButton("");
			return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
		
	}
	else if(map.getPath().equalsIgnoreCase("/LDRecovery"))
	{
		try 
		{  
			System.out.println("*************Inside Recovery***************");
			LDRecoveryForm recoveryform=(LDRecoveryForm)form;
			LDDelegate lddeligate=new LDDelegate();
			double calamt=0.0;
			double intpayable=0.0;
			double prin_amt=0.0;
			LoanReportObject lnrepObj=null;
			recovery_lndesc(lddeligate,recoveryform,req);
			
			req.setAttribute("TabNum","0");
			req.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
			getrecovery_Tabbedpane(req,recoveryform);
			String button_values=recoveryform.getButton();
			System.out.println("button_values==========>"+button_values); 
			String Page_value=recoveryform.getValue();
			System.out.println("Value===?"+recoveryform.getValue());
			req.setAttribute("Page_value",Page_value);
			//LDCashForm ldcashform=recoveryform.getCashform();
			//req.setAttribute("Cashform",ldcashform)
			
			System.out.println("Voucher no in recovery ----------->"+recoveryform.getTxt_voucherno());
			if( recoveryform.getTxt_voucherno()!=0 && recoveryform.getCombo_loantype().equalsIgnoreCase("Select")&& recoveryform.getValue().equalsIgnoreCase("1") )
			{	
				
				System.out.println("**************inside verification vocher num***********");
				  
				LoanTransactionObject loan_tran_obj=lddeligate.getLoanTransaction(Integer.valueOf(recoveryform.getTxt_voucherno()));
 
				System.out.println("loan tran obj======>"+loan_tran_obj);
				if(loan_tran_obj!=null)
				{
				for(int i=0;i<array_ln_moduleobject.length;i++)
								{	 
									System.out.println(""+array_ln_moduleobject[i].getModuleCode()+"\n"+"loan transaction object===========>"+loan_tran_obj.getAccType());
									if(array_ln_moduleobject[i].getModuleCode().equals(loan_tran_obj.getAccType()))
									{
										System.out.println("index===========>====>"+i);
										
										String loan_acc_type=array_ln_moduleobject[i].getModuleAbbrv();
										
										req.setAttribute("loan_acc_num",String.valueOf(i));
										req.setAttribute("loan_acc_type",loan_acc_type);
										
										//System.out.println(""+loan_acc_type);
									} 
								}
				Recovery_verification(recoveryform, req,lddeligate);
				req.setAttribute("Button_value","VERIFY");
			   
			}
				else
				{
					req.setAttribute("displaymsg","Invalid Voucher Number");
				}
			}
			if(recoveryform.getTxt_tracNum()!=0)
			{	
				try
				{
					recovery_accountdetail(req,recoveryform,lddeligate);
					
				}catch(SQLException e)
				{
					e.printStackTrace();
					path=MenuNameReader.getScreenProperties("0000");
		            setErrorPageElements(""+e,req,path);
		            return map.findForward(ResultHelp.getError());
				}catch(Exception ex) {
					ex.printStackTrace();
					path=MenuNameReader.getScreenProperties("0000");
		            setErrorPageElements(""+ex,req,path);
		            return map.findForward(ResultHelp.getError());
				}
			}
			else
			{
				req.setAttribute("Accbal",0.0);
				
			}
			
			
			 if(recoveryform.getTabPaneHeading()!=null)
			{
				if(recoveryform.getTabPaneHeading().trim().equalsIgnoreCase("Status"))
				{
				System.out.println("Inside tabheading"); 
				
				req.setAttribute("TabNum","1");
				}
				if(recoveryform.getTabPaneHeading().trim().equalsIgnoreCase("Cash"))
				{
				System.out.println("Inside tabheading----------->>>"+recoveryform.getCashform());
				req.setAttribute("TabNum","2");
				}
				if(recoveryform.getTabPaneHeading().trim().equalsIgnoreCase("Transfer"))
				{
				System.out.println("Inside tabheading"); 	
				req.setAttribute("TabNum","3");
				}
			}
			if(recoveryform.getTxt_ammount()==0)
			{
				System.out.println("hiiiiiiiiiiiiiiiiii"); 
				req.setAttribute("cashprinciple",0.0);
				req.setAttribute("cashtotalamt",0.0);
				req.setAttribute("cashint_rate",0.0);
			}
			 
			if(recoveryform.getTxt_accno()!=0)   
			{
			 try{	
				 	LoanReportObject rep_obj=recovery_showdetail(lddeligate,recoveryform,req);
				 	
				 	if(rep_obj==null)
				 	{
				 		
				 		req.setAttribute("AccountNotFound","AccountNotFound");
				 		req.setAttribute("recov_num",0);
						req.setAttribute("Loanacnum",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
						return map.findForward(ResultHelp.getSuccess());
				 	}
				 	
				 	
				 	
			    }catch(SQLException e)
				{
			    	e.printStackTrace();
					path=MenuNameReader.getScreenProperties("0000");
		            setErrorPageElements(""+e,req,path);
		            return map.findForward(ResultHelp.getError());
				}catch(Exception ex) {
					ex.printStackTrace();
					path=MenuNameReader.getScreenProperties("0000");
		            setErrorPageElements(""+ex,req,path);
		            return map.findForward(ResultHelp.getError());
				}
			}
			else
			{
			req.setAttribute("depamt",0.0);
			req.setAttribute("matammount",0.0);
			req.setAttribute("sancamt",0.0);
			req.setAttribute("reptnum",0);
			req.setAttribute("LoanBalance",0.0);
			req.setAttribute("Interestpaid",0.0);
			req.setAttribute("loanbalance",0.0);
			req.setAttribute("amtpaid",0.0);
			req.setAttribute("intamt",0.0);
			req.setAttribute("intrate",0.0);
			req.setAttribute("cashprinciple",0.0);
			req.setAttribute("cashtotalamt",0.0);
			req.setAttribute("cashint_rate",0.0);
			}
			if(recoveryform.getTxt_voucherno()!=0){
				req.setAttribute("buttonvalue", "verify");
			}
			if(button_values!=null)
			{ 
				if(recoveryform.getTxt_voucherno()==0){
				if(button_values.equalsIgnoreCase("submit") && recoveryform.getCombo_loantype()!=null && recoveryform.getTxt_accno()!=0 && recoveryform.getTxt_tranfrom()!=null && recoveryform.getTxt_tracNum()!=0 && recoveryform.getTxt_entAmo()!=0)
				{
					System.out.println("-----------------********inside testing********----------");
					int value=lddeligate.loanRecovery(array_ln_moduleobject[Integer.valueOf(recoveryform.getCombo_loantype())].getModuleCode(),recoveryform.getTxt_accno(),array_transfer_moduleobject[Integer.valueOf(recoveryform.getTxt_tranfrom())].getModuleCode(),recoveryform.getTxt_tracNum(),recoveryform.getTxt_entAmo(),lduser,ldtml);
					if(value!=0)
					{	
					System.out.println("Recovery value=================>"+value);
					req.setAttribute("displaymsg","Voucher Number"    +value+   "genrated sucessfully");
					req.setAttribute("Loanacnum",0);
					req.setAttribute("pageId",MenuNameReader.getScreenProperties("6004"));
					
					return map.findForward(ResultHelp.getSuccess()); 
					}
					else  
					{
						req.setAttribute("recov_num",10);
						req.setAttribute("Loanacnum",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6004"));
						return map.findForward(ResultHelp.getSuccess()); 
					}
				}
				}
				System.out.println("BUTTON VALUE___________"+button_values);
				if(button_values.equalsIgnoreCase("VERIFY") && recoveryform.getTxt_voucherno()!=0 && recoveryform.getValue().equalsIgnoreCase("2") )
				{  
					System.out.println("*****************Inside Verify*********************");
					int vocher_num=lddeligate.verifyLoanTransaction(Integer.valueOf(recoveryform.getTxt_voucherno()),lduser,ldtml,LDDelegate.getSysDate(),LDDelegate.getSysTime());
					System.out.println("vocher number generated after verification=======>"+vocher_num);
					if(vocher_num!=0)
						req.setAttribute("displaymsg","Voucher Number verified sucessfully");
					if(vocher_num>=1) 
					{ 
						req.setAttribute("vocher_num",recoveryform.getTxt_voucherno());
						req.setAttribute("recov_num",0);
						req.setAttribute("Loanacnum",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6004"));
						return map.findForward(ResultHelp.getSuccess()); 
					}
				}
				
			} 
			
			if(MenuNameReader.containsKeyScreen(recoveryform.getPageId()))
			{
				InitialParameter(lddeligate,req);	
				path=MenuNameReader.getScreenProperties(recoveryform.getPageId());
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
			}
			else
			{
				/*path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);*/
				return map.findForward(ResultHelp.getError());
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements(""+e,req,path);
			return map.findForward(ResultHelp.getError());	
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDPassbookMenuLink")) 
	{
		try
		{
		System.out.println("**********Inside LD Passbook MenuLink*************");
		LDPassbookMenuForm passbookmenuform=(LDPassbookMenuForm)form; 
		LDDelegate deligate=new LDDelegate();
		InitialParameter(deligate,req);
		req.setAttribute("sanc_amt",0.0);//double
		req.setAttribute("Dep_acno",0);//int
		req.setAttribute("cid",0);//int
		req.setAttribute("phonenum",0);//int
		req.setAttribute("Nom_num",0);//int
		req.setAttribute("Int_rate",0.0);//double
		req.setAttribute("Loan_rate",0.0);//double
		req.setAttribute("period_in_days",0);//int
		req.setAttribute("Mat_amt",0.0);//double
		req.setAttribute("Dep_amt",0.0);//double
		if(MenuNameReader.containsKeyScreen(passbookmenuform.getPageId()))
		{
				
			path=MenuNameReader.getScreenProperties(passbookmenuform.getPageId());
			req.setAttribute("pageId",path);
			return map.findForward(ResultHelp.getSuccess());
		}
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
		}
		}catch(Exception e){
			e.printStackTrace();
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
			
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDPassbook")) 
	{
		try
		{
		System.out.println("**********Inside LD Passbook*************");
		LDPassbookForm passbookform=(LDPassbookForm)form;
		System.out.println("button values===============>"+passbookform.getButton_value());
		String button_value=passbookform.getButton_value();
		LDDelegate deligate=new LDDelegate();
		InitialParameter(deligate,req);
		if(button_value!=null)
		{		
		if(button_value.equalsIgnoreCase("VIEW")) 
		{	
			System.out.println("************Inside view************");
			
			if(passbookform.getTxt_acno()!=0) 
			{
				reportobject=passbookdesc(req,deligate,passbookform);
				System.out.println("loan rate^^^^^^^^^^^^^^^=========================>"+passbookform.getTxt_loanrate());
			
				if(reportobject==null) 
				{
					req.setAttribute("AccountNotFound","AccountNotFound");
					req.setAttribute("recov_num",0);
					req.setAttribute("Loanacnum",0);
					req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
					return map.findForward(ResultHelp.getSuccess());	
				}
			
			}
		}
		/*if(button_value.equalsIgnoreCase("FILE"))
		{	
			JFileChooser ff=new JFileChooser();
			int b=ff.showSaveDialog();
		
		}*/
		
		if(button_value.equalsIgnoreCase("save"))
		{	
			
			
				 System.out.println("Inside save ");
        		 //TO save to an excel Sheet
        		 res.setContentType("application/.csv");
        	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
        	        
        	        java.io.PrintWriter out = res.getWriter();
        	        out.print("\n");
        	        out.print("\n");
        	        out.print("\n");
        	        out.print(",");out.print(",");out.print(",");
        	        out.print("PassBook Details for A/C No: "+passbookform.getTxt_acno());
        	        out.print("\n");
        	        out.print("\n");
           		   
        	       
           		out.print("TranDate"); out.print(",");
           		out.print("ParticularNarr"); out.print(",");
           		out.print("Principal"); out.print(",");
           		out.print("Interest"); out.print(",");
           		out.print("Other"); out.print(",");
           		out.print("total"); out.print(",");
           		out.print("Debit"); out.print(",");
           		out.print("balance"); out.print(",");
           		out.print("\n");
           		reportobject=passbookdesc(req,deligate,passbookform);
           		//PygmyTransactionObject[] pgtrans = pgDelegate.getPygmyTransaction(passbookform.getPygtype(),Integer.parseInt(passbookform.getPygno()),passbookform.getFrom_date(), passbookform.getTo_date());
				if(reportobject!=null)
				{
					System.out.println("-------------" + reportobject);
					req.setAttribute("PassBook", reportobject);
				
				for(int k=0;k<reportobject.length;k++)
				{
					out.print(reportobject[k].getTransactionDate());
           			out.print(",");
           			//StringTokenizer st=new StringTokenizer(reportobject[k].getTranNarration(),",");
           			//out.print(st.nextToken()+" "+st.nextToken());
           			out.print(reportobject[k].getTranNarration());
           			out.print(",");
           			out.print(reportobject[k].getPrincipalPaid());
           			out.print(",");
           			out.print(reportobject[k].getInterestPaid()+reportobject[k].getExtraIntPaid());
           			out.print(",");
           			out.print(reportobject[k].getOtherAmt());
           			out.print(",");
           			out.print(reportobject[k].getInterestPaid()+reportobject[k].getExtraIntPaid()+reportobject[k].getPrincipalPaid()+reportobject[k].getOtherAmt());
           			out.print(",");
           			out.print(reportobject[k].getPrincipalPaid());
           			out.print(",");
           			out.print(reportobject[k].getLoanBalance());
           			out.print(",");
           			
           			out.print("\n");
				}
				}
				else {
					req.setAttribute("msg","NO Rows Found to download");
					}
			
				req.setAttribute("msg","Saved to excel file in C:");
    		    return null;
		}
		}
		else
		{
			req.setAttribute("sanc_amt",0.0);//double
			req.setAttribute("Dep_acno",0);//int
			req.setAttribute("cid",0);//int
			req.setAttribute("phonenum",0);//int
			req.setAttribute("Nom_num",0);//int
			req.setAttribute("Int_rate",0.0);//double
			req.setAttribute("Loan_rate",0.0);//double
			req.setAttribute("period_in_days",0);//int
			req.setAttribute("Mat_amt",0.0);//double
			req.setAttribute("Dep_amt",0.0);//double
		}
		if(MenuNameReader.containsKeyScreen(passbookform.getPageId()))
		{
				
			path=MenuNameReader.getScreenProperties(passbookform.getPageId());
			req.setAttribute("pageId",path);
			return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
		}
		}catch(Exception e){
			e.printStackTrace();
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
			
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDOpenClosedMenuLink"))
	{
	try{	
		System.out.println("*************LDOpenClosedMenuLink*************");
		LDOpenClosedMenuForm openclosedmenuform=(LDOpenClosedMenuForm)form;
		LDDelegate deligate=new LDDelegate();
		InitialParameter(deligate,req);
		req.setAttribute("FromDate",deligate.getSysDate());
		openclosedmenuform.setSysdate(LDDelegate.getSysDate());
		if(MenuNameReader.containsKeyScreen(openclosedmenuform.getPageId()))
		{
			path=MenuNameReader.getScreenProperties(openclosedmenuform.getPageId());
			req.setAttribute("pageId",path);
			return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
		}
	}catch(Exception e)
	{
		path=MenuNameReader.getScreenProperties("0000");
		setErrorPageElements("Path doest exit",req,path);
    	return map.findForward(ResultHelp.getError());
	}
		
	}
	else if(map.getPath().equalsIgnoreCase("/LDOpenClosedReports"))
	{
	 try{	
    	 	System.out.println("************LDOpenClosedReports************"); 
    	 	LDOpenClosedForm openclosedform=(LDOpenClosedForm)form;
    	 	String button=openclosedform.getButton_value();
    	 	System.out.println("button values===========>"+button); 
    	 	LDDelegate deligate=new LDDelegate();
			InitialParameter(deligate,req);
			req.setAttribute("FromDate",deligate.getSysDate());
			openclosedform.setSysdate(LDDelegate.getSysDate());
			
			System.out.println("acc type==============>"+openclosedform.getCombo_acctype());
			
			if(button!=null)
			{	
				if(button.equalsIgnoreCase("View"))
				{
					LoanReportObject[] repobj=openclosedrep(deligate,openclosedform,req);
					if(repobj==null)
					{
						req.setAttribute("AccountNotFound","NO Records Found!");
						/*req.setAttribute("recov_num",0);
						req.setAttribute("Loanacnum",0);*/
						req.setAttribute("pageId",path);
						return map.findForward(ResultHelp.getSuccess());
					}
				}
				
				if(button.equalsIgnoreCase("download")){
					System.out.println("I am in download=======");
					LoanReportObject[] reportobj=null;
					//reportobj=openclosedrep(deligate,openclosedform,req);
					
					if(openclosedform.getCombo_account().equalsIgnoreCase("open"))
					{	
						System.out.println("********inside open accounts**********");
						if(openclosedform.getCombo_acctype().equalsIgnoreCase("alltype"))
						{
						reportobj=deligate.getOpenedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),null,1);
						OpenClosedRepDet(reportobj,req);
						}
						else
						{   System.out.println("************inside the Open Individual***********");  
							reportobj=deligate.getOpenedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),array_ln_moduleobject[Integer.valueOf(openclosedform.getCombo_acctype())].getModuleCode(),0);
							OpenClosedRepDet(reportobj,req);
						}
					}
					else if(openclosedform.getCombo_account().equalsIgnoreCase("close"))
					{	
						System.out.println("********inside closed accounts**********");
						if(openclosedform.getCombo_acctype().equalsIgnoreCase("alltype"))
						{
						reportobj=deligate.getClosedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),null,1);
						OpenClosedRepDet(reportobj,req);
						
						}
						else
						{ 	System.out.println("************inside the Closed Individual***********");
							reportobj=deligate.getClosedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),array_ln_moduleobject[Integer.valueOf(openclosedform.getCombo_acctype())].getModuleCode(),0);
							OpenClosedRepDet(reportobj,req);
						}
					}
					if (reportobj == null) {
						openclosedform.setTesting("Cannot Print");
					} else {
						System.out.println(" i am inside down load");

						// TO save to an excel Sheet
						res.setContentType("application/.csv");
						res.setHeader("Content-disposition",
										"attachment;filename=OpenCloseReport.csv");

						java.io.PrintWriter out = res.getWriter();
						out.print("\n");
						out.print("\n");
						out.print("\n");
						out.print(",");
						out.print(",");
						out.print(",");
						out.print("OpenClose Account Details for A/C Type: "
								+ reportobj[0].getAccType());
						out.print("\n");
						out.print("\n");

						out.print("SrlNo");
						out.print(",");
						out.print("LoanAcNo");
						out.print(",");
						out.print("Name");
						out.print(",");
						out.print("DepositAcNo");
						out.print(",");
						out.print("DepDate");
						out.print(",");
						out.print("MatDate");
						out.print(",");
						out.print("Perioddays");
						out.print(",");
						out.print("Intrate");
						out.print(",");
						out.print("Amount");
						out.print(",");
						out.print("AmtAdv");
						out.print(",");
						out.print("LoanRate");
						out.print(",");
						out.print("SanDate");
						out.print(",");
						out.print("");
						out.print("\n");
						

						for (int k = 0; k < reportobj.length; k++) {
							out.print(k);
							out.print(",");
							out.print(reportobj[k].getAccNo());
							out.print(",");
							out.print(reportobj[k].getName());
							out.print(",");
							out.print(reportobj[k].getDepositAccNo());
							out.print(",");
							out.print(reportobj[k].getDepDate());
							out.print(",");
							out.print(reportobj[k].getMaturityDate());
							out.print(",");
							out.print(reportobj[k].getDepositDays());
							out.print(",");
							out.print(reportobj[k].getDepositIntRate());
							out.print(",");
							out.print(reportobj[k].getDepositAmt());
							out.print(",");
							out.print(reportobj[k].getSanctionedAmount());
							out.print(",");
							out.print(reportobj[k].getLoanIntRate());
							out.print(",");
							out.print(reportobj[k].getSanctionDate());
							out.print(",");
							out.print("\n");
						}

						req.setAttribute("msg","Saved to excel file in C:");
						return null;

					}
				
				}
					if(button.equalsIgnoreCase("Clear")){
					req.setAttribute("OpenClosed",null);
				}
			}
			if(MenuNameReader.containsKeyScreen(openclosedform.getPageId()))
			{
					path=MenuNameReader.getScreenProperties(openclosedform.getPageId());
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
			}
			else
			{
				//.path=MenuNameReader.getScreenProperties("0000");
				//setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
			}catch(Exception e)
			{
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
		    	return map.findForward(ResultHelp.getError());
			}
	} 
	else if(map.getPath().equalsIgnoreCase("/LDIntrestAccruedReportMenuLink"))
	{
		System.out.println("********Inside Intrest AccruedReport MenuLink*******");
		IntrestAccruedReportForm intform=(IntrestAccruedReportForm)form;
		LDDelegate deligate=new LDDelegate();
		InitialParameter(deligate,req);
		intform.setSysdate(LDDelegate.getSysDate());
		//req.setAttribute("SystemDate",deligate.getSysDate());
		if(MenuNameReader.containsKeyScreen(intform.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(intform.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDIntrestAccrued"))
	{
		System.out.println("********Inside Intrest Accrued**********");
		IntrestAccruedReportForm intform=(IntrestAccruedReportForm)form;
		LDDelegate deligate=new LDDelegate(); 
		//req.setAttribute("SystemDate",deligate.getSysDate());
		String button_value=intform.getButton_value(); 
		System.out.println("button values===========>"+button_value);
		InitialParameter(deligate,req);
		intform.setSysdate(LDDelegate.getSysDate());
		if(button_value!=null)
		{
			if(button_value.equalsIgnoreCase("View"))
			{
				 IntrestAccuredreport(deligate,intform,req);
			}
			
			
			if (button_value.equalsIgnoreCase("download")) {
				System.out.println("I am in download=======");
				LoanReportObject[] reportobj=null;
				IntrestAccuredreport(deligate,intform,req);
				if(intform.getCombo_acctype()!=null)
				{	
				 reportobj=deligate.getAccruedInterest(deligate.getSysDate(),array_ln_moduleobject[Integer.valueOf(intform.getCombo_acctype())].getModuleCode());
				req.setAttribute("IntrestAccured",reportobj);
				
				if (reportobj == null) {
					intform.setTesting("Cannot Print");
				} else {
					System.out.println(" i am inside down load");

					// TO save to an excel Sheet
					res.setContentType("application/.csv");
					res
							.setHeader("Content-disposition",
									"attachment;filename=InterestAccruedReport.csv");

					java.io.PrintWriter out = res.getWriter();
					out.print("\n");
					out.print("\n");
					out.print("\n");
					out.print(",");
					out.print(",");
					out.print(",");
					out.print("Intrest accrued Details for A/C Type: "
							+ reportobj[0].getAccType());
					out.print("\n");
					out.print("\n");

					out.print("Name");
					out.print(",");
					out.print("AccNo");
					out.print(",");
					out.print("DepAccNo");
					out.print(",");
					out.print("SancDate");
					out.print(",");
					out.print("SancAmt");
					out.print(",");
					out.print("IntAccured");
					out.print(",");
					out.print("IntPaid");
					out.print(",");
					out.print("ExtraIntPaid");
					out.print(",");
					out.print("IntUpToDate");
					out.print(",");
					out.print("SanctAmt");
					out.print(",");
					out.print("LoanBal");
					out.print(",");
					out.print("");
					out.print("\n");

					for (int k = 0; k < reportobj.length; k++) {
						/*out.print(k);
						out.print(",");*/
						out.print(reportobj[k].getName());
						out.print(",");
						out.print(reportobj[k].getAccNo());
						out.print(",");
						out.print(reportobj[k].getDepositAccNo());
						out.print(",");
						out.print(reportobj[k].getSanctionDate());
						out.print(",");
						out.print(reportobj[k].getSanctionedAmount());
						out.print(",");
						out.print(reportobj[k].getInterestAccrued());
						out.print(",");
						out.print(reportobj[k].getInterestPaid());
						out.print(",");
						out.print(reportobj[k].getExtraIntPaid());
						out.print(",");
						out.print(reportobj[k].getIntUptoDate());
						out.print(",");
						out.print(reportobj[k].getSanctionedAmount());
						out.print(",");
						out.print(reportobj[k].getLoanBalance());
						out.print(",");
						out.print("\n");
					}
					req
							.setAttribute("msg",
									"Saved to excel file in C:");
					return null;

				}
			}
			}
		}
		if(MenuNameReader.containsKeyScreen(intform.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(intform.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDMaturityListMenuLink"))
	{
		System.out.println("********LDMaturityListMenuLink**********"); 
		LDMaturityListForm maturityform=(LDMaturityListForm)form;
		LDDelegate deligate=new LDDelegate();
		InitialParameter(deligate,req);
		
		req.setAttribute("SystemDate",deligate.getSysDate());
		req.setAttribute("value",maturityform.getValue());
		maturityform.setSysdate(LDDelegate.getSysDate());
		if(MenuNameReader.containsKeyScreen(maturityform.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(maturityform.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDMaturityList"))
	{
		System.out.println("********LDMaturityList**********");
		LDMaturityListForm maturityform=(LDMaturityListForm)form;
		String button_valu=maturityform.getButton_value();
		LDDelegate deligate=new LDDelegate();
		System.out.println("button value=====>"+button_valu);
		InitialParameter(deligate,req);
		
		req.setAttribute("value",maturityform.getValue());
		req.setAttribute("SystemDate",deligate.getSysDate());
		maturityform.setSysdate(LDDelegate.getSysDate());
		if(button_valu!=null)
		{	
		if(button_valu.equalsIgnoreCase("View"))
		{	
			
			int valid_date=deligate.date_validation(maturityform.getTxt_fromdate(),maturityform.getTxt_todate());
			if(valid_date==1)
			{
				req.setAttribute("DateFun","CanNot");
				req.setAttribute("recov_num",0);
				req.setAttribute("Loanacnum",0);
				req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
				return map.findForward(ResultHelp.getSuccess());
			
			}
			else if(maturityform.getTxt_fromdate().equals(maturityform.getTxt_todate()))
			{
				req.setAttribute("NoRecords","NoRecords");
				req.setAttribute("recov_num",0);
				req.setAttribute("Loanacnum",0);
				req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
				return map.findForward(ResultHelp.getSuccess());
			
			}
			MaturityList(deligate,req,maturityform);
			
		}
		
		if(button_valu.equalsIgnoreCase("download")){
			
			System.out.println("I am in download=======");
			LoanReportObject[] repobj=null;
			if(maturityform.getCombo_acctype()!=null)
			{
				if(maturityform.getCombo_acctype().equalsIgnoreCase("alltype"))
				{
					System.out.println("from date=-=======>"+maturityform.getTxt_fromdate());
					System.out.println("To date======>"+maturityform.getTxt_todate());
					 repobj=deligate.getLDMaturityList(maturityform.getTxt_fromdate(),maturityform.getTxt_todate(),null,1);
					MatlistValues(repobj,req,1);
				}
				else 
				{
					System.out.println("*********inside else**********"); 
				 repobj=deligate.getLDMaturityList(maturityform.getTxt_fromdate(),maturityform.getTxt_todate(),array_ln_moduleobject[Integer.valueOf(maturityform.getCombo_acctype())].getModuleCode(),2);
					MatlistValues(repobj,req,1);
				}
			
			if (repobj == null) {
				maturityform.setTesting("Cannot Print");
			} else {
				System.out.println(" i am inside down load");

				// TO save to an excel Sheet
				res.setContentType("application/.csv");
				res.setHeader("Content-disposition","attachment;filename=MaturityListReport.csv");

				java.io.PrintWriter out = res.getWriter();
				out.print("\n");
				out.print("\n");
				out.print("\n");
				out.print(",");
				out.print(",");
				out.print(",");
				out.print("Maturity List Details for A/C Type: "
						+ repobj[0].getAccType());
				out.print("\n");
				out.print("\n");

				out.print("AcType");
				out.print(",");
				out.print("AccNo");
				out.print(",");
				out.print("Name");
				out.print(",");
				out.print("DepDate");
				out.print(",");
				out.print("MatDate");
				out.print(",");
				out.print("DepAmt");
				out.print(",");
				out.print("MatAmt");
				out.print(",");
				out.print("TranMode");
				out.print(",");
				out.print("TranNarr");
				out.print(",");
				out.print("IntPaidDate");
				out.print(",");
				out.print("LoanNumber");
				out.print(",");
				out.print("LoanIntRat");
				out.print(",");
				out.print("PrinBal");
				out.print(",");
				out.print("IntDue");
				out.print(",");
				out.print("TotalBala");
				out.print(",");
				out.print("");
				out.print("\n");

				for (int k = 0; k < repobj.length; k++) {
					/*out.print(k);
					out.print(",");*/
					out.print(repobj[k].getAccType());
					out.print(",");
					out.print(repobj[k].getAccNo());
					out.print(",");
					out.print(repobj[k].getName());
					out.print(",");
					out.print(repobj[k].getDepDate());
					out.print(",");
					out.print(repobj[k].getMaturityDate());
					out.print(",");
					out.print(repobj[k].getDepositAmt());
					out.print(",");
					out.print(repobj[k].getMaturityAmt());
					out.print(",");
					out.print(repobj[k].getTranMode());
					out.print(",");
					out.print(repobj[k].getTranNarration());
					out.print(",");
					out.print(repobj[k].getIntUptoDate());
					out.print(",");
					out.print(repobj[k].getAccNo());
					out.print(",");
					out.print(repobj[k].getLoanIntRate());
					out.print(",");
					out.print(repobj[k].getLoanBalance());
					out.print(",");
					out.print(repobj[k].getInterestPayable());
					out.print(",");
					out.print(repobj[k].getInterestPayable()+repobj[k].getLoanBalance());
					out.print(",");
					out.print("\n");
				}

				req.setAttribute("msg","Saved to excel file in C:");
				return null;

			}
		}
		}
		
		
		}
		
		if(MenuNameReader.containsKeyScreen(maturityform.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(maturityform.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}
	else if(map.getPath().equalsIgnoreCase("/LDMaturityExcedingMatListMenuLink"))
	{
	  try
	  {
		System.out.println("********LDMaturityExcedingMatListMenuLink**********");
		
		LDExcedingMatListForm excedingmatlist=(LDExcedingMatListForm)form;
		String button_valu=excedingmatlist.getButton_value();
		LDDelegate deligate=new LDDelegate();
		System.out.println("button value=====>"+button_valu);
		InitialParameter(deligate,req);
		req.setAttribute("SystemDate",deligate.getSysDate());
		excedingmatlist.setSysdate(LDDelegate.getSysDate());
		
		if(MenuNameReader.containsKeyScreen(excedingmatlist.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(excedingmatlist.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	  }catch(Exception e)
	  {
		e.printStackTrace();  
		return map.findForward(ResultHelp.getError());
	  }
	}
	else if(map.getPath().equalsIgnoreCase("/LDExceedMaturityList"))
	{
	 try
	 {
		System.out.println("********LDMaturityExcedingMatListMenuLink**********");
		LDExcedingMatListForm excedingmatlist=(LDExcedingMatListForm)form;
		String button_valu=excedingmatlist.getButton_value();
		LDDelegate deligate=new LDDelegate();
		System.out.println("button value=====>"+button_valu);
		InitialParameter(deligate,req);
		req.setAttribute("SystemDate",deligate.getSysDate());   
		excedingmatlist.setSysdate(LDDelegate.getSysDate());
		if(button_valu!=null)
		{
			if(button_valu.equalsIgnoreCase("view"))
			{
				System.out.println("am inside the view");
				LDExceedingMatList(req, excedingmatlist, deligate);
			}
			
		}
		if(button_valu!=null)
		{
		if(excedingmatlist.getButton_value().equalsIgnoreCase("download")){
			System.out.println("I am in download=======");
			LoanReportObject[] repobj_exce=null;
			
			if(excedingmatlist.getCombo_acctype()!=null)
			{
				if(excedingmatlist.getCombo_acctype().equalsIgnoreCase("alltype"))
				{
					 repobj_exce = deligate.getLDExceedingMaturity(excedingmatlist.getTxt_date1(),excedingmatlist.getTxt_date2(),null,1);
					System.out.println("repobj_exce=========>"+repobj_exce.toString());
					MatlistValues(repobj_exce,req,0);
				}
				else
				{
					System.out.println("*********inside else**********"); 
					repobj_exce=deligate.getLDExceedingMaturity(excedingmatlist.getTxt_date1(),excedingmatlist.getTxt_date2(),array_ln_moduleobject[Integer.valueOf(excedingmatlist.getCombo_acctype())].getModuleCode(),2);
					System.out.println("repobj2=========>"+repobj_exce.toString());
					MatlistValues(repobj_exce,req,0);
					
					}
				//}
			//LDExceedingMatList(req, excedingmatlist, deligate);
			if (repobj_exce == null) {
				excedingmatlist.setTesting("Cannot Print");
			} else {
				System.out.println(" i am inside down load");

				// TO save to an excel Sheet
				res.setContentType("application/.csv");
				res.setHeader("Content-disposition","attachment;filename=ExceedMaturityListReport.csv");
				java.io.PrintWriter out = res.getWriter();
				out.print("\n");
				out.print("\n");
				out.print("\n");
				out.print(",");
				out.print(",");
				out.print(",");
				out.print("Exceed Maturity List Details for A/C Type: "
						+ repobj_exce[0].getAccType());
				out.print("\n");
				out.print("\n");

				out.print("AcType");
				out.print(",");
				out.print("AccNo");
				out.print(",");
				out.print("Name");
				out.print(",");
				out.print("DepDate");
				out.print(",");
				out.print("MatDate");
				out.print(",");
				out.print("DepAmt");
				out.print(",");
				out.print("MatAmt");
				out.print(",");
				out.print("TranMode");
				out.print(",");
				out.print("TranNarr");
				out.print(",");
				out.print("IntPaidDate");
				out.print(",");
				out.print("LoanNumber");
				out.print(",");
				out.print("LoanIntRat");
				out.print(",");
				out.print("PrinBal");
				out.print(",");
				out.print("IntDue");
				out.print(",");
				out.print("TotalBala");
				out.print(",");
				out.print("");
				out.print("\n");

				for (int k = 0; k < repobj_exce.length; k++) {
					/*out.print(k);
					out.print(",");*/
					out.print(repobj_exce[k].getDepositAccType());
					out.print(",");
					out.print(repobj_exce[k].getDepositAccNo());
					out.print(",");
					out.print(repobj_exce[k].getName());
					out.print(",");
					out.print(repobj_exce[k].getDepDate());
					out.print(",");
					out.print(repobj_exce[k].getMaturityDate());
					out.print(",");
					out.print(repobj_exce[k].getDepositAmt());
					out.print(",");
					out.print(repobj_exce[k].getMaturityAmt());
					out.print(",");
					out.print(repobj_exce[k].getTranMode());
					out.print(",");
					out.print(repobj_exce[k].getTranNarration());
					out.print(",");
					out.print(repobj_exce[k].getIntUptoDate());
					out.print(",");
					out.print(repobj_exce[k].getAccNo());
					out.print(",");
					out.print(repobj_exce[k].getLoanIntRate());
					out.print(",");
					out.print(repobj_exce[k].getLoanBalance());
					out.print(",");
					out.print(repobj_exce[k].getInterestPayable());
					out.print(",");
					out.print(repobj_exce[k].getInterestPayable()+repobj_exce[k].getLoanBalance());
					out.print(",");
					out.print("\n");
				}

				req.setAttribute("displaymsg",	"Saved to excel file in C:");
				return null;

			}
		}
		}
	 }
		
		/*if(button_valu!=null)
		{	
		if(button_valu.equalsIgnoreCase("View"))
		{	
			int valid_date=deligate.date_validation(excedingmatlist.getTxt_fromdate(),excedingmatlist.getTxt_todate());
			if(valid_date==1)
			{
				req.setAttribute("DateFun","CanNot");
				req.setAttribute("recov_num",0);
				req.setAttribute("Loanacnum",0);
				req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
				return map.findForward(ResultHelp.getSuccess());
			
			}
			else if(excedingmatlist.getTxt_fromdate().equals(excedingmatlist.getTxt_todate()))
			{
				req.setAttribute("NoRecords","NoRecords");
				req.setAttribute("recov_num",0);
				req.setAttribute("Loanacnum",0);
				req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
				return map.findForward(ResultHelp.getSuccess());
			
			}
			
			//sweeeeeeeeeeeeeeeee
			LDExceedingMatList(req, excedingmatlist, deligate);
			
			
		}	
		}*/
		
		if(MenuNameReader.containsKeyScreen(excedingmatlist.getPageId()))
		{ 
				path=MenuNameReader.getScreenProperties(excedingmatlist.getPageId());
				System.out.println("pageid==========>"+path); 
				req.setAttribute("pageId",path);
				return map.findForward(ResultHelp.getSuccess());
		}
		else
		{
			//.path=MenuNameReader.getScreenProperties("0000");
			//setErrorPageElements("Path doest exit",req,path);
			return map.findForward(ResultHelp.getError());
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		return map.findForward(ResultHelp.getError());
	}
	
	}
	
	else if(map.getPath().equalsIgnoreCase("/AdditionalVerMenuLink"))
	{ 
		System.out.println("*************additional loan Verification*************");
		try{
			LoanApplicationDE addappl=(LoanApplicationDE)form;
			LDDelegate deligate=new LDDelegate(); 
			InitialParameter(deligate,req);
			getdate(deligate, req);
			getTabbedpane_additional(req,addappl);
			req.setAttribute("AccountNumber",0);
			req.setAttribute("Maturityamount",0.0);
			req.setAttribute("Intrestrate",0.0);
			req.setAttribute("deposit_days",0);
			req.setAttribute("DepositAmmount",0.0);
			req.setAttribute("CurrentIntRate",0.0);
			req.setAttribute("AmmountEligable",0.0);
			req.setAttribute("Pygmyamount",0.0);
			req.setAttribute("reqamounts",0.0);
			req.setAttribute("depaccnum",0);//int
			req.setAttribute("applsrlnum",0);//int 
			if(MenuNameReader.containsKeyScreen(addappl.getPageId()))
			{ 
					path=MenuNameReader.getScreenProperties(addappl.getPageId());
					System.out.println("pageid==========>"+path); 
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
			}
			else
			{
				//.path=MenuNameReader.getScreenProperties("0000");
				//setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		 }catch(Exception e)
		 {
			e.printStackTrace();
			return map.findForward(ResultHelp.getError());
		}
	}
	
	else if(map.getPath().equalsIgnoreCase("/AddLoanVerification")){
		try{ 
			LoanApplicationDE addappl=(LoanApplicationDE)form;
			LDDelegate deligate=new LDDelegate();
			InitialParameter(deligate,req);
			String paymode=addappl.getTxt_paymode();
			getdate(deligate,req);
			String button_value=addappl.getForward();
			if(paymode!=null){
			if(paymode.equalsIgnoreCase("Cash"))
			{
				req.setAttribute("Combo_PayMode","Cash");
			}
			else if(paymode.equalsIgnoreCase("Transfer")||(paymode.equalsIgnoreCase("PayOrder")))
			{
				req.setAttribute("Combo_PayMode","Transfer");
				req.setAttribute("Combo_PayMode","PayOrder");
			}
			}
			
			req.setAttribute("TabNum", "0");
           
			req.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
			
			getTabbedpane_additional(req,addappl);
			 
			
			if(addappl.getTxt_payacno()!=0)
			{
			
				   String pageActions[]={"/AddLoanVerification?tabPaneHeading=Personal&pageId="+addappl.getPageId(),"/AddLoanVerification?tabPaneHeading=DepositDetail&pageId="+addappl.getPageId(),"/AddLoanVerification?tabPaneHeading=AccountInfo&pageId="+addappl.getPageId()};
				   
				   String tabHeading[]={"Personal","DepositDetail","AccountInfo"};
				   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim(),MenuNameReader.getScreenProperties("0001").trim()};
				   
				   req.setAttribute("TabHeading",tabHeading);
				   req.setAttribute("PageActions", pageActions);
				   req.setAttribute("PagePath", pagePath);
				   req.setAttribute("pageNum","3003");
			}
			else
			{ 
				System.out.println("<<<<<<<<<<<<<<<<inside else condition>>>>>>>>>>>>>>>>>>>");
				   String pageActions[]={"/AddLoanVerification?tabPaneHeading=Personal&pageId="+addappl.getPageId(),"/AddLoanVerification?tabPaneHeading=DepositDetail&pageId="+addappl.getPageId()};
				   
				   String tabHeading[]={"Personal","DepositDetail"};
				   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim()};
				   
				   req.setAttribute("TabHeading",tabHeading);
				   req.setAttribute("PageActions", pageActions);
				   req.setAttribute("PagePath", pagePath);
				   req.setAttribute("pageNum","3003");
			}
		
			 
				req.setAttribute("AccountNumber",0);
				req.setAttribute("Maturityamount",0.0);
				req.setAttribute("Intrestrate",0.0);
				req.setAttribute("deposit_days",0);
				req.setAttribute("DepositAmmount",0.0);
				req.setAttribute("CurrentIntRate",0.0);
				req.setAttribute("AmmountEligable",0.0);
				req.setAttribute("Pygmyamount",0.0);
				req.setAttribute("reqamounts",0.0);
				req.setAttribute("depaccnum",0);//int
				req.setAttribute("applsrlnum",0);//int
				
				 if(addappl.getTxt_loanaccno()!=0 && addappl.getTxt_LoanActype()!=null){
					loanmasterobject=deligate.getLoanMaster(addappl.getTxt_LoanActype(),addappl.getTxt_loanaccno());
					if(loanmasterobject!=null){	  
						if(loanmasterobject.getClosedt()!=null){
							req.setAttribute("Loanacc_closed","Closed");
							req.setAttribute("Loanacnum",0);
							req.setAttribute("recov_num",0);
							req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
						}
						req.setAttribute("AmmountEligable", getDepositAmountDrawable(addappl, deligate, loanmasterobject.getDepositAccNo(), req));
						for(int i=0;i<array_deposit_moduleobject.length;i++){
							if(array_deposit_moduleobject[i].getModuleCode().equals(loanmasterobject.getDepositAccType())){
								req.setAttribute("index",String.valueOf(i));
								req.setAttribute("dep_type_abbr",array_deposit_moduleobject[i].getModuleAbbrv());
							}
						}
						showDetail(addappl,loanmasterobject.getAccType(),loanmasterobject.getAccNo(),deligate,req,0,map);
						req.setAttribute("AmmountEligable", getDepositAmountDrawable(addappl, deligate, loanmasterobject.getDepositAccNo(), req));
						LoanMasterObject loan_mast_obj=deligate.additionalLoanDetails(addappl.getTxt_LoanActype(),addappl.getTxt_loanaccno());
						if(loan_mast_obj!=null)
						{
 							if(loan_mast_obj.getTrnMode().equalsIgnoreCase("C"))
							{
								req.setAttribute("Paymode","Cash");
							}
							else if(loan_mast_obj.getTrnMode().equalsIgnoreCase("T")) 
							{ 
								System.out.println("testing============>"+loan_mast_obj.getTrnMode());
								req.setAttribute("Paymode","Transfer");
								req.setAttribute("paymentaccno",String.valueOf(loan_mast_obj.getPaymentAccno()));
								req.setAttribute("paymentacctype",loan_mast_obj.getPaymentAcctype());
								
								for(int i=0;i<array_transfer_moduleobject.length;i++)
								{
									if(array_transfer_moduleobject[i].getModuleCode().equals(loan_mast_obj.getPaymentAcctype()))
									{
										req.setAttribute("Paymode","Transfer");
									}
								}
							}
						}
					}
					else
					{
						req.setAttribute("NoRecords","NoRecords");
						req.setAttribute("Loanacnum",0);
						req.setAttribute("recov_num",0);
						req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
					}
					AccountDetail(deligate, addappl, req);
					if(addappl.getTabPaneHeading()!=null){
						if(addappl.getTabPaneHeading().trim().equalsIgnoreCase("DepositDetail"))
							req.setAttribute("TabNum","1");
						else if(addappl.getTabPaneHeading().trim().equalsIgnoreCase("AccountInfo"))
							req.setAttribute("TabNum", "2");
					}
				}
				if(button_value!=null)
				{
					if(button_value.equalsIgnoreCase("Verify"))
					{ 
						 System.out.println("************Inside Additional loan Verification****************");
						 loanmasterobject.setAccType(addappl.getTxt_LoanActype());
						 loanmasterobject.uv.setVerId(lduser);
						 loanmasterobject.uv.setVerTml(ldtml);
						 loanmasterobject.uv.setVerDate(LDDelegate.getSysDate());
						 int ver_add_loan=deligate.verifyAdditionalLoan(loanmasterobject,LDDelegate.getSysDate());
						 
						 System.out.println("after verifivation=============>"+ver_add_loan);
						 
						 if(ver_add_loan!=0)
						 {
							 if(loanmasterobject.getPayMode().equalsIgnoreCase("C"))
							 {
								 req.setAttribute("Verification",String.valueOf(ver_add_loan));
									req.setAttribute("Loanacnum",0);
									req.setAttribute("recov_num",0);
									req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
									return map.findForward(ResultHelp.getSuccess());
								}
								else if(loanmasterobject.getPayMode().equals("P"))
								{
									req.setAttribute("Verification",String.valueOf(ver_add_loan));
									req.setAttribute("Loanacnum",0);
									req.setAttribute("recov_num",0);
									req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
									return map.findForward(ResultHelp.getSuccess());
								}else{
									req.setAttribute("Verification",String.valueOf(ver_add_loan));
									req.setAttribute("Loanacnum",0);
									req.setAttribute("recov_num",0);
									req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
									return map.findForward(ResultHelp.getSuccess());
								}
						 }
								else if(ver_add_loan==0)
								{
									req.setAttribute("Verification",String.valueOf(ver_add_loan));
									req.setAttribute("Loanacnum",0);
									req.setAttribute("recov_num",0);
									req.setAttribute("pageId",MenuNameReader.getScreenProperties("6010"));
									return map.findForward(ResultHelp.getSuccess());
								}
						 
					}
				}
			if(MenuNameReader.containsKeyScreen(addappl.getPageId()))
			{ 
					path=MenuNameReader.getScreenProperties(addappl.getPageId());
					System.out.println("pageid==========>"+path); 
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
			}
			else
			{
				//.path=MenuNameReader.getScreenProperties("0000");
				//setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return map.findForward(ResultHelp.getError());
		}
			
		
	}
		else
		{
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Path doest exit",req,path);
        	return map.findForward(ResultHelp.getError());
		}
	}
	
	
		
	
	
	

	private void setErrorPageElements(String exception,HttpServletRequest req,String path)
	{
		req.setAttribute("exception",exception);
		req.setAttribute("pageId",path);

	}
	
	private void InitialParameter(LDDelegate lddeligate,HttpServletRequest req)throws RemoteException,SQLException
	{
		ModuleObject fmod[]=lddeligate.getMainModules(2,"1003000");//FD
		ModuleObject rmod[]=lddeligate.getMainModules(2,"1004000");//RD
		ModuleObject rimod[]=lddeligate.getMainModules(2,"1005000");//Reinvestment Deposit
		ModuleObject pgmod[]=lddeligate.getMainModules(2,"1006000");
		array_transfer_moduleobject=lddeligate.getMainModules(2,"1002000,1007000,1014000,1015000");
		array_deposit_moduleobject=new ModuleObject[fmod.length+rmod.length+rimod.length+pgmod.length];
		
		int j=0;
		for(int i=0;i<fmod.length;i++,j++)		
			array_deposit_moduleobject[j]=fmod[i];
		
		for(int i=0;i<rmod.length;i++,j++)		
			array_deposit_moduleobject[j]=rmod[i];
		
		for(int i=0;i<rimod.length;i++,j++)		
			array_deposit_moduleobject[j]=rimod[i];
		
		for(int i=0;i<pgmod.length;i++,j++)		 
			array_deposit_moduleobject[j]=pgmod[i];
		
		 lnpurobj=lddeligate.getLoanPurposes(); //code for getting LoanPurpose
		 System.out.println("lnpurobj===="+lnpurobj); 
		 
		 array_ln_moduleobject=lddeligate.getMainModules(2,"1008000"); //code for getting Loan AC Type
		 
		 str=lddeligate.getPayMode(); //code for getting Payment mode
		 req.setAttribute("PayAcNum",array_transfer_moduleobject);
		 req.setAttribute("PayMode",str);
		 req.setAttribute("LoanACType",array_ln_moduleobject);
		 req.setAttribute("LoanPurpose",lnpurobj);
		 req.setAttribute("DepositType",array_deposit_moduleobject);
		 
		 
	}
	private void getdate(LDDelegate lddeligate,HttpServletRequest req){
		String date_var=LDDelegate.getSysDate();
		req.setAttribute("Datefun",date_var);
	}
	
	
	//code for fetching module code from 
	
	private DepositMasterObject getModuleCode(LDDelegate lddeligate,HttpServletRequest req,LoanApplicationDE applicationDe,int ac_num)throws RemoteException,SQLException
	{
		try{ 
			int Deposit_Type=0,ref_no=0;
			System.out.println("*******inside getmodulecode function   sweeeeeeee**********");
		CustomerDelegate custdeligate=new CustomerDelegate();
		ModuleObject fmod[]=lddeligate.getMainModules(2,"1003000");//FD
		ModuleObject rmod[]=lddeligate.getMainModules(2,"1004000");//RD
		ModuleObject rimod[]=lddeligate.getMainModules(2,"1005000");//Reinvestment Deposit
		ModuleObject pgmod[]=lddeligate.getMainModules(2,"1006000");
		array_transfer_moduleobject=lddeligate.getMainModules(2,"1002000,1007000,1014000,1015000");
		array_deposit_moduleobject=new ModuleObject[fmod.length+rmod.length+rimod.length+pgmod.length];
		    
		int j=0;
		for(int i=0;i<fmod.length;i++,j++)		
			array_deposit_moduleobject[j]=fmod[i];
		
		for(int i=0;i<rmod.length;i++,j++)		
			array_deposit_moduleobject[j]=rmod[i];
		
		for(int i=0;i<rimod.length;i++,j++)		
			array_deposit_moduleobject[j]=rimod[i];
		
		for(int i=0;i<pgmod.length;i++,j++)		
			array_deposit_moduleobject[j]=pgmod[i];
		
		
		if(applicationDe.getTxt_reftype()!=null)
			Deposit_Type=Integer.valueOf(applicationDe.getTxt_reftype());
		String DepositType=array_deposit_moduleobject[Deposit_Type].getModuleDesc();
		req.setAttribute("Description",DepositType);
		
		 if(applicationDe.getTxt_reftype()!=null)
		  ref_no=Integer.valueOf(applicationDe.getTxt_reftype());
		System.out.println("ref_no======"+ref_no);
   		String ac_type=array_deposit_moduleobject[ref_no].getModuleCode();
   		System.out.println("ac_type===>"+ac_type);
   		sub_modcode=ac_type.substring(1,4);
		//int ac_num=applicationDe.getTxt_refno(); 
		System.out.println("ac_num===>"+ac_num);
		System.out.println("sub_modcode===>"+sub_modcode);
		ac=lddeligate.getDepositMaster(ac_type,ac_num);
		System.out.println("ac object==============>"+ac);
		if(ac!=null) 
		{
			if(sub_modcode.equals("003") || sub_modcode.equals("004") ||sub_modcode.equals("005"))
			{
			
				if(sub_modcode.equals("003"))
				{
				//lbl_deposit_amt.setText(String.valueOf(nf.format(FDAmt())));	
					FDAmt(applicationDe,ac_num);
				Double dep_amount=ac.getDepositAmt();
				req.setAttribute("DepositAmmount",dep_amount);
				}
				if(sub_modcode.equals("005"))
				{			
				Double dep_amount=ac.getDepositAmt();	
				req.setAttribute("DepositAmmount",dep_amount);
			    }
			 if(sub_modcode.equals("004"))
			   {											
				 Double dep_amount=ac.getDepositAmt();	
				 req.setAttribute("DepositAmmount",dep_amount);
			   }
			int acc_no=ac.getAccNo();
			double Maturityamount=ac.getMaturityAmt();
			double Intrestrate=ac.getInterestRate();
			int days=ac.getDepositDays();
			String matdate=ac.getMaturityDate();
			String Depdate=ac.getDepDate();
			int cid=ac.getCustomerId();
			req.setAttribute("AccountNumber",acc_no);
			req.setAttribute("Maturityamount",Maturityamount);
			req.setAttribute("Intrestrate",Intrestrate);
			req.setAttribute("deposit_days",days);
			req.setAttribute("deposit_matdate",matdate);
			req.setAttribute("deposit_Depdate",Depdate);
			req.setAttribute("Pygmyamount",0.0);
			
			
			
			req.setAttribute("personalInfo",custdeligate.getCustomerdetails(cid));
			req.setAttribute("panelName","Personal");
			
			double int_rate=lddeligate.getCurrentIntRate(ac_type,ac_num);
			req.setAttribute("CurrentIntRate",int_rate);
			 
			System.out.println("current int rate==========>"+int_rate);
			System.out.println("get attribute========>"+req.getAttribute("CurrentIntRate"));
			} 
			else if(sub_modcode.equals("006"))
			{
				System.out.println("inside pygmy condition in action classs"); 
				int cid=ac.getCustomerId();
				//int_method_call=2;
				
				double PygmyAmt=pygmyamt(applicationDe);
				String Depdate=ac.getDepDate(); 
				req.setAttribute("deposit_Depdate",Depdate);
				 
				double deposit_ammount=pygmyamt(applicationDe);;
				req.setAttribute("Pygmyamount",deposit_ammount);
				System.out.println("Pygmy deposit amount===="+deposit_ammount); 
				
				req.setAttribute("AccountNumber",0);
				req.setAttribute("Intrestrate",0.0);
				req.setAttribute("deposit_days",0);
				req.setAttribute("CurrentIntRate",0.0);
				req.setAttribute("AmmountEligable",0.0);
				req.setAttribute("DepositAmmount",0.0);
				req.setAttribute("Maturityamount",0.0);
				
				/*double_ln_percentage=lddeligate.getLoanPercentage(ac.getAccType());
				System.out.println("pygmy intrrest in action class "+double_interest_rate);*/ 
				
				req.setAttribute("personalInfo",custdeligate.getCustomerdetails(cid));
				req.setAttribute("panelName","Personal");
			}
			
		}
		else
		{
		System.out.println("*******************IN ELSE CONDITION***********************");	
		applicationDe.setAcc_notfound("AccountNotFound");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	    return ac;	 
		
	}
	
	
	/**
	 `  	 *	This function is called on focus lost of loan amount entry field, if the account
	 *	type if of Term Deposit type. 
	 *	This function calculates the maximum anount that could be taken as Loan.		
	 */
	
	public double getDepositAmountDrawable(LoanApplicationDE applicationDe,LDDelegate deligate,int accno,HttpServletRequest req)throws SQLException,RemoteException{ 
		double amt=0;
		String ac_type=null;
		if(applicationDe.getTxt_reftype()!=null)	
			ac_type=array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode();
		sub_modcode=ac_type.substring(1,4); 		
		if(sub_modcode.equals("003"))
			amt=FDAmt(applicationDe,accno);
		if(sub_modcode.equals("005"))											
			amt=reinvestimentAmt(applicationDe);
		if(sub_modcode.equals("004"))			
			amt=RdDepositAmt(applicationDe,deligate,accno);
		double_ln_percentage=deligate.getLoanPercentage(ac_type);
		double double_amtcan=(amt*double_ln_percentage)/100;
		double double_amtsanc=0;
		if(loanmasterobject!=null)
			double_amtsanc=loanmasterobject.getSanctionedAmount();
		return double_amtcan-double_amtsanc;
	}
	
	private void getTabbedpane_AccountInfo(HttpServletRequest req,LoanApplicationDE applicationDe,ActionMapping map)
	{ 
		if(map.getPath().equalsIgnoreCase("/LoansOnDeposit/ApplicationDE")){
		String pageActions[]={"/LoansOnDeposit/ApplicationDE?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/LoansOnDeposit/ApplicationDE?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId(),"/LoansOnDeposit/ApplicationDE?tabPaneHeading=AccountInfo&pageId="+applicationDe.getPageId()};
			   
		String tabHeading[]={"Personal","DepositDetail","AccountInfo"};
		String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim(),MenuNameReader.getScreenProperties("0001").trim()};
			   
		req.setAttribute("TabHeading",tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum","3003");
		}else if(map.getPath().equalsIgnoreCase("/AddLoanVerification")){
			String pageActions[]={"/AddLoanVerification?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/AddLoanVerification?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId(),"/AddLoanVerification?tabPaneHeading=AccountInfo&pageId="+applicationDe.getPageId()};
			   
			   String tabHeading[]={"Personal","DepositDetail","AccountInfo"};
			   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim(),MenuNameReader.getScreenProperties("0001").trim()};
			   
			   req.setAttribute("TabHeading",tabHeading);
			   req.setAttribute("PageActions", pageActions);
			   req.setAttribute("PagePath", pagePath);
			   req.setAttribute("pageNum","3003");
		}
	}	
	
	private void getTabbedpane(HttpServletRequest req,LoanApplicationDE applicationDe)
	{ 
		
		if(applicationDe.getTxt_payacno()!=0)
		{
		
			   String pageActions[]={"/LoansOnDeposit/ApplicationDE?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/LoansOnDeposit/ApplicationDE?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId(),"/LoansOnDeposit/ApplicationDE?tabPaneHeading=AccountInfo&pageId="+applicationDe.getPageId()};
			   
			   String tabHeading[]={"Personal","DepositDetail","AccountInfo"};
			   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim(),MenuNameReader.getScreenProperties("0001").trim()};
			   
			   req.setAttribute("TabHeading",tabHeading);
			   req.setAttribute("PageActions", pageActions);
			   req.setAttribute("PagePath", pagePath);
			   req.setAttribute("pageNum","3003");
		}
		else
		{ 
			System.out.println("<<<<<<<<<<<<<<<<inside else condition>>>>>>>>>>>>>>>>>>>");
			   String pageActions[]={"/LoansOnDeposit/ApplicationDE?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/LoansOnDeposit/ApplicationDE?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId()};
			   
			   String tabHeading[]={"Personal","DepositDetail"};
			   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim()};
			   
			   req.setAttribute("TabHeading",tabHeading);
			   req.setAttribute("PageActions", pageActions);
			   req.setAttribute("PagePath", pagePath);
			   req.setAttribute("pageNum","3003");
		}
		      
		   
		   
	}
	
	//tabbed pane method used for additional loans
	
	private void getTabbedpane_additional(HttpServletRequest req,LoanApplicationDE applicationDe)
	{ 
		
		if(applicationDe.getTxt_payacno()!=0)
		{
		
			   String pageActions[]={"/AddLoanVerification?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/AddLoanVerification?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId(),"/AddLoanVerification?tabPaneHeading=AccountInfo&pageId="+applicationDe.getPageId()};
			   
			   String tabHeading[]={"Personal","DepositDetail","AccountInfo"};
			   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim(),MenuNameReader.getScreenProperties("0001").trim()};
			   
			   req.setAttribute("TabHeading",tabHeading);
			   req.setAttribute("PageActions", pageActions);
			   req.setAttribute("PagePath", pagePath);
			   req.setAttribute("pageNum","3003");
		}
		else
		{ 
			System.out.println("<<<<<<<<<<<<<<<<inside else condition>>>>>>>>>>>>>>>>>>>");
			   String pageActions[]={"/AddLoanVerification?tabPaneHeading=Personal&pageId="+applicationDe.getPageId(),"/AddLoanVerification?tabPaneHeading=DepositDetail&pageId="+applicationDe.getPageId()};
			   
			   String tabHeading[]={"Personal","DepositDetail"};
			   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6002").trim()};
			   
			   req.setAttribute("TabHeading",tabHeading);
			   req.setAttribute("PageActions", pageActions);
			   req.setAttribute("PagePath", pagePath);
			   req.setAttribute("pageNum","3003");
		}
	}
	
	public void showloantype(LoanApplicationDE applicationDe,LDDelegate lddeligate,HttpServletRequest req)throws RemoteException
	{
		
		System.out.println("****inside Show loan Type***");
		if(applicationDe.getTxt_reftype()!=null)
		{	
			String ln_mod_code=array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode();
			array_ln_moduleobject=lddeligate.getMainModules(2,"1008000");
			for(int k=0;k<array_deposit_moduleobject.length;k++)
	
				for(int i=0;i<array_ln_moduleobject.length;i++)
				{
					if(array_ln_moduleobject[i].getLoanModuleCode().equals(ln_mod_code))
					{ 
			
						req.setAttribute("Loan_Type",array_ln_moduleobject[i].getModuleAbbrv());
						req.setAttribute("Loan_ModuleCode",array_ln_moduleobject[i].getModuleCode());
						req.setAttribute("LTDescription",array_ln_moduleobject[i].getModuleDesc());
				
					} 
				}
		}
	}
	
	public double FDAmt(LoanApplicationDE applicationDe,int ac_num)
	{
		
		double depositamt=0,intamt_paid=0,int_payble=0;
		DepositMasterObject depositmasterobject;
		System.out.println("Inside FD amount calculate condition");
		System.out.println("applicationDe.getTxt_appdate()==="+applicationDe.getTxt_appdate());
	
		try{
			LDDelegate lddeligate=new LDDelegate();
			int ref_no=Integer.valueOf(applicationDe.getTxt_reftype());
			System.out.println("ref_no======"+ref_no);
	   		String ac_type=array_deposit_moduleobject[ref_no].getModuleCode();
	   		System.out.println("ac_type===>"+ac_type);
	   		sub_modcode=ac_type.substring(1,4);
			//int ac_num=applicationDe.getTxt_refno(); 
			ac=lddeligate.getDepositMaster(ac_type,ac_num);
			int int_total_days = lddeligate.getDaysFromTwoDate(ac.getDepDate(),applicationDe.getTxt_appdate());
			System.out.println("---No of Days"+int_total_days);
			array_double_interest_rate= lddeligate.getDepositInterestRate(ac.getAccType(),ac.getDPType(),ac.getCategory(),Validations.convertYMD(ac.getDepDate()),int_total_days,ac.getDepositAmt());
			
			depositmasterobject=lddeligate.getFDClosureData(array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode(),applicationDe.getTxt_refno(),0);
			double_interest_rate=array_double_interest_rate[0];
			System.out.println("---InterestRate"+double_interest_rate);
			intamt_paid=depositmasterobject.getInterestPaid();
			System.out.println("---Interest paid"+intamt_paid);
			ModuleObject mod_obj[]=lddeligate.getMainModules(3,array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode());
			double_interest_rate=double_interest_rate-mod_obj[0].getPenaltyRate();
			System.out.println("New Interest Rate"+double_interest_rate);
			if(double_interest_rate < 0)
				double_interest_rate = 0;
			int_payble=ac.getDepositAmt()*double_interest_rate*int_total_days/36500;
			System.out.println("interestpayable"+int_payble);
			if(intamt_paid>int_payble)
			{
				depositamt=ac.getDepositAmt()-(intamt_paid-int_payble);
				System.out.println("---depositAmt"+depositamt);
			}
			else
				depositamt=ac.getDepositAmt();
			System.out.println("---depositAmt"+depositamt);
		}catch(Exception e){}
	return depositamt;	
	}
	public  double reinvestimentAmt(LoanApplicationDE applicationDe)
	{
		double deposit_amt=0;
		try{
			
			LDDelegate lddeligate=new LDDelegate();		
			int int_total_days = lddeligate.getDaysFromTwoDate(ac.getDepDate(),applicationDe.getTxt_appdate());
			array_double_interest_rate= lddeligate.getDepositInterestRate(ac.getAccType(),ac.getDPType(),ac.getCategory(),Validations.convertYMD(ac.getDepDate()),int_total_days,ac.getDepositAmt());
			double_interest_rate=array_double_interest_rate[0];
			System.out.println("first rate"+double_interest_rate);
		
			ModuleObject mod_obj[]=lddeligate.getMainModules(3,array_deposit_moduleobject[Integer.valueOf(applicationDe.getTxt_reftype())].getModuleCode());
			double_interest_rate=double_interest_rate-mod_obj[0].getPenaltyRate();
			System.out.println("second rate"+double_interest_rate);
			if(double_interest_rate<0)
			double_interest_rate=0;
			deposit_amt=reinvestmentCalc(ac.getDepositAmt(),ac.getDepDate(),applicationDe.getTxt_appdate(),double_interest_rate,lddeligate);
			System.out.println("deposit amt"+deposit_amt);
			}catch(Exception e){}
			return deposit_amt;
	}
	public double RdDepositAmt(LoanApplicationDE appliactionde,LDDelegate deligate,int accno)
	{
		System.out.println("**************Inside deposit ammount RD*******************");
		
		double depositamt=0, dep_int_rate=0;; 
		String array_string_date[]=null;
		double array_double_amount[]=null;
		DepositMasterObject[] deposit_object=null;
		//System.out.println("The lengh of object"+deposit_object.length);
		try{
			System.out.println("********ac*********"+ac);
			System.out.println("********2**********");
			ac=deligate.getDepositMaster(array_deposit_moduleobject[Integer.valueOf(appliactionde.getTxt_reftype())].getModuleCode(),accno);
			System.out.println("reference type============>===>"+array_deposit_moduleobject[Integer.valueOf(appliactionde.getTxt_reftype())].getModuleCode()+"/n"+"======ref deposit acc num========>"+appliactionde.getTxt_refno());
			deposit_object=deligate.RdBalancecalc(array_deposit_moduleobject[Integer.valueOf(appliactionde.getTxt_reftype())].getModuleCode(),accno);
			System.out.println("<===========rb balance============>"+deposit_object);
			System.out.println("depdate=====>"+ac.getDepDate()+"appdate=========>"+appliactionde.getTxt_appdate());
			System.out.println("********3**********"); 
			int int_total_days = deligate.getDaysFromTwoDate(ac.getDepDate(),LDDelegate.getSysDate());
			System.out.println("******int_total_days======>"+int_total_days);
			array_double_interest_rate= deligate.getDepositInterestRate(ac.getAccType(),ac.getDPType(),ac.getCategory(),Validations.convertYMD(ac.getDepDate()),int_total_days,ac.getDepositAmt());
		System.out.println("The lengh of object"+deposit_object.length);
		System.out.println("no of days"+int_total_days);
		//double dep_amt=deposit_object[0].getDepositAmt();
		
		array_string_date=new String[deposit_object.length];
		array_double_amount=new double[deposit_object.length];
		System.out.println(array_string_date.length);
		
		int k=0; 
		for(int i=1;i<deposit_object.length;i++)
		{
			System.out.println("inside--");
			array_double_amount[k] = deposit_object[i].getDepositAmt();
			array_string_date[k] =deposit_object[i].getTranDate();
			k++;	
		}
		System.out.println("puuuuuu");
		array_double_amount[k] = 0.0;
		array_string_date[k]=appliactionde.getTxt_appdate();
		System.out.println(deposit_object[1].getDepositAmt());
		System.out.println("amount is"+array_double_amount[0]); 
	
		
		dep_int_rate=array_double_interest_rate[0];
		System.out.println("first rate"+dep_int_rate);
		ModuleObject mod_obj[]=deligate.getMainModules(3,array_deposit_moduleobject[Integer.valueOf(appliactionde.getTxt_reftype())].getModuleCode());
		dep_int_rate=dep_int_rate-mod_obj[0].getPenaltyRate();
		System.out.println("second rate"+dep_int_rate); 
		if(dep_int_rate<0)
			dep_int_rate=0;
			
		System.out.println("1st==============="+ac.getDepositAmt()+"2nd==========="+dep_int_rate+"3rd==========="+ac.getDepDate()+"4th======="+appliactionde.getTxt_appdate()+"5th========="+array_double_amount+"6th========="+array_string_date);
		 depositamt=rdIntReCal(ac.getDepositAmt(), dep_int_rate, ac.getDepDate(),LDDelegate.getSysDate(),array_double_amount,array_string_date);
		 System.out.println("depositamt@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+depositamt); 
		System.out.println(ac.getDepositAmt());
		System.out.println(ac.getDepDate());
		System.out.println("1"+array_double_amount);
		System.out.println("2"+array_string_date);
		System.out.println("deposit amt"+depositamt);
		}catch(Exception e){}
		
		return depositamt;
		
	}
   	
	public double pygmyamt(LoanApplicationDE applicationde)
	{
		double pyamount=0;
		try{ 
			LDDelegate lddeligate=new LDDelegate();
		pyamount=lddeligate.getPygmyAmt(array_deposit_moduleobject[Integer.valueOf(applicationde.getTxt_reftype())].getModuleCode(),applicationde.getTxt_refno(),applicationde.getTxt_appdate());
		System.out.println("pygmy amount===============>"+pyamount); 
	}catch(Exception e){} 
	return pyamount;
	}
	//For Getting Pygmy Amount
	
	public double getPygmyAmountDrawable(LoanApplicationDE applicationde,LDDelegate deligate) throws RemoteException,SQLException
	{
		String ac_type=null;
		
		if(applicationde.getTxt_reftype()!=null)	
			ac_type=array_deposit_moduleobject[Integer.valueOf(applicationde.getTxt_reftype())].getModuleCode();
		double_ln_percentage=deligate.getLoanPercentage(ac_type);
		double double_amtcan=(pygmyamt(applicationde)*double_ln_percentage)/100; 
		 System.out.println("laonmaster object==============>"+loanmasterobject);
		double double_amtsanc=0;
		if(loanmasterobject!=null)
			double_amtsanc=loanmasterobject.getSanctionedAmount();
		
		return (double_amtcan-double_amtsanc);
	}
	
	
	//this function is used to get the account holder name based on the account 
	@SuppressWarnings("null")
	public void AccountDetail(LDDelegate deligate,LoanApplicationDE appldeform,HttpServletRequest req)
	{
		try{
			CustomerDelegate custdeligate=new CustomerDelegate();
			int cid1=0;
			if(appldeform.getTxt_payactype()!=null)
			{	
				AccountObject accountobj=deligate.getAccount(null,appldeform.getTxt_payactype(),appldeform.getTxt_payacno(),appldeform.getTxt_appdate());
				if(accountobj!=null)
				{
				
					String name=accountobj.getAccname();
					double balance=accountobj.getAmount();
					req.setAttribute("Name_AccountHolder",name);
					/*req.setAttribute("AccBalance",balance);*/
				 
					System.out.println("tabpane heading sweeeeeeeeeee  ===============>"+appldeform.getTabPaneHeading());
					
					if(appldeform.getTabPaneHeading()!=null)
					{
						if(appldeform.getTabPaneHeading().trim().equalsIgnoreCase("AccountInfo"))
						{ 
							System.out.println("account object customer id sweeeeee=============>"+accountobj.getCid());
							int cid=accountobj.getCid();
							req.setAttribute("panelName","AccountInfo");
							req.setAttribute("personalInfo",custdeligate.getCustomerdetails(cid));
						}
				
				
					else if(appldeform.getTabPaneHeading().equalsIgnoreCase("Personal"))
					{
							if(ac!=null)
							{	
								int cid=ac.getCustomerId(); 
								System.out.println("*********Customer inside testing123====="+cid);
								req.setAttribute("panelName","Personal");
								req.setAttribute("personalInfo",custdeligate.getCustomerdetails(cid));
						
							}
					}
				}
			}
			
			else
			{	if(accountobj!=null)
				req.setAttribute("Name_AccountHolder",accountobj.getAccname());
			}
			}
			
		}
			catch(Exception e){ 
			e.printStackTrace(); 
		}
	}
	private void getScreenDetails(LoanApplicationDE appldeform)
	{
		
		//System.out.println("<========>=====>payment account type=====>"+array_transfer_moduleobject[Integer.valueOf(appldeform.getTxt_payactype())].getModuleCode()); 
		
		loanmasterobject=new LoanMasterObject();   
		
		loanmasterobject.setAccType(appldeform.getTxt_LoanActype()); 
		if(appldeform.getTxt_loanaccno()>0)   
			loanmasterobject.setAccNo(appldeform.getTxt_loanaccno());	
		 
		loanmasterobject.setApplicationSrlNo(appldeform.getTxt_appsrlno());
		loanmasterobject.setApplicationDate(appldeform.getTxt_appdate());
		loanmasterobject.setRequiredAmount(appldeform.getTxt_reqammount());
		loanmasterobject.setSanctionedAmount(appldeform.getTxt_reqammount());
		if(appldeform.getTxt_AutoLoanRecovery().equalsIgnoreCase("Interest"))
		{
			loanmasterobject.setAuto_loan("I");
		}if(appldeform.getTxt_AutoLoanRecovery().equalsIgnoreCase("Total Amount"))
		{
			loanmasterobject.setAuto_loan("T"); 
		}else
			loanmasterobject.setAuto_loan("N");
		    
		StringTokenizer str=new StringTokenizer(appldeform.getTxt_purpose()); 
		System.out.println("@@@@@@@@@#########>>>>>>>>>>>>>>>>>>>>>>>>>>."+appldeform.getTxt_purpose()+"\n"+"========auto laon recovery====>"+appldeform.getTxt_AutoLoanRecovery());
		
		loanmasterobject.setPurposeCode(Integer.valueOf(str.nextToken()));
		
		loanmasterobject.setDepositAccType(array_deposit_moduleobject[Integer.valueOf(appldeform.getTxt_reftype())].getModuleCode());
		
		System.out.println("dep type===========>"+array_deposit_moduleobject[Integer.valueOf(appldeform.getTxt_reftype())].getModuleCode());
		System.out.println("appldeform.getTxt_refno()=====>"+appldeform.getTxt_refno());
		
		
		loanmasterobject.setDepositAccNo(appldeform.getTxt_refno());
		
		if(appldeform.getTxt_paymode().equalsIgnoreCase("Cash"))
		{
			loanmasterobject.setPayMode("C");
		}
		else if(appldeform.getTxt_paymode().equalsIgnoreCase("Transfer"))
		{
			loanmasterobject.setPayMode("T");
			loanmasterobject.setPaymentAccno(appldeform.getTxt_payacno());
			//loanmasterobject.setPaymentAcctype(String.valueOf(array_transfer_moduleobject[Integer.valueOf(appldeform.getTxt_payactype())].getModuleCode()));
			loanmasterobject.setPaymentAcctype(String.valueOf(appldeform.getTxt_payactype()));
		System.out.println("At line :2315 "+loanmasterobject.getPaymentAcctype());
			
		}
		else if(appldeform.getTxt_paymode().equalsIgnoreCase("PayOrder"))
			loanmasterobject.setPayMode("P");
		
		loanmasterobject.uv.setUserId(lduser);
		loanmasterobject.uv.setUserTml(ldtml);
		loanmasterobject.setInterestRate(appldeform.getTxt_intrate());

		
		
	} 
	//************for displaying the details of a loan********** 
	
	private void showDetail(LoanApplicationDE applform,String actype,int acno,LDDelegate deligate,HttpServletRequest req, int num,ActionMapping map){
		try{	 
			loanmasterobject=deligate.getLoanMaster(actype,acno);
			if(loanmasterobject!=null){
				getModuleCode(deligate, req,applform,loanmasterobject.getDepositAccNo());
				if(applform.getTabPaneHeading()!=null){
					if(applform.getTabPaneHeading().trim().equalsIgnoreCase("DepositDetail")){
						req.setAttribute("TabNum", "1");
					}
				}
				req.setAttribute("applsrlnum",loanmasterobject.getApplicationSrlNo());//int
				req.setAttribute("Datefun",loanmasterobject.getApplicationDate()); //string
				req.setAttribute("reqamounts",loanmasterobject.getRequiredAmount());//double
				req.setAttribute("depaccnum",loanmasterobject.getDepositAccNo());//int
				req.setAttribute("sanctionamt",loanmasterobject.getSanctionedAmount());//double
				req.setAttribute("principlebal",loanmasterobject.getPrBal());//double 
				req.setAttribute("CurrentIntRate",loanmasterobject.getInterestRate());//double
				if(loanmasterobject.getPurposeCode()==1){
					req.setAttribute("LoanPurposecode","1 COT/SMALL SCALE INDUSTRY");
				}else if(loanmasterobject.getPurposeCode()==2){
					req.setAttribute("LoanPurposecode","2 WHOLESALE TRADE");
				}else if(loanmasterobject.getPurposeCode()==3){
					req.setAttribute("LoanPurposecode","3 RETAIL TRADE");
				}else if(loanmasterobject.getPurposeCode()==4){
					req.setAttribute("LoanPurposecode","4 SMALL BUSINESS ENTERPRSES");
				}else if(loanmasterobject.getPurposeCode()==5){
					req.setAttribute("LoanPurposecode","5 TRANSPORT OPERATORS");
				}else if(loanmasterobject.getPurposeCode()==6){
					req.setAttribute("LoanPurposecode","6 EDUCATION");
				}else if(loanmasterobject.getPurposeCode()==7){
					req.setAttribute("LoanPurposecode","7 PUR OF SITE/CONSTN/REPAIR");
				}else if(loanmasterobject.getPurposeCode()==8){
					req.setAttribute("LoanPurposecode","8 ACT. ALLIED TO AGRICULTUR");
				}else if(loanmasterobject.getPurposeCode()==9){
					req.setAttribute("LoanPurposecode","9 CONSUMPTION/CEREMONIAL");
				}else if(loanmasterobject.getPurposeCode()==10){
					req.setAttribute("LoanPurposecode","10 REPAYMENT OF PRIOR DEBTS");
				}else if(loanmasterobject.getPurposeCode()==11){
					req.setAttribute("LoanPurposecode","11 PROFESSIONAL/SELF-EMPLOYD");
				}else if(loanmasterobject.getPurposeCode()==12){
					req.setAttribute("LoanPurposecode","12 DOMESTIC");
				}else if(loanmasterobject.getPurposeCode()==99){
					req.setAttribute("LoanPurposecode","99 OTHERS");
				}
				if(loanmasterobject.getAuto_loan()!=null)
					if(loanmasterobject.getAuto_loan().equalsIgnoreCase("I")){
						req.setAttribute("Aoto_loanrec","Interest");
					}else if(loanmasterobject.getAuto_loan().equalsIgnoreCase("T")){
						req.setAttribute("Aoto_loanrec","Total Amount");
					}else if(loanmasterobject.getAuto_loan().equalsIgnoreCase("N")){
						req.setAttribute("Aoto_loanrec","NONE");
					}
				if(loanmasterobject.getPayMode().equalsIgnoreCase("C")){
					req.setAttribute("Paymode","Cash");
				}else if(loanmasterobject.getPayMode().equalsIgnoreCase("T")){ 
					AccountObject acc_obj=deligate.getAccount(null,loanmasterobject.getPaymentAcctype(),loanmasterobject.getPaymentAccno(),LDDelegate.getSysDate());
					req.setAttribute("Paymode","Transfer");
					req.setAttribute("paymentaccno",String.valueOf(loanmasterobject.getPaymentAccno()));
					for(int i=0;i<array_transfer_moduleobject.length;i++){
						if(array_transfer_moduleobject[i].getModuleCode().equals(loanmasterobject.getPaymentAcctype())){
							req.setAttribute("PayIndex",String.valueOf(i));
							req.setAttribute("paymentacctype",array_transfer_moduleobject[i].getModuleAbbrv());
						}
					}
					if(acc_obj!=null){
						req.setAttribute("Name_AccountHolder", acc_obj.getAccname());
						getTabbedpane_AccountInfo(req,applform,map);
						if(applform.getTabPaneHeading()!=null){
							if(applform.getTabPaneHeading().equalsIgnoreCase("AccountInfo")){ 
								CustomerDelegate custdeligate=new CustomerDelegate();
								int cid=acc_obj.getCid();
								req.setAttribute("panelName","AccountInfo");
								req.setAttribute("personalInfo",custdeligate.getCustomerdetails(cid));
							}
						}
					}
					for(int i=0;i<array_transfer_moduleobject.length;i++){
						if(array_transfer_moduleobject[i].getModuleCode().equals(loanmasterobject.getPaymentAcctype())){
							req.setAttribute("Paymode","Transfer");
						}
					}
				}else if(loanmasterobject.getPayMode().equalsIgnoreCase("P")){
					req.setAttribute("Paymode","PayOrder");
				}
				if(actype.equalsIgnoreCase("1008006")){ 
					double_ln_percentage=deligate.getLoanPercentage(loanmasterobject.getDepositAccType()); 
					double double_amtcan=(pygmyamt(applform)*double_ln_percentage)/100; 
					double double_amtsanc=0;
					if(loanmasterobject!=null)
						double_amtsanc=loanmasterobject.getSanctionedAmount();
					double pygmydepamount=double_amtcan-double_amtsanc;
					req.setAttribute("AmmountEligable",pygmydepamount);
				}else{	
					double dep_amount_drawable=getDepositAmountDrawable(applform, deligate,loanmasterobject.getDepositAccNo(),req);
					double sanc_amt=loanmasterobject.getSanctionedAmount();
					double ammount=0.0;
					if(applform.getAdd_val()!=null){
						if(applform.getAdd_val().equalsIgnoreCase("no")){
							ammount=sanc_amt+dep_amount_drawable;
						}else if(applform.getAdd_val().equalsIgnoreCase("yes")){
							ammount=dep_amount_drawable;
						}
					}else{
						ammount=dep_amount_drawable;
					}
					req.setAttribute("AmmountEligable",ammount);
				}
			}else{
				req.setAttribute("reqamounts",0.0);//double
				req.setAttribute("depaccnum",0);//int
				req.setAttribute("applsrlnum",0);//int
				req.setAttribute("sanctionamt",0.0);//double
				req.setAttribute("principlebal",0.0);//double
				req.setAttribute("CurrentIntRate",0.0);//double                                                                                                                                                                                                                                                                                                                                                                                           
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void recovery_lndesc(LDDelegate lddeligate,LDRecoveryForm recoveryform,HttpServletRequest req)throws RemoteException
	{
		
		array_transfer_moduleobject=lddeligate.getMainModules(2,"1002000,1007000,1014000,1015000");
		array_ln_moduleobject=lddeligate.getMainModules(2,"1008000");
		if(!recoveryform.getCombo_loantype().equalsIgnoreCase("Select"))
		{
		req.setAttribute("Recoveryabbr",array_ln_moduleobject[Integer.valueOf(recoveryform.getCombo_loantype())].getModuleDesc());
		req.setAttribute("LDCashAbbr",array_ln_moduleobject[Integer.valueOf(recoveryform.getCombo_loantype())].getModuleAbbrv());
		req.setAttribute("LDcashacno",String.valueOf(recoveryform.getTxt_accno()));
		}
		if(recoveryform.getTxt_tranfrom()!=null)
		{	
			String accabbr=array_transfer_moduleobject[Integer.valueOf(recoveryform.getTxt_tranfrom())].getModuleDesc();
		
			System.out.println("@@@@@@@@@@@@------>"+accabbr); 
		
			req.setAttribute("PayAcNumabbr",accabbr);
		}
	} 
	private LoanReportObject recovery_showdetail(LDDelegate lddeligate,LDRecoveryForm recoveryform,HttpServletRequest req)throws SQLException,RemoteException,ApplicationException,SystemException
	{	
		req.setAttribute("cashintrate", 0.0);
		req.setAttribute("trfintamt", 0.0);
		req.setAttribute("trfothers", 0.0);
		req.setAttribute("trfextraint", 0.0);
		req.setAttribute("cashintpaiduptodate", null);
		req.setAttribute("cashintrate", 0.0);
		req.setAttribute("cashtotalamt", 0.0);
		
		array_ln_moduleobject=lddeligate.getMainModules(2,"1008000");
		CustomerDelegate custdel=new CustomerDelegate();
		
		int vocher_no=recoveryform.getTxt_voucherno();
		System.out.println("Voucher no------------>"+vocher_no);
		if(vocher_no!=0)
		{
		LoanTransactionObject loan_tran_obj=lddeligate.getLoanTransaction(vocher_no);
		if(loan_tran_obj!=null)
		{
			System.out.println("Loan trn objects ac_no1233 -------------->"+loan_tran_obj.getAccNo());
			System.out.println("Loan trn objects ac_typ122-------------->"+loan_tran_obj.getAccType());
			String ac_typ= loan_tran_obj.getAccType();
			//req.setAttribute("loantrn",loan_tran_obj);
			recoveryform.setTxt_accno(loan_tran_obj.getAccNo());
			recoveryform.setTxt_entAmo(loan_tran_obj.getTransactionAmount());
			//System.out.println("======+++++++++++++++++======>"+recoveryform.getTxt_entAmo());
		    
		        	
		    	
		    
		}
		}
		if(!recoveryform.getCombo_loantype().equalsIgnoreCase("Select"))
		{
		loanreportobject=lddeligate.getLoanDetails(array_ln_moduleobject[Integer.valueOf(recoveryform.getCombo_loantype())].getModuleCode(),recoveryform.getTxt_accno());
		ln_last_details=lddeligate.getLastPaymentDetails(array_ln_moduleobject[Integer.valueOf(recoveryform.getCombo_loantype())].getModuleCode(),recoveryform.getTxt_accno());
		}
		if(ln_last_details!=null)
		{
			req.setAttribute("LoanBalance",ln_last_details.getPrnAmt());
			req.setAttribute("Interestpaid",ln_last_details.getInterestPaid());
			req.setAttribute("lastdetail",ln_last_details.getIntUptoDate());
			//lbl_last_pr_amt.setText(String.valueOf(nf.format(ln_last_details.getPrnAmt())));
			//lbl_last_int_amt.setText(String.valueOf(nf.format(ln_last_details.getInterestPaid())));
			//lbl_last_int_date.setText(ln_last_details.getIntUptoDate());
			
		}
		double trn_amt=0.0,int_amt=0.0;
		double others=0.0,extra=0.0;
		double principal=0.0,int_rate=0.0;
		String name=null;
		
		if(loanreportobject!=null)
		{	
			int_rate=loanreportobject.getLoanIntRate();
			System.out.println("***********In the loanReport object**********");
			if(recoveryform.getTxt_ammount()!=0 || recoveryform.getTxt_entAmo()!=0)
			{
				System.out.println("***********1111111111111111***********");
				try
				{
				
					int_amt=loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid();
					name=loanreportobject.getName();
					if(name!=null){
						recoveryform.setTxt_name(name);
					}
					System.out.println("&&&&&&Interest Amount&&&&&&&&" + int_amt );
					recoveryform.setTxt_interest(int_amt);
					/*trn_amt = Double.parseDouble(txt_trf_amt.getText());
            		others = Math.round(Double.parseDouble(lbl_other_amt.getText().trim()));
            		extra = Math.round(Double.parseDouble(lbl_ext_int.getText().trim()));*/
					trn_amt=recoveryform.getTxt_entAmo();
					System.out.println("**********trn_amt**********" + trn_amt);
				    others=loanreportobject.getOtherAmt();
					System.out.println("***********Others************" + others );
					extra= loanreportobject.getExtraIntPaid();
					System.out.println("**********extra*********" + extra);
					principal=recoveryform.getTxt_ammount()-int_amt;
					if(trn_amt > others){
	                	
						req.setAttribute("trfothers", others);
						trn_amt = trn_amt - others;
						req.setAttribute("trfintamt", trn_amt);
						req.setAttribute("trfextraint", extra);
						req.setAttribute("cashprinciple",principal );
						req.setAttribute("cashintrate", int_rate);
	                }else{
	                	req.setAttribute("trfothers", trn_amt);
	                	trn_amt=0;
	                	req.setAttribute("trfintamt", trn_amt);
						req.setAttribute("trfextraint", extra);
						req.setAttribute("cashprinciple", principal);
						req.setAttribute("cashintrate", int_rate);
	                }
	                
					if(trn_amt>int_amt)
					{
						
						req.setAttribute("trfintamt", int_amt);
						trn_amt = trn_amt - int_amt;
						req.setAttribute("cashprinciple",principal);
						req.setAttribute("cashintrate", int_rate);
						req.setAttribute("cashintpaiduptodate", Validations.addDays(LDDelegate.getSysDate(),-1));
					}
					else
					{
						trn_amt = 0;
						req.setAttribute("trfintamt", trn_amt);
						req.setAttribute("cashprinciple", principal);
						req.setAttribute("cashintrate", int_rate);
						masterObject.loans.LoanTransactionObject ltrn= lddeligate.calculateInterestForRecovery(loanreportobject.getAccType(),loanreportobject.getAccNo(),lddeligate.getSysDate(),trn_amt,-1);
						if(ltrn !=null)
						{
							req.setAttribute("cashintpaiduptodate",ltrn.getIntUptoDate());
						}
						else
						{
							req.setAttribute("cashintpaiduptodate",null);
						}
						 
					}
					
					req.setAttribute("cashintpaiduptodate",loanreportobject.getIntUptoDate());
					req.setAttribute("cashprinciple", principal);
					req.setAttribute("cashtotalamt", recoveryform.getTxt_entAmo());
					req.setAttribute("cashtotalamt", recoveryform.getTxt_ammount());
					req.setAttribute("cashintrate", loanreportobject.getLoanIntRate());
					}
			
				catch(Exception e)
				{
					e.printStackTrace();
				}
		
			}	
		
		}
		
		if(loanreportobject!=null)
		{
		int cid=loanreportobject.getCID();	
			 try{
				 req.setAttribute("panelName","Personal");
				 req.setAttribute("personalInfo",custdel.getCustomerdetails(cid));
			 	}catch (Exception e) 
			 	{
			 		e.printStackTrace();
			 	}
			
			if(loanreportobject.getDepositAccType().substring(1,4).equals("003") || loanreportobject.getDepositAccType().substring(1,4).equals("004") || loanreportobject.getDepositAccType().substring(1,4).equals("005"))
			{
				
				for(int i=0;i<array_deposit_moduleobject.length;i++)
				{
					if(array_deposit_moduleobject[i].getModuleCode().equals(loanreportobject.getDepositAccType()) )
					{
						req.setAttribute("DepositAccNum",array_deposit_moduleobject[i].getModuleAbbrv()+"  "+loanreportobject.getDepositAccNo()+" -----------"+array_deposit_moduleobject[i].getModuleDesc());
						break;
					}
				}
				req.setAttribute("Depdate",loanreportobject.getDepDate());
				req.setAttribute("matdate",loanreportobject.getMaturityDate());
				req.setAttribute("depamt",loanreportobject.getDepositAmt());
				req.setAttribute("matammount",loanreportobject.getMaturityAmt());
				req.setAttribute("loanbalance",loanreportobject.getLoanBalance());
				req.setAttribute("amtpaid",loanreportobject.getInterestPaid()+loanreportobject.getExtraIntPaid());
				req.setAttribute("intpaiduptodate",loanreportobject.getIntUptoDate());
				req.setAttribute("intamt",loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid());
				req.setAttribute("sancdate",loanreportobject.getSanctionDate());
				req.setAttribute("sancamt",loanreportobject.getSanctionedAmount());
				req.setAttribute("dep_int_uptodate",loanreportobject.getIntUptoDate());
				req.setAttribute("sancpur",loanreportobject.getLoanPurposeDesc());
				req.setAttribute("Interestpaid",loanreportobject.getInterestPaid());
				

				if(loanreportobject.getIntFreq().equalsIgnoreCase("Q"))
				    req.setAttribute("int_freq","Quarterly");
				else if(loanreportobject.getIntFreq().equalsIgnoreCase("M"))
					req.setAttribute("int_freq","Monthly");
					  
				else if(loanreportobject.getIntFreq().equalsIgnoreCase("Y"))
					req.setAttribute("int_freq","Yearly");
					
				else if(loanreportobject.getIntFreq().equalsIgnoreCase("H"))
					req.setAttribute("int_freq","Half-Yearly");
					 
				else if(loanreportobject.getIntFreq().equalsIgnoreCase("O"))
					req.setAttribute("int_freq","On Maturity");
					
				else if(loanreportobject.getIntFreq()==null)
					req.setAttribute("int_freq","------------");
					
				else 
					req.setAttribute("int_freq",loanreportobject.getIntFreq());

				if(loanreportobject.getCloseDate()==null)
					req.setAttribute("status","ACTIVE");
					
				else if(loanreportobject.getCloseDate()!=null)
					req.setAttribute("status","CLOSED");
				
				req.setAttribute("reptnum",loanreportobject.getRctNo());
				
				
				req.setAttribute("intrate",loanreportobject.getLoanIntRate());
				
				 System.out.println("Ammount======"+recoveryform.getCashform().getTxt_ammount());
				
				 
				 double interest=loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid();
				 if(interest!=0)
				 {
					int ammount=recoveryform.getCashform().getTxt_ammount();
					 System.out.println("Ammount======"+recoveryform.getCashform().getTxt_ammount());
					 
				 }
				/*lbl_sanc_purpose.setText(loanreportobject.getLoanPurposeDesc());
				System.out.println("lbl_int_rate==>"+loanreportobject.getLoanIntRate());
				lbl_int_rate.setText(String.valueOf(nf.format(loanreportobject.getLoanIntRate())));
                */ 			
			}
			else if(loanreportobject.getDepositAccType().substring(1,4).equals("006"))
			{   
				
				for(int i=0;i<array_deposit_moduleobject.length;i++)
				{
					if(array_deposit_moduleobject[i].getModuleCode().equals(loanreportobject.getDepositAccType()) )
					{    
						
						req.setAttribute("DepositAccNum",array_deposit_moduleobject[i].getModuleAbbrv()+"  "+loanreportobject.getDepositAccNo()+" -----------"+array_deposit_moduleobject[i].getModuleDesc());
						break; 
					}
				}
				req.setAttribute("Depdate",loanreportobject.getDepDate());
				req.setAttribute("matdate","-----------------");
				req.setAttribute("depamt",0.0);
				req.setAttribute("matammount",0.0);
				req.setAttribute("loanbalance",loanreportobject.getLoanBalance());
				req.setAttribute("amtpaid",loanreportobject.getInterestPaid()+loanreportobject.getExtraIntPaid());
				//req.setAttribute("intpaiduptodate",loanreportobject.getIntUptoDate());
				req.setAttribute("intamt",loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid());
				req.setAttribute("sancdate",loanreportobject.getSanctionDate());
				req.setAttribute("sancamt",loanreportobject.getSanctionedAmount());
				//req.setAttribute("dep_int_uptodate","---------------");
				req.setAttribute("sancpur",loanreportobject.getLoanPurposeDesc());
				req.setAttribute("reptnum",0);
				req.setAttribute("int_freq","---------");
				req.setAttribute("intrate",loanreportobject.getLoanIntRate());
				if(loanreportobject.getCloseDate()==null)
					req.setAttribute("status","ACTIVE"); 
				else if(loanreportobject.getCloseDate()!=null)
					req.setAttribute("status","CLOSED");
			
			}
			
			
		}
		return loanreportobject;
		
	}	/*lbl_adj_principal.setText(String.valueOf(nf.format(principal)));
        lbl_adj_int_paid_upto.setText(Validations.addDays(MainScreen.head.getSysDate(),-1));
        lbl_adj_total_amt.setText(nf.format(Double.parseDouble(txt_cash_amt.getText().trim())));
        lbl_adj_int_rate.setText(nf.format(Double.parseDouble(lbl_int_rate.getText())));
        txt_cash_amt.setText(nf.format(Double.parseDouble(txt_cash_amt.getText().trim())));
        */
	
	private void getrecovery_Tabbedpane(HttpServletRequest req,LDRecoveryForm recoveryform)
	{  
		String pageActions[]={"/LDRecovery?tabPaneHeading=Personal&pageId="+recoveryform.getPageId(),"/LDRecovery?tabPaneHeading=Status&pageId="+recoveryform.getPageId(),"/LDRecovery?tabPaneHeading=Cash&pageId="+recoveryform.getPageId(),"/LDRecovery?tabPaneHeading=Transfer&pageId="+recoveryform.getPageId()};
		String tabHeading[]={"Personal","Status","Cash","Transfer"};
		System.out.println("recovery pageId"+recoveryform.getPageId()); 
		String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("6005").trim(),MenuNameReader.getScreenProperties("6006").trim(),MenuNameReader.getScreenProperties("6007").trim()};

		req.setAttribute("TabHeading",tabHeading);
		req.setAttribute("PageActions",pageActions);
		req.setAttribute("PagePath",pagePath);
		req.setAttribute("pageNum","3003");
		
	}
	private void recovery_accountdetail(HttpServletRequest req,LDRecoveryForm recoveryform,LDDelegate deligate)throws RemoteException,SQLException
	{
   		    System.out.println("sb account num===========>"+recoveryform.getTxt_tranfrom());
			if(recoveryform.getTxt_tranfrom()!=null)
			{	
				AccountObject trf_acc=deligate.getAccount(null,array_transfer_moduleobject[Integer.valueOf(recoveryform.getTxt_tranfrom())].getModuleCode(),recoveryform.getTxt_tracNum(),LDDelegate.getSysDate());
		
				if(trf_acc!=null)
				{ 
					String acc_name=trf_acc.getAccname();
					double acc_balance=trf_acc.getAmount();
					req.setAttribute("Acc_name",acc_name);
					req.setAttribute("Accbal",acc_balance);
				}
			
			}
			else
			{
				req.setAttribute("Accbal",0.0);
				req.setAttribute("cashprinciple",0.0);
				req.setAttribute("cashint_rate",0.0);
				req.setAttribute("cashtotalamt",0.0);
			}
			
			
		
		
	}
	
	
	//Methods taken from Validations===============================================================================================
	public 	static double rdIntReCal(double amt,double rate,String opdate,String closedate,double arr[],String trndate[]) throws NamingException, RemoteException, CreateException, InterestCalculation,ServiceLocatorException
	{ 
		int length = arr.length,qrts,months=0,t=0;
		int no_of_days=0;
		
		double intrest=0.0,nextamt=amt;
		String fromdate=opdate,todate=opdate,date=opdate;
		LDDelegate deligate=new LDDelegate();
        months=noOfMonths(opdate,closedate);
       
        System.out.println("length==>"+length);
        System.out.println("months==>"+months);
        qrts=months/3;
        System.out.println("Qtrs==>"+qrts);
        
	try{
		for(int i=1;i<=qrts;i++)
		{
			int loop=3;
			todate=deligate.getFutureMonthDate(todate,3);
			System.out.println("todate==>"+todate);
			while(loop>=1)
		  	{
					System.out.println("t==>"+t);
					if(t==length-1)
						break;
					
		  			if(t<length){
		  				fromdate= trndate[t];
		  				date=trndate[t+1];
		  				nextamt = arr[t]; 
		  			}
		  			else
		  				date=closedate;
		  			System.out.println("date==>"+date);
		  			
		  			t++;
		  			
		  			no_of_days=deligate.getDaysFromTwoDate(date,todate);
		  			System.out.println("days==>"+no_of_days);
		  			if(no_of_days<=0)
		  			{
		  				no_of_days=deligate.getDaysFromTwoDate(fromdate,todate);
		  				System.out.println("days==>"+no_of_days);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  				//break;
		  				no_of_days=deligate.getDaysFromTwoDate(todate,date);
		  				System.out.println("days==>"+no_of_days);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  				
		  			}
		  			else{
		  				no_of_days=deligate.getDaysFromTwoDate(fromdate,date);
		  				System.out.println("amt==>"+amt);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  			}
		  			amt+=nextamt;
		  			loop--;
			}
		  	amt+=intrest;intrest=0.0;
		}
		while(t<length-1){
			System.out.println("t==>"+t);
			fromdate= trndate[t]; 
			if(t<length)
  				date=trndate[t+1];
			else
				date=closedate;
  			nextamt = arr[t]; 
  			t++;
  			todate=closedate;
  			System.out.println("todate==>"+todate);
  			no_of_days=deligate.getDaysFromTwoDate(date,todate);
  			System.out.println("days==>"+no_of_days);
  			if(no_of_days<=0)
  			{
  				no_of_days=deligate.getDaysFromTwoDate(fromdate,todate);
  				System.out.println("amt==>"+amt);
  				intrest+=Math.round(amt*no_of_days*rate/36500);
  				//break;
  				no_of_days=deligate.getDaysFromTwoDate(todate,date);
  				System.out.println("days==>"+no_of_days);
  				intrest+=Math.round(amt*no_of_days*rate/36500);
  			}
  			else{
  			no_of_days=deligate.getDaysFromTwoDate(fromdate,date);
  			System.out.println("amt==>"+amt);
  			intrest+=Math.round(amt*no_of_days*rate/36500);
  			}
  			if(t!=length-1)
  			amt+=nextamt;
  			fromdate=date;
  			//todate=date;
		}
		System.out.println("interest==>"+intrest);
	}
	catch(Exception e){e.printStackTrace();}
		return Math.round(amt+intrest);
	}
	
	
	public static int noOfMonths(String fromdate,String todate)
	{
		StringTokenizer fd,td;
		int m1,m2,y1,y2,d1,d2;

		fd=new StringTokenizer(fromdate,"/");
		d1 =Integer.parseInt(fd.nextToken());
		m1 =Integer.parseInt(fd.nextToken());
		y1=Integer.parseInt(fd.nextToken());

		td=new StringTokenizer(todate,"/");
		d2 =Integer.parseInt(td.nextToken());
		m2 =Integer.parseInt(td.nextToken());
		y2=Integer.parseInt(td.nextToken());
		int no_of_months=0;
		while(y1<y2)
		{
			no_of_months++;
			if(++m1==13){
				y1++;
				m1=1;
			}
		}
		while(true)
		{
			if(m1==m2)
				break;
			no_of_months++;
			m1++;
		}
		if(y2>y1)
		{
		while(y2>(y1+1))
		{
				for(int i=0;i<12;i++)
				{
					no_of_months++;
					//System.out.println("i amm in year");
				}
		     	y1++;
		     	//System.out.println("y1="+y1);
		}
			
		for(int i=m1;i<12;i++)
		{
			//System.out.println("i amm in m1");
			no_of_months++;
		}
		for(int i=0;i<(m2-1);i++)
		{
			
			//System.out.println("i amm in m2"+m2);
			no_of_months++;
		}
		}
		else if(y2==y1)
		{
			if(m2>m1)
			{
			for(int i=m1;i<(m2-1);i++)
			{
			no_of_months++;
			}
			}
		}


		
		System.out.println("testing111111111111111111111"+no_of_months);
			
		return no_of_months;
	}
	
	//===============================================================================================================================
    
	private LoanReportObject[] passbookdesc(HttpServletRequest req,LDDelegate deligate,LDPassbookForm passbookform)
	{
		try
		{
		String sub_code;	
			
		String combo_loantype=passbookform.getCombo_actype();
		int accno=passbookform.getTxt_acno();
		System.out.println("loan type in passbook==========>"+combo_loantype);
		System.out.println("loan accno in passbook==========>"+accno);
		//array_ln_moduleobject=deligate.getMainModules(2,"1008000");
		reportobject=deligate.getPassBook(array_ln_moduleobject[Integer.valueOf(combo_loantype)].getModuleCode(),accno);
		Map mobjectmap=null;
		List listobj=new ArrayList();
		if(reportobject!=null)
		{ 
			System.out.println("length===---===--===>"+reportobject.length);
			listobj.clear();
			
			for(int i=0;i<reportobject.length;i++)
			{
				mobjectmap=new HashMap();
				
				mobjectmap.put("TranDate",reportobject[i].getTransactionDate());
				System.out.println("i value=====>"+i);
				if(reportobject[i].getTranType().equals("D"))
				{	   
					System.out.println("******Inside Debit Entry*******"+reportobject[i].getTranMode());
					System.out.println("Transaction Narr====>"+reportobject[i].getTranNarration());
					 
					if(reportobject[i].getTranMode().equals("C"))
						mobjectmap.put("ParticularNarr",reportobject[i].getReferenceNo());
					else if(reportobject[i].getTranMode().equals("T"))
						mobjectmap.put("ParticularNarr",reportobject[i].getTranNarration());
					else if(reportobject[i].getTranMode().equals("P"))
						mobjectmap.put("ParticularNarr",reportobject[i].getTranNarration());
						
					mobjectmap.put("Principal",new String(""));
					mobjectmap.put("Interest",new String(""));
					mobjectmap.put("Other",new String(""));
					mobjectmap.put("total",new String(""));
					mobjectmap.put("Debit",reportobject[i].getPrincipalPaid());
					mobjectmap.put("balance",reportobject[i].getLoanBalance());
										
				}
				else if(reportobject[i].getTranType().equals("R"))
				{
					System.out.println("******Tran Mode*******"+reportobject[i].getTranMode());
					System.out.println("Ref Num====>"+reportobject[i].getReferenceNo());
					System.out.println("Tran Narr==-==>"+reportobject[i].getTranNarration());
					
					mobjectmap.put("balance",reportobject[i].getLoanBalance());
					
					if(reportobject[i].getTranMode().equals("C"))
						mobjectmap.put("ParticularNarr",reportobject[i].getReferenceNo());
					else if(reportobject[i].getTranMode().equals("T"))
							mobjectmap.put("ParticularNarr",reportobject[i].getTranNarration());
											
					mobjectmap.put("Debit",new String());	
					mobjectmap.put("Principal",reportobject[i].getPrincipalPaid());
					mobjectmap.put("Interest",reportobject[i].getInterestPaid()+reportobject[i].getExtraIntPaid());
					mobjectmap.put("Other",reportobject[i].getOtherAmt()); 
					mobjectmap.put("total",reportobject[i].getInterestPaid()+reportobject[i].getExtraIntPaid()+reportobject[i].getPrincipalPaid()+reportobject[i].getOtherAmt());
					
				} 
				System.out.println("Map Size====>"+mobjectmap.size());
				listobj.add(mobjectmap); 							
			}
			
			req.setAttribute("PassBook",listobj);
		
			/*for(int i=0;i<reportobject.length;i++)
			{ 
				if(reportobject[i].getTranType().equals("R"))
				{
					req.setAttribute("PassBook2",reportobject);
					
				}
			}*/
					sub_code=reportobject[0].getDepositAccType();
				    req.setAttribute("Name",reportobject[0].getName());
					req.setAttribute("Dep_date",reportobject[0].getDepDate());
					req.setAttribute("sanc_date",reportobject[0].getSanctionDate());//string
					req.setAttribute("Dep_actype",reportobject[0].getDepositAccType());//string
					if(reportobject[0].getCloseDate()==null)
					
						req.setAttribute("acc_status","Active");
					else
						
						req.setAttribute("acc_status","Closed");
					
					req.setAttribute("sanc_amt",reportobject[0].getSanctionedAmount());//double
					req.setAttribute("Dep_acno",reportobject[0].getDepositAccNo());//int
					req.setAttribute("cid",reportobject[0].getCID());//int
					req.setAttribute("phonenum",reportobject[0].getPhoneNo());//int
					req.setAttribute("Nom_num",reportobject[0].getNomNo());//int
					req.setAttribute("mailid",reportobject[0].getEmail());//String
					req.setAttribute("Int_rate",reportobject[0].getInterestPaid());//double
					req.setAttribute("Loan_rate",reportobject[0].getLoanIntRate());//double
					if(sub_code.substring(1,4).equals("003")|| sub_code.substring(1,4).equals("004") || sub_code.substring(1,4).equals("005"))
					{
						req.setAttribute("period_in_days",reportobject[0].getDepositDays());//int
						req.setAttribute("Dep_date",reportobject[0].getDepDate());
						req.setAttribute("Mat_dat",reportobject[0].getMaturityDate());//string
						req.setAttribute("Mat_amt",reportobject[0].getMaturityAmt());//double
						req.setAttribute("Dep_amt",reportobject[0].getDepositAmt());//double
					}
					else
					{
						req.setAttribute("period_in_days",0);//int
						req.setAttribute("Mat_amt",0.0);//double
						req.setAttribute("Dep_amt",0.0);//double
					
					}
				
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		return reportobject;
	}
	private LoanReportObject[] openclosedrep(LDDelegate deligate,LDOpenClosedForm openclosedform,HttpServletRequest req)throws RemoteException,SQLException
	{
		LoanReportObject[] reportobj=null,reportobj1=null;
		openclosedform.getCombo_account();
		openclosedform.getCombo_acctype();
		System.out.println("acc type==============>"+openclosedform.getCombo_acctype());
		//array_loanreportobject=loansondepositremote.getOpenedAccounts(txt_from_date.getText(),txt_to_date.getText(),null,1);
		if(openclosedform.getCombo_account().equalsIgnoreCase("open"))
		{	
			System.out.println("********inside open accounts**********");
			if(openclosedform.getCombo_acctype().equalsIgnoreCase("alltype"))
			{
			reportobj=deligate.getOpenedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),null,1);
			OpenClosedRepDet(reportobj,req);
			}
			else
			{   System.out.println("************inside the Open Individual***********");  
				reportobj1=deligate.getOpenedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),array_ln_moduleobject[Integer.valueOf(openclosedform.getCombo_acctype())].getModuleCode(),0);
				OpenClosedRepDet(reportobj1,req);
			}
		}
		else if(openclosedform.getCombo_account().equalsIgnoreCase("close"))
		{	
			System.out.println("********inside closed accounts**********");
			if(openclosedform.getCombo_acctype().equalsIgnoreCase("alltype"))
			{
			reportobj=deligate.getClosedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),null,1);
			OpenClosedRepDet(reportobj,req);
			
			}
			else
			{ 	System.out.println("************inside the Closed Individual***********");
				reportobj=deligate.getClosedAccounts(openclosedform.getTxt_frm_dt(),openclosedform.getTxt_to_dt(),array_ln_moduleobject[Integer.valueOf(openclosedform.getCombo_acctype())].getModuleCode(),0);
				OpenClosedRepDet(reportobj,req);
			}
		}
		
		return reportobj;
		
	}
	private void IntrestAccuredreport(LDDelegate deligate,IntrestAccruedReportForm intaccuredform,HttpServletRequest req)
	{
		try
		{
		if(intaccuredform.getCombo_acctype()!=null)
		{	
		LoanReportObject[] reportobj=deligate.getAccruedInterest(deligate.getSysDate(),array_ln_moduleobject[Integer.valueOf(intaccuredform.getCombo_acctype())].getModuleCode());
		req.setAttribute("IntrestAccured",reportobj);
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void MaturityList(LDDelegate deligate,HttpServletRequest req,LDMaturityListForm maturityform)
	{
	try
	{ 
		//int_type_report=1 for Mat list
		if(maturityform.getCombo_acctype()!=null)
			{
				if(maturityform.getCombo_acctype().equalsIgnoreCase("alltype"))
				{
					System.out.println("from date=-=======>"+maturityform.getTxt_fromdate());
					System.out.println("To date======>"+maturityform.getTxt_todate());
					LoanReportObject[] repobj=deligate.getLDMaturityList(maturityform.getTxt_fromdate(),maturityform.getTxt_todate(),null,1);
					MatlistValues(repobj,req,1);
				}
				else 
				{
					System.out.println("*********inside else**********"); 
					LoanReportObject[] repobj2=deligate.getLDMaturityList(maturityform.getTxt_fromdate(),maturityform.getTxt_todate(),array_ln_moduleobject[Integer.valueOf(maturityform.getCombo_acctype())].getModuleCode(),2);
					MatlistValues(repobj2,req,1);
				}
			}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
		
	}
	public void LDExceedingMatList(HttpServletRequest req,LDExcedingMatListForm excedform,LDDelegate deligate)
	{
	try
	{	
		if(excedform.getCombo_acctype()!=null)
		{
			if(excedform.getCombo_acctype().equalsIgnoreCase("alltype"))
			{
				LoanReportObject[] repobj_exce = deligate.getLDExceedingMaturity(excedform.getTxt_date1(),excedform.getTxt_date2(),null,1);
				System.out.println("repobj_exce=========>"+repobj_exce.toString());
				MatlistValues(repobj_exce,req,0);
			}
			else
			{
				System.out.println("*********inside else**********"); 
				LoanReportObject[] repobj2=deligate.getLDExceedingMaturity(excedform.getTxt_date1(),excedform.getTxt_date2(),array_ln_moduleobject[Integer.valueOf(excedform.getCombo_acctype())].getModuleCode(),2);
				System.out.println("repobj2=========>"+repobj2.toString());
				MatlistValues(repobj2,req,0);
					/*req.setAttribute( "ExcedMatruityList",repobj2 );
					req.setAttribute("deposit_moduleobject",array_deposit_moduleobject);
					System.out.println("length========>"+repobj2.length);
					if ( repobj2 != null )  { 
						for(int i=0;i<repobj2.length;i++){
						System.out.println("value of i              "+i);	
						System.out.println(repobj2[i].getAccNo() + "account no "  );
						System.out.println(  repobj2[i].getName() + " Account holder name ");
						}
					}
*/					
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace(); 
		} 
	
	}
	
	//this method is called on focuslost of loan accnum 
	// to check whether loan acc num is verified or we can do update or not
	
	private void loanAccountdetail(LoanApplicationDE applde,HttpServletRequest req,LDDelegate deligate,ActionMapping map)
	{    
		try 
		{   
			hash_obj=deligate.getLoanTransaction(applde.getTxt_LoanActype(),applde.getTxt_loanaccno());
			System.out.println("hash_obj======>"+hash_obj);
			if(hash_obj!=null)  
			{
				loanmastobj=(LoanMasterObject)hash_obj.get("LoanMasterObject");
				loantranobj=(LoanTransactionObject)hash_obj.get("LoanTransaction");
				req.setAttribute("loanmastobj",loanmastobj);
				req.setAttribute("loantranobj",loantranobj);
				req.setAttribute("DepositAmmount",0.0);
				req.setAttribute("CurrentIntRate",0.0);   
				req.setAttribute("AmmountEligable",0.0); 
				req.setAttribute("reqamounts",0.0);//double
				req.setAttribute("depaccnum",0);//int
				req.setAttribute("applsrlnum",0);//int
				
				if(loanmastobj!=null)
				{	
				
				if(loanmastobj.getClosedt()!=null)
				{
					
					applde.setLoanacc_closed("closed");
					req.setAttribute("reqamounts",0.0);//double
					req.setAttribute("depaccnum",0);//int
					req.setAttribute("applsrlnum",0);//int
					req.setAttribute("sanctionamt",0.0);//double
					req.setAttribute("principlebal",0.0);//double
					req.setAttribute("CurrentIntRate",0.0);//double

				}
			}
		}
		 	if(loantranobj!=null && applde.getValue()!=null)
		 	{	
		 		if( applde.getValue().equalsIgnoreCase("1") &&  loantranobj.uv.getVerTml()==null)
		 		{
		 			System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiii hello");
		 			applde.setLoanacnum("notverified");
		 			showDetail(applde,applde.getTxt_LoanActype(),applde.getTxt_loanaccno(),deligate,req,0,map);
		 		}
		 		if(applde.getValue().equalsIgnoreCase("1") &&  loantranobj.uv.getVerTml()!=null && applde.getTxt_loanaccno()!=0 )
		 		{
		 			applde.setLoanacnum("VerifiedAlready");
		 		}
		 	}
		 	else
		 	{
		 		applde.setLoanacc_notfound("NotFound");
		 		req.setAttribute("reqamounts",0.0);//double
		 		req.setAttribute("depaccnum",0);//int
		 		req.setAttribute("applsrlnum",0);//int
		 		req.setAttribute("sanctionamt",0.0);//double
		 		req.setAttribute("principlebal",0.0);//double
		 		req.setAttribute("CurrentIntRate",0.0);//double

		 	}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements(""+e,req,path);
			//return map.findForward(ResultHelp.getError());
		}
	}
	
	//method used in validation to calculate renivst
	
	private static double reinvestmentCalc(double amt,String depdate,String matdate,double rate,LDDelegate deligate) throws NamingException, RemoteException, CreateException
	{
    	
    	String todate=depdate;
		String nextdate = deligate.getFutureMonthDate(todate,3);
		double pamt=amt;
		int no_of_days = 0;
		while(deligate.getDaysFromTwoDate(nextdate,matdate) > 0) {
		    no_of_days = deligate.getDaysFromTwoDate(todate,nextdate);
		    pamt += ((pamt*rate*no_of_days)/(36500));
		    todate = nextdate;
		    nextdate = deligate.getFutureMonthDate(todate,3);
		                
		}
		no_of_days = deligate.getDaysFromTwoDate(todate, matdate);
	    pamt += ((pamt*rate*no_of_days)/(36500));	     
		return Math.round(pamt);
	}
	
	private void getscreendetail_updation(LoanApplicationDE appldeform,LoanMasterObject loanmasterobj2)
	{
		 
		loanmasterobject=new LoanMasterObject(); 
		loanmasterobject.setAccType(loanmasterobj2.getAccType()); 
		if(appldeform.getTxt_loanaccno()>0)   
			loanmasterobject.setAccNo(loanmasterobj2.getAccNo());	
		 
		loanmasterobject.setApplicationSrlNo(loanmasterobj2.getApplicationSrlNo());
		loanmasterobject.setApplicationDate(loanmasterobj2.getApplicationDate());
		loanmasterobject.setRequiredAmount(appldeform.getTxt_reqammount());
		loanmasterobject.setSanctionedAmount(appldeform.getTxt_reqammount());
		
		System.out.println("laon recovery===>  I"+loanmasterobj2.getAuto_loan()); 
		
		if(appldeform.getTxt_AutoLoanRecovery().equalsIgnoreCase("Interest"))
		{
			loanmasterobject.setAuto_loan("I");
		}if(appldeform.getTxt_AutoLoanRecovery().equalsIgnoreCase("Total Amount"))
		{
			loanmasterobject.setAuto_loan("T"); 
		}else
			loanmasterobject.setAuto_loan("N");
		    
		StringTokenizer str=new StringTokenizer(appldeform.getTxt_purpose()); 
		
		
		loanmasterobject.setPurposeCode(loanmasterobj2.getPurposeCode());
		
		loanmasterobject.setDepositAccType(array_deposit_moduleobject[Integer.valueOf(appldeform.getTxt_reftype())].getModuleCode());
		
		loanmasterobject.setDepositAccNo(appldeform.getTxt_refno());
		
		System.out.println("loanmasterobj.getPayMode()==========> c"+loanmasterobj2.getPayMode());
		 
		if(appldeform.getTxt_paymode().equalsIgnoreCase("Cash"))
		{
			loanmasterobject.setPayMode("C");
		}
		else if(appldeform.getTxt_paymode().equalsIgnoreCase("Transfer"))
		{
			loanmasterobject.setPayMode("T");
			loanmasterobject.setPaymentAccno(loanmasterobj2.getPaymentAccno());
			loanmasterobject.setPaymentAcctype(appldeform.getTxt_payactype());
		
			
		}
		else if(appldeform.getTxt_paymode().equalsIgnoreCase("PayOrder"))
			loanmasterobject.setPayMode("P");
		
		loanmasterobject.uv.setUserId(lduser);
		loanmasterobject.uv.setUserTml(ldtml);
		loanmasterobject.setInterestRate(loanmasterobj2.getInterestRate());

		
		
	}
	
	private void Recovery_verification(LDRecoveryForm recoveryform,HttpServletRequest req,LDDelegate deligate)
	{
		try
		{
			int vocher_no=recoveryform.getTxt_voucherno();
			System.out.println("Voucher Number===========>"+vocher_no);
			LoanTransactionObject loan_tran_obj=deligate.getLoanTransaction(Integer.valueOf(vocher_no));
						
			System.out.println("Loan transaction object============>"+loan_tran_obj);
			if(loan_tran_obj!=null)
			{
				System.out.println("Loan trn objects ac_no -------------->"+loan_tran_obj.getAccNo());
				System.out.println("Loan trn objects ac_typ-------------->"+loan_tran_obj.getAccType());
				String ac_typ= loan_tran_obj.getAccType();
				req.setAttribute("loantrn",loan_tran_obj);
				recoveryform.setTxt_accno(loan_tran_obj.getAccNo());
				recoveryform.setTxt_entAmo(loan_tran_obj.getTransactionAmount());
				//System.out.println("======+++++++++++++++++======>"+recoveryform.getTxt_entAmo());
				
				ModuleObject[] moo = deligate.getMainModules(3,ac_typ);
			     	req.setAttribute("ac_type_ver",moo);
			    
				StringTokenizer str_token=new StringTokenizer(loan_tran_obj.getTranNarration());
				String type=str_token.nextToken();
				for(int i=0;i<array_transfer_moduleobject.length;i++)
				{
					if(array_transfer_moduleobject[i].getModuleCode().equals(type))
					{
						System.out.println("Array ac_type--------->"+array_transfer_moduleobject[i].getModuleAbbrv());
						 req.setAttribute("sb_index",String.valueOf(i));
						 req.setAttribute("sb_abbr",array_transfer_moduleobject[i].getModuleAbbrv());
						 req.setAttribute("Accbal",0.0);
						 req.setAttribute("cashprinciple",0.0);
 						 req.setAttribute("cashtotalamt",0.0); 
						 req.setAttribute("cashint_rate",0.0);
						
					}
					
				}
				
				recoveryform.setTxt_tracNum(Integer.valueOf(str_token.nextToken())); 
				
				for(int i=0;i<array_ln_moduleobject.length;i++)
				{	 
					System.out.println(""+array_ln_moduleobject[i].getModuleCode()+"\n"+"loan transaction object===========>"+loan_tran_obj.getAccType());
					if(array_ln_moduleobject[i].getModuleCode().equals(loan_tran_obj.getAccType()))
					{
						System.out.println("index===========>====>"+i);
						
						String loan_acc_type=array_ln_moduleobject[i].getModuleAbbrv();
						
						req.setAttribute("loan_acc_num",String.valueOf(i));
						req.setAttribute("loan_acc_type",loan_acc_type);
						
						//System.out.println(""+loan_acc_type);
					} 
				}
				
				System.out.println("==============================>"+recoveryform.getTxt_tracNum());
				recoveryform.setTxt_ammount(loan_tran_obj.getTransactionAmount());
				
				if(recoveryform.getTxt_tranfrom()!=null)
				{	
				
      			AccountObject acc_obj=deligate.getAccount(null,array_transfer_moduleobject[Integer.valueOf(recoveryform.getTxt_tranfrom())].getModuleCode(),recoveryform.getTxt_tracNum(),deligate.getSysDate());
				
      			System.out.println("Account object==========>"+acc_obj);
      			
      			if(acc_obj!=null)
      			{
      				recoveryform.setTxt_custname(acc_obj.getAccname());
      				req.setAttribute("Accbal",acc_obj.getAmount());
      				
      				//recoveryform.setTxt_bal((acc_obj.getAmount()));
      				 
      				// req.setAttribute("Accbal",0.0);
					 req.setAttribute("cashprinciple",0.0);
					 req.setAttribute("cashtotalamt",0.0); 
					 req.setAttribute("cashint_rate",0.0);
      			}         
      			else
      			{
      				 req.setAttribute("Accbal",0.0);
					 req.setAttribute("cashprinciple",0.0);
					 req.setAttribute("cashtotalamt",0.0); 
					 req.setAttribute("cashint_rate",0.0);
      			}
				//getAccount(null,String.valueOf(array_transfer_moduleobject[Integer.valueOf(recoveryform.getTxt_tranfrom())].getModuleCode()),recoveryform.getTxt_tracNum(),deligate.getSysDate());
				
				//array_loan_modules[combo_ln_type.getSelectedIndex()-1].getModuleCode(),Integer.parseInt(txt_ln_ac_no.getText()),MainScreen.head.getSysDate()
				//loanmasterobject=deligate.getLoanDetails(array_ln_moduleobject[], acno, date);
				//recoveryform.setTxt_tracNum(loan_tran_obj.get)
			}
				
			}	
		}catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void MatlistValues(LoanReportObject[] repobj,HttpServletRequest req,int int_type_report)
	{
		
		Map mapobj=null;
		List list=new ArrayList(); 
		System.out.println("rep o Bject=========>"+repobj);
		if(repobj!=null)
		{	
		for(int i=0;i<repobj.length;i++)
		{
			mapobj=new TreeMap();
			if(int_type_report==1)
			{	
				mapobj.put("AcType",repobj[i].getDepositAccType());
				mapobj.put("AccNo",repobj[i].getAccNo());
				mapobj.put("Name",repobj[i].getName());
			}
			else
			    //data[0]="   "+array_loanreportobject[i].getDepositAccType()+"    "+array_loanreportobject[i].getAccNo()+"  "+array_loanreportobject[i].getName();
			{
				for(int k=0;k<array_deposit_moduleobject.length;k++)
				{
					//file_logger.info("dep_acc==>"+array_loanreportobject[i].getDepositAccType());
					if(repobj[i].getDepositAccType()!=null)
					if(repobj[i].getDepositAccType().equals(array_deposit_moduleobject[k].getModuleCode()) )
					{
						mapobj.put("AcType",array_deposit_moduleobject[k].getModuleAbbrv());
						mapobj.put("AccNo",repobj[i].getDepositAccNo());
						mapobj.put("Name",repobj[i].getName());
							/*data[1]="   "+array_dep_moduleobject[k].getModuleAbbrv();
							data[2]="  "+array_loanreportobject[i].getDepositAccNo();
							data[3]=" "+array_loanreportobject[i].getName();//" --- "+array_dep_moduleobject[k].getModuleDesc();
							*/
						break;
					} 
				}
			}
			mapobj.put("DepDate",repobj[i].getDepDate());
			mapobj.put("MatDate",repobj[i].getMaturityDate());
			mapobj.put("DepAmt",repobj[i].getDepositAmt());
			mapobj.put("MatAmt",repobj[i].getMaturityAmt());
			mapobj.put("TranMode",repobj[i].getTranMode());
			 
			System.out.println("Transaction Mode===>"+repobj[i].getTranMode());
			
			if(int_type_report==1)
				mapobj.put("TranNarr",repobj[i].getTranNarration());
			else
				mapobj.put("TranNarr",repobj[i].getMaturityAmt());
			if(repobj==null)
				req.setAttribute("msg", "no records found");
			for(int k=0;k<array_ln_moduleobject.length-1;k++)
			{
				if(repobj[i].getAccType()==null)
				{
					req.setAttribute("msg", "no records found");
				}
				if(repobj[i].getAccType().equals(array_ln_moduleobject[k].getModuleCode()) )
					{
						mapobj.put("LoanNumber",array_ln_moduleobject[k].getModuleAbbrv()+"  "+repobj[i].getAccNo()+" --- "+array_ln_moduleobject[k].getModuleDesc());
							//data[10]="  "+array_ln_moduleobject[k].getModuleAbbrv()+"  "+array_loanreportobject[i].getAccNo()+" --- "+array_ln_moduleobject[k].getModuleDesc();
								break;
					}
			}
			if(repobj[i].getIntUptoDate()!=null)
				mapobj.put("IntPaidDate",repobj[i].getIntUptoDate());
				
			else 
				mapobj.put("","--------");
			
			
			mapobj.put("LoanIntRat",repobj[i].getLoanIntRate());
			mapobj.put("PrinBal",repobj[i].getLoanBalance());
			mapobj.put("IntDue",repobj[i].getInterestPayable());
			mapobj.put("TotalBala",repobj[i].getInterestPayable()+repobj[i].getLoanBalance());
			list.add(mapobj);
			System.out.println("Map Size=====>"+list.size());
				
	}
				
		req.setAttribute("MatruityList",list);
		}
	}
	
	public void OpenClosedRepDet(LoanReportObject[] reportobj,HttpServletRequest req)
	{
		if(reportobj!=null)
		{	
			Map mapobj=null;
			ArrayList list=new ArrayList();
			for(int i=0;i<reportobj.length;i++)
			{
				mapobj=new HashMap();
				mapobj.put("SrlNo",(i+1));
				//data[0]=String.valueOf(i+1); 
				System.out.println("array loan module object=====>"+array_ln_moduleobject);
				for(int k=0;k<array_ln_moduleobject.length;k++)
				{
					if(reportobj[i].getAccType().equals(array_ln_moduleobject[k].getModuleCode()) )
					{
						mapobj.put("LoanAcNo",array_ln_moduleobject[k].getModuleAbbrv()+"  "+reportobj[i].getAccNo()+"--"+array_ln_moduleobject[k].getModuleDesc());
						//data[1]="  "+array_ln_moduleobject[k].getModuleAbbrv()+"  "+array_loanreportobject[i].getAccNo()+"--"+array_ln_moduleobject[k].getModuleDesc();
						break;
					}
				}
				mapobj.put("NameAddress",reportobj[i].getName());	
				//data[2]="  "+reportobj[i].getName(); 
				System.out.println("Deposit Module Object-----=======>"+array_deposit_moduleobject);
				for(int k=0;k<reportobj.length;k++)
				{
					if(reportobj[i].getDepositAccType().equals(array_deposit_moduleobject[k].getModuleCode()) )
					{
							mapobj.put("DepositAcNo",array_deposit_moduleobject[k].getModuleAbbrv()+"  "+reportobj[i].getDepositAccNo()+"--"+array_deposit_moduleobject[k].getModuleDesc());
							///data[3]="  "+array_dep_moduleobject[k].getModuleAbbrv()+"  "+array_loanreportobject[i].getDepositAccNo()+"--"+array_dep_moduleobject[k].getModuleDesc();
							break;
					}
				}
				
			/**
			 * The below if loop checks for Term Deposit Account types (or)
			 * Pygmy Account types with the help of module code.
			 * The array_loanreportobject[i].getDepositAccType().substring(1,4)
			 *  below would either give 006 or(003/004/005).
			 * If it is 006, it indicates that it is Pygmy Deposit type
			 * and sets certain columns to "-------", else it process the else
			 * loop, which is for Term Deposit Account types.
			 * 
			 */
			if(reportobj[i].getDepositAccType().substring(1,4).equals("006"))
			{
				mapobj.put("DepDate","    "+"---");
				mapobj.put("MatDate","    "+"---");
				mapobj.put("Perioddays","    "+"---");
				mapobj.put("Intrate","    "+"---");
				mapobj.put("Amount","    "+"---");
				
				/*data[4]="    "+"---";
				data[5]="    "+"---";
				data[6]="    "+"---";
				data[7]="    "+"---";
				data[8]="    "+"---";*/
			}
			else
			{
				mapobj.put("DepDate",reportobj[i].getDepDate());
				mapobj.put("MatDate",reportobj[i].getMaturityDate());
				mapobj.put("Perioddays",reportobj[i].getDepositDays());
				mapobj.put("Intrate",reportobj[i].getDepositIntRate());
				mapobj.put("Amount",reportobj[i].getDepositAmt());
				
				System.out.println("Deposit Int Rate=====>"+reportobj[i].getDepositIntRate());
				/*data[4]="    "+array_loanreportobject[i].getDepDate();
				data[5]="    "+array_loanreportobject[i].getMaturityDate();
				data[6]=String.valueOf(array_loanreportobject[i].getDepositDays());
				data[7]=String.valueOf(array_loanreportobject[i].getDepositIntRate());
				data[8]=String.valueOf(array_loanreportobject[i].getDepositAmt());*/
			}
			mapobj.put("AmtAdv",reportobj[i].getSanctionedAmount());
			mapobj.put("LoanRate",reportobj[i].getLoanIntRate());
			mapobj.put("SanDate",reportobj[i].getSanctionDate());
			
			/*data[9]=String.valueOf(array_loanreportobject[i].getSanctionedAmount());
			System.out.println("LOAN INT RATE="+array_loanreportobject[i].getLoanIntRate());
			data[10]=String.valueOf(array_loanreportobject[i].getLoanIntRate());
			data[11]="    "+array_loanreportobject[i].getSanctionDate();

			dtm.addRow(data);*/
						
	  		for(int k=0;k<12;k++)
	  			//data[k] = null;
	  		
	  		mapobj.put("Name",reportobj[i].getName());
	  		mapobj.put("Address",reportobj[i].getAddr().getAddress());
			
			
	  		/*data[2]=array_loanreportobject[i].addr.getAddress();
	  		dtm.addRow(data);
	  		data[2]=array_loanreportobject[i].addr.getCity();
	  		dtm.addRow(data);
	  		data[2]=array_loanreportobject[i].addr.getState();
	  		dtm.addRow(data);
	  		data[2]=array_loanreportobject[i].addr.getPin();
	  		dtm.addRow(data);	
	  		
	  		
			
	  		for(int k=0;k<12;k++)
	  			data[k] = null;
	  		
	  		dtm.addRow(data);*/
		//////////
		
			list.add(mapobj);
		}
		
			req.setAttribute("OpenClosed",list);
	}
		
		
	}
}
	
	
	
	    

