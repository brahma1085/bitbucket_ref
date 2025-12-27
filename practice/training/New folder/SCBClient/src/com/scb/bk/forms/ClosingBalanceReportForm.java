package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class ClosingBalanceReportForm extends ActionForm {
	
  private String fromdate,todate,acccategory,shcategory,totalpages,currentpage,acctype;
  private String forward,pageId,valid,agentno,date;
  String sysdate;


  public String getSysdate() {
  		return sysdate;
  	}


  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
  	}

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
public String getAcccategory() {
	return acccategory;
}
public void setAcccategory(String acccategory) {
	this.acccategory = acccategory;
}
public String getShcategory() {
	return shcategory;
}
public void setShcategory(String shcategory) {
	this.shcategory = shcategory;
}
public String getTotalpages() {
	return totalpages;
}
public void setTotalpages(String totalpages) {
	this.totalpages = totalpages;
}
public String getCurrentpage() {
	return currentpage;
}
public void setCurrentpage(String currentpage) {
	this.currentpage = currentpage;
}
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getAgentno() {
	return agentno;
}
public void setAgentno(String agentno) {
	this.agentno = agentno;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
}