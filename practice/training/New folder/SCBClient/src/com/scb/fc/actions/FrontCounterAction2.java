package com.scb.fc.actions;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import masterObject.administrator.UserActivityMasterObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.AdminObject;
import masterObject.frontCounter.ChequeObject;
import masterObject.frontCounter.ODCCMasterObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.frontCounter.StockDetailsObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.GLMasterObject;
import masterObject.general.IncomeObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.share.ShareMasterObject;
import masterObject.termDeposit.DepositMasterObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.ShareDelegate;
import com.scb.fc.forms.FCCashedPOReportForm;
import com.scb.fc.forms.ODCCApplnForm;
import com.scb.fc.forms.SBOpeningActionForm;
import com.scb.props.MenuNameReader;
/**
 * 
 */
public class FrontCounterAction2 extends Action {
    private FrontCounterDelegate fcDelegate,fcDelegatenew;
    private String path;
    int chqInststop,chqInstpost,chqInstcancel;
    StockDetailsObject ccpDetails;
    AccountTransObject array_accounttransobject[] = null;
    String chqIssuevalue;
    String acStatus;
    Address addr;
    HttpSession session;
    AccountMasterObject accountmasterobject;
    ChequeObject chq,chqtokenObject;
    NomineeObject[] nominnee;
    AdministratorDelegate admDelegate;
    Vector vector_accounts=null;
    Map user_role;
    AccountInfoObject accountinfoobject;
    PayOrderObject[] payorderobject,payorderobject_view,payordersubmit;
    PayOrderObject payorderobj,payoorderobject;
    AccountMasterObject[] array_account_master,acMasterObject;
    AccountMasterObject amObj;
       AdminObject[] admin_object;
       String user,tml;
   //    ChequeBookObject chequeobject;
       int g;
       String[] cbkno;
       ChequeObject chequeobject;
       GLMasterObject[] array_glmasterobject;
       ODCCMasterObject odccmasterobject;
       AccountObject accountObj,accountobject,acObject,odAccountobject;
       CustomerMasterObject cmObj;
       NomineeObject NomineeObject;
       LoanPurposeObject[] loanpurposeobject;
       IncomeObject[] incomeobject;
       double loanPercentage;
       IncomeObject incomeobj;
       Object[][] unverified;
       HashMap address=new HashMap();
       DepositMasterObject dObject;
       SignatureInstructionObject signObj;
       
       //for ODCC
double toaldep;
String[] dupchk;


final Logger logger=LogDetails.getInstance().getLoggerObject("FrontCounterAction");
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
    	System.out.println("This id is not perfect=="+map.getPath());
    	
    	session=req.getSession();
    	user=(String)session.getAttribute("UserName");
    	tml=(String)session.getAttribute("UserTml") ;
    	System.out.println("This id is not perfect=="+user);
    	try{
    		synchronized(req) {
				
			
    		if(user!=null){
    			
    			admDelegate=new AdministratorDelegate();
    			user_role=admDelegate.getUserLoginAccessRights(user,"FC");
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
    		fcDelegatenew=new FrontCounterDelegate();
    		req.setAttribute("acccat",fcDelegatenew.getAccCategories());
    		req.setAttribute("accsubcat",fcDelegatenew.getSubCategories());
    		}
    	}
    	catch(Exception ex){ex.printStackTrace();}
    	
	  if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SBOpeningMenu"))
	  {
          try{
        	  SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
        	  sbForm.setSysdate(FrontCounterDelegate.getSysDate());
         	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
             req.setAttribute("pageNum",sbForm.getPageId().trim());
             System.out.println("PageId is"+sbForm.getPageId().trim());
             
             if(MenuNameReader.containsKeyScreen(sbForm.getPageId())){
                 path=MenuNameReader.getScreenProperties(sbForm.getPageId());
                 req.setAttribute("path", path);
                 fcDelegate=new FrontCounterDelegate();
                 System.out.println("At 57"+path);
                 setSBOpeningInitParams(req,path,fcDelegate);
                 sbForm.setCid("0");
                 sbForm.setAcNum("0");
                 sbForm.setNom("0");
                 req.setAttribute("","acType");
                 return map.findForward(ResultHelp.getSuccess());
             }
             else{
                  path=MenuNameReader.getScreenProperties("0000");
                  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                  return map.findForward(ResultHelp.getError());
             }


         }catch(Exception e){
           path=MenuNameReader.getScreenProperties("0000");
           System.out.println("At 69 here is exception"+e);
           setErrorPageElements(""+e,req,path);
           return map.findForward(ResultHelp.getError());
         }
     }
 

     else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SBOpening")){
         SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
         sbForm.setSysdate(FrontCounterDelegate.getSysDate());
         String path;
         System.out.println("sbform=="+sbForm);
         System.out.println("sbform=="+sbForm.getPageId());
         try{
             req.setAttribute("pageNum",sbForm.getPageId().trim());
             if(MenuNameReader.containsKeyScreen(sbForm.getPageId())){
                 path=MenuNameReader.getScreenProperties(sbForm.getPageId());
                 System.out.println(path);
                 fcDelegate=new FrontCounterDelegate();
                 System.out.println(fcDelegate);
                 System.out.println("actype=="+sbForm.getAcType());
                 System.out.println("acNum=="+sbForm.getAcNum());
                 
                 System.out.println(">> sbform.cid=="+sbForm.getCid());
                 System.out.println(">> Details=="+sbForm.getDetailsCombo());
                 System.out.println(">> Account no length"+sbForm.getAcNum().length());
                 System.out.println("Account no: selected is "+sbForm.getAcNum());
                 
                 
         
        if(!sbForm.getAcType().trim().equals("SELECT")){
              
                 	
                 	/*if(true){*/
                 		if(sbForm.getAcNum().trim().length()!=0&&!sbForm.getAcNum().trim().equals("0")){
                 			accountObj=fcDelegate.getAccountObj(sbForm.getAcType(),sbForm.getAcNum());
                 			amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
                 			
                 			setSBOpeningInitParams(req,path,fcDelegate);
                 			if(amObj!=null){
                 				System.out.println("CID at 169 ============="+amObj.getCid());
                     			cmObj=fcDelegate.getCustomer(amObj.getCid());
                     			
                     			System.out.println("amobj "+amObj);
                 				req.setAttribute("setreceipt","enable");
                 				//to check for verification
                 				if(amObj.getVerified().equals("T")){
                     				req.setAttribute("msg","Account Already Verified");
                     				req.setAttribute("subenable","submitenable");
                     			
                     				sbForm.setCid(String.valueOf(amObj.getCid()));
                     				sbForm.setCustname(cmObj.getName());
                     				sbForm.setIntroAcType(amObj.getIntrAccType());
                     				sbForm.setIntroAcNum(String.valueOf(amObj.getIntrAccNo()));
                     				
                     			//FOR DETAILS
                     				
                     				
                     				if(sbForm.getDetailsCombo().equals("Personal")&&sbForm.getDetailsCombo().length()!=0){
                            			CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                            			req.setAttribute("cust",cust);
                            			
                            			
                            			path=MenuNameReader.getScreenProperties("3032");
                            			System.out.println("path is---------------"+path);
                            			req.setAttribute("flag",path); 
                            			  
                            		  }
                        		 
                     				/*if(sbForm.getDetailsCombo().equals("JointHolders")&&sbForm.getDetailsCombo().length()!=0){
                            			//for JointHolder Details
                     					amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
                     					if(amObj.getJointCid()!=null&&amObj.getJointCid().length>0)
       		    					 {	System.out.println("At 7279==================>");
       		    						 cmObj=fcDelegate.getCustomer(amObj.getJointCid()[0]);
       		    		     			req.setAttribute("cust",cmObj);
       		    		     			req.setAttribute("jointholder","joint");
       		    					 }
                     					path=MenuNameReader.getScreenProperties("3032");
                            			System.out.println("path is---------------"+path);
                            			req.setAttribute("flag",path); 
                            			  
                            		  }*/
                     				
                        		  if(sbForm.getDetailsCombo().equals("Signature Ins")&&sbForm.getDetailsCombo().length()!=0){
                            			CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                            			req.setAttribute("cust",cust);
                            			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType());
                            			
                            			req.setAttribute("sigObject",signObject);
                            			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                            			path=MenuNameReader.getScreenProperties("3056");
                            			System.out.println("path is------- --------"+path);
                            			req.setAttribute("flag","/FCSingnatureInst.jsp"); 
                            			  
                            		  }
                        		  /*if(sbForm.getDetailsCombo().equals("Cash")&&sbForm.getDetailsCombo().length()!=0){
                        			  System.out.println("accountObj is"+accountObj);
                        			  
                          			req.setAttribute("master",accountObj);
                          			System.out.println("==Scroll No is====> "+accountObj.getScrollno());
                          			System.out.println("==Date  is====> "+accountObj.getAcOpenDate());
                          			System.out.println("==Amount is====> "+accountObj.getAmount());
                          			req.setAttribute("newdata","data");
                            			
                            			
                            			
                            			path=MenuNameReader.getScreenProperties("3031");
                            			System.out.println("path is---------------"+path);
                            			req.setAttribute("flag",path); 
                            			  
                            		  }*/
                        		  /*if(sbForm.getDetailsCombo().equals("Introducer Type")&&sbForm.getDetailsCombo().length()!=0){
                        			  System.out.println("====in Introducer ==="+path);
              						req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
              						req.setAttribute("panelName",CommonPanelHeading.getIntroucer());
              						System.out.println("====sbForm.getIntroAcNum()====="+sbForm.getIntroAcNum());
              						System.out.println("====sbForm.getIntroAcType()====="+sbForm.getIntroAcType());

              						

              						AccountObject acntObject =fcDelegate.getIntroducerAcntDetails(sbForm.getIntroAcType(),Integer.parseInt(sbForm.getIntroAcNum()));
              						System.out.println("====in Introducer ==="+path);

              						if (acntObject != null) {
              							System.out.println("acntObject.getCid()=="+ acntObject.getCid());
              							req.setAttribute("personalInfo", fcDelegate.getCustomer(acntObject.getCid()));
              						} else {
              							req.setAttribute("personalInfo", new CustomerMasterObject());
              						}
                          			  
                        			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getIntroAcNum()),sbForm.getIntroAcType().trim());
                        			  if(amObj!=null){
                        			  CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                          			req.setAttribute("cust",cust);
                          			req.setAttribute("Introducer","Introducer");
                          			
                          			path=MenuNameReader.getScreenProperties("3032");
                          			System.out.println("path is---------------"+path);
                          			req.setAttribute("flag",path); 
                        			  } 
                          		  }*/
                        		  
                        		  //Nominee
                        		  /*if(sbForm.getDetailsCombo().equals("Nominee")&&sbForm.getDetailsCombo().length()!=0){
                        			  System.out.println("Inside Nominee---->");
                        			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
              						req.setAttribute("flag",MenuNameReader.getScreenProperties(String.valueOf(13026)));			
              						//shDelegate=new ShareDelegate();
              						//req.setAttribute("pageId",path);
              						//setInitParam(req, path, tddelegate);
              						
              						
              						//dep_open_obj=tddelegate.getDepositMasterValues(dep_renewal.getAc_no(), dep_renewal.getAc_type());
              						nominnee=amObj.getNomineeDetails();
              						
              						System.out.println("Length of Nominee----------------->"+nominnee.length);
              						System.out.println("NOmiNEEE CID----> "+nominnee[0].getRegNo());
              						//CustomerMasterObject cust_obj=tddelegate.getCustomer(nominnee[0].getRegNo());
              						
              						//String NomAddress=fcDelegate.getNomineeAddrDetails(nominnee[0].getRegNo());
              						//System.out.println("THe address of the customer is "+cust_obj.getAddress());
              						//req.setAttribute("custdetails",cust_obj);
              						
              						//req.setAttribute("NOMADDR",NomAddress);
              						req.setAttribute("NomDetail", nominnee);
              						req.setAttribute("HideTheFields",null);

                          			  
                          		  }
*/                        		 /* if(sbForm.getDetailsCombo().equals("Nominee")&&sbForm.getDetailsCombo().length()!=0&&sbForm.getNomcid().trim()!=null&& sbForm.getNomcid().trim().length()>0){
                          			//for Nominee Details
                        			  
                   					System.out.println("At 970==================>");
                   					if(amObj.getNomineeDetails()!=null&&amObj.getNomineeDetails().length>0){
      		    						 cmObj=fcDelegate.getCustomer(amObj.getNomineeDetails()[0].getCustomerId());
                   					}
                   					else{
                   						req.setAttribute("msg","There are no Nominee's for this account No.");
                   						req.setAttribute("msgs","There are no Nominee's for this account No.");
                   					}
      		    		     			req.setAttribute("cust",cmObj);
      		    		     			req.setAttribute("Nominee","nom");
      		    					 
                   					path=MenuNameReader.getScreenProperties("3032");
                          			System.out.println("path is---------------"+path);
                          			req.setAttribute("flag",path); 
                          			  
                          		  }*/
                        		  
                     			}
                     			else{
                     				req.setAttribute("msg","Account not Verified");
                     				//on 25.04.2009
                     				 req.setAttribute("getreceipt","enable");
                          	    	req.setAttribute("setreceipt","enable");
                     				//req.setAttribute("visible","visible");
                     				System.out.println("setting Attribute Visible");
                     				amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
                 				if(sbForm.getBeforesub()!=null&&sbForm.getBeforesub().trim().equals("unverified"))
                 				{
                     				sbForm.setCustname(cmObj.getName());   
                 				sbForm.setCid(String.valueOf(amObj.getCid()));
                 				req.setAttribute("AccountDetails",amObj);
                 				sbForm.setScrollNum(String.valueOf(amObj.getRef_No()));
                 				sbForm.setDate(amObj.getAccOpenDate());
                 				sbForm.setName(amObj.getAccName());
                 				sbForm.setAmount(String.valueOf(amObj.getTransAmount()));
                 				System.out.println(sbForm.getDetailsCombo());
                 				req.setAttribute("TabNum","0");
                 				//System.out.println("sbForm.getTabPaneHeading()=="+sbForm.getTabPaneHeading());
                 				sbForm.setCid(String.valueOf(amObj.getCid()));
                 				if(amObj.getNomineeDetails()!=null&&amObj.getNom_regno()!=0){
                 					sbForm.setNom("1");
                 					
                 					if(amObj.getNomineeDetails()[0].getCustomerId()>0){
                 					req.setAttribute("visible","visible");
                 					sbForm.setCheck("checkin");
                 					req.setAttribute("nomdetailhasaccount","nomdetailhasaccount");
                 					sbForm.setNomcid(String.valueOf(amObj.getNomineeDetails()[0].getCustomerId()));
                 					sbForm.setNomcidrelation(amObj.getNomineeDetails()[0].getNomineeRelation());
                 					}
                 					else{
                 						req.setAttribute("nomdetail","nomdetail");
                 					sbForm.setNomname(amObj.getNomineeDetails()[0].getNomineeName());
                 					sbForm.setNomdob(amObj.getNomineeDetails()[0].getNomineeDOB());
                 					if(amObj.getNomineeDetails()[0].getSex()==1)
                 					sbForm.setNomsex("Male");
                 					else
                 						sbForm.setNomsex("Female");
                 					
                 					sbForm.setNomrelation(amObj.getNomineeDetails()[0].getNomineeRelation());
                 					sbForm.setNomaddress(amObj.getNomineeDetails()[0].getNomineeAddress());
                 					}
                 					
                 				}
                 				
                 				if(amObj.getJointCid()!=null&&amObj.getJointCid().length>0){
                 					sbForm.setJointh("1");
                 					sbForm.setJointcid(String.valueOf(amObj.getJointCid()[0]));
                 				}
                 				if(amObj.getSigObj()!=null&&amObj.getSigObj().length>0){
                 					sbForm.setChqbook(amObj.getSigObj()[0].getTypeOfOperation().trim());
                 				}
                 				sbForm.setBeforesub("");
                 				//setting receipt details
                     			}
                 				else{
                 				
                 					cmObj=fcDelegate.getCustomer(Integer.parseInt(sbForm.getCid().trim()));
                         	    	
                         	    	if(cmObj!=null){
                         	    	sbForm.setCustname(cmObj.getName());  
                         	    	System.out.println("before introducer");
                         	    	//check for Introducer here=================
                         	    	if(!sbForm.getIntroAcType().equals("SELECT")&&!sbForm.getIntroAcNum().equals("0")){
                         	    		System.out.println("Account no is==========="+sbForm.getIntroAcNum());
                         	    	AccountObject acntObject = fcDelegate.getIntroducerAcntDetails(sbForm.getIntroAcType(),Integer.parseInt(sbForm.getIntroAcNum()));
                         	    	if(acntObject==null)
                         	    	{
                         	    		req.setAttribute("msg","Introducer Account no. does not exist");
                         	    		req.setAttribute("subenable","subenable");
                         	    	}
                         	    	else{
                         	    		//sbForm.setCustname(acntObject.getAccname());
                         	    		
                         	    		
                         	    	}
                         	    	}  //inroducer ends here
                         	    	
                         	    	//receipt details
                         	    	if(sbForm.getScrollNum()!=null){
                         	    	if(sbForm.getScrollNum().length()!=0&&sbForm.getScrollNum()!=null){
                         	    		accountobject=fcDelegate.receiptDetailsOnScroll(sbForm.getAcType(),sbForm.getScrollNum().trim());
                         	    		if(accountobject==null){
                         	    			req.setAttribute("msg","Please Enter a Proper Scroll no.");
                         	    			req.setAttribute("subenable","subenable");
                         	    		}
                         	    		else if(accountobject!=null){
                         	    			System.out.println("accountobject.getAccname()============>>>>>>>>>>>>>>>>"+accountobject.getAccname());
                         	    			if (accountobject.getVerified()!=null){
                             	    			req.setAttribute("msg"," Scroll Number Already Used");
                             	    			req.setAttribute("subenable","subenable");
                             	    		}
                         	    			else{
                             	    		sbForm.setName(accountobject.getAccname());
                             	    		sbForm.setAmount(String.valueOf(accountobject.getAmount()));
                             	    		sbForm.setDate(fcDelegate.getSysDate());
                         	    			}
                         	    		}
                         	    		
                         	    	}
                         	    	}
                         	    	
                         	    	}
                         	    	if(sbForm.getNom()!=null&&sbForm.getNom().equals("1")){
                         	    		System.out.println("Inside Nominee==============");
                         	    		req.setAttribute("visible","visible");
                         	    		if(sbForm.getCheck()!=null&&sbForm.getCheck().equals("checkin")){
                         	    			req.setAttribute("nomdetailhasaccount","nomdetailhasaccount");
                         	    			if(sbForm.getNomcid()!=null&&sbForm.getNomcid().length()!=0){
                         	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getNomcid()));
                         	    		if(cumaster==null){
                         	    			req.setAttribute("msg","Nominee customer id does not exist");
                         	    			req.setAttribute("subenable","subenable");
                         	    			sbForm.setForward("");
                         	    		}
                         	    			}
                         	    			else{
                         	    				req.setAttribute("msg","Please Enter Nominee customer id ");
                             	    			req.setAttribute("subenable","subenable");
                             	    			sbForm.setForward("");
                         	    			}
                         	    			
                         	    		}
                         	    		else{
                         	    			
                         	    			req.setAttribute("nomdetail","nomdetails");
                         	    		}
                         	    		
                         	    		
                         	    	}
                     			      
                         	    	if(sbForm.getJointh()!=null&&sbForm.getJointh().equals("1")){
                         	    		//Checking for Joint Holder
                         	    		if(sbForm.getJointcid()!=null&&sbForm.getJointcid().length()!=0){
                             	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getJointcid()));
                             	    		if(cumaster==null){
                             	    			req.setAttribute("msg","JointHolder's customer id does not exist");
                             	    			req.setAttribute("subenable","subenable");
                             	    			sbForm.setForward("");
                             	    		}
                             	    			}
                             	    			else{
                             	    				req.setAttribute("msg","Please Enter JointHolder's customer id ");
                                 	    			req.setAttribute("subenable","subenable");
                                 	    			sbForm.setForward("");
                             	    			}	
                         	    		
                         	    		
                         	    	}
                 					
                 					//End is here
                 				}
                 				//sbForm.setScrollNum(amObj.get);
                 				System.out.println("Just before Updating the values");
                 			      if(sbForm.getForward()!=null){
                 			    	  
                  			    	 System.out.println("---------Inside Submitting");
                   			       if(sbForm.getForward().equalsIgnoreCase("submit"))
                   			  {
                   			    	   
                   	/*if(sbForm.getAcNum().trim().length()==0||sbForm.getAmount().trim().length()==0||sbForm.getCid().trim().length()==0||sbForm.getCid().trim().length()==0)
                   	{}*/		    	   
                   			    	   
                   			    	   
                   				System.out.println("Onclick of submit in action class");
                   			AccountMasterObject accountmasterobject = new AccountMasterObject();
                   			
                   			CustomerMasterObject customermasterobject = new CustomerMasterObject();
                   			UserVerifier  uv = new UserVerifier();
                   			
                   		
                   			
                   			accountmasterobject.setAccType(String.valueOf(sbForm.getAcType()));
                 			if(Integer.parseInt(sbForm.getAcNum())>0&&sbForm.getAcNum().trim().length()>0)
                 				accountmasterobject.setAccNo(Integer.parseInt(sbForm.getAcNum().trim()));
                 			else
                 				accountmasterobject.setAccNo(0);
                 				
                 			accountmasterobject.setCid(Integer.parseInt(sbForm.getCid()));
                 			accountmasterobject.setMailingAddress(1);
                 			if(sbForm.getNom()!=null&&sbForm.getNom().equals("1")){
                 				System.out.println("At 494 in FrontcounterAction2 inside nominee detail");
                 				//For Nominee Details
                 				if(sbForm.getCheck()!=null&&sbForm.getCheck().equals("checkin")){
                 				NomineeObject[] nom1=new NomineeObject[1];
                 				if(sbForm.getNomcid()!=null&&sbForm.getNomcid().trim().length()!=0){
                      	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getNomcid().trim()));
                      	    		System.out.println("sbForm.getNomcid()---->>>>>>"+sbForm.getNomcid());
                      	    		if(cumaster!=null){
                      	    			nom1[0]=new NomineeObject();
                      	    			System.out.println("--------->cumaster is not null----");
                      	    			System.out.println("--------->cumaster is ----CID--->"+sbForm.getNomcid());
                      	    			nom1[0].setCustomerId(Integer.parseInt(sbForm.getNomcid()));
                      	    			nom1[0].setNomineeName(cumaster.getName());
                      	    			nom1[0].setNomineeDOB(cumaster.getDOB());
                      	    			if(cumaster.getSex()!=null&&cumaster.getSex().equals("M"))
                      	    			nom1[0].setSex(0);
                      	    			else if(cumaster.getSex()!=null&&cumaster.getSex().equals("F"))
                      	    				nom1[0].setSex(1);
                      	    			addr=new Address();
                      	    			addr=(Address)cumaster.getAddress().get(1);
                      	    			nom1[0].setNomineeAddress(addr.getAddress());
                      	    			nom1[0].setNomineeRelation(sbForm.getNomcidrelation());
                      	    			nom1[0].setPercentage(Double.parseDouble(sbForm.getNompercentage()));
                      	    			nom1[0].setFromDate(fcDelegate.getSysDate());
                      	    			nom1[0].setRegNo(amObj.getNom_regno());
                      	    			accountmasterobject.setNomineeDetails(nom1);
                      	    		
                      	    		}
                      	    			}
                      	    			else{
                      	    				//Nom Details
                      	    				req.setAttribute("msg","Please Enter Nominee customer id ----563");
                          	    			req.setAttribute("subenable","subenable");
                          	    			sbForm.setForward("");
                      	    			}
                 			}
                 				else{
                					//if nominee cid is not entered
                					NomineeObject[] nom1=new NomineeObject[1];
                					nom1[0]=new NomineeObject();
                 	    			//nom1[0].setCustomerId(Integer.parseInt(sbForm.getNomcid()));
                 	    			nom1[0].setNomineeName(sbForm.getNomname());
                 	    			nom1[0].setNomineeDOB(sbForm.getNomdob());
                 	    			if(sbForm.getNomsex().trim()!=null&&sbForm.getNomsex().trim().equals("male"))
                 	    			nom1[0].setSex(0);
                 	    			else if(sbForm.getNomsex().trim()!=null&&sbForm.getNomsex().trim().equals("female"))
                 	    				nom1[0].setSex(1);
                 	    			
                 	    			
                 	    			nom1[0].setNomineeAddress(sbForm.getNomaddress());
                 	    			nom1[0].setNomineeRelation(sbForm.getNomrelation());
                 	    			nom1[0].setPercentage(Double.parseDouble(sbForm.getNompercentage()));
                 	    			nom1[0].setFromDate(fcDelegate.getSysDate());
                 	    			
                 	    			accountmasterobject.setNomineeDetails(nom1);	
                					
                				}
                 			
                 			}
                 			else{
                 				System.out.println("=============>Setting nominee as null");
                 				accountmasterobject.setNomineeDetails(null);
                 			}
                 			
                 			
                 			//Joint Holder Information 
                 			if(sbForm.getJointh()!=null&&sbForm.getJointh().equals("1")){
                  	    		//Checking for Joint Holder
                 				accountmasterobject.setNoOfJointHolders(Integer.parseInt(sbForm.getJointh()));
                  	    		if(sbForm.getJointcid()!=null&&sbForm.getJointcid().length()!=0){
                      	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getJointcid()));
                      	    		if(cumaster!=null){
                      	    			int[] jcid=new int[1];	
 		    							jcid[0]=cumaster.getCustomerID();
 		    							accountmasterobject.setJointCid(jcid);
 		    							int[] addtyp=new int[1];
 		    							addtyp[0]=2;
 		    							accountmasterobject.setJointAddrType(addtyp);
                      	    			sbForm.setForward("");
                      	    		}
                      	    			}
                      	    			else{
                      	    				req.setAttribute("msg","Please Enter JointHolder's customer id ");
                          	    			req.setAttribute("subenable","subenable");
                          	    			sbForm.setForward("");
                      	    			}	
                  	    		
                  	    		
                  	    	}
                 			else{
                 				accountmasterobject.setNoOfJointHolders(0);
                 			}
                  	    	
                 			
                 			
                 			
                 			accountmasterobject.setChqBKIssue(sbForm.getChqbook());
                 			//accountmasterobject.setNoOfJointHolders(accountmasterobject.getCount());
                 			accountmasterobject.setLastTrnDate(fcDelegate.getSysDate());
                 			//accountmasterobject.setLastTrnSeq(0);// commented on 14/02/2007  Swapna B
                 			accountmasterobject.setLastTrnSeq(1);
                 			accountmasterobject.setTransAmount(Double.parseDouble(sbForm.getAmount()));
                 			accountmasterobject.setPassBkSeq(0);
                 			accountmasterobject.setLedgerSeq(0);
                 			accountmasterobject.setGLRefCode(Integer.parseInt(sbForm.getAcType()));
                 			if(sbForm.getIntroAcNum().length()>0 && Integer.parseInt(sbForm.getIntroAcNum().trim())!=0 )
                 			{
                 				accountmasterobject.setIntrAccNo(Integer.parseInt(sbForm.getIntroAcNum()));
                 				accountmasterobject.setIntrAccType(sbForm.getIntroAcType());
                 			}
                 			else
                 			{
                 				accountmasterobject.setIntrAccType("");
                 				accountmasterobject.setIntrAccNo(0);
                 			}
                 			accountmasterobject.setAccStatus("O");
                 			accountmasterobject.setFreezeInd("F");
                 			accountmasterobject.setAccOpenDate(fcDelegate.getSysDate());
                 			accountmasterobject.setAccCloseDate(null);
                 			accountmasterobject.setLastLine(0);
                 			accountmasterobject.setGLRefCode(Integer.parseInt(sbForm.getAcType()));
                 			//set Scroll no
                 			accountmasterobject.setRef_No(Integer.parseInt(sbForm.getScrollNum()));
                 			accountmasterobject.uv.setUserDate(fcDelegate.getSysDate());
                 			accountmasterobject.uv.setUserId(user);//hardcoded
                 			accountmasterobject.uv.setUserTml(tml);
                 			System.out.println("---------accountmasterobject.uv.setUserDate--------"+accountmasterobject.uv.getUserDate());
                 			System.out.println("In action at line===========464===========");
                 			System.out.println("==================="+accountmasterobject.getAccOpenDate());
                 		
                  			
                  			
                  			int int_result=fcDelegate.storeAccountMaster(accountmasterobject,1);
             					System.out.println("Account Number:******"+int_result);
             					if(int_result>0)
             					{
             				    req.setAttribute("msg","Account No  "+int_result+"  Updated Successfully");
             					
             					
                   			  }
             				
             					else{
             						req.setAttribute("msg","Error Account Not Updated ");	
             				    req.setAttribute("verify","verify");
                      			//sbForm.setAcNum(String.valueOf(int_result));
             				    sbForm.setForward("");
                                  System.out.println("=====Succesfully Updated=======");
                  			
             					}
                  			
                   		}
                   			       			       
                   		
                   			       
                 			      }
                 				if(sbForm.getForward().equals("delete")){
                 					accountmasterobject= new AccountMasterObject();
                 					accountmasterobject.setAccNo(Integer.parseInt(sbForm.getAcNum()));
            						accountmasterobject.setAccType(sbForm.getAcType());
            						accountmasterobject.uv.setUserId(user);//HARD CODED
            						accountmasterobject.uv.setUserTml(tml);//HARD CODED
            						accountmasterobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());			
            						accountmasterobject.setRef_No(Integer.parseInt(sbForm.getScrollNum()));
                 					
                 					UserActivityMasterObject user_activ=new UserActivityMasterObject();
                 					user_activ.setUser_id(user);//HARD CODED
                 					user_activ.setTml_no(tml);//HARD CODED
                 					user_activ.setPage_visit("SB/CA Opening form");
                 					user_activ.setActivity("Deleting SB/CA Account");//HARD CODED
                 					user_activ.setAc_no(Integer.parseInt(sbForm.getAcNum()));
                 					user_activ.setCid(Integer.parseInt(sbForm.getCid()));
                 					user_activ.setActivity_date(fcDelegate.getSysDate());
                 					user_activ.setActivity_time(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                 					fcDelegate.storeUserActivity(user_activ);
                 					int del=fcDelegate.storeAccountMaster(accountmasterobject,2);
                 					if(del!=0)
                 						req.setAttribute("msg","Account No."+sbForm.getAcNum()+"  deleted successfully");
                 					else
                 						req.setAttribute("msg","Account No."+sbForm.getAcNum()+"  could not be deleted");
                 				}
                 				
                 		          
                 				
                     			}//end of not verified
                 				
                 				if(sbForm.getDetailsCombo().equals("Personal")&&sbForm.getDetailsCombo().length()!=0){
                        			CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                        			req.setAttribute("cust",cust);
                        			
                        			
                        			path=MenuNameReader.getScreenProperties("3032");
                        			System.out.println("path is---------------"+path);
                        			req.setAttribute("flag",path); 
                        			  
                        		  }
                 				
                 				if(sbForm.getDetailsCombo().equals("JointHolders")&&sbForm.getDetailsCombo().length()!=0){
                        			//for JointHolder Details
                 					if(amObj.getJointCid()!=null&&amObj.getJointCid().length>0)
   		    					 {	System.out.println("At 7279==================>");
   		    						 cmObj=fcDelegate.getCustomer(amObj.getJointCid()[0]);
   		    		     			req.setAttribute("cust",cmObj);
   		    		     			req.setAttribute("jointholder","joint");
   		    					 }
                 					path=MenuNameReader.getScreenProperties("3032");
                        			System.out.println("path is---------------"+path);
                        			req.setAttribute("flag",path); 
                        			  
                        		  }
                 				//for Nominee
                 				if(sbForm.getDetailsCombo().equals("Nominee")&&sbForm.getDetailsCombo().length()!=0){
                        			//for JointHolder Details
                 					if(amObj.getNomineeDetails()!=null)
   		    					 {
                 						if(amObj.getNomineeDetails().length>0){
                 					System.out.println("At 407==================>");
   		    						 cmObj=fcDelegate.getCustomer(amObj.getNomineeDetails()[0].getCustomerId());
   		    		     			req.setAttribute("cust",cmObj);
   		    		     			req.setAttribute("Nominee","nom");
   		    					 }
   		    					 }
                 					path=MenuNameReader.getScreenProperties("3032");
                        			System.out.println("path is---------------"+path);
                        			req.setAttribute("flag",path); 
                        			  
                        		  }
                 				
                 				
                 				if(sbForm.getDetailsCombo().equals("Signature Ins")&&sbForm.getDetailsCombo().length()!=0){
                        			CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                        			req.setAttribute("cust",cust);
                        			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType());
                        			
                        			req.setAttribute("sigObject",signObject);
                        			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                        			path=MenuNameReader.getScreenProperties("3056");
                        			System.out.println("path is-------741	--------"+path);
                        			req.setAttribute("flag",path); 
                        			  
                        		  }
                    		  if(sbForm.getDetailsCombo().equals("Cash")&&sbForm.getDetailsCombo().length()!=0){
                        			//System.out.println("accountObj is"+accountObj.getScrollno());
                        			req.setAttribute("master",accountObj);
                        			req.setAttribute("sbcamaster",amObj);
                        			req.setAttribute("data","data");
                        			
                        			
                        			path=MenuNameReader.getScreenProperties("3031");
                        			System.out.println("path is---------------"+path);
                        			req.setAttribute("flag",path); 
                        			  
                        		  }
                    		  if(sbForm.getDetailsCombo().equals("Introducer Type")&&sbForm.getDetailsCombo().length()!=0){
                    			  System.out.println("====in Introducer ==="+path);
          						/*req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
          						req.setAttribute("panelName",CommonPanelHeading.getIntroucer());
          						System.out.println("====sbForm.getIntroAcNum()====="+sbForm.getIntroAcNum());
          						System.out.println("====sbForm.getIntroAcType()====="+sbForm.getIntroAcType());

          						

          						AccountObject acntObject =fcDelegate.getIntroducerAcntDetails(sbForm.getIntroAcType(),Integer.parseInt(sbForm.getIntroAcNum()));
          						System.out.println("====in Introducer ==="+path);

          						if (acntObject != null) {
          							System.out.println("acntObject.getCid()=="+ acntObject.getCid());
          							req.setAttribute("personalInfo", fcDelegate.getCustomer(acntObject.getCid()));
          						} else {
          							req.setAttribute("personalInfo", new CustomerMasterObject());
          						}*/
                    			  
                    			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getIntroAcNum()),sbForm.getIntroAcType().trim());
                    			  if(amObj!=null){
                    			  CustomerMasterObject custin=fcDelegate.getCustomer(amObj.getCid());
                    			  if(custin!=null){
                    			  CustomerMasterObject cust=fcDelegate.getCustomer(Integer.parseInt(custin.getIntroducerId()));
                      			req.setAttribute("cust",cust);
                      			req.setAttribute("Introducer","Introducer");
                      			
                      			path=MenuNameReader.getScreenProperties("3032");
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                    			  } 
                    			}
                      		  }
                    		  
                    		  //Nominee
/*                        		  if(sbForm.getDetailsCombo().equals("Nominee")&&sbForm.getDetailsCombo().length()!=0){
                    			  System.out.println("Inside Nominee---->");
                    			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
          						req.setAttribute("flag",MenuNameReader.getScreenProperties(String.valueOf(13026)));			
          						//shDelegate=new ShareDelegate();
          						//req.setAttribute("pageId",path);
          						//setInitParam(req, path, tddelegate);
          						
          						
          						//dep_open_obj=tddelegate.getDepositMasterValues(dep_renewal.getAc_no(), dep_renewal.getAc_type());
          						nominnee=amObj.getNomineeDetails();
          						System.out.println("NOmiNEEE CID----> "+nominnee[0].getRegNo());
          						//CustomerMasterObject cust_obj=tddelegate.getCustomer(nominnee[0].getRegNo());
          						
          						//String NomAddress=fcDelegate.getNomineeAddrDetails(nominnee[0].getRegNo());
          						//System.out.println("THe address of the customer is "+cust_obj.getAddress());
          						//req.setAttribute("custdetails",cust_obj);
          						
          						//req.setAttribute("NOMADDR",NomAddress);
          						req.setAttribute("NomDetail", nominnee);
          						req.setAttribute("HideTheFields",null);

                      			  
                      		  }*/
                 				path=MenuNameReader.getScreenProperties(String.valueOf(3001));	  
                 				setSBOpeningInitParams(req,path,fcDelegate);	 
                 				setTabbedPaneInitParams(req, path, fcDelegate,sbForm);
                 				req.setAttribute("SBOpeningForm",sbForm);
                 				req.setAttribute("SubmitValue","Upadte");
                 				req.setAttribute("ActionPath",map.getPath().trim());
                 				return map.findForward(ResultHelp.getSuccess());
                 			}else{
                 				System.out.println(" i am here just checkin");
                 				req.setAttribute("FocusTo","acNum");
                 				req.setAttribute("msg","Account not Found");
                 				req.setAttribute("AccountNotFound",new Boolean(true));
                 				return map.findForward(ResultHelp.getSuccess());
                 			}
                 		}
                 		
                 		
                 		
                 		
                 		
                 		
                 	
                 		else if(sbForm.getAcNum().trim().equals("0")&&Integer.parseInt(sbForm.getCid())==0){
                     		setSBOpeningInitParams(req,path,fcDelegate);
                     		req.setAttribute("FocusTo","cid");
                     		req.setAttribute("NewAccount",new Boolean(false));
                     		return map.findForward(ResultHelp.getSuccess());
                     	}
                 		
                 		
                 	     if(sbForm.getAcNum().trim().equals("0")&&Integer.parseInt(sbForm.getCid().trim())!=0)
           		         {
                 	    	 
                 	    	req.setAttribute("NewAccount",new Boolean(false));
                 	    	 req.setAttribute("getreceipt","enable");
                 	    	req.setAttribute("setreceipt","enable");
                 	    	try{
                 	    	cmObj=fcDelegate.getCustomer(Integer.parseInt(sbForm.getCid().trim()));
                 	    	}
                 	    	catch(Exception ex){
                 	    		
                 	    		req.setAttribute("msg","Customer not found");
                 	    		sbForm.setCid("");
                 	    		sbForm.setForward("");
                 	    		sbForm.setCustname("");
                 	    	}
                 	    	if(cmObj!=null){
                 	    	sbForm.setCustname(cmObj.getName());  
                 	    	System.out.println("before introducer");
                 	    	//req.setAttribute("FocusTo","detailsCombo");
                 	    	//return map.findForward(ResultHelp.getSuccess());
                 	    	//}
                 	    	//check for Introducer here=================
                 	    	//if(cmObj!=null){
                 	    	if(sbForm.getIntroAcNum().trim().length()>0&&!sbForm.getIntroAcType().equals("SELECT")&&!sbForm.getIntroAcNum().equals("0")){
                 	    		System.out.println("Account no is==========="+sbForm.getIntroAcNum());
                 	    	AccountObject acntObject = fcDelegate.getIntroducerAcntDetails(sbForm.getIntroAcType(),Integer.parseInt(sbForm.getIntroAcNum()));
                 	    	if(acntObject==null)
                 	    	{
                 	    		req.setAttribute("msg","Introducer Account no. does not exist");
                 	    		req.setAttribute("subenable","subenable");
                 	    	}
                 	    	else{
                 	    		//sbForm.setCustname(acntObject.getAccname());
                 	    		req.setAttribute("FocusTo","detailsCombo");
                 	    		//return map.findForward(ResultHelp.getSuccess());
                 	    	}
                 	    	}  //inroducer ends here
                 	    	
                 	    	//receipt details
                 	    	if(sbForm.getScrollNum()!=null){
                     	    	if(sbForm.getScrollNum().length()!=0&&sbForm.getScrollNum()!=null){
                     	    		accountobject=fcDelegate.receiptDetailsOnScroll(sbForm.getAcType(),sbForm.getScrollNum().trim());
                     	    		if(accountobject==null){
                     	    			req.setAttribute("msg","Please Enter a Proper Scroll no.");
                     	    			req.setAttribute("subenable","subenable");
                     	    		}
                     	    		else if(accountobject!=null){
                     	    			System.out.println("accountobject.getAccname()============>>>>>>>>>>>>>>>>"+accountobject.getAccname());
                     	    			if (accountobject.getVerified().trim().length()!=0){
                         	    			req.setAttribute("msg"," Scroll Number Already Used");
                         	    			req.setAttribute("subenable","subenable");
                         	    		}
                     	    			else{
                         	    		sbForm.setName(accountobject.getAccname());
                         	    		sbForm.setAmount(String.valueOf(accountobject.getAmount()));
                         	    		sbForm.setDate(fcDelegate.getSysDate());
                     	    			}
                     	    		}
                     	    		
                     	    	}
                     	    	}
                 	    	
                 	    	}
                 	    	//if(!sbForm.getChqbook().equals("SELECT") && Integer.parseInt(sbForm.getCid().trim())!=0){
                 	    	if(sbForm.getNom()!=null&&sbForm.getNom().equals("1")){
                 	    		System.out.println("Inside Nominee==============");
                 	    		req.setAttribute("visible","visible");
                 	    		req.setAttribute("FocusTo","check");
                 	    		System.out.println("sbForm.getNomcid()======>"+sbForm.getNomcid());
                 	    		if(sbForm.getCheck()!=null&&sbForm.getCheck().equals("checkin")){
                 	    			req.setAttribute("nomdetail",null);
                 	    			req.setAttribute("nomdetailhasaccount","hasaccount");
                 	    			req.setAttribute("FocusTo","nomcid");
                 	    			if(sbForm.getNomcid()!=null&&sbForm.getNomcid().length()!=0){
                 	    				try{
                 	    					System.out.println("sbForm.getNomcid()======>"+sbForm.getNomcid());
                 	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getNomcid()));
                 	    				
                 	    		if(cumaster==null){
                 	    			req.setAttribute("msg","Nominee customer id does not exist");
                 	    			req.setAttribute("subenable","subenable");
                 	    			req.setAttribute("FocusTo","nomcid");
                 	    			sbForm.setForward("");
                 	    		}
                 	    		                 	    			
                 	    				}
                 	    				catch(Exception ex){req.setAttribute("msg","Nominee customer does not exist");
                 	    				req.setAttribute("subenable","subenable");
                 	    				}
                 	    				}
                 	    			else{
                 	    				req.setAttribute("msg","Please Enter Nominee customer");
                     	    			req.setAttribute("subenable","subenable");
                     	    			sbForm.setForward("");
                     	    			req.setAttribute("FocusTo","nomcid");
                 	    			}
                 	    			
                 	    		}
                 	    		else{
                 	    		//	req.setAttribute("visible",null);
                 	    			req.setAttribute("nomdetail","nomdetails");
                 	    			req.setAttribute("nomdetailhasaccount",null);
                 	    			
                 	    		}
                 	    		
                 	    		
                 	    	}
                 	    	//}
             			      
                 	    	if(sbForm.getJointh()!=null&&sbForm.getJointh().equals("1")){
                 	    		//Checking for Joint Holder
                 	    		req.setAttribute("FocusTo","jointcid");
                 	    		if(sbForm.getJointcid()!=null&&sbForm.getJointcid().length()!=0){
                 	    			try{
                 	    			
                     	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getJointcid()));
                 	    			
                     	    		if(cumaster==null){
                     	    			req.setAttribute("msg","JointHolder's customer id does not exist");
                     	    			req.setAttribute("subenable","subenable");
                     	    			sbForm.setForward("");
                     	    		}
                 	    			}
                 	    			catch(Exception ex){req.setAttribute("msg","Joint holder customer does not exist");
                 	    			req.setAttribute("subenable","subenable");
                 	    			}
                     	    			}
                     	    			else{
                     	    				req.setAttribute("msg","Please Enter JointHolder's customer id ");
                         	    			req.setAttribute("subenable","subenable");
                         	    			sbForm.setForward("");
                     	    			}	
                 	    		
                 	    		
                 	    	}
                 	    	//for details
                 	    	
                 	    	if(sbForm.getCid().trim()!=null&& sbForm.getCid().trim().length()>0&&sbForm.getDetailsCombo().equals("Personal")&&sbForm.getDetailsCombo().length()!=0){
                    			CustomerMasterObject cust=fcDelegate.getCustomer(Integer.parseInt(sbForm.getCid()));
                    			req.setAttribute("cust",cust);
                    			req.setAttribute("FocusTo","chqbook");
                    			
                    			path=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
             				
             				if(sbForm.getDetailsCombo().equals("JointHolders")&&sbForm.getDetailsCombo().length()!=0&&sbForm.getJointcid().trim()!=null&& sbForm.getJointcid().trim().length()>0){
                    			//for JointHolder Details
             						System.out.println("At 7279==================>");
		    						 cmObj=fcDelegate.getCustomer(Integer.parseInt(sbForm.getJointcid()));
		    		     			req.setAttribute("cust",cmObj);
		    		     			req.setAttribute("FocusTo","chqbook");
		    		     			req.setAttribute("jointholder","joint");
		    					 
             					path=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
             				//for Nominee
             				
             				/*if(sbForm.getDetailsCombo().equals("Nominee")&&sbForm.getDetailsCombo().length()!=0&&sbForm.getNomcid().trim()!=null&& sbForm.getNomcid().trim().length()>0){
                    			//for Nominee Details
             					
             					System.out.println("At 970==================>");
		    						 cmObj=fcDelegate.getCustomer(Integer.parseInt(sbForm.getNomcid()));
		    		     			req.setAttribute("cust",cmObj);
		    		     			req.setAttribute("FocusTo","chqbook");
		    		     			req.setAttribute("Nominee","nom");
		    					 
             					path=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }*/
             				
             				/*
             				if(sbForm.getDetailsCombo().equals("Signature Ins")&&sbForm.getDetailsCombo().length()!=0){
                    			CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType());
                    			
                    			req.setAttribute("sigObject",signObject);
                    			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                    			path=MenuNameReader.getScreenProperties("3012");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag","/SingnatureInst.jsp"); 
                    			  
                    		  }
                		  if(sbForm.getDetailsCombo().equals("Cash")&&sbForm.getDetailsCombo().length()!=0){
                    			//System.out.println("accountObj is"+accountObj.getScrollno());
                    			req.setAttribute("master",accountObj);
                    			req.setAttribute("sbcamaster",amObj);
                    			req.setAttribute("data","data");
                    			
                    			
                    			path=MenuNameReader.getScreenProperties("3031");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }*/
                		  if(sbForm.getDetailsCombo().equals("Introducer Type")&&sbForm.getDetailsCombo().length()!=0 && sbForm.getCid().trim().length()!=0){
                			  System.out.println("====in Introducer ==="+path);
      						/*req.setAttribute("flag",MenuNameReader.getScreenProperties("0001"));
      						req.setAttribute("panelName",CommonPanelHeading.getIntroucer());
      						System.out.println("====sbForm.getIntroAcNum()====="+sbForm.getIntroAcNum());
      						System.out.println("====sbForm.getIntroAcType()====="+sbForm.getIntroAcType());

      						

      						AccountObject acntObject =fcDelegate.getIntroducerAcntDetails(sbForm.getIntroAcType(),Integer.parseInt(sbForm.getIntroAcNum()));
      						System.out.println("====in Introducer ==="+path);

      						if (acntObject != null) {
      							System.out.println("acntObject.getCid()=="+ acntObject.getCid());
      							req.setAttribute("personalInfo", fcDelegate.getCustomer(acntObject.getCid()));
      						} else {
      							req.setAttribute("personalInfo", new CustomerMasterObject());
      						}*/
                			  //amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getIntroAcNum().trim()),sbForm.getIntroAcType().trim()); 
                			  CustomerMasterObject custin=fcDelegate.getCustomer(Integer.parseInt(sbForm.getCid()));
                			  if(custin!=null){
                			  CustomerMasterObject cust=fcDelegate.getCustomer(Integer.parseInt(custin.getIntroducerId()));
                			  req.setAttribute("cust",cust);
                  			req.setAttribute("FocusTo","chqbook");
                  			req.setAttribute("Introducer","intro");
                  			path=MenuNameReader.getScreenProperties("3032");
                  			System.out.println("path is---------------"+path);
                  			req.setAttribute("flag",path); 
                			  }
                  			  
                  		  }
                		  
//end of details
                 	    	
             			      if(sbForm.getForward()!=null){
             			    	  
             			    	 System.out.println("---------Inside Submitting");
              			       if(sbForm.getForward().equalsIgnoreCase("submit"))
              			        {
              				System.out.println("Onclick of submit in action class");
              			AccountMasterObject accountmasterobject = new AccountMasterObject();
              			
              			CustomerMasterObject customermasterobject = new CustomerMasterObject();
              			UserVerifier  uv = new UserVerifier();
              			
              		
              			
              			accountmasterobject.setAccType(String.valueOf(sbForm.getAcType()));
            			if(Integer.parseInt(sbForm.getAcNum())>0&&sbForm.getAcNum().trim().length()>0)
            				accountmasterobject.setAccNo(Integer.parseInt(sbForm.getAcNum().trim()));
            			else
            				accountmasterobject.setAccNo(0);
            			
            			SignatureInstructionObject[] signObject=new SignatureInstructionObject[1];
            			signObject[0]=new SignatureInstructionObject();
            			signObject[0].setCid(Integer.parseInt(sbForm.getCid()));
            			signObject[0].setSAccType(sbForm.getAcType());
            			signObject[0].setSAccNo(Integer.parseInt(sbForm.getAcNum()));
            			signObject[0].setName(sbForm.getName());
            			signObject[0].setTypeOfOperation(sbForm.getChqbook());
            			
            			accountmasterobject.setSigObj(signObject);
            			//signature instruction is set
            			
            			accountmasterobject.setCid(Integer.parseInt(sbForm.getCid()));
            			accountmasterobject.setMailingAddress(1);
            			if(sbForm.getNom()!=null&&sbForm.getNom().equals("1")){
            				//For Nominee Details
            				if(sbForm.getCheck()!=null&&sbForm.getCheck().equals("checkin")){
            					
            				NomineeObject[] nom1=new NomineeObject[1];
            				if(sbForm.getNomcid()!=null&&sbForm.getNomcid().length()!=0){
                 	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getNomcid()));
                 	    		
                 	    		if(cumaster!=null){
                 	    			nom1[0]=new NomineeObject();
                 	    			nom1[0].setCustomerId(Integer.parseInt(sbForm.getNomcid()));
                 	    			nom1[0].setNomineeName(cumaster.getName());
                 	    			nom1[0].setNomineeDOB(cumaster.getDOB());
                 	    			if(cumaster.getSex()!=null&&cumaster.getSex().equals("M"))
                 	    			nom1[0].setSex(0);
                 	    			else if(cumaster.getSex()!=null&&cumaster.getSex().equals("F"))
                 	    				nom1[0].setSex(1);
                 	    			
                 	    			addr=(Address)cumaster.getAddress().get(1);
                 	    			if(addr!=null)
                 	    			nom1[0].setNomineeAddress(addr.getAddress());
                 	    			nom1[0].setNomineeRelation(sbForm.getNomcidrelation());
                 	    			nom1[0].setPercentage(Double.parseDouble(sbForm.getNompercentage()));
                 	    			nom1[0].setFromDate(fcDelegate.getSysDate());
                 	    			
                 	    			accountmasterobject.setNomineeDetails(nom1);
                 	    		
                 	    		}
                 	    			}
                 	    			else{
                 	    				//Nom Details
                 	    				req.setAttribute("msg","Please Enter Nominee customer id 1132");
                     	    			req.setAttribute("subenable","subenable");
                     	    			sbForm.setForward("");
                 	    			}
            			
            			}
            				else{
            					//if nominee cid is not entered
            					NomineeObject[] nom1=new NomineeObject[1];
            					nom1[0]=new NomineeObject();
             	    			//nom1[0].setCustomerId(Integer.parseInt(sbForm.getNomcid()));
             	    			nom1[0].setNomineeName(sbForm.getNomname());
             	    			nom1[0].setNomineeDOB(sbForm.getNomdob());
             	    			if(sbForm.getNomsex().trim()!=null&&sbForm.getNomsex().trim().equals("male"))
             	    			nom1[0].setSex(0);
             	    			else if(sbForm.getNomsex().trim()!=null&&sbForm.getNomsex().trim().equals("female"))
             	    				nom1[0].setSex(1);
             	    			
             	    			
             	    			nom1[0].setNomineeAddress(sbForm.getNomaddress());
             	    			nom1[0].setNomineeRelation(sbForm.getNomrelation());
             	    			nom1[0].setPercentage(Double.parseDouble(sbForm.getNompercentage()));
             	    			nom1[0].setFromDate(fcDelegate.getSysDate());
             	    			
             	    			accountmasterobject.setNomineeDetails(nom1);	
            					
            				}
            			
            				
            			}
            			
            			
            			//Joint Holder Information 
            			if(sbForm.getJointh()!=null&&sbForm.getJointh().equals("1")){
             	    		//Checking for Joint Holder
            				accountmasterobject.setNoOfJointHolders(Integer.parseInt(sbForm.getJointh()));
             	    		if(sbForm.getJointcid()!=null&&sbForm.getJointcid().length()!=0){
                 	    		CustomerMasterObject cumaster=fcDelegate.getCustomer(Integer.parseInt(sbForm.getJointcid()));
                 	    		if(cumaster!=null){
                 	    			int[] jcid=new int[1];	
	    							jcid[0]=cumaster.getCustomerID();
	    							accountmasterobject.setJointCid(jcid);
	    							int[] addtyp=new int[1];
	    							addtyp[0]=2;
	    							accountmasterobject.setJointAddrType(addtyp);
                 	    			sbForm.setForward("");
                 	    		}
                 	    			}
                 	    			else{
                 	    				req.setAttribute("msg","Please Enter JointHolder's customer id ");
                     	    			req.setAttribute("subenable","subenable");
                     	    			sbForm.setForward("");
                 	    			}	
             	    		
             	    		
             	    	}
             	    	
            			
            			
            			
            			accountmasterobject.setChqBKIssue("F");
            			//accountmasterobject.setNoOfJointHolders(accountmasterobject.getCount());
            			accountmasterobject.setLastTrnDate(fcDelegate.getSysDate());
            			//accountmasterobject.setLastTrnSeq(0);// commented on 14/02/2007  Swapna B
            			accountmasterobject.setLastTrnSeq(1);
            			if(sbForm.getAmount().trim().length()>0){
            			accountmasterobject.setTransAmount(Double.parseDouble(sbForm.getAmount()));
            			}
            			
            			accountmasterobject.setPassBkSeq(0);
            			accountmasterobject.setLedgerSeq(0);
            			accountmasterobject.setGLRefCode(Integer.parseInt(sbForm.getAcType()));
            			if(sbForm.getIntroAcNum().length()>0 && Integer.parseInt(sbForm.getIntroAcNum().trim())!=0 )
            			{
            				accountmasterobject.setIntrAccNo(Integer.parseInt(sbForm.getIntroAcNum()));
            				accountmasterobject.setIntrAccType(sbForm.getIntroAcType());
            			}
            			else
            			{
            				accountmasterobject.setIntrAccType("");
            				accountmasterobject.setIntrAccNo(0);
            			}
            			accountmasterobject.setAccStatus("O");
            			accountmasterobject.setFreezeInd("F");
            			accountmasterobject.setAccOpenDate(fcDelegate.getSysDate());
            			accountmasterobject.setAccCloseDate(null);
            			accountmasterobject.setLastLine(0);
            			accountmasterobject.setGLRefCode(Integer.parseInt(sbForm.getAcType()));
            			//set Scroll no
            			accountmasterobject.setRef_No(Integer.parseInt(sbForm.getScrollNum()));
            			accountmasterobject.uv.setUserDate(fcDelegate.getSysDate());
            			accountmasterobject.uv.setUserId(user);
            			accountmasterobject.uv.setUserTml(tml);
            			System.out.println("---------accountmasterobject.uv.setUserDate--------"+accountmasterobject.uv.getUserDate());
            			System.out.println("In action at line===========360===========");
            			System.out.println("==================="+accountmasterobject.getAccOpenDate());
            		
             			
             			
             			int int_result=fcDelegate.storeAccountMaster(accountmasterobject,0);
        					System.out.println("Account Number:******"+int_result);
        					if(int_result>0)
        				    req.setAttribute("msg","New Account created and Account No is   "+int_result+"");
        					
        					else
        						req.setAttribute("msg","Error Account Not created ");	
        				    req.setAttribute("verify","verify");
                 			//sbForm.setAcNum(String.valueOf(int_result));
        				    sbForm.setForward("");
                             System.out.println("=====Succesfully Inserted=======");
             			
              		}
              			       
           		         }
              		}
                 	
                 	    path=MenuNameReader.getScreenProperties(sbForm.getPageId());
                        req.setAttribute("path", path);
                        fcDelegate=new FrontCounterDelegate();
                        System.out.println("At 370"+path);
                        setSBOpeningInitParams(req,path,fcDelegate);
                        
                        req.setAttribute("","acType");
                        req.setAttribute("pageId",path);
                        return map.findForward(ResultHelp.getSuccess());	
                 		
                 	
                 	/*else{
                 		return map.findForward(ResultHelp.getSuccess());
                 	}*/
                 }
                 else{
                	 req.setAttribute("msg","Select Account Type");
                     setSBOpeningInitParams(req,path,fcDelegate);
                     return map.findForward(ResultHelp.getSuccess());
                 }
             }
             else{
                 path=MenuNameReader.getScreenProperties("0000");
                 setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                 return map.findForward(ResultHelp.getError());
             }
         }catch(Exception e){
        	 e.printStackTrace();
           path=MenuNameReader.getScreenProperties("3001");
           setErrorPageElements(""+e.toString(),req,path);
           logger.error(e.getMessage());
           return map.findForward(ResultHelp.getError());
         }
         
     }
	  
	  

    
    //ODCC
	  //ODCC Apllication starts
      if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ODCCApplicationEntryMenu")){
          try{
          	ODCCApplnForm applnform=(ODCCApplnForm)form;
          	
          	
          	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(applnform.getPageId()));
          	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(applnform.getPageId()));
          	
          	System.out.println("At 373"+applnform);
             
          	req.setAttribute("pageNum",applnform.getPageId());
          	
             System.out.println("At 379"+applnform.getPageId());
             logger.info(applnform.getPageId()+"This is from Front Counter Log"+map.getPath());
             if(MenuNameReader.containsKeyScreen(applnform.getPageId())){
          	   System.out.println("At 292---------->");
                 path=MenuNameReader.getScreenProperties(applnform.getPageId());
                 applnform.setAc_no("0");
                 fcDelegate=new FrontCounterDelegate();
                 System.out.println("At 2523");
                 req.setAttribute("pageId",path);
                 req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                 System.out.println("At 2525 in ODCC Application entry");
                 req.setAttribute("sh",fcDelegate.getSHModule(0));
                 req.setAttribute("AcType",fcDelegate.getodccModule(0));
                 System.out.println("At 2528 in ODCC Application");
                 req.setAttribute("","acType");
                 return map.findForward(ResultHelp.getSuccess());
             }

             else{
                  path=MenuNameReader.getScreenProperties("0000");
                  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                  return map.findForward(ResultHelp.getError());
             }


         }catch(Exception e){
           path=MenuNameReader.getScreenProperties("0000");
           e.printStackTrace();
           System.out.println("At 393"+e);
           setErrorPageElements(""+e,req,path);
           return map.findForward(ResultHelp.getError());
         }
         
     }
      else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccApplication")){
      	System.out.println("at 1225inside ODCCAppln");
      	ODCCApplnForm applnform=(ODCCApplnForm)form;
          String path;
          applnform.setAppdate(fcDelegate.getSysDate());
          System.out.println("applnform=="+applnform);
          System.out.println("applnform=="+applnform.getPageId());
          try{
              req.setAttribute("pageNum",applnform.getPageId().trim());
              if(MenuNameReader.containsKeyScreen(applnform.getPageId())){
                  path=MenuNameReader.getScreenProperties(applnform.getPageId());
                  System.out.println(path);
                  fcDelegate=new FrontCounterDelegate();
                  System.out.println(fcDelegate);
                  System.out.println("actype=="+applnform.getAcType());
                  System.out.println("acNum=="+applnform.getAc_no());
                  
                  if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
          			  System.out.println("Co borrower to be added");
          			req.setAttribute("coborrower","coborrower");
          			}
                  if(applnform.getAcType().equals("SELECT")||applnform.getAc_no()==null)
                  {
                  
                  		req.setAttribute("closingmsg","Fill all the fields");
                  		path=MenuNameReader.getScreenProperties("3022");
                  		fcDelegate=new FrontCounterDelegate();
                          setSBOpeningInitParams(req,path,fcDelegate);
                          req.setAttribute("","acType");
                  		req.setAttribute("pageId",path);
                  		req.setAttribute("sh",fcDelegate.getSHModule(0));
                  		req.setAttribute("AcType",fcDelegate.getodccModule(0));
                       	req.setAttribute("ODCCApplnForm",applnform);
                       	return map.findForward(ResultHelp.getSuccess());
                       	}
                 
                  else{
                  	
                  	System.out.println("Account Number is not null");
                  	//--------------------CONDITION SHOULD B HERE
                  	if(!applnform.getAc_no().equals("0")){
                  	String acStatus="";
                  	req.setAttribute("sAcType",fcDelegate.getComboElements(0));
                      if(!applnform.getAcType().equalsIgnoreCase("SELECT")){
                    	  if(applnform.getAc_no()!=null&&!applnform.getAc_no().equals(""))
                    	  {
                    		odccmasterobject=fcDelegate.odccData(applnform.getAcType(),applnform.getAc_no());
                    		ODCCMasterObject accountinfoobject=fcDelegate.odccData(applnform.getAcType(), applnform.getAc_no());
                    		                      		  if(accountinfoobject!=null){
                    			  
                    			  if(accountinfoobject.getAccountStatus().equals("C")){
                    				  req.setAttribute("closingmsg","CLOSED");
                    			  }/*else if(accountinfoobject.getAccountStatus().equals("O")){
                    				  acStatus="OPENED";
                    			  }*/else if(accountinfoobject.getAccountStatus().equals("I")){
                    				req.setAttribute("closingmsg","INOPERATIVE");
                    			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                    				req.setAttribute("closingmsg","FREEZED");
                    			  }
                    			  accountObj=fcDelegate.getOdccAccount(applnform.getCombo_share_type(),String.valueOf(accountinfoobject.getShareAccNo()));
                    			
                    			//odccmasterobject=fcDelegate.odccData(applnform.getAcType(),applnform.getAc_no());
                    			  System.out.println("Name is "+accountObj.getAccname());
                    			applnform.setName(accountObj.getAccname());
                    			applnform.setTxt_sh_type(String.valueOf(accountinfoobject.getShareAccNo()));
                    			
                    			System.out.println("--------------------------CHECK HERE-------------------------");
                    			//System.out.println("Now data from details combo box is"+applnform.getDetails());
                    			//applnform.setTxt_sh_type(String.valueOf(accountinfoobject.getShareAccNo()));
                    			/*applnform.setName(accountinfoobject.getName());
                    			applnform.setName(accountinfoobject.getName());
                    			applnform.setName(accountinfoobject.getName());
                    			applnform.setName(accountinfoobject.getName());
                    			applnform.setName(accountinfoobject.getName());*/
                    			//System.out.println("Now data from details combo box is"+applnform.getDetails());
                    			//Checking for AccountVerification
                    			if(odccmasterobject.uv.getVerId()!=null){
                    				req.setAttribute("closingmsg","Account Already verified you cant do modification");
                    				req.setAttribute("verified","true");
                    		  if(applnform.getDetails().equals("Application")&&applnform.getDetails().length()!=0){
                    			
                    			  req.setAttribute("master",accountObj);
                    			req.setAttribute("odccmaster",odccmasterobject);
                    			  System.out.println("odccmasterobject value is ================"+odccmasterobject);
                    			  path=MenuNameReader.getScreenProperties("3028");
                    			
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
                    		  
                    		  
                    		if(applnform.getDetails().equals("EmploymentDetails")&&applnform.getDetails().length()!=0){
                    			 req.setAttribute("master",accountObj);
                     			req.setAttribute("odccmaster",odccmasterobject);
                    			path=MenuNameReader.getScreenProperties("3029");
                    			System.out.println("path is----------3029-----"+path);
                    			req.setAttribute("flag",path); 
                    			
                    		  }
                    		if(applnform.getDetails().equals("Loan&ShareDetails")&&applnform.getDetails().length()!=0){
                    			path=MenuNameReader.getScreenProperties("3030");
                    			ModuleObject[] mod=fcDelegate.getLoanMod(0);
                    			
                    			req.setAttribute("loanmod",mod);
                    			System.out.println("Length of ModuleObject Array is==="+mod.length);
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
                    		if(applnform.getDetails().equals("ReceiptDetails")&&applnform.getDetails().length()!=0){
                    			System.out.println("Receipt Details ");
                    			accountobject=fcDelegate.getOdccAccount(applnform.getAcType(),applnform.getAc_no());
                    			odccmasterobject=fcDelegate.odccData(applnform.getAcType(),applnform.getAc_no());
                    			System.out.println("accountobject------------->"+odccmasterobject.getRef_No());
                    			req.setAttribute("master",accountobject);
                    			req.setAttribute("newdata","newdata");
                    			req.setAttribute("odccmaster",odccmasterobject);
                    			
                    			
                    			
                    			path=MenuNameReader.getScreenProperties("3031");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
                    		if(applnform.getDetails().equals("DepositDetails")&&applnform.getDetails().length()!=0){
           					AccountMasterObject amcObj=fcDelegate.getAccountMaster(Integer.parseInt(applnform.getDacno()),applnform.getDacType().trim());
           					CustomerMasterObject cust=fcDelegate.getCustomer(amcObj.getCid());
                  			req.setAttribute("cust",cust);
                  			
                  			
                  			path=MenuNameReader.getScreenProperties("3055");
                  			System.out.println("path is---------------"+path);
                  			req.setAttribute("flag",path); 
                  			  
                  		  }
                    		if(applnform.getDetails().equals("SignatureInst")&&applnform.getDetails().length()!=0){
                    			System.out.println("getting Signature instruction");
                    			
                    			CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                    			req.setAttribute("cust",cust);
                    			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(applnform.getAc_no()),applnform.getAcType());
                    			
                    			req.setAttribute("signature",signObject);
                    			if(signObject!=null)
                    			System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                    			path=MenuNameReader.getScreenProperties("3012");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag","/SingnatureInst.jsp"); 
                    			  
                    		  }
                    		  
                    		if(applnform.getDetails().equals("PersonalDetails")&&applnform.getDetails().length()!=0){
                    			CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                    			req.setAttribute("cust",cust);
                    			
                    			
                    			path=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
                    			} //verification  check ends
                    			else{
                    				//if Account is not verified
                    				System.out.println("Account is not verified");
                    				loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
                    				req.setAttribute("purpose",loanpurposeobject);
                    				if(applnform.getFirst().equals("First")){
                    				accountobject=fcDelegate.getOdccAccount(applnform.getCombo_share_type(),applnform.getTxt_sh_type());
                    				
                    				
      							
      							if(applnform.getAcType().startsWith("1015")){
      								Vector  v=odccmasterobject.getDeposits();
      								String[] str_deps=new String[v.size()];
      								System.out.println("______________1_____________________");
      								for(int i=0;i<v.size();i++){
      									System.out.println("-------->"+v.get(i).toString().substring(8));
      									str_deps[i]=v.get(i).toString().substring(8);
      								}
      								
      								System.out.println("______________2_____________________");
      								
          							Enumeration en=v.elements();
          							System.out.println("v========>"+v.size());
                          			  System.out.println("OD Account Selected");
                          			  req.setAttribute("odaccount","odaccount");
                          			 //AccountMasterObject
                          			  acMasterObject=fcDelegate.getODDeposit(applnform.getTxt_sh_type());
                          			//req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                          			  if(acMasterObject!=null){
                          				   dupchk=new String[v.size()];
                      					 // req.setAttribute("dupchk",dupchk);
                          				  
                          			  req.setAttribute("acMasterObject",acMasterObject);
                          			 
                          				  
                          					  for(int k=0;k<v.size();k++){
                          						
                          						 
                          			  for(int c=0;c<acMasterObject.length;c++){
                          				  
                          					  System.out.println("inside while====>"+c+"acMasterObject" +acMasterObject.length+"  val"+acMasterObject[c].getAccNo());
                								if(v.get(k).toString().substring(8).equals(String.valueOf(acMasterObject[c].getAccNo()))){
                									
                									System.out.println("String.valueOf(en.nextElement()).substring(0,7)"+String.valueOf(en.nextElement()).substring(0,7));
                									dupchk[k]=String.valueOf(c);
                									
                								
                								
                							}
                          				  
                          			  }
                          			  
                          			  }
                          			//  System.out.println("dupchk--size---->>"+dupchk.length+"  value="+dupchk[0]);
                          			  req.setAttribute("dupchk",dupchk);
                          			  }
                          			  else{
                          				  req.setAttribute("closingmsg","ShareHolder Does not have any Deposits");
                          				  applnform.setHidval("");
                          			  }
                          			  }
      							if(odccmasterobject.getCoBorrowers()!=null&&odccmasterobject.getNoOfCoBorrowers()>0){
      								//req.setAttribute("coborrower","coborrower");
      								applnform.setCobor("coborchecked");
      								Vector v2=odccmasterobject.getCoBorrowers();
      								if(v2.size()==1){
      									applnform.setChk1("chk1");
      									applnform.setCobor1(v2.get(0).toString().substring(8));
      								}
      								if(v2.size()==2){
      									applnform.setChk1("chk1");
      									applnform.setCobor1(v2.get(0).toString().substring(8));
      									applnform.setChk1("chk2");
      									applnform.setCobor2(v2.get(1).toString().substring(8));
      									
      								}
      								if(v2.size()==3){
      									applnform.setChk1("chk1");
      									applnform.setCobor1(v2.get(0).toString().substring(8));
      									applnform.setChk1("chk2");
      									applnform.setCobor2(v2.get(1).toString().substring(8));
      									applnform.setChk1("chk3");
      									applnform.setCobor3(v2.get(2).toString().substring(8));
      								
      								}
      								
      								
      							}
                    				req.setAttribute("closingmsg","Account not yet verified");
                    				req.setAttribute("getreceipt","getreceipt");
                    				//odAccountobject=fcDelegate.getOdccAccount(applnform.getAcType(),applnform.getAc_no());
                    				odAccountobject=fcDelegate.getOdccAccountReceipt(applnform.getAcType(),String.valueOf(odccmasterobject.getRef_No()));
                    				if(odAccountobject!=null){
                    				System.out.println("-----1----1--unverified--"+odccmasterobject.getRef_No());
                    				System.out.println("-----1----2--unverified--"+accountobject.getAccname());
                    				System.out.println("-----1----3--unverified--"+odccmasterobject.getAccOpenDate());
                    				System.out.println("-----1----4--unverified--"+accountobject.getAmount());
                    				System.out.println("-----1----5--unverified--"+odccmasterobject.getApplicationSrlNo());
                    				System.out.println("-----1----4--unverified--"+odccmasterobject.getRequiredAmount());
                    				System.out.println("-----1----4--unverified--"+odccmasterobject.getPurposeCode());
                    				odAccountobject=fcDelegate.getOdccAccountReceipt(applnform.getAcType(),String.valueOf(odccmasterobject.getRef_No()));
                    				
                    				applnform.setScrollNum(String.valueOf(odccmasterobject.getRef_No()));
                    				applnform.setRcptname(accountobject.getAccname());
                    				applnform.setDate(odccmasterobject.getAccOpenDate());
                    				applnform.setAmount(String.valueOf(odAccountobject.getAmount()));
                    				applnform.setSrlno(String.valueOf(odccmasterobject.getApplicationSrlNo()));
                    				applnform.setLoanamount(String.valueOf(odccmasterobject.getRequiredAmount()));
                    				applnform.setPurpose(String.valueOf(odccmasterobject.getPurposeCode()));
                    				System.out.println("------2---unverified");
                    				if(applnform.getAcType().equalsIgnoreCase("1014001")){
                    					
                    					//For CC Accounts
                    					System.out.println("Unverified CC Account");
                    					incomeobject=odccmasterobject.getIncomeDetails();
                    					applnform.setBusinessName(incomeobject[0].getName());
                    					applnform.setBusinessAddr(incomeobject[0].getAddress());
                    					applnform.setBusinessNature(incomeobject[0].getNature());
                    					applnform.setStockValue(String.valueOf(incomeobject[0].getStockValue()));
                    					applnform.setGoodsType(incomeobject[0].getTypeOfGoods());
                    					applnform.setGoodsCondition(incomeobject[0].getGoodsCondition());
                    					applnform.setTurnover(String.valueOf(incomeobject[0].getTurnOver()));
                    					applnform.setBusinessPhno(String.valueOf(incomeobject[0].getPhNo()));
                    					applnform.setBusMonthlyIncome(String.valueOf(incomeobject[0].getIncome()));
                    					applnform.setBusMonthlyExpenditure(String.valueOf(incomeobject[0].getExpenditure()));
                    					applnform.setBusNetMonthlyIncome(String.valueOf(incomeobject[0].getNetIncome()));
                    					if(odccmasterobject.getSigObj()!=null)
                    					applnform.setTypeofoperation(odccmasterobject.getSigObj()[2].getTypeOfOperation());
                    				}
                    				if(applnform.getAcType().equalsIgnoreCase("1015001")){
                    					req.setAttribute("odaccount","odaccount");
                    					
                    				}
                    				}
                    				else{
                    					req.setAttribute("closingmsg","Cash receipt date does not match with System date");
                    				}
                    				applnform.setFirst("");
                    			}else{
                    				//for updating the data
                    	    		req.setAttribute("getreceipt","getreceipt");
                          		req.setAttribute("newac","New Account"); 
                          		if(applnform.getAcType().startsWith("1015")){
                        			  System.out.println("OD Account Selected");
                        			  req.setAttribute("odaccount","odaccount");
                        			req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                        			  }
                          		req.setAttribute("sAcType",fcDelegate.getComboElements(0));
                          		loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
                    				req.setAttribute("purpose",loanpurposeobject);
                          		if(applnform.getTxt_sh_type().trim()!=null&&applnform.getTxt_sh_type().length()>1){
                          		
                          			if(applnform.getAcType().startsWith("1015")){
                              			  System.out.println("OD Account Selected");
                              			  req.setAttribute("odaccount","odaccount");
                              			 //AccountMasterObject
                              			  acMasterObject=fcDelegate.getODDeposit(applnform.getTxt_sh_type());
                              			//req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                              			  if(acMasterObject!=null)
                              			  req.setAttribute("acMasterObject",acMasterObject);
                              			  else{
                              				  req.setAttribute("closingmsg","ShareHolder Does not have any Deposits");
                              				  applnform.setHidval("");
                              			  }
                              			  }
                          			
                          			System.out.println("applnform.getTxt_sh_type()============="+applnform.getTxt_sh_type());
                          		accountObj=fcDelegate.getOdccAccount(applnform.getCombo_share_type(),applnform.getTxt_sh_type().trim());
                          		if(accountObj!=null){
                          		  applnform.setName(accountObj.getAccname());
                          		  
                          		  
                          		  System.out.println("nom======================"+accountObj.getNomRegno());
                          		  applnform.setNom(String.valueOf(accountObj.getNomRegno()));
                          		  //focuslost of Scroll no
                          		  if(applnform.getScrollNum()!=null){
                             	    	if(applnform.getScrollNum().length()!=0&&applnform.getScrollNum()!=null){
                             	    		
                             	    		acObject=fcDelegate.receiptDetailsOnScroll(applnform.getAcType(),applnform.getScrollNum().trim());
                             	    		if(acObject!=null){
                             	    			System.out.println("acObject.getAccname()============>>>>>>>>>>>>>>>3073>");
                             	    			applnform.setRcptname(acObject.getAccname());
                             	    			applnform.setAmount(String.valueOf(acObject.getAmount()));
                             	    			applnform.setDate(fcDelegate.getSysDate());
                             	    			System.out.println("got scroll no");
                             	    		}
                             	    		else{
                             	    			applnform.setHidval("");
                             	    			req.setAttribute("closingmsg","Scroll no. not correct");
                             	    		}
                             	    	}
                             	    	}
                          		  
                          		  
                          		 //for Details
                          		  System.out.println("applnform.getDetails()========>"+applnform.getDetails());
                          		  if(applnform.getDetails().equals("PersonalDetails")&&applnform.getDetails().length()!=0){
                              			CustomerMasterObject cust=fcDelegate.getCustomer(accountObj.getCid());
                              			req.setAttribute("cust",cust);
                              			
                              			
                              			path=MenuNameReader.getScreenProperties("3032");
                              			System.out.println("path is---------------"+path);
                              			req.setAttribute("flag",path); 
                              			  
                              		  }
                          		  if(applnform.getDetails().equals("Application")&&applnform.getDetails().length()!=0){
                              			req.setAttribute("master",accountObj);
                              			req.setAttribute("data","data");
                              			req.setAttribute("odccmaster",odccmasterobject);
                              			  System.out.println("odccmasterobject value is ================"+odccmasterobject);
                              			  path=MenuNameReader.getScreenProperties("3028");
                              			
                              			System.out.println("path is---------------"+path);
                              			req.setAttribute("flag",path); 
                              			  
                              		  }
                          		  if(applnform.getDetails().equals("DepositDetails")&&applnform.getDetails().length()!=0){
                          			  System.out.println("Deposit Details is selected");
                     					AccountMasterObject amcObj=fcDelegate.getAccountMaster(Integer.parseInt(applnform.getDacno()),applnform.getDacType().trim());
                     					
                     					
                            			dObject=fcDelegate.getDepositMaster(applnform.getDacType(),applnform.getDacno());
                            			if(dObject!=null){
                            			req.setAttribute("depositInfo",dObject);
                            			CustomerMasterObject cust=fcDelegate.getCustomer(dObject.getCustomerId());
                            			req.setAttribute("cust",cust);
                            			path=MenuNameReader.getScreenProperties("3055");
                            			System.out.println("path is---------------"+path);
                            			req.setAttribute("flag",path); 
                     					}
                     					else{
                     						applnform.setHidval("");
                     						System.out.println("Deposit Master is null");
                     					req.setAttribute("closingmsg","Deposit Account Does not exist" );
                     					}
                            		  }
                          		  
                          		  if(applnform.getDetails().equals("SignatureInst")&&applnform.getDetails().length()!=0){
                              			CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                              			req.setAttribute("cust",cust);
                              			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(applnform.getAc_no()),applnform.getAcType());
                              			
                              			req.setAttribute("signature",signObject);
                              			System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                              			path=MenuNameReader.getScreenProperties("3012");
                              			System.out.println("path is---------------"+path);
                              			req.setAttribute("flag","/SingnatureInst.jsp"); 
                              			  
                              		  }
                          		  if(applnform.getAcType().startsWith("1015")){
                          			  System.out.println("OD Account Selected");
                          			  req.setAttribute("odaccount","odaccount");
                          			  if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                          				  //to check for required amount
                          				  ModuleObject[] modObject=fcDelegate.getODModule(0);
                          				  if(Double.parseDouble(applnform.getLoanamount())>modObject[0].getMaxAmount())
                          				  {
                          					  applnform.setHidval("");
                          					  req.setAttribute("closingmsg","Loan Amount greater than the Maximum Loan Amount");
                          					  
                          				  }
                          				 
                          				  //to check for DepositAmounts
                          				  String[] chk=(String[])req.getParameterValues("dpcheck");
                          				  String[] dpactyp=(String[])req.getParameterValues("dpactyp");
                          				  String[] dpacno=(String[])req.getParameterValues("dpacno");
                          				  
                          				  if(chk!=null){
                          					  String[] dupchk=chk;
                          					  req.setAttribute("dupchk",dupchk);
                          					  for(int k=0;k<chk.length;k++){
                          						  int x=Integer.parseInt(chk[k]);
                          						  int y=x+1;
                          						  System.out.println("deposit Accounts selected values-->"+dpacno[x]+"  Account type==>"+dpactyp[x].substring(0,7).trim());
                          						  dObject=fcDelegate.getDepositMaster(dpactyp[x].substring(0,7).trim(),dpacno[x]);
                          						  if(dObject!=null){
                          						  if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                                    					
                                    					
                                    					
                                    					////  //to check for required amount
                                    					loanPercentage=fcDelegate.getLoanPercentage(dpactyp[x].substring(0,7));
                                    					toaldep=(dObject.getDepositAmt()*loanPercentage)/100;
                                      				  
                                    					System.out.println("toaldep----------->>>"+toaldep);
                                      				  
                                      				  
                                      			  }
                          						  }
                          						  else{
                          							  applnform.setHidval("");
                          							  req.setAttribute("closingmsg"," Deposit Account Not Found");
                          							  
                          						  }
                          						  
                          					  }
                          					  if(dObject!=null){
                          					  if(Double.parseDouble(applnform.getLoanamount())>toaldep)
                              				  {applnform.setHidval("");
                              					  req.setAttribute("closingmsg","Required Amount is greater than Amount "+toaldep+" you can avail on these Deposits");
                              					req.setAttribute("verified","verified");
                              				  }
                          					  }
                          				  }
                          				  
                          			  }
                          			
                          			  //co-Borrower
                          			/*if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
                          			  System.out.println("Co borrower to be added");
                          			req.setAttribute("coborrower","coborrower");
                          			}*/
                          			if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
                            			// System.out.println("Co borrower to be added");
                            			//req.setAttribute("coborrower","coborrower");
                            			if(applnform.getChk1()!=null&& applnform.getChk1().equals("chk1")&& applnform.getCobor1()!=null){
                            				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype1(),applnform.getCobor1().trim());
                            				if(accountObj!=null){
                            					if(accountObj.getAccStatus().equals("C")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" is closed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getFreezeInd().equals("T")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" is Freezed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getVerified()==null){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" yet to be verified");
                            						req.setAttribute("verified","verified");
                            					}
                            				}
                            				else{
                            					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" does not exist");
                        						req.setAttribute("verified","verified");
                            				}
                            			}
                            			  //cobor2
                            			if(applnform.getChk2()!=null&& applnform.getChk2().equals("chk2")&& applnform.getCobor2()!=null){
                            				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype2(),applnform.getCobor2().trim());
                            				if(accountObj!=null){
                            					if(accountObj.getAccStatus().equals("C")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" is closed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getFreezeInd().equals("T")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" is Freezed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getVerified()==null){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" yet to be verified");
                            						req.setAttribute("verified","verified");
                            					}
                            				}
                            				else{
                            					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" does not exist");
                        						req.setAttribute("verified","verified");
                            				}
                            			}
                            			  
                            			//cobor 3
                            			if(applnform.getChk3()!=null&& applnform.getChk3().equals("chk3")&& applnform.getCobor3()!=null){
                            				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype3(),applnform.getCobor3().trim());
                            				if(accountObj!=null){
                            					if(accountObj.getAccStatus().equals("C")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" is closed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getFreezeInd().equals("T")){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" is Freezed");
                            						req.setAttribute("verified","verified");
                            					}
                            					if(accountObj.getVerified()==null){
                            						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" yet to be verified");
                            						req.setAttribute("verified","verified");
                            					}
                            				}
                            				else{
                            					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" does not exist");
                        						req.setAttribute("verified","verified");
                            				}
                            			}
                            		  }  
                          			  
                          		System.out.println("OD Account Deposit is set");
                          		req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                          		if(applnform.getDacType()!=null&&applnform.getAc_no()!=null&&!applnform.getDacType().equals("SELECT")&& applnform.getDacno().length()!=0){
                          			
                          			dObject=fcDelegate.getDepositMaster(applnform.getDacType(),applnform.getDacno());
                          			
                          			if(dObject!=null){
                          				AccountObject dacObject=fcDelegate.getAccountObj(applnform.getDacType(),applnform.getDacno());
                          				if(dacObject.getAccStatus().equals("C")){
                          					req.setAttribute("closingmsg","Deposit Account is Closed");
                          					req.setAttribute("verified","verified");
                          				}
                          				else if(dacObject.getAccStatus().equals("F")){
                          					req.setAttribute("closingmsg","Deposit Account is Freezed");
                          					req.setAttribute("verified","verified");
                          				}
                          				else if(dObject.getLoanAvailed().equals("T")){
                          					req.setAttribute("closingmsg","Loan Already Availed on this Account");
                          					req.setAttribute("verified","verified");
                          				}
                          				else if(dObject.getVerified().equals("F")){
                          					req.setAttribute("closingmsg","Deposit Account Not Verified");
                          					req.setAttribute("verified","verified");
                          				}
                          				
                          				
                          				if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                          					
                          					
                          					
                          					////  //to check for required amount
                          					loanPercentage=fcDelegate.getLoanPercentage(applnform.getDacType());
                          					double  amount4loan=(dObject.getDepositAmt()*loanPercentage)/100;
                            				  if(Double.parseDouble(applnform.getLoanamount())>amount4loan)
                            				  {applnform.setHidval("");
                            					  req.setAttribute("closingmsg","Required Amount is greater than Amount "+amount4loan+" you can avail on these Deposits");
                            					req.setAttribute("verified","verified");
                            				  }
                            				  
                            				  
                            			  }
                          				
                          				
                          			}
                          			else{
                          				req.setAttribute("closingmsg","Deposit Account Does not exist" +
                          						"");
                          				req.setAttribute("verified","verified");
                          			}
                          		}
                          			  
                          		  }
                          		 /* if(applnform.getDetails().equals("ReceiptDetails")&&applnform.getDetails().length()!=0){
                              			System.out.println("accountObj is"+accountObj);
                              			req.setAttribute("master",accountObj);
                              			
                              			req.setAttribute("data","data");
                              				path=MenuNameReader.getScreenProperties("3031");
                              			System.out.println("path is---------------"+path);
                              			req.setAttribute("flag",path); 
                              			  
                              		  }*/
                          		  //for creating new Account Number
                          System.out.println("Before going to create new account");		  
                      	//to delete or Update the data
           				 if(applnform.getHidval().equals("create")&&applnform.getHidval()!=null)
                          {
                              	  System.out.println("INSIDE CREATE FOR CREATING NEW ACCOUNT----------");
                              	  ODCCMasterObject  odccmasterobject=new ODCCMasterObject();
            	                    odccmasterobject.setModuleCode(Integer.parseInt(applnform.getAcType()));
            	                    odccmasterobject.setAccType(applnform.getAcType());
            	                    odccmasterobject.setTransDate(fcDelegate.getSysDate());
            	                    odccmasterobject.setRef_No(Integer.parseInt(applnform.getScrollNum()));
            	                   System.out.println("--------------No Error--------------1---------");
            	                    if(applnform.getAmount().length()!=0)
            	                        odccmasterobject.setTransAmount(Double.parseDouble(applnform.getAmount()));
            	                    else
            	                    {
            	                    	                         
            	                        
            	                    }	                    
            	                    // to set Signature details
            	                    System.out.println("Before setting signature=====");
            	                   // SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(applnform.getAc_no()),applnform.getAcType());
            	                    SignatureInstructionObject array_siginsobject[]=new SignatureInstructionObject[1];
            	                    signObj=new SignatureInstructionObject();
            	                    System.out.println("Signature Object=="+signObj);
            	                    System.out.println("CID is============================="+accountObj.getCid());
            	                  signObj.setCid(accountObj.getCid());
            	                    System.out.println("--------------------2324---------------"+applnform.getName());
            	                  signObj.setName(applnform.getName());
            	                  System.out.println("==========================HERE");
            	                signObj.setSAccNo(0);
            	              signObj.setTypeOfOperation(applnform.getTypeofoperation());
            	            array_siginsobject[0]=signObj;
            	              System.out.println("111111111111111111111111111111111111");
            	                 System.out.println("====operation type is===="+applnform.getTypeofoperation());   
            	                        odccmasterobject.setSigObj(array_siginsobject);	                   
            	                      System.out.println("--------------No Error--------------2---------");
            	                    double double_nominee_percentage=0;		            
            	                    /*if(obj_nomineescreen.getCount()>0)
            	                    {
            	                        array_nomineeobject=new NomineeObject[obj_nomineescreen.getCount()];
            	                        for(int j=0;j<obj_nomineescreen.getCount();j++)
            	                        {
            	                            array_nomineeobject[j]=new NomineeObject();
            	                            array_nomineeobject[j]=obj_nomineescreen.getDetails(j);
            	                            double_nominee_percentage+=array_nomineeobject[j].getPercentage();										
            	                        }
            	                        if(double_nominee_percentage<100)
            	                        {
            	                            System.out.println("Nominee is < than 100");
            	                            break here;
            	                        }
            	                    }*/
            	                    //Setting nominee to null
            	                    //for customer details
            	                    
            	                  CustomerMasterObject cust=fcDelegate.getCustomer(accountObj.getCid());
            	                System.out.println("--------------No Error--------------3---------");
            	                    odccmasterobject.setNomineeDetails(null);
            	                    if(!applnform.getAc_no().equals("0"))
            	                    	odccmasterobject.setAccNo(Integer.parseInt(applnform.getAc_no()));
            	                    else if(applnform.getAc_no().equals("0"))
            	                    	odccmasterobject.setAccNo(0);
            	                    odccmasterobject.setCustomerId(accountObj.getCid());
            	                    odccmasterobject.setMailingAddress(1);//hardcoded
            	                    odccmasterobject.setApplicationSrlNo(Integer.parseInt(applnform.getSrlno()));
            	                    odccmasterobject.setApplicationDate(applnform.getAppdate());
            	                    odccmasterobject.setRequiredAmount(Double.parseDouble(applnform.getLoanamount()));
            	                    odccmasterobject.setShareAccType(applnform.getCombo_share_type());
            	                    odccmasterobject.setShareAccNo(Integer.parseInt(applnform.getTxt_sh_type()));
            	                    odccmasterobject.setCreditLimit(Double.parseDouble(applnform.getAmount()));
            	                    odccmasterobject.setPurposeCode(Integer.parseInt(applnform.getPurpose()));
            	                    odccmasterobject.setInterestRateType(Integer.parseInt(applnform.getIntType()));//HARDCODED----------------------------
            	                    odccmasterobject.setInterestType(Integer.parseInt(applnform.getIntcalc()));
            	                    odccmasterobject.setChqBKIssue("F");
            	                    //odccmasterobject.setRelative(obj_applicationdet.isRelative()?"T":"F");
            		            /*
            	                    if(obj_applicationdet.isRelative()){
            	                    	odccmasterobject.setRelative(obj_applicationdet.getRelation());
            	                        odccmasterobject.setDirectorCode(obj_applicationdet.getDirectorCode());
            	                    }*/
            		            System.out.println("HERE SETTING SURITY AND BORROWER 0-------------");
            	                    odccmasterobject.setNoOfSurities(0);
            	                    //Setting CO-Borrower
            		                 //   Vector vector_accounts=null;
            	                			if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
            	                    			  System.out.println("Co borrower to be added at the time of submiting");
            	                    			  
            	                      			vector_accounts=new Vector(0,Integer.parseInt(applnform.getCountchk()));
            	                      			odccmasterobject.setNoOfCoBorrowers(Integer.parseInt(applnform.getCountchk()));
            	                      			
            	                    			req.setAttribute("coborrower","coborrower");
            	                    			if(applnform.getChk1()!=null&& applnform.getChk1().equals("chk1")&& applnform.getCobor1()!=null){
            	                    				vector_accounts.add(applnform.getCoborActype1().trim()+" "+applnform.getCobor1().trim());
            	                    			}
            	                    			if(applnform.getChk2()!=null&& applnform.getChk1().equals("chk2")&& applnform.getCobor2()!=null){
            	                    				vector_accounts.add(applnform.getCoborActype2().trim()+" "+applnform.getCobor2().trim());
            	                    			}
            	                    			if(applnform.getChk3()!=null&& applnform.getChk3().equals("chk3")&& applnform.getCobor3()!=null){
            	                    				vector_accounts.add(applnform.getCoborActype3().trim()+" "+applnform.getCobor3().trim());
            	                    			}
            	                			
            	                    			 odccmasterobject.setCoBorrowers(vector_accounts);
            	                			//odccmasterobject.setCoBorrowers(coborrowers);
            		                    }
            	                			else{
            		                    odccmasterobject.setNoOfCoBorrowers(0);
            	                			}
            	                    	//TO SET BUSINESS DETAILS================================
            	                    
            	                 //incomeobject=new IncomeObject();
            	                    
            	                    	
            	           
            		            
            	                    /*if(odccmasterobject.getNoOfSurities()>0)
            	                        odccmasterobject.setSurities(obj_coborrowers_surity.getCoBorrowers());
            		           
            	                    if(odccmasterobject.getNoOfCoBorrowers()>0)
            	                        odccmasterobject.setCoBorrowers(obj_coborrowers.getCoBorrowers());
            		            	   */                
            	                      odccmasterobject.setGoldDet(null);
                                    odccmasterobject.setVehicleDet(null);
                                    odccmasterobject.setRelatives(null);
                                    odccmasterobject.setInterests(null);
                                    odccmasterobject.setDependents(null);		            
                                    odccmasterobject.setPropertyDetails(null);                        	                    
            	                    odccmasterobject.uv.setUserId(user);//HardCoded
            	                    odccmasterobject.uv.setUserTml(tml);//Hardcoded
            	                    odccmasterobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
            	                    double double_amount=Double.parseDouble(applnform.getAmount());
            	                   // String sub=array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getModuleCode().substring(1,4);
            					                    		
            	                  /*  if(obj_depositscreen.getCount()!=0)	if(sub.equals("015") && obj_depositscreen.getCount()!=0)
            	                    {	
            	                        
            	                      //For OD Accounts 
          	                         *//** 1.It checks with TermDeposit AccountNo entered, 
          	                         * 2.Whether any loan has been availed on this account.
          	                         * 3.Whether requested amount is less than Deposit amount.
          	                        *//*
          	                        
          	                        Vector obj_termdepositdetails=obj_depositscreen.getDepositDetails(0);
            	                        String array_string_details[]=null;
            	                        double double_amount_apply=0;
            	                        for(int i=0;i<obj_termdepositdetails.size();i++)
            	                        {	                            
            	                            array_string_details=((TermDepositDetails)obj_termdepositdetails.get(i)).getOtherDetails();
            	                            double_amount_apply+=(Double.parseDouble(array_string_details[5])*Double.parseDouble(array_string_details[4]))/100;
            	                        }	
            	                        if((double_amount_apply<obj_applicationdet.getRequestedAmount()) && items_relavent[11].equalsIgnoreCase("D") )//&& string_get.equals("D"))
            	                        {
            	                            JOptionPane.showMessageDialog(null,"Required Amount is greater than Amount "+double_amount_apply+" you can avail on these Deposits");
            	                            combo_details.setSelectedIndex(3);
            	                            break here;
            	                        }		                
            	                        odccmasterobject.setDeposits(obj_depositscreen.getDepositAccounts(0));	                        
            	                    }
            	                    else*/
            	                        odccmasterobject.setDeposits(null);
            				
            	                    //if(applnform.getAcType().equals("1014001") && obj_employmentdet!=null)
            	                        if(applnform.getAcType().equals("1014001"))
            	                    {
            	                       //For CC Accounts 
            	                         /*1.It checks for Employment (Buisness Stock) details entered. 
            	                         * 2.Whether any loan has been availed on this account.
            	                         * 3.Whether requested amount is greater than Stock value.
            	                         */
            	                    	  System.out.println("Commenting all Verification details==================");
            	                        incomeobject=new IncomeObject[3];
            	    	                incomeobj=new IncomeObject();
            	    	               System.out.println("setting Income Object------------"+incomeobj);
            	    	             incomeobj.setTypeOfIncome("Business");
            	    	             incomeobj.setName(applnform.getBusinessName().trim());
            	    	             incomeobj.setAddress(applnform.getBusinessAddr().trim());
            	    	             System.out.println("----------------2390-----------------");
            	    	    			// Code added by Mohsin
            	    	           incomeobj.setNature(applnform.getBusinessNature().trim());
            	    	         incomeobj.setTurnOver(Double.parseDouble(applnform.getTurnover().trim()));
            	    	    			//
            	    	    			//incomeobject.setPhNo(Integer.parseInt(txt_phone_no.getText().trim()));
            	    	    			incomeobj.setPhNo(applnform.getBusinessPhno().trim());			
            	    	    			incomeobj.setIncome(Double.parseDouble(applnform.getBusMonthlyIncome().trim()));
            	    	    			incomeobj.setExpenditure(Double.parseDouble(applnform.getBusMonthlyExpenditure().trim()));
            	    	    			//incomeobject.setSurplus(Double.parseDouble(applnform.getb));
            	    	    			
            	    	    			
            	    	    			System.out.println("In the middle");
            	    	                  
            	    	    			incomeobj.setStockValue(Double.parseDouble(applnform.getStockValue().trim()));
            	    	    			incomeobj.setTypeOfGoods(applnform.getGoodsType().trim());
            	    	    			incomeobj.setGoodsCondition(applnform.getGoodsCondition().trim());
            	    	    			incomeobj.setTurnOver(Double.parseDouble(applnform.getTurnover().trim()));
            	    	    			incomeobj.setNetIncome(Double.parseDouble(applnform.getBusNetMonthlyIncome().trim()));
            	    	    			System.out.println("Before setting obj to obj array");
            	    	    			incomeobject[0]=null;
            	    	    			incomeobject[1]=null;
            	    	    			incomeobject[2]=incomeobj;
            	    	                   System.out.println("===============Setting Income Details===========");
            	    	                        //file_logger.info("INCOME DETAILS = )))) "+obj_employmentdet.getDetails()[2].getStringPhNo());
            	    	                        odccmasterobject.setIncomeDetails(incomeobject);  //setting Income Details
            	    	                   
            	                       /* IncomeObject income[]=obj_employmentdet.getDetails();
            	                        
            	                        if( (obj_applicationdet.getRequestedAmount()>income[2].getStockValue()) && (items_relavent[2].equals("Y")||items_relavent[2].equals("O")))
            	                        {
            	                            JOptionPane.showMessageDialog(null,"Required Amount is greater than Stock Value Rs. "+income[2].getStockValue()+" ");
            	                            combo_details.setSelectedIndex(3);	                            
            	                            break here;
            	                        }
            	                    }
            	                    
            	                    
            	                     * file_logger.info("request amt==================="+obj_applicationdet.getRequestedAmount());
            	                    file_logger.info("amount apply==============="+obj_loanandsharedetails.getAmountCanApply());
            	                    if(obj_applicationdet.getRequestedAmount()>obj_loanandsharedetails.getAmountCanApply())
            	                    {
            	                        JOptionPane.showMessageDialog(null,"Required Amount is greater than Amount he can Avail Rs. "+obj_loanandsharedetails.getAmountCanApply()+" ");
            	                        combo_details.setSelectedIndex(3);
            		                	break here;
            	                    }
            	                    
            	                    file_logger.info("AMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMount="+array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount());
            	                    if(obj_applicationdet.getRequestedAmount()>array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount())
            	                    {
            	                        JOptionPane.showMessageDialog(null,"Required Amount is greater than MaxAmount Rs. "+array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount()+" that can be availed on this Account");
            	                        combo_details.setSelectedIndex(3);
            	                        break here;
            	                    }*/
            	                    	  
            	                    }	// for if condition
            	                        
            	                        else if(applnform.getAcType().startsWith("1015")){
            	                     //to add deposit details
            	                        	Vector vector_accounts=null;
            	                        	 String[] chk=(String[])req.getParameterValues("dpcheck");
                         				  String[] dpactyp=(String[])req.getParameterValues("dpactyp");
                         				  String[] dpacno=(String[])req.getParameterValues("dpacno");
                         				  
                         				  if(chk!=null){
                         					vector_accounts=new Vector(0,chk.length);
                         					  for(int k=0;k<chk.length;k++){
                         						  int x=Integer.parseInt(chk[k]);
                         						  int y=x+1;
                         						vector_accounts.add(dpactyp[x].substring(0,7).trim()+" "+dpacno[x]);
                         						
                         						 // System.out.println("deposit Accounts selected values-->"+dpacno[x]+"  Account type==>"+dpactyp[x].substring(0,7).trim());
                         						 // dObject=fcDelegate.getDepositMaster(dpactyp[x].substring(0,7).trim(),dpacno[x]);
                         					  }
                         					 odccmasterobject.setDeposits(vector_accounts);
                         					System.out.println("Deposit Detail are---size--->"+vector_accounts.size());
                         				  }
            	                        	
            	                        	
              	                    	
              	                    }
            	                     
            	                      System.out.println("========================Comennted all verification condition");
                              			  if(applnform.getDel()!=null&&applnform.getDel().trim().equals("delete")){
                              			String output=fcDelegate.createOdccAc(odccmasterobject,1);  
                              			req.setAttribute("closingmsg",output);
                              			  }
                              			  else{
                              				 String output=fcDelegate.createOdccAc(odccmasterobject,0);  
                                   			req.setAttribute("closingmsg",output);
                              			  }
                              		  }             		  
                          		}
                          		else{
                          			applnform.setName("");
                          			req.setAttribute("closingmsg","Share Account Not Found");
                          		}
                          	}
                          	
                    				
                    				
                    			}
                    			
                    				
                    			}
                    		  
                    		  }
                    		  else req.setAttribute("closingmsg","Account not Found");
                    	  
                    	  }}
                      req.setAttribute("odccDetails","accountinfoobject");
                      System.out.println("Attribute has been set============================");
                  	}
                  	else if(applnform.getAc_no().equals("0")||applnform.getAc_no().equals("")){
                  		System.out.println("Account Number is 0");
                  		req.setAttribute("getreceipt","getreceipt");
                  		req.setAttribute("newac","New Account"); 
                  		if(applnform.getAcType().startsWith("1015")){
                			  System.out.println("OD Account Selected");
                			  req.setAttribute("odaccount","odaccount");
                			req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                			  }
                  		req.setAttribute("sAcType",fcDelegate.getComboElements(0));
                  		loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
            				req.setAttribute("purpose",loanpurposeobject);
                  		if(applnform.getTxt_sh_type().trim()!=null&&applnform.getTxt_sh_type().length()>=1){
                  		
                  			if(applnform.getAcType().startsWith("1015")){
                      			  System.out.println("OD Account Selected");
                      			  
                      			  req.setAttribute("odaccount","odaccount");
                      			accountObj=fcDelegate.getOdccAccount(applnform.getCombo_share_type(),applnform.getTxt_sh_type().trim());
                          		if(accountObj!=null){
                          			if(accountObj.getAccStatus().trim().equals("C")){
                          				req.setAttribute("closingmsg","Share Account is closed");
                          				 applnform.setHidval("");
                          			}
                          			else{
                      			 //AccountMasterObject
                      			  acMasterObject=fcDelegate.getODDeposit(applnform.getTxt_sh_type());
                      			//req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                      			  if(acMasterObject!=null)
                      			  req.setAttribute("acMasterObject",acMasterObject);
                      			  else{
                      				  req.setAttribute("closingmsg","ShareHolder Does not have any Deposits");
                      				  applnform.setHidval("");
                      			  }
                      			  
                          			}
                          		}
                      			  }
                  			
                  			System.out.println("applnform.getTxt_sh_type()============="+applnform.getTxt_sh_type());
                  		accountObj=fcDelegate.getOdccAccount(applnform.getCombo_share_type(),applnform.getTxt_sh_type().trim());
                  		if(accountObj!=null){
                  			if(accountObj.getAccStatus().trim().equals("C")){
                  				req.setAttribute("closingmsg","Share Account is closed");
                  				 applnform.setHidval("");
                  			}
                  			else{
                  		  applnform.setName(accountObj.getAccname());
                  		  
                  		  
                  		  System.out.println("nom======================"+accountObj.getNomRegno());
                  		  applnform.setNom(String.valueOf(accountObj.getNomRegno()));
                  		  //focuslost of Scroll no
                  		  if(applnform.getScrollNum()!=null){
                     	    	if(applnform.getScrollNum().length()!=0&&applnform.getScrollNum()!=null){
                     	    		
                     	    		acObject=fcDelegate.receiptDetailsOnScroll(applnform.getAcType(),applnform.getScrollNum().trim());
                     	    		if(acObject!=null){
                     	    			System.out.println("acObject.getAccname()============>>>>>>>>>>>>>>>3073>");
                     	    			if(acObject.getVerified().trim().length()!=0){
                     	    				applnform.setHidval("");
                     	    				req.setAttribute("closingmsg","Scroll no. not correct");
                     	    				
                     	    			}else{	
                     	    			applnform.setRcptname(acObject.getAccname());
                     	    			applnform.setAmount(String.valueOf(acObject.getAmount()));
                     	    			applnform.setDate(fcDelegate.getSysDate());
                     	    			System.out.println("got scroll no");
                     	    			}
                     	    			                     	    			                     	    			
                     	    		}
                     	    		else{
                     	    			applnform.setHidval("");
                     	    			req.setAttribute("closingmsg","Scroll no. not correct");
                     	    		}
                     	    	}
                     	    	}
                  		  if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
                  			  System.out.println("Co borrower to be added");
                  			req.setAttribute("coborrower","coborrower");
                  			if(applnform.getChk1()!=null&& applnform.getChk1().equals("chk1")&& applnform.getCobor1()!=null){
                  				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype1(),applnform.getCobor1().trim());
                  				if(accountObj!=null){
                  					if(accountObj.getAccStatus().equals("C")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" is closed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getFreezeInd().equals("T")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" is Freezed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getVerified()==null){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" yet to be verified");
                  						req.setAttribute("verified","verified");
                  					}
                  				}
                  				else{
                  					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor1()+" does not exist");
              						req.setAttribute("verified","verified");
                  				}
                  			}
                  			  //cobor2
                  			if(applnform.getChk2()!=null&& applnform.getChk2().equals("chk2")&& applnform.getCobor2()!=null){
                  				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype2(),applnform.getCobor2().trim());
                  				if(accountObj!=null){
                  					if(accountObj.getAccStatus().equals("C")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" is closed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getFreezeInd().equals("T")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" is Freezed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getVerified()==null){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" yet to be verified");
                  						req.setAttribute("verified","verified");
                  					}
                  				}
                  				else{
                  					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor2()+" does not exist");
              						req.setAttribute("verified","verified");
                  				}
                  			}
                  			  
                  			//cobor 3
                  			if(applnform.getChk3()!=null&& applnform.getChk3().equals("chk3")&& applnform.getCobor3()!=null){
                  				accountObj=fcDelegate.getOdccAccount(applnform.getCoborActype3(),applnform.getCobor3().trim());
                  				if(accountObj!=null){
                  					if(accountObj.getAccStatus().equals("C")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" is closed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getFreezeInd().equals("T")){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" is Freezed");
                  						req.setAttribute("verified","verified");
                  					}
                  					if(accountObj.getVerified()==null){
                  						req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" yet to be verified");
                  						req.setAttribute("verified","verified");
                  					}
                  				}
                  				else{
                  					req.setAttribute("closingmsg","CoBorrower's Account No  "+applnform.getCobor3()+" does not exist");
              						req.setAttribute("verified","verified");
                  				}
                  			}
                  		  }
                  		  
                  		 //for Details
                  		System.out.println("applnform.getDetails()========>"+applnform.getDetails());
                  		//loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
        				//req.setAttribute("purpose",loanpurposeobject);
        				
        				if(applnform.getDetails().equals("Application")){
        					AccountObject accountobject=fcDelegate.getOdccAccount(applnform.getAcType(),applnform.getAc_no());
                  			odccmasterobject=fcDelegate.odccData(applnform.getAcType(),applnform.getAc_no());
                  			req.setAttribute("master",accountObj);
        					req.setAttribute("odccmaster",odccmasterobject);
        					System.out.println("odccmasterobject value is ================"+odccmasterobject);
        					path=MenuNameReader.getScreenProperties("3028");
        					System.out.println("path is---------------"+path);
        					req.setAttribute("flag",path); 
          			  
          		  }
        				
        				/*else if(applnform.getDetails().equals("EmploymentDetails")&&applnform.getDetails().length()!=0){
        					AccountObject accountobject=fcDelegate.getOdccAccount(applnform.getAcType(),applnform.getAc_no());
                  			odccmasterobject=fcDelegate.odccData(accountobject.getAcctype(),String.valueOf(accountobject.getAccno()));
               	           req.setAttribute("odccmasterobj",odccmasterobject);
               	           path=MenuNameReader.getScreenProperties("3029");
               	           System.out.println("path is---------------"+path);
               	           req.setAttribute("flag",path); 
          			
          		  }
        				else if(applnform.getDetails().equals("ReceiptDetails")&&applnform.getDetails().length()!=0){
          			System.out.println("Receipt Details ");
          			AccountObject accountobject=fcDelegate.getOdccAccount(applnform.getAcType(),applnform.getAc_no());
          			odccmasterobject=fcDelegate.odccData(applnform.getAcType(),applnform.getAc_no());
          			System.out.println("accountobject------------->"+odccmasterobject.getRef_No());
          			req.setAttribute("master",accountobject);
          			req.setAttribute("newdata","newdata");
          			req.setAttribute("odccmaster",odccmasterobject);
          			path=MenuNameReader.getScreenProperties("3002");
          			System.out.println("path is---------------"+path);
          			req.setAttribute("flag",path); 
          			  
          		  }
        				else if(applnform.getDetails().equals("DepositDetails")&&applnform.getDetails().length()!=0){
        					ShareDelegate shdelegate=new ShareDelegate();
                  			ShareMasterObject shobj=shdelegate.getShareMaster(applnform.getCombo_share_type(), Integer.parseInt(applnform.getTxt_sh_type()));
                  			CustomerMasterObject cust=fcDelegate.getCustomer(shobj.getCustomerId());
                  			req.setAttribute("cust",cust);
                  			path=MenuNameReader.getScreenProperties("3055");
                  			System.out.println("path is---------------"+path);
                  			req.setAttribute("flag",path); 
        			  
        		  }*/
        				else if(applnform.getDetails().equals("SignatureInst")&&applnform.getDetails().length()!=0){
          			System.out.println("getting Signature instruction");
          			ShareDelegate shdelegate=new ShareDelegate();
          			ShareMasterObject shobj=shdelegate.getShareMaster(applnform.getCombo_share_type(), Integer.parseInt(applnform.getTxt_sh_type()));
          			CustomerMasterObject cust=fcDelegate.getCustomer(shobj.getCustomerId());
          			req.setAttribute("cust",cust);
          			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(applnform.getTxt_sh_type()),applnform.getCombo_share_type());
          			req.setAttribute("signature",signObject);
          			if(signObject!=null)
          			System.out.println("SignatureInstruction Object gives values========="+signObject.length);
          			path=MenuNameReader.getScreenProperties("3012");
          			System.out.println("path is------Sign---------"+path);
          			req.setAttribute("flag",path); 
          			  
          		  }
          		  
        				else if(applnform.getDetails().equals("PersonalDetails")&&applnform.getTxt_sh_type()!=null){
          			System.out.println("======inside personal details=====");
          			ShareDelegate shdelegate=new ShareDelegate();
          			ShareMasterObject shobj=shdelegate.getShareMaster(applnform.getCombo_share_type(), Integer.parseInt(applnform.getTxt_sh_type()));
          			CustomerMasterObject cust=fcDelegate.getCustomer(shobj.getCustomerId());
          			//req.setAttribute("cust",cust);
          			//path=MenuNameReader.getScreenProperties("3032");
          			path=MenuNameReader.getScreenProperties("0001");
          			req.setAttribute("panelName", "Personal Details");
					req.setAttribute("personalInfo", cust);
					req.setAttribute("flag",path);
          			System.out.println("path is---------------"+path);
          			req.setAttribute("flag",path); 
          			  
          		  }
                  		  if(applnform.getAcType().startsWith("1015")){
                  			  System.out.println("OD Account Selected");
                  			  req.setAttribute("odaccount","odaccount");
                  			  if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                  				  //to check for required amount
                  				  ModuleObject[] modObject=fcDelegate.getODModule(0);
                  				  if(Double.parseDouble(applnform.getLoanamount())>modObject[0].getMaxAmount())
                  				  {
                  					  applnform.setHidval("");
                  					  req.setAttribute("closingmsg","Loan Amount greater than the Maximum Loan Amount");
                  					  
                  				  }
                  				 
                  				  //to check for DepositAmounts
                  				  String[] chk=(String[])req.getParameterValues("dpcheck");
                  				  String[] dpactyp=(String[])req.getParameterValues("dpactyp");
                  				  String[] dpacno=(String[])req.getParameterValues("dpacno");
                  				  
                  				  if(chk!=null){
                  					  String[] dupchk=chk;
                  					  req.setAttribute("dupchk",dupchk);
                  					  for(int k=0;k<chk.length;k++){
                  						  int x=Integer.parseInt(chk[k]);
                  						  int y=x+1;
                  						  System.out.println("deposit Accounts selected values-->"+dpacno[x]+"  Account type==>"+dpactyp[x].substring(0,7).trim());
                  						  dObject=fcDelegate.getDepositMaster(dpactyp[x].substring(0,7).trim(),dpacno[x]);
                  						  if(dObject!=null){
                  						  if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                            					
                            					
                            					
                            					////  //to check for required amount
                            					loanPercentage=fcDelegate.getLoanPercentage(dpactyp[x].substring(0,7));
                            					toaldep=(dObject.getDepositAmt()*loanPercentage)/100;
                              				  
                            					System.out.println("toaldep----------->>>"+toaldep);
                              				  
                              				  
                              			  }
                  						  }
                  						  else{
                  							  applnform.setHidval("");
                  							  req.setAttribute("closingmsg"," Deposit Account Not Found");
                  							  
                  						  }
                  						  
                  					  }
                  					  if(dObject!=null){
                  					  if(Double.parseDouble(applnform.getLoanamount())>toaldep)
                      				  {applnform.setHidval("");
                      					  req.setAttribute("closingmsg","Required Amount is greater than Amount "+toaldep+" you can avail on these Deposits");
                      					req.setAttribute("verified","verified");
                      				  }
                  					  }
                  				  }
                  				  
                  			  }
                  			  
                  		System.out.println("OD Account Deposit is set");
                  		req.setAttribute("DAcType",fcDelegate.getDepositAccountType(0));
                  		if(applnform.getDacType()!=null&&applnform.getAc_no()!=null&&!applnform.getDacType().equals("SELECT")&& applnform.getDacno().length()!=0){
                  			
                  			dObject=fcDelegate.getDepositMaster(applnform.getDacType(),applnform.getDacno());
                  			
                  			if(dObject!=null){
                  				AccountObject dacObject=fcDelegate.getAccountObj(applnform.getDacType(),applnform.getDacno());
                  				if(dacObject.getAccStatus().equals("C")){
                  					req.setAttribute("closingmsg","Deposit Account is Closed");
                  					req.setAttribute("verified","verified");
                  				}
                  				else if(dacObject.getAccStatus().equals("F")){
                  					req.setAttribute("closingmsg","Deposit Account is Freezed");
                  					req.setAttribute("verified","verified");
                  				}
                  				else if(dObject.getLoanAvailed().equals("T")){
                  					req.setAttribute("closingmsg","Loan Already Availed on this Account");
                  					req.setAttribute("verified","verified");
                  				}
                  				else if(dObject.getVerified().equals("F")){
                  					req.setAttribute("closingmsg","Deposit Account Not Verified");
                  					req.setAttribute("verified","verified");
                  				}
                  				
                  				
                  				if(applnform.getLoanamount()!=null&&applnform.getLoanamount().length()!=0){
                  					
                  					
                  					
                  					////  //to check for required amount
                  					loanPercentage=fcDelegate.getLoanPercentage(applnform.getDacType());
                  					double  amount4loan=(dObject.getDepositAmt()*loanPercentage)/100;
                    				  if(Double.parseDouble(applnform.getLoanamount())>amount4loan)
                    				  {applnform.setHidval("");
                    					  req.setAttribute("closingmsg","Required Amount is greater than Amount "+amount4loan+" you can avail on these Deposits");
                    					req.setAttribute("verified","verified");
                    				  }
                    				  
                    				  
                    			  }
                  				
                  				
                  			}
                  			else{
                  				req.setAttribute("closingmsg","Deposit Account Does not exist" +
                  						"");
                  				req.setAttribute("verified","verified");
                  			}
                  		}
                  			  
                  		  }
                  		 /* if(applnform.getDetails().equals("ReceiptDetails")&&applnform.getDetails().length()!=0){
                      			System.out.println("accountObj is"+accountObj);
                      			req.setAttribute("master",accountObj);
                      			
                      			req.setAttribute("data","data");
                      				path=MenuNameReader.getScreenProperties("3031");
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			  
                      		  }*/
                  		  //for creating new Account Number
                  System.out.println("Before going to create new account");		  
              if(applnform.getHidval().equals("create")&&applnform.getHidval()!=null)
              {
                  	  System.out.println("INSIDE CREATE FOR CREATING NEW ACCOUNT----------");
                  	  ODCCMasterObject  odccmasterobject=new ODCCMasterObject();
	                    odccmasterobject.setModuleCode(Integer.parseInt(applnform.getAcType()));
	                    odccmasterobject.setAccType(applnform.getAcType());
	                    odccmasterobject.setTransDate(fcDelegate.getSysDate());
	                    odccmasterobject.setRef_No(Integer.parseInt(applnform.getScrollNum()));
	                   System.out.println("--------------No Error--------------1---------");
	                    if(applnform.getAmount().length()!=0)
	                        odccmasterobject.setTransAmount(Double.parseDouble(applnform.getAmount()));
	                    else
	                    {
	                    	                         
	                        
	                    }	                    
	                    // to set Signature details
	                    System.out.println("Before setting signature=====");
	                   // SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(applnform.getAc_no()),applnform.getAcType());
	                    SignatureInstructionObject array_siginsobject[]=new SignatureInstructionObject[1];
	                    signObj=new SignatureInstructionObject();
	                    System.out.println("Signature Object=="+signObj);
	                    System.out.println("CID is============================="+accountObj.getCid());
	                  signObj.setCid(accountObj.getCid());
	                    System.out.println("--------------------2324---------------"+applnform.getName());
	                  signObj.setName(applnform.getName());
	                  System.out.println("==========================HERE");
	                signObj.setSAccNo(0);
	              signObj.setTypeOfOperation(applnform.getTypeofoperation());
	            array_siginsobject[0]=signObj;
	              System.out.println("111111111111111111111111111111111111");
	                 System.out.println("====operation type is===="+applnform.getTypeofoperation());   
	                        odccmasterobject.setSigObj(array_siginsobject);	                   
	                      System.out.println("--------------No Error--------------2---------");
	                    double double_nominee_percentage=0;		            
	                    /*if(obj_nomineescreen.getCount()>0)
	                    {
	                        array_nomineeobject=new NomineeObject[obj_nomineescreen.getCount()];
	                        for(int j=0;j<obj_nomineescreen.getCount();j++)
	                        {
	                            array_nomineeobject[j]=new NomineeObject();
	                            array_nomineeobject[j]=obj_nomineescreen.getDetails(j);
	                            double_nominee_percentage+=array_nomineeobject[j].getPercentage();										
	                        }
	                        if(double_nominee_percentage<100)
	                        {
	                            System.out.println("Nominee is < than 100");
	                            break here;
	                        }
	                    }*/
	                    //Setting nominee to null
	                    //for customer details
	                    
	                  CustomerMasterObject cust=fcDelegate.getCustomer(accountObj.getCid());
	                System.out.println("--------------No Error--------------3---------");
	                    odccmasterobject.setNomineeDetails(null);
	                    if(!applnform.getAc_no().equals("0"))
	                    	odccmasterobject.setAccNo(Integer.parseInt(applnform.getAc_no()));
	                    else if(applnform.getAc_no().equals("0"))
	                    	odccmasterobject.setAccNo(0);
	                    odccmasterobject.setCustomerId(accountObj.getCid());
	                    odccmasterobject.setMailingAddress(1);//hardcoded
	                    odccmasterobject.setApplicationSrlNo(Integer.parseInt(applnform.getSrlno()));
	                    odccmasterobject.setApplicationDate(applnform.getAppdate());
	                    odccmasterobject.setRequiredAmount(Double.parseDouble(applnform.getLoanamount()));
	                    odccmasterobject.setShareAccType(applnform.getCombo_share_type());
	                    odccmasterobject.setShareAccNo(Integer.parseInt(applnform.getTxt_sh_type()));
	                    odccmasterobject.setCreditLimit(Double.parseDouble(applnform.getAmount()));
	                    odccmasterobject.setPurposeCode(Integer.parseInt(applnform.getPurpose()));
	                    odccmasterobject.setInterestRateType(Integer.parseInt(applnform.getIntType()));//HARDCODED----------------------------
	                    odccmasterobject.setInterestType(Integer.parseInt(applnform.getIntcalc()));
	                    odccmasterobject.setChqBKIssue("F");
	                    //odccmasterobject.setRelative(obj_applicationdet.isRelative()?"T":"F");
		            /*
	                    if(obj_applicationdet.isRelative()){
	                    	odccmasterobject.setRelative(obj_applicationdet.getRelation());
	                        odccmasterobject.setDirectorCode(obj_applicationdet.getDirectorCode());
	                    }*/
		            System.out.println("HERE SETTING SURITY AND BORROWER 0-------------");
	                    odccmasterobject.setNoOfSurities(0);
	                    
	                    //Setting CO-Borrower
	                 //   Vector vector_accounts=null;
                			if(applnform.getCobor()!=null&&applnform.getCobor().equals("coborchecked")){
                    			  System.out.println("Co borrower to be added at the time of submiting");
                    			  
                      			vector_accounts=new Vector(0,Integer.parseInt(applnform.getCountchk()));
                      			odccmasterobject.setNoOfCoBorrowers(Integer.parseInt(applnform.getCountchk()));
                      			
                    			req.setAttribute("coborrower","coborrower");
                    			if(applnform.getChk1()!=null&& applnform.getChk1().equals("chk1")&& applnform.getCobor1()!=null){
                    				vector_accounts.add(applnform.getCoborActype1().trim()+" "+applnform.getCobor1().trim());
                    			}
                    			if(applnform.getChk2()!=null&& applnform.getChk2().equals("chk2")&& applnform.getCobor2()!=null){
                    				vector_accounts.add(applnform.getCoborActype2().trim()+" "+applnform.getCobor2().trim());
                    			}
                    			if(applnform.getChk3()!=null&& applnform.getChk3().equals("chk3")&& applnform.getCobor3()!=null){
                    				vector_accounts.add(applnform.getCoborActype3().trim()+" "+applnform.getCobor3().trim());
                    			}
                			
                    			 odccmasterobject.setCoBorrowers(vector_accounts);
                			//odccmasterobject.setCoBorrowers(coborrowers);
	                    }
                			else{
	                    odccmasterobject.setNoOfCoBorrowers(0);
                			}
	                    	//TO SET BUSINESS DETAILS================================
	                    
	                 //incomeobject=new IncomeObject();
	                    
	                    	
	           
		            
	                    /*if(odccmasterobject.getNoOfSurities()>0)
	                        odccmasterobject.setSurities(obj_coborrowers_surity.getCoBorrowers());
		           
	                    if(odccmasterobject.getNoOfCoBorrowers()>0)
	                        odccmasterobject.setCoBorrowers(obj_coborrowers.getCoBorrowers());
		            	   */                
	                      odccmasterobject.setGoldDet(null);
                        odccmasterobject.setVehicleDet(null);
                        odccmasterobject.setRelatives(null);
                        odccmasterobject.setInterests(null);
                        odccmasterobject.setDependents(null);		            
                        odccmasterobject.setPropertyDetails(null);                        	                    
	                    odccmasterobject.uv.setUserId(user);//HardCoded
	                    odccmasterobject.uv.setUserTml(tml);//Hardcoded
	                    odccmasterobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
	                    double double_amount=Double.parseDouble(applnform.getAmount());
	                   // String sub=array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getModuleCode().substring(1,4);
					                    		
	                  /*  if(obj_depositscreen.getCount()!=0)	if(sub.equals("015") && obj_depositscreen.getCount()!=0)
	                    {	
	                        
	                      //For OD Accounts 
	                         *//** 1.It checks with TermDeposit AccountNo entered, 
	                         * 2.Whether any loan has been availed on this account.
	                         * 3.Whether requested amount is less than Deposit amount.
	                        *//*
	                        
	                        Vector obj_termdepositdetails=obj_depositscreen.getDepositDetails(0);
	                        String array_string_details[]=null;
	                        double double_amount_apply=0;
	                        for(int i=0;i<obj_termdepositdetails.size();i++)
	                        {	                            
	                            array_string_details=((TermDepositDetails)obj_termdepositdetails.get(i)).getOtherDetails();
	                            double_amount_apply+=(Double.parseDouble(array_string_details[5])*Double.parseDouble(array_string_details[4]))/100;
	                        }	
	                        if((double_amount_apply<obj_applicationdet.getRequestedAmount()) && items_relavent[11].equalsIgnoreCase("D") )//&& string_get.equals("D"))
	                        {
	                            JOptionPane.showMessageDialog(null,"Required Amount is greater than Amount "+double_amount_apply+" you can avail on these Deposits");
	                            combo_details.setSelectedIndex(3);
	                            break here;
	                        }		                
	                        odccmasterobject.setDeposits(obj_depositscreen.getDepositAccounts(0));	                        
	                    }
	                    else*/
	                        odccmasterobject.setDeposits(null);
				
	                    //if(applnform.getAcType().equals("1014001") && obj_employmentdet!=null)
	                        if(applnform.getAcType().equals("1014001"))
	                    {
	                       //For CC Accounts 
	                         /*1.It checks for Employment (Buisness Stock) details entered. 
	                         * 2.Whether any loan has been availed on this account.
	                         * 3.Whether requested amount is greater than Stock value.
	                         */
	                    	  System.out.println("Commenting all Verification details==================");
	                        incomeobject=new IncomeObject[3];
	    	                incomeobj=new IncomeObject();
	    	               System.out.println("setting Income Object------------"+incomeobj);
	    	             incomeobj.setTypeOfIncome("Business");
	    	             incomeobj.setName(applnform.getBusinessName().trim());
	    	             incomeobj.setAddress(applnform.getBusinessAddr().trim());
	    	             System.out.println("----------------2390-----------------");
	    	    			// Code added by Mohsin
	    	           incomeobj.setNature(applnform.getBusinessNature().trim());
	    	         incomeobj.setTurnOver(Double.parseDouble(applnform.getTurnover().trim()));
	    	    			//
	    	    			//incomeobject.setPhNo(Integer.parseInt(txt_phone_no.getText().trim()));
	    	    			incomeobj.setPhNo(applnform.getBusinessPhno().trim());			
	    	    			incomeobj.setIncome(Double.parseDouble(applnform.getBusMonthlyIncome().trim()));
	    	    			incomeobj.setExpenditure(Double.parseDouble(applnform.getBusMonthlyExpenditure().trim()));
	    	    			//incomeobject.setSurplus(Double.parseDouble(applnform.getb));
	    	    			
	    	    			
	    	    			System.out.println("In the middle");
	    	                  
	    	    			incomeobj.setStockValue(Double.parseDouble(applnform.getStockValue().trim()));
	    	    			incomeobj.setTypeOfGoods(applnform.getGoodsType().trim());
	    	    			incomeobj.setGoodsCondition(applnform.getGoodsCondition().trim());
	    	    			incomeobj.setTurnOver(Double.parseDouble(applnform.getTurnover().trim()));
	    	    			incomeobj.setNetIncome(Double.parseDouble(applnform.getBusNetMonthlyIncome().trim()));
	    	    			System.out.println("Before setting obj to obj array");
	    	    			incomeobject[0]=null;
	    	    			incomeobject[1]=null;
	    	    			incomeobject[2]=incomeobj;
	    	                   System.out.println("===============Setting Income Details===========");
	    	                        //file_logger.info("INCOME DETAILS = )))) "+obj_employmentdet.getDetails()[2].getStringPhNo());
	    	                        odccmasterobject.setIncomeDetails(incomeobject);  //setting Income Details
	    	                   
	                       /* IncomeObject income[]=obj_employmentdet.getDetails();
	                        
	                        if( (obj_applicationdet.getRequestedAmount()>income[2].getStockValue()) && (items_relavent[2].equals("Y")||items_relavent[2].equals("O")))
	                        {
	                            JOptionPane.showMessageDialog(null,"Required Amount is greater than Stock Value Rs. "+income[2].getStockValue()+" ");
	                            combo_details.setSelectedIndex(3);	                            
	                            break here;
	                        }
	                    }
	                    
	                    
	                     * file_logger.info("request amt==================="+obj_applicationdet.getRequestedAmount());
	                    file_logger.info("amount apply==============="+obj_loanandsharedetails.getAmountCanApply());
	                    if(obj_applicationdet.getRequestedAmount()>obj_loanandsharedetails.getAmountCanApply())
	                    {
	                        JOptionPane.showMessageDialog(null,"Required Amount is greater than Amount he can Avail Rs. "+obj_loanandsharedetails.getAmountCanApply()+" ");
	                        combo_details.setSelectedIndex(3);
		                	break here;
	                    }
	                    
	                    file_logger.info("AMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMount="+array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount());
	                    if(obj_applicationdet.getRequestedAmount()>array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount())
	                    {
	                        JOptionPane.showMessageDialog(null,"Required Amount is greater than MaxAmount Rs. "+array_moduleobject_loans[combo_loans_type.getSelectedIndex()-1].getMaxAmount()+" that can be availed on this Account");
	                        combo_details.setSelectedIndex(3);
	                        break here;
	                    }*/
	                    	  
	                    }	// for if condition
	                        
	                        else if(applnform.getAcType().startsWith("1015")){
	                     //to add deposit details
	                        	
	                        	 String[] chk=(String[])req.getParameterValues("dpcheck");
             				  String[] dpactyp=(String[])req.getParameterValues("dpactyp");
             				  String[] dpacno=(String[])req.getParameterValues("dpacno");
             				  
             				  if(chk!=null){
             					vector_accounts=new Vector(0,chk.length);
             					  for(int k=0;k<chk.length;k++){
             						  int x=Integer.parseInt(chk[k]);
             						  int y=x+1;
             						vector_accounts.add(dpactyp[x].substring(0,7).trim()+" "+dpacno[x]);
             						
             						 // System.out.println("deposit Accounts selected values-->"+dpacno[x]+"  Account type==>"+dpactyp[x].substring(0,7).trim());
             						 // dObject=fcDelegate.getDepositMaster(dpactyp[x].substring(0,7).trim(),dpacno[x]);
             					  }
             					 odccmasterobject.setDeposits(vector_accounts);
             					System.out.println("Deposit Detail are---size--->"+vector_accounts.size());
             				  }
	                        	
	                        	
  	                    	
  	                    }
	                     
	                      System.out.println("========================Cmennted all verification condition");
                  			  
                  			String output=fcDelegate.createOdccAc(odccmasterobject,0);  
                  			req.setAttribute("closingmsg",output);
                  		  }   
                  			}
                  		}
                  		else{
                  			applnform.setName("");
                  			req.setAttribute("closingmsg","Share Account Not Found");
                  		}
                  	}
                  		
                  	}
                      //Condition ends here
                  	loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
        				req.setAttribute("purpose",loanpurposeobject);
                  	 String perPath=MenuNameReader.getScreenProperties("3022");
                    	 req.setAttribute("pageId",path);
                    	 path=MenuNameReader.getScreenProperties(applnform.getPageId());
                    	req.setAttribute("sh",fcDelegate.getSHModule(0));
                    	 req.setAttribute("pageId",path);
                    	req.setAttribute("AcType",fcDelegate.getodccModule(0));
                    	req.setAttribute("ODCCApplnForm",applnform);
                    	System.out.println("----------------------------ITS going to JSP now");
                    	return map.findForward(ResultHelp.getSuccess());
                          
                  		
                  		
                  	}
                  
              }
                  	else{
                  String perPath=MenuNameReader.getScreenProperties("3007");
                  path=MenuNameReader.getScreenProperties(applnform.getPageId());
                  System.out.println("PerPath"+perPath);
                  req.setAttribute("closingmsg","");
                  req.setAttribute("error1","false");
                  req.setAttribute("sh",fcDelegate.getSHModule(0));
                  req.setAttribute("pageId",path);
                  req.setAttribute("AcType",fcDelegate.getodccModule(0));
             	 req.setAttribute("PassbookForm",applnform);
  				System.out.println("At 438 path:"+map.getPath());
  				
  					
  				
                  	}
              
                  }
  				
  				
          catch(Exception e){
          	path=MenuNameReader.getScreenProperties("0000");
          	setErrorPageElements("Exception   "+e,req,path);
          	
          	System.out.println("Exception at 332"+e);
          	return map.findForward(ResultHelp.getError());
          	}
          return map.findForward(ResultHelp.getSuccess());
      }
      
      
      //ODCC Application Ends
    //TO GET PAYORDER'S CASHED=================================
      
      if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOCashedMenu")){
          try{
          	FCCashedPOReportForm pomake=(FCCashedPOReportForm)form;
          	pomake.setSysdate(FrontCounterDelegate.getSysDate());
          	req.setAttribute("error1","false");
          	
          	System.out.println("At 5068<<---------->"+MenuNameReader.containsKeyScreen(pomake.getPageId()));
          	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(pomake.getPageId()));
          	
          	System.out.println("At 5071"+pomake);
          	pomake.setTdate(fcDelegate.getSysDate());
          	req.setAttribute("pageNum",pomake.getPageId());
             System.out.println("At 5075"+pomake.getPageId());
             logger.info(pomake.getPageId()+"This is from Front Counter Log"+map.getPath());
             if(MenuNameReader.containsKeyScreen(pomake.getPageId())){
          	   System.out.println("At 5078---------->");
                 path=MenuNameReader.getScreenProperties(pomake.getPageId());
                 fcDelegate=new FrontCounterDelegate();
                 unverified=fcDelegate.getUnverifiedPOConsol();
             	req.setAttribute("unverified",unverified);
                 setSBOpeningInitParams(req,path,fcDelegate);
                 req.setAttribute("","acType");
                 return map.findForward(ResultHelp.getSuccess());
             }
             else{
                  path=MenuNameReader.getScreenProperties("0000");
                  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                  return map.findForward(ResultHelp.getError());
             }


         }catch(Exception e){
           path=MenuNameReader.getScreenProperties("0000");
           System.out.println("At 5096"+e);
           setErrorPageElements("Unable to process request",req,path);
           return map.findForward(ResultHelp.getError());
         }
         
     }
      else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOCashed")){
      	System.out.println("at 5102 inside /FrontCounter/ListPOCashed");
      	FCCashedPOReportForm pomake=(FCCashedPOReportForm)form;
      	pomake.setSysdate(FrontCounterDelegate.getSysDate());
          String path;
          System.out.println("pomake=="+pomake);
          System.out.println("pomake=="+pomake.getPageId());
          try{
              req.setAttribute("pageNum",pomake.getPageId().trim());
              if(MenuNameReader.containsKeyScreen(pomake.getPageId())){
                  path=MenuNameReader.getScreenProperties(pomake.getPageId());
                  System.out.println(path);
                  fcDelegate=new FrontCounterDelegate();
                  System.out.println(fcDelegate);
                  System.out.println("unpo=to date="+pomake.getTdate());
                  System.out.println("unpo=From date="+pomake.getTdate());
                  //System.out.println("acNum=="+acForm.getAcNum());
                  if(pomake.getTdate().length()==0||pomake.getFdate().length()==0){
                  	System.out.println("At 5119 inside FcAction");
                  	String tr="true";
                  	req.setAttribute("error1",tr);
                  	req.setAttribute("closingmsg","Enter TO and From Dates");
                  	 String perPath=MenuNameReader.getScreenProperties("3053");
                  	 req.setAttribute("pageId",path);
                  	 
                  	 req.setAttribute("FCPOMakeForm",pomake);
                   	
       				return map.findForward(ResultHelp.getSuccess());
                  }
                  else{
                  	PayOrderObject[] poObj=fcDelegate.RetrieveCashed(pomake.getTdate(),pomake.getFdate());
                  	if(poObj!=null){
                  		req.setAttribute("poObj",poObj);
                  		if(pomake.getSave()!=null&&pomake.getSave().equals("submit")){
                  			/*//TO save to an excel Sheet
                        		 HSSFWorkbook wb = new HSSFWorkbook();
                        		   HSSFSheet sheet = wb.createSheet("Consolidated PO Report");
                        		 HSSFRow row = sheet.createRow((short)0);
                        		row.createCell((short)1).setCellValue("List Of Consolidated  Payorder");
                        		 
                        		   HSSFRow row1 = sheet.createRow((short)1);
                        		   HSSFCell cell = row.createCell((short)0);
                        		   cell.setCellValue(1);
                        		   row1.createCell((short)1).setCellValue("Sr No.");
                        		   row1.createCell((short)2).setCellValue("Voucher No.");
                        		   row1.createCell((short)3).setCellValue("Debit A/c No.");
                        		   row1.createCell((short)4).setCellValue("Name");
                        		   row1.createCell((short)5).setCellValue("GL Type");
                        		   row1.createCell((short)6).setCellValue("GL Code");
                        		 row1.createCell((short)7).setCellValue("Amount");
                        		row1.createCell((short)8).setCellValue("Comission");
                        		   
                        		  for(int k=0;k<poObj.length;k++){
                        			   HSSFRow row2 = sheet.createRow((short)k+2);
                        			row1.createCell((short)1).setCellValue(k);
                        			row1.createCell((short)2).setCellValue(poObj[k].getPOType()+"   "+String.valueOf(poObj[k].getPOSerialNo()));
                        			row1.createCell((short)3).setCellValue(poObj[k].getPOAccType()+" "+poObj[k].getPOAccNo() );
                        			row1.createCell((short)4).setCellValue(poObj[k].getPOPayee() );
                        			row1.createCell((short)5).setCellValue(poObj[k].getGLRefCode());
                        			row1.createCell((short)6).setCellValue(poObj[k].getPOGlCode());
                        			row1.createCell((short)7).setCellValue(poObj[k].getPOAmount());
                        			row1.createCell((short)8).setCellValue(poObj[k].getCommissionAmount());
                        		   }
                        		     FileOutputStream fileOut = new FileOutputStream("c:\\FirstUncashedPOReport.xls");
                        		    wb.write(fileOut);
                        		    fileOut.close(); 
                        		    req.setAttribute("closingmsg","Saved to excel file in C:");
                      */  		
                  			res.setContentType("application/.csv");
                   	        res.setHeader("Content-disposition", "attachment;filename=Cashed PO Report.csv");
                   	        
                   	        java.io.PrintWriter out = res.getWriter();
                   	        out.print("\n");
                   	        out.print("\n");
                   	        out.print("\n");
                   	        out.print(",");out.print(",");out.print(",");
                   	        out.print("Cashed PO's Report from "+pomake.getFdate()+"  to  "+pomake.getTdate());
                   	        out.print("\n");
                   	        out.print("\n");
                   	       out.print("Sr No.");out.print(",");
                   	       out.print("Pay Order No.");out.print(",");
                   	       out.print("Cheque No");out.print(",");
                   	       out.print("Payee Name");out.print(",");
                   	       out.print("Amount");out.print(",");
                   	       
                      		out.print("\n");
                      		for(int i=0;i<poObj.length;i++){
                      			out.print(i+1);out.print(",");
                      			out.print(poObj[i].getPayOrderNo());out.print(",");
                      			out.print(poObj[i].getPOChqNo());out.print(",");
                      			out.print(poObj[i].getPOPayee());out.print(",");
                      			out.print(poObj[i].getTransAmount());out.print(",");
                      			
                      			out.print("\n");
                      		}
                  			pomake.setSave("");
                  		return null;
                  		}
                  	}
                  	else{
                  		
                  		req.setAttribute("closingmsg","No Records Found");
                  	}
                  String perPath=MenuNameReader.getScreenProperties("3053");
                  System.out.println("PerPath"+perPath);
                  
                  req.setAttribute("error1","false");
                  req.setAttribute("pageId",path);
             	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
             	 req.setAttribute("FCPOMakeForm",pomake);
  				System.out.println("At 5047 path:"+map.getPath());
  				
  					
  				
                  	}
                  }
  				
  				return map.findForward(ResultHelp.getSuccess());
              }
          catch(Exception e){System.out.println("Exception at 5056"+e);}
          return map.findForward(ResultHelp.getSuccess());
      }
      
      
      //===============================PO REPORTS ENDS==========================================

      return map.findForward(ResultHelp.getSuccess());
    }
    private void setErrorPageElements(String exception,HttpServletRequest req,String path){
        req.setAttribute("exception",exception);
        req.setAttribute("pageId",path);

    }
    private void setSBOpeningInitParams(HttpServletRequest req,String path,FrontCounterDelegate fcDelegate)throws Exception{
        try{
     	   System.out.println("at 572 in setInitparam----------------->");
     	   System.out.println("path is "+path);
           req.setAttribute("pageId",path);
           req.setAttribute("AcType",fcDelegate.getComboElements(0));
           req.setAttribute("IntroAcType",fcDelegate.getComboElements(1));
           req.setAttribute("Details",fcDelegate.getDetailsComboElements());
        }catch(Exception e){
            throw e;
        }
    }
    private void setTabbedPaneInitParams(HttpServletRequest req,String path,FrontCounterDelegate fcDelegate,SBOpeningActionForm sbForm)throws Exception{
 	   System.out.println(path);
 	   
 	   String pageActions[]={"/FrontCounter/SBOpening?tabPaneHeading=PersonalDetails&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=Cash&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=Nominee&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=JointHolders&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=Introducer Type&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=Signature Ins&defaultSignIndex=0&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo()};
 	   
 	   String tabHeading[]={"PersonalDetails","ReceiptDetails","Nominee","JointHolders","Introducer","SignatureIns"};
 	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("3002").trim(),MenuNameReader.getScreenProperties("0002").trim(),MenuNameReader.getScreenProperties("0003").trim(),MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0004").trim()};
 	   
 	   req.setAttribute("TabHeading",tabHeading);
 	   req.setAttribute("PageActions", pageActions);
 	   req.setAttribute("PagePath", pagePath);
 	   req.setAttribute("pageNum","3003");
 	   
    }
    private void setTabbedPaneInitParams1(HttpServletRequest req,String path,FrontCounterDelegate fcDelegate,ODCCApplnForm sbForm)throws Exception{
  	   System.out.println(path);
  	   
  	   String pageActions[]={"/FrontCounter/PersonalDetails?tabPaneHeading=PersonalDetails&pageId="+sbForm.getPageId(),"/FrontCounter/ReceiptDetails?tabPaneHeading=ReceiptDetails&pageId="+sbForm.getPageId(),"/FrontCounter/OdccApplication?tabPaneHeading=Application&pageId="+sbForm.getPageId(),"/FrontCounter/Signature?tabPaneHeading=SignatureInst&defaultSignIndex=0&pageId="+sbForm.getPageId(),"/FrontCounter/OdccApplication?tabPaneHeading=EmploymentDetails&pageId="+sbForm.getPageId(),"/FrontCounter/VerifyFCChqwithdrawal?tabPaneHeading=DepositDetails&pageId="+sbForm.getPageId()};
  	   
  	   String tabHeading[]={"PersonalDetails","ReceiptDetails","Application","SignatureInst","EmploymentDetails","DepositDetails"};
  	   String pagePath[]={MenuNameReader.getScreenProperties("3032").trim(),MenuNameReader.getScreenProperties("3002").trim(),MenuNameReader.getScreenProperties("3028").trim(),MenuNameReader.getScreenProperties("3012").trim(),MenuNameReader.getScreenProperties("3029").trim(),MenuNameReader.getScreenProperties("3055").trim()};
  	   
  	   req.setAttribute("TabHeading",tabHeading);
  	   req.setAttribute("PageActions", pageActions);
  	   req.setAttribute("PagePath", pagePath);
  	   req.setAttribute("pageNum","3022");
  	   
     }
    private void setSignatureTabPageDetails(HttpServletRequest request,SBOpeningActionForm sbForm){
 	   
 	   String pageAction[]={"/FrontCounter/SBOpening?tabPaneHeading=Signature Ins&signIndex=0&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo(),"/FrontCounter/SBOpening?tabPaneHeading=Signature Ins&signIndex=1&pageId="+sbForm.getPageId()+"&acType="+sbForm.getAcType()+"&acNum="+sbForm.getAcNum()+"&cid="+sbForm.getCid()+"&introAcType="+sbForm.getIntroAcType()+"&introAcNum="+sbForm.getIntroAcNum()+"&detailsCombo="+sbForm.getDetailsCombo()};
 	   request.setAttribute("SignPageActions",pageAction);
 	   String pagePath=MenuNameReader.getScreenProperties("0005").trim();
 	   request.setAttribute("SignPagePath",pagePath);
    	   request.setAttribute("SignTabNum","0");
    	   String tabHeading[]={"Sign1","Sign2"};
    	   request.setAttribute("SignTabHeading",tabHeading);
    	   
    	
    }
 }
