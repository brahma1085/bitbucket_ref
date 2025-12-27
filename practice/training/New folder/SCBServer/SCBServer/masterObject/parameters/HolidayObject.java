
package masterObject.parameters;

import java.io.*;

import masterObject.general.UserVerifier;


public class HolidayObject implements Serializable {
	
	
	private String txt_date,txt_day,txt_reason,de_tml,de_user,de_date;
	
	public String getDate()
	   {
	   	return txt_date;
	   }
	 
	 public void setDate(String txt_date)
	   {
	   	this.txt_date = txt_date;
	   }
	 
	 public String getReason()
	   {
	   	return txt_reason;
	   }

	
	  public void setReason(String txt_reason)
	   {
	   	this.txt_reason = txt_reason;
	   }
	 
	 
	 public String getDay()
	   {
	   	return txt_day;
	   }

	
	  public void setDay(String txt_day)
	   {
	   	this.txt_day = txt_day;
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
