package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class SimpleLoanForm extends ActionForm
{
	private String pageId;
	private String simpLoanName;
	private int cid;
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getSimpLoanName() {
		return simpLoanName;
	}
	public void setSimpLoanName(String simpLoanName) {
		this.simpLoanName = simpLoanName;
	}
	
}
