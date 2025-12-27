package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanSplIntRateUpdnForm extends ActionForm {

	private String txt_loancat,txt_fromdate,txt_laststage,txt_Desc,loan_acc_notfound,button_value,splintrestset;
	private PageIdForm  pageidentity= new PageIdForm();
	private double txt_specialintrate;
	private int txt_loanaccno;
	String sysdate;


	public String getSysdate() {
			return sysdate;
		}

		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_loancat() {
		return txt_loancat;
	}

	public void setTxt_loancat(String txt_loancat) {
		this.txt_loancat = txt_loancat;
	}

	

	public int getTxt_loanaccno() {
		return txt_loanaccno;
	}

	public void setTxt_loanaccno(int txt_loanaccno) {
		this.txt_loanaccno = txt_loanaccno;
	}

	public String getTxt_fromdate() {
		return txt_fromdate;
	}

	public void setTxt_fromdate(String txt_fromdate) {
		this.txt_fromdate = txt_fromdate;
	}

	
	public double getTxt_specialintrate() {
		return txt_specialintrate;
	}

	public void setTxt_specialintrate(double txt_specialintrate) {
		this.txt_specialintrate = txt_specialintrate;
	}

	public String getTxt_laststage() {
		return txt_laststage;
	}

	public void setTxt_laststage(String txt_laststage) {
		this.txt_laststage = txt_laststage;
	}

	public String getTxt_Desc() {
		return txt_Desc;
	}

	public void setTxt_Desc(String txt_Desc) {
		this.txt_Desc = txt_Desc;
	}

	public String getLoan_acc_notfound() {
		return loan_acc_notfound;
	}

	public void setLoan_acc_notfound(String loan_acc_notfound) {
		this.loan_acc_notfound = loan_acc_notfound;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public String getSplintrestset() {
		return splintrestset;
	}

	public void setSplintrestset(String splintrestset) {
		this.splintrestset = splintrestset;
	}
	
	
}
