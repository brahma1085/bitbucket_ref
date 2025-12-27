package masterObject.termDeposit; 

import java.io.Serializable;
import masterObject.general.NomineeObject;
import masterObject.general.Address;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import masterObject.loansOnDeposit.LoanReportObject;


public class DepositMasterObject implements Serializable
{
	String accType,nextPaydt,maturityPost,string_postdate,string_introducer_name,string_introducer_acctype,string_repay_opt,receivedBy,receivedAccType,interestFrq,interestMode;
	String transferAccType,interestUpto,string_lsttrndt,string_ln_acctype,autoRenewal,closedt,string_renewal_actype,string_ldgrprtdt,intPaiddt,trantypetemp;
	String string_deposit_renewal,newReceipt,receiptPrtd,receiptSign,loanAvailed,string_poststart,string_close_start,string_close_end;
	String depDate,maturityDate,name,string_verify,string_catname,depType,tranDate,trntype,string_addr_type,Limit,Moduletype,Modulecode,Moduleabbr;
	int accNo,customerId,int_autoren_no,noofJtHldrs,depositDays,introAccno,int_recd_acno,transferAccno,loanAccno,receiptno,closeInd,renewalAccno,int_glcode,min_period,Fr_lmt,To_lmt;
	long long_lsttrn_seq,period_in_days;
	String custtype,email;
	double depositAmt,maturityAmt,interestRate,double_excamt,interestPaid,double_penalty_rate,double_dep_paid,cumInterest,RDBalance,double_trf_amt;
	int nomineeRegNo,DPType,category,extraRateType,int_glacccode,srl_no;

	double int_loan_payable;
	
	
	int[] array_joint_addrtype=null;
	String array_string_typeop[]=null;
	int[] array_cid=null;
	
	NomineeObject array_nomineeobject[]=null;	
	SignatureInstructionObject array_siginsobject[]=null;
	
	// added by Shiva
	String string_ph_no,string_mob_no,string_email,string_scroll_date;
	int int_lst_rct_no;
	boolean boolean_rct_update;
	
	private LoanReportObject loan_reportobj;
	
	
	public LoanReportObject getLoan_reportobj() {
		return loan_reportobj;
	}
	public void setLoan_reportobj(LoanReportObject loan_reportobj) {
		this.loan_reportobj = loan_reportobj;
	}
	public void setNomineeDetails(NomineeObject[] array_nominee_object){this.array_nomineeobject=array_nominee_object;}
	public NomineeObject[] getNomineeDetails(){return array_nomineeobject;}
	
	public void setSigObj(SignatureInstructionObject[] array_siginsobject){this.array_siginsobject=array_siginsobject;}
	public SignatureInstructionObject[] getSigObj(){return array_siginsobject;}

	public NomineeObject nomineeobject=new NomineeObject();
	public UserVerifier userverifier=new UserVerifier();
	public Address address=new Address();
	
	String details_address;
	
	private int mailingAddress;
	private String deposittype;
    private double amount_rec;
    private double interestAccured;
    private int Account_number;

	public int getAccount_number() {
		return Account_number;
	}
	public void setAccount_number(int accountNumber) {
		Account_number = accountNumber;
	}
	public String getVerified(){return string_verify;}
	public void  setVerified(String string_verify){this.string_verify=string_verify;}
	
	public String getAccType(){return accType;}
	public void setAccType(String string_acctype){this.accType=string_acctype;}

	public String getName(){return name;}
	public void setName(String string_name){this.name=string_name;}

	public int getDPType(){return DPType;}
	public void setDPType(int int_category){this.DPType=int_category;}

	public int getCategory(){return category;}
	public void setCategory(int int_subcategory){this.category=int_subcategory;}

	public int getCustomerId(){return customerId;}
	public void setCustomerId(int int_cidi){customerId=int_cidi;}

	public String getNextPaydt(){return nextPaydt;}
	public void setNextPaydt(String string_nextpay_date){this.nextPaydt=string_nextpay_date;}

	public String getMaturityPost(){return maturityPost;}
	public void setMaturityPost(String string_maturity_post){this.maturityPost=string_maturity_post;}

	public String getPostdt(){return string_postdate;}
	public void setPostdt(String string_postdate){this.string_postdate=string_postdate;}

	public String getIntroName(){return string_introducer_name;}
	public void setIntroName(String string_introducer_name){this.string_introducer_name=string_introducer_name;}

	public String getIntroAccType(){return string_introducer_acctype;}
	public void setIntroAccType(String string_introducer_acctype){this.string_introducer_acctype=string_introducer_acctype;}

	public String getRepayOpt(){return string_repay_opt;}
	public void setRepayOpt(String string_repay_opt){this.string_repay_opt=string_repay_opt;}

	public String getReceivedBy(){return receivedBy;}
	public void setReceivedBy(String string_recd_by){this.receivedBy=string_recd_by;}

	public String getReceivedAccType(){return receivedAccType;}
	public void setReceivedAccType(String string_recv_acctype){this.receivedAccType=string_recv_acctype;}

	public String getInterestFrq(){return interestFrq;}
	public void setInterestFrq(String string_int_freq){this.interestFrq=string_int_freq;}

	public String getInterestMode(){return interestMode;}
	public void setInterestMode(String stringint_mode){this.interestMode=stringint_mode;}

	public String getTransferAccType(){return transferAccType;}
	public void setTransferAccType(String string_trf_acctype){this.transferAccType=string_trf_acctype;}

	public String getInterestUpto(){return interestUpto;}
	public void setInterestUpto(String string_intuptodt){this.interestUpto=string_intuptodt;}

	public String getLastTrndt(){return string_lsttrndt;}
	public void setLastTrndt(String string_lsttrndt){this.string_lsttrndt=string_lsttrndt;}

	public String getLoanAcType(){return string_ln_acctype;}
	public void setLoanAcType(String string_ln_acctype){this.string_ln_acctype=string_ln_acctype;}

	public String getAutoRenewal(){return autoRenewal;}
	public void setAutoRenewal(String string_autorenewal){this.autoRenewal=string_autorenewal;}

	public String getClosedt(){return closedt;}
	public void setClosedt(String string_close_date){this.closedt=string_close_date;}

	public String getRenType(){return string_renewal_actype;}
	public void setRenType(String string_renewal_actype){this.string_renewal_actype=string_renewal_actype;}

	public String getLdgrPrtdt(){return string_ldgrprtdt;}
	public void setLdgrPrtdt(String string_ldgrprtdt){this.string_ldgrprtdt=string_ldgrprtdt;}

	public String getIntPaiddt(){return intPaiddt;}
	public void setIntPaiddt(String string_intpddt){this.intPaiddt=string_intpddt;}

	public String getDepositRenew(){return string_deposit_renewal;}
	public void setDepositRenew(String string_deposit_renewal){this.string_deposit_renewal=string_deposit_renewal;}

	public String getNewReceipt(){return newReceipt;}
	public void setNewReceipt(String string_new_rct){this.newReceipt=string_new_rct;}

	public String getReceiptPrtd(){return receiptPrtd;}
	public void setReceiptPrtd(String string_rct_prtd){this.receiptPrtd=string_rct_prtd;}

	public String getReceiptSign(){return receiptSign;}
	public void setReceiptSign(String string_rct_sign){this.receiptSign=string_rct_sign;}

	public String getLoanAvailed(){return loanAvailed;}
	public void setLoanAvailed(String string_ln_avld){this.loanAvailed=string_ln_avld;}

	public String getPostStart(){return string_poststart;}
	public void setPostStart(String string_poststart){this.string_poststart=string_poststart;}

	public String getCloseStart(){return string_close_start;}
	public void setCloseStart(String string_close_start){this.string_close_start=string_close_start;}

	public String getCloseEnd(){return string_close_end;}
	public void setCloseEnd(String string_close_end){this.string_close_end=string_close_end;}

	public String getMaturityDate(){return maturityDate;}
	public void setMaturityDate(String string_mat_date){this.maturityDate=string_mat_date;}

	public String getDepDate(){return depDate;}
	public void setDepDate(String string_dep_date){this.depDate=string_dep_date;}

	public int getMailingAddress(){return mailingAddress;}
	public void setMailingAddress(int int_addr_type){this.mailingAddress=int_addr_type;}

	public String getTranDate(){return tranDate;}
	public void setTranDate(String string_trndate){this.tranDate=string_trndate;}


	public String getDepCatName(){return string_catname;}
	public void setDepCatName(String string_category_name){string_catname=string_category_name;}

	public String getDepType(){return depType;}
	public void setDepType(String string_dep_type){depType=string_dep_type;}

	public String getTransferType(){return trntype;}
	public void setTransferType(String trntype){this.trntype=trntype;}

	public int getAccNo(){return accNo;}
	public void setAccNo(int int_acno){this.accNo=int_acno;}
	
	public int getAutoRenwlNo(){return int_autoren_no;}
	public void setAutoRenwlNo(int int_autoren_no){this.int_autoren_no=int_autoren_no;}

	public int getNoofJtHldrs(){return noofJtHldrs;}
	public void setNoofJtHldrs(int int_no_of_joint_holder){this.noofJtHldrs=int_no_of_joint_holder;}

	public int getDepositDays(){return depositDays;}
	public void setDepositDays(int int_dep_days){this.depositDays=int_dep_days;}

	public int getIntroAccno(){return introAccno;}
	public void setIntroAccno(int int_introducer_acno){this.introAccno=int_introducer_acno;}

	public int getReceivedAccno(){return int_recd_acno;}
	public void setReceivedAccno(int int_recd_acno){this.int_recd_acno=int_recd_acno;}

	public int getTransferAccno(){return transferAccno;}
	public void setTransferAccno(int int_trf_acno){this.transferAccno=int_trf_acno;}

	public long getLastTrnSeq(){return long_lsttrn_seq;}
	public void setLastTrnSeq(long long_lsttrn_seq){this.long_lsttrn_seq=long_lsttrn_seq;}

	public int getLoanAccno(){return loanAccno;}
	public void setLoanAccno(int int_ln_acno){this.loanAccno=int_ln_acno;}

	public int getReceiptno(){return receiptno;}
	public void setReceiptno(int int_rctno){this.receiptno=int_rctno;}

	public int getNomineeRegNo(){return nomineeRegNo;}
	public void setNomineeRegNo(int int_nomregno){this.nomineeRegNo=int_nomregno;}

	public int getCloseInd(){return closeInd;}
	public void setCloseInd(int int_close_ind){this.closeInd=int_close_ind;}

	public int getRenewalAccno(){return renewalAccno;}
	public void setRenewalAccno(int int_renewal_acno){this.renewalAccno=int_renewal_acno;}

	public int getGLRefCode(){return int_glcode;}
	public void setGLRefCode(int int_glcode){this.int_glcode=int_glcode;}

	public int getGLAccRefCode(){return int_glacccode;}
	public void setGLAccRefCode(int int_glacccode){this.int_glacccode=int_glacccode;}

	public int getExtraRateType(){return extraRateType;}
	public void setExtraRateType(int int_extratetype){this.extraRateType=int_extratetype;}

	public double getDepositAmt(){return depositAmt;}
	public void setDepositAmt(double double_deposit_amt){this.depositAmt=double_deposit_amt;}

	public double getMaturityAmt(){return maturityAmt;}
	public void setMaturityAmt(double double_maturity_amt){this.maturityAmt=double_maturity_amt;}

	public double getTranAmt(){return double_trf_amt;}
	public void setTranAmt(double double_trf_amt){this.double_trf_amt=double_trf_amt;}

	public double getInterestRate(){return interestRate;}
	public void setInterestRate(double double_interest_rate){this.interestRate=double_interest_rate;}

	public double getExcessAmt(){return double_excamt;}
	public void setExcessAmt(double double_excamt){this.double_excamt=double_excamt;}

	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double double_int_paid){this.interestPaid=double_int_paid;}

	public double getDepositPaid(){return double_dep_paid;}
	public void setDepositPaid(double double_int_paid){this.double_dep_paid=double_int_paid;}

	public double getPenaltyRate(){return double_penalty_rate;}
	public void setPenaltyRate(double double_penalty_rate){this.double_penalty_rate=double_penalty_rate;}

	public double getCumInterest(){return cumInterest;}
	public void setCumInterest(double double_cumint){this.cumInterest=double_cumint;}

	public double getRDBalance(){return RDBalance;}
	public void setRDBalance(double double_rdbal){this.RDBalance=double_rdbal;}

	
	public void setJointAddrType(int[] array_string_joint_op){this.array_joint_addrtype=array_string_joint_op;}
	public int[] getJointAddrType(){return array_joint_addrtype;}
	
	public void setJointCid(int[] array_string_cid){this.array_cid=array_string_cid;}
	public int[] getJointCid(){return array_cid;}
	
	public String[] getTypeofOp(){return array_string_typeop;}
	public void setTypeofOp(String[] array_string_op){this.array_string_typeop=array_string_op;}
    /**
     * @param string
     */
    public void setDepositType(String string) {
        deposittype = string;
    }    
    public String getDepositType() {
        return deposittype;        
    }
    /**
     * @param
     */
    public void setDepositAmtReceived(double amount_rec) {
        this.amount_rec = amount_rec;
    }
    /**
     * @return
     */
    public double getDepositAmtReceived() {
        return amount_rec;
    }
    /**
     * @return
     */
    public double getInterestAccured() {
        return interestAccured;
    }
    /**
     * @param 
     */
    public void setInterestAccured(double interest_acc) {
        this.interestAccured = interest_acc;
    }
    	
    
    public void setPhoneNo(String string_ph_no) {
    	this.string_ph_no = string_ph_no;
    }    
    public String getPhoneNo() {
        return string_ph_no ;}     

    public void setMobileNo(String string_mob_no) {
    	this.string_mob_no = string_mob_no;
    }    
    public String getMobileNo() {
        return string_mob_no ;}    
    
    public void setEmailID(String string_email) {
    	this.string_email = string_email;
    }    
    public String getEmailID() {
        return string_email ;}     
    
    public void setLastRctNo(int int_lst_rct_no)
    {
    	this.int_lst_rct_no=int_lst_rct_no;
    }
    
    public int getLastRctNo()
    {
    	return int_lst_rct_no;
    }
    
    public void setRctUpdate(boolean boolean_rct_update)
    {
    	this.boolean_rct_update=boolean_rct_update;
    }
    
    public boolean isRctUpdate()
    {
    	return boolean_rct_update; 
    }
    
    public void setMinimumPeriod(int period){this.min_period=period;}
    public int getMinimumPeriod(){return min_period;}

	public int getFr_lmt() {
		return Fr_lmt;
	}
	
	public void setFr_lmt(int fr_lmt) {
		Fr_lmt = fr_lmt;
	}
	
	public int getTo_lmt() {
		return To_lmt;
	}
	
	public void setTo_lmt(int to_lmt) {
		To_lmt = to_lmt;
	}
	
	public String getLimit() {
		return Limit;
	}
	
	public void setLimit(String limit) {
		Limit = limit;
	}
	
	public int getSrl_no() {
		return srl_no;
	}
	
	public void setSrl_no(int srl_no) {
		this.srl_no = srl_no;
	}
	
	public String getModuleabbr() {
		return Moduleabbr;
	}
	
	public void setModuleabbr(String moduleabbr) {
		Moduleabbr = moduleabbr;
	}
	
	public String getModulecode() {
		return Modulecode;
	}
	
	public void setModulecode(String modulecode) {
		Modulecode = modulecode;
	}
	
	public String getModuletype() {
		return Moduletype;
	}
	
	public void setModuletype(String moduletype) {
		Moduletype = moduletype;
	}
	public String getString_scroll_date() {
		return string_scroll_date;
	}
	public void setString_scroll_date(String string_scroll_date) {
		this.string_scroll_date = string_scroll_date;
	}
	public long getPeriod_in_days() {
		return period_in_days;
	}
	public void setPeriod_in_days(long period_in_days) {
		this.period_in_days = period_in_days;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getDetails_address() {
		return details_address;
	}
	public void setDetails_address(String details_address) {
		this.details_address = details_address;
	}
	public double getInt_loan_payable() {
		return int_loan_payable;
	}
	public void setInt_loan_payable(double int_loan_payable) {
		this.int_loan_payable = int_loan_payable;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTrantypetemp() {
		return trantypetemp;
	}
	public void setTrantypetemp(String trantypetemp) {
		this.trantypetemp = trantypetemp;
	}
	
}
