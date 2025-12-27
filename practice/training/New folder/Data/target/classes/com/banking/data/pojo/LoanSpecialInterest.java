package com.banking.data.pojo;
// default package



/**
 * LoanSpecialInterest entity. @author MyEclipse Persistence Tools
 */

public class LoanSpecialInterest  implements java.io.Serializable {


    // Fields    

     private LoanSpecialInterestId id;


    // Constructors

    /** default constructor */
    public LoanSpecialInterest() {
    }

    
    /** full constructor */
    public LoanSpecialInterest(LoanSpecialInterestId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanSpecialInterestId getId() {
        return this.id;
    }
    
    public void setId(LoanSpecialInterestId id) {
        this.id = id;
    }
   








}