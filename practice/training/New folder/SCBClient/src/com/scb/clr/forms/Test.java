package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class Test extends ActionForm {

	
	private String pageId,name,user,flag,ctrlno;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCtrlno() {
		return ctrlno;
	}

	public void setCtrlno(String ctrlno) {
		this.ctrlno = ctrlno;
	}
}
