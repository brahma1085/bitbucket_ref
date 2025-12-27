package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class Reschedulingform extends ActionForm{
  String acctype,effective_date,name,purpose,disbdate,int_upto_date,sbaccno,shno,accountfound;
  int accno,noofinst,period,installments;
  double amount,intrate,sanctioned_amt,loan_bal,instal_amt,amountbetween;
  String forward,result_reschedule,sysdate;
   
   
  
  private PageIdForm  pageidentity= new PageIdForm();
  
  public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}
  
  public PageIdForm getPageidentity() {
	return pageidentity;
}
public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getEffective_date() {
	return effective_date;
}
public void setEffective_date(String effective_date) {
	this.effective_date = effective_date;
}
public int getAccno() {
	return accno;
}
public void setAccno(int accno) {
	this.accno = accno;
}
public int getInstallments() {
	return installments;
}
public void setInstallments(int installments) {
	this.installments = installments;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPurpose() {
	return purpose;
}
public void setPurpose(String purpose) {
	this.purpose = purpose;
}
public String getDisbdate() {
	return disbdate;
}
public void setDisbdate(String disbdate) {
	this.disbdate = disbdate;
}
public String getInt_upto_date() {
	return int_upto_date;
}
public void setInt_upto_date(String int_upto_date) {
	this.int_upto_date = int_upto_date;
}
public String getSbaccno() {
	return sbaccno;
}
public void setSbaccno(String sbaccno) {
	this.sbaccno = sbaccno;
}
public String getShno() {
	return shno;
}
public void setShno(String shno) {
	this.shno = shno;
}
public int getNoofinst() {
	return noofinst;
}
public void setNoofinst(int noofinst) {
	this.noofinst = noofinst;
}
public int getPeriod() {
	return period;
}
public void setPeriod(int period) {
	this.period = period;
}
public double getIntrate() {
	return intrate;
}
public void setIntrate(double intrate) {
	this.intrate = intrate;
}
public double getSanctioned_amt() {
	return sanctioned_amt;
}
public void setSanctioned_amt(double sanctioned_amt) {
	this.sanctioned_amt = sanctioned_amt;
}
public double getLoan_bal() {
	return loan_bal;
}
public void setLoan_bal(double loan_bal) {
	this.loan_bal = loan_bal;
}
public double getInstal_amt() {
	return instal_amt;
}      
public void setInstal_amt(double instal_amt) {
	this.instal_amt = instal_amt;
}

public double getAmountbetween() {
	return amountbetween;
}
public void setAmountbetween(double amountbetween) {
	this.amountbetween = amountbetween;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getAccountfound() {
	return accountfound;
}
public void setAccountfound(String accountfound) {
	this.accountfound = accountfound;
}
public String getResult_reschedule() {
	return result_reschedule;
}
public void setResult_reschedule(String result_reschedule) {
	this.result_reschedule = result_reschedule;
}
}
