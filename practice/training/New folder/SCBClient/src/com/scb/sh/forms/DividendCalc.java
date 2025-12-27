package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class DividendCalc extends ActionForm{

	private String pageId,validations;
	private String from_dt,to_dt,setrate,drfamt;
	private String butt_setrate,butt_cal,butt_recal,butt_clear,forward;

	public String getButt_setrate() {
		return butt_setrate;
	}

	public void setButt_setrate(String butt_setrate) {
		this.butt_setrate = butt_setrate;
	}

	public String getButt_cal() {
		return butt_cal;
	}

	public void setButt_cal(String butt_cal) {
		this.butt_cal = butt_cal;
	}

	public String getButt_recal() {
		return butt_recal;
	}

	public void setButt_recal(String butt_recal) {
		this.butt_recal = butt_recal;
	}

	public String getButt_clear() {
		return butt_clear;
	}

	public void setButt_clear(String butt_clear) {
		this.butt_clear = butt_clear;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getFrom_dt() {
		return from_dt;
	}

	public void setFrom_dt(String from_dt) {
		this.from_dt = from_dt;
	}

	public String getTo_dt() {
		return to_dt;
	}

	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}

	public String getSetrate() {
		return setrate;
	}

	public void setSetrate(String setrate) {
		this.setrate = setrate;
	}

	public String getDrfamt() {
		return drfamt;
	}

	public void setDrfamt(String drfamt) {
		this.drfamt = drfamt;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}
}
