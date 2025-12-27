package com.scb.cm.forms;

import org.apache.struts.action.ActionForm;

public class CustFull extends ActionForm {
	private String pageId,acc_option,ac_type,ac_no,custinfo,ac_status;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAcc_option() {
		return acc_option;
	}

	public void setAcc_option(String acc_option) {
		this.acc_option = acc_option;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public String getCustinfo() {
		return custinfo;
	}

	public void setCustinfo(String custinfo) {
		this.custinfo = custinfo;
	}

	public String getAc_status() {
		return ac_status;
	}

	public void setAc_status(String ac_status) {
		this.ac_status = ac_status;
	}
}
