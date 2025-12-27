package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoansQuarterlyInterestForm extends ActionForm {
	
	private PageIdForm  pageidentity= new PageIdForm();
	private String calc_submit,quarterdates,view,forward;

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getCalc_submit() {
		return calc_submit;
	}

	public void setCalc_submit(String calc_submit) {
		this.calc_submit = calc_submit;
	}

	public String getQuarterdates() {
		return quarterdates;
	}

	public void setQuarterdates(String quarterdates) {
		this.quarterdates = quarterdates;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

}	
