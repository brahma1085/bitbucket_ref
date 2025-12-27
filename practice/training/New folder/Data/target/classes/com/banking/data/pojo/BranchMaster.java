package com.banking.data.pojo;
// default package



/**
 * BranchMaster entity. @author MyEclipse Persistence Tools
 */

public class BranchMaster  implements java.io.Serializable {


    // Fields    

     private BranchMasterId id;


    // Constructors

    /** default constructor */
    public BranchMaster() {
    }

    
    /** full constructor */
    public BranchMaster(BranchMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public BranchMasterId getId() {
        return this.id;
    }
    
    public void setId(BranchMasterId id) {
        this.id = id;
    }
   








}