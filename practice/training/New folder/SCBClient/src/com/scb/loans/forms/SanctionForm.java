package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class SanctionForm extends ActionForm {
	
	int sanction_res;
	private String acctype,shtype,shno,details,priority,disbamt,combo_pay_mode,buttonvalue,agentcode,value,tabPaneHeading,disbleft;
	private int accno,period,sanction,purpose;
	private double amount,holiday,intrate,penalrate,installment;
	private boolean weak;
	private PageIdForm  pageidentity= new PageIdForm();
	ApplicationForm applicationform = new ApplicationForm();
	// ServiceActionForm serviceActionForm = new ServiceActionForm();
	SelfEmployedForm selfEmployedForm = new SelfEmployedForm();
	BusinessActionForm businessActionForm = new BusinessActionForm();
	RentActionFrom rentActionFrom = new RentActionFrom();
	PensionActionForm pensionActionForm = new PensionActionForm();

	
	//used in Sanction form
	private int appln_no,interesttype,interestcalctype,payaccno;
	private double reqamount;
	private String appndate,relativetodir,submit,paymtmode,dirrelations,dirdetails,payactype;
	
	//Used in Signature form
	
	private int acNum;
	private String acType,name,tyop;
	
	String employername, address, designation, department, employmtnature,
	emp_nature;
	double income, expenditure, netincome;
	int phoneno, empno, serv_length, phno;
	boolean confirmation, transferable, certicateenclosed;

	// BusinessForm
	private String concernname, businessnature;
	private double surplus, avg_turnover;

	// 	PensionForm

	String empname, bankname;
	double pensionamt;

	// Rent Form
	private String land_addr;
	private double totamt, tax_payment;

	public String getConcernname() {
		return concernname;
	}
	public void setConcernname(String concernname) {
		this.concernname = concernname;
	}
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	public double getSurplus() {
		return surplus;
	}
	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
	public double getAvg_turnover() {
		return avg_turnover;
	}
	public void setAvg_turnover(double avgTurnover) {
		avg_turnover = avgTurnover;
	}
	public String getLand_addr() {
		return land_addr;
	}
	public void setLand_addr(String landAddr) {
		land_addr = landAddr;
	}
	public double getTotamt() {
		return totamt;
	}
	public void setTotamt(double totamt) {
		this.totamt = totamt;
	}
	public double getTax_payment() {
		return tax_payment;
	}
	public void setTax_payment(double taxPayment) {
		tax_payment = taxPayment;
	}
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public String getShtype() {
		return shtype;
	}
	public void setShtype(String shtype) {
		this.shtype = shtype;
	}
	public String getShno() {
		return shno;
	}
	public void setShno(String shno) {
		this.shno = shno;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public boolean isWeak() {
		return weak;
	}
	public void setWeak(boolean weak) {
		this.weak = weak;
	}
	public String getDisbamt() {
		return disbamt;
	}
	public void setDisbamt(String disbamt) {
		this.disbamt = disbamt;
	}
	public String getCombo_pay_mode() {
		return combo_pay_mode;
	}
	public void setCombo_pay_mode(String combo_pay_mode) {
		this.combo_pay_mode = combo_pay_mode;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	public double getIntrate() {
		return intrate;
	}
	public void setIntrate(double intrate) {
		this.intrate = intrate;
	}
	
	public int getAppln_no() {
		return appln_no;
	}
	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}
	
	
	public double getReqamount() {
		return reqamount;
	}
	public void setReqamount(double reqamount) {
		this.reqamount = reqamount;
	}
	
	public int getInteresttype() {
		return interesttype;
	}
	public void setInteresttype(int interesttype) {
		this.interesttype = interesttype;
	}
	public int getInterestcalctype() {
		return interestcalctype;
	}
	public void setInterestcalctype(int interestcalctype) {
		this.interestcalctype = interestcalctype;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getHoliday() {
		return holiday;
	}
	public void setHoliday(double holiday) {
		this.holiday = holiday;
	}
	public double getPenalrate() {
		return penalrate;
	}
	public void setPenalrate(double penalrate) {
		this.penalrate = penalrate;
	}
	public double getInstallment() {
		return installment;
	}
	public void setInstallment(double installment) {
		this.installment = installment;
	}
	public String getButtonvalue() {
		return buttonvalue;
	}
	public void setButtonvalue(String buttonvalue) {
		this.buttonvalue = buttonvalue;
	}
	public int getSanction() {
		return sanction;
	}
	public void setSanction(int sanction) {
		this.sanction = sanction;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public String getAppndate() {
		return appndate;
	}
	public void setAppndate(String appndate) {
		this.appndate = appndate;
	}
	public String getRelativetodir() {
		return relativetodir;
	}
	public void setRelativetodir(String relativetodir) {
		this.relativetodir = relativetodir;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getPaymtmode() {
		return paymtmode;
	}
	public void setPaymtmode(String paymtmode) {
		this.paymtmode = paymtmode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getSanction_res() {
		return sanction_res;
	}
	public void setSanction_res(int sanction_res) {
		this.sanction_res = sanction_res;
	}
	public String getDirrelations() {
		return dirrelations;
	}
	public void setDirrelations(String dirrelations) {
		this.dirrelations = dirrelations;
	}
	public String getDirdetails() {
		return dirdetails;
	}
	public void setDirdetails(String dirdetails) {
		this.dirdetails = dirdetails;
	}
	public int getPayaccno() {
		return payaccno;
	}
	public void setPayaccno(int payaccno) {
		this.payaccno = payaccno;
	}
	public String getPayactype() {
		return payactype;
	}
	public void setPayactype(String payactype) {
		this.payactype = payactype;
	}
	public int getAcNum() {
		return acNum;
	}
	public void setAcNum(int acNum) {
		this.acNum = acNum;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTyop() {
		return tyop;
	}
	public void setTyop(String tyop) {
		this.tyop = tyop;
	}
	public String getTabPaneHeading() {
		return tabPaneHeading;
	}
	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}
	public String getDisbleft() {
		return disbleft;
	}
	public void setDisbleft(String disbleft) {
		this.disbleft = disbleft;
	}

}
