package masterObject.clearing;

public class BankMaster implements java.io.Serializable
{
	int int_bankcode;
	String string_bankname,string_de_tml,string_de_user,string_de_dt_time,string_abbrevation;

	public int getBankCode(){return int_bankcode;}
	public void setBankCode(int int_bankcode){this.int_bankcode=int_bankcode;}

	public String getBankName(){return string_bankname;}
	public void setBankName(String string_bankname){this.string_bankname=string_bankname;}

	public String getDeTml(){return string_de_tml;}
	public void setDeTml(String string_de_tml){this.string_de_tml=string_de_tml;}

	public String getDeUser(){return string_de_user;}
	public void setDeUser(String string_de_user){this.string_de_user=string_de_user;}

	public String getDeTime(){return string_de_dt_time;}
	public void setDeTime(String string_de_dt_time){this.string_de_dt_time=string_de_dt_time;}
	public String getabbrevation() {
		return string_abbrevation;
	}
	public void setabbrevation(String string_abbrevation) {
		this.string_abbrevation = string_abbrevation;
	}
}
