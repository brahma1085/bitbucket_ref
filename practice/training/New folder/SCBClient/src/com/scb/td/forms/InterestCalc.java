package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class InterestCalc extends ActionForm {
	
	private String pageId;
	
	private String ac_type,forward;
	
	private String utml,udate,uid;
	
	private String but_int_payment,but_view,but_print,but_reprint,testing;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getBut_int_payment() {
		return but_int_payment;
	}

	public void setBut_int_payment(String but_int_payment) {
		this.but_int_payment = but_int_payment;
	}

	public String getBut_view() {
		return but_view;
	}

	public void setBut_view(String but_view) {
		this.but_view = but_view;
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

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getUtml() {
		return utml;
	}

	public void setUtml(String utml) {
		this.utml = utml;
	}

	public String getUdate() {
		return udate;
	}

	public void setUdate(String udate) {
		this.udate = udate;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}
	
	
	

}
