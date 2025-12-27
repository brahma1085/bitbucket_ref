package com.banking.data.pojo;
// default package



/**
 * ReasonMaster entity. @author MyEclipse Persistence Tools
 */

public class ReasonMaster  implements java.io.Serializable {


    // Fields    

     private ReasonMasterId id;


    // Constructors

    /** default constructor */
    public ReasonMaster() {
    }

    
    /** full constructor */
    public ReasonMaster(ReasonMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public ReasonMasterId getId() {
        return this.id;
    }
    
    public void setId(ReasonMasterId id) {
        this.id = id;
    }
   








}