package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDMaturityListForm extends ActionForm {
	
	private String pageId,txt_fromdate,txt_todate,combo_acctype,button_value,clear,value,but_print,testing;
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

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTxt_fromdate() {
		return txt_fromdate;
	}

	public void setTxt_fromdate(String txt_fromdate) {
		this.txt_fromdate = txt_fromdate;
	}

	public String getTxt_todate() {
		return txt_todate;
	}

	public void setTxt_todate(String txt_todate) {
		this.txt_todate = txt_todate;
	}

	public String getCombo_acctype() {
		return combo_acctype;
	}

	public void setCombo_acctype(String combo_acctype) {
		this.combo_acctype = combo_acctype;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
