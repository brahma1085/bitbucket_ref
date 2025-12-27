package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class MailingChargesForm  extends ActionForm{

	String pageId,flag,validateFlag,form_flag,error_flag,error_message,selectAll,chkBox;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

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

	public String getForm_flag() {
		return form_flag;
	}

	public void setForm_flag(String form_flag) {
		this.form_flag = form_flag;
	}

	public String getError_flag() {
		return error_flag;
	}

	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}

	public String getChkBox() {
		return chkBox;
	}

	public void setChkBox(String chkBox) {
		this.chkBox = chkBox;
	}
}
