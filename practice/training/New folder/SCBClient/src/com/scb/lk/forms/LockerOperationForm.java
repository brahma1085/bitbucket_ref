package com.scb.lk.forms;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class LockerOperationForm extends ActionForm{
	
	String txt_operatedby;
    String txt_operatedon;
    String txt_timein;
    String txt_timeout;
    String acn_no;
    String butt_timein;
    String butt_timeout;
    String tex;
    String pageId;
    String combo_types;
    String delete;
    String txt_password;
    String button;
    String submit;
    String forward,hiddenTime;
    String lockerNo,lockerType;
    String validateFlag,flag;
    String fromDate,button_verify;
    
    
    
    
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getTxt_operatedby() {
		return txt_operatedby;
	}
	public void setTxt_operatedby(String txt_operatedby) {
		this.txt_operatedby = txt_operatedby;
	}
	public String getTxt_operatedon() {
		return txt_operatedon;
	}
	public void setTxt_operatedon(String txt_operatedon) {
		this.txt_operatedon = txt_operatedon;
	}
	public String getTxt_timein() {
		return txt_timein;
	}
	public void setTxt_timein(String txt_timein) {
		this.txt_timein = txt_timein;
	}
	public String getTxt_timeout() {
		return txt_timeout;
	}
	public void setTxt_timeout(String txt_timeout) {
		this.txt_timeout = txt_timeout;
	}
	public String getAcn_no() {
		return acn_no;
	}
	public void setAcn_no(String acn_no) {
		this.acn_no = acn_no;
	}
	public String getButt_timein() {
		return butt_timein;
	}
	public void setButt_timein(String butt_timein) {
		this.butt_timein = butt_timein;
	}
	public String getButt_timeout() {
		return butt_timeout;
	}
	public void setButt_timeout(String butt_timeout) {
		this.butt_timeout = butt_timeout;
	}
	public String getTex() {
		return tex;
	}
	public void setTex(String tex) {
		this.tex = tex;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getCombo_types() {
		return combo_types;
	}
	public void setCombo_types(String combo_types) {
		this.combo_types = combo_types;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getTxt_password() {
		return txt_password;
	}
	public void setTxt_password(String txt_password) {
		this.txt_password = txt_password;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getLockerNo() {
		return lockerNo;
	}
	public void setLockerNo(String lockerNo) {
		this.lockerNo = lockerNo;
	}
	public String getLockerType() {
		return lockerType;
	}
	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}
    
    /*public void reset(ActionMapping mapping, HttpServletRequest request) {
    
    	this.lockerNo="";
    	this.lockerType="";
    	this.txt_timein="";
    }*/
public void reset(ActionMapping mapping, ServletRequest request) {
	this.lockerNo="";
	this.lockerType="";
	this.txt_timein="";
	super.reset(mapping, request);
}
public String getButton_verify() {
	return button_verify;
}
public void setButton_verify(String button_verify) {
	this.button_verify = button_verify;
}
public String getHiddenTime() {
	return hiddenTime;
}
public void setHiddenTime(String hiddenTime) {
	this.hiddenTime = hiddenTime;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}
}
