package masterObject.frontCounter;

import java.io.Serializable;

public class AdminObject implements Serializable{
	String fr_date,ac_type,subcat,date,de_user,de_tml,de_date,to_date,type_of_sec;
	double int_rate,extra_rate,percentage;
	String relation;
	int tokennum,cid,dircode,days_fr,days_to,cat;
	String col_names[];
	Object data[][];
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public double getExtra_rate() {
		return extra_rate;
	}
	public void setExtra_rate(double extra_rate) {
		this.extra_rate = extra_rate;
	}
	public double getInt_rate() {
		return int_rate;
	}
	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}
	public String getSubcat() {
		return subcat;
	}
	public void setSubcat(String subcat) {
		this.subcat = subcat;
	}
	public String[] getCol_names() {
		return col_names;
	}
	public void setCol_names(String[] col_names) {
		this.col_names = col_names;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	public int getTokennum() {
		return tokennum;
	}
	public void setTokennum(int tokennum) {
		this.tokennum = tokennum;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public int getDircode() {
		return dircode;
	}
	public void setDircode(int dircode) {
		this.dircode = dircode;
	}
	public String getType_of_sec() {
		return type_of_sec;
	}
	public void setType_of_sec(String type_of_sec) {
		this.type_of_sec = type_of_sec;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public int getDays_fr() {
		return days_fr;
	}
	public void setDays_fr(int days_fr) {
		this.days_fr = days_fr;
	}
	public int getDays_to() {
		return days_to;
	}
	public void setDays_to(int days_to) {
		this.days_to = days_to;
	}
	public int getCat() {
		return cat;
	}
	public void setCat(int cat) {
		this.cat = cat;
	}
	public String getFr_date() {
		return fr_date;
	}
	public void setFr_date(String fr_date) {
		this.fr_date = fr_date;
	}

}
