package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class UserActivityFormBean extends ActionForm{
	private String pageId;
	private String validations,printFile,flag,uid,frmDate,toDate,ckDate,ipAddr,tml,ipAddrValue,uidValue,tmlNo;
	
	public String getCkDate() {
		return ckDate;
	}
	public void setCkDate(String ckDate) {
		this.ckDate = ckDate;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getTml() {
		return tml;
	}
	public void setTml(String tml) {
		this.tml = tml;
	}
	public String getIpAddrValue() {
		return ipAddrValue;
	}
	public void setIpAddrValue(String ipAddrValue) {
		this.ipAddrValue = ipAddrValue;
	}
	public String getUidValue() {
		return uidValue;
	}
	public void setUidValue(String uidValue) {
		this.uidValue = uidValue;
	}
	public String getTmlNo() {
		return tmlNo;
	}
	public void setTmlNo(String tmlNo) {
		this.tmlNo = tmlNo;
	}
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
	
	public String getPrintFile() {
		return printFile;
	}
	public void setPrintFile(String printFile) {
		this.printFile = printFile;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFrmDate() {
		return frmDate;
	}
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
    

}
