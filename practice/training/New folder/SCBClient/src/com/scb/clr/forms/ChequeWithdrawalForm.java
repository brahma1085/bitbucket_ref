package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ChequeWithdrawalForm extends ActionForm{
	private String pageId,flag,validateFlag,controlNum,MICRCode,branchName,creditAcType,creditAcNum,amount,payee,chqddpo,chqno,errorFlag,chqdate,buttonWithDrawal,buttonClear;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

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

	public String getControlNum() {
		return controlNum;
	}

	public void setControlNum(String controlNum) {
		this.controlNum = controlNum;
	}

	public String getMICRCode() {
		return MICRCode;
	}

	public void setMICRCode(String code) {
		MICRCode = code;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCreditAcType() {
		return creditAcType;
	}

	public void setCreditAcType(String creditAcType) {
		this.creditAcType = creditAcType;
	}

	public String getCreditAcNum() {
		return creditAcNum;
	}

	public void setCreditAcNum(String creditAcNum) {
		this.creditAcNum = creditAcNum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getChqddpo() {
		return chqddpo;
	}

	public void setChqddpo(String chqddpo) {
		this.chqddpo = chqddpo;
	}

	public String getChqno() {
		return chqno;
	}

	public void setChqno(String chqno) {
		this.chqno = chqno;
	}

	public String getChqdate() {
		return chqdate;
	}

	public void setChqdate(String chqdate) {
		this.chqdate = chqdate;
	}

	public String getButtonWithDrawal() {
		return buttonWithDrawal;
	}

	public void setButtonWithDrawal(String buttonWithDrawal) {
		this.buttonWithDrawal = buttonWithDrawal;
	}

	public String getButtonClear() {
		return buttonClear;
	}

	public void setButtonClear(String buttonClear) {
		this.buttonClear = buttonClear;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
}
