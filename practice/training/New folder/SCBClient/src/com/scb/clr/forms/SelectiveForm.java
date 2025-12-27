package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class SelectiveForm extends ActionForm{

	private String pageId,amount,sent_to,  controlNumber  ,controlNum , source ,bankCode ,branchName ,clr_date ,multi_credit ,creditAcType , creditAcNum ,chqddpo ,chqDdPoDate , chqDdPoNo;
	private String flag,validateFlag,clgNum,errorFlag;
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

	public String getControlNum() {
		return controlNum;
	}

	public void setControlNum(String controlNum) {
		this.controlNum = controlNum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getClr_date() {
		return clr_date;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
	}

	public String getMulti_credit() {
		return multi_credit;
	}

	public void setMulti_credit(String multi_credit) {
		this.multi_credit = multi_credit;
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

	public String getChqddpo() {
		return chqddpo;
	}

	public void setChqddpo(String chqddpo) {
		this.chqddpo = chqddpo;
	}

	public String getChqDdPoDate() {
		return chqDdPoDate;
	}

	public void setChqDdPoDate(String chqDdPoDate) {
		this.chqDdPoDate = chqDdPoDate;
	}

	public String getChqDdPoNo() {
		return chqDdPoNo;
	}

	public void setChqDdPoNo(String chqDdPoNo) {
		this.chqDdPoNo = chqDdPoNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSent_to() {
		return sent_to;
	}

	public void setSent_to(String sent_to) {
		this.sent_to = sent_to;
	}

	public String getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}

	public String getClgNum() {
		return clgNum;
	}

	public void setClgNum(String clgNum) {
		this.clgNum = clgNum;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
}
