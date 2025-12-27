package masterObject.termDeposit; 

import java.io.Serializable;

public class DepositIntRepObject implements Serializable
{
	String accType,name,interestFrq,interestUpto,interestMode,narration,state,int_upto_date,int_freq,trn_date,trn_type,ac_type;
	int accno,int_refno,cid,ac_no,trn_seq;
	double depositAmt,interestRate,interestAmt,interestPaid,double_maturity_amt,double_lstbalance,dep_amt,int_rate;
	

	
	
	public String getAccType(){return accType;}
	public void setAccType(String string_dpactype){this.accType=string_dpactype;}

	public int getAccNo(){return accno;}
	public void setAccNo(int int_dpacno){this.accno=int_dpacno;}

	public int getRefNo(){return int_refno;}
	public void setRefNo(int int_refno){this.int_refno=int_refno;}


	public String getName(){return name;}
	public void setName(String string_name){this.name=string_name;}
	
	public String getInterestFrq(){return interestFrq;}
	public void setInterestFrq(String string_int_freq){this.interestFrq=string_int_freq;}

	public String getInterestUpto(){return interestUpto;}
	public void setInterestUpto(String string_intuptodate){this.interestUpto=string_intuptodate;}
	
	public String getInterestMode(){return interestMode;}
	public void setInterestMode(String string_int_mode){this.interestMode=string_int_mode;}
	
	public String getNarration(){return narration;}
	public void setNarration(String string_narration){this.narration=string_narration;}

	public String getState(){return state;}
	public void setState(String string_state){this.state=string_state;}
	
	
	public double getDepositAmt(){return depositAmt;}
	public void setDepositAmt(double double_dep_amt){this.depositAmt=double_dep_amt;}

	public double getMaturityAmt(){return double_maturity_amt;}
	public void setMaturityAmt(double double_maturity_amt){this.double_maturity_amt=double_maturity_amt;}
	
	public double getInterestRate(){return interestRate;}
	public void setInterestRate(double double_interest_rate){this.interestRate=double_interest_rate;}
	
	public double getInterestAmt(){return interestAmt;}
	public void setInterestAmt(double double_interest_amount){this.interestAmt=double_interest_amount;}

	public double getInterestPaid(){return interestPaid;}
	public void setInterestPaid(double double_interest_paid){this.interestPaid=double_interest_paid;}

	public double getLastBalance(){return double_lstbalance;}
	public void setLastBalance(double double_lstbalance){this.double_lstbalance=double_lstbalance;}
	public String getInt_upto_date() {
		return int_upto_date;
	}
	public void setInt_upto_date(String int_upto_date) {
		this.int_upto_date = int_upto_date;
	}
	public String getInt_freq() {
		return int_freq;
	}
	public void setInt_freq(String int_freq) {
		this.int_freq = int_freq;
	}
	public String getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	public String getTrn_type() {
		return trn_type;
	}
	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	public int getInt_refno() {
		return int_refno;
	}
	public void setInt_refno(int int_refno) {
		this.int_refno = int_refno;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
	public int getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(int trn_seq) {
		this.trn_seq = trn_seq;
	}
	public double getDouble_maturity_amt() {
		return double_maturity_amt;
	}
	public void setDouble_maturity_amt(double double_maturity_amt) {
		this.double_maturity_amt = double_maturity_amt;
	}
	public double getDouble_lstbalance() {
		return double_lstbalance;
	}
	public void setDouble_lstbalance(double double_lstbalance) {
		this.double_lstbalance = double_lstbalance;
	}
	public double getDep_amt() {
		return dep_amt;
	}
	public void setDep_amt(double dep_amt) {
		this.dep_amt = dep_amt;
	}
	public double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
}
