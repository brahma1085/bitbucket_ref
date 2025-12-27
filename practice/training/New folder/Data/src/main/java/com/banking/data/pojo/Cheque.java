package com.banking.data.pojo;
// default package



/**
 * Cheque entity. @author MyEclipse Persistence Tools
 */

public class Cheque  implements java.io.Serializable {


    // Fields    

     private ChequeId id;


    // Constructors

    /** default constructor */
    public Cheque() {
    }

    
    /** full constructor */
    public Cheque(ChequeId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeId getId() {
        return this.id;
    }
    
    public void setId(ChequeId id) {
        this.id = id;
    }
   








}