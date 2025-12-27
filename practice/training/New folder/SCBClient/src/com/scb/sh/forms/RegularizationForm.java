package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class RegularizationForm extends ActionForm {
 private String pageId;
 private String forward,display,submit,clear;
 private String temp_num,perm_num;
 private String[]  temp_no;





public String getTemp_num() {
	return temp_num;
}

public void setTemp_num(String temp_num) {
	this.temp_num = temp_num;
}

public String[] getTemp_no() {
	return temp_no;
}

public void setTemp_no(String[] temp_no) {
	this.temp_no = temp_no;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getDisplay() {
	return display;
}

public void setDisplay(String display) {
	this.display = display;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getSubmit() {
	return submit;
}

public void setSubmit(String submit) {
	this.submit = submit;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}

public String getPerm_num() {
	return perm_num;
}

public void setPerm_num(String perm_num) {
	this.perm_num = perm_num;
}
}
