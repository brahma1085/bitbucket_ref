package com.banking.data.pojo;
// default package



/**
 * ChequeNumberLog entity. @author MyEclipse Persistence Tools
 */

public class ChequeNumberLog  implements java.io.Serializable {


    // Fields    

     private ChequeNumberLogId id;


    // Constructors

    /** default constructor */
    public ChequeNumberLog() {
    }

    
    /** full constructor */
    public ChequeNumberLog(ChequeNumberLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeNumberLogId getId() {
        return this.id;
    }
    
    public void setId(ChequeNumberLogId id) {
        this.id = id;
    }
   








}