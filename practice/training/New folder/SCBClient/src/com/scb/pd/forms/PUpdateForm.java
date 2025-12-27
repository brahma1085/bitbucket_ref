package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

public class PUpdateForm extends ActionForm{
	
	String pageid;
	
	String pyg_Type,pyg_no,pyg_name,cust_id,addr_Type,ag_Type,ag_no,ag_Name,op_date,ac_Status,lst_int_date,loan_avail,loan_ac_Type,loan_ac_No,pay_mode,pay_ac_Type,pay_ac_no,pay_ac_name,close_date,details,forward,tabPaneHeading,defaultTab;
	String flag;
	
	NomineeDetailsForm nomform=new NomineeDetailsForm();
    private String  nompageid,hidval,cidis;
    private String acno,actype,nomname,dob,gender,address,rel_ship,percentage,nomforward,issuedate,has_ac,cid,nomvalidations;

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

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getPyg_Type() {
		return pyg_Type;
	}

	public void setPyg_Type(String pyg_Type) {
		this.pyg_Type = pyg_Type;
	}

	public String getPyg_no() {
		return pyg_no;
	}

	public void setPyg_no(String pyg_no) {
		this.pyg_no = pyg_no;
	}

	public String getPyg_name() {
		return pyg_name;
	}

	public void setPyg_name(String pyg_name) {
		this.pyg_name = pyg_name;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getAddr_Type() {
		return addr_Type;
	}

	public void setAddr_Type(String addr_Type) {
		this.addr_Type = addr_Type;
	}

	public String getAg_Type() {
		return ag_Type;
	}

	public void setAg_Type(String ag_Type) {
		this.ag_Type = ag_Type;
	}

	public String getAg_no() {
		return ag_no;
	}

	public void setAg_no(String ag_no) {
		this.ag_no = ag_no;
	}

	public String getAg_Name() {
		return ag_Name;
	}

	public void setAg_Name(String ag_Name) {
		this.ag_Name = ag_Name;
	}

	public String getOp_date() {
		return op_date;
	}

	public void setOp_date(String op_date) {
		this.op_date = op_date;
	}

	public String getAc_Status() {
		return ac_Status;
	}

	public void setAc_Status(String ac_Status) {
		this.ac_Status = ac_Status;
	}

	public String getLst_int_date() {
		return lst_int_date;
	}

	public void setLst_int_date(String lst_int_date) {
		this.lst_int_date = lst_int_date;
	}

	public String getLoan_avail() {
		return loan_avail;
	}

	public void setLoan_avail(String loan_avail) {
		this.loan_avail = loan_avail;
	}

	public String getLoan_ac_Type() {
		return loan_ac_Type;
	}

	public void setLoan_ac_Type(String loan_ac_Type) {
		this.loan_ac_Type = loan_ac_Type;
	}

	public String getLoan_ac_No() {
		return loan_ac_No;
	}

	public void setLoan_ac_No(String loan_ac_No) {
		this.loan_ac_No = loan_ac_No;
	}

	public String getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}

	public String getPay_ac_Type() {
		return pay_ac_Type;
	}

	public void setPay_ac_Type(String pay_ac_Type) {
		this.pay_ac_Type = pay_ac_Type;
	}

	public String getPay_ac_no() {
		return pay_ac_no;
	}

	public void setPay_ac_no(String pay_ac_no) {
		this.pay_ac_no = pay_ac_no;
	}

	public String getPay_ac_name() {
		return pay_ac_name;
	}

	public void setPay_ac_name(String pay_ac_name) {
		this.pay_ac_name = pay_ac_name;
	}

	public String getClose_date() {
		return close_date;
	}

	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	

}
