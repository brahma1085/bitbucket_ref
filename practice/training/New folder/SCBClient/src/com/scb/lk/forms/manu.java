package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class manu extends ActionForm{

	
	String pageId;
	String lkTypes;
	String cabNo;
	String forward;
	String submit;
	String field1,field2;
	String accountNum;
	
	
	
	
	
	
	
	
	
	
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getLkTypes() {
		return lkTypes;
	}
	public void setLkTypes(String lkTypes) {
		this.lkTypes = lkTypes;
	}
	public String getCabNo() {
		return cabNo;
	}
	public void setCabNo(String cabNo) {
		this.cabNo = cabNo;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	
}
