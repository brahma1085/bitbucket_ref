package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class DepositRenewal extends ActionForm{
	
	private String pageId;
	private int ac_no,rct_no,cid;
	private String ac_type,pay_mode,pay_ac_type,details;
	private String dep_date,closure_date,mat_date,detail,forward;
	private double dep_amt,mat_amt;
	private double agreed_int_rate,applied_int_rate;
	private String int_upto_date;
	private double int_amt_paid,int_amt_pay;
	private int  no_of_joint,nominee_reg_no,introducer_ac_no,trn_acno;
	
	
	private boolean loan_avail,new_respt; 
	private String butt_submit,butt_update,butt_clear,butt_del,nojonthd;
	private String combo_pay_mode,combo_details,combo_pay_acno,introducer_ac_type,total_amt,testing,hidval;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
	public int getRct_no() {
		return rct_no;
	}
	public void setRct_no(int rct_no) {
		this.rct_no = rct_no;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public String getClosure_date() {
		return closure_date;
	}
	public void setClosure_date(String closure_date) {
		this.closure_date = closure_date;
	}
	public String getMat_date() {
		return mat_date;
	}
	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}
	public double getDep_amt() {
		return dep_amt;
	}
	public void setDep_amt(double dep_amt) {
		this.dep_amt = dep_amt;
	}
	public double getMat_amt() {
		return mat_amt;
	}
	public void setMat_amt(double mat_amt) {
		this.mat_amt = mat_amt;
	}
	public double getAgreed_int_rate() {
		return agreed_int_rate;
	}
	public void setAgreed_int_rate(double agreed_int_rate) {
		this.agreed_int_rate = agreed_int_rate;
	}
	public double getApplied_int_rate() {
		return applied_int_rate;
	}
	public void setApplied_int_rate(double applied_int_rate) {
		this.applied_int_rate = applied_int_rate;
	}
	public String getInt_upto_date() {
		return int_upto_date;
	}
	public void setInt_upto_date(String int_upto_date) {
		this.int_upto_date = int_upto_date;
	}
	public double getInt_amt_paid() {
		return int_amt_paid;
	}
	public void setInt_amt_paid(double int_amt_paid) {
		this.int_amt_paid = int_amt_paid;
	}
	public double getInt_amt_pay() {
		return int_amt_pay;
	}
	public void setInt_amt_pay(double int_amt_pay) {
		this.int_amt_pay = int_amt_pay;
	}
	public int getNo_of_joint() {
		return no_of_joint;
	}
	public void setNo_of_joint(int no_of_joint) {
		this.no_of_joint = no_of_joint;
	}
	public int getNominee_reg_no() {
		return nominee_reg_no;
	}
	public void setNominee_reg_no(int nominee_reg_no) {
		this.nominee_reg_no = nominee_reg_no;
	}
	public boolean isLoan_avail() {
		return loan_avail;
	}
	public void setLoan_avail(boolean loan_avail) {
		this.loan_avail = loan_avail;
	}
	public String getButt_submit() {
		return butt_submit;
	}
	public void setButt_submit(String butt_submit) {
		this.butt_submit = butt_submit;
	}
	public String getButt_update() {
		return butt_update;
	}
	public void setButt_update(String butt_update) {
		this.butt_update = butt_update;
	}
	public String getButt_clear() {
		return butt_clear;
	}
	public void setButt_clear(String butt_clear) {
		this.butt_clear = butt_clear;
	}
	public String getButt_del() {
		return butt_del;
	}
	public void setButt_del(String butt_del) {
		this.butt_del = butt_del;
	}
	public String getCombo_pay_mode() {
		return combo_pay_mode;
	}
	public void setCombo_pay_mode(String combo_pay_mode) {
		this.combo_pay_mode = combo_pay_mode;
	}
	public String getCombo_pay_acno() {
		return combo_pay_acno;
	}
	public void setCombo_pay_acno(String combo_pay_acno) {
		this.combo_pay_acno = combo_pay_acno;
	}
	public String getCombo_details() {
		return combo_details;
	}
	public void setCombo_details(String combo_details) {
		this.combo_details = combo_details;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getIntroducer_ac_no() {
		return introducer_ac_no;
	}
	public void setIntroducer_ac_no(int introducer_ac_no) {
		this.introducer_ac_no = introducer_ac_no;
	}
	public String getIntroducer_ac_type() {
		return introducer_ac_type;
	}
	public void setIntroducer_ac_type(String introducer_ac_type) {
		this.introducer_ac_type = introducer_ac_type;
	}
	public String getNojonthd() {
		return nojonthd;
	}
	public void setNojonthd(String nojonthd) {
		this.nojonthd = nojonthd;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public int getTrn_acno() {
		return trn_acno;
	}
	public void setTrn_acno(int trn_acno) {
		this.trn_acno = trn_acno;
	}
	public String getTotal_amt() {
		return total_amt;
	}
	public void setTotal_amt(String total_amt) {
		this.total_amt = total_amt;
	}
	public boolean isNew_respt() {
		return new_respt;
	}
	public void setNew_respt(boolean new_respt) {
		this.new_respt = new_respt;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getHidval() {
		return hidval;
	}
	public void setHidval(String hidval) {
		this.hidval = hidval;
	}
	

	
	
	

}
