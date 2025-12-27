package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class MonthlyConsolidatedRP extends ActionForm{
private String pageId;
private String forward,validations,fromYear,fromMonth,toYear,toMonth,printFile;


public String getPrintFile() {
	return printFile;
}
public void setPrintFile(String printFile) {
	this.printFile = printFile;
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
public String getFromYear() {
	return fromYear;
}
public void setFromYear(String fromYear) {
	this.fromYear = fromYear;
}
public String getFromMonth() {
	return fromMonth;
}
public void setFromMonth(String fromMonth) {
	this.fromMonth = fromMonth;
}
public String getToYear() {
	return toYear;
}
public void setToYear(String toYear) {
	this.toYear = toYear;
}
public String getToMonth() {
	return toMonth;
}
public void setToMonth(String toMonth) {
	this.toMonth = toMonth;
}



}
