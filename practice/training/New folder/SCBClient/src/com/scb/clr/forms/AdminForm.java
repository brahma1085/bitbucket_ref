package com.scb.clr.forms;

import org.apache.struts.action.ActionForm;

public class AdminForm extends ActionForm
{
    private String adminModules,pageId,flag,validateFlag;
    private String branchName,shortName,branchTyp,branchACTyp,branchACNum,branchAddress,glType,glCode;
    private String bankAbbr,bankCode,bankName,deTml,deUser,deDate,deTime;
    private String accTyp,accNum,veTml,veUser,VeDate,veTime;
    private String linkDiscription,reasonCode,bounceCode,fine,returnFine,mailingChg,smsChg,emailChg;
    private String fromAmt,toAmt,fineAmt,int_rate,int_type;
	private String accountType,BrhCode,companyName,branchCode,companyCode;
	
    private String code,reason;

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAdminModules() {
		return adminModules;
	}

	public void setAdminModules(String adminModules) {
		this.adminModules = adminModules;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	

	public String getBrhCode() {
		return BrhCode;
	}

	public void setBrhCode(String brhCode) {
		BrhCode = brhCode;
	}

	
	

	

	

	

	

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getGlType() {
		return glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getBankAbbr() {
		return bankAbbr;
	}

	public void setBankAbbr(String bankAbbr) {
		this.bankAbbr = bankAbbr;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDeTime() {
		return deTime;
	}

	public void setDeTime(String deTime) {
		this.deTime = deTime;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBranchTyp() {
		return branchTyp;
	}

	public void setBranchTyp(String branchTyp) {
		this.branchTyp = branchTyp;
	}

	public String getBranchACTyp() {
		return branchACTyp;
	}

	public void setBranchACTyp(String branchACTyp) {
		this.branchACTyp = branchACTyp;
	}

	public String getBranchACNum() {
		return branchACNum;
	}

	public void setBranchACNum(String branchACNum) {
		this.branchACNum = branchACNum;
	}

	public String getDeTml() {
		return deTml;
	}

	public void setDeTml(String deTml) {
		this.deTml = deTml;
	}

	public String getDeUser() {
		return deUser;
	}

	public void setDeUser(String deUser) {
		this.deUser = deUser;
	}

	public String getDeDate() {
		return deDate;
	}

	public void setDeDate(String deDate) {
		this.deDate = deDate;
	}

	

	public String getVeDate() {
		return VeDate;
	}

	public void setVeDate(String veDate) {
		VeDate = veDate;
	}

	public String getLinkDiscription() {
		return linkDiscription;
	}

	public void setLinkDiscription(String linkDiscription) {
		this.linkDiscription = linkDiscription;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getBounceCode() {
		return bounceCode;
	}

	public void setBounceCode(String bounceCode) {
		this.bounceCode = bounceCode;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public String getReturnFine() {
		return returnFine;
	}

	public void setReturnFine(String returnFine) {
		this.returnFine = returnFine;
	}

	public String getMailingChg() {
		return mailingChg;
	}

	public void setMailingChg(String mailingChg) {
		this.mailingChg = mailingChg;
	}

	public String getSmsChg() {
		return smsChg;
	}

	public void setSmsChg(String smsChg) {
		this.smsChg = smsChg;
	}

	public String getEmailChg() {
		return emailChg;
	}

	public void setEmailChg(String emailChg) {
		this.emailChg = emailChg;
	}

	public String getFromAmt() {
		return fromAmt;
	}

	public void setFromAmt(String fromAmt) {
		this.fromAmt = fromAmt;
	}

	public String getToAmt() {
		return toAmt;
	}

	public void setToAmt(String toAmt) {
		this.toAmt = toAmt;
	}

	public String getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(String fineAmt) {
		this.fineAmt = fineAmt;
	}

	public String getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(String int_rate) {
		this.int_rate = int_rate;
	}

	public String getInt_type() {
		return int_type;
	}

	public void setInt_type(String int_type) {
		this.int_type = int_type;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getAccTyp() {
		return accTyp;
	}

	public void setAccTyp(String accTyp) {
		this.accTyp = accTyp;
	}

	public String getAccNum() {
		return accNum;
	}

	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	public String getVeTml() {
		return veTml;
	}

	public void setVeTml(String veTml) {
		this.veTml = veTml;
	}

	public String getVeUser() {
		return veUser;
	}

	public void setVeUser(String veUser) {
		this.veUser = veUser;
	}

	public String getVeTime() {
		return veTime;
	}

	public void setVeTime(String veTime) {
		this.veTime = veTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	

}
