package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class DisbursementForm extends ActionForm
{
	private static final long serialVersionUID = 1L;
	//private String pageId;
	private String acctype,validateFlag,verify_button;
	private String details,forward;
	private String agentcode,buttonvalue;
	private String shtype;
	int accno,appln_no;
	private double reqamount,disbleft;
	private PageIdForm  pageidentity= new PageIdForm();
	private String tabPaneHeading;
	private String purpose,payAccountType;
	private String priority,appndate,dirrelations,dirdetails,paymtmode,payactype;
	int dis_result;
	private double disbamt,amount,intrate,installment,penalrate;
	private int period,holiday,interesttype,interestcalctype;
	private String combo_pay_mode;
	private String payAccType,payAccName,disburse_submit,weak;
	public String getWeak() {
		return weak;
	}
	public void setWeak(String weak) {
		this.weak = weak;
	}
	private int payAccNo,shno,payaccno;
	public String getPayAccName() {
		return payAccName;
	}
	public void setPayAccName(String payAccName) {
		this.payAccName = payAccName;
	}
	public int getPayAccNo() {
		return payAccNo;
	}
	public void setPayAccNo(int payAccNo) {
		this.payAccNo = payAccNo;
	}
	public String getPayAccType() {
		return payAccType;
	}
	public void setPayAccType(String payAccType) {
		this.payAccType = payAccType;
	}
	public String getCombo_pay_mode() {
		return combo_pay_mode;
	}
	public void setCombo_pay_mode(String combo_pay_mode) {
		this.combo_pay_mode = combo_pay_mode;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTabPaneHeading() {
		return tabPaneHeading;
	}
	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public int getShno() {
		return shno;
	}
	public void setShno(int shno) {
		this.shno = shno;
	}
	public int getAppln_no() {
		return appln_no;
	}
	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}
	public String getAppndate() {
		return appndate;
	}
	public void setAppndate(String appndate) {
		this.appndate = appndate;
	}
	public double getReqamount() {
		return reqamount;
	}
	public void setReqamount(double reqamount) {
		this.reqamount = reqamount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getHoliday() {
		return holiday;
	}
	public void setHoliday(int holiday) {
		this.holiday = holiday;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getIntrate() {
		return intrate;
	}
	public void setIntrate(double intrate) {
		this.intrate = intrate;
	}
	public double getInstallment() {
		return installment;
	}
	public void setInstallment(double installment) {
		this.installment = installment;
	}
	public String getShtype() {
		return shtype;
	}
	public void setShtype(String shtype) {
		this.shtype = shtype;
	}
	public double getPenalrate() {
		return penalrate;
	}
	public void setPenalrate(double penalrate) {
		this.penalrate = penalrate;
	}
	public double getDisbamt() {
		return disbamt;
	}
	public void setDisbamt(double disbamt) {
		this.disbamt = disbamt;
	}
	public String getPayAccountType() {
		return payAccountType;
	}
	public void setPayAccountType(String payAccountType) {
		this.payAccountType = payAccountType;
	}
	public String getButtonvalue() {
		return buttonvalue;
	}
	public void setButtonvalue(String buttonvalue) {
		this.buttonvalue = buttonvalue;
	}
	public String getValidateFlag() {
		return validateFlag;
	}
	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}
	public double getDisbleft() {
		return disbleft;
	}
	public void setDisbleft(double disbleft) {
		this.disbleft = disbleft;
	}
	public int getDis_result() {
		return dis_result;
	}
	public void setDis_result(int dis_result) {
		this.dis_result = dis_result;
	}
	public String getVerify_button() {
		return verify_button;
	}
	public void setVerify_button(String verify_button) {
		this.verify_button = verify_button;
	}
	public String getPayactype() {
		return payactype;
	}
	public void setPayactype(String payactype) {
		this.payactype = payactype;
	}
	public int getPayaccno() {
		return payaccno;
	}
	public void setPayaccno(int payaccno) {
		this.payaccno = payaccno;
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
	public String getPaymtmode() {
		return paymtmode;
	}
	public void setPaymtmode(String paymtmode) {
		this.paymtmode = paymtmode;
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
	public String getDisburse_submit() {
		return disburse_submit;
	}
	public void setDisburse_submit(String disburse_submit) {
		this.disburse_submit = disburse_submit;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
}
