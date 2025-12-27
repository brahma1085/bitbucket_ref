package com.scb.adm.forms;

import org.apache.struts.action.ActionForm;

public class ChangePwdFormBean extends ActionForm{
	private String pageId;
	private String validations,flag,name,oldPwd,NewPwd,confirmPwd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return NewPwd;
	}
	public void setNewPwd(String newPwd) {
		NewPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
}
