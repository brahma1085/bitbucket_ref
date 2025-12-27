package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ChqIssueForm extends ActionForm {
	String pageId,accname;
String acType,acnum,leaveno,fromno,tono,dateissue,chbookno,submiting,prechbook,deleting;
public String getPageId() {
	return pageId;
}
public String getAcType() {
	return acType;
}
public void setAcType(String acType) {
	this.acType = acType;
}
public String getAcnum() {
	return acnum;
}
public void setAcnum(String acnum) {
	this.acnum = acnum;
}
public String getLeaveno() {
	return leaveno;
}
public void setLeaveno(String leaveno) {
	this.leaveno = leaveno;
}
public String getFromno() {
	return fromno;
}
public void setFromno(String fromno) {
	this.fromno = fromno;
}
public String getTono() {
	return tono;
}
public void setTono(String tono) {
	this.tono = tono;
}
public String getDateissue() {
	return dateissue;
}
public void setDateissue(String dateissue) {
	this.dateissue = dateissue;
}
public String getChbookno() {
	return chbookno;
}
public void setChbookno(String chbookno) {
	this.chbookno = chbookno;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getSubmiting() {
	return submiting;
}
public void setSubmiting(String submiting) {
	this.submiting = submiting;
}
public String getPrechbook() {
	return prechbook;
}
public void setPrechbook(String prechbook) {
	this.prechbook = prechbook;
}
public String getDeleting() {
	return deleting;
}
public void setDeleting(String deleting) {
	this.deleting = deleting;
}
public String getAccname() {
	return accname;
}
public void setAccname(String accname) {
	this.accname = accname;
}

}
