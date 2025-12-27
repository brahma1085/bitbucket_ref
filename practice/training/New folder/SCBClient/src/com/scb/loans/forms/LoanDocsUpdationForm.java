package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;
 
public class LoanDocsUpdationForm extends ActionForm{
	 
	private String ac_type,clear,check_box,loandoclist,button_value,doccode,sysdate,help;
	int ac_no;
	private PageIdForm  pageidentity= new PageIdForm();
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		System.out.println();
		this.sysdate = sysdate;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public int getAc_no() {
		return ac_no;
	}

	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getCheck_box() {
		return check_box;
	}

	public void setCheck_box(String check_box) {
		this.check_box = check_box;
	}

	public String getLoandoclist() {
		return loandoclist;
	}

	public void setLoandoclist(String loandoclist) {
		this.loandoclist = loandoclist;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public String getDoccode() {
		return doccode;
	}

	public void setDoccode(String doccode) {
		this.doccode = doccode;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

}
