package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

public class LockerDataEntry  extends ActionForm{
	
	private String pageId,totalLockers,totalRows;
	private String flag,validateFlag;
	private String lkTypes,description,keyNum,length,height,depth,keynum,nolocker;
	private String cabDesciption,totLockers,masterKey,noRows,rowNum,columnnum,ronum;
	public String getLkTypes() {
		return lkTypes;
	}

	public String getColumnnum() {
		return columnnum;
	}

	public void setColumnnum(String columnnum) {
		this.columnnum = columnnum;
	}

	public void setLkTypes(String lkTypes) {
		this.lkTypes = lkTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyNum() {
		return keyNum;
	}

	public void setKeyNum(String keyNum) {
		this.keyNum = keyNum;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getPageId() {
		return pageId;
	} 
  
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTotalLockers() {
		return totalLockers;
	}

	public void setTotalLockers(String totalLockers) {
		this.totalLockers = totalLockers;
	}

	public String getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCabDesciption() {
		return cabDesciption;
	}

	public void setCabDesciption(String cabDesciption) {
		this.cabDesciption = cabDesciption;
	}

	public String getTotLockers() {
		return totLockers;
	}

	public void setTotLockers(String totLockers) {
		this.totLockers = totLockers;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public String getNoRows() {
		return noRows;
	}

	public void setNoRows(String noRows) {
		this.noRows = noRows;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getKeynum() {
		return keynum;
	}

	public void setKeynum(String keynum) {
		this.keynum = keynum;
	}

	public String getNolocker() {
		return nolocker;
	}

	public void setNolocker(String nolocker) {
		this.nolocker = nolocker;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getRonum() {
		return ronum;
	}

	public void setRonum(String ronum) {
		this.ronum = ronum;
	}

	

}
