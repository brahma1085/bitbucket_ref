package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class JewelReportForm extends ActionForm
{
	private String txt_fromdate,txt_To_date,but_print,testing,forward;
	String sysdate;


	public String getSysdate() {
			return sysdate;
		}

		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	private PageIdForm  pageidentity= new PageIdForm();
	
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

	public String getTxt_fromdate() {
		return txt_fromdate;
	}

	public void setTxt_fromdate(String txt_fromdate) {
		this.txt_fromdate = txt_fromdate;
	}

	public String getTxt_To_date() {
		return txt_To_date;
	}

	public void setTxt_To_date(String txt_To_date) {
		this.txt_To_date = txt_To_date;
	}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
}
