package masterObject.loans;
import java.io.Serializable;

public class LoanIntRateObject implements Serializable
{

	String fdate,tdate;
	double rate,penalrate;


	public String getFromDate(){return fdate;}
	public void setFromDate(String fdate){this.fdate=fdate;}

	public String getToDate(){return tdate;}
	public void setToDate(String tdate){this.tdate=tdate;}

	public double getIntRate(){return rate;}
	public void setIntRate(double rate){this.rate=rate;}

	public double getPenalRate(){return penalrate;}
	public void setPenalRate(double rate){this.penalrate=rate;}

}
