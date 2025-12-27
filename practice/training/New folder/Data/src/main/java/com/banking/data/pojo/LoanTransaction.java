package com.banking.data.pojo;
// default package



/**
 * LoanTransaction entity. @author MyEclipse Persistence Tools
 */

public class LoanTransaction  implements java.io.Serializable {


    // Fields    

     private LoanTransactionId id;


    // Constructors

    /** default constructor */
    public LoanTransaction() {
    }

    
    /** full constructor */
    public LoanTransaction(LoanTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanTransactionId getId() {
        return this.id;
    }
    
    public void setId(LoanTransactionId id) {
        this.id = id;
    }
   








}