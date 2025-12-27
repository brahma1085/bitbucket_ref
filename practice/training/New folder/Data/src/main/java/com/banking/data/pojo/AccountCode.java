package com.banking.data.pojo;
// default package



/**
 * AccountCode entity. @author MyEclipse Persistence Tools
 */

public class AccountCode  implements java.io.Serializable {


    // Fields    

     private AccountCodeId id;


    // Constructors

    /** default constructor */
    public AccountCode() {
    }

    
    /** full constructor */
    public AccountCode(AccountCodeId id) {
        this.id = id;
    }

   
    // Property accessors

    public AccountCodeId getId() {
        return this.id;
    }
    
    public void setId(AccountCodeId id) {
        this.id = id;
    }
   








}