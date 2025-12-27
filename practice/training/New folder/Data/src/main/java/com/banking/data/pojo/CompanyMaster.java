package com.banking.data.pojo;
// default package



/**
 * CompanyMaster entity. @author MyEclipse Persistence Tools
 */

public class CompanyMaster  implements java.io.Serializable {


    // Fields    

     private CompanyMasterId id;


    // Constructors

    /** default constructor */
    public CompanyMaster() {
    }

    
    /** full constructor */
    public CompanyMaster(CompanyMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public CompanyMasterId getId() {
        return this.id;
    }
    
    public void setId(CompanyMasterId id) {
        this.id = id;
    }
   








}