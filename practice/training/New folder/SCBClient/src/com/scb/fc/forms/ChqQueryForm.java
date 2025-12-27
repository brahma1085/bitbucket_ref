package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ChqQueryForm extends ActionForm {
	String pageId;
	String chqno,clr;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getChqno() {
		return chqno;
	}
	public void setChqno(String chqno) {
		this.chqno = chqno;
	}
	public String getClr() {
		return clr;
	}
	public void setClr(String clr) {
		this.clr = clr;
	}
}
