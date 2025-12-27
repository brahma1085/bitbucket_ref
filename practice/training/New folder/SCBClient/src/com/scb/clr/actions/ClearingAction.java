package com.scb.clr.actions;

import exceptions.RecordsNotFoundException;
import frontCounterServer.FrontCounterHome;
import frontCounterServer.FrontCounterRemote;
import general.Validations;

import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
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
import javax.swing.JOptionPane;

import masterObject.clearing.AckObject;
import masterObject.clearing.BankMaster;
import masterObject.clearing.BounceFineObject;
import masterObject.clearing.ChequeAckObject;
import masterObject.clearing.ChequeDepositionObject;
import masterObject.clearing.ClearingObject;
import masterObject.clearing.CompanyMaster;
import masterObject.clearing.DiscountCharges;
import masterObject.clearing.Reason;
import masterObject.clearing.ReasonLink;
import masterObject.clearing.ReasonMaster;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccountObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import masterObject.share.ShareCategoryObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.HomeFactory;

import com.scb.clr.forms.AckReconciliationForm;
import com.scb.clr.forms.AcknowledgementDataEntryForm;
import com.scb.clr.forms.AdminForm;
import com.scb.clr.forms.BounceReasonForm;
import com.scb.clr.forms.BouncedChequePostForm;
import com.scb.clr.forms.BouncedLetterForm;
import com.scb.clr.forms.BouncedregForm;
import com.scb.clr.forms.BundledDataEntry;
import com.scb.clr.forms.ChequeDiscountingForm;
import com.scb.clr.forms.ChequeWithdrawalForm;
import com.scb.clr.forms.ClearingDepositionForm;
import com.scb.clr.forms.ClearingDispatchForm;
import com.scb.clr.forms.ClearingIdentificationForm;
import com.scb.clr.forms.ClearingPostForm;
import com.scb.clr.forms.CombinedChequeForm;
import com.scb.clr.forms.ControlNoForm;
import com.scb.clr.forms.ControlNumbersQuery;
import com.scb.clr.forms.InwardstmtForm;
import com.scb.clr.forms.LateReturnsForm;
import com.scb.clr.forms.MailingChargesForm;
import com.scb.clr.forms.OutStationForm;
import com.scb.clr.forms.OutStationLetterForm;
import com.scb.clr.forms.OutwardstmtForm;
import com.scb.clr.forms.RecievedChequeEntryForm;
import com.scb.clr.forms.RecievedECSForm;
import com.scb.clr.forms.SelectiveForm;
import com.scb.clr.forms.Test;
import com.scb.clr.forms.UnIdentify;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.ClearingDelegate;
import com.scb.designPatterns.CommonDelegate;
import com.scb.designPatterns.CustomerDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.LockerDelegate;
import com.scb.props.MenuNameReader;

  

public class ClearingAction extends Action 
{
	
	ClearingDelegate delegate = ClearingDelegate.getInstance();
	CustomerDelegate customerDelegate;
	ModuleObject[] module = null ;
	Object[][] arr_object = null;
	AccountObject accountobject_crd;
	ModuleObject[] module_debit = null ;
	BankMaster[] bankMasters = null;
	BranchObject array_branchobject[];
	Reason array_reason[] = null;
	ModuleObject[] loan_module = null;
	Vector vector_all_reasons = new Vector(0,1);
	ChequeDepositionObject[] chequedepositionobject;
	ChequeDepositionObject depositionObject;
	 private String path;
	 double fine_amount=0.0;
	 UserVerifier uv=new UserVerifier();
	 ClearingObject[] array_clearingobject;
	 NumberFormat nf ;
	 int arr1[],arr2[];
	 int array_int_default_reasons[] = new int[9];
	 String string_qry=null;
	 AckObject ackobject;
	 java.util.Hashtable hash_bank;
	 java.util.Hashtable hash_branch;
	 Enumeration enume_bank, enum_branch;
	 String bankName;
	 int n=2;
	 private HttpSession session;
	 Vector vector_fine_amt = new Vector(0,1);//setting fine amount w.r.t selected reason codes..
	 Vector vector_link_codes = new Vector(0,1);
	 Vector vector_normal_reasons = new Vector(0, 1);
	 String user,tml;
	 AdministratorDelegate admDelegate;
	 Map user_role;
	 FrontCounterDelegate fcDelegatenew;	
	 public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception 
	 {	
		System.out.println("m inside execute method");
		
	    	
			
			
	// Coding By Balu Started on 04/01/2009
              
		
		System.out.println("loading14 ........>>"+actionMapping.getPath());
		String perPath=MenuNameReader.getScreenProperties("0001");
		String sigPath=MenuNameReader.getScreenProperties("0007");
		session=httpServletRequest.getSession();
		String tml=(String)session.getAttribute("UserTml");
		String uid=(String)session.getAttribute("UserName");
		bankName=(String)session.getAttribute("BankName");
		String bankLocation=(String)session.getAttribute("BankLocation");
		System.out.println("====UserName====="+uid+"==SysTime==="+delegate.getSysTime());
		
		try{
    		synchronized(httpServletRequest) {
				
			
    		if(uid!=null){
    			System.out.println("m in if====>>"+uid);
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(uid,"CL");
    			if(user_role!=null){
    			httpServletRequest.setAttribute("user_role",user_role);
    			System.out.println("pageid======>>>"+httpServletRequest.getParameter("pageId"));
    			if(httpServletRequest.getParameter("pageId")!=null){
    				System.out.println("req.getParameter--in FC ACtion===>"+httpServletRequest.getParameter("pageId"));
    				httpServletRequest.setAttribute("accesspageId",httpServletRequest.getParameter("pageId").trim());
    			}
				
    			}
    			else
    			{
    				//to error page for display
    				path=MenuNameReader.getScreenProperties("0000");
    		        
    		          // setErrorPageElements("Sorry, You do not have access to this page",req,path);
    		           return actionMapping.findForward(ResultHelp.getError());
    			}
    		}
    		fcDelegatenew=new FrontCounterDelegate();
    		httpServletRequest.setAttribute("acccat",fcDelegatenew.getAccCategories());
    		httpServletRequest.setAttribute("accsubcat",fcDelegatenew.getSubCategories());
    		}
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	
	if(actionMapping.getPath().equalsIgnoreCase("/Clearing/ChequeDeposition"))
	{	
		try
		{
		
		ClearingDepositionForm form = (ClearingDepositionForm)actionForm;
		System.out.println("page id is---"+form.getPageId());
		form.setError_message(null);
		form.setError_flag(0);
		Iterator itr_param = (Iterator)httpServletRequest.getParameterNames();
		
		while(itr_param.hasNext())
		{
			      String str = (String)itr_param.next();
			      if(str.startsWith("chk")) 
			      {
			    	  System.out.println(str  + "-----------------" + httpServletRequest.getParameter(str) );
			      }
		}  
		if(MenuNameReader.containsKeyScreen(form.getPageId()))
		{
			
			String path = MenuNameReader.getScreenProperties(form.getPageId());
			System.out.println("path is---"+path);
			System.out.println( form.getCredit_account_type() );
			
			if(module == null)
			{
				System.out.println("loaded..........@@@");
				
				if(form.getInternal_clearing() != null) 
					module = ClearingDelegate.getInstance().getMainModule(4);
				else if(form.getLaon_credit()!=null)
					module = ClearingDelegate.getInstance().getMainModule(2);
				else 
					module = ClearingDelegate.getInstance().getMainModule(1);
			}								
			if(loan_module == null) 
			{
				System.out.println("in loan module...");
				loan_module = ClearingDelegate.getInstance().getMainModule(3);
			}
			httpServletRequest.setAttribute("Main_Module_code", module);
			httpServletRequest.setAttribute("Loan_Module_code", loan_module);
			
			System.out.println(form.getInternal_clearing() + " selected clearing modules -------------------- " );
			
			if(form.getInternal_clearing()!=null)                
			{
				
				System.out.println(form.getInternal_clearing()+ "internal clearing value ..NOwwwwwww... ");
				module_debit = setDebitAccountDetail();
				bankMasters=delegate.getBankDetails(-5,0,0);
				form.setCity_code("000");
				form.setBank_code("000");
				form.setBranch_code("001");
				form.setBankname(bankMasters[0].getBankName());
				form.setBranchname("HeadOffice");
				httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
				httpServletRequest.setAttribute("Debit_Module_code", module_debit);
				
			}
			/*  forms are submitted according to the flag  
			 *  1 -   to get Bank name , branch name to given MICR Code
			 * 	2 -   to get the Cheque Detail for given Control NO 
			 * 	3 -   to get the Account Details for given Account No
			 * 	4 -   to get the debit Account type Details for given Account No
			 *  5 -   to get the debit Account no Details  
			 *  6 -   to get the Cheque No details for given account number
			 *  7 -   to get cheque details for given cheque num
			 *  8 -    to submit the form  
			 *  9 -     update the from 
			 *  10 -    delete the form 
			 *  11 -   to get the discount amount
			 *  12 -   to get the pay order details
			 *  13 - To check Entered Amount With Req. Amount 
			 *  15 -   to get the Loan Account Details for given loan Acc num
			 * */
			
			
			
			if(form.getForm_flag() == 1)
			{
				
				if(!(form.getBank_code().equalsIgnoreCase("000")))
				{
					
					String code = form.getCity_code()+ form.getBank_code()+form.getBranch_code() ;
					System.out.println("<----code value just now--->"+code);
					
					HashMap map_result   = delegate.getCityBankBranchDetail(code);					
					
					if(map_result.containsKey(code.substring(0, 3))) 
					{
						System.out.println("city code==>"+map_result.get(code.substring(0, 3)));
						form.setCityname((String)(map_result.get(code.substring(0, 3))));
					}
					else
					{
						form.setCityname("");
						form.setCity_code("");
						form.setError_message("Bank Code Not Found");
					}
					if(map_result.containsKey(code.substring(3,6)))  
					{ 
							form.setBankname((String)(map_result.get(code.substring(3, 6))));
					}
					else
					{
						form.setBankname("");
						form.setBank_code("");
						form.setError_message("Bank Code Not Found");
					}
					if(map_result.containsKey(code.substring(6,9)+1))  
					{
						
						form.setBranchname((String)(map_result.get(code.substring(6,9)+1)));
					}
					else
					{
						form.setBranch_code("000");
						form.setBranchname("");
						form.setError_message("Enter Branch Name");
					}
				}
				else
				{
					form.setError_message("Enter Valid Bank code");
					form.setBank_code("");
				}
				
				
			} 
			else if(form.getForm_flag()==2)
			{
				System.out.println("inside ctrl num");
				
				if(form.getCtrl_no()!=0)
				{
				ChequeDepositionObject[] chq_obj = delegate.retrieveChequeDetails(form.getCtrl_no(),0);
				System.out.println("afetr fetching details");
				
				if(chq_obj!= null)
				{
					System.out.println("inside not null");
					String code = chq_obj[0].getBankCode();
					System.out.println("bank code from object==>"+chq_obj[0].getBankCode());
					System.out.println("bank code===>"+code);
					form.setMicrcode(code); 
					
					//form.setCtrl_no( (int) chq_obj[0].getControlNo()  );
					
					if(code!=null)
					{
							HashMap map_result = delegate.getCityBankBranchDetail(code);					
					
							if(map_result.containsKey(code.substring(0,3))) 
							{
								System.out.println("inside bank name---"+(map_result.get(code.substring(0, 3))));
								form.setCityname((String)(map_result.get(code.substring(0, 3))));
							}
							if(map_result.containsKey(code.substring(3,6)))  
							{
								System.out.println("inside bank name 22---"+(map_result.get(code.substring(3,6))));
								form.setBankname((String)(map_result.get(code.substring(3,6))));
							}
							if(map_result.containsKey(code.substring(6,9)+1))  
							{
								System.out.println("inside branch name 33---"+(map_result.get(code.substring(6,9)+1)));
								form.setBranchname(chq_obj[0].getBranchName());
							}
							form.setCity_code(code.substring(0,3));
							form.setBank_code(code.substring(3,6));
							form.setBranch_code(code.substring(6,9));
							System.out.println("after branch details");
							form.setBranchname(chq_obj[0].getBranchName()); 
					}
					if(chq_obj[0].getMultiCredit()!=null)
					{
						if(chq_obj[0].getMultiCredit().equalsIgnoreCase("T"))
						{
							System.out.println("inside multi credit");
							form.setMulticredit("true");
							form.setCompany_name( chq_obj[0].getCompanyName());
						}
					}	
					if(chq_obj[0].getLoanAcc()!= null && chq_obj[0].getMultiCredit().equals("F")) 
					{
						System.out.println("inside loan ac");
						form.setLaon_credit("true");
						form.setLaon_credit_ac_type(Integer.parseInt(chq_obj[0].getLoanAcc()));
						form.setLaon_credit_no(chq_obj[0].getLoanAcc_No());
					}
					form.setCredit_account_type(chq_obj[0].getCreditACType());
					form.setCredit_account_no(chq_obj[0].getCreditACNo());
					form.setAmount(chq_obj[0].getTranAmt());
					form.setPaydetail(chq_obj[0].getPayeeName());
					form.setDebit_chq_date(chq_obj[0].getQdpDate()); 
					form.setChqddpo(chq_obj[0].getChqDDPO());
					
					if(chq_obj[0].getDocDestination()==1)
					{
						System.out.println("inside clg typ");
						form.setInternal_clearing("true");
						form.setDebit_account_type(chq_obj[0].getDebitACType());
						form.setDebit_account_no(chq_obj[0].getDebitACNo());
						form.setBankname(chq_obj[0].getBranchName());
						getDebitDetail(form, httpServletRequest);
						form.setDebit_chq_date(chq_obj[0].getQdpDate());
						form.setDebit_chequeno(chq_obj[0].getQdpNo());
						getChequeDetail(form ,httpServletRequest);
					
					}  
					else if(chq_obj[0].getClgType().equalsIgnoreCase("S") )
					{
						form.setOutstation_chq("true");
						form.setChequedate(chq_obj[0].getQdpDate());
						form.setChequeno(chq_obj[0].getQdpNo());
					}
					else
					{
						form.setChequedate(chq_obj[0].getQdpDate());
						form.setChequeno(chq_obj[0].getQdpNo()) ;
					}	
					if(chq_obj[0].getBounceInd().equalsIgnoreCase("T")) 
					{
						System.out.println("inside bounce");
						form.setBounce("true");
						System.out.println("fine======"+(chq_obj[0].getReasonFine()));
						form.setBounceFine(String.valueOf(chq_obj[0].getReasonFine()));
						
					}
					if(chq_obj[0].getDiscInd().equalsIgnoreCase("T") )
					{
						form.setDiscount("true");
						form.setDiscountamount( chq_obj[0].getDiscAmt());
						form.setDiscountcharge( chq_obj[0].getDiscChg() );
					} 
					
				}
				else
				{
					System.out.println("inside null");
					form.setError_message("Control Number Not Found");
					form.setCtrl_no(0);
				}
			  }
			  else
			  {
				   form.setError_message("New Control Number");
					form.setCtrl_no(0);
			  }
			} 
			else if(form.getForm_flag()==3) 
			{
				if((String.valueOf(form.getCredit_account_type()).startsWith("1003") || String.valueOf(form.getCredit_account_type()).startsWith("1005")|| String.valueOf(form.getCredit_account_type()).startsWith("1006")) && (form.getCredit_account_no())!=0)
				{
					//txt_cr_ac_no.requestFocus();
					System.out.println("this not a valid account numberrerr");
					form.setError_message("This Is Not A Valid A/c Number For  "+form.getCredit_account_type());
					form.setCredit_acc_type_name(" ");
					form.setCredit_account_no(0);
				}
				else
				{	
					if((form.getCredit_account_no())== 0  && (form.getCredit_account_type().startsWith("1004")))
					{
						
						System.out.println( " inside the clr delegate , flag 3  "+ form.getCredit_account_no() + "--------" + form.getCredit_account_type()) ;
						form.setCredit_acc_type_name("New Account");
					} 
					else if((form.getCredit_account_no()==0) && (form.getCredit_account_type().startsWith("1001"))) 
					{
						
							System.out.println( " inside the clr delegate , flag 3  "+ form.getCredit_account_no() + "--------" + form.getCredit_account_type()) ;
						
							form.setError_message("You Can't Open An A/c, Give A/c Number" );
							form.setCredit_account_no(0);
							form.setCredit_acc_type_name(" ");
					} 
					else 
					{
					
						 if(form.getCredit_account_type().startsWith("1012"))
						 {
							String val = delegate.getGLName(form.getCredit_account_type(), form.getCredit_account_no() );
						
								if( val == null)
								{
									form.setError_message("No Such GL Found With GL Code" );
									form.setCredit_acc_type_name(" ");
								} 
								else
								{    
									form.setCredit_acc_type_name( val );
								}	
						  }  
						  else 
						  {
							  	AccountObject acc_obj = delegate.getAccount(form.getCredit_account_type(), form.getCredit_account_no(),delegate.getSysDate());
						
							  	if(acc_obj == null)
							  	{
							  		System.out.println("inside account object-----baluuuu");
							  		form.setError_message("No Such A/c Found With A/c Number  " + form.getCredit_account_no());
							  		form.setCredit_account_no(0);
							  		form.setCredit_acc_type_name("");
							
							  	} 
							  	else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
							  	{
							  		form.setError_message("Account Is Closed" );
							  		form.setCredit_account_no(0);
							  		form.setCredit_acc_type_name("");
							  	}
							  	else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
							  	{
							  		form.setError_message("Account Is Freezed" );
							  		form.setCredit_account_no(0);
							  		form.setCredit_acc_type_name("");
							  	} 
							  	else 
							  	{
									form.setCredit_acc_type_name(acc_obj.getAccname());
							  		CustomerMasterObject cust = delegate.getCustomerDetail(acc_obj.getCid());
							  		httpServletRequest.setAttribute("personalInfo", cust);
							
							  	}
						    }
					
					
						}
				
						if(form.getReason_codes()!= null) 
						{
					
							StringTokenizer tok = new StringTokenizer(form.getReason_codes() ,"," );
					
							while(tok.hasMoreTokens())
							{
								System.out.println(tok.nextToken()+ " reason code ");
							}
						}
					}
			}
			else if(form.getForm_flag()==4)
			{
				if(module_debit==null)
				{
					module_debit = setDebitAccountDetail();
				}
				httpServletRequest.setAttribute("Debit_Module_code", module_debit );
			} 
			else if(form.getForm_flag() == 5)
			{
				
				double val = 0.0;
				
				if(form.getCredit_account_type().startsWith("1016") )
				{
					
					val =((Double)delegate.getCommission(form.getDebit_account_type(),form.getDebit_account_no(),form.getCredit_account_type(),form.getAmount(),form.getToday_date(),1)).doubleValue() ;
				    form.setPocommision(val);
				}
				
				getDebitDetail( form, httpServletRequest );
			}
			else if(form.getForm_flag()==6)
			{
				
				System.out.println("form flag 6 ");
				if(Validations.validDate( Validations.DateAdd(Validations.checkDate( form.getDebit_chq_date()),Integer.parseInt(delegate.getGenParam().toString())),form.getToday_date() )==-1 ){		           
		        	
					System.out.println("inside the cheque out dated -- form 6");
					
					form.setError_message("Cheque Is Out Dated" );
		    	}
			}
			else if(form.getForm_flag()==7)
			{
				getChequeDetail(form,httpServletRequest);
			}
			else if(form.getForm_flag()==8)   //frm submit
			{
				
				int ctl=setControlNo(form,httpServletRequest,tml,uid);
				if(ctl!=0 && ctl!=1)
				{
					form.setError_message(" Note Down Control Number  "+ctl);
					form.setError_flag(1);
				}	
			}
			else if(form.getForm_flag()==9)
			{
				int ctl=setControlNo(form,httpServletRequest,tml,uid);
				
				if(ctl!=0)
				{
					form.setError_message("Control Number Updated Successfully");
					form.setError_flag(1);
				}
			}
			else if(form.getForm_flag()==10)
			{
				System.out.println("inside delete");
				int k = delegate.deleteChequeData(form.getCtrl_no() );
				if(k<0) 
				{
					form.setError_message("Error While Deleting");
				}
				else
				{	
					form.setError_flag(1);
					form.setError_message("Control Number Sucessfully Deleted");
				}
			}
			else if(form.getForm_flag()==11)
			{
				System.out.println(" inside the flag 11 discount  " + form.getCredit_account_type()  + "-----   discount amount " + form.getDiscountamount() );
				double val = delegate.DiscountChargeCalculation( form.getCredit_account_type() , form.getDiscountamount() );
				form.setDiscountcharge(val);
			}
			else if(form.getForm_flag()==12)
			{
				PayOrderObject pay_order = delegate.getPayOrderInstrn(form.getPay_order_no()); 
				if(pay_order == null)
				{
					form.setError_message("Pay Order Not Found");
				}
				else 
				{	
					form.setPay_order_amt(pay_order.getPOAmount());
					form.setPo_date(pay_order.getPODate());
					if(Validations.dayCompare( form.getToday_date(),pay_order.getPOValidUpTo())< 0)
					{
						form.setError_message("Pay Order Out Dated");
					}
					else if(pay_order.getPOStop().equals("T"))
					{
					
						form.setError_message("Payment Stopped By Drawer");
					}
					else if(pay_order.getPOCancel().equals("T")) 
					{
					
						form.setError_message("Pay Order Is Blocked");	
					} 
					else if(pay_order.getPOCshIndicator() == 1)
					{
					
						form.setError_message("Pay Order Is Closed");
					}
					else if((form.getAmount())!=(pay_order.getPOAmount()))
					{
					
						form.setError_message("Pay Order Is Bounced");
					}
				}
			} 
			else if(form.getForm_flag()==13)
			{
				if(form.getMulticredit()==null)
				{
				
					if(form.getCredit_account_type().startsWith("1004")) 
					{
						System.out.println("inside the rd loan credit laon") ;
					
						if(form.getCredit_account_no()==0)
						{
							double min_amt = 0.0;
							module = ClearingDelegate.getInstance().getMainModule(1);
							
							for(int i =0;i<module.length;i++)
							{
								if(module[i].getModuleCode().equalsIgnoreCase(form.getCredit_account_type()))
								{
									min_amt = module[i].getMinAmount();
									break;
								}
							}
							System.out.println( min_amt + "  minimum amount of the given rd   "  + form.getAmount()   );
							
							if(form.getAmount() < min_amt) 
							{
								form.setError_message("Your Amount Is Less To Open An Account");
								form.setAmount(0.0);
							}  
						} 
						else 
						{
							AccountObject ac_obj = delegate.getAccount( form.getCredit_account_type(), form.getCredit_account_no(), form.getToday_date() );
						
							  if(ac_obj != null)
							  {
								if(form.getAmount() < ac_obj.getAmount())  
								{
									form.setError_message(" Your Installment Amount Is "+ac_obj.getAmount() +"\n You Are Paying Less Than That");
								} 
								else if(form.getAmount()>((ac_obj.getAmount() *  ac_obj.getDepositdays())+ac_obj.getIntAmount() -   ac_obj.getShadowBalance())) 
								{
									form.setError_message("  Paid Amount Is More Than Aamount To Pay");
								} 
								else if((form.getAmount()%ac_obj.getAmount())!= 0) 
								{
									form.setError_message(" Amount Is Not Multiple Of Installment Amount");
								}
							  }
							}
					
						} 
						else if(form.getCredit_account_type().startsWith("1001")) 
						{
							ShareCategoryObject[] sh_rm = delegate.getShareCategories(0, form.getCredit_account_type() );
							double shareamt = 0.0; 
							if(sh_rm != null) 
							{
								shareamt = sh_rm[0].getShareVal();
								if(form.getAmount() < shareamt)
								{
									form.setError_message( "The Share Value Is Rs. "+shareamt);
								}
							}
						}
						if(form.getLaon_credit()!= null)
						{
							System.out.println("inside loan credit meeee");
							double total_amt=0.00d;
					
							total_amt = delegate.getLoanDetails(form.getAmount(),form.getLaon_credit_ac_type(),form.getLaon_credit_no(),form.getToday_date());
					
							if(total_amt!=0.00)
							{
								System.out.println("inside total amt first meeee");
						
								if(form.getAmount()>total_amt)
								{
									System.out.println("inside ifff");
									form.setError_message(" Cheque Amount Is More Than Loan Closure - "+total_amt );
									form.setAmount(0.0);
								}	
							}
							else
							{
								form.setError_message(" Cheque Amount Is More Than Loan Closure - "+total_amt);
								form.setAmount(0.0);
							}
					
					  } 
				  }
				  if(form.getInternal_clearing()!=null)
				  {
					  if(form.getAmount()>form.getBalance())
					  {
						  form.setError_message("Cheque Amount Is More Than The Balance Amount");
						  form.setAmount(0.0);
					  }
				  }
			}
			else if (form.getForm_flag() ==14 && form.getMulticredit().equalsIgnoreCase("Yes"))
			{			
				//focus_flag=false;
				CompanyMaster array_companymaster[] = null; 
				if (form.getCompany_name().length() > 0)
				{
					
					try
					{
					    //gets the company details by calling the server method. 
					
						array_companymaster = delegate.retrieveCompanyDetails(-1);
						
						//sets the company details
						if(array_companymaster!=null)
						{
							for (int i=0;i<array_companymaster.length;i++)
							{
								if(Integer.parseInt(form.getCompany_name())==array_companymaster[i].getCompanyCode())
								{
									form.setCname(array_companymaster[i].getCompanyName());
									break;
								}
								else
								{
									form.setCname("");
									
								}
								
							}	
						}	
						if (form.getCname().length() == 0)
						{
							
							form.setError_message("No Such Company Found");
							form.setCname("");
							form.setCompany_name("");
						}	
					
					}
					catch(Exception m)
					{
						
						m.printStackTrace();
					}
				
				}
				else 
				{
					form.setError_message("Enter Company Name");
				}
				
			}
			else if(form.getForm_flag()==15 && form.getLaon_credit()!=null)
			{	
				System.out.println("inside loan account");
				String loan_ac=String.valueOf(form.getLaon_credit_ac_type());
				int loan_no =(form.getLaon_credit_no());
				System.out.println("loan"+" "+loan_ac+" "+loan_no);
				AccountObject acc_obj = delegate.getAccount(loan_ac,loan_no,ClearingDelegate.getSysDate());
  	
				if(acc_obj==null)
				{
					form.setLaon_credit_no(0);
					form.setError_message("Loan A/c Not Found");
				}
				else if(acc_obj.getAccStatus().equalsIgnoreCase("C"))
				{
					form.setLaon_credit_no(0);
					form.setError_message("Account Is Closed");
				} 
				else if(acc_obj.getFreezeInd().equalsIgnoreCase("T"))
				{
					form.setError_message("Account Is Freezed");
					form.setLaon_credit_no(0);
				}
				else
				{
						if(acc_obj.getAccname().length()<=10)
							form.setLoan_acc_name(acc_obj.getAccname());
						else
							form.setLoan_acc_name(acc_obj.getAccname());
				}
			}
			else if(form.getForm_flag()==16)
			{
				module = setDebitAccountDetail();
				httpServletRequest.setAttribute("Main_Module_code",module);
				form.setCredit_acc_type_name("");
				form.setCredit_account_no(0);
			}
			else if(form.getForm_flag()==0)
			{
				module = ClearingDelegate.getInstance().getMainModule(1);
				httpServletRequest.setAttribute("Main_Module_code",module);
				form.setCredit_acc_type_name("");
				form.setCredit_account_no(0);
			}
			if(form.getCredit_account_type()!=null && form.getCredit_account_no()!= 0 ) 
			{
				System.out.println("index credit ac type--"+form.getCredit_account_type());
				System.out.println("index credit ac num---"+form.getCredit_account_no());
				setPersonalDetails(httpServletRequest,form.getCredit_account_type(),form.getCredit_account_no() );
				getSignatureDetails(httpServletRequest,form.getCredit_account_type(),form.getCredit_account_no());
			}	
			if(form.getDebit_account_type()!= null &&  form.getDebit_account_no()!= 0 ) 
			{
				  
				setPersonalDetails( httpServletRequest , form.getDebit_account_type(), form.getDebit_account_no() );
				getSignatureDetails(httpServletRequest,form.getCredit_account_type(),form.getCredit_account_no());
			}
			if(module_debit == null)
			{
				module_debit = setDebitAccountDetail();
			}
			httpServletRequest.setAttribute("Debit_Module_code", module_debit );
			Reason[] reason = delegate.getReasonDetails("1002001", 0 );
			httpServletRequest.setAttribute("Reason", reason );
			httpServletRequest.setAttribute("flag",sigPath);
			httpServletRequest.setAttribute("pageId",path);
			httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
			
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			return actionMapping.findForward(ResultHelp.getError());
		}
	}
	
	else if(actionMapping.getPath().equalsIgnoreCase("/Clearing/ChequeMenuDeposition")  )
	{	
		
		System.out.println("--.........");
		
		ClearingDepositionForm form = (ClearingDepositionForm)actionForm;
		form.setError_message(null);
		System.out.println("page id is---"+form.getPageId());
		
		if ( MenuNameReader.containsKeyScreen(form.getPageId())){
			
			String path = MenuNameReader.getScreenProperties(form.getPageId());
			
			System.out.println("path is---"+path);
			
			if ( module == null ) 
				module = ClearingDelegate.getInstance().getMainModule(1);
			
			
			if ( module != null ){
				
				System.out.println( module[0].getModuleCode() + "-----------" + module[0].getModuleAbbrv());
				
			}
			
			if(loan_module==null) 
			{
				loan_module = ClearingDelegate.getInstance().getMainModule(3);
			}  
			
			httpServletRequest.setAttribute("Main_Module_code", module );
			
			httpServletRequest.setAttribute("Loan_Module_code", loan_module  );
			httpServletRequest.setAttribute("pageId",path);
			
			
			module_debit = setDebitAccountDetail();
			httpServletRequest.setAttribute("Debit_Module_code", module_debit );
			
			
			if ( form.getDebit_account_type() != null &&  form.getDebit_account_no() != 0 ) {
				  
				getSignatureDetails( httpServletRequest , form.getDebit_account_type(), form.getDebit_account_no() );
				
			}
			Reason[] reason = delegate.getReasonDetails("1002001", 0 );
			httpServletRequest.setAttribute("Reason",reason);
			httpServletRequest.setAttribute("flag",sigPath);
			httpServletRequest.setAttribute("pageId",path);
			httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
			
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	
	else if(actionMapping.getPath().equalsIgnoreCase("/Clearing/BounceReason"))
	{		
		BounceReasonForm bounceReasonForm=(BounceReasonForm)actionForm;
		
		if ( MenuNameReader.containsKeyScreen(bounceReasonForm.getPageId()))
		{
			String path = MenuNameReader.getScreenProperties(bounceReasonForm.getPageId());
				System.out.println(  httpServletRequest.getParameter("bounce") +    "    got the value  from the param  ");
				System.out.println(" inside the action class bounce Reason ");
		
				Reason[] reason = delegate.getReasonDetails("1002001", 0 );
				
		
				httpServletRequest.setAttribute("bounce", httpServletRequest.getParameter("bounce") );
				System.out.println("bounce value"+httpServletRequest.getParameter("bounce"));
				httpServletRequest.setAttribute("Reason", reason );
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward("success");
		}
	}
	
	else if (actionMapping.getPath().equalsIgnoreCase("/Clearing/RecievedChequeEntryLink")) 
	{
		
		try
		{
		ChequeDepositionObject chDepositionObject=null;
		RecievedChequeEntryForm recievedChequeEntryForm =(RecievedChequeEntryForm)actionForm;
		recievedChequeEntryForm.setError_message("");
		recievedChequeEntryForm.setError_flag(0);
		module=delegate.getMainModule(4);
		System.out.println("page id is---"+recievedChequeEntryForm.getPageId());
		int custId=0;	
		
		if(MenuNameReader.containsKeyScreen(recievedChequeEntryForm.getPageId()))
		{	
			ChequeDepositionObject	chq_obj=null;
				 
			if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmCtrlNum")) 
			{	
					 	String ctrl =String.valueOf(recievedChequeEntryForm.getCtrl_no());
					 	System.out.println("inside ctrl num");
					 	if((recievedChequeEntryForm.getBooleanFlag())==1)
					 	{	
					 		System.out.println("inside boolean");
					 		chq_obj = delegate.getInwardData((recievedChequeEntryForm.getCtrl_no()),12);
							System.out.println("5.....................................................");
						}
						else if((recievedChequeEntryForm.getBooleanFlag())==0)
						{
							chq_obj = delegate.getInwardData(recievedChequeEntryForm.getCtrl_no(),1);
					 		System.out.println("afetr getinward data");
						}
					 	if(chq_obj==null)  
					 	{
					 			//recievedChequeEntryForm.setError_flag(1);
					 			recievedChequeEntryForm.setError_message(" Control Number Not Found " );
					 	}
					 	else if (chq_obj.getVeUser().equals("F")&& recievedChequeEntryForm.getBooleanFlag()==1) 
					 	{
					 		//recievedChequeEntryForm.setError_flag(1);
					 		recievedChequeEntryForm.setError_message("Acknowledgement Not Yet Reconciled");
						}
					 	else if (chq_obj.getDeTml() == null && recievedChequeEntryForm.getBooleanFlag()==1)
					 	{
					 		//recievedChequeEntryForm.setError_flag(1);
					 		recievedChequeEntryForm.setError_message("Acknowledgement Not Yet Verified");
					 	} 
					 	else if ((chq_obj.getMultiCredit()!=null) && chq_obj.getMultiCredit().equalsIgnoreCase("T"))  
					 	{
					 		
					 				System.out.println("inside multi credit");
//					 				String code = chq_obj.getBankCode();
//									HashMap map_result   = delegate.getCityBankBranchDetail( code );	
//									recievedChequeEntryForm.setCity_code( code.substring(0, 3)  );
//									recievedChequeEntryForm.setBank_code( code.substring(3, 6)  );
//									recievedChequeEntryForm.setBranch_code(  code.substring( 6, 9) );
//							
//									if ( map_result.containsKey( code.substring(0, 3) ) )  
//									{
//								
//										recievedChequeEntryForm.setBankname( (String)(map_result.get(  code.substring(0, 3))));
//									}
//							
//									if ( map_result.containsKey( code.substring(3, 6) ) )  
//									{
//								
//										recievedChequeEntryForm.setBankname( recievedChequeEntryForm.getBankname()+" "+ (String)(map_result.get(  code.substring(3, 6))));
//									}
//							
//									if ( map_result.containsKey( code.substring( 6, 9) ) ) 
//									{
//								
//										recievedChequeEntryForm.setBranchname(  (String)(map_result.get(  code.substring(6, 9))));
//									}
					 				
					 				
									recievedChequeEntryForm.setCtrl_no(Integer.parseInt(ctrl));
									recievedChequeEntryForm.setAckno((chq_obj.getAckNo()));
									recievedChequeEntryForm.setSend_to(String.valueOf(chq_obj.getSendTo()));
									recievedChequeEntryForm.setSource_name(chq_obj.getCompanyName());
									
									if(chq_obj.getCreditACType().equals("R")) 
									{
										recievedChequeEntryForm.setReturn_type("Y");// combo_returned_chq.setSelectedIndex(1);
										recievedChequeEntryForm.setPrev_ctrl_no(chq_obj.getCreditACNo());
										
									} 
									else 
									{
										recievedChequeEntryForm.setReturn_type("N");
										recievedChequeEntryForm.setPrev_ctrl_no(0);
										
									}
									// multi_credit = new MultiCredit(); table to display multi credit cheque
							
									recievedChequeEntryForm.setPaydetail( chq_obj.getPayeeName() );
									recievedChequeEntryForm.setBankname(chq_obj.getBankName());
									recievedChequeEntryForm.setCity_code(chq_obj.getBankCode().substring(0,3));
									recievedChequeEntryForm.setBank_code(chq_obj.getBankCode().substring(3,6));
									recievedChequeEntryForm.setBranch_code(chq_obj.getBankCode().substring(6,9));
									recievedChequeEntryForm.setBranchname(chq_obj.getBranchName());
									recievedChequeEntryForm.setAmount(chq_obj.getTranAmt());
									recievedChequeEntryForm.setChqddpo(chq_obj.getChqDDPO());
									
//									recievedChequeEntryForm.setDebit_chq_date( chq_obj.getQdpDate() );
//									recievedChequeEntryForm.setDebit_chequeno( chq_obj.getQdpNo() );
//									recievedChequeEntryForm.setDebit_account_type( chq_obj.getDebitACType() );
//									recievedChequeEntryForm.setDebit_account_no( chq_obj.getDebitACNo() );
							
							
//									if (!(chq_obj.getDebitACType().startsWith("1003")|| chq_obj.getDebitACType().startsWith("1004")|| chq_obj.getDebitACType().startsWith("1005") || chq_obj.getDebitACType().startsWith("1012")))
//									{
//											AccountObject acc_obj = delegate.getAccount(  chq_obj.getDebitACType(), chq_obj.getDebitACNo(), "12/02/2007");
//											recievedChequeEntryForm.setBalance(acc_obj.getAmount());
//											recievedChequeEntryForm.setShodowbalance(acc_obj.getShadowBalance());
//									}
					 		}
					 	else
					 		{
					 			
					 				recievedChequeEntryForm.setCtrl_no(Integer.parseInt(ctrl));
					 				recievedChequeEntryForm.setAckno((chq_obj.getAckNo()));
					 				recievedChequeEntryForm.setSend_to(String.valueOf(chq_obj.getSendTo()));
					 				recievedChequeEntryForm.setSource_name(chq_obj.getCompanyName());
					 				if (chq_obj.getCreditACType().equals("R") && (chq_obj.getCreditACNo()) > 0) 
					 				{
										recievedChequeEntryForm.setReturn_type("Y");
										recievedChequeEntryForm.setPrev_ctrl_no(chq_obj.getCreditACNo());
										
					 				} 
					 			
					 			else{
					 					recievedChequeEntryForm.setReturn_type("N");
					 					recievedChequeEntryForm.setPrev_ctrl_no(0);
										
					 				}
					 				recievedChequeEntryForm.setBankname(chq_obj.getBankName());
									recievedChequeEntryForm.setPaydetail(chq_obj.getPayeeName());
									recievedChequeEntryForm.setCity_code(String.valueOf(chq_obj.getBankCode()).substring(0, 3));
									recievedChequeEntryForm.setBank_code(String.valueOf(chq_obj.getBankCode()).substring(3, 6));
									recievedChequeEntryForm.setBranch_code(String.valueOf(chq_obj.getBankCode()).substring(6, 9));
									recievedChequeEntryForm.setBranchname(chq_obj.getBranchName());
									recievedChequeEntryForm.setAmount(chq_obj.getTranAmt());
									
									if(chq_obj.getChqDDPO()!=null)
									{
										if(chq_obj.getChqDDPO().equals("C")|| (chq_obj.getBounceInd().equals("T") && chq_obj.getChqDDPO().equals("P")))
										{
										System.out.println("9.....................................................");
										System.out.println("cheq");
										
										if (chq_obj.getChqDDPO().equals("C"))
										{
												recievedChequeEntryForm.setChqddpo("C");
												recievedChequeEntryForm.setPanelFlag(1);
											
										}
										else if (chq_obj.getChqDDPO().equals("P"))
										{
											recievedChequeEntryForm.setChqddpo("P");
											recievedChequeEntryForm.setPanelFlag(2);
										}	
										recievedChequeEntryForm.setDebit_chq_date(chq_obj.getQdpDate());
										recievedChequeEntryForm.setDebit_chequeno((chq_obj.getQdpNo()));

										System.out.println("debit ac"+ chq_obj.getDebitACType());
										recievedChequeEntryForm.setDebit_account_type(chq_obj.getDebitACType());
										recievedChequeEntryForm.setDebit_account_no(chq_obj.getDebitACNo());
										String val = null;
//										AccountObject accountobject =delegate.getAccount(chq_obj.getDebitACType(),chq_obj.getDebitACNo(),ClearingDelegate.getSysDate());
										if (chq_obj.getDebitACType().startsWith("1012")) 
										{
											val = delegate.getGL(chq_obj.getDebitACType(),chq_obj.getDebitACNo());

										} 
										else 
										{
											AccountObject accountobject = delegate.getAccount(chq_obj.getDebitACType(),chq_obj.getDebitACNo(),ClearingDelegate.getSysDate());
											if(accountobject!=null)
											{
												recievedChequeEntryForm.setBalance((accountobject.getAmount()));
												recievedChequeEntryForm.setShodowbalance(accountobject.getShadowBalance());
												custId=accountobject.getCid();
												System.out.println(chq_obj.getTranAmt()+"---------"+accountobject.getAmount());
												
												/*if((accountobject.getAmount()-chq_obj.getTranAmt())>0.0)
												{
													chq_obj.setBounceInd("F");
													System.out.println("positive??????????????????????????????????????");
												}
												else
												{
														System.out.println("negative!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
														chq_obj.setBounceInd("T");
												}*/
											
											}
											
										}

									}
									else if (chq_obj.getChqDDPO().equals("P")&& chq_obj.getBounceInd().equals("F")) 
									{
										
										
										recievedChequeEntryForm.setChqddpo("P");
										recievedChequeEntryForm.setPayorderDate(chq_obj.getQdpDate());
										recievedChequeEntryForm.setPayorderNum(String.valueOf(chq_obj.getQdpNo()));

										PayOrderObject payorderobject = delegate.getPayOrderInstrn(Integer.parseInt(recievedChequeEntryForm.getPayorderNum()));

										if (payorderobject != null && payorderobject.getPOSerialNo() > 0) 
										{
											recievedChequeEntryForm.setPayorderName(String.valueOf(payorderobject.getPOPayee()));
											recievedChequeEntryForm.setPayorderBalance(String.valueOf(payorderobject.getPOAmount()));

										}
										else 
										{
											recievedChequeEntryForm.setError_message("Pay Order Not Found");
										}
									}
									if(chq_obj.getBounceInd().equals("T"))
									{
											vector_normal_reasons = chq_obj.getReasonArray();
											fine_amount=chq_obj.getFineAmt();
											recievedChequeEntryForm.setBounceFine(String.valueOf(fine_amount));
											recievedChequeEntryForm.setBounce(true);
									}
									else
									{
										recievedChequeEntryForm.setBounceFine(String.valueOf(0.0));
										recievedChequeEntryForm.setBounce(false);
						
									}
								 }
								if(recievedChequeEntryForm.getCtrl_no()> 1 && recievedChequeEntryForm.getBooleanFlag()==0) 
								{
										System.out.println("ack");
										AckObject ackobject = delegate.getAcknowledgeAmount(chq_obj.getAckNo(),"I");
										if (ackobject == null)
										{	
//											recievedChequeEntryForm.setError_flag(1);
											recievedChequeEntryForm.setError_message("Acknowledgement Already Verified");
										}
										else
										{
											recievedChequeEntryForm.setAcknowledge_amt(Validations.changeFloat(ackobject.getAckEntered()));
											if (chq_obj.getVeUser().toString() == null)
												recievedChequeEntryForm.setAcknowledge_rem_amt(ackobject.getAckEntered()- ackobject.getTotal()+ chq_obj.getTranAmt());
											else
												recievedChequeEntryForm.setAcknowledge_rem_amt(ackobject.getAckEntered()- ackobject.getTotal());
										}	
									}
					 			}
					 		
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmMICRCode"))
				 {
					
					if ( !((recievedChequeEntryForm.getMicrcode())).substring(3, 6).equalsIgnoreCase("000") )
							{
								
								String code  = ((recievedChequeEntryForm.getMicrcode()));
								
								HashMap map_result   = delegate.getCityBankBranchDetail( (recievedChequeEntryForm.getMicrcode()) );					
								
								if(map_result.containsKey(code.substring(0,3))) 
								{
									recievedChequeEntryForm.setCityname((String)(map_result.get(code.substring(0,3))));
								}
								if(map_result.containsKey(code.substring(3,6)))  
								{
									recievedChequeEntryForm.setBankname((String)(map_result.get(code.substring(3,6))));
								}
								if(map_result.containsKey(code.substring(6,9)))  
								{
									recievedChequeEntryForm.setBranchname((String)(map_result.get(code.substring(6,9))));
								}
							}
						 
						
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmVerify"))
				 {
						int updated = 0;
						double col_bal=0.0;
						String post_ind= null;
						int int_value[] = null;
						
						int return_res=0;
						String account_type = null;
						int account_no = 0;
					try
					{
					
						if(recievedChequeEntryForm.getReturn_type()=="N")
						{
						String string_acctype = String.valueOf(recievedChequeEntryForm.getDebit_account_type());
						int int_verifycheque = 0;
						Vector chequeobject = delegate.getChequeNoDet(string_acctype,(recievedChequeEntryForm.getDebit_account_no()),(recievedChequeEntryForm.getDebit_chequeno()));
						System.out.println(chequeobject+"<--->"+chequeobject.size());
						
						if (chequeobject!= null && chequeobject.size() > 0) 
						{
							Enumeration enumeration = chequeobject.elements();
							Vector vector = null;
							if (enumeration.hasMoreElements()) 
							{
								vector = new Vector();
								while (enumeration.hasMoreElements()) 
								{
									
									ChequeObject element = (ChequeObject) enumeration.nextElement();
									
									if (element.getCheque_Del().equals("T"))
										int_verifycheque = 2;
									else if (element.getStop_payment().equals("T"))
										int_verifycheque = 4;
									else if ( element.getExpDate()!= null && Validations.dayCompare(ClearingDelegate.getSysDate(), element.getExpDate()) < 0)
										int_verifycheque = 3;
									
									System.out.println("int_verifycheque == "+ int_verifycheque);
									
									vector.add(element);
								}
								
							}
							
							if (vector.size() > 0)
								
							{
								
								if (int_verifycheque == 2)
								{

									System.out.println("2");
									recievedChequeEntryForm.setBounce(true);//  combo_bounce.setSelectedIndex(1);
									array_int_default_reasons[7] = 7;
									recievedChequeEntryForm.setBounce(true);//combo_bounce.setSelectedIndex(1);
									
									recievedChequeEntryForm.setError_message("Cheque Already Used/Closed");
								} 
								else if (int_verifycheque == 3) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[3] = 3;
									recievedChequeEntryForm.setBounce(true);
									recievedChequeEntryForm.setError_message("Cheque Date Expired Refer To Drawer");
								}
								else if (int_verifycheque == 4) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[4] = 4;
									recievedChequeEntryForm.setBounce(true);
								
									recievedChequeEntryForm.setError_message("Payment Stopped By Drawer");
								}
							}
						} 
						else 
						{
							array_int_default_reasons[6] = 6;
							recievedChequeEntryForm.setBounce(true);
						
							recievedChequeEntryForm.setError_message("Cheque Number Not Found For This Account");
							int_verifycheque = 1;
						}
					}
						
						 		System.out.println("1........Verify...........................");
						 		int int_value_pass = 0; 
							 		
							 	// checking the condition for Cheque, or pay order, for bounce , and prev control number greater than zero (  for outward return cheque  )   
							 	chq_obj=delegate.getInwardData((recievedChequeEntryForm.getCtrl_no()), 12);
							 	
							 if ((chq_obj.getChqDDPO().equals("C"))|| (chq_obj.getChqDDPO().equals("P")&& (chq_obj.getBounceInd().equals("T")) && (chq_obj.getCreditACNo() > 0))) 
								{
									System.out.println("2.................Verify..................");
									if (chq_obj.getChqDDPO().equals("C") && chq_obj.getCreditACNo() > 0)
									{
										java.util.Hashtable result=delegate.getColbalanePostInd(chq_obj.getDebitACType(),chq_obj.getDebitACNo(),Integer.parseInt(String.valueOf(chq_obj.getprev_ctrl_no())),ClearingDelegate.getSysDate());
										
										Iterator itr= (Iterator)result.keys();
										
										if(itr.hasNext()) 
										{
											Object d = itr.next();
											col_bal= new Double(d.toString()).doubleValue();
											post_ind = (String)result.get(d);
										}
										
										System.out.println(col_bal+"........Verify Col Bal..........."+post_ind);
										
										//if(chequedepositionobject_co.getTranAmt()>col_bal  && post_ind.equalsIgnoreCase("T"))
										if(post_ind.equalsIgnoreCase("T"))
										{
										  if( chq_obj.getTranAmt() > col_bal )
										  {		
											if (JOptionPane.showConfirmDialog(null,"Allow Negative Balance", "Outward Return",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
											{
												if (chq_obj.getMultiCredit() != null && chq_obj.getMultiCredit().equals("T")) 
												{
													for (int p = 0; p < arr_object.length; p++) 
													{

														for (int q = 0; q < arr_object[p].length; q++) 
														{
															if (q == 0)
																account_type = String.valueOf(arr_object[p][q]);
															if (q == 1)
																account_no = Integer.parseInt(String.valueOf(arr_object[p][q]));
														}
														System.out.println("--Allow Negative--");
																
													int_value = delegate.verifyInwardData((recievedChequeEntryForm.getCtrl_no()),tml,uid,ClearingDelegate.getSysDate(),account_type,account_no, 1,ClearingDelegate.getSysDate());

													}
												}
												else 
												{
													//int_value = clearingRemote.verifyInwardData(Long.parseLong(txt_ctrlno.getText()),MainScreen.head.getTml(),MainScreen.head.getUid(),MainScreen.head.getSysDateTime(),null, 0, 1);
													int_value=new int[1];
													int_value[0]=delegate.outwardReturnBounce(recievedChequeEntryForm.getCtrl_no(),1,tml,uid,ClearingDelegate.getSysDate());
												      System.out.println("value----Verify" + int_value[0]);
												}
												
											  }
											   else 
											   {
													// if they select no for jOPtion pane for  allow negative balance 
													int_value=new int[1];
													int_value[0]= delegate.outwardReturnBounce(recievedChequeEntryForm.getCtrl_no(),2,tml,uid,ClearingDelegate.getSysDate());
													System.out.println("value" + int_value[0]);
												
											   	}
											
											}
										  	else 
											{
												
												if (JOptionPane.showConfirmDialog(null,"Shall I Return The Cheque ", "Outward Return",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
												{
													int_value=new int[1];
													int_value[0]= delegate.outwardReturnBounce(recievedChequeEntryForm.getCtrl_no(),2,tml,uid,ClearingDelegate.getSysDate());
													 System.out.println("value" + int_value[0]);
												}
												else
												{
													 System.out.println("qwqwqwqwqwq............................weeeeeeee");
													int_value=new int[1];
													int_value[0]= delegate.outwardReturnBounce(recievedChequeEntryForm.getCtrl_no(),1,tml,uid,ClearingDelegate.getSysDate());
												      System.out.println(" dfdhgsdfgtryrwhgfdgfvalue" + int_value[0]);
												}
											}
											
											//have to go for return or debit 
										}
										else 
										{ // if the closeing balance is more than the trn_amt and posting is false 
											
											if (chq_obj.getMultiCredit() != null && chq_obj.getMultiCredit().equals("T")) 
											{
												for (int p = 0; p < arr_object.length; p++) {

													for (int q = 0; q < arr_object[p].length; q++) {
														if (q == 0)
															account_type = String.valueOf(arr_object[p][q]);
														if (q == 1)
															account_no = Integer.parseInt(String.valueOf(arr_object[p][q]));
													}
													System.out.println("--balance is more than the trn_am---");
															
													int_value = delegate.verifyInwardData(recievedChequeEntryForm.getCtrl_no(),tml,uid,ClearingDelegate.getSysDate(),account_type,account_no, 0,ClearingDelegate.getSysDate());

												}

											} 
											else 
											{
												System.out.println("--balance is more--else---than the trn_am---");
												
												     int_value = delegate.verifyInwardData(recievedChequeEntryForm.getCtrl_no(),tml,uid,ClearingDelegate.getSysDate(),null, 0, 0,ClearingDelegate.getSysDate());
												     System.out.println("value" + int_value[0]);
															
											}
											
										}
										
									}
									else if (chq_obj.getChqDDPO().equals("C")&& chq_obj.getCreditACNo() == 0&& chq_obj.getBounceInd().equalsIgnoreCase("F"))
									{
										System.out.println("pass Cheque");
										int_value = delegate.verifyInwardData(recievedChequeEntryForm.getCtrl_no(),tml,uid, ClearingDelegate.getSysDate(), null, 0, 0,ClearingDelegate.getSysDate());
										System.out.println("value" + int_value[0]);
									}	
							  } 
								
								if (chq_obj.getBounceInd().equals("T")&& chq_obj.getCreditACNo()== 0) 
								{
									
									if(Double.valueOf(recievedChequeEntryForm.getBalance()) > Double.valueOf(recievedChequeEntryForm.getAmount()) &&  vector_normal_reasons.size() == 1 && Integer.parseInt(vector_normal_reasons.firstElement().toString()) == 1)
									{
									
										// vector_normal_reasons.size() == 1 && Integer.parseInt(vector_normal_reasons.firstElement().toString()) == 1 &&  (Double.valueOf(txt_chq_balance.getText().toString().trim()) > Double.valueOf(txt_amount.getText().toString().trim()))
										recievedChequeEntryForm.setError_message("Cheque Has Been Passed ");
										chq_obj.setControlNo(recievedChequeEntryForm.getCtrl_no());
										chq_obj.setVeTml(tml);
										chq_obj.setVe_date(ClearingDelegate.getSysDate());
										chq_obj.setVeUser(uid);
										chq_obj.setSysdate( ClearingDelegate.getSysDate());
										 int_value_pass = delegate.addAccountTransactionDebitEntry(chq_obj);
										
									}
									else 
									{
									System.out.println("---I/w RTN---");
									
									int_value = delegate.verifyInwardData(recievedChequeEntryForm.getCtrl_no(),tml, uid, ClearingDelegate.getSysDate(), null, 0, 3,ClearingDelegate.getSysDate());
									
									///For  I/W  RTN
									System.out.println("value" + int_value[0]);
									}

								}

								if (chq_obj.getChqDDPO().equals("P")&& chq_obj.getBounceInd().equals("F")) 
								{
									System.out.println("payorder verification");
									int_value = delegate.verifyInwardData(recievedChequeEntryForm.getCtrl_no(),tml, uid, ClearingDelegate.getSysDate(), null, 0, 2,ClearingDelegate.getSysDate());///For
																				   // Payorder
									System.out.println("value" + int_value[0]);

								}
								if(int_value_pass == -1 ||int_value[0] > 0) 
								{
									recievedChequeEntryForm.setError_flag(1);	
									recievedChequeEntryForm.setError_message("Sucessfully Verified");
								}
								else
									recievedChequeEntryForm.setError_message( " Verification Failed");

					}
					catch (Exception e) {
						e.printStackTrace();
					}
				 
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmAckNum"))
				 {	
					 
					 String remin_amt = null;
					 if((recievedChequeEntryForm.getCtrl_no())==0)
					 {
						 recievedChequeEntryForm.setAcknowledge_amt("0.0");
						 recievedChequeEntryForm.setAcknowledge_rem_amt(0.0);
						 recievedChequeEntryForm.setSource_name("");
						 recievedChequeEntryForm.setSend_to("");
						 if (recievedChequeEntryForm.getAckno() != 0 ) 
						 {
									System.out.println("again ack");
									ackobject = delegate.getAcknowledgeAmount(recievedChequeEntryForm.getAckno(),recievedChequeEntryForm.getCredebit());
									if (ackobject == null) 
									{
										recievedChequeEntryForm.setError_message("Acknowledgement Number Not Found");
									  	recievedChequeEntryForm.setAckno(0); 
										
									}
									else if (ackobject!=null && ackobject.getReconciled().equals("T"))
									{
										System.out.println("inside else if");
										recievedChequeEntryForm.setError_message("Acknowledgement Number Already Reconciled");
										recievedChequeEntryForm.setAckno(0);
										
									}
									else 
									{
										System.out.println("inside else");
										recievedChequeEntryForm.setAcknowledge_amt(Validations.changeFloat(ackobject.getAckEntered()));
										remin_amt =String.valueOf(ackobject.getAckEntered()- ackobject.getTotal());
										recievedChequeEntryForm.setAcknowledge_rem_amt(Double.parseDouble(remin_amt));
										recievedChequeEntryForm.setBalance(ackobject.getTotal());
										recievedChequeEntryForm.setSend_to(ackobject.getBankCode());
										recievedChequeEntryForm.setSource_name(ackobject.getBankName());
										if(Double.parseDouble(remin_amt) <= 0.0)
										{
											recievedChequeEntryForm.setError_message( "Acknowledgement Amount Is Less");
										}
									}
								} 
						 		else
						 		{
						 			recievedChequeEntryForm.setError_message("Enter Valid Acknowledgement Number");
						 		}
					 }
				 }
				 else if (recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmPrevCtrlNum")) 
				 {		
					 
					 System.out.println("previous control num");
						if (recievedChequeEntryForm.getPrev_ctrl_no()<= 0)
						{
					
							recievedChequeEntryForm.setError_message("Please Enter Previous Control Number");
						}
							
						else if (recievedChequeEntryForm.getPrev_ctrl_no() > 0) 
						{
								System.out.println("if prev ctrl num greater than zero");
								String string_prev_ctrlno =String.valueOf( recievedChequeEntryForm.getPrev_ctrl_no());
								chDepositionObject = delegate.getInwardData(recievedChequeEntryForm.getPrev_ctrl_no(),3);
							
								if (chDepositionObject == null)
								{
									recievedChequeEntryForm.setError_message("Previous Control Number Not Found");
									recievedChequeEntryForm.setPrev_ctrl_no(0);
								
								} 
								else if (chDepositionObject.getDespInd().equals("F")) 
								{
									
									recievedChequeEntryForm.setError_message("Cheque Not Yet Dispatched");
									recievedChequeEntryForm.setPrev_ctrl_no(0);
									
								
								}
								else 
								{ 
									
									
									System.out.println("yeo"+ chDepositionObject.getMultiCredit());
								
									if (chDepositionObject.getMultiCredit().equals("T"))
									{
										System.out.println("its inside");
										recievedChequeEntryForm.setPrev_ctrl_no(Long.parseLong(string_prev_ctrlno));
										
										recievedChequeEntryForm.setBankname(chDepositionObject.getBankName());
										recievedChequeEntryForm.setPaydetail(chDepositionObject.getPayeeName());
										
										recievedChequeEntryForm.setCity_code(String.valueOf(chDepositionObject.getBankCode()).substring(0, 3));
										recievedChequeEntryForm.setBank_code(String.valueOf(chDepositionObject.getBankCode()).substring(3, 6));
										recievedChequeEntryForm.setBranch_code(String.valueOf(chDepositionObject.getBankCode()).substring(6, 9));
										recievedChequeEntryForm.setBranchname(chDepositionObject.getBranchName());
										recievedChequeEntryForm.setAmount(chDepositionObject.getTranAmt());
										System.out.println("out is"+ chDepositionObject.getChqDDPO());
										if (chDepositionObject.getChqDDPO().equals("C"))
												recievedChequeEntryForm.setChqddpo("C");

									}
									else 
									{
										
										recievedChequeEntryForm.setPrev_ctrl_no(Long.parseLong(string_prev_ctrlno));
										recievedChequeEntryForm.setBankname(chDepositionObject.getBankName());
										recievedChequeEntryForm.setPaydetail(chDepositionObject.getPayeeName());
										recievedChequeEntryForm.setCity_code(String.valueOf(chDepositionObject.getBankCode()).substring(0, 3));
										recievedChequeEntryForm.setBank_code(String.valueOf(chDepositionObject.getBankCode()).substring(3, 6));
										recievedChequeEntryForm.setBranch_code(String.valueOf(chDepositionObject.getBankCode()).substring(6, 9));
										recievedChequeEntryForm.setBranchname(chDepositionObject.getBranchName());
										recievedChequeEntryForm.setAmount(chDepositionObject.getTranAmt());
										System.out.println("out is"+ chDepositionObject.getChqDDPO());
										if (chDepositionObject.getChqDDPO().equalsIgnoreCase("C") || chDepositionObject.getChqDDPO().equals ("Q"))
											recievedChequeEntryForm.setChqddpo("C");
										else if (chDepositionObject.getChqDDPO().equalsIgnoreCase("P"))
											recievedChequeEntryForm.setChqddpo("P");
										else
											recievedChequeEntryForm.setChqddpo("D");
										
										recievedChequeEntryForm.setDebit_chq_date(chDepositionObject.getQdpDate());
										recievedChequeEntryForm.setDebit_chequeno(chDepositionObject.getQdpNo());

										recievedChequeEntryForm.setDebit_account_type(chDepositionObject.getDebitACType());
										
										//combo_actype.setSelectedIndex(j);
										recievedChequeEntryForm.setDebit_account_no(chDepositionObject.getDebitACNo());
										
										if(!(chDepositionObject.getDebitACType().startsWith("1003")|| chDepositionObject.getDebitACType().startsWith("1004")|| chDepositionObject.getDebitACType().startsWith("1005") || chDepositionObject.getDebitACType().startsWith("1012"))) 
										{
											AccountMasterObject accountmasterobject_am = delegate.getAccountInfoInd(chDepositionObject.getDebitACType(),chDepositionObject.getDebitACNo());
											custId=accountmasterobject_am.getCid();
											recievedChequeEntryForm.setBalance(accountmasterobject_am.getCloseBal());
											recievedChequeEntryForm.setShodowbalance(accountmasterobject_am.getPreCloseBal()- accountmasterobject_am.getTransAmount());
											
										}

										else if (chDepositionObject.getMultiCredit().equals("P")) 
										{
											recievedChequeEntryForm.setChqddpo("P"); 
											recievedChequeEntryForm.setPayorderDate(chDepositionObject.getQdpDate());
											recievedChequeEntryForm.setPayorderNum(String.valueOf(chDepositionObject.getQdpNo()));
											recievedChequeEntryForm.setPayorderName(chDepositionObject.getPayeeName());
											recievedChequeEntryForm.setPayorderBalance(String.valueOf(chDepositionObject.getTranAmt()));
										}
										recievedChequeEntryForm.setBounce(true);
										
									}
									
									
								}
							
						}
				}
				else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmAccNum"))
				{	
					 
							
					 		if((recievedChequeEntryForm.getDebit_account_no()) == 0  && ( recievedChequeEntryForm.getDebit_account_type().startsWith("1004")))
							{
								
								System.out.println( " inside the clr delegate , flag 3  "+ recievedChequeEntryForm.getDebit_account_no() + "--------" + recievedChequeEntryForm.getDebit_account_type()) ;
								//form.setCredit_acc_type_name("New Account");
							
							} 
					 		else if((recievedChequeEntryForm.getDebit_account_no()==0 ) &&  (recievedChequeEntryForm.getDebit_account_type().startsWith("1001"))) 
							{
										System.out.println( " inside the clr delegate , flag 3  "+ recievedChequeEntryForm.getDebit_account_no() + "--------" + recievedChequeEntryForm.getDebit_account_type()) ;
										recievedChequeEntryForm.setError_message("You Can't Open An Account, Give Account Number" );
									
							} 
					 		else 
					 		{
								
								if(recievedChequeEntryForm.getDebit_account_type().startsWith("1012"))
								{
									String val = delegate.getGLName( recievedChequeEntryForm.getDebit_account_type(), recievedChequeEntryForm.getDebit_account_no() );
									
									if(val == null)
									{
										
										recievedChequeEntryForm.setError_message("No Such GL Found With GL Code" );
										recievedChequeEntryForm.setDebit_account_no(0);
									} 
								//	else
									    //recievedChequeEntryForm.setDebit_account_type name( val );
							
								} 
								else 
								{
									
									AccountObject acc_obj = delegate.getAccount( recievedChequeEntryForm.getDebit_account_type(), recievedChequeEntryForm.getDebit_account_no(), "12/02/2007");
									
									if(acc_obj==null)
									{
										
										//recievedChequeEntryForm.setError_flag(1);
										recievedChequeEntryForm.setError_message("No Such Account Found With Account Number   " + recievedChequeEntryForm.getDebit_account_no());
										recievedChequeEntryForm.setDebit_account_no(0);
										recievedChequeEntryForm.setBalance(0.0);
										recievedChequeEntryForm.setShodowbalance(0.0);
										
									} 
									else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
									{
										//recievedChequeEntryForm.setError_flag(1);
										recievedChequeEntryForm.setError_message("Account Is Closed");
										recievedChequeEntryForm.setDebit_account_no(0);
								
									} 
									else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
									{
										//recievedChequeEntryForm.setError_flag(1);
										recievedChequeEntryForm.setError_message("Account Is Freezed" );
										recievedChequeEntryForm.setDebit_account_no(0);
									}
									else 
									{
									//recievedChequeEntryForm.setError_flag(0);
										//recievedChequeEntryForm.setDebit_account_type name( acc_obj.getAccname() );
										custId=acc_obj.getCid();
										recievedChequeEntryForm.setBalance(acc_obj.getAmount());
										recievedChequeEntryForm.setShodowbalance(acc_obj.getShadowBalance());
										if(recievedChequeEntryForm.getAmount()!=0.0)
										{
											if((acc_obj.getAmount()-recievedChequeEntryForm.getAmount())>0.0)
											{
												recievedChequeEntryForm.setBounce(false);
												System.out.println("positive??????????????????????????????????????");
											}
											else
											{
												System.out.println("negative!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
												recievedChequeEntryForm.setBounce(true);
											}
										}
									}
								}
							}
							if(recievedChequeEntryForm.getReason_codes()!= null) 
							{
								
								StringTokenizer tok = new StringTokenizer(recievedChequeEntryForm.getReason_codes() ,"," );
								
								while(tok.hasMoreTokens())
								{
									
									System.out.println(  tok.nextToken() + " reason code ");
								}
								
							}
							
							
							
						
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmChqNo"))
				 {
					 getChequeDetail(recievedChequeEntryForm ,httpServletRequest); 
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmSubmit"))
				 {	
					 
					 System.out.println("inside submit");
					 depositionObject = new ChequeDepositionObject();

					 depositionObject.setControlNo(recievedChequeEntryForm.getCtrl_no());
					 depositionObject.setAckNo(recievedChequeEntryForm.getAckno());
					 depositionObject.setSendTo(Integer.parseInt(recievedChequeEntryForm.getSend_to()));
					 depositionObject.setCompanyName(recievedChequeEntryForm.getSource_name());
					 depositionObject.setClgType("I");
					 String code = recievedChequeEntryForm.getCity_code() + recievedChequeEntryForm.getBank_code() + recievedChequeEntryForm.getBranch_code();
					 depositionObject.setBankCode(code);
					 depositionObject.setBranchName(recievedChequeEntryForm.getBranchname());
					 depositionObject.setBankName(recievedChequeEntryForm.getBankname());
					 depositionObject.setPayeeName(recievedChequeEntryForm.getPaydetail());
					 System.out.println("inside  before if");
					 
					 if (recievedChequeEntryForm.getReturn_type().equalsIgnoreCase("No")) 
					 {
						 	depositionObject.setCreditACType("N");//not
																		// combo_returned_chq if
							depositionObject.setCreditACNo(0);//no pre ctrl No if
					 }
					 else 
					 {
							depositionObject.setCreditACType("R");//combo_returned_chq if
							String creNum=String.valueOf(recievedChequeEntryForm.getPrev_ctrl_no());
							depositionObject.setCreditACNo(Integer.parseInt(creNum));//pre ctrl No if
					 }
					 if (recievedChequeEntryForm.getChqddpo().equalsIgnoreCase("C"))
					 {
							
							System.out.println("inside cheque");

							if (chDepositionObject!= null&& chDepositionObject.getMultiCredit() != null && chDepositionObject.getMultiCredit().equals("T")) 
							{
								depositionObject.setDebitACType(null);
								depositionObject.setDebitACNo(0);
								depositionObject.setMultiCredit("T");
							}
							else 
							{
								String acctype =String.valueOf(recievedChequeEntryForm.getDebit_account_type());
								System.out.println("accno" + " " +recievedChequeEntryForm.getDebit_account_no());
								System.out.println("acctype" + " " + acctype);
								depositionObject.setMultiCredit("F");
								depositionObject.setDebitACType(acctype);
								depositionObject.setDebitACNo(recievedChequeEntryForm.getDebit_account_no());
							}

							depositionObject.setTranAmt((recievedChequeEntryForm.getAmount()));
							depositionObject.setQdpNo((recievedChequeEntryForm.getDebit_chequeno()));
							depositionObject.setQdpDate(recievedChequeEntryForm.getDebit_chq_date());
							depositionObject.setChqDDPO("C");//for chq/PO/W combo box
						}
					 	else if (recievedChequeEntryForm.getChqddpo().equalsIgnoreCase("P") && recievedChequeEntryForm.getPrev_ctrl_no() == 0)
					 	{

						 	depositionObject.setTranAmt(recievedChequeEntryForm.getAmount());
						 	// depositionObject.setQdpNo(Integer.parseInt(txt_po_no.getText()));
						 	//depositionObject.setQdpDate(txt_po_date.getText());
						 	depositionObject.setChqDDPO("P");//for chq/PO/W combo box
					 	}
					 	else if (recievedChequeEntryForm.getChqddpo().equalsIgnoreCase("P") && recievedChequeEntryForm.getPrev_ctrl_no()>0)
					 	{
								String acctype = String.valueOf(Integer.parseInt(recievedChequeEntryForm.getDebit_account_type()));
								System.out.println("accno" + " " + recievedChequeEntryForm.getDebit_account_no());
								System.out.println("acctype" + " " + acctype);
								depositionObject.setDebitACType(acctype);
								depositionObject.setDebitACNo(recievedChequeEntryForm.getDebit_account_no());

								depositionObject.setTranAmt((recievedChequeEntryForm.getAmount()));
								depositionObject.setQdpNo((recievedChequeEntryForm.getDebit_chequeno()));
								depositionObject.setQdpDate(recievedChequeEntryForm.getDebit_chq_date());
								depositionObject.setChqDDPO("P");//for chq/PO/W combo box

						}
					 	if (recievedChequeEntryForm.isBounce())
					 	{
							System.out.println("inside combo");
							depositionObject.setBounceInd("T");
							String[] cbox=httpServletRequest.getParameterValues("chkBox");
							String[] bounceFine=httpServletRequest.getParameterValues("txtBounceFine");
							String[] description=httpServletRequest.getParameterValues("txtDesription");
							String[] reasonCode=httpServletRequest.getParameterValues("txtReasonCode");
						
							if(cbox!=null)
							{
									for(int k=0;k<cbox.length;k++)
									{
										int x=Integer.parseInt(cbox[k]);
										System.out.println("value of XXXXX==="+x);
										System.out.println("value of cbox==="+cbox[k]);
										System.out.println("length of chk box===="+cbox.length);
										vector_all_reasons.add(reasonCode[x]);
										System.out.println("inside reason code======"+reasonCode[x]);
										System.out.println("inside fineee===="+bounceFine[x]);
										fine_amount+=Double.parseDouble(bounceFine[x]);
									}
							
									System.out.println("fineeeee===="+fine_amount);
									depositionObject.setFineAmt(fine_amount);
									depositionObject.setReasonArray( vector_all_reasons );
									System.out.println("set values");
									depositionObject.setCreditACType("R");
							}
							else
							{
								recievedChequeEntryForm.setError_message("Select Proper Reason");
							}
					 	} 
					 	else 
					 	{
							depositionObject.setBounceInd("F");
							depositionObject.setReasonArray(vector_normal_reasons);
							depositionObject.setFineAmt(0.00);//settong max fine amt
						}

					 	depositionObject.setDeTml(tml);
					 	depositionObject.setDeUser(uid);
					 	depositionObject.setDe_date(delegate.getSysDate());
					 	depositionObject.setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());

					 	System.out.println("before long ctrl num");
					 	long long_ctrlno = delegate.storeInwardData(depositionObject);
						System.out.println("after ctrl num");
						if (long_ctrlno > 0) 
						{	
							if(recievedChequeEntryForm.getCtrl_no()==0)
							{
								System.out.println("inside if long");
								recievedChequeEntryForm.setError_message("Note Down Control Number  " + long_ctrlno+ " ");
								recievedChequeEntryForm.setError_flag(1);
							}
							else
							{
								recievedChequeEntryForm.setError_message("Control Number Updated Successfully");
								recievedChequeEntryForm.setError_flag(1);
							}
						} 
						else
							recievedChequeEntryForm.setError_message("Submission Not Done. Try Again");
					 
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmPayorderNum"))
				 {	
					 
					 if (recievedChequeEntryForm.getPayorderNum().length() > 0)
					 {
						 
							System.out.println("payorder");
							System.out.println("Po Number == " + recievedChequeEntryForm.getPayorderNum());
							PayOrderObject payorderobject = delegate.getPayOrderInstrn(Integer.parseInt(recievedChequeEntryForm.getPayorderNum().trim()));

							array_int_default_reasons[6] = 0;
							array_int_default_reasons[5] = 0;
							array_int_default_reasons[4] = 0;
							array_int_default_reasons[8] = 0;
							array_int_default_reasons[7] = 0;
							array_int_default_reasons[1] = 0;
							
							if(payorderobject == null) 
							{
								recievedChequeEntryForm.setBounce(true);
								array_int_default_reasons[6] = 6;
								recievedChequeEntryForm.setError_message("Pay Order Not Found");
								recievedChequeEntryForm.setPayorderNum("");
								recievedChequeEntryForm.setPayorderName("");
								recievedChequeEntryForm.setPayorderBalance("");
							}
							else
							{
									System.out.println("ljl----"+ payorderobject.getPOSerialNo());
									System.out.println("payorder");
									recievedChequeEntryForm.setBounce(false);
									recievedChequeEntryForm.setPayorderName(String.valueOf(payorderobject.getPOPayee()));
									recievedChequeEntryForm.setPayorderBalance(String.valueOf(payorderobject.getPOAmount()));
									recievedChequeEntryForm.setPayorderDate(String.valueOf(payorderobject.getPODate()));
									recievedChequeEntryForm.setAmount(payorderobject.getPOAmount());
									recievedChequeEntryForm.setPaydetail(payorderobject.getPOPayee());

								if (Validations.dayCompare(ClearingDelegate.getSysDate(), payorderobject.getPOValidUpTo()) < 0) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[5] = 5;
									recievedChequeEntryForm.setError_message("Pay Order Out Dated");
									recievedChequeEntryForm.setPayorderNum("");
								}
								else if (payorderobject.getPOStop().equals("T")) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[4] = 4;
									recievedChequeEntryForm.setError_message("Payment Stopped By Drawer");
								}
								else if (payorderobject.getPOCancel().equals("T")) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[8] = 8;
									recievedChequeEntryForm.setError_message("Pay Order Is Blocked");
									recievedChequeEntryForm.setPayorderNum("");
								}
								else if (payorderobject.getPOCshIndicator() == 1) 
								{
									recievedChequeEntryForm.setBounce(true);
									array_int_default_reasons[7] = 7;
									recievedChequeEntryForm.setError_message("Pay Order Is Closed");
									recievedChequeEntryForm.setPayorderNum("");
								}

							}
						//	recievedChequeEntryForm.setPanelFlag(2);
					 }		
				 }
				 else if(recievedChequeEntryForm.getFlag().equalsIgnoreCase("frmDelete"))
				 {	
					 
					 int int_value = delegate.deleteInwardData(recievedChequeEntryForm.getCtrl_no());
						if (int_value > 0) 
						{
							recievedChequeEntryForm.setError_message("Sucessfully Deleted");
							recievedChequeEntryForm.setError_flag(1);
						} 
						else
							recievedChequeEntryForm.setError_message("Unable To Delete");
					 
				 }
				 	
				 if(custId>0)
				 {
					 System.out.println("inside custId");
				 	CustomerMasterObject cust = delegate.getCustomerDetail(custId);
				 	SignatureInstructionObject[] sigObject=delegate.getSignatureDetails(recievedChequeEntryForm.getDebit_account_no(),recievedChequeEntryForm.getDebit_account_type());
				 	httpServletRequest.setAttribute("signInfo",sigObject);
				 	httpServletRequest.setAttribute("personalInfo", cust);
				 }
					
				 	module_debit = setDebitAccountDetail();
					httpServletRequest.setAttribute("Debit_Module_code", module_debit );
			
					Reason[] reason = delegate.getReasonDetails("1002001", 0 );
					httpServletRequest.setAttribute("Reason", reason );
					httpServletRequest.setAttribute("flag",sigPath);
					httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(recievedChequeEntryForm.getPageId()));
					httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
					return actionMapping.findForward(ResultHelp.getSuccess());
			
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
	
}
	
	else if(actionMapping.getPath().equalsIgnoreCase("/Clearing/ClearingIdentification")  )
	{
		
		ClearingIdentificationForm clearingIdentificationForm = (ClearingIdentificationForm)actionForm;
		System.out.println("page id is---"+clearingIdentificationForm.getPageId());
		clearingIdentificationForm.setValidateFlag("");
		if ( MenuNameReader.containsKeyScreen(clearingIdentificationForm.getPageId())){
				
			String path = MenuNameReader.getScreenProperties(clearingIdentificationForm.getPageId());
			clearingIdentificationForm.setValidateFlag("");
			clearingIdentificationForm.setError_flag(0);
			System.out.println("path is---"+path);
			ModuleObject[] array_module = ClearingDelegate.getInstance().getMainModule(4);
            
			if(clearingIdentificationForm.getFlag().equalsIgnoreCase("frmSendTo"))
			{
				
				
				BranchObject[] br_obj =  delegate.getBranchDetails(Integer.parseInt(clearingIdentificationForm.getSendTo()));
				if(br_obj!=null)
				{	
					clearingIdentificationForm.setSourceName(br_obj[0].getBranchName());
					if ( br_obj[0].getBranchCode() != -1 )
					{
						ChequeDepositionObject[] chq = delegate.getUnidentifiedCheques(Integer.parseInt(clearingIdentificationForm.getSendTo()));
						
						if ( chq != null )
						{
//							for ( int i =0; i< chq.length; i++ )
//							{
//								if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("O"))
//										chq[i].setClgType("O/W");
//								else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("C"))
//									chq[i].setClgType("Cr ECS");
//								else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("D"))
//									chq[i].setClgType("Dr ECS");
//								else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("T"))
//									chq[i].setClgType("Trf");
//								else if ( chq[i].getClgType() != null && chq[i].getClgType().equalsIgnoreCase("I"))
//									chq[i].setClgType("I/W");
//							}
							httpServletRequest.setAttribute("Unidentified", chq ) ;
						
						}
						else
						{
							clearingIdentificationForm.setValidateFlag("Records Not Found");
						}
						
					 }
				}
				else
				{
					clearingIdentificationForm.setValidateFlag("Outward Bank Not Found");
					
				}
				
			}
			 if(clearingIdentificationForm.getFlag().equalsIgnoreCase("frmSubmit"))
			{
				
				System.out.println("inside submit");
				String[] cbox=httpServletRequest.getParameterValues("chkBox");
				long[]  ctrl_no = new long[cbox.length] ;
				if(cbox==null)
				{
					clearingIdentificationForm.setValidateFlag("No Rows Checked");
				}
					
				if(cbox.length>0)
				{
					 String[] ctrlNum=httpServletRequest.getParameterValues("txtCtrlNum");
					 System.out.println("inside if");
							for(int i=0,j=0;i<cbox.length;i++)
							{
								System.out.println("inside for"+cbox[i]);
								int x=Integer.parseInt(cbox[i]);
								System.out.println("......,"+x);
								System.out.println("******1234***"+ctrlNum[x]);
								System.out.println(" ctrlNum[x]==============="+ Long.parseLong(ctrlNum[x]));
								ctrl_no[j]=Long.parseLong(ctrlNum[x]);
								System.out.println("ctrl_no[j]-------->"+ctrl_no[j]);
								j++;
							}
			
				}
					
					if(delegate.identifyCheques(ctrl_no, clearingIdentificationForm.getClgDate(), Integer.parseInt(clearingIdentificationForm.getClgNum()), Integer .parseInt(clearingIdentificationForm.getSendTo()), 1 ) > 0 )
					{
						clearingIdentificationForm.setValidateFlag("  Identified Successfully ");
					} 
					else 
					{
						clearingIdentificationForm.setValidateFlag("Identification UnSuccessfull ");
					}
					
				} 
				
			
			
			module = ClearingDelegate.getInstance().getMainModule(4);
			httpServletRequest.setAttribute("Main_Module_code",module);
			httpServletRequest.setAttribute("flag",perPath);
			httpServletRequest.setAttribute("pageId",path);
			httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
			
			return actionMapping.findForward(ResultHelp.getSuccess());
			
		}
		
		
	}
	 
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ackDataEntryMLink"))
	{
    	try{
    		 
    		
    		AcknowledgementDataEntryForm acknowledgementDataEntryForm=(AcknowledgementDataEntryForm)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(acknowledgementDataEntryForm.getPageId())){

    			BranchObject array_branchobject[]= delegate.getBranchDetails(-1);
    			acknowledgementDataEntryForm.setAckNumber("0");
    			httpServletRequest.setAttribute("sysDate",LockerDelegate.getSysDate());
    			httpServletRequest.setAttribute("sourceNums", array_branchobject);
    			//acknowledgementDataEntryForm.setBname(array_branchobject[0].getBranchName());
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(acknowledgementDataEntryForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ackDataEntryLink"))
	{
    	try{
    		 
    		
    		AcknowledgementDataEntryForm acknowledgementDataEntryForm=(AcknowledgementDataEntryForm)actionForm;
    		acknowledgementDataEntryForm.setValidateFlag("");
    		acknowledgementDataEntryForm.setErrorFlag("");
    		acknowledgementDataEntryForm.setValidateMsg("");
    		
    		if(MenuNameReader.containsKeyScreen(acknowledgementDataEntryForm.getPageId()))
    		{
    			BranchObject array_branchobject[]= delegate.getBranchDetails(-1);
    			
    			System.out.println("-=-=-=hhhhhhhhh-=-=-=-=-=>"+acknowledgementDataEntryForm.getFlag());
    			
    			
    			if(acknowledgementDataEntryForm.getFlag().equalsIgnoreCase("1"))
    			{
    				
    				if(acknowledgementDataEntryForm.getSource().equalsIgnoreCase("9999"))
    				{
    					String[] clgTypes={"I","D","C"};
    					//acknowledgementDataEntryForm.setBname(array_branchobject[0].getBranchName());
    					httpServletRequest.setAttribute("clgTypes", clgTypes); 
    				}
    				else
    				{
    					String[] clgTypes={"O","D","C"};
    					//acknowledgementDataEntryForm.setBname(array_branchobject[0].getBranchName());
    					httpServletRequest.setAttribute("clgTypes", clgTypes);
    				}
    			}
    			
    			else if(acknowledgementDataEntryForm.getFlag().equalsIgnoreCase("delete"))
    			{
    				if(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber())!=0)
    				{
    							int i = delegate.deleteAck(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber()));
    									System.out.println("---value of i---->"+i);
    							if(i>-1)
    							{
    								acknowledgementDataEntryForm.setValidateMsg("Acknowledgement Number Is Successfully Deleted");
    								acknowledgementDataEntryForm.setErrorFlag("1");
    							}
    							else if(i==-1)
    							{
    								acknowledgementDataEntryForm.setValidateMsg("Acknowledgement Number Cannot Be Deleted");
    								acknowledgementDataEntryForm.setErrorFlag("1");
    							}
    						
    				}
    			}
    			
    			else if(acknowledgementDataEntryForm.getFlag().equalsIgnoreCase("submit"))
    			{
    				if(acknowledgementDataEntryForm.getBooleanFlag()==0)
    				{
    					if(acknowledgementDataEntryForm.getClgType()!=null)
    					{
    						if(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber())==0)
    						{	
    							int int_ackno=delegate.ackEntry(acknowledgementDataEntryForm.getSource(),acknowledgementDataEntryForm.getClgType(),acknowledgementDataEntryForm.getAmount(),tml,uid,"0");
    							System.out.println("ack no"+int_ackno);
    							acknowledgementDataEntryForm.setValidateMsg("Note Down The Acknowledgement Number "+int_ackno);
    							acknowledgementDataEntryForm.setErrorFlag("1");
    						}
    						else
    						{
    							acknowledgementDataEntryForm.setValidateMsg("Acknowledgement Number Should Be Zero");
    						}
    					}
    					else
    					{
    						acknowledgementDataEntryForm.setValidateMsg("Select ClgTyp");
    					}
    				}
    				else if(acknowledgementDataEntryForm.getBooleanFlag()==1)
    				{
    					
    					int  status =delegate.checkDailyStatus(ClearingDelegate.getSysDate(),1);
    			    	
    			    	if(status==3)//day close
    		            {
    		                acknowledgementDataEntryForm.setValidateFlag("Day Closed You Can't Do Any Transactions");
    		               
    		            }
    		           else if(status == 2)//gl posting
    		            {
    		              int result = JOptionPane.showConfirmDialog(null,"GL Posting Has Been Done. Do You Want To Continue The Transaction ?","GL Posting Done",JOptionPane.YES_NO_OPTION);
    		        	  
    		              if(result==JOptionPane.YES_OPTION)
    		              {
    		            	  boolean glposting = delegate.updatePostInd(bankName,ClearingDelegate.getSysDate());
    		            	  if(glposting==false)
    		                   {
    		                    	acknowledgementDataEntryForm.setValidateFlag("Error : Unable To Proceed");
    		                    	acknowledgementDataEntryForm.setErrorFlag("1");
    		                   }
    		              }    
    		             }
    					
    				    //verification of the acknowledgement's data entry made.
    					int int_verified=delegate.verifyAck(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber().trim()),Integer.parseInt(acknowledgementDataEntryForm.getSource().trim()),Double.parseDouble(acknowledgementDataEntryForm.getAmount().trim()),tml,uid,ClearingDelegate.getSysDate());
    					if(int_verified != 0)
    					{
    						acknowledgementDataEntryForm.setValidateFlag("Verified Successfully");
    						acknowledgementDataEntryForm.setErrorFlag("1");
    					}
    					else 
    					{	
    						acknowledgementDataEntryForm.setValidateFlag("Verification Failed");
    						acknowledgementDataEntryForm.setErrorFlag("1");
    					}
    				}
    			}
    			else if(acknowledgementDataEntryForm.getFlag().equalsIgnoreCase("frmAccountNumber"))
    			{
    				System.out.println("focus lost of account number--->");
    				acknowledgementDataEntryForm.setValidateMsg("");
    				
    				if(acknowledgementDataEntryForm.getBooleanFlag()==0) //retrieve the Details Of the Acknowledge
    				{
    					System.out.println("inside boolean 0");
    					if(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber())!= 0)
    					{
    						 
    						AckObject ackobject = delegate.getAckDetails(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber()),0);
    						if(ackobject==null)
    						{
    							acknowledgementDataEntryForm.setValidateMsg("Records Not Found");
    							acknowledgementDataEntryForm.setAckNumber("");
    						}
    						
    						else if(ackobject.getAckNo() !=0 && ackobject.getReconciled().equals("F"))
    						{	
    							acknowledgementDataEntryForm.setBname(ackobject.getBankName());
    							if(ackobject.getDocSource() > 1000)
    							{
    								String[] clgTypes={"I","D","C"};
    			    				httpServletRequest.setAttribute("clgTypes", clgTypes);
    			    				acknowledgementDataEntryForm.setSource(String.valueOf(ackobject.getDocSource()));
        							
    							} 
    							else 
    							{
    								String[] clgTypes={"O","D","C"};
    			    				httpServletRequest.setAttribute("clgTypes", clgTypes);
    			    				acknowledgementDataEntryForm.setSource(String.valueOf(ackobject.getDocSource()));
        							
    							}
    							acknowledgementDataEntryForm.setClgType(ackobject.getClgType());
    							httpServletRequest.setAttribute("ackObject",ackobject);	
    							
    						}		
    						
    						else
    						{
    							acknowledgementDataEntryForm.setValidateMsg("Acknowledgement Reconceiled");
    							acknowledgementDataEntryForm.setAckNumber("");
    							
    						}
    					}
    				}	
    				if((acknowledgementDataEntryForm.getBooleanFlag()==1) && Integer.parseInt(acknowledgementDataEntryForm.getAckNumber())!= 0)
    				{
    					System.out.println("1.............................");
    					AckObject ackobject=delegate.getAckDetails(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber().trim()),0);
    					System.out.println("2.............................");
    					
    					if( ackobject != null && ackobject.getAckNo()>0)//If acknowledgement number exists 
    					{
    						System.out.println("3.............................");
    						if(ackobject.getVeTml() == null)
    						{
    							System.out.println("4.............................");
    							if(ackobject.getReconciled().equals("T") ) //and if the acknowledgement is fully utilized.
    							{
    								System.out.println("5......Before set attr.......................");
    								//gives each and every details of the acknowledgement where and all it is used.
    								ChequeAckObject array_chequeackobject[] = delegate.ackChequeDetails(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber().trim()));
    								System.out.println("------Lenght------>"+array_chequeackobject.length);
    								ArrayList all = new ArrayList();
    								Map map = null;
    								
    								for(int i=0;i<array_chequeackobject.length;i++)
    								{
    									map = new TreeMap();
    									
    									map.put("controlNo",String.valueOf(array_chequeackobject[i].getControlNo()));
    									map.put("ackNo",String.valueOf(array_chequeackobject[i].getAckNum()));
    									map.put("docBs",String.valueOf(array_chequeackobject[i].getDocSource()));
    									map.put("docTotal",String.valueOf(array_chequeackobject[i].getDocTotal()));
    									map.put("docDestination",String.valueOf(array_chequeackobject[i].getDocDestination()));
    									map.put("SourceName",String.valueOf(array_chequeackobject[i].getSourceName()));
    									map.put("DestName",String.valueOf(array_chequeackobject[i].getDestName()));
    									map.put("BankName",String.valueOf(array_chequeackobject[i].getBankName()));
    									all.add(map);
    									
    								}
    							
    								httpServletRequest.setAttribute("ackChqDetails",all);	
    								System.out.println("6.........After set attr....................");							
    								acknowledgementDataEntryForm.setAckDate(ackobject.getAckDate());
    								acknowledgementDataEntryForm.setSource(String.valueOf(ackobject.getDocSource()));
    								acknowledgementDataEntryForm.setBname(ackobject.branchobject.getBranchName());
    								acknowledgementDataEntryForm.setAmount(String.valueOf(ackobject.getTotal()));
    							
    							if (ackobject.getClgType()!= null &&  ackobject.getClgType().equalsIgnoreCase("O")) //  Outward Inward Debit ECS Credit ECS
    									acknowledgementDataEntryForm.setClgType("O");
    								
    							else if(ackobject.getClgType()!= null &&  ackobject.getClgType().equalsIgnoreCase("I") )
    								acknowledgementDataEntryForm.setClgType("I");
    							
    							else if(ackobject.getClgType()!= null &&  ackobject.getClgType().equalsIgnoreCase("D") )
    								acknowledgementDataEntryForm.setClgType("D");
    							
    							else if (ackobject.getClgType()!= null &&  ackobject.getClgType().equalsIgnoreCase("C") )
    								acknowledgementDataEntryForm.setClgType("C");
    						}
    						else
    						{
    							acknowledgementDataEntryForm.setValidateFlag("Acknowledgement Not Reconcieled");
    							acknowledgementDataEntryForm.setAckNumber("");
    						}
    					}
    					else
    					{
    							acknowledgementDataEntryForm.setValidateFlag("Acknowledgement Already Verified");
    							acknowledgementDataEntryForm.setAckNumber("");
    					}
    				}
    					
    				else
    				{
    						acknowledgementDataEntryForm.setValidateFlag("Acknowledgement Not Found");
    						acknowledgementDataEntryForm.setAckNumber("");
    				}
    			 }
    				
    			}
    			else if(acknowledgementDataEntryForm.getFlag().equalsIgnoreCase("update"))
    			{
    				if(Integer.parseInt(acknowledgementDataEntryForm.getAckNumber())!=0)
    				{
    					int int_ackno=delegate.ackEntry(acknowledgementDataEntryForm.getSource(),acknowledgementDataEntryForm.getClgType(),acknowledgementDataEntryForm.getAmount(),tml,uid,acknowledgementDataEntryForm.getAckNumber());
    					if(int_ackno>0)
    					{
    						acknowledgementDataEntryForm.setValidateMsg("Acknowledgement Number Is Successfully Updated");
    						acknowledgementDataEntryForm.setErrorFlag("1");
    					}
    				}
    				else
    				{
    					acknowledgementDataEntryForm.setValidateMsg("Enter Valid Ack Num");
    				}
    			}
    			
    			httpServletRequest.setAttribute("sysDate",LockerDelegate.getSysDate());
    			httpServletRequest.setAttribute("sourceNums", array_branchobject);	
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(acknowledgementDataEntryForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ControlNumbersQueryMlink"))
	{
    	try{
    		 
    		
    		ControlNumbersQuery controlNumbersQuery=(ControlNumbersQuery)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(controlNumbersQuery.getPageId())){

    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(controlNumbersQuery.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ControlNumbersQuerylink"))
	{
    	try
    	{
    		 
    		
    		ControlNumbersQuery controlNumbersQuery=(ControlNumbersQuery)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(controlNumbersQuery.getPageId())){

    			ClearingObject clearingobject[]=delegate.getDetails(Integer.parseInt(controlNumbersQuery.getFromControlNumber()),Integer.parseInt(controlNumbersQuery.getToControlNumber()),null);
    			httpServletRequest.setAttribute("details", clearingobject);
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(controlNumbersQuery.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ControlNumbersQuerylink")){
    	
		try
		{
    		 
    		
    		ControlNumbersQuery controlNumbersQuery=(ControlNumbersQuery)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(controlNumbersQuery.getPageId())){

    			ClearingObject clearingobject[]=delegate.getDetails(Integer.parseInt(controlNumbersQuery.getFromControlNumber()),Integer.parseInt(controlNumbersQuery.getToControlNumber()),null);
    			httpServletRequest.setAttribute("details", clearingobject);
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(controlNumbersQuery.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
		catch(Exception e)
		{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ControlNumbersQueryMlink")){
    	
		try
		{
    		 
    		
    		ControlNumbersQuery controlNumbersQuery=(ControlNumbersQuery)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(controlNumbersQuery.getPageId())){

    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(controlNumbersQuery.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
		catch(Exception e)
		{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ClearingIdentificationM"))
	{
    	try
    	{
    		 
    		
    		ClearingIdentificationForm clearingIdentificationForm=(ClearingIdentificationForm)actionForm;
    		System.out.println("am i into deep"+clearingIdentificationForm.getPageId());
    			
    		if(MenuNameReader.containsKeyScreen(clearingIdentificationForm.getPageId())){
    			
    			System.out.println("hv i lost my mind");
    			module = ClearingDelegate.getInstance().getMainModule(4);   			
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(clearingIdentificationForm.getPageId()));
    			httpServletRequest.setAttribute("date",delegate.getSysDate()); 
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/InwardstmtMlink"))
	{
		try
		{
			 System.out.println("*********InwardStatement*************");
			InwardstmtForm Inwdstmtform=(InwardstmtForm)actionForm;
			httpServletRequest.setAttribute("clrdate", ClearingDelegate.getSysDate());
			
		     if(MenuNameReader.containsKeyScreen(Inwdstmtform.getPageId()))
				{
		    	 	path =MenuNameReader.getScreenProperties(Inwdstmtform.getPageId());
					httpServletRequest.setAttribute("pageId",path);
					setInwardstmtForm(httpServletRequest,path,delegate);
					
					return actionMapping.findForward(ResultHelp.getSuccess());

				}
		     else
		     	{
		    	 return actionMapping.findForward(ResultHelp.getError());
		     	}
			}
			catch(Exception e) 
			{		
			       e.printStackTrace(); 
		    	   return actionMapping.findForward(ResultHelp.getSuccess());
			}
		      
	}
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/BundledDataEntryMlink"))
	{
    	try
    	{
    		BundledDataEntry  bundledDataEntry=(BundledDataEntry)actionForm;
    		bundledDataEntry.setErrorFlag("0");
    		System.out.println("from page id menu-----<?-?>"+MenuNameReader.containsKeyScreen(bundledDataEntry.getPageId()));
    		if(MenuNameReader.containsKeyScreen(bundledDataEntry.getPageId()))
    		{
    			bundledDataEntry.setControlNum("0");
    			bundledDataEntry.setRemAckAmount("");
    			bundledDataEntry.setAmount("");
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(bundledDataEntry.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/BundledDataEntrylink"))
	{
    	try
    	{
    		 BundledDataEntry  bundledDataEntry=(BundledDataEntry)actionForm;
    		 double double_ackrem=0.0;
    		 bundledDataEntry.setValidateMsg("");
    		 bundledDataEntry.setErrorFlag("0");
    		if(MenuNameReader.containsKeyScreen(bundledDataEntry.getPageId()))
    		{
    			System.out.println("inside linkkkk");
    			
    			if(bundledDataEntry.getFlag().equalsIgnoreCase("frmCntrlNum"))
    			{
    				if(bundledDataEntry.getControlNum()!=null && bundledDataEntry.getControlNum().length()>0 )
    				{
    					if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==1)
    					{
    						System.out.println("Inside boolen flag 11111--------now");
    						ackobject=delegate.getBundleAckDetails(Integer.parseInt(bundledDataEntry.getControlNum()),2);
    					}
    					else if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==0)
    					{
    						System.out.println("Inside boolen flag 0000----now");
    						ackobject=delegate.getBundleAckDetails(Integer.parseInt(bundledDataEntry.getControlNum()),1);
    					}
    					if(ackobject != null)
						{
								
								System.out.println("ooooo66666666666666ooooooooo");
								bundledDataEntry.setAckNum(String.valueOf(ackobject.getAckNo()));
								bundledDataEntry.setAmount(String.valueOf(ackobject.getTotal()));
								bundledDataEntry.setAckName(ackobject.branchobject.getBranchName());
								System.out.println("branchname" +ackobject.branchobject.getBranchName());
								System.out.println("o is"+ ackobject.getDocSource() + "$$" +ackobject.getClgType());
							
								if(ackobject.getClgType().equalsIgnoreCase("I"))
								{
									bundledDataEntry.setBundleType("InWard");
									//setComboBundleType(ackobject.getDocSource(), ackobject.getClgType() ,1);
								}
								else if(ackobject.getClgType().equalsIgnoreCase("O"))
								{
									bundledDataEntry.setBundleType("OutWard");
								}
								else if(ackobject.getClgType().equalsIgnoreCase("D"))
								{
									bundledDataEntry.setBundleType("DeditECS");
								}
								else if(ackobject.getClgType().equalsIgnoreCase("C"))
								{
									bundledDataEntry.setBundleType("CreditECS");
								}
								else if(ackobject.getClgType().equalsIgnoreCase("T"))
								{
									bundledDataEntry.setBundleType("Transfer");
								}
								System.out.println("pppppppppppppppppp");
								String str = ackobject.getBankCode();
								System.out.println(str.substring(0, 3)+"-"+ str.substring(3, 6)+"-"+str.substring(6, 9));
								bundledDataEntry.setCityCode(str.substring(0, 3));
								bundledDataEntry.setBankCode(str.substring(3, 6));
								bundledDataEntry.setBranchCode(str.substring(6, 9));
								System.out.println("after setting bankcode");
								System.out.println("fter bank name====="+ackobject.getBankName());
								bundledDataEntry.setBankName(ackobject.getBankName());
								System.out.println("after bank name");
								bundledDataEntry.setDestinationBranch(String.valueOf(ackobject.getBranch()));
								bundledDataEntry.setDestName(ackobject.branchobject.getBranchAddress());
								bundledDataEntry.setDocument(String.valueOf(ackobject.getNoDocs()));
								bundledDataEntry.setTotalAmount(String.valueOf(ackobject.getDocTotal()));
								System.out.println("before ack amount");	
								System.out.println("double_ackrem====="+double_ackrem);
								double_ackrem=ackobject.getTotal()- ackobject.getAckEntered();
								System.out.println("double_ackrem2222222222"+double_ackrem);
								double_ackrem += ackobject.getDocTotal();
								System.out.println("double_ackrem333333333"+double_ackrem);
							 
								if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==1)
								{
									bundledDataEntry.setRemAckAmount("0.0");
								}
								else if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==0)
									bundledDataEntry.setRemAckAmount(String.valueOf(double_ackrem));
							
								System.out.println("setRemAckAmount====="+String.valueOf(double_ackrem));
							
						}
    					else
    					{
    						bundledDataEntry.setValidateMsg("Control Number Not Found");
    						
    					}
    				}
    				else bundledDataEntry.setControlNum("0");
    				
    			}    			
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("frmAckNum"))
    			{ 
    				System.out.println("inside ack num");
    				if(Integer.parseInt(bundledDataEntry.getAckNum())>0)
    				{	
    						System.out.println("before ackobject");	
    						ackobject = delegate.getAckDetails(Integer.parseInt(bundledDataEntry.getAckNum()),0);
    						System.out.println("after ackobject");
    						
    						if(ackobject!=null && ackobject.getReconciled().equals("F"))
    						{
    					 	
    							System.out.println("ackobject.getAckNo()>0==========ackobject.getTotal()================="+ackobject.getTotal());
    							bundledDataEntry.setAmount(String.valueOf(ackobject.getTotal()));
    							bundledDataEntry.setAckName(ackobject.branchobject.getBranchName());
    							bundledDataEntry.setBankName(ackobject.getBankName());
    							System.out.println("ackobject.getClgType().equalsIgnoreCase=="+ackobject.getClgType());
    							int ackno = ackobject.getAckNo();
    							System.out.println("--ack-no--->"+ackno);
    							
    							if((ackobject.getDocSource()%1111)==0)
    							{
    								System.out.println("helllo =----------------->"+bankLocation);
    						
    								bundledDataEntry.setDestinationBranch("");
    								bundledDataEntry.setDestName("");
    								bundledDataEntry.setBranchCode("001");
    								bundledDataEntry.setBankCode("000");
    								bundledDataEntry.setCityCode("000");
    								bundledDataEntry.setBankName(bankLocation);
    							
    								if(ackobject.getClgType().equalsIgnoreCase("I"))
    									bundledDataEntry.setBundleType("InWard");
    								else if ( ackobject.getClgType().equalsIgnoreCase("D"))
    									bundledDataEntry.setBundleType("DeditECS");
    								else if ( ackobject.getClgType().equalsIgnoreCase("C"))
    									bundledDataEntry.setBundleType("CreditECS");
    								
    							}
    							else
    							{
    							
    								bundledDataEntry.setDestinationBranch("9999");  
    								bundledDataEntry.setDestName("ApexBank");
    								bundledDataEntry.setBranchCode("000");
    								bundledDataEntry.setBankCode("000");
    								bundledDataEntry.setCityCode("000");
    								bundledDataEntry.setBankName(ackobject.getBankName());
    								
    							
    								if(ackobject.getClgType().equalsIgnoreCase("O"))
    									bundledDataEntry.setBundleType("OutWard");
    								else if(ackobject.getClgType().equalsIgnoreCase("T"))
    									bundledDataEntry.setBundleType("Transfer");
    								else if(ackobject.getClgType().equalsIgnoreCase("D"))
    									bundledDataEntry.setBundleType("DeditECS");
    								else if(ackobject.getClgType().equalsIgnoreCase("C"))
    									bundledDataEntry.setBundleType("CreditECS");
    						
    						    }
    							
    							
    						double_ackrem = ackobject.getTotal()-ackobject.getAckEntered();
    						bundledDataEntry.setRemAckAmount(String.valueOf(double_ackrem));
    						httpServletRequest.setAttribute("ackobject",ackobject);
    					}
    					else
    						bundledDataEntry.setValidateMsg("Acknowledgement Not Found");
    					
    				}
    				else
    				{
    					bundledDataEntry.setValidateMsg("Enter Valid Ack Number");
    				}
    				
    			}
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("frmBankCode"))
    			{
    				//String code=bundledDataEntry.getCityCode()+bundledDataEntry.getBankCode()+bundledDataEntry.getBranchCode();
    				
    				if(hash_bank == null)
    				{
    					hash_bank = delegate.getCityBankBranchDetail(3, null, bundledDataEntry.getBankCode(),null);
    				}
    				if(hash_branch == null)
    				{
						hash_branch = delegate.getCityBankBranchDetail(4, bundledDataEntry.getCityCode(), bundledDataEntry.getBankCode(),bundledDataEntry.getBranchCode());
					}
    				enume_bank = hash_bank.keys(); 
    				Object str1 = (Object)  bundledDataEntry.getBankCode();
    				
    				while(enume_bank.hasMoreElements())
    				{
    					if (enume_bank.nextElement().equals(str1.toString()))
    					{
    						String ss = hash_bank.get(str1).toString();
    						if(ss.length()<22)
    							bundledDataEntry.setBankName(hash_bank.get(str1).toString());
    						else
    							bundledDataEntry.setBankName(hash_bank.get(str1).toString().substring(0, 22));
    					}
    				}
    				
    			}
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("DestBranch"))
    			{
    				
    				if(bundledDataEntry.getDestinationBranch().length()>0)
    				{
    				try
    				{
    					int bra_code = Integer.parseInt(bundledDataEntry.getDestinationBranch().trim());
    					if( bra_code == 2 || bra_code == 3 || bra_code == 4)
    					{
    					BranchObject[] bo=delegate.getBranchDetails(Integer.parseInt(bundledDataEntry.getDestinationBranch()));
    					if(bo[0].getBranchCode()!=-1)
    						bundledDataEntry.setDestName(bo[0].getBranchName());
    					}
    					else
    					{
    						bundledDataEntry.setValidateMsg("Please Enter Valid Branch Code");
    						bundledDataEntry.setDestinationBranch("");
    						bundledDataEntry.setDestName("");
    					}
    				}
    				catch(RemoteException re)
    				{
    					System.out.println("clg:ackDE:getting Br name"+re);
    				} 
    				catch (NumberFormatException e) 
    				{
    					
    					e.printStackTrace();
    				}
    				catch (RecordsNotFoundException e) 
    				{
    					
    					e.printStackTrace();
    				}
    			  }
    			}
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("frmAmount"))
				{
					if((bundledDataEntry.getRemAckAmount().toString())!=null)
					{
						System.out.println("inside not null");
						if(Double.parseDouble(bundledDataEntry.getTotalAmount().toString().trim()) > Double.parseDouble(bundledDataEntry.getRemAckAmount().toString().trim()) )
						{
							bundledDataEntry.setValidateMsg( "Amount Is Greater Than Acknowledgement Amount");
							bundledDataEntry.setTotalAmount("");
						}
					}
					else
					{
						bundledDataEntry.setValidateMsg("Enter Remaining Acknowledgement Amount");
					}
				}
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("frmButton"))
    			{
    				
    				ChequeAckObject chequeackobject=new ChequeAckObject();
					
					chequeackobject.setControlNo(Integer.parseInt(bundledDataEntry.getControlNum()));
					System.out.println(bundledDataEntry.getControlNum() +" control number");
					  
					chequeackobject.setTranType("G");
					if(bundledDataEntry.getBundleType()!=null)
					{
						if(bundledDataEntry.getBundleType().toString().equalsIgnoreCase("InWard"))
						{
							chequeackobject.setClgType("I");
						} 
						else if(bundledDataEntry.getBundleType().toString().equalsIgnoreCase("CreditECS"))
						{
							chequeackobject.setClgType("C");
						}
						else if(bundledDataEntry.getBundleType().toString().equalsIgnoreCase("DeditECS"))
						{
							chequeackobject.setClgType("D");
						}
						else if(bundledDataEntry.getBundleType().equalsIgnoreCase("OutWard"))
						{
							chequeackobject.setClgType("O");
						}
						else if(bundledDataEntry.getBundleType().toString().equalsIgnoreCase("Transfer"))
						{
							chequeackobject.setTranType("T");
							chequeackobject.setClgType("T");
						}
					}
					else
					{
						bundledDataEntry.setValidateMsg("Enter Bundled Type");
					}
					if(bundledDataEntry.getAckNum()!=null)
					{
							chequeackobject.setAckNo(Integer.parseInt(bundledDataEntry.getAckNum()));
							chequeackobject.setDocSource(ackobject.getDocSource());
					}
					else
					{
							bundledDataEntry.setValidateMsg("Enter Acknowledgement Number");
					}
					if(bundledDataEntry.getDestinationBranch()!=null)
					{
						chequeackobject.setDocDestination(Integer.parseInt(bundledDataEntry.getDestinationBranch()));
						chequeackobject.setDocBs("B");
					}
					else
					{
							bundledDataEntry.setValidateMsg("Enter Destination Branch ");
					}
					if(bundledDataEntry.getDocument()!=null)
					{
							chequeackobject.setNoOfDocs(Integer.parseInt(bundledDataEntry.getDocument()));
					}
					else
					{
							bundledDataEntry.setValidateMsg("Enter Number Of Documents");
					}
					if(bundledDataEntry.getTotalAmount()!=null)
					{
						chequeackobject.setDocTotal(Double.parseDouble(bundledDataEntry.getTotalAmount()));
					}
					else
					{
							bundledDataEntry.setValidateMsg("Enter Total Amount");
					}
					if(bundledDataEntry.getBankCode()==null && bundledDataEntry.getCityCode()==null && bundledDataEntry.getBranchCode()==null)
					{
							bundledDataEntry.setValidateMsg("Enter Valid Bank Code");
					}
					else
					{
							System.out.println("before bank code");
							chequeackobject.setBankCode(bundledDataEntry.getCityCode() + bundledDataEntry.getBankCode()+ bundledDataEntry.getBranchCode());
							System.out.println("after bank code");
						
							chequeackobject.setBankName(bundledDataEntry.getBankName());
					}
						
					chequeackobject.setCreditACType(ackobject.branchobject.getBranchACType());
					chequeackobject.setCreditACNo(ackobject.branchobject.getBranchACNo());
						
						chequeackobject.setDeTml(tml);
						System.out.println("tml===>"+tml);
						chequeackobject.setDeUser(uid);
						System.out.println("u8id====>"+uid);
						chequeackobject.setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
						if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==1)
						{
							
							chequeackobject.setVeTml(tml);
							chequeackobject.setVeUser(uid);
						}
						long long_ctrlno = 0; 
						long_ctrlno=delegate.storeChequeData(chequeackobject);
					    		
						if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==0)
						{	
							if(long_ctrlno >0)
							{	
								if(Integer.parseInt(bundledDataEntry.getControlNum())>0)
								{
									bundledDataEntry.setValidateMsg("Updated SuccesFully");
								}
								else
								{
									bundledDataEntry.setValidateMsg("Note Down Control Number "+long_ctrlno);
									bundledDataEntry.setErrorFlag("1");
								}
							
							}
						}
						else if(Integer.parseInt(bundledDataEntry.getBooleanFlag())==1)
						{
							if(long_ctrlno>0)
							{
								bundledDataEntry.setValidateMsg("Successfully Verified");
								bundledDataEntry.setErrorFlag("1");
							}
						}
    			}
    			else if(bundledDataEntry.getFlag().equalsIgnoreCase("frmDelete"))
    			{	
    				System.out.println("inside delete");
    				if(bundledDataEntry.getControlNum()!=null && Integer.parseInt(bundledDataEntry.getControlNum())>0)
    				{
    						  int int_delete=delegate.deleteControl(Integer.parseInt(bundledDataEntry.getControlNum()));
    								if(int_delete==1)
    								{
    									bundledDataEntry.setValidateMsg("Control Number Deleted!");
    									bundledDataEntry.setErrorFlag("1");
    								}
    								else
    									bundledDataEntry.setValidateMsg("Unable to Delete.Try Again!");
    				}
    				else
    				{
    					bundledDataEntry.setValidateMsg("Enter Valid Control Number");
    				}
    			}
    			else
    			{
    				bundledDataEntry.setValidateMsg("Records Not Found");
    			}
    			System.out.println("from page id link-----<??>"+MenuNameReader.getScreenProperties(bundledDataEntry.getPageId()));
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(bundledDataEntry.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }

	else  if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/unIdentifyMlink"))
	{
    	try
    	{
    		UnIdentify uniden=(UnIdentify) actionForm;
    		if(MenuNameReader.containsKeyScreen(uniden.getPageId()))
    		{

    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(uniden.getPageId()));
    			httpServletRequest.setAttribute("date",delegate.getSysDate()); 
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }

	
	else  if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/unIdentifylink"))
	{
		UnIdentify unIdentify=(UnIdentify)actionForm;
		unIdentify.setValidateFlag("");
		try
		{
    		  			
    		if(MenuNameReader.containsKeyScreen(unIdentify.getPageId()))
    		{
    			ArrayList arrayList=new ArrayList();
    			httpServletRequest.setAttribute("date",delegate.getSysDate()); 
    			
    		 if(unIdentify.getFlag().equalsIgnoreCase("frmSendTo"))
    			{	
    			 
    			 if(unIdentify.getSent_to()!=null)
    			 {
    			 		System.out.println("inside send");
    			 	BranchObject[] array_branchobject=(BranchObject[])delegate.getBranchDetails(Integer.parseInt(unIdentify.getSent_to()));
    				//System.out.println("after object"+array_branchobject.length);
    				if(array_branchobject!=null)
    				{	System.out.println("indide if");
    					if( array_branchobject != null && array_branchobject[0].getBranchCode()!=-1)
    						{	
    							try
    							{
    								ChequeDepositionObject[] cd=(ChequeDepositionObject[])delegate.getIdentifiedCheques(unIdentify.getClr_date(),Integer.parseInt(unIdentify.getClr_no()),Integer.parseInt(unIdentify.getSent_to()));
    								if(cd!=null)
    								{
    									System.out.println("cd!=null in undentify action");
    									httpServletRequest.setAttribute("details", cd);
    								}
    								else
    								{
    									System.out.println("rexords not found in unidentify....");
    									unIdentify.setValidateFlag("Records Not Found");
    								}
    							}
    							catch (RecordsNotFoundException e) 
    							{
    								unIdentify.setValidateFlag("Records Not Found");
								}
    							
    						}
    				
    						else unIdentify.setValidateFlag("Records not found");
    					
    				}  
    				else
    				{
    					System.out.println("Records not found");
    					unIdentify.setValidateFlag("Out Ward Statement Not found");
    				}
    			 }
    			 else
    			 {
    				 unIdentify.setValidateFlag("Enter Value In SendTo");
    			 }
    			}
    		 	else if(unIdentify.getFlag().equalsIgnoreCase("unIdentify"))
    			{
    			 System.out.println("inside unidentify");
    			 	String[] cbox=httpServletRequest.getParameterValues("chkBox");
    			 	if(cbox==null)
    			 	{
    			 		unIdentify.setValidateFlag("No Rows Checked");
    			 	}
 					else if( cbox.length > 0)
    			 	{
    			 		long[]  ctrl_no = new long[cbox.length] ;
    			 		String[] ctrlNum=httpServletRequest.getParameterValues("txtCtrlNum");
    			 		System.out.println("inside if");
 							for(int i=0,j=0;i<cbox.length;i++)
 							{
 								System.out.println("inside for"+cbox[i]);
 								int x=Integer.parseInt(cbox[i]);
 								System.out.println("......,"+x);
 								System.out.println("******1234***"+ctrlNum[x]);
 								System.out.println(" ctrlNum[x]==============="+ Long.parseLong(ctrlNum[x]));
 								ctrl_no[j]=Long.parseLong(ctrlNum[x]);
 								System.out.println("ctrl_no[j]-------->"+ctrl_no[j]);
 								j++;
 							}
 			
 							if ( delegate.identifyCheques(ctrl_no, unIdentify.getClr_date(), Integer.parseInt(unIdentify.getClr_no()), Integer .parseInt(unIdentify.getSent_to()), -1 ) > 0 )
 							{
 									unIdentify.setValidateFlag("Successfully UnIdentified ");
 							} 
 							else 
 							{
 									unIdentify.setValidateFlag("UnIdentification UnSuccessfull");
 							}
 					}
    			}
    			 			
    		 
    		 httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(unIdentify.getPageId()));
    		 return actionMapping.findForward(ResultHelp.getSuccess());
    			}
    		else
    			{
    				return actionMapping.findForward(ResultHelp.getSuccess());
    			}
    		
    		}
			catch(Exception e)
			{
				e.printStackTrace();
				return actionMapping.findForward(ResultHelp.getSuccess());
			}
	}

	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/AckReconciliationMlink"))
	{
    	try
    	{
    			
    		BranchObject branchobject=new BranchObject();
    		AckReconciliationForm ackReconciliationForm=(AckReconciliationForm)actionForm;
    		ackReconciliationForm.setValidateFlag(null);
    		System.out.println("988888888"+ackReconciliationForm.getPageId());
    		ArrayList arrayList=new ArrayList();
    		if(MenuNameReader.containsKeyScreen(ackReconciliationForm.getPageId())){
    			
    			AckObject[] array_ackobject=delegate.getAckReconcillation(false);
    			try
    			{
    				if(array_ackobject!=null)
    				{
    					httpServletRequest.setAttribute("details",array_ackobject);
    				}
    				else
    				{
    					ackReconciliationForm.setValidateFlag("Records Not Found");
    				}
    			}
    			catch (Exception e) {
    				ackReconciliationForm.setValidateFlag("Records Not Found");
				}
    			session=httpServletRequest.getSession(true);
    			session.setAttribute("reconceilNumbers",arrayList);
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(ackReconciliationForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }


	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/AckReconciliationlink"))
	{
    	try
    	{
    		
    		AckReconciliationForm ackReconciliationForm=(AckReconciliationForm)actionForm;
    		ackReconciliationForm.setValidateFlag(null);
    		if(MenuNameReader.containsKeyScreen(ackReconciliationForm.getPageId()))
    		{

    			System.out.println("ackReconciliationForm.getFlag()"+ackReconciliationForm.getFlag());
    		
    			if(ackReconciliationForm.getFlag().equalsIgnoreCase("submit"))
    			{
    						String[] cBox=httpServletRequest.getParameterValues("chkBox");
    						String[] ackNum=httpServletRequest.getParameterValues("txtAckNum");
    					if(cBox!=null)
    					{
    						for(int i=0;i<cBox.length;i++)
    						{
								int x=Integer.parseInt(cBox[i]);
								System.out.println("value of XXXXX==="+x);
								System.out.println("value of cbox==="+cBox[i]);
								System.out.println("length of chk box===="+cBox.length);
								System.out.println("ack num===>"+ackNum[x]);
								try
								{
									double double_diffamount=delegate.reconcile(Integer.parseInt((ackNum[x])));
									System.out.println("after double_diffamount ");
    					
									if(double_diffamount==0.0)
									{
										ackReconciliationForm.setValidateFlag("Reconciled");
									}	
									else if(double_diffamount==Double.NaN)
									{
										ackReconciliationForm.setValidateFlag("Not Reconciled");
									}	
    					
									else
									{
										session= httpServletRequest.getSession(true);
										session.setAttribute("ackNumberToNewTab",ackNum[x]);
    						
										//System.out.println("uuuuuuuuuuuooooooooooo"+map.get("acNum").toString());
										ackReconciliationForm.setValidateFlag("getNewTable");
    						
									}
    							}
							
    						catch (RecordsNotFoundException re)
    						{	
    							//re.printStackTrace();
    							ackReconciliationForm.setValidateFlag("Records Not found");
    						}
    				    }
    				}
    				else
    				{
    						ackReconciliationForm.setValidateFlag("Select Rows");
    				}
    				try
    				{	
    					AckObject[] array_ackobject=delegate.getAckReconcillation(false);
    					
    	    			if(array_ackobject!=null)
    	    			{
    	    				httpServletRequest.setAttribute("details",array_ackobject);
    	    			}
    	    			
    				}
    				catch (Exception e) {
					}
    			
    			}
    			
    			if(ackReconciliationForm.getFlag().equalsIgnoreCase("getNewTable"))
    			{
    				
    				System.out.println("getting new table...???..!!!!");
    				
    				
    				String getNewTable="getNewTable";
    				Object ackNumberToNewTab=session.getAttribute("ackNumberToNewTab");
    				System.out.println("where do u goooooooo"+ackNumberToNewTab.toString());
    			
    				
    				
    				//AckObject array_ackobject=Client.getAckDetails(Integer.parseInt(table_reconsol.getValueAt(table_reconsol.getSelectedRow(),1).toString()),0);
    				AckObject ackObject=delegate.getAckDetails(Integer.parseInt(ackNumberToNewTab.toString()),0);
    		 		
    				if(ackObject.getAckNo()!=-1)
    				{
    					
    					ChequeAckObject chequeAckObject[]=delegate.getChequeDetail(Integer.parseInt(ackNumberToNewTab.toString()));
    					System.out.println("-+-+-+-+-+-+-+"+chequeAckObject);
    					if(chequeAckObject!=null)
    					{
    						
    						ackReconciliationForm.setValidateFlag("buildNewTable");
    						httpServletRequest.setAttribute("chequeAckObject",chequeAckObject);
    						for(int i=0;i<chequeAckObject.length;i++)
    						{
    							System.out.println(chequeAckObject[i].getControlNo()+"<-------->"+chequeAckObject[i].getDocTotal());
    						}
    					}
    					
    				}else
    					ackReconciliationForm.setValidateFlag("No Rows Found");
    				
    				httpServletRequest.setAttribute("getNewTable",getNewTable);
    			}
    			
    			
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(ackReconciliationForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/LateReturnsMLink"))
	{
    	try
    	{
    		
    		LateReturnsForm lateReturnsForm=(LateReturnsForm)actionForm;
    		
    		if(MenuNameReader.containsKeyScreen(lateReturnsForm.getPageId())){

    			 httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());   			
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(lateReturnsForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/LateReturnsLink"))
	{
    	try
    	{
    		 
    		System.out.println("inside try");
    		LateReturnsForm lateReturnsForm=(LateReturnsForm)actionForm;
    		lateReturnsForm.setValidateFlag("");
    		module = ClearingDelegate.getInstance().getMainModule(1);
    		
    		if(MenuNameReader.containsKeyScreen(lateReturnsForm.getPageId()))
    		{
    			System.out.println("before ctrl");
    			if(lateReturnsForm.getFlag().equalsIgnoreCase("frmCtrlNum"))
    			{
    				System.out.println("inside ifffff");				
    				ChequeDepositionObject[] chequedeposaitionobject=new ChequeDepositionObject[1];
    				System.out.println("after iffff");
    				 chequedeposaitionobject[0]=delegate.getLateReturnDetail(Integer.parseInt(lateReturnsForm.getControlno()));
    				
    				if(chequedeposaitionobject[0]!=null)
    				{	
    					System.out.println("iam not nulllll");
    					if(chequedeposaitionobject[0].getDespInd().equals("F"))
    					{
    							lateReturnsForm.setValidateFlag("Cheque Not Yet Dispatched");
    							System.out.println("after iffmmmmeeeee");
    					}
    					if(chequedeposaitionobject[0].getMultiCredit().equals("T"))
						{
						    lateReturnsForm.setMulti_credit(true);
						    
						}
    					else
    					{
    							lateReturnsForm.setMulti_credit(false);
    							lateReturnsForm.setCreditAcType(chequedeposaitionobject[0].getCreditACType());
    							lateReturnsForm.setCreditAcNum(String.valueOf(chequedeposaitionobject[0].getCreditACNo()));
//    							AccountObject ac=null;
//    							ac=delegate.getAccount(chequedeposaitionobject[0].getCreditACType(),chequedeposaitionobject[0].getCreditACNo(),ClearingDelegate.getSysDate());
//						        //lbl_name.setText(ac.getAccname());
//						         System.out.println("inside else");
    							httpServletRequest.setAttribute("chequedeposaitionobject",chequedeposaitionobject);
    					}
    						lateReturnsForm.setAmount(String.valueOf(chequedeposaitionobject[0].getTranAmt()));
						
						if(chequedeposaitionobject[0].getDiscInd().equals("T"))
						    lateReturnsForm.setDiscount(true); 
						else
							lateReturnsForm.setDiscount(false); 
						
						if(chequedeposaitionobject[0].getBounceInd().equals("T"))
							lateReturnsForm.setBounce(true);
						else
							lateReturnsForm.setBounce(false);
						
						if(chequedeposaitionobject[0].getChqDDPO()!=null)
						{	
							if(chequedeposaitionobject[0].getChqDDPO().equals("C"))
								lateReturnsForm.setChqddpo("Cheque");
							else if(chequedeposaitionobject[0].getChqDDPO().equals("P"))
								lateReturnsForm.setChqddpo("Pay Order");
							else
								lateReturnsForm.setChqddpo("DDraft");
						}
						lateReturnsForm.setChqno(String.valueOf(chequedeposaitionobject[0].getQdpNo()));
						lateReturnsForm.setChqdate(chequedeposaitionobject[0].getQdpDate());
						lateReturnsForm.setPocomm(String.valueOf(chequedeposaitionobject[0].getPOCommission()));
						lateReturnsForm.setDiscountCharge(String.valueOf(chequedeposaitionobject[0].getDiscAmt()));
						
    				}
    				else 
    					{
    					lateReturnsForm.setValidateFlag("Records Not Found");
    					}
    			}
    			
    			else if(lateReturnsForm.getFlag().equalsIgnoreCase("submit"))
    			{
    				System.out.println(Long.parseLong(lateReturnsForm.getControlno())+"<--inside submit-->"+lateReturnsForm.getClr_date());
    				
    				
    				int b=delegate.lateReturnCheques(Long.parseLong(lateReturnsForm.getControlno()),lateReturnsForm.getClr_date());
    				if(b>0)
    					lateReturnsForm.setValidateFlag("Clearing Date Updated Successfully");
    				else
    					lateReturnsForm.setValidateFlag("Clearing Date Updation UnSuccessful");
    				
    			}
    			httpServletRequest.setAttribute("Loan_Module_code",module);
    			httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(lateReturnsForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
}
	
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/OutStationChequeMLink"))
	{
    		try
    		{	
    			System.out.println("inside try++===");
    			OutStationForm outStationForm=(OutStationForm)actionForm;
    			outStationForm.setValidateFlag("");
    			outStationForm.setErrorFlag("");
    			if(MenuNameReader.containsKeyScreen(outStationForm.getPageId()))
    			{	System.out.println("before page id");
    				httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(outStationForm.getPageId()));
    				outStationForm.setAmount("0.0");
    				outStationForm.setPanelAmount("0.0");
    				outStationForm.setCtrlNum("0");
    				outStationForm.setCommission("0.0");
    				outStationForm.setPrevCtrlNum("0");
       				System.out.println("after page id");
    				Reason[] reason = delegate.getReasonDetails("1002001",0);
    				httpServletRequest.setAttribute("Reason", reason);
       				module = ClearingDelegate.getInstance().getMainModule(1);
       				httpServletRequest.setAttribute("Main_Module_code",module);
       				return actionMapping.findForward(ResultHelp.getSuccess());
    			}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    	 
    }
	else if(actionMapping.getPath().equalsIgnoreCase("/Clearing/OutStationChequeLink"))
	{
		try
		{	
			 	ChequeDepositionObject cheDepoObject;
				AccountObject accountobject_crd=null;
				OutStationForm outStationForm=(OutStationForm)actionForm;
				outStationForm.setValidateFlag("");
				outStationForm.setErrorFlag("0");
				int custId=0;
				if(MenuNameReader.containsKeyScreen(outStationForm.getPageId()))
				{	
						Reason[] reason = delegate.getReasonDetails("1002001",0);
						

						if(outStationForm.getFlag().equalsIgnoreCase("frmMicrCode"))
						{
							
							if(!(outStationForm.getBnkCode().equalsIgnoreCase("000") ))
							{
								
								String code  = ((outStationForm.getPanelCityCode()+outStationForm.getPanelBnkCode()+outStationForm.getPanelBrhCode()));
								System.out.println("====>>>"+code);
								HashMap map_result   = delegate.getCityBankBranchDetail(code);					
								if(map_result!=null)
								{
								
									if ( map_result.containsKey( code.substring(0, 3) ) ) 
									{
										outStationForm.setPanelBnkName( (String)(map_result.get(  code.substring(0, 3))));
									}
								
									if ( map_result.containsKey( code.substring(3, 6) ) )  
									{
									
										outStationForm.setPanelBnkName( outStationForm.getPanelBnkName()+" "+ (String)(map_result.get(  code.substring(3, 6))));
									}
								
									if ( map_result.containsKey( code.substring( 6, 9) ) )  
									{
									
										outStationForm.setPanelBrhName((String)(map_result.get(  code.substring(6, 9))));
									}
								}
								else
								{
									outStationForm.setValidateFlag("Bank Doesn't Found");
								}
							}
							
							
						} 
						else if(outStationForm.getFlag().equalsIgnoreCase("frmAcNum"))
						{
							AccountObject acc_obj = delegate.getAccount(  outStationForm.getCrAccTyp(), Integer.parseInt(outStationForm.getCrAccNum()), "12/02/2007");
							
							if(acc_obj == null)
							{
								outStationForm.setValidateFlag("No Such Account Found With Account Number   " + outStationForm.getCrAccNum());
								
							} 
							else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
							{
								
								outStationForm.setValidateFlag("Account Is Closed   " );
						
							} 
							else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
							{
								
								outStationForm.setValidateFlag("Account Is Freezed   " );
						
							} 
							else 
							{
								
								
								outStationForm.setAcName(acc_obj.getAccname());
								
								CustomerMasterObject cust = delegate.getCustomerDetail( acc_obj.getCid() );
								
								httpServletRequest.setAttribute("personalInfo", cust);
								
							}
						}
						else if(outStationForm.getFlag().equalsIgnoreCase("frmCtrlNum"))
						{	
							ChequeDepositionObject[] chequedepositionobject = new ChequeDepositionObject[1];
							chequedepositionobject = delegate.retrieveChequeDetails(Integer.parseInt(outStationForm.getCtrlNum()),1);
							System.out.println("afetr delegaETE ");
							if(chequedepositionobject != null)
							{
								System.out.println("inside if====");
								if ( chequedepositionobject[0].getClgType().equalsIgnoreCase("S"))
								{
										outStationForm.setCityCode(chequedepositionobject[0].getBankCode().substring(0,3));
										outStationForm.setBnkCode(chequedepositionobject[0].getBankCode().substring(3,6));
										outStationForm.setBrhCode(chequedepositionobject[0].getBankCode().substring(6,9));
										outStationForm.setBrhName(chequedepositionobject[0].getBranchName());
										outStationForm.setCrAccNum(String.valueOf(chequedepositionobject[0].getCreditACNo()));
										outStationForm.setAmount(String.valueOf(chequedepositionobject[0].getTranAmt()));
										outStationForm.setChqDate(chequedepositionobject[0].getQdpDate());
										outStationForm.setCrAccTyp(chequedepositionobject[0].getCreditACType());
									
										accountobject_crd = (AccountObject)delegate.getAccount(chequedepositionobject[0].getCreditACType(),chequedepositionobject[0].getCreditACNo(),ClearingDelegate.getSysDate());
										custId=accountobject_crd.getCid();
										if (chequedepositionobject[0].getBounceInd().equalsIgnoreCase("T"))
										{
											
												//vector_normal_reasons =  chequedepositionobject[0].getReasonArray();
												 vector_all_reasons = chequedepositionobject[0].getReasonArray();
												outStationForm.setReturned(true);//combo_return.setSelectedIndex(1);
												outStationForm.setBounceFine(Double.toString(chequedepositionobject[0].getReasonFine()));
												
//												for(int i=0;i<vector_all_reasons.size();i++)
//												{
//													int j=Integer.parseInt(vector_all_reasons.get(i).toString());
//													if(j==Integer.parseInt(outStationForm.getTxtReasonCode()))
//													{
//														outStationForm.setChkBox(i);
//													}
//												}
												
										}
										else if ( chequedepositionobject[0].getprev_ctrl_no() != 0 )
										{
											System.out.println("inside prev ctrl num");
											ChequeDepositionObject[] chqdepobj_prev = new ChequeDepositionObject[1];
											chqdepobj_prev = delegate.retrieveChequeDetails((int)(chequedepositionobject[0].getprev_ctrl_no()),0);
											if (chqdepobj_prev != null)
											{
												String code  = chqdepobj_prev[0].getBankCode();
												outStationForm.setPanelCityCode(code.substring(0,3));
												outStationForm.setPanelBnkCode(code.substring(3,6));
												outStationForm.setPanelBrhCode(code.substring(6,9));
												
												outStationForm.setPanelBrhName(chqdepobj_prev[0].getBranchName());
												outStationForm.setPanelAmount(Double.toString(chqdepobj_prev[0].getTranAmt()));
												outStationForm.setPanelChqDate(chqdepobj_prev[0].getQdpDate());
												outStationForm.setPanelChqDDNum(Integer.toString(chqdepobj_prev[0].getQdpNo()));
												outStationForm.setCommission(Double.toString(chqdepobj_prev[0].getPOCommission()));
												outStationForm.setPrevCtrlNum(Long.toString(chequedepositionobject[0].getprev_ctrl_no()));
											}		
												
										}
								
								}
								
								else if ( chequedepositionobject[0].getClgType().equalsIgnoreCase("O") )
										{     
									
											outStationForm.setValidateFlag("Outward Clearing Cheque ");
										}
								
							}
							else
							{
								outStationForm.setValidateFlag("Records Not Found");
								
							}
							
						
						}
						else if(outStationForm.getFlag().equalsIgnoreCase("frmUpdate"))
						{
						 
							System.out.println("inside try");
							cheDepoObject = new ChequeDepositionObject();
								System.out.println("inside if");
									if(outStationForm.isReturned())
									{
											System.out.println("inside NO");
											if (Integer.parseInt( outStationForm.getCtrlNum().toString().trim() ) > 0 )
											{	
												System.out.println("inside ctrl num"+Integer.parseInt(outStationForm.getCtrlNum()));
												cheDepoObject.setControlNo( Integer.parseInt(outStationForm.getCtrlNum()));
												System.out.println("after ctrl num");
											} 
										
										
											String[] cbox=httpServletRequest.getParameterValues("chkBox");
											String[] bounceFine=httpServletRequest.getParameterValues("txtBounceFine");
											String[] description=httpServletRequest.getParameterValues("txtDesription");
											String[] reasonCode=httpServletRequest.getParameterValues("txtReasonCode");
											if(cbox!=null)
											{ 
												System.out.println("inside chkbox");
												for(int k=0;k<cbox.length;k++)
												{
													int x=Integer.parseInt(cbox[k]);
													System.out.println("value of XXXXX==="+x);
													System.out.println("value of cbox==="+cbox[k]);
													System.out.println("length of chk box===="+cbox.length);
													System.out.println("inside reason code======"+reasonCode[x]);
													vector_all_reasons.add(reasonCode[x]);
													System.out.println("inside fineee===="+bounceFine[x]);
													fine_amount+=Double.parseDouble(bounceFine[x]);
												}
										
												cheDepoObject.setReasonArray( vector_all_reasons );
												cheDepoObject.setFineAmt(fine_amount);
												cheDepoObject.setDe_date(delegate.getSysDate());
												cheDepoObject.setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
												cheDepoObject.setDeTml(tml);
												cheDepoObject.setDeUser(uid);
												cheDepoObject.setSysdate(delegate.getSysDate());
												boolean cond = delegate.updateOutStationBounceCheque(cheDepoObject);
												if (cond)
												{
												  outStationForm.setValidateFlag(" Cheque Has Been Bounced ");
												
												} 
												else
												{ 
													outStationForm.setValidateFlag(" Problem In Bouncing " );
												}
											}
											else
											{
												System.out.println("inside chkbox elsee");
												outStationForm.setValidateFlag("Select BounceFine And Reason Code");
											}
									}
							
								else{
									
									if((outStationForm.getCtrlNum().trim().length())>=1 )
										{
											long cn=0;
											System.out.println("before ctrl num===>>>"+cheDepoObject);
											cheDepoObject.setControlNo(cn);
											System.out.println("inside try 2222");
										}
										if(outStationForm.getPanelBnkCode().equalsIgnoreCase("000"))
										{
											
											System.out.println("in side micr code");
											cheDepoObject.setBankCode(outStationForm.getPanelBnkCode());
										}
										else
										{
											System.out.println("inside else 232323"+outStationForm.getCityCode()+String.valueOf(outStationForm.getBnkCode())+ outStationForm.getBrhCode());
											cheDepoObject.setBankCode(outStationForm.getPanelCityCode()+String.valueOf(outStationForm.getPanelBnkCode())+ outStationForm.getPanelBrhCode());
											cheDepoObject.setBankName(outStationForm.getPanelBnkName().toString());
											cheDepoObject.setBranchName(outStationForm.getPanelBrhName().trim());

										}	


								if(outStationForm.getAmount().trim().length() >= 1)
								{   
                                             System.out.println("another inside iffff");
									if(Double.parseDouble(outStationForm.getAmount().trim()) < Double.parseDouble(outStationForm.getPanelAmount().trim()))
									{

										outStationForm.setValidateFlag("Recieved Amount Is Greater Than Cheque Amount");
									} 
									else 
									{
										cheDepoObject.setTranAmt(Double.parseDouble(outStationForm.getPanelAmount().trim()));
									}

								} 


								if(outStationForm.getPanelChqDate().trim().length() > 1)
									cheDepoObject.setQdpDate(outStationForm.getPanelChqDate().trim());

								if(outStationForm.getPanelChqDDNum().toString().trim().length() >1)
									cheDepoObject.setQdpNo(Integer.parseInt(outStationForm.getPanelChqDDNum().trim()));
								
								if(outStationForm.getCommission().trim().length() > 1)
								{

									if ( Double.parseDouble(outStationForm.getCommission().trim()) > Double.parseDouble(outStationForm.getPanelAmount().toString().trim()))
									{
										outStationForm.setValidateFlag(" Commission Amount Is More Than Received Amount" );
									}
									else 
										cheDepoObject.setPOCommission(Double.parseDouble(outStationForm.getCommission().toString().trim()));
								} 

									cheDepoObject.setCreditACNo(Integer.parseInt(outStationForm.getCrAccNum().toString().trim()));
									cheDepoObject.setCreditACType(outStationForm.getCrAccTyp());
									cheDepoObject.setDocSource(1);
									cheDepoObject.setDocDestination(9999);
									cheDepoObject.setChqDDPO("");
									cheDepoObject.setClgType("O");
									cheDepoObject.setMultiCredit("F");
									cheDepoObject.setBounceInd("F");
									cheDepoObject.setDiscInd("F");
									cheDepoObject.setprev_ctrl_no(Long.parseLong(outStationForm.getCtrlNum().toString().trim()));
									cheDepoObject.setDe_date(delegate.getSysDate());
									cheDepoObject.setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
									cheDepoObject.setDeTml(uid);
									cheDepoObject.setDeUser(tml);
									cheDepoObject.setSysdate(ClearingDelegate.getSysDate());
									System.out.println("inside else");
									if(outStationForm.getBooleanFlag()==1)
									{	
										
										cheDepoObject.setVeTml(tml);
										cheDepoObject.setVeUser(uid);
										cheDepoObject.setControlNo(Long.parseLong(outStationForm.getCtrlNum().trim()));
										cheDepoObject.setTranAmt( Double.parseDouble(outStationForm.getAmount().trim()));
										cheDepoObject.setprev_ctrl_no( Long.parseLong(outStationForm.getPrevCtrlNum().trim()) );
									
										if (delegate.verifyOutStationCheque(cheDepoObject))
										{
											  outStationForm.setValidateFlag(" Sucessfully Verified" );
										}
										else 
										{
											 outStationForm.setValidateFlag( "Verification UnSuccessful" );
										}
									}
									else if((outStationForm.getBooleanFlag()==0) && Integer.parseInt(outStationForm.getPrevCtrlNum())>=1)
									{
										cheDepoObject.setControlNo(Integer.parseInt(outStationForm.getPrevCtrlNum()));
										long long_ctrl_no = delegate.storeChequeData(cheDepoObject);
										outStationForm.setValidateFlag("Control Number Updated Successfully "+ long_ctrl_no);
										outStationForm.setErrorFlag("1");
									}
									else if(outStationForm.getBooleanFlag()==0 && Integer.parseInt(outStationForm.getPrevCtrlNum())==0)
									{
										long long_ctrl_no = delegate.storeChequeData(cheDepoObject);
										outStationForm.setValidateFlag( "Note Down Control Number "+ long_ctrl_no );
										outStationForm.setErrorFlag("1");
										outStationForm.setPrevCtrlNum(Long.toString(long_ctrl_no));
									}
								}       
							           
						}
						else if(outStationForm.getFlag().equalsIgnoreCase("frmDelete"))
						{
							if (delegate.deleteChequeData(Integer.parseInt(outStationForm.getPrevCtrlNum()))>0)
							{
									outStationForm.setValidateFlag("Record Deleted Successfully");
									outStationForm.setErrorFlag("1");
							}
							else
								outStationForm.setValidateFlag("Error While Deleting");
						}
					
					if(custId>0)
					{
						CustomerMasterObject cust=delegate.getCustomerDetail(custId);
						httpServletRequest.setAttribute("personalInfo",cust);
					}	
					module = ClearingDelegate.getInstance().getMainModule(1);
					httpServletRequest.setAttribute("Main_Module_code",module);
					httpServletRequest.setAttribute("Reason", reason );
					httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
					httpServletRequest.setAttribute("flag",perPath);
					httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(outStationForm.getPageId()));	
					return actionMapping.findForward(ResultHelp.getSuccess());
					
				}
			}	
			catch (Exception e)
			{
				e.printStackTrace();
				return actionMapping.findForward(ResultHelp.getSuccess());
			}
			
				
	}
		
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Bouncedletter"))
	{
		BouncedLetterForm bncltrForm=(BouncedLetterForm)actionForm;
		bncltrForm.setValidation("");
		httpServletRequest.setAttribute("clrdate", ClearingDelegate.getSysDate());
		module=delegate.getMainModules(4,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000");		
		module_debit=delegate.getMainModules(2,"1011000");
		setInwardstmtForm(httpServletRequest, path, delegate); 
		if(bncltrForm.getBut_value().equalsIgnoreCase("Show_Tables"))
		{
			ClearingObject clearObj=new ClearingObject();
			System.out.println("clearing dateee---"+bncltrForm.getClr_date());
			clearObj.setClgDate(bncltrForm.getClr_date());
			clearObj.setClgNo(Integer.parseInt(bncltrForm.getClr_no()));
			clearObj.setDocSource(Integer.parseInt(bncltrForm.getDest_bank()));
			clearObj.setToBounce("T");
			if(bncltrForm.getInoutward().equalsIgnoreCase("Inward")){
				clearObj.setClgType("I");
			}else{
				clearObj.setClgType("O");
			}
			System.out.println("values were the "+clearObj.getClgDate()+"-----"+ clearObj.getClgNo()+"----"+clearObj.getDocSource()+"------"+clearObj.getClgType());
			{
				System.out.println("before delegate");
				ClearingObject clear[]=delegate.getBounceCheque(clearObj);
				System.out.println("after delegate");
    			if(clear==null)
    			{
    				bncltrForm.setValidation("nobounce");
    				System.out.println("no Bounce Cheque Found");
    			}
    			else
    			{	
    				ArrayList bounced_list= new ArrayList();
    				Map map=null;
    				for(int i=0;i<clear.length;i++)
    				{
    			        map=new HashMap();
    			        map.put("id",""+(i+1));
    					map.put("name",String.valueOf(clear[i].getClgType()));
    					map.put("controlno",String.valueOf(clear[i].getControlNo()));
    					map.put("clrdate",clear[i].getClgDate()+"");
    					map.put("docsrc",clear[i].getDocSource()+"");	
    					map.put("docdest",clear[i].getDocDestination()+"");
    					map.put("ackno",clear[i].getAckNo()+"");
    					map.put("trnamt",clear[i].getTranAmt()+"");
    					
    					if (bncltrForm.getInoutward().equalsIgnoreCase("Inward"))
    					{
    						map.put("debactype",clear[i].getDebitACType()+"");
    						map.put("dbacno",clear[i].getDebitACNo()+"");
    						
    					} 
    					else 
    					{
    						map.put("debactype",clear[i].getCreditACType()+"");
    						map.put("dbacno",clear[i].getCreditACNo()+"");
    					}
    					
    					map.put("chqdpo",clear[i].getChqDDPO()+""); //getCompanyName()+"";						
    					map.put("qdpno",clear[i].getQdpNo()+"");  //getChqDDPO()+"";
    					map.put("qdpdte",clear[i].getQdpDate()+""); //getQdpNo()+"";
    					
    					map.put("bkcode",clear[i].getBankCode()+"");  //.getQdpDate()+"";
    					map.put("bnkname",clear[i].getBranchName()+"");
    					
    					map.put("cmpname",clear[i].getCompanyName()+"");//getPrevCtrlNo()+"";							
    					map.put("discind",clear[i].getDiscInd()+"");
    					bounced_list.add(map);
    				}
    				httpServletRequest.setAttribute("bounced_list",bounced_list);
    			
    				if(bncltrForm.getFlag().equalsIgnoreCase("File"))
        			{
        				try
        				{
        				   HSSFWorkbook wb = new HSSFWorkbook();
        				   HSSFSheet sheet = wb.createSheet("new sheet");
        				   HSSFRow row1=sheet.createRow((short)0);
        				   row1.createCell((short)1).setCellValue("Control No");
    					   row1.createCell((short)2).setCellValue("Clg Date");
    					   row1.createCell((short)3).setCellValue("Send To");
    					   row1.createCell((short)4).setCellValue("Source");
    					   row1.createCell((short)5).setCellValue("Ack No");
    					   row1.createCell((short)6).setCellValue("Amount");
    					   row1.createCell((short)7).setCellValue("Account Type");
    					   row1.createCell((short)8).setCellValue("Account No");
    					   row1.createCell((short)9).setCellValue("ChqDDPO");
    					   row1.createCell((short)10).setCellValue("Cheque No");
    					   row1.createCell((short)11).setCellValue("Cheque Date");
    					   row1.createCell((short)12).setCellValue("Bank Code");
    					   row1.createCell((short)13).setCellValue("Branch Name");
    				    
        				   
        				   for(int i=0;i<clear.length;i++)
        				   {
        					   HSSFRow row = sheet.createRow((short)(i+1));
        					   row.createCell((short)1).setCellValue(clear[i].getControlNo());
        					   row.createCell((short)2).setCellValue(clear[i].getClgDate());
        					   row.createCell((short)3).setCellValue(clear[i].getSendTo());
        					   row.createCell((short)4).setCellValue(clear[i].getDocSource());
        					   row.createCell((short)5).setCellValue(clear[i].getAckNo());
        					   row.createCell((short)6).setCellValue(clear[i].getDiscAmt());
        					   row.createCell((short)7).setCellValue(clear[i].getCreditACType());
        					   row.createCell((short)8).setCellValue(clear[i].getCreditACNo());
        					   row.createCell((short)9).setCellValue(clear[i].getChqDDPO());
        					   row.createCell((short)10).setCellValue(clear[i].getQdpNo());
        					   row.createCell((short)11).setCellValue(clear[i].getQdpDate());
        					   row.createCell((short)12).setCellValue(clear[i].getBankCode());
        					   row.createCell((short)13).setCellValue(clear[i].getBranchName());
        				    
        					   FileOutputStream fileOut = new FileOutputStream("c:\\BouncedLetter.xls");
        					   wb.write(fileOut);
        					   fileOut.close(); 
        				   }   	
        				 }
        				catch(Exception ex)
        				{       
        				}  
        				
        			}
    			}
    		}
		}
		setInwardstmtForm(httpServletRequest, path, delegate); 
		return actionMapping.findForward(ResultHelp.getSuccess());
	}	
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/BouncedletterLink"))
	{

		try
		{
			 System.out.println("*********Bounced Letter*************");
			 BouncedLetterForm bcltrForm =(BouncedLetterForm)actionForm;
			 httpServletRequest.setAttribute("clrdate", delegate.getSysDate());
			 
		     if(MenuNameReader.containsKeyScreen(bcltrForm.getPageId()))
				{
		    	 	path =MenuNameReader.getScreenProperties(bcltrForm.getPageId());
					httpServletRequest.setAttribute("pageId",path);
					setInwardstmtForm(httpServletRequest,path,delegate);
					
					return actionMapping.findForward(ResultHelp.getSuccess());

				}
		     else
		     	{
		    	 return actionMapping.findForward(ResultHelp.getError());
		     	}
			}
			catch (Exception e) 
			{		
			       e.printStackTrace(); 
		    	   return actionMapping.findForward(ResultHelp.getSuccess());
			}
	}

	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/MailingChargesMLink"))
	{
    	try
    	{
    		 System.out.println("=-=-???????????--=>");
    		
    		MailingChargesForm mailingChargesForm=(MailingChargesForm)actionForm;
    		mailingChargesForm.setError_message("");
    		if(MenuNameReader.containsKeyScreen(mailingChargesForm.getPageId()))
    		{

    			    			
    			ChequeDepositionObject[] array_chequedepositionobject=delegate.getChargeableCheques();
    			if(array_chequedepositionobject!=null)
    			{
    					httpServletRequest.setAttribute("ctrlNums",array_chequedepositionobject);
    			}
    			else
    			{
    					mailingChargesForm.setError_message("Records Not Found");
    			}
    			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(mailingChargesForm.getPageId()));
    			return actionMapping.findForward(ResultHelp.getSuccess());
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	 
    }

	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/MailingChargesLink"))
	{
    	try
    	{
    		MailingChargesForm mailingChargesForm=(MailingChargesForm)actionForm;
    		mailingChargesForm.setError_message("");
    		UserVerifier uv=new UserVerifier();
    		
    		if(MenuNameReader.containsKeyScreen(mailingChargesForm.getPageId()))
    		{
    			ChequeDepositionObject[] array_chequedepositionobject=delegate.getChargeableCheques();
    			if(array_chequedepositionobject!=null)
    			{
    				if(mailingChargesForm.getForm_flag().equalsIgnoreCase("frmButton"))
    				{	
    					System.out.println("inside submit");
    					String[] cbox=httpServletRequest.getParameterValues("chkBox");
    					if(cbox!=null)
    					{
    						System.out.println("vakl of chk----"+cbox.length);
    						ChequeDepositionObject co[] = new ChequeDepositionObject[cbox.length];
    						int i=0;
    						for(int k = 0; k<cbox.length; k++)
    						{
    							co[i] = array_chequedepositionobject[k];
    							co[i].setDeTml(uv.getUserTml());
    							co[i].setDeUser(uv.getUserId());
    							i++;
    						}
    						try 
    						{
    				    		delegate.mailingCharge(co);
    				    		mailingChargesForm.setValidateFlag("Mailing Charges Set Successfully");
    						}
    						catch (Exception ex) 
    						{
    							ex.printStackTrace();
    						}	
    					}
    					else
    					{
    						mailingChargesForm.setError_message("CheckBox Is Not Selected");
    						
    					}
    				}
    				
        					httpServletRequest.setAttribute("ctrlNums",array_chequedepositionobject);
        		}
        		else
        		{
        					mailingChargesForm.setError_message("Records Not Found");
        		}
    				httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(mailingChargesForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
    			
    		}
    		else
    			return actionMapping.findForward(ResultHelp.getError());
    		
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return actionMapping.findForward(ResultHelp.getSuccess());
    	}
    	
    }

	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Inwardstmt"))
	{
		System.out.println("****************Inward Statement*******************");
		InwardstmtForm Inwdstmtform=(InwardstmtForm)actionForm;
		Inwdstmtform.setValidation("");
		httpServletRequest.setAttribute("clrdate", delegate.getSysDate());
		System.out.println("inside view >>>>>>>>>>>>>>>0000000");
		 if(MenuNameReader.containsKeyScreen(Inwdstmtform.getPageId()))
			{
			 	if(Inwdstmtform.getBut_value().equalsIgnoreCase("View"))
			 	{
			 		System.out.println("inside view >>>>>>>>>>>>>>>");
			 		List inward_list= getNewQuery(Inwdstmtform,httpServletRequest);
			 		if(inward_list==null)
			 		{
			 			Inwdstmtform.setValidation("Records Not Found");
			 		}
			 		else
			 		{
			 			httpServletRequest.setAttribute("inward_list", inward_list);
			 		}
			 	
			 	}
			 	if(Inwdstmtform.getBut_value().equalsIgnoreCase("Store"))
			 	{
			 		System.out.println("inside Store >>>>>>>>>>>>>>>");
			 		array_clearingobject = delegate.getInwardStatement(Inwdstmtform.getClr_date(),Integer.parseInt(Inwdstmtform.getClr_no()),Integer.parseInt(Inwdstmtform.getBank_name()),string_qry);
					if(array_clearingobject==null)
			 		{
			 			Inwdstmtform.setValidation("Records Not Found To Store");
			 		}
			 		else
			 		{
			 			httpServletRequest.setAttribute("inward_list", array_clearingobject);
			 			try
			 			{
			 			   HSSFWorkbook wb = new HSSFWorkbook();
			 			   HSSFSheet sheet = wb.createSheet("new sheet");
			 			   
			 			  
			 			   for(int i=0;i<array_clearingobject.length;i++)
			 			   {   	
			 			   
			 				   HSSFRow row = sheet.createRow((short)(i+1));
			 			   
			 				   row.createCell((short)1).setCellValue(String.valueOf(array_clearingobject[i].getCtrlNo()));
			 				   row.createCell((short)2).setCellValue(array_clearingobject[i].getClgType());
			 				   row.createCell((short)3).setCellValue(array_clearingobject[i].getDocBs());
			 				   row.createCell((short)4).setCellValue(array_clearingobject[i].getAckNo());
			 				   row.createCell((short)5).setCellValue(array_clearingobject[i].getNoOfDocs());
			 				   row.createCell((short)6).setCellValue(array_clearingobject[i].getTranAmt());
			 				   row.createCell((short)7).setCellValue(array_clearingobject[i].getSourceName());
			 				   row.createCell((short)8).setCellValue(array_clearingobject[i].getDestName());
			 				   row.createCell((short)9).setCellValue(array_clearingobject[i].getChqDDPO());
			 				   row.createCell((short)10).setCellValue(array_clearingobject[i].getQdpNo());
			 				   row.createCell((short)11).setCellValue(array_clearingobject[i].getQdpDate());
			 				   row.createCell((short)12).setCellValue(array_clearingobject[i].getCompanyName());
			 				   row.createCell((short)13).setCellValue(array_clearingobject[i].getPayeeName());
			 				   row.createCell((short)14).setCellValue(array_clearingobject[i].getBankName());
			 				   row.createCell((short)15).setCellValue(array_clearingobject[i].getPrevCtrlNo());
			 				   row.createCell((short)16).setCellValue(array_clearingobject[i].getDeUser());
			 				   row.createCell((short)17).setCellValue(array_clearingobject[i].getVeUser());
			 				   
			 			  } 
			 			  
			 			   FileOutputStream fileOut = new FileOutputStream("c:\\InwardStatement.xls");
			 			   wb.write(fileOut);
			 			   fileOut.close();       
			 			 }
			 			catch ( Exception ex )
			 			{   
			 				ex.printStackTrace();
			 			}      
			 			        
			 		}
			 	
			 	}
			 	else if(Inwdstmtform.getBut_value().equalsIgnoreCase("print"))
			 	{
			 		
			 		
			 	}
			 	
			 	httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(Inwdstmtform.getPageId()));
			 	setInwardstmtForm(httpServletRequest,path,delegate);
			 	return actionMapping.findForward(ResultHelp.getSuccess());
			}
		 else
		 {
			 return actionMapping.findForward(ResultHelp.getError());
		 }
	
	}
	
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Outwardstmtlink"))
	{
		try
		{
			 System.out.println("*********OutwardStatement*************");
			 OutwardstmtForm outwardstmtForm=(OutwardstmtForm)actionForm;
			 httpServletRequest.setAttribute("clrdate",delegate.getSysDate());
			 
		     if(MenuNameReader.containsKeyScreen(outwardstmtForm.getPageId()))
			 {
		    	 	path =MenuNameReader.getScreenProperties(outwardstmtForm.getPageId());
					httpServletRequest.setAttribute("pageId",path);
					setInwardstmtForm(httpServletRequest,path,delegate);
					return actionMapping.findForward(ResultHelp.getSuccess());
			}
		    else
		    {
		    	 return actionMapping.findForward(ResultHelp.getError());
		    }
		}
		catch (Exception e) 
		{		
			       e.printStackTrace(); 
		    	   return actionMapping.findForward(ResultHelp.getSuccess());
		}
		      
	}
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Outwardstmt"))
	{
		OutwardstmtForm outwardstmtForm=(OutwardstmtForm)actionForm;
		outwardstmtForm.setValidation("");
		if(MenuNameReader.containsKeyScreen(outwardstmtForm.getPageId()))
		{
			if(outwardstmtForm.getBut_value().equalsIgnoreCase("View"))
			{	
				System.out.println("inside view");
				if(outwardstmtForm.getReports().equalsIgnoreCase("Clg Statements"))//Clg Statements
				{
						System.out.println("inside clg stmt");
						array_clearingobject=delegate.getOutwardStatement(outwardstmtForm.getClr_date(),Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
						System.out.println("after clg stmt");
						if(array_clearingobject!=null)
						{
						httpServletRequest.setAttribute("clgstatement",array_clearingobject);
						}
						else
						{
							outwardstmtForm.setValidation("Records Not Found");
						}
				}		
				else if(outwardstmtForm.getReports().equalsIgnoreCase("Enclosure Slips"))
				{
					System.out.println("inside enclosure slips");
					array_clearingobject=delegate.getOutwardSlips(outwardstmtForm.getClr_date(),Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
					if(array_clearingobject!=null)
					{
						httpServletRequest.setAttribute("enclosureslips",array_clearingobject);
					}
					else
					{
						outwardstmtForm.setValidation("Records Not Found");
					}
				}
				else
				{
					System.out.println("inside else");
					n=2;
					array_clearingobject=delegate.getOutwardSummary(outwardstmtForm.getClr_date(),n,Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
					if(array_clearingobject!=null)
					{
						httpServletRequest.setAttribute("clgsummery",array_clearingobject);
					}
					else
					{
						outwardstmtForm.setValidation("Records Not Found");
					}
				}
			}
		setInwardstmtForm(httpServletRequest,path,delegate);
		return actionMapping.findForward(ResultHelp.getSuccess());
		}
	else
		{
			return actionMapping.findForward(ResultHelp.getError());
		}
	}
	
	
	
/*	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Outwardstmt"))
	{
		OutwardstmtForm outwardstmtForm=(OutwardstmtForm)actionForm;
		
		
		setInwardstmtForm(httpServletRequest,path,delegate);
		if(outwardstmtForm.getBut_value().equalsIgnoreCase("View"))
		{
		if(outwardstmtForm.getReports().equalsIgnoreCase("Clg Statements"))//Clg Statements
		{
			array_clearingobject=delegate.getOutwardStatement(outwardstmtForm.getClr_date(),Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
			httpServletRequest.setAttribute("clgstatement",array_clearingobject);
			setOutwardstmtList(delegate, outwardstmtForm,httpServletRequest);
			/*List clg_list=new ArrayList();
			Map map=null;
			setOutwardstmtList(delegate, outwardstmtForm,httpServletRequest);
			for(int i=0;i<array_clearingobject.length;i++){
				
				map=new TreeMap();
			    map.put("srlno",String.valueOf(i+1));
			    map.put("ctrlno",String.valueOf(array_clearingobject[i].getControlNo()));
			    map.put("trntype",array_clearingobject[i].getTranType());
			    map.put("docbs",array_clearingobject[i].getDocBs());
			    map.put("ackno",String.valueOf(array_clearingobject[i].getAckNo()));
			    map.put("noofdoc",String.valueOf(array_clearingobject[i].getNoOfDocs()));
			    if(array_clearingobject[i].getDocBs().equalsIgnoreCase("S"))
			    	map.put("trnamt",String.valueOf(array_clearingobject[i].getTranAmt()));
			    else
			    	map.put("trnamt",String.valueOf(array_clearingobject[i].getDocTotal()));
			    
			    	map.put("brname",String.valueOf(array_clearingobject[i].getBranchName()+"/"+array_clearingobject[i].getRetNormally()));
			    	map.put("chqdpo",array_clearingobject[i].getChqDDPO());
			    	map.put("qdpno",String.valueOf(array_clearingobject[i].getQdpNo()));
			    	map.put("qdpdate",array_clearingobject[i].getQdpDate());
			    	map.put("cractype",array_clearingobject[i].getCreditACType()+" "+String.valueOf(array_clearingobject[i].getCreditACNo()));
			    	map.put("despind",array_clearingobject[i].getDespInd());
			    	map.put("payee",array_clearingobject[i].getPayeeName());
			    	map.put("trnmode",String.valueOf(array_clearingobject[i].getTranMode())+"  "+array_clearingobject[i].getTrfType());
			    	map.put("mlcredit",array_clearingobject[i].getMultiCredit());
			    	map.put("cmpname",array_clearingobject[i].getCompanyName());
			    	map.put("discamt",String.valueOf(array_clearingobject[i].getDiscAmt()));
			    	map.put("pocomm",String.valueOf(array_clearingobject[i].getPOCommission()));
			    	map.put("blank","");
			    	map.put("deuser",String.valueOf(array_clearingobject[i].getDeUser()));
			    	map.put("detml",String.valueOf(array_clearingobject[i].getDeTml()));
			    	map.put("detime",String.valueOf(array_clearingobject[i].getDeTime()));
			    	map.put("veuser",String.valueOf(array_clearingobject[i].getVeUser()));
			    	map.put("vetml",String.valueOf(array_clearingobject[i].getVeTml()));
			    	map.put("vetime",String.valueOf(array_clearingobject[i].getVeTime()));
			    	
			    	clg_list.add(map);
			    	httpServletRequest.setAttribute("clg_list", clg_list);
			    	
			}
			setInwardstmtForm(httpServletRequest,path,delegate);
			
			 
		}
		else if(outwardstmtForm.getReports().equalsIgnoreCase("Enclosure Slips"))
		{
			array_clearingobject=delegate.getOutwardSlips(outwardstmtForm.getClr_date(),Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
			httpServletRequest.setAttribute("enclosureslips",array_clearingobject);
			setOutwardstmtList(delegate, outwardstmtForm,httpServletRequest);
        }
		else
		{
			
			
			n=2;
			array_clearingobject=delegate.getOutwardSummary(outwardstmtForm.getClr_date(),n,Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
			httpServletRequest.setAttribute("clgsummery",array_clearingobject);
			setOutwardstmtList(delegate, outwardstmtForm,httpServletRequest);
			/*List clgsummery_list=new ArrayList();
			Map map=null;
			for(int i=0;i<array_clearingobject.length;i++){
			map=new TreeMap();
			map.put("srlno",String.valueOf(i+1));
			map.put("bkcode",String.valueOf(array_clearingobject[i].getBankCode()));
			map.put("brname",array_clearingobject[i].getBranchName());
			map.put("noofdoc",String.valueOf(array_clearingobject[i].getNoOfDocs()));
			map.put("discamt",String.valueOf(array_clearingobject[i].getDiscAmt()));
			map.put("ctrlno",String.valueOf(array_clearingobject[i].getControlNo()));
			map.put("trnamt",String.valueOf(array_clearingobject[i].getTranAmt()));
			map.put("qdpno",String.valueOf(array_clearingobject[i].getQdpNo()));
			map.put("pocomm",String.valueOf(array_clearingobject[i].getPOCommission()));
			map.put("ackno",String.valueOf(array_clearingobject[i].getAckNo()));
			map.put("fineamt",String.valueOf(array_clearingobject[i].getFineAmt()));
		    clgsummery_list.add(map);
		   
		}
			
		}
		if(array_clearingobject == null)
		{
			outwardstmtForm.setValidation("norows");
			System.out.println("No Rows Found");
		}
		}
		setInwardstmtForm(httpServletRequest,path,delegate);
		return actionMapping.findForward(ResultHelp.getSuccess());
	}   */
	
	
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Bouncedreglink"))
	{
		try
		{
			 System.out.println("*********OutwardStatement*************");
			 BouncedregForm bouncedregForm=(BouncedregForm)actionForm;
			 httpServletRequest.setAttribute("clrdate", delegate.getSysDate());
			 
		     if(MenuNameReader.containsKeyScreen(bouncedregForm.getPageId()))
				{
		    	 	path =MenuNameReader.getScreenProperties(bouncedregForm.getPageId());
					httpServletRequest.setAttribute("pageId",path);
					setInwardstmtForm(httpServletRequest,path,delegate);
					System.out.println("before return");
					return actionMapping.findForward(ResultHelp.getSuccess());

				}
		     else
		     	{
		    	 return actionMapping.findForward(ResultHelp.getError());
		     	}
			}
			catch (Exception e) 
			{		
			       e.printStackTrace(); 
		    	   return actionMapping.findForward(ResultHelp.getSuccess());
			}
	}
	else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Bouncedreg"))
	{
		BouncedregForm bouncedregForm=(BouncedregForm)actionForm;
		if(MenuNameReader.containsKeyScreen(bouncedregForm.getPageId()))
		{
			String string_fromdate=null;
			String string_todate=null;
			ChequeAckObject[] chequeackobject=null;
	        if(bouncedregForm.getFrom_date().length()>0 && bouncedregForm.getTo_date().length()>0)
			{
			    try
				{
					string_fromdate=Validations.checkDate(bouncedregForm.getFrom_date());
					string_todate=Validations.checkDate(bouncedregForm.getTo_date());
					chequeackobject=delegate.getBouncedCheques(string_fromdate,string_todate,string_qry);
					
					if(chequeackobject != null)
					{
						httpServletRequest.setAttribute("ChequeDet",chequeackobject);
						
						
						 if(bouncedregForm.getFlag().equalsIgnoreCase("Store"))
					        {
					        	try
					        	{
					        	   HSSFWorkbook wb = new HSSFWorkbook();
					        	   HSSFSheet sheet = wb.createSheet("new sheet");
					        	   HSSFRow row1 = sheet.createRow((short)1);
					        	   row1.createCell((short)1).setCellValue("Control No");
					        	   row1.createCell((short)2).setCellValue("Tran Type");
					        	   row1.createCell((short)3).setCellValue("ChequeDate");
					        	   row1.createCell((short)4).setCellValue("ChequeAmt");
					        	   row1.createCell((short)5).setCellValue("Source");
					        	   row1.createCell((short)6).setCellValue("Destination");
					        	   row1.createCell((short)7).setCellValue("A/c No");
					        	   row1.createCell((short)8).setCellValue("BankName");
					        	   row1.createCell((short)9).setCellValue("Ack No");
					        	   
					        	   for(int i=0;i<chequeackobject.length;i++)
					        	   {	   
					        		   HSSFRow row = sheet.createRow((short)(i+1));
					        	       row.createCell((short)1).setCellValue(chequeackobject[i].getControlNo());
					        	       row.createCell((short)2).setCellValue(chequeackobject[i].getTranType());
					        	       row.createCell((short)3).setCellValue(chequeackobject[i].getDocBs());
					        	       row.createCell((short)4).setCellValue(chequeackobject[i].getDocTotal());
					        	       row.createCell((short)5).setCellValue(chequeackobject[i].getSourceName());
					        	       row.createCell((short)6).setCellValue(chequeackobject[i].getDestName());
					        	       row.createCell((short)7).setCellValue(chequeackobject[i].getCreditACNo());
					        	       row.createCell((short)8).setCellValue(chequeackobject[i].getBankName());
					        	       row.createCell((short)9).setCellValue(chequeackobject[i].getAckNo());
					        	  
					        	   }
					        	   FileOutputStream fileOut = new FileOutputStream("c:\\BouncedReg.xls");
					        	   wb.write(fileOut);
					        	   fileOut.close(); 
					        	   bouncedregForm.setValidateFlag("File Stored Successfully");
					        	 }
					        	 catch(Exception ex)
					        	{
					        		
					        	}  
					        	
					        }
						
					}
					else
					{
						bouncedregForm.setValidateFlag("No Rows Found");
					}
				}catch(Exception e){
				    e.printStackTrace();
				}
			}
			else
			{	
				bouncedregForm.setValidateFlag("Enter Valid Date--DD/MM/YYYY");
			}
	        
	       
			httpServletRequest.setAttribute("clrdate", delegate.getSysDate());	
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(bouncedregForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
	
		}
	}	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ChequeWithdrawalMlink"))
{
	try
	{
		ChequeWithdrawalForm chequeWithdrawalForm=(ChequeWithdrawalForm)actionForm;
		
		if(MenuNameReader.containsKeyScreen(chequeWithdrawalForm.getPageId())){

			module=ClearingDelegate.getInstance().getMainModule(4);
			httpServletRequest.setAttribute("Main_Module_code", module);
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(chequeWithdrawalForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
	
}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ChequeWithdrawalLink"))
{
	try
	{
		ChequeWithdrawalForm chequeWithdrawalForm=(ChequeWithdrawalForm)actionForm;
		chequeWithdrawalForm.setValidateFlag("");
		chequeWithdrawalForm.setErrorFlag("0");
		if(MenuNameReader.containsKeyScreen(chequeWithdrawalForm.getPageId()))
		{

			if(chequeWithdrawalForm.getFlag().equalsIgnoreCase("fromControlNum"))
			{
				
					ChequeDepositionObject[] chequedepositionobject = delegate.retrieveChequeDetails(Integer.parseInt(chequeWithdrawalForm.getControlNum()),1);
					System.out.println("after retrieve");
				if(chequedepositionobject!=null)
				{	
						if(chequedepositionobject[0].getDiscInd() != null && chequedepositionobject[0].getDiscInd().equalsIgnoreCase("F"))
						{
							if(chequedepositionobject[0].getPost_ind().equalsIgnoreCase("F")) 
							{
									chequeWithdrawalForm.setMICRCode( chequedepositionobject[0].getBankCode().substring(0,3) + "-"+ chequedepositionobject[0].getBankCode().substring(3,6)+"-"+chequedepositionobject[0].getBankCode().substring(6,9));
									chequeWithdrawalForm.setPayee(chequedepositionobject[0].getPayeeName());
									chequeWithdrawalForm.setBranchName(chequedepositionobject[0].getBranchName());
									chequeWithdrawalForm.setChqdate(chequedepositionobject[0].getQdpDate());
									chequeWithdrawalForm.setChqno( new Integer( chequedepositionobject[0].getQdpNo()).toString());
									
									if(chequedepositionobject[0].getChqDDPO()!=null) 
									{
										if(chequedepositionobject[0].getChqDDPO().equalsIgnoreCase("C"))
											chequeWithdrawalForm.setChqddpo("Cheque"); 
										else if ( chequedepositionobject[0].getChqDDPO().equalsIgnoreCase("P") )
											chequeWithdrawalForm.setChqddpo("PayOrder"); 
										else 
											chequeWithdrawalForm.setChqddpo("DD");
											
									}
									if(chequedepositionobject[0].getCreditACType()!=null)
									{
										chequeWithdrawalForm.setCreditAcType(chequedepositionobject[0].getCreditACType());
										chequeWithdrawalForm.setCreditAcNum( new Integer(chequedepositionobject[0].getCreditACNo()).toString());
													
									}
									
									if ( chequedepositionobject[0].getDoc_bs().equalsIgnoreCase("S"))
									{
									chequeWithdrawalForm.setAmount( new Double (chequedepositionobject[0].getTranAmt()).toString());
									}
									else if ( chequedepositionobject[0].getDoc_bs().equalsIgnoreCase("B") ) 
									{
									
										chequeWithdrawalForm.setAmount( new Double (chequedepositionobject[0].getFineAmt()).toString());
										
									}
									
							}
							else if(chequedepositionobject[0].getPost_ind().equalsIgnoreCase("T"))
							{
								chequeWithdrawalForm.setValidateFlag("Cheque Has Been Posted");
							}
						}
						else
						{
							chequeWithdrawalForm.setValidateFlag("Discounted Cheque");
						}
				}
				else
				{
					chequeWithdrawalForm.setValidateFlag("Records Not Found");
				}
						
			}
			else if(chequeWithdrawalForm.getFlag().equalsIgnoreCase("withdraw"))
			{
				try
				{	
					System.out.println("inside withdrawal");
					int i = delegate.chequeWithdrawal(Integer.parseInt(chequeWithdrawalForm.getControlNum()),tml, uid,ClearingDelegate.getSysDate());
					if(i==1)
					{
						chequeWithdrawalForm.setValidateFlag("Successfully Withdrawn");
						chequeWithdrawalForm.setErrorFlag("1");
					}
					else
					{
						chequeWithdrawalForm.setValidateFlag("Withdrawing UnSuccessfull");
					}	
				}  
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			module=ClearingDelegate.getInstance().getMainModule(4);
			httpServletRequest.setAttribute("Main_Module_code", module);
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(chequeWithdrawalForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
	
}

else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ReceivedECSMLink"))
{
	
	try
	{
		String path;
		RecievedECSForm recievedECSForm =(RecievedECSForm)actionForm;
		recievedECSForm.setValidateFlag("");
	if(MenuNameReader.containsKeyScreen(recievedECSForm.getPageId()))
		{	

		System.out.println("*************"+MenuNameReader.containsKeyScreen(recievedECSForm.getPageId()));
		Reason[] reason = delegate.getReasonDetails("1002001",0);
		httpServletRequest.setAttribute("Reason", reason);
		recievedECSForm.setControlNum("0");
		httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
		path=MenuNameReader.getScreenProperties(recievedECSForm.getPageId());
		httpServletRequest.setAttribute("Main_Module_code", delegate.getMainModules(4, "'1002000','1007000','1018000','1017000','1014000','1015000','1001000','1003000','1004000','1005000','1012000'"));
		httpServletRequest.setAttribute("pageId",path);
		return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
	                                                    
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ReceivedECSLink"))
{
	
	System.out.println("-------->");
	AckObject ackObject = null ;
	String path=null;
	
	module=delegate.getMainModules(4, "'1002000','1007000','1018000','1017000','1014000','1015000','1001000','1003000','1004000','1005000','1012000'");
	try
	{
		
		RecievedECSForm recievedECSForm=(RecievedECSForm)actionForm;
		recievedECSForm.setValidateFlag("");
		recievedECSForm.setErrorFlag("");
		System.out.println("*************"+MenuNameReader.containsKeyScreen(recievedECSForm.getPageId()));
		if(MenuNameReader.containsKeyScreen(recievedECSForm.getPageId()))
		{	
			double fine_amount=0.0;
			int custId=0;
			module = ClearingDelegate.getInstance().getMainModule(4);
			String actyp=null;
			int acno=0;
			
			if(recievedECSForm.getFlag().equalsIgnoreCase("frmCtrlNum"))
			{	System.out.println("inside ctrl num");
				if((recievedECSForm.getControlNum().trim())!=null)
				{	
						System.out.println("inside not null");
						ChequeDepositionObject chequedepositionobject = null;
						String ac_type = null;
						int ac_no =0;
						// calling Remote Method to get Control Number Value ........
						chequedepositionobject = delegate.getInwardData(Integer.parseInt(recievedECSForm.getControlNum()),2);
						if(chequedepositionobject != null) 
						{
							System.out.println(chequedepositionobject.getVeTml()+ " value of vetml");
							if(chequedepositionobject.getVeTml() == null)
							{
								if((recievedECSForm.getBooleanFlag())==1 && chequedepositionobject.getDeTml() == null)
								{
									recievedECSForm.setValidateFlag(" Acknowledgement Is Not Verified");
								}
								if (chequedepositionobject.getVeUser() != null && chequedepositionobject.getVeUser().equalsIgnoreCase("F") && (recievedECSForm.getBooleanFlag())==1)
								{
									
									recievedECSForm.setValidateFlag(" Acknowledgement Is Not Reconciled" );
									
								}
								
									if(chequedepositionobject.getClgType().equalsIgnoreCase("C"))
									{
										
										System.out.println("------- hdhddh" +chequedepositionobject.getCreditACType() );
										recievedECSForm.setCredebit("C");
										recievedECSForm.setCredit_account_type(chequedepositionobject.getCreditACType());
										ac_type = chequedepositionobject.getCreditACType();
										recievedECSForm.setAcNumber(Integer.toString(chequedepositionobject.getCreditACNo()));
										ac_no = chequedepositionobject.getCreditACNo();

									}
									else if(chequedepositionobject.getClgType().equalsIgnoreCase("D"))
									{
										recievedECSForm.setCredebit("D");
										recievedECSForm.setCredit_account_type(chequedepositionobject.getDebitACType());
										ac_type = chequedepositionobject.getDebitACType();
										recievedECSForm.setAcNumber(Integer.toString(chequedepositionobject.getDebitACNo()));
										ac_no = chequedepositionobject.getDebitACNo();
									}
									recievedECSForm.setAckNumber(Integer.toString(chequedepositionobject.getAckNo()));
									recievedECSForm.setAck_balance(Double.toString(chequedepositionobject.getActDiscAmt()));

									AccountObject accountobject = delegate.getAccount(ac_type,ac_no, ClearingDelegate.getSysDate());

									if (accountobject == null) 
									{
										array_int_default_reasons[0] = 9;
										recievedECSForm.setValidateFlag("Account Number Not Found");
										recievedECSForm.setAcNumber("");
										
									} 
									else if (accountobject.getAccStatus().equals("C")) 
									{
										array_int_default_reasons[1] = 10;
										recievedECSForm.setValidateFlag("Account Is Closed");
										recievedECSForm.setAcNumber("");
									} 
									else if (accountobject.getVerified() == null) 
									{
										recievedECSForm.setValidateFlag("Account Not Yet Verified");
										recievedECSForm.setAcNumber("");
									}
									else if (accountobject.getFreezeInd() != null && accountobject.getFreezeInd().equals("T"))
									{
										recievedECSForm.setValidateFlag("Account Is Freezed");
										recievedECSForm.setAcNumber("");
									}
									else if (accountobject.getDefaultInd() != null && accountobject.getDefaultInd().equals("T")) 
									{
										recievedECSForm.setValidateFlag("Defaulted Account");
										recievedECSForm.setAcNumber("");
									} 
									else 
									{
										System.out.println("balance amount ===="+accountobject.getAmount());
										custId=accountobject.getCid();
										recievedECSForm.setBalance(String.valueOf(accountobject.getAmount()));
									}
									
									
									System.out.println(chequedepositionobject.getDebitACNo()+"debit ac no ");
									System.out.println(chequedepositionobject.getDebitACType()+" debit ac no");

									System.out.println(chequedepositionobject.getCreditACNo()+ " credit ac no");
									System.out.println(chequedepositionobject.getCreditACType()+ " credi ac type");

									recievedECSForm.setClr_date(chequedepositionobject.getQdpDate());

									String cod =chequedepositionobject.getBankCode();
									recievedECSForm. setCityCode(cod.substring(0,3));
									recievedECSForm.setBankCode(cod.substring(3,6));
									recievedECSForm.setBranchCode(cod.substring(6,9));
									recievedECSForm.setBankName(chequedepositionobject.getBankName());
									recievedECSForm.setAmount(Double.toString(chequedepositionobject.getTranAmt()));

									recievedECSForm.setPayee( chequedepositionobject.getPayeeName());

									recievedECSForm.setItemSeqNum(Integer.toString(chequedepositionobject.getQdpNo()));
									System.out.println("after item seq ");
									if(chequedepositionobject.getBounceInd().equalsIgnoreCase("T"))
									{
										System.out.println("inside bounce fine");
										vector_all_reasons = chequedepositionobject.getReasonArray();
										recievedECSForm.setBounce(true);
										recievedECSForm.setBounceFine(String.valueOf(chequedepositionobject.getFineAmt()));
									}
								}
							else
								{
									recievedECSForm.setControlNum("0");
									recievedECSForm.setValidateFlag("Records Already Verified");
								}
							}
							else 
							{
								recievedECSForm.setControlNum("0");
								recievedECSForm.setValidateFlag("Records Not found");
							}
						
					}
					else
					{
						recievedECSForm.setControlNum("0");
					}
				
			}
			else if(recievedECSForm.getFlag().equalsIgnoreCase("ackNumber"))
			{
			
			System.out.println(recievedECSForm.getAckNumber()+"inside ackNumber===!!!!!!!!?"+recievedECSForm.getAckNumber());
			ackObject=delegate.getAcknowledgeAmount(Integer.parseInt(recievedECSForm.getAckNumber().trim()),recievedECSForm.getCredebit());
			System.out.println("after delegate ack amount");
			if(ackObject==null)
			{
					recievedECSForm.setValidateFlag("Acknowledgement Not Found");
					recievedECSForm.setAckNumber("");
			}
			else if(ackObject.getReconciled().trim().equalsIgnoreCase("T"))
			{
					recievedECSForm.setValidateFlag("Acknowledgement Already Reconceiled");
					recievedECSForm.setAckNumber("");
			}
			else
				{
					String remin_amt =String.valueOf(ackObject.getAckEntered()- ackObject.getTotal());
					recievedECSForm.setAck_balance(remin_amt);
					recievedECSForm.setAck_source(ackObject.getBankName());
					System.out.println(remin_amt+"-------"+ackObject.getBankName());	
					if(Double.parseDouble(remin_amt) <= 0.0)
					{
						recievedECSForm.setValidateFlag("Amount Is Too Less");
						recievedECSForm.setAckNumber("");
					}
				}
			
		}
	
		else if ( recievedECSForm.getFlag().equalsIgnoreCase("Update"))
		{
			
			long long_ctrl_no = 0;
			ChequeDepositionObject chq_deposition_obj = new ChequeDepositionObject();
			
			if (recievedECSForm.getControlNum().toString().trim().length() >= 1)
			{
				chq_deposition_obj.setControlNo(Long.parseLong(recievedECSForm.getControlNum().toString().trim()));
				System.out.println( chq_deposition_obj.getControlNo());
			}
			else
			{
				recievedECSForm.setValidateFlag("Enter Valid Control Number");
			}
			if (recievedECSForm.getAckNumber().toString().trim().length() > 0)
			{
				
				chq_deposition_obj.setAckNo(Integer.parseInt(recievedECSForm.getAckNumber().toString().trim()));
				System.out.println( chq_deposition_obj.getAckNo());
			}
			else
			{
				recievedECSForm.setValidateFlag("Enter Valid Acknowledgement Number");
			}
			if (recievedECSForm.getAcNumber().toString().trim().length() > 0)
			{
				
				if (recievedECSForm.getCredebit().toString().equalsIgnoreCase("C")) 
				{ 
					System.out.println("inside C");
					System.out.println( recievedECSForm.getCredit_account_type()+ "selected module code");
					chq_deposition_obj.setCreditACType(recievedECSForm.getCredit_account_type());
					chq_deposition_obj.setCreditACNo(Integer.parseInt(recievedECSForm.getAcNumber().toString().trim()));
					System.out.println( chq_deposition_obj.getCreditACNo());
					System.out.println( chq_deposition_obj.getCreditACType());
				}
				else 
				{
					System.out.println("inside else after CC");
					chq_deposition_obj.setDebitACType(recievedECSForm.getCredit_account_type());
					chq_deposition_obj.setDebitACNo(Integer.parseInt(recievedECSForm.getAcNumber().toString().trim()));
					
					System.out.println( recievedECSForm.getCredit_account_type()+ "selected module code");
					System.out.println( chq_deposition_obj.getDebitACNo());
					System.out.println( chq_deposition_obj.getDebitACType());
				}
				
			}
			else
			{
				recievedECSForm.setValidateFlag("Enter Valid Account Number");
			}
			if(recievedECSForm.getBankCode().equalsIgnoreCase("000"))
			{
				//return txt_bank_name;
				System.out.println("inside bank code");
				recievedECSForm.setValidateFlag("Enter Valid Bank code");
			}
			else
			{	
				System.out.println("inside else Bank Name");
				System.out.println("-------------------recievedECSForm.getBankName().substring(0,3)----------"+recievedECSForm.getBankCode());
				chq_deposition_obj.setBankCode(recievedECSForm.getCityCode()+recievedECSForm.getBankCode()+recievedECSForm.getBranchCode());
			}
			if (recievedECSForm.getAmount().toString().trim().length()>0)
			{
				chq_deposition_obj.setTranAmt( Double.parseDouble(recievedECSForm.getAmount().toString().trim()));
				System.out.println( chq_deposition_obj.getTranAmt());
			} 
			else
			{
				recievedECSForm.setValidateFlag("Enter Valid Amount");
			}
			
			if(recievedECSForm.getItemSeqNum().toString().trim().length() > 0)
			{
				
				chq_deposition_obj.setQdpNo(Integer.parseInt(recievedECSForm.getItemSeqNum().toString().trim()));
				System.out.println(chq_deposition_obj.getQdpNo());
			
			}
			else
			{
				recievedECSForm.setValidateFlag("Enter Valid Item Sequence Number");
			}
			
			chq_deposition_obj.setDocSource(9999);
			chq_deposition_obj.setDocDestination(1);
			chq_deposition_obj.setQdpDate(recievedECSForm.getClr_date().toString().trim());
			chq_deposition_obj.setPayeeName(recievedECSForm.getPayee().toString());
		
			if(recievedECSForm.isBounce())
			{
				System.out.println("inside bounce update");
				String[] chkbox=httpServletRequest.getParameterValues("chkBoxVal");			
				String[] reasonCode=httpServletRequest.getParameterValues("txtReasonCode");
				String[] reasonFine=httpServletRequest.getParameterValues("txtBounceFine");
				if(chkbox!=null)
				{
					System.out.println("inside not null");
					for(int k=0;k<chkbox.length;k++)
					{
						int x=Integer.parseInt(chkbox[k]);
						vector_all_reasons.add(reasonCode[x]);
						fine_amount+=Double.parseDouble(reasonFine[x]);
					}
					System.out.println("bounce val==inside action=>");
					chq_deposition_obj.setBounceInd("T");
					chq_deposition_obj.setReasonArray(vector_all_reasons);
					chq_deposition_obj.setFineAmt(fine_amount);
					System.out.println(fine_amount+ "fine Amount");
				}
				else
				{	
					System.out.println("inside null");
					recievedECSForm.setValidateFlag("CheckBox Is Not Selected");
				}
			}
			else
			{
				chq_deposition_obj.setBounceInd("F");
			}
			chq_deposition_obj.setChqDDPO("E");
			chq_deposition_obj.setClgType(recievedECSForm.getCredebit().toString());
			chq_deposition_obj.setMultiCredit("F");
			chq_deposition_obj.setDiscInd("F");
			System.out.println( chq_deposition_obj.getQdpDate());
			System.out.println( chq_deposition_obj.getPayeeName());
			chq_deposition_obj.setDe_date(ClearingDelegate.getSysDate());
			chq_deposition_obj.setDeTime(ClearingDelegate.getSysDate()+" "+delegate.getSysTime());
			chq_deposition_obj.setDeTml(tml);
			chq_deposition_obj.setDeUser(uid);

			try
			{	
				if(recievedECSForm.getControlNum()!=null && Integer.parseInt(recievedECSForm.getControlNum())!=0)
				{
					System.out.println("inside after confirm");
			        long_ctrl_no = delegate.storeInwardData(chq_deposition_obj);
			        recievedECSForm.setValidateFlag("Control Number "+ long_ctrl_no + " Updated Sucessfully " );
			        recievedECSForm.setErrorFlag("1");
				}
			}
			catch(Exception rmi)
			{
				rmi.printStackTrace();
			}
				
		
		}
		else if ( recievedECSForm.getFlag().equalsIgnoreCase("frmMICRCode"))
		{
				
				if (!((recievedECSForm.getBankCode())).equalsIgnoreCase("000"))
				{
					
					String code = ((recievedECSForm.getCityCode()+recievedECSForm.getBankCode()+recievedECSForm.getBranchCode()));
					
					HashMap map_result   = delegate.getCityBankBranchDetail((code));					
					
					if(map_result.containsKey(code.substring(0,3))) 
					{
						System.out.println("city code==>"+map_result.get(code.substring(0, 3)));
						recievedECSForm.setBankName((String)(map_result.get(code.substring(0, 3))));
					}
					
					if(map_result.containsKey(code.substring(3,6)))  
					{
						
						recievedECSForm.setBankName((String)(map_result.get(code.substring(3,6))));
					}
					
					if ( map_result.containsKey(code.substring(6,9)))  
					{
						
						//form.setBranchname((String)(map_result.get(  code.substring(6, 9))));
					}
				}
				else
				{
					recievedECSForm.setValidateFlag("Enter Valid Bank Code");
				}
				
				
		}
		else if(recievedECSForm.getFlag().equalsIgnoreCase("submit"))
		{	
			System.out.println("inside submit-----");
			long long_ctrl_no=5;
			ChequeDepositionObject chequeDepositionObject =new ChequeDepositionObject();
			
			System.out.println(recievedECSForm.getControlNum()+"<-***->"+recievedECSForm.getAckNumber()+"<-->"+recievedECSForm.getCredebit()+"<-->"+recievedECSForm.getAcType()+"<-->"+recievedECSForm.getAcNumber()+"<-->"+recievedECSForm.getBankCode()+"<-->"+recievedECSForm.getBankName()+"<-->"+recievedECSForm.getAmount()+"<-->"+recievedECSForm.getItemSeqNum()+"<-->"+recievedECSForm.getClr_date()+"<-->"+recievedECSForm.getPayee()+"<-->");
			
			chequeDepositionObject.setControlNo(Integer.parseInt(recievedECSForm.getControlNum()));
			chequeDepositionObject.setAckNo( Integer.parseInt(recievedECSForm.getAckNumber()));
			if(recievedECSForm.getCredebit().equalsIgnoreCase("C"))
			{
				chequeDepositionObject.setCreditACType(recievedECSForm.getCredit_account_type());
				chequeDepositionObject.setCreditACNo(Integer.parseInt(recievedECSForm.getAcNumber()));
			}
			if(recievedECSForm.getCredebit().equalsIgnoreCase("D"))
			{
				chequeDepositionObject.setDebitACType(recievedECSForm.getCredit_account_type());
				chequeDepositionObject.setDebitACNo(Integer.parseInt(recievedECSForm.getAcNumber()));
			}
			
			chequeDepositionObject.setBankCode(recievedECSForm.getCityCode()+recievedECSForm.getBankCode()+recievedECSForm.getBranchCode());
			chequeDepositionObject.setBankName(recievedECSForm.getBankName());
			chequeDepositionObject.setTranAmt(Double.parseDouble(recievedECSForm.getAmount()));
			chequeDepositionObject.setQdpNo(Integer.parseInt(recievedECSForm.getItemSeqNum()));
			chequeDepositionObject.setDocSource(9999);
			chequeDepositionObject.setDocDestination(1);
			chequeDepositionObject.setQdpDate(recievedECSForm.getClr_date());
			chequeDepositionObject.setPayeeName(recievedECSForm.getPayee());
			
			if(recievedECSForm.isBounce())
			{	
				System.out.println("inside bounce submit");
				String[] cbox=httpServletRequest.getParameterValues("chkBoxVal");
				String[] reasonCode=httpServletRequest.getParameterValues("txtReasonCode");
				String[] reasonFine=httpServletRequest.getParameterValues("txtBounceFine");
				if(cbox!=null)
				{
					for(int k=0;k<cbox.length;k++)
					{
						int x=Integer.parseInt(cbox[k]);
						vector_all_reasons.add(reasonCode[x]);
						fine_amount+=Double.parseDouble(reasonFine[x]);
					}
					chequeDepositionObject.setBounceInd("T");
					chequeDepositionObject.setReasonArray(vector_all_reasons);
					chequeDepositionObject.setFineAmt(fine_amount);
					System.out.println(fine_amount+ "fine Amount");
				}
				else
				{
					recievedECSForm.setValidateFlag("CheckBox Is Not Selected");
				}
			}
			else 
				chequeDepositionObject.setBounceInd("F");
			
			
			chequeDepositionObject.setChqDDPO("E");
			chequeDepositionObject.setClgType(recievedECSForm.getCredebit());
			chequeDepositionObject.setMultiCredit("F");
			chequeDepositionObject.setDiscInd("F");
			chequeDepositionObject.setDe_date(delegate.getSysDate());
			chequeDepositionObject.setDeTime(delegate.getSysDate()+" "+delegate.getSysDate());
			chequeDepositionObject.setDeTml(tml);
			chequeDepositionObject.setDeUser(uid);
			
			if((recievedECSForm.getBooleanFlag())==1)
			{
				System.out.println("Inside Verify");
				chequeDepositionObject.setVe_date(ClearingDelegate.getSysDate());
				chequeDepositionObject.setVe_datetime(ClearingDelegate.getSysDate());
				chequeDepositionObject.setVeTml(tml);
				chequeDepositionObject.setVeUser(uid);
				
				if (recievedECSForm.getCredebit().equalsIgnoreCase("D"))
				{
					System.out.println("inside debit verifyyy---");
					long_ctrl_no=delegate.verifyDebitECS(chequeDepositionObject);
				}
				else if(recievedECSForm.getCredebit().equalsIgnoreCase("C"))
				{
					System.out.println(" ya .....................................");
					long_ctrl_no=delegate.verifyCreditECS(chequeDepositionObject);
				}
				if(long_ctrl_no==0)
				{
					recievedECSForm.setValidateFlag("Successfully Verified");
					recievedECSForm.setErrorFlag("1");
				}
				else
				{
					recievedECSForm.setValidateFlag("Verification Failed");
				}
			}
			else if(recievedECSForm.getBooleanFlag()==0)
			{
				long_ctrl_no=delegate.storeChequeData(chequeDepositionObject);
				System.out.println("---------->>>>"+long_ctrl_no);
				if(long_ctrl_no>0.0)
				{
					recievedECSForm.setValidateFlag("Note Down Control Number "+long_ctrl_no);
					recievedECSForm.setErrorFlag("1");
				}
			}
		}
		else if(recievedECSForm.getFlag().equalsIgnoreCase("frmAmt"))
		{
			if(Double.parseDouble(recievedECSForm.getAmount())> Double.parseDouble(recievedECSForm.getAck_balance()))
			{
				recievedECSForm.setValidateFlag("Amount Entered Is More Than Acknowledgement Amount");
				recievedECSForm.setAmount("0.0");
			}
		}	
		else if(recievedECSForm.getFlag().equalsIgnoreCase("frmAccNum"))
		{	
			if(recievedECSForm.getCredit_account_type().equalsIgnoreCase("select"))
			{
				recievedECSForm.setValidateFlag("Select Proper Account Type");
			}
			else
			{
					AccountObject accountobject = delegate.getAccount(recievedECSForm.getCredit_account_type(),Integer.parseInt(recievedECSForm.getAcNumber()), ClearingDelegate.getSysDate());

				if(accountobject==null) 
				{
					array_int_default_reasons[0] = 9;
					recievedECSForm.setValidateFlag("Account Number Not Found");
					recievedECSForm.setAcNumber("0");
					recievedECSForm.setBalance("0.0");
				} 
				else if (accountobject.getAccStatus().equals("C")) 
				{
					array_int_default_reasons[1] = 10;
					recievedECSForm.setValidateFlag("Account Is Closed");
				} 
				else if (accountobject.getVerified() == null) 
				{
					recievedECSForm.setValidateFlag("Account Not Yet Verified");
				}
				else if (accountobject.getFreezeInd() != null && accountobject.getFreezeInd().equals("T"))
				{
					recievedECSForm.setValidateFlag("Account Is Freezed");
				}
				else if (accountobject.getDefaultInd() != null && accountobject.getDefaultInd().equals("T")) 
				{
					recievedECSForm.setValidateFlag("Defaulted Account");
				} 
				else 
				{
					System.out.println("balance amount ===="+accountobject.getAmount());
					custId=accountobject.getCid();
					actyp=recievedECSForm.getCredit_account_type();
					acno=Integer.parseInt(recievedECSForm.getAcNumber());
					recievedECSForm.setBalance(String.valueOf(accountobject.getAmount()));
				}
			
			}
		}
			if(recievedECSForm.getCredit_account_type()!=null && recievedECSForm.getAcNumber().length()!=0)
			{
				if(Integer.parseInt(recievedECSForm.getAcNumber())!=0)
				{
					System.out.println("inside personal details");
					actyp=recievedECSForm.getCredit_account_type();
					acno=Integer.parseInt(recievedECSForm.getAcNumber());
					accountobject_crd = delegate.getAccount(actyp,acno,delegate.getSysDate());
					custId=accountobject_crd.getCid();
					System.out.println("after cid");
					
				}	
			}	
		
		
		Reason[] reason = delegate.getReasonDetails("1002001",0);
		httpServletRequest.setAttribute("Reason", reason);
		httpServletRequest.setAttribute("Main_Module_code", module);
		path=MenuNameReader.getScreenProperties(recievedECSForm.getPageId());	
		httpServletRequest.setAttribute("flag",sigPath);
		httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
		httpServletRequest.setAttribute("pageId",path);
		httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
		httpServletRequest.setAttribute("signInfo",delegate.getSignatureDetails((acno),actyp));
		
		return actionMapping.findForward(ResultHelp.getSuccess());
		}

	}
	catch(Exception e)
	{	
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
	


else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ControlnoDetail"))
{
	try
	{
		 System.out.println("*********ControlNo Detail*************");
		 ControlNoForm ctrlnoForm=(ControlNoForm)actionForm;
		 ctrlnoForm.setValidation("");
		 httpServletRequest.setAttribute("clrdate", delegate.getSysDate());
	     if(MenuNameReader.containsKeyScreen(ctrlnoForm.getPageId()))
			{
	    	 	path =MenuNameReader.getScreenProperties(ctrlnoForm.getPageId());
				httpServletRequest.setAttribute("pageId",path);
				setInwardstmtForm(httpServletRequest,path,delegate);
				
				return actionMapping.findForward(ResultHelp.getSuccess());

			}
	     else
	     	{
	    	 return actionMapping.findForward(ResultHelp.getError());
	     	}
		}
		catch (Exception e) 
		{		
		       e.printStackTrace(); 
	    	   return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
}
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Controlno"))
{
	ControlNoForm ctrlnoForm=(ControlNoForm)actionForm;
	ClearingObject clearingobject[]=null;
	ctrlnoForm.setValidation("");
	if(ctrlnoForm.getBut_value().equalsIgnoreCase("View"))
	{
		System.out.println("from date"+Integer.parseInt(ctrlnoForm.getFrom_date())+"to date"+ctrlnoForm.getTo_date());
		try
		{
		clearingobject=delegate.getDetails(Integer.parseInt(ctrlnoForm.getFrom_date()),Integer.parseInt(ctrlnoForm.getTo_date()),string_qry);
		System.out.println("after delegate");
		
		if(clearingobject!=null)
		{
		
			ArrayList chq_list=  new ArrayList();
			
			module = delegate.getMainModules(1,"");
			
			for(int i=0;i<clearingobject.length;i++)
			{
				Map map=new TreeMap();	
				map.put("cldate",clearingobject[i].getClgDate());
				map.put("clrno", new Integer(clearingobject[i].getClgNo()));
				map.put("sendto",new Integer(clearingobject[i].getSendTo()));
				map.put("ctrlno",new Long(clearingobject[i].getControlNo()));
				map.put("trntype",clearingobject[i].getTranType());
				map.put("clrtype",clearingobject[i].getClgType());
				map.put("ackno",new Integer(clearingobject[i].getAckNo()));
				map.put("docsrc",new Integer(clearingobject[i].getDocSource()));
				map.put("docdest",new Integer(clearingobject[i].getDocDestination()));
				map.put("docbs",clearingobject[i].getDocBs());
				map.put("noofdocs",new Integer(clearingobject[i].getNoOfDocs()));
				map.put("doctotal",new Double(clearingobject[i].getDocTotal()));
				map.put("mlcredit",clearingobject[i].getMultiCredit());
				map.put("cmpname", clearingobject[i].getCompanyName());
				map.put("chqddpo",clearingobject[i].getChqDDPO());
				map.put("qdpno",new Integer(clearingobject[i].getQdpNo()));
				map.put("qdpdate", clearingobject[i].getQdpDate());
				map.put("retnormal",clearingobject[i].getRetNormally());
				map.put("prevctrlno",new Integer(clearingobject[i].getPrevCtrlNo()));
				map.put("trftype",clearingobject[i].getTrfType());
				if(clearingobject[i].getClgType().equalsIgnoreCase("O")){
	                    if(clearingobject[i].getMultiCredit()!=null && clearingobject[i].getMultiCredit().equals("F")){
	                        for (int k = 0; k<module.length; k++)
	                            if(clearingobject[i].getCreditACType()!= null && clearingobject[i].getCreditACType().equals(String.valueOf(module[k].getModuleCode()))){
	                                map.put("abbrvcredit",module[k].getModuleAbbrv()+" "+clearingobject[i].getCreditACNo());
	                                break;
	                            }
	                    }
	                    map.put("pocomm",new Double(clearingobject[i].getPOCommission()));	
	                    map.put("blank"," ");	
	             }else if(clearingobject[i].getClgType().equalsIgnoreCase("I")){
	                    for(int k = 0; k<module.length; k++)
	                        if(clearingobject[i].getDebitACType()!= null && clearingobject[i].getDebitACType().equals(String.valueOf(module[k].getModuleCode()))){
	                            map.put("abbrvdebit",module[k].getModuleAbbrv()+" "+clearingobject[i].getDebitACNo());
	                            break;
	                        }
	                    map.put("pocomm",new Double(clearingobject[i].getPOCommission()));
	                    map.put("blank"," ");
	             }else{
	                    if(clearingobject[i].getMultiCredit().equals("F")){
	                        for(int k = 0;k<module.length; k++)
	                            if(clearingobject[i].getCreditACType()!=null && clearingobject[i].getCreditACType().equals(String.valueOf(module[k].getModuleCode()))){
	                                map.put("abbrvcredit",module[k].getModuleAbbrv()+" "+clearingobject[i].getCreditACNo());
	                                break;
	                            }
	                    }		
	                    map.put("pocpmm",new Double(clearingobject[i].getPOCommission()));
	                    map.put("blank"," ");
	                    for(int k = 0; k<module.length; k++)
	                        if(clearingobject[i].getDebitACType()!= null && clearingobject[i].getDebitACType().equals((module[k].getModuleCode()))){
	                        	map.put("abbrvdebit",module[k].getModuleAbbrv()+" "+clearingobject[i].getDebitACNo());
	                            break;
	                        }
	                    map.put("pocomm",new Double(clearingobject[i].getPOCommission()));
	                    map.put("blank"," ");
	                }
	                map.put("payename",clearingobject[i].getPayeeName());
	                map.put("bkcode",new Integer(clearingobject[i].getBankCode()));
	                map.put("brname",clearingobject[i].getBranchName());
	                map.put("trnamt",new Double(clearingobject[i].getTranAmt()));
	                map.put("tobounce",clearingobject[i].getToBounce());
	                map.put("despind",clearingobject[i].getDespInd());
	                map.put("postind",clearingobject[i].getPostInd());
	                map.put("discind",clearingobject[i].getDiscInd());
	                map.put("discamt",new Double(clearingobject[i].getDiscAmt()));
	                map.put("exclgdte",clearingobject[i].getExpectedClgDate());
	                map.put("expclno",new Integer(clearingobject[i].getExpClgNo()));
	                map.put("lettersent",clearingobject[i].getLetterSent());
	                map.put("mchangeamt",new Double(clearingobject[i].getMChangeAmt()));
	                map.put("fineamt",new Double(clearingobject[i].getFineAmt()));
	                map.put("detml",clearingobject[i].getDeTml());
	                map.put("deuser",clearingobject[i].getDeUser());
	                map.put("detime",clearingobject[i].getDeTime());
	                map.put("vetml",clearingobject[i].getVeTml());
	                map.put("veuser",clearingobject[i].getVeUser());
	                map.put("vetime",clearingobject[i].getVeTime());
	                chq_list.add(map);
			}
			httpServletRequest.setAttribute("chequelist",chq_list);
		}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(clearingobject==null)
		{
			ctrlnoForm.setValidation("norecords");
		}
	}
	
	setInwardstmtForm(httpServletRequest, path, delegate);
	return actionMapping.findForward(ResultHelp.getSuccess());
}
	

else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/SelectivePostMLink"))
{
	
	try
	{
		String path;
		System.out.println("8822288888888888888888888888888888");
		SelectiveForm selectiveForm =(SelectiveForm)actionForm;
		System.out.println("2222222111111111111111111111111111111111");
		System.out.println("///////////"+MenuNameReader.containsKeyScreen(selectiveForm.getPageId()));
		
		
		if(MenuNameReader.containsKeyScreen(selectiveForm.getPageId())){	

		System.out.println("*************"+MenuNameReader.containsKeyScreen(selectiveForm.getPageId()));
	
		path=MenuNameReader.getScreenProperties(selectiveForm.getPageId());	
		httpServletRequest.setAttribute("pageId",path);
		return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/SelectivePostLink"))
{
	try
	{
		String path;
		SelectiveForm selectiveForm =(SelectiveForm)actionForm;
		selectiveForm.setValidateFlag("");
		selectiveForm.setErrorFlag("");
		if(MenuNameReader.containsKeyScreen(selectiveForm.getPageId()))
		{	
			selectiveForm.setValidateFlag("");
		System.out.println("*************"+MenuNameReader.containsKeyScreen(selectiveForm.getPageId()));
		
		if(selectiveForm.getFlag().equalsIgnoreCase("frmControlNum"))
		{
			
			
			ClearingObject clearingObject=delegate.getSelectiveDetails(Long.parseLong(selectiveForm.getControlNumber()));
			
			if(clearingObject==null)
			{
				selectiveForm.setValidateFlag("Control Number Not Found");
			}
			else if(!clearingObject.getClgType().equals("O"))
			{
				selectiveForm.setValidateFlag("Control Number Not An OutWard");
			}
			else if(clearingObject.getRetNormally().equals("R"))
			{
				selectiveForm.setValidateFlag("Control Number Is An Outward Returned");
			}
			else if(clearingObject.getVeUser()==null)
			{
				selectiveForm.setValidateFlag("Control Number Not Yet Verified");
				
			}
			else if(clearingObject.getClgDate()==null)
			{
				
				selectiveForm.setValidateFlag("Control Number Not Yet Identified");
			}
			else if(clearingObject.getDespInd().equals("F"))
			{
				
				
				selectiveForm.setValidateFlag("Control Number Not Yet Dispatched");
			}
			else if(clearingObject.getPostInd().equals("T"))
			{
				
				selectiveForm.setValidateFlag("Control Number Already Posted");
			}
			
			else
			{
				httpServletRequest.setAttribute("clearingObject", clearingObject);
			}
			
			
			
			
		}
		
		else if(selectiveForm.getFlag().equalsIgnoreCase("submit"))
		{
			
			System.out.println("*****************");
			
			int	value=0;
			 value= delegate.storeSelectivePosting(Long.parseLong(selectiveForm.getControlNumber()),uid,tml,CommonDelegate.getSysDate(),CommonDelegate.getSysDate(),null);
			
		if(value>0)
		{
			selectiveForm.setValidateFlag("Posting Is Successfull");
			selectiveForm.setErrorFlag("1");
			System.out.println("11********");
		}
		else
		{
			selectiveForm.setValidateFlag("Error While Posting");
			System.out.println("2*********");
		}
		}
		
		
		path=MenuNameReader.getScreenProperties(selectiveForm.getPageId());	
		httpServletRequest.setAttribute("pageId",path);
		return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}

}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/RecievedChequeEntryMLink"))
{
	
	try
	{
		System.out.println("^^^^hiiii^^^^^^^^^^");
		String path;
		RecievedChequeEntryForm recievedChequeEntryForm=(RecievedChequeEntryForm)actionForm;
	
		
		if(MenuNameReader.containsKeyScreen(recievedChequeEntryForm.getPageId()))
		{	

		System.out.println("*************"+MenuNameReader.containsKeyScreen(recievedChequeEntryForm.getPageId()));
		module_debit = setDebitAccountDetail();
		httpServletRequest.setAttribute("Debit_Module_code", module_debit );

		path=MenuNameReader.getScreenProperties(recievedChequeEntryForm.getPageId());	
		Reason[] reason = delegate.getReasonDetails("1002001", 0 );
		httpServletRequest.setAttribute("Reason", reason );
		System.out.println("At 2304 ===="+CommonDelegate.getSysDate());
    	httpServletRequest.setAttribute("date",CommonDelegate.getSysDate());
    	httpServletRequest.setAttribute("pageId",path);
    	return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
		{
			return actionMapping.findForward(ResultHelp.getError());
		}
		
	}
	catch(Exception e)
	{	e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/BouncedChequePostMLink"))
{
	
	
	try
	{
		System.out.println("^^^^hiiii^^^^^^^^^^");
		String path;
		BouncedChequePostForm bouncedChequePostForm=(BouncedChequePostForm)actionForm;
		bouncedChequePostForm.setValidateFlag("");
		bouncedChequePostForm.setErrorFlag("");
		if(MenuNameReader.containsKeyScreen(bouncedChequePostForm.getPageId()))
		{	
			System.out.println("*************"+MenuNameReader.containsKeyScreen(bouncedChequePostForm.getPageId()));
			path=MenuNameReader.getScreenProperties(bouncedChequePostForm.getPageId());	
			httpServletRequest.setAttribute("pageId",path);
			System.out.println("At 2304"+CommonDelegate.getSysDate());
			httpServletRequest.setAttribute("date",CommonDelegate.getSysDate());
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	catch(Exception e)
	{	
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/BouncedChequePostLink"))
{
	try
	{
		System.out.println("11^^^^^^^^^^^^^^");
		String path;
		BouncedChequePostForm bouncedChequePostForm=(BouncedChequePostForm)actionForm;
        bouncedChequePostForm.setValidateFlag("");
        bouncedChequePostForm.setErrorFlag("");
		ChequeDepositionObject[] chequeDepositionObject;
		
		if(MenuNameReader.containsKeyScreen(bouncedChequePostForm.getPageId()))
		{	

			System.out.println("*************"+MenuNameReader.containsKeyScreen(bouncedChequePostForm.getPageId()));
		
		
			//Client.getBouncedDetails(Validations.checkDate(txt_clg_date.getText().trim()),MainScreen.head.getUid(),MainScreen.head.getTml(),MainScreen.head.getSysDate(),1);
		
				if(bouncedChequePostForm.getFlag().equalsIgnoreCase("frmClrDate"))
				{
					System.out.println("here inside clrDate");
						try
						{
							chequeDepositionObject = delegate.getBouncedDetails(bouncedChequePostForm.getClr_date(),uid,tml,CommonDelegate.getSysDate(),1);
							System.out.println("Aferer hiii delegate");
							
							if(chequeDepositionObject!=null)
							{
								
									System.out.println("Chequew hiiii-->"+chequeDepositionObject.length);
									bouncedChequePostForm.setControlNum(String.valueOf(chequeDepositionObject[0].getControlNo()));
									bouncedChequePostForm.setAckNumber(String.valueOf(chequeDepositionObject[0].getAckNo()));
									bouncedChequePostForm.setSourceNum(String.valueOf(chequeDepositionObject[0].getSendTo()));
									bouncedChequePostForm.setSourceName(String.valueOf(chequeDepositionObject[0].getCompanyName()));
									bouncedChequePostForm.setPayee(String.valueOf(chequeDepositionObject[0].getPayeeName()));
									bouncedChequePostForm.setBankCode(String.valueOf(chequeDepositionObject[0].getBankCode()));
									bouncedChequePostForm.setBankName(String.valueOf(chequeDepositionObject[0].getBankName()));
									bouncedChequePostForm.setBranchName(String.valueOf(chequeDepositionObject[0].getBranchName()));
									bouncedChequePostForm.setAmount(String.valueOf(chequeDepositionObject[0].getTranAmt()));
									
									if(chequeDepositionObject[0].getChqDDPO().equals("C"))
									{
										bouncedChequePostForm.setChqPoWarrant(String.valueOf(chequeDepositionObject[0].getChqDDPO()));
										bouncedChequePostForm.setChqno(String.valueOf(chequeDepositionObject[0].getQdpNo()));
										bouncedChequePostForm.setChqddpo(String.valueOf(chequeDepositionObject[0].getQdpDate()));
										bouncedChequePostForm.setAckType(String.valueOf(chequeDepositionObject[0].getDebitACNo()));
										bouncedChequePostForm.setAcntType(String.valueOf(chequeDepositionObject[0].getDebitACType()));
										AccountMasterObject accountmasterobject = delegate.getAccountInfoInd(chequeDepositionObject[0].getDebitACType(),chequeDepositionObject[0].getDebitACNo());
										bouncedChequePostForm.setBalance(String.valueOf(accountmasterobject.getCloseBal()));
										bouncedChequePostForm.setShadowBalance(String.valueOf(accountmasterobject.getPreCloseBal()-accountmasterobject.getTransAmount()));
									}
									else if(chequeDepositionObject[0].getChqDDPO().equals("P"))
									{
										bouncedChequePostForm.setChqPoWarrant(String.valueOf(chequeDepositionObject[0].getChqDDPO()));
										bouncedChequePostForm.setChqno(chequeDepositionObject[0].getQdpDate());
										bouncedChequePostForm.setChqddpo(String.valueOf(chequeDepositionObject[0].getQdpNo()));

										FrontCounterRemote front=null;
										try
										{
											//Object ob = Login.getContext().lookup("FRONTCOUNTER");
										    //FrontCounterHome fchome = (FrontCounterHome) PortableRemoteObject.narrow(ob,FrontCounterHome.class);
										    FrontCounterHome fchome = (FrontCounterHome) HomeFactory.getFactory().lookUpHome("FRONTCOUNTER");
											front=fchome.create();
										}catch(Exception e){
											e.printStackTrace();
										}
															
										PayOrderObject payorderobject = front.getPayOrderInstrn(Integer.parseInt(bouncedChequePostForm.getChqno()));
										
										if(payorderobject.getPOSerialNo() != -1){
											//lbl_po_name.setText(String.valueOf(payorderobject.getPOPayee()));
											bouncedChequePostForm.setBalance(String.valueOf(payorderobject.getPOAmount()));
										}
									}
									
									
								httpServletRequest.setAttribute("chequeDepositionObject", chequeDepositionObject);
							}
							else
							{
								System.out.println("Records not found....in action");
								bouncedChequePostForm.setValidateFlag("Records Not Found");
							}
							
							
							
						}
						catch(Exception e)
						{
						e.printStackTrace();
						}
					
					}
		
					if(bouncedChequePostForm.getFlag().equalsIgnoreCase("frmSubmit") )
					{
						int k=0;
						chequedepositionobject = delegate.getBouncedDetails(bouncedChequePostForm.getClr_date(),uid,tml,CommonDelegate.getSysDate(),1);
						
						if(chequedepositionobject!=null)
						{	
						
							if(chequedepositionobject[k].getCreditACNo()==0 && chequedepositionobject[k].getBounceInd().equals("T"))
							{
								if(chequedepositionobject[k].getBalanceInd()==false)
								{
									int kk = -1;
									// bouncedChequePostForm.setChooseFlag("0");
									if(JOptionPane.showConfirmDialog(null,"Shall I Resend The Cheque Too Clearing Bank ","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
									{
									// to outward
									//create new ctrl no
								
										try
										{
											chequedepositionobject[k].setDeTml(uv.getUserId());
											chequedepositionobject[k].setDeUser(uv.getUserTml());
											chequedepositionobject[k].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
											chequedepositionobject[k].setSysdate( delegate.getSysDate());
											long long_ctrl = delegate.storeInwardBouncedData(chequedepositionobject[k]);
											kk = delegate.setChequeNo(chequedepositionobject[k]);
									
											if (kk < 0 )
											{
												bouncedChequePostForm.setValidateFlag("Records Not Inserted");
											}
											bouncedChequePostForm.setValidateFlag("Note Down Control Number" +long_ctrl);
											bouncedChequePostForm.setErrorFlag("1");
											System.out.println("contrl no "+long_ctrl);
										}
										catch(Exception data)
										{
											System.out.println("ctrl generation "+data);
											data.printStackTrace();
										}
									}
								}
								else
								{
								// if only one reason i.e insuffient fund
							//	if(array_chequedepositionobject[k].getVeTml().equals("TT")){
								
								if(Double.parseDouble(bouncedChequePostForm.getBalance().trim()) > Double.parseDouble(bouncedChequePostForm.getAmount().trim()))
								{
									bouncedChequePostForm.setValidateFlag("Cheque Is Going To Be Pass");
									try{
										System.out.println("in trans");
										//array_chequedepositionobject = Client.getBouncedDetails(Validations.checkDate(txt_clg_date.getText()),MainScreen.head.getUid(),MainScreen.head.getTml(),2);
										chequedepositionobject[k].setVeTml(uv.getVerTml());
										chequedepositionobject[k].setVeUser(uv.getVerId());
										chequedepositionobject[k].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
										chequedepositionobject[k].setSysdate( delegate.getSysDate());
										int int_value = delegate.addAccountTransactionDebitEntry(chequedepositionobject[k]);
										System.out.println("updated "+int_value);
										}
										catch(Exception balance)
										{	System.out.println("Allow -ve Balance"+balance);
											balance.printStackTrace();
										}
								}
								else if(JOptionPane.showConfirmDialog(null," Amount Is Less ,Shall I Allow Negative Balance","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
								{
										// put debit entry in ac tran
										try{
											System.out.println("in trans");
											//array_chequedepositionobject = Client.getBouncedDetails(Validations.checkDate(txt_clg_date.getText()),MainScreen.head.getUid(),MainScreen.head.getTml(),2);
											chequedepositionobject[k].setVeTml(uv.getVerTml());
											chequedepositionobject[k].setVeUser(uv.getVerId());
											chequedepositionobject[k].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
											chequedepositionobject[k].setSysdate( delegate.getSysDate());
											int int_value = delegate.addAccountTransactionDebitEntry(chequedepositionobject[k]);
											System.out.println("updated "+int_value);
											}
										catch(Exception balance)
											{
												System.out.println("Allow -ve Balance"+balance);
												balance.printStackTrace();
											}
								}
								else
								{
									if(JOptionPane.showConfirmDialog(null,"Shall I Resend The Cheque To Clearing Bank ","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
									{
										// to outward
										//create new ctrl no
										int res = -1;
										//double col_bal = 0.0;
										try{
											
											chequedepositionobject[k].setDeTml(uv.getUserId());
											chequedepositionobject[k].setDeUser(uv.getUserTml());
											chequedepositionobject[k].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
											chequedepositionobject[k].setSysdate( delegate.getSysDate());
											System.out.println(chequedepositionobject[k].getDeTime());
											
																			
											if((chequedepositionobject[k].getFineAmt() > new Double( bouncedChequePostForm.getBalance().toString())))
											{
													bouncedChequePostForm.setErrorFlag("0");
													if(bouncedChequePostForm.getErrorFlag()=="1")
													{
														chequedepositionobject[k].setFineAmt(0.00);
													}
											}
											
												long long_ctrl = delegate.storeInwardBouncedData(chequedepositionobject[k]);
											
												res = delegate.setChequeNo(chequedepositionobject[k]);
											
												if(res <= 0 )
												{
												 bouncedChequePostForm.setValidateFlag( "Records Not Inserted");
												}
												bouncedChequePostForm.setValidateFlag("Note Down Control Number" +long_ctrl);//,"Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
												bouncedChequePostForm.setErrorFlag("1");
												System.out.println("contrl no "+long_ctrl);
											}
										catch(Exception data)
										{
											System.out.println("ctrl generation "+data);
											data.printStackTrace();
										}
									}
								}
									
				
							}
						}
					}
				}
				
		    }
				path=MenuNameReader.getScreenProperties(bouncedChequePostForm.getPageId());	
				httpServletRequest.setAttribute("pageId",path);
				return actionMapping.findForward(ResultHelp.getSuccess());
				
	 }
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
						
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ClearingDispatchMLink"))
{
	
	try
	{
		
		String path;
		
		
		ClearingDispatchForm clearingDispatchForm=(ClearingDispatchForm)actionForm;
	
		
		if(MenuNameReader.containsKeyScreen(clearingDispatchForm.getPageId())){	

		System.out.println("*********************"+clearingDispatchForm.getPageId());
	
		path=MenuNameReader.getScreenProperties(clearingDispatchForm.getPageId());	
		httpServletRequest.setAttribute("date",CommonDelegate.getSysDate() );
		httpServletRequest.setAttribute("pageId",path);
		return actionMapping.findForward(ResultHelp.getSuccess());
		}
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ClearingDispathcLink"))
{
	
	try
	{                     
		
		String path;
		ClearingDispatchForm clearingDispatchForm=(ClearingDispatchForm)actionForm;
		clearingDispatchForm.setValidateFlag("");
		
		if(MenuNameReader.containsKeyScreen(clearingDispatchForm.getPageId()))
		{	
			clearingDispatchForm.setValidateFlag("");
		System.out.println("*************"+MenuNameReader.containsKeyScreen(clearingDispatchForm.getPageId()));
		path=MenuNameReader.getScreenProperties(clearingDispatchForm.getPageId());	


		
		if(clearingDispatchForm.getFlag().equalsIgnoreCase("frmDate"))
		{
			
			System.out.println("---->"+clearingDispatchForm.getClr_date());
			if(clearingDispatchForm.getClr_date()!=null)
			{
				int arr[] = delegate.getClearingNo(clearingDispatchForm.getClr_date(), 0,3);
				httpServletRequest.setAttribute("bankNums", arr);
			}
			else
			{
				clearingDispatchForm.setValidateFlag("Enter Clearing Date");
			}
		}
		
		else if(clearingDispatchForm.getFlag().equalsIgnoreCase("frmClgBank"))
		{
			if(clearingDispatchForm.getClgBank()!=null)
			{
				int arr[] = delegate.getClearingNo(clearingDispatchForm.getClr_date(), Integer.parseInt(clearingDispatchForm.getClgBank()),4);
				httpServletRequest.setAttribute("clgNums", arr);	
			}
			else
			{
				clearingDispatchForm.setValidateFlag("Select Clearing Bank");
			}
		}


		else if(clearingDispatchForm.getFlag().equalsIgnoreCase("frmClr_no"))
		{
			if((clearingDispatchForm.getClgBank()!=null)&&(clearingDispatchForm.getClr_no()!=null))
			{
				ChequeDepositionObject[]  array_chequedepositionobject=delegate.getIdentifiedCheques(1,0,CommonDelegate.getSysDate(),Integer.parseInt(clearingDispatchForm.getClgBank()), Integer.parseInt(clearingDispatchForm.getClr_no()));
				if(array_chequedepositionobject==null)
				{
					clearingDispatchForm.setValidateFlag("Records Not Found");
				}
				else
				{
					httpServletRequest.setAttribute("dispatchDeatils",array_chequedepositionobject );
				}
			}
			else
			{
				clearingDispatchForm.setValidateFlag("Enter Valid Details");
			}
		}

		else if(clearingDispatchForm.getFlag().equalsIgnoreCase("submit"))
		{
			
//				Enumeration enumeration =httpServletRequest.getParameterNames();
//				ArrayList list=new ArrayList();
//				String name;
//			
//				while(enumeration.hasMoreElements())
//				{
//					System.out.println("--->?");
//				 	name=(String)enumeration.nextElement();
//					if(name.startsWith("chkBox"))
//					{
//					name  = name.substring(3 , name.length());					
//					System.out.println("----->"+name);
//					list.add(name);
//					}
//			
//				}
				System.out.println("inside submit");
				
				String[] cbox=httpServletRequest.getParameterValues("chkBox");
				
				if(cbox.length>0)
				{		
					long[] ctrl_no=new long[cbox.length];
					String[] ctrlNum=httpServletRequest.getParameterValues("txtCtrlNum");
				
				
						System.out.println("inside if");
							for(int i=0,j=0;i<cbox.length;i++)
							{
								System.out.println("inside for");
								int x=Integer.parseInt(cbox[i]);
								System.out.println("......,"+x);
								System.out.println("******1234***"+ctrlNum[x]);
								System.out.println(" ctrlNum[x]==============="+ Long.parseLong(ctrlNum[x]));
								ctrl_no[j]=Long.parseLong(ctrlNum[x]);
								System.out.println("ctrl_no[j]-------->"+ctrl_no[j]);
								j++;
							}
			
							int i=delegate.dispatchCheques(ctrl_no,tml,uid,CommonDelegate.getSysDate(),CommonDelegate.getSysDate());
			
							if(i>0)
							{
								System.out.println("insode if 222222222222");
								clearingDispatchForm.setValidateFlag("Dispathed Successfully");
							}
							else
							{
								System.out.println("inside elseeeeeeeeee");
								clearingDispatchForm.setValidateFlag("Error in Dispatch");
							}
				
					}
				
				}
			
				if(clearingDispatchForm.getClr_date()!=null)
					httpServletRequest.setAttribute("date",clearingDispatchForm.getClr_date());

		
				if(clearingDispatchForm.getClgBank()!=null)
					httpServletRequest.setAttribute("bankNums",clearingDispatchForm.getClgBank());
		
				if(clearingDispatchForm.getClr_no()!=null)
					httpServletRequest.setAttribute("clgNums",clearingDispatchForm.getClr_no());
		
				httpServletRequest.setAttribute("pageId",path);
				return actionMapping.findForward(ResultHelp.getSuccess());
			}
		
		}
	
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ClearingPostMLink"))
{
try
	{
		ClearingPostForm clearingPostForm=(ClearingPostForm)actionForm;
		clearingPostForm.setValidateFlag("");
		System.out.println("inside try ====");
		if(MenuNameReader.containsKeyScreen(clearingPostForm.getPageId()))
		{
			System.out.println("inside if");
			
			array_clearingobject = delegate.getOutwardSummary(ClearingDelegate.getSysDate(),4,0,0,null);
			
			if( array_clearingobject!= null)
			{
			
				httpServletRequest.setAttribute("arrayClearingObject",array_clearingobject);
			}
			else
			{
				clearingPostForm.setValidateFlag("No Clearing Date Found");
			}
			
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(clearingPostForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}	

	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ClearingPostLink"))
{
try
	{	
	
		ClearingPostForm clearingPostForm=(ClearingPostForm)actionForm;
		clearingPostForm.setValidateFlag("");
		Vector []vector_acc_type_no=null;
		System.out.println("inside try ====");
		if(MenuNameReader.containsKeyScreen(clearingPostForm.getPageId()))
		{
			System.out.println("inside if");
			
			array_clearingobject = delegate.getOutwardSummary(ClearingDelegate.getSysDate(),4,0,0,null);
			if( array_clearingobject!= null)
			{
				httpServletRequest.setAttribute("arrayClearingObject",array_clearingobject);
			}
			else
			{
				clearingPostForm.setValidateFlag("No Clearing Date Found");
			}
			
			
			 if(clearingPostForm.getFlag().equalsIgnoreCase("clearingDate"))
			{
				 if(clearingPostForm.getClrDate()!=null)
				 {
					 arr1 = delegate.getClearingNo(clearingPostForm.getClrDate().toString(), 0,2);
					 System.out.println(arr1 + " *********arr length is *******************   ");
					 if (arr1 != null)
					 {
						 httpServletRequest.setAttribute("arrayClrBank",arr1);
					 }
					 else
					 {
						 clearingPostForm.setValidateFlag("No Clearing Bank Found");
					 }
				 }
				 else
				 {
					 clearingPostForm.setValidateFlag("Clearing Date Not Found");
				 }
			}
			
			 else if(clearingPostForm.getFlag().equalsIgnoreCase("clearingBank"))
			 {
				 if((clearingPostForm.getClrDate()!=null)&& (clearingPostForm.getClrBank()!=null))
				 {
				 	arr2 =delegate.getClearingNo(clearingPostForm.getClrDate().toString(), Integer.parseInt(clearingPostForm.getClrBank()),1);
				 	System.out.println(arr2 + " ****************************   ");
				 	if (arr2 != null)
				 	{
				 		httpServletRequest.setAttribute("arrayClrNum",arr2);
				 	}
				 	else
				 	{
				 		clearingPostForm.setValidateFlag("No Clearing Number Found");
				 	}
				 }
				 else
				 {
					 clearingPostForm.setValidateFlag("Enter Proper Clearing Date And Bank");
				 }
			}
			 else if(clearingPostForm.getFlag().equalsIgnoreCase("clrNum"))
			 {
				 if((clearingPostForm.getClrDate()!=null)&& (clearingPostForm.getClrBank()!=null)&&(clearingPostForm.getClrNum()!=null))
				 {
					 array_clearingobject = delegate.getOutwardSummary(clearingPostForm.getClrDate().toString().trim(),1,Integer.parseInt(clearingPostForm.getClrNum()),Integer.parseInt("9999"),null);
					 if(array_clearingobject!=null)
					 {
						 httpServletRequest.setAttribute("arrayTableData",array_clearingobject);
					 }
					 else
					 {
						 clearingPostForm.setValidateFlag("No Rows Found");
					 }
				 }
				 else
				 {
					 clearingPostForm.setValidateFlag("Some Values Are Missing");
				 }
			 
			 }
			 else if(clearingPostForm.getFlag().equalsIgnoreCase("frmSubmit"))
			 {	
				 System.out.println("inside Submit");
				double total_amt =0.0;
				int int_count_cheques=0;
				Vector vector_ctrlno= new Vector();
				Vector vector_bankcd= new Vector();
				Vector vector_clgno=new Vector();
				String[] amount=httpServletRequest.getParameterValues("txtTranAmt");
				String[] ctrlNum=httpServletRequest.getParameterValues("txtCtrlNum");
				String[] cbox=httpServletRequest.getParameterValues("chkBox");
				System.out.println("before for loop");
				 for(int k=0;k<cbox.length;k++)
				 {	
						int x=Integer.parseInt(cbox[k]);
						total_amt += Double.parseDouble(amount[x].toString().trim());
						int_count_cheques++;
						vector_ctrlno.add(ctrlNum[x]);
						vector_clgno.add(clearingPostForm.getClrNum());
						vector_acc_type_no = delegate.clearingPosting( clearingPostForm.getClrDate(),vector_clgno,vector_bankcd,vector_ctrlno,uv.getUserTml(),uv.getUserId(),ClearingDelegate.getSysDate(),ClearingDelegate.getSysDate());
				 }
				if(vector_acc_type_no.length > 0)
				{
					 clearingPostForm.setValidateFlag(" Number Of Instruments  "+int_count_cheques +"\n Amount  "+ total_amt+"");
				}
				else 
				{
					clearingPostForm.setValidateFlag("Posting Failed");
				}
			 }
			 httpServletRequest.setAttribute("arrayClrNum",arr2);
			 httpServletRequest.setAttribute("arrayClrBank",arr1);
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(clearingPostForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/AdminMlink"))
{
try
	{
		AdminForm  adminForm=(AdminForm)actionForm;
		adminForm.setValidateFlag("");
		System.out.println("inside try ====");
		if(MenuNameReader.containsKeyScreen(adminForm.getPageId()))
		{
			System.out.println("inside if");
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(adminForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}	

	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/OutStationLetterMLink"))
{
try
	{
		OutStationLetterForm  letterForm=(OutStationLetterForm)actionForm;
		
		System.out.println("inside try ====");
		if(MenuNameReader.containsKeyScreen(letterForm.getPageId()))
		{
			System.out.println("inside if");
			httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(letterForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}		
	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/OutStationLetterLink"))
{
	try
	{
		OutStationLetterForm  letterForm=(OutStationLetterForm)actionForm;
		
		System.out.println("inside try ====");
		if(MenuNameReader.containsKeyScreen(letterForm.getPageId()))
		{
			System.out.println("inside if");
			
			if(letterForm.getFlag().equalsIgnoreCase("clgDate"))
			{
				
				try 
				{
					
					ChequeDepositionObject[] chq_obj = delegate.getOutstationCheque(letterForm.getClgDate().toString().trim());
					
					if ( chq_obj != null && chq_obj.length > 0)
					{
						httpServletRequest.setAttribute("OutStationCheque",chq_obj);
					}
				} 
				catch ( RecordsNotFoundException rec )
				{letterForm.setValidateFlag( " Records Not Found" );} 
				catch ( RemoteException rm ){
					rm.printStackTrace();
				}
				letterForm.setFlag(null);
			}
			httpServletRequest.setAttribute("date",ClearingDelegate.getSysDate());
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(letterForm.getPageId()));
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
			return actionMapping.findForward(ResultHelp.getError());
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}			
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Admin"))
{
	
	try
	{
		
		AdminForm  adminForm=(AdminForm)actionForm;
		Vector[] vector_value = new Vector[1];
		 vector_value[0]=new Vector();
		adminForm.setValidateFlag("");
		//Object array_object_obj[]=null;
		if(MenuNameReader.containsKeyScreen(adminForm.getPageId()))
		{	
			String value=null;
			int int_rows=0,int_column=0;
			String [][] str_arr=null;
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(adminForm.getPageId()));
			System.out.println("inside try==============");
			int_column=2;
			adminForm.setValidateFlag("");
			if(adminForm.getAdminModules().equalsIgnoreCase("ClearingNo"))
			{
				System.out.println("inside clearing num");
				String object_maxclg=(String)(delegate.getGenParam("top_margin").toString());
				if(object_maxclg!=null)
				{	
				
					httpServletRequest.setAttribute("clearingNum",object_maxclg);
				}
				else
				{
					adminForm.setValidateFlag("Records Not Found");
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("ClearingBanks"))
			{
				
			    BranchObject[] array_branchobject = delegate.getBranchDetails(9999);
				module= delegate.getMainModules(2, "1012000");
				
				if(array_branchobject==null)
				{
					adminForm.setValidateFlag("No Rows Found");
				}
				else
				{
					httpServletRequest.setAttribute("ClrBank", array_branchobject);
				}
			}
			
			else if(adminForm.getAdminModules().equalsIgnoreCase("BankMaster"))
			{
				BankMaster array_bankmaster[]=delegate.getBankDetails(-1,0,0);
				if(array_bankmaster!=null)
				{
					httpServletRequest.setAttribute("BankMaster",array_bankmaster);
				}
				else
				{
					adminForm.setValidateFlag("Records Not Found");
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BranchMaster"))
			{
				System.out.println("Branch Master...");
				BranchObject array_branchobject[]=delegate.getBranchDetails(-3);
				module=delegate.getMainModules(2, "1018000");
		
				if(array_branchobject[0].getBranchCode()==-1)
				{
					adminForm.setValidateFlag("No Rows Found");
				}
				else
				{
					
					httpServletRequest.setAttribute("BranchMaster",array_branchobject);
				}
				
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("CompanyMaster"))
			{
				CompanyMaster array_companymaster[]=delegate.retrieveCompanyDetails(-2);
				if(array_companymaster== null)
					adminForm.setValidateFlag("No Rows Found");
				else
				{
					
					httpServletRequest.setAttribute("CompanyMaster",array_companymaster);
				}	
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("ReasonMaster"))
			{
				ReasonMaster array_reasonmaster[] =delegate.getReasons();
				if(array_reasonmaster != null)
				{
					
					httpServletRequest.setAttribute("ReasonMaster",array_reasonmaster);
				}
				else
					adminForm.setValidateFlag("No Rows Found");
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("ReasonLink"))
			{
				ReasonLink array_reasonlink[]=delegate.getLinkReasons();
				if(array_reasonlink != null)
				{
					
					httpServletRequest.setAttribute("ReasonLink",array_reasonlink);
					
				}
				else 
					adminForm.setValidateFlag("No Rows Found");
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BounceFine"))
			{
				BounceFineObject[] array_bouncefineobject=delegate.retrieveBounceFine();
				module = delegate.getMainModules(2, "'1002000','1007000','1014000','1015000'");
				if(array_bouncefineobject!=null)
				{
					httpServletRequest.setAttribute("BounceFine",array_bouncefineobject);
				}
				else
					adminForm.setValidateFlag("No Rows Found");
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("DiscountCharges"))
			{
				DiscountCharges[] array_discountcharges=delegate.retrieveDiscountCharges();
				module = delegate.getMainModules(2, "'1002000','1007000','1014000','1015000'");
				if(array_discountcharges!=null)
				{
					
					httpServletRequest.setAttribute("DiscountCharges",array_discountcharges);
				}
				else
					adminForm.setValidateFlag("No Rows Found");
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("MailingCharges"))
			{
				String[] obj =(String[])delegate.getMailChg("mail_chg");
				if(obj!=null)
				{
					
					httpServletRequest.setAttribute("MailingCharges",obj);
				}
				else
					adminForm.setValidateFlag("No Rows Found");
			}	
		if(adminForm.getFlag().equalsIgnoreCase("Update"))
		{
			if(adminForm.getAdminModules().equalsIgnoreCase("ClearingNo"))
			{		
					String clgNum=httpServletRequest.getParameter("txtClrNum");
					
					if(delegate.UpdateGenParam(Integer.parseInt(clgNum)))
					{
							adminForm.setValidateFlag("Successfully Updated");
					}
					else
						adminForm.setValidateFlag("Error While Updating");
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("DiscountCharges"))
			{
				String[] cbox=httpServletRequest.getParameterValues("chkBox");
				int int_rowindex=cbox.length;
				Object array_object_value[]= new Object[8];
				System.out.println(int_rowindex);
				for(int k=0;k<cbox.length;k++)
				{	
					int x=Integer.parseInt(cbox[k]);
					//vector_rowidentity.add(brcode[x]);
				}		
				try
				{
					if(delegate.clearingAdminUpdation(array_object_value,8)>0)	
						adminForm.setValidateFlag("Successfully Updated");
					else
						adminForm.setValidateFlag("Error While Updating");
				}
				catch(Exception i)
				{
					i.printStackTrace();
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BounceFine"))
			{
				String[] cbox=httpServletRequest.getParameterValues("chkBox");
				
				if(cbox!=null)
				{
					int int_rowindex=cbox.length;
					Object array_object_value[]= new Object[8];
					String[] bcode=httpServletRequest.getParameterValues("txtbounceCode");
					String[] atype=httpServletRequest.getParameterValues("txtaccountType");
					String[] fine=httpServletRequest.getParameterValues("txtfine");
					String[] returnfine=httpServletRequest.getParameterValues("txtreturnFine");
					String[] mailingchg=httpServletRequest.getParameterValues("txtmailingChg");
					String[] smschg=httpServletRequest.getParameterValues("txtsmsChg");
					String[] email=httpServletRequest.getParameterValues("txtemailChg");
				
					for(int k=0;k<cbox.length;k++)
					{	
						int x=Integer.parseInt(cbox[k]);
						array_object_value[1]=bcode[x];
						array_object_value[2]=atype[x];
						array_object_value[3]=fine[x];
						array_object_value[4]=returnfine[x];
						array_object_value[5]=mailingchg[x];
						array_object_value[6]=smschg[x];
						array_object_value[7]=email[x];
					}
					try
					{
						if(delegate.clearingAdminUpdation(array_object_value,7)>0)	
							adminForm.setValidateFlag("Successfully Updated");
						else
							adminForm.setValidateFlag("Updation UnSuccessful");
					}
					catch(Exception i)
					{
						i.printStackTrace();
					}
				}
				else
				{
					adminForm.setValidateFlag("CheckBox Is Not Selected");
				}
				
			}
			
		}
		else if(adminForm.getFlag().equalsIgnoreCase("frmaddRow"))
		{	
			 if(adminForm.getAdminModules().equalsIgnoreCase("ReasonLink"))
			{
				adminForm.setValidateFlag("You Can't Add Row For This Module");
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("DiscountCharges"))
			{
				adminForm.setValidateFlag("You Can't Add Row For This Module");
			}
			else
			{
			
				value="addrow";
				String deTml=tml;
				String deUser=uid;
				String deDate=ClearingDelegate.getSysDate()+ClearingDelegate.getSysTime();
				String veTml=tml;
				String veUser=uid;
				String veDate=ClearingDelegate.getSysDate()+ClearingDelegate.getSysTime();
				System.out.println("------>"+veDate);
				httpServletRequest.setAttribute("Arow",value);
//				httpServletRequest.setAttribute("deTml",deTml);
//				httpServletRequest.setAttribute("deUser",deUser);
//				httpServletRequest.setAttribute("deDate",deDate);
//				httpServletRequest.setAttribute("veTml",veTml);
//				httpServletRequest.setAttribute("veUser",veUser);
//				httpServletRequest.setAttribute("veDate",veDate);
			}
		}
		else if(adminForm.getFlag().equalsIgnoreCase("frmSubmit"))
		{
			
			
			if(adminForm.getAdminModules().equalsIgnoreCase("ClearingBanks"))
			{
				
				vector_value[0].add(adminForm.getBranchCode());
				vector_value[0].add(adminForm.getBranchName());
				vector_value[0].add(adminForm.getShortName());
				vector_value[0].add(adminForm.getBranchAddress());
				vector_value[0].add(adminForm.getBranchACTyp());
				vector_value[0].add(adminForm.getBranchACNum());
				//computerised
				vector_value[0].add(adminForm.getBranchTyp());
				vector_value[0].add("1");//GLCODE
				vector_value[0].add("1");//GLTYPE
				//br_comp
				vector_value[0].add(tml);
				vector_value[0].add(uid);
				
				
//				vector_value[0].add("y");
//				vector_value[0].add("h");
				
				if(delegate.ClearingAdminInsertion(vector_value,1)>0)
				{
					adminForm.setValidateFlag("Rows Inserted Successfully");
				}
				else
				{
					adminForm.setValidateFlag("Error While Insertion");
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BranchMaster"))
			{
				vector_value[0].add(adminForm.getBranchCode());
				vector_value[0].add(adminForm.getBranchName());
				vector_value[0].add(adminForm.getShortName());
				vector_value[0].add(adminForm.getBranchAddress());
				vector_value[0].add(adminForm.getBranchACTyp());
				vector_value[0].add(adminForm.getBranchACNum());
				//computerised
				vector_value[0].add(adminForm.getBranchTyp());
				vector_value[0].add(adminForm.getGlCode());
				vector_value[0].add(adminForm.getGlType());
				//br_comp
				vector_value[0].add(tml);//DeTml
				vector_value[0].add(uid);//DeUser
				
				
//				vector_value[0].add("y");
//				vector_value[0].add("h");
				
				if(delegate.ClearingAdminInsertion(vector_value,1)>0)
				{
					adminForm.setValidateFlag("Rows Inserted Successfully");
				}
				else
				{
					adminForm.setValidateFlag("Error While Insertion");
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BankMaster"))
			{
				
				vector_value[0].add(adminForm.getBankCode());
				vector_value[0].add(adminForm.getBankName());
			    vector_value[0].add(adminForm.getBankAbbr());
			    vector_value[0].add(tml);
				vector_value[0].add(uid);
				vector_value[0].add(ClearingDelegate.getSysDate());
			    
			    if(delegate.ClearingAdminInsertion(vector_value,2)>0)
					adminForm.setValidateFlag("Rows Inserted Successfully");
				else
					adminForm.setValidateFlag("Error While Insertion");
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("CompanyMaster"))
			{
				vector_value[0].add(adminForm.getCompanyCode());
				vector_value[0].add(adminForm.getCompanyName());
				vector_value[0].add(adminForm.getAccTyp());
				vector_value[0].add(adminForm.getAccNum());
				vector_value[0].add(tml);
				vector_value[0].add(uid);
				vector_value[0].add(ClearingDelegate.getSysDate());
				vector_value[0].add(tml);
				vector_value[0].add(uid);
				vector_value[0].add(ClearingDelegate.getSysDate());
				try
				{
				 if(delegate.ClearingAdminInsertion(vector_value,4)>0)
						adminForm.setValidateFlag("Rows Inserted Successfully");
					else
						adminForm.setValidateFlag("Error While Insertion");
				}
				catch (Exception e) {
					adminForm.setValidateFlag("Error While Insertion");
				}
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("ReasonMaster"))
			{
				System.out.println("inside Reason master");
				vector_value[0].add(adminForm.getCode());
				System.out.println("reason code==>"+adminForm.getCode());
				vector_value[0].add(adminForm.getReason());
				System.out.println("reason==>"+adminForm.getReason());
				vector_value[0].add(tml);
				vector_value[0].add(uid);
				 if(delegate.ClearingAdminInsertion(vector_value,5)>0)
						adminForm.setValidateFlag("Rows Inserted Successfully");
					else
						adminForm.setValidateFlag("Error While Insertion");
				
			}
			else if(adminForm.getAdminModules().equalsIgnoreCase("BounceFine"))
			{
				vector_value[0].add(adminForm.getBounceCode());
				vector_value[0].add(adminForm.getAccountType());
				vector_value[0].add(adminForm.getFine());
				vector_value[0].add(adminForm.getReturnFine());
				vector_value[0].add(adminForm.getMailingChg());
				vector_value[0].add(adminForm.getSmsChg());
				vector_value[0].add(adminForm.getEmailChg());
				vector_value[0].add(tml);
				vector_value[0].add("128");
				try{
				if(delegate.ClearingAdminInsertion(vector_value,7)>0)
					adminForm.setValidateFlag("Rows Inserted Successfully");
				else
					adminForm.setValidateFlag("Error While Insertion");
				}
				catch (Exception e) {
					adminForm.setValidateFlag("Error While Insertion");
				}
			}
			
			
		}
		else if(adminForm.getFlag().equalsIgnoreCase("frmdeleteRow"))
		{
			value=null;
			httpServletRequest.setAttribute("Arow",value);
			String[] cbox=httpServletRequest.getParameterValues("chkBox");
			if(cbox!=null)
			{
				
					if(adminForm.getAdminModules().equalsIgnoreCase("ClearingBanks"))
					{	
							int int_rowindex=cbox.length;
							Vector vector_rowidentity = new Vector();
							String[] brcode=httpServletRequest.getParameterValues("txtBranchCode");
							System.out.println(int_rowindex);
							for(int k=0;k<cbox.length;k++)
							{	
								int x=Integer.parseInt(cbox[k]);
								vector_rowidentity.add(brcode[x]);
							}		
							try
							{
								if(delegate.clearingAdminDeletion(vector_rowidentity,1)>0)	
									adminForm.setValidateFlag("Rows Deleted Successfully");
								else
									adminForm.setValidateFlag("Error While Deleting");
							}
							catch(Exception i)
							{
								i.printStackTrace();
							}
									
					}
					else if(adminForm.getAdminModules().equalsIgnoreCase("BranchMaster"))
					{	
						int int_rowindex=cbox.length;
						Vector vector_rowidentity = new Vector();
						String[] brcode=httpServletRequest.getParameterValues("txtBranchCode");
						System.out.println(int_rowindex);
						for(int k=0;k<cbox.length;k++)
					   {	
							int x=Integer.parseInt(cbox[k]);
							vector_rowidentity.add(brcode[x]);
					   }
						
						try
						{
							if(delegate.clearingAdminDeletion(vector_rowidentity,3)>0)	
								adminForm.setValidateFlag("Rows Deleted Successfully");
						    else
							   adminForm.setValidateFlag("Error While Deleting");
						}
						catch(Exception i)
						{
							i.printStackTrace();
						}
										
					}		
					else if(adminForm.getAdminModules().equalsIgnoreCase("CompanyMaster"))
					{
						int int_rowindex=cbox.length;
						Vector vector_rowidentity = new Vector();
									
						String[] compname=httpServletRequest.getParameterValues("txtCompanyName");
						String[] acctyp=httpServletRequest.getParameterValues("txtAccTyp");
						String[] accnum=httpServletRequest.getParameterValues("txtAccNum");
						System.out.println(int_rowindex);
						for(int k=0;k<cbox.length;k++)
						 {	
							int x=Integer.parseInt(cbox[k]);
							vector_rowidentity.add(compname[x]);
							vector_rowidentity.add(acctyp[x]);
							vector_rowidentity.add(accnum[x]);
						 }
						try
							{
								if(delegate.clearingAdminDeletion(vector_rowidentity,4)>0)	
									adminForm.setValidateFlag("Rows Deleted Successfully");
								else
									adminForm.setValidateFlag("Error While Deleting");
							}
							catch(Exception i)
							{
									i.printStackTrace();
							}
									
					}
					else if(adminForm.getAdminModules().equalsIgnoreCase("BankMaster"))
					{ 
									int int_rowindex=cbox.length;
									Vector vector_rowidentity = new Vector();
									
									String[] bnkcode=httpServletRequest.getParameterValues("txtBankCode");
									System.out.println(int_rowindex);
									for(int k=0;k<cbox.length;k++)
									{	
											int x=Integer.parseInt(cbox[k]);
											vector_rowidentity.add(bnkcode[x]);
									}
									try
									{
											if(delegate.clearingAdminDeletion(vector_rowidentity,2)>0)	
												adminForm.setValidateFlag("Rows Deleted Successfully");
											else
												adminForm.setValidateFlag("Error While Deleting");
									}
									catch(Exception i)
									{
										i.printStackTrace();
									}
									
								}
								else if(adminForm.getAdminModules().equalsIgnoreCase("ReasonMaster"))
								{
									int int_rowindex=cbox.length;
									Vector vector_rowidentity = new Vector();
									
									String[] reasoncode=httpServletRequest.getParameterValues("txtcode");
									System.out.println(int_rowindex);
									for(int k=0;k<cbox.length;k++)
									 {	
											int x=Integer.parseInt(cbox[k]);
											vector_rowidentity.add(reasoncode[x]);
									}
									try
									{
											if(delegate.clearingAdminDeletion(vector_rowidentity,5)>0)	
												adminForm.setValidateFlag("Rows Deleted Successfully");
											else
												adminForm.setValidateFlag("Error While Deleting");
									}
									catch(Exception i)
									{
										i.printStackTrace();
									}
									
								}
								else if(adminForm.getAdminModules().equalsIgnoreCase("BounceFine"))
								{	
									
									int int_rowindex=cbox.length;
									Vector vector_rowidentity = new Vector();
									
									String[] bouncecode=httpServletRequest.getParameterValues("txtbounceCode");
									String[] acctyp=httpServletRequest.getParameterValues("txtaccountType");
									System.out.println(int_rowindex);
									for(int k=0;k<cbox.length;k++)
									 {	
											int x=Integer.parseInt(cbox[k]);
											vector_rowidentity.add(bouncecode[x]);
											vector_rowidentity.add(acctyp[x]);
									}
									try
									{
											if(delegate.clearingAdminDeletion(vector_rowidentity,7)>0)	
												adminForm.setValidateFlag("Rows Deleted Successfully");
											else
												adminForm.setValidateFlag("Error While Deleting");
									}
									catch(Exception i)
									{
										i.printStackTrace();
									}
									
								}
				
			}
		}
			
		httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(adminForm.getPageId()));
		return actionMapping.findForward(ResultHelp.getSuccess());
			
	}
		else
		{
			return actionMapping.findForward(ResultHelp.getError());
		}
	}	
	
	catch (Exception e) 
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
}	

else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ChequeDiscountingMLink"))
{
	
	
	try
	{
		ChequeDiscountingForm chequeDiscountingForm=(ChequeDiscountingForm)actionForm;
		if(MenuNameReader.containsKeyScreen(chequeDiscountingForm.getPageId())){
			
			httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(chequeDiscountingForm.getPageId()));
			System.out.println("in cheque discounting menu---->");
			httpServletRequest.setAttribute("acountTypes", delegate.getMainModules(4, "'1002000','1007000','1018000','1017000','1014000','1015000','1001000','1003000','1004000','1005000','1012000'"));
			System.out.println("after set attr--->");
			return actionMapping.findForward(ResultHelp.getSuccess());
			
		}
		else
				return actionMapping.findForward(ResultHelp.getSuccess());
		} 
	
	catch(Exception e) 
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
	
}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/ChequeDiscountingLink"))
{
	
	
	try
	{
	ChequeDiscountingForm chequeDiscountingForm=(ChequeDiscountingForm)actionForm;
	chequeDiscountingForm.setValidateFlag("");
	if(MenuNameReader.containsKeyScreen(chequeDiscountingForm.getPageId()))
	{	
		chequeDiscountingForm.setValidateFlag("");
		String path=MenuNameReader.getScreenProperties(chequeDiscountingForm.getPageId());
		httpServletRequest.setAttribute("pageId",path);
		
		System.out.println("indide mmmmmeee"+path);
		ChequeDepositionObject[] array_chequedepositionobject;
		DiscountCharges[] array_discountcharges;
		
		 if(chequeDiscountingForm.getFlag().equalsIgnoreCase("frmAccNum"))
		 {
			System.out.println("inside iffff");
			String str=chequeDiscountingForm.getAcc_type();
			System.out.println("accctyt1111-->"+str);
			array_discountcharges=delegate.retrieveDiscountCharges();
			System.out.println("after dicount charges");
			int acNum=Integer.parseInt(chequeDiscountingForm.getAcc_no());
			System.out.println("accnum--->"+acNum);
			array_chequedepositionobject=delegate.retrieveChequeDetails(chequeDiscountingForm.getAcc_type(),acNum);
				if(array_chequedepositionobject!=null)
				{
					System.out.println("inside if");
					httpServletRequest.setAttribute("chequedetails",array_chequedepositionobject);
				}
				else
				{
					chequeDiscountingForm.setValidateFlag("Records  Not Found");
				}
				httpServletRequest.setAttribute("array_disCharges",array_discountcharges);
			} 
		 
		 
		 else 	if(chequeDiscountingForm.getFlag().equalsIgnoreCase("frmButt"))
		 	{
			 	String[] cbox=httpServletRequest.getParameterValues("cbox");
			 	
			 	if(cbox!=null)
				 {
			 		array_chequedepositionobject = new ChequeDepositionObject[(cbox.length)];
			 		String[] ctrlNum=httpServletRequest.getParameterValues("txtCtrlNum");
			 		String[] brachName=httpServletRequest.getParameterValues("txtBrhName");
			 		String[] chqDDPo=httpServletRequest.getParameterValues("txtInstType");
			 		String[] qdNo=httpServletRequest.getParameterValues("txtChqNum");
				
			 		String[] qdpDate=httpServletRequest.getParameterValues("txtChqDate");
				
			 		String[] disAmt=httpServletRequest.getParameterValues("txtDisAmt");
			 		String[] chqAmt=httpServletRequest.getParameterValues("txtChqAmt");
			 		String[] disChg=httpServletRequest.getParameterValues("txtDisChg");
			 		String[] disCountAmt=httpServletRequest.getParameterValues("txtDisCountAmt");
			 		String[] disCountChg=httpServletRequest.getParameterValues("txtDisCountChg");
				
			 		int j=0;
			 		int k=0;
				 	
					 
					 	for(;k<cbox.length;k++)
						{	
							array_chequedepositionobject[j]=new ChequeDepositionObject();
							int x=Integer.parseInt(cbox[k]);
							System.out.println("length od cd before ==>"+array_chequedepositionobject.length);
							String txt=(String)disCountAmt[x];
							if(Double.parseDouble(txt)==0)
							{
							chequeDiscountingForm.setValidateFlag( "Please Enter The Amount In The TextField At Row"+x);
							break;
							}
						
						
							if((Double.parseDouble(disAmt[x].toString())+Double.parseDouble(disChg[x].toString())+Double.parseDouble(disCountAmt[x].toString())+Double.parseDouble(disCountChg[x].toString()))>Double.parseDouble(chqAmt[x].toString()))
							{
								chequeDiscountingForm.setValidateFlag("Discount Amount Is More Than Cheque Amount By Rs."+(Double.parseDouble(disAmt[x].toString())+Double.parseDouble(disChg[x].toString())-Double.parseDouble(chqAmt[x].toString()))+" At Row "+(x));
								break;
							}
						
				 		System.out.println("of cbox[k]"+x);
				 		System.out.println("=========disCountAmt-----"+disCountAmt[x]);
				 		System.out.println("of qdNo "+qdNo[x]);
				 		System.out.println("=============ctrlNum[x]===  "+ctrlNum[x]);
				 		Long s= Long.parseLong(ctrlNum[x]);
				 		System.out.println("ctrl no"+s);
				 		array_chequedepositionobject[j].setControlNo(Long.parseLong(ctrlNum[x]));
				 		//System.out.println("ctrl no"+s);
				 		System.out.println("brachName[x]   "+brachName[x]);
				 		array_chequedepositionobject[j].setBranchName(brachName[x]);
				 		array_chequedepositionobject[j].setChqDDPO(chqDDPo[x]);
				 		array_chequedepositionobject[j].setQdpNo(Integer.parseInt(qdNo[x]));
				 		System.out.println("chq no"+qdNo[x]);
				 		array_chequedepositionobject[j].setQdpDate(qdpDate[x]);
				 		System.out.println("qdpDate[x]5555555555"+qdpDate[x]);
				 		array_chequedepositionobject[j].setActDiscAmt(Double.valueOf(disAmt[x]));
				 		array_chequedepositionobject[j].setDiscAmt(Double.valueOf(disCountAmt[x]));
				 		array_chequedepositionobject[j].setDiscChg(Double.valueOf(disCountChg[x]));
				 		System.out.println("-----------------------------before acno set");
				 		array_chequedepositionobject[j].setCreditACType(chequeDiscountingForm.getAcc_type());
				 		System.out.println("user acc type"+chequeDiscountingForm.getAcc_type());
				 		array_chequedepositionobject[j].setCreditACNo(Integer.parseInt(chequeDiscountingForm.getAcc_no()));
				 		System.out.println("acc num"+chequeDiscountingForm.getAcc_no());
				 		array_chequedepositionobject[j].setTranAmt(Double.parseDouble(chqAmt[x]));
				 		System.out.println("enter amt=============@@@@@@@@@@@@@@"+disCountAmt[x]);
				 		array_chequedepositionobject[j].setDeTml(tml);
				 		array_chequedepositionobject[j].setDeUser(uid);
						System.out.println("user id"+uv.getUserId());
						array_chequedepositionobject[j].setDe_date(delegate.getSysDate());
						array_chequedepositionobject[j].setVeTml(tml);
						array_chequedepositionobject[j].setVeUser(uid);
						array_chequedepositionobject[j].setVe_date(delegate.getSysDate());
						array_chequedepositionobject[j].setVe_datetime(delegate.getSysDate());
						System.out.println("after set");
						j++;
						System.out.println("length of object==>"+array_chequedepositionobject.length);
					}
		 		
					if(j>0)
					{
						if(delegate.chequeDiscount(array_chequedepositionobject,0,delegate.getSysDate())>0)
						{
							System.out.println("Inside if ---------------===================");
						
						chequeDiscountingForm.setValidateFlag("Cheques Discounted Successfully");
						}
						else
						{
							chequeDiscountingForm.setValidateFlag("Cheque Discounting Failed");
						}
					}
				 }
				 else
				 {
					 chequeDiscountingForm.setValidateFlag("CheckBox is Not Selected");
				 }	
			}
			
		 
		 
		else{ 
			httpServletRequest.setAttribute("acountTypes", delegate.getMainModules(4, "'1002000','1007000','1018000','1017000','1014000','1015000','1001000','1003000','1004000','1005000','1012000'"));
			return actionMapping.findForward(ResultHelp.getSuccess());
			}
	
		
	
		 	
			httpServletRequest.setAttribute("acountTypes", delegate.getMainModules(4, "'1002000','1007000','1018000','1017000','1014000','1015000','1001000','1003000','1004000','1005000','1012000'"));		
			return actionMapping.findForward(ResultHelp.getSuccess());
	
	
		}
	}
	
	catch (Exception e) 
	{
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	}
	
}	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/TestMenuLink"))
{
	
	
	try
	{
	Test testForm=(Test)actionForm;

	
	
	if(MenuNameReader.containsKeyScreen(testForm.getPageId())){
		
		
		System.out.println("Data base connectivity...thro Business Delegate method");
		
		String path=MenuNameReader.getScreenProperties(testForm.getPageId());
		
		System.out.println("Path is--->"+path);
		
		
		httpServletRequest.setAttribute("pageId",path);
		
		return actionMapping.findForward(ResultHelp.getSuccess());
	} 
	}
	catch (Exception e) {
		
		System.out.println("In catch Block");
		return actionMapping.findForward(ResultHelp.getSuccess());
	
	}
	
}
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/TestLink"))
{
	
	
	try{
	Test testForm=(Test)actionForm;

	
	
	if(MenuNameReader.containsKeyScreen(testForm.getPageId())){
		
		
		System.out.println("From Jsp page");
		
		System.out.println("Data base connectivity...thro Business Delegate method");
		
		String path=MenuNameReader.getScreenProperties(testForm.getPageId());
		
		System.out.println("Path is--->"+path);
		
		if(testForm.getFlag().equalsIgnoreCase("frmName")){
			String uname=testForm.getName();
			CustomerMasterObject custMastObj=delegate.customerDetails(uname);
			httpServletRequest.setAttribute("svname", custMastObj);
			
		}
		/*if(testForm.getFlag().equalsIgnoreCase("frmUser")){
			
			httpServletRequest.setAttribute("svname", testForm.getUser());
		}*/
		
		
		String name=testForm.getName();
		String user=testForm.getUser();
		System.out.println("name--->"+name);
		httpServletRequest.setAttribute("pageId",path);
		
		return actionMapping.findForward(ResultHelp.getSuccess());
	} 
	}
	catch (Exception e) {
		
		System.out.println("In catch Block");
		return actionMapping.findForward(ResultHelp.getSuccess());
	
	}
	
}	
	
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/CombinedChequeMLink"))
{
	
	
	try
	{
	CombinedChequeForm comcheque =(CombinedChequeForm)actionForm;

	
	
	if(MenuNameReader.containsKeyScreen(comcheque.getPageId())){
		
		
		System.out.println("Data base connectivity...thro Business Delegate method");
		
		String path=MenuNameReader.getScreenProperties(comcheque.getPageId());
		
		System.out.println("Path is--->"+path);
		
		
		httpServletRequest.setAttribute("pageId",path);
		
		return actionMapping.findForward(ResultHelp.getSuccess());
	} 
	}
	catch (Exception e) {
		
		System.out.println("In catch Block");
		return actionMapping.findForward(ResultHelp.getSuccess());
	
	}
	
}
	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/CombinedChequeLink"))
{
	
	    
	try
	{
		CombinedChequeForm combinedChequeForm =(CombinedChequeForm)actionForm;
		CompanyMaster[] companyMasters=null;
		combinedChequeForm.setValidateFlag("");
		combinedChequeForm.setFormflag("");
		double tot_amount;
		String me=null;
		String addme=null;
		if(MenuNameReader.containsKeyScreen(combinedChequeForm.getPageId()))
		{	
			
		if(combinedChequeForm.getCtrlno()!=null && Integer.parseInt(combinedChequeForm.getCtrlno())!=0)
		{	
			module = ClearingDelegate.getInstance().getMainModule(4);
			loan_module = ClearingDelegate.getInstance().getMainModule(3);
		
			System.out.println("From Jsp page");
			int ctrl_no=Integer.parseInt(combinedChequeForm.getCtrlno());
			if(ctrl_no!=0 && ctrl_no>0)
			{
				System.out.println("hi before  retrieve");
				try
				{
				companyMasters =delegate.retrieveChqDtls(ctrl_no,1);
				
				System.out.println("hi after retrieve");
				if(companyMasters!=null)
				{
					System.out.println("inside not null");	
					if(companyMasters[0].getVeUser()!= null)
					{	
						System.out.println("inside veuser");
						combinedChequeForm.setValidateFlag("Control Number Is Already Verified");
						combinedChequeForm.setCtrlno("");
					}
					else
					{	
						System.out.println("inside veuser else");
						tot_amount=Double.parseDouble(companyMasters[0].getDeUser());
						combinedChequeForm.setTotamount(companyMasters[0].getDeUser());
						//combinedChequeForm.setAccTyp(companyMasters[0].getAccType());
						httpServletRequest.setAttribute("chqdetails",companyMasters);
						System.out.println("hi after retrieve inside else");
						System.out.println("Length of companyMasters is  "+companyMasters.length);
						
					}	
				}
				else
				{
					combinedChequeForm.setValidateFlag("This Control Number Is Not A Combined Cheque");
					combinedChequeForm.setCtrlno("");
				}
			
			}
			catch(RecordsNotFoundException e)
			{
					combinedChequeForm.setValidateFlag("Records Not Found");
			}
		
			}
		}
		if(combinedChequeForm.getFlag().equalsIgnoreCase("frmSubmit"))
		{
			double  double_sum=0;
			String AccountFlag="";
			String[] cbox=(String[])httpServletRequest.getParameterValues("chkBox");
			
			if(cbox!=null)
			{
					if(cbox.length>0)
					{	
						String acctype="";
						String loantype="";
						companyMasters=new CompanyMaster[cbox.length];
						String[] accTyp=httpServletRequest.getParameterValues("accTyp");
						String[] accNum=httpServletRequest.getParameterValues("accNum");
						//String[] Name=httpServletRequest.getParameterValues("companyName");
						String[] amount=httpServletRequest.getParameterValues("amount");
						String[] loanActyp=httpServletRequest.getParameterValues("loanActyp");
						String[] loanAcnum=httpServletRequest.getParameterValues("loanAcnum");
						
//						double_sum=double_sum+Double.parseDouble(amount);
						
						System.out.println("inside if");
						
						if(accNum!=null || amount!=null)
						{
							
							for(int i=0,j=0;i<cbox.length;i++)
							{
								companyMasters[j]=new CompanyMaster();
							
								System.out.println("inside for"+cbox[i]);
								
								int x=Integer.parseInt(cbox[i].trim());
								
								System.out.println("......,"+x);
								System.out.println("******1234***"+accTyp[x]);
								System.out.println(" accNum[x]==============="+ Long.parseLong(accNum[x]));
								//System.out.println("company name"+Name[x]);
								System.out.println("amount==========="+amount[x]);
								System.out.println("setting acc Type----"+accTyp[x]+"companyMasters.length==="+companyMasters.length);
								
								if(combinedChequeForm.getBooleanFlag()==0)
								{
									for(int k=0;k<module.length;k++)
									{	
										if((module[k].getModuleAbbrv().equalsIgnoreCase(accTyp[x])))
										{
											acctype=module[k].getModuleCode();
										}
									}	
								}
								else if(combinedChequeForm.getBooleanFlag()==1)
								{
									acctype=accTyp[x];
								}
								companyMasters[j].setAccType(acctype);
								
								if(accNum[x]!=null)
								{
									AccountObject acc_obj = delegate.getAccount(acctype,Integer.parseInt(accNum[x]),delegate.getSysDate());
									
								  	if(acc_obj == null)
								  	{
								  		System.out.println("inside account object-----baluuuu");
								  		combinedChequeForm.setValidateFlag("Account Number Not Found");
								  		AccountFlag="NOTFOUND";
								  	} 
								  	else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
								  	{
								  		combinedChequeForm.setValidateFlag("Account Is Closed" );
								  		AccountFlag="NOTFOUND";
								  	}
								  	else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
								  	{
								  		combinedChequeForm.setValidateFlag("Account Is Freezed" );
								  		AccountFlag="NOTFOUND";
								  	}
								  	else
								  	{
								  		companyMasters[j].setAccNo(Integer.parseInt(accNum[x]));
								  		AccountFlag="";
								  	}
								}
								else
								{
									combinedChequeForm.setValidateFlag("Account Number Not Entered");
								}
								
								double_sum=double_sum+Double.parseDouble(amount[x]);
								
								if(combinedChequeForm.getBooleanFlag()==1)
								{
									companyMasters[j].setCompanyName("1");
								}
								else if(combinedChequeForm.getBooleanFlag()==0)
								{
									companyMasters[j].setCompanyName("0");
									System.out.println("0");
								}
								for(int k=0;k<loan_module.length;k++)
								{	
									if((loan_module[k].getModuleAbbrv().equalsIgnoreCase(loanActyp[x])))
									{
										loantype=loan_module[k].getModuleCode();
									}
								}	
								companyMasters[j].setLoanAccType(loantype);
								companyMasters[j].setLoanAccNo(Integer.parseInt(loanAcnum[x]));
								
								companyMasters[j].setVeUser(amount[x]);	
								if(combinedChequeForm.getBooleanFlag()==1)
								{
									companyMasters[j].setVeTml(tml);
									companyMasters[j].setVeTime(delegate.getSysDate()+" "+delegate.getSysTime());
								}
								else if(combinedChequeForm.getBooleanFlag()==0)
								{	
									companyMasters[j].setDeTml(tml);
									companyMasters[j].setDeUser(uid);
									companyMasters[j].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
								}
							
								companyMasters[j].setDate( delegate.getSysDate());
								companyMasters[j].setDe_date(delegate.getSysDate());
								System.out.println("after settingg value");
								j++;
							}
							
						}
						else
						{
							combinedChequeForm.setValidateFlag("Some Values Are Missing");
						}
						if(!(AccountFlag.equalsIgnoreCase("NOTFOUND")))
						{
							if(double_sum == Double.parseDouble(combinedChequeForm.getTotamount()))
							{	
								if(delegate.storeChequeData(companyMasters,Integer.parseInt(combinedChequeForm.getCtrlno()))>=cbox.length)
								{
									if(combinedChequeForm.getBooleanFlag()==0)
									{
										combinedChequeForm.setValidateFlag("Cheques Deposited Successfully");
									}
									else if(combinedChequeForm.getBooleanFlag()==1)
									{
										combinedChequeForm.setValidateFlag("Cheques Verified Successfully");
									}
								}	
								else
									
								{
									if(combinedChequeForm.getBooleanFlag()==0)
									{
										combinedChequeForm.setValidateFlag("Cheques Deposition Failed");
									}
									else if(combinedChequeForm.getBooleanFlag()==1)
									{
										combinedChequeForm.setValidateFlag("Cheques Verification Failed");
									}
								}	
						    }
							else if(double_sum<Double.parseDouble(combinedChequeForm.getTotamount()))
							{
									double_sum=Double.parseDouble(combinedChequeForm.getTotamount())-double_sum;
									combinedChequeForm.setValidateFlag("Amount Is Less Than Cheque Amount By Rs "+double_sum);
							}
							else
							{
									double_sum=double_sum-Double.parseDouble(combinedChequeForm.getTotamount());
									combinedChequeForm.setValidateFlag("Amount Is More Than Cheque Amount By Rs "+double_sum);
							}
						}
						else
						{
							combinedChequeForm.setValidateFlag("Enter Valid Account Number");
						}
						
						
				}
			}
			else
			{
				combinedChequeForm.setValidateFlag("CheckBox Is Not Selected");
			}
			
		}
		else if(combinedChequeForm.getFlag().equalsIgnoreCase("addRow"))
		{	
			System.out.println("inside add row");
			addme="added";
			httpServletRequest.setAttribute("addrow",addme);
		}
		else if(combinedChequeForm.getFlag().equalsIgnoreCase("removeRow"))
		{
			addme="";
			httpServletRequest.setAttribute("addrow",addme);
		}
		else if(combinedChequeForm.getFlag().equalsIgnoreCase("accNum"))
		{
			AccountObject acc_obj = delegate.getAccount(  combinedChequeForm.getAccTyp(), Integer.parseInt(combinedChequeForm.getAccNum()), "12/02/2007");
			
			if ( acc_obj == null ){
				
				
				combinedChequeForm.setValidateFlag("No Such Account Found With Account Number   " + combinedChequeForm.getAccNum());
				
			} 
			else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
			{
				
				combinedChequeForm.setValidateFlag("Account Is Closed");
		
			} 
			else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
			{
				
				combinedChequeForm.setValidateFlag("Account Is Freezed");
		
			}
			addme="added";
			httpServletRequest.setAttribute("addrow",addme);
		}
		httpServletRequest.setAttribute("Loan_Module_code", loan_module);
		httpServletRequest.setAttribute("Main_Module_code", module);
		String path=MenuNameReader.getScreenProperties(combinedChequeForm.getPageId());	
		System.out.println("Path is---->"+path);
		httpServletRequest.setAttribute("pageId",path);
		return actionMapping.findForward(ResultHelp.getSuccess());
	} 
	}
	catch (Exception e) {
		
		System.out.println("In catch Block");
		e.printStackTrace();
		return actionMapping.findForward(ResultHelp.getSuccess());
	
	}
	
}	
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Bouncedreglink"))
{
	try{
		 System.out.println("*********OutwardStatement*************");
		 BouncedregForm bouncedForm=(BouncedregForm)actionForm;
		 httpServletRequest.setAttribute("clrdate", ClearingDelegate.getSysDate());
		 
	     if(MenuNameReader.containsKeyScreen(bouncedForm.getPageId()))
			{
	    	 	path =MenuNameReader.getScreenProperties(bouncedForm.getPageId());
				httpServletRequest.setAttribute("pageId",path);
				setInwardstmtForm(httpServletRequest,path,delegate);
				
				return actionMapping.findForward(ResultHelp.getSuccess());

			}
	     else
	     	{
	    	 return actionMapping.findForward(ResultHelp.getError());
	     	}
		}catch (Exception e) 
			{		
		       e.printStackTrace(); 
	    	   return actionMapping.findForward(ResultHelp.getSuccess());
			}
	      
}
else if(actionMapping.getPath().trim().equalsIgnoreCase("/Clearing/Bouncedreg"))
{
	try
	{
		BouncedregForm bouncedForm=(BouncedregForm)actionForm;
		if(MenuNameReader.containsKeyScreen(bouncedForm.getPageId()))
		{
			path =MenuNameReader.getScreenProperties(bouncedForm.getPageId());
			httpServletRequest.setAttribute("pageId",path);
			httpServletRequest.setAttribute("clrdate", ClearingDelegate.getSysDate());
			return actionMapping.findForward(ResultHelp.getSuccess());
		}
		else
		{
			return actionMapping.findForward(ResultHelp.getError());
		}
	}
	catch (Exception e) 
	{		
       e.printStackTrace(); 
	   return actionMapping.findForward(ResultHelp.getSuccess());
	}
}

else if(actionMapping.getPath().trim().equalsIgnoreCase("/DummyLink"))
{
	
	try
	{
		System.out.println("-------->");
		if(actionForm!=null)
		{
			System.out.println("-------->");
		}
		
	}
	catch(Exception e)
	{
	
		e.printStackTrace();
	}
	
}
	
	return null;
	
}
	private void setComboBundleType(int ack_no, String str, int opt) 
	{
		BundledDataEntry bundledDataEntry;
		if((ack_no%1111)==0)
		{
			
//			if ( str.equalsIgnoreCase("I"))
//				bundledDataEntry.setBundleType("InWard");
//			else if ( str.equalsIgnoreCase("D"))
//				bundledDataEntry.setBundleType("Dedit ECS");
//			else if ( str.equalsIgnoreCase("C"))
//				bundledDataEntry.setBundleType("Credit ECS");
//			
			String[] clgTypes={"I","D","C"};
		}
		else
		{
			String[] clgTypes={"O","D","C"};
		}
		
	}
	private void setInwardstmtForm(HttpServletRequest req, String path,ClearingDelegate delegate) throws Exception
	{
		try 
		{
			req.setAttribute("pageId", path);
		} 
		catch(Exception e) 
		{
			throw e;
		}
	}
	
	private void setPersonalDetails(HttpServletRequest req,String ac_type,int ac_no )
	{
		try 
		{
			AccountObject acc_obj = delegate.getAccount(  ac_type, ac_no, "12/02/2007");
			if(acc_obj!=null) 
			{
				CustomerMasterObject cust = delegate.getCustomerDetail( acc_obj.getCid() );
				req.setAttribute("personalInfo", cust);
			}
		} 
		catch(Exception e)
		{
				e.printStackTrace();
		}
	}
	
	private void getSignatureDetails(HttpServletRequest req, String ac_type, int ac_no)
	{
		try
		{
			SignatureInstructionObject[] sigObject=(SignatureInstructionObject[])delegate.getSignatureDetails(ac_no,ac_type);
			if(sigObject!=null)
			{
				req.setAttribute("signInfo",sigObject);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private ModuleObject[] setDebitAccountDetail()
	{
			ModuleObject[] module_debit = ClearingDelegate.getInstance().getMainModule(2);
			return module_debit;
	}
	
	private ClearingDepositionForm getDebitDetail( ClearingDepositionForm form ,HttpServletRequest httpServletRequest )
	{
		
		System.out.println( form.getDebit_account_type() + ",.,.,.,.,.,.,...,.,.,." +  form.getDebit_account_no());
		AccountObject acc_obj = delegate.getAccount(form.getDebit_account_type(),form.getDebit_account_no(), "12/02/2007");
		
		if(acc_obj==null )
		{
			form.setError_message("No Such Account Found with Account No   " + form.getCredit_account_no());
			form.setDebit_account_no(0);
			form.setBalance(0.0);
			form.setShodowbalance(0.0);
		} 
		else if (acc_obj.getAccStatus().equalsIgnoreCase("C"))
		{
			form.setError_message("Account is closed" );
			form.setDebit_account_no(0);
			form.setBalance(0.0);
			form.setShodowbalance(0.0);
		} 
		else if (acc_obj.getFreezeInd().equalsIgnoreCase("T"))
		{
			form.setError_message("Account is Freezed");
			form.setDebit_account_no(0);
			form.setBalance(0.0);
			form.setShodowbalance(0.0);
		} 
		else 
		{
			double min_bal = 0.0;
			
			form.setBalance(acc_obj.getAmount());
			form.setShodowbalance(acc_obj.getShadowBalance());
			if(module_debit == null)
			{
				System.out.println(" inside the module_debit null ");
				module_debit = setDebitAccountDetail();
			}
			for(int i=0;i<module_debit.length;i++)
			{
				if(module_debit[i].getModuleCode().equalsIgnoreCase(form.getDebit_account_type()))
				{
					min_bal = module_debit[i].getMinBal();
					System.out.println( min_bal + " of the given account ");
					break;
				}
			}
			if(form.getShodowbalance() == 0.0 ) 
			{
				if(form.getAmount()>(form.getBalance()-min_bal))
				{
					System.out.println("we will  .....................1:-) :-) :-) :-) :-) ");
					form.setError_message("Cheque is Going to Bounce due to InSuffient Balance..   " );
					System.out.println( form.getError_flag() + form.getError_message() + "Error......");
				}
				
			} 
			else 
			{
				if(form.getAmount()>(form.getBalance() - min_bal)) 
				{
					System.out.println("we will  .....................2");
					if(form.getAmount()<(form.getBalance()+form.getShodowbalance()-min_bal ))
					{
						System.out.println("we will  .....................3");
						
						form.setError_message("Effects not Cleared..Present  Again" );
					}
				}
			}
			CustomerMasterObject cust = delegate.getCustomerDetail(acc_obj.getCid());
			httpServletRequest.setAttribute("personalInfo", cust);
			
		}
		return form;
	}
	
	private void getChequeDetail(ClearingDepositionForm form ,HttpServletRequest httpServletRequest)
	{
		Vector vec = delegate.getChequeDetail(form.getDebit_account_type(),form.getDebit_account_no(),form.getDebit_chequeno()) ;
		
		if(vec!=null &&  vec.size()>0)
		{
			
				Iterator itr =  vec.iterator();
				
				while(itr.hasNext())
				{
					masterObject.frontCounter.ChequeObject chq_obj = (masterObject.frontCounter.ChequeObject)itr.next();
					
					 if(chq_obj.getCheque_Del().equals("T"))
					 {
						form.setError_message("Cheque Already Used/Closed" );
					 }       
	                 else if(chq_obj.getStop_payment().equals("T"))
	                 {
	                	 form.setError_message("Cheque Date Expired Refer to Drawer.." );
	                 }
	                 else if(Validations.dayCompare(form.getToday_date(),chq_obj.getExpDate())< 0)
	                 {
	                	 form.setError_message("Payment Stopped by Drawer" );
	                	 
	                 }
				}
		} 
		else 
		{
			form.setError_message("Cheque No Not Found For This Account" );
			form.setChequeno(0);
		}
	
	}
	
private void getChequeDetail(RecievedChequeEntryForm form ,HttpServletRequest httpServletRequest)
{
		
		Vector vec=delegate.getChequeDetail(form.getDebit_account_type(),form.getDebit_account_no(),form.getDebit_chequeno()) ;
		
		if(vec!=null && vec.size()>0)
		{
			
				Iterator itr =  vec.iterator();
				
				while(itr.hasNext())
				{
					
					masterObject.frontCounter.ChequeObject chq_obj=(masterObject.frontCounter.ChequeObject)itr.next();
					
					 if(chq_obj.getCheque_Del().equals("T"))
					 {
						form.setError_message("Cheque Already Used/Closed" );
					 }       
	                 else if(chq_obj.getStop_payment().equals("T"))
	                 {
	                	 form.setError_message("Cheque Date Expired Refer to Drawer");
	                 }
	                 else if(Validations.dayCompare(form.getToday_date(),chq_obj.getExpDate())< 0)
	                 {
	                	 form.setError_message("Payment Stopped by Drawer" );
	                 }
	        	}
			
		} 
		else 
		{
			form.setError_message("Cheque No Not Found For This Account" );
		}
	
	}
	
	private int setControlNo(ClearingDepositionForm form ,HttpServletRequest httpServletRequest,String tml,String uid)
	{
		
		int res = 0 ;
		System.out.println(form.getCtrl_no()+ "controlno ");
		System.out.println(form.getReason_codes()+"reasoncode");
		ChequeDepositionObject[] chq_dep_obj = new ChequeDepositionObject[1];
		chq_dep_obj[0] = new ChequeDepositionObject();
		chq_dep_obj[0].setControlNo( form.getCtrl_no());
		if(form.getOutstation_chq()!= null)
		{
			chq_dep_obj[0].setClgType("S");
		}
		else 
		{
			chq_dep_obj[0].setClgType("O");
		}
		BankMaster[]  bm =null;
		
		if(form.getInternal_clearing()!= null )
		{
			System.out.println("inside internal clearing");
			bm = delegate.getBankDetails(-5, 0, 0);
			chq_dep_obj[0].setBankCode("000000001");
			chq_dep_obj[0].setQdpDate(form.getDebit_chq_date());
			chq_dep_obj[0].setBranchName(form.getBranchname());
			chq_dep_obj[0].setDocDestination(1);
			//chq_dep_obj[0].setDocDestination(Integer.parseInt(form.getBranchname()));
			System.out.println("after bank details");
			
		}
		else 
		{
			System.out.println("inside intrl clg null");
			String code1=(form.getCity_code()+form.getBank_code()+form.getBranch_code());
			System.out.println("micre code--FSFSFSFWSFSWGWTRW---"+code1);
			chq_dep_obj[0].setBankCode(form.getCity_code()+form.getBank_code()+form.getBranch_code());	
			chq_dep_obj[0].setBranchName(form.getBranchname());
			bm = delegate.getBankDetails(-2,0,0);
			chq_dep_obj[0].setDocDestination(9999);
		}
		// for Setting Document Source as Head Office 1  
		 chq_dep_obj[0].setDocSource(1);
		
		//chq_dep_obj[0].setDocSource(Integer.parseInt(bm[0].getDeTml()));
		if(form.getMulticredit()!= null)
		{
			chq_dep_obj[0].setMultiCredit("T");
			chq_dep_obj[0].setCompanyName(form.getCname());					
		}
		else
		{
			chq_dep_obj[0].setMultiCredit("F");
			chq_dep_obj[0].setCreditACType( form.getCredit_account_type());
		
				if(form.getCredit_account_type().startsWith("1016"))
				{
					int val = ((Integer)delegate.getCommission( form.getDebit_account_type() , form.getDebit_account_no(), form.getCredit_account_type(), form.getAmount(), form.getToday_date(),0)).intValue();  
					chq_dep_obj[0].setCreditACNo(val);
					chq_dep_obj[0].setPOCommission( form.getPocommision() );
				}
				else 
				{
					chq_dep_obj[0].setCreditACNo(  form.getCredit_account_no() );
				}
		}
		if(form.getLaon_credit()!= null)
		{
			chq_dep_obj[0].setLoanAcc( Integer.toString( form.getLaon_credit_ac_type()));
			chq_dep_obj[0].setLoanAcc_No(form.getLaon_credit_no());
		}
			chq_dep_obj[0].setTranAmt(form.getAmount());
			chq_dep_obj[0].setPayeeName(form.getPaydetail());
		
		if(form.getDiscount()!=null)
		{
			chq_dep_obj[0].setDiscInd("T");
			chq_dep_obj[0].setDiscAmt(form.getDiscountamount());
			chq_dep_obj[0].setDiscChg(form.getDiscountcharge());
		} 
		else 
			chq_dep_obj[0].setDiscInd("F");
		
		if(form.getBounce()!= null)
		{
			System.out.println("inside bounce");
			chq_dep_obj[0].setBounceInd("T");
			fine_amount=0.0;
//			System.out.println( form.getReason_codes()  +  "selected reason codes" );
//			
//			StringTokenizer tok = new StringTokenizer ( form.getReason_codes(), "," );
//	
//			Vector vec = new Vector();					
//			
//			while ( tok.hasMoreTokens() ) 
//			{
//				vec.add( tok.nextToken() );
//			} 
			String[] cbox=httpServletRequest.getParameterValues("chkBox");
			String[] bounceFine=httpServletRequest.getParameterValues("txtBounceFine");
			String[] description=httpServletRequest.getParameterValues("txtDesription");
			String[] reasonCode=httpServletRequest.getParameterValues("txtReasonCode");
//			String string_acc_type="";
//			string_acc_type = String.valueOf(module[Integer.parseInt(form.getCredit_account_type())].getModuleCode());
//			array_reason=delegate.getReasonDetails(string_acc_type,0);
			if(cbox!=null)
			{		
				for(int k=0;k<cbox.length;k++)
				{
					int x=Integer.parseInt(cbox[k]);
					vector_all_reasons.add(reasonCode[x]);
					fine_amount+=Double.parseDouble(bounceFine[x]);
				}
			}
			else
			{
				form.setError_message("CheckBox Is Not Selected");
			}
			System.out.println("fineeeee===="+fine_amount);
			chq_dep_obj[0].setFineAmt(fine_amount);
			chq_dep_obj[0].setReasonArray( vector_all_reasons );
			System.out.println("set values");
		} 
		else
		{
			chq_dep_obj[0].setBounceInd("F");
		}
		
		chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		if(form.getInternal_clearing() == null)  
		{
			System.out.println("cheque no---"+form.getChequeno()+"==che date---"+form.getChequedate());
				chq_dep_obj[0].setQdpNo(form.getChequeno());
				chq_dep_obj[0].setQdpDate(form.getChequedate());           
		}
		else 
		{		
					if(form.getChqddpo().equalsIgnoreCase("P")) 
					{
						chq_dep_obj[0].setQdpNo(form.getPay_order_no() );
						chq_dep_obj[0].setQdpDate(form.getPo_date());
					} 
					else if(form.getChqddpo().equalsIgnoreCase("C")) 
					{
						chq_dep_obj[0].setDebitACType(form.getDebit_account_type());
						chq_dep_obj[0].setDebitACNo(form.getDebit_account_no());
						chq_dep_obj[0].setQdpNo(form.getDebit_chequeno());
						chq_dep_obj[0].setQdpDate(form.getDebit_chq_date());
					}
		}
			
			if(form.getBooleanFlag()==0)
			{
				chq_dep_obj[0].setChqDDPO( form.getChqddpo());
				chq_dep_obj[0].setDeTml(tml);
				chq_dep_obj[0].setDeUser(uid);
				chq_dep_obj[0].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
			}
			
			else if(form.getBooleanFlag()==1)
			{
				chq_dep_obj[0].setVeTml(tml);
				chq_dep_obj[0].setVeUser(uid);
				chq_dep_obj[0].setVe_date(ClearingDelegate.getSysDate());
				chq_dep_obj[0].setVe_datetime(ClearingDelegate.getSysDate());
			}
			System.out.println( form.getBounce() +  "---" + form.getReason_codes()  + "-----*************----"  );
			System.out.println( chq_dep_obj[0].getBounceInd()  +  "------------------"+ chq_dep_obj[0].getReasonArray()  );
			res = delegate.storeChequeData(chq_dep_obj[0]);
			if(form.getBooleanFlag()==0)
			{
				return res;
				
			}
			else if((form.getCtrl_no())>0 && form.getBooleanFlag()==1)
	        {
				System.out.println("Inside Verify--------BAla");
	                try
	                {
	                 	if(form.getMulticredit()==null && form.getCredit_account_type().startsWith("1016"))
	                	{
	                 			chq_dep_obj[0].setDe_date(ClearingDelegate.getSysTime());
	                			int pay_order_no = delegate.createPayOrder(chq_dep_obj[0]);
	                			if(pay_order_no != 0)
	                			{
	                				form.setError_flag(1);
	                				form.setError_message("Please note down PayOrder No " + pay_order_no);
	                			}
	                			else
	                				form.setError_message("Problem in PayOrder No Creation ");
	                	}
	                 	else 
	                 	{    				                	
	                			long ctrl_no = chq_dep_obj[0].getControlNo();
	                			int k=delegate.onVerification(ctrl_no,tml,uid,ClearingDelegate.getSysDate());
	                			if(k!=0)
	                			{
	                				form.setError_flag(1);
	                				System.out.println("Successfully Verified iiiiii");
	                				form.setError_message("Successfully Verified");
	                			}
	                	}
	                
	                }
	                catch(Exception ae)
					{
	                	ae.printStackTrace();
					}
	                if(chq_dep_obj[0].getDiscAmt() >0.0)
	                {
	                	try
	                	{
	                		delegate.chequeDiscount(chq_dep_obj,0,ClearingDelegate.getSysDate());	
	                	}
	                	catch (Exception e)
	                	{
							e.printStackTrace();
						}
	                }
	               
	               
	            }
			else
	            {
					
					form.setError_message("Error While Verifying");
	            }
			
		
		return res;
	}
      
	private long setInwardControlNo(ClearingDepositionForm form ,HttpServletRequest httpServletRequest)
	{
		
		long r  = 0;
		
		System.out.println(  form.getCtrl_no() + "control  no " );
		System.out.println(   form.getReason_codes() + " reason code ");
		ChequeDepositionObject[] chq_dep_obj = new ChequeDepositionObject[1];
		chq_dep_obj[0] = new ChequeDepositionObject();
		chq_dep_obj[0].setControlNo( form.getCtrl_no()  );
		chq_dep_obj[0].setAckNo( form.getAckno() );
		chq_dep_obj[0].setSendTo(  Integer.parseInt(form.getSend_to() ));
		chq_dep_obj[0].setCompanyName( form.getSource_name() );
		chq_dep_obj[0].setClgType("I");
		BankMaster[]  bm = null;
		{
			chq_dep_obj[0].setBankCode( form.getMicrcode() );	
			chq_dep_obj[0].setBranchName( form.getBankname() );
		}
		if(form.getReturn_type().equalsIgnoreCase("N")) 
		{
			chq_dep_obj[0].setCreditACType("N");
			chq_dep_obj[0].setCreditACNo(0);
		} 
		else
		{
			chq_dep_obj[0].setCreditACType("R");
			chq_dep_obj[0].setCreditACNo( form.getPrev_ctrl_no()  );
		}
		
		 if(form.getChqddpo().equalsIgnoreCase("C")) 
		 {
			 chq_dep_obj[0].setDebitACType(form.getDebit_account_type());
			 chq_dep_obj[0].setDebitACNo(form.getDebit_account_no());
			 chq_dep_obj[0].setQdpNo(form.getDebit_chequeno());
			 chq_dep_obj[0].setQdpDate(form.getDebit_chq_date());
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
			 
		 } 
		 else if(form.getChqddpo().equalsIgnoreCase("P") && form.getPrev_ctrl_no() > 0  ) {
			 
			 chq_dep_obj[0].setDebitACType(form.getDebit_account_type());
			 chq_dep_obj[0].setDebitACNo(form.getDebit_account_no());
			 chq_dep_obj[0].setQdpNo(form.getDebit_chequeno());
			 chq_dep_obj[0].setQdpDate(form.getDebit_chq_date());
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		 
		 } 
		 else if(form.getChqddpo().equalsIgnoreCase("P") && form.getPrev_ctrl_no()==0) 
		 {
			 chq_dep_obj[0].setQdpNo( form.getPay_order_no() );
			 chq_dep_obj[0].setQdpDate ( form.getPo_date()  )  ;
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		 }
		
		 chq_dep_obj[0].setTranAmt(form.getAmount());
		 chq_dep_obj[0].setPayeeName(form.getPaydetail());
		 
		 	if(form.getBounce()!= null)
		 	{
				
				chq_dep_obj[0].setBounceInd("T");
				System.out.println( form.getReason_codes()  +  "  selected reason codes  "  );
				StringTokenizer tok = new StringTokenizer ( form.getReason_codes(), "," );
				Vector vec = new Vector();					
				
				while(tok.hasMoreTokens()) 
				{
					vec.add( tok.nextToken() );
				} 
				System.out.println(  vec + " reason codes   ----------------  " );
				chq_dep_obj[0].setReasonArray(vec);
				chq_dep_obj[0].setFineAmt(0.00);
				chq_dep_obj[0].setCreditACType("R");
				
			} 
		 	else  
		 	{
					chq_dep_obj[0].setBounceInd("F");
					chq_dep_obj[0].setFineAmt(0.00);
			}
			chq_dep_obj[0].setDeTml("CA99");
			chq_dep_obj[0].setDeUser("1044");
			chq_dep_obj[0].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
		
			System.out.println( form.getBounce() +  "---" + form.getReason_codes()  + "-----*************----"  );
			r = delegate.storeInwardData(chq_dep_obj[0]);
		
		
		return r;
}
	
	
	
	
	
private long setInwardControlNo(RecievedChequeEntryForm form ,HttpServletRequest httpServletRequest)
{
		
		long r  = 0;
		
		
		System.out.println(  form.getCtrl_no() + "control  no " );
		System.out.println(   form.getReason_codes() + " reason code ");
		
		
		ChequeDepositionObject[] chq_dep_obj = new ChequeDepositionObject[1];
		
		chq_dep_obj[0] = new ChequeDepositionObject();
		
		chq_dep_obj[0].setControlNo(form.getCtrl_no());
		
		chq_dep_obj[0].setAckNo(form.getAckno());
		chq_dep_obj[0].setSendTo(Integer.parseInt(form.getSend_to()));
		chq_dep_obj[0].setCompanyName(form.getSource_name());
		chq_dep_obj[0].setClgType("I");
		
		BankMaster[]  bm = null;
		{
			chq_dep_obj[0].setBankCode( form.getMicrcode() );	
			chq_dep_obj[0].setBranchName( form.getBankname() );
		}
		if(form.getReturn_type().equalsIgnoreCase("N")) 
		{
			chq_dep_obj[0].setCreditACType("N");
			chq_dep_obj[0].setCreditACNo(0);
		} 
		else 
		{
			chq_dep_obj[0].setCreditACType("R");
			chq_dep_obj[0].setCreditACNo((int)form.getPrev_ctrl_no()  );
		}
		 if(form.getChqddpo().equalsIgnoreCase("C")) 
		 {
			 chq_dep_obj[0].setDebitACType(  form.getDebit_account_type() );
			 chq_dep_obj[0].setDebitACNo(  form.getDebit_account_no()  );
			 chq_dep_obj[0].setQdpNo( form.getDebit_chequeno() );
			 chq_dep_obj[0].setQdpDate ( form.getDebit_chq_date()  )  ;
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		 } 
		 else if(form.getChqddpo().equalsIgnoreCase("P") && form.getPrev_ctrl_no() > 0) 
		 {
			 chq_dep_obj[0].setDebitACType(form.getDebit_account_type());
			 chq_dep_obj[0].setDebitACNo(form.getDebit_account_no());
			 chq_dep_obj[0].setQdpNo(form.getDebit_chequeno());
			 chq_dep_obj[0].setQdpDate(form.getDebit_chq_date())  ;
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		 
		 } 
		 else if(form.getChqddpo().equalsIgnoreCase("P") && form.getPrev_ctrl_no()==0) 
		 {
//			 chq_dep_obj[0].setQdpNo( form.getPay_order_no() );
//			 chq_dep_obj[0].setQdpDate ( form.getPo_date()  )  ;
			 chq_dep_obj[0].setChqDDPO(form.getChqddpo());
		 }
		 	chq_dep_obj[0].setTranAmt(form.getAmount());
		 	chq_dep_obj[0].setPayeeName(form.getPaydetail());
		 
		 	if(form.isBounce())
		 	{
				chq_dep_obj[0].setBounceInd("T");
				System.out.println( form.getReason_codes()  +  "  selected reason codes  "  );
				StringTokenizer tok = new StringTokenizer(form.getReason_codes(),",");
				Vector vec = new Vector();					
				while(tok.hasMoreTokens()) 
				{
					vec.add( tok.nextToken() );
				} 
				System.out.println(  vec + " reason codes   ----------------  " );
				chq_dep_obj[0].setReasonArray( vec );
				chq_dep_obj[0].setFineAmt(0.00);
				chq_dep_obj[0].setCreditACType("R");
				
			} 
		 	else  
		 	{
					chq_dep_obj[0].setBounceInd("F");
					chq_dep_obj[0].setFineAmt(0.00);
			}
			chq_dep_obj[0].setDeTml("CA99");
			chq_dep_obj[0].setDeUser("1044");
			chq_dep_obj[0].setDeTime(delegate.getSysDate()+" "+delegate.getSysTime());
			System.out.println( chq_dep_obj[0].getBounceInd()  +  "------------------"+ chq_dep_obj[0].getReasonArray()  );
			r = delegate.storeInwardData(chq_dep_obj[0]);
		
		
		return r;
	}
/*private void getNewQuery(InwardstmtForm Inwdstmtform,HttpServletRequest request)
{
	if((Inwdstmtform.getBank_name()!=null) && (Inwdstmtform.getClr_date()!=null)&& (Inwdstmtform!=null))
	{
		try{
			System.out.println("inside method "+string_qry);
		    //calling the server method to get all the data.
			array_clearingobject = delegate.getInwardStatement(Validations.checkDate(Inwdstmtform.getClr_date()),Integer.parseInt(Inwdstmtform.getClr_no()),Integer.parseInt(Inwdstmtform.getBank_name()),string_qry);
			request.setAttribute("arryclearobject",array_clearingobject);
			if(array_clearingobject != null)
			   System.out.println("display....");
			else
				System.out.println("No Rows Found");
			}catch(RecordsNotFoundException notfound)
			{
				System.out.println("No Rows Found");
			}
		
			catch (Exception ex) {System.out.println(ex);}
			
	

	}
}*/

private List getNewQuery(InwardstmtForm Inwdstmtform,HttpServletRequest request)
{
	List inward_list=null;
	System.out.println("##########################getnew query");
	if((Inwdstmtform.getBank_name()!=null) && (Inwdstmtform.getClr_date()!=null)&& (Inwdstmtform!=null))
	{
		try{
			
			module=delegate.getMainModule(4);
			System.out.println("inside method "+string_qry);
		    //calling the server method to get all the data.
			array_clearingobject = delegate.getInwardStatement(Inwdstmtform.getClr_date(),Integer.parseInt(Inwdstmtform.getClr_no()),Integer.parseInt(Inwdstmtform.getBank_name()),string_qry);
			request.setAttribute("arryclearobject",array_clearingobject);
				if(array_clearingobject != null)
				{
					try{
							
						   inward_list=new ArrayList();
						   Map map=null;
							for(int i=0;i<array_clearingobject.length;i++)
							{
								map=new HashMap();
								map.put("srlno",(i+1));
								map.put("ctrlno",new Long(array_clearingobject[i].getCtrlNo()));
								map.put("clgtype",array_clearingobject[i].getClgType());				
								map.put("docbs",array_clearingobject[i].getDocBs());
								map.put("ackno",new Integer(array_clearingobject[i].getAckNo()));
								map.put("nofdocs",new Integer(array_clearingobject[i].getNoOfDocs()));
								map.put("trnamt",new Double(array_clearingobject[i].getTranAmt()));
								map.put("srcname",array_clearingobject[i].getSourceName());
								map.put("destname",array_clearingobject[i].getDestName());
								map.put("chqdpo",array_clearingobject[i].getChqDDPO());
								map.put("qdpno",new Integer(array_clearingobject[i].getQdpNo()));
								map.put("qdpdt",array_clearingobject[i].getQdpDate());
								
								if(array_clearingobject[i].getDocBs().equalsIgnoreCase("S"))
								{
									for(int kk = 0; kk<module.length; kk++)
									if(array_clearingobject[i].getDebitACType().equals(String.valueOf(module[kk].getModuleCode()))){
										map.put("mabbrv",module[kk].getModuleAbbrv()+" "+array_clearingobject[i].getDebitACNo());
										break;
									}
								}
								else
									map.put("blank"," ");
								
								map.put("cmpname",array_clearingobject[i].getCompanyName());
								map.put("payeename",array_clearingobject[i].getPayeeName());
								map.put("bkname",array_clearingobject[i].getBankName());
								map.put("brname",array_clearingobject[i].getBranchName());
								map.put("prevctrlno",new Integer(array_clearingobject[i].getPrevCtrlNo()));
								map.put("deuser",array_clearingobject[i].getDeUser());
								map.put("veuser",array_clearingobject[i].getVeUser());
								inward_list.add(map);
							}
							request.setAttribute("inward_list",inward_list);
							
						}catch(Exception ex){
							System.out.println(ex);
						ex.printStackTrace();	
						}
						string_qry=null;
				}
		
				else
				{
					System.out.println("No Rows Found memememme");
					Inwdstmtform.setValidation("No Rows Found");
					inward_list=null;
					return inward_list;
				}
			}
		
				catch (Exception ex) 
				{
				System.out.println(ex);
				}
			
	}
	return inward_list;
	
			
	

}
private List setOutwardstmtList(ClearingDelegate clDelegate,OutwardstmtForm outwardstmtForm,HttpServletRequest req)throws Exception
{
	try{
	array_clearingobject=clDelegate.getOutwardStatement(outwardstmtForm.getClr_date(),Integer.parseInt(outwardstmtForm.getClr_no()),string_qry);
	}catch (Exception e) {
		e.printStackTrace();
	}
	int no_of_indi=0;
	double indi_amount = 0.0;
	int no_of_bundle = 0;
	int no_of_Cheque = 0;
	double bundle_amount = 0.0;
	
	 for(int i=0;i<array_clearingobject.length;i++)
	 {
	
	if(array_clearingobject[i].getDocBs().equalsIgnoreCase("S")){
    	no_of_indi++;
    	indi_amount += array_clearingobject[i].getTranAmt();
    }
    
    if(array_clearingobject[i].getDocBs().equalsIgnoreCase("B")){
    	
    	no_of_bundle++;
    	no_of_Cheque += array_clearingobject[i].getNoOfDocs();
    	bundle_amount+=array_clearingobject[i].getDocTotal();
    }
   /* dtm.addRow(array_object);
    if(i<(array_clearingobject.length-1))
    {
        for(int j=0;j<26;j++)
            array_object[j]="";
    }*/
}
	 
System.out.println("no of docs -------"+ no_of_indi +" amount ------"+indi_amount);
System.out.println("no of docs -------"+ no_of_bundle+" no of cheque "+no_of_Cheque +" amount ------"+bundle_amount);

//dtm_table_statement_report.setRowCount(0);

List Ostmt_list=(List) new ArrayList();

Map map=new TreeMap();

map.put("individual",new String("Individual"));
map.put("noofdoc",new String(""+no_of_indi));
map.put("noofchq",new String(""+no_of_indi));
map.put("amt",new String(""+indi_amount));
Ostmt_list.add(map);

map=new TreeMap();

map.put("individual",new String("Bundled"));
map.put("noofdoc",new String(""+no_of_bundle));
map.put("noofchq",new String(""+no_of_Cheque));
map.put("amt",new String(""+bundle_amount));
Ostmt_list.add(map);

map=new TreeMap();

map.put("individual",new String("Total"));
map.put("noofdoc",new String(""+(no_of_bundle+no_of_indi)));
map.put("noofchq",new String(""+(no_of_Cheque+no_of_indi)));
map.put("amt", new String(""+(bundle_amount+indi_amount)));
Ostmt_list.add(map);

req.setAttribute("Ostmtlist",Ostmt_list);
//session=req.getSession(true);
//session.setAttribute("Ostmtlist",Ostmt_list);


	
	return Ostmt_list;



}

	private void InwardStmtPrinting() 
	{
		/*DefaultTableModel dtm_array[]=new DefaultTableModel[1];
		dtm_array[0]=dtm;
		JTable tables[]=new JTable[1];
		tables[0]=table;
		
		PrintEmpty pempty=new PrintEmpty(tables,dtm_array);
		pempty.setPrinterEmpty(pempty);
		pempty.setReport_heading("Inward Statement Report upto:"+MainScreen.head.getSysDateTime());
		pempty.printAnalyser();
*/

	}



}
