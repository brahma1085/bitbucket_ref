package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class MonthlyBalanceSheet extends ActionForm{
private String pageId;
private String forward,validations,comparedYear,comparedMonth,reqYear,reqMonth,printFile,flag;


public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
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
public String getComparedYear() {
	return comparedYear;
}
public void setComparedYear(String comparedYear) {
	this.comparedYear = comparedYear;
}
public String getComparedMonth() {
	return comparedMonth;
}
public void setComparedMonth(String comparedMonth) {
	this.comparedMonth = comparedMonth;
}
public String getReqYear() {
	return reqYear;
}
public void setReqYear(String reqYear) {
	this.reqYear = reqYear;
}
public String getReqMonth() {
	return reqMonth;
}
public void setReqMonth(String reqMonth) {
	this.reqMonth = reqMonth;
}



}
