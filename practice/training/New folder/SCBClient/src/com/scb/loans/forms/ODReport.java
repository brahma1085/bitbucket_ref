package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class ODReport extends ActionForm{
  String acctype,stagetype,reportdate,intuptodate,forward,but_print,testing;
  int from_accno,to_accno;
  double prn_fromamt,prn_toamt,int_fromamt,into_toamt;
  
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
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getStagetype() {
	return stagetype;
}
public void setStagetype(String stagetype) {
	this.stagetype = stagetype;
}
public String getReportdate() {
	return reportdate;
}
public void setReportdate(String reportdate) {
	this.reportdate = reportdate;
}
public String getIntuptodate() {
	return intuptodate;
}
public void setIntuptodate(String intuptodate) {
	this.intuptodate = intuptodate;
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
public double getPrn_fromamt() {
	return prn_fromamt;
}
public void setPrn_fromamt(double prn_fromamt) {
	this.prn_fromamt = prn_fromamt;
}
public double getPrn_toamt() {
	return prn_toamt;
}
public void setPrn_toamt(double prn_toamt) {
	this.prn_toamt = prn_toamt;
}
public double getInt_fromamt() {
	return int_fromamt;
}
public void setInt_fromamt(double int_fromamt) {
	this.int_fromamt = int_fromamt;
}
public double getInto_toamt() {
	return into_toamt;
}
public void setInto_toamt(double into_toamt) {
	this.into_toamt = into_toamt;
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
