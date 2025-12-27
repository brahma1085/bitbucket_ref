package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class AgentChangingForm extends ActionForm{
	
	String agType,agNo,alt_agType,alt_agNo,pageid,forward;

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

	public String getAlt_agType() {
		return alt_agType;
	}

	public void setAlt_agType(String alt_agType) {
		this.alt_agType = alt_agType;
	}

	public String getAlt_agNo() {
		return alt_agNo;
	}

	public void setAlt_agNo(String alt_agNo) {
		this.alt_agNo = alt_agNo;
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
