package com.banking.data.pojo;
// default package



/**
 * PeriodLimit entity. @author MyEclipse Persistence Tools
 */

public class PeriodLimit  implements java.io.Serializable {


    // Fields    

     private PeriodLimitId id;


    // Constructors

    /** default constructor */
    public PeriodLimit() {
    }

    
    /** full constructor */
    public PeriodLimit(PeriodLimitId id) {
        this.id = id;
    }

   
    // Property accessors

    public PeriodLimitId getId() {
        return this.id;
    }
    
    public void setId(PeriodLimitId id) {
        this.id = id;
    }
   








}