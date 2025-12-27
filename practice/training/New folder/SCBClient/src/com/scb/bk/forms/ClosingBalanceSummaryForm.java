package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class ClosingBalanceSummaryForm extends ActionForm {
	
  private String date,totalpages,currentpage,acctype;
  private String forward,pageId,valid,accabbrv;
  String sysdate;


  public String getSysdate() {
  		return sysdate;
  	}


  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
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
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getAccabbrv() {
	return accabbrv;
}
public void setAccabbrv(String accabbrv) {
	this.accabbrv = accabbrv;
}

}