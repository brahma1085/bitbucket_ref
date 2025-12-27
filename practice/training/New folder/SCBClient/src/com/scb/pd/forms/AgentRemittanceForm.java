package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class AgentRemittanceForm extends ActionForm{
	private String agent_code,agent_no,rem_date,pageid,forward;
	String sysdate;

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getAgent_code() {
		return agent_code;
	}

	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}

	public String getAgent_no() {
		return agent_no;
	}

	public void setAgent_no(String agent_no) {
		this.agent_no = agent_no;
	}

	public String getRem_date() {
		return rem_date;
	}

	public void setRem_date(String rem_date) {
		this.rem_date = rem_date;
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
