package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class AckReconciliationForm extends ActionForm {

	private String pageId,flag,validateFlag,dummyButton,txtAckNum,txtAckDate,txtTotAmount,chkBox;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	public String getDummyButton() {
		return dummyButton;
	}

	public void setDummyButton(String dummyButton) {
		this.dummyButton = dummyButton;
	}

	public String getTxtAckNum() {
		return txtAckNum;
	}

	public void setTxtAckNum(String txtAckNum) {
		this.txtAckNum = txtAckNum;
	}

	public String getTxtAckDate() {
		return txtAckDate;
	}

	public void setTxtAckDate(String txtAckDate) {
		this.txtAckDate = txtAckDate;
	}

	public String getTxtTotAmount() {
		return txtTotAmount;
	}

	public void setTxtTotAmount(String txtTotAmount) {
		this.txtTotAmount = txtTotAmount;
	}

	public String getChkBox() {
		return chkBox;
	}

	public void setChkBox(String chkBox) {
		this.chkBox = chkBox;
	}
	
}
