package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class DividendDataForm extends ActionForm{

	private String pageId;
	private String acctype,acno,div_dt,div_amt,pay_mode,pay_ac_type,pay_ac_no,name,acname;
    private String forward,clear,submit,validations;
	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getAcno() {
		return acno;
	}

	public void setAcno(String acno) {
		this.acno = acno;
	}

	public String getDiv_dt() {
		return div_dt;
	}

	public void setDiv_dt(String div_dt) {
		this.div_dt = div_dt;
	}

	public String getDiv_amt() {
		return div_amt;
	}

	public void setDiv_amt(String div_amt) {
		this.div_amt = div_amt;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getPay_ac_type() {
		return pay_ac_type;
	}

	public void setPay_ac_type(String pay_ac_type) {
		this.pay_ac_type = pay_ac_type;
	}

	public String getPay_ac_no() {
		return pay_ac_no;
	}

	public void setPay_ac_no(String pay_ac_no) {
		this.pay_ac_no = pay_ac_no;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcname() {
		return acname;
	}

	public void setAcname(String acname) {
		this.acname = acname;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}
	
}
