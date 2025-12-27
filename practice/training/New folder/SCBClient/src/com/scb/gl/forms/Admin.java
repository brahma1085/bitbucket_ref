package com.scb.gl.forms;

import org.apache.struts.action.ActionForm;

public class Admin extends ActionForm{
private String pageId;
private String table,forward,validations,flag;



public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}

public String getTable() {
	return table;
}

public void setTable(String table) {
	this.table = table;
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
}
