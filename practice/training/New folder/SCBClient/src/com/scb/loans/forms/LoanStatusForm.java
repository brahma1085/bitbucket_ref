package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class LoanStatusForm extends ActionForm{
	
	
	private String txt_addpaid,txt_ppoverdue,txt_current_inst,txt_interest,txt_pinealInt,txt_otherchar,txt_extra,txt_tca,txt_totcloamt;
	private double txt_loanba;
	

	public double getTxt_loanba() {
		return txt_loanba;
	}

	public void setTxt_loanba(double txt_loanba) {
		this.txt_loanba = txt_loanba;
	}

	public String getTxt_addpaid() {
		return txt_addpaid;
	}

	public void setTxt_addpaid(String txt_addpaid) {
		this.txt_addpaid = txt_addpaid;
	}

	public String getTxt_ppoverdue() {
		return txt_ppoverdue;
	}

	public void setTxt_ppoverdue(String txt_ppoverdue) {
		this.txt_ppoverdue = txt_ppoverdue;
	}

	public String getTxt_current_inst() {
		return txt_current_inst;
	}

	public void setTxt_current_inst(String txt_current_inst) {
		this.txt_current_inst = txt_current_inst;
	}

	public String getTxt_interest() {
		return txt_interest;
	}

	public void setTxt_interest(String txt_interest) {
		this.txt_interest = txt_interest;
	}

	public String getTxt_pinealInt() {
		return txt_pinealInt;
	}

	public void setTxt_pinealInt(String txt_pinealInt) {
		this.txt_pinealInt = txt_pinealInt;
	}

	public String getTxt_otherchar() {
		return txt_otherchar;
	}

	public void setTxt_otherchar(String txt_otherchar) {
		this.txt_otherchar = txt_otherchar;
	}

	public String getTxt_extra() {
		return txt_extra;
	}

	public void setTxt_extra(String txt_extra) {
		this.txt_extra = txt_extra;
	}

	public String getTxt_tca() {
		return txt_tca;
	}

	public void setTxt_tca(String txt_tca) {
		this.txt_tca = txt_tca;
	}

	public String getTxt_totcloamt() {
		return txt_totcloamt;
	}

	public void setTxt_totcloamt(String txt_totcloamt) {
		this.txt_totcloamt = txt_totcloamt;
	}

}
