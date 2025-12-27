package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class SIExecutionForm extends ActionForm {
	
private String forward;
private String  pageId,valid;
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

}