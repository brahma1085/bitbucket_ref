package com.scb.td.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.scb.pd.forms.NomineeDetailsForm;

public class ApplnDE extends ActionForm{
	
	
  private String ac_type,intro_ac_type,intro_ac_no,pageId,dep_date,mat_date,date;
  private int period_of_days,dep_amt,ac_no,intFREQ,cid;
  private String amt_recv,pay_mode,pay_ac_type,label_name,cust_name,pay_ac_typetwo;
  private String combo_autorenewal,details,combo_mat_cat,amt_recv_ac_no;
  private String interest_freq,int_payable;
  private double int_rate,balance_amt,mat_amt;
  private int scroll_no,trf_acno,extra_type,pay_mode_ac_no,control_no,accountgen,validate,amd_det;
  private double amount,dep_amttwo;
  private String forward,testing,hidval,amou,mysub,first;
  private String butt_submit,butt_update,butt_clear,butt_del,freqint;
  private String trf_actype,acc_holder_name,detail;
  private String utml,udate,uid,verified,pvr_date,new_dpamt,ac_type_name,commad,intfreqee;
  private int value,account_number;
  private String account_type;
  public String getAccount_type() {
	return account_type;
}
public void setAccount_type(String accountType) {
	account_type = accountType;
}
public int getAccount_number() {
	return account_number;
}
public void setAccount_number(int accountNumber) {
	account_number = accountNumber;
}
private long period_of_daystwo;
  private String ver; 
  
  public String getVer() {
	return ver;
}
public void setVer(String ver) {
	this.ver = ver;
}
//Nominee data members
  NomineeDetailsForm nomform=new NomineeDetailsForm();
  private String  nompageid,cidis;
  private String acno,actype,nomname,dob,gender,address,rel_ship,percentage,nomforward,issuedate,has_ac,nomvalidations;
  
  //Joint Holder data members
  private int jointnum,jcid0,jcid1,jcid2,jcid3,jcid4,jcid5,jcid6,jcid7,jcid8,jcid9,jcid10,primejoint;
  public int getJcid0() {
	return jcid0;
}
public void setJcid0(int jcid0) {
	this.jcid0 = jcid0;
}
private String custname;
  
public String getCustname() {
	return custname;
}
public void setCustname(String custname) {
	this.custname = custname;
}
public int getPrimejoint() {
	return primejoint;
}
public void setPrimejoint(int primejoint) {
	this.primejoint = primejoint;
}

public int getJcid1() {
	return jcid1;
}
public void setJcid1(int jcid1) {
	this.jcid1 = jcid1;
}
public int getJcid2() {
	return jcid2;
}
public void setJcid2(int jcid2) {
	this.jcid2 = jcid2;
}
public int getJcid3() {
	return jcid3;
}
public void setJcid3(int jcid3) {
	this.jcid3 = jcid3;
}
public int getJcid4() {
	return jcid4;
}
public void setJcid4(int jcid4) {
	this.jcid4 = jcid4;
}
public int getJcid5() {
	return jcid5;
}
public void setJcid5(int jcid5) {
	this.jcid5 = jcid5;
}
public int getJcid6() {
	return jcid6;
}
public void setJcid6(int jcid6) {
	this.jcid6 = jcid6;
}
public int getJcid7() {
	return jcid7;
}
public void setJcid7(int jcid7) {
	this.jcid7 = jcid7;
}
public int getJcid8() {
	return jcid8;
}
public void setJcid8(int jcid8) {
	this.jcid8 = jcid8;
}
public int getJcid9() {
	return jcid9;
}
public void setJcid9(int jcid9) {
	this.jcid9 = jcid9;
}
public int getJcid10() {
	return jcid10;
}
public void setJcid10(int jcid10) {
	this.jcid10 = jcid10;
}
public int getJointnum() {
	return jointnum;
}
public void setJointnum(int jointnum) {
	this.jointnum = jointnum;
}
public NomineeDetailsForm getNomform() {
	return nomform;
}
public void setNomform(NomineeDetailsForm nomform) {
	this.nomform = nomform;
}
public String getNompageid() {
	return nompageid;
}
public void setNompageid(String nompageid) {
	this.nompageid = nompageid;
}
public String getCidis() {
	return cidis;
}
public void setCidis(String cidis) {
	this.cidis = cidis;
}
public String getAcno() {
	return acno;
}
public void setAcno(String acno) {
	this.acno = acno;
}
public String getActype() {
	return actype;
}
public void setActype(String actype) {
	this.actype = actype;
}
public String getNomname() {
	return nomname;
}
public void setNomname(String nomname) {
	this.nomname = nomname;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getRel_ship() {
	return rel_ship;
}
public void setRel_ship(String relShip) {
	rel_ship = relShip;
}
public String getPercentage() {
	return percentage;
}
public void setPercentage(String percentage) {
	this.percentage = percentage;
}
public String getNomforward() {
	return nomforward;
}
public void setNomforward(String nomforward) {
	this.nomforward = nomforward;
}
public String getIssuedate() {
	return issuedate;
}
public void setIssuedate(String issuedate) {
	this.issuedate = issuedate;
}
public String getHas_ac() {
	return has_ac;
}
public void setHas_ac(String hasAc) {
	has_ac = hasAc;
}
public String getNomvalidations() {
	return nomvalidations;
}
public void setNomvalidations(String nomvalidations) {
	this.nomvalidations = nomvalidations;
}
public long getPeriod_of_daystwo() {
	return period_of_daystwo;
}
public void setPeriod_of_daystwo(long period_of_daystwo) {
	this.period_of_daystwo = period_of_daystwo;
}
public int getValue() {
	return value;
}
public void setValue(int value) {
	this.value = value;
}
public String getButt_submit() {
	return butt_submit;
}
public void setButt_submit(String butt_submit) {
	this.butt_submit = butt_submit;
}
public String getButt_update() {
	return butt_update;
}
public void setButt_update(String butt_update) {
	this.butt_update = butt_update;
}
public String getButt_clear() {
	return butt_clear;
}
public void setButt_clear(String butt_clear) {
	this.butt_clear = butt_clear;
}
public String getButt_del() {
	return butt_del;
}
public void setButt_del(String butt_del) {
	this.butt_del = butt_del;
}

public String getAc_type() {
	return ac_type;
}
public void setAc_type(String ac_type) {
	this.ac_type = ac_type;
}
public String getIntro_ac_type() {
	return intro_ac_type;
}
public void setIntro_ac_type(String intro_ac_type) {
	this.intro_ac_type = intro_ac_type;
}
public int getPeriod_of_days() {
	return period_of_days;
}
public void setPeriod_of_days(int period_of_days) {
	this.period_of_days = period_of_days;
}


public double getMat_amt() {
	return mat_amt;
}
public void setMat_amt(double mat_amt) {
	this.mat_amt = mat_amt;
}
public int getDep_amt() {
	return dep_amt;
}
public void setDep_amt(int dep_amt) {
	this.dep_amt = dep_amt;
}
public String getAmt_recv() {
	return amt_recv;
}
public void setAmt_recv(String amt_recv) {
	this.amt_recv = amt_recv;
}
public String getPay_mode() {
	return pay_mode;
}
public void setPay_mode(String pay_mode) {
	this.pay_mode = pay_mode;
}

public String getPageId() {
	return pageId;
}
public void setPageId(String pageId) {
	this.pageId = pageId;
}


public String getDep_date() {
	return dep_date;
}
public void setDep_date(String dep_date) {
	this.dep_date = dep_date;
}
public String getMat_date() {
	return mat_date;
}
public void setMat_date(String mat_date) {
	this.mat_date = mat_date;
}
public String getCombo_autorenewal() {
	return combo_autorenewal;
}
public void setCombo_autorenewal(String combo_autorenewal) {
	this.combo_autorenewal = combo_autorenewal;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}
public String getPay_ac_type() {
	return pay_ac_type;
}
public void setPay_ac_type(String pay_ac_type) {
	this.pay_ac_type = pay_ac_type;
}
public String getCombo_mat_cat() {
	return combo_mat_cat;
}
public void setCombo_mat_cat(String combo_mat_cat) {
	this.combo_mat_cat = combo_mat_cat;
}
public String getIntro_ac_no() {
	return intro_ac_no;
}
public void setIntro_ac_no(String intro_ac_no) {
	this.intro_ac_no = intro_ac_no;
}
public String getAmt_recv_ac_no() {
	return amt_recv_ac_no;
}
public void setAmt_recv_ac_no(String amt_recv_ac_no) {
	this.amt_recv_ac_no = amt_recv_ac_no;
}
public String getLabel_name() {
	return label_name;
}
public void setLabel_name(String label_name) {
	this.label_name = label_name;
}
public String getInterest_freq() {
	return interest_freq;
}
public void setInterest_freq(String interest_freq) {
	this.interest_freq = interest_freq;
}

public String getInt_payable() {
	return int_payable;
}
public void setInt_payable(String int_payable) {
	this.int_payable = int_payable;
}

public double getInt_rate() {
	return int_rate;
}
public void setInt_rate(double int_rate) {
	this.int_rate = int_rate;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getScroll_no() {
	return scroll_no;
}
public void setScroll_no(int scroll_no) {
	this.scroll_no = scroll_no;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getForward() {
	return forward;
}
public void setForward(String forward) {
	this.forward = forward;
}
public int getAc_no() {
	return ac_no;
}
public void setAc_no(int ac_no) {
	this.ac_no = ac_no;
}
public int getExtra_type() {
	return extra_type;
}
public void setExtra_type(int extra_type) {
	this.extra_type = extra_type;
}
public int getPay_mode_ac_no() {
	return pay_mode_ac_no;
}
public void setPay_mode_ac_no(int pay_mode_ac_no) {
	this.pay_mode_ac_no = pay_mode_ac_no;
}
public int getControl_no() {
	return control_no;
}
public void setControl_no(int control_no) {
	this.control_no = control_no;
}
public int getTrf_acno() {
	return trf_acno;
}
public void setTrf_acno(int trf_acno) {
	this.trf_acno = trf_acno;
}
public String getTrf_actype() {
	return trf_actype;
}
public void setTrf_actype(String trf_actype) {
	this.trf_actype = trf_actype;
}
public double getBalance_amt() {
	return balance_amt;
}
public void setBalance_amt(double balance_amt) {
	this.balance_amt = balance_amt;
}
public String getAcc_holder_name() {
	return acc_holder_name;
}
public void setAcc_holder_name(String acc_holder_name) {
	this.acc_holder_name = acc_holder_name;
}

public int getValidate() {
	return validate;
}
public void setValidate(int validate) {
	this.validate = validate;
}
public int getAccountgen() {
	return accountgen;
}
public void setAccountgen(int accountgen) {
	this.accountgen = accountgen;
}
public String getDetail() {
	return detail;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public int getAmd_det() {
	return amd_det;
}
public void setAmd_det(int amd_det) {
	this.amd_det = amd_det;
}
public String getUtml() {
	return utml;
}
public void setUtml(String utml) {
	this.utml = utml;
}
public String getUdate() {
	return udate;
}
public void setUdate(String udate) {
	this.udate = udate;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getTesting() {
	return testing;
}
public void setTesting(String testing) {
	this.testing = testing;
}
public String getVerified() {
	return verified;
}
public void setVerified(String verified) {
	this.verified = verified;
}
public String getHidval() {
	return hidval;
}
public void setHidval(String hidval) {
	this.hidval = hidval;
}

public String getPvr_date() {
	return pvr_date;
}
public void setPvr_date(String pvr_date) {
	this.pvr_date = pvr_date;
}
public String getNew_dpamt() {
	return new_dpamt;
}
public void setNew_dpamt(String new_dpamt) {
	this.new_dpamt = new_dpamt;
}
public String getAc_type_name() {
	return ac_type_name;
}
public void setAc_type_name(String ac_type_name) {
	this.ac_type_name = ac_type_name;
}
public String getCust_name() {
	return cust_name;
}
public void setCust_name(String cust_name) {
	this.cust_name = cust_name;
}
public String getFreqint() {
	return freqint;
}
public void setFreqint(String freqint) {
	this.freqint = freqint;
}
public String getAmou() {
	return amou;
}
public void setAmou(String amou) {
	this.amou = amou;
}
public String getCommad() {
	return commad;
}
public void setCommad(String commad) {
	this.commad = commad;
}
public String getIntfreqee() {
	return intfreqee;
}
public void setIntfreqee(String intfreqee) {
	this.intfreqee = intfreqee;
}
public String getMysub() {
	return mysub;
}
public void setMysub(String mysub) {
	this.mysub = mysub;
}
public int getIntFREQ() {
	return intFREQ;
}
public void setIntFREQ(int intFREQ) {
	this.intFREQ = intFREQ;
}
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public String getPay_ac_typetwo() {
	return pay_ac_typetwo;
}
public void setPay_ac_typetwo(String pay_ac_typetwo) {
	this.pay_ac_typetwo = pay_ac_typetwo;
}
public double getDep_amttwo() {
	return dep_amttwo;
}
public void setDep_amttwo(double dep_amttwo) {
	this.dep_amttwo = dep_amttwo;
}
public String getFirst() {
	return first;
}
public void setFirst(String first) {
	this.first = first;
}


  
  
}
