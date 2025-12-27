package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class InterestAccuredReportForm extends ActionForm {
  int from_accno,to_accno,fromintamt,tointamt;
  String acctype,reportdate,forward,but_print,testing;
  String sysdate;

  public String getSysdate() {
  		return sysdate;
  	}

  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
  	}

private PageIdForm  pageidentity= new PageIdForm();

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
  
public int getFrom_accno() {
	return from_accno;
}
public void setFrom_accno(int from_accno) {
	this.from_accno = from_accno;
}
public int getTo_accno() {
	return to_accno;
}
public void setTo_accno(int to_accno) {
	this.to_accno = to_accno;
}
public int getFromintamt() {
	return fromintamt;
}
public void setFromintamt(int fromintamt) {
	this.fromintamt = fromintamt;
}
public int getTointamt() {
	return tointamt;
}
public void setTointamt(int tointamt) {
	this.tointamt = tointamt;
}
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getReportdate() {
	return reportdate;
}
public void setReportdate(String reportdate) {
	this.reportdate = reportdate;
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
  
  
  
}
