package com.banking.data.pojo;
// default package

import java.sql.Timestamp;


/**
 * BatchProcessDetailsId entity. @author MyEclipse Persistence Tools
 */

public class BatchProcessDetailsId  implements java.io.Serializable {


    // Fields    

     private String processId;
     private String processName;
     private String processStatus;
     private Timestamp processStartDate;
     private Timestamp processEndDate;
     private String userId;


    // Constructors

    /** default constructor */
    public BatchProcessDetailsId() {
    }

    
    /** full constructor */
    public BatchProcessDetailsId(String processId, String processName, String processStatus, Timestamp processStartDate, Timestamp processEndDate, String userId) {
        this.processId = processId;
        this.processName = processName;
        this.processStatus = processStatus;
        this.processStartDate = processStartDate;
        this.processEndDate = processEndDate;
        this.userId = userId;
    }

   
    // Property accessors

    public String getProcessId() {
        return this.processId;
    }
    
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return this.processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessStatus() {
        return this.processStatus;
    }
    
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Timestamp getProcessStartDate() {
        return this.processStartDate;
    }
    
    public void setProcessStartDate(Timestamp processStartDate) {
        this.processStartDate = processStartDate;
    }

    public Timestamp getProcessEndDate() {
        return this.processEndDate;
    }
    
    public void setProcessEndDate(Timestamp processEndDate) {
        this.processEndDate = processEndDate;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BatchProcessDetailsId) ) return false;
		 BatchProcessDetailsId castOther = ( BatchProcessDetailsId ) other; 
         
		 return ( (this.getProcessId()==castOther.getProcessId()) || ( this.getProcessId()!=null && castOther.getProcessId()!=null && this.getProcessId().equals(castOther.getProcessId()) ) )
 && ( (this.getProcessName()==castOther.getProcessName()) || ( this.getProcessName()!=null && castOther.getProcessName()!=null && this.getProcessName().equals(castOther.getProcessName()) ) )
 && ( (this.getProcessStatus()==castOther.getProcessStatus()) || ( this.getProcessStatus()!=null && castOther.getProcessStatus()!=null && this.getProcessStatus().equals(castOther.getProcessStatus()) ) )
 && ( (this.getProcessStartDate()==castOther.getProcessStartDate()) || ( this.getProcessStartDate()!=null && castOther.getProcessStartDate()!=null && this.getProcessStartDate().equals(castOther.getProcessStartDate()) ) )
 && ( (this.getProcessEndDate()==castOther.getProcessEndDate()) || ( this.getProcessEndDate()!=null && castOther.getProcessEndDate()!=null && this.getProcessEndDate().equals(castOther.getProcessEndDate()) ) )
 && ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProcessId() == null ? 0 : this.getProcessId().hashCode() );
         result = 37 * result + ( getProcessName() == null ? 0 : this.getProcessName().hashCode() );
         result = 37 * result + ( getProcessStatus() == null ? 0 : this.getProcessStatus().hashCode() );
         result = 37 * result + ( getProcessStartDate() == null ? 0 : this.getProcessStartDate().hashCode() );
         result = 37 * result + ( getProcessEndDate() == null ? 0 : this.getProcessEndDate().hashCode() );
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         return result;
   }   





}