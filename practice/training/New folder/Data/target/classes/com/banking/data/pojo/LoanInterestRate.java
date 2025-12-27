package com.banking.data.pojo;
// default package



/**
 * LoanInterestRate entity. @author MyEclipse Persistence Tools
 */

public class LoanInterestRate  implements java.io.Serializable {


    // Fields    

     private LoanInterestRateId id;


    // Constructors

    /** default constructor */
    public LoanInterestRate() {
    }

    
    /** full constructor */
    public LoanInterestRate(LoanInterestRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanInterestRateId getId() {
        return this.id;
    }
    
    public void setId(LoanInterestRateId id) {
        this.id = id;
    }
   








}