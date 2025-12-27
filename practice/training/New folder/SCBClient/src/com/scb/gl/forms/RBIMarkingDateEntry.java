package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class RBIMarkingDateEntry extends ActionForm{
private String pageId;
private String forward,validations,flag,selcted;




public String getSelcted() {
	return selcted;
}
public void setSelcted(String selcted) {
	this.selcted = selcted;
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
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}



}
