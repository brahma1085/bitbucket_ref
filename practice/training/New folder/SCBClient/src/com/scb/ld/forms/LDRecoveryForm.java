package com.scb.ld.forms;

import org.apache.struts.action.ActionForm;

public class LDRecoveryForm extends ActionForm {
	private String combo_loantype,txt_sancamt,txt_sancdate,txt_sancpur,txt_accstatus,txt_depacnum,txt_loandesc,pageId,value;
    private String txt_dep_date,txt_mat_date,txt_Int_freq,txt_int_upto_date,txt_cash,txt_Mat_amt,txt_receipt_no,defaultTab,tabPaneHeading;
    private int txt_accno,txt_voucherno;
    private double txt_dep_amt;
    
    //variables used in cash form
    
    private String txt_acctype,txt_acNum,txt_name,txt_acdesc,txt_prin,txt_paid,txt_intrate,txt_totalamm;
	private double txt_ammount,txt_interest;
	
	// variables used in transfer form
	
	private String txt_ac,txt_tranfrom,txt_custname,button;
    private int txt_tracNum;
    double txt_bal,txt_entAmo;
    
    
    LDCashForm cashform=new LDCashForm();
    
	public LDCashForm getCashform() {
		return cashform;
	}

	public void setCashform(LDCashForm cashform) {
		this.cashform = cashform;
	}

	public String getCombo_loantype() {
		return combo_loantype;
	}

	public void setCombo_loantype(String combo_loantype) {
		this.combo_loantype = combo_loantype;
	}
	public int getTxt_accno() {
		return txt_accno;
	}

	public void setTxt_accno(int txt_accno) {
		this.txt_accno = txt_accno;
	}

	public String getTxt_sancamt() {
		return txt_sancamt;
	}

	public void setTxt_sancamt(String txt_sancamt) {
		this.txt_sancamt = txt_sancamt;
	}

	public String getTxt_sancdate() {
		return txt_sancdate;
	}

	public void setTxt_sancdate(String txt_sancdate) {
		this.txt_sancdate = txt_sancdate;
	}

	public String getTxt_sancpur() {
		return txt_sancpur;
	}

	public void setTxt_sancpur(String txt_sancpur) {
		this.txt_sancpur = txt_sancpur;
	}

	public String getTxt_accstatus() {
		return txt_accstatus;
	}

	public void setTxt_accstatus(String txt_accstatus) {
		this.txt_accstatus = txt_accstatus;
	}

	public String getTxt_depacnum() {
		return txt_depacnum;
	}

	public void setTxt_depacnum(String txt_depacnum) {
		this.txt_depacnum = txt_depacnum;
	}

	public String getTxt_dep_date() {
		return txt_dep_date;
	}

	public void setTxt_dep_date(String txt_dep_date) {
		this.txt_dep_date = txt_dep_date;
	}

	public String getTxt_mat_date() {
		return txt_mat_date;
	}

	public void setTxt_mat_date(String txt_mat_date) {
		this.txt_mat_date = txt_mat_date;
	}
	public double getTxt_dep_amt() {
		return txt_dep_amt;
	}

	public void setTxt_dep_amt(double txt_dep_amt) {
		this.txt_dep_amt = txt_dep_amt;
	}

	public String getTxt_Int_freq() {
		return txt_Int_freq;
	}

	public void setTxt_Int_freq(String txt_Int_freq) {
		this.txt_Int_freq = txt_Int_freq;
	}

	public String getTxt_int_upto_date() {
		return txt_int_upto_date;
	}

	public void setTxt_int_upto_date(String txt_int_upto_date) {
		this.txt_int_upto_date = txt_int_upto_date;
	}

	public String getTxt_cash() {
		return txt_cash;
	}

	public void setTxt_cash(String txt_cash) {
		this.txt_cash = txt_cash;
	}

	public String getTxt_Mat_amt() {
		return txt_Mat_amt;
	}

	public void setTxt_Mat_amt(String txt_Mat_amt) {
		this.txt_Mat_amt = txt_Mat_amt;
	}

	public String getTxt_receipt_no() {
		return txt_receipt_no;
	}

	public void setTxt_receipt_no(String txt_receipt_no) {
		this.txt_receipt_no = txt_receipt_no;
	}

	public String getTxt_loandesc() {
		return txt_loandesc;
	}

	public void setTxt_loandesc(String txt_loandesc) {
		this.txt_loandesc = txt_loandesc;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getTabPaneHeading() {
		return tabPaneHeading;
	}

	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}

	public String getTxt_acctype() {
		return txt_acctype;
	}

	public void setTxt_acctype(String txt_acctype) {
		this.txt_acctype = txt_acctype;
	}

	public String getTxt_acNum() {
		return txt_acNum;
	}

	public void setTxt_acNum(String txt_acNum) {
		this.txt_acNum = txt_acNum;
	}

	public String getTxt_name() {
		return txt_name;
	}

	public void setTxt_name(String txt_name) {
		this.txt_name = txt_name;
	}

	public String getTxt_acdesc() {
		return txt_acdesc;
	}

	public void setTxt_acdesc(String txt_acdesc) {
		this.txt_acdesc = txt_acdesc;
	}

	
	public double getTxt_interest() {
		return txt_interest;
	}

	public void setTxt_interest(double txtInterest) {
		txt_interest = txtInterest;
	}

	public String getTxt_prin() {
		return txt_prin;
	}

	public void setTxt_prin(String txt_prin) {
		this.txt_prin = txt_prin;
	}

	public String getTxt_paid() {
		return txt_paid;
	}

	public void setTxt_paid(String txt_paid) {
		this.txt_paid = txt_paid;
	}

	public String getTxt_intrate() {
		return txt_intrate;
	}

	public void setTxt_intrate(String txt_intrate) {
		this.txt_intrate = txt_intrate;
	}

	public String getTxt_totalamm() {
		return txt_totalamm;
	}

	public void setTxt_totalamm(String txt_totalamm) {
		this.txt_totalamm = txt_totalamm;
	}

	public double getTxt_ammount() {
		return txt_ammount;
	}

	public void setTxt_ammount(double txt_ammount) {
		this.txt_ammount = txt_ammount;
	}

	public int getTxt_voucherno() {
		return txt_voucherno;
	}

	public void setTxt_voucherno(int txt_voucherno) {
		this.txt_voucherno = txt_voucherno;
	}

	public String getTxt_tranfrom() {
		return txt_tranfrom;
	}

	public void setTxt_tranfrom(String txt_tranfrom) {
		this.txt_tranfrom = txt_tranfrom;
	}

	

	public int getTxt_tracNum() {
		return txt_tracNum;
	}

	public void setTxt_tracNum(int txt_tracNum) {
		this.txt_tracNum = txt_tracNum;
	}

	public String getTxt_custname() {
		return txt_custname;
	}

	public void setTxt_custname(String txt_custname) {
		this.txt_custname = txt_custname;
	}

	
	
	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getTxt_ac() {
		return txt_ac;
	}

	public void setTxt_ac(String txt_ac) {
		this.txt_ac = txt_ac;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getTxt_bal() {
		return txt_bal;
	}

	public void setTxt_bal(double txt_bal) {
		this.txt_bal = txt_bal;
	}

	public double getTxt_entAmo() {
		return txt_entAmo;
	}

	public void setTxt_entAmo(double txt_entAmo) {
		this.txt_entAmo = txt_entAmo;
	}

	

	

}
