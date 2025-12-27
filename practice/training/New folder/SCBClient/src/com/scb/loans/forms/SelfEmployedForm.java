package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class SelfEmployedForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9130157991754533279L;
	private String employmtnature,address,serv_length,emp_nature;
	int phno;
	double income,expenditure,netincome;

	
	public String getEmploymtnature() {
		return employmtnature;
	}

	public void setEmploymtnature(String employmtnature) {
		this.employmtnature = employmtnature;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getServ_length() {
		return serv_length;
	}

	public void setServ_length(String serv_length) {
		this.serv_length = serv_length;
	}

	public int getPhno() {
		return phno;
	}

	public void setPhno(int phno) {
		this.phno = phno;
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

	public String getEmp_nature() {
		return emp_nature;
	}

	public void setEmp_nature(String emp_nature) {
		this.emp_nature = emp_nature;
	}
}