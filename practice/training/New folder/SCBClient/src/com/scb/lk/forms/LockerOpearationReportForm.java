package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerOpearationReportForm  extends ActionForm{
	
	private String pageId;
	private String frmDate,fromDate;
	private String toDate,validateFlag,flag;
	String sysdate;
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	


	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

}
