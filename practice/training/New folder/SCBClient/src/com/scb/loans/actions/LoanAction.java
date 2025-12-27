package com.scb.loans.actions;

import general.Validations;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
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
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.DoubleFormat;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.ModuleObject;
import masterObject.general.PropertyObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.VehicleObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.SurityCoBorrowerObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.pygmyDeposit.SimpleMasterObject;
import masterObject.share.ShareCategoryObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.forms.PageIdForm;
import com.scb.common.forms.SignatureInstructionForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.CommonDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.LoansDelegate;
import com.scb.designPatterns.ShareDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.loans.forms.ActionDueReport;
import com.scb.loans.forms.InterestAccuredReportForm;
import com.scb.loans.forms.LastLoanTranForm;
import com.scb.loans.forms.LoanAdminForm;
import com.scb.loans.forms.LoanApplicationForm;
import com.scb.loans.forms.LoanDailyPostingForm;
import com.scb.loans.forms.LoanDefaulterForm;
import com.scb.loans.forms.LoanHistForm;
import com.scb.loans.forms.LoanStatusForm;
import com.scb.loans.forms.LoansAmmendmentsForm;
import com.scb.loans.forms.NPASummaryForm;
import com.scb.loans.forms.ODReport;
import com.scb.loans.forms.OpenClosedStat;
import com.scb.loans.forms.OtherChargesDEForm;
import com.scb.loans.forms.OtherChargesReport;
import com.scb.loans.forms.PenalActionForm;
import com.scb.loans.forms.RecoveryForm;
import com.scb.loans.forms.Reschedulingform;
import com.scb.loans.forms.ReverseRecoveryForm;
import com.scb.loans.forms.SanctionForm;
import com.scb.loans.forms.SurityCoBorrower;
import com.scb.props.MenuNameReader;

public class LoanAction extends Action {
	final Logger logger = LogDetails.getInstance().getLoggerObject("LoansDelegate");
	LoansDelegate delegate = null;
	String path = null;
	Object data[][];
	int count_disburse=0;
	LoanMasterObject lmobj = null;
	LoanTransactionObject lntrnobj = null, loantran[] = null, lntrn = null,loan_trn[][] = null;
	LoanReportObject loanreportobj = null;
	LoanPurposeObject purpose[] = null;
	ModuleObject object[] = null, obj[] = null, moduleobject_transfer[] = null,array_moduleobject[] = null;;
	SignatureInstructionObject[] signObject, siob1;
	ArrayList arraylist = null;
	boolean[] submitteddetails;
	AccountObject am = null;
	String items_relavent[] = null;
	boolean submited_details[] = null;
	Hashtable hash_obj = new Hashtable();
	boolean flag = false;
	HttpSession session,sessions;
	CommonDelegate comdelegate=null;
	private VehicleObject vech = null;
	ShareCategoryObject sharecat = null;
	ShareDelegate sharedelegate = null;
	double debit=0,credit=0,balance=0;
	private List obj_list=null;
	String button_value;
	 String lnuser,lntml;
	  AdministratorDelegate admDelegate;
	  Map user_role;
	
	public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res) {
		String[] str = null, str1 = null;
		Vector vec_obj = null;
		String loan = null;
		logger.info("**************IN THE ACTION CLASS on 10/11**************");
		logger.info("PATH IS ===============>" + map.getPath());
		  sessions=req.getSession();
		  lnuser=(String)sessions.getAttribute("UserName");
	    	lntml=(String)sessions.getAttribute("UserTml") ;
	    	try{
	    		synchronized(req) {
					if(lnuser!=null){
						admDelegate=new AdministratorDelegate();
						user_role=admDelegate.getUserLoginAccessRights(lnuser,"LN");
						System.out.println("userRole"+user_role);
						System.out.println("PageID"+req.getParameter("pageidentity.pageId"));
						if(user_role!=null){
							req.setAttribute("user_role",user_role);
							if(req.getParameter("pageidentity.pageId")!=null){
								System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageidentity.pageId"));
								req.setAttribute("accesspageId",req.getParameter("pageidentity.pageId").trim());
							}
								System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageidentity.pageId"));
								if("method=SaveSurityDet"!=null)
								req.setAttribute("accesspageId","5001");
								if("method=SaveCoborrowerDet"!=null)
									req.setAttribute("accesspageId","5001");
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
		if (map.getPath().equalsIgnoreCase("/Loans/LoanApplicationMenu")){
			try {
				LoanApplicationForm appform = (LoanApplicationForm) form;
				LoansDelegate delegate = new LoansDelegate();
				appform.setSysdate(LoansDelegate.getSysDate());
				logger.info("File--->" + appform.getPageidentity().getPageId());
				PageIdForm pg = appform.getPageidentity();
				path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
				setTabbedpaneattributes(req,path,delegate,appform);
				appform.setShno(0);
				appform.setShtype("Select");
				appform.setLoantype("Select");
				req.setAttribute("ApplicationDate", LoansDelegate.getSysDate());
				appform.setLoan_acc_no(0);
				session=req.getSession();
				session.setAttribute("LnMasterObject", null);
			    req.getSession().setAttribute("RelationDetMap", null);
				req.getSession().setAttribute("Pension", null);
				req.getSession().setAttribute("Service", null);
				req.getSession().setAttribute("Business", null);
				req.getSession().setAttribute("Rent", null);
				req.getSession().setAttribute("Self", null);
				req.getSession().setAttribute("EmploymentDetMap", null);
				req.getSession().setAttribute("ApplicationDetMap", null);
				req.getSession().setAttribute("SignDetMap", null);
				req.getSession().setAttribute("PropertyDetMap", null);
				req.getSession().setAttribute("CoborrowerDetMap", null);
				req.getSession().setAttribute("SuritiesDetMap", null);
				req.getSession().setAttribute("VehicleDetMap", null);
				req.getSession().setAttribute("GoldDetMap", null);
				req.getSession().setAttribute("count", null);
				appform.setButton_value(null);
				appform.setMain_submit(null);
				if(MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())){
					try {
						path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
						IntialParameters(req, delegate);
						req.setAttribute("pageId", path);
						return map.findForward(ResultHelp.getSuccess());
					}catch (Exception e){
						setErrorPageElements("" + e, req, path);
						return map.findForward(ResultHelp.getError());
					}
				}else{
					return map.findForward(ResultHelp.getError());
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}else if(map.getPath().trim().equalsIgnoreCase("/Loans/SignatureInst")){//28.09.2011
			SignatureInstructionForm siForm=(SignatureInstructionForm)form;
			LoanApplicationForm sbForm=(LoanApplicationForm)form;
        	String path;
            try{
                req.setAttribute("pageNum",sbForm.getPageid().trim());
                if(MenuNameReader.containsKeyScreen(sbForm.getPageid())){
                    path=MenuNameReader.getScreenProperties(sbForm.getPageid());
                    LoansDelegate delegate = new LoansDelegate();
                    if(!sbForm.getAcType().trim().equals("SELECT")){
                    	SimpleMasterObject amObj=delegate.getAccountDetails(String.valueOf(sbForm.getAccno()));
                        req.setAttribute("AccountDetails",amObj);
                        if(!siForm.getSignCombo().equalsIgnoreCase("SELECT"))
                        	if(!sbForm.getDetails().trim().equalsIgnoreCase("SELECT")){
                        		if(sbForm.getDetails().trim().equalsIgnoreCase("Nominee")){
                        	           String perPath=MenuNameReader.getScreenProperties("0002");
                        	           req.setAttribute("pageNum",sbForm.getPageid());
                        	           req.setAttribute("TabNum","3");
                        	           req.setAttribute("flag",perPath);
                        	       }
                        	}
                        setLoanInitParams(req,path,delegate);
                        return map.findForward(ResultHelp.getSuccess());
                    }else{
                    	setLoanInitParams(req,path,delegate);
                    	return map.findForward(ResultHelp.getSuccess());
                    }
                }else{
                	path=MenuNameReader.getScreenProperties("0000");
                	setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                	return map.findForward(ResultHelp.getError());
                }
            }catch(Exception e){
            	 path=MenuNameReader.getScreenProperties("0000");
                 setErrorPageElements(""+e.toString(),req,path);
                 logger.error(e.getMessage());
                 return map.findForward(ResultHelp.getError());
            }
        }else if(map.getPath().trim().equalsIgnoreCase("/Loans/LoanApplicationDE")){
        	try{
				LoanMasterObject lnmodobj = null;
				AccountObject array_accountObject[] = null;
				ModuleObject[] modobj=null;
				IncomeObject[] array_incomeobject=null;
				Vector coborrowers=null;
				Vector surities=null;
				SignatureInstructionObject[] signObject1=null;
				SignatureInstructionObject[] signObject=null;
				LoansDelegate delegate = new LoansDelegate();
				modobj=delegate.getSharemodulecode(2);
				LoanApplicationForm appform = (LoanApplicationForm) form;
				appform.setSysdate(LoansDelegate.getSysDate());
				if(appform.getLoantype()!= null){
					try{
						lnmodobj = delegate.getModName(appform.getLoantype());
						//req.setAttribute("disable1", "disable");
					}catch (Exception e){
						e.printStackTrace();
					}
					req.setAttribute("ModName", lnmodobj);
				}else {
					req.setAttribute("msg", "Select the Loan Type!");
				}
				if(appform.getLnaccno()!= 0){
					lnmodobj = delegate.getLoanNoDetails(appform.getLnaccno());
					appform.setShtype(lnmodobj.getShareAccType());
					appform.setPurpose(lnmodobj.getPurposeCode());
					appform.setLoan_acc_no(0);
					appform.setShno(0);
				}
			
				String button = appform.getForward();
				LoansDelegate loandelegate = new LoansDelegate();
				req.setAttribute("ApplicationDate", LoansDelegate.getSysDate());
				String str12[] = loandelegate.getDetails();
				appform.setLoan_acc_no(0);
				if(appform.getShno() != 0) {
					if(!appform.getLoantype().equalsIgnoreCase("SELECT")){
						req.setAttribute("disable1", "disable");
						req.setAttribute("lntype", appform.getLoantype());
					}
					req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
					String sh_type = "1001001";
					array_accountObject = new AccountObject[1];
					if(appform.getShno() != 0 && sh_type != null) {
						array_accountObject[0] = loandelegate.getAccount(null,sh_type, appform.getShno());
						if(array_accountObject[0] == null){
							req.setAttribute("msg", "Sorry!!Share Number not found");
						}else if (array_accountObject[0] != null){
							if (array_accountObject[0].getVerified() == null){
								req.setAttribute("msg", "Share Account number is not verified");
							}
							if(array_accountObject[0] != null && !array_accountObject[0].isOtherBranch()&& !array_accountObject[0].isDirector()){
								req.setAttribute("personalInfo", loandelegate.getCustomer(array_accountObject[0].getCid()));
								appform.setCid(array_accountObject[0].getCid());
								req.setAttribute("ShareAccountObject",array_accountObject);
							}
							if(array_accountObject[0].isOtherBranch()){
								req.setAttribute("msg", "Branch Membership cannot be considered");
							}
							if(array_accountObject[0].isDirector()){
								req.setAttribute("msg", "Director cannot be a Loanee");
							}
						}
					}
				}
				if(appform.getDetails()!=null){
				if((appform.getDetails().equalsIgnoreCase("5002"))){
					FrontCounterDelegate counterDelegate=null;
					try{
					counterDelegate=new FrontCounterDelegate();
					}catch(Exception e){
						e.printStackTrace();
					}
					req.setAttribute("Application", MenuNameReader.getScreenProperties("5002"));
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					String dirdetails[][]=delegate.getDirectorDetails(LoansDelegate.getSysDate());
					String[] dirrelations=delegate.getDirectorRelations();
					if(appform.getPaymtmode()!=null){
						if(appform.getPaymtmode().equalsIgnoreCase("Transfer")){
							if(appform.getPayaccno()!=0 && appform.getPayactype()!=null){
								AccountMasterObject masterObject=counterDelegate.getAccountMaster(appform.getPayaccno(), appform.getPayactype());
								if(masterObject==null){
									req.setAttribute("msg","Account Number Not Found!");
								}
							}
						}
					}
					req.setAttribute("Dirrelations",dirrelations);
					req.setAttribute("DirDetails",dirdetails);
					req.setAttribute("flag", null);
				}else if(appform.getDetails().equalsIgnoreCase("0001")&&appform.getShno()!=0){
					if(String.valueOf(appform.getShno()).trim().length()!=0 && array_accountObject[0] != null){
						signObject=loandelegate.getSignatureDetails(appform.getShno(), appform.getShtype());
						req.setAttribute("panelName", "Personal Details");
						if(signObject!=null){
							req.setAttribute("personalInfo", loandelegate.getCustomer(signObject[0].getCid()));
						}
						req.setAttribute("TabNum", "0");
						req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
					}
				}else if((appform.getDetails().equalsIgnoreCase("5022"))){
					req.setAttribute("flag", null);
					req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5003"));
				}else if((appform.getDetails().equalsIgnoreCase("5004"))){
					req.setAttribute("flag", null);
					req.setAttribute("Property", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
				}else if((appform.getDetails().equalsIgnoreCase("5005"))){
					req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
					req.setAttribute("TabNum", "0");
					tabpane_employment(req, appform);
					if (appform.getTabPaneHeading() != null) {
						if (appform.getTabPaneHeading().equalsIgnoreCase("Service")) {
							req.setAttribute("TabNum", "1");
						} else if (appform.getTabPaneHeading().equalsIgnoreCase("Business")) {
							req.setAttribute("TabNum", "2");
						} else if (appform.getTabPaneHeading().equalsIgnoreCase("Pension")) {
							req.setAttribute("TabNum", "3");
						} else if (appform.getTabPaneHeading().equalsIgnoreCase("Rent")) {
							req.setAttribute("TabNum", "4");
						}
					}
				}else if(appform.getDetails().trim().equalsIgnoreCase("5093")) {
					req.setAttribute("flag", null);
					req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
				}else if(appform.getDetails().trim().equalsIgnoreCase("Surities")){
					req.setAttribute("flag", null);
					req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
					if(lnmodobj!=null)
						req.setAttribute("GoldDet", lnmodobj.getGoldDet());
				}else if((appform.getDetails().trim().equalsIgnoreCase("5032"))){
					req.setAttribute("flag", null);
					req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5100"));
					req.setAttribute("CoButton", "CoBorrower");
					array_accountObject = new AccountObject[1];
					if(appform.getCoshareno()!=null && appform.getCosharetype() != null&& appform.getCoshareno().trim().length()!=0){
						if(Integer.parseInt(appform.getCoshareno())==appform.getShno()){
							req.setAttribute("msg", "Loanee Can't be CoBorrower or Surity");
						}else{
							array_accountObject[0] = loandelegate.getAccount(null,modobj[0].getModuleCode(), Integer.parseInt(appform.getCoshareno()));
						if(array_accountObject[0]==null){
							req.setAttribute("msg", "Sorry!!!!!!!!Share Number not found");
						}else if (array_accountObject[0]!=null){
							if (array_accountObject[0].getVerified() == null){
								req.setAttribute("msg", "Share Account number is not verified");
							}
						}
						if(appform.getCoshareno().trim().length()!=0 && array_accountObject[0]!=null){
							signObject1=loandelegate.getSignatureDetails(Integer.parseInt(appform.getCoshareno()),modobj[0].getModuleCode());
							req.setAttribute("panelName", "Personal Details");
							req.setAttribute("personalInfo", loandelegate.getCustomer(signObject1[0].getCid()));
							req.setAttribute("TabNum", "0");
							req.setAttribute("flag",null);
						}
						req.setAttribute("ShareAccountObject",array_accountObject);
						}
					}
				 }else if((appform.getDetails().trim().equalsIgnoreCase("5027"))){
					req.setAttribute("flag", null);
					req.setAttribute("Surities",MenuNameReader.getScreenProperties("5027"));
					req.setAttribute("CoButton", "Surity");
					appform.setSurityname("");
					appform.setBrname("");
					appform.setBrcode(0);
					appform.setCovalue(0);
					array_accountObject = new AccountObject[1];
					if(appform.getCoshareno()!=null && appform.getCosharetype() != null && appform.getCoshareno().trim().length()!=0) {
						if(Integer.parseInt(appform.getCoshareno())==appform.getShno()){
							req.setAttribute("msg", "Loanee Can't be CoBorrower or Surity");
						}else{
							array_accountObject[0] = loandelegate.getAccount(null,modobj[0].getModuleCode(), Integer.parseInt(appform.getCoshareno()));
							if(array_accountObject[0]==null){
								req.setAttribute("msg", "Sorry!!!!!!Share Number not found");appform.setSurityname("");
								appform.setBrname("");
								appform.setBrcode(0);
								appform.setCovalue(0);
							}else if (array_accountObject[0]!=null){
								if (array_accountObject[0].getVerified() == null){
									req.setAttribute("msg", "Share Account number is not verified");
								}
							}
							if(appform.getCoshareno().trim().length()!=0 && array_accountObject[0]!=null){
								signObject1=loandelegate.getSignatureDetails(Integer.parseInt(appform.getCoshareno()),modobj[0].getModuleCode());
								req.setAttribute("panelName", "Personal Details");
								if(signObject1[0]!=null)
									req.setAttribute("personalInfo", loandelegate.getCustomer(signObject1[0].getCid()));
								req.setAttribute("TabNum", "0");
								req.setAttribute("flag",null);
							}
							req.setAttribute("ShareAccountObject",array_accountObject);
						}
					}
				 }else if((appform.getDetails().trim().equalsIgnoreCase("Signature Ins"))){
					CustomerMasterObject cust=null;
					signObject=loandelegate.getSignatureDetails(appform.getShno(), appform.getShtype());
					if(signObject!=null)
						cust=loandelegate.getCustomer(signObject[0].getCid());
          			req.setAttribute("cust",cust);
          			req.setAttribute("sigObject",signObject);
          			path=MenuNameReader.getScreenProperties("5053");
          			req.setAttribute("flag","/SingnatureInst1.jsp"); 	
				}else if((appform.getDetails().equalsIgnoreCase("Loan and Share Details"))){
					req.setAttribute("flag", null);
					Object[][] sharedet=loandelegate.getLoanAndShareDetails(appform.getShtype(),appform.getShno());
					req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
					if(sharedet!=null){
						req.setAttribute("L&DDetails", loandelegate.getLoanAndShareDetails(appform.getShtype(),appform.getShno()));
						req.setAttribute("MaxLoanAmt", loandelegate.getModuleTableValue("ind_max_value"));
						req.setAttribute("MaxLoanIns", loandelegate.getModuleTableValue("ins_max_value"));
					}else{
						req.setAttribute("L&DDetails",null);
						req.setAttribute("MaxLoanAmt", 0.0);
						req.setAttribute("MaxLoanIns", 0.0);
						req.setAttribute("msg", "This Share Number has no Loan Details!");
					}
				}
        	}
				String method = req.getParameter("method");
				session=req.getSession(true);
				if(session.getAttribute("LnMasterObject")==null){
					lnmodobj=new LoanMasterObject();
				    session.setAttribute("LnMasterObject", lnmodobj);
				}
				if(method!= null){
					if(method.equalsIgnoreCase("SaveApplication")){	
						HashMap appMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						lnmodobj.setApplicationSrlNo(appform.getAppln_no());
						lnmodobj.setApplicationDate(appform.getAppndate());
						lnmodobj.setRequiredAmount(appform.getReqamount());
						if(appform.getRelativetodir()==null){
							lnmodobj.setRelative("null");
							lnmodobj.setDirectorCode(0);
						}else{	
							lnmodobj.setRelative(appform.getDirrelations());
							lnmodobj.setDirectorCode(Integer.parseInt(appform.getDirdetails()));
						}
						String paymode=appform.getPaymtmode();
						if(paymode.equalsIgnoreCase("Cash")){	
							lnmodobj.setPayMode(paymode);
						}else if(paymode.equalsIgnoreCase("Transfer")){
							lnmodobj.setPayMode(paymode);
							lnmodobj.setPaymentAcctype(appform.getPayactype());
							lnmodobj.setPaymentAccno(appform.getPayaccno());
						}else{	
							lnmodobj.setPayMode(paymode);
						}
						lnmodobj.setInterestType(appform.getInteresttype());
						lnmodobj.setInterestRateType(appform.getInterestcalctype());
						appMap.put("ApplicationDet",lnmodobj);
						session.setAttribute("ApplicationDetMap",appMap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
					}else if (method.equalsIgnoreCase("SaveVehicleDet")){
						HashMap vehMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						VehicleObject vech = new VehicleObject();
						vech.setVehicleType(appform.getVehicletype());
						vech.setVehicleMake(appform.getMaketype());
						vech.setVehicleCost((appform.getCost()));
						vech.setVehicleFor(appform.getVehiclefor());
						vech.setLicenceNo(appform.getLicenseno());
						vech.setLicenceValidity(appform.getValidity());
						vech.setPermitNo(appform.getPermitno());
						vech.setPermitValidity(appform.getPermitvalid());
						vech.setArea(appform.getVehiclearea());
						vech.setAddressParking(appform.getVehicleparked());
						vech.setAddressDealer(appform.getDealername());
						vech.setOtherDet(appform.getOthervehicle());
						lnmodobj.setVehicleDet(vech);
						vehMap.put("VehicleDet",lnmodobj);
						session.setAttribute("VehicleDetMap",vehMap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
					}else if (method.equalsIgnoreCase("SaveGoldDet")){
						Integer count=0;
						int result=0;
						if(session.equals(session)){
							if(req.getSession().getAttribute("count")!=null)
								count=(Integer)req.getSession().getAttribute("count");
							while(method.equalsIgnoreCase("SaveGoldDet")){
								result=count++;
								req.getSession().setAttribute("count", count);
								break;
							}
						}
						HashMap goldMap=new HashMap();
						Object array_obj_data[][]= null;
						GoldObject gold=null;
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
					 gold = new GoldObject();
						gold.setSlno(appform.getSlno());
						gold.setDesc(appform.getDesc());
						gold.setGrWeight(appform.getGrWeight());
						gold.setNetWeight(appform.getNetWeight());
						gold.setRate(appform.getRate());
						gold.setNetRate(appform.getNetRate());
						gold.setAppCode(appform.getAppCode());
						gold.setApprisalDate(LoansDelegate.getSysDate());
						gold.setApprisalTime(CommonDelegate.getSysTime());
						array_obj_data=new Object[result][10];
						for(int i=0;i<result;i++){
							for(int j=0;j<10;j++){
								array_obj_data[i][0]=gold.getSlno();
								array_obj_data[i][1]=gold.getDesc();
								array_obj_data[i][2]=gold.getGrWeight();
								array_obj_data[i][3]=gold.getNetWeight();
								array_obj_data[i][5]=gold.getRate();
							}
						}
						gold.setGoldDet(array_obj_data);
						lnmodobj.setGoldDet(gold);
						goldMap.put("GoldDet",lnmodobj);
						session.setAttribute("GoldDetMap",goldMap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
					}else if (method.equalsIgnoreCase("SaveCoborrowerDet")){
						HashMap coborrowerMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						SurityCoBorrowerObject coBorrowerObject=new SurityCoBorrowerObject();
						coBorrowerObject.setAc_no(appform.getCoshareno());
						coBorrowerObject.setAc_type(modobj[0].getModuleCode());
						coBorrowerObject.setCid(String.valueOf(appform.getCid()));
						coBorrowerObject.setModuleabbr(modobj[0].getModuleAbbrv());
						coBorrowerObject.setType("C");
						coborrowers=new Vector();
						coborrowers.add(coBorrowerObject);
						lnmodobj.setCoBorrowers(coborrowers);
						for(int i=0;i<=coborrowers.size();i++){
							coborrowerMap.put("CoborrowerDet",lnmodobj);
						}
						lnmodobj.setNoOfCoBorrowers(coborrowers.size());
						session.setAttribute("CoborrowerDetMap",coborrowerMap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
						
					}else if (method.equalsIgnoreCase("SaveSurityDet")){
						HashMap surityMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						SurityCoBorrowerObject surityObject=new SurityCoBorrowerObject();
						surityObject.setAc_no(appform.getCoshareno());
						surityObject.setAc_type(modobj[0].getModuleCode());
						surityObject.setCid(String.valueOf(appform.getCid()));
						surityObject.setModuleabbr(modobj[0].getModuleAbbrv());
						surityObject.setType("S");
						surities=new Vector();
						surities.add(surityObject);
						lnmodobj.setSurities(surities);
						for(int i=0;i<=surities.size();i++){
							surityMap.put("SuritiesDet",lnmodobj);
						}
						lnmodobj.setNoOfCoBorrowers(surities.size());
						session.setAttribute("SuritiesDetMap",surityMap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
						req.setAttribute("pageId",5027);
					}else if (method.equalsIgnoreCase("SaveSignature")){
						HashMap signmap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						SignatureInstructionObject[] array_sign_obj=null; 
						SignatureInstructionObject signObject2=new SignatureInstructionObject();
						array_sign_obj=delegate.getSignatureDetails(appform.getShno(), appform.getShtype());
						signObject2.setCid(appform.getCid());
						signObject2.setSAccNo(appform.getAcNum());
						signObject2.setSAccType(appform.getAcType());
						signObject2.setName(appform.getName());
						signObject2.setTypeOfOperation(appform.getTyop());
						array_sign_obj[0]=signObject2;
						lnmodobj.setSignatureDet(array_sign_obj);
						signmap.put("SignDet",lnmodobj);
						session.setAttribute("SignDetMap",signmap);
						req.getSession().setAttribute("LnMasterObject",lnmodobj);
					}else if(method.equalsIgnoreCase("SaveRelative")){
						HashMap relMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						Object[][] relation=new Object[1][6];
						relation[0][0]=appform.getRelName();
						relation[0][1]=appform.getRelDob();
						relation[0][2]=appform.getRelTor();
						relation[0][3]=appform.getRelTos();
						relation[0][4]=appform.getRelTom();
						relation[0][5]=appform.getRelTostatus();
						Object[][] indent=new Object[1][5];
						indent[0][0]=appform.getIndName();
						indent[0][1]=appform.getInfDob();
						indent[0][2]=appform.getIndTos();
						indent[0][3]=appform.getIndTom();
						indent[0][4]=appform.getIndTostatus();
						Object[][] dependents=new Object[1][4];
						dependents[0][0]=appform.getDepName();
						dependents[0][1]=appform.getDepDob();
						dependents[0][2]=appform.getDepTos();
						dependents[0][3]=appform.getDepTor();
						lnmodobj.setRelatives(relation);
						lnmodobj.setInterests(indent);
						lnmodobj.setDependents(dependents);
						relMap.put("RelationDet",lnmodobj);
						session.setAttribute("RelationDetMap",relMap);
					}else if(method.equalsIgnoreCase("SaveProperty")){
						HashMap PropMap=new HashMap();
						lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
						PropertyObject propertyobj=new PropertyObject();
						propertyobj.setTenantsDetail(appform.getTenant());
						propertyobj.setPropertyValue(appform.getValue());
						propertyobj.setPropertyNo(appform.getPropertyno());
						propertyobj.setAddress(appform.getAddr());
						propertyobj.setMeasurementEW(appform.getEwbyfeets());
						propertyobj.setMeasurementNS(appform.getNsbyfeets());
						propertyobj.setEastBy(appform.getEastby());
						propertyobj.setWestBy(appform.getWestby());
						propertyobj.setNorthBy(appform.getNorthby());
						propertyobj.setSouthBy(appform.getSouthby());
						propertyobj.setPropertyAqdBy(appform.getPropertyacquiredby());
						propertyobj.setPropertyNature(appform.getNature());
						lnmodobj.setPropertyDetails(propertyobj);
						PropMap.put("PropertyDet", lnmodobj);
						session.setAttribute("PropertyDetMap", PropMap);
						req.getSession().setAttribute("LnMasterObject", lnmodobj);
					}
				}else if(appform.getEmp_submit()!=null){
					if(appform.getEmp_submit().equalsIgnoreCase("SaveService")){
						IncomeObject incomeobject=null;
						incomeobject=new IncomeObject();
						incomeobject.setTypeOfIncome("Service");
						incomeobject.setName(appform.getEmployername());
						incomeobject.setAddress(appform.getAddress());
						incomeobject.setPhNo(appform.getPhoneno());
						incomeobject.setService(appform.getServ_length());
						incomeobject.setEmpNo(String.valueOf(appform.getEmpno()));
						incomeobject.setDesignation(appform.getDesignation());
						incomeobject.setDept(appform.getDepartment());
						incomeobject.setIncome(appform.getIncome());
						incomeobject.setExpenditure(appform.getExpenditure());
						incomeobject.setNetIncome(appform.getNetincome());
						session.setAttribute("Service", incomeobject);
					}else if(appform.getEmp_submit().equalsIgnoreCase("SaveBusiness")){
						IncomeObject incomeobject=null;
						incomeobject=new IncomeObject();
						incomeobject.setTypeOfIncome("Business");
						incomeobject.setName(appform.getEmploymtnature());
						incomeobject.setAddress(appform.getAddress());
						incomeobject.setNature(appform.getBusinessnature());
						incomeobject.setTurnOver(appform.getAvg_turnover());
						incomeobject.setPhNo(appform.getPhoneno());			
						incomeobject.setIncome(appform.getIncome());
						incomeobject.setExpenditure(appform.getExpenditure());
						incomeobject.setSurplus(appform.getSurplus());
						session.setAttribute("Business", incomeobject);
					}else if(appform.getEmp_submit().equalsIgnoreCase("SavePension")){
						IncomeObject incomeobject=null;
						incomeobject=new IncomeObject();
						incomeobject.setTypeOfIncome("Pension");
						incomeobject.setName(appform.getEmpname());
						incomeobject.setAddress(appform.getAddress());
						incomeobject.setPhNo(appform.getPhno());
						incomeobject.setBankName(appform.getBankname());
						incomeobject.setAccType(appform.getAcctype());
						incomeobject.setAccNo(String.valueOf(appform.getAccno()));
						incomeobject.setIncome(appform.getPensionamt());
						incomeobject.setNetIncome(appform.getNetincome());
						session.setAttribute("Pension", incomeobject);
					}else if(appform.getEmp_submit().equalsIgnoreCase("SaveRent")){
						IncomeObject incomeobject=null;
						incomeobject=new IncomeObject();
						incomeobject.setTypeOfIncome("Rent");
						incomeobject.setAddress(appform.getLand_addr());
						incomeobject.setIncome((appform.getTotamt()));
						incomeobject.setExpenditure((appform.getTax_payment()));
						incomeobject.setSurplus((appform.getNetincome()));
						incomeobject.setNetIncome((appform.getNetincome()));
						session.setAttribute("Rent", incomeobject);
					}else if(appform.getEmp_submit().equalsIgnoreCase("SaveSelfEmployed")){
						IncomeObject incomeobject=null;
						incomeobject=new IncomeObject();
						incomeobject.setAddress(appform.getAddress());
						incomeobject.setIncome((appform.getIncome()));
						incomeobject.setExpenditure((appform.getExpenditure()));
						incomeobject.setNetIncome((appform.getNetIncome()));
						incomeobject.setTypeOfIncome("Self");
						incomeobject.setPhNo(appform.getPhoneNum());
						incomeobject.setService((appform.getLoService()));
						session.setAttribute("Self", incomeobject);
					}
					appform.setEmp_submit(null);
			}
			lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
			HashMap empMap=new HashMap();
			array_incomeobject=new IncomeObject[5];
			IncomeObject incomeObject = (IncomeObject)req.getSession().getAttribute("Pension");
			IncomeObject incomeObject1 = (IncomeObject)req.getSession().getAttribute("Service");
			IncomeObject incomeObject2 = (IncomeObject)req.getSession().getAttribute("Business"); 
			IncomeObject incomeObject3 = (IncomeObject)req.getSession().getAttribute("Rent");
			IncomeObject incomeObject4 = (IncomeObject)req.getSession().getAttribute("Self");
			if(incomeObject!=null || incomeObject1!=null || incomeObject2!=null || incomeObject3!=null || incomeObject4!=null){
				array_incomeobject[0]=incomeObject4;
				array_incomeobject[1]=incomeObject1;
				array_incomeobject[2]=incomeObject2;
				array_incomeobject[3]=incomeObject;
				array_incomeobject[4]=incomeObject3;
			}
			if(array_incomeobject[0]!=null||array_incomeobject[1]!=null||array_incomeobject[2]!=null||array_incomeobject[3]!=null||array_incomeobject[4]!=null){
				lnmodobj.setIncomeDetails(array_incomeobject);
				empMap.put("EmploymentDet", lnmodobj);
				session.setAttribute("EmploymentDetMap",empMap);
				req.getSession().setAttribute("LnMasterObject",lnmodobj);
			}
			String modcode = appform.getLoantype();
			items_relavent = loandelegate.getReleventDetails_str(modcode);
			int count = 0;
			if (items_relavent != null) {
				for (int i = 0; i < items_relavent.length; i++) {
					if (items_relavent[i].equals("Y"))
						count++;
					if (items_relavent[i].equals("O"))
						count++;
				}
			}
			if (appform.getButton_value() != null) {
				if (appform.getButton_value().equalsIgnoreCase("Submit")) {
					session=req.getSession();
					HashMap submitMap=new HashMap();
					LoanMasterObject submitMasterObj=new LoanMasterObject();
					if(req.getSession().getAttribute("ApplicationDetMap")!=null){
						submitMap.put("SubmitAppDet", ((HashMap)req.getSession().getAttribute("ApplicationDetMap")).get("ApplicationDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitAppDet");
						submitMasterObj.setApplicationSrlNo(lnmodobj.getApplicationSrlNo());
						submitMasterObj.setApplicationDate(lnmodobj.getApplicationDate());
						submitMasterObj.setRequiredAmount(lnmodobj.getRequiredAmount());
						submitMasterObj.setRelative(lnmodobj.getRelative());
						submitMasterObj.setDirectorCode(lnmodobj.getDirectorCode());
						submitMasterObj.setPayMode(lnmodobj.getPayMode());
						submitMasterObj.setPaymentAcctype(lnmodobj.getPaymentAcctype());
						submitMasterObj.setPaymentAccno(lnmodobj.getPaymentAccno());
						submitMasterObj.setInterestType(lnmodobj.getInterestType());
						submitMasterObj.setInterestRateType(lnmodobj.getInterestRateType());
					}
					if(req.getSession().getAttribute("VehicleDetMap")!=null){
						submitMap.put("SubmitVehDet", ((HashMap)req.getSession().getAttribute("VehicleDetMap")).get("VehicleDet"));
						System.out.println("_inside SaveRelative_________________-");
						submitMap.put("SubmitVehDet", ((HashMap)req.getSession().getAttribute("VehicleDetMap")).get("VehicleDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitVehDet");
						VehicleObject vho=(VehicleObject)lnmodobj.getVehicleDet();
						submitMasterObj.setVehicleDet(vho);
					}
					if(req.getSession().getAttribute("RelationDetMap")!=null){
						submitMap.put("SubmitRelDet", ((HashMap)req.getSession().getAttribute("RelationDetMap")).get("RelationDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitRelDet");
						submitMasterObj.setRelatives(lnmodobj.getRelatives());
						submitMasterObj.setInterests(lnmodobj.getInterests());
						submitMasterObj.setDependents(lnmodobj.getDependents());
					}
					if(req.getSession().getAttribute("PropertyDetMap")!=null){
						submitMap.put("SubmitProDet", ((HashMap)req.getSession().getAttribute("PropertyDetMap")).get("PropertyDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitProDet");
						PropertyObject proobj=(PropertyObject)lnmodobj.getPropertyDetails();
						submitMasterObj.setPropertyDetails(proobj);
					}
					if(req.getSession().getAttribute("SignDetMap")!=null){
						submitMap.put("SubmitSignDet", ((HashMap)req.getSession().getAttribute("SignDetMap")).get("SignDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitSignDet");
						SignatureInstructionObject signobj[]=(SignatureInstructionObject[])lnmodobj.getSignatureDet();
						submitMasterObj.setSignatureDet(signobj);
					}
					if(req.getSession().getAttribute("EmploymentDetMap")!=null){
						submitMap.put("SubmitEmpDet", ((HashMap)req.getSession().getAttribute("EmploymentDetMap")).get("EmploymentDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitEmpDet");
						IncomeObject[] incomeObj=(IncomeObject[])lnmodobj.getIncomeDetails();
						submitMasterObj.setIncomeDetails(incomeObj);
					}
					if(req.getSession().getAttribute("CoborrowerDetMap")!=null){
						submitMap.put("SubmitCoDet", ((HashMap)req.getSession().getAttribute("CoborrowerDetMap")).get("CoborrowerDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitCoDet");
						coborrowers=lnmodobj.getCoBorrowers();
						submitMasterObj.setNoOfCoBorrowers(coborrowers.size());
						submitMasterObj.setCoBorrowers(coborrowers);
					}
					if(req.getSession().getAttribute("SuritiesDetMap")!=null){
						submitMap.put("SubmitSurDet", ((HashMap)req.getSession().getAttribute("SuritiesDetMap")).get("SuritiesDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitSurDet");
						surities=lnmodobj.getSurities();
						submitMasterObj.setNoOfSurities(surities.size());
						submitMasterObj.setSurities(surities);
					}
					if(req.getSession().getAttribute("GoldDetMap")!=null){
						submitMap.put("SubmitGoldDet", ((HashMap)req.getSession().getAttribute("GoldDetMap")).get("GoldDet"));
						lnmodobj=(LoanMasterObject)submitMap.get("SubmitGoldDet");
						GoldObject goldObject=(GoldObject)lnmodobj.getGoldDet();
						submitMasterObj.setGoldDet(goldObject);
					}
					submited_details =null;
					submited_details = new boolean[11];
					submited_details[0]= true;
					if(items_relavent!=null){
						if(items_relavent[1].equalsIgnoreCase("Y") ){
							if(req.getSession().getAttribute("RelationDetMap")!=null)
								submited_details[1]=true;
							else if(req.getSession().getAttribute("RelationDetMap")==null ){
								 req.setAttribute("msg", "Relative Details is must");
								 appform.setMain_submit(null);
							}
						}else if(items_relavent[1].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("RelationDetMap")!=null)
								submited_details[1]=true;
						}
						if(items_relavent[2].equalsIgnoreCase("Y")){
							if((incomeObject!=null || incomeObject1!=null || incomeObject2!=null || incomeObject3!=null || incomeObject4!=null) && req.getSession().getAttribute("EmploymentDetMap")!=null)
								submited_details[2]=true;
							else{
								req.setAttribute("msg", "Employment Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[2].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("EmploymentDetMap")!=null)
								submited_details[2]=true;
						}
						if(items_relavent[3].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("ApplicationDetMap")!=null)
								submited_details[3]=true;
							else if(req.getSession().getAttribute("ApplicationDetMap")==null ){
								req.setAttribute("msg", "Application Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[3].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("ApplicationDetMap")!=null)
								submited_details[3]=true;
						}
						submited_details[4]=true; // Loan and Share details
						if(items_relavent[5].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("SignDetMap")!=null)
								submited_details[5]=true;
							else if(req.getSession().getAttribute("SignDetMap")==null){
								req.setAttribute("msg", "SignatureDetails is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[5].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("SignDetMap")!=null)
								submited_details[5]=true;
						}
						if(items_relavent[6].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("PropertyDetMap")!=null)
								submited_details[6]=true;
							else if(req.getSession().getAttribute("PropertyDetMap")==null ){
								req.setAttribute("msg", "Property Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[6].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("PropertyDetMap")!=null)
								submited_details[6]=true;
						}
						if(items_relavent[7].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("CoborrowerDetMap")!=null)
								submited_details[7]=true;
							else if(req.getSession().getAttribute("CoborrowerDetMap")==null ){
								req.setAttribute("msg", "Coborrowers Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[7].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("CoborrowerDetMap")!=null)
								submited_details[7]=true;
						}
						if(items_relavent[8].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("SuritiesDetMap")!=null)
								submited_details[8]=true;
							else if(req.getSession().getAttribute("SuritiesDetMap")==null ){
								req.setAttribute("msg", "Surities Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[8].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("SuritiesDetMap")!=null)
								submited_details[8]=true;
						}
						if(items_relavent[9].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("VehicleDetMap")!=null)
								submited_details[9]=true;
							else if(req.getSession().getAttribute("VehicleDetMap")==null ){
								req.setAttribute("msg", "Vehicle Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[9].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("VehicleDetMap")!=null)
								submited_details[9]=true;
						}
						if(items_relavent[10].equalsIgnoreCase("Y")){
							if(req.getSession().getAttribute("GoldDetMap")!=null)
								submited_details[10]=true;
							else if(req.getSession().getAttribute("GoldDetMap")==null ){
								req.setAttribute("msg", "Gold Details is must");
								appform.setMain_submit(null);
							}
						}else if(items_relavent[10].equalsIgnoreCase("O")){
							if(req.getSession().getAttribute("GoldDetMap")!=null)
								submited_details[10]=true;
						}
					}
					CustomerMasterObject cm=null;				
					signObject1=delegate.getSignatureDetails(appform.getShno(), appform.getShtype());
					if(signObject1!=null){
						if(signObject1[0].getCid()!=0){
							 cm=(CustomerMasterObject)loandelegate.getCustomer(signObject1[0].getCid());
							 submitMasterObj.setCustomerId(cm.getCustomerID());
							 submitMasterObj.setSubmitedDetails(submited_details);
							 submitMasterObj.setShareAccNo(appform.getShno());
							 submitMasterObj.setShareAccType(appform.getShtype());
						
							 if(appform.getLoantype()!=null){
								
							 submitMasterObj.setModuleCode(Integer.parseInt(appform.getLoantype()));
							 submitMasterObj.setPurposeCode(appform.getPurpose());
							 submitMasterObj.uv.setUserId(lnuser);
							 submitMasterObj.uv.setUserTml(lntml);
							 submitMasterObj.uv.setUserDate(loandelegate.getSysDateTime());
							 submitMasterObj.uv.setVerId(lnuser);
							 submitMasterObj.uv.setVerTml(lntml);
							 submitMasterObj.uv.setVerDate(loandelegate.getSysDateTime());
							 }
						}
					}
					int rest =0;
					if(appform.getMain_submit()!=null && appform.getMain_submit().equalsIgnoreCase("Submit") && req.getAttribute("msg")==null ){
							rest=loandelegate.storeLoanMaster(submitMasterObj,0);
							if (rest != 0){
								req.setAttribute("msg", "New Loan Account number is  " + rest );
								appform.setValidate(rest);
							    session.setAttribute("LnMasterObject", null);
							    req.getSession().setAttribute("RelationDetMap", null);
								req.getSession().setAttribute("Pension", null);
								req.getSession().setAttribute("Service", null);
								req.getSession().setAttribute("Business", null);
								req.getSession().setAttribute("Rent", null);
								req.getSession().setAttribute("Self", null);
								req.getSession().setAttribute("EmploymentDetMap", null);
								req.getSession().setAttribute("ApplicationDetMap", null);
								req.getSession().setAttribute("SignDetMap", null);
								req.getSession().setAttribute("PropertyDetMap", null);
								req.getSession().setAttribute("CoborrowerDetMap", null);
								req.getSession().setAttribute("SuritiesDetMap", null);
								req.getSession().setAttribute("VehicleDetMap", null);
								req.getSession().setAttribute("GoldDetMap", null);
								req.getSession().setAttribute("count", null);
								appform.setButton_value(null);
								appform.setMain_submit(null);
							}
						}
					}
				}
			if(appform.getPageidentity().getPageId()!=null)
				if(MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())){
					path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
					IntialParameters(req, loandelegate);
					req.setAttribute("pageId", path);
					try{
						Vector vec_final[] = loandelegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
					}catch (NullPointerException e){
						appform.setLoantype("Select");
					}
					return map.findForward(ResultHelp.getSuccess());
				}
			}catch(Exception e){
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e.toString(), req, path);
				return map.findForward(ResultHelp.getError());
			}
		}
		if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanRecoveryMenu")) {
			System.out.println("Inside /Loans/LoanRecoveryMenu");
			try {
				delegate = new LoansDelegate();
				RecoveryForm recovform = (RecoveryForm) form;
				LoanStatusForm staform = recovform.getLoanstatusform();
				getInitialPramforRecoveryByTransfer(req, delegate);
				req.setAttribute("TabNum", "3");
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				getTabbedpane(req, recovform);
				System.out.println("===============>"+ recovform.getTabPaneHeading());
				if (recovform.getTabPaneHeading() != null) {
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
						req.setAttribute("TabNum", "1");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanHistory")) {
						req.setAttribute("TabNum", "2");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("RecoveryByTransfer")) {
						req.setAttribute("TabNum", "3");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanTran")) {
						req.setAttribute("TabNum", "4");
					}
				}
				if (MenuNameReader.containsKeyScreen(recovform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(recovform.getPageidentity().getPageId());
					OtherchargesInitailParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		// LoanRecovery
		/*********** Query on Loan Status**************/
		else if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanRecovery")) {
			try {
				RecoveryForm recovform = (RecoveryForm) form;
				
				
				LoanStatusForm statform = recovform.getLoanstatusform();
				LoanHistForm histform = recovform.getLoanhistform();
				LastLoanTranForm lasttran = recovform.getLastloantran();
				delegate = new LoansDelegate();
				req.setAttribute("LoanStatus", statform);
				req.setAttribute("LoanHistory", histform);
				req.setAttribute("LoanTran", lasttran);
				OtherchargesInitailParam(req, delegate);
				getInitialPramforRecoveryByTransfer(req, delegate);
				System.out.println("Systemmmmmmmmm Date"+ LoansDelegate.getSysDate());
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				getTabbedpane(req, recovform);
				System.out.println("===============>"+ recovform.getTabPaneHeading());
				if (recovform.getTabPaneHeading() != null) {
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
						req.setAttribute("TabNum", "1");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanHistory")) {
						req.setAttribute("TabNum", "2");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("RecoveryByTransfer")) {
						req.setAttribute("TabNum", "3");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanTran")) {
						req.setAttribute("TabNum", "4");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("Surity/Co-Borrower")) {
						req.setAttribute("TabNum", "5");
					}
				}
				System.out.println("recovform.getAccno()"+ recovform.getAccno());
				System.out.println("recovform.getAcctype()"+ recovform.getAcctype());
				if (recovform.getAccno() != 0) {
					double loan_amt=0;
					double share_amt=0;
					double disb_amt=0;
					double amt=0;
				    double sanc_amt=0; //dee
					lmobj = delegate.getQueryOnLoanStatus(recovform.getAcctype(), recovform.getAccno(), LoansDelegate.getSysDate());
					if(lmobj!=null)
					{	
					System.out.println("************Loan Master Object***************" + lmobj.getShareAccNo());
					Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
					System.out.println("-------------Share Details------------" + obj.length);
					for(int sh=0;sh<obj.length;sh++){
						if(obj[sh][3]!=null && obj[sh][4]!=null && obj[sh][5]!=null){
					       amt=((Double.parseDouble(obj[sh][3].toString())+Double.parseDouble(obj[sh][4].toString()))*Double.parseDouble(obj[sh][5].toString())/100);//2,3,4
					       loan_amt+=Double.parseDouble(obj[sh][4].toString());//3
						   share_amt+=(amt);//5
						   disb_amt+=Double.parseDouble(obj[sh][3].toString());     //2
						   sanc_amt+=Double.parseDouble(obj[sh][2].toString());
						}
					}
					if(lmobj.getClosedt()!=null){
						req.setAttribute("msg", "Given Account Number is Closed");
					}
					else if (lmobj != null) {
						req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						req.setAttribute("panelName", "Personal");
						recovform.setName(lmobj.getName());
						recovform.setShno(lmobj.getShareAccNo());
						recovform.setShamt((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString()));
						recovform.setShtype(obj[0][3].toString());
						recovform.setExcessshamt(((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString())-(share_amt)));
						recovform.setDisbursed_on(lmobj.getSanctionDate());
						recovform.setInstamt(lmobj.getInstallmentAmt());
						recovform.setIntrate(lmobj.getInterestRate());
						recovform.setPenrate(lmobj.getPenalRate());
						recovform.setPurpose(lmobj.getLdgPrntDate());
						recovform.setPeriod(lmobj.getLastTrnSeq());
						recovform.setLoanamt(lmobj.getSanctionedAmount());
						System.out.println("obj[0][2]======>>>>"+(Double.parseDouble(obj[0][2].toString())));
						System.out.println("AddshareAmount=======>>>"+lmobj.getAddShareAmount());
						if (lmobj.getInterestType() == 1)
							recovform.setRepay_type("Reducing Balance");
						else
							recovform.setRepay_type("EMI");
						if (lmobj.getInterestRateType() == 1)
							recovform.setInt_rate_type("Fixed");
						else
							recovform.setInt_rate_type("Floating");
						lntrnobj = delegate.getQueryLoanStatus(recovform.getAcctype(), recovform.getAccno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDateTime());
						System.out.println("LOanTran Object"+ lntrnobj.toString());
						req.setAttribute("LoanTranObject", lntrnobj);
						if(lntrnobj != null) 
						{
							statform.setTxt_loanba(lntrnobj.getLoanBalance());
							statform.setTxt_addpaid(String.valueOf(lntrnobj.getPrincipalPaid()));
							req.setAttribute("AdvancePaidAmount", String.valueOf(lntrnobj.getPrincipalPaid()));
							statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPrincipalBalance()));
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
							req.setAttribute("TotalLoanAmount", String.valueOf(total_amt));
							/*lbl_total_amount.setText(nf.format(total_amt));
					        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
					        lbl_close_amt.setText(nf.format(total_amt));*/
							double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
							System.out.println("*********************** Total Closeable amount****************" + close_amt);
							statform.setTxt_totcloamt(String.valueOf(close_amt));
							req.setAttribute("TotalClosableAmount", String.valueOf(close_amt));
						}
						System.out.println("Cid" + lmobj.getCustomerId());
						System.out.println("Mailing Address"+ lmobj.getMailingAddress());
						HashMap maps = null;
						Address address = null;
						maps = delegate.getAddress(lmobj.getCustomerId());
						for(int i=0;i<maps.size();i++){
							Collection collection=maps.values();
							Iterator iterator=collection.iterator();
							while(iterator.hasNext()){
								address=(Address)iterator.next();
							}
						}
						if(address != null) {
							System.out.println("Address object===========>"+ address.toString());
							System.out.println("Phno" + address.getPhno());
							System.out.println("Mobile" + address.getMobile());
							System.out.println("Fax" + address.getFax());
							System.out.println("Email" + address.getEmail());
							histform.setPhone(address.getPhno());
							histform.setMob(address.getMobile());
							histform.setFax(address.getFax());
							histform.setEmail(address.getEmail());
						}
						arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
						if (!arraylist.isEmpty()) {
							System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
							lasttran.setLastprincipleamt(arraylist.get(6).toString());
							lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
							lasttran.setLastintamt(arraylist.get(4).toString());
							lasttran.setLastintamttrnamt(arraylist.get(5).toString());
							lasttran.setLastpiamt(arraylist.get(3).toString());
							lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
							lasttran.setLastotheramt(arraylist.get(1).toString());
							lasttran.setLastprincipledate(arraylist.get(6).toString());
							lasttran.setLastintdate(arraylist.get(4).toString());
							lasttran.setLastpidate(arraylist.get(2).toString());
							lasttran.setLastotherdate(arraylist.get(0).toString());
						}
						arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
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

						System.out.println("NPAStage" + histform.getNpastage());
						System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
						System.out.println("Recovery Account Number==>"+ recovform.getAccno());
						ArrayList<String> arraylist=new ArrayList<String>();
						SurityCoBorrower suritycb=new SurityCoBorrower();
						System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetailsTwo(recovform.getAcctype(), recovform.getAccno()));
						req.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetailsTwo(recovform.getAcctype(), recovform.getAccno()));
						arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
						if(!arraylist.isEmpty()) 
						{
							Iterator iterator = arraylist.iterator();
							while (iterator.hasNext()) {
								String strr = (String) iterator.next();
								if(strr!=null){
									if (strr.length() > 0) {
										histform.setLastnotice(strr);
									}
								}
							}
						}

					}
				}
					else
					{
						req.setAttribute("msg","Account Number Not Found");
					}
				}else{
					req.setAttribute("msg","Enter Account Number");
				}
				if (recovform.getTrf_voucherno() != 0 && recovform.getTrf_voucherno() > 0) {
					LoanTransactionObject loanTransactionObject=null;
					LoanTransactionObject obj_transfer=null;
					try{
						    loanTransactionObject = delegate.getTransferVoucherNo(recovform.getTrf_voucherno(),LoansDelegate.getSysDate());
						    if(loanTransactionObject==null){
						        req.setAttribute("msg","Transfer Voucher No Not Found");
						        recovform.setTrf_voucherno(0);
						    }
						    else if(loanTransactionObject.uv.getVerTml() != null){
						    	req.setAttribute("msg","Transfer Vouvher No Already Verified");
						    	recovform.setTrf_voucherno(0);
						    }
						    else
						    {
								lmobj = delegate.getQueryOnLoanStatus(loanTransactionObject.getAccType(), loanTransactionObject.getAccNo(), LoansDelegate.getSysDate());
								Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
								recovform.setAcctype(loanTransactionObject.getAccType());
								recovform.setAccno(loanTransactionObject.getAccNo());
								if (lmobj != null) {
									req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
									req.setAttribute("panelName", "Personal");
									recovform.setName(lmobj.getName());
									recovform.setShno(lmobj.getShareAccNo());
									recovform.setShamt((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString()));
									recovform.setShtype(obj[0][3].toString());
									recovform.setExcessshamt(lmobj.getAddShareAmount());
									recovform.setDisbursed_on(lmobj.getSanctionDate());
									recovform.setInstamt(lmobj.getInstallmentAmt());
									recovform.setIntrate(lmobj.getInterestRate());
									recovform.setPenrate(lmobj.getPenalRate());
									recovform.setPurpose(lmobj.getLdgPrntDate());
									recovform.setPeriod(lmobj.getLastTrnSeq());
									recovform.setLoanamt(lmobj.getSanctionedAmount());
									if (lmobj.getInterestType() == 1)
										recovform.setRepay_type("Reducing Balance");
									else
										recovform.setRepay_type("EMI");
									if (lmobj.getInterestRateType() == 1)
										recovform.setInt_rate_type("Fixed");
									else
										recovform.setInt_rate_type("Floating");
									lntrnobj = delegate.getQueryLoanStatus(recovform.getAcctype(), recovform.getAccno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDateTime());
									System.out.println("LOanTran Object"+ lntrnobj.toString());
									req.setAttribute("LoanTranObject", lntrnobj);
									if (lntrnobj != null) 
									{
										statform.setTxt_loanba(lntrnobj.getLoanBalance());
										statform.setTxt_addpaid(DoubleFormat.toString(lntrnobj.getPrincipalPaid()));
										statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPrincipalBalance()));
										System.out.println("lntrnobj.getPrincipalBalance()" + lntrnobj.getPrincipalBalance());
										statform.setTxt_current_inst(DoubleFormat.toString(lntrnobj.getPrincipalPayable()));
										statform.setTxt_interest(DoubleFormat.toString(lntrnobj.getInterestPayable()));
										statform.setTxt_pinealInt(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
										statform.setTxt_otherchar(DoubleFormat.toString(lntrnobj.getOtherAmount()));
										statform.setTxt_extra(DoubleFormat.toString(lntrnobj.getExtraIntAmount()));
										/*double total_amt = obj_loanstatus.getPrincipalBalance()+obj_loanstatus.getPrincipalPayable()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount();*/
										double total_amt = lntrnobj.getPrincipalBalance()+lntrnobj.getPrincipalPayable()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
										System.out.println("*************Total Amount**************" + total_amt);
										statform.setTxt_tca(DoubleFormat.toString(total_amt));
										/*lbl_total_amount.setText(nf.format(total_amt));
								        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
								        lbl_close_amt.setText(nf.format(total_amt));*/
										double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
										System.out.println("*********************** Total Closeable amount****************" + close_amt);
										statform.setTxt_totcloamt(DoubleFormat.toString(close_amt));
									}
									System.out.println("Cid" + lmobj.getCustomerId());
									System.out.println("Mailing Address"+ lmobj.getMailingAddress());
									HashMap maps = null;
									maps = delegate.getAddress(lmobj.getCustomerId());
									Address address = (Address) maps.get(new Integer(lmobj.getMailingAddress()));
									System.out.println("Address object===========>"+ address);
									System.out.println("Phno" + address.getPhno());
									System.out.println("Mobile" + address.getMobile());
									System.out.println("Fax" + address.getFax());
									System.out.println("Email" + address.getEmail());
									if (address != null) {
										histform.setPhone(address.getPhno());
										histform.setMob(address.getMobile());
										histform.setFax(address.getFax());
										histform.setEmail(address.getEmail());
									}
									arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
									if (!arraylist.isEmpty()) {
										System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
										lasttran.setLastprincipleamt(arraylist.get(6).toString());
										lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
										lasttran.setLastintamt(arraylist.get(4).toString());
										lasttran.setLastintamttrnamt(arraylist.get(5).toString());
										lasttran.setLastpiamt(arraylist.get(3).toString());
										lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
										lasttran.setLastotheramt(arraylist.get(1).toString());
									}
									arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
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
									System.out.println("NPAStage" + histform.getNpastage());
									System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
									System.out.println("Recovery Account Number==>"+ recovform.getAccno());
									System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
									ArrayList surity = delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno());
									System.out.println("size==============>"+ surity.size());
									for (int i = 0; i < surity.size(); i++) {
										System.out.println("data=====>" + surity.get(i));
									}
									req.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
									arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
									if (!arraylist.isEmpty()) {
										Iterator iterator = arraylist.iterator();
										while (iterator.hasNext()) {
											String strr = (String) iterator.next();
											if (strr.length() > 0) {
												histform.setLastnotice(strr);
											}
										}
									}

								}
						    	try{
						    		obj_transfer=delegate.getQueryLoanStatus(loanTransactionObject.getAccType(), loanTransactionObject.getAccNo(), loanTransactionObject.getTransactionDate(), lnuser, lntml, delegate.getSysDateTime());
						    	}catch(Exception e){
						    		e.printStackTrace();
						    	}
						        int i=0;	
						        int ac_no=0;
						        StringTokenizer st = new StringTokenizer(loanTransactionObject.getTranNarration()," ");
						        String acc_type = st.nextToken();
						        System.out.println("Amzad ****************"+acc_type+"****");
						        recovform.setTrf_from(acc_type); 
						        ac_no=Integer.parseInt((st.nextToken()));
						        recovform.setTrf_accno(ac_no);
						        AccountObject am=null;
						        try {
						            am = delegate.getAccount(null, acc_type,ac_no);
						        } catch (NumberFormatException e) {
						            e.printStackTrace();
						        } catch (RemoteException e) {
						            e.printStackTrace();
						        }  
						        recovform.setBalance(am.getAmount());
						       recovform.setAmount(loanTransactionObject.getTransactionAmount());	
						         double transfer_amount=0.0;
						    	try {
						    		transfer_amount=loanTransactionObject.getTransactionAmount();
									double temp_transfer_amount=transfer_amount;
								    if(obj_transfer != null) {	        
								        double max_req_amt = obj_transfer.getLoanBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount()-obj_transfer.getExtraIntAmount();
								        if(transfer_amount > max_req_amt) {
								            req.setAttribute("msg","More than required amount");
								            recovform.setAmount(0.0);
								        }
								        else {
								        	 System.out.println("Extra Amonut"+obj_transfer.getExtraIntAmount()+"Transafer_amt"+loanTransactionObject.getTransactionAmount());
								        	 System.out.println("Max_req_amt"+max_req_amt+"LoanBalance"+obj_transfer.getLoanBalance()+"Interst Payable"+obj_transfer.getInterestPayable()+"OtherAmont"+obj_transfer.getOtherAmount());
								        	 recovform.setExtraint(obj_transfer.getExtraIntAmount());
								            if(transfer_amount > obj_transfer.getOtherAmount()) {
								            	recovform.setOthercharges(obj_transfer.getOtherAmount());
								                transfer_amount -= obj_transfer.getOtherAmount();
								            }
								            else {
								            	recovform.setOthercharges(transfer_amount);
								                transfer_amount = 0;
								            }
								            if(transfer_amount > obj_transfer.getPenaltyAmount()) {
								            	recovform.setPenalinterest(obj_transfer.getPenaltyAmount());
								                transfer_amount -= obj_transfer.getPenaltyAmount();
								            }
								            else {
								            	recovform.setPenalinterest(transfer_amount);
								                transfer_amount = 0;
								            }
								            if(transfer_amount > (obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount())) {
								            	System.out.println("Intersest Payable=====>"+obj_transfer.getInterestPayable()+"Extra Amount"+obj_transfer.getExtraIntAmount());
								            	recovform.setInterest(obj_transfer.getInterestPayable());
								            	System.out.println("hey Ther===========>"+(obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount()));
								            	recovform.setIntuptodate(Validations.addDays(delegate.getSysDate(), -1));
								                transfer_amount -= obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount();
								                System.out.println("Transafer Amount=====>"+transfer_amount);
								            }
								            else {
								            	LoanTransactionObject ltrn=delegate.calculateInterestForRecovery(obj_transfer.getAccType(),obj_transfer.getAccNo(),transfer_amount);
								            	if(ltrn != null) {
								            		recovform.setInterest(transfer_amount);
								            		recovform.setIntuptodate(ltrn.getIntUptoDate());
								            	} else {
								            		recovform.setInterest(transfer_amount);
								            		recovform.setIntuptodate("");
								            	}
								                transfer_amount = 0;
								            }
								            if(obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable() > 0 ) {
								            	recovform.setPrinciple(obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable());
								                transfer_amount -= obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable();  // Code changed by Murugesh on 14/03/2006
								                System.out.println("The getPrincipalBalance is:"+obj_transfer.getPrincipalBalance());
								                System.out.println(" the getPrincipalPayable is:"+obj_transfer.getPrincipalPayable());
								                System.out.println("The getPrincipalBalance is:"+obj_transfer.getInterestPayable());
								                System.out.println(" the getPrincipalPayable is:"+obj_transfer.getInterestPaid());
								                recovform.setPrinciple(transfer_amount); // Code added by Murugesh on 14/03/2006
								                 if(temp_transfer_amount>obj_transfer.getPrincipalBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount())
								                 {
								                	 recovform.setAdvance(temp_transfer_amount-(obj_transfer.getPrincipalBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount()));
								                 }
								            } 
								            else {
								            	recovform.setAdvance(transfer_amount);
								            	recovform.setPrinciple(0.0);
								            }  
								        }
								    }
								} catch (Exception exe) {
									exe.printStackTrace();
								}
						    }
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				if (recovform.getTrf_accno() != 0
						&& recovform.getTrf_accno() > 0) {
					System.out.println("!!!!!!!!!!!!!getting inside tranmsfer account!!!!!!!!!!!!!!!");
					int i = 0;
					moduleobject_transfer = delegate.getMainModules(2,"'1002000','1007000','1015000','1014000','1017000','1018000'");
					am = delegate.getAccount(null, recovform.getTrf_from(),recovform.getTrf_accno());
					System.out.println("account object====>===>" + am);
					if (am == null) {
						System.out.println("Account not found");
						req.setAttribute("Valid_account", "AccountNotFound");
						recovform.setBalance(0.0);
					} else if (am.getAccStatus().equals("C")) {
						req.setAttribute("Valid_account", "AccountClosed");
						recovform.setBalance(0.0);
					} else if (am.getVerified() == null) {// Account not verified
						req.setAttribute("Valid_account", "AccountNotVerified");
						recovform.setBalance(0.0);
					} else if (am.getDefaultInd().equals("T")) {// Account is blocked
						req.setAttribute("Valid_account", "AccountBlocked");
						recovform.setBalance(0.0);
					} else if (am.getFreezeInd().equals("T")) {// Account is blocked
						System.out.println("Accno freezed");
						req.setAttribute("Valid_account", "AccountFrezed");
						recovform.setBalance(0.0);
					} else {
						recovform.setBalance(am.getAmount());
						System.out.println("Name===>" + am.getAccname());
					}
					if (recovform.getButton_value() != null) {
						System.out.println("inside button value==>"+ recovform.getButton_value());
						if (recovform.getButton_value().equalsIgnoreCase("submit") || recovform.getButton_value().equalsIgnoreCase("verify")) {
							System.out.println("=====In setTransactionObject====");
							System.out.println("module transfer object===>" + moduleobject_transfer);
							LoanTransactionObject obj_submit = new LoanTransactionObject();
							int ln = 0;
							System.out.println("Transfer account number===>"+ recovform.getTrf_accno());
							System.out.println("Transfer trf from===>" + recovform.getTrf_from());
							System.out.println("Transfer amount===>" + recovform.getAmount());
							 obj_submit.setAccType(recovform.getAcctype());
							 obj_submit.setAccNo(recovform.getAccno());
							 obj_submit.setTransactionDate(delegate.getSysDate());
							 obj_submit.setTranType(recovform.getTrf_from());
							 obj_submit.setTranSequence(recovform.getTrf_accno());
							 obj_submit.setTransactionAmount(recovform.getAmount());
							 obj_submit.setOtherAmount(recovform.getOthercharges());
							 obj_submit.setPenaltyAmount(recovform.getPenalinterest());
							 if(recovform.getInterest() > 0){
							 obj_submit.setInterestPayable(recovform.getInterest());
							 obj_submit.setExtraIntAmount(0); } else {
							 obj_submit.setInterestPayable(0);
							 obj_submit.setExtraIntAmount(recovform.getExtraint()); }
							  if(recovform.getTrf_voucherno()!=0) 
							 obj_submit.uv.setUserTml(lntml);
							 obj_submit.uv.setUserId(lnuser);
							 obj_submit.uv.setUserDate(delegate.getSysDate());
							 if(recovform.getButton_value().equalsIgnoreCase("submit")){
							try{
							int vch=delegate.storeLoanAmount(obj_submit, 1, delegate.getSysDate());
							if(vch>0){
								req.setAttribute("msg","Transaction Successfull..! Note Down Voucher No:  "+vch);
								recovform.setClearFl("Y");
								recovform.setName("");
								recovform.setTrf_accno(0);
								recovform.setAmount(0.0);
							}else{
								req.setAttribute("msg","Transaction is Incomplete...!");
							}
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(recovform.getButton_value().equalsIgnoreCase("verify")){
							try{
								int verifyVch=delegate.recoverLoanAmount(obj_submit);
								if(verifyVch==1){
									req.setAttribute("msg","Voucher Number Verified Successfully...!");
									recovform.setClearFl("Y");
									
									recovform.setTrf_accno(0);
									recovform.setAmount(0.0);
								}else{
									req.setAttribute("msg","Error During Verification...!");
								}
								}catch(Exception e){
									e.printStackTrace();
								}
						}
						}
					}
				}
				if (recovform.getAmount() != 0 && recovform.getAmount() > 0) {
					int i = 0;
					double transfer_amount = 0;
					transfer_amount = recovform.getAmount();
					System.out.println("Object!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ lmobj.toString());
					if (transfer_amount <= 0) {// Account is blocked
                     req.setAttribute("msg", "Amount Should be greater than Zero...!");
						req.setAttribute("msg", "Insufficient Balance...!");// Account is blocked
					} else {
						System.out.println("Before calling setPayPanel");
						setPayPanel(recovform, transfer_amount);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().trim().equalsIgnoreCase("/Loans/RecoveryVerifyMenu")) {
			System.out.println("Inside /Loans/LoanRecoveryMenu");
			try {
				delegate = new LoansDelegate();
				RecoveryForm recovform = (RecoveryForm) form;
				LoanStatusForm staform = recovform.getLoanstatusform();
				getInitialPramforRecoveryByTransfer(req, delegate);
				req.setAttribute("TabNum", "3");
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				getTabbedpane(req, recovform);
				System.out.println("===============>"+ recovform.getTabPaneHeading());
				if (recovform.getTabPaneHeading() != null) {
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
						req.setAttribute("TabNum", "1");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanHistory")) {
						req.setAttribute("TabNum", "2");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("RecoveryByTransfer")) {
						req.setAttribute("TabNum", "3");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanTran")) {
						req.setAttribute("TabNum", "4");
					}
				}
				if (MenuNameReader.containsKeyScreen(recovform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(recovform.getPageidentity().getPageId());
					OtherchargesInitailParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (map.getPath().trim().equalsIgnoreCase("/Loans/RecoveryVerify")) {
			try {
				RecoveryForm recovform = (RecoveryForm) form;
				LoanStatusForm statform = recovform.getLoanstatusform();
				LoanHistForm histform = recovform.getLoanhistform();
				LastLoanTranForm lasttran = recovform.getLastloantran();
				delegate = new LoansDelegate();
				req.setAttribute("LoanStatus", statform);
				req.setAttribute("LoanHistory", histform);
				req.setAttribute("LoanTran", lasttran);
				OtherchargesInitailParam(req, delegate);
				getInitialPramforRecoveryByTransfer(req, delegate);
				System.out.println("Systemmmmmmmmm Date"+ LoansDelegate.getSysDate());
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				getTabbedpane(req, recovform);
				System.out.println("===============>"+ recovform.getTabPaneHeading());
				if (recovform.getTabPaneHeading() != null) {
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
						req.setAttribute("TabNum", "1");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanHistory")) {
						req.setAttribute("TabNum", "2");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("RecoveryByTransfer")) {
						req.setAttribute("TabNum", "3");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("LoanTran")) {
						req.setAttribute("TabNum", "4");
					}
					if (recovform.getTabPaneHeading().equalsIgnoreCase("Surity/Co-Borrower")) {
						req.setAttribute("TabNum", "5");
					}
				}
				System.out.println("recovform.getAccno()"+ recovform.getAccno());
				System.out.println("recovform.getAcctype()"+ recovform.getAcctype());
				if (recovform.getAccno() != 0) {
					double loan_amt=0;
					double share_amt=0;
					double disb_amt=0;
					double amt=0;
				    double sanc_amt=0; //dee
					lmobj = delegate.getQueryOnLoanStatus(recovform.getAcctype(), recovform.getAccno(), LoansDelegate.getSysDate());
					if(lmobj!=null)
					{	
					System.out.println("************Loan Master Object***************" + lmobj.getShareAccNo());
					Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
					System.out.println("-------------Share Details------------" + obj.length);
					for(int sh=0;sh<obj.length;sh++)
					{
						System.out.println("----loan and sh values of obj----"+obj[sh][0]);
						System.out.println("----loan and sh values of obj----"+obj[sh][1]);
						System.out.println("----loan and sh values of obj----"+obj[sh][2]);
						System.out.println("----loan and sh values of obj----"+obj[sh][3]);
						System.out.println("----loan and sh values of obj----"+obj[sh][4]);
						System.out.println("----loan and sh values of obj----"+obj[sh][5]);
						if(obj[sh][3]!=null && obj[sh][4]!=null && obj[sh][5]!=null){
					       amt=((Double.parseDouble(obj[sh][3].toString())+Double.parseDouble(obj[sh][4].toString()))*Double.parseDouble(obj[sh][5].toString())/100);//2,3,4
					       loan_amt+=Double.parseDouble(obj[sh][4].toString());//3
						//   dtm.setValueAt(String.valueOf(amt),(i-1),6);//5
						   share_amt+=(amt);//5
						   disb_amt+=Double.parseDouble(obj[sh][3].toString());     //2
						   sanc_amt+=Double.parseDouble(obj[sh][2].toString());
						}
					}
					if(lmobj.getClosedt()!=null){
						req.setAttribute("msg", "Given Account Number is Closed");
					}
					else if (lmobj != null) {
						req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						req.setAttribute("panelName", "Personal");
						recovform.setName(lmobj.getName());
						recovform.setShno(lmobj.getShareAccNo());
						recovform.setShamt((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString()));
						recovform.setShtype(obj[0][3].toString());
						recovform.setExcessshamt(((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString())-(share_amt)));
						recovform.setDisbursed_on(lmobj.getSanctionDate());
						recovform.setInstamt(lmobj.getInstallmentAmt());
						recovform.setIntrate(lmobj.getInterestRate());
						recovform.setPenrate(lmobj.getPenalRate());
						recovform.setPurpose(lmobj.getLdgPrntDate());
						recovform.setPeriod(lmobj.getLastTrnSeq());
						recovform.setLoanamt(lmobj.getSanctionedAmount());
						System.out.println("obj[0][2]======>>>>"+(Double.parseDouble(obj[0][2].toString())));
						System.out.println("AddshareAmount=======>>>"+lmobj.getAddShareAmount());
						if (lmobj.getInterestType() == 1)
							recovform.setRepay_type("Reducing Balance");
						else
							recovform.setRepay_type("EMI");
						if (lmobj.getInterestRateType() == 1)
							recovform.setInt_rate_type("Fixed");
						else
							recovform.setInt_rate_type("Floating");//getQueryLoanStatus(String ln_acty,int ln_acno,String trn_date,String user,String terminal,String datetime)  
						lntrnobj = delegate.getQueryLoanStatus(recovform.getAcctype(), recovform.getAccno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDateTime());
						System.out.println("LOanTran Object"+ lntrnobj.toString());
						req.setAttribute("LoanTranObject", lntrnobj);
						if(lntrnobj != null) 
						{
							statform.setTxt_loanba(lntrnobj.getLoanBalance());
							statform.setTxt_addpaid(String.valueOf(lntrnobj.getPrincipalPaid()));
							req.setAttribute("AdvancePaidAmount", String.valueOf(lntrnobj.getPrincipalPaid()));
							statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPrincipalBalance()));
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
							req.setAttribute("TotalLoanAmount", String.valueOf(total_amt));
							/*lbl_total_amount.setText(nf.format(total_amt));
					        total_amt = obj_loanstatus.getLoanBalance()+obj_loanstatus.getPenaltyAmount()+obj_loanstatus.getInterestPayable()+obj_loanstatus.getOtherAmount()-obj_loanstatus.getExtraIntAmount(); 
					        lbl_close_amt.setText(nf.format(total_amt));*/
							double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
							System.out.println("*********************** Total Closeable amount****************" + close_amt);
							statform.setTxt_totcloamt(String.valueOf(close_amt));
							req.setAttribute("TotalClosableAmount", String.valueOf(close_amt));
						}
						System.out.println("Cid" + lmobj.getCustomerId());
						System.out.println("Mailing Address"+ lmobj.getMailingAddress());
						HashMap maps = null;
						Address address = null;
						maps = delegate.getAddress(lmobj.getCustomerId());
						for(int i=0;i<maps.size();i++){
							Collection collection=maps.values();
							Iterator iterator=collection.iterator();
							while(iterator.hasNext()){
								address=(Address)iterator.next();
							}
						}
						if(address != null) {
							System.out.println("Address object===========>"+ address.toString());
							System.out.println("Phno" + address.getPhno());
							System.out.println("Mobile" + address.getMobile());
							System.out.println("Fax" + address.getFax());
							System.out.println("Email" + address.getEmail());
							histform.setPhone(address.getPhno());
							histform.setMob(address.getMobile());
							histform.setFax(address.getFax());
							histform.setEmail(address.getEmail());
						}
						arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
						if (!arraylist.isEmpty()) {
							System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
							lasttran.setLastprincipleamt(arraylist.get(6).toString());
							lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
							lasttran.setLastintamt(arraylist.get(4).toString());
							lasttran.setLastintamttrnamt(arraylist.get(5).toString());
							lasttran.setLastpiamt(arraylist.get(3).toString());
							lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
							lasttran.setLastotheramt(arraylist.get(1).toString());
							lasttran.setLastprincipledate(arraylist.get(6).toString());
							lasttran.setLastintdate(arraylist.get(4).toString());
							lasttran.setLastpidate(arraylist.get(2).toString());
							lasttran.setLastotherdate(arraylist.get(0).toString());
						}
						arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
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

						System.out.println("NPAStage" + histform.getNpastage());
						System.out.println("Recovery Account Type====>"+ recovform.getAcctype());
						System.out.println("Recovery Account Number==>"+ recovform.getAccno());
						ArrayList<String> arraylist=new ArrayList<String>();
						SurityCoBorrower suritycb=new SurityCoBorrower();
						System.out.println("surity Details======>"+ delegate.getSurityCoBorrowerDetailsTwo(recovform.getAcctype(), recovform.getAccno()));
						req.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetailsTwo(recovform.getAcctype(), recovform.getAccno()));
						arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
						if(!arraylist.isEmpty()) 
						{
							Iterator iterator = arraylist.iterator();
							while (iterator.hasNext()) {
								String strr = (String) iterator.next();
								if(strr!=null){
									if (strr.length() > 0) {
										histform.setLastnotice(strr);
									}
								}
							}
						}
					}
				}
					else
					{
						req.setAttribute("msg","Account Number Not Found");
					}
				}else{
					req.setAttribute("msg","Enter Account Number");
				}
				if (recovform.getTrf_voucherno() != 0 && recovform.getTrf_voucherno() > 0) {
					LoanTransactionObject loanTransactionObject=null;
					LoanTransactionObject obj_transfer=null;
					try{
						System.out.println("LoanRecovery Voucher Number------------->"+recovform.getTrf_voucherno());
						    loanTransactionObject = delegate.getTransferVoucherNo(recovform.getTrf_voucherno(),delegate.getSysDate());
						    if(loanTransactionObject==null){
						        req.setAttribute("msg","Transfer Voucher No Not Found");
						        recovform.setTrf_voucherno(0);
						    }
						    else if(loanTransactionObject.uv.getVerTml() != null){
						    	req.setAttribute("msg","Transfer Vouvher No Already Verified");
						    	recovform.setTrf_voucherno(0);
						    }
						    else
						    {
								lmobj = delegate.getQueryOnLoanStatus(loanTransactionObject.getAccType(), loanTransactionObject.getAccNo(), LoansDelegate.getSysDate());
								System.out.println("************Loan Master Object***************" + lmobj.getShareAccNo());
								Object obj[][]=delegate.getLoanAndShareDetails(lmobj.getShareAccType(), lmobj.getShareAccNo());
								System.out.println("-------------Share Details------------" + obj.length);
								recovform.setAcctype(loanTransactionObject.getAccType());
								recovform.setAccno(loanTransactionObject.getAccNo());
								for(int sh=0;sh<obj.length;sh++){
									System.out.println("----loan and sh values of obj----"+obj[sh][0]);
									System.out.println("----loan and sh values of obj----"+obj[sh][1]);
									System.out.println("----loan and sh values of obj----"+obj[sh][2]);
								}
								
								if (lmobj != null) {
									req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
									req.setAttribute("panelName", "Personal");
									recovform.setName(lmobj.getName());
									recovform.setShno(lmobj.getShareAccNo());
									recovform.setShamt((Double.parseDouble(obj[0][2].toString()))*Double.parseDouble(obj[0][0].toString()));
									recovform.setShtype(obj[0][3].toString());
									recovform.setExcessshamt(lmobj.getAddShareAmount());
									//recovform.setShamt(lmobj. );
									recovform.setDisbursed_on(lmobj.getSanctionDate());
									recovform.setInstamt(lmobj.getInstallmentAmt());
									recovform.setIntrate(lmobj.getInterestRate());
									recovform.setPenrate(lmobj.getPenalRate());
									recovform.setPurpose(lmobj.getLdgPrntDate());
									recovform.setPeriod(lmobj.getLastTrnSeq());
									recovform.setLoanamt(lmobj.getSanctionedAmount());
									if (lmobj.getInterestType() == 1)
										recovform.setRepay_type("Reducing Balance");
									else
										recovform.setRepay_type("EMI");
									if (lmobj.getInterestRateType() == 1)
										recovform.setInt_rate_type("Fixed");
									else
										recovform.setInt_rate_type("Floating");
									lntrnobj = delegate.getQueryLoanStatus(recovform.getAcctype(), recovform.getAccno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDateTime());
									System.out.println("LOanTran Object"+ lntrnobj.toString());
									req.setAttribute("LoanTranObject", lntrnobj);
									if (lntrnobj != null) 
									{
										statform.setTxt_loanba(lntrnobj.getLoanBalance());
										statform.setTxt_addpaid(DoubleFormat.toString(lntrnobj.getPrincipalPaid()));
										statform.setTxt_ppoverdue(DoubleFormat.toString(lntrnobj.getPrincipalBalance()));
										System.out.println("lntrnobj.getPrincipalBalance()" + lntrnobj.getPrincipalBalance());
										statform.setTxt_current_inst(DoubleFormat.toString(lntrnobj.getPrincipalPayable()));
										statform.setTxt_interest(DoubleFormat.toString(lntrnobj.getInterestPayable()));
										statform.setTxt_pinealInt(DoubleFormat.toString(lntrnobj.getPenaltyAmount()));
										statform.setTxt_otherchar(DoubleFormat.toString(lntrnobj.getOtherAmount()));
										statform.setTxt_extra(DoubleFormat.toString(lntrnobj.getExtraIntAmount()));
										double total_amt = lntrnobj.getPrincipalBalance()+lntrnobj.getPrincipalPayable()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
										System.out.println("*************Total Amount**************" + total_amt);
										statform.setTxt_tca(DoubleFormat.toString(total_amt));
										double close_amt=lntrnobj.getLoanBalance()+lntrnobj.getPenaltyAmount()+lntrnobj.getInterestPayable()+ lntrnobj.getOtherAmount()-lntrnobj.getExtraIntAmount();
										System.out.println("*********************** Total Closeable amount****************" + close_amt);
										statform.setTxt_totcloamt(DoubleFormat.toString(close_amt));
									}
									System.out.println("Cid" + lmobj.getCustomerId());
									System.out.println("Mailing Address"+ lmobj.getMailingAddress());
									HashMap maps = null;
									maps = delegate.getAddress(lmobj.getCustomerId());
									Address address = (Address) maps.get(new Integer(lmobj.getMailingAddress()));
									System.out.println("Address object===========>"+ address.toString());
									System.out.println("Phno" + address.getPhno());
									System.out.println("Mobile" + address.getMobile());
									System.out.println("Fax" + address.getFax());
									System.out.println("Email" + address.getEmail());
									if (address != null) {
										histform.setPhone(address.getPhno());
										histform.setMob(address.getMobile());
										histform.setFax(address.getFax());
										histform.setEmail(address.getEmail());
									}

									arraylist = delegate.getLastLoanTransactionDate(recovform.getAcctype(), recovform.getAccno());
									if (!arraylist.isEmpty()) {
										System.out.println("arraylist.get(6).toString()"+ arraylist.get(6).toString());
										lasttran.setLastprincipleamt(arraylist.get(6).toString());
										lasttran.setLastprincipletrnamt(arraylist.get(7).toString());
										lasttran.setLastintamt(arraylist.get(4).toString());
										lasttran.setLastintamttrnamt(arraylist.get(5).toString());
										lasttran.setLastpiamt(arraylist.get(3).toString());
										lasttran.setLastotheramttrnamt(arraylist.get(0).toString());
										lasttran.setLastotheramt(arraylist.get(1).toString());
									}

									arraylist = delegate.getLoanNPAStatus(recovform.getAcctype(), recovform.getAccno());
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
									ArrayList surity = delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno());
									System.out.println("size==============>"+ surity.size());
									for (int i = 0; i < surity.size(); i++) {
										System.out.println("data=====>" + surity.get(i));
									}
									req.setAttribute("SurityDetails", delegate.getSurityCoBorrowerDetails(recovform.getAcctype(), recovform.getAccno()));
									arraylist = delegate.getLoanHistory(recovform.getAcctype(), recovform.getAccno());
									if (!arraylist.isEmpty()) {

										Iterator iterator = arraylist.iterator();
										while (iterator.hasNext()) {
											String strr = (String) iterator.next();
											if (strr.length() > 0) {
												histform.setLastnotice(strr);
											}
										}
									}
								}
						    	try{
						    		obj_transfer=delegate.getQueryLoanStatus(loanTransactionObject.getAccType(), loanTransactionObject.getAccNo(), loanTransactionObject.getTransactionDate(), lnuser, lntml, delegate.getSysDateTime());
						    	}catch(Exception e){
						    		e.printStackTrace();
						    	}
						        int i=0;	
						        int ac_no=0;
						        StringTokenizer st = new StringTokenizer(loanTransactionObject.getTranNarration()," ");
						        String acc_type = st.nextToken();
						        System.out.println("Amzad ****************"+acc_type+"****");
						        recovform.setTrf_from(acc_type); 
						        ac_no=Integer.parseInt((st.nextToken()));
						        recovform.setTrf_accno(ac_no);
						        AccountObject am=null;
						        try {
						        	
						            am = delegate.getAccount(null, acc_type,ac_no);
						        } catch (NumberFormatException e) {
						            e.printStackTrace();
						        } catch (RemoteException e) {
						            e.printStackTrace();
						        }  
						        recovform.setBalance(am.getAmount());
						       recovform.setAmount(loanTransactionObject.getTransactionAmount());	
						         double transfer_amount=0.0;
						    	try {
						    		transfer_amount=loanTransactionObject.getTransactionAmount();
									double temp_transfer_amount=transfer_amount;
								    if(obj_transfer != null) {	        
								        double max_req_amt = obj_transfer.getLoanBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount()-obj_transfer.getExtraIntAmount();
								        if(transfer_amount > max_req_amt) {
								            req.setAttribute("msg","More than required amount");
								            recovform.setAmount(0.0);
								        }
								        else {
								        	 recovform.setExtraint(obj_transfer.getExtraIntAmount());
								            if(transfer_amount > obj_transfer.getOtherAmount()) {
								            	recovform.setOthercharges(obj_transfer.getOtherAmount());
								                transfer_amount -= obj_transfer.getOtherAmount();
								            }
								            else {
								            	recovform.setOthercharges(transfer_amount);
								                transfer_amount = 0;
								            }
								            if(transfer_amount > obj_transfer.getPenaltyAmount()) {
								            	recovform.setPenalinterest(obj_transfer.getPenaltyAmount());
								                transfer_amount -= obj_transfer.getPenaltyAmount();
								            }
								            else {
								            	recovform.setPenalinterest(transfer_amount);
								                transfer_amount = 0;
								            }
								            if(transfer_amount > (obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount())) {
								            	recovform.setInterest(obj_transfer.getInterestPayable());
								            	System.out.println("hey Ther===========>"+(obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount()));
								            	recovform.setIntuptodate(Validations.addDays(delegate.getSysDate(), -1));
								                transfer_amount -= obj_transfer.getInterestPayable()-obj_transfer.getExtraIntAmount();
								                System.out.println("Transafer Amount=====>"+transfer_amount);
								            }
								            else {
								            	LoanTransactionObject ltrn=delegate.calculateInterestForRecovery(obj_transfer.getAccType(),obj_transfer.getAccNo(),transfer_amount);
								            	if(ltrn != null) {
								            		recovform.setInterest(transfer_amount);
								            		recovform.setIntuptodate(ltrn.getIntUptoDate());
								            	} else {
								            		recovform.setInterest(transfer_amount);
								            		recovform.setIntuptodate("");
								            	}
								                transfer_amount = 0;
								            }
								            if(obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable() > 0 ) {
								            	recovform.setPrinciple(obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable());
								                transfer_amount -= obj_transfer.getPrincipalBalance()+obj_transfer.getPrincipalPayable();  // Code changed by Murugesh on 14/03/2006
								                System.out.println("The getPrincipalBalance is:"+obj_transfer.getPrincipalBalance());
								                System.out.println(" the getPrincipalPayable is:"+obj_transfer.getPrincipalPayable());
								                System.out.println("The getPrincipalBalance is:"+obj_transfer.getInterestPayable());
								                System.out.println(" the getPrincipalPayable is:"+obj_transfer.getInterestPaid());
								                recovform.setPrinciple(transfer_amount); // Code added by Murugesh on 14/03/2006
								                 if(temp_transfer_amount>obj_transfer.getPrincipalBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount())
								                 {
								                	 recovform.setAdvance(temp_transfer_amount-(obj_transfer.getPrincipalBalance()+obj_transfer.getInterestPayable()+obj_transfer.getPenaltyAmount()+obj_transfer.getOtherAmount()));
								                 }
								            } 
								            else {
								            	recovform.setAdvance(transfer_amount);
								            	recovform.setPrinciple(0.0);
								            }  
								        }
								    }
								} catch (Exception exe) {
									exe.printStackTrace();
								}
						    }
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				if (recovform.getTrf_accno() != 0
						&& recovform.getTrf_accno() > 0) {
					System.out.println("!!!!!!!!!!!!!getting inside tranmsfer account!!!!!!!!!!!!!!!");
					int i = 0;
					moduleobject_transfer = delegate.getMainModules(2,"'1002000','1007000','1015000','1014000','1017000','1018000'");
					am = delegate.getAccount(null, recovform.getTrf_from(),recovform.getTrf_accno());
					System.out.println("account object====>===>" + am);
					if (am == null) {// Validation account not found
						System.out.println("Account not found");// recovform.setAcc_notfound("AccountNotFound");
						req.setAttribute("Valid_account", "AccountNotFound");
						recovform.setBalance(0.0);
					} else if (am.getAccStatus().equals("C")) {// Account is closed
						req.setAttribute("Valid_account", "AccountClosed");
						recovform.setBalance(0.0);
					} else if (am.getVerified() == null) {	// Account not verified
						req.setAttribute("Valid_account", "AccountNotVerified");
						recovform.setBalance(0.0);
					} else if (am.getDefaultInd().equals("T")) {// Account is blocked
						req.setAttribute("Valid_account", "AccountBlocked");
						recovform.setBalance(0.0);
					} else if (am.getFreezeInd().equals("T")) {// Account is frezed
						System.out.println("Accno freezed");
						req.setAttribute("Valid_account", "AccountFrezed");
						recovform.setBalance(0.0);
					} else {
						recovform.setBalance(am.getAmount());
						System.out.println("Name===>" + am.getAccname());
					}
					if (recovform.getButton_value() != null) {
						System.out.println("inside button value==>"+ recovform.getButton_value());
						if (recovform.getButton_value().equalsIgnoreCase("submit") || recovform.getButton_value().equalsIgnoreCase("verify")) {
							LoanTransactionObject obj_submit = new LoanTransactionObject();
							int ln = 0;
							 obj_submit.setAccType(recovform.getAcctype());
							 obj_submit.setAccNo(recovform.getAccno());
							 obj_submit.setTransactionDate(delegate.getSysDate());
							 obj_submit.setTranType(recovform.getTrf_from());
							 obj_submit.setTranSequence(recovform.getTrf_accno());
							 obj_submit.setTransactionAmount(recovform.getAmount());
							 obj_submit.setOtherAmount(recovform.getOthercharges());
							 obj_submit.setPenaltyAmount(recovform.getPenalinterest());
							 if(recovform.getInterest() > 0){
							 obj_submit.setInterestPayable(recovform.getInterest());
							 obj_submit.setExtraIntAmount(0); } else {
							 obj_submit.setInterestPayable(0);
							 obj_submit.setExtraIntAmount(recovform.getExtraint()); }
							  if(recovform.getTrf_voucherno()!=0) 
								  obj_submit.setReferenceNo(recovform.getTrf_voucherno());
							 obj_submit.uv.setUserTml(lnuser);
							 obj_submit.uv.setUserId(lntml);
							 obj_submit.uv.setUserDate(delegate.getSysDate());
							 
							 if(recovform.getButton_value().equalsIgnoreCase("submit")){
							try{
							int vch=delegate.storeLoanAmount(obj_submit, 1, delegate.getSysDate());
							if(vch>0){
								req.setAttribute("msg","Transaction Successfull..! Note Down Voucher No:  "+vch);
								recovform.setClearFl("Y");
								recovform.setName("");
								recovform.setTrf_accno(0);
								recovform.setAmount(0.0);
							}else{
								req.setAttribute("msg","Transaction is Incomplete...!");
							}
							}catch(Exception e){
								e.printStackTrace();
							}
						}else if(recovform.getButton_value().equalsIgnoreCase("verify")){
							try{
								int verifyVch=delegate.recoverLoanAmount(obj_submit);
								if(verifyVch==1){
									req.setAttribute("msg","Voucher Number Verified Successfully...!");
									recovform.setClearFl("Y");
									recovform.setTrf_accno(0);
									recovform.setAmount(0.0);
								}else{
									req.setAttribute("msg","Error During Verification...!");
								}
								}catch(Exception e){
									e.printStackTrace();
								}
						}
						}
					}
				}
				if (recovform.getAmount() != 0 && recovform.getAmount() > 0) {
					int i = 0;
					double transfer_amount = 0;
					transfer_amount = recovform.getAmount();
					System.out.println("Object!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ lmobj.toString());
					if (transfer_amount <= 0) {
                     req.setAttribute("msg", "Amount Should be greater than Zero...!");
					} else if (transfer_amount > (recovform.getBalance() - moduleobject_transfer[i].getMinBal())) {
						req.setAttribute("msg", "Insufficient Balance...!");
					} else {
						System.out.println("Before calling setPayPanel");
						setPayPanel(recovform, transfer_amount);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanDailyPostingMenu")) {
			System.out.println("inside /Loans/LoanDailyPosting" + path);
			try {
				delegate = new LoansDelegate();
				LoanDailyPostingForm postingform = (LoanDailyPostingForm) form;

				if (MenuNameReader.containsKeyScreen(postingform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(postingform.getPageidentity().getPageId());
					LoanDailyPostInitialParam(req, delegate, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanDailyPosting")) {
			try {
				int value = 0;
				AccountObject accountObject=null;
				delegate = new LoansDelegate();
				LoanDailyPostingForm postingform = (LoanDailyPostingForm) form;
				if(!postingform.getAcctype().equalsIgnoreCase("All Types") ){
					if(postingform.getAccno()!=0){
					 accountObject = delegate.getAccount(null,postingform.getAcctype(),postingform.getAccno());
					if(accountObject==null){
							req.setAttribute("msg", "Account Number Not Found!");
						}
				}
				}
				if (postingform.getProcess().equalsIgnoreCase("Process")) {
					if (postingform.getAcctype().equalsIgnoreCase("All Types")) 
					{
						value = delegate.loandailypostingforAllTypes(postingform.getDate(), lnuser, lntml);
					} else{
						
						value = delegate.loandailypostingforSpecificTypes(postingform.getAcctype(), postingform.getAccno(), postingform.getDate(),201, 1, lnuser, lntml);
						}
					}
				System.out.println("Posting" + value);
				if(value!=0){
					postingform.setResult("AccountisPosted");
				req.setAttribute("LoanPosting", value);
				}
				if (MenuNameReader.containsKeyScreen(postingform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(postingform.getPageidentity().getPageId());
					LoanDailyPostInitialParam(req, delegate, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			}// try
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		LoanTransactionObject lntrn = new LoanTransactionObject();
		if (map.getPath().trim().equalsIgnoreCase("/Loans/ReverseRecoveryMenu")) {
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
		}// if /Loans/ReverseRecoveryMenu
		else if (map.getPath().trim().equalsIgnoreCase("/Loans/ReverseRecovery")) {
			try {
				ReverseRecoveryForm reverseRecoveryForm = (ReverseRecoveryForm) form;
				System.out.println("Hi in /Loans/ReverseRecovery "+reverseRecoveryForm.getForward());
				RevRecoveryInitialParam(req, delegate, path);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
				req.setAttribute("panelName", "Personal");
				reverseRecoveryForm.setResult_update(0);
				reverseRecoveryForm.setResult_submit(0);
				reverseRecoveryForm.setResult_del(0);
				reverseRecoveryForm.setAccountclosed("null");
				reverseRecoveryForm.setAccountnotfound("null");
				if (reverseRecoveryForm.getAccno() != 0) {
					LoanMasterObject mast_obj = delegate.getLoanMaster(reverseRecoveryForm.getAccno(), reverseRecoveryForm.getAcctype());
					if (mast_obj == null) {
						req.setAttribute("msg","Account Number Not Found!");
					}
					System.out.println("------Inside ac_num!=0 Condition-------");
					req.setAttribute("personalInfo", delegate.getCustomer(delegate.getCid(reverseRecoveryForm.getAcctype(), reverseRecoveryForm.getAccno())));
					req.setAttribute("panelName", "Personal");
				}
				if(reverseRecoveryForm.getCreditgl().equalsIgnoreCase("yes")){
					req.setAttribute("checkbox", "visible");
				}else{
					req.setAttribute("checkbox", null);
					reverseRecoveryForm.setCreditgltype("");
					reverseRecoveryForm.setCreditglcode("0");
				}
				String buttonvalue = reverseRecoveryForm.getForward();
				System.out.println("buttonvalue====" + buttonvalue);
				if (reverseRecoveryForm.getForward() != null) {
					if (reverseRecoveryForm.getForward().equalsIgnoreCase("submit")|| reverseRecoveryForm.getForward().equalsIgnoreCase("Update")) {
						System.out.println("On click of submit button");
						System.out.println("Acctype"+ reverseRecoveryForm.getAcctype() + "Accno"+ reverseRecoveryForm.getAccno());
						lntrn.setAccType(reverseRecoveryForm.getAcctype());
						lntrn.setAccNo(reverseRecoveryForm.getAccno());
						lntrn.setTranMode("T");
						lntrn.setTranType("R");
						lntrn.setCdind("D");
						if(reverseRecoveryForm.getCreditgl().equalsIgnoreCase("yes")){
							lntrn.setTranNarration(reverseRecoveryForm.getCreditgltype()+" "+reverseRecoveryForm.getCreditglcode());
						}else{
							lntrn.setTranNarration(reverseRecoveryForm.getCreditgltype()+" "+reverseRecoveryForm.getCreditglcode());
						}
						lntrn.setTransactionDate(delegate.getSysDate());
						lntrn.setTranSou(lntml);
						lntrn.setPrincipalPayable(reverseRecoveryForm.getPrinamt());
						lntrn.setInterestPayable(reverseRecoveryForm.getIntamt());
						lntrn.setPenaltyAmount(reverseRecoveryForm.getPenalamt());
						lntrn.setOtherAmount(reverseRecoveryForm.getOthercharges());
						lntrn.uv.setUserDate(delegate.getSysDate());
						lntrn.uv.setUserId(lnuser);
						lntrn.uv.setUserTml(lntml);
						if (reverseRecoveryForm.getForward().equalsIgnoreCase("Submit")&& reverseRecoveryForm.getVoucherno() == 0)
							lntrn.setReferenceNo(0);
						else if (reverseRecoveryForm.getForward().equalsIgnoreCase("Update"))
							lntrn.setReferenceNo(reverseRecoveryForm.getVoucherno());
						int val = (Integer) delegate.reverserecovery(lntrn, 0,null, 0, null);
						System.out.println("value=======>" + val);
						if (reverseRecoveryForm.getForward().equalsIgnoreCase("Submit"))
							reverseRecoveryForm.setResult_submit(val);
						else if (reverseRecoveryForm.getForward().equalsIgnoreCase("Update"))
							reverseRecoveryForm.setResult_update(val);
					}// On click of Submit or Update button
					else if (reverseRecoveryForm.getForward().equalsIgnoreCase("Delete")) {
						if (reverseRecoveryForm.getVoucherno() != 0) {
							int dele_val = delegate.deleteReverseDetails(
									reverseRecoveryForm.getVoucherno(), 1);
							System.out.println("Delete value==========>"+ dele_val);
							if (dele_val != 0) {
								reverseRecoveryForm.setResult_del(1);
							}
						}
					}// onclick of Delete button
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
		if (map.getPath().trim().equalsIgnoreCase("/Loans/OtherChargesMenu")) {
			try {
				delegate = new LoansDelegate();
				OtherChargesDEForm ocform = (OtherChargesDEForm) form;
				ocform.setUpdate(0);
				ocform.setRefnoGen(0);
				ocform.setDelete(0);
				getTabbedpane_for_Othercharges(req, ocform);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				if (ocform.getTabPaneHeading() != null&& ocform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}
				if (MenuNameReader.containsKeyScreen(ocform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(ocform.getPageidentity().getPageId());
					OtherchargesInitailParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Loans/OtherCharges")) {
			try {
				OtherChargesDEForm ocForm = (OtherChargesDEForm) form;
				ocForm.setUpdate(0);
				ocForm.setRefnoGen(0);
				ocForm.setDelete(0);
				OtherchargesInitailParam(req, delegate);
				lmobj = new LoanMasterObject();
				lntrnobj = new LoanTransactionObject();
				getTabbedpane_for_Othercharges(req, ocForm);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				if (ocForm.getTabPaneHeading() != null && ocForm.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}	// Focuslost of Accno
				if (ocForm.getAcno() != 0) {
						System.out.println("In focuslost of accno in OC");
						lmobj = delegate.getLoanMastervalues(ocForm.getAcctype(), ocForm.getAcno(), delegate.getSysDate());
						if(lmobj!=null){
						System.out.println("Loanmaser obj===>" + lmobj);
						System.out.println("cid===>" + lmobj.getCustomerId());
						ocForm.setSancdate(lmobj.getSanctionDate());
						ocForm.setSancamt(lmobj.getSanctionedAmount());
						ocForm.setInstallments(lmobj.getNoOfInstallments());
						ocForm.setTranamt(lmobj.getRequiredAmount());
						ocForm.setTrandate(delegate.getSysDate());
						req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						req.setAttribute("panelName", "Personal");
						setQueryLoanStatus(delegate.getSysDate(), ocForm,delegate, lmobj, req);
						}else{
							req.setAttribute("msg","Accounr Number Not Found!");
						}
				}// focuslost of Refno
				else if (ocForm.getRefno() != 0) {
					hash_obj = delegate.getOtherChargeDetails(ocForm.getRefno());
					System.out.println("loan Transaction object====>"+ hash_obj);
					lmobj = (LoanMasterObject) hash_obj.get("LoanMaster");
					lntrnobj = (LoanTransactionObject) hash_obj.get("LoanTransaction");
					System.out.println("loan master object====>" + lmobj+ "Loan Transaction object====>" + lntrnobj);
					setQueryLoanStatus(delegate.getSysDate(), ocForm, delegate,lmobj, req);
					if (lmobj != null && lntrnobj != null) {
						ocForm.setAcctype(lmobj.getAccType());
						ocForm.setAcno(lmobj.getAccNo());
						ocForm.setSancdate(lmobj.getSanctionDate());
						ocForm.setSancamt(lmobj.getSanctionedAmount());
						ocForm.setTrandate(lntrnobj.getTransactionDate());
						req.setAttribute("Transaction_amount", String.valueOf(lntrnobj.getTransactionAmount()));
						ocForm.setInstallments(lmobj.getNoOfInstallments());
						req.setAttribute("Reason", lntrnobj.getTranNarration());
						req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						req.setAttribute("panelName", "Personal");
					}
				}
				String button_value = ocForm.getForward();
				System.out.println("Button value" + button_value);
				if (ocForm.getForward() != null) {
					if (ocForm.getForward().equalsIgnoreCase("Submit")) {
						System.out.println("ac_type====" + ocForm.getAcctype()+ "ac_num====>" + ocForm.getAcno());
						LoanTransactionObject ltrnobj = new LoanTransactionObject();
						ltrnobj.setAccNo(ocForm.getAcno());
						ltrnobj.setAccType(ocForm.getAcctype());
						ltrnobj.setCdind("D");
						ltrnobj.uv.setUserTml("Vinay");
						ltrnobj.uv.setUserId("1044");
						ltrnobj.uv.setUserDate(delegate.getSysDate());
						ltrnobj.setTransactionAmount(ocForm.getTranamt());
						ltrnobj.setTransactionDate(ocForm.getTrandate());
						ltrnobj.setTranType("O");
						ltrnobj.setTranMode("T");
						ltrnobj.setTranSou("Other Charges");
						ltrnobj.setTranSequence(lmobj.getLastTrnSeq() + 1);
						ltrnobj.setTranNarration(ocForm.getReason());
						ltrnobj.setIntUptoDate(lmobj.getInterestUpto());
						ltrnobj.setPrincipalBalance(lmobj.getInstallmentAmt());// req.setAttribute("Reference",delegate.storeOtherCharges(ltrnobj));
						int ref_num = delegate.storeOtherCharges(ltrnobj);
						ocForm.setRefnoGen(ref_num);
						System.out.println("after inserting ====>" + ref_num);
					} else if (ocForm.getForward().equalsIgnoreCase("Update")) {
						System.out.println("=====Inside Update====");
						LoanTransactionObject lto = new LoanTransactionObject();
						System.out.println("Account type====>"+ ocForm.getAcctype());
						lto.setAccNo(ocForm.getAcno());
						lto.setAccType(ocForm.getAcctype());
						lto.setCdind("D");
						lto.uv.setUserTml("Vinay");
						lto.uv.setUserId("1044");
						lto.uv.setUserDate(delegate.getSysDateTime());
						lto.setTransactionAmount(ocForm.getTranamt());
						lto.setTransactionDate(ocForm.getTrandate());
						lto.setTranType("O");
						lto.setTranMode("T");
						lto.setTranSou("Other Charges");
						lto.setTranSequence(lmobj.getLastTrnSeq() + 1);
						lto.setTranNarration(ocForm.getReason());
						lto.setIntUptoDate(lmobj.getInterestUpto());
						lto.setPrincipalBalance(lmobj.getInstallmentAmt());
						int result = delegate.getUpdateOtherCharges(lto, ocForm.getRefno());
						System.out.println("result=====>" + result);
						if (result == 1) {
							ocForm.setUpdate(result);
						} else {
							ocForm.setUpdate(result);
						}
					} else if (ocForm.getForward().equalsIgnoreCase("Delete")) {
						System.out.println("Inside Delete");
						System.out.println("ref num====>" + ocForm.getRefno()+ "acc type===>" + ocForm.getAcctype()+ "Acc no====>" + ocForm.getAcno());
						LoanTransactionObject dele_val = delegate.deleteOtherCharges(ocForm.getRefno(), ocForm.getAcctype(), ocForm.getAcno(), 1);
						if (dele_val == null) {
							ocForm.setDelete(1);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // close
		if(map.getPath().equalsIgnoreCase("/Loans/OtherChargesVerifyMenu"))
		{	
			System.out.println("************/Loans/OtherChargesVerifyMenu**********");
			try {
				delegate = new LoansDelegate();
				OtherChargesDEForm ocform = (OtherChargesDEForm) form;
				ocform.setUpdate(0);
				ocform.setRefnoGen(0);
				ocform.setDelete(0);
				getTabbedpane_for_Othercharges(req, ocform);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				if (ocform.getTabPaneHeading() != null&& ocform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}
				if (MenuNameReader.containsKeyScreen(ocform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(ocform.getPageidentity().getPageId());
					System.out.println("(((((((((((path))))))))))))" + path);
					OtherchargesInitailParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				}

			} catch (Exception e) {
				e.printStackTrace();
		}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/OtherChargesVerify"))
		{	
			System.out.println("*************/Loans/OtherChargesVerify************");
			try {
				OtherChargesDEForm ocForm = (OtherChargesDEForm) form;
				ocForm.setUpdate(0);
				ocForm.setRefnoGen(0);
				ocForm.setDelete(0);
				OtherchargesInitailParam(req, delegate);
				lmobj = new LoanMasterObject();
				lntrnobj = new LoanTransactionObject();
				getTabbedpane_for_Othercharges(req, ocForm);
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				if (ocForm.getTabPaneHeading() != null && ocForm.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					}
				System.out.println("The code should go here");
				if (ocForm.getRefno() != 0) {
					hash_obj = delegate.getOtherChargeDetails(ocForm.getRefno());
					System.out.println("loan Transaction object====>"+ hash_obj);
					lmobj = (LoanMasterObject) hash_obj.get("LoanMaster");
					lntrnobj = (LoanTransactionObject) hash_obj.get("LoanTransaction");
					System.out.println("loan master object====>" + lmobj+ "Loan Transaction object====>" + lntrnobj);
					setQueryLoanStatus(delegate.getSysDate(), ocForm, delegate,lmobj, req);
					if (lmobj != null && lntrnobj != null) {
						ocForm.setAcctype(lmobj.getAccType());
						ocForm.setAcno(lmobj.getAccNo());
						ocForm.setSancdate(lmobj.getSanctionDate());
						ocForm.setSancamt(lmobj.getSanctionedAmount());
						ocForm.setTrandate(lntrnobj.getTransactionDate());
						req.setAttribute("Transaction_amount", String.valueOf(lntrnobj.getTransactionAmount()));
						ocForm.setInstallments(lmobj.getNoOfInstallments());
						req.setAttribute("Reason", lntrnobj.getTranNarration());
						req.setAttribute("personalInfo", delegate.getCustomer(lmobj.getCustomerId()));
						req.setAttribute("panelName", "Personal");
					}
					String button_value = ocForm.getForward();
					System.out.println("Button value" + button_value);
					if (ocForm.getForward() != null) {
						if (ocForm.getForward().equalsIgnoreCase("Verify")) {//verstatus=loanremote.verifyOtherCharges(lo.getAccType(),Integer.parseInt(acno.getText()),Integer.parseInt(tseq.getText()),Double.parseDouble(trnamt.getText()),MainScreen.head.getSysDate(),MainScreen.head.getTml(),MainScreen.head.getUid(),MainScreen.head.getSysDateTime());
							int verstatus=delegate.verifyOtherCharges(lmobj.getAccType(), ocForm.getAcno(), ocForm.getRefno(), ocForm.getTranamt(), delegate.getSysDate(), lntml, lnuser, delegate.getSysDateTime()); 
							System.out.println("Verstaus"+verstatus);
							 if(verstatus==1){
							    	System.out.println("Verified! ");
							    	ocForm.setRefnoGen(verstatus);
							    }
							    else {
							    	System.out.println("Not Verified  ");
							    	ocForm.setRefnoGen(verstatus);
							    }
						}
						}
				}
				}
				catch (Exception e) 
				{
						e.printStackTrace();
				}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/ReschedulingMenu")) {
			try {
				System.out.println("hi LoansRescheduling Menu");
				Reschedulingform reshuform = (Reschedulingform) form;
				reshuform.setSysdate(LoansDelegate.getSysDate());
				reshuform.setResult_reschedule(null);// reshuform.setAmountbetween(0.0);
				LoansDelegate delegate = new LoansDelegate();
				reshuform.setAccno(0);
				reshuform.setName("");
				reshuform.setPurpose("");
				reshuform.setSbaccno("");
				reshuform.setIntrate(0.00);
				reshuform.setShno("0");
				reshuform.setDisbdate("");
				reshuform.setSanctioned_amt(0.00);
				reshuform.setNoofinst(0);
				reshuform.setInstal_amt(0.00);
				reshuform.setPeriod(0);
				reshuform.setLoan_bal(0.00);
				reshuform.setInt_upto_date("");
				reshuform.setEffective_date("");
				reshuform.setInstallments(0);
				reshuform.setAmount(0.0);
				if (MenuNameReader.containsKeyScreen(reshuform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(reshuform.getPageidentity().getPageId());
					OtherchargesInitailParam(req, delegate);
					System.out.println("After ReschedulingMenu");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else if(map.getPath().equalsIgnoreCase("/Loans/Rescheduling")) 
		{
			try 
			{
				String trndate = null, date = null;
				List list = new ArrayList();
				Vector vector=new Vector();
				vec_obj = new Vector();
				System.out.println("In /Loans/Rescheduling ");
				String string_effective_date="";
				Reschedulingform reshuform = (Reschedulingform) form;
				reshuform.setResult_reschedule(null);
				LoansDelegate delegate = new LoansDelegate();
				reshuform.setSysdate(LoansDelegate.getSysDate());
				LoanMasterObject masterobj = null;
				loanreportobj = new LoanReportObject();
				OtherchargesInitailParam(req, delegate);
				double instamt=0;
				int installments=0;
				System.out.println("After Rescheduling=====>"+ reshuform.getAccno() + "Acctype"+ reshuform.getAcctype() + "Date"+ delegate.getSysDate());
				if(reshuform.getAccno()!=0) 
				{
					System.out.println("=====");
					loanreportobj = delegate.getLoanDetails(reshuform.getAcctype(), reshuform.getAccno(), delegate.getSysDate());
					if(loanreportobj == null) 
					{
						req.setAttribute("msg","Account Not Found");
						reshuform.setAccno(0);
						reshuform.setName("");
						reshuform.setPurpose("");
						reshuform.setSbaccno("");
						reshuform.setIntrate(0.00);
						reshuform.setShno("0");
						reshuform.setDisbdate("");
						reshuform.setSanctioned_amt(0.00);
						reshuform.setNoofinst(0);
						reshuform.setInstal_amt(0.00);
						reshuform.setPeriod(0);
						reshuform.setLoan_bal(0.00);
						reshuform.setInt_upto_date("");
						reshuform.setEffective_date("");
						reshuform.setInstallments(0);
						reshuform.setAmount(0.0);
					}
					else
					{
						System.out.println("Acctype" + loanreportobj.getAccType());
						System.out.println("Acctype" + reshuform.getAcctype());
						loantran = delegate.getLoanTransaction(reshuform.getAcctype(), reshuform.getAccno(), "Schedule");
						System.out.println("loan tran======>" + loantran);
						System.out.println("Helllllloooooo");	// List list=new ArrayList();
						Map map_obj;// Object obj[] = new Object[5];
						if(loantran!= null) 
						{
							for(int i = 0;i<loantran.length;i++) 
							{
								map_obj = new TreeMap();
								map_obj.put("id", (i + 1));
								System.out.println("ID=======================>" + i);
								map_obj.put("LoanTrandate", loantran[i].getTransactionDate());
								map_obj.put("PrincipalPaid", loantran[i].getPrincipalPaid());
								map_obj.put("IntPaid", loantran[i].getInterestPaid());
								map_obj.put("TranAmt", loantran[i].getTransactionAmount());
								map_obj.put("LoanBal", loantran[i].getLoanBalance());
								list.add(map_obj);
								System.out.println("<***************list.size()*************>" + list.size());
							}

							req.setAttribute("list", list);
						}
						reshuform.setName(loanreportobj.getName());
						reshuform.setPurpose(loanreportobj.getLoanPurpose());
						reshuform.setSbaccno(loanreportobj.getSavingDet());
						reshuform.setIntrate(loanreportobj.getLoanIntRate());
						reshuform.setShno(loanreportobj.getShareDet());
						System.out.println("SB=====>"+ loanreportobj.getSavingDet());
						reshuform.setSbaccno(loanreportobj.getSavingDet());
						reshuform.setDisbdate(loanreportobj.getDisbursementDate());
						reshuform.setSanctioned_amt(loanreportobj.getSanctionedAmount());
						reshuform.setNoofinst(loanreportobj.getNoInstallments());
						reshuform.setInstal_amt(loanreportobj.getInstallmentAmount());
						reshuform.setPeriod(loanreportobj.getHolidayPeriod());
						reshuform.setLoan_bal(loanreportobj.getLoanBalance());
						reshuform.setInt_upto_date(loanreportobj.getIntUptoDate());
						System.out.println("Before calling");
				    } 
				}// Focus lost of amount
					if(reshuform.getAmount()!= 0) 
					{
						System.out.println("***********In focus lost of ballll amount**************");
						string_effective_date=reshuform.getEffective_date();
						loantran = delegate.getLoanTransaction(reshuform.getAcctype(), reshuform.getAccno(), "Schedule");
						if(reshuform.getAmount()>0)
							instamt=(reshuform.getAmount());
						if(reshuform.getNoofinst()>0)
							installments=reshuform.getNoofinst();
						int i=0;//To find from the which schedule the effective date starts
						for(;loantran != null && i<loantran.length;i++)
							if(Validations.dayCompare(loantran[i].getTransactionDate(),string_effective_date)<=0)
								break;			
						double ln_bal=0;
						String lsttrndt="";
					/**
					 * if first schedule means take disbursement amt,
					 * disbursement date as balance,lst transaction date,
					 * otherwise take previous schedules loan balance and
					 * transaction date
					 */
						if(i==0) 
						{
							ln_bal = loanreportobj.getDisburseAmount();
							lsttrndt = loanreportobj.getDisbursementDate();
							System.out.println("loan Bala--->"+ln_bal);
						} 
						else 
						{
							ln_bal = loantran[i-1].getLoanBalance();
							lsttrndt = loantran[i-1].getTransactionDate();
							System.out.println("loan Bala-else-->"+ln_bal);
							System.out.println("val of i-->"+i);
						}//int b = 0;
						if(Validations.dayCompare(string_effective_date,loantran[i].getTransactionDate())!=0)
						{
							string_effective_date = loantran[i].getTransactionDate();
						}	
						if(instamt<=0 || instamt>ln_bal)
						{		
							try
							{
								req.setAttribute("msg","Enter installment amount between 1 and "+loantran[i-1].getLoanBalance());
							}catch(Exception execp){
								req.setAttribute("msg","Enter installment correct amount");
							}
						}
						else 
						{
							System.out.println("Inside Else Before Calculation");
							Calculation calc = new Calculation();
							double inttotal=0,prntotal=0,rectotal=0;
							installments+=(loanreportobj.getNoInstallments()-i);
							System.out.println("Interest Type"+ loanreportobj.getInterestType());
							if(loanreportobj.getInterestType() == 1) 
							{
								System.out.println("AdjustRI");
								obj_list = calc.adjustReducingInterest(ln_bal,instamt,loanreportobj.getLoanIntRate(),installments,lsttrndt,string_effective_date,i+1);
							} 
							else if(loanreportobj.getInterestType() == 0) 
							{
								System.out.println("*******AdjustEMI*************");//list.clear();//v_obj.clear();
								System.out.println("LoanBal" + ln_bal + "Amt" + instamt + "IntRate" + loanreportobj.getLoanIntRate() + "Inst"+ installments);
								System.out.println("TrnDate" + lsttrndt + "Date"+ string_effective_date + "");
								obj_list = calc.adjustEMIInterest(ln_bal,instamt,loanreportobj.getLoanIntRate(),installments,lsttrndt,string_effective_date,i+1);
								System.out.println("<========AdjustEMIInterest" + obj_list.size());
								if(obj_list.size()== 0)
									req.setAttribute("msg","EMI Amount Is Less Than Interest Amount");
							}
							int n=list.size();
							for(int j=i;j<n;j++)
								list.remove(i);
							for(int j=0;j<obj_list.size();j++)
							{
								list.add(obj_list.get(j));
							}		
							for(int y=0;y<list.size();y++)  // Code added by Murugesh on 17/1/2006
							{
									Map map2 = new TreeMap();
									map2 = (TreeMap)list.get(y);
									prntotal=prntotal+Double.parseDouble(map2.get("PrincipalPaid").toString());
									inttotal=inttotal+Double.parseDouble(map2.get("IntPaid").toString());
									rectotal=rectotal+Double.parseDouble(map2.get("TranAmt").toString());
							}
							req.setAttribute("principle",prntotal);
							req.setAttribute("interst",inttotal);
							req.setAttribute("recovery",rectotal);
						System.out.println("List Size^^^^^^^^^^^^^");
						req.setAttribute("list", list);
					  }// focus lost of Amount
				    }
				System.out.println("Button value=====>"+ reshuform.getForward());
				if(reshuform.getForward()!= null && reshuform.getForward().equalsIgnoreCase("Reschedule")) 
				{
					double amt1=0;
					for(int j=0;j<obj_list.size();j++)
					{
						vec_obj.add(obj_list.get(j));
					}
					int k=0;
					Object o[]=new Object[6];
					Iterator it = vec_obj.iterator();
					while(it.hasNext())
					{
						 Map map1 = new TreeMap();
						 map1 = (Map)it.next();
						 o[0]=map1.get("id");
						 o[1]=map1.get("LoanTrandate");
						 o[2]=map1.get("PrincipalPaid");
						 o[3]=map1.get("IntPaid");
						 o[4]=map1.get("TranAmt");
						 o[5]=map1.get("LoanBal");
					}	
					if(loanreportobj.getInterestType()==0)
						amt1=Double.parseDouble(o[3].toString());
					else if(loanreportobj.getInterestType()==1)
						amt1=Double.parseDouble(o[4].toString());
					int ress = delegate.reScheduleLoan(reshuform.getAcctype(),reshuform.getAccno(),installments,amt1,vec_obj,string_effective_date);
					if(ress>=1) 
					{
						req.setAttribute("msg","Successfully Reschedule");
					} 
					else 
					{
						req.setAttribute("msg","Unable To Reschedule");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// InterestAccuredReport
		if (map.getPath().equalsIgnoreCase("/Loans/InterestAccuredReportMenu")) {
			try {
				InterestAccuredReportForm accuredform = (InterestAccuredReportForm) form;
				delegate = new LoansDelegate();
				accuredform.setSysdate(LoansDelegate.getSysDate());
				req.setAttribute("SysDate", delegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(accuredform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(accuredform.getPageidentity().getPageId());
					LoanDailyPostInitialParam(req, delegate, path);
					return map.findForward(ResultHelp.getSuccess());
				} 
				else
				return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().equalsIgnoreCase("/Loans/InterestAccuredReport")) {
			try {
				Vector vecreportobj = new Vector();
				delegate = new LoansDelegate();
				req.setAttribute("SysDate", delegate.getSysDate());
				InterestAccuredReportForm accuredform = (InterestAccuredReportForm) form;
				LoanDailyPostInitialParam(req, delegate, path);
				LoanReportObject[] loan_rep;
				accuredform.setSysdate(LoansDelegate.getSysDate());
				System.out.println("Button Clicked" + accuredform.getForward());
				if (accuredform.getForward() != null && accuredform.getForward().equalsIgnoreCase("View")) 
				{
					if (accuredform.getAcctype().equalsIgnoreCase("All Types"))
						accuredform.setAcctype("1010%");
						System.out.println("Acctype" + accuredform.getAcctype());
						vecreportobj = delegate.getIntAccuredReport(accuredform.getReportdate(), accuredform.getAcctype(),accuredform.getFrom_accno(), accuredform.getTo_accno(),accuredform.getFromintamt(), accuredform.getTointamt());
						 loan_rep = new LoanReportObject[vecreportobj.size()];
						System.out.println("LoanReportObject size-----" + vecreportobj.size());
						System.out.println("loan_rep"+loan_rep.length);
						for (int i = 0; i < loan_rep.length; i++)
						{	
							loan_rep[i]=new LoanReportObject();
							System.out.println("vector size Object--->"+ i);
							loan_rep[i] = (LoanReportObject) vecreportobj.get(i);
							System.out.println("*****Loan Report*****"+loan_rep[i].getAccNo());
						}
						req.setAttribute("IntAccruedReport", loan_rep);
				}
				if (accuredform.getForward().equalsIgnoreCase("download")) {
					System.out.println("I am in download=======");
					if (accuredform.getAcctype().equalsIgnoreCase("All Types"))
						accuredform.setAcctype("1010%");
					vecreportobj = delegate.getIntAccuredReport(accuredform.getReportdate(), accuredform.getAcctype(),accuredform.getFrom_accno(), accuredform.getTo_accno(),accuredform.getFromintamt(), accuredform.getTointamt());
					 loan_rep = new LoanReportObject[vecreportobj.size()];
						for (int i = 0; i < loan_rep.length; i++)
						{	
							loan_rep[i]=new LoanReportObject();
							System.out.println("vector size Object--->"+ i);
							loan_rep[i] = (LoanReportObject) vecreportobj.get(i);
							System.out.println("*****Loan Report*****"+loan_rep[i].getAccNo());
						}
					if (loan_rep == null) {
						accuredform.setTesting("Cannot Print");
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
						loan_rep = new LoanReportObject[vecreportobj.size()];
						System.out.println("LoanReportObject size-----" + vecreportobj.size());
						System.out.println("loan_rep"+loan_rep.length);
						for (int i = 0; i < loan_rep.length; i++)
						{	
							loan_rep[i]=new LoanReportObject();
							System.out.println("vector size Object--->"+ i);
							loan_rep[i] = (LoanReportObject) vecreportobj.get(i);
							System.out.println("*****Loan Report*****"+loan_rep[i].getAccNo());
						}
							out.print("InterestAccured Details for A/C Type: "
									+ loan_rep[0].getAccType());
						out.print("\n");
						out.print("\n");
						out.print("SrlNo");
						out.print(",");
						out.print("Ac/No");
						out.print(",");
						out.print(" Name");
						out.print(",");
						out.print("Disbursement Amount");
						out.print(",");
						out.print("IntOverDueAmt");
						out.print(",");
						out.print("InterestPaid");
						out.print(",");
						out.print("");
						out.print("\n");
						for (int k = 0; k < loan_rep.length; k++) {
							out.print(k+1);
							out.print(",");
							out.print(loan_rep[k].getAccNo());
							out.print(",");
							out.print(loan_rep[k].getName());
							out.print(",");
							out.print(loan_rep[k].getDisburseAmount());
							out.print(",");
							out.print(loan_rep[k].getIntOverDueAmt());
							out.print(",");
							out.print(loan_rep[k].getInterestPaid());
							out.print(",");
							out.print(",");
							out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
						return null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/OpenClosedStatMenu")) {
			try {
				OpenClosedStat openclosedform = (OpenClosedStat) form;
				delegate = new LoansDelegate();
				openclosedform.setFromdate("");
				openclosedform.setTodate("");
				openclosedform.setSysdate(LoansDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(openclosedform.getPageidentity().getPageId())) {
					System.out.println("In MenuReader");
					path = MenuNameReader.getScreenProperties(openclosedform.getPageidentity().getPageId());
					LoanDailyPostInitialParam(req, delegate, path);
					System.out.println("after initialParam");
					return map.findForward(ResultHelp.getSuccess());
				} else
					return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().equalsIgnoreCase("/Loans/OpenClosedStat")) {
			try {
				delegate = new LoansDelegate();
				OpenClosedStat opencloseform = (OpenClosedStat) form;
				opencloseform.setSysdate(LoansDelegate.getSysDate());
				LoanReportObject lnrep[] = null;
				OtherchargesInitailParam(req, delegate);
				if (opencloseform.getForward() != null && opencloseform.getForward().equalsIgnoreCase("View")) {
					System.out.println("opencloseform.getAcctype()==============>"+ opencloseform.getAcctype());
					if (opencloseform.getSelect().equalsIgnoreCase("Open")) {
						System.out.println("**************Inside testing****************");
						lnrep = delegate.getLoanDetails(opencloseform.getAcctype(), 0, opencloseform.getFromdate(),opencloseform.getTodate(), 3, null);
						System.out.println("Report obj===>" + lnrep);
					} else
						lnrep = delegate.getLoanDetails(opencloseform.getAcctype(), 0, opencloseform.getFromdate(),opencloseform.getTodate(), 4, null);
				}
				if (opencloseform.getForward().equalsIgnoreCase("download")) {
					System.out.println("I am in download=======");
					if (opencloseform.getSelect().equalsIgnoreCase("Open")) {
						System.out.println("**************Inside testing****************");
						lnrep = delegate.getLoanDetails(opencloseform.getAcctype(), 0, opencloseform.getFromdate(),opencloseform.getTodate(), 3, null);
						System.out.println("Report obj===>" + lnrep);
					} else{
						lnrep = delegate.getLoanDetails(opencloseform.getAcctype(), 0, opencloseform.getFromdate(),opencloseform.getTodate(), 4, null);
					}
					if (lnrep == null) {
						opencloseform.setTesting("Cannot Print");
					} else {
						res.setHeader("Content-disposition","attachment;filename=Open-ClosedStatusReport.csv");
						java.io.PrintWriter out = res.getWriter();
						out.print("\n");
						out.print("\n");
						out.print("\n");
						out.print(",");
						out.print(",");
						out.print(",");
							out.print("Open-ClosedStatus Details for A/C Type: "
									+ lnrep[0].getAccType());
							out.print("\n");
							out.print("\n");
							out.print("SrlNo");
							out.print(",");
							out.print("AccNum");
							out.print(",");
							out.print("ApplicationSrlNum");
							out.print(",");
							out.print("ApplicationDate");
							out.print(",");
							out.print("Required Amount");
							out.print(",");
							out.print("SanctionDate");
							out.print(",");
							out.print("SanctionAmount");
							out.print(",");
							out.print("NoInstallments");
							out.print(",");
							out.print("InstallmentAmt");
							out.print(",");
							out.print("");
							out.print("\n");
							for (int k = 0; k < lnrep.length; k++) {
								out.print(k+1);
								out.print(",");
								out.print(lnrep[k].getAccNo());
								out.print(",");
								out.print(lnrep[k].getApplicationSrlNo());
								out.print(",");
								out.print(lnrep[k].getApplicationDate());
								out.print(",");
								out.print(lnrep[k].getRequiredAmount());
								out.print(",");
								out.print(lnrep[k].getSanctionDate());
								out.print(",");
								out.print(lnrep[k].getSanctionedAmount());
								out.print(",");
								out.print(lnrep[k].getNoInstallments());
								out.print(",");
								out.print(lnrep[k].getInstallmentAmount());
								out.print(",");
								out.print(",");
								out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
						return null;
					}
				}
				System.out.println("After cmg from delegate");
				req.setAttribute("OpenClosed", lnrep);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/OCReportMenu")) {
			try {
				OtherChargesReport ocreport = (OtherChargesReport) form;
				delegate = new LoansDelegate();
				if (MenuNameReader.containsKeyScreen(ocreport.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(ocreport.getPageidentity().getPageId());
					OCReportInitialParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				} else
					return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else if(map.getPath().equalsIgnoreCase("/Loans/OCReport")) 
		{
			try 
			{
				delegate = new LoansDelegate();
				String button_value = null, date = null;
				LoanTransactionObject[][] loantran=null,loantran1=null;
				OtherChargesReport ocreport = (OtherChargesReport) form;
				OCReportInitialParam(req, delegate);
				button_value = ocreport.getForward();
				System.out.println("Button===>" + button_value);
				ModuleObject lnmod[]=delegate.getMainModules(2,"1010000");
				ArrayList arraylist=new ArrayList();
				if(ocreport.getForward()!= null) 
				{
					System.out.println("************After get forward not null***********");
					if(ocreport.getForward().equalsIgnoreCase("View")) 
					{
						System.out.println("************Inside view ****************** ");
						if(ocreport.getTodate().length() > 0)
							date = ocreport.getTodate();
						else
							date = LoansDelegate.getSysDate();
						if(ocreport.getCombo_othercharges().equalsIgnoreCase("OverDue")) 
						{	
							if(ocreport.getAcctype().equalsIgnoreCase("All Types"))
							{
								System.out.println("In the all types Overdue");
								for(int k=0;k<lnmod.length;k++)
								{   
									System.out.println("Length of the loan Module" + lnmod.length);
									if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
									{
										System.out.println("OOOOOOOOOOOOOOOOOOOOOALL111");
										loan_trn=delegate.getOtherChargesDue(lnmod[k].getModuleCode(),ocreport.getFrom_acno(),ocreport.getTo_acno(),1,date);
									}
									else
									{	
										 if(ocreport.getFrom_acno() > 0)
										 {
											 System.out.println("OOOOOOOOOOOOOOALL2222");
											 loan_trn = delegate.getOtherChargesDue(lnmod[k].getModuleCode(),ocreport.getFrom_acno(),ocreport.getFrom_acno(),1,date);
										 }
										 else
										 {	
											 System.out.println("OOOOOOOOOOOOOOOOOALL33333");
											 loan_trn=delegate.getOtherChargesDue(lnmod[k].getModuleCode(),0,0,2,date);
										 }
									}
									Object data[][]=null;
									if(loan_trn!=null)
									{
										System.out.println("333333");
										for(int i=0;i<loan_trn.length;i++)
										{
											System.out.println("4444444 the value of i is"+i);
											if(loan_trn[i]!=null)
											{
												data=new Object[3][6];
												data[0][0]=""+(i+1)+"";
												String acc_type=null;
												for(int j=0;j<lnmod.length;j++)
												{
													if(lnmod[j].getModuleCode().equals(loan_trn[i][0].getAccType()))
														acc_type=lnmod[j].getModuleAbbrv();
												}
												data[0][1]=acc_type;
												data[0][2]=""+loan_trn[i][0].getAccNo();
												data[0][3]=""+loan_trn[i][0].getName();
												double total=0;
												for(int j=0;j<loan_trn[i].length;j++)
												{
													data[0][4]=""+loan_trn[i][j].getTranNarration();
													data[0][5]=""+loan_trn[i][j].getOtherAmount();
													total+=loan_trn[i][j].getOtherAmount();
													data[1][0]="";//dtm.addRow(data);
													data[1][1]="";
													data[1][2]="";
													data[1][3]="";
												}
												data[1][4]="Total";
												data[1][5]=""+total;	//dtm.addRow(data);
												data[2][0]="";
												data[2][1]="";
												data[2][2]="";
												data[2][3]="";
												data[2][4]="";
												data[2][5]="";	//dtm.addRow(data);
												arraylist.add(data);
											}
										}
										req.setAttribute("OCReportOver", arraylist);
									}
								 } 
							}
							else
							{
								if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
								{
									System.out.println("OOOOOOOOOOOOOActionOOOOOOOSingle1111111111");
									loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), ocreport.getFrom_acno(),ocreport.getTo_acno(), 1, date);
									if(loan_trn!=null)
									{
										System.out.println("**********Tran1******ssss111222****" + loan_trn.length);
									}
								}
								else
								{	
									if(ocreport.getFrom_acno() > 0)
									{
										System.out.println("OOOOOOOOOOOOOOOOOOOOSingle222222222222");
										loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), ocreport.getFrom_acno(),ocreport.getFrom_acno(), 1, date);
									}
									else
									{	
										System.out.println("OOOOOOOOOOOOOOOOOOOOSingle3333333333333");
										loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), 0, 0, 1, date);
									}
								}	
								Object data[][]=null;
								if(loan_trn!=null)
								{
									System.out.println("loan_trn===============>>>>"+loan_trn.length);
									for(int i=0;i<loan_trn.length;i++)
									{
										System.out.println("4444444 the 2222 value of i is"+i);
										if(loan_trn[i]!=null)
										{
											data=new Object[3][6];
											data[0][0]=""+(i+1)+"";
											String acc_type=null;
											for(int j=0;j<lnmod.length;j++)
											{
												if(lnmod[j].getModuleCode().equals(loan_trn[i][0].getAccType()))
													acc_type=lnmod[j].getModuleAbbrv();
											}
											data[0][1]=acc_type;
											data[0][2]=""+loan_trn[i][0].getAccNo();
											data[0][3]=""+loan_trn[i][0].getName();
											double total=0;
											for(int j=0;j<loan_trn[i].length;j++)
											{
												data[0][4]=""+loan_trn[i][j].getTranNarration();
												data[0][5]=""+loan_trn[i][j].getOtherAmount();
												total+=loan_trn[i][j].getOtherAmount();
												data[1][0]="";//dtm.addRow(data);
												data[1][1]="";
												data[1][2]="";
												data[1][3]="";
											}
											data[1][4]="Total";
											data[1][5]=""+total;//dtm.addRow(data);
											data[2][0]="";
											data[2][1]="";
											data[2][2]="";
											data[2][3]="";
											data[2][4]="";
											data[2][5]="";//dtm.addRow(data);
											arraylist.add(data);
										}
									}
									System.out.println("arraylist======>>>"+arraylist.size());
									req.setAttribute("OCReportOver", arraylist);
								}else{
									req.setAttribute("msg","Records11 Not Found");
								}
							}
						}
						if(ocreport.getCombo_othercharges().equalsIgnoreCase("Collected"))
						{	
							System.out.println("************ In the Collected ***************");
							if (ocreport.getAcctype().equalsIgnoreCase("All Types")) 
							{
								if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
								{
									System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCALL111111111");
									loan_trn = delegate.getOtherChargesCollected(null, ocreport.getFromdate(), ocreport.getTodate(), 1, 1, 1);
								}
							}
							else 
							{
								if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
								{
									System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCSingle1111111");
									loan_trn = delegate.getOtherChargesCollected(ocreport.getAcctype(), ocreport.getFromdate(), ocreport.getTodate(), ocreport.getFrom_acno(), ocreport.getTo_acno(), 2);
								}
								else
								{
									System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCSingle333333333");
									loan_trn = delegate.getOtherChargesCollected(ocreport.getAcctype(), ocreport.getFromdate(), ocreport.getTodate(), 1, 1, 3);
								}
							}
							Object data[][]=null;
							if(loan_trn!=null)
							{
								for(int i=0;i<loan_trn.length;i++)
								{
									credit=0;
									debit=0;
									balance=0;
									data= new Object[4][9];
									data[0][0]=""+(i+1)+"";
									data[0][1]=""+loan_trn[i][0].getAccType();
									data[0][2]=""+loan_trn[i][0].getAccNo();
									data[0][3]="";
									data[0][4]="";
									data[0][5]="";
									if(loan_trn[i][0].getCdind().equals("Debit"))/*data[6]=""+loan_trn[i][0].getLoanBalance();
										balance=loan_trn[i][0].getLoanBalance();
									}
									else/*data[6]="";
										data[7]=""+loan_trn[i][0].getLoanBalance();*/{
										credit=loan_trn[i][0].getLoanBalance();
									}
									data[0][8]=""+loan_trn[i][0].getLoanBalance();//dtm.addRow(data);
									for(int j=0;j<loan_trn[i].length;j++)
									{
										data[1][0]="";
										data[1][1]="";
										data[1][2]="";
										data[1][3]="";
										data[1][4]=loan_trn[i][j].getTransactionDate();
										data[1][5]=loan_trn[i][j].getTranNarration();
										if(loan_trn[i][j].getTranType().equals("O"))
										{
											data[1][6]=""+loan_trn[i][j].getOtherAmount();
											data[1][7]="";
											balance+=loan_trn[i][j].getOtherAmount();
										}
										if(loan_trn[i][j].getTranType().equals("R"))
										{
											data[1][6]="";
											data[1][7]=""+loan_trn[i][j].getOtherAmount();
											balance-=loan_trn[i][j].getOtherAmount();
										}
										data[1][8]=""+balance;//dtm.addRow(data);
									}
									data[2][0]="";
									data[2][1]="";
									data[2][2]="";
									data[2][3]="";
									data[2][4]="";
									data[2][5]="";
									if(debit>credit)
									{
										data[2][6]=""+(debit-credit);
										data[2][7]="";
									}
									else
									{
										data[2][6]="";
										data[2][7]=""+(credit-debit);
									}
									data[2][8]="";
									data[3][0]="";
									data[3][1]="";
									data[3][2]="";
									data[3][3]="";
									data[3][4]="";
									data[3][5]="";
									data[3][6]="";
									data[3][7]="";
									data[3][8]="";//dtm.addRow(data);
									arraylist.add(data);
								}
								System.out.println("arraylist===collected=====>>>"+arraylist.size());
								req.setAttribute("OCReportCollection", arraylist);
							}else{
								req.setAttribute("msg", "Records Not Found");
							}
						}  	
					  }
					if (ocreport.getForward().equalsIgnoreCase("download")) {
						System.out.println("I am in download=======");
						if(ocreport.getTodate().length() > 0)
							date = ocreport.getTodate();
						else
							date = LoansDelegate.getSysDate();
						if(ocreport.getCombo_othercharges().equalsIgnoreCase("OverDue")) 
						{	
							if(ocreport.getAcctype().equalsIgnoreCase("All Types"))
							{
								System.out.println("In the all types Overdue");
								for(int k=0;k<lnmod.length;k++)
								{   
									System.out.println("Length of the loan Module" + lnmod.length);
									if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
									{
										System.out.println("OOOOOOOOOOOOOOOOOOOOOALL111");
										loan_trn=delegate.getOtherChargesDue(lnmod[k].getModuleCode(),ocreport.getFrom_acno(),ocreport.getTo_acno(),1,date);
									}
									else
									{	
										 if(ocreport.getFrom_acno() > 0)
										 {
											 System.out.println("OOOOOOOOOOOOOOALL2222");
											 loan_trn = delegate.getOtherChargesDue(lnmod[k].getModuleCode(),ocreport.getFrom_acno(),ocreport.getFrom_acno(),1,date);
										 }
										 else
										 {	
											 System.out.println("OOOOOOOOOOOOOOOOOALL33333");
											 loan_trn=delegate.getOtherChargesDue(lnmod[k].getModuleCode(),0,0,2,date);
										 }
									}
									if (loan_trn== null) {
										ocreport.setTesting("Cannot Print");
									} else {
												System.out.println("loan_trn===============>>>>"+loan_trn.length);
												for(int i=0;i<loan_trn.length;i++)
												{
													System.out.println("4444444 the 2222 value of i is"+i);
													if(loan_trn[i]!=null)
													{
														res.setContentType("application/.csv");
														res.setHeader("Content-disposition","attachment;filename=InterestAccruedReport.csv");
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
														out.print("AcType");
														out.print(",");
														out.print("Ac/No");
														out.print(",");
														out.print(" Name");
														out.print(",");
														out.print("Particulates/Narration/Ref.No");
														out.print(",");
														out.print("OtherChargeAmt");
														out.print(",");
													
														out.print("\n");
														for (int k1 = 0; k1 < loan_trn.length; k1++) {
															out.print(k1+1);
															out.print(",");
															out.print(loan_trn[i][0].getAccType());
															out.print(",");
														/*	out.print(loan_trn[i][0].getAccNo());
															out.print(",");
															out.print(",");
															out.print(",");
															out.print(",");
															out.print(",");
															out.print(",");
															out.print(",");
															*/
															/*data[0][0]=""+(i+1)+"";
															data[0][1]=""+loan_trn[i][0].getAccType();
															data[0][2]=""+loan_trn[i][0].getAccNo();
															data[0][3]="";
															data[0][4]="";
															data[0][5]="";*/
															String acc_type=null;
															for(int j=0;j<lnmod.length;j++)
															{
																if(lnmod[j].getModuleCode().equals(loan_trn[i][0].getAccType()))
																	acc_type=lnmod[j].getModuleAbbrv();
																
															}
															//data[0][1]=acc_type;
															out.print(acc_type);
															out.print(",");
															out.print(loan_trn[0][0].getAccNo());
															out.print(",");
															out.print(loan_trn[0][0].getName());
															out.print(",");
															/*data[0][2]=""+loan_trn[i][0].getAccNo();
															data[0][3]=""+loan_trn[i][0].getName();*/
															double total=0;
															for(int j=0;j<loan_trn[i].length;j++)
															{
																out.print(loan_trn[0][0].getTranNarration());
																out.print(",");
																out.print(loan_trn[0][0].getOtherAmount());
																out.print(",");
															/*	data[0][4]=""+loan_trn[i][j].getTranNarration();
																data[0][5]=""+loan_trn[i][j].getOtherAmount();
																total+=loan_trn[i][j].getOtherAmount();*/
																/*data[1][0]="";//dtm.addRow(data);
																data[1][1]="";
																data[1][2]="";
																data[1][3]="";*/
															}
															/*data[1][4]="Total";
															data[1][5]=""+total;//dtm.addRow(data);
															data[2][0]="";
															data[2][1]="";
															data[2][2]="";
															data[2][3]="";
															data[2][4]="";
															data[2][5]="";//dtm.addRow(data);
	*/														
															
														}
														req.setAttribute("msg","Saved to excel file in C:");
														return null;
														
													}
												}
												
									
									}
								 } 
							}
							else
							{
								if(ocreport.getTo_acno() > 0 && ocreport.getFrom_acno() > 0)
								{
									System.out.println("OOOOOOOOOOOOOActionOOOOOOOSingle1111111111");
									loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), ocreport.getFrom_acno(),ocreport.getTo_acno(), 1, date);
									if(loan_trn!=null)
									{
										System.out.println("**********Tran1******ssss111222****" + loan_trn.length);
									}
								}
								else
								{	
									if(ocreport.getFrom_acno() > 0)
									{
										System.out.println("OOOOOOOOOOOOOOOOOOOOSingle222222222222");
										loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), ocreport.getFrom_acno(),ocreport.getFrom_acno(), 1, date);
									}
									else
									{	
										System.out.println("OOOOOOOOOOOOOOOOOOOOSingle3333333333333");
										loan_trn = delegate.getOtherChargesDue(ocreport.getAcctype(), 0, 0, 1, date);
									}
								}
								
								if (loan_trn== null) {
									ocreport.setTesting("Cannot Print");
								} else {
			
							
											System.out.println("loan_trn===============>>>>"+loan_trn.length);
											for(int i=0;i<loan_trn.length;i++)
											{
												System.out.println("4444444 the 2222 value of i is"+i);
												if(loan_trn[i]!=null)
												{
													
													res.setContentType("application/.csv");
													res.setHeader("Content-disposition","attachment;filename=InterestAccruedReport.csv");
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
													out.print("Disbursement Amount");
													out.print(",");
													out.print("IntOverDueAmt");
													out.print(",");
													out.print("InterestPaid");
													out.print(",");
													out.print("");
													out.print("\n");
													for (int k = 0; k < loan_trn.length; k++) {
														
														
														out.print(",");
														out.print(k+1);
														out.print(loan_trn[i][0].getAccType());
														out.print(",");
														out.print(loan_trn[i][1].getAccNo());
														out.print(",");
													/*	out.print(loan_trn[i][2].get);
														out.print(",");
														out.print(loan_trn[k]);
														out.print(",");
														out.print(loan_trn[k]);
														out.print(",");
														out.print(",");
														out.print("\n");
														String acc_type=null;*/
														for(int j=0;j<lnmod.length;j++)
														{
															//if(lnmod[j].getModuleCode().equals(loan_trn[i][0].getAccType()))
																//acc_type=lnmod[j].getModuleAbbrv();
															out.print(lnmod[j].getModuleAbbrv());
															out.print(",");
														}
														//data[0][1]=acc_type;
														out.print(loan_trn[i][0].getAccNo());
														out.print(",");
														out.print(loan_trn[i][0].getName());
														out.print(",");
														/*data[0][2]=""+loan_trn[i][0].getAccNo();
														data[0][3]=""+loan_trn[i][0].getName();*/
														double total=0;
														for(int j=0;j<loan_trn[i].length;j++)
														{
															out.print(loan_trn[i][j].getTranNarration());
															out.print(",");
															out.print(loan_trn[i][j].getOtherAmount());
															out.print(",");
														/*	data[0][4]=""+loan_trn[i][j].getTranNarration();
															data[0][5]=""+loan_trn[i][j].getOtherAmount();
															total+=loan_trn[i][j].getOtherAmount();*/
															/*data[1][0]="";//dtm.addRow(data);
															data[1][1]="";
															data[1][2]="";
															data[1][3]="";*/
														}
														/*data[1][4]="Total";
														data[1][5]=""+total;//dtm.addRow(data);
														data[2][0]="";
														data[2][1]="";
														data[2][2]="";
														data[2][3]="";
														data[2][4]="";
														data[2][5]="";//dtm.addRow(data);
*/														
														
													}
													req.setAttribute("msg","Saved to excel file in C:");
													return null;
													
												}
											}
											
								
								}
								
							
							}
						}
				
					}
				
				    }
				}  
				catch (Exception e) 
				{
					e.printStackTrace();
				}
	}
		if (map.getPath().equalsIgnoreCase("/Loans/ODReportMenu")) {
			try {
				ODReport odreport = (ODReport) form;
				delegate = new LoansDelegate();
				odreport.setSysdate(LoansDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(odreport.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(odreport.getPageidentity().getPageId());
					OCReportInitialParam(req, delegate);
					return map.findForward(ResultHelp.getSuccess());
				} else
					return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().equalsIgnoreCase("/Loans/ODReport")) {
			ODReport odreport = (ODReport) form;
			odreport.setSysdate(LoansDelegate.getSysDate());
			String button_value = null, ac_type = null, stage_type = null;
			String report_date = null, int_upto_date = null;
			int from_ac_no = 0, to_ac_no = 0;
			double prn_from_amt = 0.0, prn_to_amt = 0.0, int_from_amt = 0.0, int_to_amt = 0.0;
			double sanction_total = 0.0, inst_amt = 0.0, loan_balance = 0.0, prn_total = 0.0, int_total = 0.0, penal_total = 0.0, other_total = 0.0, total_total = 0.0;
			try {
				delegate = new LoansDelegate();
				if (MenuNameReader.containsKeyScreen(odreport.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(odreport.getPageidentity().getPageId());
					button_value = odreport.getForward();
					System.out.println("Button===>" + button_value);
					ArrayList arraylist=new ArrayList();
					if (odreport.getForward() != null) {
						if (button_value.equalsIgnoreCase("View")) {
							if (odreport.getAcctype().equalsIgnoreCase("All Types"))
								ac_type = "1010%";
							else
								ac_type = odreport.getAcctype();
							if (odreport.getStagetype().equalsIgnoreCase("All"))
								stage_type = "%";
							else
								stage_type = odreport.getStagetype();
							report_date = odreport.getReportdate();
							int_upto_date = odreport.getIntuptodate();
							from_ac_no = odreport.getFrom_accno();
							to_ac_no = odreport.getTo_accno();
							prn_from_amt = odreport.getPrn_fromamt();
							prn_to_amt = odreport.getPrn_toamt();
							int_from_amt = odreport.getInt_fromamt();
							int_to_amt = odreport.getInto_toamt();
							Vector loan_rep= delegate.getLoanOverdueReport(ac_type, stage_type, report_date,int_upto_date, from_ac_no, to_ac_no,prn_from_amt, prn_to_amt, int_from_amt,int_to_amt);
							LoanReportObject loanreportobj=null;
							ArrayList list = new ArrayList();
							if(loan_rep!=null)
							{
							for(int i=0;i<loan_rep.size();i++)
							{
								loanreportobj = (LoanReportObject) loan_rep.get(i);
								Object obj[] = new Object[22];
								obj[0] = loanreportobj.getAccNo();
								obj[1] = loanreportobj.getName();
								obj[2] = loanreportobj.getShareType();
								obj[3] = loanreportobj.getShareNo();
								obj[4] = loanreportobj.getShareAmt();
								obj[5] = loanreportobj.getSanctionDate();
								obj[6] = loanreportobj.getSanctionedAmount();
								sanction_total += loanreportobj.getSanctionedAmount();
								obj[7] = loanreportobj.getAccNo();
								if (loanreportobj.getInterestType() == 0)
									obj[8] = " EMI";
								else
									obj[8] = " RB";
								obj[9] = loanreportobj.getNoInstallments();
								obj[10] = loanreportobj.getInstallmentAmount();
								inst_amt += loanreportobj.getInstallmentAmount();
								obj[11] = loanreportobj.getIntUptoDate();
								obj[12] = loanreportobj.getTransactiondate();
								obj[13] = loanreportobj.getLoanBalance();
								loan_balance += loanreportobj.getLoanBalance();
								obj[14] = loanreportobj.getRemainderNo();
								obj[15] = loanreportobj.getRemainderDesc();
								obj[16] = loanreportobj.getPrnOverDueAmt();
								prn_total += loanreportobj.getPrnOverDueAmt();
								obj[17] = loanreportobj.getIntOverDueAmt();
								int_total += loanreportobj.getIntOverDueAmt();
								obj[18] = loanreportobj.getPenalInterest();
								penal_total += loanreportobj.getPenalInterest();
								obj[19] = loanreportobj.getOtherCharges();
								other_total += loanreportobj.getOtherCharges();
								obj[20] = loanreportobj.getPrnOverDueAmt()+ loanreportobj.getIntOverDueAmt()+ loanreportobj.getPenalInterest()+ loanreportobj.getOtherCharges();
								total_total += (loanreportobj.getPrnOverDueAmt()+ loanreportobj.getIntOverDueAmt()+ loanreportobj.getPenalInterest() + loanreportobj.getOtherCharges());
								obj[21] = Integer.valueOf(String.valueOf(loanreportobj.getDefaultedMths()));
								list.add(obj);
							}
								req.setAttribute("OverdueReport",list);
						  }else{
							  req.setAttribute("msg","Records Not Found");
						  }
						}	
						if (odreport.getForward().equalsIgnoreCase("download")) {
							System.out.println("I am in download=======");
							if (odreport.getAcctype().equalsIgnoreCase("All Types"))
								ac_type = "1010%";
							else
								ac_type = odreport.getAcctype();
							if (odreport.getStagetype().equalsIgnoreCase("All"))
								stage_type = "%";
							else
								stage_type = odreport.getStagetype();
							report_date = odreport.getReportdate();
							int_upto_date = odreport.getIntuptodate();
							from_ac_no = odreport.getFrom_accno();
							to_ac_no = odreport.getTo_accno();
							prn_from_amt = odreport.getPrn_fromamt();
							prn_to_amt = odreport.getPrn_toamt();
							int_from_amt = odreport.getInt_fromamt();
							int_to_amt = odreport.getInto_toamt();
							Vector loan_rep= delegate.getLoanOverdueReport(ac_type, stage_type, report_date,int_upto_date, from_ac_no, to_ac_no,prn_from_amt, prn_to_amt, int_from_amt,int_to_amt);
							LoanReportObject loanreportobj=null;
							Enumeration enumerate=loan_rep.elements();
							int k=1;
							while(enumerate.hasMoreElements()){
								loanreportobj=(LoanReportObject)enumerate.nextElement();
							ArrayList list = new ArrayList();
							if(loanreportobj!=null)
							{
								odreport.setTesting("Cannot Print");
							} else {
								res.setContentType("application/.csv");
								res.setHeader("Content-disposition","attachment;filename=OverDueReport.csv");
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
									out.print("AccNum");
									out.print(",");
									out.print("NameOfBorrower");
									out.print(",");
									out.print("ShareType");
									out.print(",");
									out.print("ShareNumber");
									out.print(",");
									out.print("Amount");
									out.print(",");
									out.print("Date");
									out.print(",");
									out.print("Amount");
									out.print(",");
									out.print("AccNumber");
									out.print(",");
									out.print("IntType");
									out.print(",");
									out.print("NoInst");
									out.print(",");
									out.print("IntAmt");
									out.print(",");
									out.print("UptoDate");
									out.print(",");
									out.print("Date");
									out.print(",");
									out.print("OutStanding");
									out.print(",");
									out.print("No");
									out.print(",");
									out.print("LastDate");
									out.print(",");
									out.print("Principal");
									out.print(",");
									out.print("Int");
									out.print(",");
									out.print("PenalInt");
									out.print(",");
									out.print("OtherCharges");
									out.print(",");
									out.print("Total");
									out.print(",");
									out.print("Months");
									out.print(",");
									out.print("");
									out.print("\n");
									out.print(k);
									out.print(",");
									out.print(loanreportobj.getAccNo());
									out.print(",");
									out.print(loanreportobj.getName());
									out.print(",");
									out.print(loanreportobj.getShareType());
									out.print(",");
									out.print(loanreportobj.getShareNo());
									out.print(",");
									out.print(loanreportobj.getShareAmt());
									out.print(",");
									out.print(loanreportobj.getSanctionDate());
									out.print(",");
									out.print(loanreportobj.getSanctionedAmount());
									out.print(",");
									sanction_total += loanreportobj.getSanctionedAmount();
									out.print(loanreportobj.getAccNo());
									out.print(",");
									if (loanreportobj.getInterestType() == 0)
										out.print("EMI");
									else
										out.print("RB");
									out.print(",");
									out.print(loanreportobj.getNoInstallments());
									out.print(",");
									out.print(loanreportobj.getInstallmentAmount());
									inst_amt += loanreportobj.getInstallmentAmount();
									out.print(",");
									out.print(loanreportobj.getIntUptoDate());
									out.print(",");
									out.print(loanreportobj.getTransactiondate());
									out.print(",");
									out.print(loanreportobj.getLoanBalance());
									loan_balance += loanreportobj.getLoanBalance();
									out.print(",");
									out.print(loanreportobj.getRemainderNo());
									out.print(",");
									out.print(loanreportobj.getRemainderDesc());
									out.print(",");
									out.print(loanreportobj.getPrnOverDueAmt());
									prn_total += loanreportobj.getPrnOverDueAmt();
									out.print(",");
									out.print(loanreportobj.getIntOverDueAmt());
									int_total += loanreportobj.getIntOverDueAmt();
									out.print(",");
									out.print(loanreportobj.getPenalInterest());
									penal_total += loanreportobj.getPenalInterest();
									out.print(",");
									out.print(loanreportobj.getOtherCharges());
									other_total += loanreportobj.getOtherCharges();
									out.print(",");
									out.print(loanreportobj.getPrnOverDueAmt()+ loanreportobj.getIntOverDueAmt()+ loanreportobj.getPenalInterest()+ loanreportobj.getOtherCharges());
									total_total += (loanreportobj.getPrnOverDueAmt()+ loanreportobj.getIntOverDueAmt()+ loanreportobj.getPenalInterest() + loanreportobj.getOtherCharges());
									out.print(",");
									out.print(Integer.valueOf(String.valueOf(loanreportobj.getDefaultedMths())));
									out.print(",");
									out.print(",");
									out.print("\n");
									k++;
								req.setAttribute("msg","Saved to excel file in C:");
								return null;
							}
							}
						}
					}
				}
				path = MenuNameReader.getScreenProperties(odreport.getPageidentity().getPageId());
				LoanDailyPostInitialParam(req, delegate, path);
				return map.findForward(ResultHelp.getSuccess());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/NPALoanSummaryMenu")) {
			try {
				NPASummaryForm npaform = (NPASummaryForm) form;
				delegate = new LoansDelegate();
				if (MenuNameReader.containsKeyScreen(npaform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(npaform.getPageidentity().getPageId());
					button_value = npaform.getForward();
					System.out.println("Button===>" + button_value);
					if (npaform.getForward() != null) {
						if (npaform.getForward().equalsIgnoreCase("View")) {
							int days = 180;
							if (npaform.isDays_180()) {
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/PenalIntFixMenu")) {
			PenalActionForm penalform = (PenalActionForm) form;
			try {
				LoansDelegate loandelegate = new LoansDelegate();
				req.setAttribute("TabNum", "1");
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				getTabbedpane_for_OtherchargesTwo(req, penalform);	//req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				if (penalform.getTabPaneHeading() != null&& penalform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}
				if (MenuNameReader.containsKeyScreen(penalform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(penalform.getPageidentity().getPageId());
					System.out.println("Path in PenalIntFix===>" + path);
					LoanDailyPostInitialParam(req, loandelegate, path);
					return map.findForward(ResultHelp.getSuccess());
				} 
				else
				{
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}else if (map.getPath().equalsIgnoreCase("/Loans/PenalIntFix")) {
			try {
				PenalActionForm penalform = (PenalActionForm) form;
				LoansDelegate loandelegate = new LoansDelegate();
				lmobj = new LoanMasterObject();
				LoanTransactionObject	lntrnobj=null;
				CustomerMasterObject customerobj=null;
				req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
				req.setAttribute("TabNum", "0");
				getTabbedpane_for_OtherchargesTwo(req, penalform);
				System.out.println("===============>"+ penalform.getTabPaneHeading());
				if (penalform.getTabPaneHeading() != null && penalform.getTabPaneHeading().equalsIgnoreCase("LoanStatus")) {
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}
				else
				{
					req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
					req.setAttribute("TabNum", "0");
				}
				if(penalform.getDetails().equalsIgnoreCase("Personal"))
				{
					req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
					req.setAttribute("TabNum", "0");
				}
				else if(penalform.getDetails().equalsIgnoreCase("Loan Status"))
				{
					req.setAttribute("flag", MenuNameReader.getScreenProperties("5018"));
					req.setAttribute("TabNum", "1");
				}
				if (MenuNameReader.containsKeyScreen(penalform.getPageidentity().getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(penalform.getPageidentity().getPageId());
					lntrnobj=new LoanTransactionObject();
					customerobj=new CustomerMasterObject();
					if (penalform.getAcno() != 0) 
					{
						lmobj = loandelegate.getLoanMaster(penalform.getAcno(),penalform.getAcctype());	// Validate for account exist or not
						if(lmobj!=null)
						{
							customerobj=loandelegate.getCustomer(lmobj.getCustomerId());
							lntrnobj = loandelegate.getQueryLoanStatus(penalform.getAcctype(), penalform.getAcno(),loandelegate.getSysDate(),lnuser,lntml,loandelegate.getSysDateTime());
							System.out.println("LOanTran Object"+ lntrnobj.toString());
							req.setAttribute("LoanTranObject", lntrnobj);
							if(customerobj!=null)
							{
								req.setAttribute("personalInfo", customerobj);
								req.setAttribute("panelName","Personal Details");
							}
							else
							{
								req.setAttribute("msg","Customer Does Not exist");
							}
						}
						else
						{
							req.setAttribute("msg","Account Does Not exist");
						}
						System.out.println("Lmobj" + lmobj);
					}
					String button_value = penalform.getForward();
					System.out.println("Button in PenalIntFix===>"+ button_value);
					if (penalform.getForward() != null) {
						if (penalform.getForward().equalsIgnoreCase("Submit")) 
						{
							lntrnobj = new LoanTransactionObject();
							lntrnobj.setAccType(penalform.getAcctype());
							lntrnobj.setAccNo(penalform.getAcno());
							lntrnobj.setTranType("R");
							lntrnobj.setTranMode("T");
							lntrnobj.setCdind("D");
							lntrnobj.setTransactionDate(delegate.getSysDate());
							lntrnobj.setTranSou(lntml);
							lntrnobj.setPenaltyAmount(penalform.getPen_amt());
							Object return_value = loandelegate.penalIntFix(lntrnobj);
							req.setAttribute("return_value", return_value);
							System.out.println("Return" + return_value);// Display Penal Interest successfully
							if(return_value != null) {
								req.setAttribute("msg","Penal Interest Sucessfully Inserted");
								System.out.println("Penal Interest Sucessfully Inserted");
							}
							if(return_value=="1")
							{	
								System.out.println("In the return value");
								penalform.setValid("penalset");
							}
						}
					}
				}
				path = MenuNameReader.getScreenProperties(penalform.getPageidentity().getPageId());
				System.out.println("Path in PenalIntFix===>" + path);
				LoanDailyPostInitialParam(req, loandelegate, path);
				return map.findForward(ResultHelp.getSuccess());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.getPath().equalsIgnoreCase("/Loans/ActionDueReportMenu")) {
			try {
				ActionDueReport dueform = (ActionDueReport) form;
				LoansDelegate loansdelegate = new LoansDelegate();
				if (MenuNameReader.containsKeyScreen(dueform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(dueform.getPageidentity().getPageId());
					LoanDailyPostInitialParam(req, loansdelegate, path);
					return map.findForward(ResultHelp.getSuccess());
				} else
					return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().equalsIgnoreCase("/Loans/ActionDueReport")) {
			try {
				ActionDueReport dueform = (ActionDueReport) form;
				LoansDelegate loandelegate = new LoansDelegate();
				LoanMasterObject lnObj;
				if (MenuNameReader.containsKeyScreen(dueform.getPageidentity().getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(dueform.getPageidentity().getPageId());
					if(dueform.getAcctype()!=null)
					{
						lnObj=loandelegate.getModName(dueform.getAcctype());
						req.setAttribute("ModName", lnObj);
					}
					LoanDailyPostInitialParam(req, loandelegate, path);
					String button_value = dueform.getForward();
					System.out.println("Button Value=======>" + button_value);
					if (dueform.getForward().equalsIgnoreCase("View")) 
					{
						if (button_value != null) 
						{
						System.out.println("am in View");
						System.out.println("am in View1");//object = loandelegate.getMainModules(1, "1001000");
						req.setAttribute("DueReport", loandelegate.getActionDue(dueform.getDate(), dueform.getAcctype(), dueform.getStagecode()));
					}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// LoanDefaulterProcessing Action
		if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanDefaultProcessingMenu")) {
			System.out.println("In LoanDefaultProcessingMenu ");
			try {
				LoanDefaulterForm defaultform = (LoanDefaulterForm) form;
				LoansDelegate delegate = new LoansDelegate();
				logger.info("File--->"+ defaultform.getPageidentity().getPageId());
				PageIdForm pg = defaultform.getPageidentity();
				path = MenuNameReader.getScreenProperties(defaultform.getPageidentity().getPageId());
				System.out.println("delegate.getSysDate()===================="+ delegate.getSysDate());
				req.setAttribute("ApplicationDate", delegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(defaultform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(defaultform.getPageidentity().getPageId());
					System.out.println("Path in LoanDefaultProcessingMenu=====>"+ path);
					LoanDefauterInitialParam(req, delegate, path);
					return map.findForward(ResultHelp.getSuccess());
				} else
					return map.findForward(ResultHelp.getError());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (map.getPath().equalsIgnoreCase("/Loans/LoanDefaultProcessing")) {
			System.out.println("In LoanDefaultProcessing");
			try {
				LoanDefaulterForm defaultform = (LoanDefaulterForm) form;
				LoansDelegate delegate = new LoansDelegate();
				String ac_type = null;
				ModuleObject lnmod[];
				if (MenuNameReader.containsKeyScreen(defaultform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(defaultform.getPageidentity().getPageId());
					LoanDefauterInitialParam(req, delegate, path);
					String button_clicked = null, process_due_towards = null;
					String stage_code[][] = null;
					int from_ac_no, to_ac_no, stage_no;
					double prn_from_amt, prn_to_amt, int_from_amt, int_to_amt, bal_from_amt, bal_to_amt;
					Vector defaulter_vector = null;
					button_clicked = defaultform.getForward();
					if (button_clicked != null) {
						if (button_clicked.equalsIgnoreCase("Process")) {
							lnmod = delegate.getMainModules(2, "1010000");
							if (defaultform.getAcctype().equalsIgnoreCase("All Types"))
								ac_type = "1010%";
							else
								ac_type = defaultform.getAcctype();
							System.out.println("Process Button=====>"+ defaultform.getRemainder());
							if (defaultform.getRemainder().equalsIgnoreCase("Process")) {
								System.out.println("PrincipalWise===>"+ defaultform.getPr_wise()+ "Int Wise=====>"+ defaultform.getInt_wise());
								System.out.println("Check============>"+ defaultform.getBal_fromamt()+ "check----->"+ defaultform.getBal_toamt());
								if (defaultform.getPr_wise() != null && defaultform.getInt_wise() != null)
									if (defaultform.getPr_wise().trim().toString().equalsIgnoreCase("on")&& defaultform.getInt_wise().trim().toString().equalsIgnoreCase("on")) {
										System.out.println("11111111111111");
										process_due_towards = "B";
									} else if (defaultform.getPr_wise() != null)
										System.out.println("================");
								if (defaultform.getPr_wise().trim().toString().equalsIgnoreCase("on")) {
									System.out.println("22222222");
									process_due_towards = "P";
								} else {
									System.out.println("33333333");
									process_due_towards = "I";
								}
							} else if (defaultform.getRemainder().equalsIgnoreCase("Notice")) {
								if (defaultform.getPr_wise() != null && defaultform.getInt_wise() != null)
									if (defaultform.getPr_wise().trim().equalsIgnoreCase("on")&& defaultform.getInt_wise().trim().equalsIgnoreCase("on"))
										process_due_towards = "Y%";
									else if (defaultform.getPr_wise() != null)
										if (defaultform.getPr_wise().trim().equalsIgnoreCase("on"))
											process_due_towards = "Y P";
										else
											process_due_towards = "Y I";
							}
							stage_code = delegate.getStageCode();
							for (int i = 0; i < stage_code.length; i++) {
								System.out.println("Stage Number====>"+ stage_code[i][0]);
								System.out.println("Stage Code Desc======>"+ stage_code[i][1]);
							}
							from_ac_no = defaultform.getFrom_accno();
							to_ac_no = defaultform.getTo_accno();
							prn_from_amt = defaultform.getPrn_fromamt();
							prn_to_amt = defaultform.getPrn_toamt();
							int_from_amt = defaultform.getInt_fromamt();
							int_to_amt = defaultform.getInt_toamt();
							bal_from_amt = defaultform.getBal_fromamt();
							bal_to_amt = defaultform.getBal_toamt();
							if (defaultform.getRemainder().equalsIgnoreCase("Process"))
								delegate.loanDefaulterProcessing(ac_type,from_ac_no, to_ac_no, prn_from_amt,prn_to_amt, int_from_amt, int_to_amt,bal_from_amt, bal_to_amt,process_due_towards);
							else {
							}
							if (defaulter_vector != null)
								if (defaulter_vector.size() > 0) {
									System.out.println();
									setDefaulterValues(defaulter_vector);
								}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 if(map.getPath().equalsIgnoreCase("/Loans/LoanAdminMenu")) 
		{
			LoanAdminForm adminform = (LoanAdminForm) form;
			try 
			{
				logger.info("this is Login Admin link======> 1");
				LoansDelegate delegate = new LoansDelegate();
				PageIdForm pg = adminform.getPageidentity();
				path = MenuNameReader.getScreenProperties(adminform.getPageidentity().getPageId());
				req.setAttribute("ApplicationDate", delegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(adminform.getPageidentity().getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(adminform.getPageidentity().getPageId());
					LoanAdminInitialParam(req, delegate, path);
					session = req.getSession(true);
					req.setAttribute("pageId", "/LoanAdmin.jsp");
					
					return map.findForward(ResultHelp.getSuccess());
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
		else if(map.getPath().equalsIgnoreCase("/Loans/LoanAdmin")) 
		{
			LoanAdminForm lnadminform = (LoanAdminForm) form;
			try 
			{
				LoansDelegate delegate = new LoansDelegate();
				String value = null;
				String button_clicked = null, actype = null, loantables = null;
				String strr[][] = null;
				String table_data[][]=null;
				loantables = lnadminform.getLoanmodules();
				logger.info("Loan Module is ======" + loantables);
				System.out.println("Loan Module is ======" + loantables);
				button_clicked = lnadminform.getForward();
				logger.info("button value is ====" + button_clicked);
				actype = lnadminform.getAcctype();
			//	if(lnadminform.getForward().equalsIgnoreCase("View")){
				req.setAttribute("Disable","disabled");
				if(actype.equalsIgnoreCase("All Types"))
					actype = "%";
						if(lnadminform.getLoanmodules().equalsIgnoreCase("LoanEntryIns")) 
						{
							System.out.println("******LoanEntryIns******");
							strr = delegate.getLoanAdminTable("LoanEntryIns");
							if(strr!=null){
							req.setAttribute("EntryIns",strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						}
						else if (loantables.equalsIgnoreCase("LoanCategoryRate")) 
						{
							strr = delegate.getLoanAdminTable("LoanCategoryRate where ln_type like '"+ actype + "'");
							if(strr!=null){
							req.setAttribute("CategoryRate", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						} 
						else if (loantables.equalsIgnoreCase("LoanIntRate")) 
						{
							strr = delegate.getLoanAdminTable("LoanIntRate where ln_type like '"+ actype + "'");
							if(strr!=null){
							req.setAttribute("IntRate", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						}
						else if (loantables.equalsIgnoreCase("LoanPeriodRate")) 
						{
							strr = delegate.getLoanAdminTable("LoanPeriodRate where ln_type like '"+ actype + "'");
							if(strr!=null){
							req.setAttribute("PeriodRate", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						} 
						else if (loantables.equalsIgnoreCase("PenalIntRate")) 
						{
							strr = delegate.getLoanAdminTable("PenalIntRate where ln_type like '"+ actype + "'");
							if(strr!=null){
							req.setAttribute("StrPenalIntRate", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						} 
						else if (loantables.equalsIgnoreCase("LoanPurposes")) 
						{
							strr = delegate.getLoanAdminTable("LoanPurposes");
							if(strr!=null){
							req.setAttribute("Purposes", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						} 
						else if (loantables.equalsIgnoreCase("DocParam")) 
						{
							strr = delegate.getLoanAdminTable("DocParam");
							if(strr!=null){
							req.setAttribute("StrDocParam", strr);
							}
							else
							{
								req.setAttribute("msg","Records Not Found");
							}
						}
			//}
						if(button_clicked.equalsIgnoreCase("Submit")) 
						{
							req.setAttribute("Disable",null);
							String[] moduleCode = req.getParameterValues("ModuleCode");
							
							String[] moduleAbbr = req.getParameterValues("ModuleAbbr");
							String[] personal = req.getParameterValues("Personal");
							String[] relative = req.getParameterValues("Relative");
							String[] employment = req.getParameterValues("Employment");
							String[] application = req.getParameterValues("Application");
							String[] loanshareDetails = req.getParameterValues("LoanShareDetails");
							String[] signature = req.getParameterValues("Signature");
							String[] property = req.getParameterValues("Property");
							String[] coborrowers = req.getParameterValues("CoBorrowers");
							String[] surity = req.getParameterValues("Surity");
							String[] vehicle = req.getParameterValues("Vehicle");
							String[] gold = req.getParameterValues("Gold");
							String[] overdraft = req.getParameterValues("OverDraft");
							String[] loanType = req.getParameterValues("LoanType");
							String[] dateFrom = req.getParameterValues("DateFrom");
							String[] dateTo = req.getParameterValues("DateTo");
							String[] category = req.getParameterValues("Category");
							String[] rate = req.getParameterValues("Rate");
							String[] deTml = req.getParameterValues("De_Tml");
							String[] deUser = req.getParameterValues("De_User");
							String[] deDateTime = req.getParameterValues("De_Datetime");
							String[] purposeCode = req.getParameterValues("PurposeCode");
							String[] minimumBalance = req.getParameterValues("MinimumBalance");
							String[] maximumBalance = req.getParameterValues("MaximumBalance");
							String[] fromMonth = req.getParameterValues("FromMonth");
							String[] toMonth = req.getParameterValues("ToMonth");
							String[] penalRate = req.getParameterValues("PenalRate");
							String[] purposeNo = req.getParameterValues("PurposeNo");
							String[] purposeDesc = req.getParameterValues("PurposeDesc");
							String[] docNo =req.getParameterValues("DocNo");
							String[] docDesc = req.getParameterValues("DocDesc");
							String[] loanCat = req.getParameterValues("LoanCat");
							if(lnadminform.getLoanmodules().equalsIgnoreCase("LoanEntryIns")) 
							{
								if(moduleCode!=null && moduleAbbr!=null)
								{
									table_data = new String[moduleCode.length][14];
									for(int i=0;i<moduleCode.length;i++)
									{
										table_data[i][0]=moduleCode[i];
										table_data[i][1]=moduleAbbr[i];
										table_data[i][2]=personal[i];
										table_data[i][3]=relative[i];
										table_data[i][4]=employment[i];
										table_data[i][5]=application[i];
										table_data[i][6]=loanshareDetails[i];
										table_data[i][7]=signature[i];
										table_data[i][8]=property[i];
										table_data[i][9]=coborrowers[i];
										table_data[i][10]=surity[i];
										table_data[i][11]=vehicle[i];
										table_data[i][12]=gold[i];
										table_data[i][13]=overdraft[i];
									}
								}
								delegate.setLoanAdminTable("LoanEntryIns",table_data,"LoanEntryIns");
						}
						  else if(loantables.equalsIgnoreCase("LoanCategoryRate")) 
						  {
							  if(loanType!=null)
							  {
								  table_data = new String[loanType.length][8];
								  for(int i=0;i<loanType.length;i++)
								  {	  
								  	table_data[i][0]=loanType[i];
									table_data[i][1]=dateFrom[i];
									table_data[i][2]=dateTo[i];
									table_data[i][3]=category[i];
									table_data[i][4]=rate[i];
									table_data[i][5]=deTml[i];
									table_data[i][6]=deUser[i];
									table_data[i][7]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("LoanCategoryRate",table_data,"LoanCategoryRate where ln_type like '"+actype+"'"); 
						  } 
						  else if(loantables.equalsIgnoreCase("LoanIntRate")) 
						  {
							  if(loanType!=null)
							  {
								  table_data = new String[loanType.length][10];
								  for(int i=0;i<loanType.length;i++)
								  {
									   table_data[i][0]=loanType[i];
									   table_data[i][1]=purposeCode[i];
										table_data[i][2]=dateFrom[i];
										table_data[i][3]=dateTo[i];
										table_data[i][4]=minimumBalance[i];
										table_data[i][5]=maximumBalance[i];
										table_data[i][6]=rate[i];
										table_data[i][7]=deTml[i];
										table_data[i][8]=deUser[i];
										table_data[i][9]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("LoanIntRate",table_data,"LoanIntRate where ln_type like '"+actype+"'"); 
						  }
						  else if(loantables.equalsIgnoreCase("LoanPeriodRate")) 
						  {
							  if(loanType!=null)
							  {
								  table_data = new String[loanType.length][9];
								  for(int i=0;i<loanType.length;i++)
								  {
									    table_data[i][0]=loanType[i];
									 	table_data[i][1]=dateFrom[i];
										table_data[i][2]=dateTo[i];
										table_data[i][3]=fromMonth[i];
										table_data[i][4]=toMonth[i];
										table_data[i][5]=rate[i];
										table_data[i][6]=deTml[i];
										table_data[i][7]=deUser[i];
										table_data[i][8]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("LoanPeriodRate",table_data,"LoanIntRate where ln_type like '"+actype+"'"); 
						  } 
						  else if(loantables.equalsIgnoreCase("PenalIntRate")) 
						  {
							  if(loanType!=null)
							  {
								  table_data = new String[loanType.length][8];
								  for(int i=0;i<loanType.length;i++)
								  {
									    table_data[i][0]=loanType[i];
									 	table_data[i][1]=dateFrom[i];
										table_data[i][2]=dateTo[i];
										table_data[i][3]=category[i];
										table_data[i][4]=penalRate[i];
										table_data[i][5]=deTml[i];
										table_data[i][6]=deUser[i];
										table_data[i][7]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("PenalIntRate",table_data,"PenalIntRate where ln_type like '"+actype+"'"); 
						  } 
						  else if(loantables.equalsIgnoreCase("LoanPurposes")) 
						  {
							  if(purposeNo!=null)
							  {
								  table_data = new String[purposeNo.length][5];
								  for(int i=0;i<purposeNo.length;i++)
								  {
									    table_data[i][0]=purposeNo[i];
									 	table_data[i][1]=purposeDesc[i];
										table_data[i][2]=deUser[i];
										table_data[i][3]=deTml[i];
										table_data[i][4]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("LoanPurposes",table_data,"LoanPurposes"); 
						  }
						  else if(loantables.equalsIgnoreCase("DocParam")) 
						  {
							  if(docNo!=null)
							  {
								  table_data = new String[docNo.length][6];
								  for(int i=0;i<docNo.length;i++)
								  {
									    table_data[i][0]=docNo[i];
									 	table_data[i][1]=docDesc[i];
									 	table_data[i][2]=loanCat[i];
										table_data[i][3]=deTml[i];
										table_data[i][4]=deUser[i];
										table_data[i][5]=deDateTime[i];
								  }
							  }
							  delegate.setLoanAdminTable("DocParam",table_data,"DocParam"); 
						  }
							req.setAttribute("msg","Data Submitted Successfully");
				}
				else if(button_clicked.equalsIgnoreCase("AddRow"))
				{
					value="AddRow";
					req.setAttribute("AddRow",value);		
				}
				/*else{
					req.setAttribute("msg","Please Select the Tables");
				}*/
				if (MenuNameReader.containsKeyScreen(lnadminform.getPageidentity().getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(lnadminform.getPageidentity().getPageId());
					LoanAdminInitialParam(req, delegate, path);
					req.setAttribute("pageId", "/LoanAdmin.jsp");
				}
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}
		 if(map.getPath().equalsIgnoreCase("/Loans/LoanAdminNewMenu")) 
			{
				LoanAdminForm adminform = (LoanAdminForm) form;
				try 
				{
					logger.info("this is Login Admin link======> 1");
					LoansDelegate delegate = new LoansDelegate();
					PageIdForm pg = adminform.getPageidentity();
					path = MenuNameReader.getScreenProperties(adminform.getPageidentity().getPageId());
					req.setAttribute("ApplicationDate", delegate.getSysDate());
					if (MenuNameReader.containsKeyScreen(adminform.getPageidentity().getPageId())) 
					{
						path = MenuNameReader.getScreenProperties(adminform.getPageidentity().getPageId());
						LoanAdminInitialParam(req, delegate, path);
						session = req.getSession(true);
						req.setAttribute("pageId", "/LoanAdminNew.jsp");
						System.out.println("After LoanAdminInitialParam");
						return map.findForward(ResultHelp.getSuccess());
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			} 
		if (map.getPath().equalsIgnoreCase("/Loans/SanctioningMenu")) {
			try {
				SanctionForm sanform = (SanctionForm) form;
				LoansDelegate delegate = new LoansDelegate();
				System.out.println("PAGE I~D ==== >"+ sanform.getPageidentity().getPageId());
				if (MenuNameReader.containsKeyScreen(sanform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(sanform.getPageidentity().getPageId());
					req.setAttribute("pageId", path);
					IntialParameters(req, delegate);
					sanform.setAccno(0);
					sanform.setShno(String.valueOf(0));
					sanform.setAccno(0);
					sanform.setAmount(0.0);
					sanform.setPeriod(0);
					sanform.setHoliday(0.0);
					sanform.setInstallment(0.0);
					sanform.setIntrate(0.0);
					sanform.setPenalrate(0.0);
					sanform.setCombo_pay_mode("C");
					sanform.setPayaccno(0);
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					System.out.println("COMING BACK TO ACTION CLASS ===== >");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("pageId", path);
			map.findForward(ResultHelp.getSuccess());
		}
		if (map.getPath().equalsIgnoreCase("/Loans/Sanctioning")) 
		{
			try 
			{
			//	LoanApplicationForm appform=(LoanApplicationForm)form;
				SanctionForm sanform = (SanctionForm) form;
				LoansDelegate delegate = new LoansDelegate();
				sanform.setSanction(0);
				double insta = 0;
				double int_rate = 0;
				AccountObject accountObject = null;
				req.setAttribute("msg"," ");
				System.out.println("page id=====>===>"+ sanform.getPageidentity().getPageId());
				System.out.println("acctype=====>" + sanform.getAcctype());
				req.setAttribute("pay_mode", delegate.getPayMode());
				req.setAttribute("pay_actype", delegate.getpayAcctype());
				req.setAttribute("disable"," ");
				LoanMasterObject lnobj = delegate.getLoanMaster(sanform.getAccno(), sanform.getAcctype());
				if(lnobj!= null) 
				{
					if(lnobj.uv.getVerId()==null)
					{
						req.setAttribute("msg","Loan is not yet verified");
					}
					else
					{
						if(sanform.getAccno()!= 0) 
						{
							req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
							req.setAttribute("personalInfo", delegate.getCustomer(lnobj.getCustomerId()));
							sanform.setShno(String.valueOf(lnobj.getShareAccNo()));
							if(lnobj.isLoanSanctioned() && !lnobj.isSanctionVerified())
							{				
								req.setAttribute("msg","Loan Is Already Sanctioned Do The Verification");
								req.setAttribute("disable","disable");
								sanform.setAmount((lnobj.getSanctionedAmount()));
								sanform.setPeriod((lnobj.getNoOfInstallments()));
								sanform.setWeak(lnobj.isWeakerSection());
								sanform.setHoliday((lnobj.getHolidayPeriod()));
								sanform.setCombo_pay_mode(lnobj.getPayMode());
								if(lnobj.getPayMode().equalsIgnoreCase("T")) 
								{
									sanform.setPayactype(lnobj.getPaymentAcctype());
									sanform.setPayaccno(lnobj.getPaymentAccno());
								}
								sanform.setPriority(String.valueOf(lnobj.getPrior()));
							}else if(!lnobj.isLoanSanctioned() && lnobj.isSanctionVerified()){
								req.setAttribute("msg","Loan Is Already Sanctioned Do The Verification");
							}
							if(sanform.getAmount()!= 0 && sanform.getPeriod()!=0) 
							{
								double amtsanc=0;
								double lnkshares=0;
								if(sanform.getShno()!=null && sanform.getShtype()!=null){
								accountObject = delegate.getAccount(null,sanform.getShtype(),Integer.parseInt(sanform.getShno()));
								if(accountObject!=null)
								{	
									lnkshares=delegate.getSharePercentage(String.valueOf(sanform.getAcctype()));
									amtsanc=sanform.getAmount();
									if(amtsanc>lnobj.getRequiredAmount())
									{
										req.setAttribute("msg","Sanctioned amount is greater than requested amount");
										sanform.setAmount(0.0);
									}
									else if((amtsanc*lnkshares/100)>accountObject.getAmount() && !delegate.isNominal(String.valueOf(sanform.getAcctype()), sanform.getAccno()))
									{ 
										req.setAttribute("msg","Amount required is less than linking shares");
										sanform.setAmount(0.0);
									}
									else
									{
										double prd = 0;
										int_rate = delegate.getIntRate(sanform.getAcctype(), delegate.getSysDate(), 0,sanform.getPeriod(), sanform.getAmount(),sanform.getAccno());
										sanform.setIntrate(int_rate);
										double penal_int = delegate.getPenalIntRate(sanform.getAcctype(), delegate.getSysDate(), 3);
										sanform.setPenalrate(penal_int);//	if(!sanform.getPriority().equalsIgnoreCase("None")){
										sanform.setPriority(sanform.getPriority());
										prd = (sanform.getPeriod()- sanform.getHoliday());
										if(lnobj.getInterestType()==0) // Reducing Balance
											insta=Math.round(sanform.getAmount()/prd);
										else if(lnobj.getInterestType()==1) // EMI
										{
											double rate = int_rate / 1200;
											double pow = 1;
											for(int j = 0;j < prd; j++)
												pow = pow * (1 + rate);
											insta = Math.round(0.01 * Math.round(100*(sanform.getAmount()*pow*rate) / (pow - 1)));
										}
										sanform.setInstallment(insta);
										sanform.setCombo_pay_mode(lnobj.getPayMode());
										sanform.setPayactype(lnobj.getPaymentAcctype());
										sanform.setPayaccno(lnobj.getPaymentAccno());
									}
								}
								else
								{
									req.setAttribute("msg","Share Details Are Not Found");
								}
								}
							}
							if(sanform.getButtonvalue()!= null && sanform.getButtonvalue().equalsIgnoreCase("Submit")) 
							{
								if(sanform.getPriority().equalsIgnoreCase("None")){
									req.setAttribute("msg","Please Select the priority Number!");
									}else{
										sanform.setPriority(sanform.getPriority());
								int result = delegate.sanctionLoan(Integer.valueOf(sanform.getAcctype()), sanform.getAccno(), sanform.getAmount(),sanform.getPeriod(), Integer.parseInt(sanform.getPriority()), false,int_rate, sanform.getInteresttype(),sanform.getInterestcalctype(), insta,sanform.getHoliday(), delegate.getSysDate());
								if(result!=0)
								{
									req.setAttribute("msg", "Loan is sanctioned successfully go to Verification");
									sanform.setShno(String.valueOf(0));
									sanform.setAccno(0);
									sanform.setAmount(0.0);
									sanform.setPeriod(0);
									sanform.setHoliday(0.0);
									sanform.setInstallment(0.0);
									sanform.setIntrate(0.0);
									sanform.setPenalrate(0.0);
									sanform.setCombo_pay_mode("C");
									sanform.setPayaccno(0);
								}
								else
								{
									req.setAttribute("msg","Loan cannot be sanctioned");
								}
							}}
							else if(sanform.getButtonvalue()!= null && sanform.getButtonvalue().equalsIgnoreCase("Delete")) 
							{
								if(!(lnobj.isLoanDisbursed()))
								{
									int i=delegate.deleteLoanSanction(sanform.getAcctype(),sanform.getAccno());
									if(i==1)
									{
										req.setAttribute("msg","Loan Account Sanctioning is successfully Deleted");
									}
									else
									{
										req.setAttribute("msg"," Deletion Failed");
									}
								}
								else
								{
									req.setAttribute("msg","Delete The Disbursement Details");
								}
							}	
					   }
					   else
						{
						   req.setAttribute("msg","Enter Loan Account Number");
						}
					}
				}
				else
				{
					req.setAttribute("msg","Loan Number Not Found");
					sanform.setAccno(0);
					sanform.setShno(String.valueOf(0));
					sanform.setAccno(0);
					sanform.setAmount(0.0);
					sanform.setPeriod(0);
					sanform.setHoliday(0.0);
					sanform.setInstallment(0.0);
					sanform.setIntrate(0.0);
					sanform.setPenalrate(0.0);
					sanform.setCombo_pay_mode("C");
					sanform.setPayaccno(0);
				}
				String modcode = sanform.getAcctype();
				System.out.println("modcode is ============" + modcode);
				items_relavent = delegate.getReleventDetails_str(modcode);
				int count = 0;
				if(items_relavent!= null) 
				{
					for (int i = 0; i < items_relavent.length; i++) {
						if (items_relavent[i].equals("Y"))
							count++;
						if (items_relavent[i].equals("O"))
							count++;
					}
				}
				 if (sanform.getDetails().equalsIgnoreCase("5002")) {
	        		 System.out.println("+++++++++++++++Action class application form code ++++++++++++");
						System.out.println("appform.getDetails() ==========  "+ sanform.getDetails());
						System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						req.setAttribute("Application", MenuNameReader.getScreenProperties("5002"));
						req.setAttribute("pay_mode", delegate.getPayMode());
						req.setAttribute("pay_actype", delegate.getpayAcctype());
						sanform.setShtype(lnobj.getShareAccType());
						sanform.setShno(String.valueOf(lnobj.getShareAccNo()));
						String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
						String[] dirrelations=delegate.getDirectorRelations();
						req.setAttribute("Dirrelations",dirrelations);
						System.out.println("***********Director details**************" + dirdetails.toString());
						System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
						req.setAttribute("DirDetails",dirdetails);
						req.setAttribute("flag", null);
						sanform.setAppln_no(lnobj.getApplicationSrlNo());
						sanform.setAppndate(lnobj.getApplicationDate());
						sanform.setReqamount(lnobj.getRequiredAmount());
						sanform.setDirrelations(lnobj.getRelative());
						//appform.setDirdetails(loanmaster.getDirectorCode());
						sanform.setPaymtmode(lnobj.getPayMode());
						sanform.setPayactype(lnobj.getPaymentAcctype());
						sanform.setPayaccno(lnobj.getPaymentAccno());
						sanform.setInteresttype(lnobj.getInterestType());
						sanform.setInterestcalctype(lnobj.getInterestRateType());
					}
				else if(sanform.getDetails().equalsIgnoreCase("0001")){
	        		req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
	        		if(lnobj.getCustomerId()!=0){
	        		CustomerMasterObject Cust=delegate.getCustomer(lnobj.getCustomerId());
	        		if(Cust!=null)
	        		req.setAttribute("personalInfo", Cust);
				}
	        		req.setAttribute("panelName", CommonPanelHeading.getPersonal());
	        		req.setAttribute("TabNum", "0");
	        		System.out.println("((((((((((((((LoanMasterObject)))))))))))" + lnobj.getApplicationSrlNo());
	        		//sanform.setLoantype(lnobj.getAccType());
	        		sanform.setShtype(lnobj.getShareAccType());
	        		//sanform.setShno(lnobj.getShareAccNo());
	        		sanform.setPurpose(lnobj.getPurposeCode());
	        		System.out.println("***********After the Loanmaster object**********");
	        		System.out.println("((((((((((((sanform.getDetails()))))))))))" + sanform.getDetails());
	        	}
				else if((sanform.getDetails().equalsIgnoreCase("5003"))) 
				{
					System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
					System.out.println("appform.getDetails() ==========2");
					req.setAttribute("flag", null);
					req.setAttribute("Vehicle", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
					if(lnobj!=null){
						if(lnobj.getVehicleDet()!=null)
							req.setAttribute("VECHOBJ",lnobj.getVehicleDet());
						else 
							req.setAttribute("msg","Vehicle Details Not Found");
					}
				}
				else if ((sanform.getDetails().equalsIgnoreCase("5004"))) 
				{
					req.setAttribute("flag", null);
					System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
					System.out.println("appform.getDetails() ==========3");
					req.setAttribute("Property", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
					if(lnobj!=null){
						if(lnobj.getPropertyDetails()!=null)	
							req.setAttribute("PROPOBJ",lnobj.getPropertyDetails());
						else
							req.setAttribute("msg","Property Details Not Found");
						}
					
				}  else if (sanform.getDetails().equalsIgnoreCase("5005")) {
					System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
					System.out.println("appform.getDetails() ==========4");
					session=req.getSession();
					System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(sanform.getDetails()));
					req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
					tabpane_employment(req, sanform);
					req.setAttribute("TabNum", "0");
					IncomeObject[] incomeObjects=lnobj.getIncomeDetails();
					IncomeObject inObj=null;
					for(int i=0;i<incomeObjects.length;i++){
						inObj=(IncomeObject)Array.get(incomeObjects, i);
						if(inObj.getTypeOfIncome()!=null && sanform.getTabPaneHeading()!=null){
							sanform.setShtype(lnobj.getShareAccType());
						if (sanform.getTabPaneHeading().equalsIgnoreCase("Self") && inObj.getTypeOfIncome().equalsIgnoreCase("Self")) {
							req.setAttribute("TabNum", "0");
							session.setAttribute("Self", inObj);
						}else if (sanform.getTabPaneHeading().equalsIgnoreCase("Service") && inObj.getTypeOfIncome().equalsIgnoreCase("Service")) {
							req.setAttribute("TabNum", "1");
							session.setAttribute("Service", inObj);
						} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Business") && inObj.getTypeOfIncome().equalsIgnoreCase("Business")) {
							req.setAttribute("TabNum", "2");
							session.setAttribute("Business", inObj);
						} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Pension") && inObj.getTypeOfIncome().equalsIgnoreCase("Pension")) {
							req.setAttribute("TabNum", "3");
							session.setAttribute("Pension", inObj);
						} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Rent") && inObj.getTypeOfIncome().equalsIgnoreCase("Rent")) {
							req.setAttribute("TabNum", "4");
							session.setAttribute("Rent", inObj);
						}
						}
					}
				}else if(sanform.getDetails().trim().equalsIgnoreCase("5093")) 
				{
					System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
					System.out.println("appform.getDetails() ==========5");
					req.setAttribute("flag", null);
					System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
					req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
					if(lnobj!=null){
						if(lnobj.getRelatives()!=null){
							req.setAttribute("RelObj",(lnobj.getRelatives()));
						}
						else if(lnobj.getDependents()!=null){
							req.setAttribute("RelDepObj",(lnobj.getDependents()));
						}
						else if(lnobj.getInterests()!=null){
							req.setAttribute("RelIndObj",(lnobj.getInterests()));
						}
					}else{
						req.setAttribute("msg","Relative Details Not Found");
					}
				}else if(sanform.getDetails().trim().equalsIgnoreCase("5027")) 
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
					vector=lnobj.getSurities();
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
												//sanform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
				}else if (sanform.getDetails().trim().equalsIgnoreCase("Signature Ins")) {
					CustomerMasterObject cust=null;
					System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
					if(signObject!=null){
					signObject=delegate.getSignatureDetails(Integer.parseInt(sanform.getShno()), sanform.getShtype());
					if(signObject[0].getCid()!=0){
      			   cust=delegate.getCustomer(signObject[0].getCid());
          			req.setAttribute("cust",cust);
          			req.setAttribute("sigObject",signObject);
          			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
          			path=MenuNameReader.getScreenProperties("5053");
          			System.out.println("path is---------------"+path);
          			req.setAttribute("flag","/SingnatureInst1.jsp"); 
					}}else{
						req.setAttribute("msg", "Signature Details are not Found!");
					}
				}else if((sanform.getDetails().equalsIgnoreCase("Loan and Share Details"))) 
				{
					System.out.println("appform.getDetails() ==========8");
					req.setAttribute("flag", null);
					System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
					req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
					req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(sanform.getShtype(),Integer.parseInt(sanform.getShno())));
					req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
					req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
					req.setAttribute("IndividualCoBorrower", null);
				} else if (sanform.getDetails().trim().equalsIgnoreCase("5032")) {
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
					vector=lnobj.getCoBorrowers();
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
												//sanform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
				}else if (sanform.getDetails().trim().equalsIgnoreCase("Surities")) {
	        		 GoldObject goldObject=null;	
	        		 System.out.println("appform.getDetails() ==========Gold");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
						
						goldObject=lnobj.getGoldDet();
						if(goldObject!=null){
						req.setAttribute("GoldDet", goldObject);
						req.setAttribute("flag", null);
						req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
					
						}
						else{
							req.setAttribute("msg", "Gold Details are not found for this account");
						}
					}
				if (MenuNameReader.containsKeyScreen(sanform.getPageidentity().getPageId())) {
					path = MenuNameReader.getScreenProperties(sanform.getPageidentity().getPageId());
					IntialParameters(req, delegate);
					req.setAttribute("pageId", path);
					Vector vec_final[] = delegate.getRelevantDet(modcode);
					req.setAttribute("RelevantDetails", vec_final[0]);
					req.setAttribute("RelevantPageId", vec_final[1]);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(map.getPath().equalsIgnoreCase("/Loans/SanctionVerifyMenu"))
		{	
			System.out.println("*****************/Loans/SanctionVerifyMenu***********");
			try {
				SanctionForm sanform = (SanctionForm) form;
				LoansDelegate delegate = new LoansDelegate();
				System.out.println("PAGE I~D ==== >"+ sanform.getPageidentity().getPageId());
				if (MenuNameReader.containsKeyScreen(sanform.getPageidentity().getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(sanform.getPageidentity().getPageId());
					req.setAttribute("pageId", path);
					sanform.setShno(String.valueOf(0));
					sanform.setAccno(0);
					sanform.setAmount(0.0);
					sanform.setPeriod(0);
					sanform.setHoliday(0.0);
					sanform.setInstallment(0.0);
					sanform.setIntrate(0.0);
					sanform.setPenalrate(0.0);
					sanform.setCombo_pay_mode("C");
					sanform.setPayaccno(0);
					req.setAttribute("pay_mode", delegate.getPayMode());
					req.setAttribute("pay_actype", delegate.getpayAcctype());
					IntialParameters(req, delegate);
					System.out.println("COMING BACK TO ACTION CLASS ===== >");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("pageId", path);
			map.findForward(ResultHelp.getSuccess());	
		}
		if(map.getPath().equalsIgnoreCase("/Loans/SanctionVerify"))
		{
			System.out.println("*********************/Loans/SanctionVerify****************");
			try {
				
				SanctionForm sanform = (SanctionForm) form;
				LoansDelegate delegate = new LoansDelegate();
				sanform.setSanction(0);
				req.setAttribute("pay_mode", delegate.getPayMode());
				req.setAttribute("pay_actype", delegate.getpayAcctype());
				System.out.println("page id=====>===>"+ sanform.getPageidentity().getPageId());
				System.out.println("acctype=====>" + sanform.getAcctype());
				LoanMasterObject lnobj = delegate.getLoanMaster(sanform.getAccno(), sanform.getAcctype());
				System.out.println("Inside Loan Sanctioned Verification Before");
				if(lnobj!=null) 
				{
					if(sanform.getAccno()!= 0) 
					{
						if(!(lnobj.isSanctionVerified()))
						{
							System.out.println("Inside Loan Sanctioned Verification");
							sanform.setShno(String.valueOf(lnobj.getShareAccNo()));
							sanform.setPurpose(lnobj.getPurposeCode());
							sanform.setAmount(lnobj.getSanctionedAmount());
							sanform.setPeriod(lnobj.getNoOfInstallments());
							sanform.setHoliday(lnobj.getHolidayPeriod());
							sanform.setInstallment(lnobj.getInstallmentAmt());
							sanform.setIntrate(lnobj.getInterestRate());
							req.setAttribute("penalty",String.valueOf(lnobj.getPenalRate()));
							sanform.setPenalrate(lnobj.getPenalRate());
							req.setAttribute("Priority",String.valueOf(lnobj.getPrior()));
							sanform.setPriority(String.valueOf(lnobj.getPrior()));
							//sanform.setCombo_pay_mode(lnobj.getPayMode());
							req.setAttribute("paymode", lnobj.getPayMode());
							sanform.setPayactype(lnobj.getPaymentAcctype());
							sanform.setPayaccno(lnobj.getPaymentAccno());
						}
						else
						{
							req.setAttribute("msg","Loan Sanction Is Already Verified");
							sanform.setAccno(0);
							sanform.setShno(String.valueOf(0));
							sanform.setAccno(0);
							sanform.setAmount(0.0);
							sanform.setPeriod(0);
							sanform.setHoliday(0.0);
							sanform.setInstallment(0.0);
							sanform.setIntrate(0.0);
							sanform.setPenalrate(0.0);
							sanform.setCombo_pay_mode("C");
							sanform.setPayaccno(0);
						}
					}
					else
					{
						req.setAttribute("msg","Enter Loan Account Number");
					}
					if(sanform.getButtonvalue()!= null && sanform.getButtonvalue().equalsIgnoreCase("Verify")) 
					{
						System.out.println("Button value====>"+ sanform.getButtonvalue());
						System.out.println("*********Inside Verify******");
					      int result=delegate.verifySanction(Integer.parseInt(sanform.getAcctype()), sanform.getAccno());
						if(result != 0)
						{
							req.setAttribute("msg", "The Sanctioned loan is verified do the Disbursement!");
							sanform.setAccno(0);
							sanform.setShno(String.valueOf(0));
							sanform.setAccno(0);
							sanform.setAmount(0.0);
							sanform.setPeriod(0);
							sanform.setHoliday(0.0);
							sanform.setInstallment(0.0);
							sanform.setIntrate(0.0);
							sanform.setPenalrate(0.0);
							sanform.setCombo_pay_mode("C");
							sanform.setPayaccno(0);//sanform.setSanction_res(result);
						}
						else
						{	
							req.setAttribute("msg", "The Sanctioned loan cannot be verified ");//sanform.setSanction_res(result);
						}
					}
				}
					String modcode = sanform.getAcctype();
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
					}if(lnobj!=null){
					if(sanform.getDetails().equalsIgnoreCase("0001")){
		        		req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
		        		
		        		if(lnobj.getCustomerId()!=0){
		        		req.setAttribute("personalInfo", delegate.getCustomer(lnobj.getCustomerId()));
		        		req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		        		req.setAttribute("TabNum", "0");
		        		System.out.println("((((((((((((((LoanMasterObject)))))))))))" + lnobj.getApplicationSrlNo());
		        		sanform.setAcctype(lnobj.getAccType());
		        		sanform.setShtype(lnobj.getShareAccType());
		        		sanform.setShno(String.valueOf(lnobj.getShareAccNo()));
		        		sanform.setPurpose(lnobj.getPurposeCode());
		        		System.out.println("***********After the Loanmaster object**********");
		        		System.out.println("((((((((((((appform.getDetails()))))))))))" + sanform.getDetails());
		        		}
		        	}
					else if(sanform.getDetails().equalsIgnoreCase("5002")) 
					{
						System.out.println("+++++++++++++++Action class application form code ++++++++++++");
						System.out.println("appform.getDetails() ==========  "+ sanform.getDetails());
						System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						req.setAttribute("Application", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						req.setAttribute("pay_mode", delegate.getPayMode());
						req.setAttribute("pay_actype", delegate.getpayAcctype());
						String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
						String[] dirrelations=delegate.getDirectorRelations();
						req.setAttribute("Dirrelations",dirrelations);
						System.out.println("***********Director details**************" + dirdetails.toString());
						System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
						req.setAttribute("DirDetails",dirdetails);
						req.setAttribute("flag", null);
						req.setAttribute("LoanDetails",lnobj);
						sanform.setAppln_no(lnobj.getApplicationSrlNo());
						sanform.setAppndate(lnobj.getApplicationDate());
						sanform.setReqamount(lnobj.getRequiredAmount());
						sanform.setDirrelations(lnobj.getRelative());
						//sanform.setDirdetails(lnobj.getDirectorCode());
						sanform.setPaymtmode(lnobj.getPayMode());
						sanform.setPayactype(lnobj.getPaymentAcctype());
						sanform.setPayaccno(lnobj.getPaymentAccno());
						sanform.setInteresttype(lnobj.getInterestType());
						sanform.setInterestcalctype(lnobj.getInterestRateType());
					}
					else if((sanform.getDetails().equalsIgnoreCase("5003"))) 
					{
						System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
						System.out.println("appform.getDetails() ==========2");
						req.setAttribute("flag", null);
						req.setAttribute("Vehicle", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						
						if(lnobj!=null){
							if(lnobj.getVehicleDet()!=null)
								req.setAttribute("VECHOBJ",lnobj.getVehicleDet());
							else 
								req.setAttribute("msg","Vehicle Details Not Found");
						}
					}
					else if ((sanform.getDetails().equalsIgnoreCase("5004"))) 
					{
						req.setAttribute("flag", null);
						System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
						System.out.println("appform.getDetails() ==========3");
						req.setAttribute("Property", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						if(lnobj!=null){
							if(lnobj.getPropertyDetails()!=null)	
								req.setAttribute("PROPOBJ",lnobj.getPropertyDetails());
							else
								req.setAttribute("msg","Property Details Not Found");
							}
						
					}
					 else if (sanform.getDetails().equalsIgnoreCase("5005")) {
							System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
							System.out.println("appform.getDetails() ==========4");
							session=req.getSession();
							System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(sanform.getDetails()));
							req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
							tabpane_employment(req, sanform);
							req.setAttribute("TabNum", "0");
							
							IncomeObject[] incomeObjects=lnobj.getIncomeDetails();
							IncomeObject inObj=null;
							for(int i=0;i<incomeObjects.length;i++){
								inObj=(IncomeObject)Array.get(incomeObjects, i);
								if(inObj.getTypeOfIncome()!=null && sanform.getTabPaneHeading()!=null){
									sanform.setShtype(lnobj.getShareAccType());
								if (sanform.getTabPaneHeading().equalsIgnoreCase("Self") && inObj.getTypeOfIncome().equalsIgnoreCase("Self")) {
									req.setAttribute("TabNum", "0");
									session.setAttribute("Self", inObj);
								}else if (sanform.getTabPaneHeading().equalsIgnoreCase("Service") && inObj.getTypeOfIncome().equalsIgnoreCase("Service")) {
									req.setAttribute("TabNum", "1");
									session.setAttribute("Service", inObj);
								} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Business") && inObj.getTypeOfIncome().equalsIgnoreCase("Business")) {
									req.setAttribute("TabNum", "2");
									session.setAttribute("Business", inObj);
								} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Pension") && inObj.getTypeOfIncome().equalsIgnoreCase("Pension")) {
									req.setAttribute("TabNum", "3");
									session.setAttribute("Pension", inObj);
								} else if (sanform.getTabPaneHeading().equalsIgnoreCase("Rent") && inObj.getTypeOfIncome().equalsIgnoreCase("Rent")) {
									req.setAttribute("TabNum", "4");
									session.setAttribute("Rent", inObj);
								}
								}
							}
						}else if(sanform.getDetails().trim().equalsIgnoreCase("5093")) 
					{
						System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
						System.out.println("appform.getDetails() ==========5");
						req.setAttribute("flag", null);
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
						req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(sanform.getDetails().trim()));
						if(lnobj!=null){
							if(lnobj.getRelatives()!=null){
								req.setAttribute("RelObj",(lnobj.getRelatives()));
							}
							else if(lnobj.getDependents()!=null){
								req.setAttribute("RelDepObj",(lnobj.getDependents()));
							}
							else if(lnobj.getInterests()!=null){
								req.setAttribute("RelIndObj",(lnobj.getInterests()));
							}
						}else{
							req.setAttribute("msg","Relative Details Not Found");
						}
					}else if(sanform.getDetails().trim().equalsIgnoreCase("5027")) 
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
						vector=lnobj.getSurities();
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
													//sanform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
					}else if (sanform.getDetails().trim().equalsIgnoreCase("Signature Ins")) {
						
						System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
						signObject=delegate.getSignatureDetails(Integer.parseInt(sanform.getShno()), sanform.getShtype());
	      			  	CustomerMasterObject cust=delegate.getCustomer(signObject[0].getCid());
	          			req.setAttribute("cust",cust);
	          			req.setAttribute("sigObject",signObject);
	          			path=MenuNameReader.getScreenProperties("5053");
	          			System.out.println("path is---------------"+path);
	          			req.setAttribute("flag","/SingnatureInst1.jsp"); 
					}else if((sanform.getDetails().equalsIgnoreCase("Loan and Share Details"))) 
					{
						System.out.println("appform.getDetails() ==========8");
						req.setAttribute("flag", null);
						System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(sanform.getShtype(),Integer.parseInt(sanform.getShno())));
						req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
						req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
						req.setAttribute("IndividualCoBorrower", null);
					} else if (sanform.getDetails().trim().equalsIgnoreCase("5032")) {
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
						vector=lnobj.getCoBorrowers();
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
													//sanform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
					}else if (sanform.getDetails().trim().equalsIgnoreCase("Surities")) {
		        		 GoldObject goldObject=null;	
		        		 System.out.println("appform.getDetails() ==========Gold");
							System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
							req.setAttribute("flag", null);
							req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
							goldObject=lnobj.getGoldDet();
							if(goldObject!=null){
							req.setAttribute("GoldDet", goldObject);
							}else{
								req.setAttribute("msg", "Gold Details are not found for this account");
							}
						}else if (sanform.getDetails().equalsIgnoreCase("5022")) {
							System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
							System.out.println("sanform.getDetails() ==========2" + sanform.getDetails());
							VehicleObject vehicleObject=null;
							req.setAttribute("flag", null);
							req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5003"));
							vehicleObject=lnobj.getVehicleDet();
							if(vehicleObject!=null){
							req.setAttribute("VECHOBJ", vehicleObject);
							}else{
								req.setAttribute("msg","Vehicle Details are not found for this account");
							}
						}}
					if (MenuNameReader.containsKeyScreen(sanform.getPageidentity().getPageId())) {
						path = MenuNameReader.getScreenProperties(sanform.getPageidentity().getPageId());
						IntialParameters(req, delegate);
						req.setAttribute("pageId", path);
						Vector vec_final[] = delegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
						return map.findForward(ResultHelp.getSuccess());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

			 if(map.getPath().equalsIgnoreCase("/Loans/LoansApplicationDEVerifyMenu"))
			 {
				 System.out.println("*********************/Loans/LoansApplicationDEVerifyMenu*************");
					try {
						LoanApplicationForm appform = (LoanApplicationForm) form;
						LoansDelegate delegate = new LoansDelegate();
						appform.setLnaccno(0);
						appform.setShno(0);
						appform.setLoantype("SELECT");
						System.out.println("PAGE I~D ==== >"+ appform.getPageidentity().getPageId());
						if (MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())) 
						{
						try
						{
						String	path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
						req.setAttribute("pageId", path);
					    IntialParameters(req, delegate);
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
		 if(map.getPath().equalsIgnoreCase("/Loans/LoansApplicationDEVerify"))
		 {
			 System.out.println("*********************/Loans/LoansApplicationDEVerify*************");
				try {
					LoanApplicationForm appform = (LoanApplicationForm) form;
					LoansDelegate delegate = new LoansDelegate();
					System.out.println("PAGE I~D ==== >"+ appform.getPageidentity().getPageId());
					if (MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())) 
					{
					try
					{
					String	path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
					req.setAttribute("pageId", path);
				    IntialParameters(req, delegate);
			        System.out.println("<=========COMING BACK TO ACTION CLASS Loans ADE =======>");
			        LoanMasterObject loanmaster=delegate.getLoanMaster(appform.getLnaccno(), appform.getLoantype());
			        String modcode = appform.getLoantype();
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
			        if(appform.getLnaccno()!=0 && appform.getLoantype()!=null)
			        {
			        	LoanMasterObject lmobj=delegate.getLoanMaster(appform.getLnaccno(),appform.getLoantype());
			        	LoansDelegate loandelegate = new LoansDelegate();
			        	LoanMasterObject lnObj=loandelegate.getModName(appform.getLoantype());
							System.out.println("inside the inner if loop---->");
							req.setAttribute("ModName", lnObj);
							req.setAttribute("disable", "disable");
						if(lmobj!=null)
						{
			        	if(appform.getDetails().equalsIgnoreCase("0001")){
			        		req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
			        		if(loanmaster.getCustomerId()!=0){
			        		req.setAttribute("personalInfo", delegate.getCustomer(loanmaster.getCustomerId()));
			        		req.setAttribute("panelName", CommonPanelHeading.getPersonal());
			        		req.setAttribute("TabNum", "0");
			        		System.out.println("((((((((((((((LoanMasterObject)))))))))))" + loanmaster.getApplicationSrlNo());
			        		appform.setLoantype(loanmaster.getAccType());
			        		appform.setShtype(loanmaster.getShareAccType());
							appform.setShno(loanmaster.getShareAccNo());
							appform.setPurpose(loanmaster.getPurposeCode());
			        		System.out.println("***********After the Loanmaster object**********");
			        		System.out.println("((((((((((((appform.getDetails()))))))))))" + appform.getDetails());
			        		}
			        	}
			        	else if (appform.getDetails().equalsIgnoreCase("5002")) {
			        		 System.out.println("+++++++++++++++Action class application form code ++++++++++++");
								System.out.println("appform.getDetails() ==========  "+ appform.getDetails());
								System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(appform.getDetails().trim()));
								req.setAttribute("Application", MenuNameReader.getScreenProperties("5002"));
								req.setAttribute("pay_mode", delegate.getPayMode());
								req.setAttribute("pay_actype", delegate.getpayAcctype());
						   		appform.setShtype(loanmaster.getShareAccType());
								appform.setShno(loanmaster.getShareAccNo());
								String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
								String[] dirrelations=delegate.getDirectorRelations();
								req.setAttribute("Dirrelations",dirrelations);
								System.out.println("***********Director details**************" + dirdetails.toString());
								System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
								req.setAttribute("DirDetails",dirdetails);
								req.setAttribute("flag", null);
								appform.setAppln_no(loanmaster.getApplicationSrlNo());
								appform.setAppndate(loanmaster.getApplicationDate());
								appform.setReqamount(loanmaster.getRequiredAmount());
								appform.setDirrelations(loanmaster.getRelative());
								//appform.setDirdetails(loanmaster.getDirectorCode());
								appform.setPaymtmode(loanmaster.getPayMode());
								appform.setPayactype(loanmaster.getPaymentAcctype());
								appform.setPayaccno(loanmaster.getPaymentAccno());
								appform.setInteresttype(loanmaster.getInterestType());
								appform.setInterestcalctype(loanmaster.getInterestRateType());
							}
			        	else if (appform.getDetails().equalsIgnoreCase("5005")) {
							System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
							System.out.println("appform.getDetails() ==========4");
							session=req.getSession();
							System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(appform.getDetails()));
							req.setAttribute("flag", MenuNameReader.getScreenProperties("6003"));
							tabpane_employment(req, appform);
							req.setAttribute("TabNum", "0");
							IncomeObject[] incomeObjects=loanmaster.getIncomeDetails();
							IncomeObject inObj=null;
							for(int i=0;i<incomeObjects.length;i++){
								inObj=(IncomeObject)Array.get(incomeObjects, i);
								appform.setShareno(String.valueOf(loanmaster.getShareAccNo()));
								if(inObj.getTypeOfIncome()!=null){
									appform.setShareno(String.valueOf(loanmaster.getShareAccNo()));
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
			        	else if (appform.getDetails().trim().equalsIgnoreCase("Signature Ins")) {
			        		CustomerMasterObject cust=null;
							System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
							signObject=delegate.getSignatureDetails(appform.getShno(), appform.getShtype());
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
			        	else if (appform.getDetails().equalsIgnoreCase("Loan and Share Details")) {
							System.out.println("appform.getDetails() ==========8");
							req.setAttribute("flag", null);
							System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
							req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
							req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(appform.getShtype(),appform.getShno()));
							req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
							req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
							req.setAttribute("IndividualCoBorrower", null);
						}
			        	 else if (appform.getDetails().equalsIgnoreCase("5022")) {
								System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
								System.out.println("appform.getDetails() ==========2" + appform.getDetails());
								VehicleObject vehicleObject=null;
								req.setAttribute("flag", null);
								req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5003"));
								vehicleObject=loanmaster.getVehicleDet();
								req.setAttribute("VECHOBJ", vehicleObject);
							}
			        	 else if (appform.getDetails().equalsIgnoreCase("5004")) {
								PropertyObject propertyObject=null;
								System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
								System.out.println("appform.getDetails() ==========3");
								req.setAttribute("flag", null);
								req.setAttribute("Property", MenuNameReader.getScreenProperties("5004"));
								propertyObject=loanmaster.getPropertyDetails();
								req.setAttribute("PROPOBJ", propertyObject);
							}
			        	 else if (appform.getDetails().trim().equalsIgnoreCase("5093")) {
			        		 	Object[][] relatives=null;
			        		 	Object[][] indents=null;
			        		 	Object[][] dependents=null;
								System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
								System.out.println("appform.getDetails() ==========5");
								req.setAttribute("flag", null);
								System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
								req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties("5093"));
								relatives=loanmaster.getRelatives();
								indents=loanmaster.getInterests();
								dependents=loanmaster.getDependents();
								req.setAttribute("RelObj", relatives);
								req.setAttribute("RelIndObj", indents);
								req.setAttribute("RelDepObj", dependents);
							}
			        	 else if(appform.getDetails().trim().equalsIgnoreCase("5027")) 
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
								vector=loanmaster.getSurities();
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
															appform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
			        	 else if (appform.getDetails().trim().equalsIgnoreCase("5032")) {
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
								vector=loanmaster.getCoBorrowers();
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
															appform.setCoshareno(String.valueOf(array_accountObject[0].getAccno()));
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
			        	 else if (appform.getDetails().trim().equalsIgnoreCase("Surities")) {
			        		 GoldObject goldObject=null;	
			        		 System.out.println("appform.getDetails() ==========Gold");
			        		 appform.setShtype(loanmaster.getShareAccType());
								appform.setShno(loanmaster.getShareAccNo());
								System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
								goldObject=loanmaster.getGoldDet();
								if(goldObject!=null){
									req.setAttribute("flag", null);
									req.setAttribute("GoldDet", goldObject.getGoldDet());
									req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
								}else{
									req.setAttribute("msg", "Gold Details are not found for this account");
								}
							}
					}else{
	        			req.setAttribute("msg", "Account Number Not Found!");
	        		}
			        }
							if(loanmaster !=null )
							{
								if(loanmaster.uv.getVerId()!=null && loanmaster.uv.getVerTml()!=null && loanmaster.uv.getVerDate()!=null){
									req.setAttribute("msg", "Loan Account Number is Already Verified");
								}
								else if(appform.getButton_value().equalsIgnoreCase("verify")){
			        			int loanDE_result=delegate.verifyLoanMaster(loanmaster.getModuleCode(), loanmaster.getAccNo(), lnuser, lntml);
			        			System.out.println("&&&&&&&&&loanDE_result&&&&&&&&&&" + loanDE_result);
			        			if(loanDE_result>0)
			        			{
			        				System.out.println("***********Verified*********");
			        				req.setAttribute("msg", "Loan Account number is successfully verified");
			        			}
			        			else
			        			{	
			        				req.setAttribute("msg", "Loan Account number is cannot be verified");
			        				System.out.println("**********Not Verified*********");
			        			}
			        		}
			        	}
			        if (MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())) {
						path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
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
		 else if(map.getPath().equalsIgnoreCase("/Loans/LoanAmmendmentsMenu"))
			{	
				String dates[]=null;
				System.out.println("*************/Loans/LoanAmmendmentsMenu***********");
				try{
					LoansAmmendmentsForm loanammendmentform = (LoansAmmendmentsForm)form;
					  LoansDelegate delegate = new LoansDelegate();
					  if(MenuNameReader.containsKeyScreen(loanammendmentform.getPageidentity().getPageId()))
					  {
						  String path1 = MenuNameReader.getScreenProperties(loanammendmentform.getPageidentity().getPageId());
						  System.out.println("path===========>"+path1); 
						  req.setAttribute("pageId","/LoanAmmendments.jsp");
						  IntialParameters(req, delegate);
						  req.setAttribute("pay_mode", delegate.getPayMode());
						  req.setAttribute("pay_actype", delegate.getpayAcctype());
						  return map.findForward(ResultHelp.getSuccess());
					  }
					  else
					  {   
							return map.findForward(ResultHelp.getError());
					  }	
				  }
					  catch(Exception e){e.printStackTrace();}
			}
			else if(map.getPath().equalsIgnoreCase("/Loans/LoanAmmendments"))
			{	
				LoanMasterObject lnmodobj = null;
				System.out.println("*************/Loans/LoanAmmendments***********");
				try{
					  LoansAmmendmentsForm loanammendmentform = (LoansAmmendmentsForm)form;
					  LoansDelegate delegate = new LoansDelegate();
					  LoanTransactionObject tranobj=new LoanTransactionObject();
					  LoanMasterObject lnobj=new LoanMasterObject();
					  AccountObject array_accountObject[] = null;
					  lnobj=delegate.getLoanMaster(loanammendmentform.getAccno(), loanammendmentform.getAcctype());
					  if(MenuNameReader.containsKeyScreen(loanammendmentform.getPageidentity().getPageId()))
					  {
						  String path1 = MenuNameReader.getScreenProperties(loanammendmentform.getPageidentity().getPageId());
						  System.out.println("path===========>"+path1); 
						   IntialParameters(req, delegate);
						  req.setAttribute("pay_mode", delegate.getPayMode());
						  req.setAttribute("pay_actype", delegate.getpayAcctype());
						  if(loanammendmentform.getAccno()!=0)
						  {
						  tranobj=delegate.getLoanTran(String.valueOf(loanammendmentform.getAccno()),Integer.parseInt(loanammendmentform.getAcctype()));
						  if(lnobj!=null){
							  req.setAttribute("LoanDetails", lnobj);
							  req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
							  array_accountObject = new AccountObject[1];
							  if (loanammendmentform.getAccno()!=0 ) {
							  array_accountObject[0] = delegate.getAccount(null,loanammendmentform.getShtype(), loanammendmentform.getShno());
							  }
							  if (array_accountObject[0] != null)
							  {
								  System.out.println("delegate.getCustomer(array_accountObject[0].getCid()"+ delegate.getCustomer(array_accountObject[0].getCid()));
							  req.setAttribute("personalInfo", delegate.getCustomer(array_accountObject[0].getCid()));
							  }
							  if(lnobj.uv.getVerTml()!=null){
								  loanammendmentform.setShtype(lnobj.getShareAccType());
								  loanammendmentform.setShno(lnobj.getShareAccNo());
								  loanammendmentform.setPurpose(lnobj.getPurposeCode());
								  if(lnobj.isLoanSanctioned()){
									  if(lnobj.isSanctionVerified()){
										  loanammendmentform.setAmount(lnobj.getSanctionedAmount());
										  loanammendmentform.setPeriod(lnobj.getNoOfInstallments());
										  loanammendmentform.setInstallment(lnobj.getInstallmentAmt());
										  System.out.println("**********lnobj.getInstallmentAmt()**********" + lnobj.getInstallmentAmt());
										  loanammendmentform.setHoliday(lnobj.getHolidayPeriod());
										  loanammendmentform.setIntrate(lnobj.getInterestRate());
										  System.out.println("^^^^^^^^^^^^lnobj.getPrior()^^^^^^^^^^" + lnobj.getPrior());
										  if(lnobj.getPrior()==0){
							  					loanammendmentform.setPriority(String.valueOf(lnobj.getPrior()));
							  				}else{
							  					loanammendmentform.setPriority(String.valueOf(lnobj.getPrior()));
							  				}
										  if(tranobj!=null){
											  if(tranobj.uv.getVerId()!=null){
												  System.out.println("$$$$$$$$$$tranobj.getTranMode()$$$$$$$$$$" + tranobj.getTranMode());
												  if(tranobj.getTranMode().equals("C"))
														loanammendmentform.setPaymtmode("Cash");
								  					else if(tranobj.getTranMode().equals("P"))
								  						loanammendmentform.setPaymtmode("PayOrder");
								  					else if(tranobj.getTranMode().equals("T"))
								  					{
								  						loanammendmentform.setPaymtmode("Transfer");
								  						StringTokenizer stk=new StringTokenizer(tranobj.getTranNarration());
								  						System.out.println("**************stk************" + stk);
													    String a=stk.nextToken();
													    System.out.println("&&&&&&&&&&&&&&&" + a);
												        setPayAccNo(a, Integer.parseInt(stk.nextToken()),loanammendmentform);
												        loanammendmentform.setDisbleft(lnobj.getDisbursementLeft());
								  					}
												  if(lnobj.isLoanSanctioned()){
													  double disb_left=delegate.getDisbursementLeft(String.valueOf(lnobj.getModuleCode()),lnobj.getAccNo());
													  System.out.println("%%%%%%%%%%%%disb_left%%%%%%%%%%%" + disb_left);
													  loanammendmentform.setDisbleft(disb_left);
												  }
												  }
											  }
										  }
									  }
									  
								  }
							  	else
							  		{
								  req.setAttribute("msg","Loan Application is not yet Verified,Ammendments cannot be allowed");
							  	}
							  }
						  		else
						  		{
								 req.setAttribute("msg","Loan number not found");
						  		}
						  }
						  String modcode = loanammendmentform.getAcctype();
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
						  System.out.println("appform.getDetailsammend()() ========== "+ loanammendmentform.getDetailsammend());
						  System.out.println("****************Details********************");
						  if ((loanammendmentform.getDetailsammend().equalsIgnoreCase("Application"))) {
								System.out.println("+++++++++++++++Action class application form code ++++++++++++");
								System.out.println("appform.getDetails() ==========  "+ loanammendmentform.getDetailsammend());
								System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties("5060"));
								req.setAttribute("Application", MenuNameReader.getScreenProperties("5060"));
								req.setAttribute("pay_mode", delegate.getPayMode());
								req.setAttribute("pay_actype", delegate.getpayAcctype());
								String dirdetails[][]=delegate.getDirectorDetails(delegate.getSysDate());
								String[] dirrelations=delegate.getDirectorRelations();
								req.setAttribute("Dirrelations",dirrelations);
								System.out.println("***********Director details**************" + dirdetails.toString());
								System.out.println("&&&&&&&&&&&& DirDetails &&&&&&&&&&&&&7" + dirdetails[0][1]);
								req.setAttribute("DirDetails",dirdetails);
								req.setAttribute("flag", null);
								req.setAttribute("LoanDetails", lnobj);
							}

							if ((loanammendmentform.getDetailsammend().equalsIgnoreCase("Vehicle"))) {
								System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
								System.out.println("appform.getDetails() ==========2");
								req.setAttribute("flag", null);
								req.setAttribute("Vehicle", MenuNameReader.getScreenProperties("5062"));
								VehicleObject vehobj=lnobj.getVehicleDet();
								req.setAttribute("VECHOBJ", vehobj);
							}
							if ((loanammendmentform.getDetailsammend().equalsIgnoreCase("Properpty"))) {
								req.setAttribute("flag", null);
								System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
								System.out.println("appform.getDetails() ==========3");
								req.setAttribute("Property", MenuNameReader.getScreenProperties("5063"));
								PropertyObject propobj=lnobj.getPropertyDetails();
								req.setAttribute("PROPOBJ", propobj);
							}
							if (loanammendmentform.getDetailsammend().trim().equalsIgnoreCase("Relative")) {
								System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
								System.out.println("appform.getDetails() ==========5");
								req.setAttribute("flag", null);
								System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5061"));
								req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties("5061"));
								Object[][] relation=new Object[1][6];
								relation=lnobj.getRelatives();
								Object[][] indent=new Object[1][5];
								indent=lnobj.getInterests();
								Object[][] dependents=new Object[1][4];
								dependents=lnobj.getDependents();
								req.setAttribute("relation", relation);
								req.setAttribute("indent", indent);
								req.setAttribute("dependents", dependents);
								
							}
							
							  if((loanammendmentform.getDetailsammend().trim().equalsIgnoreCase("Surities"))) 
							  {
							  System.out.println("*****************Inside to Suruties***************");
							  req.setAttribute("flag", null);
							  req.setAttribute("Surities",MenuNameReader.getScreenProperties("5032"));
							  }
							if ((loanammendmentform.getDetailsammend().trim().equalsIgnoreCase("Signature Ins"))) {
								System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
								signObject = delegate.getSignatureDetails(loanammendmentform.getShno(), loanammendmentform.getShtype());
								if (signObject == null) {
								System.out.println("*************Signature Object is null**********");	
								}
								else{
									req.setAttribute("flag",MenuNameReader.getScreenProperties("0044"));
									req.setAttribute("signInfo",signObject);	  
									req.setAttribute("panelName",CommonPanelHeading.getSignature());
									}
							}
							if ((loanammendmentform.getDetailsammend().equalsIgnoreCase("Loan and Share Details"))) {
								System.out.println("appform.getDetails() ==========8");
								req.setAttribute("flag", null);
								System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
								req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
								req.setAttribute("L&DDetails", delegate.getLoanAndShareDetails(loanammendmentform.getShtype(),loanammendmentform.getShno()));
								req.setAttribute("MaxLoanAmt", delegate.getModuleTableValue("ind_max_value"));
								req.setAttribute("MaxLoanIns", delegate.getModuleTableValue("ins_max_value"));
								req.setAttribute("IndividualCoBorrower", null);
							}
							if ((loanammendmentform.getDetailsammend().trim().equalsIgnoreCase("CoBorrower"))) {
								System.out.println("appform.getDetails() ==========Co Borrower");
								System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5032"));
								req.setAttribute("flag", null);
								req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5032"));
							}
							if ((loanammendmentform.getDetailsammend().trim().equalsIgnoreCase("Gold"))) {
								System.out.println("appform.getDetails() ==========Gold");
								System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
								req.setAttribute("flag", null);
								req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
								req.setAttribute("IndividualCoBorrower", null);
							}
							System.out.println("Button clicked" + loanammendmentform.getButton_value());
							System.out.println("Application form---->"+ loanammendmentform.getAppln_no());
							String method = req.getParameter("method");
							System.out.println("************method**************" + method);
							session=req.getSession(true);
							System.out.println("___________Object status before Initialization_______"+session.getAttribute("LnMasterObject"));
							if(session.getAttribute("LnMasterObject")==null){
								System.out.println("Hi HashMap in SaveApplication is null");
								lnmodobj=new LoanMasterObject();
							    session.setAttribute("LnMasterObject", lnmodobj);
							}
							if (method != null) {
								if (method.equalsIgnoreCase("UpdateApplication")) 
								{	
									HashMap appMap=new HashMap();
									System.out.println("***********Am in the Update Application**********");
									lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
									lnmodobj.setApplicationSrlNo(loanammendmentform.getAppln_no());
									lnmodobj.setApplicationDate(loanammendmentform.getAppndate());
									lnmodobj.setRequiredAmount(loanammendmentform.getReqamount());
									System.out.println("Application Relative to director" + loanammendmentform.getRelativetodir());
									if(loanammendmentform.getRelativetodir()==null)
									{
										lnmodobj.setRelative("null");
										lnmodobj.setDirectorCode(0);
									}
									else
									{	
										lnmodobj.setRelative(loanammendmentform.getDirrelations());
										lnmodobj.setDirectorCode(loanammendmentform.getDirdetails());
										
									}
									System.out.println("appform.getPaymtmode()" + loanammendmentform.getPaymtmode());
									System.out.println("*********After setting the paymode********");
									String paymode=loanammendmentform.getPaymtmode();
									if(paymode.equalsIgnoreCase("Cash"))
									{	
										System.out.println("In cash paymode");
										lnmodobj.setPayMode(paymode);
									}
									else if(paymode.equalsIgnoreCase("Transfer"))
									{
										System.out.println("In Transfer paymode");
										System.out.println("appform.getPayactype()" + loanammendmentform.getPayactype());
										System.out.println("appform.getPayaccno()" + loanammendmentform.getPayaccno());
										lnmodobj.setPayMode(paymode);
										lnmodobj.setPaymentAcctype(loanammendmentform.getPayactype());
										lnmodobj.setPaymentAccno(loanammendmentform.getPayaccno());
									}
									else
									{	
										System.out.println("In payorder paymode");
										lnmodobj.setPayMode(paymode);
									}
									lnmodobj.setInterestType(loanammendmentform.getInteresttype());
									lnmodobj.setInterestRateType(loanammendmentform.getInterestcalctype());
									appMap.put("ApplicationDet",lnmodobj);
									session.setAttribute("ApplicationDetMap",appMap);
									System.out.println("After adding application det into Mpa size---"+appMap.size());
									req.getSession().setAttribute("LnMasterObject",lnmodobj);
								} 
								else if (method.equalsIgnoreCase("SaveVehicleDet")) {
									HashMap vehMap=new HashMap();
									lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
									VehicleObject vech = new VehicleObject();
									System.out.println("this is vehicle details");
									vech.setVehicleType(loanammendmentform.getVehicletype());
									vech.setVehicleMake(loanammendmentform.getMaketype());
									vech.setVehicleCost((loanammendmentform.getCost()));
									vech.setVehicleFor(loanammendmentform.getVehiclefor());
									vech.setLicenceNo(loanammendmentform.getLicenseno());
									vech.setLicenceValidity(loanammendmentform.getValidity());
									vech.setPermitNo(loanammendmentform.getPermitno());
									vech.setPermitValidity(loanammendmentform.getPermitvalid());
									vech.setArea(loanammendmentform.getVehiclearea());
									vech.setAddressParking(loanammendmentform.getVehicleparked());
									vech.setAddressDealer(loanammendmentform.getDealername());
									vech.setOtherDet(loanammendmentform.getOthervehicle());
									lnmodobj.setVehicleDet(vech);
									vehMap.put("VehicleDet",lnmodobj);
									session.setAttribute("VehicleDetMap",vehMap);
									System.out.println("After adding vehicle det into Mpa size---"+vehMap.size());
									req.getSession().setAttribute("LnMasterObject",lnmodobj);
								}
								else if (method.equalsIgnoreCase("SaveSignature")) {
									System.out.println("Am in the  Save Signature");
									HashMap signmap=new HashMap();
									lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
									SignatureInstructionObject[] array_sign_obj=null; 
									SignatureInstructionObject signObject=new SignatureInstructionObject();
									array_sign_obj=delegate.getSignatureDetails(loanammendmentform.getShno(), loanammendmentform.getShtype());
									System.out.println("Length of the sign object" + array_sign_obj.length);
									for(int i=0;i<array_sign_obj.length;i++){
									System.out.println("signature object" + array_sign_obj[i].getTypeOfOperation());
									}
									System.out.println("appform.getTyop()" + loanammendmentform.getTyop());
									signObject.setCid(loanammendmentform.getCid());
									signObject.setSAccNo(loanammendmentform.getAcNum());
									signObject.setSAccType(loanammendmentform.getAcType());
									signObject.setName(loanammendmentform.getName());
									signObject.setTypeOfOperation(loanammendmentform.getTyop());
									array_sign_obj[0]=signObject;
									lnmodobj.setSignatureDet(array_sign_obj);
									signmap.put("SignDet",lnmodobj);
									session.setAttribute("SignDetMap",signmap);
									System.out.println("After adding signature det into Mpa size---"+signmap.size());
									req.getSession().setAttribute("LnMasterObject",lnmodobj);
								} 
								else if(method.equalsIgnoreCase("SaveRelative")){
									HashMap relMap=new HashMap();
									lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
									Object[][] relation=new Object[1][6];
									relation[0][0]=loanammendmentform.getRelName();
									relation[0][1]=loanammendmentform.getRelDob();
									relation[0][2]=loanammendmentform.getRelTor();
									relation[0][3]=loanammendmentform.getRelTos();
									relation[0][4]=loanammendmentform.getRelTom();
									relation[0][5]=loanammendmentform.getRelTostatus();
									Object[][] indent=new Object[1][5];
									indent[0][0]=loanammendmentform.getIndName();
									indent[0][1]=loanammendmentform.getInfDob();
									indent[0][2]=loanammendmentform.getIndTos();
									indent[0][3]=loanammendmentform.getIndTom();
									indent[0][4]=loanammendmentform.getIndTostatus();
									Object[][] dependents=new Object[1][4];
									dependents[0][0]=loanammendmentform.getDepName();
									dependents[0][1]=loanammendmentform.getDepDob();
									dependents[0][2]=loanammendmentform.getDepTos();
									dependents[0][3]=loanammendmentform.getDepTor();
									lnmodobj.setRelatives(relation);
									lnmodobj.setInterests(indent);
									lnmodobj.setDependents(dependents);
									relMap.put("RelationDet",lnmodobj);
									session.setAttribute("RelationDetMap",relMap);
									}
								else if(method.equalsIgnoreCase("SaveProperty")){
									System.out.println("In the Property save option");
									HashMap PropMap=new HashMap();
									lnmodobj=(LoanMasterObject)req.getSession().getAttribute("LnMasterObject");
									PropertyObject propertyobj=new PropertyObject();
									propertyobj.setTenantsDetail(loanammendmentform.getTenant());
									propertyobj.setPropertyValue(loanammendmentform.getValue());
									propertyobj.setPropertyNo(loanammendmentform.getPropertyno());
									propertyobj.setAddress(loanammendmentform.getAddr());
									propertyobj.setMeasurementEW(loanammendmentform.getEwbyfeets());
									propertyobj.setMeasurementNS(loanammendmentform.getNsbyfeets());
									propertyobj.setEastBy(loanammendmentform.getEastby());
									propertyobj.setWestBy(loanammendmentform.getWestby());
									propertyobj.setNorthBy(loanammendmentform.getNorthby());
									propertyobj.setSouthBy(loanammendmentform.getSouthby());
									propertyobj.setPropertyAqdBy(loanammendmentform.getPropertyacquiredby());
									propertyobj.setPropertyNature(loanammendmentform.getNature());
									lnmodobj.setPropertyDetails(propertyobj);
									PropMap.put("PropertyDet", lnmodobj);
									session.setAttribute("PropertyDetMap", PropMap);
									System.out.println("After adding Property det into Mpa size---"+PropMap.size());
									req.getSession().setAttribute("LnMasterObject", lnmodobj);
								}
								else if(method.equalsIgnoreCase("SaveService")){
									
								}
								else if(method.equalsIgnoreCase("SaveBusiness"))
								{
								
								}
								else if(method.equalsIgnoreCase("SavePension"))
								{
								
								}
								else if(method.equalsIgnoreCase("SaveRent"))
								{
								
								}
							}
							System.out.println("loanammendmentform.getDisburse_submit()"+ loanammendmentform.getDisburse_submit());
							System.out.println("^^^^^^^^^^^^^^^Before Submit ammend^^^^^^^^^^^^^");
							if (loanammendmentform.getDisburse_submit() != null) {
								if (loanammendmentform.getDisburse_submit().equalsIgnoreCase("Submit")) {
									System.out.println("^^^^^^^^^^^^^^^After Submit^^^^^^^^^^^^^");
									session=req.getSession();
									HashMap submitMap=new HashMap();
									LoanMasterObject submitMasterObj=new LoanMasterObject();
									/***************************Application ********************************/
									if(req.getSession().getAttribute("ApplicationDetMap")!=null){
									System.out.println("&&&&&&&&&&In Application&&&&&&&&&");
									submitMap.put("SubmitAppDet", ((HashMap)req.getSession().getAttribute("ApplicationDetMap")).get("ApplicationDet"));
									lnmodobj=(LoanMasterObject)submitMap.get("SubmitAppDet");
									submitMasterObj.setApplicationSrlNo(lnmodobj.getApplicationSrlNo());
									submitMasterObj.setApplicationDate(lnmodobj.getApplicationDate());
									submitMasterObj.setRequiredAmount(lnmodobj.getRequiredAmount());
									//lnmodobj.setRelative(appform.getDirrelations());
									//lnmodobj.setDirectorCode(Integer.parseInt(appform.getDirdetails()));
									submitMasterObj.setRelative(lnmodobj.getRelative());
									submitMasterObj.setDirectorCode(lnmodobj.getDirectorCode());
									submitMasterObj.setPayMode(lnmodobj.getPayMode());
									submitMasterObj.setPaymentAcctype(lnmodobj.getPaymentAcctype());
									submitMasterObj.setPaymentAccno(lnmodobj.getPaymentAccno());
									submitMasterObj.setInterestType(lnmodobj.getInterestType());
									submitMasterObj.setInterestRateType(lnmodobj.getInterestRateType());
									System.out.println("__________Inside SaveRelative Application details are_______"+lnmodobj.getApplicationSrlNo());
									System.out.println("__________Inside SaveRelative Application details are_______"+lnmodobj.getApplicationDate());
									}
									/********************* Vehicle********************************************/
									if(req.getSession().getAttribute("VehicleDetMap")!=null){
									submitMap.put("SubmitVehDet", ((HashMap)req.getSession().getAttribute("VehicleDetMap")).get("VehicleDet"));
									System.out.println("_inside SaveRelative_________________-");
									submitMap.put("SubmitVehDet", ((HashMap)req.getSession().getAttribute("VehicleDetMap")).get("VehicleDet"));
									lnmodobj=(LoanMasterObject)submitMap.get("SubmitVehDet");
									VehicleObject vho=(VehicleObject)lnmodobj.getVehicleDet();
									submitMasterObj.setVehicleDet(vho);
									}
									/******************************* Relative********************************/
									if(req.getSession().getAttribute("RelationDetMap")!=null)
									{
									submitMap.put("SubmitRelDet", ((HashMap)req.getSession().getAttribute("RelationDetMap")).get("RelationDet"));
									lnmodobj=(LoanMasterObject)submitMap.get("SubmitRelDet");
									submitMasterObj.setRelatives(lnmodobj.getRelatives());
									submitMasterObj.setInterests(lnmodobj.getInterests());
									submitMasterObj.setDependents(lnmodobj.getDependents());
									}
									/****************** Property*************************************/
									if(req.getSession().getAttribute("PropertyDetMap")!=null)
									{
										System.out.println("Inside the Property Detail Submit");
										submitMap.put("SubmitRelDet", ((HashMap)req.getSession().getAttribute("PropertyDetMap")).get("PropertyDet"));
										lnmodobj=(LoanMasterObject)submitMap.get("SubmitRelDet");
										PropertyObject proobj=(PropertyObject)lnmodobj.getPropertyDetails();
										submitMasterObj.setPropertyDetails(proobj);
									}
									/************************Signature Instruction*********************/
									if(req.getSession().getAttribute("SignDetMap")!=null)
									{
										System.out.println("Inside the Signature Detail Submit");
										submitMap.put("SubmitRelDet", ((HashMap)req.getSession().getAttribute("SignDetMap")).get("SignDet"));
										lnmodobj=(LoanMasterObject)submitMap.get("SubmitRelDet");
										SignatureInstructionObject signobj[]=(SignatureInstructionObject[])lnmodobj.getSignatureDet();
										submitMasterObj.setSignatureDet(signobj);
									}
										submited_details =null;
										submited_details = new boolean[11];
										submited_details[0]= true;
										System.out.println("(((((((((((((((We will see))))))))))))");	
										if(items_relavent[1].equals("Y")){
											if(req.getSession().getAttribute("RelationDetMap")!=null)
												submited_details[1]=true;
											else{
												 req.setAttribute("msg", "Relative Details is must");
											}
										}
										else if(items_relavent[1].equals("O")){
											if(req.getSession().getAttribute("RelationDetMap")!=null)
												submited_details[1]=true;
										}
										if(items_relavent[2].equals("Y")){
											if(req.getSession().getAttribute("EmploymentDetMap")!=null)
												submited_details[2]=true;
											else{
												req.setAttribute("msg", "Employment Details is must");
											}
										}
										else if(items_relavent[2].equals("O")){
											if(req.getSession().getAttribute("EmploymentDetMap")!=null)
												submited_details[2]=true;
										}
										if(items_relavent[3].equals("Y")){
											if(req.getSession().getAttribute("ApplicationDetMap")!=null)
												submited_details[3]=true;
											else{
												req.setAttribute("msg", "Application Details is must");
											}
										}
										else if(items_relavent[3].equals("O")){
											if(req.getSession().getAttribute("ApplicationDetMap")!=null)
												submited_details[3]=true;
										}
										submited_details[4]=true; // Loan and Share details
										if(items_relavent[5].equals("Y")){
											if(req.getSession().getAttribute("SignDetMap")!=null)
												submited_details[5]=true;
											else{
												req.setAttribute("msg", "SignatureDetails is must");
											}
										}
										else if(items_relavent[5].equals("O")){
											if(req.getSession().getAttribute("SignDetMap")!=null)
												submited_details[5]=true;
										}
										if(items_relavent[6].equals("Y")){
											if(req.getSession().getAttribute("PropertyDetMap")!=null)
												submited_details[6]=true;
											else{
												req.setAttribute("msg", "Property Details is must");
											}
										}
										else if(items_relavent[6].equals("O")){
											if(req.getSession().getAttribute("PropertyDetMap")!=null)
												submited_details[6]=true;
										}
										
										if(items_relavent[7].equals("Y")){
											if(req.getSession().getAttribute("CoborrowerDetMap")!=null)
												submited_details[7]=true;
											else{
												req.setAttribute("msg", "Coborrowers Details is must");
											}
										}
										else if(items_relavent[7].equals("O")){
											if(req.getSession().getAttribute("CoborrowerDetMap")!=null)
												submited_details[7]=true;
										}
										if(items_relavent[8].equals("Y")){
											if(req.getSession().getAttribute("SuritiesDetMap")!=null)
												submited_details[8]=true;
											else{
												req.setAttribute("msg", "Surities Details is must");
											}
										}
										else if(items_relavent[8].equals("O")){
											if(req.getSession().getAttribute("SuritiesDetMap")!=null)
												submited_details[8]=true;
										}
										if(items_relavent[9].equals("Y")){
											if(req.getSession().getAttribute("VehicleDetMap")!=null)
												submited_details[9]=true;
											else{
												req.setAttribute("msg", "Vehicle Details is must");
											}
										}
										else if(items_relavent[9].equals("O")){
											if(req.getSession().getAttribute("VehicleDetMap")!=null)
												submited_details[9]=true;
										}
										if(items_relavent[10].equals("Y")){
											if(req.getSession().getAttribute("GoldDetMap")!=null)
												submited_details[10]=true;
											else{
												req.setAttribute("msg", "Gold Details is must");
											}
										}
										else if(items_relavent[10].equals("O")){
											if(req.getSession().getAttribute("GoldDetMap")!=null)
												submited_details[10]=true;
										}
										for(int i=0;i<submited_details.length;i++)
										{
											System.out.println("&&&&&&&&&&& Submitted Details&&&&&&&&&&&&" + submited_details[i]);
										}
										System.out.println("(((((((((((((((We will see After))))))))))))");	
										CustomerMasterObject cm=(CustomerMasterObject)delegate.getCustomer(array_accountObject[0].getCid());
										submitMasterObj.setCustomerId(cm.getCustomerID());
										submitMasterObj.setSubmitedDetails(submited_details);
										submitMasterObj.setShareAccNo(loanammendmentform.getShno());
										submitMasterObj.setShareAccType(loanammendmentform.getShtype());
										submitMasterObj.setModuleCode(Integer.parseInt(loanammendmentform.getAcctype()));
										submitMasterObj.uv.setUserId(lnuser);
										submitMasterObj.uv.setUserTml(lntml);
										submitMasterObj.uv.setUserDate(delegate.getSysDateTime());
										submitMasterObj.uv.setVerId(lnuser);
										submitMasterObj.uv.setVerTml(lntml);
										submitMasterObj.uv.setVerDate(delegate.getSysDateTime());
										System.out.println("LoanMaster Object Deteilss ----------"+submitMasterObj.getApplicationSrlNo());
										System.out.println("String.valueOf(lnmodobj.getModuleCode())" + String.valueOf(lnobj.getModuleCode()));
										System.out.println("loanammendmentform.getAccno()" + loanammendmentform.getAccno());
										System.out.println("loanammendmentform.getAcctype"+loanammendmentform.getAcctype());
										int rest =delegate.updateLoanMaster(submitMasterObj,String.valueOf(lnobj.getModuleCode()),loanammendmentform.getAccno() ,delegate.getSysDateTime());
										System.out.println("Loan Account Number=======================>"+ rest);
								}
							}
					  if (MenuNameReader.containsKeyScreen(loanammendmentform.getPageidentity().getPageId())) {
							path = MenuNameReader.getScreenProperties(loanammendmentform.getPageidentity().getPageId());
							IntialParameters(req, delegate);
							req.setAttribute("pageId", path);
							Vector vec_final[] = delegate.getRelevantDetails(modcode);
							req.setAttribute("RelevantDetails", vec_final[0]);
							req.setAttribute("RelevantPageId", vec_final[1]);
							return map.findForward(ResultHelp.getSuccess());
						}
					  }
				 }
				catch(Exception e){e.printStackTrace();}
			}
		return map.findForward(ResultHelp.getError());
	} // execute
	public void IntialParameters(HttpServletRequest req, LoansDelegate delegate)throws Exception {
		req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
		req.setAttribute("LoanPurpose", delegate.getLoanDesc());
		req.setAttribute("Details", delegate.getDetails());
		req.setAttribute("ShareModule", delegate.getSharemodulecode(2));
		req.setAttribute("PriorityDesc", delegate.getPriorityDesc());
	}
	public void LoanDailyPostInitialParam(HttpServletRequest req,LoansDelegate delegate, String path) throws Exception {
		System.out.println("SystemDate" + LoansDelegate.getSysDate());
		req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
		req.setAttribute("pageId", path);
		req.setAttribute("Date", LoansDelegate.getSysDate());
	}
	public void OCReportInitialParam(HttpServletRequest req,
			LoansDelegate delegate) throws Exception {
		req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
		req.setAttribute("StageCode", delegate.getStageCode());
		req.setAttribute("pageId", path);
	}
	public void OtherchargesInitailParam(HttpServletRequest req,
			LoansDelegate delegate) {
		try {
			System.out.println("In Other Charges Inital Param");
			req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
			req.setAttribute("LoanQueryDet", delegate.getLoanQueryDetails());
			req.setAttribute("pageId", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void ODReportInitialParam(HttpServletRequest req,
			LoansDelegate delegate) {
		try {
			req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
			req.setAttribute("Stages", delegate.getStageCode());
			req.setAttribute("pageId", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void NPASummaryInitalForm(HttpServletRequest req,
			LoansDelegate delegate) throws RemoteException {
		req.setAttribute("NPAProcessedDate", delegate.getNPAProcessedDate(180));
		req.setAttribute("pageId", path);
	}
	private void setErrorPageElements(String exception, HttpServletRequest req,
			String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);
	}
	private void setTabbedpaneattributes(HttpServletRequest req, String path,
			LoansDelegate loandelegate, LoanApplicationForm appform) {
		String pageAction[] = {
				"/Loans/LoanApplicationDE?tabPaneHeading=Personal&pageId"
						+ appform.getPageid(),
				"/Loans/LoanApplicationDE?tabPaneHeading=Self-Employed&pageId"
						+ appform.getPageid() };
		String tabheading[] = { "Personal", "Self-Employed" };
		String pagePath[] = { MenuNameReader.getScreenProperties("0001").trim() };
		req.setAttribute("TabHeading", tabheading);
		req.setAttribute("PageActions", pageAction);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "5012");
		System.out.println("inside method tabbed pane function");
	}
	private void getTabbedpane_for_OtherchargesTwo(HttpServletRequest req,PenalActionForm penalform) {
		System.out.println("**************hi from tabbed pane**two********************");
		System.out.println("+===========pageid in tab pane==>"+ penalform.getPageidentity().getPageId());
		String pageActions[] = {"/Loans/PenalIntFix?tabPaneHeading=Personal&pageId="+ penalform.getPageidentity().getPageId(),"/Loans/PenalIntFix?tabPaneHeading=LoanStatus&pageId="+ penalform.getPageidentity().getPageId() };
		String tabHeading[] = { "Personal", "LoanStatus" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("5018").trim() };
		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "6003");
	}
	private void getTabbedpane(HttpServletRequest req, RecoveryForm revform) {
		System.out.println("**************hi from tabbed pane**********************");
		System.out.println("+===========pageid in tab pane==>"+ revform.getPageidentity().getPageId());
		String pageActions[] = {"/Loans/LoanRecovery?tabPaneHeading=Personal&pageId="+ revform.getPageidentity().getPageId(),"/Loans/LoanRecovery?tabPaneHeading=LoanStatus&pageId="+ revform.getPageidentity().getPageId(),"/Loans/LoanRecovery?tabPaneHeading=LoanHistory&pageId="+ revform.getPageidentity().getPageId(),"/Loans/LoanRecovery?tabPaneHeading=RecoveryByTransfer&pageId="	+ revform.getPageidentity().getPageId(),"/Loans/LoanRecovery?tabPaneHeading=LoanTran&pageId="+ revform.getPageidentity().getPageId(),"/Loans/LoanRecovery?tabPaneHeading=Surity/Co-Borrower&pageId="+ revform.getPageidentity().getPageId() };
		String tabHeading[] = { "Personal", "LoanStatus", "LoanHistory","RecoveryByTransfer", "LoanTran", "Surity/Co-Borrower" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("5018").trim(),MenuNameReader.getScreenProperties("5019").trim(),MenuNameReader.getScreenProperties("5020").trim(),MenuNameReader.getScreenProperties("5021").trim(),MenuNameReader.getScreenProperties("5022").trim() };
		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "6003");
	}
	public void tabpane_relative(HttpServletRequest req,
			LoanApplicationForm appform) {System.out.println("**************hi from tabbed pane**********************");
		System.out.println("+===========page id in tab pane==>"+ appform.getPageidentity().getPageId());
		String pageActions[] = {"/Loans/LoanApplicationDE?tabPaneHeading=Relation&pageId="+ appform.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Indent&pageId="+ appform.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Dependent&pageId="+ appform.getPageidentity().getPageId() };
		String tabHeading[] = { "Relation", "Indent", "Dependent" };
		String pagePath[] = {MenuNameReader.getScreenProperties("5094").trim(),MenuNameReader.getScreenProperties("5095").trim(),MenuNameReader.getScreenProperties("5096").trim() };
		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "6003");
	}
	public void setPayPanel(RecoveryForm recoverform, double trn_amt)
			throws RemoteException {
		System.out.println("Inside setPayPanel");
		double temp_transfer_amount = trn_amt;
		lntrnobj = delegate.getQueryLoanStatus(recoverform.getAcctype(), recoverform.getAccno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDate());
		if (lntrnobj != null) {
			double max_req_amt = lntrnobj.getLoanBalance()
					+ lntrnobj.getInterestPayable()
					+ lntrnobj.getPenaltyAmount() + lntrnobj.getOtherAmount()
					- lntrnobj.getExtraIntAmount();
			if (trn_amt > max_req_amt) {	// More than required amount
			} else {
				recoverform.setExtraint(lntrnobj.getExtraIntAmount());
				System.out.println("Trn Amt========>" + trn_amt	+ "OtherChareges===>" + lntrnobj.getOtherAmount());
				if (trn_amt > lntrnobj.getOtherAmount()) {recoverform.setOthercharges(lntrnobj.getOtherAmount());
					trn_amt -= lntrnobj.getOtherAmount();
				} else {
					System.out.println("Else");
					recoverform.setOthercharges(trn_amt);
					trn_amt = 0;
				}
				if (trn_amt > lntrnobj.getPenaltyAmount()) {recoverform.setPenrate(lntrnobj.getPenaltyAmount());
					trn_amt -= lntrnobj.getPenaltyAmount();
				} else {
					recoverform.setPenrate(lntrnobj.getOtherAmount());
					trn_amt = 0;
				}
				System.out.println("Interest Payable"+ lntrnobj.getInterestPayable() + "Ext Int Amount"+ lntrnobj.getExtraIntAmount());
				if (trn_amt > (lntrnobj.getInterestPayable() - lntrnobj.getExtraIntAmount())) {
					recoverform.setInterest(lntrnobj.getPenaltyAmount());
					trn_amt -= lntrnobj.getInterestPayable();
					recoverform.setIntuptodate(lntrnobj.getIntUptoDate());
					trn_amt -= lntrnobj.getInterestPayable()- lntrnobj.getExtraIntAmount();
				} else {
					lntrn = delegate.calculateInterestForRecovery(lntrnobj.getAccType(), lntrnobj.getAccNo(), trn_amt);
					if (lntrn != null) {
						recoverform.setInterest(trn_amt);
						recoverform.setIntuptodate(lntrn.getIntUptoDate());
					} else {
						recoverform.setInterest(trn_amt);
					}
					trn_amt = 0;
				}
				if (lntrnobj.getPrincipalBalance()+ lntrnobj.getPrincipalPayable() > 0) {recoverform.setPrinciple(trn_amt);
					if (temp_transfer_amount > lntrnobj.getPrincipalBalance()
							+ lntrnobj.getInterestPayable()
							+ lntrnobj.getPenaltyAmount()
							+ lntrnobj.getOtherAmount())
						recoverform.setAdvance(temp_transfer_amount	- (lntrnobj.getPrincipalBalance()+ lntrnobj.getInterestPayable()+ lntrnobj.getPenaltyAmount() + lntrnobj.getOtherAmount()));
				} else {
					recoverform.setAdvance(trn_amt);
					recoverform.setPrinciple(0.0);
				}
			}
		}
	}
	public void LoanDefauterInitialParam(HttpServletRequest req,
			LoansDelegate delegate, String path) {
		try {
			req.setAttribute("Remainder", delegate.getRemainderSet());
			req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
			req.setAttribute("StageType", delegate.getStageCode());
			req.setAttribute("pageId", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void LoanAdminInitialParam(HttpServletRequest req,
			LoansDelegate delegate, String path) {
		try {
			System.out.println("================"
					+ delegate.getLoanmodulecode(2));
			req.setAttribute("LoanTable", delegate.getTables());
			req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
			req.setAttribute("pageId", path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setDefaulterValues(Vector defaulter_vector) {
		List list_obj = new ArrayList();
		System.out.println("Vector Size=======>" + defaulter_vector.size());
	}
	private void tabpane_employment(HttpServletRequest req,LoanApplicationForm appliform) {
		String pageActions[] = {"/Loans/LoanApplicationDE?tabPaneHeading=SelfEmployed&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Service&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Business&pageId="+ appliform.getPageidentity().getPageId(),	"/Loans/LoanApplicationDE?tabPaneHeading=Pension&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/LoanApplicationDE?tabPaneHeading=Rent&pageId="+ appliform.getPageidentity().getPageId() };
		String tabHeading[] = { "SelfEmployed", "Service", "Business","Pension", "Rent" };
		String pagePath[] = {MenuNameReader.getScreenProperties("5005").trim(),	MenuNameReader.getScreenProperties("5006").trim(),MenuNameReader.getScreenProperties("5007").trim(),MenuNameReader.getScreenProperties("5008").trim(),MenuNameReader.getScreenProperties("5009").trim() };
		req.setAttribute("TabHeading", tabHeading);
		System.out.println("tab heading"+tabHeading);
		req.setAttribute("PageActions", pageActions);
		System.out.println("pageActions"+pageActions);
		req.setAttribute("PagePath", pagePath);
		System.out.println("pagePath"+pagePath);
		
	}
	private void tabpane_employment(HttpServletRequest req,SanctionForm appliform) {
		String pageActions[] = {"/Loans/Sanctioning?tabPaneHeading=SelfEmployed&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/Sanctioning?tabPaneHeading=Service&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/Sanctioning?tabPaneHeading=Business&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/Sanctioning?tabPaneHeading=Pension&pageId="+ appliform.getPageidentity().getPageId(),"/Loans/Sanctioning?tabPaneHeading=Rent&pageId="+ appliform.getPageidentity().getPageId() };
		String tabHeading[] = { "SelfEmployed", "Service", "Business","Pension", "Rent" };
		String pagePath[] = {MenuNameReader.getScreenProperties("5005").trim(),MenuNameReader.getScreenProperties("5006").trim(),MenuNameReader.getScreenProperties("5007").trim(),MenuNameReader.getScreenProperties("5008").trim(),MenuNameReader.getScreenProperties("5009").trim() };
		req.setAttribute("TabHeading", tabHeading);
		System.out.println("tab heading"+tabHeading);
		req.setAttribute("PageActions", pageActions);
		System.out.println("pageActions"+pageActions);
		req.setAttribute("PagePath", pagePath);
		System.out.println("pagePath"+pagePath);
		
	}
	
	private void setSignatureTabPageDetails(HttpServletRequest request,
			LoanApplicationForm loanappform) {String pageAction[] = { "/Loans/LoanApplicationDE?tabPaneHeading=Signature Ins&signIndex=0&pageId="+ loanappform.getPageidentity().getPageId() };
			request.setAttribute("SignPageActions", pageAction);
		String pagePath = MenuNameReader.getScreenProperties("0044").trim();
		request.setAttribute("SignPagePath", pagePath);
		request.setAttribute("SignTabNum", "0");
		String tabHeading[] = { "Sign1", "Sign2" };
		request.setAttribute("SignTabHeading", tabHeading);
	}
	private void getTabbedpane_for_Othercharges(HttpServletRequest req,OtherChargesDEForm ocform) {
		System.out.println("**************hi from tabbed pane**********************");
		System.out.println("+===========pageid in tab pane==>"+ ocform.getPageidentity().getPageId());
		String pageActions[] = {"/Loans/OtherCharges?tabPaneHeading=Personal&pageId="+ ocform.getPageidentity().getPageId(),"/Loans/OtherCharges?tabPaneHeading=LoanStatus&pageId="+ ocform.getPageidentity().getPageId() };
		String tabHeading[] = { "Personal", "LoanStatus" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("5018").trim() };		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "6003");
	}
	void setQueryLoanStatus(String trn_date, OtherChargesDEForm ocform,LoansDelegate deligate, LoanMasterObject lmobj,HttpServletRequest req) {
		try {
			LoanTransactionObject loantran = new LoanTransactionObject();
			if (ocform.getRefno() == 0)
				loantran = delegate.getQueryLoanStatus(ocform.getAcctype(), ocform.getAcno(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDate());
			else
				loantran = delegate.getQueryLoanStatus(lmobj.getAccType(),lmobj.getAccNo(),delegate.getSysDate(),lnuser,lntml,delegate.getSysDate());
			System.out.println("Loan Transaction object===>" + loantran);
			req.setAttribute("LoanStatusInfo", loantran);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	LoanTransactionObject setTransactionObject(RecoveryForm recoveryform) {
		System.out.println("=====In setTransactionObject====");
		System.out.println("module transfer object===>" + moduleobject_transfer);
		LoanTransactionObject obj_submit = new LoanTransactionObject();
		int i = 0;
		System.out.println("Transfer account number===>"+ recoveryform.getTrf_accno());
		System.out.println("Transfer trf from===>" + recoveryform.getTrf_from());
		System.out.println("Transfer amount===>" + recoveryform.getAmount());

		return obj_submit;
	}
	public void getInitialPramforRecoveryByTransfer(HttpServletRequest req,LoansDelegate deligate) throws RemoteException {
		req.setAttribute("ModuleobjTrf", delegate.getMainModules(2,"'1002000','1007000','1015000','1014000','1017000','1018000'"));
	}
	public masterObject.general.VehicleObject getDetails(LoanApplicationForm appliform) {
		vech = null;
		if (appliform.getMaketype() != null) {
			if (appliform.getMaketype().length() > 0) {
				vech = new VehicleObject();
				vech.setVehicleType(appliform.getVehicletype());
				vech.setVehicleMake(appliform.getMaketype());
				vech.setVehicleCost((appliform.getCost()));
				vech.setVehicleFor(appliform.getVehiclefor());
				vech.setLicenceNo(appliform.getLicenseno());
				vech.setLicenceValidity(appliform.getValidity());
				vech.setPermitNo(appliform.getPermitno());
				vech.setPermitValidity(appliform.getPermitvalid());
				vech.setArea(appliform.getVehiclearea());
				vech.setAddressParking(appliform.getVehicleparked());
				vech.setAddressDealer(appliform.getDealername());
				vech.setOtherDet(appliform.getOthervehicle());
			}
		}
		return vech;
	}
	public VehicleObject getVechDetails(LoanApplicationForm appform) {
		VehicleObject vech = new VehicleObject();
		System.out.println("this is vehicle details");
		System.out.println("the cost is ======" + appform.getCost());
		vech.setVehicleType(appform.getVehicletype());
		System.out.println("Vehicletype ===" + appform.getVehicletype());
		vech.setVehicleMake(appform.getMaketype());
		System.out.println("getMaketype() ======" + appform.getMaketype());
		vech.setVehicleCost((appform.getCost()));
		System.out.println("Cost" + appform.getCost());
		vech.setVehicleFor(appform.getVehiclefor());
		System.out.println("vehicle for ====" + appform.getVehiclefor());
		vech.setLicenceNo(appform.getLicenseno());
		System.out.println("License No ====" + appform.getLicenseno());
		vech.setLicenceValidity(appform.getValidity());
		System.out.println("Validity =====" + appform.getValidity());
		vech.setPermitNo(appform.getPermitno());
		System.out.println("Permit number =====" + appform.getPermitno());
		vech.setPermitValidity(appform.getPermitvalid());
		System.out.println("Permit validity" + appform.getPermitvalid());
		vech.setArea(appform.getVehiclearea());
		System.out.println("vehicle area ====" + appform.getVehiclearea());
		vech.setAddressParking(appform.getVehicleparked());
		System.out.println("Vehicle parking area" + appform.getVehicleparked());
		vech.setAddressDealer(appform.getDealername());
		System.out.println("Dealer name ====" + appform.getDealername());
		vech.setOtherDet(appform.getOthervehicle());
		System.out.println("Other vehicle ======" + appform.getOthervehicle());
		return vech;
	}
	public Object[][] getRelDetails(LoanApplicationForm appform, String acType,
			int acNo) {
		Object relObj[][] = new Object[25][25];
		relObj[0][0] = appform.getRelName();
		System.out.println("relObj[0][1]" + relObj[0][0]);
		relObj[0][1] = appform.getRelDob();
		System.out.println("relObj[0][1]" + relObj[0][1]);
		relObj[0][2] = appform.getRelTos();
		System.out.println("relObj[0][1]" + relObj[0][2]);
		relObj[0][3] = appform.getRelTom();
		System.out.println("relObj[0][1]" + relObj[0][3]);
		relObj[0][4] = appform.getRelTom();
		System.out.println("relObj[0][1]" + relObj[0][4]);
		relObj[0][5] = appform.getRelTostatus();
		System.out.println("relObj[0][1]" + relObj[0][5]);
		return relObj;
	}
	public Object[][] getIndDetails(LoanApplicationForm appform) {
		Object relObj[][] = new Object[25][25];
		relObj[0][3] = appform.getIndName();
		System.out.println("relObj[0][1]" + relObj[0][0]);
		relObj[0][4] = appform.getInfDob();
		System.out.println("relObj[0][1]" + relObj[0][1]);
		relObj[0][5] = appform.getIndTom();
		System.out.println("relObj[0][1]" + relObj[0][2]);
		relObj[0][6] = appform.getIndTos();
		System.out.println("relObj[0][1]" + relObj[0][3]);
		relObj[0][7] = appform.getIndTostatus();
		System.out.println("relObj[0][1]" + relObj[0][4]);
		return relObj;
	}
	public Object[][] getDepDetails(LoanApplicationForm appform) {
		Object relObj[][] = new Object[25][25];
		relObj[0][3] = appform.getDepName();
		System.out.println("relObj[0][1]" + relObj[0][0]);
		relObj[0][4] = appform.getDepDob();
		System.out.println("relObj[0][1]" + relObj[0][1]);
		relObj[0][5] = appform.getDepTos();
		System.out.println("relObj[0][1]" + relObj[0][2]);
		relObj[0][6] = appform.getDepTor();
		System.out.println("relObj[0][1]" + relObj[0][3]);
		return relObj;
	}
	public int getDetailStatus(String[] items_relavent, LoansDelegate delegate,
			VehicleObject vech) {
		System.out.println("Insidethe method ========= 1"+ items_relavent.length);
		int checkDet = -1;
		if (items_relavent != null) {
			String LoanDet[] = delegate.getDetails();
			for (int i = 0; i < items_relavent.length; i++) {
				if (items_relavent[i].equals("Y")) {
					System.out.println("The Loan Detail is" + LoanDet[i]);
					if (LoanDet[i].equalsIgnoreCase("Vehicle")) {
						if (vech.getVehicleCost() == 0.0) {
							return checkDet;
						} else {
							checkDet = 2;
							return checkDet;
						}
					}
					if (LoanDet[i].equalsIgnoreCase("Relative")) {
						System.out.println("Relative data in func");
						checkDet = 2;
						return checkDet;
					}
					if (LoanDet[i].equalsIgnoreCase("Employment")) {
						System.out.println("Employment data in func");
						checkDet = 2;
						return checkDet;
					}
					if (LoanDet[i].equalsIgnoreCase("Application")) {
						System.out.println("Application data in func");
						checkDet = 2;
						return checkDet;
					}
					if (LoanDet[i].equalsIgnoreCase("Signature Ins")) {
						System.out.println("Signature data in func");
						checkDet = 2;
						return checkDet;
					}
				}
			}
		}
		return checkDet;
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
	private void setPayAccNo(String ac_type, int ac_no,LoansAmmendmentsForm loanammendform) throws Exception
	{
		ModuleObject[] tmod=delegate.getMainModules(2,"'1002000','1007000','1015000','1014000','1017000','1018000'");
		try {
				for(int i=0;i<tmod.length;i++) {
				if(tmod[i].getModuleCode().equals(ac_type)) {//loanammendform.setPayactype(payactype);
				}
			}
				loanammendform.setPayaccno(ac_no);
	       	 AccountObject payac=delegate.getAccount(ac_type,String.valueOf(ac_no),Integer.parseInt(delegate.getSysDate()));
	       	 if(payac!=null) {
				loanammendform.setName(payac.getAccname());		
				System.out.println("============Name"+payac.getAccname());
	       	 }
		} catch (Exception exe) {
			exe.printStackTrace();
		}
}
	private void setLoanInitParams(HttpServletRequest req,String path,LoansDelegate lndel)throws Exception{
	       try{
	    	   System.out.println("at 572 in setInitparam----------------->");
	    	   System.out.println("path is "+path);
	          req.setAttribute("pageId",path);
	          req.setAttribute("AcType",lndel.getAccountType(0));
	          req.setAttribute("IntroAcType",lndel.getAccountType(0));
	          req.setAttribute("Details",lndel.getDetails());
	       }catch(Exception e){
	           throw e;
	       }
	   }
} // LoanAction class
