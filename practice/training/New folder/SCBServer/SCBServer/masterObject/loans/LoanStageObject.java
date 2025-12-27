package masterObject.loans;


public class LoanStageObject implements java.io.Serializable{
	
	String lntype,actdt,stdesc,actpert,name,closedt;
	int acno,stage_cd;

	public void setLoanType(String lntype){this.lntype=lntype;}
	public String getLoanType(){return lntype;}

	public void setAccNo(int acno){this.acno=acno;}
	public int getAccNo(){return acno;}
	
	public void setStageCode(int acno){this.stage_cd=acno;}
	public int getStageCode(){return stage_cd;}

	public void setDescription(String desc){stdesc=desc;}
	public String getDescription(){return stdesc;}
	
	public void setActionDate(String desc){actdt=desc;}
	public String getActionDate(){return actdt;}
	
	public void setActionParticulars(String desc){actpert=desc;}
	public String getActionParticulars(){return actpert;}
	
	public void setCloseDate(String desc){closedt=desc;}
	public String getCloseDate(){return closedt;}
	
	public void setName(String desc){name=desc;}
	public String getName(){return name;}


}
