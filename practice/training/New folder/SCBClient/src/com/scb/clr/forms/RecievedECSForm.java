package com.scb.clr.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class RecievedECSForm extends ActionForm {

	private String clr_date,controlNum,ackNumber,acType,acNumber,bankName,amount,payee,itemSeqNum,ack_balance,ack_source,bankCode,balance;
	boolean bounce;
	private String pageId,flag,validateFlag,credit_account_type;
	String credebit,select,bounceFine,chkBox,errorFlag;
	
	private String custId,name,cityCode,branchCode,category,subcategory,mailaddress,scst,address,dob,sex,age,occupation;
	private int booleanFlag;	

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getScst() {
		return scst;
	}

	public void setScst(String scst) {
		this.scst = scst;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

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

	

	public String getClr_date() {
		return clr_date;
	}

	public void setClr_date(String clr_date) {
		this.clr_date = clr_date;
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

	
	

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

	public boolean isBounce() {
		return bounce;
	}

	public String getCredebit() {
		return credebit;
	}

	public void setCredebit(String credebit) {
		this.credebit = credebit;
	}

	public String getCredit_account_type() {
		return credit_account_type;
	}

	public void setCredit_account_type(String credit_account_type) {
		this.credit_account_type = credit_account_type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	

	public String getBounceFine() {
		return bounceFine;
	}

	public void setBounceFine(String bounceFine) {
		this.bounceFine = bounceFine;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public int getBooleanFlag() {
		return booleanFlag;
	}

	public void setBooleanFlag(int booleanFlag) {
		this.booleanFlag = booleanFlag;
	}

	public String getChkBox() {
		return chkBox;
	}

	public void setChkBox(String chkBox) {
		this.chkBox = chkBox;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}


	
	
	
	 
}
