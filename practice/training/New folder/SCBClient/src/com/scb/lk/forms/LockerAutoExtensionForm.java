package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerAutoExtensionForm extends ActionForm{
	
	//eligibleAcntNum:-used in javascript to validate weather acnt num is eligible to do autoExtn
	//acntNum1 used to send selected acnt num to action class(for extending)
	//lockerNum used after clicking on particular eligible locker number
	//finalTokenizer:the name of extending field(eg:Extend-123-s)
	
	String forward;
	
	String pageId;
	private String acntNum,acntType,expDate,dummyAcntNum;
	private String eligibleAcntNum,extendButton;
	private String clickButton,acntNum1,lockerNum,finalTokenizer,lkNumType;
	
	
	public String getClickButton() {
		return clickButton;
	}
	public void setClickButton(String clickButton) {
		this.clickButton = clickButton;
	}
	public String getAcntNum() {
		return acntNum;
	}
	public void setAcntNum(String acntNum) {
		this.acntNum = acntNum;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	
	public String getAcntType() {
		return acntType;
	}
	public void setAcntType(String acntType) {
		this.acntType = acntType;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getDummyAcntNum() {
		return dummyAcntNum;
	}
	public void setDummyAcntNum(String dummyAcntNum) {
		this.dummyAcntNum = dummyAcntNum;
	}
	public String getEligibleAcntNum() {
		return eligibleAcntNum;
	}
	public void setEligibleAcntNum(String eligibleAcntNum) {
		this.eligibleAcntNum = eligibleAcntNum;
	}
	public String getExtendButton() {
		return extendButton;
	}
	public void setExtendButton(String extendButton) {
		this.extendButton = extendButton;
	}
	public String getAcntNum1() {
		return acntNum1;
	}
	public void setAcntNum1(String acntNum1) {
		this.acntNum1 = acntNum1;
	}
	public String getLockerNum() {
		return lockerNum;
	}
	public void setLockerNum(String lockerNum) {
		this.lockerNum = lockerNum;
	}
	public String getFinalTokenizer() {
		return finalTokenizer;
	}
	public void setFinalTokenizer(String finalTokenizer) {
		this.finalTokenizer = finalTokenizer;
	}
	public String getLkNumType() {
		return lkNumType;
	}
	public void setLkNumType(String lkNumType) {
		this.lkNumType = lkNumType;
	}

	

}
