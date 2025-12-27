package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class OpenReportForm extends ActionForm{

	private String pageId;
	private String frm_dt,to_dt;
	private String forward,view,clear,printfile,reprint,file,file1;

	public String getFrm_dt() {
		return frm_dt;
	}

	public void setFrm_dt(String frm_dt) {
		this.frm_dt = frm_dt;
	}

	public String getTo_dt() {
		return to_dt;
	}

	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	

	public String getPrintfile() {
		return printfile;
	}

	public void setPrintfile(String printfile) {
		this.printfile = printfile;
	}

	public String getReprint() {
		return reprint;
	}

	public void setReprint(String reprint) {
		this.reprint = reprint;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}
}
