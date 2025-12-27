package masterObject.loans;


public class DetailsObject implements java.io.Serializable{
	String mod,actype;
	int acno;
	double bal;
	String opendt,closedt;

	public void setMainModule(String mod){this.mod=mod;}
	public String getMainModule(){return mod;}

	public void setAccType(String actype){this.actype=actype;}
	public String getAccType(){return actype;}

	public void setAccNo(int acno){this.acno=acno;}
	public int getAccNo(){return acno;}

	public void setBalance(double bal){this.bal=bal;}
	public double getBalance(){return bal;}

	public void setOpenDate(String opendt){this.opendt=opendt;}
	public String getOpenDate(){return opendt;}

	public void setCloseDate(String cldate){closedt=cldate;}
	public String getCloseDate(){return closedt;}
}
