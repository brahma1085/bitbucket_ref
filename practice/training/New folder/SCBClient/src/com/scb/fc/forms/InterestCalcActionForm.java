package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class InterestCalcActionForm extends ActionForm {
String pageId;
String acType,acno,minibal,todate,nextdate,rad,cal,recal;
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getAcType() {
	return acType;
}
public void setAcType(String acType) {
	this.acType = acType;
}
public String getAcno() {
	return acno;
}
public void setAcno(String acno) {
	this.acno = acno;
}
public String getMinibal() {
	return minibal;
}
public void setMinibal(String minibal) {
	this.minibal = minibal;
}
public String getTodate() {
	return todate;
}
public void setTodate(String todate) {
	this.todate = todate;
}
public String getNextdate() {
	return nextdate;
}
public void setNextdate(String nextdate) {
	this.nextdate = nextdate;
}
public String getRad() {
	return rad;
}
public void setRad(String rad) {
	this.rad = rad;
}
public String getCal() {
	return cal;
}
public void setCal(String cal) {
	this.cal = cal;
}
public String getRecal() {
	return recal;
}
public void setRecal(String recal) {
	this.recal = recal;
}
}
