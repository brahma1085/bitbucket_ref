package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class NomineeForm extends ActionForm {
 private String  pageId;
 private String acno,actype,name,dob,gender,address,rel_ship,percentage,forward,issuedate,has_ac,cid,validations;

public String getAcno() {
	return acno;
}

public void setAcno(String acno) {
	this.acno = acno;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDob() {
	return dob;
}

public void setDob(String dob) {
	this.dob = dob;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getRel_ship() {
	return rel_ship;
}

public void setRel_ship(String rel_ship) {
	this.rel_ship = rel_ship;
}

public String getPercentage() {
	return percentage;
}

public void setPercentage(String percentage) {
	this.percentage = percentage;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getIssuedate() {
	return issuedate;
}

public void setIssuedate(String issuedate) {
	this.issuedate = issuedate;
}

public String getHas_ac() {
	return has_ac;
}

public void setHas_ac(String has_ac) {
	this.has_ac = has_ac;
}

public String getCid() {
	return cid;
}

public void setCid(String cid) {
	this.cid = cid;
}

public String getValidations() {
	return validations;
}

public void setValidations(String validations) {
	this.validations = validations;
}
}
