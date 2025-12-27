package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class ShareLedgerform extends ActionForm {
 private String pageId;
 private String acctype,acno,introby,sharetype,branch,nom_name,rel,acc_status,pay_mode,cert_dt,cert_no,lnavld,div_dt,name,ac_not_fnd,forward,clear;
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getAcno() {
	return acno;
}
public void setAcno(String acno) {
	this.acno = acno;
}
public String getIntroby() {
	return introby;
}
public void setIntroby(String introby) {
	this.introby = introby;
}
public String getSharetype() {
	return sharetype;
}
public void setSharetype(String sharetype) {
	this.sharetype = sharetype;
}
public String getBranch() {
	return branch;
}
public void setBranch(String branch) {
	this.branch = branch;
}
public String getNom_name() {
	return nom_name;
}
public void setNom_name(String nom_name) {
	this.nom_name = nom_name;
}
public String getRel() {
	return rel;
}
public void setRel(String rel) {
	this.rel = rel;
}
public String getAcc_status() {
	return acc_status;
}
public void setAcc_status(String acc_status) {
	this.acc_status = acc_status;
}
public String getPay_mode() {
	return pay_mode;
}
public void setPay_mode(String pay_mode) {
	this.pay_mode = pay_mode;
}
public String getCert_dt() {
	return cert_dt;
}
public void setCert_dt(String cert_dt) {
	this.cert_dt = cert_dt;
}
public String getCert_no() {
	return cert_no;
}
public void setCert_no(String cert_no) {
	this.cert_no = cert_no;
}
public String getLnavld() {
	return lnavld;
}
public void setLnavld(String lnavld) {
	this.lnavld = lnavld;
}
public String getDiv_dt() {
	return div_dt;
}
public void setDiv_dt(String div_dt) {
	this.div_dt = div_dt;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAc_not_fnd() {
	return ac_not_fnd;
}
public void setAc_not_fnd(String ac_not_fnd) {
	this.ac_not_fnd = ac_not_fnd;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getClear() {
	return clear;
}
public void setClear(String clear) {
	this.clear = clear;
}
}
