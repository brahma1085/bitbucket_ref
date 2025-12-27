package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Dec 4, 2007
 * Time: 5:10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class LockerTypesForm extends ActionForm {
    /*String height;
    
    String length;
    String depth;*/
    String combo_types;
    String PageId;
    String insert;
    String delete;
    String text;
    String description,lockerHeight,lockerLength,lockerDepth,lockerAcType,lockerType;
    String clear;
    String butt,validateFlag,forward;
    
    

    public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getPageId() {
        return PageId;
    }

    public void setPageId(String pageId) {
        PageId = pageId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getCombo_types() {
        return combo_types;
    }

    public void setCombo_types(String combo_types) {
        this.combo_types = combo_types;
    }

	public String getLockerType() {
		return lockerType;
	}

	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLockerHeight() {
		return lockerHeight;
	}

	public void setLockerHeight(String lockerHeight) {
		this.lockerHeight = lockerHeight;
	}

	public String getLockerLength() {
		return lockerLength;
	}

	public void setLockerLength(String lockerLength) {
		this.lockerLength = lockerLength;
	}

	public String getLockerDepth() {
		return lockerDepth;
	}

	public void setLockerDepth(String lockerDepth) {
		this.lockerDepth = lockerDepth;
	}

	public String getLockerAcType() {
		return lockerAcType;
	}

	public void setLockerAcType(String lockerAcType) {
		this.lockerAcType = lockerAcType;
	}

	public String getButt() {
		return butt;
	}

	public void setButt(String butt) {
		this.butt = butt;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}
}
