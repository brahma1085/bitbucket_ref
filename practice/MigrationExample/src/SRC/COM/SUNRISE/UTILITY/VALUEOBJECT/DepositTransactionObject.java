package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.util.Hashtable;
import java.util.Vector;


import java.io.Serializable;
public class DepositTransactionObject implements Serializable
{
	String string_dpacty,string_trndate,mat_date,string_trn_type,string_paid_date,string_interest_date,string_trnnarr,string_trnmode,string_trnsou,string_cdind,string_name;
	double double_depamt,double_intamt,double_deppaid,double_intpaid,double_rdbal,double_cum_int;
	int int_acno,int_refno,int_trnkey;
	long long_trnseq;
	String mat_amt;
	int dep_days;
	
	int max_renewal_days;
	//added by geetha
	Hashtable trn_seq,trn_date,dep_amt,int_amt,rd_bal,interest_date,cum_int;
	Hashtable hash_dep;
	//added by geetha
	
	String de_tml,de_user,de_date,de_id;
	
	public Hashtable getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(Hashtable trn_seq) {
		this.trn_seq = trn_seq;
	}
	public String getAccType(){return string_dpacty;}
	public void setAccType(String string_dpacty){this.string_dpacty=string_dpacty;}
	
	public Hashtable getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(Hashtable trn_date) {
		this.trn_date = trn_date;
	}
	//commented by geeth
	public String getTranDate(){return string_trndate;}
	public void setTranDate(String string_trndate){this.string_trndate=string_trndate;}	
	
	public String getTranType(){return string_trn_type;}
	public void setTranType(String string_trn_type){this.string_trn_type=string_trn_type;}	
	
	public String getPaidDate(){return string_paid_date;}
	public void setPaidDate(String string_paid_date){this.string_paid_date=string_paid_date;}
	
	public String getInterestDate(){return string_interest_date;}
	public void setInterestDate(String string_interest_date){this.string_interest_date=string_interest_date;}
	
	public String getTranNarration(){return string_trnnarr;}
	public void setTranNarration(String string_trnnarr){this.string_trnnarr=string_trnnarr;}
	
	public String getTranMode(){return string_trnmode;}
	public void setTranMode(String string_trnmode){this.string_trnmode=string_trnmode;}
	
	public String getTranSou(){return string_trnsou;}
	public void setTranSou(String string_trnsou){this.string_trnsou=string_trnsou;}
	
	public String getCdind(){return string_cdind;}
	public void setCdind(String string_cdind){this.string_cdind=string_cdind;}
	
	
	public double getDepositAmt(){return double_depamt;}
	public void setDepositAmt(double double_depamt){this.double_depamt=double_depamt;}
	
	public double getInterestAmt(){return double_intamt;}
	public void setInterestAmt(double double_intamt){this.double_intamt=double_intamt;}
	
	public double getDepositPaid(){return double_deppaid;}
	public void setDepositPaid(double double_deppaid){this.double_deppaid=double_deppaid;}
	
	public double getInterestPaid(){return double_intpaid;}
	public void setInterestPaid(double double_intpaid){this.double_intpaid=double_intpaid;}
	
	public double getRDBalance(){return double_rdbal;}
	public void setRDBalance(double double_rdbal){this.double_rdbal=double_rdbal;}
	
	public double getCumInterest(){return double_cum_int;}
	public void setCumInterest(double double_cum_int){this.double_cum_int=double_cum_int;}
	
	
	public int getAccNo(){return int_acno;}
	public void setAccNo(int int_acno){this.int_acno=int_acno;}	
	
	public int getReferenceNo(){return int_refno;}
	public void setReferenceNo(int int_refno){this.int_refno=int_refno;}
	
	public int getTranKey(){return int_trnkey;}
	public void setTranKey(int int_trnkey){this.int_trnkey=int_trnkey;}
	
	public long getTranSequence(){return long_trnseq;}
	public void setTranSequence(long long_trnseq){this.long_trnseq=long_trnseq;}

	public String getName(){return string_name;}
	public void setName(String string_name){this.string_name=string_name;}
	
	public String getMat_date() {
		return mat_date;
	}
	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}
	public String getMat_amt() {
		return mat_amt;
	}
	public void setMat_amt(String mat_amt) {
		this.mat_amt = mat_amt;
	}
	public int getMax_renewal_days() {
		return max_renewal_days;
	}
	public void setMax_renewal_days(int max_renewal_days) {
		this.max_renewal_days = max_renewal_days;
	}
	public int getDep_days() {
		return dep_days;
	}
	public void setDep_days(int dep_days) {
		this.dep_days = dep_days;
	}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}
	public String getDe_id() {
		return de_id;
	}
	public void setDe_id(String de_id) {
		this.de_id = de_id;
	}
	public String getDe_tml() {
		return de_tml;
	}
	public void setDe_tml(String de_tml) {
		this.de_tml = de_tml;
	}
	public String getDe_user() {
		return de_user;
	}
	public void setDe_user(String de_user) {
		this.de_user = de_user;
	}
	public Hashtable getCum_int() {
		return cum_int;
	}
	public void setCum_int(Hashtable cum_int) {
		this.cum_int = cum_int;
	}
	public Hashtable getDep_amt() {
		return dep_amt;
	}
	public void setDep_amt(Hashtable dep_amt) {
		this.dep_amt = dep_amt;
	}
	public Hashtable getInt_amt() {
		return int_amt;
	}
	public void setInt_amt(Hashtable int_amt) {
		this.int_amt = int_amt;
	}
	public Hashtable getInterest_date() {
		return interest_date;
	}
	public void setInterest_date(Hashtable interest_date) {
		this.interest_date = interest_date;
	}
	public Hashtable getRd_bal() {
		return rd_bal;
	}
	public void setRd_bal(Hashtable rd_bal) {
		this.rd_bal = rd_bal;
	}
	public Hashtable getHash_dep() {
		return hash_dep;
	}
	public void setHash_dep(Hashtable hash_dep) {
		this.hash_dep = hash_dep;
	}
	
}
