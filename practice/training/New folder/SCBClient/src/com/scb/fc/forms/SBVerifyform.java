package com.scb.fc.forms;
import org.apache.struts.action.ActionForm;


public class SBVerifyform extends ActionForm
{
	String pageId,acType,cid,introacno,acno,forward,introType,detailsCombo;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getIntroacno() {
		return introacno;
	}

	public void setIntroacno(String introacno) {
		this.introacno = introacno;
	}

	public String getAcno() {
		return acno;
	}

	public void setAcno(String acno) {
		this.acno = acno;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getIntroType() {
		return introType;
	}

	public void setIntroType(String introType) {
		this.introType = introType;
	}

	public String getDetailsCombo() {
		return detailsCombo;
	}

	public void setDetailsCombo(String detailsCombo) {
		this.detailsCombo = detailsCombo;
	}
}

