package com.banking.data.pojo;
// default package



/**
 * RBIForm1Transaction entity. @author MyEclipse Persistence Tools
 */

public class RBIForm1Transaction  implements java.io.Serializable {


    // Fields    

     private RBIForm1TransactionId id;


    // Constructors

    /** default constructor */
    public RBIForm1Transaction() {
    }

    
    /** full constructor */
    public RBIForm1Transaction(RBIForm1TransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public RBIForm1TransactionId getId() {
        return this.id;
    }
    
    public void setId(RBIForm1TransactionId id) {
        this.id = id;
    }
   








}