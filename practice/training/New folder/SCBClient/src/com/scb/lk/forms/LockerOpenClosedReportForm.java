package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerOpenClosedReportForm extends ActionForm{
	
	String pageId,frmDate,toDate,selectOption,fromDate,forward;
    String flag;
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

	public String getSelectOption() {
		return selectOption;
	}

	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getSysdate() {
			return sysdate;
		}

		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}
	

}
