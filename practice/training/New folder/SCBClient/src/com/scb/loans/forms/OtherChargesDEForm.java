package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class OtherChargesDEForm extends ActionForm{
   int refno,acno,installments,refnoGen,update,delete,result;
   double sancamt,tranamt;
   String acctype,sancdate,trandate,reason,forward,accountnotfound,tabPaneHeading,defaultTab;
   
   String trnamt;
   
   private PageIdForm  pageidentity= new PageIdForm();
   LoanStatusForm loanstatusform=new LoanStatusForm();
   
public LoanStatusForm getLoanstatusform() {
	return loanstatusform;
}
public void setLoanstatusform(LoanStatusForm loanstatusform) {
	this.loanstatusform = loanstatusform;
}
public int getRefno() {
	return refno;
}
public void setRefno(int refno) {
	this.refno = refno;
}
public int getAcno() {
	return acno;
}
public void setAcno(int acno) {
	this.acno = acno;
}
public int getInstallments() {
	return installments;
}
public void setInstallments(int installments) {
	this.installments = installments;
}
public double getSancamt() {
	return sancamt;
}
public void setSancamt(double sancamt) {
	this.sancamt = sancamt;
}
/*public double getTranamt() {
	return tranamt;
}
public void setTranamt(double tranamt) {
	this.tranamt = tranamt;
}*/
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getSancdate() {
	return sancdate;
}
public void setSancdate(String sancdate) {
	this.sancdate = sancdate;
}
public String getTrandate() {
	return trandate;
}
public void setTrandate(String trandate) {
	this.trandate = trandate;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public PageIdForm getPageidentity() {
	return pageidentity;
}
public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public int getRefnoGen() {
	return refnoGen;
}
public void setRefnoGen(int refnoGen) {
	this.refnoGen = refnoGen;
}
public String getAccountnotfound() {
	return accountnotfound;
}
public void setAccountnotfound(String accountnotfound) {
	this.accountnotfound = accountnotfound;
}
public int getUpdate() {
	return update;
}
public void setUpdate(int update) {
	this.update = update;
}
public int getDelete() {
	return delete;
}
public void setDelete(int delete) {
	this.delete = delete;
}
public int getResult() {
	return result;
}
public void setResult(int result) {
	this.result = result;
}
public double getTranamt() {
	return tranamt;
}
public void setTranamt(double tranamt) {
	this.tranamt = tranamt;
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

}
