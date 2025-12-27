package masterObject.clearing;

public class ReasonMaster implements java.io.Serializable
{
	int int_code;
	String string_reason ,de_tml,de_user,de_date;

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
	public int getCode(){ return int_code;}
	public void setCode(int int_code){this.int_code=int_code;}

	public String getReason(){return string_reason;}
	public void setReason(String string_reason){this.string_reason=string_reason;}
}