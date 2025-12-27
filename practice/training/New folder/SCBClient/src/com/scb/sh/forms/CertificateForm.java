package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class CertificateForm extends ActionForm {
 private String pageId;
 private String actype,sh_cat,ac_no,validations;
 private String name,address,sh_certno,category,distnum,sh_val,no_of_shares,nominee,cert_date,div_pay,relation;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getSh_certno() {
	return sh_certno;
}

public void setSh_certno(String sh_certno) {
	this.sh_certno = sh_certno;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public String getDistnum() {
	return distnum;
}

public void setDistnum(String distnum) {
	this.distnum = distnum;
}

public String getSh_val() {
	return sh_val;
}

public void setSh_val(String sh_val) {
	this.sh_val = sh_val;
}

public String getNo_of_shares() {
	return no_of_shares;
}

public void setNo_of_shares(String no_of_shares) {
	this.no_of_shares = no_of_shares;
}

public String getNominee() {
	return nominee;
}

public void setNominee(String nominee) {
	this.nominee = nominee;
}

public String getCert_date() {
	return cert_date;
}

public void setCert_date(String cert_date) {
	this.cert_date = cert_date;
}

public String getDiv_pay() {
	return div_pay;
}

public void setDiv_pay(String div_pay) {
	this.div_pay = div_pay;
}

public String getRelation() {
	return relation;
}

public void setRelation(String relation) {
	this.relation = relation;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getSh_cat() {
	return sh_cat;
}

public void setSh_cat(String sh_cat) {
	this.sh_cat = sh_cat;
}

public String getAc_no() {
	return ac_no;
}

public void setAc_no(String ac_no) {
	this.ac_no = ac_no;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getValidations() {
	return validations;
}

public void setValidations(String validations) {
	this.validations = validations;
}
}
