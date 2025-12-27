package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class CCPerInspectForm extends ActionForm {
	private String ac_type,nat_of_bus,sanc_per,sanc_date,mat_date,sanc_limit,ac_status,prev_insp_date,prev_stck_value,prev_cr_limit,value_stock,cr_limit,next_insp_date;
	int ac_no;
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getNat_of_bus() {
		return nat_of_bus;
	}
	public void setNat_of_bus(String nat_of_bus) {
		this.nat_of_bus = nat_of_bus;
	}
	public String getSanc_per() {
		return sanc_per;
	}
	public void setSanc_per(String sanc_per) {
		this.sanc_per = sanc_per;
	}
	public String getSanc_date() {
		return sanc_date;
	}
	public void setSanc_date(String sanc_date) {
		this.sanc_date = sanc_date;
	}
	public String getMat_date() {
		return mat_date;
	}
	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}
	public String getSanc_limit() {
		return sanc_limit;
	}
	public void setSanc_limit(String sanc_limit) {
		this.sanc_limit = sanc_limit;
	}
	public String getAc_status() {
		return ac_status;
	}
	public void setAc_status(String ac_status) {
		this.ac_status = ac_status;
	}
	public String getPrev_insp_date() {
		return prev_insp_date;
	}
	public void setPrev_insp_date(String prev_insp_date) {
		this.prev_insp_date = prev_insp_date;
	}
	public String getPrev_stck_value() {
		return prev_stck_value;
	}
	public void setPrev_stck_value(String prev_stck_value) {
		this.prev_stck_value = prev_stck_value;
	}
	public String getPrev_cr_limit() {
		return prev_cr_limit;
	}
	public void setPrev_cr_limit(String prev_cr_limit) {
		this.prev_cr_limit = prev_cr_limit;
	}
	public String getValue_stock() {
		return value_stock;
	}
	public void setValue_stock(String value_stock) {
		this.value_stock = value_stock;
	}
	public String getCr_limit() {
		return cr_limit;
	}
	public void setCr_limit(String cr_limit) {
		this.cr_limit = cr_limit;
	}
	public String getNext_insp_date() {
		return next_insp_date;
	}
	public void setNext_insp_date(String next_insp_date) {
		this.next_insp_date = next_insp_date;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
}
