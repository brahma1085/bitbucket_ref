package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerIssueForm extends ActionForm {
	private	String  pageId,cid;
    private String  txt_cid,lkacNum,lkacType,lkType,lkNum,lkKey,lkRent;
    private String  pass,months,days,allotDate,expDate,opInstr,membAc,membType;
    private String intrType,intrNum,nomReg,details,autoExtn,forward;
    private String validateFlag,transferAcntNum,transferAcntType;
    private String transferAcNum,transferAcType,tabView,receiptDetails,scrollNum,date,amount;
    private String buttonClear,button_submit,submitButton,combo_details,acc_no;
    private String nomineeName,nomineeDob,nomineeAge,nomineeSex,nomineeAddress,nomineeRelationship,nomineePercentage,nomineeCid;
    private String membAcNum,ronum,columnnum,depositFlag,flag;
    private boolean hasAccount,required,freeze;
    
	public String getMembAcNum() {
		return membAcNum;
	}



	public void setMembAcNum(String membAcNum) {
		this.membAcNum = membAcNum;
	}



	public boolean isHasAccount() {
		return hasAccount;
	}



	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
	}



	public String getNomineeName() {
		return nomineeName;
	}



	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}



	public String getNomineeDob() {
		return nomineeDob;
	}



	public void setNomineeDob(String nomineeDob) {
		this.nomineeDob = nomineeDob;
	}



	public String getNomineeAge() {
		return nomineeAge;
	}



	public void setNomineeAge(String nomineeAge) {
		this.nomineeAge = nomineeAge;
	}



	public String getNomineeSex() {
		return nomineeSex;
	}



	public void setNomineeSex(String nomineeSex) {
		this.nomineeSex = nomineeSex;
	}



	public String getNomineeAddress() {
		return nomineeAddress;
	}



	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}



	public String getNomineeRelationship() {
		return nomineeRelationship;
	}



	public void setNomineeRelationship(String nomineeRelationship) {
		this.nomineeRelationship = nomineeRelationship;
	}



	public String getNomineePercentage() {
		return nomineePercentage;
	}



	public void setNomineePercentage(String nomineePercentage) {
		this.nomineePercentage = nomineePercentage;
	}



	public String getNomineeCid() {
		return nomineeCid;
	}



	public void setNomineeCid(String nomineeCid) {
		this.nomineeCid = nomineeCid;
	}



	public String getButtonClear() {
		return buttonClear;
	}



	public void setButtonClear(String buttonClear) {
		this.buttonClear = buttonClear;
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
	public String getTxt_cid() {
		return txt_cid;
	}
	public void setTxt_cid(String txt_cid) {
		this.txt_cid = txt_cid;
	}
	public String getLkacNum() {
		return lkacNum;
	}
	public void setLkacNum(String lkacNum) {
		this.lkacNum = lkacNum;
	}
	public String getLkacType() {
		return lkacType;
	}
	public void setLkacType(String lkacType) {
		this.lkacType = lkacType;
	}
	public String getLkType() {
		return lkType;
	}
	public void setLkType(String lkType) {
		this.lkType = lkType;
	}
	public String getLkNum() {
		return lkNum;
	}
	public void setLkNum(String lkNum) {
		this.lkNum = lkNum;
	}
	public String getLkKey() {
		return lkKey;
	}
	public void setLkKey(String lkKey) {
		this.lkKey = lkKey;
	}
	public String getLkRent() {
		return lkRent;
	}
	public void setLkRent(String lkRent) {
		this.lkRent = lkRent;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getAllotDate() {
		return allotDate;
	}
	public void setAllotDate(String allotDate) {
		this.allotDate = allotDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getOpInstr() {
		return opInstr;
	}
	public void setOpInstr(String opInstr) {
		this.opInstr = opInstr;
	}
	public String getMembAc() {
		return membAc;
	}
	public void setMembAc(String membAc) {
		System.out.println("inside form bean-->"+membAc);
		this.membAc = membAc;
	}
	public String getMembType() {
		return membType;
	}
	public void setMembType(String membType) {
		this.membType = membType;
	}
	public String getIntrType() {
		return intrType;
	}
	public void setIntrType(String intrType) {
		this.intrType = intrType;
	}
	public String getIntrNum() {
		return intrNum;
	}
	public void setIntrNum(String intrNum) {
		this.intrNum = intrNum;
	}
	public String getNomReg() {
		return nomReg;
	}
	public void setNomReg(String nomReg) {
		this.nomReg = nomReg;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getAutoExtn() {
		return autoExtn;
	}
	public void setAutoExtn(String autoExtn) {
		this.autoExtn = autoExtn;
	}



	public String getForward() {
		return forward;
	}



	public void setForward(String forward) {
		this.forward = forward;
	}



	public String getTransferAcNum() {
		return transferAcNum;
	}



	public void setTransferAcNum(String transferAcNum) {
		this.transferAcNum = transferAcNum;
	}



	public String getTransferAcType() {
		return transferAcType;
	}



	public void setTransferAcType(String transferAcType) {
		this.transferAcType = transferAcType;
	}



	public String getTabView() {
		return tabView;
	}



	public void setTabView(String tabView) {
		this.tabView = tabView;
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



	public String getTransferAcntNum() {
		return transferAcntNum;
	}



	public void setTransferAcntNum(String transferAcntNum) {
		this.transferAcntNum = transferAcntNum;
	}



	public String getTransferAcntType() {
		return transferAcntType;
	}



	public void setTransferAcntType(String transferAcntType) {
		this.transferAcntType = transferAcntType;
	}


	public String getButton_submit() {
		return button_submit;
	}



	public void setButton_submit(String button_submit) {
		this.button_submit = button_submit;
	}



	public String getSubmitButton() {
		return submitButton;
	}



	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}



	public String getCombo_details() {
		return combo_details;
	}



	public void setCombo_details(String combo_details) {
		this.combo_details = combo_details;
	}



	public String getAcc_no() {
		return acc_no;
	}



	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}



	public String getCid() {
		return cid;
	}



	public void setCid(String cid) {
		this.cid = cid;
	}



	public boolean isRequired() {
		return required;
	}



	public void setRequired(boolean required) {
		this.required = required;
	}



	public String getRonum() {
		return ronum;
	}



	public void setRonum(String ronum) {
		this.ronum = ronum;
	}



	public String getColumnnum() {
		return columnnum;
	}



	public void setColumnnum(String columnnum) {
		this.columnnum = columnnum;
	}



	public String getDepositFlag() {
		return depositFlag;
	}



	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}



	public String getFlag() {
		return flag;
	}



	public void setFlag(String flag) {
		this.flag = flag;
	}



	public boolean isFreeze() {
		return freeze;
	}



	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}






	


	


	

}
