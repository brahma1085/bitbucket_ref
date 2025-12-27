package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class QueryOnReceipt extends ActionForm{
	
	private String pageId,ac_type;
	private String receipt_no,name,testing;
	
	private  String view,cancel,validate;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}	
	
	
	
	
	
	
	

}
