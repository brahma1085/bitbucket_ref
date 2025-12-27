package com.banking.data.pojo;
// default package



/**
 * DepositMasterLog entity. @author MyEclipse Persistence Tools
 */

public class DepositMasterLog  implements java.io.Serializable {


    // Fields    

     private DepositMasterLogId id;


    // Constructors

    /** default constructor */
    public DepositMasterLog() {
    }

    
    /** full constructor */
    public DepositMasterLog(DepositMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositMasterLogId getId() {
        return this.id;
    }
    
    public void setId(DepositMasterLogId id) {
        this.id = id;
    }
   








}