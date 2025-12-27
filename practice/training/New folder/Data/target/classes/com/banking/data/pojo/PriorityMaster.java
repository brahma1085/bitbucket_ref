package com.banking.data.pojo;
// default package



/**
 * PriorityMaster entity. @author MyEclipse Persistence Tools
 */

public class PriorityMaster  implements java.io.Serializable {


    // Fields    

     private PriorityMasterId id;


    // Constructors

    /** default constructor */
    public PriorityMaster() {
    }

    
    /** full constructor */
    public PriorityMaster(PriorityMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public PriorityMasterId getId() {
        return this.id;
    }
    
    public void setId(PriorityMasterId id) {
        this.id = id;
    }
   








}