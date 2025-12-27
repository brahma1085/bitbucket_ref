package com.banking.data.pojo;
// default package



/**
 * CashCode entity. @author MyEclipse Persistence Tools
 */

public class CashCode  implements java.io.Serializable {


    // Fields    

     private CashCodeId id;


    // Constructors

    /** default constructor */
    public CashCode() {
    }

    
    /** full constructor */
    public CashCode(CashCodeId id) {
        this.id = id;
    }

   
    // Property accessors

    public CashCodeId getId() {
        return this.id;
    }
    
    public void setId(CashCodeId id) {
        this.id = id;
    }
   








}