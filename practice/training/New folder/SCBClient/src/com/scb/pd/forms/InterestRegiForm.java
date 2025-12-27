package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class InterestRegiForm  extends ActionForm{
	String pageid,from_dt,to_dt,forward;
	String sysdate;

	

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	
	
	public String getFrom_dt() {
		return from_dt;
	}

	public void setFrom_dt(String from_dt) {
		this.from_dt = from_dt;
	}

	public String getTo_dt() {
		return to_dt;
	}

	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}
	
}
