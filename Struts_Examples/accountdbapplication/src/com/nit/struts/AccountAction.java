package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountAction extends Action {
	private AccountModel model;

	public AccountAction() {
		model = new AccountModel();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = "failure";
		AccountFormBean formBean = (AccountFormBean) form;
		String accno = formBean.getAccno();
		Account account = model.getAccount(accno);
		if (account != null) {
			request.setAttribute("acc", account);
			responsekey = "success";
		}
		return mapping.findForward(responsekey);
	}

}
