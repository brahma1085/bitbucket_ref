package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class SurityCoBorrower extends ActionForm {
   String ln_ac_type,type;
   int ln_acc_no,cid;
public String getLn_ac_type() {
	return ln_ac_type;  
}
public void setLn_ac_type(String ln_ac_type) {
	this.ln_ac_type = ln_ac_type;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public int getLn_acc_no() {
	return ln_acc_no;
}
public void setLn_acc_no(int ln_acc_no) {
	this.ln_acc_no = ln_acc_no;
}
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
}
