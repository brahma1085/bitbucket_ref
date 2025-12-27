package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

public class RentActionFrom extends ActionForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6648981540538353360L;
	String land_addr;
    double totamt,tax_payment,netincome;
	public String getLand_addr() {
		return land_addr;
	}
	public void setLand_addr(String land_addr) {
		this.land_addr = land_addr;
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
	public void setTax_payment(double tax_payment) {
		this.tax_payment = tax_payment;
	}
	public double getNetincome() {
		return netincome;
	}
	public void setNetincome(double netincome) {
		this.netincome = netincome;
	}
}
