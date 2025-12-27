package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class UnIdentify  extends ActionForm{
	
	private String pageId,flag,validateFlag,clr_date,sent_to,clr_no,buttonUnIdentify;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId=pageId;
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

	public String getClr_date() {
		return clr_date;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
	}

	public String getSent_to() {
		return sent_to;
	}

	public void setSent_to(String sent_to) {
		this.sent_to = sent_to;
	}

	public String getClr_no() {
		return clr_no;
	}

	public void setClr_no(String clr_no) {
		this.clr_no = clr_no;
	}

	public String getButtonUnIdentify() {
		return buttonUnIdentify;
	}

	public void setButtonUnIdentify(String buttonUnIdentify) {
		this.buttonUnIdentify = buttonUnIdentify;
	}

}
