package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ClearingIdentificationForm extends ActionForm{
	
	
	String clgDate,clgNum,sendTo,flag,pageId,sourceName,validateFlag;
	String error_message,today_date;
	int error_flag,form_flag,reason_codes;

	
	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getToday_date() {
		return today_date;
	}

	public void setToday_date(String today_date) {
		this.today_date = today_date;
	}

	public int getError_flag() {
		return error_flag;
	}

	public void setError_flag(int error_flag) {
		this.error_flag = error_flag;
	}

	public int getForm_flag() {
		return form_flag;
	}

	public void setForm_flag(int form_flag) {
		this.form_flag = form_flag;
	}

	public int getReason_codes() {
		return reason_codes;
	}

	public void setReason_codes(int reason_codes) {
		this.reason_codes = reason_codes;
	}

	public String getClgNum() {
		return clgNum;
	}

	public void setClgNum(String clgNum) {
		this.clgNum = clgNum;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getClgDate() {
		return clgDate;
	}

	public void setClgDate(String clgDate) {
		this.clgDate = clgDate;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	
	

}
