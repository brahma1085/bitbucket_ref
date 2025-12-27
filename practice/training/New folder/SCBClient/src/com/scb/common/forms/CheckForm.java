package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

public class CheckForm extends ActionForm{
	
	private String name,pass,tml;

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

}
