package com.banking.data.pojo;
// default package



/**
 * FreezedAccounts entity. @author MyEclipse Persistence Tools
 */

public class FreezedAccounts  implements java.io.Serializable {


    // Fields    

     private FreezedAccountsId id;


    // Constructors

    /** default constructor */
    public FreezedAccounts() {
    }

    
    /** full constructor */
    public FreezedAccounts(FreezedAccountsId id) {
        this.id = id;
    }

   
    // Property accessors

    public FreezedAccountsId getId() {
        return this.id;
    }
    
    public void setId(FreezedAccountsId id) {
        this.id = id;
    }
   








}