package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class MonthlyRemittanceForm extends ActionForm{
	String comboAgents,agent_code,agent_no,agentname,from_date,to_date,pageid,forward;
	String validate;
	String sysdate;
	
	
	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getComboAgents() {
		return comboAgents;
	}

	public void setComboAgents(String comboAgents) {
		this.comboAgents = comboAgents;
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

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

}
