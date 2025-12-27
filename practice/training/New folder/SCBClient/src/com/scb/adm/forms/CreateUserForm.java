package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class CreateUserForm extends ActionForm {
	String pageId;
String uid,cid,sname,pwd,rpwd,pxp,pxd,acopfdate,acoptdate,option,branch;

public String getBranch() {
	return branch;
}
public void setBranch(String branch) {
	this.branch = branch;
}
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
public String getSname() {
	return sname;
}
public void setSname(String sname) {
	this.sname = sname;
}
public String getPwd() {
	return pwd;
}
public void setPwd(String pwd) {
	this.pwd = pwd;
}
public String getRpwd() {
	return rpwd;
}
public void setRpwd(String rpwd) {
	this.rpwd = rpwd;
}
public String getPxp() {
	return pxp;
}
public void setPxp(String pxp) {
	this.pxp = pxp;
}
public String getPxd() {
	return pxd;
}
public void setPxd(String pxd) {
	this.pxd = pxd;
}
public String getAcopfdate() {
	return acopfdate;
}
public void setAcopfdate(String acopfdate) {
	this.acopfdate = acopfdate;
}
public String getAcoptdate() {
	return acoptdate;
}
public void setAcoptdate(String acoptdate) {
	this.acoptdate = acoptdate;
}
public String getOption() {
	return option;
}
public void setOption(String option) {
	this.option = option;
}
}
