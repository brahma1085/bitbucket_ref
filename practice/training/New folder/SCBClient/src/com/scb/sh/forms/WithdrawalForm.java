package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class WithdrawalForm extends ActionForm {

	private String pageId;
	private String trn_no,ac_type,sh_no,no_of_sh_al,sh_type,br_code,sh_cat,sh_val,with_type,no_of_sh,tot_amt,details,paymode;
    private String submit,flag,forward,verify,validations;
    private String pay_ac_type,pay_ac_no,balance,amount;
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

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTrn_no() {
		return trn_no;
	}

	public void setTrn_no(String trn_no) {
		this.trn_no = trn_no;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getSh_no() {
		return sh_no;
	}

	public void setSh_no(String sh_no) {
		this.sh_no = sh_no;
	}

	public String getNo_of_sh_al() {
		return no_of_sh_al;
	}

	public void setNo_of_sh_al(String no_of_sh_al) {
		this.no_of_sh_al = no_of_sh_al;
	}

	public String getSh_type() {
		return sh_type;
	}

	public void setSh_type(String sh_type) {
		this.sh_type = sh_type;
	}

	public String getBr_code() {
		return br_code;
	}

	public void setBr_code(String br_code) {
		this.br_code = br_code;
	}

	public String getSh_cat() {
		return sh_cat;
	}

	public void setSh_cat(String sh_cat) {
		this.sh_cat = sh_cat;
	}

	public String getSh_val() {
		return sh_val;
	}

	public void setSh_val(String sh_val) {
		this.sh_val = sh_val;
	}

	public String getWith_type() {
		return with_type;
	}

	public void setWith_type(String with_type) {
		this.with_type = with_type;
	}

	public String getNo_of_sh() {
		return no_of_sh;
	}

	public void setNo_of_sh(String no_of_sh) {
		this.no_of_sh = no_of_sh;
	}

	public String getTot_amt() {
		return tot_amt;
	}

	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}
	
}
