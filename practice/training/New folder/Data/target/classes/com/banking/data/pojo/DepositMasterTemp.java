package com.banking.data.pojo;
// default package



/**
 * DepositMasterTemp entity. @author MyEclipse Persistence Tools
 */

public class DepositMasterTemp  implements java.io.Serializable {


    // Fields    

     private DepositMasterTempId id;


    // Constructors

    /** default constructor */
    public DepositMasterTemp() {
    }

    
    /** full constructor */
    public DepositMasterTemp(DepositMasterTempId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositMasterTempId getId() {
        return this.id;
    }
    
    public void setId(DepositMasterTempId id) {
        this.id = id;
    }
   








}