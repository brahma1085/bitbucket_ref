package com.banking.data.pojo;
// default package



/**
 * BatchExceptionLog entity. @author MyEclipse Persistence Tools
 */

public class BatchExceptionLog  implements java.io.Serializable {


    // Fields    

     private BatchExceptionLogId id;


    // Constructors

    /** default constructor */
    public BatchExceptionLog() {
    }

    
    /** full constructor */
    public BatchExceptionLog(BatchExceptionLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public BatchExceptionLogId getId() {
        return this.id;
    }
    
    public void setId(BatchExceptionLogId id) {
        this.id = id;
    }
   








}