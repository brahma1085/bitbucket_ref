package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class OpenedClosedAccountsPrintingReportForm extends ActionForm {
	
	private String fromdate,todate,acc_status,acc_type;
	private String forward,pageId,records;
	String sysdate;
	

	public String getSysdate() {
		return sysdate;
	}


	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}


	  
	public String getFromdate() {return fromdate;}
	public void setFromdate(String fromdate) {this.fromdate = fromdate;}

	public String getTodate() {	return todate;}
	public void setTodate(String todate) {this.todate = todate;}

	public String getForward() {return forward;}
	public void setForward(String forward) {this.forward = forward;}

	public String getPageId() {	return pageId;}
	public void setPageId(String pageId) {this.pageId = pageId;}
	
	public String getAcc_status() { return acc_status;}
	public void setAcc_status(String acc_status) {this.acc_status = acc_status;	}
	
	public String getAcc_type() {return acc_type;}
	public void setAcc_type(String acc_type) {this.acc_type = acc_type;	}
	
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}

}