package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.domainobj.Account;
import edu.service.AccountService;

public class AccountAction extends Action {
	AccountService accountService;

	public AccountAction() {
		accountService = new AccountService();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = "success";
		AccountFormBean formBean = (AccountFormBean) form;
		int accno = formBean.getAccno();
		Account account = accountService.getAccount(accno);
		request.getSession().setAttribute("account", account);
		ActionForward forward = mapping.findForward(responsekey);
		return forward;
	}
}
