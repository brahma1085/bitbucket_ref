package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class ReverseRecoveryForm extends ActionForm{
	String acctype,creditgltype,creditglcode,forward,update,delete,clear,accountclosed,accountnotfound; 
	int voucherno,accno,vouchernumber,result_del,result_update,result_submit;
	double othercharges,penalamt,intamt,prinamt;
	String creditgl;
	
	public String getCreditgl() {
		return creditgl;
	}
	public void setCreditgl(String creditgl) {
		this.creditgl = creditgl;
	}
	private PageIdForm  pageidentity= new PageIdForm();
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public String getCreditgltype() {
		return creditgltype;
	}
	public void setCreditgltype(String creditgltype) {
		this.creditgltype = creditgltype;
	}
	public String getCreditglcode() {
		return creditglcode;
	}
	public void setCreditglcode(String creditglcode) {
		this.creditglcode = creditglcode;
	}
	
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getClear() {
		return clear;
	}
	public void setClear(String clear) {
		this.clear = clear;
	}
	public int getVoucherno() {
		return voucherno;
	}
	public void setVoucherno(int voucherno) {
		this.voucherno = voucherno;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public double getOthercharges() {
		return othercharges;
	}
	public void setOthercharges(double othercharges) {
		this.othercharges = othercharges;
	}
	public double getPenalamt() {
		return penalamt;
	}
	public void setPenalamt(double penalamt) {
		this.penalamt = penalamt;
	}
	public double getIntamt() {
		return intamt;
	}
	public void setIntamt(double intamt) {
		this.intamt = intamt;
	}
	public double getPrinamt() {
		return prinamt;
	}
	public void setPrinamt(double prinamt) {
		this.prinamt = prinamt;
	}
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	/*public boolean isCreditgl() {
		return creditgl;
	}
	public void setCreditgl(boolean creditgl) {
		this.creditgl = creditgl;
	}*/
	public int getVouchernumber() {
		return vouchernumber;
	}
	public void setVouchernumber(int vouchernumber) {
		this.vouchernumber = vouchernumber;
	}
	public String getAccountclosed() {
		return accountclosed;
	}
	public void setAccountclosed(String accountclosed) {
		this.accountclosed = accountclosed;
	}
	
	public String getAccountnotfound() {
		return accountnotfound;
	}
	public void setAccountnotfound(String accountnotfound) {
		this.accountnotfound = accountnotfound;
	}
	public int getResult_del() {
		return result_del;
	}
	public void setResult_del(int result_del) {
		this.result_del = result_del;
	}
	public int getResult_update() {
		return result_update;
	}
	public void setResult_update(int result_update) {
		this.result_update = result_update;
	}
	public int getResult_submit() {
		return result_submit;
	}
	public void setResult_submit(int result_submit) {
		this.result_submit = result_submit;
	}
	
}