 package com.scb.fc.actions;
 /**
  * author: Mohsin
  * 
  */
import exceptions.AccountNotFoundException;
import general.Validations;

import java.io.FileOutputStream;
import java.net.InetAddress;
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
import masterObject.frontCounter.IntPayObject;
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
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.termDeposit.DepositMasterObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.forms.SignatureInstructionForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.ResultHelp;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.fc.forms.AccountCloseActionForm;
import com.scb.fc.forms.CCPeriodical;
import com.scb.fc.forms.ChqInstructionForm;
import com.scb.fc.forms.ChqIssueForm;
import com.scb.fc.forms.ChqQueryForm;
import com.scb.fc.forms.ChqWithdrawalForm;
import com.scb.fc.forms.ConsolPOForm;
import com.scb.fc.forms.EmpDetailsFB;
import com.scb.fc.forms.FCIntPostForm;
import com.scb.fc.forms.FCInterestregForm;
import com.scb.fc.forms.FCPOConsolidatedReportForm;
import com.scb.fc.forms.FCPOMakeForm;
import com.scb.fc.forms.FCPassbookForm;
import com.scb.fc.forms.InoperativeProcess;
import com.scb.fc.forms.InterestCalcActionForm;
import com.scb.fc.forms.JointHoldersFormBean;
import com.scb.fc.forms.JointHoldersUpdationFB;
import com.scb.fc.forms.ODCCSBCAUpdationForm;
import com.scb.fc.forms.ODCCSanctionForm;
import com.scb.fc.forms.ODCCUpdationFB;
import com.scb.fc.forms.OdCcActionForm;
import com.scb.fc.forms.POActionForm;
import com.scb.fc.forms.PODataEntryForm;
import com.scb.fc.forms.POInstructionForm;
import com.scb.fc.forms.PassSheetForm;
import com.scb.fc.forms.ReceiptDetailsForm;
import com.scb.fc.forms.SBOpeningActionForm;
import com.scb.fc.forms.SBUpdationFormBean;
import com.scb.fc.forms.SBVerifyform;
import com.scb.fc.forms.SbCaAdminForm;
import com.scb.fc.forms.TabbedPaneForm;
import com.scb.fc.forms.UncashedPOReportForm;
import com.scb.fc.forms.VerifyFCChqWithdrawalForm;
import com.scb.fc.forms.VerifyODCCForm;
import com.scb.fc.forms.VerifyPOConsolidationForm;
import com.scb.fc.forms.ViewLogActionForm;
import com.scb.props.MenuNameReader;
/**
 * 
 */
public class FrontCounterAction extends Action {
    private FrontCounterDelegate fcDelegate,fcDelegatenew;
    private String path;
    int chqInststop,chqInstpost,chqInstcancel;
    StockDetailsObject ccpDetails;
    AccountTransObject array_accounttransobject[] = null;
    String chqIssuevalue;
    String acStatus;
    double toaldep;
    Address addr;
    AccountMasterObject accountmasterobject;
    ChequeObject chq,chqtokenObject;
    NomineeObject[] nominnee;
    AccountInfoObject accountinfoobject;
    PayOrderObject[] payorderobject,payorderobject_view,payordersubmit;
    PayOrderObject payorderobj,payoorderobject;
    AccountMasterObject[] array_account_master,acMasterObject;
    AccountMasterObject amObj;
       AdminObject[] admin_object;
   //    ChequeBookObject chequeobject;
       int g;
       String[] cbkno;
       ChequeObject chequeobject;
       String user,tml;
       String[] dupchk=null;
       GLMasterObject[] array_glmasterobject;
       ODCCMasterObject odccmasterobject;
       AccountObject accountObj,accountobject,acObject,odAccountobject;
       CustomerMasterObject cmObj;
       NomineeObject NomineeObject;
       LoanPurposeObject[] loanpurposeobject;
       IncomeObject[] incomeobject;
       HttpSession session;
       Vector cobor;
       double loanPercentage;
       IncomeObject incomeobj;
       Object[][] unverified;
       Map user_role;
       HashMap address=new HashMap();
       AdministratorDelegate admDelegate;
       DepositMasterObject dObject;
       SignatureInstructionObject signObj;
   
    final Logger logger=LogDetails.getInstance().getLoggerObject("FrontCounterAction");
    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res){
    	System.out.println("This id is not perfect=="+map.getPath());
    	

    	session=req.getSession();
/*user=(String)session.getServletContext().getAttribute("UserName");
tml=(String)session.getServletContext().getAttribute("UserTml");*/
    	/*user=(String)getServlet().getServletContext().getAttribute("UserName");
    	tml=(String)getServlet().getServletContext().getAttribute("UserTml");*/
    	user=(String)session.getAttribute("UserName");
    	tml=(String)session.getAttribute("UserTml") ;
    	   
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
    	String usertml=(String)session.getAttribute("UserName");
        if(usertml!=null){
        	
        	System.out.println("UserName----152----------->"+usertml);
        	
        	System.out.println("The inactive time is  =====>"+session.getMaxInactiveInterval());
        }
        
         if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/TabbedMenu")){
        	TabbedPaneForm tbForm=(TabbedPaneForm)form;
        	String pageActions[]={"/FrontCounter/TabbedMenu","/FrontCounter/TabbedMenu"};
        	String tabHeading[]={"PersonalDetails","ReceiptDetails"};
      	   	String pagePath[]={MenuNameReader.getScreenProperties("0002").trim(),MenuNameReader.getScreenProperties("3002").trim()};
      	   	req.setAttribute("pageId",MenuNameReader.getScreenProperties("3003"));
      	   	req.setAttribute("TabHeading",tabHeading);
      	   	req.setAttribute("PageActions", pageActions);
      	   	req.setAttribute("PagePath", pagePath);
      	   	req.setAttribute("pageNum","3003");
      	   	req.setAttribute("TabNum","0");
        	
        	return map.findForward(ResultHelp.getSuccess());
        }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/Signature")){
        	SignatureInstructionForm siForm=(SignatureInstructionForm)form;
        	SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
        	String path;
            try{
                req.setAttribute("pageNum",sbForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(sbForm.getPageId())){
                	//SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
                    path=MenuNameReader.getScreenProperties(sbForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    if(!sbForm.getAcType().trim().equals("SELECT")){
                    	AccountMasterObject amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType().trim());
                        req.setAttribute("AccountDetails",amObj);
                        System.out.println(sbForm.getDetailsCombo());
                        if(!siForm.getSignCombo().equalsIgnoreCase("SELECT"))
                        	if(!sbForm.getDetailsCombo().trim().equalsIgnoreCase("SELECT")){
                        		//setDetails(sbForm.getDetailsCombo(), req, fcDelegate, amObj, sbForm,siForm,1);
                        		if(sbForm.getDetailsCombo().trim().equalsIgnoreCase("Nominee")){
                        	           String perPath=MenuNameReader.getScreenProperties("0002");
                        	             req.setAttribute("pageNum",sbForm.getPageId());
                        	             req.setAttribute("TabNum","2");
                        	           req.setAttribute("flag",perPath);
                        	       }
                        	
                        	}
                        setSBOpeningInitParams(req,path,fcDelegate);
                        return map.findForward(ResultHelp.getSuccess());
                    }
                   else{
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
            	 path=MenuNameReader.getScreenProperties("0000");
                 setErrorPageElements(""+e.toString(),req,path);
                 logger.error(e.getMessage());
                 return map.findForward(ResultHelp.getError());
            }
        }
        // by Mohsin
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ViewLogMenu")){
            try{
            	ViewLogActionForm vlForm=(ViewLogActionForm)form;
            	System.out.println("<At 297<---------->");
               req.setAttribute("pageNum",vlForm.getPageId());
               logger.info(vlForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(vlForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(vlForm.getPageId());
                   System.out.println("At 305---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   req.setAttribute("","acType");
                   req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ViewLogfc")){
            ViewLogActionForm vlForm=(ViewLogActionForm)form;
            String path;
            System.out.println("sbform=="+vlForm);
            System.out.println("sbform=="+vlForm.getPageId());
            try{
            	
                req.setAttribute("pageNum",vlForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(vlForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(vlForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+vlForm.getAcType());
                    System.out.println("acNum=="+vlForm.getAcNum());
                    String perPath=MenuNameReader.getScreenProperties("3006");
                    System.out.println("PerPath"+perPath);
                    setSBOpeningInitParams(req,path,fcDelegate);
                	req.setAttribute("flag",perPath);
                	req.setAttribute("ActionPath",map.getPath().trim());
                	req.setAttribute("ViewLogActionForm",vlForm);
    				req.setAttribute("SubmitValue","Upadte");
    				req.setAttribute("ActionPath",map.getPath().trim());
    				System.out.println("At 337 path:"+map.getPath());
    				if(vlForm.getAcType().equals("1002001")||vlForm.getAcType().equals("1007001")){
    					
    					String sbcols[]=fcDelegate.sbcaColumnHeading();
    					System.out.println("At 361 length is "+sbcols.length);
    					/*for(int j=1;j<sbcols.length;j++){
    						System.out.println("columns are "+sbcols[j]);
    					}*/
    					Object colData[][]=fcDelegate.viewLogSBCAData(Integer.parseInt(vlForm.getAcNum()),vlForm.getAcType());
    					System.out.println("At 366 length is "+colData.length);
    					if(colData!=null){
    						for(int k=0;k<colData.length;k++){
    							for(int c=0;c<25;c++){
    							//System.out.println("At 591 in action colData "+colData[k][c].toString());
    							}
    						}
    						
    					}
    					req.setAttribute("columnData",colData);
    					
    					req.setAttribute("sbcols", sbcols);
    					req.setAttribute("pageId",path);
    				}
    				else if(vlForm.getAcType().trim().equals("1014001")||vlForm.getAcType().trim().equals("1015001")){
    					
    					System.out.println("inside CCCCC_______________________");
    					String sbcols[]=fcDelegate.odccColumnHeading();
    					System.out.println("At 284 length is "+sbcols.length);
    					/*for(int j=1;j<sbcols.length;j++){
    						System.out.println("columns are "+sbcols[j]);
    					}*/
    					Object colData[][]=fcDelegate.viewLogODCCData(Integer.parseInt(vlForm.getAcNum()),vlForm.getAcType());
    					System.out.println("At 366  for ODlength is "+colData.length);
    					if(colData!=null){
    						for(int k=0;k<colData.length;k++){
    							for(int c=0;c<25;c++){
    							//System.out.println("At 591 in action colData "+colData[k][c].toString());
    							}
    						}
    						
    					}
    					req.setAttribute("columnData",colData);
    					
    					req.setAttribute("sbcols", sbcols);
    					req.setAttribute("pageId",path);
    				}
    					
    				System.out.println("_________________FrontCounter___________________");
    				
    				req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
    				
    				return map.findForward(ResultHelp.getSuccess());
                }}
            catch(Exception e){System.out.println("Exception at 332"+e);
            req.setAttribute("msg","Records not found");
            }
            
            return map.findForward(ResultHelp.getSuccess());
        }
        
        //sbopening verify==================
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifySBopenMenu"))
  	  {
            try{
            	SBVerifyform sbverify=(SBVerifyform)form;
           	// SBOpeningActionForm sbForm=(SBOpeningActionForm)form;
               req.setAttribute("pageNum",sbverify.getPageId().trim());
               System.out.println("PageId is"+sbverify.getPageId().trim());
               logger.info("This is from Front Counter"+map.getPath());
               if(MenuNameReader.containsKeyScreen(sbverify.getPageId())){
                   path=MenuNameReader.getScreenProperties(sbverify.getPageId());
                   req.setAttribute("path", path);
                   fcDelegate=new FrontCounterDelegate();
                   System.out.println("At 57"+path);
                   setSBOpeningInitParams(req,path,fcDelegate);
                   sbverify.setCid("0");
                   sbverify.setAcno("0");
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
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifySBopen")){
        	SBVerifyform sbverify=(SBVerifyform)form;
            String path;
            System.out.println("sbform=="+sbverify);
            System.out.println("sbform=="+sbverify.getPageId());
            try{
            	
            	// req.setAttribute("Details",fcDelegate.getDetailsComboElements());
                req.setAttribute("pageNum",sbverify.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(sbverify.getPageId())){
                    path=MenuNameReader.getScreenProperties(sbverify.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+sbverify.getAcType());
                    System.out.println("acNum=="+sbverify.getAcno());
                    
                    if(!sbverify.getAcType().equals("SELECT")||sbverify.getAcno().equals("0")){
                    	String[][] unverified=fcDelegate.getunverified(sbverify.getAcType());
                    	if(unverified!=null&&unverified.length>0)
                    	req.setAttribute("unverified",unverified);
                    }
                    	
                   
                    if(!sbverify.getAcType().equals("SELECT")&&!sbverify.getAcno().equals("0")&&sbverify.getAcno().trim().length()!=0){
                    //for accounts to be verified
                    	String[][] unverified=fcDelegate.getunverified(sbverify.getAcType());
                    	if(unverified!=null){
                    	if(unverified.length>0)
                    	req.setAttribute("unverified",unverified);
                    	}
                    	AccountMasterObject amobj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
                    	if(amobj!=null){
                    		if(amobj.getAccStatus().equals("C")){
                    		  req.setAttribute("closingmsg","Account is Closed");
                    		  sbverify.setForward("");
                    		}
                    		else if(amobj.getVerified().equals("T")){
                    		  req.setAttribute("closingmsg","Account Already Verified");
                    		  sbverify.setForward("");
                    		}
                    	}
                    	sbverify.setCid(String.valueOf(amobj.getCid()));
                    	sbverify.setIntroacno(String.valueOf(amobj.getIntrAccNo()));
                    	//sbverify.setIntroType(amobj.getIntrAccType());
                    	System.out.println("intraacc desc"+amobj.getIntrAccType());
                    	sbverify.setIntroType(amobj.getIntrAccType());
                    	//ModuleObject[] mod=fcDelegate.getComboElements(0);
                    	/*for(int k=0;k<mod.length;k++){
                    	if(amobj.getIntrAccType().equals(mod[k].getModuleCode()))
                    	{
                    		sbverify.setIntroType(amobj.getIntrAccType());
                    		
                    	}
                    	}*/
                    	
                    	
                    	if(sbverify.getDetailsCombo().equals("Personal")&&sbverify.getDetailsCombo().length()!=0){
                			CustomerMasterObject cust=fcDelegate.getCustomer(amobj.getCid());
                			req.setAttribute("cust",cust);
                			
                			
                			path=MenuNameReader.getScreenProperties("3032");
                			System.out.println("path is---------------"+path);
                			req.setAttribute("flag",path); 
                			  
                		  }
            		 
         				if(sbverify.getDetailsCombo().equals("JointHolders")&&sbverify.getDetailsCombo().length()!=0){
                			//for JointHolder Details
         					amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
         					if(amObj.getJointCid()!=null&&amObj.getJointCid().length>0)
	    					 {	System.out.println("At 7279==================>");
	    						 cmObj=fcDelegate.getCustomer(amobj.getJointCid()[0]);
	    		     			req.setAttribute("cust",cmObj);
	    		     			req.setAttribute("jointholder","joint");
	    					 }else{
	    						 req.setAttribute("msg", "Joint Holders Not Found");
	    					 }
         					path=MenuNameReader.getScreenProperties("3032");
                			System.out.println("path is---------------"+path);
                			req.setAttribute("flag",path); 
                			  
                		  }
         				
            		  if(sbverify.getDetailsCombo().equals("Signature Ins")&&sbverify.getDetailsCombo().length()!=0){
            			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
            			  CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                			req.setAttribute("cust",cust);
                			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType());
                			
                			req.setAttribute("sigObject",signObject);
                			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                			path=MenuNameReader.getScreenProperties("3012");
                			System.out.println("path is---------------"+path);
                			req.setAttribute("flag","/SingnatureInst.jsp"); 
                			  
                		  }
            		 if(sbverify.getDetailsCombo().equals("Cash")&&sbverify.getDetailsCombo().length()!=0){
            			 	amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno().trim()),sbverify.getAcType().trim()); 
            			  //accountObj=fcDelegate.getAccountObj(sbverify.getAcType(),sbverify.getAcno());  
            			  System.out.println("accountObj in receipt is"+amObj);
            			req.setAttribute("data","data");  
              			req.setAttribute("sbcamaster",amObj);
              			
              			req.setAttribute("newdata","data");
                			
                			
                			
                			path=MenuNameReader.getScreenProperties("3031");
                			System.out.println("path is---------------"+path);
                			req.setAttribute("flag",path); 
                			  
                		  }
            		 if(sbverify.getDetailsCombo().equals("Introducer Type")&&sbverify.getDetailsCombo().length()!=0){
            			  System.out.println("====in Introducer ==="+path);
            			AccountMasterObject  amObj1=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno().trim()),sbverify.getAcType().trim()); 
              			if(amObj1!=null){
            			CustomerMasterObject custin=fcDelegate.getCustomer(amObj1.getCid());
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
            		  if(sbverify.getDetailsCombo().equals("Nominee")&&sbverify.getDetailsCombo().length()!=0 ){
            			  System.out.println("Inside Nominee---->");
            			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
            			  if(amObj!=null){
  						nominnee=amObj.getNomineeDetails();
  						if(nominnee!=null){
  						System.out.println("NOmiNEEE CID----> "+nominnee[0].getRegNo());
  						 req.setAttribute("flag",MenuNameReader.getScreenProperties(String.valueOf(13026)));
  						req.setAttribute("NomDetail", nominnee);
  						req.setAttribute("HideTheFields",null);
            			  }
  						else{
            				  req.setAttribute("msg", "Nominee Details are not found");
            			  }
            			 } 
              			  
              		  }
            		  

                    //for verification
   			       
       			   if(sbverify.getForward().equalsIgnoreCase("verify")){
       			    	System.out.println("Inside verification====================");
       			    	amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
       					      AccountTransObject accounttransobject=new AccountTransObject();
       						
       					        accounttransobject.setAccNo(Integer.parseInt(sbverify.getAcno()));
       					        accounttransobject.setAccType(sbverify.getAcType());
       					        accounttransobject.uv.setVerId(user);//uid
       					        accounttransobject.uv.setVerTml(tml);//tml
       					        accounttransobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
       					        accounttransobject.setCdInd("C");
       					        accounttransobject.setTransSeqNo(1);
       					        accounttransobject.setTransDate(amObj.getAccOpenDate());
       					        accounttransobject.setTransType("R");
       					        accounttransobject.setTransSource("C");
       					        accounttransobject.setGLRefCode(Integer.parseInt(sbverify.getAcType()));
       					        AccountMasterObject amObj=fcDelegate.getAccountMaster(Integer.parseInt(sbverify.getAcno()),sbverify.getAcType().trim());
       					        accounttransobject.setTransAmount(amObj.getTransAmount());
       					        accounttransobject.setRef_No(amObj.getRef_No());
       						
       					       int result=fcDelegate.sbVerify(accounttransobject);
       					       System.out.println("Verification result is "+result);
       					       if(result==1)
       					    	    req.setAttribute("closingmsg","Account Successfully Verified");
       					       else
       					    	   req.setAttribute("closingmsg","Account Could not be Verified");
       					    sbverify.setForward("");
       			   
       			     }
                
                    
                }
                    
                  //for going back
                    path=MenuNameReader.getScreenProperties(sbverify.getPageId());
                    req.setAttribute("path", path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println("-----------------At 679"+path);
                    setSBOpeningInitParams(req,path,fcDelegate);
                    
                    req.setAttribute("","acType");
                    req.setAttribute("pageId",path);
                    return map.findForward(ResultHelp.getSuccess());
                }
                
                else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
                }
                
            }
            catch(Exception ex){ex.printStackTrace();}
        }
        
        
        //Cheque Operation by Mohsin
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChqWithdrawMenu")){
            try{
            	ChqWithdrawalForm chwForm=(ChqWithdrawalForm)form;
            	
            	
            	System.out.println("At 904<<---------->"+MenuNameReader.containsKeyScreen(chwForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(chwForm.getPageId()));
            	
            	System.out.println("At 907"+chwForm);
               
            	req.setAttribute("pageNum",chwForm.getPageId());
               System.out.println("At 910"+chwForm.getPageId());
               logger.info(chwForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(chwForm.getPageId())){
            	   System.out.println("At 913---------->pageId"+chwForm.getPageId());
                   path=MenuNameReader.getScreenProperties(chwForm.getPageId());
                   System.out.println("At 914---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   chwForm.setDate(fcDelegate.getSysDate());
                   chwForm.setAmount("0.0");
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getComboElementsAll(0));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 932"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChqWithdraw")){
        	
        	System.out.println("at 420 inside AChequeWithdraw");
        	int int_daycash=fcDelegate.getCashStatus();
        	if(int_daycash==3)
			{
        		req.setAttribute("chqw","DayCash is Closed cant do any Transaction");				
				//setAttribute("chwdetail",new String[] {"chwdetail"});
			}	
			else if(int_daycash==1)
			{
				req.setAttribute("chqw","Cashier is Closed cant do any Transaction");				
				//req.setAttribute("chwdetail",new String[] {"chwdetail"});
			}	
        	
        	ChqWithdrawalForm chwForm=(ChqWithdrawalForm)form;
            String path;
            System.out.println("sbform=="+chwForm);
            System.out.println("sbform=="+chwForm.getPageId());
            try{
                req.setAttribute("pageNum",chwForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(chwForm.getPageId())){
                	
                    path=MenuNameReader.getScreenProperties(chwForm.getPageId());
                    
                    if(chwForm.getDetailsCombo()!=null&&chwForm.getDetailsCombo().equals("unverifiedtokens")&&chwForm.getDetailsCombo().length()!=0){
            			
          			  System.out.println("At 998 trying to display help window");
          			  String dis_colname[]=new String[5];
        		        dis_colname[0]="Token";
        		        dis_colname[1]="AC_Type";
        		        dis_colname[2]="AC_No";
        		        dis_colname[3]="Chq_No";
        		        dis_colname[4]="Amount";
        		        
        		      String colname[]=new String[5];
    		        colname[0]="token_no";
    		        colname[1]="ac_type";
    		        colname[2]="ac_no";
    		        colname[3]="chq_no";
    		        colname[4]="trn_amt";
    		        
        		        String colname_T[]= new String[1];
        		        colname_T[0]="token_no";
        		        String dis_colname_T[]=new String[1];
        		        dis_colname_T[0]="Token Number";
        		        String tablename="ChequeWithdrawal";
        		        String condition="ve_tml is null and ve_user is null and ve_date is null and trn_date='"+fcDelegate.getSysDate()+"'";
        		        
        		        Object[][] unverifiedtokens=fcDelegate.getunverifiedtokens(tablename,colname,condition);
        		        req.setAttribute("sbcols",dis_colname);
        		      req.setAttribute("columnData",unverifiedtokens);
        		    req.setAttribute("Pagehead","Unverified Token Nos.");
        		    
        		  req.setAttribute("flag",MenuNameReader.getScreenProperties("3058"));
        		    
          		  }
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    //to check for token Details
                    chwForm.setDate(fcDelegate.getSysDate());
                    if(chwForm.getAcType()!=null){
                    System.out.println("actype=="+chwForm.getAcType());
                    System.out.println("acNum=="+chwForm.getAcno());
                    }
                    else{
                    	System.out.println("Account type is null");
                    }
                    if(chwForm.getTokenno().trim()==null||chwForm.getTokenno().trim().length()==0){
                    
                    	req.setAttribute("chqw","Please enter the token number");
                    	String perPath=MenuNameReader.getScreenProperties("3008");
                    	   req.setAttribute("pageId",path);
                    	   req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	   req.setAttribute("ChqWithdrawalForm",chwForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    
                    }
                    else if(chwForm.getAcType()!=null&&chwForm.getAcType().equals("SELECT")||chwForm.getAcno()!=null&&chwForm.getAcno().equals("")){
                    	System.out.println("At 435 inside FcAction");
                    	
                    	
                    	req.setAttribute("chqw"," give proper account type and account number");
                    	String perPath=MenuNameReader.getScreenProperties("3008");
                    	   req.setAttribute("pageId",path);
                    	   req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	   req.setAttribute("ChqWithdrawalForm",chwForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	
                    String checTno=fcDelegate.chkTokenno(chwForm.getTokenno());
                    	
                    	System.out.println("At 452 in Action return of check"+checTno);
                    	if(!checTno.equals("no")){
                    		System.out.println("At 454 inside not null");
                    		String perPath=MenuNameReader.getScreenProperties("3008");
                            System.out.println("PerPath"+perPath);
                            req.setAttribute("chqw", checTno);
                            req.setAttribute("pageId",path);
                            
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqWithdrawalForm",chwForm);;
            				System.out.println("At 461 path:"+map.getPath());
                    		
                    		
                    	}
                    	else{
                    		if(chwForm.getChqslpo().equals("PO")){
                    			payorderobj=fcDelegate.getPOWithdrawal(chwForm.getChqslno());
                    			if(payorderobj!=null){
                    			if(payorderobj.getPOCancel().equals("T"))
    							{
                    				req.setAttribute("chqw","PayOrder is cancelled");
    								
    							}
    							else if(payorderobj.getPOStop().equals("T"))
    							{
    								req.setAttribute("chqw","PayOrder is stopped");
    								
    							}
    							else if(Validations.validDate(payorderobj.getPOValidUpTo(),fcDelegate.getSysDate())==-1)
    							{
    								System.out.println("po valid="+payorderobj.getPOValidUpTo());
    								req.setAttribute("chqw","PayOrder Valid Date is only upto"+payorderobj.getPOValidUpTo());
    																
    							}
    							else if(payorderobj.getPOCshIndicator()==1)
    							{
    								req.setAttribute("chqw","PayOrder is already Cashed");
    																
    							}
    							else
    							{
    								chwForm.setAmount(String.valueOf(payorderobj.getPOAmount()));
    								chwForm.setDate(payorderobj.getPODate());
    								chwForm.setPayee(payorderobj.getPOPayee());
    							    /*txt_amount.setText(String.valueOf(DoubleFormat.doubleToString(payorderobject.getPOAmount(),2)));
    								txt_payeename.setText(payorderobject.getPOPayee());
    								txt_date.setText(payorderobject.getPODate());*/
    								/*txt_accountnum.setText("0");
    								combo_accounttype.setSelectedIndex(0);
    																								
    								po_dtm.setNumRows(0);
    								Object data[]=new Object[4];
    								if(payorderobject!=null)
    								{									
    									data[0]=String.valueOf(payorderobject.getPOChqNo());
    									data[1]=String.valueOf(payorderobject.getPOSerialNo());									
    									data[2]=String.valueOf(payorderobject.getPODate());
    																		
    									po_dtm.addRow(data);
    								}			*/
                    			
                    		}
                    			}
                    			else{
                    				req.setAttribute("chqw","PayOrder Not Found");
                    			}
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                				return map.findForward(ResultHelp.getSuccess());
                    		}
                    		String[] chqWaction=fcDelegate.chqWithdraw(chwForm.getTokenno(),chwForm.getAcType(),chwForm.getAcno(),chwForm.getChqslpo());
                    		System.out.println("At 987 in else chqWaction.length "+chqWaction.length);
                    		System.out.println("At 988 values are "+chqWaction[0]);
                    		if(chqWaction.length>1){
                    			
                    			
                    			
                    			for(int k=0;k<chqWaction.length;k++){
                    				System.out.println("At 991 values are "+chqWaction[k]);
                    				
                    				
                    			}
                    			
                    			if(chwForm.getChqslno().trim()!=null&&chwForm.getChqslno().trim().length()!=0&&chwForm.getChqslpo().equals("Cheque"))
                    			{
                    				//to get Cheque Details of Individual Accounts
                    			Vector checqdet=fcDelegate.getindividualChequeDet(chwForm.getAcType(),chwForm.getAcno());
                    			System.out.println("checqdet====>"+checqdet);
                    			req.setAttribute("chqdet",checqdet);
                    				
                    			String chequeResult=fcDelegate.getAccountChequeDet(chwForm.getAcType(),chwForm.getAcno(),chwForm.getChqslno());	
                    				if(!chequeResult.equals("Alright")){
                    			req.setAttribute("chqw",chequeResult);
                    				//req.setAttribute("chwdetail",new String[] {"chwdetail"});
                    				chwForm.setHidval("");
                    				}
                    			}
                    			
                    			if(chwForm.getHidval().equals("submit")){
                    				chequeobject=new ChequeObject();
                    			//	chequeobject.setBookNo(Integer.parseInt(dtm.getValueAt(table_chq_details.getSelectedRow(),0).toString()));
                    				System.out.println("----------------Submitting values-----------------"+chwForm.getChqslno());
        				        	if(chwForm.getChqslpo().equals("Cheque"))
        				        	{
        				        	    chequeobject.setTransMode("Q");
        				        	    chequeobject.setCashPD("F");
        				        	    chequeobject.setBookNo(0);
        				        	}
        				        	else if(chwForm.getChqslpo().equals("Slip")){
        				        	    chequeobject.setTransMode("S");
        				        	    chequeobject.setCashPD("T");
        				        	}
        				        	else if(chwForm.getChqslpo().equals("PO")){
        				        	    chequeobject.setTransMode("PO");
        				        	    chequeobject.setCashPD("F");
        				        	}
        				        	String[] chqdetail=fcDelegate.getChqDetails(chwForm.getChqslno());
        				        	if(chqdetail!=null&&chqdetail.length>1){
       				        		 
            				        	chequeobject.setBookNo(Integer.parseInt(chqdetail[2]));
            				        	}
            				        	else{chequeobject.setBookNo(0);}
        				        	chequeobject.setAccType(chwForm.getAcType());
        				        	chequeobject.setAccNo(Integer.parseInt(chwForm.getAcno()));
        				        	chequeobject.setTokenNo(Integer.parseInt(chwForm.getTokenno()));					    				 
        				        	chequeobject.setTransType("P");
        				        	chequeobject.setCashPD("F");
        				        					        	
        				        	chequeobject.setTransDate(fcDelegate.getSysDate());
        				        	chequeobject.setChqDate(chwForm.getDate());
        				        	chequeobject.setTransAmount(Double.parseDouble(chwForm.getAmount()));
        			        	    chequeobject.setPayeeName(chwForm.getPayee());		
        			        	    System.out.println("---------Submitting tml -------------------------");
        				        	chequeobject.uv.setUserTml((String)session.getServletContext().getAttribute("UserTml"));//(String)req.getAttribute("tml")
        				        	chequeobject.uv.setUserId((String)session.getServletContext().getAttribute("UserName"));//(String)req.getAttribute("uid")	
        				        	chequeobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
        				        	chequeobject.setChqNo(Integer.parseInt(chwForm.getChqslno()));
        				        	
                    				if(chwForm.getDelet()!=null&&chwForm.getDelet().equals("delete")){
        				        	String result=fcDelegate.submitCheque(chequeobject,1);
        				        	req.setAttribute("chqw",result);
                    				}else{
                    					System.out.println("-----Hello Making a Cheque Withdrawal-------");
                    					String result=fcDelegate.submitCheque(chequeobject,0);
                    					req.setAttribute("chqw",result);
                    				}
        				        	
        				        	 /*UserActivityMasterObject user_activ=new UserActivityMasterObject();
 									user_activ.setUser_id(MainScreen.head.getUid());
 					            	user_activ.setTml_no(MainScreen.head.getTml());
 					            	user_activ.setPage_visit(MainScreen.ji.getTitle());
 					            	user_activ.setActivity(UserActivityHeading.getActivity(2));
 					            	user_activ.setAc_no(chequeobject.getAccNo());
 					            	user_activ.setCid(chequeobject.getCustomerId());
 					            	user_activ.setActivity_date(MainScreen.head.getSysDate());
 					            	user_activ.setActivity_time(MainScreen.head.getSysTime());*/
                    			}
                    			else if(chwForm.getHidval().equals("verify")){
                    				chequeobject=fcDelegate.getVerifyChqObject(chwForm.getTokenno());
                    				
                    				System.out.println("-------------INSIDE VERIFICATION------------------------");
                    				if(chequeobject!=null)
                    				{
                    				AccountTransObject accounttransobject=new AccountTransObject();
                    				
                			        accounttransobject.setTransType(chequeobject.getTransType());
                			        accounttransobject.setTransAmount(chequeobject.getTransAmount());
                			        accounttransobject.setTransMode(chequeobject.getTransMode());
                			        accounttransobject.setChqDDNo(chequeobject.getChqNo());
                			        accounttransobject.setPassBkSeq(chequeobject.getBookNo());			        
                			        accounttransobject.setTransDate(chequeobject.getTransDate());
                			        accounttransobject.setTransNarr("Token "+chequeobject.getTokenNo());
                			        accounttransobject.setRef_No(chequeobject.getTokenNo());						
                			        accounttransobject.setPayeeName(chequeobject.getPayeeName());				
                					accounttransobject.setCdInd("D");
                					accounttransobject.uv.setUserId(chequeobject.uv.getUserId());
                					accounttransobject.uv.setUserTml(chequeobject.uv.getUserTml());
                					accounttransobject.uv.setUserDate(chequeobject.uv.getUserDate());				
                					accounttransobject.uv.setVerTml(tml);
                					accounttransobject.uv.setVerId(user);	
                					accounttransobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                				
                					if(!chequeobject.getTransMode().equals("PO"))
                					{
                					    accounttransobject.setAccNo(chequeobject.getAccNo());				
                					    accounttransobject.setAccType(String.valueOf(chequeobject.getAccType()));					    
                					    accounttransobject.setTransSource(chequeobject.uv.getUserTml());
                					    accounttransobject.setGLRefCode(Integer.parseInt(chequeobject.getAccType()));
                					    accounttransobject.setCloseBal(accountinfoobject.getAmount()-chequeobject.getTransAmount());
                					}
                					else
                					{
                					    accounttransobject.setAccNo(chequeobject.getAccNo());				
                					    accounttransobject.setAccType(chequeobject.getAccType());
                					    accounttransobject.setGLRefCode(1016001);					  
                					    accounttransobject.setTransSource(chequeobject.uv.getUserTml());
                					    accounttransobject.setCloseBal(0);
                					}
                					
                					String result=fcDelegate.verifyChequeWithdraw(accounttransobject);
                    			req.setAttribute("chqw",result);
                    			}
                    				else{
                    					
                    					req.setAttribute("chqw","Given Token number is Verified you can't do any modification");
                    				}
                    				
                    			}
                    			
                    			if(!chwForm.getAmount().equals("0.0")&& chwForm.getAmount()!=null){
                    				if(chwForm.getAcType().startsWith("101")){
                    					odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                    					chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                             			if(Double.parseDouble(chwForm.getAmount())>odccmasterobject.getCreditLimit()){
                        				req.setAttribute("chqw","Cannot Withdraw! CreditLimit is less than amount");
                             			}
                    				}
                    				else{
                    				AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                         			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                         			if(Double.parseDouble(chwForm.getAmount())>accountinfoobject.getAmount()){
                    				req.setAttribute("chqw","Cannot Withdraw! Account Balance is less than amount");
                         			}
                    			}
                    			}
                    				req.setAttribute("chwdetail",new String[]{chqWaction[0],chwForm.getAcno(),chwForm.getAcType()});
                    			
                    			req.setAttribute("chqwdetail",chqWaction);
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                    			
                    			
                    		}
                    		else{
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                req.setAttribute("chqw", chqWaction[0]);
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                    			
                    			
                    		}
                    		
                    		if(chwForm.getDetailsCombo().equals("Personal")&&chwForm.getDetailsCombo().length()!=0){
                    			if(chwForm.getAcType().startsWith("101")){
                    				odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                    				CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                        			req.setAttribute("cust",cust);
                        			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                        			chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                    			}
                    			else{
                				amObj=fcDelegate.getAccountMaster(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType().trim());
                				CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                    			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                    			}
                				CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                    			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                    			
                    			String jpath=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+jpath);
                    			req.setAttribute("flag",jpath); 
                    			  
                    		  }
                		 
                		  if(chwForm.getDetailsCombo().equals("Signature Ins")&&chwForm.getDetailsCombo().length()!=0){
                			  if(chwForm.getAcType().startsWith("101")){
                				  odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                  				CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                      			req.setAttribute("cust",cust);
                      			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                      			chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                  			
                			  }
                			  else{
                			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType().trim());
                			  CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                      			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                			  }
                    			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType());
                    			
                    			req.setAttribute("sigObject",signObject);
                    			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                    			//path=MenuNameReader.getScreenProperties("3012");
                    			System.out.println("path is---in Signature------------"+path);
                    			req.setAttribute("flag","/SingnatureInst.jsp"); 
                    			
                    		  }
                		
                			
                    		String perPath=MenuNameReader.getScreenProperties("3008");
                            System.out.println("PerPath"+perPath);
                            
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqWithdrawalForm",chwForm);;
            				System.out.println("At 438 path:"+map.getPath());
    				
    					
                    		
                    		}
                    }
                    
                    //should end here
                    String Path=MenuNameReader.getScreenProperties("3008");
                    System.out.println("PerPath"+Path);
                    
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("ChqWithdrawalForm",chwForm);;
    				System.out.println("At 1442 path:"+map.getPath());
    				return map.findForward(ResultHelp.getSuccess());
                }
                    
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        
        //Cheque Withdrawal Verification
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyFCChqwithdrawalMenu")){
            try{
            	VerifyFCChqWithdrawalForm chwForm=(VerifyFCChqWithdrawalForm)form;
            	
            	
            	System.out.println("At 1367<<---------->"+MenuNameReader.containsKeyScreen(chwForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(chwForm.getPageId()));
            	
            	System.out.println("At 1370"+chwForm);
               
            	req.setAttribute("pageNum",chwForm.getPageId());
               System.out.println("At 1373"+chwForm.getPageId());
               logger.info(chwForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(chwForm.getPageId())){
            	   System.out.println("At 1376---------->pageId"+chwForm.getPageId());
                   path=MenuNameReader.getScreenProperties(chwForm.getPageId());
                   System.out.println("At 390---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   chwForm.setDate(fcDelegate.getSysDate());
                   chwForm.setAmount("0.0");
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getComboElementsAll(0));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyFCChqwithdrawal")){
        	System.out.println("at 420 inside Verify AChequeWithdraw");
        	VerifyFCChqWithdrawalForm chwForm=(VerifyFCChqWithdrawalForm)form;

        	
        	System.out.println("at 1407 inside VerifyFCChqwithdrawal");
        	int int_daycash=fcDelegate.getCashStatus();
        	if(int_daycash==3)
			{
        		req.setAttribute("chqw","DayCash is Closed cant do any Transaction");				
				//setAttribute("chwdetail",new String[] {"chwdetail"});
			}	
			else if(int_daycash==1)
			{
				req.setAttribute("chqw","Cashier is Closed cant do any Transaction");				
				//req.setAttribute("chwdetail",new String[] {"chwdetail"});
			}	
        	
        	
            String path;
            System.out.println("sbform=="+chwForm);
            System.out.println("sbform=="+chwForm.getPageId());
            try{
                req.setAttribute("pageNum",chwForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(chwForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(chwForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    //to check for token Details
                    if(chwForm.getDetailsCombo()!=null&&chwForm.getDetailsCombo().equals("unverifiedtokens")&&chwForm.getDetailsCombo().length()!=0){
            			
            			  System.out.println("At 998 trying to display help window");
            			  String dis_colname[]=new String[5];
          		        dis_colname[0]="Token";
          		        dis_colname[1]="AC_Type";
          		        dis_colname[2]="AC_No";
          		        dis_colname[3]="Chq_No";
          		        dis_colname[4]="Amount";
          		        
          		      String colname[]=new String[5];
      		        colname[0]="token_no";
      		        colname[1]="ac_type";
      		        colname[2]="ac_no";
      		        colname[3]="chq_no";
      		        colname[4]="trn_amt";
      		        
          		        String colname_T[]= new String[1];
          		        colname_T[0]="token_no";
          		        String dis_colname_T[]=new String[1];
          		        dis_colname_T[0]="Token Number";
          		        String tablename="ChequeWithdrawal";
          		        String condition="ve_tml is null and ve_user is null and ve_date is null and trn_date='"+fcDelegate.getSysDate()+"'";
          		        
          		        Object[][] unverifiedtokens=fcDelegate.getunverifiedtokens(tablename,colname,condition);
          		        req.setAttribute("sbcols",dis_colname);
          		      req.setAttribute("columnData",unverifiedtokens);
          		    req.setAttribute("Pagehead","Unverified Token Nos.");
          		    
          		  req.setAttribute("flag",MenuNameReader.getScreenProperties("3058"));
          		    
            		  }
                    chequeobject=fcDelegate.gettokendetails(chwForm.getTokenno());
                    if(chequeobject!=null){
                    	if(chwForm.getSetwith().equals("setwith")){
                    	chwForm.setAcno(String.valueOf(chequeobject.getAccNo()));
                    	chwForm.setAcType(chequeobject.getAccType());
                    	chwForm.setChqslno(String.valueOf(chequeobject.getChqNo()));
                    	chwForm.setDate(chequeobject.getChqDate());				
                    	chwForm.setAmount(String.valueOf(chequeobject.getTransAmount()));
                    	chwForm.setPayee(chequeobject.getPayeeName());
        					        												
        				if(chequeobject.getTransMode().equals("Q"))
        					chwForm.setChqslpo("Cheque");
        				else if(chequeobject.getTransMode().equals("S"))
        					chwForm.setChqslpo("Slip");
        				else if(chequeobject.getTransMode().equals("PO"))
        					chwForm.setChqslpo("PO");
        		       	
        				chwForm.setSetwith("");
                    	}
                    
                    
                    System.out.println("actype=="+chwForm.getAcType());
                    System.out.println("acNum=="+chwForm.getAcno());
                    if(chwForm.getTokenno().equals("")){
                    if(chwForm.getAcType().equals("SELECT")||chwForm.getAcno().equals("")){
                    	System.out.println("At 435 inside FcAction");
                    	
                    	
                    	req.setAttribute("chqw","Enter token No");
                    	 String perPath=MenuNameReader.getScreenProperties("3008");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("ChqWithdrawalForm",chwForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    }
                    
                    else{
                    	
                    String checTno=fcDelegate.chkTokennoAgain(chwForm.getTokenno());
                    	
                    	System.out.println("At 452 in Action return of check"+checTno);
                    	if(!checTno.equals("no")){
                    		System.out.println("At 454 inside not null");
                    		String perPath=MenuNameReader.getScreenProperties("3008");
                            System.out.println("PerPath"+perPath);
                            req.setAttribute("chqw", checTno);
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqWithdrawalForm",chwForm);;
            				System.out.println("At 461 path:"+map.getPath());
                    		
                    		
                    	}
                    	else{
                    		if(chwForm.getChqslpo().equals("PO")){
                    			payorderobj=fcDelegate.getPOWithdrawal(chwForm.getChqslno());
                    			if(payorderobj!=null){
                    			if(payorderobj.getPOCancel().equals("T"))
    							{
                    				req.setAttribute("chqw","PayOrder is cancelled");
    								
    							}
    							else if(payorderobj.getPOStop().equals("T"))
    							{
    								req.setAttribute("chqw","PayOrder is stopped");
    								
    							}
    							else if(Validations.validDate(payorderobj.getPOValidUpTo(),fcDelegate.getSysDate())==-1)
    							{
    								System.out.println("po valid="+payorderobj.getPOValidUpTo());
    								req.setAttribute("chqw","PayOrder Valid Date is only upto"+payorderobj.getPOValidUpTo());
    																
    							}
    							else if(payorderobj.getPOCshIndicator()==1)
    							{
    								req.setAttribute("chqw","PayOrder is already Cashed");
    																
    							}
    							else
    							{
    								chwForm.setAmount(String.valueOf(payorderobj.getPOAmount()));
    								chwForm.setDate(payorderobj.getPODate());
    								chwForm.setPayee(payorderobj.getPOPayee());
    							    /*txt_amount.setText(String.valueOf(DoubleFormat.doubleToString(payorderobject.getPOAmount(),2)));
    								txt_payeename.setText(payorderobject.getPOPayee());
    								txt_date.setText(payorderobject.getPODate());*/
    								/*txt_accountnum.setText("0");
    								combo_accounttype.setSelectedIndex(0);
    																								
    								po_dtm.setNumRows(0);
    								Object data[]=new Object[4];
    								if(payorderobject!=null)
    								{									
    									data[0]=String.valueOf(payorderobject.getPOChqNo());
    									data[1]=String.valueOf(payorderobject.getPOSerialNo());									
    									data[2]=String.valueOf(payorderobject.getPODate());
    																		
    									po_dtm.addRow(data);
    								}			*/
                    			
                    		}
                    			}
                    			else{
                    				req.setAttribute("chqw","PayOrder Not Found");
                    			}
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                				return map.findForward(ResultHelp.getSuccess());
                    		}
                    		String[] chqWaction=fcDelegate.chqWithdraw(chwForm.getTokenno(),chwForm.getAcType(),chwForm.getAcno(),chwForm.getChqslpo());
                    		System.out.println("At 987 in else chqWaction.length "+chqWaction.length);
                    		System.out.println("At 988 values are "+chqWaction[0]);
                    		if(chqWaction.length>1){
                    			
                    			
                    			
                    			for(int k=0;k<chqWaction.length;k++){
                    				System.out.println("At 991 values are "+chqWaction[k]);
                    				
                    				
                    			}
                    			
                    			if(chwForm.getChqslno().trim()!=null&&chwForm.getChqslno().trim().length()!=0&&chwForm.getChqslpo().equals("Cheque"))
                    			{
                    				//to get Cheque Details of Individual Accounts
                    			Vector checqdet=fcDelegate.getindividualChequeDet(chwForm.getAcType(),chwForm.getAcno());
                    			System.out.println("checqdet====>"+checqdet);
                    			req.setAttribute("chqdet",checqdet);
                    				
                    			String chequeResult=fcDelegate.getAccountChequeDet(chwForm.getAcType(),chwForm.getAcno(),chwForm.getChqslno());	
                    				if(!chequeResult.equals("Alright")){
                    			req.setAttribute("chqw",chequeResult);
                    				//req.setAttribute("chwdetail",new String[] {"chwdetail"});
                    				chwForm.setHidval("");
                    				}
                    			}
                    			
                    			if(chwForm.getHidval().equals("submit")){
                    				chequeobject=new ChequeObject();
                    			//	chequeobject.setBookNo(Integer.parseInt(dtm.getValueAt(table_chq_details.getSelectedRow(),0).toString()));
                    				System.out.println("----------------Updating  values-----------------"+chwForm.getChqslno());
        				        	if(chwForm.getChqslpo().equals("Cheque"))
        				        	{
        				        	    chequeobject.setTransMode("Q");
        				        	    chequeobject.setCashPD("F");
        				        	    chequeobject.setBookNo(0);
        				        	}
        				        	else if(chwForm.getChqslpo().equals("Slip")){
        				        	    chequeobject.setTransMode("S");
        				        	    chequeobject.setCashPD("T");
        				        	}
        				        	else if(chwForm.getChqslpo().equals("PO")){
        				        	    chequeobject.setTransMode("PO");
        				        	    chequeobject.setCashPD("F");
        				        	}
        				        	String[] chqdetail=fcDelegate.getChqDetails(chwForm.getChqslno());
        				        	
        				        	if(chqdetail!=null&&chqdetail.length>1){
        				        		 
        				        	chequeobject.setBookNo(Integer.parseInt(chqdetail[2]));
        				        	}
        				        	else{chequeobject.setBookNo(0);}
        				        	chequeobject.setAccType(chwForm.getAcType());
        				        	chequeobject.setAccNo(Integer.parseInt(chwForm.getAcno()));
        				        	chequeobject.setTokenNo(Integer.parseInt(chwForm.getTokenno()));					    				 
        				        	chequeobject.setTransType("P");
        				        					        	
        				        	chequeobject.setTransDate(fcDelegate.getSysDate());
        				        	chequeobject.setChqDate(chwForm.getDate());
        				        	chequeobject.setTransAmount(Double.parseDouble(chwForm.getAmount()));
        			        	    chequeobject.setPayeeName(chwForm.getPayee());		
        			        	    System.out.println("---------Submitting tml -------------------------");
        				        	chequeobject.uv.setUserTml(tml);//(String)req.getAttribute("tml")
        				        	chequeobject.uv.setUserId(user);//(String)req.getAttribute("uid")	
        				        	chequeobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
        				        	chequeobject.setChqNo(Integer.parseInt(chwForm.getChqslno()));
        				        	
        				        	
        				        	
                    				if(chwForm.getDelet()!=null&&chwForm.getDelet().equals("delete")){
        				        	String result=fcDelegate.submitCheque(chequeobject,1);
        				        	req.setAttribute("chqw",result);
        				        	UserActivityMasterObject user_activ=new UserActivityMasterObject();
 									user_activ.setUser_id(user);//HARD CODED
 					            	user_activ.setTml_no("LN01");//HARD CODED
 					            	user_activ.setPage_visit("VerifyFCChqWithdrawal");//HARD CODED
 					            	user_activ.setActivity("Deleting Withdrawal");
 					            	user_activ.setAc_no(chequeobject.getAccNo());
 					            	user_activ.setCid(chequeobject.getCustomerId());
 					            	user_activ.setActivity_date(fcDelegate.getSysDate());
 					            	user_activ.setActivity_time(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
 					            	fcDelegate.storeUserActivity(user_activ);
 					            	
                    				}else{
                    					String result=fcDelegate.submitCheque(chequeobject,0);
                    					if(result.startsWith("Transaction"))
                    					req.setAttribute("chqw","Cheque Withdrawal Successfully Updated");
                    					else
                    						req.setAttribute("chqw",result);
                    					UserActivityMasterObject user_activ=new UserActivityMasterObject();
     									user_activ.setUser_id(user);//HARD CODED
     					            	user_activ.setTml_no("LN01");//HARD CODED
     					            	user_activ.setPage_visit("VerifyFCChqWithdrawal");//HARD CODED
     					            	user_activ.setActivity("Updating Withdrawal");
     					            	user_activ.setAc_no(chequeobject.getAccNo());
     					            	user_activ.setCid(chequeobject.getCustomerId());
     					            	user_activ.setActivity_date(fcDelegate.getSysDate());
     					            	user_activ.setActivity_time(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
     					            	fcDelegate.storeUserActivity(user_activ);
                    				}
        				        	
        				        	 /*UserActivityMasterObject user_activ=new UserActivityMasterObject();
 									user_activ.setUser_id(MainScreen.head.getUid());
 					            	user_activ.setTml_no(MainScreen.head.getTml());
 					            	user_activ.setPage_visit(MainScreen.ji.getTitle());
 					            	user_activ.setActivity(UserActivityHeading.getActivity(2));
 					            	user_activ.setAc_no(chequeobject.getAccNo());
 					            	user_activ.setCid(chequeobject.getCustomerId());
 					            	user_activ.setActivity_date(MainScreen.head.getSysDate());
 					            	user_activ.setActivity_time(MainScreen.head.getSysTime());*/
                    			}
                    			else if(chwForm.getHidval().equals("verify")){
                    				chequeobject=fcDelegate.getVerifyChqObject(chwForm.getTokenno());
                    				AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                    				System.out.println("-------------INSIDE VERIFICATION------------------------");
                    				if(chequeobject!=null)
                    				{
                    				AccountTransObject accounttransobject=new AccountTransObject();
                    				
                			        accounttransobject.setTransType(chequeobject.getTransType());
                			        accounttransobject.setTransAmount(chequeobject.getTransAmount());
                			        accounttransobject.setTransMode(chequeobject.getTransMode());
                			        accounttransobject.setChqDDNo(chequeobject.getChqNo());
                			        accounttransobject.setPassBkSeq(chequeobject.getBookNo());			        
                			        accounttransobject.setTransDate(chequeobject.getTransDate());
                			        accounttransobject.setTransNarr("Token "+chequeobject.getTokenNo());
                			        accounttransobject.setRef_No(chequeobject.getTokenNo());						
                			        accounttransobject.setPayeeName(chequeobject.getPayeeName());				
                					accounttransobject.setCdInd("D");
                					System.out.println("Nothing is wrong till 1686");
                					accounttransobject.uv.setUserId(chequeobject.uv.getUserId());
                					accounttransobject.uv.setUserTml(chequeobject.uv.getUserTml());
                					accounttransobject.uv.setUserDate(chequeobject.uv.getUserDate());
                					System.out.println("Hello----");
                					accounttransobject.uv.setVerTml(tml);
                					accounttransobject.uv.setVerId(user);	
                					accounttransobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                					System.out.println("Hello---1695-"+chequeobject.getTransMode());
                					if(!chequeobject.getTransMode().equals("PO"))
                					{
                					    accounttransobject.setAccNo(chequeobject.getAccNo());				
                					    accounttransobject.setAccType(chequeobject.getAccType());					    
                					    accounttransobject.setTransSource(chequeobject.uv.getUserTml());
                					    accounttransobject.setGLRefCode(Integer.parseInt(chequeobject.getAccType()));
                					    accounttransobject.setCloseBal(accountinfoobject.getAmount()-chequeobject.getTransAmount());
                					    System.out.println("Hell");
                					}
                					else
                					{
                					    accounttransobject.setAccNo(chequeobject.getAccNo());				
                					    accounttransobject.setAccType(chequeobject.getAccType());
                					    accounttransobject.setGLRefCode(1016001);					  
                					    accounttransobject.setTransSource(chequeobject.uv.getUserTml());
                					    accounttransobject.setCloseBal(0);
                					}
                					System.out.println("Hello---1712-");
                					String result=fcDelegate.verifyChequeWithdraw(accounttransobject);
                    			req.setAttribute("chqw",result);
                    			}
                    				else{
                    					
                    					req.setAttribute("chqw","Given Token number is Verified you can't do any modification");
                    				}
                    				chwForm.setHidval("");
                    			}
                    			
                    			if(!chwForm.getAmount().equals("0.0")&& chwForm.getAmount()!=null){
                    				if(chwForm.getAcType().startsWith("101")){
                    					odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                    					chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                             			if(Double.parseDouble(chwForm.getAmount())>odccmasterobject.getCreditLimit()){
                        				req.setAttribute("chqw","Cannot Withdraw! CreditLimit is less than amount");
                             			}
                    				}
                    				else{
                    				AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                         			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                         			if(Double.parseDouble(chwForm.getAmount())>accountinfoobject.getAmount()){
                    				req.setAttribute("chqw","Cannot Withdraw! Account Balance is less than amount");
                         			}
                    			}
                    			}
                    				req.setAttribute("chwdetail",new String[]{chqWaction[0],chwForm.getAcno(),chwForm.getAcType()});
                    			
                    			req.setAttribute("chqwdetail",chqWaction);
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                    			
                    			
                    		}
                    		else{
                    			String perPath=MenuNameReader.getScreenProperties("3008");
                                System.out.println("PerPath"+perPath);
                                req.setAttribute("chqw", chqWaction[0]);
                                req.setAttribute("pageId",path);
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	 req.setAttribute("ChqWithdrawalForm",chwForm);;
                				System.out.println("At 438 path:"+map.getPath());
                    			
                    			
                    		}
                    		
                    		if(chwForm.getDetailsCombo().equals("Personal")&&chwForm.getDetailsCombo().length()!=0){
                    			if(chwForm.getAcType().startsWith("101")){
                    				odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                    				CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                        			req.setAttribute("cust",cust);
                        			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                        			chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                    			}
                    			else{
                				amObj=fcDelegate.getAccountMaster(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType().trim());
                				CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                    			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                    			}
                				CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                    			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                    			
                    			String jpath=MenuNameReader.getScreenProperties("3032");
                    			System.out.println("path is---------------"+jpath);
                    			req.setAttribute("flag",jpath); 
                    			  
                    		  }
                		 
                		  if(chwForm.getDetailsCombo().equals("Signature Ins")&&chwForm.getDetailsCombo().length()!=0){
                			  if(chwForm.getAcType().startsWith("101")){
                				  odccmasterobject=fcDelegate.odccData(chwForm.getAcType(),chwForm.getAcno());
                  				CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                      			req.setAttribute("cust",cust);
                      			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                      			chwForm.setAcbalance(String.valueOf(odccmasterobject.getCreditLimit()));
                  			
                			  }
                			  else{
                			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType().trim());
                			  CustomerMasterObject cust=fcDelegate.getCustomer(amObj.getCid());
                    			req.setAttribute("cust",cust);
                    			 AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(chwForm.getAcType(), Integer.parseInt(chwForm.getAcno()));
                      			chwForm.setAcbalance(String.valueOf(accountinfoobject.getAmount()));
                			  }
                    			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(chwForm.getAcno()),chwForm.getAcType());
                    			
                    			req.setAttribute("sigObject",signObject);
                    			//System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                    			//path=MenuNameReader.getScreenProperties("3012");
                    			System.out.println("path is---in Signature------------"+path);
                    			req.setAttribute("flag","/SingnatureInst.jsp"); 
                    			
                    		  }
                			
                    		String perPath=MenuNameReader.getScreenProperties("3055");
                            System.out.println("PerPath"+perPath);
                            
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqWithdrawalForm",chwForm);;
            				System.out.println("At 438 path:"+map.getPath());
    				
    					
                    		
                    		}
                    }
                    } //end of if(chequeObject!=null)
                    else{
                    	req.setAttribute("chqw","Please Enter Correct Token No.");
                    	
                    }
                    //should end here
                    String Path=MenuNameReader.getScreenProperties("3055");
                    System.out.println("PerPath"+Path);
                    
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("ChqWithdrawalForm",chwForm);;
    				System.out.println("At 1442 path:"+map.getPath());
    				return map.findForward(ResultHelp.getSuccess());
                }
                
            
                    
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
                }
        
        //Cheque Withdrawal Verification Ends here
//Cheque Issue
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChqIssueMenu")){
            try{
            	ChqIssueForm chiForm=(ChqIssueForm)form;
            	
            	
            	System.out.println("At 379<<---------->"+MenuNameReader.containsKeyScreen(chiForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(chiForm.getPageId()));
            	
            	System.out.println("At 382"+chiForm);
               
            	req.setAttribute("pageNum",chiForm.getPageId());
               System.out.println("At 379"+chiForm.getPageId());
               logger.info(chiForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(chiForm.getPageId())){
            	   System.out.println("At 388---------->pageId"+chiForm.getPageId());
                   path=MenuNameReader.getScreenProperties(chiForm.getPageId());
                   System.out.println("At 390---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("chqdetails","");
                   req.setAttribute("AcType",fcDelegate.getComboElementsAll(0));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChequeIssue")){
        	System.out.println("at 407 inside /FrontCounter/ChequeIssue");
        	ChqIssueForm chiForm=(ChqIssueForm)form;
            String path;
            System.out.println("chiForm=="+chiForm);
            System.out.println("chiForm=="+chiForm.getPageId());
            try{
                req.setAttribute("pageNum",chiForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(chiForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(chiForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+chiForm.getAcType());
                    System.out.println("acNum=="+chiForm.getAcnum().trim());
                    if(chiForm.getAcType().equals("SELECT")||chiForm.getAcnum().trim().length()==0){
                    	System.out.println("At 412 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("empty","Account Type and Account Number must be Provided");
                    	req.setAttribute("closingmsg","");
                    	 String perPath=MenuNameReader.getScreenProperties("3009");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("ChqIssueForm",chiForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	
                    	if(chiForm.getAcnum()!=null&&!chiForm.getAcnum().equals("")){
                    	
                    		   accountinfoobject=fcDelegate.getAccountInfo(chiForm.getAcType(), Integer.parseInt(chiForm.getAcnum()));
                    		   if(accountinfoobject==null)
                    		   {
                   				req.setAttribute("empty","Account Not found");
                   				req.setAttribute("closingmsg","");
                   				String perPath=MenuNameReader.getScreenProperties("3009");
                   				req.setAttribute("pageId",path);
                   				req.setAttribute("AcType",fcDelegate.getComboElements(0));
                   				req.setAttribute("ChqIssueForm",chiForm);
                            	
                				return map.findForward(ResultHelp.getSuccess());
                   			
                    		   }
                    		            			
                    			
                    		  if(accountinfoobject!=null){
                    			  
                    			  if(accountinfoobject.getAccStatus().equals("C")){
                    				  req.setAttribute("empty","ACCOUNT CLOSED");
                    				  chiForm.setSubmiting("");
                    			  }else if(accountinfoobject.getAccStatus().equals("I")){
                    				  req.setAttribute("empty","ACCOUNT INOPERATIVE");
                    				  chiForm.setSubmiting("");
                    				 
                    			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                    				  req.setAttribute("empty","ACCOUNT FREEZED");
                    				  chiForm.setSubmiting("");
                    				  
                    			  }
                    			  
                    			  //for Signature Instruction
                    			
                          			/*CustomerMasterObject cust=fcDelegate.getCustomer(accountinfoobject.getCid());
                          			req.setAttribute("cust",cust);
                          			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(chiForm.getAcnum()),chiForm.getAcType());
                          			
                          			req.setAttribute("signature",signObject);
                          			System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                          			path=MenuNameReader.getScreenProperties("3012");
                          			System.out.println("path is---------------"+path);
                          			req.setAttribute("flag","/SingnatureInst.jsp"); 
                          			  */
                          		 
                    		  
                    			  req.setAttribute("acinfo", accountinfoobject);
                    		  
                    		  }
                    		  else{
                    			  req.setAttribute("empty","Account No. not found");
                    			  chiForm.setSubmiting("");
                    		  }
                    		  
                    	
                    	}
                    	
                    	if(chiForm.getSubmiting()!=null&&chiForm.getSubmiting().length()>0){
                    		try
                    		{
                    			if(chiForm.getSubmiting().equals("update"))
                    				chqIssuevalue="submit";
                    			else if(chiForm.getSubmiting().equals("delete"))
                    			chqIssuevalue="delete";
                    			
                    		    ChequeObject chequeobject=new ChequeObject();

                    			chequeobject.setAccNo(Integer.parseInt(chiForm.getAcnum()));
                    			chequeobject.setAccType(String.valueOf(chiForm.getAcType()));
                    			chequeobject.setTransDate(fcDelegate.getSysDate());
                    			chequeobject.setBookNo(Integer.parseInt(chiForm.getChbookno()));
                    			chequeobject.setNumLeaf(Integer.parseInt(chiForm.getLeaveno()));
                    			chequeobject.setF_Chq_Prev(Integer.parseInt(chiForm.getPrechbook()));

                    			chequeobject.setFirstChequeNo(Integer.parseInt(chiForm.getFromno()));
                    			chequeobject.setLastChequeNo(Integer.parseInt(chiForm.getTono()));
                    			chequeobject.setNumBounce(0);
                    			chequeobject.uv.setUserId(user);
                    			chequeobject.uv.setUserTml(tml);
                    			chequeobject.uv.setUserDate(fcDelegate.getSysDate());
                    			if(chiForm.getSubmiting().equals("verify")){
                    				String store=fcDelegate.chequeIssueVerification(chequeobject,chiForm.getAcType(),chiForm.getAcnum(),chiForm.getChbookno(),chiForm.getFromno(),chiForm.getTono());
                    				req.setAttribute("empty",store);
                    				chiForm.setSubmiting("");
                   			}
                    			else{
                    			String store=fcDelegate.storechqIssue(chequeobject,chqIssuevalue);
                    			req.setAttribute("empty",store);
                    			chiForm.setSubmiting("");
                    			}
                    		}
                    		catch(Exception e){e.printStackTrace();}
                    		String perPath=MenuNameReader.getScreenProperties("3007");
                            System.out.println("PerPath"+perPath);
                            
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqIssueForm",chiForm);;
            				System.out.println("At 610 path:"+map.getPath());
                    	}
                    	else{
                    String[] chqret=fcDelegate.chqissue(chiForm.getAcType(), chiForm.getAcnum());
                    SignatureInstructionObject[] sio=fcDelegate.getSignature(Integer.parseInt(chiForm.getAcnum()),chiForm.getAcType());
                    	System.out.println("At 437 in Action return of check"+chqret.length);
                    	if(chqret.length>1){
                    		req.setAttribute("chqissuedetails","chqret");
                    		for(int k=0;k<chqret.length;k++){
                    		System.out.println("At 555 value "+k+ ""+chqret[k]);
                    		}
                    		chiForm.setAccname(accountinfoobject.getAccname()); 
                    		chiForm.setChbookno(chqret[2]);
                    		chiForm.setPrechbook(chqret[1]);
                    		chiForm.setLeaveno(chqret[4]);
                    		chiForm.setFromno(chqret[6]);
                    		chiForm.setTono(chqret[7]);
                    		chiForm.setDateissue(chqret[3]);
                    		
                    		
                    		
                    		req.setAttribute("chqissuedetails",chqret);
                    		req.setAttribute("signInst",sio);
                    		
                    		String perPath=MenuNameReader.getScreenProperties("3007");
                            System.out.println("PerPath"+perPath);
                            req.setAttribute("flag",MenuNameReader.getScreenProperties("3012"));
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("ChqIssueForm",chiForm);;
            				System.out.println("At 446 path:"+map.getPath());
                    		
                    		
                    	}
                    	else{
                    		accountinfoobject=fcDelegate.getAccountInfo(chiForm.getAcType(), Integer.parseInt(chiForm.getAcnum()));
                    		                   		
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    chiForm.setAccname(accountinfoobject.getAccname());
                    chiForm.setDateissue(fcDelegate.getSysDate());
                    chiForm.setPrechbook("0");
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("empty",chqret[0]);
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("ChqIssueForm",chiForm);;
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                    }
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }
               // return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);
            path=MenuNameReader.getScreenProperties("0000");
            System.out.println("At 1395"+e);
            setErrorPageElements("Unable to process request",req,path);
            return map.findForward(ResultHelp.getError());
            
            }
            return map.findForward(ResultHelp.getSuccess());
        }

        //Query On Cheque
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChqQueryMenu")){
            try{
            	ChqQueryForm chqrForm=(ChqQueryForm)form;
            	
            	
            	System.out.println("At 2069<<---------->"+MenuNameReader.containsKeyScreen(chqrForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(chqrForm.getPageId()));
            	
            	System.out.println("At 2072"+chqrForm);
               
            	req.setAttribute("pageNum",chqrForm.getPageId());
               System.out.println("At 2075"+chqrForm.getPageId());
               logger.info(chqrForm.getPageId()+"This is from QueryOnChqMenu"+map.getPath());
               if(MenuNameReader.containsKeyScreen(chqrForm.getPageId())){
            	   System.out.println("At 2078---------->pageId"+chqrForm.getPageId());
                   path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                   System.out.println("At 2080---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                  
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChqQuery")){
        	System.out.println("at 624 inside QueryOn Cheque");
        	ChqQueryForm chqrForm=(ChqQueryForm)form;
            String path;
            System.out.println("ChqueryOnChequeform=="+chqrForm);
            System.out.println("ChqueryOnChequeformID=="+chqrForm.getPageId());
            try{
                req.setAttribute("pageNum",chqrForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(chqrForm.getPageId())){
                	if(chqrForm.getClr().equals("clearall")){
                		 System.out.println("At 2111---------->pageId"+chqrForm.getPageId());
                         path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                         System.out.println("At 2114---------->path"+path);
                         fcDelegate=new FrontCounterDelegate();
                         req.setAttribute("pageId",path);
                         chqrForm.setChqno("0");
                         return map.findForward(ResultHelp.getSuccess());
                		
                	}
                	if(!chqrForm.getChqno().equals("")){
                    path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("ChequeNo=="+chqrForm.getChqno());
                    
                    String[] chqdetail=fcDelegate.getChqDetails(chqrForm.getChqno());
                    req.setAttribute("chqdetail",chqdetail);
                    if(chqdetail==null){
                    	path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                        System.out.println("At 642---------->path"+path);
                        req.setAttribute("empty","Invalid Cheque Number ");
                        req.setAttribute("pageId",path);
                        
        				return map.findForward(ResultHelp.getSuccess());
                    	
                    }
                    System.out.println("At 645 in Action chqdetail.length is "+chqdetail.length);
                    for(int l=0;l<chqdetail.length;l++){
                    	System.out.println("At 647in Actionchq detail values "+chqdetail[l]);
                    	
                    }
                   // System.out.println("At 646 in Action First value "+chqdetail[2]);
                    //changed
                    
        			
                    //here
                    
                    
                    path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                    System.out.println("At 642---------->path"+path);
                    
                    req.setAttribute("pageId",path);
                    
    				return map.findForward(ResultHelp.getSuccess());
                	}
                	else{
                		req.setAttribute("empty","Cheque number must be provided");
                	path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("ChequeNo=="+chqrForm.getChqno());
                    
                   
                    path=MenuNameReader.getScreenProperties(chqrForm.getPageId());
                    System.out.println("At 657---------->path"+path);
                    
                    req.setAttribute("pageId",path);
                    
    				return map.findForward(ResultHelp.getSuccess());
                }
                	}
                
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }

       //Cheque Instruction Start
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChequeInstructionMenu")){
            try{
            	ChqInstructionForm chinstForm=(ChqInstructionForm)form;
            	chinstForm.setSysdate(FrontCounterDelegate.getSysDate());
            	System.out.println("At 379<<---------->"+MenuNameReader.containsKeyScreen(chinstForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(chinstForm.getPageId()));
            	
            	System.out.println("At 382"+chinstForm);
               
            	req.setAttribute("pageNum",chinstForm.getPageId());
               System.out.println("At 379"+chinstForm.getPageId());
               logger.info(chinstForm.getPageId()+"This is from Cheque Instruction"+map.getPath());
               if(MenuNameReader.containsKeyScreen(chinstForm.getPageId())){
            	   System.out.println("At 388---------->pageId"+chinstForm.getPageId());
                   path=MenuNameReader.getScreenProperties(chinstForm.getPageId());
                   System.out.println("At 390---------->path"+path);
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   //req.setAttribute("chqdetails","");
                   //req.setAttribute("AcType",fcDelegate.getComboElementsAll(0));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ChequeInstruction")){
        	System.out.println("at 782 inside ChequeInstruction");
        	ChqInstructionForm chinsForm=(ChqInstructionForm)form;
        	chinsForm.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("chqInsform=="+chinsForm);
            System.out.println("chqInsform=="+chinsForm.getPageId());
            try{
                req.setAttribute("pageNum",chinsForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(chinsForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(chinsForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("Chq Inst=="+chinsForm.getChqins());
                    System.out.println("Cheque Num=="+chinsForm.getChqno());
                    if(chinsForm.getChqins().equals("SELECT")&&chinsForm.getChqno().equals("")){
                    	System.out.println("At 796 inside FcAction");
                    	
                    	req.setAttribute("chqins","Cheque Instruction and Cheque Number must be Provided");
                    	
                    	 String perPath=MenuNameReader.getScreenProperties("3013");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("ChqInstructionForm",chinsForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	if(chinsForm.getChqins().equals("StopPayment")){
                    		req.setAttribute("chqinscombo","stop");
                    		
                    	}
                    	else if(chinsForm.getChqins().equals("PostDated")){
                    		req.setAttribute("chqinscombo","post");
                    	}
                    	else if(chinsForm.getChqins().equals("Cancel")){
                    		req.setAttribute("chqinscombo","cancel");
                    	}
                    	//On Submit
                    	if(chinsForm.getSelectedcombo().equals("submit")){
                    		System.out.println("At 824 in action on Submit");
                    		if(chinsForm.getChqins().equals("StopPayment")){
                    			chqInststop=1;
                    			System.out.println("Stop payment"+chqInststop);}
                    		else if(chinsForm.getChqins().equals("PostDated")){chqInstpost=1;}
                    		else if(chinsForm.getChqins().equals("Cancel")){chqInstcancel=1;}
                    		String result=fcDelegate.storeIntoChequeObject(chinsForm.getChqno(),chqInststop,chqInstpost,chqInstcancel);
                    		System.out.println("AT 1752 result "+result);
                    		req.setAttribute("chqins",result);
                    		chinsForm.setSelectedcombo("a");
                    		String perPath=MenuNameReader.getScreenProperties("3013");
                          	 req.setAttribute("pageId",path);
                          	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                          	 req.setAttribute("ChqInstructionForm",chinsForm);
                          	return map.findForward(ResultHelp.getSuccess());
                    		
                    	}
                    	
                    	
                    	fcDelegate=new FrontCounterDelegate();
                    	String[] ChqDetails=fcDelegate.select(chinsForm.getChqno());
                    	//On Submit
                    	
                    	if(ChqDetails!=null&&ChqDetails.length>1){
                    		for(int h=0;h<ChqDetails.length;h++){
                    			
                    			System.out.println("value of ChqDetail "+ChqDetails[h]);
                    		}
                    		req.setAttribute("chqinsDetails",ChqDetails);
                    		String perPath=MenuNameReader.getScreenProperties("3013");
                          	 req.setAttribute("pageId",path);
                          	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                          	 req.setAttribute("ChqInstructionForm",chinsForm);
                          	return map.findForward(ResultHelp.getSuccess());
                    	
                    	
                    	
                    	}
                    	else{
                    		req.setAttribute("chqins","Cheque Not Found");
                    	String perPath=MenuNameReader.getScreenProperties("3013");
                   	 req.setAttribute("pageId",path);
                   	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                   	 req.setAttribute("ChqInstructionForm",chinsForm);
                    	
                   	return map.findForward(ResultHelp.getSuccess());
                    	}	
                    }
    				
    				
                }
                return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }

        
        //Cheque Instruction Ends
        
        
        //PO data entry starts
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoDeMenu")){
            try{
            	PODataEntryForm poDEForm=(PODataEntryForm)form;
            	
            	
            	System.out.println("At 1658<<---------->"+MenuNameReader.containsKeyScreen(poDEForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(poDEForm.getPageId()));
            	
            	System.out.println("At 1661"+poDEForm);
               
            	req.setAttribute("pageNum",poDEForm.getPageId());
               System.out.println("At 1664"+poDEForm.getPageId());
               logger.info(poDEForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(poDEForm.getPageId())){
            	   System.out.println("At 1667---------->");
                   path=MenuNameReader.getScreenProperties(poDEForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   req.setAttribute("po",fcDelegate.getPoModule(0));
                   req.setAttribute("","acType");
                   req.setAttribute("cat",fcDelegate.subCat("0"));
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoDe")){
        	System.out.println("at inside /FrontCounter/PoDe");
        	PODataEntryForm poDEForm=(PODataEntryForm)form;
            String path;
            System.out.println("poDEForm=="+poDEForm);
            System.out.println("poDEForm=="+poDEForm.getPageId());
            try{
                req.setAttribute("pageNum",poDEForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(poDEForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(poDEForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    if(poDEForm.getSrlno()!=null&&poDEForm.getSrlno().trim().length()!=0&&Integer.parseInt(poDEForm.getSrlno().trim())>0){
                    	System.out.println("Srl No is not 0");
                    payoorderobject=fcDelegate.getPOStoreDetails(poDEForm.getSrlno());
                    if(payoorderobject!=null){
                    	System.out.println("PO Object is not null");
                    	if(payoorderobject.uv.getVerTml()!=null){
                    		
                    		req.setAttribute("closingmsg","PayOrder Already Verified");
                    		
                    	}
                    	else{
                    		//if PO not Verified
                    		if(poDEForm.getSrno().equals("hello")){
                    	if(payoorderobject.getCustType()!=0){
                    		//customer PayOrder
                    		req.setAttribute("cust","forcustomer");
                    		poDEForm.setTo("customer");
                    		poDEForm.setCat(String.valueOf(payoorderobject.getCustType()));
                    		poDEForm.setAcType(payoorderobject.getPOAccType());
                    		poDEForm.setAcno(String.valueOf(payoorderobject.getPOAccNo()));
                    		poDEForm.setPurName(payoorderobject.getPOPayee());
                    		poDEForm.setGlcode(String.valueOf(payoorderobject.getPOGlCode()));
                    		poDEForm.setGltype(payoorderobject.getPOGlType());
                    		poDEForm.setFavour(payoorderobject.getPOFavour());
                    		poDEForm.setAmount(String.valueOf(payoorderobject.getPOAmount()));
                    		poDEForm.setFavour(payoorderobject.getPOFavour());
                    		poDEForm.setComission(String.valueOf(payoorderobject.getCommissionAmount()));
                    		
                    		
                    		
                    	}
                    	else{
                    		req.setAttribute("cust","forbank");
                    		poDEForm.setTo("bank");
                    		poDEForm.setGltyp(String.valueOf(payoorderobject.getPOGlCode()));
                    		poDEForm.setGl(payoorderobject.getPOGlType());
                    		poDEForm.setFavour(payoorderobject.getPOFavour());
                    		poDEForm.setAmount(String.valueOf(payoorderobject.getPOAmount()));
                    		poDEForm.setComission(String.valueOf(payoorderobject.getCommissionAmount()));
                    		poDEForm.setBankpurName(payoorderobject.getPOPayee());
                    		
                    	}
                    	poDEForm.setSrno("");
                    		}
                    	//Starts here
                    	System.out.println("to=="+poDEForm.getTo());
                        // System.out.println("acNum=="+poDEForm.getAcno());
                         
                         if(poDEForm.getTo().equals("SELECT")){
                         	
                         		
                         		req.setAttribute("closingmsg","Fill all the fields");
                         		
                         		
                         		req.setAttribute("pageId",path);
                         		 req.setAttribute("cat",fcDelegate.subCat("0"));
                              	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                              	req.setAttribute("PODataEntryForm",poDEForm);
                              	return map.findForward(ResultHelp.getSuccess());
                         	
                          	
              			
                         }
                         else{
                         	
                             if(poDEForm.getAcno()!=null){
                           	  if(poDEForm.getAcno()!=null&&!poDEForm.getAcno().equals("")){
                           		  if(poDEForm.getAcType().startsWith("100")){
                           		  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                           		  if(accountinfoobject!=null){
                           			  
                           			  if(accountinfoobject.getAccStatus().equals("C")){
                           				  req.setAttribute("closingmsg","CLOSED");
                           			  }else if(accountinfoobject.getAccStatus().equals("I")){
                           				req.setAttribute("closingmsg","INOPERATIVE");
                           			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                           				req.setAttribute("closingmsg","FREEZED");
                           			  }
                           			  else{
                           			poDEForm.setPurName(accountinfoobject.getAccname());
                               		poDEForm.setGlcode(accountinfoobject.getGLCode());
                               		System.out.println("glname==========="+accountinfoobject.getGLName());
                               		poDEForm.setGlname(accountinfoobject.getGLName());
                               		poDEForm.setGltype(accountinfoobject.getGLType());
                               		poDEForm.setBalance(String.valueOf(accountinfoobject.getAmount()));
                           			  }
                           		  }
                           		  else{
                           			req.setAttribute("closingmsg","Account not found");
                           			poDEForm.setSubval("invalid");
                           		  }
                           		
                           		
                           		if(poDEForm.getAmount().length()!=0&&poDEForm.getAmount()!=null){
                           			if(Double.parseDouble(poDEForm.getAmount())<Double.parseDouble(poDEForm.getBalance())){
                           			double comm=fcDelegate.poComission(poDEForm.getPo(),poDEForm.getCat(),poDEForm.getAmount());
                           			
                           			if(comm<=0.0){
                           				System.out.println("comm<0.0");
                           				req.setAttribute("closingmsg","PO Commission not defined");
                           				req.setAttribute("sub","invalid");
                           			}
                           			else{
                           				poDEForm.setComission(String.valueOf(comm));
                           			}
                           		}
                           			else{
                               			req.setAttribute("closingmsg","Amount is more than balance ");
                           				req.setAttribute("sub","invalid");
                               			
                               		}
                           		}
                           		
                           		/*if(poDEForm.getHidval().equals("submit")){
                           			//for submitting the values
                           			
                           		}*/
                           	  }
                           		  else if(poDEForm.getAcType().startsWith("101")){
                           			
                           			odccmasterobject=fcDelegate.odccData(poDEForm.getAcType(),poDEForm.getAcno());	  
                           			if(odccmasterobject!=null){
                           				
                             			  if(odccmasterobject.getAccountStatus().equals("C")){
                             				  req.setAttribute("closingmsg","CLOSED");
                             			  }/*else if(accountinfoobject.getAccountStatus().equals("O")){
                             				  acStatus="OPENED";
                             			  }*/else if(odccmasterobject.getAccountStatus().equals("I")){
                             				req.setAttribute("closingmsg","INOPERATIVE");
                             			  }else if(odccmasterobject.getFreezeInd().equals("T")){
                             				req.setAttribute("closingmsg","FREEZED");
                             			  }
                             			  else{
                             				  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                             			  accountObj=fcDelegate.getOdccAccount("1001001",String.valueOf(odccmasterobject.getShareAccNo()));
                                 			poDEForm.setPurName(accountObj.getAccname());
                                     		poDEForm.setGlcode(accountinfoobject.getGLCode());
                                     		//System.out.println("glname==========="+odccmasterobject.getGLName());
                                     		poDEForm.setGlname(accountinfoobject.getGLName());
                                     		poDEForm.setGltype(accountinfoobject.getGLType());
                                     		poDEForm.setBalance(String.valueOf(odccmasterobject.getCreditLimit()));
                           			
                                     		//for PO Commission 
                                     		
                                     		if(poDEForm.getAmount().length()!=0&&poDEForm.getAmount()!=null){
                                       			if(Double.parseDouble(poDEForm.getAmount())<Double.parseDouble(poDEForm.getBalance())){
                                       			double comm=fcDelegate.poComission(poDEForm.getPo(),poDEForm.getCat(),poDEForm.getAmount());
                                       			
                                       			if(comm<=0.0){
                                       				System.out.println("comm<0.0");
                                       				req.setAttribute("closingmsg","PO Commission not defined");
                                       				req.setAttribute("sub","invalid");
                                       			}
                                       			else{
                                       				poDEForm.setComission(String.valueOf(comm));
                                       			}
                                       		}
                                       			else{
                                           			req.setAttribute("closingmsg","Amount is more than balance ");
                                       				req.setAttribute("sub","invalid");
                                           			
                                           		}
                                       		}
                                     		
                                     		
                                     		
                             			  }
                           			}
                           			
                           			  
                           		  }
                           	  }
                           	  
                             
                             }
                            // AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                             if(poDEForm.getTo().equals("bank")){
                             	poDEForm.setComission("0.0");
                             	if(poDEForm.getSubval().equals("generate")){
                             	System.out.println("generating PO for Bank");
                             		
                             		payoorderobject=new PayOrderObject();
                     			    
                     			   				        
                     			       // payoorderobject.setPOType(poDEForm.getPotype());
                     			        /*payoorderobject.setPOCustType(combo_select.getSelectedIndex());*/
                     			        payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                                         
                     			        payoorderobject.setPOPayee(null);
                                        
                                         
                     			        
                     			        payoorderobject.setPOFavour(poDEForm.getFavour());
                     								
                     			      //payoorderobject.setCustType(Integer.parseInt(poDEForm.getCat()));
                     			                payoorderobject.setPOAccType(null);
                     			                payoorderobject.setPOAccNo(0);
                     			         System.out.println("No Exception at 1852"); 
                     			       payoorderobject.setPOMakeDate(fcDelegate.getSysDate());
                     			        System.out.println("DATE="+payoorderobject.getPOMakeDate());
                     			        payoorderobject.setPOAmount(Double.parseDouble(poDEForm.getAmount()));
                     			        payoorderobject.setPOMade("F");
                     			        payoorderobject.setPOGlCode(Integer.parseInt(poDEForm.getGltyp()));
                     			       //to be given
                     			       array_glmasterobject=fcDelegate.getGL();
                   			        for(int c=0;c<array_glmasterobject.length;c++){
                   			        	if(array_glmasterobject[c].getGLCode().equals(poDEForm.getGltyp().trim())){
                   			        		payoorderobject.setPOGlName(array_glmasterobject[c].getGLName());
                   			        	}
                   			        	
                   			        }
                   			        
                   			        payoorderobject.setPOPayee(poDEForm.getBankpurName());
                   			    String ip_address = InetAddress.getLocalHost().getHostAddress();
                     			        
                     			        payoorderobject.setPOGlType(poDEForm.getGl());
                     			        payoorderobject.setPOType("X");
                     			        payoorderobject.setCommissionAmount(Double.parseDouble(poDEForm.getComission()));
                     			        payoorderobject.uv.setUserId(user);//HARDCODED
                     			        payoorderobject.uv.setUserTml(tml);
                     			        payoorderobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                     			       UserActivityMasterObject user_activ=new UserActivityMasterObject(); 
                     		        	user_activ.setUser_id(user.trim());
                     			        user_activ.setTml_no(tml.trim());
                     		         	user_activ.setIp_address(ip_address);
                     		         	user_activ.setPage_visit("Login Page");
                     		         	
                     		         	
                     		         	user_activ.setAc_no(0);
                     		         	user_activ.setCid(0);
                     		         	user_activ.setActivity_date(fcDelegate.getSysDate());
                     		         	user_activ.setActivity_time(fcDelegate.getSysTime());
                     			        if(poDEForm.getVerval().equals("verify")){
                     			        	payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                                   			 payoorderobject.uv.setVerId(user);//HARDCODED
                          		            payoorderobject.uv.setVerTml(tml);//HARDCODED
                          		            payoorderobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                          		          user_activ.setActivity("Verification of PO no:"+poDEForm.getSrlno());
                          		            System.out.println("Ver Date="+payoorderobject.getPOMakeDate());
                                   			String result=fcDelegate.verifyPayOrder(payoorderobject);
                                   			req.setAttribute("closingmsg",result);
                                   			
                                   		}
                     			        else if(poDEForm.getVerval().equals("delete")){
                    			        	 int pono=fcDelegate.setPODetails(payoorderobject,1);
                         			        poDEForm.setSrlno(String.valueOf(pono));
                         			        if(pono!=0)
                                 		req.setAttribute("closingmsg","PayOrder with serial no: "+pono+"  is  Deleted");
                         			       user_activ.setActivity("Deleting PO no:"+poDEForm.getSrlno());
                    			        	
                    			        }
                    			        else {
                    			        int pono=fcDelegate.setPODetails(payoorderobject,0);
                    			        poDEForm.setSrlno(String.valueOf(pono));
                    			        if(pono!=0)
                            		req.setAttribute("closingmsg","PayOrder with serial no: "+pono+"  Updated successfully");
                    			        user_activ.setActivity("Updating PO no:"+poDEForm.getSrlno());
                    			        }
                     			        fcDelegate.storeUserActivity(user_activ);
                             	}
                             	
                             	
                             	req.setAttribute("bank","forbank");
                             	array_glmasterobject=fcDelegate.getGL();
                             	//array_glmasterobject[0].get
                             	req.setAttribute("gl",array_glmasterobject);
                             }
                             else if(poDEForm.getTo().equals("customer")){
                             	System.out.println("Customer is selected");
                             	
                             	//-----------------To Generate PO number-------
                             	if(poDEForm.getSubval().equals("generate")){
                             	
                             		
                             		payoorderobject=new PayOrderObject();
                     			    
                     			   				        
                     			        payoorderobject.setPOType(poDEForm.getPotype());
                     			        /*payoorderobject.setPOCustType(combo_select.getSelectedIndex());*/
                     			       payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                                         
                     			        payoorderobject.setPOPayee(poDEForm.getPurName());
                                        
                                         
                     			        
                     			        payoorderobject.setPOFavour(poDEForm.getFavour());
                     								
                     			      payoorderobject.setCustType(Integer.parseInt(poDEForm.getCat()));
                     			                payoorderobject.setPOAccType(poDEForm.getAcType());
                     			                payoorderobject.setPOAccNo(Integer.parseInt(poDEForm.getAcno().trim()));
                     			          
                     			       payoorderobject.setPOMakeDate(fcDelegate.getSysDate());
                     			        System.out.println("DATE="+payoorderobject.getPOMakeDate());
                     			        payoorderobject.setPOAmount(Double.parseDouble(poDEForm.getAmount()));
                     			        payoorderobject.setPOMade("F");
                     			        payoorderobject.setPOGlCode(Integer.parseInt(poDEForm.getGlcode()));
                     			       //to be given
                     			        payoorderobject.setPOGlName(" ");
                     			        
                     			        payoorderobject.setPOGlType(poDEForm.getGltype());
                     			        payoorderobject.setPOType("X");
                     			        payoorderobject.setCommissionAmount(Double.parseDouble(poDEForm.getComission()));
                     			        payoorderobject.uv.setUserId(user);//HARDCODED
                     			        payoorderobject.uv.setUserTml(tml);
                     			        payoorderobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                     			        if(poDEForm.getVerval().equals("verify")){
                     			        	payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                                   			 payoorderobject.uv.setVerId(user);//HARDCODED
                          		            payoorderobject.uv.setVerTml(tml);//HARDCODED
                          		            payoorderobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                                              
                          		            System.out.println("Ver Date="+payoorderobject.getPOMakeDate());
                                   			String result=fcDelegate.verifyPayOrder(payoorderobject);
                                   			req.setAttribute("closingmsg",result);
                                   			
                                   		}
                     			        else if(poDEForm.getVerval().equals("delete")){
                     			        	 int pono=fcDelegate.setPODetails(payoorderobject,1);
                          			        poDEForm.setSrlno(String.valueOf(pono));
                          			        if(pono!=0)
                                  		req.setAttribute("closingmsg","PayOrder with serial no: "+pono+"  is Deleted");
                                  			
                     			        	
                     			        }
                     			        else {
                     			        int pono=fcDelegate.setPODetails(payoorderobject,0);
                     			        poDEForm.setSrlno(String.valueOf(pono));
                     			        if(pono!=0)
                             		req.setAttribute("closingmsg","PayOrder Updated with serial no: "+pono);
                             			
                     			        }
                     			        poDEForm.setSubval("");
                     			        poDEForm.setVerval("");
                     			        
                             	}
                           		
                                      	req.setAttribute("cust","forcustomer");
                             	
                             }
                             
                             //req.setAttribute("acStatus",acStatus);
                         	// array_accounttransobject=fcDelegate.sendpass(poDEForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                         	 //req.setAttribute("passvalues",array_accounttransobject);
                         	 String perPath=MenuNameReader.getScreenProperties("3018");
                           	 req.setAttribute("pageId",path);
                           	req.setAttribute("po",fcDelegate.getPoModule(0));
                           	req.setAttribute("cat",fcDelegate.subCat("0"));
                           	// req.setAttribute("cat",fcDelegate.subCat("0"));
                           	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                           	req.setAttribute("PODataEntryForm",poDEForm);
                           	return map.findForward(ResultHelp.getSuccess());
                                 
                         		
                         		
                         	}
                    	
                    	
                    	
                    	
                    	//Ends here
                    	}
                    }
                    else{
                    	System.out.println("PO Object is null");
                    	req.setAttribute("closingmsg","PayOrder Does not exist");
                    	poDEForm.setSubval("invalid");                    }
                    req.setAttribute("po",fcDelegate.getPoModule(0));
                  	req.setAttribute("cat",fcDelegate.subCat("0"));
                  	// req.setAttribute("cat",fcDelegate.subCat("0"));
                  	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    
                    }
                    else{
                    System.out.println("to=="+poDEForm.getTo());
                   // System.out.println("acNum=="+poDEForm.getAcno());
                    
                    if(poDEForm.getTo().equals("SELECT")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                    		 req.setAttribute("cat",fcDelegate.subCat("0"));
                         	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                         	req.setAttribute("PODataEntryForm",poDEForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	
                        if(poDEForm.getAcno()!=null){
                      	  if(poDEForm.getAcno()!=null&&!poDEForm.getAcno().equals("")){
                      		  if(poDEForm.getAcType().startsWith("100")){
                      		  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                      		  if(accountinfoobject!=null){
                      			  
                      			  if(accountinfoobject.getAccStatus().equals("C")){
                      				  req.setAttribute("closingmsg","CLOSED");
                      			  }else if(accountinfoobject.getAccStatus().equals("I")){
                      				req.setAttribute("closingmsg","INOPERATIVE");
                      			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                      				req.setAttribute("closingmsg","FREEZED");
                      			  }
                      			  else{
                      		poDEForm.setPurName(accountinfoobject.getAccname());
                      		poDEForm.setGlcode(accountinfoobject.getGLCode());
                      		System.out.println("glname==========="+accountinfoobject.getGLName());
                      		poDEForm.setGlname(accountinfoobject.getGLName());
                      		poDEForm.setGltype(accountinfoobject.getGLType());
                      		poDEForm.setBalance(String.valueOf(accountinfoobject.getAmount()));
                      		  
                      			  }
                      			  }
                      		  else{
                      			req.setAttribute("closingmsg","Account not Found");
                  				req.setAttribute("sub","invalid");
                      		  }
                      		if(poDEForm.getAmount().length()!=0&&poDEForm.getAmount()!=null){
                      			if(Double.parseDouble(poDEForm.getAmount())<Double.parseDouble(poDEForm.getBalance())){
                      			double comm=fcDelegate.poComission(poDEForm.getPo(),poDEForm.getCat(),poDEForm.getAmount());
                      			
                      			if(comm<=0.0){
                      				System.out.println("comm<0.0");
                      				req.setAttribute("closingmsg","PO Commission not defined");
                      				req.setAttribute("sub","invalid");
                      			}
                      			else{
                      				poDEForm.setComission(String.valueOf(comm));
                      			}
                      		}
                      			else{
                          			req.setAttribute("closingmsg","Amount is more than balance ");
                      				req.setAttribute("sub","invalid");
                          			
                          		}
                      		}
                      		
                      		/*if(poDEForm.getHidval().equals("submit")){
                      			//for submitting the values
                      			
                      		}*/
                      	  }
                      		  else if(poDEForm.getAcType().startsWith("101")){
                      			
                      			odccmasterobject=fcDelegate.odccData(poDEForm.getAcType(),poDEForm.getAcno());	  
                      			if(odccmasterobject!=null){
                      				
                        			  if(odccmasterobject.getAccountStatus().equals("C")){
                        				  req.setAttribute("closingmsg","CLOSED");
                        			  }/*else if(accountinfoobject.getAccountStatus().equals("O")){
                        				  acStatus="OPENED";
                        			  }*/else if(odccmasterobject.getAccountStatus().equals("I")){
                        				req.setAttribute("closingmsg","INOPERATIVE");
                        			  }else if(odccmasterobject.getFreezeInd().equals("T")){
                        				req.setAttribute("closingmsg","FREEZED");
                        			  }
                        			  else{
                        				  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                        			  accountObj=fcDelegate.getOdccAccount("1001001",String.valueOf(odccmasterobject.getShareAccNo()));
                            			poDEForm.setPurName(accountObj.getAccname());
                                		poDEForm.setGlcode(accountinfoobject.getGLCode());
                                		//System.out.println("glname==========="+odccmasterobject.getGLName());
                                		poDEForm.setGlname(accountinfoobject.getGLName());
                                		poDEForm.setGltype(accountinfoobject.getGLType());
                                		poDEForm.setBalance(String.valueOf(odccmasterobject.getCreditLimit()));
                      			
                                		//for PO Commission 
                                		
                                		if(poDEForm.getAmount().length()!=0&&poDEForm.getAmount()!=null){
                                  			if(Double.parseDouble(poDEForm.getAmount())<Double.parseDouble(poDEForm.getBalance())){
                                  			double comm=fcDelegate.poComission(poDEForm.getPo(),poDEForm.getCat(),poDEForm.getAmount());
                                  			
                                  			if(comm<=0.0){
                                  				System.out.println("comm<0.0");
                                  				req.setAttribute("closingmsg","PO Commission not defined");
                                  				req.setAttribute("sub","invalid");
                                  			}
                                  			else{
                                  				poDEForm.setComission(String.valueOf(comm));
                                  			}
                                  		}
                                  			else{
                                      			req.setAttribute("closingmsg","Amount is more than balance ");
                                  				req.setAttribute("sub","invalid");
                                      			
                                      		}
                                  		}
                                		
                                		
                                		
                        			  }
                      			}
                      			
                      			  
                      		  }
                      	  }
                      	  
                        
                        }
                       // AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(poDEForm.getAcType(), Integer.parseInt(poDEForm.getAcno()));
                        if(poDEForm.getTo().equals("bank")){
                        	poDEForm.setComission("0.0");
                        	if(poDEForm.getSubval().equals("generate")){
                        	System.out.println("generating PO for Bank");
                        		
                        		payoorderobject=new PayOrderObject();
                			    
                			   				        
                			       // payoorderobject.setPOType(poDEForm.getPotype());
                			        /*payoorderobject.setPOCustType(combo_select.getSelectedIndex());*/
                			        payoorderobject.setPOSerialNo(0);
                                    
                			        payoorderobject.setPOPayee(null);
                                   
                                    
                			        
                			        payoorderobject.setPOFavour(poDEForm.getFavour());
                								
                			      //payoorderobject.setCustType(Integer.parseInt(poDEForm.getCat()));
                			                payoorderobject.setPOAccType(null);
                			                payoorderobject.setPOAccNo(0);
                			         System.out.println("No Exception at 1852"); 
                			       payoorderobject.setPOMakeDate(fcDelegate.getSysDate());
                			        System.out.println("DATE="+payoorderobject.getPOMakeDate());
                			        payoorderobject.setPOAmount(Double.parseDouble(poDEForm.getAmount()));
                			        payoorderobject.setPOMade("F");
                			        payoorderobject.setPOGlCode(Integer.parseInt(poDEForm.getGltyp()));
                			       //to be given
                			        array_glmasterobject=fcDelegate.getGL();
                			        for(int c=0;c<array_glmasterobject.length;c++){
                			        	if(array_glmasterobject[c].getGLCode().equals(poDEForm.getGltyp().trim())){
                			        		payoorderobject.setPOGlName(array_glmasterobject[c].getGLName());
                			        	}
                			        	
                			        }
                			        
                			        payoorderobject.setPOPayee(poDEForm.getBankpurName());
                			        payoorderobject.setPOFavour(poDEForm.getFavour());
                			        payoorderobject.setPOGlType(poDEForm.getGl());
                			        payoorderobject.setPOType("X");
                			        payoorderobject.setCommissionAmount(Double.parseDouble(poDEForm.getComission()));
                			        payoorderobject.uv.setUserId(user);//HARDCODED
                			        payoorderobject.uv.setUserTml(tml);
                			        payoorderobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                			        if(poDEForm.getVerval().equals("verify")){
                			        	payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                              			 payoorderobject.uv.setVerId(user);//HARDCODED
                     		            payoorderobject.uv.setVerTml(tml);//HARDCODED
                     		            payoorderobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                                         
                     		            System.out.println("Ver Date="+payoorderobject.getPOMakeDate());
                              			String result=fcDelegate.verifyPayOrder(payoorderobject);
                              			req.setAttribute("closingmsg",result);
                              			
                              		}
                			        else if(poDEForm.getVerval().equals("delete")){
               			        	 int pono=fcDelegate.setPODetails(payoorderobject,1);
                    			        poDEForm.setSrlno(String.valueOf(pono));
                    			        if(pono!=0)
                            		req.setAttribute("closingmsg","PayOrder with serial no: "+pono+"Deleted");
                            			
               			        	
               			        }
               			        else {
               			        int pono=fcDelegate.setPODetails(payoorderobject,0);
               			        poDEForm.setSrlno(String.valueOf(pono));
               			        if(pono!=0)
                       		req.setAttribute("closingmsg","PayOrder created with serial no: "+pono);
                       			
               			        }
                        	}
                        	
                        	
                        	req.setAttribute("bank","forbank");
                        	array_glmasterobject=fcDelegate.getGL();
                        	//array_glmasterobject[0].get
                        	req.setAttribute("gl",array_glmasterobject);
                        }
                        else if(poDEForm.getTo().equals("customer")){
                        	System.out.println("Customer is selected");
                        	
                        	//-----------------To Generate PO number-------
                        	if(poDEForm.getSubval().equals("generate")){
                        	
                        		
                        		payoorderobject=new PayOrderObject();
                			    
                			   				        
                			        payoorderobject.setPOType(poDEForm.getPotype());
                			        /*payoorderobject.setPOCustType(combo_select.getSelectedIndex());*/
                			        payoorderobject.setPOSerialNo(0);
                                    
                			        payoorderobject.setPOPayee(poDEForm.getPurName());
                                   
                                    
                			        
                			        payoorderobject.setPOFavour(poDEForm.getFavour());
                								
                			      payoorderobject.setCustType(Integer.parseInt(poDEForm.getCat()));
                			                payoorderobject.setPOAccType(poDEForm.getAcType());
                			                payoorderobject.setPOAccNo(Integer.parseInt(poDEForm.getAcno().trim()));
                			          
                			       payoorderobject.setPOMakeDate(fcDelegate.getSysDate());
                			        System.out.println("DATE="+payoorderobject.getPOMakeDate());
                			        payoorderobject.setPOAmount(Double.parseDouble(poDEForm.getAmount()));
                			        payoorderobject.setPOMade("F");
                			        payoorderobject.setPOGlCode(Integer.parseInt(poDEForm.getGlcode()));
                			       //to be given
                			        payoorderobject.setPOGlName(" ");
                			        
                			        payoorderobject.setPOGlType(poDEForm.getGltype());
                			        payoorderobject.setPOType("X");
                			        payoorderobject.setCommissionAmount(Double.parseDouble(poDEForm.getComission()));
                			        payoorderobject.uv.setUserId(user);//HARDCODED
                			        payoorderobject.uv.setUserTml(tml);
                			        payoorderobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                			        if(poDEForm.getVerval().equals("verify")){
                			        	payoorderobject.setPOSerialNo(Integer.parseInt(poDEForm.getSrlno()));
                              			 payoorderobject.uv.setVerId(user);//HARDCODED
                     		            payoorderobject.uv.setVerTml(tml);//HARDCODED
                     		            payoorderobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                                         
                     		            System.out.println("Ver Date="+payoorderobject.getPOMakeDate());
                              			String result=fcDelegate.verifyPayOrder(payoorderobject);
                              			req.setAttribute("closingmsg",result);
                              			
                              		}
                			        else if(poDEForm.getVerval().equals("delete")){
                			        	 int pono=fcDelegate.setPODetails(payoorderobject,1);
                     			        poDEForm.setSrlno(String.valueOf(pono));
                     			        if(pono!=0)
                             		req.setAttribute("closingmsg","PayOrder with serial no: "+pono+"Deleted");
                             			
                			        	
                			        }
                			        else {
                			        int pono=fcDelegate.setPODetails(payoorderobject,0);
                			        poDEForm.setSrlno(String.valueOf(pono));
                			        if(pono!=0)
                        		req.setAttribute("closingmsg","PayOrder created with serial no: "+pono);
                        			
                			        }
                			        poDEForm.setSubval("");
                			        poDEForm.setVerval("");
                			        
                        	}
                      		
                                 	req.setAttribute("cust","forcustomer");
                        	
                        }
                        
                        //req.setAttribute("acStatus",acStatus);
                    	// array_accounttransobject=fcDelegate.sendpass(poDEForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	 //req.setAttribute("passvalues",array_accounttransobject);
                    	 String perPath=MenuNameReader.getScreenProperties("3018");
                      	 req.setAttribute("pageId",path);
                      	req.setAttribute("po",fcDelegate.getPoModule(0));
                      	req.setAttribute("cat",fcDelegate.subCat("0"));
                      	// req.setAttribute("cat",fcDelegate.subCat("0"));
                      	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                      	req.setAttribute("PODataEntryForm",poDEForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    }
                    //if to be closed here
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3018");
                    path=MenuNameReader.getScreenProperties(poDEForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("po",fcDelegate.getPoModule(0));
                    req.setAttribute("pageId",path);
                    req.setAttribute("cat",fcDelegate.subCat("0"));
                    req.setAttribute("cat",fcDelegate.subCat("0"));
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("PODataEntryForm",poDEForm);
    				System.out.println("At 438 path:"+map.getPath());
    				return map.findForward(ResultHelp.getSuccess());
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            
            path=MenuNameReader.getScreenProperties(poDEForm.getPageId());
            req.setAttribute("pageId",path);
            return map.findForward(ResultHelp.getSuccess());
        }      
        
        
        //PO data  entry ends
        
        //ODcc SAnction starts
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccSanctionMenu")){
            try{
            	ODCCSanctionForm odccform=(ODCCSanctionForm)form;
            	
            	
            	System.out.println("At 1973<<---------->"+MenuNameReader.containsKeyScreen(odccform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(odccform.getPageId()));
            	
            	System.out.println("At 1976"+odccform);
               
            	req.setAttribute("pageNum",odccform.getPageId());
               System.out.println("At 1979"+odccform.getPageId());
               logger.info(odccform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(odccform.getPageId())){
            	   System.out.println("At 1982---------->");
                   path=MenuNameReader.getScreenProperties(odccform.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("sh",fcDelegate.getSHModule(0));
                   req.setAttribute("AcType",fcDelegate.getodccModule(0));
                   System.out.println("Account and Share type are set");
                   odccform.setCreditlimit("0.00");
                   odccform.setLimit("0");
                   req.setAttribute("","acType");
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 2001"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccSanction")){
        	System.out.println(" inside /FrontCounter/OdccSanction");
        	ODCCSanctionForm odccform=(ODCCSanctionForm)form;
            String path;
            System.out.println("odccform=="+odccform);
            System.out.println("odccform=="+odccform.getPageId());
            try{
                req.setAttribute("pageNum",odccform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(odccform.getPageId())){
                    path=MenuNameReader.getScreenProperties(odccform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+odccform.getAcType());
                    System.out.println("acNum=="+odccform.getAcc_no());
                    if(odccform.getAcType().equals("SELECT")&&odccform.getAcc_no().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                    		req.setAttribute("AcType",fcDelegate.getodccModule(0));
                         	req.setAttribute("ODCCSanctionForm",odccform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	try{
                    	odccmasterobject=fcDelegate.odccData(odccform.getAcType(),odccform.getAcc_no());
                    	}
                    	catch(Exception ex){
                    		req.setAttribute("closingmsg",""+ex);
                    		ex.printStackTrace();}
                    	if(odccmasterobject!=null){
                    		 if(!(odccmasterobject.uv.getVerId()!=null))
                             {
                    			 req.setAttribute("closingmsg","Loan is not yet verified");
                    			 odccform.setShare_no(String.valueOf(odccmasterobject.getShareAccNo()));
                    			 req.setAttribute("verified","verified");
                    			 
                                
                             }
                             
                             if(odccmasterobject.isSanctionVerified() && odccmasterobject.isLoanSanctioned())
                             {
                            	 System.out.println("odccmasterobject.getShareAccNo()=============="+odccmasterobject.getShareAccNo());
                            	 odccform.setShare_no(String.valueOf(odccmasterobject.getShareAccNo()));
                    			 req.setAttribute("verified","verified");
                    			 odccform.setCreditlimit(String.valueOf(odccmasterobject.getCreditLimit()));                         
                            	 req.setAttribute("closingmsg","Sorry Sanction is already Verified!");
                            	 odccform.setCreditlimit(String.valueOf(odccmasterobject.getCreditLimit()));
                            	 System.out.println("values have been set");
                            	 odccform.setLimit(String.valueOf(odccmasterobject.getLimitUpto()));
                            	 odccform.setInterestrate(String.valueOf(odccmasterobject.getInterestRate()));
                             }
                             else{
                            	
                            	
                           		//It ends here
                             if(odccmasterobject.getAccountStatus().equalsIgnoreCase("C"))
                             {
                                 
                                 
                            	 req.setAttribute("closingmsg","Loan Account is Closed");                        
                            	 req.setAttribute("verified","verified");
                                
                             }
                             
                             else if(odccmasterobject.getFreezeInd().equals("T"))
                             {
                                
                                 
                            	 req.setAttribute("closingmsg","Loan Account is Freezed");
                            	 req.setAttribute("verified","verified");
                                 
                             }
                             
                             else if(odccmasterobject.getDefaultInd().equals("T"))
                             {
                                 
                                 
                            	 req.setAttribute("closingmsg","Loan Account is DefaultAccount");
                            	 req.setAttribute("verified","verified");
                                 
                             }
                             else{
                            	 
                            	 System.out.println("odccform.getDetails()===>Values===>"+odccform.getDetails());
                            	 odccmasterobject=fcDelegate.odccData(odccform.getAcType(),odccform.getAcc_no()); 		
                            	 accountObj=fcDelegate.getOdccAccount(odccmasterobject.getShareAccType(),String.valueOf(odccmasterobject.getAccNo()));
                       			
                            	 //it starts here
                            	 
                            	 /*loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
                    				req.setAttribute("purpose",loanpurposeobject);*/
                    				
                    				if(odccform.getDetails().equals("Application")&&odccform.getDetails().length()!=0){
                    					loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
                        				req.setAttribute("purpose",loanpurposeobject);
                      			  req.setAttribute("master",accountObj);
                      			req.setAttribute("odccmaster",odccmasterobject);
                      			  System.out.println("odccmasterobject value is ================"+odccmasterobject);
                      			  path=MenuNameReader.getScreenProperties("3028");
                      			
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			  
                      		  }
                    				
                    				else if(odccform.getDetails().equals("EmploymentDetails")&&odccform.getDetails().length()!=0){
                      			odccmasterobject=fcDelegate.odccData(odccform.getAcType(),odccform.getAcc_no()); 		
                           	 accountObj=fcDelegate.getOdccAccount(odccmasterobject.getShareAccType(),String.valueOf(odccmasterobject.getAccNo()));
                      			
                           	req.setAttribute("odccmasterobj",odccmasterobject);
                      			path=MenuNameReader.getScreenProperties("3029");
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			
                      		  }
                    				else if(odccform.getDetails().equals("Loan&ShareDetails")&&odccform.getDetails().length()!=0){
                      			path=MenuNameReader.getScreenProperties("3030");
                      			ModuleObject[] mod=fcDelegate.getLoanMod(0);
                      			
                      			req.setAttribute("loanmod",mod);
                      			System.out.println("Length of ModuleObject Array is==="+mod.length);
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			  
                      		  }
                    				else if(odccform.getDetails().equals("ReceiptDetails")&&odccform.getDetails().length()!=0){
                      			System.out.println("Receipt Details ");
                      			accountobject=fcDelegate.getOdccAccount(odccform.getAcType(),odccform.getAcc_no());
                      			odccmasterobject=fcDelegate.odccData(odccform.getAcType(),odccform.getAcc_no());
                      			System.out.println("accountobject------------->"+odccmasterobject.getRef_No());
                      			//req.setAttribute("master",accountobject);
                      			req.setAttribute("newdata","newdata");
                      			req.setAttribute("odccmaster",odccmasterobject);
                      			
                      			
                      			
                      			path=MenuNameReader.getScreenProperties("3031");
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			  
                      		  }
                    				else if(odccform.getDetails().equals("DepositDetails")&&odccform.getDetails().length()!=0){
             					AccountMasterObject amcObj=fcDelegate.getAccountMaster(Integer.parseInt(odccform.getAcc_no()),odccform.getAcType().trim());
             					CustomerMasterObject cust=fcDelegate.getCustomer(amcObj.getCid());
                    			req.setAttribute("cust",cust);
                    			
                    			
                    			path=MenuNameReader.getScreenProperties("3055");
                    			System.out.println("path is---------------"+path);
                    			req.setAttribute("flag",path); 
                    			  
                    		  }
                    				else if(odccform.getDetails().equals("SignatureInst")&&odccform.getDetails().length()!=0){
                      			System.out.println("getting Signature instruction");
                      			
                      			CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                      			req.setAttribute("cust",cust);
                      			SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(odccform.getAcc_no()),odccform.getAcType());
                      			
                      			req.setAttribute("signature",signObject);
                      			if(signObject!=null)
                      			System.out.println("SignatureInstruction Object gives values========="+signObject.length);
                      			path=MenuNameReader.getScreenProperties("3012");
                      			System.out.println("path is------Sign---------"+path);
                      			req.setAttribute("flag","/SingnatureInst.jsp"); 
                      			  
                      		  }
                      		  
                    				else if(odccform.getDetails().equals("PersonalDetails")&&odccform.getDetails().length()!=0){
                      			System.out.println("======inside personal details=====");
                      			CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
                      			req.setAttribute("cust",cust);
                      			
                      			
                      			path=MenuNameReader.getScreenProperties("3032");
                      			System.out.println("path is---------------"+path);
                      			req.setAttribute("flag",path); 
                      			  
                      		  }
                             //it ends here 
                            	 
                            	 if(!odccmasterobject.isSanctionVerified() && odccmasterobject.isLoanSanctioned())
                                 {
                                	 System.out.println("odccmasterobject.getShareAccNo()=============="+odccmasterobject.getShareAccNo());
                                	 odccform.setShare_no(String.valueOf(odccmasterobject.getShareAccNo()));
                        			                          
                                	// req.setAttribute("closingmsg","Loan is Sanctioned please verify ");
                                	// odccform.setHidval("");
                                	// req.setAttribute("verified","verified");
                                	 odccform.setCreditlimit(String.valueOf(odccmasterobject.getCreditLimit()));
                                	
                                	 odccform.setLimit(String.valueOf(odccmasterobject.getLimitUpto()));
                                	 odccform.setInterestrate(String.valueOf(odccmasterobject.getInterestRate()));
                                 }
                            	 else{
                            		 acObject=fcDelegate.getAccountObj(odccform.getAcType(),odccform.getAcc_no());
                            		 if(acObject!=null){
                            	 Double lnkshares=fcDelegate.getPercentageShare(odccform.getAcType());
                            	 Double amtsanc=Double.parseDouble(odccform.getCreditlimit());
                            	 //for validations
                            	 /*if(!obj_loanandsharedetails.setDetails((amtsanc*lnkshares/100)))
                                 {
                                 	file_logger.info("11111111111111111111111111111111");
                                     JOptionPane.showMessageDialog(null,"Amount required is less than linking shares");			
                                     combo_details.setSelectedIndex(1);
                                     txt_credit.setText("0.00");
                                     txt_credit.setSelectionStart(0);
                                     txt_credit.setSelectionEnd(1);
                                     txt_credit.requestFocus();
                                 }*/
                                 if(amtsanc>odccmasterobject.getRequiredAmount())
                                 {
                                	 req.setAttribute("closingmsg","Credit Limit is greater than requested amount");
                                	 req.setAttribute("verified","verified");
                                	 odccform.setHidval("");
                                 }	
                                 
                                 //Link Share has been Commented
                                 /*else if((amtsanc*lnkshares/100)>acObject.getAmount())
                                 {
                                 	
                                 	System.out.println("amtsanc*lnkshares/100="+(amtsanc*lnkshares/100));
                                 	
                                 	req.setAttribute("closingmsg","Amount required is less than linking shares");
                 			
                                     
                                 }*/
                                 if(odccform.getAcType().startsWith("1014")||odccform.getAcType().startsWith("1015"))
                                 {	
                                	 if(Double.parseDouble(odccform.getCreditlimit())>0.00){
                                	 odccform.setInterestrate(fcDelegate.getODCCIntRate(odccform.getAcType(),odccform.getAcc_no(),odccform.getLimit(),odccform.getCreditlimit(),String.valueOf(acObject.getSubCategory())));	
                                	 }
                                 }
                               //  else{  
                            	 System.out.println(" going to getDepositMaturity-->");
                            	 String res3=fcDelegate.getDepositMaturity(odccform.getAcType(),odccform.getAcc_no(),odccform.getLimit());
                            	 if(res3!=null){
                            		 req.setAttribute("closingmsg",res3);
                            		 odccform.setCreditlimit("0.00");
                            		 req.setAttribute("verified","verified");
                            		 odccform.setHidval("");
                            	 }
                            		 odccform.setShare_no(String.valueOf(odccmasterobject.getShareAccNo()));
                            	 
                            	//	 }
                            		 }
                             }
                            	 /*if(odccform.getAcType().startsWith("1014")||odccform.getAcType().startsWith("1015"))
                                 {	
                                	 if(Double.parseDouble(odccform.getCreditlimit())>0.00){
                                	 odccform.setInterestrate(fcDelegate.getODCCIntRate(odccform.getAcType(),odccform.getAcc_no(),odccform.getLimit(),odccform.getCreditlimit(),String.valueOf(acObject.getSubCategory())));	
                                	 }
                                 }*/
                    		if(odccform.getHidval().equals("submit")&&odccform.getInterestrate()!=null){
                    			String sub=odccform.getAcType().substring(1,4);
                    			odccmasterobject.setCreditLimit(Double.parseDouble(odccform.getCreditlimit()));
                                odccmasterobject.setLimitUpto(odccform.getLimit());
                                odccmasterobject.setInterestRate(Double.parseDouble(odccform.getInterestrate()));
                                //odccmasterobject.setPenalRate(Double.parseDouble(lbl_penal_rate.getText()));                        
                                odccmasterobject.uv.setUserId((String)req.getAttribute("uid"));
                                odccmasterobject.uv.setUserId((String)req.getAttribute("tml"));
                                odccmasterobject.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                                
                                if(odccform.getVerifyhid().equals("verify")){
                                	odccmasterobject.setTransDate(fcDelegate.getSysDate());
                                	odccmasterobject.uv.setVerId(user);
                                	odccmasterobject.uv.setVerTml(tml);
                                	odccmasterobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                                	String re=fcDelegate.verifyODCCSanction(odccmasterobject,0);
                                	req.setAttribute("closingmsg",re);
                                }
                                else{
                                String result=fcDelegate.storeodccSanction(odccmasterobject,false,false);
                                req.setAttribute("closingmsg",result);
                                }
                                }
                    		/*else{
                    			req.setAttribute("closingmsg","Detail cannot be submitted");
                    		}*/
                              
                    		
                    		
                             }
                             }
                    	}
                    	else{
                    		req.setAttribute("closingmsg","Account Not Found");
                    	}
                    	System.out.println("just before going to Sanction JSP");
                      //  req.setAttribute("acStatus",acStatus);
                    	// array_accounttransobject=fcDelegate.sendpass(odccform.getAcType(),odccform.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	// req.setAttribute("passvalues",array_accounttransobject);
                    	 String Path=MenuNameReader.getScreenProperties("3020");
                    	 loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
         				req.setAttribute("purpose",loanpurposeobject);
                      	 req.setAttribute("pageId",MenuNameReader.getScreenProperties("3020"));
                      	req.setAttribute("sh",fcDelegate.getSHModule(0));
                      	req.setAttribute("AcType",fcDelegate.getodccModule(0));
                      	req.setAttribute("ODCCSanctionForm",odccform);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3020");
                    path=MenuNameReader.getScreenProperties(odccform.getPageId());
                    loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
    				req.setAttribute("purpose",loanpurposeobject);
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
                    req.setAttribute("sh",fcDelegate.getSHModule(0));
                    req.setAttribute("AcType",fcDelegate.getodccModule(0));
               	 req.setAttribute("ODCCSanctionForm",odccform);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }      
        
        
        //odcc sanction  ends
        
       
        //ODCC RECEIPT DETAILS STARTS HERE
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccReceiptDetails")){
        	try{
        		ReceiptDetailsForm inopform=(ReceiptDetailsForm)form;
            	
            	
            	System.out.println("At 2154<<---------->"+MenuNameReader.containsKeyScreen(inopform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(inopform.getPageId()));
            	
            	System.out.println("At2157"+inopform);
               
            	req.setAttribute("pageNum",inopform.getPageId());
               System.out.println("At 2160"+inopform.getPageId());
               logger.info(inopform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(inopform.getPageId())){
            	  
            	   System.out.println("At 2164------Receipt Details---->");
            	  
                   fcDelegate=new FrontCounterDelegate();
                   if(inopform.getScrollno()!=null&&!inopform.getScrollno().equals("0")&&inopform.getScrollno().length()!=0)
                   {//AccountObject master=(AccountObject)req.getAttribute("master");
                	   accountobject=fcDelegate.receiptDetailsOnScroll("1014001",inopform.getScrollno());
                	   inopform.setAmount(String.valueOf(accountobject.getAmount()));
                	   inopform.setName(accountobject.getAccname());
                	   inopform.setDate(fcDelegate.getSysDate());
                
                   }
                    return map.findForward("receipt");
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward("receipt");
               }
        	
        }
        	catch(Exception ex){ex.printStackTrace();}
        }
        //ODCC RECEIPT DETAILS ENDS HERE
        
        
        //Inoperative starts
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InOpProcessMenu")){
            try{
            	InoperativeProcess inopform=(InoperativeProcess)form;
            	
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(inopform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(inopform.getPageId()));
            	
            	System.out.println("At 373"+inopform);
               
            	req.setAttribute("pageNum",inopform.getPageId());
               System.out.println("At 379"+inopform.getPageId());
               logger.info(inopform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(inopform.getPageId())){
            	  
            	   System.out.println("At 1323---------->");
            	   String perPath=MenuNameReader.getScreenProperties("3023");
            	   path=MenuNameReader.getScreenProperties(inopform.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InOpProcess")){
        	System.out.println("at 1374 inside Inoperative");
        	InoperativeProcess inopform=(InoperativeProcess)form;
            String path;
            System.out.println("inopform=="+inopform);
            System.out.println("inopform  pageId=="+inopform.getPageId());
            try{
                req.setAttribute("pageNum",inopform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(inopform.getPageId())){
                    path=MenuNameReader.getScreenProperties(inopform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+inopform.getAcType());
                   // System.out.println("acNum=="+passForm.getAcno());
                    if(inopform.getAcType().equals("SELECT")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                    		req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                         	req.setAttribute("InoperativeProcess",inopform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	
                        //req.setAttribute("acStatus",acStatus);
                    	
                    	 if(inopform.getHidval().equals("submit")&&inopform.getHidval()!=null){
                    		 String[] cbox=req.getParameterValues("cbox");
                    		 //String[] actyp=req.getParameterValues("actyp");
                    		 String[] acnum=req.getParameterValues("acnum");
                    		 String[] cid=req.getParameterValues("cid");
                    		 String[] name=req.getParameterValues("name");
                    		 String[] lasttrndt=req.getParameterValues("lasttrndt");
                    		 String[] acstatus=req.getParameterValues("acstatus");
                    		 String[] cl_bal=req.getParameterValues("cl_bal"); 
                    		 
                    		 for(int k=0;k<cbox.length;k++){
                    			 int x=Integer.parseInt(cbox[k]);
                    			 array_account_master[k].setAccountType(Integer.parseInt(inopform.getAcType()));
                    			 array_account_master[k].setAccNo(Integer.parseInt(acnum[x]));
                    			 array_account_master[k].setCid(Integer.parseInt(cid[x]));
                    			 array_account_master[k].setAccName(name[x]);
                    			 array_account_master[k].setLastTrnDate(lasttrndt[x]);
                    			 array_account_master[k].setAccStatus(acstatus[x]);
                    			 array_account_master[k].setCloseBal(Double.parseDouble(cl_bal[x]));
                    			                   			 
                    			 }
                    		 
                    		 int result=fcDelegate.makeInoperative(array_account_master);
                    		 if(result==1){req.setAttribute("closingmsg","Account No.'s selected are made inoperative success fully");}
                    		 else{req.setAttribute("closingmsg","Operation Failed");}
                    	 }
                    	 
                    	 else if(inopform.getHidval().equals("view")&&inopform.getHidval()!=null)
                    	 {
                    	 req.setAttribute("actypvalue",inopform.getAcType());
                    	 array_account_master=fcDelegate.getInopdata(inopform.getAcType());
                    	 if(array_account_master==null){
                    		 req.setAttribute("closingmsg","Records not Found");
                    	 }
                    	 req.setAttribute("passvalues",array_account_master);
                    	 }
                    	 String perPath=MenuNameReader.getScreenProperties("3023");
                      	 req.setAttribute("pageId",path);
                      	req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                      	req.setAttribute("InoperativeProcess",inopform);
                      	return map.findForward(ResultHelp.getSuccess());
                    	 }
                    		
                    	                   	                   
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3023");
                    path=MenuNameReader.getScreenProperties(inopform.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
                    req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
               	 req.setAttribute("InoperativeProcess",inopform);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }      
        //Inoperative  Ends
        
        
        //CCPeriodical starts
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/CCPeriodMenu")){
            try{
            	CCPeriodical  ccForm=(CCPeriodical)form;
            	
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(ccForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(ccForm.getPageId()));
            	
            	System.out.println("At 373"+ccForm);
               
            	req.setAttribute("pageNum",ccForm.getPageId());
               System.out.println("At 379"+ccForm.getPageId());
               logger.info(ccForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(ccForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties("3021");
                   fcDelegate=new FrontCounterDelegate();
                   
                   String perPath=MenuNameReader.getScreenProperties("3021");
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getCCModule(0));
                   
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/CCPeriod")){
        	System.out.println("at 1490 inside CCPeriod");
        	CCPeriodical  ccPForm=(CCPeriodical)form;
            String path;
            System.out.println("ccPForm=="+ccPForm);
            System.out.println("ccPForm=="+ccPForm.getPageId());
            try{
                req.setAttribute("pageNum",ccPForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(ccPForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(ccPForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+ccPForm.getAcType());
                    System.out.println("acNum=="+ccPForm.getAc_no());
                    if(ccPForm.getAcType().equals("SELECT")&&ccPForm.getAc_no().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getCCModule(0));
                         	req.setAttribute("CCPeriodical",ccPForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	
                    	StockDetailsObject ccpDetails=fcDelegate.getCCPeriodicDetails(ccPForm.getAcType(), ccPForm.getAc_no());
                    	if(ccpDetails!=null){
                    		ccPForm.setSanction_purp(ccpDetails.getLoanPurpose());
                    		ccPForm.setNature(ccpDetails.getNatureOfBusiness());
                    		ccPForm.setMaturity_dt(ccpDetails.getMaturutyDate());
                    		ccPForm.setSan_dt(ccpDetails.getSanctionDate());
                    		if(ccpDetails.getAccountStatus().equals("C")){
                				  acStatus="CLOSED";
                			  }else if(ccpDetails.getAccountStatus().equals("O")){
                				  acStatus="OPENED";
                			  }else if(ccpDetails.getAccountStatus().equals("I")){
                				  acStatus="INOPERATIVE";
                			  }else if(ccpDetails.getAccountStatus().equals("T")){
                				  acStatus="FREEZED";
                			  }
                			  
                    		ccPForm.setAc_status(acStatus);
                    		ccPForm.setSan_limit(String.valueOf(ccpDetails.getSanctionedLimit()));
                    		ccPForm.setPrev_stock(String.valueOf(ccpDetails.getPrevStockValue()));
                    		ccPForm.setPre_inspec(ccpDetails.getPrevInspectionDate());
                    		ccPForm.setPrv_credit(String.valueOf(ccpDetails.getPrevCreditLimit()));
                    	if(ccPForm.getHidval().equals("submitting")){
                    		String[] str=fcDelegate.onStockvalue(ccPForm.getAcType(),ccPForm.getStock_value(),ccPForm.getAc_no());
                    		if(str!=null){
                    			if(str.length==2){
                    				ccPForm.setCredit_limit(str[0]);
                    				ccPForm.setNext_inspec(str[1]);
                    				
                    			}
                    			
                    			
                    		}
                    		
                    	}
                    	if(ccPForm.getSubval().equals("subvalues")){
                    		ccpDetails.setStockValue(Double.parseDouble(ccPForm.getStock_value()));
                    		ccpDetails.setInspDate(fcDelegate.getSysDate());
                    		ccpDetails.setNextInspDate(ccPForm.getNext_inspec());
                    		ccpDetails.setInspCreditLimit(Double.parseDouble(ccPForm.getCredit_limit()));
                    		//uid has to be modified
                    		ccpDetails.uv.setUserId(user);
                    		ccpDetails.uv.setUserTml(tml);
                    		ccpDetails.uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		
                    		String st1=fcDelegate.onSubmitting(ccpDetails);
                    		req.setAttribute("closingmsg",st1);
                    		
                    	}
                    	
                    	}
                    	else{req.setAttribute("closingmsg","Account Not Found");}
                    	
                    	
                        //req.setAttribute("acStatus",acStatus);
                    	// array_accounttransobject=fcDelegate.sendpass(passForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	 //req.setAttribute("passvalues",array_accounttransobject);
                    	 String perPath=MenuNameReader.getScreenProperties("3021");
                    	 path=MenuNameReader.getScreenProperties("3021");
                      	 req.setAttribute("pageId",path);
                      	 req.setAttribute("AcType",fcDelegate.getCCModule(0));
                      	req.setAttribute("CCPeriodical",ccPForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    path=MenuNameReader.getScreenProperties(ccPForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getCCModule(0));
               	 req.setAttribute("CCPeriodical",ccPForm);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
        
        //CCPeriodical Ends
        
        
     //PO Instruction Starts here
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoInsMenu")){
            try{
            	POInstructionForm poInsForm=(POInstructionForm)form;
            	poInsForm.setSysdate(FrontCounterDelegate.getSysDate());
            	
            	System.out.println("At 2954<<---------->"+MenuNameReader.containsKeyScreen(poInsForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(poInsForm.getPageId()));
            	
            	System.out.println("At 2957"+poInsForm);
               
            	req.setAttribute("pageNum",poInsForm.getPageId());
               System.out.println("At 2960"+poInsForm.getPageId());
               logger.info(poInsForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(poInsForm.getPageId())){
            	   System.out.println("At 2963---------->");
                   path=MenuNameReader.getScreenProperties(poInsForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoIns")){
        	System.out.println("------------- inside /FrontCounter/PoIns");
        	POInstructionForm poInsForm=(POInstructionForm)form;
        	poInsForm.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("sbform=="+poInsForm);
            System.out.println("sbform=="+poInsForm.getPageId());
            try{
                req.setAttribute("pageNum",poInsForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(poInsForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(poInsForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+poInsForm.getChno());
                    //System.out.println("acNum=="+poInsForm.getAcno());
                    if(poInsForm.getChno().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                         	req.setAttribute("POInstructionForm",poInsForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	
                    	
                    	payorderobj=fcDelegate.poInstruction(poInsForm.getChno());
                    	if(payorderobj!=null){
                    	poInsForm.setAmount(String.valueOf(payorderobj.getPOAmount()));
                    	poInsForm.setCancel(String.valueOf(payorderobj.getPOCancel()));
                    	poInsForm.setDate(String.valueOf(payorderobj.getPODate()));
                    	poInsForm.setStop(String.valueOf(payorderobj.getPOStop()));
                    	poInsForm.setValidity(String.valueOf(payorderobj.getPOValidUpTo()));
                    	poInsForm.setPayee(String.valueOf(payorderobj.getPOPayee()));
                    	poInsForm.setPon(String.valueOf(payorderobj.getPOAccNo()));
                    	}
                    	else{
                    		poInsForm.setAmount("");
                        	
                        	poInsForm.setDate("");
                        	
                        	poInsForm.setValidity("");
                        	poInsForm.setPayee("");
                        	poInsForm.setPon("");
                    		req.setAttribute("closingmsg","Cheque no not found");
                    	}
                    	if(poInsForm.getSubval().equals("submit")){
                    		if(poInsForm.getPoinst().equals("1")){
                    		String result=fcDelegate.poSubmit(Integer.parseInt(poInsForm.getPoinst()),poInsForm.getChno(),poInsForm.getValidity(),poInsForm.getStop());
                    		req.setAttribute("result",result);
                    		}
                    		else if(poInsForm.getPoinst().equals("2")){
                    			String result=fcDelegate.poSubmit(Integer.parseInt(poInsForm.getPoinst()),poInsForm.getChno(),poInsForm.getValidity(),poInsForm.getCancel());
                        		req.setAttribute("result",result);	
                    			
                    		}
                    		else if(poInsForm.getPoinst().equals("3")){
                    			//validity date should not be todays date
                    			String result=fcDelegate.poSubmit(Integer.parseInt(poInsForm.getPoinst()),poInsForm.getChno(),poInsForm.getValidity(),poInsForm.getStop());
                        		req.setAttribute("result",result);	
                    			
                    		}
                    		
                    	}
                      //  req.setAttribute("acStatus",acStatus);
                    //	 array_accounttransobject=fcDelegate.sendpass(passForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	 //req.setAttribute("passvalues",array_accounttransobject);
                    	 String perPath=MenuNameReader.getScreenProperties("3014");
                      	 req.setAttribute("pageId",path);
                      	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                      	req.setAttribute("POInstructionForm",poInsForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    path=MenuNameReader.getScreenProperties(poInsForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("POInstructionForm",poInsForm);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//PO Instruction Ends Here       
        
        
        
        //PO Consolidation Starts here
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoCoMenu")){
            try{
            	ConsolPOForm copoForm=(ConsolPOForm)form;
            	
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(copoForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(copoForm.getPageId()));
            	
            	System.out.println("At 373"+copoForm);
               
            	req.setAttribute("pageNum",copoForm.getPageId());
               System.out.println("At 379"+copoForm.getPageId());
               logger.info(copoForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(copoForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(copoForm.getPageId());
                   copoForm.setPayorder("0");
                   fcDelegate=new FrontCounterDelegate();
                   payorderobject_view=fcDelegate.poConsol();
                   req.setAttribute("poconsol",payorderobject_view);
                   System.out.println("value for poconsole is set");
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PoCo")){
        	System.out.println("at 2677 inside /FrontCounter/PoCo");
        	ConsolPOForm copoForm=(ConsolPOForm)form;
            String path;
            System.out.println("sbform=="+copoForm);
            System.out.println("sbform=="+copoForm.getPageId());
            try{
                req.setAttribute("pageNum",copoForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(copoForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(copoForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                   // System.out.println("Chqno=="+copoForm.getChequeno());
                   // System.out.println("acNum=="+copoForm.);
                    if(copoForm.getChequeno()==null||copoForm.getPayeename()==null||copoForm.getPayorder()==null){
                    	
                    		System.out.println("Chq no and payee name are null");
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                         	req.setAttribute("ConsolPOForm",copoForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	String str=fcDelegate.checkPOChequeno(copoForm.getChequeno());
                    	if(str!=null){
                    		
                    		req.setAttribute("closingmsg",str);
                    		req.setAttribute("disablesub","disable");
                    	}
                    	System.out.println("values are filled properly");
                    	String cbox[]=req.getParameterValues("cbox");
                    	String potype[]=req.getParameterValues("potype");
                    	String date[]=req.getParameterValues("date");
                    	String actypno[]=req.getParameterValues("actypno");
                    	String gltype[]=req.getParameterValues("gltype");
                    	String glcode[]=req.getParameterValues("glcode");
                    	String purname[]=req.getParameterValues("purname");
                    	String favour[]=req.getParameterValues("favour");
                    	String amount[]=req.getParameterValues("amount");
                    	String sno[]=req.getParameterValues("sno");
                    	
                    	if(cbox!=null){
                    		System.out.println("cbox=====================> "+cbox.length);
                    		payordersubmit=new PayOrderObject[cbox.length];
                    			for(int j=0;j<cbox.length;j++){
                    		int x=Integer.parseInt(cbox[j].trim());
                    		System.out.println("value of x====> "+x);
                    		payordersubmit[j]=new PayOrderObject();
                    		payordersubmit[j].setPOSerialNo(Integer.parseInt(sno[x]));
			                payordersubmit[j].setPOType(String.valueOf(potype[x]));
			                payordersubmit[j].setPOChqNo(Integer.parseInt(copoForm.getChequeno()));
			                payordersubmit[j].uv.setUserId((String)session.getAttribute("UserName"));
			                payordersubmit[j].uv.setUserTml((String)session.getAttribute("UserTml"));
			                payordersubmit[j].setPOPayee(copoForm.getPayeename());
			                payordersubmit[j].setPOFavour(favour[x]);
			                payordersubmit[j].setPOAmount(Double.parseDouble(amount[x]));
			                payordersubmit[j].setPayOrderNo(Integer.parseInt(copoForm.getPayorder()));
			                payordersubmit[j].uv.setUserDate(fcDelegate.getSysDate()+" "+fcDelegate.getSysTime());
		                					
                    	}
                    	if(copoForm.getHidval().equals("submit")){
                    	if(copoForm.getPayorder().equals("0")){
                    	String result=fcDelegate.storePOConsolidation(payordersubmit,0);
                    	req.setAttribute("result",result);
                    	}
                    	else{
                    		String result=fcDelegate.storePOConsolidation(payordersubmit,1);
                    		req.setAttribute("result",result);
                    	}
                    	}
                    	}
                    	else
                    	{
                    		req.setAttribute("result","select payorder's to be consolidated");	
                    	}
                       // req.setAttribute("acStatus",acStatus);
                    	 //array_accounttransobject=fcDelegate.sendpass(copoForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	 //req.setAttribute("passvalues",array_accounttransobject);
                    	 String perPath=MenuNameReader.getScreenProperties("3024");
                      	 req.setAttribute("pageId",path);
                      	payorderobject_view=fcDelegate.poConsol();
                        req.setAttribute("poconsol",payorderobject_view);
                        System.out.println("value for poconsole is set");
                      	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                      	req.setAttribute("ConsolPOForm",copoForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3024");
                    path=MenuNameReader.getScreenProperties(copoForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("ConsolPOForm",copoForm);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//PO Consolidation Ends Here     
        
        //SB ca Admin Starts here
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SbCaAdminMenu")){
            try{
            	SbCaAdminForm  adminform =(SbCaAdminForm)form;
            	
            	adminform.setSysdate(FrontCounterDelegate.getSysDate());
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(adminform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(adminform.getPageId()));
            	
            	System.out.println("At 373"+adminform);
               
            	req.setAttribute("pageNum",adminform.getPageId());
               System.out.println("At 379"+adminform.getPageId());
               logger.info(adminform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(adminform.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(adminform.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   req.setAttribute("","acType");
                   adminform.setDe_user(user);
                   adminform.setDe_tml(tml);
                   req.setAttribute("subcat",fcDelegate.subCat("0"));
                   req.setAttribute("SbCaAdminForm",adminform);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SbCaAdmin")){
        	System.out.println("at 931 inside /FrontCounter/SbCaAdmin");
        	SbCaAdminForm  adminform =(SbCaAdminForm)form;
        	adminform.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("sbform=="+adminform);
            System.out.println("Page ID is ====="+adminform.getPageId());
            System.out.println("SELECTED VALUE IS =="+adminform.getSelectedval());
            try{
                req.setAttribute("pageNum",adminform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(adminform.getPageId())){
                    path=MenuNameReader.getScreenProperties(adminform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("value selected is ============="+req.getParameter("selectedval"));
                    System.out.println("Table value is =="+adminform.getSelectedval());
                   // System.out.println("acNum=="+adminform.getAcno());
                    if(adminform.getSelectedval().equals("SELECT")){
                    	
                    	path=MenuNameReader.getScreenProperties(adminform.getPageId());
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                    		req.setAttribute("AcType",fcDelegate.getSBCA(0));
                         	req.setAttribute("SbCaAdminForm",adminform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	if(adminform.getSelectedval().equals("TokenNumbers")){
                    		req.setAttribute("letupdate","letupdate");
                    		
                    	}
                    	admin_object=fcDelegate.getColnames(adminform.getSelectedval());
                    	System.out.println("admin_object length is  ==="+admin_object.length);
                    	for(int i=0;i<admin_object.length;i++){
                    		
                    		System.out.println("Array value is "+admin_object[i]);
                    		String[] strArry=admin_object[i].getCol_names();
                    		System.out.println("Length of column name is "+strArry.length);
                    		for(int p=0;p<strArry.length;p++){
                    			System.out.println("Name of columns are "+strArry[p]);
                    		}
                    		
                    	//System.out.println("admin_object[0].getAc_type();====="+admin_object[i].getAc_type());	
                    	}
                    	System.out.println("Getting the data is11111111111111111111111111 ");
                    	Object[][] admobj2=admin_object[0].getData();
                    	System.out.println("admobj2-------------"+admobj2);
                    	//String[][] str=(String[][])admobj2;
                    	System.out.println("4254===length of data is =admobj2=="+admobj2.length);
                    	for(int l=0;l<admobj2.length;l++){
                    		//System.out.println("value of data ======****==="+admobj2[0][l].toString());
                    		
                    		
                    	}
                    	
                    	
                 	if(adminform.getHidval()!=null&&adminform.getHidval().equals("toadd")){
                 		adminform.setDe_user(user);
                        adminform.setDe_tml(tml);
                    	if(adminform.getSelectedval().equals("SavingsIntRate")){
                    		req.setAttribute("settrue","savingInt");
                    		
                    	}
                    	else if(adminform.getSelectedval().equals("SavingsCatRate")){
                    		req.setAttribute("subcat",fcDelegate.subCat("0"));
                    		req.setAttribute("settrue","savingCat");
                    		
                    	}
                    	else if(adminform.getSelectedval().equals("TokenNumbers")){
                    		req.setAttribute("settrue","token");
                    		
                    	}
                    	}
                 	if(adminform.getHidval().equals("toupdate")){
                		
                    	if(adminform.getSelectedval().equals("SavingsIntRate")){
                    		admin_object[0].setAc_type(adminform.getSavacType());
                    		admin_object[0].setDate(fcDelegate.getSysDate());
                    		admin_object[0].setInt_rate(Double.parseDouble(adminform.getInt_rate()));
                    		admin_object[0].setDe_date(fcDelegate.getSysDate());
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		
                    		if(adminform.getInsertval().equals("toinsert")){
                    		String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    		
                    	}
                    	else if(adminform.getSelectedval().equals("SavingsCatRate")){


                    		admin_object[0].setAc_type(adminform.getSavacType());
                    		admin_object[0].setFr_date(fcDelegate.getSysDate());
                    		admin_object[0].setExtra_rate(Double.parseDouble(adminform.getInt_rate()));
                    		admin_object[0].setDe_date(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		admin_object[0].setCat(Integer.parseInt(adminform.getSubcat()));
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		admin_object[0].setDays_fr(Integer.parseInt(adminform.getDays_fr()));
                    		admin_object[0].setDays_to(Integer.parseInt(adminform.getDays_to()));
                    		admin_object[0].setTo_date(adminform.getDate_to());
                    		if(adminform.getInsertval().equals("toinsert")){
                    		String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    	}
                    	else if(adminform.getSelectedval().equals("TokenNumbers")){


                    		admin_object[0].setTokennum(Integer.parseInt(adminform.getTokenno()));
                    		admin_object[0].setTo_date(adminform.getTdate());
                    		admin_object[0].setDate(fcDelegate.getSysDate());
                    		admin_object[0].setDe_date(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                  
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		
                    		if(adminform.getInsertval().equals("toinsert")){
                    		String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,adminform.getSelectedval(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    	}
                    	}
                    	 req.setAttribute("sbcaAdminvalues",admin_object);
                    	 String perPath=MenuNameReader.getScreenProperties("3025");
                      	 req.setAttribute("pageId",path);
                      	 req.setAttribute("AcType",fcDelegate.getSBCA(0));
                      	req.setAttribute("SbCaAdminForm",adminform);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    path=MenuNameReader.getScreenProperties(adminform.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
                    req.setAttribute("subcat",fcDelegate.subCat("0"));
                    req.setAttribute("AcType",fcDelegate.getSBCA(0));
               	 req.setAttribute("SbCaAdminForm",adminform);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//SB Ca Amin ends Ends Here       
        
        
        //ODCCAdmin Starts here
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdCcAdminMenu")){
            try{
            	OdCcActionForm  odccadminform =(OdCcActionForm)form;
            	odccadminform.setSysdate(FrontCounterDelegate.getSysDate());
            	System.out.println("At 371<<--------->"+MenuNameReader.containsKeyScreen(odccadminform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(odccadminform.getPageId()));
            	
            	System.out.println("At 373"+odccadminform);
               
            	req.setAttribute("pageNum",odccadminform.getPageId());
               System.out.println("At 379"+odccadminform.getPageId());
               logger.info(odccadminform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(odccadminform.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(odccadminform.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   req.setAttribute("","acType");
                   odccadminform.setDe_user(user);
                   odccadminform.setDe_tml(tml);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdCcAdmin")){
        	System.out.println("at 2061 inside ODCCADMIN");
        	OdCcActionForm  odccadminform =(OdCcActionForm)form;
        	odccadminform.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("odccadminform=="+odccadminform);
            System.out.println("PageId=="+odccadminform.getPageId());
            try{
                req.setAttribute("pageNum",odccadminform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(odccadminform.getPageId())){
                    path=MenuNameReader.getScreenProperties(odccadminform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("selected value is getODcc_table=="+odccadminform.getOdcc_table());
                    //System.out.println("acNum=="+odccadminform.getAcno());
                    if(odccadminform.getOdcc_table().equals("SELECT")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getodccModule(0));
                         	req.setAttribute("OdCcActionForm",odccadminform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	//copy of sbca admin
                    	
                    	admin_object=fcDelegate.getColnames(odccadminform.getOdcc_table());
                    	System.out.println("admin_object length is  ==="+admin_object.length);
                    	for(int i=0;i<admin_object.length;i++){
                    		
                    		System.out.println("Array value is "+admin_object[i]);
                    		String[] strArry=admin_object[i].getCol_names();
                    		System.out.println("Length of column name is "+strArry.length);
                    		for(int p=0;p<strArry.length;p++){
                    			System.out.println("Name of columns are "+strArry[p]);
                    		}
                    		
                    	//System.out.println("admin_object[0].getAc_type();====="+admin_object[i].getAc_type());	
                    	}
                    	System.out.println("Getting the data is11111111111111111111111111 ");
                    	Object[][] admobj2=admin_object[0].getData();
                    	System.out.println("admobj2-------------"+admobj2);
                    	//String[][] str=(String[][])admobj2;
                    	System.out.println("4460 ==length of data is =admobj2=="+admobj2.length);
                    	System.out.println("Page ID is----------------- "+3026);
                    	/*for(int l=0;l<admobj2.length;l++){
                    		//System.out.println("value of data ======****==="+admobj2[0][l].toString());
                    		
                    		
                    	}*/
                    	
                    	
                 	if(odccadminform.getHidval().equals("toadd")){
                 		odccadminform.setDe_user(user);
                 		odccadminform.setDe_tml(tml);
                    	if(odccadminform.getOdcc_table().equals("DirectorRelation")){
                    		req.setAttribute("settrue","dirRel");
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("SecurityDetails")){
                    		req.setAttribute("settrue","securitydet");
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("Relations")){
                    		req.setAttribute("settrue","Relation");
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("DirectorMaster")){
                    		req.setAttribute("settrue","dirmaster");
                    		
                    	}
                    	}
                 	if(odccadminform.getOdcc_table().equals("DirectorMaster")){
                 		if(odccadminform.getDircid()!=null&&odccadminform.getDircid().length()>0){
                 			try{
                			CustomerMasterObject cust=fcDelegate.getCustomer(Integer.parseInt(odccadminform.getDircid()));
                			System.out.println("cust---4157>"+cust.getName());
                			odccadminform.setDirname(cust.getName());
                 			}
                 			catch(Exception ex){
                 				req.setAttribute("closingmsg","Customer Not Found");
                 				ex.printStackTrace();}
                		}
                 	}
                 	System.out.println("Before toupdate-------------");
                 	if(odccadminform.getHidval().equals("toupdate")){
                		System.out.println("**********INSIDE TO UPDATE(**********");
                    	if(odccadminform.getOdcc_table().equals("DirectorRelation")){
                    		admin_object[0].setRelation(odccadminform.getRel_type());
                    		//admin_object[0].set
                    		admin_object[0].setDe_date(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		
                    		if(odccadminform.getInsertval().equals("toinsert")){
                    		String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("SecurityDetails")){

                    		admin_object[0].setType_of_sec(odccadminform.getSectype());
                    		admin_object[0].setAc_type(odccadminform.getSecActype());
                    		admin_object[0].setDate(fcDelegate.getSysDate());
                    		admin_object[0].setTo_date(odccadminform.getTdate());
                    		admin_object[0].setPercentage(Double.parseDouble(odccadminform.getPerc_ln_availed()));
                    		admin_object[0].setDe_date(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		
                    		if(odccadminform.getInsertval().equals("toinsert")){
                    		String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("Relations")){


                    		
                    		admin_object[0].setRelation(odccadminform.getType_rel());
                    		
                    		admin_object[0].setDe_date(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                  
                    		admin_object[0].setDe_tml(tml);
                    		admin_object[0].setDe_user(user);
                    		
                    		if(odccadminform.getInsertval().equals("toinsert")){
                    			System.out.println("inserting new relation");
                    		String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		
                    	}
                    	else if(odccadminform.getOdcc_table().equals("DirectorMaster")){
                    		//admin_object[0]=new AdminObject();
                    		admin_object[0].setCid(Integer.parseInt(odccadminform.getDircid()));
                    		System.out.println("inside director Master");
                    		if(odccadminform.getDircid()!=null&&odccadminform.getDircid().length()>0){
                    			CustomerMasterObject cust=fcDelegate.getCustomer(Integer.parseInt(odccadminform.getDircid()));
                    			System.out.println("cust---4157>"+cust.getName());
                    			odccadminform.setDirname(cust.getName());
                    		}
                    		admin_object[0].setDate(fcDelegate.getSysDate());
                    		admin_object[0].setTo_date(odccadminform.getTdate());
                    		
                    		if(odccadminform.getInsertval().equals("toinsert")){
                    			System.out.println("inside action to insert");
                    		String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),0);
                    		req.setAttribute("closingmsg",str);
                    		}else {
                    			String str=fcDelegate.insertData(admin_object,odccadminform.getOdcc_table(),1);
                    			req.setAttribute("closingmsg",str);
                    		}
                    		//req.setAttribute("sbcaAdminvalues",admin_object);
                 	}
                    	
                 	}
                    	
                    	System.out.println("Before end toupdate-------------");
                    	
                    	
                    	req.setAttribute("sbcaAdminvalues",admin_object);
                    	path=MenuNameReader.getScreenProperties("3026");
                    	System.out.println("AT the End odccadminform.getPageId() "+odccadminform.getPageId());
                    	 String perPath=MenuNameReader.getScreenProperties("3026");
                      	 req.setAttribute("pageId",path);
                      	 req.setAttribute("AcType",fcDelegate.getodccModule(0));
                      	req.setAttribute("OdCcActionForm",odccadminform);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	
                    
                }}
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3026");
                    path=MenuNameReader.getScreenProperties(odccadminform.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("OdCcActionForm",odccadminform);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//ODCC Amin ends Ends Here     
        
        
      //PayOrder Admin starts Here   
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PayOrdAdminMenu")){
            try{
            	POActionForm  poform =(POActionForm)form;
            	poform.setSysdate(FrontCounterDelegate.getSysDate());
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(poform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(poform.getPageId()));
            	
            	System.out.println("At 373"+poform);
               
            	req.setAttribute("pageNum",poform.getPageId());
               System.out.println("At 379"+poform.getPageId());
               logger.info(poform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(poform.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(poform.getPageId());
                  fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getPoModule(0));
                  // req.setAttribute("AcType",fcDelegate.getodccModule(0));
                  
                   req.setAttribute("","acType");
                   req.setAttribute("cat",fcDelegate.accCat());
                   //req.setAttribute("subcat",fcDelegate.accCat());
                 //  req.setAttribute("","acType");
                   poform.setDeuser(user);
                   poform.setDetml(tml);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PayOrdAdmin")){
        	System.out.println("at 931 inside PO Admin");
        	POActionForm  poform =(POActionForm)form;
        	poform.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            try{
                req.setAttribute("pageNum",poform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(poform.getPageId())){
                    path=MenuNameReader.getScreenProperties(poform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+poform.getAcType());
                  //  System.out.println("acNum=="+poform.getAcno());
                    if(poform.getAcType().equals("SELECT")&&poform.getCat().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Select the fields");
                    		
                    		
                    		path=MenuNameReader.getScreenProperties(poform.getPageId());
                            fcDelegate=new FrontCounterDelegate();
                             req.setAttribute("pageId",path);
                             req.setAttribute("AcType",fcDelegate.getPoModule(0));
                         	req.setAttribute("POActionForm",poform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	if(poform.getViewval().equals("view")){
                    	
                    		payorderobject=fcDelegate.getPOdetails(poform.getAcType(),poform.getCat(),poform.getSubcat());
                    		System.out.println("At 4313 Checking poobject is null");
                    		if(payorderobject!=null)
                    			req.setAttribute("podetails",payorderobject);
                    		else
                    			req.setAttribute("closingmsg","There are no records click on add to add new details");
                    		
                    	}
                    	if(poform.getAddval()!=null&&poform.getAddval().equals("add")){
                    		System.out.println("Inside ADD");
                    		poform.setDeuser(user);
                            poform.setDetml(tml);
                    		req.setAttribute("showtab","showtable");
                    		req.setAttribute("closingmsg",null);
                    		
                    	}
                    	
                    	if(poform.getSubmiting()!=null&&poform.getSubmiting().equals("1")||poform.getSubmiting().equals("2")){
                    		System.out.println("Just before going to submit");
                    		payorderobject=new PayOrderObject[1];
                    		payorderobject[0]=new PayOrderObject();
                    		payorderobject[0].setPOFromDate(poform.getFdate());
                    		System.out.println("After setting the from Date");
                    		payorderobject[0].setPOToDate(poform.getTdate());
                    		payorderobject[0].setPOFromAmt(Double.parseDouble(poform.getFamount()));
                    		payorderobject[0].setPOToAmt(Double.parseDouble(poform.getTamount()));
                    		payorderobject[0].setPOCommRate(Double.parseDouble(poform.getComrate()));
                    		payorderobject[0].setPOSubCat(Integer.parseInt(poform.getSubcat()));
                    		payorderobject[0].setCustType(Integer.parseInt(poform.getCat()));
                    		payorderobject[0].setPOType(poform.getAcType());
                    		payorderobject[0].setPOCommType(poform.getComtype());
                    		payorderobject[0].setPOMinCommAmt(Double.parseDouble(poform.getMincomamt()));
                    		payorderobject[0].setPOExtraCommRate(Double.parseDouble(poform.getExtcomrate()));
                    		payorderobject[0].uv.setUserTml(tml);
                    		payorderobject[0].uv.setUserId(user);
                    		payorderobject[0].uv.setUserDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		
                    		String POstore=fcDelegate.storePODetails(Integer.parseInt(poform.getSubmiting()), payorderobject);
                    		req.setAttribute("closingmsg",POstore);
                    		
                    	}
                        //req.setAttribute("acStatus",acStatus);
                    	 //array_accounttransobject=fcDelegate.sendpass(passForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate(),passForm.getSqno());
                    	// req.setAttribute("passvalues",array_accounttransobject);
                    	System.out.println("+poform.getCat()=============="+poform.getCat());
                    	req.setAttribute("subcat",fcDelegate.subCat(req.getParameter("cat")));
                    	System.out.println("After setting Attribute");
                    	 String perPath=MenuNameReader.getScreenProperties("3027");
                      	  req.setAttribute("pageId",path);
                         req.setAttribute("AcType",fcDelegate.getPoModule(0));
                        // req.setAttribute("AcType",fcDelegate.getodccModule(0));
                        
                         req.setAttribute("","acType");
                         req.setAttribute("cat",fcDelegate.accCat());
                      	
                      	req.setAttribute("POActionForm",poform);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    
                }
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    path=MenuNameReader.getScreenProperties(poform.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("POActionForm",poform);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//PayOrder Admin ends Ends Here  
        
        //Interest Posting Starts Here    
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InterestPostingMenu")){
            try{
            	System.out.println("inside InterestPostingMenu");
            	FCIntPostForm  intform =(FCIntPostForm)form;
            	
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(intform.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(intform.getPageId()));
            	
            	System.out.println("At 373"+intform);
               
            	req.setAttribute("pageNum",intform.getPageId());
               System.out.println("At 379"+intform.getPageId());
               logger.info(intform.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(intform.getPageId())){
            	   System.out.println("At 3970--------->");
                   path=MenuNameReader.getScreenProperties(intform.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                   req.setAttribute("odccintdetails",fcDelegate.ODCCInterestDetails());
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/IntPost")){
        	System.out.println("at 2196 inside /FrontCounter/IntPost");
        	FCIntPostForm  intform =(FCIntPostForm)form;
            String path;
            System.out.println("intform=="+intform);
            System.out.println("intform=="+intform.getPageId());
            try{
                req.setAttribute("pageNum",intform.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(intform.getPageId())){
                	req.setAttribute("odccintdetails",fcDelegate.ODCCInterestDetails());
                    path=MenuNameReader.getScreenProperties(intform.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+intform.getAcType());
                    System.out.println("acNum=="+intform.getAcno());
                    System.out.println("Rad=="+intform.getRad());
                    /*if(intform.getRad().equals("selected")){*/
                    if(intform.getAcType().equals("SELECT")&&intform.getAcno()!=null&&intform.getAcno().length()!=0){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                         	req.setAttribute("FCIntPostForm",intform);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	if(intform.getRad().equals("selected")){
                    		System.out.println("for selected account");
                    	String acStatus="";
                        if(!intform.getAcType().equalsIgnoreCase("SELECT")){
                      	  if(intform.getAcno()!=null&&!intform.getAcno().equals("")){
                      		  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(intform.getAcType(), Integer.parseInt(intform.getAcno()));
                      		  if(accountinfoobject!=null){
                      			  
                      			  if(accountinfoobject.getAccStatus().equals("C")){
                      				 req.setAttribute("closingmsg","CLOSED");
                      			  }/*else if(accountinfoobject.getAccStatus().equals("O")){
                      				 req.setAttribute("closingmsg","OPENED");
                      			  }*/else if(accountinfoobject.getAccStatus().equals("I")){
                      				 req.setAttribute("closingmsg","INOPERATIVE");
                      			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                      				 req.setAttribute("closingmsg","FREEZED");
                      			  }
                      			  System.out.println("Calling Delegate");
                      			String result=fcDelegate.intPost(intform.getAcType(),intform.getAcno(),user,tml);  
                      		  System.out.println("in Action result"+result);
                      		req.setAttribute("closingmsg",result);
                      		  
                      		  
                      		  }
                      		  else
                      			  {req.setAttribute("closingmsg","Account not Found");}
                      	  
                      	  
                      	  
                      	  }}
                        
                    	 //array_accounttransobject=fcDelegate.sendpass(intform.getAcType(),intform.getAcno(),intform.getFdate(),intform.getTdate(),passForm.getSqno());
                    	 req.setAttribute("passvalues",array_accounttransobject);
                    	 path=MenuNameReader.getScreenProperties(intform.getPageId());
                         fcDelegate=new FrontCounterDelegate();
                         req.setAttribute("pageId",path);
                         req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                         return map.findForward(ResultHelp.getSuccess());
                    	} 
                    	else if(intform.getRad().equals("all")){
                        	String result=fcDelegate.intPost(intform.getAcType(),"0",user,tml);
                    		System.out.println("RESULT======4286========="+result);
                    		req.setAttribute("closingmsg",result);
                    		 path=MenuNameReader.getScreenProperties(intform.getPageId());
                             fcDelegate=new FrontCounterDelegate();
                             req.setAttribute("pageId",path);
                             req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                             return map.findForward(ResultHelp.getSuccess());
                        }
                    }
                    		
                    	
                    
                    
                }
                    	else{
                    		
                    String perPath=MenuNameReader.getScreenProperties("3017");
                    path=MenuNameReader.getScreenProperties(intform.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    path=MenuNameReader.getScreenProperties(intform.getPageId());
                    fcDelegate=new FrontCounterDelegate();
                    req.setAttribute("pageId",path);
                    req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                    return map.findForward(ResultHelp.getSuccess());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }    
//Interest Posting ends Ends Here    
        
        
        
        //Passbook Strats here
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PassbookMenu")){
            try{
            	FCPassbookForm passForm=(FCPassbookForm)form;
            	passForm.setSysdate(FrontCounterDelegate.getSysDate());
            	req.setAttribute("pageNum",passForm.getPageId());
               System.out.println("At 379"+passForm.getPageId());
               logger.info(passForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(passForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(passForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   String dates=fcDelegate.getSysDate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                   passForm.setFdate(dates.trim());
                   passForm.setTdate(dates.trim());
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/Passbook")){
        	System.out.println("at 931 inside Passbook");
        	FCPassbookForm passForm=(FCPassbookForm)form;
        	passForm.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("sbform=="+passForm);
            System.out.println("sbform=="+passForm.getPageId());
            try{
                req.setAttribute("pageNum",passForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(passForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(passForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+passForm.getAcType());
                    System.out.println("acNum=="+passForm.getAcno());
                    if(passForm.getAcType().equals("SELECT")&&passForm.getAcno().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		
                    		req.setAttribute("pageId",path);
                            req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                         	req.setAttribute("PassbookForm",passForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	String acStatus="";
                        if(!passForm.getAcType().equalsIgnoreCase("SELECT")){
                      	  if(passForm.getAcno()!=null&&!passForm.getAcno().equals("")){
                      		  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(passForm.getAcType(), Integer.parseInt(passForm.getAcno()));
                      		  if(accountinfoobject!=null){
                      			CustomerMasterObject cust=fcDelegate.getCustomer(accountinfoobject.getCid());
                  			  req.setAttribute("cust",cust);
                      			  if(accountinfoobject.getAccStatus().equals("C")){
                      				  acStatus="CLOSED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getAccStatus().equals("O")){
                      				  acStatus="OPENED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getAccStatus().equals("I")){
                      				  acStatus="INOPERATIVE";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                      				  acStatus="FREEZED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }
                      			else if(accountinfoobject.getVerified()==null){
                    				  acStatus="Not Verified";
                    				  req.setAttribute("Verified","Verified");
                    				  req.setAttribute("msg","Account Not yet Verified");
                    				req.setAttribute("acStatus",acStatus);
                    			  }
                      			  /*else{
                      				CustomerMasterObject cust=fcDelegate.getCustomer(accountinfoobject.getCid());
                      			  req.setAttribute("cust",cust);
                      			  }*/
                      		  }}}
                        
                        System.out.println("To date is "+passForm.getTdate());
                        
                    	// array_accounttransobject=fcDelegate.sendpass(passForm.getAcType(),passForm.getAcno(),passForm.getSqno(),passForm.getFdate(),passForm.getTdate());
                        array_accounttransobject=fcDelegate.getPassSheet(passForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate());
                    	if(array_accounttransobject!=null) {
                        System.out.println("*********************length of passbook object is "+array_accounttransobject.length);
                    	 
                    	 req.setAttribute("passvalues",array_accounttransobject);
                    	//to save to file
                    	 if(passForm.getSave()!=null&&passForm.getSave().length()!=0&&passForm.getSave().equals("saveit")){
                    		 System.out.println("Inside save ");
                    		 //TO save to an excel Sheet
                    		 res.setContentType("application/.csv");
                    	        res.setHeader("Content-disposition", "attachment;filename=PassBook.csv");
                    	        
                    	        java.io.PrintWriter out = res.getWriter();
                    	        out.print("\n");
                    	        out.print("\n");
                    	        out.print("\n");
                    	        out.print(",");out.print(",");out.print(",");
                    	        out.print("PassBook Details for A/C No: "+passForm.getAcno());
                    	        out.print("\n");
                    	        out.print("\n");
                       		   /*HSSFCell cell = row.createCell((short)0);
                       		   cell.setCellValue(1);*/
                    	       
                       		out.print("Date"); out.print(",");
                       		out.print("Cheque No"); out.print(",");
                       		out.print("Narration/Payee"); out.print(",");
                       		out.print("Debit"); out.print(",");
                       		out.print("Credit"); out.print(",");
                       		out.print("Closing Balance"); out.print("\n");
                       		   
                       		 for(int k=0;k<array_accounttransobject.length;k++){
                       			  
                       			out.print(array_accounttransobject[k].getTransDate());
                       			out.print(",");
                  					if(array_accounttransobject[k].getTransMode().equals("G")){
                  						out.print(array_accounttransobject[k].getChqDDNo());out.print(",");
                  						out.print(array_accounttransobject[k].getTransNarr());
                  						if(array_accounttransobject[k].getPayeeName()!=null){
                  							out.print(" ");
                      						out.print(array_accounttransobject[k].getPayeeName());
                      						out.print(",");
                      					}
                  						else{
                  							out.print(",");
                  						}
                       		   }
                  					
                  						
                  				
                  					else if (array_accounttransobject[k].getTransMode().equals("C")){
                  						out.print(" ");out.print(",");
                  						out.print("Cash.ScrNo "+(array_accounttransobject[k].getRef_No()==0?" ":""+array_accounttransobject[k].getRef_No()));
                  						if(array_accounttransobject[k].getPayeeName()!=null){
                  							out.print(" ");
                      						out.print(array_accounttransobject[k].getPayeeName());
                      						out.print(",");
                      					}
                  						else{
                  							out.print(",");
                  						}
                  					}
                  					else{
                  						out.print(" ");out.print(",");
                  						out.print("Trf frm "+array_accounttransobject[k].getTransNarr());
                  						if(array_accounttransobject[k].getPayeeName()!=null){
                  							out.print(" ");
                      						out.print(array_accounttransobject[k].getPayeeName());
                      						out.print(",");
                      					}
                  						else{
                  							out.print(",");
                  						}
                  					}
                  					
                  					
                  					if(array_accounttransobject[k].getCdInd().equals("D")) {
                  						out.print(array_accounttransobject[k].getTransAmount());
                  						out.print(",");out.print(" ");out.print(",");
                  					}
                  					else if(array_accounttransobject[k].getCdInd().equals("C")) {
                  						out.print(" ");out.print(",");
                  						out.print(array_accounttransobject[k].getTransAmount());
                  						out.print(",");
                  					} 
                  					
                  					out.print(array_accounttransobject[k].getCloseBal());
                  					//<td><%=acctranobj[k].getCloseBal()%></td>
                  					
                  					
                  					
                  			
                  			 
                       			   
                       			out.print("\n");   
                       		   }
                    		    req.setAttribute("msg","Saved to excel file in C:");
                    		    return null;
                    		 
                    	 }
                    	
                    	
                    	}
                    	else{
                    		req.setAttribute("msg","No transactions");
                    	}
                    	 String perPath=MenuNameReader.getScreenProperties("3014");
                    	 req.setAttribute("pageId",path);
                         req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                      	req.setAttribute("PassbookForm",passForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    }
                    
                                   	else{
                    String perPath=MenuNameReader.getScreenProperties("3015");
                    path=MenuNameReader.getScreenProperties(passForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
                    req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
               	 req.setAttribute("PassbookForm",passForm);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        //Passbook ends here
        
        
        //For PassBook Printing
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PassbookPrintMenu")){
            try{
            	FCPassbookForm passForm=(FCPassbookForm)form;
            	
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(passForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(passForm.getPageId()));
            	
            	System.out.println("At 373"+passForm);
               
            	req.setAttribute("pageNum",passForm.getPageId());
               System.out.println("At 379"+passForm.getPageId());
               logger.info(passForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(passForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(passForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   String dates=fcDelegate.getSysDate();
                   setSBOpeningInitParams(req,path,fcDelegate);
                   passForm.setFdate(dates.trim());
                   passForm.setTdate(dates.trim());
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PassbookPrint")){
        	System.out.println("at 931 inside Passbook");
        	FCPassbookForm passForm=(FCPassbookForm)form;
            String path;
            System.out.println("sbform=="+passForm);
            System.out.println("sbform=="+passForm.getPageId());
            try{
                req.setAttribute("pageNum",passForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(passForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(passForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+passForm.getAcType());
                    System.out.println("acNum=="+passForm.getAcno());
                    if(passForm.getAcType().equals("SELECT")&&passForm.getAcno().equals("")){
                    	
                    		
                    		req.setAttribute("closingmsg","Fill all the fields");
                    		
                    		
                    		req.setAttribute("pageId",path);
                         	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                         	req.setAttribute("PassbookForm",passForm);
                         	return map.findForward(ResultHelp.getSuccess());
                    	
                     	
         			
                    }
                    else{
                    	String acStatus="";
                        if(!passForm.getAcType().equalsIgnoreCase("SELECT")){
                      	  if(passForm.getAcno()!=null&&!passForm.getAcno().equals("")){
                      		  AccountInfoObject accountinfoobject=fcDelegate.getAccountInfo(passForm.getAcType(), Integer.parseInt(passForm.getAcno()));
                      		  if(accountinfoobject!=null){
                      			CustomerMasterObject cust=fcDelegate.getCustomer(accountinfoobject.getCid());
                  			  req.setAttribute("cust",cust);
                      			  if(accountinfoobject.getAccStatus().equals("C")){
                      				  acStatus="CLOSED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getAccStatus().equals("O")){
                      				  acStatus="OPENED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getAccStatus().equals("I")){
                      				  acStatus="INOPERATIVE";
                      				req.setAttribute("acStatus",acStatus);
                      			  }else if(accountinfoobject.getFreezeInd().equals("T")){
                      				  acStatus="FREEZED";
                      				req.setAttribute("acStatus",acStatus);
                      			  }
                      			  /*else{
                      				CustomerMasterObject cust=fcDelegate.getCustomer(accountinfoobject.getCid());
                      			  req.setAttribute("cust",cust);
                      			  }*/
                      		  }}}
                        
                        System.out.println("To date is "+passForm.getTdate());
                        
                    	// array_accounttransobject=fcDelegate.sendpass(passForm.getAcType(),passForm.getAcno(),passForm.getSqno(),passForm.getFdate(),passForm.getTdate());
                        array_accounttransobject=fcDelegate.getPassSheet(passForm.getAcType(),passForm.getAcno(),passForm.getFdate(),passForm.getTdate());
                    	if(array_accounttransobject!=null) {
                        System.out.println("*********************length of passbook object is "+array_accounttransobject.length);
                    	 
                    	 req.setAttribute("passvalues",array_accounttransobject);
                    	//to save to file
                    	 if(passForm.getSave()!=null&&passForm.getSave().length()!=0&&passForm.getSave().equals("saveit")){
                    		 System.out.println("Inside save ");
                    		 //TO save to an excel Sheet
                    		 HSSFWorkbook wb = new HSSFWorkbook();
                    		   HSSFSheet sheet = wb.createSheet("new sheet");
                    		   HSSFRow row = sheet.createRow((short)0);
                    		   /*HSSFCell cell = row.createCell((short)0);
                    		   cell.setCellValue(1);*/
                    		   row.createCell((short)1).setCellValue("DATE");
                    		   row.createCell((short)2).setCellValue("Cheque No");
                    		   row.createCell((short)3).setCellValue("Narration/Payee");
                    		   row.createCell((short)4).setCellValue("Debit");
                    		   row.createCell((short)5).setCellValue("Credit");
                    		   row.createCell((short)6).setCellValue("Closing Balance");
                    		   
                    		  for(int k=0;k<array_accounttransobject.length;k++){
                    			   HSSFRow row1 = sheet.createRow((short)k+1);
                    			   row1.createCell((short)1).setCellValue(array_accounttransobject[k].getTransDate());
                    			   
               					if(array_accounttransobject[k].getTransMode().equals("G")){
               					row1.createCell((short)2).setCellValue(array_accounttransobject[k].getChqDDNo());
               					row1.createCell((short)3).setCellValue(array_accounttransobject[k].getTransNarr());
                    		   }
               					
               						
               				
               					else if (array_accounttransobject[k].getTransMode().equals("C")){
               						row1.createCell((short)3).setCellValue("Cash.ScrNo "+(array_accounttransobject[k].getRef_No()==0?" ":""+array_accounttransobject[k].getRef_No()));
               					/*<td><%=""%></td>
               					<td><%=String.valueOf("Cash.ScrNo "+(acctranobj[k].getRef_No()==0?" ":""+acctranobj[k].getRef_No()))%></td>*/
               						}
               					else{
               						row1.createCell((short)3).setCellValue("Trf frm "+array_accounttransobject[k].getTransNarr());
               					/*<td><%=""%></td>
               					<td><%=String.valueOf("Trf frm "+acctranobj[k].getTransNarr())%></td>
               						*/
               					}
               					if(array_accounttransobject[k].getCdInd().equals("D")) {
               						row1.createCell((short)4).setCellValue(array_accounttransobject[k].getTransAmount());
               					/*<td><%=acctranobj[k].getTransAmount()%></td>
               					<td><%=""%></td>*/
               					}
               					else if(array_accounttransobject[k].getCdInd().equals("C")) {
               						row1.createCell((short)5).setCellValue(array_accounttransobject[k].getTransAmount());
               					/*<td><%=""%></td>
               					<td><%=acctranobj[k].getTransAmount()%></td>*/
               					
               					} 
               					else{
               						row1.createCell((short)5).setCellValue(array_accounttransobject[k].getTransAmount());
               					/*<td><%=""%></td>
               					<td><%=acctranobj[k].getTransAmount()%></td>*/
               					} 
               					row1.createCell((short)6).setCellValue(array_accounttransobject[k].getCloseBal());
               					//<td><%=acctranobj[k].getCloseBal()%></td>
               					
               					
               					
               			
               			 
                    			   
                    			   
                    		   }
                    		     FileOutputStream fileOut = new FileOutputStream("c:\\FirstPassbook.xls");
                    		    wb.write(fileOut);
                    		    fileOut.close(); 
                    		    req.setAttribute("msg","Saved to excel file in C:");
                    		 
                    	 }
                    	
                    	
                    	}
                    	else{
                    		req.setAttribute("msg","No transactions");
                    	}
                    	 String perPath=MenuNameReader.getScreenProperties("3014");
                      	 req.setAttribute("pageId",path);
                      	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                      	req.setAttribute("PassbookForm",passForm);
                      	return map.findForward(ResultHelp.getSuccess());
                            
                    		
                    		
                    	}
                    }
                    
                                   	else{
                    String perPath=MenuNameReader.getScreenProperties("3015");
                    path=MenuNameReader.getScreenProperties(passForm.getPageId());
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("PassbookForm",passForm);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                
                    }
    				
    				
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        //Passbook ends here
        
        
        
        
        //*****************passbook printing*********************
        
        
        //PassSheet starts here
        
         if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PassSheetMenu")){
            try{
            	PassSheetForm passStForm=(PassSheetForm)form;
            	passStForm.setSysdate(FrontCounterDelegate.getSysDate());
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(passStForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(passStForm.getPageId()));
            	
            	System.out.println("At 373"+passStForm);
               
            	req.setAttribute("pageNum",passStForm.getPageId());
               System.out.println("At 379"+passStForm.getPageId());
               logger.info(passStForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(passStForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(passStForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/PassSheet")){
        	System.out.println("at 1088 inside PassSheetForm");
        	PassSheetForm passStForm=(PassSheetForm)form;
        	passStForm.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            try{
                req.setAttribute("pageNum",passStForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(passStForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(passStForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+passStForm.getAcType());
                    //System.out.println("acNum=="+passStForm.getAcNum());
                    if(passStForm.getAcType().equals("SELECT")&&passStForm.getAcno().equals("")){
                    	System.out.println("At1103 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","");
                    	 String perPath=MenuNameReader.getScreenProperties("3007");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("PassSheetForm",passStForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	
                    String checkret=fcDelegate.checkCondition(passStForm.getAcType(), passStForm.getAcno());
                    	
                    	System.out.println("At 437 in Action return of check"+checkret);
                    	if(!(checkret.equals("good"))){
                    		
                    		String perPath=MenuNameReader.getScreenProperties("3007");
                            System.out.println("PerPath"+perPath);
                            req.setAttribute("error1",checkret);
                            req.setAttribute("closingmsg",checkret);
                            req.setAttribute("pageId",path);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("PassSheetForm",passStForm);;
            				System.out.println("At 446 path:"+map.getPath());
                    		
                    		
                    	}
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3007");
                    System.out.println("PerPath"+perPath);
                    req.setAttribute("closingmsg","");
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("PassSheetForm",passStForm);;
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }}
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }

        
        //PassSheet ends here
        
        
         
         //InterestCalaulation starts here
         
         if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InterestCalcMenu")){
            try{
            	InterestCalcActionForm IntcalcForm=(InterestCalcActionForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 1166<<---------->"+MenuNameReader.containsKeyScreen(IntcalcForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(IntcalcForm.getPageId()));
            	
            	System.out.println("At 1169"+IntcalcForm.getPageId());
               
            	req.setAttribute("pageNum",IntcalcForm.getPageId());
               System.out.println("At 1172"+IntcalcForm.getPageId());
               logger.info(IntcalcForm.getPageId()+"This is from Interest Calculation"+map.getPath());
               if(MenuNameReader.containsKeyScreen(IntcalcForm.getPageId())){
            	   System.out.println("At 1175---------->");
                   path=MenuNameReader.getScreenProperties("3016");
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
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
             System.out.println("At 1191"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InterestCalc")){
        	System.out.println("at 1199 inside InterestCalcForm");
        	InterestCalcActionForm IntcalcForm=(InterestCalcActionForm)form;
            String path;
            System.out.println("IntcalcForm=="+IntcalcForm);
            System.out.println("IntcalcForm=="+IntcalcForm.getPageId());
            try{
                req.setAttribute("pageNum",IntcalcForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(IntcalcForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(IntcalcForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+IntcalcForm.getAcType());
                    System.out.println("IntcalcForm new========="+IntcalcForm.getRad());
                    
                    
                    if(IntcalcForm.getRad()!=null&&IntcalcForm.getRad().equals("account")){
                    	System.out.println("its Account");
                    	if(IntcalcForm.getRad().equals("account")&&IntcalcForm.getAcno()!=null){
                    		System.out.println("IntcalcForm =**************1222"+IntcalcForm.getAcno());
                    		AccountObject accountobject=(AccountObject)fcDelegate.getIntroducerAcntDetails(IntcalcForm.getAcType(),Integer.parseInt(IntcalcForm.getAcno()));
                    		if(accountobject==null)
                			{
                				req.setAttribute("msg","Given account number not found");
                				
                			}
                			else if(accountobject.getAccStatus().equals("C"))
                			{
                				req.setAttribute("msg","Account is Closed");
                				
                			}
                			else if(accountobject.getAccStatus().equals("I"))
                			{	
                				req.setAttribute("msg","InOperative Account");
                				
                			}
                			else if(accountobject.getVerified()==null)
                			{	
                				req.setAttribute("msg","Given account is not yet Verified");
                				
                			}
                			else if(accountobject.getDefaultInd().equals("T"))
                			{	
                				req.setAttribute("msg","Default account");
                				
                			}
                			else if(accountobject.getFreezeInd().equals("T"))
                			{	
                				req.setAttribute("msg","Freezed account");
                				
                			}
                			else{
                				
                				String int_date=fcDelegate.Interestcal(IntcalcForm.getAcType(),IntcalcForm.getAcno());
                				if(int_date==null){
                					req.setAttribute("msg","Last interst Paid date not updated");
                					
                				}
                				else{
                					
                					IntcalcForm.setTodate(int_date);
                					
                					IntcalcForm.setNextdate(fcDelegate.getSysDate());
                					req.setAttribute("enable","true");
                				}
                			}
                    		
                    		
                    		
                    		/*System.out.println(" u have selected all");
                        	req.setAttribute("all","yes");
                        	path=MenuNameReader.getScreenProperties("3016");
                            fcDelegate=new FrontCounterDelegate();
                            setSBOpeningInitParams(req,path,fcDelegate);
                            req.setAttribute("pageId",path);
                            req.setAttribute("","acType");
                            IntcalcForm.setMinibal("0.0");
                            IntcalcForm.setRad(IntcalcForm.getRad());
                            req.setAttribute("InterestCalcActionForm",IntcalcForm);
                            return map.findForward(ResultHelp.getSuccess());*/
                    		
                    		
                    		 if(IntcalcForm.getRad().equals("account")&&IntcalcForm.getAcno()!=null&&IntcalcForm.getCal()!=null&&IntcalcForm.getCal().equals("calculate")){
                             	if(IntcalcForm.getRad().equals("account")&&IntcalcForm.getAcno().trim().length()==0&&IntcalcForm.getRecal()!=null&&IntcalcForm.getRecal().equals("recalculate")){
                             		
                             		String result=fcDelegate.recalclulateInterest(IntcalcForm.getAcType(),IntcalcForm.getAcno(),IntcalcForm.getTodate(),IntcalcForm.getNextdate(),IntcalcForm.getMinibal(),user,tml);
                                 	req.setAttribute("msg",result);
                             	}
                             	else{
                             	String result=fcDelegate.calclulateInterest(IntcalcForm.getAcType(),IntcalcForm.getAcno(),IntcalcForm.getTodate(),IntcalcForm.getNextdate(),IntcalcForm.getMinibal(),user,tml);
                             	if(result.equals("Already Calculated")){
                             		req.setAttribute("recalc","recalc");
                             		req.setAttribute("msg",result+"  click Recalculate to recalculate Interest");	
                             	}
                             	else{
                             	req.setAttribute("msg",result);
                             	}
                             	}
                             	//changed
                             	}
                    	}
                    	
                    	
                    	System.out.println(" u have selected Account");
                    	req.setAttribute("all","yes");
                    	path=MenuNameReader.getScreenProperties("3016");
                        fcDelegate=new FrontCounterDelegate();
                        req.setAttribute("pageId",path);
                        req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                        req.setAttribute("","acType");
                        IntcalcForm.setMinibal("0.0");
                        req.setAttribute("InterestCalcActionForm",IntcalcForm);
                        return map.findForward(ResultHelp.getSuccess());	
                    	
                    }
                    else if(IntcalcForm.getRad()!=null&&IntcalcForm.getRad().equals("all")){
                    	System.out.println("At 4480 calculating Interest for Alls");
                    	req.setAttribute("enable","true");
                    	if(IntcalcForm.getRad().equals("all")&&!IntcalcForm.getAcType().equals("SELECT")){
                    		String int_date=fcDelegate.Interestcal(IntcalcForm.getAcType(),"0");
                    		
                    		if(int_date==null){
                    			req.setAttribute("msg","Last interst Paid date not updated");
            					
            				}
            				else{
            					String st=fcDelegate.checkIntDate(IntcalcForm.getAcType(),int_date);
            					if(st!=null){
            					req.setAttribute("msg",st);
            					}
            					else{
            					IntcalcForm.setTodate(int_date);
            					IntcalcForm.setNextdate(fcDelegate.getSysDate());
            					}
            				}
                    		
                    	}
                    	if(IntcalcForm.getRad().equals("all")&&!IntcalcForm.getAcType().equals("SELECT")&&IntcalcForm.getCal().equals("calculate")){
                    		if(IntcalcForm.getRad().equals("account")&&IntcalcForm.getAcno()!=null&&IntcalcForm.getRecal().equals("recalculate")){
                        		
                        		String result=fcDelegate.recalclulateInterest(IntcalcForm.getAcType(),"0",IntcalcForm.getTodate(),IntcalcForm.getNextdate(),IntcalcForm.getMinibal(),user,tml);
                        		req.setAttribute("msg","Interest Calculated Successfully");
                        	}
                        	else{
                        	String result=fcDelegate.calclulateInterest(IntcalcForm.getAcType(),"0",IntcalcForm.getTodate(),IntcalcForm.getNextdate(),IntcalcForm.getMinibal(),user,tml);
                        	if(result.equals("Already Calculated")){
                        		req.setAttribute("recalc","recalc");
                        		req.setAttribute("msg",result+"  click Recalculate to recalculate Interest");	
                        	}
                        	else{
                        		req.setAttribute("msg","Interest Calculated Successfully");
                        	}
                        	}
                        		
                    	}
                    	System.out.println(" u have selected all");
                    	
                    	path=MenuNameReader.getScreenProperties("3016");
                        fcDelegate=new FrontCounterDelegate();
                        req.setAttribute("pageId",path);
                        req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
                        req.setAttribute("","acType");
                        IntcalcForm.setMinibal("0.0");
                        //req.setAttribute("enable","true");
                        req.setAttribute("InterestCalcActionForm",IntcalcForm);
                        return map.findForward(ResultHelp.getSuccess());	
                    	
                    }
                    
                    	else{
                    String perPath=MenuNameReader.getScreenProperties("3016");
                    System.out.println("PerPath"+perPath);
                   
                    req.setAttribute("pageId",path);
                    req.setAttribute("AcType",fcDelegate.getComboElementsInt(0));
               	 req.setAttribute("InterestCalcActionForm",IntcalcForm);
    				System.out.println("At 1251 path:"+map.getPath());
    				
    					
    				
                    	}
                    
    				
    				return map.findForward(ResultHelp.getSuccess());
                }}
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }

        
        //InterestCalaulation ends here
        
         

         
        
        //ACCOUNT CLOSE 
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/AccountCloseMenu")){
            try{
            	AccountCloseActionForm acForm=(AccountCloseActionForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 371<<---------->"+MenuNameReader.containsKeyScreen(acForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(acForm.getPageId()));
            	
            	System.out.println("At 373"+acForm);
               
            	req.setAttribute("pageNum",acForm.getPageId());
               System.out.println("At 379"+acForm.getPageId());
               logger.info(acForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(acForm.getPageId())){
            	   System.out.println("At 292---------->");
                   path=MenuNameReader.getScreenProperties(acForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/AccountClosing")){
        	System.out.println("at 407 inside AccountClosing");
        	AccountCloseActionForm acForm=(AccountCloseActionForm)form;
            String path;
            System.out.println("sbform=="+acForm);
            System.out.println("sbform=="+acForm.getPageId());
            try{
                req.setAttribute("pageNum",acForm.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(acForm.getPageId())){
                    path=MenuNameReader.getScreenProperties(acForm.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("actype=="+acForm.getAcType());
                    System.out.println("acNum=="+acForm.getAcNum());
                    if(acForm.getAcType().equals("SELECT")||acForm.getAcNum().equals("")){
                    	System.out.println("At 412 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","");
                    	 String perPath=MenuNameReader.getScreenProperties("3007");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("AccountCloseActionForm",acForm);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	
                    String checkret=fcDelegate.checkCondition(acForm.getAcType(), acForm.getAcNum());
                    	
                    	System.out.println("At 4497 in Action return of check"+checkret);
                    	AccountInfoObject acinfo=fcDelegate.toGetAccountInfo(acForm.getAcType(),acForm.getAcNum());
                    	
                    	if(acinfo!=null){
                    	if(acinfo.getAccStatus().equals("C")){
                    		req.setAttribute("closingmsg","Account Already closed");
                    		
                    	}
                    	else{
                    	if(!(checkret.equals("good"))){
                    		
                    		String perPath=MenuNameReader.getScreenProperties("3007");
                            System.out.println("PerPath"+perPath);
                            req.setAttribute("error1",checkret);
                           
                            req.setAttribute("pageId",path);
                            if(acForm.getHidvar().equals("submit")){
                            	String result=fcDelegate.accountClose(acForm.getAcType(),acForm.getAcNum());
                            	 req.setAttribute("closingmsg",result);
                            }
                            else
                            	 req.setAttribute("closingmsg",checkret);
                       	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                       	 req.setAttribute("AccountCloseActionForm",acForm);;
            				System.out.println("At 446 path:"+map.getPath());
                    		req.setAttribute("disabling","disable");
                    		
                    	}
                    	else{
                    		req.setAttribute("closingmsg","Click on close button to close the account.");
                    		if(acForm.getHidvar().equals("submit")){
                            	String result=fcDelegate.accountClose(acForm.getAcType(),acForm.getAcNum());
                            	 req.setAttribute("closingmsg",result);
                            }
                    	
                    
    				
    					
    				
                    	}
                    	}
                    	}
                    	else
                    	{
                    		req.setAttribute("closingmsg","Account not found");
                    	}
                    	String perPath=MenuNameReader.getScreenProperties("3007");
                        System.out.println("PerPath"+perPath);
                        
                        req.setAttribute("error1","false");
                        req.setAttribute("pageId",path);
                   	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                   	 req.setAttribute("AccountCloseActionForm",acForm);;
        				System.out.println("At 438 path:"+map.getPath());
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }}
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        //------------------------FOR POConsolidation Verification -----------------
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyPOConsolMenu")){
            try{
            	VerifyPOConsolidationForm pocon=(VerifyPOConsolidationForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 4623<<---------->"+MenuNameReader.containsKeyScreen(pocon.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(pocon.getPageId()));
            	
            	System.out.println("At 4626"+pocon);
               
            	req.setAttribute("pageNum",pocon.getPageId());
               System.out.println("At 4628"+pocon.getPageId());
               logger.info(pocon.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(pocon.getPageId())){
            	   System.out.println("At 4632---------->");
                   path=MenuNameReader.getScreenProperties(pocon.getPageId());
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyPOConsol")){
        	System.out.println("at 4655 inside /FrontCounter/VerifyPOConsol");
        	VerifyPOConsolidationForm pocon=(VerifyPOConsolidationForm)form;
            String path;
            System.out.println("pocon=="+pocon);
            System.out.println("pocon=="+pocon.getPageId());
            try{
                req.setAttribute("pageNum",pocon.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(pocon.getPageId())){
                    path=MenuNameReader.getScreenProperties(pocon.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("pono=="+pocon.getPayorder());
                    //System.out.println("acNum=="+acForm.getAcNum());
                    if(pocon.getPayorder().length()==0){
                    	System.out.println("At 4670 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","Enter po no.");
                    	 String perPath=MenuNameReader.getScreenProperties("3049");
                    	 req.setAttribute("pageId",path);
                    	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
                    	 req.setAttribute("VerifyPOConsolidationForm",pocon);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	unverified=fcDelegate.getUnverifiedPOConsol();
                    	req.setAttribute("unverified",unverified);
                    	payorderobject=fcDelegate.poConsolDetails(pocon.getPayorder());
                    	if(payorderobject!=null){
                    	pocon.setChequeno(String.valueOf(payorderobject[0].getPOChqNo()));
                    	pocon.setPayeename(payorderobject[0].getPOPayee());
                    	req.setAttribute("payorderobject",payorderobject);
                    	}
                    	else{
                    		req.setAttribute("closingmsg","No Details Found");
                    		
                    	}
                    	
                    	if(pocon.getHidval().equals("verify")){
                    		PayOrderObject poObj=new PayOrderObject();//HARDCODED========Values=============
                    		poObj.uv.setVerId(user);
                    		poObj.uv.setVerTml(tml);
                    		poObj.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
                    		poObj.setPayOrderNo(Integer.parseInt(pocon.getPayorder()));
                    		poObj.setPODate(payorderobject[0].getPODate());
                    		poObj.setPOMakeDate(payorderobject[0].getPODate());
                    		poObj.setPOAmount(payorderobject[0].getTransAmount());
                    		poObj.setPOChqNo(Integer.parseInt(pocon.getChequeno()));
        	
                    		String str=fcDelegate.verifyConsolidatedPO(poObj);
                    		req.setAttribute("closingmsg",str);
                    	}
                    String perPath=MenuNameReader.getScreenProperties("3049");
                    System.out.println("PerPath"+perPath);
                    
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("VerifyPOConsolidationForm",pocon);
    				System.out.println("At 438 path:"+map.getPath());
    				
    					
    				
                    	}
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        
        //========================POConsolidation ends here------------------
        
        //============Start=======================PO REPORT==========================
        
        //UNCASHED PAYORDER'S-----------
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOUncashMenu")){
            try{
            	UncashedPOReportForm unpo=(UncashedPOReportForm)form;
            	unpo.setSysdate(FrontCounterDelegate.getSysDate());
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 4623<<---------->"+MenuNameReader.containsKeyScreen(unpo.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(unpo.getPageId()));
            	
            	System.out.println("At 4626"+unpo);
            	unpo.setTdate(fcDelegate.getSysDate());
            	req.setAttribute("pageNum",unpo.getPageId());
               System.out.println("At 4628"+unpo.getPageId());
               logger.info(unpo.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(unpo.getPageId())){
            	   System.out.println("At 4632---------->");
                   path=MenuNameReader.getScreenProperties(unpo.getPageId());
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOUncash")){
        	System.out.println("at 4801 inside /FrontCounter/ListPOUncash");
        	UncashedPOReportForm unpo=(UncashedPOReportForm)form;
        	unpo.setSysdate(FrontCounterDelegate.getSysDate());
            String path;
            System.out.println("pocon=="+unpo);
            System.out.println("pocon=="+unpo.getPageId());
            try{
                req.setAttribute("pageNum",unpo.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(unpo.getPageId())){
                    path=MenuNameReader.getScreenProperties(unpo.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    System.out.println("unpo=to date="+unpo.getTdate());
                    System.out.println("unpo=From date="+unpo.getTdate());
                    //System.out.println("acNum=="+acForm.getAcNum());
                    if(unpo.getTdate().length()==0||unpo.getFdate().length()==0){
                    	System.out.println("At 4817 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","Enter TO and From Dates");
                    	 String perPath=MenuNameReader.getScreenProperties("3050");
                    	 req.setAttribute("pageId",path);
                    	 
                    	 req.setAttribute("UncashedPOReportForm",unpo);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	PayOrderObject[] poObj=fcDelegate.RetrieveCashUncash(unpo.getTdate(),unpo.getFdate());
                    	if(poObj!=null){
                    		req.setAttribute("poObj",poObj);
                    		if(unpo.getSave()!=null&&unpo.getSave().length()!=0&&unpo.getSave().equals("saveit")){
                       		 System.out.println("Inside save ");
                     /*  		 //TO save to an excel Sheet
                       		 HSSFWorkbook wb = new HSSFWorkbook();
                       		   HSSFSheet sheet = wb.createSheet("Uncashed PO Report");
                       		   HSSFRow row = sheet.createRow((short)0);
                       		   HSSFCell cell = row.createCell((short)0);
                       		   cell.setCellValue(1);
                       		   row.createCell((short)1).setCellValue("Sr No.");
                       		   row.createCell((short)2).setCellValue("PO No.");
                       		   row.createCell((short)3).setCellValue("PO Date");
                       		   row.createCell((short)4).setCellValue("Cheque No.");
                       		   row.createCell((short)5).setCellValue("Payee Name");
                       		   row.createCell((short)6).setCellValue("Amount");
                       		   
                       		  for(int k=0;k<poObj.length;k++){
                       			   HSSFRow row1 = sheet.createRow((short)k+1);
                       			row1.createCell((short)1).setCellValue(k);
                       			row1.createCell((short)2).setCellValue(poObj[k].getPayOrderNo());
                       			row1.createCell((short)3).setCellValue(poObj[k].getPODate() );
                       			row1.createCell((short)4).setCellValue(poObj[k].getPOChqNo() );
                       			row1.createCell((short)5).setCellValue(poObj[k].getPOPayee());
                       			row1.createCell((short)6).setCellValue(poObj[k].getTransAmount());
                                
                       		   }
                       		     FileOutputStream fileOut = new FileOutputStream("c:\\FirstUncashedPOReport.xls");
                       		    wb.write(fileOut);//req.getRemoteAddr()+
                       		    fileOut.close(); 
                       		    req.setAttribute("closingmsg","Saved to excel file in C:");*/
                       		 
                       		    
                       		 res.setContentType("application/.csv");
                 	        res.setHeader("Content-disposition", "attachment;filename=UnCashed PO Report.csv");
                 	        
                 	        java.io.PrintWriter out = res.getWriter();
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print("\n");
                 	        out.print(",");out.print(",");out.print(",");
                 	        out.print("Uncashed PO Report from "+unpo.getFdate()+"  to  "+unpo.getTdate());
                 	        out.print("\n");
                 	        out.print("\n");
                    		
                    		
                    		out.print("Sr No.");out.print(",");
                    		out.print("PO No.");out.print(",");
                    		out.print("PO Date");out.print(",");
                    		out.print("Cheque No.");out.print(",");
                    		out.print("Payee Name");out.print(",");
                    		out.print("Amount");out.print(",");out.print("\n");
                    		
                    		for(int k=0;k<poObj.length;k++){
                    			  
                    			out.print(k+1);out.print(",");
                    			out.print(poObj[k].getPayOrderNo());out.print(",");
                    			out.print(poObj[k].getPODate() );out.print(",");
                    			out.print(poObj[k].getPOChqNo() );out.print(",");
                    			out.print(poObj[k].getPOPayee());out.print(",");
                    			out.print(poObj[k].getTransAmount());out.print(",");
                    			out.print("\n");
                    		   }
                       		    unpo.setSave("");
                      return null; 		 
                       	 }
                    		
                    		
                    		
                    	}
                    	else{
                    		
                    		req.setAttribute("closingmsg","No Records Found");
                    	}
                    String perPath=MenuNameReader.getScreenProperties("3050");
                    System.out.println("PerPath"+perPath);
                    
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("UncashedPOReportForm",unpo);
    				System.out.println("At 4845 path:"+map.getPath());
    				
    					
    				
                    	}
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        //=====================PayOrder Make=============
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOCrMenu")){
            try{
            	FCPOMakeForm pomake=(FCPOMakeForm)form;
            	pomake.setSysdate(FrontCounterDelegate.getSysDate());
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 4868<<---------->"+MenuNameReader.containsKeyScreen(pomake.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(pomake.getPageId()));
            	
            	System.out.println("At 4871"+pomake);
            	pomake.setTdate(fcDelegate.getSysDate());
            	req.setAttribute("pageNum",pomake.getPageId());
               System.out.println("At 4874"+pomake.getPageId());
               logger.info(pomake.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(pomake.getPageId())){
            	   System.out.println("At 4632---------->");
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOMake")){
        	System.out.println("at 4902 inside /FrontCounter/ListPOMake");
        	FCPOMakeForm pomake=(FCPOMakeForm)form;
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
                    	System.out.println("At 4918 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","Enter TO and From Dates");
                    	 String perPath=MenuNameReader.getScreenProperties("3051");
                    	 req.setAttribute("pageId",path);
                    	 
                    	 req.setAttribute("FCPOMakeForm",pomake);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	PayOrderObject[] poObj=fcDelegate.RetrivePOConsolidatedReport(pomake.getFdate(),pomake.getTdate());
                    	if(poObj!=null){
                    		req.setAttribute("poObj",poObj);
                    		
                    		if(pomake.getSave()!=null&&pomake.getSave().equals("submit")){
                    			/*//TO save to an excel Sheet
                          		 HSSFWorkbook wb = new HSSFWorkbook();
                          		   HSSFSheet sheet = wb.createSheet("Created PO Report");
                          		 HSSFRow row = sheet.createRow((short)0);
                          		row.createCell((short)1).setCellValue("List Of PayOrders Created");
                          		 
                          		   HSSFRow row1 = sheet.createRow((short)2);
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
                          			   HSSFRow row2 = sheet.createRow((short)k+4);
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
                      	        res.setHeader("Content-disposition", "attachment;filename=PO Created Report.csv");
                      	        
                      	        java.io.PrintWriter out = res.getWriter();
                      	        out.print("\n");
                      	        out.print("\n");
                      	        out.print("\n");
                      	        out.print(",");out.print(",");out.print(",");
                      	        out.print("PayOrder's Created Report from "+pomake.getFdate()+"  to  "+pomake.getTdate());
                      	        out.print("\n");
                      	        out.print("\n");
                         		
                         		
                         		
                         		out.print("Sr No.");out.print(",");
                         		out.print("Voucher No.");out.print(",");
                         		out.print("Debit A/c No.");out.print(",");
                         		out.print("Name");out.print(",");
                         		out.print("GL Type");out.print(",");
                         		out.print("GL Code");out.print(",");
                         		out.print("Amount");out.print(",");
                         		out.print("Comission");out.print(",");out.print("\n");
                         		
                         		for(int k=0;k<poObj.length;k++){
                       			   
                         			out.print(k+1);
                         			out.print(poObj[k].getPOType()+"   "+String.valueOf(poObj[k].getPOSerialNo()));
                         			out.print(poObj[k].getPOAccType()+" "+poObj[k].getPOAccNo() );
                         			out.print(poObj[k].getPOPayee() );
                         			out.print(poObj[k].getGLRefCode());
                         			out.print(poObj[k].getPOGlCode());
                         			out.print(poObj[k].getPOAmount());
                         			out.print(poObj[k].getCommissionAmount());
                         			out.print("\n");
                       		   }
                         		pomake.setSave("");
                    		return null;
                    		}
                    	}
                    	else{
                    		
                    		req.setAttribute("closingmsg","No Records Found");
                    	}
                    String perPath=MenuNameReader.getScreenProperties("3050");
                    System.out.println("PerPath"+perPath);
                    
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
               	 req.setAttribute("AcType",fcDelegate.getComboElements(0));
               	 req.setAttribute("FCPOMakeForm",pomake);
    				System.out.println("At 4845 path:"+map.getPath());
    				
    					
    				
                    	}
                    }
    				
    				return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }
        
        
        
        //Consolidated PO report Starts here
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOConsolReportMenu")){
            try{
            	FCPOConsolidatedReportForm pomake=(FCPOConsolidatedReportForm)form;
            	pomake.setSysdate(FrontCounterDelegate.getSysDate());
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 4969<<---------->"+MenuNameReader.containsKeyScreen(pomake.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(pomake.getPageId()));
            	
            	System.out.println("At 4971"+pomake);
            	pomake.setTdate(fcDelegate.getSysDate());
            	req.setAttribute("pageNum",pomake.getPageId());
               System.out.println("At 4875"+pomake.getPageId());
               logger.info(pomake.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(pomake.getPageId())){
            	   System.out.println("At 4978---------->");
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
             System.out.println("At 4996"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/ListPOConsol")){
        	System.out.println("at 5003 inside /FrontCounter/ListPOConsol");
        	FCPOConsolidatedReportForm pomake=(FCPOConsolidatedReportForm)form;
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
                    	System.out.println("At 5019 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","Enter TO and From Dates");
                    	 String perPath=MenuNameReader.getScreenProperties("3050");
                    	 req.setAttribute("pageId",path);
                    	 
                    	 req.setAttribute("FCPOMakeForm",pomake);
                     	
         				return map.findForward(ResultHelp.getSuccess());
                    }
                    else{
                    	PayOrderObject[] poObj=fcDelegate.RetrievePOMadeInfo(pomake.getFdate(),pomake.getTdate());
                    	if(poObj!=null){
                    		req.setAttribute("poObj",poObj);
                    		//to save to File
                    		
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
                     	        res.setHeader("Content-disposition", "attachment;filename=Consolidated PO Report.csv");
                     	        
                     	        java.io.PrintWriter out = res.getWriter();
                     	        out.print("\n");
                     	        out.print("\n");
                     	        out.print("\n");
                     	        out.print(",");out.print(",");out.print(",");
                     	        out.print("Consolidated PO's Report from "+pomake.getFdate()+"  to  "+pomake.getTdate());
                     	        out.print("\n");
                     	        out.print("\n");
                     	       out.print("Sr No.");out.print(",");
                     	       out.print("Pay Order No.");out.print(",");
                     	       out.print("Cheque No");out.print(",");
                     	       out.print("Payee Name");out.print(",");
                     	       out.print("Amount");out.print(",");
                     	       out.print("Vouchers Consolidated Type/No");out.print(",");
                        		out.print("\n");
                        		for(int i=0;i<poObj.length;i++){
                        			out.print(i+1);out.print(",");
                        			out.print(poObj[i].getPOSerialNo());out.print(",");
                        			out.print(poObj[i].getPOChqNo());out.print(",");
                        			out.print(poObj[i].getPOPayee());out.print(",");
                        			out.print(poObj[i].getTransAmount());out.print(",");
                        			out.print(poObj[i].getPOType()+"   "+String.valueOf(poObj[i].getPOSerialNo()));out.print(",");
                        			out.print("\n");
                        		}
                    			pomake.setSave("");
                    		return null;
                    		}
                    		
                    	}
                    	else{
                    		
                    		req.setAttribute("closingmsg","No Records Found");
                    	}
                    String perPath=MenuNameReader.getScreenProperties("3050");
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
        
                
        
        //To Verify ODCC Opening ===================================
        
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyODCCOpenMenu")){
            try{
            	VerifyODCCForm vodcc=(VerifyODCCForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 5178<<---------->"+MenuNameReader.containsKeyScreen(vodcc.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(vodcc.getPageId()));
            	
            	System.out.println("At 5181"+vodcc);
            	
            	req.setAttribute("pageNum",vodcc.getPageId());
               System.out.println("At 5184"+vodcc.getPageId());
               logger.info(vodcc.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(vodcc.getPageId())){
            	   System.out.println("At 5186---------->");
                   path=MenuNameReader.getScreenProperties(vodcc.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   req.setAttribute("pageId",path);
                   req.setAttribute("sh",fcDelegate.getSHModule(0));
                   req.setAttribute("AcType",fcDelegate.getodccModule(0));
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
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/VerifyODCCOpen")){
        	System.out.println("at 5211 inside /FrontCounter/VerifyODCCOpen");
        	VerifyODCCForm vodcc=(VerifyODCCForm)form;
            String path;
            System.out.println("vodcc=="+vodcc);
            System.out.println("vodcc=="+vodcc.getPageId());
            try{
                req.setAttribute("pageNum",vodcc.getPageId().trim());
                if(MenuNameReader.containsKeyScreen(vodcc.getPageId())){
                    path=MenuNameReader.getScreenProperties(vodcc.getPageId());
                    System.out.println(path);
                    fcDelegate=new FrontCounterDelegate();
                    System.out.println(fcDelegate);
                    if(!vodcc.getAcType().equals("SELECT")){
                    String[][] unverified=fcDelegate.getunverified(vodcc.getAcType());
               	 String perPath=MenuNameReader.getScreenProperties("3050");
            	 req.setAttribute("pageId",path);
                    req.setAttribute("unverified", unverified);
                    }
                   
                    //System.out.println("acNum=="+acForm.getAcNum());
                    if(vodcc.getAcType().equals("SELECT")||vodcc.getAc_no().length()==0){
                    	System.out.println("At 4817 inside FcAction");
                    	String tr="true";
                    	req.setAttribute("error1",tr);
                    	req.setAttribute("closingmsg","Please Enter Account type and Account no.");
                    	 String perPath=MenuNameReader.getScreenProperties("3050");
                    	 req.setAttribute("pageId",path);
                    	 
                    	 req.setAttribute("VerifyODCCForm",vodcc);
                    	 
                    }
                    else{
                    	ODCCMasterObject accountinfoobject=fcDelegate.odccData(vodcc.getAcType(), vodcc.getAc_no());
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
  else
  {
	  if(accountinfoobject.uv.getVerId()==null){
		  System.out.println("==============Not YET VERIFIED");
	  vodcc.setTxt_sh_type(String.valueOf(accountinfoobject.getShareAccNo()));
	  
	  accountObj=fcDelegate.getOdccAccount(vodcc.getCombo_share_type(),String.valueOf(accountinfoobject.getShareAccNo()));
	  if(accountObj!=null){
		  vodcc.setName(accountObj.getAccname());
	  }
	  //for detail
	  
	  if(vodcc.getDetails().equals("Application")&&vodcc.getDetails().length()!=0){
		  odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());	
		  accountObj=fcDelegate.getOdccAccount(vodcc.getCombo_share_type(),String.valueOf(accountinfoobject.getShareAccNo()));
		  req.setAttribute("master",accountObj);
		  if(odccmasterobject!=null){
		req.setAttribute("odccmaster",odccmasterobject);
		req.setAttribute("odccmasterobj",odccmasterobject);
		  System.out.println("odccmasterobject value is ================"+odccmasterobject);
		  }
		  else{
			  System.out.println("odccmasterobject value is ===null==");
		  }
		 String path1=MenuNameReader.getScreenProperties("3028");
		
		System.out.println("path is---------------"+path1);
		req.setAttribute("flag",path1); 
		  
	  }
	  
	  
	if(vodcc.getDetails().equals("EmploymentDetails")&&vodcc.getDetails().length()!=0){
		 odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());	
		  accountObj=fcDelegate.getOdccAccount(vodcc.getCombo_share_type(),String.valueOf(accountinfoobject.getShareAccNo()));
		  if(odccmasterobject!=null){
			  req.setAttribute("odccmasterobj",odccmasterobject);
			  System.out.println("odccmasterobject----------->>>"+odccmasterobject.getIncomeDetails()[0].getGoodsCondition());
		  }
		 req.setAttribute("master",accountObj);
			//req.setAttribute("odccmaster",odccmasterobject);
	String path1=MenuNameReader.getScreenProperties("3029");
		System.out.println("path is----------3029-----"+path1);
		req.setAttribute("flag",path1); 
		
	  }
	if(vodcc.getDetails().equals("Loan&ShareDetails")&&vodcc.getDetails().length()!=0){
		String path1=MenuNameReader.getScreenProperties("3030");
		ModuleObject[] mod=fcDelegate.getLoanMod(0);
		
		req.setAttribute("loanmod",mod);
		System.out.println("Length of ModuleObject Array is==="+mod.length);
		System.out.println("path is---------------"+path1);
		req.setAttribute("flag",path1); 
		  
	  }
	if(vodcc.getDetails().equals("ReceiptDetails")&&vodcc.getDetails().length()!=0){
		System.out.println("Receipt Details ");
		accountobject=fcDelegate.getOdccAccount(vodcc.getAcType(),vodcc.getAc_no());
		odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());
		System.out.println("accountobject------------->"+odccmasterobject.getRef_No());
		req.setAttribute("master",accountobject);
		req.setAttribute("odccmasterobj",odccmasterobject);
		req.setAttribute("newdata","newdata");
		req.setAttribute("odccmaster",odccmasterobject);
		
		
		
		String path1=MenuNameReader.getScreenProperties("3031");
		System.out.println("path is---------------"+path);
		req.setAttribute("flag",path1); 
		  
	  }
	/*if(applnform.getDetails().equals("DepositDetails")&&applnform.getDetails().length()!=0){
		AccountMasterObject amcObj=fcDelegate.getAccountMaster(Integer.parseInt(applnform.getDacno()),applnform.getDacType().trim());
		CustomerMasterObject cust=fcDelegate.getCustomer(amcObj.getCid());
		req.setAttribute("cust",cust);
		
		
		path=MenuNameReader.getScreenProperties("3055");
		System.out.println("path is---------------"+path);
		req.setAttribute("flag",path); 
		  
	  }*/
	if(vodcc.getDetails().equals("SignatureInst")&&vodcc.getDetails().length()!=0){
		System.out.println("getting Signature instruction");
		odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());
		CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
		req.setAttribute("cust",cust);
		SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(vodcc.getAc_no()),vodcc.getAcType());
		
		req.setAttribute("signature",signObject);
		if(signObject!=null)
		System.out.println("SignatureInstruction Object gives values========="+signObject.length);
		String path1=MenuNameReader.getScreenProperties("3012");
		System.out.println("path is---------------"+path1);
		req.setAttribute("flag","/SingnatureInst.jsp"); 
		  
	  }
	  
	if(vodcc.getDetails().equals("PersonalDetails")&&vodcc.getDetails().length()!=0){
		odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());
		CustomerMasterObject cust=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
		req.setAttribute("cust",cust);
		
		
		String path1=MenuNameReader.getScreenProperties("3032");
		System.out.println("path is---------------"+path1);
		req.setAttribute("flag",path1); 
		  
	  }
	  
	  
	  
	  //to verify
	  if(vodcc.getHidvar().equals("verify")){
		  
		  System.out.println("Inside verify condition");
		  odccmasterobject=fcDelegate.odccData(vodcc.getAcType(),vodcc.getAc_no());
		  //AccountTransObject[] accounttransobject=new AccountTransObject[1];
		 AccountTransObject accounttransobject=new AccountTransObject();
          accounttransobject.setAccNo(accountinfoobject.getAccNo());
          accounttransobject.setAccType(accountinfoobject.getAccType());
          //====HARD CODED FROM HERE========================
          accounttransobject.uv.setVerId(user);
          accounttransobject.uv.setVerTml(tml);
          accounttransobject.uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
          accounttransobject.setCdInd("C");
          accounttransobject.setTransSeqNo(1);
          accounttransobject.setTransType("R");
          accounttransobject.setTransDate(odccmasterobject.getAccOpenDate());
          //accounttransobject.setTransSource("C");
          accounttransobject.setTransMode("C");
          System.out.println("action get tran amount="+accountinfoobject.getTransAmount());
          accounttransobject.setTransAmount(accountinfoobject.getTransAmount());
          accounttransobject.setRef_No(accountinfoobject.getRef_No());
          
          
		 /* accounttransobject[0]=new AccountTransObject();
		  accounttransobject[0].setAccNo(accountinfoobject.getAccNo());
		  accounttransobject[0].setAccType(accountinfoobject.getAccType());
          //====HARD CODED FROM HERE========================
		  accounttransobject[0].uv.setVerId(user);
		  accounttransobject[0].uv.setVerTml(tml);
		  accounttransobject[0].uv.setVerDate(fcDelegate.getSysDate()+""+fcDelegate.getSysTime());
		  accounttransobject[0].setCdInd("C");
		  accounttransobject[0].setTransSeqNo(1);
		  accounttransobject[0].setTransType("R");
		  accounttransobject[0].setTransDate(fcDelegate.getSysDate());
          //accounttransobject.setTransSource("C");
		  accounttransobject[0].setTransMode("C");
          System.out.println("action get tran amount="+accountinfoobject.getTransAmount());
          accounttransobject[0].setTransAmount(accountinfoobject.getTransAmount());
         
		  int result=fcDelegate.verifyODCCMaster(accounttransobject);*/
          
// accounttransobject[0].setRef_No(accountinfoobject.getRef_No());
		  
          System.out.println("Before going for verification");
          int result=fcDelegate.verifyODCCMaster(accounttransobject);
		  if(result==1)
			  req.setAttribute("closingmsg","Account Verified Successfully");
		  else
			  req.setAttribute("closingmsg","Account could not Verified");
	  }
	  }
	  else{
  		
  		req.setAttribute("closingmsg","Account Already Verified");
  	}
  }
                  		  }
                    	
                    	else{
                    		
                    		req.setAttribute("closingmsg","No Records Found");
                    	}
                    String perPath=MenuNameReader.getScreenProperties("3054");
                    System.out.println("PerPath"+perPath);
                    
                    req.setAttribute("error1","false");
                    req.setAttribute("pageId",path);
                    req.setAttribute("sh",fcDelegate.getSHModule(0));
                    req.setAttribute("AcType",fcDelegate.getodccModule(0));
                    req.setAttribute("","acType");
    				System.out.println("At 4845 path:"+map.getPath());
    				
    					
    				
                    	}
                    fcDelegate=new FrontCounterDelegate();
                   
                    req.setAttribute("sh",fcDelegate.getSHModule(0));
                    req.setAttribute("AcType",fcDelegate.getodccModule(0));	
    				return map.findForward(ResultHelp.getSuccess());
                    }
                String Path=MenuNameReader.getScreenProperties("3054");
                System.out.println("PerPath"+Path);
                req.setAttribute("pageId",Path);
    				return map.findForward(ResultHelp.getSuccess());
                }
            catch(Exception e){System.out.println("Exception at 332"+e);}
            return map.findForward(ResultHelp.getSuccess());
        }

        
        
        //----------------------------FOR INTERESTrEGISTER---------------------
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InterestRegisterMenu")){
            try{
            	FCInterestregForm acForm=(FCInterestregForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 4481<<---------->"+MenuNameReader.containsKeyScreen(acForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(acForm.getPageId()));
            	
            	System.out.println("At 4484"+acForm);
               
            	req.setAttribute("pageNum",acForm.getPageId());
               System.out.println("At 4487"+acForm.getPageId());
               logger.info(acForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(acForm.getPageId())){
            	   System.out.println("At 4491---------->");
                   path=MenuNameReader.getScreenProperties(acForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   IntPayObject[] array_inpay=fcDelegate.fcInterestRegister();
                   if(array_inpay!=null)
                	   req.setAttribute("intpayobj",array_inpay);
                   else
                	   req.setAttribute("closingmsg","No Records found:  Interest not calculated for any account");                   System.out.println("path is===4499=== "+path);
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/InterestRegister")){
            try{
            	FCInterestregForm acForm=(FCInterestregForm)form;
            	req.setAttribute("error1","false");
            	
            	System.out.println("At 5675<<---------->"+MenuNameReader.containsKeyScreen(acForm.getPageId()));
            	System.out.println("Jsp Page PAth"+MenuNameReader.getScreenProperties(acForm.getPageId()));
            	
            	System.out.println("At 5678"+acForm);
               
            	req.setAttribute("pageNum",acForm.getPageId());
               System.out.println("At 5681"+acForm.getPageId());
               logger.info(acForm.getPageId()+"This is from Front Counter Log"+map.getPath());
               if(MenuNameReader.containsKeyScreen(acForm.getPageId())){
            	   System.out.println("At 5684---------->");
                   path=MenuNameReader.getScreenProperties(acForm.getPageId());
                   fcDelegate=new FrontCounterDelegate();
                   IntPayObject[] array_inpay=fcDelegate.fcInterestRegister();
                   if(array_inpay!=null){
                	   req.setAttribute("intpayobj",array_inpay);
                	   
                	  /* HSSFWorkbook wb = new HSSFWorkbook();
            		   HSSFSheet sheet = wb.createSheet("Interest Register");
            		   HSSFRow row = sheet.createRow((short)0);
            		   HSSFCell cell = row.createCell((short)0);
            		   cell.setCellValue(1);
            		   row.createCell((short)1).setCellValue("Sr No.");
            		   row.createCell((short)2).setCellValue("Account No.");
            		   row.createCell((short)3).setCellValue("Name");
            		   row.createCell((short)4).setCellValue("Prod1");
            		   row.createCell((short)5).setCellValue("Prod2");
            		   row.createCell((short)6).setCellValue("Prod3");
            		   row.createCell((short)7).setCellValue("Prod4");
            		   row.createCell((short)8).setCellValue("Prod5");
            		   row.createCell((short)9).setCellValue("Prod6");
            		   row.createCell((short)10).setCellValue("IntRate");
            		   row.createCell((short)11).setCellValue("Interest Amount");
            		   row.createCell((short)12).setCellValue("Call By");
            		   
            		   for(int i=0;i<array_inpay.length;i++){ 
            			   HSSFRow row1 = sheet.createRow((short)i+1);
            			   row1.createCell((short)1).setCellValue(i);
            			   row1.createCell((short)2).setCellValue(array_inpay[i].getAccNo());
            			   row1.createCell((short)3).setCellValue(array_inpay[i].getName());
            	         
            	         double[] prod=array_inpay[i].getProducts(); 
            	         for(int j=0,c=4;j<prod.length;j++,c++){
            	        	 
            	        	 row1.createCell((short)c).setCellValue(prod[j]);
            	         
            	         
            	         } 
            	         row1.createCell((short)10).setCellValue(array_inpay[i].getIntRate());
            	         row1.createCell((short)11).setCellValue(array_inpay[i].getIntAmt());
            	         row1.createCell((short)12).setCellValue(array_inpay[i].getCalBy());
            	         
            	         
            	         }
            		  
            		     FileOutputStream fileOut = new FileOutputStream("c:\\Interest Register.xls");
            		    wb.write(fileOut);
            		    fileOut.close(); 
            		    req.setAttribute("closingmsg","Saved to excel file in C:");
            		 asddf*/
                	   res.setContentType("application/.csv");
           	        res.setHeader("Content-disposition", "attachment;filename=InterestRegister.csv");
           	        
           	        java.io.PrintWriter out = res.getWriter();
           	        out.print("\n");
           	        out.print("\n");
           	        out.print("\n");
           	        out.print(",");out.print(",");out.print(",");
           	        out.print("Interest Register ");
           	        out.print("\n");
           	        out.print("\n");
           	   
           	  out.print("Sr No.");out.print(",");
           	 out.print("Account No.");out.print(",");
           	 out.print("Name");out.print(",");
           	 out.print("Prod1");out.print(",");
           	 out.print("Prod2");out.print(",");
           	 out.print("Prod3");out.print(",");
           	 out.print("Prod4");out.print(",");
           	 out.print("Prod5");out.print(",");
           	 out.print("Prod6");out.print(",");
           	 out.print("IntRate");out.print(",");
           	 out.print("Interest Amount");out.print(",");
           	 out.print("Call By");out.print(",");out.print("\n");
           	for(int i=0;i<array_inpay.length;i++){ 
 			   int d=i+1;
           		out.print(d);out.print(",");
           		out.print(array_inpay[i].getAccNo());out.print(",");
           		out.print(array_inpay[i].getName());out.print(",");
 	         
 	         double[] prod=array_inpay[i].getProducts(); 
 	         for(int j=0,c=4;j<prod.length;j++,c++){
 	        	 
 	        	out.print(prod[j]);out.print(",");
 	         
 	         
 	         } 
 	        out.print(array_inpay[i].getIntRate());out.print(",");
 	       out.print(array_inpay[i].getIntAmt());out.print(",");
 	      out.print(array_inpay[i].getCalBy());out.print(",");
 	     out.print("\n"); 
 	         
 	         }
              return null;
                   }
                   else
                	   req.setAttribute("closingmsg","No Records found :  Interest not calculated for any account to be posted");
                   System.out.println("path is===5739=== "+path);
                   req.setAttribute("pageId",path);
                   return map.findForward(ResultHelp.getSuccess());
               }
               else{
                    path=MenuNameReader.getScreenProperties("0000");
                    setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                    return map.findForward(ResultHelp.getError());
               }


           }catch(Exception e){
             path=MenuNameReader.getScreenProperties("0000");
             System.out.println("At 393"+e);
             setErrorPageElements("Unable to process request",req,path);
             return map.findForward(ResultHelp.getError());
           }
           
       }
        


        //-------------------END OF INTEREST REGISTER----------------------
        
        //FOR Updation Code Starts here========================
        
        /* ******************FrontCounter SBUpdation Action Starts here******** */
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SBUpdationMenu"))
		{
	      try{
	    	  SBUpdationFormBean sbUpdation=(SBUpdationFormBean)form;
	       	  sbUpdation.setSysdate(FrontCounterDelegate.getSysDate());
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+sbUpdation);
	    	  System.out.println("the page id is "+sbUpdation.getPageId());
	    	 // req.setAttribute("pagenum", sbUpdation.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(sbUpdation.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(sbUpdation.getPageId());
	    		  fcDelegate=new FrontCounterDelegate();
	    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/SBUpdation")){
			try{
				SBUpdationFormBean sbUpdation=(SBUpdationFormBean)form;
				sbUpdation.setSysdate(FrontCounterDelegate.getSysDate());
		    	  if(MenuNameReader.containsKeyScreen(sbUpdation.getPageId()))
		    	  {
		    		  path=MenuNameReader.getScreenProperties(sbUpdation.getPageId());
		    		  fcDelegate=new FrontCounterDelegate();
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
		    		  if(sbUpdation.getFlag().equalsIgnoreCase("from_acno")){
		    	      //code for acno starts
		    			  String string_modulecode="";
		    			  AccountMasterObject accountmasterobject=null;
		    			  CustomerMasterObject custMasterObj=null;
		    			  ModuleObject array_moduleobject[]=null;
		    			  array_moduleobject=fcDelegate.getComboElements(0);
		    		        if(sbUpdation.getAcNo().trim().length()>0 && !sbUpdation.getAcNo().equals("0"))
		    		        {  
		    		            try
		    		            {
		    		            	System.out.println("MOdulecode==="+sbUpdation.getAcType());	            	
		    		            	accountmasterobject=fcDelegate.getAccountMaster(Integer.parseInt(sbUpdation.getAcNo().trim()),String.valueOf(sbUpdation.getAcType()));
		    		            	if(accountmasterobject!=null){
		    		            	custMasterObj=fcDelegate.getCustomer(accountmasterobject.getCid());
		    		            	}
		    		            	//System.out.println("Account Master Object value is============"+accountmasterobject);

		    		                if(accountmasterobject==null||accountmasterobject.getAccStatus().equals("C"))
		    		                {
		    		                    /**-----Validating whether the Account Exist,Account Closed,Default & Freeze Account,Valid user */
		    		                   
		    		                    if(accountmasterobject==null)
		    		                    	req.setAttribute("msg","Account number not found");
		    		                    else if(accountmasterobject.getAccStatus().equals("C"))
		    		                    	req.setAttribute("msg","Account is Closed");	                   
		    		                }		
		    		                else
		    		                {
		    		                	System.out.println("===========in else condition===============");
		    		                    /**------Checknig whether the Account is already Verified-------*/
		    		                    if(accountmasterobject.getVerified().equals("F"))
		    		                    {
		    		                    	req.setAttribute("msg","Account number is not verified");
		    		                        
		    		                    }

		    		                    if(accountmasterobject.getVerified().equals("T"))
		    		                    {   	sbUpdation.setCid(String.valueOf(accountmasterobject.getCid()));	
		    		                    sbUpdation.setCid(String.valueOf(accountmasterobject.getCid()));
		    		                    
		    		                    
	    					        	   req.setAttribute("personalInfo", custMasterObj);
		    		                        /*obj_personal.showCustomer(accountmasterobject.getCid());					
		    		                        obj_personal.setAddressSelectedItem(accountmasterobject.getMailingAddress());*/

		    		                        /*if(accountmasterobject.getIntrAccType()!=null)
		    		                        {	
		    		                            for(int i=0;i<array_moduleobject_introducer.length;i++)
		    		                                if(accountmasterobject.getIntrAccType().equals(array_moduleobject_introducer[i].getModuleCode()))
		    		                                {	
		    		                                    combo_introduceractype.setSelectedItem(array_moduleobject_introducer[i].getModuleAbbrv());
		    		                                    break;
		    		                                }
		    		                        }
		    		                        
		    		                        txt_introduceracnum.setText(String.valueOf(accountmasterobject.getIntrAccNo()));					
		    		                        if(accountmasterobject.getIntrAccNo()!=0)
		    		                        {
		    		                            try
		    		                            {	
		    		                                accountobject_introducer = commonremote.getAccount(null,accountmasterobject.getIntrAccType(),accountmasterobject.getIntrAccNo(),MainScreen.head.getSysDate());
		    		                                if(accountobject_introducer!=null)	
		    		                                    obj_introducerpersonal.showCustomer(accountobject_introducer.getCid());
		    		                            }catch(Exception exception){exception.printStackTrace();}			
		    		                        }
		    		                        
		    		                        if(accountmasterobject.getSigObj()!=null)
		    		                            obj_signaturedeatils.addCoBorrower(accountmasterobject.getSigObj(),obj_personal.getCategory());

		    		                        if(accountmasterobject.getNom_regno()!=0)
		    		                            obj_nomineescreen.setDetails(accountmasterobject.getNomineeDetails());

		    		                        if(accountmasterobject.getJointCid()!=null)
		    		                            obj_jointholderscreen.addCoBorrower(accountmasterobject.getJointCid(),accountmasterobject.getJointAddrType());
		    						        

*/                                          
		    		                        sbUpdation.setIntroducerAcType(accountmasterobject.getIntrAccType());
		    		                        sbUpdation.setIntroducerAcNo(String.valueOf(accountmasterobject.getIntrAccNo()));
		    		                        boolean chequeValue=accountmasterobject.getChqBKIssue().equals("T")?true:false;
                                            if(chequeValue)
                                            	sbUpdation.setCkBook("ChequeBook");
                                            boolean acFreeze=accountmasterobject.getFreezeInd().equals("T")?true:false;
                                             if(acFreeze){
                                            	 sbUpdation.setFreezed("Freezed");
                                            	 sbUpdation.setFreezeReason(accountmasterobject.getFreezeReason());
                                             }
		    		                        
		    		                        boolean acInoperative=accountmasterobject.getAccStatus().equals("I")?true:false;
		    		                        if(acInoperative)
		    		                        	sbUpdation.setInOperative("InOperative");
		    		                        
		    			                    /*if(accountmasterobject.getAccStatus().equals("I"))
		    			                       checkbox_inoperative_ind.setEnabled(true);*/
		    		                        sbUpdation.setIntPayDate(accountmasterobject.getIntrstPayDate());
		    		                        System.out.println("Date1::::"+accountmasterobject.getIntrstPayDate());
		    		                        sbUpdation.setAcCloseDate(accountmasterobject.getAccCloseDate());
		    		                        sbUpdation.setAcOpenDate(accountmasterobject.getAccOpenDate());
		    		                        sbUpdation.setPbSeq(String.valueOf(accountmasterobject.getPassBkSeq()));
		    		                       // sbUpdation.setIntroducerAcType(accountmasterobject.getIntrAccType());
		    		                        System.out.println("Date2::::"+accountmasterobject.getAccCloseDate());
		    		                        
		    		                        
		    		                    }				                    
		    		                }				
		    		            }catch(AccountNotFoundException exception){
		    		            	req.setAttribute("msg","Account number not found");
		    		            	}
		    		            catch(Exception exception){exception.printStackTrace();}
		    		        }
		    			  //code for acno ends
		    		  
		    				
		    		  
		    		     
		    		  String perPath=MenuNameReader.getScreenProperties("0001");
		    		  req.setAttribute("flag",perPath);
		    		  req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		    	  }
		    		  if(sbUpdation.getFlag().equalsIgnoreCase("from_uid")){
		    			  		  String perPath=MenuNameReader.getScreenProperties("0001");
			    		  req.setAttribute("flag",perPath);
			    		  req.setAttribute("panelName", CommonPanelHeading.getPersonal());  
		    		  }
		    		  //code for update starts here
		    		  if(sbUpdation.getFlag().equalsIgnoreCase("update")){
		    		      AccountMasterObject accountmasterobject=new AccountMasterObject();
						 int int_result=0;
							if(sbUpdation.getAcType()!=null){
							accountmasterobject.setAccType(String.valueOf(sbUpdation.getAcType()));
							}
							if(sbUpdation.getAcNo()!=null){
							accountmasterobject.setAccNo(Integer.parseInt(sbUpdation.getAcNo()));
							}
							if(sbUpdation.getPbSeq()!=null){
							accountmasterobject.setPassBkSeq(Integer.parseInt(sbUpdation.getPbSeq()));
							}
							if(sbUpdation.getCkBook()!=null){
							if(sbUpdation.getCkBook().equalsIgnoreCase("ChequeBook"))
								accountmasterobject.setChqBKIssue("T");
							else
								accountmasterobject.setChqBKIssue("F");
							}
							else
								accountmasterobject.setChqBKIssue("F");
							if(sbUpdation.getIntroducerAcNo()!=null){	
							if(sbUpdation.getIntroducerAcNo().length()>0 && Integer.parseInt(sbUpdation.getIntroducerAcNo().trim())!=0 )
							{
								accountmasterobject.setIntrAccNo(Integer.parseInt(sbUpdation.getIntroducerAcNo()));
								accountmasterobject.setIntrAccType(sbUpdation.getIntroducerAcType());
							}
							else
							{
								accountmasterobject.setIntrAccType(null);
								accountmasterobject.setIntrAccNo(0);
							}
		    		  }
							System.out.println("Freezwd ind==="+sbUpdation.getFreezed());
							if(sbUpdation.getFreezed()!=null){
							if(sbUpdation.getFreezed().equalsIgnoreCase("Freezed"))
								accountmasterobject.setFreezeInd("T");
							else
								accountmasterobject.setFreezeInd("F");
							}
							else
								accountmasterobject.setFreezeInd("F");
							/*if(sbUpdation.getFreezed()!=null){
							if(sbUpdation.getFreezed().equalsIgnoreCase("Freezed"))
								accountmasterobject.setFreezeReason(sbUpdation.getFreezeReason());
							}*/
							if(sbUpdation.getInOperative()!=null){
							if(sbUpdation.getInOperative().equalsIgnoreCase("InOperative"))
							  accountmasterobject.setAccStatus("I"); 
							else
								accountmasterobject.setAccStatus("O");
							}
							else
								accountmasterobject.setAccStatus("O");
							if(sbUpdation.getAcCloseDate()!=null){
							accountmasterobject.setAccCloseDate(sbUpdation.getAcCloseDate());
							}
							if(sbUpdation.getAcOpenDate()!=null){
							accountmasterobject.setAccOpenDate(sbUpdation.getAcOpenDate());
							}
							if(sbUpdation.getIntPayDate()!=null){
							accountmasterobject.setIntrstPayDate(sbUpdation.getIntPayDate());
							}
							accountmasterobject.setIdIssueDate(fcDelegate.getSysDate()); //TO Just get the Transaction Date
							accountmasterobject.uv.setUserId(user);//HARDCODED
							accountmasterobject.uv.setUserTml("LN01");
							
							try{
							int_result=fcDelegate.updateAccountMaster(accountmasterobject);
							if(int_result!=0)
							{
							    /** -----------Account Updated----------*/
								req.setAttribute("msg","Account No "+int_result+" is Updated Successfully");
									
							}
							else
								req.setAttribute("msg","Account No "+int_result+" is not Updated");
							}catch(Exception ex){
								ex.printStackTrace();
							}
		    	  }

		    		  //code for update ends here
		    		 
		    		      		return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
/* ***************************FrontCounter SBUpdation Action Ends Here********* */
        /* *********************FC JointHolders Action Starts Here********** */
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/JointHolders")){
			try{
				JointHoldersFormBean jointHolders=(JointHoldersFormBean)form;
		       	  
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(jointHolders.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(jointHolders.getPageId());
		    		  fcDelegate=new FrontCounterDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
		    		  if(jointHolders.getFlag().equalsIgnoreCase("add")){
		    	      req.setAttribute("AddJointHolder", "Add");
		    		  
		    		  
		    				
		    		  
	  
		    		 
		    	  }
		    		  if(jointHolders.getFlag().equalsIgnoreCase("from_cid")){
		    			  
		    			  CustomerMasterObject custMasterObj=null;
		    			  if(jointHolders.getCid()!=null){
		    			  custMasterObj=fcDelegate.getCustomer(Integer.parseInt(jointHolders.getCid()));
		    			  }
		    			  req.setAttribute("AddJointHolder", "Add");
		    			  req.setAttribute("cid", "ShowCid");
		    			  req.setAttribute("personalInfo", custMasterObj);
		    			  String perPath=MenuNameReader.getScreenProperties("0001");
			    		  req.setAttribute("flag",perPath);
			    		  req.setAttribute("panelName", CommonPanelHeading.getPersonal()); 
		    		  }
		    		  if(jointHolders.getFlag().equalsIgnoreCase("cancel")){
		    			  
		    		  }
		    		  return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
/* ****************************FC JointHolders Action ends here**************** */
        /* ******************FC JointHOlders Updation Action starts here********* */
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/JointHoldersUpdationMenu"))
		{
	      try{
	    	  JointHoldersUpdationFB jointUpdation=(JointHoldersUpdationFB)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+jointUpdation);
	    	  System.out.println("the page id is "+jointUpdation.getPageId());
	    	  req.setAttribute("pagenum", jointUpdation.getPageId());
	    	  System.out.println("MenuNameReader.containsKeyScreen(jointUpdation.getPageId()===> "+MenuNameReader.containsKeyScreen(jointUpdation.getPageId()));
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(jointUpdation.getPageId()))
	    	  {
	    		 
	    		  path=MenuNameReader.getScreenProperties("3044");
	    		  fcDelegate=new FrontCounterDelegate();
	    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
	    		  String perPath=MenuNameReader.getScreenProperties("3044");
	    		  req.setAttribute("flag",perPath);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("cid", "Show");
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/JointHoldersUpdation")){
			try{
				JointHoldersUpdationFB jointUpdation=(JointHoldersUpdationFB)form;
		       	  
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(jointUpdation.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(jointUpdation.getPageId());
		    		  fcDelegate=new FrontCounterDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
		    		  if(jointUpdation.getFlag().equalsIgnoreCase("from_acno")){
		    			  System.out.println("getFlag value is--------------->"+jointUpdation.getFlag());
		    			  amObj=fcDelegate.getAccountMaster(Integer.parseInt(jointUpdation.getAcno()),jointUpdation.getAcType());
		    			  if(amObj!=null){
		    				  System.out.println("At 7272 in JointHolderUpdation");
		    				 if(amObj.getAccStatus().equals("C"))
		    					 req.setAttribute("msg","Account Closed");
		    				 else if(amObj.getFreezeInd().equals("T"))
		    					 req.setAttribute("msg","Account Freezed");
		    				 else{
		    					 if(amObj.getJointCid()!=null&&amObj.getJointCid().length>0)
		    					 {	System.out.println("At 7279==================>");
		    						 cmObj=fcDelegate.getCustomer(amObj.getJointCid()[0]);
		    						 jointUpdation.setJointhcid(String.valueOf(amObj.getJointCid()[0]));
		    						 req.setAttribute("cust",cmObj);
		    					 }
		    					 else{
		    						 req.setAttribute("msg","No JiontHolder"); 
		    					 }
		    					 
		    					 
		    					 if(jointUpdation.getSubflag().equalsIgnoreCase("submit")){
		    				
		    						 SignatureInstructionObject[] signObject=fcDelegate.getSignature(amObj.getAccNo(),amObj.getAccType());
		    						 amObj.setSigObj(signObject);
		    							
		    						// amObj.setAccType(String.valueOf(string_modulecode));
		    						 //amObj.setAccNo(Integer.parseInt(txt_accountnum.getText()));				
		    						 if(jointUpdation.getDelflag().equals("delete")){
		    							 amObj.setJointCid(null);
		    							 amObj.setJointAddrType(null); 
		    							 
		    							 
		    						 }
		    						 else{
		    						 
		    						 amObj.setNoOfJointHolders(1);
		    							System.out.println("CID==============="+jointUpdation.getJointhcid());
		    							//amObj.setCid(cid);							
		    								//System.out.println("obj_jointholderscreen.getJointAddress()===============>"+obj_jointholderscreen.getJointAddress()[0]);
		    							int[] jcid=new int[1];	
		    							jcid[0]=Integer.parseInt(jointUpdation.getJointhcid());
		    							amObj.setJointCid(jcid);
		    							int[] addtyp=new int[1];
		    							addtyp[0]=2;
		    								amObj.setJointAddrType(addtyp);
		    					 }
		    							String str=fcDelegate.storeJointHolderUpdation(amObj);
		    							req.setAttribute("msg",str);
		    							jointUpdation.setFlag("");
		    							jointUpdation.setSubflag("");
		    					 }
		    				 
		    				 
		    				 }
		    			  }
		    			  else{
		    				  req.setAttribute("msg","Account Not Found");
		    			  }
		    			  String perPath=MenuNameReader.getScreenProperties("3043");
			    		  req.setAttribute("flag",perPath);
			    		  req.setAttribute("AcType", fcDelegate.getComboElements(0));
		    		  }
		    	      return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
        
       
 //*********************ODCCSBCAUpdation ***************************//
        
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccSbcaUpdationMenu"))
		{
	      try{
	    	  ODCCSBCAUpdationForm osUpdation=(ODCCSBCAUpdationForm)form;
	       	  osUpdation.setSysdate(FrontCounterDelegate.getSysDate());
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+osUpdation);
	    	  System.out.println("the page id is "+osUpdation.getPageId());
	    	  req.setAttribute("pagenum", osUpdation.getPageId());
	    	  System.out.println("MenuNameReader.containsKeyScreen(jointUpdation.getPageId()===> "+MenuNameReader.containsKeyScreen(osUpdation.getPageId()));
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(osUpdation.getPageId()))
	    	  {
	    		 
	    		  path=MenuNameReader.getScreenProperties("3057");
	    		  fcDelegate=new FrontCounterDelegate();
	    		  req.setAttribute("AcType", fcDelegate.getSBCAODCC(0));
	    		  String perPath=MenuNameReader.getScreenProperties("3057");
	    		  req.setAttribute("flag",perPath);
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  req.setAttribute("cid", "Show");
	    		  req.setAttribute("sh",fcDelegate.getSHModule(0));
	    		  loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
  				req.setAttribute("purpose",loanpurposeobject);
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
        else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccSbcaUpdation"))
		{
  	      try{
  	    	  ODCCSBCAUpdationForm osUpdation=(ODCCSBCAUpdationForm)form;
  	       	  osUpdation.setSysdate(FrontCounterDelegate.getSysDate());
  	    	  System.out.println("******************************=="+form);
  	    	  System.out.println("the page path is "+map.getPath().trim());
  	    	  System.out.println("******************************=="+osUpdation);
  	    	  System.out.println("the page id is "+osUpdation.getPageId());
  	    	  req.setAttribute("pagenum", osUpdation.getPageId());
  	    	  System.out.println("MenuNameReader.containsKeyScreen(jointUpdation.getPageId()===> "+MenuNameReader.containsKeyScreen(osUpdation.getPageId()));
  		       
  	    	  
  	    	  if(MenuNameReader.containsKeyScreen(osUpdation.getPageId()))
  	    	  {
  	    		 
  	    		  path=MenuNameReader.getScreenProperties("3057");
  	    		  fcDelegate=new FrontCounterDelegate();
  	    		  req.setAttribute("AcType", fcDelegate.getSBCAODCC(0));
  	    		  String perPath=MenuNameReader.getScreenProperties("3057");
  	    		  req.setAttribute("flag",perPath);
  	    		  System.out.println("the path from MenuNameReader is "+path);
  	    		  req.setAttribute("pageId",path);
  	    		  req.setAttribute("cid", "Show");
  	    		req.setAttribute("sh",fcDelegate.getSHModule(0));
  	    		  
  	    		//for SB/CA Accounts
  	    		 if(osUpdation.getAcType().trim().startsWith("100")){
  	    			 
  	    			amObj=fcDelegate.getAccountMaster(Integer.parseInt(osUpdation.getAcno()),osUpdation.getAcType().trim());
  	    			if(amObj!=null){
  	    				if(amObj.getAccStatus().equals("C")){
  	    					req.setAttribute("msg","Account No:  "+osUpdation.getAcno()+"  is Closed");
  	    					osUpdation.setUpdt("");
  	    				}
  	    				else if(amObj.getFreezeInd().equals("T")){
  	    					req.setAttribute("msg","Account No:  "+osUpdation.getAcno()+"  is Freezed");
  	    					osUpdation.setUpdt("");
  	    				}
  	    				else{
  	    					
  	    					//here is the actual code
  	    					if(osUpdation.getDetail().trim().equals("Jointholders")){
  	    						req.setAttribute("jointholder","jointholder");
  	    						
  	    						if(amObj.getJointCid()!=null){
  	    							req.setAttribute("jointarray",amObj.getJointCid());
  	    						}
  	    						else{
  	    							req.setAttribute("msg","Click on Add button to Add JointHolder");
  	    						}
  	    						
  	    						if(osUpdation.getAddjoint()!=null&&osUpdation.getAddjoint().equals("ADDJOINT")){
  	    							
  	    							req.setAttribute("addjoint","addjoint");
  	    						if(osUpdation.getJointcid()!=null&&osUpdation.getJointcid().length()>0){
  	    							try{
  	    	                 	    	cmObj=fcDelegate.getCustomer(Integer.parseInt(osUpdation.getJointcid().trim()));
  	    	                 	    	if(cmObj!=null)
  	    	                 	    		osUpdation.setJointname(cmObj.getName());
  	    	                 	    	}
  	    	                 	    	catch(Exception ex){
  	    	                 	    		
  	    	                 	    		req.setAttribute("msg","Customer not found");
  	    	                 	    		
  	    	                 	    	}
  	    							
  	    							
  	    						}
  	    						else{
  	    							osUpdation.setUpdt("");
  	    						}
  	    							
  	    						}
  	    						
  	    					}
  	    					
  	    					
  	    				}
  	    			}
  	    			else{
  	    				
  	    				req.setAttribute("msg","Account No Does not exist");
  	    			}
  	    		 }
  	    		
  	    		
  	    		  //for OD/CC
  	    		  if(osUpdation.getAcType().trim().startsWith("101")){
  	    			  
  	    			  
  	    		  loanpurposeobject=(LoanPurposeObject[])fcDelegate.loanpurpose();
    				req.setAttribute("purpose",loanpurposeobject);
    				
    				odccmasterobject=fcDelegate.odccData(osUpdation.getAcType(),osUpdation.getAcno());
    				if(odccmasterobject!=null){
    					
    					
    					
    					
    					if(osUpdation.getDetail().trim().equals("Application")){
    						req.setAttribute("Appln","Appln");
    						
    					osUpdation.setAppdate(odccmasterobject.getApplicationDate());
    					osUpdation.setPurpose(String.valueOf(odccmasterobject.getPurposeCode()));
    					osUpdation.setSrlno(String.valueOf(odccmasterobject.getApplicationSrlNo()));
    					osUpdation.setLoanamount(String.valueOf(odccmasterobject.getRequiredAmount()));
    					osUpdation.setIntType(String.valueOf(odccmasterobject.getInterestRateType()));
    					}
    				
    					if(osUpdation.getDetail().trim().equals("CoBorrower")){
    						req.setAttribute("coborrower","coborrower");
    						req.setAttribute("cobordetails",odccmasterobject.getCoBorrowers());
    						if(osUpdation.getAddco()!=null&&osUpdation.getAddco().trim().equals("addcoborrower")){
    							req.setAttribute("addcoborrower","addcoborrower");
    							if(osUpdation.getCoacno()!=null&& osUpdation.getCoacno().length()>0){
    								accountObj=fcDelegate.getOdccAccount(osUpdation.getCoacType(),osUpdation.getCoacno().trim());
                              		if(accountObj!=null){
    								
                              			if(accountObj.getAccStatus().equals("C")){
                              				req.setAttribute("msg","ShareAccount No: "+osUpdation.getCoacno()+"  is closed");
                              				osUpdation.setUpdt("");
                              			}
                              			else if(accountObj.getFreezeInd().equals("T")){
                              				req.setAttribute("msg","ShareAccount No: "+osUpdation.getCoacno()+"  is Freezed");
                              				osUpdation.setUpdt("");
                              			}
                              			else{
                              				
                              			}
                              		}	
                              		else{
                              			req.setAttribute("msg","ShareAccount No: "+osUpdation.getCoacno()+" does not exist");
                              			osUpdation.setUpdt("");
                              		}
                              		
    							}
    							
    						}
    					}
    				
    				}
    				else{
    					req.setAttribute("msg","Account No Does not exist");
    				}
    				
  	    	  }
  	    		  //for Updation
  	    		if(osUpdation.getUpdt()!=null&&osUpdation.getUpdt().trim().equals("update"))
				{
  	    			if(osUpdation.getDetail().trim().equals("Jointholders")){
  	    				SignatureInstructionObject[] signObject=fcDelegate.getSignature(amObj.getAccNo(),amObj.getAccType());
  	    				if(signObject!=null&&signObject.length>0){
  	    				signObject[0].setTypeOfOperation(osUpdation.getSigninst().trim());
  	    				}
  	    				amObj.setSigObj(signObject);
  	    				
  	    				System.out.println("updating JointHolder--------6767-");
  	    				if(amObj.getJointCid()!=null){
  	    					
  	    					
  	    				int j=amObj.getJointCid().length;
  	    				
  	    				amObj.setNoOfJointHolders(j+1);
  	    				System.out.println("updating JointHolder---6770--");
						//System.out.println("CID==============="+jointUpdation.getJointhcid());
						//amObj.setCid(cid);							
							//System.out.println("obj_jointholderscreen.getJointAddress()===============>"+obj_jointholderscreen.getJointAddress()[0]);
						int[] jcid=new int[j+1];	
						if(j>0){
							System.out.println("updating JointHolder-----1----");
							for(int b=0;b<amObj.getJointCid().length;b++){
								
								System.out.println("updating JointHolder--0000--");
								jcid[b]=amObj.getJointCid()[b];
							}
						}
						System.out.println("updating JointHolder-----2----"+j);
							jcid[j]=Integer.parseInt(osUpdation.getJointcid());
							amObj.setJointCid(jcid);
							
							int[] addtyp=new int[j+1];
							for(int a=0;a<addtyp.length;a++){
								addtyp[a]=2;
							}
							amObj.setJointAddrType(addtyp);
  	    				}
  	    				else{
  	    					int[] jcid=new int[1];
  	    					jcid[0]=Integer.parseInt(osUpdation.getJointcid());
  	    					amObj.setJointCid(jcid);
  	    					int[] addtyp=new int[1];
  	    					addtyp[0]=2;
  	    					amObj.setJointAddrType(addtyp);
  	    				}
						
						
							
							System.out.println("updating JointHolder------3---");
							String str=fcDelegate.storeJointHolderUpdation(amObj);
							req.setAttribute("msg",str);
							
  	    			}
  	    			
  	    			if(osUpdation.getDetail().trim().equals("Application")){
  	    				
					odccmasterobject.setApplicationDate(osUpdation.getAppdate());
					odccmasterobject.setPurposeCode(Integer.parseInt(osUpdation.getPurpose()));
					odccmasterobject.setApplicationSrlNo(Integer.parseInt(osUpdation.getSrlno()));
					odccmasterobject.setRequiredAmount(Double.parseDouble(osUpdation.getLoanamount()));
					odccmasterobject.setInterestRateType(Integer.parseInt(osUpdation.getIntType()));
					int int_update=fcDelegate.updateODCCMaster(odccmasterobject,0);
					if(int_update!=0)
					{
					    /** -----------Account Updated----------*/
						req.setAttribute("msg","Account No "+int_update+" is Updated Successfully");
							
					}
					else
						req.setAttribute("msg","Account No "+int_update+" is not Updated");
		  
					
				}	
  	    			
  	    			//for coborrower updation
  	    			else if(osUpdation.getDetail().trim().equals("CoBorrower")){
  	    				int x=odccmasterobject.getCoBorrowers().size()+1;
  	    				int b=x;
  	    				cobor=new Vector(0,x);
  	    				cobor.add(osUpdation.getCoacType()+" "+osUpdation.getCoacno());
  	    				for(int c=0;c<b-1;c++){
  	    					cobor.add(odccmasterobject.getCoBorrowers().get(c).toString().substring(0,7)+" "+odccmasterobject.getCoBorrowers().get(c).toString().substring(8));
  	    					System.out.println("Adding Coborrower----1----");
  	    				}
  	    				odccmasterobject.setCoBorrowers(cobor);
  	    				odccmasterobject.setNoOfCoBorrowers(x);
  	    				System.out.println("Adding Coborrower---2---");
  	    				int int_update=fcDelegate.updateODCCMaster(odccmasterobject,3);
  						if(int_update!=0)
  						{
  						    /** -----------Account Updated----------*/
  							req.setAttribute("msg","Account No "+int_update+" is Updated Successfully");
  								
  						}
  						else
  							req.setAttribute("msg","Account No "+int_update+" is not Updated");
  			  
  	    			}
  	    			
  	    			
  	    			
					osUpdation.setUpdt("");
				}
  	    		  return map.findForward(ResultHelp.getSuccess());
  	    	  
  	    	  }
  	    	  else
  	    	  {
  	    		  path=MenuNameReader.getScreenProperties("0000");
  	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
  	    		  return map.findForward(ResultHelp.getError());
  	    	  }
  	      }
  	      catch(Exception e)
  	      {
  	    	  path=MenuNameReader.getScreenProperties("0000");
  	    	  setErrorPageElements(""+e, req, path);
  	    	  return map.findForward(ResultHelp.getError());
  	      }
  		}
        
        
        
/* *****************************FC JointHolders Updation Action Ends here *************** */
        /* *****************FC ODCC Updation Action starts here*************************** */
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccUpdationMenu"))
		{
	      try{
	    	  ODCCUpdationFB odccUpdation=(ODCCUpdationFB)form;
	    	  odccUpdation.setSysdate(FrontCounterDelegate.getSysDate());
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+odccUpdation);
	    	  System.out.println("the page id is "+odccUpdation.getPageId());
	    	  req.setAttribute("pagenum", odccUpdation.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(odccUpdation.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(odccUpdation.getPageId());
	    		  fcDelegate=new FrontCounterDelegate();
	    		  System.out.println("Before calling the delegate method"+path);
	    		  //req.setAttribute("AcType", fcDelegate.getodccModule(0));
	    		  System.out.println("After coming back to the Action class"+path);
	    		  req.setAttribute("pageId",path);
	              req.setAttribute("AcType",fcDelegate.getodccModule(0));
	              return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/OdccUpdation")){
			try{
				ODCCUpdationFB odccUpdation=(ODCCUpdationFB)form;
		       	 odccUpdation.setSysdate(FrontCounterDelegate.getSysDate());		    	 
		    	  if(MenuNameReader.containsKeyScreen(odccUpdation.getPageId()))
		    	  {
		    		  
		    		  path=MenuNameReader.getScreenProperties(odccUpdation.getPageId());
		    		  fcDelegate=new FrontCounterDelegate();
		    		  ODCCMasterObject odccmasterobject=null;
		    		  CustomerMasterObject custMasterObj=null;
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  if(odccUpdation.getFlag()!=null&&odccUpdation.getFlag().equalsIgnoreCase("update"))
		    		  {
		    			  if(!odccUpdation.getAcNo().trim().equals("0")||Integer.parseInt(odccUpdation.getAcNo().trim())>0 && odccUpdation.getAcType()!=null)  
		    			  {
		    				  ODCCMasterObject odccMasterObj=new ODCCMasterObject();
		    				    if(odccUpdation.getAcType()!=null){
		    				    	odccMasterObj.setModuleCode(Integer.parseInt(odccUpdation.getAcType()));
		    				    	odccMasterObj.setAccType(odccUpdation.getAcType());
		    				    }
		    				    if(odccUpdation.getAcNo()!=null){
		    				    	odccMasterObj.setAccNo(Integer.parseInt(odccUpdation.getAcNo()));
		    				    }
		    				    odccMasterObj.setTransDate(fcDelegate.getSysDate());
			                    
			                    	if(odccUpdation.getCkBook()!=null&&odccUpdation.getCkBook().equalsIgnoreCase("ChequeBook"))
			                    		odccMasterObj.setChqBKIssue("T");
			                    	else
			                    		odccMasterObj.setChqBKIssue("F");
			                    
			                    
			                    if(odccUpdation.getIntPayDate()!=null){
			                    String intdate=odccUpdation.getIntPayDate().toString();
			                    System.out.println("................."+intdate);
			                    odccMasterObj.setInterestUpto(intdate);
			                    }
			                    //odccmasterobject.setInterestUpto(txt_int_pbl_date.getText().toString());
			                    
			                    /*odccmasterobject.setApplicationSrlNo(obj_applicationdet.getApplicationNo());
			                    odccmasterobject.setApplicationDate(obj_applicationdet.getApplicationDate());
			                    odccmasterobject.setRequiredAmount(obj_applicationdet.getRequestedAmount());
			                    odccmasterobject.setPurposeCode(obj_applicationdet.getPurposeCode());*/
			                    if(odccUpdation.getPbSeq()!=null){
			                    	odccMasterObj.setPassBookSeq(Integer.parseInt(odccUpdation.getPbSeq()));
			                    }
			                    
				                   // odccmasterobject.setInterestUpto(txt_int_pbl_date.getText().toString());
			                      
			                    	  if(odccUpdation.getInOperative()!=null&&odccUpdation.getInOperative().equalsIgnoreCase("InOperative"))
			                    		  odccMasterObj.setAccountStatus("I");
			                    	  else
			                    		  odccMasterObj.setAccountStatus("O");
			                      
			                      
			                    	  if(odccUpdation.getDefaultInd()!=null&&odccUpdation.getDefaultInd().equalsIgnoreCase("DefaultInd"))
			                    		  odccMasterObj.setDefaultInd("T");
			                    	  else
			                    		  odccMasterObj.setDefaultInd("F");
			                      
			                      
			                    	  if(odccUpdation.getFreezed()!=null&&odccUpdation.getFreezed().equalsIgnoreCase("Freezed"))
			                    		  odccMasterObj.setFreezeInd("T");
			                    	  else
			                    		  odccMasterObj.setFreezeInd("F");
			                      
			                      if(odccUpdation.getAcOpenDate()!=null)
			                    	  odccMasterObj.setAccOpenDate(odccUpdation.getAcOpenDate());
				                  if(odccUpdation.getAcCloseDate()!=null) 
				                	  odccMasterObj.setClosedt(odccUpdation.getAcCloseDate());	                   	                   
				                    //odccmasterobject.setInterestRateType(obj_applicationdet.getInterestRateType());
				                  if(odccUpdation.getPriority()!=null){
				                	  if(odccUpdation.getPriority().equalsIgnoreCase("Priority"))
				                		  odccMasterObj.setPrioritySectorCode(1);
				                	  else
				                		  odccMasterObj.setPrioritySectorCode(0);
				                	  if(odccUpdation.getPriority().equalsIgnoreCase("Priority"))
				                		  odccMasterObj.setPrioritySector(true);
				                	  else
				                		  odccMasterObj.setPrioritySector(false);
				                  }
				                  if(odccUpdation.getWeak()!=null){
				                	  if(odccUpdation.getWeak().equalsIgnoreCase("Weak"))
				                		  odccMasterObj.setWeakerSection(true);
				                	  else
				                		  odccMasterObj.setWeakerSection(false);
				                  }
				                    
				                    /*odccmasterobject.setRelative(obj_applicationdet.isRelative()?"T":"F");		            
				                    if(obj_applicationdet.isRelative()){
				                        odccmasterobject.setDirectorCode(obj_applicationdet.getDirectorCode());
				                        odccmasterobject.setRelative(obj_applicationdet.getRelation());
				                    }
				                        
				                    */	                    	                    
				                  odccMasterObj.setLimitUpto(odccUpdation.getPeriod());	                                        
				                    int int_update=fcDelegate.updateODCCMaster(odccMasterObj,0);
									if(int_update!=0)
									{
									    /** -----------Account Updated----------*/
										req.setAttribute("msg","Account No "+int_update+" is Updated Successfully");
											
									}
									else
										req.setAttribute("msg","Account No "+int_update+" is not Updated");
		    			  }
		    			  req.setAttribute("AcType",fcDelegate.getodccModule(0));
		    		  }
		    		  
		    		  
		    		  if(odccUpdation.getFlag().equalsIgnoreCase("from_acno"))
		    		  {
		    			  //focus lost of acno starts
		    			  if(odccUpdation.getAcNo().length()>0 && Integer.parseInt(odccUpdation.getAcNo())>0 )
		  				{
		  					try
		  					{					
		  						String string_modulecode=odccUpdation.getAcType();
		  						
		  						odccmasterobject=fcDelegate.getODCCMaster(Integer.parseInt(odccUpdation.getAcNo()),String.valueOf(odccUpdation.getAcType()));
		  						custMasterObj=fcDelegate.getCustomer(odccmasterobject.getCustomerId());
		  						if(odccmasterobject==null||odccmasterobject.getAccountStatus().equals("C"))
		  						{
		  							/**-----Validating whether the Account Exist,Account Closed,Default & Freeze Account,Valid user */
		  								                    
		  	                    				
		  							if(odccmasterobject==null)
		  								req.setAttribute("msg","Account number not found");
		  							else if(odccmasterobject.getAccountStatus().equals("C"))
		  								req.setAttribute("msg","Account is Closed");	                    
		  						}
		  						else
		  						{						 	
		  							if(odccmasterobject.uv.getVerId()==null)
		  							{
		  								
		  								req.setAttribute("msg","Given account is not yet Verified");
		  							}
		  							else if(odccmasterobject.uv.getVerTml()!=null)	
		  							{	   
		  								
		  								System.out.println("1111111111111111111111111111111");
		  				           
		  								/*for(int i=0;i<array_moduleobject_share.length;i++)
		  									if(array_moduleobject_share[i].getModuleCode().equals(odccmasterobject.getShareAccType()))
		  									{	
		  										combo_share_type.setSelectedItem(array_moduleobject_share[i].getModuleAbbrv());
		  										break;
		  									}*/
		  						
		  								odccUpdation.setShNo(String.valueOf(odccmasterobject.getShareAccNo()));
		  								odccUpdation.setCid(String.valueOf(odccmasterobject.getCustomerId()));
		  								boolean chequebook=odccmasterobject.getChqBKIssue().equals("T")?true:false;
		  								if(chequebook)
		  									odccUpdation.setCkBook("ChequeBook");
		  								boolean isFreezed=odccmasterobject.getFreezeInd().equals("T")?true:false;
		  								if(isFreezed)
		  									odccUpdation.setFreezed("freezed");
		  								boolean isDefault=odccmasterobject.getDefaultInd().equals("T")?true:false;
		  								if(isDefault)
		  									odccUpdation.setDefaultInd("DefaultInd");
		  								
		  								odccUpdation.setIntPayDate(String.valueOf(odccmasterobject.getInterestUpto()));
		  								
		  								odccUpdation.setAcOpenDate(odccmasterobject.getAccOpenDate());
		  								odccUpdation.setAcCloseDate(odccmasterobject.getClosedt());
		  								odccUpdation.setPbSeq(String.valueOf(odccmasterobject.getPassBookSeq()));
		  								if(odccmasterobject.isWeakerSection())
		  									odccUpdation.setWeak("WeaK");
		  								if(odccmasterobject.isPrioritySector())
		  									odccUpdation.setPriority("Priority");								
		  								odccUpdation.setPeriod(odccmasterobject.getLimitUpto());
		  								if(odccmasterobject.getAccountStatus().equals("I")?true:false)
		  									odccUpdation.setInOperative("InOperative");
		  								
		  								Object obj[][]=fcDelegate.getLoanDetails(odccmasterobject.getShareAccType(),odccmasterobject.getShareAccNo());
		  								System.out.println("Today---------------------------------------------");
		  								odccUpdation.setShVal(obj[0][1].toString());
		  								odccUpdation.setShType(odccmasterobject.getShareAccType());
		  											}				                
		  						}				
		  					}catch(Exception exception){exception.printStackTrace();}
		  			       req.setAttribute("personalInfo", custMasterObj);
				    		  String perPath=MenuNameReader.getScreenProperties("0001");
				    		  req.setAttribute("flag",perPath);
				    		  req.setAttribute("panelName", CommonPanelHeading.getPersonal());
		  				}
		  			

		    			  //focus lost of acno ends
		    			  
		    			  req.setAttribute("AcType",fcDelegate.getodccModule(0));
		    		  }
		    	      return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  e.printStackTrace();
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
        /* *****************FC ODCC Updation Action ends here*************************** */
        /* *****************FC Employment Details Action Starts here******************* */
        if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/EmpDetailsMenu"))
		{
	      try{
	    	  EmpDetailsFB empDet=(EmpDetailsFB)form;
	       	  
	    	  System.out.println("******************************=="+form);
	    	  System.out.println("the page path is "+map.getPath().trim());
	    	  System.out.println("******************************=="+empDet);
	    	  System.out.println("the page id is "+empDet.getPageId());
	    	  req.setAttribute("pagenum", empDet.getPageId());
	    	  
		       
	    	  
	    	  if(MenuNameReader.containsKeyScreen(empDet.getPageId()))
	    	  {
	    		  path=MenuNameReader.getScreenProperties(empDet.getPageId());
	    		  fcDelegate=new FrontCounterDelegate();
	    		  req.setAttribute("AcType", fcDelegate.getodccModule(0));
	    		  
	    		  System.out.println("the path from MenuNameReader is "+path);
	    		  req.setAttribute("pageId",path);
	    		  
	    		  return map.findForward(ResultHelp.getSuccess());
	    	  }
	    	  else
	    	  {
	    		  path=MenuNameReader.getScreenProperties("0000");
	    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    		  return map.findForward(ResultHelp.getError());
	    	  }
	      }
	      catch(Exception e)
	      {
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements(""+e, req, path);
	    	  return map.findForward(ResultHelp.getError());
	      }
		}
		
		
		else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/EmpDetails")){
			try{
				EmpDetailsFB empDet=(EmpDetailsFB)form;
		       	  
		    	 
			      			 
				  
		    	  
		    	  if(MenuNameReader.containsKeyScreen(empDet.getPageId()))
		    	  {
		    		  ODCCMasterObject odccmasterobject=null;
	    			  String limit_upto=null;
	    				double req_amt=0,prev_stock_value=0;
	    				boolean sanc_ver=false;
		    		  int emp_customer_id=0;
		    		  String type_of_income=null;
		    		  path=MenuNameReader.getScreenProperties(empDet.getPageId());
		    		  fcDelegate=new FrontCounterDelegate();
		    		  
		    		  System.out.println("the path from MenuNameReader is "+path);
		    		  req.setAttribute("pageId",path);
		    		  
		    		  req.setAttribute("AcType", fcDelegate.getodccModule(0));
		    		  //on focus lost of the Acno starts here
		    		  if(empDet.getFlag().equalsIgnoreCase("from_acno")){
		    			  
			    			if(empDet.getAcNo().length()>0 && Integer.parseInt(empDet.getAcNo())>0 )
			    			{
			    				
			    				try
			    				{
			    					
			    					String string_modulecode=empDet.getAcType();
			    					System.out.println("In Employment Action the value of Actype is===="+string_modulecode);
			    					odccmasterobject=fcDelegate.getODCCMaster(Integer.parseInt(empDet.getAcNo()),String.valueOf(string_modulecode));
			    					limit_upto=odccmasterobject.getLimitUpto();
			    					req_amt=odccmasterobject.getRequiredAmount();
			    					sanc_ver=odccmasterobject.isSanctionVerified();
			    					if(odccmasterobject==null||odccmasterobject.getAccountStatus().equals("C"))
			    					{
			    						/**-----Validating whether the Account Exist,Account Closed,Default & Freeze Account,Valid user */
			    							                    
			                        				
			    						if(odccmasterobject==null)
			    							empDet.setValidations("Account number not found");
			    						else if(odccmasterobject.getAccountStatus().equals("C"))
			    							empDet.setValidations("Account is Closed");	                    
			    					}
			    					else
			    					{						 	
			    						if(odccmasterobject.uv.getVerId()==null)
			    						{
			    								
			    							empDet.setValidations("Given account is not yet Verified");
			    						}
			    						else if(odccmasterobject.uv.getVerTml()!=null)	
			    						{		
			    							IncomeObject incomeObj[]=null;
			    							if(odccmasterobject.getIncomeDetails()!=null)
			    							{
			    								incomeObj=odccmasterobject.getIncomeDetails();
			    								emp_customer_id=odccmasterobject.getCustomerId();
			    								prev_stock_value=incomeObj[0].getStockValue();
			    								System.out.println("Previous Stock Valueeeeee=="+prev_stock_value);
			    								System.out.println("Type of Incomeof Income Object is===="+incomeObj[0].getTypeOfIncome());
			    								type_of_income=incomeObj[0].getTypeOfIncome();
			    								if(type_of_income==null || type_of_income.equalsIgnoreCase(" ") || type_of_income.length()==0){
			    								  System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<No Employment Details>>>>>>>>>>>");
			    								}else{
			    								if(type_of_income.equalsIgnoreCase("Business")){
			    								empDet.setBusinessAddr(incomeObj[0].getAddress());
			    								empDet.setBusinessName(incomeObj[0].getName());
			    								empDet.setBusinessNature(incomeObj[0].getNature());
			    								empDet.setStockValue(String.valueOf(incomeObj[0].getStockValue()));
			    								empDet.setGoodsType(incomeObj[0].getTypeOfGoods());
			    								empDet.setGoodsCondition(incomeObj[0].getGoodsCondition());
			    								empDet.setTurnover(String.valueOf(incomeObj[0].getTurnOver()));
			    								empDet.setBusinessPhno(String.valueOf(incomeObj[0].getPhNo()));
			    								empDet.setBusMonthlyIncome(String.valueOf(incomeObj[0].getIncome()));
			    								empDet.setBusMonthlyExpenditure(String.valueOf(incomeObj[0].getExpenditure()));
			    								empDet.setBusNetMonthlyIncome(String.valueOf(incomeObj[0].getNetIncome()));
			    					System.out.println("Net Monthly Income issssssssssssssssssssssss"+incomeObj[0].getNetIncome());
			    								
			    								}
			    								}
			    							}
			    							
			    						}
			    						
			    					}	
			    					if(type_of_income!=null)
			    					       req.setAttribute("Employment", type_of_income);
			    				}catch(AccountNotFoundException ae){
			    					
			    					empDet.setValidations("Account Not Found");
			    				}
			    				catch(Exception exception){exception.printStackTrace();}
			    			}
			    			//enableUpdateButton(false);
			    		}
				
			    		  
			    System.out.println("At 7596 empDet.getBusinessNature()"+empDet.getBusinessNature());
		    		  
		    		  //on focus lost of the acno ends here
		    		  //code for updating the Odcc Employment Details starts
		    		  if(empDet.getFlag().equalsIgnoreCase("update")){
		    			  try{
		    			  System.out.println("Start of the Update in the Action");
		    			  IncomeObject inObj[]=new IncomeObject[3];
		    			  inObj[2]=new IncomeObject();
		    			  ODCCMasterObject odccmasterobj=fcDelegate.getODCCMaster(Integer.parseInt(empDet.getAcNo()),empDet.getAcType());
		    			 // ODCCMasterObject odccmasterobj=new ODCCMasterObject();
		    			  System.out.println("OdccMasterObject valuee issssssss"+odccmasterobj);
		    			  odccmasterobj.setAccNo(Integer.parseInt(empDet.getAcNo()));
		    			  System.out.println("\\\\\\\\\\\\\\\\\\odccmasterobj.getAccNo"+odccmasterobj.getAccNo());
		    			  odccmasterobj.setModuleCode(Integer.parseInt(empDet.getAcType()));
		    			  System.out.println();
		    			  odccmasterobj.setCustomerId(fcDelegate.getODCCMaster(Integer.parseInt(empDet.getAcNo()),String.valueOf(empDet.getAcType())).getCustomerId());
		    			  odccmasterobj.setSanctionVerified(sanc_ver);
		    			  System.out.println("After Assingning the values to the Odccmaster Obj");
		    			  inObj[2].setTypeOfIncome("Business");
		    			  System.out.println("valu of inObj[2].getTypeOfIncome"+inObj[2].getTypeOfIncome());
		    			  inObj[2].setName(empDet.getBusinessName());
		    			  inObj[2].setAddress(empDet.getBusinessAddr());
		    			  inObj[2].setPhNo(empDet.getBusinessPhno());
		    			  inObj[2].setNature(empDet.getBusinessNature());
		    			  inObj[2].setStockValue(Double.parseDouble(empDet.getStockValue()));
		    			  inObj[2].setTypeOfGoods(empDet.getGoodsType());
		    			  inObj[2].setGoodsCondition(empDet.getGoodsCondition());
		    			  inObj[2].setIncome(Double.parseDouble(empDet.getBusMonthlyIncome()));
		    			  inObj[2].setExpenditure(Double.parseDouble(empDet.getBusMonthlyExpenditure()));
		    			  inObj[2].setNetIncome(Double.parseDouble(empDet.getBusNetMonthlyIncome()));
		    			  inObj[2].setTurnOver(Double.parseDouble(empDet.getTurnover()));
		    			  odccmasterobj.setIncomeDetails(inObj);
		    			  int int_update=0;
		    			  
		    				  int_update=fcDelegate.updateODCCMaster(odccmasterobj,4);
								if(int_update!=0)
								{
								   
									empDet.setValidations("Account No "+int_update+" is Updated Successfully");
									
								}
								else
									empDet.setValidations("Account No "+int_update+" is not Updated");
						
		    			  }catch(Exception e){
		    				  e.printStackTrace();
		    			  }
		    			  empDet.setFlag("");
		    		  }
		    		  //code for updating the oddcc Employment Details ends
		    		  /*if(empDet.getFlag().equalsIgnoreCase("from_type")){
		    			  req.setAttribute("Employment", empDet.getEmpType());
			    		  req.setAttribute("AcType", fcDelegate.getodccModule(0));
		    		  }*/
		    	      return map.findForward(ResultHelp.getSuccess());
		    	  }
		    	  else
		    	  {
		    		  path=MenuNameReader.getScreenProperties("0000");
		    		  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
		    		  return map.findForward(ResultHelp.getError());
		    	  }
		      }
		      catch(Exception e)
		      {
		    	  path=MenuNameReader.getScreenProperties("0000");
		    	  setErrorPageElements(""+e, req, path);
		    	  return map.findForward(ResultHelp.getError());
		      }
		}
        
        
        /* *****************FC Employment Details Action ends here******************* */

        //Updation code ends here=====================
        
        
        
        
        
        
        
        
        /*else if(map.getPath().trim().equalsIgnoreCase("/FrontCounter/TabbedMenu")){
        	try{
        		TabbedPaneForm tbForm=(TabbedPaneForm)form;
        		path=MenuNameReader.getScreenProperties(tbForm.getPageID().trim());
        		System.out.println("path=="+path);
        		fcDelegate=new FrontCounterDelegate();
        		setTabbedPaneInitParams(req,path,fcDelegate,sb);
        		System.out.println("tbForm.getTabNum().trim()=="+tbForm.getTabNum().trim());
        		req.setAttribute("TabNum",tbForm.getTabNum().trim());
        		return map.findForward(ResultHelp.getSuccess());
        	}catch(Exception e){
        		logger.error(e.getMessage());
        		e.printStackTrace();
        		path=MenuNameReader.getScreenProperties("0000");
                setErrorPageElements(""+e.toString(),req,path);
                return map.findForward(ResultHelp.getError());
        	}
        	
        }*/
        else{
            path=MenuNameReader.getScreenProperties("0000");
            setErrorPageElements("Please chaack ! There is no matching path",req,path);
            return map.findForward(ResultHelp.getError());
        }
        
        
   }
  /* private void setDetails(String details,HttpServletRequest req,FrontCounterDelegate fcDelegate,AccountMasterObject amObj,SBOpeningActionForm sbForm,SignatureInstructionForm siForm,int signFlag)throws Exception{
	   if(details.equalsIgnoreCase("PersonalDetails")){
		   String perPath=MenuNameReader.getScreenProperties("0002");
           req.setAttribute("personalInfo",fcDelegate.getCustomer(amObj.getCid()));
           req.setAttribute("flag",perPath);
           req.setAttribute("panelName", CommonPanelHeading.getPersonal());
           req.setAttribute("TabNum","0");
           System.out.println("yes flag is set");
           
	   }
	   if(details.trim().equalsIgnoreCase("Introducer Type")){
           String perPath=MenuNameReader.getScreenProperties("0001");
           AccountObject acntObject=fcDelegate.getIntroducerAcntDetails(amObj.getIntrAccType(),amObj.getIntrAccNo());
           System.out.println("acntObject.getCid()=="+acntObject);
           req.setAttribute("TabNum","4");
           if(acntObject!=null){
               System.out.println("acntObject.getCid()=="+acntObject.getCid());
               req.setAttribute("personalInfo",fcDelegate.getCustomer(acntObject.getCid()));
               
           }
           else{
               req.setAttribute("personalInfo",new CustomerMasterObject());
           }
           req.setAttribute("flag",perPath);
           req.setAttribute("panelName", CommonPanelHeading.getIntroucer());
       }
	   if(details.trim().equalsIgnoreCase("Cash")){
		   req.setAttribute("TabNum","1");
           String perPath=MenuNameReader.getScreenProperties("3002");
           System.out.println("path=="+perPath);
           req.setAttribute("pageNum",sbForm.getPageId());  
           req.setAttribute("flag",perPath);
       }
	   if(details.trim().equalsIgnoreCase("Nominee")){
           String perPath=MenuNameReader.getScreenProperties("0002");
             req.setAttribute("pageNum",sbForm.getPageId());
             req.setAttribute("TabNum","2");
           req.setAttribute("flag",perPath);
       }
	   if(details.trim().equalsIgnoreCase("Signature Ins")){
		   	System.out.println("i am in sign ins");
		   	String perPath=MenuNameReader.getScreenProperties("0004");
        	req.setAttribute("flag",perPath);
        	SignatureInstructionObject[] signObject=fcDelegate.getSignature(Integer.parseInt(sbForm.getAcNum()),sbForm.getAcType());
        	req.setAttribute("SignInstActnPath","/FrontCounter/Signature");
        	req.setAttribute("panelName","Signature Instruction");
        	req.setAttribute("SBOpeningForm", sbForm);
        	req.setAttribute("TabNum","5");
        	if(signObject!=null){
        		System.out.println("sbForm.getDefaultSignIndex()=="+sbForm.getDefaultSignIndex());
        		System.out.println("sbForm.getDefSIndex()=="+sbForm.getDefSIndex());
        		req.setAttribute("signInfo",signObject);
        		if(sbForm.getSignIndex()!=null)
        			req.setAttribute("signInfo1",signObject[Integer.parseInt(sbForm.getSignIndex())]);
        		else
        			req.setAttribute("signInfo1",signObject[Integer.parseInt(sbForm.getDefaultSignIndex())]);
        		req.setAttribute("Signature",MenuNameReader.getScreenProperties("0005"));
        		req.setAttribute("SIFormPath","/FrontCounter/SignCombo");
        	}
        	else{
        		req.setAttribute("Signature",MenuNameReader.getScreenProperties("0005"));
				req.setAttribute("SIFormPath","/FrontCounter/SignCombo");
        	}
        	setSignatureTabPageDetails(req,sbForm);
     
       }
   }*/
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