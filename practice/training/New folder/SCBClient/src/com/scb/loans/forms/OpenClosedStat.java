package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class OpenClosedStat extends ActionForm {
   String fromdate,todate,acctype,open_closed,forward,select,but_print,testing;
   String sysdate;


   public String getSysdate() {
   		return sysdate;
   	}


   	public void setSysdate(String sysdate) {
   		this.sysdate = sysdate;
   	}

private PageIdForm  pageidentity= new PageIdForm();
public String getTesting() {
	return testing;
}

public void setTesting(String testing) {
	this.testing = testing;
}

public String getFromdate() {
	return fromdate;
}

public void setFromdate(String fromdate) {
	this.fromdate = fromdate;
}
public String getBut_print() {
	return but_print;
}

public void setBut_print(String butPrint) {
	but_print = butPrint;
}

public String getTodate() {
	return todate;
}

public void setTodate(String todate) {
	this.todate = todate;
}

public String getAcctype() {
	return acctype;
}

public void setAcctype(String acctype) {
	this.acctype = acctype;
}

public String getOpen_closed() {
	return open_closed;
}

public void setOpen_closed(String open_closed) {
	this.open_closed = open_closed;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public PageIdForm getPageidentity() {
	return pageidentity;
}

public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}

public String getSelect() {
	return select;
}

public void setSelect(String select) {
	this.select = select;
}
}
