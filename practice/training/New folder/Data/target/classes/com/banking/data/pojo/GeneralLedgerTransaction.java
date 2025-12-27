package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerTransaction entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerTransaction  implements java.io.Serializable {


    // Fields    

     private GeneralLedgerTransactionId id;


    // Constructors

    /** default constructor */
    public GeneralLedgerTransaction() {
    }

    
    /** full constructor */
    public GeneralLedgerTransaction(GeneralLedgerTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public GeneralLedgerTransactionId getId() {
        return this.id;
    }
    
    public void setId(GeneralLedgerTransactionId id) {
        this.id = id;
    }
   








}