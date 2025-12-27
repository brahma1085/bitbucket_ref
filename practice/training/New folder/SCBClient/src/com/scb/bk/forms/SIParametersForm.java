package com.scb.bk.forms;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public class SIParametersForm extends ActionForm {
	
private String forward;
private String fromacc,toacc, pageId,valid,prior_num;


public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getFromacc() {
	return fromacc;
}
public void setFromacc(String fromacc) {
	this.fromacc = fromacc;
}
public String getToacc() {
	return toacc;
}
public void setToacc(String toacc) {
	this.toacc = toacc;
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
public String getPrior_num() {
	return prior_num;
}
public void setPrior_num(String prior_num) {
	this.prior_num = prior_num;
}


}