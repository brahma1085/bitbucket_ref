package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class AdmPOModuleForm extends ActionForm{
	private String pageId;
	
	String acname,sname,lastacno,minbal,maxamount,chqvalid,lastpodeno;
	String newactype,ac,ad,wc,wd,codedesc,setv;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getAcname() {
		return acname;
	}
	public void setAcname(String acname) {
		this.acname = acname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getLastacno() {
		return lastacno;
	}
	public void setLastacno(String lastacno) {
		this.lastacno = lastacno;
	}
	public String getMinbal() {
		return minbal;
	}
	public void setMinbal(String minbal) {
		this.minbal = minbal;
	}
	public String getMaxamount() {
		return maxamount;
	}
	public void setMaxamount(String maxamount) {
		this.maxamount = maxamount;
	}
	public String getChqvalid() {
		return chqvalid;
	}
	public void setChqvalid(String chqvalid) {
		this.chqvalid = chqvalid;
	}
	public String getLastpodeno() {
		return lastpodeno;
	}
	public void setLastpodeno(String lastpodeno) {
		this.lastpodeno = lastpodeno;
	}
	public String getNewactype() {
		return newactype;
	}
	public void setNewactype(String newactype) {
		this.newactype = newactype;
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
	public String getCodedesc() {
		return codedesc;
	}
	public void setCodedesc(String codedesc) {
		this.codedesc = codedesc;
	}
	public String getSetv() {
		return setv;
	}
	public void setSetv(String setv) {
		this.setv = setv;
	}

}
