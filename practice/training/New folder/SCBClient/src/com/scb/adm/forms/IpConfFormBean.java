package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class IpConfFormBean extends ActionForm{
	private String pageId;
	private String validations,tmlNo,flag,ckBox,ipAddr;
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
	public String getTmlNo() {
		return tmlNo;
	}
	public void setTmlNo(String tmlNo) {
		this.tmlNo = tmlNo;
	}
	public String getCkBox() {
		return ckBox;
	}
	public void setCkBox(String ckBox) {
		this.ckBox = ckBox;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	    

}
