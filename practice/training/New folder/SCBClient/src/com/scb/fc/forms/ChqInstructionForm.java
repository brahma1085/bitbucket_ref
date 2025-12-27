package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ChqInstructionForm extends ActionForm {
String pageId;
String chqins,chqno,payname,date,selectedcombo,chqissuedate,chqamount;

String sysdate;

public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getChqins() {
	return chqins;
}
public void setChqins(String chqins) {
	this.chqins = chqins;
}
public String getChqno() {
	return chqno;
}
public void setChqno(String chqno) {
	this.chqno = chqno;
}
public String getPayname() {
	return payname;
}
public void setPayname(String payname) {
	this.payname = payname;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getSelectedcombo() {
	return selectedcombo;
}
public void setSelectedcombo(String selectedcombo) {
	this.selectedcombo = selectedcombo;
}
public String getChqissuedate() {
	return chqissuedate;
}
public void setChqissuedate(String chqissuedate) {
	this.chqissuedate = chqissuedate;
}
public String getChqamount() {
	return chqamount;
}
public void setChqamount(String chqamount) {
	this.chqamount = chqamount;
}
}
