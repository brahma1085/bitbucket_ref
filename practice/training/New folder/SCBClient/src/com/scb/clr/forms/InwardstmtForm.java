package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class InwardstmtForm extends ActionForm{
String pageId,bank_name,clr_date,clr_no,but_value,validation;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getBank_name() {
	return bank_name;
}

public void setBank_name(String bank_name) {
	this.bank_name = bank_name;
}

public String getClr_no() {
	return clr_no;
}

public void setClr_no(String clr_no) {
	this.clr_no = clr_no;
}

public String getClr_date() {
	return clr_date;
}

public void setClr_date(String clr_date) {
	this.clr_date = clr_date;
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
