package com.scb.cm.forms;

import org.apache.struts.action.ActionForm;

public class QueryOnCustomerForm extends ActionForm{
String name,pageId,comboname;
 
public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPageId() {
	return pageId; 
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getComboname() {
	return comboname;
}

public void setComboname(String comboname) {
	this.comboname = comboname;
}
}
