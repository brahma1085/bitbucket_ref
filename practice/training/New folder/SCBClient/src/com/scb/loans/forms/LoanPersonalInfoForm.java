package com.scb.loans.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.scb.common.forms.PageIdForm;

public class LoanPersonalInfoForm extends ActionForm
{
	//private String pageId;
	private String acctype,validateFlag;
	private String details;
	private String agentcode,buttonvalue;
	private String shno,shtype;
	int accno,appln_no,appndate;
	private double reqamount;
	private PageIdForm  pageidentity= new PageIdForm();
	private String tabPaneHeading;
	private String purpose,disbamt,payAccountType;
	private String priority;
	private double amount,holiday,intrate,installment,penalrate;
	private int period;
	private String combo_pay_mode;
	private String payAccType,payAccName;
	private int payAccNo;
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
	public String getShno() {
		return shno;
	}
	public void setShno(String shno) {
		this.shno = shno;
	}
	public int getAppln_no() {
		return appln_no;
	}
	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}
	public int getAppndate() {
		return appndate;
	}
	public void setAppndate(int appndate) {
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
	public double getHoliday() {
		return holiday;
	}
	public void setHoliday(double holiday) {
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
	public String getDisbamt() {
		return disbamt;
	}
	public void setDisbamt(String disbamt) {
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
}
