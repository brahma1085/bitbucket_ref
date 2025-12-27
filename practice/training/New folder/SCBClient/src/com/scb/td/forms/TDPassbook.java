package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class TDPassbook extends ActionForm{
	
	private String pageId;
	private int cid,ac_no,period_in_days,ph_no,ln_no,credit_accno,receipt_no;
	private String name,dep_date,mat_date,int_upto_date;
	private double mat_amt,dep_amt,int_rate,loan_sanc_amt;
	private String int_mode,int_freq,auto_renewal,email;
    private String ln_avail,ac_type,ln_actype,credit_acctype;
    private int nominee_no,int_amt_paid;
    private String butt_file,butt_print,butt_reprint;
    private String validation,flag;
   
    
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getPeriod_in_days() {
		return period_in_days;
	}
	public void setPeriod_in_days(int period_in_days) {
		this.period_in_days = period_in_days;
	}
	public int getPh_no() {
		return ph_no;
	}
	public void setPh_no(int ph_no) {
		this.ph_no = ph_no;
	}
	public int getLn_no() {
		return ln_no;
	}
	public void setLn_no(int ln_no) {
		this.ln_no = ln_no;
	}
	public int getCredit_accno() {
		return credit_accno;
	}
	public void setCredit_accno(int credit_accno) {
		this.credit_accno = credit_accno;
	}
	public int getReceipt_no() {
		return receipt_no;
	}
	public void setReceipt_no(int receipt_no) {
		this.receipt_no = receipt_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public String getMat_date() {
		return mat_date;
	}
	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}
	public String getInt_upto_date() {
		return int_upto_date;
	}
	public void setInt_upto_date(String int_upto_date) {
		this.int_upto_date = int_upto_date;
	}
	public double getMat_amt() {
		return mat_amt;
	}
	public void setMat_amt(double mat_amt) {
		this.mat_amt = mat_amt;
	}
	public double getDep_amt() {
		return dep_amt;
	}
	public void setDep_amt(double dep_amt) {
		this.dep_amt = dep_amt;
	}
	public double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}
	public double getLoan_sanc_amt() {
		return loan_sanc_amt;
	}
	public void setLoan_sanc_amt(double loan_sanc_amt) {
		this.loan_sanc_amt = loan_sanc_amt;
	}
	public String getInt_mode() {
		return int_mode;
	}
	public void setInt_mode(String int_mode) {
		this.int_mode = int_mode;
	}
	public String getInt_freq() {
		return int_freq;
	}
	public void setInt_freq(String int_freq) {
		this.int_freq = int_freq;
	}
	public String getAuto_renewal() {
		return auto_renewal;
	}
	public void setAuto_renewal(String auto_renewal) {
		this.auto_renewal = auto_renewal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLn_avail() {
		return ln_avail;
	}
	public void setLn_avail(String ln_avail) {
		this.ln_avail = ln_avail;
	}
	public String getLn_actype() {
		return ln_actype;
	}
	public void setLn_actype(String ln_actype) {
		this.ln_actype = ln_actype;
	}
	public String getCredit_acctype() {
		return credit_acctype;
	}
	public void setCredit_acctype(String credit_acctype) {
		this.credit_acctype = credit_acctype;
	}
	public int getNominee_no() {
		return nominee_no;
	}
	public void setNominee_no(int nominee_no) {
		this.nominee_no = nominee_no;
	}
	public String getButt_file() {
		return butt_file;
	}
	public void setButt_file(String butt_file) {
		this.butt_file = butt_file;
	}
	public String getButt_print() {
		return butt_print;
	}
	public void setButt_print(String butt_print) {
		this.butt_print = butt_print;
	}
	public String getButt_reprint() {
		return butt_reprint;
	}
	public void setButt_reprint(String butt_reprint) {
		this.butt_reprint = butt_reprint;
	}
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
	public int getInt_amt_paid() {
		return int_amt_paid;
	}
	public void setInt_amt_paid(int int_amt_paid) {
		this.int_amt_paid = int_amt_paid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
