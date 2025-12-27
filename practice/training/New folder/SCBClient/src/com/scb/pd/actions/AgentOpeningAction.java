
package com.scb.pd.actions;

import exceptions.ScrollNotFoundException;
import general.Validations;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.pygmyDeposit.AgentMasterObject;
import masterObject.pygmyDeposit.PygmyMasterObject;
import masterObject.pygmyDeposit.PygmyRateObject;
import masterObject.pygmyDeposit.PygmyTransactionObject;
import masterObject.pygmyDeposit.QuarterInterestObject;
import masterObject.share.ShareMasterObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.forms.SignatureInstructionForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.Date;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.designPatterns.ShareDelegate;
import com.scb.designPatterns.TDDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.pd.forms.AcknowledgementForm;
import com.scb.pd.forms.AgentChangeForm;
import com.scb.pd.forms.AgentClosingForm;
import com.scb.pd.forms.AgentCommissionForm;
import com.scb.pd.forms.AgentOpeningForm;
import com.scb.pd.forms.AgentRemittanceForm;
import com.scb.pd.forms.AgentUpdateForm;
import com.scb.pd.forms.DailyRemittanceForm;
import com.scb.pd.forms.InterestRegiForm;
import com.scb.pd.forms.LedgerPrintingForm;
import com.scb.pd.forms.MonthlyRemittanceForm;
import com.scb.pd.forms.NomineeDetailsForm;
import com.scb.pd.forms.OpenCloseForm;
import com.scb.pd.forms.PUpdateForm;
import com.scb.pd.forms.PassBookForm;
import com.scb.pd.forms.PygmyIntForm;
import com.scb.pd.forms.PygmyInterestCalForm;
import com.scb.pd.forms.PygmyOpeningForm;
import com.scb.pd.forms.PygmyWithdrawalForm;
import com.scb.pd.forms.RegisterPrintForm;
import com.scb.props.MenuNameReader;


   
       
public class AgentOpeningAction extends Action 
{

	private PygmyDelegate pgDelegate;
	private String path;
	int int_agent_cid = 0, jt_no_jt_hldr = 0;
	private ModuleObject[] array_moduleobject_pygmy;
	private HttpSession session; 
	PygmyRateObject pygInt[];
	final Logger logger = LogDetails.getInstance().getLoggerObject("Pygmy");
	private static byte byte_update=0;   
	private AgentMasterObject[] agent_nos=null; 
	int cid_val=0,before_cid=0,self_acno=0,before_jtaccno;
	 String self_acctype=null,self_name=null;
	 AccountObject accountobject=null;
	 private int pers_cid=0,pers_no_jt_hldr=0;
	 private boolean check_cid_minor=false,check_cid=false;
	 AdministratorDelegate admDelegate;
	 Map user_role;
	 FrontCounterDelegate fcDelegatenew;
	 String user,tml;

	public ActionForward execute(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res) throws Exception 
	{
		System.out.println("m inside action class");
		session = req.getSession(true);
			logger.info("inside execute");
			user=(String)session.getAttribute("UserName");
	    	tml=(String)session.getAttribute("UserTml") ;
	    	
			
			try{
	    		synchronized(req) {
					
				
	    		if(user!=null){
	    			System.out.println("m in if====>>"+user);
	    			admDelegate=new AdministratorDelegate();
	    			user_role=admDelegate.getUserLoginAccessRights(user,"PD");
	    			if(user_role!=null){
	    			req.setAttribute("user_role",user_role);
	    			System.out.println("pageid======>>>"+req.getParameter("pageid"));
	    			if(req.getParameter("pageid")!=null){
	    				System.out.println("req.getParameter--in FC ACtion===>"+req.getParameter("pageid"));
	    				req.setAttribute("accesspageId",req.getParameter("pageid").trim());
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
			
			if (map.getPath().equalsIgnoreCase("/Pygmy/AgentOpeningMenu")) {
			try {
				AgentOpeningForm agform = (AgentOpeningForm) form;
				String pageId= agform.getPageid().trim();
				req.setAttribute("pageNum",pageId );
				String value=agform.getValue();
				req.setAttribute("VALUE",value);
				agform.setDetails("SELECT");
				agform.setJointname("");
				agform.setSelfname("");
				agform.setSysdate(PygmyDelegate.getSysDate());
				
				if (MenuNameReader.containsKeyScreen(pageId)) 
				{
					path = MenuNameReader.getScreenProperties(pageId);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					agform.setValidation("");
					agform.setAgentnum("0");
					agform.setAgentname("");
					setAgentOpeningInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());
				} 
				else 
				{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Unable to process the reqest", req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} 
			catch (Exception e) 
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} 
	 	 else if(map.getPath().trim().equalsIgnoreCase("/Common/sign")){
	        	SignatureInstructionForm siForm=(SignatureInstructionForm)form;
	        	AgentOpeningForm agForm=siForm.getAgentForm();
	        	String path;
	            try{
	                req.setAttribute("pageNum",agForm.getPageid().trim());
	                if(MenuNameReader.containsKeyScreen(agForm.getPageid())){
	                    path=MenuNameReader.getScreenProperties(agForm.getPageid());
	                     System.out.println(path);
	                    
	                    pgDelegate =  PygmyDelegate.getPygmyDelegateInstance() ;
	                    
	                    if(!agForm.getAgentcode().trim().equals("SELECT")){
	                    	AgentMasterObject amObj=pgDelegate.getAccountMaster(agForm.getAgentcode(),Integer.parseInt(agForm.getAgentnum()),agForm.getDate());
	                        req.setAttribute("AccountDetails",amObj);
	                        System.out.println(agForm.getDetails());
	                        if(!siForm.getSignCombo().equalsIgnoreCase("SELECT"))
	                        	if(!agForm.getDetails().trim().equalsIgnoreCase("SELECT"))
	                        		setAgentDetails(agForm.getDetails(), req, pgDelegate, amObj, agForm,siForm,null,1);
	                        
	                        setAgentOpeningInitParams(req,path,pgDelegate);
	                        return map.findForward(ResultHelp.getSuccess());
	                    }
	                   else{
	                	   setAgentOpeningInitParams(req,path,pgDelegate);
	                    return map.findForward(ResultHelp.getSuccess());
	                   }
	                }
	                else{
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
	        }
	 	
	 	else if (map.getPath().equalsIgnoreCase("/Pygmy/AgentOpening")) {
	 		AgentOpeningForm agform = (AgentOpeningForm) form;
			String path;
			AccountObject selfObject=null;
			AccountObject  jointObject=null;
			AccountObject intrObject = null;
			
			
			try {
				req.setAttribute("pageNum", agform.getPageid().trim());

				System.out.println(agform.getValue()+"<<------->>"+agform.getForward()+"insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(agform.getPageid()));
				
				if (MenuNameReader.containsKeyScreen(agform.getPageid())) {
					path = MenuNameReader.getScreenProperties(agform.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					String value=agform.getValue();
					req.setAttribute("VALUE",value);
					agform.setSysdate(PygmyDelegate.getSysDate());
					
					if (!agform.getAgentcode().trim().equalsIgnoreCase("SELECT")) 
					{
						agform.setValidation("");
						System.out.println("Integer.parseInt(agform.getAgentnum())" + agform.getAgentnum());
						System.out.println("agform.getAgentcode()"+ agform.getAgentcode());
						System.out.println("agform.getDate()" + agform.getDate());
						AgentMasterObject amObj = pgDelegate.getAccountMaster(agform.getAgentcode().trim(), Integer.parseInt(agform.getAgentnum()),agform.getDate());
						System.out.println("acount=====>"+Integer.parseInt(agform.getAgentnum()));
						System.out.println("Agent Master Object ---------------" +amObj);
						int cid=amObj.getCid();
						System.out.println("cid=====>>>>"+cid);

						
						if (Integer.parseInt(agform.getAgentnum().trim()) != 0) {
							System.out.println("Are You Here123!Q!!!!!!!!!!!!!");
							System.out.println("Agent Code---------------"+agform.getAgentcode());
							System.out.println("Agent Number----------------"+Integer.parseInt(agform.getAgentnum()));
							System.out.println("Date-------------"+agform.getDate());
							AgentMasterObject amObj1 = pgDelegate.getAccountMaster(agform.getAgentcode(),Integer.parseInt(agform.getAgentnum()), agform.getDate());
							System.out.println("Are You Here!Q!!!!!!!!!!!!!");
							System.out.println("amobj1==============="+amObj1);
							agform.setValidation("");
							
						if(amObj1.getAgentNo()!=-1) {
							req.setAttribute("AgentDetails", amObj1);
							
							setAgentDetails(agform.getDetails(), req, pgDelegate, amObj1, agform,null,null,0);

							//req.setAttribute("personalInfo", pgDelegate.getCustomer(amObj.getCid()));

							String name = pgDelegate.getJointDetails(null, 0,amObj1.getPersonalAccType(), amObj1.getAccNo(), amObj1.getJtAccType(), amObj1.getJointAccNo(), 3);
							System.out.println("return name==" + name);
							System.out.println("NAME===" + name);
							if (name.length() > 50) {
								req.setAttribute("JName", name.substring(0, name.indexOf(',')));
							} else {
								System.out.println(name.indexOf(','));
								req.setAttribute("JName", name);
							}
						}
						
						else 
						{
							
							System.out.println("Agent not Found!!!!!!!!!!!!!!!!!!");
							req.setAttribute("msg","Agent Not Found");
						}
							//setTabbedPaneInitParams(req, path, pgDelegate,agform);
							//req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
						
						
							if (agform.getForward().equalsIgnoreCase("Delete")) {
								System.out.println("---------- M in delete stmt----------");
								if (agform.getAgentnum() != null) {
									int del = pgDelegate.AgentDelete(agform.getAgentcode(), Integer.parseInt(agform.getAgentnum()));
									if (del != 0) {
										req.setAttribute("msg","Deleted Successfully");
									}
								}
							}
							
							 if ( agform.getForward().equalsIgnoreCase("Verify") && agform.getValue().equalsIgnoreCase("2")){
								System.out.println("-----------VERIFYYYYYYYYYYYYYYYYYYYYY-----");
								AgentMasterObject amo=new AgentMasterObject();
								amo.setAgentType(agform.getAgentcode());
								amo.setAgentNo(Integer.parseInt(agform.getAgentnum()));
								amo.setCloseInd(0);
								amo.setVeTml(user);
								amo.setVeUser(tml);
								
								int agInfo = pgDelegate.AgentVerify(amo, 2,Date.getSysDate());
								System.out.println("agInfo======>>>>"+agInfo);
								if (agInfo != 0)
									req.setAttribute("msg","Verified Successfully--------");
								else
									req.setAttribute("msg","Account Already Varified!!");
							}

							setAgentOpeningInitParams(req, path, pgDelegate);
							return map.findForward(ResultHelp.getSuccess());
						}

						else if (Integer.parseInt(agform.getAgentnum().trim()) == 0) {
						    agform.setValidation("");
							String name1 = "New Agent";
							req.setAttribute("NewAgent", name1);
							//int selfAccNo=Integer.parseInt(agform.getSelfAcno());
							if (((agform.getSelfactypeno() != null || agform.getSelfactypeno().equalsIgnoreCase("null")) && !agform.getSelfAcno().equals("0"))&& agform.getJointAcno().equals("0")) {

								System.out.println("------$$$$$$$$-------");
								System.out.println("Date.getSysDate()"+Date.getSysDate());
								selfObject = pgDelegate.getSelfAcntDetails(null, agform.getSelfactypeno(),Integer.parseInt(agform.getSelfAcno()),Date.getSysDate());
								
								if (selfObject == null ) {
									req.setAttribute("msg","Self Account Not Found");
								}
								else {
									agform.setValidation("");
									req.setAttribute("SelfAccountDetails",selfObject);
									logger.info("Hi M in SELFDETAILS===="+ selfObject.getAccno());
									//req.setAttribute("CustomerDetails",pgDelegate.getCustomer(selfObject.getCid()));
									req.setAttribute("personalInfo",pgDelegate.getCustomer(selfObject.getCid()));
								}
								agform.setAgentname("New Agent");
							}

							else if (agform.getJointactypeno() != null&& !agform.getJointAcno().equals("0") && agform.getIntroducerAcno().equals("0")) {

								jointObject = pgDelegate.getSelfAcntDetails(null, agform.getJointactypeno(),Integer.parseInt(agform.getJointAcno()), Date.getSysDate());
								System.out.println("^^^^^^^^^^^^^^^^^^^^^^=="+ jointObject);
								if (jointObject==null) {
									System.out.println("m inside Joint Account");
									req.setAttribute("msg","Joint Account Not Found");
								}
								else if(jointObject!=null){
									agform.setValidation("");
									selfObject = pgDelegate.getSelfAcntDetails(null, agform.getSelfactypeno(),Integer.parseInt(agform.getSelfAcno()), Date.getSysDate());
									req.setAttribute("SelfAccountDetails",selfObject);
									req.setAttribute("JointAccountDetails",jointObject);
									req.setAttribute("CustomerDetails",pgDelegate.getCustomer(jointObject.getCid()));
								}
								if(jointObject!=null){
								String date = agform.getDate();
								req.setAttribute("Date", date);

								String name = pgDelegate.getJointDetails(agform.getAgentcode(), Integer.parseInt(agform.getAgentnum()), agform.getSelfactypeno().trim(), Integer.parseInt(agform.getSelfAcno()), agform.getJointactypeno().trim(), Integer.parseInt(agform.getJointAcno()), 3);
								System.out.println("return name==mmmmmmmmmmmmmmmmmmmmmm" + name);
								System.out.println("NAME===nnnnnnnnnnnnnnnnnnnnnnn" + name);
								if (name.length() > 50) {
									req.setAttribute("JName", name.substring(0,name.indexOf('.')));
								} else {
									//System.out.println(name.indexOf(','));
									System.out.println("name---------------000000000000000000"+name);
									req.setAttribute("JName", name);
								}
								}
								/*AccountObject acObj = pgDelegate.getSelfAcntDetails(null, agform.getJointactypeno(), Integer.parseInt(agform.getJointAcno()),Date.getSysDate());
								System.out.println("plz show d o/p------------"+acObj);
                    			setAgentDetails(agform.getDetails(), req, pgDelegate, null, agform,null,acObj,0);
                		*/

							} else if (agform.getIntroduceractypeno() != null&& !agform.getIntroducerAcno().equals("0")) {
								agform.setValidation("");
								jointObject = pgDelegate.getSelfAcntDetails(null, agform.getJointactypeno(),Integer.parseInt(agform.getJointAcno()), Date.getSysDate());
								int cid1=jointObject.getCid();
								System.out.println("cid1=====intro===>>>"+cid1);
								intrObject = pgDelegate.getIntroducerAcntDetails(agform.getIntroduceractypeno(),Integer.parseInt(agform.getIntroducerAcno()));
								
								//System.out.println("acntObject.getCid()========>>"+ intrObject.getCid());
								
								if (intrObject == null)
									req.setAttribute("msg","Introducer Account Not Found");
								else
								if(intrObject.getCid()==cid1)
								{
									req.setAttribute("msg","Introducer Cid same as Openig Customer Cid");
								}
								else if(intrObject.getAccStatus().equalsIgnoreCase("C"))
								{
									agform.setIntroducerAcno("0");
									req.setAttribute("msg","Given Introducer A/c is Closed");
								}
								else if(intrObject.getAccStatus().equalsIgnoreCase("I"))
								{
									agform.setIntroducerAcno("0");
									req.setAttribute("msg","Given Introducer A/c is InOperative");
								}
								else if((intrObject.getFreezeInd()!=null)?intrObject.getFreezeInd().equals("T"):false)
		                        {
									agform.setIntroducerAcno("0");
		                            req.setAttribute("msg","Given Introducer A/c is Freezed");
		                        }
								if (intrObject == null) {
									agform.setIntroducerAcno("0");
									req.setAttribute("msg","Introducer Account Not Found");
								}else{	
									agform.setValidation("");
									jointObject = pgDelegate.getSelfAcntDetails(null, agform.getJointactypeno(),Integer.parseInt(agform.getJointAcno()),Date.getSysDate());
									selfObject = pgDelegate.getSelfAcntDetails(null, agform.getSelfactypeno(),Integer.parseInt(agform.getSelfAcno()), Date.getSysDate());
									req.setAttribute("SelfAccountDetails",selfObject);
									req.setAttribute("JointAccountDetails",jointObject);
									req.setAttribute("IntrAccountDetails",intrObject);
									req.setAttribute("CustomerDetails",pgDelegate.getCustomer(intrObject.getCid()));
								}
								
								String name = pgDelegate.getJointDetails(agform.getAgentcode(), Integer.parseInt(agform.getAgentnum()), agform.getSelfactypeno(), Integer.parseInt(agform.getSelfAcno()), agform.getJointactypeno(), Integer.parseInt(agform.getJointAcno()), 3);
								System.out.println("return name==iiiiiiiiiiiiiii" + name);
								System.out.println("NAME===jjjjjjjjjjjjjjjjj" + name);
								if (name.length() > 50) {
									req.setAttribute("JName", name.substring(0,name.indexOf(',')));
								} else {
									System.out.println(name.indexOf(','));
									req.setAttribute("JName", name);
								}
				
								/*AccountObject acObj = pgDelegate.getSelfAcntDetails(null, agform.getIntroduceractypeno(), Integer.parseInt(agform.getIntroducerAcno()),Date.getSysDate());
                    			setAgentDetails(agform.getDetails(), req, pgDelegate, null, agform,null,acObj,0);*/
							}

							System.out.println("!!!!!!!!M IN ELSE PART!!!!!!!!");
							
							AccountObject acObj = pgDelegate.getSelfAcntDetails(null, agform.getSelfactypeno(), Integer.parseInt(agform.getSelfAcno()),Date.getSysDate());
							setAgentDetails(agform.getDetails(), req, pgDelegate, null, agform,null,selfObject,0);
														
							 AgentMasterObject amo = new AgentMasterObject();
							 System.out.println("-------->>>>"+agform.getForward());
							 System.out.println("-------->>>>"+agform.getValue());
							 
							 
							if ( agform.getForward().equals("Submit") && agform.getValue().equalsIgnoreCase("1")) {
								System.out.println("(((((%%%%%%%%%)))))))"+ agform.getForward());
								
								System.out.println("!!!!!!!!!agform.getAgentcode()!!!!!!!!!"+ agform.getAgentcode());
								amo.setAgentType(agform.getAgentcode());
								amo.setAgentNo(Integer.parseInt(agform.getAgentnum()));
								System.out.println("!!!!!!!!!agform.getCid())!!!!!!!!!"+ agform.getCid());
								if (agform.getCid() != null&& !agform.getCid().equals(""))
									amo.setCid(acObj.getCid());

								amo.setJointHldrs(jt_no_jt_hldr);
								amo.setAppointDate(agform.getDate());
								amo.setAccType(agform.getSelfactypeno());
								amo.setAccNo(Integer.parseInt(agform.getSelfAcno()));

								System.out.println("!!!!!!!!!agform.getDate()!!!!!!!!!"+ agform.getDate());

								amo.setJointAccType(agform.getJointactypeno());
								amo.setJointAccNo(Integer.parseInt(agform.getJointAcno()));

								amo.setIntrAccType(agform.getIntroduceractypeno());
								amo.setIntrAccNo(Integer.parseInt(agform.getIntroducerAcno()));

								amo.setCloseInd(9);
								amo.setDeTml(user);
								amo.setDeUser(tml);
								
								
								
					 			int agInfo = pgDelegate.AgentInsert(amo, 1,agform.getDate());
								
								if (agInfo != 0){
									req.setAttribute("msg","Agent Number Generated is=="+String.valueOf(agInfo));
								req.setAttribute("disabled","disabled");}
								else
									agform.setValidation("");

							}
							
							
							

							setAgentOpeningInitParams(req, path, pgDelegate);
							return map.findForward(ResultHelp.getSuccess());
						}
						 if (amObj.getAccNo() == -1) {
							 req.setAttribute("msg","account not found");
							System.out.println("acount=====>");
						}

						else {
							setAgentOpeningInitParams(req, path, pgDelegate);
							return map.findForward(ResultHelp.getSuccess());
						}
					} else {
						
						System.out.println("select Account Type....................");
						agform.setValidation("select Account Type!!!!!!!!!");
						req.setAttribute("path", MenuNameReader.getScreenProperties(agform.getPageid()));
						
					}
				}
				else {
					
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file!!",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}
	 	else if(map.getPath().equalsIgnoreCase("/Pygmy/NomineeMenu"))
		{	
			try{
				System.out.println("Nominee");
			    
				NomineeDetailsForm nomform=(NomineeDetailsForm)form;
				System.out.println("The page iid is "+nomform.getNompageid());
				req.setAttribute("pagenum", nomform.getNompageid());
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(nomform.getNompageid()))
				{
					path=MenuNameReader.getScreenProperties(nomform.getNompageid());
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setNomineeInitParams(req, path, pgDelegate);
					req.setAttribute("pageId",path);
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getSuccess());
				}
			}
			catch(Exception e)
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}

	 	else if(map.getPath().equalsIgnoreCase("/Pygmy/Nominee"))
		{	
	 		NomineeDetailsForm nomform=(NomineeDetailsForm)form;
			try{
				req.setAttribute("AgNum",nomform.getHidval());
			    
				
				System.out.println("The page id is "+8023);
				req.setAttribute("pagenum", 8023);
				System.out.println("CHECK AGAIN");
				
				if(MenuNameReader.containsKeyScreen(String.valueOf(8023)))
				{
					path=MenuNameReader.getScreenProperties(String.valueOf(8023));
					System.out.println("path============================"+path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setNomineeInitParams(req, path, pgDelegate);
					req.setAttribute("pageId",path);
					System.out.println("The value is------>>>>>>>>>>>"+nomform.getHas_ac());
					//PygmyMasterObject pmo=pgDelegate.getAccMaster(Integer.parseInt(nomform.getAcno()), nomform.getActype());
					
					if(nomform.getHas_ac().equalsIgnoreCase("on")){
						req.setAttribute("vis", "visible");
						System.out.println("Inside has Account");
						System.out.println("CID IS"+nomform.getCidis().length());
						if(nomform.getCidis().length()!=0){
							//if(Integer.parseInt(nomform.getCidis().trim())>0){
								//nomform.setAcno(nomform.getAcno());
								
						
							System.out.println("-----------CID IS NOT NULL");
							System.out.println("The cid is "+nomform.getCidis().trim());
							CustomerMasterObject cust_obj=pgDelegate.getCustomer(Integer.parseInt(nomform.getCidis().trim()));
							System.out.println("======>>>>>>>>======="+cust_obj);
							System.out.println("THe address of the customer is "+cust_obj.getAddress());
							nomform.setNomname(cust_obj.getName());
							nomform.setDob(cust_obj.getDOB());
							nomform.setGender(cust_obj.getSex());
							nomform.setAddress(cust_obj.getAddress().toString());
							
							req.setAttribute("custDetail",cust_obj);
							//}
							
						}
						System.out.println("cid length is 0");
						
					}
					
					if(nomform.getNomforward().equalsIgnoreCase("submit")){

						NomineeObject[] nom=new NomineeObject[1];
						nom[0]=new NomineeObject();
						nom[0].setRegNo(0);
						System.out.println("in submittttt");
						nom[0].setAccNo(Integer.parseInt(nomform.getAcno()));
						nom[0].setAccType(nomform.getActype());
						nom[0].setCustomerId(Integer.parseInt(nomform.getCidis()));
					 	nom[0].setNomineeName(nomform.getNomname());
						nom[0].setNomineeDOB(nomform.getDob());
						System.out.println("----1-----"+nomform.getDob());
						nom[0].setNomineeAddress(nomform.getAddress());
						System.out.println("----2-----"+nomform.getAddress());
						nom[0].setNomineeRelation(nomform.getRel_ship());
						System.out.println("----3-----"+nomform.getRel_ship());
						nom[0].setPercentage(Double.parseDouble(nomform.getPercentage()));
						System.out.println("----4-----"+nomform.getPercentage());
						/*nom[0].setFromDate(nomform.getIssuedate());
						System.out.println("----5-----"+nomform.getIssuedate());*/
					//	int nomi=pgDelegate.storeNominee(nom, nomform.getActype(), Integer.parseInt(nomform.getAcno()),Date.getSysDate());
						
					}
					
					
					
					return map.findForward(ResultHelp.getSuccess());
				}
				else
				{
					path=MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
					return map.findForward(ResultHelp.getSuccess());
				}
			}
			catch(Exception e)
			{
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}

	 	else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyOpeningMenu")) {
			PygmyOpeningForm pgform = (PygmyOpeningForm) form;
			System.out.println("hi i am here" + map.getPath());

			try {

				req.setAttribute("pageNum", pgform.getPageid().trim());
				req.setAttribute("Index", new String("0"));
				System.out.println("===" + pgform.getPageid());
				String value=pgform.getValue();
				req.setAttribute("VALUE",value);
				pgform.setAcno("0");
				pgform.setSysdate(PygmyDelegate.getSysDate());

				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(pgform.getPageid()));
				if (MenuNameReader.containsKeyScreen(pgform.getPageid())) {
					path = MenuNameReader.getScreenProperties(pgform.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					pgform.setValidations("");
					
					setPygmyOpeningInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getError());
			}

		}

		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyOpening")) {

			PygmyOpeningForm pgform = (PygmyOpeningForm) form;
			System.out.println("#############=" + pgform.getPageid().trim());
			System.out.println("from top ***********" + pgform.getIntagno());
			AccountObject accountObject=null;
			try {
				req.setAttribute("pageNum", pgform.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(pgform.getPageid()));
				if (MenuNameReader.containsKeyScreen(pgform.getPageid())) {
					path = MenuNameReader.getScreenProperties(pgform.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					
					String value=pgform.getValue();
					req.setAttribute("VALUE",value);
					pgform.setSysdate(PygmyDelegate.getSysDate());
					

					if (!pgform.getPgactypeno().trim().equals("SELECT")) 
					{
						System.out.println("pgform.getPgactypeno()=="+ pgform.getPgactypeno());
						System.out.println("pgform.getPgno()=="+ pgform.getPgno());
						System.out.println("pgform.getCustid()=="+ pgform.getCustid());
						
						PygmyMasterObject pmObj = pgDelegate.getAccMaster(Integer.parseInt(pgform.getPgno()), pgform.getPgactypeno());
						System.out.println("customer id"+ pmObj.getCid());
						System.out.println(pgform.getPygdetails());
						System.out.println("pmObj==" + pmObj.getAccNo());
						NomineeObject[] nom=null;
						
						/*if (pmObj.getAccNo() == -1) {
							pgform.setValidations("account not found");
							System.out.println("acount=====>");
						}*/
						 
						 /*if(pgform.getForward().equalsIgnoreCase("PygmyAccNo") && pgform.getValue().equalsIgnoreCase("2"))
						 {
							 System.out.println("m inside varify=======>>>");
							 if(pmObj.getAccStatus().equalsIgnoreCase("O"))
								{
									req.setAttribute("msg","Account Already Verified");
								}
						 }*/
						 
						if(pgform.getForward().equalsIgnoreCase("CustId"))
						{
							int custid=pgDelegate.checkAgCid(pgform.getCustid().toString());
							if(custid==-1)
							{
								req.setAttribute("msg","Customer ID Not found");
							}
						}else if(pgform.getForward().equalsIgnoreCase("Cidis")){
							System.out.println("-----------CID IS NOT NULL");
							System.out.println("The cid is "+pgform.getCidis().trim());
							CustomerMasterObject cust_obj=pgDelegate.getCustomer(Integer.parseInt(pgform.getCidis().trim()));
							System.out.println("======>>>>>>>>======="+cust_obj);
							System.out.println("THe address of the customer is "+cust_obj.getAddress());
							pgform.setNomname(cust_obj.getName());
							pgform.setDob(cust_obj.getDOB());
							pgform.setGender(cust_obj.getSex());
							HashMap hashMap=cust_obj.getAddress();
							Collection collection=hashMap.values();
							Iterator itr=collection.iterator();
							Address address=null;
							while(itr.hasNext()){
								address=(Address)itr.next();
							}if(address!=null)
							pgform.setAddress(address.getAddress());
						}
						else if (pgform.getPgno().trim().length() != 0 && Integer.parseInt(pgform.getPgno())!=0) 
						{
							pgform.setValidations("");
							req.setAttribute("PygmyDetails", pmObj);
							System.out.println("Lets ckeck");
							if(pgform.getIntagno().trim().length()!=0){
							accountObject=pgDelegate.getIntroducerAcntDetails(pgform.getInagenttypeno(),Integer.parseInt(pgform.getIntagno()));
							}
							if(accountObject!=null){
							setPygmyDetails(pgform.getPygdetails(),req, pgDelegate, pmObj, accountObject, pgform,null,null, 0);
							System.out.println("After setPygmyDetails");
							}
							
							
							 if (pmObj.getAccNo() == -1) {
									req.setAttribute("msg","Pygmy Account not found");
									pgform.setPgno("0");
									pgform.setPayno("0");
									System.out.println("pygmy acount=====>");
								}
							 else if(pmObj.getAccStatus().equalsIgnoreCase("C"))
							 {
								 req.setAttribute("msg","Account Already Closed");
							 }
							 else if(pmObj.getAccStatus().equalsIgnoreCase("O"))
								{
									req.setAttribute("msg","Account Already Verified");
								}else if(pgform.getPygdetails().equalsIgnoreCase("Nominee"))
								   {
									req.setAttribute("enable", "enable");
								   }

							if (pgform.getForward().equalsIgnoreCase("Delete")) {
								System.out.println("---------- M in delete stmt----------");
								if (pgform.getPgno() != null) {
									if(pgform.getPgno().trim().length()!=0){
									int del = pgDelegate.pygmyAccountDeletion(pgform.getPgactypeno(), Integer.parseInt(pgform.getPgno()));
									if (del != 0){
										req.setAttribute("msg","Deleted Successfully!!!!!!!!!");
									}
									else{
										req.setAttribute("msg", "Error in Delete!!");
									}
								}
								}
							}
							
							if ( pgform.getForward().equalsIgnoreCase("Verify") && pgform.getValue().equalsIgnoreCase("2")){
								System.out.println("-----------VERIFYYYYYYYYYYYYYYYYYYYYY-----");
								PygmyMasterObject pmo=new PygmyMasterObject();
								if(pmObj.getAccStatus().equalsIgnoreCase("O"))
								{
									req.setAttribute("msg","Account Already Verified");
								}
								pmo.setAccStatus("O");
								pmo.setAccType(pgform.getPgactypeno());
								pmo.setAccNo(Integer.parseInt(pgform.getPgno()));
								
								int pgInfo = pgDelegate.pygmyAccountVerify(pmo, user, tml, Date.getSysDate(),pgform.getOpdate(), 1);
								
								if (pgInfo != 0)
									req.setAttribute("msg","Verified Successfully");
								else
									req.setAttribute("msg","Verification Failed");
								
								
								
							}
							
							setPygmyOpeningInitParams(req, path, pgDelegate);
							return map.findForward(ResultHelp.getSuccess());
						}
						
						
						if (Integer.parseInt(pgform.getPgno()) == 0) 
						{

							System.out.println("Intagno---->"+pgform.getIntagno());
							
							if(pgform.getIntagno().trim().length()!=0)
							{
							
								if((Integer.parseInt(pgform.getIntagno())!= 0)) 
								{
									String agm=null;
									agm = pgDelegate.getAgentName(pgform.getInagenttypeno(), Integer.parseInt(pgform.getIntagno()));
									System.out.println("name returned to action"+ agm);
								
									if(agm.trim().length()==0)
									{
									
										req.setAttribute("msg","Agent account not found");
									}	
									else
									{
										pgform.setValidations("");
										req.setAttribute("IntroAgentDetails", agm);
									
									}
									
								}
								AgentMasterObject agentmaster=pgDelegate.getAgentDetails(pgform.getInagenttypeno(), Integer.parseInt(pgform.getIntagno()),pgform.getOpdate());
								accountObject=pgDelegate.getIntroducerAcntDetails(pgform.getInagenttypeno(), Integer.parseInt(pgform.getIntagno()));
								System.out.println("pmObj======>>>>"+pmObj.getAccNo());
								setPygmyDetails(pgform.getPygdetails(),req, pgDelegate, pmObj, accountObject, pgform,null,agentmaster, 0);
								
							}                                     
							//pgDelegate.getAgentDetails(agttype, agtno, date)
							//AgentMasterObject agmaster=pgDelegate.getAgentDetails(pmObj.getAgentType(),Integer.parseInt(pmObj.getAgentNo()), pmObj.getAccOpenDate());
							//System.out.println("pmObj.getCid()"+ pmObj.getCid());
							//System.out.println("pgform.getPygdetails(" + pgform.getPygdetails());
							

							if (pgform.getPaymode().equalsIgnoreCase("Transfer")) 
							{
								if (pgform.getPayactypeno() != null && pgform.getPayno()!=null) {
									
									if (pgform.getPayno().trim().length()!=0 && pgform.getPayactypeno().trim().length()!=0) {
										
										AccountObject acObj = pgDelegate.getPayAccount(null, pgform.getPayactypeno(),Integer.parseInt(pgform.getPayno()),Date.getSysDate());
										req.setAttribute("AccountDetail",acObj);
										if(acObj==null)
										req.setAttribute("msg","Account not found");
											
									}
								}
							}
							if(pgform.getPygdetails().equalsIgnoreCase("Nominee"))
							   {
								req.setAttribute("enable", "enable");
							   }
							if(pgform.getPygdetails()!="Nominee"||pgform.getPygdetails()!="Personal"||pgform.getPygdetails()!="Agent Details"){
								req.setAttribute("disable", "disable");
							}
							
							//int pnum = 0;
							if (pgform.getForward().equalsIgnoreCase("Submit") && pgform.getValue().equalsIgnoreCase("1")) {
								System.out.println("--------------M In Pygmy insert mode--------------");
								PygmyMasterObject pmo = new PygmyMasterObject();

								pmo.setAccType(pgform.getPgactypeno());
								pmo.setAccNo(0);
								pmo.setCid(Integer.parseInt(pgform.getCustid()));
								pmo.setAgentType(pgform.getInagenttypeno());
								pmo.setAgentNo(pgform.getIntagno());
								pmo.setAgentName(pgform.getAgname());
								pmo.setAccOpenDate(pgform.getOpdate());

								if (pgform.getPaymode().equalsIgnoreCase("Cash")) {
									pmo.setPayMode(pgform.getPaymode());
									pmo.setPayAccType("NULL");
									pmo.setPayAccNo(0);
								} else if (pgform.getPaymode().equalsIgnoreCase("Transfer")) {
									pmo.setPayMode(pgform.getPaymode());
									pmo.setPayAccType(pgform.getPayactypeno());
									pmo.setPayAccNo(Integer.parseInt(pgform.getPayno()));
								}
								System.out.println("pgform.getName()=====>>"+pgform.getName());

								pmo.setPay_acc_name(pgform.getName());
								pmo.setAccStatus("V");
								pmo.setAddrType(1);
								pmo.setJointHolders(0);
								
								/*NomineeObject[] nom1=pgDelegate.getNominee(pmo.getNomineeNo());
								for(int i=0;i<nom1.length;i++){
								pmo.setNomineeNo(nom1[i].getRegNo());
								}*/
								
								pmo.setLastTrnSeq(0);
								pmo.setLastIntrDate("10/04/2008");
								pmo.setLastWDDate("10/04/2008");
								pmo.setLastWDNo(0);
								pmo.setLnAvailed("T");
								pmo.setLnAccType("1008001");
								pmo.setLnAccNo(0);
								pmo.setWDAmt(2000);
								pmo.setOpenDt(pgform.getOpdate());
								pmo.setAccCloseDate("10/04/2008");
								pmo.setLdgrPrtDate("10/04/2008");
								pmo.setRef_ind(0);
								
 
								
								if(pgform.getPygdetails().equalsIgnoreCase("Nominee")){
									if(pgform.getCidis().trim().length()!=0){
									nom=new NomineeObject[1];
									nom[0]=new NomineeObject();
									nom[0].setRegNo(0);
									System.out.println("in submittttt");
									nom[0].setCustomerId(Integer.parseInt(pgform.getCidis()));
								 	nom[0].setNomineeName(pgform.getNomname());
									nom[0].setNomineeDOB(pgform.getDob());
									System.out.println("----1-----"+pgform.getDob());
									nom[0].setNomineeAddress(pgform.getAddress());
									System.out.println("----2-----"+pgform.getAddress());
									nom[0].setNomineeRelation(pgform.getRel_ship());
									System.out.println("----3-----"+pgform.getRel_ship());
									nom[0].setPercentage(Double.parseDouble(pgform.getPercentage()));
									System.out.println("----4-----"+pgform.getPercentage());
								}else{
									nom=new NomineeObject[1];
									nom[0]=new NomineeObject();
									nom[0].setRegNo(0);
									System.out.println("in submittttt");
									nom[0].setCustomerId(0);
								 	nom[0].setNomineeName(pgform.getNomname());
									nom[0].setNomineeDOB(pgform.getDob());
									System.out.println("----1-----"+pgform.getDob());
									nom[0].setNomineeAddress(pgform.getAddress());
									System.out.println("----2-----"+pgform.getAddress());
									nom[0].setNomineeRelation(pgform.getRel_ship());
									System.out.println("----3-----"+pgform.getRel_ship());
									nom[0].setPercentage(Double.parseDouble(pgform.getPercentage()));
									System.out.println("----4-----"+pgform.getPercentage());
								}
								}
								else{
									req.setAttribute("msg","Enter the Nominee Details!");
								}
								
								if(nom!=null){
									pmo.setNomineeDetails(nom);
								}else{
									req.setAttribute("msg","Enter the Nominee Details!");
								}
								int pnum = pgDelegate.pygmyAccountOpen(pmo, user,tml, Date.getSysDate(),pgform.getOpdate(), 0);
								System.out.println("pnum is------------->" +pnum);
								
								if (pnum != 0)
								{
									req.setAttribute("msg","Pygmy Account No is "+ String.valueOf(pnum));
									req.setAttribute("disabled","disabled");
								System.out.println("Pygmy Account No is==="+pnum);
								}
								else
									pgform.setValidations("");
							
								
								/*int newAcNum=pnum;
								System.out.println("the number is------"+newAcNum);*/
								
								/*if(newAcNum!=0)
								   {
									   pgform.setPgno("Pygmy Number generated is"+String.valueOf(newAcNum));
							           
									   req.setAttribute("pagenum",8023);
									   req.setAttribute("ac_type", pgform.getPgactypeno());
									   req.setAttribute("AgNum",String.valueOf(newAcNum));
									   
									   if(MenuNameReader.containsKeyScreen(String.valueOf(8023)))
										{
											path=MenuNameReader.getScreenProperties(String.valueOf(8023));
											//pgDelegate=new PygmyDelegate();
											req.setAttribute("pageId",path);
											setAgentOpeningInitParams(req, path, pgDelegate);
										}
									  // return map.findForward(ResultHelp.getSuccess());
								   }*/
								
							}
							
							
							
							
						}
						
						
						else if (pmObj.getAccNo() == -1) {
							pgform.setValidations("Pygmy Account No not found");
							pgform.setPayno("0");
							System.out.println("acount=====>");
						}

						setPygmyOpeningInitParams(req, path, pgDelegate);
						return map.findForward(ResultHelp.getSuccess());

					} else {
						path = MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
						return map.findForward(ResultHelp.getError());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}

			return map.findForward(ResultHelp.getSuccess());
		} 
			
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentClosingMenu")) {
			AgentClosingForm acform = (AgentClosingForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + acform.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(acform.getPageid()));
				if (MenuNameReader.containsKeyScreen(acform.getPageid())) 
				{
					path = MenuNameReader.getScreenProperties(acform.getPageid());
					System.out.println("insideExecutre path" + path);
					
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					AgentMasterObject[] agMaster = pgDelegate.getAgentNos("1013001", 0);
					req.setAttribute("AgentDetails", agMaster);
					String value=acform.getValue();
					System.out.println("value=================???????????????"+value);
					req.setAttribute("AGENTCLOSE",value);
					
                    setAgentClosingInitParams(req, path, pgDelegate);
					req.setAttribute("pageNum", acform.getPageid().trim());
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
			
			
		}else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentClosingVerMenu")) {
				AgentClosingForm acform = (AgentClosingForm) form;
				System.out.println("hi i am here" + map.getPath());
				try {
					System.out.println("===" + acform.getPageid());
					System.out.println("--------------------------------------------------------");
					System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(acform.getPageid()));
					if (MenuNameReader.containsKeyScreen(acform.getPageid())) 
					{
						path = MenuNameReader.getScreenProperties(acform.getPageid());
						System.out.println("insideExecutre path" + path);
						
						pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
						//AgentMasterObject[] agMaster = pgDelegate.getAgentNos("1013001", 0);
						//req.setAttribute("AgentDetails", agMaster);
						
						AgentMasterObject[] agnum=null;
			        	agnum=pgDelegate.getAgentCloseToVerify("1013001");
			        	System.out.println("agnum123===========================>>>>>>"+agnum);
			        	req.setAttribute("AgentDetails", agnum);
						String value=acform.getValue();
						System.out.println("value=================???????????????"+value);
						req.setAttribute("AGENTCLOSE",value);
						
	                    setAgentClosingInitParams(req, path, pgDelegate);
						req.setAttribute("pageNum", acform.getPageid().trim());
						return map.findForward(ResultHelp.getSuccess());
					} else {
						path = MenuNameReader.getScreenProperties("0000");
						setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
						return map.findForward(ResultHelp.getSuccess());
					}
				} catch (Exception e) {
					e.printStackTrace();
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Unable to process the reqest", req, path);
					return map.findForward(ResultHelp.getSuccess());
				}


		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentClosing")) {
			AgentClosingForm acform = (AgentClosingForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				AgentMasterObject[] agMaster1 = pgDelegate.getAgentNos("1013001", 0);
				req.setAttribute("AgentDetails", agMaster1);
				System.out.println("===" + acform.getPageid());
				acform.setValidating("");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(acform.getPageid()));
				if (MenuNameReader.containsKeyScreen(acform.getPageid())) {
					path = MenuNameReader.getScreenProperties(acform.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					acform.setValidating("");
					String value=acform.getValue();
					System.out.println("value=================???????????????"+value);
					req.setAttribute("AGENTCLOSE",value);
					
					setAgentClosingInitParams(req, path, pgDelegate);
					System.out.println("After setting agent closing parameters");

					if (!acform.getAgType().equalsIgnoreCase("SELECT")) {
						acform.setValidating("");
						AgentMasterObject[] agMaster = pgDelegate.getAgentNos(acform.getAgType(), 0);
						PygmyMasterObject[] pgMaster = pgDelegate.getAccountsUnderAgent(acform.getAgType(),Integer.parseInt(acform.getAgNo()));
							if(acform.getForward().equalsIgnoreCase("AgentNo"))
							{
								if (pgMaster != null && pgMaster.length > 0){
									req.setAttribute("msg","Agent has accounts,cannot be closed");
									System.out.println("Agent has accounts,cannot be closed====>");
								}
								else{
									req.setAttribute("msg","No Accounts under this Agent,Can be closed..");
								}
								req.setAttribute("AgClose", agMaster);
								req.setAttribute("AccUnderAgent", pgMaster);
								setAgentClosingDetails(acform.getAgNo(), req,pgDelegate, agMaster, acform);
							}
							
						
							
						
						int int_agentnum=Integer.parseInt(acform.getAgNo());
						if(acform.getForward().equalsIgnoreCase("Verify")&& acform.getValue().equalsIgnoreCase("2"))
						{
							System.out.println("!!!!!!!!!!!!!!!!!!!! VERIFY !!!!!!!!");
							int executed=0;
							executed=pgDelegate.agentUpdate(acform.getAgType(), int_agentnum,Date.getSysDate(),1);
							System.out.println("executed==========>>>"+executed);
							if(executed == -1)
		                    {
		                        req.setAttribute("msg","Already Verified");
		                    }
		                    else if(executed==1)
		                    {
		                    	req.setAttribute("msg","Agent Close Verified");
		                    }
							
						}
								
						else if(acform.getForward().equalsIgnoreCase("Submit")&& acform.getValue().equalsIgnoreCase("1")){
							System.out.println("!!!!!!!!!!!!!!!!!!!! SUBMIT %%%%%%%%%!!!!!!!!AgentClose11111111111111");
							int exec=0;
							setAgentClosingDetails(acform.getAgNo(), req,pgDelegate, agMaster, acform);
							PygmyMasterObject[] pgMaster1 = pgDelegate.getAccountsUnderAgent(acform.getAgType(),Integer.parseInt(acform.getAgNo()));
							exec=pgDelegate.agentUpdate(acform.getAgType(),int_agentnum,Date.getSysDate(),0);
							if(pgMaster1==null){
								if(exec>0){
									req.setAttribute("msg","Agent Closed");
								}else{
									acform.setValidating("");
								}
							}
								else{
									req.setAttribute("msg","Agent has accounts,cannot be closed");
								}
							
						}
						//}
						

					}else
					{
						req.setAttribute("msg", "Select Account Type!");
					}

					req.setAttribute("pageNum", acform.getPageid().trim());
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}

		} 
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentChangeMenu")) {
			 AgentChangeForm acgForm=(AgentChangeForm)form;
			  System.out.println("hi i am here"+map.getPath());
			  pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			  try{
				  System.out.println("==="+acgForm.getPageid());
				  System.out.println("insideExecutre111111111111"+MenuNameReader.containsKeyScreen(acgForm.getPageid()));
				  System.out.println("((((((((((((((((()))))))))))))))))))------");
				  acgForm.setValidate("");
			  if(MenuNameReader.containsKeyScreen(acgForm.getPageid())){
				  path=MenuNameReader.getScreenProperties(acgForm.getPageid());
				  System.out.println("insideExecutre path"+path);
				  setAgentChangeInitParams(req,path,pgDelegate);
				  
				  
				  AgentMasterObject[] agMaster = pgDelegate.getAgentNos("1013001", 0);
			  		System.out.println("<<<<<<------->>>>>"+agMaster);
			  		req.setAttribute("AgNum", agMaster);
			  		
			  		
			  	
				  req.setAttribute("pageNum",acgForm.getPageid().trim());
			  
				  System.out.println("++++++++++++++++++++++++-----------"); 
				  return map.findForward(ResultHelp.getSuccess());
			 } else{
					  path=MenuNameReader.getScreenProperties("0000");
					  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path); return
					  map.findForward(ResultHelp.getSuccess());
			 		} 
			  }catch(Exception e){
				  		path=MenuNameReader.getScreenProperties("0000"); e.printStackTrace();
				  		
				  		return map.findForward(ResultHelp.getSuccess());
				  		
			  	}
		}
			
			
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentChangeVerMenu")) {
			 AgentChangeForm acgForm=(AgentChangeForm)form;
			  System.out.println("hi i am here"+map.getPath());
			  pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			  try{
				  System.out.println("==="+acgForm.getPageid());
				  System.out.println("insideExecutre111111111111"+MenuNameReader.containsKeyScreen(acgForm.getPageid()));
				  System.out.println("((((((((((((((((()))))))))))))))))))------");
			  
			  if(MenuNameReader.containsKeyScreen(acgForm.getPageid())){
				  path=MenuNameReader.getScreenProperties(acgForm.getPageid());
				  System.out.println("insideExecutre path "+path);
				  setAgentChangeInitParams(req,path,pgDelegate);
				  acgForm.setValidate("");
			  		
			  		AgentMasterObject[] agMaster = pgDelegate.getAgentNos("1013001", 0);
			  		System.out.println("<<<<<<------->>>>>"+agMaster);
			  		req.setAttribute("AgNum", agMaster);
			  		
				  req.setAttribute("pageNum",acgForm.getPageid().trim());
			  
				  System.out.println("++++++++++++++++++++++++-----------"); 
				  return map.findForward(ResultHelp.getSuccess());
			 } else{
					  path=MenuNameReader.getScreenProperties("0000");
					  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path); return
					  map.findForward(ResultHelp.getSuccess());
			 		} 
			  }catch(Exception e){
				  		path=MenuNameReader.getScreenProperties("0000"); e.printStackTrace();
				  		setErrorPageElements("Unable to process the reqest", req, path);
				  		return map.findForward(ResultHelp.getSuccess());
				  		
			  	}
		}
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/AgChange")){
			 AgentChangeForm acgForm=(AgentChangeForm)form;
			 System.out.println("#############="+acgForm.getPageid().trim());
			 
			  try{ 
				 
				  acgForm.setValidate("");
				  req.setAttribute("pageNum",acgForm.getPageid().trim());
				  System.out.println("--------------------------------------------------------");
				  System.out.println("insideExecutre"+MenuNameReader.containsKeyScreen(acgForm.getPageid()));
			  if(MenuNameReader.containsKeyScreen(acgForm.getPageid())){
				  	path=MenuNameReader.getScreenProperties(acgForm.getPageid());
				  	System.out.println(path);
				  	pgDelegate=PygmyDelegate.getPygmyDelegateInstance() ;
				  	System.out.println(pgDelegate);
				  	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********");
				  	setAgentChangeInitParams(req,path,pgDelegate);
				  	
				  	
				  	if(!acgForm.getAgType().equalsIgnoreCase("SELECT")){
				  		System.out.println("m inside select block-------------->");
				  		AgentMasterObject[] agMaster = pgDelegate.getAgentNos(acgForm.getAgType(), 0);
				  		System.out.println("<<<<<<------->>>>>"+agMaster);
				  		req.setAttribute("AgNum", agMaster);
				  		
				  	if(!acgForm.getAgNum().equalsIgnoreCase("SELECT")&&(Integer.parseInt(acgForm.getAgNum())!=0))
				  	{
				  		PygmyMasterObject[] pm = pgDelegate.getAccountsUnderAgent(acgForm.getAgType(),Integer.parseInt(acgForm.getAgNum()));
						if (pm != null && pm.length > 0){
							
							req.setAttribute("PygmyNum", pm);
					  		
					  		System.out.println("======Pygmy Numbers=====1111111223344"+pm);
						}
						
						System.out.println("pm================>>>>>>>>>>>"+pm);
						if(pm==null)
						{
							System.out.println("No Accounts Under Agent.");
							req.setAttribute("msg","No Accounts Under Agent!!!!!!");
						}
						
						req.setAttribute("agno", acgForm.getAgNum());
						req.setAttribute("alt_agno",acgForm.getAlt_agNo());
						
						
						
						
						
						
						
				  		/*AgentMasterObject agMas = pgDelegate.getAgentNumber(acgForm.getAgType(),Integer.parseInt(acgForm.getAgNo()));
				  		System.out.println("<<<<<<------->>>>>"+agMas);
				  		req.setAttribute("AgName", agMas);
				  		
				  		String name = pgDelegate.getAgentNames(acgForm.getAgType(), Integer.parseInt(acgForm.getAgNo()));
						req.setAttribute("Name", name);
						System.out.println("---------=======" + name);*/
				  		
				  		
				  		
				  	}
				  	}
				  	
				  	/*if(!acgForm.getAlt_agType().equalsIgnoreCase("SELECT")){
				  		
				  		AgentMasterObject[] agMaster = pgDelegate.getAgentNos(acgForm.getAlt_agType(), 0);
				  		System.out.println("<<<<<<------->>>>>"+agMaster);
				  		//req.setAttribute("AgNum", agMaster);
				  		
				  	if( Integer.parseInt(acgForm.getAlt_agNo())!=0){
				  		PygmyMasterObject[] pm = pgDelegate.getAccountsUnderAgent(acgForm.getAlt_agType(),Integer.parseInt(acgForm.getAlt_agNo()));
						if (pm != null && pm.length > 0){
							req.setAttribute("PygmyNum", pm);
					  		
					  		System.out.println("======Pygmy Numbers====="+pm);
						}
				  		AgentMasterObject agMas = pgDelegate.getAgentNumber(acgForm.getAgType(),Integer.parseInt(acgForm.getAgNo()));
				  		System.out.println("<<<<<<------->>>>>"+agMas);
				  		req.setAttribute("AgName", agMas);
				  		
				  		String name = pgDelegate.getAgentNames(acgForm.getAgType(), Integer.parseInt(acgForm.getAgNo()));
						req.setAttribute("Name", name);
						System.out.println("---------=======" + name);
				  		
				  		
				  		
				  	}
				  	}
				  	*/
				  	if(acgForm.getForward().equalsIgnoreCase("view"))
				  	{
				  		System.out.println("m inside View of AgentChange........");
				  		String frm_agtype=acgForm.getAgType();
				  		String to_agtype=acgForm.getAlt_agType();
				  		System.out.println("frm_agtype=====>>>>"+frm_agtype);
				  		int frm_agno=Integer.parseInt(acgForm.getAgNum());
				  		int to_agno=Integer.parseInt(acgForm.getAlt_agNo());
					  
				  		System.out.println("frm_agno=========>"+frm_agno);
				  		PygmyMasterObject[] pgMaster = pgDelegate.getUnverifiedAgtChange(frm_agtype,frm_agno,to_agtype,to_agno);
				  		System.out.println("<<<<<<------->>>>>ppppppppp"+pgMaster);
				  		req.setAttribute("PygmyNumber", pgMaster);
				  		
				  		if(pgMaster==null)
				  		{
				  			System.out.println("No records to verify!!!!!!!!!!!!!!");
				  			req.setAttribute("msg","No records to verify!!!!!!!!!!!!!!");
				  		}
				  		
				  		if(frm_agno==to_agno)
						{
							System.out.println("From Agent and To Agent cannot be same");
							req.setAttribute("msg","From Agent and To Agent cannot be same!!!!!!!!!!!!!!!");
						}
				  	}
				  	
				  	if(acgForm.getForward().equalsIgnoreCase("verify"))
				  	{
				  		PygmyMasterObject[] pyg_obj=null;
				  		System.out.println("=========M inside Verify of AgentChange===========");
				  		
				  		String frm_agtype=acgForm.getAgType();
				  		String to_agtype=acgForm.getAlt_agType();
				  		System.out.println("frm_agtype=====>>>>"+frm_agtype);
				  		int frm_agno=Integer.parseInt(acgForm.getAgNum());
				  		int to_agno=Integer.parseInt(acgForm.getAlt_agNo());
						int result=pgDelegate.VerifyAgentChange( pyg_obj, frm_agtype, frm_agno, to_agtype,to_agno, user,tml, Date.getSysDate());
						System.out.println("result==========>>>>>>>>>>>>>>>11111111111"+result);
						if(result==1)
						{
							System.out.println("Successfully verified!!!!!!!!");
							req.setAttribute("msg","Successfully verified!!!!!!!!");
						}
						else if(result==0)
						{
							req.setAttribute("msg","Verification failed");
						}
						
						
						
					}
				  	System.out.println("m before submit block----------------->");
				  	if(acgForm.getForward().equalsIgnoreCase("Submit"))
				  	{
				  		System.out.println("======= M In Submit of Agent Change =====");
				  		
				  		if(!acgForm.getAlt_agNo().equalsIgnoreCase("SELECT")&&(Integer.parseInt(acgForm.getAlt_agNo())!=0))
				  		{
				  			
				  			if(!acgForm.getAlt_agType().equalsIgnoreCase("SELECT"))	
				  			{
				  				
				  				
				  				
				  				String frm_agtype=acgForm.getAgType();
				  				String to_agtype=acgForm.getAlt_agType();
				  		
				  				int frm_agno=Integer.parseInt(acgForm.getAgNum());
				  				int to_agno=Integer.parseInt(acgForm.getAlt_agNo());
				  		
				  		if(!(acgForm.getAgNum().equalsIgnoreCase(acgForm.getAlt_agNo())))
				  		{
						
				  		//String selected=acgForm.getSelectedones();
				  		//System.out.println("selectedonce===========>"+selected);
				  		//PygmyMasterObject[] pm=pgDelegate.getAccountsUnderAgent(acgForm.getAgType(), Integer.parseInt(acgForm.getAgNum()));
				  		//System.out.println("selectedones00000000================>"+acgForm.getSelectedones());
				  		PygmyMasterObject[] pmobj=null;
				  		
				  		String[] values=req.getParameterValues("selectedone");
				  		
				  		if(values!=null)
				  		{	
				  		     pmobj=new PygmyMasterObject[values.length];
				  		
				  		
				  		
				  		
				  		
				  		for(int i=0;i<values.length;i++)
				  		{
				  			System.out.println("values-------------->"+values[i]);
				  			//pmobj[0].setSelectedone(values[i]);
				  			
				  		}
				  		System.out.println("values.length===============>"+values.length);
				  		
				  		//System.out.println("Sumanth the acvount number is===>"+acgForm.getXyz());
				  		if(values.length!=0)
				  		{
				  			System.out.println("m inside if=========="+values.length);
				  			for(int l=0;l<values.length;l++)
				  			{
				  				System.out.println("m inside loop1=========="+l);
				  				
				  				
				  					pmobj[l]=new PygmyMasterObject();
				  					pmobj[l].setSelectedone(values[l]);	
				  					StringTokenizer st=new StringTokenizer(values[l]," ");
				  					String[] acctype=new String[st.countTokens()];
				  					String[] accno=new String[st.countTokens()];
				  					String[] Cname=new String[st.countTokens()];
				  					
				  					 acctype[l]=st.nextToken();
				  					 accno[l]=st.nextToken();
				  					 Cname[l]=st.nextToken();
				  					
				  					System.out.println("type====>"+acctype[l]);
				  					System.out.println("accountnumber----->"+accno[l]);
				  					System.out.println("Cname-----> "+Cname[l]);
				  					
				  					pmobj[l].setAccType(acctype[l]);
						  			pmobj[l].setAccNo(Integer.parseInt(accno[l]));
						  			System.out.println("values-------1122334455"+values[l]);
				  					}
				  		}
				  		
				  		/*System.out.println("before if condition====from_agno1111111111111vvvvv"+frm_agno);
						if(frm_agno==to_agno)
						{
							System.out.println("From Agent and To Agent cannot be same11");
							acgForm.setValidate("From Agent and To Agent cannot be same!!!!!!!!");
						}*/
				  		
				  		
				  		
				  	
				  		
				  		
				  		/*for(int j=0;j<pmobj.length;j++)
				  		{
				  			PygmyMasterObject pmo[]=new PygmyMasterObject[j];
				  			pmo[j].setAccType(acgForm.getAlt_agType());
				  			pmo[j].setAccNo(Integer.parseInt(acgForm.getAlt_agNo()));
				  		}*/
				  		System.out.println("--- M In Agent Change Submit Chk -----");
				  		int value=pgDelegate.storeAgentChange(pmobj, frm_agtype, frm_agno, to_agtype, to_agno, user, tml,Date.getSysDate(), 1);
				  		System.out.println("m after storeAgentChange----------------->vvvvvv"+value);
				  		
				  		
				  		
				  		
				  		
				  		if(value==1)
				  		{
				  			System.out.println("Sucessfully Transferred!!!!!!!!");
				  			req.setAttribute("msg","Sucessfully Transferred!!!!!!!!");
				  		}
				  	  }
				  	   else
				  	  {
				  		 req.setAttribute("msg","Select Values From TextBox");
				  	  }	
				  		
				  	}
				  	else
				  	{
				  		req.setAttribute("msg","From Agent and To Agent cannot be same!!");
							
				  	}	
				  	
				  	}
				  	else
				  	{
				  		req.setAttribute("msg","Select Proper Agent Type");
				  	}
				  }
				 else
				 {
					 req.setAttribute("msg","Select Proper Agent Number");
				 }	
				  		
				  	return map.findForward(ResultHelp.getSuccess()); 
			  }
			  }
			  else{ 
				      path=MenuNameReader.getScreenProperties("0000");
				      setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path); return
				      map.findForward(ResultHelp.getSuccess()); 
					} 
			  }catch(Exception e){
					  e.printStackTrace(); path=MenuNameReader.getScreenProperties("0000");
					  setErrorPageElements("Unable to process the reqest", req, path); return
					  map.findForward(ResultHelp.getSuccess()); 
					  }
				  }
				
			
		
		
		
		
		
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyWithdrawalMenu")) {
			PygmyWithdrawalForm pwform1 = (PygmyWithdrawalForm) form;
			pwform1.setTyop("SELECT");
			pwform1.setWithdrawal_amt("0");
			pwform1.setPygdetails("SELECT");
			
			
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + pwform1.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(pwform1.getPageid()));
				if (MenuNameReader.containsKeyScreen(pwform1.getPageid())) {
					path = MenuNameReader.getScreenProperties(pwform1.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setPygmyWithdrawalInitParams(req, path, pgDelegate);

					req.setAttribute("LoanNo","N/A");
        			req.setAttribute("LoanBal", 0.0);
        			req.setAttribute("LoanInt", 0.0);
					
					req.setAttribute("pageNum", pwform1.getPageid().trim());
					return map.findForward(ResultHelp.getSuccess());
				} 
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
		  	} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
 
		}
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyWithdrawal")){
        	
			PygmyWithdrawalForm pwform=(PygmyWithdrawalForm)form;
         	System.out.println("@@@@@##########@@@@@@@@@@######="+pwform.getPageid().trim());
        	
        	double lntrn=0.0,lnintr=0.0;
        	try{
				req.setAttribute("pageNum",pwform.getPageid().trim());
				
                System.out.println("insideExecutre"+MenuNameReader.containsKeyScreen(pwform.getPageid()));
                if(MenuNameReader.containsKeyScreen(pwform.getPageid())){
                	pwform.setValidate("");
                    path=MenuNameReader.getScreenProperties(pwform.getPageid());
                    System.out.println(path);
                    pgDelegate= PygmyDelegate.getPygmyDelegateInstance() ;
                    req.setAttribute("PayAcType",pgDelegate.getPymentTypes());
                    array_moduleobject_pygmy = pgDelegate.getPygmyMasterComboElements(2);
                    System.out.println(pgDelegate);
                    
                    
                    String value=pwform.getValue();
                    req.setAttribute("VALUE", value);
                    
                    PygmyMasterObject pmObj=pgDelegate.getPyAccMaster(Integer.parseInt(pwform.getPgno()),pwform.getPgactypeno());
                    System.out.println("cid===========>>>"+pmObj.getCid());
                    System.out.println("Pygmy Master  Details-----> With Drawal"+ pmObj.getWDAmt());
                    System.out.println("pmObj.getAccNo()=================>>"+pmObj.getAccNo());
                    System.out.println("status=============>>>>"+pmObj.getAccStatus());
                    System.out.println("pmObj.getLastWDNo()======>>>"+pmObj.getLastWDNo());
                  
                     if(pmObj.getAccNo() == -1)
                    {
                    	req.setAttribute("msg","Pygmy Account Not Found");
                    }
                  
                   
                    else if(!pwform.getPgactypeno().trim().equals("SELECT"))
                    {
                    	PygmyMasterObject verifypmObj=null;
                    	
                    	if(pmObj.getAccNo()!=0)
                    	{
                    		pwform.setValidate("");
                    		System.out.println("pgform.getPgactypeno()=="+pwform.getPgactypeno());
                    		System.out.println("pwObj.getPrevBalance()===>>>>"+pmObj.getPrevBalance());
                    		System.out.println("status=============>>>>"+pmObj.getAccStatus());
                    		System.out.println("no of day========>>>>"+pmObj.getAccOpenDate());
                    		System.out.println("withdrawal Account no======>>>>"+pmObj.getPayAccNo());
                    		System.out.println("withdrawal Account type======>>>>"+pmObj.getPayAccType());
                    		req.setAttribute("PygmyWithDetails",pmObj);
                    		System.out.println("pmObj=="+pmObj);
                    		System.out.println("pmObj.getLnAvailed()=====>>"+pmObj.getLnAvailed());
                    		if(pmObj.getLnAvailed().equalsIgnoreCase("T"))
                    		{
                    			req.setAttribute("msg","Partial Withdrawal Not allowed for this account,Loan Availed! Less than MinPeriod1");
                    		}
                    		
                    		boolean chk_pygtrn=pgDelegate.checkPygTran(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()),0);
                    		System.out.println("chk_pygtrn=============>>>>"+chk_pygtrn);
                    		if(chk_pygtrn == false)
                    		{
                    			req.setAttribute("msg","No Transaction Found");
                    		}
                    		String str="trn_narr";
                        	String payname=null;
                    		StringTokenizer st=new StringTokenizer(str,"-");
                        	
                        	
                    		
                        	 
                        PygmyTransactionObject[] pTranObject=pgDelegate.getPyWithClosureDetails(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
                        if(pmObj!=null){
                        	System.out.println("pmObj.getAccStatus()====>>>>"+pmObj.getAccStatus());
                        	if(pmObj.getAccStatus().equalsIgnoreCase("C")){
                        		req.setAttribute("msg","Account already closed");
                        		System.out.println("m inside closure11111111111====");
                        		
                        	}
                        	if(pmObj.getAccStatus().equalsIgnoreCase("O")){
                        		double balance=pgDelegate.getClosingBal(pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()));
                        		ModuleObject[] modObjP=pgDelegate.getPgWithComboElements(1);
                        		if(pTranObject!=null && pTranObject.length>0){
                        			for(int i=0;i<pTranObject.length;i++){
                        				pTranObject[i].getRefNo();
                        				 if(pTranObject[i].getTranType().equalsIgnoreCase("W")){
                        					 req.setAttribute("With_Amt",pTranObject[i].getTranAmt());
                        					 
                        					 double max_withdrawal=pgDelegate.getClosingBal(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
                        					 req.setAttribute("Max-With",max_withdrawal);
                        					 System.out.println("-----MaxWithdrawalAmt-----"+max_withdrawal);
                        				}else if(pTranObject[i].getTranType().equalsIgnoreCase("E")){
                        					System.out.println("It is coming into TranType=E--------------");
                        					req.setAttribute("With_Amt",pTranObject[i].getTranAmt());
                        					double max_withdrawable = Math.round(balance*(modObjP[0].getMaxAmount()/100));
                        					req.setAttribute("Max-With",max_withdrawable);
                        					if(pTranObject[i].getTranMode().equalsIgnoreCase("C")){
                        						pwform.setPaymode("Cash");
                        					}else if(pTranObject[i].getTranMode().equalsIgnoreCase("T")){
                        						pwform.setPaymode("Transfer");
                        						//if(pTranObject[i].getTranNarration().equalsIgnoreCase(anotherString))
                        						if(pTranObject[i].getTranMode().equalsIgnoreCase("T") && (!pTranObject[i].getTranNarration().equalsIgnoreCase("Penalty") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Intr") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Closure-PayOrder") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Pay Order")))
                        		                {
                        		                	StringTokenizer stk=new StringTokenizer(pTranObject[i].getTranNarration(),"-");
                        		                	
                        		                	String payac_type=st.nextToken();
                        		                	String payAc_type=st.nextToken();
                        		                  	int payac_no=Integer.parseInt(payAc_type);
                        		                  	System.out.println("------->>>>>>>-----"+payac_no);
                        		                  	AccountObject acObj = pgDelegate.getPayAccount(null, payac_type,payac_no,Date.getSysDate());
                			 						req.setAttribute("AccountDetail",acObj);
                									System.out.println("$$$$$$$$$$$$$$$$$$$$$$"+acObj.getAccname());
                									
                									String Name=acObj.getAccname();
                									req.setAttribute("SelfName", Name);
                									
                        		                }
                        						
                        						
                        						
                        					}
                        					
                        					if(pTranObject[i].getTranNarration().equalsIgnoreCase("Intr"))
                                            {
                    							req.setAttribute("Interest",pTranObject[i].getTranAmt());
			                                    //txt_interest.setText(String.valueOf(pTranObject[i].getTranAmt()));
                                            }
			                                else if(pTranObject[i].getTranNarration().equalsIgnoreCase("Loan"))
                                            {
								                //txt_loan_balance.setText(String.valueOf(pTranObject[i].getTranAmt()));
							                }
			                                else if(pTranObject[i].getTranNarration().equalsIgnoreCase("Penalty"))
                                            {
			                                    //file_logger.info("Setting Value of Penalty Amnt...");
			                                	req.setAttribute("FINE",pTranObject[i].getTranAmt());
			                                    //txt_fines.setText(String.valueOf(pTranObject[i].getTranAmt()));
                                            }else if(pTranObject[i].getCloseBal() == 0)
                                            {
                                            pwform.setTyop("Closure");
                                            try
                                            {
		                                        PygmyRateObject probj=pgDelegate.getIntDetails();
		                                        // "Interest Rate: "+probj.getIntRate());
		                                        pwform.setIntrate(String.valueOf(probj.getIntRate()));
		                                        
                                            }
                                            catch(Exception re)
                                            {
                                                re.printStackTrace();
                                            }
                                            
                                            /*
                                            txt_max_withdrawable.setText(String.valueOf(pt[i].getTranAmt()));
                                            txt_withdrawal_amt.setText(String.valueOf(pt[i].getTranAmt()));*/
                                            }
                        				}
			                        }
                        		}
                        		
                        			if(pmObj.getLnAvailed().equalsIgnoreCase("T")){
                        				System.out.println(pmObj.getLnAccType()+"<------>"+pmObj.getLnAccNo());
                        				lntrn=pgDelegate.getCurrentDayClosingBalance(pmObj.getLnAccType(),pmObj.getLnAccNo(), pwform.getCurdate());
                        				req.setAttribute("LoanBal", lntrn);
                        				System.out.println("----LoanBallllllllllll---------------"+lntrn);
                        			
                        				lnintr=pgDelegate.getCurrentIntPay(pmObj.getLnAccType(),pmObj.getLnAccNo(), pwform.getCurdate());
                        				req.setAttribute("LoanInt",lnintr);
                        				System.out.println("----LoanInttttttttttttttttt-----"+lnintr);
                        			}
                        			else{
                        				req.setAttribute("LoanNo","N/A");
                        				req.setAttribute("LoanBal", 0.0);
                        				req.setAttribute("LoanInt", 0.0);
                        			}
                        		
                        	}
                        	if(pmObj.getAccStatus().equalsIgnoreCase("V")){
                        		req.setAttribute("VALUE", "2");
                        		req.setAttribute("Interest",0.0);
                        		double balance=pgDelegate.getClosingBal(pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()));
                        		ModuleObject[] modObjP=pgDelegate.getPgWithComboElements(1);
                        		if(pmObj.getLnAvailed().equalsIgnoreCase("T")){
                    				System.out.println(pmObj.getLnAccType()+"<------>"+pmObj.getLnAccNo());
                    				lntrn=pgDelegate.getCurrentDayClosingBalance(pmObj.getLnAccType(),pmObj.getLnAccNo(), pwform.getCurdate());
                    				req.setAttribute("LoanBal", lntrn);
                    				System.out.println("----LoanBallllllllllll---------------"+lntrn);
                    			
                    				lnintr=pgDelegate.getCurrentIntPay(pmObj.getLnAccType(),pmObj.getLnAccNo(), pwform.getCurdate());
                    				req.setAttribute("LoanInt",lnintr);
                    				pwform.setLoan_int(String.valueOf(lnintr));
                    				System.out.println("----LoanInttttttttttttttttt-----"+lnintr);
                    			}
                    			else{
                    				req.setAttribute("LoanNo","N/A");
                    				req.setAttribute("LoanBal", 0.0);
                    				req.setAttribute("LoanInt", 0.0);
                    			}
                        		verifypmObj=pgDelegate.getPyAccMaster(Integer.parseInt(pwform.getPgno()),pwform.getPgactypeno());
                        		PygmyTransactionObject[] verifypTranObject=pgDelegate.getPyWithClosureDetails(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
                        		double veWithAmt=0.0;
                        		double max_withdrawable=0.0;
                        		for(int i=0;i<pTranObject.length;i++){
                        		if(pTranObject[i].getTranType().equalsIgnoreCase("E")){
                					System.out.println("It is coming into TranType=E--------------");
                					req.setAttribute("With_Amt",pTranObject[i].getTranAmt());
                					max_withdrawable = Math.round(balance*(modObjP[0].getMaxAmount()/100));
                					req.setAttribute("Max-With",max_withdrawable);
                					if(pTranObject[i].getTranMode().equalsIgnoreCase("C")){
                						pwform.setPaymode("Cash");
                					}else if(pTranObject[i].getTranMode().equalsIgnoreCase("T")){
                						pwform.setPaymode("Transfer");
                						//if(pTranObject[i].getTranNarration().equalsIgnoreCase(anotherString))
                						if(pTranObject[i].getTranMode().equalsIgnoreCase("T") && (!pTranObject[i].getTranNarration().equalsIgnoreCase("Penalty") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Intr") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Closure-PayOrder") && !pTranObject[i].getTranNarration().equalsIgnoreCase("Pay Order")))
                		                {
                		                	StringTokenizer stk=new StringTokenizer(pTranObject[i].getTranNarration(),"-");
                		                	System.out.println("String Tokenizer Length--------");
                		                	String payac_type=stk.nextToken();
                		                	String payAc_type=stk.nextToken();
                		                	pwform.setPayactypeno(payac_type);
                		                	pwform.setPayno(payAc_type);
                		                  	int payac_no=Integer.parseInt(payAc_type);
                		                  	System.out.println("------->>>>>>>-----"+payac_no);
                		                  	AccountObject acObj = pgDelegate.getPayAccount(null, payac_type,payac_no,Date.getSysDate());
        			 						req.setAttribute("AccountDetail",acObj);
        									System.out.println("$$$$$$$$$$$$$$$$$$$$$$"+acObj.getAccname());
        									
        									String Name=acObj.getAccname();
        									req.setAttribute("SelfName", Name);
        									
                		                }
                						
                						
                						
                					}
                					
                					if(pTranObject[i].getTranNarration().equalsIgnoreCase("Intr"))
                                    {
            							req.setAttribute("Interest",pTranObject[i].getTranAmt());
	                                    //txt_interest.setText(String.valueOf(pTranObject[i].getTranAmt()));
                                    }
	                                else if(pTranObject[i].getTranNarration().equalsIgnoreCase("Loan"))
                                    {
						                //txt_loan_balance.setText(String.valueOf(pTranObject[i].getTranAmt()));
					                }
	                                else if(pTranObject[i].getTranNarration().equalsIgnoreCase("Penalty"))
                                    {
	                                    //file_logger.info("Setting Value of Penalty Amnt...");
	                                	req.setAttribute("FINE",pTranObject[i].getTranAmt());
	                                    //txt_fines.setText(String.valueOf(pTranObject[i].getTranAmt()));
                                    }else if(pTranObject[i].getCloseBal() == 0)
                                    {
                                    pwform.setTyop("Closure");
                                    try
                                    {
                                        PygmyRateObject probj=pgDelegate.getIntDetails();
                                        // "Interest Rate: "+probj.getIntRate());
                                        pwform.setIntrate(String.valueOf(probj.getIntRate()));
                                        
                                    }
                                    catch(Exception re)
                                    {
                                        re.printStackTrace();
                                    }
                                    
                                    veWithAmt=pTranObject[i].getTranAmt();
                                    System.out.println("========Withdrawal Amount======="+veWithAmt);
                                    pwform.setWithdrawal_amt(String.valueOf(veWithAmt));
                                    pwform.setMax_amt(String.valueOf(veWithAmt));
                                    req.setAttribute("With_Amt",veWithAmt);
                					max_withdrawable = Math.round(balance*(modObjP[0].getMaxAmount()/100));
                					req.setAttribute("Max-With",veWithAmt);
                                    /*
                                    txt_max_withdrawable.setText(String.valueOf(pt[i].getTranAmt()));
                                    txt_withdrawal_amt.setText(String.valueOf(pt[i].getTranAmt()));*/
                                    }
                        		}
                				}
                        		req.setAttribute("With_Amt",veWithAmt);
            					max_withdrawable = Math.round(balance*(modObjP[0].getMaxAmount()/100));
            					req.setAttribute("Max-With",veWithAmt);
                        	}
                       	}
                        //req.setAttribute("TabNum", "0");
						//req.setAttribute("Index", "0");
						
							setPygmyWithDetails(pwform.getPygdetails(),req, pgDelegate, pmObj, pwform,null, 0);
							
							if(pwform.getPygdetails().equalsIgnoreCase("Nominee"))
							   {
								req.setAttribute("enable", "enable");
								PygmyDelegate pydell=PygmyDelegate.getPygmyDelegateInstance();
								PygmyMasterObject pmObj1=pydell.getPygmyDetails(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
								if(pmObj1.getNomineeNo()==0){
									req.setAttribute("msg","No nominee details");	
								}
								else if(pmObj1.getNomineeNo()>=0){
									NomineeObject[] nom2=pydell.getNominee(pmObj1.getNomineeNo());
									if(nom2.length!=0){
										pwform.setCidis(String.valueOf(nom2[0].getCustomerId()));
										pwform.setNomname(nom2[0].getNomineeName());
										pwform.setDob(nom2[0].getNomineeDOB());
										pwform.setGender(String.valueOf(nom2[0].getSex()));
										pwform.setAddress(nom2[0].getNomineeAddress());
										pwform.setRel_ship(nom2[0].getNomineeRelation());
										pwform.setPercentage(String.valueOf(nom2[0].getPercentage()));
									}
								}
							   }
							//setPygWithTabbedPaneInitParams(req, path, pgDelegate,pwform);
							//req.setAttribute("flag", MenuNameReader.getScreenProperties("3003"));
							System.out.println(req.getAttribute("flag"));
                    }
                    	
                    }else{
                    	req.setAttribute("msg","Select Account Type");
                    }
                    
                    
					
                    
                     if(pwform.getTyop().equalsIgnoreCase("Closure"))
                     {
                    	 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    	if(Integer.parseInt(pwform.getPgno())!=0){
                            PygmyTransactionObject[] pTranObject=pgDelegate.getPyWithClosureDetails(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
	                        req.setAttribute("PygmyTranDetails", pTranObject);
	                       
	                        if(pTranObject==null||pTranObject.length==0){
                    	 		double bal=0.0,fine_amount=0;
                    	 	
                    	 		bal=Double.parseDouble(pwform.getPrincipal());
                    	 		req.setAttribute("BAL", bal);
                    	 		//PygmyMasterObject pmObj=pgDelegate.getPyAccMaster(Integer.parseInt(pwform.getPgno()),pwform.getPgactypeno());
                    	 		int noof_days = Validations.dayCompare(pmObj.getAccOpenDate(),Date.getSysDate());
                    	 		
                    	 		int no_of_months=0;
                    	 		no_of_months=pgDelegate.pygGetNoOfMonths(pmObj.getAccOpenDate(),Date.getSysDate());
                    	 		
                    	 		PygmyRateObject prObj=pgDelegate.getIntDetails(no_of_months,Date.getSysDate());
                    	 		req.setAttribute("PygmyRate", prObj);
                    	 		
                    	 		System.out.println("---INTRATE----"+prObj.getIntRate());
                            	if(prObj!=null && prObj.getIntRate()==0){
                            		ModuleObject[] mod_array=null;
                            		
                            		fine_amount=pgDelegate.fineAmt(pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()));
                            		req.setAttribute("msg", "Fine Towards Prematured Closure"+fine_amount+"");
                            		req.setAttribute("FINE",fine_amount);
                                	System.out.println("------ fine_amount -----"+fine_amount);	
                                	//System.out.println("mod_array.getNoOfTrnsPerMonth()=========>>"+mod_array[0].getNoOfTrnsPerMonth());
                            	}
                            	else{
                            		
                                    double interest=pgDelegate.calculateInterest(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()),Date.getSysDate());
                                    
                            		req.setAttribute("Interest",interest);
                            		System.out.println("------ Interest -----"+interest);
                            	
                            	}
                            	
                            	double interest=pgDelegate.calculateInterest(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()),Date.getSysDate());
                            	
                            	double max_with=pmObj.getPrevBalance()+interest-fine_amount-lntrn+lnintr;
                            	req.setAttribute("MaxWith", max_with);
                            	req.setAttribute("WithAmt", max_with);
                            	System.out.println("---------- MAX AAAAMTTT-------"+max_with);


                            		
                            		
	                        }
                    	}
                    	
                     }
                     if(pwform.getTyop().equalsIgnoreCase("PartialWithdrawal")||pwform.getForward().equalsIgnoreCase("Verify") && pwform.getValue().equalsIgnoreCase("2"))
                     {
                    	 System.out.println("*************");
/*                    	 if(Integer.parseInt(pwform.getPgno())!=0){
                    		 //int noof_days = Validations.dayCompare(pmObj.getAccOpenDate(),Date.getSysDate());
                    		 double bal=0.0;
                    		 bal=Double.parseDouble(pwform.getPrincipal());
                    		 System.out.println("------------- Balance PW ---------------------->"+Double.parseDouble(pwform.getPrincipal()));
                    		 double max_with_amt=pgDelegate.getPartialWith(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()));
                    		 req.setAttribute("par_max_amt", max_with_amt);
                    		 req.setAttribute("par_with_amt", max_with_amt);
                    	 }	 
                    	 
                      
                    	 if(pwform.getWithdrawal_amt().length()>0 ){
                    		 System.out.println("---- Hey M Here in partwith----");
                         	int no_of_months=0;
                         	double WithAmt=0.0;
                 	 		no_of_months=pgDelegate.pygGetNoOfMonths(pmObj.getAccOpenDate(),Date.getSysDate());
                 	 		
                 	 		PygmyRateObject prObj=pgDelegate.getIntDetails(no_of_months,Date.getSysDate());
                 	 		req.setAttribute("PygmyRate", prObj);
                 	 		System.out.println("---INTRATE----->>>>>>>>>>"+prObj.getIntRate());
                 	 		if(prObj!=null && prObj.getIntRate()==0 && WithAmt!=Double.parseDouble(pwform.getWithdrawal_amt()) && pwform.getTyop().equalsIgnoreCase("PartialWithdrawal")) {
                 	 			double penalAmt=pgDelegate.getPenaltyAmount(Double.parseDouble(pwform.getWithdrawal_amt()));
                 	 		
                 	 			System.out.println("******************penal amount"+penalAmt);
                 	 			req.setAttribute("Fines",penalAmt);
                 	 			double withdrawal_amt=Double.parseDouble(pwform.getWithdrawal_amt())-penalAmt;
                 	 			req.setAttribute("WithDrawAmt", withdrawal_amt);
                 	 		}
                         }*/
                         
         				int noof_days = Validations.dayCompare(pmObj.getAccOpenDate(),Date.getSysDate());
        				System.out.println("No of Days : "+noof_days);
        				boolean allow_wd=false;
        				
        				if(array_moduleobject_pygmy[0].getProperties().equalsIgnoreCase("F"))
        				{ //Partial Withdrawal is allowed or not for Particular Sub Module(Pygmy Module).
        					 pwform.setTyop("Closure");                     
        					req.setAttribute("msg","Partial Withdrawal Not allowed for this account");
        				}
        				else
        				{	 
        				
        					if(pmObj.getLnAvailed().equalsIgnoreCase("T"))
        					{
        						pwform.setTyop("Closure"); 
        						req.setAttribute("msg","Partial Withdrawal Not allowed for this account");
                			}
        					else
        					{
        					 allow_wd=true;
        					 System.out.println("RIZWAN .. ind 2=> "+allow_wd);
        					}
        				
        				}
        				if(noof_days<= array_moduleobject_pygmy[0].getMinPeriod())
        				{
        				    System.out.println("min peeeeeerioooooood======="+array_moduleobject_pygmy[0].getMinPeriod());
        				    pwform.setTyop("Closure"); 
        				    req.setAttribute("msg","Partial Withdrawal Not allowed for this account");
        				}
        				else
        					 allow_wd=true;
        					
        				System.out.println("LAst With No frm Pygmy Mas : "+pmObj.getLastWDNo());
        				System.out.println("LAst With No frm Modules :"+array_moduleobject_pygmy[0].getNoOfTrnsPerMonth());
        				
        				if(pmObj.getLastWDNo()+1 > array_moduleobject_pygmy[0].getNoOfTrnsPerMonth())
        				{	
        					pwform.setTyop("Closure");
        					req.setAttribute("msg","Partial Withdrawal Not allowed for this account");
                		}
        				else
                        {
        					allow_wd=true;
        					 System.out.println("RIZWAN .. ind 1=> "+allow_wd);
        				}
        			    
        				System.out.println("BABU .. ind=> "+allow_wd);	

        			    if(allow_wd==true)
        			    {
        			    	if(pwform.getTyop().equalsIgnoreCase("PartialWithdrawal")){
        					double balance = 0;
        					
        					 //allow partial withdraw..
        					System.out.println("Allowing Partial Withdraw..@@@");
        				  
        					//txt_withdrawal_amt.setEnabled(true);
                            
                            balance = Double.parseDouble(pwform.getPrincipal());
        					
                            pwform.setInterest("0.00");
        					
        					
        					double max_withdrawable = Math.round(balance*(array_moduleobject_pygmy[0].getMaxAmount()/100));
        					 pwform.setMax_amt(String.valueOf(max_withdrawable));
        					 pwform.setWithdrawal_amt(String.valueOf(max_withdrawable));
        					
        					System.out.println("Max With Amt =>"+max_withdrawable);
        			    	}
        				}
                    	 
                    	 
                    	 
                     }
                     
                     
						if (pwform.getPaymode().equalsIgnoreCase("Transfer") && pwform.getPayno()!=null && pwform.getPayactypeno()!=null && !(pwform.getPayno().equals("")) && !(pwform.getPayactypeno().equals(""))) {
							if (pwform.getPayactypeno() != null) {
								if (Integer.parseInt(pwform.getPayno()) != 0) {
									AccountObject acObj = pgDelegate.getPayAccount(null, pwform.getPayactypeno(),Integer.parseInt(pwform.getPayno()),Date.getSysDate());
			 						req.setAttribute("AccountDetail",acObj);
									System.out.println("$$$$$$$$$$$$$$$$$$$$$$"+acObj.getAccname());
									
									String Name=acObj.getAccname();
									req.setAttribute("SelfName", Name);
									
									
								}
							}
						}
						
						if(pwform.getForward().equalsIgnoreCase("Delete")){
                        	System.out.println("-->>>>>>>M In del mode-------------------");
                        	pwform.setForward("");
                        	
                        	int del=0;
                        	int intId=0;
                        	String id=pwform.getTyop();
                        	System.out.println("-----ID------->"+id);
                        	if(id.equalsIgnoreCase("closure")){
                        		
                        		del=pgDelegate.pygmyWithdrwalDeletion(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()), intId);
                        	}else {
                        		intId=1;
                        		del=pgDelegate.pygmyWithdrwalDeletion(pwform.getPgactypeno(),Integer.parseInt(pwform.getPgno()), intId);
                        	}
                        	
                        	if(del==2){
                        		req.setAttribute("msg","PygmyWithdrawal successfully Deleted!!!!!!!");
                        		pwform.setForward("");
                        	}else if(del==1)
                        	{
                        		req.setAttribute("msg","PygmyClosure Details Deleted");
                        		pwform.setForward("");
                        	}
                        	else if(del==-1){
                        		req.setAttribute("msg","Unable to Delete the Transaction..!!");
                        		pwform.setForward("");
                        	}
                        }
						
						if(pwform.getForward().equalsIgnoreCase("Verify")&&pwform.getTyop().equalsIgnoreCase("Closure")){
							 System.out.println(">>>>>>>>>>>>> Verify >>>>>>>>>>>>");
							 pwform.setForward("");
							 int exec=0;
							 PygmyMasterObject pmo=new PygmyMasterObject();
							 exec=pgDelegate.verifyClosure(Double.parseDouble(pwform.getLoan_bal()), Double.parseDouble(pwform.getLoan_int()), Date.getSysDate(), Date.getSysTime(), pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()),user,tml);
							 System.out.println("exec==============>>>>"+exec);
							 if(exec == -2 || exec >1){
							 if(exec>=1){
								 req.setAttribute("msg","Verified.Note down the Voucher /PayOrder Scroll No: "+exec);
								 pwform.setForward("");
							 }else if(exec==-2)
								 req.setAttribute("msg","Verified");
							 pwform.setForward("");
							 }
							 else if(exec==-1)
							 {
								 req.setAttribute("msg","Error");
								 pwform.setForward("");
							 }
						 }else if(pwform.getForward().equalsIgnoreCase("Verify")&&pwform.getTyop().equalsIgnoreCase("PartialWithdrawal") && pwform.getValue().equalsIgnoreCase("2")){
							 System.out.println(">>>>>>>>>>>>> Verify >>>>>>>>>>>>");
							 int exec=0;
							 PygmyMasterObject pmo=new PygmyMasterObject();
							 exec=pgDelegate.verifyClosure(Double.parseDouble(pwform.getLoan_bal()), Double.parseDouble(pwform.getLoan_int()), Date.getSysDate(), Date.getSysTime(), pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()),user,tml);
							 System.out.println("exec1111111==============>>>"+exec);
							 if(exec >= 1){
								 req.setAttribute("msg","Verified.Note down the Voucher /PayOrder Scroll No: "+exec);
								 pwform.setForward("");
							 }else if(exec == -2){
								 req.setAttribute("msg","Verified");
								 pwform.setForward("");
							 }
							 else if(exec == -1)
							 {
								 req.setAttribute("msg","Error!!!");
								 pwform.setForward("");
							 }
						 }
						
						if(pwform.getForward().equalsIgnoreCase("Submit")&&pwform.getTyop().equalsIgnoreCase("Closure") &&  pwform.getValue().equalsIgnoreCase("1")){
							System.out.println(">>>>>>>>>>>>> SUBMIT >>>>>>>>>>>>");
							double exec=0,exec1=0;
							String ac_p_mode="";
							if(pwform.getPaymode().equalsIgnoreCase("Transfer")){
								ac_p_mode="T";
							}else if(pwform.getPaymode().equalsIgnoreCase("Cash")){
								ac_p_mode="C";
							}else if(pwform.getPaymode().equalsIgnoreCase("PayOrder")){
								ac_p_mode="P";
							}
							System.out.println("-Before Closure->------------------>"+pwform.getInterest());
							System.out.println("-Before Closure->------------------>"+pwform.getFines());
							System.out.println("-Before Closure->------------------>"+pwform.getPaymode());
							System.out.println("-Before Closure->------------------>"+pwform.getPayactypeno()+"  NO"+pwform.getPayno());
							System.out.println("-Before Closure->------------------>"+pwform.getPgactypeno());
							System.out.println("-Before Closure->------------------>"+pwform.getPgno());
								exec=pgDelegate.closure(Double.parseDouble(pwform.getInterest()),Double.parseDouble(pwform.getFines()),Double.parseDouble(pwform.getLoan_bal()),Double.parseDouble( pwform.getLoan_int()), ac_p_mode,pwform.getPayactypeno(),Integer.parseInt( pwform.getPayno()), Date.getSysDate(), ""+Date.getSysDate()+""+Date.getSysTime(),1, pwform.getPgactypeno(), Integer.parseInt(pwform.getPgno()), user, tml);
							    System.out.println("exec=============>>>>"+exec);
								if(exec == 1){
									req.setAttribute("msg","Account Closed");
									pwform.setForward("");
								}else if(exec == 0){
									req.setAttribute("msg","Main Cashier not yet Opened..!\n Can't do this Transaction");
									pwform.setForward("");
								}else if(exec == -1)
								{
									req.setAttribute("msg","Error!!");
									pwform.setForward("");
								}
								
						}
						else if(pwform.getForward().equalsIgnoreCase("Submit")&& pwform.getTyop().equalsIgnoreCase("PartialWithdrawal")&&  pwform.getValue().equalsIgnoreCase("1")){
							System.out.println(">>>>>>>>>>>>> SUBMIT-1 >>>>>>>>>>>>");
							
								double exec1=0;
								exec1=pgDelegate.partialWithdraw(Double.parseDouble(pwform.getWithdrawal_amt()),Double.parseDouble(pwform.getFines()), pwform.getPaymode(),pwform.getPgactypeno(), Integer.parseInt(pwform.getPayno()),Date.getSysDate(),Date.getSysTime());
								if(exec1 == 1){
									req.setAttribute("msg","Account Transfered");
									pwform.setForward("");
								}else if(exec1 == 0){
									req.setAttribute("msg","Main Cashier not yet Opened..!\n Can't do this Transaction");
									pwform.setForward("");
								}else if(exec1 == -1)
								{
									req.setAttribute("msg","Error");
									pwform.setForward("");
								}
						}
						
						 
                    }   
                    
                else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getSuccess());
                }
			}catch(Exception e){
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e.toString(),req,path);
				return map.findForward(ResultHelp.getSuccess());
			}
			 System.out.println("hi i am here end :("+map.getPath());
			 setPygmyWithdrawalInitParams(req,path,pgDelegate);
		     return map.findForward(ResultHelp.getSuccess());
        }

		
		
	else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/InterestRegisteredMenu")) {
			InterestRegiForm intRegiForm = (InterestRegiForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + intRegiForm.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(intRegiForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(intRegiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(intRegiForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setReportsInitParams(req, path, pgDelegate);
					intRegiForm.setSysdate(PygmyDelegate.getSysDate());
					req.setAttribute("pageNum", intRegiForm.getPageid().trim());
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}
	

	else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/InterestRegistered")) {

			InterestRegiForm intRegiForm = (InterestRegiForm) form;
			System.out.println("#############="+ intRegiForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", intRegiForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(intRegiForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(intRegiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(intRegiForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					intRegiForm.setSysdate(PygmyDelegate.getSysDate());
					if(intRegiForm.getForward().equalsIgnoreCase("view"))
					{
						if (intRegiForm.getFrom_dt().length() > 0&& intRegiForm.getTo_dt().length() > 0)
						{
						QuarterInterestObject[] qtran = pgDelegate.retrieveInterestRegister(intRegiForm.getFrom_dt(), intRegiForm.getTo_dt(),null);
						if(qtran!=null){
						req.setAttribute("InterRegi", qtran);
						}else{
							System.out.println("i am inside length ZERO");
							req.setAttribute("msg","No Rows Found");
						}
						
					}
					}
					if(intRegiForm.getForward().equalsIgnoreCase("download"))
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
                	        out.print("Interest Registered Report Details: ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("Pygmy Type"); out.print(",");
                   		out.print("Pygmy No"); out.print(",");
                   		out.print("Interest Rate"); out.print(",");
                   		out.print("Interest Amt"); out.print(",");
                   		out.print("DETml"); out.print(",");
                   		out.print("DEUser"); out.print(",");
                   		out.print("DE Dt/Time"); out.print("\n");
                   		QuarterInterestObject[] qtran = pgDelegate.retrieveInterestRegister(intRegiForm.getFrom_dt(), intRegiForm.getTo_dt(),null);
						if(qtran!=null){
						req.setAttribute("InterRegi", qtran);
						
						
						 for(int k=0;k<qtran.length;k++){
                  			  
                    			out.print(qtran[k].getPygmyType());
                    			out.print(",");
                    			out.print(qtran[k].getPygmyNo());
                    			out.print(",");
                    			out.print(qtran[k].getIntDate());
                    			out.print(",");
                    			out.print(qtran[k].getIntAmount());
                    			out.print(",");
                    			out.print(qtran[k].getTml());
                    			out.print(",");
                    			out.print(qtran[k].getUid());
                    			out.print(",");
                    			out.print(qtran[k].getTime());
                    			out.print(",");
                    			out.print("\n"); 
                   		
					}
						req.setAttribute("msg","Saved to excel file in C:");
             		    return null;
             		 
					}
						else
						{
							req.setAttribute("msg","No Rows Found to download");
						
						}
						}
					setPygmyWithdrawalInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}

		}

		
			
	else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/OpenCloseMenu")) {
		OpenCloseForm OCForm = (OpenCloseForm) form;
		System.out.println("hi i am here" + map.getPath());
		try {
			System.out.println("===" + OCForm.getPageid());
			System.out.println("--------------------------------------------------------");
			System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(OCForm.getPageid()));
			System.out.println("((((((((((((((((()))))))))))))))))))------");
			if (MenuNameReader.containsKeyScreen(OCForm.getPageid())) {
				path = MenuNameReader.getScreenProperties(OCForm.getPageid());
				System.out.println("insideExecutre path" + path);
				pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
				setReportsInitParams(req, path, pgDelegate);
				OCForm.setSysdate(PygmyDelegate.getSysDate());
				OCForm.setFrom_date(Date.getSysDate());
				OCForm.setTo_date(Date.getSysDate());
				System.out.println("++++++++++++++++++++++++");
				req.setAttribute("pageNum", OCForm.getPageid().trim());
				System.out.println("++++++++++++++++++++++++-----------");
				
				return map.findForward(ResultHelp.getSuccess());
			} else {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} catch (Exception e) {
			path = MenuNameReader.getScreenProperties("0000");
			e.printStackTrace();
			setErrorPageElements("Unable to process the reqest", req, path);
			return map.findForward(ResultHelp.getSuccess());
		}
	} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/OpenCloseReport")) {
		System.out.println("(((((((((((((((((((((((((((((((()))))))))))))))))");
		OpenCloseForm OCForm = (OpenCloseForm) form;
		
		System.out.println("thesfkdfjdshf====>"+OCForm.getForward());
		System.out.println("The value of forward---->"+OCForm.getForward());
		System.out.println("#############=" + OCForm.getPageid().trim());

		try {
			req.setAttribute("pageNum", OCForm.getPageid().trim());
			System.out.println("--------------------------------------------------------");
			System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(OCForm.getPageid()));
			if (MenuNameReader.containsKeyScreen(OCForm.getPageid())) {
				
				
				System.out.println("OCForm.getForward()====>>."+OCForm.getForward());
				
				
				path = MenuNameReader.getScreenProperties(OCForm.getPageid());
				System.out.println(path);
				pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
				System.out.println(pgDelegate);
				OCForm.setSysdate(PygmyDelegate.getSysDate());
				if(OCForm.getForward()!=null&&OCForm.getForward().equalsIgnoreCase("View"))
				{
				if (OCForm.getTo_date().length() != 0)
				{
					
					if (OCForm.getComboActive().equalsIgnoreCase("Closed A/C")) {
						if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
							PygmyMasterObject pm_array[] = pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 1);
							req.setAttribute("OpenCloseDetails", pm_array);
							System.out.println("-------- no pf records---------Closed A/C"+ pm_array.length);
							if(pm_array.length==0)
							{
								req.setAttribute("msg", "Records Not Found");
								//req.setAttribute(arg0, arg1)
							}
						}
					} else if (OCForm.getComboActive().equalsIgnoreCase("Active A/C")) {
						if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
							PygmyMasterObject pm_array[] = pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 0);
							req.setAttribute("OpenCloseDetails", pm_array);
							System.out.println("-------- no pf records---------Active A/C"+ pm_array.length);
							if(pm_array.length==0)
							{
								req.setAttribute("msg", "Records Not Found");
							}
						}
					} else if (OCForm.getComboActive().equalsIgnoreCase("All A/C")) {
						if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
							PygmyMasterObject pm_array[] = pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 2);
							req.setAttribute("OpenCloseDetails", pm_array);
							System.out.println("-------- no pf records---------All A/C"+ pm_array.length);
							if(pm_array.length==0)
							{
								req.setAttribute("msg", "Records Not Found");
							}
						}
					}
					
				}	
				}
				
				if(OCForm.getForward().equalsIgnoreCase("save"))
				{
					PygmyMasterObject pm_array[]=null;
					System.out.println("Inside save ");
           		 //TO save to an excel Sheet
           		 res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=ocreport.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("Open/Close Report Details: ");
           	        out.print("\n");
           	        out.print("\n");
           	     /*HSSFCell cell = row.createCell((short)0);
            		   cell.setCellValue(1);*/
         	       
            		out.print("A/C No"); out.print(",");
            		out.print("Name"); out.print(",");
            		//out.print("Address"); out.print(",");
            		out.print("AgentNo"); out.print(",");
            		out.print("Agent Name"); out.print(",");
            		out.print("Date of Opening"); out.print("\n");
            		if (OCForm.getComboActive().equalsIgnoreCase("Closed A/C")) {
						if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
							pm_array= pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 1);
							req.setAttribute("OpenCloseDetails", pm_array);
							System.out.println("-------- no pf records---------Closed A/C"+ pm_array.length);
							
						}
            		}
				 else if (OCForm.getComboActive().equalsIgnoreCase("Active A/C")) {
					if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
						 pm_array = pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 0);
						req.setAttribute("OpenCloseDetails", pm_array);
						System.out.println("-------- no pf records---------Active A/C"+ pm_array.length);
						
					}
				} else if (OCForm.getComboActive().equalsIgnoreCase("All A/C")) {
					if (OCForm.getFrom_date().length() > 0&& OCForm.getTo_date().length() > 0) {
						pm_array = pgDelegate.getOpenClose(OCForm.getFrom_date(),OCForm.getTo_date(), 2);
						req.setAttribute("OpenCloseDetails", pm_array);
						System.out.println("-------- no pf records---------All A/C"+ pm_array.length);
						
					}
				}
            		for(int k=0;k<pm_array.length;k++){
            			System.out.println("array length====>>>"+pm_array);
            			out.print(pm_array[k].getAccNo());
               			out.print(",");
               			out.print(pm_array[k].getName());
               			out.print(",");
               			
               			/*StringTokenizer st=new StringTokenizer(pm_array[k].getAddress(),",");
               			out.println(st.nextToken());*/
               			
               		//	out.print(",");
               			out.print(pm_array[k].getAgentNo());
               			out.print(",");
               			out.print(pm_array[k].getAgentName());
               			out.print(",");
               			out.print(pm_array[k].getAccOpenDate());
               			out.print("\n");  
            		}
            		  req.setAttribute("msg","Saved to excel file in C:");
          		    return null;
				}
				
				setReportsInitParams(req, path, pgDelegate);
				return map.findForward(ResultHelp.getSuccess());

			}

			else {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} catch (Exception e) {
			e.printStackTrace();
			path = MenuNameReader.getScreenProperties("0000");
			setErrorPageElements("Unable to process the reqest", req, path);
			return map.findForward(ResultHelp.getSuccess());
		}

			
			
			
			
			
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentRemittanceMenu")) {
			AgentRemittanceForm AgRemiForm = (AgentRemittanceForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + AgRemiForm.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(AgRemiForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");
				AgRemiForm.setAgent_no("");
				if (MenuNameReader.containsKeyScreen(AgRemiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(AgRemiForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					AgRemiForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					System.out.println("++++++++++++++++++++++++");
					req.setAttribute("pageNum", AgRemiForm.getPageid().trim());
					AgRemiForm.setRem_date(Date.getSysDate());
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("" + e, req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentRemittanceReport")) {
			System.out.println("(((((((((((((((((((((((((((((((()))))))))))))))))");
			AgentRemittanceForm AgRemiForm = (AgentRemittanceForm) form;
			System.out.println("#############=" + AgRemiForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", AgRemiForm.getPageid().trim());
				System.out.println("----------------Action----------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(AgRemiForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(AgRemiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(AgRemiForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println("delegete=====>>>"+pgDelegate);
					AgRemiForm.setSysdate(PygmyDelegate.getSysDate());
					
					AgentMasterObject amObj = pgDelegate.getAccountMaster(AgRemiForm.getAgent_code(),Integer.parseInt(AgRemiForm.getAgent_no()), AgRemiForm.getRem_date());
					System.out.println("amObj====AgentRem===========>>"+amObj);
					//req.setAttribute("AgentDetails", amObj);
					if(amObj.getAgentNo()== -1)
					{
						req.setAttribute("msg","Agent Not Found!!");
					}
					if(amObj.getCloseInd()==1)
					{
						req.setAttribute("msg","Closed Account");
					}

					if (AgRemiForm.getForward().equalsIgnoreCase("View")) {
						PygmyTransactionObject[] ptrans = pgDelegate.getAgtRemittanceReport(Integer.parseInt(AgRemiForm.getAgent_no()),AgRemiForm.getAgent_code(), AgRemiForm.getRem_date());
						//System.out.println("acnoooooo"+ptrans[0].getAccNo());
						req.setAttribute("AgRemittance", ptrans);
						if(ptrans.length==0)
						{
							req.setAttribute("msg", "Records Not Found!");
						}
					}
					
					if(AgRemiForm.getForward().equalsIgnoreCase("save"))
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
                	        out.print("Agent Details for A/C No: "+AgRemiForm.getAgent_no());
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("Account Type"); out.print(",");
                   		out.print("Account No"); out.print(",");
                   		//out.print("Narration/Payee"); out.print(",");
                   		out.print("Name"); out.print(",");
                   		out.print("Collection Date"); out.print(",");
                   		out.print("Remittance Amount"); out.print("\n");
                   		PygmyTransactionObject[] ptrans = pgDelegate.getAgtRemittanceReport(Integer.parseInt(AgRemiForm.getAgent_no()),AgRemiForm.getAgent_code(), AgRemiForm.getRem_date());
                   		for(int k=0;k<ptrans.length;k++){
                   			out.print(ptrans[k].getAccType());
                   			out.print(",");
                   			out.print(ptrans[k].getAccNo());
                   			out.print(",");
                   			out.print(ptrans[k].getName());
                   			out.print(",");
                   			out.print(ptrans[k].getCollectionDate());
                   			out.print(",");
                   			out.print(ptrans[k].getTranAmt());
                   			out.print(",");
                   			out.print("\n"); 
                   		 
                   		}
                   		req.setAttribute("msg","Saved to excel file in C:");
            		    return null;
                   		
					}

					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/MonthlyRemittanceMenu")) {
			MonthlyRemittanceForm MthRemiForm = (MonthlyRemittanceForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + MthRemiForm.getPageid());
				
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(MthRemiForm.getPageid()));
				
				if (MenuNameReader.containsKeyScreen(MthRemiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(MthRemiForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					MthRemiForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					System.out.println("++++++++++++++++++++++++");
					req.setAttribute("pageNum", MthRemiForm.getPageid().trim());
					MthRemiForm.setFrom_date(Date.getSysDate());
					MthRemiForm.setTo_date(Date.getSysDate());
					MthRemiForm.setAgent_no("0");
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/MonthlyRemittance")) {
			System.out.println("(((((((((((((((((((((((((((((((()))))))))))))))))");
			MonthlyRemittanceForm MthRemiForm = (MonthlyRemittanceForm) form;
			System.out.println("#############="+ MthRemiForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", MthRemiForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(MthRemiForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(MthRemiForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(MthRemiForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					MthRemiForm.setSysdate(PygmyDelegate.getSysDate());
					if(MthRemiForm.getForward().equalsIgnoreCase("AgentNo"))
					{
						AgentMasterObject amObjRem = pgDelegate.getAccountMaster(MthRemiForm.getAgent_code(),Integer.parseInt(MthRemiForm.getAgent_no()),Date.getSysDate());
						System.out.println("amObj====MonthlyRem===========>>"+amObjRem.getAgentNo());
						//req.setAttribute("AgentDetails", amObj);
						if(amObjRem.getAgentNo()==-1)
						{
							amObjRem.setAgentNo(0);
							req.setAttribute("AgentDetails", amObjRem);
							System.out.println("----m inside Agent???-----");
							req.setAttribute("msg","Agent Not Found");
						}
						

					}
					if(MthRemiForm.getForward().equalsIgnoreCase("view")){
					if(!MthRemiForm.getComboAgents().equalsIgnoreCase("SELECT"))
					{
					if (MthRemiForm.getComboAgents().equalsIgnoreCase("All")) {

						PygmyTransactionObject[] ptran = pgDelegate.retrieveMonthlyRemit("", 0, MthRemiForm.getFrom_date(), MthRemiForm.getTo_date());
						req.setAttribute("MnthRemi", ptran);
						if(ptran==null)
						{
							req.setAttribute("msg","No records Found");
						}

					} else if (MthRemiForm.getComboAgents().equalsIgnoreCase("Individual")) {
						// req.setAttribute("Disable", false);
						AgentMasterObject amObj = pgDelegate.getAccountMaster(MthRemiForm.getAgent_code(),Integer.parseInt(MthRemiForm.getAgent_no()), Date.getSysDate());
						if(amObj.getAgentNo()==-1)
						{
							amObj.setAccNo(0);
						req.setAttribute("AgentDetails", amObj);
						req.setAttribute("msg","Agent Not Found");
						System.out.println("^^^^^^^^^^^^^^^^^^^"+amObj);
						}
						else{
						if (Integer.parseInt(MthRemiForm.getAgent_no()) != 0) {
							PygmyTransactionObject[] ptran = pgDelegate.retrieveMonthlyRemit(MthRemiForm.getAgent_code(),Integer.parseInt(MthRemiForm.getAgent_no()),MthRemiForm.getFrom_date(),MthRemiForm.getTo_date());
							if(ptran!=null){
								req.setAttribute("MnthRemi", ptran);
								System.out.println("length of ptran is --->>"+ptran.length);
							}
							else
							{
								req.setAttribute("msg","No records Found");
							}
						}
					}
					}
					else{
						req.setAttribute("msg","Select Agent Type");
					}
					}
					}
					
					else if(MthRemiForm.getForward().equalsIgnoreCase("save"))
					{
						AgentMasterObject amObj=null;
						PygmyTransactionObject[] ptran=null;
						 System.out.println("Inside save ");
                		 //TO save to an excel Sheet
                		 res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("Monthly Remittance Report Details ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("Pygmy Type"); out.print(",");
                   		out.print("Pygmy No"); out.print(",");
                   		out.print("Collected on"); out.print(",");
                   		out.print("Remitted on"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Amount credit"); out.print(",");
                   		out.print("Close Balance"); out.print("\n");
                   		if (MthRemiForm.getComboAgents().equalsIgnoreCase("Individual"))
                   		{
                   			amObj = pgDelegate.getAccountMaster(MthRemiForm.getAgent_code(),Integer.parseInt(MthRemiForm.getAgent_no()), Date.getSysDate());
    						System.out.println("length=======>>>>"+amObj.toString().length());
    						for(int k=0;k<amObj.toString().length();k++)
    						{
    							System.out.println("length=======>>>>"+amObj.getAppointDate());
    							
    						}
                   		}else{
    						
    							ptran = pgDelegate.retrieveMonthlyRemit(MthRemiForm.getAgent_code(),Integer.parseInt(MthRemiForm.getAgent_no()),MthRemiForm.getFrom_date(),MthRemiForm.getTo_date());
    							for(int k=0;k<ptran.length;k++){
    	                   			out.print(ptran[k].getAccType());
    	                   			out.print(",");
    	                   			out.print(ptran[k].getAccNo());
    	                   			out.print(",");
    	                   			out.print(ptran[k].getCollectionDate());
    	                   			out.print(",");
    	                   			out.print(ptran[k].getTranDate());
    	                   			out.print(",");
    	                   			/*StringTokenizer st=new StringTokenizer(ptran[k].getTranNarration(),",");
    	                   			out.print(st.nextToken()+" "+st.nextToken());*/
    	                   			out.print(ptran[k].getTranNarration());
    	                   			out.print(",");
    	                   			out.print(ptran[k].getTranAmt());
    	                   			out.print(",");
    	                   			out.print(ptran[k].getCloseBal());
    	                   			out.print(",");
    	                   			out.print("\n"); 
    	                   		}
    							
                   		}
                   		req.setAttribute("msg","Saved to excel file in C:");
                   		return null;
						}
					
					
					

					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PassBookMenu")){
			PassBookForm pbForm = (PassBookForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + pbForm.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(pbForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");
				pbForm.setPygno("");
				if (MenuNameReader.containsKeyScreen(pbForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(pbForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					pbForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					System.out.println("++++++++++++++++++++++++");
					req.setAttribute("pageNum", pbForm.getPageid().trim());
					pbForm.setFrom_date(Date.getSysDate());
					pbForm.setTo_date(Date.getSysDate());
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PassBook")) {
			System.out.println("(((((((((((((((((((((((((((((((()))))))))))))))))");
			PassBookForm pbForm = (PassBookForm) form;
			System.out.println("#############=" + pbForm.getPageid().trim());
			boolean flag = false;
			try {
				req.setAttribute("pageNum", pbForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(pbForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(pbForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(pbForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					pbForm.setSysdate(PygmyDelegate.getSysDate());
					System.out.println(pgDelegate);
					if (!pbForm.getPygtype().equalsIgnoreCase("SELECT")) {
						System.out.println("Inside select----->>>");
						if(pbForm.getPygno()!=null){
							PygmyMasterObject pmObj = pgDelegate.getAccMaster(Integer.parseInt(pbForm.getPygno()), pbForm.getPygtype());
							req.setAttribute("PygmyDetails", pmObj);
							int acno = pmObj.getAccNo();
							System.out.println("ACNO is65767==================="+ pmObj);

							if (pmObj.getAccStatus() == null) {
								pbForm.setPygno("0");
								req.setAttribute("PygmyDetails", pmObj);
								System.out.println("====AgentCheck1111225====");
								req.setAttribute("msg","Account Num not Found");
							} 
							else if(pmObj.getAccStatus().equalsIgnoreCase("C"))
							{
								req.setAttribute("msg","Closed Account");
							}
						}
						else
						{
							req.setAttribute("msg","Enter Account Num ");
						}
					}
					else{
						req.setAttribute("msg","Select Account Type");
					}

					if (pbForm.getForward().equalsIgnoreCase("View")) {
						
						PygmyMasterObject pmObj = pgDelegate.getAccMaster(Integer.parseInt(pbForm.getPygno()), pbForm.getPygtype());
						if(pmObj.getAccNo()==-1)
						{
							System.out.println("====m inside Viewww====");
							pbForm.setPygno("0");
							req.setAttribute("PygmyDetails", pmObj);
							req.setAttribute("msg","Account Not Found");
						}
						else{
						PygmyTransactionObject[] pgtrans = pgDelegate.getPygmyTransaction(pbForm.getPygtype(),Integer.parseInt(pbForm.getPygno()),pbForm.getFrom_date(), pbForm.getTo_date());
						if(pgtrans!=null)
						{
							System.out.println("-------------" + pgtrans);
							req.setAttribute("PassBook", pgtrans);
						}else{
							
							req.setAttribute("msg", "Records Not Found");
							System.out.println("m inside else--------->");
						}
					}
					}
					
					if(pbForm.getForward().equalsIgnoreCase("save"))
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
                	        out.print("PassBook Details for A/C No: "+pbForm.getPygno());
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("Date"); out.print(",");
                   		out.print("Narration"); out.print(",");
                   		out.print("Debit"); out.print(",");
                   		out.print("Credit"); out.print(",");
                   		out.print("Balance"); out.print("\n");
                   		PygmyTransactionObject[] pgtrans = pgDelegate.getPygmyTransaction(pbForm.getPygtype(),Integer.parseInt(pbForm.getPygno()),pbForm.getFrom_date(), pbForm.getTo_date());
						if(pgtrans!=null)
						{
							System.out.println("-------------" + pgtrans);
							req.setAttribute("PassBook", pgtrans);
						
						for(int k=0;k<pgtrans.length;k++)
						{
							out.print(pgtrans[k].getTranDate());
                   			out.print(",");
                   			StringTokenizer st=new StringTokenizer(pgtrans[k].getTranNarration(),",");
                   			//out.print(st.nextToken()+" "+st.nextToken());
                   			out.print(pgtrans[k].getTranNarration());
                   			out.print(",");
                   			out.print(pgtrans[k].getDebAmt());
                   			out.print(",");
                   			out.print(pgtrans[k].getCrAmt());
                   			out.print(",");
                   			out.print(pgtrans[k].getCloseBal());
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

					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}

		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/RegisterPrintingMenu")) {
			RegisterPrintForm rpForm = (RegisterPrintForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + rpForm.getPageid());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(rpForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");

				if (MenuNameReader.containsKeyScreen(rpForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(rpForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					rpForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					System.out.println("++++++++++++++++++++++++");
					req.setAttribute("pageNum", rpForm.getPageid().trim());
					rpForm.setFrom_date(Date.getSysDate());
					rpForm.setTo_date(Date.getSysDate());
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getError());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/RegisterPrint")) {
			System.out.println("(((((((((((((((((((((((((((((((()))))))))))))))))");
			RegisterPrintForm rpForm = (RegisterPrintForm) form;
			System.out.println("#############=" + rpForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", rpForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(rpForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(rpForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(rpForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					rpForm.setSysdate(PygmyDelegate.getSysDate());
					System.out.println(pgDelegate);
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********");
					if(rpForm.getForward().equalsIgnoreCase("view"))
					{
					if (rpForm.getTo_date().length() != 0) {
						if (rpForm.getRecType().equalsIgnoreCase("Opened Accounts")) {
							QuarterInterestObject[] qtr = pgDelegate.retrieveRegister(rpForm.getFrom_date(),rpForm.getTo_date(), 0);
							if(qtr!=null){
							req.setAttribute("REGIPRINT", qtr);
							System.out.println("===========" + qtr.length);
							}
							else 
							{
								System.out.println("m inside else=====");
								req.setAttribute("msg", "Records Not Found!");
							}
						} else if (rpForm.getRecType().equalsIgnoreCase("Closed Accounts")) {
							QuarterInterestObject[] qtr = pgDelegate.retrieveRegister(rpForm.getFrom_date(),rpForm.getTo_date(), 1);
							if(qtr!=null){
							req.setAttribute("REGIPRINT", qtr);
							System.out.println("===========" + qtr.length);
							}else
							{
								req.setAttribute("msg", "Records Not Found!");
								System.out.println("m inside else--------");
							}
						}

					}
					}
					if(rpForm.getForward().equalsIgnoreCase("save"))
					{
						QuarterInterestObject[] qtr=null;
						 System.out.println("Inside save ");
                		 //TO save to an excel Sheet
                		 res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("Register Printing Report Details  ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("Pygmy Type"); out.print(",");
                   		out.print("Pygmy No"); out.print(",");
                   		out.print("Nominee Name"); out.print(",");
                   		out.print("Nominee Relation"); out.print(",");
                   		out.print("Date of Opening"); out.print(",");
                   		out.print("int pd upto"); out.print(",");
                   		out.print("closing Bal"); out.print("\n");
                   		if (rpForm.getTo_date().length() != 0) {
                   		if (rpForm.getRecType().equalsIgnoreCase("Opened Accounts")) {
                   			
                   			qtr = pgDelegate.retrieveRegister(rpForm.getFrom_date(),rpForm.getTo_date(), 0);
							if(qtr!=null){
							req.setAttribute("REGIPRINT", qtr);
							System.out.println("===========" + qtr.length);
							}
							
                   		}else if (rpForm.getRecType().equalsIgnoreCase("Closed Accounts")) {
							qtr = pgDelegate.retrieveRegister(rpForm.getFrom_date(),rpForm.getTo_date(), 1);
							if(qtr!=null){
							req.setAttribute("REGIPRINT", qtr);
							System.out.println("===========" + qtr.length);
							}
						 }
                   	 for(int k=0;k<qtr.length;k++){
              			  
                			out.print(qtr[k].getPygmyType());
                			out.print(",");
                			out.print(qtr[k].getPygmyNo());
                			out.print(",");
                			out.print(qtr[k].getNomName());
                			out.print(",");
                			out.print(qtr[k].getNomRelation());
                			out.print(",");
                			out.print(qtr[k].getIntDate());
                			out.print(",");
                			out.print(qtr[k].getIntPdUpto());
                			out.print(",");
                			out.print(qtr[k].getClBal());
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
					
					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}

		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyInterestCalculationMenu")) {
			PygmyInterestCalForm intcalForm = (PygmyInterestCalForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + intcalForm.getPageid());
				
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(intcalForm.getPageid()));
				

				if (MenuNameReader.containsKeyScreen(intcalForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(intcalForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setIntCalInitParams(req, path, pgDelegate);
					
					req.setAttribute("pageNum", intcalForm.getPageid().trim());
					
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyInterestCal")) {
			
			PygmyInterestCalForm intcalForm = (PygmyInterestCalForm) form;
			System.out.println("#############=" + intcalForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", intcalForm.getPageid().trim());
				
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(intcalForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(intcalForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(intcalForm.getPageid());
					
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					
					

					if (intcalForm.getForward().equals("Calculate Interest")) {
						intcalForm.setUid(tml);
						intcalForm.setTml(user);
						intcalForm.setBr_location("Head office");
						
						System.out.println("intcalForm.getUid()==============>>>"+intcalForm.getUid());
						System.out.println("intcalForm.getTml()==============>>>"+intcalForm.getTml());
						System.out.println("Date.getSysDate()==============>>>"+Date.getSysDate());
						System.out.println("Date.getSysTime()==============>>"+Date.getSysTime());
						System.out.println("intcalForm.getBr_location()====>>>"+intcalForm.getBr_location());
						int cal = pgDelegate.pigmyCalendarQuerterInterestCalc(intcalForm.getUid(), intcalForm.getTml(),Date.getSysDate(),Date.getSysTime(),intcalForm.getBr_location());
						System.out.println("cal================>>>>"+cal);
						if(cal==-2){
							req.setAttribute("msg","Interest should be calculated at Quarter year end.");
						}
						else if(cal==1){
							req.setAttribute("msg","No open records for calculation");
						}
						else if(cal==-1){
							req.setAttribute("msg","Interest should be calculated at Quarter year end");
						}
						else{
							req.setAttribute("msg","Interest calculated successfully");
						}
					}
					setIntCalInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} 
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/LedgerPrintingMenu")) {
			LedgerPrintingForm lpForm = (LedgerPrintingForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + lpForm.getPageid());
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(lpForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");
				lpForm.setStAcno("");
				if (MenuNameReader.containsKeyScreen(lpForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(lpForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					lpForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					lpForm.setTrn_dt(Date.getSysDate());
					lpForm.setTrn_dt_upto(Date.getSysDate());
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/LedgerPrinting")) {
			LedgerPrintingForm lpForm = (LedgerPrintingForm) form;
			System.out.println("#############=" + lpForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", lpForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre--"+ MenuNameReader.containsKeyScreen(lpForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(lpForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(lpForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********");
					lpForm.setSysdate(PygmyDelegate.getSysDate());
					PygmyMasterObject pmObj = pgDelegate.getAccMaster(Integer.parseInt(lpForm.getStAcno()),lpForm.getPygtype());
					System.out.println("pmObj===m in ledger111===>>"+pmObj);
					//req.setAttribute("PygmyDetails", pmObj);
					if(pmObj.getAccNo()==-1)
					{
						req.setAttribute("msg","Account Not Found");
					}
					
					
					if(lpForm.getForward().equalsIgnoreCase("view"))
					{
					if(lpForm.getTrn_dt_upto()!=null)
					{
						PygmyMasterObject[] pm = pgDelegate.getPygmyLedgerReportNew(lpForm.getTrn_dt().trim(),lpForm.getTrn_dt_upto().trim(), Integer.parseInt(lpForm.getStAcno()));
						System.out.println("--- pm+++++++-Action---" + pm.length);
						req.setAttribute("LEDGERDETAILS", pm);
						//System.out.println("pm[0].getAccStatus()"+pm[0].getAccStatus());
					}
					//}
					
					//if(lpForm.getForward().equalsIgnoreCase("view"))
					//{

						PygmyTransactionObject[] ptran = pgDelegate.getPygmyLedgerTransactionNew(lpForm.getTrn_dt(), lpForm.getTrn_dt_upto(),Integer.parseInt(lpForm.getStAcno()));
						if(ptran!=null){
						System.out.println("======== ptran ++===Action===" + ptran.length);
						req.setAttribute("LedgerTable", ptran);
						}
						else{
							req.setAttribute("msg","No Rows Found");
						}
					
					}
					
					
					if(lpForm.getForward().equalsIgnoreCase("save"))
					{
						PygmyTransactionObject[] ptran=null;
						System.out.println("Inside save ");
               		 //TO save to an excel Sheet
               		 res.setContentType("application/.csv");
               	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
               	        
               	        java.io.PrintWriter out = res.getWriter();
               	        out.print("\n");
               	        out.print("\n");
               	        out.print("\n");
               	        out.print(",");out.print(",");out.print(",");
               	        out.print("PassBook Details for A/C No: "+lpForm.getStAcno());
               	        out.print("\n");
               	        out.print("\n");
                  		   /*HSSFCell cell = row.createCell((short)0);
                  		   cell.setCellValue(1);*/
               	       
                  		out.print("Date"); out.print(",");
                  		out.print("Narration"); out.print(",");
                  		out.print("Debit"); out.print(",");
                  		out.print("Credit"); out.print(",");
                  		out.print("Balance"); out.print("\n");
                  		ptran = pgDelegate.getPygmyLedgerTransactionNew(lpForm.getTrn_dt(), lpForm.getTrn_dt_upto(),Integer.parseInt(lpForm.getStAcno()));
						if(ptran!=null){
						System.out.println("======== ptran ++===Action===" + ptran.length);
						req.setAttribute("LedgerTable", ptran);
						}
						for(int k=0;k<ptran.length;k++){
                 			  
                   			out.print(ptran[k].getTranDate());
                   			out.print(",");
                   			/*StringTokenizer st=new StringTokenizer(ptran[k].getTranNarration(),",");
                   			out.print(st.nextToken()+" "+st.nextToken());*/
                   			out.print(ptran[k].getTranNarration());
                   			out.print(",");
                   			
                   			out.print(ptran[k].getDebAmt());
                   			out.print(",");
                   			out.print(ptran[k].getCrAmt());
                   			out.print(",");
                   			out.print(ptran[k].getCloseBal());
                   			out.print(",");
                   			out.print("\n");
						}
						req.setAttribute("msg","Saved to excel file in C:");
            		    return null;
					}
					

					


					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AcknowledgementMenu")) {
			AcknowledgementForm ackForm = (AcknowledgementForm) form;
			System.out.println("hi i am here" + map.getPath());
			try { 
				System.out.println("===" + ackForm.getPageid());
				System.out.println("insideExecutre111111111111222"+ MenuNameReader.containsKeyScreen(ackForm.getPageid()));
				System.out.println("((((((helloooo(((((((((((Action)))))))))))))))))))------");
				ackForm.setAgNo("");
				if (MenuNameReader.containsKeyScreen(ackForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(ackForm.getPageid());
					System.out.println("insideExecutre path2222" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					ackForm.setSysdate(PygmyDelegate.getSysDate());
					setReportsInitParams(req, path, pgDelegate);
					req.setAttribute("pageNum", ackForm.getPageid().trim());
					ackForm.setRemi_frm_date(Date.getSysDate());
					ackForm.setRemi_to_date(Date.getSysDate());
					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/Acknowledgement")) {
			System.out.println("-------m inside Acknowledgement!!!!-------------");
			AcknowledgementForm ackForm = (AcknowledgementForm) form;
			System.out.println("#############=" + ackForm.getPageid().trim());
			pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			ackForm.setSysdate(PygmyDelegate.getSysDate());
			try {
				req.setAttribute("pageNum", ackForm.getPageid().trim());
				System.out.println("-----------------------Ack---------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(ackForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(ackForm.getPageid())) {
					System.out.println("________________Inside AgentOpeeinig  Action _______________________");
					path = MenuNameReader.getScreenProperties(ackForm.getPageid());
					System.out.println(path);
					//pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********");
					System.out.println("ackForm.getAgNo()=====>>>"+ackForm.getAgNo());
					if(ackForm.getForward().equalsIgnoreCase("AgentNo"))
					{
						AgentMasterObject amObj = pgDelegate.getAccountMaster(ackForm.getAgType(),Integer.parseInt(ackForm.getAgNo()),Date.getSysDate());
						System.out.println("amObj====Ackn===========>>"+amObj);
						//req.setAttribute("AgentDetails", amObj);
						if(amObj.getAgentNo()== -1)
						{
							req.setAttribute("msg","Agent Not Found!!");
						}

						AgentMasterObject[] agtmaster_obj=pgDelegate.getAgentNos(ackForm.getAgType(),0);
						System.out.println("agtmaster_obj=====>>>"+agtmaster_obj);
						
						System.out.println("m inside AgentNumber55555==");
						System.out.println("ackForm.getAgType()=====>>>"+ackForm.getAgType());
						String agName = pgDelegate.getAgentName1(ackForm.getAgType(), Integer.parseInt(ackForm.getAgNo()));
						String agNum=pgDelegate.getAgentName1(ackForm.getAgType(), Integer.parseInt(ackForm.getAgNo()));
						System.out.println("--- agName----" + agName);
						System.out.println("***********----------->");
						req.setAttribute("AgentName", agName);
						
					}
					if(ackForm.getForward().equalsIgnoreCase("View"))
					{
						System.out.println("------>"+ ackForm.getAgType());
						System.out.println("------>"+ Integer.parseInt(ackForm.getAgNo()));
						System.out.println("------>"+ ackForm.getRemi_frm_date());
						System.out.println("------>"+ ackForm.getRemi_to_date());
						String agName = pgDelegate.getAgentName1(ackForm.getAgType(), Integer.parseInt(ackForm.getAgNo()));
						req.setAttribute("AgentName", agName);
						PygmyTransactionObject[] ptrn = pgDelegate.printAcknowledgementSlips(ackForm.getAgType(),Integer.parseInt(ackForm.getAgNo()),ackForm.getRemi_frm_date(), ackForm.getRemi_to_date());
						if(ptrn!=null){
							System.out.println("--- tran----22" + ptrn);
							req.setAttribute("AckTable", ptrn);
							}else
							{
								System.out.println("m in else---------");
								req.setAttribute("msg","NO Rows Found");
							}
						
					}
					
					if(ackForm.getForward().equalsIgnoreCase("save"))
					{
						PygmyTransactionObject[] ptrn=null;
						 System.out.println("Inside save ");
                		 //TO save to an excel Sheet
						 	res.setContentType("application/.csv");
                	        res.setHeader("Content-disposition", "attachment;filename=output.csv");
                	        
                	        java.io.PrintWriter out = res.getWriter();
                	        out.print("\n");
                	        out.print("\n");
                	        out.print("\n");
                	        out.print(",");out.print(",");out.print(",");
                	        out.print("Acknowledgement Printing Report Details ");
                	        out.print("\n");
                	        out.print("\n");
                   		   /*HSSFCell cell = row.createCell((short)0);
                   		   cell.setCellValue(1);*/
                	       
                   		out.print("accNo"); out.print(",");
                   		out.print("name"); out.print(",");
                   		out.print("collectionDate"); out.print(",");
                   		out.print("tranDate"); out.print(",");
                   		out.print("recvFrom"); out.print(",");
                   		out.print("prevBalance"); out.print(",");
                   		out.print("total_remittance"); out.print(",");
                   		out.print("closeBal"); out.print("\n");
                   		ptrn = pgDelegate.printAcknowledgementSlips(ackForm.getAgType(),Integer.parseInt(ackForm.getAgNo()),ackForm.getRemi_frm_date(), ackForm.getRemi_to_date());
						if(ptrn!=null){
							System.out.println("--- tran----22" + ptrn);
							req.setAttribute("AckTable", ptrn);
							
						for(int k=0;k<ptrn.length;k++){
                 			  
                   			out.print(ptrn[k].getAccNo());
                   			out.print(",");
                   			out.print(ptrn[k].getName());
                   			out.print(",");
                   			out.print(ptrn[k].getCollectionDate());
                   			out.print(",");
                   			out.print(ptrn[k].getTranDate());
                   			out.print(",");
                   			out.print(ptrn[k].getRecvFrom());
                   			out.print(",");
                   			out.print(ptrn[k].getPrevBalance());
                   			out.print(",");
                   			out.print(ptrn[k].getTotal_remittance());
                   			out.print(",");
                   			out.print(ptrn[k].getCloseBal());
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
					

					setReportsInitParams(req, path, pgDelegate);
					return map.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} 
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentUpdateMenu")) 
		{
				AgentUpdateForm auForm = (AgentUpdateForm) form;
				System.out.println("hi i am here" + map.getPath());
				pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			try {
				System.out.println("page Id" + auForm.getPageid());
				if (MenuNameReader.containsKeyScreen(auForm.getPageid())) {
					auForm.setValid("");
					req.setAttribute("AgentDetails",null);
					auForm.setAgNo("0");
					auForm.setAgName("");
					auForm.setCustId("0");
					auForm.setAppDate("");
					auForm.setSelfAcNo("0");
					auForm.setSelfAccName("");
					auForm.setJtAcNo("0");
					auForm.setJtAccName("");
					auForm.setIntAccNo("0");
					auForm.setCloseInd("");
					auForm.setCloseDate("");
					auForm.setDetails("SELECT");
					path = MenuNameReader.getScreenProperties(auForm.getPageid());
					System.out.println("insideExecutre path" + path);
					setAgentUpdateInitParams(req, path, pgDelegate);
					req.setAttribute("pageNum", auForm.getPageid().trim());
					System.out.println("After Set Attribute");
					return map.findForward(ResultHelp.getSuccess());
				} 
				else 
				{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} 
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentUpdate")) 
		{
			AccountObject selfObject=null;
			AccountObject  jointObject=null;
			AccountObject intrObject = null;
			AgentUpdateForm auForm = (AgentUpdateForm) form;
			System.out.println("pageId---->" + auForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", auForm.getPageid().trim());
				if (MenuNameReader.containsKeyScreen(auForm.getPageid())) 
				{
					path = MenuNameReader.getScreenProperties(auForm.getPageid());
					System.out.println("Path---->"+ path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					setAgentUpdateInitParams(req, path, pgDelegate);
					
					if(!auForm.getAgType().equalsIgnoreCase("SELECT"))
					{
						AgentMasterObject amo=pgDelegate.getAgentDetails(auForm.getAgType(),Integer.parseInt(auForm.getAgNo()),Date.getSysDate());
						
						if(auForm.getForward().equalsIgnoreCase("AgAccNo"))
						{
							
							if(amo!=null)
							{
								auForm.setAgName(amo.getName());
								String custid=Integer.toString(amo.getCid());
								auForm.setCustId(custid);
								auForm.setAppDate(amo.getAppointDate());
								auForm.setSelfAccType(amo.getAccType());
								String selfAccNo=Integer.toString(amo.getAccNo());
								auForm.setSelfAcNo(selfAccNo);
								auForm.setSelfAccName(amo.getFname());
								auForm.setJtAccType(amo.getJointAccType());
								String jointAccNo=Integer.toString(amo.getJointAccNo()); 
								auForm.setJtAcNo(jointAccNo);
								String name = pgDelegate.getJointDetails(null, 0,amo.getPersonalAccType(), amo.getAccNo(), amo.getJtAccType(), amo.getJointAccNo(), 3);
								auForm.setJtAccName(name);
								auForm.setIntAccType(amo.getIntrAccType());
								String IntAccNo=Integer.toString(amo.getIntrAccNo()); 
								auForm.setIntAccNo(IntAccNo);
								if(amo.getCloseInd()==1){
									auForm.setCloseInd("CLOSED");
			                	}	
			                	else if(amo.getCloseInd()==0){
			                		auForm.setCloseInd("OPEN");
			                	}
								if(amo.getCloseDate()!=null)
								{
								auForm.setCloseDate(amo.getCloseDate());
								}
								else{
									auForm.setCloseDate("");
								}
								//auForm.setDetails();
								req.setAttribute("AgentDetails",amo);
								setAgentUpdateDetails("Personal", req,pgDelegate, amo, auForm, null, null,0);
							}
							else{
								req.setAttribute("msg","Agent Not Found!!");
							}
						}
						else if(auForm.getForward().equalsIgnoreCase("CustId"))
						{
							
		                		System.out.println("m inside CustId");
		                		if(auForm.getCustId().length()>0)
			                    {
			                        try
			                        {
			                            cid_val=Integer.parseInt(auForm.getCustId());
			                            System.out.println("cid_val====>>>"+cid_val);
			                            System.out.println("auForm.getCustId()&&&&=====>>"+auForm.getCustId());
			                                self_acno=pgDelegate.checkAgCid(auForm.getCustId().toString());
			                                System.out.println("self accc no==="+self_acno);
			                                if(self_acno==-1)
			                                {
			                                	req.setAttribute("msg","Customer ID not found");
			                                	auForm.setAgName("");
			                                    auForm.setSelfAccName("");
			                                	auForm.setCustId("");
			                                }
			                                else if(self_acno==1)
			                                {
			                                	amo.setCid(Integer.parseInt(auForm.getCustId()));
			                                	setAgentUpdateDetails("Personal", req,pgDelegate, amo, auForm, null, null,0);
			                                    String self_name=pgDelegate.getCustomerName(auForm.getCustId().toString());
			                                    System.out.println("self_name==++====>>>>"+self_name);
			                                    auForm.setAgName(self_name);
			                                    auForm.setSelfAccName(self_name);
			                                    auForm.setSelfAcNo("");
			                                    
			                                }   
			                        }
			                        catch(RemoteException re){re.printStackTrace();}
			                    }
		                	}
						else if(auForm.getForward().equalsIgnoreCase("SelfAccNo"))
	                	{
							System.out.println("m in SelfAccount");
	                		accountobject=pgDelegate.getSelfAcntDetails(null,auForm.getSelfAccType(),Integer.parseInt(auForm.getSelfAcNo()),Date.getSysDate());
	                		System.out.println("accountobject======>>>"+accountobject);
	                		if(accountobject!=null)
	                        {     
	                            pers_cid=accountobject.getCid();
	                            pers_no_jt_hldr=accountobject.getNo_of_jt_hldr();
	                        }
	                		if(pers_no_jt_hldr!=0)
	                        {   
	                			req.setAttribute("msg","Enter The Acc No which does not have JointAccount Holder!");
	                			auForm.setSelfAcNo("");
	                        }
	                		if(auForm.getSelfAcNo().length()>0)
	                        {
	                            if(pers_cid!=Integer.parseInt(auForm.getCustId()))
	                            {
	                                req.setAttribute("msg","Enter self account number that matches cid!!");
	                                auForm.setSelfAcNo("");
	                            }
	                            else
	                            {
	                            	auForm.setSelfAccName(pgDelegate.getJointDetail(null, 0,amo.getPersonalAccType(), amo.getAccNo(), amo.getJtAccType(), amo.getJointAccNo(), 2));
	                            }
	                        }
	                		 if(accountobject==null)
	                         {
	                			 req.setAttribute("msg","Self Account Not Found");
	                			 auForm.setSelfAcNo("");
	                         }
	                		else if(accountobject.getAccStatus().equalsIgnoreCase("C"))
	                        {
	                            req.setAttribute("msg","Account Closed");
	                            auForm.setSelfAcNo("");
	                        }
	                        else if(accountobject.getVerified()==null)
	                        {
	                        	req.setAttribute("msg","Account to be verified");
	                        	auForm.setSelfAcNo("");
	                        }
	                        else if(accountobject.getFreezeInd().equalsIgnoreCase("T"))
	                        {
	                        	req.setAttribute("msg","Account Freezed!!");
	                        	auForm.setSelfAcNo("");
	                        }
	                        else if(accountobject.getAccStatus().equalsIgnoreCase("I"))
	                        {
	                        	req.setAttribute("msg","Account InOperative!!");
	                        	auForm.setSelfAcNo("");
	                        }
                         	
	                	}
						else if(auForm.getForward().equalsIgnoreCase("JointAccNo"))
	                	{
							System.out.println("M in Joint Account");
							accountobject=pgDelegate.getSelfAcntDetails(null,auForm.getJtAccType(),Integer.parseInt(auForm.getJtAcNo()),Date.getSysDate());
                            System.out.println("AccObj  "+accountobject);
                            if(accountobject!=null)
	                        {     
                               if(pers_cid!=accountobject.getCid())
                                {
                                    req.setAttribute("msg","Account Number does not match with cid, check");
                                    auForm.setJtAcNo("");
                                    auForm.setJtAccName("");
                                }
                                else
                                {
    	                            auForm.setSelfAccName(pgDelegate.getJointDetail(null, 0,amo.getPersonalAccType(), amo.getAccNo(), amo.getJtAccType(), amo.getJointAccNo(), 3));
                                   // obj_jt_acc_personal.showCustomer(accountobject.getCid());
                                }
                            
                            if(accountobject.getNo_of_jt_hldr()==0)
                            {
                                req.setAttribute("msg","Enter account number with a joint holder");
                                auForm.setJtAcNo("");
                            }
                            else if(accountobject.getNo_of_jt_hldr()==-1)
                            {   
                                req.setAttribute("msg","Account Number does not exist");
                                auForm.setJtAcNo("");
                            }   
                            
	                        }	
                            
                            
	                }
						 if(amo.getAgentNo()==-1){
							 req.setAttribute("msg","Agent Not Found");
						 	}
						
						 if(amo.getAccNo() == -2)
						 {
							 req.setAttribute("msg","Agent self/joint account does not exist");
						 }
						 
						 if(amo.getCloseInd()==19)
						 {
							 req.setAttribute("msg","Agent Closure not yet verified,cannot update");
						 }
						 
						 if(amo.getCloseInd()==9)
						 {
							 req.setAttribute("msg","Agent Opening not yet verified,cannot update");
						 }
						 
						 setAgentUpdateDetails(auForm.getDetails(), req,pgDelegate, amo, auForm, null, null,0);
						
					}
					if (auForm.getForward().equalsIgnoreCase("Update")) {
						System.out.println("Hi in submit mode-------------"+ auForm.getCustId());
						AgentMasterObject ag_obj = new AgentMasterObject();
						ag_obj.setAgentType(auForm.getAgType().toString());
						ag_obj.setAgentNo(Integer.parseInt(auForm.getAgNo()));
						ag_obj.setCid(Integer.parseInt(auForm.getCustId()));
						ag_obj.setAppointDate(auForm.getAppDate());
						System.out.println("DATE----------"+auForm.getAppDate());
						ag_obj.setAccType(auForm.getSelfAccType());
						ag_obj.setAccNo(Integer.parseInt(auForm.getSelfAcNo()));
						ag_obj.setJointAccType(auForm.getJtAccType());
						System.out.println("auForm.getJtAcNo()=========>>>"+auForm.getJtAcNo());
						ag_obj.setJointAccNo(Integer.parseInt(auForm.getJtAcNo()));
						ag_obj.setIntrAccType(auForm.getIntAccType());
						ag_obj.setIntrAccNo(Integer.parseInt(auForm.getIntAccNo()));
						ag_obj.setCloseIndicator(auForm.getCloseInd());
						ag_obj.setCloseDate(auForm.getCloseDate());
						if(auForm.getCloseInd().equalsIgnoreCase("CLOSED"))
							ag_obj.setCloseIndicator("C");
						else if(auForm.getCloseInd().equalsIgnoreCase("OPEN"))
							ag_obj.setCloseIndicator("O");

						int r = pgDelegate.updateAgentMaster(ag_obj);
						System.out.println("r value==========>>>"+r);
						
						if(r==2){
							req.setAttribute("msg","Updated Successfully");
						}else{
							req.setAttribute("msg","Error During Updation");
						}

					}

					return map.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/PUpdateMenu")){
		 	
			PUpdateForm pUpdateForm =(PUpdateForm)form;
			
			System.out.println("hi i am here" + map.getPath());
			pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
		try {
			System.out.println("===" + pUpdateForm.getPageid());
			System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(pUpdateForm.getPageid()));
			System.out.println("((((((((((((((((()))))))))))))))))))------");

			if (MenuNameReader.containsKeyScreen(pUpdateForm.getPageid())) {
				path = MenuNameReader.getScreenProperties(pUpdateForm.getPageid());
				System.out.println("insideExecutre path" + path);
				setPygmyUpdateInitParams(req, path, pgDelegate);
				req.setAttribute("pageNum", pUpdateForm.getPageid().trim());
				pUpdateForm.setPay_ac_no("0");
				pUpdateForm.setPyg_name("");
				pUpdateForm.setCust_id("0");
				pUpdateForm.setForward("");
				pUpdateForm.setAg_no("0");
				pUpdateForm.setAg_Name("");
				pUpdateForm.setOp_date("");
				pUpdateForm.setAc_Status("");
				pUpdateForm.setLoan_avail("");
				pUpdateForm.setLoan_ac_No("0");
				pUpdateForm.setPay_ac_no("0");
				pUpdateForm.setPay_ac_name("");
				pUpdateForm.setClose_date("");
				pUpdateForm.setPyg_no("0");
				pUpdateForm.setDetails("SELECT");
				System.out.println("++++++++++++++++++++++++-----------");
				return map.findForward(ResultHelp.getSuccess());
			} else {
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} catch (Exception e) {
			path = MenuNameReader.getScreenProperties("0000");
			e.printStackTrace();
			setErrorPageElements("Unable to process the reqest", req, path);
			return map.findForward(ResultHelp.getSuccess());
		}	  
		}	
			
			
			
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/PUpdate")){
		 	
			PUpdateForm pUpdateForm =(PUpdateForm)form;
			
			System.out.println("#############=" + pUpdateForm.getPageid().trim());
			pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			try {
				req.setAttribute("pageNum", pUpdateForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(pUpdateForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(pUpdateForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(pUpdateForm.getPageid());
					System.out.println(path);
					setPygmyUpdateInitParams(req, path, pgDelegate);
					
					System.out.println(pgDelegate);
					System.out.println(pUpdateForm.getForward()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********"+pUpdateForm.getCust_id());
					
					
					if(!pUpdateForm.getPyg_Type().equalsIgnoreCase("SELECT")&& pUpdateForm.getPyg_no().trim().length()!=0) 
					{
						PygmyMasterObject pm_Obj=null;
						AgentMasterObject amo=null;
						pm_Obj = pgDelegate.getPyAccMasterDetails(Integer.parseInt(pUpdateForm.getPyg_no()),pUpdateForm.getPyg_Type());
						if(pm_Obj.getAccStatus()!=null){
						amo=pgDelegate.getAgentDetails(pm_Obj.getAgentType(), Integer.parseInt(pm_Obj.getAgentNo()),pm_Obj.getAccOpenDate());
						}
					 if(pUpdateForm.getForward().equalsIgnoreCase("PygmyAccNo")){
						 if(pm_Obj.getAccStatus()==null)
							{
								req.setAttribute("msg","Account Not Found");
							}
						 else if(pm_Obj.getAccStatus()!=null)
						{
							req.setAttribute("PygmyUpdateDetails", pm_Obj);
							pUpdateForm.setPyg_name(pm_Obj.getPay_acc_name());
							String cust_id=Integer.toString(pm_Obj.getCid());
							pUpdateForm.setCust_id(cust_id);
							pUpdateForm.setAg_Type(pm_Obj.getAgentType());
							pUpdateForm.setAg_no(pm_Obj.getAgentNo());
							pUpdateForm.setAg_Name(pm_Obj.getAgentName());
							pUpdateForm.setOp_date(pm_Obj.getAccOpenDate());
							if(pm_Obj.getAccStatus().equalsIgnoreCase("O"))
							{
								pUpdateForm.setAc_Status("OPEN");
							}else if(pm_Obj.getAccStatus().equalsIgnoreCase("C"))
							{
								pUpdateForm.setAc_Status("CLOSE");
							}
							else  if(pm_Obj.getAccStatus().equalsIgnoreCase("V"))
							{
								pUpdateForm.setAc_Status("OPEN");
							}
							pUpdateForm.setLst_int_date(pm_Obj.getLastIntrDate());
							if((pm_Obj.getLnAvailed().equalsIgnoreCase("T"))){
								pUpdateForm.setLoan_avail("YES");
							}
							else
							{
								pUpdateForm.setLoan_avail("NO");
							}
							pUpdateForm.setLoan_ac_Type(pm_Obj.getLnAccType());
							String LoanAccNo=Integer.toString(pm_Obj.getLnAccNo());
							pUpdateForm.setLoan_ac_No(LoanAccNo);
							String pay_mode=pm_Obj.getPayMode();
							if(pay_mode!=null){
								if(pay_mode.equalsIgnoreCase("T"))
									pUpdateForm.setPay_mode("Transfer");
								else if(pay_mode.equalsIgnoreCase("C"))
									pUpdateForm.setPay_mode("Cash");
								else
									pUpdateForm.setPay_mode("Pay Order");
							}
							pUpdateForm.setPay_ac_Type(pm_Obj.getPayAccType());
							String payAccNo=Integer.toString(pm_Obj.getPayAccNo());
							pUpdateForm.setPay_ac_no(payAccNo);
							pUpdateForm.setPay_ac_name(pm_Obj.getPay_acc_name());
							pUpdateForm.setClose_date(pm_Obj.getAccCloseDate());
							
							setPygmyUpdateDetails(pUpdateForm.getDetails(), req,pgDelegate, pm_Obj, pUpdateForm, null, null,amo,0);
							
						}
						else
						{
							req.setAttribute("msg","Pygmy Account Not Found!");
						}
						 
					 }
					 if(pUpdateForm.getForward().equalsIgnoreCase("CustId")){
						 if(pUpdateForm.getCust_id().length()>0)
						 {
							 //cid_val=Integer.parseInt(pUpdateForm.getCust_id());
	                            System.out.println("cid_val====>>>"+cid_val);
	                                self_acno=pgDelegate.checkAgCid(pUpdateForm.getCust_id().toString());
	                                System.out.println("self accc no==="+self_acno);
	                                if(self_acno==-1)
	                                {
	                                	req.setAttribute("msg","Customer ID not found");
	                                	pUpdateForm.setCust_id("");
	                                }
	                                else if(self_acno==1)
	                                {
	                                	pm_Obj.setCid(Integer.parseInt(pUpdateForm.getCust_id()));
	                                	setPygmyUpdateDetails("Personal", req,pgDelegate, pm_Obj, pUpdateForm, null, null,amo,0);
	                                	req.setAttribute("personalInfo", pgDelegate.getCustomer(pm_Obj.getCid()));
	                                }
							 
						 }
					 
					 }
					 
					 if(pUpdateForm.getForward().equalsIgnoreCase("AgentNo"))
					 {
						 AgentMasterObject amo1=pgDelegate.getAccountMaster(pUpdateForm.getAg_Type(), Integer.parseInt(pUpdateForm.getAg_no()),pm_Obj.getAccOpenDate());
						 if(amo1.getAgentNo()==-1)
						 {
							 req.setAttribute("msg", "Agent Not Found");
							 pUpdateForm.setAg_no("");
						 }
					 }
					 
					 if(pUpdateForm.getForward().equalsIgnoreCase("PayAccNo"))
					 {
						 if(pUpdateForm.getPay_ac_no().length()>0 && Integer.parseInt(pUpdateForm.getPay_ac_no())!=0)
					        {
					            AccountObject accountobject=null;
					            accountobject=pgDelegate.getSelfAcntDetails(null,String.valueOf(pUpdateForm.getPay_ac_Type()),Integer.parseInt(pUpdateForm.getPay_ac_no()),Date.getSysDate());
					            System.out.println("accountobject-==========>>"+accountobject);
					            
					            if(accountobject==null)
					            {
					                req.setAttribute("msg","Pay Account Not Found");
					                pUpdateForm.setPay_ac_no("");
					            }
					            else if(accountobject.getFreezeInd().equalsIgnoreCase("T")){
					            	req.setAttribute("msg","Account Freezed!!");
					            	pUpdateForm.setPay_ac_no("");
					               					            }
					            else if(accountobject.getAccStatus().equalsIgnoreCase("I")){
					            	req.setAttribute("msg","Account InOperative!!");
					            	pUpdateForm.setPay_ac_no("");
					            }
					            else if(accountobject.getAccStatus().equalsIgnoreCase("C")){
					            	req.setAttribute("msg","Account Closed");
					            	pUpdateForm.setPay_ac_no("");
					            }
					            else if(accountobject.getVerified()==null){
					            	req.setAttribute("msg","Account to be verified");
					            	pUpdateForm.setPay_ac_no("");
					                
					            }
					            else {
					                if(accountobject.getAccname().length()>30)
					                	pUpdateForm.setPay_ac_name(accountobject.getAccname().substring(0,30));
					                else
					                	pUpdateForm.setPay_ac_name(accountobject.getAccname());
					            }
					        }
					 }
					 setPygmyUpdateDetails(pUpdateForm.getDetails(), req,pgDelegate, pm_Obj, pUpdateForm, null, null,amo,0);
					 if(pUpdateForm.getDetails().equalsIgnoreCase("Nominee"))
					   {
						req.setAttribute("enable", "enable");
						PygmyDelegate pydell=PygmyDelegate.getPygmyDelegateInstance();
						PygmyMasterObject pmObj1=pydell.getPygmyDetails(pUpdateForm.getPyg_Type(),Integer.parseInt(pUpdateForm.getPyg_no()));
						if(pmObj1.getNomineeNo()==0){
							req.setAttribute("msg","No nominee details");	
						}
						else if(pmObj1.getNomineeNo()>=0){
							NomineeObject[] nom2=pydell.getNominee(pmObj1.getNomineeNo());
							if(nom2.length!=0){
								pUpdateForm.setCidis(String.valueOf(nom2[0].getCustomerId()));
								pUpdateForm.setNomname(nom2[0].getNomineeName());
								pUpdateForm.setDob(nom2[0].getNomineeDOB());
								pUpdateForm.setGender(String.valueOf(nom2[0].getSex()));
								pUpdateForm.setAddress(nom2[0].getNomineeAddress());
								pUpdateForm.setRel_ship(nom2[0].getNomineeRelation());
								pUpdateForm.setPercentage(String.valueOf(nom2[0].getPercentage()));
							}
						}
					   }
					}
					else
					{
						req.setAttribute("msg","select Proper Accounttype");
					}
					 
					
				       if(pUpdateForm.getForward().equalsIgnoreCase("Update")){
							System.out.println("======UPDATE MODE======");
							
							PygmyMasterObject pmObj=new PygmyMasterObject();
							pmObj.setAccType(pUpdateForm.getPyg_Type());
							pmObj.setAccNo(Integer.parseInt(pUpdateForm.getPyg_no()));
							pmObj.setName(pUpdateForm.getPyg_name());
							pmObj.setCid(Integer.parseInt(pUpdateForm.getCust_id()));
							
							pmObj.setAgentType(pUpdateForm.getAg_Type());
							pmObj.setAgentNo(pUpdateForm.getAg_no());
							pmObj.setAgentName(pUpdateForm.getAg_Name());
							pmObj.setAccOpenDate(pUpdateForm.getOp_date());
							pmObj.setAccStatus(pUpdateForm.getAc_Status());
							pmObj.setLastIntrDate(pUpdateForm.getLst_int_date());
							if(pUpdateForm.getLoan_avail().equalsIgnoreCase("YES"))
								pmObj.setLnAvailed("T");
							else if(pUpdateForm.getLoan_avail().equalsIgnoreCase("NO"))
								pmObj.setLnAvailed("F");
							pmObj.setLnAccType(pUpdateForm.getLoan_ac_Type());
							pmObj.setLnAccNo(Integer.parseInt(pUpdateForm.getLoan_ac_No()));
							if (pUpdateForm.getPay_mode().equalsIgnoreCase("Cash")) {
								pmObj.setPayMode(pUpdateForm.getPay_mode());
								pmObj.setPayAccType("NULL");
								pmObj.setPayAccNo(0);
							} else if (pUpdateForm.getPay_mode().equalsIgnoreCase("Transfer")) {
								pmObj.setPayMode(pUpdateForm.getPay_mode());
								pmObj.setPayAccType(pUpdateForm.getPay_ac_Type());
								pmObj.setPayAccNo(Integer.parseInt(pUpdateForm.getPay_ac_no()));
							}
							pmObj.setPay_acc_name(pUpdateForm.getPay_ac_name());
							pmObj.setAccCloseDate(pUpdateForm.getClose_date());

							int upPyg=pgDelegate.updatePygmyMaster(pmObj);
							
							if(upPyg!=0){
								req.setAttribute("msg","Updated Successfully!!!!!!!!!!!!");
							}else{
								req.setAttribute("msg","Error During Updation");
							}
							
						}
					
					/*else if(Integer.parseInt(pUpdateForm.getAg_no())!=0&&!pUpdateForm.getForward().equals("Update")){
						
	                	AgentMasterObject amo=pgDelegate.getAgentDetails(pUpdateForm.getAg_Type(),Integer.parseInt(pUpdateForm.getAg_no()),pUpdateForm.getOp_date());
	                	req.setAttribute("AgentInfo",amo);
	                	System.out.println("+++++++++----&&&&&&&&&------"+amo.getName());
					}*/
			
				}
				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/DailyRemittanceMenu")) 
		{
			DailyRemittanceForm drForm = (DailyRemittanceForm) form;
			System.out.println("hi i am here" + map.getPath());
			try 
			{
				System.out.println("===" + drForm.getPageid());
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(drForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");
				
				if(MenuNameReader.containsKeyScreen(drForm.getPageid())) 
				{
					path = MenuNameReader.getScreenProperties(drForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setDailyInitParams(req, path, pgDelegate);
					req.setAttribute("pageNum", drForm.getPageid().trim());
					drForm.setScrNo("0");
					drForm.setAmt("0");
					AgentMasterObject[] agMaster = pgDelegate.getAgentNos("1013001",0);
					//session.invalidate();
			  		System.out.println("<<<<<<------->>>>>"+agMaster);
			  		req.setAttribute("Disable",null);
			  		req.setAttribute("AgNum", agMaster);
					return map.findForward(ResultHelp.getSuccess());
				} 
				else 
				{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			}
			catch(Exception e) 
			{
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("" + e, req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} 
		else if(map.getPath().trim().equalsIgnoreCase("/Pygmy/DailyRemittance")) 
		{
			int oldscr=0;
			
			try 
			{
				DailyRemittanceForm drForm = (DailyRemittanceForm) form;
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(drForm.getPageid()));
				
				if(MenuNameReader.containsKeyScreen(drForm.getPageid())) 
				{
					path = MenuNameReader.getScreenProperties(drForm.getPageid());
					String[] acNum=null;
					String[] acName=null;
					String addRow = null;
					String[] preBalance = null;
					String[] remiAmt=null;
					String acType=null;
					double totalremi=0.0;
					String[] use_id;
					use_id=req.getParameterValues("update");
					
					
					if(!(drForm.getAgType().equalsIgnoreCase("SELECT")))
					{
						
						String method=req.getParameter("method");
						acNum=req.getParameterValues("AccountNo");
						if(acNum!=null)
						{
							for(int i=0;i<acNum.length;i++)
							{
								System.out.println("---A/c Number-before--->"+acNum[i]);
							}	
						}
						else
						{
							System.out.println("---A/c Number-before--->"+acNum);
						}
						System.out.println("method==============>>>>>>>>>>>"+method);
						if(use_id!=null){
							for(int i=0;i<use_id.length;i++){
								System.out.println("value of use_id--->"+use_id[i]);
							}
						}
						AgentMasterObject[] agMaster = pgDelegate.getAgentNos(drForm.getAgType(),0);
						req.setAttribute("AgNum", agMaster);
						
						if(drForm.getForward().equalsIgnoreCase("agentno"))
						{
							if(Integer.parseInt(drForm.getAgNo())!=0 && drForm.getAgName()!=null) 
							{
								String name = pgDelegate.getAgentNames(drForm.getAgType(), Integer.parseInt(drForm.getAgNo()));
								req.setAttribute("Name", name);
								System.out.println("---------=======" + name);
								String coll_date=Validations.checkDate(pgDelegate.prevWorkingDay(Date.getSysDate()));
								req.setAttribute("COLLDATE",coll_date);
								System.out.println("------>>>>>>>>------"+coll_date);
								
							}
						}
						else if(drForm.getForward().equalsIgnoreCase("all"))
						{
							if((Integer.parseInt(drForm.getAgNo()))!=0)
							{
								setDailyRemiValues(pgDelegate,req,drForm);
							}
							
							
						}
						else if(drForm.getForward().equalsIgnoreCase("selected"))
						{
							if((Integer.parseInt(drForm.getAgNo()))!=0)
							{	
								List drList = new ArrayList();
								
								Map m=new TreeMap();
								System.out.println("Creating map in all");
								m.put("update",1);
								m.put("AccountType","PD");
								m.put("AccountNo","");
								m.put("Name","");
								m.put("PrevBal",0.0);
								m.put("RemiAmt",0.0);
								drList.add(m);
								session.setAttribute("DailyRemiList", drList);//to keep the last data
								req.setAttribute("DailyRemiList", drList);
							}
						}	
						else if(drForm.getForward().equalsIgnoreCase("scrollno"))
						{	
							if(Integer.parseInt(drForm.getScrNo())!=0)
							{
								Object[] scrHsh=new Object[5]; 
								scrHsh= pgDelegate.getScrollDetails(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()),Date.getSysDate(),0);
								if(scrHsh!=null)
								{ 
									for(int i=0;i<scrHsh.length;i++)
									{
										Object scrNo=scrHsh[0];
										Object scrAmt=scrHsh[4];

										System.out.println("--ScrollNo-->"+scrHsh[0]);
										System.out.println("--ScrollAmt-->"+scrHsh[4]);
										
										if(scrHsh[0]!=null)
										{
											try{
											Object[] scroll_obj=pgDelegate.checkScrollAttached(Integer.parseInt(scrHsh[0].toString()),Validations.checkDate(drForm.getRemiDate()),String.valueOf(drForm.getAgType()),String.valueOf(drForm.getAgNo()),0);
											
												if(scroll_obj!=null)
												{
													System.out.println("Inside check scroll not null");
													drForm.setScrNo(scrNo.toString());
													drForm.setAmt(scrAmt.toString());
												}
												else
												{
													req.setAttribute("msg","Invalid Scroll Number");
													drForm.setScrNo("0");
												}
											
											}
											catch (ScrollNotFoundException e) 
											{
												req.setAttribute("msg","Invalid Scroll Number");
												drForm.setScrNo("0");
											}
											
										}
										else
										{
											req.setAttribute("msg","Invalid Scroll Number");
										 
										  drForm.setScrNo("0");
										}
									}
								 }	
		    				  }	
						}
						else if(drForm.getForward().equalsIgnoreCase("scrollnoverify"))
						{
							System.out.println("Inside Scroll Verify");
							
						//	pt=g_pygmyRemote.getAgentRemittance(String.valueOf(array_moduleobject_agents[combo_agent_type.getSelectedIndex()].getModuleCode()),array_agentmasterobject[combo_agent_no.getSelectedIndex()-1].getAgentNumber(),string_collection_date,flag);
			                
							
					  		try
		            		{
		                		Object[] scrHsh=new Object[5]; 
		                		
		                		scrHsh= pgDelegate.getScrollDetails(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()),Date.getSysDate(),1);
			                	
		                		System.out.println("===scrHsh===>"+scrHsh);
		                		
								if(scrHsh!=null)
								{ 
									for(int i=0;i<scrHsh.length;i++)
									{	
										Object scrNo=scrHsh[0];
										Object scrAmt=scrHsh[4];
										if(scrNo!=null){
										drForm.setScrNo(scrNo.toString());
										drForm.setAmt(scrAmt.toString());
										}
									}
								 }
								else
								{
									req.setAttribute("msg","Scroll Number Not Found");
									drForm.setScrNo("0");
								}
		            		}
					  		catch(Exception e)
					  		{
					  			e.printStackTrace();
					  		}	
					  	
					  		PygmyTransactionObject[] ptran=null;
					  		Object[] data=new Object[5];
		            	
					  		double amount=0;
					  		ptran=pgDelegate.getAgentRemittanceForUpdate(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()) ,Integer.parseInt(drForm.getScrNo().trim()),drForm.getCollDate().toString().trim(),drForm.getRemiDate().toString().trim());
					  		if(ptran==null || ptran.length==0)
					  		{
					  			req.setAttribute("msg","No Records To Verify For This Collection Date");
					  			
		            		}
					  		else if(ptran!=null || ptran.length!=0){
					  		Object[] scroll_obj=pgDelegate.checkScrollAttached(Integer.parseInt(drForm.getScrNo()),Validations.checkDate(drForm.getRemiDate()),String.valueOf(drForm.getAgType()),String.valueOf(drForm.getAgNo()),0);
					  			List drList = new ArrayList();
					  			
					  			for(int i=0;i<ptran.length;i++)
					  			{
					  				Map drMap=new TreeMap();
					  				System.out.println("Creating map");
					  				drMap.put("update",(i+1));
					  				drMap.put("AccountType",ptran[i].getAccType());
					  				drMap.put("AccountNo",ptran[i].getAccNo());
					  				drMap.put("Name",ptran[i].getName());
					  				drMap.put("PrevBal",ptran[i].getPrevBalance());
					  				drMap.put("RemiAmt",ptran[i].getTranAmt());
					  				drList.add(drMap);
					  			}
					  			 
					  			 session.setAttribute("DailyRemiList", drList);//to keep the last data
					  			 req.setAttribute("DailyRemiList", drList);
						}
						}
						
						
						List drList=(ArrayList)session.getAttribute("DailyRemiList"); 
						
							if(drForm.getForward().equalsIgnoreCase("Submit"))
							{
								System.out.println("Inside Submit--kkkkkkkkkk->");
								
								PygmyTransactionObject[] pygtrn=new PygmyTransactionObject[1]; 
								PygmyMasterObject pm_obj= new PygmyMasterObject();
								
								acType="1006001";
								acNum=req.getParameterValues("AccountNo");
								acName=req.getParameterValues("Name");
								preBalance=req.getParameterValues("PrevBal");
								remiAmt=req.getParameterValues("RemiAmt");
								if(acNum!=null)
								{
									for(int i=0;i<acNum.length;i++){
										System.out.println("--A/c Num-->"+acNum[i]);
										//System.out.println("--A/c Name-->"+acName[i]);
										System.out.println("--PreBal-->"+preBalance[i]);
										System.out.println("--RemiAmt--->"+remiAmt[i]);
									}	
								}
								else
								{
									System.out.println("--A/c Num-->"+acNum);
									System.out.println("--A/c Name-->"+acName);
									System.out.println("--PreBal-->"+preBalance);
									System.out.println("--RemiAmt--->"+remiAmt);
								}
								if(acNum!=null)
								{
									
									for(int i=0;i<acNum.length;i++){
									
									pm_obj=pgDelegate.getPygmyDetails(acType,Integer.parseInt(acNum[i]));
									
									if(pm_obj!=null)
									{
										if(pm_obj.getAccNo()==-1)
										{
											req.setAttribute("msg","Invalid Account");
											
										}
										else if(pm_obj!=null && pm_obj.getAccStatus().equalsIgnoreCase("C"))
										{
											req.setAttribute("msg","Account "+pm_obj.getAccNo()+" has Closed!!");
											
										}
										else if(pm_obj==null || !pm_obj.getAccStatus().equalsIgnoreCase("O"))
										{
											req.setAttribute("msg","Account "+ pm_obj.getAccNo() +" Not Verified");
											
											
										}
										else if(pm_obj.getAgentType().equals("PAG") && Integer.parseInt(pm_obj.getAgentNo())==Integer.parseInt(drForm.getAgNo()) && pm_obj.getAccNo()==(Integer.parseInt(acNum[i])))
				                        { 
											if(remiAmt!=null)
											{
												for(int j=0;j<remiAmt.length;j++)
												{
													totalremi = totalremi+Double.parseDouble(remiAmt[j]);
												}
												
												if(Double.parseDouble(drForm.getAmt())==totalremi)
												{	
													System.out.println("---pygtrn-->"+pygtrn.length);
													Object[][] pm=new Object[pygtrn.length][5];
													for(int k=0;i<pygtrn.length;i++)
													{
														pm[k][0]="1006001";
														pm[k][1]=acNum[i];
														pm[k][2]=pm_obj.getName();
														pm[k][3]=pm_obj.getPrevBalance();
														pm[k][4]=remiAmt[i];
													}
										 
													int r=0;
													
													r = pgDelegate.storeDailyRemittance(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()),pm,Validations.checkDate(drForm.getCollDate()),Integer.parseInt(drForm.getScrNo()),Integer.parseInt(drForm.getScrNo()),tml,user,Date.getSysDate(),Date.getSysTime(),byte_update);
												
													if(r==1)
													{
														
														req.setAttribute("msg","Completed Remittance");
													}
												
												}
												else
												{
													req.setAttribute("msg","Remittance Amount Doesn't Match With Scroll Amount");
													
												}
											}
											else
											{
												req.setAttribute("msg","Enter Remittance Amount");
												
											}
					                        
								        }
										else
										{
											
											req.setAttribute("msg","Pygmy Not belongs to this Agent");
											
										}
								
									}
								   }
								}
						}
						else if(drForm.getForward().equalsIgnoreCase("AddRow"))
						{
							Map m=new TreeMap();
							Map map_ac=(TreeMap)drList.get(0);
							System.out.println("map_accccccc----->"+map_ac);
							System.out.println("the size of list------>"+drList.size());
							String update=""+(drList.size()+1);
							System.out.println("the value of update----->"+update);
						
							m.put("update",update);
							m.put("AccountType","PD");
							m.put("AccountNo","");
							m.put("Name","");
							m.put("PrevBal",0.0);
							m.put("RemiAmt",0.0);
							drList.add(m);
								
							System.out.println("Size in add row --->"+drList.size());	
						}
						else if(drForm.getForward().equalsIgnoreCase("Verify"))
						{
								boolean allow_VE=false;   	
						        PygmyMasterObject pm_obj=null;
		                    	
		                    	PygmyTransactionObject[] ve_data_obj=pgDelegate.getAgentRemittance(drForm.getAgType() ,Integer.parseInt(drForm.getAgNo()),drForm.getCollDate(),true);
		                    if(ve_data_obj!=null){	
		                    	for(int b=0; b<ve_data_obj.length; b++)
		                        {	
		                            try 
		                            {
		                            	System.out.println("Ac_type :"+ve_data_obj[b].getAccType());
		                            	System.out.println("Ac No :"+ve_data_obj[b].getAccNo());
		                        		pm_obj=pgDelegate.getPygmyDetails(ve_data_obj[b].getAccType(),ve_data_obj[b].getAccNo());
									}
		                            catch(NumberFormatException e1) 
		                            {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
		                            
		                            
		                            if(pm_obj.getAccNo()==-1)
		                            {
		                            	allow_VE=false;
		                            	req.setAttribute("msg","Invalid Account");
		                                break;
		                            }
		                            else
		                            	allow_VE=true;
		                            
		                            if(pm_obj!=null && pm_obj.getAccStatus().equalsIgnoreCase("C"))
		                            {
		                            	allow_VE=false;
		                            	req.setAttribute("msg","Account "+pm_obj.getAccNo()+" has Closed!!");
		                                break;  
		                            }
		                            else
		                            	allow_VE=true;
		                            
		                            if(pm_obj==null || !pm_obj.getAccStatus().equalsIgnoreCase("O"))
		                            {
		                            	allow_VE=false;
		                            	req.setAttribute("msg","Account Not Verified");
		                                break;
		                            }
		                            else
		                            	allow_VE=true;
		                            
		                            pm_obj=null;
		                        }
						}//=> end of for loop.
		                    	
		                    	int trn_status=pgDelegate.getRefInd(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()),drForm.getCollDate(),Integer.parseInt(drForm.getScrNo()));
		                    	
		                    	System.out.println("Trn Status => "+trn_status);
		                    	if(trn_status==2)
		                    	{
		                    	  allow_VE=false;
		                    	  req.setAttribute("msg","Transaction Under Updation Mode!\n Can not Verify.");
		                    	}
		                        else
		                    	  allow_VE=true;	
		                    	
		                    	if(allow_VE==true)
		                    	{
		                        		System.out.println("Before VerifyRemittance In Action");
		                            	int i=pgDelegate.verifyRemittance(drForm.getAgType(),Integer.parseInt(drForm.getAgNo()),drForm.getCollDate(),Integer.parseInt(drForm.getScrNo()),user,tml,Date.getSysDate(),Date.getSysTime());
		                                System.out.println("Veriication started..."+i);
		                                
		                               
		                                if(i>0)
		                                {	            	
		                                	req.setAttribute("msg","Verified");
                           
		                                }
		                                else
		                                	req.setAttribute("msg","Error in Verification!");
		                        	//=> end of Confirmation Dialog
		                    	}
		                    	
						}
						else if(drForm.getForward().equalsIgnoreCase("Clear"))
						{
							drList.clear();
							drForm.setScrNo("0");
							drForm.setAmt("0.0");
							drForm.setAgType("SELECT");
							drForm.setAgName("");
						}
												
						req.setAttribute("DailyRemiList",drList);
						session.setAttribute("DailyRemiList",drList);
						System.out.println("the page id is "+path);	
						
						/*if(!(drForm.getSelected().equalsIgnoreCase("")))
						{
							if(drForm.getSelected().equalsIgnoreCase("all"))
							{
								addRow="ADDROW";
							}
						}*/
				}	
				req.setAttribute("ADDROW",addRow);	
				setDailyInitParams(req, path, pgDelegate);
				return map.findForward(ResultHelp.getSuccess());
			}
			else 
			{
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
			}
		}
		catch(Exception e) 
		{
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
		}
	}	

		
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentCommissionMenu")) {
			AgentCommissionForm acForm = (AgentCommissionForm) form;
			System.out.println("hi i am here" + map.getPath());
			try {
				System.out.println("===" + acForm.getPageid());
				
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(acForm.getPageid()));
				
				if (MenuNameReader.containsKeyScreen(acForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(acForm.getPageid());
					System.out.println("insideExecutre path" + path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					setAgentCommInitParams(req, path, pgDelegate);
					setAgCommValues(pgDelegate, req);
					req.setAttribute("pageNum", acForm.getPageid().trim());
					acForm.setValid("");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}		
		else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/AgentCommission")) {
			
			AgentCommissionForm acForm = (AgentCommissionForm) form;
			System.out.println("#############=" + acForm.getPageid().trim());
			boolean flag = false;
			try {
				req.setAttribute("pageNum", acForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(acForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(acForm.getPageid())) {
					acForm.setValid("");
					path = MenuNameReader.getScreenProperties(acForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					
					setAgentCommInitParams(req, path, pgDelegate);
					List agComList=(ArrayList)session.getAttribute("AdminList");
					String use_id;
					String method=req.getParameter("method");
					System.out.println("method=============>>>>"+method);
					/*use_id=req.getParameterValues("update").toString();
					System.out.println("The use_id issssssss --------------->"+use_id);*/
					
					if(method!=null){
						if(method.equalsIgnoreCase("Submit")){
							acForm.setValid("");
							//if(req.getParameterValues("update").toString()!=null){
							use_id=req.getParameterValues("update")!=null?req.getParameterValues("update").toString():null;
							//System.out.println("The use_id issssssss --------------->"+use_id);
							System.out.println("The use_id issssssss ---------3847------>");
							
						   PygmyRateObject pyg=new PygmyRateObject();  
							for(int i=0;i<agComList.size();i++){
								System.out.println("M inside loop");
								Map m=(TreeMap)agComList.get(i);
								String update=m.get("update").toString();
								System.out.println("update=========>>"+update);
								if(use_id!=null){
									System.out.println("use_id==========>>>>"+use_id);
									//if(use_id.equalsIgnoreCase(update)){
										System.out.println("use_id333333333==============>>>"+use_id);
										pyg=new PygmyRateObject();
										System.out.println("update=="+(i+1));
										m.put("update",req.getParameter("update"));
										String acType=req.getParameter("PAG");
										pyg.setAgentType(acType);
			
										System.out.println("The from date is "+req.getParameter("From"));
										
										pyg.setFromDate(req.getParameter("From"));
										pyg.setToDate(req.getParameter("To"));
										System.out.println("the to date is"+req.getParameter("To"));
										pyg.setMinAmt(Double.parseDouble(req.getParameter("MinAmount")));
										pyg.setMaxAmt(Double.parseDouble(req.getParameter("MaxAmount")));
										pyg.setCommissionRate(Double.parseDouble(req.getParameter("CommRate")));
										pyg.setSecurityDepRate(Double.parseDouble(req.getParameter("SecurityDepRate")));
										pyg.setDeUser(req.getParameter("DeUser"));
										pyg.setDeTml(req.getParameter("DeTml"));
										pyg.setDeDate(req.getParameter("DeDate"));
										//agComList.set(i,m);
									//}
								}
							}
							int insert_val=pgDelegate.insertCommissionRt(pyg, req.getParameter("DeTml"), req.getParameter("DeUser"), Date.getSysDate(), Date.getSysTime());
							System.out.println("insert_val========>>>112233"+insert_val);
							if(insert_val == 0){
								acForm.setValid("Cannot insert,CommissionRate already defined for this period");
								}
							else if(insert_val == 1)
							{
								acForm.setValid("Cannot insert,CommissionRate already defined for this period,go for updation");
							}
							else if(insert_val == 2)
							{
								acForm.setValid("Cannot insert for same agent,sorry!!!");
							}
							else
							{
								acForm.setValid("Successfully inserted");
							}
							setAgCommValues(pgDelegate, req);
						}
						//added by Mohsin for update
						else if(method!=null && method.equalsIgnoreCase("Update")){
							acForm.setValid("");
							//if(req.getParameterValues("update").toString()!=null){
							use_id=req.getParameterValues("update")!=null?req.getParameterValues("update").toString():null;
							//System.out.println("The use_id issssssss --------------->"+use_id);
							System.out.println("The use_id issssssss ---------3847------>");
							
						   PygmyRateObject pyg=new PygmyRateObject();  
							for(int i=0;i<agComList.size();i++){
								System.out.println("M inside loop");
								Map m=(TreeMap)agComList.get(i);
								String update=m.get("update").toString();
								System.out.println("update=========>>"+update);
								if(use_id!=null){
									System.out.println("use_id==========>>>>"+use_id);
									//if(use_id.equalsIgnoreCase(update)){
										System.out.println("use_id333333333==============>>>"+use_id);
										pyg=new PygmyRateObject();
										System.out.println("update=="+(i+1));
										m.put("update",req.getParameter("update"));
										String acType=req.getParameter("PAG");
										pyg.setAgentType(acType);
			
										System.out.println("The from date is "+req.getParameter("From"));
										
										pyg.setFromDate(req.getParameter("From"));
										pyg.setToDate(req.getParameter("To"));
										System.out.println("the to date is"+req.getParameter("To"));
										pyg.setMinAmt(Double.parseDouble(req.getParameter("MinAmount")));
										pyg.setMaxAmt(Double.parseDouble(req.getParameter("MaxAmount")));
										pyg.setCommissionRate(Double.parseDouble(req.getParameter("CommRate")));
										pyg.setSecurityDepRate(Double.parseDouble(req.getParameter("SecurityDepRate")));
										pyg.setDeUser(req.getParameter("DeUser"));
										pyg.setDeTml(req.getParameter("DeTml"));
										pyg.setDeDate(req.getParameter("DeDate"));
										//agComList.set(i,m);
									//}
								}
							}
							int insert_val=pgDelegate.CommissionRtUpdate(pyg,1);
							System.out.println("insert_val========>>>112233"+insert_val);
							
							if(insert_val == -1)
							{
								acForm.setValid("Rate already defined for this period");
							}
							
							else 
							{
								acForm.setValid("Successfully Upated");
							}
							setAgCommValues(pgDelegate, req);
						}
						//for deletion by Mohsin on 18.11.2009
						else if(method.equalsIgnoreCase("Delete")){
							acForm.setValid("");
							//if(req.getParameterValues("update").toString()!=null){
							use_id=req.getParameterValues("update")!=null?req.getParameterValues("update").toString():null;
							//System.out.println("The use_id issssssss --------------->"+use_id);
							System.out.println("The use_id issssssss ---------3847------>");
							
						   PygmyRateObject pyg=new PygmyRateObject();  
							for(int i=0;i<agComList.size();i++){
								System.out.println("M inside loop");
								Map m=(TreeMap)agComList.get(i);
								String update=m.get("update").toString();
								System.out.println("update=========>>"+update);
								if(use_id!=null){
									System.out.println("use_id==========>>>>"+use_id);
									//if(use_id.equalsIgnoreCase(update)){
										System.out.println("use_id333333333==============>>>"+use_id);
										pyg=new PygmyRateObject();
										System.out.println("update=="+(i+1));
										m.put("update",req.getParameter("update"));
										String acType=req.getParameter("PAG");
										pyg.setAgentType(acType);
			
										System.out.println("The from date is "+req.getParameter("From"));
										
										pyg.setFromDate(req.getParameter("From"));
										pyg.setToDate(req.getParameter("To"));
										System.out.println("the to date is"+req.getParameter("To"));
										pyg.setMinAmt(Double.parseDouble(req.getParameter("MinAmount")));
										pyg.setMaxAmt(Double.parseDouble(req.getParameter("MaxAmount")));
										pyg.setCommissionRate(Double.parseDouble(req.getParameter("CommRate")));
										pyg.setSecurityDepRate(Double.parseDouble(req.getParameter("SecurityDepRate")));
										pyg.setDeUser(req.getParameter("DeUser"));
										pyg.setDeTml(req.getParameter("DeTml"));
										pyg.setDeDate(req.getParameter("DeDate"));
										//agComList.set(i,m);
									//}
								}
							}
							int insert_val=pgDelegate.CommissionRtUpdate(pyg,2);
							System.out.println("insert_val========>>>112233"+insert_val);
							
							if(insert_val == 2)
							{
								acForm.setValid("Data Successfully Deleted");
								
							}
							
							else
							{
								acForm.setValid("Data could not be deleted");
							}
							setAgCommValues(pgDelegate, req);
						}
			
						
						else if(method.equalsIgnoreCase("AddRow")){
							Map m=new TreeMap();
							Map map_ac=(TreeMap)agComList.get(0);
							
							String update=""+(agComList.size()+1);
							
							m.put("update",update);
							m.put("AgentType","1013001");
							m.put("From","");
							m.put("To","");
							m.put("MinAmount",0.0);
							m.put("MaxAmount",0.0);
							m.put("CommRate",0.0);
							m.put("Security_Dep_Rate",0.0);
							m.put("DeUser",tml);
							m.put("DeTml",user);
							m.put("DeDate",Date.getSysDate()+" "+Date.getSysTime());
							agComList.add(m);
						}
						
						else if(method.equalsIgnoreCase("Reset")){
							//agComList=setAgCommValues(pgDelegate, req);
							//Changed on 18/09/2011
							
							path = MenuNameReader.getScreenProperties(acForm.getPageid());
							System.out.println("insideExecutre path" + path);
							pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
							setAgentCommInitParams(req, path, pgDelegate);
							setAgCommValues(pgDelegate, req);
							req.setAttribute("pageNum", acForm.getPageid().trim());
							acForm.setValid("");
							return map.findForward(ResultHelp.getSuccess());
						}
					}
					session.setAttribute("AdminList",agComList);
					
					req.setAttribute("AdminList",agComList);
					req.setAttribute("pageId",path);
					//setAgCommValues(pgDelegate, req);
					System.out.println("the page id is "+path);	
					return map.findForward(ResultHelp.getSuccess());

				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		}



		//Pygmtintrate


			else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyIntMenu")) {
				PygmyIntForm piForm = (PygmyIntForm) form;
				System.out.println("hi i am here" + map.getPath());
				pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
			   try {
				System.out.println("===" + piForm.getPageid());
				System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(piForm.getPageid()));
				System.out.println("((((((((((((((((()))))))))))))))))))------");

				if (MenuNameReader.containsKeyScreen(piForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(piForm.getPageid());
					System.out.println("insideExecutre path" + path);
					setPygmyCommInitParams(req, path, pgDelegate);
					setPgCommValues(pgDelegate, req);
					req.setAttribute("pageNum", piForm.getPageid().trim());

					System.out.println("++++++++++++++++++++++++-----------");
					return map.findForward(ResultHelp.getSuccess());
				} else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				path = MenuNameReader.getScreenProperties("0000");
				e.printStackTrace();
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}
		} else if (map.getPath().trim().equalsIgnoreCase("/Pygmy/PygmyInt")) {
			PygmyIntForm piForm = (PygmyIntForm) form;
			System.out.println("#############=" + piForm.getPageid().trim());

			try {
				req.setAttribute("pageNum", piForm.getPageid().trim());
				System.out.println("--------------------------------------------------------");
				System.out.println("insideExecutre"+ MenuNameReader.containsKeyScreen(piForm.getPageid()));
				if (MenuNameReader.containsKeyScreen(piForm.getPageid())) {
					path = MenuNameReader.getScreenProperties(piForm.getPageid());
					System.out.println(path);
					pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
					System.out.println(pgDelegate);
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*********");
					setPygmyCommInitParams(req, path, pgDelegate);
					
					List pgComList=(ArrayList)session.getAttribute("PygmyAdminList");
					String[] use_id;
					String method=req.getParameter("method");
					System.out.println("HI Suniths See Here----->"+method);
					
					String[] fdate=req.getParameterValues("FromDate");
					System.out.println("=============value fdate"+fdate);
					String[] tdate=req.getParameterValues("ToDate");
					String[] PFrom=req.getParameterValues("PeriodFrom");
					String[] PTo=req.getParameterValues("PeriodTo");
					String[] IntRate=req.getParameterValues("IntRate");
					String[] DeUser=req.getParameterValues("DeUser");
					String[] DeTml=req.getParameterValues("DeTml");
					String[] DeDate=req.getParameterValues("DeDate");
					 use_id=req.getParameterValues("update");
					System.out.println("The use_id is --------------->"+use_id);   
					
					/*if(method!=null){
						if(method.equalsIgnoreCase("Submit")){
							System.out.println("########## 1 #####");
						   PygmyRateObject[] pygInt=new PygmyRateObject[1];  
						   System.out.println("########## 2 #####"+pygInt);
							for(int i=0;i<pgComList.size();i++){
						   for(int l=0;l<use_id.length;l++){
						   for(int i=10;i<11;i++){
								System.out.println("########## 3 #####"+pygInt);
							Map m=(TreeMap)pgComList.get(i);
								String update=m.get("update").toString();
								if(use_id!=null){
									System.out.println("########## 4 #####"+pygInt);
									//if(use_id.equals(update)){
										pygInt[0]=new PygmyRateObject();
										System.out.println("update=="+(i));
										//m.put("update",req.getParameter("update"));
										String acType="PD";
										for(int l=0;l<use_id.length;l++){
											pygInt[0].setACType(acType);
											System.out.println("FDate is ---------"+fdate[l]);
											pygInt[0].setFromDate(fdate[l]);
											pygInt[0].setToDate(tdate[l]);
											pygInt[0].setPeriodFrom(Integer.parseInt(PFrom[l]));
											pygInt[0].setPeriodTo(Integer.parseInt(PTo[l]));
											pygInt[0].setIntRate(Double.parseDouble(IntRate[l]));
											pygInt[0].setDeUser(DeUser[l]);
											pygInt[0].setDeTml(DeTml[l]);
											pygInt[0].setDeDate(DeDate[l]);
											int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 0);
										}
										{
										pygInt[0].setACType(acType);
										pygInt[0].setFromDate(req.getParameter("FromDate"));
										pygInt[0].setToDate(req.getParameter("ToDate"));
										pygInt[0].setPeriodFrom(Integer.parseInt(req.getParameter("PeriodFrom")));
										pygInt[0].setPeriodTo(Integer.parseInt(req.getParameter("PeriodTo")));
										pygInt[0].setIntRate(Double.parseDouble(req.getParameter("IntRate")));
										pygInt[0].setDeUser(req.getParameter("DeUser"));
										pygInt[0].setDeTml(req.getParameter("DeTml"));
										pygInt[0].setDeDate(req.getParameter("DeDate"));
										//pgComList.set(i,m);
									}
								//}
							}
							int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 0);
						}*/
					if(method!=null){
						if(method.equalsIgnoreCase("Submit")){
							
							   
							pygInt=new PygmyRateObject[1];
							for(int i=0;i<pgComList.size();i++){
								Map m=(TreeMap)pgComList.get(i);
								String update=m.get("update").toString();
							for(int c=0;c<2;c++){
								System.out.println("At 2380");
								if(use_id!=null){
									System.out.println("The use_id is -------------2374-->"+use_id.length);
									
									for(int l=0;l<use_id.length;l++)
									{
										System.out.println("At 2389" + l);
													
										
										System.out.println("update=="+(l+1));
										m.put("update",req.getParameter("update"));
										
										System.out.println("FDate is "+fdate[l]);
										for(int k=0;k<1;k++){
											pygInt[k]=new PygmyRateObject();
											System.out.println("At 2400 in loop");
											String acType="PD";
											pygInt[k].setACType("1006001");
											System.out.println("FDate is ---------"+req.getParameter("FromDate"));
											pygInt[k].setFromDate(req.getParameter("FromDate"));
											pygInt[k].setToDate(req.getParameter("ToDate"));
											pygInt[k].setPeriodFrom(Integer.parseInt(req.getParameter("PeriodFrom")));
											pygInt[k].setPeriodTo(Integer.parseInt(req.getParameter("PeriodTo")));
											pygInt[k].setIntRate(Double.parseDouble(req.getParameter("IntRate")));
											pygInt[k].setDeUser(req.getParameter("DeUser"));
											pygInt[k].setDeTml(req.getParameter("DeTml"));
											pygInt[k].setDeDate(Date.getSysDate()+" "+Date.getSysTime());
										
										}
										//pgComist.set(i,m);
										//int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 0);
									}
									}
								}
							}
							
							int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml,Date.getSysTime(), 0);
							System.out.println("insert_val=============>>"+insert_val);
							if(insert_val==2)
							{
								req.setAttribute("msg","Successfully inserted!!");
							}
							else if(insert_val==1)
							{
								req.setAttribute("msg","Interest rate already defined for this account type and for the given period");
							}
							else if(insert_val==3)
							{
								req.setAttribute("msg","Successfully updated");
							}
							else if(insert_val==-3)
							{
								req.setAttribute("msg","Cannot Update");
							}
							else if(insert_val==5)
							{
								req.setAttribute("msg","Interest rate already defined for this account type and for the period");
							}
						}
						//for updation and deletion Added by Mohsin on 18.11.2009
						
						else if(method.equalsIgnoreCase("Update")){
							
							   
							 pygInt=new PygmyRateObject[1];
							 
							
								//if(req.getParameterValues("update").toString()!=null){
								//use_id=req.getParameterValues("update")!=null?req.getParameterValues("update").toString():null;
								//System.out.println("The use_id issssssss --------------->"+use_id);
								System.out.println("The use_id issssssss ---------3847------>");
								
							   PygmyRateObject pyg=new PygmyRateObject();  
								//for(int i=0;i<pgComList.size();i++){
									/*System.out.println("M inside loop");
									Map m=(TreeMap)pgComList.get(i);
									String update=m.get("update").toString();
									System.out.println("update=========>>"+update);*/
									if(use_id!=null){
										System.out.println("use_id==========>>>>"+use_id);
										//if(use_id.equalsIgnoreCase(update)){
											System.out.println("use_id333333333==============>>>"+use_id);
											
											pygInt[0]=new PygmyRateObject();
											System.out.println("At 2400 in loop");
											String acType="PD";
										pygInt[0].setACType("1006001");
										System.out.println("FDate is ---------"+req.getParameter("FromDate"));
										pygInt[0].setFromDate(req.getParameter("FromDate"));
										pygInt[0].setToDate(req.getParameter("ToDate"));
										pygInt[0].setPeriodFrom(Integer.parseInt(req.getParameter("PeriodFrom")));
										pygInt[0].setPeriodTo(Integer.parseInt(req.getParameter("PeriodTo")));
										pygInt[0].setIntRate(Double.parseDouble(req.getParameter("IntRate")));
										pygInt[0].setDeUser(req.getParameter("DeUser"));
										pygInt[0].setDeTml(req.getParameter("DeTml"));
										pygInt[0].setDeDate(Date.getSysDate()+" "+Date.getSysTime());
											
											/*adding above
											pyg=new PygmyRateObject();
											System.out.println("update=="+(i+1));
											m.put("update",req.getParameter("update"));
											acType=req.getParameter("PD");
											pyg.setAgentType(acType);
				
											System.out.println("The from date is "+req.getParameter("From"));
											
											pyg.setFromDate(req.getParameter("From"));
											pyg.setToDate(req.getParameter("To"));
											System.out.println("the to date is"+req.getParameter("To"));
											pyg.setMinAmt(Double.parseDouble(req.getParameter("MinAmount")));
											pyg.setMaxAmt(Double.parseDouble(req.getParameter("MaxAmount")));
											pyg.setCommissionRate(Double.parseDouble(req.getParameter("CommRate")));
											pyg.setSecurityDepRate(Double.parseDouble(req.getParameter("SecurityDepRate")));
											pyg.setDeUser(req.getParameter("DeUser"));
											pyg.setDeTml(req.getParameter("DeTml"));
											pyg.setDeDate(req.getParameter("DeDate"));
											//agComList.set(i,m);
										*/}
									
							//	}
									int insert_val=0;
									if(pygInt!=null){
								insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 1);
								System.out.println("insert_val========>>>112233"+insert_val);}
							/*for(int i=0;i<pgComList.size();i++){
								Map m=(TreeMap)pgComList.get(i);
								String update=m.get("update").toString();
							//for(int c=0;c<2;c++){
								System.out.println("At 2380");
								if(use_id!=null){
									System.out.println("The use_id is -------------2374-->"+use_id.length);
									
									for(int l=0;l<use_id.length;l++)
									{
										System.out.println("At 2389" + l);
													
										
										System.out.println("update=="+(l+1));
										m.put("update",req.getParameter("update"));
										
										System.out.println("FDate is "+fdate[l]);
										for(int k=0;k<1;k++){
											pygInt[k]=new PygmyRateObject();
											System.out.println("At 2400 in loop");
											String acType="PD";
										pygInt[k].setACType(piForm.getAc_Type());
										System.out.println("FDate is ---------"+fdate[l]);
										pygInt[k].setFromDate(fdate[l]);
										pygInt[k].setToDate(tdate[l]);
										pygInt[k].setPeriodFrom(Integer.parseInt(PFrom[l]));
										pygInt[k].setPeriodTo(Integer.parseInt(PTo[l]));
										pygInt[k].setIntRate(Double.parseDouble(IntRate[l]));
										pygInt[k].setDeUser(DeUser[l]);
										pygInt[k].setDeTml(DeTml[l]);
										pygInt[k].setDeDate(Date.getSysDate()+" "+Date.getSysTime());
										
										}
										//pgComist.set(i,m);
										int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 0);
									}
									}
								//}
							}
							String str=Date.getSysDate()+""+Date.getSysTime();
							System.out.println("str.length is----------"+str.length()+""+str);
							int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysDate()+" "+Date.getSysTime(), 1);
							System.out.println("insert_val=============>>"+insert_val);*/
							 if(insert_val==3)
							{
								req.setAttribute("msg"," Data Successfully updated");
							}
							else if(insert_val==-3)
							{
								req.setAttribute("msg","Data Could not be updated");
							}
							else if(insert_val==5)
							{
								req.setAttribute("msg","Interest rate already defined for this account type and for the period");
							}
						}
						
						else if(method.equalsIgnoreCase("Delete")){
							
							   
							 pygInt=new PygmyRateObject[1];
							for(int i=0;i<pgComList.size();i++){
								Map m=(TreeMap)pgComList.get(i);
								String update=m.get("update").toString();
							for(int c=0;c<2;c++){
								System.out.println("At 2380");
								if(use_id!=null){
									System.out.println("The use_id is -------------2374-->"+use_id.length);
									
									for(int l=0;l<use_id.length;l++)
									{
										System.out.println("At 2389" + l);
													
										
										System.out.println("update=="+(l+1));
										m.put("update",req.getParameter("update"));
										
										System.out.println("FDate is "+fdate[l]);
										for(int k=0;k<1;k++){
											pygInt[k]=new PygmyRateObject();
											System.out.println("At 2400 in loop");
											String acType="PD";
										pygInt[k].setACType("1006001");
										System.out.println("FDate is ---------"+fdate[l]);
										pygInt[k].setFromDate(req.getParameter("FromDate"));
										pygInt[k].setToDate(req.getParameter("ToDate"));
										pygInt[k].setPeriodFrom(Integer.parseInt(req.getParameter("PeriodFrom")));
										pygInt[k].setPeriodTo(Integer.parseInt(req.getParameter("PeriodTo")));
										pygInt[k].setIntRate(Double.parseDouble(req.getParameter("IntRate")));
										pygInt[k].setDeUser(req.getParameter("DeUser"));
										pygInt[k].setDeTml(req.getParameter("DeTml"));
										pygInt[k].setDeDate(Date.getSysDate()+" "+Date.getSysTime());
										
										}
										//pgComist.set(i,m);
										//int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysTime(), 2);
									}
									}
								}
							}
							String str=Date.getSysDate()+""+Date.getSysTime();
							System.out.println("str.length is----------"+str.length()+""+str);
							int insert_val=pgDelegate.insert_updatePygmyRate(pygInt, user, tml, Date.getSysDate()+" "+Date.getSysTime(),2);
							System.out.println("insert_val=============>>"+insert_val);
							 if(insert_val==4)
							{
								req.setAttribute("msg"," Data Successfully Deleted");
							}
							else if(insert_val==-3)
							{
								req.setAttribute("msg","Data Could not be updated");
							}
							else 
							{
								req.setAttribute("msg","Data Could not be deleted");
							}
						}
						
						else if(method.equalsIgnoreCase("AddRow")){
							Map m=new TreeMap();
							Map map_ac=(TreeMap)pgComList.get(0);
							
							String update=""+(pgComList.size()+1);
							
							
								m.put("update",update);
								m.put("PygmyType","PD");
								m.put("FromDate","");
								m.put("ToDate","");
								m.put("PeriodFrom",0);
								m.put("PeriodTo",0);
								m.put("IntRate",0.0);
								m.put("DeUser",tml);
								m.put("DeTml",user);
								m.put("DeDate",Date.getSysDate()+" "+Date.getSysTime());
								pgComList.add(m);
								
						}
						
						else if(method.equalsIgnoreCase("Reset")){
							pgComList=setPgCommValues(pgDelegate, req);
						}
					}
					session.setAttribute("PygmyAdminList",pgComList);
					
					req.setAttribute("PygmyAdminList",pgComList);
					req.setAttribute("pageId",path);
					System.out.println("the page id is "+path);	
					
					
					return map.findForward(ResultHelp.getSuccess());
				}

				else {
					path = MenuNameReader.getScreenProperties("0000");
					setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
					return map.findForward(ResultHelp.getSuccess());
				}
			} catch (Exception e) {
				e.printStackTrace();
				path = MenuNameReader.getScreenProperties("0000");
				setErrorPageElements("Unable to process the reqest", req, path);
				return map.findForward(ResultHelp.getSuccess());
			}



		
	
		}
	 	return map.findForward(ResultHelp.getSuccess());
				
	}	
	// ********************** All Common Methods ****************************

	private void setAgentDetails(String details, HttpServletRequest req,PygmyDelegate pgDelegate, AgentMasterObject amObj,AgentOpeningForm agform, SignatureInstructionForm siForm,AccountObject acObj, int signFlag) throws Exception {
		System.out.println("details==" + details);
		if (details.equalsIgnoreCase("Personal")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			req.setAttribute("flag",perPath);
			if (amObj != null) {
				req.setAttribute("personalInfo", pgDelegate.getCustomer(amObj.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				}
			else if (acObj != null) {
				System.out.println("********perObject.getCid()========="+ acObj.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acObj.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());

			}
			System.out.println("yes flag is set");
		}else if (details.equalsIgnoreCase("Introducer Type")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			AccountObject acntObject = pgDelegate.getIntroducerAcntDetails(agform.getIntroduceractypeno(), Integer.parseInt(agform.getIntroducerAcno()));
			System.out.println("acntObject.getCid()==" + acntObject);
			
			if (acntObject != null) {
				System.out.println("acntObject.getCid()=="+ acntObject.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acntObject.getCid()));
			} else {
				req.setAttribute("personalInfo", new CustomerMasterObject());
			}
			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
		}

		
		/*if(agform.getDetails().trim().equalsIgnoreCase("Signature Ins")){
			String perPath=MenuNameReader.getScreenProperties("0004");
			System.out.println("path=="+perPath);
			req.setAttribute("pageNum",agform.getPageid()); 
			System.out.println("pageNum======="+agform.getPageid());
			System.out.println(agform.getAgentnum()+"\t"+agform.getAgentcode());
			SignatureInstructionObject[] signObject=pgDelegate.getSignature(Integer.parseInt(agform.getAgentnum()),agform.getAgentcode());
			System.out.println("sign=="+signObject);
			
			req.setAttribute("SignValues", signObject);
			req.setAttribute("AgentForm", agform);
			req.setAttribute("SignInstActnPath","/Common/sign");
			
			if(signObject!=null){
        		req.setAttribute("signInfo",signObject);
            }else{
        		req.setAttribute("signInfo",signObject);
        		
        		
        	}
            req.setAttribute("flag",perPath);
            req.setAttribute("panelName", CommonPanelHeading.getSignature());
        
		}*/
		
		else if (details.trim().equalsIgnoreCase("Joint Holders")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("perpath\\\\\\\\\\\\\\\\\\\\\\"+perPath);
			AgentMasterObject agMaster = null;

			if (amObj != null) {
				System.out.println("AgntMaster =" + amObj.getJtAccType()+ "\t" + amObj.getJointAccNo());
				agMaster = pgDelegate.getPAGJointAccount(amObj.getJtAccType(), amObj.getJointAccNo());
			} else {
				if (agform != null) {
					System.out.println("agform.getJointactypeno()"+agform.getJointactypeno());
					System.out.println("agform.getJointAcno()"+agform.getJointAcno());
					agMaster = pgDelegate.getPAGJointAccount(agform.getJointactypeno(), Integer.parseInt(agform.getJointAcno()));
					System.out.println("agMaster=== in action==>>>"+agMaster);
					System.out.println("cid=== in action==>>>"+agMaster.getCid());
				}
			}
			req.setAttribute("Joint Holders", agMaster);
			req.setAttribute("flag",perPath);
			/*int cid[] = agMaster.getJt_cids();
			System.out.println("acntObject.getCid()==" + agMaster.getJt_cids());

			System.out.println("cid[0].length==" + cid.length);
			System.out.println("cid[0]==" + cid[0]);*/
			

			if (agMaster != null) 
			{
				int cid[] = agMaster.getJt_cids();
				System.out.println("acntObject.getCid()==" + cid[0]);
				req.setAttribute("personalInfo", pgDelegate.getCustomer(cid[0]));
			} else {
				req.setAttribute("personalInfo",null);
			}

			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getJointHolder());
		}
	}

	private void setErrorPageElements(String exception, HttpServletRequest req,String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);

	}

	private void setAgentOpeningInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("SelfAcType", pgDelegate.getComboElements(0));
			req.setAttribute("IntroAcType", pgDelegate.getComboElements(1));
			req.setAttribute("JointAcType", pgDelegate.getComboElements(2));
			req.setAttribute("AgentAcType", pgDelegate.getComboElements(3));
			req.setAttribute("Details", pgDelegate.getDetailsComboElements());
		} catch (Exception e) {
			throw e;
		}
	}
	//"/Pygmy/AgentOpening?tabPaneHeading=Joint Holders&pageid="+ agform.getPageid() + "&agentcode="+ agform.getAgentcode() + "&agentnum="+ agform.getAgentnum() + "&agentname="+ agform.getAgentname() + "&selfactypeno="+ agform.getSelfactypeno() + "&selfAcno="+ agform.getSelfAcno() + "&selfname="+ agform.getSelfname() + "&date=" + agform.getDate()+ "&jointactypeno=" + agform.getJointactypeno()+ "&jointAcno=" + agform.getJointAcno() + "&jointname="+ agform.getJointname() + "&introduceractypeno="+ agform.getIntroduceractypeno() + "&introducerAcno="+ agform.getIntroducerAcno() + "&details="+ agform.getDetails(),"/Pygmy/AgentOpening?tabPaneHeading=Signature Ins&defaultSignIndex=0&pageid="+ agform.getPageid() + "&agentcode="+ agform.getAgentcode() + "&agentnum="+ agform.getAgentnum() + "&agentname="+ agform.getAgentname() + "&selfactypeno="+ agform.getSelfactypeno() + "&selfAcno="+ agform.getSelfAcno() + "&selfname="+ agform.getSelfname() + "&date=" + agform.getDate()+ "&jointactypeno=" + agform.getJointactypeno()+ "&jointAcno=" + agform.getJointAcno() + "&jointname="+ agform.getJointname() + "&introduceractypeno="+ agform.getIntroduceractypeno() + "&introducerAcno="+ agform.getIntroducerAcno() + "&details="+ agform.getDetails()
	/*private void setTabbedPaneInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate, AgentOpeningForm agform) throws Exception {
		System.out.println(path);
		String pageActions[] = {"/Pygmy/AgentOpening?tabPaneHeading=Personal&pageid="+ agform.getPageid() + "&agentcode="+ agform.getAgentcode() + "&agentnum="+ agform.getAgentnum() + "&agentname="+ agform.getAgentname() + "&selfactypeno="+ agform.getSelfactypeno() + "&selfAcno="+ agform.getSelfAcno() + "&selfname="+ agform.getSelfname() + "&date=" + agform.getDate()+ "&jointactypeno=" + agform.getJointactypeno()+ "&jointAcno=" + agform.getJointAcno() + "&jointname="+ agform.getJointname() + "&introduceractypeno="+ agform.getIntroduceractypeno() + "&introducerAcno="+ agform.getIntroducerAcno() + "&details="+ agform.getDetails(),"/Pygmy/AgentOpening?tabPaneHeading=Introducer Type&pageid="+ agform.getPageid() + "&agentcode="+ agform.getAgentcode() + "&agentnum="+ agform.getAgentnum() + "&agentname="+ agform.getAgentname() + "&selfactypeno="+ agform.getSelfactypeno() + "&selfAcno="+ agform.getSelfAcno() + "&selfname="+ agform.getSelfname() + "&date=" + agform.getDate()+ "&jointactypeno=" + agform.getJointactypeno()+ "&jointAcno=" + agform.getJointAcno() + "&jointname="+ agform.getJointname() + "&introduceractypeno="+ agform.getIntroduceractypeno() + "&introducerAcno="+ agform.getIntroducerAcno() + "&details="+ agform.getDetails() };
		String tabHeading[] = { "Personal", "Introducer Type" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim() };
		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "3003");

	}*/

	/*private void setSignatureTabPageDetails(HttpServletRequest request,AgentOpeningForm agform) {

		String pageAction[] = { "/Pygmy/AgentOpening?tabPaneHeading=Signature Ins&signIndex=0&pageid="+ agform.getPageid()+ "&agentcode="+ agform.getAgentcode()+ "&agentnum="+ agform.getAgentnum()+ "&agentname="+ agform.getAgentname()+ "&selfactypeno="+ agform.getSelfactypeno()+ "&selfAcno="+ agform.getSelfAcno()+ "&selfname="+ agform.getSelfname()+ "&date="+ agform.getDate()+ "&jointactypeno="+ agform.getJointactypeno()+ "&jointAcno="+ agform.getJointAcno()+ "&jointname="+ agform.getJointname()+ "&introduceractypeno="+ agform.getIntroduceractypeno()+ "&introducerAcno="+ agform.getIntroducerAcno()+ "&details="+ agform.getDetails() };
		request.setAttribute("SignPageActions", pageAction);
		String pagePath = MenuNameReader.getScreenProperties("0004").trim();
		request.setAttribute("SignPagePath", pagePath);
		//request.setAttribute("SignTabNum", "0");
		String tabHeading[] = { "Sign1", "Sign2" };
		request.setAttribute("SignTabHeading", tabHeading);

	}*/

	// *********************** PYGMYOPENING METHODS*********************

	private void setPygmyOpeningInitParams(HttpServletRequest req, String path,
			PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("PayAcType", pgDelegate.getPgComboElements(0));
			req.setAttribute("PygmyAcType", pgDelegate.getPgComboElements(1));
			req.setAttribute("IntroAgentAcType", pgDelegate.getPgComboElements(2));
		    //req.setAttribute("ComboAgentNo",pgDelegate.getAgentNos("1013001",0));
			req.setAttribute("PygmyComboDetails", pgDelegate.getPygDetails());
			req.setAttribute("PayMode", pgDelegate.getPayMode());
			req.setAttribute("AgentAcType", pgDelegate.getComboElements(3));
			req.setAttribute("Date", Date.getSysDate());
			// req.setAttribute("Disable",true);

		} catch (Exception e) {
			throw e;
		}
	}

	private void setPygmyDetails(String pygdetails, HttpServletRequest req,PygmyDelegate pgDelegate, PygmyMasterObject pmObj,AccountObject acObject, PygmyOpeningForm pgform,SignatureInstructionForm siForm,AgentMasterObject agmaster, int signFlag) throws Exception {

		if (pygdetails.trim().equalsIgnoreCase("Personal")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			req.setAttribute("TabNum", "0");
			
			if(pmObj!=null  && (pgform.getCustid()!=null)){
				System.out.println("pmObj.getCid()==" + pgform.getCustid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(Integer.parseInt(pgform.getCustid())));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				req.setAttribute("flag", perPath);
				System.out.println("yes flag is set");
			}else if(acObject!=null)
			{
				System.out.println("pgDelegate.getCustomer(acObject.getCid())"+ pgDelegate.getCustomer(acObject.getCid()));
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acObject.getCid()));
				
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				req.setAttribute("flag", perPath);
			}
		} 

		/*if (pygdetails.trim().equalsIgnoreCase("Nominee")) {
			String perPath = MenuNameReader.getScreenProperties("0002");
			System.out.println("-----------============>>" + perPath);
			ShareDelegate shDelegate=new ShareDelegate();
			ShareMasterObject shobj=shDelegate.getShareMaster("1001001",Integer.parseInt(pgform.getIntagno()));
			req.setAttribute("pageNum", pgform.getPageid());
			if(shobj!=null){
				System.out.println("The cid is "+pmObj.getCid());
		        CustomerMasterObject cust_obj=pgDelegate.getCustomer(shobj.getCustomerId());
					
					System.out.println("THe address of the customer is "+cust_obj.getAddress());
					req.setAttribute("custdetails",cust_obj);
					
				
			}
			req.setAttribute("panelName", CommonPanelHeading.getNominee());
			req.setAttribute("TabNum", "1");
			req.setAttribute("flag", perPath);
		}*/
		/*if (pygdetails.trim().equalsIgnoreCase("Nominee")) {
			String perPath = MenuNameReader.getScreenProperties("8023");
			System.out.println("yes flag is set");
			req.setAttribute("pageNum", pgform.getPageid());
			req.setAttribute("panelName", CommonPanelHeading.getNominee());
			req.setAttribute("TabNum", "1");
			req.setAttribute("flag", perPath);
		}*/
		

		if (pygdetails.equalsIgnoreCase("Agent Details")) {
			System.out.println("m inside Agent Details===");
			String perPath = MenuNameReader.getScreenProperties("0001");
			req.setAttribute("pageNum", pgform.getPageid());
			AgentMasterObject agMaster = null;

			if (pmObj != null && String.valueOf(pmObj.getAccNo())!=null && pmObj.getAgentNo()!=null) {
				System.out.println("pmObj======="+pmObj);
				System.out.println("AgntMaster =" + pmObj.getAgentType()+ "\t" + pmObj.getAgentNo());
				System.out.println("pmObj.getAgentNo()========>>>"+pmObj.getAgentNo());
				System.out.println("pmObj.getAccOpenDate()========>>"+pmObj.getAccOpenDate());
				agMaster = pgDelegate.getAgentDetails("1013001", Integer.parseInt(pmObj.getAgentNo()),pmObj.getAccOpenDate());
				if (agMaster != null) {
					System.out.println("amobj.getCid()==" + agMaster.getCid());
					req.setAttribute("personalInfo", pgDelegate.getCustomer(agMaster.getCid()));
				} else {
					req.setAttribute("personalInfo",null);
				}
			} else if (pgform != null) {
					System.out.println("(((((((()))))))))"+pgform.getInagenttypeno());
					System.out.println("(((((((()))))))))"+pgform.getIntagno());
					agMaster = pgDelegate.getAgentDetails(pgform.getInagenttypeno(), Integer.parseInt(pgform.getIntagno()),pgform.getOpdate());
					System.out.println("inside delegate cid--->"+agMaster.getCid());
			if (agMaster != null) {
				System.out.println("amobj.getCid()==" + agMaster.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(agMaster.getCid()));
			} else {
				req.setAttribute("personalInfo",null);
			}
			}
			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getAgentDetails());
		}
	}
	
	private void setPygOpTabbedPaneInitParams(HttpServletRequest req,
			String path, PygmyDelegate pgDelegate, PygmyOpeningForm pgform)throws Exception {
		System.out.println(path);

		String pageActions[] = {"/Pygmy/PygmyOpening?tabPaneHeading=Personal&pageid="+ pgform.getPageid() + "&pgactypeno="+ pgform.getPgactypeno() + "&pgno=" + pgform.getPgno()+ "&custid=" + pgform.getCustid() + "&inagenttypeno="+ pgform.getInagenttypeno() + "&intagno="+ pgform.getIntagno() + "&agname=" + pgform.getAgname()+ "&opdate=" + pgform.getOpdate() + "&paymode="+ pgform.getPaymode() + "&payactypeno="+ pgform.getPayactypeno() + "&payno="+ pgform.getPayno() + "&name=" + pgform.getName()+ "&pygdetails=" + pgform.getPygdetails(),"/Pygmy/PygmyOpening?tabPaneHeading=Nominee&pageid="+ pgform.getPageid() + "&pgactypeno="+ pgform.getPgactypeno() + "&pgno=" + pgform.getPgno()+ "&custid=" + pgform.getCustid() + "&inagenttypeno="+ pgform.getInagenttypeno() + "&intagno="+ pgform.getIntagno() + "&agname=" + pgform.getAgname()+ "&opdate=" + pgform.getOpdate() + "&paymode="+ pgform.getPaymode() + "&payactypeno="+ pgform.getPayactypeno() + "&payno="+ pgform.getPayno() + "&name=" + pgform.getName()+ "&pygdetails=" + pgform.getPygdetails(),"/Pygmy/PygmyOpening?tabPaneHeading=JointHolders&pageid="+ pgform.getPageid() + "&pgactypeno="+ pgform.getPgactypeno() + "&pgno=" + pgform.getPgno()+ "&custid=" + pgform.getCustid() + "&inagenttypeno="+ pgform.getInagenttypeno() + "&intagno="+ pgform.getIntagno() + "&agname=" + pgform.getAgname()+ "&opdate=" + pgform.getOpdate() + "&paymode="+ pgform.getPaymode() + "&payactypeno="+ pgform.getPayactypeno() + "&payno="+ pgform.getPayno() + "&name=" + pgform.getName()+ "&pygdetails=" + pgform.getPygdetails(),"/Pygmy/PygmyOpening?tabPaneHeading=Signature Instruction&pageid="+ pgform.getPageid() + "&pgactypeno="+ pgform.getPgactypeno() + "&pgno=" + pgform.getPgno()+ "&custid=" + pgform.getCustid() + "&inagenttypeno="+ pgform.getInagenttypeno() + "&intagno="+ pgform.getIntagno() + "&agname=" + pgform.getAgname()+ "&opdate=" + pgform.getOpdate() + "&paymode="+ pgform.getPaymode() + "&payactypeno="+ pgform.getPayactypeno() + "&payno="+ pgform.getPayno() + "&name=" + pgform.getName()+ "&pygdetails=" + pgform.getPygdetails(),"/Pygmy/PygmyOpening?tabPaneHeading=Agent Details&pageid="+ pgform.getPageid() + "&pgactypeno="+ pgform.getPgactypeno() + "&pgno=" + pgform.getPgno()+ "&custid=" + pgform.getCustid() + "&inagenttypeno="+ pgform.getInagenttypeno() + "&intagno="+ pgform.getIntagno() + "&agname=" + pgform.getAgname()+ "&opdate=" + pgform.getOpdate() + "&paymode="+ pgform.getPaymode() + "&payactypeno="+ pgform.getPayactypeno() + "&payno="+ pgform.getPayno() + "&name=" + pgform.getName()+ "&pygdetails=" + pgform.getPygdetails() };

		String tabHeading[] = { "Personal", "Nominee", "JointHolders","Signature Instruction", "Agent Details" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0002"),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim(),MenuNameReader.getScreenProperties("0001").trim() };

		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "8002");

	}

	/*private void setSignatureTabPageDetails(HttpServletRequest request,PygmyOpeningForm pgForm) {

		String pageAction = "/Pygmy/PygmyOpening?tabPaneHeading=Signature Instruction&pageid="+ pgForm.getPageid()+ "&pgactypeno="+ pgForm.getPgactypeno()+ "&pgno="+ pgForm.getPgno()+ "&custid="+ pgForm.getCustid()+ "&inagenttypeno="+ pgForm.getInagenttypeno()+ "&agname="+ pgForm.getAgname()+ "&opdate="+ pgForm.getOpdate()+ "&paymode="+ pgForm.getPaymode()+ "&payactypeno="+ pgForm.getPayactypeno()+ "&payno="+ pgForm.getPayno()+ "&name="+ pgForm.getName()+ "&pygdetails="+ pgForm.getPygdetails();
		request.setAttribute("SignPageActions", pageAction);
		String pagePath = MenuNameReader.getScreenProperties("0004").trim();
		request.setAttribute("SignPagePath", pagePath);
		//request.setAttribute("SignTabNum", "0");
		String tabHeading[] = { "Sign1", "Sign2" };
		request.setAttribute("SignTabHeading", tabHeading);

	}*/


	
	
	

	
	// ********************** AGENTCLOSING METHODS ***************************

	private void setAgentClosingInitParams(HttpServletRequest req, String path,
			PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("AgentAcType", pgDelegate.getAgentCloseComboElements(2));
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void setAgentClosingDetails(String agNo, HttpServletRequest req,PygmyDelegate pgDelegate, AgentMasterObject[] agMaster,AgentClosingForm acform) throws Exception {
		if (Integer.parseInt(agNo) != 0) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			AgentMasterObject agm = null;
			System.out.println("yes flag is set");
			req.setAttribute("TabNum", "0");
			if (agMaster[0].getCid() != 0) {
				agm = pgDelegate.getAccountMaster1(Integer.parseInt(acform.getAgNo()), acform.getAgType(), Date.getSysDate());
				if(agm.getCid()!=0)
				{
				int cid = agm.getCid();
				System.out.println("acntObject.getCid()==@@@" + cid);
				req.setAttribute("personalInfo", pgDelegate.getCustomer(cid));
				}else{
					req.setAttribute("msg","Customer ID Not Found");
				}
			} else {
				req.setAttribute("msg","AgentDetails Not Found");
				req.setAttribute("personalInfo", new CustomerMasterObject());
			}
			req.setAttribute("panelName", CommonPanelHeading.getPersonal());
			req.setAttribute("flag", perPath);
			System.out.println("yes flag is set");
		}
	}

	// *********************** PYGMYWITHDRAWAL METHODS*********************

	private void setPygmyWithdrawalInitParams(HttpServletRequest req,
			String path, PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("PayAcType", pgDelegate.getPymentTypes());
			req.setAttribute("PygmyAcType", pgDelegate.getPgWithComboElements(1));
			req.setAttribute("AgentAcType", pgDelegate.getPgWithComboElements(2));
			req.setAttribute("TypeOfOperation", pgDelegate.getTOP());
			req.setAttribute("PygmyComboDetails", pgDelegate.getPygDetails());
			req.setAttribute("PayMode", pgDelegate.getPayMode());
			//req.setAttribute("details",pgDelegate.getPgComboElements(type))
		} catch (Exception e) {
			throw e;
		}
	}

	/*private void setPygWithTabbedPaneInitParams(HttpServletRequest req,String path, PygmyDelegate pgDelegate, PygmyWithdrawalForm pwform)throws Exception {
		System.out.println(path);

		String pageActions[] = {"/Pygmy/PygmyWithdrawal?tabPaneHeading=Personal&pageid="+ pwform.getPageid() + "&pgactypeno="+ pwform.getPgactypeno() + "&pgno=" + pwform.getPgno()+ "&pname=" + pwform.getPname() + "&agentcode="+ pwform.getAgentcode() + "&agentnum="+ pwform.getAgentnum() + "&agentname="+ pwform.getAgentname() + "&opdate="+ pwform.getOpdate() + "&tyop=" + pwform.getTyop()+ "&principal=" + pwform.getPrincipal() + "&interest="+ pwform.getInterest() + "&intrate="+ pwform.getIntrate() + "&max_amt="+ pwform.getMax_amt() + "&withdrawal_amt="+ pwform.getWithdrawal_amt() + "&loan_acc="+ pwform.getLoan_acc() + "&loan_bal="+ pwform.getLoan_bal() + "&fines=" + pwform.getFines()+ "&paymode=" + pwform.getPaymode() + "&payactypeno="+ pwform.getPayactypeno() + "&payno="+ pwform.getPayno() + "&name=" + pwform.getName()+ "&pygdetails=" + pwform.getPygdetails(),"/Pygmy/PygmyWithdrawal?tabPaneHeading=Nominee&pageid="+ pwform.getPageid() + "&pgactypeno="+ pwform.getPgactypeno() + "&pgno=" + pwform.getPgno()+ "&pname=" + pwform.getPname() + "&agentcode="+ pwform.getAgentcode() + "&agentnum="+ pwform.getAgentnum() + "&agentname="+ pwform.getAgentname() + "&opdate="+ pwform.getOpdate() + "&tyop=" + pwform.getTyop()+ "&principal=" + pwform.getPrincipal() + "&interest="+ pwform.getInterest() + "&intrate="+ pwform.getIntrate() + "&max_amt="+ pwform.getMax_amt() + "&withdrawal_amt="+ pwform.getWithdrawal_amt() + "&loan_acc="+ pwform.getLoan_acc() + "&loan_bal="+ pwform.getLoan_bal() + "&fines=" + pwform.getFines()+ "&paymode=" + pwform.getPaymode() + "&payactypeno="+ pwform.getPayactypeno() + "&payno="+ pwform.getPayno() + "&name=" + pwform.getName()+ "&pygdetails=" + pwform.getPygdetails(),"/Pygmy/PygmyWithdrawal?tabPaneHeading=JointHolders&pageid="+ pwform.getPageid() + "&pgactypeno="+ pwform.getPgactypeno() + "&pgno=" + pwform.getPgno()+ "&pname=" + pwform.getPname() + "&agentcode="+ pwform.getAgentcode() + "&agentnum="+ pwform.getAgentnum() + "&agentname="+ pwform.getAgentname() + "&opdate="+ pwform.getOpdate() + "&tyop=" + pwform.getTyop()+ "&principal=" + pwform.getPrincipal() + "&interest="+ pwform.getInterest() + "&intrate="+ pwform.getIntrate() + "&max_amt="+ pwform.getMax_amt() + "&withdrawal_amt="+ pwform.getWithdrawal_amt() + "&loan_acc="+ pwform.getLoan_acc() + "&loan_bal="+ pwform.getLoan_bal() + "&fines=" + pwform.getFines()+ "&paymode=" + pwform.getPaymode() + "&payactypeno="+ pwform.getPayactypeno() + "&payno="+ pwform.getPayno() + "&name=" + pwform.getName()+ "&pygdetails=" + pwform.getPygdetails(),"/Pygmy/PygmyWithdrawal?tabPaneHeading=Signature Instruction&pageid="+ pwform.getPageid() + "&pgactypeno="+ pwform.getPgactypeno() + "&pgno=" + pwform.getPgno()+ "&pname=" + pwform.getPname() + "&agentcode="+ pwform.getAgentcode() + "&agentnum="+ pwform.getAgentnum() + "&agentname="+ pwform.getAgentname() + "&opdate="+ pwform.getOpdate() + "&tyop=" + pwform.getTyop()+ "&principal=" + pwform.getPrincipal() + "&interest="+ pwform.getInterest() + "&intrate="+ pwform.getIntrate() + "&max_amt="+ pwform.getMax_amt() + "&withdrawal_amt="+ pwform.getWithdrawal_amt() + "&loan_acc="+ pwform.getLoan_acc() + "&loan_bal="+ pwform.getLoan_bal() + "&fines=" + pwform.getFines()+ "&paymode=" + pwform.getPaymode() + "&payactypeno="+ pwform.getPayactypeno() + "&payno="+ pwform.getPayno() + "&name=" + pwform.getName()+ "&pygdetails=" + pwform.getPygdetails(),"/Pygmy/PygmyWithdrawal?tabPaneHeading=Agent Details&pageid="+ pwform.getPageid() + "&pgactypeno="+ pwform.getPgactypeno() + "&pgno=" + pwform.getPgno()+ "&pname=" + pwform.getPname() + "&agentcode="+ pwform.getAgentcode() + "&agentnum="+ pwform.getAgentnum() + "&agentname="+ pwform.getAgentname() + "&opdate="+ pwform.getOpdate() + "&tyop=" + pwform.getTyop()+ "&principal=" + pwform.getPrincipal() + "&interest="+ pwform.getInterest() + "&intrate="+ pwform.getIntrate() + "&max_amt="+ pwform.getMax_amt() + "&withdrawal_amt="+ pwform.getWithdrawal_amt() + "&loan_acc="+ pwform.getLoan_acc() + "&loan_bal="+ pwform.getLoan_bal() + "&fines=" + pwform.getFines()+ "&paymode=" + pwform.getPaymode() + "&payactypeno="+ pwform.getPayactypeno() + "&payno="+ pwform.getPayno() + "&name=" + pwform.getName()+ "&pygdetails=" + pwform.getPygdetails() };

		String tabHeading[] = { "Personal", "Nominee", "JointHolders","Signature Instruction", "Agent Details" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0002"),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim(),MenuNameReader.getScreenProperties("0001").trim() };

		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "3003");

	}
*/
	private void setPygmyWithDetails(String pygdetails, HttpServletRequest req,PygmyDelegate pgDelegate, PygmyMasterObject pmObj,PygmyWithdrawalForm pwform, SignatureInstructionForm siForm,int signFlag) throws Exception {

		if (pygdetails.trim().equalsIgnoreCase("Personal")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			//req.setAttribute("TabNum", "0");
			System.out.println("pmObj.getCid()==" + pmObj.getCid());
			req.setAttribute("personalInfo", pgDelegate.getCustomer(pmObj.getCid()));
			req.setAttribute("panelName", CommonPanelHeading.getPersonal());
			req.setAttribute("flag", perPath);
			System.out.println("yes flag is set");
		}
		
		if (pygdetails.equalsIgnoreCase("Agent Details")) {
			System.out.println("M inside Agent Details");
			String perPath = MenuNameReader.getScreenProperties("0001");
			req.setAttribute("pageNum", pwform.getPageid());
			AgentMasterObject agMaster = null;

			if (pmObj != null) {
				System.out.println("pmObj??????????"+pmObj);
				System.out.println("AgntMaster =" + pmObj.getAgentType()+ "\t" + pmObj.getAgentNo());
				System.out.println("pmObj.getAgentNo()===========>>>"+pmObj.getAgentNo());
				System.out.println("pmObj.getAccOpenDate()=======>>>"+pmObj.getAccOpenDate());
				agMaster = pgDelegate.getAgentDetails("1013001", Integer.parseInt(pmObj.getAgentNo()),pmObj.getAccOpenDate());
				System.out.println("agMaster=========>>>"+agMaster.getCid());
			} else {

				if (pwform != null) {
					System.out.println("(((((((()))))))))"+pwform.getAgentcode());
					System.out.println("(((((((()))))))))"+pwform.getAgentnum());
					agMaster = pgDelegate.getAgentDetails(pwform.getAgentcode(), Integer.parseInt(pwform.getAgentnum()),pwform.getOpdate());
					System.out.println("inside action cid--->"+agMaster.getCid());
				}else{
					System.out.println("Agent details not Found!!!!!!!");
					req.setAttribute("msg","Agent details not Found!!");
				}
			}
			
			
			if(agMaster != null) {
				System.out.println("agMaster.getCid()=+++++++++" + agMaster.getCid());
				if(agMaster.getCid()!=0){
				req.setAttribute("personalInfo", pgDelegate.getCustomer(agMaster.getCid()));
				}
				else
				{
					req.setAttribute("msg","Agent Details Not Found");
				}
			} else {
				req.setAttribute("personalInfo",null);
			}

			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getAgentDetails());
		}
	}
	

	// %%%%%%%%%%%%%%%%%%%%%% Reports Methods %%%%%%%%%%%%%%%%%%%%%%%%

	private void setReportsInitParams(HttpServletRequest req, String path,
			PygmyDelegate pgDelegate) throws Exception {
		
		try {
			System.out.println("i am in set int parameter"+path);
			
			req.setAttribute("pageId", path);
			req.setAttribute("COMBOACTIVE", pgDelegate.getComboActive());
			req.setAttribute("COMBOAGENTS", pgDelegate.getComboAgents());
			req.setAttribute("AgentAcType", pgDelegate.getAgentRemiComboElements(2));
			req.setAttribute("AgentAcType1", pgDelegate.getMonthRemiComboElements(2));
			req.setAttribute("PygmyType", pgDelegate.getPassBkComboElements(1));
			req.setAttribute("RECTYPE", pgDelegate.getRecType());
			req.setAttribute("Date", Date.getSysDate());
			req.setAttribute("LedgerType", pgDelegate.getLedgerType());
			req.setAttribute("AgentType", pgDelegate.getAckComboElements(2));
			// req.setAttribute("Disable",true);
		} catch (Exception e) {
			throw e;
		}

	}

	private void setIntCalInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);

		} catch (Exception e) {
			throw e;
		}

	}
	
	//******************************** Daily Remi Methods **********************************
	
	private void setDailyInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			
			ModuleObject[] a=pgDelegate.getDrComboElements(2);
			
			System.out.println("------>>>"+a);
			
			for(int i=0;i<a.length;i++){
				
				System.out.println("---->????"+a[i].getModuleAbbrv());
			}
			
			
			req.setAttribute("AgentType", pgDelegate.getDrComboElements(2));
			req.setAttribute("Date", Date.getSysDate());
			req.setAttribute("COLLDATE",Validations.checkDate(pgDelegate.prevWorkingDay(Date.getSysDate())));
		} catch (Exception e) {
			throw e;
		}

	}
	
	
	//************************** AGENT UPDATE METHODS ***************************************

	private void setAgentUpdateInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("SelfAcType", pgDelegate.getAgentMasterComboElements(0));
			req.setAttribute("IntroAcType", pgDelegate.getAgentMasterComboElements(1));
			req.setAttribute("JointAcType", pgDelegate.getAgentMasterComboElements(3));
			req.setAttribute("AgentAcType", pgDelegate.getAgentMasterComboElements(2));
			req.setAttribute("Details", pgDelegate.getDetailsElements());
			req.setAttribute("Date", Date.getSysDate());
			
			 req.setAttribute("Disabled", true);
			 req.setAttribute("Disabled1",true);
			 
		} catch (Exception e) {
			throw e;
		}

	}
	
	/*private void setAgentUpTabbedPaneInitParams(HttpServletRequest req,String path, PygmyDelegate pgDelegate,AgentUpdateForm auForm)throws Exception {
		System.out.println(path);

		String pageActions[] = {"/Pygmy/AgentUpdate?tabPaneHeading=Personal&pageid="+ auForm.getPageid() + "&agType="+ auForm.getAgType() + "&agNo=" + auForm.getAgNo()+ "&agName=" + auForm.getAgName() + "&custId="+ auForm.getCustId() + "&appDate="+ auForm.getAppDate() + "&selfAccType=" + auForm.getSelfAccType()+ "&selfAcNo=" + auForm.getSelfAcNo() + "&selfAccName="+ auForm.getSelfAccName() + "&jtAccType="+ auForm.getJtAccType() + "&jtAcNo="+ auForm.getJtAcNo() + "&jtAccName=" + auForm.getJtAccName()+ "&intAccType=" + auForm.getIntAccType()+"&intAccNo"+auForm.getIntAccNo()+"&closeInd"+auForm.getCloseInd()+"&closeDate"+auForm.getCloseDate()+"&details"+auForm.getDetails(),"/Pygmy/AgentUpdate?tabPaneHeading=Introducer Type&pageid="+ auForm.getPageid() + "&agType="+ auForm.getAgType() + "&agNo=" + auForm.getAgNo()+ "&agName=" + auForm.getAgName() + "&custId="+ auForm.getCustId() + "&appDate="+ auForm.getAppDate() + "&selfAccType=" + auForm.getSelfAccType()+ "&selfAcNo=" + auForm.getSelfAcNo() + "&selfAccName="+ auForm.getSelfAccName() + "&jtAccType="+ auForm.getJtAccType() + "&jtAcNo="+ auForm.getJtAcNo() + "&jtAccName=" + auForm.getJtAccName()+ "&intAccType=" + auForm.getIntAccType()+"&intAccNo"+auForm.getIntAccNo()+"&closeInd"+auForm.getCloseInd()+"&closeDate"+auForm.getCloseDate()+"&details"+auForm.getDetails()};

		String tabHeading[] = { "Personal", "Introducer Type" };
		String pagePath[] = {MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim() };

		req.setAttribute("TabHeading", tabHeading);
		req.setAttribute("PageActions", pageActions);
		req.setAttribute("PagePath", pagePath);
		req.setAttribute("pageNum", "3003");

	}*/

	private void setAgentUpdateDetails(String details, HttpServletRequest req,PygmyDelegate pgDelegate, AgentMasterObject amObj,AgentUpdateForm auForm, SignatureInstructionForm siForm,AccountObject acObj, int signFlag) throws Exception {
		System.out.println("details==" + details);
		if (details.equalsIgnoreCase("Personal")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			req.setAttribute("flag",perPath);
		//	amObj=pgDelegate.getAgentDetails(agform.getAgentcode(), Integer.parseInt(agform.getAgentnum()), agform.getDate());
			if (amObj.getCid() != 0) {
				
				System.out.println("----amObj.getCid()-----" + amObj.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(amObj.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				}
			else
			{
				req.setAttribute("msg","Customer Not Found");
			}
			 if (acObj != null) {
				System.out.println("********perObject.getCid()========="+ acObj.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acObj.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());

			}
			System.out.println("yes flag is set");
		}

		if (auForm.getDetails().equalsIgnoreCase("Introducer Type")) 
		{
			String perPath = MenuNameReader.getScreenProperties("0001");
			AccountObject acntObject = pgDelegate.getIntroducerAcntDetails(auForm.getIntAccType(), Integer.parseInt(auForm.getIntAccNo()));
			System.out.println("acntObject.getCid()==" + acntObject);
			
			if (acntObject != null) {
				System.out.println("acntObject.getCid()=="+ acntObject.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acntObject.getCid()));
			} else {
				req.setAttribute("personalInfo", new CustomerMasterObject());
			}
			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
		}

		
		/*if(auForm.getDetails().trim().equalsIgnoreCase("Signature Ins"))
		{
			String perPath=MenuNameReader.getScreenProperties("0004");
			System.out.println("path=="+perPath);
			req.setAttribute("pageNum",auForm.getPageid()); 
			System.out.println("pageNum======="+auForm.getPageid());
			System.out.println(auForm.getAgNo()+"\t"+auForm.getAgType());
			SignatureInstructionObject[] signObject=pgDelegate.getSignature(Integer.parseInt(auForm.getAgNo()),auForm.getAgType());
			System.out.println("sign=="+signObject);
			
			req.setAttribute("SignValues", signObject);
			req.setAttribute("AgentForm", auForm);
			req.setAttribute("SignInstActnPath","/Common/sign");
			
			if(signObject!=null){
        		req.setAttribute("signInfo",signObject);
            }else{
        		req.setAttribute("signInfo",signObject);
        		
        		
        	}
            req.setAttribute("flag",perPath);
            req.setAttribute("panelName", CommonPanelHeading.getSignature());
        
		}*/
		
		if (details.trim().equalsIgnoreCase("Joint Holders")) 
		{
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("perpath\\\\\\\\\\\\\\\\\\\\\\"+perPath);
			AgentMasterObject agMaster = null;

			if(amObj != null) 
			{
				System.out.println("AgntMaster Master Objects=--->" + amObj.getJointAccType()+ "\t" + amObj.getJointAccNo());
				//agMaster = pgDelegate.getPAGJointAccountCids1(amObj.getJointAccType(), amObj.getJointAccNo());
				agMaster=pgDelegate.getPAGJointAccount(amObj.getJtAccType(), amObj.getJointAccNo());
				System.out.println("Inside If-----jointHolder--------->"+agMaster.getCid());
				
				if(agMaster.getCid() != 0) 
				{
					int cid[] = agMaster.getJt_cids();
					System.out.println("acntObject.getCid()==" + cid[0]);
					req.setAttribute("personalInfo", pgDelegate.getCustomer(cid[0]));
				} 
			}
				else 
				{
					//req.setAttribute("msg","Customer Not Found");
					req.setAttribute("personalInfo",null);
					req.setAttribute("msg","Joint A/c Details Not Found");
				}
			


			
				if(auForm != null) 
				{
					System.out.println(auForm.getJtAccType());
					System.out.println(auForm.getJtAcNo());
					agMaster = pgDelegate.getPAGJointAccountCids(auForm.getJtAccType(), Integer.parseInt(auForm.getJtAcNo()));
					System.out.println("agMaster========>>>"+agMaster.getCid());
					if(agMaster != null) 
					{
						int cid[] = agMaster.getJt_cids();
						System.out.println("acntObject.getCid()==" + cid[0]);
						req.setAttribute("personalInfo", pgDelegate.getCustomer(cid[0]));
					} 
					else 
					{
						req.setAttribute("personalInfo",null);
						//req.setAttribute("msg","Joint A/c Personal Details Not Found");
					}
					
				}
			
			
			
			
			//req.setAttribute("Joint Holders", agMaster);
			//req.setAttribute("flag",perPath);

			

			req.setAttribute("flag", perPath);
			req.setAttribute("panelName", CommonPanelHeading.getJointHolder());
		}
	}	
	
	//******************************* AGENT CHANGING METHODS***************************
	
	private void setAgentChangeInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("AgAcType", pgDelegate.getAgentChangeComboElements(2));
			req.setAttribute("Date", Date.getSysDate());

		} catch (Exception e) {
			throw e;
		}

	}
	
	//*********************************** PYGMY UPDATE MASTER ***************************
	
	
	private void setPygmyUpdateInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			req.setAttribute("AgentAcType", pgDelegate.getPygmyMasterComboElements(1));
			req.setAttribute("PygmyAcType", pgDelegate.getPygmyMasterComboElements(2));
			req.setAttribute("LoanAcType", pgDelegate.getPygmyMasterComboElements(3));
			req.setAttribute("PayAcType", pgDelegate.getPygmyMasterComboElements(0));
			req.setAttribute("AddressType", pgDelegate.getAddressType());
			req.setAttribute("Details",pgDelegate.getPygUpdateDetails());
			req.setAttribute("PayMode",pgDelegate.getPygmyMasterPayMode());
			req.setAttribute("Date", Date.getSysDate());
			req.setAttribute("Disabled", true);
		} catch (Exception e) {
			throw e;
		}

	}
	
	
	private void setPygmyUpdateDetails(String details, HttpServletRequest req,PygmyDelegate pgDelegate,PygmyMasterObject pmo,PUpdateForm pUpdateForm, SignatureInstructionForm siForm,AccountObject acObj,AgentMasterObject amo, int signFlag) throws Exception {
		System.out.println("details==" + details);
		if (details.equalsIgnoreCase("Personal")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			
			if (pmo != null) {
				
				System.out.println("----amObj.getCid()-----" + pmo.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(pmo.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				req.setAttribute("flag", perPath);
				}
			else if (acObj != null) {
				System.out.println("********perObject.getCid()========="+ acObj.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(acObj.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getPersonal());
				req.setAttribute("flag", perPath);

			}
			System.out.println("yes flag is set");
		}
		/*if (details.trim().equalsIgnoreCase("Nominee")) {
			String perPath = MenuNameReader.getScreenProperties("0002");
			System.out.println("-----------============" + perPath);
			req.setAttribute("pageNum", pUpdateForm.getPageid());
			if(pmo.getNomineeDetails()!=null){
				System.out.println("The cid is "+pmo.getCid());
		        CustomerMasterObject cust_obj=pgDelegate.getCustomer(pmo.getCid());
					
					System.out.println("THe address of the customer is "+cust_obj.getAddress());
					req.setAttribute("custdetails",cust_obj);
					
				
			}
			req.setAttribute("panelName", CommonPanelHeading.getNominee());
			//req.setAttribute("TabNum", "1");
			req.setAttribute("flag", perPath);
		}*/

		
		if (details.equalsIgnoreCase("Agent Details")) {
			String perPath = MenuNameReader.getScreenProperties("0001");
			System.out.println("yes flag is set");
			
			 amo=pgDelegate.getAgentDetails("1013001", Integer.parseInt(pmo.getAgentNo()),pmo.getAccOpenDate());
			System.out.println("amo======AgentDetails=====>>>>"+amo);
			if (amo.getCid() != 0) {
				System.out.println("pgform.getCustid()==" + amo.getCid());
				req.setAttribute("personalInfo", pgDelegate.getCustomer(amo.getCid()));
				req.setAttribute("panelName", CommonPanelHeading.getAgentDetails());
				req.setAttribute("flag", perPath);
			}
			else{
				req.setAttribute("msg","Agent Details Not Found");
			}
		}
		
	}	
	
	//********************************************************************
	
	private void setAgentCommInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	private List setAgCommValues(PygmyDelegate pgDelegate,HttpServletRequest req)throws RemoteException{
		PygmyRateObject[] comRate=pgDelegate.commissionRatesChange();
		List agComList=new ArrayList();
		for(int i=0;i<comRate.length;i++){
			Map agComMap=new TreeMap();
			System.out.println("Creating map");
			agComMap.put("update",(i+1));
			agComMap.put("AgentType","1013001");
			agComMap.put("From",comRate[i].getFromDate());
			agComMap.put("To",comRate[i].getToDate());
			agComMap.put("MinAmount",comRate[i].getMinAmt());
			agComMap.put("MaxAmount",comRate[i].getMaxAmt());
			agComMap.put("CommRate",comRate[i].getCommissionRate());
			agComMap.put("Security_Dep_Rate",comRate[i].getSecurityDepRate());
			agComMap.put("DeUser",comRate[i].getDeUser());
			agComMap.put("DeTml",comRate[i].getDeTml());
			agComMap.put("DeDate",comRate[i].getDeDate());
			agComList.add(agComMap);
		}
		
		 session.setAttribute("AdminList", agComList);//to keep the last data
		 req.setAttribute("AdminList", agComList);//send the data to the client
		 return agComList;
	}	
	
	private void setPygmyCommInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			
		} catch (Exception e) {
			throw e;
		}

	}
	
	private List setPgCommValues(PygmyDelegate pgDelegate,HttpServletRequest req)throws RemoteException{
		PygmyRateObject[] pygRate=pgDelegate.getPygmyRate();
		List pgComList=new ArrayList();
		for(int i=0;i<pygRate.length;i++){
			Map pgComMap=new TreeMap();
			System.out.println("Creating map");
			pgComMap.put("update",(i+1));
			pgComMap.put("PygmyType","PD");
			pgComMap.put("FromDate",pygRate[i].getFromDate());
			pgComMap.put("ToDate",pygRate[i].getToDate());
			pgComMap.put("PeriodFrom",pygRate[i].getPeriodFrom());
			pgComMap.put("PeriodTo",pygRate[i].getPeriodTo());
			pgComMap.put("IntRate",pygRate[i].getIntRate());
			pgComMap.put("DeDate",pygRate[i].getDeDate());
			pgComMap.put("DeTml",pygRate[i].getDeTml());
			pgComMap.put("DeUser",pygRate[i].getDeUser());
			pgComList.add(pgComMap);
		}
		 
		 session.setAttribute("PygmyAdminList", pgComList);//to keep the last data
		 req.setAttribute("PygmyAdminList", pgComList);//send the data to the client
		 return pgComList;
	}
	
	
	private List setDailyRemiValues(PygmyDelegate pgDelegate,HttpServletRequest req,DailyRemittanceForm drForm)throws RemoteException{
		
		
		PygmyTransactionObject[] ptran=pgDelegate.getAgentInformation(drForm.getAgType(), Integer.parseInt(drForm.getAgNo()));
		
		List drList = new ArrayList();
	
		for(int i=0;i<ptran.length;i++)
		{
			Map drMap=new TreeMap();
			System.out.println("Creating map");
			drMap.put("update",(i+1));
			drMap.put("AccountType",ptran[i].getAccType());
			drMap.put("AccountNo",ptran[i].getAccNo());
			drMap.put("Name",ptran[i].getName());
			drMap.put("PrevBal",ptran[i].getPrevBalance());
			//drMap.put("RemiAmt","");
			drList.add(drMap);
		}
		 
		 session.setAttribute("DailyRemiList", drList);//to keep the last data
		 req.setAttribute("DailyRemiList", drList);//send the data to the client
		 return drList;
	}
	private void setNomineeInitParams(HttpServletRequest req, String path,PygmyDelegate pgDelegate) throws Exception {
		try {
			req.setAttribute("pageId", path);
			//req.setAttribute("AgAcType", pgDelegate.getAgentChangeComboElements(2));
			req.setAttribute("Date", Date.getSysDate());
			req.setAttribute("PygmyAcType", pgDelegate.getPgWithComboElements(1));
			req.setAttribute("vis", "hidden");
			 req.setAttribute("vistable", "hidden");
		} catch (Exception e) {
			throw e;
		}

	}
	
	private int setNomId(){
		int nomid=0;
		
		return nomid;
	}
	
}
