package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action {
	private LoginModel model;

	public LoginAction() {
		model = new LoginModel();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = "failure";
		boolean flag = false;
		LoginFormBean formBean = (LoginFormBean) form;
		String username = formBean.getUsername();
		String password = formBean.getPassword();
		flag = model.isAuthenticated(username, password);
		if (flag)
			responsekey = "success";
		return mapping.findForward(responsekey);
	}
}
