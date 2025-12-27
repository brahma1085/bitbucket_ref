package com.banking.data.pojo;
// default package



/**
 * LockerTransaction entity. @author MyEclipse Persistence Tools
 */

public class LockerTransaction  implements java.io.Serializable {


    // Fields    

     private LockerTransactionId id;


    // Constructors

    /** default constructor */
    public LockerTransaction() {
    }

    
    /** full constructor */
    public LockerTransaction(LockerTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public LockerTransactionId getId() {
        return this.id;
    }
    
    public void setId(LockerTransactionId id) {
        this.id = id;
    }
   








}