package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.io.Serializable;
import java.util.Hashtable;

public class LoanTransactionObject implements Serializable
{
	String lnactype,trnty,trnmode,trnnarr,trnsou,cdind,paid_upto,trndate,rcy_dt,name;
	int lnacno,trnseq,refno,sbaccno;
	double trn_amt,int_paid,ln_bal,prn_paid,other_amt,penal_amt,int_payable,extra_int,intrate,prn_payable,prn_bal;

	Hashtable trn_date,trn_seq,trn_type,amt,trn_mode,int_date,hash_loan;
	
	private double max_amt_loan_ind;
	private double max_amt_loan_ins;


	public void setName(String name){this.name=name;}
	public String getName(){return name;}
	
	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}

	public int getAccNo(){return lnacno;}
	public void setAccNo(int acno){this.lnacno=acno;}

	public String getTranType(){return trnty;}
	public void setTranType(String trnty){this.trnty=trnty;}

	public String getTranNarration(){return trnnarr;}
	public void setTranNarration(String trnnarr){this.trnnarr=trnnarr;}
	
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

	public double getLoanBalance(){return ln_bal;}
	public void setLoanBalance(double amount){this.ln_bal=amount;}

	public double getTransactionAmount(){return trn_amt;}
	public void setTransactionAmount(double amount){this.trn_amt=amount;}

	public double getOtherAmount(){return other_amt;}
	public void setOtherAmount(double amount){this.other_amt=amount;}

	public double getInterestRate(){return intrate;}
	public void setInterestRate(double amount){this.intrate=amount;}

	public double getExtraIntAmount(){return extra_int;}
	public void setExtraIntAmount(double amount){this.extra_int=amount;}

	public double getPenaltyAmount(){return penal_amt;}
	public void setPenaltyAmount(double amount){this.penal_amt=amount;}

	public double getInterestPaid(){return int_paid;}
	public void setInterestPaid(double amount){this.int_paid=amount;}

	public double getPrincipalPaid(){return prn_paid;}
	public void setPrincipalPaid(double amount){this.prn_paid=amount;}

	public double getPrincipalBalance(){return prn_bal;}
	public void setPrincipalBalance(double amount){this.prn_bal=amount;}

	public double getPrincipalPayable(){return prn_payable;}
	public void setPrincipalPayable(double amount){this.prn_payable=amount;}

	public double getInterestPayable(){return int_payable;}
	public void setInterestPayable(double amount){this.int_payable=amount;}

	public String getIntUptoDate(){return paid_upto;}
	public void setIntUptoDate(String acctype){this.paid_upto=acctype;}

	public String getTransactionDate(){return trndate;}
	public void setTransactionDate(String acctype){this.trndate=acctype;}

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
	public Hashtable getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(Hashtable trn_date) {
		this.trn_date = trn_date;
	}
	public Hashtable getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(Hashtable trn_seq) {
		this.trn_seq = trn_seq;
	}
	public Hashtable getTrn_type() {
		return trn_type;
	}
	public void setTrn_type(Hashtable trn_type) {
		this.trn_type = trn_type;
	}
	public Hashtable getAmt() {
		return amt;
	}
	public void setAmt(Hashtable amt) {
		this.amt = amt;
	}
	public Hashtable getTrn_mode() {
		return trn_mode;
	}
	public void setTrn_mode(Hashtable trn_mode) {
		this.trn_mode = trn_mode;
	}
	public Hashtable getInt_date() {
		return int_date;
	}
	public void setInt_date(Hashtable int_date) {
		this.int_date = int_date;
	}
	public Hashtable getHash_loan() {
		return hash_loan;
	}
	public void setHash_loan(Hashtable hash_loan) {
		this.hash_loan = hash_loan;
	}
	public int getSbaccno() {
		return sbaccno;
	}
	public void setSbaccno(int sbaccno) {
		this.sbaccno = sbaccno;
	}
    	
}
