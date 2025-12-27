package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class LateReturnsForm extends ActionForm {

	private String pageId,validateFlag,flag,controlno,bankCodeName,branch_name,creditAcType,creditAcNum,creditAcName,amount,chqno,chqddpo,chqdate,pocomm,clr_date,button_submit,discountCharge;
	private boolean multi_credit,bounce,discount;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	public String getControlno() {
		return controlno;
	}

	public void setControlno(String controlno) {
		this.controlno = controlno;
	}

	public String getBankCodeName() {
		return bankCodeName;
	}

	public void setBankCodeName(String bankCodeName) {
		this.bankCodeName = bankCodeName;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	



	public boolean isMulti_credit() {
		return multi_credit;
	}

	public void setMulti_credit(boolean multi_credit) {
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

	public String getCreditAcName() {
		return creditAcName;
	}

	public void setCreditAcName(String creditAcName) {
		this.creditAcName = creditAcName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	

	public boolean isBounce() {
		return bounce;
	}

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
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

	public String getChqdate() {
		return chqdate;
	}

	public void setChqdate(String chqdate) {
		this.chqdate = chqdate;
	}

	public String getPocomm() {
		return pocomm;
	}

	public void setPocomm(String pocomm) {
		this.pocomm = pocomm;
	}

	public String getClr_date() {
		return clr_date;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
	}

	public String getButton_submit() {
		return button_submit;
	}

	public void setButton_submit(String button_submit) {
		this.button_submit = button_submit;
	}

	public String getDiscountCharge() {
		return discountCharge;
	}

	public void setDiscountCharge(String discountCharge) {
		this.discountCharge = discountCharge;
	}

	
}
