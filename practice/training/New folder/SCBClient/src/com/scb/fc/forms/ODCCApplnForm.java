package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class ODCCApplnForm  extends ActionForm
{
	String pageId,acType,ac_no,combo_share_type,txt_sh_type,name,details,hidval;
	String chqbook,nom,scrollNum,rcptname,date,amount,surityacno,sacType;
	String purpose,appdate,loanamount,paymode,intType,intcalc,srlno,typeofoperation;
	//for Business
	String businessName,businessAddr,businessNature,stockValue,goodsType,goodsCondition,turnover;
	String businessPhno,busMonthlyIncome,busMonthlyExpenditure,busNetMonthlyIncome;
	//For Deposit details
	String dacType,dacno,p,dpacno,dpcheck,del,first,cobor,cobortext,coborActype,coboracno;
	String chk1,coborActype1,cobor1,chk2,coborActype2,cobor2,chk3,coborActype3,cobor3,cocheckbox;
	String countchk;
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessAddr() {
		return businessAddr;
	}

	public void setBusinessAddr(String businessAddr) {
		this.businessAddr = businessAddr;
	}

	public String getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public String getStockValue() {
		return stockValue;
	}

	public void setStockValue(String stockValue) {
		this.stockValue = stockValue;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsCondition() {
		return goodsCondition;
	}

	public void setGoodsCondition(String goodsCondition) {
		this.goodsCondition = goodsCondition;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getBusinessPhno() {
		return businessPhno;
	}

	public void setBusinessPhno(String businessPhno) {
		this.businessPhno = businessPhno;
	}

	public String getBusMonthlyIncome() {
		return busMonthlyIncome;
	}

	public void setBusMonthlyIncome(String busMonthlyIncome) {
		this.busMonthlyIncome = busMonthlyIncome;
	}

	public String getBusMonthlyExpenditure() {
		return busMonthlyExpenditure;
	}

	public void setBusMonthlyExpenditure(String busMonthlyExpenditure) {
		this.busMonthlyExpenditure = busMonthlyExpenditure;
	}

	public String getBusNetMonthlyIncome() {
		return busNetMonthlyIncome;
	}

	public void setBusNetMonthlyIncome(String busNetMonthlyIncome) {
		this.busNetMonthlyIncome = busNetMonthlyIncome;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAppdate() {
		return appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getIntcalc() {
		return intcalc;
	}

	public void setIntcalc(String intcalc) {
		this.intcalc = intcalc;
	}

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

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public String getCombo_share_type() {
		return combo_share_type;
	}

	public void setCombo_share_type(String combo_share_type) {
		this.combo_share_type = combo_share_type;
	}

	public String getTxt_sh_type() {
		return txt_sh_type;
	}

	public void setTxt_sh_type(String txt_sh_type) {
		this.txt_sh_type = txt_sh_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getHidval() {
		return hidval;
	}

	public void setHidval(String hidval) {
		this.hidval = hidval;
	}

	public String getChqbook() {
		return chqbook;
	}

	public void setChqbook(String chqbook) {
		this.chqbook = chqbook;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getScrollNum() {
		return scrollNum;
	}

	public void setScrollNum(String scrollNum) {
		this.scrollNum = scrollNum;
	}

	public String getRcptname() {
		return rcptname;
	}

	public void setRcptname(String rcptname) {
		this.rcptname = rcptname;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSrlno() {
		return srlno;
	}

	public void setSrlno(String srlno) {
		this.srlno = srlno;
	}

	public String getTypeofoperation() {
		return typeofoperation;
	}

	public void setTypeofoperation(String typeofoperation) {
		this.typeofoperation = typeofoperation;
	}

	public String getSurityacno() {
		return surityacno;
	}

	public void setSurityacno(String surityacno) {
		this.surityacno = surityacno;
	}

	public String getSacType() {
		return sacType;
	}

	public void setSacType(String sacType) {
		this.sacType = sacType;
	}

	public String getDacType() {
		return dacType;
	}

	public void setDacType(String dacType) {
		this.dacType = dacType;
	}

	public String getDacno() {
		return dacno;
	}

	public void setDacno(String dacno) {
		this.dacno = dacno;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getCobor() {
		return cobor;
	}

	public void setCobor(String cobor) {
		this.cobor = cobor;
	}

	public String getCobortext() {
		return cobortext;
	}

	public void setCobortext(String cobortext) {
		this.cobortext = cobortext;
	}

	public String getCoborActype() {
		return coborActype;
	}

	public void setCoborActype(String coborActype) {
		this.coborActype = coborActype;
	}

	public String getCoboracno() {
		return coboracno;
	}

	public void setCoboracno(String coboracno) {
		this.coboracno = coboracno;
	}

	public String getChk1() {
		return chk1;
	}

	public void setChk1(String chk1) {
		this.chk1 = chk1;
	}

	public String getCoborActype1() {
		return coborActype1;
	}

	public void setCoborActype1(String coborActype1) {
		this.coborActype1 = coborActype1;
	}

	public String getCobor1() {
		return cobor1;
	}

	public void setCobor1(String cobor1) {
		this.cobor1 = cobor1;
	}

	public String getChk2() {
		return chk2;
	}

	public void setChk2(String chk2) {
		this.chk2 = chk2;
	}

	public String getCoborActype2() {
		return coborActype2;
	}

	public void setCoborActype2(String coborActype2) {
		this.coborActype2 = coborActype2;
	}

	public String getCobor2() {
		return cobor2;
	}

	public void setCobor2(String cobor2) {
		this.cobor2 = cobor2;
	}

	public String getChk3() {
		return chk3;
	}

	public void setChk3(String chk3) {
		this.chk3 = chk3;
	}

	public String getCoborActype3() {
		return coborActype3;
	}

	public void setCoborActype3(String coborActype3) {
		this.coborActype3 = coborActype3;
	}

	public String getCobor3() {
		return cobor3;
	}

	public void setCobor3(String cobor3) {
		this.cobor3 = cobor3;
	}

	public String getCocheckbox() {
		return cocheckbox;
	}

	public void setCocheckbox(String cocheckbox) {
		this.cocheckbox = cocheckbox;
	}

	public String getCountchk() {
		return countchk;
	}

	public void setCountchk(String countchk) {
		this.countchk = countchk;
	}

}
