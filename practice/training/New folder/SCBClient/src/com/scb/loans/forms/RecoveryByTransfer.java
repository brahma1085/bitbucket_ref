package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class RecoveryByTransfer extends ActionForm {
   int trf_voucherno,trf_accno;
   String trf_from,intuptodate,acc_notfound;
   double balance,amount;
   double advance,principle,interest,penalinterest,othercharges,extraint;
   
   
   
public int getTrf_voucherno() {
	return trf_voucherno;
}
public void setTrf_voucherno(int trf_voucherno) {
	this.trf_voucherno = trf_voucherno;
}
public int getTrf_accno() {
	return trf_accno;
}
public void setTrf_accno(int trf_accno) {
	this.trf_accno = trf_accno;
}
public String getTrf_from() {
	return trf_from;
}
public void setTrf_from(String trf_from) {
	this.trf_from = trf_from;
}
public String getIntuptodate() {
	return intuptodate;
}
public void setIntuptodate(String intuptodate) {
	this.intuptodate = intuptodate;
}
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public double getAdvance() {
	return advance;
}
public void setAdvance(double advance) {
	this.advance = advance;
}
public double getPrinciple() {
	return principle;
}
public void setPrinciple(double principle) {
	this.principle = principle;
}
public double getInterest() {
	return interest;
}
public void setInterest(double interest) {
	this.interest = interest;
}
public double getPenalinterest() {
	return penalinterest;
}
public void setPenalinterest(double penalinterest) {
	this.penalinterest = penalinterest;
}
public double getOthercharges() {
	return othercharges;
}
public void setOthercharges(double othercharges) {
	this.othercharges = othercharges;
}
public double getExtraint() {
	return extraint;
}
public void setExtraint(double extraint) {
	this.extraint = extraint;
}
public String getAcc_notfound() {
	return acc_notfound;
}
public void setAcc_notfound(String acc_notfound) {
	this.acc_notfound = acc_notfound;
}
}
