package masterObject.clearing;

public class DiscountCharges implements java.io.Serializable
{
	double from_amt,to_amt,fine_amt,int_rate;
	String int_type,ac_type,de_tml,de_user,de_date;
	
	public double getFromAmt(){return from_amt;}
	public void setFromAmt(double from_amt){this.from_amt=from_amt;}

	public double getToAmt(){return to_amt;}
	public void setToAmt(double to_amt){this.to_amt=to_amt;}

	public double getFineAmt(){return fine_amt;}
	public void setFineAmt(double fine_amt){this.fine_amt=fine_amt;}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
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
	public double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}
	public String getInt_type() {
		return int_type;
	}
	public void setInt_type(String int_type) {
		this.int_type = int_type;
	}
	
}