package com.scb.fc.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class TabbedPaneForm extends ActionForm implements Serializable {
	String pageID;
	String tabNum;
	SBOpeningActionForm sbForm=new SBOpeningActionForm();
	public String getTabNum() {
		return tabNum;
	}

	public void setTabNum(String tabNum) {
		this.tabNum = tabNum;
	}

	public String getPageID() {
		return pageID;
	}

	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

	public SBOpeningActionForm getSbForm() {
		return sbForm;
	}

	public void setSbForm(SBOpeningActionForm sbForm) {
		this.sbForm = sbForm;
	}
}
