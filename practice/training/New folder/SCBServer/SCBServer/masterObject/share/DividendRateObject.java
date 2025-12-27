package masterObject.share;

import java.io.Serializable;

public class DividendRateObject implements Serializable
{

	String name,lstdivdt;
	int flag;
	double divamt;

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	public double getAmount(){return divamt;}
	public void setAmount(double divamt){this.divamt=divamt;}

	public int getFlag(){return flag;}
	public void setFlag(int flag){this.flag=flag;}

	public String getLastDivDate(){return lstdivdt;}
	public void setLastDivDate(String lstdivdt){this.lstdivdt=lstdivdt;}

	String fdate,tdate,caldone,calopt;
	double rate,amt;

	public String getFromDate(){return fdate;}
	public void setFromDate(String fdate){this.fdate=fdate;}

	public String getToDate(){return tdate;}
	public void setToDate(String tdate){this.tdate=tdate;}

	public double getRate(){return rate;}
	public void setRate(double rate){this.rate=rate;}

	public String getCalDone(){return caldone;}
	public void setCalDone(String caldone){this.caldone=caldone;}
	
	public void setCalopt(String cal_opt){this.calopt=cal_opt;}//Karthi
	public String getCalopt(){return calopt;}





}
