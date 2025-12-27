package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {
	private String name,pass,tml,submit,clear;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}



	public String getTml() {
		return tml;
	}

	public void setTml(String tml) {
		this.tml = tml;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}
}
