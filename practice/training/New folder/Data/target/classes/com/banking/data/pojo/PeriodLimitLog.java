package com.banking.data.pojo;
// default package



/**
 * PeriodLimitLog entity. @author MyEclipse Persistence Tools
 */

public class PeriodLimitLog  implements java.io.Serializable {


    // Fields    

     private PeriodLimitLogId id;


    // Constructors

    /** default constructor */
    public PeriodLimitLog() {
    }

    
    /** full constructor */
    public PeriodLimitLog(PeriodLimitLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public PeriodLimitLogId getId() {
        return this.id;
    }
    
    public void setId(PeriodLimitLogId id) {
        this.id = id;
    }
   








}