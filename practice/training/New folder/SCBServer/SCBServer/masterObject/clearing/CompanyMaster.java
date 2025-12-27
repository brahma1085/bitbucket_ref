package masterObject.clearing;

public class CompanyMaster implements java.io.Serializable
{
	String companyName,string_ac_type,string_loan_ac_type,deTml,string_de_user;
	String string_de_dt_time,string_ve_tml,string_ve_user,string_ve_dt_time,de_date,date;
	int accNo,int_comp_no,int_loan_ac_no;
	
	public int getCompanyCode(){return int_comp_no;}
	public void setCompanyCode(int int_comp_no){this.int_comp_no=int_comp_no;}

	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getAccNo() {
		return accNo;
	}
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}
	public String getAccType(){return string_ac_type;}
	public void setAccType(String string_ac_type){this.string_ac_type=string_ac_type;}

	
	
	public String getLoanAccType(){return string_loan_ac_type;}
	public void setLoanAccType(String string_loan_ac_type){this.string_loan_ac_type=string_loan_ac_type;}

	public int getLoanAccNo(){return int_loan_ac_no;}
	public void setLoanAccNo(int int_loan_ac_no){this.int_loan_ac_no=int_loan_ac_no;}


	
	public String getDeTml() {
		return deTml;
	}
	public void setDeTml(String deTml) {
		this.deTml = deTml;
	}
	public String getDeUser(){return string_de_user;}
	public void setDeUser(String string_de_user){this.string_de_user=string_de_user;}

	public String getDeTime(){return string_de_dt_time;}
	public void setDeTime(String string_de_dt_time){this.string_de_dt_time=string_de_dt_time;}

	public String getVeTml(){return string_ve_tml;}
	public void setVeTml(String string_ve_tml){this.string_ve_tml=string_ve_tml;}

	public String getVeUser(){return string_ve_user;}
	public void setVeUser(String string_ve_user){this.string_ve_user=string_ve_user;}

	public String getVeTime(){return string_ve_dt_time;}
	public void setVeTime(String string_ve_dt_time){this.string_ve_dt_time=string_ve_dt_time;}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_time) {
		this.de_date = de_time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
