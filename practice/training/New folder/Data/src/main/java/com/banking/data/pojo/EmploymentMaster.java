package com.banking.data.pojo;
// default package



/**
 * EmploymentMaster entity. @author MyEclipse Persistence Tools
 */

public class EmploymentMaster  implements java.io.Serializable {


    // Fields    

     private EmploymentMasterId id;


    // Constructors

    /** default constructor */
    public EmploymentMaster() {
    }

    
    /** full constructor */
    public EmploymentMaster(EmploymentMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public EmploymentMasterId getId() {
        return this.id;
    }
    
    public void setId(EmploymentMasterId id) {
        this.id = id;
    }
   








}