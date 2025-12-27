package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerRemainderNoticeForm extends ActionForm {
	
	private String pageId,fromDate,toDate,forward,flag,acntNum,validateFlag;
	String sysdate;
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

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getAcntNum() {
		return acntNum;
	}

	public void setAcntNum(String acntNum) {
		this.acntNum = acntNum;
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
