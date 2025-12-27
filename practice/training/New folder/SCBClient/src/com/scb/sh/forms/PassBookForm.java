package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class PassBookForm extends ActionForm {
	private String pageId;
	private String shtype,shnum,cat,lnavld,branch,div_dt,accstat,tabPaneHeading,defaultTab,code;
	private String trn_dt,amt_dt,amt_cr,no_of_sh;
	private String validation,downLoad,flag;
	

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getTrn_dt() {
		return trn_dt;
	}

	public void setTrn_dt(String trn_dt) {
		this.trn_dt = trn_dt;
	}

	public String getAmt_dt() {
		return amt_dt;
	}

	public void setAmt_dt(String amt_dt) {
		this.amt_dt = amt_dt;
	}

	public String getAmt_cr() {
		return amt_cr;
	}

	public void setAmt_cr(String amt_cr) {
		this.amt_cr = amt_cr;
	}

	public String getNo_of_sh() {
		return no_of_sh;
	}

	public void setNo_of_sh(String no_of_sh) {
		this.no_of_sh = no_of_sh;
	}

	public String getShtype() {
		return shtype;
	}

	public void setShtype(String shtype) {
		this.shtype = shtype;
	}

	public String getShnum() {
		return shnum;
	}

	public void setShnum(String shnum) {
		this.shnum = shnum;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getLnavld() {
		return lnavld;
	}

	public void setLnavld(String lnavld) {
		this.lnavld = lnavld;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDiv_dt() {
		return div_dt;
	}

	public void setDiv_dt(String div_dt) {
		this.div_dt = div_dt;
	}

	public String getAccstat() {
		return accstat;
	}

	public void setAccstat(String accstat) {
		this.accstat = accstat;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTabPaneHeading() {
		return tabPaneHeading;
	}

	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDownLoad() {
		return downLoad;
	}

	public void setDownLoad(String downLoad) {
		this.downLoad = downLoad;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
