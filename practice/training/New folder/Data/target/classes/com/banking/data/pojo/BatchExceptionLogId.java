package com.banking.data.pojo;
// default package

import java.sql.Timestamp;


/**
 * BatchExceptionLogId entity. @author MyEclipse Persistence Tools
 */

public class BatchExceptionLogId  implements java.io.Serializable {


    // Fields    

     private String processId;
     private Timestamp dateTime;
     private String logMsg;


    // Constructors

    /** default constructor */
    public BatchExceptionLogId() {
    }

    
    /** full constructor */
    public BatchExceptionLogId(String processId, Timestamp dateTime, String logMsg) {
        this.processId = processId;
        this.dateTime = dateTime;
        this.logMsg = logMsg;
    }

   
    // Property accessors

    public String getProcessId() {
        return this.processId;
    }
    
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Timestamp getDateTime() {
        return this.dateTime;
    }
    
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getLogMsg() {
        return this.logMsg;
    }
    
    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BatchExceptionLogId) ) return false;
		 BatchExceptionLogId castOther = ( BatchExceptionLogId ) other; 
         
		 return ( (this.getProcessId()==castOther.getProcessId()) || ( this.getProcessId()!=null && castOther.getProcessId()!=null && this.getProcessId().equals(castOther.getProcessId()) ) )
 && ( (this.getDateTime()==castOther.getDateTime()) || ( this.getDateTime()!=null && castOther.getDateTime()!=null && this.getDateTime().equals(castOther.getDateTime()) ) )
 && ( (this.getLogMsg()==castOther.getLogMsg()) || ( this.getLogMsg()!=null && castOther.getLogMsg()!=null && this.getLogMsg().equals(castOther.getLogMsg()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProcessId() == null ? 0 : this.getProcessId().hashCode() );
         result = 37 * result + ( getDateTime() == null ? 0 : this.getDateTime().hashCode() );
         result = 37 * result + ( getLogMsg() == null ? 0 : this.getLogMsg().hashCode() );
         return result;
   }   





}