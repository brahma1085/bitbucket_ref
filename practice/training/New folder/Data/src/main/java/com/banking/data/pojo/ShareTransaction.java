package com.banking.data.pojo;
// default package



/**
 * ShareTransaction entity. @author MyEclipse Persistence Tools
 */

public class ShareTransaction  implements java.io.Serializable {


    // Fields    

     private ShareTransactionId id;


    // Constructors

    /** default constructor */
    public ShareTransaction() {
    }

    
    /** full constructor */
    public ShareTransaction(ShareTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareTransactionId getId() {
        return this.id;
    }
    
    public void setId(ShareTransactionId id) {
        this.id = id;
    }
   








}