package com.banking.data.pojo;
// default package



/**
 * PenalInterestRate entity. @author MyEclipse Persistence Tools
 */

public class PenalInterestRate  implements java.io.Serializable {


    // Fields    

     private PenalInterestRateId id;


    // Constructors

    /** default constructor */
    public PenalInterestRate() {
    }

    
    /** full constructor */
    public PenalInterestRate(PenalInterestRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public PenalInterestRateId getId() {
        return this.id;
    }
    
    public void setId(PenalInterestRateId id) {
        this.id = id;
    }
   








}