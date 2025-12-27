package com.banking.data.pojo;
// default package



/**
 * BorrowerMasterLog entity. @author MyEclipse Persistence Tools
 */

public class BorrowerMasterLog  implements java.io.Serializable {


    // Fields    

     private BorrowerMasterLogId id;


    // Constructors

    /** default constructor */
    public BorrowerMasterLog() {
    }

    
    /** full constructor */
    public BorrowerMasterLog(BorrowerMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public BorrowerMasterLogId getId() {
        return this.id;
    }
    
    public void setId(BorrowerMasterLogId id) {
        this.id = id;
    }
   








}