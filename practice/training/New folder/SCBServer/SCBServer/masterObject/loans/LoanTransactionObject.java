package masterObject.loans;

import masterObject.general.UserVerifier;

import java.io.Serializable;

public class LoanTransactionObject implements Serializable
{
	String accType,trnty,trnmode,tranNarration,trnsou,cdind,paid_upto,transactionDate,rcy_dt,name;
	int accNo,trnseq,referenceNo,count;
	double transactionAmount,interestPaid,loanBalance,principalPaid,otherAmount,penaltyAmount,interestPayable,extra_int,intrate,principalPayable,prn_bal;

	public UserVerifier uv=new UserVerifier();
	private double max_amt_loan_ind;
	private double max_amt_loan_ins;


	public void setName(String name){this.name=name;}
	public String getName(){return name;}
	
	public String getAccType(){return accType;}
	public void setAccType(String actype){this.accType=actype;}

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

	public int getReferenceNo(){return referenceNo;}
	public void setReferenceNo(int refno){this.referenceNo=refno;}

	public double getLoanBalance(){return loanBalance;}
	public void setLoanBalance(double amount){this.loanBalance=amount;}

	public double getTransactionAmount(){return transactionAmount;}
	public void setTransactionAmount(double amount){this.transactionAmount=amount;}

	public double getOtherAmount(){return otherAmount;}
	public void setOtherAmount(double amount){this.otherAmount=amount;}

	public double getInterestRate(){return intrate;}
	public void setInterestRate(double amount){this.intrate=amount;}

	public double getExtraIntAmount(){return extra_int;}
	public void setExtraIntAmount(double amount){this.extra_int=amount;}

	public double getPenaltyAmount(){return penaltyAmount;}
	public void setPenaltyAmount(double amount){this.penaltyAmount=amount;}

	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double amount){this.interestPaid=amount;}

	public double getPrincipalPaid(){return principalPaid;}
	public void setPrincipalPaid(double amount){this.principalPaid=amount;}

	public double getPrincipalBalance(){return prn_bal;}
	public void setPrincipalBalance(double amount){this.prn_bal=amount;}

	public double getPrincipalPayable(){return principalPayable;}
	public void setPrincipalPayable(double amount){this.principalPayable=amount;}

	public double getInterestPayable(){return interestPayable;}
	public void setInterestPayable(double amount){this.interestPayable=amount;}

	public String getIntUptoDate(){return paid_upto;}
	public void setIntUptoDate(String acctype){this.paid_upto=acctype;}

	public String getTransactionDate(){return transactionDate;}
	public void setTransactionDate(String acctype){this.transactionDate=acctype;}

	public String getRecoveryDate(){return rcy_dt;}
	public void setRecoveryDate(String acctype){this.rcy_dt=acctype;}

	public void setIndMaxAmount(double max_amt_loan_ind) {
		this.max_amt_loan_ind=max_amt_loan_ind;	
	}
	public double getIndMaxAmount(){return max_amt_loan_ind;}
	
	public void setInsMaxAmount(double max_amt_loan_ins) {
		this.max_amt_loan_ins=max_amt_loan_ins;	
	}
	public double getInsMaxAmount(){return max_amt_loan_ins;}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public UserVerifier getUv() {
		return uv;
	}
	public void setUv(UserVerifier uv) {
		this.uv = uv;
	}
    	
}
