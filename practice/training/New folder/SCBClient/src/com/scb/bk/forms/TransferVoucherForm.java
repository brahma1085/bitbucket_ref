package com.scb.bk.forms;

import org.apache.struts.action.ActionForm;

public class TransferVoucherForm extends ActionForm{

	private String forward,flag,cbox;
	private String pageId,valid,lk_amount,ac_holder;
	private String date,cdindicator,narration,gltype,glcode,totalcreditamt,totaldebitamt,amount;
	private String gldesc,acctype,cdind,closebal;
	private int vchnum,accno;
	String creditTot,debitTot,voucherno,clear;
	private String vouchervalue;
	String tab_gltype,tab_glcode,tab_acctyp,tab_accno,cd_ind,tab_amt,tab_edit,tab_del;
	String closed, accountobject,accexist,miniamt,scrverify,newscrverify,debitamt,creditamt;
	int check_accno1,check_accno2,check_accno3,check_accno4,check_accno5;
	String t_type,trn_tpe1,addbutton1,cdind1,checkbox1,check_acctype1,check_glcode1,check_gltype1,check_amt1;
	String trn_tpe2,addbutton2,cdind2,checkbox2,check_acctype2,check_glcode2,check_gltype2,check_amt2;
	String trn_tpe3,addbutton3,cdind3,checkbox3,check_acctype3,check_glcode3,check_gltype3,check_amt3;
	String trn_tpe4,addbutton4,cdind4,checkbox4,check_acctype4,check_glcode4,check_gltype4,check_amt4;
	String trn_tpe5,addbutton5,cdind5,checkbox5,check_acctype5,check_glcode5,check_gltype5,check_amt5;
	String sysdate;
	
	public String getSysdate() {
		return sysdate;
	}


	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}






	public String getForward() {
		return forward;
	}
	public String getAddbutton1() {
		return addbutton1;
	}
	public void setAddbutton1(String addbutton1) {
		this.addbutton1 = addbutton1;
	}
	public String getAddbutton2() {
		return addbutton2;
	}
	public void setAddbutton2(String addbutton2) {
		this.addbutton2 = addbutton2;
	}
	public String getAddbutton3() {
		return addbutton3;
	}
	public void setAddbutton3(String addbutton3) {
		this.addbutton3 = addbutton3;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCdindicator() {
		return cdindicator;
	}
	public void setCdindicator(String cdindicator) {
		this.cdindicator = cdindicator;
	}
	public String getCreditamt() {
		return creditamt;
	}
	public void setCreditamt(String creditamt) {
		this.creditamt = creditamt;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getDebitamt() {
		return debitamt;
	}
	public void setDebitamt(String debitamt) {
		this.debitamt = debitamt;
	}
	public String getGltype() {
		return gltype;
	}
	public void setGltype(String gltype) {
		this.gltype = gltype;
	}
	public String getGlcode() {
		return glcode;
	}
	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}
	public String getTotalcreditamt() {
		return totalcreditamt;
	}
	public void setTotalcreditamt(String totalcreditamt) {
		this.totalcreditamt = totalcreditamt;
	}
	public String getTotaldebitamt() {
		return totaldebitamt;
	}
	public void setTotaldebitamt(String totaldebitamt) {
		this.totaldebitamt = totaldebitamt;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGldesc() {
		return gldesc;
	}
	public void setGldesc(String gldesc) {
		this.gldesc = gldesc;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	
	public String getCdind() {
		return cdind;
	}
	public void setCdind(String cdind) {
		this.cdind = cdind;
	}
	public String getClosebal() {
		return closebal;
	}
	public void setClosebal(String closebal) {
		this.closebal = closebal;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public int getVchnum() {
		return vchnum;
	}
	public void setVchnum(int vchnum) {
		this.vchnum = vchnum;
	}
	public String getVouchervalue() {
		return vouchervalue;
	}
	public void setVouchervalue(String vouchervalue) {
		this.vouchervalue = vouchervalue;
	}
	public String getAccexist() {
		return accexist;
	}
	public void setAccexist(String accexist) {
		this.accexist = accexist;
	}
	public String getLk_amount() {
		return lk_amount;
	}
	public void setLk_amount(String lk_amount) {
		this.lk_amount = lk_amount;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	public String getAccountobject() {
		return accountobject;
	}
	public void setAccountobject(String accountobject) {
		this.accountobject = accountobject;
	}
	public String getMiniamt() {
		return miniamt;
	}
	public void setMiniamt(String miniamt) {
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
	public String getTab_gltype() {
		return tab_gltype;
	}
	public void setTab_gltype(String tab_gltype) {
		this.tab_gltype = tab_gltype;
	}
	public String getTab_glcode() {
		return tab_glcode;
	}
	public void setTab_glcode(String tab_glcode) {
		this.tab_glcode = tab_glcode;
	}
	public String getTab_acctyp() {
		return tab_acctyp;
	}
	public void setTab_acctyp(String tab_acctyp) {
		this.tab_acctyp = tab_acctyp;
	}
	public String getTab_accno() {
		return tab_accno;
	}
	public void setTab_accno(String tab_accno) {
		this.tab_accno = tab_accno;
	}

	public String getCd_ind() {
		return cd_ind;
	}
	public void setCd_ind(String cd_ind) {
		this.cd_ind = cd_ind;
	}
	public String getTab_amt() {
		return tab_amt;
	}
	public void setTab_amt(String tab_amt) {
		this.tab_amt = tab_amt;
	}
	public String getTab_edit() {
		return tab_edit;
	}
	public void setTab_edit(String tab_edit) {
		this.tab_edit = tab_edit;
	}
	public String getTab_del() {
		return tab_del;
	}
	public void setTab_del(String tab_del) {
		this.tab_del = tab_del;
	}
	public String getCheck_acctype1() {
		return check_acctype1;
	}
	public void setCheck_acctype1(String check_acctype1) {
		this.check_acctype1 = check_acctype1;
	}
	public int getCheck_accno1() {
		return check_accno1;
	}
	public void setCheck_accno1(int check_accno1) {
		this.check_accno1 = check_accno1;
	}
	public String getCheck_glcode1() {
		return check_glcode1;
	}
	public void setCheck_glcode1(String check_glcode1) {
		this.check_glcode1 = check_glcode1;
	}
	public String getCheck_gltype1() {
		return check_gltype1;
	}
	public void setCheck_gltype1(String check_gltype1) {
		this.check_gltype1 = check_gltype1;
	}
	public String getCheck_amt1() {
		return check_amt1;
	}
	public void setCheck_amt1(String check_amt1) {
		this.check_amt1 = check_amt1;
	}
	public String getCheck_acctype2() {
		return check_acctype2;
	}
	public void setCheck_acctype2(String check_acctype2) {
		this.check_acctype2 = check_acctype2;
	}
	public int getCheck_accno2() {
		return check_accno2;
	}
	public void setCheck_accno2(int check_accno2) {
		this.check_accno2 = check_accno2;
	}
	public String getCheck_glcode2() {
		return check_glcode2;
	}
	public void setCheck_glcode2(String check_glcode2) {
		this.check_glcode2 = check_glcode2;
	}
	public String getCheck_gltype2() {
		return check_gltype2;
	}
	public void setCheck_gltype2(String check_gltype2) {
		this.check_gltype2 = check_gltype2;
	}
	public String getCheck_amt2() {
		return check_amt2;
	}
	public void setCheck_amt2(String check_amt2) {
		this.check_amt2 = check_amt2;
	}
	public String getCheck_acctype3() {
		return check_acctype3;
	}
	public void setCheck_acctype3(String check_acctype3) {
		this.check_acctype3 = check_acctype3;
	}
	public int getCheck_accno3() {
		return check_accno3;
	}
	public void setCheck_accno3(int check_accno3) {
		this.check_accno3 = check_accno3;
	}
	public String getCheck_glcode3() {
		return check_glcode3;
	}
	public void setCheck_glcode3(String check_glcode3) {
		this.check_glcode3 = check_glcode3;
	}
	public String getCheck_gltype3() {
		return check_gltype3;
	}
	public void setCheck_gltype3(String check_gltype3) {
		this.check_gltype3 = check_gltype3;
	}
	public String getCheck_amt3() {
		return check_amt3;
	}
	public void setCheck_amt3(String check_amt3) {
		this.check_amt3 = check_amt3;
	}
	public String getCheck_acctype4() {
		return check_acctype4;
	}
	public void setCheck_acctype4(String check_acctype4) {
		this.check_acctype4 = check_acctype4;
	}
	public int getCheck_accno4() {
		return check_accno4;
	}
	public void setCheck_accno4(int check_accno4) {
		this.check_accno4 = check_accno4;
	}
	public String getCheck_glcode4() {
		return check_glcode4;
	}
	public void setCheck_glcode4(String check_glcode4) {
		this.check_glcode4 = check_glcode4;
	}
	public String getCheck_gltype4() {
		return check_gltype4;
	}
	public void setCheck_gltype4(String check_gltype4) {
		this.check_gltype4 = check_gltype4;
	}
	public String getCheck_amt4() {
		return check_amt4;
	}
	public void setCheck_amt4(String check_amt4) {
		this.check_amt4 = check_amt4;
	}
	public String getCheck_acctype5() {
		return check_acctype5;
	}
	public void setCheck_acctype5(String check_acctype5) {
		this.check_acctype5 = check_acctype5;
	}
	public int getCheck_accno5() {
		return check_accno5;
	}
	public void setCheck_accno5(int check_accno5) {
		this.check_accno5 = check_accno5;
	}
	public String getCheck_glcode5() {
		return check_glcode5;
	}
	public void setCheck_glcode5(String check_glcode5) {
		this.check_glcode5 = check_glcode5;
	}
	public String getCheck_gltype5() {
		return check_gltype5;
	}
	public void setCheck_gltype5(String check_gltype5) {
		this.check_gltype5 = check_gltype5;
	}
	public String getCheck_amt5() {
		return check_amt5;
	}
	public void setCheck_amt5(String check_amt5) {
		this.check_amt5 = check_amt5;
	}
	public String getCheckbox1() {
		return checkbox1;
	}
	public void setCheckbox1(String checkbox1) {
		this.checkbox1 = checkbox1;
	}
	public String getCheckbox2() {
		return checkbox2;
	}
	public void setCheckbox2(String checkbox2) {
		this.checkbox2 = checkbox2;
	}
	public String getCheckbox3() {
		return checkbox3;
	}
	public void setCheckbox3(String checkbox3) {
		this.checkbox3 = checkbox3;
	}
	public String getCheckbox4() {
		return checkbox4;
	}
	public void setCheckbox4(String checkbox4) {
		this.checkbox4 = checkbox4;
	}
	public String getCheckbox5() {
		return checkbox5;
	}
	public void setCheckbox5(String checkbox5) {
		this.checkbox5 = checkbox5;
	}
	public String getTrn_tpe1() {
		return trn_tpe1;
	}
	public void setTrn_tpe1(String trn_tpe1) {
		this.trn_tpe1 = trn_tpe1;
	}

	public String getTrn_tpe2() {
		return trn_tpe2;
	}
	public void setTrn_tpe2(String trn_tpe2) {
		this.trn_tpe2 = trn_tpe2;
	}
	public String getTrn_tpe3() {
		return trn_tpe3;
	}
	public void setTrn_tpe3(String trn_tpe3) {
		this.trn_tpe3 = trn_tpe3;
	}
	public String getTrn_tpe4() {
		return trn_tpe4;
	}
	public void setTrn_tpe4(String trn_tpe4) {
		this.trn_tpe4 = trn_tpe4;
	}
	public String getTrn_tpe5() {
		return trn_tpe5;
	}
	public void setTrn_tpe5(String trn_tpe5) {
		this.trn_tpe5 = trn_tpe5;
	}
	public String getCbox() {
		return cbox;
	}
	public void setCbox(String cbox) {
		this.cbox = cbox;
	}
	public String getCreditTot() {
		return creditTot;
	}
	public void setCreditTot(String creditTot) {
		this.creditTot = creditTot;
	}
	public String getDebitTot() {
		return debitTot;
	}
	public void setDebitTot(String debitTot) {
		this.debitTot = debitTot;
	}
	public String getCdind1() {
		return cdind1;
	}
	public void setCdind1(String cdind1) {
		this.cdind1 = cdind1;
	}
	public String getCdind2() {
		return cdind2;
	}
	public void setCdind2(String cdind2) {
		this.cdind2 = cdind2;
	}
	public String getCdind3() {
		return cdind3;
	}
	public void setCdind3(String cdind3) {
		this.cdind3 = cdind3;
	}
	public String getCdind4() {
		return cdind4;
	}
	public void setCdind4(String cdind4) {
		this.cdind4 = cdind4;
	}
	public String getCdind5() {
		return cdind5;
	}
	public void setCdind5(String cdind5) {
		this.cdind5 = cdind5;
	}
	public String getT_type() {
		return t_type;
	}
	public void setT_type(String t_type) {
		this.t_type = t_type;
	}
	public String getVoucherno() {
		return voucherno;
	}
	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}
	public String getClear() {
		return clear;
	}
	public void setClear(String clear) {
		this.clear = clear;
	}
	public String getAc_holder() {
		return ac_holder;
	}
	public void setAc_holder(String ac_holder) {
		this.ac_holder = ac_holder;
	}


	
	
}

