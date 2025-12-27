package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.io.Serializable;

public class LoanObject implements Serializable {
    int trn_seq;
    String trn_date,int_date,trn_mode,trn_type;
    double amt;
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getInt_date() {
		return int_date;
	}
	public void setInt_date(String int_date) {
		this.int_date = int_date;
	}
	public String getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	public String getTrn_mode() {
		return trn_mode;
	}
	public void setTrn_mode(String trn_mode) {
		this.trn_mode = trn_mode;
	}
	public int getTrn_seq() {
		return trn_seq;
	}
	public void setTrn_seq(int trn_seq) {
		this.trn_seq = trn_seq;
	}
	public String getTrn_type() {
		return trn_type;
	}
	public void setTrn_type(String trn_type) {
		this.trn_type = trn_type;
	}
    
    
}
