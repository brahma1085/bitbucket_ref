package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class CombinedChequeForm extends ActionForm{
	
	String pageId,ctrlno,accTyp,formflag,totamount,validateFlag,flag;
	String accNum,companyName,amount,loanActyp,loanAcnum;
	int booleanFlag;
	boolean chkBox;
	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getCtrlno() {
		return ctrlno;
	}

	public void setCtrlno(String ctrlno) {
		this.ctrlno = ctrlno;
	}

	public String getTotamount() {
		return totamount;
	}

	public void setTotamount(String totamount) {
		this.totamount = totamount;
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

	

	public int getBooleanFlag() {
		return booleanFlag;
	}

	public void setBooleanFlag(int booleanFlag) {
		this.booleanFlag = booleanFlag;
	}

	public String getFormflag() {
		return formflag;
	}

	public void setFormflag(String formflag) {
		this.formflag = formflag;
	}

	public String getAccTyp() {
		return accTyp;
	}

	public void setAccTyp(String accTyp) {
		this.accTyp = accTyp;
	}

	public String getAccNum() {
		return accNum;
	}

	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLoanActyp() {
		return loanActyp;
	}

	public void setLoanActyp(String loanActyp) {
		this.loanActyp = loanActyp;
	}

	public String getLoanAcnum() {
		return loanAcnum;
	}

	public void setLoanAcnum(String loanAcnum) {
		this.loanAcnum = loanAcnum;
	}

	

}
