package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class DepositAdmin extends ActionForm {
	private String pageId,fd_actype,fd_table,forward,testing;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getFd_actype() {
		return fd_actype;
	}

	public void setFd_actype(String fd_actype) {
		this.fd_actype = fd_actype;
	}

	
	public String getFd_table() {
		return fd_table;
	}

	public void setFd_table(String fd_table) {
		this.fd_table = fd_table;
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
