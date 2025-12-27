

package masterObject.parameters;

import java.io.*;

import masterObject.general.UserVerifier;


public class BranchesObject implements Serializable{
	
	private int txt_code;
	private String txt_name,de_tml,de_user,de_date;
	private int balance;
	
	 public int getCode()
	   {
	   	return txt_code;
	   }
	 
	 public void setCode(int txt_code)
	   {
	   	this.txt_code = txt_code;
	   }
	 
	 
	 public int getBalance()
	   {
	   	return balance;
	   }
	 
	 public void setBalance(int balance)
	   {
	   	this.balance = balance;
	   }
	   
	 
	 public String getName()
	   {
	   	return txt_name;
	   }

	
	  public void setName(String txt_name)
	   {
	   	this.txt_name = txt_name;
	   }
	  
	  public String getDeTml()
	   {
	   	return de_tml;
	   }

	
	  public void setDeTml(String de_tml)
	   {
	   	this.de_tml = de_tml;
	   }
	  
	  public String getDeUser()
	   {
	   	return de_user;
	   }

	
	  public void setDeUser(String de_user)
	   {
	   	this.de_user = de_user;
	   }
	  
	  public String getDeDate()
	   {
	   	return de_date;
	   }

	
	  public void setDeDate(String de_date)
	   {
	   	this.de_date = de_date;
	   }
	   

}
