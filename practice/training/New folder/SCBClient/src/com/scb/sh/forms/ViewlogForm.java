package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class ViewlogForm extends ActionForm {
	
  private String pageId;
  private String actype,shnum,validations,forward;
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getActype() {
	return actype;
}
public void setActype(String actype) {
	this.actype = actype;
}
public String getShnum() {
	return shnum;
}
public void setShnum(String shnum) {
	this.shnum = shnum;
}
public String getValidations() {
	return validations;
}
public void setValidations(String validations) {
	this.validations = validations;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
}
