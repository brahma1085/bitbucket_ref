package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class PSWSProcessingForm extends ActionForm{

	private String txt_date,button_val,pswsprocessing,pro_value,but_print,testing;
	String sysdate;

	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

	private PageIdForm  pageidentity= new PageIdForm();

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	
	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}
	
	public String getBut_print() {
		return but_print;
	}

	public void setBut_print(String butPrint) {
		but_print = butPrint;
	}


	public String getTxt_date() {
		return txt_date;
	}

	public void setTxt_date(String txt_date) {
		this.txt_date = txt_date;
	}

	public String getButton_val() {
		return button_val;
	}

	public void setButton_val(String button_val) {
		this.button_val = button_val;
	}

	public String getPswsprocessing() {
		return pswsprocessing;
	}

	public void setPswsprocessing(String pswsprocessing) {
		this.pswsprocessing = pswsprocessing;
	}

	public String getPro_value() {
		return pro_value;
	}

	public void setPro_value(String pro_value) {
		this.pro_value = pro_value;
	}
	
}
