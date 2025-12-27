package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String responsekey = "failed";
		LoginFormBean formBean = (LoginFormBean) form;
		String user = formBean.getUsername();
		String pwd = formBean.getPassword();
		LoginService service = new LoginService();
		boolean flag = service.isAuthenticate(user, pwd);
		if (flag)
			responsekey = "success";
		ActionForward forward = mapping.findForward(responsekey);
		return forward;
	}
}
