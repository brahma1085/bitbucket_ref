package com.scb.csh.forms;

import org.apache.struts.action.ActionForm;

public class ClosingForm extends ActionForm{
String pageId,send_to,clear,but_value,closed,currencytab;
int scroll_no;
double amount;


public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getSend_to() {
	return send_to;
}

public void setSend_to(String send_to) {
	this.send_to = send_to;
}

public int getScroll_no() {
	return scroll_no;
}

public void setScroll_no(int scroll_no) {
	this.scroll_no = scroll_no;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}

public String getBut_value() {
	return but_value;
}

public void setBut_value(String but_value) {
	this.but_value = but_value;
}

public String getClosed() {
	return closed;
}

public void setClosed(String closed) {
	this.closed = closed;
}

public void setCurrencytab(String currencytab) {
	this.currencytab = currencytab;
}

public String getCurrencytab() {
	return currencytab;
}


}
