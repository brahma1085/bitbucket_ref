package com.scb.csh.forms;

import org.apache.struts.action.ActionForm;

public class ConsolidateScrollform extends ActionForm{
	String date,report,terminal,pageId,but_value,but_print,testing,table1,table2,value,validation;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getBut_value() {
		return but_value;
	}

	public void setBut_value(String but_value) {
		this.but_value = but_value;
	}

	public String getTable1() {
		return table1;
	}

	public void setTable1(String table1) {
		this.table1 = table1;
	}

	public String getTable2() {
		return table2;
	}

	public void setTable2(String table2) {
		this.table2 = table2;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

}
