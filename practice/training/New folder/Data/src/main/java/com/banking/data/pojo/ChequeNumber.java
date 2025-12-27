package com.banking.data.pojo;
// default package



/**
 * ChequeNumber entity. @author MyEclipse Persistence Tools
 */

public class ChequeNumber  implements java.io.Serializable {


    // Fields    

     private ChequeNumberId id;


    // Constructors

    /** default constructor */
    public ChequeNumber() {
    }

    
    /** full constructor */
    public ChequeNumber(ChequeNumberId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeNumberId getId() {
        return this.id;
    }
    
    public void setId(ChequeNumberId id) {
        this.id = id;
    }
   








}