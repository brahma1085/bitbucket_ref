package com.nit.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountAction extends Action {
	private AccountService service;

	public AccountAction() {
		service = new AccountService();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = "failure";
		AccountFormBean formBean = (AccountFormBean) form;
		float balance = formBean.getBalance();
		List<Account> accounts = service.getAccounts(balance);
		if (accounts != null) {
			request.setAttribute("accounts", accounts);
			responsekey = "success";
		}
		return mapping.findForward(responsekey);
	}

}
