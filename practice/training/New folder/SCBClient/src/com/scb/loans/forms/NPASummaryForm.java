package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class NPASummaryForm extends ActionForm {
  private String processdate,forward,but_print,testing;
 
boolean days_180;
  private int num_days;
  
  private PageIdForm  pageidentity= new PageIdForm();

public String getProcessdate() {
	return processdate;
}

public void setProcessdate(String processdate) {
	this.processdate = processdate;
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

public int getNum_days() {
	return num_days;
}

public void setNum_days(int num_days) {
	this.num_days = num_days;
}

public boolean isDays_180() {
	return days_180;
}

public void setDays_180(boolean days_180) {
	this.days_180 = days_180;
}





}
