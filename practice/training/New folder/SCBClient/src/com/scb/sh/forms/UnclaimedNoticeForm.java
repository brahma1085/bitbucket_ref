package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class UnclaimedNoticeForm extends ActionForm {
 private String pageId;
 private String actype,notice_dt,template ;
 private String show,forward,validations,content;

public String getShow() {
	return show;
}

public void setShow(String show) {
	this.show = show;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getNotice_dt() {
	return notice_dt;
}

public void setNotice_dt(String notice_dt) {
	this.notice_dt = notice_dt;
}

public String getValidations() {
	return validations;
}

public void setValidations(String validations) {
	this.validations = validations;
}

public String getTemplate() {
	return template;
}

public void setTemplate(String template) {
	this.template = template;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}


}
