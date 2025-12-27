package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class TrfScrollPrinting extends ActionForm{
private String pageId;
private String fromDate,toDate,forward,types,codes,validations,flag,printFile;



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

public String getFromDate() {
	return fromDate;
}

public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}

public String getToDate() {
	return toDate;
}

public void setToDate(String toDate) {
	this.toDate = toDate;
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


}
