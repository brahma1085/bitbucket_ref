package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class ConsolidatedDayBook extends ActionForm{
private String pageId;
private String forward,validations,date,brConsolidation,conDayBook,dayBook,trialBalance,flag,printFile;




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

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getBrConsolidation() {
	return brConsolidation;
}

public void setBrConsolidation(String brConsolidation) {
	this.brConsolidation = brConsolidation;
}

public String getConDayBook() {
	return conDayBook;
}

public void setConDayBook(String conDayBook) {
	this.conDayBook = conDayBook;
}

public String getDayBook() {
	return dayBook;
}

public void setDayBook(String dayBook) {
	this.dayBook = dayBook;
}

public String getTrialBalance() {
	return trialBalance;
}

public void setTrialBalance(String trialBalance) {
	this.trialBalance = trialBalance;
}


}
