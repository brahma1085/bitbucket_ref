package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class LoanandShareDetailsForm extends ActionForm{
   private double max_loan_amt,loan_avail,tot_no_shares,tot_share_value;

public double getMax_loan_amt() {
	return max_loan_amt;
}

public void setMax_loan_amt(double max_loan_amt) {
	this.max_loan_amt = max_loan_amt;
}

public double getLoan_avail() {
	return loan_avail;
}

public void setLoan_avail(double loan_avail) {
	this.loan_avail = loan_avail;
}

public double getTot_no_shares() {
	return tot_no_shares;
}

public void setTot_no_shares(double tot_no_shares) {
	this.tot_no_shares = tot_no_shares;
}

public double getTot_share_value() {
	return tot_share_value;
}

public void setTot_share_value(double tot_share_value) {
	this.tot_share_value = tot_share_value;
}
}
