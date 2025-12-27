package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

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
		DynaValidatorForm formBean = (DynaValidatorForm) form;
		String username = (String) formBean.get("username");
		String password = (String) formBean.get("password");
		flag = model.isAuthenticated(username, password);
		if (flag)
			responsekey = "success";
		return mapping.findForward(responsekey);
	}
}
