package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class ChangeofModulesForm extends ActionForm {
private String pageId;
private String actype;
private String forward,insert,delete,clear;

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getInsert() {
	return insert;
}

public void setInsert(String insert) {
	this.insert = insert;
}

public String getDelete() {
	return delete;
}

public void setDelete(String delete) {
	this.delete = delete;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}
}
