package masterObject.loansOnDeposit;

import masterObject.general.Address;

import java.io.Serializable;

public class LoanReportObject implements Serializable
{
	private String lnactype,sanctionDate,name,depositAccType,maturityDate,depDate,intUptoDate,transactionDate,close_dt,email;
	private int accNo,depositAccNo,modcode,depositDays;
	private double sanctionedAmount,depositAmt,maturityAmt,loanBalance,interestPaid,int_payable,depositIntRate,loanIntRate,interestAccrued;	
	private double principalPaid,extraIntPaid,otherAmt;
	private int referenceNo,rct_no,cid,phno,nom_no;
	private String cdind,trnmode,tranNarration,tranType;
	String string_ln_purpose,string_int_freq;
	
	
	double trn_amt,prn_amt;
	 private Address addr;
	//public =new Address();

	public String getCloseDate(){return close_dt;	}
	public void setCloseDate(String str){close_dt=str;}

	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}

	public String getDepositAccType(){return depositAccType;}
	public void setDepositAccType(String actype){this.depositAccType=actype;}

	public String getTransactionDate(){return transactionDate;}
	public void setTransactionDate(String actype){this.transactionDate=actype;}

	public int getDepositAccNo(){return depositAccNo;}
	public void setDepositAccNo(int acno){this.depositAccNo=acno;}

	public int getAccNo(){return accNo;}
	public void setAccNo(int acno){this.accNo=acno;}

	public int getModuleCode(){return modcode;}
	public void setModuleCode(int acno){this.modcode=acno;}

	public String getSanctionDate(){return sanctionDate;}
	public void setSanctionDate(String acctype){this.sanctionDate=acctype;}

	public String getIntUptoDate(){return intUptoDate;}
	public void setIntUptoDate(String acctype){this.intUptoDate=acctype;}

	public double getSanctionedAmount(){return sanctionedAmount;}
	public void setSanctionedAmount(double amount){this.sanctionedAmount=amount;}

	public double getLoanBalance(){return loanBalance;}
	public void setLoanBalance(double amount){this.loanBalance=amount;}

	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double amount){this.interestPaid=amount;}
	public double getOtherAmt(){return otherAmt;}
	public void setOtherAmt(double oth_amt){this.otherAmt=oth_amt;}

	public double getInterestAccrued(){return interestAccrued;}
	public void setInterestAccrued(double amount){this.interestAccrued=amount;}

	public double getInterestPayable(){return int_payable;}
	public void setInterestPayable(double amount){this.int_payable=amount;}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	public double getDepositAmt(){return depositAmt;}
	public void setDepositAmt(double depamt){this.depositAmt=depamt;}

	public double getMaturityAmt(){return maturityAmt;}
	public void setMaturityAmt(double matamt){this.maturityAmt=matamt;}

	public String getMaturityDate(){return maturityDate;}
	public void setMaturityDate(String matdate){this.maturityDate=matdate;}

	public String getDepDate(){return depDate;}
	public void setDepDate(String depdate){this.depDate=depdate;}

	public String getTranType(){return tranType;}
	public void setTranType(String trnty){this.tranType=trnty;}

	public String getTranNarration(){return tranNarration;}
	public void setTranNarration(String trnnarr){this.tranNarration=trnnarr;}
	
	public String getTranMode(){return trnmode;}
	public void setTranMode(String trnmode){this.trnmode=trnmode;}

	public String getCdind(){return cdind;}
	public void setCdind(String cdind){this.cdind=cdind;}

	public int getReferenceNo(){return referenceNo;}
	public void setReferenceNo(int refno){this.referenceNo=refno;}

	public double getPrincipalPaid(){return principalPaid;}
	public void setPrincipalPaid(double amount){this.principalPaid=amount;}

	public double getDepositIntRate(){return depositIntRate;}
	public void setDepositIntRate(double rate){this.depositIntRate=rate;}

	public double getLoanIntRate(){return loanIntRate;}
	public void setLoanIntRate(double rate){this.loanIntRate=rate;}

	public int getDepositDays(){return depositDays;}
	public void setDepositDays(int rate){this.depositDays=rate;}

	public double getExtraIntPaid(){return extraIntPaid;}
	public void setExtraIntPaid(double rate){this.extraIntPaid=rate;}
	
	public String getLoanPurposeDesc(){return string_ln_purpose;}
	public void setLoanPurposeDesc(String string_ln_purpose){this.string_ln_purpose=string_ln_purpose;}
	
	public double getTrnAmt(){return trn_amt;}
	public void setTrnAmt(double trn_amt){this.trn_amt=trn_amt;}
	
	public double getPrnAmt(){return prn_amt;}
	public void setPrnAmt(double prn_amt){this.prn_amt=prn_amt;}
	
	public int getRctNo(){return rct_no;}
	public void setRctNo(int rct_no){this.rct_no=rct_no;}
	
	public String getIntFreq(){return string_int_freq;}
	public void setIntFreq(String string_int_freq){this.string_int_freq=string_int_freq;}
	
	public int getCID(){return cid;}
	public void setCID(int cid){this.cid=cid;}
	public int getPhoneNo(){return phno;}
	public void setPhoneNo(int phno){this.phno=phno;}
	public String getEmail(){return email;}
	public void setEmail(String email){this.email=email;}
	public int getNomNo(){return nom_no;}
	public void setNomNo(int nom_no){this.nom_no=nom_no;}
	public Address getAddr() {
		return addr;
	}
	public void setAddr(Address addr) {
		this.addr = addr;
	}
	

	
}
