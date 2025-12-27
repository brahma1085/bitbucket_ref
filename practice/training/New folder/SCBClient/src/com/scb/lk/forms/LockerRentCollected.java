package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerRentCollected extends ActionForm{

	
	String pageId;
	private String frmDate,fromDate;
	private String toDate,flag;
	private String butt,validateFlag;
	
	String sysdate;

	public String getButt() {
		return butt;
	}

	public void setButt(String butt) {
		this.butt = butt;
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
