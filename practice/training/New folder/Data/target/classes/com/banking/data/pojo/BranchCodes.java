package com.banking.data.pojo;
// default package



/**
 * BranchCodes entity. @author MyEclipse Persistence Tools
 */

public class BranchCodes  implements java.io.Serializable {


    // Fields    

     private BranchCodesId id;


    // Constructors

    /** default constructor */
    public BranchCodes() {
    }

    
    /** full constructor */
    public BranchCodes(BranchCodesId id) {
        this.id = id;
    }

   
    // Property accessors

    public BranchCodesId getId() {
        return this.id;
    }
    
    public void setId(BranchCodesId id) {
        this.id = id;
    }
   








}