package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;
 
public class LoanApplicationDE extends ActionForm{
  private String txt_reftype,txt_LoanActype,txt_AutoLoanRecovery,txt_acname,txt_paymode,txt_refdisplay,txt_loandisplay,forward,loanacnum,add_val,clear,loan_acc_notfound;
  private String txt_purpose,txt_appdate,txt_ammeligable,txt_payactype,pageId,tabPaneHeading,defaultTab,value,loanacc_closed,loanacc_notfound,loan_acc_notver,amt_cannot_gre_amteli;
  private String update_value,acc_notfound,matdate_elapsed,accountclosed,delete,button_value;
  int txt_refno,txt_payacno,txt_appsrlno,txt_loanaccno,loan_acc_num,loan_acno_update;
  double txt_reqammount,txt_intrate;
  boolean additional_loan;
  
  private int flag_num;

public int getFlag_num() {
	return flag_num;
}

public void setFlag_num(int flag_num) {
	this.flag_num = flag_num;
}

public String getLoanacc_closed() {
	return loanacc_closed;
}

public void setLoanacc_closed(String loanacc_closed) {
	this.loanacc_closed = loanacc_closed;
}

public String getTxt_purpose() {
	return txt_purpose;
}

public void setTxt_purpose(String txt_purpose) {
	this.txt_purpose = txt_purpose;
}

public String getTxt_reftype() {
	return txt_reftype;
}

public void setTxt_reftype(String txt_reftype) {
	this.txt_reftype = txt_reftype;
}

public String getTxt_LoanActype() {
	return txt_LoanActype;
}

public void setTxt_LoanActype(String txt_LoanActype) {
	this.txt_LoanActype = txt_LoanActype;
}



public int getTxt_loanaccno() {
	return txt_loanaccno;
}

public void setTxt_loanaccno(int txt_loanaccno) {
	this.txt_loanaccno = txt_loanaccno;
}

public String getTxt_AutoLoanRecovery() {
	return txt_AutoLoanRecovery;
}

public void setTxt_AutoLoanRecovery(String txt_AutoLoanRecovery) {
	this.txt_AutoLoanRecovery = txt_AutoLoanRecovery;
}



public int getTxt_appsrlno() {
	return txt_appsrlno;
}

public void setTxt_appsrlno(int txt_appsrlno) {
	this.txt_appsrlno = txt_appsrlno;
}

public String getTxt_appdate() {
	return txt_appdate;
}

public void setTxt_appdate(String txt_appdate) {
	this.txt_appdate = txt_appdate;
}

public String getTxt_ammeligable() {
	return txt_ammeligable;
}

public void setTxt_ammeligable(String txt_ammeligable) {
	this.txt_ammeligable = txt_ammeligable;
}



public double getTxt_reqammount() {
	return txt_reqammount;
}

public void setTxt_reqammount(double txt_reqammount) {
	this.txt_reqammount = txt_reqammount;
}

public int getTxt_payacno() {
	return txt_payacno;
}

public void setTxt_payacno(int txt_payacno) {
	this.txt_payacno = txt_payacno;
}

public String getTxt_payactype() {
	return txt_payactype;
}

public void setTxt_payactype(String txt_payactype) {
	this.txt_payactype = txt_payactype;
}

public String getTxt_acname() {
	return txt_acname;
}

public void setTxt_acname(String txt_acname) {
	this.txt_acname = txt_acname;
}

public String getTxt_paymode() {
	return txt_paymode;
}

public void setTxt_paymode(String txt_paymode) {
	this.txt_paymode = txt_paymode;
}

public String getTxt_refdisplay() {
	return txt_refdisplay;
}

public void setTxt_refdisplay(String txt_refdisplay) {
	this.txt_refdisplay = txt_refdisplay;
}

public String getTxt_loandisplay() {
	return txt_loandisplay;
}

public void setTxt_loandisplay(String txt_loandisplay) {
	this.txt_loandisplay = txt_loandisplay;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public int getTxt_refno() {
	return txt_refno;
}

public void setTxt_refno(int txt_refno) {
	this.txt_refno = txt_refno;
}

public String getTabPaneHeading() {
	return tabPaneHeading;
}

public void setTabPaneHeading(String tabPaneHeading) {
	this.tabPaneHeading = tabPaneHeading;
}

public String getDefaultTab() {
	return defaultTab;
}

public void setDefaultTab(String defaultTab) {
	this.defaultTab = defaultTab;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public double getTxt_intrate() {
	return txt_intrate;
}

public void setTxt_intrate(double txt_intrate) {
	this.txt_intrate = txt_intrate;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public String getLoanacnum() {
	return loanacnum;
}

public void setLoanacnum(String loanacnum) {
	this.loanacnum = loanacnum;
}

public String getLoanacc_notfound() {
	return loanacc_notfound;
}

public void setLoanacc_notfound(String loanacc_notfound) {
	this.loanacc_notfound = loanacc_notfound;
}

public String getLoan_acc_notver() {
	return loan_acc_notver;
}

public void setLoan_acc_notver(String loan_acc_notver) {
	this.loan_acc_notver = loan_acc_notver;
}

public boolean isAdditional_loan() {
	return additional_loan;
}

public void setAdditional_loan(boolean additional_loan) {
	this.additional_loan = additional_loan;
}

public String getAdd_val() {
	return add_val;
}

public void setAdd_val(String add_val) {
	this.add_val = add_val;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}

public String getLoan_acc_notfound() {
	return loan_acc_notfound;
}

public void setLoan_acc_notfound(String loan_acc_notfound) {
	this.loan_acc_notfound = loan_acc_notfound;
}

public String getUpdate_value() {
	return update_value;
}

public void setUpdate_value(String update_value) {
	this.update_value = update_value;
}

public String getAmt_cannot_gre_amteli() {
	return amt_cannot_gre_amteli;
}

public void setAmt_cannot_gre_amteli(String amt_cannot_gre_amteli) {
	this.amt_cannot_gre_amteli = amt_cannot_gre_amteli;
}

public String getAcc_notfound() {
	return acc_notfound;
}

public void setAcc_notfound(String acc_notfound) {
	this.acc_notfound = acc_notfound;
}

public String getMatdate_elapsed() {
	return matdate_elapsed;
}

public void setMatdate_elapsed(String matdate_elapsed) {
	this.matdate_elapsed = matdate_elapsed;
}

public String getAccountclosed() {
	return accountclosed;
}

public void setAccountclosed(String accountclosed) {
	this.accountclosed = accountclosed;
}

public String getDelete() {
	return delete;
}

public void setDelete(String delete) {
	this.delete = delete;
}

public String getButton_value() {
	return button_value;
}

public void setButton_value(String button_value) {
	this.button_value = button_value;
}

public int getLoan_acc_num() {
	return loan_acc_num;
}

public void setLoan_acc_num(int loan_acc_num) {
	this.loan_acc_num = loan_acc_num;
}

public int getLoan_acno_update() {
	return loan_acno_update;
}

public void setLoan_acno_update(int loan_acno_update) {
	this.loan_acno_update = loan_acno_update;
}



}
