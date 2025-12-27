package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class AllotmentForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String pageId;
	private String actype, cid, appdate, sh_type, sh_cat, branch, num_shares,
			sh_amt, intro_type, intro_ac_num, div_paymode, div_ac_type,
			div_ac_num, pay_type, pay_ac_type, pay_ac_no, pay_ac_branch,
			combo_details, balance, amount, sh_val;
	private String acnum, sh_fee, ad_fee, sh_sale, misc, amt_rcvd, totshval;
	private String submit, forward, check, verify, validations, newnumber;
	private String frm_dt, to_dt, ac_not_fnd, cid_not_fnd, intr_not_fnd, ver;
	private String flag, nomineeChoice, verifyCh;
	int sharenumber;
	private String sr_no, date, name, amt, operation, sign, sname, nom_name;
	private String has_ac, nomCid, nomName, dob, gender, address, rel_ship,
			percentage,operationtype;

	public String getOperationtype() {
		return operationtype;
	}

	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}

	public String getHas_ac() {
		return has_ac;
	}

	public void setHas_ac(String has_ac) {
		this.has_ac = has_ac;
	}

	public String getNomCid() {
		return nomCid;
	}

	public void setNomCid(String nomCid) {
		this.nomCid = nomCid;
	}

	public String getNomName() {
		return nomName;
	}

	public void setNomName(String nomName) {
		this.nomName = nomName;
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

	public void setRel_ship(String rel_ship) {
		this.rel_ship = rel_ship;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNomineeChoice() {
		return nomineeChoice;
	}

	public void setNomineeChoice(String nomineeChoice) {
		this.nomineeChoice = nomineeChoice;
	}

	public String getSr_no() {
		return sr_no;
	}

	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public int getSharenumber() {
		return sharenumber;
	}

	public void setSharenumber(int sharenumber) {
		this.sharenumber = sharenumber;
	}

	public String getFrm_dt() {
		return frm_dt;
	}

	public void setFrm_dt(String frm_dt) {
		this.frm_dt = frm_dt;
	}

	public String getTo_dt() {
		return to_dt;
	}

	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public String getAcnum() {
		return acnum;
	}

	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAppdate() {
		return appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getSh_type() {
		return sh_type;
	}

	public void setSh_type(String sh_type) {
		this.sh_type = sh_type;
	}

	public String getSh_cat() {
		return sh_cat;
	}

	public void setSh_cat(String sh_cat) {
		this.sh_cat = sh_cat;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getNum_shares() {
		return num_shares;
	}

	public void setNum_shares(String num_shares) {
		this.num_shares = num_shares;
	}

	public String getSh_amt() {
		return sh_amt;
	}

	public void setSh_amt(String sh_amt) {
		this.sh_amt = sh_amt;
	}

	public String getIntro_type() {
		return intro_type;
	}

	public void setIntro_type(String intro_type) {
		this.intro_type = intro_type;
	}

	public String getIntro_ac_num() {
		return intro_ac_num;
	}

	public void setIntro_ac_num(String intro_ac_num) {
		this.intro_ac_num = intro_ac_num;
	}

	public String getDiv_paymode() {
		return div_paymode;
	}

	public void setDiv_paymode(String div_paymode) {
		this.div_paymode = div_paymode;
	}

	public String getDiv_ac_type() {
		return div_ac_type;
	}

	public void setDiv_ac_type(String div_ac_type) {
		this.div_ac_type = div_ac_type;
	}

	public String getDiv_ac_num() {
		return div_ac_num;
	}

	public void setDiv_ac_num(String div_ac_num) {
		this.div_ac_num = div_ac_num;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_ac_type() {
		return pay_ac_type;
	}

	public void setPay_ac_type(String pay_ac_type) {
		this.pay_ac_type = pay_ac_type;
	}

	public String getPay_ac_no() {
		return pay_ac_no;
	}

	public void setPay_ac_no(String pay_ac_no) {
		this.pay_ac_no = pay_ac_no;
	}

	public String getCombo_details() {
		return combo_details;
	}

	public void setCombo_details(String combo_details) {
		this.combo_details = combo_details;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSh_val() {
		return sh_val;
	}

	public void setSh_val(String sh_val) {
		this.sh_val = "100";
	}

	public String getSh_fee() {
		return sh_fee;
	}

	public void setSh_fee(String sh_fee) {
		this.sh_fee = sh_fee;
	}

	public String getAd_fee() {
		return ad_fee;
	}

	public void setAd_fee(String ad_fee) {
		this.ad_fee = ad_fee;
	}

	public String getSh_sale() {
		return sh_sale;
	}

	public void setSh_sale(String sh_sale) {
		this.sh_sale = sh_sale;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getAmt_rcvd() {
		return amt_rcvd;
	}

	public void setAmt_rcvd(String amt_rcvd) {
		this.amt_rcvd = amt_rcvd;
	}

	public String getTotshval() {
		return totshval;
	}

	public void setTotshval(String totshval) {
		this.totshval = totshval;
	}

	public String getPay_ac_branch() {
		return pay_ac_branch;
	}

	public void setPay_ac_branch(String pay_ac_branch) {
		this.pay_ac_branch = pay_ac_branch;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getAc_not_fnd() {
		return ac_not_fnd;
	}

	public void setAc_not_fnd(String ac_not_fnd) {
		this.ac_not_fnd = ac_not_fnd;
	}

	public String getCid_not_fnd() {
		return cid_not_fnd;
	}

	public void setCid_not_fnd(String cid_not_fnd) {
		this.cid_not_fnd = cid_not_fnd;
	}

	public String getIntr_not_fnd() {
		return intr_not_fnd;
	}

	public void setIntr_not_fnd(String intr_not_fnd) {
		this.intr_not_fnd = intr_not_fnd;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}

	public String getNewnumber() {
		return newnumber;
	}

	public void setNewnumber(String newnumber) {
		this.newnumber = newnumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getNom_name() {
		return nom_name;
	}

	public void setNom_name(String nom_name) {
		this.nom_name = nom_name;
	}

	public String getVerifyCh() {
		return verifyCh;
	}

	public void setVerifyCh(String verifyCh) {
		this.verifyCh = verifyCh;
	}

}
