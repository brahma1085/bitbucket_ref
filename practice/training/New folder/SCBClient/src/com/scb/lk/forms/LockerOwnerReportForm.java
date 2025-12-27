package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerOwnerReportForm  extends ActionForm{
	
	private  String dateUpto,pageId,fromDate,toDate,validateFlag,flag;
	String sysdate;

	public String getDateUpto() {
		return dateUpto;
	}

	
	public void setDateUpto(String dateUpto) {
		this.dateUpto = dateUpto;
	}


	public String getPageId() {
		return pageId;
	}


	public void setPageId(String pageId) {
		this.pageId = pageId;
	}


	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
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
