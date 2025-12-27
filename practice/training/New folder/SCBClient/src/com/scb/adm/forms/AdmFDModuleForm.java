package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class AdmFDModuleForm extends ActionForm{
	private String pageId;
	String maxrenewal,mindepamount,minperiod,maxloanP,maxdepamount,maxperiod,maxjoints,prematrate,lastreceipt,intcalvchno;
	String minbal,lastacno,sname,acname,newactype,codedesc,ac,ad,wc,wd,setv;

	

	public String getPageId() {
		System.out.println("Getting PageId===>"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("Setting PageId===>"+pageId);
		this.pageId = pageId;
	}



	public String getMinbal() {
		return minbal;
	}

	public void setMinbal(String minbal) {
		this.minbal = minbal;
	}

	public String getLastacno() {
		return lastacno;
	}

	public void setLastacno(String lastacno) {
		this.lastacno = lastacno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAcname() {
		return acname;
	}

	public void setAcname(String acname) {
		this.acname = acname;
	}

	public String getNewactype() {
		return newactype;
	}

	public void setNewactype(String newactype) {
		this.newactype = newactype;
	}

	public String getCodedesc() {
		return codedesc;
	}

	public void setCodedesc(String codedesc) {
		this.codedesc = codedesc;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getWc() {
		return wc;
	}

	public void setWc(String wc) {
		this.wc = wc;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getMaxrenewal() {
		return maxrenewal;
	}

	public void setMaxrenewal(String maxrenewal) {
		this.maxrenewal = maxrenewal;
	}

	public String getMindepamount() {
		return mindepamount;
	}

	public void setMindepamount(String mindepamount) {
		this.mindepamount = mindepamount;
	}

	public String getMinperiod() {
		return minperiod;
	}

	public void setMinperiod(String minperiod) {
		this.minperiod = minperiod;
	}

	public String getMaxloanP() {
		return maxloanP;
	}

	public void setMaxloanP(String maxloanP) {
		this.maxloanP = maxloanP;
	}

	public String getMaxdepamount() {
		return maxdepamount;
	}

	public void setMaxdepamount(String maxdepamount) {
		this.maxdepamount = maxdepamount;
	}

	public String getMaxperiod() {
		return maxperiod;
	}

	public void setMaxperiod(String maxperiod) {
		this.maxperiod = maxperiod;
	}

	public String getMaxjoints() {
		return maxjoints;
	}

	public void setMaxjoints(String maxjoints) {
		this.maxjoints = maxjoints;
	}

	public String getPrematrate() {
		return prematrate;
	}

	public void setPrematrate(String prematrate) {
		this.prematrate = prematrate;
	}

	public String getLastreceipt() {
		return lastreceipt;
	}

	public void setLastreceipt(String lastreceipt) {
		this.lastreceipt = lastreceipt;
	}

	public String getIntcalvchno() {
		return intcalvchno;
	}

	public void setIntcalvchno(String intcalvchno) {
		this.intcalvchno = intcalvchno;
	}

	public String getSetv() {
		return setv;
	}

	public void setSetv(String setv) {
		this.setv = setv;
	}

		
	}
