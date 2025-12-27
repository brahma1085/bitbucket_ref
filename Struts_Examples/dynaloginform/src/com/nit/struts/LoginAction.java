package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class LoginAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String responsekey = "failed";
		DynaActionForm formBean = (DynaActionForm) form;
		String user = (String) formBean.get("username");
		String pwd = (String) formBean.get("password");
		LoginService service = new LoginService();
		boolean flag = service.isAuthenticate(user, pwd);
		if (flag)
			responsekey = "success";
		ActionForward forward = mapping.findForward(responsekey);
		return forward;
	}
}
