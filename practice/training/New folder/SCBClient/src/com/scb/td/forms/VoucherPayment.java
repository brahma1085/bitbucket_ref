package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class VoucherPayment extends ActionForm {
	
	
	private String pageId;
	
	private String ac_type,pay_ac_type,pay_mode,pay_mode_ac_no,details;
	private String flag,add,testing,flagValue;
	private int Vouch_hide;
	
	private int ac_no;
	private double total_amt;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public int getAc_no() {
		return ac_no;
	}

	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}

	public String getPay_ac_type() {
		return pay_ac_type;
	}

	public void setPay_ac_type(String pay_ac_type) {
		this.pay_ac_type = pay_ac_type;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getPay_mode_ac_no() {
		return pay_mode_ac_no;
	}

	public void setPay_mode_ac_no(String pay_mode_ac_no) {
		this.pay_mode_ac_no = pay_mode_ac_no;
	}

	public double getTotal_amt() {
		return total_amt;
	}

	public void setTotal_amt(double total_amt) {
		this.total_amt = total_amt;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getVouch_hide() {
		return Vouch_hide;
	}

	public void setVouch_hide(int vouch_hide) {
		Vouch_hide = vouch_hide;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}

	public String getFlagValue() {
		return flagValue;
	}

	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}



}
