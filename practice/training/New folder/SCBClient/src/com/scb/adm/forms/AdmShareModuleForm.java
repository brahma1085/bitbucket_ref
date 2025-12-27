package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class AdmShareModuleForm extends ActionForm{
	private String pageId;
	String dvchno,ldivwarno,divcashno,trnno,divrefno,ltempshno,lshcertno,divcdate;
	String minbal,shDistno,lastacno,sname,acname,newactype,codedesc,ac,ad,wc,wd,setv;

	

	public String getPageId() {
		System.out.println("Getting PageId===>"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("Setting PageId===>"+pageId);
		this.pageId = pageId;
	}

	public String getDvchno() {
		return dvchno;
	}

	public void setDvchno(String dvchno) {
		this.dvchno = dvchno;
	}

	public String getLdivwarno() {
		return ldivwarno;
	}

	public void setLdivwarno(String ldivwarno) {
		this.ldivwarno = ldivwarno;
	}

	public String getDivcashno() {
		return divcashno;
	}

	public void setDivcashno(String divcashno) {
		this.divcashno = divcashno;
	}

	public String getTrnno() {
		return trnno;
	}

	public void setTrnno(String trnno) {
		this.trnno = trnno;
	}

	public String getDivrefno() {
		return divrefno;
	}

	public void setDivrefno(String divrefno) {
		this.divrefno = divrefno;
	}

	public String getLtempshno() {
		return ltempshno;
	}

	public void setLtempshno(String ltempshno) {
		this.ltempshno = ltempshno;
	}

	public String getLshcertno() {
		return lshcertno;
	}

	public void setLshcertno(String lshcertno) {
		this.lshcertno = lshcertno;
	}

	public String getDivcdate() {
		return divcdate;
	}

	public void setDivcdate(String divcdate) {
		this.divcdate = divcdate;
	}

	public String getMinbal() {
		return minbal;
	}

	public void setMinbal(String minbal) {
		this.minbal = minbal;
	}

	public String getShDistno() {
		return shDistno;
	}

	public void setShDistno(String shDistno) {
		this.shDistno = shDistno;
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
