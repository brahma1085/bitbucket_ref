package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDOpenClosedMenuForm extends ActionForm {
	
	private String pageId;  
	String sysdate;
	
  
	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
