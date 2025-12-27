package com.banking.data.pojo;
// default package



/**
 * LoanPurposes entity. @author MyEclipse Persistence Tools
 */

public class LoanPurposes  implements java.io.Serializable {


    // Fields    

     private LoanPurposesId id;


    // Constructors

    /** default constructor */
    public LoanPurposes() {
    }

    
    /** full constructor */
    public LoanPurposes(LoanPurposesId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanPurposesId getId() {
        return this.id;
    }
    
    public void setId(LoanPurposesId id) {
        this.id = id;
    }
   








}