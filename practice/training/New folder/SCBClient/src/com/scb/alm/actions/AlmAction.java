package com.scb.alm.actions;

import exceptions.RecordsNotFoundException;
import exceptions.RecordNotInsertedException;
import general.Validations;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.alm.forms.ALMOutFlowForm;
import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.AdministratorDelegate;
import com.scb.designPatterns.AlmDelegate;
import com.scb.props.MenuNameReader;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AlmAction extends Action {
	private AlmDelegate almDelegate;
	private AdministratorDelegate admDelegate;
	private String path;
	HttpSession session;
	String user, tml;
	Map user_role;

	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		
		
		
		if(map.getPath().equalsIgnoreCase("/ALM/SlsOutflowsMenu")){
			
			try{
				System.out.println("line 45");
			ALMOutFlowForm  almoutform=(ALMOutFlowForm)form; 
//			String year=almoutform.getYear();
//			String month=almoutform.getMonth();
			String pageid=almoutform.getPageId();
//			System.out.println("The year is "+year);
//			System.out.println("The month is "+month);
			if(MenuNameReader.containsKeyScreen(almoutform.getPageId())){
                path=MenuNameReader.getScreenProperties(almoutform.getPageId());
                
                
                System.out.println("At 55"+path);
               
                
                req.setAttribute("pageId",path);
                return map.findForward(ResultHelp.getSuccess());
            }
			
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
		else if(map.getPath().equalsIgnoreCase("/ALM/SlsInflows")){
			
			try{
			ALMOutFlowForm  almoutform=(ALMOutFlowForm)form; 
//			String year=almoutform.getYear();
//			String month=almoutform.getMonth();
			String pageid=almoutform.getPageId();
//			System.out.println("The year is "+year);
//			System.out.println("The month is "+month);
			if(MenuNameReader.containsKeyScreen(almoutform.getPageId())){
                path=MenuNameReader.getScreenProperties(almoutform.getPageId());
                
                
                System.out.println("At 55"+path);
               
                
                req.setAttribute("pageId",path);
                return map.findForward(ResultHelp.getSuccess());
            }
			
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		}		
		
		
		
		return map.findForward(ResultHelp.getSuccess());

		
	}

	private void setErrorPageElements(String exception, HttpServletRequest req,String path) {
		req.setAttribute("exception", exception);
		req.setAttribute("pageId", path);

	}

}
