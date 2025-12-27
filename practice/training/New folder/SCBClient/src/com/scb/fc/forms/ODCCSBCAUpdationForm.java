package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ODCCSBCAUpdationForm extends ActionForm
{
	String pageId,acno,purpose,srlno,appdate,loanamount,paymode,intType,intcalc,acType;
    String updt,detail,addco,coacType,coacno,addjoint,jointcid,jointname,signinst;
    String sysdate;
   
    public String getSysdate() {
		return sysdate;
	}
	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}	
    public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAcno() {
		return acno;
	}

	public void setAcno(String acno) {
		this.acno = acno;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSrlno() {
		return srlno;
	}

	public void setSrlno(String srlno) {
		this.srlno = srlno;
	}

	public String getAppdate() {
		return appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getIntcalc() {
		return intcalc;
	}

	public void setIntcalc(String intcalc) {
		this.intcalc = intcalc;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getUpdt() {
		return updt;
	}

	public void setUpdt(String updt) {
		this.updt = updt;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAddco() {
		return addco;
	}

	public void setAddco(String addco) {
		this.addco = addco;
	}

	public String getCoacType() {
		return coacType;
	}

	public void setCoacType(String coacType) {
		this.coacType = coacType;
	}

	public String getCoacno() {
		return coacno;
	}

	public void setCoacno(String coacno) {
		this.coacno = coacno;
	}

	public String getAddjoint() {
		return addjoint;
	}

	public void setAddjoint(String addjoint) {
		this.addjoint = addjoint;
	}

	public String getJointcid() {
		return jointcid;
	}

	public void setJointcid(String jointcid) {
		this.jointcid = jointcid;
	}

	public String getJointname() {
		return jointname;
	}

	public void setJointname(String jointname) {
		this.jointname = jointname;
	}

	public String getSigninst() {
		return signinst;
	}

	public void setSigninst(String signinst) {
		this.signinst = signinst;
	}
}
