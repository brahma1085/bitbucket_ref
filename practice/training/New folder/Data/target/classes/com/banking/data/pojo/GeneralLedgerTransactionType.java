package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerTransactionType entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerTransactionType  implements java.io.Serializable {


    // Fields    

     private GeneralLedgerTransactionTypeId id;


    // Constructors

    /** default constructor */
    public GeneralLedgerTransactionType() {
    }

    
    /** full constructor */
    public GeneralLedgerTransactionType(GeneralLedgerTransactionTypeId id) {
        this.id = id;
    }

   
    // Property accessors

    public GeneralLedgerTransactionTypeId getId() {
        return this.id;
    }
    
    public void setId(GeneralLedgerTransactionTypeId id) {
        this.id = id;
    }
   








}