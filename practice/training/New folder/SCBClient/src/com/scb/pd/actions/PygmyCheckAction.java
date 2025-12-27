package com.scb.pd.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import masterObject.pygmyDeposit.AgentMasterObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.common.help.ResultHelp;
import com.scb.designPatterns.PygmyDelegate;
import com.scb.pd.forms.AgentClosingForm;
import com.scb.pd.forms.PygmyCheckForm;
import com.scb.pd.forms.PygmyOpeningForm;
import com.scb.props.MenuNameReader;

public class PygmyCheckAction extends Action{
	public ActionForward execute(ActionMapping map,ActionForm form,HttpServletRequest req,HttpServletResponse res)
	{
	if(map.getPath().equalsIgnoreCase("/Pygmy/PygmyCheckMenu"))
	{
		PygmyCheckForm pcform = (PygmyCheckForm) form;
		System.out.println("hi i am here" + map.getPath());

		try {

			req.setAttribute("pageNum", pcform.getPageId().trim());
			req.setAttribute("Index", new String("0"));
			System.out.println("===" + pcform.getPageId());
			

			System.out.println("insideExecutre111111111111"+ MenuNameReader.containsKeyScreen(pcform.getPageId()));
			if (MenuNameReader.containsKeyScreen(pcform.getPageId())) {
				String path = MenuNameReader.getScreenProperties(pcform.getPageId());
				System.out.println("insideExecutre path" + path);
				//pgDelegate = PygmyDelegate.getPygmyDelegateInstance();
				//pcform.setValidations("");
				//setPygmyOpeningInitParams(req, path, pgDelegate);
				return map.findForward(ResultHelp.getSuccess());
			} else {
				 String path = MenuNameReader.getScreenProperties("0000");
				//setErrorPageElements("Please check ! There is no name for the given key in the properties file",req, path);
				return map.findForward(ResultHelp.getError());
			}
		
		} catch (Exception e) {
			String path = MenuNameReader.getScreenProperties("0000");
			e.printStackTrace();
			//setErrorPageElements("" + e, req, path);
			return map.findForward(ResultHelp.getError());
		}
	}

	
	return map.findForward(ResultHelp.getSuccess());

	}
}
