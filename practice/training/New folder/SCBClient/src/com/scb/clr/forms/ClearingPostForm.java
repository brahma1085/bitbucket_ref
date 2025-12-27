package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class ClearingPostForm extends ActionForm {
	
	private String pageId,clrDate,flag,validateFlag;
	private String clrBank,clrNum;
	private boolean selectAll,chkBox;
	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getClrDate() {
		return clrDate;
	}

	public void setClrDate(String clrDate) {
		this.clrDate = clrDate;
	}

	

	

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	

	public String getClrBank() {
		return clrBank;
	}

	public void setClrBank(String clrBank) {
		this.clrBank = clrBank;
	}

	public String getClrNum() {
		return clrNum;
	}

	public void setClrNum(String clrNum) {
		this.clrNum = clrNum;
	}

	public boolean isChkBox() {
		return chkBox;
	}

	public void setChkBox(boolean chkBox) {
		this.chkBox = chkBox;
	}

}
