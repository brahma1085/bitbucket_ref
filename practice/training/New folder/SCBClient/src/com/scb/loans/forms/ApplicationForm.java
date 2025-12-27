package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class ApplicationForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getPayactype() {
		return payactype;
	}

	public void setPayactype(int payactype) {
		this.payactype = payactype;
	}

	public int getPayaccno() {
		return payaccno;
	}

	public void setPayaccno(int payaccno) {
		this.payaccno = payaccno;
	}

	public String getRelativetodir() {
		return relativetodir;
	}

	public void setRelativetodir(String relativetodir) {
		this.relativetodir = relativetodir;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String[] getDirdetails() {
		return dirdetails;
	}

	public void setDirdetails(String[] dirdetails) {
		this.dirdetails = dirdetails;
	}

	public String[] getDirrelations() {
		return dirrelations;
	}

	public void setDirrelations(String[] dirrelations) {
		this.dirrelations = dirrelations;
	}

	int appln_no, interesttype, interestcalctype, payactype, payaccno;
	String appndate, paymtmode, paymentmode, relativetodir, clear;
	String[] dirdetails, dirrelations;
	double reqamount;

	public int getAppln_no() {
		return appln_no;
	}

	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}

	public int getInteresttype() {
		return interesttype;
	}

	public void setInteresttype(int interesttype) {
		this.interesttype = interesttype;
	}

	public int getInterestcalctype() {
		return interestcalctype;
	}

	public void setInterestcalctype(int interestcalctype) {
		this.interestcalctype = interestcalctype;
	}

	public String getAppndate() {
		return appndate;
	}

	public void setAppndate(String appndate) {
		this.appndate = appndate;
	}

	public String getPaymtmode() {
		return paymtmode;
	}

	public void setPaymtmode(String paymtmode) {
		this.paymtmode = paymtmode;
	}

	public double getReqamount() {
		return reqamount;
	}

	public void setReqamount(double reqamount) {
		this.reqamount = reqamount;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
}
