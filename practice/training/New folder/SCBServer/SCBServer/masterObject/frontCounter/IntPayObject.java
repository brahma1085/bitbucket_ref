package masterObject.frontCounter;
import java.io.Serializable;


public class IntPayObject implements Serializable
{
	int ac_no;
	String name,calby,ac_ty,trn_dt;
	public double prod[],int_amt,int_rate;
	
	public int getAccNo(){return ac_no;}
	public void setAccNo(int acno){this.ac_no=acno;}	
	
	public String getName(){return name;}
	public void setName(String name){this.name=name;}	
	
	public String getAccType(){return ac_ty;}
	public void setAccType(String name){this.ac_ty=name;}
	
	public String getTrnDate(){return trn_dt;}
	public void setTrnDate(String trn_dt){this.trn_dt=trn_dt;}
	
	public String getCalBy(){return calby;}
	public void setCalBy(String name){this.calby=name;}	
	
	public double getIntAmt(){return int_amt;}
	public void setIntAmt(double int_amt){this.int_amt=int_amt;}	
	
	public double getIntRate(){return int_rate;}
	public void setIntRate(double int_amt){this.int_rate=int_amt;}	
	
	public double[] getProducts(){return prod;}
	public void setProducts(double[] prod){this.prod=prod;}	
	
	public void initarray(int a)
	{
	prod=new double[a];	
		
	}
	
}