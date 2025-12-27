package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;


public class FCIntPostForm extends ActionForm {
	
	
	String pageId;
	String acType,acno,rad;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcno() {
		return acno;
	}
	public void setAcno(String acno) {
		this.acno = acno;
	}
	public String getRad() {
		return rad;
	}
	public void setRad(String rad) {
		this.rad = rad;
	}
}



