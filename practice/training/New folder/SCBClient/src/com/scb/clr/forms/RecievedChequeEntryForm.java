package com.scb.clr.forms;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
public class RecievedChequeEntryForm extends ActionForm {

	
	String send_to,source_name,return_type,pageId,bankname,branchname,cityname;
	String city_code,bank_code,micrcode,chqddpo,paydetail,branch_code;
	String reason_codes,error_message,today_date,bounceFine;
	String debit_account_type,debit_chq_date, debit_module,flag;
	
	int ctrl_no,form_flag,error_flag,booleanFlag,panelFlag,debit_account_no,debit_chequeno,ackno,credit_account_no;
	double shodowbalance,balance,amount,acknowledge_rem_amt;
    private	String  acknowledge_amt;
	long prev_ctrl_no;
	boolean bounce;
	String warrantDate,warrantNum,warrantBalance,warrantName,payorderNum,payorderDate,payorderBalance,payorderName;
	private String credebit;
	
	public String getCredebit() {
		return credebit;
	}
	public void setCredebit(String credebit) {
		this.credebit = credebit;
	}
	public String getWarrantDate() {
		return warrantDate;
	}
	public void setWarrantDate(String warrantDate) {
		this.warrantDate = warrantDate;
	}
	public String getWarrantNum() {
		return warrantNum;
	}
	public void setWarrantNum(String warrantNum) {
		this.warrantNum = warrantNum;
	}
	public String getWarrantBalance() {
		return warrantBalance;
	}
	public void setWarrantBalance(String warrantBalance) {
		this.warrantBalance = warrantBalance;
	}
	public String getWarrantName() {
		return warrantName;
	}
	public void setWarrantName(String warrantName) {
		this.warrantName = warrantName;
	}
	public String getPayorderNum() {
		return payorderNum;
	}
	public void setPayorderNum(String payorderNum) {
		this.payorderNum = payorderNum;
	}
	public String getPayorderDate() {
		return payorderDate;
	}
	public void setPayorderDate(String payorderDate) {
		this.payorderDate = payorderDate;
	}
	public String getPayorderBalance() {
		return payorderBalance;
	}
	public void setPayorderBalance(String payorderBalance) {
		this.payorderBalance = payorderBalance;
	}
	public String getPayorderName() {
		return payorderName;
	}
	public void setPayorderName(String payorderName) {
		this.payorderName = payorderName;
	}
	
	public boolean isBounce() {
		return bounce;
	}
	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}
	public String getSend_to() {
		return send_to;
	}
	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getReturn_type() {
		return return_type;
	}
	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}
	
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	
	public String getMicrcode() {
		return micrcode;
	}
	public void setMicrcode(String micrcode) {
		this.micrcode = micrcode;
	}
	
	
	public String getBank_code() {
		return bank_code;
	}
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	public void setPrev_ctrl_no(long prev_ctrl_no) {
		this.prev_ctrl_no = prev_ctrl_no;
	}
	public String getChqddpo() {
		return chqddpo;
	}
	public void setChqddpo(String chqddpo) {
		this.chqddpo = chqddpo;
	}
	
	public String getPaydetail() {
		return paydetail;
	}
	public void setPaydetail(String paydetail) {
		this.paydetail = paydetail;
	}
	
	public String getReason_codes() {
		return reason_codes;
	}
	public void setReason_codes(String reason_codes) {
		this.reason_codes = reason_codes;
	}
	
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getToday_date() {
		return today_date;
	}
	public void setToday_date(String today_date) {
		this.today_date = today_date;
	}
	
	public String getDebit_account_type() {
		return debit_account_type;
	}
	public void setDebit_account_type(String debit_account_type) {
		this.debit_account_type = debit_account_type;
	}
	
	public String getDebit_chq_date() {
		return debit_chq_date;
	}
	public void setDebit_chq_date(String debit_chq_date) {
		this.debit_chq_date = debit_chq_date;
	}
	
	public int getCtrl_no() {
		return ctrl_no;
	}
	public void setCtrl_no(int ctrl_no) {
		this.ctrl_no = ctrl_no;
	}
	public int getForm_flag() {
		return form_flag;
	}
	public void setForm_flag(int form_flag) {
		this.form_flag = form_flag;
	}
	public int getError_flag() {
		return error_flag;
	}
	public void setError_flag(int error_flag) {
		this.error_flag = error_flag;
	}
	public int getDebit_account_no() {
		return debit_account_no;
	}
	public void setDebit_account_no(int debit_account_no) {
		this.debit_account_no = debit_account_no;
	}
	public int getDebit_chequeno() {
		return debit_chequeno;
	}
	public void setDebit_chequeno(int debit_chequeno) {
		this.debit_chequeno = debit_chequeno;
	}
	public int getAckno() {
		return ackno;
	}
	public void setAckno(int ackno) {
		this.ackno = ackno;
	}
	public double getShodowbalance() {
		return shodowbalance;
	}
	public void setShodowbalance(double shodowbalance) {
		this.shodowbalance = shodowbalance;
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
	public double getAcknowledge_rem_amt() {
		return acknowledge_rem_amt;
	}
	public void setAcknowledge_rem_amt(double acknowledge_rem_amt) {
		this.acknowledge_rem_amt = acknowledge_rem_amt;
	}
	
	public String getAcknowledge_amt() {
		return acknowledge_amt;
	}
	public void setAcknowledge_amt(String acknowledge_amt) {
		this.acknowledge_amt = acknowledge_amt;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public long getPrev_ctrl_no() {
		return prev_ctrl_no;
	}
	public int getCredit_account_no() {
		return credit_account_no;
	}
	public void setCredit_account_no(int credit_account_no) {
		this.credit_account_no = credit_account_no;
	}
	public String getDebit_module() {
		return debit_module;
	}
	public void setDebit_module(String debit_module) {
		this.debit_module = debit_module;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getPanelFlag() {
		return panelFlag;
	}
	public void setPanelFlag(int panelFlag) {
		this.panelFlag = panelFlag;
	}
	public String getBounceFine() {
		return bounceFine;
	}
	public void setBounceFine(String bounceFine) {
		this.bounceFine = bounceFine;
	}
	public int getBooleanFlag() {
		return booleanFlag;
	}
	public void setBooleanFlag(int booleanFlag) {
		this.booleanFlag = booleanFlag;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
}
