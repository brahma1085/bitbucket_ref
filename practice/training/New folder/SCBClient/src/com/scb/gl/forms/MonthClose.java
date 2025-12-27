package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class MonthClose extends ActionForm{
private String pageId;
private String year,month,forwarding,validations,flag;
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
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

public String getForwarding() {
	return forwarding;
}
public void setForwarding(String forwarding) {
	this.forwarding = forwarding;
}
public String getValidations() {
	return validations;
}
public void setValidations(String validations) {
	this.validations = validations;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}


}
