package com.scb.login.forms;

import org.apache.struts.action.ActionForm;


/**
 * @author admin
 *
 */
public class LoginFB extends ActionForm {
	private String userTml;
	private String pageId;
	private String userName;
	private String userPwd;
	private String errorMsg;
	private String validations;
	private String branch;
	private String alert;
	
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getValidations() {
		return validations;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getUserTml() {
		return userTml;
	}
	public void setUserTml(String userTml) {
		this.userTml = userTml;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
