
package com.scb.fc.forms;
import org.apache.struts.action.ActionForm;

public class SBUpdationFormBean extends ActionForm{
	private String pageId;
	private String validations,flag,acType,acNo,cid,introducerAcType,introducerAcNo,ckBook,pbSeq,intPayDate,freezed,inOperative,freezeReason,acOpenDate,acCloseDate,details;
	String sysdate;

	public String getSysdate() {
			return sysdate;
	}
	public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
	}
	public String getCkBook() {
		return ckBook;
	}
	public void setCkBook(String ckBook) {
		this.ckBook = ckBook;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcNo() {
		return acNo;
	}
	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getIntroducerAcType() {
		return introducerAcType;
	}
	public void setIntroducerAcType(String introducerAcType) {
		this.introducerAcType = introducerAcType;
	}
	public String getIntroducerAcNo() {
		return introducerAcNo;
	}
	public void setIntroducerAcNo(String introducerAcNo) {
		this.introducerAcNo = introducerAcNo;
	}
	
	public String getPbSeq() {
		return pbSeq;
	}
	public void setPbSeq(String pbSeq) {
		this.pbSeq = pbSeq;
	}
	public String getIntPayDate() {
		return intPayDate;
	}
	public void setIntPayDate(String intPayDate) {
		this.intPayDate = intPayDate;
	}
	public String getFreezed() {
		return freezed;
	}
	public void setFreezed(String freezed) {
		this.freezed = freezed;
	}
	public String getInOperative() {
		return inOperative;
	}
	public void setInOperative(String inOperative) {
		this.inOperative = inOperative;
	}
	public String getFreezeReason() {
		return freezeReason;
	}
	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}
	public String getAcOpenDate() {
		return acOpenDate;
	}
	public void setAcOpenDate(String acOpenDate) {
		this.acOpenDate = acOpenDate;
	}
	public String getAcCloseDate() {
		return acCloseDate;
	}
	public void setAcCloseDate(String acCloseDate) {
		this.acCloseDate = acCloseDate;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	
}
