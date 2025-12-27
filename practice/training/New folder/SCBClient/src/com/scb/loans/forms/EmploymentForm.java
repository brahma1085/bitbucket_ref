package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class EmploymentForm extends ActionForm{
	String employmtnature,address;

	private PageIdForm  pageidentity= new PageIdForm();
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

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
}