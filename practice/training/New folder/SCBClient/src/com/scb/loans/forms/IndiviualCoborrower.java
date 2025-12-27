package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class IndiviualCoborrower extends ActionForm {
    String shareno,surityname,brname;
    double value;
    int brcode;
    
    String forward,coshtype,coshno;
    private PageIdForm  pageidentity= new PageIdForm();
    
    public String getCoshtype() {
		return coshtype;
	}
	public void setCoshtype(String coshtype) {
		this.coshtype = coshtype;
	}
	public String getCoshno() {
		return coshno;
	}
	public void setCoshno(String coshno) {
		this.coshno = coshno;
	}
    
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	public String getShareno() {
		return shareno;
	}
	public void setShareno(String shareno) {
		this.shareno = shareno;
	}
	public String getSurityname() {
		return surityname;
	}
	public void setSurityname(String surityname) {
		this.surityname = surityname;
	}
	public String getBrname() {
		return brname;
	}
	public void setBrname(String brname) {
		this.brname = brname;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getBrcode() {
		return brcode;
	}
	public void setBrcode(int brcode) {
		this.brcode = brcode;
	}
}
