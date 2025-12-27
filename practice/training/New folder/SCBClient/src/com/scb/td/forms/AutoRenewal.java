package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class AutoRenewal extends ActionForm{
	
	

	private String pageId;
	
	private String  butt_auto_renewal;
	
	private int auto_acno;

	

	public int getAuto_acno() {
		return auto_acno;
	}

	public void setAuto_acno(int auto_acno) {
		this.auto_acno = auto_acno;
	}

	public String getButt_auto_renewal() {
		return butt_auto_renewal;
	}

	public void setButt_auto_renewal(String butt_auto_renewal) {
		this.butt_auto_renewal = butt_auto_renewal;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
