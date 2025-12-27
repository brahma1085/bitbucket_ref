package masterObject.loansOnDeposit;

import masterObject.general.UserVerifier;
import java.io.Serializable;

public class LoanPurposeObject implements Serializable
{
	private String purposeDesc;  
	private int purposeCode;
	

	 public UserVerifier uv=new UserVerifier();

	public String getPurposeDesc(){ return purposeDesc;}
	public void setPurposeDesc(String modd){this.purposeDesc=modd;}

	public int getPurposeCode(){ return purposeCode;}
	public void setPurposeCode(int modc){this.purposeCode=modc;}
	
}
