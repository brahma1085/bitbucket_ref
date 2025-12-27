
package masterObject.parameters;

import java.io.*;

import masterObject.general.UserVerifier;


public class CustomerObject implements Serializable{

	
	private String tablename,colname;
	private int sl_no;
	
	public String getTableName()
	   {
	   	return tablename;
	   }

	
	  public void setTableName(String tablename)
	   {
	   	this.tablename = tablename;
	   }
	  
	  public String getColName()
	   {
	   	return colname;
	   }

	
	  public void setColName(String colname)
	   {
	   	this.colname = colname;
	   }
	  
	  public int getSlNo()
	   {
	   	return sl_no;
	   }

	
	  public void setSlNo(int sl_no)
	   {
	   	this.sl_no = sl_no;
	   }
	  

}
