package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class BouncedregForm extends ActionForm{
String pageId,to_date,from_date,validateFlag,flag;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getTo_date() {
	return to_date;
}

public void setTo_date(String to_date) {
	this.to_date = to_date;
}

public String getFrom_date() {
	return from_date;
}

public void setFrom_date(String from_date) {
	this.from_date = from_date;
}

public String getValidateFlag() {
	return validateFlag;
}

public void setValidateFlag(String validateFlag) {
	this.validateFlag = validateFlag;
}

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}
}
