package com.nit.struts;

import org.apache.struts.action.ActionForm;

public class AccountFormBean extends ActionForm {
	private static final long serialVersionUID = 1L;
	private float balance;

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
