
package masterObject.parameters;

import java.io.*;

import masterObject.general.UserVerifier;

public class QtrDefinitionObject implements Serializable{

	private int month;
	private String de_tml,de_user,de_date;
	private String qtr_defn,hyr_defn,yr_defn;
	
	 public int getMonth()
	   {
	   	return month;
	   }
	 
	 public void setMonth(int month)
	   {
	   	this.month = month;
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
	  
	  
	  public String getQtrDefn()
	   {
	   	return qtr_defn;
	   }

	
	  public void setQtrDefn(String qtr_defn)
	   {
	   	this.qtr_defn = qtr_defn;
	   }
	  
	  public String getHalfDefn()
	   {
	   	return hyr_defn;
	   }

	
	  public void setHalfDefn(String hyr_defn)
	   {
	   	this.hyr_defn = hyr_defn;
	   }
	  
	  public String getYearDefn()
	   {
	   	return hyr_defn;
	   }

	
	  public void setYearDefn(String yr_defn)
	   {
	   	this.yr_defn = yr_defn;
	   }

	
	   

}
