package masterObject.termDeposit;

import masterObject.general.UserVerifier;
import java.io.Serializable;
public class DepositTransactionObject implements Serializable
{
	String accType,tranDate,tranType,paidDate,interestDate,tranNarration,string_trnmode,tranSou,cdind,string_name;
	double depositAmt,interestAmt,depositPaid,interestPaid,double_cum_int,rDBalance;
	int accNo,referenceNo,tranKey;
	long tranSequence;
	int trn_seq;
	
	String ve_date,de_date,de_user,ve_user;
	
	
	public UserVerifier obj_userverifier=new UserVerifier();
	
	public String getAccType(){return accType;}
	public void setAccType(String string_dpacty){this.accType=string_dpacty;}
	
	public String getTranDate(){return tranDate;}
	public void setTranDate(String string_trndate){this.tranDate=string_trndate;}	
	
	public String getTranType(){return tranType;}
	public void setTranType(String string_trn_type){this.tranType=string_trn_type;}	
	
	public String getPaidDate(){return paidDate;}
	public void setPaidDate(String string_paid_date){this.paidDate=string_paid_date;}
	
	public String getInterestDate(){return interestDate;}
	public void setInterestDate(String string_interest_date){this.interestDate=string_interest_date;}
	
	public String getTranNarration(){return tranNarration;}
	public void setTranNarration(String string_trnnarr){this.tranNarration=string_trnnarr;}
	
	public String getTranMode(){return string_trnmode;}
	public void setTranMode(String string_trnmode){this.string_trnmode=string_trnmode;}
	
	public String getTranSou(){return tranSou;}
	public void setTranSou(String string_trnsou){this.tranSou=string_trnsou;}
	
	public String getCdind(){return cdind;}
	public void setCdind(String string_cdind){this.cdind=string_cdind;}
	
	
	public double getDepositAmt(){return depositAmt;}
	public void setDepositAmt(double double_depamt){this.depositAmt=double_depamt;}
	
	public double getInterestAmt(){return interestAmt;}
	public void setInterestAmt(double double_intamt){this.interestAmt=double_intamt;}
	
	public double getDepositPaid(){return depositPaid;}
	public void setDepositPaid(double double_deppaid){this.depositPaid=double_deppaid;}
	
	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double double_intpaid){this.interestPaid=double_intpaid;}
	
	
	
	public double getCumInterest(){return double_cum_int;}
	public void setCumInterest(double double_cum_int){this.double_cum_int=double_cum_int;}
	
	
	public int getAccNo(){return accNo;}
	public void setAccNo(int int_acno){this.accNo=int_acno;}	
	
	public int getReferenceNo(){return referenceNo;}
	public void setReferenceNo(int int_refno){this.referenceNo=int_refno;}
	
	public int getTranKey(){return tranKey;}
	public void setTranKey(int int_trnkey){this.tranKey=int_trnkey;}
	
	public long getTranSequence(){return tranSequence;}
	public void setTranSequence(long long_trnseq){this.tranSequence=long_trnseq;}

	public String getName(){return string_name;}
	public void setName(String string_name){this.string_name=string_name;}
	public String getVe_date() {
		return ve_date;
	}
	public void setVe_date(String ve_date) {
		ve_date = obj_userverifier.getVerDate();
	}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}
	public String getDe_user() {
		return de_user;
	}
	public void setDe_user(String de_user) {
		this.de_user = de_user;
	}
	public String getVe_user() {
		return ve_user;
	}
	public void setVe_user(String ve_user) {
		this.ve_user = obj_userverifier.getVerId();
		
	}
	public int getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(int trn_seq) {
		this.trn_seq = trn_seq;
	}
	public double getRDBalance() {
		return rDBalance;
	}
	public void setRDBalance(double balance) {
		rDBalance = balance;
	}
	public UserVerifier getObj_userverifier() {
		return obj_userverifier;
	}
	public void setObj_userverifier(UserVerifier obj_userverifier) {
		this.obj_userverifier = obj_userverifier;
	}
	
}
