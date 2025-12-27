package com.scb.clr.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ReceivedECSForm extends ActionForm {

	private String clr_date,controlNum,c_d,ackNumber,acType,acNumber,bankName,amount,payee,itemSeqNum,bounce,ack_balance,ack_source,bankCode;

	private String pageId,flag,validateFlag,button_submit;
	
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
	
	public String getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(String ackNumber) {
		this.ackNumber = ackNumber;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAcNumber() {
		return acNumber;
	}

	public void setAcNumber(String acNumber) {
		this.acNumber = acNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getItemSeqNum() {
		return itemSeqNum;
	}

	public void setItemSeqNum(String itemSeqNum) {
		this.itemSeqNum = itemSeqNum;
	}

	public String getBounce() {
		return bounce;
	}

	public void setBounce(String bounce) {
		this.bounce = bounce;
	}

	public String getClr_date() {
		return clr_date;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
	}

	public String getC_d() {
		return c_d;
	}

	public void setC_d(String c_d) {
		this.c_d = c_d;
	}

	public String getButton_submit() {
		return button_submit;
	}

	public void setButton_submit(String button_submit) {
		this.button_submit = button_submit;
	}

	public String getAck_balance() {
		return ack_balance;
	}

	public void setAck_balance(String ack_balance) {
		this.ack_balance = ack_balance;
	}

	public String getAck_source() {
		return ack_source;
	}

	public void setAck_source(String ack_source) {
		this.ack_source = ack_source;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	
	 
}
