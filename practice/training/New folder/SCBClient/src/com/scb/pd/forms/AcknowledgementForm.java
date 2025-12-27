package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class AcknowledgementForm extends ActionForm{
	
	String remi_frm_date,remi_to_date,agType,agNo,agName,pageid,forward;
	String sysdate;

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getRemi_frm_date() {
		return remi_frm_date;
	}

	public void setRemi_frm_date(String remi_frm_date) {
		this.remi_frm_date = remi_frm_date;
	}

	public String getRemi_to_date() {
		return remi_to_date;
	}

	public void setRemi_to_date(String remi_to_date) {
		this.remi_to_date = remi_to_date;
	}

	public String getAgType() {
		return agType;
	}

	public void setAgType(String agType) {
		this.agType = agType;
	}

	public String getAgNo() {
		return agNo;
	}

	public void setAgNo(String agNo) {
		this.agNo = agNo;
	}

	public String getAgName() {
		return agName;
	}

	public void setAgName(String agName) {
		this.agName = agName;
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

}
