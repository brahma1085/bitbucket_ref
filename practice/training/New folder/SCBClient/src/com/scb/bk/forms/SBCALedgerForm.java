package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class SBCALedgerForm extends ActionForm {
	
  private String fromdate,todate,accType,select,accname,custid,but_print,testing;
  private int fromacno,toacno,accno,userId,verId;
  private String openedon,accstatus,accategory,chq_bk_issue,nominee_name,int_pd_upto,relationship,int_revd_upto;
  private String forward,pageId;
  
  
public String getBut_print() {
	return but_print;
}
public void setBut_print(String butPrint) {
	but_print = butPrint;
}
public String getTesting() {
	return testing;
}
public void setTesting(String testing) {
	this.testing = testing;
}
public String getFromdate() {return fromdate;}
public void setFromdate(String fromdate) {this.fromdate = fromdate;}

public String getTodate() {	return todate;}
public void setTodate(String todate) {this.todate = todate;}

public String getForward() {return forward;}
public void setForward(String forward) {this.forward = forward;}

public String getPageId() {	return pageId;}
public void setPageId(String pageId) {this.pageId = pageId;}

public String getAccType() {return accType;}
public void setAccType(String accType) {this.accType = accType;}


public String getSelect() {	return select;}
public void setSelect(String select) {	this.select = select;}

public String getCustid() {
	return custid;
}
public void setCustid(String custid) {
	this.custid = custid;
}
public String getOpenedon() {
	return openedon;
}
public void setOpenedon(String openedon) {
	this.openedon = openedon;
}
public String getAccstatus() {
	return accstatus;
}
public void setAccstatus(String accstatus) {
	this.accstatus = accstatus;
}
public String getAccategory() {
	return accategory;
}
public void setAccategory(String accategory) {
	this.accategory = accategory;
}
public String getChq_bk_issue() {
	return chq_bk_issue;
}
public void setChq_bk_issue(String chq_bk_issue) {
	this.chq_bk_issue = chq_bk_issue;
}
public String getNominee_name() {
	return nominee_name;
}
public void setNominee_name(String nominee_name) {
	this.nominee_name = nominee_name;
}
public String getInt_pd_upto() {
	return int_pd_upto;
}
public void setInt_pd_upto(String int_pd_upto) {
	this.int_pd_upto = int_pd_upto;
}
public String getRelationship() {
	return relationship;
}
public void setRelationship(String relationship) {
	this.relationship = relationship;
}
public String getInt_revd_upto() {
	return int_revd_upto;
}
public void setInt_revd_upto(String int_revd_upto) {
	this.int_revd_upto = int_revd_upto;
}
public String getAccname() {
	return accname;
}
public void setAccname(String accname) {
	this.accname = accname;
}
public int getFromacno() {
	return fromacno;
}
public void setFromacno(int fromacno) {
	this.fromacno = fromacno;
}
public int getToacno() {
	return toacno;
}
public void setToacno(int toacno) {
	this.toacno = toacno;
}
public int getAccno() {
	return accno;
}
public void setAccno(int accno) {
	this.accno = accno;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getVerId() {
	return verId;
}
public void setVerId(int verId) {
	this.verId = verId;
}
	
}