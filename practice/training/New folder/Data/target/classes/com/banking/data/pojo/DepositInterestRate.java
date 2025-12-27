package com.banking.data.pojo;
// default package



/**
 * DepositInterestRate entity. @author MyEclipse Persistence Tools
 */

public class DepositInterestRate  implements java.io.Serializable {


    // Fields    

     private DepositInterestRateId id;


    // Constructors

    /** default constructor */
    public DepositInterestRate() {
    }

    
    /** full constructor */
    public DepositInterestRate(DepositInterestRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositInterestRateId getId() {
        return this.id;
    }
    
    public void setId(DepositInterestRateId id) {
        this.id = id;
    }
   








}