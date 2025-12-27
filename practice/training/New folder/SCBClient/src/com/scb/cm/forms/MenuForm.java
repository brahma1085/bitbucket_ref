package com.scb.cm.forms;

import org.apache.struts.action.ActionForm;

public class MenuForm extends ActionForm {
	
	String pageId,value,cid_first,totalclear;
	int custid;
	
	
	
	
	public String getTotalclear() {
		return totalclear;
	}
	public void setTotalclear(String totalclear) {
		this.totalclear = totalclear;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) 
	{
		this.pageId = pageId;
	}
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public String getCid_first() {
		return cid_first;
	}
	public void setCid_first(String cid_first) {
		this.cid_first = cid_first;
	}
	
	
	
}
