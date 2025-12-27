package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class DividendRegistryForm extends ActionForm{

	private String pageId;
	private String date,comb_branch,individual,brcode;
	private String forward,view,clear,printfile;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getComb_branch() {
		return comb_branch;
	}

	public void setComb_branch(String comb_branch) {
		this.comb_branch = comb_branch;
	}

	public String getIndividual() {
		return individual;
	}

	public void setIndividual(String individual) {
		this.individual = individual;
	}

	public String getBrcode() {
		return brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPrintfile() {
		return printfile;
	}

	public void setPrintfile(String printfile) {
		this.printfile = printfile;
	} 
}
