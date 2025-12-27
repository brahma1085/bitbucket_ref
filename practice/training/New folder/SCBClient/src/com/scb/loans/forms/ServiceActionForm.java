package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class ServiceActionForm extends ActionForm{
   /**
	 * 
	 */
	private static final long serialVersionUID = 3850308105373006037L;
	String employername,address,designation,department,serv_length;
   double income,expenditure,netincome;
   int phoneno,empno;
   boolean confirmation,transferable,certicateenclosed;
public String getEmployername() {
	return employername;
}
public void setEmployername(String employername) {
	this.employername = employername;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getServ_length() {
	return serv_length;
}
public void setServ_length(String serv_length) {
	this.serv_length = serv_length;
}
public double getIncome() {
	return income;
}
public void setIncome(double income) {
	this.income = income;
}
public double getExpenditure() {
	return expenditure;
}
public void setExpenditure(double expenditure) {
	this.expenditure = expenditure;
}
public double getNetincome() {
	return netincome;
}
public void setNetincome(double netincome) {
	this.netincome = netincome;
}
public int getPhoneno() {
	return phoneno;
}
public void setPhoneno(int phoneno) {
	this.phoneno = phoneno;
}
public int getEmpno() {
	return empno;
}
public void setEmpno(int empno) {
	this.empno = empno;
}
public boolean isConfirmation() {
	return confirmation;
}
public void setConfirmation(boolean confirmation) {
	this.confirmation = confirmation;
}
public boolean isTransferable() {
	return transferable;
}
public void setTransferable(boolean transferable) {
	this.transferable = transferable;
}
public boolean isCerticateenclosed() {
	return certicateenclosed;
}
public void setCerticateenclosed(boolean certicateenclosed) {
	this.certicateenclosed = certicateenclosed;
}
}
