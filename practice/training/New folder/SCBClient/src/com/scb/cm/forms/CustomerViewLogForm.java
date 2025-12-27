package com.scb.cm.forms;

import org.apache.struts.action.ActionForm;

public class CustomerViewLogForm extends ActionForm {
	
	private String pageId,cid; 
    private int custid;
	
	public int getCustid() {
		return custid;
	}

	public void setCustid(int custid) {
		this.custid = custid;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
}
