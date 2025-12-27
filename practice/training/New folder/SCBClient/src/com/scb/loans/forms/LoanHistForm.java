package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class LoanHistForm extends ActionForm {
  String phone,mob,fax;
  String email,npadate,npastage,npaprinciplefrom,lastnotice;
  double npaprincipleamt,npaintamt;
  int npaodprd;
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getMob() {
	return mob;
}
public void setMob(String mob) {
	this.mob = mob;
}
public String getFax() {
	return fax;
}
public void setFax(String fax) {
	this.fax = fax;
}

public int getNpaodprd() {
	return npaodprd;
}
public void setNpaodprd(int npaodprd) {
	this.npaodprd = npaodprd;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNpadate() {
	return npadate;
}
public void setNpadate(String npadate) {
	this.npadate = npadate;
}
public String getNpastage() {
	return npastage;
}
public void setNpastage(String npastage) {
	this.npastage = npastage;
}
public String getNpaprinciplefrom() {
	return npaprinciplefrom;
}
public void setNpaprinciplefrom(String npaprinciplefrom) {
	this.npaprinciplefrom = npaprinciplefrom;
}
public String getLastnotice() {
	return lastnotice;
}
public void setLastnotice(String lastnotice) {
	this.lastnotice = lastnotice;
}
public double getNpaprincipleamt() {
	return npaprincipleamt;
}
public void setNpaprincipleamt(double npaprincipleamt) {
	this.npaprincipleamt = npaprincipleamt;
}
public double getNpaintamt() {
	return npaintamt;
}
public void setNpaintamt(double npaintamt) {
	this.npaintamt = npaintamt;
}

  
  
  
  
}