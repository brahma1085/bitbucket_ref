package masterObject.clearing;

public class BounceFineObject implements java.io.Serializable
{
	int int_bankcode;
	String string_acc_type,de_tml,de_user,de_date;
	double double_fine,double_ret_fine,double_mail_chg,double_sms_chg,double_email_chg;

	public int getBounceCode(){return int_bankcode;}
	public void setBounceCode(int int_bankcode){this.int_bankcode=int_bankcode;}

	public String getAccountType(){return string_acc_type;}
	public void setAccountType(String string_acc_type){this.string_acc_type=string_acc_type;}
	
	public double getFine(){return double_fine;}
	public void setFine(double double_fine){this.double_fine=double_fine;}
	
	public double getReturnFine(){return double_ret_fine;}
	public void setReturnFine(double double_ret_fine){this.double_ret_fine=double_ret_fine;}

	public double getMailingChg(){return double_mail_chg;}
	public void setMailingChg(double double_mail_chg){this.double_mail_chg=double_mail_chg;}
	
	public double getSmsChg(){return double_sms_chg;}
	public void setSmsChg(double double_sms_chg){this.double_sms_chg=double_sms_chg;}
	
	public double getEmailChg(){return double_email_chg;}
	public void setEmailChg(double double_email_chg){this.double_email_chg=double_email_chg;}
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
	
}