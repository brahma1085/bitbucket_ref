package com.banking.data.pojo;
// default package



/**
 * DepositTransaction entity. @author MyEclipse Persistence Tools
 */

public class DepositTransaction  implements java.io.Serializable {


    // Fields    

     private DepositTransactionId id;


    // Constructors

    /** default constructor */
    public DepositTransaction() {
    }

    
    /** full constructor */
    public DepositTransaction(DepositTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositTransactionId getId() {
        return this.id;
    }
    
    public void setId(DepositTransactionId id) {
        this.id = id;
    }
   








}