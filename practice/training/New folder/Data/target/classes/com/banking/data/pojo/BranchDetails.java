package com.banking.data.pojo;
// default package



/**
 * BranchDetails entity. @author MyEclipse Persistence Tools
 */

public class BranchDetails  implements java.io.Serializable {


    // Fields    

     private BranchDetailsId id;


    // Constructors

    /** default constructor */
    public BranchDetails() {
    }

    
    /** full constructor */
    public BranchDetails(BranchDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public BranchDetailsId getId() {
        return this.id;
    }
    
    public void setId(BranchDetailsId id) {
        this.id = id;
    }
   








}