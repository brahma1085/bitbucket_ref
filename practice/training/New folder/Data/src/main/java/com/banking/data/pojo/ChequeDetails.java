package com.banking.data.pojo;
// default package



/**
 * ChequeDetails entity. @author MyEclipse Persistence Tools
 */

public class ChequeDetails  implements java.io.Serializable {


    // Fields    

     private ChequeDetailsId id;


    // Constructors

    /** default constructor */
    public ChequeDetails() {
    }

    
    /** full constructor */
    public ChequeDetails(ChequeDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeDetailsId getId() {
        return this.id;
    }
    
    public void setId(ChequeDetailsId id) {
        this.id = id;
    }
   








}