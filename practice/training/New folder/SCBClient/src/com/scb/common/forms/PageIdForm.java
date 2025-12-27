package com.scb.common.forms;

import java.io.Serializable;

public class PageIdForm implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String pageId;

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
	System.out.println("this.pageId==="+this.pageId);
}
}
