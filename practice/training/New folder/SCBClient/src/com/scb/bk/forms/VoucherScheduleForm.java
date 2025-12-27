package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class VoucherScheduleForm extends ActionForm {
	
  private String fromdate,todate,voucher_type;
  private String forward,pageId,valid,accountno;
  String sysdate;


  public String getSysdate() {
  		return sysdate;
  	}


  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
  	}


public String getFromdate() {
	return fromdate;
}
public void setFromdate(String fromdate) {
	this.fromdate = fromdate;
}
public String getTodate() {
	return todate;
}
public void setTodate(String todate) {
	this.todate = todate;
}
public String getVoucher_type() {
	return voucher_type;
}
public void setVoucher_type(String voucher_type) {
	this.voucher_type = voucher_type;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}
public String getAccountno() {
	return accountno;
}
public void setAccountno(String accountno) {
	this.accountno = accountno;
}
}