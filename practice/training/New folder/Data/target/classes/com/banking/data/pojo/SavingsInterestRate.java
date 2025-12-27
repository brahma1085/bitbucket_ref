package com.banking.data.pojo;
// default package



/**
 * SavingsInterestRate entity. @author MyEclipse Persistence Tools
 */

public class SavingsInterestRate  implements java.io.Serializable {


    // Fields    

     private SavingsInterestRateId id;


    // Constructors

    /** default constructor */
    public SavingsInterestRate() {
    }

    
    /** full constructor */
    public SavingsInterestRate(SavingsInterestRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public SavingsInterestRateId getId() {
        return this.id;
    }
    
    public void setId(SavingsInterestRateId id) {
        this.id = id;
    }
   








}