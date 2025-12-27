package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class RecoveryForm extends ActionForm {
   String acctype,details,shtype,disbursed_on,purpose,repay_type,int_rate_type,forward,name,tabPaneHeading,accountnotfound;
   int shno,period,accno;
   double shamt,excessshamt,loanamt,instamt,intrate,penrate;
   
   // variables used in Recovery By Transfer 
   int trf_voucherno,trf_accno;
   String trf_from,intuptodate,acc_notfound,validacc,button_value,clearFl;
   double balance,amount;
   double advance,principle,interest,penalinterest,othercharges,extraint;
   
   
   
   
   
   private String txt_addpaid,txt_ppoverdue,txt_current_inst,txt_interest,txt_pinealInt,txt_otherchar,txt_extra,txt_tca;
   private double txt_loanba,txt_others,txt_extraint;
	
   private PageIdForm  pageidentity= new PageIdForm();
   LoanStatusForm loanstatusform = new LoanStatusForm();
   LoanHistForm loanhistform = new LoanHistForm();
   RecoveryByTransfer transferform = new RecoveryByTransfer();
   LastLoanTranForm lastloantran = new LastLoanTranForm();
   


public String getAcctype() {
	return acctype;
}


public String getTxt_addpaid() {
	return txt_addpaid;
}


public void setTxt_addpaid(String txt_addpaid) {
	this.txt_addpaid = txt_addpaid;
}


public String getTxt_ppoverdue() {
	return txt_ppoverdue;
}


public void setTxt_ppoverdue(String txt_ppoverdue) {
	this.txt_ppoverdue = txt_ppoverdue;
}


public String getTxt_current_inst() {
	return txt_current_inst;
}


public void setTxt_current_inst(String txt_current_inst) {
	this.txt_current_inst = txt_current_inst;
}


public String getTxt_interest() {
	return txt_interest;
}


public void setTxt_interest(String txt_interest) {
	this.txt_interest = txt_interest;
}


public String getTxt_pinealInt() {
	return txt_pinealInt;
}


public void setTxt_pinealInt(String txt_pinealInt) {
	this.txt_pinealInt = txt_pinealInt;
}


public String getTxt_otherchar() {
	return txt_otherchar;
}


public void setTxt_otherchar(String txt_otherchar) {
	this.txt_otherchar = txt_otherchar;
}


public String getTxt_extra() {
	return txt_extra;
}


public void setTxt_extra(String txt_extra) {
	this.txt_extra = txt_extra;
}


public String getTxt_tca() {
	return txt_tca;
}


public void setTxt_tca(String txt_tca) {
	this.txt_tca = txt_tca;
}


public double getTxt_loanba() {
	return txt_loanba;
}


public void setTxt_loanba(double txt_loanba) {
	this.txt_loanba = txt_loanba;
}


public void setAcctype(String acctype) {
	this.acctype = acctype;
}


public String getDetails() {
	return details;
}


public void setDetails(String details) {
	this.details = details;
}


public String getShtype() {
	return shtype;
}


public void setShtype(String shtype) {
	this.shtype = shtype;
}


public String getDisbursed_on() {
	return disbursed_on;
}


public void setDisbursed_on(String disbursed_on) {
	this.disbursed_on = disbursed_on;
}


public String getPurpose() {
	return purpose;
}


public void setPurpose(String purpose) {
	this.purpose = purpose;
}


public String getRepay_type() {
	return repay_type;
}


public void setRepay_type(String repay_type) {
	this.repay_type = repay_type;
}


public String getInt_rate_type() {
	return int_rate_type;
}


public void setInt_rate_type(String int_rate_type) {
	this.int_rate_type = int_rate_type;
}


public String getForward() {
	return forward;
}


public void setForward(String forward) {
	this.forward = forward;
}


public int getShno() {
	return shno;
}


public void setShno(int shno) {
	this.shno = shno;
}


public int getPeriod() {
	return period;
}


public void setPeriod(int period) {
	this.period = period;
}


public double getShamt() {
	return shamt;
}


public void setShamt(double shamt) {
	this.shamt = shamt;
}


public double getExcessshamt() {
	return excessshamt;
}


public void setExcessshamt(double excessshamt) {
	this.excessshamt = excessshamt;
}


public double getLoanamt() {
	return loanamt;
}


public void setLoanamt(double loanamt) {
	this.loanamt = loanamt;
}


public double getInstamt() {
	return instamt;
}


public void setInstamt(double instamt) {
	this.instamt = instamt;
}


public double getIntrate() {
	return intrate;
}


public void setIntrate(double intrate) {
	this.intrate = intrate;
}


public double getPenrate() {
	return penrate;
}


public void setPenrate(double penrate) {
	this.penrate = penrate;
}


public PageIdForm getPageidentity() {
	return pageidentity;
}


public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}


public int getAccno() {
	return accno;
}


public void setAccno(int accno) {
	this.accno = accno;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getTabPaneHeading() {
	return tabPaneHeading;
}


public void setTabPaneHeading(String tabPaneHeading) {
	this.tabPaneHeading = tabPaneHeading;
}


public LoanStatusForm getLoanstatusform() {
	return loanstatusform;
}


public void setLoanstatusform(LoanStatusForm loanstatusform) {
	this.loanstatusform = loanstatusform;
}


public LoanHistForm getLoanhistform() {
	return loanhistform;
}


public void setLoanhistform(LoanHistForm loanhistform) {
	this.loanhistform = loanhistform;
}


public RecoveryByTransfer getTransferform() {
	return transferform;
}


public void setTransferform(RecoveryByTransfer transferform) {
	this.transferform = transferform;
}


public int getTrf_voucherno() {
	return trf_voucherno;
}


public void setTrf_voucherno(int trf_voucherno) {
	this.trf_voucherno = trf_voucherno;
}


public int getTrf_accno() {
	return trf_accno;
}


public void setTrf_accno(int trf_accno) {
	this.trf_accno = trf_accno;
}


public String getTrf_from() {
	return trf_from;
}


public void setTrf_from(String trf_from) {
	this.trf_from = trf_from;
}


public String getIntuptodate() {
	return intuptodate;
}


public void setIntuptodate(String intuptodate) {
	this.intuptodate = intuptodate;
}


public String getAcc_notfound() {
	return acc_notfound;
}


public void setAcc_notfound(String acc_notfound) {
	this.acc_notfound = acc_notfound;
}


public double getBalance() {
	return balance;
}


public void setBalance(double balance) {
	this.balance = balance;
}


public double getAmount() {
	return amount;
}


public void setAmount(double amount) {
	this.amount = amount;
}


public double getAdvance() {
	return advance;
}


public void setAdvance(double advance) {
	this.advance = advance;
}


public double getPrinciple() {
	return principle;
}


public void setPrinciple(double principle) {
	this.principle = principle;
}


public double getInterest() {
	return interest;
}


public void setInterest(double interest) {
	this.interest = interest;
}


public double getPenalinterest() {
	return penalinterest;
}


public void setPenalinterest(double penalinterest) {
	this.penalinterest = penalinterest;
}


public double getOthercharges() {
	return othercharges;
}


public void setOthercharges(double othercharges) {
	this.othercharges = othercharges;
}


public double getExtraint() {
	return extraint;
}


public void setExtraint(double extraint) {
	this.extraint = extraint;
}


public String getValidacc() {
	return validacc;
}


public void setValidacc(String validacc) {
	this.validacc = validacc;
}


public LastLoanTranForm getLastloantran() {
	return lastloantran;
}


public void setLastloantran(LastLoanTranForm lastloantran) {
	this.lastloantran = lastloantran;
}


public String getAccountnotfound() {
	return accountnotfound;
}


public void setAccountnotfound(String accountnotfound) {
	this.accountnotfound = accountnotfound;
}


public String getButton_value() {
	return button_value;
}


public void setButton_value(String button_value) {
	this.button_value = button_value;
}


public String getClearFl() {
	return clearFl;
}


public void setClearFl(String clearFl) {
	this.clearFl = clearFl;
}


public double getTxt_others() {
	return txt_others;
}


public void setTxt_others(double txt_others) {
	this.txt_others = txt_others;
}


public double getTxt_extraint() {
	return txt_extraint;
}


public void setTxt_extraint(double txt_extraint) {
	this.txt_extraint = txt_extraint;
}
}
