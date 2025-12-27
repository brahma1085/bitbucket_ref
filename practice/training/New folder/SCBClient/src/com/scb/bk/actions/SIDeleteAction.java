package com.scb.bk.actions;

import masterObject.backOffice.SIEntryObject;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccountObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scb.bk.forms.SIDeleteForm;
import com.scb.bk.forms.SIEntryComboElements;
import com.scb.bk.forms.SIEntryForm;
import com.scb.common.forms.PersonnelForm;
import com.scb.common.help.CommonPanelHeading;
import com.scb.common.log4j.LogDetails;
import com.scb.designPatterns.BackOfficeDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.props.MenuNameReader;


/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 22, 2007
 * Time: 11:52:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class SIDeleteAction extends Action{
        
		private BackOfficeDelegate bd;
		private String path;
		final Logger logger=LogDetails.getInstance().getLoggerObject("BackOfficeDelegate");

   public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
             
           String inst_num=null;
           logger.info("******************************");
           logger.info(map.getPath());
           logger.info("******************************");
          
            if(map.getPath().equalsIgnoreCase("/BackOffice/SIDeleteMenu")){
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
                                 
                                 setTabbedPaneInitParams(req,path,myform);    
                                 
                                 
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
                                   
                                   setTabbedPaneInitParams(req,path,myform);    
                                   
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

     private void setSIDeleteInitParams(HttpServletRequest req,String path, BackOfficeDelegate bd)throws Exception{
        try{
           req.setAttribute("pageId",path);
           
           
        }catch(Exception e){
            throw e;
        }
    }

     private void setTabbedPaneInitParams(HttpServletRequest req,String path,SIDeleteForm sidelForm)throws Exception{
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


