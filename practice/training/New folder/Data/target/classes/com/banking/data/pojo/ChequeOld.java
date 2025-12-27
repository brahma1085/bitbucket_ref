package com.banking.data.pojo;
// default package



/**
 * ChequeOld entity. @author MyEclipse Persistence Tools
 */

public class ChequeOld  implements java.io.Serializable {


    // Fields    

     private ChequeOldId id;


    // Constructors

    /** default constructor */
    public ChequeOld() {
    }

    
    /** full constructor */
    public ChequeOld(ChequeOldId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeOldId getId() {
        return this.id;
    }
    
    public void setId(ChequeOldId id) {
        this.id = id;
    }
   








}