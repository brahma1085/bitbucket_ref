package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class DayClose extends ActionForm{
private String pageId;
private String date,forward,validations,flag;

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getValidations() {
	return validations;
}

public void setValidations(String validations) {
	this.validations = validations;
}
}
