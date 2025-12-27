package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class FormsDetailForm extends ActionForm{
String pageId,modulecode,forward,addinformname,addinpageno;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getModulecode() {
	return modulecode;
}

public void setModulecode(String modulecode) {
	this.modulecode = modulecode;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getAddinformname() {
	return addinformname;
}

public void setAddinformname(String addinformname) {
	this.addinformname = addinformname;
}

public String getAddinpageno() {
	return addinpageno;
}

public void setAddinpageno(String addinpageno) {
	this.addinpageno = addinpageno;
}
}
