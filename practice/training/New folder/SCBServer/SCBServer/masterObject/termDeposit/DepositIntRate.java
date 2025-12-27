package masterObject.termDeposit; 

import java.io.*;


import masterObject.general.UserVerifier;

public class DepositIntRate implements Serializable
{
	String string_acc_type,string_date_from,dpdl_date,prod_date,lmt_hdg,rinve_prod_date,string_date_to,qtr_defn,hyr_defn,yr_defn,de_tml,de_user,de_date,de_time,mod_ty;
	int int_days_from,int_days_to,int_category,month,srl_no,fr_lmt,to_lmt;
	double double_int_rate,double_min_amt,double_max_amt,extra_int_rate,extra_lnint_rate;
	
	public UserVerifier obj_userverifier = new UserVerifier();
	
	public void setDPAccType(String string_acc_type){this.string_acc_type=string_acc_type;}
	public String getDPAccType(){return string_acc_type;}
	
	public void setDateFrom(String string_date_from){this.string_date_from=string_date_from;}
	public String getDateFrom(){return string_date_from;}
	
	public void setDateTo(String string_date_to){this.string_date_to=string_date_to;}
	public String getDateTo(){return string_date_to;}
	
	public void setDaysFrom(int int_days_from){this.int_days_from=int_days_from;}
	public int getDaysFrom(){return int_days_from;}
	
	public void setDaysTo(int int_days_to){this.int_days_to=int_days_to;}
	public int getDaysTo(){return int_days_to;}
	
	public void setCategory(int int_category){this.int_category=int_category;}
	public int getCategory(){return int_category;}
	
	public void setIntRate(double double_int_rate){this.double_int_rate=double_int_rate;}
	public double getIntRate(){return double_int_rate;}	 
	
	public void setMinAmt(double double_min_amt){this.double_min_amt=double_min_amt;}
	public double getMinAmt(){return double_min_amt;}	
	
	public void setMaxAmt(double double_max_amt){this.double_max_amt=double_max_amt;}
	public double getMaxAmt(){return double_max_amt;}
	
	
	//  Code Added By Sanjeet..
	
	
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}
	public String getDe_time() {
		return de_time;
	}
	public void setDe_time(String de_time) {
		this.de_time = de_time;
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
	public String getDpdl_date() {
		return dpdl_date;
	}
	public void setDpdl_date(String dpdl_date) {
		this.dpdl_date = dpdl_date;
	}
	
	public String getHyr_defn() {
		return hyr_defn;
	}
	public void setHyr_defn(String hyr_defn) {
		this.hyr_defn = hyr_defn;
	}
	public String getLmt_hdg() {
		return lmt_hdg;
	}
	public void setLmt_hdg(String lmt_hdg) {
		this.lmt_hdg = lmt_hdg;
	}
	public String getMod_ty() {
		return mod_ty;
	}
	public void setMod_ty(String mod_ty) {
		this.mod_ty = mod_ty;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getProd_date() {
		return prod_date;
	}
	public void setProd_date(String prod_date) {
		this.prod_date = prod_date;
	}
	public String getQtr_defn() {
		return qtr_defn;
	}
	public void setQtr_defn(String qtr_defn) {
		this.qtr_defn = qtr_defn;
	}
	public String getRinve_prod_date() {
		return rinve_prod_date;
	}
	public void setRinve_prod_date(String rinve_prod_date) {
		this.rinve_prod_date = rinve_prod_date;
	}
	public int getSrl_no() {
		return srl_no;
	}
	public void setSrl_no(int srl_no) {
		this.srl_no = srl_no;
	}
	
	
	public int getFr_lmt() {
		return fr_lmt;
	}
	
	public void setFr_lmt(int fr_lmt) {
		this.fr_lmt = fr_lmt;
	}
	
	public int getTo_lmt() {
		return to_lmt;
	}
	
	public void setTo_lmt(int to_lmt) {
		this.to_lmt = to_lmt;
	}
	public String getYr_defn() {
		return yr_defn;
	}
	public void setYr_defn(String yr_defn) {
		this.yr_defn = yr_defn;
	}
	public double getExtra_int_rate() {
		return extra_int_rate;
	}
	public void setExtra_int_rate(double extra_int_rate) {
		this.extra_int_rate = extra_int_rate;
	}
	public double getExtra_lnint_rate() {
		return extra_lnint_rate;
	}
	public void setExtra_lnint_rate(double extra_lnint_rate) {
		this.extra_lnint_rate = extra_lnint_rate;
	}
	
	
}