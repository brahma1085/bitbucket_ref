package com.scb.bk.forms;

import org.apache.struts.action.ActionForm;

public class OdccbalForm extends ActionForm {

private String forward;
private String pageId,valid,accountType,accountNo,accountName,accountBalance,accountOpenDate,creditLimit;

public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}
public String getCreditLimit() {
	return creditLimit;
}
public void setCreditLimit(String creditLimit) {
	this.creditLimit = creditLimit;
}
public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public String getAccountNo() {
	return accountNo;
}
public void setAccountNo(String accountNo) {
	this.accountNo = accountNo;
}
public String getAccountName() {
	return accountName;
}
public void setAccountName(String accountName) {
	this.accountName = accountName;
}
public String getAccountBalance() {
	return accountBalance;
}
public void setAccountBalance(String accountBalance) {
	this.accountBalance = accountBalance;
}
public String getAccountOpenDate() {
	return accountOpenDate;
}
public void setAccountOpenDate(String accountOpenDate) {
	this.accountOpenDate = accountOpenDate;
}


}
