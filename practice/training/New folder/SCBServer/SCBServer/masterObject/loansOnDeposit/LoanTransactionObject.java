package masterObject.loansOnDeposit;


import masterObject.general.UserVerifier;

import java.io.Serializable;



public class LoanTransactionObject implements Serializable
{
	private String lnactype,trnty,trnmode,tranNarration,trnsou,cdind,paid_upto,trndate,rcy_dt,name;
	private int accNo,trnseq,refno;
	private double transactionAmount,interestPaid,loanBalance,principalPaid,otherAmount,penal_amt,int_payable,extraIntPaid,extraIntAmount,principalBalance;

	public UserVerifier uv=new UserVerifier();

	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}
	

	public int getAccNo(){return accNo;}
	public void setAccNo(int acno){this.accNo=acno;}

	public String getTranType(){return trnty;}
	public void setTranType(String trnty){this.trnty=trnty;}

	public String getTranNarration(){return tranNarration;}
	public void setTranNarration(String trnnarr){this.tranNarration=trnnarr;}
	
	public String getTranMode(){return trnmode;}
	public void setTranMode(String trnmode){this.trnmode=trnmode;}
	
	public String getTranSou(){return trnsou;}
	public void setTranSou(String trnsou){this.trnsou=trnsou;}
	
	public String getCdind(){return cdind;}
	public void setCdind(String cdind){this.cdind=cdind;}

	public int getTranSequence(){return trnseq;}
	public void setTranSequence(int trnseq){this.trnseq=trnseq;}

	public int getReferenceNo(){return refno;}
	public void setReferenceNo(int refno){this.refno=refno;}

	public double getLoanBalance(){return loanBalance;}
	public void setLoanBalance(double amount){this.loanBalance=amount;}

	public double getTransactionAmount(){return transactionAmount;}
	public void setTransactionAmount(double amount){this.transactionAmount=amount;}

	public double getOtherAmount(){return otherAmount;}
	public void setOtherAmount(double amount){this.otherAmount=amount;}

	public double getPenaltyAmount(){return penal_amt;}
	public void setPenaltyAmount(double amount){this.penal_amt=amount;}

	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double amount){this.interestPaid=amount;}

	public double getPrincipalPaid(){return principalPaid;}
	public void setPrincipalPaid(double amount){this.principalPaid=amount;}
	public double getExtraIntPaid(){return extraIntPaid;}
	public void setExtraIntPaid(double extraIntPaid){this.extraIntPaid=extraIntPaid;}


	public double getInterestPayable(){return int_payable;}
	public void setInterestPayable(double amount){this.int_payable=amount;}

	public String getIntUptoDate(){return paid_upto;}
	public void setIntUptoDate(String acctype){this.paid_upto=acctype;}

	public String getTransactionDate(){return trndate;}
	public void setTransactionDate(String acctype){this.trndate=acctype;}

	public String getRecoveryDate(){return rcy_dt;}
	public void setRecoveryDate(String acctype){this.rcy_dt=acctype;}
	
	public UserVerifier getUv() {
		return uv;
	}
	public void setUv(UserVerifier uv) {
		this.uv = uv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getExtraIntAmount() {
		return extraIntAmount;
	}
	public void setExtraIntAmount(double extraIntAmount) {
		this.extraIntAmount = extraIntAmount;
	}
	public double getPrincipalBalance() {
		return principalBalance;
	}
	public void setPrincipalBalance(double principalBalance) {
		this.principalBalance = principalBalance;
	}

	


}
