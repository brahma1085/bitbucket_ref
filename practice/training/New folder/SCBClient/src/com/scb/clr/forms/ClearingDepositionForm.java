package com.scb.clr.forms;

import masterObject.clearing.ClearingObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

public class ClearingDepositionForm extends ActionForm {

	
	private String credit_account_type, debit_account_type,branchname,bankname,pageId,chequedate;
	
	private int credit_account_no,debit_account_no,ctrl_no,form_flag, laon_credit_ac_type,laon_credit_no;
	
	private String multicredit ,checkbox,laon_credit,company_name,paydetail,bounce,discount, micrcode;
	
	private String chqddpo, internal_clearing, outstation_chq, credit_acc_type_name, error_message  ;
	
	private int chequeno,debit_chequeno,error_flag,booleanFlag;
	
	private String city_code, bank_code, branch_code,cname,cityname; 
	
	private double amount,pocommision,balance,discountamount,discountcharge,shodowbalance;
	
	private String clr_obj;
	
	private String submits,debit_chq_date ,today_date, reason_codes; 
	
	private int ackno,prev_ctrl_no,krishnaName;
	
	private String return_type,source_name,send_to;
	
	private double acknowledge_amt, acknowledge_rem_amt; 
	
	private String clearingdate,clearing_no,loan_acc_name;
	
	private String po_favour,po_date,bouCheck,bounceFine;
	
	private int pay_order_no;
	
	private double pay_order_amt;
	
	
	
	public String getPo_favour() {
		return po_favour;
	}
	public void setPo_favour(String po_favour) {
		this.po_favour = po_favour;
	}
	public String getPo_date() {
		return po_date;
	}
	public void setPo_date(String po_date) {
		this.po_date = po_date;
	}
	public int getPay_order_no() {
		return pay_order_no;
	}
	public void setPay_order_no(int pay_order_no) {
		this.pay_order_no = pay_order_no;
	}
	public double getPay_order_amt() {
		return pay_order_amt;
	}
	public void setPay_order_amt(double pay_order_amt) {
		this.pay_order_amt = pay_order_amt;
	}
	public String getClearingdate() {
		return clearingdate;
	}
	public void setClearingdate(String clearingdate) {
		this.clearingdate = clearingdate;
	}
	public String getClearing_no() {
		return clearing_no;
	}
	public void setClearing_no(String clearing_no) {
		this.clearing_no = clearing_no;
	}
	public double getAcknowledge_amt() {
		return acknowledge_amt;
	}
	public void setAcknowledge_amt(double acknowledge_amt) {
		this.acknowledge_amt = acknowledge_amt;
	}
	public double getAcknowledge_rem_amt() {
		return acknowledge_rem_amt;
	}
	public void setAcknowledge_rem_amt(double acknowledge_rem_amt) {
		this.acknowledge_rem_amt = acknowledge_rem_amt;
	}
	public String getSubmits() {
		return submits;
	}
	public void setSubmits(String submits) {
		this.submits = submits;
	}
	public String getCredit_account_type() {
		return credit_account_type;
	}
	public void setCredit_account_type(String credit_account_type) {
		this.credit_account_type = credit_account_type;
	}
	public String getDebit_account_type() {
		return debit_account_type;
	}
	public void setDebit_account_type(String debit_account_type) {
		this.debit_account_type = debit_account_type;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getChequedate() {
		return chequedate;
	}
	public void setChequedate(String chequedate) {
		this.chequedate = chequedate;
	}
	public int getCredit_account_no() {
		return credit_account_no;
	}
	public void setCredit_account_no(int credit_account_no) {
		this.credit_account_no = credit_account_no;
	}
	public int getDebit_account_no() {
		return debit_account_no;
	}
	public void setDebit_account_no(int debit_account_no) {
		this.debit_account_no = debit_account_no;
	}
	public int getCtrl_no() {
		return ctrl_no;
	}
	public void setCtrl_no(int ctrl_no) {
		this.ctrl_no = ctrl_no;
	}
	
	
	
	public String getMulticredit() {
		return multicredit;
	}
	public void setMulticredit(String multicredit) {
		this.multicredit = multicredit;
	}
	public String getLaon_credit() {
		return laon_credit;
	}
	public void setLaon_credit(String laon_credit) {
		this.laon_credit = laon_credit;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String compan_yname) {
		this.company_name = compan_yname;
	}
	public String getPaydetail() {
		return paydetail;
	}
	public void setPaydetail(String paydetail) {
		this.paydetail = paydetail;
	}
	public String getBounce() {
		return bounce;
	}
	public void setBounce(String bounce) {
		this.bounce = bounce;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getMicrcode() {
		return micrcode;
	}
	public void setMicrcode(String micrcode) {
		this.micrcode = micrcode;
	}
	public int getChequeno() {
		return chequeno;
	}
	public void setChequeno(int chequeno) {
		this.chequeno = chequeno;
	}
	public int getDebit_chequeno() {
		return debit_chequeno;
	}
	public void setDebit_chequeno(int debit_chequeno) {
		this.debit_chequeno = debit_chequeno;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPocommision() {
		return pocommision;
	}
	public void setPocommision(double pocommision) {
		this.pocommision = pocommision;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getDiscountamount() {
		return discountamount;
	}
	public void setDiscountamount(double discountamount) {
		this.discountamount = discountamount;
	}
	public double getDiscountcharge() {
		return discountcharge;
	}
	public void setDiscountcharge(double discountcharge) {
		this.discountcharge = discountcharge;
	}
	public double getShodowbalance() {
		return shodowbalance;
	}
	public void setShodowbalance(double shodowbalance) {
		this.shodowbalance = shodowbalance;
	}
	public int getLaon_credit_no() {
		return laon_credit_no;
	}
	public void setLaon_credit_no(int laon_credit_no) {
		this.laon_credit_no = laon_credit_no;
	}
	public String getChqddpo() {
		return chqddpo;
	}
	public void setChqddpo(String chqddpo) {
		this.chqddpo = chqddpo;
	}
	public String getInternal_clearing() {
		return internal_clearing;
	}
	public void setInternal_clearing(String internal_clearing) {
		this.internal_clearing = internal_clearing;
	}
	public String getOutstation_chq() {
		return outstation_chq;
	}
	public void setOutstation_chq(String outstation_chq) {
		this.outstation_chq = outstation_chq;
	}
	public String getCredit_acc_type_name() {
		return credit_acc_type_name;
	}
	public void setCredit_acc_type_name(String credit_acc_type_name) {
		this.credit_acc_type_name = credit_acc_type_name;
	}
	public int getError_flag() {
		return error_flag;
	}
	public void setError_flag(int error_flag) {
		this.error_flag = error_flag;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getDebit_chq_date() {
		return debit_chq_date;
	}
	public void setDebit_chq_date(String debit_chq_date) {
		this.debit_chq_date = debit_chq_date;
	}
	public int getForm_flag() {
		return form_flag;
	}
	public void setForm_flag(int form_flag) {
		this.form_flag = form_flag;
	}
	public int getLaon_credit_ac_type() {
		return laon_credit_ac_type;
	}
	public void setLaon_credit_ac_type(int laon_credit_ac_type) {
		this.laon_credit_ac_type = laon_credit_ac_type;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
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
	public String getToday_date() {
		return today_date;
	}
	public void setToday_date(String today_date) {
		this.today_date = today_date;
	}
	
	public String getClr_obj() {
		return clr_obj;
	}
	public void setClr_obj(String clr_obj) {
		this.clr_obj = clr_obj;
	}
	public String getReason_codes() {
		return reason_codes;
	}
	public void setReason_codes(String reason_codes) {
		this.reason_codes = reason_codes;
	}
	public int getAckno() {
		return ackno;
	}
	public void setAckno(int ackno) {
		this.ackno = ackno;
	}
	
	public String getSend_to() {
		return send_to;
	}
	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}
	public String getReturn_type() {
		return return_type;
	}
	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public int getPrev_ctrl_no() {
		return prev_ctrl_no;
	}
	public void setPrev_ctrl_no(int prev_ctrl_no) {
		this.prev_ctrl_no = prev_ctrl_no;
	}
	public String getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}
	public String getBouCheck() {
		return bouCheck;
	}
	public void setBouCheck(String bouCheck) {
		this.bouCheck = bouCheck;
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
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getLoan_acc_name() {
		return loan_acc_name;
	}
	public void setLoan_acc_name(String loan_acc_name) {
		this.loan_acc_name = loan_acc_name;
	}
	public int getKrishnaName() {
		return krishnaName;
	}
	public void setKrishnaName(int krishnaName) {
		this.krishnaName = krishnaName;
	}
	
	
	
	


}
