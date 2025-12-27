package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class MonthlyGlSchedule extends ActionForm{
private String pageId;
private String year,month,forward,types,codes,validations,flag,toTypes,toCodes,printFile;



public String getPrintFile() {
	return printFile;
}

public void setPrintFile(String printFile) {
	this.printFile = printFile;
}

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

public String getTypes() {
	return types;
}

public void setTypes(String types) {
	this.types = types;
}

public String getCodes() {
	return codes;
}

public void setCodes(String codes) {
	this.codes = codes;
}

public String getToTypes() {
	return toTypes;
}

public void setToTypes(String toTypes) {
	this.toTypes = toTypes;
}

public String getToCodes() {
	return toCodes;
}

public void setToCodes(String toCodes) {
	this.toCodes = toCodes;
}


}
