package com.banking.data.pojo;
// default package



/**
 * ChequeLog entity. @author MyEclipse Persistence Tools
 */

public class ChequeLog  implements java.io.Serializable {


    // Fields    

     private ChequeLogId id;


    // Constructors

    /** default constructor */
    public ChequeLog() {
    }

    
    /** full constructor */
    public ChequeLog(ChequeLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeLogId getId() {
        return this.id;
    }
    
    public void setId(ChequeLogId id) {
        this.id = id;
    }
   








}