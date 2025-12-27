package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class Ledger extends ActionForm{
	
	String pageId;
	String  from_date,to_date,by_date,mat_date,int_upto_date;
	String ac_type;
	int from_acno,to_acno,ac_no;
	String open_close_stat;
	String forward,testing,subval;
	String but_first,butt_prev,butt_next,butt_last;
	String but_view,but_query,butt_search,but_file,but_print,but_reprint,but_clear,relation_ship;
	
	String name1,dep_date,int_frq,dep_cat,received_by,int_mode,loan_avail;
	int nominee_reg_no,receipt_no;
	
	double int_rate,deptype,period_of_days,dep_amt,mat_amt,int_paid;
	String sysdate;
	
	
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getOpen_close_stat() {
		return open_close_stat;
	}
	public void setOpen_close_stat(String open_close_stat) {
		this.open_close_stat = open_close_stat;
	}
	public String getBut_view() {
		return but_view;
	}
	public void setBut_view(String but_view) {
		this.but_view = but_view;
	}
	public String getBut_query() {
		return but_query;
	}
	public void setBut_query(String but_query) {
		this.but_query = but_query;
	}
	public String getButt_search() {
		return butt_search;
	}
	public void setButt_search(String butt_search) {
		this.butt_search = butt_search;
	}
	public String getBut_file() {
		return but_file;
	}
	public void setBut_file(String but_file) {
		this.but_file = but_file;
	}
	public String getBut_print() {
		return but_print;
	}
	public void setBut_print(String but_print) {
		this.but_print = but_print;
	}
	public String getBut_reprint() {
		return but_reprint;
	}
	public void setBut_reprint(String but_reprint) {
		this.but_reprint = but_reprint;
	}
	public String getBut_clear() {
		return but_clear;
	}
	public void setBut_clear(String but_clear) {
		this.but_clear = but_clear;
	}
	public String getBy_date() {
		return by_date;
	}
	public void setBy_date(String by_date) {
		this.by_date = by_date;
	}
	public String getBut_first() {
		return but_first;
	}
	public void setBut_first(String but_first) {
		this.but_first = but_first;
	}
	public String getButt_prev() {
		return butt_prev;
	}
	public void setButt_prev(String butt_prev) {
		this.butt_prev = butt_prev;
	}
	public String getButt_next() {
		return butt_next;
	}
	public void setButt_next(String butt_next) {
		this.butt_next = butt_next;
	}
	public String getButt_last() {
		return butt_last;
	}
	public void setButt_last(String butt_last) {
		this.butt_last = butt_last;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public int getFrom_acno() {
		return from_acno;
	}
	public void setFrom_acno(int from_acno) {
		this.from_acno = from_acno;
	}
	public int getTo_acno() {
		return to_acno;
	}
	public void setTo_acno(int to_acno) {
		this.to_acno = to_acno;
	}
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
	
	public double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}
	public double getDeptype() {
		return deptype;
	}
	public void setDeptype(double deptype) {
		this.deptype = deptype;
	}
	public double getPeriod_of_days() {
		return period_of_days;
	}
	public void setPeriod_of_days(double period_of_days) {
		this.period_of_days = period_of_days;
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
	public String getInt_frq() {
		return int_frq;
	}
	public void setInt_frq(String int_frq) {
		this.int_frq = int_frq;
	}
	public String getDep_cat() {
		return dep_cat;
	}
	public void setDep_cat(String dep_cat) {
		this.dep_cat = dep_cat;
	}
	public String getReceived_by() {
		return received_by;
	}
	public void setReceived_by(String received_by) {
		this.received_by = received_by;
	}
	public String getInt_mode() {
		return int_mode;
	}
	public void setInt_mode(String int_mode) {
		this.int_mode = int_mode;
	}
	public String getLoan_avail() {
		return loan_avail;
	}
	public void setLoan_avail(String loan_avail) {
		this.loan_avail = loan_avail;
	}
	public int getNominee_reg_no() {
		return nominee_reg_no;
	}
	public void setNominee_reg_no(int nominee_reg_no) {
		this.nominee_reg_no = nominee_reg_no;
	}
	public int getReceipt_no() {
		return receipt_no;
	}
	public void setReceipt_no(int receipt_no) {
		this.receipt_no = receipt_no;
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
	public double getInt_paid() {
		return int_paid;
	}
	public void setInt_paid(double int_paid) {
		this.int_paid = int_paid;
	}
	public String getRelation_ship() {
		return relation_ship;
	}
	public void setRelation_ship(String relation_ship) {
		this.relation_ship = relation_ship;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getSubval() {
		return subval;
	}
	public void setSubval(String subval) {
		this.subval = subval;
	}
	


	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

	
	

}
