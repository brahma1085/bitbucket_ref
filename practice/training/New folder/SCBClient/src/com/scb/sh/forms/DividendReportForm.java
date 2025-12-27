package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class DividendReportForm  extends ActionForm{
  private String pageId;
  private String actype,acno,frm_dt,to_dt,pay_type;
  private String forward,view,file,print,clear;

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

public String getAcno() {
	return acno;
}

public void setAcno(String acno) {
	this.acno = acno;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getFrm_dt() {
	return frm_dt;
}

public void setFrm_dt(String frm_dt) {
	this.frm_dt = frm_dt;
}

public String getTo_dt() {
	return to_dt;
}

public void setTo_dt(String to_dt) {
	this.to_dt = to_dt;
}

public String getPay_type() {
	return pay_type;
}

public void setPay_type(String pay_type) {
	this.pay_type = pay_type;
}

public String getView() {
	return view;
}

public void setView(String view) {
	this.view = view;
}

public String getFile() {
	return file;
}

public void setFile(String file) {
	this.file = file;
}

public String getPrint() {
	return print;
}

public void setPrint(String print) {
	this.print = print;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}
}
