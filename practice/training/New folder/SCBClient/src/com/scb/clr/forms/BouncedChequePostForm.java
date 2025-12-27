package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class BouncedChequePostForm extends ActionForm {
	
	private String pageId,sourceNum,sourceName ,controlNum ,ackNumber, payee,  bankCode ,bankName, branchName ,amount, chqPoWarrant,buttonSubmit;
	private String chqno,chqddpo,ackType,acntType;
	private String clr_date,errorFlag;
	String chooseFlag;
	
	private String flag,validateFlag,balance,shadowBalance;
	
	
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(String sourceNum) {
		this.sourceNum = sourceNum;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getControlNum() {
		return controlNum;
	}

	public void setControlNum(String controlNum) {
		this.controlNum = controlNum;
	}

	public String getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(String ackNumber) {
		this.ackNumber = ackNumber;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getChqPoWarrant() {
		return chqPoWarrant;
	}

	public void setChqPoWarrant(String chqPoWarrant) {
		this.chqPoWarrant = chqPoWarrant;
	}

	public String getButtonSubmit() {
		return buttonSubmit;
	}

	public void setButtonSubmit(String buttonSubmit) {
		this.buttonSubmit = buttonSubmit;
	}

	public String getChqno() {
		return chqno;
	}

	public void setChqno(String chqno) {
		this.chqno = chqno;
	}

	public String getChqddpo() {
		return chqddpo;
	}

	public void setChqddpo(String chqddpo) {
		this.chqddpo = chqddpo;
	}

	public String getAckType() {
		return ackType;
	}

	public void setAckType(String ackType) {
		this.ackType = ackType;
	}

	public String getAcntType() {
		return acntType;
	}

	public void setAcntType(String acntType) {
		this.acntType = acntType;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(String shadowBalance) {
		this.shadowBalance = shadowBalance;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
	}
	public String getClr_date() {
		return clr_date;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}

	public String getChooseFlag() {
		return chooseFlag;
	}

	public void setChooseFlag(String chooseFlag) {
		this.chooseFlag = chooseFlag;
	}

	

	
}
