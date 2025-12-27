package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class PensionActionForm extends ActionForm {
   /**
	 * 
	 */
	private static final long serialVersionUID = 2629905132453107047L;
String empname,address,bankname,acctype;
   int phno,accno;
   double pensionamt,netincome;
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getBankname() {
	return bankname;
}
public void setBankname(String bankname) {
	this.bankname = bankname;
}
public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public int getPhno() {
	return phno;
}
public void setPhno(int phno) {
	this.phno = phno;
}
public int getAccno() {
	return accno;
}
public void setAccno(int accno) {
	this.accno = accno;
}
public double getPensionamt() {
	return pensionamt;
}
public void setPensionamt(double pensionamt) {
	this.pensionamt = pensionamt;
}
public double getNetincome() {
	return netincome;
}
public void setNetincome(double netincome) {
	this.netincome = netincome;
}
}
