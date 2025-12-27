
package com.scb.td.actions;

import general.Validations;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.termDeposit.DepositIntRate;
import masterObject.termDeposit.DepositIntRepObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositReportObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.TDDelegate;
import com.scb.props.MenuNameReader;
import com.scb.td.forms.ApplnDE;
import com.scb.td.forms.DepositAdmin;
import com.scb.td.forms.MISReport;
import com.scb.td.forms.MailSys;
import com.scb.td.forms.QuantumwiseReport;
import com.scb.td.forms.RenewalNoticeForm;
import com.scb.td.forms.TDnominee;

public class AccountOpeningActionTwo extends Action {
	private TDDelegate tddelegate;
	String path;
	DepositMasterObject dep_sub = new DepositMasterObject();
	
	
	DepositMasterObject dep_close;
	CustomerMasterObject cust_obj;
	TDDelegate tDDelaggate;
	java.text.NumberFormat numberFormat = java.text.NumberFormat.getNumberInstance();
	final Logger logger=LogDetails.getInstance().getLoggerObject("AccountOpeningAction");
	private boolean int_change = false;
	double depint[]={0,0,0};	
	double double_extra_int_amount = 0,double_int_amount=0;
	String string_today_date="";
	String string_renewal_date="";
	String Mdate;
	String IntFreq=null;
	
	String d_email="sumanthkeith@gmail.com";
	String  d_password="##########";
	AdministratorDelegate admDelegate;
	Map user_role;
	String user,tml;
	double paybl=0;
	double kemeete=0;
	
	ModuleObject array_moduleobject_acctype[]=null,array_moduleobject_transfer[]=null;
	private HttpSession session=null,newsession;
	public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception
	{
		System.out.println("Ia min ACTIONClass SECOND----. "+map.getPath());
		newsession=req.getSession();
		user=(String)newsession.getAttribute("UserName");
    	tml=(String)newsession.getAttribute("UserTml") ;
		try{
    		synchronized(req) {
				
			
    		if(user!=null){
    			
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(user,"TD");
    			if(user_role!=null){
    			req.setAttribute("user_role",user_role);
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
    		FrontCounterDelegate fcDelegatenew;
    		fcDelegatenew=new FrontCounterDelegate();
    		req.setAttribute("acccat",fcDelegatenew.getAccCategories());
    		req.setAttribute("accsubcat",fcDelegatenew.getSubCategories());
    		}
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    		}
		if(map.getPath().equalsIgnoreCase("/TermDeposit/DepositAccountRenewal"))
		{	
		try{
						
			DepositMasterObject dep_open_obj=new DepositMasterObject();
			DepositMasterObject dep_ren=null;
			
			TDDelegate tddelegate=new TDDelegate();
			ApplnDE acc_de=(ApplnDE)form;
			  
			AccountObject accobject;
			if(acc_de.getAccount_number()!=0 && acc_de.getAccount_type()!=null){
				req.setAttribute("account_number", acc_de.getAccount_number());
				req.setAttribute("account_type", acc_de.getAccount_type());
				dep_ren=tddelegate.getDepositMasterValues(acc_de.getAccount_number(), acc_de.getAccount_type());
			}
			double h=0;
			double olddepamt=0;
			String pageId=acc_de.getPageId();
			//String acc_no=acc_de.getAc_no();
			System.out.println("pageId==="+pageId);
			req.setAttribute("pagename",pageId);
			acc_de.setValidate(0);
			System.out.println("ACTION CLASS:: value======="+acc_de.getValue());
			//req.setAttribute("Verifyvalue",acc_de.getValue());// commented on 24/01/2009
			req.setAttribute("Verifyvalue",1);
			setInitParam(req, path, tddelegate);
			System.out.println("Parameter Forward VALUE Seethis--------------"+req.getParameter("forward"));
			
			 if(acc_de.getMysub().equalsIgnoreCase("submit")){

					System.out.println(" acc_de.getAmt_recv()=========="+acc_de.getAmt_recv());

					dep_open_obj.setAccType(acc_de.getAc_type());
					dep_open_obj.setAccNo(acc_de.getAc_no());
					dep_open_obj.setCustomerId(acc_de.getCid());
					dep_open_obj.setMaturityDate(acc_de.getMat_date());
					dep_open_obj.setAccount_number(acc_de.getAccount_number());
					dep_open_obj.setDepDate(acc_de.getDep_date());
					dep_open_obj.setDepositAmt(acc_de.getDep_amt());
					dep_open_obj.setDepositDays(acc_de.getPeriod_of_days());
					//dep_open_obj.setPeriod_in_days(acc_de.getPeriod_of_days());
					dep_open_obj.setMaturityAmt(Double.parseDouble(acc_de.getAmou()));
					dep_open_obj.setInterestRate(acc_de.getInt_rate());
					dep_open_obj.setExtraRateType(acc_de.getExtra_type());
					dep_open_obj.setTranDate(tddelegate.getSysdate());
					dep_open_obj.setInterestFrq(acc_de.getInterest_freq());
					dep_open_obj.setIntroAccType(acc_de.getIntro_ac_type());
					dep_open_obj.setIntroAccno(Integer.parseInt(acc_de.getIntro_ac_no()));
									
					if(acc_de.getPay_mode().equalsIgnoreCase("Transfer")){

						dep_open_obj.setReceivedBy("T");
						dep_open_obj.setReceivedAccno(acc_de.getPay_mode_ac_no());
						dep_open_obj.setReceivedAccType(acc_de.getPay_ac_type());
						dep_open_obj.setInterestMode(acc_de.getPay_mode());

						int trf_ac_no=acc_de.getPay_mode_ac_no();

						System.out.println("paid acc no===="+trf_ac_no);
						System.out.println("geetha inside transfer ");

					}    

					if(acc_de.getPay_mode().equalsIgnoreCase("Cash")){

						dep_open_obj.setReceivedBy("C");
						// dep_open_obj.set
						System.out.println("geetha inside cash");
						dep_open_obj.setInterestMode(acc_de.getPay_mode());

					}    

					if(acc_de.getPay_mode().equalsIgnoreCase("Clearing")){

						dep_open_obj.setTransferType("G");
						dep_open_obj.setReceivedAccno(acc_de.getControl_no());
						System.out.println("geetha inside clearing");
						dep_open_obj.setInterestMode(acc_de.getPay_mode());

					}    
					if(acc_de.getAmt_recv().equalsIgnoreCase("Transfer")){

						dep_open_obj.setTransferType("T");
						dep_open_obj.setTransferAccType(acc_de.getTrf_actype());
						dep_open_obj.setTransferAccno(acc_de.getTrf_acno());
						System.out.println("trf_ac_no===in action==="+acc_de.getTrf_acno());
					}
					if(acc_de.getAmt_recv().equalsIgnoreCase("Cash")){

						dep_open_obj.setReceivedBy("C");
						//dep_open_obj.setTransferAccType(null);

						dep_open_obj.setReceivedAccno(acc_de.getScroll_no());
						System.out.println("scroll_no====="+acc_de.getScroll_no());
						dep_open_obj.setString_scroll_date(acc_de.getDate().trim());
					}

					if(acc_de.getAmt_recv().equalsIgnoreCase("Q/DD/PO")){

						dep_open_obj.setReceivedBy("G");
						//dep_open_obj.setTransferAccType(null);
						System.out.println("inside cleaeringggggg");
						dep_open_obj.setReceivedAccno(acc_de.getControl_no());
						System.out.println("control no======"+acc_de.getControl_no());
						dep_open_obj.setString_scroll_date(acc_de.getDate().trim());
						dep_open_obj.setReceivedAccno(acc_de.getControl_no());
					}
					
					int ac_value=tddelegate.storeDeposit(dep_open_obj,acc_de.getAc_type(),acc_de.getAc_no(),0);

					System.out.println("ac no genereated===="+ac_value);
					
					req.setAttribute("shnum",ac_value);
					if(ac_value!=0){
						acc_de.setValidate(ac_value);
						
					}else{
						acc_de.setValidate(0);
					}

					
				}
		
			int revisedAmt=acc_de.getDep_amt();
			String NewDepDate=acc_de.getDep_date();
			acc_de.setDep_amt(revisedAmt);
			acc_de.setCid(acc_de.getCid());		
			acc_de.setDep_date(acc_de.getMat_date());
			
			req.setAttribute("oldMAT=newDEP",NewDepDate);
			if(acc_de.getNew_dpamt()!=null){
				
				olddepamt=Double.parseDouble(acc_de.getNew_dpamt());
				req.setAttribute("NewDepAMt",olddepamt);
			}
			else{
				kemeete=Double.valueOf(req.getParameter("amou"));
				req.setAttribute("NewDepAMt",kemeete);
				
			}
			if(dep_ren.getIntroAccno()!=0 && dep_ren.getIntroAccType()!=null){
				
				req.setAttribute("intra_acc_no",String.valueOf(dep_ren.getIntroAccno()));
				req.setAttribute("intra_acc_type",dep_ren.getIntroAccType());
			}
			
			if(acc_de.getDetails()!=null){
				
				if(acc_de.getDetails().equals("Personal")){
					cust_obj = tddelegate.getCustomer(dep_ren.getCustomerId());
					req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
					req.setAttribute("personalInfo", cust_obj);
					String custpersonal_add = tddelegate.getNomineeAddress(dep_ren.getCustomerId());
					req.setAttribute("Address", custpersonal_add);
					req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				}
				if(acc_de.getDetails().equals("Introducer"))
				{
					String perpath=MenuNameReader.getScreenProperties("0001");
					CustomerMasterObject custObj=null;
					custObj=tddelegate.getCustomer(acc_de.getCid());
					if(custObj!=null){
						if(custObj.getIntroducerId()!=null && custObj.getIntroducerId().trim().length()!=0){
							req.setAttribute("personalInfo", tddelegate.getCustomer(Integer.parseInt(custObj.getIntroducerId())));
							req.setAttribute("flag", perpath);
							req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
						}else{
							req.setAttribute("msg", "Introducer details are not available for this Customer");
						}
					}
				}
				if(acc_de.getDetails().equals("SignatureDetails"))
				{
					if (acc_de.getCid() > 0) {
						SignatureInstructionObject[] signObj=null;
						signObj = tddelegate.getComRemote().getSignatureDetails(acc_de.getCid());
						if (signObj == null) {
							req.setAttribute("msg", "No SignatureDetails available!!!");
						} else {
							req.setAttribute("flag",MenuNameReader.getScreenProperties("13030"));
							req.setAttribute("sigObject", signObj);
						}
					}
				}
				if(acc_de.getDetails().equals("Nominee"))
				{
					session=req.getSession();
				    req.setAttribute("enable", "enable");
					NomineeObject[] nomineeObjects=dep_ren.getNomineeDetails();
					if(nomineeObjects!=null){
						session.setAttribute("Nominee", nomineeObjects);
					}else{
						req.setAttribute("msg", "No Nominee Details are available!!!");
					}
	
				}
				if(acc_de.getDetails().equals("JointHolders"))
				{
					req.setAttribute("enable1", "enable1");
					int[] cids=dep_ren.getJointCid();
					if(cids!=null)
					req.setAttribute("joint", cids);
					else
					req.setAttribute("msg", "No JoinyHolders Details are available!!!");
				}
				
			}
			
			if(acc_de.getTrf_actype()!=null && acc_de.getTrf_acno()!=0){
			String TrnCustName=tddelegate.getCustmerName(acc_de.getTrf_actype(),acc_de.getTrf_acno());
            if(TrnCustName!=null){
            acc_de.setLabel_name(TrnCustName);
            }
			}
			
			System.out.println("Acount Type is-------> ");
			if(acc_de.getAc_type().equalsIgnoreCase("1003001"))
			{
				acc_de.setMat_amt(kemeete);
				
			}
			
			System.out.println("Transfer AccType====> "+acc_de.getTrf_actype());
		    System.out.println("Transfer Accno====> "+ acc_de.getTrf_acno());
			
			if(acc_de.getTrf_actype()!=null && acc_de.getTrf_acno()!=0){
			
			accobject=tddelegate.getAcccountDetails(acc_de.getTrf_actype(), null, acc_de.getTrf_acno(), tddelegate.getSysdate());
		    if(accobject!=null)
		    {
		    	System.out.println("ACount BAl---> "+accobject.getAmount());
		    	acc_de.setBalance_amt(accobject.getAmount());
		    }
			}
			
			
			if(acc_de.getScroll_no()>0){
				System.out.println("I am inside Scroll Number Greater than Zero "+acc_de.getScroll_no());
				double double_scroll_amount=0.0;
				AccountObject accountobject = tddelegate.getAcccountDetails(acc_de.getAc_type(),"C",acc_de.getScroll_no(),tddelegate.getSysdate());
				
				if(accountobject==null){
					System.out.println(" accountobject is null");
					acc_de.setTesting("Scroll Number Not Found");						
				}
				else if(accountobject.getAccStatus().equals("C") && (!(accountobject.getAccno()==(acc_de.getAc_no()))))
				{
					
					acc_de.setTesting("Given scroll number already attached ");					     
				}
				else if(!(accountobject.getAcctype().equalsIgnoreCase(acc_de.getAc_type()))){
					
					acc_de.setTesting("Scroll Number is Not for this Account");						
				}
				else
				{
					System.out.println("AMOUNT-----+++ "+accountobject.getAmount());
					double_scroll_amount=accountobject.getAmount();
					if(acc_de.getDep_amt() == 0){
						
						acc_de.setTesting(" Enter Deposit Amount");							
					}
					/*else if(!((acc_de.getDep_amt()) == double_scroll_amount)){
						
						acc_de.setTesting(" Deposit Amount Not Equal To Scroll Amount");							
					}*/
					else 
					{
						System.out.println("SCROLL NAME----> "+accountobject.getAccname());
						acc_de.setLabel_name(accountobject.getAccname());	
						System.out.println("SCRoll amount---> "+double_scroll_amount);
						acc_de.setAmount(double_scroll_amount);
						String todaysdate=tddelegate.getSysdate();
						acc_de.setDate(todaysdate);
						//	    combo_acc_details.requestFocus();
					}
				
				
				}
				
			}
			else if(acc_de.getControl_no()>0)
			{
				System.out.println("Inside COntrol number >0");
				System.out.println("Acount Type---> "+acc_de.getAc_type());
				System.out.println("Control Number---> "+acc_de.getControl_no());
				
				double double_control_amount=0.0;
				AccountObject accountObject = tddelegate.getAcccountDetails(acc_de.getAc_type(),"G",acc_de.getControl_no(),tddelegate.getSysdate());
				System.out.println("Control amount----> "+accountObject.getAmount());
				System.out.println("Control Name---> "+accountObject.getAccname());
				if(accountObject==null){
					acc_de.setTesting("Control Number Not Found");	
				}
				else if(accountObject.getVerified()==null){
					
					acc_de.setTesting("Control Number Not Yet Verified");						
				}
				else if(accountObject.getAccStatus().equals("F")){
					
					acc_de.setTesting("Control Number Not Yet Dispatched");						
				}
				else if(accountObject.getPostInd().equals("T")){
					
					acc_de.setTesting("Control Number Already Used");						
				}
				else if(!(accountObject.getAcctype().equalsIgnoreCase(acc_de.getAc_type()) && accountObject.getAccno()==0)){
					
					acc_de.setTesting("Control Number is Not for this Account");						
				}
				else
				{
					double_control_amount=accountObject.getAmount();
					if(acc_de.getDep_amt() == 0)
					{
						
						acc_de.setTesting(" Enter Deposit Amount");							
					}		
			    
				else if(!((acc_de.getDep_amt())== double_control_amount)){
					
					acc_de.setTesting(" Deposit Amount Not Equal To Control Number Amount");							
				}
					else {
						acc_de.setLabel_name(accountObject.getAccname());						
						acc_de.setAmount(double_control_amount);
						
						acc_de.setDate(accountObject.getQdp_date());
						//			combo_acc_details.requestFocus();
					}	
					
					
					
			  }
			
			
			}
			
			acc_de.setAmount(kemeete);
			
			System.out.println("CIDCID=-=-=-> "+acc_de.getCid());
			System.out.println("Period_of_days---> "+acc_de.getPeriod_of_days());
			System.out.println("Forward-----> "+acc_de.getForward());
			
			System.out.println("Int freq hidden before=======> "+req.getParameter("intfreqee"));
			System.out.println("Dep Date-----> "+acc_de.getDep_date());
			System.out.println("Mat date------> "+acc_de.getMat_date());
			System.out.println("dep amount=====> "+kemeete);
			System.out.println("Intrest Rate=====> "+acc_de.getInt_rate());
			if(acc_de.getMat_date()!=null && acc_de.getInt_rate()!=0 && req.getParameter("intfreqee")!=null){
			paybl=tddelegate.setPayableAmt(req.getParameter("intfreqee"), NewDepDate,acc_de.getMat_date(), kemeete, acc_de.getInt_rate());
			if(paybl!=0){
			 System.out.println("payaaaaaa---- > "+paybl);
			
			   
			   acc_de.setInt_payable(String.valueOf(paybl));
			}
			}
			
			if(acc_de.getCid() > 0 &&(acc_de.getPeriod_of_days())>0 && !(acc_de.getForward().equalsIgnoreCase("submit"))){
			{
					System.out.println("acc_de.getNew_dpamt()===> "+acc_de.getNew_dpamt());
				     if(acc_de.getNew_dpamt()!=null){
					 h=Double.parseDouble(acc_de.getNew_dpamt());
					 System.out.println("Int freq Iside loop=======> "+req.getParameter("intfreqee"));
					System.out.println("h New Deposit Amount---> "+h);
										
					}
					if(h>100){

						int no_of_days=acc_de.getPeriod_of_days();
						//String dep_date=acc_de.getDep_date();
						//String mat_date=acc_de.getMat_date();
						System.out.println("in action mat_date"+NewDepDate);
						String ac_type=acc_de.getAc_type();
						int  dep_amt=acc_de.getDep_amt();
						int cid=acc_de.getCid();
						int ac_no=acc_de.getAc_no();
						
						String revisedAmt3=acc_de.getHidval();
						System.out.println("The Revised Amount Is 3-----> "+revisedAmt3);
						System.out.println("dep_date====> "+NewDepDate);
						System.out.println("days---TOMATO------"+no_of_days + ""+ "****date********"+ "dep_date"  +NewDepDate);
						String NewMat_Date=tddelegate.getmat_date(acc_de.getAc_no(), acc_de.getPeriod_of_days());
                        System.out.println("Maturity date-----> "+NewMat_Date);
						req.setAttribute("matdate", tddelegate.getmat_date(acc_de.getAc_no(), acc_de.getPeriod_of_days()));

						System.out.println("verify value afetr refreshing----->>>"+acc_de.getValue());

						//req.setAttribute("Verifyvalue",acc_de.getValue());//commented on 24.1.2009
						req.setAttribute("Verifyvalue",1);

                         
						 // double[] int_rate=tddelegate.setInterestRate(acc_de.getAc_type(),acc_de.getDep_date(),acc_de.getMat_date(), acc_de.getPeriod_of_days(), kemeete,acc_de.getCid());
	                        
							
						
						

						System.out.println("mmmmmmmmmmm"+tddelegate.getmat_date(acc_de.getAc_no(), acc_de.getPeriod_of_days()));
						//req.setAttribute("interestrate",tddelegate.setInterestRate(ac_type,dep_date,mat_date, no_of_days, dep_amt,cid));
						double[] int_rate=tddelegate.setInterestRate(ac_type,NewDepDate,NewMat_Date, acc_de.getPeriod_of_days(),kemeete,acc_de.getCid());
												
						acc_de.setInt_rate(int_rate[0]);
						
						req.setAttribute("interestrate",int_rate);
                        
						
						if(acc_de.getInterest_freq().equalsIgnoreCase("Monthly")){

							req.setAttribute("interestamount",tddelegate.setPayableAmt(acc_de.getInterest_freq(), acc_de.getDep_date(),acc_de.getMat_date(), acc_de.getDep_amt(), acc_de.getInt_rate()));

							System.out.println("@@@@@@@@@@@start of maturity amount@@@@");

							req.setAttribute("maturityamt",tddelegate.setMaturityAmt(acc_de.getAc_type(), acc_de.getDep_amt(), acc_de.getDep_date(), acc_de.getMat_date(),acc_de.getInt_rate()));							
							System.out.println("############end of Maturity amount#########");
						}
						path=MenuNameReader.getScreenProperties(acc_de.getPageId());
						setInitParam(req,path, tddelegate);

						if(acc_de.getAmt_recv().equalsIgnoreCase("Transfer")){

							if(acc_de.getTrf_acno()!=0){
								req.setAttribute("balance",tddelegate.getAcccountDetails(acc_de.getTrf_actype(), acc_de.getPay_mode(),acc_de.getTrf_acno(), acc_de.getDate()));
							}
						}

						return map.findForward(ResultHelp.getSuccess());

					}

								}
			

			//for deleting 


			if(acc_de.getForward().equalsIgnoreCase("delete")){

				System.out.println("DELETING FROM ACTION CLASSSSSS  Ac No->>"+acc_de.getAc_no());

				System.out.println("Actype----->"+acc_de.getAc_type());

				DepositMasterObject dep_delete = new DepositMasterObject();

				int delete=tddelegate.deleteDeposit(acc_de.getAc_no(),acc_de.getAc_type());

				System.out.println("acc_de.getMat_date()"+acc_de.getMat_date());
				System.out.println("acc_de.getDep_date()"+acc_de.getDep_date());
				System.out.println("acc_de.getDep_amt()"+acc_de.getDep_amt());
				System.out.println("acc_de.getPeriod_of_days()"+acc_de.getPeriod_of_days());

				dep_delete.setAccType(acc_de.getAc_type());
				dep_delete.setAccNo(acc_de.getAc_no());
				//dep_delete.setCustomerId(Integer.parseInt(acc_de.getCid()));
				dep_delete.setMaturityDate(acc_de.getMat_date());

				dep_delete.setDepDate(acc_de.getDep_date());
				dep_delete.setDepositAmt(acc_de.getDep_amt());
				dep_delete.setDepositDays(acc_de.getPeriod_of_days());

				System.out.println("ACTION CLASS:::::Acno Enterd for deletion");
				System.out.println("ACTION CLASS::::AFTER DELETINGGGGGGGGGGGG");

			}
			//For Verification----fethching the data as soon as the fouslost of ac_no

			if(acc_de.getAc_no() > 0){

				System.out.println("ACTION CLASS:: Account No. TOOOO be verified--->>"+acc_de.getAc_no());

				DepositMasterObject depmasterverify_val = new DepositMasterObject();

				depmasterverify_val = tddelegate.getDepositMasterValues(acc_de.getAc_no(), acc_de.getAc_type());


				System.out.println("ACTION CLASS::depmasterverify_val-----"+depmasterverify_val.toString());

				System.out.println("cid==="+depmasterverify_val.getCustomerId());
				System.out.println("depdate==="+depmasterverify_val.getDepDate());
				System.out.println("dep mat==="+depmasterverify_val.getDepositAmt());

				req.setAttribute("verify_values",depmasterverify_val);
//				added on 27 th jan 09 to add personal and introducer
				/*if(acc_de.getDetails().equals("Personal"))

				{
					//DepositMasterObject depmasterverify_val = new DepositMasterObject();
					depmasterverify_val = tddelegate.getDepositMasterValues(acc_de.getAc_no(), acc_de.getAc_type());
					int cid=depmasterverify_val.getCustomerId();
					req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
					req.setAttribute("personalInfo",tddelegate.getCustomer(cid));	  
					req.setAttribute("panelName",CommonPanelHeading.getPersonal());

				}*/
				 if(acc_de.getDetails().equals("Introducer"))
				{

					System.out.println("====in Introducer ==="+path);
					req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
					req.setAttribute("panelName",CommonPanelHeading.getIntroucer());
					System.out.println("====td_closure.getIntroducerac_type()====="+acc_de.getIntro_ac_no());
					System.out.println("====td_closure.getIntroducerac_type()====="+acc_de.getIntro_ac_type());

					DepositMasterObject dep_close =tddelegate.getClosureInfo(acc_de.getAc_type(), acc_de.getAc_no(), false);

					AccountObject acntObject =tddelegate.getIntroducerAcntDetails(dep_close.getIntroAccType(),dep_close.getIntroAccno());
					System.out.println("====in Introducer ==="+path);

					if (acntObject != null) {
						System.out.println("acntObject.getCid()=="+ acntObject.getCid());
						req.setAttribute("personalInfo", tddelegate.getCustomer(acntObject.getCid()));
					} else {
						req.setAttribute("personalInfo", new CustomerMasterObject());
					}


				}


				return map.findForward(ResultHelp.getSuccess());

			}

			if(acc_de.getAc_no()==0){

				System.out.println("ACCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCOUNT IS ZERO");
				req.setAttribute("new_acno","new_ac_lable" );
				
				return map.findForward(ResultHelp.getSuccess());

			}

			if(acc_de.getForward().equalsIgnoreCase("Verify")){

				System.out.println("ACTION CLASS::::INSIDE VERIFICATION");

				DepositMasterObject dep_verify = new DepositMasterObject();	

				dep_verify.userverifier.setVerId("Geetha");
				dep_verify.userverifier.setVerTml("TD01");
				dep_verify.userverifier.setVerDate(TDDelegate.getSysdate() + TDDelegate.getSysTime());
				dep_verify.setDPType(1);			

				if(dep_verify.getReceivedBy().equalsIgnoreCase("Transfer")){

					dep_verify.setReceivedBy("T");

					System.out.println("ACTION CLASS::geetha inside transfer ");

				}    
				System.out.println("ACTION CLASS:: Before requesting......"+tddelegate.delegateverify(dep_verify));
				//req.setAttribute("verify",tddelegate.delegateverify(dep_verify));

				System.out.println(" ACTION CLASS::END ");

			} 	

			else 
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("The given request is not found", req, path);

				return map.findForward(ResultHelp.getError());

			}


		}
			path=MenuNameReader.getScreenProperties(acc_de.getPageId());
			setInitParam(req,path, tddelegate);
			return map.findForward(ResultHelp.getSuccess());
			
		}catch(Exception e){
			e.printStackTrace();
			path=MenuNameReader.getScreenProperties("0000");
			setErrorPageElements(""+e, req, path);
			return map.findForward(ResultHelp.getError());
		}


	}
		
		//Admin started	
		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/AdminMenu"))
		{

			try{

				TDDelegate tddelegate=new TDDelegate();
				DepositAdmin dep_admin = (DepositAdmin)form;
				String pageId=dep_admin.getPageId();
				System.out.println("admin in menu"+dep_admin.getPageId());
				req.setAttribute("pagename",pageId);


				if(MenuNameReader.containsKeyScreen(dep_admin.getPageId().trim())){
					path=MenuNameReader.getScreenProperties(dep_admin.getPageId());
					setAdminInitParam(req, path, tddelegate);
					List int_list=new ArrayList();
					session=req.getSession(true);
					session.setAttribute("IntList",int_list);
					req.setAttribute("table","table");
					return map.findForward(ResultHelp.getSuccess());
				}

			}catch(Exception e){
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("The given request is not found", req, path);
				e.printStackTrace();
				return map.findForward(ResultHelp.getError());
			}

			return map.findForward(ResultHelp.getSuccess());
		}
		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/Admin"))
		{

			try{

				TDDelegate tddelegate=new TDDelegate();
				DepositAdmin dep_admin = (DepositAdmin)form;
				String pageId=dep_admin.getPageId();
				System.out.println("Admin"+dep_admin.getPageId());
				req.setAttribute("pagename",pageId);
    
				if(MenuNameReader.containsKeyScreen(dep_admin.getPageId())){
					path=MenuNameReader.getScreenProperties(dep_admin.getPageId());
					setAdminInitParam(req, path, tddelegate);
					String method=req.getParameter("method");
					DepositIntRate array_depositintrate[]=null;
					req.setAttribute("id", req.getParameter("id"));
					req.setAttribute("method", req.getParameter("method"));
					List int_list=(ArrayList)session.getAttribute("IntList");
					System.out.println("the value of forward in Admin is==++YY+YIU+Y=> "+dep_admin.getForward());
                    System.out.println("the value of req.parameter forward in Admin is===> "+req.getParameter("forward"));
               

					if(method!=null)
					{        
                         
						
						if(method.trim().equalsIgnoreCase("Clear"))
						{	
						path=MenuNameReader.getScreenProperties(dep_admin.getPageId());
						setAdminInitParam(req, path, tddelegate);
						List int_list1=new ArrayList();
						session=req.getSession(true);
						session.setAttribute("IntList",int_list1);
						req.setAttribute("table","table");
						return map.findForward(ResultHelp.getSuccess());						
						
					    }
                         
						else if(method.trim().equalsIgnoreCase("Refresh")||method.trim().equalsIgnoreCase("Reset"))
						{
							int_list.clear();
							array_depositintrate = tddelegate.getDepIntRate(dep_admin.getFd_actype().trim(), dep_admin.getFd_table().trim());
							System.out.println("the value is+++++++> "+array_depositintrate);
							if(array_depositintrate==null)
							{
								System.out.println("i am in Null of admin");
								req.setAttribute("table",dep_admin.getFd_table());
								dep_admin.setTesting("Records Not Found");
														
								
							}else{
							req.setAttribute("table",dep_admin.getFd_table());
							if(dep_admin.getFd_table().equalsIgnoreCase("DepositIntRate")){

								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("frm_date",array_depositintrate[i].getDateFrom());
									m.put("to_date",array_depositintrate[i].getDateTo());
									m.put("frm_days",array_depositintrate[i].getDaysFrom());
									m.put("to_days",array_depositintrate[i].getDaysTo());
									m.put("int_rate",array_depositintrate[i].getIntRate());
									int_list.add(m);
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositQuantumRate")){

								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("frm_date",array_depositintrate[i].getDateFrom());
									m.put("to_date",array_depositintrate[i].getDateTo());
									m.put("frm_days",array_depositintrate[i].getDaysFrom());
									m.put("to_days",array_depositintrate[i].getDaysTo());
									m.put("cat",array_depositintrate[i].getCategory());
									m.put("min_amt",array_depositintrate[i].getMinAmt());
									m.put("max_amt",array_depositintrate[i].getMaxAmt());
									m.put("extr_rate",array_depositintrate[i].getMinAmt());
									int_list.add(m);
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositCategoryRate")){
								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("frm_date",array_depositintrate[i].getDateFrom());
									m.put("to_date",array_depositintrate[i].getDateTo());
									m.put("frm_days",array_depositintrate[i].getDaysFrom());
									m.put("to_days",array_depositintrate[i].getDaysTo());
									m.put("cat",array_depositintrate[i].getCategory());
									m.put("extr_rate",array_depositintrate[i].getIntRate());
									m.put("extr_LoanRate",array_depositintrate[i].getIntRate());
									int_list.add(m);
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("PeriodLimit")){
								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("mod_Ty",array_depositintrate[i].getMod_ty());
									m.put("srl_No",array_depositintrate[i].getSrl_no());
									m.put("limit",array_depositintrate[i].getLmt_hdg());
									m.put("frm_limit",array_depositintrate[i].getFr_lmt());
									m.put("to_limit",array_depositintrate[i].getTo_lmt());
									int_list.add(m);
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("QuantumLimit")){
								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("mod_Ty",array_depositintrate[i].getMod_ty());
									m.put("srl_No",array_depositintrate[i].getSrl_no());
									m.put("limit",array_depositintrate[i].getLmt_hdg());
									m.put("frm_limit",array_depositintrate[i].getFr_lmt());
									m.put("to_limit",array_depositintrate[i].getTo_lmt());
									int_list.add(m);
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("Products")){
								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("dpdl_date",array_depositintrate[i].getDpdl_date());
									m.put("prod_date",array_depositintrate[i].getProd_date());
									m.put("rinve_prod_date",array_depositintrate[i].getRinve_prod_date());
									int_list.add(m);
								}
							}
							else if(dep_admin.getFd_table().equalsIgnoreCase("QtrDefinition")){
								for(int i=0;i<array_depositintrate.length;i++){
									Map m=new TreeMap();
									m.put("id",""+(i+1));
									m.put("month",array_depositintrate[i].getMonth());
									m.put("quart_defn",array_depositintrate[i].getQtr_defn());
									m.put("halfyear_defn",array_depositintrate[i].getHyr_defn());
									m.put("yearly_defn",array_depositintrate[i].getYr_defn());
									int_list.add(m);
								}
							}

						}
						}else if(method.trim().equalsIgnoreCase("Edit")){
							req.setAttribute("table",dep_admin.getFd_table());

						}else if(method.trim().equalsIgnoreCase("Delete"))
						{
							req.setAttribute("table",dep_admin.getFd_table());
							for(int i=0;i<int_list.size();i++){
								Map m=(TreeMap)int_list.get(i);
								System.out.println("Th evalue of m--->"+m);
								String id=m.get("id").toString();
								System.out.println("the id to be Deleted is----> "+id);
								System.out.println("reqParameter----->"+req.getParameter("id"));
								int result=0;
								if(req.getParameter("id").equals(id))
								{
									DepositIntRate depositintrate = new DepositIntRate();
									 
									System.out.println("the table name---->"+dep_admin.getFd_table());
									if(dep_admin.getFd_table().equalsIgnoreCase("DepositIntRate"))
									{
										System.out.println("i am insdde atlast");
										String table_name=dep_admin.getFd_table();
										depositintrate.setDPAccType(dep_admin.getFd_actype());
										depositintrate.setDateFrom((m.get("frm_date")).toString());
										//depositintrate.setDateTo((m.get("to_date")).toString());
										depositintrate.setDateTo("Future Date");
										depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
										depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
										depositintrate.setIntRate(Double.valueOf((m.get("int_rate")).toString()));
										 result=tddelegate.deleteAdminValues(depositintrate,table_name);
									}
									else if(dep_admin.getFd_table().equalsIgnoreCase("QtrDefinition"))
									{
										
									        String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setMonth(Integer.parseInt(m.get("month").toString()));
											depositintrate.setQtr_defn(m.get("quart_defn").toString());
											depositintrate.setHyr_defn(m.get("halfyear_defn").toString());
											depositintrate.setYr_defn(m.get("yearly_defn").toString());
											result=tddelegate.deleteAdminValues(depositintrate,table_name);
								    }
									else if(dep_admin.getFd_table().equalsIgnoreCase("DepositQuantumRate"))
									{
										
									    String table_name=dep_admin.getFd_table();
										depositintrate.setDPAccType(dep_admin.getFd_actype());
										depositintrate.setDateFrom((m.get("frm_date")).toString());
										depositintrate.setDateTo((m.get("to_date")).toString());
										depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
										depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
										depositintrate.setCategory(Integer.parseInt(m.get("cat").toString()));
										depositintrate.setMinAmt(Double.valueOf(m.get("min_amt").toString()));
										depositintrate.setExtra_int_rate(Double.valueOf(m.get("extr_rate").toString()));
										result=tddelegate.submitAdminValues(depositintrate,table_name);
										
									}
									else if(dep_admin.getFd_table().equalsIgnoreCase("DepositCategoryRate"))
									{
										
										
										        String table_name=dep_admin.getFd_table();
												depositintrate.setDPAccType(dep_admin.getFd_actype());
												depositintrate.setDateFrom((m.get("frm_date")).toString());
												depositintrate.setDateTo((m.get("to_date")).toString());
												depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
												depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
												depositintrate.setCategory(Integer.parseInt(m.get("cat").toString()));
												depositintrate.setExtra_int_rate(Double.valueOf(m.get("extr_rate").toString()));
												depositintrate.setExtra_lnint_rate(Double.valueOf(m.get("extr_LoanRate").toString()));
												result=tddelegate.submitAdminValues(depositintrate,table_name);
								    }
									else if(dep_admin.getFd_table().equalsIgnoreCase("PeriodLimit"))
									{
										     String table_name=dep_admin.getFd_table();
												depositintrate.setDPAccType(dep_admin.getFd_actype());
												depositintrate.setSrl_no(Integer.parseInt(m.get("srl_No").toString()));
												depositintrate.setMod_ty(m.get("mod_Ty").toString());
												depositintrate.setLmt_hdg(m.get("limit").toString());
												depositintrate.setFr_lmt(Integer.parseInt(m.get("frm_limit").toString()));
												depositintrate.setTo_lmt(Integer.parseInt(m.get("to_limit").toString()));
												result=tddelegate.submitAdminValues(depositintrate,table_name);
									}
									else if(dep_admin.getFd_table().equalsIgnoreCase("QuantumLimit")){
												
												String table_name=dep_admin.getFd_table();
												depositintrate.setDPAccType(dep_admin.getFd_actype());
												depositintrate.setSrl_no(Integer.parseInt(m.get("srl_No").toString()));
									            depositintrate.setMod_ty(m.get("mod_Ty").toString());
									            depositintrate.setLmt_hdg(m.get("limit").toString());
									            depositintrate.setFr_lmt(Integer.parseInt(m.get("frm_limit").toString()));
												depositintrate.setTo_lmt(Integer.parseInt(m.get("to_limit").toString()));
												result=tddelegate.submitAdminValues(depositintrate,table_name);
									}
									else if(dep_admin.getFd_table().equalsIgnoreCase("Products"))
									{
											    											
												String table_name=dep_admin.getFd_table();
												depositintrate.setDPAccType(dep_admin.getFd_actype());
												depositintrate.setDpdl_date(m.get("dpdl_date").toString());
												depositintrate.setProd_date(m.get("prod_date").toString());
												depositintrate.setRinve_prod_date(m.get("rinve_prod_date").toString());
												result=tddelegate.submitAdminValues(depositintrate,table_name);
									}
									if(result==1)
									{
									int_list.remove(m);
									dep_admin.setTesting("Data Success fully Deleted");
									}else{
										dep_admin.setTesting("Delete Operation Failed-You Can Only Delete Data That Is Inserted Today");
									}
									break;
								}
								
								
								
							}
						}
						else if(method.trim().equalsIgnoreCase("Save")){
							req.setAttribute("table",dep_admin.getFd_table());
							if(dep_admin.getFd_table().equalsIgnoreCase("DepositIntRate")){

								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);
										m.put("frm_date",req.getParameter("frm_date"));
										m.put("to_date",req.getParameter("to_date"));
										m.put("frm_days",req.getParameter("frm_days"));
										m.put("to_days",req.getParameter("to_days"));
										m.put("int_rate",req.getParameter("int_rate"));
										int_list.set(i,m);
									}
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositQuantumRate")){

								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);
										m.put("frm_date",req.getParameter("frm_date"));
										m.put("to_date",req.getParameter("to_date"));
										m.put("frm_days",req.getParameter("frm_days"));
										m.put("to_days",req.getParameter("to_days"));
										m.put("cat",req.getParameter("cat"));
										m.put("min_amt",req.getParameter("min_amt"));
										m.put("max_amt",req.getParameter("max_amt"));
										m.put("extr_rate",req.getParameter("extr_rate"));
										int_list.set(i,m);
									}
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositCategoryRate")){
								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);
										m.put("frm_date",req.getParameter("frm_date"));
										m.put("to_date",req.getParameter("to_date"));
										m.put("frm_days",req.getParameter("frm_days"));
										m.put("to_days",req.getParameter("to_days"));
										m.put("cat",req.getParameter("cat"));
										m.put("extr_rate",req.getParameter("extr_rate"));
										m.put("extr_LoanRate",req.getParameter("extr_LoanRate"));
										int_list.set(i,m);
									}
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("PeriodLimit")){
								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);
										m.put("mod_Ty",req.getParameter("mod_Ty"));
										m.put("srl_No",req.getParameter("extr_LoanRate"));
										m.put("limit",req.getParameter("srl_No"));
										m.put("frm_limit",req.getParameter("frm_limit"));
										m.put("to_limit",req.getParameter("to_limit"));
										int_list.set(i,m);
									}
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("QuantumLimit")){
								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);	
										m.put("mod_Ty",req.getParameter("mod_Ty"));
										m.put("srl_No",req.getParameter("srl_No"));
										m.put("limit",req.getParameter("limit"));
										m.put("frm_limit",req.getParameter("frm_limit"));
										m.put("to_limit",req.getParameter("to_limit"));
										int_list.set(i,m);
									}
								}
							}else if(dep_admin.getFd_table().equalsIgnoreCase("Products")){
								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);	
										m.put("dpdl_date",req.getParameter("dpdl_date"));
										m.put("prod_date",req.getParameter("prod_date"));
										m.put("rinve_prod_date",req.getParameter("rinve_prod_date"));
										int_list.set(i,m);
									}
								}
							}
							else if(dep_admin.getFd_table().equalsIgnoreCase("QtrDefinition")){
								for(int i=0;i<int_list.size();i++){
									Map m=(TreeMap)int_list.get(i);
									String map_id=m.get("id").toString();
									if(map_id.equals(req.getParameter("id"))){
										m.put("id",map_id);	
										m.put("month",req.getParameter("month"));
										m.put("quart_defn",req.getParameter("quart_defn"));
										m.put("halfyear_defn",req.getParameter("halfyear_defn"));
										m.put("yearly_defn",req.getParameter("yearly_defn"));
										int_list.add(m);
									}
								}
							}
						}else if(method.trim().equalsIgnoreCase("Add"))
						{
							req.setAttribute("table",dep_admin.getFd_table());
							if(dep_admin.getFd_table().equalsIgnoreCase("DepositIntRate")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);
								m.put("frm_date",req.getParameter("frm_date"));
								m.put("to_date",req.getParameter("to_date"));
								m.put("frm_days",req.getParameter("frm_days"));
								m.put("to_days",req.getParameter("to_days"));
								m.put("int_rate",req.getParameter("int_rate"));
								int_list.add(m);


							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositQuantumRate")){

								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);
								m.put("frm_date",req.getParameter("frm_date"));
								m.put("to_date",req.getParameter("to_date"));
								m.put("frm_days",req.getParameter("frm_days"));
								m.put("to_days",req.getParameter("to_days"));
								m.put("cat",req.getParameter("cat"));
								m.put("min_amt",req.getParameter("min_amt"));
								m.put("max_amt",req.getParameter("max_amt"));
								m.put("extr_rate",req.getParameter("extr_rate"));
								int_list.add(m);


							}else if(dep_admin.getFd_table().equalsIgnoreCase("DepositCategoryRate")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);
								m.put("frm_date",req.getParameter("frm_date"));
								m.put("to_date",req.getParameter("to_date"));
								m.put("frm_days",req.getParameter("frm_days"));
								m.put("to_days",req.getParameter("to_days"));
								m.put("cat",req.getParameter("cat"));
								m.put("extr_rate",req.getParameter("extr_rate"));
								m.put("extr_LoanRate",req.getParameter("extr_LoanRate"));
								int_list.add(m);


							}else if(dep_admin.getFd_table().equalsIgnoreCase("PeriodLimit")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);
								m.put("mod_Ty",req.getParameter("mod_Ty"));
								m.put("srl_No",req.getParameter("extr_LoanRate"));
								m.put("limit",req.getParameter("srl_No"));
								m.put("frm_limit",req.getParameter("frm_limit"));
								m.put("to_limit",req.getParameter("to_limit"));
								int_list.add(m);

							}else if(dep_admin.getFd_table().equalsIgnoreCase("QuantumLimit")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);	
								m.put("mod_Ty",req.getParameter("mod_Ty"));
								m.put("srl_No",req.getParameter("srl_No"));
								m.put("limit",req.getParameter("limit"));
								m.put("frm_limit",req.getParameter("frm_limit"));
								m.put("to_limit",req.getParameter("to_limit"));
								int_list.add(m);

							}else if(dep_admin.getFd_table().equalsIgnoreCase("Products")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);	
								m.put("dpdl_date",req.getParameter("dpdl_date"));
								m.put("prod_date",req.getParameter("prod_date"));
								m.put("rinve_prod_date",req.getParameter("rinve_prod_date"));
								int_list.add(m);

							}
							else if(dep_admin.getFd_table().equalsIgnoreCase("QtrDefinition")){
								Map m=new TreeMap();
								String map_id=""+(int_list.size()+1);
								m.put("id",map_id);	
								m.put("month",req.getParameter("month"));
								m.put("quart_defn",req.getParameter("quart_defn"));
								m.put("halfyear_defn",req.getParameter("halfyear_defn"));
								m.put("yearly_defn",req.getParameter("yearly_defn"));
								int_list.add(m);

							}
						}
						//for submit all,sending values to database..

						//else if(dep_admin.getForward().equalsIgnoreCase("submit")){
						else if(method.trim().equalsIgnoreCase("submit"))
						{
                            System.out.println("I am inside submit of Admin---he he");
							req.setAttribute("table",dep_admin.getFd_table());

							System.out.println("checkking for submit-----------"+int_list.size());

							Map m=new TreeMap();

							for(int i=0;i < int_list.size();i++)
							{

								m =(TreeMap)int_list.get(i);

								System.out.println("inside submit all"+(TreeMap)int_list.get(i));
                             	System.out.println("map---->"+m.get("dpdl_date"));
                                System.out.println("map 1 id---->"+m.get("id"));
                                System.out.println("map 2 frm_date---->"+m.get("frm_date"));
                                System.out.println("map 3 to_date---->"+m.get("to_date"));
                                System.out.println("map 4 to_daysss---->"+m.get("to_days"));
                                System.out.println("map 5 int_rate---->"+m.get("int_rate"));
                                
                                      
							}
							if(int_list.size()==Integer.parseInt((m.get("id").toString())))
							{  
								DepositIntRate depositintrate = new DepositIntRate();
								//String table_name=null;
								//hard coded for timebeing
								depositintrate.obj_userverifier.setUserId("1044");
								depositintrate.obj_userverifier.setUserTml("CA99");
								depositintrate.obj_userverifier.setUserDate(tddelegate.getSysdate()+" "+tddelegate.getSysTime());
							    int result=0;
								System.out.println("hello i am inside atlast===> "+m.get("id"));
								if(dep_admin.getFd_table().equalsIgnoreCase("DepositIntRate"))
								{
									
								System.out.println("HI sumanth you are great you did it");
								System.out.println("the account type====>"+dep_admin.getFd_actype());
								String table_name=dep_admin.getFd_table();
								depositintrate.setDPAccType(dep_admin.getFd_actype());
								depositintrate.setDateFrom((m.get("frm_date")).toString());
								depositintrate.setDateTo((m.get("to_date")).toString());
								depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
								depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
								depositintrate.setIntRate(Double.valueOf((m.get("int_rate")).toString()));
								result=tddelegate.submitAdminValues(depositintrate,table_name);
								}
								else if(dep_admin.getFd_table().equalsIgnoreCase("DepositQuantumRate"))
								{
									
								    String table_name=dep_admin.getFd_table();
									depositintrate.setDPAccType(dep_admin.getFd_actype());
									depositintrate.setDateFrom((m.get("frm_date")).toString());
									depositintrate.setDateTo((m.get("to_date")).toString());
									depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
									depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
									depositintrate.setCategory(Integer.parseInt(m.get("cat").toString()));
									depositintrate.setMinAmt(Double.valueOf(m.get("min_amt").toString()));
									depositintrate.setExtra_int_rate(Double.valueOf(m.get("extr_rate").toString()));
									result=tddelegate.submitAdminValues(depositintrate,table_name);
									
								}
								else if(dep_admin.getFd_table().equalsIgnoreCase("DepositCategoryRate"))
								{
									
									
									        String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setDateFrom((m.get("frm_date")).toString());
											depositintrate.setDateTo((m.get("to_date")).toString());
											depositintrate.setDaysFrom(Integer.parseInt((m.get("frm_days")).toString()));
											depositintrate.setDaysTo(Integer.parseInt((m.get("to_days")).toString()));
											depositintrate.setCategory(Integer.parseInt(m.get("cat").toString()));
											depositintrate.setExtra_int_rate(Double.valueOf(m.get("extr_rate").toString()));
											depositintrate.setExtra_lnint_rate(Double.valueOf(m.get("extr_LoanRate").toString()));
											result=tddelegate.submitAdminValues(depositintrate,table_name);
							    }
								else if(dep_admin.getFd_table().equalsIgnoreCase("PeriodLimit"))
								{
									     String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setSrl_no(Integer.parseInt(m.get("srl_No").toString()));
											depositintrate.setMod_ty(m.get("mod_Ty").toString());
											depositintrate.setLmt_hdg(m.get("limit").toString());
											depositintrate.setFr_lmt(Integer.parseInt(m.get("frm_limit").toString()));
											depositintrate.setTo_lmt(Integer.parseInt(m.get("to_limit").toString()));
											result=tddelegate.submitAdminValues(depositintrate,table_name);
								}
								else if(dep_admin.getFd_table().equalsIgnoreCase("QuantumLimit")){
											
											String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setSrl_no(Integer.parseInt(m.get("srl_No").toString()));
								            depositintrate.setMod_ty(m.get("mod_Ty").toString());
								            depositintrate.setLmt_hdg(m.get("limit").toString());
								            depositintrate.setFr_lmt(Integer.parseInt(m.get("frm_limit").toString()));
											depositintrate.setTo_lmt(Integer.parseInt(m.get("to_limit").toString()));
											result=tddelegate.submitAdminValues(depositintrate,table_name);
								}
								else if(dep_admin.getFd_table().equalsIgnoreCase("Products"))
								{
										    											
											String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setDpdl_date(m.get("dpdl_date").toString());
											depositintrate.setProd_date(m.get("prod_date").toString());
											depositintrate.setRinve_prod_date(m.get("rinve_prod_date").toString());
											result=tddelegate.submitAdminValues(depositintrate,table_name);
								}
								else if(dep_admin.getFd_table().equalsIgnoreCase("QtrDefinition")){
											
									      String table_name=dep_admin.getFd_table();
											depositintrate.setDPAccType(dep_admin.getFd_actype());
											depositintrate.setMonth(Integer.parseInt(m.get("month").toString()));
											depositintrate.setQtr_defn(m.get("quart_defn").toString());
											depositintrate.setHyr_defn(m.get("halfyear_defn").toString());
											depositintrate.setYr_defn(m.get("yearly_defn").toString());
											result=tddelegate.submitAdminValues(depositintrate,table_name);
								}
								
				   			    
				   			    System.out.println("Dude you are too good====> "+result);
				   			     if(result==1)
				   			     {
				   			    	dep_admin.setTesting("Data Successfully Updated");
				   			     }else{
				   			    	dep_admin.setTesting("Data Not Updated");
				   			     }
								
							
							}
							

						}


						//submit ends here
						req.setAttribute("IntList", int_list);
											
					}


					session.setAttribute("IntList", int_list);




					return map.findForward(ResultHelp.getSuccess());
				}

			}catch(Exception e){
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("The given request is not found", req, path);
				e.printStackTrace();
				return map.findForward(ResultHelp.getError());
			}
			
			return map.findForward(ResultHelp.getSuccess());
		}
		
	
		
		else if(map.getPath().equalsIgnoreCase("/Term/Nominee"))
		{
			System.out.println("------Hi from Nominee-------"); 
			try{
				
				DepositMasterObject dep_mstr_obj= new DepositMasterObject(); 
					TDnominee nomee=(TDnominee)form;
					TDDelegate delegate=new TDDelegate();
					System.out.println("nomee.getForward()==>"+nomee.getForward());
					if(nomee.getHas_ac().equalsIgnoreCase("Yes") && nomee.getHas_ac()!=null)
					{
						//req.setAttribute("disabled", "has");
						System.out.println("CID PRESENT DOOOD");
						req.setAttribute("cid_psnt","vizcid" ); 
						req.setAttribute("Dep type",delegate.getModulesTypes());

					}else{ 
						
						req.setAttribute("cid_psnt","null" ); 
						req.setAttribute("Dep type",delegate.getModulesTypes());
					}
					
					
					if(nomee.getCid()!=null && nomee.getCid().length()>0)
					{
						NomineeObject[] nom=new NomineeObject[1];
							
							
						
							System.out.println("The cid is "+nomee.getCid());
							
							CustomerMasterObject cust_obj=delegate.getCustomer(Integer.parseInt(nomee.getCid()));
							
							String NomAddress=delegate.getNomineeAddress(Integer.parseInt(nomee.getCid()));
							System.out.println("THe address of the customer is "+cust_obj.getAddress());
							req.setAttribute("custdetails",cust_obj);
							
							System.out.println("The Address in Action++++> "+NomAddress);
							req.setAttribute("NOMADDR",NomAddress);
					}
					/*if(nomee.getForward().equalsIgnoreCase("perCent"))
					{
					int percentage=delegate.getnomineePercentage(Integer.parseInt(nomee.getAcno()),nomee.getActype());
					if(percentage<100){
						System.out.println("The Percentage is--> "+percentage);
						nomee.setValidations("The Percentage is less than 100.Add another nominee");
					}
					}*/
					
					System.out.println("cid In NoMINee----> "+nomee.getCid());
					System.out.println("nAME nOMINEE---> "+nomee.getName());
					if(nomee.getCid()!=null || nomee.getName()!=null )
					{
						System.out.println("hi rakesh");
					 if(nomee.getForward().equalsIgnoreCase("Submit"))
					 {
						DepositMasterObject dep_open_obj = new DepositMasterObject();
						System.out.println("-------Inside Submit-------");  
						NomineeObject[] nom=new NomineeObject[1];
						nom[0]=new NomineeObject();
						nom[0].setRegNo(0);
						nom[0].setAccNo(Integer.parseInt(nomee.getAcno()));
						nom[0].setAccType(nomee.getActype());
						nom[0].setCustomerId(Integer.parseInt(nomee.getCid()));
						nom[0].setPercentage(Double.parseDouble(nomee.getPercentage()));
						nom[0].setNomineeRelation(nomee.getRel_ship());
						System.out.println("relation"+nomee.getRel_ship());
						System.out.println("i want customer id"+Integer.parseInt(nomee.getCid()));
						System.out.println("i want percenatgr"+Double.parseDouble(nomee.getPercentage()));
						//int percent=0;
					//	percent=Double.parseDouble(nomee.getPercentage());
						
						  if(nomee.getCid().length()>0)
						  {
							nom[0].setCustomerId(Integer.parseInt(nomee.getCid()));
						  }
						
						if( Double.parseDouble(nomee.getPercentage())==100.00 && nomee.getRel_ship()!=null  )
						  {
						  int s=delegate.Nominee(nom);
						  System.out.println("s=================>"+s);
						  req.setAttribute("msg", "Nominee added successfully and the number is "+s+"");
						  nomee.setValidations("Nominee added successfully and the number is "+s+".");
						  
						  }
						/* else{
								nomee.setValidations("Nominee Already Present-Cannot Add Anymore");
							}
*/
				 
				/*			
				  int percent=delegate.getnomineePercentage(Integer.parseInt(nomee.getAcno()),nomee.getActype());
					System.out.println("Perentage====> hurray===> "+percent);	
					if(percent==0||String.valueOf(percent)==null)
					{						
						System.out.println("I am in Else  NOminee Submit Name "+nomee.getName());
						System.out.println("I am in Else  NOminee Submit getDob "+nomee.getDob());
						System.out.println("I am in Else  NOminee Submit Address"+nomee.getAddress());
						System.out.println("I am in Else  NOminee Submit Rel_ship"+nomee.getRel_ship());
						System.out.println("I am in Else  NOminee Submit Percentage "+nomee.getPercentage());
						System.out.println("I am in Else  NOminee Submit Issuedate "+nomee.getIssuedate());
						nom[0].setAccNo(Integer.parseInt(nomee.getAcno()));
						nom[0].setAccType(nomee.getActype());
						nom[0].setCustomerId(0);
						nom[0].setNomineeName(nomee.getName());
						nom[0].setNomineeDOB(nomee.getDob());
						nom[0].setNomineeAddress(nomee.getAddress());
						nom[0].setNomineeRelation(nomee.getRel_ship());
						nom[0].setPercentage(Double.parseDouble(nomee.getPercentage()));
						nom[0].setFromDate(tddelegate.getSysdate());
					}
					else  if( Double.parseDouble(nomee.getPercentage())==100.00)
						  {
						  int s=delegate.Nominee(nom);
						  System.out.println("s=================>"+s);
						  req.setAttribute("msg", "Nominee added successfully and the number is "+s+"");
						  nomee.setValidations("Nominee added successfully and the number is "+s+".");
						  
						  }
						
					    
						else{
							nomee.setValidations("Nominee Already Present-Cannot Add Anymore");
						}*/
						
					 
					 }
					// NomineeObject[] nom=new NomineeObject[1];
						 
						 
					}
					else
					{
						
						nomee.setValidations("Cannot Submit-Please Enter The Mandatory Fields");
					}
					if(MenuNameReader.containsKeyScreen(nomee.getPageId().trim()))
					{
						System.out.println("hi kanch");
						path=MenuNameReader.getScreenProperties(nomee.getPageId());
						req.setAttribute("pageId",path);
						return map.findForward(ResultHelp.getSuccess());
					}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(map.getPath().equalsIgnoreCase("/TermDeposit/TDMailMenu"))
		{
		   System.out.println("I am in Mainling sumanth");
			try
		   
		   {
			    MailSys mail=(MailSys) form;
			    System.out.println("***********1112");
				String pageId = mail.getPageId();
				System.out.println("***********1113"+pageId);
				req.setAttribute("pagename",pageId);

				System.out.println("pageid====="+pageId);
				
				if(MenuNameReader.containsKeyScreen(mail.getPageId()))
				{
					System.out.println("Hey boy u r good");
					path = MenuNameReader.getScreenProperties(mail.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****"+path);
					req.setAttribute("pageId",path);
                    System.out.println("SYS date---> "+tddelegate.getSysdate());
					
					System.out.println("NEW FUTURE DATE=====> "+tddelegate.getFutureSysdateMail());
					String nextpaydate=tddelegate.getFutureSysdateMail();
					mail.setPaydate(nextpaydate);

					System.out.println("the path"+path);
					
					
					
					
					return map.findForward(ResultHelp.getSuccess()); 
				}else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}
			   
		   }
		   catch(Exception e){
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e, req, path);
				return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/TermDeposit/EmailSystem"))
			
		{
			System.out.println("I am in else of mailing block");
			try
			{
				TDDelegate tddelegate=new TDDelegate();
				MailSys mail=(MailSys) form;
				String pageId=mail.getPageId();
				DepositMasterObject depmail;
				System.out.println("pageId==="+mail.getPageId());
				req.setAttribute("pagename",pageId);
				setInitParam(req, path, tddelegate);
				
				if(MenuNameReader.containsKeyScreen(mail.getPageId()))
				{
					Calendar calender=null;
					path=MenuNameReader.getScreenProperties(mail.getPageId());
					tddelegate=new TDDelegate();
					setInitParam(req, path, tddelegate);
					req.setAttribute("pageId",path);
									
									
					System.out.println("The date from form is--->"+mail.getPaydate());
					System.out.println("The Acount Type=> "+mail.getAc_type());
						
					
					if(mail.getPaydate()!=null)
					{
						System.out.println("i am in pay dtae is not null");
						depmail=tddelegate.getMailDetails(mail.getAc_type(), mail.getPaydate());
						if(depmail==null)
						{
						   mail.setTesting("Rescords Not Found!!");	
						}
						if(depmail!=null)
						{
							req.setAttribute("emaildata", depmail);
						}
					}
					
					System.out.println("The value is ===>"+req.getParameter("flag"));
					System.out.println("The email is ===>"+req.getParameter("id"));
					System.out.println("The email is ===>"+req.getParameter("email"));
					System.out.println("The email is ===>"+req.getParameter("Name"));
					System.out.println("The email is ===>"+req.getParameter("depositdate"));
					if(req.getParameter("flag").equalsIgnoreCase("submit"))
					{
						System.out.println("I am inside Submit");
						d_email="sumanthkeith@gmail.com";
						d_password="##########";
						String d_host="smtp.gmail.com";
						String d_port="465";
						String m_to="sumanth@yahoo.com";
						String m_subject="Testing";
						String m_text="Hey This is sumanth";
						
						Properties props=new Properties();
						props.put("mail.smtp.user",d_email);
						props.put("mail.smtp.host",d_host);
						props.put("mail.smtp.port",d_port);
						props.put("mail.smtp.starttls.enable", "true");
						//props.put("mail.smtp.debug","true");
						props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
						props.put("mail.smtp.socketFactory.fallback","false");
						SecurityManager security=System.getSecurityManager();
						try{
							Authenticator auth=new SMTPAuthenticator();
							Session session=Session.getInstance(props,auth);
							session.setDebug(true);
							MimeMessage msg=new MimeMessage(session);
							msg.setText(m_text);
							msg.setSubject(m_subject);
							msg.setFrom(new InternetAddress(d_email));
							msg.addRecipient(Message.RecipientType.TO,new InternetAddress(m_to));
							Transport.send(msg);
							
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						
						
					}
					
					
					
					
				}
				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}

				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
		
		//Loans Part 
		if(map.getPath().equalsIgnoreCase("/TermDeposit/LoansSanctionDibursementMenu"))
		{
			 System.out.println("HI i am in LoansSanctionDibursementMenu**********");
				try
			   
			   {
				    com.scb.td.forms.LoanSactionDisburse loansd=(com.scb.td.forms.LoanSactionDisburse) form;
				    System.out.println("***********1112");
					String pageId = loansd.getPageId();
					System.out.println("***********1113"+pageId);
					req.setAttribute("pagename",pageId);

					System.out.println("pageid====="+pageId);
					
					if(MenuNameReader.containsKeyScreen(loansd.getPageId()))
					{
						System.out.println("Hey boy u r good");
						path = MenuNameReader.getScreenProperties(loansd.getPageId());

						TDDelegate tDelegate = new TDDelegate();

						setInitParam(req, path, tDelegate);
						System.out.println("path****"+path);
						req.setAttribute("pageId",path);
	                    System.out.println("SYS date---> "+tddelegate.getSysdate());
						
						System.out.println("NEW FUTURE DATE=====> "+tddelegate.getFutureSysdateMail());
						String nextpaydate=tddelegate.getFutureSysdateMail();
						

						System.out.println("the path"+path);
						
						
						
						
						return map.findForward(ResultHelp.getSuccess()); 
					}else 
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("The given request is not found", req, path);

						return map.findForward(ResultHelp.getError());

					}
				   
			   }
			   catch(Exception e){
					e.printStackTrace();
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(""+e, req, path);
					return map.findForward(ResultHelp.getError());
				}
		}
		else if(map.getPath().equalsIgnoreCase("/TermDeposit/LoansSanctionDibursement"))
		{
		try{
		System.out.println(" I am in LoansSanctionDibursement Sumanth&&&&&&&&&&&");
		
		System.out.println("The LoanSanction value of flag=====> "+req.getParameter("flag"));
		
		LoanMasterObject lmaster=null;
		
		TDDelegate tldeligate = new TDDelegate();
		setInitParam(req, path, tldeligate);
	    LoanTransactionObject ltrn=new LoanTransactionObject();
		com.scb.td.forms.LoanSactionDisburse loansd=(com.scb.td.forms.LoanSactionDisburse) form;
		System.out.println("Loan Acount type----> "+loansd.getLn_ac_type());
		System.out.println("Loan Acount NUmber----> "+loansd.getLn_acno());
		
        System.out.println("Temp value ON Top===> "+loansd.getTemp());
	    
	    System.out.println("the par val ON Top=======+> "+req.getParameter("temp"));
		
		
		int acc_number=loansd.getLn_acno();
		String ac_type=loansd.getLn_ac_type();
		System.out.println("ac_type ac_type type----> "+ac_type);
		System.out.println("acc_number SUMANTHHHHHHHHHHHH---> "+acc_number);
		
			lmaster=new LoanMasterObject();
			lmaster=tldeligate.getLoanMasterDetailsAdd(acc_number,	ac_type);
			
		     String[] details=tldeligate.getdetails();
		     System.out.println("ooooooooo----> "+details[0]);
		
		if(lmaster!=null)
		{
			if(lmaster.uv.getVerId()==null)
			{
				loansd.setTesting("Loan is not yet verified");

			}
			else
			{
				StringTokenizer stk=null;
				String a=null;
				int ac_no=0;
				System.out.println("Share account number========> "+lmaster.getShareAccNo());
				System.out.println("Interest rate------> "+lmaster.getInterestRate());
				System.out.println("Number Of instalMents----> "+lmaster.getNoOfInstallments());
				System.out.println("lmaster.getPrior()=======> "+lmaster.getPrior());
				System.out.println("Installment Amount----> "+lmaster.getInstallmentAmt());
				loansd.setLn_sharenumber(lmaster.getShareAccNo());
				loansd.setLn_purpose(lmaster.getPurposeCode());
				//loansd.setLn_details(lnobj.getd)
				
				loansd.setLn_amount(lmaster.getSanctionedAmount());
				loansd.setLn_period(lmaster.getNoOfInstallments());
				loansd.setLn_months(lmaster.getHolidayPeriod());
				loansd.setLn_installments(lmaster.getInstallmentAmt());
				loansd.setLn_intrate(lmaster.getInterestRate());
				
				double prate=tldeligate.getPenalIntRatedetails(loansd.getLn_ac_type(), tldeligate.getSysdate(), 3);
				
				
				
				loansd.setLn_penalrate(lmaster.getPenalRate());
				loansd.setLn_priority(lmaster.getPrior());
				ltrn=tldeligate.getUnVerifiedDisbursementDetails(lmaster.getAccType(), lmaster.getAccNo());
			    if(ltrn!=null)
			    {
			    	loansd.setLn_disbamtleft(ltrn.getTransactionAmount());
			    
			    if(ltrn.getTranMode().equals("C"))
			    	loansd.setLn_paymode("C");
			    if(ltrn.getTranMode().equals("P"))
			    	loansd.setLn_paymode("P");
			    if(ltrn.getTranMode().equals("T"))
				{	
					
					System.out.println("Narration"+ltrn.getTranNarration());

					 stk=new StringTokenizer(ltrn.getTranNarration());
					 a=stk.nextToken();					
					 ac_no=Integer.parseInt(stk.nextToken());
					System.out.println("----Value of a----> "+a);
					System.out.println("----Value of ac_no----> "+ac_no);
					loansd.setLn_payac_no(ac_no);
					
					AccountObject[] payac=tldeligate.getAccounts(a, ac_no, tddelegate.getSysdate());
				    
				    if(payac!=null)
				    {
				    	loansd.setLn_custname(payac[0].getAccname());
				    }
					
				}
			    }
			    //
			    String items_relavent[]=null;
			    String items_combo_details_relevent[]=null;
			    String items_combo_details[]={"Personal","Relative","Employment","Application","Loan and Share Details","Ammendments","Signature Ins","Properpty ","CoBorrower ","Surities ","Vehicle ","Gold "};
			    System.out.println("The value of Forward-----> "+loansd.getForward());
			    System.out.println("The value of Parameter----> "+req.getParameter("forward"));
			    System.out.println("Temp value===> "+loansd.getTemp());
			    
			    System.out.println("the par val=======+> "+req.getParameter("temp"));
			    if(loansd.getTemp().equalsIgnoreCase("actype"))
				{
					/*makeVisible();
					clearForm();
					//details.setSelectedIndex(0);
					new_makeDisabled();
					if(e.getSource()==lntype) {
						if(lnmod!=null)
							lbl_loan_desc.setText(lnmod[lntype.getSelectedIndex()].getModuleDesc());
					}
					//remove_action_listener();
					flag_clear=false;*/
					System.out.println(" after removing the action listener");
					try
					{
						
						System.out.println("Loan Type====> "+loansd.getLn_ac_type());
						items_relavent=tldeligate.getReleventDetailsDeligate(loansd.getLn_ac_type());
						int count=0;
						
						if(items_relavent!=null)
						{
							for(int i=0;i<items_relavent.length;i++)
							{
								if(items_relavent[i].equals("Y"))
								{
									count++;
								}
								if(items_relavent[i].equals("O"))
								{
									count++;
								}
							}
						}
						System.out.println("the count Value:"+count);
						
						if(items_combo_details_relevent!=null)
							items_combo_details_relevent=null;
						items_combo_details_relevent = new String[count];
						count=0;
						if(items_relavent!=null)
						{
							for(int i=0;i<items_relavent.length;i++)
							{
								if(items_relavent[i].equals("Y"))
								{
									items_combo_details_relevent[count]=items_combo_details[i]+"***";
																
									count++;
								}
								if(items_relavent[i].equals("O"))
								{
									items_combo_details_relevent[count]=items_combo_details[i];
									count++;
								}
							}
						}
						//flag_clear=false;
						
						for(int i=0;i<count;i++)
						{
							//details.addItem(items_combo_details_relevent[i]);
							System.out.println("hmmmmm----> "+items_combo_details_relevent[i]);
							loansd.setLn_details(items_combo_details_relevent[i]);
							
						}
						//flag_clear=true;
						
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
					finally
					{
						//add_action_listener();
						
					}
				}
			    //
			    
			    
			   
			    
			    
			}
		
		}
		if(loansd.getLn_details()!=null)
		{
			System.out.println("The details Value is-----------> "+loansd.getLn_details());
			if(loansd.getLn_details().equalsIgnoreCase("Personal"))
			{
				/*System.out.println("i am in Personal Loan Part");
				CustomerMasterObject custmast1;
				System.out.println("21/MAr/2009 Sumanth is in personal------->");					 
				DepositMasterObject depmasterverify_val;
				//depmasterverify_val = tddelegate.getDepositMasterValues(td_closure.getAc_no(), td_closure.getAc_type());
				//int cid=depmasterverify_val.getCustomerId();
				System.out.println("the cid is===> "+cid);
				custmast1=tddelegate.getCustomer(cid);
				System.out.println("custmast1====? "+custmast1.getDOB());
				String bdate= tddelegate.getBOD(cid);
				String sysdate= tddelegate.getSysdate();

				StringTokenizer d=new StringTokenizer(bdate,"/");
				d.nextToken(); d.nextToken();
				int yy=Integer.parseInt(d.nextToken());

				Calendar cal=Calendar.getInstance();
				int age= cal.get(Calendar.YEAR)-yy;
				System.out.println("Age is---------"+age);
				req.setAttribute("personalInfo",custmast1);	
				req.setAttribute("age",age);   
				String custpersonal_add=tddelegate.getNomineeAddress(cid);
				System.out.println("custpersonal_add---> "+custpersonal_add);*/

				//req.setAttribute("Address",custpersonal_add);
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
			}else if(loansd.getLn_details().equalsIgnoreCase("Relative"))
			{
				System.out.println("i am in Side Relative");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5093"));
			}
			else if(loansd.getLn_details().equalsIgnoreCase("Application"))
			{
				System.out.println("i am in Side Application");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5002"));
			}
			else if(loansd.getLn_details().equalsIgnoreCase("Properpty"))
			{
				System.out.println("i am in Side Properpty");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5004"));
			}
			else if(loansd.getLn_details().equalsIgnoreCase("Vehicle"))
			{
				
				System.out.println("i am in Side Vehicle");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5003"));
			}
			else if(loansd.getLn_details().equalsIgnoreCase("Gold"))
			{
				
				System.out.println("i am in Side Gold");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5094"));
			}
			else if(loansd.getLn_details().equalsIgnoreCase("CoBorrower"))
			{
				
				System.out.println("i am in Side Gold");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5032"));
			}
			else if(loansd.getLn_details().trim().equalsIgnoreCase("Loan and Share Details"))
			{
				
				System.out.println("i am in Side Loan and Share Details");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("5029"));
			}
			else if(loansd.getLn_details().trim().equalsIgnoreCase("Signature"))
			{
				
				System.out.println("i am in Side Signature");
				req.setAttribute("panelName",CommonPanelHeading.getPersonal());
				req.setAttribute("flag",MenuNameReader.getScreenProperties("13028"));
			}
		}
		
		
		
		
		if(MenuNameReader.containsKeyScreen(loansd.getPageId().trim()))
		{
			path=MenuNameReader.getScreenProperties(loansd.getPageId());
			req.setAttribute("pageId",path);
			return map.findForward(ResultHelp.getSuccess());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		}
//		Renewal notice Started here

		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/TermDepositRenewalNoticeMenu"))
		{

			try{
				System.out.println("In Term deposit Renewal notice111");	
				System.out.println("check");
				TDDelegate tDelegate = new TDDelegate();
				RenewalNoticeForm renewalform = (RenewalNoticeForm)form;
				
				renewalform.setSysdate(tDelegate.getSysdate()); 
				System.out.println("renewalformrenewalformrenewalform"+renewalform);

				//	System.out.println("In Term deposit Renewal notice112"+renewalform.getPageId());

				String pageId = renewalform.getPageId();
				System.out.println("In Term deposit Renewal notice113");
				req.setAttribute("pagename",13012);

				System.out.println("pageid====="+pageId);
				
                 if(pageId!=null)
                 {
       				if(MenuNameReader.containsKeyScreen(renewalform.getPageId())){

					path = MenuNameReader.getScreenProperties(renewalform.getPageId());

					
					
					setInitParam(req, path, tDelegate);
					System.out.println("path****"+path);
					req.setAttribute("pageId",path);


					System.out.println("the path"+path);

					return map.findForward(ResultHelp.getSuccess()); 

				}
                 

				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}
                 
                 }

			}
			
			catch(Exception e){
				e.printStackTrace();
			}

		}


		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/TermDepositRenewalNotice"))
		{

			try{

				RenewalNoticeForm renewalform=(RenewalNoticeForm)form;
				TDDelegate tDelegate = new TDDelegate();
				renewalform.setSysdate(tDelegate.getSysdate()); 
				String pageId = renewalform.getPageId();

				req.setAttribute("pagename",pageId);

				System.out.println("pageid====="+pageId);
	
                if(pageId!=null)
                {	
				if(MenuNameReader.containsKeyScreen(renewalform.getPageId())){

					path = MenuNameReader.getScreenProperties(renewalform.getPageId());

					

					setInitParam(req, path, tDelegate);
					System.out.println("path****"+path);
					req.setAttribute("pageId",path);
					   

					System.out.println("the path"+path);

					String method=req.getParameter("method");
					System.out.println("The method is "+method);
					DepositReportObject[] dep_rep_obj=null;
					dep_rep_obj=tDelegate.RetriveRenewalNotice(renewalform.getFromdt(),renewalform.getTodt(),renewalform.getAc_type(),1,0,null);
				
					if(method.equalsIgnoreCase("DownLoad"))
					{
	               		    res.setContentType("application/.csv");
	               	        res.setHeader("Content-disposition", "attachment;filename=InterestAccruedReport.csv");
	               	        java.io.PrintWriter out = res.getWriter();
	               	        out.print("\n");
	               	        out.print("\n");
	               	        out.print("\n");
	               	        out.print(",");out.print(",");out.print(",");
	               	        out.print("TermDepositRenewalNotice Details for A/C Type: "+dep_rep_obj[0].getAcctype());
	               	        out.print("\n");
	               	        out.print("\n");
	                  		   /*HSSFCell cell = row.createCell((short)0);
	                  		   cell.setCellValue(1);*/
	               	        out.print("sl no"); out.print(",");
	                  		out.print("Account Type"); out.print(",");
	                  		out.print("Ac/No"); out.print(",");
	                  		out.print("Depositor Name"); out.print(",");
	                  		out.print("Deposit Amount"); out.print(",");
	                  		out.print("Period"); out.print(",");
	                  		out.print("MaturityDate");
	                  		out.print(",");
	                  		out.print(""); 
	                  		out.print("\n");
	                  		 for(int k=0;k<dep_rep_obj.length;k++)
	                  		 {
	                  			out.print(k+1);
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getAcctype());
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getAccno());
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getName());
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getDepAmt());
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getPeriod());
	                   			out.print(","); 
	                   			out.print(dep_rep_obj[k].getMatDate());
	                   			out.print(","); 
	                   			out.print("\n");
	                   			
	                  		 }
	                  		req.setAttribute("msg","Saved to excel file in C:");
	    				    return null;
					}
					else if(method.equalsIgnoreCase("View")){
						System.out.println("The ac type is"+renewalform.getAc_type()); 
						System.out.println("The from date is"+renewalform.getFromdt());
						System.out.println("The to date is"+renewalform.getTodt());
						List ren_list=new ArrayList();
						dep_rep_obj=tDelegate.RetriveRenewalNotice(renewalform.getFromdt(),renewalform.getTodt(),renewalform.getAc_type(),1,0,null);
						if(dep_rep_obj==null)
                         {
							System.out.println("Hello boys ia m inside NULLLLLLLLLLLL");
                        	 renewalform.setTesting("Records Not Found");   	 
                         }
                         else{



						for(int i=0;i<dep_rep_obj.length;i++){
							Map ren_map=new TreeMap();
							ren_map.put("id",""+(i+1));
							ren_map.put("actype", dep_rep_obj[i].getAcctype());
							ren_map.put("accno",dep_rep_obj[i].getAccno());
							ren_map.put("name",dep_rep_obj[i].getName());
							ren_map.put("dep_amt",dep_rep_obj[i].getDepAmt());
							ren_map.put("period", dep_rep_obj[i].getPeriod());
							ren_map.put("mat_date",dep_rep_obj[i].getMatDate());
							ren_list.add(ren_map);
						}
						session=req.getSession(true);
						session.setAttribute("renewalnotice",ren_list);
						req.setAttribute("renewalnotice",ren_list);
					}
					}
											
										return map.findForward(ResultHelp.getSuccess()); 

				}

				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}

                }
			}catch(Exception e){
				e.printStackTrace();
			}

		}
//		code for Quantum wise report...	
		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/QuantumwiseReportMenu"))
		{

			try{
				TDDelegate tDelegate = new TDDelegate();

				QuantumwiseReport quantum_wise = (QuantumwiseReport)form;
				String pageId = quantum_wise.getPageId();

				req.setAttribute("pagename",pageId);
				quantum_wise.setSysdate(TDDelegate.getSysdate());
				System.out.println("pageid====="+pageId);


				if(MenuNameReader.containsKeyScreen(quantum_wise.getPageId())){

					path = MenuNameReader.getScreenProperties(quantum_wise.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****"+path);
					req.setAttribute("pageId",path);


					System.out.println("the path"+path);

					return map.findForward(ResultHelp.getSuccess()); 

				}

				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}


			}catch(Exception e){
				e.printStackTrace();
			}

		}


		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/QuantumwiseReport"))
		{
			try{
				TDDelegate tddelegate=new TDDelegate();
				QuantumwiseReport quantum_wise = (QuantumwiseReport)form;
				String pageId=quantum_wise.getPageId();
				DepositMasterObject[] depo_masto=null; 
				quantum_wise.setSysdate(TDDelegate.getSysdate());
				System.out.println("pageId= of Quantum report=="+quantum_wise.getPageId());
				req.setAttribute("pagename",pageId);
				setInitParam(req, path, tddelegate);

				if(MenuNameReader.containsKeyScreen(quantum_wise.getPageId())){
					path=MenuNameReader.getScreenProperties(quantum_wise.getPageId());
					tddelegate=new TDDelegate();
					System.out.println("inside menu- Quantum wise report");
					setInitParam(req, path, tddelegate);


					System.out.println("path****"+path);
					req.setAttribute("pageId",path);
					System.out.println("the path"+path);
					System.out.println("SCROLLLLLLLLLLLLLLLLLLLLl===> "+quantum_wise.getCombo_periodyears().trim());
					/*DepositReportObject[] deprep=null;
					for(int i=)
					deprep=tddelegate.getQuantumLimt(quantum_wise.getAc_type(),1);

					System.out.println("The Scroll number is ======> "+deprep[0].getScrollno());*/
					/*if(period_wise.getAc_type().equalsIgnoreCase("1003001")){

			 System.out.println("account type=="+period_wise.getAc_type());

			 req.setAttribute("periodwise",tddelegate.getPeriodWise(period_wise.getAc_type(), period_wise.getCombo_periodyears(),1 , period_wise.getSrlno()));

			 System.out.println("{ inside fd loop..");
		 }*/
					/*	if{
			 System.out.println("other than fd....");

			 req.setAttribute("interestreport",tddelegate.getIntAccruedInfo(int_accrued.getAc_type(),int_accrued.getFrom_date(), int_accrued.getTo_date(),0));
		   }*/
					System.out.println("BEFORE VIEW srlno==="+quantum_wise.getCombo_periodyears().trim());
                                        
					System.out.println("==Button Print="+quantum_wise.getForward());
					   
					if(quantum_wise.getForward().equalsIgnoreCase("DownLoad"))
					{
						System.out.println("I am in Print Block");
						DepositMasterObject[] deptrn_obj=null;
						deptrn_obj=depo_masto=tddelegate.getQuantumWise(quantum_wise.getAc_type(), quantum_wise.getProcess_date(), 1,Integer.parseInt(quantum_wise.getCombo_periodyears().trim()));
						
						if(deptrn_obj==null)
						{
							quantum_wise.setTesting("Cannot Print");	
						}
						else
						{
							res.setContentType("application/.csv");
	               	        res.setHeader("Content-disposition", "attachment;filename=InterestAccruedReport.csv");
	               	        
	               	        java.io.PrintWriter out = res.getWriter();
	               	        out.print("\n");
	               	        out.print("\n");
	               	        out.print("\n");
	               	        out.print(",");out.print(",");out.print(",");
	               	        out.print("PassBook Details for A/C Type: "+deptrn_obj[0].getAccType());
	               	        out.print("\n");
	               	        out.print("\n");
	               	        out.print("Srl-No");out.print(",");
	               	        out.print("Act_type");out.print(",");
							out.print("Ac_No");out.print(",");
							out.print("Customer ID");out.print(",");
							out.print("Depositor Name");out.print(",");
							out.print("Category");out.print(",");
							out.print("Deposit Date");out.print(",");
							out.print("Maturity Date");out.print(",");
							out.print("Period in Days");out.print(",");
							out.print("Interest rate");out.print(",");
							out.print("Interest Freq");out.print(",");
							out.print("Interest Upto");out.print(",");
							out.print("Deposit Amt");out.print(",");
							out.print("Maturity Amt");out.print(",");
							out.print("Interest Accrued");out.print(",");
							out.print("Interest Paid");out.print(",");
							out.print("Colose-dat");out.print(",");
							out.print("\n");
								for(int i=0;i<deptrn_obj.length;i++){
									out.print(i+1);
									out.print(","); 
									out.print(deptrn_obj[i].getAccType());
									out.print(",");
									out.print(deptrn_obj[i].getAccNo());
									out.print(",");
									out.print(deptrn_obj[i].getCustomerId());
									out.print(",");
									out.print(deptrn_obj[i].getName());
									out.print(",");
									out.print(deptrn_obj[i].getCategory());
									out.print(",");
									out.print(deptrn_obj[i].getDepDate());
									out.print(",");
									out.print(deptrn_obj[i].getMaturityDate());
									out.print(",");
									out.print(deptrn_obj[i].getPeriod_in_days());
									out.print(",");
									out.print(deptrn_obj[i].getInterestRate());
									out.print(",");
									out.print(deptrn_obj[i].getInterestFrq());
									out.print(",");
									out.print(deptrn_obj[i].getInterestUpto());
									out.print(",");
									out.print(deptrn_obj[i].getDepositAmt());
									out.print(",");
									out.print(deptrn_obj[i].getMaturityAmt());
									out.print(",");
									out.print(deptrn_obj[i].getInterestAccured());
									out.print(",");
									out.print(deptrn_obj[i].getInterestPaid());
									out.print(",");
									out.print(deptrn_obj[i].getClosedt());
									out.print(",");
									out.print("\n");
								}								   
								req.setAttribute("msg","Saved to excel file in C:");
		    				    return null;
							
					}
					}
					
					
					
					
					
					
					if(quantum_wise.getProcess_date().length()!=0){
						if(quantum_wise.getBut_view()!=null){
						if(quantum_wise.getBut_view().equalsIgnoreCase("View"))
						{ 

							System.out.println("process date==="+quantum_wise.getProcess_date());

							System.out.println("srlno==="+quantum_wise.getCombo_periodyears().trim());
							depo_masto=tddelegate.getQuantumWise(quantum_wise.getAc_type(), quantum_wise.getProcess_date(), 1,Integer.parseInt(quantum_wise.getCombo_periodyears().trim()));
							//depo_masto=tddelegate.getQuantumWise(quantum_wise.getAc_type(), quantum_wise.getProcess_date(), 1,deprep[0].getScrollno());
							if(depo_masto!=null){
								quantum_wise.setTesting("");	
								req.setAttribute("quantumwise",depo_masto);
							}
							else if(depo_masto==null){
								
								quantum_wise.setTesting("Records Not Found!!!!");	
							}

							System.out.println("after req.setattribute==="+ quantum_wise.getSrlno());

						}
						
						
					}
						
						
						
						
						
						
					}
					return map.findForward(ResultHelp.getSuccess()); 

				}		 
				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}



			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		//code for MIS Report
		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/MISReportMenu"))
		{

			try{
				TDDelegate tDelegate = new TDDelegate();

				MISReport mis_report = (MISReport)form;
				String pageId = mis_report.getPageId();
				req.setAttribute("pagename",pageId);

				System.out.println("pageid====="+pageId);
				mis_report.setSysdate(TDDelegate.getSysdate());

				if(MenuNameReader.containsKeyScreen(mis_report.getPageId())){

					path = MenuNameReader.getScreenProperties(mis_report.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****"+path);
					req.setAttribute("pageId",path);

					System.out.println("the path"+path);
					mis_report.setProcess_date(tddelegate.getSysdate());
							return map.findForward(ResultHelp.getSuccess()); 

				}

				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}


			}catch(Exception e){
				e.printStackTrace();
			}

		}


		else if(map.getPath().trim().equalsIgnoreCase("/TermDeposit/MISReport"))
		{
			try{
				TDDelegate tddelegate=new TDDelegate();
				MISReport mis_report = (MISReport)form;
				String pageId=mis_report.getPageId();
				mis_report.setSysdate(TDDelegate.getSysdate());
				System.out.println("pageId==="+mis_report.getPageId());
				req.setAttribute("pagename",pageId);
				setInitParam(req, path, tddelegate);

				if(MenuNameReader.containsKeyScreen(mis_report.getPageId())){
					path=MenuNameReader.getScreenProperties(mis_report.getPageId());
					tddelegate=new TDDelegate();
					System.out.println("inside menu- Passbook");
					setInitParam(req, path, tddelegate);



					System.out.println("path****"+path);
					req.setAttribute("pageId",path);
					System.out.println("the path"+path);


					if(mis_report.getProcess_date().length() !=0){


						System.out.println("account type=="+mis_report.getAc_type());


						System.out.println("processing date==="+mis_report.getProcess_date());

					}
					  
					if(mis_report.getForward().equalsIgnoreCase("DownLoad"))
					{
						DepositMasterObject[] deptrn_obj = null;
						System.out.println("process date==="+mis_report.getProcess_date());
						deptrn_obj=tddelegate.getMisReport(mis_report.getAc_type(), mis_report.getProcess_date());
						if(deptrn_obj==null)
						{
							mis_report.setTesting("Cannot Print");	
						}
						else
						{
							
								res.setContentType("application/.csv");
		               	        res.setHeader("Content-disposition", "attachment;filename=InterestAccruedReport.csv");
		               	        
		               	        java.io.PrintWriter out = res.getWriter();
		               	        out.print("\n");
		               	        out.print("\n");
		               	        out.print("\n");
		               	        out.print(",");out.print(",");out.print(",");
		               	        out.print("MISReport Details for A/C Type: "+deptrn_obj[0].getAccType());
		               	        out.print("\n");
		               	        out.print("\n");
								out.print("Ac_No");out.print(",");
								out.print("Depositor Name");out.print(",");
								out.print("Deposit Date");out.print(",");
								out.print("Maturity Date");out.print(",");
								out.print("Deposit Amount");out.print(",");
								out.print("Maturity Amount");out.print(",");
								out.print("Interest Paid");out.print(",");
								out.print("Cum Interest");out.print(",");
								out.print("\n");
								for(int i=0;i<deptrn_obj.length;i++){
									out.print(i);
		                   			out.print(","); 									
									out.print(deptrn_obj[i].getAccNo());
									out.print(",");
									out.print(deptrn_obj[i].getDepCatName());
									out.print(",");
									out.print(deptrn_obj[i].getDepDate() );
									out.print(",");
									out.print(deptrn_obj[i].getDepositAmt());
									out.print(",");
									out.print(deptrn_obj[i].getMaturityAmt());
									out.print(",");
									out.print(deptrn_obj[i].getInterestPaid());
									out.print(",");
									out.print(deptrn_obj[i].getCumInterest());
									out.print(",");
									out.print("\n");
								}								   
								req.setAttribute("msg","Saved to excel file in C:");
		    				    return null;
						}
					}      
					
					
					
					if(mis_report.getBut_view().equalsIgnoreCase("View")){
						DepositMasterObject[] dep_mis = null;
						System.out.println("process date==="+mis_report.getProcess_date());
						dep_mis=tddelegate.getMisReport(mis_report.getAc_type(), mis_report.getProcess_date());
						if(dep_mis!=null){
						req.setAttribute("misreport",dep_mis);
						mis_report.setTesting("");
						System.out.println("after req.setattribute==="+ mis_report.getProcess_date());
						}else{
							mis_report.setTesting("Records Not Found");
						}
					}

					return map.findForward(ResultHelp.getSuccess()); 

				}	
				else 
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);

					return map.findForward(ResultHelp.getError());

				}



			}catch (Exception e) {
				e.printStackTrace();
			}

		}
			
		
		//path=MenuNameReader.getScreenProperties(acc_de.getPageId);
		return map.findForward(ResultHelp.getSuccess());
		}
	
	public class SMTPAuthenticator extends javax.mail.Authenticator{
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(d_email,d_password);
		}

	}
	

	private void setErrorPageElements(String exception,HttpServletRequest req,String path){
		req.setAttribute("exception",exception);
		req.setAttribute("pageId",path);

	}
	private void setInitParam(HttpServletRequest req,String path,TDDelegate tDelegate)throws Exception{
		req.setAttribute("pageId",path);
		req.setAttribute("date",TDDelegate.getSysdate());
		req.setAttribute("Dep type",tDelegate.getModulesTypes());
		req.setAttribute("intro_type", tDelegate.getModTypes());
		//System.out.println("module type"+tddelegate.getModulesTypes());
		req.setAttribute("details",tDelegate.getdetails());
        
		req.setAttribute("pay_mode",tDelegate.getpayMode());
		req.setAttribute("pay_actype",tDelegate.getpayAcctype());
		req.setAttribute("amt_recv", tDelegate.getpayMode());
		req.setAttribute("auto",tDelegate.getauto());
		
		req.setAttribute("combo_mat_cat1",tDelegate.getMatCat());
		req.setAttribute("int_freq",tDelegate.getInt_freq());
		req.setAttribute("visible1", "visible");
		req.setAttribute("invisible","hidden");
		req.setAttribute("Combodate",tDelegate.getCombodate("1003001"));
		req.setAttribute("quantumlimit", tDelegate.getQuantumLimt("1003001",1));// temp comment
		req.setAttribute("periodlimit",tDelegate.getPeriodLimit("1003%,1004%,1005%", 0));
		req.setAttribute("opendetail",tDelegate.getopenclosedetails());
	
	//Loan purpose
		
	    req.setAttribute("loanActype", tDelegate.getLoanTypes(2, "1010000"));
	    req.setAttribute("laonPurpose", tDelegate.getLoanPurposeDetails());
	    
	    req.setAttribute("comDetails", tDelegate.getLoanComboDeatils());
	    
	    req.setAttribute("LNpriority", tDelegate.getPriorityDesc());
	}
	private void setAdminInitParam(HttpServletRequest req,String path,TDDelegate tDelegate)throws Exception{
		req.setAttribute("pageId",path);
		req.setAttribute("FdAcType",tDelegate.getAdminModules());
		req.setAttribute("IntType",tDelegate.getAdminTableNames());

	}

//added by sumnath to cal maturity date is ammendments form	if changed
	boolean setMaturityDate(TDDelegate deligate,ApplnDE appl,HttpServletRequest req) 
	{
		try
		{
		
		int int_modulecode_minperiod=0;
		ModuleObject[] array_moduleobject_td_acctype=deligate.getModulesTypes();
		int int_module_code_type=0,index=-1;
		System.out.println("appl.getAc_type()===>"+appl.getAc_type()); 
		if(appl.getAc_type().equalsIgnoreCase("1003000"))
		{
			index=0;
			int_module_code_type=1;
		}
		if(appl.getAc_type().equalsIgnoreCase("1004000"))
		{
					index=1;
					int_module_code_type=2;
		}
		if(appl.getAc_type().equalsIgnoreCase("1005000"))
		{
					index=2;
					int_module_code_type=3;
		}

		if(index!=-1)
			int_modulecode_minperiod = array_moduleobject_td_acctype[index].getMinPeriod();
		
		if(appl.getPeriod_of_days() < int_modulecode_minperiod)
		{
			if(int_module_code_type==2)
				//JOptionPane.showMessageDialog(null,"No of Installments should be greater than "+int_modulecode_minperiod);
				System.out.println("HI in if");
			else
				System.out.println("Hi from else"); 
				//JOptionPane.showMessageDialog(null,"No of Periods should be greater than "+int_modulecode_minperiod);					
		}
			
		
			if(int_module_code_type==2){
				try {
					String mat_date = deligate.getFutureMonthDate(appl.getDep_date(),appl.getPeriod_of_days());
					System.out.println("Mat date1===>"+mat_date+"Sysdate--> "+tddelegate.getSysdate());
					if(appl.getMat_date().length()==0 )
					{
						appl.setTesting("Maturity Date Shoud Not Be Zero");	
					}
					else if(Validations.dayCompare(mat_date,tddelegate.getSysdate())>0)
					{
						
						int Days=Validations.dayCompare(appl.getMat_date(),tddelegate.getSysdate());
					     System.out.println("in side date checking less than today s  date "+ Days);      
					     appl.setTesting("Maturity Date Cannot Be Less Than Todays Date");
					
				  }
					else{
						appl.setTesting("Ok");
					}
					req.setAttribute("MatDateCal",mat_date);
					System.out.println("The NO of days-------> "+appl.getPeriod_of_days());
					req.setAttribute("chperddays",appl.getPeriod_of_days());
				} catch (Exception exception) {
					exception.printStackTrace();
				}						
			}
			else
			{
				String date;
				try {
					date = deligate.getFutureDayDate(appl.getDep_date(),appl.getPeriod_of_days());
					System.out.println("date2===>"+date); 
					System.out.println("The NO of days-------> "+appl.getPeriod_of_days());
					System.out.println("Mat date1===>"+date+"Sysdate--> "+tddelegate.getSysdate());
					if(appl.getMat_date().length()==0 )
					{
						appl.setTesting("Maturity Date Shoud Not Be Zero");	
					}
					else if(Validations.dayCompare(date,tddelegate.getSysdate())>0)
					{
						
						int Days1=Validations.dayCompare(appl.getMat_date(),tddelegate.getSysdate());
					     System.out.println("in side date checking less than today s  date "+ Days1);      
					     appl.setTesting("Maturity Date Cannot Be Less Than Todays Date");
					
				  }
					else{
						appl.setTesting("Ok");
					}
					
					req.setAttribute("MatDateCal",date);
					req.setAttribute("chperddays",appl.getPeriod_of_days());
				} catch (RemoteException e) {
					e.printStackTrace();
				}                        
			}
						

		
	
	}catch (Exception e) {
		
		e.printStackTrace();
	}
		return true;
	}
	
boolean setInterestRate(TDDelegate deligate,ApplnDE appl,HttpServletRequest req) throws RemoteException
{ 
	  System.out.println("I am in setInterestRate------------------------->");
	
	   ModuleObject[] array_moduleobject_td_acctype=deligate.getModulesTypes();
	   double array_double_deposit_interest[]={0,0,0};
	   double old_rate = Double.valueOf((appl.getInt_rate()));
	   DepositMasterObject dep_open_obj = new DepositMasterObject();
	   for(int i = 0;i<3;i++)
			array_double_deposit_interest[i] = 0;
		try
		{
			System.out.println("selected module code "+ deligate.getModulesTypes());
			
					
			System.out.println("---------------"+deligate.getModulesTypes()+"---------------"+0+"---------------"+201);
					
			if(appl.getAc_type().equalsIgnoreCase("1004000"))
				
				
				array_double_deposit_interest=deligate.getDepositInterestRate(appl.getAc_type(),0,201,deligate.getSysdate(),appl.getPeriod_of_days(),appl.getDep_amt());
			else if(appl.getAc_type().equalsIgnoreCase("1003000"))
				array_double_deposit_interest=deligate.getDepositInterestRate(appl.getAc_type(),0,201,deligate.getSysdate(),appl.getPeriod_of_days(),appl.getDep_amt()); 
			else
				array_double_deposit_interest=deligate.getDepositInterestRate(appl.getAc_type(),0,201,deligate.getSysdate(),appl.getPeriod_of_days(),appl.getDep_amt());
			
			if(old_rate != array_double_deposit_interest[0])
				int_change = true;
				
			System.out.println("Int Payable-----> "+numberFormat.format(array_double_deposit_interest[0]+array_double_deposit_interest[1]+array_double_deposit_interest[2]));
			
			appl.setInt_payable(numberFormat.format(array_double_deposit_interest[0]+array_double_deposit_interest[1]+array_double_deposit_interest[2]));
			
			
			
			/*if(array_double_deposit_interest[0]!=0)
			{*/
				//combo_interest_rate.removeItemListener(this);
				/*String ComMatCaty[]=deligate.getMatCat();
				combo_interest_rate.removeAllItems();
				combo_interest_rate.addItem("NONE");      //obj_personal_person
				combo_interest_rate.addItem("Category: "+(obj_personal_person.lbl_category.getText().length()>0?obj_personal_person.lbl_category.getText():"Others")+"  "+array_double_deposit_interest[1]);
				combo_interest_rate.addItem("SubCategory: "+(obj_personal_person.lbl_sub_category.getText().length() > 0?obj_personal_person.lbl_sub_category.getText():"Others")+"  "+array_double_deposit_interest[2]);
				combo_interest_rate.addItem("BOTH  "+String.valueOf(array_double_deposit_interest[1]+array_double_deposit_interest[2]));
				*/
				//combo_interest_rate.addItemListener(this);
				
				if(appl.getAc_type().equalsIgnoreCase("1004000")){ 
					//lbl_maturity_amount.setText(numberFormat.format(Validations.rdInterestCalculation(Double.parseDouble(lbl_deposit_amount.getText()),Integer.parseInt(txt_td_period.getText()),Double.parseDouble(lbl_interest_rate.getText()),lbl_deposit_date.getText(),txt_maturity_date.getText())));
					DepositIntRepObject deporepobj[]=tddelegate.interestCalculation(1, appl.getAc_type(),appl.getUtml(),appl.getUid(),appl.getUdate());
				}	
					
					if(appl.getAc_type().equalsIgnoreCase("1003000"))
						dep_open_obj.setMaturityAmt(appl.getMat_amt());
				else if(appl.getAc_type().equalsIgnoreCase("1005000")){
					DepositIntRepObject deporepobj2[]=tddelegate.interestCalculation(2, appl.getAc_type(),appl.getUtml(),appl.getUid(),appl.getUdate());
			       } 
			else
			{
				
					
				//txt_td_period.setText("");
				//txt_td_period.requestFocus(); 
				//JOptionPane.showMessageDialog(null,"Interest rate not defined for this Account Type");							
			}
			
			
			
			
		
		}
			catch(RemoteException ex){
			ex.printStackTrace();
			
		    
			}
			return true;
}
			

	
	private void setPayableAmount(TDDelegate deligate,ApplnDE appl,HttpServletRequest req) {
		try 
		{  
			System.out.println("EllowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwW");
			DepositMasterObject dep_ammendment = new DepositMasterObject();
			    if(appl.getDep_date().length() > 0 && appl.getMat_date().length() > 0 ) {
				double days=0;
				String[] intfreq = deligate.getInt_freq();
				System.out.println("selectde index of combo_interest"+intfreq+"-----"+deligate.getInt_freq());
				System.out.println("THE VALUE OF INT_RATEeeee ----> "+appl.getInt_rate());
				System.out.println("THE DEPOSIT AMO00000000nt ACTION CLASS=======> "+appl.getHidval());
				
				//appl.getDep_amt() is changed to appl.getHidval() as it was returning "0"
				
				double total_days = deligate.setPayableAmt(appl.getInterest_freq(), appl.getDep_date(),appl.getMat_date(),Double.valueOf(appl.getHidval()),appl.getInt_rate());
				System.out.println("total number of days()()()(()()() "+total_days);
				days = total_days;
				if(appl.getInterest_freq().equalsIgnoreCase("Monthly")) {
					//String to_date = commonRemote.getFutureMonthDate(lbl_deposit_date.getText(),1);
					//days = commonRemote.getDaysFromTwoDate(lbl_deposit_date.getText(),to_date);
					
					
					System.out.println("-------INSIDE MONTHLY 1------ "+appl.getHidval());
					String to_date = deligate.getFutureMonthDate(appl.getDep_date(), 1);
					System.out.println("&&&&&&&&&to_date&&&&&&&&&& "+to_date);
					days = deligate.setPayableAmt(appl.getInterest_freq(), appl.getDep_date(),to_date,Double.valueOf(appl.getHidval()),appl.getInt_rate());
					System.out.println("$$$$$$$$$$DAYS$$$$$$$$$$$ "+days);
				}
				
				else if(appl.getInterest_freq().equalsIgnoreCase("Quarterly")) {
					System.out.println("-------INSIDE Quarterly 2------ "+appl.getHidval());
					String to_date = deligate.getFutureMonthDate(appl.getDep_date(),3);
					days = deligate.setPayableAmt(appl.getInterest_freq(), appl.getDep_date(),to_date,Double.valueOf(appl.getHidval()),appl.getInt_rate());
				}
				else if(appl.getInterest_freq().equalsIgnoreCase("Halfyearly")) {
					System.out.println("-------INSIDE Halfyearly 3------ "+appl.getHidval());
					String to_date = deligate.getFutureMonthDate(appl.getDep_date(),6);
					days = deligate.setPayableAmt(appl.getInterest_freq(), appl.getDep_date(),to_date,Double.valueOf(appl.getHidval()),appl.getInt_rate());
				}
				else if(appl.getInterest_freq().equalsIgnoreCase("Yearly")) {
					System.out.println("-------INSIDE Yearly 4------ "+appl.getHidval());
					String to_date = deligate.getFutureMonthDate(appl.getDep_date(),12);
					days =deligate.setPayableAmt(appl.getInterest_freq(), appl.getDep_date(),to_date,Double.valueOf(appl.getHidval()),appl.getInt_rate());
				}
				
				System.out.println("no of days "+days +" total days " + total_days);
				System.out.println("INTEREST RATE^^^^^^^^^^^^^^^ "+appl.getInt_rate());
				if(days <= total_days) {
					
					//txt_payable.setText(numberFormat.format(new Double(Math.round((Double.parseDouble(lbl_deposit_amount.getText())*Double.parseDouble(lbl_interest_rate.getText())*days)/(36500)))));					
					
					
					//double rate=(Math.round(Double.valueOf((appl.getHidval()))*(appl.getInt_rate())*days)/36500);
					double rate=Math.round(((Double.valueOf((appl.getHidval())) * appl.getInt_rate() * days )/36500));
					System.out.println("THE VALUE OF INTRATE IN ACTION IS-*-*-*-*-* "+rate);
					//appl.setInt_payable(String.valueOf(rate));
					
					req.setAttribute("intereste",rate);
				}
				else {
					appl.getInterest_freq().equalsIgnoreCase("Select");
					
				}
			}
		} catch (RemoteException e) { 
			e.printStackTrace();
		} 

}
	
	
	
}








