package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class MaturityLedgerReport extends ActionForm {
	
	String pageId;
	String  from_date,to_date;
	String ac_type;
	String but_view,but_query,butt_search,but_file,but_print,but_reprint,but_clear,testing,forward;
	String sysdate;
	
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getBut_view() {
		return but_view;
	}
	public void setBut_view(String but_view) {
		this.but_view = but_view;
	}
	public String getBut_query() {
		return but_query;
	}
	public void setBut_query(String but_query) {
		this.but_query = but_query;
	}
	public String getButt_search() {
		return butt_search;
	}
	public void setButt_search(String butt_search) {
		this.butt_search = butt_search;
	}
	public String getBut_file() {
		return but_file;
	}
	public void setBut_file(String but_file) {
		this.but_file = but_file;
	}
	public String getBut_print() {
		return but_print;
	}
	public void setBut_print(String but_print) {
		this.but_print = but_print;
	}
	public String getBut_reprint() {
		return but_reprint;
	}
	public void setBut_reprint(String but_reprint) {
		this.but_reprint = but_reprint;
	}
	public String getBut_clear() {
		return but_clear;
	}
	public void setBut_clear(String but_clear) {
		this.but_clear = but_clear;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	


	public String getSysdate() {
			return sysdate;
		}


		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
		}

}
