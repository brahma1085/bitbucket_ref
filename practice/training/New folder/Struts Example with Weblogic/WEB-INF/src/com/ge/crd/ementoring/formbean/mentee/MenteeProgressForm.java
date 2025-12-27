package com.ge.crd.ementoring.formbean.mentee;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MenteeProgressForm extends ActionForm{
	
	private ArrayList inputProgressRecord;
	private String userAdminFlag;
	
	
	public String getUserAdminFlag() {
		return userAdminFlag;
	}

	public void setUserAdminFlag(String userAdminFlag) {
		this.userAdminFlag = userAdminFlag;
	}

	public ArrayList getInputProgressRecord() {
		return inputProgressRecord;
	}

	public void setInputProgressRecord(ArrayList inputProgressRecord) {
		this.inputProgressRecord = inputProgressRecord;
	}

}
