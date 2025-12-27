package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ControlNumbersQuery  extends ActionForm{
	
	private String pageId,fromControlNumber,toControlNumber;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getFromControlNumber() {
		return fromControlNumber;
	}

	public void setFromControlNumber(String fromControlNumber) {
		this.fromControlNumber = fromControlNumber;
	}

	public String getToControlNumber() {
		return toControlNumber;
	}

	public void setToControlNumber(String toControlNumber) {
		this.toControlNumber = toControlNumber;
	}
	
	

}
