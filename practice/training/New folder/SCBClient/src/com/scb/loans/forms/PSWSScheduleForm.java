package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;
public class PSWSScheduleForm extends ActionForm {

	private String txt_repdate,button_value,delete_value,but_print,testing;


	private int txt_priorityseccode;

	private PageIdForm  pageidentity= new PageIdForm();
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getBut_print() {
		return but_print;
	}

	public void setBut_print(String butPrint) {
		but_print = butPrint;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}
	
	public String getTxt_repdate() {
		return txt_repdate;
	}

	public void setTxt_repdate(String txt_repdate) {
		this.txt_repdate = txt_repdate;
	}

	public int getTxt_priorityseccode() {
		return txt_priorityseccode;
	}

	public void setTxt_priorityseccode(int txt_priorityseccode) {
		this.txt_priorityseccode = txt_priorityseccode;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public String getDelete_value() {
		return delete_value;
	}

	public void setDelete_value(String delete_value) {
		this.delete_value = delete_value;
	}

	

	

	
	
}
