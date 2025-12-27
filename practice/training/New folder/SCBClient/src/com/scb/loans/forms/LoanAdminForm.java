package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanAdminForm extends ActionForm {
 
	private String loantable,loanmodules,acctype,forward,view;
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	private PageIdForm  pageidentity= new PageIdForm();

	public String getLoantable() {
		return loantable;
	}

	public void setLoantable(String loantable) {
		this.loantable = loantable;
	}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getLoanmodules() {
		return loanmodules;
	}

	public void setLoanmodules(String loanmodules) {
		this.loanmodules = loanmodules;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}
}
