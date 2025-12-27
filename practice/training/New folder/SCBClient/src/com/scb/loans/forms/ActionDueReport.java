package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class ActionDueReport extends ActionForm {
    private String date,acctype,forward;
    private int stagecode;
    
    private PageIdForm  pageidentity= new PageIdForm();
    
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public int getStagecode() {
		return stagecode;
	}
	public void setStagecode(int stagecode) {
		this.stagecode = stagecode;
	}
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
}
