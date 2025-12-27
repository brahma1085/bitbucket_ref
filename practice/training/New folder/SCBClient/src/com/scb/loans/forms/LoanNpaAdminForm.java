package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanNpaAdminForm extends ActionForm
{
	private String txt_acctype,txt_tab180,txt_tab90,txt_from,txt_fromdaymonth,txt_to,txt_todaymonth,txt_provamt;
	private String txt_from1,txt_fromdaymonth1,txt_to1,txt_todaymonth1,txt_provamt1,txt_from2,txt_fromdaymonth2,txt_to2,txt_todaymonth2,txt_provamt2;
	private String txt_from3,txt_fromdaymonth3,txt_to3,txt_todaymonth3,txt_provamt3,txt_todaymonth4,txt_fromdaymonth4,txt_to4,but_val;
	private String txt_from4,txt_provamt4,valid_values;
	private String txt_standard,txt_substandard,txt_default1,txt_default2,txt_default3;
	private String npa_res;
	private PageIdForm  pageidentity= new PageIdForm();

	public String getTxt_acctype() {
		return txt_acctype;
	}

	public void setTxt_acctype(String txt_acctype) {
		this.txt_acctype = txt_acctype;
	}

	public String getTxt_tab180() {
		return txt_tab180;
	}

	public void setTxt_tab180(String txt_tab180) {
		this.txt_tab180 = txt_tab180;
	}

	public String getTxt_tab90() {
		return txt_tab90;
	}

	public void setTxt_tab90(String txt_tab90) {
		this.txt_tab90 = txt_tab90;
	}

	public String getTxt_from() {
		return txt_from;
	}

	public void setTxt_from(String txt_from) {
		this.txt_from = txt_from;
	}

	public String getTxt_fromdaymonth() {
		return txt_fromdaymonth;
	}

	public void setTxt_fromdaymonth(String txt_fromdaymonth) {
		this.txt_fromdaymonth = txt_fromdaymonth;
	}

	public String getTxt_to() {
		return txt_to;
	}

	public void setTxt_to(String txt_to) {
		this.txt_to = txt_to;
	}

	public String getTxt_todaymonth() {
		return txt_todaymonth;
	}

	public void setTxt_todaymonth(String txt_todaymonth) {
		this.txt_todaymonth = txt_todaymonth;
	}

	public String getTxt_provamt() {
		return txt_provamt;
	}

	public void setTxt_provamt(String txt_provamt) {
		this.txt_provamt = txt_provamt;
	}

	public String getTxt_from1() {
		return txt_from1;
	}

	public void setTxt_from1(String txt_from1) {
		this.txt_from1 = txt_from1;
	}

	public String getTxt_fromdaymonth1() {
		return txt_fromdaymonth1;
	}

	public void setTxt_fromdaymonth1(String txt_fromdaymonth1) {
		this.txt_fromdaymonth1 = txt_fromdaymonth1;
	}

	public String getTxt_to1() {
		return txt_to1;
	}

	public void setTxt_to1(String txt_to1) {
		this.txt_to1 = txt_to1;
	}

	public String getTxt_todaymonth1() {
		return txt_todaymonth1;
	}

	public void setTxt_todaymonth1(String txt_todaymonth1) {
		this.txt_todaymonth1 = txt_todaymonth1;
	}

	public String getTxt_provamt1() {
		return txt_provamt1;
	}

	public void setTxt_provamt1(String txt_provamt1) {
		this.txt_provamt1 = txt_provamt1;
	}

	public String getTxt_from2() {
		return txt_from2;
	}

	public void setTxt_from2(String txt_from2) {
		this.txt_from2 = txt_from2;
	}

	public String getTxt_fromdaymonth2() {
		return txt_fromdaymonth2;
	}

	public void setTxt_fromdaymonth2(String txt_fromdaymonth2) {
		this.txt_fromdaymonth2 = txt_fromdaymonth2;
	}

	public String getTxt_to2() {
		return txt_to2;
	}

	public void setTxt_to2(String txt_to2) {
		this.txt_to2 = txt_to2;
	}

	public String getTxt_todaymonth2() {
		return txt_todaymonth2;
	}

	public void setTxt_todaymonth2(String txt_todaymonth2) {
		this.txt_todaymonth2 = txt_todaymonth2;
	}

	public String getTxt_provamt2() {
		return txt_provamt2;
	}

	public void setTxt_provamt2(String txt_provamt2) {
		this.txt_provamt2 = txt_provamt2;
	}

	public String getTxt_from3() {
		return txt_from3;
	}

	public void setTxt_from3(String txt_from3) {
		this.txt_from3 = txt_from3;
	}

	public String getTxt_fromdaymonth3() {
		return txt_fromdaymonth3;
	}

	public void setTxt_fromdaymonth3(String txt_fromdaymonth3) {
		this.txt_fromdaymonth3 = txt_fromdaymonth3;
	}

	public String getTxt_to3() {
		return txt_to3;
	}

	public void setTxt_to3(String txt_to3) {
		this.txt_to3 = txt_to3;
	}

	public String getTxt_todaymonth3() {
		return txt_todaymonth3;
	}

	public void setTxt_todaymonth3(String txt_todaymonth3) {
		this.txt_todaymonth3 = txt_todaymonth3;
	}

	public String getTxt_provamt3() {
		return txt_provamt3;
	}

	public void setTxt_provamt3(String txt_provamt3) {
		this.txt_provamt3 = txt_provamt3;
	}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_todaymonth4() {
		return txt_todaymonth4;
	}

	public void setTxt_todaymonth4(String txt_todaymonth4) {
		this.txt_todaymonth4 = txt_todaymonth4;
	}

	public String getTxt_fromdaymonth4() {
		return txt_fromdaymonth4;
	}

	public void setTxt_fromdaymonth4(String txt_fromdaymonth4) {
		this.txt_fromdaymonth4 = txt_fromdaymonth4;
	}

	

	public String getTxt_to4() {
		return txt_to4;
	}

	public void setTxt_to4(String txt_to4) {
		this.txt_to4 = txt_to4;
	}

	public String getBut_val() {
		return but_val;
	}

	public void setBut_val(String but_val) {
		this.but_val = but_val;
	}

	public String getTxt_from4() {
		return txt_from4;
	}

	public void setTxt_from4(String txt_from4) {
		this.txt_from4 = txt_from4;
	}

	public String getTxt_provamt4() {
		return txt_provamt4;
	}

	public void setTxt_provamt4(String txt_provamt4) {
		this.txt_provamt4 = txt_provamt4;
	}

	public String getTxt_standard() {
		return txt_standard;
	}

	public void setTxt_standard(String txt_standard) {
		this.txt_standard = txt_standard;
	}

	public String getTxt_substandard() {
		return txt_substandard;
	}

	public void setTxt_substandard(String txt_substandard) {
		this.txt_substandard = txt_substandard;
	}

	public String getTxt_default1() {
		return txt_default1;
	}

	public void setTxt_default1(String txt_default1) {
		this.txt_default1 = txt_default1;
	}

	public String getTxt_default2() {
		return txt_default2;
	}

	public void setTxt_default2(String txt_default2) {
		this.txt_default2 = txt_default2;
	}

	public String getTxt_default3() {
		return txt_default3;
	}

	public void setTxt_default3(String txt_default3) {
		this.txt_default3 = txt_default3;
	}

	public String getValid_values() {
		return valid_values;
	}

	public void setValid_values(String valid_values) {
		this.valid_values = valid_values;
	}

	public String getNpa_res() {
		return npa_res;
	}

	public void setNpa_res(String npa_res) {
		this.npa_res = npa_res;
	}
}
