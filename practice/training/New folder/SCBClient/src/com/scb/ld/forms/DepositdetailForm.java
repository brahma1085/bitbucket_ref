package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class DepositdetailForm extends ActionForm{
	String txt_matdate,txt_depdate,txt_depintrate,txt_amt,txt_matamount,txt_period;

	public String getTxt_matdate() {
		return txt_matdate;
	}

	public void setTxt_matdate(String txt_matdate) {
		this.txt_matdate = txt_matdate;
	}

	public String getTxt_depdate() {
		return txt_depdate;
	}

	public void setTxt_depdate(String txt_depdate) {
		this.txt_depdate = txt_depdate;
	}

	public String getTxt_period() {
		return txt_period;
	}

	public void setTxt_period(String txt_period) {
		this.txt_period = txt_period;
	}

	public String getTxt_depintrate() {
		return txt_depintrate;
	}

	public void setTxt_depintrate(String txt_depintrate) {
		this.txt_depintrate = txt_depintrate;
	}

	public String getTxt_amt() {
		return txt_amt;
	}

	public void setTxt_amt(String txt_amt) {
		this.txt_amt = txt_amt;
	}

	public String getTxt_matamount() {
		return txt_matamount;
	}

	public void setTxt_matamount(String txt_matamount) {
		this.txt_matamount = txt_matamount;
	}

}
