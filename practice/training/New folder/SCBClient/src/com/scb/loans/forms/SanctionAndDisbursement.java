package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class SanctionAndDisbursement extends ActionForm
{
	private String loanType;

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
}
