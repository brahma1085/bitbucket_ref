package com.nit.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.exceptions.DataProcessingException;

import edu.domainobj.Account;

public class AccountAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = "failed";
		AccountFormBean formBean = (AccountFormBean) form;
		int accno = formBean.getAccno();
		AccountModel model = new AccountModel();
		Account account = null;
		try {
			account = model.getAccount(accno);
		} catch (DataProcessingException e) {
			responsekey = "abnormal";
		}
		if (account != null) {
			request.getSession().setAttribute("acc", account);
			responsekey = "success";
		}
		ActionForward forward = mapping.findForward(responsekey);
		return forward;
	}
}
