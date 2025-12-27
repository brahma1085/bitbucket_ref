package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class RBIForm9Reports extends ActionForm{
private String pageId;
private String forward,validations,year,month,flag;

public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
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
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}


}
