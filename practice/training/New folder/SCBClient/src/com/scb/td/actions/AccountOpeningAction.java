package com.scb.td.actions;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.termDeposit.DepositIntRepObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositReportObject;
import masterObject.termDeposit.DepositTransactionObject;

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
import com.scb.td.forms.AutoRenewal;
import com.scb.td.forms.AutoRenewalList;
import com.scb.td.forms.DepositRenewal;
import com.scb.td.forms.DepositViewLog;
import com.scb.td.forms.InterestAccruedReport;
import com.scb.td.forms.InterestCalc;
import com.scb.td.forms.Ledger;
import com.scb.td.forms.MaturityLedgerReport;
import com.scb.td.forms.OpenClosedAccounts;
import com.scb.td.forms.PeriodwiseReport;
import com.scb.td.forms.QuarterlyInterest;
import com.scb.td.forms.QueryOnReceipt;
import com.scb.td.forms.ReceiptUpdation;
import com.scb.td.forms.TDClosure;
import com.scb.td.forms.TDPassbook;
import com.scb.td.forms.VoucherPayment;

import exceptions.RecordsNotFoundException;
import general.Validations;

public class AccountOpeningAction extends Action {
	private TDDelegate tddelegate;
	String path;
	DepositMasterObject dep_sub = new DepositMasterObject();
	DepositMasterObject dep_close;
	CustomerMasterObject cust_obj;
	TDDelegate tDDelaggate;
	java.text.NumberFormat numberFormat = java.text.NumberFormat.getNumberInstance();
	final Logger logger = LogDetails.getInstance().getLoggerObject("AccountOpeningAction");
	private boolean int_change = false;
	double depint[] = { 0, 0, 0 };
	double double_extra_int_amount = 0, double_int_amount = 0;
	String string_today_date = "";
	String string_renewal_date = "";
	String Mdate;
	AdministratorDelegate admDelegate;
	Map user_role;
	String user, tml;
	String IntFreq = null;
	ModuleObject array_moduleobject_acctype[] = null,
			array_moduleobject_transfer[] = null;
	private HttpSession session = null, newsession;

	public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)
			throws RecordsNotFoundException, ArrayIndexOutOfBoundsException {	
		newsession = req.getSession();
		user = (String) newsession.getAttribute("UserName");
		tml = (String) newsession.getAttribute("UserTml");
		try {
			synchronized (req) {
				if (user != null) {
					admDelegate = new AdministratorDelegate();
					user_role = admDelegate.getUserLoginAccessRights(user, "TD");
					if (user_role != null) {
						req.setAttribute("user_role", user_role);
						if (req.getParameter("pageId") != null) {						
							req.setAttribute("accesspageId", req.getParameter(
									"pageId").trim());
						}
					} else {
						path = MenuNameReader.getScreenProperties("0000");
						setErrorPageElements(
								"Sorry, You do not have access to this page",
								req, path);
						return map.findForward(ResultHelp.getError());
					}
				}
				FrontCounterDelegate fcDelegatenew;
				fcDelegatenew = new FrontCounterDelegate();
				req.setAttribute("acccat", fcDelegatenew.getAccCategories());
				req.setAttribute("accsubcat", fcDelegatenew.getSubCategories());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (map.getPath().equalsIgnoreCase("/TermDeposit/AccountOpeningMenu")) {
			try {			
				ApplnDE acc_de = (ApplnDE) form;
				String pageId = acc_de.getPageId();
				req.setAttribute("pagename", pageId);
				req.setAttribute("Verifyvalue", 1);
				req.setAttribute("getfocus", "ac_type");
				System.out.println("happy new year 2008!!!:)");
				acc_de.setValidate(0);

				if (MenuNameReader.containsKeyScreen(acc_de.getPageId())) {
					path = MenuNameReader.getScreenProperties(acc_de.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside menu name reader");
					setInitParam(req, path, tddelegate);
					System.out.println("path===" + path);
					req.setAttribute("pageId", path);
					System.out.println("geetha changed here the path is "+ path);
					System.out.println("ACTION CLASS::  value---------------->"+ acc_de.getValue());
					return map.findForward(ResultHelp.getSuccess());
				}
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req, path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, req, path);
				return map.findForward(ResultHelp.getError());
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AccountOpening")) {
			DepositMasterObject dep_open_obj = new DepositMasterObject();
			try {
				AccountObject acc_object = new AccountObject();
				int instal = 0;
				TDDelegate tddelegate = new TDDelegate();
				ApplnDE acc_de = (ApplnDE) form;
				String pageId = acc_de.getPageId();
				req.setAttribute("pagename", pageId);
				path = MenuNameReader.getScreenProperties(acc_de.getPageId());
				acc_de.setValidate(0);
				double paybl = 0;
				req.setAttribute("Verifyvalue", 1);
				setInitParam(req, path, tddelegate);
				
				if (acc_de.getAc_no() > 0) {
					try {
						DepositMasterObject depmasterverify_val = null;
						depmasterverify_val = tddelegate.getDepositMasterValues(acc_de.getAc_no(),acc_de.getAc_type());
						if (depmasterverify_val == null) {
							acc_de.setTesting("Account Not Found");
						} else if (depmasterverify_val.getCloseInd() != 0&& depmasterverify_val.getCloseInd() != 12) {
							acc_de.setTesting("Account Already Closed");
						} else if (depmasterverify_val.getCloseInd() == 12) {
							acc_de.setTesting("Renewed Account ! You con't OPEN");
						} else if (depmasterverify_val.getVerified().equals("T")) {
							acc_de.setTesting("Account Number Already Verified");
						}else {
							if (depmasterverify_val != null) {
								acc_de.setInt_rate(depmasterverify_val.getInterestRate());
							}
							AccountObject accobject=null;
							accobject = tddelegate.getAcccountDetails(depmasterverify_val.getReceivedAccType(),null, depmasterverify_val.getReceivedAccno(), TDDelegate.getSysdate());
							if (accobject != null) {
								acc_de.setBalance_amt(accobject.getAmount());
							}
							setPayableAmount(tddelegate, acc_de, req, depmasterverify_val);
							acc_de.setAmount(depmasterverify_val.getDepositAmt());
							String TrnCustName = tddelegate.getCustmerName(depmasterverify_val.getTransferAccType(),depmasterverify_val.getTransferAccno());
							if (TrnCustName != null) {
								acc_de.setLabel_name(TrnCustName);
							}
							if (depmasterverify_val != null) {
								if (depmasterverify_val.getVerified().equalsIgnoreCase("F")) {
									req.setAttribute("Verifyvalue", 2);
								}
								req.setAttribute("verify_values",depmasterverify_val);
							}
							if (acc_de.getDetails().equalsIgnoreCase("Personal")){
								String perpath=MenuNameReader.getScreenProperties("0001");
								CustomerMasterObject cmobj1=null;
								cmobj1 = tddelegate.getCustomer(depmasterverify_val.getCustomerId());
								if(cmobj1!=null){
									req.setAttribute("personalInfo", cmobj1);
									req.setAttribute("flag", perpath);
									req.setAttribute("panelName", CommonPanelHeading.getPersonal());
								}else{
									req.setAttribute("msg", "Personal details are not available");
								}
							} else if (acc_de.getDetails().equals("JointHolders")) {
								session=req.getSession();
								req.setAttribute("enable1", "enable1");
								req.setAttribute("jointnum", depmasterverify_val.getNoofJtHldrs());
								acc_de.setPrimejoint(depmasterverify_val.getCustomerId());
								acc_de.setJointnum(depmasterverify_val.getNoofJtHldrs());
								int[] cids=depmasterverify_val.getJointCid();
								CustomerMasterObject cmObj1=null;
								for(int i=0;i<cids.length;i++){
									cmObj1=tddelegate.getCustomer(cids[i]);
									session.setAttribute("custnames"+i, cmObj1);
								}
							} else if (acc_de.getDetails().equals("Nominee")) {
								session=req.getSession();
								req.setAttribute("enable", "enable");
								NomineeObject[] nomineeObjects=depmasterverify_val.getNomineeDetails();
								if(nomineeObjects!=null){
									session.setAttribute("Nominee", nomineeObjects);
								}else{
									req.setAttribute("msg", "No Nominee Details are available!!!");
								}
							}else if (acc_de.getDetails().equalsIgnoreCase("SignatureDetails")) {
								if (depmasterverify_val.getCustomerId() > 0) {
									SignatureInstructionObject[] signObj=null;
									signObj = tddelegate.getComRemote().getSignatureDetails(depmasterverify_val.getCustomerId());
									if (signObj == null) {
										req.setAttribute("msg", "No SignatureDetails available!!!");
									} else {
										req.setAttribute("flag",MenuNameReader.getScreenProperties("13030"));
										req.setAttribute("sigObject", signObj);
									}
								}
							}else if (acc_de.getDetails().equals("Introducer")) {
								String perpath=MenuNameReader.getScreenProperties("0001");
								CustomerMasterObject custObj=null;
								custObj=tddelegate.getCustomer(depmasterverify_val.getCustomerId());
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
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(acc_de.getAc_no()==0){
					req.setAttribute("pageId", path);
					req.setAttribute("msgnew", "New Account");
					if (acc_de.getCid() > 0) {
						System.out.println("I am in side CID Gretaer than 0");
						String perpath = MenuNameReader.getScreenProperties("0001");
						int max_cid=0;
						CustomerMasterObject cmobj = null;
						max_cid = tddelegate.validate(0, null, 0);
						if (acc_de.getCid() > max_cid) {
							acc_de.setTesting("CID Not Found.The Max Cid In Data Base is:"+ max_cid + ".");
							acc_de.setValidate(0);
							req.setAttribute("flag", perpath);
						}else {
							cmobj = tddelegate.getCustomer(acc_de.getCid());
							if (cmobj == null) {
								acc_de.setTesting("CID Not Found");
							}
							req.setAttribute("personalInfo", tddelegate.getCustomer(acc_de.getCid()));
							req.setAttribute("flag", perpath);
							req.setAttribute("panelName", CommonPanelHeading.getPersonal());
						}
					}
				
					if(acc_de.getDep_amt()!=0){
						ModuleObject[] mod1 = new ModuleObject[1];
						mod1 = tddelegate.getModuleMethods(acc_de.getAc_type());
						if (mod1[0]!=null && acc_de.getDep_amt() <= mod1[0].getMinBal()) {
							acc_de.setTesting("Deposit Amount should be Greater than "+ mod1[0].getMinBal() + "");
						}
					}
				}
				if (acc_de.getCid() > 0 && acc_de.getPeriod_of_days()> 0 && !acc_de.getForward().equalsIgnoreCase("submit")) {
					System.out.println("This is Second---------->");
					if (acc_de.getDep_amt() > 100) {
						int no_of_days = acc_de.getPeriod_of_days();
						String dep_date = acc_de.getDep_date();
						String mat_date = acc_de.getMat_date();
						String ac_type = acc_de.getAc_type();
						int dep_amt = acc_de.getDep_amt();
						int cid = acc_de.getCid();
						setInterestRate(tddelegate, acc_de, req);
						double array_double_deposit_interest[] = { 0, 0, 0 };
						cust_obj = tddelegate.getCustomer(acc_de.getCid());
						array_double_deposit_interest = tddelegate.getDepositInterestRate(ac_type, cust_obj.getCategory(), cust_obj.getSubCategory(),Validations.convertYMD(dep_date),Validations.dayCompare(dep_date, mat_date),Double.parseDouble(String.valueOf(dep_amt)));
						if (array_double_deposit_interest != null) {
							req.setAttribute("Cat_type",array_double_deposit_interest);
						}
						double[] int_rate = tddelegate.setInterestRate(ac_type, dep_date, mat_date, no_of_days,	dep_amt, cid);
						acc_de.setInt_rate(int_rate[0]);
						if (acc_de.getAc_type().equalsIgnoreCase("1003001")|| acc_de.getAc_type().equalsIgnoreCase("1003002")&& acc_de.getInterest_freq() != null) {
							if(mat_date!=null)
								paybl = tddelegate.setPayableAmt(acc_de.getInterest_freq(), dep_date, mat_date,dep_amt, acc_de.getInt_rate());
								
								acc_de.setInt_payable(String.valueOf(paybl));
						}else if (acc_de.getAc_type().equalsIgnoreCase("1004001")) {
							instal = acc_de.getPeriod_of_days() / 30;
							String mat_dateDate = tddelegate.getFutureMonthDate(acc_de.getDep_date(),instal);
							String intfreq = "Q";
							if(mat_dateDate!=null)
							paybl = tddelegate.setPayableAmt(intfreq, dep_date, mat_dateDate, dep_amt, acc_de.getInt_rate());
							acc_de.setInt_payable(String.valueOf(paybl));
						}else {
							String intfreq = "Q";
							if(acc_de.getMat_date()!=null)
							paybl = tddelegate.setPayableAmt(intfreq, acc_de.getDep_date(), acc_de.getMat_date(),acc_de.getDep_amt(), acc_de.getInt_rate());
							acc_de.setInt_payable(String.valueOf(paybl));
						}
						
						AccountObject accobject12=null;
						if (acc_de.getTrf_acno() != 0) {
							accobject12 = tddelegate.getAcccountDetails(acc_de.getTrf_actype(), null, acc_de.getTrf_acno(), TDDelegate.getSysdate());
							if (accobject12 == null) {
								System.out.println("Transfer Ac_no");
								acc_de.setTesting("Transfer-Account Not Found");
							} else if (accobject12.getAccStatus().equals("C")) {
								acc_de.setTesting("Account Closed");
							} else if (accobject12.getVerified() == null) {
								acc_de.setTesting("Account Not Yet verified");
							} else if (accobject12.getDefaultInd().equals("T")) {
								acc_de.setTesting("Defaulted Account");
							} else if (accobject12.getFreezeInd().equals("T")) {
								acc_de.setTesting("Account Freezed");
							}
							if (accobject12 != null) {
								System.out.println("CLosing Balanceeeeee-> "+ accobject12.getAmount());
								acc_de.setBalance_amt(accobject12.getAmount());
							}
						}
						if (acc_de.getScroll_no() > 0) {
							System.out.println("I am inside Scroll Number Greater than Zero " + acc_de.getScroll_no());
							double double_scroll_amount = 0.0;
							AccountObject accountobject = tddelegate.getAcccountDetails(acc_de.getAc_type(),"C", acc_de.getScroll_no(),TDDelegate.getSysdate());
							if (accountobject == null) {
								acc_de.setTesting("Scroll Number Not Found");
							} else if (accountobject.getAccStatus().equals("C")&& (!(accountobject.getAccno() == acc_de.getAc_no()))) {
								acc_de.setTesting("Given scroll number already attached ");
							} else if (!(accountobject.getAcctype().equalsIgnoreCase(acc_de.getAc_type()))) {
								acc_de.setTesting("Scroll Number is Not for this Account");
							} else {
								System.out.println("AMOUNT-----+++ " + accountobject.getAmount());
								double_scroll_amount = accountobject.getAmount();
								if (acc_de.getDep_amt() == 0) {
									acc_de.setTesting(" Enter Deposit Amount");
								}else {
									acc_de.setLabel_name(accountobject.getAccname());
									acc_de.setAmount(double_scroll_amount);
									acc_de.setDate(TDDelegate.getSysdate());
								}
							}
						}else if (acc_de.getControl_no() > 0) {
							double double_control_amount = 0.0;
							AccountObject accountObject = tddelegate.getAcccountDetails(acc_de.getAc_type(),"G", acc_de.getControl_no(),TDDelegate.getSysdate());
							if (accountObject == null) {
								acc_de.setTesting("Control Number Not Found");
							} else if (accountObject.getVerified() == null) {
								acc_de.setTesting("Control Number Not Yet Verified");
							} else if (accountObject.getAccStatus().equals("F")) {
								acc_de.setTesting("Control Number Not Yet Dispatched");
							} else if (accountObject.getPostInd().equals("T")) {
								acc_de.setTesting("Control Number Already Used");
							} else if (!(accountObject.getAcctype().equalsIgnoreCase(acc_de.getAc_type()) && accountObject.getAccno() == 0)) {
								acc_de.setTesting("Control Number is Not for this Account");
							} else {
								double_control_amount = accountObject.getAmount();
								if (acc_de.getDep_amt() == 0) {
									acc_de.setTesting(" Enter Deposit Amount");
								}else if (!(acc_de.getDep_amt() == double_control_amount)) {
									acc_de.setTesting(" Deposit Amount Not Equal To Control Number Amount");
								}else {
									acc_de.setLabel_name(accountObject.getAccname());
									acc_de.setAmount(double_control_amount);
									acc_de.setDate(accountObject.getQdp_date());
								}
							}
						}
						
						if (acc_de.getCombo_mat_cat() != null) {
							if (acc_de.getCombo_mat_cat().equalsIgnoreCase("Category")|| acc_de.getCombo_mat_cat().equalsIgnoreCase("1")) {
								System.out.println("Category----");
								acc_de.setInt_rate(array_double_deposit_interest[0]+ array_double_deposit_interest[1]);
							} else if (acc_de.getCombo_mat_cat().equalsIgnoreCase("sub")|| acc_de.getCombo_mat_cat().equalsIgnoreCase("2")) {
								System.out.println("SUB-----");
								double sub = (array_double_deposit_interest[0] + array_double_deposit_interest[2]);
								System.out.println("Doubbbbbbdldlkdk----> "	+ sub);
								acc_de.setInt_rate(sub);
							} else if (acc_de.getCombo_mat_cat().equalsIgnoreCase("BOTH")|| acc_de.getCombo_mat_cat().equalsIgnoreCase("3")) {
								System.out.println("BOTH-------");
								acc_de.setInt_rate(array_double_deposit_interest[0]+ array_double_deposit_interest[1]+ array_double_deposit_interest[2]);
							}
						}
						
						req.setAttribute("matdate", tddelegate.getmat_date(acc_de.getAc_no(), acc_de.getPeriod_of_days()));
						System.out.println("verify value afetr refreshing----->>>"+ acc_de.getValue());
						req.setAttribute("Verifyvalue", 1);
						if (acc_de.getIntro_ac_no()!=null && acc_de.getIntro_ac_no().trim().length() > 0) {
							System.out.println("Hey Mite i am in Introducer >0 method---> "	+ acc_de.getIntro_ac_no());
							try {
								acc_object = tddelegate.getAcccountDetails(acc_de.getIntro_ac_type(), null,Integer.parseInt(acc_de.getIntro_ac_no()), TDDelegate.getSysdate());
								if (acc_object == null) {
									req.setAttribute("msg","Account holder is not A Valid Introducer");
									System.out.println("I am nulllllllllllllllllllllllllll");
								} else if (acc_object.getAccStatus().equals("C")) {
									req.setAttribute("msg", "Account Closed");
								} else if (acc_object.getVerified() == null) {
									req.setAttribute("msg","Account Not Yet verified");
								} else if (acc_object.getDefaultInd().equals("T")) {
									req.setAttribute("msg","Defaulted Account");
								} else if (acc_object.getFreezeInd().equals("T")) {
									req.setAttribute("msg","Account is Freezed");
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						if (acc_de.getDetails().equalsIgnoreCase("Personal")){
							String perpath=MenuNameReader.getScreenProperties("0001");
							CustomerMasterObject cmobj1=null;
							cmobj1 = tddelegate.getCustomer(acc_de.getCid());
							if(cmobj1!=null){
								req.setAttribute("personalInfo", cmobj1);
								req.setAttribute("flag", perpath);
								req.setAttribute("panelName", CommonPanelHeading.getPersonal());
							}else{
								acc_de.setTesting("Personal details are not available");
							}
						}else if (acc_de.getDetails().equals("SignatureDetails")) {
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
						}else if (acc_de.getDetails().equals("Introducer")&& acc_de.getIntro_ac_no().trim().length()!=0) {
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
						}else if (acc_de.getDetails().equals("Nominee")) {
							req.setAttribute("enable", "enable");
							CustomerMasterObject cust_obj=null;
							session=req.getSession();
							session.setAttribute("Nominee", null);
							if(acc_de.getHas_ac()!=null){
							if(!acc_de.getHas_ac().equalsIgnoreCase("SELECT") && acc_de.getHas_ac().equalsIgnoreCase("yes")){
								req.setAttribute("showcid", acc_de.getHas_ac());
								if(acc_de.getForward().equalsIgnoreCase("Cidis")){
								if(acc_de.getCidis()!=null && acc_de.getCidis().trim().length()!=0){
									cust_obj=tddelegate.getCustomer(Integer.parseInt(acc_de.getCidis().trim()));
									if(cust_obj!=null){
										acc_de.setNomname(cust_obj.getName());
										acc_de.setDob(cust_obj.getDOB());
										acc_de.setGender(cust_obj.getSex());
										HashMap hashMap=cust_obj.getAddress();
										Collection collection=hashMap.values();
										Iterator itr=collection.iterator();
										Address address=null;
										while(itr.hasNext()){
											address=(Address)itr.next();
										}
										acc_de.setAddress(address.getAddress());
									}else{
										req.setAttribute("msg", "Details are not available for this Customer");
									}
								}else{
									req.setAttribute("msg", "Please enter the Customer ID");
								}
								}
							}
							}
							if(acc_de.getRel_ship()!=null && acc_de.getPercentage()!=null && acc_de.getPercentage().trim().equals("100")){
								NomineeObject[] nomObj=new NomineeObject[1];
								nomObj[0]=new NomineeObject();
								if(acc_de.getCidis()!=null && acc_de.getCidis().trim().length()!=0)
									nomObj[0].setCustomerId(Integer.parseInt(acc_de.getCidis()));
								else
									nomObj[0].setCustomerId(0);
								nomObj[0].setNomineeName(acc_de.getNomname());
								nomObj[0].setNomineeDOB(acc_de.getDob());
								nomObj[0].setSex(Integer.parseInt(acc_de.getGender()));
								nomObj[0].setNomineeAddress(acc_de.getAddress());
								nomObj[0].setNomineeRelation(acc_de.getRel_ship());
								nomObj[0].setPercentage(Double.parseDouble(acc_de.getPercentage()));
								session.setAttribute("Nominee", nomObj);
							}
						}else if (acc_de.getDetails().equals("JointHolders")) {
							req.setAttribute("enable1", "enable1");
							session=req.getSession();
							acc_de.setPrimejoint(acc_de.getCid());
							int jointnum=0;
							CustomerMasterObject cmObj1=null;
							req.setAttribute("jointnum", jointnum);
							if(acc_de.getJointnum()!=0){
								jointnum=acc_de.getJointnum();
								req.setAttribute("jointnum", jointnum);
								if(acc_de.getForward().equalsIgnoreCase("JCid")){
									
									if(acc_de.getJcid0()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid0());
										if(cmObj1!=null){
											session.setAttribute("custnames0", cmObj1);
										}
									}
									if(acc_de.getJcid1()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid1());
										if(cmObj1!=null){
											session.setAttribute("custnames1", cmObj1);
										}
									}
									if(acc_de.getJcid2()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid2());
										if(cmObj1!=null){
											session.setAttribute("custnames2", cmObj1);
										}
									}
									if(acc_de.getJcid3()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid3());
										if(cmObj1!=null){
											session.setAttribute("custnames3", cmObj1);
										}
									}
									if(acc_de.getJcid4()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid4());
										if(cmObj1!=null){
											session.setAttribute("custnames4", cmObj1);
										}
									}
									if(acc_de.getJcid5()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid5());
										if(cmObj1!=null){
											session.setAttribute("custnames5", cmObj1);
										}
									}
									if(acc_de.getJcid6()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid6());
										if(cmObj1!=null){
											session.setAttribute("custnames6", cmObj1);
										}
									}
									if(acc_de.getJcid7()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid7());
										if(cmObj1!=null){
											session.setAttribute("custnames7", cmObj1);
										}
									}
									if(acc_de.getJcid8()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid8());
										if(cmObj1!=null){
											session.setAttribute("custnames8", cmObj1);
										}
									}
									if(acc_de.getJcid9()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid9());
										if(cmObj1!=null){
											session.setAttribute("custnames9", cmObj1);
										}
									}
									if(acc_de.getJcid10()!=0){
										cmObj1=tddelegate.getCustomer(acc_de.getJcid10());
										if(cmObj1!=null){
											session.setAttribute("custnames10", cmObj1);
										}
									}
								}
							}
						}
						
						if (acc_de.getPay_mode_ac_no() > 0) {
							try {
								acc_object = tddelegate.getAcccountDetails(acc_de.getPay_ac_type(), null, acc_de.getPay_mode_ac_no(),TDDelegate.getSysdate());
								if (acc_object == null) {
									acc_de.setTesting("Paymode-Account Not Found");
								} else if (acc_object.getAccStatus().equals("C")) {
									acc_de.setTesting("Account Closed");
								} else if (acc_object.getVerified() == null) {
									acc_de.setTesting("Account Not Yet verified");
								} else if (acc_object.getDefaultInd().equals("T")) {
									acc_de.setTesting("Defaulted Account");
								} else if (acc_object.getFreezeInd().equals("T")) {
									acc_de.setTesting("Account Freezed");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						if (int_rate[0] != 0) {
							int_change = true;
							// acc_de.setInt_payable(numberFormat.format(array_double_deposit_interest[0]+array_double_deposit_interest[1]+array_double_deposit_interest[2]));
							if (acc_de.getAc_type().equalsIgnoreCase("1004001")) {
								acc_de.setInt_rate(int_rate[0]);
								double maturityAM = (((Validations.rdInterestCalculation(Double.parseDouble(String.valueOf(acc_de.getDep_amt())),(instal),(acc_de.getInt_rate()), acc_de.getDep_date(), acc_de.getMat_date()))));
								req.setAttribute("maturityamt", maturityAM);
							}
							if (acc_de.getAc_type().equalsIgnoreCase("1003001")) {
								acc_de.setInt_rate(int_rate[0]);
								acc_de.setMat_amt(acc_de.getDep_amt());
							}
							else if (acc_de.getAc_type().startsWith("1005")) {
								acc_de.setInt_rate(int_rate[0]);
								acc_de.setMat_amt(((tddelegate.reinvestmentCalc(Double.parseDouble(String.valueOf(acc_de.getDep_amt())),acc_de.getDep_date(),tddelegate.getmat_date(acc_de.getAc_no(),acc_de.getPeriod_of_days()),acc_de.getInt_rate()))));
							}
						}
						acc_de.setInt_rate(int_rate[0]);
						req.setAttribute("interestrate", int_rate);
						if (acc_de.getAmt_recv().equalsIgnoreCase("Transfer")) {
							if (acc_de.getTrf_acno() != 0) {
								acc_object = tddelegate.getAcccountDetails(acc_de.getTrf_actype(), acc_de.getPay_mode(), acc_de.getTrf_acno(), acc_de.getDate());
								if (acc_object == null) {
									req.setAttribute("Accountnotfound","Account Not Found");
								} else {
									acc_de.setLabel_name(acc_object.getAccname());
									acc_de.setAmount(acc_de.getDep_amt());
									req.setAttribute("balance", acc_object);
								}
							}
						}
						return map.findForward(ResultHelp.getSuccess());
					}
				}
				if(acc_de.getForward()!=null)
				{
				if (acc_de.getForward().equalsIgnoreCase("submit")) {
					if (acc_de.getDep_amt() > acc_de.getBalance_amt()) {
						acc_de.setTesting("Deposit amount is Greater than balance");
					}
					dep_open_obj.setAccType(acc_de.getAc_type());
					dep_open_obj.setAccNo(acc_de.getAc_no());
					dep_open_obj.setCustomerId(acc_de.getCid());
					dep_open_obj.setMailingAddress(Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getCid()),0));
					dep_open_obj.setNoofJtHldrs(acc_de.getJointnum());
					dep_open_obj.setMaturityDate(acc_de.getMat_date());
					dep_open_obj.setDepDate(acc_de.getDep_date());
					dep_open_obj.setDepositAmt(acc_de.getDep_amt());
					dep_open_obj.setDepositDays(acc_de.getPeriod_of_days());
					// dep_open_obj.setPeriod_in_days(acc_de.getPeriod_of_days());
					dep_open_obj.setMaturityAmt(acc_de.getMat_amt());
					dep_open_obj.setInterestRate(acc_de.getInt_rate());
					// dep_open_obj.setExtraRateType(acc_de.getExtra_type());
					dep_open_obj.setExtraRateType(Integer.parseInt(acc_de.getCombo_mat_cat()));
					System.out.println("Combo category---> "+ acc_de.getCombo_mat_cat());
					dep_open_obj.userverifier.setUserId(user);
					dep_open_obj.userverifier.setUserTml(tml);
					dep_open_obj.userverifier.setUserDate(TDDelegate.getSysdate()+ TDDelegate.getSysTime());
					dep_open_obj.setTranDate(TDDelegate.getSysdate());
					if (acc_de.getAc_type().equalsIgnoreCase("1003001")|| acc_de.getAc_type().equalsIgnoreCase("1003002")) {
						dep_open_obj.setInterestFrq(acc_de.getInterest_freq());
					} else if (acc_de.getAc_type().equalsIgnoreCase("1004001")|| acc_de.getAc_type().startsWith("1005")) {
						dep_open_obj.setInterestFrq(String.valueOf(acc_de.getIntFREQ()));
					}
					dep_open_obj.setAutoRenewal(acc_de.getCombo_autorenewal());
					dep_open_obj.setIntroAccType(acc_de.getIntro_ac_type());
					dep_open_obj.setIntroAccno(Integer.parseInt(acc_de.getIntro_ac_no()));
					SignatureInstructionObject[] signObj=null;
					signObj = tddelegate.getComRemote().getSignatureDetails(acc_de.getCid());
					if(signObj!=null){
						for(int i=0;i<signObj.length;i++)
							signObj[i].setSAccType(acc_de.getAc_type());
						dep_open_obj.setSigObj(signObj);
					}
					int jointnum=acc_de.getJointnum();
					int[] ids=new int[jointnum];
					int[] addrs=new int[jointnum];
					if(acc_de.getJcid0()!=0){
						ids[0]=acc_de.getJcid0();
						addrs[0]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid0()),0);
					}
					if(acc_de.getJcid1()!=0){
						ids[1]=acc_de.getJcid1();
						addrs[1]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid1()),0);
					}
					if(acc_de.getJcid2()!=0){
						ids[2]=acc_de.getJcid2();
						addrs[2]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid2()),0);
					}
					if(acc_de.getJcid3()!=0){
						ids[3]=acc_de.getJcid3();
						addrs[3]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid3()),0);
					}
					if(acc_de.getJcid4()!=0){
						ids[4]=acc_de.getJcid4();
						addrs[4]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid4()),0);
					}
					if(acc_de.getJcid5()!=0){
						ids[5]=acc_de.getJcid5();
						addrs[5]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid5()),0);
					}
					if(acc_de.getJcid6()!=0){
						ids[6]=acc_de.getJcid6();
						addrs[6]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid6()),0);
					}
					if(acc_de.getJcid7()!=0){
						ids[7]=acc_de.getJcid7();
						addrs[7]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid7()),0);
					}
					if(acc_de.getJcid8()!=0){
						ids[8]=acc_de.getJcid8();
						addrs[8]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid8()),0);
					}
					if(acc_de.getJcid9()!=0){
						ids[9]=acc_de.getJcid9();
						addrs[9]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid9()),0);
					}
					if(acc_de.getJcid10()!=0){
						ids[10]=acc_de.getJcid10();
						addrs[10]=Array.getInt(tddelegate.getCustomerRemote().getCustomerAddrType(acc_de.getJcid10()),0);
					}
					dep_open_obj.setJointCid(ids);
					dep_open_obj.setJointAddrType(addrs);
					if(session.getAttribute("Nominee")!=null)
						dep_open_obj.setNomineeDetails((NomineeObject[])session.getAttribute("Nominee"));
					
					if (acc_de.getPay_mode().equalsIgnoreCase("Transfer")) {
						dep_open_obj.setReceivedBy("T");
						dep_open_obj.setTransferAccno(acc_de.getPay_mode_ac_no());
						dep_open_obj.setTransferAccType(acc_de.getPay_ac_type());
						dep_open_obj.setInterestMode(acc_de.getPay_mode());
					}
					if (acc_de.getPay_mode().equalsIgnoreCase("Cash")) {
						dep_open_obj.setReceivedBy("C");
						dep_open_obj.setInterestMode(acc_de.getPay_mode());
					}
					if (acc_de.getPay_mode().equalsIgnoreCase("Clearing")) {
						dep_open_obj.setReceivedBy("G");
						dep_open_obj.setTransferAccno(acc_de.getControl_no());
						dep_open_obj.setInterestMode(acc_de.getPay_mode());
					}
					if (acc_de.getAmt_recv().equalsIgnoreCase("Transfer")) {
						dep_open_obj.setReceivedBy("T");
						dep_open_obj.setReceivedAccType(acc_de.getTrf_actype());
						dep_open_obj.setReceivedAccno(acc_de.getTrf_acno());
					}
					System.out.println("Amount Rev---2--> "+ acc_de.getAmt_recv());
					if (acc_de.getAmt_recv().equalsIgnoreCase("Cash")) {
						dep_open_obj.setReceivedBy("C");
						dep_open_obj.setReceivedAccno(acc_de.getScroll_no());
						dep_open_obj.setString_scroll_date(acc_de.getDate().trim());
					}
					if (acc_de.getAmt_recv().equalsIgnoreCase("Q/DD/PO")) {
						dep_open_obj.setReceivedBy("G");
						dep_open_obj.setReceivedAccno(acc_de.getControl_no());
						dep_open_obj.setString_scroll_date(acc_de.getDate().trim());
						dep_open_obj.setReceivedAccno(acc_de.getControl_no());
					}
					System.out.println("=====Before Storing into Database=====");
					int ac_value = tddelegate.storeDeposit(dep_open_obj, acc_de.getAc_type(), acc_de.getAc_no(), 0);
					System.out.println("AC---type====> " + acc_de.getAc_type());
					req.setAttribute("ACTyp", acc_de.getAc_type());
					req.setAttribute("shnum", ac_value);
					if (ac_value != 0) {
						acc_de.setValidate(ac_value);
					} else {
						acc_de.setValidate(0);
					}
				}else if (acc_de.getForward().equalsIgnoreCase("TVerify")) {
					System.out.println("ACTION CLASS::::INSIDE VERIFICATION");
					ModuleObject[] mod1 = new ModuleObject[1];
					mod1 = tddelegate.getModuleMethods(acc_de.getAc_type());
					DepositMasterObject dep_verify = new DepositMasterObject();
					dep_verify.userverifier.setVerId(user);
					dep_verify.userverifier.setVerTml(tml);
					dep_verify.userverifier.setVerDate(TDDelegate.getSysdate()+ TDDelegate.getSysTime());
					dep_verify.setDPType(1);
					if (dep_verify.getReceivedBy().equalsIgnoreCase("Transfer")) {
						dep_verify.setReceivedBy("T");
					}
					dep_verify.setGLRefCode(Integer.parseInt(mod1[0].getModuleCode()));
					tddelegate.delegateverify(dep_verify);
					acc_de.setTesting("Account Number Sucessfully Verified ");
					}
				else if (acc_de.getForward().equalsIgnoreCase("Verify")) {
					int result = 0;
					ModuleObject[] mod1 = new ModuleObject[1];
					mod1 = tddelegate.getModuleMethods(acc_de.getAc_type());
					acc_object = tddelegate.getAcccountDetails(acc_de.getTrf_actype(), acc_de.getPay_mode(), acc_de.getTrf_acno(), acc_de.getDate());
					dep_open_obj = tddelegate.getDepositMasterValues(acc_de.getAc_no(), acc_de.getAc_type());
					dep_open_obj.userverifier.setVerId(user);
					dep_open_obj.userverifier.setVerTml(tml);
					dep_open_obj.userverifier.setVerDate(TDDelegate.getSysdate()+ TDDelegate.getSysTime());
					dep_open_obj.userverifier.setUserId(user);
					dep_open_obj.userverifier.setUserTml(tml);
					dep_open_obj.userverifier.setUserDate(TDDelegate.getSysdate()+ TDDelegate.getSysTime());
					dep_open_obj.setDPType(1);
					if (acc_de.getAmt_recv().equalsIgnoreCase("T")) {
						dep_open_obj.setReceivedBy("T");
					}
					if (dep_open_obj != null) {
						result = tddelegate.delegateverify(dep_open_obj);
					}
					if (result == 1) {
						req.setAttribute("verified","Acccount Successfully Verified");
					} else {
						acc_de.setTesting("Unable to verify");
						req.setAttribute("verified", "Unable to verify");
					}
					/*req.setAttribute("pageId", MenuNameReader.getScreenProperties(acc_de.getPageId()));
					return map.findForward(ResultHelp.getSuccess());*/
				}else if (acc_de.getForward().equalsIgnoreCase("delete")) {
					DepositMasterObject dep_delete = new DepositMasterObject();
					int delete = tddelegate.deleteDeposit(acc_de.getAc_no(),acc_de.getAc_type());
					dep_delete.setAccType(acc_de.getAc_type());
					dep_delete.setAccNo(acc_de.getAc_no());
					// dep_delete.setCustomerId((acc_de.getCid());
					dep_delete.setMaturityDate(acc_de.getMat_date());
					dep_delete.setDepDate(acc_de.getDep_date());
					dep_delete.setDepositAmt(acc_de.getDep_amt());
					dep_delete.setDepositDays(acc_de.getPeriod_of_days());
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, req, path);
				return map.findForward(ResultHelp.getError());
			}
			return map.findForward(ResultHelp.getSuccess());
		}
		if (map.getPath().equalsIgnoreCase("/TermDeposit/AmmendmentsMenu")) {
			try {

				ApplnDE acc_de = (ApplnDE) form;
				String pageId = acc_de.getPageId();
				req.setAttribute("pagename", pageId);
				// String acc_type=acc_de.getAc_type();
				// req.setAttribute("getfocus","ac_type");
				System.out.println("happy new year 2008!!!:)");

				if (MenuNameReader.containsKeyScreen(acc_de.getPageId())) {

					path = MenuNameReader.getScreenProperties(acc_de.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside menu name reader");
					setInitParam(req, path, tddelegate);

					req.setAttribute("pageId", path);
					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, req, path);
				return map.findForward(ResultHelp.getError());
			}
		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/Ammendments")) {
			try {

				DepositMasterObject dep_ammendment = new DepositMasterObject();
				ApplnDE acc_de = (ApplnDE) form;
				TDDelegate tddelegate = new TDDelegate();
				AccountObject accountOBJ;
				double array_double_deposit_interest[] = { 0, 0, 0 };
				String intfrequency = (String) req.getParameter("freqint");
			
				if (intfrequency.equalsIgnoreCase("inrfreq")) {
					if (acc_de.getAc_no() > 0) {
						int days = 0;
						double matamount = 0.0;
						int total_days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), acc_de.getMat_date());
						days = total_days;
						if (acc_de.getInterest_freq().startsWith("M")) {
							double paybl = 0;
							if(acc_de.getMat_date()!=null)
							paybl = tddelegate.setPayableAmt(acc_de.getInterest_freq(), acc_de.getDep_date(),acc_de.getMat_date(), acc_de.getDep_amttwo(), acc_de.getInt_rate());
							String to_date = tddelegate.getFutureMonthDate(acc_de.getDep_date(), 1);
							days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), to_date);
							if (days <= total_days) {
								String Result = (numberFormat.format(new Double(Math.round((acc_de.getDep_amttwo())* (acc_de.getInt_rate())* days) / (36500))));
								acc_de.setInt_payable(Result);
							}

							matamount = tddelegate.setMaturityAmt(acc_de.getAc_type(), acc_de.getDep_amttwo(),acc_de.getDep_date(), acc_de.getMat_date(),acc_de.getInt_rate());

						} else if (acc_de.getInterest_freq().startsWith("Q")) {
							String to_date = tddelegate.getFutureMonthDate(	acc_de.getDep_date(), 3);
							days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), to_date);
							System.out.println("to_date in Qqq=====" + to_date);
							System.out.println("days in QQQ=====" + days);

							if (days <= total_days) {
								String Result = (numberFormat.format(new Double(Math.round((acc_de.getDep_amttwo())* (acc_de.getInt_rate())* days) / (36500))));
								acc_de.setInt_payable(Result);

							}
							matamount = tddelegate.setMaturityAmt(acc_de
									.getAc_type(), acc_de.getDep_amttwo(),
									acc_de.getDep_date(), acc_de.getMat_date(),
									acc_de.getInt_rate());

						} else if (acc_de.getInterest_freq().startsWith("H")) {
							String to_date = tddelegate.getFutureMonthDate(acc_de.getDep_date(), 6);
							days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), to_date);

							if (days <= total_days) {
								String Result = (numberFormat.format(new Double(Math.round((acc_de.getDep_amttwo())* (acc_de.getInt_rate())* days) / (36500))));
								acc_de.setInt_payable(Result);

							}
							matamount = tddelegate.setMaturityAmt(acc_de
									.getAc_type(), acc_de.getDep_amttwo(),
									acc_de.getDep_date(), acc_de.getMat_date(),
									acc_de.getInt_rate());
						} else if (acc_de.getInterest_freq().startsWith("Y")) {

							String to_date = tddelegate.getFutureMonthDate(acc_de.getDep_date(), 12);
							days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), to_date);
							if (days <= total_days) {
								String Result = (numberFormat.format(new Double(Math.round((acc_de.getDep_amttwo())* (acc_de.getInt_rate())	* days) / (36500))));
								acc_de.setInt_payable(Result);

							}
							matamount = tddelegate.setMaturityAmt(acc_de
									.getAc_type(), acc_de.getDep_amttwo(),
									acc_de.getDep_date(), acc_de.getMat_date(),
									acc_de.getInt_rate());
						} else if (acc_de.getInterest_freq().startsWith("O")) {
							days = tddelegate.getDaysFromTwoDate(acc_de.getDep_date(), acc_de.getMat_date());
							if (days <= total_days) {
								String Result = (numberFormat.format(new Double(Math.round((acc_de.getDep_amttwo())* (acc_de.getInt_rate())	* days) / (36500))));
								acc_de.setInt_payable(Result);

							}
							matamount = tddelegate.setMaturityAmt(acc_de
									.getAc_type(), acc_de.getDep_amttwo(),
									acc_de.getDep_date(), acc_de.getMat_date(),
									acc_de.getInt_rate());
						}
					} else {
						acc_de.setTesting("Please Enter Deposit Account Number");
					}
				}

				if (acc_de.getForward().equalsIgnoreCase("depositdays")) {
					System.out.println("i am in depositdays mite ");
					if (acc_de.getPeriod_of_daystwo() > 0) {

						setMaturityDate(tddelegate, acc_de, req);
						setInterestRate(tddelegate, acc_de, req);
						setPayableAmount(tddelegate, acc_de, req,dep_ammendment);
						acc_de.setPeriod_of_daystwo(acc_de.getPeriod_of_daystwo());
					}

				}
				if (acc_de.getForward().equalsIgnoreCase("introacno")) {
					if (acc_de.getIntro_ac_type() != null && (acc_de.getIntro_ac_no().length()) != 0) {
						accountOBJ = tddelegate.getAcccountDetails(acc_de.getIntro_ac_type(), null, Integer.parseInt(acc_de.getIntro_ac_no()), TDDelegate.getSysdate());
						if (accountOBJ == null) {
							acc_de.setTesting("Introducer-Account Not Found");
						} else {
							acc_de.setIntro_ac_no(acc_de.getIntro_ac_no());
						}
					}
				}
				if (acc_de.getForward().equalsIgnoreCase("payacno")) {
					if (acc_de.getPay_ac_typetwo() != null 	&& (acc_de.getPay_mode_ac_no()) != 0) {
						accountOBJ = tddelegate.getAcccountDetails(acc_de.getPay_ac_typetwo(), null, acc_de.getPay_mode_ac_no(), TDDelegate.getSysdate());
						if (accountOBJ == null) {
							acc_de.setTesting("Paymode-Account Not Found");
						} else {
							acc_de.setLabel_name(accountOBJ.getAccname());
							acc_de.setPay_mode_ac_no(acc_de.getPay_mode_ac_no());
						}
					}
				}

			
				if (acc_de.getFirst().equalsIgnoreCase("firsttime")) {

					if (acc_de.getAc_no() > 0) {
						dep_ammendment = tddelegate.getDepositMasterValues(	acc_de.getAc_no(), acc_de.getAc_type());

						
						if (dep_ammendment == null) {
							System.out.println("account not found sumanth");
							acc_de.setTesting("Deposit Number Not Found");
						} else if (dep_ammendment.getVerified().equalsIgnoreCase("F")) {

							acc_de.setTesting("Deposit Number Not Yet Verified");
						} else if (dep_ammendment.getCloseInd() != 0) {

							acc_de.setTesting("Account Already Closed");
						}

						else {

							if (dep_ammendment != null) {
								
								acc_de.setCust_name(dep_ammendment.getName());
								acc_de.setCid(dep_ammendment.getCustomerId());
								acc_de.setDep_amttwo(dep_ammendment.getDepositAmt());
								acc_de.setDep_date(dep_ammendment.getDepDate());
								acc_de.setPeriod_of_daystwo(dep_ammendment.getDepositDays());
								acc_de.setMat_date(dep_ammendment.getMaturityDate());
								acc_de.setIntro_ac_no(String.valueOf(dep_ammendment.getIntroAccno()));
								acc_de.setInt_rate(dep_ammendment.getInterestRate());
								acc_de.setMat_amt(dep_ammendment.getMaturityAmt());
								acc_de.setIntro_ac_type(dep_ammendment.getIntroAccType());
								acc_de.setPay_mode(dep_ammendment.getInterestMode());
								acc_de.setPay_ac_typetwo(dep_ammendment.getTransferType());
								acc_de.setPay_mode_ac_no(dep_ammendment.getTransferAccno());
								acc_de.setCombo_autorenewal(dep_ammendment.getAutoRenewal());
								acc_de.setCombo_mat_cat(String.valueOf(dep_ammendment.getExtraRateType()));
								acc_de.setInterest_freq(dep_ammendment.getInterestFrq());

								int daysamend = 0;

								int total_days = tddelegate.getDaysFromTwoDate(dep_ammendment.getDepDate(),	dep_ammendment.getMaturityDate());
								daysamend = total_days;

								double paybl = 0;
								if(dep_ammendment.getMaturityDate()!=null)
								paybl = tddelegate.setPayableAmt(dep_ammendment.getInterestFrq(), dep_ammendment.getDepDate(), dep_ammendment.getMaturityDate(), dep_ammendment
										.getDepositAmt(), dep_ammendment
										.getInterestRate());

								System.out.println("payaaaaaa---- > " + paybl);
								acc_de.setInt_payable(String.valueOf(paybl));
								
								System.out
										.println("@@@@@@@@@@@start of maturity amount@@@@");

								double matamountamend = tddelegate
										.setMaturityAmt(dep_ammendment.getAccType(), dep_ammendment.getDepositAmt(),
												dep_ammendment.getDepDate(),dep_ammendment.getMaturityDate(),dep_ammendment.getInterestRate());
								System.out.println("MAturiry amount-----> "+ matamountamend);

							}

							if (dep_ammendment.getAccType().equalsIgnoreCase("1003001")
									|| dep_ammendment.getAccType().equalsIgnoreCase("1004001")
									|| dep_ammendment.getAccType().startsWith("100500")) {

								cust_obj = tddelegate.getCustomer(dep_ammendment.getCustomerId());
								if (cust_obj != null) {
									System.out.println("The SubCategory code------> "+ cust_obj.getCategory());
									AccSubCategoryObject[] array_accsubcategoryobject = tddelegate
											.getAccSubCategories(cust_obj.getCategory());

									array_double_deposit_interest = tddelegate
											.getDepositInterestRate(
													dep_ammendment.getAccType(),
													cust_obj.getCategory(),
													cust_obj.getSubCategory(),
													Validations.convertYMD(dep_ammendment.getDepDate()),
													Validations.dayCompare(dep_ammendment.getDepDate(),	dep_ammendment.getMaturityDate()),dep_ammendment.getDepositAmt());
								}
								if (array_double_deposit_interest != null) {
									req.setAttribute("Cat_type",array_double_deposit_interest);
								}
							}
							acc_de.setFirst("");
						}
						if (acc_de.getDetails().equalsIgnoreCase("Personal"))

						{
							CustomerMasterObject custmast = null;
							System.out.println("21/MAr/2009 Sumanth is in personal------->");
							DepositMasterObject depmasterverify_val = new DepositMasterObject();
							depmasterverify_val = tddelegate.getDepositMasterValues(acc_de.getAc_no(),acc_de.getAc_type());
							if (depmasterverify_val == null) {
								acc_de.setTesting("Personal Details Not Found");
							}
							int cid = depmasterverify_val.getCustomerId();
							custmast = tddelegate.getCustomer(cid);
							String bdate = tddelegate.getBOD(cid);
							String sysdate = TDDelegate.getSysdate();

							StringTokenizer d = new StringTokenizer(bdate, "/");
							d.nextToken();
							d.nextToken();
							int yy = Integer.parseInt(d.nextToken());

							Calendar cal = Calendar.getInstance();
							int age = cal.get(Calendar.YEAR) - yy;
							System.out.println("Age is---------" + age);

							req.setAttribute("age", age);

						

							String custpersonal_add = tddelegate.getNomineeAddress(cid);
							req.setAttribute("disabled", "Yes");
							req.setAttribute("Address", custpersonal_add);
							req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
							req.setAttribute("personalInfo", custmast);
							req.setAttribute("panelName", CommonPanelHeading.getPersonal());

						}

					}


					acc_de.setFirst("");
				}

				if (acc_de.getAc_no() > 0) {
					dep_ammendment = tddelegate.getDepositMasterValues(acc_de.getAc_no(), acc_de.getAc_type());

					if (dep_ammendment == null) {
						acc_de.setTesting("Deposit Number Not Found");
					} else if (dep_ammendment.getVerified().equalsIgnoreCase("F")) {
						acc_de.setTesting("Deposit Number Not Yet Verified");
					} else if (dep_ammendment.getCloseInd() != 0) {
						acc_de.setTesting("Account Already Closed");
					} else {
						if (acc_de.getAc_type() != null) {
							if (acc_de.getAc_type().equalsIgnoreCase("1003001")
									|| acc_de.getAc_type().equalsIgnoreCase("1004001")
									|| acc_de.getAc_type().startsWith("100500")) {

								cust_obj = tddelegate.getCustomer(acc_de.getCid());
								if (cust_obj != null) {
									AccSubCategoryObject[] array_accsubcategoryobject = tddelegate.getAccSubCategories(cust_obj.getCategory());


									array_double_deposit_interest = tddelegate
											.getDepositInterestRate(acc_de.getAc_type(),cust_obj.getCategory(),cust_obj.getSubCategory(),
													Validations.convertYMD(acc_de.getDep_date()),
													Validations.dayCompare(acc_de.getDep_date(),acc_de.getMat_date()),acc_de.getDep_amttwo());
								}
								if (array_double_deposit_interest != null) {


									req.setAttribute("Cat_type",array_double_deposit_interest);
								}

							}
						}


						if (acc_de.getDetails() != null) {

							if (acc_de.getDetails().equalsIgnoreCase("Personal"))

							{
								CustomerMasterObject custmast = null;
								DepositMasterObject depmasterverify_val;
								System.out.println("21/MAr/2009 Sumanth is in personal------->");
								depmasterverify_val = tddelegate
										.getDepositMasterValues(acc_de.getAc_no(), acc_de.getAc_type());
								int cid = depmasterverify_val.getCustomerId();
								System.out.println("the cid is===> " + cid);
								custmast = tddelegate.getCustomer(cid);
								String bdate = tddelegate.getBOD(cid);
								String sysdate = tddelegate.getSysdate();

								StringTokenizer d = new StringTokenizer(bdate,"/");
								d.nextToken();
								d.nextToken();
								int yy = Integer.parseInt(d.nextToken());

								Calendar cal = Calendar.getInstance();
								int age = cal.get(Calendar.YEAR) - yy;
								System.out.println("Age is---------" + age);

								req.setAttribute("age", age);

								
								String custpersonal_add = tddelegate.getNomineeAddress(cid);
								System.out.println("custpersonal_add---> "+ custpersonal_add);

								req.setAttribute("Address", custpersonal_add);
								req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
								req.setAttribute("personalInfo", custmast);
								req.setAttribute("panelName",CommonPanelHeading.getPersonal());

							}

						}

						System.out.println("The value of forward====> "+ acc_de.getForward());

						if (acc_de.getForward().equalsIgnoreCase("Submit")) {

							System.out.println("Inside SUBMIT (TDAMMENDMENTS)");
							// DepositMasterObject dep_ammendment = new
							// DepositMasterObject();

							DepositMasterObject dep_open_obj = new DepositMasterObject();

							dep_open_obj.setAccType(acc_de.getAc_type());
							dep_open_obj.setAccNo(acc_de.getAc_no());
							dep_open_obj.setCustomerId(acc_de.getCid());
							dep_open_obj.setMaturityDate(acc_de.getMat_date());
							dep_open_obj.setDepDate(acc_de.getDep_date());
							dep_open_obj.setDepositAmt(acc_de.getDep_amttwo());
							dep_open_obj.setDepositDays(Integer.parseInt(String.valueOf(acc_de.getPeriod_of_daystwo())));
							dep_open_obj.setMaturityAmt(acc_de.getMat_amt());
							dep_open_obj.setInterestRate(acc_de.getInt_rate());
							System.out.println("ExtraArte type-----Ammend in submit> "+ acc_de.getCombo_mat_cat());
							System.out.println("Auto renewal-----------> "+ acc_de.getCombo_autorenewal());
							dep_open_obj.setAutoRenewal(acc_de.getCombo_autorenewal());
							dep_open_obj.setExtraRateType(Integer.parseInt(acc_de.getCombo_mat_cat()));
							System.out.println("dep_open_obj GET EXTRA---> "+ dep_open_obj.getExtraRateType());
							dep_open_obj.setTranDate(TDDelegate.getSysdate());
							dep_open_obj.setInterestFrq(acc_de.getInterest_freq());
							System.out.println("Intro Ac_type===> "	+ acc_de.getIntro_ac_type());
							System.out.println("Intro Ac_no===> "+ acc_de.getIntro_ac_no());
							dep_open_obj.setIntroAccType(acc_de.getIntro_ac_type());
							dep_open_obj.setIntroAccno(Integer.parseInt(acc_de.getIntro_ac_no()));
							dep_open_obj.setInterestMode(acc_de.getPay_mode());
							dep_open_obj.userverifier.setUserId(user);
							dep_open_obj.userverifier.setUserTml(tml);
							dep_open_obj.userverifier.setUserDate(TDDelegate.getSysdate()+ TDDelegate.getSysTime());

							System.out.println("Inetro DepositMasster"
									+ dep_open_obj.getIntroAccType());
							System.out.println("Inetro ACNO DepositMasster"
									+ dep_open_obj.getIntroAccno());
							System.out.println("interest freq==="
									+ acc_de.getInterest_freq());
							System.out.println("TransferAccType------->"
									+ acc_de.getPay_ac_type());
							System.out.println("TransferAccno------->"
									+ acc_de.getPay_mode_ac_no());
							System.out.println("mat amt in action CLASSSSSSSS"
									+ acc_de.getMat_amt());
							System.out.println("period of days==="
									+ acc_de.getPeriod_of_days());
							System.out.println("interest rate=="
									+ acc_de.getInt_rate());

							System.out.println("acc_de.getPay_mode()-----> "
									+ acc_de.getPay_mode());
							if (acc_de.getPay_mode().equalsIgnoreCase(
									"Transfer")
									|| acc_de.getPay_mode().startsWith("T")) {

								System.out
										.println("I think i am inside hope fully");

								dep_open_obj.setReceivedBy("T");
								/*
								 * dep_open_obj.setReceivedAccno(acc_de.getPay_mode_ac_no
								 * ());dep_open_obj.setReceivedAccType(acc_de.
								 * getPay_ac_typetwo());
								 * dep_open_obj.setInterestMode
								 * (acc_de.getPay_mode());
								 */
								dep_open_obj.setTransferAccType(acc_de
										.getPay_ac_typetwo());
								dep_open_obj.setTransferAccno(acc_de
										.getPay_mode_ac_no());
								// int trf_ac_no=acc_de.getPay_mode_ac_no();
								int trf_ac_no = Integer.parseInt(acc_de
										.getPay_ac_typetwo());
								System.out.println("paid acc no===="
										+ trf_ac_no);
								System.out.println("geetha inside transfer ");

							}

							if (acc_de.getPay_mode().equalsIgnoreCase("Cash")
									|| acc_de.getPay_mode().startsWith("C")) {

								System.out.println("Yup i am inside this time");
								dep_open_obj.setReceivedBy("C");
								// dep_open_obj.set
								System.out.println("geetha inside cash");
								dep_open_obj.setInterestMode(acc_de
										.getPay_mode());

							}

							if (acc_de.getPay_mode().equalsIgnoreCase(
									"Clearing")
									|| acc_de.getPay_mode().startsWith("G")) {
								System.out
										.println("Yup i am inside Clearing dude");
								dep_open_obj.setTransferType("G");
								dep_open_obj.setReceivedAccno(acc_de
										.getControl_no());
								System.out.println("geetha inside clearing");
								dep_open_obj.setInterestMode(acc_de
										.getPay_mode());

							}
							/*
							 * if(acc_de.getAmt_recv().equalsIgnoreCase("Transfer"
							 * )){
							 * 
							 * dep_open_obj.setTransferType("T");
							 * dep_open_obj.setTransferAccType
							 * (acc_de.getTrf_actype());
							 * dep_open_obj.setTransferAccno
							 * (acc_de.getTrf_acno());
							 * System.out.println("trf_ac_no===in action==="
							 * +acc_de.getTrf_acno()); }
							 * if(acc_de.getAmt_recv().
							 * equalsIgnoreCase("Cash")){
							 * 
							 * dep_open_obj.setReceivedBy("C");
							 * //dep_open_obj.setTransferAccType(null);
							 * 
							 * 
							 * 
							 * dep_open_obj.setReceivedAccno(acc_de.getScroll_no(
							 * ));System.out.println("scroll_no====="+acc_de.
							 * getScroll_no());
							 * dep_open_obj.setString_scroll_date
							 * (acc_de.getDate().trim()); }
							 * 
							 * 
							 * 
							 * if(acc_de.getAmt_recv().equalsIgnoreCase("Q/DD/PO"
							 * )){
							 * 
							 * dep_open_obj.setReceivedBy("G");
							 * //dep_open_obj.setTransferAccType(null);
							 * System.out.println("inside cleaeringggggg");
							 * dep_open_obj
							 * .setReceivedAccno(acc_de.getControl_no());
							 * System.
							 * out.println("control no======"+acc_de.getControl_no
							 * ());
							 * dep_open_obj.setString_scroll_date(acc_de.getDate
							 * ().trim());
							 * dep_open_obj.setReceivedAccno(acc_de.getControl_no
							 * ()); }
							 */

							int ac_value = tddelegate.storeDeposit(
									dep_open_obj, acc_de.getAc_type(), acc_de
											.getAc_no(), 1);

							System.out
									.println("ac_value in ammend Sumanth lets see ---"
											+ ac_value);
							if (ac_value != 0) {
								acc_de
										.setTesting("Account Successfully Updated");
							} else {
								acc_de.setTesting("Updated Failed");

							}

							// req.setAttribute("ammendments",ac_value);

						}

					}
				}

				if (MenuNameReader.containsKeyScreen(acc_de.getPageId())) {

					path = MenuNameReader.getScreenProperties(acc_de
							.getPageId());
					req.setAttribute("pagename", path);
					System.out.println("geetha changed here the path is "
							+ path);
					setInitParam(req, path, tddelegate);

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		// ending of Ammendments

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/InterestCalculationMenu")) {

			try {

				InterestCalc int_calc = (InterestCalc) form;
				String pageId = int_calc.getPageId();
				System.out.println("pageId===" + pageId);
				req.setAttribute("pagename", pageId);
				// req.setAttribute("getfocus","ac_type");
				System.out.println("in interest calculation...");

				if (MenuNameReader.containsKeyScreen(int_calc.getPageId())) {
					path = MenuNameReader.getScreenProperties(int_calc
							.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside menu--- interest..");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/InterestCalculation")) {

			try {
				TDDelegate tddelegate = new TDDelegate();
				InterestCalc int_calc = (InterestCalc) form;
				String pageId = int_calc.getPageId();

				System.out.println("pageId==----->>>=" + pageId);
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(int_calc.getPageId())) {
					path = MenuNameReader.getScreenProperties(int_calc.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu--- interest.. SUMANTH");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					if (int_calc.getForward().equalsIgnoreCase("submit")) {
						DepositIntRepObject[] DepositIntRep;
						if (int_calc.getAc_type().startsWith("1003001")) {
							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), tml, user,TDDelegate.getSysdate());
							if (DepositIntRep == null) {
														int_calc.setTesting("Nothing Found To Display");
														} 
							else {
								req.setAttribute("interestcalculation",DepositIntRep);
							     }
							
						}
						else if (int_calc.getAc_type().startsWith("1003002")) {
							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), tml, user,TDDelegate.getSysdate());
							if (DepositIntRep == null) {
														int_calc.setTesting("Nothing Found To Display");
														} 
							else {
								req.setAttribute("interestcalculation",DepositIntRep);
							     }
							
						}

						else if (int_calc.getAc_type().equalsIgnoreCase("1004001")) {

							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), int_calc.getUtml(),int_calc.getUid(), int_calc.getUdate());

							
							if (DepositIntRep == null) {
													int_calc.setTesting("Nothing Found To Display");
														} 
							else 	{
									if(DepositIntRep!=null)
								      {
									  req.setAttribute("interestcalculation",DepositIntRep);
									  }
							        }

						   }

						else if (int_calc.getAc_type().startsWith("1005001")) {

							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), int_calc.getUtml(),int_calc.getUid(), int_calc.getUdate());

							if (DepositIntRep == null) {
								int_calc.setTesting("Nothing Found To Display");
							} else {
								req.setAttribute("interestcalculation",DepositIntRep);
							}
													}
						else if (int_calc.getAc_type().startsWith("1005002")) {

							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), int_calc.getUtml(),int_calc.getUid(), int_calc.getUdate());

							if (DepositIntRep == null) {
								int_calc.setTesting("Nothing Found To Display");
							} else {
								req.setAttribute("interestcalculation",DepositIntRep);
							}
													}
						else if (int_calc.getAc_type().startsWith("1005003")) {

							DepositIntRep = tddelegate.interestCalculation(0,int_calc.getAc_type(), int_calc.getUtml(),int_calc.getUid(), int_calc.getUdate());

							if (DepositIntRep == null) {
								int_calc.setTesting("Nothing Found To Display");
							} else {
								req.setAttribute("interestcalculation",DepositIntRep);
							}
													}

					}

					else if (int_calc.getForward().equalsIgnoreCase("view")) {
						
						DepositIntRepObject[] depRio;
						if (int_calc.getAc_type().equalsIgnoreCase("1003001")) {
							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), tml, user, TDDelegate.getSysdate());			
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
							
						}
						else if (int_calc.getAc_type().equalsIgnoreCase("1003002")) {
							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), tml, user, TDDelegate.getSysdate());			
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
							
						}
						else if (int_calc.getAc_type().equalsIgnoreCase("1004001")) {

							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), int_calc.getUtml(), int_calc.getUid(), int_calc.getUdate());
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
						}

						else if (int_calc.getAc_type().startsWith("1005001")) {
							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), int_calc.getUtml(), int_calc.getUid(), int_calc.getUdate());
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
						}
						else if (int_calc.getAc_type().startsWith("1005002")) {
							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), int_calc.getUtml(), int_calc.getUid(), int_calc.getUdate());
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
						}
						else if (int_calc.getAc_type().startsWith("1005003")) {
							depRio = tddelegate.interestCalculation(1, int_calc.getAc_type(), int_calc.getUtml(), int_calc.getUid(), int_calc.getUdate());
							if (depRio == null) {
								int_calc.setTesting("RecordsNotFound");
							} else {
								req.setAttribute("interestcalculation", depRio);
							}
						}
					}

					return map.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// Daily interest calc finished

		// code for Quarterly Interest Calculation
		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/QuarterlyInterestMenu")) {

			try {
				System.out.println(" inside quarterly Interest....");
				QuarterlyInterest quarter_int_calc = (QuarterlyInterest) form;
				String pageId = quarter_int_calc.getPageId();
				System.out.println("pageId===" + pageId);
				req.setAttribute("pagename", pageId);

				System.out.println("in Quarterly interest calculation...");

				if (MenuNameReader.containsKeyScreen(quarter_int_calc
						.getPageId())) {
					path = MenuNameReader.getScreenProperties(quarter_int_calc
							.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside menu--- interest..");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/QuarterlyInterest")) {

			try {
				TDDelegate tddelegate = new TDDelegate();
				QuarterlyInterest quarter_int_calc = (QuarterlyInterest) form;
				String pageId = quarter_int_calc.getPageId();

				System.out.println("pageId===" + pageId);
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(quarter_int_calc
						.getPageId())) {
					path = MenuNameReader.getScreenProperties(quarter_int_calc
							.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu--- interest.222.");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					System.out.println("A-----t--y---> "
							+ quarter_int_calc.getAc_type());

					if (quarter_int_calc.getAc_type() != null) {
						System.out
								.println("I am inside combodate action if loop=>"
										+ quarter_int_calc.getAc_type());

						req.setAttribute("Combodate", tddelegate
								.getCombodate(quarter_int_calc.getAc_type()));
						// req.setAttribute("Combodate",tDelegate.getCombodate("1003001"));
					}

					if (quarter_int_calc.getForward().equalsIgnoreCase("clear")) {
						if (MenuNameReader.containsKeyScreen(quarter_int_calc
								.getPageId())) {
							path = MenuNameReader
									.getScreenProperties(quarter_int_calc
											.getPageId());

							System.out.println("inside menu--- interest..");
							setInitParam(req, path, tddelegate);

							System.out.println("path****" + path);
							req.setAttribute("pageId", path);
							System.out.println("the path" + path);

							return map.findForward(ResultHelp.getSuccess());

						}
					}

					if (quarter_int_calc.getForward()
							.equalsIgnoreCase("submit")) {
						DepositIntRepObject[] dep_int_rep = null;
						if (quarter_int_calc.getAc_type().equalsIgnoreCase(
								"1003001")) {

							System.out
									.println("inside interest calc action class...");

							dep_int_rep = tddelegate.quarterlyInterestCalc(0,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not found");
							}
							System.out.println("actype===="
									+ quarter_int_calc.getAc_type());

							System.out.println("after interest calculaton....");
						}

						else if (quarter_int_calc.getAc_type()
								.equalsIgnoreCase("1004001")) {

							System.out
									.println("inside interest calc action class..recurring deposit.");

							// req.setAttribute("quarterlyinterestcalc",tddelegate.quarterlyInterestCalc(0,
							// quarter_int_calc.getAc_type(),
							// quarter_int_calc.getUtml(),
							// quarter_int_calc.getUid(),
							// quarter_int_calc.getUdate(),quarter_int_calc.getCombo_select()));
							dep_int_rep = tddelegate.quarterlyInterestCalc(0,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not found");
							}
							System.out
									.println("........inside recurring deposit.........");

							System.out
									.println("after interest calculaton. in rd...");
						}

						else if (quarter_int_calc.getAc_type()
								.equalsIgnoreCase("1005001")) {

							System.out
									.println("inside interest calc action class..reinvestment.");

							// req.setAttribute("quarterlyinterestcalc",tddelegate.quarterlyInterestCalc(0,
							// quarter_int_calc.getAc_type(),
							// quarter_int_calc.getUtml(),
							// quarter_int_calc.getUid(),
							// quarter_int_calc.getUdate(),quarter_int_calc.getCombo_select()));
							dep_int_rep = tddelegate.quarterlyInterestCalc(0,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not found");
							}
							System.out
									.println("acctype wen on click of Reinvestment");

							System.out
									.println("after interest calculaton..reinvestment..");
						}

					}

					else if (quarter_int_calc.getForward().equalsIgnoreCase(
							"view")) {
						DepositIntRepObject[] dep_int_rep = null;
						if (quarter_int_calc.getAc_type().equalsIgnoreCase(
								"1003001")) {

							System.out
									.println("########### inside view button####  1003 type====1");
							dep_int_rep = tddelegate.quarterlyInterestCalc(1,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not Found");
							}
							System.out.println("actype====="
									+ quarter_int_calc.getAc_type());

							System.out.println("after view button");
						}
						// block added by rock on 08/09/2011## ########
						else if (quarter_int_calc.getAc_type()
								.equalsIgnoreCase("1003002")) {

							System.out
									.println("########### inside view button# TMCB###  1003 type====1");
							dep_int_rep = tddelegate.quarterlyInterestCalc(1,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not Found");
							}
							System.out.println("actype====="
									+ quarter_int_calc.getAc_type());

							System.out.println("after view button");
						}

						else if (quarter_int_calc.getAc_type()
								.equalsIgnoreCase("1004001")) {

							System.out
									.println("########### inside view button###RD# 1004 type====1");
							dep_int_rep = tddelegate.quarterlyInterestCalc(1,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							// req.setAttribute("quarterlyinterestcalc",tddelegate.quarterlyInterestCalc(1,
							// quarter_int_calc.getAc_type(),
							// quarter_int_calc.getUtml(),
							// quarter_int_calc.getUid(),
							// quarter_int_calc.getUdate(),quarter_int_calc.getCombo_select()));
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not Found");
							}
							System.out.println("actype====="
									+ quarter_int_calc.getAc_type());

							System.out.println("after view button");
						}
						// block added by rock for VD actype on 08/09/2011
						else if (quarter_int_calc.getAc_type()
								.equalsIgnoreCase("1005003")) {

							System.out
									.println("########### inside view button#ROCK##VD# 1005 type====1");
							dep_int_rep = tddelegate.quarterlyInterestCalc(1,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							// req.setAttribute("quarterlyinterestcalc",tddelegate.quarterlyInterestCalc(1,
							// quarter_int_calc.getAc_type(),
							// quarter_int_calc.getUtml(),
							// quarter_int_calc.getUid(),
							// quarter_int_calc.getUdate(),quarter_int_calc.getCombo_select()));
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not Found");
							}
							System.out.println("actype====="
									+ quarter_int_calc.getAc_type());

							System.out.println("after view button");
						}

						if (quarter_int_calc.getAc_type().startsWith("1005")) {

							System.out
									.println("########### inside view button##BLCC## 1500 type====1 "
											+ quarter_int_calc.getAc_type());
							dep_int_rep = tddelegate.quarterlyInterestCalc(1,
									quarter_int_calc.getAc_type(),
									quarter_int_calc.getUtml(),
									quarter_int_calc.getUid(), quarter_int_calc
											.getUdate(), quarter_int_calc
											.getCombo_select());
							// req.setAttribute("quarterlyinterestcalc",
							// tddelegate.quarterlyInterestCalc(1,
							// quarter_int_calc.getAc_type(),
							// quarter_int_calc.getUtml(),
							// quarter_int_calc.getUid(),
							// quarter_int_calc.getUdate(),quarter_int_calc.getCombo_select()));
							if (dep_int_rep != null) {
								req.setAttribute("quarterlyinterestcalc",
										dep_int_rep);
							} else {
								quarter_int_calc
										.setTesting("Records Not Found");
							}
							System.out.println("actype====="
									+ quarter_int_calc.getAc_type());

							System.out.println("after view button");
						}
					}

					/*
					 * if(quarter_int_calc.getCombo_select().length()>0){
					 * 
					 * 
					 * 
					 * req.setAttribute("DepositTran",tddelegate.getCombodate());
					 * 
					 * }
					 */

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// Quarterly interest ended
		// closure started

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/TDCLosureMenu")) {

			try {

				TDClosure td_closure = (TDClosure) form;
				String pageId = td_closure.getPageId();
				System.out.println("pageId.........." + pageId);
				req.setAttribute("pagename", pageId);
				td_closure.setFlag("null");
				td_closure.setLoantrue("null");

				td_closure.setForward("null");

				if (MenuNameReader.containsKeyScreen(td_closure.getPageId())) {
					path = MenuNameReader.getScreenProperties(td_closure
							.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside --- closure..");
					setInitParam(req, path, tddelegate);

					System.out.println("path$$$$$$" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					req.setAttribute("Verifyvalue", 1);
					req.setAttribute("boolflag", 1);
					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim()
				.equalsIgnoreCase("/TermDeposit/TDCLosure")) {
			try {
				System.out
						.println("--------/TermDeposit/TDCLosure-------------");
				TDDelegate tddelegate = new TDDelegate();
				TDClosure td_closure = (TDClosure) form;
				// DepositRenewal dep_renewal=(DepositRenewal)form;
				String pageId = td_closure.getPageId();
				td_closure.setFlag("null");
				td_closure.setLoantrue("null");
				DepositMasterObject master_obj = new DepositMasterObject();

				System.out.println("The Details Sumanth-----> "
						+ td_closure.getDetails());

				if (td_closure.getDetails() != null
						&& td_closure.getAc_no() > 0
						&& td_closure.getAc_type() != null) {
					if (td_closure.getDetails().equals("Personal")) {
						CustomerMasterObject custmast1;
						System.out
								.println("21/MAr/2009 Sumanth is in personal------->");
						DepositMasterObject depmasterverify_val;
						depmasterverify_val = tddelegate
								.getDepositMasterValues(td_closure.getAc_no(),
										td_closure.getAc_type());
						int cid = depmasterverify_val.getCustomerId();
						System.out.println("the cid is===> " + cid);
						custmast1 = tddelegate.getCustomer(cid);
						System.out.println("custmast1====? "
								+ custmast1.getDOB());
						String bdate = tddelegate.getBOD(cid);
						String sysdate = tddelegate.getSysdate();

						StringTokenizer d = new StringTokenizer(bdate, "/");
						d.nextToken();
						d.nextToken();
						int yy = Integer.parseInt(d.nextToken());

						Calendar cal = Calendar.getInstance();
						int age = cal.get(Calendar.YEAR) - yy;
						System.out.println("Age is---------" + age);
						req.setAttribute("personalInfo", custmast1);
						req.setAttribute("age", age);
						String custpersonal_add = tddelegate
								.getNomineeAddress(cid);
						System.out.println("custpersonal_add---> "
								+ custpersonal_add);

						req.setAttribute("Address", custpersonal_add);
						req.setAttribute("panelName", CommonPanelHeading
								.getPersonal());
						req.setAttribute("flag", MenuNameReader
								.getScreenProperties("0001"));

					} else if (td_closure.getDetails().equals("Introducer")) {
						System.out.println("====in Introducer ===" + path);

						DepositMasterObject dep_close = tddelegate
								.getClosureInfo(td_closure.getAc_type(),
										td_closure.getAc_no(), false);
						System.out.println("Cid in Introducer----> "
								+ dep_close.getCustomerId());
						String introactype = dep_close.getIntroAccType();
						DepositMasterObject dep_closeintro = tddelegate
								.getClosureInfo(dep_close.getIntroAccType(),
										dep_close.getIntroAccno(), false);
						int introno = dep_close.getIntroAccno();
						if (introactype == null || introno == 0) {
							td_closure.setTesting("Introducer Not Present!!!");
						}
						AccountObject acntObject2 = tddelegate
								.getIntroducerAcntDetails(dep_close
										.getIntroAccType(), dep_close
										.getIntroAccno());
						System.out.println("====in Introducer ===" + path);

						if (acntObject2 != null) {
							int cid7 = dep_closeintro.getCustomerId();
							System.out.println("the cid7 ho my is===> " + cid7);
							CustomerMasterObject custmast33 = null;
							custmast33 = tddelegate.getCustomer(cid7);
							String bdate = tddelegate.getBOD(cid7);
							String sysdate = tddelegate.getSysdate();

							StringTokenizer d = new StringTokenizer(bdate, "/");
							d.nextToken();
							d.nextToken();
							int yy = Integer.parseInt(d.nextToken());

							Calendar cal = Calendar.getInstance();
							int age = cal.get(Calendar.YEAR) - yy;
							System.out.println("Age is---------" + age);

							req.setAttribute("age", age);
							String custpersonal_add = tddelegate
									.getNomineeAddress(cid7);
							System.out.println("custpersonal_add---> "
									+ custpersonal_add);
							System.out.println("acntObject.getCid()=="
									+ acntObject2.getCid());
							req.setAttribute("Address", custpersonal_add);
							req.setAttribute("personalInfo", tddelegate
									.getCustomer(acntObject2.getCid()));
							req.setAttribute("flag", MenuNameReader
									.getScreenProperties("0001"));
							req.setAttribute("panelName", CommonPanelHeading
									.getIntroucer());

						} else {
							req.setAttribute("personalInfo",
									new CustomerMasterObject());
						}
					} else if (td_closure.getDetails().equals("JointHolders")) {
						System.out.println("INSIDE JOINT holders");
						// req.setAttribute("personalInfo",tddelegate.getCustomer(cid));

						DepositMasterObject dep_close = tddelegate
								.getClosureInfo(td_closure.getAc_type(),
										td_closure.getAc_no(), false);
						int jointhold = dep_close.getNoofJtHldrs();
						if (jointhold == 0) {
							System.out.println("I am in No joint holder");
							td_closure.setTesting("NO Joint Holders!!! ");
						}
						if (dep_close != null) {
							int[] cid = dep_close.getJointCid();
							if (cid != null) {
								req.setAttribute("personalInfo", tddelegate
										.getCustomer(cid[0]));
								req.setAttribute("flag", MenuNameReader
										.getScreenProperties("0001"));
								req.setAttribute("panelName",
										CommonPanelHeading.getJointHolder());
							}
						} else {
							td_closure.setTesting("oknojoint");
						}

					} else if (td_closure.getDetails().equals("Nominee")) {
						System.out.println("Inside Nominee---->");
						NomineeObject[] nominnee = null;

						dep_close = tddelegate.getDepositMasterValues(
								td_closure.getAc_no(), td_closure.getAc_type());
						System.out.println("Deposit master Nominee---> "
								+ dep_close.getNomineeRegNo());
						int NomResult = dep_close.getNomineeRegNo();
						if (NomResult == 0) {
							System.out.println("I am in the nim zero");
							td_closure.setTesting("Nominee Not present");
						}
						if (dep_close != null && NomResult != 0) {
							nominnee = tddelegate.getNomineeDetails(dep_close
									.getNomineeRegNo());
							req.setAttribute("disabled", "Yes");
							System.out.println("NOmiNEEE CID----> "
									+ nominnee[0].getRegNo());
							req
									.setAttribute("flag", MenuNameReader
											.getScreenProperties(String
													.valueOf(13026)));
							String NomAddress = tddelegate
									.getNomineeAddrDetails(nominnee[0]
											.getRegNo());
							req.setAttribute("NOMADDR", NomAddress);
							req.setAttribute("NomDetail", nominnee);
							req.setAttribute("HideTheFields", null);
						}

					} else if (td_closure.getDetails().equals(
							"SignatureDetails")) {
						System.out
								.println("Inside Signature Deatails%%%%%%%%%%%%%%");
						CustomerMasterObject custmast12 = null;
						dep_close = tddelegate.getDepositMasterValues(
								td_closure.getAc_no(), td_closure.getAc_type());
						int cid = dep_close.getCustomerId();
						custmast12 = tddelegate.getCustomer(cid);
						if (custmast12.getSign() == null) {
							td_closure
									.setTesting("No SignatureDetails available!!!");
						} else {
							dep_close = tddelegate.getDepositMasterValues(
									td_closure.getAc_no(), td_closure
											.getAc_type());
							req.setAttribute("flag", MenuNameReader
									.getScreenProperties("0007"));
							req.setAttribute("personalInfo", custmast12);
							req.setAttribute("panelName", CommonPanelHeading
									.getSignature());

						}

					} 
					else if (td_closure.getDetails().trim().equalsIgnoreCase(
							"Loan Details")) {
						dep_close = tddelegate.getDepositMasterValues(
								td_closure.getAc_no(), td_closure.getAc_type());
						String loanresult = dep_close.getLoanAcType();
						int lonno = dep_close.getLoanAccno();
						if (loanresult == null || lonno == 0) {
							td_closure.setTesting("Loan NOt Availed!!!");
						}
					}

				}

				String Deleteaction = (String) req.getParameter("detail");
				System.out.println("The Value of Deleteaction sumanth==> "
						+ Deleteaction);

				/*
				 * if(td_closure.getPay_mode().equalsIgnoreCase("Transfer")) {
				 * 
				 * 
				 * 
				 * req.setAttribute("panelName",CommonPanelHeading.getPersonal())
				 * ;
				 * req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"
				 * ));
				 * req.setAttribute("panelName",CommonPanelHeading.getTransfer
				 * ()); System.out.println("====in Transfer==="+path);
				 * dep_sub.setTransferType("T");
				 * System.out.println("page Id in transfer panel==>"
				 * +td_closure.getPageId());
				 * 
				 * DepositMasterObject dep_close
				 * =tddelegate.getClosureInfo(td_closure.getAc_type(),
				 * td_closure.getAc_no(), false); //DepositMasterObject
				 * dep_close =tddelegate.getTranDetails(td_closure.getAc_type(),
				 * td_closure.getAc_no(), false);
				 * System.out.println("TransferAccountType----))))))-> "+
				 * dep_close.getTransferAccType());
				 * System.out.println("TransferAccountNo----))))))-> " +
				 * dep_close.getTransferAccno()); AccountObject acntObject
				 * =tddelegate
				 * .getIntroducerAcntDetails(dep_close.getTransferAccType
				 * (),dep_close.getTransferAccno());
				 * 
				 * req.setAttribute("personalInfo",acntObject);
				 * 
				 * dep_sub.setInterestPaid(td_closure.getInt_amt_paid());
				 * dep_sub.setDepositPaid(td_closure.getDep_amt());
				 * path=MenuNameReader
				 * .getScreenProperties(td_closure.getPageId());
				 * System.out.println("path=============>"+path); tddelegate=new
				 * TDDelegate();
				 * 
				 * setInitParam(req, path, tddelegate);
				 * 
				 * 
				 * 
				 * }
				 */

				if (Deleteaction != null) {
					AccountObject accountObject12 = tddelegate
							.getAcccountDetails(td_closure.getAc_type(), null,
									td_closure.getAc_no(), tddelegate
											.getSysdate());
					System.out.println("accountObject12 length==> "
							+ accountObject12);
					if (accountObject12 != null) {
						System.out.println("accountObject12===> "
								+ accountObject12.getVerified());
					}
					if (accountObject12 == null) {
						td_closure
								.setTesting("Cannot Delete-Already Verified!!");
					} else if (Deleteaction.equalsIgnoreCase("delete")
							&& accountObject12.getVerified() == null) {
						System.out.println("Hi i am in delete TDClosure");
						// tddelegate.TDClosureDataDelete(td_closure.getAc_type(),td_closure.getAc_no());
					}
				} else if (td_closure.getPay_mode().equalsIgnoreCase("Cash")) {
					/*
					 * System.out.println("====in cash path===");
					 * dep_sub.setTransferType("C");
					 * path=MenuNameReader.getScreenProperties
					 * (td_closure.getPageId());
					 * System.out.println("geetha in else loop");
					 * System.out.println
					 * ("trnsfer type setting in action class----"
					 * +dep_sub.getTransferType());
					 */
				}

				/*
				 * else if(td_closure.getDetails().equals("Loan Details")) {
				 * 
				 * System.out.println("====in Loan Details==="+path);
				 * req.setAttribute
				 * ("flag",MenuNameReader.getScreenProperties("13025"));
				 * req.setAttribute
				 * ("panelName",CommonPanelHeading.getLoanDetails());
				 * if(td_closure.getAc_type()!=null && td_closure.getAc_no()!=0)
				 * { //hard coded false for time
				 * 
				 * DepositMasterObject dep_close
				 * =tddelegate.getClosureInfo(td_closure.getAc_type(),
				 * td_closure.getAc_no(), false);
				 * System.out.println("Loan Account type ==> "+
				 * dep_close.getLoanAcType()+ "Loan Account number==>" +
				 * dep_close.getLoanAccno()); String
				 * systemdate=tddelegate.getSysdate(); LoanReportObject
				 * loanReportObject
				 * =tddelegate.getLoanDetails_Closure(dep_close.
				 * getLoanAcType(),systemdate,dep_close.getLoanAccno());
				 * System.out
				 * .println("loan report object===>"+loanReportObject);
				 * req.setAttribute("DepositDetail",master_obj);
				 * req.setAttribute("LoanDetails",loanReportObject); } }
				 */

				if (MenuNameReader.containsKeyScreen(td_closure.getPageId())) {
					System.out.println("Dooooollerrrrrr"
							+ td_closure.getAc_no());
					path = MenuNameReader.getScreenProperties(td_closure
							.getPageId());
					setInitParam(req, path, tddelegate);
					// int ac_no=dep_renewal.getAc_no();
					int close_ind = 0;
					tddelegate = new TDDelegate();
					String ac_type = td_closure.getAc_type();
					System.out.println("----------For Verify------>>>>> "
							+ td_closure.getForward());
					//
					DepositMasterObject dep_sub = new DepositMasterObject();

					if (td_closure.getForward().equalsIgnoreCase("submit")) {

						System.out.println("Entering into submit button11111");

						DepositMasterObject dep_ind = new DepositMasterObject();

						System.out.println("inside submit button1111");

						dep_sub.setAccType(td_closure.getAc_type());
						dep_sub.setAccNo(td_closure.getAc_no());
						dep_sub.setClosedt(td_closure.getClosure_date());
						dep_sub.setCumInterest(td_closure.getCid());
						// /dep_sub.setCloseInd(close_ind);
						dep_sub.setMaturityDate(td_closure.getMat_date());

						System.out.println("closeind in submit===" + close_ind);
						System.out.println("close_ind in action class----"
								+ dep_sub.getCloseInd());

						// dep_ind=tddelegate.getClosureInfo(td_closure.getAc_type(),
						// td_closure.getAc_no(), false);

						System.out.println("close ind***********"
								+ dep_ind.getCloseInd());
						/*
						 * if(dep_ind.getCloseInd()==90)
						 * 
						 * dep_sub.setCloseInd(close_ind);
						 */

						dep_sub.userverifier.setUserId(user);
						dep_sub.userverifier.setUserTml(tml);
						dep_sub.userverifier.setUserDate(TDDelegate
								.getSysdate()
								+ TDDelegate.getSysTime());

						dep_sub.setAutoRenwlNo(0);

						dep_sub.setLoanAvailed("F");

						System.out.println("pay mode=Sumanthhhhhh===="
								+ td_closure.getPay_mode());

						if (td_closure.getPay_mode().equalsIgnoreCase(
								"Transfer")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"T")) {

							System.out.println("ia min Transfer Submit------> "
									+ td_closure.getPay_mode());

							dep_sub.setTransferType("T");
							System.out.println("geetha inside loan availed");
							System.out
									.println("Trans Submit getInt_amt_paid()--> "
											+ td_closure.getInt_amt_paid());
							System.out.println("getDep_amt()"
									+ td_closure.getDep_amt());
							System.out.println("After setting============> "
									+ dep_sub.getTransferType());
							System.out.println("Transfer Ac_type "
									+ td_closure.getPay_ac_type());
							System.out.println("Transfer Ac_no "
									+ td_closure.getCombo_pay_acno());
							dep_sub.setTransferAccType(td_closure
									.getPay_ac_type());
							dep_sub.setTransferAccno(Integer
									.parseInt(td_closure.getCombo_pay_acno()));
							dep_sub.setInterestPaid(td_closure
									.getInt_amt_paid());
							dep_sub.setDepositPaid(td_closure.getDep_amt());

						}

						else if (td_closure.getPay_mode().equalsIgnoreCase(
								"Cash")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"C")) {
							System.out
									.println("I am in Side Cash in submit------------"
											+ td_closure.getPay_mode());
							dep_sub.setTransferType("C");
							System.out.println("Aftersetting the value====> "
									+ dep_sub.getTransferType());

						} else if (td_closure.getPay_mode().equalsIgnoreCase(
								"G")) {
							System.out
									.println("I am in Side Clearing in submit------------"
											+ td_closure.getPay_mode());
							// dep_sub.setTransferType("G");
							dep_sub.setTransferType("P");
							System.out.println("Aftersetting the value====> "
									+ dep_sub.getTransferType());
						} else if (td_closure.getPay_mode().equalsIgnoreCase(
								"P")) {
							System.out
									.println("I am in Side Clearing in submit------------"
											+ td_closure.getPay_mode());
							dep_sub.setTransferType("P");
							System.out.println("Aftersetting the value====> "
									+ dep_sub.getTransferType());
						}

						System.out.println("CHECKING----------"
								+ req.getParameter("flag"));

						System.out.println("CHECKING 1111----------"
								+ td_closure.getForward());

						if (req.getParameter("flag").equalsIgnoreCase(
								"withoutpenalty")) {

							System.out.println("inside withoutpenalty ");
							System.out.println("after withoutpenalty----"
									+ dep_sub.getCloseInd());

							dep_sub.setCloseInd(92);
						} else if (req.getParameter("flag").equalsIgnoreCase(
								"withpenalty")) {

							System.out.println("after withpenalty----"
									+ dep_sub.getCloseInd());
							dep_sub.setCloseInd(91);

						}

						else if (req.getParameter("flag").equalsIgnoreCase(
								"Normalclosure")) {

							System.out
									.println("@@@@@@CHECK CHECK CHECK@@@@@@@@@");

							System.out
									.println("Inside Normal Closure Submit--> "
											+ dep_sub.getCloseInd());
							dep_sub.setCloseInd(90);
						}

						int closure_val = tddelegate.storeClosureInfo(dep_sub);
						System.out
								.println("Ending of submit in action class===> "
										+ closure_val);
						req.setAttribute("Verifyvalue", 1);
						req.setAttribute("boolflag", 1);
						td_closure.setTesting("Account Successfully Closed");

					}
					// end of submit
					else if (td_closure.getForward().equalsIgnoreCase("verify")) {

						DepositMasterObject dep_closename;
						dep_closename = tddelegate.getDepositMasterValues(
								td_closure.getAc_no(), td_closure.getAc_type());

						System.out.println("See there with TransferType----> "
								+ dep_sub.getTransferType());
						System.out.println("See there with AccNo----> "
								+ dep_closename.getAccNo());
						System.out.println("See there with "
								+ dep_closename.getInterestMode());
						System.out.println("---Ac_type-----> "
								+ td_closure.getAc_type());

						System.out.println("---Ac_type-----> "
								+ td_closure.getAc_no());
						DepositMasterObject depositmasterobject = new DepositMasterObject();
						int int_return_value = 0;
						System.out.println("td_closure.getClosure()---:> "
								+ td_closure.getClosure());
						System.out.println("FLAg VAlue----:> "
								+ req.getParameter("flag"));
						if (req.getParameter("flag").equalsIgnoreCase(
								"withoutpenalty")) {

							depositmasterobject.setCloseInd(92);

						} else if (req.getParameter("flag").equalsIgnoreCase(
								"withpenalty"))
							depositmasterobject.setCloseInd(91);

						else if (req.getParameter("flag").equalsIgnoreCase(
								"Normalclosure"))
							depositmasterobject.setCloseInd(90);

						// System.out.println("closeind----3---->>"+int_closeind);
						depositmasterobject.setClosedt(TDDelegate.getSysdate());
						depositmasterobject.setAccType(td_closure.getAc_type());
						depositmasterobject.setAccNo(td_closure.getAc_no());
						String ln_ac_type = depositmasterobject.getLoanAcType();
						System.out.println("ln_ac_type" + ln_ac_type);
						depositmasterobject.setLoanAcType(ln_ac_type);
						System.out.println("The value of transfer type====> "
								+ dep_closename.getTransferType());
						System.out.println("pay mode Sumanthhhhh===="
								+ td_closure.getPay_mode());

						if (td_closure.getPay_mode().equalsIgnoreCase("Cash")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"C")) {

							depositmasterobject.setReceivedBy("C");
							depositmasterobject.setTransferType("C");
							System.out.println("geetha inside cash");
							depositmasterobject.setInterestMode(td_closure
									.getPay_mode());
							System.out.println("mode visible==="
									+ depositmasterobject.getReceivedBy());
							System.out.println("in cah----"
									+ depositmasterobject.getTransferType());

						} else if (td_closure.getPay_mode().equalsIgnoreCase(
								"Transfer")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"T")) {

							depositmasterobject.setReceivedBy("T");
							System.out.println("geetha inside transfer");
							depositmasterobject.setInterestMode(td_closure
									.getPay_mode());
							depositmasterobject.setTransferType("T");

							System.out.println("if 'T' u r setting^^^^^^^^^"
									+ depositmasterobject.getTransferType());

						} else if (td_closure.getPay_mode().equalsIgnoreCase(
								"P")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"G")) {

							// depositmasterobject.setReceivedBy("G");
							depositmasterobject.setReceivedBy("P");
							System.out.println("geetha inside clearing");
							depositmasterobject.setInterestMode(td_closure
									.getPay_mode());

						}
						if (td_closure.getPay_ac_type() != null
								&& Integer.parseInt(td_closure
										.getCombo_pay_acno()) > 0) {
							depositmasterobject.setTransferAccType(td_closure
									.getPay_ac_type());
							depositmasterobject.setTransferAccno(Integer
									.parseInt(td_closure.getCombo_pay_acno()));

							System.out.println("transfer ac_no===="
									+ depositmasterobject.getTransferAccno());
						}
						// depositmasterobject.setTransferType(td_closure.getPay_mode());
						depositmasterobject.userverifier.setUserDate(TDDelegate
								.getSysdate()
								+ TDDelegate.getSysTime());
						depositmasterobject.userverifier.setUserId(user);
						depositmasterobject.userverifier.setUserTml(tml);
						depositmasterobject.userverifier.setVerId(user);
						depositmasterobject.userverifier.setVerTml(tml);

						depositmasterobject.userverifier.setVerDate(TDDelegate
								.getSysdate()
								+ TDDelegate.getSysTime());
						depositmasterobject.setCumInterest(td_closure
								.getInt_amt_pay());

						System.out
								.println("the name of the DepositCustomer is----> "
										+ dep_closename.getName());
						depositmasterobject.setName(dep_closename.getName());

						System.out.println("===depositmasterobject----> "
								+ depositmasterobject.getCloseInd());

						int_return_value = tddelegate
								.closureVerify(depositmasterobject);
						System.out.println("Hurray see this mite======> "
								+ td_closure.getPay_mode());
						System.out
								.println("int_return_value------> Number====> "
										+ int_return_value);
						System.out.println("Pymode  is ======> "
								+ td_closure.getPay_mode());
						if (td_closure.getPay_mode().equalsIgnoreCase("Cash")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"C")) {
							req.setAttribute("Verifyvalue", 2);
							req.setAttribute("boolflag", 2);
							td_closure
									.setTesting("Sucessfully Verified \n Note Down Voucher No :"
											+ int_return_value);
						} else if (td_closure.getPay_mode().equalsIgnoreCase(
								"Q/DD/PO")
								|| td_closure.getPay_mode().equalsIgnoreCase(
										"P")) {
							req.setAttribute("Verifyvalue", 2);
							req.setAttribute("boolflag", 2);
							td_closure
									.setTesting("Sucessfully Verified \n Note Down PO No :"
											+ int_return_value);
						} else {
							req.setAttribute("Verifyvalue", 2);
							req.setAttribute("boolflag", 2);
							td_closure
									.setTesting("Account Sucessfully Verified");
						}

						System.out
								.println("%%%%%%%%After verification in Action Class%%%%%%");

					}

					else if (td_closure.getAc_no() != 0) {

						System.out
								.println("HOooooooooooooooooooooooooooooooooooooooo"
										+ td_closure.getAc_no());

						DepositMasterObject dep_closetest = tddelegate.getClosureInfo(td_closure.getAc_type(),
										td_closure.getAc_no(), false);
						System.out.println("dep_closetest===========> "
								+ dep_closetest.getCloseInd());

						if (dep_closetest.getCloseInd() != 0
								&& dep_closetest.getCloseInd() < 6) {
							System.out
									.println("I am in Tis Account Already Closed method");
							req.setAttribute("Verifyvalue", 1);
							req.setAttribute("boolflag", 1);
							td_closure.setTesting("Account Already Closed");
						}

						else {

							System.out.println("I am in ELSE BLOCK");

							if (ac_type.startsWith("1005")) {
								AccountObject accountObject = null;
								if (td_closure.getCombo_pay_acno().length() != 0) {
									accountObject = tddelegate
											.getAcccountDetails(
													td_closure.getPay_ac_type(),
													null,
													Integer
															.parseInt(td_closure
																	.getCombo_pay_acno()),
													tddelegate.getSysdate());

									if (accountObject == null) {
										td_closure
												.setTesting("Account Not Found");

									} else if (accountObject.getAccStatus()
											.equals("C")) {
										td_closure.setTesting("Account Closed");

									}
									/*
									 * else
									 * if(accountObject.getVerified()==null){
									 * td_closure
									 * .setTesting("Account Not Yet verified");
									 * 
									 * 
									 * }
									 */
									else if (accountObject.getDefaultInd()
											.equals("T")) {
										td_closure
												.setTesting("Defaulted Account");

									} else if (accountObject.getFreezeInd()
											.equals("T")) {
										td_closure
												.setTesting("Account Freezed");

									} else {
										td_closure
												.setTesting("Enter Trnsfer AC-NO");
									}

								}
								// end of trns_no

								System.out
										.println("#######Entering inside Actype===1005#######");

								System.out
										.println("##########Entering into Reinvestment Closure#########");

								DepositMasterObject dep_close_reinvest[] = new DepositMasterObject[1];

								dep_close_reinvest[0] = new DepositMasterObject();

								dep_close_reinvest = tddelegate
										.getReinvestmentDetails(td_closure
												.getAc_type(), td_closure
												.getAc_no());

								System.out.println("loan availed===="
										+ dep_close_reinvest[0]
												.getLoanAvailed());

								if (dep_close_reinvest[0].getCloseInd() != 0
										&& dep_close_reinvest[0].getCloseInd() < 6) // ||dm[0].getCloseInd()==2||dm[0].getCloseInd()==3)
								{
									td_closure
											.setTesting("Account Already Closed");

								}
								/*
								 * else
								 * if(dep_close_reinvest[0].getCloseInd()==0) {
								 * td_closure.setTesting("Permission Denied");
								 * 
								 * }
								 */
								else if (dep_close_reinvest[0].getVerified()
										.length() == 0) {
									td_closure
											.setTesting("Account Not Yet Verified");

								}

								else {

									if ((dep_close_reinvest[0].getCloseInd() == 90
											|| dep_close_reinvest[0]
													.getCloseInd() == 91 || dep_close_reinvest[0]
											.getCloseInd() == 92)) {
										td_closure
												.setAlertdispay("Account Closed But Not Yet Verified");
										// button_update.setEnabled(true);
										req.setAttribute("boolflag", 2);
									}
									if (Validations.dayCompare(
											dep_close_reinvest[0]
													.getMaturityDate(),
											TDDelegate.getSysdate()) > 0) {

										System.out
												.println("^^^^^^^^^^^^^^inside normal closure^^^^^^^^^^^^^^^^");

										if (dep_close_reinvest[0]
												.getLoanAvailed()
												.equalsIgnoreCase("T")) {

											td_closure.setLoantrue("LoanTrue");

											System.out
													.println("LoanTrue value------>>>"
															+ td_closure
																	.getLoantrue());

											System.out
													.println("if loan availed true==="
															+ dep_close_reinvest[0]
																	.getLoanAcType()
															+ "and loan ac_no"
															+ dep_close_reinvest[0]
																	.getLoanAccno());

											System.out
													.println("days in normal closure=="
															+ Validations
																	.dayCompare(
																			dep_close_reinvest[0]
																					.getMaturityDate(),
																			TDDelegate
																					.getSysdate()));

											req
													.setAttribute(
															"DepositTranRI",
															tddelegate
																	.getReinvestmentDetails(
																			td_closure
																					.getAc_type(),
																			td_closure
																					.getAc_no()));

											System.out
													.println("loan availed-----"
															+ dep_close_reinvest[0]
																	.getLoanAvailed());

											System.out
													.println("loan availed===T");
											System.out.println("loan actype=="
													+ dep_close_reinvest[0]
															.getLoanAcType()
													+ "loan ac_no"
													+ dep_close_reinvest[0]
															.getLoanAccno());

											dep_close_reinvest[0]
													.setLoanAcType(td_closure
															.getLoan_ac_type());
											dep_close_reinvest[0]
													.setLoanAccno(td_closure
															.getLoan_ac_no());

										} else {

											System.out
													.println("days in normal closure=="
															+ Validations
																	.dayCompare(
																			dep_close_reinvest[0]
																					.getMaturityDate(),
																			TDDelegate
																					.getSysdate()));

											req
													.setAttribute(
															"DepositTranRI",
															tddelegate
																	.getReinvestmentDetails(
																			td_closure
																					.getAc_type(),
																			td_closure
																					.getAc_no()));

											System.out
													.println("loan availed-----"
															+ dep_close_reinvest[0]
																	.getLoanAvailed());

											System.out
													.println("IntupTodate---> "
															+ dep_close_reinvest[0]
																	.getInterestUpto());
											System.out
													.println("IntAmt Paid---> "
															+ dep_close_reinvest[0]
																	.getInterestPaid());
											td_closure
													.setInt_upto_date(dep_close_reinvest[0]
															.getInterestUpto());
											System.out
													.println("dep_close_reinvest[0].getMaturityAmt()"
															+ dep_close_reinvest[0]
																	.getMaturityAmt());
											System.out
													.println("dep_close_reinvest[0].getDepositAmt() "
															+ dep_close_reinvest[0]
																	.getDepositAmt());
											System.out
													.println("dep_close_reinvest[0].getInterestPaid() "
															+ dep_close_reinvest[0]
																	.getInterestPaid());

											double int_amt_paid = (Math
													.round(dep_close_reinvest[0]
															.getMaturityAmt()
															- dep_close_reinvest[0]
																	.getDepositAmt()
															- dep_close_reinvest[0]
																	.getInterestPaid()));
											System.out
													.println("int_amt_paid---> "
															+ int_amt_paid);
											td_closure
													.setInt_amt_pay(int_amt_paid);

											double double_total_amount = dep_close_reinvest[0]
													.getMaturityAmt();
											td_closure
													.setTotal_amt(double_total_amount);

											td_closure
													.setApplied_int_rate(dep_close_reinvest[0]
															.getInterestRate());

											dep_close_reinvest[0]
													.setDepositPaid(0);
											dep_close_reinvest[0]
													.setInterestPaid(0);
										}

										System.out.println("dep_date=="
												+ td_closure.getDep_date()
												+ "closure_date---"
												+ td_closure.getClosure_date());

										System.out
												.println("************before getnormalclosure*********");

										// req.setAttribute("normal",tddelegate.getReinvestmentDetails(td_closure.getAc_type(),
										// td_closure.getAc_no()));

										System.out
												.println("day compare days==="
														+ Validations
																.dayCompare(
																		td_closure
																				.getMat_date(),
																		td_closure
																				.getClosure_date()));

										td_closure.setFlag("Normalclosure");

									}

									else if (Validations.dayCompare(
											dep_close_reinvest[0]
													.getMaturityDate(),
											TDDelegate.getSysdate()) < 0) {

										td_closure.setFlag("Abn");

										System.out
												.println("+++++++++++++++++++++>>>>>>>>"
														+ td_closure
																.getClosure());
										if (td_closure
												.getClosure()
												.equalsIgnoreCase("withpenalty")) {
											td_closure.setFlag("");
											double double_interest_rate;
											double applied_interest_rate;
											double total_interest_amount;
											double interest_amount;
											double interest_payable;
											double total_amount;
											double_interest_rate = dep_close_reinvest[0]
													.getInterestRate();
											int int_total_days = tddelegate
													.getDaysFromTwoDate(
															dep_close_reinvest[0]
																	.getDepDate(),
															tddelegate
																	.getSysdate());
											double[] array_double_interest_rate = tddelegate
													.getDepositInterestRate(
															ac_type,
															dep_close_reinvest[0]
																	.getDPType(),
															dep_close_reinvest[0]
																	.getCategory(),
															Validations
																	.convertYMD(dep_close_reinvest[0]
																			.getDepDate()),
															int_total_days,
															dep_close_reinvest[0]
																	.getDepositAmt());
											double_interest_rate = array_double_interest_rate[0];
											if (dep_close_reinvest[0]
													.getExtraRateType() == 2)
												double_interest_rate += array_double_interest_rate[1];
											else if (dep_close_reinvest[0]
													.getExtraRateType() == 3)
												double_interest_rate += array_double_interest_rate[2];
											else if (dep_close_reinvest[0]
													.getExtraRateType() == 4)
												double_interest_rate += array_double_interest_rate[1]
														+ array_double_interest_rate[2];

											/**
											 * with penalty
											 */
											System.out
													.println("with penalty---->");
											ModuleObject array_moduleobject_moduleinfo[] = tddelegate
													.getMainModules(3, ac_type);
											double_interest_rate = double_interest_rate
													- array_moduleobject_moduleinfo[0]
															.getPenaltyRate();
											if (double_interest_rate < 0)
												double_interest_rate = 0;

											td_closure
													.setApplied_int_rate(double_interest_rate);

											total_interest_amount = tddelegate
													.getreinvestmentCalc(
															dep_close_reinvest,
															double_interest_rate);
											interest_amount = (total_interest_amount - dep_close_reinvest[0]
													.getDepositAmt());
											interest_payable = (interest_amount - dep_close_reinvest[0]
													.getInterestPaid());
											if (interest_payable < 0) {
												td_closure
														.setInt_amt_pay(interest_payable);

												total_amount = (dep_close_reinvest[0]
														.getDepositAmt()
														+ dep_close_reinvest[0]
																.getInterestPaid() - interest_payable);
											} else {

												td_closure
														.setInt_amt_pay(interest_payable);
												total_amount = total_interest_amount;
											}
											td_closure
													.setApplied_int_rate(double_interest_rate);
											td_closure
													.setTotal_amt(total_amount);

											/*
											 * td_closure.setFlag("");
											 * System.out.println(
											 * "withhhhhhhhhhhhhhhhhhhh changed"
											 * );
											 * req.setAttribute("closureform",
											 * tddelegate
											 * .getDepositIntRate(td_closure
											 * .getAc_type
											 * (),td_closure.getDp_type(),
											 * td_closure.getCat_type(),
											 * td_closure.getDays(),
											 * td_closure.getDep_amt(),0)); //
											 * req
											 * .setAttribute("normal",tddelegate
											 * .getnormalclosure(ac_type, ac_no,
											 * 91));
											 * 
											 * System.out.println(
											 * "Before Loan SEE here Sir!!!!");
											 */
											if (dep_close_reinvest[0]
													.getLoanAvailed()
													.equalsIgnoreCase("T")) {
												// loan case
												// if it is with penalty

												double interest_rate = tddelegate
														.getDepositIntRate(
																td_closure
																		.getAc_type(),
																td_closure
																		.getDp_type(),
																td_closure
																		.getCat_type(),
																td_closure
																		.getDays(),
																td_closure
																		.getDep_amt(),
																0);
												LoanReportObject loanReportObject = new LoanReportObject();

												System.out
														.println("if loan interest rate====>>>>"
																+ interest_rate);

												double interest = interest_rate
														+ (loanReportObject
																.getLoanIntRate() - dep_close_reinvest[0]
																.getInterestRate());
												System.out
														.println("loan int"
																+ loanReportObject
																		.getLoanIntRate());
												System.out
														.println("interest====>>>>>"
																+ interest);
												String to_date = Validations
														.addDays(TDDelegate
																.getSysdate(),
																-1);

												System.out
														.println("***inside Action class**");

												System.out
														.println("loanReportObject.getLoanBalance()"
																+ loanReportObject
																		.getLoanBalance());
												System.out
														.println("todate isssssssss  "
																+ to_date);
												System.out
														.println("validation int upto date-----"
																+ loanReportObject
																		.getIntUptoDate());
												System.out
														.println("Validations.dayCompare(loanReportObject.getIntUptoDate(),to_date)"
																+ Validations
																		.dayCompare(
																				loanReportObject
																						.getIntUptoDate(),
																				to_date));

												double interest_payable_penality = Math
														.round(loanReportObject
																.getLoanBalance()
																* Validations
																		.dayCompare(
																				loanReportObject
																						.getIntUptoDate(),
																				to_date)
																* interest
																/ 36500);

												System.out
														.println("interest balance^^^^^"
																+ interest_payable_penality);

												req
														.setAttribute(
																"int_loan",
																interest_payable_penality);
												// lbl_interest_balance.setText(String.valueOf(interest_payable_penality));
											}

											req
													.setAttribute(
															"DepositTranRI",
															tddelegate
																	.getReinvestmentDetails(
																			td_closure
																					.getAc_type(),
																			td_closure
																					.getAc_no()));

											td_closure.setFlag("withpenalty");
										} else if (td_closure.getClosure()
												.equalsIgnoreCase(
														"withoutpenalty")) {
											System.out
													.println("withhooooooooooooooo");
											td_closure.setFlag("");

											LoanReportObject loanReportObject = new LoanReportObject();
											System.out.println("dep_days===="
													+ td_closure.getDays());
											// req.setAttribute("closureform",tddelegate.getDepositIntRate(td_closure.getAc_type(),td_closure.getDp_type(),
											// td_closure.getCat_type(),
											// td_closure.getDays(),
											// td_closure.getDep_amt(),1));
											// req.setAttribute("normal",tddelegate.getnormalclosure(ac_type,
											// ac_no, 92));
											double double_interest_rate;
											double applied_interest_rate;
											double total_interest_amount;
											double interest_amount;
											double interest_payable;
											double total_amount;
											double_interest_rate = dep_close_reinvest[0]
													.getInterestRate();
											int int_total_days = tddelegate
													.getDaysFromTwoDate(
															dep_close_reinvest[0]
																	.getDepDate(),
															tddelegate
																	.getSysdate());
											double[] array_double_interest_rate = tddelegate
													.getDepositInterestRate(
															ac_type,
															dep_close_reinvest[0]
																	.getDPType(),
															dep_close_reinvest[0]
																	.getCategory(),
															Validations
																	.convertYMD(dep_close_reinvest[0]
																			.getDepDate()),
															int_total_days,
															dep_close_reinvest[0]
																	.getDepositAmt());
											double_interest_rate = array_double_interest_rate[0];
											if (dep_close_reinvest[0]
													.getExtraRateType() == 2)
												double_interest_rate += array_double_interest_rate[1];
											else if (dep_close_reinvest[0]
													.getExtraRateType() == 3)
												double_interest_rate += array_double_interest_rate[2];
											else if (dep_close_reinvest[0]
													.getExtraRateType() == 4)
												double_interest_rate += array_double_interest_rate[1]
														+ array_double_interest_rate[2];

											if (double_interest_rate < 0)
												double_interest_rate = 0;
											td_closure
													.setApplied_int_rate(double_interest_rate);

											total_interest_amount = tddelegate
													.getreinvestmentCalc(
															dep_close_reinvest,
															double_interest_rate);

											interest_amount = (total_interest_amount - dep_close_reinvest[0]
													.getDepositAmt());
											interest_payable = (interest_amount - dep_close_reinvest[0]
													.getInterestPaid());
											if (interest_payable < 0) {

												td_closure
														.setInt_amt_pay(interest_payable);
												total_amount = (dep_close_reinvest[0]
														.getDepositAmt()
														+ dep_close_reinvest[0]
																.getInterestPaid() - interest_payable);
											} else {
												td_closure
														.setInt_amt_pay(interest_payable);
												total_amount = total_interest_amount;
											}
											td_closure
													.setApplied_int_rate(double_interest_rate);
											td_closure
													.setTotal_amt(total_amount);

											if (dep_close_reinvest[0]
													.getLoanAvailed()
													.equalsIgnoreCase("T")) {
												// without penalty
												double interest_balance = (Math
														.round(loanReportObject
																.getInterestPayable()));

												System.out
														.println("interest balance without penalty^^^^^"
																+ interest_balance);
												double total_balance = (Math
														.round(loanReportObject
																.getLoanBalance()
																+ interest_balance));

											}

											req
													.setAttribute(
															"DepositTranRI",
															tddelegate
																	.getReinvestmentDetails(
																			td_closure
																					.getAc_type(),
																			td_closure
																					.getAc_no()));
											td_closure
													.setFlag("withoutpenalty");
										}

										System.out
												.println("entering to focuslost"
														+ td_closure
																.getClosure());

										System.out
												.println("days with/without penalty=="
														+ Validations
																.dayCompare(
																		dep_close_reinvest[0]
																				.getMaturityDate(),
																		TDDelegate
																				.getSysdate()));

										// System.out.println("close_date==="+td_closure.getDep_date());

										System.out.println("dep amt===="
												+ td_closure.getDep_amt());

										// req.setAttribute("normal",tddelegate.getReinvestmentDetails(td_closure.getAc_type(),
										// td_closure.getAc_no()));//suraj
										// commented here

										// req.setAttribute("DepositTran",tddelegate.getClosureInfo(td_closure.getAc_type(),
										// td_closure.getAc_no(), false));

										// req.setAttribute("closureform",tddelegate.getDepositIntRate(td_closure.getAc_type(),td_closure.getDp_type(),
										// td_closure.getCat_type(),
										// td_closure.getDays(),
										// td_closure.getDep_amt(),0));

										if (td_closure.getClosure()
												.equalsIgnoreCase(
														"withoutpenalty")) {

											close_ind = 91;

										} else if (td_closure
												.getClosure()
												.equalsIgnoreCase("withpenalty"))
											close_ind = 92;

										else if (td_closure.getClosure()
												.equalsIgnoreCase("Normal"))
											close_ind = 90;

										// req.setAttribute("intpayable",tddelegate.interestAmount(td_closure.getAc_type(),td_closure.getDp_type(),td_closure.getCat_type(),
										// 354,td_closure.getDep_amt(),
										// 0,td_closure.getMat_date(),td_closure.getDep_date()));

										System.out
												.println("int amt in action class is@@@@@@@@@@@"
														+ td_closure
																.getInt_amt_pay());

									}
								}
							}
							// end for ac_type==='1005'

							else if (ac_type.startsWith("1003")) {

								System.out
										.println("##########Entering into FD Closure######### Sumanth");

								DepositMasterObject dep_close = tddelegate
										.getClosureInfo(
												td_closure.getAc_type(),
												td_closure.getAc_no(), false);
								System.out
										.println("ho please see this if it scorrect===> "
												+ dep_close.getInterestPaid());

								if (dep_close.getCloseInd() != 0
										&& dep_close.getCloseInd() < 6) {
									td_closure
											.setTesting("Account Already Closed");
								}
								/*
								 * else if(dep_close.getCloseInd()==0) {
								 * td_closure.setTesting("Permission Denied");
								 * 
								 * }
								 */
								else if (dep_close.getVerified().length() == 0) {
									td_closure
											.setTesting("Account Not Yet Verified");

								}

								System.out.println("loan availed===="
										+ dep_close.getLoanAvailed());
								System.out.println("==TransferAccType=="
										+ dep_close.getTransferAccType());
								System.out.println("==TransferAccno=="
										+ dep_close.getTransferAccno());
								System.out.println("FD Day Compare==> "
										+ Validations.dayCompare(dep_close
												.getMaturityDate(), TDDelegate
												.getSysdate()));

								if (dep_close.getTransferAccType() != null) {
									System.out
											.println("I am inside TransferAccno");
									td_closure.setCombo_pay_acno(String
											.valueOf(dep_close
													.getTransferAccno()));
								}
								DepositMasterObject dep_close_reinvest[] = new DepositMasterObject[1];

								dep_close_reinvest[0] = new DepositMasterObject();

								dep_close_reinvest = tddelegate
										.getReinvestmentDetails(td_closure
												.getAc_type(), td_closure
												.getAc_no());

								System.out.println("loan availed===="
										+ dep_close_reinvest[0]
												.getLoanAvailed());

								if (dep_close_reinvest[0].getCloseInd() != 0
										&& dep_close_reinvest[0].getCloseInd() < 6) // ||dm[0].getCloseInd()==2||dm[0].getCloseInd()==3)
								{
									td_closure
											.setTesting("Account Already Closed");

								}
								/*
								 * else
								 * if(dep_close_reinvest[0].getCloseInd()==0) {
								 * td_closure.setTesting("Permission Denied");
								 * 
								 * }
								 */
								else if (dep_close_reinvest[0].getVerified()
										.length() == 0) {
									td_closure
											.setTesting("Account Not Yet Verified");

								}
								if ((dep_close_reinvest[0].getCloseInd() == 90
										|| dep_close_reinvest[0].getCloseInd() == 91 || dep_close_reinvest[0]
										.getCloseInd() == 92)) {

									td_closure
											.setAlertdispay("Account Closed But Not Yet Verified");
									req.setAttribute("Verifyvalue", 2);
									// button_update.setEnabled(true);

								}

								if (Validations.dayCompare(dep_close
										.getMaturityDate(), TDDelegate
										.getSysdate()) > 0) {

									if ((dep_close_reinvest[0].getCloseInd() == 90
											|| dep_close_reinvest[0]
													.getCloseInd() == 91 || dep_close_reinvest[0]
											.getCloseInd() == 92)) {

										td_closure
												.setAlertdispay("Account Closed But Not Yet Verified");
										req.setAttribute("Verifyvalue", 2);
										// button_update.setEnabled(true);
										req.setAttribute("boolflag", 2);
									} else {
										req.setAttribute("Verifyvalue", 1);
									}

									int int_no_of_days;
									double double_interest_amount = 0;
									System.out
											.println("^^^^^^^^^^^^^^inside normal closure^^^^^^^^^^^^^^^^");
									if (dep_close.getLoanAvailed()
											.equalsIgnoreCase("T")) {

										td_closure.setLoantrue("LoanTrue");

										System.out
												.println("LoanTrue value------>>>"
														+ td_closure
																.getLoantrue());

										System.out
												.println("if loan availed true==="
														+ dep_close
																.getLoanAcType()
														+ "and loan ac_no"
														+ dep_close
																.getLoanAccno());

										System.out
												.println("days in normal closure=="
														+ Validations
																.dayCompare(
																		dep_close
																				.getMaturityDate(),
																		TDDelegate
																				.getSysdate()));

										req
												.setAttribute(
														"DepositTran",
														tddelegate
																.getClosureInfo(
																		td_closure
																				.getAc_type(),
																		td_closure
																				.getAc_no(),
																		false));

										System.out.println("loan availed-----"
												+ dep_close.getLoanAvailed());

										System.out.println("loan availed===T");
										System.out.println("loan actype=="
												+ dep_close.getLoanAcType()
												+ "loan ac_no"
												+ dep_close.getLoanAccno());

										dep_close.setLoanAcType(td_closure
												.getLoan_ac_type());
										dep_close.setLoanAccno(td_closure
												.getLoan_ac_no());

									} else {

										System.out
												.println("days in normal closure=="
														+ Validations
																.dayCompare(
																		dep_close
																				.getMaturityDate(),
																		TDDelegate
																				.getSysdate()));
										System.out
												.println("Interest Applied ==> "
														+ dep_close
																.getInterestRate());
										td_closure
												.setApplied_int_rate(dep_close
														.getInterestRate());
										int_no_of_days = tddelegate
												.getDaysFromTwoDate(
														dep_close.getDepDate(),
														dep_close
																.getMaturityDate());
										// fre_ind is the frequency indicator...
										// Code added by sanjeet..
										System.out
												.println("I am in calculation Process.normal closure...."
														+ int_no_of_days);
										int fre_ind = 0;
										if (dep_close.getInterestFrq()
												.equalsIgnoreCase("M"))
											fre_ind = 1;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Q"))
											fre_ind = 3;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("H"))
											fre_ind = 6;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Y"))
											fre_ind = 12;
										else {
											double_interest_amount = Math
													.round((dep_close
															.getDepositAmt()
															* int_no_of_days * dep_close
															.getInterestRate()) / 36500);
											System.out
													.println("double double_interest_amount===> "
															+ double_interest_amount);
											td_closure
													.setInt_amt_pay(double_interest_amount
															- dep_close
																	.getInterestPaid());

											double interest_amount_payable = (Math
													.round(double_interest_amount
															- dep_close
																	.getInterestPaid()));
											System.out
													.println("yappa payable===> "
															+ ((dep_close
																	.getDepositAmt()) + interest_amount_payable));
											td_closure.setTotal_amt((dep_close
													.getDepositAmt())
													+ interest_amount_payable);
											System.out
													.println("in normal closureeeee---"
															+ interest_amount_payable);

										}
										if (fre_ind != 0) {
											String int_upto_date = Validations
													.addDays(dep_close
															.getDepDate(), -1);
											System.out.println("date1 "
													+ int_upto_date);

											StringTokenizer st = new StringTokenizer(
													int_upto_date, "/");
											int D = Integer.parseInt(st
													.nextToken());
											int M = Integer.parseInt(st
													.nextToken()) - 1;
											int Y = Integer.parseInt(st
													.nextToken());

											GregorianCalendar grgcal = new GregorianCalendar(
													Y, M, D);
											String temp_int_upto_date = null, maturity_date = Validations
													.addDays(dep_close
															.getMaturityDate(),
															-1);
											int days = 0;
											while (Validations.dayCompare(
													int_upto_date,
													maturity_date) > 0) {
												grgcal.add(Calendar.MONTH,
														fre_ind);
												temp_int_upto_date = Validations
														.checkDate(grgcal
																.get(Calendar.DATE)
																+ "/"
																+ (grgcal
																		.get(Calendar.MONTH) + 1)
																+ "/"
																+ grgcal
																		.get(Calendar.YEAR));
												System.out
														.println("temp int_upto_date"
																+ temp_int_upto_date);
												System.out
														.println("maturity date"
																+ maturity_date);
												if (Validations.dayCompare(
														temp_int_upto_date,
														maturity_date) < 0)
													temp_int_upto_date = maturity_date;
												System.out
														.println(" Murugesh 5 no of days:"
																+ Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date));
												double_interest_amount += Math
														.round((dep_close
																.getDepositAmt()
																* Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date) * dep_close
																.getInterestRate()) / 36500);
												System.out
														.println(" amount=>"
																+ double_interest_amount);
												if (temp_int_upto_date
														.equalsIgnoreCase(maturity_date))
													break;
												int_upto_date = temp_int_upto_date;
											}
											System.out
													.println(" amount 999 ===>>>"
															+ double_interest_amount);
											if (Validations
													.dayCompare(
															dep_close
																	.getInterestUpto(),
															dep_close
																	.getMaturityDate()) != 1) {

												System.out
														.println("Yapo yappa==> "
																+ (double_interest_amount - dep_close
																		.getInterestPaid()));
												td_closure
														.setInt_amt_pay(double_interest_amount
																- dep_close
																		.getInterestPaid());
												double interest_amount_payable = (Math
														.round(double_interest_amount
																- dep_close
																		.getInterestPaid()));
												td_closure
														.setInt_amt_paid((dep_close
																.getDepositAmt())
																+ interest_amount_payable);

												System.out
														.println("nooooormal"
																+ interest_amount_payable);
												/*
												 * lbl_interest_amount.setText(String
												 * .valueOf(Math.round(
												 * double_interest_amount
												 * -depositmasterobject
												 * .getInterestPaid())));
												 * //geeth
												 * lbl_total_amount_payable
												 * .setText(String.valueOf((
												 * depositmasterobject
												 * .getDepositAmt
												 * ())+interest_amount_payable
												 * ));
												 */
												System.out
														.println("norrrrr"
																+ String
																		.valueOf((dep_close
																				.getDepositAmt())
																				+ interest_amount_payable));

											} else {
												System.out
														.println("haaaa ia min else block Int_paid 0.0");
												td_closure
														.setInt_amt_paid(0.00);
											}
										}

										DepositMasterObject DepClose = tddelegate
												.getClosureInfo(td_closure
														.getAc_type(),
														td_closure.getAc_no(),
														false);
										if (DepClose != null) {
											if (DepClose.getVerified()
													.equalsIgnoreCase("F")) {
												if (DepClose.getInterestMode()
														.equalsIgnoreCase("C")) {
													req.setAttribute(
															"boolflag", 2);
												}
											} else {
												if ((dep_close_reinvest[0]
														.getCloseInd() == 90
														|| dep_close_reinvest[0]
																.getCloseInd() == 91 || dep_close_reinvest[0]
														.getCloseInd() == 92)) {
													req.setAttribute(
															"boolflag", 2);
													System.out
															.println("The value-----is ===> "
																	+ DepClose
																			.getTrantypetemp());
													System.out
															.println("Normalllll-----> "
																	+ DepClose
																			.getTransferType());
													if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"T")) {
														td_closure
																.setCombo_pay_acno(String
																		.valueOf(DepClose
																				.getTransferAccno()));
														td_closure
																.setPay_ac_type(DepClose
																		.getTransferType());
														td_closure
																.setPay_mode("T");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"C")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("C");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"P")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("P");
													}
													req.setAttribute(
															"DepositTran",
															DepClose);
													// td_closure.setFlag("withoutpenalty");

												} else {
													req.setAttribute(
															"boolflag", 1);
													td_closure
															.setCombo_pay_acno(String
																	.valueOf(DepClose
																			.getTransferAccno()));
													td_closure
															.setPay_ac_type(DepClose
																	.getTransferAccType());
													td_closure
															.setPay_mode(DepClose
																	.getInterestMode());
													req.setAttribute(
															"DepositTran",
															DepClose);
													// td_closure.setFlag("withoutpenalty");
												}
											}
										}

										// req.setAttribute("DepositTran",tddelegate.getClosureInfo(td_closure.getAc_type(),
										// td_closure.getAc_no(), false));

										System.out.println("loan availed-----"
												+ dep_close.getLoanAvailed());

										// dep_close.setDepositPaid(0);
										// dep_close.setInterestPaid(0);
									}

									System.out.println("dep_date=="
											+ td_closure.getDep_date()
											+ "closure_date---"
											+ td_closure.getClosure_date());

									System.out
											.println("************before getnormalclosure*********");

									// req.setAttribute("normal",tddelegate.getnormalclosure(ac_type,
									// ac_no, 90));

									System.out.println("day compare days==="
											+ Validations.dayCompare(td_closure
													.getMat_date(), td_closure
													.getClosure_date()));

									td_closure.setFlag("NormalClosure");

								}
								// added by sumanth on 04/06/09 foe
								// prematurityclosure with and with penality
								else if (Validations.dayCompare(dep_close
										.getMaturityDate(), TDDelegate
										.getSysdate()) < 0) {

									if ((dep_close_reinvest[0].getCloseInd() == 90
											|| dep_close_reinvest[0]
													.getCloseInd() == 91 || dep_close_reinvest[0]
											.getCloseInd() == 92)) {

										td_closure
												.setAlertdispay("Account Closed But Not Yet Verified");
										req.setAttribute("Verifyvalue", 2);
										req.setAttribute("boolflag", 2);
										// button_update.setEnabled(true);

									} else {
										req.setAttribute("Verifyvalue", 1);
									}
									System.out
											.println("hi i am in else block with or with out penality block FDClosure");

									/**
									 * This is premature closure with or with
									 * out penalty. if with penalty get penalty
									 * rate from modules table. Get interest
									 * rate from getDepoistInterestRate for
									 * interest calculation set close ind w.r.o
									 * closure type.
									 */
									// lbl_interest_amount_paid.setText(String.valueOf(depositmasterobject.getInterestPaid()));

									td_closure.setInt_amt_paid(dep_close
											.getInterestPaid());
									double double_interest_amount = 0;
									int int_no_of_days = 0;
									string_today_date = tddelegate.getSysdate();
									int_no_of_days = tddelegate
											.getDaysFromTwoDate(dep_close
													.getDepDate(),
													string_today_date);
									double double_interest_rate = 0;
									System.out.println(">> int_no_of_days "
											+ int_no_of_days);
									double array_double_interest_rate[] = tddelegate
											.getDepositInterestRate(
													ac_type,
													dep_close.getDPType(),
													dep_close.getCategory(),
													Validations
															.convertYMD(dep_close
																	.getDepDate()),
													int_no_of_days, dep_close
															.getDepositAmt());
									double_interest_rate = array_double_interest_rate[0];
									// double_interest_rate=depositmasterobject.getInterestRate();
									ModuleObject array_moduleobject_fd[] = null;
									array_moduleobject_fd = tddelegate
											.getMainModules(3, ac_type);
									System.out.println(ac_type + "===00== "
											+ int_no_of_days + "===oo== "
											+ dep_close.getDepDate());
									System.out
											.println(">> depositmasterobject.getExtraRateType() "
													+ dep_close
															.getExtraRateType()
													+ "\n "
													+ dep_close.getCategory()
													+ "\n"
													+ array_double_interest_rate[2]
													+ "\n"
													+ double_interest_rate);
									if (dep_close.getExtraRateType() == 2)
										double_interest_rate += array_double_interest_rate[1];
									else if (dep_close.getExtraRateType() == 3
											|| dep_close.getCategory() == 202)
										double_interest_rate += array_double_interest_rate[2];
									else if (dep_close.getExtraRateType() == 4)
										double_interest_rate += array_double_interest_rate[1]
												+ array_double_interest_rate[2];

									Object array_object[] = { "With Fine",
											"Without Fine" };
									int int_result_value = JOptionPane
											.showOptionDialog(
													null,
													"Premature Closure !!",
													null,
													JOptionPane.YES_NO_OPTION,
													JOptionPane.QUESTION_MESSAGE,
													null, array_object,
													array_object[0]);
									if (int_result_value == JOptionPane.YES_OPTION) {
										/**
										 * With penalty
										 */
										System.out.println("rate==>"
												+ array_moduleobject_fd[0]
														.getPenaltyRate());
										System.out
												.println("double_int_rate==>>"
														+ double_interest_rate
														+ " "
														+ array_moduleobject_fd[0]
																.getPenaltyRate());
										double_interest_rate -= array_moduleobject_fd[0]
												.getPenaltyRate();
										if (double_interest_rate < 0)
											double_interest_rate = 0;

										System.out
												.println("int_rate after penalty"
														+ double_interest_rate);
										// lbl_applied_inrerest_rate.setText(String.valueOf(double_interest_rate));
										td_closure
												.setApplied_int_rate(double_interest_rate);
										// Code added by sanjeet..
										System.out
												.println("I am in calculation Process:"
														+ dep_close
																.getInterestFrq());
										int fre_ind = 0;
										if (dep_close.getInterestFrq()
												.equalsIgnoreCase("M"))
											fre_ind = 1;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Q"))
											fre_ind = 3;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("H"))
											fre_ind = 6;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Y"))
											fre_ind = 12;
										else {
											double_interest_amount = Math
													.round((dep_close
															.getDepositAmt()
															* int_no_of_days * double_interest_rate) / 36500);
											/*
											 * if(depositmasterobject.getInterestPaid
											 * ()>double_interest_amount)
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid()))); else
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid())));
											 */
											if (dep_close.getInterestPaid() == 0) {
												System.out
														.println("Int_amt_paid===> "
																+ double_interest_amount);
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount)));

												td_closure
														.setInt_amt_pay(double_interest_amount);
											} else {
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject.getInterestPaid())));
												System.out
														.println("Int_amt_paid in else ===> "
																+ (double_interest_amount - dep_close
																		.getInterestPaid()));
												td_closure
														.setInt_amt_pay(double_interest_amount
																- dep_close
																		.getInterestPaid());
											}

											System.out
													.println("penalty int rate***************:"
															+ double_interest_amount);
										}

										if (fre_ind != 0) {
											String int_upto_date = Validations
													.addDays(dep_close
															.getDepDate(), -1);
											System.out.println("date1 "
													+ int_upto_date);

											StringTokenizer st = new StringTokenizer(
													int_upto_date, "/");
											int D = Integer.parseInt(st
													.nextToken());
											int M = Integer.parseInt(st
													.nextToken()) - 1;
											int Y = Integer.parseInt(st
													.nextToken());

											GregorianCalendar grgcal = new GregorianCalendar(
													Y, M, D);
											String temp_int_upto_date = null, maturity_date = Validations
													.addDays(dep_close
															.getMaturityDate(),
															-1), close_date = tddelegate
													.getSysdate();
											while (Validations.dayCompare(
													int_upto_date, close_date) > 0) {
												grgcal.add(Calendar.MONTH,
														fre_ind);
												temp_int_upto_date = Validations
														.checkDate(grgcal
																.get(Calendar.DATE)
																+ "/"
																+ (grgcal
																		.get(Calendar.MONTH) + 1)
																+ "/"
																+ grgcal
																		.get(Calendar.YEAR));
												System.out
														.println("temp int_upto_date"
																+ temp_int_upto_date);
												System.out.println("close date"
														+ close_date);
												System.out
														.println(" day comp "
																+ double_interest_amount);
												if (Validations.dayCompare(
														temp_int_upto_date,
														close_date) <= 0) {
													temp_int_upto_date = close_date;
													temp_int_upto_date = Validations
															.addDays(
																	temp_int_upto_date,
																	-1);
												}
												System.out
														.println(" Murugesh 2 no of days:"
																+ Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date));
												double_interest_amount += Math
														.round((dep_close
																.getDepositAmt()
																* Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date) * double_interest_rate) / 36500);
												System.out
														.println(" amount=>"
																+ double_interest_amount);
												if (temp_int_upto_date
														.equalsIgnoreCase(Validations
																.addDays(
																		close_date,
																		-1)))
													break;
												int_upto_date = temp_int_upto_date;
											}
											System.out
													.println(" amount 6666 ===>>>"
															+ double_interest_amount);
											/*
											 * if(depositmasterobject.getInterestPaid
											 * ()>double_interest_amount)
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid()))); else
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid())));
											 */

											System.out
													.println("TRhe value of int paid==> "
															+ dep_close
																	.getInterestPaid());
											if (dep_close.getInterestPaid() == 0) {

												double interest_amount_payable = (Math
														.round(double_interest_amount
																- dep_close
																		.getInterestPaid()));
												System.out
														.println("saaaaaa"
																+ interest_amount_payable);
												// lbl_total_amount_payable.setText(String.valueOf((dep_close.getDepositAmt())+interest_amount_payable));
												System.out
														.println("ho please stop this===> "
																+ ((dep_close
																		.getDepositAmt()) + interest_amount_payable));
												td_closure
														.setTotal_amt((dep_close
																.getDepositAmt())
																+ interest_amount_payable);
												System.out
														.println("riiiiiii"
																+ String
																		.valueOf((dep_close
																				.getDepositAmt())
																				+ interest_amount_payable));
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount)));
												System.out
														.println("Hmmm ya right===> "
																+ double_interest_amount);
												td_closure
														.setInt_amt_pay(double_interest_amount);
											} else {
												// geeetha
												double interest_amount_payable = (Math
														.round(double_interest_amount
																- dep_close
																		.getInterestPaid()));
												System.out
														.println("saaaaaa"
																+ interest_amount_payable);
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject.getInterestPaid())));
												System.out
														.println("Lololol ===> "
																+ (double_interest_amount - dep_close
																		.getInterestPaid()));
												// here is the problem...
												System.out
														.println("double_interest_amount===> "
																+ double_interest_amount);
												System.out
														.println("dep_close.getInterestPaid()===> "
																+ dep_close
																		.getInterestPaid());
												td_closure
														.setInt_amt_pay(double_interest_amount
																- dep_close
																		.getInterestPaid());
												// lbl_total_amount_payable.setText(String.valueOf((depositmasterobject.getDepositAmt())+interest_amount_payable));
												td_closure
														.setTotal_amt((dep_close
																.getDepositAmt())
																+ interest_amount_payable);
												System.out
														.println("BeboooBeboo ===> "
																+ ((dep_close
																		.getDepositAmt()) + interest_amount_payable));
												// td_closure.setInt_amt_pay((dep_close.getDepositAmt())+interest_amount_payable);
												System.out
														.println("riiiiiii"
																+ String
																		.valueOf((dep_close
																				.getDepositAmt())
																				+ interest_amount_payable));

											}

										}

										DepositMasterObject DepClose = tddelegate
												.getClosureInfo(td_closure
														.getAc_type(),
														td_closure.getAc_no(),
														false);
										if (DepClose != null) {

											if (DepClose.getVerified()
													.equalsIgnoreCase("F")) {
												if (DepClose.getInterestMode()
														.equalsIgnoreCase("C")) {
													req.setAttribute(
															"boolflag", 2);
												}
											} else {
												if ((dep_close_reinvest[0]
														.getCloseInd() == 90
														|| dep_close_reinvest[0]
																.getCloseInd() == 91 || dep_close_reinvest[0]
														.getCloseInd() == 92)) {
													req.setAttribute(
															"boolflag", 2);

													System.out
															.println("DepClose.getInterestMode()------> "
																	+ DepClose
																			.getTransferType());
													if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"T")) {
														td_closure
																.setCombo_pay_acno(String
																		.valueOf(DepClose
																				.getTransferAccno()));
														td_closure
																.setPay_ac_type(DepClose
																		.getTransferAccType());
														td_closure
																.setPay_mode("T");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"C")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("C");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"G")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("G");
													}
													req.setAttribute(
															"DepositTran",
															DepClose);
													td_closure
															.setFlag("WithPenalty");

												} else {
													req.setAttribute(
															"boolflag", 1);
													td_closure
															.setCombo_pay_acno(String
																	.valueOf(DepClose
																			.getTransferAccno()));
													td_closure
															.setPay_ac_type(DepClose
																	.getTransferAccType());
													td_closure
															.setPay_mode(DepClose
																	.getInterestMode());
													req.setAttribute(
															"DepositTran",
															DepClose);
													td_closure
															.setFlag("WithPenalty");
												}
											}
										}

										// req.setAttribute("DepositTran",tddelegate.getClosureInfo(td_closure.getAc_type(),
										// td_closure.getAc_no(), false));
										td_closure.setFlag("WithPenalty");
										// int_closeind=91;
									} else {
										/**
										 * with out penalty
										 */
										// lbl_applied_inrerest_rate.setText(String.valueOf(double_interest_rate));
										System.out
												.println("double_interest_rate in else===> "
														+ double_interest_rate);
										td_closure
												.setApplied_int_rate(double_interest_rate);
										// Code added by sanjeet..
										System.out
												.println("I am in calculation Process....without penalty!!!");
										int fre_ind = 0;
										if (dep_close.getInterestFrq()
												.equalsIgnoreCase("M"))
											fre_ind = 1;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Q"))
											fre_ind = 3;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("H"))
											fre_ind = 6;
										else if (dep_close.getInterestFrq()
												.equalsIgnoreCase("Y"))
											fre_ind = 12;
										else {
											fre_ind = 0;
											System.out.println("interest rate"
													+ dep_close
															.getInterestRate());
											double_interest_amount = Math
													.round((dep_close
															.getDepositAmt()
															* int_no_of_days * double_interest_rate) / 36500);
											/*
											 * if(depositmasterobject.getInterestPaid
											 * ()>double_interest_amount)
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid()))); else
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid())));
											 */
											if (dep_close.getInterestPaid() == 0) {
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount)));
												td_closure
														.setInt_amt_pay(double_interest_amount);
											} else {
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject.getInterestPaid())));
												td_closure
														.setInt_amt_pay(double_interest_amount
																- dep_close
																		.getInterestPaid());
											}

										}

										if (fre_ind != 0) {
											String int_upto_date = Validations
													.addDays(dep_close
															.getDepDate(), -1);
											System.out.println("date1 "
													+ int_upto_date);

											StringTokenizer st = new StringTokenizer(
													int_upto_date, "/");
											int D = Integer.parseInt(st
													.nextToken());
											int M = Integer.parseInt(st
													.nextToken()) - 1;
											int Y = Integer.parseInt(st
													.nextToken());

											GregorianCalendar grgcal = new GregorianCalendar(
													Y, M, D);
											String temp_int_upto_date = null, maturity_date = Validations
													.addDays(dep_close
															.getMaturityDate(),
															-1);
											if (Validations.dayCompare(
													tddelegate.getSysdate(),
													maturity_date) > 0) {
												maturity_date = Validations
														.addDays(tddelegate
																.getSysdate(),
																-1);
											}
											int days = 0;
											while (Validations.dayCompare(
													int_upto_date,
													maturity_date) > 0) {
												grgcal.add(Calendar.MONTH,
														fre_ind);
												temp_int_upto_date = Validations
														.checkDate(grgcal
																.get(Calendar.DATE)
																+ "/"
																+ (grgcal
																		.get(Calendar.MONTH) + 1)
																+ "/"
																+ grgcal
																		.get(Calendar.YEAR));
												System.out
														.println(" int_upto_date"
																+ int_upto_date);
												if (Validations.dayCompare(
														temp_int_upto_date,
														maturity_date) < 0) {
													System.out
															.println("maturity date"
																	+ maturity_date);
													temp_int_upto_date = maturity_date;
												}
												System.out
														.println("temp int_upto_date"
																+ temp_int_upto_date);
												System.out
														.println(" Murugesh 3 no of days:"
																+ Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date));
												System.out
														.println("checking th value....."
																+ double_interest_amount);
												System.out
														.println("interest rate...."
																+ double_interest_rate);
												double_interest_amount += Math
														.round((dep_close
																.getDepositAmt()
																* Validations
																		.dayCompare(
																				int_upto_date,
																				temp_int_upto_date) * double_interest_rate) / 36500);
												System.out
														.println(" amount=>"
																+ double_interest_amount);
												if (temp_int_upto_date
														.equalsIgnoreCase(maturity_date))
													break;
												int_upto_date = temp_int_upto_date;
											}
											System.out
													.println(" amount 88888 ===>>>"
															+ double_interest_amount);
											/*
											 * if(depositmasterobject.getInterestPaid
											 * ()>double_interest_amount)
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid()))); else
											 * lbl_interest_amount
											 * .setText(String
											 * .valueOf(Math.round
											 * (double_interest_amount
											 * -depositmasterobject
											 * .getInterestPaid())));
											 */
											if (dep_close.getInterestPaid() == 0) {
												// geetha on 31/jan/08
												double interest_amount_payable = Math
														.round(double_interest_amount);
												System.out
														.println("gaaaa"
																+ interest_amount_payable);
												// lbl_total_amount_payable.setText(String.valueOf((dep_close.getDepositAmt())+interest_amount_payable));
												td_closure
														.setTotal_amt((dep_close
																.getDepositAmt())
																+ interest_amount_payable);
												System.out
														.println("maaaa"
																+ String
																		.valueOf((dep_close
																				.getDepositAmt())
																				+ interest_amount_payable));

												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount)));
												td_closure
														.setInt_amt_pay(double_interest_amount);
											} else {
												double interest_amount_payable = (Math
														.round(double_interest_amount
																- dep_close
																		.getInterestPaid()));
												// lbl_total_amount_payable.setText(String.valueOf((depositmasterobject.getDepositAmt())+interest_amount_payable));
												td_closure
														.setTotal_amt((dep_close
																.getDepositAmt())
																+ interest_amount_payable);
												System.out
														.println("gaaaa"
																+ interest_amount_payable);
												// lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject.getInterestPaid())));
												td_closure
														.setInt_amt_pay((double_interest_amount - dep_close
																.getInterestPaid()));
											}

										}

										DepositMasterObject DepClose = tddelegate
												.getClosureInfo(td_closure
														.getAc_type(),
														td_closure.getAc_no(),
														false);
										if (DepClose != null) {

											if (DepClose.getVerified()
													.equalsIgnoreCase("F")) {
												if (DepClose.getInterestMode()
														.equalsIgnoreCase("C")) {
													req.setAttribute(
															"boolflag", 2);
												}
											} else {
												if ((dep_close_reinvest[0]
														.getCloseInd() == 90
														|| dep_close_reinvest[0]
																.getCloseInd() == 91 || dep_close_reinvest[0]
														.getCloseInd() == 92)) {
													req.setAttribute(
															"boolflag", 2);

													System.out
															.println("Hooo===>"
																	+ DepClose
																			.getTransferType());
													System.out
															.println("TEMPVAL:UE-----> "
																	+ DepClose
																			.getTrantypetemp());

													if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"T")) {
														td_closure
																.setCombo_pay_acno(String
																		.valueOf(DepClose
																				.getTransferAccno()));
														td_closure
																.setPay_ac_type(DepClose
																		.getTransferAccType());
														td_closure
																.setPay_mode("T");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"C")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("C");
													} else if (DepClose
															.getTrantypetemp()
															.equalsIgnoreCase(
																	"G")) {
														td_closure
																.setPay_ac_type("");
														td_closure
																.setCombo_pay_acno("");
														td_closure
																.setPay_mode("G");
													}
													req.setAttribute(
															"DepositTran",
															DepClose);
													td_closure
															.setFlag("WithoutPenalty");
												}

												else {
													req.setAttribute(
															"boolflag", 1);
													td_closure
															.setCombo_pay_acno(String
																	.valueOf(DepClose
																			.getTransferAccno()));
													td_closure
															.setPay_ac_type(DepClose
																	.getTransferAccType());
													td_closure
															.setPay_mode(DepClose
																	.getInterestMode());
													req.setAttribute(
															"DepositTran",
															DepClose);
													td_closure
															.setFlag("WithoutPenalty");
												}
											}
										}

										// req.setAttribute("DepositTran",tddelegate.getClosureInfo(td_closure.getAc_type(),
										// td_closure.getAc_no(), false));
										td_closure.setFlag("WithoutPenalty");
										// int_closeind=92;
									}

								}
								// ////end of with and with out penality
							}
							// end of FD

							else if (ac_type.startsWith("1004")) {

								System.out
										.println("Inside ReeeeeeeeeeeeeddddddDD");
								String TodayDate = tddelegate.getSysdate();
								int option1 = 0;
								String string_applied_date = null;

								String array_string_close_type[] = {
										"With Penalty", "With Out Penalty" };
								String array_close_type[] = { "Close", "Cancel" };
								int int_total_no_of_days = 0, int_no_of_installment = 0;
								DepositMasterObject array_depositmasterobject[] = null;
								double array_double_interest_rate[], double_interest_rate = 0, double_excess_amount = 0.0;
								DepositTransactionObject dep[] = null;
								String array_string_date[];
								double array_double_amount[], double_total_rdinterestamount = 0.0, double_total_installment_amount = 0.0;
								int int_excess_days;

								array_depositmasterobject = tddelegate
										.getRDClosureDetails(td_closure
												.getAc_no(), td_closure
												.getAc_type(), false);
								if (array_depositmasterobject[0] == null) {
									td_closure
											.setTesting("Records Not Found!!!!");
								}

								else if (array_depositmasterobject[0]
										.getCloseInd() != 0
										&& array_depositmasterobject[0]
												.getCloseInd() < 6) // ||dm[0].getCloseInd()==2||dm[0].getCloseInd()==3)
								{
									JOptionPane.showMessageDialog(null,
											"Account Already Closed");

								}
								/*
								 * else
								 * if(array_depositmasterobject[0].getCloseInd
								 * ()==0) {JOptionPane.showMessageDialog(null,
								 * "Permission Denied");
								 * 
								 * }
								 */
								else if (array_depositmasterobject[0]
										.getVerified().length() == 0) {
									JOptionPane.showMessageDialog(null,
											"Account Not Yet Verified");

								}

								else {
									dep = tddelegate.getRDReBal(
											array_depositmasterobject[0]
													.getAccType(), td_closure
													.getAc_no());

									array_string_date = new String[array_depositmasterobject.length];
									int_no_of_installment = array_depositmasterobject[0]
											.getDepositDays();
									System.out
											.println("int_no_of_installment===> "
													+ int_no_of_installment);

									td_closure
											.setInst_amt(array_depositmasterobject[0]
													.getDepositAmt());
									td_closure
											.setAmt_collected(dep[dep.length - 1]
													.getRDBalance()
													- dep[dep.length - 1]
															.getCumInterest());
									td_closure
											.setAgreed_int_rate(array_depositmasterobject[0]
													.getInterestRate());
									double_total_installment_amount = int_no_of_installment
											* array_depositmasterobject[0]
													.getDepositAmt();
									System.out
											.println("double_total_installment_amount----> "
													+ double_total_installment_amount);
									array_double_amount = new double[array_depositmasterobject.length];

									array_string_date = new String[array_depositmasterobject.length];
									int k = 0;
									for (int i = 1; i < array_depositmasterobject.length; i++) {
										array_double_amount[k] = array_depositmasterobject[i]
												.getTranAmt();
										array_string_date[k] = array_depositmasterobject[i]
												.getTranDate();
										k++;
									}
									array_double_amount[k] = 0.0;
									int option = 0;
									System.out
											.println("array_depositmasterobject[0].getDepositAmt() "
													+ array_depositmasterobject[0]
															.getDepositAmt());
									System.out
											.println("array_depositmasterobject[0].getDepDate() "
													+ array_depositmasterobject[0]
															.getDepDate());
									System.out
											.println("array_depositmasterobject[0].getMaturityDate() "
													+ array_depositmasterobject[0]
															.getMaturityDate());
									System.out
											.println("array_double_amount---> "
													+ array_double_amount);
									System.out
											.println("array_string_date-----> "
													+ array_string_date);
									int_excess_days = Validations
											.rdMaturityDate(
													array_depositmasterobject[0]
															.getDepositAmt(),
													array_depositmasterobject[0]
															.getDepDate(),
													array_depositmasterobject[0]
															.getMaturityDate(),
													tddelegate.getSysdate(),
													array_double_amount,
													array_string_date);

									System.out
											.println("----int_excess_days-------->>>>>***** "
													+ int_excess_days);
									array_string_date[k] = array_depositmasterobject[0]
											.getMaturityDate();
									int_total_no_of_days = tddelegate
											.getDaysFromTwoDate(
													tddelegate.getSysdate(),
													array_depositmasterobject[0]
															.getMaturityDate());

									System.out
											.println("---int_total_no_of_days---- "
													+ int_total_no_of_days);
									System.out
											.println("dep[dep.length-1].getRDBalance() "
													+ dep[dep.length - 1]
															.getRDBalance());
									System.out
											.println("dep[dep.length-1].getCumInterest() "
													+ dep[dep.length - 1]
															.getCumInterest());
									System.out
											.println("total collected amount"
													+ (dep[dep.length - 1]
															.getRDBalance() - array_depositmasterobject[0]
															.getCumInterest()));
									System.out
											.println("double_total_installment_amount "
													+ double_total_installment_amount);
									// changed by sumanth 04/06/09 for testing
									// from || or &&
									if (int_total_no_of_days > 0
											|| (dep[dep.length - 1]
													.getRDBalance() - dep[dep.length - 1]
													.getCumInterest()) < double_total_installment_amount) {
										System.out
												.println("Ho my god its nhhgggf0000000000000000");

										array_double_amount[k] = 0.0;
										array_string_date[k] = tddelegate
												.getSysdate();
										int int_total_days = tddelegate
												.getDaysFromTwoDate(
														array_depositmasterobject[0]
																.getDepDate(),
														tddelegate.getSysdate());
										System.out.println("int_total_days"
												+ int_total_days);
										array_double_interest_rate = tddelegate
												.getDepositInterestRate(
														td_closure.getAc_type(),
														array_depositmasterobject[0]
																.getDPType(),
														array_depositmasterobject[0]
																.getCategory(),
														Validations
																.convertYMD(array_depositmasterobject[0]
																		.getDepDate()),
														int_total_days,
														array_depositmasterobject[0]
																.getDepositAmt());
										double_interest_rate = array_double_interest_rate[0];
										System.out
												.println("Normail day compare===> "
														+ Validations
																.dayCompare(
																		string_today_date,
																		tddelegate
																				.getSysdate()));
										if (array_depositmasterobject[0]
												.getExtraRateType() == 2)
											double_interest_rate += array_double_interest_rate[1];
										else if (array_depositmasterobject[0]
												.getExtraRateType() == 3)
											double_interest_rate += array_double_interest_rate[2];
										else if (array_depositmasterobject[0]
												.getExtraRateType() == 4)
											double_interest_rate += array_double_interest_rate[1]
													+ array_double_interest_rate[2];
										option = -1;
										System.out
												.println("int_total_no_of_days "
														+ int_total_no_of_days);
										if (int_total_no_of_days > 0) {
											option = JOptionPane
													.showOptionDialog(
															null,
															"Before the Maturity Date ",
															"RD PreMature Closure",
															JOptionPane.YES_NO_OPTION,
															JOptionPane.QUESTION_MESSAGE,
															null,
															array_string_close_type,
															array_string_close_type[0]);

											System.out.println("9090909-----> "
													+ option);
										}

										else if (Validations.dayCompare(
												string_today_date, tddelegate
														.getSysdate()) >= 0) {
											JOptionPane.showMessageDialog(null,
													"Normal Closure");
											System.out
													.println("Option sumanth for normal---->"
															+ option1);
											System.out.println("Option---===> "
													+ option);
											System.out
													.println("ooooption 1-----> "
															+ option);
										} else if ((dep[dep.length - 1]
												.getRDBalance() - dep[dep.length - 1]
												.getCumInterest()) < double_total_installment_amount) {
											System.out
													.println("ooooption 2-----> "
															+ option);
											// geeta
											System.out
													.println("RDBalance() value..."
															+ (dep[dep.length - 1]
																	.getRDBalance() - dep[dep.length - 1]
																	.getCumInterest()));
											System.out
													.println("double_total_installment_amount"
															+ double_total_installment_amount);
											option = JOptionPane
													.showOptionDialog(
															null,
															"Shortage of Installment Amount",
															"RD PreMature Closure",
															JOptionPane.YES_NO_OPTION,
															JOptionPane.QUESTION_MESSAGE,
															null,
															array_string_close_type,
															array_string_close_type[0]);
										}

										// else if(option ==
										// JOptionPane.YES_OPTION ) is chnaged
										// to

										// //////////

										if (option == JOptionPane.YES_OPTION) {
											/**
											 * with penalty
											 */
											try {
												System.out
														.println("Inside With Penalty yes option----->");
												ModuleObject array_moduleobject_moduleinfo[] = tddelegate
														.getMainModules(
																3,
																td_closure
																		.getAc_type());
												double_interest_rate = double_interest_rate
														- array_moduleobject_moduleinfo[0]
																.getPenaltyRate();
												if (double_interest_rate < 0)
													double_interest_rate = 0;
												System.out
														.println("interest rate== SUMANTH >>"
																+ double_interest_rate);

												System.out
														.println("account no inside penalty..."
																+ dep[0]
																		.getAccNo());
												System.out
														.println("rd re balance...value 33333.."
																+ dep[0]
																		.getRDBalance());

												double_total_rdinterestamount = Validations
														.rdIntReCal(
																array_depositmasterobject[0]
																		.getDepositAmt(),
																double_interest_rate,
																array_depositmasterobject[0]
																		.getDepDate(),
																tddelegate
																		.getSysdate(),
																array_double_amount,
																array_string_date,
																array_depositmasterobject[0]
																		.getMaturityDate(),
																array_depositmasterobject[0]
																		.getNextPaydt());
												// double_total_rdinterestamount=Validations.rdIntReCal(array_depositmasterobject[0].getDepositAmt(),double_interest_rate,array_depositmasterobject[0].getDepDate(),tddelegate.getSysdate(),array_double_amount,array_string_date,array_depositmasterobject[0].getMaturityDate(),array_depositmasterobject[0].getNextPaydt());
												System.out
														.println(" for the first time double_rdinterestamount==>"
																+ double_total_rdinterestamount
																+ "for ac_no"
																+ dep[dep.length - 1]
																		.getAccNo());
												System.out
														.println("Cum.. interest geeeeeeeeeeeee"
																+ dep[dep.length - 1]
																		.getCumInterest());
												System.out
														.println("dep_amt  ggggggeeeeeeee"
																+ dep[dep.length - 1]
																		.getDepositAmt());
												System.out
														.println(" the values..3 are "
																+ dep[dep.length - 1]
																		.getRDBalance()
																+ " the amt is "
																+ dep[dep.length - 1]
																		.getCumInterest());
												// lbl_interest_amount.setText(DoubleFormat.doubleToString(double_total_rdinterestamount-(dep[dep.length-1].getRDBalance()-dep[dep.length-1].getCumInterest()),2));
												// lbl_interest_amount.setText(DoubleFormat.doubleToString(double_total_rdinterestamount-(dep[dep.length-1].getRDBalance()-dep[dep.length-1].getCumInterest()),2));
												td_closure
														.setInt_amt_pay(double_total_rdinterestamount);
												System.out
														.println("..........with penalty..7...........interest amount "
																+ (double_total_rdinterestamount));
												// int_closeind=91;
												// req.setAttribute("DepositTranRI",tddelegate.getRDClosureDetails(
												// td_closure.getAc_no(),td_closure.getAc_type(),
												// false));

												td_closure
														.setFlag("withpenalty");

											} catch (Exception e) {
												e.printStackTrace();
											}
											td_closure.setFlag("withpenalty");
										} else {

											/**
											 * with out penalty
											 */
											System.out
													.println("With out Penalty------>");
											if (double_interest_rate < 0)
												double_interest_rate = 0;
											System.out
													.println("interest rate== 1111 >>"
															+ double_interest_rate);
											double_total_rdinterestamount = Validations
													.rdIntReCal(
															array_depositmasterobject[0]
																	.getDepositAmt(),
															double_interest_rate,
															array_depositmasterobject[0]
																	.getDepDate(),
															tddelegate
																	.getSysdate(),
															array_double_amount,
															array_string_date,
															array_depositmasterobject[0]
																	.getMaturityDate(),
															array_depositmasterobject[0]
																	.getNextPaydt());
											System.out
													.println("double_rdinterestamount==>"
															+ double_total_rdinterestamount);
											System.out
													.println(" the values...4 are "
															+ dep[dep.length - 1]
																	.getRDBalance()
															+ " the amt is "
															+ dep[dep.length - 1]
																	.getCumInterest());
											// lbl_interest_amount.setText(DoubleFormat.doubleToString(double_total_rdinterestamount-(dep[dep.length-1].getRDBalance()-dep[dep.length-1].getCumInterest()),2));
											td_closure
													.setApplied_int_rate(double_interest_rate);
											td_closure
													.setInt_amt_pay(double_total_rdinterestamount);
											System.out
													.println("........without penatlty....7...........interest amount "
															+ (double_total_rdinterestamount));
											// int_closeind=92;

											td_closure
													.setFlag("withoutpenalty");
										}
										// double_interest_rate = 0;
										System.out
												.println("interest rate== 1111 >>"
														+ double_interest_rate);
										System.out.println("Nextpaydate===> "
												+ array_depositmasterobject[0]
														.getNextPaydt());
										System.out
												.println("array_string_date===> "
														+ array_string_date);
										System.out
												.println("array_double_amount===> "
														+ array_double_amount);
										// double_total_rdinterestamount=Validations.rdIntReCal(array_depositmasterobject[0].getDepositAmt(),array_depositmasterobject[0].getInterestRate(),array_depositmasterobject[0].getDepDate(),tddelegate.getSysdate(),array_double_amount,array_string_date,array_depositmasterobject[0].getMaturityDate(),array_depositmasterobject[0].getNextPaydt());
										System.out
												.println("double_ for the seconf time rdinterestamount==>"
														+ double_total_rdinterestamount);
										System.out
												.println("String.valueOf(double_interest_rate sssssssss===> "
														+ double_interest_rate);
										td_closure
												.setApplied_int_rate(double_interest_rate);
										// lbl_total_amount.setText(String.valueOf(double_total_rdinterestamount));
										System.out
												.println("lbl_total_amount"
														+ double_total_rdinterestamount);
										System.out.println("RDDDD---->"
												+ (dep[dep.length - 1]
														.getRDBalance()));
										System.out.println("hh00000=---> "
												+ dep[dep.length - 1]
														.getCumInterest());
										// lbl_interest_amount.setText(String.valueOf(dep[dep.length-1].getCumInterest()+double_excess_amount));

										td_closure
												.setNet_amt(double_total_rdinterestamount
														+ (dep[dep.length - 1]
																.getRDBalance() - dep[dep.length - 1]
																.getCumInterest()));
										System.out
												.println("InterestRate()===> "
														+ array_depositmasterobject[0]
																.getInterestRate());
										System.out
												.println("CumInterest()===---> "
														+ dep[dep.length - 1]
																.getCumInterest());
										double_excess_amount = ((array_depositmasterobject[0]
												.getInterestRate()) - dep[dep.length - 1]
												.getCumInterest());
										System.out.println("hmmm====> "
												+ double_excess_amount);
										System.out
												.println("dep[dep.length-1].getCumInterest()===> "
														+ dep[dep.length - 1]
																.getCumInterest());
										// uncomment for normal closure int
										// payable value
										// td_closure.setInt_amt_pay(double_total_rdinterestamount);
										req
												.setAttribute(
														"DepositTranRI",
														tddelegate
																.getRDClosureDetails(
																		td_closure
																				.getAc_no(),
																		td_closure
																				.getAc_type(),
																		false));
										td_closure.setFlag("normalclosue");
										System.out.println("---END---END----");

									}

									else if (int_excess_days > 0) {
										System.out
												.println("-----Inside RD With or With out Penality------");
										String maturity_date = tddelegate
												.getFutureDayDate(
														array_depositmasterobject[0]
																.getMaturityDate(),
														int_excess_days);
										option = JOptionPane.showOptionDialog(
												null,
												"Irregular Payment\nMaturity Date:"
														+ maturity_date,
												"RD PreMature Closure",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, array_close_type,
												array_close_type[0]);
										if (option == JOptionPane.YES_OPTION) {
											System.out
													.println("nside int_excess_days YES Block--->");
											// int_closeind=93;
											double_total_rdinterestamount = dep[dep.length - 1]
													.getRDBalance();
											td_closure
													.setAgreed_int_rate(array_depositmasterobject[0]
															.getInterestRate());
											if (double_total_rdinterestamount > array_depositmasterobject[0]
													.getMaturityAmt())
												double_excess_amount = array_depositmasterobject[0]
														.getMaturityAmt()
														- double_total_rdinterestamount;
											else
												double_excess_amount = 0.0;
											td_closure
													.setInt_amt_paid(dep[dep.length - 1]
															.getCumInterest()
															+ double_excess_amount);
											td_closure
													.setTotal_amt(double_total_rdinterestamount
															+ (dep[dep.length - 1]
																	.getRDBalance() - dep[dep.length - 1]
																	.getCumInterest()));
											string_applied_date = tddelegate
													.getSysdate();
											req
													.setAttribute(
															"DepositTranRI",
															tddelegate
																	.getRDClosureDetails(
																			td_closure
																					.getAc_no(),
																			td_closure
																					.getAc_type(),
																			false));

										} else {
											td_closure
													.setTesting("ClearForm()");
										}

									}
									// changed by sumanth from else to else
									// if(int_excess_days<0) for just testing
									else {
										/**
										 * Normal Closure
										 */
										System.out
												.println("-----Inside RD Normal Closure--in el;seeeeee Sumanthkkk----");
										if (option1 == JOptionPane.YES_OPTION
												|| option1 == JOptionPane.NO_OPTION) {
											System.out
													.println("Inside Normal Closure YES BLOCK---->");
											// int_closeind=90;
											System.out
													.println("RDBalance----> "
															+ dep[dep.length - 1]
																	.getRDBalance());
											double_total_rdinterestamount = dep[dep.length - 1]
													.getRDBalance();
											System.out
													.println("InterestRate----> "
															+ array_depositmasterobject[0]
																	.getInterestRate());
											td_closure
													.setApplied_int_rate(array_depositmasterobject[0]
															.getInterestRate());
											System.out
													.println("InterestRate in normal closure"
															+ array_depositmasterobject[0]
																	.getInterestRate());
											if (double_total_rdinterestamount > array_depositmasterobject[0]
													.getMaturityAmt()) {
												double_excess_amount = array_depositmasterobject[0]
														.getMaturityAmt()
														- double_total_rdinterestamount;
											} else {
												System.out
														.println("double_total_rdinterestamount===> "
																+ double_total_rdinterestamount);
												System.out
														.println("MaturityAmt===---> "
																+ array_depositmasterobject[0]
																		.getMaturityAmt());
												double_excess_amount = array_depositmasterobject[0]
														.getMaturityAmt()
														- double_total_rdinterestamount;
												System.out
														.println("double_excess_amount----> "
																+ double_excess_amount);
												System.out
														.println("CumInterest---> "
																+ dep[dep.length - 1]
																		.getCumInterest());
												td_closure
														.setInt_amt_pay(dep[dep.length - 1]
																.getCumInterest()
																+ double_excess_amount);
												td_closure
														.setNet_amt(array_depositmasterobject[0]
																.getMaturityAmt());
												td_closure
														.setClosure_date(array_depositmasterobject[0]
																.getMaturityDate());
												string_applied_date = array_depositmasterobject[0]
														.getMaturityDate();
												td_closure
														.setFlag("Normalclosure");
											}
										}
										req
												.setAttribute(
														"DepositTranRI",
														tddelegate
																.getRDClosureDetails(
																		td_closure
																				.getAc_no(),
																		td_closure
																				.getAc_type(),
																		false));
									}
									if (array_depositmasterobject[0]
											.getLoanAvailed().equals("T")) {
										LoanReportObject loanReportObject = tddelegate
												.getLoanDetails_Closure(
														array_depositmasterobject[0]
																.getLoanAcType(),
														tddelegate.getSysdate(),
														array_depositmasterobject[0]
																.getLoanAccno());

										td_closure.setLoan_avail(true);
										/*
										 * lbl_loan_type.setText(loanReportObject
										 * .getTranMode()+" "+
										 * array_depositmasterobject
										 * [0].getLoanAccno());
										 * lbl_san_date.setText
										 * (loanReportObject.getSanctionDate());
										 * lbl_san_amount
										 * .setText(String.valueOf(
										 * loanReportObject
										 * .getSanctionedAmount()));
										 * lbl_ln_name.
										 * setText(loanReportObject.getName());
										 * lbl_interest_uptodate
										 * .setText(loanReportObject
										 * .getIntUptoDate());
										 * lbl_principle_balance
										 * .setText(String.valueOf
										 * (loansOnDepositRemote
										 * .getCurrentPrBal(
										 * array_depositmasterobject
										 * [0].getLoanAcType
										 * (),array_depositmasterobject
										 * [0].getLoanAccno
										 * (),MainScreen.head.getSysDate())));
										 * // if(option ==
										 * JOptionPane.YES_OPTION){ // if it is
										 * with penality //double
										 * interest=Double
										 * .parseDouble(lbl_applied_inrerest_rate
										 * .getText().toString())+
										 * array_moduleobject_fd
										 * [combo_fd_acctype
										 * .getSelectedIndex()].
										 * getPenaltyRate(); double
										 * interest=Double
										 * .parseDouble(lbl_applied_rate
										 * .getText(
										 * ).toString())+(loanReportObject
										 * .getLoanIntRate
										 * ()-array_depositmasterobject
										 * [0].getInterestRate());
										 * file_logger.info
										 * ("loan int"+loanReportObject
										 * .getLoanIntRate());
										 * file_logger.info("interest====>>>>>"
										 * +interest); String to_date=null; try{
										 * to_date
										 * =Validations.addDays(MainScreen
										 * .head.getSysDate(),-1); }
										 * catch(Exception
										 * e){e.printStackTrace();} double
										 * interest_payable_penality
										 * =Math.round(loanReportObject
										 * .getLoanBalance
										 * ()*Validations.dayCompare
										 * (loanReportObject
										 * .getIntUptoDate(),to_date
										 * )*interest/36500);
										 * lbl_interest_balance
										 * .setText(String.valueOf
										 * (loansOnDepositRemote
										 * .getCurrentIntPay
										 * (array_depositmasterobject
										 * [0].getLoanAcType
										 * (),array_depositmasterobject
										 * [0].getLoanAccno
										 * (),MainScreen.head.getSysDate())));
										 * lbl_total_balance
										 * .setText(String.valueOf
										 * (Math.round(Double
										 * .parseDouble(lbl_principle_balance
										 * .getText())+Double.parseDouble(
										 * lbl_interest_balance
										 * .getText().trim()))));
										 */

									}

								}
								// end of RD Closure-------
							}
						}

					}
					// End of Ac_no>0

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());

				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			// geetha

		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AutoRenewalMenu")) {
			try {
				AutoRenewal auto_renewal = (AutoRenewal) form;
				String pageId = auto_renewal.getPageId();
				req.setAttribute("pagename", pageId);
				System.out.println("pageid=====" + pageId);
				auto_renewal.setAuto_acno(0);
				if (MenuNameReader.containsKeyScreen(auto_renewal.getPageId())) {
					path = MenuNameReader.getScreenProperties(auto_renewal.getPageId());
					TDDelegate tDelegate = new TDDelegate();
					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ---------------------------AUTORENEWAL-----------------------------------------------------------//

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AutoRenewal")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				AutoRenewal auto_renewal = (AutoRenewal) form;
				String pageId = auto_renewal.getPageId();
				System.out.println("pageId===" + auto_renewal.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
                if(pageId!=null)
                {	
				if (MenuNameReader.containsKeyScreen(auto_renewal.getPageId())) {
					path = MenuNameReader.getScreenProperties(auto_renewal.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu-  auto renewal list");
					setInitParam(req, path, tddelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					auto_renewal.setAuto_acno(0);
					String method = req.getParameter("method");
					System.out.println("Method is" + req.getParameter("method"));
					if (method.equalsIgnoreCase("AutoRenewal")) {
						DepositTransactionObject deptran_auto[] = tddelegate.autoRenewalInfo(tddelegate.getSysdate());
						System.out.println("**********Inside Action class AutoRenewal*************");
						List autorenew_list = new ArrayList();
						for (int i = 0; i < deptran_auto.length; i++) {
							Map map_auto = new HashMap();
							map_auto.put("id", i + 1);
							double int_amt = deptran_auto[i].getDepositAmt()+ deptran_auto[i].getRDBalance()+ deptran_auto[i].getCumInterest();

							map_auto.put("acc_no", deptran_auto[i].getAccNo());

							map_auto.put("ac_type", deptran_auto[i].getAccType());

							map_auto.put("max_renewal_times", deptran_auto[i].getTranSequence());

							map_auto.put("renewal_times", deptran_auto[i].getReferenceNo());

							map_auto.put("dep_date", deptran_auto[i].getCdind());

							map_auto.put("mat_date", deptran_auto[i].getPaidDate());

							map_auto.put("period_of_days", deptran_auto[i].getTranKey());

							map_auto.put("dep_amt", deptran_auto[i].getDepositAmt());

							map_auto.put("mat_amt", int_amt);

							if (deptran_auto[i].getInterestDate().equalsIgnoreCase("D")) {

								map_auto.put("Interest_date", "Deposit");
								deptran_auto[0].setInterestDate("D");

							} else {

								map_auto.put("Interest_date", "Maturity");
								deptran_auto[0].setInterestDate("M");
							}
							if (deptran_auto[i].getTranDate().equalsIgnoreCase("T")) {

								map_auto.put("remark","Not Yet Closed Loan Account");

							} else if (Validations.dayCompare(deptran_auto[i].getTranMode(), deptran_auto[i].getPaidDate()) != 1) {
								map_auto.put("remark","Interest Not Yet Calculated");

							}
							session = req.getSession();
							session.setAttribute("autorenewal", autorenew_list);
							autorenew_list.add(map_auto);
							req.setAttribute("autorenewal", autorenew_list);
						}

						if (req.getParameter("id") != null) {

							String id = req.getParameter("id");
							List auto_renew = (ArrayList) session.getAttribute("autorenewal");
							for (int i = 0; i < auto_renew.size(); i++) {
								Map map_pay = (TreeMap) auto_renew.get(i);
								String id1 = map_pay.get("id").toString();
								if (auto_renew != null) {
									if (id.equals(id1)) {
										System.out.println("****************************"+ map_pay.get("acc_no"));
									}
								}
							}
						}
					}
					if (req.getParameter("method").equalsIgnoreCase("SUBMIT")) {

						DepositTransactionObject deptrn_auto[] = new DepositTransactionObject[1];

						if (req.getParameter("id") != null) {

							String id = req.getParameter("id");
							System.out.println("&&&&&&&&&&" + id);

							List autorenewal = (ArrayList) req.getSession().getAttribute("autorenewal");
							req.setAttribute("autorenewal", autorenewal);
							System.out.println("auto_renew >>>>>>>"+ autorenewal);
							System.out.println("id---------->>>>"+ req.getParameter("id"));

							for (int i = 0; i < autorenewal.size(); i++) {
								Map map_auto = (Map) autorenewal.get(i);
								String id1 = String.valueOf(map_auto.get("id"));
								if (autorenewal != null) {
									if (id.equals(id1)) {
										System.out.println("****************************"+ map_auto.get("acc_no"));
										String acc_no = map_auto.get("acc_no").toString();
										String ac_type = map_auto.get("ac_type").toString();
										String renewal_inst = map_auto.get("Interest_date").toString();
										deptrn_auto[0] = new DepositTransactionObject();
										deptrn_auto[0].setAccNo(Integer.parseInt(acc_no));
										deptrn_auto[0].setAccType(ac_type);
										if (renewal_inst.equalsIgnoreCase("Deposit")) {
											deptrn_auto[0].setInterestDate("D");
										} else {
											deptrn_auto[0].setInterestDate("M");
										}
										deptrn_auto[0].obj_userverifier.setUserDate(TDDelegate.getSysdate());
										deptrn_auto[0].obj_userverifier.setUserTml(user);
										deptrn_auto[0].obj_userverifier.setUserId(tml);

										int auto[] = tddelegate.storeautorenewal(deptrn_auto);
										if(auto!=null){
										for (int j = 0; j < auto.length; j++) {

											System.out.println("After Store autoRenewal");
											System.out.println("The acc no  is"+ auto[0]);
											auto_renewal.setAuto_acno(auto[0]);
										}
										}
									}
								}
								System.out.println("Removing id ==="+ req.getParameter("id"));
								map_auto.remove("id");
							}
							System.out.println("hi Good Morning!!");
						}
					}
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AccountRenewalMenu")) {
			try {
				DepositRenewal dep_renewal = (DepositRenewal) form;
				String pageId = dep_renewal.getPageId();
				req.setAttribute("pagename", pageId);
				if(pageId!=null)
				{
				if (MenuNameReader.containsKeyScreen(dep_renewal.getPageId())) {
					path = MenuNameReader.getScreenProperties(dep_renewal.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					setInitParam(req, path, tddelegate);
					req.setAttribute("pageId", path);
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			                       }

		} else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AccountRenewal")){
			DepositMasterObject dep_open_obj = new DepositMasterObject();
			try{
				TDDelegate tddelegate = new TDDelegate();
				DepositRenewal dep_renewal = (DepositRenewal) form;
				String pageId = dep_renewal.getPageId();
				array_moduleobject_acctype = tddelegate.getModulesTypes();
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
				if(MenuNameReader.containsKeyScreen(dep_renewal.getPageId())){
					path = MenuNameReader.getScreenProperties(dep_renewal.getPageId());
					tddelegate = new TDDelegate();
					int ac_no = dep_renewal.getAc_no();
					String ac_type = dep_renewal.getAc_type();
					double_int_amount = 0.0;
					setInitParam(req, path, tddelegate);
					req.setAttribute("pageId", path);
					
					
					if (dep_renewal.getAc_no()!= 0) {
						ModuleObject[] mod1 = new ModuleObject[1];
						mod1 = tddelegate.getModuleMethods(dep_renewal.getAc_type());
						string_today_date = TDDelegate.getSysdate();
						dep_open_obj = tddelegate.getRenewalInfo(ac_type,ac_no, false);
						if (dep_open_obj == null) {
							dep_renewal.setTesting("Account Not Found");
						} else if (dep_open_obj.getCloseInd() >= 1&& dep_open_obj.getCloseInd() <= 4) {
							dep_renewal.setTesting("Account Closed");
						} else if (dep_open_obj.getVerified().length() == 0) {
							dep_renewal.setTesting("Account Not Yet Verified");
						}else if (!(dep_open_obj.getCloseInd() == 0 || dep_open_obj.getCloseInd() == 9)) {
							dep_renewal.setTesting("Renewal Not possible");
						}else if (Validations.dayCompare(dep_open_obj.getMaturityDate(), string_today_date) < 0) {
							dep_renewal	.setTesting("PreMature Renewal Not Possible");
						} else if (dep_open_obj.getCloseInd() == 9) {
							dep_renewal.setTesting("Account Closed But Not Yet Verified");
						}
						if (tddelegate.getDaysFromTwoDate(dep_open_obj.getInterestUpto(), dep_open_obj.getMaturityDate()) != 1) {
							double_int_amount += Math.round((dep_open_obj.getDepositAmt()* dep_open_obj.getInterestRate() * tddelegate
											.getDaysFromTwoDate(tddelegate.getFutureDayDate(dep_open_obj.getInterestUpto(),1),
													dep_open_obj.getMaturityDate())) / (36500));
						}
						if (tddelegate.getDaysFromTwoDate(dep_open_obj.getMaturityDate(), string_today_date) <= mod1[0].getMaxRenewalDays()) {
							string_renewal_date = dep_open_obj.getMaturityDate();
						} else {							
							string_renewal_date = string_today_date;
							double_int_amount += Math.round((dep_open_obj.getDepositAmt()* mod1[0].getTDInterestRate() * tddelegate.getDaysFromTwoDate(dep_open_obj.getMaturityDate(),string_today_date)) / (36500));
						}
						double int_payEd = ((dep_open_obj.getRDBalance() + double_int_amount));
						req.setAttribute("INtAmtPayed", int_payEd);
						double_extra_int_amount = (dep_open_obj.getRDBalance() + double_int_amount)	- dep_open_obj.getInterestPaid();
						double toatl_amt = (double_extra_int_amount)+ dep_open_obj.getDepositAmt();
						req.setAttribute("Tot_amt", toatl_amt);
						req.setAttribute("intrestpayable",double_extra_int_amount);
						Mdate = dep_open_obj.getMaturityDate();
						IntFreq = dep_open_obj.getInterestFrq();
						req.setAttribute("DepositTran", dep_open_obj);
					}
					// personal code add by rakesh 3dec
					if (dep_renewal.getDetails().equals("Personal")){
						String perpath=MenuNameReader.getScreenProperties("0001");
						CustomerMasterObject cmobj2=null;
						cmobj2 = tddelegate.getCustomer(dep_renewal.getCid());
						if(cmobj2!=null){ 
							req.setAttribute("personalInfo", cmobj2);
							req.setAttribute("flag", perpath);
							req.setAttribute("panelName", CommonPanelHeading.getPersonal());
						}else{
							dep_renewal.setTesting("Personal details are not available");
						}
					} 
					/*if (dep_renewal.getDetails().equals("Personal")) {
						DepositMasterObject dep_obj = new DepositMasterObject();
						dep_obj = tddelegate.getDepositMasterValues(dep_renewal.getAc_no(), dep_renewal.getAc_type());
						cust_obj = tddelegate.getCustomer(dep_obj.getCustomerId());
						req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
						req.setAttribute("personalInfo", cust_obj);
						String custpersonal_add = tddelegate.getNomineeAddress(dep_obj.getCustomerId());
						req.setAttribute("Address", custpersonal_add);
						req.setAttribute("panelName", CommonPanelHeading.getPersonal());
					}*/
					if (dep_renewal.getDetails().equals("Introducer")) {
						req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
						req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
						DepositMasterObject dep_close = tddelegate.getClosureInfo(dep_renewal.getAc_type(),dep_renewal.getAc_no(), false);
						AccountObject acntObject = tddelegate.getIntroducerAcntDetails(dep_close.getIntroAccType(), dep_close.getIntroAccno());
						if (acntObject != null) {
							req.setAttribute("personalInfo", tddelegate.getCustomer(acntObject.getCid()));
						} else {
							req.setAttribute("personalInfo",new CustomerMasterObject());
						}
					}
					if (dep_renewal.getPay_ac_type().equalsIgnoreCase("1002001")) {
						req.setAttribute("Dont_hide", "displ");
						int trf_ac_no = dep_renewal.getTrn_acno();
						AccountObject acntObject = tddelegate.getAcccountDetails(dep_renewal.getPay_ac_type(), dep_renewal.getPay_mode(), dep_renewal.getTrn_acno(), tddelegate.getSysdate());
						if (acntObject == null) {
							dep_renewal.setTesting("Account Not Found");
						}
						dep_sub.setTransferType("T");
					}

					if (dep_renewal.getPay_mode().equalsIgnoreCase("Transfer")) {
						try {
							AccountObject acntObject = tddelegate.getAcccountDetails(dep_renewal.getPay_ac_type(), dep_renewal.getPay_mode(), dep_renewal.getTrn_acno(), tddelegate.getSysdate());
							req.setAttribute("panelName", CommonPanelHeading.getPersonal());
							req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
							req.setAttribute("panelName", CommonPanelHeading.getTransfer());
							cust_obj = tddelegate.getCustomer(acntObject.getCid());
							if (cust_obj == null) {
								dep_renewal.setTesting("Transfer Account Not Found");
							}
							req.setAttribute("personalInfo", cust_obj);
							String cust_add = tddelegate.getNomineeAddress(acntObject.getCid());
							req.setAttribute("Address", cust_add);
							setInitParam(req, path, tddelegate);
						} catch (NullPointerException e) {
							dep_renewal.setTesting("Account Not Found");
						}
					} else if (dep_renewal.getDetails().equals("JointHolders")) {
						DepositMasterObject dep_close = tddelegate.getClosureInfo(dep_renewal.getAc_type(),dep_renewal.getAc_no(), false);
						if (dep_close != null) {
							int[] cid = dep_close.getJointCid();
							if (cid != null) {
								req.setAttribute("personalInfo", tddelegate.getCustomer(cid[0]));
								req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
								req.setAttribute("panelName",CommonPanelHeading.getJointHolder());
							}
						} else {
							dep_renewal.setNojonthd("oknojoint");
						}
					} else if (dep_renewal.getDetails().equals("Nominee")) {
						req.setAttribute("disabled", "Yes");
						req.setAttribute("flag", MenuNameReader.getScreenProperties(String.valueOf(13026)));
						NomineeObject[] nominnee = null;
						dep_open_obj = tddelegate.getDepositMasterValues(dep_renewal.getAc_no(), dep_renewal.getAc_type());
						nominnee = tddelegate.getNomineeDetails(dep_open_obj.getNomineeRegNo());
						String NomAddress = tddelegate.getNomineeAddrDetails(nominnee[0].getRegNo());
						req.setAttribute("NOMADDR", NomAddress);
						req.setAttribute("NomDetail", nominnee);
						req.setAttribute("HideTheFields", null);
					}
					
					if (dep_renewal.getForward().equalsIgnoreCase("Submit")|| dep_renewal.getForward().equalsIgnoreCase("Update") && dep_close != null)
					{
						if (IntFreq.equalsIgnoreCase("O")|| tddelegate.checkIntPaid(dep_renewal.getAc_type(), dep_renewal.getAc_no())) {
							req.setAttribute("Verifyvalue", 1);
							path = MenuNameReader.getScreenProperties(String.valueOf(13027));
							req.setAttribute("pageId", path);
							req.setAttribute("account_type",dep_open_obj.getAccType());
							req.setAttribute("account_number",dep_open_obj.getAccNo());
							req.setAttribute("ccid_Newpage", String.valueOf(dep_open_obj.getCustomerId()));
							req.setAttribute("TolAmt", dep_renewal.getTotal_amt());
							req.setAttribute("DepDate", dep_open_obj.getMaturityDate());
							req.setAttribute("PerdDays", String.valueOf(dep_open_obj.getPeriod_in_days()));
							setInitParam(req, path, tddelegate);
						} else {
							dep_renewal.setTesting("Interest Payment is pending, you cant go for renewal");
						}
					}

					if (dep_renewal.getForward().equalsIgnoreCase("Delete")&& dep_close != null) {
						String Today_date = TDDelegate.getSysdate();
						dep_close.setAccType(dep_close.getRenType());
						dep_close.setAccNo(dep_close.getRenewalAccno());
						tddelegate.deleteDeposit(dep_close, Today_date, 2);
						dep_renewal.setTesting("Successfully Deleted");
					}
					return map.findForward(ResultHelp.getSuccess());
				}
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/TDPassbookMenu")) {

			try {
				System.out.println(" entering into passbook menu Interest....");
				TDPassbook td_passbook = (TDPassbook) form;

				String pageId = td_passbook.getPageId();
				System.out.println("pageId===" + pageId);
				req.setAttribute("pagename", pageId);

				System.out.println("inside  Passbook...");

				if (MenuNameReader.containsKeyScreen(td_passbook.getPageId())) {
					path = MenuNameReader.getScreenProperties(td_passbook
							.getPageId());
					TDDelegate tddelegate = new TDDelegate();
					System.out.println("inside menu--- passbook..");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/TDPassbook")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				TDPassbook tdPassbook = (TDPassbook) form;
				String pageId = tdPassbook.getPageId();

				System.out.println("pageId===" + pageId);
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(tdPassbook.getPageId())) {
					path = MenuNameReader.getScreenProperties(tdPassbook.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu- Passbook");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					System.out.println("The PAssBook Flag value-----> "
							+ tdPassbook.getFlag());
					if (tdPassbook.getAc_no() != 0) {

						AccountObject acc_obj = new AccountObject();

						acc_obj = tddelegate.getAcccountDetails(tdPassbook
								.getAc_type(), null, tdPassbook.getAc_no(),TDDelegate.getSysdate());

						System.out.println("accobj in action classs" + acc_obj);

						if (tdPassbook.getFlag().equalsIgnoreCase("DownLoad")) {
							System.out.println("I am in Print Block");
							DepositTransactionObject[] deptrn_obj;
							deptrn_obj = tddelegate.getPassbooktableInfo(tdPassbook.getAc_type(), tdPassbook
											.getAc_no());
							if (deptrn_obj == null) {
								tdPassbook.setValidation("Cannot Print");
							} else {
								res.setContentType("application/.csv");
								res.setHeader("Content-disposition",
												"attachment;filename=InterestAccruedReport.csv");

								java.io.PrintWriter out = res.getWriter();
								out.print("\n");
								out.print("\n");
								out.print("\n");
								out.print(",");
								out.print(",");
								out.print(",");
								out.print("PassBook Details for A/C Number: "+ deptrn_obj[0].getAccNo());
								out.print("\n");
								out.print("\n");
								out.print("TrnSeQ");
								out.print("TrnDate");
								out.print("TrnType");
								out.print("TrnNarration");
								out.print("PaidDate");
								out.print("EntdBy");
								out.print("VerBy");
								for (int i = 0; i < deptrn_obj.length; i++) {
									out.print(i);
									out.print(",");
									out.print(deptrn_obj[i].getTranDate());
									out.print(",");
									out.print(deptrn_obj[i].getTranType());
									out.print(",");
									out.print(deptrn_obj[i].getTranNarration());
									out.print(",");
									out.print(deptrn_obj[i].getPaidDate());
									out.print(",");
									out.print(deptrn_obj[i].getDe_user());
									out.print(",");
									out.print(deptrn_obj[i].getDe_date());
									out.print(",");
									out.print("\n");
								}
								req.setAttribute("msg",	"Saved to excel file in C:");
								return null;

							}
						}

						if (acc_obj == null || acc_obj.equals("null")) {

							System.out.println("inside acconut not found");

							tdPassbook.setValidation("Account Not Found!!!");

						} else {
							tdPassbook.setValidation("");
							DepositTransactionObject[] deptrn_obj;
							deptrn_obj = tddelegate.getPassbooktableInfo(tdPassbook.getAc_type(), tdPassbook
											.getAc_no());
							System.out.println("account type=="+ tdPassbook.getAc_type());
							System.out.println("account no==="+ tdPassbook.getAc_no());
							req.setAttribute("passbook", tddelegate.getPassbookInfo(tdPassbook.getAc_type(),
											tdPassbook.getAc_no()));
							req.setAttribute("passbooktable", tddelegate.getPassbooktableInfo(tdPassbook
											.getAc_type(), tdPassbook
											.getAc_no()));
						}
					}

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for Passbook

		// code for Maturity Ledger report

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/MaturityLedgerReportMenu")) {

			try {

				MaturityLedgerReport maturity_report = (MaturityLedgerReport) form;
				String pageId = maturity_report.getPageId();
				TDDelegate tDelegate = new TDDelegate();
				req.setAttribute("pagename", pageId);
				maturity_report.setSysdate(TDDelegate.getSysdate());
				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(maturity_report
						.getPageId())) {

					path = MenuNameReader.getScreenProperties(maturity_report
							.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/MaturityLedgerReport")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				MaturityLedgerReport maturity_report = (MaturityLedgerReport) form;
				String pageId = maturity_report.getPageId();
				maturity_report.setSysdate(TDDelegate.getSysdate());
				DepositMasterObject[] depmaster = null;
				System.out.println("pageId===" + maturity_report.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
				if (MenuNameReader.containsKeyScreen(maturity_report.getPageId())) {
					path = MenuNameReader.getScreenProperties(maturity_report
							.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu-  maturity ledger");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					System.out.println("VAlue of BUTTON==>"
							+ maturity_report.getBut_view());
					if (maturity_report.getForward().equalsIgnoreCase(
							"DownLoad")) {
						System.out.println("I am in Print Block");
						if (maturity_report.getAc_type().equalsIgnoreCase(
								"1003001")
								|| maturity_report.getAc_type()
										.equalsIgnoreCase("1004001")
								|| maturity_report.getAc_type().startsWith(
										"1005")) {

							System.out.println("Hey account type=="
									+ maturity_report.getAc_type());
							System.out.println("From date in action==> "
									+ maturity_report.getFrom_date());
							System.out.println("To date in action==> "
									+ maturity_report.getTo_date());
							depmaster = tddelegate.getMaturityLegerInfo(
									maturity_report.getAc_type(),
									maturity_report.getFrom_date(),
									maturity_report.getTo_date(), 0, 0);
							if (depmaster != null) {
								req.setAttribute("maturityledger", depmaster);
							} else if (depmaster == null) {
								System.out.println("I am inside Ledger null");
								maturity_report
										.setTesting("Records Not Found!!");
							}

						} else {
							System.out
									.println("I am inside EEEEEEElse block for query in action===???");
							depmaster = tddelegate.getMaturityLegerInfo(
									maturity_report.getAc_type(),
									maturity_report.getFrom_date(),
									maturity_report.getTo_date(), 1, 0);
							if (depmaster != null) {
								req.setAttribute("maturityledger", depmaster);

							}
						}

						DepositMasterObject[] deptrn_obj;
						deptrn_obj = depmaster;

						if (deptrn_obj == null) {
							maturity_report.setTesting("Cannot Print");
						} else {

							res.setContentType("application/.csv");
							res.setHeader("Content-disposition",
											"attachment;filename=InterestAccruedReport.csv");

							java.io.PrintWriter out = res.getWriter();
							out.print("\n");
							out.print("\n");
							out.print("\n");
							out.print(",");
							out.print(",");
							out.print(",");
							out.print("Maturity Ledger Details for A/C Type: "
									+ deptrn_obj[0].getAccType());
							out.print("\n");
							out.print("\n");
							out.print("Srl_No");
							out.print(",");
							out.print("Ac_No");
							out.print(",");
							out.print("Depositor Name");
							out.print(",");
							out.print("Deposit Date");
							out.print(",");
							out.print("Maturity Date");
							out.print(",");
							out.print("Deposit Amount");
							out.print(",");
							out.print("Maturity Amount");
							out.print(",");
							out.print("Interest Paid");
							out.print(",");
							out.print("Cum Interest");
							out.print(",");
							out.print("\n");
							for (int i = 0; i < deptrn_obj.length; i++) {
								out.print(i+1);
								out.print(",");
								out.print(deptrn_obj[i].getAccNo());
								out.print(",");
								out.print(deptrn_obj[i].getName());
								out.print(",");
								out.print(deptrn_obj[i].getDepDate());
								out.print(",");
								out.print(deptrn_obj[i].getMaturityDate());
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
							req
									.setAttribute("msg",
											"Saved to excel file in C:");
							return null;
						}
					}

					if (maturity_report.getBut_view().equalsIgnoreCase("View")) {

						if (maturity_report.getAc_type().startsWith("1003")
								|| maturity_report.getAc_type()
										.equalsIgnoreCase("1004001")
								|| maturity_report.getAc_type().startsWith(
										"1005")) {

							System.out.println("Hey account type=="
									+ maturity_report.getAc_type());
							System.out.println("From date in action==> "
									+ maturity_report.getFrom_date());
							System.out.println("To date in action==> "
									+ maturity_report.getTo_date());
							depmaster = tddelegate.getMaturityLegerInfo(
									maturity_report.getAc_type(),
									maturity_report.getFrom_date(),
									maturity_report.getTo_date(), 0, 0);
							if (depmaster != null) {
								req.setAttribute("maturityledger", depmaster);
							} else if (depmaster == null) {
								System.out.println("I am inside Ledger null");
								maturity_report
										.setTesting("Records Not Found!!");
							}

						} else {
							System.out
									.println("I am inside EEEEEEElse block for query in action===???");
							depmaster = tddelegate.getMaturityLegerInfo(
									maturity_report.getAc_type(),
									maturity_report.getFrom_date(),
									maturity_report.getTo_date(), 1, 0);
							if (depmaster != null) {
								req.setAttribute("maturityledger", depmaster);

							}
						}
					}

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for Maturity ledger
		// code for auto renewal list
		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/AutoRenewalListMenu")) {

			try {

				AutoRenewalList auto_list = (AutoRenewalList) form;
				String pageId = auto_list.getPageId();

				req.setAttribute("pagename", pageId);
				TDDelegate tDelegate = new TDDelegate();

				auto_list.setSysdate(tDelegate.getSysdate());
				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(auto_list.getPageId())) {

					path = MenuNameReader.getScreenProperties(auto_list.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/AutoRenewalList")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				AutoRenewalList auto_list = (AutoRenewalList) form;
				String pageId = auto_list.getPageId();
				auto_list.setSysdate(tddelegate.getSysdate());
				System.out.println("pageId===" + auto_list.getPageId());
				req.setAttribute("pagename", pageId); 
				setInitParam(req, path, tddelegate);
				if (MenuNameReader.containsKeyScreen(auto_list.getPageId())) {
					path = MenuNameReader.getScreenProperties(auto_list.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu-  auto renewal list");
					setInitParam(req, path, tddelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
				
					if (auto_list.getAc_type().equalsIgnoreCase("1003001")) {

						System.out.println("account type=="	+ auto_list.getAc_type());

						req.setAttribute("autorenewallist", tddelegate.getAutoRenewalList(auto_list.getAc_type(),auto_list.getFrom_date(), auto_list.getTo_date(), 0, 0));

						System.out.println("{ inside fd loop..");
					} else {
						System.out.println("other than fd....");

						req.setAttribute("autorenewallist", tddelegate.getAutoRenewalList(auto_list.getAc_type(),auto_list.getFrom_date(), auto_list.getTo_date(), 0, 1));
					}
					if (auto_list.getForward().equalsIgnoreCase("DownLoad")) {
						System.out.println("I am in Print Block");
						DepositTransactionObject[] deptrn_obj = null;
						if (auto_list.getAc_type().equalsIgnoreCase("1003001")) {

							System.out.println("account type=="+ auto_list.getAc_type());

							deptrn_obj = tddelegate.getAutoRenewalList(auto_list.getAc_type(), auto_list.getFrom_date(), auto_list.getTo_date(), 0, 0);

							System.out.println("{ inside fd loop..");
						} else {
							System.out.println("other than fd....");

							deptrn_obj = tddelegate.getAutoRenewalList(auto_list.getAc_type(), auto_list.getFrom_date(), auto_list.getTo_date(), 0, 0);
						}
						if (deptrn_obj == null) {
							// quantum_wise.setTesting("Cannot Print");
						} else {
							res.setContentType("application/.csv");
							res.setHeader("Content-disposition","attachment;filename=InterestAccruedReport.csv");
							java.io.PrintWriter out = res.getWriter();
							out.print("\n");
							out.print("\n");
							out.print("\n");
							out.print(",");
							out.print(",");
							out.print(",");
							out.print("QuantumWiseReport Details for A/C Type: "+ deptrn_obj[0].getAccType());
							out.print("\n");
							out.print("\n");
							out.print("Account Type");
							out.print(",");
							out.print("AccountNumber");
							out.print(",");
							out.print("Deposit Date");
							out.print(",");
							out.print("Maturity Date");
							out.print(",");
							out.print("Deposit Amount");
							out.print(",");
							out.print("Auto RenewalInstruction");
							out.print(",");
							out.print("Period In Days");
							out.print(",");
							out.print("New Account Type/Number");
							out.print(",");
							out.print("Balance");
							out.print(",");
							for (int i = 0; i < deptrn_obj.length; i++) {
								out.print(i);
								out.print(",");
								out.print(deptrn_obj[i].getAccType());
								out.print(",");
								out.print(deptrn_obj[i].getAccNo());
								out.print(",");
								out.print(deptrn_obj[i].getDe_date());
								out.print(",");
								out.print(deptrn_obj[i].getDe_date());
								out.print(",");
								out.print(deptrn_obj[i].getDepositPaid());
								out.print(",");
								out.print(deptrn_obj[i].getPaidDate());
								out.print(",");
								out.print(deptrn_obj[i].getRDBalance());
								out.print(",");
								out.print("\n");
							}
							req.setAttribute("msg",	"Saved to excel file in C:");
							return null;
						}
					}

					if (auto_list.getBut_view().equalsIgnoreCase("View")) {
						System.out.println("to date==="+ auto_list.getTo_date());
						req.setAttribute("autorenewallist", tddelegate.getAutoRenewalList(auto_list.getAc_type(),auto_list.getFrom_date(), auto_list.getTo_date(), 0, 0));
						System.out.println("after req.setattribute==="+ auto_list.getTo_date());
					}
					else
					{
						System.out.println("this is in other than fd");
						//req.setAttribute("autorenewallist", tddelegate.getAutoRenewalList(auto_list.getAc_type(),auto_list.getFrom_date(), auto_list.getTo_date(), 0, 1));
					}

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/InterestAccruedReportMenu")) {

			try {

				InterestAccruedReport int_accrued = (InterestAccruedReport) form;
				String pageId = int_accrued.getPageId();
				TDDelegate tDelegate = new TDDelegate();

				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);
				int_accrued.setSysdate(TDDelegate.getSysdate());

				if (MenuNameReader.containsKeyScreen(int_accrued.getPageId())) {

					path = MenuNameReader.getScreenProperties(int_accrued.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/InterestAccruedReport")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				InterestAccruedReport int_accrued = (InterestAccruedReport) form;
				String pageId = int_accrued.getPageId();
				DepositMasterObject[] depmaster = null;
				int_accrued.setSysdate(TDDelegate.getSysdate());

				System.out.println("pageId===" + int_accrued.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(int_accrued.getPageId())) {
					path = MenuNameReader.getScreenProperties(int_accrued.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu-  interest acrued");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
			
					System.out.println("The value of Forward===>"+ int_accrued.getForward());
					System.out.println("===int_accrued===> "+ int_accrued.getBut_view());

					if (int_accrued.getForward() != null) {
						if (int_accrued.getForward().equalsIgnoreCase("DownLoad")) {
							System.out.println(" i am inside down load");

							// TO save to an excel Sheet
							res.setContentType("application/.csv");
							res.setHeader("Content-disposition",
											"attachment;filename=InterestAccruedReport.csv");

							java.io.PrintWriter out = res.getWriter();
							out.print("\n");
							out.print("\n");
							out.print("\n");
							out.print(",");
							out.print(",");
							out.print(",");
							out.print("PassBook Details for A/C Type: "
									+ int_accrued.getAc_type());
							out.print("\n");
							out.print("\n");
							/*
							 * HSSFCell cell = row.createCell((short)0);
							 * cell.setCellValue(1);
							 */

							out.print("SrlNo");
							out.print(",");
							out.print("Ac/No");
							out.print(",");
							out.print("Depositor Name");
							out.print(",");
							out.print("DepositDate");
							out.print(",");
							out.print("MaturityDate");
							out.print(",");
							out.print("Deposit Amount");
							out.print(",");
							out.print("Interest Paid");
							out.print(",");
							out.print("Cum Interest");
							out.print(",");
							out.print("Balance");
							out.print(",");
							out.print("");
							out.print("\n");
							
							if (int_accrued.getAc_type().equalsIgnoreCase(
									"1003001")
									|| int_accrued.getAc_type()
											.equalsIgnoreCase("1004001")
									|| int_accrued.getAc_type().startsWith(
											"1005")) {

								System.out.println("account type=="
										+ int_accrued.getAc_type());
								depmaster = tddelegate.getIntAccruedInfo(
										int_accrued.getAc_type(), int_accrued
												.getFrom_date(), int_accrued
												.getTo_date(), 0);
								if (depmaster != null) {
									req.setAttribute("interestreport",
											depmaster);
								} else if (depmaster == null) {
									int_accrued
											.setTesting("Records Not Found!!!");
								}

							} else {
								System.out.println("other than fd....");

								req.setAttribute("interestreport", tddelegate
										.getIntAccruedInfo(int_accrued
												.getAc_type(), int_accrued
												.getFrom_date(), int_accrued
												.getTo_date(), 1));
							}

							for (int k = 0; k < depmaster.length; k++) {
								out.print(k);
								out.print(",");
								out.print(depmaster[k].getAccNo());
								out.print(",");
								out.print(depmaster[k].getDepCatName());
								out.print(",");
								out.print(depmaster[k].getDepDate());
								out.print(",");
								out.print(depmaster[k].getMaturityDate());
								out.print(",");
								out.print(depmaster[k].getMaturityAmt());
								out.print(",");
								out.print(depmaster[k].getInterestPaid());
								out.print(",");
								out.print(depmaster[k].getCumInterest());
								out.print(",");
								out.print(depmaster[k].getRDBalance());
								out.print(",");
								out.print("\n");
							}

							req.setAttribute("msg",
											"Saved to excel file in C:");
							return null;

						}

				}
					
					if (int_accrued.getBut_view() != null) {
						if (int_accrued.getBut_view().equalsIgnoreCase("View")) {

							System.out.println("to date==="+ int_accrued.getTo_date());

							if (int_accrued.getAc_type().startsWith("1003")
									|| int_accrued.getAc_type()
											.equalsIgnoreCase("1004001")
									|| int_accrued.getAc_type().startsWith(
											"1005")) {

								System.out.println("account type=="
										+ int_accrued.getAc_type());
								depmaster = tddelegate.getIntAccruedInfo(
										int_accrued.getAc_type(), int_accrued
												.getFrom_date(), int_accrued
												.getTo_date(), 0);
								if (depmaster != null) {
									req.setAttribute("interestreport",depmaster);
									int_accrued.setTesting("");
								} else if (depmaster == null) {
									int_accrued
											.setTesting("Records Not Found!!!");
								}

							} else {
								System.out.println("other than fd....");

								req.setAttribute("interestreport", tddelegate
										.getIntAccruedInfo(int_accrued
												.getAc_type(), int_accrued
												.getFrom_date(), int_accrued
												.getTo_date(), 1));
							}

						}
						return map.findForward(ResultHelp.getSuccess());
					}



				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for Int accrued report

		// code for period wise report
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/PeriodwiseReportMenu")) {

			try {

				PeriodwiseReport period_wise = (PeriodwiseReport) form;
				String pageId = period_wise.getPageId();

				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(period_wise.getPageId())) {
					System.out.println("PeriodwiseReport------>");
					/*
					 * String[] period=null; DepositReportObject[]
					 * depRate=tddelegate.getLimit("",1); period=new
					 * String[depRate.length]; for(int
					 * i=0;i<depRate.length;i++){
					 * period[i]=depRate[i].getLmt_hdg(); }
					 * req.setAttribute("DEPRATE",period);
					 */
					path = MenuNameReader.getScreenProperties(period_wise.getPageId());

					TDDelegate tDelegate = new TDDelegate();
					System.out.println("Periodewise Repost000000000");
					setInitParam(req, path, tDelegate);
					System.out.println("Periodewise Rep111111111");
					System.out.println("path****" + path);
					System.out.println("Periodewise Repost2222222");
					req.setAttribute("pageId", path);

					System.out.println("Periodewise Repost333333333");
					System.out.println("the path" + path);
					System.out.println("Periodewise Repost44444");
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/PeriodwiseReport")) {
			try {
				System.out.println("/TermDeposit/PeriodwiseReport------>");
				TDDelegate tddelegate = new TDDelegate();
				PeriodwiseReport period_wise = (PeriodwiseReport) form;
				String pageId = period_wise.getPageId();

				System.out.println("pageId===" + period_wise.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(period_wise.getPageId())) {

					System.out.println("/TermDeposit/PeriodwiseReport HELLO------>");
					path = MenuNameReader.getScreenProperties(period_wise.getPageId());
					tddelegate = new TDDelegate();
					DepositMasterObject[] dmasterob = new DepositMasterObject[0];
					System.out.println("inside menu-  interest acrued");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					DepositReportObject[] depRate = tddelegate.getLimit(period_wise.getAc_type(), 1);
					req.setAttribute("DEPRATE", depRate);
					
					/*
					 * if(period_wise.getAc_type().equalsIgnoreCase("1003001")){
					 * 
					 * 
					 * 
					 * System.out.println("account type=="+period_wise.getAc_type
					 * ());
					 * 
					 * req.setAttribute("periodwise",tddelegate.getPeriodWise(
					 * period_wise.getSrlno(),period_wise.getAc_type(),
					 * period_wise.getCombo_periodyears(),1 ));
					 * 
					 * System.out.println("{ inside fd loop.."); }
					 */
					/*
					 * if{ System.out.println("other than fd....");
					 * 
					 * req.setAttribute("interestreport",tddelegate.
					 * getIntAccruedInfo
					 * (int_accrued.getAc_type(),int_accrued.getFrom_date(),
					 * int_accrued.getTo_date(),0)); }
					 */

					if (period_wise.getProcess_date().length() != 0) {
						if (period_wise.getForward().equalsIgnoreCase("View")) {
							DepositMasterObject[] depperiod = null;
							req.setAttribute("table", "displayit");
							String Processdate = Validations.convertYMD(period_wise.getProcess_date());

							dmasterob = tddelegate.getPeriodWise(Integer.parseInt(period_wise.getCombo_periodyears()),period_wise.getAc_type(), period_wise.getProcess_date(), 1);
							if (dmasterob != null) {
								
								req.setAttribute("periodwise", dmasterob);
							} else {
								period_wise.setTesting("Records Not Found!!!!");
							}

							if (period_wise.getAc_type().equalsIgnoreCase("1004001")|| period_wise.getAc_type().startsWith(	"1005")) {

								System.out.println("account type=="
										+ period_wise.getAc_type());

								// req.setAttribute("periodwise",tddelegate.getPeriodWise(period_wise.getSrlno(),period_wise.getAc_type(),
								// period_wise.getCombo_periodyears(),1 ));

								// period_wise.getCombo_periodyears() is changed
								// to null
								depperiod = tddelegate.getPeriodWise(Integer
										.valueOf(period_wise
												.getCombo_periodyears()),
										period_wise.getAc_type(), period_wise
												.getProcess_date(), 1);

								if (depperiod != null) {
									req.setAttribute("periodwise", depperiod);
									System.out.println("{ inside fd loop.."
													+ tddelegate.getPeriodWise(
																	Integer.valueOf(period_wise.getCombo_periodyears()),
																	period_wise.getAc_type(),
																	period_wise.getProcess_date(),1));
								} else {
									period_wise.setTesting("Records Not Found!!");
								}
							}

						} else if (period_wise.getForward().equalsIgnoreCase("DownLoad")) {
							System.out.println("I am in Print Block");
							DepositMasterObject[] deptrn_obj;
							deptrn_obj = tddelegate.getPeriodWise(Integer.parseInt(period_wise.getCombo_periodyears()),
									period_wise.getAc_type(), period_wise.getProcess_date(), 1);
							if (deptrn_obj == null) {
								period_wise.setTesting("Cannot Print");
							} else {

								res.setContentType("application/.csv");
								res.setHeader("Content-disposition","attachment;filename=InterestAccruedReport.csv");

								java.io.PrintWriter out = res.getWriter();
								out.print("\n");
								out.print("\n");
								out.print("\n");
								out.print(",");
								out.print(",");
								out.print(",");
								out.print("Period Wise Details for A/C Type: "+ deptrn_obj[0].getAccType());
								out.print("\n");
								out.print("\n");
								out.print("Srlno");
								out.print(",");
								out.print("AccNo");
								out.print(",");
								out.print("DepositorName");
								out.print(",");
								out.print("DepositDate");
								out.print(",");
								out.print("MaturityDate");
								out.print(",");
								out.print("DepositDays");
								out.print(",");
								out.print("InterestRate");
								out.print(",");
								out.print("AutoRenewal");
								out.print(",");
								out.print("IntFreq");
								out.print(",");
								out.print("DepAmount");
								out.print(",");
								out.print("MaturityAmount");
								out.print(",");
								out.print("InterestMode");
								out.print(",");
								out.print("ReceiptNumber");
								out.print(",");
								out.print("\n");
								
								for (int i = 0; i < deptrn_obj.length; i++) {
									out.print(i+1);
									out.print(",");
									out.print(deptrn_obj[i].getAccNo());
									out.print(",");
									out.print(deptrn_obj[i].getName());
									out.print(",");
									out.print(deptrn_obj[i].getDepDate());
									out.print(",");
									out.print(deptrn_obj[i].getMaturityDate());
									out.print(",");
									out.print(deptrn_obj[i].getDepositDays());
									out.print(",");
									out.print(deptrn_obj[i].getInterestRate());
									out.print(",");
									out.print(deptrn_obj[i].getAutoRenewal());
									out.print(",");
									out.print(deptrn_obj[i].getInterestFrq());
									out.print(",");
									out.print(deptrn_obj[i].getDepositAmt());
									out.print(",");
									out.print(deptrn_obj[i].getMaturityAmt());
									out.print(",");
									out.print(deptrn_obj[i].getInterestMode());
									out.print(",");
									out.print(deptrn_obj[i].getReceiptno());
									out.print(",");
									out.print("\n");
									i++;
								}
								req.setAttribute("msg",
										"Saved to excel file in C:");
								return null;
							}
						}

					}
					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// code ended for periodwise report

		// code for open/closed accounts

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/OpenClosedAccountsMenu")) {

			try {

				OpenClosedAccounts open_close = (OpenClosedAccounts) form;
				String pageId = open_close.getPageId();
				TDDelegate tDelegate = new TDDelegate();
				req.setAttribute("pagename", pageId);
				open_close.setSysdate(tDelegate.getSysdate());
				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(open_close.getPageId())) {

					path = MenuNameReader.getScreenProperties(open_close
							.getPageId());


					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					open_close.setFrom_date("0");
					open_close.setTo_date("0");

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/OpenClosedAccounts")) {

			try {
				TDDelegate tDelegate = new TDDelegate();
				OpenClosedAccounts open_close = (OpenClosedAccounts) form;
				String pageId = open_close.getPageId();
				open_close.setSysdate(tDelegate.getSysdate());
				System.out.println("pageId===" + open_close.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(open_close.getPageId())) {
					path = MenuNameReader.getScreenProperties(open_close
							.getPageId());
					tddelegate = new TDDelegate();
					DepositMasterObject[] depOpenCloseAll = null;
					System.out.println("inside menu-open closed accounts");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					if (open_close.getForward().equalsIgnoreCase("DownLoad")) {
						System.out.println("I am in Print Block");
						DepositMasterObject[] deptrn_obj;
						deptrn_obj = tddelegate.getOpenAccounts(open_close
								.getAc_type(), open_close.getFrom_date(),
								open_close.getTo_date(), 0);
						if (deptrn_obj == null) {
							open_close.setTesting("Cannot Print");
						} else {

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
							out
									.print("QuantumWiseReport Details for A/C Type: "
											+ deptrn_obj[0].getAccType());
							out.print("\n");
							out.print("\n");
							out.print("Srl-No");
							out.print(",");
							out.print("Ac_No");
							out.print(",");
							out.print("Depositor Name");
							out.print(",");
							out.print("Details_Address");
							out.print(",");
							out.print("Deposit Type");
							out.print(",");
							out.print("Deposit Date");
							out.print(",");
							out.print("Maturity Date");
							out.print(",");
							out.print("Deposit Days");
							out.print(",");
							out.print("Interest rate");
							out.print(",");
							out.print("AutoRenewal");
							out.print(",");
							out.print("Interest Freq");
							out.print(",");
							out.print("Deposit Amt");
							out.print(",");
							out.print("Maturity Amt");
							out.print(",");
							out.print("Interest Mode");
							out.print(",");
							out.print("receipt number");
							out.print(",");
							for (int i = 0; i < deptrn_obj.length; i++) {
								out.print(i);
								out.print(",");
								out.print(deptrn_obj[i].getAccNo());
								out.print(",");
								out.print(deptrn_obj[i].getDepCatName());
								out.print(",");
								out.print(deptrn_obj[i].getAccType());
								out.print(",");
								out.print(deptrn_obj[i].getDepositType());
								out.print(",");
								out.print(deptrn_obj[i].getDepDate());
								out.print(",");
								out.print(deptrn_obj[i].getMaturityDate());
								out.print(",");
								out.print(deptrn_obj[i].getDepositDays());
								out.print(",");
								out.print(deptrn_obj[i].getInterestRate());
								out.print(",");
								out.print(deptrn_obj[i].getAutoRenewal());
								out.print(",");
								out.print(deptrn_obj[i].getInterestFrq());
								out.print(",");
								out.print(deptrn_obj[i].getDepositAmt());
								out.print(",");
								out.print(deptrn_obj[i].getMaturityAmt());
								out.print(",");
								out.print(deptrn_obj[i].getInterestMode());
								out.print(",");
								out.print(deptrn_obj[i].getReceiptno());
								out.print(",");
								out.print("\n");
							}
							req
									.setAttribute("msg",
											"Saved to excel file in C:");
							return null;
						}
					}
					/*
					 * if(open_close.getOpen_close_stat().equalsIgnoreCase("OPEN ACCOUNTS"
					 * )){
					 * 
					 * System.out.println("open close status====="+open_close.
					 * getOpen_close_stat());
					 * 
					 * req.setAttribute("openclosed",tddelegate.getOpenAccounts(
					 * open_close.getAc_type(),open_close.getFrom_date(),
					 * open_close.getTo_date(),0));
					 * 
					 * }
					 * 
					 * if(open_close.getOpen_close_stat().equalsIgnoreCase(
					 * "CLOSED ACCOUNTS")) {
					 * System.out.println("open close status====="
					 * +open_close.getOpen_close_stat());
					 * 
					 * 
					 * 
					 * req.setAttribute("openclosed",tddelegate.getcloseAccounts(
					 * open_close.getAc_type(),open_close.getFrom_date(),
					 * open_close.getTo_date(),0));
					 * 
					 * 
					 * }
					 */

					if (open_close.getAc_type().startsWith("1003")
							|| open_close.getAc_type().equalsIgnoreCase(
									"1004001")
							|| open_close.getAc_type().startsWith("1005")) {
						System.out
								.println("The value of the view button is -----> "
										+ open_close.getBut_view());

						if (open_close.getBut_view().equalsIgnoreCase("View")) {
							System.out
									.println("Value os Stat 11th may 09====> "
											+ open_close.getOpen_close_stat());
							System.out.println("Account Type 11th may 09==> "
									+ open_close.getAc_type());
							System.out.println("From date 11th may 09===> "
									+ open_close.getFrom_date());
							System.out.println("To Date 11th may 09====> "
									+ open_close.getTo_date());
							if (open_close.getOpen_close_stat()
									.equalsIgnoreCase("OpenAccounts")) {
								System.out.println("open close status====="
										+ open_close.getOpen_close_stat());
								depOpenCloseAll = tddelegate.getOpenAccounts(
										open_close.getAc_type(), open_close
												.getFrom_date(), open_close
												.getTo_date(), 0);
								if (depOpenCloseAll == null) {
									open_close.setTesting("NO RECORDS FOUND!!");
								} else if (depOpenCloseAll != null) {
									open_close.setTesting("");
									req.setAttribute("openclosed",
											depOpenCloseAll);
								}
							}

							else if (open_close.getOpen_close_stat()
									.equalsIgnoreCase("CloseAccounts")) {
								System.out.println("open close status====="
										+ open_close.getOpen_close_stat());
								depOpenCloseAll = tddelegate.getcloseAccounts(
										open_close.getAc_type(), open_close
												.getFrom_date(), open_close
												.getTo_date(), 0);
								if (depOpenCloseAll == null) {
									open_close.setTesting("NO RECORDS FOUND!!");
								} else if (depOpenCloseAll != null) {
									open_close.setTesting("");
									req.setAttribute("openclosed",
											depOpenCloseAll);
								}
							}

							else if (open_close.getOpen_close_stat()
									.equalsIgnoreCase("ALL")) {
								System.out.println("open close status89987987====="
										+ open_close.getOpen_close_stat());


								req.setAttribute("openclosed", tddelegate
										.getAllAccounts(
												open_close.getAc_type(),
												open_close.getFrom_date(),
												open_close.getTo_date(), 2));

								System.out.println("{ inside fd loop..");
							}

						}

					} else {

						System.out.println("other than fd....");

						depOpenCloseAll = tddelegate.getOpenAccounts(open_close
								.getAc_type(), open_close.getFrom_date(),
								open_close.getTo_date(), 0);
						if (depOpenCloseAll == null) {
							open_close.setTesting("NO RECORDS FOUND!!");
						} else if (depOpenCloseAll != null) {
							open_close.setTesting("");
							req.setAttribute("openclosed", depOpenCloseAll);
						}
					}

					System.out
							.println("The value of open_close.getBut_view()=====> "
									+ open_close.getBut_view());

					if (open_close.getBut_view().equalsIgnoreCase("View")) {
					if (open_close.getAc_type().equalsIgnoreCase("1003001")|| open_close.getAc_type().equalsIgnoreCase("1003002")|| open_close.getAc_type().equalsIgnoreCase("1004001")|| open_close.getAc_type().startsWith("1005")) {
							System.out.println("The value of the view button is -----> "+ open_close.getBut_view());

							if (open_close.getBut_view().equalsIgnoreCase("View")) {
								System.out.println("Value os Stat 11th may 09====> "+ open_close.getOpen_close_stat());
								System.out.println("Account Type 11th may 09==> "+ open_close.getAc_type());
								System.out.println("From date 11th may 09===> "+ open_close.getFrom_date());
								System.out.println("To Date 11th may 09====> "+ open_close.getTo_date());
								if (open_close.getOpen_close_stat().equalsIgnoreCase("OpenAccounts")) {
									System.out.println("open close status====="+ open_close.getOpen_close_stat());
									depOpenCloseAll = tddelegate
											.getOpenAccounts(open_close
													.getAc_type(), open_close
													.getFrom_date(), open_close
													.getTo_date(), 0);
									if (depOpenCloseAll == null) {
										open_close
												.setTesting("NO RECORDS FOUND!!");
									} else if (depOpenCloseAll != null) {
										open_close.setTesting("");
										req.setAttribute("openclosed",
												depOpenCloseAll);
									}
								}

								else if (open_close.getOpen_close_stat().equalsIgnoreCase("CloseAccounts")) {
									System.out.println("open close status====="
											+ open_close.getOpen_close_stat());
									depOpenCloseAll = tddelegate
											.getcloseAccounts(open_close
													.getAc_type(), open_close
													.getFrom_date(), open_close
													.getTo_date(), 0);
									if (depOpenCloseAll == null) {
										open_close
												.setTesting("NO RECORDS FOUND!!");
									} else if (depOpenCloseAll != null) {
										open_close.setTesting("");
										req.setAttribute("openclosed",
												depOpenCloseAll);
									}
								}

								else if (open_close.getOpen_close_stat()
										.equalsIgnoreCase("ALL")) {
									/*
									 * System.out.println("open close status====="
									 * +open_close.getOpen_close_stat());
									 * 
									 * req.setAttribute("openclosed",tddelegate.
									 * getcloseAccounts
									 * (open_close.getAc_type(),open_close
									 * .getFrom_date(),
									 * open_close.getTo_date(),0));
									 * 
									 * req.setAttribute("openclosed",tddelegate.
									 * getOpenAccounts
									 * (open_close.getAc_type(),open_close
									 * .getFrom_date(),
									 * open_close.getTo_date(),0));
									 */
									depOpenCloseAll = tddelegate
											.getAllAccounts(open_close
													.getAc_type(), open_close
													.getFrom_date(), open_close
													.getTo_date(), 2);
									if (depOpenCloseAll == null) {
										open_close
												.setTesting("NO RECORDS FOUND!!");
									} else if (depOpenCloseAll != null) {
										open_close.setTesting("");
										req.setAttribute("openclosed",
												depOpenCloseAll);
									}

									System.out.println("{ inside fd loop..");
								}

							}

							/*
							 * if(open_close.getBut_file().equalsIgnoreCase("File"
							 * )) { System.out.println("I am in Print Block");
							 * DepositMasterObject[] deptrn_obj; //
							 * deptrn_obj=tddelegate
							 * .getQuantumWise(quantum_wise.getAc_type(),
							 * quantum_wise.getProcess_date(),
							 * 1,Integer.parseInt
							 * (quantum_wise.getCombo_periodyears().trim()));
							 * if(deptrn_obj==null) { //
							 * quantum_wise.setTesting("Cannot Print"); } else {
							 * try{ String name="TDPassBookFile"; HSSFWorkbook
							 * wb = new HSSFWorkbook(); HSSFSheet sheet =
							 * wb.createSheet("TD-PassBook"); HSSFRow row =
							 * sheet.createRow((short)0); HSSFCell cell =
							 * row.createCell((short)0); cell.setCellValue(1);
							 * row.createCell((short)1).setCellValue("TrnSeQ");
							 * row.createCell((short)2).setCellValue("TrnDate");
							 * row.createCell((short)3).setCellValue("TrnType");
							 * row
							 * .createCell((short)4).setCellValue("TrnNarration"
							 * );
							 * row.createCell((short)5).setCellValue("PaidDate"
							 * );
							 * row.createCell((short)6).setCellValue("EntdBy");
							 * row.createCell((short)7).setCellValue("VerBy");
							 * for(int i=0;i<deptrn_obj.length;i++){
							 * System.out.println("how many times=====> "+i);
							 * HSSFRow row1 = sheet.createRow((short)i+1);
							 * row1.createCell((short)1).setCellValue(i);
							 * row1.createCell
							 * ((short)2).setCellValue(deptrn_obj[
							 * i].getAccType());
							 * row1.createCell((short)3).setCellValue
							 * (deptrn_obj[i].getAccNo());
							 * row1.createCell((short
							 * )4).setCellValue(deptrn_obj[i].getDepDate() );
							 * row1
							 * .createCell((short)5).setCellValue(deptrn_obj[
							 * i].getDepositAmt());
							 * row1.createCell((short)6).setCellValue
							 * (deptrn_obj[i].getInterestFrq());
							 * row1.createCell(
							 * (short)6).setCellValue(deptrn_obj[
							 * i].getCustomerId()); } FileOutputStream fileOut =
							 * new FileOutputStream("c:\\"+name+".xls");
							 * wb.write(fileOut); fileOut.close(); } catch(
							 * Exception ex ) {
							 * 
							 * } //quantum_wise.setTesting(
							 * "SuccessFullyCreated In C-Drive with File Name-QuantumWiseReportFile"
							 * ); } }
							 */

							// if loop of '1003001'

						}
						System.out
								.println("***************view loop completed**********");

					}

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // code ended for open/close acs

		// code for Query on receipt

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/QueryOnReceiptNoMenu")) {

			try {

				QueryOnReceipt query_receipt = (QueryOnReceipt) form;
				String pageId = query_receipt.getPageId();

				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(query_receipt.getPageId())) {

					path = MenuNameReader.getScreenProperties(query_receipt
							.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);
					query_receipt.setReceipt_no("0");
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/QueryOnReceiptNo")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				QueryOnReceipt query_receipt = (QueryOnReceipt) form;
				String pageId = query_receipt.getPageId();

				System.out.println("pageId of Qury on receipt"
						+ query_receipt.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(query_receipt.getPageId())) {
					path = MenuNameReader.getScreenProperties(query_receipt
							.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside  Query on receipt");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					if (query_receipt.getReceipt_no().length() != 0) {

						String perpath = MenuNameReader
								.getScreenProperties("0001");
						int max_receipt_no = tddelegate.validate(2, null, 0);

						System.out.println("max_receipt_no===="
								+ max_receipt_no);
						/*
						 * if(Integer.parseInt(query_receipt.getReceipt_no()) >
						 * max_receipt_no){
						 * 
						 * System.out.println("Receipt No. not found------");
						 * query_receipt.setValidate("Receipt No. Notfound");
						 * 
						 * System.out.println("perpath--------"+perpath);
						 * req.setAttribute("flag",perpath); return
						 * map.findForward(ResultHelp.getSuccess());
						 * 
						 * }
						 */

						/*
						 * else if{
						 */
						query_receipt.setValidate("");
						if (query_receipt.getView().equalsIgnoreCase("View")) {
							DepositMasterObject[] depmastarray = null;
							System.out.println("Name==="
									+ query_receipt.getName());
							System.out.println("Receipt no==="
									+ query_receipt.getReceipt_no());
							depmastarray = tddelegate.getQueryReceipt(
									query_receipt.getAc_type(), query_receipt
											.getReceipt_no());
							if (depmastarray == null) {

								System.out
										.println("I am in Query on receipt records nt found");
								query_receipt
										.setTesting("Receipt Number Not Found!!!");
							} else {
								req.setAttribute("queryreceipt", depmastarray);
							}

							System.out.println("after req.setattribute==="
									+ query_receipt.getName());

							// }
						}
					}
					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// code for Receipt Updation

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/ReceiptUpdationMenu")) {
			System.out.println("***********1111");
			try {

				ReceiptUpdation receipt_updation = (ReceiptUpdation) form;
				System.out.println("***********1112");
				String pageId = receipt_updation.getPageId();
				System.out.println("***********1113" + pageId);
				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(receipt_updation
						.getPageId())) {

					path = MenuNameReader.getScreenProperties(receipt_updation
							.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/ReceiptUpdation")) {

			try {
				System.out.println("Inside Reciept updation=====>");
				TDDelegate tddelegate = new TDDelegate();
				ReceiptUpdation receipt_updation = (ReceiptUpdation)form;
				String pageId = receipt_updation.getPageId();

				System.out.println("pageId===" + receipt_updation.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);

				if (MenuNameReader.containsKeyScreen(receipt_updation
						.getPageId())) {
					path = MenuNameReader.getScreenProperties(receipt_updation
							.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu- ReceiptUpdation ");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					if (receipt_updation.getFrom_ac_no() != 0
							&& receipt_updation.getTo_ac_no() != 0) {

						System.out.println("account type=="
								+ receipt_updation.getAc_type());

						System.out.println("from date==="
								+ receipt_updation.getFrom_ac_no());

						System.out.println("is it FD 0r 1003???"
								+ receipt_updation.getAc_type());

						if (receipt_updation.getAc_type().equalsIgnoreCase(
								"1003001")
								|| receipt_updation.getAc_type()
										.equalsIgnoreCase("1004001")
								|| receipt_updation.getAc_type()
										.equalsIgnoreCase("1005001")
								|| receipt_updation.getAc_type()
										.equalsIgnoreCase("1005002")
								|| receipt_updation.getAc_type()
										.equalsIgnoreCase("1005003")) {
							System.out
									.println("Inside Reciept updation FD=====>");

							// req.setAttribute("receiptupdation",tddelegate.getFdReceiptUpdation(receipt_updation.getFrom_ac_no(),
							// receipt_updation.getTo_ac_no(),
							// receipt_updation.getAc_type()));

							System.out.println("HHHHHHHHhmmmmmmmmmmmm.......");

							int FromAc_no = receipt_updation.getFrom_ac_no();

							int To_acno = receipt_updation.getFrom_ac_no();

							System.out.println("FromAc_no====> " + FromAc_no);
							System.out.println("To_acno===> " + To_acno);
							// as we can update only on recode at a time the
							// code is changed to fetch only one record;

							DepositMasterObject[] dep_receipt_obj = tddelegate
									.getFdReceiptUpdation(null,
											receipt_updation.getFrom_ac_no(),
											receipt_updation.getTo_ac_no(),
											receipt_updation.getAc_type(), 0);

							// DepositMasterObject[]
							// dep_receipt_obj=tddelegate.getFdReceiptUpdation(null,FromAc_no,
							// To_acno, receipt_updation.getAc_type(),0);
							System.out
									.println("The value of dep_receipt_obj is====> "
											+ dep_receipt_obj);
							if (dep_receipt_obj == null) {
								System.out.println("I am inside null hello");
								receipt_updation
										.setTesting("RecordsNotFound!!");
							}

							List receipt_list = new ArrayList();

							System.out.println("After listtttttttttt");
							// System.out.println("The length of the recipt is "+dep_receipt_obj.length);
							// since more than one record for loop is required..
							if (dep_receipt_obj != null) {
								for (int i = 0; i < dep_receipt_obj.length; i++) {

									Map m = new TreeMap();
									System.out
											.println(".......inside MAp.....");
									System.out.println("No of Records=====>>>"
											+ dep_receipt_obj.length);
									m.put("id", i + 1);
									m.put("acc_no", dep_receipt_obj[i]
											.getAccNo());
									m.put("depositorname", dep_receipt_obj[i]
											.getName());
									m.put("dep_date", dep_receipt_obj[i]
											.getDepDate());
									m.put("mat_date", dep_receipt_obj[i]
											.getMaturityDate());
									m.put("depamt", dep_receipt_obj[i]
											.getDepositAmt());
									m.put("matamt", dep_receipt_obj[i]
											.getMaturityAmt());
									m.put("intrate", dep_receipt_obj[i]
											.getInterestRate());
									m.put("intfreq", dep_receipt_obj[i]
											.getInterestFrq());
									m.put("receiptno", dep_receipt_obj[i]
											.getReceiptno());
									m.put("newreceiptno", dep_receipt_obj[i]
											.getNewReceipt());

									receipt_list.add(m);

									System.out.println(".....After MAP....");

								}

								session = req.getSession(true);
								session.setAttribute("receipt_updation",
										receipt_list);
								req.setAttribute("receipt_updation",
										receipt_list);
							}
							System.out.println("inside fd loop  done..");
						}

					} else {
						System.out
								.println("Inside Reciept updation  ELSE BLOCK=====>");

						System.out.println("other than fd...."
								+ receipt_updation.getAc_type());

						req.setAttribute("receiptupdation", tddelegate
								.getFdReceiptUpdation(null, receipt_updation
										.getFrom_ac_no(), receipt_updation
										.getTo_ac_no(), receipt_updation
										.getAc_type(), 0));

					}

					String method = req.getParameter("method");
					System.out.println("method value----->>>" + method);

					String id = req.getParameter("id");

					System.out.println("The id is " + id);
                   	
					if (method.trim().equalsIgnoreCase("Save")) {

						List rec_update = (ArrayList) session
								.getAttribute("receipt_updation");
						if (rec_update != null) {

							for (int i = 0; i < rec_update.size(); i++) {
								Map m = (TreeMap) rec_update.get(i);
								String map_id = m.get("id").toString();
								if (map_id.equals(req.getParameter("id"))) {
									m.put("id", map_id);
									m.put("acc_no", req.getParameter("accno"));
									m.put("depositorname", req
											.getParameter("name"));
									m.put("dep_date", req
											.getParameter("depositdate"));

									m.put("mat_date", req
											.getParameter("matdate"));
									m.put("depamt", req.getParameter("depamt"));
									m.put("matamt", req.getParameter("matamt"));
									m.put("intrate", req
											.getParameter("intrate"));
									// m.put("intfreq",req.getParameter("intfreq"));
									m.put("receiptno", req
											.getParameter("currrctno"));
									m.put("newreceiptno", req
											.getParameter("newrctno"));
									System.out.println("the new recipt no iis "
											+ req.getParameter("newrctno"));

									rec_update.set(i, m);
								}
							}
						}

					
                    }
					if (method != null) {
						if (method.equalsIgnoreCase("update")) {

							List rec_update = (ArrayList) session
									.getAttribute("receipt_updation");

							DepositMasterObject dep_recobj[] = new DepositMasterObject[1];
							dep_recobj[0] = new DepositMasterObject();

							if (rec_update != null) {

								for (int i = 0; i < rec_update.size(); i++) {

									Map map_rec = new TreeMap();

									map_rec = (TreeMap) rec_update.get(i);
									String map_id = map_rec.get("id")
											.toString();
									if (id.equals(map_id)) {

										System.out
												.println("actype in action class----->>>"
														+ receipt_updation
																.getAc_type());
										System.out.println("acc_no=====>>>"
												+ map_rec.get("acc_no"));
										System.out.println("name===>>"
												+ map_rec.get("depositorname"));

										System.out.println("dep date =====>>>"
												+ map_rec.get("dep_date"));

										System.out.println("mat date=====>>>"
												+ map_rec.get("mat_date"));
										System.out.println("dep amt=====>>>"
												+ map_rec.get("depamt"));

										System.out.println("mat amt=====>>>"
												+ map_rec.get("matamt"));

										System.out.println("int rate=====>>>"
												+ map_rec.get("intrate"));

										System.out.println("int freq=====>>>"
												+ map_rec.get("intfreq"));

										System.out.println("receipt no=====>>>"
												+ map_rec.get("receiptno"));

										System.out
												.println("new receipt no=====>>>"
														+ req
																.getParameter("newrctno"));

										if (receipt_updation.getAc_type()
												.equalsIgnoreCase("1003001")) {
											dep_recobj[0].setAccType("1003001");
										} else if (receipt_updation
												.getAc_type().equalsIgnoreCase(
														"1004001")) {
											dep_recobj[0].setAccType("1004001");
										} else if (receipt_updation
												.getAc_type().equalsIgnoreCase(
														"1005001")) {
											dep_recobj[0].setAccType("1005001");
										} else if (receipt_updation
												.getAc_type().equalsIgnoreCase(
														"1005002")) {
											dep_recobj[0].setAccType("1005002");
										} else if (receipt_updation
												.getAc_type().equalsIgnoreCase(
														"1005003")) {
											dep_recobj[0].setAccType("1005003");
										}

										dep_recobj[0].setAccNo(Integer
												.parseInt(map_rec.get("acc_no")
														.toString()));
										dep_recobj[0].setName(map_rec.get(
												"depositorname").toString());
										dep_recobj[0].setDepDate(map_rec.get(
												"dep_date").toString());
										dep_recobj[0].setMaturityDate((map_rec
												.get("mat_date").toString()));

										dep_recobj[0].setMaturityAmt(Double
												.parseDouble((map_rec
														.get("depamt"))
														.toString()));

										dep_recobj[0].setDepositAmt(Double
												.parseDouble((map_rec
														.get("matamt"))
														.toString()));
										dep_recobj[0]
												.setInterestRate((Double
														.parseDouble(map_rec
																.get("intrate")
																.toString())));

										// dep_recobj[0].setInterestFrq(map_rec.get("intfreq").toString());

										// dep_recobj[0].setReceiptno(Integer.parseInt((map_rec.get("receiptno").toString())));

										dep_recobj[0]
												.setReceiptno(Integer
														.parseInt(req
																.getParameter(("newrctno")
																		.toString())));

										System.out
												.println("&&&&&&&&&&&&Before Updation&&&&&&&&&&");

										dep_recobj[0].setRctUpdate(true);

										tddelegate.getFdReceiptUpdation(
												dep_recobj, receipt_updation
														.getFrom_ac_no(),
												receipt_updation.getTo_ac_no(),
												receipt_updation.getAc_type(),
												1);

										System.out
												.println("after updation in account opening action");
									}

								}
								receipt_updation.setRefreshpage("referesh");
							}
							receipt_updation.setRefreshpage("referesh");
						}
					}

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for Query on Receipt No

		// code for Receipt Printing

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/ReceiptPrintingMenu")) {
			System.out.println("***********1111");
			try {

				ReceiptUpdation receipt_updation = (ReceiptUpdation) form;
				System.out.println("***********1112");
				String pageId = receipt_updation.getPageId();
				System.out.println("***********1113" + pageId);
				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(receipt_updation
						.getPageId())) {

					path = MenuNameReader.getScreenProperties(receipt_updation
							.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/ReceiptPrinting")) {
			try {

				TDDelegate tddelegate = new TDDelegate();
				ReceiptUpdation receiptPrinting = (ReceiptUpdation) form;
				String pageId = receiptPrinting.getPageId();

				System.out.println("pageId===" + receiptPrinting.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
				DepositMasterObject[] dm, dm_ver = null;

				if (MenuNameReader.containsKeyScreen(receiptPrinting
						.getPageId())) {
					path = MenuNameReader.getScreenProperties(receiptPrinting
							.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside menu- ReceiptUpdation ");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					if (receiptPrinting.getFrom_ac_no() != 0
							&& receiptPrinting.getTo_ac_no() != 0) {
						/*
						 * try{
						 * 
						 * dm=tddelegate.getReceiptPrinting(receiptPrinting.
						 * getFrom_ac_no
						 * (),receiptPrinting.getTo_ac_no(),0,receiptPrinting
						 * .getAc_type());// query to Server
						 * System.out.println("hooo hi"); } catch
						 * (RecordsNotFoundException rec) {
						 * if((JOptionPane.showConfirmDialog
						 * (null,"Do you want to reprint the record ? "
						 * ,"Info",JOptionPane
						 * .YES_NO_OPTION))==JOptionPane.YES_OPTION) {
						 * 
						 * dm=tddelegate.getReceiptPrinting(receiptPrinting.
						 * getFrom_ac_no
						 * (),receiptPrinting.getTo_ac_no(),1,receiptPrinting
						 * .getAc_type()); System.out.println("mahesh ");
						 * 
						 * } }
						 */

						System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDD=====>"
								+ receiptPrinting.getForward());

						if (receiptPrinting.getAc_type().equalsIgnoreCase(
								"1003%")
								|| receiptPrinting.getAc_type()
										.equalsIgnoreCase("1004001")
								|| receiptPrinting.getAc_type()
										.equalsIgnoreCase("1005001")
								|| receiptPrinting.getAc_type()
										.equalsIgnoreCase("1005002")
								|| receiptPrinting.getAc_type()
										.equalsIgnoreCase("1005003")) {

							// req.setAttribute("receiptupdation",tddelegate.getFdReceiptUpdation(receipt_updation.getFrom_ac_no(),
							// receipt_updation.getTo_ac_no(),
							// receipt_updation.getAc_type()));

							System.out.println("HHHHHHHHhmmmmmmmmmmmm.......");

							dm = tddelegate.getReceiptPrinting(receiptPrinting
									.getFrom_ac_no(), receiptPrinting
									.getTo_ac_no(), 1, receiptPrinting
									.getAc_type());

							if (dm == null) {
								System.out
										.println("i am in NUll Receipt Primnting");
								receiptPrinting.setTesting("Records Not Found");
							} else if (dm[0].getReceiptno() != 0) {
								receiptPrinting.setTesting("Records Not Found");
							}

							else {

								List receipt_list = new ArrayList();

								System.out.println("After listtttttttttt");
								System.out
										.println("The length of the recipt is "
												+ dm.length);
								// since more than one record for loop is
								// required..

								System.out.println("Account Number-----> "
										+ receiptPrinting.getAc_type());
								System.out.println("From account number----> "
										+ receiptPrinting.getFrom_ac_no());
								System.out.println("To account Number---> "
										+ receiptPrinting.getTo_ac_no());

								if (dm != null) {
									for (int i = 0; i < dm.length; i++) {
										boolean updated = false;
										Map m = new TreeMap();
										System.out
												.println(".......inside MAp.....");
										System.out
												.println("No of Records=====>>>"
														+ dm.length);
										System.out.println("DM.Ac_no===> "
												+ dm[i].getAccNo());
										int lastrectno = dm[i].getLastRctNo();
										m.put("id", i + 1);
										m.put("acc_no", dm[i].getAccNo());
										m.put("depositorname", dm[i].getName());
										m.put("dep_date", dm[i].getDepDate());
										m.put("mat_date", dm[i]
												.getMaturityDate());
										m.put("depamt", dm[i].getDepositAmt());
										m.put("matamt", dm[i].getMaturityAmt());
										m.put("intrate", dm[i]
												.getInterestRate());
										m
												.put("intfreq", dm[i]
														.getInterestFrq());
										m
												.put("receiptno", dm[i]
														.getReceiptno());
										// m.put("newreceiptno",dm[i].getNewReceipt());
										System.out
												.println("Sumanth The new Receipt number is-----> "
														+ dm[i].getLastRctNo());

										int changed = ++lastrectno;
										System.out
												.println("The New receipt number is---------> "
														+ changed);
										m.put("newreceiptno", (changed));
										dm[i].setModulecode(receiptPrinting
												.getAc_type());
										dm[i].setReceiptno(changed);
										dm[i].setAccType(receiptPrinting
												.getAc_type());
										dm[i].setAccNo(receiptPrinting
												.getFrom_ac_no());
										updated = tddelegate
												.updateReceiptPrint(dm);
										receipt_list.add(m);

										System.out
												.println(".....After MAP....");
										// receiptPrinting.setTesting("The Receipt Number is: "+changed);

									}
								}
								session = req.getSession(true);
								session.setAttribute("receipt_updation",
										receipt_list);
								req.setAttribute("receipt_updation",
										receipt_list);

								System.out.println("inside fd loop  done..");

							}
						}
						/*
						 * else {
						 * 
						 * 
						 * 
						 * System.out.println("other than fd...."+receiptPrinting
						 * .getAc_type());
						 * 
						 * req.setAttribute("receipt_updation",tddelegate.
						 * getReceiptPrinting
						 * (receiptPrinting.getFrom_ac_no(),receiptPrinting
						 * .getTo_ac_no(),1,receiptPrinting.getAc_type()));
						 * 
						 * }
						 */

					}

				}

				// return map.findForward(ResultHelp.getSuccess());

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,
							path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for Receipt Printing

		// code for Voucher Payment

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/VoucherPaymentMenu")) {

			try {

				System.out.println("Lets start-----------------");
				VoucherPayment vouch_payment = (VoucherPayment) form;
				String pageId = vouch_payment.getPageId();

				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(vouch_payment.getPageId())) {

					path = MenuNameReader.getScreenProperties(vouch_payment.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}
		} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/VoucherPayment")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				VoucherPayment vouch_payment = (VoucherPayment) form;
				String pageId = vouch_payment.getPageId();
				Double amt = 0.0;
				SignatureInstructionObject[] sign_detal = null;
				System.out.println("voucher " + vouch_payment.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
				if (MenuNameReader.containsKeyScreen(vouch_payment.getPageId())) {
					path = MenuNameReader.getScreenProperties(vouch_payment.getPageId());
					setInitParam(req, path, tddelegate);
					req.setAttribute("pageId", path);
				}
				if (vouch_payment.getAc_no() != 0) {
					if (vouch_payment.getDetails().equals("Personal")) {
						DepositMasterObject depmasterverify_val = new DepositMasterObject();
						depmasterverify_val = tddelegate.getDepositMasterValues(vouch_payment.getAc_no(), vouch_payment.getAc_type());
						if (depmasterverify_val != null) {

							int cid = depmasterverify_val.getCustomerId();
							if (cid != 0) {
								req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
								req.setAttribute("personalInfo", tddelegate.getCustomer(cid));
								req.setAttribute("panelName",CommonPanelHeading.getPersonal());
							}
						}
					}
					/*
					 * else
					 * if(vouch_payment.getDetails().equals("SignatureDetails"
					 * )){ sign_detal=new SignatureInstructionObject[0];
					 * DepositMasterObject depmasterverify_val = new
					 * DepositMasterObject();
					 * sign_detal=tddelegate.getSignatureDetails
					 * (vouch_payment.getAc_no(), vouch_payment.getAc_type());
					 * System
					 * .out.println("------sign_detal------>>>>>"+sign_detal);
					 * if(sign_detal!=null){
					 * 
					 * req.setAttribute("SignInfo",sign_detal);
					 * req.setAttribute(
					 * "flag",MenuNameReader.getScreenProperties("0007"));
					 * req.setAttribute
					 * ("panelName",CommonPanelHeading.getPersonal()); } }
					 */

					if (vouch_payment.getAc_type().equalsIgnoreCase("1003001")) {

						System.out.println("HHHHHHHHhmmmmmmmmmmmm.......");

						TrfVoucherObject[] trf_voucher = tddelegate.getVoucherDetails(vouch_payment.getAc_no(),vouch_payment.getAc_type(), 0);

						if (trf_voucher == null) {
							vouch_payment.setTesting("Records Not Found");

						}
						System.out.println("!!!!!!!!!!be happy!!!!!!!");

						List trf_list = new ArrayList();

						System.out.println("After listtttttttttt");
						// System.out.println("The length of the voucher--"+trf_voucher.length);
						// since more than one record for loop is required..

						for (int i = 0; i < trf_voucher.length; i++) {

							Map m = new TreeMap();
							System.out.println(".......inside MAp.....");
							m.put("id", i + 1);
							m.put("acc_no", trf_voucher[i].getAccNo());
							m.put("Voucher_no", trf_voucher[i].getVoucherNo());

							m.put("Voucher_date", trf_voucher[i].getVoucherDate());

							m.put("Voucher_Amount", trf_voucher[i].getTrfAmount());

							System.out.println("trf_voucher[i].getVoucherNo---->>"+ trf_voucher[i].getVoucherNo());
							session = req.getSession(true);
							session.setAttribute("voucherpayment", trf_list);
							trf_list.add(m);

							System.out.println(".....After MAP....");
						}
						// This part of the code is for CHECKBOX.....
						/* session is used to hold the data of the page */

						if (vouch_payment.getFlag().equalsIgnoreCase("true")) {

							// this below session holds the list values.

							session = req.getSession(true);

							session.setAttribute("voucherpayment", trf_list);

							System.out.println("flag==="+ vouch_payment.getFlag());

							req.setAttribute("voucherpayment", trf_list);// this
							// is
							// to
							// pass
							// for
							// the
							// JSP
							// page

							System.out.println("inside flaggggggggg...");

						}
						// till(above) here to display the JSP page

						// the below code is executed when checkbox is clicked
						// in the jsp page
						// "id" - to get the check box id.

						if (req.getParameter("id") != null) {

							String id = req.getParameter("id");
							System.out.println("&&&&&&&&&&" + id);

							List vouc_pay = (ArrayList) session.getAttribute("voucherpayment");

							System.out.println("vouch_pay >>>>>>>" + vouc_pay);
							System.out.println("id---------->>>>"+ req.getParameter("id"));

							for (int i = 0; i < vouc_pay.size(); i++) {
								Map map_pay = (TreeMap) vouc_pay.get(i);
								String id1 = map_pay.get("id").toString();
								if (vouc_pay != null) {
									if (id.equals(id1)) {
										System.out.println("****************************"+ map_pay.get("Voucher_Amount"));
										amt = (Double) map_pay.get("Voucher_Amount");
										req.setAttribute("sbamt", amt);// to set
										// the
										// total
										// amount
										// field
										// in
										// the
										// text
										// box

									}

								}
							}

						}

						System.out.println("inside fd loop  done..");
					}

				} else {

					System.out.println("other than fd...."	+ vouch_payment.getAc_type());
					req.setAttribute("Voucherpayment", tddelegate.getVoucherDetails(vouch_payment.getAc_no(),vouch_payment.getAc_type(), 0));

				}

				String method = req.getParameter("method");// fetching method
				// value from JSP
				// wen submit is
				// clicked.

				if (method != null) {
					System.out.println("method value----->>>" + method);
				}

				System.out.println("Adddddd Value====>"+ vouch_payment.getAdd());

				String id5 = req.getParameter("id");

				if (method != null && method.equalsIgnoreCase("submit")) {

					System.out.println("The id5 is----------???? " + id5);

					TrfVoucherObject trf_voucher[] = new TrfVoucherObject[1];

					trf_voucher[0] = new TrfVoucherObject();

					System.out.println("session.getAttributereceipt_updation------------------->"+ session.getAttribute("receipt_updation"));

					// List trf_sumbmit =
					// (ArrayList)session.getAttribute("voucherpayment");

					List trf_sumbmit = (ArrayList) session.getAttribute("receipt_updation");

					System.out.println(" the value of TRF_SUBMIT-----> "+ trf_sumbmit);

					if (trf_sumbmit != null) {

						for (int i = 0; i < trf_sumbmit.size(); i++) {

							Map map_submit = new TreeMap();

							map_submit = (TreeMap) trf_sumbmit.get(i);
							String map_id = map_submit.get("id").toString();
							if (id5.equals(map_id)) {

								System.out.println("acc_no=====>>>"+ map_submit.get("acc_no"));

								System.out.println("Acctype===="+ map_submit.get("Voucher_no").toString());

								System.out.println("Voucher No=========>>"+ map_submit.get("Voucher_no").toString());
								System.out.println("Voucher date--->"+ map_submit.get("Voucher_date").toString());
								System.out.println("Voucher Amount---->"+ map_submit.get("Voucher_date").toString());
								// Com By Sumanth reason(It was not Reachable)
								trf_voucher[0].setAccNo(Integer.parseInt(map_submit.get("acc_no").toString()));
								trf_voucher[0].setAccType(map_submit.get("Voucher_no").toString());
								trf_voucher[0].setVoucherNo(Integer.parseInt(map_submit.get("Voucher_no").toString()));
								trf_voucher[0].setVoucherDate(map_submit.get("Voucher_date").toString());
								trf_voucher[0].setTrfAmount(Double.parseDouble(map_submit.get("Voucher_Amount").toString()));

								int number = tddelegate.storeDepositTransferVoucher(trf_voucher, tddelegate.getSysdate());

								System.out.println("---------Voucher submitting here--------"+ number);

							}
							//

						}

						// String id=req.getParameter("id");

						System.out.println("The id is----------???? " + id5);
						/*
						 * if(req.getParameter("id")!=null){
						 * session.getAttribute("Voucherpayment"); List
						 * vouc_pay=
						 * (ArrayList)session.getAttribute("Voucherpayment");
						 * System.out.println("((((((((((((((((("+vouc_pay);
						 * System
						 * .out.println("id---------->>>>"+req.getParameter
						 * ("id")); for(int i=0;i<vouc_pay.size();i++){ Map
						 * map_pay=(TreeMap)vouc_pay.get(i); String
						 * id1=map_pay.get("id").toString(); if(vouc_pay!=null){
						 * if(id.equals(id1)){
						 * System.out.println("****************************"
						 * +map_pay.get("Voucher_Amount")); } } }
						 * 
						 * 
						 * } }
						 */

					}
				}

				else if (vouch_payment.getAdd().equalsIgnoreCase("Add")) {

					if (vouch_payment.getAc_no() == 0 || String.valueOf(vouch_payment.getAc_no()) == ""
							|| vouch_payment.getAc_no() < 0) {
						vouch_payment.setTesting("Please Enter AccountNumber");
					} else {
						String cbox[] = req.getParameterValues("id");
						double trf_amt = 0.0;
						tddelegate = new TDDelegate();

						List trf_sumbmit = (ArrayList) session.getAttribute("voucherpayment");
						System.out.println("the valude of Session ADD---> "	+ trf_sumbmit);
						if (trf_sumbmit != null) {
							TrfVoucherObject trf_voucher[] = new TrfVoucherObject[1];

							trf_voucher[0] = new TrfVoucherObject();

							for (int i = 0; i < trf_sumbmit.size(); i++) {

								Map map_submit = new TreeMap();

								map_submit = (TreeMap) trf_sumbmit.get(i);
								String map_id = map_submit.get("id").toString();

								for (int k = 0; k < cbox.length; k++) {

									if (cbox[k].equals(map_id)) {

										System.out.println("acc_no=====>>>"	+ map_submit.get("acc_no"));
										System.out.println("Acctype===="+ map_submit.get("Voucher_no").toString());
										System.out.println("Trf amt======>"+ map_submit.get("Voucher_Amount"));
										System.out.println("voucher date----->"+ map_submit.get("Voucher_date"));
										trf_voucher[0].setTvPayInd(tml);
										trf_voucher[0].setTvPrtInd("Sumanth");
										trf_voucher[0].setTvPayDate(tddelegate.getSysdate());
										trf_amt += Double.parseDouble(map_submit.get("Voucher_Amount").toString());
										System.out.println("11111111111");
										trf_voucher[0].setAccNo(Integer.parseInt(map_submit.get("acc_no").toString()));
										System.out.println("2222222222");
										trf_voucher[0].setAccType(vouch_payment.getAc_type());
										System.out.println("Account type"+ vouch_payment.getAc_type());
										trf_voucher[0].setVoucherNo(Integer.parseInt(map_submit.get("Voucher_no").toString()));
										System.out.println("44444444444");
										trf_voucher[0].setVoucherDate(map_submit.get("Voucher_date").toString());
										System.out.println("555555555"+ map_submit.get("Voucher_date").toString());
										trf_voucher[0].setTrfAmount(Double.parseDouble(map_submit.get("Voucher_Amount").toString()));
										System.out.println("666666666"+ vouch_payment.getPay_mode());
										if (vouch_payment.getPay_mode() != null) {
											System.out.println("Testingh  ===>");
											if (vouch_payment.getPay_mode().equalsIgnoreCase("Cash")) {
												System.out.println("Cash(Action) 1 ===>");
												trf_voucher[0].setPayMode("C");
												trf_voucher[0].setPayAccType(null);
												trf_voucher[0].setPayAccNo(0);
											} else if (vouch_payment.getPay_mode().equalsIgnoreCase("Transfer")) {
												System.out.println("Transfer(Action) 2 ===>");
												trf_voucher[0].setPayMode("T");
												trf_voucher[0].setPayAccType(vouch_payment.getPay_ac_type());
												if (vouch_payment.getPay_mode_ac_no() != null)
													trf_voucher[0].setPayAccNo(Integer.parseInt(vouch_payment.getPay_mode_ac_no()));

											} else if (vouch_payment.getPay_mode().equalsIgnoreCase("Q/DD/PO")) {
												System.out.println("Q/DDPO (Action) 1 ===>");
												trf_voucher[0].setPayMode("P");
												trf_voucher[0].setPayAccType(null);
												trf_voucher[0].setPayAccNo(0);

											}

										}
										int total = tddelegate.storeDepositTransferVoucher(	trf_voucher, tddelegate.getSysdate());

										System.out.println("============Checking=================== "+ total);
										if (total > 0) {
											vouch_payment.setTesting("Note Down Reference No :"+ total + ".");

										} else {
											vouch_payment.setTesting("Successfully Completed");
										}

									}
									System.out.println("The total amt is77777777"+ trf_amt);

									/*
									 * else{ trf_sumbmit =
									 * (ArrayList)session.getAttribute
									 * ("voucherpayment");
									 * req.setAttribute("voucherpayment"
									 * ,trf_sumbmit); }
									 */

									System.out.println("a am in Add");

								}

								req.setAttribute("trf_amt", trf_amt);

							}

						} 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// code Ended For Voucher Payment

		// code for deposit view log

		else if (map.getPath().trim().equalsIgnoreCase(
				"/TermDeposit/DepositViewLogMenu")) {

			try {
				System.out.println("inside DepositViewLogMenu++++---------->");

				DepositViewLog view_log = (DepositViewLog) form;

				String pageId = view_log.getPageId();

				req.setAttribute("pagename", pageId);

				System.out.println("pageid=====" + pageId);

				if (MenuNameReader.containsKeyScreen(view_log.getPageId())) {

					path = MenuNameReader.getScreenProperties(view_log.getPageId());

					TDDelegate tDelegate = new TDDelegate();

					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);

					System.out.println("the path" + path);

					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
			                     	e.printStackTrace();
			                     }

		}

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/DepositViewLog")) {
			try {
				TDDelegate tDelegate = new TDDelegate();
				DepositViewLog view_log = (DepositViewLog) form;
				String pageId = view_log.getPageId();

				System.out.println("deposit view log" + view_log.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tDelegate);

				if (MenuNameReader.containsKeyScreen(view_log.getPageId())) {
					path = MenuNameReader.getScreenProperties(view_log.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside view log");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					Object array_object[][] = null;

					if (view_log.getAc_no() != 0) {
						System.out.println("Hello i am inside here dude;;;;;");
						AccountObject acc_obj = new AccountObject();

						// acc_obj =
						// tddelegate.getAcccountDetails(view_log.getAc_type(),
						// null, view_log.getAc_no(),TDDelegate.getSysdate());

						array_object = tddelegate.getRowsviewlog(
								"DepositMasterLog", "ac_no", "ac_type",
								view_log.getAc_no(), view_log.getAc_type(), 0);

						        req.setAttribute("viewlog", array_object);

						if (array_object == null || array_object.equals("null")) {

							System.out.println("inside acconut not found");

							view_log.setValidation("Account Not Found!!!");
							view_log.setTesting("Account Not Found!!!");
						} else {

							view_log.setValidation("");

						}

					}

					/*
					 * if(view_log.getAc_no()!=0){
					 * 
					 * System.out.println("acno==="+view_log.getAc_no());
					 * 
					 * DepositMasterObject[] depmastobj=null;
					 * 
					 * depmastobj =
					 * tddelegate.getPassbookInfo(view_log.getAc_type
					 * (),view_log.getAc_no());
					 * 
					 * if(depmastobj==null){
					 * 
					 * System.out.println("depmast obj===="+depmastobj);
					 * 
					 * view_log.setValidation("Deposit Account Not Found!!");
					 * 
					 * } else{
					 * 
					 * view_log.setValidation("");
					 * req.setAttribute("viewlog",tddelegate
					 * .getPassbookInfo(view_log
					 * .getAc_type(),view_log.getAc_no()));
					 * 
					 * }
					 * 
					 * System.out.println("after req.setattribute===");
					 * 
					 * }
					 */

					return map.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// code ended for deposit view log file

		// code for Term Deposit Ledger

		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/LedgerMenu")) {

			try {
				System.out.println("I am inside /TermDeposit/LedgerMenu----------> ");
				Ledger td_ledger = (Ledger) form;
				String pageId = td_ledger.getPageId();
				TDDelegate tDelegate = new TDDelegate();
				// req.setAttribute("pagename",pageId);
				td_ledger.setSysdate(tDelegate.getSysdate());

				System.out.println("c" + pageId);

				if (MenuNameReader.containsKeyScreen(td_ledger.getPageId())) {

					path = MenuNameReader.getScreenProperties(td_ledger.getPageId());
					setInitParam(req, path, tDelegate);
					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/TermDeposit/Ledger")) {
			try {
				TDDelegate tddelegate = new TDDelegate();
				Ledger td_ledger = (Ledger) form;
				String pageId = td_ledger.getPageId();
				DepositMasterObject[] dep_obja, dep_objectdate;
				dep_obja = null;
				dep_objectdate = null;
				System.out.println("ledger" + td_ledger.getPageId());
				req.setAttribute("pagename", pageId);
				setInitParam(req, path, tddelegate);
				TDDelegate tDelegate = new TDDelegate();
				td_ledger.setSysdate(tDelegate.getSysdate());

				if (MenuNameReader.containsKeyScreen(td_ledger.getPageId())) {
					path = MenuNameReader.getScreenProperties(td_ledger.getPageId());
					tddelegate = new TDDelegate();
					System.out.println("inside view log");
					setInitParam(req, path, tddelegate);

					System.out.println("path****" + path);
					req.setAttribute("pageId", path);
					System.out.println("the path" + path);

					System.out.println("Hide Paramerter----> "+ req.getParameter("forward"));
					System.out.println("Forward value----> "+ td_ledger.getForward());
					if (td_ledger.getSubval() != null) {
						System.out.println("Hide value in scion----> "+ td_ledger.getSubval());
					}

					/*
					 * if(td_ledger.getAc_type().equalsIgnoreCase("1003001"))
					 * //||
					 * td_ledger.getAc_type().equalsIgnoreCase("1004001")||td_ledger
					 * .getAc_type().equalsIgnoreCase("1005001")) {
					 */
					if (td_ledger.getBy_date() != null) {

						if (td_ledger.getBy_date().equalsIgnoreCase("on")) {

							System.out.println("geetha nayak is visible or not?"+ td_ledger.getBy_date());
							req.setAttribute("invisi", "visible");
						}
					}

					System.out.println("hhhhhhhhhhhhhhhi TDLedger=====> "+ td_ledger.getForward());
					if (td_ledger.getForward().equalsIgnoreCase("View")) {

						System.out.println("GEETHA INSIDE LEDGER");
						if (td_ledger.getOpen_close_stat().equalsIgnoreCase("OpenAccounts")) {
							System.out.println("open close status====="+ td_ledger.getOpen_close_stat());
							System.out.println("from ac no in action class===="	+ td_ledger.getFrom_date());
							System.out.println("to ac no in action class========"+ td_ledger.getTo_date());

							req.setAttribute("openclosed", tddelegate.getOpenAccounts(td_ledger.getAc_type(),
											td_ledger.getFrom_date(), td_ledger
													.getTo_date(), 0));

							dep_obja = dep_obja = tddelegate.getLedgerDetails(
									td_ledger.getFrom_acno(), td_ledger
											.getTo_acno(), td_ledger
											.getFrom_date(), td_ledger
											.getTo_date(), td_ledger
											.getAc_type(), 2);
							// dep_objectdate=tddelegate.getLedgerDetails((td_ledger.getFrom_date()),(td_ledger.getTo_date()),td_ledger.getFrom_date(),td_ledger.getTo_date(),
							// td_ledger.getAc_type(),2);
							if (dep_obja != null) {
								req.setAttribute("ledger", dep_obja);
							} else if (dep_objectdate != null) {
								req.setAttribute("ledger", dep_objectdate);
							}
							System.out.println("*********Ledger entered**********");
						}

						else if (td_ledger.getOpen_close_stat().equalsIgnoreCase("CloseAccounts")) {

							System.out.println("open close status====="	+ td_ledger.getOpen_close_stat());
							dep_obja = tddelegate.getLedgerDetails(td_ledger
									.getFrom_acno(), td_ledger.getTo_acno(),
									td_ledger.getFrom_date(), td_ledger
											.getTo_date(), td_ledger
											.getAc_type(), 1);
							// dep_obja=tddelegate.getLedgerDetails(Integer.parseInt(td_ledger.getFrom_date()),Integer.parseInt(td_ledger.getTo_date()),td_ledger.getFrom_date(),td_ledger.getTo_date(),
							// td_ledger.getAc_type(),1);
							// req.setAttribute("ledger",
							// tddelegate.getLedgerDetails(Integer.parseInt(td_ledger.getFrom_date()),Integer.parseInt(td_ledger.getTo_date()),td_ledger.getFrom_date(),td_ledger.getTo_date(),
							// td_ledger.getAc_type(),1));
							if (dep_obja != null) {
								req.setAttribute("ledger", dep_obja);
							}
						}

						else if (td_ledger.getOpen_close_stat().equalsIgnoreCase("All")) {

							System.out.println("open close status====="+ td_ledger.getOpen_close_stat());

							dep_obja = tddelegate.getLedgerDetails(td_ledger.getFrom_acno(), td_ledger.getTo_acno(),td_ledger.getFrom_date(), td_ledger.getTo_date(), td_ledger.getAc_type(), 0);

							// req.setAttribute("ledger",
							// tddelegate.getLedgerDetails(Integer.parseInt(td_ledger.getFrom_date()),Integer.parseInt(td_ledger.getTo_date()),td_ledger.getFrom_date(),td_ledger.getTo_date(),
							// td_ledger.getAc_type(),0));
							if (dep_obja != null) {
								req.setAttribute("ledger", dep_obja);
							}
							System.out.println("{ inside fd loop..");
						}

						// }
					}
					// if loop of '1003001'
					if (td_ledger.getForward().equalsIgnoreCase("download")) {
						System.out.println("I am in download=======");
						DepositMasterObject[] deptrn_obj;
						deptrn_obj =tddelegate.getLedgerDetails(
								td_ledger.getFrom_acno(), td_ledger.getTo_acno(),
								td_ledger.getFrom_date(), td_ledger.getTo_date(), td_ledger.getAc_type(),2);
						if (deptrn_obj == null) {
							td_ledger.setTesting("Cannot Print");
						} else { 
							System.out.println(" i am inside down load");

							// TO save to an excel Sheet
							res.setContentType("application/.csv");
							res.setHeader("Content-disposition","attachment;filename=InterestAccruedReport.csv");

							java.io.PrintWriter out = res.getWriter();
							out.print("\n");
							out.print("\n");
							out.print("\n");
							out.print(",");
							out.print(",");
							out.print(",");
							out.print("TDLedger Details for A/C Type: "+ td_ledger.getAc_type());
							out.print("\n");
							out.print("\n");
							out.print("SrlNo");
							out.print(",");
							out.print("Ac/No");
							out.print(",");
							out.print("Depositor Name");
							out.print(",");
							out.print("DepositDate");
							out.print(",");
							out.print("MaturityDate");
							out.print(",");
							out.print("DepositDays");
							out.print(",");
							out.print("Interest Rate");
							out.print(",");
							out.print("Auto Renewal");
							out.print(",");
							out.print("Interest Freq");
							out.print(",");
							out.print("Deposit Amount");
							out.print(",");
							out.print("Maturity Amount");
							out.print(",");
							out.print("Interest Mode");
							out.print(",");
							out.print("Receipt Number");
							out.print(",");
							out.print("");
							out.print("\n");

							for (int k = 0; k < deptrn_obj.length; k++) {
								out.print(k+1);
								out.print(",");
								out.print(deptrn_obj[k].getAccNo());
								out.print(",");
								out.print(deptrn_obj[k].getName());
								out.print(",");
								out.print(deptrn_obj[k].getDepDate());
								out.print(",");
								out.print(deptrn_obj[k].getMaturityDate());
								out.print(",");
								out.print(deptrn_obj[k].getDepositDays());
								out.print(",");
								out.print(deptrn_obj[k].getInterestRate());
								out.print(",");
								out.print(deptrn_obj[k].getAutoRenewal());
								out.print(",");
								out.print(deptrn_obj[k].getInterestFrq());
								out.print(",");
								out.print(deptrn_obj[k].getDepositAmt());
								out.print(",");
							/*	out.print(deptrn_obj[k].getCumInterest());
								out.print(",");*/
								out.print(deptrn_obj[k].getMaturityAmt());
								out.print(",");
								out.print(deptrn_obj[k].getInterestMode());
								out.print(",");
								out.print(deptrn_obj[k].getReceiptno());
								out.print(",");
								out.print("\n");
							}

							req.setAttribute("msg","Saved to excel file in C:");
							return null;

						}
					}

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("The given request is not found", req,	path);

					return map.findForward(ResultHelp.getError());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// ledger Ended

		// Renewal notice ended here

		return map.findForward(ResultHelp.getSuccess());
	}

	private void setErrorPageElements(String exception, HttpServletRequest req,String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);

	}

	private void setInitParam(HttpServletRequest req, String path,
			TDDelegate tDelegate) throws Exception {
		req.setAttribute("pageId", path);
		req.setAttribute("date", TDDelegate.getSysdate());
		req.setAttribute("Dep type", tDelegate.getModulesTypes());
		req.setAttribute("intro_type", tDelegate.getModTypes());
		// System.out.println("module type"+tddelegate.getModulesTypes());
		req.setAttribute("details", tDelegate.getdetails());

		req.setAttribute("pay_mode", tDelegate.getpayMode());
		req.setAttribute("pay_actype", tDelegate.getpayAcctype());
		req.setAttribute("amt_recv", tDelegate.getpayMode());
		req.setAttribute("auto", tDelegate.getauto());

		req.setAttribute("combo_mat_cat1", tDelegate.getMatCat());
		req.setAttribute("int_freq", tDelegate.getInt_freq());
		req.setAttribute("visible1", "visible");
		req.setAttribute("invisible", "hidden");
		req.setAttribute("Combodate", tDelegate.getCombodate("1003001"));
		req.setAttribute("quantumlimit", tDelegate.getQuantumLimt(	"1003001", 1));// temp comment
		req.setAttribute("periodlimit", tDelegate.getPeriodLimit(
				"1003%,1004%,1005%", 0));
		req.setAttribute("opendetail", tDelegate.getopenclosedetails());
	}

	private void setAdminInitParam(HttpServletRequest req, String path,
			TDDelegate tDelegate) throws Exception {
		req.setAttribute("pageId", path);
		req.setAttribute("FdAcType", tDelegate.getAdminModules());
		req.setAttribute("IntType", tDelegate.getAdminTableNames());

	}

	// added by sumnath to cal maturity date is ammendments form if changed
	boolean setMaturityDate(TDDelegate deligate, ApplnDE appl,
			HttpServletRequest req) {
		try {

			int int_modulecode_minperiod = 0;
			ModuleObject[] array_moduleobject_td_acctype = deligate
					.getModulesTypes();
			int int_module_code_type = 0, index = -1;
			System.out.println("appl.getAc_type()===>" + appl.getAc_type());
			if (appl.getAc_type().equalsIgnoreCase("1003000")) {
				index = 0;
				int_module_code_type = 1;
			}
			if (appl.getAc_type().equalsIgnoreCase("1004000")) {
				index = 1;
				int_module_code_type = 2;
			}
			if (appl.getAc_type().equalsIgnoreCase("1005000")) {
				index = 2;
				int_module_code_type = 3;
			}

			if (index != -1)
				int_modulecode_minperiod = array_moduleobject_td_acctype[index]
						.getMinPeriod();

			if (appl.getPeriod_of_days() < int_modulecode_minperiod) {
				if (int_module_code_type == 2)
					// JOptionPane.showMessageDialog(null,"No of Installments should be greater than "+int_modulecode_minperiod);
					System.out.println("HI in if");
				else
					System.out.println("Hi from else");
				// JOptionPane.showMessageDialog(null,"No of Periods should be greater than "+int_modulecode_minperiod);
			}

			if (int_module_code_type == 2) {
				try {
					String mat_date = deligate.getFutureMonthDate(appl
							.getDep_date(), appl.getPeriod_of_days());
					System.out.println("Mat date1===>" + mat_date
							+ "Sysdate--> " + tddelegate.getSysdate());
					if (appl.getMat_date().length() == 0) {
						appl.setTesting("Maturity Date Shoud Not Be Zero");
					} else if (Validations.dayCompare(mat_date, tddelegate
							.getSysdate()) > 0) {

						int Days = Validations.dayCompare(appl.getMat_date(),
								tddelegate.getSysdate());
						System.out
								.println("in side date checking less than today s  date "
										+ Days);
						appl
								.setTesting("Maturity Date Cannot Be Less Than Todays Date");

					} else {
						appl.setTesting("Ok");
					}
					req.setAttribute("MatDateCal", mat_date);
					System.out.println("The NO of days-------> "
							+ appl.getPeriod_of_days());
					req.setAttribute("chperddays", appl.getPeriod_of_days());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			} else {
				String date;
				try {
					date = deligate.getFutureDayDate(appl.getDep_date(),
							Integer.parseInt(String.valueOf(appl
									.getPeriod_of_daystwo())));
					System.out.println("date2===>" + date);
					System.out.println("The NO of days-------> "
							+ appl.getPeriod_of_daystwo());
					System.out.println("Mat date1===>" + date + "Sysdate--> "
							+ tddelegate.getSysdate());
					if (appl.getMat_date().length() == 0) {
						appl.setTesting("Maturity Date Shoud Not Be Zero");
					} else if (Validations.dayCompare(date, tddelegate
							.getSysdate()) > 0) {

						int Days1 = Validations.dayCompare(appl.getMat_date(),
								tddelegate.getSysdate());
						System.out
								.println("in side date checking less than today s  date "
										+ Days1);
						appl
								.setTesting("Maturity Date Cannot Be Less Than Todays Date");

					} else {
						appl.setTesting("Ok");
					}

					req.setAttribute("MatDateCal", date);

					req.setAttribute("chperddays", appl.getPeriod_of_days());
					appl.setPeriod_of_daystwo(appl.getPeriod_of_daystwo());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return true;
	}

	boolean setInterestRate(TDDelegate deligate, ApplnDE appl,
			HttpServletRequest req) throws RemoteException {
		System.out.println("I am in setInterestRate------------------------->");

		ModuleObject[] array_moduleobject_td_acctype = deligate
				.getModulesTypes();
		double array_double_deposit_interest[] = { 0, 0, 0 };
		double old_rate = Double.valueOf((appl.getInt_rate()));
		DepositMasterObject dep_open_obj = new DepositMasterObject();
		for (int i = 0; i < 3; i++)
			array_double_deposit_interest[i] = 0;
		try {
			System.out.println("selected module code "
					+ deligate.getModulesTypes());

			System.out.println("---------------" + deligate.getModulesTypes()
					+ "-------Actyyappl.getAc_type(---" + appl.getAc_type()
					+ "--------" + 0 + "---------------" + 201);
			System.out.println("The date is =-----------> "
					+ deligate.getSysdate());
			if (appl.getAc_type().equalsIgnoreCase("1004001")) {
				System.out.println("Before---");
				array_double_deposit_interest = deligate
						.getDepositInterestRate(appl.getAc_type(), 0, 201,
								deligate.getSysdate(),
								appl.getPeriod_of_days(), appl.getDep_amt());
				System.out.println("aFTER----");
			} else if (appl.getAc_type().equalsIgnoreCase("1003000"))
				array_double_deposit_interest = deligate
						.getDepositInterestRate(appl.getAc_type(), 0, 201,
								deligate.getSysdate(),
								appl.getPeriod_of_days(), appl.getDep_amt());
			else
				array_double_deposit_interest = deligate
						.getDepositInterestRate(appl.getAc_type(), 0, 201,
								deligate.getSysdate(),
								appl.getPeriod_of_days(), appl.getDep_amt());

			System.out.println("Hey pls see this vvvvsdnfgdhsvalue===> "
					+ array_double_deposit_interest[0]);
			if (array_double_deposit_interest[0] != 0) {
				int_change = true;

				System.out.println("Int Payable-----> "
						+ numberFormat.format(array_double_deposit_interest[0]
								+ array_double_deposit_interest[1]
								+ array_double_deposit_interest[2]));

				// appl.setInt_payable(numberFormat.format(array_double_deposit_interest[0]+array_double_deposit_interest[1]+array_double_deposit_interest[2]));

				if (appl.getAc_type().equalsIgnoreCase("1004001")) {
					appl.setMat_amt(Double.parseDouble(numberFormat
							.format(Validations.rdInterestCalculation(Double
									.parseDouble(String.valueOf(appl
											.getDep_amt())), (appl
									.getPeriod_of_days()),
									(appl.getInt_rate()), appl.getDep_date(),
									appl.getMat_date()))));
					System.out
							.println("Maturity amt----MEEEWEEE------"
									+ Double
											.parseDouble(numberFormat
													.format(Validations
															.rdInterestCalculation(
																	Double
																			.parseDouble(String
																					.valueOf(appl
																							.getDep_amt())),
																	(appl
																			.getPeriod_of_days()),
																	(appl
																			.getInt_rate()),
																	appl
																			.getDep_date(),
																	appl
																			.getMat_date()))));
				}
				if (appl.getAc_type().equalsIgnoreCase("1003001"))
					appl.setMat_amt(appl.getDep_amt());
				else if (appl.getAc_type().startsWith("100500"))
					appl.setMat_amt(Double.parseDouble(numberFormat
							.format(Validations.reinvestmentCalc(Double
									.parseDouble(String.valueOf(appl
											.getDep_amt())),
									appl.getDep_date(), appl.getMat_date(),
									(appl.getInt_rate())))));

			}
			/*
			 * if(array_double_deposit_interest[0]!=0) {
			 */
			// combo_interest_rate.removeItemListener(this);
			/*
			 * String ComMatCaty[]=deligate.getMatCat();
			 * combo_interest_rate.removeAllItems();
			 * combo_interest_rate.addItem("NONE"); //obj_personal_person
			 * combo_interest_rate
			 * .addItem("Category: "+(obj_personal_person.lbl_category
			 * .getText().
			 * length()>0?obj_personal_person.lbl_category.getText():"Others"
			 * )+"  "+array_double_deposit_interest[1]);
			 * combo_interest_rate.addItem
			 * ("SubCategory: "+(obj_personal_person.lbl_sub_category
			 * .getText().length() >
			 * 0?obj_personal_person.lbl_sub_category.getText
			 * ():"Others")+"  "+array_double_deposit_interest[2]);
			 * combo_interest_rate
			 * .addItem("BOTH  "+String.valueOf(array_double_deposit_interest
			 * [1]+array_double_deposit_interest[2]));
			 */
			// combo_interest_rate.addItemListener(this);

			/*
			 * if(appl.getAc_type().equalsIgnoreCase("1004000")) {
			 * //lbl_maturity_amount
			 * .setText(numberFormat.format(Validations.rdInterestCalculation
			 * (Double
			 * .parseDouble(lbl_deposit_amount.getText()),Integer.parseInt
			 * (txt_td_period
			 * .getText()),Double.parseDouble(lbl_interest_rate.getText
			 * ()),lbl_deposit_date.getText(),txt_maturity_date.getText())));
			 * DepositIntRepObject
			 * deporepobj[]=tddelegate.interestCalculation(1,
			 * appl.getAc_type(),appl.getUtml(),appl.getUid(),appl.getUdate());
			 * }
			 * 
			 * if(appl.getAc_type().equalsIgnoreCase("1003000"))
			 * dep_open_obj.setMaturityAmt(appl.()); else
			 * if(appl.getAc_type().equalsIgnoreCase("1005000")) {
			 * DepositIntRepObject
			 * deporepobj2[]=tddelegate.interestCalculation(2,
			 * appl.getAc_type(),appl.getUtml(),appl.getUid(),appl.getUdate());
			 * } else {
			 * 
			 * 
			 * //txt_td_period.setText(""); //txt_td_period.requestFocus();
			 * //JOptionPane.showMessageDialog(null,
			 * "Interest rate not defined for this Account Type"); }
			 */

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return true;
	}

	private void setPayableAmount(TDDelegate deligate, ApplnDE appl,
			HttpServletRequest req, DepositMasterObject depomast) {
		try {
			System.out
					.println("EllowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwW"
							+ depomast.getInterestFrq());
			System.out.println("InSide setPayableAmount method AcNO----->  "
					+ depomast.getAccNo());
			System.out.println("InSide setPayableAmount method Ac_type----->"
					+ depomast.getAccType());
			System.out.println("DepDate()-----> " + depomast.getDepDate());
			System.out.println("MaturityDate-----> "
					+ depomast.getMaturityDate());
			DepositMasterObject dep_ammendment = new DepositMasterObject();
			if (depomast.getDepDate() == null
					&& depomast.getMaturityDate() == null) {
				if (appl.getDep_date().length() > 0
						&& appl.getMat_date().length() > 0) {

					if (appl.getAc_type().equalsIgnoreCase("1003001")) {
						double days = 0;
						double total_days = 0.0;
						String[] intfreq = deligate.getInt_freq();
						System.out.println("selectde index of combo_interest"
								+ intfreq + "-----" + deligate.getInt_freq());
						System.out.println("THE VALUE OF INT_RATEeeee ----> "
								+ appl.getInt_rate());
						System.out
								.println("THE DEPOSIT AMO00000000nt ACTION CLASS=======> "
										+ appl.getHidval());

						// appl.getDep_amt() is changed to appl.getHidval() as
						// it was returning "0"
						if (appl.getHidval() != null) {
							if(appl.getMat_date()!=null)
							total_days = deligate.setPayableAmt(appl
									.getInterest_freq(), appl.getDep_date(),
									appl.getMat_date(), Double.valueOf(appl
											.getHidval()), appl.getInt_rate());
						} else {
							if(appl.getMat_date()!=null)
							total_days = deligate.setPayableAmt(appl
									.getInterest_freq(), appl.getDep_date(),
									appl.getMat_date(), appl.getDep_amttwo(),
									appl.getInt_rate());
						}

						System.out.println("total number of days()()()(()()() "
								+ total_days);
						days = total_days;
						if (appl.getInterest_freq().equalsIgnoreCase("Monthly")) {
							// String to_date =
							// commonRemote.getFutureMonthDate(lbl_deposit_date.getText(),1);
							// days =
							// commonRemote.getDaysFromTwoDate(lbl_deposit_date.getText(),to_date);

							System.out.println("-------INSIDE MONTHLY 1------ "
									+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 1);
							System.out.println("&&&&&&&&&to_date&&&&&&&&&& "
									+ to_date);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, Double
												.valueOf(appl.getHidval()),
										appl.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}

							System.out.println("$$$$$$$$$$DAYS$$$$$$$$$$$ "
									+ days);
						}

						else if (appl.getInterest_freq().equalsIgnoreCase(
								"Quarterly")
								|| appl.getInterest_freq().startsWith("Q")) {
							System.out
									.println("-------INSIDE Quarterly 2------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 3);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, Double
												.valueOf(appl.getHidval()),
										appl.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						} else if (appl.getInterest_freq().equalsIgnoreCase(
								"Halfyearly")
								|| appl.getInterest_freq().startsWith("H")) {
							System.out
									.println("-------INSIDE Halfyearly 3------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 6);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, Double
												.valueOf(appl.getHidval()),
										appl.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						} else if (appl.getInterest_freq().equalsIgnoreCase(
								"Yearly")
								|| appl.getInterest_freq().startsWith("Y")) {
							System.out.println("-------INSIDE Yearly 4------ "
									+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 12);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, Double
												.valueOf(appl.getHidval()),
										appl.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						}
						/*
						 * else
						 * if(appl.getInterest_freq().equalsIgnoreCase("Onmaturity"
						 * ) || appl.getInterest_freq().startsWith("O")) {
						 * System
						 * .out.println("-------INSIDE Yearly 4-- sumanth---- "
						 * +appl.getHidval()); String to_date =
						 * deligate.getFutureMonthDate
						 * (appl.getDep_date(),Integer
						 * .parseInt(appl.getMat_date()));
						 * if(appl.getHidval()!=null){ days =
						 * deligate.setPayableAmt(appl.getInterest_freq(),
						 * appl.getDep_date
						 * (),to_date,Double.valueOf(appl.getHidval
						 * ()),appl.getInt_rate()); }else{ days =
						 * deligate.setPayableAmt(appl.getInterest_freq(),
						 * appl.getDep_date
						 * (),to_date,appl.getDep_amttwo(),appl.getInt_rate());
						 * } }
						 */
						System.out.println("no of days " + days
								+ " total days " + total_days);
						System.out.println("INTEREST RATE^^^^^^^^^^^^^^^ "
								+ appl.getInt_rate());
						if (days <= total_days) {
							double rate = 0.0;
							// txt_payable.setText(numberFormat.format(new
							// Double(Math.round((Double.parseDouble(lbl_deposit_amount.getText())*Double.parseDouble(lbl_interest_rate.getText())*days)/(36500)))));

							// double
							// rate=(Math.round(Double.valueOf((appl.getHidval()))*(appl.getInt_rate())*days)/36500);
							if (appl.getHidval() != null) {
								rate = Math.round(((Double.valueOf((appl
										.getHidval()))
										* appl.getInt_rate() * days) / 36500));
							} else {
								rate = Math.round(((appl.getDep_amttwo()
										* appl.getInt_rate() * days) / 36500));
							}
							System.out
									.println("THE VALUE OF INTRATE IN ACTION IS-*-*-*-*-* "
											+ rate);
							//

							req.setAttribute("intereste", rate);
						} else {
							appl.getInterest_freq().equalsIgnoreCase("Select");

						}
					}

					// this is for RD and RI payable int amount

					if (appl.getAc_type().equalsIgnoreCase("1004001")
							|| appl.getAc_type().startsWith("100500")) {

						System.out
								.println("The interest Frequesncy is------ RD RI=====> "
										+ appl.getIntFREQ());
						double days = 0;
						double total_days = 0.0;
						String[] intfreq = deligate.getInt_freq();
						System.out.println("selectde index of combo_interest"
								+ intfreq + "-----" + deligate.getInt_freq());
						System.out.println("THE VALUE OF INT_RATEeeee ----> "
								+ appl.getInt_rate());
						System.out
								.println("THE DEPOSIT AMO00000000nt ACTION CLASS=======> "
										+ appl.getHidval());

						// appl.getDep_amt() is changed to appl.getHidval() as
						// it was returning "0"
						if (appl.getHidval() != null) {
							if(appl.getMat_date()!=null)
							total_days = deligate.setPayableAmt(String
									.valueOf(appl.getIntFREQ()), appl
									.getDep_date(), appl.getMat_date(), Double
									.valueOf(appl.getHidval()), appl
									.getInt_rate());
						} else {
							if(appl.getMat_date()!=null)
								
							total_days = deligate.setPayableAmt(appl
									.getInterest_freq(), appl.getDep_date(),
									appl.getMat_date(), appl.getDep_amttwo(),
									appl.getInt_rate());
						}

						System.out.println("total number of days()()()(()()() "
								+ total_days);
						days = total_days;
						if (String.valueOf(appl.getIntFREQ()).equalsIgnoreCase(
								"Monthly")
								|| appl.getInterest_freq().startsWith("M")) {
							// String to_date =
							// commonRemote.getFutureMonthDate(lbl_deposit_date.getText(),1);
							// days =
							// commonRemote.getDaysFromTwoDate(lbl_deposit_date.getText(),to_date);

							System.out.println("-------INSIDE MONTHLY 1------ "
									+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 1);
							System.out.println("&&&&&&&&&to_date&&&&&&&&&& "
									+ to_date);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(String
										.valueOf(appl.getIntFREQ()), appl
										.getDep_date(), to_date, Double
										.valueOf(appl.getHidval()), appl
										.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
							System.out.println("$$$$$$$$$$DAYS$$$$$$$$$$$ "
									+ days);
						}

						else if (String.valueOf(appl.getIntFREQ())
								.equalsIgnoreCase("Quarterly")
								|| appl.getInterest_freq().startsWith("Q")) {
							System.out
									.println("-------INSIDE Quarterly 2------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 3);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(String
										.valueOf(appl.getIntFREQ()), appl
										.getDep_date(), to_date, Double
										.valueOf(appl.getHidval()), appl
										.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						} else if (String.valueOf(appl.getIntFREQ())
								.equalsIgnoreCase("Halfyearly")
								|| appl.getInterest_freq().startsWith("H")) {
							System.out
									.println("-------INSIDE Halfyearly 3------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 6);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(String
										.valueOf(appl.getIntFREQ()), appl
										.getDep_date(), to_date, Double
										.valueOf(appl.getHidval()), appl
										.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						} else if (String.valueOf(appl.getIntFREQ())
								.equalsIgnoreCase("Yearly")
								|| appl.getInterest_freq().startsWith("Y")) {
							System.out
									.println("-------INSIDE Yearly 4 SUMANTH------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(appl
									.getDep_date(), 12);
							if (appl.getHidval() != null) {
								days = deligate.setPayableAmt(String
										.valueOf(appl.getIntFREQ()), appl
										.getDep_date(), to_date, Double
										.valueOf(appl.getHidval()), appl
										.getInt_rate());
							} else {
								days = deligate.setPayableAmt(appl
										.getInterest_freq(),
										appl.getDep_date(), to_date, appl
												.getDep_amttwo(), appl
												.getInt_rate());
							}
						}
						System.out.println("no of days " + days
								+ " total days " + total_days);
						System.out.println("INTEREST RATE^^^^^^^^^^^^^^^ "
								+ appl.getInt_rate());
						if (days <= total_days) {
							double rate = 0.0;
							// txt_payable.setText(numberFormat.format(new
							// Double(Math.round((Double.parseDouble(lbl_deposit_amount.getText())*Double.parseDouble(lbl_interest_rate.getText())*days)/(36500)))));

							// double
							// rate=(Math.round(Double.valueOf((appl.getHidval()))*(appl.getInt_rate())*days)/36500);
							if (appl.getHidval() != null) {
								rate = Math.round(((Double.valueOf((appl
										.getHidval()))
										* appl.getInt_rate() * days) / 36500));
							} else {
								rate = Math.round(((appl.getDep_amttwo()
										* appl.getInt_rate() * days) / 36500));
							}
							System.out
									.println("THE VALUE OF INTRATE IN ACTION IS-*-*-*-*-* "
											+ rate);
							// appl.setInt_payable(String.valueOf(rate));

							req.setAttribute("intereste", rate);
						} else {
							appl.getInterest_freq().equalsIgnoreCase("Select");

						}

					}

					// end of rd and ri intrate payable

				}
			} else {
				System.out.println("HEY HEY I am in DEP not equal to null");

				System.out.println("DepDate.Length----> "
						+ depomast.getDepDate().length());
				System.out.println("MaturityDate.Length----> "
						+ depomast.getMaturityDate().length());
				if (depomast.getDepDate().length() > 0
						&& depomast.getMaturityDate().length() > 0) {
					System.out.println("hoooooooooooooooooooooo");
					if (depomast.getAccType().equalsIgnoreCase("1003001")) {
						double days = 0;
						String[] intfreq = deligate.getInt_freq();
						System.out.println("selectde index of combo_interest"
								+ intfreq + "-----" + deligate.getInt_freq());
						System.out.println("THE VALUE OF INT_RATEeeee ----> "
								+ depomast.getInterestRate());
						System.out
								.println("THE DEPOSIT AMO00000000nt ACTION CLASS=======> "
										+ depomast.getDepositAmt());
						System.out.println("DepositDate====> "
								+ depomast.getDepDate());
						System.out.println("Maturity date---> "
								+ depomast.getMaturityDate());
						System.out.println("Deposit Amount===> "
								+ depomast.getDepositAmt());
						System.out.println("In terest Rate=====> "
								+ depomast.getInterestRate());
						// appl.getDep_amt() is changed to appl.getHidval() as
						// it was returning "0"
						System.out.println("The interest Freqence====> "
								+ depomast.getInterestFrq());
						
						double total_days = deligate.setPayableAmt(depomast
								.getInterestFrq(), depomast.getDepDate(),
								depomast.getMaturityDate(), depomast
										.getDepositAmt(), depomast
										.getInterestRate());
						System.out.println("total number of days()()()(()()() "
								+ total_days);
						days = total_days;
						if (depomast.getInterestFrq().startsWith("M")) {
							// String to_date =
							// commonRemote.getFutureMonthDate(lbl_deposit_date.getText(),1);
							// days =
							// commonRemote.getDaysFromTwoDate(lbl_deposit_date.getText(),to_date);

							System.out.println("-------INSIDE MONTHLY 1------ "
									+ depomast.getDepositAmt());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 1);
							System.out.println("&&&&&&&&&to_date&&&&&&&&&& "
									+ to_date);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
							System.out.println("$$$$$$$$$$DAYS$$$$$$$$$$$ "
									+ days);
						}

						else if (depomast.getInterestFrq().startsWith("Q")) {
							System.out
									.println("-------INSIDE Quarterly 2------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 3);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						} else if (depomast.getInterestFrq().startsWith("H")) {
							System.out
									.println("-------INSIDE Halfyearly 3------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 6);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						} else if (depomast.getInterestFrq().startsWith("Y")) {
							System.out.println("-------INSIDE Yearly 4------ "
									+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 12);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						}

						System.out.println("no of days " + days
								+ " total days " + total_days);
						System.out.println("INTEREST RATE^^^^^^^^^^^^^^^ "
								+ depomast.getInterestRate());
						if (days <= total_days) {

							// txt_payable.setText(numberFormat.format(new
							// Double(Math.round((Double.parseDouble(lbl_deposit_amount.getText())*Double.parseDouble(lbl_interest_rate.getText())*days)/(36500)))));

							// double
							// rate=(Math.round(Double.valueOf((appl.getHidval()))*(appl.getInt_rate())*days)/36500);
							double rate = Math
									.round(((depomast.getDepositAmt())
											* depomast.getInterestRate() * days) / 36500);
							System.out
									.println("THE VALUE OF INTRATE IN ACTION IS-*-*-*-*-* "
											+ rate);
							appl.setInt_payable(String.valueOf(rate));

							// req.setAttribute("intereste",rate);
						} else {
							System.out
									.println("I am in SET payable ELSE block");
							appl.getInterest_freq().equalsIgnoreCase("Select");

						}

						appl.setInt_payable(String.valueOf(total_days));
					}

					else if (depomast.getAccType().equalsIgnoreCase("1004001")
							|| depomast.getAccType().startsWith("100500")) {

						System.out
								.println("hoooooooooooooooooooooo interest freq----> "
										+ depomast.getInterestFrq());
						double days = 0;
						String[] intfreq = deligate.getInt_freq();
						System.out.println("selectde index of combo_interest"
								+ intfreq + "-----" + deligate.getInt_freq());
						System.out.println("THE VALUE OF INT_RATEeeee ----> "
								+ depomast.getInterestRate());
						System.out
								.println("THE DEPOSIT AMO00000000nt ACTION CLASS=======> "
										+ depomast.getDepositAmt());

						System.out.println("DepositDate====> "
								+ depomast.getDepDate());
						System.out.println("Maturity date---> "
								+ depomast.getMaturityDate());
						System.out.println("Deposit Amount===> "
								+ depomast.getDepositAmt());
						System.out.println("In terest Rate=====> "
								+ depomast.getInterestRate());
						// appl.getDep_amt() is changed to appl.getHidval() as
						// it was returning "0"
						System.out.println("The interest Freqence====> "
								+ depomast.getInterestFrq());
						double total_days = deligate.setPayableAmt(depomast
								.getInterestFrq(), depomast.getDepDate(),
								depomast.getMaturityDate(), depomast
										.getDepositAmt(), depomast
										.getInterestRate());
						System.out.println("total number of days()()()(()()() "
								+ total_days);
						days = total_days;
						if (depomast.getInterestFrq().startsWith("M")) {
							// String to_date =
							// commonRemote.getFutureMonthDate(lbl_deposit_date.getText(),1);
							// days =
							// commonRemote.getDaysFromTwoDate(lbl_deposit_date.getText(),to_date);

							System.out.println("-------INSIDE MONTHLY 1------ "
									+ depomast.getDepositAmt());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 1);
							System.out.println("&&&&&&&&&to_date&&&&&&&&&& "
									+ to_date);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
							System.out.println("$$$$$$$$$$DAYS$$$$$$$$$$$ "
									+ days);
						}

						else if (depomast.getInterestFrq().startsWith("Q")) {
							System.out
									.println("-------INSIDE Quarterly 2------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 3);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						} else if (depomast.getInterestFrq().startsWith("H")) {
							System.out
									.println("-------INSIDE Halfyearly 3------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 6);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						} else if (depomast.getInterestFrq().startsWith("Y")) {
							System.out.println("-------INSIDE Yearly 4------ "
									+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), 12);
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());
						} else if (depomast.getInterestFrq().startsWith("O")) {

							System.out
									.println("-------INSIDE Onmaturity 5------ "
											+ appl.getHidval());
							String to_date = deligate.getFutureMonthDate(
									depomast.getDepDate(), Integer
											.parseInt(depomast
													.getMaturityDate()));
							days = deligate.setPayableAmt(depomast
									.getInterestFrq(), depomast.getDepDate(),
									depomast.getMaturityDate(), depomast
											.getDepositAmt(), depomast
											.getInterestRate());

						}

						System.out.println("no of days " + days
								+ " total days " + total_days);
						System.out.println("INTEREST RATE^^^^^^^^^^^^^^^ "
								+ depomast.getInterestRate());
						if (days <= total_days) {

							// txt_payable.setText(numberFormat.format(new
							// Double(Math.round((Double.parseDouble(lbl_deposit_amount.getText())*Double.parseDouble(lbl_interest_rate.getText())*days)/(36500)))));

							// double
							// rate=(Math.round(Double.valueOf((appl.getHidval()))*(appl.getInt_rate())*days)/36500);
							double rate = Math
									.round(((depomast.getDepositAmt())
											* depomast.getInterestRate() * days) / 36500);
							System.out
									.println("THE VALUE OF INTRATE IN ACTION IS-*-*-*-*-* "
											+ rate);
							appl.setInt_payable(String.valueOf(rate));

							// req.setAttribute("intereste",rate);
						} else {
							System.out
									.println("I am in SET payable ELSE block");
							appl.getInterest_freq().equalsIgnoreCase("Select");

						}

						appl.setInt_payable(String.valueOf(total_days));
					}
				}

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}
