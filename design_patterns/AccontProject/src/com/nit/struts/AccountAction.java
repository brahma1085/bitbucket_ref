package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccountAction extends Action {
	AccountService as;

	public AccountAction() {
		as = new AccountService();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AccountForm accountform = (AccountForm) form;// TODO Auto-generated
		// method stub
		String responseKey = "failure";
		int accno = accountform.getAccno();
		Account acc;
		try {
			acc = as.getAccountDetails(accno);
			if (acc != null) {
				request.setAttribute("account", acc);
				responseKey = "success";
			}
		} catch (DBException e) {
			System.out.println("DBException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			responseKey = "abnormal";
		}

		ActionForward forward = mapping.findForward(responseKey);
		return forward;
	}
}