package com.banking.data.pojo;
// default package



/**
 * AppriserMaster entity. @author MyEclipse Persistence Tools
 */

public class AppriserMaster  implements java.io.Serializable {


    // Fields    

     private AppriserMasterId id;


    // Constructors

    /** default constructor */
    public AppriserMaster() {
    }

    
    /** full constructor */
    public AppriserMaster(AppriserMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public AppriserMasterId getId() {
        return this.id;
    }
    
    public void setId(AppriserMasterId id) {
        this.id = id;
    }
   








}