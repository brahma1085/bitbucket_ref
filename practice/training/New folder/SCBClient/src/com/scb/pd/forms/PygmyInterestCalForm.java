package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;



public class PygmyInterestCalForm extends ActionForm{
	
	String uid,tml,date,time,br_location,pageid,forward,valid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTml() {
		return tml;
	}

	public void setTml(String tml) {
		this.tml = tml;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBr_location() {
		return br_location;
	}

	public void setBr_location(String br_location) {
		this.br_location = br_location;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}
