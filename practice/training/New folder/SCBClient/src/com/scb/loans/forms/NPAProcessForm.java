package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class NPAProcessForm extends ActionForm
{
	private String txt_processdate,checkbox_prinwise,checkbox_Intrestwise,checkbox_InOperativewise,radio_180,radio_90,txt_loanacctype,txt_loantype,txt_startno,txt_endNo,button_val;
	private PageIdForm  pageidentity= new PageIdForm();
	String sysdate;
	public String getSysdate() {
			return sysdate;
		}
		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_processdate() {
		return txt_processdate;
	}

	public void setTxt_processdate(String txt_processdate) {
		this.txt_processdate = txt_processdate;
	}

	public String getCheckbox_prinwise() {
		return checkbox_prinwise;
	}

	public void setCheckbox_prinwise(String checkbox_prinwise) {
		this.checkbox_prinwise = checkbox_prinwise;
	}

	public String getCheckbox_Intrestwise() {
		return checkbox_Intrestwise;
	}

	public void setCheckbox_Intrestwise(String checkbox_Intrestwise) {
		this.checkbox_Intrestwise = checkbox_Intrestwise;
	}

	public String getCheckbox_InOperativewise() {
		return checkbox_InOperativewise;
	}

	public void setCheckbox_InOperativewise(String checkbox_InOperativewise) {
		this.checkbox_InOperativewise = checkbox_InOperativewise;
	}

	public String getRadio_180() {
		return radio_180;
	}

	public void setRadio_180(String radio_180) {
		this.radio_180 = radio_180;
	}

	public String getRadio_90() {
		return radio_90;
	}

	public void setRadio_90(String radio_90) {
		this.radio_90 = radio_90;
	}

	public String getTxt_loanacctype() {
		return txt_loanacctype;
	}

	public void setTxt_loanacctype(String txt_loanacctype) {
		this.txt_loanacctype = txt_loanacctype;
	}

	public String getTxt_loantype() {
		return txt_loantype;
	}

	public void setTxt_loantype(String txt_loantype) {
		this.txt_loantype = txt_loantype;
	}

	public String getTxt_startno() {
		return txt_startno;
	}

	public void setTxt_startno(String txt_startno) {
		this.txt_startno = txt_startno;
	}

	public String getTxt_endNo() {
		return txt_endNo;
	}

	public void setTxt_endNo(String txt_endNo) {
		this.txt_endNo = txt_endNo;
	}

	public String getButton_val() {
		return button_val;
	}

	public void setButton_val(String button_val) {
		this.button_val = button_val;
	}
}
