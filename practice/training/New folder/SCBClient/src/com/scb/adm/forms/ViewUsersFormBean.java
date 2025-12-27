package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class ViewUsersFormBean extends ActionForm{
	private String pageId;
	private String validations,userChoice,printFile,flag,uid,frmDate,toDate;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getUserChoice() {
		return userChoice;
	}
	public void setUserChoice(String userChoice) {
		this.userChoice = userChoice;
	}
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFrmDate() {
		return frmDate;
	}
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
    

}
