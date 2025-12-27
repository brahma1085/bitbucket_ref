package masterObject.backOffice;
import java.io.*;
import java.util.Map;

import masterObject.general.UserVerifier;

 public class AdminObject implements Serializable
 {
   private int priorityNo;
   private String fromType,toType,frTypeDesc,toTypeDesc,string_date;
   public UserVerifier obj_userverifier=new UserVerifier();
   private Map insertval;
   
   public int getPriorityNo()
   {
   	return priorityNo;
   }
   public void setPriorityNo(int int_priority_no)
   {
   	this.priorityNo=int_priority_no;
   }
   
  
   public String getFromType()
   {
   	return fromType;
   }
   public void setFromType(String string_from_acc_type)
   {
   	this.fromType=string_from_acc_type;
   }
   
   
   public String getFrTypeDesc()
   {
   	return frTypeDesc;
   }
   public void setFrTypeDesc(String string_from_mod_desc)
   {
   	this.frTypeDesc=string_from_mod_desc;
   }
   
   	
      
   public String getToType()
   {
   	return toType;
   }
   
   public void setToType(String string_to_acc_type)
   {
   	this.toType=string_to_acc_type;
   }
   
   
    public String getToTypeDesc()
   {
   	return toTypeDesc;
   }
   public void setToTypeDesc(String string_to_mod_desc)
   {
   	this.toTypeDesc=string_to_mod_desc;
   }
   
   public String getDate()
   {
   	return string_date;
   }
   public void setDate(String string_date)
   {
   	this.string_date=string_date;
   }

public Map getInsertval() {
	return insertval;
}
public void setInsertval(Map insertval) {
	this.insertval = insertval;
}
   
   
       
  }