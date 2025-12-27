package com.scb.login.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.scb.designPatterns.AdministratorDelegate;

public class LogoutAction extends Action {
	private AdministratorDelegate admDelegate;
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		System.out.println("inside the logout Action---------------->");
		
		try{
		admDelegate=new AdministratorDelegate();
		HttpSession session=request.getSession();
		admDelegate.doLogout(session.getAttribute("UserTml").toString(),session.getAttribute("UserName").toString());
		session.invalidate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("thanks");
	}
}
