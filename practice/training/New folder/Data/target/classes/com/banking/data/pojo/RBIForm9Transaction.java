package com.banking.data.pojo;
// default package



/**
 * RBIForm9Transaction entity. @author MyEclipse Persistence Tools
 */

public class RBIForm9Transaction  implements java.io.Serializable {


    // Fields    

     private RBIForm9TransactionId id;


    // Constructors

    /** default constructor */
    public RBIForm9Transaction() {
    }

    
    /** full constructor */
    public RBIForm9Transaction(RBIForm9TransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public RBIForm9TransactionId getId() {
        return this.id;
    }
    
    public void setId(RBIForm9TransactionId id) {
        this.id = id;
    }
   








}