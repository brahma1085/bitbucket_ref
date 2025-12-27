package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerExtensionForm extends ActionForm{
	String fatButt;
    String txt_acType;
    String txt_acNo;
    String txt_lockType;
    String txt_lockNo;
    String txt_allotDate;
    String txt_expiryDate;
    String txt_rentUpto;
    String txt_totRent;
    String txt_extnMonths;
    String txt_extnDays;
    String txt_extnDate;
    String txt_rentAmnt;
    String select_details;
    String pageId,clearButt;
    String submit;
    String forward,combo_details,acc_no;
    boolean chk;
    //success flag :- used to display alert(successfully extended r can't)
    private String successFlag,validateFlag,clearButton,receiptDetails,scrollNum,date,amount,transferAcntType,transferAcntNum,flag;
    
    private String extendButton;
    
    
    
	public String getExtendButton() {
		return extendButton;
	}
	public void setExtendButton(String extendButton) {
		this.extendButton = extendButton;
	}
	public String getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getFatButt() {
		return fatButt;
	}
	public void setFatButt(String fatButt) {
		this.fatButt = fatButt;
	}
	public String getTxt_acType() {
		return txt_acType;
	}
	public void setTxt_acType(String txt_acType) {
		this.txt_acType = txt_acType;
	}
	public String getTxt_acNo() {
		return txt_acNo;
	}
	public void setTxt_acNo(String txt_acNo) {
		this.txt_acNo = txt_acNo;
	}
	public String getTxt_lockType() {
		return txt_lockType;
	}
	public void setTxt_lockType(String txt_lockType) {
		this.txt_lockType = txt_lockType;
	}
	public String getTxt_lockNo() {
		return txt_lockNo;
	}
	public void setTxt_lockNo(String txt_lockNo) {
		this.txt_lockNo = txt_lockNo;
	}
	public String getTxt_allotDate() {
		return txt_allotDate;
	}
	public void setTxt_allotDate(String txt_allotDate) {
		this.txt_allotDate = txt_allotDate;
	}
	public String getTxt_expiryDate() {
		return txt_expiryDate;
	}
	public void setTxt_expiryDate(String txt_expiryDate) {
		this.txt_expiryDate = txt_expiryDate;
	}
	public String getTxt_rentUpto() {
		return txt_rentUpto;
	}
	public void setTxt_rentUpto(String txt_rentUpto) {
		this.txt_rentUpto = txt_rentUpto;
	}
	public String getTxt_totRent() {
		return txt_totRent;
	}
	public void setTxt_totRent(String txt_totRent) {
		this.txt_totRent = txt_totRent;
	}
	public String getTxt_extnMonths() {
		return txt_extnMonths;
	}
	public void setTxt_extnMonths(String txt_extnMonths) {
		this.txt_extnMonths = txt_extnMonths;
	}
	public String getTxt_extnDays() {
		return txt_extnDays;
	}
	public void setTxt_extnDays(String txt_extnDays) {
		this.txt_extnDays = txt_extnDays;
	}
	public String getTxt_extnDate() {
		return txt_extnDate;
	}
	public void setTxt_extnDate(String txt_extnDate) {
		this.txt_extnDate = txt_extnDate;
	}
	public String getTxt_rentAmnt() {
		return txt_rentAmnt;
	}
	public void setTxt_rentAmnt(String txt_rentAmnt) {
		this.txt_rentAmnt = txt_rentAmnt;
	}
	public String getSelect_details() {
		return select_details;
	}
	public void setSelect_details(String select_details) {
		this.select_details = select_details;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public String getClearButton() {
		return clearButton;
	}
	public void setClearButton(String clearButton) {
		this.clearButton = clearButton;
	}
	public String getReceiptDetails() {
		return receiptDetails;
	}
	public void setReceiptDetails(String receiptDetails) {
		this.receiptDetails = receiptDetails;
	}
	public String getScrollNum() {
		return scrollNum;
	}
	public void setScrollNum(String scrollNum) {
		this.scrollNum = scrollNum;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTransferAcntType() {
		return transferAcntType;
	}
	public void setTransferAcntType(String transferAcntType) {
		this.transferAcntType = transferAcntType;
	}
	public String getTransferAcntNum() {
		return transferAcntNum;
	}
	public void setTransferAcntNum(String transferAcntNum) {
		this.transferAcntNum = transferAcntNum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getClearButt() {
		return clearButt;
	}
	public void setClearButt(String clearButt) {
		this.clearButt = clearButt;
	}
	public String getCombo_details() {
		return combo_details;
	}
	public void setCombo_details(String combo_details) {
		this.combo_details = combo_details;
	}
	
	public boolean isChk() {
		return chk;
	}
	public void setChk(boolean chk) {
		this.chk = chk;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	
	
    

}
