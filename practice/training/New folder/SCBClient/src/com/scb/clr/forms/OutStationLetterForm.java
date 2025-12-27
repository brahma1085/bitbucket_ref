package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class OutStationLetterForm extends ActionForm{
	
	private String pageId,clgDate,flag,validateFlag;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getClgDate() {
		return clgDate;
	}

	public void setClgDate(String clgDate) {
		this.clgDate = clgDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

}
