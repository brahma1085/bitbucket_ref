package com.banking.data.pojo;
// default package



/**
 * AccountTransaction entity. @author MyEclipse Persistence Tools
 */

public class AccountTransaction  implements java.io.Serializable {


    // Fields    

     private AccountTransactionId id;


    // Constructors

    /** default constructor */
    public AccountTransaction() {
    }

    
    /** full constructor */
    public AccountTransaction(AccountTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public AccountTransactionId getId() {
        return this.id;
    }
    
    public void setId(AccountTransactionId id) {
        this.id = id;
    }
   








}