package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class PSWSProcessForm extends ActionForm {
  String date,forward;

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}
  
  
}
