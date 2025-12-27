
package com.scb.fc.forms;
import org.apache.struts.action.ActionForm;

public class JointHoldersUpdationFB extends ActionForm{
	private String pageId,delflag;
	private String validations,flag,acno,acType,details,jointhcid,subflag;
	
	
	
	
	
	
	
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getAcno() {
		return acno;
	}
	public void setAcno(String acno) {
		this.acno = acno;
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
	public String getJointhcid() {
		return jointhcid;
	}
	public void setJointhcid(String jointhcid) {
		this.jointhcid = jointhcid;
	}
	public String getSubflag() {
		return subflag;
	}
	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
}
