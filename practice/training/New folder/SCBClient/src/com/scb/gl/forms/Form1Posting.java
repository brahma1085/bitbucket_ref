package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class Form1Posting extends ActionForm{
private String pageId;
private String toDate,fromDate,forward,validations,butnStatus,flag;

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}

public String getButnStatus() {
	return butnStatus;
}

public void setButnStatus(String butnStatus) {
	this.butnStatus = butnStatus;
}


public String getToDate() {
	return toDate;
}

public void setToDate(String toDate) {
	this.toDate = toDate;
}

public String getFromDate() {
	return fromDate;
}

public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
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
