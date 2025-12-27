package com.banking.data.pojo;
// default package



/**
 * LoanPeriodRate entity. @author MyEclipse Persistence Tools
 */

public class LoanPeriodRate  implements java.io.Serializable {


    // Fields    

     private LoanPeriodRateId id;


    // Constructors

    /** default constructor */
    public LoanPeriodRate() {
    }

    
    /** full constructor */
    public LoanPeriodRate(LoanPeriodRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanPeriodRateId getId() {
        return this.id;
    }
    
    public void setId(LoanPeriodRateId id) {
        this.id = id;
    }
   








}