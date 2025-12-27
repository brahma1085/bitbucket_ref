package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class UnclaimedDivForm extends ActionForm {

	private String pageId;
	private String actype,brcode,date,ac_start,ac_end,chk;
	private String forward,view,clear,validations; 

	public String getPageId() {
		return pageId;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAc_start() {
		return ac_start;
	}

	public void setAc_start(String ac_start) {
		this.ac_start = ac_start;
	}

	public String getAc_end() {
		return ac_end;
	}

	public void setAc_end(String ac_end) {
		this.ac_end = ac_end;
	}

	public String getChk() {
		return chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}
}
