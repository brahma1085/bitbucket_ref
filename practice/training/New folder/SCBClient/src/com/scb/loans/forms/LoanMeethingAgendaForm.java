package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanMeethingAgendaForm extends ActionForm 
{
	private String txt_Loancat,button_value,txt_actype;
	private int txt_loanaccno;
	private PageIdForm  pageidentity= new PageIdForm();
  

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_Loancat() {
		return txt_Loancat;
	}

	public void setTxt_Loancat(String txt_Loancat) {
		this.txt_Loancat = txt_Loancat;
	}

	

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public int getTxt_loanaccno() {
		return txt_loanaccno;
	}

	public void setTxt_loanaccno(int txt_loanaccno) {
		this.txt_loanaccno = txt_loanaccno;
	}

	public String getTxt_actype() {
		return txt_actype;
	}

	public void setTxt_actype(String txt_actype) {
		this.txt_actype = txt_actype;
	}
	
	

}
