package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class AccessToTmlFormBean extends ActionForm{
	private String pageId;
	private String validations,tmlNo,flag,uid,tmlName,tmlDesc,ckBox;
	
	public String getCkBox() {
		return ckBox;
	}
	public void setCkBox(String ckBox) {
		this.ckBox = ckBox;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTmlNo() {
		return tmlNo;
	}
	public void setTmlNo(String tmlNo) {
		this.tmlNo = tmlNo;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTmlName() {
		return tmlName;
	}
	public void setTmlName(String tmlName) {
		this.tmlName = tmlName;
	}
	public String getTmlDesc() {
		return tmlDesc;
	}
	public void setTmlDesc(String tmlDesc) {
		this.tmlDesc = tmlDesc;
	}
	
	}
