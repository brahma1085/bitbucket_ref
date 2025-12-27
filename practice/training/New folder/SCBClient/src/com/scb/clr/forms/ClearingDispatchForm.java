package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ClearingDispatchForm extends ActionForm{

	private String pageId;
	private String clr_date,clr_no,clgBank;
	
	
	private String flag,validateFlag,button_submit; 
	
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
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
	public String getClgBank() {
		return clgBank;
	}
	public void setClgBank(String clgBank) {
		this.clgBank = clgBank;
	}
	public String getButton_submit() {
		return button_submit;
	}
	public void setButton_submit(String button_submit) {
		this.button_submit = button_submit;
	}

}
