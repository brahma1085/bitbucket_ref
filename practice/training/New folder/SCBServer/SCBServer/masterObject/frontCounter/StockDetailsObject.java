package masterObject.frontCounter;
import masterObject.general.UserVerifier;
import java.io.Serializable;

public class StockDetailsObject implements Serializable
{
	String actype,sancdate,name,mat_date,prev_insp_date,insp_date,nxt_insp_date;
	double prev_credit_lmt,prev_stk_val,cur_credit_lmt,insp_credit_lmt;
	int acno,modcode;
	int cid;
	private double stockValue;
	double sanc_limit;

	String ac_sta;
	public UserVerifier uv=new UserVerifier();
	private String lnpus;
	private String nature;
	private int period;

	public String getAccountStatus(){return ac_sta;}
	public void setAccountStatus(String loan_sta){this.ac_sta=loan_sta;}

	public int getCustomerId(){return cid;}
	public void setCustomerId(int no){this.cid=no;}


	public String getAccType(){return actype;}
	public void setAccType(String actype){this.actype=actype;}


	public int getAccNo(){return acno;}
	public void setAccNo(int acno){this.acno=acno;}

	public int getModuleCode(){return modcode;}
	public void setModuleCode(int acno){this.modcode=acno;}


	public String getSanctionDate(){return sancdate;}
	public void setSanctionDate(String acctype){this.sancdate=acctype;}

	public String getMaturutyDate(){return mat_date;}
	public void setMaturityDate(String acctype){this.mat_date=acctype;}


	public double getSanctionedLimit(){return sanc_limit;}
	public void setSanctionedLimit(double amount){this.sanc_limit=amount;}


	public double getPrevCreditLimit(){return prev_credit_lmt;}
	public void setPrevCreditLimit(double amount){this.prev_credit_lmt=amount;}

	public double getPrevStockValue(){return prev_stk_val;}
	public void setPrevStockValue(double amount){this.prev_stk_val=amount;}

	
	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	
	public String getInspDate(){return insp_date;}
	public void setInspDate(String name){this.insp_date=name;}
	
	public String getNextInspDate(){return nxt_insp_date;}
	public void setNextInspDate(String name){this.nxt_insp_date=name;}

	public String getPrevInspectionDate(){return prev_insp_date;}
	public void setPrevInspectionDate(String name){this.prev_insp_date=name;}
	
	public double getCurrentCreditLimit(){return cur_credit_lmt;}
	public void setCurrentCreditLimit(double amount){this.cur_credit_lmt=amount;}

	public double getInspCreditLimit(){return insp_credit_lmt;}
	public void setInspCreditLimit(double amount){this.insp_credit_lmt=amount;}

	
	public void setStockValue(double stockValue) {
		this.stockValue=stockValue;	
	}
	
	public double getStockValue(){ return stockValue;}
	public void setLoanPurpose(String string) {
		this.lnpus=string;
		
	}
	public String getLoanPurpose() {return lnpus;}
	public void setNatureOfBusiness(String string) {
		this.nature=string;
		
	}
	public String getNatureOfBusiness() {return nature;}
	public void setPeriod(int int1) {
		this.period=int1;
		
	}
	public int getPeriod()
	{
		return period;
	}
}
