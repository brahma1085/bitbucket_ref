package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class DummyForm extends ActionForm{

	
	String pageId,txtField;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTxtField() {
		return txtField;
	}

	public void setTxtField(String txtField) {
		this.txtField = txtField;
	}
	
	
	
}
