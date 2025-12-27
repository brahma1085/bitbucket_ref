package masterObject.loans;


import masterObject.general.Address;

import java.io.Serializable;
import java.util.Vector;

public class LoanReportObject implements Serializable
{
	String npa_since_date,transactionDate,close_date,disbursementDate=null,sharedet=null,savingdet=null,loanPurpose=null,address=null,weakerSection=null,scstInd=null,modcode=null,matdate=null;
	int noInstallments,holidayperiod;
	int nominee_reg_no,accNo,sh_no,temp_no,stage_no,ref_no,no_of_inst,def_mths,refno,remd_no,int_type;
	double sanctionedAmount,loanbalance,installmentAmount,curinst,shareamt,loanintrate,penal_int,oth_chrgs,amt_overdue,disburseAmount;
	String surity=null,accType=null,sh_ty=null,prt_date=null,prt_ind=null,sanctionDate=null,name=null,intUptoDate=null,transactiondate=null,br_code=null,remd_desc=null;
	int applicationSrlNo,ac_no,tdacno,depdays,phno;
	String applicationDate=null,ac_ty=null,cdind=null,trnty=null,trnnarr=null,trnmode=null,email=null;
	double requiredAmount,interestPaid,depamt,dep_int_rate,int_payable,matamt,prn_paid;	
	Vector surities;
	char sexInd;
	
	// Code added by Murugesh on 19/05/2006
	String npasincedate=null,npatowards=null,action_particulars=null,psect_code;
	double prnOverDueAmt=0.0,intOverDueAmt=0.0,provisionReq=0.0,prov_made=0.0,processing_charges=0.0;
	int overdueperiod=0;
	//
	
	double int_accr;
	public Address addr=new Address();
		
	public String getClose_date() {
		return close_date;
	}
	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}
	public int getPhoneNo(){return phno;}
	public void setPhoneNo(int phno){this.phno=phno;}
	
	public String getEmail(){return email;}
	public void setEmail(String email){this.email=email;}
	
	public int getNomRegNo(){return nominee_reg_no;}
	public void setNomRegNo(int nominee_reg_no){this.nominee_reg_no=nominee_reg_no;}
	
	public String getTranMode(){return trnmode;}
	public void setTranMode(String trnmode){this.trnmode=trnmode;}
	
	public String getCdind(){return cdind;}
	public void setCdind(String cdind){this.cdind=cdind;}
	
	public String getTranType(){return trnty;}
	public void setTranType(String trnty){this.trnty=trnty;}

	public String getTranNarration(){return trnnarr;}
	public void setTranNarration(String trnnarr){this.trnnarr=trnnarr;}


	
	public int getDepositDays(){return depdays;}
	public void setDepositDays(int rate){this.depdays=rate;}

	public double getPrincipalPaid(){return prn_paid;}
	public void setPrincipalPaid(double amount){this.prn_paid=amount;}

	public int getDepositAccNo(){return tdacno;}
	public void setDepositAccNo(int acno){this.tdacno=acno;}

	
	public double getMaturityAmt(){return matamt;}
	public void setMaturityAmt(double matamt){this.matamt=matamt;}

	public String getMaturityDate(){return matdate;}
	public void setMaturityDate(String matdate){this.matdate=matdate;}

	
	public double getDepositAmt(){return depamt;}
	public void setDepositAmt(double depamt){this.depamt=depamt;}
	
	public double getInterestPayable(){return int_payable;}
	public void setInterestPayable(double amount){this.int_payable=amount;}
	
	public double getDepositIntRate(){return dep_int_rate;}
	public void setDepositIntRate(double rate){this.dep_int_rate=rate;}

	
	public int getInterestType(){return int_type;}
	public void setInterestType(int int_type){this.int_type=int_type;}

	
	public double getDisburseAmount(){return disburseAmount;}
	public void setDisburseAmount(double rate){this.disburseAmount=rate;}

	
	public int getPayAccountNo(){return ac_no;}
	public void setPayAccountNo(int remd_no){this.ac_no=remd_no;}
	
	public String getPayAccountType(){return ac_ty;}
	public void setPayAccountType(String remd_no){this.ac_ty=remd_no;}
	
	public int getRemainderNo(){return remd_no;}
	public void setRemainderNo(int remd_no){this.remd_no=remd_no;}
	
	public String getPrioritySectorCode(){return psect_code;}
	public void setPrioritySectorCode(String psect_code){this.psect_code=psect_code;}
	
	public int getApplicationSrlNo(){return applicationSrlNo;}
	public void setApplicationSrlNo(int app){this.applicationSrlNo=app;}
	
	public char getSexInd(){return sexInd;}
	public void  setSexInd(char ver){this.sexInd=ver;}
	
	public String getApplicationDate(){return applicationDate;}
	public void setApplicationDate(String actype){this.applicationDate=actype;}
	
	public double getRequiredAmount(){return requiredAmount;}
	public void setRequiredAmount(double amount){this.requiredAmount=amount;}
	
	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double amount){this.interestPaid=amount;}
	
	public double getInterestAccrued(){return int_accr;}
	public void setInterestAccrued(double amount){this.int_accr=amount;}

	
	public String getModuleCode(){return modcode;}
	public void setModuleCode(String acno){this.modcode=acno;}
	
	public String getAddress(){return address;}
	public void setAddress(String addrs){this.address=addrs;}
	
	public String getScstInd(){return scstInd;}
	public void  setScstInd(String ver){this.scstInd=ver;}
	
	public String getWeakerSection(){return weakerSection;}
	public void  setWeakerSection(String wkr){this.weakerSection=wkr;}
	
	public String getRemainderDesc(){return remd_desc;}
	public void setRemainderDesc(String remd_dt){this.remd_desc=remd_dt;}

	public String getDisbursementDate(){return disbursementDate;}
	public void setDisbursementDate(String actype){this.disbursementDate=actype;}
	
	public String getShareDet(){return sharedet;}
	public void setShareDet(String shdet){this.sharedet=shdet;}

	public String getLoanPurpose(){return loanPurpose;}
	public void setLoanPurpose(String shdet){this.loanPurpose=shdet;}

	public String getSavingDet(){return savingdet;}
	public void setSavingDet(String shdet){this.savingdet=shdet;}
	
	public int getNoInstallments(){return noInstallments;}
	public void setNoInstallments(int acno){this.noInstallments=acno;}

	public int getHolidayPeriod(){return holidayperiod;}
	public void setHolidayPeriod(int acno){this.holidayperiod=acno;}
	
	public Vector getSurities(){return surities;}
	public void setSurities(Vector surities){this.surities=surities;}

	public String getAccType(){return accType;}
	public void setAccType(String actype){this.accType=actype;}

	

	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public int getAccNo(){return accNo;}
	public void setAccNo(int acno){this.accNo=acno;}

	public String getSanctionDate(){return sanctionDate;}
	public void setSanctionDate(String acctype){this.sanctionDate=acctype;}

	public String getIntUptoDate(){return intUptoDate;}
	public void setIntUptoDate(String paid_upto){this.intUptoDate=paid_upto;}

	public double getSanctionedAmount(){return sanctionedAmount;}
	public void setSanctionedAmount(double amount){this.sanctionedAmount=amount;}

	public double getLoanBalance(){return loanbalance;}
	public void setLoanBalance(double amount){this.loanbalance=amount;}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}


	public int getReferenceNo(){return refno;}
	public void setReferenceNo(int refno){this.refno=refno;}

	public double getLoanIntRate(){return loanintrate;}
	public void setLoanIntRate(double rate){this.loanintrate=rate;}

	
	public double getPenalInterest(){return penal_int;}
	public void setPenalInterest(double rate){this.penal_int=rate;}
	
	public double getOtherCharges(){return oth_chrgs;}
	public void setOtherCharges(double rate){this.oth_chrgs=rate;}
	
	public double getAmountOverDue(){return amt_overdue;}
	public void setAmountOverDue(double rate){this.amt_overdue=rate;}
	
	public double getInstallmentAmount(){return installmentAmount;}
	public void setInstallmentAmount(double rate){this.installmentAmount=rate;}
	
	public double getCurrentInstallment(){return curinst;}
	public void setCurrentInstallment(double rate){this.curinst=rate;}
	
	public String getShareType(){return sh_ty;}
	public void setShareType(String trnty){this.sh_ty=trnty;}
	
	public String getPrintedDate(){return prt_date;}
	public void setPrintedDate(String trnty){this.prt_date=trnty;}
	
	public String getPrintInd(){return prt_ind;}
	public void setPrintInd(String trnty){this.prt_ind=trnty;}
	
	public String getBranchCode(){return br_code;}
	public void setBranchCode(String trnty){this.br_code=trnty;}
	
	public int getShareNo(){return sh_no;}
	public void setShareNo(int acno){this.sh_no=acno;}
	
	public int getTemplateNo(){return temp_no;}
	public void setTemplateNo(int acno){this.temp_no=acno;}
	
	public int getStageNo(){return stage_no;}
	public void setStageNo(int acno){this.stage_no=acno;}
	
	public int getRefNo(){return ref_no;}
	public void setRefNo(int acno){this.ref_no=acno;}
	
	public int getNoOfInstals(){return no_of_inst;}
	public void setNoOfInstals(int acno){this.no_of_inst=acno;}
	
	public int getDefaultedMths(){return def_mths;}
	public void setDefaultedMths(int acno){this.def_mths=acno;}
	
	public double getShareAmt(){return shareamt;}
	public void setShareAmt(double rate){this.shareamt=rate;}

    public String getSurityDetails(){return surity;}
    public void setSurityDetails(String surity){this.surity=surity;}
    
    // Code added by Murugesh on 19/05/2006
    public String getActionParticulars(){return action_particulars;}
    public void setActionParticulars(String action_particulars){this.action_particulars = action_particulars;}
    
   
    
    public String getNpasincedate() {
		return npasincedate;
	}
	public void setNpasincedate(String npasincedate) {
		this.npasincedate = npasincedate;
	}
	
    
   
	
	public double getIntOverDueAmt(){return intOverDueAmt;}
	public void setIntOverDueAmt(double int_overdue_amt){this.intOverDueAmt=int_overdue_amt;}

	

	public double getProvisionReq() {
		return provisionReq;
	}
	public void setProvisionReq(double provisionReq) {
		this.provisionReq = provisionReq;
	}
	public double getProvisionMade(){return prov_made;}
	public void setProvisionMade(double prov_made){this.prov_made=prov_made;}

	
	
	public int getOverdueperiod() {
		return overdueperiod;
	}
	public void setOverdueperiod(int overdueperiod) {
		this.overdueperiod = overdueperiod;
	}
	public double getProcessingCharges(){return processing_charges;}
	public void setProcessingCharges(double processing_charges){this.processing_charges=processing_charges;}
	//
	public double getPrnOverDueAmt() {
		return prnOverDueAmt;
	}
	public void setPrnOverDueAmt(double prnOverDueAmt) {
		this.prnOverDueAmt = prnOverDueAmt;
	}
	public String getNpatowards() {
		return npatowards;
	}
	public void setNpatowards(String npatowards) {
		this.npatowards = npatowards;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getNpa_since_date() {
		return npa_since_date;
	}
	public void setNpa_since_date(String npa_since_date) {
		this.npa_since_date = npa_since_date;
	}
}
