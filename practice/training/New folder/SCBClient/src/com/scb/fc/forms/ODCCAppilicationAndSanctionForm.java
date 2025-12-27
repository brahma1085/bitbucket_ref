package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ODCCAppilicationAndSanctionForm extends ActionForm{
	private String ac_type,sh_type,detailsCombo,pageId;
	private int ac_no,sh_no,limit_upto;
	double int_rate,credit_limit;
	

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getSh_type() {
		return sh_type;
	}

	public void setSh_type(String sh_type) {
		this.sh_type = sh_type;
	}

	

	public String getDetailsCombo() {
		return detailsCombo;
	}

	public void setDetailsCombo(String detailsCombo) {
		this.detailsCombo = detailsCombo;
	}

	public int getAc_no() {
		return ac_no;
	}

	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}

	public int getSh_no() {
		return sh_no;
	}

	public void setSh_no(int sh_no) {
		this.sh_no = sh_no;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public int getLimit_upto() {
		return limit_upto;
	}

	public void setLimit_upto(int limit_upto) {
		this.limit_upto = limit_upto;
	}

	public double getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}

	public double getCredit_limit() {
		return credit_limit;
	}

	public void setCredit_limit(double credit_limit) {
		this.credit_limit = credit_limit;
	}
	
}
