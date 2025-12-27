package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanLeadgerForm extends ActionForm {

	
	private String combo_loancat,txt_startaccnum,txt_endingaccnum,txt_startdate,txt_enddate,combo_accounts,CatDesc;
	private String forward,file;
	private PageIdForm  pageidentity= new PageIdForm();

	String sysdate;

	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	
	public String getSysdate() {
		return sysdate;
	}


	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}


	public String getCombo_loancat() {
		return combo_loancat;
	}

	public void setCombo_loancat(String combo_loancat) {
		this.combo_loancat = combo_loancat;
	}

	public String getTxt_startaccnum() {
		return txt_startaccnum;
	}

	public void setTxt_startaccnum(String txt_startaccnum) {
		this.txt_startaccnum = txt_startaccnum;
	}

	public String getTxt_endingaccnum() {
		return txt_endingaccnum;
	}

	public void setTxt_endingaccnum(String txt_endingaccnum) {
		this.txt_endingaccnum = txt_endingaccnum;
	}

	public String getTxt_startdate() {
		return txt_startdate;
	}

	public void setTxt_startdate(String txt_startdate) {
		this.txt_startdate = txt_startdate;
	}

	public String getTxt_enddate() {
		return txt_enddate;
	}

	public void setTxt_enddate(String txt_enddate) {
		this.txt_enddate = txt_enddate;
	}

	public String getCombo_accounts() {
		return combo_accounts;
	}

	public void setCombo_accounts(String combo_accounts) {
		this.combo_accounts = combo_accounts;
	}

	public String getCatDesc() {
		return CatDesc;
	}

	public void setCatDesc(String catDesc) {
		CatDesc = catDesc;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	
}
