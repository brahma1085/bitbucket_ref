package com.banking.data.pojo;
// default package



/**
 * LoanRecoveryDetail entity. @author MyEclipse Persistence Tools
 */

public class LoanRecoveryDetail  implements java.io.Serializable {


    // Fields    

     private LoanRecoveryDetailId id;


    // Constructors

    /** default constructor */
    public LoanRecoveryDetail() {
    }

    
    /** full constructor */
    public LoanRecoveryDetail(LoanRecoveryDetailId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanRecoveryDetailId getId() {
        return this.id;
    }
    
    public void setId(LoanRecoveryDetailId id) {
        this.id = id;
    }
   








}