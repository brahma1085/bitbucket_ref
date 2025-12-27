package com.scb.csh.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;
import com.scb.loans.forms.LoanStatusForm;
public class Recieiptsform extends ActionForm
	{
String clearatstart,amtverify,pageId,accounttype,accname,Moduledesc,checkbox,purchase,description,custtype,runnble,clear,scrverify,newscrverify,confirm,receive;
int scrollno,gen_scroll,verify_scroll;
String favour,total,details,custacctype,Moduledesc1,sharecat,lockertype,defaultTab,delete,update,required;
String accountobject,accexist,name,poaccno,abbrv,newlabel,but_value,closed,lk_amount,value,ver_button;
double amount,poamount,commAcc,miniamt,payorderamt,rec_amt;
String tabPaneHeading,deletefun,cur_stock,tab_pane,gettabheading;
String currency;


public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

int accno,custacno;
// currency Exchange

int lbl_thousand,lbl_fivered,lbl_hundred,lbl_fifty,lbl_twenty,lbl_ten,lbl_five,lbl_two,lbl_one;
int r_thousand,r_fivered,r_hundred,r_fifty,r_twenty,r_ten,r_five,r_two,r_one,p_thousand,p_fivered,p_hundred,p_fifty,p_twenty,p_ten,p_five,p_two,p_one;
double r_coins,p_coins,refund,strecamount,cur_total;
double rval_thousand,rval_fivered,rval_hundred,rval_fifty,rval_twenty,rval_ten,rval_five,rval_two,rval_one,rval_coins;
double pval_thousand,pval_fivered,pval_hundred,pval_fifty,pval_twenty,pval_ten,pval_five,pval_two,pval_one,pval_coins;
LoanStatusForm loanstatusform = new LoanStatusForm();
private PageIdForm  pageidentity= new PageIdForm();

public PageIdForm getPageidentity() {
	return pageidentity;
}

public void setPageidentity(PageIdForm pageidentity) {
	this.pageidentity = pageidentity;
}

public LoanStatusForm getLoanstatusform() {
	return loanstatusform;
}

public void setLoanstatusform(LoanStatusForm loanstatusform) {
	this.loanstatusform = loanstatusform;
}

public String getPageId() {
	System.out.println("Form Is Building??????");
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}



public int getScrollno() {
	return scrollno;
}

public void setScrollno(int scrollno) {
	this.scrollno = scrollno;
}

public String getAccounttype() {
	return accounttype;
}

public void setAccounttype(String accounttype) {
	this.accounttype = accounttype;
}



public String getAccname() {
	return accname;
}

public void setAccname(String accname) {
	this.accname = accname;
}



public String getModuledesc() {
	return Moduledesc;
}

public void setModuledesc(String moduledesc) {
	Moduledesc = moduledesc;
}

public String getCheckbox() {
	return checkbox;
}

public void setCheckbox(String checkbox) {
	this.checkbox = checkbox;
}

public String getPurchase() {
	return purchase;
}

public void setPurchase(String purchase) {
	this.purchase = purchase;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public int getCustacno() {
	return custacno;
}

public void setCustacno(int custacno) {
	this.custacno = custacno;
}

public String getCusttype() {
	return custtype;
}

public void setCusttype(String custtype) {
	this.custtype = custtype;
}

public String getFavour() {
	return favour;
}

public void setFavour(String favour) {
	this.favour = favour;
}

public double getCommAcc() {
	return commAcc;
}

public void setCommAcc(double commAcc) {
	this.commAcc = commAcc;
}

public String getTotal() {
	return total;
}

public void setTotal(String total) {
	this.total = total;
}

public String getCustacctype() {
	return custacctype;
}

public void setCustacctype(String custacctype) {
	this.custacctype = custacctype;
}

public String getModuledesc1() {
	return Moduledesc1;
}

public void setModuledesc1(String moduledesc1) {
	Moduledesc1 = moduledesc1;
}

public String getSharecat() {
	return sharecat;
}

public void setSharecat(String sharecat) {
	this.sharecat = sharecat;
}

public String getLockertype() {
	return lockertype;
}

public void setLockertype(String lockertype) {
	this.lockertype = lockertype;
}



public String getAccountobject() {
	return accountobject;
}

public void setAccountobject(String accountobject) {
	this.accountobject = accountobject;
}

public String getAccexist() {
	return accexist;
}

public void setAccexist(String accexist) {
	this.accexist = accexist;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public double getPoamount() {
	return poamount;
}

public void setPoamount(double poamount) {
	this.poamount = poamount;
}

public String getPoaccno() {
	return poaccno;
}

public void setPoaccno(String poaccno) {
	this.poaccno = poaccno;
}

public int getAccno() {
	return accno;
}

public void setAccno(int accno) {
	this.accno = accno;
}

public String getAbbrv() {
	return abbrv;
}

public void setAbbrv(String abbrv) {
	this.abbrv = abbrv;
}

public String getNewlabel() {
	return newlabel;
}

public void setNewlabel(String newlabel) {
	this.newlabel = newlabel;
}

public String getBut_value() {
	return but_value;
}

public void setBut_value(String but_value) {
	this.but_value = but_value;
}

public String getClosed() {
	return closed;
}

public void setClosed(String closed) {
	this.closed = closed;
}



public String getDefaultTab() {
	return defaultTab;
}

public void setDefaultTab(String defaultTab) {
	this.defaultTab = defaultTab;
}

public String getTabPaneHeading() {
	return tabPaneHeading;
}

public void setTabPaneHeading(String tabPaneHeading) {
	this.tabPaneHeading = tabPaneHeading;
}


public int getGen_scroll() {
	return gen_scroll;
}

public void setGen_scroll(int gen_scroll) {
	this.gen_scroll = gen_scroll;
}

public String getLk_amount() {
	return lk_amount;
}

public void setLk_amount(String lk_amount) {
	this.lk_amount = lk_amount;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

public String getVer_button() {
	return ver_button;
}

public void setVer_button(String ver_button) {
	this.ver_button = ver_button;
}

public String getRunnble() {
	return runnble;
}

public void setRunnble(String runnble) {
	this.runnble = runnble;
}

public double getAmount() {
	return amount;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public String getDelete() {
	return delete;
}

public void setDelete(String delete) {
	this.delete = delete;
}

public String getUpdate() {
	return update;
}

public void setUpdate(String update) {
	this.update = update;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}

public double getMiniamt() {
	return miniamt;
}

public void setMiniamt(double miniamt) {
	this.miniamt = miniamt;
}

public String getScrverify() {
	return scrverify;
}

public void setScrverify(String scrverify) {
	this.scrverify = scrverify;
}

public String getNewscrverify() {
	return newscrverify;
}

public void setNewscrverify(String newscrverify) {
	this.newscrverify = newscrverify;
}

public int getVerify_scroll() {
	return verify_scroll;
}

public void setVerify_scroll(int verify_scroll) {
	this.verify_scroll = verify_scroll;
}

public double getPayorderamt() {
	return payorderamt;
}

public void setPayorderamt(double payorderamt) {
	this.payorderamt = payorderamt;
}

public String getConfirm() {
	return confirm;
}

public void setConfirm(String confirm) {
	this.confirm = confirm;
}

public String getReceive() {
	return receive;
}

public void setReceive(String receive) {
	this.receive = receive;
}

public String getDeletefun() {
	return deletefun;
}

public void setDeletefun(String deletefun) {
	this.deletefun = deletefun;
}

public int getLbl_thousand() {
	return lbl_thousand;
}

public void setLbl_thousand(int lbl_thousand) {
	this.lbl_thousand = lbl_thousand;
}

public int getLbl_fivered() {
	return lbl_fivered;
}

public void setLbl_fivered(int lbl_fivered) {
	this.lbl_fivered = lbl_fivered;
}

public int getLbl_hundred() {
	return lbl_hundred;
}

public void setLbl_hundred(int lbl_hundred) {
	this.lbl_hundred = lbl_hundred;
}

public int getLbl_fifty() {
	return lbl_fifty;
}

public void setLbl_fifty(int lbl_fifty) {
	this.lbl_fifty = lbl_fifty;
}

public int getLbl_twenty() {
	return lbl_twenty;
}

public void setLbl_twenty(int lbl_twenty) {
	this.lbl_twenty = lbl_twenty;
}

public int getLbl_ten() {
	return lbl_ten;
}

public void setLbl_ten(int lbl_ten) {
	this.lbl_ten = lbl_ten;
}

public int getLbl_five() {
	return lbl_five;
}

public void setLbl_five(int lbl_five) {
	this.lbl_five = lbl_five;
}

public int getLbl_two() {
	return lbl_two;
}

public void setLbl_two(int lbl_two) {
	this.lbl_two = lbl_two;
}

public int getLbl_one() {
	return lbl_one;
}

public void setLbl_one(int lbl_one) {
	this.lbl_one = lbl_one;
}

public int getR_thousand() {
	return r_thousand;
}

public void setR_thousand(int r_thousand) {
	this.r_thousand = r_thousand;
}

public int getR_fivered() {
	return r_fivered;
}

public void setR_fivered(int r_fivered) {
	this.r_fivered = r_fivered;
}

public int getR_hundred() {
	return r_hundred;
}

public void setR_hundred(int r_hundred) {
	this.r_hundred = r_hundred;
}

public int getR_fifty() {
	return r_fifty;
}

public void setR_fifty(int r_fifty) {
	this.r_fifty = r_fifty;
}

public int getR_twenty() {
	return r_twenty;
}

public void setR_twenty(int r_twenty) {
	this.r_twenty = r_twenty;
}

public int getR_ten() {
	return r_ten;
}

public void setR_ten(int r_ten) {
	this.r_ten = r_ten;
}

public int getR_five() {
	return r_five;
}

public void setR_five(int r_five) {
	this.r_five = r_five;
}

public int getR_two() {
	return r_two;
}

public void setR_two(int r_two) {
	this.r_two = r_two;
}

public int getR_one() {
	return r_one;
}

public void setR_one(int r_one) {
	this.r_one = r_one;
}

public int getP_thousand() {
	return p_thousand;
}

public void setP_thousand(int p_thousand) {
	this.p_thousand = p_thousand;
}

public int getP_fivered() {
	return p_fivered;
}

public void setP_fivered(int p_fivered) {
	this.p_fivered = p_fivered;
}

public int getP_hundred() {
	return p_hundred;
}

public void setP_hundred(int p_hundred) {
	this.p_hundred = p_hundred;
}

public int getP_fifty() {
	return p_fifty;
}

public void setP_fifty(int p_fifty) {
	this.p_fifty = p_fifty;
}

public int getP_twenty() {
	return p_twenty;
}

public void setP_twenty(int p_twenty) {
	this.p_twenty = p_twenty;
}

public int getP_ten() {
	return p_ten;
}

public void setP_ten(int p_ten) {
	this.p_ten = p_ten;
}

public int getP_five() {
	return p_five;
}

public void setP_five(int p_five) {
	this.p_five = p_five;
}

public int getP_two() {
	return p_two;
}

public void setP_two(int p_two) {
	this.p_two = p_two;
}

public int getP_one() {
	return p_one;
}

public void setP_one(int p_one) {
	this.p_one = p_one;
}

public double getR_coins() {
	return r_coins;
}

public void setR_coins(double r_coins) {
	this.r_coins = r_coins;
}

public double getP_coins() {
	return p_coins;
}

public void setP_coins(double p_coins) {
	this.p_coins = p_coins;
}

public double getRefund() {
	return refund;
}

public void setRefund(double refund) {
	this.refund = refund;
}

public double getStrecamount() {
	return strecamount;
}

public void setStrecamount(double strecamount) {
	this.strecamount = strecamount;
}

public double getRval_thousand() {
	return rval_thousand;
}

public void setRval_thousand(double rval_thousand) {
	this.rval_thousand = rval_thousand;
}

public double getRval_fivered() {
	return rval_fivered;
}

public void setRval_fivered(double rval_fivered) {
	this.rval_fivered = rval_fivered;
}

public double getRval_hundred() {
	return rval_hundred;
}

public void setRval_hundred(double rval_hundred) {
	this.rval_hundred = rval_hundred;
}

public double getRval_fifty() {
	return rval_fifty;
}

public void setRval_fifty(double rval_fifty) {
	this.rval_fifty = rval_fifty;
}

public double getRval_twenty() {
	return rval_twenty;
}

public void setRval_twenty(double rval_twenty) {
	this.rval_twenty = rval_twenty;
}

public double getRval_ten() {
	return rval_ten;
}

public void setRval_ten(double rval_ten) {
	this.rval_ten = rval_ten;
}

public double getRval_five() {
	return rval_five;
}

public void setRval_five(double rval_five) {
	this.rval_five = rval_five;
}

public double getRval_two() {
	return rval_two;
}

public void setRval_two(double rval_two) {
	this.rval_two = rval_two;
}

public double getRval_one() {
	return rval_one;
}

public void setRval_one(double rval_one) {
	this.rval_one = rval_one;
}

public double getRval_coins() {
	return rval_coins;
}

public void setRval_coins(double rval_coins) {
	this.rval_coins = rval_coins;
}

public double getPval_thousand() {
	return pval_thousand;
}

public void setPval_thousand(double pval_thousand) {
	this.pval_thousand = pval_thousand;
}

public double getPval_fivered() {
	return pval_fivered;
}

public void setPval_fivered(double pval_fivered) {
	this.pval_fivered = pval_fivered;
}

public double getPval_hundred() {
	return pval_hundred;
}

public void setPval_hundred(double pval_hundred) {
	this.pval_hundred = pval_hundred;
}

public double getPval_fifty() {
	return pval_fifty;
}

public void setPval_fifty(double pval_fifty) {
	this.pval_fifty = pval_fifty;
}

public double getPval_twenty() {
	return pval_twenty;
}

public void setPval_twenty(double pval_twenty) {
	this.pval_twenty = pval_twenty;
}

public double getPval_ten() {
	return pval_ten;
}

public void setPval_ten(double pval_ten) {
	this.pval_ten = pval_ten;
}

public double getPval_five() {
	return pval_five;
}

public void setPval_five(double pval_five) {
	this.pval_five = pval_five;
}

public double getPval_two() {
	return pval_two;
}

public void setPval_two(double pval_two) {
	this.pval_two = pval_two;
}

public double getPval_one() {
	return pval_one;
}

public void setPval_one(double pval_one) {
	this.pval_one = pval_one;
}

public double getPval_coins() {
	return pval_coins;
}

public void setPval_coins(double pval_coins) {
	this.pval_coins = pval_coins;
}

public String getRequired() {
	return required;
}

public void setRequired(String required) {
	this.required = required;
}

public String getCur_stock() {
	return cur_stock;
}

public void setCur_stock(String cur_stock) {
	this.cur_stock = cur_stock;
}

public double getCur_total() {
	return cur_total;
}

public void setCur_total(double cur_total) {
	this.cur_total = cur_total;
}

public String getTab_pane() {
	return tab_pane;
}

public void setTab_pane(String tab_pane) {
	this.tab_pane = tab_pane;
}

public String getGettabheading() {
	return gettabheading;
}

public void setGettabheading(String gettabheading) {
	this.gettabheading = gettabheading;
}

public double getRec_amt() {
	return rec_amt;
}

public void setRec_amt(double rec_amt) {
	this.rec_amt = rec_amt;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

public String getAmtverify() {
	return amtverify;
}

public void setAmtverify(String amtverify) {
	this.amtverify = amtverify;
}

public String getClearatstart() {
	return clearatstart;
}

public void setClearatstart(String clearatstart) {
	this.clearatstart = clearatstart;
}



}
