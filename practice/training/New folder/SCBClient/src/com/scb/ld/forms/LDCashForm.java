package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDCashForm extends ActionForm{
	
	private String txt_acctype,txt_acNum,txt_name,txt_acdesc,txt_interest,txt_prin,txt_paid,txt_intrate,txt_totalamm;
	private int txt_ammount;

	public String getTxt_acctype() {
		return txt_acctype;
	}

	public void setTxt_acctype(String txt_acctype) {
		this.txt_acctype = txt_acctype;
	}

	public String getTxt_acNum() {
		return txt_acNum;
	}

	public void setTxt_acNum(String txt_acNum) {
		this.txt_acNum = txt_acNum;
	}

	public String getTxt_name() {
		return txt_name;
	}

	public void setTxt_name(String txt_name) {
		this.txt_name = txt_name;
	}
	
	public String getTxt_prin() {
		return txt_prin;
	}

	public void setTxt_prin(String txt_prin) {
		this.txt_prin = txt_prin;
	}

	public String getTxt_paid() {
		return txt_paid;
	}

	public void setTxt_paid(String txt_paid) {
		this.txt_paid = txt_paid;
	}

	public String getTxt_intrate() {
		return txt_intrate;
	}

	public void setTxt_intrate(String txt_intrate) {
		this.txt_intrate = txt_intrate;
	}

	public String getTxt_totalamm() {
		return txt_totalamm;
	}

	public void setTxt_totalamm(String txt_totalamm) {
		this.txt_totalamm = txt_totalamm;
	}

	public String getTxt_interest() {
		return txt_interest;
	}

	public void setTxt_interest(String txt_interest) {
		this.txt_interest = txt_interest;
	}

	public String getTxt_acdesc() {
		return txt_acdesc;
	}

	public void setTxt_acdesc(String txt_acdesc) {
		this.txt_acdesc = txt_acdesc;
	}

	public int getTxt_ammount() {
		return txt_ammount;
	}

	public void setTxt_ammount(int txt_ammount) {
		this.txt_ammount = txt_ammount;
	}
	

}
