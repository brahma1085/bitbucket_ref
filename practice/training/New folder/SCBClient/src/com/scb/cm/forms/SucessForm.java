package com.scb.cm.forms;

import org.apache.struts.action.ActionForm;

public class SucessForm extends ActionForm {
String pageId;
int cid;

public int getCid() {
	return cid;
}

public void setCid(int cid) {
	this.cid = cid;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

}
