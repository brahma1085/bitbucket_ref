package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class DailyRemittanceForm extends ActionForm{
	
	String agType,agNo,agName,collDate,remiDate,scrNo,oldscr,amt,totAmt,pageid,forward,check1,valid;
	String selected;
	
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

	public String getCollDate() {
		return collDate;
	}

	public void setCollDate(String collDate) {
		this.collDate = collDate;
	}

	public String getRemiDate() {
		return remiDate;
	}

	public void setRemiDate(String remiDate) {
		this.remiDate = remiDate;
	}

	public String getScrNo() {
		return scrNo;
	}

	public void setScrNo(String scrNo) {
		this.scrNo = scrNo;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(String totAmt) {
		this.totAmt = totAmt;
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

	public String getCheck1() {
		return check1;
	}

	public void setCheck1(String check1) {
		this.check1 = check1;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getOldscr() {
		return oldscr;
	}

	public void setOldscr(String oldscr) {
		this.oldscr = oldscr;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	

	

}
