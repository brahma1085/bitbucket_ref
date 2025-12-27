package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class DCBSchedulingForm extends ActionForm{
	
	private String  txt_monthandyear,combo_year,combo_pay_actype,combo_actype,txt_fromacnum,txt_toaccnum,button_val,combo_Monthyear,button_value,DCBMainValue,delete_sucess,button_process,valid_dcbpro_futuredate,validate_dcbprocess,checkbox_prpo;
	private PageIdForm  pageidentity= new PageIdForm();
	
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTxt_monthandyear() {
		return txt_monthandyear;
	}

	public void setTxt_monthandyear(String txt_monthandyear) {
		this.txt_monthandyear = txt_monthandyear;
	}

	public String getCombo_year() {
		return combo_year;
	}

	public void setCombo_year(String combo_year) {
		this.combo_year = combo_year;
	}

	public String getCombo_pay_actype() {
		return combo_pay_actype;
	}

	public void setCombo_pay_actype(String combo_pay_actype) {
		this.combo_pay_actype = combo_pay_actype;
	}

	public String getCombo_actype() {
		return combo_actype;
	}

	public void setCombo_actype(String combo_actype) {
		this.combo_actype = combo_actype;
	}

	public String getTxt_fromacnum() {
		return txt_fromacnum;
	}

	public void setTxt_fromacnum(String txt_fromacnum) {
		this.txt_fromacnum = txt_fromacnum;
	}

	public String getTxt_toaccnum() {
		return txt_toaccnum;
	}

	public void setTxt_toaccnum(String txt_toaccnum) {
		this.txt_toaccnum = txt_toaccnum;
	}

	public String getButton_val() {
		return button_val;
	}

	public void setButton_val(String button_val) {
		this.button_val = button_val;
	}

	public String getCombo_Monthyear() {
		return combo_Monthyear;
	}

	public void setCombo_Monthyear(String combo_Monthyear) {
		this.combo_Monthyear = combo_Monthyear;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public String getDCBMainValue() {
		return DCBMainValue;
	}

	public void setDCBMainValue(String mainValue) {
		DCBMainValue = mainValue;
	}

	public String getDelete_sucess() {
		return delete_sucess;
	}

	public void setDelete_sucess(String delete_sucess) {
		this.delete_sucess = delete_sucess;
	}

	public String getButton_process() {
		return button_process;
	}

	public void setButton_process(String button_process) {
		this.button_process = button_process;
	}

	public String getValid_dcbpro_futuredate() {
		return valid_dcbpro_futuredate;
	}

	public void setValid_dcbpro_futuredate(String valid_dcbpro_futuredate) {
		this.valid_dcbpro_futuredate = valid_dcbpro_futuredate;
	}

	public String getValidate_dcbprocess() {
		return validate_dcbprocess;
	}

	public void setValidate_dcbprocess(String validate_dcbprocess) {
		this.validate_dcbprocess = validate_dcbprocess;
	}

	public String getCheckbox_prpo() {
		return checkbox_prpo;
	}

	public void setCheckbox_prpo(String checkbox_prpo) {
		this.checkbox_prpo = checkbox_prpo;
	}

	

}
