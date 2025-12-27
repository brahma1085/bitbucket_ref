package com.scb.loans.action2;





import general.Validations;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.general.AccountObject;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.ModuleObject;
import masterObject.general.PropertyObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.VehicleObject;
import masterObject.loans.DCBObject;
import masterObject.loans.LedgerObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.NPAObject;
import masterObject.loans.PSWSObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.LoansDelegate;
import com.scb.loans.forms.AutomaticRecoveryForm;
import com.scb.loans.forms.DCBSchedulingForm;
import com.scb.loans.forms.DisbursementForm;
import com.scb.loans.forms.JewelReportForm;
import com.scb.loans.forms.LoanDocsUpdationForm;
import com.scb.loans.forms.LoanLeadgerForm;
import com.scb.loans.forms.LoanMeethingAgendaForm;
import com.scb.loans.forms.LoanNpaAdminForm;
import com.scb.loans.forms.LoanPassBookForm;
import com.scb.loans.forms.LoanSplIntRateUpdnForm;
import com.scb.loans.forms.LoansAmmendmentsForm;
import com.scb.loans.forms.LoansQuarterlyInterestForm;
import com.scb.loans.forms.NPAProcessForm;
import com.scb.loans.forms.NPAReportForm;
import com.scb.loans.forms.NPASummaryForm;
import com.scb.loans.forms.PSWSProcessingForm;
import com.scb.loans.forms.PSWSScheduleForm;
import com.scb.loans.forms.ReverseRecoveryForm;
import com.scb.props.MenuNameReader;
  
      
         
public class LoanAction2 extends Action 
{
	ModuleObject loan_modobj[]=null;
	DCBObject dcb[];
	Object data[][];
	boolean flag = false;
	int count_disburse=0;
	LoanReportObject loanRepObj=null,array_loanreportobject[][]=null;
	LoanTransactionObject ltrn[]=null;
	ModuleObject array_moduleobject[];
	NPAObject[] npa_values;    
	PSWSObject array_pswsobject[]=null;
	LedgerObject[] ledger_obj=null;
	LoansDelegate delegate = null;
	LoanTransactionObject lntrn = null;
	LoanMasterObject lmobj = null;
	String path = null;
	HttpSession session,sessions;
	SignatureInstructionObject[] signObject;
	String items_relavent[] = null;
	 String lnuser,lntml;
	 AdministratorDelegate admDelegate;
	 Map user_role;
	public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception
	{         
		System.out.println("<*****************In the Action Class 2***********************>"); 
		  sessions=req.getSession();
		    lnuser=(String)sessions.getAttribute("UserName");
	    	lntml=(String)sessions.getAttribute("UserTml") ;
	    	try{
	    		synchronized(req) {
					if(lnuser!=null){
						admDelegate=new AdministratorDelegate();
						user_role=admDelegate.getUserLoginAccessRights(lnuser,"LN");
						if(user_role!=null){
							req.setAttribute("user_role",user_role);
							if(req.getParameter("pageidentity.pageId")!=null){
								System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageidentity.pageId"));
								req.setAttribute("accesspageId",req.getParameter("pageidentity.pageId").trim());
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
		if(map.getPath().equalsIgnoreCase("/Loans/LoanDocsUpdationMenu"))
		{ 
			String path=null;
			try   
			{
				System.out.println("************Inside /Loans/LoanDefaultProcessMenu******************");
				LoanDocsUpdationForm docsupdation=(LoanDocsUpdationForm)form;
				LoansDelegate ldeligate=new LoansDelegate();
				
				Initialparam_loanActyp(req, ldeligate);
				LoanDocumList(req, ldeligate);
				
				System.out.println("-------sysdate------>"+docsupdation.getSysdate());
				if(docsupdation.getSysdate()!=null)
				{
					req.setAttribute("unverified_accounts",ldeligate.pendingTrayList("24",docsupdation.getSysdate()));
					System.out.println("form deligate value"+ldeligate.pendingTrayList("24",docsupdation.getSysdate()));
				}
				
				
				if(MenuNameReader.containsKeyScreen(docsupdation.getPageidentity().getPageId()))
				{ 
					path=MenuNameReader.getScreenProperties(docsupdation.getPageidentity().getPageId());
					System.out.println("path=======>"+path);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess()); 
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
				
				
			}  
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanDocsUpdation"))
		{
			String path="null";
			try
			{
				System.out.println("************Inside /Loans/LoanDefaultProcess******************");
				LoanDocsUpdationForm docsupdation=(LoanDocsUpdationForm)form;
				String button_value=docsupdation.getButton_value();
				System.out.println("button value==========>"+button_value); 
				LoansDelegate ldeligate=new LoansDelegate();
				Initialparam_loanActyp(req, ldeligate);
				LoanDocumList(req, ldeligate);
				System.out.println("***********loan ac num==="+docsupdation.getAc_no());
				
				/*if(docsupdation.getHelp()!=null)
				{
					req.setAttribute("unverified_accounts",ldeligate.pendingTrayList("24",docsupdation.getSysdate()));
					System.out.println("form deligate value"+ldeligate.pendingTrayList("24",docsupdation.getSysdate()));
					req.setAttribute("Helpcall",0);
					req.setAttribute("path",map.getPath());
					return map.findForward("help");
				}*/
				
				
				
				if(docsupdation.getAc_no()!=0)
				{
					System.out.println("*******Testing********"); 
					req.setAttribute("PledgeDoc",ldeligate.getLoanDocuments(docsupdation.getAc_type(),docsupdation.getAc_no()));
				
				}	
				
				if(button_value!=null)
				{
					if(button_value.equalsIgnoreCase("submit"))
					{
						System.out.println("**************Hi Inside Submit***************");
						System.out.println("<============code=============>"+docsupdation.getDoccode());
						
					}
				} 
				
				
				if(MenuNameReader.containsKeyScreen(docsupdation.getPageidentity().getPageId()))
				{ 
					path=MenuNameReader.getScreenProperties(docsupdation.getPageidentity().getPageId());
					System.out.println("path=======>"+path);
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
				e.printStackTrace();
			}
			
		}
		if (map.getPath().trim().equalsIgnoreCase("/Loans/ReverseRecoveryVerifyMenu")) {
			try {
				delegate = new LoansDelegate();
				ReverseRecoveryForm revrecoveryform = (ReverseRecoveryForm) form;
				revrecoveryform.setResult_update(0);
				revrecoveryform.setResult_submit(0);
				revrecoveryform.setResult_del(0);
				revrecoveryform.setAccountclosed("null");
				revrecoveryform.setAccountnotfound("null");
				req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
				req.setAttribute("panelName", "Personal");
				if (MenuNameReader.containsKeyScreen(revrecoveryform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(revrecoveryform.getPageidentity().getPageId());
					System.out.println("Path is=====" + path);
					RevRecoveryInitialParam(req, delegate, path);
					req.setAttribute("LoanTrnObj",null);
					revrecoveryform.setAccno(0);
					revrecoveryform.setIntamt(0.00);
					revrecoveryform.setPenalamt(0.00);
					revrecoveryform.setPrinamt(0.00);
					revrecoveryform.setVoucherno(0);
					revrecoveryform.setOthercharges(0.00);
					return map.findForward(ResultHelp.getSuccess());
				}
			
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (map.getPath().trim().equalsIgnoreCase("/Loans/ReverseRecoveryVerify")) {
			try {
				delegate = new LoansDelegate();
				ReverseRecoveryForm reverseRecoveryForm = (ReverseRecoveryForm) form;
				path = MenuNameReader.getScreenProperties(reverseRecoveryForm.getPageidentity().getPageId());
				System.out.println("Hi in /Loans/ReverseRecovery "+reverseRecoveryForm.getForward());
				RevRecoveryInitialParam(req, delegate, path);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
				req.setAttribute("panelName", "Personal");
				reverseRecoveryForm.setResult_update(0);
				reverseRecoveryForm.setResult_submit(0);
				reverseRecoveryForm.setResult_del(0);
				reverseRecoveryForm.setAccountclosed("null");
				reverseRecoveryForm.setAccountnotfound("null");
				if(reverseRecoveryForm.getCreditgl().equalsIgnoreCase("yes")){
					req.setAttribute("checkbox", "visible");
				}else{
					req.setAttribute("checkbox", null);
					reverseRecoveryForm.setCreditgltype("");
					reverseRecoveryForm.setCreditglcode("0");
				}
				
				
						
				if (reverseRecoveryForm.getVoucherno() != 0) {
					//
					req.setAttribute("panelName", "Personal");
					lntrn = delegate.getRevDetails(reverseRecoveryForm.getVoucherno());
					
					System.out.println("Loan Tran obj====>" + lntrn);
					if (lntrn != null) {
						req.setAttribute("LoanTrnObj", lntrn);
						reverseRecoveryForm.setAcctype(lntrn.getAccType());
						
						req.setAttribute("personalInfo", delegate.getCustomer(delegate.getCid(lntrn.getAccType(), lntrn.getAccNo())));
					   	} 
					else {
						//reverseRecoveryForm.setAccountclosed("Voucher Number NontFound");
						req.setAttribute("msg","Voucher Number NotFound");
					     }
				}
				
				
				
				if (reverseRecoveryForm.getAccno() != 0) {
					LoanMasterObject mast_obj = delegate.getLoanMaster(reverseRecoveryForm.getAccno(), reverseRecoveryForm.getAcctype());
					if (mast_obj == null) {
						reverseRecoveryForm.setAccountnotfound("ACOUNTNOTFOUND");
					}
					System.out.println("------Inside ac_num!=0 Condition-------");
					req.setAttribute("personalInfo", delegate.getCustomer(delegate.getCid(reverseRecoveryForm.getAcctype(), reverseRecoveryForm.getAccno())));
					req.setAttribute("panelName", "Personal");
				}
				String buttonvalue = reverseRecoveryForm.getForward();
				System.out.println("buttonvalue====" + buttonvalue);
				if (reverseRecoveryForm.getForward() != null) {
					if(reverseRecoveryForm.getForward().equalsIgnoreCase("verify"))
					{
					   System.out.println("i am in Verify");	
					   	try {
					   		 if(lntrn.uv.getVerId()==null && lntrn.uv.getVerDate()==null)
					   		 {
								lntrn.setTranType("R");
								lntrn.setTranMode("T");
								lntrn.setCdind("D");
								lntrn.setTransactionDate(delegate.getSysDate());
								lntrn.setPrincipalPayable(reverseRecoveryForm.getPrinamt());
								lntrn.setInterestPayable(reverseRecoveryForm.getIntamt());
								lntrn.setPenaltyAmount(reverseRecoveryForm.getPenalamt());
								lntrn.setOtherAmount(reverseRecoveryForm.getOthercharges());
								lntrn.uv.setVerDate(delegate.getSysDate());
								lntrn.uv.setVerId("1044");
								lntrn.uv.setVerTml("CA99");
								String to_ac_type=null;
								String to_ac_type1=null;
								String to_ac_type2=null;
								to_ac_type = lntrn.getTranNarration().trim();
								String ac_no = to_ac_type.substring(to_ac_type.indexOf(' '), to_ac_type.length()).trim();
								to_ac_type1 = to_ac_type.substring(0,4).trim();
								to_ac_type2=to_ac_type.substring(0, 7);
								Object return_value = null;
								if(to_ac_type1.startsWith("1012")) {
									return_value = delegate.reverserecovery(lntrn, 1, null, Integer.parseInt(ac_no), to_ac_type);
								} else {
									AccountTransObject acc_trn = new AccountTransObject();
									acc_trn.setAccType(to_ac_type2);
									acc_trn.setAccNo(Integer.parseInt(ac_no));
									acc_trn.setTransDate(delegate.getSysDate());
									acc_trn.setTransType("R");
									acc_trn.setTransAmount(lntrn.getTransactionAmount());
									acc_trn.setTransMode("T");
									acc_trn.setCdInd("C");
									acc_trn.setTransNarr(lntrn.getAccType()+"  "+lntrn.getAccNo());
									acc_trn.uv.setUserTml(lntrn.uv.getUserTml());
									acc_trn.uv.setUserId(lntrn.uv.getUserId());
									acc_trn.uv.setUserDate(lntrn.uv.getUserDate());
									acc_trn.uv.setVerTml(lntrn.uv.getVerTml());
									acc_trn.uv.setVerId(lntrn.uv.getVerId());
									acc_trn.uv.setVerDate(lntrn.uv.getVerDate());
									return_value = delegate.reverserecovery(lntrn, 1, acc_trn, 0, null);
								}
								if(return_value != null) 
								{
									req.setAttribute("msg","Voucher Number " + reverseRecoveryForm.getVoucherno() + " Sucessfully Verified");
								}
							}else{
								req.setAttribute("msg","Voucher Number Already Verified");
							}
					}
					   	catch (Exception exe) {
								exe.printStackTrace();
							}
					}
					else if (reverseRecoveryForm.getForward().equalsIgnoreCase("Clear")) {
						reverseRecoveryForm.setVoucherno(0);
						reverseRecoveryForm.setAcctype("Select");
						reverseRecoveryForm.setAccno(0);
						reverseRecoveryForm.setOthercharges(0.0);
						reverseRecoveryForm.setPenalamt(0.0);
						reverseRecoveryForm.setIntamt(0.0);
						reverseRecoveryForm.setPrinamt(0.0);
					}// Clear Button
				} // if any button
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		 if(map.getPath().equalsIgnoreCase("/Loans/DisbursementMenu"))
			{
			try 
			{
				DisbursementForm disform = (DisbursementForm) form;
				LoansDelegate delegate = new LoansDelegate();
				System.out.println("PAGE I~D ==== >"+ disform.getPageidentity().getPageId());
				if(MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
				{
					try
					{
						String	path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
						req.setAttribute("pageId", path);
						IntialParameters(req, delegate);
						req.setAttribute("pay_mode", delegate.getPayMode());
						req.setAttribute("pay_actype", delegate.getpayAcctype());
						disform.setAccno(0);
						disform.setShno(0);
						disform.setAmount(0.0);
						disform.setInstallment(0.0);
						disform.setIntrate(0.0);
						disform.setPenalrate(0.0);
						disform.setDisbleft(0.0);
						disform.setCombo_pay_mode("C");
						disform.setPayaccno(0);
						disform.setDisbamt(0.0);
						System.out.println("<=========COMING BACK TO ACTION CLASS =======>");
						return map.findForward(ResultHelp.getSuccess());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return map.findForward(ResultHelp.getError());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 if(map.getPath().equalsIgnoreCase("/Loans/Disbursement"))
			{
				try
				{
					DisbursementForm disform = (DisbursementForm) form;
					LoansDelegate delegate = new LoansDelegate();
					LoanTransactionObject tranobj = null;
					req.setAttribute("msg"," ");
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					System.out.println("PageId Loan Disbursement"+ disform.getPageidentity().getPageId());
					System.out.println("<=========COMING BACK TO ACTION CLASS DISBURSEMENT =======>");
					lmobj=delegate.getLoanMaster(disform.getAccno(), disform.getAcctype());
					if(lmobj!=null)
					{
						System.out.println("inside loan master action ");
						if(disform.getAccno()!=0)
						{
							
						tranobj=delegate.getUnVerifiedDisbursement(disform.getAcctype(), disform.getAccno());
					
						if(tranobj==null)
						{
							System.out.println("inside loan Tran=====>"+lmobj.getShareAccNo());
							disform.setShno(lmobj.getShareAccNo());
							disform.setInstallment(lmobj.getInstallmentAmt());
							disform.setIntrate(lmobj.getInterestRate());
							disform.setPenalrate(lmobj.getPenalRate());//disform.setDisbleft(lmobj.getDisbursementLeft());
							disform.setCombo_pay_mode(lmobj.getPayMode());
							disform.setPriority(String.valueOf(lmobj.getPrior()));
							
							req.setAttribute("priority_code", String.valueOf(lmobj.getPrior()));
						
							System.out.println("Priority sector"+String.valueOf(lmobj.getPrior()));
							if(lmobj.isLoanSanctioned())
							{					
								disform.setAmount(lmobj.getSanctionedAmount());
								disform.setPeriod(lmobj.getNoOfInstallments());
								disform.setHoliday(lmobj.getHolidayPeriod());
							
								try
								{
									double disb_left=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo()); // Code added by Murugesh on 30/12/2005
									disform.setDisbleft(disb_left);
								}
								catch(Exception exce)
								{exce.printStackTrace();}
							}
							if(lmobj.isLoanSanctioned() &&  lmobj.isSanctionVerified())
							{
								if(lmobj.getPayMode().equalsIgnoreCase("T")) {
									disform.setPayactype(lmobj.getPaymentAcctype());
									disform.setPayaccno(lmobj.getPaymentAccno());
								}
								disform.setPaymtmode(lmobj.getPayMode());
								if(lmobj.getDisbursementLeft()==0)
								{
									req.setAttribute("msg","Amount sanctioned is already disbursed");
								}
							}	
						}
						else
						{
							req.setAttribute("msg","Disbursement Is Not Yet Verified");
						}
					}
					if(disform.getForward().equalsIgnoreCase("Disburse"))
					{
						double disbursement=(disform.getDisbamt());
						   try
						   {
						   	double disb_left_amt=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo()); // Code added by Murugesh on 30/12/2005
						   	System.out.println("--disb_left_amt->"+disb_left_amt);
						   	System.out.println("--lmobj.getModuleCode()---->"+lmobj.getModuleCode());
						   	if(disb_left_amt!=-1 && disbursement>disb_left_amt )
							{
						   		req.setAttribute("msg","Disbursing amount is higher**** than the Disbursement left");
						   		disform.setDisbamt(0.0);
							}
						   	else
						   	{
						   		System.out.println("Inside Else");
								data=null;
									if(lmobj.getInterestType()==1)
										data=calculateReducingInterest(lmobj.getSanctionedAmount(), lmobj.getInterestRate(), lmobj.getNoOfInstallments(),lmobj);
									else if(lmobj.getInterestType()==0)
										data=calculateEMIInterest(lmobj.getSanctionedAmount(), lmobj.getInterestRate(), lmobj.getNoOfInstallments(),lmobj);
									System.out.println("*************data***************" + data.length);
									if(disform.getDisbamt()!=0)
									{
										double disb_left=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo());
										if(disb_left!=-1 && disform.getDisbamt()>disb_left)
										{
											req.setAttribute("msg", "Disbursing amount is higher than the Disbursement left");
											disform.setDisbamt(0.0);
										}
										else
										{
											System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
											Object values[][]=null;
											Object values1[][]=null;
											Object disb_values[][]=null;
											String[] dates;
											count_disburse=0;
											double amount_remaining=0,amtsanc;
											double amount_sanctioned=0;
											int installments=0;
											values = delegate.getPreviousDisbursement(lmobj.getAccType(),lmobj.getAccNo(),flag);
											count_disburse =  delegate.getPreviousDisbDet(lmobj.getAccType(),lmobj.getAccNo(),delegate.getSysDate());
											if(values==null || delegate.getNumPreviousDisb(lmobj.getAccType(),lmobj.getAccNo(),1)==0)
											{
												if(disform.getPeriod()!=0)
													installments=disform.getPeriod();
												else
													req.setAttribute("msg","Enter the number of installments");
												amtsanc=disform.getDisbamt();
												installments = (installments - disform.getHoliday());
												if(installments<0)
												{
													installments=-installments;
												}
												System.out.println("amount"+installments+"  "+amtsanc+" "+lmobj.getInterestRate());
												data=null;
												if(lmobj.getInterestType()==1)
													data=calculateReducingInterest(amtsanc,lmobj.getInterestRate(),installments,lmobj);		
												else if(lmobj.getInterestType()==0)
													data=calculateEMIInterest(amtsanc,lmobj.getInterestRate(),installments,lmobj);	
											}
											else
											{
												values1 = new Object[values.length+1][values[0].length];
												dates= new String[values.length-count_disburse+1];
												for(int i=0;i<values.length+1;i++)
													for(int j=0;j<values[0].length;j++)
													{
														if(i<count_disburse)
														{
															values1[i][j]=values[i][j];
														}
														else
															if(i>count_disburse)
																values1[i][j]=values[i-1][j];
															else
																if(i==count_disburse)
																	values1[i][j]="   ";
													}
												for(int i=0;i<dates.length-1;i++)
												{
													if(count_disburse==0)
													{
														if(values[i+count_disburse][1]!=null)
															dates[i]=values[i+count_disburse][1].toString();
													}
													else
														dates[i]=values[i+count_disburse-1][1].toString();
													System.out.println("The dates are:"+dates[i]);
												}
												if(count_disburse==0)
													amount_remaining = Double.parseDouble(values[count_disburse][5].toString())+Double.parseDouble(values[count_disburse][2].toString());
												else
													amount_remaining = Double.parseDouble(values[count_disburse-1][5].toString());
												amount_sanctioned=disform.getDisbamt()+amount_remaining;
												installments=dates.length-2;
												String a,b;
												a=values[count_disburse][3].toString();
												b=values[count_disburse][4].toString();
									System.out.println("the sanctioned amount:"+amount_sanctioned);
									if(lmobj.getInterestType()==1)
										disb_values=newCalculateReducingInterest(amount_sanctioned,lmobj.getInterestRate(),installments,dates);
									if(lmobj.getInterestType()==0)
										disb_values=newCalculateEMIInterest(amount_sanctioned,lmobj.getInterestRate(),installments,dates);
										for(int i=0;i<disb_values.length-1;i++)
										{
											for(int j=1;j<values[0].length;j++)
												values1[i+count_disburse+1][j]=disb_values[i][j];
										}
									for(int i=0;i<disb_values.length-1;i++)
									{
										for(int j=1;j<values[0].length;j++)
											values[i+count_disburse][j]=disb_values[i][j];
									}
									{
										values[count_disburse][3]=a;
										double interest=0;
										if(count_disburse==0)
											 interest=Math.round(disform.getDisbamt()*lmobj.getInterestRate()*(Validations.dayCompare(delegate.getSysDate(),dates[0]))/36500.00);
										else
											 interest=Math.round(disform.getDisbamt()*lmobj.getInterestRate()*(Validations.dayCompare(delegate.getSysDate(),dates[1]))/36500.00);
										System.out.println("the value of the interest is:"+interest);
										System.out.println("the value of the interest is:"+String.valueOf(Double.parseDouble(values[count_disburse][3].toString())+ interest));
										values[count_disburse][3]=String.valueOf(Double.parseDouble(values[count_disburse][3].toString())+ interest);
										values[count_disburse][4]=String.valueOf(Double.parseDouble(values[count_disburse][2].toString())+ Double.parseDouble(values[count_disburse][3].toString()));
									}
									System.out.println("The remaining amount is:"+amount_remaining);
									System.out.println("the no of rows is: "+count_disburse);
									System.out.println("the length of object is:"+values.length);
									System.out.println("the length of object coplumn is:"+values[0].length);
									data=values; // Setting the global data object in order to pass it to the bean in the action performed
						   	         }
						          }
							    }
									req.setAttribute("ScheduleData", data);
							 }
						   }
						   	catch(Exception e1)
							{
							   	e1.printStackTrace();
							}
					    }
						System.out.println("************After new schedule*****************");
						if(disform.getForward().equalsIgnoreCase("Submit"))
						{
							double disbursement=disform.getDisbamt();
							double amtsanct=disform.getAmount();
							if(disform.getDisbamt()==0.0)
								req.setAttribute("msg","disbursement  amount is not given");
							else if(disform.getDisbamt()>disform.getDisbleft())
								req.setAttribute("msg","Disbursement amount is greater than amount sanctioned");	
							else
							{
							 	System.out.println("Inside submit-=-");
									String narr="";
									String trnmode="";
									if(disform.getCombo_pay_mode().equalsIgnoreCase("C"))
									{
										System.out.println("Inside Cash");
											trnmode="C";
											narr="  CASH";
									}
									else if(disform.getCombo_pay_mode().equalsIgnoreCase("T"))
									{
										System.out.println("Inside Transfer");
											trnmode="T";
											narr=disform.getPayactype()+" "+disform.getPayaccno(); // Code added by Murugesh on 29/12/2005
									}
									else if(disform.getCombo_pay_mode().equalsIgnoreCase("P"))
									{
										System.out.println("Inside Payorder");
										trnmode="P";
										narr="  PayOrder";
									}
									System.out.println("Narattttttion"+trnmode+"  "+narr);
									int result=delegate.disburseLoan(Integer.parseInt(disform.getAcctype()), disform.getAccno(),disform.getDisbamt(),data,disform.getAmount(),"LN", "LN01", trnmode,narr,delegate.getSysDate(), delegate.getSysDateTime());
									if(result!=0)
									{
										req.setAttribute("msg", "Loan is Disbursed Successfully");
										disform.setAccno(0);
										disform.setShno(0);
										disform.setAmount(0.0);
										disform.setInstallment(0.0);
										disform.setIntrate(0.0);
										disform.setPenalrate(0.0);
										disform.setDisbleft(0.0);
										disform.setCombo_pay_mode("C");
										disform.setPayaccno(0);
										disform.setDisbamt(0.0);
									}
									else
										req.setAttribute("msg", "Unable To Disburse");
							}
						}
						else if(disform.getForward().equalsIgnoreCase("Delete"))
						{
							if(lmobj.isLoanDisbursed())
							{
								int i=delegate.deleteLoanDisbursement(disform.getAcctype(),disform.getAccno());
								if(i==1)
								{
									req.setAttribute("msg","Loan Account Disbursement is successfully Deleted");
								}
								else
								{
									req.setAttribute("msg"," Deletion Failed");
								}
							}
							else
							{
								req.setAttribute("msg","Loan Is Not Disbursed For This Account");
							}
						}
					System.out.println("disform.getDetails() ==========2" + disform.getDetails());
					if ((disform.getDetails().equalsIgnoreCase("5002"))) {
						System.out.println("+++++++++++++++Action class application form code ++++++++++++");
						System.out.println("appform.getDetails() ==========  "+ disform.getDetails());
						System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						req.setAttribute("Application", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						req.setAttribute("pay_mode", delegate.getPayMode());
						req.setAttribute("pay_actype", delegate.getpayAcctype());
						String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
						String[] dirrelations=delegate.getDirectorRelations();
						req.setAttribute("Dirrelations",dirrelations);
						System.out.println("***********Director details**************" + dirdetails.toString());
						System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
						req.setAttribute("DirDetails",dirdetails);
						req.setAttribute("flag", null);
						if(lmobj!=null){
							req.setAttribute("LoanDetails",lmobj);
						}
					}
					if ((disform.getDetails().equalsIgnoreCase("5003"))) {
						System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
						req.setAttribute("flag", null);
						req.setAttribute("Vehicle", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getVehicleDet()!=null)
								req.setAttribute("VECHOBJ",lmobj.getVehicleDet());
							else 
								req.setAttribute("msg","Vehicle Details Not Found");
						}
					}
					if ((disform.getDetails().equalsIgnoreCase("Scheduling"))) {
						System.out.println("+++++++++++++++Action class Schedule form code ++++++++++++");
						System.out.println("disform.getDetails() ==========2" + disform.getDetails());
						req.setAttribute("flag", null);
						req.setAttribute("Schedule", MenuNameReader.getScreenProperties("5050"));
					}
					if ((disform.getDetails().equalsIgnoreCase("5004"))) {
						req.setAttribute("flag", null);
						System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
						req.setAttribute("Property", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getPropertyDetails()!=null)	
								req.setAttribute("PROPOBJ",lmobj.getPropertyDetails());
							else
								req.setAttribute("msg","Property Details Not Found");
							}
					}
					if(disform.getDetails().trim().equalsIgnoreCase("5093")) 
					{
						System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
						System.out.println("appform.getDetails() ==========5");
						req.setAttribute("flag", null);
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
						req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getRelatives()!=null)
								req.setAttribute("msg",lmobj.getRelatives());
							else
								req.setAttribute("msg","Relative Details Not Found");
						}
					}
				  if((disform.getDetails().trim().equalsIgnoreCase("Surities"))) 
					  {
						  System.out.println("*****************Inside to Suruties***************");
						  req.setAttribute("flag", null);
						  req.setAttribute("Surities",MenuNameReader.getScreenProperties("5032"));
						  if(lmobj!=null){
								if(lmobj.getSurities()!=null)
									req.setAttribute("msg",lmobj.getSurities());
								else
									req.setAttribute("msg","Surity Details Not Found");
							}
					  }
					if((disform.getDetails().trim().equalsIgnoreCase("Signature Ins"))) 
					{
						System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
						signObject = delegate.getSignatureDetails(disform.getShno(), disform.getShtype());
						if(signObject == null) 
						{
							System.out.println("*************Signature Object is null**********");	
							req.setAttribute("msg","Signature Details Not Found");
						}
						else
						{
							req.setAttribute("flag",MenuNameReader.getScreenProperties("0044"));
							req.setAttribute("signInfo",signObject);
							req.setAttribute("panelName",CommonPanelHeading.getSignature());
						}
					}
					
					if((disform.getDetails().equalsIgnoreCase("Loan and Share Details"))) {
						System.out.println("appform.getDetails() ==========8");
						req.setAttribute("flag", null);
						System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(disform.getShtype(),disform.getShno()));
						req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
						req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
						req.setAttribute("IndividualCoBorrower", null);
					}
					if((disform.getDetails().trim().equalsIgnoreCase("CoBorrower"))) {
						System.out.println("appform.getDetails() ==========Co Borrower");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5032"));
						req.setAttribute("flag", null);
						req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5032"));
					}
	                  
					if ((disform.getDetails().trim().equalsIgnoreCase("Gold"))) {
						System.out.println("appform.getDetails() ==========Gold");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("flag", null);
						req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("IndividualCoBorrower", null);
						
						if(lmobj!=null){
							if(lmobj.getGoldDet()!=null)
							{
								req.setAttribute("GoldDet",lmobj.getGoldDet());
							}
							else
								req.setAttribute("msg","Gold Details Not Found");
						}
					  }	 
					
				}
				else
				{
					req.setAttribute("msg","Loan Account Not Found");
					disform.setAccno(0);
					disform.setShno(0);
					disform.setAmount(0.0);
					disform.setInstallment(0.0);
					disform.setIntrate(0.0);
					disform.setPenalrate(0.0);
					disform.setDisbleft(0.0);
					disform.setCombo_pay_mode("C");
					disform.setPayaccno(0);
					disform.setDisbamt(0.0);
					
				}		
						
					if(MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
					{
						path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
						IntialParameters(req, delegate);
						req.setAttribute("pageId", path);
						String modcode = disform.getAcctype();
						System.out.println("modcode is ============" + modcode);
						items_relavent = delegate.getReleventDetails_str(modcode);
						int count = 0;
						if(items_relavent!= null) {
							for(int i=0;i<items_relavent.length;i++) 
							{
								if(items_relavent[i].equals("Y"))
									count++;
								if(items_relavent[i].equals("O"))
									count++;
							}
						}
						Vector vec_final[] = delegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
						return map.findForward(ResultHelp.getSuccess());
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		if(map.getPath().equalsIgnoreCase("/Loans/LoanLeadgerMenu"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanLeadgerMenu ****************");
				 LoanLeadgerForm loanledger=(LoanLeadgerForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 loanledger.setSysdate(LoansDelegate.getSysDate());
				 String path=null;
				 System.out.println("page id==>"+loanledger.getPageidentity().getPageId());
				 Initialparam_loanActyp1(req, ldeligate);
				 if(MenuNameReader.containsKeyScreen(loanledger.getPageidentity().getPageId()))
					{ 
						path=MenuNameReader.getScreenProperties(loanledger.getPageidentity().getPageId());
						System.out.println("path=======>"+path);
						req.setAttribute("pageId",path);
						return map.findForward(ResultHelp.getSuccess()); 
					}
					else
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Path doest exit",req,path);
						return map.findForward(ResultHelp.getError());
					}
				 
				 
			 }catch (Exception e) {
				 e.printStackTrace();
				
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/LoanLeadger"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanLeadger ****************");
				 LoanLeadgerForm loanledger=(LoanLeadgerForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 loanledger.setSysdate(LoansDelegate.getSysDate());
				 String path=null;
				 System.out.println("page id==>"+loanledger.getPageidentity().getPageId());
				 Initialparam_loanActyp1(req, ldeligate);
				 if(loanledger.getCombo_loancat()!=null)
				 {
					 ModuleObject cat[]= ldeligate.getMainModules(2,"'1010000','1008000'");
					 req.setAttribute("CatDesc",cat[Integer.valueOf(loanledger.getCombo_loancat())].getModuleDesc());
					 System.out.println("After category Description");
				
				 if(loanledger.getFile()!=null)
				 {
					 if(loanledger.getFile().equalsIgnoreCase("File"))
					 {
						 System.out.println("**************File*****************");
						 System.out.println("In the view of Loan Ledger ACtion2");
						 if(loanledger.getCombo_accounts().equalsIgnoreCase("OpenAccounts")||loanledger.getCombo_accounts().equalsIgnoreCase("ClosedAccounts")|| loanledger.getCombo_accounts().equalsIgnoreCase("AllAccounts"))
						 { 
							// public LedgerObject[] getLedger(int fromac,int toac,int acstatus,String fdate,String tdate){
							 if(loanledger.getCombo_accounts().equalsIgnoreCase("OpenAccounts"))
							 {
							 ledger_obj=ldeligate.getLedger(Integer.parseInt(loanledger.getTxt_startaccnum()),Integer.parseInt(loanledger.getTxt_endingaccnum()),Integer.parseInt("0"),"null","null");
							 System.out.println("Ledger object" + ledger_obj.length);
							 }
							 else if(loanledger.getCombo_accounts().equalsIgnoreCase("ClosedAccounts")){
								 ledger_obj=ldeligate.getLedger(0,0,Integer.parseInt("1"),loanledger.getTxt_startdate(),loanledger.getTxt_enddate());
								 System.out.println("Ledger object" + ledger_obj.length);
							 }
							 
							 else if(loanledger.getCombo_accounts().equalsIgnoreCase("AllAccounts")){
								 ledger_obj=ldeligate.getLedger(Integer.parseInt(loanledger.getTxt_startaccnum()),Integer.parseInt(loanledger.getTxt_endingaccnum()),Integer.parseInt("3"),loanledger.getTxt_startdate(),loanledger.getTxt_enddate());
								 System.out.println("Ledger object" + ledger_obj.length);
							 }
								if(ledger_obj!=null)
								{
									try{
										
																
										String name="Loan Ledger";
										HSSFWorkbook wb = new HSSFWorkbook();
										HSSFSheet sheet = wb.createSheet("Loan Ledger");
										HSSFRow row = sheet.createRow((short)0);
										/*HSSFCell cell = row.createCell((short)0);
			                       		   cell.setCellValue(1);*/
										row.createCell((short)1).setCellValue("Srl No");
										row.createCell((short)2).setCellValue("Acc No");
										row.createCell((short)3).setCellValue("Name");
										row.createCell((short)4).setCellValue("Sex");
										row.createCell((short)5).setCellValue("SC/ST");
										row.createCell((short)6).setCellValue("ShareNo");
										row.createCell((short)7).setCellValue("Appn No");
										row.createCell((short)8).setCellValue("Purpose");
										row.createCell((short)9).setCellValue("Requested Amount");
										row.createCell((short)10).setCellValue("Sanctioned Amt");
										row.createCell((short)11).setCellValue("Sanctioned Date");
										row.createCell((short)12).setCellValue("Installment");
										row.createCell((short)13).setCellValue("Period");
										row.createCell((short)14).setCellValue("Holiday");
										row.createCell((short)15).setCellValue("PaymentMode");
										
										
										for(int i=0;i<ledger_obj.length;i++){
											System.out.println("how many times=====> "+i);
											HSSFRow row1 = sheet.createRow((short)i+1);
											
											row1.createCell((short)1).setCellValue(i);
											row1.createCell((short)2).setCellValue(ledger_obj[i].lm.getAccNo());
											row1.createCell((short)3).setCellValue(ledger_obj[i].lm.getName());
											row1.createCell((short)4).setCellValue(ledger_obj[i].lm.getSexInd());
											row1.createCell((short)5).setCellValue(ledger_obj[i].lm.getScstInd());
											row1.createCell((short)6).setCellValue(ledger_obj[i].lm.getShareAccNo());
											row1.createCell((short)7).setCellValue(ledger_obj[i].lm.getApplicationSrlNo());
											row1.createCell((short)8).setCellValue(ledger_obj[i].lm.getPurposeCode());
											row1.createCell((short)9).setCellValue(ledger_obj[i].lm.getRequiredAmount());
											row1.createCell((short)10).setCellValue(ledger_obj[i].lm.getSanctionedAmount());
											row1.createCell((short)11).setCellValue(ledger_obj[i].lm.getSanctionDate());
											row1.createCell((short)12).setCellValue(ledger_obj[i].lm.getInstallmentAmt());
											row1.createCell((short)13).setCellValue(ledger_obj[i].lm.getNoOfInstallments());
											row1.createCell((short)14).setCellValue(ledger_obj[i].lm.getHolidayPeriod());
											row1.createCell((short)15).setCellValue(ledger_obj[i].lm.getPayMode());
											
											
											
											
										}								   
										FileOutputStream fileOut = new FileOutputStream("c:\\"+name+".xls");
										wb.write(fileOut);
										fileOut.close(); 
									}
									catch( Exception ex )
									{  
										ex.printStackTrace();
									}  
									req.setAttribute("msg","SuccessFullyCreated In C-Drive with File Name-Loan Ledger-File");
								}
							 req.setAttribute("LedgerObject",ledger_obj);
						
						 }	 
					 
					 }
				 }
				 }
				 if(MenuNameReader.containsKeyScreen(loanledger.getPageidentity().getPageId()))
					{ 
						path=MenuNameReader.getScreenProperties(loanledger.getPageidentity().getPageId());
						System.out.println("path=======>"+path);
						req.setAttribute("pageId",path);
						return map.findForward(ResultHelp.getSuccess()); 
					}
				
					else
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Path doest exit",req,path);
						return map.findForward(ResultHelp.getError());
					}
				 
				 
			 }catch (Exception e) {
				 e.printStackTrace();
				
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanMeetingAgendaMenu"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanMeetingAgendamenu ****************");
				 LoanMeethingAgendaForm loanmeeting=(LoanMeethingAgendaForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 String path=null;
				 System.out.println("page id==>"+loanmeeting.getPageidentity().getPageId());
				
				 //System.out.println("!!!!!!!!!!!!!!!!!"+loanmeeting.getTxt_Loancat());
				 if(MenuNameReader.containsKeyScreen(loanmeeting.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(loanmeeting.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 Initialparam_loanActyp(req, ldeligate);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 }catch (Exception e) {
				 e.printStackTrace();
				// TODO: handle exception
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanMeetingAgenda"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanMeetingAgenda ****************");
				 LoanMeethingAgendaForm loanmeeting=(LoanMeethingAgendaForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 String path=null;
				 LoanReportObject reportobj[]=null;
				 System.out.println("page id==>"+loanmeeting.getPageidentity().getPageId());
				 Initialparam_loanActyp(req, ldeligate);
				 System.out.println("sweeeeeeeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!"+loanmeeting.getTxt_Loancat());
				 String button_val=loanmeeting.getButton_value();
				 System.out.println("button value===>"+button_val); 
				 if(button_val!=null && button_val.equalsIgnoreCase("View"))
				 {	 
					 if(loanmeeting.getTxt_loanaccno()!=0)
				 	 {
						 
						 reportobj=ldeligate.getLoanDetails(loanmeeting.getTxt_Loancat(),loanmeeting.getTxt_loanaccno(),"","",1,null);
						 req.setAttribute("rep_obj",reportobj);
						 System.out.println("rep obj==========<>"+ldeligate.getLoanDetails(loanmeeting.getTxt_Loancat(),Integer.valueOf(loanmeeting.getTxt_loanaccno()),"","",1,null));
					 
				 	 }
				 }
				 else if(button_val!=null && button_val.equalsIgnoreCase("File"))
				 {
					 System.out.println("i am inside File");
					 if(loanmeeting.getTxt_loanaccno()>0){
					 reportobj=ldeligate.getLoanDetails(loanmeeting.getTxt_Loancat(),loanmeeting.getTxt_loanaccno(),"","",1,null);
					 }
						if(reportobj!=null)
						{
							try{
								
														
								String name="Loan Meeting Agenda";
								HSSFWorkbook wb = new HSSFWorkbook();
								HSSFSheet sheet = wb.createSheet("Loan Metting Agenda");
								HSSFRow row = sheet.createRow((short)0);
								/*HSSFCell cell = row.createCell((short)0);
	                       		   cell.setCellValue(1);*/
								row.createCell((short)1).setCellValue("Srl No");
								row.createCell((short)2).setCellValue("Me No");
								row.createCell((short)3).setCellValue("Name and Address");
								row.createCell((short)4).setCellValue("Purpose");
								row.createCell((short)5).setCellValue("Appn No and Date");
								row.createCell((short)6).setCellValue("Requested Amount");
								row.createCell((short)7).setCellValue("Surity Details");
								row.createCell((short)8).setCellValue("Sex");
								row.createCell((short)9).setCellValue("SC/ST");
								row.createCell((short)10).setCellValue("WKR SEC");
								for(int i=0;i<reportobj.length;i++){
									System.out.println("how many times=====> "+i);
									HSSFRow row1 = sheet.createRow((short)i+1);
									
									row1.createCell((short)1).setCellValue(i);
									row1.createCell((short)2).setCellValue(i);
									row1.createCell((short)3).setCellValue(reportobj[i].getName());
									row1.createCell((short)4).setCellValue(reportobj[i].getLoanPurpose());
									row1.createCell((short)5).setCellValue(reportobj[i].getApplicationDate());
									row1.createCell((short)6).setCellValue(reportobj[i].getRequiredAmount());
									row1.createCell((short)7).setCellValue(reportobj[i].getSurityDetails());
									row1.createCell((short)8).setCellValue(reportobj[i].getSexInd());
									row1.createCell((short)9).setCellValue(reportobj[i].getScstInd());
									row1.createCell((short)10).setCellValue(reportobj[i].getWeakerSection());
								}								   
								FileOutputStream fileOut = new FileOutputStream("c:\\"+name+".xls");
								wb.write(fileOut);
								fileOut.close(); 
							}
							catch( Exception ex )
							{  
								ex.printStackTrace();
							}  
							req.setAttribute("msg","SuccessFullyCreated In C-Drive with File Name-Loan Metting Agenda-File");
						}else{
							req.setAttribute("msg", "Account Number Not Found!");
							
							
						}
					 
					 
				 }
				 if(MenuNameReader.containsKeyScreen(loanmeeting.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(loanmeeting.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 }catch (Exception e) {
				 e.printStackTrace();
				// TODO: handle exception
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanSplIntRateUpdnMenu"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanMeetingAgenda Menu****************");
				 LoanSplIntRateUpdnForm SplIntRateUpdnForm=(LoanSplIntRateUpdnForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 SplIntRateUpdnForm.setLoan_acc_notfound("null");
				 SplIntRateUpdnForm.setSplintrestset("null");
				SplIntRateUpdnForm.setTxt_loanaccno(0);
				 String path=null;
				 Initialparam_loanActyp(req, ldeligate);
				 if(MenuNameReader.containsKeyScreen(SplIntRateUpdnForm.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(SplIntRateUpdnForm.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 }catch (Exception e) {
				 e.printStackTrace();
				
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanSplIntRateUpdn"))
		{ 
			 try{
				 System.out.println("*************Inside /Loans/LoanSplIntRateUpdn ****************");
				 LoanSplIntRateUpdnForm SplIntRateUpdnForm=(LoanSplIntRateUpdnForm)form;
				 LoansDelegate ldeligate=new LoansDelegate();
				 String path=null;
				 System.out.println("page id==>"+SplIntRateUpdnForm.getPageidentity().getPageId());
				 Initialparam_loanActyp(req, ldeligate);
				 String button_value=SplIntRateUpdnForm.getButton_value();
				 System.out.println("button value=============>"+button_value);
				 SplIntRateUpdnForm.setLoan_acc_notfound("null");
				 SplIntRateUpdnForm.setSplintrestset("null");
				 if(SplIntRateUpdnForm.getTxt_loanaccno()!=0)
				 {
				 System.out.println("**************Inside loan acc num not null*************");
				 LoanReportObject loan_rep_obj[]=ldeligate.getLoanDetails(SplIntRateUpdnForm.getTxt_loancat(),SplIntRateUpdnForm.getTxt_loanaccno(),"","",2,null);
				 if(loan_rep_obj!=null)
				 {	 LoanReportObject rep_obj=loan_rep_obj[0];
				 	 SplIntRateUpdnForm.setTxt_laststage(String.valueOf(rep_obj.getRemainderNo()));
					 SplIntRateUpdnForm.setTxt_Desc(rep_obj.getRemainderDesc());
				 }
				 else
				 {
					 SplIntRateUpdnForm.setLoan_acc_notfound("RecordsNotFound");
					// SplIntRateUpdnForm.setTxt_loanaccno(0);
					 SplIntRateUpdnForm.setTxt_laststage("");
					 SplIntRateUpdnForm.setTxt_Desc("");
					 SplIntRateUpdnForm.setTxt_fromdate("");
					 SplIntRateUpdnForm.setTxt_specialintrate(0.0);
				 }
				 } //close of if
				 else
				 {
					 SplIntRateUpdnForm.setTxt_loanaccno(0);
					 SplIntRateUpdnForm.setTxt_laststage("");
					 SplIntRateUpdnForm.setTxt_Desc("");
					 SplIntRateUpdnForm.setTxt_fromdate("");
					 SplIntRateUpdnForm.setTxt_specialintrate(0.0);
				 }
				 if(button_value!=null)
				 {
					 if(button_value.equalsIgnoreCase("submit"))
					 {
						 	String array_string_data[]=new String[7];
						 	array_string_data[0]=SplIntRateUpdnForm.getTxt_loancat();
							array_string_data[1]=String.valueOf(SplIntRateUpdnForm.getTxt_loanaccno());
							array_string_data[2]=SplIntRateUpdnForm.getTxt_fromdate();
							array_string_data[3]="";
							array_string_data[4]=String.valueOf(SplIntRateUpdnForm.getTxt_specialintrate());
							array_string_data[5]="lntml";
							array_string_data[6]="lnuser";
							
							int store_value=ldeligate.storeSpecialInt(array_string_data);
							System.out.println("Store value=============>"+store_value);
							if(store_value==1)
							{ 
								SplIntRateUpdnForm.setSplintrestset("IntRateSet");
							}
							else if(store_value==0)
							{
								SplIntRateUpdnForm.setSplintrestset("IntRateNotSet");
							}
							else if(store_value==2)
							{
								SplIntRateUpdnForm.setSplintrestset("NotEligibleforSpecialInterest");
							}
					 }
				 }
				 if(MenuNameReader.containsKeyScreen(SplIntRateUpdnForm.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(SplIntRateUpdnForm.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 }catch (Exception e) {
				 e.printStackTrace();
				
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/PSWSProcessingMenu"))
		{ 
			PSWSProcessingForm pswsform=(PSWSProcessingForm)form;
			String path;
			pswsform.setPswsprocessing(null);
			pswsform.setSysdate(LoansDelegate.getSysDate());
			if(MenuNameReader.containsKeyScreen(pswsform.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(pswsform.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
			
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/PSWSProcessing"))
		{
		try{	
			PSWSObject pawsobject=null;
			PSWSObject array_pswsobject[]=null;
			PSWSProcessingForm pswsform=(PSWSProcessingForm)form;
			String path;
			String button_value=pswsform.getButton_val();
			LoansDelegate deligate=new LoansDelegate();
			pswsform.setSysdate(LoansDelegate.getSysDate());
			System.out.println("button value--------->"+button_value);
			String date=pswsform.getTxt_date();
			System.out.println("date value--------->"+date);
			if(date!=null)
			{
				 int psws=deligate.processPSWS(date,"lntml","lnuser", 0);
				 System.out.println("psws---------->---->--->"+psws);
				 if(psws==3) 
				 {
					 pswsform.setPswsprocessing("PrevRecorNotPrinted");
					 System.out.println("psws process value==============>"+pswsform.getPro_value());
					 
					 if(pswsform.getPro_value().equalsIgnoreCase("process_value"))
						 if(deligate.processPSWS(date,"lntml","lnuser", 1)==1)
							System.out.println("Loan Processed");
					else
							System.out.println("Loan Not Processed"); 
					 
				 }
				 else if(psws==1)
						System.out.println("LoanProcessed");
					else
						System.out.println("LoanNotProcessed");
			} 
			if(button_value!=null)
			{
				System.out.println("************Button value*************"+button_value);
				if(button_value.equalsIgnoreCase("ShowPart1") || pswsform.getButton_val().equalsIgnoreCase("download"))
				{ 
					System.out.println("************Inside Show part one*************");
					
					Map mobjectmap=null,mobjectmap1=null;
					List listobj=new ArrayList();
					array_pswsobject=deligate.getPSWSDetails(date,0,1,null);
					pawsobject=deligate.getPSDetails();
					if(array_pswsobject!=null && pawsobject!=null || pswsform.getButton_val().equalsIgnoreCase("download") )
					{
						String desc[]=pawsobject.getPriorityDesc();
						int code[]=pawsobject.getPriorityCode();
						for(int j=0;j<code.length;j++)
						{ 
							System.out.println("code===========>"+code[j]);
							System.out.println("desc-------->"+desc[j]);
							mobjectmap=new HashMap();
							mobjectmap.put("SrlNo",String.valueOf(code[j]));
							mobjectmap.put("PrioritySectorItems",String.valueOf(desc[j]));
							mobjectmap.put("SrlNo",String.valueOf(code[j]));
							mobjectmap.put("PrioritySectorItems",String.valueOf(desc[j]));
							listobj.add(mobjectmap);
							System.out.println("list object--------->"+listobj.size());

						} 
							
						for(int i=0;i<array_pswsobject.length;i++)
						{
							mobjectmap1=new HashMap();
							int j=0;
							for(j=0;j<code.length;j++)					
							{
								if(code[j]==array_pswsobject[i].getPrioritySectorCode())
								break;
							}
							mobjectmap1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[0].getNoOfBorrowers()));
							mobjectmap1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[0].getSanctionedAmt()));
							mobjectmap1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[0].getAmtAdvanced()));
							mobjectmap1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[0].getLoanBalance()));
							mobjectmap1.put("AmountoverdueunderColumn",String.valueOf(array_pswsobject[i].det[0].getAmtOverDue()));
							if(array_pswsobject[i].det[1]!=null)					
							{
								mobjectmap1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[0].getNoOfBorrowers()));
								mobjectmap1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[0].getSanctionedAmt()));
								mobjectmap1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[0].getAmtAdvanced()));
								mobjectmap1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[0].getLoanBalance()));
								mobjectmap1.put("AmountoverdueunderColumn",String.valueOf(array_pswsobject[i].det[0].getAmtOverDue()));
							}
							listobj.add(mobjectmap1);
							System.out.println("List size======>2===>"+listobj.size());
						}
						if (pswsform.getButton_val().equalsIgnoreCase("download")) {
							System.out.println("I am in download=======");
							array_pswsobject=deligate.getPSWSDetails(date,0,1,null);
							pawsobject=deligate.getPSDetails();
							/*	System.out.println("loan_rep"+array_pswsobject.length);
								for (int i = 0; i < array_pswsobject.length; i++)
								{	
									array_pswsobject[i]=new PSWSObject();
									System.out.println("vector size Object--->"+ i);
									//array_pswsobject[i] = (LoanReportObject) vecreportobj.get(i);
									System.out.println("*****Loan Report*****"+array_pswsobject[i].getAccNo());
								}*/
							if (array_pswsobject == null) {
								pswsform.setTesting("Cannot Print");
							} else {
								System.out.println(" i am inside down load");

								// TO save to an excel Sheet
								res.setContentType("application/.csv");
								res.setHeader("Content-disposition","attachment;filename=PSWSProcessingReport.csv");
								java.io.PrintWriter out = res.getWriter();
								out.print("\n");
								out.print("\n");
								out.print("\n");
								out.print(",");
								out.print(",");
								out.print(",");
								//array_pswsobject = new LoanReportObject[vecreportobj.size()];
								System.out.println("loan_rep"+array_pswsobject.length);
								/*for (int i = 0; i < array_pswsobject.length; i++)
								{	
									array_pswsobject[i]=new PSWSObject();
									System.out.println("vector size Object--->"+ i);
									//loan_rep[i] = (LoanReportObject) vecreportobj.get(i);
									System.out.println("*****Loan Report*****"+array_pswsobject[i].getAccNo());
								}*/
									out.print("PSWSProcessing Details for A/C Type: "
											+ array_pswsobject[0].getAccType());
								
								/*out.print("TDLedger Details for A/C Type: "
										+ loan_rep[i].getAccType());*/
								out.print("\n");
								out.print("\n");
								out.print("SrlNo");
								out.print(",");
								out.print("No.ofCoborrowersUnits");
								out.print(",");
								out.print(" LimitSanctioned");
								out.print(",");
								out.print("AmountAdvanced");
								out.print(",");
								out.print("BalanceOutStanding");
								out.print(",");
								out.print("AmountOverDueUnderColumn-6");
								out.print(",");
								out.print("No.ofCoborrowersUnits");
								out.print(",");
								out.print(" LimitSanctioned");
								out.print(",");
								out.print("AmountAdvanced");
								out.print(",");
								out.print("BalanceOutStanding");
								out.print(",");
								out.print("");
								out.print("\n");

								for (int k = 0; k < array_pswsobject.length; k++) {
									out.print(k+1);
									out.print(",");
									out.print(array_pswsobject[k].getAccNo());
									out.print(",");
									out.print(array_pswsobject[k].getAccType());
									out.print(",");
									out.print(array_pswsobject[k].getCategory());
									out.print(",");
									out.print(array_pswsobject[k].getPrioritySectorCode());
									out.print(",");
									out.print(array_pswsobject[k].getPrioritySectorDesc());
									out.print(",");
									out.print(",");
									out.print("\n");
								}
								req.setAttribute("msg","Saved to excel file in C:");
								return null;

							}
						}
								
						req.setAttribute("PSWSProcess",listobj);
						
						}
					
					pswsform.setPswsprocessing(null);
				}
/*				else if(button_value.equalsIgnoreCase("ShowPart2"))
				{ 
					Map mapobject=null,mapobject1=null;
					List listobject=new ArrayList();
					array_pswsobject=deligate.getPSWSDetails(date,0,1,null);
					pawsobject=deligate.getPSDetails();
					if(array_pswsobject!=null && pawsobject!=null)
					{
						String desc[]=pawsobject.getPriorityDesc();
						int code[]=pawsobject.getPriorityCode();
						for(int j=0;j<code.length;j++)
						{ 
							System.out.println("code===========>"+code[j]);
							System.out.println("desc-------->"+desc[j]);
							mapobject=new HashMap();
							mapobject.put("SrlNo",String.valueOf(code[j]));
							mapobject.put("PrioritySectorItems",String.valueOf(desc[j]));
							mapobject.put("SrlNo",String.valueOf(code[j]));
							mapobject.put("PrioritySectorItems",String.valueOf(desc[j]));
							listobject.add(mapobject);
							System.out.println("list object--------->"+listobject.size());

						} 
							
						for(int i=0;i<array_pswsobject.length;i++)
						{
							mapobject1=new HashMap();
							int j=0;
							for(j=0;j<code.length;j++)					
							{
								if(code[j]==array_pswsobject[i].getPrioritySectorCode())
								break;
							}
							if(array_pswsobject[i].det[1]!=null)					
							{
								if(array_pswsobject[i].det[2]!=null)
								{
									mapobject1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[2].getNoOfBorrowers()));
									mapobject1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[2].getSanctionedAmt()));
									mapobject1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[2].getAmtAdvanced()));
									mapobject1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[2].getLoanBalance()));
									mapobject1.put("AmountoverdueunderColumn6",String.valueOf(array_pswsobject[i].det[2].getAmtOverDue()));

								}
								if(array_pswsobject[i].det[3]!=null)					
								{
									mapobject1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[3].getNoOfBorrowers()));
									mapobject1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[3].getSanctionedAmt()));
									mapobject1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[3].getAmtAdvanced()));
									mapobject1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[3].getLoanBalance()));
									mapobject1.put("AmountoverdueunderColumn12",String.valueOf(array_pswsobject[i].det[3].getAmtOverDue()));

								}
								if(array_pswsobject[i].det[4]!=null)					
								{
									
									mapobject1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[4].getNoOfBorrowers()));
									mapobject1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[4].getSanctionedAmt()));
									mapobject1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[4].getAmtAdvanced()));
									mapobject1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[4].getLoanBalance()));
									mapobject1.put("AmountoverdueunderColumn12",String.valueOf(array_pswsobject[i].det[4].getAmtOverDue()));

								}
								mapobject1.put("NoOfborrowerunits",String.valueOf(array_pswsobject[i].det[1].getNoOfBorrowers()));
								mapobject1.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[1].getSanctionedAmt()));
								mapobject1.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[1].getAmtAdvanced()));
								mapobject1.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[1].getLoanBalance()));
								mapobject1.put("AmountoverdueunderColumn12",String.valueOf(array_pswsobject[1].det[4].getAmtOverDue()));

							}
							listobject.add(mapobject1);
							System.out.println("List size======>2===>"+listobject.size());
						}
								
						req.setAttribute("PSWSProcess",listobject);
						
						}
				}*/
			}
			
			
			if(MenuNameReader.containsKeyScreen(pswsform.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(pswsform.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			}
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch (Exception e) {
			String path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements(""+e,req,path);
            return map.findForward(ResultHelp.getError());
		}
		
			
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/PSWSScheduleMenu"))
		{ 
		try{	
			PSWSScheduleForm pswsshedule=(PSWSScheduleForm)form;
			LoansDelegate deligate=new LoansDelegate();
			pswsshedule.setDelete_value(null);
			String path;
			req.setAttribute("processdates",deligate.getProcessedDates());
			
			
			
			if(MenuNameReader.containsKeyScreen(pswsshedule.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(pswsshedule.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch (Exception e) {
			String path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements(""+e,req,path);
            return map.findForward(ResultHelp.getError());
		}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/PSWSSchedule"))
		{ 
		try{	
			PSWSScheduleForm pswsshedule=(PSWSScheduleForm)form;
			LoansDelegate deligate=new LoansDelegate();
			String path; 
			pswsshedule.setDelete_value(null);
			req.setAttribute("processdates",deligate.getProcessedDates());
			System.out.println("button value------------>"+pswsshedule.getButton_value());
			if(pswsshedule.getButton_value()!=null)
			{
				if(pswsshedule.getButton_value().equalsIgnoreCase("PrioritySector"))
				{	 
					if(pswsshedule.getTxt_priorityseccode()!=0)
					{
						
						System.out.println("********PrioritySector*****************"+pswsshedule.getTxt_priorityseccode());
						array_pswsobject=deligate.getPSWSDetails(pswsshedule.getTxt_repdate(),pswsshedule.getTxt_priorityseccode(), 2,null);
						if(array_pswsobject!=null)
						{
							String ty="";
							Map mobjectmap=null,mobjectmap1=null;
							List listobj=new ArrayList();
							for(int i=0;i<array_pswsobject.length;i++)
							{
								mobjectmap=new HashMap();
								/*for(int j=0;j<array_moduleobject.length;j++)
								if(array_pswsobject[i].getAccType().equals(array_moduleobject[j].getModuleCode()))
								{
									ty=array_moduleobject[j].getModuleAbbrv();
									break;
								}*/
								mobjectmap.put("LoanACNo",array_pswsobject[i].getAccNo());
								mobjectmap.put("Name",array_pswsobject[i].getName());
								mobjectmap.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[0].getSanctionedAmt()));
								mobjectmap.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[0].getAmtAdvanced()));
								mobjectmap.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[0].getLoanBalance()));
								mobjectmap.put("AmountOverdue",String.valueOf(array_pswsobject[i].det[0].getAmtOverDue()));
								
								
								if(array_pswsobject[i].getWKSect()!=null?array_pswsobject[i].getWKSect().equals("Y"):false)
								{
									mobjectmap.put("LimitSactioned",String.valueOf(array_pswsobject[i].det[0].getSanctionedAmt()));
									mobjectmap.put("AmountAdvanced",String.valueOf(array_pswsobject[i].det[0].getAmtAdvanced()));
									mobjectmap.put("BalanceOutStanding",String.valueOf(array_pswsobject[i].det[0].getLoanBalance()));
									mobjectmap.put("AmountOverdue",String.valueOf(array_pswsobject[i].det[0].getAmtOverDue()));
								}
								listobj.add(mobjectmap);
							}
							req.setAttribute("PSWSSchedule",listobj);
						}
					}
				
				}
				else if(pswsshedule.getButton_value().equalsIgnoreCase("WeekerSector"))
				{	 
					if(pswsshedule.getTxt_priorityseccode()!=0)
					{
						req.setAttribute("Pswssheduledetail",deligate.getPSWSDetails(pswsshedule.getTxt_repdate(),pswsshedule.getTxt_priorityseccode(), 2,null));
					}
				}
				
				else if(pswsshedule.getButton_value().equalsIgnoreCase("Delete"))
				{	 
					if(pswsshedule.getTxt_priorityseccode()!=0)
					{
						if(deligate.getPSWSDetails(pswsshedule.getTxt_repdate(),pswsshedule.getTxt_priorityseccode(), 3,null)!=null)
						{
							pswsshedule.setDelete_value("Deleted");
						}
						else
						{
							pswsshedule.setDelete_value("CannotDelete");
						}
					}
				}
				/*if(pswsshedule.getButton_value().equalsIgnoreCase("PrioritySector"))
				{*/	 
					/*if(pswsshedule.getTxt_priorityseccode()!=0)
					{*/
				if (pswsshedule.getButton_value().equalsIgnoreCase("download")) {
					System.out.println("I am in download=======");
					if(pswsshedule.getButton_value().equalsIgnoreCase("PrioritySector")!=true){ 
				if(pswsshedule.getTxt_priorityseccode()!=0)
						{
							System.out.println("********PrioritySector*****************"+pswsshedule.getTxt_priorityseccode());
							array_pswsobject=deligate.getPSWSDetails(pswsshedule.getTxt_repdate(),pswsshedule.getTxt_priorityseccode(), 2,null);
							if(array_pswsobject!=null)
							{
						System.out.println(" i am inside down load");
						// TO save to an excel Sheet
						res.setContentType("application/.csv");
						res.setHeader("Content-disposition","attachment;filename=PSWSScheduleReport.csv");
						java.io.PrintWriter out = res.getWriter();
						out.print("\n");
						out.print("\n");
						out.print("\n");
						out.print(",");
						out.print(",");
						out.print(",");
						out.print("\n");
						out.print("\n");
						out.print("SrlNo");
						out.print(",");
						out.print("Ac/No");
						out.print(",");
						out.print(" Name");
						out.print(",");
						out.print("LimitSanctioned");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("BalanceOutStanding");
						out.print(",");
						out.print("AmountOverDue");
						out.print(",");
						out.print("LimitSanctioned");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("BalanceOutStanding");
						out.print(",");
						out.print("");
						out.print("\n");

						for (int k = 0; k < array_pswsobject.length; k++) {
							out.print(k+1);
							out.print(",");
							out.print(array_pswsobject[k].getAccNo());
							out.print(",");
							out.print(array_pswsobject[k].getName());
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getSanctionedAmt()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtAdvanced()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getLoanBalance()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtOverDue()));
							out.print(",");
							if(array_pswsobject[k].getWKSect()!=null?array_pswsobject[k].getWKSect().equals("Y"):false)
							{
							out.print(String.valueOf(array_pswsobject[k].det[0].getSanctionedAmt()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtAdvanced()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getLoanBalance()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtOverDue()));
							out.print(",");
							}
							out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
						return null;
				}
							}
				}
				

					else if(pswsshedule.getButton_value().equalsIgnoreCase("WeekerSector")!=true)
					{
						System.out.println("am in else if");
						if(pswsshedule.getTxt_priorityseccode()!=0)
						{
							array_pswsobject=deligate.getPSWSDetails(pswsshedule.getTxt_repdate(),pswsshedule.getTxt_priorityseccode(), 2,null);
							
							if(array_pswsobject!=null)
							{
								
						System.out.println(" i am inside down load");

						// TO save to an excel Sheet
						res.setContentType("application/.csv");
						res.setHeader("Content-disposition","attachment;filename=PSWSScheduleReport.csv");
						java.io.PrintWriter out = res.getWriter();
						out.print("\n");
						out.print("\n");
						out.print("\n");
						out.print(",");
						out.print(",");
						out.print(",");
						System.out.println("loan_rep"+array_pswsobject.length);
						out.print("\n");
						out.print("\n");
						out.print("SrlNo");
						out.print(",");
						out.print("Ac/No");
						out.print(",");
						out.print(" Name");
						out.print(",");
						out.print("LimitSanctioned");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("BalanceOutStanding");
						out.print(",");
						out.print("AmountOverDue");
						out.print(",");
						out.print("LimitSanctioned");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("AmountAdvanced");
						out.print(",");
						out.print("BalanceOutStanding");
						out.print(",");
						out.print("");
						out.print("\n");

						for (int k = 0; k < array_pswsobject.length; k++) {
							out.print(k+1);
							out.print(",");
							out.print(array_pswsobject[k].getAccNo());
							out.print(",");
							out.print(array_pswsobject[k].getName());
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getSanctionedAmt()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtAdvanced()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getLoanBalance()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtOverDue()));
							out.print(",");
							if(array_pswsobject[k].getWKSect()!=null?array_pswsobject[k].getWKSect().equals("Y"):false)
							{
							out.print(String.valueOf(array_pswsobject[k].det[0].getSanctionedAmt()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtAdvanced()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getLoanBalance()));
							out.print(",");
							out.print(String.valueOf(array_pswsobject[k].det[0].getAmtOverDue()));
							out.print(",");
							}
							out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
						return null;
				}
						}
					}
						}
			}
			if(MenuNameReader.containsKeyScreen(pswsshedule.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(pswsshedule.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch (Exception e) {
			e.printStackTrace();
			String path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements(""+e,req,path);
            return map.findForward(ResultHelp.getError());
		}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/JewelReportMenu"))
		{ 
		try{	
			JewelReportForm jewelrepform=(JewelReportForm)form;
			LoansDelegate deligate=new LoansDelegate();
			jewelrepform.setSysdate(LoansDelegate.getSysDate());
			String path;
			if(MenuNameReader.containsKeyScreen(jewelrepform.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(jewelrepform.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 req.setAttribute("From_date", LoansDelegate.getSysDate());
				 req.setAttribute("To_date", LoansDelegate.getSysDate());
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exist",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch (Exception e) {
			e.printStackTrace();
			String path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements(""+e,req,path);
            return map.findForward(ResultHelp.getError());
		}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/JewelReport"))
		{ 
		try{	
			
			JewelReportForm jewelrepform=(JewelReportForm)form;
			LoansDelegate deligate=new LoansDelegate();
			jewelrepform.setSysdate(LoansDelegate.getSysDate());
			String path;
			if(jewelrepform.getTxt_fromdate()!=null && jewelrepform.getTxt_To_date()!=null)
			{
				Vector gold=deligate.getJewelReport(jewelrepform.getTxt_fromdate(),jewelrepform.getTxt_To_date());
				req.setAttribute("From_date", jewelrepform.getTxt_fromdate());
				req.setAttribute("To_date", jewelrepform.getTxt_To_date());
				JwelReport(gold,req);
			}
			if(jewelrepform.getForward()!=null){
			if (jewelrepform.getForward().equalsIgnoreCase("download")) {
				if(jewelrepform.getTxt_fromdate()!=null && jewelrepform.getTxt_To_date()!=null)
				{
				GoldObject gold_rep=null;
				Vector gold=deligate.getJewelReport(jewelrepform.getTxt_fromdate(),jewelrepform.getTxt_To_date());
				Enumeration enumerate=gold.elements();
				while(enumerate.hasMoreElements()){
					gold_rep=(GoldObject)enumerate.nextElement();
				//}
				System.out.println("I am in download=======");
				
				if (gold_rep == null) {
					jewelrepform.setTesting("Cannot Print");
				} else {
					System.out.println(" i am inside down load");

					// TO save to an excel Sheet
					res.setContentType("application/.csv");
					res.setHeader("Content-disposition","attachment;filename=JewelReport.csv");
					java.io.PrintWriter out = res.getWriter();
					out.print("\n");
					out.print("\n");
					out.print("\n");
					out.print(",");
					out.print(",");
					out.print(",");
						out.print("\n");
						out.print("\n");
						out.print("SrlNo");
						out.print(",");
						out.print("Date");
						out.print(",");
						out.print("AccTypeAndAccNum");
						out.print(",");
						out.print("Appraisername");
						out.print(",");
						out.print("Description");
						out.print(",");
						out.print("GrossWeight");
						out.print(",");
						out.print("NetWeight");
						out.print(",");
						out.print("Rate");
						out.print(",");
						out.print("NetGold");
						out.print(",");
						out.print("");
						out.print("\n");
//
//						for (int k = 0; k < gold_rep.; k++) {
//							out.print(k);
							
							out.print(gold_rep.getSlno());
							out.print(",");
							out.print(gold_rep.getApprisalDate());
							out.print(",");
							out.print(gold_rep.getAccType()+" "+gold_rep.getAccNo());
							out.print(",");
							out.print(gold_rep.getApprisersName());
							out.print(",");
							Object[] objects=null;
							Vector vector1=gold_rep.getGoldDetVector();
							Enumeration enumeration=vector1.elements();
							while(enumeration.hasMoreElements()){
								objects=(Object[])enumeration.nextElement();
									out.print(objects[1]);
									out.print(",");
									out.print(objects[2]);
									out.print(",");
									out.print(objects[3]);
									out.print(",");
									out.print(objects[4]);
									out.print(",");
							}
							out.print(",");
							out.print("\n");
					}
					req.setAttribute("msg","Saved to excel file in C:");
					return null;

				//}
			}
			}}}
			if(MenuNameReader.containsKeyScreen(jewelrepform.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(jewelrepform.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
		}catch (Exception e) {
			e.printStackTrace();
			String path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements(""+e,req,path);
            return map.findForward(ResultHelp.getError());
		}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/DCBSchedulingMenu"))
		{
			try{
					String path=null;
					DCBSchedulingForm schedulingform =(DCBSchedulingForm)form;
					LoansDelegate deligate=new LoansDelegate();
					getInitialparam_DCB(deligate, req);
					if(MenuNameReader.containsKeyScreen(schedulingform.getPageidentity().getPageId()))
					 { 
						 path=MenuNameReader.getScreenProperties(schedulingform.getPageidentity().getPageId());
						 System.out.println("path=======>"+path);
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
				   e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			   }
		}  
		else if(map.getPath().equalsIgnoreCase("/Loans/DCBScheduling"))
		{
			try{ 
					System.out.println("*****Inside /Loans/DCBScheduling*******");
					String path=null;
					DCBSchedulingForm schedulingform =(DCBSchedulingForm)form;
					LoansDelegate deligate=new LoansDelegate();
					getInitialparam_DCB(deligate, req);
					
					if(schedulingform.getButton_val()!=null)
					{	 
						if(schedulingform.getButton_val().equalsIgnoreCase("View"))
						{
							if(schedulingform.getCombo_pay_actype().equalsIgnoreCase("ALL"))
							{	
								ArrayList list=new ArrayList();
								dcb=deligate.dcbSchedule(Integer.valueOf(schedulingform.getTxt_monthandyear()),Integer.valueOf(schedulingform.getCombo_year()),0,"",0,0);
								TreeMap treemap=new TreeMap(); 
								for(int i=0;i<dcb.length;i++)
								{
									treemap.put("LoanBalance",dcb[i].getLoanBalance());
									treemap.put("PrincipalArr",dcb[i].getPrincipalArr());
									treemap.put("IntrArr",dcb[i].getIntrArr());
									treemap.put("PenalIntArr",dcb[i].getPenalIntArr());
									treemap.put("OtherArr",dcb[i].getOtherArr());
									treemap.put("AdvPaid",dcb[i].getAdvPaid());
									treemap.put("PrincipalDemand",dcb[i].getPrincipalDemand());
									treemap.put("IntrDemand",dcb[i].getIntrDemand());
									treemap.put("OtherDemand",dcb[i].getOtherDemand());
									treemap.put("PrincipalCollected",dcb[i].getPrincipalCollected());
									treemap.put("IntrCollected",dcb[i].getIntrCollected());
									treemap.put("PenalCollected",dcb[i].getPenalCollected());
									treemap.put("OtherCollected",dcb[i].getOtherCollected());
									treemap.put("AdvCollected",dcb[i].getAdvCollected());
									treemap.put("data15",dcb[i].getPrincipalArr()+dcb[i].getPrincipalDemand()-dcb[i].getPrincipalCollected());
									treemap.put("data16",dcb[i].getIntrArr()+dcb[i].getIntrDemand()-dcb[i].getIntrCollected());
									treemap.put("data17",dcb[i].getPenalIntArr()-dcb[i].getPenalCollected());
									treemap.put("data18",dcb[i].getOtherArr()+dcb[i].getOtherDemand()-dcb[i].getOtherCollected());
									treemap.put("data19",dcb[i].getLoanBalance()-dcb[i].getPrincipalCollected());
									System.out.println("loan balance ----->--->"+dcb[i].getLoanBalance());
									list.add(treemap); 
								}
								
								req.setAttribute("list",list);
							}	
							else if(schedulingform.getCombo_pay_actype().equalsIgnoreCase("selected") && schedulingform.getTxt_fromacnum()!=null && schedulingform.getTxt_toaccnum()!=null)
							{
								dcb=deligate.dcbSchedule(Integer.valueOf(schedulingform.getTxt_monthandyear()),Integer.valueOf(schedulingform.getCombo_year()),1,schedulingform.getCombo_actype(),Integer.valueOf(schedulingform.getTxt_fromacnum()),Integer.valueOf(schedulingform.getTxt_toaccnum()));
								ArrayList list=new ArrayList();
								dcb=deligate.dcbSchedule(Integer.valueOf(schedulingform.getTxt_monthandyear()),Integer.valueOf(schedulingform.getCombo_year()),0,"",0,0);
								TreeMap treemap=new TreeMap(); 
								for(int i=0;i<dcb.length;i++)
								{
									treemap.put("LoanBalance",dcb[i].getLoanBalance());
									treemap.put("PrincipalArr",dcb[i].getPrincipalArr());
									treemap.put("IntrArr",dcb[i].getIntrArr());
									treemap.put("PenalIntArr",dcb[i].getPenalIntArr());
									treemap.put("OtherArr",dcb[i].getOtherArr());
									treemap.put("AdvPaid",dcb[i].getAdvPaid());
									treemap.put("PrincipalDemand",dcb[i].getPrincipalDemand());
									treemap.put("IntrDemand",dcb[i].getIntrDemand());
									treemap.put("OtherDemand",dcb[i].getOtherDemand());
									treemap.put("Prin Coll",dcb[i].getPrincipalCollected());
									treemap.put("Intr Coll",dcb[i].getIntrCollected());
									treemap.put("Penal Coll",dcb[i].getPenalCollected());
									treemap.put("Other Coll",dcb[i].getOtherCollected());
									treemap.put("Adv Coll",dcb[i].getAdvCollected());
									treemap.put("Prin Bal",dcb[i].getPrincipalArr()+dcb[i].getPrincipalDemand()-dcb[i].getPrincipalCollected());
									treemap.put("Intr Bal",dcb[i].getIntrArr()+dcb[i].getIntrDemand()-dcb[i].getIntrCollected());
									treemap.put("Pintr Bal",dcb[i].getPenalIntArr()-dcb[i].getPenalCollected());
									treemap.put("Other Bal",dcb[i].getOtherArr()+dcb[i].getOtherDemand()-dcb[i].getOtherCollected());
									treemap.put("Closing Bal",dcb[i].getLoanBalance()-dcb[i].getPrincipalCollected());
									System.out.println("loan balance ----->--->"+dcb[i].getLoanBalance());
									list.add(treemap); 
								}
								req.setAttribute("list",list);
							}
						}
					}
					
					if(MenuNameReader.containsKeyScreen(schedulingform.getPageidentity().getPageId()))
					 { 
						 path=MenuNameReader.getScreenProperties(schedulingform.getPageidentity().getPageId());
						 System.out.println("path=======>"+path);
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
				   e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			   }
		}
		if(map.getPath().equalsIgnoreCase("/Loans/DCBSummaryMenu"))
		{
			try{
				String path=null;
				DCBSchedulingForm schedulingform=(DCBSchedulingForm)form;
				LoansDelegate deligate=new LoansDelegate(); 
				System.out.println("*********/Loans/DCBSummaryMenu*****");
				req.setAttribute("DCBSummary",deligate.dcbDelete(1,null));
				
				if(MenuNameReader.containsKeyScreen(schedulingform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(schedulingform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/DCBSummary"))
		{
			try{
				String path=null; 
				DCBSchedulingForm schedulingform=(DCBSchedulingForm)form;
				LoansDelegate deligate=new LoansDelegate(); 
				System.out.println("*********/Loans/DCBSummary*****");
				req.setAttribute("DCBSummary",deligate.dcbDelete(1,null));
				System.out.println("button value=========>"+schedulingform.getButton_value());
				if(schedulingform.getButton_value()!=null)
				{
					if(schedulingform.getButton_value().equalsIgnoreCase("View"))
					{ 
						System.out.println("button value---------inside view---->"+schedulingform.getButton_val());
						DCBObject dcbobj[]=deligate.dcbSummary(schedulingform.getCombo_Monthyear());
						TreeMap treemap=new TreeMap();
						ArrayList arraylist=new ArrayList();
						for(int i=0;i<dcbobj.length;i++)
						{ 
							treemap.put("PrinOD",dcbobj[i].getPrincipalArr());
							treemap.put("IntrOD",dcbobj[i].getIntrArr());
							treemap.put("PIntrOD",dcbobj[i].getPenalIntArr());
							treemap.put("OtherOD",dcbobj[i].getOtherArr());
							treemap.put("AdvPaid",dcbobj[i].getAdvPaid());
							treemap.put("PrinDemand",dcbobj[i].getPrincipalDemand());
							treemap.put("IntrDemand",dcbobj[i].getIntrDemand());
							treemap.put("OtherDemand",dcbobj[i].getOtherDemand());
							treemap.put("PrColl",dcbobj[i].getPrincipalCollected());
							treemap.put("IntrColl",dcbobj[i].getIntrCollected());
							treemap.put("PIntrColl",dcbobj[i].getPenalCollected());
							treemap.put("OtherColl",dcbobj[i].getOtherCollected());
							treemap.put("AdvColl",dcbobj[i].getAdvCollected());
							treemap.put("PrinBal",dcbobj[i].getPrincipalArr()+dcbobj[i].getPrincipalDemand()-dcbobj[i].getPrincipalCollected());
							treemap.put("IntrBal",dcbobj[i].getIntrArr()+dcbobj[i].getIntrDemand()-dcbobj[i].getIntrCollected());
							treemap.put("PintrBal",dcbobj[i].getPenalIntArr()-dcbobj[i].getPenalCollected());
							treemap.put("OtherBal",dcbobj[i].getOtherArr()+dcbobj[i].getOtherDemand()-dcbobj[i].getOtherCollected());
							arraylist.add(treemap);
						}
						
						req.setAttribute("arraylist",arraylist);
					}
				}
				if(MenuNameReader.containsKeyScreen(schedulingform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(schedulingform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/DCBMaintenanceMenu"))
		{
			try{
				String path=null;
				DCBSchedulingForm maintenanceform=(DCBSchedulingForm)form;
				LoansDelegate deligate=new LoansDelegate();
				maintenanceform.setDelete_sucess(null);
				System.out.println("*********/Loans/DCBMaintenanceMenu*****");
				req.setAttribute("DCBMaint",deligate.dcbDelete(2,null));
				
				if(MenuNameReader.containsKeyScreen(maintenanceform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(maintenanceform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",MenuNameReader.getScreenProperties("5098"));
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		
//		if(map.getPath().equalsIgnoreCase("/Loans/DCBMaintenanceMenu"))
//		{
//			System.out.println("hi am in LoanAction2.java");
//		try{
//			DCBSchedulingForm dcbmaintenceform=(DCBSchedulingForm)form;
//			LoansDelegate deligate =new LoansDelegate();
//			String path=null;
//			dcbmaintenceform.setDelete_sucess("null");
//			req.setAttribute("DCBMaint",deligate.dcbDelete(1,null)); 
//			if(MenuNameReader.containsKeyScreen(dcbmaintenceform.getPageidentity().getPageId()))
//			 { 
//				 path=MenuNameReader.getScreenProperties(dcbmaintenceform.getPageidentity().getPageId());
//				 System.out.println("path===*********====>"+path);
//				 req.setAttribute("pageId",path);
//				 return map.findForward(ResultHelp.getSuccess()); 
//			 }
//			else
//			{
//				path=MenuNameReader.getScreenProperties("0000");
//				setErrorPageElements("Path doest exit",req,path);
//				return map.findForward(ResultHelp.getError());
//			}
//			}catch (Exception e) {
//				e.printStackTrace();
//				   String path=MenuNameReader.getScreenProperties("0000");
//		           setErrorPageElements(""+e,req,path);
//		           return map.findForward(ResultHelp.getError());
//			}
//		}
		if(map.getPath().equalsIgnoreCase("/Loans/DCBMaintenance"))
		{
			System.out.println("hi am in **********LoanAction2.java***********");
		try{
			
			DCBSchedulingForm dcbmaintenceform=(DCBSchedulingForm)form;
			LoansDelegate deligate =new LoansDelegate();
			String path=null;
			req.setAttribute("DCBMaint",deligate.dcbDelete(1,null)); 
			System.out.println("button value========-----====>"+dcbmaintenceform.getButton_value());
			dcbmaintenceform.setDelete_sucess("null");
			System.out.println("Check box property==========>"+dcbmaintenceform.getCheckbox_prpo());
			if(dcbmaintenceform.getButton_value()!=null)
			{
				if(dcbmaintenceform.getButton_value().equalsIgnoreCase("Delete"))
				{     
					System.out.println("value=====>"+dcbmaintenceform.getDCBMainValue());
					String[] value={dcbmaintenceform.getDCBMainValue()};
					deligate.dcbDelete(2,value); 
				 	dcbmaintenceform.setDelete_sucess("DELETED");
					
				}
				
			} 
			if(MenuNameReader.containsKeyScreen(dcbmaintenceform.getPageidentity().getPageId()))
			 { 
				 path=MenuNameReader.getScreenProperties(dcbmaintenceform.getPageidentity().getPageId());
				 System.out.println("path=======>"+path);
				 req.setAttribute("pageId",path);
				 return map.findForward(ResultHelp.getSuccess()); 
			 }
			else
			{
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Path doest exit",req,path);
				return map.findForward(ResultHelp.getError());
			}
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		
		else if(map.getPath().equalsIgnoreCase("/Loans/DCBProcessMenu"))
		{
			 try{
				 DCBSchedulingForm dcbProcessform=(DCBSchedulingForm)form;
				 LoansDelegate deligate=new LoansDelegate();
				 String path=null;
				 getInitialparam_DCB(deligate, req);
				 if(MenuNameReader.containsKeyScreen(dcbProcessform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(dcbProcessform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 	}catch (Exception e) {
				 e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			 	}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/DCBProcess"))
		{
			 try{
				 DCBSchedulingForm dcbProcessform=(DCBSchedulingForm)form;
				 LoansDelegate deligate=new LoansDelegate();
				 String path=null;
				 getInitialparam_DCB(deligate, req); 
				 System.out.println("Process Button value=====>"+dcbProcessform.getButton_process());
				 if(dcbProcessform.getButton_process()!=null)
				 {
					 if(dcbProcessform.getButton_process().equalsIgnoreCase("Process"))
					 { 
						 System.out.println("**********Inside DCB Process Button*******");
						 System.out.println("year=====>"+dcbProcessform.getCombo_year()+"/n"+"month"+dcbProcessform.getTxt_monthandyear());
						 StringTokenizer st = new StringTokenizer(deligate.getSysDate(),"/");
						 st.nextToken();
				         int mm=Integer.parseInt(st.nextToken());
				         int yy=Integer.parseInt(st.nextToken());
				         if( yy==Integer.parseInt(dcbProcessform.getCombo_year()) && mm<Integer.valueOf(dcbProcessform.getTxt_monthandyear()))
				         { 
				        	 System.out.println("*******You can't process DCB for the future dates...*******");
				        	 dcbProcessform.setValid_dcbpro_futuredate("CanotProcessDcb");
				         }	
				         boolean check = deligate.checkDCBReport(Integer.valueOf(dcbProcessform.getTxt_monthandyear()),Integer.valueOf(dcbProcessform.getCombo_year()));
				         System.out.println("boolean value=====>======>===>"+check); 
				         if(check)
				         {
				        	dcbProcessform.setValidate_dcbprocess("SomeRecordsExit");
				        	dcbProcess(true, deligate, dcbProcessform); 
				         }
				         else
				         { 
				        	 System.out.println("******Inside Else Condition In DCBProcess******");
				        	 int value_dcb=dcbProcess(false, deligate, dcbProcessform);
				        	 System.out.println("After processing dcb value==="+value_dcb);
				         }
					 }
				 }
				 if(MenuNameReader.containsKeyScreen(dcbProcessform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(dcbProcessform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			 	}catch (Exception e) {
				 e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			 	}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/LoanPassBookMenu"))
		{
			try{
				System.out.println("this is tested by banu");
				System.out.println("**********In /Loans/LoanPassBookMenu***********");
				String path;
				LoanPassBookForm passbookform=(LoanPassBookForm)form;
				passbookform.setValidate_accnum(null);
				LoansDelegate delidate=new LoansDelegate();
				Initialparam_loanActyp(req,delidate);
				if(MenuNameReader.containsKeyScreen(passbookform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(passbookform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/LoanPassBook"))
		{
			try{ 
				System.out.println("**********In /Loans/LoanPassBook===========>banu***********");
				String path;
				LoanPassBookForm passbookform=(LoanPassBookForm)form;
				LoansDelegate delidate=new LoansDelegate();
				passbookform.setValidate_accnum(null);
				TreeMap treemap= null;
				ArrayList list=new ArrayList();
				Initialparam_loanActyp(req,delidate);
				String button_value=passbookform.getButton_value(); 
				System.out.println("button value==============<"+button_value);
				if(button_value!=null)
				{
					if(button_value.equalsIgnoreCase("View"))
					{
						if(passbookform.getTxt_accnum()!=0)
						{ 
							System.out.println("*******Inside Loan Account Number Not Null*******"+passbookform.getTxt_accnum());
							loanRepObj=delidate.getLoanDetails(passbookform.getTxt_loancatAccno(),passbookform.getTxt_accnum(),delidate.getSysDate());
							System.out.println("Loan Report object======>"+loanRepObj); 
							if(loanRepObj!=null)
							{
								passbookform.setTxt_name(loanRepObj.getName());
								passbookform.setTxt_SBNo(loanRepObj.getSavingDet());
								passbookform.setTxt_purpose(loanRepObj.getLoanPurpose());
								passbookform.setTxt_sharenum(loanRepObj.getShareDet());
								passbookform.setTxt_intrate(String.valueOf(loanRepObj.getLoanIntRate()));
								passbookform.setTxt_intpaidupto(loanRepObj.getIntUptoDate());
								passbookform.setTxt_Sanctiondate(loanRepObj.getDisbursementDate());
								passbookform.setTxt_nominee_no(String.valueOf(loanRepObj.getNomRegNo()));
								passbookform.setTxt_sanctionammt(String.valueOf(loanRepObj.getSanctionedAmount()));
								passbookform.setTxt_phoneno(String.valueOf(loanRepObj.getPhoneNo()));
								passbookform.setTxt_noofInstallment(String.valueOf(loanRepObj.getNoInstallments()));
								passbookform.setTxt_email(loanRepObj.getEmail());
								passbookform.setTxt_Installmentamt(String.valueOf(loanRepObj.getInstallmentAmount()));
								passbookform.setTxt_suritydetail(loanRepObj.getSurityDetails());
						
								ltrn=delidate.getLoanTransaction(passbookform.getTxt_loancatAccno(),passbookform.getTxt_accnum(),"Recovery");
								if(ltrn!=null)
								{
									for(int i=0;i<ltrn.length;i++)
									{
										treemap=new TreeMap();
										treemap.put("TranDate",ltrn[i].getTransactionDate());
										//treemap.put("TranType",ltrn[i].getTranType());
										if(ltrn[i].getTranType().equals("D"))
										{
											if(ltrn[i].getTranMode().equals("C"))
												treemap.put("Disp: Csh",ltrn[i].getReferenceNo());
											else if(ltrn[i].getTranMode().equals("T"))
												treemap.put("Disp: Csh",ltrn[i].getTranNarration());
											else if(ltrn[i].getTranMode().equals("P"))  
												treemap.put("Disp: Csh",ltrn[i].getTranNarration());
									
											treemap.put("Debit",ltrn[i].getTransactionAmount());
											treemap.put("Credit",new String(""));
											treemap.put("LoanBal",ltrn[i].getLoanBalance());
											list.add(treemap);
										}
										else if(ltrn[i].getTranType().equals("O"))
										{
											treemap.put("Disp: Csh",ltrn[i].getTranNarration());
											treemap.put("Debit",ltrn[i].getTransactionAmount());
											treemap.put("Credit",new String(""));
											treemap.put("LoanBal",ltrn[i].getLoanBalance());
											list.add(treemap);
										}
										else if(ltrn[i].getTranType().equals("R"))
										{
											treemap.put("LoanBal",ltrn[i].getLoanBalance());
											if(ltrn[i].getPrincipalPaid()!=0)
											{
										
												if(ltrn[i].getTranMode().equals("C"))
													treemap.put("Prin:Csh",ltrn[i].getReferenceNo());
												else if(ltrn[i].getTranMode().equals("T"))
													treemap.put("Prin:Trf",ltrn[i].getTranNarration());	
											
												else if(ltrn[i].getTranMode().equals("G"))
													treemap.put("Prin:",ltrn[i].getTranNarration());
										
													treemap.put("Debit",new String(""));
													treemap.put("Credit",ltrn[i].getPrincipalPaid());
													list.add(treemap);
											}
									
											if(ltrn[i].getInterestPaid()!=0 || ltrn[i].getExtraIntAmount() != 0)
											{
												if(ltrn[i].getTranMode().equals("C"))
													treemap.put("Csh",ltrn[i].getReferenceNo()+"Int Upto Date:"+ltrn[i].getIntUptoDate());
												//data[1]="Int: Csh "+ltrn[i].getReferenceNo()+"Int Upto Date:"+ltrn[i].getIntUptoDate();
												else if(ltrn[i].getTranMode().equals("T"))
													treemap.put("Trf",ltrn[i].getTranNarration()+"      "+"Int Upto Date:"+ltrn[i].getIntUptoDate());
												else if(ltrn[i].getTranMode().equals("G"))
													treemap.put("Int",ltrn[i].getTranNarration()+"      "+"Int Upto Date:"+ltrn[i].getIntUptoDate());
											
										
												treemap.put("Debit",new String(""));
												treemap.put("Credit",(ltrn[i].getInterestPaid() + ltrn[i].getExtraIntAmount()));
												//data[3]=DoubleFormat.toString(ltrn[i].getInterestPaid() + ltrn[i].getExtraIntAmount());
												list.add(treemap);
											}
											if(ltrn[i].getPenaltyAmount()!=0)
											{
												//data[0]="";  // Code changed by Murugesh on 20/1/2006
												if(ltrn[i].getTranMode().equals("C"))
													treemap.put("Csh",ltrn[i].getReferenceNo()+"Int Upto Date:"+ltrn[i].getIntUptoDate());
												//data[1]="P.Int: Csh "+ltrn[i].getReferenceNo()+"      "+"Int Upto Date:"+ltrn[i].getIntUptoDate();
												else if(ltrn[i].getTranMode().equals("T"))
													treemap.put("Trf",ltrn[i].getTranNarration()+"Int Upto Date:"+ltrn[i].getIntUptoDate());
												//data[1]="P.Int: Trf "+ltrn[i].getTranNarration()+"      "+"Int Upto Date:"+ltrn[i].getIntUptoDate();	
												else if(ltrn[i].getTranMode().equals("G"))
													treemap.put("Int",ltrn[i].getTranNarration()+"Int Upto Date:"+ltrn[i].getIntUptoDate());	
												//data[1]="P.Int: "+ltrn[i].getTranNarration()+"      "+"Int Upto Date:"+ltrn[i].getIntUptoDate();
										
												treemap.put("Debit",new String(""));
												treemap.put("Credit",ltrn[i].getPenaltyAmount());
										
											}
											if(ltrn[i].getOtherAmount()!=0)
											{
										
												if(ltrn[i].getTranMode().equals("C"))
													treemap.put("Csh",ltrn[i].getReferenceNo());
											
												else if(ltrn[i].getTranMode().equals("T"))
													treemap.put("Trf",ltrn[i].getTranNarration());
												
												else if(ltrn[i].getTranMode().equals("G"))
													treemap.put("OthChg: ",ltrn[i].getTranNarration());
										
										
													treemap.put("Debit",new String(""));
													treemap.put("Credit",ltrn[i].getOtherAmount());
													list.add(treemap);
											
											}

									
										}
										list.add(treemap);
								
									}
									req.setAttribute("list",list);
								}
						
							}
							else
							{
								passbookform.setValidate_accnum("AccountNotFound");
							}
						}
						}
					}
						//LOAN ACCOUNT NUMBER NOT NULL
					else
					{
						passbookform.setTxt_name("");
						passbookform.setTxt_SBNo("");
						passbookform.setTxt_purpose("");
						passbookform.setTxt_sharenum("");
						passbookform.setTxt_intrate("");
						passbookform.setTxt_intpaidupto("");
						passbookform.setTxt_Sanctiondate("");
						passbookform.setTxt_nominee_no("");
						passbookform.setTxt_sanctionammt("");
						passbookform.setTxt_phoneno("");
						passbookform.setTxt_noofInstallment("");
						passbookform.setTxt_email("");
						passbookform.setTxt_Installmentamt("");
						passbookform.setTxt_suritydetail("");

					}
					if(MenuNameReader.containsKeyScreen(passbookform.getPageidentity().getPageId()))
					{ 
						path=MenuNameReader.getScreenProperties(passbookform.getPageidentity().getPageId());
						System.out.println("path=======>"+path);
						req.setAttribute("pageId",path);
						return map.findForward(ResultHelp.getSuccess()); 
					}
					else
					{
						path=MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Path doest exit",req,path);
						return map.findForward(ResultHelp.getError());
					}
				}catch (Exception e) {
					e.printStackTrace();
					String path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(""+e,req,path);
					return map.findForward(ResultHelp.getError());
				}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/NPAProcessMenu"))
		{
			try{
				NPAProcessForm npaprocessform=(NPAProcessForm)form;
				LoansDelegate deligate=new LoansDelegate();
				npaprocessform.setSysdate(LoansDelegate.getSysDate());
				String path=null;
				req.setAttribute("LoanType",deligate.getMainModules(2,"1010000,1014000,1015000"));
				if(MenuNameReader.containsKeyScreen(npaprocessform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(npaprocessform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/NPAProcess"))
		{
			System.out.println("hi am inside the NPAProcess");
			try{
				NPAProcessForm npaprocessform=(NPAProcessForm)form;
				LoansDelegate deligate=new LoansDelegate();
				npaprocessform.setSysdate(LoansDelegate.getSysDate());
				String path=null;
				req.setAttribute("LoanType",deligate.getMainModules(2,"1010000,1014000,1015000"));
				
				if(npaprocessform.getButton_val()!=null)
				{
					if(npaprocessform.getButton_val().equalsIgnoreCase("Process"))
					{
						System.out.println("************Inside Process Button**************");
						System.out.println("Checkbox_Intrestwise()=====>"+npaprocessform.getCheckbox_Intrestwise());
						System.out.println("Checkbox_prinwise()=====>"+ npaprocessform.getCheckbox_prinwise());
						System.out.println("Radio_180()=====>"+ npaprocessform.getRadio_180());
						System.out.println("getRadio_90()=====>"+ npaprocessform.getRadio_90());
						String  i="0--";
						System.out.println("=====Int Val=====>"+npaprocessform.getCheckbox_prinwise());
						if(npaprocessform.getCheckbox_prinwise()!=null && npaprocessform.getCheckbox_Intrestwise()!=null && npaprocessform.getCheckbox_InOperativewise()!=null){
						if(npaprocessform.getCheckbox_prinwise().trim().equals("on") && npaprocessform.getCheckbox_Intrestwise().trim().equalsIgnoreCase("on") && npaprocessform.getCheckbox_InOperativewise().trim().equalsIgnoreCase("on"))
						{	
							System.out.println("***********1************");
							i="PIO";
						}	
						}
						 if(npaprocessform.getCheckbox_prinwise()!=null && npaprocessform.getCheckbox_Intrestwise()!=null){
						 if(npaprocessform.getCheckbox_prinwise().trim().equalsIgnoreCase("on") && npaprocessform.getCheckbox_Intrestwise().trim().equalsIgnoreCase("on"))
						{
							System.out.println("***********2************");
							i="PI";
						}
						 }
						 if(npaprocessform.getCheckbox_prinwise()!=null && npaprocessform.getCheckbox_InOperativewise()!=null){
						if(npaprocessform.getCheckbox_prinwise().trim().equalsIgnoreCase("on") && npaprocessform.getCheckbox_InOperativewise().trim().equalsIgnoreCase("on"))
						{
							System.out.println("***********3************");
							i="PO";
						}
						 }
						 if(npaprocessform.getCheckbox_Intrestwise()!=null && npaprocessform.getCheckbox_InOperativewise()!=null){
						if(npaprocessform.getCheckbox_Intrestwise().trim().equalsIgnoreCase("on") && npaprocessform.getCheckbox_InOperativewise().trim().equalsIgnoreCase("on"))
						{
							System.out.println("*************4**********");
							i="IO";
						}
						 }
						if(npaprocessform.getCheckbox_prinwise()!=null){
						if(npaprocessform.getCheckbox_prinwise().trim().toString().equalsIgnoreCase("on"))
						{
							System.out.println("************5***********");
							i="P";
						}
						}
						// 
						if(npaprocessform.getCheckbox_Intrestwise()!=null){
						if(npaprocessform.getCheckbox_Intrestwise().trim().equalsIgnoreCase("on"))
						{
							System.out.println("************6***********");
							i="I";
						}
						}
						 if(npaprocessform.getCheckbox_InOperativewise()!=null){
						if(npaprocessform.getCheckbox_InOperativewise().trim().equalsIgnoreCase("on"))
						{
							System.out.println("************7***********");
							i="O";			
						}
						 }
						System.out.println("i value===>"+i);
						int days = 180;  
						if(npaprocessform.getRadio_90()=="true")
							days = 90;
						try{		
							if(npaprocessform.getTxt_loanacctype().equalsIgnoreCase("Selected"))
							{
								System.out.println("i value===>"+i+"days===>"+days+"loan typre=======>"+npaprocessform.getTxt_loantype()); 
								if(npaprocessform.getTxt_loantype().startsWith("1014") || npaprocessform.getTxt_loantype().startsWith("1015"))
									deligate.ODCCNPAProcessing(npaprocessform.getTxt_processdate(),i,days,Integer.parseInt(npaprocessform.getTxt_loantype()),Integer.parseInt(npaprocessform.getTxt_startno()),Integer.parseInt(npaprocessform.getTxt_endNo()),"lnuser","LN01");
								else
									deligate.newstartNPAProcessing(npaprocessform.getTxt_processdate(),i,days,Integer.parseInt(npaprocessform.getTxt_loantype()),Integer.parseInt(npaprocessform.getTxt_startno()),Integer.parseInt(npaprocessform.getTxt_endNo()),"lnuser","LN01");
							}
							else 
							{				    
							   	//loanremote.startNPAProcessing(txt_npa_date.getText(),i,days,0,0,0,MainScreen.head.getUid(),MainScreen.head.getTml());
								deligate.newstartNPAProcessing(npaprocessform.getTxt_processdate(),i,days,0,0,0,"lnuser","LN01");
								deligate.ODCCNPAProcessing(npaprocessform.getTxt_processdate(),i,days,0,0,0,"lnuser","LN01");
							}
						}catch(Exception start){
							start.printStackTrace();
						}
						
						
						
					}  
				}
				
				if(MenuNameReader.containsKeyScreen(npaprocessform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(npaprocessform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				
			}catch (Exception e) {
				e.printStackTrace();
				   String path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/NPAAdminMenu"))
		{
			 System.out.println("********/Loans/NPAAdminMenu***********"); 
			 String path=null;
			 try{
				LoanNpaAdminForm npaadminform=(LoanNpaAdminForm)form;
				LoansDelegate deligate=new LoansDelegate();
				npaadminform.setValid_values("null");
				req.setAttribute("laon_moduleobj",deligate.getMainModules(2,"1010000, 1008000, 1014000, 1015000"));
				if(MenuNameReader.containsKeyScreen(npaadminform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(npaadminform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				 
			 }catch (Exception e) {
				 e.printStackTrace();
				    path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/NPAAdmin"))
		{
			 System.out.println("********/Loans/NPAAdminMenu***********"); 
			 String path=null;
			 try{
				LoanNpaAdminForm npaadminform=(LoanNpaAdminForm)form;
				LoansDelegate deligate=new LoansDelegate();
				req.setAttribute("laon_moduleobj",deligate.getMainModules(2,"1010000, 1008000, 1014000, 1015000"));
				npaadminform.setValid_values("null");
				if(npaadminform.getTxt_acctype()!=null)
				{
					setNPAValues(npaadminform.getTxt_acctype(),deligate,npaadminform,req) ;
					
				}
				System.out.println("button value====>"+npaadminform.getBut_val());
				if(npaadminform.getBut_val()!=null)
				{	
					System.out.println("=============:-)==============");
					if(npaadminform.getBut_val().equalsIgnoreCase("submit"))
					{ 
						System.out.println(":-)");
						//getSubmitData
						NPAObject obj[]=getSubmitData(npaadminform);
						boolean valu=deligate.setNPAAdminValues(obj,Integer.valueOf(npaadminform.getTxt_tab180()));
						System.out.println("NPA Values=====>"+valu); 
						if(valu==true)
							npaadminform.setNpa_res("npa_success");
						
					}	
					
				}
				if(MenuNameReader.containsKeyScreen(npaadminform.getPageidentity().getPageId()))
				 { 
					 path=MenuNameReader.getScreenProperties(npaadminform.getPageidentity().getPageId());
					 System.out.println("path=======>"+path);
					 req.setAttribute("pageId",path);
					 return map.findForward(ResultHelp.getSuccess()); 
				 }
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Path doest exit",req,path);
					return map.findForward(ResultHelp.getError());
				}
				 
			 }catch (Exception e) {
				 e.printStackTrace();
				    path=MenuNameReader.getScreenProperties("0000");
		           setErrorPageElements(""+e,req,path);
		           return map.findForward(ResultHelp.getError());
			}
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/NPALoanSummaryMenu"))
		{
			try{
				System.out.println("**********Inside NPA laon Summary***************");
				NPASummaryForm npaform = (NPASummaryForm)form;
				LoansDelegate delegate = new LoansDelegate(); 
				System.out.println("Page id===========>"+npaform.getPageidentity().getPageId());
				if(MenuNameReader.containsKeyScreen(npaform.getPageidentity().getPageId()))
				{
					String	path = MenuNameReader.getScreenProperties(npaform.getPageidentity().getPageId());
					System.out.println("path============>"+path);
					int days=npaform.getNum_days(); 
					System.out.println("day==========>=====>"+days);
					NPASummaryInitalForm(req,delegate,path,180);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
					return map.findForward(ResultHelp.getError());
			}catch(Exception e){e.printStackTrace();}	
		}else if(map.getPath().equalsIgnoreCase("/Loans/NPALoanSummary"))
		{
			try
			{
				LoansDelegate delegate = new LoansDelegate();
				String button_value=null;
				NPASummaryForm npaform = (NPASummaryForm)form;
				if(MenuNameReader.containsKeyScreen(npaform.getPageidentity().getPageId()))
				{
					String path = MenuNameReader.getScreenProperties(npaform.getPageidentity().getPageId());
					int days=npaform.getNum_days();
					NPASummaryInitalForm(req,delegate,path,days);
					button_value  = npaform.getForward();
					System.out.println("Button===>"+button_value);
					int k=1;
					
					if(npaform.getForward()!=null)
					{
						if(npaform.getForward().equalsIgnoreCase("View"))
						{ 
							System.out.println("**********View************"+npaform.getNum_days());
							if(npaform.getNum_days()!=0)
							{	
								array_loanreportobject=delegate.getNPASummary(npaform.getProcessdate(),npaform.getNum_days());
								showForm(req);
							}	
						}
						if (npaform.getForward().equalsIgnoreCase("download")) {
									System.out.println("I am in download=======");
									double balance_os_amt=0,amt_odue=0,npa_amt=0,total_prov_amt=0;
									if(npaform.getNum_days()!=0)
											{	
											array_loanreportobject=delegate.getNPASummary(npaform.getProcessdate(),npaform.getNum_days());
														showForm(req);
														}	
							
							if (array_loanreportobject == null) {
								npaform.setTesting("Cannot Print");
							} else {
								System.out.println(" i am inside down load");

								// TO save to an excel Sheet
								res.setContentType("application/.csv");
								res.setHeader("Content-disposition","attachment;filename=NPALoanSummaryReport.csv");
								java.io.PrintWriter out = res.getWriter();
								out.print("\n");
								out.print("\n");
								out.print("\n");
								out.print(",");
								out.print(",");
								out.print(",");
									/*out.print("InterestAccured Details for A/C Type: "
											+ array_loanreportobject[0].getAccType());*/
								out.print("\n");
								out.print("SrlNo");
								out.print(",");
								out.print("LoanType");
								out.print(",");
								out.print(" No.OfAccounts");
								out.print(",");
								out.print("Balance O/S");
								out.print(",");
								out.print("AmountOD");
								out.print(",");
								out.print("AssetCode");
								out.print(",");
								out.print("AssetDesc");
								out.print(",");
								out.print("No.OfAcc'sPerAssetCode");
								out.print(",");
								out.print("BalAmount");
								out.print(",");
								out.print("ProvPer(%)");
								out.print(",");
								out.print("ProvAmount");
								out.print(",");
								out.print("TotalProvision");
								out.print(",");
								out.print("");
								out.print("\n");

								for (int i = 0; i < array_loanreportobject.length; i++) 
								{
									for(int j=0;j<array_loanreportobject[i].length;j++)
									{
										if(j==0){
									out.print(i+1);//SrlNo
									out.print(",");
									out.print(array_loanreportobject[i][j].getName());//LoanType
									out.print(",");
									out.print(array_loanreportobject[i][j].getAccNo());//No.ofAcct's
									out.print(",");
									out.print(array_loanreportobject[i][j].getMaturityAmt());//BalanceO/S
									out.print(",");
									out.print(array_loanreportobject[i][j].getPrincipalPaid());//AmountOD
									out.print(",");
										}
										out.print(array_loanreportobject[i][j].getDepositAccNo());//AssetCode
										out.print(",");
									out.print(array_loanreportobject[i][j].getAccType());//No.OfInst)
									out.print(",");
									out.print(array_loanreportobject[i][j].getDepositDays());//InstAml
									out.print(",");
									out.print(array_loanreportobject[i][j].getLoanBalance());//InstAml
									out.print(",");
									out.print(array_loanreportobject[i][j].getDepositIntRate());//InstAml
									out.print(",");
									out.print(array_loanreportobject[i][j].getLoanIntRate());//InstAml
									out.print(",");
									if(j==array_loanreportobject[i].length-1)
										out.print(array_loanreportobject[i][j].getDepositAmt());
									out.print(",");
									out.print("\n");
									if(array_loanreportobject[i][j].getDepositAccNo() != 1) 
									{
										npa_amt += 	array_loanreportobject[i][j].getLoanBalance();
									}
										//}
								}}
								req.setAttribute("msg","Saved to excel file in C:");
								return null;

							}
						}
					}
				}	
			}catch (Exception e) {
	              e.printStackTrace();
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/NPAReportMenu"))
		{
		  try{
			  NPAReportForm npareport = (NPAReportForm)form;
			  LoansDelegate delegate = new LoansDelegate();
			  if(MenuNameReader.containsKeyScreen(npareport.getPageidentity().getPageId()))
			  {
				  String path1 = MenuNameReader.getScreenProperties(npareport.getPageidentity().getPageId());
				  System.out.println("path===========>"+path1); 
				  NPAReportsInitialParam(req,delegate,180);
				  req.setAttribute("pageId","/NPAReports.jsp"); 
				  return map.findForward(ResultHelp.getSuccess());
			  }else
			  {   
					return map.findForward(ResultHelp.getError());
			  }	
		  }catch(Exception e){e.printStackTrace();}
		}else if(map.getPath().equalsIgnoreCase("/Loans/NPAReport"))
		 {
			try{
			 NPAReportForm npareport = (NPAReportForm)form;
			 LoansDelegate delegate = new LoansDelegate();
			 String ac_type=null,asset_code=null,process_date=null,table_type=null;
			 LoanReportObject loanreport[] = null;
			 int from_acno=0,to_acno=0;
			 String button_value=npareport.getForward(); 
			 System.out.println("Button value=============>"+button_value);
			 
			 if(npareport.getForward()!=null && npareport.getForward().equalsIgnoreCase("View") )
			 {
			 
			 process_date = npareport.getNpadate();
			 System.out.println("Account type===========>"+npareport.getAcctype());
			
			 if( npareport.getAcctype()!= null && npareport.getAcctype().equalsIgnoreCase("All Types"))
			 { 
				 System.out.println("********Inside ALL decent vinny*********"+npareport.getNpadate());
				 System.out.println("Table type====>"+npareport.getTabletype()); 
				 System.out.println();
				 loanreport = delegate.getNPAReport(npareport.getNpadate(),String.valueOf(npareport.getTabletype()),"-1","ALL",-1,-1);
				 System.out.println("Loan Report Object======>===>"+loanreport); 
				 display_table(loanreport,req);
				 
				       
			 } 
			 else 
			 {	
				 System.out.println("*****************Let me test*****************"+ npareport.getAcctype()+ npareport.getAssettype());
				 loanreport = delegate.getNPAReport(npareport.getNpadate(),String.valueOf(npareport.getTabletype()),"1",npareport.getAcctype(),npareport.getFrom_accno(),npareport.getTo_accno());
				 System.out.println("Loan Report Object In Else Condition=====>"+loanreport); 
			 	 display_table(loanreport,req);
			 }
			}
				if (npareport.getForward().equalsIgnoreCase("download")) {
					 if( npareport.getAcctype()!= null && npareport.getAcctype().equalsIgnoreCase("All Types"))
					 { 
						 System.out.println("********Inside ALL decent vinny*********"+npareport.getNpadate());
						 System.out.println("Table type====>"+npareport.getTabletype()); 
						 System.out.println();
						 loanreport = delegate.getNPAReport(npareport.getNpadate(),String.valueOf(npareport.getTabletype()),"-1","ALL",-1,-1);
						 System.out.println("Loan Report Object======>===>"+loanreport); 
						 display_table(loanreport,req);
						 
						       
					 } 
					 else 
					 {	
						 System.out.println("*****************Let me test*****************"+ npareport.getAcctype()+ npareport.getAssettype());
						 loanreport = delegate.getNPAReport(npareport.getNpadate(),String.valueOf(npareport.getTabletype()),"1",npareport.getAcctype(),npareport.getFrom_accno(),npareport.getTo_accno());
						 System.out.println("Loan Report Object In Else Condition=====>"+loanreport); 
					 	 display_table(loanreport,req);
					 }
					System.out.println("I am in download=======");
					
					
					if (loanreport == null) {
						npareport.setTesting("Cannot Print");
					} else {
						System.out.println(" i am inside down load");

						// TO save to an excel Sheet
						res.setContentType("application/.csv");
						res.setHeader("Content-disposition","attachment;filename=NPAReport.csv");
						java.io.PrintWriter out = res.getWriter();
						out.print("\n");
						out.print("\n");
						out.print("\n");
						out.print(",");
						out.print(",");
						out.print(",");
							out.print("NPAReport Details for A/C Type: "
									+ loanreport[0].getAccType());
						out.print("\n");
						out.print("SrlNo");
						out.print(",");
						out.print("Ac/No");
						out.print(",");
						out.print(" Name");
						out.print(",");
						out.print("Sanctioned Amount");
						out.print(",");
						out.print("DisbursementDate");
						out.print(",");
						out.print("No.ofInstallments");
						out.print(",");
						out.print("InstallmentAmt");
						out.print(",");
						out.print("Recovery");
						out.print(",");
						out.print("RecAmount");
						out.print(",");
						out.print("DisbursementAmt");
						out.print(",");
						out.print("Principal");
						out.print(",");
						out.print("IntUptoDate");
						out.print(",");
						out.print("IntOverDueAmt");
						out.print(",");
						out.print("OverDuePeriod");
						out.print(",");
						out.print("LastTransactionDate");
						out.print(",");
						out.print("NPAsinceDate");
						out.print(",");
						out.print("NPATowards");
						out.print(",");
						out.print("LoanBalance");
						out.print(",");
						out.print("Provision");
						out.print(",");
						out.print("");
						out.print("\n");

						for (int k = 0; k < loanreport.length; k++) {
							out.print(k+1);//SrlNo
							out.print(",");
							out.print(loanreport[k].getAccNo());//ac_num
							out.print(",");
							out.print(loanreport[k].getName());//name
							out.print(",");
							out.print(loanreport[k].getSanctionedAmount());//Sanctioned
							out.print(",");
							out.print(loanreport[k].getDisbursementDate());//Disbursedate
							out.print(",");
							out.print(loanreport[k].getNoInstallments());//No.OfInst
							out.print(",");
							out.print(loanreport[k].getInstallmentAmount());//InstAml
							out.print(",");
							if(loanreport[k].getInterestType()==0)//Recovery
								out.print("EMI");
							else
								out.print("RB");
							out.print(",");
							out.print(loanreport[k].getDisburseAmount());//RecAmount
							out.print(",");
							out.print(loanreport[k].getDisburseAmount());//DisburseAmt
							out.print(",");
							out.print(loanreport[k].getPrnOverDueAmt());//Principal
							out.print(",");
							out.print(loanreport[k].getIntUptoDate());//IntUptoDate
							out.print(",");
							out.print(loanreport[k].getIntOverDueAmt());//IntOverDueAmt
							out.print(",");
							out.print(loanreport[k].getOverdueperiod());//OverDuePeriod
							out.print(",");
							out.print(loanreport[k].getTransactiondate());//LastTranDate
							out.print(",");
							out.print(loanreport[k].getNpasincedate());//NPAsinceDate
							out.print(",");
							out.print(loanreport[k].getNpatowards());//NPAtowardsDate
							out.print(",");
							out.print(loanreport[k].getLoanBalance());//Loanbalance
							out.print(",");
							out.print(loanreport[k].getProvisionReq());//provision
							out.print(",");
							out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
						return null;

					}
				}
			 
			 if(MenuNameReader.containsKeyScreen(npareport.getPageidentity().getPageId()))
			  {
				  String path = MenuNameReader.getScreenProperties(npareport.getPageidentity().getPageId());
				  NPAReportsInitialParam(req,delegate,180);
				  req.setAttribute("pageId",path);
				  return map.findForward(ResultHelp.getSuccess());
			  }else
					return map.findForward(ResultHelp.getError());
			}catch(Exception e){e.printStackTrace();}
		 }
		
		else if(map.getPath().equalsIgnoreCase("/Loans/AutomaticRecoveryMenu"))
		{
			System.out.println("*************/Loans/AutomaticRecoveryMenu***********");
			try{
				  AutomaticRecoveryForm autoform = (AutomaticRecoveryForm)form;
				  LoansDelegate delegate = new LoansDelegate();
				  if(MenuNameReader.containsKeyScreen(autoform.getPageidentity().getPageId()))
				  {
					  String path1 = MenuNameReader.getScreenProperties(autoform.getPageidentity().getPageId());
					  System.out.println("path===========>"+path1); 
					  req.setAttribute("pageId","/AutomaticRecovery.jsp");
					  return map.findForward(ResultHelp.getSuccess());
				  }
				  else
				  {   
						return map.findForward(ResultHelp.getError());
				  }	
			  }catch(Exception e){e.printStackTrace();}
			
		}
		else if(map.getPath().equalsIgnoreCase("/Loans/AutomaticRecovery"))
		{
			System.out.println("*************/Loans/AutomaticRecovery***********");
			try{
				  AutomaticRecoveryForm autoform = (AutomaticRecoveryForm)form;
				  LoansDelegate delegate = new LoansDelegate();
				  if(MenuNameReader.containsKeyScreen(autoform.getPageidentity().getPageId()))
				  {
					  String path1 = MenuNameReader.getScreenProperties(autoform.getPageidentity().getPageId());
					  System.out.println("path===========>"+path1);
					  int auto_result=delegate.loanRecovery("LN", "lntml", delegate.getSysDate(), delegate.getSysDateTime());
					  if(auto_result==0)
					  {
						  req.setAttribute("msg","The recovery is done automatically");
					  }
					  return map.findForward(ResultHelp.getSuccess());
				  }
				  else
				  {   
						return map.findForward(ResultHelp.getError());
				  }	
			  }catch(Exception e){e.printStackTrace();}
			
		}
		
		else if(map.getPath().equalsIgnoreCase("/Loans/QuarterlyInterestCalcMenu"))
		{	
			String dates[]=null;
			System.out.println("m in try=================>>>>0");
			System.out.println("*************/Loans/QuarterlyInterestCalcMenu***********");
			try{
				System.out.println("m in try=================>>>>1");
				LoansQuarterlyInterestForm loanquarterform = (LoansQuarterlyInterestForm)form;
				System.out.println("m in try=================>>>>2");
				  LoansDelegate delegate = new LoansDelegate();
				  System.out.println("m in try=================>>>>3");
				  System.out.println("m in try=================>>>>"+loanquarterform.getPageidentity().getPageId());
				  if(MenuNameReader.containsKeyScreen(loanquarterform.getPageidentity().getPageId()))
				  {
					  System.out.println("m in try=================>>>>4");
					  String path1 = MenuNameReader.getScreenProperties(loanquarterform.getPageidentity().getPageId());
					  System.out.println("m in try=================>>>>5");
					  System.out.println("path===========>"+path1); 
					  dates=delegate.getQuaterlydates();
						if(dates!=null){
						for(int i=0;i<dates.length;i++){
							//combo_date.addItem(dates[i].toString());
							System.out.println("*********Dates***********" + dates[i]);
							req.setAttribute("QuaterlyDates", dates);
						}
					  req.setAttribute("pageId","/LoanQuarterlyInterest.jsp");
					  return map.findForward(ResultHelp.getSuccess());
				  }
				  else
				  {   
						return map.findForward(ResultHelp.getError());
				  }	
			  }
			}
				  catch(Exception e){e.printStackTrace();}
			
		}

		else if(map.getPath().equalsIgnoreCase("/Loans/QuarterlyInterestCalc"))
		{	String dates[]=null;
		System.out.println("m in try=================>>>>0");
			System.out.println("*************/Loans/QuarterlyInterestCalc77777777777777***********");
			try{
				System.out.println("m in try=================>>>>1");
				LoansQuarterlyInterestForm loanquarterform = (LoansQuarterlyInterestForm)form;
				System.out.println("m in try=================>>>>2");
				  LoansDelegate delegate = new LoansDelegate();
				  System.out.println("m in try=================>>>>3");
				  if(MenuNameReader.containsKeyScreen(loanquarterform.getPageidentity().getPageId()))
				  {
					  System.out.println("m in try=================>>>>4");
					  String path1 = MenuNameReader.getScreenProperties(loanquarterform.getPageidentity().getPageId());
					  System.out.println("path===========>"+path1);
					  Object array_object_data[]=null;
					   List dtm=new ArrayList();
					  	dates=delegate.getQuaterlydates();
						if(dates!=null){
						for(int i=0;i<dates.length;i++){
							//combo_date.addItem(dates[i].toString());
							System.out.println("*********Dates***********" + dates[i]);
							req.setAttribute("QuaterlyDates", dates);
						}
						}
							
							if(loanquarterform.getForward().equalsIgnoreCase("InterestCalculation"))
							{
								ltrn=delegate.calc_int(delegate.getSysDate());
								if(ltrn!=null){						        	 
						        	 for(int i=0;i<ltrn.length;i++){						        	
						        		/*array_object_data=new Object[6];	
							        	System.out.println("The array object is=====>"+array_object_data);
							        	array_object_data[0]="  "+String.valueOf(i+1);
							            array_object_data[1]=ltrn[i].getAccType();
							            array_object_data[2]=String.valueOf(ltrn[i].getAccNo());
							            array_object_data[3]=String.valueOf(ltrn[i].getName());
							            array_object_data[4]=String.valueOf(ltrn[i].getInterestPayable());
							            array_object_data[5]=String.valueOf(ltrn[i].getPrincipalBalance());
							            //tot_val+=ltrn[i].getInterestPayable();
							               
							            dtm.add(array_object_data);*/
							            System.out.println("The accno is------> "+ltrn[i].getAccNo());
						        	 	}
						        	 }
								//req.setAttribute("objectdata", dtm);
								
								
								 req.setAttribute("pageId",path1);
								 return map.findForward(ResultHelp.getSuccess());
							}
							
						
						
							if(loanquarterform.getForward().equalsIgnoreCase("View") || loanquarterform.getForward().equalsIgnoreCase("File") )
							{	
								System.out.println("************View****************");
								System.out.println("loanquarterform.getQuarterdates()===>>"+loanquarterform.getQuarterdates());
								ltrn=delegate.getQuaterlyIntDetails(loanquarterform.getQuarterdates());
								System.out.println("The length of ln trn is"+ltrn.length);
								
								int tot_val=0;
								if(ltrn != null && ltrn.length>0)
							    {
							    	System.out.println("******************************************");
							    	System.out.println("The length is "+ltrn.length);
							    	System.out.println("The transaction is"+ltrn);
							        for(int int_no_of_rows=0;int_no_of_rows<ltrn.length;int_no_of_rows++)
							        {
							        	array_object_data=new Object[6];	
							        	System.out.println("The array object is=====>"+array_object_data);
							        	array_object_data[0]="  "+String.valueOf(int_no_of_rows+1);
							            array_object_data[1]=ltrn[int_no_of_rows].getAccType();
							            array_object_data[2]=String.valueOf(ltrn[int_no_of_rows].getAccNo());
							            array_object_data[3]=String.valueOf(ltrn[int_no_of_rows].getName());
							            array_object_data[4]=String.valueOf(ltrn[int_no_of_rows].getInterestPayable());
							            array_object_data[5]=String.valueOf(ltrn[int_no_of_rows].getPrincipalBalance());
							            tot_val+=ltrn[int_no_of_rows].getInterestPayable();
							               
							           dtm.add(array_object_data);
							           // double_total_interest_amount+= array_depositintrepobject[int_no_of_rows].getInterestAmt();
							        }
							        System.out.println("The total intrest amount is "+tot_val);
							        
							        
							        for(int k=0;k<1;k++){
							        	//data[0]="Total intrest Amount=="+tot_val;
							        }
							      }	
							   } 
							 if(loanquarterform.getForward().equalsIgnoreCase("File")){
								
								 System.out.println("i am inside File");
								 if(loanquarterform.getQuarterdates()!=null){
										ltrn=delegate.getQuaterlyIntDetails(loanquarterform.getQuarterdates());
								 }
									if(ltrn!=null)
									{
										try{
											
																	
											String name="Loan Quarterly Interest";
											HSSFWorkbook wb = new HSSFWorkbook();
											HSSFSheet sheet = wb.createSheet("Loan Quarterly Interest");
											HSSFRow row = sheet.createRow((short)0);
											/*HSSFCell cell = row.createCell((short)0);
				                       		   cell.setCellValue(1);*/
											row.createCell((short)1).setCellValue("Srl No");
											row.createCell((short)2).setCellValue("Account Type");
											row.createCell((short)3).setCellValue("Account Number");
											row.createCell((short)4).setCellValue("Name");
											row.createCell((short)5).setCellValue("Interest Amount");
											row.createCell((short)6).setCellValue("Sanctioned Amount");
											
											for(int i=0;i<ltrn.length;i++){
												System.out.println("how many times=====> "+i);
												HSSFRow row1 = sheet.createRow((short)i+1);
												
												row1.createCell((short)1).setCellValue(i+1);
												row1.createCell((short)2).setCellValue(ltrn[i].getAccType());
												row1.createCell((short)3).setCellValue(ltrn[i].getAccNo());
												
												row1.createCell((short)4).setCellValue(ltrn[i].getName());
												row1.createCell((short)5).setCellValue(ltrn[i].getInterestPayable());
												row1.createCell((short)6).setCellValue(ltrn[i].getPrincipalBalance());
												
											}								   
											FileOutputStream fileOut = new FileOutputStream("c:\\"+name+".xls");
											wb.write(fileOut);
											fileOut.close(); 
										}
										catch( Exception ex )
										{  
											ex.printStackTrace();
										}  
										req.setAttribute("msg","SuccessFullyCreated In C-Drive with File Name-Loan Quarterly Interest-File");
									}
								 
								 
							 
							   }
							//System.out.println("objectdata================>>"+array_object_data.length);
							req.setAttribute("objectdata", dtm);												
							
							 req.setAttribute("pageId",path1);
					  return map.findForward(ResultHelp.getSuccess());
					  
				  }
				  else
				  {   
						return map.findForward(ResultHelp.getError());
				  }	
			  }catch(Exception e){e.printStackTrace();}
			
		}
		if(map.getPath().equalsIgnoreCase("/Loans/DisbursementMenu"))
		{
		System.out.println("*********************/Loans/DisbursementMenu*************");
		try 
		{
			DisbursementForm disform = (DisbursementForm) form;
			LoansDelegate delegate = new LoansDelegate();
			System.out.println("PAGE I~D ==== >"+ disform.getPageidentity().getPageId());
			if(MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
			{
				try
				{
					String	path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
					req.setAttribute("pageId", path);
					IntialParameters(req, delegate);
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					disform.setAccno(0);
					disform.setShno(0);
					disform.setAmount(0.0);
					disform.setInstallment(0.0);
					disform.setIntrate(0.0);
					disform.setPenalrate(0.0);
					disform.setDisbleft(0.0);
					disform.setCombo_pay_mode("C");
					disform.setPayaccno(0);
					disform.setDisbamt(0.0);
					System.out.println("<=========COMING BACK TO ACTION CLASS =======>");
					return map.findForward(ResultHelp.getSuccess());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					return map.findForward(ResultHelp.getError());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

		 if(map.getPath().equalsIgnoreCase("/Loans/Disbursement"))
			{
				System.out.println("**************************Inside /Loans/Disbursement*************");
				try
				{
					DisbursementForm disform = (DisbursementForm) form;
					LoansDelegate delegate = new LoansDelegate();
					LoanTransactionObject tranobj = null;
					req.setAttribute("msg"," ");
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					System.out.println("PageId Loan Disbursement"+ disform.getPageidentity().getPageId());
					System.out.println("<=========COMING BACK TO ACTION CLASS DISBURSEMENT =======>");
					lmobj=delegate.getLoanMaster(disform.getAccno(), disform.getAcctype());
					req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
	        		req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
	        		//req.setAttribute("panelName", CommonPanelHeading.getPersonal());
					if(lmobj!=null)
					{
						System.out.println("inside loan master action ");
						if(disform.getAccno()!=0)
						{
						 	if(disform.getDetails().equalsIgnoreCase("0001")){
				        		req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
				        		if(lmobj.getCustomerId()!=0){
				        		req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
				        		req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				        		req.setAttribute("TabNum", "0");
				        		System.out.println("((((((((((((((LoanMasterObject)))))))))))" + lmobj.getApplicationSrlNo());
				        		disform.setAcctype(lmobj.getAccType());
				        		disform.setShtype(lmobj.getShareAccType());
				        		disform.setShno(lmobj.getShareAccNo());
				        		disform.setPurpose(String.valueOf(lmobj.getPurposeCode()));
				        		System.out.println("***********After the Loanmaster object**********");
				        		System.out.println("((((((((((((appform.getDetails()))))))))))" + disform.getDetails());
				        		}
				        	}
				        	else if (disform.getDetails().equalsIgnoreCase("5002")) {
				        		 System.out.println("+++++++++++++++Action class application form code ++++++++++++");
									System.out.println("appform.getDetails() ==========  "+ disform.getDetails());
									System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(disform.getDetails().trim()));
									req.setAttribute("Application", MenuNameReader.getScreenProperties("5002"));
									req.setAttribute("pay_mode", delegate.getPayMode());
									req.setAttribute("pay_actype", delegate.getpayAcctype());
									disform.setShtype(lmobj.getShareAccType());
									disform.setShno(lmobj.getShareAccNo());
									String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
									String[] dirrelations=delegate.getDirectorRelations();
									req.setAttribute("Dirrelations",dirrelations);
									System.out.println("***********Director details**************" + dirdetails.toString());
									System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
									req.setAttribute("DirDetails",dirdetails);
									req.setAttribute("flag", null);
									disform.setAppln_no(lmobj.getApplicationSrlNo());
									disform.setAppndate(lmobj.getApplicationDate());
									disform.setReqamount(lmobj.getRequiredAmount());
									disform.setDirrelations(lmobj.getRelative());
									//appform.setDirdetails(lmobj.getDirectorCode());
									disform.setPaymtmode(lmobj.getPayMode());
									disform.setPayactype(lmobj.getPaymentAcctype());
									disform.setPayaccno(lmobj.getPaymentAccno());
									disform.setInteresttype(lmobj.getInterestType());
									disform.setInterestcalctype(lmobj.getInterestRateType());
								}
				        	else if (disform.getDetails().equalsIgnoreCase("5005")) {
								System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
								System.out.println("appform.getDetails() ==========4");
								session=req.getSession();
								System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(disform.getDetails()));
								req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
								tabpane_employment(req, disform);
								req.setAttribute("TabNum", "0");
								IncomeObject[] incomeObjects=lmobj.getIncomeDetails();
								IncomeObject inObj=null;
								for(int i=0;i<incomeObjects.length;i++){
									inObj=(IncomeObject)Array.get(incomeObjects, i);
									if(inObj.getTypeOfIncome()!=null){
										disform.setShtype(lmobj.getShareAccType());
										disform.setShno(lmobj.getShareAccNo());
									if (inObj.getTypeOfIncome().equalsIgnoreCase("Self")) {
										req.setAttribute("TabNum", "0");
										session.setAttribute("Self", inObj);
									}else if (inObj.getTypeOfIncome().equalsIgnoreCase("Service")) {
										req.setAttribute("TabNum", "1");
										session.setAttribute("Service", inObj);
									} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Business")) {
										req.setAttribute("TabNum", "2");
										session.setAttribute("Business", inObj);
									} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Pension")) {
										req.setAttribute("TabNum", "3");
										session.setAttribute("Pension", inObj);
									} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Rent")) {
										req.setAttribute("TabNum", "4");
										session.setAttribute("Rent", inObj);
									}
									}
								}
							}
				        	else if (disform.getDetails().trim().equalsIgnoreCase("Signature Ins")) {
				        		CustomerMasterObject cust=null;
								System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
								signObject=delegate.getSignatureDetails(disform.getShno(), disform.getShtype());
								if(signObject!=null){
								if(signObject[0].getCid()!=0)
			      			   cust=delegate.getCustomer(signObject[0].getCid());
			          			req.setAttribute("cust",cust);
			          			req.setAttribute("sigObject",signObject);
			          			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
			          			path=MenuNameReader.getScreenProperties("5053");
			          			System.out.println("path is---------------"+path);
			          			req.setAttribute("flag","/SingnatureInst1.jsp"); 
								}else{
									req.setAttribute("msg","Account Number Not Found!!");
								}
							}
				        	else if (disform.getDetails().equalsIgnoreCase("Loan and Share Details")) {
								System.out.println("appform.getDetails() ==========8");
								req.setAttribute("flag", null);
								System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
								req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
								req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(disform.getShtype(),disform.getShno()));
								req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
								req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
								req.setAttribute("IndividualCoBorrower", null);
							}
				        	 else if (disform.getDetails().equalsIgnoreCase("5022")) {
									System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
									System.out.println("appform.getDetails() ==========2" + disform.getDetails());
									VehicleObject vehicleObject=null;
									req.setAttribute("flag", null);
									req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5003"));
									vehicleObject=lmobj.getVehicleDet();
									req.setAttribute("VECHOBJ", vehicleObject);
								}
				        	 else if (disform.getDetails().equalsIgnoreCase("5004")) {
									PropertyObject propertyObject=null;
									System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
									System.out.println("appform.getDetails() ==========3");
									req.setAttribute("flag", null);
									req.setAttribute("Property", MenuNameReader.getScreenProperties("5004"));
									propertyObject=lmobj.getPropertyDetails();
									req.setAttribute("PROPOBJ", propertyObject);
								}
				        	 else if (disform.getDetails().trim().equalsIgnoreCase("5093")) {
				        		 	Object[][] relatives=null;
				        		 	Object[][] indents=null;
				        		 	Object[][] dependents=null;
									System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
									System.out.println("appform.getDetails() ==========5");
									req.setAttribute("flag", null);
									System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
									req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties("5093"));
									relatives=lmobj.getRelatives();
									indents=lmobj.getInterests();
									dependents=lmobj.getDependents();
									req.setAttribute("RelObj", relatives);
									req.setAttribute("RelIndObj", indents);
									req.setAttribute("RelDepObj", dependents);
								}
				        	 else if(disform.getDetails().trim().equalsIgnoreCase("5027")) 
								  {
				        		 	Vector vector=null;
				        		 	String coborower=null;
				        		 	Iterator iterator=null;
				        		 	String ac_type=null;
				        		 	String ac_no=null;
				        		 	AccountObject[] array_accountObject=null;
				        		 	SignatureInstructionObject[] signObject2=null;
				        		  	System.out.println("*****************Inside to Suruties***************");
				        		  	req.setAttribute("flag", null);
				        		  	req.setAttribute("Surities",MenuNameReader.getScreenProperties("5027"));
									vector=lmobj.getSurities();
									iterator=vector.iterator();
									array_accountObject = new AccountObject[1];
									while(iterator.hasNext()){
										coborower=(String)iterator.next();
										System.out.println("coborrower vector values==>"+coborower);
										ac_type=coborower.substring(0, 7);
										System.out.println("ac_type values==>"+ac_type);
										ac_no=coborower.substring(8);
										System.out.println("ac_no values==>"+ac_no);
										if(ac_type!=null && ac_no != null&& ac_no.trim().length()!=0) {
											array_accountObject[0] = delegate.getAccount(null,ac_type, Integer.parseInt(ac_no));
															if(ac_no.trim().length()!=0 && array_accountObject[0]!=null){
																disform.setShno(array_accountObject[0].getAccno());
																signObject2=delegate.getSignatureDetails(Integer.parseInt(ac_no),ac_type);
																req.setAttribute("panelName", "Personal Details");
																req.setAttribute("personalInfo", delegate.getCustomer(signObject2[0].getCid()));
																req.setAttribute("TabNum", "0");
																System.out.println("Suraj is setting the tab number");
																req.setAttribute("flag",null);
															}
															req.setAttribute("ShareAccountObject",array_accountObject);
										}
									}
								}
				        	 else if (disform.getDetails().trim().equalsIgnoreCase("5032")) {
				        		 	Vector vector=null;
				        		 	String coborower=null;
				        		 	Iterator iterator=null;
				        		 	String ac_type=null;
				        		 	String ac_no=null;
				        		 	AccountObject[] array_accountObject=null;
				        		 	SignatureInstructionObject[] signObject2=null;
									System.out.println("appform.getDetails() ==========Co Borrower");
									System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5027"));
									req.setAttribute("flag", null);
									req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5027"));
									vector=lmobj.getCoBorrowers();
									iterator=vector.iterator();
									array_accountObject = new AccountObject[1];
									while(iterator.hasNext()){
										coborower=(String)iterator.next();
										System.out.println("coborrower vector values==>"+coborower);
										ac_type=coborower.substring(0, 7);
										System.out.println("ac_type values==>"+ac_type);
										ac_no=coborower.substring(8);
										System.out.println("ac_no values==>"+ac_no);
										if(ac_type!=null && ac_no != null&& ac_no.trim().length()!=0) {
											array_accountObject[0] = delegate.getAccount(null,ac_type, Integer.parseInt(ac_no));
															if(ac_no.trim().length()!=0 && array_accountObject[0]!=null){
																disform.setShno(array_accountObject[0].getAccno());
																signObject2=delegate.getSignatureDetails(Integer.parseInt(ac_no),ac_type);
																req.setAttribute("panelName", "Personal Details");
																req.setAttribute("personalInfo", delegate.getCustomer(signObject2[0].getCid()));
																req.setAttribute("TabNum", "0");
																System.out.println("Suraj is setting the tab number");
																req.setAttribute("flag",null);
															}
															req.setAttribute("ShareAccountObject",array_accountObject);
										}
									}
								}
				        	 else if (disform.getDetails().trim().equalsIgnoreCase("Surities")) {
				        		 GoldObject goldObject=null;	
				        		 System.out.println("appform.getDetails() ==========Gold");
				        		 disform.setShtype(lmobj.getShareAccType());
				        		 disform.setShno(lmobj.getShareAccNo());
									System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
									req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
									if(goldObject!=null){
										req.setAttribute("flag", null);
										goldObject=lmobj.getGoldDet();
										req.setAttribute("GoldDet", goldObject);
									}else{
										req.setAttribute("msg", "Gold Details are not found for this account");
									}
								}
						tranobj=delegate.getUnVerifiedDisbursement(disform.getAcctype(), disform.getAccno());
						if(tranobj==null)
						{
							System.out.println("inside loan Tran=====>"+lmobj.getShareAccNo());
							disform.setShno(lmobj.getShareAccNo());
							disform.setInstallment(lmobj.getInstallmentAmt());
							disform.setIntrate(lmobj.getInterestRate());
							disform.setPenalrate(lmobj.getPenalRate());
							//disform.setDisbleft(lmobj.getDisbursementLeft());
							disform.setCombo_pay_mode(lmobj.getPayMode());
							if(lmobj.isLoanSanctioned())
							{					
								disform.setAmount(lmobj.getSanctionedAmount());
								disform.setPeriod(lmobj.getNoOfInstallments());
								disform.setHoliday(lmobj.getHolidayPeriod());
								try
								{
									double disb_left=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo()); // Code added by Murugesh on 30/12/2005
									disform.setDisbleft(disb_left);
								}
								catch(Exception exce)
								{exce.printStackTrace();}
							}
							if(lmobj.isLoanSanctioned() &&  lmobj.isSanctionVerified())
							{
								if(lmobj.getPayMode().equalsIgnoreCase("T")) {
									
									disform.setPayactype(lmobj.getPaymentAcctype());
									disform.setPayaccno(lmobj.getPaymentAccno());
								}
								disform.setPaymtmode(lmobj.getPayMode());
					
								if(lmobj.getDisbursementLeft()==0)
								{
									req.setAttribute("msg","Amount sanctioned is already disbursed");
								}
							}	
						}
						else
						{
							req.setAttribute("msg","Disbursement Is Not Yet Verified");
						}
					}
					if(disform.getForward().equalsIgnoreCase("Disburse"))
					{
						double disbursement=(disform.getDisbamt());
						   try
						   {
						   	double disb_left_amt=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo()); // Code added by Murugesh on 30/12/2005
						   	System.out.println("--disb_left_amt->"+disb_left_amt);
						   	System.out.println("--lmobj.getModuleCode()---->"+lmobj.getModuleCode());
						   	if(disb_left_amt!=-1 && disbursement>disb_left_amt )
							{
						   		req.setAttribute("msg","Disbursing amount is higher**** than the Disbursement left");
						   		disform.setDisbamt(0.0);
							}
						   	else
						   	{
						   		System.out.println("Inside Else");
								data=null;
									if(lmobj.getInterestType()==1)
										data=calculateReducingInterest(lmobj.getSanctionedAmount(), lmobj.getInterestRate(), lmobj.getNoOfInstallments(),lmobj);
									else if(lmobj.getInterestType()==0)
										data=calculateEMIInterest(lmobj.getSanctionedAmount(), lmobj.getInterestRate(), lmobj.getNoOfInstallments(),lmobj);
									System.out.println("*************data***************" + data.length);
									if(disform.getDisbamt()!=0)
									{
										double disb_left=delegate.getDisbursementLeft(lmobj.getAccType(),lmobj.getAccNo());
										if(disb_left!=-1 && disform.getDisbamt()>disb_left)
										{
											req.setAttribute("msg", "Disbursing amount is higher than the Disbursement left");
											disform.setDisbamt(0.0);
										}
										else
										{
											System.out.println(">>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
											Object values[][]=null;
											Object values1[][]=null;
											Object disb_values[][]=null;
											String[] dates;
											count_disburse=0;
											double amount_remaining=0,amtsanc;
											double amount_sanctioned=0;
											int installments=0;
											values = delegate.getPreviousDisbursement(lmobj.getAccType(),lmobj.getAccNo(),flag);
											count_disburse =  delegate.getPreviousDisbDet(lmobj.getAccType(),lmobj.getAccNo(),delegate.getSysDate());
									
											if(values==null || delegate.getNumPreviousDisb(lmobj.getAccType(),lmobj.getAccNo(),1)==0)
											{
												if(disform.getPeriod()!=0)
													installments=disform.getPeriod();
												else
													req.setAttribute("msg","Enter the number of installments");
												amtsanc=disform.getDisbamt();
												installments = (installments - disform.getHoliday());
												if(installments<0)
												{
													installments=-installments;
												}
												System.out.println("amount"+installments+"  "+amtsanc+" "+lmobj.getInterestRate());
												data=null;
												if(lmobj.getInterestType()==1)
													data=calculateReducingInterest(amtsanc,lmobj.getInterestRate(),installments,lmobj);		
												else if(lmobj.getInterestType()==0)
													data=calculateEMIInterest(amtsanc,lmobj.getInterestRate(),installments,lmobj);	
											}
											else
											{
												values1 = new Object[values.length+1][values[0].length];
												dates= new String[values.length-count_disburse+1];
												for(int i=0;i<values.length+1;i++)
													for(int j=0;j<values[0].length;j++)
													{
														if(i<count_disburse)
														{
															values1[i][j]=values[i][j];
														}
														else
															if(i>count_disburse)
																values1[i][j]=values[i-1][j];
															else
																if(i==count_disburse)
																	values1[i][j]="   ";
													}
									
												for(int i=0;i<dates.length-1;i++)
												{
													if(count_disburse==0)
													{
														if(values[i+count_disburse][1]!=null)
															dates[i]=values[i+count_disburse][1].toString();
													}
													else
														dates[i]=values[i+count_disburse-1][1].toString();
													System.out.println("The dates are:"+dates[i]);
												}
												if(count_disburse==0)
													amount_remaining = Double.parseDouble(values[count_disburse][5].toString())+Double.parseDouble(values[count_disburse][2].toString());
												else
													amount_remaining = Double.parseDouble(values[count_disburse-1][5].toString());
												amount_sanctioned=disform.getDisbamt()+amount_remaining;
												installments=dates.length-2;
												String a,b;
												a=values[count_disburse][3].toString();
												b=values[count_disburse][4].toString();
									System.out.println("the sanctioned amount:"+amount_sanctioned);
									if(lmobj.getInterestType()==1)
										disb_values=newCalculateReducingInterest(amount_sanctioned,lmobj.getInterestRate(),installments,dates);
									if(lmobj.getInterestType()==0)
										disb_values=newCalculateEMIInterest(amount_sanctioned,lmobj.getInterestRate(),installments,dates);
										for(int i=0;i<disb_values.length-1;i++)
										{
											for(int j=1;j<values[0].length;j++)
												values1[i+count_disburse+1][j]=disb_values[i][j];
										}
									for(int i=0;i<disb_values.length-1;i++)
									{
										for(int j=1;j<values[0].length;j++)
											values[i+count_disburse][j]=disb_values[i][j];
									}
									{
										values[count_disburse][3]=a;//values[count_disburse][4]=b;
										double interest=0;
										if(count_disburse==0)
											 interest=Math.round(disform.getDisbamt()*lmobj.getInterestRate()*(Validations.dayCompare(delegate.getSysDate(),dates[0]))/36500.00);
										else
											 interest=Math.round(disform.getDisbamt()*lmobj.getInterestRate()*(Validations.dayCompare(delegate.getSysDate(),dates[1]))/36500.00);
										System.out.println("the value of the interest is:"+interest);
										System.out.println("the value of the interest is:"+String.valueOf(Double.parseDouble(values[count_disburse][3].toString())+ interest));
										values[count_disburse][3]=String.valueOf(Double.parseDouble(values[count_disburse][3].toString())+ interest);
										values[count_disburse][4]=String.valueOf(Double.parseDouble(values[count_disburse][2].toString())+ Double.parseDouble(values[count_disburse][3].toString()));
									}
									System.out.println("The remaining amount is:"+amount_remaining);
									System.out.println("the no of rows is: "+count_disburse);
									System.out.println("the length of object is:"+values.length);
									System.out.println("the length of object coplumn is:"+values[0].length);
									data=values; // Setting the global data object in order to pass it to the bean in the action performed
						   	         }
						          }
							    }
									req.setAttribute("ScheduleData", data);
							 }
						   }
						   	catch(Exception e1)
							{
							   	e1.printStackTrace();
							}
					    }
						System.out.println("************After new schedule*****************");
						if(disform.getForward().equalsIgnoreCase("Submit"))
						{
							double disbursement=disform.getDisbamt();
							double amtsanct=disform.getAmount();
							if(disform.getDisbamt()==0.0)
								req.setAttribute("msg","disbursement  amount is not given");
							else if(disform.getDisbamt()>disform.getDisbleft())
								req.setAttribute("msg","Disbursement amount is greater than amount sanctioned");	
							else
							{
							 	System.out.println("Inside submit-=-");
									String narr="";
									String trnmode="";
									if(disform.getCombo_pay_mode().equalsIgnoreCase("C"))
									{
										System.out.println("Inside Cash");
											trnmode="C";
											narr="  CASH";
									}
									else if(disform.getCombo_pay_mode().equalsIgnoreCase("T"))
									{
										System.out.println("Inside Transfer");
											trnmode="T";
											narr=disform.getPayactype()+" "+disform.getPayaccno(); // Code added by Murugesh on 29/12/2005
									}
									else if(disform.getCombo_pay_mode().equalsIgnoreCase("P"))
									{
										System.out.println("Inside Payorder");
										trnmode="P";
										narr="  PayOrder";
									}
									System.out.println("Narattttttion"+trnmode+"  "+narr);
									int result=delegate.disburseLoan(Integer.parseInt(disform.getAcctype()), disform.getAccno(),disform.getDisbamt(),data,disform.getAmount(),"LN", "LN01", trnmode,narr,delegate.getSysDate(), delegate.getSysDateTime());
									if(result!=0)
									{
										req.setAttribute("msg", "Loan is Disbursed Successfully");
										disform.setAccno(0);
										disform.setShno(0);
										disform.setAmount(0.0);
										disform.setInstallment(0.0);
										disform.setIntrate(0.0);
										disform.setPenalrate(0.0);
										disform.setDisbleft(0.0);
										disform.setCombo_pay_mode("C");
										disform.setPayaccno(0);
										disform.setDisbamt(0.0);
									}
									else
										req.setAttribute("msg", "Unable To Disburse");
							}
						}
						else if(disform.getForward().equalsIgnoreCase("Delete"))
						{
							if(lmobj.isLoanDisbursed())
							{
								int i=delegate.deleteLoanDisbursement(disform.getAcctype(),disform.getAccno());
								if(i==1)
								{
									req.setAttribute("msg","Loan Account Disbursement is successfully Deleted");
								}
								else
								{
									req.setAttribute("msg"," Deletion Failed");
								}
							}
							else
							{
								req.setAttribute("msg","Loan Is Not Disbursed For This Account");
							}
						}
					System.out.println("disform.getDetails() ==========2" + disform.getDetails());
				/*	if ((disform.getDetails().equalsIgnoreCase("5002"))) {
						System.out.println("+++++++++++++++Action class application form code ++++++++++++");
						System.out.println("appform.getDetails() ==========  "+ disform.getDetails());
						System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						req.setAttribute("Application", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						req.setAttribute("pay_mode", delegate.getPayMode());
						req.setAttribute("pay_actype", delegate.getpayAcctype());
						String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
						String[] dirrelations=delegate.getDirectorRelations();
						req.setAttribute("Dirrelations",dirrelations);
						System.out.println("***********Director details**************" + dirdetails.toString());
						System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
						req.setAttribute("DirDetails",dirdetails);
						req.setAttribute("flag", null);
						if(lmobj!=null){
							req.setAttribute("LoanDetails",lmobj);
						}
					}
					if ((disform.getDetails().equalsIgnoreCase("5003"))) {
						System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
						req.setAttribute("flag", null);
						req.setAttribute("Vehicle", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getVehicleDet()!=null)
								req.setAttribute("VECHOBJ",lmobj.getVehicleDet());
							else 
								req.setAttribute("msg","Vehicle Details Not Found");
						}
					}
					if ((disform.getDetails().equalsIgnoreCase("Scheduling"))) {
						System.out.println("+++++++++++++++Action class Schedule form code ++++++++++++");
						System.out.println("disform.getDetails() ==========2" + disform.getDetails());
						req.setAttribute("flag", null);
						req.setAttribute("Schedule", MenuNameReader.getScreenProperties("5050"));
					}
					if ((disform.getDetails().equalsIgnoreCase("5004"))) {
						req.setAttribute("flag", null);
						System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
						req.setAttribute("Property", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getPropertyDetails()!=null)	
								req.setAttribute("PROPOBJ",lmobj.getPropertyDetails());
							else
								req.setAttribute("msg","Property Details Not Found");
							}
					}
					if(disform.getDetails().trim().equalsIgnoreCase("5093")) 
					{
						System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
						System.out.println("appform.getDetails() ==========5");
						req.setAttribute("flag", null);
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
						req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(disform.getDetails().trim()));
						if(lmobj!=null){
							if(lmobj.getRelatives()!=null)
								req.setAttribute("msg",lmobj.getRelatives());
							else
								req.setAttribute("msg","Relative Details Not Found");
						}
					}
				  if((disform.getDetails().trim().equalsIgnoreCase("Surities"))) 
					  {
						  System.out.println("*****************Inside to Suruties***************");
						  req.setAttribute("flag", null);
						  req.setAttribute("Surities",MenuNameReader.getScreenProperties("5032"));
						  if(lmobj!=null){
								if(lmobj.getSurities()!=null)
									req.setAttribute("msg",lmobj.getSurities());
								else
									req.setAttribute("msg","Surity Details Not Found");
							}
					  }
					if((disform.getDetails().trim().equalsIgnoreCase("Signature Ins"))) 
					{
						System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
						signObject = delegate.getSignatureDetails(disform.getShno(), disform.getShtype());
						if(signObject == null) 
						{
							System.out.println("*************Signature Object is null**********");	
							req.setAttribute("msg","Signature Details Not Found");
						}
						else
						{
							req.setAttribute("flag",MenuNameReader.getScreenProperties("0044"));
							req.setAttribute("signInfo",signObject);
							req.setAttribute("panelName",CommonPanelHeading.getSignature());
						}
					}
					if((disform.getDetails().equalsIgnoreCase("Loan and Share Details"))) {
						System.out.println("appform.getDetails() ==========8");
						req.setAttribute("flag", null);
						System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(disform.getShtype(),disform.getShno()));
						req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
						req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
						req.setAttribute("IndividualCoBorrower", null);
					}
					if((disform.getDetails().trim().equalsIgnoreCase("CoBorrower"))) {
						System.out.println("appform.getDetails() ==========Co Borrower");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5032"));
						req.setAttribute("flag", null);
						req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5032"));
					}
					if ((disform.getDetails().trim().equalsIgnoreCase("Gold"))) {
						System.out.println("appform.getDetails() ==========Gold");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("flag", null);
						req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("IndividualCoBorrower", null);
						if(lmobj!=null){
							if(lmobj.getGoldDet()!=null)
							{
								req.setAttribute("GoldDet",lmobj.getGoldDet());
							}
							else
								req.setAttribute("msg","Gold Details Not Found");
						}
					  }*/	 
				}
				else
				{
					req.setAttribute("msg","Loan Account Not Found");
					disform.setAccno(0);
					disform.setShno(0);
					disform.setAmount(0.0);
					disform.setInstallment(0.0);
					disform.setIntrate(0.0);
					disform.setPenalrate(0.0);
					disform.setDisbleft(0.0);
					disform.setCombo_pay_mode("C");
					disform.setPayaccno(0);
					disform.setDisbamt(0.0);
				}		
					if(MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
					{
						path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
						IntialParameters(req, delegate);
						req.setAttribute("pageId", path);
						String modcode = disform.getAcctype();
						System.out.println("modcode is ============" + modcode);
						items_relavent = delegate.getReleventDetails_str(modcode);
						int count = 0;
						if(items_relavent!= null) {
							for(int i=0;i<items_relavent.length;i++) 
							{
								if(items_relavent[i].equals("Y"))
									count++;
								if(items_relavent[i].equals("O"))
									count++;
							}
						}
						Vector vec_final[] = delegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
						return map.findForward(ResultHelp.getSuccess());
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		 if(map.getPath().equalsIgnoreCase("/Loans/DisbursementVerifyMenu"))
		 {
			 System.out.println("*********************/Loans/DisbursementVerifyMenu*************");
				try {
					DisbursementForm disform = (DisbursementForm) form;
					LoansDelegate delegate = new LoansDelegate();
					System.out.println("PAGE I~D ==== >"+ disform.getPageidentity().getPageId());
					if (MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
					{
					try
					{
					String	path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
					req.setAttribute("pageId", path);
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					disform.setAccno(0);
					disform.setShno(0);
					disform.setAmount(0.0);
					disform.setInstallment(0.0);
					disform.setIntrate(0.0);
					disform.setPenalrate(0.0);
					disform.setDisbleft(0.0);
					disform.setCombo_pay_mode("C");
					disform.setPayaccno(0);
					disform.setDisbamt(0.0);
				    IntialParameters(req, delegate);
			        System.out.println("<=========COMING BACK TO ACTION CLASS Verify Menu=======>");
			        return map.findForward(ResultHelp.getSuccess());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return map.findForward(ResultHelp.getError());
					}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
		 }
		 if(map.getPath().equalsIgnoreCase("/Loans/DisbursementVerify"))
		 {
			 System.out.println("*********************/Loans/DisbursementVerify*************");
				try {
					DisbursementForm disform = (DisbursementForm) form;
					LoansDelegate delegate = new LoansDelegate();
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					LoanTransactionObject tranobj=null;
					System.out.println("PAGE I~D ==== >"+ disform.getPageidentity().getPageId());
					if(MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) 
					{
						try
						{
								String	path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
								req.setAttribute("pageId", path);
								IntialParameters(req, delegate);
								System.out.println("<=========COMING BACK TO ACTION CLASS Verify page =======>");
								System.out.println("disform.getAccno()------------->" + disform.getAccno());
								System.out.println("disform.getAcctype()------------->" + disform.getAcctype());
								LoanMasterObject lmobj=delegate.getLoanMaster(disform.getAccno(),disform.getAcctype());
								if(lmobj!=null)
								{
								if(disform.getAccno()!=0)
								{ 
									System.out.println("disform.getAcctype()******" + disform.getAcctype());
									if(disform.getDetails()!=null)
								  	if(disform.getDetails().equalsIgnoreCase("0001")){
						        		req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
						        		if(lmobj.getCustomerId()!=0){
						        		req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						        		req.setAttribute("panelName", CommonPanelHeading.getPersonal());
						        		req.setAttribute("TabNum", "0");
						        		System.out.println("((((((((((((((LoanMasterObject)))))))))))" + lmobj.getApplicationSrlNo());
						        		disform.setAcctype(lmobj.getAccType());
						        		disform.setShtype(lmobj.getShareAccType());
						        		disform.setShno(lmobj.getShareAccNo());
						        		disform.setCombo_pay_mode(lmobj.getPayMode());
						        		disform.setPayactype(lmobj.getPaymentAcctype());
						        		disform.setHoliday(lmobj.getHolidayPeriod());
						        		disform.setPayaccno(lmobj.getPaymentAccno());
						        		disform.setIntrate(lmobj.getInterestRate());
						        		disform.setPenalrate(lmobj.getPenalRate());
						        		disform.setDisbleft(lmobj.getDisbursementLeft());
						        		req.setAttribute("Priority",String.valueOf(lmobj.getPrior()));
						        		disform.setPriority(String.valueOf(lmobj.getPrior()));
						        		disform.setPurpose(String.valueOf(lmobj.getPurposeCode()));
						        		System.out.println("***********After the Loanmaster object**********");
						        		System.out.println("((((((((((((appform.getDetails()))))))))))" + disform.getDetails());
						        		}
						        	}
						        	else if (disform.getDetails().equalsIgnoreCase("5002")) {
						        		 System.out.println("+++++++++++++++Action class application form code ++++++++++++");
											System.out.println("appform.getDetails() ==========  "+ disform.getDetails());
											System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(disform.getDetails().trim()));
											req.setAttribute("Application", MenuNameReader.getScreenProperties("5002"));
											req.setAttribute("pay_mode", delegate.getPayMode());
											req.setAttribute("pay_actype", delegate.getpayAcctype());
											disform.setShtype(lmobj.getShareAccType());
											disform.setShno(lmobj.getShareAccNo());
											String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
											String[] dirrelations=delegate.getDirectorRelations();
											req.setAttribute("Dirrelations",dirrelations);
											System.out.println("***********Director details**************" + dirdetails.toString());
											System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
											req.setAttribute("DirDetails",dirdetails);
											req.setAttribute("flag", null);
											disform.setAppln_no(lmobj.getApplicationSrlNo());
											disform.setAppndate(lmobj.getApplicationDate());
											disform.setReqamount(lmobj.getRequiredAmount());
											disform.setDirrelations(lmobj.getRelative());
											//appform.setDirdetails(loanmaster.getDirectorCode());
											disform.setPaymtmode(lmobj.getPayMode());
											disform.setPayactype(lmobj.getPaymentAcctype());
											disform.setPayaccno(lmobj.getPaymentAccno());
											disform.setInteresttype(lmobj.getInterestType());
											disform.setInterestcalctype(lmobj.getInterestRateType());
										}
						        	else if (disform.getDetails().equalsIgnoreCase("5005")) {
										System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
										System.out.println("appform.getDetails() ==========4");
										session=req.getSession();
										System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(disform.getDetails()));
										req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
										tabpane_employment(req, disform);
										req.setAttribute("TabNum", "0");
										IncomeObject[] incomeObjects=lmobj.getIncomeDetails();
										IncomeObject inObj=null;
										for(int i=0;i<incomeObjects.length;i++){
											inObj=(IncomeObject)Array.get(incomeObjects, i);
											if(inObj.getTypeOfIncome()!=null){
												disform.setShtype(lmobj.getShareAccType());
												disform.setShno(lmobj.getShareAccNo());
											if (inObj.getTypeOfIncome().equalsIgnoreCase("Self")) {
												req.setAttribute("TabNum", "0");
												session.setAttribute("Self", inObj);
											}else if (inObj.getTypeOfIncome().equalsIgnoreCase("Service")) {
												req.setAttribute("TabNum", "1");
												session.setAttribute("Service", inObj);
											} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Business")) {
												req.setAttribute("TabNum", "2");
												session.setAttribute("Business", inObj);
											} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Pension")) {
												req.setAttribute("TabNum", "3");
												session.setAttribute("Pension", inObj);
											} else if (inObj.getTypeOfIncome().equalsIgnoreCase("Rent")) {
												req.setAttribute("TabNum", "4");
												session.setAttribute("Rent", inObj);
											}
											}
										}
									}
						        	else if (disform.getDetails().trim().equalsIgnoreCase("Signature Ins")) {
						        		CustomerMasterObject cust=null;
										System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
										signObject=delegate.getSignatureDetails(disform.getShno(), disform.getShtype());
										if(signObject!=null){
										if(signObject[0].getCid()!=0)
					      			   cust=delegate.getCustomer(signObject[0].getCid());
					          			req.setAttribute("cust",cust);
					          			req.setAttribute("sigObject",signObject);
					          			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
					          			path=MenuNameReader.getScreenProperties("5053");
					          			System.out.println("path is---------------"+path);
					          			req.setAttribute("flag","/SingnatureInst1.jsp"); 
										}else{
											req.setAttribute("msg","Account Number Not Found!!");
										}
									}
						        	else if (disform.getDetails().equalsIgnoreCase("Loan and Share Details")) {
										System.out.println("appform.getDetails() ==========8");
										req.setAttribute("flag", null);
										System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
										req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
										req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(disform.getShtype(),disform.getShno()));
										req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
										req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
										req.setAttribute("IndividualCoBorrower", null);
									}
						        	 else if (disform.getDetails().equalsIgnoreCase("5022")) {
											System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
											System.out.println("appform.getDetails() ==========2" + disform.getDetails());
											VehicleObject vehicleObject=null;
											req.setAttribute("flag", null);
											req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5003"));
											vehicleObject=lmobj.getVehicleDet();
											req.setAttribute("VECHOBJ", vehicleObject);
										}
						        	 else if (disform.getDetails().equalsIgnoreCase("5004")) {
											PropertyObject propertyObject=null;
											System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
											System.out.println("appform.getDetails() ==========3");
											req.setAttribute("flag", null);
											req.setAttribute("Property", MenuNameReader.getScreenProperties("5004"));
											propertyObject=lmobj.getPropertyDetails();
											req.setAttribute("PROPOBJ", propertyObject);
										}
						        	 else if (disform.getDetails().trim().equalsIgnoreCase("5093")) {
						        		 	Object[][] relatives=null;
						        		 	Object[][] indents=null;
						        		 	Object[][] dependents=null;
											System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
											System.out.println("appform.getDetails() ==========5");
											req.setAttribute("flag", null);
											System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
											req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties("5093"));
											relatives=lmobj.getRelatives();
											indents=lmobj.getInterests();
											dependents=lmobj.getDependents();
											req.setAttribute("RelObj", relatives);
											req.setAttribute("RelIndObj", indents);
											req.setAttribute("RelDepObj", dependents);
										}
						        	 else if(disform.getDetails().trim().equalsIgnoreCase("5027")) 
										  {
						        		 	Vector vector=null;
						        		 	String coborower=null;
						        		 	Iterator iterator=null;
						        		 	String ac_type=null;
						        		 	String ac_no=null;
						        		 	AccountObject[] array_accountObject=null;
						        		 	SignatureInstructionObject[] signObject2=null;
						        		  	System.out.println("*****************Inside to Suruties***************");
						        		  	req.setAttribute("flag", null);
						        		  	req.setAttribute("Surities",MenuNameReader.getScreenProperties("5027"));
											vector=lmobj.getSurities();
											iterator=vector.iterator();
											array_accountObject = new AccountObject[1];
											while(iterator.hasNext()){
												coborower=(String)iterator.next();
												System.out.println("coborrower vector values==>"+coborower);
												ac_type=coborower.substring(0, 7);
												System.out.println("ac_type values==>"+ac_type);
												ac_no=coborower.substring(8);
												System.out.println("ac_no values==>"+ac_no);
												if(ac_type!=null && ac_no != null&& ac_no.trim().length()!=0) {
													array_accountObject[0] = delegate.getAccount(null,ac_type, Integer.parseInt(ac_no));
																	if(ac_no.trim().length()!=0 && array_accountObject[0]!=null){
																		disform.setShno(array_accountObject[0].getAccno());
																		signObject2=delegate.getSignatureDetails(Integer.parseInt(ac_no),ac_type);
																		req.setAttribute("panelName", "Personal Details");
																		req.setAttribute("personalInfo", delegate.getCustomer(signObject2[0].getCid()));
																		req.setAttribute("TabNum", "0");
																		System.out.println("Suraj is setting the tab number");
																		req.setAttribute("flag",null);
																	}
																	req.setAttribute("ShareAccountObject",array_accountObject);
												}
											}
										}
						        	 else if (disform.getDetails().trim().equalsIgnoreCase("5032")) {
						        		 	Vector vector=null;
						        		 	String coborower=null;
						        		 	Iterator iterator=null;
						        		 	String ac_type=null;
						        		 	String ac_no=null;
						        		 	AccountObject[] array_accountObject=null;
						        		 	SignatureInstructionObject[] signObject2=null;
											System.out.println("appform.getDetails() ==========Co Borrower");
											System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5027"));
											req.setAttribute("flag", null);
											req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5027"));
											vector=lmobj.getCoBorrowers();
											iterator=vector.iterator();
											array_accountObject = new AccountObject[1];
											while(iterator.hasNext()){
												coborower=(String)iterator.next();
												System.out.println("coborrower vector values==>"+coborower);
												ac_type=coborower.substring(0, 7);
												System.out.println("ac_type values==>"+ac_type);
												ac_no=coborower.substring(8);
												System.out.println("ac_no values==>"+ac_no);
												if(ac_type!=null && ac_no != null&& ac_no.trim().length()!=0) {
													array_accountObject[0] = delegate.getAccount(null,ac_type, Integer.parseInt(ac_no));
																	if(ac_no.trim().length()!=0 && array_accountObject[0]!=null){
																		disform.setShno(array_accountObject[0].getAccno());
																		signObject2=delegate.getSignatureDetails(Integer.parseInt(ac_no),ac_type);
																		req.setAttribute("panelName", "Personal Details");
																		req.setAttribute("personalInfo", delegate.getCustomer(signObject2[0].getCid()));
																		req.setAttribute("TabNum", "0");
																		System.out.println("Suraj is setting the tab number");
																		req.setAttribute("flag",null);
																	}
																	req.setAttribute("ShareAccountObject",array_accountObject);
												}
											}
										}
						        	 else if (disform.getDetails().trim().equalsIgnoreCase("Surities")) {
						        		 GoldObject goldObject=null;	
						        		 System.out.println("appform.getDetails() ==========Gold");
						        		 disform.setShtype(lmobj.getShareAccType());
						        		 disform.setShno(lmobj.getShareAccNo());
											System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
											req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
											if(goldObject!=null){
												req.setAttribute("flag", null);
												goldObject=lmobj.getGoldDet();
												req.setAttribute("GoldDet", goldObject);
											}else{
												req.setAttribute("msg", "Gold Details are not found for this account");
											}
										}
									System.out.println("*********Loan Master Object********" + lmobj.getApplicationSrlNo());
									tranobj=delegate.getUnVerifiedDisbursement(disform.getAcctype(), disform.getAccno());
									if(tranobj==null) 
									{
											req.setAttribute("msg","No Disbursement found ");
											disform.setAccno(0);
											disform.setShno(0);
											disform.setAmount(0.0);
											disform.setInstallment(0.0);
											disform.setIntrate(0.0);
											disform.setPenalrate(0.0);
											disform.setDisbleft(0.0);
											disform.setCombo_pay_mode("C");
											disform.setPayaccno(0);
											disform.setDisbamt(0.0);
									}
									else
									{
										disform.setShno(lmobj.getShareAccNo());
										disform.setAmount(lmobj.getSanctionedAmount());
										disform.setPeriod(lmobj.getNoOfInstallments());
										disform.setHoliday(lmobj.getHolidayPeriod());
										disform.setInstallment(lmobj.getInstallmentAmt());
										disform.setIntrate(lmobj.getInterestRate());
										disform.setPenalrate(lmobj.getPenalRate());
										disform.setDisbamt(tranobj.getTransactionAmount());
										disform.setDisbleft((lmobj.getDisbursementLeft()-tranobj.getTransactionAmount()));
										disform.setCombo_pay_mode(lmobj.getPayMode());
										if(lmobj.getPayMode()=="T")
										{
											disform.setPayactype(lmobj.getPaymentAcctype());
											disform.setPayaccno(lmobj.getPaymentAccno());
										}
									}
								} 	
								if(disform.getButtonvalue().equalsIgnoreCase("Verify"))
								{	
									System.out.println("Am in Verify block");
									tranobj=delegate.getUnVerifiedDisbursement(disform.getAcctype(), disform.getAccno());
									if(tranobj!=null)
									{
										disform.setDisbamt(tranobj.getTransactionAmount());
										String type="";
										int payno=0;
										if(disform.getCombo_pay_mode().equalsIgnoreCase("T"))
										{
											type=disform.getPayactype();
											System.out.println("***********Type**********"+ type);
											payno=disform.getPayaccno();
										}
										tranobj.setTranMode(lmobj.getPayMode());
										System.out.println("*********Loan Transaction Object********" + tranobj.getAccNo());
										if(tranobj.getTranMode().equals("T"))
										{
											lmobj.setPaymentAcctype(type);
											lmobj.setPaymentAccno(payno);
										}
										int verify_dis=delegate.verifyLoanDisbursement(tranobj.getTranSequence(),tranobj.getAccType(),tranobj.getAccNo(), "LN", "LN01", type, payno, tranobj.getTransactionAmount(), lmobj, tranobj, delegate.getSysDateTime());
										System.out.println("****************verify_dis****************" + verify_dis );
										if(verify_dis>0)
										{	
											if(disform.getCombo_pay_mode().equalsIgnoreCase("C"))
											{	
												req.setAttribute("msg","Loan disbursment is verified " + "The Voucher Number is "+verify_dis+" ");
												disform.setAccno(0);
												disform.setShno(0);
												disform.setAmount(0.0);
												disform.setInstallment(0.0);
												disform.setIntrate(0.0);
												disform.setPenalrate(0.0);
												disform.setDisbleft(0.0);
												disform.setCombo_pay_mode("C");
												disform.setPayaccno(0);
												disform.setDisbamt(0.0);
											}	
											else if(disform.getCombo_pay_mode().equalsIgnoreCase("P"))
											{	
												req.setAttribute("msg","Loan disbursment is verified " + "The PayOrder Number is "+verify_dis+" ");
												disform.setAccno(0);
												disform.setShno(0);
												disform.setAmount(0.0);
												disform.setInstallment(0.0);
												disform.setIntrate(0.0);
												disform.setPenalrate(0.0);
												disform.setDisbleft(0.0);
												disform.setCombo_pay_mode("C");
												disform.setPayaccno(0);
												disform.setDisbamt(0.0);
											}
											else
											{
												req.setAttribute("msg","Loan disbursment is verified ");
												disform.setAccno(0);
												disform.setShno(0);
												disform.setAmount(0.0);
												disform.setInstallment(0.0);
												disform.setIntrate(0.0);
												disform.setPenalrate(0.0);
												disform.setDisbleft(0.0);
												disform.setCombo_pay_mode("C");
												disform.setPayaccno(0);
												disform.setDisbamt(0.0);	
											}
										}
										else
										{	
											req.setAttribute("msg","Disbursed Loan is not verified");
										}
									}
								  }
								}
								else
								{	
									req.setAttribute("msg","Loan Account Not Found");
									disform.setAccno(0);
									disform.setShno(0);
									disform.setAmount(0.0);
									disform.setInstallment(0.0);
									disform.setIntrate(0.0);
									disform.setPenalrate(0.0);
									disform.setDisbleft(0.0);
									disform.setCombo_pay_mode("C");
									disform.setPayaccno(0);
									disform.setDisbamt(0.0);
								}
								String modcode = disform.getAcctype();
								System.out.println("modcode is ============" + modcode);
								items_relavent = delegate.getReleventDetails_str(modcode);
								int count = 0;
								if (items_relavent != null) {
									for (int i = 0; i < items_relavent.length; i++) {
										if (items_relavent[i].equals("Y"))
											count++;
										if (items_relavent[i].equals("O"))
											count++;
									}
								}
					
					if (MenuNameReader.containsKeyScreen(disform.getPageidentity().getPageId())) {
						path = MenuNameReader.getScreenProperties(disform.getPageidentity().getPageId());
						IntialParameters(req, delegate);
						req.setAttribute("pageId", path);
						Vector vec_final[] = delegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
						return map.findForward(ResultHelp.getSuccess());
					}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return map.findForward(ResultHelp.getError());
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }



		
		return map.findForward(ResultHelp.getSuccess()); 
	}
	Object [][] calculateReducingInterest(double prn,double rate,int period,LoanMasterObject lmobj){
		{
			Object data[][]=null;
			data = new Object[period+1][6];
			try{
				String str1=null,str=null;
				GregorianCalendar g=null;
				str1=Validations.addNoOfMonths(delegate.getSysDate(),lmobj.getHolidayPeriod());
				StringTokenizer st = new StringTokenizer(str1,"/");
				int int_day = Integer.parseInt(st.nextToken());
				int int_month = Integer.parseInt(st.nextToken())-1;
				int int_year = Integer.parseInt(st.nextToken());
				g = new GregorianCalendar(int_year,int_month,int_day);
				str1 =delegate.getSysDate();
				double sumi=0.0,sumt=0.0,sump=0.0;
				double install=Math.round(prn/period);
				double bal=Math.round(prn-(period*install));
				int i=0;
				for(i=0;i<period;i++)
				{
				g.add(Calendar.MONTH,1);
				str=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
				double interest=Math.round((prn*rate*(Validations.dayCompare(str1,str)))/36500);
				str1=str;
				if(i==(period-1))
					install=install+bal;
				prn=prn-install;
				data[i][0]=String.valueOf(i+1);
				data[i][1]=str;
				data[i][2]=String.valueOf(install);
				data[i][3]=String.valueOf(interest);
				data[i][4]=String.valueOf(interest+install);
				data[i][5]=String.valueOf(prn);	
				sump += install;
				sumi+=interest;
				sumt+=(interest+install);
			}
			data[i][0]="";
			data[i][1]="";
			data[i][2]=String.valueOf(sump);
			data[i][3]=String.valueOf(sumi);
			data[i][4]=String.valueOf(sumt);
			data[i][5]="";	
			}catch(Exception ex){System.out.println(ex);}
		return data;
		}
	}
	Object [][] calculateEMIInterest(double prn,double rate,int period,LoanMasterObject lmobj)
	{
		Object data[][]=null;
	   try{
	    double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0,sump=0.0;
	    data=new Object[period+1][6];
	    String str1=Validations.addNoOfMonths(delegate.getSysDate(),lmobj.getHolidayPeriod());
		StringTokenizer st = new StringTokenizer(str1,"/");
		int int_day = Integer.parseInt(st.nextToken());
		int int_month = Integer.parseInt(st.nextToken())-1;
		int int_year = Integer.parseInt(st.nextToken());
		GregorianCalendar g = new GregorianCalendar(int_year,int_month,int_day);
		str1 = delegate.getSysDate();
		System.out.println("/////////////////// ++++++++++++++++++++++++ date:"+str1);
	    rate=rate/1200;
	    double pow=1;
	    for(int j=0;j<period;j++)
	        pow=pow*(1+rate);
	    double install=Math.round(0.01*Math.round(100*(prn*pow*rate)/(pow-1)));
	    double bal=Math.round(prn-(period*install));
	    int i=0;
	    for(i=0;i<period;i++)
	    {
	        g.add(Calendar.MONTH,1);
	        String str=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
	        System.out.println(" the date is:"+str);
	        double interest=Math.round((prn*cprate*(Validations.dayCompare(str1,str)))/36500.00);
	        str1=str;
	        cin=(install-interest);
	        if(i==(period-1))
	        {
	        	install = (interest+prn);
	        	cin = prn;
	        }
	        data[i][0]=String.valueOf(i+1);
	        data[i][1]=str; // data[i][2]=String.valueOf(install); // Code changed by Murugesh on 23/12/2005
	        data[i][2]=String.valueOf(cin); // Code added by Murugesh on 23/12/2005
	        data[i][3]=String.valueOf(interest);  //  data[i][4]=String.valueOf(cin); // Code changed by Murugesh on 23/12/2005
	        data[i][4]=String.valueOf(install); // Code added by Murugesh on 23/12/2005
	        prn=prn-(install-interest);
	        data[i][5]=String.valueOf(prn);	 // prn=prn-(install-interest);
	        sump += cin;
	        sumi+=interest;
	        sumt=sumt+(install-interest); 
	    }
	    data[i][0]="";
	    data[i][1]="";
	    data[i][2]=String.valueOf(sump);
	    data[i][3]=String.valueOf(sumi);// data[i][4]=String.valueOf(sumt);// Code changed by Murugesh on 26/12/2005
	    data[i][4]=String.valueOf(sumt+sumi);  // Code added by Murugesh on 26/12/2005
	    data[i][5]="";	
	    }
	    catch(Exception e){e.printStackTrace();}
	    return data;
	}
	Object [][] newCalculateReducingInterest(double prn,double rate,int period, String[] dates)
	{
		Object data[][]=new Object[period+1][6];
		try{
		String str1=null,str=null;
		str1=dates[0];
		double sumi=0.0,sumt=0.0,sump=0.0;
		double install=Math.round(prn/period);
		double bal=Math.round(prn-(period*install));
		int i=0;
		double interest=0;
		for(i=0;i<period;i++)
		{
			if(count_disburse==0)//str=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
				str=dates[i];
			else
				str=dates[i+1]; 
			 interest=Math.round((prn*rate*(Validations.dayCompare(dates[i],dates[i+1])))/36500.00);
			if(count_disburse==0 && i==period-1)
				 interest=Math.round((prn*rate*(Validations.dayCompare(dates[i],Validations.DateAdd(dates[i],1))))/36500.00);
			str1=str;
			if(i==(period-1))
				install=install+bal;
			prn=prn-install;
			data[i][0]=String.valueOf(i+1);
			data[i][1]=str1;
			data[i][2]=String.valueOf(install);
			data[i][3]=String.valueOf(interest);
			data[i][4]=String.valueOf(interest+install);
			data[i][5]=String.valueOf(prn);	
			sump += install;
			sumi+=interest;
			sumt+=(interest+install);
		}
		data[i][0]="";
		data[i][1]="";
		data[i][2]=String.valueOf(sump);
		data[i][3]=String.valueOf(sumi);
		data[i][4]=String.valueOf(sumt);
		data[i][5]="";	
		}catch(Exception ex){System.out.println(ex);}
		return data;
	}
	Object [][] newCalculateEMIInterest(double prn,double rate,int period,String[] dates)
	{
	    Object data[][]=new Object[period+1][6];
	    double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0,sump=0.0;
		String str="";
	    String str1=dates[0];
	    rate=rate/1200;
	    double pow=1;
	    for(int j=0;j<period;j++)
	        pow=pow*(1+rate);
	    double install=Math.round(0.01*Math.round(100*(prn*pow*rate)/(pow-1)));
	    int i=0;
	    double interest=0;
	    for(i=0;i<period;i++)
	    {
	    	if(count_disburse==0)
	    		str=dates[i];
	    	else
	    		str = dates[i+1];
	         interest=Math.round((prn*cprate*(Validations.dayCompare(dates[i],dates[i+1])))/36500.00);
	        if(count_disburse==0 && i==period-1)
				 interest=Math.round((prn*cprate*(Validations.dayCompare(dates[i],Validations.DateAdd(dates[i],1))))/36500.00);
	        str1=str;
	        cin=(install-interest);
	        if(i==(period-1))
	        {
	        	install = (interest+prn);
	        	cin = prn;
	        }
	        data[i][0]=String.valueOf(i+1);
	        data[i][1]=str;  // data[i][2]=String.valueOf(install); // Code changed by Murugesh on 23/12/2005
	        data[i][2]=String.valueOf(cin); // Code added by Murugesh on 23/12/2005
	        data[i][3]=String.valueOf(interest);
	      //  data[i][4]=String.valueOf(cin); // Code changed by Murugesh on 23/12/2005
	        data[i][4]=String.valueOf(install); // Code added by Murugesh on 23/12/2005
	        prn=prn-(install-interest);
	        data[i][5]=String.valueOf(prn);	
	        sump += cin;
	        sumi+=interest;
	        sumt=sumt+(install-interest); 
	    }
	    data[i][0]="";
	    data[i][1]="";
	    data[i][2]=String.valueOf(sump);
	    data[i][3]=String.valueOf(sumi); // data[i][4]=String.valueOf(sumt);// Code changed by Murugesh on 26/12/2005
	    data[i][4]=String.valueOf(sumt+sumi);  // Code added by Murugesh on 26/12/2005
	    data[i][5]="";	
	    return data;
	}
	
	public void IntialParameters(HttpServletRequest req, LoansDelegate delegate)throws Exception {
		req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
		req.setAttribute("LoanPurpose", delegate.getLoanDesc());
		req.setAttribute("Details", delegate.getDetails());
		req.setAttribute("ShareModule", delegate.getSharemodulecode(2));
		req.setAttribute("PriorityDesc", delegate.getPriorityDesc());
	}
	private void tabpane_employment(HttpServletRequest req,DisbursementForm disbursementForm) {
		String pageActions[] = {"/Loans/LoanApplicationDE?tabPaneHeading=SelfEmployed&pageId="+ disbursementForm.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Service&pageId="+ disbursementForm.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Business&pageId="	+ disbursementForm.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Pension&pageId="+ disbursementForm.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Rent&pageId="+ disbursementForm.getPageidentity().getPageId() };
		String tabHeading[] = { "SelfEmployed", "Service", "Business","Pension", "Rent" };
		String pagePath[] = {MenuNameReader.getScreenProperties("5005").trim(),MenuNameReader.getScreenProperties("5006").trim(),MenuNameReader.getScreenProperties("5007").trim(),MenuNameReader.getScreenProperties("5008").trim(),MenuNameReader.getScreenProperties("5009").trim() };
		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
	}
	
	private void setErrorPageElements(String exception,HttpServletRequest req,String path)
	{
		req.setAttribute("exception",exception);
		req.setAttribute("pageId",path);
	}
	private void Initialparam_loanActyp(HttpServletRequest req,LoansDelegate ldeligate)throws RemoteException
	{
		System.out.println("In Initialparam_loanActyp==============");
		req.setAttribute("LoanAccType",ldeligate.getMainModules(2,"1010000"));
		//req.setAttribute("LoanCategory",ldeligate.getMainModules(2,"'1010000','1008000'"));
	}	
	
	private void Initialparam_loanActyp1(HttpServletRequest req,LoansDelegate ldeligate)throws RemoteException
	{
		//req.setAttribute("LoanAccType",ldeligate.getMainModules(2,"1010000"));
		req.setAttribute("LoanCategory",ldeligate.getMainModules(2,"'1010000','1008000'"));
	}	
	private void LoanDocumList(HttpServletRequest req,LoansDelegate ldeligate)throws RemoteException
	{
		req.setAttribute("LoanDocumentList",ldeligate.getDocs("a",1));
	}	
	private void getInitialparam_DCB(LoansDelegate deligate,HttpServletRequest req)throws RemoteException
	{
		req.setAttribute("Months",deligate.getMonths());
		req.setAttribute("AccountType",deligate.getMainModules(2,"'1010000','1008000'"));
	}
	
	private void RevRecoveryInitialParam(HttpServletRequest req,
			LoansDelegate delegate, String path) {
		try {

			req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
			req.setAttribute("GLType", delegate.getGLTypes());
			req.setAttribute("GLCodes", delegate.getGLcodes());
			req.setAttribute("pageId", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int dcbProcess(boolean del,LoansDelegate deligate,DCBSchedulingForm dcbProcessform)throws NumberFormatException,RemoteException
	{ 
		System.out.println("***********Inside DCB Process**************"); 
		System.out.println("combo values=====================>"+dcbProcessform.getCombo_pay_actype());
		int exec=0;
		if(dcbProcessform.getCombo_pay_actype().equalsIgnoreCase("All"))
		{
			 exec=deligate.dcbProcess(Integer.valueOf(dcbProcessform.getTxt_monthandyear()),Integer.parseInt(dcbProcessform.getCombo_year()),0,"",0,0,"lntml","lnuser",del);
	    	if(exec ==1) 
	    	return  exec;
	    }
	    else if(dcbProcessform.getCombo_pay_actype().equalsIgnoreCase("Selected"))
	    {
	    	 exec = deligate.dcbProcess(Integer.valueOf(dcbProcessform.getTxt_monthandyear()),Integer.parseInt(dcbProcessform.getCombo_year()),1,dcbProcessform.getCombo_actype(),Integer.parseInt(dcbProcessform.getTxt_fromacnum()),Integer.parseInt(dcbProcessform.getTxt_toaccnum()),"lntml","lnuser",del);
	        System.out.println("exec============>"+exec);
			if(exec ==1) 
			System.out.println("Process Completed....");
			//JOptionPane.showMessageDialog(null,"Process Completed..");
			return exec;
	    }
		return exec;
	}
	
	private void setNPAValues(String ac_type,LoansDelegate deligate,LoanNpaAdminForm npaadminform,HttpServletRequest req)throws RemoteException
	{
		System.out.println("******Inside set NPA Values**********"+ac_type);
		 npa_values=deligate.getNPAAdminValues(ac_type, Integer.valueOf(npaadminform.getTxt_tab180()));
		if(npa_values!=null)
		{
			npaadminform.setValid_values("null");
			System.out.println("==>"+npa_values.length); 
			for(int i = 0; i < npa_values.length; i++) {
				if(npa_values[i].getDaysFrom() > 0) { 
					req.setAttribute("NpaObject",npa_values);
					req.setAttribute("MY","Days");
					System.out.println("Days from values====>"+npa_values[i].getDaysFrom());
					//txt_from[i].setText(String.valueOf(npa_values[i].getDaysFrom()));
					//combo_from_daymonth[i].setSelectedIndex(0);
				} else if(npa_values[i].getMonthsFrom() > 0) {
					req.setAttribute("NpaObject",npa_values);
					req.setAttribute("MY","Months");
					System.out.println("Months from==>"+npa_values[i].getMonthsFrom());
					//txt_from[i].setText(String.valueOf(npa_values[i].getMonthsFrom()));
					//combo_from_daymonth[i].setSelectedIndex(1);
				} 
				
				if(npa_values[i].getDaysTo() > 0) {
					req.setAttribute("NpaObject",npa_values);
					req.setAttribute("MY","Days");
					System.out.println("Days to===>"+npa_values[i].getDaysTo());
					//txt_to[i].setText(String.valueOf(npa_values[i].getDaysTo()));
					//combo_to_daymonth[i].setSelectedIndex(0);
				} else if(npa_values[i].getMonthsTo() > 0) {
					req.setAttribute("NpaObject",npa_values);
					req.setAttribute("MY","Months");
					System.out.println("Months to===>"+npa_values[i].getMonthsTo());
					//txt_to[i].setText(String.valueOf(npa_values[i].getMonthsTo()));
					//combo_to_daymonth[i].setSelectedIndex(1);
				} 
				
				//txt_pro_amt[i].setText(String.valueOf(npa_values[i].getProvPerc()));
				req.setAttribute("NpaObject",npa_values);
				
			}
		}
		else
		{
			npaadminform.setValid_values("NoValues");
		}
		
	}
	public void NPASummaryInitalForm(HttpServletRequest req,LoansDelegate delegate,String path,int days) throws RemoteException
	   {
		   req.setAttribute("NPAProcessedDate",delegate.getNPAProcessedDate(days));
		   req.setAttribute("pageId",path); 
	   }
	private NPAObject[] getSubmitData( LoanNpaAdminForm npaadminform) 
	{
	System.out.println("txt_to======>====>"+npaadminform.getTxt_to()); 	
	String txt_to[]={npaadminform.getTxt_to(),npaadminform.getTxt_to1(),npaadminform.getTxt_to2(),npaadminform.getTxt_to3(),npaadminform.getTxt_to4()};
	String txt_from[]={npaadminform.getTxt_from(),npaadminform.getTxt_from1(),npaadminform.getTxt_from2(),npaadminform.getTxt_from3(),npaadminform.getTxt_from4()};
	String  combo_from_daymonth[]={npaadminform.getTxt_fromdaymonth(),npaadminform.getTxt_fromdaymonth1(),npaadminform.getTxt_fromdaymonth2(),npaadminform.getTxt_fromdaymonth3(),npaadminform.getTxt_fromdaymonth4()};
	String txt_pro_amt[]={npaadminform.getTxt_provamt(),npaadminform.getTxt_provamt1(),npaadminform.getTxt_provamt2(),npaadminform.getTxt_provamt3(),npaadminform.getTxt_provamt4()};
	String lbl_asset_type[]={npaadminform.getTxt_standard(),npaadminform.getTxt_substandard(),npaadminform.getTxt_default1(),npaadminform.getTxt_default2(),npaadminform.getTxt_default3()};
	String combo_to_daymonth[]={npaadminform.getTxt_todaymonth(),npaadminform.getTxt_todaymonth1(),npaadminform.getTxt_todaymonth2(),npaadminform.getTxt_todaymonth3(),npaadminform.getTxt_todaymonth4()};
	
	NPAObject npa_values[]=new NPAObject[txt_to.length];
	
	System.out.println("txt to length====>"+txt_to.length);
	System.out.println("txt to length====>"+txt_to[0]);
	System.out.println("txt to length====>"+txt_to[1]);
	System.out.println("txt to length====>"+txt_to[2]);
	System.out.println("txt to length====>"+txt_to[3]);
		  
	for(int i=0;i<npa_values.length;i++)
	{
		npa_values[i] = new NPAObject();
		npa_values[i].setAcType(npaadminform.getTxt_acctype());
		
		if(combo_from_daymonth[i].equalsIgnoreCase("Days")) 
		{
			if(txt_from[i]!=null){
			npa_values[i].setDaysFrom(Integer.parseInt(txt_from[i]));
			
			npa_values[i].setMonthsFrom(-1);
			}
		} 
		else 
		{
			npa_values[i].setDaysFrom(-1);
			npa_values[i].setMonthsFrom(Integer.parseInt(txt_from[i]));
		}
		if(combo_to_daymonth[i].equalsIgnoreCase("Days")) 
		{
			npa_values[i].setDaysTo(Integer.parseInt(txt_to[i]));
			npa_values[i].setMonthsTo(-1);
		} else 
		{
			npa_values[i].setDaysTo(-1);
			npa_values[i].setMonthsTo(Integer.parseInt(txt_to[i]));
		}	
		
		npa_values[i].setAssetCode(i+1);
		npa_values[i].setAssetDesc(lbl_asset_type[i]);
		npa_values[i].setProvPerc(Double.parseDouble(txt_pro_amt[i]));
		npa_values[i].setFromDate(null);
		npa_values[i].setToDate(null);
	}
		return npa_values;
	}
	private void showForm(HttpServletRequest req)
	{
		Object data[] = new Object[12];
		Map mobjectmap=null;
		List listobj=new ArrayList();
		try
		{
			 
			System.out.println("array_loanreportobject==========----------->"+array_loanreportobject);
			double balance_os_amt=0,amt_odue=0,npa_amt=0,total_prov_amt=0;
			if(array_loanreportobject!=null)
			{	
				for (int i = 0; i < array_loanreportobject.length; i++) 
				{
					for(int j=0;j<array_loanreportobject[i].length;j++)
					{
						mobjectmap=new HashMap();
						if(j==0)
						{
						
							mobjectmap.put("SrlNo",new Integer(i+1));
							mobjectmap.put("LoanType",array_loanreportobject[i][j].getName());
							mobjectmap.put("NoofACs",array_loanreportobject[i][j].getAccNo());
							mobjectmap.put("BalanceOS",array_loanreportobject[i][j].getMaturityAmt());
							mobjectmap.put("AmtOD",array_loanreportobject[i][j].getPrincipalPaid());
						}
						mobjectmap.put("AssetCode",array_loanreportobject[i][j].getDepositAccNo());
						mobjectmap.put("AssetDesc",array_loanreportobject[i][j].getAccType());
						mobjectmap.put("NoofAcsperAssetCode",array_loanreportobject[i][j].getDepositDays());			
						mobjectmap.put("BalAmount",array_loanreportobject[i][j].getLoanBalance());
						mobjectmap.put("ProvPerc",array_loanreportobject[i][j].getDepositIntRate());
						mobjectmap.put("ProvAmount",array_loanreportobject[i][j].getLoanIntRate());
						if(j==array_loanreportobject[i].length-1)
							mobjectmap.put("TotalProvision",array_loanreportobject[i][j].getDepositAmt());
						listobj.add(mobjectmap);				
						
						if(array_loanreportobject[i][j].getDepositAccNo() != 1) 
						{
							npa_amt += 	array_loanreportobject[i][j].getLoanBalance();
						}
					}
						balance_os_amt += 	array_loanreportobject[i][0].getMaturityAmt();
						amt_odue += 	array_loanreportobject[i][0].getPrincipalPaid();
						total_prov_amt += 	array_loanreportobject[i][0].getDepositAmt();
				
				}
			}
				double percentage = npa_amt/balance_os_amt;
			 
						mobjectmap.put("Loan Type","Grand Total:");
						mobjectmap.put("BalanceOS",balance_os_amt);
						mobjectmap.put("AmtOD",amt_odue);
						mobjectmap.put("AssetDesc","NPA AMT/PER(%)");
						mobjectmap.put("BalAmount",npa_amt);
						mobjectmap.put("ProvPerc",percentage);
						mobjectmap.put("Total Provision",total_prov_amt);
					
						listobj.add(mobjectmap);
						req.setAttribute("NpaSummary",listobj);
		}catch(Exception ex){ex.printStackTrace();}
	}
	public void NPAReportsInitialParam(HttpServletRequest req,LoansDelegate delegate,int days) throws RemoteException
	{
		  System.out.println("===NPAReportsInitialParam==="+days);
		  req.setAttribute("NPAProcessDate",delegate.getNPAProcessedDate(days));
		  req.setAttribute("NPACode",delegate.getNPACode());
		  req.setAttribute("LoanModuleCode",delegate.getLoanmodulecode(2));
		  
	}
	
	void display_table(LoanReportObject loan_obj[],HttpServletRequest req)
	{
		Map mapobj=null;
		List list=new ArrayList();
		if(loan_obj!=null){
			for(int i=0;i<loan_obj.length;i++)
			{
				mapobj=new HashMap();
				mapobj.put("No",Integer.valueOf(String.valueOf(i+1)));
				mapobj.put("Number",Integer.valueOf(String.valueOf(loan_obj[i].getAccNo())));
				mapobj.put("Name",loan_obj[i].getName());
				mapobj.put("LoanType",loan_obj[i].getAccType());
				mapobj.put("Sanctioned",Double.valueOf(String.valueOf(loan_obj[i].getSanctionedAmount())));
				mapobj.put("Dateof",loan_obj[i].getDisbursementDate());
				mapobj.put("Numberof",loan_obj[i].getNoInstallments());
				mapobj.put("Installment",Double.valueOf(String.valueOf(loan_obj[i].getInstallmentAmount())));
				if(loan_obj[i].getInterestType()==0)
					mapobj.put("TypeOf","EMI");
				else
					mapobj.put("TypeOf","RB");
				       
				mapobj.put("Amount",Double.valueOf(String.valueOf(loan_obj[i].getDisburseAmount())));
				mapobj.put("DisbAmt",Double.valueOf(String.valueOf(loan_obj[i].getDisburseAmount())));
				mapobj.put("PrinOverDue", Double.valueOf(String.valueOf(loan_obj[i].getPrnOverDueAmt())));
				mapobj.put("IntUptoDate", loan_obj[i].getIntUptoDate());
				mapobj.put("IntOverdueAmt",  Double.valueOf(String.valueOf(loan_obj[i].getIntOverDueAmt())));
				mapobj.put("IntOverduePeriod", loan_obj[i].getOverdueperiod());
				mapobj.put("TranDate",String.valueOf(loan_obj[i].getTransactiondate()));
				mapobj.put("NPASince",String.valueOf(loan_obj[i].getNpasincedate()));
				mapobj.put("NPATowards",String.valueOf(loan_obj[i].getNpatowards()));
				mapobj.put("Loan",Double.valueOf(String.valueOf(loan_obj[i].getLoanBalance())));
				mapobj.put("Provision",Double.valueOf(String.valueOf(loan_obj[i].getProvisionReq())));
				list.add(mapobj);
			}
			
			req.setAttribute("NPAReport",list);
		}
	}
	private void JwelReport(Vector gold,HttpServletRequest req)
	{
		Map mapgold=null;
		List jwelreport=new ArrayList();
		System.out.println("Gold====>"+gold); 
		for(int i=0;i<gold.size();i++)
		{
			mapgold=new HashMap();
			GoldObject gobj=(GoldObject)gold.elementAt(i); 
			System.out.println("gobj.getApprisalDate()===>"+gobj.getApprisalDate());
			mapgold.put("Date",gobj.getApprisalDate());
			mapgold.put("AccTypeandNo",gobj.getAccType()+" "+String.valueOf(gobj.getAccNo()));
			mapgold.put("AppriserName",String.valueOf(gobj.getApprisersName()));

			Vector vec=gobj.getGoldDetVector();
			for(int j=0;j<vec.size();j++)
			{
				Object obj[]=(Object[])vec.elementAt(j);
				mapgold.put("Description",obj[1]);
				mapgold.put("GrossWeight",obj[2]);
				mapgold.put("NetWeight",obj[3]);
				mapgold.put("Rate",obj[4]);
				mapgold.put("NetGold",obj[5]);
				//jwelreport.add(mapgold);
				/*mapgold.put("Date","");
				mapgold.put("AccTypeandNo","");
				mapgold.put("AppriserName","");*/
			}
			mapgold.put("Date", gobj.getApprisalDate());
			mapgold.put("AccTypeandNo", gobj.getAccType()+" "+gobj.getAccNo());
			mapgold.put("AppriserName", gobj.getApprisersName());
			/*mapgold.put("Description","");
			mapgold.put("GrossWeight","");
			mapgold.put("NetWeight","");
			mapgold.put("Rate","");
			mapgold.put("NetGold","");*/
			jwelreport.add(mapgold);
		}
		req.setAttribute("JewelReport",jwelreport);
	}
	
}