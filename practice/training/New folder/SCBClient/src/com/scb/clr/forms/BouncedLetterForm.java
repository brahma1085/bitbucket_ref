package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class BouncedLetterForm extends ActionForm {
String pageId,inoutward,clr_date,clr_no,dest_bank,but_value,validation,flag;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getInoutward() {
	return inoutward;
}

public void setInoutward(String inoutward) {
	this.inoutward = inoutward;
}

public String getClr_date() {
	return clr_date;
}

public void setClr_date(String clr_date) {
	this.clr_date = clr_date;
}

public String getClr_no() {
	return clr_no;
}

public void setClr_no(String clr_no) {
	this.clr_no = clr_no;
}

public String getDest_bank() {
	return dest_bank;
}

public void setDest_bank(String dest_bank) {
	this.dest_bank = dest_bank;
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

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}
}
