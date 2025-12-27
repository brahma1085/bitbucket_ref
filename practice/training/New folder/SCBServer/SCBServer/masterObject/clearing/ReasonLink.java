package masterObject.clearing;

public class  ReasonLink implements java.io.Serializable
{
	String string_reason,de_tml,de_user,de_date;
	int int_code,int_rcode;
	
	public int getLinkCode(){ return int_code;}
	public void setLinkCode(int int_code){this.int_code=int_code;}

	public String getLinkDiscription(){return string_reason;}
	public void setLinkDiscription(String string_reason){this.string_reason=string_reason;}
	
	public int getReasonCode(){ return int_rcode;}
	public void setReasonCode(int int_rcode){this.int_rcode=int_rcode;}
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