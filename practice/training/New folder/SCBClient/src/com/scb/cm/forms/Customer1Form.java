package com.scb.cm.forms;


import org.apache.struts.validator.ValidatorForm;

public class Customer1Form extends ValidatorForm {
	String name=null;
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
