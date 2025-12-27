package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LoansApplicationForm extends ActionForm{
	private String pageId,value;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
 
}
