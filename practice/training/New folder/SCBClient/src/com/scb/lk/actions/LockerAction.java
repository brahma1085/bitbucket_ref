package com.scb.lk.actions;

import exceptions.DateFormatException;
import exceptions.LockerMemberNotFoundException;
import general.Validations;

import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.lockers.LockerDetailsObject;
import masterObject.lockers.LockerMasterObject;
import masterObject.lockers.LockerTransObject;
import masterObject.termDeposit.DepositMasterObject;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.omg.CORBA.Request;

import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.ClearingDelegate;
import com.scb.designPatterns.CommonDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.LockerDelegate;
import com.scb.lk.forms.LockerAutoExtensionForm;
import com.scb.lk.forms.LockerDE;
import com.scb.lk.forms.LockerDataEntry;
import com.scb.lk.forms.LockerExtensionForm;
import com.scb.lk.forms.LockerIssueForm;
import com.scb.lk.forms.LockerOpearationReportForm;
import com.scb.lk.forms.LockerOpenClosedReportForm;
import com.scb.lk.forms.LockerOperationForm;
import com.scb.lk.forms.LockerOwnerReportForm;
import com.scb.lk.forms.LockerPassBookForm;
import com.scb.lk.forms.LockerRemainderNoticeForm;
import com.scb.lk.forms.LockerRentCollected;
import com.scb.lk.forms.LockerRentDueReport;
import com.scb.lk.forms.LockerSurrenderForm;
import com.scb.lk.forms.LockerTableForm;
import com.scb.lk.forms.LockerTypesForm;
import com.scb.lk.forms.NotSurrendered;
import com.scb.lk.forms.RateAdmin;
import com.scb.lk.forms.manu;
import com.scb.props.MenuNameReader;

public class LockerAction extends Action 
{
	private String path;

	
	public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception 
	{

		LockerDelegate lkd = new LockerDelegate();
		System.out.println("08....  yo man" + actionMapping.getPath());
		Vector vobject= new Vector();
		CustomerMasterObject cmobject;
		masterObject.general.Address address;
		LockerDetailsObject[] array_cabs;
		SignatureInstructionObject[] sObject;
		ModuleObject[] array_lockers_module=null;
		LockerDetailsObject array_lockerparamobject[]=null;
		NomineeObject nObjects;
		LockerDetailsObject[] lockerDetailsObjec;
		NomineeObject[] arrayNomineeObject;
		AccountObject accountobject;
		String layout="layout";
		HttpSession session;
		LockerDetailsObject[] array_lockers;
		FrontCounterDelegate fcDelegatenew;
		AdministratorDelegate admDelegate;
		Map user_role;
		
		session=httpServletRequest.getSession();
		String tml=(String)session.getAttribute("UserTml");
		String uid=(String)session.getAttribute("UserName");
		
		try{
    		synchronized(httpServletRequest) {
				
			
    		if(uid!=null){
    			System.out.println("m in if====>>"+uid);
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(uid,"LK");
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
    		        
    		          //setErrorPageElements("Sorry, You do not have access to this page",req,path);
    		           return actionMapping.findForward(ResultHelp.getError());
    			}
    		}
    		fcDelegatenew=new FrontCounterDelegate();
    		httpServletRequest.setAttribute("acccat",fcDelegatenew.getAccCategories());
    		httpServletRequest.setAttribute("accsubcat",fcDelegatenew.getSubCategories());
    		}
    	}
    	catch(Exception ex){ex.printStackTrace();}
		
		
			
		// Locker Table MANU NEW
		
		if(actionMapping.getPath().trim().equalsIgnoreCase("/manumenulink")) 
		{
			LockerMasterObject array_lockermasterobject[];
			System.out.println("confusing wat s real");
			manu formobj = (manu) actionForm;
			try {
				if (MenuNameReader.containsKeyScreen(formobj.getPageId())) {
					String path = MenuNameReader.getScreenProperties(formobj
							.getPageId());
					array_lockermasterobject = lkd.getAutoExtnLockers(ClearingDelegate.getSysDate());

					for (int i = 0; i < array_lockermasterobject.length; i++) {
						System.out.println("tran ac type"
								+ array_lockermasterobject[i].getLockerAcNo()
								+ "acnt type"
								+ array_lockermasterobject[i].getLockerAcType()
								+ "CLOSE DATE"
								+ array_lockermasterobject[i].getCloseDate()
								+ "MAT DATE"
								+ array_lockermasterobject[i].getMatDate()
								+ "LOCK NUM"
								+ array_lockermasterobject[i].getLockerNo()
								+ "TYPE"
								+ array_lockermasterobject[i].getLockerType());

					}
					httpServletRequest.setAttribute("details",
							array_lockermasterobject);
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (actionMapping.getPath().trim().equalsIgnoreCase("/manulink")) {
			manu formobj = (manu) actionForm;
			LockerMasterObject array_lockermasterobject[];
			try {
				if (MenuNameReader.containsKeyScreen(formobj.getPageId())) {
					String path = MenuNameReader.getScreenProperties(formobj
							.getPageId());

					System.out.println("LOckerType" + formobj.getLkTypes()
							+ "CAB no" + formobj.getCabNo());
					String acnt = formobj.getAccountNum();
					System.out.println("&**&**&&&**&ACOUNT NUMB" + acnt);

					if (acnt != null) {

						lkd.fetchAccountDetails(acnt, "1009000");

					}
					array_lockermasterobject = lkd.getAutoExtnLockers(ClearingDelegate.getSysDate());
					httpServletRequest.setAttribute("details",array_lockermasterobject);

					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Locker Auto Extension
		if (actionMapping.getPath().trim().equalsIgnoreCase("/LKAutoExtensionMenuLink")) 
		{
			LockerAutoExtensionForm autoformobj = (LockerAutoExtensionForm)actionForm;
			try 
			{
				if(MenuNameReader.containsKeyScreen(autoformobj.getPageId())) 
				{
					String path = MenuNameReader.getScreenProperties(autoformobj.getPageId());
					System.out.println("I'm Frm Auto Extensiom MENU:");
				
						LockerMasterObject array_lockermasterobject[];
						array_lockermasterobject =lkd.getAutoExtnLockers(ClearingDelegate.getSysDate());
						
						if(array_lockermasterobject!=null)
						{
							httpServletRequest.setAttribute("details",array_lockermasterobject);
						}
						else
						{	
						      autoformobj.setEligibleAcntNum("Records Not Found");
						}
						
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (actionMapping.getPath().trim().equalsIgnoreCase("/LKAutoExtensionLink")) 
		{
			LockerAutoExtensionForm autoformobj = (LockerAutoExtensionForm) actionForm;
			try 
			{
				
				if (MenuNameReader.containsKeyScreen(autoformobj.getPageId())) 
				{
					String path = MenuNameReader.getScreenProperties(autoformobj.getPageId());

					System.out.println(autoformobj.getAcntNum1()+ "<--I'm Frm Auto Extension LINK-->"+ autoformobj.getForward() + "@@@@@@@@@"+ autoformobj.getLkNumType());

					LockerMasterObject array_lockermasterobject[];
					
					array_lockermasterobject = lkd.getAutoExtnLockers(ClearingDelegate.getSysDate());
						
					  if(autoformobj.getForward()!= null && (!autoformobj.getForward().equalsIgnoreCase(""))) 
					  {

						System.out.println("####--->####--Acnum->"+ autoformobj.getDummyAcntNum());

						if(autoformobj.getForward().equalsIgnoreCase("extend")) 
						{
							
							int proceed = 0;
							boolean continue_transaction = false;

							try 
							{
								proceed = lkd.checkDailyStatus(LockerDelegate.getSysDate(),1);
							} 
							catch (Exception ex) 
							{
								ex.printStackTrace();
							}

							System.out.println("proceed = " + proceed);
							
							if (proceed == 3) 
							{
								autoformobj.setEligibleAcntNum("Day Closed You Can't Do Any Transactions");
								continue_transaction = false;

							} 
							else if (proceed == 2) 
							{
								autoformobj.setEligibleAcntNum("GL Posting Has Been Done");
								continue_transaction = false;
							}
							else if(proceed==-1)
							{ 
								autoformobj.setEligibleAcntNum("Error :No Entry In Daily Status For Today");
								continue_transaction = false; 
							}
							else 
							{
								continue_transaction = true;
							}
							if (continue_transaction == true) 
							{
								try 
								{

									System.out.println("Continue Tran");

									System.out.println(autoformobj.getLkNumType()+ "<|--this must b selected tokenizer--->");

									StringTokenizer extendAcNum = new StringTokenizer(autoformobj.getLkNumType(), "-");

									extendAcNum.nextElement();// it gives
									// "Extend" but
									// not
									// interested

									String acn = String.valueOf(extendAcNum.nextElement());

									int extendAcNum1 = Integer.parseInt(acn); // here
									// comes
									// locker
									// number

									String lockerType = String.valueOf(extendAcNum.nextElement());// here
									// comes
									// locker
									// type

									System.out.println("----------->"+ extendAcNum1 + "------->"+ lockerType);

									// big code goes here

									LockerMasterObject lockermasterobject = lkd.getLockerMaster(extendAcNum1, 5);
									ModuleObject array_moduleobject_lockers[] = lkd.getMainModules(1);

									int req_mths = lockermasterobject.getReqdMonths();
									int req_days = lockermasterobject.getRequiredDays();

									int total_days = (req_mths * 30) + req_days;
									
									System.out.println("--master length-->"+array_moduleobject_lockers.length);
									
									for (int i = 0; i<array_moduleobject_lockers.length; i++) 
									{
										if (array_moduleobject_lockers[i].getModuleCode().equals("1009001")) 
										{
											int lk_min_period = array_moduleobject_lockers[i].getMinPeriod();
											String mt_dt = null;
											double locker_rent = 0.0;

											System.out.println("total_days = "+ total_days);
											System.out.println("lk_min_period = "+ lk_min_period);

											if(total_days >= lk_min_period) 
											{
												System.out.println("total_days>=lk_min_period");

												mt_dt = lkd.getFutureDayDate(lockermasterobject.getMatDate(),total_days);
												lockermasterobject.setReqdMonths(req_mths);
												lockermasterobject.setRequiredDays(req_days);

												lockermasterobject.setLockerAcNo(extendAcNum1);
												lockermasterobject.setLockerAcType("1009001");

												locker_rent = lkd.getRent(lockerType, total_days,0, LockerDelegate.getSysDate());
												lockermasterobject.setRentColl(locker_rent);

												System.out.println("locker rent mat_date = "+ mt_dt+ "--->"+ locker_rent);
												lockermasterobject.setMatDate(mt_dt);
												lockermasterobject.setRentUpto(mt_dt);
												lockermasterobject.uv.setUserId(uid);
												lockermasterobject.uv.setUserTml(tml);
												lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
												lockermasterobject.uv.setVerId(uid);
												lockermasterobject.uv.setVerTml(tml);
												lockermasterobject.uv.setVerDate(LockerDelegate.getSysDate());

												lockermasterobject.setTrnDate(LockerDelegate.getSysDate());

												if (lkd.updateLocker(lockermasterobject, 6))
													autoformobj.setEligibleAcntNum("Locker Extended To "+ lk_min_period+ " Days");
												else
													autoformobj.setEligibleAcntNum("Locker Not Extended");

											} 
											else if (total_days < lk_min_period) 
											{
												System.out.println("total_days<lk_min_period");
												// 

												lockermasterobject.setLockerAcNo(extendAcNum1);
												lockermasterobject.setLockerAcType("1009001");

												mt_dt = lkd.getFutureDayDate(lockermasterobject.getMatDate(),lk_min_period);
												
												lockermasterobject.setReqdMonths(0);
												lockermasterobject.setRequiredDays(lk_min_period);

												locker_rent = lkd.getRent(lockerType, total_days,0, LockerDelegate.getSysDate());
												
												System.out.println("Locker Rent-->"+locker_rent);
														
												lockermasterobject.setRentColl(locker_rent);

												System.out.println("locker rent mat_date = "+ mt_dt);
												lockermasterobject.setMatDate(mt_dt);
												lockermasterobject.setRentUpto(mt_dt);
												lockermasterobject.uv.setUserId(uid);
												lockermasterobject.uv.setUserTml(tml);
												lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
												lockermasterobject.uv.setVerId(uid);
												lockermasterobject.uv.setVerTml(tml);
												lockermasterobject.uv.setVerDate(LockerDelegate.getSysDate());

												lockermasterobject.setTrnDate(LockerDelegate.getSysDate());

												if (lkd.updateLocker(lockermasterobject, 6))
													autoformobj.setEligibleAcntNum("Locker Extended To "+ lk_min_period+ " Days");
												else
													autoformobj.setEligibleAcntNum("locker not extended");

											}
										}
									}

									// big code ends here

									//autoformobj.setEligibleAcntNum("Extended successfully");

								} 
								catch(Exception e) 
								{
									e.printStackTrace();
								}

							}
							else
							{
								
							}
						}

						// here taken code
					   }
					
					
					  else if(autoformobj.getDummyAcntNum() != null && (!autoformobj.getDummyAcntNum().equalsIgnoreCase(""))) 
					  {

						LockerMasterObject lockerMasterObject = lkd.getLockerMaster(Integer.parseInt(autoformobj.getDummyAcntNum()), 5);
						
						System.out.println("#### $$$--->####"+ lockerMasterObject);
						if (lockerMasterObject != null) 
						{
							ModuleObject[] array_moduleobject_lockers = null;
							int locker_max_renewal_period = 0;

							array_moduleobject_lockers = lkd.getMainModules(1);
							
							for(int m=0; m< array_moduleobject_lockers.length; m++) 
							{
								if(array_moduleobject_lockers[m].getModuleCode().equals("1009001"))
								{
									locker_max_renewal_period = array_moduleobject_lockers[m].getMaxRenewalCount();
									System.out.println("Module Code--->"+array_moduleobject_lockers[m].getModuleCode());
									System.out.println("Max Renewal Period-->"+locker_max_renewal_period);
									System.out.println("Maturity Date-->"+lockerMasterObject.getMatDate());
								}	
							}

							if(Generic.dayCompare(lockerMasterObject.getMatDate(),LockerDelegate.getSysDate())>locker_max_renewal_period) 
							{
								autoformobj.setEligibleAcntNum("Locker Expired");
								System.out.println("LockerSucks");
							} 
							else if(lockerMasterObject.getFreezeInd().equals("T")) {
								autoformobj.setEligibleAcntNum("Freezed");
								System.out.println("Freezed");

							}
							else 
							{
								autoformobj.setEligibleAcntNum("");
								String perPath = MenuNameReader.getScreenProperties("0001");
								String cid = String.valueOf(lockerMasterObject.getCid());
								String[] lkNumAc = new String[3];

								System.out.println("*******"+ lockerMasterObject.getLockerAcNo()+ "*****"+ lockerMasterObject.getLockerNo());

								lkNumAc[0] = String.valueOf(lockerMasterObject.getLockerAcNo());
								lkNumAc[1] = String.valueOf(lockerMasterObject.getLockerNo());
								lkNumAc[2] = lockerMasterObject.getLockerType();
								System.out.println(lkNumAc + "<--/-->"+ lkNumAc[0] + "<---/---->"+ lkNumAc[1] + "<---/--->"+ lkNumAc[2]);

								cmobject = lkd.getCustomer(Integer.parseInt(cid));
								httpServletRequest.setAttribute("lkDetail",lkNumAc);
								httpServletRequest.setAttribute("personalInfo",cmobject);
								httpServletRequest.setAttribute("flag", perPath);
								httpServletRequest.setAttribute("panelName",CommonPanelHeading.getPersonal());
								System.out.println("<0--yes flag is set--0>");

							}

						} 
						else 
						{

							autoformobj.setEligibleAcntNum("No Records found");
							System.out.println("masterIsNull");
							// dude LockMas is null
						}
					}

					httpServletRequest.setAttribute("details",array_lockermasterobject);
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
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
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward(ResultHelp.getSuccess());
			}
		}

		// For locker surrender
		if (actionMapping.getPath().trim().equalsIgnoreCase("/LKSurrenderMenuLink")) 
		{
			LockerSurrenderForm lkformobj = (LockerSurrenderForm) actionForm;

			try 
			{
				if(MenuNameReader.containsKeyScreen(lkformobj.getPageId())) 
				{
					String path = MenuNameReader.getScreenProperties(lkformobj.getPageId());
					System.out.println("wen i gone thro Reader" + path);
					getLkAbbr(httpServletRequest, path, lkd);
					lkformobj.setTxt_acNo(String.valueOf(0));
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("disability"," ");
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());

				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerSurrenderLink")) 
		{
			
			try 
			{
				LockerSurrenderForm lkSurrenderformobj =(LockerSurrenderForm)actionForm;
				lkSurrenderformobj.setValidateFlag("");  
				AccountMasterObject amobj;
				lkSurrenderformobj.setVal("xxx");
				LockerMasterObject lockermasterobject;
				String Disable=null;
				System.out.println("********************surrender menu$$$$$$$$$$$");
				
				if(MenuNameReader.containsKeyScreen(lkSurrenderformobj.getPageId())) 
				{
					String path = MenuNameReader.getScreenProperties(lkSurrenderformobj.getPageId());
					System.out.println("My button name is "+ lkSurrenderformobj.getForward() + "page name is"+ path);
					System.out.println("##############**surrender menu##########");
					
					if(lkSurrenderformobj.getForward().equalsIgnoreCase("submit")) 
					{
						if(lkSurrenderformobj.getTxt_lockNo()!= null) 
						{
							System.out.println("%%%%--values are--%%%%");
							System.out.println("Account type"+ lkSurrenderformobj.getTxt_acType()+ "LkAcNum"+ lkSurrenderformobj.getTxt_acNo()+ "LkType"+ lkSurrenderformobj.getTxt_lockType()+ "Rent Uptooo"+ lkSurrenderformobj.getTxt_rentUpto()+ "GetSysDate,,"+ LockerDelegate.getSysDate());

							LockerTransObject lockertransobject = new LockerTransObject();
							lockertransobject.setLockerAcType(lkSurrenderformobj.getTxt_acType());
							lockertransobject.setLockerAcNo(Integer.parseInt(lkSurrenderformobj.getTxt_acNo()));
							String s = lkSurrenderformobj.getTxt_lockNo();

							int n = Integer.parseInt(s);
							System.out.println("locker num" + n);
							lockertransobject.setLockerNo(n);
							lockertransobject.setLockerType(lkSurrenderformobj.getTxt_lockType());
							lockertransobject.setRentUpto(lkSurrenderformobj.getTxt_rentUpto());
							lockertransobject.setTrnDate(LockerDelegate.getSysDate());

							lockertransobject.uv.setUserId(uid);
							lockertransobject.uv.setUserTml(tml);
							lockertransobject.uv.setUserDate(LockerDelegate.getSysDate());

							int flag = lkd.surrender(lkSurrenderformobj.getTxt_acType(),lkSurrenderformobj.getTxt_rentUpto(),lockertransobject,4);

							if(flag==1) 
							{
								lkSurrenderformobj.setValidateFlag("Locker Surrendered Successfully");
								System.out.println("OOOOO 'm succseeeee");
							}
							if(flag==2) 
							{
								lkSurrenderformobj.setValidateFlag("Locker Not Surrendered");
								System.out.println("Ahhhhh failure again");
								
							}
							if(flag==3)
							{
								lkSurrenderformobj.setValidateFlag("Rent Collection Due");
							}
						}
						httpServletRequest.setAttribute("pageId",path);
						getLkAbbr(httpServletRequest, path, lkd);
						return actionMapping.findForward(ResultHelp.getSuccess());

					}
					else if (lkSurrenderformobj.getForward().equalsIgnoreCase("delete")) 
					{
						lkSurrenderformobj.setForward("");
						System.out.println("..hi 'm here inside delete..");
						LockerTransObject lockertransobject = new LockerTransObject();

						lockertransobject.setLockerAcType(lkSurrenderformobj.getTxt_acType());
						lockertransobject.setLockerAcNo(Integer.parseInt(lkSurrenderformobj.getTxt_acNo()));
						System.out.println("locker ac num--->"+lkSurrenderformobj.getTxt_acNo());
						String s = lkSurrenderformobj.getTxt_lockNo();
						int n = Integer.parseInt(s);
						lockertransobject.setLockerNo(n);
						lockertransobject.setLockerType(lkSurrenderformobj.getTxt_lockType());
						lockertransobject.setRentUpto(lkSurrenderformobj.getTxt_rentUpto());
						lockertransobject.setTrnDate(lkd.getSysDate());
						lockertransobject.uv.setUserId(uid);
						lockertransobject.uv.setUserTml(tml);
						lockertransobject.uv.setUserDate(lkd.getSysDate());

						if(lkd.deleteLockerTransaction(lockertransobject,2)) 
						{
							lkSurrenderformobj.setValidateFlag("Deleted Successfully");
							
							System.out.println("deleted successfully--->");
						}
						else 
						{
							lkSurrenderformobj.setValidateFlag("Failure in Deleting");
							System.out.println("failure in deleting-->");
						}
						httpServletRequest.setAttribute("pageId",path);
						getLkAbbr(httpServletRequest, path, lkd);
						return actionMapping.findForward(ResultHelp.getSuccess());

					}
					else if (lkSurrenderformobj.getForward().equalsIgnoreCase("fromAcntNum")) 
					{

						String acnum1 = lkSurrenderformobj.getTxt_acNo();
						int acnum = Integer.parseInt(acnum1);
						lkSurrenderformobj.setValidateFlag("");
						lockermasterobject = lkd.getLockerMaster(acnum, 4);
						System.out.println("----lockermasterobject----"+ lockermasterobject);
						
						if (lockermasterobject != null) 
						{
							if (lockermasterobject.uv.getVerId() != null) 
							{
								System.out.println("<-#### --getVerId---10-->");

								if (lockermasterobject.getCloseDate() == null) 
								{
									System.out.println("<-@@@@@--getCloseDate----->");
									if(lockermasterobject.getFreezeInd()!=null)
									{
									 if(lockermasterobject.getFreezeInd().equalsIgnoreCase("F")) 
									 {	
										
										  if(lockermasterobject.getTrnType().equals("C"))
										  {
											  Disable="Disable";
												System.out.println("Inside Not Surrender");
											  		  //lkSurrenderformobj.setDisableBut("T"); 
										  }
										  else
										  {
											  Disable="NotDisable";
											  System.out.println("Inside  Surrender");
										  }
											
										lkSurrenderformobj.setValidateFlag("");
										System.out.println("/---getFreezeInd-----/");
										System.out.println("My button HIIIHIHIHIH name is "+ lkSurrenderformobj.getSubmit()+ "Locker Number iss^^"+ lockermasterobject.getLockerNo()+ "key$$$"+ lockermasterobject.getKeyNo());
										System.out.println("alloted date"+ lockermasterobject.getAllotDate()+ "***"+ lockermasterobject.getLockerAcType()+ "**"+ lockermasterobject.getLockerAcNo());
										System.out.println(lockermasterobject.getRentColl()+ "**"+ lockermasterobject.getRentUpto()+ "***"+ lockermasterobject.getIntrAcNo()+ "***"+ lockermasterobject.getIntrAcTy());

										String pageid = lkSurrenderformobj.getPageId();

										System.out.println("Page id is"+ pageid + "path is" + path);
										System.out.println("I Selected frm Select Box"+ lkSurrenderformobj.getSelect_details());
										System.out.println("%%%%%&&&&&&&$$$$$$");
										System.out.println("^^^^^values are^^^^^");
										System.out.println("Account type"+ lkSurrenderformobj.getTxt_acType()+ "LkAcNum"+ lkSurrenderformobj.getTxt_acNo()+ "LkType"+ lkSurrenderformobj.getTxt_lockType()+ "Rent Uptooo"+ lkSurrenderformobj.getTxt_rentUpto()+ "GetSysDate,,"+ LockerDelegate.getSysDate());
										System.out.println("ZZZZZZZ"+ lkSurrenderformobj.getForward());

										int IntacNum = lockermasterobject.getIntrAcNo();
										String IntacType = lockermasterobject.getIntrAcTy();
										System.out.println("intttnumm"+ IntacNum + "accctype"+ IntacType);

										ModuleObject modobj[] = lkd.getMainModules("1009000");
										String details = lkSurrenderformobj.getSelect_details();
										lockermasterobject = lkd.getLockerMaster(acnum, 4);
										
										
										if(!lkSurrenderformobj.getSelect_details().trim().equals("SELECT")) 
										{

											if(lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("personal")) 
											{
												System.out.println("after personal");
												String perPath = MenuNameReader.getScreenProperties("0001");
												int cid = lockermasterobject.getCid();
												cmobject = lkd.getCustomer((cid));
												httpServletRequest.setAttribute("personalInfo", cmobject);
												httpServletRequest.setAttribute("flag",perPath);
												httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
												System.out.println("flag gonna set");

											}
											if(lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("Introducer")) 
											{

												    System.out.println("inside introducer-=-=---===-");
													int intacNum = (lockermasterobject.getIntrAcNo());
													String intacType = lockermasterobject.getIntrAcTy();
													accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
													if(accountobject!=null)
													{
														cmobject = lkd.getCustomer(accountobject.getCid());
														httpServletRequest.setAttribute("personalInfo", cmobject);
														String perPath = MenuNameReader.getScreenProperties("0001");
														httpServletRequest.setAttribute("flag",perPath);
														httpServletRequest.setAttribute("panelName", CommonPanelHeading.getIntroucer());
													}
													else
													{
													 lkSurrenderformobj.setValidateFlag("Introducer Account Num Found");
													}	
											
											  }
											  if(lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("signature")) 
											  {
											
														System.out.println("member numm---"+lockermasterobject.getMemberNo());
														System.out.println("member typp==="+lockermasterobject.getShareCode());
																
														sObject= lkd.getSignatureDetails(lockermasterobject.getMemberNo(),lockermasterobject.getShareCode().trim());
														if(sObject!=null)
														{
															String perPath = MenuNameReader.getScreenProperties("0007");
															cmobject = lkd.getCustomer(lockermasterobject.getCid());
															httpServletRequest.setAttribute("personalInfo", cmobject);
															httpServletRequest.setAttribute("signInfo", sObject);
															httpServletRequest.setAttribute("flag",perPath);
															httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
															System.out.println("flag gonna set");
														}
														else
														{
															lkSurrenderformobj.setValidateFlag("Signature Details Not Found");
														}
												}
												if (lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("Nominee")) 
												{
											
														System.out.println("after Nominee");
														String perPath = MenuNameReader.getScreenProperties("0002");
														NomineeObject[] objects=lockermasterobject.getNomineeDetails();
														if(objects!=null)
														{
															httpServletRequest.setAttribute("flag",perPath);
															httpServletRequest.setAttribute("nomineeDetails",objects);
														}
														else
														{
															lkSurrenderformobj.setValidateFlag("Nominee ID Not Found");
														}		
																
												}
												if(lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("Deposit"))
												{
														String actype=null;
														String accnum=null;
															System.out.println("after deposit");
															if(lockermasterobject.getNoOfSecurities()>0)
															{
																Vector vector_deposits = null;
								                                 if(lockermasterobject.getDeposits()!=null)
										                        {
										                           
										                            vector_deposits=lockermasterobject.getDeposits();
										                            System.out.println("lockermasterobject.getDeposits size = "+vector_deposits.size());
										                            Enumeration enumeration=vector_deposits.elements();
										                            while(enumeration.hasMoreElements())
										                            {
										                            	StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
										                            	actype=tokenizer.nextElement().toString();
										                            	int int_acno=Integer.parseInt(tokenizer.nextToken());
										                            	accnum=String.valueOf(int_acno);
										                            	System.out.println("inside deposit -acNum---surrender--"+accnum);
										                            	System.out.println("inside deposit -acTyp---surrender--"+actype);
										                            }
										                            String perPath=MenuNameReader.getScreenProperties("9035");
										                            try
								    	     	   	 			 	{
								    	     	   	 			 		DepositMasterObject object=lkd.getDepositMaster(actype,Integer.parseInt(accnum));
								    	     	   	 			 		System.out.println("deposit master object==>"+object);	
								    	     	   	 			 		int dcid=object.getCustomerId();
								    	     	   	 			 		System.out.println("customer id--->"+dcid);
								    	     	   	 			 		cmobject=lkd.getCustomer(dcid);
								    	     	   	 			 		httpServletRequest.setAttribute("personalInfo",cmobject);
								    	     	   	 			 		httpServletRequest.setAttribute("depositInfo",object);
								    	     	   	 			 		httpServletRequest.setAttribute("DepNumInfo",accnum);
								    	     	   	 			 		httpServletRequest.setAttribute("DepTypInfo", actype);
								    	     	   	 			 	}
								    	     	   	 			 	catch (Exception e) 
								    	     	   	 			 	{
								    	     	   	 			 		System.out.println("inside exception deposit");
								    	     	   	 			 		lkSurrenderformobj.setValidateFlag("Account Num Not Found");
								    	     	   	 			 		e.printStackTrace();
								    	     	   	 			 	}
										                            
								    	     	   	 			 	httpServletRequest.setAttribute("flag",perPath);
								    	     	   	 			 	httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
						   	     	   	 		 
										                        }
								                                
															}
															else
															{
																lkSurrenderformobj.setValidateFlag("Deposit Details Not Found");
															}
								
													  }
													  if(lkSurrenderformobj.getSelect_details().trim().equalsIgnoreCase("JointHolders"))
													  {
														  		int[] i=lockermasterobject.getJointCid();
														  		
														  	if(i!=null)
														  	{
																cmobject=lkd.getCustomer((i[0]));
																
																if(cmobject!=null)
																{		
																   String perPath = MenuNameReader.getScreenProperties("0001");
																   httpServletRequest.setAttribute("flag",perPath);
																   httpServletRequest.setAttribute("panelName", CommonPanelHeading.getJointHolder());
																   httpServletRequest.setAttribute("personalInfo",cmobject);
																   System.out.println("flag gonna set");
															    }
															    else
															    {
																  lkSurrenderformobj.setValidateFlag("CustomerID Not Found");
															    }
														  	}
														  	else
														  	{
														  		lkSurrenderformobj.setValidateFlag("Joint Holder Details Not Found");
														  	}
													   }
													}
										
												httpServletRequest.setAttribute("param", modobj);
												httpServletRequest.setAttribute("lkparams", lockermasterobject);
									}
									else
									{
										lkSurrenderformobj.setValidateFlag("Account Freezed");
										lkSurrenderformobj.setTxt_acNo("");
									}
								  }		
								}
								else
								{
									lkSurrenderformobj.setValidateFlag("Account Closed");
									lkSurrenderformobj.setTxt_acNo("");
								}	
							}
							else
							{
								lkSurrenderformobj.setValidateFlag("Not verified");
								lkSurrenderformobj.setTxt_acNo("");
							}	
							
							
						} // eOf LLK MASTER OBJ

						else {
							lkSurrenderformobj.setValidateFlag("Records Not Found");
							lkSurrenderformobj.setTxt_acNo("");
						}

						getLkAbbr(httpServletRequest, path, lkd);
						
					}
					
					System.out.println("At the ENDDDDDD");
					getLkAbbr(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("Disabled",Disable);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());

				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			// return actionMapping.findForward(LKSurrender);
		} // end of lksurrender

		// Locker types**********************

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKTypesMenuLink")) 
		{
			System.out.println("xsxsxsxsxsx");
			LockerTypesForm lktypesformobj = (LockerTypesForm) actionForm;
			System.out.println("xsxsxsxsxsx");
			
			if (MenuNameReader.containsKeyScreen(lktypesformobj.getPageId())) {
				
				System.out.println("xsxsxsxsxsx");
				String path = MenuNameReader.getScreenProperties(lktypesformobj.getPageId());
				System.out.println("my page id is" + lktypesformobj.getPageId()+ "PATH Iss" + path);

				getLockersTypes(httpServletRequest, path, lkd);
				httpServletRequest.setAttribute("pageId", path);
				System.out.println("after setting");
				return actionMapping.findForward(ResultHelp.getSuccess());
				// httpServletRequest.setAttribute("pageId",lktypesformobj.getPageId());

			}
		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKTypesLink")) 
		{
			LockerTypesForm lktypesformobj = (LockerTypesForm) actionForm;
			String height;
			String length;
			String depth;
			String type;
			lktypesformobj.setValidateFlag(null);
			type = lktypesformobj.getCombo_types();
			System.out.println("$$$" + type);

			if (MenuNameReader.containsKeyScreen(lktypesformobj.getPageId())) 
			{

				System.out.println("Length ++"+ lktypesformobj.getCombo_types().length());
				System.out.println("Butt name is" + lktypesformobj.getInsert());
				System.out.println("del butt name is"+ lktypesformobj.getDelete());
				System.out.println("****bnutt name****"+ lktypesformobj.getButt());
				String path = MenuNameReader.getScreenProperties(lktypesformobj.getPageId());
				
				
				lktypesformobj.setValidateFlag("");

				System.out.println("came inside ");
					try 
					{
						
						LockerDetailsObject lkdobj = lkd.getLockerDetails(lktypesformobj.getCombo_types());

						if (lkdobj != null&& !(lktypesformobj.getCombo_types().equalsIgnoreCase("addnew"))) 
						{
							System.out.println("lkltypes path is" + path);
							System.out.println("wen lkobj not null values"+ lkdobj.getLockerHeight() + ""+ lkdobj.getLockerLength() + ""+ lkdobj.getLockerDepth());
							httpServletRequest.setAttribute("key", lkdobj);
							System.out.println("after setting");

						}

						if (lktypesformobj.getForward().equalsIgnoreCase("delete")) 
						{
							
							System.out.println("delettinggg,,");

							int delete = lkd.deleteLockerTypeParameter(lktypesformobj.getCombo_types());

							if (delete == 1)
								lktypesformobj.setValidateFlag("Deleted Successfully");
							if (delete == 2)
								lktypesformobj.setValidateFlag("Type is in use cannot delete");
							if (delete == 3)
								lktypesformobj.setValidateFlag("Error in deleting");
						}
						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}

				
				

				// To Insert

				 if (lktypesformobj.getForward().equalsIgnoreCase("insert")) 
				{
					
					System.out.println("insertinggg,,,");
					System.out.println("....inside submit event....");
					System.out.println("---" + lktypesformobj.getLockerType()+ "---" + lktypesformobj.getDescription() + "---"+ lktypesformobj.getLockerHeight() + "---"+ lktypesformobj.getLockerLength() + "---"+ lktypesformobj.getLockerDepth() + "---"+ LockerDelegate.getSysDate());
					LockerDetailsObject lockerdatailsobject = new LockerDetailsObject();

					if (lktypesformobj.getDescription() == null&& lktypesformobj.getLockerHeight() == null&& lktypesformobj.getLockerLength() == null&& lktypesformobj.getLockerDepth() == null) {
							
							System.out.println("oh hoooooooo");
					} 
					else 
					{
						System.out.println("honey bee");
						lockerdatailsobject.setLockerType(lktypesformobj.getLockerType());
								
						System.out.println("after few steps");
						lockerdatailsobject.setDescription(lktypesformobj.getDescription());
								
						lockerdatailsobject.setLockerHeight(lktypesformobj.getLockerHeight());
								
						lockerdatailsobject.setLockerLength(lktypesformobj.getLockerLength());
								
						lockerdatailsobject.setLockerDepth(lktypesformobj.getLockerDepth());
								

						lockerdatailsobject.uv.setUserTml(tml);
						lockerdatailsobject.uv.setUserId(uid);
						lockerdatailsobject.uv.setUserDate(LockerDelegate.getSysDate());
								
						lockerdatailsobject.setTrnDate(LockerDelegate.getSysDate());
								
						System.out.println("b4 goin 2 delegate");
						System.out.println("LockerHeight=============="+ lockerdatailsobject.getLockerHeight());
								

						boolean flag = lkd.addLockerTypeParameter(lockerdatailsobject);
								

						if (flag)
							lktypesformobj.setValidateFlag(" Inserted Successfully ");
									
						if (!flag)
							lktypesformobj.setValidateFlag("Error in inserting");
									

					}
					LockerDetailsObject lkdobj = lkd.getLockerDetails(lktypesformobj.getCombo_types());
							
					httpServletRequest.setAttribute("key", lkdobj);
					System.out.println("******************************8"+ MenuNameReader.getScreenProperties(lktypesformobj.getPageId()));
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lktypesformobj.getPageId()));
							

				}
				    
				 	String[] str_locker_types = lkd.getLockersTypes();
					httpServletRequest.setAttribute("lktypes", str_locker_types);
				    httpServletRequest.setAttribute("pageId", path);
				    return actionMapping.findForward(ResultHelp.getSuccess());
				 
			}
			
		}
		// Locker operation

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKOperationMenuLink")) 
		{
			try {
				System.out.println("B4 creating form object");
				LockerOperationForm LKOpFormobj = (LockerOperationForm) actionForm;
				if (MenuNameReader.containsKeyScreen(LKOpFormobj.getPageId())) {

					path = MenuNameReader.getScreenProperties(LKOpFormobj
							.getPageId());
					String page = LKOpFormobj.getPageId();
					System.out.println("page id should be surr.jsp but=="
							+ LKOpFormobj.getPageId() + "n path is" + path);
					getLkAbbr(httpServletRequest, page, lkd);
					System.out.println("after setting intial param");
					httpServletRequest.setAttribute("pageId", path);
					System.out.println("after setting");
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKopLink")) 
		{

			try {
				LockerOperationForm LKOpFormobj1 = (LockerOperationForm) actionForm;
				LKOpFormobj1.setValidateFlag("");
				if (MenuNameReader.containsKeyScreen(LKOpFormobj1.getPageId())) 
				{

					path = MenuNameReader.getScreenProperties(LKOpFormobj1.getPageId());
					String acnum1 = LKOpFormobj1.getAcn_no();
					
					int acnum = Integer.parseInt(acnum1);
					LockerMasterObject lockerMasterObject = lkd.getLockerMaster(acnum, 1);
					
					
					if(LKOpFormobj1.getForward().equalsIgnoreCase("submit")) 
					{

						try 
						{
							LockerTransObject lockertransobject = new LockerTransObject();
							
							System.out.println("########Locker Time outttt"+ LKOpFormobj1.getTxt_timeout());

							lockertransobject.setLockerAcType("1009001");
							lockertransobject.setLockerAcNo(Integer.parseInt(LKOpFormobj1.getAcn_no()));
							lockertransobject.setLockerNo(Integer.parseInt(LKOpFormobj1.getLockerNo()));
							lockertransobject.setOperatedBy(LKOpFormobj1.getTxt_operatedby());
							lockertransobject.setTimeIn(LKOpFormobj1.getTxt_timein());
							lockertransobject.setTimeOut(LKOpFormobj1.getHiddenTime());
							lockertransobject.setTrnDate(LockerDelegate.getSysDate());
							lockertransobject.setOpDate(LKOpFormobj1.getTxt_operatedon());
							lockertransobject.uv.setUserId(uid);
							lockertransobject.uv.setUserTml(uid);
							lockertransobject.uv.setUserDate(lkd.getSysDate());

							// boolean deleted =
							// lkd.deleteLockerTransaction(lockertransobject,1);
							boolean boolean_stored = lkd.storeLockerTransaction(lockertransobject,1);
							System.out.println("BOOOOLEEEAN" + boolean_stored);

							if (boolean_stored) 
							{

								System.out.println("--------------->booltrueeeeeee");
								// set the flags here
								LKOpFormobj1.setValidateFlag("Submited Successfully");
							} 
							else 
							{
								LKOpFormobj1.setValidateFlag("Unable to Delete....replace");
								System.out.println("Unable to Delete....replace");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					if (LKOpFormobj1.getForward().equalsIgnoreCase("delete")) 
					{
						System.out.println("TimeInnnn"+ LKOpFormobj1.getTxt_timein() + "timeOutt"+ LKOpFormobj1.getTxt_timeout());
						
						try 
						{
							LockerTransObject lockertransobject = new LockerTransObject();

							lockertransobject.setLockerAcType("1009001");
							lockertransobject.setLockerAcNo(Integer.parseInt(LKOpFormobj1.getAcn_no()));
							lockertransobject.setLockerNo(Integer.parseInt(LKOpFormobj1.getLockerNo()));
							lockertransobject.setOperatedBy(LKOpFormobj1.getTxt_operatedby());
							lockertransobject.setTimeIn(LKOpFormobj1.getTxt_timein());
							lockertransobject.setTimeOut("");
							lockertransobject.setTrnDate(lkd.getSysDate());
							boolean deleted = lkd.deleteLockerTransaction(lockertransobject, 1);
							System.out.println("BOOOOOOOLEEEAN" + deleted);
							
							if (deleted == false) 
							{
								System.out.println("<---false ----->");
								LKOpFormobj1.setValidateFlag("Cannot able to delete");
							} 
							else if (deleted == true) 
							{
								System.out.println("<---true----->");
								LKOpFormobj1.setValidateFlag("Successfully deleted");
							}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
					if(LKOpFormobj1.getForward().equalsIgnoreCase("update")) 
					{
						System.out.println("crawling in my skin");
						LockerTransObject lockerTransObject = new LockerTransObject();
						System.out.println(Integer.parseInt(LKOpFormobj1.getAcn_no())+ "--op?B--"+ LKOpFormobj1.getTxt_operatedby()+ "--TmOut--"+ LKOpFormobj1.getTxt_timeout()+ "---Tim in--" + LKOpFormobj1.getTxt_timein());

						lockerTransObject.setLockerAcType("1009000");
						lockerTransObject.setLockerAcNo(Integer.parseInt(LKOpFormobj1.getAcn_no()));
						lockerTransObject.setLockerNo(Integer.parseInt(LKOpFormobj1.getLockerNo()));
						lockerTransObject.setLockerType(LKOpFormobj1.getLockerType());

						lockerTransObject.setTrnDate(LockerDelegate.getSysDate());
						lockerTransObject.setOperatedBy(LKOpFormobj1.getTxt_operatedby());
						lockerTransObject.setTimeIn(LKOpFormobj1.getTxt_timeout());
						lockerTransObject.setTimeOut(LKOpFormobj1.getTxt_timeout());
						lockerTransObject.setTrnDate(LockerDelegate.getSysDate());

						lockerTransObject.uv.setUserId(uid);
						lockerTransObject.uv.setUserTml(tml);
						lockerTransObject.uv.setUserDate(LockerDelegate.getSysDate());

						// For verification
						/*
						 * lockerTransObject.uv.setVerId(uid);
						 * lockerTransObject.uv.setVerTml(tml);
						 * lockerTransObject.uv.setVerDate(LockerDelegate.getSysDate());
						 */

						boolean boolean_stored = lkd.storeLockerTransaction(lockerTransObject, 1);

					}
					if (LKOpFormobj1.getForward().equalsIgnoreCase("timein")) 
					{
						System.out.println("confusing wat is real 'm time in");
						System.out.println(LKOpFormobj1.getTxt_timein());
						httpServletRequest.setAttribute("timeInValue",LKOpFormobj1.getTxt_timein());
						getLkAbbr(httpServletRequest, path, lkd);
						getLkParams(httpServletRequest, path, lkd, acnum, 1);
						String date1 = LockerDelegate.getSysDate();
						LKOpFormobj1.setHiddenTime(null);
						httpServletRequest.setAttribute("date", date1);
						httpServletRequest.setAttribute("pageId", path);
						return actionMapping.findForward(ResultHelp.getSuccess());
					}
					if (LKOpFormobj1.getForward().equalsIgnoreCase("timeout")) 
					{
						System.out.println("my time is out");
						System.out.println(LKOpFormobj1.getTxt_timeout());
						System.out.println("Time in Timee "+ LKOpFormobj1.getTxt_timein());
						httpServletRequest.setAttribute("timeInValue",LKOpFormobj1.getTxt_timein());
						httpServletRequest.setAttribute("timeOutValue",LKOpFormobj1.getTxt_timeout());
						LKOpFormobj1.setHiddenTime(LKOpFormobj1.getTxt_timeout());
						getLkAbbr(httpServletRequest, path, lkd);
						getLkParams(httpServletRequest, path, lkd, acnum, 1);
						String date1 = LockerDelegate.getSysDate();
						httpServletRequest.setAttribute("date", date1);
						httpServletRequest.setAttribute("pageId", path);
						return actionMapping.findForward(ResultHelp.getSuccess());
					}
					else if(lockerMasterObject!=null)
					{
						 
						if(lockerMasterObject.uv.getVerId()!=null)
						{
							if(!(lockerMasterObject.getCloseDate()!=null))
							{
							 	if(lockerMasterObject.getFreezeInd().equals("F"))
							 	{
							 		if(Validations.validDate(lockerMasterObject.getMatDate(),LockerDelegate.getSysDate())==1)
								 	{
								 	
							 		System.out.println("Allotment Experied");	
							 		Date dat = new Date();
							 		String s2=null;
							 		String s = dat.toString();
							 		StringTokenizer s1 = new StringTokenizer(s, " ");
							 	
							 		if(s1.hasMoreTokens()) 
							 		{
							 			s1.nextToken();
							 			s1.nextToken();
							 			s1.nextToken();
							 			s2 = s1.nextToken();
							 			s1.nextToken();
							 			s1.nextToken();
							 		}
							 		String strDat = s2;
						
							 		httpServletRequest.setAttribute("lkparams",lockerMasterObject);
							 		System.out.println("verify tml???"+ lockerMasterObject.uv.getVerTml());

							 		if(lockerMasterObject.getTimeIn() !=null || lockerMasterObject.getTimeOut() != null) 
							 		{

							 			httpServletRequest.setAttribute("lkparams",lockerMasterObject);
							 			httpServletRequest.setAttribute("timeInValue",lockerMasterObject.getTimeIn());
							 		
							 			if(lockerMasterObject.getTimeOut().length()!=0)
							 			{
							 				httpServletRequest.setAttribute("timeOutValue",lockerMasterObject.getTimeOut());
							 			}
							 			else
							 			{
							 				httpServletRequest.setAttribute("timeOutValue",strDat);
							 			}
						
							 		}
							 		else if (lockerMasterObject.uv.getVerTml() != null) 
							 		{
							 			httpServletRequest.setAttribute("timeInValue", null);
							 			httpServletRequest.setAttribute("timeOutValue",null);
							 			httpServletRequest.setAttribute("lkparams",lockerMasterObject);
							 		}
								 }
							 	 else if(Validations.validDate(lockerMasterObject.getMatDate(),LockerDelegate.getSysDate())==-1)
							 	 {	
									LKOpFormobj1.setValidateFlag("Allotment is Expired");
									LKOpFormobj1.setAcn_no("");
								 }	
							 		
							 	}
							 	else if(lockerMasterObject.getFreezeInd().equals("T"))
							 	{ 
									LKOpFormobj1.setValidateFlag("Locker A/c No is Freezed");
									LKOpFormobj1.setAcn_no("");
							 	}
							}
							else 
							{
								LKOpFormobj1.setAcn_no("");
								LKOpFormobj1.setValidateFlag("Locker A/c No is Closed");
							}
						}
						else 
						{
							LKOpFormobj1.setValidateFlag("Locker A/c No Not verified");
							LKOpFormobj1.setAcn_no("");
						}
					}	     
					else if(lockerMasterObject == null)
					{
						 		LKOpFormobj1.setValidateFlag("Records not found");
						 		LKOpFormobj1.setAcn_no("");
					}	
						 
						
					 	String date1 = LockerDelegate.getSysDate();
					 	getLkAbbr(httpServletRequest, path, lkd);
					 	httpServletRequest.setAttribute("date", date1);
					 	httpServletRequest.setAttribute("pageId", path);
					 	return actionMapping.findForward(ResultHelp.getSuccess());
		
			  }
			  else
				{
				  return actionMapping.findForward(ResultHelp.getError());
					
				}
				
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKExtensionMenuLink")) 
		{
			LockerExtensionForm lkExtensionForm = (LockerExtensionForm) actionForm;

			System.out.println("11111");
			if (MenuNameReader.containsKeyScreen(lkExtensionForm.getPageId())) {

				System.out.println("2222");
				String path = MenuNameReader.getScreenProperties(lkExtensionForm.getPageId());

				System.out.println("3333");
				ModuleObject[] moduleObject222 = lkd.getMainModules("1002000,1007000");
				System.out.println("pageId is" + path);
				ModuleObject[] moduleObjectDep=lkd.getMainModules(2);
				httpServletRequest.setAttribute("DepositTypes",moduleObjectDep); 
    		 	getLkAbbr(httpServletRequest, path, lkd);
				httpServletRequest.setAttribute("transferTypes", moduleObject222);
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward(ResultHelp.getSuccess());
			}

			else {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());

			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKExtensionLink")) 
		{
			
			String path;
			LockerExtensionForm LKExtnObj = (LockerExtensionForm)actionForm;
			LockerMasterObject lockermasterobject=null;
			LKExtnObj.setValidateFlag("");
			if (MenuNameReader.containsKeyScreen(LKExtnObj.getPageId())) 
			{

				path = MenuNameReader.getScreenProperties(LKExtnObj.getPageId());
				ModuleObject[] moduleObject222 = lkd.getMainModules("1002000,1007000");
				ModuleObject[] moduleObjectDep=lkd.getMainModules("1003000,1004000,1005000");
				System.out.println("'m inside lkExLink");
				System.out.println("PathId is..," + path);
				System.out.println(LKExtnObj.getForward() + "/////////////"+ LKExtnObj.getTransferAcntNum() + "<------>"+ LKExtnObj.getTransferAcntType());
				lockermasterobject=new LockerMasterObject();
			
				if (LKExtnObj.getForward().equalsIgnoreCase("delete")) 
				{
					boolean boolean_verify = false;
					lockermasterobject.setLockerAcType("1009001");
					lockermasterobject.setLockerAcNo(Integer.parseInt(LKExtnObj.getTxt_acNo()));
					lockermasterobject.setLockerType(LKExtnObj.getTxt_lockType());
					lockermasterobject.setLockerNo(Integer.parseInt(LKExtnObj.getTxt_lockNo()));
					lockermasterobject.setRentBy(LKExtnObj.getReceiptDetails());
					lockermasterobject.setTransAcType(LKExtnObj.getTransferAcntType());
					lockermasterobject.setTransAcNo(Integer.parseInt(LKExtnObj.getTransferAcntNum()));
					lockermasterobject.setRentColl(Double.parseDouble(LKExtnObj.getTxt_totRent()));
					lockermasterobject.setTrnDate(LockerDelegate.getSysDate());
					lockermasterobject.setRentUpto(LKExtnObj.getTxt_extnDate());
					lockermasterobject.setMatDate(LKExtnObj.getTxt_extnDate());
					lockermasterobject.setReqdMonths(Integer.parseInt(LKExtnObj.getTxt_extnMonths()));
					lockermasterobject.setRequiredDays(Integer.parseInt(LKExtnObj.getTxt_extnDays()));
					lockermasterobject.setDeposits(null);
					lockermasterobject.setNoOfSecurities(0);
					lockermasterobject.uv.setUserId(uid);
					lockermasterobject.uv.setUserTml(tml);
					lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
					try
					{
						lkd.deleteLockerAccount(lockermasterobject, 2);
						LKExtnObj.setValidateFlag("Deleted Successfully");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					

				}
				else if(LKExtnObj.getForward().equalsIgnoreCase("futureDate"))
				{
					try
	                {
	                    System.out.println("inside txt_required_days != 0..");
	                    double double_rent = 0.0;
	                    lockermasterobject=lkd.getLockerMaster(Integer.parseInt(LKExtnObj.getTxt_acNo()),3);
	                    array_lockers_module=lkd.getMainModules("1009000");
	                    int total=Integer.parseInt(LKExtnObj.getTxt_extnDays())+Integer.parseInt(LKExtnObj.getTxt_extnMonths())*30;
	                    
	                    System.out.println("total days = "+total);
	                    
	                    for(int i=0;i<array_lockers_module.length;i++)
	                    {
	                        if(array_lockers_module[i].getModuleCode().equals(LKExtnObj.getTxt_acType()))
	                        {
	                            int lk_min_period = array_lockers_module[i].getMinPeriod();
	                            int lk_max_period = array_lockers_module[i].getMaxRenewalDays();
	                            System.out.println("lk_min_period = "+lk_min_period);
	                            if((total >= lk_min_period) && (total <= lk_max_period))
	                            {
	                                String mt_dt = lkd.getFutureDayDate(lockermasterobject.getMatDate(),total);
	                                System.out.println("future date = "+mt_dt);
	                                
	                                //ship......04/04/2006
	                                if(Validations.checkDateValid(mt_dt,ClearingDelegate.getSysDate())==1)
	                                {
	                                    LKExtnObj.setTxt_extnDate(mt_dt);
	                                    
	                                    double_rent=lkd.getRent(new StringTokenizer(lockermasterobject.getLockerType()).nextToken(),total,1,ClearingDelegate.getSysDate());
	                                    
	                                    System.out.println("total = "+total);
	                                    System.out.println("double_rent = "+double_rent);
	                                    
	                                    if(double_rent!=0)
	                                    {
	                                        System.out.println("inside double_rent != 0");
	                                        LKExtnObj.setTxt_rentAmnt(String.valueOf(double_rent));
	                                        
	                                        //ship......24/05/2007
	                                        //lbl_rent_amt.setText(String.valueOf(double_rent));
	                             
	                                    }
	                                    else
	                                    {
	                                        LKExtnObj.setTxt_extnMonths("");
	                                        LKExtnObj.setTxt_extnDays("");
	                                        LKExtnObj.setValidateFlag("Rent Not Defined");         
	                                    }
	                                }
	                                else
	                                {
	                                	LKExtnObj.setTxt_extnMonths("");
                                        LKExtnObj.setTxt_extnDays("");
                                        LKExtnObj.setValidateFlag("Maturity date should be more than or equal to todays date");
	                                }
	                            }
	                            else
	                            {     
	                                
	                                LKExtnObj.setValidateFlag("Locker Period should be b/n "+lk_min_period+" and "+lk_max_period+" days");
	                                LKExtnObj.setTxt_extnMonths("");
                                    LKExtnObj.setTxt_extnDays("");
	                            }
	                        }
	                    }
	                }
	                catch(Exception exception)
	                {
	                    exception.printStackTrace();
	                }
				}
				else if(LKExtnObj.getForward().equalsIgnoreCase("ScrollNum"))
				{
					
					 AccountObject accountObject=null;
					 
					if(LKExtnObj.getScrollNum()==null)
			        {
						LKExtnObj.setValidateFlag("Enter the Scroll Number");
			        }
			        else if(LKExtnObj.getScrollNum()!=null && Integer.parseInt(LKExtnObj.getScrollNum())!=0)
			        {
			            try 
			            {
			                accountObject=lkd.getAccount("C",LKExtnObj.getTxt_acType(),Integer.parseInt(LKExtnObj.getScrollNum()),lkd.getSysDate());
			            } 
			            catch(NumberFormatException e) 
			            {
			                e.printStackTrace();
			            }
			            catch(RemoteException e)
			            {
			                e.printStackTrace();
			            }
			            
			            if(accountObject==null)
			            {
			            	LKExtnObj.setValidateFlag("Scroll Number is not found");
			            }
			            else
			            {
			                String string_modulecode="";
			                System.out.println("inside getCashDetails......");
			                
			                string_modulecode=LKExtnObj.getTxt_acType();
			                
			                System.out.println("mod_code......"+string_modulecode);
			                System.out.println("ac_obj mod_code......."+accountObject.getAcctype());
			                System.out.println("txt_period_rent = "+(LKExtnObj.getTxt_rentAmnt()));
			                System.out.println("acobj getAmount = "+accountObject.getAmount());
			                
			                if(LKExtnObj.getTxt_rentAmnt().length()!=0)
			                {
			                	if(accountObject.getAccStatus().equals("C"))
			                		LKExtnObj.setValidateFlag("Scroll Number Already Attached");
			                	else if(accountObject.getAmount()==Double.parseDouble(LKExtnObj.getTxt_rentAmnt()) && accountObject.getAcctype().equals(string_modulecode))
			                	{
			                		System.out.println("@@@@@@@inside getCashDetails......");
			                		LKExtnObj.setDate(lkd.getSysDate());
			                		LKExtnObj.setAmount(LKExtnObj.getTxt_rentAmnt());
			                	}
			                	else
			                	{
			                		LKExtnObj.setValidateFlag("Scroll Number Amount Doesn't Match With Rent Amount");
			                	}
			                }
			            }
			        }
				}
				else if(LKExtnObj.getForward().equalsIgnoreCase("TrnAcNum"))
				{
				  
					LKExtnObj.setValidateFlag("");
				  	System.out.println(LKExtnObj.getTransferAcntType()+"------"+LKExtnObj.getTransferAcntNum());
				  	AccountObject accountObject=lkd.getAccount(LKExtnObj.getTransferAcntType(),Integer.parseInt(LKExtnObj.getTransferAcntNum()),LockerDelegate.getSysDate());
				  	System.out.println("hiiiiiiiiiiiiii"+accountObject);
				  	if(accountObject==null)
				  	{ 
					  LKExtnObj.setValidateFlag("Account not found"); 
					  System.out.println("Account not found"); 
				  	}
				  	else 
				  	{
				  		if(accountObject.getAmount()<Double.parseDouble(LKExtnObj.getTxt_rentAmnt()))
				  		{
				  			LKExtnObj.setValidateFlag("No enough Amount in the Account");
				  			System.out.println("No enough Amount in the Account"); 
				  		}
				  		else
				  		{	
				  			if(accountObject.getAmount()==0.0)
				  			{
				  				LKExtnObj.setValidateFlag("No Amount in the Account");
				  			}
				  			else 
				  			{
				  				httpServletRequest.setAttribute("trnsferDetails",accountObject);
				  				
				  			} 
				  		}
				  	}	
				 }
				 else if(LKExtnObj.getForward().equalsIgnoreCase("depositnum"))
	   	 		 {
					 try
					 {
						 DepositMasterObject object=lkd.getDepositMaster(LKExtnObj.getCombo_details(),Integer.parseInt(LKExtnObj.getAcc_no()));
						 System.out.println("deposit master object==>"+object);
						 if(object!=null)
						 {	 
							
							 String sub=LKExtnObj.getCombo_details().substring(1,4);
						 	if(object.getVerified().equals("F"))
							{	
								LKExtnObj.setValidateFlag("Deposit Account number is not yet verified");
								LKExtnObj.setAcc_no("");
							}
							else if(object.getLoanAvailed().equals("T"))//ship.....04/02/2006....added type==0
							{	
								LKExtnObj.setValidateFlag("Loan is already availed on this Deposit");
								LKExtnObj.setAcc_no("");
							}
							else if(object.getClosedt()!=null)
							{	
								LKExtnObj.setValidateFlag("Deposit Account is closed");
								LKExtnObj.setAcc_no("");
							}
							else if((sub.equals("003") || sub.equals("004") || sub.equals("005"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
							{	
								
								LKExtnObj.setValidateFlag("Maturity Date lapsed");
								LKExtnObj.setAcc_no("");
							}	
							else if((sub.equals("006"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
							{	
								
								LKExtnObj.setValidateFlag("Minimum Period for Deposit lapsed");
								LKExtnObj.setAcc_no("");
							}
							else if((sub.equals("006"))?object.getCloseStart().equals("C"):false)
							{	
								
								LKExtnObj.setValidateFlag("Deposit Account is closed");
								LKExtnObj.setAcc_no("");
							}
						 }
						 else
						 {
							 LKExtnObj.setValidateFlag("Deposit Account number not found");
						     LKExtnObj.setAcc_no("");
						 }
					 }
					 catch (Exception e) 
					 {
						 LKExtnObj.setValidateFlag("Deposit Account Details Not Found");
						 LKExtnObj.setAcc_no("");
					 }
				}
				else if (LKExtnObj.getForward().equalsIgnoreCase("extend")) 
				{

					
					int proceed = 0;
		            
		            boolean continue_transaction = false;
		            
		            try
		            {
		                if(LKExtnObj.getReceiptDetails().equalsIgnoreCase("C"))//Cash
		                    proceed = lkd.checkDailyStatus(lkd.getSysDate(),0);
		                else if(LKExtnObj.getReceiptDetails().equalsIgnoreCase("T"))//Transfer
		                    proceed = lkd.checkDailyStatus(lkd.getSysDate(),1);
		            }
		            catch(Exception ex)
		            {
		                ex.printStackTrace();
		            }
		            if(proceed==3)//day close
		            {
		                
		            	LKExtnObj.setValidateFlag("Day closed\n You can't do any Transactions");
		                continue_transaction = false;
		                
		            }
		            else if(proceed==1)//cash close
		            {
		                
		            	LKExtnObj.setValidateFlag(" Cash closed\n You can't do any Transactions");
		                continue_transaction = false;
		                
		            }
		            else if(proceed==2)//gl posting
		            {
		                int result = JOptionPane.showConfirmDialog(null," GL Posting has been done\n Do You want to continue the Transaction ?","GL Posting done",JOptionPane.YES_NO_OPTION);
		                
		                if(result==JOptionPane.YES_OPTION)
		                {
		                    try
		                    {
		                        boolean glposting = lkd.updatePostInd("Head Office",lkd.getSysDate());
		                        
		                        if(glposting==true)
		                        {
		                            continue_transaction = true;
		                        }
		                        else if(glposting==false)
		                        {
		                            
		                            continue_transaction = false;
		                            LKExtnObj.setValidateFlag("Error : Unable to Proceed");
		                        }
		                    }
		                    catch(Exception ex)
		                    {
		                        ex.printStackTrace();
		                    }
		                }
		                else if(result==JOptionPane.NO_OPTION)
		                {
		                   
		                }
		                
		            }
		            else if(proceed==-1)
		            {
		               
		                continue_transaction = false;
		                LKExtnObj.setValidateFlag("Error : no entry in Daily status for today");
		            }
		            else
		                continue_transaction = true;
		            
		      
		            if(continue_transaction==true)
					{
							// big code goes here
							System.out.println("big code goes here");
							boolean boolean_verify = false;
							lockermasterobject.setLockerAcType("1009001");
							lockermasterobject.setLockerAcNo(Integer.parseInt(LKExtnObj.getTxt_acNo()));
							lockermasterobject.setLockerType(LKExtnObj.getTxt_lockType());
							lockermasterobject.setLockerNo(Integer.parseInt(LKExtnObj.getTxt_lockNo()));
							System.out.println(LKExtnObj.getTransferAcntType()+ "<--tran type   check for tran num-->"+ LKExtnObj.getTransferAcntNum());
							System.out.println("Receipt Details3333333,,,,"+ LKExtnObj.getReceiptDetails());

							if (LKExtnObj.getReceiptDetails().equalsIgnoreCase("T"))
							{
								System.out.println("when money floes thro transfer");
								lockermasterobject.setRentBy("T");
								lockermasterobject.setTransAcType(LKExtnObj.getTransferAcntType());
								lockermasterobject.setTransAcNo(Integer.parseInt(LKExtnObj.getTransferAcntNum()));
							}

							if (LKExtnObj.getReceiptDetails().equalsIgnoreCase("C"))
							{
								System.out.println("when money floes thro cash");
								lockermasterobject.setRentBy("C");
								lockermasterobject.setTransAcType(null);
								lockermasterobject.setTransAcNo(Integer.parseInt(LKExtnObj.getScrollNum()));
							}

							lockermasterobject.setRentColl(Double.parseDouble(LKExtnObj.getTxt_rentAmnt()));
							lockermasterobject.setTrnDate(LockerDelegate.getSysDate());
							lockermasterobject.setRentUpto(LKExtnObj.getTxt_extnDate());
							lockermasterobject.setMatDate(LKExtnObj.getTxt_extnDate());
							lockermasterobject.setReqdMonths(Integer.parseInt(LKExtnObj.getTxt_extnMonths()));
							lockermasterobject.setRequiredDays(Integer.parseInt(LKExtnObj.getTxt_extnDays()));
							
							if(LKExtnObj.isChk())
	                        {
	                        	Vector vector_accounts=null;
            					vector_accounts=new Vector(0,1);
            					vector_accounts.add(LKExtnObj.getCombo_details()+" "+LKExtnObj.getAcc_no());
            					lockermasterobject.setDeposits(vector_accounts);
            					lockermasterobject.setNoOfSecurities(1);
	                        }
	                        else
	                        {
	                            lockermasterobject.setDeposits(null);
	                            lockermasterobject.setNoOfSecurities(0);
	                        }
							if (!boolean_verify)
							{
								lockermasterobject.uv.setUserId(uid);
								lockermasterobject.uv.setUserTml(tml);
								lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
							}
							else if (boolean_verify) 
							{
								lockermasterobject.uv.setVerId(uid);
								lockermasterobject.uv.setVerTml(tml);
								lockermasterobject.uv.setVerDate(LockerDelegate.getSysDate());
							}

							if((!boolean_verify)?lkd.updateLocker(lockermasterobject,1) : lkd.updateLocker(lockermasterobject, 5))
							{
								System.out.println("done");
								LKExtnObj.setValidateFlag("Account Extended Successfully");
							}
							else
							{			
								LKExtnObj.setSuccessFlag("Cant Extended");
								System.out.println("not done ");
							}

						}
					

				}
				else if(LKExtnObj.getForward().equalsIgnoreCase("AcNum")) 
				{
					int i=0;
					lockermasterobject=lkd.getLockerMaster(Integer.parseInt(LKExtnObj.getTxt_acNo()),3);
					
					if(lockermasterobject!=null)
					{
						try
						{
	                     System.out.println("..1..");
	                       
	                     if(lockermasterobject!=null )
	                     {
	                        if(lockermasterobject.getLockerNo()==-1)
	                        {
	                           i = 1;
	                        }    
	                        else if(lockermasterobject.uv.getVerId()==null)
	                        {
	                           i = 2;
	                        }	
	                        else if(lockermasterobject.getCloseDate()!=null)
	                        {        
	                        	i = 3;
	                        }
	                        else if(lockermasterobject.getFreezeInd().equals("T"))
	                        {
	                        	i = 4;
	                        }
	                     }
	                       
	                    }
	                    catch(NullPointerException n) 
	                    {
	                        LKExtnObj.setValidateFlag("Error : "+n);
	                    }
					
	                if(i==0)
	                {
	                	int locker_max_renewal_period = 0;
	                	
	                	array_lockers_module=lkd.getMainModules("1009000");
	                	array_lockerparamobject=lkd.getLockerTypes();
                        for(int m=0;m<array_lockers_module.length;m++)
                        {
                            if(array_lockers_module[m].getModuleCode().equals(LKExtnObj.getTxt_acType()))
                            {
                               locker_max_renewal_period = array_lockers_module[m].getMaxRenewalCount();
                            }  
                        }
                        
                        if(lockermasterobject!=null)
	                    {
                        	 if(Validations.dayCompare(lockermasterobject.getMatDate(),ClearingDelegate.getSysDate())>locker_max_renewal_period)
     	                    {
     	                        
     	                        LKExtnObj.setValidateFlag("Locker Expired");
     	                        LKExtnObj.setTxt_acNo("");
     	                    }
                        	else
 	                        {
                        		for(int d=0;d<array_lockerparamobject.length;d++)
                                {
                                    if(lockermasterobject.getLockerType().equals(array_lockerparamobject[d].getLockerType()))
                                    {
                                        LKExtnObj.setTxt_lockType(array_lockerparamobject[d].getDescription());
                                    }
                                }
                        		
                        		LKExtnObj.setTxt_lockNo(String.valueOf(lockermasterobject.getLockerNo()));
                        		LKExtnObj.setTxt_allotDate(lockermasterobject.getAllotDate());
                        		LKExtnObj.setTxt_expiryDate(lockermasterobject.getMatDate());
                        		LKExtnObj.setTxt_rentUpto(lockermasterobject.getRentUpto());
                        		LKExtnObj.setTxt_totRent(String.valueOf(lockermasterobject.getTotalRentColl()));
                        		LKExtnObj.setTxt_rentAmnt(String.valueOf(lockermasterobject.getRentColl()));
                        		
                        		
                        		if(lockermasterobject.getTransAcNo()!=0)
	                            {
	                                LKExtnObj.setReceiptDetails(lockermasterobject.getRentBy());
	                                LKExtnObj.setTransferAcntType(lockermasterobject.getTransAcType());
	                                LKExtnObj.setTransferAcntNum(String.valueOf(lockermasterobject.getTransAcNo()));
	                            }
                        		
                        		if(((Validations.validDate(lockermasterobject.getRentUpto(),lockermasterobject.getMatDate()))==1))
		                        {
                        			System.out.println("inside validate date");
                        			try
	                                {
	                                    int total_no_months=lkd.getNoOfMonths(lockermasterobject.getMatDate(),lockermasterobject.getRentUpto());
	                                    LKExtnObj.setTxt_extnMonths(String.valueOf(total_no_months));
	                                    String exp_date_month=lkd.getFutureMonthDate(lockermasterobject.getMatDate(),total_no_months);
	                                    int total_no_days=lkd.getDaysFromTwoDate(exp_date_month,lockermasterobject.getRentUpto());
	                                    LKExtnObj.setTxt_extnDays(String.valueOf(total_no_days));                        
	                                }
	                                catch(RemoteException remoteException)
	                                {
	                                    remoteException.printStackTrace();
	                                }
	                                
	                                LKExtnObj.setTxt_extnDate(lockermasterobject.getRentUpto());
		                            
		                        }
                        		
 	                        }
	                    }
                        else if(lockermasterobject==null)
	                    {
	                        
	                        LKExtnObj.setValidateFlag("Locker Ac No not found");
	                        LKExtnObj.setTxt_acNo("");
	                    }
	                }
	                else if(i==1)
                    {                     
                    	LKExtnObj.setValidateFlag("Locker Not found");
	                	LKExtnObj.setTxt_acNo("");
                    }
                    else if(i==2)
                    {
                        LKExtnObj.setValidateFlag("Locker not verified");
                        LKExtnObj.setTxt_acNo("");
                    }
	                else if(i==3)
	                {
                    	LKExtnObj.setValidateFlag("Locker Account is Closed");
	                	LKExtnObj.setTxt_acNo("");
	                }
	                else if(i==4)
	                {
                        LKExtnObj.setValidateFlag("Locker Account is Freezed");
	                	LKExtnObj.setTxt_acNo("");
	                }
	                
						System.out.println("Extend $$$$" + LKExtnObj.getSubmit());
						String details = LKExtnObj.getSelect_details();
						System.out.println("Intro Ac type"+ lockermasterobject.getIntrAcTy() + "Intro Ac Num"+ lockermasterobject.getIntrAcNo());
						setDetails(details, httpServletRequest, lkd,lockermasterobject, 0, lockermasterobject.getIntrAcNo(), lockermasterobject.getIntrAcTy());
						System.out.println("after settin details!@#$$%^@#@$%!@#$@");
				}
				else 
					{
						LKExtnObj.setValidateFlag("Records not found");
						LKExtnObj.setTxt_acNo("");
					}	
				}
				if(!LKExtnObj.getSelect_details().equalsIgnoreCase("Select"))
				{
					if(LKExtnObj.getTxt_acNo().length()!=0)
					{
						lockermasterobject=lkd.getLockerMaster(Integer.parseInt(LKExtnObj.getTxt_acNo()), 3);
					
					if(LKExtnObj.getSelect_details().equalsIgnoreCase("Deposit"))
					{
						if(lockermasterobject.getDeposits()!=null && lockermasterobject.getNoOfSecurities()!=0 )
						{
						vobject=lockermasterobject.getDeposits();
						String string_code=null;
						int int_acno=0;
	                    System.out.println("lockermasterobject.getDeposits size = "+vobject.size());
	                    Enumeration enumeration=vobject.elements();
	                    while(enumeration.hasMoreElements())
	                    {
	                         StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
	                          string_code=tokenizer.nextElement().toString();
	                          int_acno=Integer.parseInt(tokenizer.nextToken());
	                          	
	                     }
	                    try
    	   	 			 {
    	   	 				 DepositMasterObject object=lkd.getDepositMaster(string_code,int_acno);
    	   	 				 System.out.println("deposit master object==>"+object);	
    	   	 				 int dcid=object.getCustomerId();
    	   	 				 System.out.println("customer id--->"+dcid);
    	   	 				 cmobject=lkd.getCustomer(dcid);
    	   	 				 String perPath=MenuNameReader.getScreenProperties("9035");
    	   	 				 httpServletRequest.setAttribute("DepNumInfo", int_acno);
    	   	 				 httpServletRequest.setAttribute("DepTypInfo",string_code);
    	   	 				 httpServletRequest.setAttribute("personalInfo",cmobject);
    	   	 				 httpServletRequest.setAttribute("depositInfo",object);
    	   	 				 httpServletRequest.setAttribute("flag",perPath);
    	   	 				 httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
   	   	 		 
    	   	 				
    	   	 			  }
    	   	 			  catch (Exception e) 
    	   	 			  {
    	   	 				  System.out.println("inside exception deposit");
    	   	 				 LKExtnObj.setValidateFlag("Deposit Details Not Found");
    	   	 				 e.printStackTrace();
						  }
						}
						else
						{
							LKExtnObj.setValidateFlag("Deposit Details Not Found");
						}
	                 }
					  if(LKExtnObj.getSelect_details().equalsIgnoreCase("signature"))
					  {
						  if(lockermasterobject.getSigObj()!=null)
						  {
							String perPath = MenuNameReader.getScreenProperties("0007");
							sObject= lkd.getSignatureDetails(lockermasterobject.getMemberNo(),lockermasterobject.getShareCode().trim());
							httpServletRequest.setAttribute("signInfo", sObject);
							httpServletRequest.setAttribute("flag",perPath);
							httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
							System.out.println("flag gonna set");
						  }
						  else
						  {
							  LKExtnObj.setValidateFlag("Signature Details Not Found");
						  }
					  }
					  if(LKExtnObj.getSelect_details().trim().equalsIgnoreCase("personal")) 
						{
							System.out.println("after personal");
							String perPath = MenuNameReader.getScreenProperties("0001");
							int cid = lockermasterobject.getCid();
							cmobject = lkd.getCustomer((cid));
							httpServletRequest.setAttribute("personalInfo", cmobject);
							httpServletRequest.setAttribute("flag",perPath);
							httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
							System.out.println("flag gonna set");

						}
					}
					else
					{
						LKExtnObj.setValidateFlag("Enter Valid Locker A/c Num");
					}
				}
				getLkAbbr(httpServletRequest, path, lkd);
				httpServletRequest.setAttribute("DepositTypes",moduleObjectDep); 
				httpServletRequest.setAttribute("transferTypes", moduleObject222);
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward(ResultHelp.getSuccess());

			}
			else 
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}
		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKIssueMenuLink")) 
		{
			try 
			{
				System.out.println("can-i-...  create lkisform ");
				ModuleObject moduleobject_introducer[] = null;
				LockerIssueForm lockerIssueForm  = (LockerIssueForm)actionForm;
				System.out.println("my page id " + lockerIssueForm.getPageId());
				lockerIssueForm.setValidateFlag("");
				System.out.println("wen i printed 2nd time my page id "+ lockerIssueForm.getPageId());
				if (MenuNameReader.containsKeyScreen(lockerIssueForm.getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(lockerIssueForm.getPageId());
					System.out.println("wen i gonelockerIssueForm thro Reader" + "0");
					lockerIssueForm.setLkacNum("0");
					moduleobject_introducer = lkd.getMainModules("1002000,1007000,1001000");
					ModuleObject[] transfer=lkd.getMainModules("1002000,1007000");
					String date1 = lkd.getSysDate();
					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);
					ModuleObject[] moduleObject=lkd.getMainModules(2);
					
        		    httpServletRequest.setAttribute("DepositTypes",moduleObject); 
        		 	httpServletRequest.setAttribute("introtypes",moduleobject_introducer);
        		 	httpServletRequest.setAttribute("transfertypes",transfer);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}
				else 
				{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
			} 
			catch (Exception e) 
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKIssueLink")) 
		{

			LockerIssueForm lockerIssueForm = (LockerIssueForm) actionForm;
			try 
			{

				ModuleObject moduleobject_introducer[] = null;
			    
				if(MenuNameReader.containsKeyScreen(lockerIssueForm.getPageId())) 
				{
					lockerIssueForm.setValidateFlag("");
					String jointholder="";
					String Nominee="";
					moduleobject_introducer = lkd.getMainModules("1002000,1007000");
					ModuleObject[] transfer=lkd.getMainModules("1002000,1007000,1001000");
					System.out.println(lockerIssueForm.getForward()+ "?#####<------------>######"+ lockerIssueForm.getDetails());
					
					if (!lockerIssueForm.getDetails().trim().equals("SELECT")) 
					{	

						if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("personal")) 
						{
							System.out.println("after personal");
							String perPath = MenuNameReader.getScreenProperties("0001");
							String cid = lockerIssueForm.getTxt_cid();
							cmobject = lkd.getCustomer(Integer.parseInt(cid));
							httpServletRequest.setAttribute("personalInfo", cmobject);
							httpServletRequest.setAttribute("flag",perPath);
							httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
							System.out.println("flag gonna set");

						}
						if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("Introducer")) 
						{

							if((lockerIssueForm.getIntrNum())!=null)
							{
								int intacNum = Integer.parseInt(lockerIssueForm.getIntrNum());
								String intacType = lockerIssueForm.getIntrType();
								String perPath = MenuNameReader.getScreenProperties("0001");
								System.out.println("NUMMMM" + intacNum+ "TYPEEE" + intacType);
								accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
								if(accountobject!=null)
								{
									cmobject = lkd.getCustomer(accountobject.getCid());
									httpServletRequest.setAttribute("personalInfo", cmobject);
									httpServletRequest.setAttribute("flag",perPath);
									httpServletRequest.setAttribute("panelName", CommonPanelHeading.getIntroucer());
								}
								else
								{
								 lockerIssueForm.setValidateFlag("Introducer Account Num Found");
								}	
						
							}
							else
							{
								lockerIssueForm.setValidateFlag("Introducer Field Can't Be Left Blank");
							}
					 }
					 if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("Signature")) 
					 {
						
						System.out.println("after personal");

						String perPath = MenuNameReader.getScreenProperties("0007");
						sObject= lkd.getSignatureDetails(Integer.parseInt(lockerIssueForm.getMembAc().trim()),lockerIssueForm.getMembType().trim());
						httpServletRequest.setAttribute("signInfo", sObject);
						httpServletRequest.setAttribute("flag",perPath);
						httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
						System.out.println("flag gonna set");

					 }
					 if(lockerIssueForm.getDetails().trim().equalsIgnoreCase("Nominee")) 
					 {
						
						System.out.println("after Nominee");
						Nominee="nomineeDetails";
						httpServletRequest.setAttribute("nomdetail",Nominee);
						
						if(lockerIssueForm.getForward().equalsIgnoreCase("nomineecid"))
						{
							if(lockerIssueForm.getNomineeCid()!=null)
							{
								int ncid=Integer.parseInt(lockerIssueForm.getNomineeCid());
									cmobject=lkd.getCustomer(ncid);
									if(cmobject!=null)
									{
										lockerIssueForm.setNomineeName(cmobject.getName());
										lockerIssueForm.setNomineeDob(cmobject.getDOB());
										lockerIssueForm.setNomineeAge(cmobject.getDOB());
										lockerIssueForm.setNomineeAddress(cmobject.getAddress().toString());
										lockerIssueForm.setNomineeSex(cmobject.getSex());
									}
									else
									{
										lockerIssueForm.setValidateFlag("Nominee ID Not Found");
									}
							 }
						}
						
						
					 }
					 if(lockerIssueForm.getDetails().trim().equalsIgnoreCase("Deposit"))
	    			 {
						 		 LockerMasterObject lockermasterobject=new LockerMasterObject();
						 		 System.out.println("after deposit");
	    	        		   
	    	        		     String actype=lockerIssueForm.getCombo_details();
	    	     	   	 		     String accnum=lockerIssueForm.getAcc_no();
	    	     	   	 			 System.out.println("combo details of deposit===>"+actype);
	    	     	   	 			 System.out.println("accnum of deposit==>"+accnum);
	    	     	   	 			 
	    	     	   	 			 try
	    	     	   	 			 {
	    	     	   	 				 DepositMasterObject object=lkd.getDepositMaster(actype,Integer.parseInt(accnum));
	    	     	   	 				 System.out.println("deposit master object==>"+object);	
	    	     	   	 				 int dcid=object.getCustomerId();
	    	     	   	 				 System.out.println("customer id--->"+dcid);
	    	     	   	 				 cmobject=lkd.getCustomer(dcid);
	    	     	   	 				 String perPath=MenuNameReader.getScreenProperties("9035");
	    	     	   	 				 httpServletRequest.setAttribute("personalInfo",cmobject);
	    	     	   	 				 httpServletRequest.setAttribute("depositInfo",object);
	    	     	   	 				 httpServletRequest.setAttribute("flag",perPath);
	    	     	   	 				 httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
	   	     	   	 		 
	    	     	   	 				
	    	     	   	 			  }
	    	     	   	 			  catch (Exception e) 
	    	     	   	 			  {
	    	     	   	 				  System.out.println("inside exception deposit");
	    	     	   	 				 lockerIssueForm.setValidateFlag("Deposit Details Not Found");
	    	     	   	 				 e.printStackTrace();
									  }
	    	     	   	 	
	    	     	   	 		 
	    	        	 }
						if(lockerIssueForm.getDetails().trim().equalsIgnoreCase("JointHolders"))
						{
							jointholder="jointholder";
							httpServletRequest.setAttribute("JOINT",jointholder);
							String perPath = MenuNameReader.getScreenProperties("0001");
							
								if(lockerIssueForm.getCid().trim()!=null && lockerIssueForm.getCid().trim().length()!=0)
								{
									cmobject=lkd.getCustomer(Integer.parseInt(lockerIssueForm.getCid().trim()));
									if(cmobject!=null)
									{
										httpServletRequest.setAttribute("flag",perPath);
										httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
										httpServletRequest.setAttribute("personalInfo",cmobject);
										System.out.println("flag gonna set");
									}
									else
									{
										lockerIssueForm.setValidateFlag("CustomerID Not Found");
									}
								}
								else 
								{
									lockerIssueForm.setValidateFlag("JointHolder Details Not Found");
									
								}	
					
					    }
				     }
					
					if(lockerIssueForm.getForward().equalsIgnoreCase("Cid"))	
					{
						    System.out.println("inside if ccccccccccccccccid");
						
							if (lockerIssueForm.getTxt_cid().trim() != null) 
							{
								
								System.out.println("################## WEN CID NNNNOOOO=NULL ###########################");
								try{
								CustomerMasterObject customerMasterObject = lkd.getCustomer(Integer.parseInt(lockerIssueForm.getTxt_cid().trim()));
								
								if(customerMasterObject!=null)
								{
									accountobject = lkd.getShareAccount(Integer.parseInt(lockerIssueForm.getTxt_cid()));
									if(accountobject!=null)
									{
										lockerIssueForm.setMembType(accountobject.getAcctype());
										lockerIssueForm.setMembAc(String.valueOf(accountobject.getAccno()));
										System.out.println("At 1964 share acnt no is account "+ accountobject.getAccno());
										System.out.println("share type "+ accountobject.getAcctype());
									}
									else
									{
										lockerIssueForm.setValidateFlag("Customer Is Not A Share Holder");
										lockerIssueForm.setTxt_cid("");
									}
								}
								else
								{
									lockerIssueForm.setValidateFlag("Customer ID Not Found");
									lockerIssueForm.setTxt_cid("");
								}
							 }
							 catch (Exception e) {
								 lockerIssueForm.setValidateFlag("Customer ID Not Found");
								 lockerIssueForm.setTxt_cid("");
								}
							
						}
						lockerIssueForm.setLkacNum("0");	
						
					
				    }
					if(lockerIssueForm.getDays().trim()!= null && lockerIssueForm.getDays().trim().length()!=0) 
					{
				
						System.out.println("################# INSIDE GET DAYS #######################");
						System.out.println(lockerIssueForm.getExpDate()+ "<===========>"+ lockerIssueForm.getAllotDate()+ "LOCKERTYPE frm ACTION"+ lockerIssueForm.getLkacType()+ "LKTYPE**" + lockerIssueForm.getLkType());
						String days = lockerIssueForm.getDays();
						System.out.println("days when s" +lockerIssueForm.getDays());
						String months = lockerIssueForm.getMonths();
						System.out.println("months whens"+ lockerIssueForm.getMonths());
						System.out.println("expirydate"+lockerIssueForm.getExpDate());
						String[] rentExpirydate = lkd.getDaysRent(lockerIssueForm.getAllotDate(), days,months, lockerIssueForm.getExpDate(),lockerIssueForm.getLkType());
						if(rentExpirydate!=null)
						{
							lockerIssueForm.setExpDate(rentExpirydate[1]);
							lockerIssueForm.setLkRent(rentExpirydate[0]);
						}
						else
						{
							lockerIssueForm.setValidateFlag("Enter Valid Num Of Months And Days");
						}
					}
					if(lockerIssueForm.getForward().equalsIgnoreCase("IntrNum"))
					{	
						if((lockerIssueForm.getIntrNum())!=null && lockerIssueForm.getIntrNum().length()!=0)
						{
							int intacNum = Integer.parseInt(lockerIssueForm.getIntrNum());
							String intacType = lockerIssueForm.getIntrType();
							System.out.println("NUMMMM--" + intacNum+ "--TYPEEE--" + intacType);
							accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
							if(accountobject!=null)
							{
								lockerIssueForm.setIntrNum(String.valueOf(intacNum));
								System.out.println("jfjejdjdjdjdyuws"+intacNum);
							}
							else
							{
								lockerIssueForm.setValidateFlag("Introducer Account Num Found");
								lockerIssueForm.setIntrNum("");
							}	
				
						}
					}	
					if(lockerIssueForm.getForward().equalsIgnoreCase("depositnum"))
    	   	 		{
						 try
						 {
							 DepositMasterObject object=lkd.getDepositMaster(lockerIssueForm.getCombo_details(),Integer.parseInt(lockerIssueForm.getAcc_no()));
							 System.out.println("deposit master object==>"+object);	
							 if(object!=null)
							 {
								 String sub=lockerIssueForm.getCombo_details().substring(1,4);
							 	if(object.getVerified().equals("F"))
								{	
									lockerIssueForm.setValidateFlag("Deposit Account number is not yet verified");
									lockerIssueForm.setAcc_no("");
								}
								else if(object.getLoanAvailed().equals("T"))//ship.....04/02/2006....added type==0
								{	
									lockerIssueForm.setValidateFlag("Loan is already availed on this Deposit");
									lockerIssueForm.setAcc_no("");
								}
								else if(object.getClosedt()!=null)
								{	
									lockerIssueForm.setValidateFlag("Deposit Account is closed");
									lockerIssueForm.setAcc_no("");
								}
								else if((sub.equals("003") || sub.equals("004") || sub.equals("005"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
								{	
									
									lockerIssueForm.setValidateFlag("Maturity Date lapsed");
									lockerIssueForm.setAcc_no("");
								}	
								else if((sub.equals("006"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
								{	
									
									lockerIssueForm.setValidateFlag("Minimum Period for Deposit lapsed");
									lockerIssueForm.setAcc_no("");
								}
								else if((sub.equals("006"))?object.getCloseStart().equals("C"):false)
								{	
									
									lockerIssueForm.setValidateFlag("Deposit Account is closed");
									lockerIssueForm.setAcc_no("");
								}
							 }
							 else
							 {
								 lockerIssueForm.setValidateFlag("Deposit Account Not Found");
								 lockerIssueForm.setAcc_no("");
							 }
								 
						 }
						 catch (Exception e) 
						 {
							lockerIssueForm.setValidateFlag("Deposit Account Details Not Found");
							lockerIssueForm.setAcc_no("");
						 }
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("jointcid"))
					{
						if(lockerIssueForm.getCid().trim()!=null && lockerIssueForm.getCid().trim().length()!=0)
						{
							cmobject=lkd.getCustomer(Integer.parseInt(lockerIssueForm.getCid().trim()));
							if(cmobject==null)
							{
								lockerIssueForm.setValidateFlag("CustomerID Not Found");
								lockerIssueForm.setCid("");
							}
							
						}
						else
						{
							lockerIssueForm.setValidateFlag("CustomerID Field Can't Be Left Blank");
						}
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("ScrollNum"))
					{
						 AccountObject accountObject=null;
						if(lockerIssueForm.getScrollNum()==null)
				        {
				            lockerIssueForm.setValidateFlag("Enter the Scroll Number");
				        }
				        else if(lockerIssueForm.getScrollNum()!=null && Integer.parseInt(lockerIssueForm.getScrollNum())!=0)
				        {
				            try 
				            {
				                accountObject=lkd.getAccount("C",lockerIssueForm.getLkacType(),Integer.parseInt(lockerIssueForm.getScrollNum()),lkd.getSysDate());
				            } 
				            catch(NumberFormatException e) 
				            {
				                e.printStackTrace();
				            }
				            catch(RemoteException e)
				            {
				                e.printStackTrace();
				            }
				            
				            if(accountObject==null)
				            {
				                lockerIssueForm.setValidateFlag("Scroll Number is not found");
				                lockerIssueForm.setScrollNum("");
				            }
				            else
				            {
				                String string_modulecode="";
				                System.out.println("inside getCashDetails......");
				                
				                string_modulecode=lockerIssueForm.getLkacType();
				                
				                System.out.println("mod_code......"+string_modulecode);
				                System.out.println("ac_obj mod_code......."+accountObject.getAcctype());
				                System.out.println("txt_period_rent = "+(lockerIssueForm.getLkRent()));
				                System.out.println("acobj getAmount = "+accountObject.getAmount());
				                
				                if(lockerIssueForm.getLkRent().length()!=0)
				                {
				                	if(accountObject.getAccStatus().equals("C"))
				                		lockerIssueForm.setValidateFlag("Scroll Number Already Attached");
				                	else if(accountObject.getAmount()==Double.parseDouble(lockerIssueForm.getLkRent()) && accountObject.getAcctype().equals(string_modulecode))
				                	{
				                		System.out.println("@@@@@@@inside getCashDetails......");
				                		lockerIssueForm.setDate(lkd.getSysDate());
				                		//txt_account_name.setText(accountObject.getAccname());
				                		lockerIssueForm.setAmount(String.valueOf(accountObject.getAmount()));
				                    }
				                	else
				                	{
				                		lockerIssueForm.setValidateFlag("Scroll Number Amount Doesn't Match With Rent Amount");
				                		lockerIssueForm.setScrollNum("0");
				                	}
				                }	
				            }
				        }
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("TrnAcNum"))
					{
						  System.out.println("inside getAccountDetails");
					        
					        AccountObject accountObject=null;
					        
					        try
					        {
					            accountObject=lkd.getAccount(null,lockerIssueForm.getTransferAcntType(),Integer.parseInt(lockerIssueForm.getTransferAcntNum()),lkd.getSysDate());
					           // System.out.println(" ac type :"+array_moduleobject_savings[combo_account_type.getSelectedIndex()].getModuleCode()+" ac no :"+txt_account_no.getText());            
					        } 
					        catch(NumberFormatException e) 
					        {
					            e.printStackTrace();
					        } 
					        catch(RemoteException e) 
					        {
					            e.printStackTrace();
					        }
					        if(accountObject==null)
					        {
					            lockerIssueForm.setTransferAcntNum("");
					            lockerIssueForm.setValidateFlag("Account Number is not found");
					        }
					        else
					        {
					            System.out.println("accountObject.getAmount() = "+accountObject.getAmount());
					            
					            if(accountObject.getAmount()< Double.parseDouble(lockerIssueForm.getLkRent()))
					            {
					            	lockerIssueForm.setTransferAcntNum("");
					                lockerIssueForm.setValidateFlag("No enough Amount in the Account ");
					                
					            }
					            else if(accountObject.getAmount()==0.0)
					            {
					               
					                lockerIssueForm.setValidateFlag("No Amount in the Account");
					                lockerIssueForm.setTransferAcntNum("");
					            }
					            else
					            {
					                System.out.println("inside valid balance");
					             }
					        }
					}
					else if (lockerIssueForm.getForward().equalsIgnoreCase("submit")) 
					{

							
							LockerMasterObject lockermasterobject =null;
							 int proceed = 0;
							 int int_locker_no=0;
							 int test_lk_acno = 0;
							 boolean continue_transaction = false;
							
							System.out.println("inside submitt of issueee");
							try
				            {
				                if(lockerIssueForm.getReceiptDetails().equalsIgnoreCase("C"))//Cash
				                    proceed = lkd.checkDailyStatus(lkd.getSysDate(),0);
				                else if(lockerIssueForm.getReceiptDetails().equalsIgnoreCase("T"))//Transfer
				                    proceed = lkd.checkDailyStatus(lkd.getSysDate(),1);
				            }
				            catch(Exception ex)
				            {
				                ex.printStackTrace();
				            }
							
				            if(proceed==3)//day close
				            {
				                
				                lockerIssueForm.setValidateFlag("Day closed\n You can't do any Transactions");
				                continue_transaction = false;
				                
				            }
				            else if(proceed==1)//cash close
				            {
				                
				                lockerIssueForm.setValidateFlag("Cash closed\n You can't do any Transactions");
				                continue_transaction = false;
				               
				            }
				            else if(proceed==2)//gl posting
				            {
				                int result = JOptionPane.showConfirmDialog(null," GL Posting has been done\n Do You want to continue the Transaction ?","GL Posting done",JOptionPane.YES_NO_OPTION);
				                
				                if(result==JOptionPane.YES_OPTION)
				                {
				                    try
				                    {
				                        boolean glposting = lkd.updatePostInd("Head Office",lkd.getSysDate());
				                        
				                        if(glposting==true)
				                        {
				                            continue_transaction = true;
				                        }
				                        else if(glposting==false)
				                        {
				                            
				                            continue_transaction = false;
				                            lockerIssueForm.setValidateFlag("Error : Unable to Proceed");
				                        }
				                    }
				                    catch(Exception ex)
				                    {
				                        ex.printStackTrace();
				                    }
				                
				                }
				            }
				            else if(proceed==-1)
				            {
				                
				                continue_transaction = false;
				                lockerIssueForm.setValidateFlag("Error : no entry in Daily status for today");
				            }
				            else
				                continue_transaction = true;
				            
				            if(continue_transaction==true)
				            {
				            				lockermasterobject=new LockerMasterObject();
				            				
											System.out.println("inside of cond true of submit");
				            				if(lockerIssueForm.getNomineeCid()!=null || lockerIssueForm.getNomineeName()!=null)
				            				{
				            						arrayNomineeObject=new NomineeObject[1];
				            						arrayNomineeObject[0]=new NomineeObject();
				            							if(lockerIssueForm.isHasAccount())
				            							{
				            								arrayNomineeObject[0].setCustomerId(Integer.parseInt(lockerIssueForm.getNomineeCid()));
				            							}
				            							else
				            							{
				            								arrayNomineeObject[0].setCustomerId(0);
					            							
				            							}
				            						
				            							arrayNomineeObject[0].setNomineeName(lockerIssueForm.getNomineeName());
				            							arrayNomineeObject[0].setNomineeDOB(lockerIssueForm.getNomineeDob());
				            							arrayNomineeObject[0].setNomineeAddress(lockerIssueForm.getNomineeAddress());
//											    		nObjects.setSex((lockerIssueForm.getNomineeSex()));
				            							arrayNomineeObject[0].setNomineeRelation(lockerIssueForm.getNomineeRelationship());
				            							arrayNomineeObject[0].setPercentage(Integer.parseInt(lockerIssueForm.getNomineePercentage()));
				            						
				            						lockermasterobject.setNomineeDetails(arrayNomineeObject);
				            					}
				            				else
				            				{
				            					lockermasterobject.setNomineeDetails(null);
					            				
				            				}
				            				
				            				 test_lk_acno = Integer.parseInt(lockerIssueForm.getLkacNum());
				            				lockermasterobject.setLockerAcType(lockerIssueForm.getLkacType());
				            				lockermasterobject.setLockerAcNo(test_lk_acno);
				            				// lockermasterobject.setLockerAcNo(Integer.parseInt(lockerIssueForm.getLkacNum()));//watch
				            				// it out
				            				lockermasterobject.setCid(Integer.parseInt(lockerIssueForm.getTxt_cid()));
				            				// lockermasterobject.setMailingAddress();

				            				System.out.println("<-->"+ lockerIssueForm.getLkNum() + "<-->"+ lockerIssueForm.getAllotDate() + "<-->"+ lockerIssueForm.getMonths() + "<-->"+ lockerIssueForm.getDays() + "<-->"+ lockerIssueForm.getExpDate() + "<-->"+ lockerIssueForm.getMembType());
				            				System.out.println(lockerIssueForm.getMembAc()+ "<-->" + lockerIssueForm.getMembType());
				            				lockermasterobject.setLockerType(lockerIssueForm.getLkType());// s m l
				            				System.out.println("1++++++++++++++++++++++++++++++");
				            				lockermasterobject.setLockerNo(Integer.parseInt(lockerIssueForm.getLkNum()));
				            				System.out.println("2++++++++++++++++++++++++++++++");
				            				lockermasterobject.setTrnDate(LockerDelegate.getSysDate());

				            				if(lockerIssueForm.isRequired())
				            				{
				            					if(lockerIssueForm.getPass()!=null)
				            						lockermasterobject.setLockerPW(lockerIssueForm.getPass()); 
				            					else
				            					  lockermasterobject.setLockerPW("");
				            				
				            				}
				            				else
				            				{
				            					lockermasterobject.setLockerPW("");
				            				}
				            				try 
				            				{
				            					lockermasterobject.setAllotDate(Validations.checkDate(lockerIssueForm.getAllotDate()));
				            				}
				            				catch (DateFormatException e2) 
				            				{
				            					e2.printStackTrace();
				            				}
				            				System.out.println("3++++++++++++++++++++++++++++++");
				            				lockermasterobject.setReqdMonths(Integer.parseInt(lockerIssueForm.getMonths()));
				            				System.out.println("4++++++++++++++++++++++++++++++");
				            				lockermasterobject.setRequiredDays(Integer.parseInt(lockerIssueForm.getDays()));

				            				try 
				            				{
				            					lockermasterobject.setMatDate(Validations.checkDate(lockerIssueForm.getExpDate()));// Expiry
				            					// date
				            				} 
				            				catch (DateFormatException e3) 
				            				{
				            					e3.printStackTrace();
				            				}

				            				System.out.println("5++++++++++++++++++++++++++++++");
				            				// String
				            				// string_share_modulecode=array_moduleobject_shares[combo_member_type.getSelectedIndex()].getModuleCode();
				            				lockermasterobject.setShareCode(lockerIssueForm.getMembType());
				            				lockermasterobject.setMemberNo(Integer.parseInt(lockerIssueForm.getMembAc()));
				            				// lockermasterobject.setNoOfJntHldrs(Integer.parseInt(txt_joint_holders.getText()));

				            				// Commended these lines because to incoporate new
				            				// changes in jointholders screen
				            				if(!(lockerIssueForm.getOpInstr().equalsIgnoreCase("Individual")))
				            				{
				            					if(lockerIssueForm.getCid()!=null)
				            					{
				            						int jointc[]=new int[1];
				            						int addtyp[]=new int[1];
				            						jointc[0]=Integer.parseInt(lockerIssueForm.getCid());
				            						addtyp[0]=Integer.parseInt("1");
				            						System.out.println("------joint ----cid---submit----"+jointc[0]);
				            						lockermasterobject.setJointCid(jointc);
				            						lockermasterobject.setAddr(addtyp);
				            					}
				            				}	
				            				else
				            				{
				            					lockermasterobject.setJointCid(null);
			            						lockermasterobject.setAddr(null);
				            				}
				            					
				            				System.out.println("6++++++++++++++++++++++++++++++");
				            				lockermasterobject.setOprInstrn(lockerIssueForm.getOpInstr());
				            				System.out.println("7++++++++++++++++++++++++++++"+ lockerIssueForm.getScrollNum()+ "<--------->"+ lockerIssueForm.getTransferAcType());

				            				if (lockerIssueForm.getReceiptDetails().equalsIgnoreCase("T")) 
				            				{
				            					System.out.println(lockerIssueForm.getTransferAcntType()+ "Money flows thro transfer"+ lockerIssueForm.getTransferAcntNum());
				            					lockermasterobject.setRentBy("T");
				            					lockermasterobject.setTransAcType(lockerIssueForm.getTransferAcntType());
				            					lockermasterobject.setTransAcNo(Integer.parseInt(lockerIssueForm.getTransferAcntNum()));
				            				}

				            				if (lockerIssueForm.getReceiptDetails().equalsIgnoreCase("C")) 
				            				{
				            					System.out.println("-->money flows thro Cash");
				            					lockermasterobject.setRentBy("C");
				            					System.out.println("money flows thro Cash"+ lockerIssueForm.getScrollNum());
				            					lockermasterobject.setTransAcType(null);
				            					System.out.println("money flows thro Cash");
				            					lockermasterobject.setTransAcNo(Integer.parseInt(lockerIssueForm.getScrollNum()));
				            				}

				            				try 
				            				{
				            					lockermasterobject.setRentUpto(Validations.checkDate(lockerIssueForm.getExpDate()));
				            				}
				            				catch (DateFormatException e1) 
				            				{
				            					e1.printStackTrace();
				            				}
				            				System.out.println("8++++++++++++++++++++++++++++++");
				            				lockermasterobject.setRentColl(Double.parseDouble(lockerIssueForm.getLkRent()));

				            				// ship......06/03/2006
				            				if(lockerIssueForm.getAutoExtn().equalsIgnoreCase("YES"))
				            					lockermasterobject.setAutoExtn("T");
				            				else if(lockerIssueForm.getAutoExtn().equalsIgnoreCase("NO"))
				            					lockermasterobject.setAutoExtn("F");
				            				// //////////////////

				            				if ((!lockerIssueForm.getIntrNum().equalsIgnoreCase("0"))&& lockerIssueForm.getIntrNum() != null && (!lockerIssueForm.getIntrNum().equalsIgnoreCase(""))) 
				            				{
				            					lockermasterobject.setIntrAcTy(lockerIssueForm.getIntrType());
				            					lockermasterobject.setIntrAcNo(Integer.parseInt(lockerIssueForm.getIntrNum()));
				            				} 
				            				else 
				            				{
				            					lockermasterobject.setIntrAcTy("");
				            					lockermasterobject.setIntrAcNo(0);
				            					lockermasterobject.setIntrName("");
				            				}

				            				lockermasterobject.setNomRegNo(0);
				            				lockermasterobject.uv.setUserTml(tml);
				            				lockermasterobject.uv.setUserId(uid);
				            				lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
				            				
				            				if(lockerIssueForm.getAcc_no()!=null && lockerIssueForm.getAcc_no().length()!=0)
				            				{
				            					//deposit master  
				            					
				            					String depactyp=lockerIssueForm.getCombo_details();
				            					String depacnum=lockerIssueForm.getAcc_no();
				            					System.out.println("depAcNum).toString()--->"+depacnum);
				            					System.out.println("depAcType).toString()--->"+depactyp);
				            					Vector vector_accounts=null;
				            					vector_accounts=new Vector(0,1);
				            					vector_accounts.add(depactyp+" "+depacnum);
				            					lockermasterobject.setDeposits(vector_accounts);
				            					lockermasterobject.setNoOfSecurities(1);
				            				}
				            				else
				            				{
				            					lockermasterobject.setDeposits(null);
				            					lockermasterobject.setNoOfSecurities(0);
				            					
				            				}
				            					try 
				            					{
				            						System.out.println("inside try - storeLockerMaster");
				                                  	int_locker_no = lkd.storeLockerMaster(lockermasterobject, 1);
				                                  	System.out.println("outside try - storeLockerMaster"+ int_locker_no);
				            					}  
				            					catch (Exception e) 
				            					{
				            						e.printStackTrace();
				            					}     	
				                                if(int_locker_no>0 &&  Integer.parseInt(lockerIssueForm.getLkacNum())==0)
					                            {
				                                	lockerIssueForm.setValidateFlag("Note down the Locker Ac No:"+ int_locker_no);
				                                  	
				                                } 
				                                else if(int_locker_no>0 &&  Integer.parseInt(lockerIssueForm.getLkacNum())!=0)
				            					{
				                                	lockerIssueForm.setValidateFlag(" Locker Ac No:"+ int_locker_no+"Updated Successfully");
				                                  	
				            					}
				                                else
				                                {
				                                	lockerIssueForm.setValidateFlag("Error! Try Again.");
				                                  	
				                                }
				            				

						
				            }
				            
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("LkNum"))
					{
						if(lockerIssueForm.getLkacNum().trim()!=null && lockerIssueForm.getLkacNum().trim().length()!=0)
						{
							LockerMasterObject lockermasterobject = lkd.getLockerMaster(Integer.parseInt(lockerIssueForm.getLkacNum().trim()), 0);
							if (lockermasterobject != null) 
							{
								if (lockermasterobject.getLockerNo() == -1)
								{
									lockerIssueForm.setValidateFlag("Locker Not found");
									lockerIssueForm.setLkacNum("");
								}
								else if (lockermasterobject.uv.getVerId() != null)
								{
									lockerIssueForm.setValidateFlag("Already verified");
									lockerIssueForm.setLkacNum("");
								}		
								else if (lockermasterobject.getClosingDEDtTime() != null)
								{
									lockerIssueForm.setValidateFlag("Locker Already alloted");
									lockerIssueForm.setLkacNum("");
								}
								else 
								{
									// all setting code goes here
									System.out.println("all setting code goes here");
									lockerIssueForm.setTxt_cid(String.valueOf(lockermasterobject.getCid()));
									lockerIssueForm.setLkNum(String.valueOf(lockermasterobject.getLockerNo()));
									lockerIssueForm.setLkType(lockermasterobject.getLockerType());
									lockerIssueForm.setLkKey(String.valueOf(lockermasterobject.getKeyNo()));
									lockerIssueForm.setAllotDate(lockermasterobject.getAllotDate());
									lockerIssueForm.setExpDate(lockermasterobject.getMatDate());
									lockerIssueForm.setMembAc(String.valueOf(lockermasterobject.getMemberNo()));
									lockerIssueForm.setOpInstr(lockermasterobject.getOprInstrn());
									lockerIssueForm.setReceiptDetails(lockermasterobject.getRentBy());
									lockerIssueForm.setTransferAcntNum(String.valueOf(lockermasterobject.getTransAcNo()));
									lockerIssueForm.setTransferAcntType(lockermasterobject.getTransAcType());
									lockerIssueForm.setIntrType(lockermasterobject.getIntrAcTy());
									lockerIssueForm.setIntrNum(String.valueOf(lockermasterobject.getIntrAcNo()));
									System.out.println("introducer nbu,m   inlk num--"+lockermasterobject.getIntrAcNo());
									lockerIssueForm.setNomReg(String.valueOf(lockermasterobject.getNomRegNo()));
									lockerIssueForm.setLkRent(String.valueOf(lockermasterobject.getRentColl()));
									lockerIssueForm.setMonths(String.valueOf(lockermasterobject.getReqdMonths()));
									lockerIssueForm.setDays(String.valueOf(lockermasterobject.getRequiredDays()));
									lockerIssueForm.setAutoExtn(lockermasterobject.getAutoExtn());
									if(lockermasterobject.getNomineeDetails()!=null)
									{
										NomineeObject[] objects=lockermasterobject.getNomineeDetails();
										if(objects!=null)
										{
											if(objects[0].getCustomerId()!=0)
											{
											lockerIssueForm.setNomineeCid(String.valueOf(objects[0].getCustomerId()));
											lockerIssueForm.setHasAccount(true);
											}
											lockerIssueForm.setNomineeName(objects[0].getNomineeName());
											lockerIssueForm.setNomineeAddress(objects[0].getNomineeAddress());
											lockerIssueForm.setNomineeDob(objects[0].getNomineeDOB());
											//lockerIssueForm.setNomineeAge(objects[0].getSex());
											lockerIssueForm.setNomineePercentage(String.valueOf(objects[0].getPercentage()));
											lockerIssueForm.setNomineeRelationship(objects[0].getNomineeRelation());
											
										}
									}
									if(lockermasterobject.getLockerPW()!=null)
									{
										lockerIssueForm.setPass(lockermasterobject.getLockerPW());
										lockerIssueForm.setRequired(true);
									}
									if(lockermasterobject.getNoOfSecurities()>0)
									{
										Vector vector_deposits = null;
		                                
		                                if(lockermasterobject.getDeposits()!=null)
				                        {
				                           
				                            vector_deposits=lockermasterobject.getDeposits();
				                            System.out.println("lockermasterobject.getDeposits size = "+vector_deposits.size());
				                            Enumeration enumeration=vector_deposits.elements();
				                            while(enumeration.hasMoreElements())
				                            {
				                            	StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
				                            	String string_code=tokenizer.nextToken();
				                            	int int_acno=Integer.parseInt(tokenizer.nextToken());
				                            	lockerIssueForm.setAcc_no(String.valueOf(int_acno));	
				                        
				                            }
				                            
				                        }
		                                
									}
									if(lockermasterobject.getJointCid()!=null)
									{
										int cid[]=lockermasterobject.getJointCid();
										lockerIssueForm.setCid(String.valueOf(cid[0]));	
									}
									//httpServletRequest.setAttribute("LockerMasterObject",lockermasterobject);
								}
							} 
							else
							{
								lockerIssueForm.setValidateFlag("Locker Account Not Found");
								lockerIssueForm.setLkacNum("");
							}
						}
						else
						{
							lockerIssueForm.setLkacNum("0");
						}
					}
					else if (lockerIssueForm.getForward().equalsIgnoreCase("delete")) 
					{
						LockerMasterObject lockermasterobject = lkd.getLockerMaster(Integer.parseInt(lockerIssueForm.getLkacNum()), 0);
						if (lockermasterobject != null) 
						{
							lkd.deleteLockerAccount(lockermasterobject, 1);
							lockerIssueForm.setValidateFlag("Successfuly Deleted");
						}
						else
						{
							lockerIssueForm.setValidateFlag("Locker Account Not Found");
							lockerIssueForm.setLkacNum("");
						}
					}

					String date1 = lkd.getSysDate();
					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);
					ModuleObject[] moduleObject=lkd.getMainModules(2);
        		    httpServletRequest.setAttribute("DepositTypes",moduleObject); 
        		 	httpServletRequest.setAttribute("introtypes",moduleobject_introducer);
        		 	httpServletRequest.setAttribute("transfertypes",transfer);
					httpServletRequest.setAttribute("pageId", path);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerIssueForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				}
				else
					return actionMapping.findForward(ResultHelp.getSuccess());

			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return actionMapping.findForward(ResultHelp.getSuccess());

			}

		}

		else if (actionMapping.getPath().equalsIgnoreCase("/LKTableMenuLink")) 
		{

			try {
				LockerTableForm lktabform = (LockerTableForm) actionForm;
				System.out.println("#@!@#$%#$%$");
				System.out.println("" + lktabform.getPageId());

				if (MenuNameReader.containsKeyScreen(lktabform.getPageId())) {

					String path = MenuNameReader.getScreenProperties(lktabform.getPageId());
					System.out.println("^^&path frm tab pane is" + path+ "and selected type is==++ "+ lktabform.getLkTypes());

					LockerDetailsObject lockerDetailsObject[] = lkd.getCabinStructure(lktabform.getLkTypes());

					getTempLockersTypes(httpServletRequest, path, lkd);
					// getLockersTypes(httpServletRequest,path,lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());

				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(
							"Please check ! There is no name for the given key in the properties file",
							httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().equalsIgnoreCase("/LKTableLink")) 
		{

			try {
				LockerTableForm lktabform1 = (LockerTableForm) actionForm;
				if (MenuNameReader.containsKeyScreen(lktabform1.getPageId())) {

					String path = MenuNameReader.getScreenProperties(lktabform1
							.getPageId());
					System.out.println("%%%path frm tab pane is" + path
							+ "$$$LK TYPE$$$" + lktabform1.getLkTypes());

					System.out.println("Above shoooot"
							+ lktabform1.getSelectedLock().length());

					// after click on particular locker
					if (lktabform1.getSelectedLock().length() != 0) {

						System.out.println("Hey i can shoot values "
								+ lktabform1.getSelectedLock());

						// This below block used to get length depth height n
						// descrp based on lk types (S,M,L)
						LockerDetailsObject[] lockerDetailsObjects = lkd
								.getLockerTypes();
						LockerDetailsObject[] lockerDetailsObjects99 = lkd
								.getLockers(Integer.parseInt(lktabform1
										.getCabTypes()));

						// StringTokenizer selectedLocker=new
						// StringTokenizer(lktabform1.getSelectedLock());
						// LockerDetailsObject[]locDetailsObjects=lkd.getLockerCabStructure(Integer.parseInt(lktabform1.getCabTypes()));

						String selectedLocker = lktabform1.getSelectedLock();
						System.out.println("RRRR   ---"
								+ selectedLocker
										.charAt(selectedLocker.length() - 1));
						System.out.println("IIII ---"
								+ selectedLocker.substring(0, selectedLocker
										.length() - 1));

						int lockNum = Integer.parseInt(selectedLocker
								.substring(0, selectedLocker.length() - 1));
						String lockType = String.valueOf(selectedLocker
								.charAt(selectedLocker.length() - 1));

						int i;
						int h;
						String index1 = null;
						String index2 = null;

						for (i = 0; i < lockerDetailsObjects99.length; i++)
							if (lockerDetailsObjects99[i].getLockerNo() == lockNum) {
								lktabform1.setHiddenIndex(i);
								index1 = String.valueOf(i);
								break;
							}

						for (h = 0; h < lockerDetailsObjects.length; h++)
							if (lockerDetailsObjects[h].getLockerType()
									.equalsIgnoreCase(lockType)) {
								lktabform1.setHiddenIndex2(h);
								index2 = String.valueOf(h);
								break;
							}
						httpServletRequest.setAttribute("index1", index1);
						httpServletRequest.setAttribute("index2", index2);

						httpServletRequest.setAttribute("panelDetails1",
								lockerDetailsObjects99);
						httpServletRequest.setAttribute("panelDetails2",
								lockerDetailsObjects);

					}

					System.out.println();
					System.out
							.println("***DETAILS***array_lockerparamobject_cabins****");
					// 5-cab types 1 -lk types
					if (lktabform1.getFocusType() == 5) {

						LockerDetailsObject[] locDetailsObjects = null;

						System.out.println("##########"
								+ lktabform1.getLkTypes() + "@@@@@@@@@@@@@@@"
								+ lktabform1.getCabTypes());

						locDetailsObjects = lkd.getLockerCabStructure(Integer.parseInt(lktabform1.getCabTypes()));
						LockerDetailsObject[] locDetailsObjects1 = lkd.getLockers(Integer.parseInt(lktabform1.getCabTypes()));

						// System.out.println("loc det
						// obj&*&*&*&*&*&*&*&"+locDetailsObjects1[22].getLockerAcNo()+"---"+
						// locDetailsObjects1[22].getLockerType()+"----"+locDetailsObjects1[22].getLockerHeight());

						httpServletRequest.setAttribute("TableDetail",
								locDetailsObjects);
						httpServletRequest.setAttribute("lockerSpecific",
								locDetailsObjects1);

						int arr[] = lkd
								.getDistinctCabs(lktabform1.getLkTypes());
						httpServletRequest.setAttribute("cabDetails", arr);

						getLockersTypes(httpServletRequest, path, lkd);
						httpServletRequest.setAttribute("pageId", path);
						return actionMapping.findForward(ResultHelp
								.getSuccess());
					}

					if (lktabform1.getFocusType() == 1) {

						System.out.println("^^^^^^^^^UUUU^^^^^^^^^^^^^^^");

						int arr[] = lkd
								.getDistinctCabs(lktabform1.getLkTypes());
						httpServletRequest.setAttribute("cabDetails", arr);

						getLockersTypes(httpServletRequest, path, lkd);
						httpServletRequest.setAttribute("pageId", path);
						return actionMapping.findForward(ResultHelp
								.getSuccess());
					}
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(
							"Please check ! There is no name for the given key in the properties file",
							httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Locker Data Entry
		else if (actionMapping.getPath().equalsIgnoreCase("/LockerDEMenuLink")) 
		{

			LockerDE ldeForm = (LockerDE) actionForm;
			try {
				if (MenuNameReader.containsKeyScreen(ldeForm.getPageId())) {

					String path = MenuNameReader.getScreenProperties(ldeForm.getPageId());
					System.out.println("*******" + path);
					
					//vobject.clear();
					LockerDetailsObject lockerDetailsObjec1 = lkd.getLockerDetails(ldeForm.getLkTypes());
					httpServletRequest.setAttribute("lkdetails",lockerDetailsObjec1);
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}

				else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().equalsIgnoreCase("/LockerDELink")) 
		{

			LockerDE ldeForm = (LockerDE) actionForm;
			try {
				if (MenuNameReader.containsKeyScreen(ldeForm.getPageId())) {

					String path = MenuNameReader.getScreenProperties(ldeForm.getPageId());
					System.out.println("%%%%%%%" + path);
					System.out.println("before storing date length ===>"+vobject.size());
					
					vobject.add(ldeForm.getDescription());
					vobject.add(ldeForm.getKeyNum());
					vobject.add(ldeForm.getLength());
					vobject.add(ldeForm.getHeight());
					vobject.add(ldeForm.getDepth());
					
					System.out.println("after stroring date the length==>"+vobject.size());
					LockerDetailsObject lockerDetailsObjec1 = lkd.getLockerDetails(ldeForm.getLkTypes());
					httpServletRequest.setAttribute("lkdetails",lockerDetailsObjec1);
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}

				else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOwnerReportMLink")) {
			try {
				LockerOwnerReportForm lockerOwnerReportForm = (LockerOwnerReportForm) actionForm;
				lockerOwnerReportForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerOwnerReportForm
						.getPageId())) {
					System.out.println("FROM LOCKER oener report menu");
					String date = LockerDelegate.getSysDate();
					httpServletRequest.setAttribute("date", date);
					httpServletRequest.setAttribute("pageId", MenuNameReader
							.getScreenProperties(lockerOwnerReportForm
									.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOwnerReportLink")) {
			
			try {
				LockerOwnerReportForm lockerOwnerReportForm = (LockerOwnerReportForm) actionForm;
				LockerMasterObject[] array_lockermasterobject = null;
				lockerOwnerReportForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerOwnerReportForm.getPageId())) {
					
					System.out.println("FROM LOCKER oener report ---");
					String toDate = lockerOwnerReportForm.getFromDate();
					int flag = 1;

					array_lockermasterobject = lkd.getLockerReport(null,toDate, flag);
					if(array_lockermasterobject!=null)
					{
						httpServletRequest.setAttribute("details",array_lockermasterobject);
					}
					else
					{
						lockerOwnerReportForm.setValidateFlag("Records Not Found");
					}
					
					if(lockerOwnerReportForm.getFlag().equalsIgnoreCase("File"))
					{
						   HSSFWorkbook wb = new HSSFWorkbook();
						   HSSFSheet sheet = wb.createSheet("New Sheet");
						   HSSFRow row = sheet.createRow((short)0);
						   
						   for(int i=0;i<array_lockermasterobject.length;i++)
						   {
							   HSSFRow row1 = sheet.createRow((short)(i+1));
							   
							   row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getKeyNo());
							   row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
							   row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getLockerType());
							   row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerNo());
							   row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getOprInstrn());
							   row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getReqdMonths());
							   row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getRequiredDays());
							   row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getRentUpto());
							   row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getMemberType());
							   row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getMemberNo());
							   row1.createCell((short)11).setCellValue(array_lockermasterobject[i].getRentBy());
							  
							  
						   }
						   FileOutputStream out = new FileOutputStream("c:\\OwnerReport.xls");
						   wb.write(out);
						   out.close();
						   lockerOwnerReportForm.setValidateFlag("Successfully Stored In File");
						
					}
					System.out.println("------" + array_lockermasterobject);
					
					String date = LockerDelegate.getSysDate();
					httpServletRequest.setAttribute("date", date);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerOwnerReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} // end

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOperationReportMLink")) {
			try {

				LockerOpearationReportForm lockerOpearationReportForm = (LockerOpearationReportForm) actionForm;
				lockerOpearationReportForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerOpearationReportForm.getPageId())) {
					System.out.println("FROM LOCKER oener report menu");
					String fromDate = LockerDelegate.getSysDate();
					String toDate = LockerDelegate.getSysDate();

					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("pageId", MenuNameReader
							.getScreenProperties(lockerOpearationReportForm
									.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOperationReportLink")) {
			try {

				LockerOpearationReportForm lockerOpearationReportForm = (LockerOpearationReportForm) actionForm;
				lockerOpearationReportForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerOpearationReportForm.getPageId())) 
				{
					int flag = 2;
					String string_qry = null;
					LockerTransObject array_lockertransobject[] = null;

					String fromDate = lockerOpearationReportForm.getFromDate();
					String toDate = lockerOpearationReportForm.getToDate();
					System.out.println("DATE@" + fromDate + "DATE!@@@@"+ toDate);

					array_lockertransobject = lkd.getLockerTransaction(null,null, fromDate, toDate, flag, string_qry);
					
					if(array_lockertransobject!=null)
					{
						httpServletRequest.setAttribute("details",array_lockertransobject);
						lockerOpearationReportForm.setValidateFlag("");
					}
					else{
						lockerOpearationReportForm.setValidateFlag("Records Not Found");
					}
					
					if(lockerOpearationReportForm.getFlag().equalsIgnoreCase("File"))
					{
						if(array_lockertransobject!=null)
						{
							HSSFWorkbook book = new HSSFWorkbook();
							HSSFSheet sheet=book.createSheet("New Sheet");
							HSSFRow row= sheet.createRow((short)0);
							
							for(int i=0;i<array_lockertransobject.length;i++)
							{
								HSSFRow row1 = sheet.createRow((short)(i+1));
								
								row1.createCell((short)1).setCellValue(array_lockertransobject[i].getLk_ac_ty());
								row1.createCell((short)2).setCellValue(array_lockertransobject[i].getLk_ac_no());
								row1.createCell((short)3).setCellValue(array_lockertransobject[i].getLocker_ty());
								row1.createCell((short)4).setCellValue(array_lockertransobject[i].getLocker_no());
								row1.createCell((short)5).setCellValue(array_lockertransobject[i].getOpDate());
								row1.createCell((short)6).setCellValue(array_lockertransobject[i].getOperatedBy());
								row1.createCell((short)7).setCellValue(array_lockertransobject[i].getTimein());
								row1.createCell((short)8).setCellValue(array_lockertransobject[i].getTimeout());
								row1.createCell((short)9).setCellValue(array_lockertransobject[i].getName());
								
							}
							
							FileOutputStream out = new FileOutputStream("c:\\Operation.xls");
							book.write(out);
							out.close();
							lockerOpearationReportForm.setValidateFlag("Successfully Stored In File");
						}
						
						
					}
					
					
					
					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("toDate", toDate);

					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerOpearationReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				}
				else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOpenClosedReportMLink")) {
			
			try {

				LockerOpenClosedReportForm lockerOpenClosedReportForm = (LockerOpenClosedReportForm) actionForm;
				
				lockerOpenClosedReportForm.setSysdate(LockerDelegate.getSysDate());
				lockerOpenClosedReportForm.setFlag(null);
				if (MenuNameReader.containsKeyScreen(lockerOpenClosedReportForm.getPageId())) {

					String fromDate = LockerDelegate.getSysDate();
					String toDate = LockerDelegate.getSysDate();
					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("toDate", toDate);

					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerOpenClosedReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerOpenClosedReportLink")) {
			
			try {

				LockerOpenClosedReportForm lockerOpenClosedReportForm = (LockerOpenClosedReportForm) actionForm;
				lockerOpenClosedReportForm.setFlag(null);
				lockerOpenClosedReportForm.setSysdate(LockerDelegate.getSysDate());
				if(MenuNameReader.containsKeyScreen(lockerOpenClosedReportForm.getPageId())) {

					LockerMasterObject array_lockermasterobject[] = null;
					String string_qry = null;
					int flag = 0;
					String fromDate = lockerOpenClosedReportForm.getFromDate();
					String toDate = lockerOpenClosedReportForm.getToDate();
					System.out.println("DATE@" + fromDate + "DATE!@@@@"+ toDate);
					System.out.println("selected option is---"+ lockerOpenClosedReportForm.getSelectOption());
					
					if(lockerOpenClosedReportForm.getSelectOption().equalsIgnoreCase("Issued"))
						flag = 2;// issued
					else
						flag = 3;// surrended

					array_lockermasterobject = lkd.getLockerReport(fromDate,toDate, flag, string_qry);
					
					if(array_lockermasterobject!=null)
					{
						httpServletRequest.setAttribute("details",array_lockermasterobject);
						
						if(lockerOpenClosedReportForm.getForward().equalsIgnoreCase("File"))
						{
							HSSFWorkbook book =new HSSFWorkbook();
							HSSFSheet sheet = book.createSheet("OpenClose");
							HSSFRow row = sheet.createRow((short)0);
							
							for(int i=0;i<array_lockermasterobject.length;i++)
							{
								HSSFRow row1=sheet.createRow((short)(i+1));
								
								row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getLockerAcType());
								row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
								row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getName());
								row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerType());
								row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getLockerNo());
								row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getOprInstrn());
								row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getReqdMonths());
								row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getRequiredDays());
								row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getMemberType());
								row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getMemberNo());
									
							}
							FileOutputStream out = new FileOutputStream("c:\\OpenCloseReport.xls");
							book.write(out);
							out.close();
						}
						else if(lockerOpenClosedReportForm.getForward().equalsIgnoreCase("View"))
						{
							httpServletRequest.setAttribute("details",array_lockermasterobject);
						}
					}
					else
					{
						lockerOpenClosedReportForm.setFlag("Records Not Found");
					}
					httpServletRequest.setAttribute("fromDate",lockerOpenClosedReportForm.getFrmDate());
					httpServletRequest.setAttribute("toDate",lockerOpenClosedReportForm.getToDate());
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerOpenClosedReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} 
				else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerRentDueReportMLink")) {
			try {

				LockerRentDueReport lockerRentDueReport = (LockerRentDueReport) actionForm;
				lockerRentDueReport.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerRentDueReport.getPageId())) {

					String toDate = LockerDelegate.getSysDate();
					System.out.println("DATE@" + toDate + "DATE!@@@@" + toDate);
					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerRentDueReport.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerRentDueReportLink")) {
			try {

				LockerRentDueReport lockerRentDueReport = (LockerRentDueReport) actionForm;
				LockerMasterObject array_lockermasterobject[] = null;
				lockerRentDueReport.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerRentDueReport.getPageId())) {
					int flag = 1;
					String string_qry = null;
					try 
					{
						array_lockermasterobject = lkd.getRentDueReport(lockerRentDueReport.getFromDate(), flag,string_qry);
						if(array_lockermasterobject==null)
						{
							lockerRentDueReport.setValidateFlag("Records Not Found");
						}
						else{
							httpServletRequest.setAttribute("details",array_lockermasterobject);
							lockerRentDueReport.setValidateFlag("");
						}
						if(lockerRentDueReport.getFlag().equalsIgnoreCase("File"))
						{
							HSSFWorkbook book = new HSSFWorkbook();
							HSSFSheet sheet = book.createSheet();
							HSSFRow row = sheet.createRow((short)0);
							
							for(int i=0;i<array_lockermasterobject.length;i++)
							{
								HSSFRow row1 = sheet.createRow((short)(i+1));
								
								row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getLockerAcType());
								row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
								row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getName());
								row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerType());
								row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getLockerNo());
								row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getMemberType());
								row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getMemberNo());
								row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getRentBy());
								row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getRentUpto());
								row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getRentColl());
								
							}
								
							FileOutputStream out = new FileOutputStream("c:\\RentDueReport.xls");
							book.write(out);
							out.close();
									
							
						}
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					String todate = lockerRentDueReport.getFromDate();
					System.out.println("DATE@" + todate + "DATE!@@@@" + todate+ "" + array_lockermasterobject);

					httpServletRequest.setAttribute("details",array_lockermasterobject);
					httpServletRequest.setAttribute("todate", todate);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerRentDueReport.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerRentCollectedReportMLink")) {
		
			try {
				System.out.println("Locker rent collected");

				LockerRentCollected lockerRentCollected = (LockerRentCollected) actionForm;
				lockerRentCollected.setSysdate(LockerDelegate.getSysDate());
				System.out.println("Locker rent collected123");
				if (MenuNameReader.containsKeyScreen(lockerRentCollected.getPageId())) {

					String toDate = LockerDelegate.getSysDate();
					String fromDate = LockerDelegate.getSysDate();

					System.out.println("TO DATE@" + toDate + "FRM DATE!@@@@"+ fromDate);

					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerRentCollected.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerRentCollectedReportLink")) {
			
			try {

				LockerRentCollected lockerRentCollected = (LockerRentCollected) actionForm;
				lockerRentCollected.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerRentCollected.getPageId())) {

					int flag = 13;
					String string_qry = null;
					LockerMasterObject array_lockermasterobject[] = null;

					String fromDate = lockerRentCollected.getFromDate();
					String todate = lockerRentCollected.getToDate();
					System.out.println("DATE@" + todate + "DATE!@@@@" + todate);
					if(fromDate.length()!=0 && todate.length()!=0)
					{
						try 
						{
							array_lockermasterobject = lkd.getLockerReport(lockerRentCollected.getFromDate(),lockerRentCollected.getToDate(), flag,string_qry);
							if(array_lockermasterobject!=null)
							{
								httpServletRequest.setAttribute("details", array_lockermasterobject);
								lockerRentCollected.setValidateFlag("");
							}
							else{
								lockerRentCollected.setValidateFlag("Records Not Found");
							}
							
							if(lockerRentCollected.getFlag().equalsIgnoreCase("File"))
							{
								HSSFWorkbook book = new HSSFWorkbook();
								HSSFSheet sheet = book.createSheet();
								HSSFRow row = sheet.createRow((short)0);
								
								for(int i=0;i<array_lockermasterobject.length;i++)
								{
									HSSFRow row1 = sheet.createRow((short)(i+1));
									
									row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getLockerAcType());
									row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
									row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getName());
									row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerType());
									row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getLockerNo());
									row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getMemberType());
									row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getMemberNo());
									row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getRentBy());
									row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getRentUpto());
									row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getRentColl());
								
								}
								FileOutputStream out = new FileOutputStream("c:\\RentColl.xls");
								book.write(out);
								out.close();
							}
						}
						catch (Exception e) 
						{
							e.printStackTrace();
							lockerRentCollected.setValidateFlag("Records Not Found");
						}
						httpServletRequest.setAttribute("details",array_lockermasterobject);
						httpServletRequest.setAttribute("fromDate", fromDate);
						httpServletRequest.setAttribute("todate", todate);
					}
					else
					{
						lockerRentCollected.setValidateFlag("Enter Valid From And To Date");
					}
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerRentCollected.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/NotSurrenderedReportMLink")) {
			
			try {

				NotSurrendered notSurrenderedReportForm = (NotSurrendered) actionForm;
				notSurrenderedReportForm.setValidateFlag(null);
				notSurrenderedReportForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(notSurrenderedReportForm.getPageId())) {

					httpServletRequest.setAttribute("toDate", LockerDelegate.getSysDate());

					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(notSurrenderedReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/NotSurrenderedReportLink")) {
			
			try {

				LockerMasterObject[] array_lockermasterobject = null;
				NotSurrendered notSurrenderedReportForm = (NotSurrendered) actionForm;
				notSurrenderedReportForm.setSysdate(LockerDelegate.getSysDate());
				notSurrenderedReportForm.setValidateFlag(null);
				if(MenuNameReader.containsKeyScreen(notSurrenderedReportForm.getPageId())) 
				{
					int flag = 3;
					String toDate = notSurrenderedReportForm.getToDate();
					
					if(notSurrenderedReportForm.getForward().equalsIgnoreCase("view"))
					{
						array_lockermasterobject = lkd.getRentDueReport(toDate,flag,null);
						
						if(array_lockermasterobject!=null)
						{
							System.out.println("-------" + array_lockermasterobject);
							
							ArrayList notSurrender= new ArrayList();
		    				Map map=null;
							
		    				for(int i=0;i<array_lockermasterobject.length;i++)
		    				{
		    					if(array_lockermasterobject[i].getLockerAcType()!=null){
		    					map = new TreeMap();
		    					map.put("lockerType",array_lockermasterobject[i].getLockerType());
		    					map.put("lockerAcType",array_lockermasterobject[i].getLockerAcType());
		    					map.put("lockerAcNo",array_lockermasterobject[i].getLockerAcNo());
		    					map.put("name",array_lockermasterobject[i].getName());
		    					map.put("closeDate",array_lockermasterobject[i].getCloseDate());
		    					map.put("addressType",array_lockermasterobject[i].getAddressType());
		    					map.put("memberType",array_lockermasterobject[i].getMemberType());
		    					map.put("memberNo",array_lockermasterobject[i].getMemberNo());
		    					map.put("rentColl",array_lockermasterobject[i].getRentColl());
		    					map.put("rentUpto",array_lockermasterobject[i].getRentUpto());
		    					
		    					notSurrender.add(map);
		    					}
		    				}
		    				
		    				if(notSurrender.size()!=0)
		    				{
		    					httpServletRequest.setAttribute("details",notSurrender);
		    					notSurrenderedReportForm.setForward(null);
		    				}
		    				else
		    				{
		    					notSurrenderedReportForm.setValidateFlag("Records Not Found");
		    				}
							if(notSurrenderedReportForm.getFlag().equalsIgnoreCase("File"))
							{
								HSSFWorkbook book =new HSSFWorkbook();
								HSSFSheet sheet = book.createSheet();
								HSSFRow row = sheet.createRow((short)0);
								
								for(int i=0;i<array_lockermasterobject.length;i++)
								{
									HSSFRow row1 = sheet.createRow((short)(i+1));
									
									row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getLockerAcType());
									row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
									row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getName());
									row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerType());
									row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getLockerNo());
									row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getMemberType());
									row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getMemberNo());
									row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getCloseDate());
									row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getRentUpto());
									row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getRentColl());
									row1.createCell((short)11).setCellValue(array_lockermasterobject[i].getAddressType());
								
									
								}
								FileOutputStream out = new FileOutputStream("c:\\NotSurrender.xls");
								book.write(out);
								out.close();
								
							}
						}
						else
						{
							notSurrenderedReportForm.setValidateFlag("Records Not Found");
						}
					}
					
					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(notSurrenderedReportForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKPassBookMLink")) {
			
			try {

				LockerPassBookForm lockerPassBookForm = (LockerPassBookForm) actionForm;
				lockerPassBookForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerPassBookForm.getPageId())) {
					String toDate = LockerDelegate.getSysDate();
					String fromDate = LockerDelegate.getSysDate();
					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(lockerPassBookForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} 
				else
					return actionMapping.findForward(ResultHelp.getError());

			} 
			catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKPassBookLink")) 
		{
			try 
			{

				LockerPassBookForm lockerPassBookForm = (LockerPassBookForm) actionForm;
				lockerPassBookForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerPassBookForm.getPageId())) 
				{

					LockerMasterObject lockermasterobject;
                    
					String fromDate = lockerPassBookForm.getFromDate();
					String toDate = lockerPassBookForm.getToDate();

					if (lockerPassBookForm.getForward().equalsIgnoreCase("view")) 
					{
						System.out.println("checking for viewwww");
						LockerTransObject array_lockertransobject[] = lkd.getLockerTransaction(lockerPassBookForm.getAcntNum(), "1009001",lockerPassBookForm.getFromDate(),lockerPassBookForm.getToDate(), 3, null);

						if (array_lockertransobject != null) 
						{
							
							System.out.println(":::::---lockerPassBookForm!=null--::::");
							httpServletRequest.setAttribute("tabDetails",array_lockertransobject);
							
							if(lockerPassBookForm.getFlag().equalsIgnoreCase("File"))
							{
								HSSFWorkbook book = new HSSFWorkbook();
								HSSFSheet sheet=book.createSheet("New Sheet");
								HSSFRow row=sheet.createRow((short)0);
								
								for(int i=0;i<array_lockertransobject.length;i++)
								{
									HSSFRow row1=sheet.createRow((short)(i+1));
									row1.createCell((short)1).setCellValue(array_lockertransobject[i].getOpDate());
									row1.createCell((short)2).setCellValue(array_lockertransobject[i].getOperatedBy());
									row1.createCell((short)3).setCellValue(array_lockertransobject[i].getTimeIn());
									row1.createCell((short)4).setCellValue(array_lockertransobject[i].getTimeOut());
									row1.createCell((short)5).setCellValue(array_lockertransobject[i].getRentBy());
									row1.createCell((short)6).setCellValue(array_lockertransobject[i].getTransAcType());
									row1.createCell((short)7).setCellValue(array_lockertransobject[i].getTransAcNo());
									row1.createCell((short)8).setCellValue(array_lockertransobject[i].getRentUpto());
									row1.createCell((short)9).setCellValue(array_lockertransobject[i].getRentColl());
								}
								FileOutputStream out= new FileOutputStream("c:\\PassBook.xls");
								book.write(out);
								out.close();
								lockerPassBookForm.setVerifyInd("Successfully Stored In File");
							}
						} 
						else 
						{
							
							lockerPassBookForm.setVerifyInd("Records not found");
							lockerPassBookForm.setAcntNum("");
							lockerPassBookForm.setCustId("");
							lockerPassBookForm.setCustName("");
							lockerPassBookForm.setLockerNum("");
							lockerPassBookForm.setLockerType("");
						}
					}

					else if(lockerPassBookForm.getForward().equalsIgnoreCase("actnum"))  
					{
						
						
						if (lockerPassBookForm.getAcntNum().length()!= 0)
						{
							System.out.println("inside elseeee");
							lockermasterobject = lkd.getLockerMaster(Integer.parseInt(lockerPassBookForm.getAcntNum()), 7);

							if (lockermasterobject != null) 
							{
								System.out.println(lockermasterobject.getOperDate());
							
								httpServletRequest.setAttribute("details",lockermasterobject);
								lockerPassBookForm.setVerifyInd("");
							} 
							else 
							{
								System.out.println("####---->");
								lockerPassBookForm.setVerifyInd("Account Doesn’t exist");
								lockerPassBookForm.setAcntNum("");
							}
						}
						else
						{
							lockerPassBookForm.setVerifyInd("Enter Valid Locker A/c Num");
							lockerPassBookForm.setAcntNum("");
						}
					}

					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(lockerPassBookForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
					
				} 
				else
					return actionMapping.findForward(ResultHelp.getError());

			}

			catch (LockerMemberNotFoundException lockerMemberNotFoundException) {
				lockerMemberNotFoundException.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKRemainderNoticeMLink")) {
		
			try {

				LockerRemainderNoticeForm lockerRemainderNoticeForm = (LockerRemainderNoticeForm) actionForm;
				lockerRemainderNoticeForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerRemainderNoticeForm.getPageId())) {

					String toDate = LockerDelegate.getSysDate();
					String fromDate = LockerDelegate.getSysDate();

					httpServletRequest.setAttribute("toDate", toDate);
					httpServletRequest.setAttribute("fromDate", fromDate);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerRemainderNoticeForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKRemainderNoticeLink")) 
		{
			try {
				LockerRemainderNoticeForm lockerRemainderNoticeForm = (LockerRemainderNoticeForm) actionForm;
				lockerRemainderNoticeForm.setSysdate(LockerDelegate.getSysDate());
				if (MenuNameReader.containsKeyScreen(lockerRemainderNoticeForm.getPageId())) {
					String toDate = lockerRemainderNoticeForm.getToDate();
					String fromDate = lockerRemainderNoticeForm.getFromDate();
					// array_lockermasterobject=lockersremote.getLockerReport(txt_from_date.getText().toString(),txt_to_date.getText().toString(),6,null);
					if(fromDate.length()!=0 && toDate.length()!=0)
					{
						LockerMasterObject array_lockermasterobject[] = lkd.getLockerReport(fromDate, toDate, 6, null);
						if (array_lockermasterobject != null) 
						{
							lockerRemainderNoticeForm.setValidateFlag("");
							System.out.println(""+ array_lockermasterobject[0].getAddressType());
							httpServletRequest.setAttribute("tabDetails",array_lockermasterobject);
							httpServletRequest.setAttribute("toDate", toDate);
							httpServletRequest.setAttribute("fromDate", fromDate);
							
							if(lockerRemainderNoticeForm.getFlag().equalsIgnoreCase("File"))
							{
								HSSFWorkbook book =new HSSFWorkbook();
								HSSFSheet sheet = book.createSheet();
								HSSFRow row = sheet.createRow((short)0);
								
								for(int i=0;i<array_lockermasterobject.length;i++)
								{
									HSSFRow row1 = sheet.createRow((short)(i+1));
									
									row1.createCell((short)1).setCellValue(array_lockermasterobject[i].getLockerAcType());
									row1.createCell((short)2).setCellValue(array_lockermasterobject[i].getLockerAcNo());
									row1.createCell((short)3).setCellValue(array_lockermasterobject[i].getName());
									row1.createCell((short)4).setCellValue(array_lockermasterobject[i].getLockerType());
									row1.createCell((short)5).setCellValue(array_lockermasterobject[i].getLockerNo());
									row1.createCell((short)6).setCellValue(array_lockermasterobject[i].getMemberType());
									row1.createCell((short)7).setCellValue(array_lockermasterobject[i].getMemberNo());
									row1.createCell((short)8).setCellValue(array_lockermasterobject[i].getCloseDate());
									row1.createCell((short)9).setCellValue(array_lockermasterobject[i].getRentUpto());
									row1.createCell((short)10).setCellValue(array_lockermasterobject[i].getRentColl());
									row1.createCell((short)11).setCellValue(array_lockermasterobject[i].getAddressType());
								
									
								}
								FileOutputStream out = new FileOutputStream("c:\\ReminderNotice.xls");
								book.write(out);
								out.close();
								
								
							}
							
						} 
						else 
						{
							lockerRemainderNoticeForm.setValidateFlag("Records not found");
							httpServletRequest.setAttribute("toDate", toDate);
							httpServletRequest.setAttribute("fromDate", fromDate);
							
						}
					}
					else
					{
						lockerRemainderNoticeForm.setValidateFlag("Enter Valid From And To Date");
					}
					httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(lockerRemainderNoticeForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} 
				else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (actionMapping.getPath().trim().equalsIgnoreCase("/RateAdminMLink")) {
			try {

				RateAdmin rateAdmin = (RateAdmin) actionForm;
				// System.out.println("-------------->"+MenuNameReader.containsKeyScreen(rateAdmin.getPageId()+"<------>"+MenuNameReader.getScreenProperties(rateAdmin.getPageId())));
				System.out.println("------------->"
						+ MenuNameReader.containsKeyScreen(rateAdmin
								.getPageId())
						+ "<------>"
						+ MenuNameReader.getScreenProperties(rateAdmin
								.getPageId()));

				// LockerOpenClosedReportForm
				// lockerOpenClosedReportForm=(LockerOpenClosedReportForm)actionForm;

				if (MenuNameReader.containsKeyScreen(rateAdmin.getPageId())) {

					String path = MenuNameReader.getScreenProperties(rateAdmin
							.getPageId());

					httpServletRequest.setAttribute("tableDetails", lkd
							.getLockerRateParameter());
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/RateAdminLink")) {
			
			
			try {

				RateAdmin rateAdmin = (RateAdmin) actionForm;
				LockerDetailsObject[] lockerDetailsObject = new LockerDetailsObject[1];

				if (MenuNameReader.containsKeyScreen(rateAdmin.getPageId())) {
					
					System.out.println("------->" + rateAdmin.getForward());

					if (rateAdmin.getForward().equalsIgnoreCase("insert")) {
						
						rateAdmin.setValidateFlag("");
						httpServletRequest.setAttribute("insert", "inertRow");

					}

					if (rateAdmin.getForward().equalsIgnoreCase("submit")) {

						lockerDetailsObject[0] = new LockerDetailsObject();

						System.out.println("chking values-->"+ rateAdmin.getLockerType1() + "-->"+ rateAdmin.getDateFrom1() + "-->"+ rateAdmin.getDateTo1());

						System.out.println(lockerDetailsObject[0] + "*-*"+ rateAdmin);

						lockerDetailsObject[0].setLockerType(rateAdmin.getLockerType1());
						lockerDetailsObject[0].setDateFrom(rateAdmin.getDateFrom1());
						lockerDetailsObject[0].setDateTo(rateAdmin.getDateTo1());
						lockerDetailsObject[0].setDaysFrom(Integer.parseInt(rateAdmin.getDaysFrom1()));
						lockerDetailsObject[0].setDaysTo(Integer.parseInt(rateAdmin.getDaysTo1()));
						lockerDetailsObject[0].setLockerRate(Double.parseDouble(rateAdmin.getLockerRate1()));
						lockerDetailsObject[0].setSecurityDeposit(Double.parseDouble(rateAdmin.getSecurityDeposit1()));

						lockerDetailsObject[0].uv.setUserId(uid);
						lockerDetailsObject[0].uv.setUserTml(uid);
						lockerDetailsObject[0].uv.setUserDate(LockerDelegate.getSysDate());

						lockerDetailsObject[0].setTrnDate(LockerDelegate.getSysDate());

						// lockersremote.addLockerRateParameter(array_lockerDetailsObject,0)
						lkd.addLockerRateParameter(lockerDetailsObject, 0);

						rateAdmin.setValidateFlag("inserted successfully");
					}
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("tableDetails", lkd.getLockerRateParameter());
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(rateAdmin.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		/*
		 * else
		 * if(actionMapping.getPath().trim().equalsIgnoreCase("/MyHelpLink")){
		 * try{
		 * 
		 * 
		 * MyHelpForm myhelpform=(MyHelpForm)actionForm;
		 * 
		 * 
		 * if(MenuNameReader.containsKeyScreen(myhelpform.getPageId())){
		 * 
		 * Object obj[][]=lkd.getHelpWindowDetails();
		 * httpServletRequest.setAttribute("pageId",MenuNameReader.getScreenProperties(myhelpform.getPageId()));
		 * return actionMapping.findForward(ResultHelp.getSuccess()); } else
		 * return actionMapping.findForward(ResultHelp.getError());
		 * 
		 * 
		 * }catch(Exception e){ e.printStackTrace(); } }
		 */
		if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerSurrenderVerificationMLink")) {
			
			LockerSurrenderForm lkformobj = (LockerSurrenderForm) actionForm;

			try 
			{
				
				if (MenuNameReader.containsKeyScreen(lkformobj.getPageId())) 
				{
					String path = MenuNameReader.getScreenProperties(lkformobj.getPageId());
					System.out.println("wen i gone thro Reader" + path);

					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);

					httpServletRequest.setAttribute("pageId", path);

					return actionMapping.findForward(ResultHelp.getSuccess());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerSurrenderVerificationLink")) 
		{
			
			try 
			{
				LockerSurrenderForm lkSurrenderformobj = (LockerSurrenderForm) actionForm;
				AccountMasterObject amobj;
				lkSurrenderformobj.setVal("xxx");
				LockerMasterObject lockermasterobject;

				System.out.println("********************surrender menu$$$$$$$$$$$");
				if (MenuNameReader.containsKeyScreen(lkSurrenderformobj.getPageId())) 
				{
					
					String path = MenuNameReader.getScreenProperties(lkSurrenderformobj.getPageId());

					System.out.println("My button name is "+ lkSurrenderformobj.getForward() + "page name is"+ path);
					System.out.println("##############**surrender menu##########");

					if (lkSurrenderformobj.getForward().equalsIgnoreCase("verify")) 
					{
						if (lkSurrenderformobj.getTxt_lockNo().length() != 0) 
						{

							LockerTransObject lockertransobject = new LockerTransObject();

							lockertransobject.setLockerAcType("1009001");
							lockertransobject.setLockerAcNo(Integer.parseInt(lkSurrenderformobj.getTxt_acNo()));
							String s = lkSurrenderformobj.getTxt_lockNo();

							int n = Integer.parseInt(s);
							System.out.println("locker num" + n);
							lockertransobject.setLockerNo(n);
							lockertransobject.setLockerType(lkSurrenderformobj.getTxt_lockType());
							lockertransobject.setRentUpto(lkSurrenderformobj.getTxt_rentUpto());
							lockertransobject.setTrnDate(LockerDelegate.getSysDate());

							lockertransobject.uv.setVerId(uid);
							lockertransobject.uv.setVerTml(tml);
							lockertransobject.uv.setVerDate(LockerDelegate.getSysDate());

							int flag = lkd.surrender(lkSurrenderformobj.getTxt_acType(), lkSurrenderformobj.getTxt_rentUpto(), lockertransobject, 5);

							if (flag == 1) 
							{
								lkSurrenderformobj.setValidateFlag("Locker Verified successfully");
								System.out.println("OOOOO 'm succseeeee");
								getLkAbbr(httpServletRequest, path, lkd);
								httpServletRequest.setAttribute("pageId", path);
								return actionMapping.findForward(ResultHelp.getSuccess());
							}
							if (flag == 2) 
							{
								lkSurrenderformobj.setValidateFlag("Locker not Verified");
								System.out.println("Ahhhhh failure again");
								getLkAbbr(httpServletRequest, path, lkd);
								httpServletRequest.setAttribute("pageId", path);
								return actionMapping.findForward(ResultHelp.getError());
							}
						}
						else
						{
							lkSurrenderformobj.setValidateFlag("Enter Valid Locker A/c Num");
						}
						

					}

					else if (lkSurrenderformobj.getForward().equalsIgnoreCase("fromAcntNum")) 
					{

						String acnum1 = lkSurrenderformobj.getTxt_acNo();
						int acnum = Integer.parseInt(acnum1);

						lockermasterobject = lkd.getLockerMaster(acnum, 4);
						System.out.println("----lockermasterobject----"+ lockermasterobject);
						System.out.println("##########################");
						if(lockermasterobject != null) 
						{
							lkSurrenderformobj.setValidateFlag("");

							if (lockermasterobject.uv.getVerId() != null) 
							{
								lkSurrenderformobj.setValidateFlag("");
								System.out.println("<-#### --getVerId---10-->");

								if (lockermasterobject.getCloseDate() == null) 
								{
									lkSurrenderformobj.setValidateFlag("");
									System.out.println("<-@@@@@--getCloseDate----->");
									
									if(lockermasterobject.getFreezeInd()!=null)
									{
									if (lockermasterobject.getFreezeInd().equalsIgnoreCase("F")) 
									{
										lkSurrenderformobj.setValidateFlag("");
										System.out.println("/---getFreezeInd-----/");

										/*
										 * if(lockermasterobject.getTrnType().equals("C")){
										 * lkSurrenderformobj.setDisableBut("T"); }
										 */

										System.out.println("My button SUUUUURRRE name is "+ lkSurrenderformobj.getSubmit()+ "Locker Number iss^^"+ lockermasterobject.getLockerNo()+ "key$$"+ lockermasterobject.getKeyNo());
										System.out.println("alloted date"+ lockermasterobject.getAllotDate()+ "***"+ lockermasterobject.getLockerAcType()+ "**"+ lockermasterobject.getLockerAcNo());
										System.out.println(lockermasterobject.getRentColl()+ "**"+ lockermasterobject.getRentUpto()+ "***"+ lockermasterobject.getIntrAcNo()+ "***"+ lockermasterobject.getIntrAcTy());

										String pageid = lkSurrenderformobj.getPageId();

										System.out.println("Page id is"+ pageid + "path is" + path);
										System.out.println("I Selected frm Select Box"+ lkSurrenderformobj.getSelect_details());
										System.out.println("%%%%%&&&&&&&$$$$$$");
										System.out.println("^^^^^values are^^^^^");
										System.out.println("Account type"+ lkSurrenderformobj.getTxt_acType()+ "LkAcNum"+ lkSurrenderformobj.getTxt_acNo()+ "LkType"+ lkSurrenderformobj.getTxt_lockType()+ "Rent Uptooo"+ lkSurrenderformobj.getTxt_rentUpto()+ "GetSysDate,,"+ LockerDelegate.getSysDate());
										System.out.println("ZZZZZZZZ"+ lkSurrenderformobj.getForward());

										int IntacNum = lockermasterobject.getIntrAcNo();
										String IntacType = lockermasterobject.getIntrAcTy();
										System.out.println("intttnumm"+ IntacNum + "accctype"+ IntacType);
										ModuleObject modobj[] = lkd.getMainModules("1009000");
										String details = lkSurrenderformobj.getSelect_details();
										lockermasterobject = lkd.getLockerMaster(acnum, 4);
										AccountObject object=lkd.getAccount(IntacType,IntacNum,lkd.getSysDate());										//httpServletRequest.setAttribute("TabNum", "0");
										httpServletRequest.setAttribute("lkparams", lockermasterobject);
										// SANGEETA
										/*if (lkSurrenderformobj.getTabPaneHeading() != null)
											  setDetails(lkSurrenderformobj.getTabPaneHeading(),httpServletRequest, lkd,lockermasterobject, 0, 0,null);
										else
											setDetails(lkSurrenderformobj.getDefaultTab(),httpServletRequest, lkd,lockermasterobject, 0, 0,null);
										 */
										if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("personal"))
										{
											httpServletRequest.setAttribute("personalInfo",lkd.getCustomer(lockermasterobject.getCid()));
											httpServletRequest.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
											httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
											httpServletRequest.setAttribute("param", modobj);
											httpServletRequest.setAttribute("pageId", path);
										}
										else if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("IntroducerType"))
										{
											int cid= object.getCid();
											httpServletRequest.setAttribute("personalInfo",lkd.getCustomer(cid));
											httpServletRequest.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
											httpServletRequest.setAttribute("panelName", CommonPanelHeading.getIntroucer());
											httpServletRequest.setAttribute("param", modobj);
											httpServletRequest.setAttribute("pageId", path);
										}
										else if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("Deposit"))
										{
											if(lockermasterobject.getDeposits()!=null )
											{	
												Vector<String> vector_deposits=lockermasterobject.getDeposits();
												
												if(vector_deposits.size()!=0)
												{
													String accnum,actype=null;
													int int_acno=0;
													System.out.println("lockermasterobject.getDeposits size = "+vector_deposits.size());
													Enumeration enumeration=vector_deposits.elements();
													while(enumeration.hasMoreElements())
													{
														StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
														actype=tokenizer.nextElement().toString();
														int_acno=Integer.parseInt(tokenizer.nextToken());
														accnum=String.valueOf(int_acno);
													}
													try{
													DepositMasterObject object1=lkd.getDepositMaster(actype,int_acno);
													if(object1!=null)
													{
					    	   	 				 		System.out.println("deposit master object==>"+object);	
					    	   	 				 		int dcid=object1.getCustomerId();
					    	   	 				 		System.out.println("customer id--->"+dcid);
					    	   	 				 		cmobject=lkd.getCustomer(dcid);
					    	   	 				 		String perPath=MenuNameReader.getScreenProperties("9035");
					    	   	 				 		httpServletRequest.setAttribute("DepNumInfo", int_acno);
					    	   	 				 		httpServletRequest.setAttribute("DepTypInfo",actype);
					    	   	 				 		httpServletRequest.setAttribute("personalInfo",cmobject);
					    	   	 				 		httpServletRequest.setAttribute("depositInfo",object);
					    	   	 				 		httpServletRequest.setAttribute("flag",perPath);
					    	   	 				 		httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
													}
													else
													{
														lkSurrenderformobj.setValidateFlag("Deposit A/c Not Found");
													}
													}
													catch (Exception e) {
														lkSurrenderformobj.setValidateFlag("Deposit A/c Not Found");
														
													}
												}
												else
												{
													lkSurrenderformobj.setValidateFlag("Deposit Details Not Found");
												}
											}	
										}
										else if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("JointHolder"))
										{
											if(lockermasterobject.getJointCid()!=null)
											{
												int[] cid=lockermasterobject.getJointCid();
												httpServletRequest.setAttribute("personalInfo",lkd.getCustomer(cid[0]));
												httpServletRequest.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
												httpServletRequest.setAttribute("panelName", CommonPanelHeading.getJointHolder());
												httpServletRequest.setAttribute("param", modobj);
												httpServletRequest.setAttribute("pageId", path);
													
											}	
											else	
											{
												lkSurrenderformobj.setValidateFlag("JointHolderDetailNotFound");
											}
										}
										else if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("Nominee"))
										{
											if(lockermasterobject.getNomineeDetails()!=null)
											{
												NomineeObject[] nomineeObjects=lockermasterobject.getNomineeDetails();
												httpServletRequest.setAttribute("nomineeDetails",nomineeObjects);
												httpServletRequest.setAttribute("flag",MenuNameReader.getScreenProperties("0002"));
												httpServletRequest.setAttribute("panelName", CommonPanelHeading.getNominee());
												httpServletRequest.setAttribute("param", modobj);
												httpServletRequest.setAttribute("pageId", path);
										
											}
											else
											{
												lkSurrenderformobj.setValidateFlag("NomineeDetailNotFound");
											}
										}
										else if(lkSurrenderformobj.getSelect_details().equalsIgnoreCase("signature"))
										{
											if(lockermasterobject.getSigObj()!=null)
											{
												SignatureInstructionObject[] cid=lockermasterobject.getSigObj();
												httpServletRequest.setAttribute("signInfo",lkd.getCustomer(cid[0].getCid()));
												httpServletRequest.setAttribute("flag",MenuNameReader.getScreenProperties("0007"));
												httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
												httpServletRequest.setAttribute("param", modobj);
												httpServletRequest.setAttribute("pageId", path);
										
											}
											else
											{
												lkSurrenderformobj.setValidateFlag("SignatureNotFound");
											}
										}
										

									}
									else 
									{
										lkSurrenderformobj.setValidateFlag("Locker A/c Freezed");
										lkSurrenderformobj.setTxt_acNo(null);
									}
								 }	
								} 
								else
								{
									lkSurrenderformobj.setValidateFlag("Locker A/c closed");
									lkSurrenderformobj.setTxt_acNo(null);
								}	
							}
							else
							{
								lkSurrenderformobj.setValidateFlag("A/c Not Verified");
								lkSurrenderformobj.setTxt_acNo(null);
							}	
						} 

						else 
						{
							lkSurrenderformobj.setValidateFlag("Records Not Found");
							lkSurrenderformobj.setTxt_acNo(null);
						}

						getLkAbbr(httpServletRequest, path, lkd);
						

					}
					
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
					
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerExtensionVerificationMLink")) {

			System.out.println("1");
			LockerExtensionForm lkExtensionForm = (LockerExtensionForm) actionForm;

			if (MenuNameReader.containsKeyScreen(lkExtensionForm.getPageId())) 
			{

				String path = MenuNameReader.getScreenProperties(lkExtensionForm.getPageId());
				System.out.println("pageId is" + path);
				ModuleObject[] transfer=lkd.getMainModules("1002000,1007000");
				getLkAbbr(httpServletRequest, path, lkd);
				httpServletRequest.setAttribute("transferTypes", transfer);
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward(ResultHelp.getSuccess());
			}
			else 
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());

			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerExtensionVerificationLink")) 
		{
			
			String path;
			LockerExtensionForm LKExtnObj = (LockerExtensionForm) actionForm;
			LockerMasterObject lockermasterobject=null;

			if (MenuNameReader.containsKeyScreen(LKExtnObj.getPageId())) 
			{

				path = MenuNameReader.getScreenProperties(LKExtnObj.getPageId());
				String acnum1 = LKExtnObj.getTxt_acNo();
				int acnum = Integer.parseInt(acnum1);
				ModuleObject[] transfer=lkd.getMainModules("1002000,1007000");
				ModuleObject moduleObject[] = lkd.getMainModules("1002000,1007000");
				httpServletRequest.setAttribute("moduleObject", moduleObject);
				LKExtnObj.setValidateFlag("");
				System.out.println("<--->m inside lkExLink");
				System.out.println("Path-==-Id is" + path);

				lockermasterobject= new LockerMasterObject();
				
				if (LKExtnObj.getForward().equalsIgnoreCase("verify")) 
				{

					boolean boolean_verify = true;
					LKExtnObj.setFlag("");
					System.out.println("///////////////"+ lockermasterobject.getRentUpto());

					lockermasterobject.setLockerAcType("1009001");
					lockermasterobject.setLockerAcNo(Integer.parseInt(LKExtnObj.getTxt_acNo()));
					lockermasterobject.setLockerType(LKExtnObj.getTxt_lockType());
					lockermasterobject.setLockerNo(Integer.parseInt(LKExtnObj.getTxt_lockNo()));

					 lockermasterobject.setRentBy(LKExtnObj.getReceiptDetails());
					 lockermasterobject.setTransAcType(LKExtnObj.getTransferAcntType());
					lockermasterobject.setTransAcNo(Integer.parseInt(LKExtnObj.getTransferAcntNum()));
					
					if(LKExtnObj.getReceiptDetails().equalsIgnoreCase("C"))
					{
						lockermasterobject.setRentColl(Double.parseDouble(LKExtnObj.getAmount()));
					}
					else if(LKExtnObj.getReceiptDetails().equalsIgnoreCase("T"))
					{
						lockermasterobject.setRentColl(Double.parseDouble(LKExtnObj.getTxt_rentAmnt()));
					}
					
					lockermasterobject.setTrnDate(CommonDelegate.getSysDate());

					lockermasterobject.setMatDate(LKExtnObj.getTxt_extnDate());
					lockermasterobject.setRentUpto(LKExtnObj.getTxt_extnDate());
					lockermasterobject.setReqdMonths(Integer.parseInt(LKExtnObj.getTxt_extnMonths()));
                    lockermasterobject.setRequiredDays(Integer.parseInt(LKExtnObj.getTxt_extnDays()));
                    
					lockermasterobject.uv.setVerId(uid);
					lockermasterobject.uv.setVerTml(tml);
					lockermasterobject.uv.setVerDate(LockerDelegate.getSysDate());

					System.out.println("Om namah shiva-->"+ lockermasterobject.getMatDate());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getReqdMonths());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getRequiredDays());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getTrnDate());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getNoOfSecurities());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getLockerAcNo());
					System.out.println("Om namah shiva-->"+ lockermasterobject.uv.getVerDate());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getTrnDate());
					System.out.println("Om namah shiva-->"+ lockermasterobject.getLockerAcType());

					if (lkd.updateLocker(lockermasterobject, 5)) 
					{
						System.out.println("done");

						LKExtnObj.setValidateFlag("Account Extension  Verified Successfully");
						
					}
					else
					{
						LKExtnObj.setSuccessFlag(" Cant Extend");
						System.out.println("not done ");
						
					}

				}
				else if(LKExtnObj.getForward().equalsIgnoreCase("LockerNo"))
				{
					lockermasterobject = lkd.getLockerMaster(acnum, 3);
					
					
					if(lockermasterobject!=null)
					{
							array_lockers_module=lkd.getMainModules("1009000");
							array_lockerparamobject=lkd.getLockerTypes();
                        
						    for(int d=0;d<array_lockerparamobject.length;d++)
                            {
                                if(lockermasterobject.getLockerType().equals(array_lockerparamobject[d].getLockerType()))
                                {
                                    LKExtnObj.setTxt_lockType(array_lockerparamobject[d].getDescription());
                                }
                            }
							
							LKExtnObj.setTxt_lockNo(String.valueOf(lockermasterobject.getLockerNo()));
                    		LKExtnObj.setTxt_allotDate(lockermasterobject.getAllotDate());
                    		LKExtnObj.setTxt_expiryDate(lockermasterobject.getMatDate());
                    		LKExtnObj.setTxt_rentUpto(lockermasterobject.getRentUpto());
                    		LKExtnObj.setTxt_totRent(String.valueOf(lockermasterobject.getTotalRentColl()));
                    		LKExtnObj.setTxt_rentAmnt(String.valueOf(lockermasterobject.getRentColl()));
                    		
                    		
                    		if(lockermasterobject.getTransAcNo()!=0)
                            {
                    			LKExtnObj.setReceiptDetails(lockermasterobject.getRentBy());
                    			if(lockermasterobject.getRentBy().equalsIgnoreCase("T"))
                    			{	
                    				LKExtnObj.setTransferAcntType(lockermasterobject.getTransAcType());
                    				LKExtnObj.setTransferAcntNum(String.valueOf(lockermasterobject.getTransAcNo()));
                    			}
                    			else if(lockermasterobject.getRentBy().equalsIgnoreCase("C"))
                    			{
                    				try
                    	            {
                    	                LKExtnObj.setScrollNum(String.valueOf(lockermasterobject.getTransAcNo()));
                    	                AccountObject a=lkd.getAccount("C",LKExtnObj.getTxt_acType(),lockermasterobject.getTransAcNo(),lkd.getSysDate());
                    	                LKExtnObj.setDate(a.getQdp_date());
                    	                LKExtnObj.setAmount(String.valueOf(a.getAmount()));
                    	            }
                    	            catch(RemoteException remoteexception)
                    	            {
                    	                remoteexception.printStackTrace();
                    	            }
                    			}
                            }
                    		
                    		if(((Validations.validDate(lockermasterobject.getRentUpto(),lockermasterobject.getMatDate()))==1))
	                        {
                    			System.out.println("inside validate date");
                    			try
                                {
                                    int total_no_months=lkd.getNoOfMonths(lockermasterobject.getMatDate(),lockermasterobject.getRentUpto());
                                    LKExtnObj.setTxt_extnMonths(String.valueOf(total_no_months));
                                    String exp_date_month=lkd.getFutureMonthDate(lockermasterobject.getMatDate(),total_no_months);
                                    int total_no_days=lkd.getDaysFromTwoDate(exp_date_month,lockermasterobject.getRentUpto());
                                    LKExtnObj.setTxt_extnDays(String.valueOf(total_no_days));                        
                                }
                                catch(RemoteException remoteException)
                                {
                                    remoteException.printStackTrace();
                                }
                                
                                LKExtnObj.setTxt_extnDate(lockermasterobject.getRentUpto());
	                            
	                        }
                    		else
                    		{
                    			LKExtnObj.setTxt_extnMonths(String.valueOf(0));
                    			LKExtnObj.setTxt_extnDays(String.valueOf(0)); 
                    			LKExtnObj.setTxt_extnDate(lockermasterobject.getMatDate());
                    		}
					
                    		if(LKExtnObj.getSelect_details().equalsIgnoreCase("personal"))
                    		{
                    			String perPath = MenuNameReader.getScreenProperties("0001");
								int cid = lockermasterobject.getCid();
								cmobject = lkd.getCustomer((cid));
								httpServletRequest.setAttribute("personalInfo", cmobject);
								httpServletRequest.setAttribute("flag",perPath);
								httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
								System.out.println("flag gonna set");

                    		}
                    		else if(LKExtnObj.getSelect_details().equalsIgnoreCase("signature"))
                    		{
                    			String perPath = MenuNameReader.getScreenProperties("0007");
								try{
                    			sObject= lkd.getSignatureDetails(lockermasterobject.getMemberNo(),lockermasterobject.getMemberType().trim());
								
								httpServletRequest.setAttribute("signInfo", sObject);
								httpServletRequest.setAttribute("flag",perPath);
								httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
								System.out.println("flag gonna set");
								}
								catch (Exception e) 
								{
									LKExtnObj.setValidateFlag("Signature Details Not Found");
								}
							 }
						
                    		else if(LKExtnObj.getSelect_details().equalsIgnoreCase("Deposit"))
                    		{
                    			  if(lockermasterobject.getDeposits()!=null)
                    			  {
                    				 vobject=lockermasterobject.getDeposits();
              						 String string_code=null;
              						 int int_acno=0;
              	                     System.out.println("lockermasterobject.getDeposits size = "+vobject.size());
              	                     Enumeration enumeration=vobject.elements();
              	                     while(enumeration.hasMoreElements())
              	                     {
              	                          StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
              	                          string_code=tokenizer.nextElement().toString();
              	                          int_acno=Integer.parseInt(tokenizer.nextToken());
              	                          	
              	                     }
              	                    try
    			    	     	    {
    			    	     		  DepositMasterObject object=lkd.getDepositMaster(string_code,int_acno);
    			    	     	   	  System.out.println("deposit master object==>"+object);	
    			    	     	   	  int dcid=object.getCustomerId();
    			    	     	   	  System.out.println("customer id--->"+dcid);
    			    	     	   	  cmobject=lkd.getCustomer(dcid);
    			    	     	   	  String perPath=MenuNameReader.getScreenProperties("9035");
    			    	     	   	  httpServletRequest.setAttribute("personalInfo",cmobject);
    			    	     	   	  httpServletRequest.setAttribute("depositInfo",object);
    			    	     	   	  httpServletRequest.setAttribute("flag",perPath);
    			    	     	   	  httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
    			   	     	   	 		 
    			    	     	   }
    			    	     	   catch (Exception e) 
    			    	     	   {
    			    	     	   	  System.out.println("inside exception deposit");
    			    	     	   	  LKExtnObj.setValidateFlag("Deposit Details Not Found");
    			    	     	   	  e.printStackTrace();
    			    	     	   }
    			    	     	   	 	
                    			}
                    			else
                    			{
                    				LKExtnObj.setValidateFlag("Deposit Details Not Found");
                    			}
                    		}
						
					}
					else if (lockermasterobject == null)
					{
						LKExtnObj.setValidateFlag("Records not found");
						LKExtnObj.setTxt_acNo(null);
					}	
					
					
				} 
				
				httpServletRequest.setAttribute("transferTypes",transfer);
				getLkAbbr(httpServletRequest, path, lkd);
				httpServletRequest.setAttribute("pageId", path);
				return actionMapping.findForward(ResultHelp.getSuccess());

			}
			else 
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKMaserUpdateMLink")) 
		{
			try 
			{
				System.out.println("can-i-...  create lkisform ");
				ModuleObject moduleobject_introducer[] = null;
				LockerIssueForm lkisform = (LockerIssueForm) actionForm;
				System.out.println("my page id " + lkisform.getPageId());

				System.out.println("wen i printed 2nd time my page id "+ lkisform.getPageId());
				if(MenuNameReader.containsKeyScreen(lkisform.getPageId())) 
				{
					path = MenuNameReader.getScreenProperties(lkisform.getPageId());
					System.out.println("wen i gone thro Reader" + path);
					moduleobject_introducer = lkd.getMainModules("1002000,1007000,1001000");
					ModuleObject[] transfer=lkd.getMainModules("1002000,1007000");
					httpServletRequest.setAttribute("transfertypes",transfer);
					String date1 = lkd.getSysDate();
					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);
					ModuleObject[] moduleObject=lkd.getMainModules(2);
        		    httpServletRequest.setAttribute("DepositTypes",moduleObject); 
        		 	httpServletRequest.setAttribute("introtypes",moduleobject_introducer);
        		 	
        		 	httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LKMaserUpdateLink")) 
		{
			
			try 
			{
				LockerIssueForm lockerIssueForm = (LockerIssueForm) actionForm;

				if(MenuNameReader.containsKeyScreen(lockerIssueForm.getPageId())) 
				{
					lockerIssueForm.setValidateFlag("");
					String jointholder="";
					ModuleObject[] moduleobject_introducer = lkd.getMainModules("1002000,1007000,1001000");
					ModuleObject[] transfer=lkd.getMainModules("1002000,1007000");
					
					String Nominee="";
					System.out.println(lockerIssueForm.getForward()+ "#####<------------>######"+ lockerIssueForm.getDetails());
					
					
					if (!lockerIssueForm.getDetails().trim().equals("SELECT")) 
					{	

						if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("personal")) 
						{
							System.out.println("after personal");
							String perPath = MenuNameReader.getScreenProperties("0001");
							String cid = lockerIssueForm.getTxt_cid();
							cmobject = lkd.getCustomer(Integer.parseInt(cid));
							httpServletRequest.setAttribute("personalInfo", cmobject);
							httpServletRequest.setAttribute("flag",perPath);
							httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
							System.out.println("flag gonna set");

						}
						if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("Introducer")) 
						{

							if((lockerIssueForm.getIntrNum())!=null)
							{
								int intacNum = Integer.parseInt(lockerIssueForm.getIntrNum());
								String intacType = lockerIssueForm.getIntrType();
								String perPath = MenuNameReader.getScreenProperties("0001");
								System.out.println("NUMMMM" + intacNum+ "TYPEEE" + intacType);
								accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
								if(accountobject!=null)
								{
									cmobject = lkd.getCustomer(accountobject.getCid());
									httpServletRequest.setAttribute("personalInfo", cmobject);
									httpServletRequest.setAttribute("flag",perPath);
									httpServletRequest.setAttribute("panelName", CommonPanelHeading.getIntroucer());
								}
								else
								{
								 lockerIssueForm.setValidateFlag("Introducer Account Num Found");
								}	
						
							}
							else
							{
								lockerIssueForm.setValidateFlag("Introducer Field Can't Be Left Blank");
							}
					 }
					 if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("Signature")) 
					 {
						
						System.out.println("after personal");

						String perPath = MenuNameReader.getScreenProperties("0007");
						sObject= lkd.getSignatureDetails(Integer.parseInt(lockerIssueForm.getMembAc().trim()),lockerIssueForm.getMembType().trim());
						httpServletRequest.setAttribute("signInfo", sObject);
						httpServletRequest.setAttribute("flag",perPath);
						httpServletRequest.setAttribute("panelName", CommonPanelHeading.getSignature());
						System.out.println("flag gonna set");

					 }
					 if (lockerIssueForm.getDetails().trim().equalsIgnoreCase("Nominee")) 
					 {
						
						System.out.println("after Nominee");
						Nominee="nomineeDetails";
						httpServletRequest.setAttribute("nomdetail",Nominee);
						
						if(lockerIssueForm.getForward().equalsIgnoreCase("nomineecid"))
						{
							if(lockerIssueForm.getNomineeCid()!=null)
							{
								int ncid=Integer.parseInt(lockerIssueForm.getNomineeCid());
									cmobject=lkd.getCustomer(ncid);
									if(cmobject!=null)
									{
										lockerIssueForm.setNomineeName(cmobject.getName());
										lockerIssueForm.setNomineeDob(cmobject.getDOB());
										lockerIssueForm.setNomineeAge(cmobject.getDOB());
										lockerIssueForm.setNomineeAddress(cmobject.getAddress().toString());
										lockerIssueForm.setNomineeSex(cmobject.getSex());
									}
									else
									{
										lockerIssueForm.setValidateFlag("Nominee ID Not Found");
									}
							 }
						}
						
						
					 }
					 if(lockerIssueForm.getDetails().trim().equalsIgnoreCase("Deposit"))
	    			 {
						 		 LockerMasterObject lockermasterobject=new LockerMasterObject();
						 		 System.out.println("after deposit");
	    	        		   
	    	        		     String actype=lockerIssueForm.getCombo_details();
	    	     	   	 		     String accnum=lockerIssueForm.getAcc_no();
	    	     	   	 			 System.out.println("combo details of deposit===>"+actype);
	    	     	   	 			 System.out.println("accnum of deposit==>"+accnum);
	    	     	   	 			 
	    	     	   	 			 try
	    	     	   	 			 {
	    	     	   	 				 DepositMasterObject object=lkd.getDepositMaster(actype,Integer.parseInt(accnum));
	    	     	   	 				 System.out.println("deposit master object==>"+object);	
	    	     	   	 				 int dcid=object.getCustomerId();
	    	     	   	 				 System.out.println("customer id--->"+dcid);
	    	     	   	 				 cmobject=lkd.getCustomer(dcid);
	    	     	   	 				 String perPath=MenuNameReader.getScreenProperties("9035");
	    	     	   	 				 httpServletRequest.setAttribute("personalInfo",cmobject);
	    	     	   	 				 httpServletRequest.setAttribute("depositInfo",object);
	    	     	   	 				 httpServletRequest.setAttribute("flag",perPath);
	    	     	   	 				 httpServletRequest.setAttribute("panelName",CommonPanelHeading.getDepositDetails());
	   	     	   	 		 
	    	     	   	 				
	    	     	   	 			  }
	    	     	   	 			  catch (Exception e) 
	    	     	   	 			  {
	    	     	   	 				  System.out.println("inside exception deposit");
	    	     	   	 				 lockerIssueForm.setValidateFlag("Deposit Details Not Found");
	    	     	   	 				 e.printStackTrace();
									  }
	    	     	   	 	
	    	     	   	 		 
	    	        	 }
						if(lockerIssueForm.getDetails().trim().equalsIgnoreCase("JointHolders"))
						{
							jointholder="jointholder";
							httpServletRequest.setAttribute("JOINT",jointholder);
							String perPath = MenuNameReader.getScreenProperties("0001");
							
								if(lockerIssueForm.getCid().trim()!=null && lockerIssueForm.getCid().trim().length()!=0)
								{
									cmobject=lkd.getCustomer(Integer.parseInt(lockerIssueForm.getCid().trim()));
									if(cmobject!=null)
									{
										httpServletRequest.setAttribute("flag",perPath);
										httpServletRequest.setAttribute("panelName", CommonPanelHeading.getPersonal());
										httpServletRequest.setAttribute("personalInfo",cmobject);
										System.out.println("flag gonna set");
									}
									else
									{
										lockerIssueForm.setValidateFlag("CustomerID Not Found");
									}
								}
								else 
								{
									lockerIssueForm.setValidateFlag("JointHolder Details Not Found");
									
								}	
					
					    }
				    }
					if(lockerIssueForm.getForward().equalsIgnoreCase("frmAcntNum"))
					{
						if(lockerIssueForm.getLkacNum().trim().length()==0 || lockerIssueForm.getLkacNum().equals("0"))
			            {
			                
							lockerIssueForm.setValidateFlag("Enter a Valid Account Number");
							lockerIssueForm.setLkacNum("0");
			            }
						else if(lockerIssueForm.getLkacNum().trim()!=null && lockerIssueForm.getLkacNum().trim().length()!=0)
						{
							LockerMasterObject lockermasterobject = lkd.getLockerMaster(Integer.parseInt(lockerIssueForm.getLkacNum().trim()),6);
							
							if(lockermasterobject != null) 
							{
								if (lockermasterobject.getLockerNo() == -1)
								{
									lockerIssueForm.setValidateFlag("Locker Not found");
									lockerIssueForm.setLkacNum("");
								}	
								else if (lockermasterobject.getClosingDEDtTime() != null)
								{
									lockerIssueForm.setValidateFlag("Locker Already alloted");
									lockerIssueForm.setLkacNum("");
								}
								else 
								{
									// all setting code goes here
									System.out.println("all setting code goes here");
									lockerIssueForm.setTxt_cid(String.valueOf(lockermasterobject.getCid()));
									lockerIssueForm.setLkNum(String.valueOf(lockermasterobject.getLockerNo()));
									lockerIssueForm.setLkType(lockermasterobject.getLockerType());
									lockerIssueForm.setLkKey(String.valueOf(lockermasterobject.getKeyNo()));
									if(lockermasterobject.getFreezeInd()!=null)
									{
										if(lockermasterobject.getFreezeInd().equals("T"))
										{
											lockerIssueForm.setFreeze(true);
										}
										else if(lockermasterobject.getFreezeInd().equals("F"))
										{
											lockerIssueForm.setFreeze(false);
										}
									}
									lockerIssueForm.setAllotDate(lockermasterobject.getAllotDate());
									lockerIssueForm.setExpDate(lockermasterobject.getMatDate());
									lockerIssueForm.setMembAc(String.valueOf(lockermasterobject.getMemberNo()));
									lockerIssueForm.setOpInstr(lockermasterobject.getOprInstrn());
									lockerIssueForm.setReceiptDetails(lockermasterobject.getRentBy());
									lockerIssueForm.setIntrType(lockermasterobject.getIntrAcTy());
									lockerIssueForm.setIntrNum(String.valueOf(lockermasterobject.getIntrAcNo()));
									System.out.println("introducer nbu,m   inlk num--"+lockermasterobject.getIntrAcNo());
									lockerIssueForm.setNomReg(String.valueOf(lockermasterobject.getNomRegNo()));
									lockerIssueForm.setLkRent(String.valueOf(lockermasterobject.getRentColl()));
									lockerIssueForm.setMonths(String.valueOf(lockermasterobject.getReqdMonths()));
									lockerIssueForm.setDays(String.valueOf(lockermasterobject.getRequiredDays()));
									lockerIssueForm.setAutoExtn(lockermasterobject.getAutoExtn());
									
									if(lockermasterobject.getNomineeDetails()!=null)
									{
										NomineeObject[] objects=lockermasterobject.getNomineeDetails();
										if(objects!=null)
										{
											if(objects[0].getCustomerId()!=0)
											{
											lockerIssueForm.setNomineeCid(String.valueOf(objects[0].getCustomerId()));
											lockerIssueForm.setHasAccount(true);
											}
											lockerIssueForm.setNomineeName(objects[0].getNomineeName());
											lockerIssueForm.setNomineeAddress(objects[0].getNomineeAddress());
											lockerIssueForm.setNomineeDob(objects[0].getNomineeDOB());
											//lockerIssueForm.setNomineeAge(objects[0].getSex());
											lockerIssueForm.setNomineePercentage(String.valueOf(objects[0].getPercentage()));
											lockerIssueForm.setNomineeRelationship(objects[0].getNomineeRelation());
											
										}
									}
									/*if(lockermasterobject.getLockerPW()!=null)
									{
										lockerIssueForm.setPass(lockermasterobject.getLockerPW());
										lockerIssueForm.setRequired(true);
									}*/
									if(lockermasterobject.getNoOfSecurities()>0)
									{
										Vector vector_deposits = null;
		                                
		                                if(lockermasterobject.getDeposits()!=null)
				                        {
				                           
				                            vector_deposits=lockermasterobject.getDeposits();
				                            System.out.println("lockermasterobject.getDeposits size = "+vector_deposits.size());
				                            Enumeration enumeration=vector_deposits.elements();
				                            while(enumeration.hasMoreElements())
				                            {
				                            	StringTokenizer tokenizer=new StringTokenizer(enumeration.nextElement().toString()," ");
				                            	String string_code=tokenizer.nextToken();
				                            	int int_acno=Integer.parseInt(tokenizer.nextToken());
				                            	lockerIssueForm.setAcc_no(String.valueOf(int_acno));	
				                        
				                            }
				                            
				                        }
		                                
									}
									if(lockermasterobject.getJointCid()!=null)
									{
										int cid[]=lockermasterobject.getJointCid();
										lockerIssueForm.setCid(String.valueOf(cid[0]));	
									}
									httpServletRequest.setAttribute("LockerDetails",lockermasterobject);
								}
							} 
							else
							{
								lockerIssueForm.setValidateFlag("Locker A/c Not Found");
								lockerIssueForm.setLkacNum("");
							}
						}
					}
					if(lockerIssueForm.getForward().equalsIgnoreCase("Cid"))	
					{
						    System.out.println("inside if ccccccccccccccccid");
						
							if (lockerIssueForm.getTxt_cid().trim() != null) 
							{
								
								System.out.println("################## WEN CID NNNNOOOO=NULL ###########################");
								CustomerMasterObject customerMasterObject = lkd.getCustomer(Integer.parseInt(lockerIssueForm.getTxt_cid()));
								if(customerMasterObject!=null)
								{
									accountobject = lkd.getShareAccount(Integer.parseInt(lockerIssueForm.getTxt_cid()));
									if(accountobject!=null)
									{
										lockerIssueForm.setMembType(accountobject.getAcctype());
										lockerIssueForm.setMembAc(String.valueOf(accountobject.getAccno()));
										System.out.println("At 1964 share acnt no is account "+ accountobject.getAccno());
										System.out.println("share type "+ accountobject.getAcctype());
									}
									else
									{
										lockerIssueForm.setValidateFlag("Customer Is Not A Share Holder");
										lockerIssueForm.setTxt_cid("");
									}
								}
								else
								{
									lockerIssueForm.setValidateFlag("Customer ID Not Found");
									lockerIssueForm.setTxt_cid("");
								}
							
							
						}
						lockerIssueForm.setLkacNum("0");	
						
					
				    }
					if(lockerIssueForm.getForward().equalsIgnoreCase("IntrNum"))
					{	
						if((lockerIssueForm.getIntrNum())!=null && lockerIssueForm.getIntrNum().length()!=0)
						{
							int intacNum = Integer.parseInt(lockerIssueForm.getIntrNum());
							String intacType = lockerIssueForm.getIntrType();
							System.out.println("NUMMMM--" + intacNum+ "--TYPEEE--" + intacType);
							accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
							if(accountobject!=null)
							{
								lockerIssueForm.setIntrNum(String.valueOf(intacNum));
								System.out.println("jfjejdjdjdjdyuws"+intacNum);
							}
							else
							{
								lockerIssueForm.setValidateFlag("Introducer A/c Number Found");
								lockerIssueForm.setIntrNum("");
							}	
				
						}
					}
					if(lockerIssueForm.getForward().equalsIgnoreCase("nomineecid"))
					{
						if(lockerIssueForm.getNomineeCid()!=null)
						{
							int ncid=Integer.parseInt(lockerIssueForm.getNomineeCid());
								cmobject=lkd.getCustomer(ncid);
								if(cmobject!=null)
								{
									lockerIssueForm.setNomineeName(cmobject.getName());
									lockerIssueForm.setNomineeDob(cmobject.getDOB());
									lockerIssueForm.setNomineeAge(cmobject.getDOB());
									lockerIssueForm.setNomineeAddress(cmobject.getAddress().toString());
									lockerIssueForm.setNomineeSex(cmobject.getSex());
								}
								else
								{
									lockerIssueForm.setValidateFlag("Nominee ID Not Found");
								}
						 }
					}
					if(lockerIssueForm.getForward().equalsIgnoreCase("depositnum"))
    	   	 		{
						 try
						 {
							 DepositMasterObject object=lkd.getDepositMaster(lockerIssueForm.getCombo_details(),Integer.parseInt(lockerIssueForm.getAcc_no()));
							 System.out.println("deposit master object==>"+object);	
							 if(object!=null)
							 {
								 String sub=lockerIssueForm.getCombo_details().substring(1,4);
							 	if(object.getVerified().equals("F"))
								{	
									lockerIssueForm.setValidateFlag("Deposit A/c Number Is Not Yet Verified");
									lockerIssueForm.setAcc_no("");
								}
								else if(object.getLoanAvailed().equals("T"))//ship.....04/02/2006....added type==0
								{	
									lockerIssueForm.setValidateFlag("Loan Is Already Availed On This Deposit");
									lockerIssueForm.setAcc_no("");
								}
								else if(object.getClosedt()!=null)
								{	
									lockerIssueForm.setValidateFlag("Deposit A/c Is Closed");
									lockerIssueForm.setAcc_no("");
								}
								else if((sub.equals("003") || sub.equals("004") || sub.equals("005"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
								{	
									
									lockerIssueForm.setValidateFlag("Maturity Date Lapsed");
									lockerIssueForm.setAcc_no("");
								}	
								else if((sub.equals("006"))?Validations.checkDateValid(object.getMaturityDate(),lkd.getSysDate())==-1:false)
								{	
									
									lockerIssueForm.setValidateFlag("Minimum Period For Deposit Lapsed");
									lockerIssueForm.setAcc_no("");
								}
								else if((sub.equals("006"))?object.getCloseStart().equals("C"):false)
								{	
									
									lockerIssueForm.setValidateFlag("Deposit Account Is Closed");
									lockerIssueForm.setAcc_no("");
								}
							 }
							 else
							 {
								 lockerIssueForm.setValidateFlag("Deposit Account Not Found");
								 lockerIssueForm.setAcc_no("");
							 }
								 
						 }
						 catch (Exception e) 
						 {
							lockerIssueForm.setValidateFlag("Deposit Account Details Not Found");
							lockerIssueForm.setAcc_no("");
						 }
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("jointcid"))
					{
						if(lockerIssueForm.getCid().trim()!=null && lockerIssueForm.getCid().trim().length()!=0)
						{
							cmobject=lkd.getCustomer(Integer.parseInt(lockerIssueForm.getCid().trim()));
							if(cmobject==null)
							{
								lockerIssueForm.setValidateFlag("CustomerID Not Found");
								lockerIssueForm.setCid("");
							}
							
						}
						else
						{
							lockerIssueForm.setValidateFlag("CustomerID Field Can't Be Left Blank");
						}
					}
					else if(lockerIssueForm.getForward().equalsIgnoreCase("submit")) 
					{

							LockerMasterObject lockermasterobject =null;
							 int proceed = 0;
							 boolean update;
							 int test_lk_acno = 0;
							 boolean continue_transaction = false;
							
							System.out.println("inside submitt of issueee");
							{
				            				lockermasterobject=new LockerMasterObject();
				            				
											System.out.println("inside of cond true of submit-->"+lockerIssueForm.getLkacType());
				            				
											if(lockerIssueForm.getNomineeCid()!=null || lockerIssueForm.getNomineeName()!=null)
				            				{
												
												System.out.println("Inside Nominee Details---------->");
														
				            						arrayNomineeObject=new NomineeObject[1];
				            						arrayNomineeObject[0]=new NomineeObject();
				            						if(lockerIssueForm.isHasAccount())
				            						{
				            								arrayNomineeObject[0].setCustomerId(Integer.parseInt(lockerIssueForm.getNomineeCid()));
				            						}
				            						else
				            						{
				            								arrayNomineeObject[0].setCustomerId(0);
					            							
				            						}
				            						
				            							arrayNomineeObject[0].setNomineeName(lockerIssueForm.getNomineeName());
				            							arrayNomineeObject[0].setNomineeDOB(lockerIssueForm.getNomineeDob());
				            							arrayNomineeObject[0].setNomineeAddress(lockerIssueForm.getNomineeAddress());
//											    		nObjects.setSex((lockerIssueForm.getNomineeSex()));
				            							arrayNomineeObject[0].setNomineeRelation(lockerIssueForm.getNomineeRelationship());
				            							arrayNomineeObject[0].setPercentage(Integer.parseInt(lockerIssueForm.getNomineePercentage()));
				            							lockermasterobject.setNomineeDetails(arrayNomineeObject);
				            				}
				            				else
				            				{
				            					lockermasterobject.setNomineeDetails(null);
					            				
				            				}
				            				
				            				test_lk_acno = Integer.parseInt(lockerIssueForm.getLkacNum());
				            				lockermasterobject.setLockerAcType(lockerIssueForm.getLkacType());
				            				lockermasterobject.setLockerAcNo(test_lk_acno);
				            				
				            				lockermasterobject.setCid(Integer.parseInt(lockerIssueForm.getTxt_cid()));
				            				// lockermasterobject.setMailingAddress();

				            				System.out.println("<-->"+ lockerIssueForm.getLkNum() + "<-->"+ lockerIssueForm.getAllotDate() + "<-->"+ lockerIssueForm.getMonths() + "<-->"+ lockerIssueForm.getDays() + "<-->"+ lockerIssueForm.getExpDate() + "<-->"+ lockerIssueForm.getMembType());
				            				System.out.println(lockerIssueForm.getMembAc()+ "<-->" + lockerIssueForm.getMembType());
				            				lockermasterobject.setLockerType(lockerIssueForm.getLkType());// s m l
				            				System.out.println("1++++++++++++++++++++++++++++++");
				            				lockermasterobject.setLockerNo(Integer.parseInt(lockerIssueForm.getLkNum()));
				            				System.out.println("2++++++++++++++++++++++++++++++");
				            				lockermasterobject.setTrnDate(LockerDelegate.getSysDate());

				            				if(lockerIssueForm.isFreeze())
				            				{
				            					lockermasterobject.setFreezeInd("T");
				                              //  lockermasterobject.setReason(txtarea_reason.getText().trim().toString());
				                        	}
				            				else
				            				{
				                                lockermasterobject.setFreezeInd("F");
				            				}
				            				try 
				            				{
				            					lockermasterobject.setAllotDate(Validations.checkDate(lockerIssueForm.getAllotDate()));
				            				}
				            				catch (DateFormatException e2) 
				            				{
				            					e2.printStackTrace();
				            				}
				            				System.out.println("3++++++++++++++++++++++++++++++");
				            				lockermasterobject.setReqdMonths(Integer.parseInt(lockerIssueForm.getMonths()));
				            				System.out.println("4++++++++++++++++++++++++++++++");
				            				lockermasterobject.setRequiredDays(Integer.parseInt(lockerIssueForm.getDays()));

				            				try 
				            				{
				            					lockermasterobject.setMatDate(Validations.checkDate(lockerIssueForm.getExpDate()));// Expiry
				            					// date
				            				} 
				            				catch (DateFormatException e3) 
				            				{
				            					e3.printStackTrace();
				            				}

				            				System.out.println("5++++++++++++++++++++++++++++++");
				            				// String
				            				// string_share_modulecode=array_moduleobject_shares[combo_member_type.getSelectedIndex()].getModuleCode();
				            				lockermasterobject.setShareCode(lockerIssueForm.getMembType());
				            				lockermasterobject.setMemberNo(Integer.parseInt(lockerIssueForm.getMembAc()));
				               				if(!(lockerIssueForm.getOpInstr().equalsIgnoreCase("Individual")))
				            				{
				            					if(lockerIssueForm.getCid()!=null)
				            					{
				            						int jointc[]=new int[1];
				            						int addtyp[]=new int[1];
				            						jointc[0]=Integer.parseInt(lockerIssueForm.getCid());
				            						addtyp[0]=Integer.parseInt("1");
				            						System.out.println("------joint ----cid---submit----"+jointc[0]);
				            						lockermasterobject.setJointCid(jointc);
				            						lockermasterobject.setAddr(addtyp);
				            					}
				            				}	
				            				else
				            				{
				            					lockermasterobject.setJointCid(null);
			            						lockermasterobject.setAddr(null);
				            				}
				            				lockermasterobject.setOprInstrn(lockerIssueForm.getOpInstr());
				            				try 
				            				{
				            					lockermasterobject.setRentUpto(Validations.checkDate(lockerIssueForm.getExpDate()));
				            				}
				            				catch (DateFormatException e1) 
				            				{
				            					e1.printStackTrace();
				            				}
				            				lockermasterobject.setRentColl(Double.parseDouble(lockerIssueForm.getLkRent()));
				            				lockermasterobject.setRentBy(lockerIssueForm.getReceiptDetails());
				            				lockerIssueForm.setTransferAcntNum(String.valueOf(lockermasterobject.getTransAcNo()));
											lockerIssueForm.setTransferAcntType(lockermasterobject.getTransAcType());
									
				            				if (lockerIssueForm.getAutoExtn().equalsIgnoreCase("YES"))
				            					lockermasterobject.setAutoExtn("T");
				            				else if (lockerIssueForm.getAutoExtn().equalsIgnoreCase("NO"))
				            					lockermasterobject.setAutoExtn("F");
				            				// //////////////////

				            				if ((!lockerIssueForm.getIntrNum().equalsIgnoreCase("0"))&& lockerIssueForm.getIntrNum() != null && (!lockerIssueForm.getIntrNum().equalsIgnoreCase(""))) 
				            				{
				            					lockermasterobject.setIntrAcTy(lockerIssueForm.getIntrType());
				            					lockermasterobject.setIntrAcNo(Integer.parseInt(lockerIssueForm.getIntrNum()));
				            				} 
				            				else 
				            				{
				            					lockermasterobject.setIntrAcTy("");
				            					lockermasterobject.setIntrAcNo(0);
				            					lockermasterobject.setIntrName("");
				            				}

				            				lockermasterobject.setNomRegNo(0);
				            				lockermasterobject.uv.setUserTml(tml);
				            				lockermasterobject.uv.setUserId(uid);
				            				lockermasterobject.uv.setUserDate(LockerDelegate.getSysDate());
				            				
				            				if(lockerIssueForm.getAcc_no()!=null && lockerIssueForm.getAcc_no().length()!=0)
				            				{
				            					//deposit master  
				            					
				            					String depactyp=lockerIssueForm.getCombo_details();
				            					String depacnum=lockerIssueForm.getAcc_no();
				            					System.out.println("depAcNum).toString()--->"+depacnum);
				            					System.out.println("depAcType).toString()--->"+depactyp);
				            					Vector vector_accounts=null;
				            					vector_accounts=new Vector(0,1);
				            					vector_accounts.add(depactyp+" "+depacnum);
				            					lockermasterobject.setDeposits(vector_accounts);
				            					lockermasterobject.setNoOfSecurities(1);
				            				}
				            				else
				            				{
				            					lockermasterobject.setDeposits(null);
				            					lockermasterobject.setNoOfSecurities(0);
				            					
				            				}
				            					try 
				            					{
				            						System.out.println("inside try - storeLockerMaster");
				            						if(Integer.parseInt(lockerIssueForm.getLkacNum())!=0)
				            						{
				            							update = lkd.updateLockerMaster(lockermasterobject);
				            							System.out.println("outside try - storeLockerMaster--"+ update);
				            						}
				            						else
				            						{
				            							lockerIssueForm.setValidateFlag("You Can't Create New A/c");
				            						}
				            					}  
				            					catch (Exception e) 
				            					{
				            						e.printStackTrace();
				            					}     	
				                               
				                                if(update=true &&  Integer.parseInt(lockerIssueForm.getLkacNum())!=0)
				            					{
				                                	lockerIssueForm.setValidateFlag(" Locker A/c No Updated Successfully");
				                                  	
				            					}
				                                else
				                                {
				                                	lockerIssueForm.setValidateFlag("Updation Failed");
				                                  	
				                                }
							            }
				            
					}

					String date1 = lkd.getSysDate();
					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);
					ModuleObject[] moduleObject=lkd.getMainModules(2);
        		    httpServletRequest.setAttribute("DepositTypes",moduleObject); 
        		 	httpServletRequest.setAttribute("introtypes",moduleobject_introducer);
        		 	httpServletRequest.setAttribute("transfertypes",transfer);
        		 	httpServletRequest.setAttribute("pageId", path);
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerIssueForm.getPageId()));
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerOperationVerificationMLink")) {
			try {
				System.out.println("B4 creating form object");
				LockerOperationForm LKOpFormobj = (LockerOperationForm) actionForm;
				if (MenuNameReader.containsKeyScreen(LKOpFormobj.getPageId())) {

					path = MenuNameReader.getScreenProperties(LKOpFormobj
							.getPageId());
					String page = LKOpFormobj.getPageId();
					System.out.println("page id should be surr.jsp but=="
							+ LKOpFormobj.getPageId() + "n path is" + path);
					getLkAbbr(httpServletRequest, page, lkd);

					LKOpFormobj.setValidateFlag("");
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				} else {
					return actionMapping.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerOperationVerificationLink")) {
			
			try {
				System.out.println("B4 creating form object");
				LockerOperationForm LKOpFormobj = (LockerOperationForm) actionForm;

				if (MenuNameReader.containsKeyScreen(LKOpFormobj.getPageId())) {

					path = MenuNameReader.getScreenProperties(LKOpFormobj.getPageId());
					String page = LKOpFormobj.getPageId();
					System.out.println("page id should be surr.jsp but=="+ LKOpFormobj.getPageId() + "n path is" + path);
					LKOpFormobj.setValidateFlag(null);
					System.out.println("-----!" + LKOpFormobj.getForward());

					LockerMasterObject lockerMasterObject = lkd.getLockerMaster(Integer.parseInt(LKOpFormobj.getAcn_no()), 2);
					LockerTransObject lockertransobject = new LockerTransObject();

					if(lockerMasterObject == null) 
					{
						
						LKOpFormobj.setValidateFlag("Account not found");
						LKOpFormobj.setAcn_no(null);
					}
					else if (LKOpFormobj.getForward().equalsIgnoreCase("acntNum")) 
					{

						System.out.println(lockerMasterObject.getTimeOut()+ "<--->" + lockerMasterObject.getTimeIn());

						
						if (lockerMasterObject.getTimeOut() == null && lockerMasterObject.getTimeIn() == null)
						{
							LKOpFormobj.setValidateFlag("Not Eliglible number");
							LKOpFormobj.setAcn_no("");
						}
						else if (lockerMasterObject.getTimeOut().length()== 0 && lockerMasterObject.getTimeIn().length() != 0)
						{
								LKOpFormobj.setValidateFlag("Time out is not entered");
								LKOpFormobj.setAcn_no("");
						}		
						else if (lockerMasterObject.getTimeOut() != null&& lockerMasterObject.getTimeIn() == null)
						{
								LKOpFormobj.setValidateFlag("TimeIn is not entered");
								LKOpFormobj.setAcn_no("");
						}
						else if (lockerMasterObject.getTimeOut() != lockerMasterObject.getTimeIn()) 
						{
							httpServletRequest.setAttribute("lkparams",lockerMasterObject);
							
						}
					}

					else if (LKOpFormobj.getForward().equalsIgnoreCase("verify")) {

						System.out.println("inside verify");

						lockertransobject.setLockerAcType(LKOpFormobj.getCombo_types());
						lockertransobject.setLockerAcNo(Integer.parseInt(LKOpFormobj.getAcn_no()));
						lockertransobject.setLockerType(LKOpFormobj.getLockerType());
						lockertransobject.setLockerNo(Integer.parseInt(LKOpFormobj.getLockerNo()));
						lockertransobject.setOperatedBy(LKOpFormobj.getTxt_operatedby());
						lockertransobject.setOpDate(LKOpFormobj.getTxt_operatedon());
						lockertransobject.setTimeIn(LKOpFormobj.getTxt_timein());
						lockertransobject.setTimeOut(LKOpFormobj.getTxt_timeout());

						lockertransobject.setTrnDate(LKOpFormobj.getTxt_operatedon());

						lockertransobject.uv.setVerDate(CommonDelegate.getSysDate());
						lockertransobject.uv.setVerId(uid);
						lockertransobject.uv.setVerTml(tml);
						// boolean boolean_stored=false;
						boolean boolean_stored = lkd.storeLockerTransaction(lockertransobject, 3);

						if (boolean_stored)
							LKOpFormobj.setValidateFlag("Successfully verified");

						else
							LKOpFormobj.setValidateFlag("Error in verifing");

					}

					getLkAbbr(httpServletRequest, page, lkd);
					System.out.println("after setting intial param");
					httpServletRequest.setAttribute("pageId", path);
					System.out.println("after setting");
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
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerIssueVerificationMLink")) 
		{
			try {

				ModuleObject moduleobject_introducer[] = null;
				LockerIssueForm lkisform = (LockerIssueForm) actionForm;

				if (MenuNameReader.containsKeyScreen(lkisform.getPageId())) {
					path = MenuNameReader.getScreenProperties(lkisform
							.getPageId());

					moduleobject_introducer = lkd
							.getMainModules("1002000,1007000,1001000");
					String date1 = lkd.getSysDate();
					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);
					httpServletRequest.setAttribute("introtypes",
							moduleobject_introducer);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements(
							"Please check ! There is no name for the given key in the properties file",
							httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/lockers/LockerIssueVerificationLink")) 
		{
			try {

				ModuleObject moduleobject_introducer[] = null;
				LockerIssueForm lkisform = (LockerIssueForm) actionForm;

				if (MenuNameReader.containsKeyScreen(lkisform.getPageId())) {
					path = MenuNameReader.getScreenProperties(lkisform.getPageId());

					LockerMasterObject lockermasterobject = lkd.getLockerMaster(Integer.parseInt(lkisform.getLkacNum()), 0);
					moduleobject_introducer = lkd.getMainModules("1002000,1007000,1001000");
					String date1 = LockerDelegate.getSysDate();
					System.out.println("++++++++++++++="+ lkisform.getLkacNum());
					if(lockermasterobject!=null)
					{
					if (!lkisform.getDetails().trim().equals("SELECT")) 
					{

						if (lkisform.getDetails().trim().equalsIgnoreCase("personal")) {
							System.out.println("after personal");

							String perPath = MenuNameReader.getScreenProperties("0001");
							String cid = lkisform.getTxt_cid();
							cmobject = lkd.getCustomer(Integer.parseInt(cid));
							httpServletRequest.setAttribute("personalInfo",cmobject);
							httpServletRequest.setAttribute("flag", perPath);

							httpServletRequest.setAttribute("panelName",CommonPanelHeading.getPersonal());
							System.out.println("flag gonna set");

						}
						if(lkisform.getDetails().trim().equalsIgnoreCase("Introducer")) 
						{

							System.out.println("1introducer....?"+ lkisform.getIntrNum());

							int intacNum = Integer.parseInt(lkisform.getIntrNum());
							
							if(intacNum!=0)
							{
								System.out.println("introducer....?"+ lkisform.getIntrType());
								String intacType = lkisform.getIntrType();
								System.out.println("introducer....?");
								String perPath = MenuNameReader.getScreenProperties("0001");
								System.out.println("NUMMMM" + intacNum + "TYPEEE"+ intacType);
								accountobject = lkd.getIntroducerAcntDetails(intacType, intacNum);
								cmobject = lkd.getCustomer(accountobject.getCid());
								httpServletRequest.setAttribute("personalInfo",cmobject);
								httpServletRequest.setAttribute("flag", perPath);
								httpServletRequest.setAttribute("panelName",CommonPanelHeading.getIntroucer());

						    }
							else
							{
								lkisform.setValidateFlag("Introducer Details Not Found");
							}
						}	
					}
					}
					else
					{
						lkisform.setValidateFlag("Locker No Not Found");
					}
					if (lkisform.getForward().equalsIgnoreCase("frmAcntNum")) 
					{

						lkisform.setValidateFlag("");

						System.out.println("----" + lockermasterobject);

						if (lockermasterobject != null) 
						{
							if (lockermasterobject.getLockerNo() == -1)
							{
								lkisform.setValidateFlag("Locker A/c Not found");
								lkisform.setLkacNum("");
							}	
							else if (lockermasterobject.uv.getVerId() != null)
							{
								lkisform.setValidateFlag("Already verified");
								lkisform.setLkacNum("");
							}	
							else
								httpServletRequest.setAttribute("LockerMasterObject",lockermasterobject);
						} 
						else
						{
							lkisform.setValidateFlag("Locker A/c Not Found");
							lkisform.setLkacNum("");
						}
					} 
					else if (lkisform.getForward().equalsIgnoreCase("verify")) 
					{

						lockermasterobject.uv.setVerTml(tml);
						lockermasterobject.uv.setVerId(uid);
						lockermasterobject.uv.setVerDate(CommonDelegate.getSysDate());

						System.out.println(lockermasterobject.getTrnDate()+ "???????????"+ lockermasterobject.getTrnDate() + "<--...,"+ lockermasterobject.getLockerNo() + "<))))>"+ lockermasterobject.getLockerAcNo()+ "<00000>"+ lockermasterobject.getLockerAcType());
						
						int int_locker_no = lkd.storeLockerMaster(lockermasterobject, 2);

						if (int_locker_no > 0)
						{
							lkisform.setValidateFlag("Successfully Verified");
						}
						else
						{
							lkisform.setValidateFlag("Not Verified");
						}
					}

					httpServletRequest.setAttribute("date", date1);
					getLkAbbr(httpServletRequest, path, lkd);
					getLockersTypes(httpServletRequest, path, lkd);

					httpServletRequest.setAttribute("introtypes",moduleobject_introducer);
					httpServletRequest.setAttribute("pageId", path);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}
				else 
				{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",httpServletRequest, path);
					return actionMapping.findForward(ResultHelp.getError());

				}
				
			} 
			catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("" + e, httpServletRequest, path);
				return actionMapping.findForward(ResultHelp.getError());
			}
		}

		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerDataEntryMenuLink")) {
			try {

				LockerDataEntry lockerDataEntry = (LockerDataEntry) actionForm;

				if (MenuNameReader.containsKeyScreen(lockerDataEntry.getPageId()))
				{
					httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerDataEntry.getPageId()));
					 lockerDetailsObjec = lkd.getLockerDetails();
					httpServletRequest.setAttribute("lkdetails",lockerDetailsObjec);
					httpServletRequest.setAttribute("norows",null);
					getLockersTypes(httpServletRequest, path, lkd);
					return actionMapping.findForward(ResultHelp.getSuccess());
				}
				else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		else if (actionMapping.getPath().trim().equalsIgnoreCase("/LockerDataEntryLink")) 
		{
			try {
				System.out.println("inside dtaentry");
				LockerDataEntry lockerDataEntry = (LockerDataEntry) actionForm;

				if(MenuNameReader.containsKeyScreen(lockerDataEntry.getPageId())) 
				{ 
					lockerDetailsObjec = lkd.getLockerDetails();
					httpServletRequest.setAttribute("lkdetails",lockerDetailsObjec);
					int k=0;
					
					/*if (lockerDataEntry.getFlag().equalsIgnoreCase("createTable")) 
					{
						array_lockers = new LockerDetailsObject[Integer.parseInt(lockerDataEntry.getTotLockers().trim())];
						array_cabs = new LockerDetailsObject[Integer.parseInt(lockerDataEntry.getNoRows().trim())];

						String[] typeDetails_tosetVal_drawTab = new String[Integer.parseInt(lockerDataEntry.getNoRows())];
						String[] cabDetails_tosetVal_drawTab = new String[Integer.parseInt(lockerDataEntry.getNoRows())];
					
						Enumeration enumeration = httpServletRequest.getParameterNames();
						while (enumeration.hasMoreElements()) 
						{
							String s = (String) enumeration.nextElement();
							System.out.println("enumeration---->"+s);
						}
						
						while (enumeration.hasMoreElements()) 
						{
							
							String s = (String) enumeration.nextElement();
							System.out.println("enumeration---->"+s);
							
							if (s.startsWith("type") || s.startsWith("cab")) 
							{

								System.out.println(s + ">>"+ httpServletRequest.getParameter(s));

								if (s.startsWith("type")) 
								{

									StringTokenizer token = new StringTokenizer(s, "-");
									token.nextToken();

									System.out.println(token.nextToken()+ "<<<TYPE>>>"+ httpServletRequest.getParameter(s));
								}

								if (s.startsWith("cab")) 
								{
									System.out.println("inside cab");
									StringTokenizer token = new StringTokenizer(s, "-");
									System.out.println("before token-->"+token);
									token.nextToken();
									System.out.println("token.nextToken();--->"+token.nextToken());
									cabDetails_tosetVal_drawTab[Integer.parseInt(token.nextToken())] = httpServletRequest.getParameter(s);
									System.out.println("token.nextToken()");

									 System.out.println(token.nextToken()+"<<<CAB>>>"+httpServletRequest.getParameter(s));
								}

							}

						}

						
						  for(int i=0;i<Integer.parseInt(lockerDataEntry.getTotalRows());i++){ }
						 

						httpServletRequest.setAttribute("cabDetails_tosetVal_drawTab",cabDetails_tosetVal_drawTab);
						httpServletRequest.setAttribute("totalRows",lockerDataEntry.getNoRows());

						System.out.println("****************"+ lockerDataEntry.getNoRows());
						System.out.println("****************"+ cabDetails_tosetVal_drawTab.length);
						System.out.println("****************"+cabDetails_tosetVal_drawTab[0]);
						System.out.println("****************"+cabDetails_tosetVal_drawTab[1]);
						for (int i = 0; i < Integer.parseInt(lockerDataEntry.getNoRows()); i++)
						{

							for (int j = 0; j < Integer.parseInt(cabDetails_tosetVal_drawTab[i]); j++)
							{

								System.out.println("Printing....");

							}
						}
						}*/
					if(lockerDataEntry.getFlag().equalsIgnoreCase("totrows"))
						{
							if(lockerDataEntry.getNoRows()!=null)
							{
								String num=lockerDataEntry.getNoRows();
								httpServletRequest.setAttribute("norows",num);
							}
						}
					else if(lockerDataEntry.getFlag().equalsIgnoreCase("frmSubmit"))
					{
						int l=0;
						System.out.println("inside frm submit");
						String[] rowno=httpServletRequest.getParameterValues("rownum");
						String[] colno=httpServletRequest.getParameterValues("colnum");
						String[] keynum=httpServletRequest.getParameterValues("keynum");
						String[] lktyp=httpServletRequest.getParameterValues("lktype");
						String[] totcol=httpServletRequest.getParameterValues("columnnum");
						String[] cbox=httpServletRequest.getParameterValues("cbox");
						Stack<String> stack=new Stack<String>();
						for(int z=totcol.length-1;z>=0;z--)
						{
							stack.push(totcol[z]);
							System.out.println("ele of stack in tot --->"+totcol[z]);
						}
						System.out.println("keynum length-->"+keynum.length);
						System.out.println("rowno length-->"+rowno.length);
						for(int m=0;m<rowno.length;m++)
						{
							System.out.println("row num values--->"+rowno[rowno.length-1]);
							int a = Integer.parseInt(rowno[rowno.length-1]);
							l=(a-1);
							System.out.println("Value of LLLL-->"+l);
						}
						
						
						int a=0,j=0;
						if(cbox!=null)
						{ 
							System.out.println("length of cbox--->"+cbox.length);
							array_lockers=new LockerDetailsObject[cbox.length];
							array_cabs=new LockerDetailsObject[l+1];
							System.out.println("length of array cabs ==now-->"+array_cabs.length);
							
							for(int i=0;i<cbox.length;i++)
							{	
								System.out.println("Inside Cbox Loop");
								int x=Integer.parseInt(cbox[i].trim());
								array_lockers[i]=new LockerDetailsObject();
								array_lockers[i].setLockerType(lktyp[x]);
								for(int c=0;c<lockerDetailsObjec.length;c++)
								{
									if(lockerDetailsObjec[c].getLockerType().equals(lktyp[x]))
									{
										array_lockers[i].setLockerLength(lockerDetailsObjec[c].getLockerLength());
										array_lockers[i].setLockerDepth(lockerDetailsObjec[c].getLockerDepth());
										array_lockers[i].setLockerHeight(lockerDetailsObjec[c].getLockerHeight());
										array_lockers[i].setDescription(lockerDetailsObjec[c].getDescription());
									}
								}
								int rr = Integer.parseInt(rowno[x]);
								System.out.println("Value of rr-->"+(rr-1));
								array_lockers[i].setKeyNo(Integer.parseInt(keynum[x]));
								array_lockers[i].setRowNum(rr-1);
								array_lockers[i].setColNo(Integer.parseInt(colno[x]));
								array_lockers[i].uv.setUserId(uid);
								array_lockers[i].uv.setUserTml(tml);
								array_lockers[i].uv.setUserDate(ClearingDelegate.getSysDate());
								array_lockers[i].setTrnDate(ClearingDelegate.getSysDate());
								
									if(a==(rr-1))
									{
										//tocol=Integer.parseInt(totcol[z]);
										int col=Integer.parseInt(stack.pop());
										array_cabs[j]=new LockerDetailsObject();
										array_cabs[j].setDescription(lockerDataEntry.getCabDesciption());
										array_cabs[j].setMasterKey(lockerDataEntry.getMasterKey().toString().trim());
										System.out.println("inside a-->"+(Integer.parseInt(rowno[x])-1));
										array_cabs[j].setRowNo(Integer.parseInt(rowno[x])-1);
										array_cabs[j].setCols(col);
										array_cabs[j].uv.setUserId(uid);
										array_cabs[j].uv.setUserTml(tml);
										array_cabs[j].uv.setUserDate(ClearingDelegate.getSysDate());
										array_cabs[j].setTrnDate(ClearingDelegate.getSysDate());
										a++;
										j++;
									}
								
							}
							
								
							try
		                    {
								int int_cabin_no=0;
		                        if((int_cabin_no=lkd.storeLockers(array_cabs,array_lockers))!=-1)
		                        {
		                           
		                            lockerDataEntry.setValidateFlag("Cabinet No "+int_cabin_no);
		                           
		                            
		                        }
		                        else
		                        	lockerDataEntry.setValidateFlag("Please Enter Details Properly");
		                    }
		                    catch(Exception exception)
		                    {
		                        exception.printStackTrace();
		                    }
						
						}
						
					}	
						httpServletRequest.setAttribute("pageId", MenuNameReader.getScreenProperties(lockerDataEntry.getPageId()));
						getLockersTypes(httpServletRequest, path, lkd);
						return actionMapping.findForward(ResultHelp.getSuccess());
				
				}
				else
					return actionMapping.findForward(ResultHelp.getError());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		

		// Let this 2 lines as it is

		System.out.println("out of order");
		return actionMapping.findForward(layout);

	}

	private void getLockersTypes(HttpServletRequest httpServletRequest,String path, LockerDelegate lkd) 
	{
		try 
		{
			String[] str_locker_types = lkd.getLockersTypes();
			System.out.println("number of lockers are"+ str_locker_types.length);
			for (int i = 0; i < str_locker_types.length; i++) 
			{
				System.out.println("my types is" + str_locker_types[i]);
			}
			httpServletRequest.setAttribute("lktypes", str_locker_types);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void getTempLockersTypes(HttpServletRequest httpServletRequest,String path, LockerDelegate lkd) 
	{

		try 
		{
			String[] str_locker_types = lkd.getLockersTypes();
			System.out.println("number of lockers are"+ str_locker_types.length);
			for (int i = 0; i < str_locker_types.length; i++) 
			{
				System.out.println("my types is" + str_locker_types[i]);
			}
			int arr[] = lkd.getDistinctCabs(str_locker_types[0]);

			httpServletRequest.setAttribute("cabDetails", arr);
			httpServletRequest.setAttribute("lktypes", str_locker_types);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}

	public void getLkAbbr(HttpServletRequest httpServletRequest, String path,LockerDelegate lkdObj) throws Exception 
	{
		ModuleObject modobj[] = lkdObj.getMainModules("1009000");
		System.out.println("i came inside initLk");
		if (modobj != null) 
		{
			System.out.println("settin values");
			for (int i = 0; i < modobj.length; i++) {
				System.out.println(modobj[i].getModuleAbbrv() + "mod desc"+ modobj[i].getModuleDesc());
			}
			httpServletRequest.setAttribute("param", modobj);
		}
	}

	public void getLkParams(HttpServletRequest httpServletRequest, String path,LockerDelegate lkdObj, int ac_num, int type) throws Exception 
	{
		System.out.println("inside getLKparams");

		LockerMasterObject lockerMasterObject = lkdObj.getLockerMaster(ac_num,type);
		
		if (lockerMasterObject != null)
		{
			System.out.println("inside getLKparams wen obj in not null");
			httpServletRequest.setAttribute("lkparams", lockerMasterObject);
			System.out.println("Operated in db"+ lockerMasterObject.getOperBy());
			System.out.println("password" + lockerMasterObject.getLockerPW());
			System.out.println("lkNum" + lockerMasterObject.getLockerNo()+ "lktype" + lockerMasterObject.getLockerType()
					+ "intro name" + lockerMasterObject.getIntrName());
			System.out.println("lockerNumber"
					+ lockerMasterObject.getLockerNo() + "opNAME"
					+ lockerMasterObject.getName());
			httpServletRequest.setAttribute("lkparams", lockerMasterObject);
		}
	}

	private void setErrorPageElements(String exception, HttpServletRequest req,String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);

	}

	private void setDetails(String details, HttpServletRequest req,LockerDelegate lkd, LockerMasterObject lkmastobj, int signFlag,int IntacNum, String IntacType) throws Exception {
		
		if (details.equalsIgnoreCase("Personal")) 
		{

			String perPath = MenuNameReader.getScreenProperties("0001");
			String cid = String.valueOf(lkmastobj.getCid());

			CustomerMasterObject cmobject = lkd.getCustomer(Integer.parseInt(cid));
			//req.setAttribute("TabNum", "0");
			req.setAttribute("personalInfo", cmobject);
			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getPersonal());
			System.out.println("yes flag is set");
		}

		else if (details.trim().equalsIgnoreCase("IntroducerType")) 
		{
			System.out.println("intro typeeee,," + IntacType + "" + IntacNum);
			String perPath = MenuNameReader.getScreenProperties("0001");
			AccountObject accountobject = lkd.getIntroducerAcntDetails(IntacType, IntacNum);
			System.out.println("acntObject.getCid()==" + accountobject.getCid());
			System.out.println("acntObject.getCid()==" + accountobject);
			String cid = String.valueOf(accountobject.getCid());

			if (accountobject != null) 
			{
				CustomerMasterObject cmobject = lkd.getCustomer(Integer.parseInt(cid));
				req.setAttribute("personalInfo", cmobject);
			}
			else 
			{
				req.setAttribute("personalInfo", new CustomerMasterObject());
			}
			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
		}
		
		/*else if(details.trim().equalsIgnoreCase("SignatureIns")){
		  System.out.println("i am in sign ins"); String
		  perPath=MenuNameReader.getScreenProperties("0004");
		  req.setAttribute("flag",perPath); SignatureInstructionObject[]
		  signObject=lad.getSignature(IntacNum,IntacType);
		  req.setAttribute("SignInstActnPath","/FrontCounter/Signature");
		  req.setAttribute("panelName","Signature Instruction");
		  //req.setAttribute("SBOpeningForm", sbForm); 
		  if(signObject!=null){
		  req.setAttribute("signInfo",signObject); if(signFlag==1){
		  if(siForm!=null){
		  req.setAttribute("signInfo1",signObject[Integer.parseInt(siForm.getSignCombo().trim())]);
		  req.setAttribute("Signature",MenuNameReader.getScreenProperties("0005")); } } } }
		 */
	}

	private void setLKSurrTabbedPaneInitParams(HttpServletRequest req,String path, LockerDelegate lkd, LockerSurrenderForm lkSurrenderForm)throws Exception 
	{
		System.out.println(path);

		String pageActions[] = { "/LockerSurrenderLink?tabPaneHeading=Personal&txt_acType="
				+ lkSurrenderForm.getTxt_acType()
				+ "&txt_acNo="
				+ lkSurrenderForm.getTxt_acNo()
				+ "&txt_lockType="
				+ lkSurrenderForm.getTxt_lockType()
				+ "&txt_lockNo="
				+ lkSurrenderForm.getTxt_lockNo()
				+ "&txt_allotDate="
				+ lkSurrenderForm.getTxt_allotDate()
				+ "&txt_expiryDate="
				+ lkSurrenderForm.getTxt_expiryDate()
				+ "&txt_rentUpto="
				+ lkSurrenderForm.getTxt_rentUpto()
				+ "&txt_totRent="
				+ lkSurrenderForm.getTxt_totRent()
				+ "&txt_rentAmnt="
				+ lkSurrenderForm.getTxt_rentAmnt()
				+ "&select_opInstr="
				+ lkSurrenderForm.getSelect_opInstr()
				+ "&select_details="
				+ lkSurrenderForm.getSelect_details()
				+ "&submit="
				+ lkSurrenderForm.getSubmit()
				+ "/LockerSurrenderLink?tabPaneHeading=Introducer&txt_acType="
				+ lkSurrenderForm.getTxt_acType()
				+ "&txt_acNo="
				+ lkSurrenderForm.getTxt_acNo()
				+ "&txt_lockType="
				+ lkSurrenderForm.getTxt_lockType()
				+ "&txt_lockNo="
				+ lkSurrenderForm.getTxt_lockNo()
				+ "&txt_allotDate="
				+ lkSurrenderForm.getTxt_allotDate()
				+ "&txt_expiryDate="
				+ lkSurrenderForm.getTxt_expiryDate()
				+ "&txt_rentUpto="
				+ lkSurrenderForm.getTxt_rentUpto()
				+ "&txt_totRent="
				+ lkSurrenderForm.getTxt_totRent()
				+ "&txt_rentAmnt="
				+ lkSurrenderForm.getTxt_rentAmnt()
				+ "&select_opInstr="
				+ lkSurrenderForm.getSelect_opInstr()
				+ "&select_details="
				+ lkSurrenderForm.getSelect_details()
				+ "&submit="
				+ lkSurrenderForm.getSubmit() };

		String tabHeading[] = { "Personal", "Nominee", "JointHolders",
				"Signature Instruction", "Agent Details" };
		String pagePath[] = {
				MenuNameReader.getScreenProperties("0001").trim(),
				MenuNameReader.getScreenProperties("0002").trim(),
				MenuNameReader.getScreenProperties("0001").trim(),
				MenuNameReader.getScreenProperties("0004").trim(),
				MenuNameReader.getScreenProperties("0001").trim() };

		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "0001");

	}
}