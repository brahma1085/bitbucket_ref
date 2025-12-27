package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDOpenClosedForm extends ActionForm{
	
	private String combo_account,combo_acctype,txt_frm_dt,txt_to_dt,pageId,button_value,clear,but_print,testing;
	String sysdate;
	

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}

	public String getBut_print() {
		return but_print;
	}

	public void setBut_print(String butPrint) {
		but_print = butPrint;
	}

	public String getCombo_account() {
		return combo_account;
	}

	public void setCombo_account(String combo_account) {
		this.combo_account = combo_account;
	}

	public String getCombo_acctype() {
		return combo_acctype;
	}

	public void setCombo_acctype(String combo_acctype) {
		this.combo_acctype = combo_acctype;
	}

	public String getTxt_frm_dt() {
		return txt_frm_dt;
	}

	public void setTxt_frm_dt(String txt_frm_dt) {
		this.txt_frm_dt = txt_frm_dt;
	}

	public String getTxt_to_dt() {
		return txt_to_dt;
	}

	public void setTxt_to_dt(String txt_to_dt) {
		this.txt_to_dt = txt_to_dt;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

}
