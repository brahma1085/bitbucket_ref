package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class LoanDetails extends ActionForm{
	private String loan_ac_type,loan_ac_no,loanee_name,ln_int_upto_date,sanc_amt,tot_bal,ln_interest_bal,details;
	private double ln_principal_bal,sanc_date;
	public String getLoan_ac_type() {
		return loan_ac_type;
	}
	
	public void setLoan_ac_type(String loan_ac_type) {
		this.loan_ac_type = loan_ac_type;
	}
	public String getLoan_ac_no() {
		return loan_ac_no;
	}
	public void setLoan_ac_no(String loan_ac_no) {
		this.loan_ac_no = loan_ac_no;
	}
	public String getLoanee_name() {
		return loanee_name;
	}
	public void setLoanee_name(String loanee_name) {
		this.loanee_name = loanee_name;
	}
	public String getLn_int_upto_date() {
		return ln_int_upto_date;
	}
	public void setLn_int_upto_date(String ln_int_upto_date) {
		this.ln_int_upto_date = ln_int_upto_date;
	}
	public String getSanc_amt() {
		return sanc_amt;
	}
	public void setSanc_amt(String sanc_amt) {
		this.sanc_amt = sanc_amt;
	}
	public String getTot_bal() {
		return tot_bal;
	}
	public void setTot_bal(String tot_bal) {
		this.tot_bal = tot_bal;
	}
	public String getLn_interest_bal() {
		return ln_interest_bal;
	}
	public void setLn_interest_bal(String ln_interest_bal) {
		this.ln_interest_bal = ln_interest_bal;
	}
	public double getLn_principal_bal() {
		return ln_principal_bal;
	}
	public void setLn_principal_bal(double ln_principal_bal) {
		this.ln_principal_bal = ln_principal_bal;
	}
	public double getSanc_date() {
		return sanc_date;
	}
	public void setSanc_date(double sanc_date) {
		this.sanc_date = sanc_date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}
