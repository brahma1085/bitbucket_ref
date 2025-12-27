package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDTransferForm extends ActionForm {
	
	private String txt_voucherno,txt_tranfrom,txt_acNum,txt_custname,txt_bal,txt_entAmo;

	public String getTxt_voucherno() {
		return txt_voucherno;
	}

	public void setTxt_voucherno(String txt_voucherno) {
		this.txt_voucherno = txt_voucherno;
	}

	public String getTxt_tranfrom() {
		return txt_tranfrom;
	}

	public void setTxt_tranfrom(String txt_tranfrom) {
		this.txt_tranfrom = txt_tranfrom;
	}

	public String getTxt_acNum() {
		return txt_acNum;
	}

	public void setTxt_acNum(String txt_acNum) {
		this.txt_acNum = txt_acNum;
	}

	public String getTxt_custname() {
		return txt_custname;
	}

	public void setTxt_custname(String txt_custname) {
		this.txt_custname = txt_custname;
	}

	public String getTxt_bal() {
		return txt_bal;
	}

	public void setTxt_bal(String txt_bal) {
		this.txt_bal = txt_bal;
	}

	public String getTxt_entAmo() {
		return txt_entAmo;
	}

	public void setTxt_entAmo(String txt_entAmo) {
		this.txt_entAmo = txt_entAmo;
	}

}
