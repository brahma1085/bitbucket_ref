package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class NPAReportForm extends ActionForm{
  
	private String npadate,assettype,acctype,forward,but_print,testing;
	
	private int from_accno,to_accno,tabletype;    
	
	private PageIdForm  pageidentity= new PageIdForm();
	
	public String getBut_print() {
		return but_print;
	}
	public void setBut_print(String butPrint) {
		but_print = butPrint;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	
	public String getNpadate() {
		return npadate;
	}
	public void setNpadate(String npadate) {
		this.npadate = npadate;
	}
	public String getAssettype() {
		return assettype;
	}
	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	
	
	public int getTabletype() {
		return tabletype;
	}
	public void setTabletype(int tabletype) {
		this.tabletype = tabletype;
	}
	public int getFrom_accno() {
		return from_accno;
	}
	public void setFrom_accno(int from_accno) {
		this.from_accno = from_accno;
	}
	public int getTo_accno() {
		return to_accno;
	}
	public void setTo_accno(int to_accno) {
		this.to_accno = to_accno;
	}
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	
}
