package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class MailSys extends ActionForm {
	private String ac_type,paydate,pageId,mailbut,testing,flag;
	private int validate;
	private int value;
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public int getValidate() {
		return validate;
	}
	public void setValidate(int validate) {
		this.validate = validate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getMailbut() {
		return mailbut;
	}
	public void setMailbut(String mailbut) {
		this.mailbut = mailbut;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
