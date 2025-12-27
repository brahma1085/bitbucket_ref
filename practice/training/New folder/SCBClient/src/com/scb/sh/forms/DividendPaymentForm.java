package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class DividendPaymentForm extends ActionForm {
 private String pageId;
 private String voucher_no,actype,acnum,frm_dt,to_dt,pay_type,pay_ac_type,ac_no;
 private String forward;
 private String cashPayMode,cashAcType,cashAcno,flag;

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}

public String getCashPayMode() {
	return cashPayMode;
}

public void setCashPayMode(String cashPayMode) {
	this.cashPayMode = cashPayMode;
}

public String getCashAcType() {
	return cashAcType;
}

public void setCashAcType(String cashAcType) {
	this.cashAcType = cashAcType;
}

public String getCashAcno() {
	return cashAcno;
}

public void setCashAcno(String cashAcno) {
	this.cashAcno = cashAcno;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getVoucher_no() {
	return voucher_no;
}

public void setVoucher_no(String voucher_no) {
	this.voucher_no = voucher_no;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getAcnum() {
	return acnum;
}

public void setAcnum(String acnum) {
	this.acnum = acnum;
}

public String getFrm_dt() {
	return frm_dt;
}

public void setFrm_dt(String frm_dt) {
	this.frm_dt = frm_dt;
}

public String getTo_dt() {
	return to_dt;
}

public void setTo_dt(String to_dt) {
	this.to_dt = to_dt;
}

public String getPay_type() {
	return pay_type;
}

public void setPay_type(String pay_type) {
	this.pay_type = pay_type;
}

public String getPay_ac_type() {
	return pay_ac_type;
}

public void setPay_ac_type(String pay_ac_type) {
	this.pay_ac_type = pay_ac_type;
}

public String getAc_no() {
	return ac_no;
}

public void setAc_no(String ac_no) {
	this.ac_no = ac_no;
}
}
