/*
 * Created on Jan 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.parameters;
import java.io.*;

import masterObject.general.UserVerifier;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AppriserObject implements Serializable{
	private int txt_code;
	private String txt_name;
	
	 public int getCode()
	   {
	   	return txt_code;
	   }
	 
	 
	   public void setCode(int txt_code)
	   {
	   	this.txt_code = txt_code;
	   }
	   
	 
	 public String getName()
	   {
	   	return txt_name;
	   }

	
	   public void setName(String txt_name)
	   {
	   	this.txt_name = txt_name;
	   }
	   
	 
}
