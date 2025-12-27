package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class RateAdmin  extends ActionForm {

	
	private String PageId,lkType,dateFrom,dateTo,daysFrom,daysTo,lockerRate,securityDeposit,forward,lockerType,chkBox,validateFlag;

	//hidden variables
	private String dateFrom1,dateTo1,daysFrom1,daysTo1,lockerRate1,securityDeposit1,lockerType1; 
	public String getDateFrom1() {
		return dateFrom1;
	}

	public void setDateFrom1(String dateFrom1) {
		this.dateFrom1 = dateFrom1;
	}

	public String getDateTo1() {
		return dateTo1;
	}

	public void setDateTo1(String dateTo1) {
		this.dateTo1 = dateTo1;
	}

	public String getDaysFrom1() {
		return daysFrom1;
	}

	public void setDaysFrom1(String daysFrom1) {
		this.daysFrom1 = daysFrom1;
	}

	public String getDaysTo1() {
		return daysTo1;
	}

	public void setDaysTo1(String daysTo1) {
		this.daysTo1 = daysTo1;
	}

	public String getLockerRate1() {
		return lockerRate1;
	}

	public void setLockerRate1(String lockerRate1) {
		this.lockerRate1 = lockerRate1;
	}

	public String getSecurityDeposit1() {
		return securityDeposit1;
	}

	public void setSecurityDeposit1(String securityDeposit1) {
		this.securityDeposit1 = securityDeposit1;
	}

	public String getLockerType1() {
		return lockerType1;
	}

	public void setLockerType1(String lockerType1) {
		this.lockerType1 = lockerType1;
	}

	public String getPageId() {
		return PageId;
	}

	public void setPageId(String pageId) {
		PageId = pageId;
	}

	public String getLkType() {
		return lkType;
	}

	public void setLkType(String lkType) {
		this.lkType = lkType;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDaysFrom() {
		return daysFrom;
	}

	public void setDaysFrom(String daysFrom) {
		this.daysFrom = daysFrom;
	}

	public String getDaysTo() {
		return daysTo;
	}

	public void setDaysTo(String daysTo) {
		this.daysTo = daysTo;
	}

	public String getLockerRate() {
		return lockerRate;
	}

	public void setLockerRate(String lockerRate) {
		this.lockerRate = lockerRate;
	}

	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getLockerType() {
		return lockerType;
	}

	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}

	public String getChkBox() {
		return chkBox;
	}

	public void setChkBox(String chkBox) {
		this.chkBox = chkBox;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
}
