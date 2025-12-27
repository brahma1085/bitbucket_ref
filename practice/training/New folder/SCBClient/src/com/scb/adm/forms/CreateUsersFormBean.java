package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class CreateUsersFormBean extends ActionForm{
	private static final long serialVersionUID = 1L;
	private String pageId;
	private String validations,userChoice,flag,uid,cid,name,pwd,rePwd,pwdExpPeriod,pwdExpDate,acFrmDate,acToDate,branch;
	private int br_code;
	public int getBr_code() {
		return br_code;
	}
	public void setBr_code(int brCode) {
		br_code = brCode;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRePwd() {
		return rePwd;
	}
	public void setRePwd(String rePwd) {
		this.rePwd = rePwd;
	}
	public String getPwdExpPeriod() {
		return pwdExpPeriod;
	}
	public void setPwdExpPeriod(String pwdExpPeriod) {
		this.pwdExpPeriod = pwdExpPeriod;
	}
	public String getPwdExpDate() {
		return pwdExpDate;
	}
	public void setPwdExpDate(String pwdExpDate) {
		this.pwdExpDate = pwdExpDate;
	}
	public String getAcFrmDate() {
		return acFrmDate;
	}
	public void setAcFrmDate(String acFrmDate) {
		this.acFrmDate = acFrmDate;
	}
	public String getAcToDate() {
		return acToDate;
	}
	public void setAcToDate(String acToDate) {
		this.acToDate = acToDate;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getUserChoice() {
		return userChoice;
	}
	public void setUserChoice(String userChoice) {
		this.userChoice = userChoice;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
