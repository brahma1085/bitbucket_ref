package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class MyHelpForm  extends ActionForm{
	
	private String pageId,ac_no;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}
	

}
