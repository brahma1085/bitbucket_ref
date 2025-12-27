package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class VoucherSlipPrinting extends ActionForm{
private String pageId;
private String forward,validations,slipDate,glTypes,radioType,fromGlNo,toGlNo,printedStationary,flag,printFile;



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

public String getSlipDate() {
	return slipDate;
}

public void setSlipDate(String slipDate) {
	this.slipDate = slipDate;
}

public String getGlTypes() {
	return glTypes;
}

public void setGlTypes(String glTypes) {
	this.glTypes = glTypes;
}

public String getRadioType() {
	return radioType;
}

public void setRadioType(String radioType) {
	this.radioType = radioType;
}

public String getFromGlNo() {
	return fromGlNo;
}

public void setFromGlNo(String fromGlNo) {
	this.fromGlNo = fromGlNo;
}

public String getToGlNo() {
	return toGlNo;
}

public void setToGlNo(String toGlNo) {
	this.toGlNo = toGlNo;
}

public String getPrintedStationary() {
	return printedStationary;
}

public void setPrintedStationary(String printedStationary) {
	this.printedStationary = printedStationary;
}


}
