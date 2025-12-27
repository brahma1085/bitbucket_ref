package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class AdmCAModuleForm extends ActionForm{
	private String pageId;
	String ifdate,itdate,trnprmonth,lintpaydate,maxjoints,linpassbook,inopDays,chqvalid,minbalchqm,minbalclr;
	String minbal,lastacno,sname,acname,newactype,codedesc,ac,ad,wc,wd,setv;

	

	public String getPageId() {
		System.out.println("Getting PageId===>"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("Setting PageId===>"+pageId);
		this.pageId = pageId;
	}

	public String getIfdate() {
		return ifdate;
	}

	public void setIfdate(String ifdate) {
		this.ifdate = ifdate;
	}

	public String getItdate() {
		return itdate;
	}

	public void setItdate(String itdate) {
		this.itdate = itdate;
	}

	public String getTrnprmonth() {
		return trnprmonth;
	}

	public void setTrnprmonth(String trnprmonth) {
		this.trnprmonth = trnprmonth;
	}

	public String getLintpaydate() {
		return lintpaydate;
	}

	public void setLintpaydate(String lintpaydate) {
		this.lintpaydate = lintpaydate;
	}

	public String getMaxjoints() {
		return maxjoints;
	}

	public void setMaxjoints(String maxjoints) {
		this.maxjoints = maxjoints;
	}

	public String getLinpassbook() {
		return linpassbook;
	}

	public void setLinpassbook(String linpassbook) {
		this.linpassbook = linpassbook;
	}

	public String getInopDays() {
		return inopDays;
	}

	public void setInopDays(String inopDays) {
		this.inopDays = inopDays;
	}

	public String getChqvalid() {
		return chqvalid;
	}

	public void setChqvalid(String chqvalid) {
		this.chqvalid = chqvalid;
	}

	public String getMinbalchqm() {
		return minbalchqm;
	}

	public void setMinbalchqm(String minbalchqm) {
		this.minbalchqm = minbalchqm;
	}

	public String getMinbalclr() {
		return minbalclr;
	}

	public void setMinbalclr(String minbalclr) {
		this.minbalclr = minbalclr;
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

	public String getSetv() {
		return setv;
	}

	public void setSetv(String setv) {
		this.setv = setv;
	}

		
	}
