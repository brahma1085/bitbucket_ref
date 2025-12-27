package com.banking.data.pojo;
// default package



/**
 * AccountMasterLog entity. @author MyEclipse Persistence Tools
 */

public class AccountMasterLog  implements java.io.Serializable {


    // Fields    

     private AccountMasterLogId id;


    // Constructors

    /** default constructor */
    public AccountMasterLog() {
    }

    
    /** full constructor */
    public AccountMasterLog(AccountMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public AccountMasterLogId getId() {
        return this.id;
    }
    
    public void setId(AccountMasterLogId id) {
        this.id = id;
    }
   








}