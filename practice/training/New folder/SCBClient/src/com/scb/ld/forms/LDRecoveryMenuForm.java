package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDRecoveryMenuForm extends ActionForm{
	
	String pageId,value;


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
