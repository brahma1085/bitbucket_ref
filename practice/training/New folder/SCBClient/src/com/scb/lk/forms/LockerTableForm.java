package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerTableForm extends ActionForm {
	
	String lkTypes;
	String cabTypes;
	String pageId;
	String createTab;
	int focusType;
	String focusCab;
	String clickButt;
	String selectedLock;
	int hiddenIndex,hiddenIndex2;
	
	//selectedLock:-used in java script takes d value like 22s 100M etc
	
	
	public String getSelectedLock() {
		return selectedLock;
	}
	public void setSelectedLock(String selectedLock) {
		this.selectedLock = selectedLock;
	}
	public int getFocusType() {
		return focusType;
	}
	public void setFocusType(int focusType) {
		this.focusType = focusType;
	}
	public String getFocusCab() {
		return focusCab;
	}
	public void setFocusCab(String focusCab) {
		this.focusCab = focusCab;
	}
	public String getClickButt() {
		return clickButt;
	}
	public void setClickButt(String clickButt) {
		this.clickButt = clickButt;
	}
	public String getCreateTab() {
		return createTab;
	}
	public void setCreateTab(String createTab) {
		this.createTab = createTab;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	public String getLkTypes() {
		return lkTypes;
	}
	public void setLkTypes(String lkTypes) {
		this.lkTypes = lkTypes;
	}
	public String getCabTypes() {
		return cabTypes;
	}
	public void setCabTypes(String cabTypes) {
		this.cabTypes = cabTypes;
	}
	public int getHiddenIndex() {
		return hiddenIndex;
	}
	public void setHiddenIndex(int hiddenIndex) {
		this.hiddenIndex = hiddenIndex;
	}
	public int getHiddenIndex2() {
		return hiddenIndex2;
	}
	public void setHiddenIndex2(int hiddenIndex2) {
		this.hiddenIndex2 = hiddenIndex2;
	}

	

}
