package com.scb.bk.forms;

import org.apache.struts.action.ActionForm;

public class PaymentVoucherForm extends ActionForm{

	private String forward,delete,sub,del,ver_button,ver;
	private String pageId,valid,deling;
	private double creditamt,debitamt,totalcreditamt,totaldebitamt,creditamt1;
	private String date,narration,txt_gltype,gltype,glcode,amount,gltype_first,glcode_first;
	private String gldesc,cdind;
	private int vchnum;
	int paym_vch;
    String scr_verify,update,updated;
	private String vouchervalue;
	
	
	
	public String getVer_button() {
		return ver_button;
	}
	public void setVer_button(String ver_button) {
		this.ver_button = ver_button;
	}
	public int getPaym_vch() {
		return paym_vch;
	}
	public void setPaym_vch(int paym_vch) {
		this.paym_vch = paym_vch;
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
	
	public int getVchnum() {
		return vchnum;
	}
	public void setVchnum(int vchnum) {
		this.vchnum = vchnum;
	}
	public String getVouchervalue() {
		return vouchervalue;
	}
	public void setVouchervalue(String vouchervalue) {
		this.vouchervalue = vouchervalue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getCreditamt() {
		return creditamt;
	}
	public void setCreditamt(double creditamt) {
		this.creditamt = creditamt;
	}
	public double getDebitamt() {
		return debitamt;
	}
	public void setDebitamt(double debitamt) {
		this.debitamt = debitamt;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	

	public double getTotalcreditamt() {
		return totalcreditamt;
	}
	public void setTotalcreditamt(double totalcreditamt) {
		this.totalcreditamt = totalcreditamt;
	}
	public double getTotaldebitamt() {
		return totaldebitamt;
	}
	public void setTotaldebitamt(double totaldebitamt) {
		this.totaldebitamt = totaldebitamt;
	}
	public String getGltype() {
		return gltype;
	}
	public void setGltype(String gltype) {
		this.gltype = gltype;
	}
	public String getGlcode() {
		return glcode;
	}
	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}
	public String getGldesc() {
		return gldesc;
	}
	public void setGldesc(String gldesc) {
		this.gldesc = gldesc;
	}
	public String getCdind() {
		return cdind;
	}
	public void setCdind(String cdind) {
		this.cdind = cdind;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTxt_gltype() {
		return txt_gltype;
	}
	public void setTxt_gltype(String txt_gltype) {
		this.txt_gltype = txt_gltype;
	}
	public String getGltype_first() {
		return gltype_first;
	}
	public void setGltype_first(String gltype_first) {
		this.gltype_first = gltype_first;
	}
	public String getGlcode_first() {
		return glcode_first;
	}
	public void setGlcode_first(String glcode_first) {
		this.glcode_first = glcode_first;
	}
	public double getCreditamt1() {
		return creditamt1;
	}
	public void setCreditamt1(double creditamt1) {
		this.creditamt1 = creditamt1;
	}
	public String getScr_verify() {
		return scr_verify;
	}
	public void setScr_verify(String scr_verify) {
		this.scr_verify = scr_verify;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getDeling() {
		return deling;
	}
	public void setDeling(String deling) {
		this.deling = deling;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	
}
