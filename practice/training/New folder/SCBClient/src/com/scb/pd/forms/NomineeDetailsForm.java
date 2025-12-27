package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class NomineeDetailsForm extends ActionForm {
 private String  nompageid,hidval,cidis;
 private String acno,actype,nomname,dob,gender,address,rel_ship,percentage,nomforward,issuedate,has_ac,cid,nomvalidations;


public String getNompageid() {
	return nompageid;
}

public void setNompageid(String nompageid) {
	this.nompageid = nompageid;
}

public String getNomforward() {
	return nomforward;
}

public void setNomforward(String nomforward) {
	this.nomforward = nomforward;
}

public String getNomvalidations() {
	return nomvalidations;
}

public void setNomvalidations(String nomvalidations) {
	this.nomvalidations = nomvalidations;
}

public String getNomname() {
	return nomname;
}

public void setNomname(String nomname) {
	this.nomname = nomname;
}

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

public String getHidval() {
	return hidval;
}

public void setHidval(String hidval) {
	this.hidval = hidval;
}

public String getCidis() {
	return cidis;
}

public void setCidis(String cidis) {
	this.cidis = cidis;
}


}

