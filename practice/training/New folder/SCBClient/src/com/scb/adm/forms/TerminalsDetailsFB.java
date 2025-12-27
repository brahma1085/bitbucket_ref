package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class TerminalsDetailsFB extends ActionForm{
	private String pageId;
	private String validations,flag,tmls,tmlName,tmlDesc,tmlIpAddr,tranAmount,tmlType,status;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getTmls() {
		return tmls;
	}
	public void setTmls(String tmls) {
		this.tmls = tmls;
	}
	public String getTmlName() {
		return tmlName;
	}
	public void setTmlName(String tmlName) {
		this.tmlName = tmlName;
	}
	public String getTmlDesc() {
		return tmlDesc;
	}
	public void setTmlDesc(String tmlDesc) {
		this.tmlDesc = tmlDesc;
	}
	public String getTmlIpAddr() {
		return tmlIpAddr;
	}
	public void setTmlIpAddr(String tmlIpAddr) {
		this.tmlIpAddr = tmlIpAddr;
	}
	public String getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getTmlType() {
		return tmlType;
	}
	public void setTmlType(String tmlType) {
		this.tmlType = tmlType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
