package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class BusinessActionForm extends ActionForm {
   /**
	 * 
	 */
	private static final long serialVersionUID = 2788459016706046104L;
private String concernname,address,businessnature,employmtnature;
   private double income,expenditure,surplus,avg_turnover;
   private int phoneno;
public String getConcernname() {
	return concernname;
}
public void setConcernname(String concernname) {
	this.concernname = concernname;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getBusinessnature() {
	return businessnature;
}
public void setBusinessnature(String businessnature) {
	this.businessnature = businessnature;
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
public double getSurplus() {
	return surplus;
}
public void setSurplus(double surplus) {
	this.surplus = surplus;
}
public double getAvg_turnover() {
	return avg_turnover;
}
public void setAvg_turnover(double avg_turnover) {
	this.avg_turnover = avg_turnover;
}
public int getPhoneno() {
	return phoneno;
}
public void setPhoneno(int phoneno) {
	this.phoneno = phoneno;
}
public String getEmploymtnature() {
	return employmtnature;
}
public void setEmploymtnature(String employmtnature) {
	this.employmtnature = employmtnature;
}
}
