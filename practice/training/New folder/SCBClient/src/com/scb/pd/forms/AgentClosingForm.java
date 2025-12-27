package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class AgentClosingForm extends ActionForm{
	String agType,agNo,pageid,forward,value;
	String validating;

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

	

	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValidating() {
		return validating;
	}

	public void setValidating(String validating) {
		this.validating = validating;
	}

	

}
