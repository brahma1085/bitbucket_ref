package com.banking.data.pojo;
// default package



/**
 * ODCCTransaction entity. @author MyEclipse Persistence Tools
 */

public class ODCCTransaction  implements java.io.Serializable {


    // Fields    

     private ODCCTransactionId id;


    // Constructors

    /** default constructor */
    public ODCCTransaction() {
    }

    
    /** full constructor */
    public ODCCTransaction(ODCCTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public ODCCTransactionId getId() {
        return this.id;
    }
    
    public void setId(ODCCTransactionId id) {
        this.id = id;
    }
   








}