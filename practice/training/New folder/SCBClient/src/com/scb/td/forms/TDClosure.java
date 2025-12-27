package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class TDClosure extends ActionForm {
	
	private String pageId;
	private int ac_no,rct_no,cid;
	private String ac_type,pay_mode,pay_ac_type,details;
	private String dep_date,closure_date,mat_date,sanc_date,testing,ac_noo;
	private double dep_amt,mat_amt,ln_principal_bal,ln_interest_bal,sanc_amt,tot_bal;
	private double agreed_int_rate,applied_int_rate;
	private String int_upto_date;
	private double int_amt_paid,int_amt_pay,total_amt,net_amt,inst_amt,amt_collected;
	private int  no_of_joint,nominee_reg_no,introducer_Acno,loan_ac_no;
	
	private String loan_ac_type,loanee_name,ln_int_upto_date;
	
	private String introducerac_type;
	
	private  String dp_type,cat_type, date,flag,detail,nojonthd;
	private int days;
	private double amount;
	
	private String submit;
	private String forward,closure,loantrue,alertdispay;
	
	
	private boolean loan_avail; 
	private String butt_submit,butt_update,butt_clear,butt_del,butt_verify;
	private String combo_pay_mode,combo_pay_acno,combo_details;
	

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
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public int getRct_no() {
		return rct_no;
	}
	public void setRct_no(int rct_no) {
		this.rct_no = rct_no;
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
	public double getInt_amt_pay() {
		return int_amt_pay;
	}
	public void setInt_amt_pay(double int_amt_pay) {
		this.int_amt_pay = int_amt_pay;
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
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getClosure() {
		return closure;
	}
	public void setClosure(String closure) {
		this.closure = closure;
	}
	public String getDp_type() {
		return dp_type;
	}
	public void setDp_type(String dp_type) {
		this.dp_type = dp_type;
	}
	public String getCat_type() {
		return cat_type;
	}
	public void setCat_type(String cat_type) {
		this.cat_type = cat_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public double getTotal_amt() {
		return total_amt;
	}
	public void setTotal_amt(double total_amt) {
		this.total_amt = total_amt;
	}
	public String getButt_verify() {
		return butt_verify;
	}
	public void setButt_verify(String butt_verify) {
		this.butt_verify = butt_verify;
	}
	public double getLn_principal_bal() {
		return ln_principal_bal;
	}
	public void setLn_principal_bal(double ln_principal_bal) {
		this.ln_principal_bal = ln_principal_bal;
	}
	public double getLn_interest_bal() {
		return ln_interest_bal;
	}
	public void setLn_interest_bal(double ln_interest_bal) {
		this.ln_interest_bal = ln_interest_bal;
	}
	public String getLoantrue() {
		return loantrue;
	}
	public void setLoantrue(String loantrue) {
		this.loantrue = loantrue;
	}
	
	public String getLoan_ac_type() {
		return loan_ac_type;
	}
	public void setLoan_ac_type(String loan_ac_type) {
		this.loan_ac_type = loan_ac_type;
	}
	
	public int getLoan_ac_no() {
		return loan_ac_no;
	}
	public void setLoan_ac_no(int loan_ac_no) {
		this.loan_ac_no = loan_ac_no;
	}
	public String getSanc_date() {
		return sanc_date;
	}
	public void setSanc_date(String sanc_date) {
		this.sanc_date = sanc_date;
	}
	public double getSanc_amt() {
		return sanc_amt;
	}
	public void setSanc_amt(double sanc_amt) {
		this.sanc_amt = sanc_amt;
	}
	public double getTot_bal() {
		return tot_bal;
	}
	public void setTot_bal(double tot_bal) {
		this.tot_bal = tot_bal;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIntroducerac_type() {
		return introducerac_type;
	}
	public void setIntroducerac_type(String introducerac_type) {
		this.introducerac_type = introducerac_type;
	}
	public int getIntroducer_Acno() {
		return introducer_Acno;
	}
	public void setIntroducer_Acno(int introducer_Acno) {
		this.introducer_Acno = introducer_Acno;
	}
	public String getNojonthd() {
		return nojonthd;
	}
	public void setNojonthd(String nojonthd) {
		this.nojonthd = nojonthd;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getAc_noo() {
		return ac_noo;
	}
	public void setAc_noo(String ac_noo) {
		this.ac_noo = ac_noo;
	}
	
	public double getInst_amt() {
		return inst_amt;
	}
	public void setInst_amt(double inst_amt) {
		this.inst_amt = inst_amt;
	}
	public double getAmt_collected() {
		return amt_collected;
	}
	public void setAmt_collected(double amt_collected) {
		this.amt_collected = amt_collected;
	}
	public double getNet_amt() {
		return net_amt;
	}
	public void setNet_amt(double net_amt) {
		this.net_amt = net_amt;
	}
	public String getAlertdispay() {
		return alertdispay;
	}
	public void setAlertdispay(String alertdispay) {
		this.alertdispay = alertdispay;
	}
	
	
	
	

	
}
