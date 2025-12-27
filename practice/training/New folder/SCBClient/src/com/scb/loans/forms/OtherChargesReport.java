package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class OtherChargesReport extends ActionForm {
  String acctype,fromdate,todate,combo_othercharges,forward,validateFlag,but_print,testing;
  String sysdate;


  public String getSysdate() {
  		return sysdate;
  	}
  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
  	}
  
  public String getBut_print() {
	return but_print;
}
public void setBut_print(String butPrint) {
	but_print = butPrint;
}
public String getTesting() {
	return testing;
}
public void setTesting(String testing) {
	this.testing = testing;
}
int from_acno,to_acno;
  
private PageIdForm  pageidentity= new PageIdForm();
  

public String getFromdate() {
	return fromdate;
}
public void setFromdate(String fromdate) {
	this.fromdate = fromdate;
}
public String getTodate() {
	return todate;
}
public void setTodate(String todate) {
	this.todate = todate;
}
public int getFrom_acno() {
	return from_acno;
}
public void setFrom_acno(int from_acno) {
	this.from_acno = from_acno;
}
public int getTo_acno() {
	return to_acno;
}
public void setTo_acno(int to_acno) {
	this.to_acno = to_acno;
}
public String getCombo_othercharges() {
	return combo_othercharges;
}
public void setCombo_othercharges(String combo_othercharges) {
	this.combo_othercharges = combo_othercharges;
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
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getValidateFlag() {
	return validateFlag;
}
public void setValidateFlag(String validateFlag) {
	this.validateFlag = validateFlag;
}
  
  
}
