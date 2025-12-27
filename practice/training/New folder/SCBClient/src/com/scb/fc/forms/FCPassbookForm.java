package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class FCPassbookForm extends ActionForm {
String pageId,save;
String acType,acno,sqno,fdate,tdate;

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
public String getSqno() {
	return sqno;
}
public void setSqno(String sqno) {
	this.sqno = sqno;
}
public String getFdate() {
	return fdate;
}
public void setFdate(String fdate) {
	this.fdate = fdate;
}
public String getTdate() {
	return tdate;
}
public void setTdate(String tdate) {
	this.tdate = tdate;
}
public String getSave() {
	return save;
}
public void setSave(String save) {
	this.save = save;
}
}
