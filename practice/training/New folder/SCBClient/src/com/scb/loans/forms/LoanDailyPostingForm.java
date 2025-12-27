package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanDailyPostingForm extends ActionForm{
	String acctype,date,process,accountclosed,result;
	int accno;
	
	private PageIdForm  pageidentity= new PageIdForm();
	
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getAccountclosed() {
		return accountclosed;
	}
	public void setAccountclosed(String accountclosed) {
		this.accountclosed = accountclosed;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}