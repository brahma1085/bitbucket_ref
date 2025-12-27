package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.io.Serializable;
import java.util.Hashtable;

public class DepositObject implements Serializable {
	
	int trn_seq;
	String trn_date,interest_date,trn_mode,trn_type;
	double dep_amt,int_amt,rd_bal,cum_int;
	public double getCum_int() {
		return cum_int;
	}
	public void setCum_int(double cum_int) {
		this.cum_int = cum_int;
	}
	public double getDep_amt() {
		return dep_amt;
	}
	public void setDep_amt(double dep_amt) {
		this.dep_amt = dep_amt;
	}
	public double getInt_amt() {
		return int_amt;
	}
	public void setInt_amt(double int_amt) {
		this.int_amt = int_amt;
	}
	public String getInterest_date() {
		return interest_date;
	}
	public void setInterest_date(String interest_date) {
		this.interest_date = interest_date;
	}
	public double getRd_bal() {
		return rd_bal;
	}
	public void setRd_bal(double rd_bal) {
		this.rd_bal = rd_bal;
	}
	public String getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	public int getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(int trn_seq) {
		this.trn_seq = trn_seq;
	}
	public String getTrn_mode() {
		return trn_mode;
	}
	public void setTrn_mode(String trn_mode) {
		this.trn_mode = trn_mode;
	}
	public String getTrn_type() {
		return trn_type;
	}
	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}
	

}
