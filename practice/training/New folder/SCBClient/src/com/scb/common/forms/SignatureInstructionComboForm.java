package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

public class SignatureInstructionComboForm extends ActionForm{
	String cid;
    String acType;
    String acNum;
    String name;
    String sign;
    String tyop;
    
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcNum() {
		return acNum;
	}
	public void setAcNum(String acNum) {
		this.acNum = acNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTyop() {
		return tyop;
	}
	public void setTyop(String tyop) {
		this.tyop = tyop;
	}

}
