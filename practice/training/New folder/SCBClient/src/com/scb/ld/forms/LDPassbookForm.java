package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDPassbookForm extends ActionForm{

	
	private String txt_loanrate,txt_intrate,txt_mailid,txt_acstatus,txt_Nom,txt_phoneno,txt_cid,txt_depacno,pageId,button_value,clear,but_print;
	private String txt_san_amt,txt_mat_amt,txt_mat_date,txt_period,txt_status,txt_Dep_ac,txt_san_date,txt_dep_amt,txt_dep_date,txt_name,combo_actype;
	private int txt_acno;
	
	public String getBut_print() {
		return but_print;
	}
	public void setBut_print(String butPrint) {
		but_print = butPrint;
	}
	public String getTxt_acstatus() {
		return txt_acstatus;
	}
	public void setTxt_acstatus(String txt_acstatus) {
		this.txt_acstatus = txt_acstatus;
	}
	public String getTxt_cid() {
		return txt_cid;
	}
	public void setTxt_cid(String txt_cid) {
		this.txt_cid = txt_cid;
	}
	public String getTxt_Dep_ac() {
		return txt_Dep_ac;
	}
	public void setTxt_Dep_ac(String txt_Dep_ac) {
		this.txt_Dep_ac = txt_Dep_ac;
	}
	public String getTxt_dep_amt() {
		return txt_dep_amt;
	}
	public void setTxt_dep_amt(String txt_dep_amt) {
		this.txt_dep_amt = txt_dep_amt;
	}
	public String getTxt_dep_date() {
		return txt_dep_date;
	}
	public void setTxt_dep_date(String txt_dep_date) {
		this.txt_dep_date = txt_dep_date;
	}
	public String getCombo_actype() {
		return combo_actype;
	}
	public void setCombo_actype(String combo_actype) {
		this.combo_actype = combo_actype;
	}
	public String getTxt_loanrate() {
		return txt_loanrate;
	}
	public void setTxt_loanrate(String txt_loanrate) {
		this.txt_loanrate = txt_loanrate;
	}
	public String getTxt_intrate() {
		return txt_intrate;
	}
	public void setTxt_intrate(String txt_intrate) {
		this.txt_intrate = txt_intrate;
	}
	public String getTxt_mailid() {
		return txt_mailid;
	}
	public void setTxt_mailid(String txt_mailid) {
		this.txt_mailid = txt_mailid;
	}
	public String getTxt_Nom() {
		return txt_Nom;
	}
	public void setTxt_Nom(String txt_Nom) {
		this.txt_Nom = txt_Nom;
	}
	public String getTxt_phoneno() {
		return txt_phoneno;
	}
	public void setTxt_phoneno(String txt_phoneno) {
		this.txt_phoneno = txt_phoneno;
	}
	public String getTxt_depacno() {
		return txt_depacno;
	}
	public void setTxt_depacno(String txt_depacno) {
		this.txt_depacno = txt_depacno;
	}
	public String getTxt_san_amt() {
		return txt_san_amt;
	}
	public void setTxt_san_amt(String txt_san_amt) {
		this.txt_san_amt = txt_san_amt;
	}
	public String getTxt_mat_amt() {
		return txt_mat_amt;
	}
	public void setTxt_mat_amt(String txt_mat_amt) {
		this.txt_mat_amt = txt_mat_amt;
	}
	public String getTxt_mat_date() {
		return txt_mat_date;
	}
	public void setTxt_mat_date(String txt_mat_date) {
		this.txt_mat_date = txt_mat_date;
	}
	public String getTxt_period() {
		return txt_period;
	}
	public void setTxt_period(String txt_period) {
		this.txt_period = txt_period;
	}
	public String getTxt_status() {
		return txt_status;
	}
	public void setTxt_status(String txt_status) {
		this.txt_status = txt_status;
	}
	public String getTxt_san_date() {
		return txt_san_date;
	}
	public void setTxt_san_date(String txt_san_date) {
		this.txt_san_date = txt_san_date;
	}
	public String getTxt_name() {
		return txt_name;
	}
	public void setTxt_name(String txt_name) {
		this.txt_name = txt_name;
	}
	
	public int getTxt_acno() {
		return txt_acno;
	}
	public void setTxt_acno(int txt_acno) {
		this.txt_acno = txt_acno;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getButton_value() {
		return button_value;
	}
	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}
	public String getClear() {
		return clear;
	}
	public void setClear(String clear) {
		this.clear = clear;
	}
}
  