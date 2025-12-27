package com.banking.data.pojo;
// default package



/**
 * PSACInst entity. @author MyEclipse Persistence Tools
 */

public class PSACInst  implements java.io.Serializable {


    // Fields    

     private PSACInstId id;


    // Constructors

    /** default constructor */
    public PSACInst() {
    }

    
    /** full constructor */
    public PSACInst(PSACInstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSACInstId getId() {
        return this.id;
    }
    
    public void setId(PSACInstId id) {
        this.id = id;
    }
   








}