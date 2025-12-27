package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class LedgerPrintingForm extends ActionForm{
	String comboLedger,stAcno,endAcno,trn_dt,trn_dt_upto,pgno,pgName,open_dt,agno,agname,lnAvail,int_paid_upto,nomiNo,payMode,nomName,pageid,forward;
	String pygtype;
	String sysdate;
	
	
	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getComboLedger() {
		return comboLedger;
	}

	public void setComboLedger(String comboLedger) {
		this.comboLedger = comboLedger;
	}

	public String getStAcno() {
		return stAcno;
	}

	public void setStAcno(String stAcno) {
		this.stAcno = stAcno;
	}

	public String getEndAcno() {
		return endAcno;
	}

	public void setEndAcno(String endAcno) {
		this.endAcno = endAcno;
	}

	public String getTrn_dt() {
		return trn_dt;
	}

	public void setTrn_dt(String trn_dt) {
		this.trn_dt = trn_dt;
	}

	public String getTrn_dt_upto() {
		return trn_dt_upto;
	}

	public void setTrn_dt_upto(String trn_dt_upto) {
		this.trn_dt_upto = trn_dt_upto;
	}

	public String getPgno() {
		return pgno;
	}

	public void setPgno(String pgno) {
		this.pgno = pgno;
	}

	public String getOpen_dt() {
		return open_dt;
	}

	public void setOpen_dt(String open_dt) {
		this.open_dt = open_dt;
	}

	public String getAgno() {
		return agno;
	}

	public void setAgno(String agno) {
		this.agno = agno;
	}

	public String getLnAvail() {
		return lnAvail;
	}

	public void setLnAvail(String lnAvail) {
		this.lnAvail = lnAvail;
	}

	public String getInt_paid_upto() {
		return int_paid_upto;
	}

	public void setInt_paid_upto(String int_paid_upto) {
		this.int_paid_upto = int_paid_upto;
	}

	public String getNomiNo() {
		return nomiNo;
	}

	public void setNomiNo(String nomiNo) {
		this.nomiNo = nomiNo;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getNomName() {
		return nomName;
	}

	public void setNomName(String nomName) {
		this.nomName = nomName;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getAgname() {
		return agname;
	}

	public void setAgname(String agname) {
		this.agname = agname;
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}

	public String getPygtype() {
		return pygtype;
	}

	public void setPygtype(String pygtype) {
		this.pygtype = pygtype;
	}
}
