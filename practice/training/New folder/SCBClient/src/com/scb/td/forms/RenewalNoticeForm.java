package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class RenewalNoticeForm extends ActionForm {
  
	private String pageId;
	private String ac_type,fromdt,todt,testing;
	String sysdate;

	

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getFromdt() {
		return fromdt;
	}

	public void setFromdt(String fromdt) {
		this.fromdt = fromdt;
	}

	public String getTodt() {
		return todt;
	}

	public void setTodt(String todt) {
		this.todt = todt;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}
	


	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

	
}
