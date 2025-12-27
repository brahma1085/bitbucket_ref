package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class LoanSactionDisburse extends ActionForm
{
	String ln_ac_type,ln_shr_type,ln_details,ln_paymode,ln_paytype,ln_custname,pageId,testing,forward,temp;
	int ln_acno,ln_sharenumber,ln_period,ln_months,ln_payac_no,ln_purpose,ln_priority;
	double ln_amount,ln_intrate,ln_penalrate,ln_disbamtleft,ln_installments;
	boolean ln_weaksectionchk;
	public String getLn_ac_type() {
		return ln_ac_type;
	}
	public void setLn_ac_type(String ln_ac_type) {
		this.ln_ac_type = ln_ac_type;
	}
	public String getLn_shr_type() {
		return ln_shr_type;
	}
	public void setLn_shr_type(String ln_shr_type) {
		this.ln_shr_type = ln_shr_type;
	}
	
	public int getLn_purpose() {
		return ln_purpose;
	}
	public void setLn_purpose(int ln_purpose) {
		this.ln_purpose = ln_purpose;
	}
	public String getLn_details() {
		return ln_details;
	}
	public void setLn_details(String ln_details) {
		this.ln_details = ln_details;
	}
	
	public int getLn_priority() {
		return ln_priority;
	}
	public void setLn_priority(int ln_priority) {
		this.ln_priority = ln_priority;
	}
	public String getLn_paymode() {
		return ln_paymode;
	}
	public void setLn_paymode(String ln_paymode) {
		this.ln_paymode = ln_paymode;
	}
	public String getLn_paytype() {
		return ln_paytype;
	}
	public void setLn_paytype(String ln_paytype) {
		this.ln_paytype = ln_paytype;
	}
	public String getLn_custname() {
		return ln_custname;
	}
	public void setLn_custname(String ln_custname) {
		this.ln_custname = ln_custname;
	}
	public int getLn_acno() {
		return ln_acno;
	}
	public void setLn_acno(int ln_acno) {
		this.ln_acno = ln_acno;
	}
	public int getLn_sharenumber() {
		return ln_sharenumber;
	}
	public void setLn_sharenumber(int ln_sharenumber) {
		this.ln_sharenumber = ln_sharenumber;
	}
	
	public double getLn_amount() {
		return ln_amount;
	}
	public void setLn_amount(double ln_amount) {
		this.ln_amount = ln_amount;
	}
	public int getLn_period() {
		return ln_period;
	}
	public void setLn_period(int ln_period) {
		this.ln_period = ln_period;
	}
	public int getLn_months() {
		return ln_months;
	}
	public void setLn_months(int ln_months) {
		this.ln_months = ln_months;
	}
	
	
	public double getLn_installments() {
		return ln_installments;
	}
	public void setLn_installments(double ln_installments) {
		this.ln_installments = ln_installments;
	}
	public double getLn_intrate() {
		return ln_intrate;
	}
	public void setLn_intrate(double ln_intrate) {
		this.ln_intrate = ln_intrate;
	}
	
	public double getLn_penalrate() {
		return ln_penalrate;
	}
	public void setLn_penalrate(double ln_penalrate) {
		this.ln_penalrate = ln_penalrate;
	}
	
	public double getLn_disbamtleft() {
		return ln_disbamtleft;
	}
	public void setLn_disbamtleft(double ln_disbamtleft) {
		this.ln_disbamtleft = ln_disbamtleft;
	}
	public int getLn_payac_no() {
		return ln_payac_no;
	}
	public void setLn_payac_no(int ln_payac_no) {
		this.ln_payac_no = ln_payac_no;
	}
	public boolean isLn_weaksectionchk() {
		return ln_weaksectionchk;
	}
	public void setLn_weaksectionchk(boolean ln_weaksectionchk) {
		this.ln_weaksectionchk = ln_weaksectionchk;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
}
