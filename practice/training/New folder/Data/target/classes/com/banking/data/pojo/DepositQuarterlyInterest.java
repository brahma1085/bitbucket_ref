package com.banking.data.pojo;
// default package



/**
 * DepositQuarterlyInterest entity. @author MyEclipse Persistence Tools
 */

public class DepositQuarterlyInterest  implements java.io.Serializable {


    // Fields    

     private DepositQuarterlyInterestId id;


    // Constructors

    /** default constructor */
    public DepositQuarterlyInterest() {
    }

    
    /** full constructor */
    public DepositQuarterlyInterest(DepositQuarterlyInterestId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositQuarterlyInterestId getId() {
        return this.id;
    }
    
    public void setId(DepositQuarterlyInterestId id) {
        this.id = id;
    }
   








}