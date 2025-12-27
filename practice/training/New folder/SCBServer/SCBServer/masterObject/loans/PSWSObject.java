package masterObject.loans;

import java.io.Serializable;


public class PSWSObject implements Serializable
{
	
	public PSWSDetails[] det=null;
	int ps_code[];
	int prioritySectorCode,ac_no;
	String pridesc,ac_ty;
	String ps_desc[];
	String sex_cd,cat,wk_sect,name;
	
	public void setPriorityDesc(String[] a){this.ps_desc=a;}
	public String[] getPriorityDesc(){return ps_desc;}
	
	public void setPriorityCode(int[] a){this.ps_code=a;}
	public int[] getPriorityCode(){return ps_code;}
	
	public int getPrioritySectorCode(){return prioritySectorCode;}
	public void setPrioritySectorCode(int remd_no){this.prioritySectorCode=remd_no;}
	
	public int getAccNo(){return ac_no;}
	public void setAccNo(int remd_no){this.ac_no=remd_no;}
	
	public String getAccType(){return ac_ty;}
	public void setAccType(String remd_no){this.ac_ty=remd_no;}
		
	public String getPrioritySectorDesc(){return pridesc;}
	public void setPrioritySectorDesc(String a){this.pridesc=a;}
	
	public String getSexInd(){return sex_cd;}
	public void setSexInd(String a){this.sex_cd=a;}
	
	public String getCategory(){return cat;}
	public void setCategory(String a){this.cat=a;}
	
	public String getWKSect(){return wk_sect;}
	public void setWKSect(String a){this.wk_sect=a;}
	
	public String getName(){return name;}
	public void setName(String a){this.name=a;}

public void initializeArray(int j)
{
	det=new PSWSDetails[j];
}

public void initialize(int a)
{
	det[a]=new PSWSDetails();

}	
	



	
/*public void initializeArray(int j)
{
	det=new Details[j];
	for(int i=0;i<j;i++)
	{
		det[i]=new Details();
	}

}*/

	
}



