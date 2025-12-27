package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class PygmyWithdrawalForm  extends ActionForm {
	String pgactypeno,pgno,pname,agentcode,agentnum,agentname,opdate,curdate,tyop,principal,interest,intrate,max_amt,withdrawal_amt,loan_acc,loan_bal,loan_int,fines,paymode,payactypeno,payno,name,pygdetails,pageid,tabPaneHeading;
    String defaultTab,signIndex,defaultSignIndex,check,forward,value,details;
	String pageId;
	String validate;
	
	NomineeDetailsForm nomform=new NomineeDetailsForm();
    private String  nompageid,hidval,cidis;
    private String acno,actype,nomname,gender,address,rel_ship,percentage,nomforward,issuedate,has_ac,cid,nomvalidations;
    private String dob=null;
    
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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

	public String getHidval() {
		return hidval;
	}

	public void setHidval(String hidval) {
		this.hidval = hidval;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getNomvalidations() {
		return nomvalidations;
	}

	public void setNomvalidations(String nomvalidations) {
		this.nomvalidations = nomvalidations;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPageId() {
		return pageId;
	}     

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
        
	public String getPgactypeno() {
		return pgactypeno;
	}

	public void setPgactypeno(String pgactypeno) {
		this.pgactypeno = pgactypeno;
	}

	public String getPgno() {
		return pgno;
	}

	public void setPgno(String pgno) {
		this.pgno = pgno;
	}

	public String getAgentcode() {
		return agentcode;
	}

	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}

	public String getAgentnum() {
		return agentnum;
	}

	public void setAgentnum(String agentnum) {
		this.agentnum = agentnum;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getTyop() {
		return tyop;
	}

	public void setTyop(String tyop) {
		this.tyop = tyop;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getIntrate() {
		return intrate;
	}

	public void setIntrate(String intrate) {
		this.intrate = intrate;
	}

	public String getMax_amt() {
		return max_amt;
	}

	public void setMax_amt(String max_amt) {
		this.max_amt = max_amt;
	}

	public String getWithdrawal_amt() {
		return withdrawal_amt;
	}

	public void setWithdrawal_amt(String withdrawal_amt) {
		this.withdrawal_amt = withdrawal_amt;
	}

	
	public String getLoan_bal() {
		return loan_bal;
	}

	public void setLoan_bal(String loan_bal) {
		this.loan_bal = loan_bal;
	}

	public String getFines() {
		return fines;
	}

	public void setFines(String fines) {
		this.fines = fines;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getPayactypeno() {
		return payactypeno;
	}

	public void setPayactypeno(String payactypeno) {
		this.payactypeno = payactypeno;
	}

	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPygdetails() {
		return pygdetails;
	}

	public void setPygdetails(String pygdetails) {
		this.pygdetails = pygdetails;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getLoan_acc() {
		return loan_acc;
	}

	public void setLoan_acc(String loan_acc) {
		this.loan_acc = loan_acc;
	}

	public String getTabPaneHeading() {
		return tabPaneHeading;
	}

	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getSignIndex() {
		return signIndex;
	}

	public void setSignIndex(String signIndex) {
		this.signIndex = signIndex;
	}

	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getLoan_int() {
		return loan_int;
	}

	public void setLoan_int(String loan_int) {
		this.loan_int = loan_int;
	}

	public String getCurdate() {
		return curdate;
	}

	public void setCurdate(String curdate) {
		this.curdate = curdate;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	
	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

		
}
