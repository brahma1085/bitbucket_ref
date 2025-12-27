package com.scb.common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import masterObject.general.SignatureInstructionObject;
import masterObject.pygmyDeposit.AgentMasterObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.scb.common.forms.SignatureInstructionForm;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.fc.actions.ResultHelp;
import com.scb.pd.forms.AgentOpeningForm;
import com.scb.props.MenuNameReader;
import com.scb.designPatterns.PygmyDelegate;


public class SignatureInstructionClass extends Action{
	 private PygmyDelegate pgDelegate;
     private String path;

	public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest req,HttpServletResponse res)throws Exception {
		String path;
		
		if(map.getPath().equalsIgnoreCase("/Pygmy/AgentOpeningMenu")){
			try{
				
				SignatureInstructionForm siform=(SignatureInstructionForm)form;
				req.setAttribute("pageNum",siform.getSignPageID().trim());
				if(MenuNameReader.containsKeyScreen(siform.getSignPageID())){
                    path=MenuNameReader.getScreenProperties(siform.getSignPageID());
                    System.out.println("insideExecutre path"+path);
                    
                    setAgentOpeningInitParams(req,path,pgDelegate);
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
              return map.findForward(ResultHelp.getError());
            }
		}
		else if(map.getPath().equalsIgnoreCase("/Pygmy/SignatureInstruction")){
			SignatureInstructionForm siform=(SignatureInstructionForm)form;
            //String path;
            System.out.println("#############="+siform.getSignPageID().trim());
            try{
                    req.setAttribute("pageNum",siform.getSignPageID().trim());
                    System.out.println("insideExecutre111111111111"+MenuNameReader.containsKeyScreen(siform.getSignPageID()));
                    if(MenuNameReader.containsKeyScreen(siform.getSignPageID())){
                        path=MenuNameReader.getScreenProperties(siform.getSignPageID());
                        System.out.println(path);
                        
                        System.out.println(pgDelegate);
                        
                        SignatureInstructionObject[] signObject=pgDelegate.getSignature(Integer.parseInt(siform.getAcNum()),siform.getAcType());
                        System.out.println("sign=="+signObject);
                        
                        if(siform.getSignCombo().equalsIgnoreCase("SignatureInstruction")){
                        	String perPath=MenuNameReader.getScreenProperties("0005");
                            System.out.println("yes flag is set");
                            req.setAttribute("flag",perPath);
                            req.setAttribute("pageNum",siform.getSignPageID()); 
                            System.out.println("pageNum======="+siform.getSignPageID());
                        }
                    }
            }catch(Exception e){
				e.printStackTrace();
				path=MenuNameReader.getScreenProperties("0000");
				setErrorPageElements(""+e.toString(),req,path);
				return map.findForward(ResultHelp.getError());
			}
		}
		  return map.findForward(ResultHelp.getError());
	}
	private void setErrorPageElements(String exception,HttpServletRequest req,String path){
	       req.setAttribute("exception",exception);
	       req.setAttribute("pageId",path);

	   }

	     private void setAgentOpeningInitParams(HttpServletRequest req,String path, PygmyDelegate pgDelegate)throws Exception{
	       try{
	          req.setAttribute("pageId",path);
	          
	          req.setAttribute("Details",pgDelegate.getSignCombo());
	       }catch(Exception e){
	           throw e;
	       }
	}
}