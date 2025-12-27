package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ControlNoForm extends ActionForm {
String pageId,from_date,to_date,but_value,validation;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}


public String getFrom_date() {
	return from_date;
}

public void setFrom_date(String from_date) {
	this.from_date = from_date;
}

public String getTo_date() {
	return to_date;
}

public void setTo_date(String to_date) {
	this.to_date = to_date;
}

public String getBut_value() {
	return but_value;
}

public void setBut_value(String but_value) {
	this.but_value = but_value;
}

public String getValidation() {
	return validation;
}

public void setValidation(String validation) {
	this.validation = validation;
}
}
