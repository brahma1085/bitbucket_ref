package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class AcknowledgementDataEntryForm  extends ActionForm{
	
	
	private String pageId,ackNumber,ackDate,source,clgType,amount,validateFlag,flag,validateMsg;
	private String submitbutton,clearbutton,deletebutton,updatebutton,bname,glflag,errorFlag;
	private int booleanFlag;
	//flag is used to set whhenever onchange n onblur n all,,,make use in action class to differentiate events

	public int getBooleanFlag() {
		return booleanFlag;
	}

	public void setBooleanFlag(int booleanFlag) {
		this.booleanFlag = booleanFlag;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(String ackNumber) {
		this.ackNumber = ackNumber;
	}

	public String getAckDate() {
		return ackDate;
	}

	public void setAckDate(String ackDate) {
		this.ackDate = ackDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getClgType() {
		return clgType;
	}

	public void setClgType(String clgType) {
		this.clgType = clgType;
	}



	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getSubmitbutton() {
		return submitbutton;
	}

	public void setSubmitbutton(String submitbutton) {
		this.submitbutton = submitbutton;
	}

	public String getClearbutton() {
		return clearbutton;
	}

	public void setClearbutton(String clearbutton) {
		this.clearbutton = clearbutton;
	}

	public String getDeletebutton() {
		return deletebutton;
	}

	public void setDeletebutton(String deletebutton) {
		this.deletebutton = deletebutton;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUpdatebutton() {
		return updatebutton;
	}

	public void setUpdatebutton(String updatebutton) {
		this.updatebutton = updatebutton;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getGlflag() {
		return glflag;
	}

	public void setGlflag(String glflag) {
		this.glflag = glflag;
	}

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}

	public String getValidateMsg() {
		return validateMsg;
	}

	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}

	

	
	

}
