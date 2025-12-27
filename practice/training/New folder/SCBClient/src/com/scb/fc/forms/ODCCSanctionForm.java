package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ODCCSanctionForm extends ActionForm 
{
	String pageId,acType,acc_no,cobo_sh_type,share_no,details,creditlimit,limit,interestrate;
	String hidval,verifyhid;
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

	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getCobo_sh_type() {
		return cobo_sh_type;
	}

	public void setCobo_sh_type(String cobo_sh_type) {
		this.cobo_sh_type = cobo_sh_type;
	}

	public String getShare_no() {
		return share_no;
	}

	public void setShare_no(String share_no) {
		this.share_no = share_no;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCreditlimit() {
		return creditlimit;
	}

	public void setCreditlimit(String creditlimit) {
		this.creditlimit = creditlimit;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(String interestrate) {
		this.interestrate = interestrate;
	}

	public String getHidval() {
		return hidval;
	}

	public void setHidval(String hidval) {
		this.hidval = hidval;
	}

	public String getVerifyhid() {
		return verifyhid;
	}

	public void setVerifyhid(String verifyhid) {
		this.verifyhid = verifyhid;
	}
	
	
}
