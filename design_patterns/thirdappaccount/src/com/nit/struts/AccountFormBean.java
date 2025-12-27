package com.nit.struts;

import org.apache.struts.action.ActionForm;

public class AccountFormBean extends ActionForm {
	private static final long serialVersionUID = 1L;
	private int accno;

	public int getAccno() {
		return accno;
	}

	public void setAccno(int accno) {
		this.accno = accno;
	}

}
