package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanDefaulterForm extends ActionForm {
   private String remainder,acctype,stage_type,int_wise,pr_wise,forward;
   private int from_accno,to_accno,prn_fromamt,prn_toamt,int_fromamt,int_toamt,bal_fromamt,bal_toamt;
   
   private PageIdForm  pageidentity= new PageIdForm();
   
   
public String getRemainder() {
	return remainder;
}
public void setRemainder(String remainder) {
	this.remainder = remainder;
}

public String getAcctype() {
	return acctype;
}
public void setAcctype(String acctype) {
	this.acctype = acctype;
}
public String getStage_type() {
	return stage_type;
}
public void setStage_type(String stage_type) {
	this.stage_type = stage_type;
}
public int getFrom_accno() {
	return from_accno;
}
public void setFrom_accno(int from_accno) {
	this.from_accno = from_accno;
}
public int getTo_accno() {
	return to_accno;
}
public void setTo_accno(int to_accno) {
	this.to_accno = to_accno;
}
public int getPrn_fromamt() {
	return prn_fromamt;
}
public void setPrn_fromamt(int prn_fromamt) {
	this.prn_fromamt = prn_fromamt;
}
public int getPrn_toamt() {
	return prn_toamt;
}
public void setPrn_toamt(int prn_toamt) {
	this.prn_toamt = prn_toamt;
}
public int getInt_fromamt() {
	return int_fromamt;
}
public void setInt_fromamt(int int_fromamt) {
	this.int_fromamt = int_fromamt;
}
public int getInt_toamt() {
	return int_toamt;
}
public void setInt_toamt(int int_toamt) {
	this.int_toamt = int_toamt;
}
public int getBal_fromamt() {
	return bal_fromamt;
}
public void setBal_fromamt(int bal_fromamt) {
	this.bal_fromamt = bal_fromamt;
}
public int getBal_toamt() {
	return bal_toamt;
}
public void setBal_toamt(int bal_toamt) {
	this.bal_toamt = bal_toamt;
}
public String getInt_wise() {
	return int_wise;
}
public void setInt_wise(String int_wise) {
	this.int_wise = int_wise;
}
public String getPr_wise() {
	return pr_wise;
}
public void setPr_wise(String pr_wise) {
	this.pr_wise = pr_wise;
}
public PageIdForm getPageidentity() {
	return pageidentity;
}
public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
   }
