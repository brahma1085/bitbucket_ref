package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerPassBookForm extends ActionForm{
	//flag used in javascript n assigned to 1(means focus lost of acnt num)
	//verifyInd used to chk wheather acnt exist r not
	private String pageId,fromDate,toDate,custId,custName,lockerType,lockerNum,acntNum,flag,forward,verifyInd;
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLockerType() {
		return lockerType;
	}

	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}

	public String getLockerNum() {
		return lockerNum;
	}

	public void setLockerNum(String lockerNum) {
		this.lockerNum = lockerNum;
	}

	public String getAcntNum() {
		return acntNum;
	}

	public void setAcntNum(String acntNum) {
		this.acntNum = acntNum;
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

	public String getVerifyInd() {
		return verifyInd;
	}

	public void setVerifyInd(String verifyInd) {
		this.verifyInd = verifyInd;
	}
	


	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}
}
