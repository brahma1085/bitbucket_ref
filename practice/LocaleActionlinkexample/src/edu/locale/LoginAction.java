package edu.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class LoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rkey = "failure";
		DynaActionForm validatorForm = (DynaActionForm) form;
		String username = (String) validatorForm.get("username");
		String password = (String) validatorForm.get("password");
		if (username.equals(password))
			rkey = "success";
		return mapping.findForward(rkey);
	}

}
