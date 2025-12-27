package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanPassBookForm extends ActionForm{
	
	private String txt_loancatAccno,txt_name,txt_SBNo,txt_purpose,txt_sharenum,txt_intrate,txt_intpaidupto,txt_Sanctiondate,txt_nominee_no,txt_sanctionammt,txt_phoneno,txt_noofInstallment,txt_email,txt_Installmentamt,txt_suritydetail,validate_accnum,button_value;
	private int txt_accnum;
	private PageIdForm  pageidentity= new PageIdForm();
	
	public String getTxt_suritydetail() {
		return txt_suritydetail;
	}

	public void setTxt_suritydetail(String txt_suritydetail) {
		this.txt_suritydetail = txt_suritydetail;
	}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_loancatAccno() {
		return txt_loancatAccno;
	}

	public void setTxt_loancatAccno(String txt_loancatAccno) {
		this.txt_loancatAccno = txt_loancatAccno;
	}

	public String getTxt_name() {
		return txt_name;
	}

	public void setTxt_name(String txt_name) {
		this.txt_name = txt_name;
	}

	public String getTxt_SBNo() {
		return txt_SBNo;
	}

	public void setTxt_SBNo(String txt_SBNo) {
		this.txt_SBNo = txt_SBNo;
	}

	public String getTxt_purpose() {
		return txt_purpose;
	}

	public void setTxt_purpose(String txt_purpose) {
		this.txt_purpose = txt_purpose;
	}

	public String getTxt_sharenum() {
		return txt_sharenum;
	}

	public void setTxt_sharenum(String txt_sharenum) {
		this.txt_sharenum = txt_sharenum;
	}

	public String getTxt_intrate() {
		return txt_intrate;
	}

	public void setTxt_intrate(String txt_intrate) {
		this.txt_intrate = txt_intrate;
	}

	public String getTxt_intpaidupto() {
		return txt_intpaidupto;
	}

	public void setTxt_intpaidupto(String txt_intpaidupto) {
		this.txt_intpaidupto = txt_intpaidupto;
	}

	public String getTxt_Sanctiondate() {
		return txt_Sanctiondate;
	}

	public void setTxt_Sanctiondate(String txt_Sanctiondate) {
		this.txt_Sanctiondate = txt_Sanctiondate;
	}

	public String getTxt_nominee_no() {
		return txt_nominee_no;
	}

	public void setTxt_nominee_no(String txt_nominee_no) {
		this.txt_nominee_no = txt_nominee_no;
	}

	public String getTxt_sanctionammt() {
		return txt_sanctionammt;
	}

	public void setTxt_sanctionammt(String txt_sanctionammt) {
		this.txt_sanctionammt = txt_sanctionammt;
	}

	public String getTxt_phoneno() {
		return txt_phoneno;
	}

	public void setTxt_phoneno(String txt_phoneno) {
		this.txt_phoneno = txt_phoneno;
	}

	public String getTxt_noofInstallment() {
		return txt_noofInstallment;
	}

	public void setTxt_noofInstallment(String txt_noofInstallment) {
		this.txt_noofInstallment = txt_noofInstallment;
	}

	public String getTxt_email() {
		return txt_email;
	}

	public void setTxt_email(String txt_email) {
		this.txt_email = txt_email;
	}

	public String getTxt_Installmentamt() {
		return txt_Installmentamt;
	}

	public void setTxt_Installmentamt(String txt_Installmentamt) {
		this.txt_Installmentamt = txt_Installmentamt;
	}

	public int getTxt_accnum() {
		return txt_accnum;
	}

	public void setTxt_accnum(int txt_accnum) {
		this.txt_accnum = txt_accnum;
	}

	public String getValidate_accnum() {
		return validate_accnum;
	}

	public void setValidate_accnum(String validate_accnum) {
		this.validate_accnum = validate_accnum;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	

}
