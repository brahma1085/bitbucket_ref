package masterObject.pygmyDeposit;

import java.io.Serializable;
public class PygmyRateObject implements Serializable {
	String pgtype,fromdate,todate,agenttype,de_user,de_tml,de_date;
	int from,to;
	double intRate,commissionrate=0.0,sec_dep_rate=0.0,min_amt=0.0,max_amt=0.0;
	
	public String getACType(){return pgtype;}
	public void setACType(String pg){this.pgtype=pg;}
	
	public int getPeriodFrom(){return from;}
	public void setPeriodFrom(int fro){this.from=fro;}
	
	public String getFromDate(){return fromdate;}
	public void setFromDate(String frdate){this.fromdate=frdate;}
	
	public String getToDate(){return todate;}
	public void setToDate(String todate){this.todate=todate;}
	
	public int getPeriodTo(){return to;}
	public void setPeriodTo(int to){this.to=to;}
	
	public double getIntRate(){return intRate;}
	public void setIntRate(double rate){this.intRate=rate;}
	
	public void setAgentType(String agenttype){this.agenttype=agenttype;}
	public String getAgentType(){return agenttype;}
	
	public void setCommissionRate(double commissionrate){this.commissionrate=commissionrate;}
	public double getCommissionRate(){return commissionrate;} 
	
	public void setMinAmt(double min_amt){this.min_amt=min_amt;}
	public double getMinAmt(){return min_amt;}
	
	public void setMaxAmt(double max_amt){this.max_amt=max_amt;}
	public double getMaxAmt(){return max_amt;}
	
	public void setSecurityDepRate(double sec_dep_rate){this.sec_dep_rate=sec_dep_rate;}
	public double getSecurityDepRate(){return sec_dep_rate;}
	
	public void setDeUser(String de_user){this.de_user=de_user;}
	public String getDeUser(){return de_user;}
	
	public void setDeTml(String de_tml){this.de_tml=de_tml;}
	public String getDeTml(){return de_tml;}
	
	public void setDeDate(String de_date){this.de_date=de_date;}
	public String getDeDate(){return de_date;}

}
