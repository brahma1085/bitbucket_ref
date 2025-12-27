package com.scb.loans.actions;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.VehicleObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanReportObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.share.ShareCategoryObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.forms.PageIdForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.LoansDelegate;
import com.scb.designPatterns.ShareDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.loans.forms.LoanApplicationForm;
import com.scb.loans.forms.OtherChargesDEForm;
import com.scb.loans.forms.RecoveryForm;
import com.scb.props.MenuNameReader;

public class LoanAction1 extends Action {
	
	public class LoanAction extends Action {
		final Logger logger = LogDetails.getInstance().getLoggerObject("LoansDelegate");
		LoansDelegate delegate = null;
		String path = null;
		
		LoanMasterObject lmobj = null;
		LoanTransactionObject lntrnobj = null, loantran[] = null, lntrn = null,loan_trn[][] = null;
		LoanReportObject loanreportobj = null;
		LoanPurposeObject purpose[] = null;
		ModuleObject object[] = null, obj[] = null, moduleobject_transfer[] = null,array_moduleobject[] = null;;
		SignatureInstructionObject[] signObject, siob1;
		ArrayList arraylist = null;
		AccountObject am = null;
		String items_relavent[] = null;
		boolean submited_details[] = null;
		Hashtable hash_obj = new Hashtable();
		boolean flag = false;
		HttpSession session;
		private VehicleObject vech = null;
		ShareCategoryObject sharecat=null;
		ShareDelegate sharedelegate=null;

		public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res) {
			String[] str = null, str1 = null;
			Vector vec_obj = null;
			String loan = null;
			logger.info("**************IN THE ACTION CLASS**************");
			logger.info("PATH IS =============== >" + map.getPath());
			if (map.getPath().equalsIgnoreCase("/Loans/LoanApplicationMenu")) {
				System.out.println("****************/Loans/LoanApplicationMenu*************** ");
				try {
					LoanApplicationForm appform = (LoanApplicationForm) form;
					System.out.println("PageId---->"+ appform.getPageidentity().getPageId());
					LoansDelegate delegate = new LoansDelegate();
					PageIdForm pg = appform.getPageidentity();
					path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
					System.out.println("delegate.getSysDate()=======>"+ delegate.getSysDate());
					req.setAttribute("ApplicationDate", delegate.getSysDate());
					appform.setLoan_acc_no(0);
					if (MenuNameReader.containsKeyScreen(appform.getPageidentity().getPageId())) {
						try {
						path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
						System.out.println("path================" + path);
				        IntialParameters(req, delegate);
				        req.setAttribute("pageId", path);
						return map.findForward(ResultHelp.getSuccess());
						} 
						catch (Exception e) {
							setErrorPageElements("" + e, req, path);
							return map.findForward(ResultHelp.getError());
						}
					} 
					else 
					{
						return map.findForward(ResultHelp.getError());
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			else if (map.getPath().trim().equalsIgnoreCase("/Loans/LoanApplicationDE")) {
				try {
					System.out.println("***************/Loans/LoanApplicationDE**********");
					LoanMasterObject lnmodobj = null;
					LoansDelegate delegate = new LoansDelegate();
					System.out.println("inside loan action in loan application de");
					LoanApplicationForm appform = (LoanApplicationForm) form;
					System.out.println("the Acc type is ======"+ appform.getLoantype());
					/*************** Check LoanType is null*********************/
					if (appform.getLoantype() != null) 
					{
						System.out.println("the value is (07-09-2009) ====="+ appform.getLoantype());
						lnmodobj = delegate.getModName(appform.getLoantype());
						req.setAttribute("ModName", lnmodobj);
						System.out.println("Loan desc is ======"+ lnmodobj.getLoanMod());
					}
	                /******************** Check Loan number is null***********************/
					if (appform.getLnaccno() != 0) {
						System.out.println("Loan number details");
						lnmodobj = delegate.getLoanNoDetails(appform.getLnaccno());
						System.out.println("Acc type is -------"+ lnmodobj.getAccType());
						System.out.println("Share type is ------"+ lnmodobj.getShareAccType());
						System.out.println("Share No is --------"+ lnmodobj.getShareAccNo());
						System.out.println("Purpose is ---------"+ lnmodobj.getPurposeCode());
						appform.setShtype(lnmodobj.getShareAccType());
						appform.setShno(lnmodobj.getShareAccNo());
						appform.setPurpose(lnmodobj.getPurposeCode());
					}
					String button = appform.getForward();
					System.out.println("Button Clicked================" + button);
					LoansDelegate loandelegate = new LoansDelegate();
					req.setAttribute("ApplicationDate", loandelegate.getSysDate());
					System.out.println("System date==>" + loandelegate.getSysDate());
					String str12[] = loandelegate.getDetails();
					for (int ik = 0; ik < str12.length; ik++) {
						System.out.println("details is ===" + str12[ik]);
					}
					System.out.println("PageIdddd================="+ appform.getDetails());
					System.out.println("detail===" + appform.getLoantype());
					// ModuleObject[] modDescIs =
					// delegate.getMainModules(2,appform.getLoantype());

					System.out.println("String is --------- >" + str12);
					appform.setLoan_acc_no(0);
					// focus lost of ShareNo.
					System.out.println("this is shno ========"+ appform.getShno());
					/****************** Check share number is null*******************/
					if (appform.getShno() != 0) {

						System.out.println("Sh no is not equal to ZERO");
						req.setAttribute("IndividualCoBorrower", MenuNameReader.getScreenProperties("5027"));
						// req.setAttribute("Personnel",MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						req.setAttribute("panelName", CommonPanelHeading.getPersonal());
						String sh_type = "1001001";
						System.out.println("sh type====>" + sh_type + "sh num===>"+ appform.getShno());
						req.setAttribute("flag", MenuNameReader.getScreenProperties("0001"));
						AccountObject array_accountObject[] = null;
						array_accountObject = new AccountObject[1];
						if (appform.getShno() != 0 && sh_type != null) {

							array_accountObject[0] = loandelegate.getAccount(null,sh_type, appform.getShno());

							if (array_accountObject[0] == null)
							{
								System.out.println("inside the null loop ========");
								appform.setValidateShno("Share Number is not found");

							} else if (array_accountObject[0] != null) 
							{
								if (array_accountObject[0].getVerified() != null) 
								{
									// Share number not yet verified
								}

								if (array_accountObject[0] != null && !array_accountObject[0].isOtherBranch()&& !array_accountObject[0].isDirector()) {

									System.out.println("Shno" + appform.getShno()+ "Share" + appform.getShtype());
									req.setAttribute("personalInfo", loandelegate.getCustomer(array_accountObject[0].getCid()));
									// req.setAttribute("ShareDet",loandelegate.getShareDetails(appform.getShtype(),appform.getShno()));
									appform.setCid(array_accountObject[0].getCid());
									req.setAttribute("ShareAccountObject",array_accountObject);
									System.out.println("share detail in action================>"+ loandelegate.getShareDetails(appform.getShtype(),appform.getShno()));
								}

							}

						}
					}
					/****************** End of Share number checking***********************/

					if ((appform.getDetails().equalsIgnoreCase("5002"))) {
						System.out.println("+++++++++++++++Action class application form code ++++++++++++");
						System.out.println("appform.getDetails() ==========  "+ appform.getDetails());
						System.out.println("MenuNameReader.getScreenProperties(appform.getDetails().trim())"+MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						req.setAttribute("Application", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						req.setAttribute("IndividualCoBorrower", null);
						req.setAttribute("flag", null);
					}

					if ((appform.getDetails().equalsIgnoreCase("5003"))) {
						System.out.println("+++++++++++++++Action class Vehicle form code ++++++++++++");
						System.out.println("appform.getDetails() ==========2");
						req.setAttribute("flag", null);
						req.setAttribute("Vehicle", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						req.setAttribute("IndividualCoBorrower", null);

					}
					if ((appform.getDetails().equalsIgnoreCase("5004"))) {
						req.setAttribute("flag", null);
						System.out.println("+++++++++++++++Action class Property details form code ++++++++++++");
						System.out.println("appform.getDetails() ==========3");
						req.setAttribute("Property", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						req.setAttribute("IndividualCoBorrower", null);
						
					}

					if ((appform.getDetails().equalsIgnoreCase("5005"))) {
						System.out.println("+++++++++++++++Action class Employee form code ++++++++++++");
						System.out.println("appform.getDetails() ==========4");
						req.setAttribute("IndividualCoBorrower", null);
						System.out.println("swetha====================>"+ MenuNameReader.getScreenProperties(appform.getDetails()));
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
					}
					if (appform.getDetails().trim().equalsIgnoreCase("5093")) {
						System.out.println("+++++++++++++++Action class Relatives form code ++++++++++++");
						System.out.println("appform.getDetails() ==========5");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5093"));
						req.setAttribute("flag", null);
						// req.setAttribute("TabNum","0");
						// req.setAttribute("flag",MenuNameReader.getScreenProperties("6003"));
						// tabpane_relative(req,appform);
						/*
						 * if(appform.getTabPaneHeading()!=null) {
						 * if(appform.getTabPaneHeading().equalsIgnoreCase("Indent")) {
						 * req.setAttribute("TabNum","1"); } else
						 * if(appform.getTabPaneHeading().equalsIgnoreCase("Dependent")) {
						 * req.setAttribute("TabNum","2"); } }
						 */
						req.setAttribute("RelativeDet", MenuNameReader.getScreenProperties(appform.getDetails().trim()));
					}
					
					  if((appform.getDetails().trim().equalsIgnoreCase("Surities"))) 
					  {
					  System.out.println("*****************Inside to Suruties***************");
					  req.setAttribute("flag", null);
					  req.setAttribute("Surities",MenuNameReader.getScreenProperties("5032"));
					  req.setAttribute("IndividualCoBorrower",null); 
					  }
					 
					
					if ((appform.getDetails().trim().equalsIgnoreCase("Signature Ins"))) {
						req.setAttribute("flag", null);
						System.out.println("*****************Inside Signature Ins***************"+ MenuNameReader.getScreenProperties("0044"));
						signObject = loandelegate.getSignatureDetails(appform.getShno(), appform.getShtype());
						if (signObject == null) {
						System.out.println("*************Signature Object is null**********");	
						}
						else{
							
							req.setAttribute("flag",MenuNameReader.getScreenProperties("0044"));
							req.setAttribute("signInfo",signObject);	  
							req.setAttribute("panelName",CommonPanelHeading.getSignature());
							}

						req.setAttribute("IndividualCoBorrower", null);
					}
					/*SignatureInstruction signform=new SignatureInstruction();
					if(signform.getSubmitsign()=="Submit")
					{
						System.out.println("In the submit");
						System.out.println("***********Type of Operation************" +  signform.getTyop());
					}*/
					/*String method1 = req.getParameter("method1");
					System.out.println("method1==" + method1);
					if (method1 != null)
					{	
						if (method1.equalsIgnoreCase("SubmitSign")) 
						{
						System.out.println("+++++++++++++In the SubmitSign+++++++++++++");
						//LoanApplicationForm appform11 = (LoanApplicationForm) form;
						SignatureInstruction signform=new SignatureInstruction();
					    //SignatureInstructionObject signObject1[] = loandelegate.getSignatureDetails(appform.getShno(), appform.getShtype());
						System.out.println("********************** Type Of Operation***********" + signform.getTyop());
					  
					   
					   
						}
					}*/
					if ((appform.getDetails().equalsIgnoreCase("Loan and Share Details"))) {
						System.out.println("appform.getDetails() ==========8");
						req.setAttribute("flag", null);
						System.out.println("***************** inside Loan and Share Details *******************"+ MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("LoanandShareDetails", MenuNameReader.getScreenProperties("5029"));
						req.setAttribute("L&DDetails", loandelegate.getLoanAndShareDetails(appform.getShtype(),appform.getShno()));
						req.setAttribute("MaxLoanAmt", loandelegate.getModuleTableValue("ind_max_value"));
						req.setAttribute("MaxLoanIns", loandelegate.getModuleTableValue("ins_max_value"));
						req.setAttribute("IndividualCoBorrower", null);
						
					}
					if ((appform.getDetails().trim().equalsIgnoreCase("CoBorrower"))) {
						System.out.println("appform.getDetails() ==========Co Borrower");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5032"));
						req.setAttribute("flag", null);
						req.setAttribute("CoBorrower", MenuNameReader.getScreenProperties("5032"));
					}
	                  
					if ((appform.getDetails().trim().equalsIgnoreCase("Gold"))) {
						System.out.println("appform.getDetails() ==========Gold");
						System.out.println("the pageid is -------------"+ MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("flag", null);
						req.setAttribute("Gold", MenuNameReader.getScreenProperties("5097"));
						req.setAttribute("IndividualCoBorrower", null);
					}
					/*********** Modified for insertion *********************/
					System.out.println("Button clicked" + appform.getButton_value());
					System.out.println("Application from---->"+ appform.getAppln_no());
					String method = req.getParameter("method");
					System.out.println("************method**************" + method);
					Map mapobj = null;
					session = req.getSession(true);
					if(session.getAttribute("Appldetail")==null)
					{
						mapobj = new HashMap();
						session.setAttribute("Appldetail", mapobj);
					}
					if (method != null) {
						if (method.equalsIgnoreCase("Save")) 
						{
							System.out.println("-------inside save-----");
							mapobj = (HashMap)req.getSession().getAttribute("Appldetail");
							mapobj.put("loanApplication",appform); 
							}
						/************************ Done till here*********************/
						else if (method.equalsIgnoreCase("SaveVehicleDet")) {
							LoanMasterObject lm = new LoanMasterObject();
							VehicleObject vech = new VehicleObject();
							System.out.println("this is vehicle details");
							System.out.println("the cost is ======"+ appform.getCost());
							vech.setVehicleType(appform.getVehicletype());
							System.out.println("Vehicletype ==="+ appform.getVehicletype());
							vech.setVehicleMake(appform.getMaketype());
							System.out.println("getMaketype() ======"+ appform.getMaketype());
							vech.setVehicleCost((appform.getCost()));
							System.out.println("Cost" + appform.getCost());
							vech.setVehicleFor(appform.getVehiclefor());
							System.out.println("vehicle for ===="+ appform.getVehiclefor());
							vech.setLicenceNo(appform.getLicenseno());
							System.out.println("License No ===="+ appform.getLicenseno());
							vech.setLicenceValidity(appform.getValidity());
							System.out.println("Validity ====="+ appform.getValidity());
							vech.setPermitNo(appform.getPermitno());
							System.out.println("Permit number ====="+ appform.getPermitno());
							vech.setPermitValidity(appform.getPermitvalid());
							System.out.println("Permit validity"+ appform.getPermitvalid());
							vech.setArea(appform.getVehiclearea());
							System.out.println("vehicle area ===="+ appform.getVehiclearea());
							vech.setAddressParking(appform.getVehicleparked());
							System.out.println("Vehicle parking area"+ appform.getVehicleparked());
							vech.setAddressDealer(appform.getDealername());
							System.out.println("Dealer name ===="+ appform.getDealername());
							vech.setOtherDet(appform.getOthervehicle());
							System.out.println("Other vehicle ======"+ appform.getOthervehicle());
							lm.setVehicleDet(vech);
						}
						else if (method.equalsIgnoreCase("SubmitSign")) {
							//LoanMasterObject lm = new LoanMasterObject()
							//SignatureInstruction signform=new SignatureInstruction();
							SignatureInstructionObject signobj=new SignatureInstructionObject();
							//SignatureInstructionObject signObject1[] = loandelegate.getSignatureDetails(appform.getShno(), appform.getShtype());
							System.out.println("*******Type of operation**********"+appform.getTyop());
							//public boolean storeSignatureDetails(SignatureInstructionObject a[],int acno) throws RemoteException;
							signobj.setTypeOfOperation(appform.getTyop());
							//CommonDelegate commondel=new CommonDelegate();
							//commondel.storeSignatureDetails(signObject1,appform.getShno());
							
						} else if(method.equalsIgnoreCase("SaveRelative")){
							System.out.println("***************In the relative save option****************");
							//System.out.println("Relative Dependant Name"+ appform.getDepName());
							
							
						}
						else if(method.equalsIgnoreCase("SaveProperty")){
							System.out.println("****************In the Property save option************");
						}
							
							
					}
					
					System.out.println("APps++++++++++" + appform.getCoshno());
					if (appform.getCoshno() != 0) {

						int int_flag = 1;
						array_moduleobject = loandelegate.getMainModules(2,"1001000");
						System.out.println("==============>"+ array_moduleobject[Integer.valueOf(appform.getCoshtype())].getModuleCode());
						AccountObject accobj = loandelegate.getAccount(null,array_moduleobject[Integer.valueOf(appform.getCoshtype())].getModuleCode(), appform.getCoshno());
						System.out.println("Account Obj===============>" + accobj);
						if (accobj != null) {
							int loanee_cid = 0;
							if (loanee_cid > 0 && loanee_cid == accobj.getCid()) {
								appform.setCoshno(0);
								// Display Loanee cannot be Coborrower
							} else if (int_flag == 1) {
								System.out.println("*********************Vinny******************************");
								req.setAttribute("IndividualCoBorrower",MenuNameReader.getScreenProperties("5027"));
								// req.setAttribute("Personnel",MenuNameReader.getScreenProperties(appform.getDetails().trim()));
								req.setAttribute("panelName", CommonPanelHeading.getPersonal());
								req.setAttribute("personalInfo", loandelegate.getCustomer(accobj.getCid()));
								// req.setAttribute("ShareDet",loandelegate.getShareDetails(appform.getShtype(),appform.getShno()));
								// req.setAttribute("ShareAccountObject",accobj);
							}
						}
					}

					String modcode = appform.getLoantype();
					System.out.println("modcode is ============" + modcode);
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
							String acType = appform.getLoantype();
							int acNo = appform.getLnaccno();
							System.out.println("acc type is " + acType + "acc No"+ acNo);
							System.out.println("**********Inside Submit***********");
							submited_details = null;
							submited_details = new boolean[11];
							for (int i = 0; i < submited_details.length; i++) {
								System.out.println("the value is ======= 1"
										+ submited_details[i]);
								submited_details[i] = false;
							}
							submited_details[0] = true;
							if (items_relavent[1].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[1]);
								submited_details[1] = true;
							} else if (items_relavent[1].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[1]);
								submited_details[1] = true;
							}
							if (items_relavent[2].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[2]);
								submited_details[2] = false;
							} else if (items_relavent[2].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[2]);
								submited_details[2] = false;
							}
							if (items_relavent[3].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[3]);
								submited_details[3] = true;
							} else if (items_relavent[3].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[3]);
								submited_details[3] = true;
							}
							submited_details[4] = false; // Loan and Share
							// details
							if (items_relavent[5].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[5]);
								submited_details[5] = true;
							} else if (items_relavent[5].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[5]);
								submited_details[5] = true;
							}
							if (items_relavent[6].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[6]);
								submited_details[6] = false;
							} else if (items_relavent[6].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[6]);
								submited_details[6] = false;
							}
							if (items_relavent[7].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[7]);
								submited_details[7] = false;
							} else if (items_relavent[7].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[7]);
								submited_details[7] = false;
							}
							if (items_relavent[8].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[8]);
								submited_details[8] = false;
							} else if (items_relavent[8].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[8]);
								submited_details[8] = false;
							}
							if (items_relavent[9].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[9]);
								submited_details[9] = false;
							} else if (items_relavent[9].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[9]);
								submited_details[9] = false;
							}
							if (items_relavent[10].equals("Y")) {
								System.out.println("the value is ======= 2"+ items_relavent[10]);
								submited_details[10] = false;
							} else if (items_relavent[10].equals("O")) {
								System.out.println("the value is ======= 2"+ items_relavent[10]);
								submited_details[10] = false;
							}
							// Signature Instruction
							System.out.println("Signature Instruction Object====>"+ signObject);
							if (signObject != null) {
								SignatureInstructionObject siob = new SignatureInstructionObject();
								siob1 = new SignatureInstructionObject[1];
								siob.setCid(signObject[0].getCid());
								siob.setName(signObject[0].getName());
								siob.setSAccType(signObject[0].getSAccType());
								siob.setSAccNo(signObject[0].getSAccNo());
								System.out.println("Type of Operation ------ >"+ appform.getTyop());
								siob.setTypeOfOperation(appform.getTyop());
								siob1[0] = siob;
							}

							System.out.println("********Deta of Signature Det====="+ siob1);
							Map map2 = (HashMap) session.getAttribute("Appldetail");
							System.out.println("<====appl no===>"+ map2.get("apllnum"));
							System.out.println("<====Appndate===>"+ map2.get("Appndate"));
							LoanMasterObject lm = new LoanMasterObject();
							lm.setSubmitedDetails(submited_details);
							System.out.println("=======In Submit====="+ lm.getSubmitedDetails());
							System.out.println("Account type===>"+ appform.getLoantype());
							lm.setModuleCode(Integer.parseInt(appform.getLoantype()));
							lm.setApplicationSrlNo((Integer) map2.get("apllnum"));
							lm.setApplicationDate((String) map2.get("Appndate"));
							lm.setRequiredAmount((Double) map2.get("Reqamount"));
							lm.setRelative((String)map2.get("Relativetodir"));
							lm.setPayMode((String)map2.get("paymode"));
							lm.setInterestType((Integer) map2.get("Interesttype"));
							lm.setInterestRateType((Integer) map2.get("Interestcalctype"));
							lm.setCustomerId(appform.getCid());
							lm.setShareAccType(appform.getShtype());
							lm.setShareAccNo(appform.getShno());
							lm.setPurposeCode(appform.getPurpose());
							lm.setDepositAccType(null);
							lm.setDepositAccNo(0);
							lm.setInterestRate(0.00);
							lm.setPrioritySector(false);
							lm.setSexInd('N');
							lm.setRelative(null);
							lm.setDirectorCode(0);
							lm.setSanctionDate(null);
							lm.setSanctionedAmount(0.00);
							lm.setPayMode(null);
							lm.setPaymentAcctype(null);
							lm.setPaymentAccno(0);
							
							VehicleObject vech = new VehicleObject();
							vech = getVechDetails(appform);
							System.out.println("vech obj is =====" + vech);
							if (vech != null) {
								lm.setVehicleDet(vech);
							}
							Object relObj[][] = new Object[25][25];
							relObj = getRelDetails(appform, acType, acNo);
							Object indObj[][] = new Object[25][25];
							indObj = getIndDetails(appform);
							Object depObj[][] = new Object[25][25];
							depObj = getDepDetails(appform);
							if (relObj != null & indObj != null & depObj != null) 
							{
								System.out.println("Rel Obj, Ind Obj, Dep Obj is not null");
								submited_details[1] = true;
								System.out.println("submited_details"+ submited_details);
								lm.setSubmitedDetails(submited_details);
								System.out.println("submited_details---->"+ lm.getSubmitedDetails());
								lm.setRelatives(relObj);
								lm.setInterests(indObj);
								lm.setDependents(depObj);
							}
							lm.uv.setUserTml("Shwe");
							lm.uv.setUserId("LN02");
							if (siob1 != null)
								lm.setSignatureDet(siob1);
							System.out.println("LOamn==========>"+ lm.getSignatureDet());
							int check = getDetailStatus(items_relavent, delegate,vech);
							System.out.println("items_relavent =" + items_relavent+ "check =" + check);
							if (check == -1) {
								System.out.println("inside Check method =======");
								appform.setValidateShno("Fields mandatory are not filled(**)");
							} else {
								// appform.setValidateShno("Fields are filled");
								appform.setValidateShno("Fields Mandatory are filled");
								System.out.println("Inside the else loop======>");
								int rest = loandelegate.storeLoanMaster(lm, 0);
								System.out.println("Loan Account Number=======================>"+ rest);
								if (rest > 0) {
									appform.setLoan_acc_no(rest);
								} else
									appform.setLoan_acc_no(rest);
							}

						}
					}
					if (MenuNameReader.containsKeyScreen(appform.getPageidentity()
							.getPageId())) {
						System.out.println("page id is ==========="+ appform.getPageidentity().getPageId());
						path = MenuNameReader.getScreenProperties(appform.getPageidentity().getPageId());
						System.out.println("path in loan action================"+ path);
						System.out.println("page id is ---------------"+ appform.getDetails());
						System.out.println("name in loan action================="+ MenuNameReader.getScreenProperties(appform.getDetails().trim()));
						IntialParameters(req, loandelegate);
						// ModuleObject mod
						// =(ModuleObject)req.getAttribute("LoanModuleCode");
						// System.out.println("Module obj is
						// "+mod.getModuleAbbrv());
						req.setAttribute("pageId", path);
						Vector vec_final[] = loandelegate.getRelevantDet(modcode);
						req.setAttribute("RelevantDetails", vec_final[0]);
						req.setAttribute("RelevantPageId", vec_final[1]);
						// Application detail subpanel chk
						return map.findForward(ResultHelp.getSuccess());
					}

				}
				catch (Exception e) {
					e.printStackTrace();
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("" + e.toString(), req, path);
					return map.findForward(ResultHelp.getError());
				}
				
			}
			return map.findForward(ResultHelp.getSuccess());
			
			

}

public void IntialParameters(HttpServletRequest req, LoansDelegate delegate)
		throws Exception {
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

public void OCReportInitialParam(HttpServletRequest req,LoansDelegate delegate) throws Exception {
	req.setAttribute("LoanModuleCode", delegate.getLoanmodulecode(2));
	req.setAttribute("StageCode",delegate.getStageCode());
	req.setAttribute("pageId", path);
}

public void OtherchargesInitailParam(HttpServletRequest req,LoansDelegate delegate) {
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

public void RevRecoveryInitialParam(HttpServletRequest req,
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

private void getTabbedpane(HttpServletRequest req, RecoveryForm revform) {
	System.out
			.println("**************hi from tabbed pane**********************");
	System.out.println("+===========pageid in tab pane==>"
			+ revform.getPageidentity().getPageId());
	String pageActions[] = {
			"/Loans/LoanRecovery?tabPaneHeading=Personal&pageId="
					+ revform.getPageidentity().getPageId(),
			"/Loans/LoanRecovery?tabPaneHeading=LoanStatus&pageId="
					+ revform.getPageidentity().getPageId(),
			"/Loans/LoanRecovery?tabPaneHeading=LoanHistory&pageId="
					+ revform.getPageidentity().getPageId(),
			"/Loans/LoanRecovery?tabPaneHeading=RecoveryByTransfer&pageId="
					+ revform.getPageidentity().getPageId(),
			"/Loans/LoanRecovery?tabPaneHeading=LoanTran&pageId="
					+ revform.getPageidentity().getPageId(),
			"/Loans/LoanRecovery?tabPaneHeading=Surity/Co-Borrower&pageId="
					+ revform.getPageidentity().getPageId() };

	String tabHeading[] = { "Personal", "LoanStatus", "LoanHistory",
			"RecoveryByTransfer", "LoanTran", "Surity/Co-Borrower" };
	String pagePath[] = {
			MenuNameReader.getScreenProperties("0001").trim(),
			MenuNameReader.getScreenProperties("5018").trim(),
			MenuNameReader.getScreenProperties("5019").trim(),
			MenuNameReader.getScreenProperties("5020").trim(),
			MenuNameReader.getScreenProperties("5021").trim(),
			MenuNameReader.getScreenProperties("5022").trim() };

	req.setAttribute("TabHeading", tabHeading);
	req.setAttribute("PageActions", pageActions);
	req.setAttribute("PagePath", pagePath);
	req.setAttribute("pageNum", "6003");

}

public void tabpane_relative(HttpServletRequest req,
		LoanApplicationForm appform) {
	System.out
			.println("**************hi from tabbed pane**********************");
	System.out.println("+===========page id in tab pane==>"
			+ appform.getPageidentity().getPageId());
	String pageActions[] = {
			"/Loans/LoanApplicationDE?tabPaneHeading=Relation&pageId="
					+ appform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Indent&pageId="
					+ appform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Dependent&pageId="
					+ appform.getPageidentity().getPageId() };
	String tabHeading[] = { "Relation", "Indent", "Dependent" };
	String pagePath[] = {
			MenuNameReader.getScreenProperties("5094").trim(),
			MenuNameReader.getScreenProperties("5095").trim(),
			MenuNameReader.getScreenProperties("5096").trim() };
	req.setAttribute("TabHeading", tabHeading);
	req.setAttribute("PageActions", pageActions);
	req.setAttribute("PagePath", pagePath);
	req.setAttribute("pageNum", "6003");
}

public void setPayPanel(RecoveryForm recoverform, double trn_amt)
		throws RemoteException {
	System.out.println("Inside setPayPanel");
	double temp_transfer_amount = trn_amt;
	lntrnobj = delegate.getQueryLoanStatus(recoverform.getAcctype(),recoverform.getAccno(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());
	if (lntrnobj != null) {
		double max_req_amt = lntrnobj.getLoanBalance()
				+ lntrnobj.getInterestPayable()
				+ lntrnobj.getPenaltyAmount() + lntrnobj.getOtherAmount()
				- lntrnobj.getExtraIntAmount();
		if (trn_amt > max_req_amt) {
			// More than required amount
		} else {

			recoverform.setExtraint(lntrnobj.getExtraIntAmount());
			System.out.println("Trn Amt========>" + trn_amt
					+ "OtherChareges===>" + lntrnobj.getOtherAmount());
			if (trn_amt > lntrnobj.getOtherAmount()) {
				recoverform.setOthercharges(lntrnobj.getOtherAmount());
				trn_amt -= lntrnobj.getOtherAmount();
			} else {
				System.out.println("Else");
				recoverform.setOthercharges(trn_amt);
				trn_amt = 0;
			}

			if (trn_amt > lntrnobj.getPenaltyAmount()) {
				recoverform.setPenrate(lntrnobj.getPenaltyAmount());
				trn_amt -= lntrnobj.getPenaltyAmount();
			} else {
				recoverform.setPenrate(lntrnobj.getOtherAmount());
				trn_amt = 0;
			}
			System.out.println("Interest Payable"
					+ lntrnobj.getInterestPayable() + "Ext Int Amount"
					+ lntrnobj.getExtraIntAmount());
			if (trn_amt > (lntrnobj.getInterestPayable() - lntrnobj
					.getExtraIntAmount())) {
				recoverform.setInterest(lntrnobj.getPenaltyAmount());
				trn_amt -= lntrnobj.getInterestPayable();
				recoverform.setIntuptodate(lntrnobj.getIntUptoDate());
				trn_amt -= lntrnobj.getInterestPayable()
						- lntrnobj.getExtraIntAmount();
			} else {
				lntrn = delegate.calculateInterestForRecovery(lntrnobj
						.getAccType(), lntrnobj.getAccNo(), trn_amt);
				if (lntrn != null) {
					recoverform.setInterest(trn_amt);
					recoverform.setIntuptodate(lntrn.getIntUptoDate());
				} else {
					recoverform.setInterest(trn_amt);
				}
				trn_amt = 0;
			}
			if (lntrnobj.getPrincipalBalance()
					+ lntrnobj.getPrincipalPayable() > 0) {
				recoverform.setPrinciple(trn_amt);

				if (temp_transfer_amount > lntrnobj.getPrincipalBalance()
						+ lntrnobj.getInterestPayable()
						+ lntrnobj.getPenaltyAmount()
						+ lntrnobj.getOtherAmount())
					recoverform
							.setAdvance(temp_transfer_amount
									- (lntrnobj.getPrincipalBalance()
											+ lntrnobj.getInterestPayable()
											+ lntrnobj.getPenaltyAmount() + lntrnobj
											.getOtherAmount()));

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

private void tabpane_employment(HttpServletRequest req,
		LoanApplicationForm appliform) {
	String pageActions[] = {
			"/Loans/LoanApplicationDE?tabPaneHeading=SelfEmployed&pageId="
					+ appliform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Service&pageId="
					+ appliform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Business&pageId="
					+ appliform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Pension&pageId="
					+ appliform.getPageidentity().getPageId(),
			"/Loans/LoanApplicationDE?tabPaneHeading=Rent&pageId="
					+ appliform.getPageidentity().getPageId() };

	String tabHeading[] = { "SelfEmployed", "Service", "Business",
			"Pension", "Rent" };
	String pagePath[] = {
			MenuNameReader.getScreenProperties("5005").trim(),
			MenuNameReader.getScreenProperties("5006").trim(),
			MenuNameReader.getScreenProperties("5007").trim(),
			MenuNameReader.getScreenProperties("5008").trim(),
			MenuNameReader.getScreenProperties("5009").trim() };

	req.setAttribute("TabHeading", tabHeading);
	req.setAttribute("PageActions", pageActions);
	req.setAttribute("PagePath", pagePath);
	// req.setAttribute("pageNum","6003");
}

private void setSignatureTabPageDetails(HttpServletRequest request,
		LoanApplicationForm loanappform) {
	String pageAction[] = { "/Loans/LoanApplicationDE?tabPaneHeading=Signature Ins&signIndex=0&pageId="
			+ loanappform.getPageidentity().getPageId() };
	request.setAttribute("SignPageActions", pageAction);
	String pagePath = MenuNameReader.getScreenProperties("0044").trim();
	request.setAttribute("SignPagePath", pagePath);
	request.setAttribute("SignTabNum", "0");
	String tabHeading[] = { "Sign1", "Sign2" };
	request.setAttribute("SignTabHeading", tabHeading);

}

private void getTabbedpane_for_Othercharges(HttpServletRequest req,
		OtherChargesDEForm ocform) {
	System.out
			.println("**************hi from tabbed pane**********************");
	System.out.println("+===========pageid in tab pane==>"
			+ ocform.getPageidentity().getPageId());
	String pageActions[] = {
			"/Loans/OtherCharges?tabPaneHeading=Personal&pageId="
					+ ocform.getPageidentity().getPageId(),
			"/Loans/OtherCharges?tabPaneHeading=LoanStatus&pageId="
					+ ocform.getPageidentity().getPageId() };

	String tabHeading[] = { "Personal", "LoanStatus" };

	String pagePath[] = {
			MenuNameReader.getScreenProperties("0001").trim(),
			MenuNameReader.getScreenProperties("5018").trim() };

	req.setAttribute("TabHeading", tabHeading);
	req.setAttribute("PageActions", pageActions);
	req.setAttribute("PagePath", pagePath);
	req.setAttribute("pageNum", "6003");

}

// method used for fetching all the loan details and showing it in the loan
// Status form
void setQueryLoanStatus(String trn_date, OtherChargesDEForm ocform,
		LoansDelegate deligate, LoanMasterObject lmobj,
		HttpServletRequest req) {
	try {
		LoanTransactionObject loantran = new LoanTransactionObject();
		
		if (ocform.getRefno() == 0)
			loantran = delegate.getQueryLoanStatus(ocform.getAcctype(),ocform.getAcno(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());
		else
			loantran = deligate.getQueryLoanStatus(lmobj.getAccType(),lmobj.getAccNo(),delegate.getSysDate(),"VINAY","LN01",delegate.getSysDateTime());

		System.out.println("Loan Transaction object===>" + loantran);
		req.setAttribute("LoanStatusInfo", loantran);

	} catch (Exception ex) {
		ex.printStackTrace();
	}
}

// for setting all the values to Loan Transaction object and to send that
// object to storeLoanAmount()
LoanTransactionObject setTransactionObject(RecoveryForm recoveryform) {
	System.out.println("=====In setTransactionObject====");
	System.out
			.println("module transfer object===>" + moduleobject_transfer);
	LoanTransactionObject obj_submit = new LoanTransactionObject();
	int i = 0;
	System.out.println("Transfer account number===>"
			+ recoveryform.getTrf_accno());
	System.out
			.println("Transfer trf from===>" + recoveryform.getTrf_from());
	System.out.println("Transfer amount===>" + recoveryform.getAmount());

	/*
	 * for(;i<moduleobject_transfer.length;i++)
	 * if(combo_transfer_type.getSelectedItem().toString().equals(moduleobject_transfer[i].getModuleAbbrv()))
	 * break;
	 * 
	 * obj_submit.setAccType(obj_transfer.getAccType());
	 * obj_submit.setAccNo(obj_transfer.getAccNo()); if(trn_date!=null)
	 * obj_submit.setTransactionDate(trn_date); else
	 * obj_submit.setTransactionDate(MainScreen.head.getSysDate());
	 * obj_submit.setTranType(String.valueOf(moduleobject_transfer[i].getModuleCode()));
	 * obj_submit.setTranSequence(Integer.parseInt(txt_transfer_accno.getText()));
	 * obj_submit.setTransactionAmount(Double.parseDouble(txt_transfer_amount.getText()));
	 * obj_submit.setOtherAmount(Double.parseDouble(lbl_other_payable.getText()));
	 * obj_submit.setPenaltyAmount(Double.parseDouble(lbl_penal_int_payable.getText()));
	 * if(Double.parseDouble(lbl_int_payable.getText()) > 0){
	 * obj_submit.setInterestPayable(Double.parseDouble(lbl_int_payable.getText()));
	 * obj_submit.setExtraIntAmount(0); } else {
	 * obj_submit.setInterestPayable(0);
	 * System.out.println("setTransactionObject"+obj_transfer.getExtraIntAmount());
	 * obj_submit.setExtraIntAmount(obj_transfer.getExtraIntAmount()); }
	 * 
	 * 
	 * if(!flag) obj_submit.setReferenceNo(obj_transfer.getReferenceNo());
	 * else { System.out.println("testing ---------------5");
	 * 
	 * if(trf_vocher_numer!=null && trf_vocher_numer.length()>0) {
	 * System.out.println("the voucher Number is"+trf_vocher_numer);
	 * obj_submit.setReferenceNo(Integer.parseInt(trf_vocher_numer)); }
	 * System.out.println("testing ---------------6"); }
	 * obj_submit.uv.setUserTml(MainScreen.head.getTml());
	 * obj_submit.uv.setUserId(MainScreen.head.getUid());
	 * obj_submit.uv.setUserDate(MainScreen.head.getSysDate()+"
	 * "+MainScreen.head.getSysTime()); // Code added by Murugesh on
	 * 04/03/2006
	 */
	return obj_submit;
}

public void getInitialPramforRecoveryByTransfer(HttpServletRequest req,
		LoansDelegate deligate) throws RemoteException {

	req.setAttribute("ModuleobjTrf", delegate.getMainModules(2,
			"'1002000','1007000','1015000','1014000','1017000','1018000'"));
}

// Method Used for getting Vehicle detail
public masterObject.general.VehicleObject getDetails(
		LoanApplicationForm appliform) {
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
	System.out.println("Insidethe method ========= 1"
			+ items_relavent.length);
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
}
}
	
