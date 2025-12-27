package com.scb.bk.actions;
import exceptions.DateFormatException;
import general.Validations;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scb.bk.forms.SIDeleteForm;
import com.scb.bk.forms.SIEntryForm;
/*import com.scb.bk.forms.PersonalDetails;*/
import com.scb.bk.forms.SIEntryComboElements;
import com.scb.common.forms.SignatureInstructionForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.help.Date;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.BackOfficeDelegate;
import com.scb.designPatterns.FrontCounterDelegate;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.fc.forms.SBOpeningActionForm;
import com.scb.props.MenuNameReader;

import masterObject.backOffice.SIEntryObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountMasterObject;
import masterObject.general.AccountObject;
import masterObject.general.DoubleFormat;
import masterObject.general.ModuleObject;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 11, 2007 
 * Time: 12:08:37 PM
 * To change this template use File | Settings | File Templates.
 */            
public class SIEntryAction extends Action {
 private BackOfficeDelegate bd;
 private String path;
 private String string_duedate;
 final Logger logger=LogDetails.getInstance().getLoggerObject("BackOfficeDelegate");

    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
       
      
        int Instruction_num;
        logger.info(map.getPath());
        if(map.getPath().equalsIgnoreCase("/BackOffice/SIEntryMenu")){
            try{
                    SIEntryForm myform=(SIEntryForm)form;
                    req.setAttribute("pageNum",myform.getPageId());
                    System.out.println("going to focus111");
                    req.setAttribute("priority","priority_num");
                    System.out.println("page********************"+myform.getPageidform());
                    System.out.println(myform.getPageId());
                if(MenuNameReader.containsKeyScreen(myform.getPageId())){
                    //path=MenuNameReader.getScreenProperties(myform.getPageidform().getPageId());
                	path=MenuNameReader.getScreenProperties(myform.getPageId());
                	System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQ");
                    logger.info("insideExecutre path"+path);
                    System.out.println("Delegate--------"+bd);
                    bd=new BackOfficeDelegate();
                    System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrr");
                    setSIEntryInitParams(req,path,bd);
                    //inst_num=myform.getInstruction_num();
                    System.out.println("going to focus222");
                    req.setAttribute("priority","priority_num");
                    req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                    req.setAttribute("TabNum","0");	
                    
                	setTabbedPaneInitParams(req,path,myform);
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
                     setErrorPageElements(""+e,req,path);
                     return map.findForward(ResultHelp.getError());
            }
                    
        }
                            
       else if(map.getPath().trim().equals("/BackOffice/SIEntry")){
           try{
                SIEntryForm myform=(SIEntryForm)form;
                req.setAttribute("pageNum",myform.getPageidform().getPageId());
               
                if(MenuNameReader.containsKeyScreen(myform.getPageidform().getPageId())){
                	path=MenuNameReader.getScreenProperties(myform.getPageidform().getPageId());
                	logger.info("insideExecutre path"+path);
                	bd=new BackOfficeDelegate();
                	setSIEntryInitParams(req,path,bd);
                	
                     
                	SIEntryObject siEntryObj=new SIEntryObject();
                	ModuleObject[] mod=bd.getMainModules(9);
                	
                	                	  	
                	System.out.println("*************** ********************************");
                	System.out.println("*********Submitting values:"+myform.getForward());    	
                	System.out.println("*********Submitting values:"+myform.getInstruction_num());  
                	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+myform.getPageId());
                	
                	if(Integer.parseInt(myform.getInstruction_num())!=0 ){
                		
                	 System.out.println("%%%%%%%%%%%%%%%%%%%#########------"+myform.getInstruction_num());
                	 SIEntryObject siretriveObj=bd.getInfoThruSiNo(Integer.parseInt(myform.getInstruction_num()),0);	
                     System.out.println("inside update");
                     System.out.println("setting values for update");
                     
                        String perPath=MenuNameReader.getScreenProperties("0001");
                          AccountObject fromaccObj=bd.getAccount(siretriveObj.getFromType(),siretriveObj.getFromAccNo());
                          if(fromaccObj!=null){
                             System.out.println("AccountObject.getCid()=="+fromaccObj.getCid());
                             req.setAttribute("personalInfo",bd.getCustomer(fromaccObj.getCid()));
                          }
                          else{
                              req.setAttribute("personalInfo",new CustomerMasterObject());
                          }
                          
                          req.setAttribute("flag",perPath);
                          req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                    			
              		      AccountObject toaccObj=bd.getAccount(siretriveObj.getToType(),siretriveObj.getToAccNo());
                          if(toaccObj!=null){
                             System.out.println("AccountObject.getCid()=="+toaccObj.getCid());
                           
                             req.setAttribute("personalInfo",bd.getCustomer(toaccObj.getCid()));
                          }
                          else{
                              req.setAttribute("personalInfo",new CustomerMasterObject());
                          }
                          
                          req.setAttribute("flag",perPath);
                          req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                     	           	
                     
                          req.setAttribute("TabNum","0");
                          req.setAttribute("SIUpdate",siretriveObj);
                          req.setAttribute("updatebutton","UPDATE"); 
                          req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                       
                          setTabbedPaneInitParams(req,path,myform);                                 	 
                     
                     
                	}
                     
                	
                	
                	//for submit
                	req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                	req.setAttribute("TabNum","0");
                	System.out.println("*********************************>>>>>>>>>**************"+myform.getTabPaneHeading());
                	if(myform.getTabPaneHeading()!=null){  
                		System.out.println("*********************************>>>>>>>>>"+myform.getTabPaneHeading());
                    	 if(myform.getTabPaneHeading().equalsIgnoreCase("ToAccountNumber")){
                    		 System.out.println("-----------------------------testing123******************");
                         	req.setAttribute("TabNum","1");
                         }   
                    	 if(myform.getTabPaneHeading().equalsIgnoreCase("FromAccountNumber")){
                    		 System.out.println("-----------------------------testing123******************");
                         	req.setAttribute("TabNum","0");
                         }  
                    	   
                	}
                	
                	
               	    setTabbedPaneInitParams(req,path,myform);
               	    
                	             
                    System.out.println("from acc no:"+myform.getFrom_ac_no());
                     if( !myform.getFrom_ac_no().equalsIgnoreCase("") && myform.getFrom_ac_no()!= null && Integer.parseInt(myform.getFrom_ac_no())!=0 )
                     {
             			String from_ac_type=null;
                        String perPath=MenuNameReader.getScreenProperties("0001");
                        //req.setAttribute("TabNum","0"); 
                        System.out.println("From Account Type1 :"+ myform.getFrom_ac_ty());
                        System.out.println("From Account Number1 :"+Integer.parseInt(myform.getFrom_ac_no()));
                         for(int i=0;i<mod.length;i++){
                         	if(mod[i].getModuleAbbrv().equalsIgnoreCase(myform.getFrom_ac_ty())){
                                 from_ac_type=mod[i].getModuleCode();                      	
                         	}
                         
                         }
                         System.out.println("from_ac_type"+from_ac_type);
                         System.out.println("from_ac_no"+Integer.parseInt(myform.getFrom_ac_no()));
                         AccountObject fromaccObj=bd.getAccount(from_ac_type,Integer.parseInt(myform.getFrom_ac_no()));
                         if(fromaccObj!=null){
                        	System.out.println("AccountObject.getCid()=="+fromaccObj.getCid());
                        	req.setAttribute("personalInfo",bd.getCustomer(fromaccObj.getCid()));
                        	req.setAttribute("TabNum","0");
                         }
                       else{
                       	 	req.setAttribute("personalInfo",new CustomerMasterObject());
                           }
                         req.setAttribute("flag",perPath);
                         req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                     }                   
                     
                     if(!myform.getTo_ac_no().equalsIgnoreCase("") && myform.getTo_ac_no()!= null &&   Integer.parseInt(myform.getTo_ac_no())!=0){
             			String to_ac_type=null;
             			//req.setAttribute("TabNum","1");
             			String perPath=MenuNameReader.getScreenProperties("0001");
                        System.out.println("To Account Type1 :"+ myform.getTo_ac_ty());
                        System.out.println("To Account Number1 :"+ myform.getTo_ac_no());
                        for(int i=0;i<mod.length;i++){
                         	if(mod[i].getModuleAbbrv().equalsIgnoreCase(myform.getTo_ac_ty())){
                                 to_ac_type=mod[i].getModuleCode();                      	
                         	}
                         	
                         }
                         System.out.println("to_ac_type"+to_ac_type);
                         System.out.println("to_ac_no"+Integer.parseInt(myform.getTo_ac_no()));
                         AccountObject toaccObj=bd.getAccount(to_ac_type,Integer.parseInt(myform.getTo_ac_no()));
                         if(toaccObj!=null){
                        	  System.out.println("AccountObject.getCid()=="+toaccObj.getCid());
                              req.setAttribute("personalInfo",bd.getCustomer(toaccObj.getCid()));
                              req.setAttribute("TabNum","1");
                             }
                         else{
                             req.setAttribute("personalInfo",new CustomerMasterObject());
                              }
                         req.setAttribute("flag",perPath);
                         req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                     }         	
                    
                      System.out.println("TabHeading"+myform.getTabPaneHeading());
                     /*if(myform.getTabPaneHeading()!=null){ 
                 	 if(myform.getTabPaneHeading().equalsIgnoreCase("ToAccountNumber")){
                      	req.setAttribute("TabNum","1");
                      }   
                     }*/
                   	if(myform.getForward()!=null){
                    if(myform.getForward().trim().equalsIgnoreCase("SUBMIT")){
                                                   	
                       	//submitting values
                    	System.out.println("pri_no:"+(myform.getPriority_num()));
                		siEntryObj.setPriorityNo((myform.getPriority_num()));
                		
                		for(int i=0;i<mod.length;i++)
						{
							if(myform.getFrom_ac_ty().equals(mod[i].getModuleAbbrv()))
							{
								System.out.println("FromAcc_type:"+mod[i].getModuleCode());
								siEntryObj.setFromType(mod[i].getModuleCode());
							}
						}
                	
                		System.out.println("FromAcc_No:"+Integer.parseInt(myform.getFrom_ac_no()));
                		siEntryObj.setFromAccNo(Integer.parseInt(myform.getFrom_ac_no()));
                		for(int i=0;i<mod.length;i++)
						{
							if(myform.getTo_ac_ty().equals(mod[i].getModuleAbbrv()))
							{
								siEntryObj.setToType(mod[i].getModuleCode());
							}
						}
                	                		
                		System.out.println("*********************"+Integer.parseInt(myform.getTo_ac_no()));
                		System.out.println("ToAcc_No:"+Integer.parseInt(myform.getTo_ac_no()));
                		System.out.println("*********************11"+Integer.parseInt(myform.getTo_ac_no()));
                		siEntryObj.setToAccNo(Integer.parseInt(myform.getTo_ac_no()));
                		System.out.println("Loan_Option:"+Integer.parseInt(myform.getLoan_option()));
                		siEntryObj.setLoanOpt(Integer.parseInt(myform.getLoan_option()));
                		System.out.println("Due Date:"+myform.getDue_date());	
                		siEntryObj.setDueDt(myform.getDue_date());
                		System.out.println("Period in months:"+Integer.parseInt(myform.getPeriod_mon()));
                		siEntryObj.setPeriodMonths(Integer.parseInt(myform.getPeriod_mon()));
                		System.out.println("Days:"+Integer.parseInt(myform.getDays()));
                		siEntryObj.setPeriodDays(Integer.parseInt(myform.getDays()));
                		System.out.println("Amount:"+Double.parseDouble(myform.getAmount()));
                		siEntryObj.setAmount(Double.parseDouble(myform.getAmount()));
                		System.out.println("No of times exec:"+Integer.parseInt(myform.getNo_of_times_exec()));
                		siEntryObj.setNoExec(Integer.parseInt(myform.getNo_of_times_exec()));
                		System.out.println("Last_exec_date :"+myform.getLast_exec_date());
                		siEntryObj.setLastExec(myform.getLast_exec_date());
                		System.out.println("Expiry days :"+Integer.parseInt(myform.getExpiry_days()));
                		siEntryObj.setExpiryDays(Integer.parseInt(myform.getExpiry_days()));
                		siEntryObj.obj_userverifier.setUserTml("Tml");
                		siEntryObj.obj_userverifier.setUserId("User Id");
                		siEntryObj.obj_userverifier.setUserDate("Time");
                		Instruction_num=bd.storeInfo(siEntryObj);
                		
                 	}
                  
                  
                 else if(myform.getForward().equalsIgnoreCase("UPDATE")){ 
                	 SIEntryObject siEntryUpdate=new SIEntryObject();
                	 
                	 System.out.println("inside update block");
                	 System.out.println("Instruction:"+Integer.parseInt(myform.getInstruction_num()));
                	 siEntryUpdate.setInstNo(Integer.parseInt(myform.getInstruction_num()));
                	 System.out.println((myform.getPriority_num()));                	
                	 siEntryUpdate.setPriorityNo((myform.getPriority_num()));
                	
                	 for(int i=0;i<mod.length;i++)
						{
							if(myform.getFrom_ac_ty().equals(mod[i].getModuleAbbrv()))
							{
								System.out.println("FromAcc_type:"+mod[i].getModuleCode());
								siEntryUpdate.setFromType(mod[i].getModuleCode());
							}
						}
                	 
                	 //siEntryUpdate.setFrommodAbbrv(myform.getFrom_ac_ty());
                	 siEntryUpdate.setFromAccNo(Integer.parseInt(myform.getFrom_ac_no()));
                	 for(int i=0;i<mod.length;i++)
						{
							if(myform.getTo_ac_ty().equals(mod[i].getModuleAbbrv()))
							{
								siEntryUpdate.setToType(mod[i].getModuleCode());
							}
						}
                	 //siEntryUpdate.setTomodAbbrv(myform.getTo_ac_ty());
                	 siEntryUpdate.setToAccNo(Integer.parseInt(myform.getTo_ac_no()));
                	 siEntryUpdate.setLoanOpt(Integer.parseInt(myform.getLoan_option()));
                	 siEntryUpdate.setDueDt(myform.getDue_date());
                	 siEntryUpdate.setPeriodMonths(Integer.parseInt(myform.getPeriod_mon()));
                	 siEntryUpdate.setPeriodDays(Integer.parseInt(myform.getDays()));
                	 siEntryUpdate.setAmount(Double.parseDouble(myform.getAmount()));
                	 siEntryUpdate.setNoExec(Integer.parseInt(myform.getNo_of_times_exec()));
                	 siEntryUpdate.setLastExec(myform.getLast_exec_date());
                	 siEntryUpdate.setExpiryDays(Integer.parseInt(myform.getExpiry_days()));
                	 int update_func=bd.update(siEntryUpdate);
                 	 System.out.println("outside update");
                                    
                 }
               /*else if(myform.getForward().equalsIgnoreCase("CLEAR")){
              	   System.out.println("clearing form........");
              	    
              	   myform.setPriority_num("");
              	   myform.setFrom_ac_ty("");
              	   myform.setFrom_ac_no("");
              	   myform.setTo_ac_ty("");
              	   myform.setTo_ac_no("");
              	   myform.setLoan_option("");
              	   myform.setDue_date("");
              	   myform.setPeriod_mon("");
              	   myform.setDays("");
              	   myform.setAmount("");
              	   myform.setNo_of_times_exec("");
              	   myform.setLast_exec_date("");
              	   myform.setExpiry_days("");
                    
               }*/  
               }    
                   
              	return map.findForward(ResultHelp.getSuccess());
                }
           
                else{
                		path=MenuNameReader.getScreenProperties("0000");
                		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                		return map.findForward(ResultHelp.getError());
                  }
           
               }
           catch(Exception e){
       	    	 e.printStackTrace();
           		 path=MenuNameReader.getScreenProperties("0000");
                 setErrorPageElements(""+e,req,path);
                 logger.info(e.getMessage());
                 return map.findForward(ResultHelp.getError());
           		  
           	  }
           	  
       	}
        
       else if(map.getPath().equalsIgnoreCase("/BackOffice/SIDeleteMenu")){
            logger.info("##############"+map.getPath());
               try{
             	 SIDeleteForm myform=(SIDeleteForm) form;
             	 req.setAttribute("pageNum",myform.getPageId());
             	 if(MenuNameReader.containsKeyScreen(myform.getPageId())){
                      path=MenuNameReader.getScreenProperties(myform.getPageId());
                      logger.info("insideExecutre path"+path);
                      bd=new BackOfficeDelegate();
                      setSIDeleteInitParams(req,path,bd);
                      req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                      req.setAttribute("TabNum","0");
                      setTabbedPaneInitParamsforSIDelete(req,path,myform);                 
                      return map.findForward(ResultHelp.getSuccess());
                  }
                  else{
                       path=MenuNameReader.getScreenProperties("0000");
                       setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
                       return map.findForward(ResultHelp.getError());
                  }
                 }catch(Exception e){
                       path=MenuNameReader.getScreenProperties("0000");
                       setErrorPageElements(""+e,req,path);
                       logger.error(e.getMessage());
                       e.printStackTrace();
                       return map.findForward(ResultHelp.getError());
                                      
              }
                 
             
         }
        
       else if(map.getPath().trim().equals("/BackOffice/SIDelete")){
           try{
         	 SIDeleteForm myform=(SIDeleteForm) form;
         	 req.setAttribute("pageNum",myform.getPageidform().getPageId());
         	 if(MenuNameReader.containsKeyScreen(myform.getPageidform().getPageId())){
                  path=MenuNameReader.getScreenProperties(myform.getPageidform().getPageId());
                  String page=myform.getPageId();
                  logger.info("insideExecutre path"+path);
                  bd=new BackOfficeDelegate();
                  setSIDeleteInitParams(req,path,bd);
                  System.out.println("Deleting values:"+myform.getForward());
                  if(myform.getInstruction_num()!=0){
                  if(myform.getForward()!=null){
                      if(myform.getForward().equalsIgnoreCase("error")){        
                      
                          SIEntryObject sidelObj=bd.getInfoThruSiNo(myform.getInstruction_num(),0 );	
                          req.setAttribute("SIDel",sidelObj);
                          logger.info("Details**************"+myform.getInstruction_num());
                          String perPath=MenuNameReader.getScreenProperties("0001");
                          
                          logger.info("From Account Type from SiObject:"+sidelObj.getFromType());
                          logger.info("From Account Number from SiObject:"+sidelObj.getFromAccNo());
                          AccountObject accObj=bd.getAccount(sidelObj.getFromType(),sidelObj.getFromAccNo());
                          if(accObj!=null){
                            logger.info("AccountObject.getCid()=="+accObj.getCid());
                            req.setAttribute("personalInfo",bd.getCustomer(accObj.getCid()));
                          }
                         else{
                            req.setAttribute("personalInfo",new CustomerMasterObject());
                          }
                  
                         req.setAttribute("flag",perPath);
                         req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                  
                         AccountObject accObj1=bd.getAccount(sidelObj.getToType(),sidelObj.getToAccNo());
                         if(accObj1!=null){
                           logger.info("AccountObject.getCid()=="+accObj1.getCid());
                           req.setAttribute("personalInfo",bd.getCustomer(accObj1.getCid()));
                          }
                         else{
                           req.setAttribute("personalInfo",new CustomerMasterObject());
                          }
                           req.setAttribute("flag",perPath);
                           req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                          
                           req.setAttribute("TabNum","0"); 
                           req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                           
                           setTabbedPaneInitParamsforSIDelete(req,path,myform);    
                           
                           
                          int loan_option_val=sidelObj.getLoanOpt();
   						String loan_option="";
   						if(loan_option_val==1)
   							loan_option="Total Amt";
   						else if(loan_option_val==2)
   							loan_option="Principal Amt";
   						else if(loan_option_val==3)
   							loan_option="Due Amt";
   						
   						if(loan_option_val==0)
   							req.setAttribute("loanoption","------");	
   						else
   				            req.setAttribute("loanoption",loan_option);
                      }
                      //System.out.println("Deleting values1:"+myform.getForward()); 
                    else if(myform.getForward().equalsIgnoreCase("DELETE")){        
                        
                            SIEntryObject sidelObj=bd.getInfoThruSiNo(myform.getInstruction_num(),0 );	
                            req.setAttribute("SIDel",sidelObj);
                            logger.info("Details**************"+myform.getInstruction_num());
                            String perPath=MenuNameReader.getScreenProperties("0001");
                            logger.info("From Account Type from SiObject:"+sidelObj.getFromType());
                            logger.info("From Account Number from SiObject:"+sidelObj.getFromAccNo());
                            AccountObject accObj=bd.getAccount(sidelObj.getFromType(),sidelObj.getFromAccNo());
                            if(accObj!=null){
                              logger.info("AccountObject.getCid()=="+accObj.getCid());
                              req.setAttribute("personalInfo",bd.getCustomer(accObj.getCid()));
                            }
                           else{
                              req.setAttribute("personalInfo",new CustomerMasterObject());
                            }
                    
                           req.setAttribute("flag",perPath);
                           req.setAttribute("panelName", CommonPanelHeading.getfromAccInfo());
                    
                           AccountObject accObj1=bd.getAccount(sidelObj.getToType(),sidelObj.getToAccNo());
                           if(accObj1!=null){
                             logger.info("AccountObject.getCid()=="+accObj1.getCid());
                             req.setAttribute("personalInfo",bd.getCustomer(accObj1.getCid()));
                            }
                           else{
                             req.setAttribute("personalInfo",new CustomerMasterObject());
                            }
                             req.setAttribute("flag",perPath);
                             req.setAttribute("panelName", CommonPanelHeading.gettoAccInfo());
                             
                             req.setAttribute("TabNum","0");
                             req.setAttribute("Tab",MenuNameReader.getScreenProperties("3003"));
                             
                             setTabbedPaneInitParamsforSIDelete(req,path,myform);    
                             
                             int loan_option_val=sidelObj.getLoanOpt();
                             System.out.println("loan--------"+loan_option_val);
      						String loan_option=null;
      						if(loan_option_val==1)
      							loan_option="Total Amt";
      							
      						else if(loan_option_val==2)
      							loan_option="Principal Amt";
      						else if(loan_option_val==3)
      							loan_option="Due Amt";
      						
      						if(loan_option_val==0)
      						   req.setAttribute("loanoption","------");	
      						else
      							req.setAttribute("loanoption",loan_option);

                                   
                                                    
                           bd.delete(myform.getInstruction_num(),"tml","uid","userdate",0); 
                    
                        }
                    /*else if(myform.getForward().equalsIgnoreCase("CLEAR")){
                  	   myform=null; 
                  	 }*/
                     }
                    }                        
                  else{
                     req.setAttribute("InstNo",new Boolean(true));   	
                   } 
                  
                return map.findForward(ResultHelp.getSuccess());
         	    }
         else{
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
       		return map.findForward(ResultHelp.getError());
       	    } 
           } catch(Exception e){
       		path=MenuNameReader.getScreenProperties("0000");
       		setErrorPageElements(""+e,req,path);
       		logger.error(e);
       		e.printStackTrace();
       		return map.findForward(ResultHelp.getError());
        	}
        }
       else{
	    	  path=MenuNameReader.getScreenProperties("0000");
	    	  setErrorPageElements("Please check ! There is no name for the given key in the properties file",req,path);
	    	  return map.findForward(ResultHelp.getError());
	      } 
    } 
   

   private void setErrorPageElements(String exception,HttpServletRequest req,String path){
    req.setAttribute("exception",exception);
    req.setAttribute("pageId",path);

   	}
   
   private void setSIEntryInitParams(HttpServletRequest req,String path, BackOfficeDelegate bd)throws Exception{
    try{
       req.setAttribute("pageId",path);
       req.setAttribute("loanoption",bd.getComboLoanOptions());
       
    }catch(Exception e){
        throw e;
    }
  }
  
   private void setTabbedPaneInitParams(HttpServletRequest req,String path,SIEntryForm siForm)throws Exception{
	   System.out.println(path);
	   
	   System.out.println("1st =====================>"+siForm.getPageId());
	   
	   System.out.println("2st =====================>"+siForm.getPageid());
	   
	   String pageActions[]={"/BackOffice/SIEntry?tabPaneHeading=FromAccountNumber&pageId="+siForm.getPageId(),"/BackOffice/SIEntry?tabPaneHeading=ToAccountNumber&pageId="+siForm.getPageId()};
	   
	   String tabHeading[]={"FromAccountNumber","ToAccountNumber"};
	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim()};
	   
	   req.setAttribute("TabHeading",tabHeading);
	   req.setAttribute("PageActions", pageActions);
	   req.setAttribute("PagePath", pagePath);
	   req.setAttribute("pageNum","3003");
	   
   } 
   

   private void setSIDeleteInitParams(HttpServletRequest req,String path, BackOfficeDelegate bd)throws Exception{
      try{
         req.setAttribute("pageId",path);
         
         
      }catch(Exception e){
          throw e;
      }
  }

   private void setTabbedPaneInitParamsforSIDelete(HttpServletRequest req,String path,SIDeleteForm sidelForm)throws Exception{
  	   System.out.println(path);
  	   
  	   String pageActions[]={"/BackOffice/SIDelete?tabPaneHeading=PersonalDetails&pageId="+sidelForm.getPageId(),"/BackOffice/SIEntry?tabPaneHeading=PersonalDetails&pageId="+sidelForm.getPageId()};
  	   
  	   String tabHeading[]={"FromAccountNumber","ToAccountNumber"};
  	   String pagePath[]={MenuNameReader.getScreenProperties("0001").trim(),MenuNameReader.getScreenProperties("0001").trim()};
  	   
  	   req.setAttribute("TabHeading",tabHeading);
  	   req.setAttribute("PageActions", pageActions);
  	   req.setAttribute("PagePath", pagePath);
  	   req.setAttribute("pageNum","3003");
  	   
     }     
 
   
  }
