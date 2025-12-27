package com.banking.data.pojo;
// default package



/**
 * LoanHistory entity. @author MyEclipse Persistence Tools
 */

public class LoanHistory  implements java.io.Serializable {


    // Fields    

     private LoanHistoryId id;


    // Constructors

    /** default constructor */
    public LoanHistory() {
    }

    
    /** full constructor */
    public LoanHistory(LoanHistoryId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanHistoryId getId() {
        return this.id;
    }
    
    public void setId(LoanHistoryId id) {
        this.id = id;
    }
   








}