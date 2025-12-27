package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class IntrestAccruedReportForm extends ActionForm {
	
	
	private String combo_acctype,txt_todate,pageId,button_value,clear,but_print,testing;
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

	public String getCombo_acctype() {
		return combo_acctype;
	}

	public void setCombo_acctype(String combo_acctype) {
		this.combo_acctype = combo_acctype;
	}

	public String getTxt_todate() {
		return txt_todate;
	}

	public void setTxt_todate(String txt_todate) {
		this.txt_todate = txt_todate;
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
