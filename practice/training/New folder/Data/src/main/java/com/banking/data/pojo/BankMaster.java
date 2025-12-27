package com.banking.data.pojo;
// default package



/**
 * BankMaster entity. @author MyEclipse Persistence Tools
 */

public class BankMaster  implements java.io.Serializable {


    // Fields    

     private BankMasterId id;


    // Constructors

    /** default constructor */
    public BankMaster() {
    }

    
    /** full constructor */
    public BankMaster(BankMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public BankMasterId getId() {
        return this.id;
    }
    
    public void setId(BankMasterId id) {
        this.id = id;
    }
   








}