package com.banking.data.pojo;
// default package



/**
 * BorrowerMaster entity. @author MyEclipse Persistence Tools
 */

public class BorrowerMaster  implements java.io.Serializable {


    // Fields    

     private BorrowerMasterId id;


    // Constructors

    /** default constructor */
    public BorrowerMaster() {
    }

    
    /** full constructor */
    public BorrowerMaster(BorrowerMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public BorrowerMasterId getId() {
        return this.id;
    }
    
    public void setId(BorrowerMasterId id) {
        this.id = id;
    }
   








}