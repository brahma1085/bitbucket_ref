package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class VerifyFCChqWithdrawalForm extends ActionForm {

	String pageId,detailsCombo,acbalance,delet,setwith;
	String tokenno,chqslpo,acType,acno,chqslno,date,amount,payee,setv,hidval,subval,onhelp;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getTokenno() {
		return tokenno;
	}
	public void setTokenno(String tokenno) {
		this.tokenno = tokenno;
	}
	public String getChqslpo() {
		return chqslpo;
	}
	public void setChqslpo(String chqslpo) {
		this.chqslpo = chqslpo;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcno() {
		System.out.println("acno"+acno);
		return acno;
	}
	public void setAcno(String acno) {
		System.out.println("acno"+this.acno);
		this.acno = acno;
	}
	public String getChqslno() {
		return chqslno;
	}
	public void setChqslno(String chqslno) {
		this.chqslno = chqslno;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getSetv() {
		return setv;
	}
	public void setSetv(String setv) {
		this.setv = setv;
	}
	public String getHidval() {
		return hidval;
	}
	public void setHidval(String hidval) {
		this.hidval = hidval;
	}
	public String getSubval() {
		return subval;
	}
	public void setSubval(String subval) {
		this.subval = subval;
	}
	public String getDetailsCombo() {
		return detailsCombo;
	}
	public void setDetailsCombo(String detailsCombo) {
		this.detailsCombo = detailsCombo;
	}
	public String getAcbalance() {
		return acbalance;
	}
	public void setAcbalance(String acbalance) {
		this.acbalance = acbalance;
	}
	public String getDelet() {
		return delet;
	}
	public void setDelet(String delet) {
		this.delet = delet;
	}
	public String getSetwith() {
		return setwith;
	}
	public void setSetwith(String setwith) {
		this.setwith = setwith;
	}
	public String getOnhelp() {
		return onhelp;
	}
	public void setOnhelp(String onhelp) {
		this.onhelp = onhelp;
	}
}
