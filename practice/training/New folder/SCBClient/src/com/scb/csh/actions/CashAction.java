package com.scb.csh.actions;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import masterObject.cashier.CashObject;
import masterObject.cashier.CurrencyStockObject;
import masterObject.cashier.TerminalObject;
import masterObject.cashier.VoucherDataObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.DoubleFormat;
import masterObject.general.ModuleObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.csh.forms.ConsolidateScrollform;
import com.scb.csh.forms.CurrencyStockForm;
import com.scb.csh.forms.ExchangeForm;
import com.scb.csh.forms.MiscellaneousForm;
import com.scb.csh.forms.PaymentForm;
import com.scb.csh.forms.RebalanceForm;
import com.scb.csh.forms.Recieiptsform;
import com.scb.csh.forms.TransferForm;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.CashDelegate;
import com.scb.designPatterns.LoansDelegate;
import com.scb.designPatterns.exceptions.ApplicationException;
import com.scb.props.MenuNameReader;

import exceptions.CustomerNotFoundException;
import exceptions.InsufficientAmountException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.UserException;
import general.Validations;

public class CashAction extends Action {

private String path;
int share_cat = 0;
int accnoexist = 0;
int po_descrptn = 0;
double double_amount=0,double_refamount=0;
double sum=0,sum1=0;
String[] cbox=null;
String[] miscell_amount=null;
masterObject.general.AccountObject accountobject = null;
AccSubCategoryObject accsubcategoryobject[] = null,
accsubcategoryobject1[] = null;
CurrencyStockObject cso = null,currencystockobject=null;
CashObject[] cashobject_withdenom;
int flag=0;
String query="";
TerminalObject[] terminalobject_view =null;
CashObject co;
CashObject cashobject =null;
CashObject[] cashobject_withdenomall;
CashObject cashobjectAll =null;
ModuleObject[] module_obj_array = null, moduleobject_gen=null;
ModuleObject[] module_obj_array1 = null;
AccSubCategoryObject[] acc =null;
AccSubCategoryObject[] acc1 =null;
AccSubCategoryObject[] acc2 =null;
String string_locker_type;
String[] Lockdesc;
ShareCategoryObject[] sharecategoryobject;
String[] lock_types;
double double_locker_rent;
ShareMasterObject sharemasterobject;
masterObject.frontCounter.ChequeObject chequeobject=null;
CashObject cashObject = null;
masterObject.cashier.VoucherDataObject voucherobject = null,voucherDataObject=null;
masterObject.cashier.VoucherDataObject[] array_VoucherDataObject =null;
String[] lock_desc = null;
String lk_ty = null;//sowmya
double lk_rent = 0.0;
CashObject[] array_cashobject=null;
double update_amt = 0.0;
String cust_subcat = "";
ModuleObject[] moduleobject;
//miscellaneous.....
boolean focus_flag = true;
private boolean boolean_flag;
public int voucher_number_currency=0;
public String voucher_type_currency=null;
private HttpSession session=null;
HttpSession sessions;
String user,tml;
CashObject cashobject_view;
AdministratorDelegate admDelegate;
Map user_role;
LoanReportObject loanreportobject = null;
LoanTransactionObject loantransactionobject = null;
final Logger logger = LogDetails.getInstance().getLoggerObject("CashDelegate");
masterObject.loans.LoanMasterObject loanmasterobject = null;
public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse res)throws Exception
{
	/**MENU- GENERAL RECEIPT **/
	
	session=request.getSession();
		user=(String)session.getAttribute("UserName");
	    	tml=(String)session.getAttribute("UserTml") ;
	
	    	try{
	    		synchronized(request) {
					
				
	    		if(user!=null){
	    			
	    			admDelegate=new AdministratorDelegate();
	    			user_role=admDelegate.getUserLoginAccessRights(user,"CA");
	    			if(user_role!=null){
	    				request.setAttribute("user_role",user_role);
	    			if(request.getParameter("pageId")!=null){
	    				System.out.println("req.getParameter--in FC ACtion===>"+request.getParameter("pageId"));
	    				request.setAttribute("accesspageId",request.getParameter("pageId").trim());
	    			}
					
	    			}
	    			else
	    			{
	    				//to error page for display
	    				path=MenuNameReader.getScreenProperties("0000");
	    		        
	    		           setErrorPageElements("Sorry, You do not have access to this page",request,path);
	    		           return map.findForward(ResultHelp.getError());
	    			}
	    		}
	    		}
	    	}
	    	catch(Exception ex){ex.printStackTrace();}
	    	
	if(map.getPath().equalsIgnoreCase("/ReceiptMenu"))
	{
		try{
		    	System.out.println("ReceiptMenu");
		    	 System.out.println("Hiiii-------This is Shreya------");
		    	CashDelegate rcDelegate =new CashDelegate();     
		    	Recieiptsform rcform=(Recieiptsform)form;
		    	request.setAttribute("TabNum","0");
		    	rcform.setMiniamt(0);
		    	rcform.setAccexist("null");
		    	rcform.setGen_scroll(0);
		    	rcform.setLk_amount("null");
		    	rcform.setGen_scroll(0);
		    	rcform.setScrverify("null");
		    	rcform.setNewscrverify("null");
		    	rcform.setClosed("");
		    	rcform.setAccountobject("null");
		    	rcform.setPayorderamt(0);
		    	rcform.setClearatstart("clear");
		    	if(rcform.getTabPaneHeading()!=null)
		    	{
		    		if(rcform.getTabPaneHeading().equalsIgnoreCase("LoanStatus"))
		    			{
		    				request.setAttribute("TabNum","1");
		    			}
		    	}
		    String closed=request.getParameter("closed");
		    
		    System.out.println("The cash closed is==="+closed);
		    	//SOWMYA....inserting personal tabedpane
		    	getTabbedpane(request,rcform);
		    	request.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
		    	request.setAttribute("panel",CommonPanelHeading.getPersonal());
		    	request.setAttribute("scrollno", rcform.getScrollno());
		    	
		    	module_obj_array  = rcDelegate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
		    	request.setAttribute("MainModules", module_obj_array);
		       		        
		        module_obj_array1 = rcDelegate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1017000,1018000");
		        request.setAttribute("MainModules1", module_obj_array1);
		        		       
		        String[] locktype = rcDelegate.getLockersDesc();
		        request.setAttribute("locktype", locktype);
		        
		        
		        
		        ShareCategoryObject[] sharecategoryobject=null;
		        sharecategoryobject =rcDelegate.getShareCategories(0,"1001001");
		        request.setAttribute("sharecategory",sharecategoryobject); 
		        
		        double tml_runable =rcDelegate.getCashTmlRunningBalance(tml,rcDelegate.getSysDate()); 
		        request.setAttribute("tml_runable",tml_runable);
		         
		        double total_runbal = rcDelegate.getAllCashTmlRunningBalance(rcDelegate.getSysDate());
		        request.setAttribute("Allterminal",total_runbal);
		 	    
		 	  
		        if(MenuNameReader.containsKeyScreen(rcform.getPageId()))
					{

		            String status=rcDelegate.checkDailyStatus2();
		            if(status==null)
		           {
		            	path=MenuNameReader.getScreenProperties(rcform.getPageId());
						System.out.println("path in action class=======>"+path); 
						request.setAttribute("pageId",path);
						setReceiptparamform(request,path,rcDelegate);
						request.setAttribute("submit", rcform.getValue());
						
						
						
						return map.findForward(ResultHelp.getSuccess());

		                                   //your code that has to work properly
		          }
		          else
		         {
		        	  path=MenuNameReader.getScreenProperties("0000");
	                    setErrorPageElements("  "+status,request,path);
	                    return map.findForward(ResultHelp.getError());
		          }	
		        	
		        	
		   
					}
		         else
		         	{
		        	 return map.findForward(ResultHelp.getError());
		         	}
		        
		        
		}catch (Exception e) 
		       {		
		    	  	e.printStackTrace(); 
		        	return map.findForward(ResultHelp.getSuccess());
		       }	
	}
	/** GENERAL RECEIPT **/
	else if(map.getPath().equalsIgnoreCase("/Receipt"))
	{ 
		
		 
		System.out.println("*******************Receipt********************");
		System.out.println("--------Hi I M Shreya---------");
		Recieiptsform recform =(Recieiptsform)form;
		CashDelegate recDelegate = new CashDelegate();
	    CashObject cash = recDelegate.getData(recform.getScrollno(),0,recDelegate.getSysDate());
		request.setAttribute("submit", recform.getValue());
		request.setAttribute("TabNum","0");
	
   	 	getTabbedpane(request,recform);
   	 	request.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
   	    module_obj_array  = recDelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
		request.setAttribute("MainModules", module_obj_array);//GENERAL module
		
		recform.setGen_scroll(0);
		recform.setMiniamt(0);
		recform.setAccexist("null");
		recform.setClosed("null");
		recform.setLk_amount("null");
		recform.setScrverify("null");
		recform.setNewscrverify("null");
		recform.setAccountobject("null");
		recform.setPayorderamt(0);
		//recform.setRefund(0);
		//recform.setRec_amt(0);
		
		request.setAttribute("accno",String.valueOf(recform.getAccno()));
		
		
		System.out.println("$$$customer customer Description$$$$"+recform.getDescription());
		
		request.setAttribute("scrollno",recform.getScrollno());
		
		if(recform.getDetails().equalsIgnoreCase("Personal Details"))
		{
			System.out.println("HIiiiiii i m in personal!!!!!!!!!");
			if(accountobject!=null && accountobject.getCid()!=0)
			if(accountobject.getCid()!=0)
			{
			int cid = accountobject.getCid();
			System.out.println("CID in Personal-------->"+cid);
			request.setAttribute("flag1",MenuNameReader.getScreenProperties("0001"));
			request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
			 request.setAttribute("address",recDelegate.getCustomerAddress(cid));
			 request.setAttribute("addr_type",recDelegate.getCustomerAddType(cid));
			 request.setAttribute("panelName", CommonPanelHeading.getPersonal());
			 request.setAttribute("required", "Personal");
		     String bdate= recDelegate.getBOD(cid);
		     if(bdate!="" && bdate!=null && bdate.trim().length()!=0){
		     String sysdate= recDelegate.getSysDate();
		     StringTokenizer d=new StringTokenizer(bdate,"/");
		     d.nextToken(); d.nextToken();
 			int yy=Integer.parseInt(d.nextToken());
 			Calendar cal=Calendar.getInstance();
 			int age= cal.get(Calendar.YEAR)-yy;
 			System.out.println("Age is---------"+age);
 			request.setAttribute("age",age);
		     System.out.println("Birth Date In Action Class-----"+bdate);
		     System.out.println("Sysdate In Action Class-----"+sysdate);
		     }else{
		    	 request.setAttribute("age",null);
		     }
			}else{
				request.setAttribute("displaymsg", "CustomerId Not Found");
			}
		}
		else if(recform.getDetails().equalsIgnoreCase("Loan Status"))
		{
			LoansDelegate delegate = null;
			LoanMasterObject lmobj = null;
			com.scb.loans.forms.LoanStatusForm statform = recform.getLoanstatusform();
			//LoanHistForm histform = recform.getLoanhistform();
			LoanTransactionObject lntrnobj = null, loantran[] = null, lntrn = null,loan_trn[][] = null;
			//LastLoanTranForm lasttran = recform.getLastloantran();
			ArrayList arraylist = null;
			// RecoveryByTransfer transferform =
			// recovform.getTransferform();
			delegate = new LoansDelegate();
			request.setAttribute("LoanStatus", statform);
			//request.setAttribute("LoanHistory", histform);
			//request.setAttribute("LoanTran", lasttran);
			if (recform.getAccno() != 0) {
			String acctype=	module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode();
				System.out.println("Loan Acc no in action -------------->>>>"+recform.getAccno());
				System.out.println("Loan acctype in action -------------->>>>"+ acctype);
				if((module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode()!=null)&&(recform.getAccno()!=0))
				lmobj = delegate.getQueryOnLoanStatus(acctype, recform.getAccno(), LoansDelegate.getSysDate());
				if(lmobj!=null && lmobj.getAccType()!=null){
					//Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
				
				
				if(lmobj.getClosedt()!=null){
					request.setAttribute("msg", "Given Account Number is Closed");
				}
				
				else if (lmobj != null) {
					lntrnobj = delegate.getQueryLoanStatus(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(), recform.getAccno(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());
					System.out.println("LOanTran Object"+ lntrnobj.toString());
					request.setAttribute("LoanTranObject", lntrnobj);
					if (lntrnobj != null) 
					{
						recform.getLoanstatusform().setTxt_loanba(lntrnobj.getLoanBalance());
						//recform.setTxt_loanba(lntrnobj.getLoanBalance());
						// req.setAttribute("", arg1)
						statform.setTxt_loanba(lntrnobj.getLoanBalance());
						statform.setTxt_addpaid(String.valueOf(lntrnobj.getPrincipalPaid()));
						request.setAttribute("AdvancePaidAmount", String.valueOf(lntrnobj.getPrincipalPaid()));
						System.out.println("Lrnobj.penal amount---------->"+DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						System.out.println("lntrnobj.getPrincipalBalance()" + lntrnobj.getPrincipalBalance());
						statform.setTxt_current_inst(DoubleFormat.toString(lntrnobj.getPrincipalPayable()));
						statform.setTxt_interest(DoubleFormat.toString(lntrnobj.getInterestPayable()));
						statform.setTxt_pinealInt(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						statform.setTxt_otherchar(DoubleFormat.toString(lntrnobj.getOtherAmount()));
						statform.setTxt_extra(DoubleFormat.toString(lntrnobj.getExtraIntAmount()));
					
						/*double total_amt = obj_loanstatus.getPrincipalBalance()+obj_loanstatus.getPrincipalPayable()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount();*/
						double total_amt = lntrnobj.getPrincipalBalance()+lntrnobj.getPrincipalPayable()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
						System.out.println("*************Total Amount**************" + total_amt);
						statform.setTxt_tca(String.valueOf(total_amt));
						request.setAttribute("TotalLoanAmount", String.valueOf(total_amt));
						/*lbl_total_amount.setText(nf.format(total_amt));
				        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
				        lbl_close_amt.setText(nf.format(total_amt));*/
						double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
						System.out.println("*********************** Total Closeable amount****************" + close_amt);
						statform.setTxt_totcloamt(String.valueOf(close_amt));
						request.setAttribute("TotalClosableAmount", String.valueOf(close_amt));
					}
				}
					
			
				/*System.out.println("Cid" + lmobj.getCustomerId());
					System.out.println("Mailing Address"+ lmobj.getMailingAddress());
					HashMap maps = null;
					maps = delegate.getAddress(lmobj.getCustomerId());
					Address address = (Address) maps.get(new Integer(lmobj.getMailingAddress()));
					System.out.println("Address object===========>"+ address.toString());
					System.out.println("Phno" + address.getPhno());
					System.out.println("Mobile" + address.getMobile());
					System.out.println("Fax" + address.getFax());
					System.out.println("Email" + address.getEmail());*/
					/*if (address != null) {
						histform.setPhone(address.getPhno());
						histform.setMob(address.getMobile());
						histform.setFax(address.getFax());
						histform.setEmail(address.getEmail());
					}
*/
					/*arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
					if (!arraylist.isEmpty()) {
						System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
						lasttran.setLastprincipleamt(arraylist.get(6).toString());
						lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
						lasttran.setLastintamt(arraylist.get(4).toString());
						lasttran.setLastintamttrnamt(arraylist.get(5).toString());
						lasttran.setLastpiamt(arraylist.get(3).toString());
						lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
						lasttran.setLastotheramt(arraylist.get(1).toString());
					}*/

					/*arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
					System.out.println("NPA Status====>" + arraylist.size());
					System.out.println("NPADate" + histform.getNpadate());
					if (!arraylist.isEmpty()) {
						histform.setNpadate(arraylist.get(0).toString());
						histform.setNpastage(arraylist.get(1).toString());
						histform.setNpaprinciplefrom(arraylist.get(2).toString());
						histform.setNpaprincipleamt(Double.parseDouble(arraylist.get(3).toString()));
						histform.setNpaodprd(Integer.parseInt(arraylist.get(4).toString()));
						histform.setNpaintamt(Double.parseDouble(arraylist.get(5).toString()));
					}

					System.out.println("NPAStage" + histform.getNpastage());*/
					// delegate.getSurityCoBorrowerDetails(recovform.getAcctype(),recovform.getAccno());
					/*
					 * System.out.println("First Col"+arraylist.get(0));
					 * System.out.println("Second Col"+arraylist.get(1));
					 * System.out.println("Third Col"+arraylist.get(2));
					 */

				/*	System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
					System.out.println("Recovery Account Number==>"+ recovform.getAccno());

					System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
					ArrayList surity = delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno());
					System.out.println("size==============>"+ surity.size());
					for (int i = 0; i < surity.size(); i++) {
						System.out.println("data=====>" + surity.get(i));
					}
					request.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
					arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
					if (!arraylist.isEmpty()) {

						Iterator iterator = arraylist.iterator();
						while (iterator.hasNext()) {
							String strr = (String) iterator.next();
							if (strr.length() > 0) {
								histform.setLastnotice(strr);
							}
						}
					}*/

				
			
			//System.out.println("CID in Personal-------->"+cid);
			
			System.out.println("Are U Here ----------2");
			request.setAttribute("flag2",MenuNameReader.getScreenProperties("5018"));
			System.out.println("Are U Here ----------3");
			 
			 request.setAttribute("panelName", CommonPanelHeading.getLoanDetails());
			 System.out.println("Are U Here ----------4");
			 request.setAttribute("required", "Loan");
			 
			}else{
				request.setAttribute("msg", "Loan Details Are Not Available For Given Account Number");
			}
		}
	}
		
		
		if(recform.getTabPaneHeading()!=null)
    	{
    		if(recform.getTabPaneHeading().equalsIgnoreCase("LoanStatus"))
    			{
    				request.setAttribute("TabNum","1");
    			}
    	}
		String acc_details=recDelegate.getCustmerName(recform.getAccounttype(),recform.getAccno());
		if(acc_details!=null){
		if(recform.getAccno()!=0 && recform.getAccounttype()!=null)
		{
		if(recform.getDetails()!=null)
		{
			System.out.println("Details======"+recform.getDetails());
			System.out.println("Details Are Not Displaying");
		
		if(recform.getDetails().equalsIgnoreCase("Personal Details"))
		{
			System.out.println("HIiiiiii i m in personal!!!!!!!!!");
			if(accountobject.getCid()!=0)
			{
			int cid = accountobject.getCid();
			
			
			System.out.println("CID in Personal-------->"+cid);
			
			request.setAttribute("flag1",MenuNameReader.getScreenProperties("0001"));
			request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
			
			
			
			 request.setAttribute("address",recDelegate.getCustomerAddress(cid));
			 request.setAttribute("addr_type",recDelegate.getCustomerAddType(cid));
			 
			 request.setAttribute("panelName", CommonPanelHeading.getPersonal());
			 request.setAttribute("required", "Personal");
			 
			
		     String bdate= recDelegate.getBOD(cid);
		     if(bdate!=null && bdate!="" && bdate.trim().length()!=0){
		     String sysdate= recDelegate.getSysDate();

		     StringTokenizer d=new StringTokenizer(bdate,"/");
		     d.nextToken(); d.nextToken();
 			int yy=Integer.parseInt(d.nextToken());
 			
 			Calendar cal=Calendar.getInstance();
 			int age= cal.get(Calendar.YEAR)-yy;
 			System.out.println("Age is---------"+age);
 			
 			request.setAttribute("age",age);
		     }else{
		    	 request.setAttribute("age",null);
		     }
			}else{
				request.setAttribute("displaymsg", "CustomerId Not Found");
			}
		
		}
		else if(recform.getDetails().equalsIgnoreCase("Loan Status"))
		{
			
			LoansDelegate delegate = null;
			LoanMasterObject lmobj = null;
			com.scb.loans.forms.LoanStatusForm statform = recform.getLoanstatusform();
			//LoanHistForm histform = recform.getLoanhistform();
			LoanTransactionObject lntrnobj = null, loantran[] = null, lntrn = null,loan_trn[][] = null;
			//LastLoanTranForm lasttran = recform.getLastloantran();
			ArrayList arraylist = null;
			// RecoveryByTransfer transferform =
			// recovform.getTransferform();
			delegate = new LoansDelegate();
			request.setAttribute("LoanStatus", statform);
			//request.setAttribute("LoanHistory", histform);
			//request.setAttribute("LoanTran", lasttran);
			if (recform.getAccno() != 0) {
				System.out.println("Loan Acc type in action -------------->>>>"+module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode());
				System.out.println("Loan Acc no in action -------------->>>>"+recform.getAccno());
				System.out.println("Loan trndate in action -------------->>>>"+ LoansDelegate.getSysDate());
				if((module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode()!=null)&&(recform.getAccno()!=0))
				
				lmobj = delegate.getQueryOnLoanStatus(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(), recform.getAccno(), LoansDelegate.getSysDate());
				//System.out.println("Loan MAster Object --------"+lmobj.getAccType());
				Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
				
				if(lmobj.getClosedt()!=null){
					request.setAttribute("displaymsg", "Given Account Number is Closed");
				}
				else if (lmobj != null) {
				
					
					lntrnobj = delegate.getQueryLoanStatus(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(), recform.getAccno(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());
					System.out.println("LOanTran Object"+ lntrnobj.toString());
					request.setAttribute("LoanTranObject", lntrnobj);

					if (lntrnobj != null) 
					{
						recform.getLoanstatusform().setTxt_loanba(lntrnobj.getLoanBalance());
						//recform.setTxt_loanba(lntrnobj.getLoanBalance());
						// req.setAttribute("", arg1)
						statform.setTxt_loanba(lntrnobj.getLoanBalance());
						statform.setTxt_addpaid(String.valueOf(lntrnobj.getPrincipalPaid()));
						request.setAttribute("AdvancePaidAmount", String.valueOf(lntrnobj.getPrincipalPaid()));
						System.out.println("Lrnobj.penal amount---------->"+DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						
						System.out.println("lntrnobj.getPrincipalBalance()" + lntrnobj.getPrincipalBalance());
						statform.setTxt_current_inst(DoubleFormat.toString(lntrnobj.getPrincipalPayable()));
						statform.setTxt_interest(DoubleFormat.toString(lntrnobj.getInterestPayable()));
						statform.setTxt_pinealInt(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
						statform.setTxt_otherchar(DoubleFormat.toString(lntrnobj.getOtherAmount()));
						statform.setTxt_extra(DoubleFormat.toString(lntrnobj.getExtraIntAmount()));
					
						/*double total_amt = obj_loanstatus.getPrincipalBalance()+obj_loanstatus.getPrincipalPayable()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount();*/
						double total_amt = lntrnobj.getPrincipalBalance()+lntrnobj.getPrincipalPayable()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
						System.out.println("*************Total Amount**************" + total_amt);
						statform.setTxt_tca(String.valueOf(total_amt));
						request.setAttribute("TotalLoanAmount", String.valueOf(total_amt));
						/*lbl_total_amount.setText(nf.format(total_amt));
				        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
				        lbl_close_amt.setText(nf.format(total_amt));*/
						double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
						System.out.println("*********************** Total Closeable amount****************" + close_amt);
						statform.setTxt_totcloamt(String.valueOf(close_amt));
						request.setAttribute("TotalClosableAmount", String.valueOf(close_amt));
					}
					/*System.out.println("Cid" + lmobj.getCustomerId());
					System.out.println("Mailing Address"+ lmobj.getMailingAddress());
					HashMap maps = null;
					maps = delegate.getAddress(lmobj.getCustomerId());
					Address address = (Address) maps.get(new Integer(lmobj.getMailingAddress()));
					System.out.println("Address object===========>"+ address.toString());
					System.out.println("Phno" + address.getPhno());
					System.out.println("Mobile" + address.getMobile());
					System.out.println("Fax" + address.getFax());
					System.out.println("Email" + address.getEmail());*/
					/*if (address != null) {
						histform.setPhone(address.getPhno());
						histform.setMob(address.getMobile());
						histform.setFax(address.getFax());
						histform.setEmail(address.getEmail());
					}
*/
					/*arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
					if (!arraylist.isEmpty()) {
						System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
						lasttran.setLastprincipleamt(arraylist.get(6).toString());
						lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
						lasttran.setLastintamt(arraylist.get(4).toString());
						lasttran.setLastintamttrnamt(arraylist.get(5).toString());
						lasttran.setLastpiamt(arraylist.get(3).toString());
						lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
						lasttran.setLastotheramt(arraylist.get(1).toString());
					}*/

					/*arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
					System.out.println("NPA Status====>" + arraylist.size());
					System.out.println("NPADate" + histform.getNpadate());
					if (!arraylist.isEmpty()) {
						histform.setNpadate(arraylist.get(0).toString());
						histform.setNpastage(arraylist.get(1).toString());
						histform.setNpaprinciplefrom(arraylist.get(2).toString());
						histform.setNpaprincipleamt(Double.parseDouble(arraylist.get(3).toString()));
						histform.setNpaodprd(Integer.parseInt(arraylist.get(4).toString()));
						histform.setNpaintamt(Double.parseDouble(arraylist.get(5).toString()));
					}

					System.out.println("NPAStage" + histform.getNpastage());*/
					// delegate.getSurityCoBorrowerDetails(recovform.getAcctype(),recovform.getAccno());
					/*
					 * System.out.println("First Col"+arraylist.get(0));
					 * System.out.println("Second Col"+arraylist.get(1));
					 * System.out.println("Third Col"+arraylist.get(2));
					 */

				/*	System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
					System.out.println("Recovery Account Number==>"+ recovform.getAccno());

					System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
					ArrayList surity = delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno());
					System.out.println("size==============>"+ surity.size());
					for (int i = 0; i < surity.size(); i++) {
						System.out.println("data=====>" + surity.get(i));
					}
					request.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
					arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
					if (!arraylist.isEmpty()) {

						Iterator iterator = arraylist.iterator();
						while (iterator.hasNext()) {
							String strr = (String) iterator.next();
							if (strr.length() > 0) {
								histform.setLastnotice(strr);
							}
						}
					}*/

				}
			}
			//System.out.println("CID in Personal-------->"+cid);
			
			System.out.println("Are U Here ----------2");
			request.setAttribute("flag2",MenuNameReader.getScreenProperties("5018"));
			System.out.println("Are U Here ----------3");
			 
			 request.setAttribute("panelName", CommonPanelHeading.getLoanDetails());
			 System.out.println("Are U Here ----------4");
			 request.setAttribute("required", "Loan");
		}
		}	
		}
		else{
			request.setAttribute("displaymsg","Account Number Not Found...!!");
		}
		}
		
		// general
		
		
		// PO
		module_obj_array1 = recDelegate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1017000,1018000");
		
        request.setAttribute("MainModules1", module_obj_array1);// po-main module
		System.out.println("reciept value333333333333333333333333"+Integer.parseInt(recform.getValue().trim()));
		
		
    	int accno=recform.getAccno();
    	
		if(accno!=0)
		{
			AccountObject accname=recDelegate.getAccount("null", recform.getAccname(),recform.getAccno(),recDelegate.getSysDate());
			System.out.println("The accname in action class===="+accname);
		}
		else
		{
			System.out.println(recform.getAccname());
		}
		
		
	  
		 String name = recform.getAccname();
	Double amts =recform.getAmount();
	if(name!=null)
	{
		  request.setAttribute("name",name);
		  recform.setAccname(name);
		  System.out.println("The name is ----------------"+name);
	}
		System.out.println("Hiiiiiiiiiiiiiiiiii i m shreya");
		if(amts!=null)
		{
	     request.setAttribute("amount",amts);
	    
	     recform.setAmount(amts);
	    
	     System.out.println("The amount is ----------------"+amts);
		}   
		
		
	 	if(cash!=null)
	 	{
		 
		   System.out.println("cash========="+cash.getAmount());
		   request.setAttribute("cashamt",cash);
	     
	   
	 	}
      
        if(Integer.parseInt(recform.getValue().trim())==1)
        {
        	request.setAttribute("button","Receive");
        }
       
        if(Integer.parseInt(recform.getValue().trim())==2)
        {
        	
        	request.setAttribute("button","Verify");
        }
       
        
        
        
        
        
        double tml_runable =recDelegate.getCashTmlRunningBalance(tml,recDelegate.getSysDate()); 
        request.setAttribute("tml_runable",tml_runable);
        	
        double total_runbal = recDelegate.getAllCashTmlRunningBalance(recDelegate.getSysDate());
	    request.setAttribute("Allterminal",total_runbal);
	  
                // ***************module code*******************

       String string_code = null;
       
      String module =module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleAbbrv();
       
       request.setAttribute("getAbbrv", module);
       
       if(module!=null)
       	{
    	   for(int i=0;i<module_obj_array.length;i++)
    	   	{
    		  String mod=module_obj_array[i].getModuleAbbrv();
       	
    		  if(mod.equalsIgnoreCase(module))
    		  	{
    			  String string_desc=module_obj_array[i].getModuleDesc();
    			  request.setAttribute("module",string_desc);
    			  string_code =String.valueOf(module_obj_array[i].getModuleCode());
    			 
    		  	}
    	   	}
       } 
   
       // *********************po - module code**************
        
      String pomodule =module_obj_array1[Integer.valueOf(recform.getCustacctype())].getModuleAbbrv();// PO
      String postring_code =null;
      
      if(pomodule!=null)
       {
    	  for(int i=0;i<module_obj_array1.length;i++)
    	  	{
    		  String pomod=module_obj_array1[i].getModuleAbbrv();
        	
    		  if(pomod.equalsIgnoreCase(pomodule))
    		  	{
    			  String postring_abbrv=module_obj_array1[i].getModuleDesc();
    			  request.setAttribute("module1",postring_abbrv);
    			  postring_code =String.valueOf(module_obj_array1[i].getModuleCode());
    			 
    		  	}
        	}
        } 
      // *******************customer type***************
     String custtype= recform.getCusttype();
     if(custtype!=null)
     	{
     		request.setAttribute("custtype", custtype);
     	}
        
     try{ 
     	if(custtype.equalsIgnoreCase("Customer"))
     		{
     		// PODescription-customer
     		int j=0;
     		acc =recDelegate.getAccSubCategories(0);
     		acc1=recDelegate.getAccSubCategories(1);
         
        if(acc!=null && acc1!=null )
          {
			AccSubCategoryObject[] accsubcategoryobject=new AccSubCategoryObject[acc.length+acc1.length];
			for(int i=0;i<acc.length;i++)
				{
					accsubcategoryobject[i]=acc[i];
				}
			try{
				for(int i=acc.length;i<(acc1.length+acc.length);i++)
        		{
        			accsubcategoryobject[i]=acc1[j++];
        			int m=accsubcategoryobject[i].getSubCategoryCode();
        			
        			request.setAttribute("podescription",accsubcategoryobject);
        		
        		}
					}
			catch (Exception e) 
				{
				e.printStackTrace();
				}
          		}
     
     		} 
       
     	else if(custtype.equalsIgnoreCase("Non Customer"))
     		{
     			// poDescription1-noncustomer
     			acc2=recDelegate.getAccSubCategories(2);
                request.setAttribute("podescription2", acc2);	
        	}
       }catch(RemoteException e)
       	{
    	   e.printStackTrace();
       	}
       	catch (SQLException se) 
       	{
       		se.printStackTrace();
       	}
        
       	// Locker type
        Lockdesc = recDelegate.getLockersDesc();
        request.setAttribute("locktype", Lockdesc);
      
       // share category
        sharecategoryobject = recDelegate.getShareCategories(0,string_code);
        request.setAttribute("sharecategory", sharecategoryobject);
        
      
        if(recform.getBut_value().equalsIgnoreCase("update"))
        {
         	request.setAttribute("buttvalue","update");
        }
        
        setReceiptparamform(request,path,recDelegate);
    	/**scroll number!==0 **/
     System.out.println("Hiiiiiiii whether you are here !!!!!!!!!!!!!");
      if(recform.getScrollno()!=0)
		{
    	       
			try
				{
				if(recform.getDetails().equalsIgnoreCase("Personal Details"))
				{
					System.out.println("HIiiiiii i m in personal!!!!!!!!!");
					if(accountobject.getCid()!=0)
					{
					int cid = accountobject.getCid();
					
					
					System.out.println("CID in Personal-------->"+cid);
					
					request.setAttribute("flag1",MenuNameReader.getScreenProperties("0001"));
					request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
					
					
					
					 request.setAttribute("address",recDelegate.getCustomerAddress(cid));
					 request.setAttribute("addr_type",recDelegate.getCustomerAddType(cid));
					 
					 request.setAttribute("panelName", CommonPanelHeading.getPersonal());
					 request.setAttribute("required", "Personal");
					 
					
				     String bdate= recDelegate.getBOD(cid);
				     String sysdate= recDelegate.getSysDate();
				     if(bdate!=null && bdate!="" && bdate.trim().length()!=0){
				     StringTokenizer d=new StringTokenizer(bdate,"/");
				     d.nextToken(); d.nextToken();
		 			int yy=Integer.parseInt(d.nextToken());
		 			
		 			Calendar cal=Calendar.getInstance();
		 			int age= cal.get(Calendar.YEAR)-yy;
		 			System.out.println("Age is---------"+age);
		 			
		 			request.setAttribute("age",age);
				     }else{
				    	 request.setAttribute("age",null);
				     }
				     }else{
						request.setAttribute("displaymsg", "CustomerId Not Found");
					}
				
				}
				else if(recform.getDetails().equalsIgnoreCase("Loan Status"))
				{
					LoansDelegate delegate = null;
					LoanMasterObject lmobj = null;
					com.scb.loans.forms.LoanStatusForm statform = recform.getLoanstatusform();
					//LoanHistForm histform = recform.getLoanhistform();
					LoanTransactionObject lntrnobj = null, loantran[] = null, lntrn = null,loan_trn[][] = null;
					//LastLoanTranForm lasttran = recform.getLastloantran();
					ArrayList arraylist = null;
					// RecoveryByTransfer transferform =
					// recovform.getTransferform();
					delegate = new LoansDelegate();
					request.setAttribute("LoanStatus", statform);
					//request.setAttribute("LoanHistory", histform);
					//request.setAttribute("LoanTran", lasttran);
					if (recform.getAccno() != 0) {
						System.out.println("Loan Acc type in action -------------->>>>"+module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode());
						System.out.println("Loan Acc no in action -------------->>>>"+recform.getAccno());
						System.out.println("Loan trndate in action -------------->>>>"+ LoansDelegate.getSysDate());
						if((module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode()!=null)&&(recform.getAccno()!=0))
						{
						lmobj = delegate.getQueryOnLoanStatus(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(), recform.getAccno(), LoansDelegate.getSysDate());
						}
						System.out.println("Loan MAster Object --------"+lmobj.getAccType());
						Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
						
						if(lmobj.getClosedt()!=null){
							request.setAttribute("msg", "Given Account Number is Closed");
						}
						else if (lmobj != null) {
						
							
							lntrnobj = delegate.getQueryLoanStatus(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(), recform.getAccno(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());
							System.out.println("LOanTran Object"+ lntrnobj.toString());
							request.setAttribute("LoanTranObject", lntrnobj);

							if (lntrnobj != null) 
							{
								recform.getLoanstatusform().setTxt_loanba(lntrnobj.getLoanBalance());
								//recform.setTxt_loanba(lntrnobj.getLoanBalance());
								// req.setAttribute("", arg1)
								statform.setTxt_loanba(lntrnobj.getLoanBalance());
								statform.setTxt_addpaid(String.valueOf(lntrnobj.getPrincipalPaid()));
								request.setAttribute("AdvancePaidAmount", String.valueOf(lntrnobj.getPrincipalPaid()));
								System.out.println("Lrnobj.penal amount---------->"+DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
								statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
								
								System.out.println("lntrnobj.getPrincipalBalance()" + lntrnobj.getPrincipalBalance());
								statform.setTxt_current_inst(DoubleFormat.toString(lntrnobj.getPrincipalPayable()));
								statform.setTxt_interest(DoubleFormat.toString(lntrnobj.getInterestPayable()));
								statform.setTxt_pinealInt(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
								statform.setTxt_otherchar(DoubleFormat.toString(lntrnobj.getOtherAmount()));
								statform.setTxt_extra(DoubleFormat.toString(lntrnobj.getExtraIntAmount()));
							
								/*double total_amt = obj_loanstatus.getPrincipalBalance()+obj_loanstatus.getPrincipalPayable()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount();*/
								double total_amt = lntrnobj.getPrincipalBalance()+lntrnobj.getPrincipalPayable()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
								System.out.println("*************Total Amount**************" + total_amt);
								statform.setTxt_tca(String.valueOf(total_amt));
								request.setAttribute("TotalLoanAmount", String.valueOf(total_amt));
								/*lbl_total_amount.setText(nf.format(total_amt));
						        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
						        lbl_close_amt.setText(nf.format(total_amt));*/
								double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
								System.out.println("*********************** Total Closeable amount****************" + close_amt);
								statform.setTxt_totcloamt(String.valueOf(close_amt));
								request.setAttribute("TotalClosableAmount", String.valueOf(close_amt));
							}
							/*System.out.println("Cid" + lmobj.getCustomerId());
							System.out.println("Mailing Address"+ lmobj.getMailingAddress());
							HashMap maps = null;
							maps = delegate.getAddress(lmobj.getCustomerId());
							Address address = (Address) maps.get(new Integer(lmobj.getMailingAddress()));
							System.out.println("Address object===========>"+ address.toString());
							System.out.println("Phno" + address.getPhno());
							System.out.println("Mobile" + address.getMobile());
							System.out.println("Fax" + address.getFax());
							System.out.println("Email" + address.getEmail());*/
							/*if (address != null) {
								histform.setPhone(address.getPhno());
								histform.setMob(address.getMobile());
								histform.setFax(address.getFax());
								histform.setEmail(address.getEmail());
							}
		*/
							/*arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
							if (!arraylist.isEmpty()) {
								System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
								lasttran.setLastprincipleamt(arraylist.get(6).toString());
								lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
								lasttran.setLastintamt(arraylist.get(4).toString());
								lasttran.setLastintamttrnamt(arraylist.get(5).toString());
								lasttran.setLastpiamt(arraylist.get(3).toString());
								lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
								lasttran.setLastotheramt(arraylist.get(1).toString());
							}*/

							/*arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
							System.out.println("NPA Status====>" + arraylist.size());
							System.out.println("NPADate" + histform.getNpadate());
							if (!arraylist.isEmpty()) {
								histform.setNpadate(arraylist.get(0).toString());
								histform.setNpastage(arraylist.get(1).toString());
								histform.setNpaprinciplefrom(arraylist.get(2).toString());
								histform.setNpaprincipleamt(Double.parseDouble(arraylist.get(3).toString()));
								histform.setNpaodprd(Integer.parseInt(arraylist.get(4).toString()));
								histform.setNpaintamt(Double.parseDouble(arraylist.get(5).toString()));
							}

							System.out.println("NPAStage" + histform.getNpastage());*/
							// delegate.getSurityCoBorrowerDetails(recovform.getAcctype(),recovform.getAccno());
							/*
							 * System.out.println("First Col"+arraylist.get(0));
							 * System.out.println("Second Col"+arraylist.get(1));
							 * System.out.println("Third Col"+arraylist.get(2));
							 */

						/*	System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
							System.out.println("Recovery Account Number==>"+ recovform.getAccno());

							System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
							ArrayList surity = delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno());
							System.out.println("size==============>"+ surity.size());
							for (int i = 0; i < surity.size(); i++) {
								System.out.println("data=====>" + surity.get(i));
							}
							request.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
							arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
							if (!arraylist.isEmpty()) {

								Iterator iterator = arraylist.iterator();
								while (iterator.hasNext()) {
									String strr = (String) iterator.next();
									if (strr.length() > 0) {
										histform.setLastnotice(strr);
									}
								}
							}*/

						}
					}
					//System.out.println("CID in Personal-------->"+cid);
					
					System.out.println("Are U Here ----------2");
					request.setAttribute("flag2",MenuNameReader.getScreenProperties("5018"));
					System.out.println("Are U Here ----------3");
					 
					 request.setAttribute("panelName", CommonPanelHeading.getLoanDetails());
					 System.out.println("Are U Here ----------4");
					 request.setAttribute("required", "Loan");
				}
			/*	if(recform.getCurrency()!=null){
					if(payForm.getCurrency().equalsIgnoreCase("Required"))
					{
						request.setAttribute("required","Required");
					}
		        }*/
				System.out.println("-------Scroll no not  valid");
				recform.getAccno();
				System.out.println("Account Number is---------"+recform.getAccno());
			
          		cashobject = recDelegate.getData((recform.getScrollno()),0,recDelegate.getSysDate());
          		System.out.println("Account Number When Scroll Number is 0--"+recform.getAccno());
          	     
          		System.out.println("Scroll 0 Account name--------"+cashobject.getAccname());
          		request.setAttribute("acc_nm",cashobject.getAccname());
          	    int scroll_verified=0;
          	   try
               {
                   scroll_verified = recDelegate.isScrollVerified(recform.getScrollno(),recDelegate.getSysDate());
                   System.out.println("Ther verified scroll number is--------"+scroll_verified);
               }
               catch(Exception ex)
               {
                   ex.printStackTrace();
               }
               
               System.out.println("scroll_verified == "+scroll_verified);
          		 if(scroll_verified==1)//Already Verified
               {
             	 System.out.println("Are You Coming Here??????");
             	 recform.setNewscrverify("already");
             	 System.out.println("Scroll No < "+recform.getScrollno()+" > Already Verified");
             	 
                  
              }
          	
				System.out.println("cashobject in action class"+cashobject);
				  if(cashobject!=null && cashobject.getAcctype()!= null)
					{ 
					
                    //sowmya
				    if(cashobject.getAttached().equals("F"))
                    	{
				    	  System.out.println("Are You getting Account Number????");
				    	  System.out.println(recform.getValue());
				        if(Integer.parseInt(recform.getValue().trim())==1)
                        	{
                            
				        	recform.setNewscrverify("null");
				        	if(cashobject.getAccno()!=null)
                            	{
                                if((!cashobject.getAcctype().startsWith("1016") && (Integer.parseInt(cashobject.getAccno())>0)) || cashobject.getAcctype().startsWith("1016"))
                                	{
                                    if(cashobject.getAcctype().startsWith("1001"))
                                    	{
                                        System.out.println("Share A/c Scroll No .. U Cant ");
                                        recform.setScrverify("scrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1009"))
                                    	{
                                    	System.out.println("Locker A/c Scroll No .. U Cant Verify");
                                    	recform.setScrverify("lkscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1013"))
                                    	{
                                    	System.out.println("Pygmy Agent Scroll No .. U Cant Verify");
                                    	recform.setScrverify("pyscrverify");
                                    	}
                                    else
                                    	{
                                        array_cashobject = recDelegate.forVerify(0,recDelegate.getSysDate());
                                        UVScrollNos(array_cashobject,recform,recDelegate,request);
                                    	}
                                	}
                                else
                                	{
                                	
                                    if(cashobject.getAcctype().startsWith("1001"))
                                    	{System.out.println("If new u should cum here .........");
                                       System.out.println("New Share A/c Scroll No .. U Cant Verify");
                                       recform.setNewscrverify("shnewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1002"))
                                    	{
                                    	 System.out.println("New SB A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("sbnewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1003"))
                                    	{
                                    	 System.out.println("New FD A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("fdnewscrverify");
                                    	}  
                                    else if(cashobject.getAcctype().startsWith("1004"))
                                    	{
                                    	 System.out.println("New RD A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("rdnewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1005"))
                                    	{
                                    	 System.out.println("New ReInvD A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("Renewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1007"))
                                    	{
                                    	 System.out.println("New CA A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("canewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1009"))
                                    	{
                                    	 System.out.println("New Locker A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("lknewscrverify");
                                    	}
                                    else if(cashobject.getAcctype().startsWith("1013"))
                                    	{
                                    	 System.out.println("New Pygmy Agent Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("pynewscrverify");
                                    	}
                                    else
                                    	{
                                    	 System.out.println("New A/c Scroll No .. U Cant Verify");
                                    	 recform.setNewscrverify("newscrverify");
                                        }
                                	}
                            }
                            else
                            	{
                            	 System.out.println("Error .... A/c No is null");
                            	 recform.setNewscrverify("errnewscrverify");
                            	}
                        }   
				      
                   
                        //sow 4 updation
				        System.out.println("udi%%%%%%%%%%((((((((((((("+Integer.parseInt(recform.getValue().trim()));
                        if(Integer.parseInt(recform.getValue().trim())==2)
                        {
                            //sowmya
                        	
                           System.out.println("udighfuisdhgfus(((((((((((((((");
                        	array_cashobject = recDelegate.forVerify(6,recDelegate.getSysDate());
                            UVScrollNos(array_cashobject,recform,recDelegate,request);
                           
                     
                        }
                   
                        else
                        {
                    	 System.out.println("Scroll No in Use .. Cant Update or Verify");
                    	 //recform.setNewscrverify("updateverify");
                       
                        }
				}
				    
					
				     
				}
				  else if(cashobject!=null)
					{
						System.out.println("If it null than it should cum here....");
						 System.out.println("Invalid Scroll No");
						 recform.setScrverify("Inscrverify");
						
					}
				
				  
				  else if(cashobject!=null && cashobject.getAcctype()!= null)
					{
						 System.out.println("This is Misc Rec Scroll No");
						 recform.setScrverify("msscrverify");
						
					}
				/*else{
				 CashObject cash = recDelegate.getData(recform.getScrollno(),0,recDelegate.getSysDate());
				   System.out.println("cash========="+cash.getAmount());
				   request.setAttribute("cashamt",cash);
				}
				*/
			}catch(ScrollNotFoundException ex)
			{
				 System.out.println(ex);
				
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}
   
      
        int txtaccno = recform.getAccno();
        System.out.println("account no====="+txtaccno);
        
        // start
        
    
		 
    if(recform.getAccno()==0)
       {
    	
        request.setAttribute("newacc","New Account");
       }
     
    	 
   /**account number is not null**/   
    
    if(recform.getAccno()!=0)
       {
    	request.setAttribute("accno",String.valueOf(recform.getAccno()));
    	
	if(string_code.startsWith("1009"))// LK
        	{
        	try 
                {
        			string_locker_type=recDelegate.getLockerType(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno());
        			lock_types = recDelegate.getLockersTypes(); 
        				
                    for(int i=0;i<lock_types.length;i++)
                     	{
                     		System.out.println("locker types before for loop==="+lock_types[i]);
                     	}
                     if(string_locker_type==null)
                     	{
                     		accnoexist = 1;
                     		System.out.println("Invalid Account No");
                     		recform.setAccexist("invalidaccount");
                     	
                     	}
         else
             {
              for(int i=0;i<lock_types.length;i++)
                 {
                  if(lock_types[i].equals(string_locker_type))
                     {
                                 
                            	String locdesc = Lockdesc[i];
                                request.setAttribute("lockerdescpn", locdesc); 
                                request.setAttribute("lockerindex",String.valueOf(i));
                                request.setAttribute("locktype",null);
                       }
                             
                   }
               }
      
          double lk_rent = 0.0;  
          double_locker_rent=recDelegate.getLockerRent(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),recDelegate.getSysDate());
          System.out.println("total rent in client...."+double_locker_rent);
            
          if(double_locker_rent==0.0)
            {
        	    accnoexist = 1;
             System.out.println("Locker Rent not defined");
             recform.setLk_amount("null");
            }
          else
            {
             lk_rent = double_locker_rent;
             recform.setLk_amount(String.valueOf(lk_rent));
             System.out.println("locker rent for particular type"+lk_rent);
             
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
        			recform.setAccexist("null");
        			System.out.println("share no..."+recform.getAccno());
        			
        			sharemasterobject=recDelegate.getShare(string_code,recform.getAccno());
        			System.out.println("share masater object"+sharemasterobject);
        			
        			if(sharemasterobject!=null)
        			share_cat = sharemasterobject.getShareType();   
                 
        			if(share_cat==0)
        				{ 
        				accnoexist = 1;
        				recform.setClosed("invalidacct");
        				}
        			else
        				{
        					for(int i=0;i<sharecategoryobject.length;i++)       						{
        							if(sharecategoryobject[i].getShCat()==share_cat)
        								{
        								String sharecategory =sharecategoryobject[i].getCatName();
        								request.setAttribute("sharecategoryname",sharecategory);
        								request.setAttribute("shareindex",String.valueOf(i));
        								request.setAttribute("sharecategory", null);
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
        		
		// for general account number
	System.out.println("accnoexist == "+accnoexist);
	   
	if(accnoexist==0)
        {
	    	try
				{	
        			accountobject=recDelegate.getAccount(null,module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),recDelegate.getSysDate());
        			
				if(accountobject!=null)
					{ 
					 recform.setAccexist("null");
					 if(accountobject.getAccStatus().equals("C"))
					 	{
						 recform.setAccountobject("accountobject");
						 System.out.println("Given account is Closed");
					 	}
					 else if(accountobject.getAccStatus().equals("I"))
					 	{
						 recform.setAccountobject("accountobject1");
						 System.out.println("InOperative Account");
					 	}
					 else if(accountobject.getVerified()==null)
					 	{
						 recform.setAccountobject("accountobject2");
						 System.out.println("Given account is not yet Verified");
					 	}
					 else if((accountobject.getDefaultInd()!=null)?accountobject.getDefaultInd().equals("T"):false)
					 	{  
						 recform.setAccountobject("accountobject3");
						 System.out.println("Default account");
					 	}
					 else if((accountobject.getFreezeInd()!=null)?accountobject.getFreezeInd().equals("T"):false)
					 	{
						 recform.setAccountobject("accountobject4");
						 System.out.println("Freezed account");
					 	}
					 else
					 	{
						 //fetching account name and customer ID
						 System.out.println("name of the account holder"+accountobject.getAccname());
						 //recform.setAccname(accountobject.getAccname());
						 request.setAttribute("accname",accountobject.getAccname());
						 System.out.println("Customer ID = "+accountobject.getCid());
						 if(accountobject.getCid()!=0)
						 {
						 int cid = accountobject.getCid();
						 request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
						 request.setAttribute("panelName", CommonPanelHeading.getPersonal());
						 if(string_code.startsWith("1008"))//Loans On Deposits
                         {
                             loanreportobject = recDelegate.getLoanDetails(string_code,recDelegate.getSysDate(),recform.getAccno());
                             
                             if(loanreportobject!=null)
                             {
                                 request.setAttribute("loan_bal",doubleToString(loanreportobject.getLoanBalance(),2));
                                 request.setAttribute("laon_ap","NA*");
                                 request.setAttribute("loan_po","NA*");
                                 request.setAttribute("loan_ci","NA*");
                                 request.setAttribute("loan_iat",doubleToString((loanreportobject.getInterestPaid()+loanreportobject.getExtraIntPaid()),2));
                                 request.setAttribute("loan_ipu",loanreportobject.getIntUptoDate());
                                 request.setAttribute("loan_ia",doubleToString((loanreportobject.getInterestPayable()-loanreportobject.getExtraIntPaid()),2));
                                 request.setAttribute("loan_ir",doubleToString(loanreportobject.getLoanIntRate(),2));
                                 request.setAttribute("loan_pi","NA*");
                                 request.setAttribute("loan_pir","NA*");
                                 request.setAttribute("loan_oc","NA*");
                                 request.setAttribute("loan_ei","NA*");
                                if(Integer.parseInt(recform.getAccounttype())!=0)
                                 if(moduleobject[Integer.parseInt(recform.getAccounttype())].getModuleCode()!=null && recform.getAccno()!=0){
                                 double mx_amt=recDelegate.getMaxPayableAmtonCurrentDay(moduleobject[Integer.parseInt(recform.getAccounttype())].getModuleCode(),recform.getAccno(),recDelegate.getSysDate());
                                 request.setAttribute("loan_aca",doubleToString(mx_amt,2));
                             }//swati
                         }
                         }
						 }
                         ///////////
                         //ship......21/07/2007
                         else if(string_code.startsWith("1010"))//Loans
                         {
                             loanmasterobject = recDelegate.getQueryOnLoanStatus(string_code,recform.getAccno(),recDelegate.getSysDate());
                             loantransactionobject = recDelegate.getQueryLoanStatus(string_code,recform.getAccno(),recDelegate.getSysDate(),user,tml);
                              
                             if(loanmasterobject!=null && loantransactionobject!=null)
                             { 
                            	 request.setAttribute("loan_bal",doubleToString(loantransactionobject.getLoanBalance(),2));
                            	 request.setAttribute("laon_ap",doubleToString(loantransactionobject.getPrincipalPaid(),2));
                            	 request.setAttribute("loan_po",doubleToString(loantransactionobject.getPrincipalBalance(),2));
                            	 request.setAttribute("loan_ci",doubleToString(loantransactionobject.getPrincipalPayable(),2));
                            	 request.setAttribute("loan_iat","NA*");
                            	 request.setAttribute("loan_ipu","NA*");
                            	 request.setAttribute("loan_ia",doubleToString(loantransactionobject.getInterestPayable(),2));
                            	 request.setAttribute("loan_ir",doubleToString(loanmasterobject.getInterestRate(),2));
                            	 request.setAttribute("loan_pi",doubleToString(loantransactionobject.getPenaltyAmount(),2));
                            	 request.setAttribute("loan_pir",doubleToString(loanmasterobject.getPenalRate(),2));
                            	 request.setAttribute("loan_oc",doubleToString(loantransactionobject.getOtherAmount(),2));
                            	 request.setAttribute("loan_ei",doubleToString(loantransactionobject.getExtraIntAmount(),2));
                            	 request.setAttribute("loan_aca",doubleToString((loantransactionobject.getLoanBalance()+loantransactionobject.getPenaltyAmount()+loantransactionobject.getInterestPayable()+loantransactionobject.getOtherAmount()-loantransactionobject.getExtraIntAmount()),2));
                             }
                         }
					 	}
					}
					else
						{
							recform.setAccexist("accexist");
							System.out.println("Given account number not found.....1");
						}
                  	
			}
			catch(Exception exception)
				{
					exception.printStackTrace();
				}
        }// end of accnoexist==0
	    else
           {
               accnoexist = 0;
           }
        
		
       }
    	// end of txt_account_no
	 	
    double amount = recform.getAmount();
   
    /**if amount is not null**/
	if(recform.getAmount()!=0)
		{
         
		 if(string_code.startsWith("1001"))// SH
		 	{
			 int minshares = 0;
			 double amt = 0.00,minamt = 0.00,shareamt = 0.00;
			 try
				{
				 System.out.println("share category"+recform.getSharecat());
				 minshares = sharecategoryobject[Integer.valueOf(recform.getSharecat())].getMinShare();
				 
				 shareamt = sharecategoryobject[Integer.valueOf(recform.getSharecat())].getShareVal();
				 minamt = minshares * shareamt;
				 System.out.println("minimum amount===="+minamt);
				 amt = (amount);
								
				 if(amt>=minamt)
					{
					 
					 for(int i=0;i<sharecategoryobject.length;i++)
						{
						 	if(sharecategoryobject[i].getCatName().equals(recform.getSharecat().toString()))
						 		share_cat=sharecategoryobject[i].getShCat();
						
						}
								
								
					}
							
				 else
				 	{
					recform.setAmtverify("shareamt");
					 
					 System.out.println("Min Share Amount for Category < "+recform.getSharecat()+" >\n should be Rs. "+minamt+"");
					}
				}
				catch(Exception e1)
					{
						e1.printStackTrace();
					}
					
		 	}
		else if(string_code.startsWith("1016"))// PO
			{
				double comm_amt = 0.00;
				System.out.println("PayOrder ...ac_no==0....");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1111===========>"+recform.getCustacno());
		   
				// po-amount*********************comm_amt,tot_amt
			
			if((recform.getPoamount())!=0)
				{
					double amt=recform.getPoamount();
					System.out.println("amount from jsp page"+amt);
				try
					{				
						System.out.println("customer Account type####"+recform.getCustacctype());
						if(amt>module_obj_array[Integer.valueOf(recform.getCustacctype())].getMaxAmount())
							{
								double maxamt=module_obj_array[Integer.valueOf(recform.getCustacctype())].getMaxAmount();	
								recform.setPayorderamt(maxamt);
								recform.setAmtverify("POmaxamt");
								System.out.println("PayOrder Amount cannot be more than "+module_obj_array1[Integer.valueOf(recform.getCustacctype())].getMaxAmount());
							
							}
					
						else if(amt<module_obj_array[Integer.valueOf(recform.getCustacctype())].getMinBal())
							{
								double minimum=module_obj_array[Integer.valueOf(recform.getCustacctype())].getMinBal();
								//recform.setMiniamt(minimum);
								recform.setAmtverify("POminamt");
								System.out.println("PayOrder Amount cannot be less than "+module_obj_array[Integer.valueOf(recform.getCustacctype())].getMinBal());
					
							}
					else
						{
				    
						if(recform.getCusttype().equalsIgnoreCase("Customer"))
							{
						try
							{
									System.out.println("inside customer");
									accsubcategoryobject1=recDelegate.getAccSubCategories(0);
				            		System.out.println("PO111111");
				            		
				            for(int i=0;i<accsubcategoryobject1.length;i++)
				            	{
				                	if((recform.getDescription()).equals(accsubcategoryobject1[i].getSubCategoryCode()));
				                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
				            	}
				            System.out.println("PO22222");
				            AccSubCategoryObject[] acc1=recDelegate.getAccSubCategories(1);
				            
				            for(int i=0;i<acc1.length;i++)
				            	{
				            		if((recform.getDescription()).equals(acc1[i].getSubCategoryCode()))
				                		po_descrptn = acc1[i].getSubCategoryCode();
				            	}
							}
							catch(Exception e1)
							{
								
								System.out.println("PO Exception :" +e1);// Joption
							}
				        
							System.out.println("po_desc Customer.....= "+po_descrptn);
				        try
				        	{
				            String date=recDelegate.getSysDate();
				            request.setAttribute("date", date);
				        	comm_amt = recDelegate.getCommission(string_code,po_descrptn,recform.getPoamount(),date);
				        	
				        	}
				        		catch(Exception e1)
				        			{
				        				System.out.println("PO Exception11 :" +e1);// JOptionPane
				        			}
							}
				 else if(recform.getCusttype().equalsIgnoreCase("Non Customer"))
				    {
				      try
			            {
				         
				           System.out.println("inside noncustomer");
				            accsubcategoryobject1 =recDelegate.getAccSubCategories(2);
				            
				            System.out.println("PO111111");
				            
				            for(int i=0;i<accsubcategoryobject1.length;i++)
				            	{
				                if((recform.getDescription()).equals(accsubcategoryobject1[i].getSubCategoryDesc()))
				                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
				            	}
				            // /////////////
			            }
			            catch(Exception exception)
					    	{
					        	exception.printStackTrace();
					    	}
			            
				        try
				        	{
				            	comm_amt = recDelegate.getCommission(string_code,po_descrptn,recform.getPoamount(),recDelegate.getSysDate());
				            	
				            	recform.setCommAcc(comm_amt);
				            	recform.setTotal(String.valueOf((recform.getPoamount())+comm_amt));
				        	}
				        		catch(Exception e1)
				        			{
				        				System.out.println("PO Exception22 :" +e1);
				        			}
				    		}
				    
						}
			}
			catch(Exception exception_pay_amount)
			{
				exception_pay_amount.printStackTrace();
			}
		}
	
	}// end of po
	else if(string_code.startsWith("1009"))// LK
		{
		  
		 double lock_amt = recform.getAmount();
		 System.out.println("inside gen rec LK lock_amt = "+recform.getLockertype());
		 lk_ty =null;
		 lock_desc =recDelegate.getLockersDesc();
		 
		 System.out.println("it is not coming inside the loop"+lock_desc.length);
		 
		 for(int i=0;i<lock_desc.length;i++)
		   {
			 
		    if((recform.getLockertype()).equals(lock_desc[i])){
		      lk_ty =lock_desc[i];
		      
		    }
		    
		   }
		    
		  System.out.println("inside gen rec LK lock_type = "+lk_ty);
		    
		 try
		 	{
		        String date =recDelegate.getSysDate();
		    	double rent = recDelegate.getRent(lk_ty,365,101,date);
		    	System.out.println("lk rent to be collected == "+rent);
		    	if(lock_amt<rent)
		        	{
		    		    recform.setAmtverify("lkamt");
		    		    request.setAttribute("amt_err","U r Paying less rent...rent to be paid is Rs. ");
		    			System.out.println("U r Paying less rent...rent to be paid is Rs. "+rent+"");
		            
		        	}
		        
		 	}
			catch(Exception e1)
				{
					System.out.println("Retrieve Exception :" +e1);
				}
		}
	else
		{
			System.out.println("not a share or LK or PO ...ac_no==0....");
			double amt = 0.00,minamt = 0.00;
         
         try
         {
             minamt = module_obj_array[Integer.valueOf(recform.getAccounttype())].getMinBal();
             System.out.println(minamt);   
             amt = (amount);
             
             if(amt>=minamt)
             	{
            	 System.out.println("currency denomination");
             	}
             else
             	{
            	recform.setAmtverify("nor_minamt");
            	 System.out.println("Min Amount should be Rs. "+minamt+"");
             	}
         }
         catch(Exception e1)
         	{
        	 System.out.println("Retrieve Exception :" +e1);
         	}
         
		}
	}
	
// Existing Account
	 if(recform.getAmount()>0 && (recform.getAccno()!=0))
	{
		if(accnoexist==0)
		{
			if(string_code.startsWith("1004"))
			{
				System.out.println("u r inside RD ");
				double amt=recform.getAmount();
				
				String Ldate=accountobject.getLastTrnDate();
				double days=accountobject.getDepositdays();// for deposit days;
				double depamt=accountobject.getAmount();
				double rdbal=accountobject.getShadowBalance();// rd balance
				double int_amt = accountobject.getIntAmount();// interest
				String date =recDelegate.getSysDate();
				System.out.println("Last Trn Date ="+ date);
				System.out.println("Amount : "+amt);
				System.out.println("DepAmt : "+depamt);
				System.out.println("days   : "+days);
				System.out.println("rdBal  : "+rdbal);
				System.out.println("rd interest  : "+int_amt);
				System.out.println("Check: "+((depamt*days)+int_amt-rdbal));
				
				if(amt<depamt)
					{
						recform.setClosed("installment");
						System.out.println("Your installment amount is "+depamt+"\n you are paying less than that");
						
					}
				else if(amt>((depamt*days)+int_amt-rdbal))
					{
						recform.setClosed("paidamt");
						System.out.println("Paid amt is more than amount to pay");
					
					}
				else if(amt%depamt!=0)
					{
						recform.setClosed("multipleof");
						System.out.println("Amount is not multiple of installment amount");
					
					}
				else
				{
					System.out.println("...RD...");
				
				}		
			}
			else if(string_code.startsWith("1008"))
			{
				double amt=recform.getAmount();
				double max_amt = 0.0;
				
				System.out.println("LD amt = "+amt);
				try
					{
						String date = recDelegate.getSysDate();
						max_amt = recDelegate.getMaxPayableAmtonCurrentDay(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),date);
						System.out.println("ld max amt = "+max_amt);
					}
				catch(Exception ex)
					{
						ex.printStackTrace();
					}
				
			if(amt > max_amt)
				{
					recform.setClosed("paidamt");
					System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
					
				}
				else if(amt==0)
				{
					recform.setClosed("entervalid");
					System.out.println("Enter Valid Amount");
				
				}
			}
			
			else if(string_code.startsWith("1010"))
			{
				double amt = recform.getAmount();
				double max_amt = 0.0;
				System.out.println("LN amt = "+amt);
			
				try
				{
				    String date=recDelegate.getSysDate();
					max_amt = recDelegate.checkLNAmount(module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleCode(),recform.getAccno(),date,user,tml);
				    
				    if(amt > max_amt)
						{
				    		recform.setClosed("paidamt");	
				    		System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
						
						}
					else if(amt==0)
						{
							recform.setClosed("entervalid");	
							System.out.println("Enter Valid Amount");
						
						}
					else
					{
					    
					    
					    // cb_currDenom.requestFocus();
					    
					}
				}
				catch(Exception ex)
				{
				    ex.printStackTrace();
				}
				// /////////////
			}
		
		
	}// end of amount
	}
		
	if(string_code.startsWith("1016"))
		{
			if((recform.getCustacno())!=0)
				{
     		
					System.out.println("account no in PO"+recform.getCustacno());
					System.out.println("focus lost from PO accno");
					System.out.println("code of the module@@@@@@@@@@@@@@@"+postring_code);
		    
				    if(postring_code.startsWith("1009"))// LK
	                {
				        try 
	                    {
	                         String string_locker_type=recDelegate.getLockerType(module_obj_array1[Integer.valueOf(recform.getCustacctype())].getModuleCode(),recform.getCustacno());
	                         
	                         System.out.println("locker type in po=====++++"+string_locker_type);  
	                         if(string_locker_type==null)
	                         { 
	                         	 accnoexist = 1;
	                         	 recform.setClosed("invalidacct");
	                             System.out.println("Invalid account no");
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
				        catch (RecordsNotFoundException e1) 
						{
	                         e1.printStackTrace();
	                    }
	                }
				    
				    else if(module_obj_array1[Integer.valueOf(recform.getCustacctype())].getModuleCode().startsWith("1001"))//SH
	                {
				        try 
	                    {
	                         String string_share_cat=String.valueOf(recDelegate.getShare(postring_code,recform.getCustacno()));
	                         
	                         if(string_share_cat.equals("null"))
	                         { 
	                         	 accnoexist = 1;
	                         	 recform.setClosed("invalidacct");
	                         	System.out.println("Invalid account no");
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
				    
				    if(accnoexist==0)
			        {
				    	
				    	try
						{	
			        		 accountobject=recDelegate.getAccount(null,module_obj_array1[Integer.valueOf(recform.getCustacctype())].getModuleCode(),recform.getCustacno(),recDelegate.getSysDate());
							
							if(accountobject!=null)
							{
								 recform.setAccexist("null");
								if(accountobject.getAccStatus().equals("C"))
								{
			                        recform.setAccountobject("accountobject");
			                        System.out.println("Given account is Closed");
								}
								else if(accountobject.getAccStatus().equals("I"))
								{
									recform.setAccountobject("accountobject1");
			                        System.out.println("InOperative Account");
								}
								else if(accountobject.getVerified()==null)
								{
									recform.setAccountobject("accountobject2");
			                        System.out.println("Given account is not yet Verified");
								}
								else if((accountobject.getDefaultInd()!=null)?accountobject.getDefaultInd().equals("T"):false)
								{  
									recform.setAccountobject("accountobject3");
			                        System.out.println("Default account");
								}
								else if((accountobject.getFreezeInd()!=null)?accountobject.getFreezeInd().equals("T"):false)
								{
									recform.setAccountobject("accountobject4");
			                        System.out.println("Freezed account");
								}
								else
								{
									
									System.out.println("name of the account holder"+accountobject.getAccname());
									recform.setPurchase(accountobject.getAccname());
								System.out.println("-------HI-----------");
									System.out.println("Customer ID = "+accountobject.getCid());
									int cid =accountobject.getCid();
									System.out.println("CID BY SHREYA IS ===="+cid);
								   String addr = recDelegate.getCustomerAddress(cid);
								   System.out.println("Adress in action is --------------"+addr);
									request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
									request.setAttribute("address",recDelegate.getCustomerAddress(cid));
									
									
							        try
							        {
							        	accsubcategoryobject1 =recDelegate.getAccSubCategories(0);
							        	cust_subcat = recDelegate.getCustSubCat(module_obj_array1[Integer.parseInt(recform.getCustacctype())].getModuleCode(),String.valueOf(recform.getCustacno()));
							        	for(int i=0;i<accsubcategoryobject1.length;i++)
						            	{
						                	if(cust_subcat.equals(accsubcategoryobject1[i].getSubCategoryDesc()));
						                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
						                    request.setAttribute("custsubcat",String.valueOf(po_descrptn));
						                    request.setAttribute("cust_cat",String.valueOf(cust_subcat));
						            	}
							        	
							        }
						 	        catch(Exception exception)
								    {
								        exception.printStackTrace();
								    }
							   
								}
							}
							else
							{
							  	recform.setAccexist("accexist");
								System.out.println("Given account number not found.....1");
								
							 }
			                  	
						}
						catch(Exception exception)
						{
							exception.printStackTrace();
						}
			        }
				    // end of accnoexist==0
				       else
			           {
			               accnoexist = 0;
			           }
			           
					System.out.println("hi hello");
				}
	 
	if((recform.getPoamount())!=0)
		{
			double comm_amt = 0.00;
			double amt=recform.getPoamount();
			System.out.println("amount from jsp page"+amt);
			try
				{				
					
					if(amt>module_obj_array[Integer.valueOf(recform.getAccounttype())].getMaxAmount())
						{
							recform.setPayorderamt(module_obj_array[Integer.valueOf(recform.getAccounttype())].getMaxAmount());
							System.out.println("PayOrder Amount cannot be more than "+module_obj_array[Integer.valueOf(recform.getAccounttype())].getMaxAmount());
						
						}
					else if(amt<module_obj_array[Integer.valueOf(recform.getAccounttype())].getMinBal())
						{
							recform.setMiniamt(module_obj_array1[Integer.valueOf(recform.getAccounttype())].getMinBal());
							System.out.println("PayOrder Amount cannot be less than "+module_obj_array1[Integer.valueOf(recform.getAccounttype())].getMinBal());
							
						}
					else
						{
							if(recform.getCusttype().equalsIgnoreCase("Customer"))
								{
									try
										{
											System.out.println("inside customer");
											accsubcategoryobject1=recDelegate.getAccSubCategories(0);
											System.out.println("PO111111");
										for(int i=0;i<accsubcategoryobject1.length;i++)
										{
										if(recform.getDescription().equals(accsubcategoryobject1[i].getSubCategoryDesc()))
											po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
										}
									    
									AccSubCategoryObject[] acc1=recDelegate.getAccSubCategories(1);
									System.out.println("PO22222"+acc1);
									for(int i=0;i<acc1.length;i++)
										{
										if(cust_subcat.equals(acc1[i].getSubCategoryCode()))
											po_descrptn = acc1[i].getSubCategoryCode();
										}
									}
								catch(Exception e1)
								{
									System.out.println("hi.....");
									System.out.println("PO Exception :" +e1);// Joption
								}
			        
								System.out.println("po_desc Customer.....= "+po_descrptn);
			        
			        try
			        	{
			            
			        	comm_amt = recDelegate.getCommission(module_obj_array[Integer.parseInt(recform.getAccounttype())].getModuleCode(),po_descrptn,recform.getPoamount(),recDelegate.getSysDate());
			        	
			        	}
			        	catch(Exception e1)
			        	{
			        		System.out.println("hello.....po amount");
			        		System.out.println("PO Exception11 :" +e1);// JOptionPane
			        	}
			    }
			    else if(recform.getCusttype().equalsIgnoreCase("Non Customer"))
			    {
			        try
		            {
			         	System.out.println("inside noncustomer");
			            accsubcategoryobject1 =recDelegate.getAccSubCategories(2);
			            
			            System.out.println("PO111111");
			           
			            for(int i=0;i<accsubcategoryobject1.length;i++)
			            	{
			                if((recform.getDescription()).equals(accsubcategoryobject1[i].getSubCategoryCode()))
			                    po_descrptn = accsubcategoryobject1[i].getSubCategoryCode();
			            	}
			            // /////////////
		            }
		            catch(Exception exception)
				    {
				        exception.printStackTrace();
				    }
		            
		            System.out.println("po_desc Non-Customer.....= "+po_descrptn);
			    }
			        try
			        	{	
			        	
			        		comm_amt = recDelegate.getCommission(string_code,po_descrptn,recform.getPoamount(),recDelegate.getSysDate());
			        		System.out.println("comm_amt.....55555.= "+comm_amt);
			        		recform.setCommAcc(comm_amt);
			        		recform.setTotal(String.valueOf((recform.getPoamount())+comm_amt));
			        	}
			        	catch(Exception e1)
			        	{
			        		System.out.println("hello.hi....");
			        		System.out.println("PO Exception22 :" +e1);
			        	}
			  
				}
		}
		catch(Exception exception_pay_amount)
		{
			exception_pay_amount.printStackTrace();
		}
		}
	}		
	System.out.println("button value in recform======"+recform.getBut_value());
	if(recform.getBut_value().equalsIgnoreCase("Receive")||recform.getBut_value().equalsIgnoreCase("Confirm"))
		{
			
			recform.setGen_scroll(0);
			recform.setClosed("null");
            int proceed = 0;
           
            boolean continue_transaction = false;
            
            try
            {
                proceed = recDelegate.checkDailyStatus(recDelegate.getSysDate(),0);
                System.out.println("proceed value"+proceed);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
            if(proceed==3)// day close
            { 
            	recform.setClosed("dayclose");
                continue_transaction = false;
               
            }
            else if(proceed==1)// cash close
            {
            	recform.setClosed("cashclose");
            	continue_transaction = false;
                
            }
            else if(proceed==-1)
            {
                
            	recform.setClosed("noentry");
            	continue_transaction = false;
            
            }
            else
                continue_transaction = true;
            
            if(continue_transaction==true)
            {
            	if(proceed==0)
            	{
            		
            		boolean update = false;
                     
                     if(recform.getBut_value().equalsIgnoreCase("Confirm"))
                     {
                        
                    	 int scroll_verified = 0;
                         
                         try
                         {
                             scroll_verified = recDelegate.isScrollVerified(recform.getScrollno(),recDelegate.getSysDate());
                             System.out.println("Ther verified scroll number is--------"+scroll_verified);
                         }
                         catch(Exception ex)
                         {
                             ex.printStackTrace();
                         }
                         
                         System.out.println("scroll_verified == "+scroll_verified);
                         
                         if(scroll_verified==1)//Already Verified
                         {
                        	 System.out.println("Are You Coming Here??????");
                        	 recform.setClosed("already");
                        	 System.out.println("Scroll No < "+recform.getScrollno()+" > Already Verified");
                             
                         }
                         else if(scroll_verified==2)//Not Yet Verified
                         {
                             update = true;
                         }
                         else if(scroll_verified==3)//Error in DB Entries
                         {
                        	 recform.setClosed("errordb");
                        	 System.out.println("Error : Check DB Entries");
                            
                         }
                         else if(scroll_verified==-1)//Invalid Scroll No
                         {
                        	 recform.setClosed("invalid");
                        	 System.out.println("Invalid Scroll No");
                            
                         }
                         else if(scroll_verified==0)//Error
                         {
                        	 recform.setClosed("error");
                        	 System.out.println("Error:");
                            
                         }
                     }
                     
                     if((recform.getBut_value().equalsIgnoreCase("Receive") && update==false) || (recform.getBut_value().equalsIgnoreCase("Confirm") && update==true))
                     {
                         //sowmya
                    	 
                    	 store(recDelegate, recform, 1, 1,request);
                    	 System.out.println("Enter the Currency Denominations");
                        /*// if(recform.getUpdate().equalsIgnoreCase("true"))
                         // {
                        	if(!recform.getAccounttype().startsWith("1016"))
                        	 {
                        	if(recform.getAccname().length()==0)
                        	 {
                        		//request.setAttribute("displaymsg","Please Enter account name Details Correctly" );
                        	 }
                        	 else if(recform.getAmount()==0.0)
                        	 {
                        		 request.setAttribute("displaymsg","Please Enter amount Details Correctly" );
                        		
                        	 }
                        	 else{
                        		 
                        		 store(recDelegate, recform, 1, 1,request);
                        	 }
                        	 }
                        	else if(recform.getAccounttype().startsWith("1016")){
                        		
                        		System.out.println("i am in po method exc");
									store(recDelegate, recform, 1, 1,request);
                        	}
                        		
                        		
                        	
                          //}
*/                         
                      }
                   
                 }
            }
		}
	
	loop:if(recform.getBut_value().equalsIgnoreCase("Verify"))
	{
        int proceed = 0;
        boolean continue_transaction = false;
        
        try
        {
            proceed =recDelegate.checkDailyStatus(recDelegate.getSysDate(),0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
       
        if(proceed==3)//day close
        {
           recform.setClosed("dayclose");
           System.out.println("Day closed\n You can't do any Transactions");
           continue_transaction = false;
           
        }
        else if(proceed==1)//cash close
        {
        	recform.setClosed("cashclose");
        	System.out.println("Cash closed\n You can't do any Transactions");
            continue_transaction = false;
           
        }
        else if(proceed==-1)
        {
           
            continue_transaction = false;
            recform.setClosed("noentry");
            System.out.println("Error : no entry in Daily status for today");
        }
        else
            continue_transaction = true;
        
        if(continue_transaction==true)
        {
            
            int scroll_verified = 0;
            
            try
            {
                
            	scroll_verified = recDelegate.isScrollVerified((recform.getScrollno()),recDelegate.getSysDate());
            	System.out.println("scroll verified here after verification is========"+scroll_verified);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
                        
            if(scroll_verified==1)//Already Verified
            {
            	System.out.println("Scroll No < "+recform.getScrollno()+" > Already Verified");
            	recform.setScrverify("alverify");
                
            }
            else if(scroll_verified==2)//Not Yet Verified
            {
                int flag = 1;
                
               System.out.println(validateDataEntered(recform, recDelegate, request));
                if(!validateDataEntered(recform,recDelegate,request))
                {
                    flag = 0;
                    System.out.println("Flag Value ==="+flag);
                }
                ///////
                
                if(flag==0)
                {
                    try
                    {
                        System.out.println("Are u verifyting here ?????");
                        cashobject.uv.setVerTml(user);
                        cashobject.uv.setVerId(tml);
                        cashobject.uv.setVerDate(recDelegate.getSysDate()+""+" "+""+recDelegate.getSysTime());
                        String ver_module = module_obj_array[Integer.valueOf(recform.getAccounttype())].getModuleAbbrv();
                        for(int i=0;i<module_obj_array.length;i++)
                        {
                            if(module_obj_array[i].getModuleAbbrv().equals(ver_module))
                                string_code=String.valueOf(module_obj_array[i].getModuleCode()); 
                        }
                        if(string_code.startsWith("1013"))//Pygmy
                        {
                        	recform.setClosed("cannotverify");
                        	System.out.println("You cannot verify this Scroll No");
                            break loop; 
                        }
                        
                        for(int i=0;i<module_obj_array.length;i++)
                        {
                            if(module_obj_array[i].getModuleAbbrv().equals(ver_module))
                            {
                                cashobject.setGLRefCode(String.valueOf(module_obj_array[i].getModuleCode()));
                                
                            }
                        }
                        
                        
                        
                        if(recform.getVer_button().equalsIgnoreCase("true"))
                        {
                           
                            if(cashobject.getAcctype().startsWith("1016"))
                            {
                                int po_srno = 0;
                                po_srno = recDelegate.verifyDayCashPO(cashobject);
                                if(po_srno>0)
                                {
                                	recform.setClosed("scrollvreify");
                                	System.out.println("Scroll No < "+recform.getScrollno()+" > Verified Successfully...PayOrder sr_no = < "+po_srno+" >");
                                }
                                else
                                {
                                	recform.setClosed("poerror");
                                	System.out.println("PO Error: Verification failed");
                                }
                            }
                            else
                            {
                                if(recDelegate.verifyDayCash(cashobject))
                                {
                                	recform.setClosed("scrollvreify");
                                	System.out.println("Scroll No < "+recform.getScrollno()+" > Verified Successfully");
                                }
                                else
                                {
                                	recform.setClosed("errorverify");
                                	System.out.println("Error: Verification failed");
                                }
                            }
                            //
                            
                           // obj_currency.setVisible(true);
                    
                        }
                      
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                	
                   
                }
            }
            else if(scroll_verified==3)//Error in DB Entries
            {
            	recform.setClosed("errordb");
            	System.out.println("Eror : Check DB Entries");
              
            }
            else if(scroll_verified==-1)//Invalid Scroll No
            {
            	recform.setClosed("invalid");
            	System.out.println("Invalid Scroll No");
                
            }
            else if(scroll_verified==0)//Error
            {
                recform.setClosed("error");
            	System.out.println("Error: ");
                
            }
        }
      
        //////////////
        
      }
	else if(recform.getBut_value().equalsIgnoreCase("Delete"))
	{
		System.out.println("But Value==="+recform.getBut_value());
        int scroll_verified = 0;
        
        try
        {
            scroll_verified = recDelegate.isScrollVerified(recform.getScrollno(),recDelegate.getSysDate());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        System.out.println("scroll_verified == "+scroll_verified);
        
        if(scroll_verified==1)//Already Verified
        {
            recform.setClosed("already");
        	System.out.println("Scroll No < "+recform.getScrollno()+" > Already Verified");
           
        }
        else if(scroll_verified==2)//Not Yet Verified
        {
           if(recform.getDelete().equalsIgnoreCase("true"))
            {
                try
                {
                    System.out.println("scroll no to be deleted is " +recform.getScrollno());
                    recDelegate.deleteData(recform.getScrollno(), 1, CashDelegate.getSysDate(), CashDelegate.getSysTime());
                    
                    //sowmya
                    //obj_currency.setVisible(true);
                    //
                    recform.setDelete("deleted");
                    recform.setNewscrverify("");
                    System.out.println("Scroll No < "+recform.getScrollno()+" > Deleted Successfully");
                  
                }
                catch(RemoteException ex)
                {
                    new UserException(ex);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        else if(scroll_verified==3)//Error in DB Entries
        {
        	recform.setClosed("errordb"); 
        	System.out.println("Eror : Check DB Entries");
           
        }
        else if(scroll_verified==-1)//Invalid Scroll No
        {
        	recform.setClosed("invalid"); 
        	System.out.println("Invalid Scroll No");
            
        }
        else if(scroll_verified==0)//Error
        {
        	recform.setClosed("error");
        	System.out.println("Error: ");
         
        }
        ///////////
	}
	else if(recform.getBut_value().equalsIgnoreCase("Confirm"))
	{
	    //sowmya
        int scroll_verified = 0;
        
        try
        {
            scroll_verified = recDelegate.isScrollVerified(recform.getScrollno(),recDelegate.getSysDate());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        System.out.println("scroll_verified == "+scroll_verified);
        
        if(scroll_verified==1)//Already Verified
        {
        	recform.setClosed("already"); 
        	System.out.println("Scroll No < "+recform.getScrollno()+" > Already Verified");
           
        }
        else if(scroll_verified==2)//Not Yet Verified
        {
            //sowmya
            try
            {
                if(string_code.startsWith("1013"))
                {
                	System.out.println("inside 1013....");
                    boolean pag = recDelegate.checkPAG(recform.getScrollno(),recDelegate.getSysDate());
                    
                    System.out.println("pag = "+pag);
                    
                    if(pag==true)
                    {
                    	System.out.println("pag==true");
                        
                    }
                    else
                    {
                    	System.out.println("pag==false");
                       
                    }
                }
                else
                {
                	System.out.println("not a pag");
                   
                }
            }
            catch(Exception ex)
            {
            	System.out.println(ex);
            }
           
           /* if(recform.getAccno()>0)
            {
            	recform.setClosed("txt_name");
            	System.out.println("txt_name is not editable");
               
            }
            else
            {
            	recform.setClosed("txt_nameedit");
            	System.out.println("txt_name is  editable");
                
            }
        */
        }
        else if(scroll_verified==3)//Error in DB Entries
        {
        	recform.setClosed("errordb");
        	System.out.println("Eror : Check DB Entries");
           
        }
        else if(scroll_verified==-1)//Invalid Scroll No
        {
        	recform.setClosed("invalid");
        	System.out.println("Invalid Scroll No");
        
        }
        else if(scroll_verified==0)//Error
        {
        	recform.setClosed("error");
        	System.out.println("Error: ");
           
        }
       
        setReceiptparamform(request, path, recDelegate);
        //////////////
	}
	if(recform.getRequired()!=null){
		if(recform.getRequired().equalsIgnoreCase("Required"))
		{
			request.setAttribute("required","Required");
		}
	}
	//setReceiptparamform(request, path, recDelegate);
	
		
	}
	
	else if(map.getPath().equalsIgnoreCase("/MiscellaneousMenu"))
	{
		System.out.println("this is the start of the Miscellaneous Menu");
		MiscellaneousForm msForm=(MiscellaneousForm)form;
		CashDelegate msDelegate=new CashDelegate();
		int[] array_int_glcodes= msDelegate.getGLCodes(msDelegate.getSysDate(),1);
		msForm.setMsClosed("null");
		msForm.setGen_scroll(0);
		msForm.setUpdate_scroll(0);
		request.setAttribute("unverifyrunnable",0);
		
		double tml_runbal = msDelegate.getCashTmlRunningBalance(tml,msDelegate.getSysDate());
		request.setAttribute("Terminal",tml_runbal);
		
		request.setAttribute("glcode",array_int_glcodes);
		session=request.getSession(true);
		moduleobject =msDelegate.getMainModules(2,"1012000");
	
		System.out.println("The moduleobject is"+moduleobject);
		request.setAttribute("gltype",moduleobject);
		
		msForm.setGl_name(msDelegate.getGLName(msForm.getGl_code()));
	try{ 
		if(MenuNameReader.containsKeyScreen(msForm.getPageId()))
			{
			String status=msDelegate.checkDailyStatus2();
            if(status==null)
	           {
				path=MenuNameReader.getScreenProperties(msForm.getPageId());
				System.out.println("path in action class=======>"+path);
				request.setAttribute("glnmcd", msDelegate.getGlCodeNames());
				String glname[][]=msDelegate.getGlCodeNames();
				

			/*	String[] glnmcd = msDelegate.getGlNameCode();
				System.out.println("glname and code"+glnmcd.length);
				request.setAttribute("glnmcd", glnmcd);*/
				
				request.setAttribute("pageId",path);
				setPaymentinitParams(request,path,msDelegate);
				request.setAttribute("submit", msForm.getValue());
				request.setAttribute("scrollno",msForm.getScroll_no());
				return map.findForward(ResultHelp.getSuccess());
	           }
            else
	         {
	        	  path=MenuNameReader.getScreenProperties("0000");
                   setErrorPageElements("  "+status,request,path);
                   return map.findForward(ResultHelp.getError());
	          }	
 			}
       else
       	{
      	 return map.findForward(ResultHelp.getError());
       	}
		}catch (Exception e) 
			{		
  	  		e.printStackTrace(); 
  	  		return map.findForward(ResultHelp.getSuccess());
			}		
	}
	
	else if(map.getPath().equalsIgnoreCase("/Miscellaneous"))
	{
		System.out.println("i am in miscellaneous.............");
		CashDelegate msDelegate=new CashDelegate();
	//	equest.setAttribute("submit",);
		request.setAttribute("glnmcd", msDelegate.getGlCodeNames());
		MiscellaneousForm msForm=(MiscellaneousForm)form;
		
		request.setAttribute("scrollno",msForm.getScroll_no());
		System.out.println("The value of submit-----------"+msForm.getValue());
		System.out.println("The scroll no is----------"+msForm.getScroll_no());
		System.out.println("The narration now is------"+msForm.getNarration());
		double sum=0.0,tot=0.0;
		
		msForm.setMsClosed("null");
		msForm.setGen_scroll(0);
		msForm.setUpdate_scroll(0);
		//msForm.setRefund(0);
		//msForm.setRec_amt(0);
		
		request.setAttribute("submit",msForm.getValue());
		String Button_value=msForm.getBut_value();
		System.out.println("Button Value===>"+Button_value); 
		System.out.println("The value of submit-----------"+msForm.getValue());
		double tml_runbal = msDelegate.getCashTmlRunningBalance(tml,msDelegate.getSysDate());
		request.setAttribute("Terminal",tml_runbal);
		    
		if(Integer.parseInt(msForm.getValue().trim())==2)
		    {
		        double total_runbal = msDelegate.getAllCashTmlRunningBalance(msDelegate.getSysDate());
		        request.setAttribute("Allterminal",total_runbal);
		    }
		
		
		
		
		 
		int[] array_int_glcodes= msDelegate.getGLCodes(msDelegate.getSysDate(),1);
		request.setAttribute("glcode",array_int_glcodes);
		
		moduleobject =msDelegate.getMainModules(2,"1012000");
		request.setAttribute("gltype",moduleobject);
		
		String glname = msDelegate.getGLName(msForm.getGl_code());
		request.setAttribute("glname", glname);
		
		
		msForm.setGl_name(msDelegate.getGLName(msForm.getGl_code()));
		System.out.println("G:Lamount from jsp"+msForm.getGl_amount()+"amount from jsp"+msForm.getAmount()+"totAmount"+msForm.getTotal());
		setmiscellinitparams(request,path,msDelegate);
		System.out.println("button value==>"+msForm.getBut_value());	
		
		 if(msForm.getScroll_no()!=0 && msForm.getScroll_no()>0)
			{	
				System.out.println("i am inside loop.....");
				MiscellUVScrollNos(msForm, msDelegate,request);//sowmya====14/05/2008
				System.out.println("Button Value Now is=="+msForm.getBut_value());
            
			}
	
		if(msForm.getBut_value().equalsIgnoreCase("Submit")||msForm.getBut_value().equalsIgnoreCase("Confirm"))
		{
			System.out.println("---------Hi from Submit-----------------"); 
			    
			int proceed = 0;
            boolean continue_transaction = false;
            double totsum=0.0,tamt;
            String value=msForm.getCbox();
            System.out.println("value============>"+value);  
            String[] cbox = request.getParameterValues("cbox");
            String[] gltype=request.getParameterValues("gltype");
            String[] glcode=request.getParameterValues("glcode");
            String[] glamount=(request.getParameterValues("glamount"));
            if(cbox!=null){
        /*    System.out.println("value============>"+cbox[0]);  
            
            
            System.out.println("The gltype is----"+gltype[0]);
            System.out.println("The gltype is----"+gltype[1]);
            
			
			 System.out.println("The glcode is----"+glcode[0]);
	         System.out.println("The glcode is----"+glcode[1]);
				
             
	        	System.out.println("The glamount is----"+glamount[0]);
	            System.out.println("The glamount is----"+glamount[1]);*/
	         for(int k=0;k<cbox.length;k++){
					int x=Integer.parseInt(cbox[k].trim());
					System.out.println("The value of CBox"+cbox[k]);
					
					 totsum+=Double.parseDouble(glamount[x]);
					 
						System.out.println("Total  amount is-------->"+totsum);
					
					}
	         request.setAttribute("sum",totsum);
					
            }
	         
	   
				
           
            try
               {
                   proceed = msDelegate.checkDailyStatus(msDelegate.getSysDate(),0);
               }
               catch(Exception ex)
               {
                   ex.printStackTrace();
               }
               
               System.out.println("update_scroll_no ====== proceed = "+proceed);
               
               if(proceed==3)//day close
               {
            	   msForm.setMsClosed("dayclose");
            	   System.out.println("Day closed\n You can't do any Transactions");
                   continue_transaction = false;
                  
               }
               else if(proceed==1)//cash close
               {
            	   msForm.setMsClosed("cashclose");
            	   System.out.println("Cash closed\n You can't do any Transactions");
                   continue_transaction = false;
                 
               }
               else if(proceed==-1)
               {
            	   continue_transaction = false;
            	   System.out.println("Error : no entry in Daily status for today");
            	   msForm.setMsClosed("noentry");
               }
               else
                   continue_transaction = true;
               System.out.println("inside getcheck before loop===="+getCheck(msForm));
               if(continue_transaction==true)
               {
            	   System.out.println("inside getcheck after loop$$$$$$$$$"+getCheck(msForm));
            	   if(getCheck(msForm)==1)
                   {   

                       boolean update = false;
                       System.out.println("inside getcheck++++++++++"+getCheck(msForm));
                       if(msForm.getBut_value().equalsIgnoreCase("Confirm"))
                       {
                           int scroll_verified = 0;
                           
                           try
                           {
                               scroll_verified = msDelegate.isScrollVerified((msForm.getScroll_no()),msDelegate.getSysDate());
                           }
                           catch(Exception ex)
                           {
                               ex.printStackTrace();
                           }
                           
                           System.out.println("scroll_verified == "+scroll_verified);
                           
                           if(scroll_verified==1)//Already Verified
                           {
                        	   msForm.setMsClosed("scrolverify");
                        	   System.out.println("Scroll No Already Verified");
                           
                           }
                          
                           else if(scroll_verified==2)//Not Yet Verified
                           {
                               update = true;
                           }
                           else if(scroll_verified==3)//Error in DB Entries
                           {
                        	   msForm.setMsClosed("errDB");
                        	   System.out.println("Eror : Check DB Entries");
                               
                           }
                           else if(scroll_verified==-1)//Invalid Scroll No
                           {
                        	  System.out.println("Invalid Scroll No");
                        	  msForm.setMsClosed("invalid"); 
                           }
                           else if(scroll_verified==0)//Error
                           {
                        	   System.out.println("Error: ");
                        	   msForm.setMsClosed("error");
                              
                           }
                       }
                       System.out.println("BUTTON VALUE ++++++++++++++++++)))))))"+msForm.getBut_value());
                       if((msForm.getBut_value().equalsIgnoreCase("Submit") && update==false) || (msForm.getBut_value().equalsIgnoreCase("Confirm")&& update==true))
                       {
                          
                           System.out.println("inside the submit button$$$$$$$");
                           
                    	   if(msForm.getAmount()!=totsum)
                           {
                              msForm.setMsClosed("alldetail");
                               System.out.println("Please enter all the details correctly");
                           }
                           else
                      
                    
                        	   //if(cb_currDenom.isSelected())
                              // {
                            	 /*  System.out.println("Enter Currency Denominations");
                                   
                                   if(actionevent.getSource()==button_update && Double.parseDouble(txt_amount.getText())!=update_amt)
                                   {
                                       obj_currency.clearForm();
                                   }
                                   ////////
                                   
                                   obj_currency.showForm(this);
                                   obj_currency.getEnabled();
                                   obj_currency.getFocus();
                                   System.out.println("Focus in SUBMIT");*/
                              // }
                              // else if(!cb_currDenom.isSelected())
                              // {
                                 if(msForm.getSubutton().equalsIgnoreCase("true")) 
                                 {
                                	 System.out.println("=========Testing==========");
                                	miscell_store(msDelegate,msForm,3,1,cbox,gltype,glcode,glamount); 
                                 } 
                                 update = false;
                       }
                    }
               }
             
           }
	
/**
 * This event is used to do the verification for the corresponding record. Afte
 * * verification the user can't do any alteration in the record. 
*/
	else if(msForm.getBut_value().equalsIgnoreCase("Verify"))
		{
	
          System.out.println("********hi i am in Verification**********123");
			int proceed = 0;
			boolean continue_transaction = false;
			cashobject=new CashObject();
			voucherDataObject=new VoucherDataObject();
			
          try
            {
                proceed = msDelegate.checkDailyStatus(msDelegate.getSysDate(),0);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
            System.out.println("proceed = "+proceed);
            
            if(proceed==3)//day close
            {
            	msForm.setMsClosed("dayclose");
            	System.out.println("Day closed\n You can't do any Transactions");
                continue_transaction = false;
                
            }
            else if(proceed==1)//cash close
            {
            	
            	msForm.setMsClosed("cashclose");
            	System.out.println("Cash closed\n You can't do any Transactions");
                continue_transaction = false;
               
            }
            else if(proceed==-1)
            {
                continue_transaction = false;
                System.out.println("Error : no entry in Daily status for today");
                msForm.setMsClosed("noentry");
            }
            else
                continue_transaction = true;
            
            if(continue_transaction==true)
            {
                if(getCheck(msForm)==1)        
                {
//                  sowmya
                    int scroll_verified = 0;
                    
                    try
                    {
                        scroll_verified = msDelegate.isScrollVerified(msForm.getScroll_no(),msDelegate.getSysDate());
                        
                        System.out.println("scroll_verified !!!!!!!!!!!!!!=============== "+scroll_verified);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    
                    
                    
                    if(scroll_verified==1)//Already Verified
                    {
                    	msForm.setMsClosed("scrolverify");
                    	System.out.println("Scroll No Already Verified");
                       
                    }
                    else if(scroll_verified==2)//Not Yet Verified
                    {
                        System.out.println("hi i am in scroll verfified ===2");
                    	if(msForm.getScroll_no()>0)
                        {
                    		System.out.println("hi i am in verification scrll no>0"+msForm.getScroll_no());
                    		try
                            {
                               cashobject = msDelegate.getData(msForm.getScroll_no(),0,msDelegate.getSysDate());
                               System.out.println("cashobject uv id====>"+cashobject.uv.getVerId()+"\n"+"cashobject========>"+cashobject.getVchno());
                               System.out.println("hi this is vouchertype"+cashobject.getVchtype());
                               voucherDataObject = msDelegate.getVoucherData(cashobject.getVchno(),cashobject.getVchtype(),msDelegate.getSysDate());
                               
                               System.out.println("************sowmya after bean method******************************");
                               //sowmya 15/05/2008
                               //array_VoucherDataObject= msDelegate.getArrayVoucherData(cashobject.getVchno(),cashobject.getVchtype(),msDelegate.getSysDate());
                               System.out.println("***************sowmya111111111111111111111*********"); 
                               System.out.println("array object in verification###"+array_VoucherDataObject);
                               System.out.println("cashobject in verification@@@@@"+cashobject);
                               System.out.println("vocucer databobject in verification***"+voucherDataObject);
                               if(cashobject.uv.getVerId()==null)
                                {
                                   
                                    if(msForm.getSubutton().equalsIgnoreCase("true"))
                                    {
                                        System.out.println("******inside verify******");
                                        
                                        cashobject.uv.setVerId("503");
                                        cashobject.uv.setVerTml(tml);
                                        cashobject.uv.setVerDate(msDelegate.getSysDate()+""+" "+""+msDelegate.getSysTime());
                                        
                                        if(msDelegate.verifyVoucherReceipts(cashobject))
                                        {
                                        	msForm.setMsClosed("success");
                                        	System.out.println("Transaction Verified sucessfully******");
                                        }
                                        else
                                        {
                                        	msForm.setMsClosed("notverify");
                                        	System.out.println("Transaction Not Verified");
                                         }
                                    }
                                }
                                else{
                                		msForm.setMsClosed("recordverify");
                                		System.out.println("Record Already Verified");
                                	}
                           
                            }
                            catch(RecordsNotFoundException ex)
                            {
                                System.out.println(ex);
                            }
                            catch(Exception exception)
                            {
                                exception.printStackTrace();
                            }               
                        }
                        else
                        {
                        	msForm.setMsClosed("enterscroll");
                        	System.out.println("Please enter the scroll no to be verified");
                        }
                    }
                    else if(scroll_verified==3)//Error in DB Entries
                    {
                    	msForm.setMsClosed("errDB");
                    	System.out.println("Eror : Check DB Entries");
                        
                    }
                    else if(scroll_verified==-1)//Invalid Scroll No
                    {
                    	msForm.setMsClosed("invalid");
                    	System.out.println("Invalid Scroll No");
                        
                    }
                    else if(scroll_verified==0)//Error
                    {
                    	msForm.setMsClosed("error");
                    	System.out.println("Error: ");
                     
                    }
                    /////////////
                }
            }
           
		}
		  
		/**
		 * This event is used to delete the selected row in the table. It will delete the single row as well
		 * as multiple row in the table. If there is no record for the corresponding voucher number it delete the record
		 * for the given voucher number.
		*/
		
        else if(msForm.getBut_value().equalsIgnoreCase("Delete"))
    	{
         
            int scroll_verified = 0;
            
            try
            {
                scroll_verified = msDelegate.isScrollVerified(msForm.getScroll_no(),msDelegate.getSysDate());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
            System.out.println("scroll_verified == "+scroll_verified);
            
            if(scroll_verified==1)//Already Verified
            {
            	msForm.setMsClosed("scrolverify");
            	System.out.println("Scroll No Already Verified");
               
            }
            else if(scroll_verified==2)//Not Yet Verified
            {
                
            	 
                 if(msForm.getSubutton().equalsIgnoreCase("true"))
                 {
                    msDelegate.deleteVoucherData(cashobject.getVchno(),cashobject.getVchtype(),tml,msDelegate.getSysDate(),msDelegate.getSysTime());
                    System.out.println("Scroll No Deleted successfully!");
                    msForm.setMsClosed("delete");
                 
                 }
                   
                }
           
            else if(scroll_verified==3)//Error in DB Entries
            {
            	msForm.setMsClosed("errDB");
            	System.out.println("Eror : Check DB Entries");
                
            }
            else if(scroll_verified==-1)//Invalid Scroll No
            {
            	msForm.setMsClosed("invalid");
            	System.out.println("Invalid Scroll No");
                
            }
            else if(scroll_verified==0)//Error
            {
            	msForm.setMsClosed("error");
            	System.out.println("Error: ");
             
            }
          
        }
	  	/**
		 * This event is used to create a scroll number for new account. If the scroll number in txt_scrollno
		 * is 0 then it is consider as a new account. If already created scroll number entered, then this event
		 * display all the information.
		 */
               
    	if(msForm.getScroll_no()==0 && focus_flag)
		{
    	    focus_flag = false;
    	    
			System.out.println("Focus out of txt_scrollno");
		}
    	System.out.println("Focus out of txt_scrollno" + msForm.getScroll_no());
    	
  
			if(msForm.getGl_code()!=null)
			{
				session.getAttribute("addrow");
				request.getAttribute("addrow");
			}
			focus_flag = true;				
			
			
			String method=request.getParameter("method");
			
			List addrow=null;
			if(method!=null)
			{
				
				if(session.getAttribute("addrow")!=null){ 
					
					addrow=(ArrayList)session.getAttribute("addrow");
					
					
				}else{
					
					addrow=new ArrayList();
					
				}
				if(method.equals("Add")){
						
						Map map1=new TreeMap();
						map1.put("gltype",msForm.getGl_type());
						map1.put("glcode",msForm.getGl_code());
						map1.put("glname",msForm.getGl_name());
						map1.put("amount",msForm.getGl_amount());
						addrow.add(map1);		
						request.setAttribute("addrow",addrow);
						session.setAttribute("addrow",addrow);
						System.out.println("inside session===="+addrow.size());
						System.out.println("size of list after===="+addrow.size());
						
				}
				if(method.equals("Clear")){
					//addrow=null;
					addrow.clear();
					for(int i=0;i<addrow.size();i++)
						addrow.remove(i);
					addrow=null;
					session.setAttribute("addrow",addrow);
					request.setAttribute("addrow",addrow);
					//session.invalidate();
				}
				System.out.println("The method is=====>---====>"+method);
				
			}
			
			
			
			
		}	

	else if(map.getPath().equalsIgnoreCase("/PaymentMenu"))
	{
		try{
			
			PaymentForm payForm = (PaymentForm)form;
		    CashDelegate payDelegate =new CashDelegate();
		    payForm.setInvalid_tkn("null");
		    payForm.setInval_vch("null");
		    payForm.setInvalid_vcno("null");
		    payForm.setInval_amt("null");
		    payForm.setGen_scroll(0);
		    System.out.println("*********ReceiptMenu*************");
		    request.setAttribute("TabNum","0");
			getTabbedpane_pay(request,payForm);
	   	 	request.setAttribute("flag",MenuNameReader.getScreenProperties("3003"));
	   	 	double tml_runbal = payDelegate.getCashTmlRunningBalance(tml,payDelegate.getSysDate());
	   	 	request.setAttribute("tml_runable",tml_runbal);
		    moduleobject_gen  = payDelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
	 		request.setAttribute("MainModules", moduleobject_gen);
	 		String string_code = null;
	        
		    if(MenuNameReader.containsKeyScreen(payForm.getPageId()))
			{
		    	String status=payDelegate.checkDailyStatus2();
	            if(status==null)
	           {
				path=MenuNameReader.getScreenProperties(payForm.getPageId());
				System.out.println("path in action class=======>"+path); 
				request.setAttribute("pageId",path);
				setPaymentinitParams(request,path,payDelegate);
			
				return map.findForward(ResultHelp.getSuccess());
	           }
	            else
		         {
		        	  path=MenuNameReader.getScreenProperties("0000");
	                    setErrorPageElements("  "+status,request,path);
	                    return map.findForward(ResultHelp.getError());
		          }	
	            
   			}
         else
         	{
        	 return map.findForward(ResultHelp.getError());
         	}
		}catch (Exception e) 
			{		
    	  		e.printStackTrace(); 
    	  		return map.findForward(ResultHelp.getSuccess());
			}	
	}
	else if(map.getPath().equalsIgnoreCase("/Payment"))
	{
		try
		{
			
			System.out.println("******************PAYMENT*********************");
			CashDelegate payDelegate = new CashDelegate();
			PaymentForm payForm =(PaymentForm)form;
			payForm.setInvalid_tkn("null");
			payForm.setInval_vch("null");
			payForm.setInvalid_vcno("null");
			payForm.setInval_amt("null");
			payForm.setGen_scroll(0);
			request.setAttribute("TabNum","0");
			getTabbedpane_pay(request,payForm);
	   	 	request.setAttribute("flag",MenuNameReader.getScreenProperties("3003") );
	   	 	double tml_runbal = payDelegate.getCashTmlRunningBalance(tml,payDelegate.getSysDate());
	   	 	request.setAttribute("tml_runable",tml_runbal);
	   	 	moduleobject_gen  = payDelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
	 		request.setAttribute("MainModules", moduleobject_gen);
	 		
	 		String string_code = null;
	        String module =moduleobject_gen[Integer.valueOf(payForm.getAcc_type())].getModuleAbbrv();
	        request.setAttribute("getAbbrv", module);
	        
	        if(module!=null)
	        	{
	     	   	for(int i=0;i<moduleobject_gen.length;i++)
	     	   		{
	     	   			String mod=moduleobject_gen[i].getModuleAbbrv();
	        	
	     	   			if(mod.equalsIgnoreCase(module))
	     	   				{
	     	   					String string_desc=moduleobject_gen[i].getModuleDesc();
	     	   					request.setAttribute("module",string_desc);
	     	   					string_code =String.valueOf(moduleobject_gen[i].getModuleCode());
	     	   					
	     	   				}
	     	   		}
	        	}
	        //General Payment
				
	if(payForm.getCombo_type().equalsIgnoreCase("General"))
	{
    		
	if(payForm.getToken_no()!=0)
	{ 
		System.out.println("*************Inside General***********************");
		int tokenno=payForm.getToken_no();
		payForm.setInvalid_tkn("null");
		payForm.setInval_amt("null");
		System.out.println("token no"+payForm.getToken_no());
		chequeobject=payDelegate.getCashWithdraw(tokenno,1,payDelegate.getSysDate());
		System.out.println("cheque object ="+chequeobject);
		 if(chequeobject!=null)
		 { 
			 if(chequeobject.getTokenNo()!=-1)
			{
				payForm.setToken_no(chequeobject.getTokenNo());
				if(chequeobject.getTransMode().equals("Q"))
				payForm.setPay_type("Cheque");
				else if(chequeobject.getTransMode().equals("S"))
				payForm.setPay_type("Slip");
				else if(chequeobject.getTransMode().equals("PO"))
				payForm.setPay_type("PayOrder");		
						
				request.setAttribute("accNo",String.valueOf(chequeobject.getAccNo()));
				
						
				for(int i=0;i<moduleobject_gen.length;i++)
				{
					if(chequeobject.getAccType().equals(moduleobject_gen[i].getModuleCode()))
					{
						request.setAttribute("modulegen",moduleobject_gen[i].getModuleDesc());
						request.setAttribute("moduleindex",String.valueOf(i));
						request.setAttribute("genabbrv",moduleobject_gen[i].getModuleAbbrv());
						
					}
				}
							
				request.setAttribute("chqDate",String.valueOf(chequeobject.getChqDate()));//sowmya
			
				request.setAttribute("trnAmt",chequeobject.getTransAmount());
				System.out.println("cheque account type*********"+chequeobject.getAccType());        
                //sowmya
				
				       
				cashobject = payDelegate.getData(payForm.getToken_no(),1,payDelegate.getSysDate());
                 
                if(cashobject!=null)
                  {
                	request.setAttribute("payName",String.valueOf(cashobject.getAccname()));
                  }
                else
                  {
                	request.setAttribute("payName",String.valueOf(chequeobject.getPayeeName()));
                  }
                  /////////
                        
                	request.setAttribute("slipNo",String.valueOf(chequeobject.getChqNo()));
						
				System.out.println("customer id ==="+chequeobject.getCustomerId());
				System.out.println("cheque account type*********"+chequeobject.getAccType()); 		
				
				if(chequeobject.getCustomerId()>0)
                  {
                   //obj_personal.setCid(String.valueOf(chequeobject.getCustomerId()));
                   //obj_personal.showCustomer(chequeobject.getCustomerId());
                   request.setAttribute("accName",chequeobject.getCheque_Payee());
					System.out.println("account name");
                   int cid =chequeobject.getCustomerId();
                   request.setAttribute("personalInfo",payDelegate.getCustomerdetails(cid));
                   // obj_signaturedetails.addCoBorrower(common_remote.getSignatureDetails(chequeobject.getAccNo(),chequeobject.getAccType()),obj_personal.getCategory());
                  }
                 
		}
          else
        {
                        
         payForm.setInvalid_tkn("token");
                     
        }
	}
	else if(chequeobject==null)
	{
		payForm.setInvalid_tkn("token");
	
	}
	setPaymentinitParams(request, path, payDelegate);
	}
	setPaymentinitParams(request, path, payDelegate);
}			
//Cash/Voucher Payment
	else if(payForm.getCombo_type().equalsIgnoreCase("Cash/Voucher"))
	 {
	  if(payForm.getVoucher_no()!=0)
	  {
		  payForm.setInvalid_vcno("null");
		  payForm.setInval_amt("null");
		  System.out.println("cash/vch selected... token is "+payForm.getVoucher_no());
	       cashobject=new CashObject();         
	       cashObject = payDelegate.getPaymentDetails(payForm.getVoucher_no(),payDelegate.getSysDate());
	                
	    if(cashObject!=null)
	      {
	        payForm.setPay_type("Cash");
	        //combo_cash.setSelectedIndex(0);
	        request.setAttribute("accNo",String.valueOf(cashObject.getAccno()));
	        request.setAttribute("chqDate",String.valueOf(cashObject.getTrndate()));
	        request.setAttribute("trnAmt",(cashObject.getAmount()));
	     for(int j=0;j<moduleobject_gen.length;j++)
	       {
	    	 System.out.println(cashObject.getAcctype()+"At 2863 in cashAction"+moduleobject_gen[j].getModuleCode());
	        if(cashObject.getAcctype().equals(moduleobject_gen[j].getModuleCode()))
	          {
	        	System.out.println(cashObject.getAcctype()+"At 2866" );
	           payForm.setModuledesc(moduleobject_gen[j].getModuleDesc());//sowmya.....07/10/2006
	           payForm.setAcc_type(""+j);
	           request.setAttribute("module",moduleobject_gen[j].getModuleDesc());
	           break;
	           }
	         }
	         cashObject.setTrndate(payDelegate.getSysDate());
	         cashObject.setTrntime(payDelegate.getSysTime());
	                    /////////
	                    
	         System.out.println("cid == "+cashObject.getCustomerId());
	                    
	         if(cashObject.getCustomerId()>0)
	             {
	               //obj_personal.setCid(String.valueOf(cashObject.getCustomerId()));
	               //obj_personal.showCustomer(cashObject.getCustomerId());
	        	   int cid =cashObject.getCustomerId();
	        	   request.setAttribute("personalInfo",payDelegate.getCustomerdetails(cid));
	               //obj_signaturedetails.addCoBorrower(common_remote.getSignatureDetails(Integer.parseInt(cashObject.getAccno()),cashObject.getAcctype()),obj_personal.getCategory());
	             }
	          ////////////
	          
	          }
	          else
	          {
	           payForm.setInvalid_vcno("cshvoucher");
	               		                   
	          }
	    	setPaymentinitParams(request, path, payDelegate);
	      }
	  setPaymentinitParams(request, path, payDelegate);
	 }	
//Voucher Payment
	else if(payForm.getCombo_type().equalsIgnoreCase("Voucher"))
	{
		if(payForm.getVoucher_no_bk()!=0)
		{
			payForm.setInval_vch("null");
			payForm.setInval_amt("null");
			int vchno=payForm.getVoucher_no_bk();
			System.out.println("vch no...."+vchno);
			voucherobject = payDelegate.getPaymentData(vchno,"P",payDelegate.getSysDate());
			System.out.println("VoucherDataObject===="+voucherobject);
			System.out.println("vch no.1."+vchno);
	                
	                //sowmya 
	                if(voucherobject!=null)
	                {
	                    
	                	array_VoucherDataObject = payDelegate.getArrayVoucherData(vchno,"P",payDelegate.getSysDate());
	                    payForm.setPay_type("PayOrder");
	                    System.out.println("gltype==="+voucherobject.getGlType());
	                    
	                    //sowmya
	                    if(array_VoucherDataObject==null)
	                    {
	                        payForm.setToken_no(0);
	                        
	                        payForm.setInval_vch("voucher");
	                    }
	                    else
	                    {
	                        System.out.println("date=="+voucherobject.getTransactionDate());
	                        request.setAttribute("tokenNo",String.valueOf(voucherobject.getVoucherNo()));
	                        request.setAttribute("trnAmt",voucherobject.getTransactionAmount());
	                        request.setAttribute("chqDate",String.valueOf(voucherobject.getTransactionDate()));
	                        request.setAttribute("Narration",voucherobject.getNarration());
	                        
	                       System.out.println("arrayvchobj len ="+array_VoucherDataObject.length);
	                       request.setAttribute("arrayvoucherObject", array_VoucherDataObject);
	                       System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	                       for(int int_index=0;int_index<array_VoucherDataObject.length;int_index++)
	                       {
	                      
	                    	  //System.out.println("hi monkey"+array_VoucherDataObject[int_index].getGlName());
	                    	   
	                       }
	                       /* for(int int_index=0;int_index<array_VoucherDataObject.length;int_index++)
	                        {
	                           payForm.setGl_type(array_VoucherDataObject[int_index].getGlType());
	                           payForm.setGl_code(String.valueOf(array_VoucherDataObject[int_index].getGlCode()));
	                           payForm.setGl_name(payDelegate.getGLName(String.valueOf(array_VoucherDataObject[int_index].getGlCode())));
	                            String glname=payDelegate.getGLName(String.valueOf(array_VoucherDataObject[int_index].getGlCode()));
	                        
	                            System.out.println("without numf"+array_VoucherDataObject[int_index].getTransactionAmount());
	                            System.out.println("with numf"+array_VoucherDataObject[int_index].getTransactionAmount());
	                            payForm.setTrn_amt(String.valueOf(array_VoucherDataObject[int_index].getTransactionAmount()));
	                        }*/
	                        
	                        voucherobject.setVoucherNo(vchno);
	                        System.out.println("vchno.. for payment.. genpyment ... "+voucherobject.getVoucherNo());
	                        
	                       
	                    }
	                }
	                else
	                {
	                	payForm.setInval_vch("voucher");
	                    
	                }
	                setPaymentinitParams(request,path,payDelegate);
				}
		setPaymentinitParams(request, path, payDelegate);
	}
	System.out.println("hi i am outside the loop .... ");

	if(payForm.getBut_value().equalsIgnoreCase("PAY"))
	{
		int proceed = 0;
		
	    boolean continue_transaction = false;
	    try
	      {
	        proceed = payDelegate.checkDailyStatus(payDelegate.getSysDate(),0);
	      }
	      catch(Exception ex)
	       {
	        ex.printStackTrace();
	       }
	      	if(proceed==3)//day close
	         {
	      		payForm.setValidation("dayclose");
	      		request.setAttribute("dayclose","Day closed\n You can't do any Transactions");
	            continue_transaction = false;
	         }
	        else if(proceed==1)//cash close
	         {
	               
	                request.setAttribute("cashclose","Cash closed\n You can't do any Transactions");
	                payForm.setValidation("cashclose");
	                continue_transaction = false;
	          }
	         else if(proceed==-1)
	          {
	        	 request.setAttribute("noentry","Error : no entry in Daily status for today");
	        	 payForm.setValidation("noentery");
	             continue_transaction = false;
	           }
	            else
	                continue_transaction = true;
	      		
	            if(continue_transaction==true)
	            {
	            	if(payForm.getPay_value().equalsIgnoreCase("true"))
	            	{
	            	//request.setAttribute("payment","Pay Amount");
	            	pay_store(payDelegate, payForm, 1, 1);// type changed to 0 to test
	            	}
	            }
	            
	}	            
	if(chequeobject!=null)
	{
	if(chequeobject.getTransDate()!=null)
	{
		if((chequeobject.getTransDate()).equalsIgnoreCase("null"))
		{
			System.out.println("hi i am inside the loop ....::::::::::::::: ");
			payForm.setInval_amt("invalamt");
		}else
			payForm.setInval_amt("null");
	}
	
	System.out.println("................."+payForm.getCurrency());
	}
	        if(payForm.getCurrency()!=null){
				if(payForm.getCurrency().equalsIgnoreCase("Required"))
				{
					request.setAttribute("required","Required");
				}
	        }
			
	        setPaymentinitParams(request, path, payDelegate);
		}catch (Exception e) {
			e.printStackTrace();
			}
		
	}
	
	else if(map.getPath().equalsIgnoreCase("/TransferMenu"))
	{
		try{
			
			TransferForm trForm =(TransferForm)form;
		    CashDelegate trDelegate =new CashDelegate();
		    trForm.setValidation("null");
		    trForm.setGen_scroll(0);
		    trForm.setUpdate_scroll(0);
		    System.out.println("*********TransferMenu*************");
		    terminalobject_view=trDelegate.getTerminalObject();
		    request.setAttribute("comboterminal",terminalobject_view);
		    double tml_runable =trDelegate.getCashTmlRunningBalance(tml,trDelegate.getSysDate()); 
	        request.setAttribute("tml_runable",tml_runable);
	        request.setAttribute("scrollno", trForm.getScroll_no());
		    if(MenuNameReader.containsKeyScreen(trForm.getPageId()))
			{
				path=MenuNameReader.getScreenProperties(trForm.getPageId());
				System.out.println("path in action class=======>"+path); 
				request.setAttribute("pageId",path);
				setPaymentinitParams(request,path,trDelegate);
			
				return map.findForward(ResultHelp.getSuccess());
   			}
         else
         	{
        	 return map.findForward(ResultHelp.getError());
         	}
		}catch (Exception e) 
			{		
    	  		e.printStackTrace(); 
    	  		return map.findForward(ResultHelp.getSuccess());
			}	
	}
	else if(map.getPath().equalsIgnoreCase("/Transfer"))
	{
		
		TransferForm trForm =(TransferForm)form;
	    CashDelegate trDelegate =new CashDelegate();
	    terminalobject_view=trDelegate.getTerminalObject();
	    request.setAttribute("comboterminal",terminalobject_view);
	    trForm.setValidation("null");
	    trForm.setGen_scroll(0);
	    trForm.setUpdate_scroll(0);
	    
	    double tml_runable =trDelegate.getCashTmlRunningBalance(tml,trDelegate.getSysDate()); 
        request.setAttribute("tml_runable",tml_runable);
        System.out.println("value of the button...."+trForm.getTransfer());
	    /**
		 * Transfer of money to the same terminal is not allowed.
		 * The amount entered should be always greater than zero.
		 * If at any point of time, the user wants to view his transaction the scroll no has to
		 * be  entered, wherein all the details can be viewed.
		 *
		 */
		request.setAttribute("scrollno", trForm.getScroll_no());
	    if(trForm.getScroll_no()>0)
		{
            
	    	boolean disp = false;//sowmya
            
			try
			{
				cashobject_view=new CashObject();
				cashobject_view=trDelegate.getData(trForm.getScroll_no(),0,trDelegate.getSysDate());
				System.out.println("Oth tml"+cashobject_view.getOthtml());
				
				if(cashobject_view.getOthtml()!=null && cashobject_view.uv.getVerTml()==null && cashobject_view.getTrntype().equals("A")) //cross checking wiht othertml
				{
					if(cashobject_view.getOthtml().equals(tml))
					{
						
						//obj_currency.getCurrency(cashobject_view);
						
                        
                        //sowmya
                       
                        
                        disp = true;
                        
						//focus_flag = true;
					}
					else if(!cashobject_view.getOthtml().equals(tml))
					{
						if(cashobject_view.getRunbal()==0)
                        {
                            System.out.println("Access denied: U cannot alter this Scroll No (Tml Close)");
                            trForm.setValidation("denied");
                            disp = false;
                        }
                        else
                        {
                           
                            //sowmya
                          
                            System.out.println("inside button modify....");
                            
                            disp = true;
                        }
                        
						request.setAttribute("objterminal",cashobject_view.getOthtml());
						
					}
					
					if(disp==true)
                    {
					    //sowmya
                        
                   double amt=(cashobject_view.getAmount());
                        //amt=amt.replaceAll(",","");
                        trForm.setAmount(amt);
                        request.setAttribute("cashamount",amt);
                        
                        System.out.println("qqqqqqqqq 6");
                        
                        //sowmya
                        update_amt = trForm.getAmount();
                        System.out.println("Before Updation amt == "+update_amt);
                        ////////////
                    }
				}
				else if(cashobject_view.getOthtml()!=null && cashobject_view.uv.getVerTml()!=null)
				{
					System.out.println("U cannot update verified scroll no");
					trForm.setValidation("cantupdate");
					
				}
				else
				{
					System.out.println("Invalid Scroll No");
					trForm.setValidation("invalid");
					
					
				}
			}
			catch(ScrollNotFoundException exec)
			{
				System.out.println(exec);
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	    
		else if((trForm.getTerminal()!=null)&& focus_flag)
		{
		    focus_flag = false;
		    
			System.out.println("Focus out of combo_terminal");
			
                if(trForm.getTerminal().equalsIgnoreCase(tml))
                {
                    if(trForm.getScroll_no()==0)//ship....added on 06/10/2005
                    { 
                      System.out.println("You cannot transfer money to the same terminal");
                      trForm.setValidation("money");
                    }
                }
              
			focus_flag = true;
		}
	    ///actionevent for all button
	    
	    //for delete and reject....
		
		if(trForm.getTransfer().equalsIgnoreCase("Delete") || trForm.getTransfer().equalsIgnoreCase("Reject"))
		{
            //sowmya
            int scroll_verified = 0;
            
            try
            {
                scroll_verified = trDelegate.isScrollVerified(trForm.getScroll_no(),trDelegate.getSysDate());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
            System.out.println("scroll_verified == "+scroll_verified);
            
            if(scroll_verified==1)//Already Verified
            {
                System.out.println("Scroll No Already Verified");
                trForm.setValidation("verified");
                
            }
            else if(scroll_verified==2)//Not Yet Verified
            {
                int int_res = 0;
                boolean re_open = false;
                
                if(trForm.getDelbutton().equalsIgnoreCase("true"))
                {
                	System.out.println("Are you sure you want to delete ?");
                    re_open = false;
                }
                else if(trForm.getTransfer().equalsIgnoreCase("Reject"))
                {
                	System.out.println("Do you want to re-open the tml "+cashobject_view.uv.getUserTml()+" ?");
                    re_open = true;
                }
                
                if(trForm.getDelbutton().equalsIgnoreCase("true"))
                {
                    System.out.println("Are you sure?");
                    
                    if(trForm.getDelbutton().equalsIgnoreCase("true"))
                    {
                        try
                        {
                            if(re_open==false?trDelegate.deleteData(trForm.getScroll_no(),2,trDelegate.getSysDate(),trDelegate.getSysTime()):trDelegate.deleteData(trForm.getScroll_no(),3,trDelegate.getSysDate(),trDelegate.getSysTime()))
                            {
                                if(trForm.getTransfer().equalsIgnoreCase("Delete")){
                                	trForm.setValidation("delete");
                                	System.out.println("Deleted Successfully");
                                }
                                else if(trForm.getTransfer().equalsIgnoreCase("Reject"))
                                {
                                	trForm.setValidation("ReOpened");
                                	System.out.println("Tml Re-Opened Successfully");
                                }
                              
                            }
                            else
                            {
                                if(trForm.getTransfer().equalsIgnoreCase("Delete"))
                                {
                                	trForm.setValidation("notdelete");
                                	System.out.println("Error : Could not Delete");
                                }
                                else if(trForm.getTransfer().equalsIgnoreCase("Reject"))
                                {
                                	trForm.setValidation("reopenerror");
                                	System.out.println("Error : Could not Re-Open the Tml");
                                }
                            }
                        }
                        catch(ScrollNotFoundException e1)
                        {
                        	System.out.println(e1);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                    {
                        re_open = false;
                        
                    }
                }
                else
                {
                    re_open = false;
                    
                }
            }
            else if(scroll_verified==3)//Error in DB Entries
            {
            	System.out.println("Eror : Check DB Entries");
            	trForm.setValidation("checkdb");
                
            }
            else if(scroll_verified==-1)//Invalid Scroll No
            {
            	System.out.println("Invalid Scroll No");
            	trForm.setValidation("invalid");
                
            }
            else if(scroll_verified==0)//Error
            {
                System.out.println("Error: ");
                trForm.setValidation("error");
            	
              
            }
            /////////////
		}
		
		else if(trForm.getTransfer().equalsIgnoreCase("Modify"))
		{
            //sowmya
            int scroll_verified = 0;
            
            try
            {
                scroll_verified = trDelegate.isScrollVerified(trForm.getScroll_no(),trDelegate.getSysDate());
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
            System.out.println("scroll_verified == "+scroll_verified);
            
            if(scroll_verified==1)//Already Verified
            {
            	System.out.println("Scroll No Already Verified");
            	trForm.setValidation("verified");
              
            }
            else if(scroll_verified==2)//Not Yet Verified
            {
                System.out.println("Confirm");
                trForm.setValidation("confirm");
                
                /////////
               
            }
            else if(scroll_verified==3)//Error in DB Entries
            {
            	 System.out.println("Eror : Check DB Entries");
            	 trForm.setValidation("checkdb");
               
            }
            else if(scroll_verified==-1)//Invalid Scroll No
            {
            	 System.out.println("Invalid Scroll No");
            	 trForm.setValidation("invalid");
               
            }
            else if(scroll_verified==0)//Error
            {
            	 System.out.println("Error: ");
            	 trForm.setValidation("error");
              
            }
            ////////////
		}
		
		//button accept..................
		
		else if(trForm.getTransfer().equalsIgnoreCase("Accept"))
		{
		    //sowmya
            int proceed = 0;
            boolean continue_transaction = false;
            
            try
            {
                proceed = trDelegate.checkDailyStatus(trDelegate.getSysDate(),0);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            

        	System.out.println("proceed = "+proceed);
            
            if(proceed==3)//day close
            {
            	System.out.println("Day closed\n You can't do any Transactions");
            	trForm.setValidation("dayclose");
                continue_transaction = false;
              
            }
            else if(proceed==1)//cash close
            {
            	System.out.println("Cash closed\n You can't do any Transactions");
            	trForm.setValidation("cashclose");
                continue_transaction = false;
               
            }
            else if(proceed==-1)
            {
              
                continue_transaction = false;
                System.out.println("Error : no entry in Daily status for today");
                trForm.setValidation("noentry");
            }
            else
                continue_transaction = true;
            
            if(continue_transaction==true)
            {
                //sowmya
                int scroll_verified = 0;
                
                try
                {
                    scroll_verified = trDelegate.isScrollVerified(trForm.getScroll_no(),trDelegate.getSysDate());
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                
                System.out.println("scroll_verified == "+scroll_verified);
                
                if(scroll_verified==1)//Already Verified
                {
                	System.out.println("Scroll No Already Verified");
                	trForm.setValidation("verified");
                    
                }
                else if(scroll_verified==2)//Not Yet Verified
                {
                   
                    try
                    {
                        //sowmya
                        cashobject_view.uv.setUserId(user);
                        cashobject_view.uv.setUserTml(tml);
                        cashobject_view.uv.setUserDate(trDelegate.getSysDateTime());
                        ////////////
                        
                        int accept = trDelegate.AcceptMoney(cashobject_view,tml,String.valueOf(trForm.getScroll_no()));
                        
                        if(accept>0)
                        {
                        	trForm.setAccept_scroll(accept);
                        	System.out.println("Note the Scroll No < "+accept+" >");
                        }
                        else
                        	trForm.setValidation("unableto");
                        	System.out.println("Error.....Unable to Accept Money");
                        
                        
                        
                      
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else if(scroll_verified==3)//Error in DB Entries
                {
                	System.out.println("Eror : Check DB Entries");
                	trForm.setValidation("checkdb");
                   
                }
                else if(scroll_verified==-1)//Invalid Scroll No
                {
                	System.out.println("Invalid Scroll No");
                	trForm.setValidation("invalid");
                   
                }
                else if(scroll_verified==0)//Error
                {
                	System.out.println("Error: ");
                	trForm.setValidation("error");
                   
                }
                //////////////
            }
            /////////
		}
	    if(trForm.getTransfer().equalsIgnoreCase("transfer")|| (trForm.getTransfer().equalsIgnoreCase("modify")))
		{
		    //sowmya
            int proceed = 0;
            boolean continue_transaction = false;
            
            try
            {
                proceed = trDelegate.checkDailyStatus(trDelegate.getSysDate(),0);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            System.out.println("inside transfer......"+trForm.getTransfer()+"proceed value..."+proceed );
            if(proceed==3)//day close
            {
                System.out.println("Day closed\n You can't do any Transactions");
                trForm.setValidation("dayclose");
                continue_transaction = false;
               
            }
            else if(proceed==1)//cash close
            {
            	 System.out.println("Cash closed\n You can't do any Transactions");
            	 trForm.setValidation("cashclose");
                continue_transaction = false;
            }
            else if(proceed==-1)
            {
                
                continue_transaction = false;
                System.out.println("Error : no entry in Daily status for today");
                trForm.setValidation("noentry");
            }
            else
                continue_transaction = true;
            
            if(continue_transaction==true)
            {
                //sowmya
                boolean modify = false;
                
                if(trForm.getTransfer().equalsIgnoreCase("modify"))
                {
                    int scroll_verified = 0;
                    
                    try
                    {
                        scroll_verified = trDelegate.isScrollVerified(trForm.getScroll_no(),trDelegate.getSysDate());
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    
                   
                    
                    if(scroll_verified==1)//Already Verified
                    {
                    	 System.out.println("Scroll No Already Verified");
                    	 trForm.setValidation("verified");
                       
                    }
                    else if(scroll_verified==2)//Not Yet Verified
                    {
                        modify = true;
                    }
                    else if(scroll_verified==3)//Error in DB Entries
                    {
                    	 System.out.println("Eror : Check DB Entries");
                    	 trForm.setValidation("checkdb");
                       
                    }
                    else if(scroll_verified==-1)//Invalid Scroll No
                    {
                    	 System.out.println("Invalid Scroll No");
                    	 trForm.setValidation("invalid");
                       
                    }
                    else if(scroll_verified==0)//Error
                    {
                    	 System.out.println("Error: ");
                    	 trForm.setValidation("error");
                       
                    }
                }
                
                if((trForm.getTransfer().equalsIgnoreCase("transfer") && modify==false) || (trForm.getTransfer().equalsIgnoreCase("modify") && modify==true))
                {
                    double runbal = 0;
                    
                    try
                    {
                        runbal = trDelegate.getCashTmlRunningBalance(tml,trDelegate.getSysDate());
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    
                   
                    
                    if(runbal-(trForm.getAmount())==0)
                    {
                       
                    	 System.out.println("U cant close the Terminal");
                    	 trForm.setValidation("terminal");
                    }
                    else
                    {
                    	
                    	if(trForm.getTrbutton().equalsIgnoreCase("true"))
                    	transfer_store(trDelegate,trForm,7,2);
                    	/* if(cb_currDenom.isSelected())
                        {
                           
                            if(trForm.getModify().equalsIgnoreCase("modify")&& trForm.getAmount()!=update_amt)
                            {
                                //obj_currency.clearForm();
                            }
                            /////////////
                            
                            //obj_currency.showForm(this);
                            //obj_currency.getEnabled();
                        }*/
                    	
                    }
                }
                //////////
            }
            ///////////
		}
	    settransferinitparams(request,path,trDelegate); 
	}
	
	else if(map.getPath().equalsIgnoreCase("/ExchangeMenu"))
	{
		try{
			
			ExchangeForm exForm =(ExchangeForm)form;
		    CashDelegate exDelegate =new CashDelegate();
		    double tml_runable =exDelegate.getCashTmlRunningBalance(tml,exDelegate.getSysDate()); 
		    exForm.setClosed("null");
		    exForm.setValidation("null");
		    exForm.setGen_scroll(0);
			request.setAttribute("tml_runable",tml_runable);		    
		    System.out.println("*********CurrencyExchangeMenu*************");
		    double sub_runable =exDelegate.getCashTmlRunningBalance("LK99",exDelegate.getSysDate()); 
			request.setAttribute("sub_runable",sub_runable);
		    if(MenuNameReader.containsKeyScreen(exForm.getPageId()))
			{
				path=MenuNameReader.getScreenProperties(exForm.getPageId());
				System.out.println("path in action class=======>"+path); 
				request.setAttribute("pageId",path);
				setPaymentinitParams(request,path,exDelegate);
				request.setAttribute("submit", exForm.getValue());
				return map.findForward(ResultHelp.getSuccess());
   			}
         else
         	{
        	 return map.findForward(ResultHelp.getError());
         	}
		}catch (Exception e) 
			{		
    	  		e.printStackTrace(); 
    	  		return map.findForward(ResultHelp.getSuccess());
			}	
	}
	
else if(map.getPath().equalsIgnoreCase("/Exchange"))
{ 
	
	ExchangeForm exForm=(ExchangeForm)form;
	System.out.println("Exform----------"+exForm.getValue());
	request.setAttribute("submit", exForm.getValue());
	sum=exForm.getTotal();
	sum1=exForm.getRefund();          
	exForm.setClosed("null");
	exForm.setValidation("null");
	exForm.setGen_scroll(0);
	int lr=0;
	CashDelegate exDelegate=new CashDelegate();
	double tml_runable =exDelegate.getCashTmlRunningBalance(tml,exDelegate.getSysDate()); 
	request.setAttribute("tml_runable",tml_runable);
	
	double sub_runable =exDelegate.getCashTmlRunningBalance("LK99",exDelegate.getSysDate()); 
	request.setAttribute("sub_runable",sub_runable);
	
	//for terminal closing
	System.out.println("button value in action class======>"+exForm.getBut_value());
	
	
	if(exForm.getValue().equalsIgnoreCase("2")||exForm.getValue()=="2")
	{
		if(exForm.getScroll_no()==0)
			{
			   
				//sowmya....allows only sub cashiers
				try
				{
					flag =exDelegate.checkTerminal("LK99");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				if(flag==1)
				{
				   
					exForm.setClosed("access");
					System.out.println("U dont have an access to close the terminal");
					
				}
				else if(flag==-1)
				{
					exForm.setClosed("doesnot");
					System.out.println("Terminal does not exist");
					
				}
				else if(flag==0)
				{
				  
					try
					{
						currencystockobject=new CurrencyStockObject();
						currencystockobject=exDelegate.getCurrencyStockObject("LK99",exDelegate.getSysDate(),0);
						System.out.println("currency stock object from delegate"+currencystockobject);
						exForm.setTr_amount(Double.parseDouble(currencystockobject.getNetamt()));
						System.out.println("amt== in constructor "+exForm.getTr_amount());
						exForm.setP_thousand(currencystockobject.gets1000());
						exForm.setP_fivered(currencystockobject.gets500());
						exForm.setP_hundred(currencystockobject.gets100());
						exForm.setP_fifty(currencystockobject.gets50());
						exForm.setP_twenty(currencystockobject.gets20());
						exForm.setP_ten(currencystockobject.gets10());
						exForm.setP_five(currencystockobject.gets5());
						exForm.setP_two(currencystockobject.gets2());
						exForm.setP_one(currencystockobject.gets1());
						exForm.setP_coins(currencystockobject.getscoins());
						
					
						
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
			else if(exForm.getScroll_no()>0)
			{
				try
				{
					co=exDelegate.getData(exForm.getScroll_no(),0,exDelegate.getSysDate());
					System.out.println("Oth tml"+co.getOthtml());
					System.out.println("run bal "+co.getRunbal());
					
					if(co.getOthtml()!=null && co.uv.getVerTml()==null && co.getTrntype().equals("A")) //cross checking wiht othertml
					{
						if(co.getOthtml().equals("LK99"))
						{
							
							getCurrency(co,exForm);
							String amt=String.valueOf((co.getAmount()));
							amt=amt.replaceAll(",","");
							exForm.setTr_amount(Double.parseDouble(amt));
							exForm.setSend_to(co.getOthtml());
						}
						else if(!co.getOthtml().equals("LK99"))
						{
							exForm.setValidation("denied");
							System.out.println("Access denied: U cannot alter this Scroll No");
							
						}
					}
					else if(co.getOthtml()!=null && co.uv.getVerTml()!=null)
					{
						exForm.setValidation("accept");
						System.out.println("This Scroll No is already Accepted");
						
					}
					else
					{
						exForm.setValidation("invalid");
						System.out.println("Invalid Scroll No");
						
					}
				}
				catch(ScrollNotFoundException exec)
				{
					exForm.setClosed("exception");
					System.out.println(exec);
					
					System.out.println("Focus is now in ???	");
		        	
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
	
	}
	//for currency exchange
	if(exForm.getValue().equalsIgnoreCase("1")||exForm.getValue()=="1")
	{
		System.out.println("Are you cuming here??????????");
	double_amount=Double.parseDouble(exForm.getAmount());
	System.out.println("Double Amount-------------"+Double.parseDouble(exForm.getAmount()));
	if(Double.parseDouble(exForm.getAmount())!=0)
	{
	   System.out.println("Focus lost from txt_amount");
	}
	if(Double.parseDouble(exForm.getAmount())>0)
    {
	//sowmya
    int proceed = 0;
    boolean continue_transaction = false;
            
    try
      {
        proceed = exDelegate.checkDailyStatus(exDelegate.getSysDate(),0);
      }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
           System.out.println("proceed = "+proceed);
            
            if(proceed==3)//day close
            {
            	
            	System.out.println("Day closed\n You can't do any Transactions");
                exForm.setClosed("dayclose");
            	continue_transaction = false;
               
            }
            else if(proceed==1)//cash close
            {
            	System.out.println("Cash closed\n You can't do any Transactions");
            	 exForm.setClosed("cashclose");
            	continue_transaction = false;
                
            }
            else if(proceed==-1)
            {
                
                continue_transaction = false;
                System.out.println("Error : no entry in Daily status for today");
                exForm.setClosed("Error");
            }
            else
                continue_transaction = true;
            
            if(continue_transaction==true)
            {
            	System.out.println("Are You cuming in continue transaction ");
            	System.out.println("Than Value of exchange -----------"+exForm.getExchange());
            	
            	if(exForm.getExchange().equalsIgnoreCase("true"))
            		
            		currencyex(lr,exForm,exDelegate,request);  
	    		
            }
            ////////////
    	}
	}
	if(exForm.getValue().equalsIgnoreCase("2")||exForm.getValue()=="2")
	{
	if(exForm.getBut_value().equalsIgnoreCase("Close"))
	{
    	int sc__no;
        int proceed = 0;
        boolean continue_transaction = false;
        
        try
        {
            proceed = exDelegate.checkDailyStatus(exDelegate.getSysDate(),0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        System.out.println("proceed = "+proceed);
        
        if(proceed==3)//day close
        {
        	
        	System.out.println("Day closed\n You can't do any Transactions");
        	exForm.setValidation("dayclose");
            continue_transaction = false;
      
        }
        else if(proceed==1)//cash close
        {
        	 System.out.println("Cash closed\n You can't do any Transactions");
            continue_transaction = false;
            exForm.setValidation("cashclose");
           
        }
        else if(proceed==-1)
        {
            
            continue_transaction = false;
            System.out.println("Error : no entry in Daily status for today");
            exForm.setValidation("noentry");
        }
        else
            continue_transaction = true;
        
        if(continue_transaction==true)
        {
        	System.out.println("++++____^^^^^777((("+exForm.getClose());
        	
        	if(exForm.getClose().equalsIgnoreCase("true"))
        	{
        		System.out.println("$$$$$%%^^&&*(*###$^");
        		exForm.setValidation("closeterminal");
        		System.out.println("Do you want to close the Cash Terminal?");
			
					try
					{
						currencystockobject=new CurrencyStockObject();
						currencystockobject=exDelegate.getCurrencyStockObject("LK99",exDelegate.getSysDate(),0);
						
						co = new CashObject(); 
							
						co.setR1000(0);
			            co.setR500(0);
			            co.setR100(0);
			            co.setR50(0);
			            co.setR20(0);
			            co.setR10(0);
			            co.setR5(0);
			            co.setR2(0);
			            co.setR1(0);
			            co.setRcoins(0.0);
			            
			            co.setP1000(currencystockobject.gets1000());
			            co.setP500(currencystockobject.gets500());
			            co.setP100(currencystockobject.gets100());
			            co.setP50(currencystockobject.gets50());
			            co.setP20(currencystockobject.gets20());
			            co.setP10(currencystockobject.gets10());
			            co.setP5(currencystockobject.gets5());
			            co.setP2(currencystockobject.gets2());
			            co.setP1(currencystockobject.gets1());
			            co.setPcoins(currencystockobject.getscoins());
			            
			            co.setAmount(Double.parseDouble(currencystockobject.getNetamt()));
			            
			            co.uv.setUserId("LK99");
                        
			            //sowmya......04/06/2008
                        co.uv.setUserDate(exDelegate.getSysDateTime());
                        co.setTrndate(exDelegate.getSysDate());
                        /////////////
			            
						int flag1 = 0;
						
						//sowmya
						flag1 = exDelegate.checkReceiptPending("LK99",exDelegate.getSysDate());
						
						System.out.println("flag1 == "+flag1);
						
						if(flag1==0)
						{
							sc__no = exDelegate.closeTerminal("LK99",exForm.getSend_to(),co);
							
							if(sc__no==-1)
							{
								exForm.setValidation("progress");
								System.out.println("Terminal Closing...transaction in progress");
								
							}
							else if(sc__no==-2)
							{
								exForm.setValidation("alreadyclose");
								System.out.println("Terminal is already Closed");
							
							}
                            else if(sc__no==-3)
                            {
                            	exForm.setValidation("incurrency");
                            	System.out.println("No Entries in Currency Stock");
                                
                            }
							else if(sc__no>0)
							{
								exForm.setGen_scroll(sc__no);
								System.out.println("Terminal closed...Note the Scroll No <"+sc__no+"> ");
								
							}
						}
						else if(flag1==1)
						{
							exForm.setValidation("errorac");
							System.out.println("Error: Existing A/c Rec. Ver. is Pending");
						
						}
						else if(flag1==2)
						{
							exForm.setValidation("newac");
							System.out.println("Error: New A/c Rec. Ver. is Pending");
							
						}
						else if(flag1==3)
						{
							exForm.setValidation("pending");
							System.out.println("Error: Misc. Rec. Ver. is Pending");
							
						}
						////////////
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
				
			}
        }
        setExinitparams(request, path, exDelegate); 
	}
	else if(exForm.getBut_value().equalsIgnoreCase("Update"))
	{
		request.setAttribute("button_value","UPDATE");
		System.out.println("before update... left");
		System.out.println("before update... right" );
		System.out.println("before update... amount");
		setExinitparams(request, path, exDelegate); 
	}		
	}
	
	setExinitparams(request, path, exDelegate);    
	}	
	
	
else if(map.getPath().equalsIgnoreCase("/ConsolidatedMenu"))
	{
	try{
	    System.out.println("*********ConsolidatedMenu*************");
	    CashDelegate scrollDelegate = new CashDelegate();
	    ConsolidateScrollform srollform =(ConsolidateScrollform)form;
	    srollform.setValidation("null");
	    TerminalObject[] terminalobject_view=scrollDelegate.getTerminalObject();
	    request.setAttribute("combo_terminal", terminalobject_view);
	    String date = scrollDelegate.getSysDate(); 
	    request.setAttribute("date",date);
	    double tml_runable =scrollDelegate.getCashTmlRunningBalance(tml,scrollDelegate.getSysDate()); 
		request.setAttribute("tml_runable",tml_runable);
	             	  
	    if(MenuNameReader.containsKeyScreen(srollform.getPageId()))
	    	{
        	 
				path=MenuNameReader.getScreenProperties(srollform.getPageId());
				request.setAttribute("pageId",path);
				setScrollInitParams(request,path,scrollDelegate);
				
				return map.findForward(ResultHelp.getSuccess());
   
	    	 	}
	    	 else
	    	 	{
	    		 return map.findForward(ResultHelp.getError());
	    	 	}
		}catch (Exception e) 
			{		
    	       e.printStackTrace(); 
        	   
    	       return map.findForward(ResultHelp.getSuccess());
			}
	}
	
	
	else if(map.getPath().equalsIgnoreCase("/Scroll"))
	{
		try{
			System.out.println("**********inside Scroll Report***********");
			CashDelegate scrollDelegate = new CashDelegate();
			ConsolidateScrollform scrollform =(ConsolidateScrollform)form; 
			scrollform.setValidation("null");
			double tml_runable =scrollDelegate.getCashTmlRunningBalance(tml,scrollDelegate.getSysDate()); 
			request.setAttribute("tml_runable",tml_runable);
			             	  
			terminalobject_view=scrollDelegate.getTerminalObject();
			request.setAttribute("combo_terminal", terminalobject_view);
		
			String terminalCode =scrollform.getTerminal();
		
			String string_from_date=scrollform.getDate();
		
			String string_present_date = scrollDelegate.getSysDate();
		
			String terminalobj =scrollform.getReport();
			request.setAttribute("report", terminalobj);
			
			int value=Validations.dayCompare(string_from_date,string_present_date);
			
			System.out.println("String string_from_date"+string_from_date);
			System.out.println("string_present_date"+string_present_date);
		
			setScrollInitParams(request, path, scrollDelegate);
			
			if(value==-1)
			{
				scrollform.setValidation("invaliddata");
				
			}
		
			else if(value!=-1)
			{
				
				setScrollInitParams(request,path,scrollDelegate); 
				String getSelectedTerminal = scrollform.getTerminal();	
				
				// **********************select Terminal******************
				
				cashobject_withdenom = scrollDelegate.getDayCashData(string_from_date,getSelectedTerminal , flag, query);
		
				
				cashobject =scrollDelegate.getDayCashSummary(string_from_date,scrollform.getTerminal());
				
				//
				// **********************all Terminal******************
				
				cashobject_withdenomall=scrollDelegate.getDayCashData(string_from_date,null,flag,query); 
				
				cashobjectAll =scrollDelegate.getDayCashSummary(string_from_date,null);
				 
				String button_value = scrollform.getValue();
				
				
				if(button_value.equalsIgnoreCase("true"))
				{
					if(scrollform.getReport().equalsIgnoreCase("Selected Terminals"))
					{
						setScrollInitParams(request, path, scrollDelegate);
						request.setAttribute("denomination","denomination");
						request.setAttribute("cashobject_withdenom",cashobject_withdenom);
						request.setAttribute("CashObject",cashobject);
					}
					else if(scrollform.getReport().equalsIgnoreCase("All Terminals"))
					{
						request.setAttribute("denomination","denomination");
						request.setAttribute("cashobject_withdenom", cashobject_withdenomall);
						request.setAttribute("CashObject", cashobjectAll);
							
					}
				}
				else if(button_value.equalsIgnoreCase("false"))
				{
					if(scrollform.getReport().equalsIgnoreCase("Selected Terminals"))
					{
						request.setAttribute("denomination","woutdenomination");
						request.setAttribute("cashobject_withdenom", cashobject_withdenom);
						request.setAttribute("CashObject", cashobject);
					}
					else if(scrollform.getReport().equalsIgnoreCase("All Terminals"))
					{
						request.setAttribute("denomination","woutdenomination");
						request.setAttribute("cashobject_withdenom",cashobject_withdenomall);
						request.setAttribute("CashObject",cashobjectAll);
					}
				}
			}
		
			if (scrollform.getBut_value().equalsIgnoreCase("download")) {
				System.out.println("I am in download=======");
				String getSelectedTerminal = scrollform.getTerminal();
				terminalobject_view=scrollDelegate.getTerminalObject();
				request.setAttribute("combo_terminal", terminalobject_view);
				cashobject_withdenom=scrollDelegate.getDayCashData(string_from_date,getSelectedTerminal , flag, query);
				
				{
				if ( cashobject_withdenom == null) {
					
					scrollform.setTesting("Cannot Print");
				} else {
					System.out.println(" i am inside down load");

					// TO save to an excel Sheet
					res.setContentType("application/.csv");
					res.setHeader("Content-disposition","attachment;filename=ConsolidatedScrollReport.csv");

					java.io.PrintWriter out = res.getWriter();
					out.print("\n");
					out.print("\n");
					out.print("\n");
					out.print(",");
					out.print(",");
					out.print(",");
					out.print("Consolidated Scroll Report for the Terminal:"
							+cashobject_withdenom[0].getTerminalNo());
					out.print("\n");
					
					out.print("Scr No");
					out.print(",");
					out.print("Ac Type");
					out.print(",");
					out.print("Ac No");
					out.print(",");
					out.print("Payee Name");
					out.print(",");
					out.print("Tkn No");
					out.print(",");
					out.print("Csh Reced");
					out.print(",");
					out.print("Csh Paid");
					out.print(",");
					out.print("1000");
					out.print(",");
					out.print("500");
					out.print(",");
					out.print("100");
					out.print(",");
					out.print("50");
					out.print(",");
					out.print("20");
					out.print(",");
					out.print("10");
					out.print(",");
					out.print("5");
					out.print(",");
					out.print("2");
					out.print(",");
					out.print("1");
					out.print(",");
					out.print("coins");
					out.print(",");
					out.print("Runabal");
					out.print(",");
					out.print("Tml No");
					out.print(",");
					out.print("");
					out.print("\n");

					for (int k = 0; k < cashobject_withdenom.length; k++) {
						out.print(k);
						out.print(",");
						out.print(cashobject_withdenom[k].getScrollno());
						out.print(",");
						out.print(cashobject_withdenom[k].getAcctype());
						out.print(",");
						out.print(cashobject_withdenom[k].getAccno());
						out.print(",");
						out.print(cashobject_withdenom[k].getPOName());
						out.print(",");
						out.print(cashobject_withdenom[k].getTokenno());
						out.print(",");
						out.print(cashobject_withdenom[k].getAmount());
						out.print(",");
						out.print(cashobject_withdenom[k].getAmount());
						out.print(",");
						out.print(cashobject_withdenom[k].getR1000());
						out.print(",");
						out.print(cashobject_withdenom[k].getR500());
						out.print(",");
						out.print(cashobject_withdenom[k].getR100());
						out.print(",");
						out.print(cashobject_withdenom[k].getR50());
						out.print(",");
						out.print(cashobject_withdenom[k].getR10());
						out.print(",");
						out.print(cashobject_withdenom[k].getR5());
						out.print(",");
						out.print(cashobject_withdenom[k].getR2());
						out.print(",");
						out.print(cashobject_withdenom[k].getR1());
						out.print(",");
						out.print(cashobject_withdenom[k].getRcoins());
						out.print(",");
						out.print(cashobject_withdenom[k].getRunbal());
						out.print(",");
						out.print(cashobject_withdenom[k].getTerminalNo());
						out.print(",");
						out.print("\n");
					}

					request.setAttribute("displaymsg","Saved to excel file in C:");
					return null;
				}
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map.findForward(ResultHelp.getSuccess());
		
		
	
		
	}
	else if(map.getPath().equalsIgnoreCase("/CurrencyStockMenu"))
	{
	try{
	 System.out.println("*********CurrencystockMenu*************");
	 CurrencyStockForm stockForm=(CurrencyStockForm)form;
	 CashDelegate curDelegate =new CashDelegate();
	 stockForm.setValidation("null");
	double tml_runable =curDelegate.getCashTmlRunningBalance(tml,curDelegate.getSysDate()); 
	request.setAttribute("tml_runable",tml_runable);
	System.out.println("terminal balance=========>"+tml_runable);
	
	CurrencyStockObject currencystockobject = curDelegate.getCurrencyStockObject(tml,curDelegate.getSysDate(), 1);
	
	request.setAttribute("curobject1", currencystockobject.gets1000());
	
	request.setAttribute("curobject2", currencystockobject.gets500());
	request.setAttribute("curobject3", currencystockobject.gets100());
	request.setAttribute("curobject4", currencystockobject.gets50());
	request.setAttribute("curobject5", currencystockobject.gets20());
	request.setAttribute("curobject6", currencystockobject.gets10());
	request.setAttribute("curobject7", currencystockobject.gets5());
	request.setAttribute("curobject8", currencystockobject.gets2());
	request.setAttribute("curobject9", currencystockobject.gets1());
	request.setAttribute("curobject10", currencystockobject.getscoins());
    
     if(MenuNameReader.containsKeyScreen(stockForm.getPageId()))
		{
    	 	path=MenuNameReader.getScreenProperties(stockForm.getPageId());
			request.setAttribute("pageId",path);
			setRebalanceInitParams(request,path,curDelegate);
			request.setAttribute("submit", stockForm.getValue());
			return map.findForward(ResultHelp.getSuccess());

		}
     else
     	{
    	 return map.findForward(ResultHelp.getError());
     	}
	}catch (Exception e) 
		{		
	       e.printStackTrace(); 
    	   return map.findForward(ResultHelp.getSuccess());
		}
      

	}
	
	else if(map.getPath().equalsIgnoreCase("/Currency"))
	{
	System.out.println("*********Currencystock Updation*************");
	CurrencyStockForm stockForm=(CurrencyStockForm)form;
	CashDelegate curDelegate=new CashDelegate();
	request.setAttribute("submit", stockForm.getValue());
	String date = CashDelegate.getSysDate();
	double tml_runable =curDelegate.getCashTmlRunningBalance(tml,date); 
	stockForm.setValidation("null");
	request.setAttribute("tml_runable",tml_runable);
	String button_value = stockForm.getBut_value();
	
	CurrencyStockObject currencystockobject = curDelegate.getCurrencyStockObject(tml,curDelegate.getSysDate(), 1);
	
	request.setAttribute("curobject1", currencystockobject.gets1000());
	request.setAttribute("curobject2", currencystockobject.gets500());
	request.setAttribute("curobject3", currencystockobject.gets100());
	request.setAttribute("curobject4", currencystockobject.gets50());
	request.setAttribute("curobject5", currencystockobject.gets20());
	request.setAttribute("curobject6", currencystockobject.gets10());
	request.setAttribute("curobject7", currencystockobject.gets5());
	request.setAttribute("curobject8", currencystockobject.gets2());
	request.setAttribute("curobject9", currencystockobject.gets1());
	request.setAttribute("curobject10",currencystockobject.getscoins());
	
	int value=curDelegate.checkTmlOpenClose(tml,date);
		
	if(value==1)
	setRebalanceInitParams(request,path,curDelegate); 
	if(Integer.parseInt(stockForm.getValue())==1)
	{
		if(button_value.equalsIgnoreCase("update"))
		{   
			System.out.println("**********inside update*********"); 
			request.setAttribute("button_val", "UPDATE");
		}	
		
		  System.out.println("Updation Value=========="+stockForm.getUpbutton());
			if(stockForm.getUpbutton().equalsIgnoreCase("true"))
			{
				System.out.println("Are You Cuming Here ");
				currency_store(curDelegate,stockForm,2);
		
			}
	}
	if(Integer.parseInt(stockForm.getValue())==2)
	{
		if(button_value.equalsIgnoreCase("update"))
		{   
			System.out.println("**********inside update*********"); 
			request.setAttribute("button_val", "UPDATE");
		}	
		
		if(stockForm.getUpbutton().equalsIgnoreCase("true"))
			{
				closed_store(curDelegate, stockForm, 10, 1);
			
			}
		
	}
	if(button_value.equalsIgnoreCase("CLEAR")){
		System.out.println("**********inside clear*********");
		request.setAttribute("button_val", "CLEAR");
	}

	else if(value==0)
		stockForm.setValidation("checkDB");
		
	else if(value==2)
		stockForm.setValidation("cantupdate");
		
	else if(value==-1)
		stockForm.setValidation("terminalnot");
		
	if(stockForm.getBut_value().equalsIgnoreCase("close"))
	{
		closeMainCashier(curDelegate,stockForm);
	}
		
	}
	/** MENU-REBALANCING SCROLL REPORT**/
	else if(map.getPath().equalsIgnoreCase("/RebalancingMenu"))
	{
		try{
			System.out.println("*********RebalancingMenu*************");
			RebalanceForm rbform =(RebalanceForm)form;     
			CashDelegate rbDelegate = new CashDelegate();
			request.setAttribute("date",CashDelegate.getSysDate()) ;
			rbform.setValidation("null");
			double tml_runable =rbDelegate.getCashTmlRunningBalance(tml,rbDelegate.getSysDate()); 
			request.setAttribute("tml_runable",tml_runable);
			if(MenuNameReader.containsKeyScreen(rbform.getPageId()))
				{
					path=MenuNameReader.getScreenProperties(rbform.getPageId());
					request.setAttribute("pageId",path);
					setRebalanceInitParams(request,path,rbDelegate);
					return map.findForward(ResultHelp.getSuccess());
   
				}
			else
				{
					return map.findForward(ResultHelp.getError());
				}
			}catch (Exception e) 
				{		
					e.printStackTrace(); 
					return map.findForward(ResultHelp.getSuccess());
				}
          
        
	}
	/**REBALANCING SCROLL REPORT**/
	else if(map.getPath().equalsIgnoreCase("/Rebalance"))
	{
		RebalanceForm rbform =(RebalanceForm)form;
		System.out.println("**********inside Rebalancing scroll report***********");
		CashDelegate rbDelegate = new CashDelegate();
		double tml_runable =rbDelegate.getCashTmlRunningBalance(tml,rbDelegate.getSysDate()); 
		request.setAttribute("tml_runable",tml_runable);
		rbform.setValidation("null");
		System.out.println(request.getAttribute("pageId"));
		
		int value=rbDelegate.rebalancingScroll(rbform.getDate(),tml);
		
		if(value == 1)
			rbform.setValidation("success");//Successfull
		else if(value == 0)
			rbform.setValidation("noentry");//No entry in currency stock
		else if(value == 2)
			rbform.setValidation("notransction");//no transaction
					
		
		setRebalanceInitParams(request,path,rbDelegate);	
	}

	
	return map.findForward(ResultHelp.getSuccess());
}

	private void getTabbedpane(HttpServletRequest req,Recieiptsform recform)
	{ 
	
		   System.out.println("pageid===========>"+recform.getPageId());   
		   String pageActions[]={"/Receipt?tabPaneHeading=Personal&pageId="+recform.getPageId(),"/Receipt?tabPaneHeading=LoanStatus&pageId="+recform.getPageId()};
		   System.out.println("tabed pane heading..... "+recform.getPageId());
		   String tabHeading[]={"Personal","LoanStatus"};
		   
		   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("2012").trim()};
		   
		   req.setAttribute("TabHeading",tabHeading);
		   req.setAttribute("PageActions", pageActions);
		   req.setAttribute("PagePath", pagePath);
		   req.setAttribute("pageNum","3003");
		   req.setAttribute("panel",CommonPanelHeading.getPersonal());
	}
	

	private void getTabbedpane_pay(HttpServletRequest req,PaymentForm payForm)
	{ 
		 
		   System.out.println("pageid===========>"+payForm.getPageId());   
		   String pageActions[]={"/Payment?tabPaneHeading=Personal&pageId="+payForm.getPageId()};
		   
		   String tabHeading[]={"Personal"};
		   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim()};
		   
		   req.setAttribute("TabHeading",tabHeading);
		   req.setAttribute("PageActions", pageActions);
		   req.setAttribute("PagePath", pagePath);
		   req.setAttribute("pageNum","3003");
		   
	}


	private void setRebalanceInitParams(HttpServletRequest req, String path,
			CashDelegate rbDelegate) throws Exception

	{
		try {

			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
	}
	
	private void setPaymentinitParams(HttpServletRequest req, String path,CashDelegate rbDelegate) throws Exception

	{
		try {

			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
	}
	private void setExinitparams(HttpServletRequest req, String path,
			CashDelegate scrollDelegate) throws Exception

	{
		try {

			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
	}
	private void setScrollInitParams(HttpServletRequest req, String path,
			CashDelegate scrollDelegate) throws Exception

	{
		try {

			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
	}

	private void setReceiptparamform(HttpServletRequest req, String path,
			CashDelegate rcDelegate) throws Exception

	{
		try {

			req.setAttribute("pageId", path);
			
			System.out.println("I am in init params==============================");

		} catch (Exception e) {
			throw e;
		}
	}

private void settransferinitparams(HttpServletRequest req, String path,CashDelegate scrollDelegate) throws Exception
{
	try {
			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
	}
private void setmiscellinitparams(HttpServletRequest req, String path,CashDelegate scrollDelegate) throws Exception
{
	try {
			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}
}
	private void setErrorPageElements(String exception, HttpServletRequest req,
			String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);

	}

public void store(CashDelegate deligate,Recieiptsform stform,int form,int type,HttpServletRequest request) throws ApplicationException, RemoteException, SQLException
	{
		System.out.println("****************hi from store************************");
		System.out.println("Account holders name in store==="+stform.getAccname());
		
		 int copy_scrollno=0;

		String string_code = null;
		 cashobject = new CashObject();
		
		try
			{
			  module_obj_array = null;
			  
			  stform.setGen_scroll(0);
			  module_obj_array  = deligate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
			  module_obj_array1 =  deligate.getMainModules(2,"1001000,1002000,1003000,1004000,1005000,1006000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1017000,1018000");
			  String module =module_obj_array[Integer.valueOf(stform.getAccounttype())].getModuleAbbrv();
			  String pomodule =module_obj_array1[Integer.valueOf(stform.getCustacctype())].getModuleAbbrv();// PO
			  if(module!=null)
			  	{
	       
				  for(int i=0;i<module_obj_array.length;i++)
				  	{
					  String mod=module_obj_array[i].getModuleAbbrv();
	       	
					  if(mod.equalsIgnoreCase(module))
					  	{
						  String string_desc=module_obj_array[i].getModuleDesc();
	       	
						  string_code =String.valueOf(module_obj_array[i].getModuleCode());
						  
					  	}
				  	}
			  	}
			 
		      String postring_code =null;
		      
		      if(pomodule!=null)
		       {
		    	  for(int i=0;i<module_obj_array1.length;i++)
		    	  	{
		    		  String pomod=module_obj_array1[i].getModuleAbbrv();
		        	
		    		  if(pomod.equalsIgnoreCase(pomodule))
		    		  	{
		    			  String postring_abbrv=module_obj_array1[i].getModuleDesc();
		    			 
		    			  postring_code =String.valueOf(module_obj_array1[i].getModuleCode());
		    			 
		    		  	}
		        	}
		        } 
		try
	        {
	        	if(form==1)
	            	{
						
	            if(type==1 && form==1)
	                {
	            		
	            		
	                    if(string_code.startsWith("1009"))
	                    	{ 
	                    		
	                    		cashobject.setLockertype(stform.getLockertype());
	                    		cashobject.setAccno(String.valueOf(stform.getAccno()));
	                    		System.out.println("AccountName---------"+stform.getAccname());
	                    		cashobject.setAccname(stform.getAccname());
	                    		cashobject.setAcctype(string_code);     
	                    		cashobject.setAmount(stform.getAmount());       
	                    		cashobject.setCommamt(stform.getCommAcc());
	                    		cashobject.setCdind("D");
	                    		cashobject.setSharecat(0);
	                    		cashobject.setCustAcctype(null);
	                    		cashobject.setCustAccno(null);
	                    		cashobject.setCustCode(null);
	                    		cashobject.setPOName(null);
	                    		cashobject.setTrndate(deligate.getSysDate());
	                    		cashobject.setTrntime(deligate.getSysTime());
	                    	}
	                    
	                    else if(string_code.startsWith("1001"))
	                    	{
	                    		System.out.println("********************1001"+stform.getSharecat());
	                    		cashobject.setSharecat(Integer.valueOf(stform.getSharecat()));
	                    		cashobject.setAccno(String.valueOf(stform.getAccno()));
	                    		System.out.println("AccountName---------"+stform.getAccname());
	                    		request.setAttribute("acc_nm",stform.getAccname());
	                    		cashobject.setAccname(stform.getAccname());
	                    		cashobject.setAcctype(string_code);     
	                    		cashobject.setAmount(stform.getAmount());       
	                    		cashobject.setCommamt(stform.getCommAcc());
	                    		cashobject.setCdind("D");
	                    		cashobject.setLockertype(null);
	                    		cashobject.setCustAcctype(null);
	                    		cashobject.setCustAccno(null);
	                    		cashobject.setCustCode(null);
	                    		cashobject.setPOName(null);
	                    		cashobject.setTrndate(deligate.getSysDate());
	                    		cashobject.setTrntime(deligate.getSysTime());
	                    }
	                    
	                    else if(string_code.startsWith("1016"))
	                    {
	                    	System.out.println("********************1016");
	                        cashobject.setAccno("0");
	                        System.out.println("AccountName---------"+stform.getAccname());
	                        cashobject.setAccname(stform.getPurchase());
	                        cashobject.setAcctype(string_code);     
	                        cashobject.setAmount(stform.getPoamount());       
	                        cashobject.setCommamt(stform.getCommAcc());
	                        cashobject.setCdind("D");
	                        cashobject.setLockertype(null);
	                        cashobject.setSharecat(0);
	                        cashobject.setCustAcctype(postring_code);
	                        cashobject.setCustAccno(String.valueOf(stform.getCustacno()));
	                        cashobject.setCustCode(stform.getDescription());
	                        cashobject.setPOName(stform.getFavour());
	                        cashobject.setTrndate(deligate.getSysDate());//sowmya
	                        cashobject.setTrntime(deligate.getSysDate());//sowmya
	                    }
	                    else 
	                    {
	                    	
	                        System.out.println("amount in store $$$$$$$$"+stform.getAmount());
	                        
	                        System.out.println("Account Number------------"+String.valueOf(stform.getAccno()));
	                    	cashobject.setAccno(String.valueOf(stform.getAccno()));
	           
	                    	System.out.println("AccountName--------"+stform.getAccname());
	                        cashobject.setAccname(stform.getAccname());
	                        cashobject.setAcctype(string_code);     
	                        cashobject.setAmount(stform.getAmount());       
	                        cashobject.setCommamt(0);
	                        cashobject.setCdind("D");
	                        cashobject.setLockertype(null);
	                        cashobject.setSharecat(0);
	                        cashobject.setCustAcctype(null);
	                        cashobject.setCustAccno(null);
	                        cashobject.setCustCode(null);
	                        cashobject.setPOName(null);
	                        cashobject.setTrndate(deligate.getSysDate());//ship.....22/06/2006
	                        cashobject.setTrntime(deligate.getSysTime());//ship.....22/06/2006
	                    }
	                  
	                    if(string_code.startsWith("1001"))
	                        cashobject.setTrntype("A");
	                    else if(string_code.startsWith("1003") || string_code.startsWith("1004") || string_code.startsWith("1005"))
	                        cashobject.setTrntype("D");
	                    else
	                        cashobject.setTrntype("R");
	                    
	                    cashobject.setScrollno(stform.getScrollno());//txtScrollNo
	                    copy_scrollno=stform.getScrollno();
	                   	 
	                }
	               
	            cashobject.uv.setUserId(user);
                cashobject.uv.setUserTml(tml);
                cashobject.uv.setUserDate(deligate.getSysDateTime());//sowmya..24/04/2008
                cashobject.setTerminalNo("CA99");
                System.out.println("in currency... store....... before storecurrncy"+ stform.getR_fifty()+"jhhjgj"+ stform.getP_fifty());
               
                receiptcurrency(cashobject,stform,2);
                
                
                // storeCurrency(cashobject);          
                System.out.println("in currency... store....... after storecurrncy");
                int int_scroll_no=0;
                
                System.out.println("in currency... form111....... ");
	          
						if(cashobject.getCdind().equals("D"))
		                {
							
							System.out.println("going inside storeDayCash");
		                    int_scroll_no=deligate.storeDayCash(cashobject);
		                    
		                    System.out.println("int scroll no = "+int_scroll_no+"scroll no from delegate"+cashobject.getScrollno());
		                    System.out.println("coming outside storeDayCash");
		                }
	              
					
					System.out.println("scroll no from jsp page"+copy_scrollno);
	                if(int_scroll_no==copy_scrollno)
	                {
	                System.out.println("Scroll No < "+int_scroll_no+" > is being Updated");
	                stform.setScrverify("scupdated");
	                stform.setGen_scroll(0);
	                }
	                else
	                {
	                	stform.setGen_scroll(int_scroll_no);
	                	System.out.println("Note the Scroll No  <"+int_scroll_no+" >Scroll Generation");
	                
	                }
	        } 
	        }
	         
	          catch(RemoteException ex1)
	          {
	            ex1.printStackTrace();
	           
	           }
	       
	        
	    }catch (Exception e) {
	    	e.printStackTrace();	}
	    }


public void miscell_store(CashDelegate msDelegate,MiscellaneousForm msForm,int form,int type,String[] cbox,String[] gltype,String[] glcode,String[] glamount)
{
try {
	System.out.println("^^^^^^^^^^^^^^inside miscell store^^^^^^^^^^^");
	int int_scroll_no=0;
	int copy_scrollno=msForm.getScroll_no();
	cashobject=new CashObject();
	msForm.setGen_scroll(0);
	 msForm.setUpdate_scroll(0);
	if(type==1 && form==3) //form 3=miscellaneousreceipt
		{
			System.out.println("............... inside else if type==1 && form==3 .................");
			cashobject.setScrollno(msForm.getScroll_no());
			//we have to addcashobject.setVchno(obj_miscellaneousreceipts.voucher_number_currency);
			//System.out.println("vch no in mis rec"+obj_miscellaneousreceipts.voucher_number_currency);
			cashobject.setTrntype("R");
			cashobject.setVchtype("R");
			cashobject.setTerminalNo(tml);
			cashobject.setAccname(msForm.getNarration());
			cashobject.setCdind("D");
			cashobject.setAttached("F");
			cashobject.setAmount((msForm.getAmount()));
			cashobject.setTrndate(msDelegate.getSysDate());//sowmya
			cashobject.setTrntime(msDelegate.getSysTime());//sowmya
		}
	if(form==3)
		{
			cashobject.uv.setUserId(user);
			cashobject.uv.setUserTml(tml);
			cashobject.uv.setUserDate(msDelegate.getSysDateTime());
			cashobject.setTerminalNo(tml);
			//sowmya.....added on 13/05/2008 to avoid two entries in DayCash
			if(msForm.getScroll_no()==0)
				{
					cashobject.setVchno(-1);
					//i have to add  storeCurrency(cashobject);
					int_scroll_no=msDelegate.storeDayCash(cashobject);
					System.out.println("scroll no from cashobject!!!!!!!!!!!!!!! !!!!"+int_scroll_no );
				}
			else     
				{
         //i have to add storeCurrency(cashobject);
					int_scroll_no=msDelegate.storeDayCash(cashobject);
					System.out.println("..scroll in 817..."+int_scroll_no);
         //flag = 1;
				}
     //
			System.out.println("............inside form 3............");
			System.out.println("****************sc no***********"+int_scroll_no);
			System.out.println("inside form 3..scroll_no trn_date"+cashobject.getTrndate());
			try
			{
			
			System.out.println("==========>"+int_scroll_no+"==============>"+msDelegate.getSysDate());
			cashobject=new CashObject();
			cashobject=msDelegate.getData(int_scroll_no,0,msDelegate.getSysDate());
			System.out.println("cash object========================>"+cashobject);
			//sowmya
		
		
			voucherDataObject=new VoucherDataObject();
			voucherDataObject.setVoucherType("R");
			//System.out.println("cashobject.getVchno()--------------->"+cashobject.getVchno());
			if(cashobject.getVchno()!=0)
			{
			voucherDataObject.setVoucherNo(cashobject.getVchno());
			voucherDataObject.setTransactionDate(cashobject.getTrndate());//sowmya 13/05/2008
			voucherDataObject.obj_userverifier.setUserId(user);
			voucherDataObject.obj_userverifier.setUserTml(tml);
			voucherDataObject.obj_userverifier.setUserDate(msDelegate.getSysDateTime());
			System.out.println("............before%%%inside form 4......");
			//sowmya.....added to delete the rows in the gl table when submit button is pressed..13/05/2008
			msDelegate.deleteVoucherDataTable(voucherDataObject.getVoucherNo(),voucherDataObject.getVoucherType(),voucherDataObject.obj_userverifier.getUserTml(),voucherDataObject.getTransactionDate());
			System.out.println("............inside form 4......");
			int[] gl_param = msDelegate.getGLParam();
			System.out.println("gl_type = "+gl_param[0]);
			System.out.println("gl_code = "+gl_param[1]);
			voucherDataObject.setGlType(String.valueOf(gl_param[0]));
			voucherDataObject.setGlCode(gl_param[1]);
			voucherDataObject.setTransactionAmount(msForm.getAmount());
			voucherDataObject.setCdIndicator("D");
			voucherDataObject.setNarration(msForm.getNarration());
			voucherDataObject.setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
			System.out.println("............inside form 5............");
			msDelegate.storeVoucherData(voucherDataObject);
			//$$$$$$$$$$this for u shoud to add dont forget .......
			
         	
             //sowmya;;;;;13/05/2008
            
                for(int x=0;x<cbox.length;x++)
                {
                	int k= Integer.parseInt(cbox[x].trim());
             voucherDataObject.setGlType(gltype[k]);
             voucherDataObject.setGlCode(Integer.parseInt(glcode[k]));
             voucherDataObject.setCdIndicator("C");
             System.out.println("The value of sum in cash action-----"+sum);
             voucherDataObject.setTransactionAmount(Double.parseDouble(glamount[k]));
             voucherDataObject.setNarration(msForm.getNarration());
             voucherDataObject.setTransactionDate(msDelegate.getSysDate());//sowmya;;;;;13/05/2008
             
             msDelegate.storeVoucherData(voucherDataObject);
                }
        
				//*****i hav to add obj_miscellaneousreceipts.clearForm();
			}
 
 if(form!=8 && form!=1 && form!=9 && form!=10 && form!=11)//ship....added form!=9 on 17/10/2005, form!=10 on 20/10/2005
 {
	 msForm.setGen_scroll(0);
	 msForm.setUpdate_scroll(0);
	 System.out.println("int_scroll_no "+int_scroll_no);
     System.out.println("copy_scrollno "+copy_scrollno);
     
     if(int_scroll_no==copy_scrollno)
     {
    	msForm.setUpdate_scroll(int_scroll_no);
    	 System.out.println("Scroll No < "+int_scroll_no+" > has been Updated");
     }
     //sowmya
     //else
     else if((int_scroll_no > 0) && (int_scroll_no!=copy_scrollno))
     {
    	 msForm.setGen_scroll(int_scroll_no);
    	 System.out.println("Note the Scroll No < "+int_scroll_no+" >");
       
     }
     else if((int_scroll_no==-1) || (int_scroll_no==-2))
     {
         if(int_scroll_no==-1){
        	 msForm.setMsClosed("terminalnotexist");
        	 System.out.println("Terminal doesnt exist");
         }
         
         else if(int_scroll_no==-2){
        	 msForm.setMsClosed("Insufficient");
        	 System.out.println("Insufficient Currency Denominations");
         }
      }
     ///////////////
 }
 
}
 catch(ScrollNotFoundException ex)
 {
     ex.printStackTrace();
 }
catch(Exception Exception)
 {
     Exception.printStackTrace();
  }
}
}catch (Exception e) {
	e.printStackTrace();
}
}

public void pay_store(CashDelegate payDelegate,PaymentForm payForm,int form,int type)
{
	
try
 {

	int vchno=payForm.getVoucher_no_bk();
	int tokenno=payForm.getToken_no();
	chequeobject=payDelegate.getCashWithdraw(tokenno,1,payDelegate.getSysDate());
	voucherDataObject = payDelegate.getPaymentData(vchno,"P",payDelegate.getSysDate());
	cashObject=new CashObject();  
	int copy_scrollno = 0;
	if(form==1)
    {
		
	if(type==1 && form==1)
    {
		//General Payment
		System.out.println("(((((((((((((((inside the payment)))))))))))))");	
     if(payForm.getCombo_type().equalsIgnoreCase("General"))
          {
    	 	
    	 cashobject=new CashObject();
    	 	System.out.println("inside general payment"+chequeobject.getAccNo());
    	 	System.out.println("$$$$$$$$"+chequeobject.getAccNo());
    	 	if(cashobject!=null){
    	 	cashobject.setAccno(String.valueOf(chequeobject.getAccNo()));
            cashobject.setAcctype(chequeobject.getAccType());
            cashobject.setAmount(chequeobject.getTransAmount());
            cashobject.setTokenno(chequeobject.getTokenNo());
            cashobject.setAccname(chequeobject.getPayeeName());
            cashobject.setCdind("C");           
            cashobject.setVchtype(chequeobject.getTransMode());
            cashobject.setChqno(chequeobject.getChqNo());
            cashobject.setTrndate(payDelegate.getSysDate());
            cashobject.setTrntime(payDelegate.getSysTime());
    	 	}
            System.out.println("****outside general payment");
           }
     
       //Voucher Payment
       else if(payForm.getCombo_type().equalsIgnoreCase("Voucher"))//changed from 2 to 0
          {
    	   System.out.println("Are You here????????");
    	   cashObject=new CashObject();
    	   System.out.println("inside Voucher payment"); 
    	   System.out.println(voucherDataObject.getNarration());
    	   System.out.println(voucherDataObject.getGlCode());
    	   System.out.println("gltype"+voucherDataObject.getGlType());
    	   System.out.println(voucherDataObject.getTransactionAmount());
    	   System.out.println(voucherDataObject.getVoucherNo());
    	   System.out.println(voucherDataObject.getVoucherType());
    	   cashObject.setCdind("C");
           cashObject.setAccname(voucherDataObject.getNarration());
           cashObject.setAccno(String.valueOf(voucherDataObject.getGlCode()));
           System.out.println("^^^^^*****"+cashObject.getAccno());
           cashObject.setAcctype(voucherDataObject.getGlType());
          
           
           cashObject.setAmount(voucherDataObject.getTransactionAmount());
           cashObject.setVchno(voucherDataObject.getVoucherNo());           
           cashObject.setVchtype(voucherDataObject.getVoucherType());
           cashObject.setTrndate(payDelegate.getSysDate());
           cashObject.setTrntime(payDelegate.getSysTime());
          // cashObject.setAcctype("v");
           
          }
          //Cash/Voucher Payment
          else if(payForm.getCombo_type().equalsIgnoreCase("Cash/Voucher"))
          {
        	  cashObject = payDelegate.getPaymentDetails(payForm.getVoucher_no(),payDelegate.getSysDate());
        	  System.out.println("cashobject ===="+cashObject);
        	  System.out.println("inside Cash/Voucher payment"); 
        	  cashObject.setAccno(String.valueOf(cashObject.getAccno()));
        	 System.out.println("cash object acc no"+String.valueOf(cashObject.getAccno()));
              cashObject.setVchno(cashObject.getVchno());
              System.out.println("cash object acc no"+cashObject.getVchno());
              cashObject.setTrndate(payDelegate.getSysDate());              
              cashObject.setTrntime(payDelegate.getSysTime());
              //cashObject.setAcctype("c/v");
              //cashobject.setAcctype(voucherDataObject.getGlType());
              System.out.println("inside Cash/Voucher payment"); 
          }
     		cashObject.setScrollno(0);//txtScrollNo   
     		copy_scrollno = 0;
       }
	  
	  cashObject.uv.setUserId(user);
      cashObject.uv.setUserTml(tml);
      cashObject.uv.setUserDate(payDelegate.getSysDate());//ship.....22/06/2006
      cashObject.setTerminalNo(tml);
     paymentcurrency(payForm,cashObject,1);
      int int_scroll_no=0;
     System.out.println("Scrolllllll in action--------"+int_scroll_no);
     String combo= payForm.getCombo_type();
     System.out.println("Value of combo========"+combo);
    // System.out.println("helllooooooooooo"+cashobject.getCdind());
      if((payForm.getCombo_type().equalsIgnoreCase("Cash/Voucher")) ||(payForm.getCombo_type().equalsIgnoreCase("Voucher")) && cashObject.getCdind().equals("C"))
      {   
    	  System.out.println("Are You Setting Cash Object ?????????");
    	  System.out.println("cashobject"+cashObject.toString());
    	  System.out.println("Are You Coming Here  !!!!!!!!!!!!!!!!!!!");
          int_scroll_no=payDelegate.storeVoucher(cashObject);
          System.out.println("scroll n from Voucher generation"+int_scroll_no);
      }
      
      else if((payForm.getCombo_type().equalsIgnoreCase("General")) && cashobject.getCdind().equals("C"))
      {
    	  cashobject.uv.setUserId(user);
          cashobject.uv.setUserTml(tml);
          cashobject.uv.setUserDate(payDelegate.getSysDate());//ship.....22/06/2006
          cashobject.setTerminalNo(tml);
    	  System.out.println("cashobject");
    	  //System.out.println("cashobject"+cashObject.toString());
    	  int_scroll_no=payDelegate.storeToken(cashobject); 
          System.out.println("scroll n from token generation"+int_scroll_no);
        
      }
      if(int_scroll_no==copy_scrollno)
      {
          payForm.setUpdate_scroll(int_scroll_no);
    	  System.out.println("Scroll No < "+int_scroll_no+" > is being Updated");
      }
      else
      {
    	  payForm.setGen_scroll(int_scroll_no);
    	  System.out.println("Note the Scroll No  <"+int_scroll_no+" >");
          
      }
    } 
 }catch (Exception e) {
	e.printStackTrace();
}	
}
public void transfer_store(CashDelegate trDelegate,TransferForm trForm,int form,int type) throws InsufficientAmountException
{
	try {  
	int copy_scrollno=0;
	 int int_scroll_no=0;
	 trForm.setUpdate_scroll(0);
	 
	if(type==2 && form==7) //form7= InterCounterTransfer
	{
		cashobject=new CashObject();
		
		if(cashobject.uv.getVerTml()==null)
		{
			System.out.println("inside intercounter_transfer store");
			cashobject.setAmount(trForm.getAmount());//txtAmt
			cashobject.setOthtml(String.valueOf(trForm.getTerminal()));//cmbTerminalNo 
			cashobject.setTerminalNo(tml);//terminal no from heading
			cashobject.setUserTml(tml);
			cashobject.setScrollno(trForm.getScroll_no());//txtScrollNo
			cashobject.setTrntype("A");
			cashobject.setCdind("C");
			cashobject.setTrndate(trDelegate.getSysDate());
			cashobject.setTrntime(trDelegate.getSysTime());
			copy_scrollno=trForm.getScroll_no();
		}
	}
itc:if(form!=8 && form!=1 && form!=9 && form!=3)//sowmya
{
        	 if(form==7)//intercountertransfer
             {
        		 cashobject.setTerminalNo(tml);
             	cashobject.uv.setUserId(user);
             	cashobject.uv.setUserTml(tml);
             	cashobject.uv.setUserDate(trDelegate.getSysDateTime());//sowmya
             	System.out.println("verifying terminal...7"+cashobject.uv.getVerTml());
                 
                 /*if(Integer.parseInt(obj_intercountertransfer.txt_scrollno.getText().trim())==0)
                 {*/
                     //storeCurrency(cashobject);
                     try
                     {
                         System.out.println("inside try");
                         cashobject.setInsufficientFlag(false);
                         int_scroll_no=trDelegate.storeDayCash(cashobject);
                     }
                     catch(InsufficientAmountException ex1)
                     {
                    	 System.out.println("insuffient_currency");
                         System.out.println("Exception");
                         ex1.printStackTrace();
                         
                         int res=JOptionPane.showConfirmDialog(null,"Do you wish to continue?","Insufficient Amount",JOptionPane.YES_NO_OPTION);
                         
                         if(res==JOptionPane.YES_OPTION)
                         {
                             cashobject.setInsufficientFlag(true);
                             int_scroll_no=trDelegate.storeDayCash(cashobject); 
                         }
                         else
                         {
                        	 System.out.println(".........//////////// else part");
                            
                             break itc;
                         }
                     }
                 }
          
}
        	 
             if(form!=8 && form!=1 && form!=9 && form!=10 && form!=11)//ship....added form!=9 on 17/10/2005, form!=10 on 20/10/2005
             {
                 
            	 System.out.println("int_scroll_no "+int_scroll_no);
     			System.out.println("copy_scrollno "+copy_scrollno);
                 if(int_scroll_no==copy_scrollno)
                 {
                     trForm.setUpdate_scroll(int_scroll_no);
                	 System.out.println("Scroll No < "+int_scroll_no+" > has been Updated");
                 
                 }
                 
                 else if((int_scroll_no > 0) && (int_scroll_no!=copy_scrollno))
                 {
                	 trForm.setGen_scroll(int_scroll_no);
                	 System.out.println("Note the Scroll No < "+int_scroll_no+" >Scroll Generation");
                   
                 }
                 else if((int_scroll_no==-1) || (int_scroll_no==-2))
                 {
                     if(int_scroll_no==-1)
                     {
                    	 trForm.setValidation("notexist");
                    	 System.out.println("Terminal doesnt exist");
                    	
                     }
                     else if(int_scroll_no==-2)
                     {
                    	 System.out.println("Insufficient Currency Denominations");
                    	 trForm.setValidation("Insufficient");
                     }
                  
                 }
                 ///////////////
             }
			
	      ///////////////
	 
    	}catch (Exception e) {
    		e.printStackTrace();
    	} 
	}
	
	
public void exchange_store(CashDelegate exDelegate,ExchangeForm exForm,int form,int type)
{
if(type==1 && form==8) //form 8=CurrencyExchange
{
	try {
		CashObject coo=storeCurrency1(exForm,1);
		System.out.println("helloooooooooooooooooooo");
		coo.uv.setUserTml(tml);
		coo.setTrndate(exDelegate.getSysDate());//sss
		coo.setTrntime(exDelegate.getSysTime());
    //sowmya
    System.out.println("in currency... store....... form 8 currncyexc"+coo.getP100());
	System.out.println("in currency... store....... form 8 currncyexc"+coo.getR50());
		exDelegate.updateCurrency(coo);
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

public void currency_store(CashDelegate curDelegate,CurrencyStockForm curForm,int form)
{
	try
	{
		 cashobject=new CashObject();
		 System.out.println("***********Currency stock updation***********");
		 System.out.println("terminal no"+cashobject);
		
		 if(form==2)//Curr Stk Updation
		 {
			cashobject.setTerminalNo(tml);
			cashobject.setTrndate(curDelegate.getSysDate());
			cashobject.uv.setUserTml(tml);
			cashobject.uv.setUserId(user);
			cashobject.uv.setUserDate(curDelegate.getSysDateTime());
    
			storeCurrency(cashobject,curForm,form);
    
			System.out.println("tml no = "+cashobject.getTerminalNo());
    
			int flag = curDelegate.updateCash(cashobject);
    
			System.out.println("flag....after update = "+flag);
    
			if(flag==1)
			{
				System.out.println("Currency Stock is Updated Successfully");
                   curForm.setValidation("updated");
			}
			else
			{
				System.out.println("Error.....could not update Curr Stk");
       
			}
		}
	}catch (Exception e) {
	e.printStackTrace();
	}
	if(form==2){
	cashobject.setTerminalNo(tml);
    cashobject.setTrndate(curDelegate.getSysDate());
    cashobject.uv.setUserTml(tml);
    cashobject.uv.setUserId(user);
    cashobject.uv.setUserDate(curDelegate.getSysDateTime());
	}
	else if(form==2)//ship.....currency stock updation.....22/06/2006
    {
        System.out.println("amount in terminal close== ");
       // double_amount = Double.parseDouble(Validations.changeFloat(obj_currencystockupdation.tml_runbal));
    }
}

public void closed_store(CashDelegate clDelegate,CurrencyStockForm curForm,int form,int type)
{
	try{
	cashobject=new CashObject();
	
	if(form==10)//sowmya....cash closing
    {
        System.out.println("amount in terminal close== ");
       // double_amount=Double.parseDouble(Validations.changeFloat(obj_cashclosing.tml_runbal));
    }
	 if(type==1 && form==10) //form 10 = cash closing
     {
		 System.out.println("in currency... store....... form9 treminalclose");
         cashobject.setAmount(0.0);
         cashobject.setOthtml(null);
         cashobject.setTerminalNo(tml);
         cashobject.setScrollno(0);
         cashobject.setTrntype("C");
         cashobject.setCdind("C");
         cashobject.setTrndate(clDelegate.getSysDate());//sowmya
         cashobject.uv.setUserTml(tml);
         cashobject.uv.setUserId(user);
         cashobject.uv.setUserDate(clDelegate.getSysDateTime());//sowmya
         cashobject.setTrntime(clDelegate.getSysDateTime());//sowmya
        int  copy_scrollno = 0;
         storeCurrency(cashobject,curForm,form);      
     }
     if(form==10)
     {
      
         System.out.println("tml no = "+cashobject.getTerminalNo());
         int flag = clDelegate.updateCash(cashobject);
         
         System.out.println("flag....after update = "+flag);
         if(flag==1)
         {
             System.out.println("Currency denomination is updated successfully");
            
         }
         else
         {
        	  System.out.println("Error.....could not update");
            
         }
     }
	}catch (Exception e) {
		e.printStackTrace();
	}
}

public void currencyex( int lr,ExchangeForm exForm,CashDelegate exDelegate,HttpServletRequest request)
{
	double_amount=0.0;
	double_amount=Double.parseDouble(exForm.getAmount());
	HttpServletRequest req=null;
	System.out.println("value of the jsp page....."+double_amount);
	if(lr==0)
		sum=exForm.getTotal();
	System.out.println("value of the total....."+sum+"gfgdhdhfd"+exForm.getTotal());
	if(lr==1)
	
		sum1=sum1+exForm.getRefund();
	System.out.println("value of the refund....."+sum1);
	if(lr==0)
	{
	
    if((sum-sum1)==double_amount)
     {
    	exForm.setValidation("invaliddata");
    	System.out.println("Invalid data entry");
     }
    else
    
   if(double_amount>0)
    {
     System.out.println("Data OK123 ?? ");
   if(exForm.getExchange().equalsIgnoreCase("true"))
     {
	   System.out.println("Hey it Not Affecting!!!!!!!!!!!!");
	   exchange_store(exDelegate,exForm,8,1);
	   System.out.println("----------Currency Exchanged Sucessfully-------");
	   exForm.setError1("exch767676anged");
	   System.out.println("Currency exchanged===+++++++++=="+exForm.getError1());
	  request.setAttribute("sucess","Currency Exchanged Sucessfully");
	   
     }
                        
     }
    
	if((sum-sum1)>double_amount)
   {
       String strefamount=Validations.changeFloat(sum-double_amount-sum1);
       double_refamount=Double.parseDouble(strefamount);
       exForm.setValidation("refund");
       System.out.println("Refund the amount"); 
     
   }
	
	}
if(lr==1)
{
   if(lr==1)
   {
       if((sum-double_amount)>0) //changed here from double_amount to (sum-double_amount)
       {
    	   System.out.println("The sum is -------------"+sum);
    	   System.out.println("The Double amount--------"+double_amount);
    	   System.out.println("Disfference===="+(sum-double_amount));
    	   
    	   
           if((sum1)==(sum-double_amount))
           {
             
             if(double_amount>0)
             {
             System.out.println("Data OK1234?? ");
                       if(exForm.getExchange().equalsIgnoreCase("true"))
                       {
                    	   
                    	   exchange_store(exDelegate,exForm,8,1);
                       }
                    
                   }
              }
       }
           
       if((sum1)>(sum-double_amount))
       {
          double_refamount=Double.parseDouble(Validations.changeFloat(double_refamount));
           double strecamount=((sum1)-(sum-double_amount));
           exForm.setTotal((strecamount));
           exForm.setStrecamount(strecamount);
           System.out.println("You are paying more " +strecamount);
       }
   } 
}

}
public void terminal_store(CashDelegate clDelegate,ExchangeForm exForm,int type,int form) throws RemoteException, SQLException
{
	int  copy_scrollno=0;
	cashobject=new CashObject();
	if(type==2 && form==9) //form 9=terminalclosing
    {
        System.out.println("in currency... store....... form9 terminal close");
        cashobject.setAmount(exForm.getTr_amount());
        cashobject.setOthtml(tml);
        cashobject.setTerminalNo("LK99");
        cashobject.setScrollno(exForm.getScroll_no());
        cashobject.setTrntype("T");
        cashobject.setCdind("C");
        cashobject.setTrndate(clDelegate.getSysDate());//ship.....22/06/2006
        cashobject.setTrntime(clDelegate.getSysTime());//ship.....22/06/2006
        cashobject.uv.setUserTml("LK99");
        cashobject.uv.setUserId("MANU");
        cashobject.uv.setUserDate(clDelegate.getSysDateTime());//ship.....22/06/2006
        copy_scrollno=exForm.getScroll_no();
        System.out.println("in currency... store....... form9 terminal closed");
    }
	
    if(form==9)
    {
    	
    	storeCurrency1(exForm,1);
        System.out.println("tml no = "+cashobject.getTerminalNo());
        int flag = clDelegate.updateTerminal(cashobject);
        System.out.println("flag....after update = "+flag);
        if(flag==1)
        {
            System.out.println("Currency denomination is updated successfully");
         
        }
        else
        {
        	 System.out.println("Error.....could not update");
           
        }
    }
}

void storeCurrency(CashObject co,CurrencyStockForm curForm,int form)
{
    System.out.println("..inside store currency....form.."+form);
    
    co.setR1000((curForm.getThousand()>0)?(curForm.getThousand()):0);
    co.setR500((curForm.getFivered()>0)?(curForm.getFivered()):0);
    co.setR100((curForm.getHundred()>0)?(curForm.getHundred()):0);
    co.setR50((curForm.getFifty()>0)?(curForm.getFifty()):0);
    co.setR20((curForm.getTwenty()>0)?(curForm.getTwenty()):0);
    co.setR10((curForm.getTen()>0)?(curForm.getTen()):0);
    co.setR5((curForm.getFive()>0)?(curForm.getFive()):0);
    co.setR2((curForm.getTwo()>0)?(curForm.getTwo()):0);
    co.setR1((curForm.getOne()>0)?(curForm.getOne()):0);
    co.setRcoins((curForm.getCoins()>0)?(curForm.getCoins()):0);        
}
public boolean validateDataEntered(Recieiptsform recForm,CashDelegate recDelegate,HttpServletRequest request)
{ 
	System.out.println("I am inside Validatedata");
	try{
		String string_code=null;
		double amount = recForm.getAmount();
		
		System.out.println("Account type of the module project"+recForm.getAccounttype());
		module_obj_array  = recDelegate.getMainModules(2, "1001000,1002000,1003000,1004000,1005000,1007000,1008000,1009000,1010000,1013000,1014000,1015000,1016000,1017000,1018000");
		String module =module_obj_array[Integer.valueOf(recForm.getAccounttype())].getModuleAbbrv();
		request.setAttribute("MainModules", module_obj_array);
	  
	  
	  if(module!=null)
	  	{
 
		  for(int i=0;i<module_obj_array.length;i++)
		  	{
			  String mod=module_obj_array[i].getModuleAbbrv();
 	
			  if(mod.equalsIgnoreCase(module))
  		  	{
  			  String string_desc=module_obj_array[i].getModuleDesc();
  			  request.setAttribute("module",string_desc);
  			string_code=module_obj_array[i].getModuleCode();
  			  
  			  
  		  	}
		  	}
	  	}  
    if(recForm.getAccno()>0)
    {
    	System.out.println("************amount>0_verification**********");
    	try
        {
            accountobject=recDelegate.getAccount(null,module_obj_array[Integer.parseInt(recForm.getAccounttype())].getModuleCode(),recForm.getAccno(),recDelegate.getSysDate());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }            
        
        if(accountobject!=null)
        {
            if(accountobject.getAccStatus().equals("C"))
            {
                System.out.println("Given account is Closed");
                return false;
              
            }
            else if(accountobject.getAccStatus().equals("I"))
            {
                
            	System.out.println("InOperative Account");
                return false;
            }
            else if(accountobject.getVerified()==null)
            {
               
            	System.out.println("Given account is not yet Verified");
                return false;
            }
            else if((accountobject.getDefaultInd()!=null)?accountobject.getDefaultInd().equals("T"):false)
            { 
               
            	System.out.println("Default account");
                return false;
            }
            else if((accountobject.getFreezeInd()!=null)?accountobject.getFreezeInd().equals("T"):false)
            {
                
            	System.out.println("Freezed account");
                return false;
            }
        }
        else
        {
           
        	System.out.println("Given account number not found.....1");
            return false;
        }
    }
    
    if((recForm.getAmount())>0)
    {
    	System.out.println("************Amount_verification**********"+string_code);
    	
    	if(string_code.startsWith("1001"))//SH
        {
        	System.out.println("************1001_verification**********"+string_code);
        	if((recForm.getAccno())==0)
            {
                int minshares = 0;
                double amt = 0.00,minamt = 0.00,shareamt = 0.00;
                
                try
                {
                	 minshares = sharecategoryobject[Integer.valueOf(recForm.getSharecat())].getMinShare();
    				 System.out.println("minimum shares"+minshares);
    				 shareamt = sharecategoryobject[Integer.valueOf(recForm.getSharecat())].getShareVal();
    				 minamt = minshares * shareamt;
                    amt =(amount);
                    
                    if(amt<minamt)
                    {
                        
                        System.out.println("Min Share Amount for Category < "+recForm.getSharecat()+" >\n should be Rs. "+minamt+"");
                        return false;
                    }
                }
                catch(Exception e1)
                {
                	System.out.println("Retrieve Exception :");
                    return false;
                }
            }
            else
                {
                    amount = recForm.getAmount();
                    
                    double amt = 0.00,shareamt = 0.00;
                    
                    try
                    {
                        shareamt = sharecategoryobject[Integer.valueOf(recForm.getSharecat())].getShareVal();
                        amt = (amount);
                        
                        if(amt<shareamt)
                        {
                            
                            System.out.println("Share Value for Category < "+recForm.getSharecat()+" > is Rs. "+shareamt+"");
                            return false;
                        }
                    }
                catch(Exception e1)
                {
                	System.out.println("Retrieve Exception :" +e1);
                    return false;
                }
            }
        }
        else if(string_code.startsWith("1004"))
        {
        	System.out.println("************1004_verification**********");
        	if(recForm.getAccno()==0)
            {
                double amt = 0.00,minamt = 0.00;
                
                try
                {
                    amt = recForm.getAmount();
                    
                    if(amt<minamt)
                    {      
                        
                        System.out.println("Min Amount should be Rs. "+minamt+"");
                        return false;
                    }
                }
                catch(Exception e1)
                {
                	System.out.println("Retrieve Exception :");
                    return false;
                }
            }
            else
            {
                double amt=(recForm.getAmount());
                
                double days=accountobject.getDepositdays();//for deposit days;
                double depamt=accountobject.getAmount();
                double rdbal=accountobject.getShadowBalance();//rd balance
                double int_amt = accountobject.getIntAmount();//interest
                
                if(amt<depamt)
                {
                    
                	System.out.println("Your installment amount is "+depamt+"\n you are paying less than that");
                    return false;
                }
                else if(amt>((depamt*days)+int_amt-rdbal))
                {
                   
                	System.out.println("Paid amt is more than amount to pay");
                    return false;
                }
                else if(amt%depamt!=0)
                {
                    
                	System.out.println("Amount is not multiple of installment amount");
                    return false;
                }
            }
        }            
        else if(string_code.startsWith("1009"))//LK
        {
           System.out.println("************Locker_verification**********");
        	double lock_amt = 0.00;
            lock_amt = recForm.getAmount();
            
            for(int i=0;i<lock_desc.length;i++)
            {
                if(recForm.getLockertype().equals(lock_desc[i]))
                    lk_ty = lock_types[i];
            }
            
            try
            {
                double rent = recDelegate.getRent(lk_ty,365,101,recDelegate.getSysDate());
                
               System.out.println("lk rent to be collected == "+rent);
                
                if(lock_amt<rent)
                {
                    
                	System.out.println("U r Paying less rent...rent to be paid is Rs. "+rent+"");
                    return false;
                }
            }
            catch(Exception e1)
            {
            	System.out.println("Retrieve Exception :" +e1);
                return false;
            }
        }
        else if(string_code.startsWith("1008"))//LD
        {
            if(recForm.getAccno()>0)
            {
                double amt=recForm.getAmount();
                double max_amt = 0.0;
                
                try
                {
                    max_amt = recDelegate.getMaxPayableAmtonCurrentDay(module_obj_array[Integer.parseInt(recForm.getAccounttype())].getModuleCode(),recForm.getAccno(),recDelegate.getSysDate());
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    return false;
                }
                
                if(amt > max_amt)
                {
                    
                    System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
                    return false;
                }
            }
        }
        else if(string_code.startsWith("1010"))//LN
        {
            if(recForm.getAccno()>0)
            {
                double amt = recForm.getAmount();
                double max_amt = 0.0;
                
                try
                {
                    max_amt = recDelegate.checkLNAmount(module_obj_array[Integer.parseInt(recForm.getAccounttype())].getModuleCode(),recForm.getAccno(),recDelegate.getSysDate(),user,tml);
                    
                    if(amt > max_amt)
                    {
                        
                      System.out.println("Paid amt is more than amount to pay. Amt to pay-->"+max_amt);
                        return false;
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        else if(string_code.startsWith("1016"))//PO
        {
        	System.out.println("************PO_verification**********");
        	double amt=recForm.getPoamount();
            
            if(Double.parseDouble(recForm.getTotal())<amt)
            {
                System.out.println("Invalid Total Amount");
                return false;
            }
            
            try
            {               
                if(amt>module_obj_array[Integer.parseInt(recForm.getAccounttype())].getMaxAmount())
                {
                    
                	System.out.println("PayOrder Amount cannot be more than "+module_obj_array[Integer.parseInt(recForm.getAccounttype())].getMaxAmount());
                    return false;
                }
                else if(amt<module_obj_array[Integer.parseInt(recForm.getAccounttype())].getMinBal())
                {
                   
                    System.out.println("PayOrder Amount cannot be less than "+module_obj_array[Integer.parseInt(recForm.getAccounttype())].getMinBal());
                    return false;
                }
            }
            catch(Exception exception_pay_amount)
            {
                exception_pay_amount.printStackTrace();
                return false;
            }
        }
        else
        {
            double amt = 0.00,minamt = 0.00;
            
            try
            {
                amt = recForm.getPoamount();
                
                if(amt<minamt)
                {         
                    
                  System.out.println("Min Amount should be Rs. "+minamt+"");
                    return false;
                }
            }
            catch(Exception e1)
            {
            	System.out.println("Retrieve Exception :" +e1);
                return false;
            }
        }
    }
    else
    {
       
    	System.out.println("Enter the Valid Amount");
        return false;
    }
    
    
}catch (Exception e) {
	e.printStackTrace();
}
		return true;
}

public void UVScrollNos(CashObject[] array_cashobject,Recieiptsform recForm,CashDelegate recDelegate,HttpServletRequest request)
{	try{
   int scr_no = 0;
    int txt_scr_no = 0;
    NumberFormat numberformat=NumberFormat.getNumberInstance();
	numberformat.setMaximumFractionDigits(2); 
	numberformat.setMinimumFractionDigits(2);
	numberformat.setGroupingUsed(false);
	
	masterObject.general.AccountObject accObj = null;		
   
    for(int i=0;i<array_cashobject.length;i++)
    { 
        scr_no = array_cashobject[i].getScrollno();
        txt_scr_no = recForm.getScrollno();
        
        if(scr_no==(txt_scr_no))
        {
        	
        	request.setAttribute("accno",String.valueOf(cashobject.getAccno()));
			
			if(recForm.getAccno()==0 && !cashobject.getAcctype().startsWith("1016"))
				recForm.setNewlabel("New Account");//sow
			else
				recForm.setNewlabel("");//sow
			
			for(int j=0;j<module_obj_array.length;j++)
			{
				
				if(String.valueOf(module_obj_array[j].getModuleCode()).equals(cashobject.getAcctype()))
				{
				  
					request.setAttribute("setmodule",module_obj_array[j].getModuleAbbrv());
					request.setAttribute("setindex", String.valueOf(j));
					request.setAttribute("moduledesc",module_obj_array[j].getModuleDesc());
				}
			}
			
			try
			{
			    if(Integer.parseInt(cashobject.getAccno())>0)
			        accObj = recDelegate.getAccount(null,cashobject.getAcctype(),Integer.parseInt(cashobject.getAccno()),recDelegate.getSysDate());
			        
			    double tml_runbal = recDelegate.getCashTmlRunningBalance(array_cashobject[i].getTerminalNo(),recDelegate.getSysDate());
				request.setAttribute("tmlrunnable",String.valueOf(tml_runbal));
				
			}
			catch(Exception ex)
			{
			    ex.printStackTrace();
			}
		
			if(cashobject.getAcctype().startsWith("1016")) //PayOrder
			{
			    String po_desc = cashobject.getCustCode();
			    System.out.println("inside PO Verify....");
			    System.out.println("cust code..."+po_desc);
			    System.out.println("po cust acc type....."+cashobject.getCustAcctype());
			    
			    
			    
			    if(po_desc.startsWith("1") || po_desc.startsWith("2"))
			    {
                   
			        request.setAttribute("custtype","Customer");
			        for(int k=0;k<module_obj_array1.length;k++)
					{
						if(String.valueOf(module_obj_array1[k].getModuleCode()).equals(cashobject.getCustAcctype()))
						{
							request.setAttribute("setcustmodule",module_obj_array1[k].getModuleAbbrv());
							request.setAttribute("setcustindex", String.valueOf(k));
							request.setAttribute("pomoduledesc",module_obj_array1[k].getModuleDesc());
						}
						
					}
			        request.setAttribute("custacno",String.valueOf(cashobject.getCustAccno()));
			      
			        System.out.println("customer account number*****"+cashobject.getCustAccno());
			        request.setAttribute("custacname",cashobject.getAccname());
			        
			    }
			    else if(po_desc.startsWith("3"))
			    {
                    							
					
			    	request.setAttribute("custtype","Non Customer");
			    	
			        //textarea_address.setText(cashobject.getAccname());
			    }
			    request.setAttribute("favour",cashobject.getPOName());
			    request.setAttribute("commamt",String.valueOf(cashobject.getCommamt()));
			    request.setAttribute("totalamt",String.valueOf(cashobject.getAmount()));
			    request.setAttribute("poamt", String.valueOf((cashobject.getAmount()-cashobject.getCommamt())));
				
			}
			else if(cashobject.getAcctype().startsWith("1009")) //Lockers
			{
			  
				
				try {
					lock_types=recDelegate.getLockersTypes();
				
				for(int l=0;l<lock_types.length;l++)
				{
					
					if(lock_types[l].equals(cashobject.getLockertype())){
				       request.setAttribute("lokertype",lock_desc[l]);
				       System.out.println("locker type from cash action"+lock_desc[l]);
					}
				}
				
				
				//recForm.setAccname(cashobject.getAccname());
				request.setAttribute("accname",cashobject.getAccname());
				
				//recForm.setAmount((cashobject.getAmount()));
				request.setAttribute("amountvalue",String.valueOf(cashobject.getAmount()));
			
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			//sowmya...shares
			else if(cashobject.getAcctype().startsWith("1001"))//shares
			{
			    
				System.out.println("am i here in shares?");
				
				for(int m=0;m<sharecategoryobject.length;m++)
				{
					System.out.println("share category ......from share object"+sharecategoryobject[m].getShCat()+"oooo"+cashobject.getSharecat());
					if(sharecategoryobject[m].getShCat()==cashobject.getSharecat())
						request.setAttribute("setshare", sharecategoryobject[m].getCatName());
						request.setAttribute("setsharindex", String.valueOf(m));
						request.setAttribute("sharecategory",null);
						System.out.println("share index==="+String.valueOf(m)+"share category=="+sharecategoryobject[m].getCatName());
				}
				
				
				request.setAttribute("accname",cashobject.getAccname());
				request.setAttribute("amountvalue",String.valueOf(cashobject.getAmount()));
			}
			//
			else
			{
				
				CashObject co=new CashObject();
				
				co=recDelegate.getData(recForm.getScrollno(),0, recDelegate.getSysDate());
				getCurrency1(co,recForm);
				System.out.println("cash object from delegate"+cashobject.getScrollno());
				request.setAttribute("accname",cashobject.getAccname());
				request.setAttribute("amountvalue",String.valueOf(cashobject.getAmount()));
				
				
				
				System.out.println("  amt in else"+cashobject.getAmount()+"llll"+cashobject.getAccno()+"jjjjj"+cashobject.getAccname());
			}
			
			
			
			int cid =0;
			
				if(accObj!=null){
					
					cid =accObj.getCid();
				System.out.println("THE CID IIS "+cid);
	     	  
				try {
					request.setAttribute("personalInfo",recDelegate.getCustomerdetails(cid));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
			 
		if( Integer.parseInt(recForm.getValue().trim())==2)
		{
			if(cashobject.uv.getVerTml()==null )
			{
                //sowmya
               //recForm.setNewscrverify("updateverify"); 
               System.out.println("Scroll No in Use .. Cant Update or verify");
             
			}  ///////////
			
			else
			{
				recForm.setNewscrverify("already");
				System.out.println("This Scroll No is already verified");
				
			}
		}
            
            //sow
			
			update_amt = recForm.getAmount();
            System.out.println("Before Updation amt == "+update_amt);
            ///////////
        }
        }
    }catch (Exception e) {
		e.printStackTrace();
	}
}

//sowmya..........14/05/2008
public void MiscellUVScrollNos(MiscellaneousForm msForm,CashDelegate msDelegate,HttpServletRequest request)
{
	try
	{
		cashobject=new CashObject();
		cashobject=msDelegate.getData(msForm.getScroll_no(),0,msDelegate.getSysDate());
		System.out.println("i am inside missellaneous form .... un verify scroll no");
		if(cashobject!=null && cashobject.getVchtype()!=null)
		{
			if(cashobject.uv.getVerId()!=null)
			{
				msForm.setMsClosed("recordverify");
				System.out.println("Record Already Verified");
				
			}
			else if(cashobject.uv.getVerId()==null)
			{
			try
			{
				double tml_runbal = msDelegate.getCashTmlRunningBalance(tml,msDelegate.getSysDate());
				System.out.println("Terminal Running Balance Rs.******* "+tml_runbal);
				request.setAttribute("unverifyrunnable",String.valueOf(tml_runbal));
				
			} catch(Exception ex)
				    {
				        ex.printStackTrace();
				    }		    
					///////////
			
				    //sowmya...............14/05/2008
				    try
				    {
				        System.out.println("Un Verified scroll nos ..... Terminal = "+cashobject.getTerminalNo());
				        
				        double tml_runbal = msDelegate.getCashTmlRunningBalance(cashobject.getTerminalNo(),msDelegate.getSysDate());
				        System.out.println("Terminal Running Balance Rs. "+tml_runbal);
				        System.out.println("Hiiiiii i want to fetch data");
				    }
				    catch(Exception ex)
				    {
				        ex.printStackTrace();
				    }		    
				
				request.setAttribute("amount",String.valueOf(cashobject.getAmount()));
				request.setAttribute("accname",cashobject.getAccname());
				request.setAttribute("totalamt",String.valueOf(msForm.getAmount()));
				
			
				System.out.println("vch no in focuslost"+cashobject.getVchno());
			
				voucher_number_currency=cashobject.getVchno();
				voucher_type_currency=cashobject.getVchtype();		
				
			    VoucherDataObject[] voucherDataDetails;
				
				voucherDataDetails=msDelegate.getVoucherDetails(cashobject.getVchno(),cashobject.getVchtype(),msDelegate.getSysDate());
			
			
					request.setAttribute("vchdataobject",voucherDataDetails);
	
					System.out.println("The Voucher---------"+voucherDataDetails);
					Double TranAmt=0.0;	
					for(int i=0;i<voucherDataDetails.length;i++)
					{
						TranAmt+=voucherDataDetails[i].getTransactionAmount();
					}
				msForm.setTotal(TranAmt);
				System.out.println("THe msform.Total-------"+TranAmt);
			
			
				System.out.println("...536...");
				update_amt = msForm.getAmount();
                System.out.println("Before Updation amt == "+update_amt);
              
			}
		}
		else
		{
           msForm.setMsClosed("invalid");
			System.out.println("Invalid Scroll No");  
           //msForm.setScroll_no(0);
		}
	}
	catch(RecordsNotFoundException ex)
	{
		 System.out.println(ex);
	}
	catch(Exception exception)
	{
		exception.printStackTrace();
	}
 
}
////////

CashObject storeCurrency1(ExchangeForm exForm,int type)
{
   co=new CashObject(); 
   if(type==1)
    {
       System.out.println("in storecurrency... type 1=======>"+exForm.getR_fifty());
       
       	co.setR1000((exForm.getR_thousand()>0)?(exForm.getR_thousand()):0);
        co.setR500((exForm.getR_fivered()>0)?(exForm.getR_fivered()):0);
        co.setR100((exForm.getR_hundred()>0)?(exForm.getR_hundred()):0);
        co.setR50((exForm.getR_fifty()>0)?(exForm.getR_fifty()):0);
        co.setR20((exForm.getR_twenty()>0)?(exForm.getR_twenty()):0);
        co.setR10((exForm.getR_ten()>0)?(exForm.getR_ten()):0);
        co.setR5((exForm.getR_five()>0)?(exForm.getR_five()):0);
        co.setR2((exForm.getR_two()>0)?(exForm.getR_two()):0);
        co.setR1((exForm.getR_one()>0)?(exForm.getR_one()):0);
        co.setRcoins((exForm.getR_coins()>0)?(exForm.getR_coins()):0);
        System.out.println("in storecurrency... type 2p=======>"+exForm.getP_hundred());
        co.setP1000((exForm.getP_thousand()>0)?(exForm.getP_thousand()):0);
        co.setP500((exForm.getP_fivered()>0)?(exForm.getP_fivered()):0);
        co.setP100((exForm.getP_hundred()>0)?(exForm.getP_hundred()):0);
        co.setP50((exForm.getP_fifty()>0)?(exForm.getP_fifty()):0);
        co.setP20((exForm.getP_twenty()>0)?(exForm.getP_twenty()):0);
        co.setP10((exForm.getP_ten()>0)?(exForm.getP_ten()):0);
        co.setP5((exForm.getP_five()>0)?(exForm.getP_five()):0);
        co.setP2((exForm.getP_two()>0)?(exForm.getP_two()):0);
        co.setP1((exForm.getP_one()>0)?(exForm.getP_one()):0);
        co.setPcoins((exForm.getP_coins()>0)?(exForm.getP_coins()):0);
        System.out.println("in storecurrency... type 1");
    }
    else if(type==2)
    {
      System.out.println("in storecurrency... type 2");
        co.setR1000((exForm.getP_thousand()>0)?(exForm.getP_thousand()):0);
        co.setR500((exForm.getP_fivered()>0)?(exForm.getP_fivered()):0);
        co.setR100((exForm.getP_hundred()>0)?(exForm.getP_hundred()):0);
        co.setR50((exForm.getP_fifty()>0)?(exForm.getP_fifty()):0);
        co.setR20((exForm.getP_twenty()>0)?(exForm.getP_twenty()):0);
        co.setR10((exForm.getP_ten()>0)?(exForm.getP_ten()):0);
        co.setR5((exForm.getP_five()>0)?(exForm.getP_five()):0);
        co.setR2((exForm.getP_two()>0)?(exForm.getP_two()):0);
        co.setR1((exForm.getP_one()>0)?(exForm.getP_one()):0);
        co.setRcoins((exForm.getP_coins()>0)?(exForm.getP_coins()):0);
        
        co.setP1000((exForm.getR_thousand()>0)?(exForm.getR_thousand()):0);
        co.setP500((exForm.getR_fivered()>0)?(exForm.getR_fivered()):0);
        co.setP100((exForm.getR_hundred()>0)?(exForm.getR_hundred()):0);
        co.setP50((exForm.getR_fifty()>0)?(exForm.getR_fifty()):0);
        co.setP20((exForm.getR_twenty()>0)?(exForm.getR_twenty()):0);
        co.setP10((exForm.getR_ten()>0)?(exForm.getR_ten()):0);
        co.setP5((exForm.getR_five()>0)?(exForm.getR_five()):0);
        co.setP2((exForm.getR_two()>0)?(exForm.getR_two()):0);
        co.setP1((exForm.getR_one()>0)?(exForm.getR_one()):0);
        co.setPcoins((exForm.getR_coins()>0)?(exForm.getR_coins()):0);
        System.out.println("in storecurrency... type 2");
       
    }
return co;
}




//*************cash closing method******************
public void closeMainCashier(CashDelegate clDelegate,CurrencyStockForm closeForm)
{
    boolean close = false;
    int flag1 = 0,sc__no = 0;
    
    int proceed = 0;
    boolean continue_transaction = false;
    
    try
    {
        proceed = clDelegate.checkDailyStatus(clDelegate.getSysDate(),0);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    
   System.out.println("proceed = "+proceed);
    
    if(proceed==3)//day close
    {
    	System.out.println("Day closed\n You can't do any Transactions");
    	closeForm.setValidation("dayclose");
        continue_transaction = false;
       
    }
    else if(proceed==1)//cash close
    {
    	System.out.println("Cash closed\n You can't do any Transactions");
    	closeForm.setValidation("cashclose");
        continue_transaction = false;
     
    }
    else if(proceed==-1)
    {
        continue_transaction = false;
        System.out.println("Error : no entry in Daily status for today");
        closeForm.setValidation("noentry");
    }
    else
        continue_transaction = true;
    
    if(continue_transaction==true)
    {
     
       if(closeForm.getClbutton().equalsIgnoreCase("true"))
            {
                try
                {
                   System.out.println("inside try..");
                    currencystockobject=new CurrencyStockObject();
                    currencystockobject=clDelegate.getCurrencyStockObject(tml,clDelegate.getSysDate(),0);
                    
                    co = new CashObject(); 
                        
                    co.setP1000(0);
                    co.setP500(0);
                    co.setP100(0);
                    co.setP50(0);
                    co.setP20(0);
                    co.setP10(0);
                    co.setP5(0);
                    co.setP2(0);
                    co.setP1(0);
                    co.setPcoins(0.0);
                    
                    co.setR1000(currencystockobject.gets1000());
                    co.setR500(currencystockobject.gets500());
                    co.setR100(currencystockobject.gets100());
                    co.setR50(currencystockobject.gets50());
                    co.setR20(currencystockobject.gets20());
                    co.setR10(currencystockobject.gets10());
                    co.setR5(currencystockobject.gets5());
                    co.setR2(currencystockobject.gets2());
                    co.setR1(currencystockobject.gets1());
                    co.setRcoins(currencystockobject.getscoins());
                    
                    co.setAmount(Double.parseDouble(currencystockobject.getNetamt()));
                    
                    
                    co.setTrndate(clDelegate.getSysDate());
                    co.setTrntime(clDelegate.getSysTime());
                    /////////
                     flag1 = clDelegate.checkReceiptPending(tml,clDelegate.getSysDate());
                    System.out.println("flag,,,,<<<<<<"+flag1);
                    if(flag1==0)
                    {
                       
                        flag1 = clDelegate.checkPaymentPending(tml,clDelegate.getSysDate());
                        System.out.println("flag,...>>>>>>>>>>>>>>"+flag1);
                        if(flag1==0)
                        {
                            close = clDelegate.checkClosingBalance(tml,clDelegate.getSysDate());
                            
                            if(close==true)
                            {
                                sc__no = clDelegate.closeCash(tml,clDelegate.getSysDate());
                                System.out.println("sc_no....***********************"+ sc__no);
                                if(sc__no==0)
                                {
                                	System.out.println("Cash Closed");
                                	closeForm.setValidation("closed");
                                    
                                }
                                else if(sc__no==1)
                                {
                                	System.out.println("Cash already Closed");
                                	closeForm.setValidation("already");
                                    
                                }
                                
                                else if(sc__no==-1)
                                {
                                	System.out.println("No Entry in Curr Stock");
                                	closeForm.setValidation("noentryincurrency");
                                    
                                }
                                /////////
                            }
                            else
                            {
                            	System.out.println("Cannot Close\n Mismatch of Closing Balance b/w CurrStk & GLTran");
                            	closeForm.setValidation("mismatch");
                            		if(closeForm.getClbutton().equalsIgnoreCase("true"))
                                   
                                    {
                                        sc__no = clDelegate.closeCash(tml,clDelegate.getSysDate());
                                        System.out.println("sc_no....%%%%%%%%%%%"+ sc__no);
                                        if(sc__no==0)
                                        {
                                        	 System.out.println("Cash Closed");
                                        	 closeForm.setValidation("closed");
                                        }
                                        else if(sc__no==1)
                                        {
                                        	 System.out.println("Cash already Closed");
                                        	 closeForm.setValidation("already");
                                            
                                        }
                                    }
                              
                            }
                        }
                        }
                        else if(flag1==1)
                        {
                            System.out.println("Cannot Close the Cash\n Close all the sub cashier tmls");
                            closeForm.setValidation("closesubcash");
                          
                        }
                        else if(flag1==2)
                        {
                        	 System.out.println("Cannot Close the Cash\n some payments are pending");
                        	 closeForm.setValidation("somepayment");
                           
                        }
                    
                    else if(flag1==1) 
                    {
                    	 System.out.println("Error: Existing A/c Rec. Ver. is Pending");
                    	 closeForm.setValidation("existacpending");
                        
                    }
                    else if(flag1==2)
                    {
                    	 System.out.println("Error: New A/c Rec. Ver. is Pending");
                    	 closeForm.setValidation("newacpending");
                        
                    }
                    else if(flag1==3)
                    {
                    	 System.out.println("Error: Misc. Rec. Ver. is Pending");
                    	 closeForm.setValidation("miscrecacpending");
                        
                    }
                    ////////////
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
     
    }
    /////////
}
/**
 * This methos is used to check wheather all necessary values are entered or not. If the user not
 * entered it will display message. Otherwise it will return 1.
 * 
 */
public int getCheck(MiscellaneousForm msForm)
{
	System.out.println("inside Total amount" +msForm.getTotal());
	if(msForm.getBut_value().equalsIgnoreCase("Update"))
	{
	if(msForm.getScroll_no()==0)
		{
			System.out.println("Enter 0 For New Account or Valid Scroll No For Updation");		
			msForm.setMsClosed("validscroll");
			return 0;
		}
	}
	/*if(msForm.getBut_value().equalsIgnoreCase("Submit"))
	{
	if(msForm.getScroll_no()==0)
		{
			System.out.println("Enter 0 For New Account or Valid Scroll No For Updation");
			msForm.setMsClosed("fornewacc");
			return 1;
		}
	}*/
   
		if(msForm.getNarration().equals("")) 
		{
			System.out.println("Enter textarea_narration");
			msForm.setMsClosed("narration");
			return 0;
		}
  
		if(msForm.getAmount()==0)
		{
			System.out.println("Enter Amount");
			msForm.setMsClosed("enteramt");
			return 0;
		}
		
   
  /* if(msForm.getTotal()==0)
   {
	   System.out.println("No Entries in Table");
	   msForm.setMsClosed("noentries");
       return 0;
   }*/
   
   return 1;
	}

//currency exchange method
public void getCurrency(CashObject co,ExchangeForm exForm)
{
  
        exForm.setR_thousand(co.getR1000());
        exForm.setR_fivered(co.getR500());
        exForm.setR_hundred(co.getR100());
        exForm.setR_fifty(co.getR50());
        exForm.setR_twenty(co.getR20());
        exForm.setR_ten(co.getR10());
        exForm.setR_five(co.getR5());
        exForm.setR_two(co.getR2());
        exForm.setR_one(co.getR1());
        NumberFormat  nf=NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2); 
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);
        exForm.setR_coins(co.getRcoins());
        exForm.setP_thousand(co.getP1000());
        exForm.setP_fivered(co.getP500());
        exForm.setP_hundred(co.getP100());
        exForm.setP_fifty(co.getP50());
        exForm.setP_twenty(co.getP20());
        exForm.setP_ten(co.getP10());
        exForm.setP_five(co.getP5());
        exForm.setP_two(co.getP2());
        exForm.setP_one(co.getP1());
        
        exForm.setP_coins(co.getPcoins());
}

public void getCurrency1(CashObject co,Recieiptsform recform)
{
  
	recform.setR_thousand(co.getP1000());
	recform.setR_fivered(co.getP500());
	recform.setR_hundred(co.getP100());
	recform.setR_fifty(co.getP50());
	recform.setR_twenty(co.getP20());
	recform.setR_ten(co.getP10());
	recform.setR_five(co.getP5());
	recform.setR_two(co.getP2());
	recform.setR_one(co.getP1());
        NumberFormat  nf=NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2); 
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);
        recform.setR_coins(co.getPcoins());
        recform.setP_thousand(co.getR1000());
        recform.setP_fivered(co.getR500());
        recform.setP_hundred(co.getR100());
        recform.setP_fifty(co.getR50());
        recform.setP_twenty(co.getR20());
        recform.setP_ten(co.getR10());
        recform.setP_five(co.getR5());
        recform.setP_two(co.getR2());
        recform.setP_one(co.getR1());
        
        recform.setP_coins(co.getRcoins());
}
	/*private List setMiscParam(CashDelegate csh,HttpServletRequest req){
		List addrow=new ArrayList();
		Map map1=new TreeMap();
					
		map1.put("gltype",msForm.getGl_type());
					map1.put("glcode",msForm.getGl_code());
					map1.put("glname",msForm.getGl_name());
					map1.put("amount",msForm.getAmount());
					addrow.add(map1);
	}*/
public static String doubleToString(double value,int scale){
	try {
		BigDecimal dec = new BigDecimal(value);
		return (dec.movePointRight(scale+1).setScale(1,0).movePointLeft(3).setScale(scale,1).toString());
	} catch (Exception exe) {
		return "0";
	}
}

void receiptcurrency(CashObject co,Recieiptsform recform,int type)
	{
   
	if(type==1)
    {
       System.out.println("in storecurrency... type 1r=======>"+recform.getR_fifty());
       
       	co.setR1000((recform.getR_thousand()>0)?(recform.getR_thousand()):0);
        co.setR500((recform.getR_fivered()>0)?(recform.getR_fivered()):0);
        co.setR100((recform.getR_hundred()>0)?(recform.getR_hundred()):0);
        co.setR50((recform.getR_fifty()>0)?(recform.getR_fifty()):0);
        co.setR20((recform.getR_twenty()>0)?(recform.getR_twenty()):0);
        co.setR10((recform.getR_ten()>0)?(recform.getR_ten()):0);
        co.setR5((recform.getR_five()>0)?(recform.getR_five()):0);
        co.setR2((recform.getR_two()>0)?(recform.getR_two()):0);
        co.setR1((recform.getR_one()>0)?(recform.getR_one()):0);
        co.setRcoins((recform.getR_coins()>0)?(recform.getR_coins()):0);
        System.out.println("in storecurrency... type 2p=======>"+recform.getP_hundred());
        co.setP1000((recform.getP_thousand()>0)?(recform.getP_thousand()):0);
        co.setP500((recform.getP_fivered()>0)?(recform.getP_fivered()):0);
        co.setP100((recform.getP_hundred()>0)?(recform.getP_hundred()):0);
        co.setP50((recform.getP_fifty()>0)?(recform.getP_fifty()):0);
        co.setP20((recform.getP_twenty()>0)?(recform.getP_twenty()):0);
        co.setP10((recform.getP_ten()>0)?(recform.getP_ten()):0);
        co.setP5((recform.getP_five()>0)?(recform.getP_five()):0);
        co.setP2((recform.getP_two()>0)?(recform.getP_two()):0);
        co.setP1((recform.getP_one()>0)?(recform.getP_one()):0);
        co.setPcoins((recform.getP_coins()>0)?(recform.getP_coins()):0);
        System.out.println("in storecurrency... type 1r");
    }
    else if(type==2)
    {
      System.out.println("in storecurrency... type 2");
        co.setR1000((recform.getP_thousand()>0)?(recform.getP_thousand()):0);
        co.setR500((recform.getP_fivered()>0)?(recform.getP_fivered()):0);
        co.setR100((recform.getP_hundred()>0)?(recform.getP_hundred()):0);
        co.setR50((recform.getP_fifty()>0)?(recform.getP_fifty()):0);
        co.setR20((recform.getP_twenty()>0)?(recform.getP_twenty()):0);
        co.setR10((recform.getP_ten()>0)?(recform.getP_ten()):0);
        co.setR5((recform.getP_five()>0)?(recform.getP_five()):0);
        co.setR2((recform.getP_two()>0)?(recform.getP_two()):0);
        co.setR1((recform.getP_one()>0)?(recform.getP_one()):0);
        co.setRcoins((recform.getP_coins()>0)?(recform.getP_coins()):0);
        
        co.setP1000((recform.getR_thousand()>0)?(recform.getR_thousand()):0);
        co.setP500((recform.getR_fivered()>0)?(recform.getR_fivered()):0);
        co.setP100((recform.getR_hundred()>0)?(recform.getR_hundred()):0);
        co.setP50((recform.getR_fifty()>0)?(recform.getR_fifty()):0);
        co.setP20((recform.getR_twenty()>0)?(recform.getR_twenty()):0);
        co.setP10((recform.getR_ten()>0)?(recform.getR_ten()):0);
        co.setP5((recform.getR_five()>0)?(recform.getR_five()):0);
        co.setP2((recform.getR_two()>0)?(recform.getR_two()):0);
        co.setP1((recform.getR_one()>0)?(recform.getR_one()):0);
        co.setPcoins((recform.getR_coins()>0)?(recform.getR_coins()):0);
        System.out.println("in storecurrency... type 2");
       
    }

}


void paymentcurrency(PaymentForm payForm,CashObject co,int type)
{
   co=new CashObject(); 
   if(type==1)
    {
       System.out.println("in storecurrency... type 1=======>"+payForm.getR_fifty());
       
       	co.setR1000((payForm.getR_thousand()>0)?(payForm.getR_thousand()):0);
        co.setR500((payForm.getR_fivered()>0)?(payForm.getR_fivered()):0);
        co.setR100((payForm.getR_hundred()>0)?(payForm.getR_hundred()):0);
        co.setR50((payForm.getR_fifty()>0)?(payForm.getR_fifty()):0);
        co.setR20((payForm.getR_twenty()>0)?(payForm.getR_twenty()):0);
        co.setR10((payForm.getR_ten()>0)?(payForm.getR_ten()):0);
        co.setR5((payForm.getR_five()>0)?(payForm.getR_five()):0);
        co.setR2((payForm.getR_two()>0)?(payForm.getR_two()):0);
        co.setR1((payForm.getR_one()>0)?(payForm.getR_one()):0);
        co.setRcoins((payForm.getR_coins()>0)?(payForm.getR_coins()):0);
        System.out.println("in storecurrency... type 2p=======>"+payForm.getP_hundred());
        co.setP1000((payForm.getP_thousand()>0)?(payForm.getP_thousand()):0);
        co.setP500((payForm.getP_fivered()>0)?(payForm.getP_fivered()):0);
        co.setP100((payForm.getP_hundred()>0)?(payForm.getP_hundred()):0);
        co.setP50((payForm.getP_fifty()>0)?(payForm.getP_fifty()):0);
        co.setP20((payForm.getP_twenty()>0)?(payForm.getP_twenty()):0);
        co.setP10((payForm.getP_ten()>0)?(payForm.getP_ten()):0);
        co.setP5((payForm.getP_five()>0)?(payForm.getP_five()):0);
        co.setP2((payForm.getP_two()>0)?(payForm.getP_two()):0);
        co.setP1((payForm.getP_one()>0)?(payForm.getP_one()):0);
        co.setPcoins((payForm.getP_coins()>0)?(payForm.getP_coins()):0);
        System.out.println("in storecurrency... type 1");
    }
    else if(type==2)
    {
      System.out.println("in storecurrency... type 2");
        co.setR1000((payForm.getP_thousand()>0)?(payForm.getP_thousand()):0);
        co.setR500((payForm.getP_fivered()>0)?(payForm.getP_fivered()):0);
        co.setR100((payForm.getP_hundred()>0)?(payForm.getP_hundred()):0);
        co.setR50((payForm.getP_fifty()>0)?(payForm.getP_fifty()):0);
        co.setR20((payForm.getP_twenty()>0)?(payForm.getP_twenty()):0);
        co.setR10((payForm.getP_ten()>0)?(payForm.getP_ten()):0);
        co.setR5((payForm.getP_five()>0)?(payForm.getP_five()):0);
        co.setR2((payForm.getP_two()>0)?(payForm.getP_two()):0);
        co.setR1((payForm.getP_one()>0)?(payForm.getP_one()):0);
        co.setRcoins((payForm.getP_coins()>0)?(payForm.getP_coins()):0);
        
        co.setP1000((payForm.getR_thousand()>0)?(payForm.getR_thousand()):0);
        co.setP500((payForm.getR_fivered()>0)?(payForm.getR_fivered()):0);
        co.setP100((payForm.getR_hundred()>0)?(payForm.getR_hundred()):0);
        co.setP50((payForm.getR_fifty()>0)?(payForm.getR_fifty()):0);
        co.setP20((payForm.getR_twenty()>0)?(payForm.getR_twenty()):0);
        co.setP10((payForm.getR_ten()>0)?(payForm.getR_ten()):0);
        co.setP5((payForm.getR_five()>0)?(payForm.getR_five()):0);
        co.setP2((payForm.getR_two()>0)?(payForm.getR_two()):0);
        co.setP1((payForm.getR_one()>0)?(payForm.getR_one()):0);
        co.setPcoins((payForm.getR_coins()>0)?(payForm.getR_coins()):0);
        System.out.println("in storecurrency... type 2");
       
    }

}
}

