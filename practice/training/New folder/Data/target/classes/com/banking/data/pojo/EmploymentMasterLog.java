package com.banking.data.pojo;
// default package



/**
 * EmploymentMasterLog entity. @author MyEclipse Persistence Tools
 */

public class EmploymentMasterLog  implements java.io.Serializable {


    // Fields    

     private EmploymentMasterLogId id;


    // Constructors

    /** default constructor */
    public EmploymentMasterLog() {
    }

    
    /** full constructor */
    public EmploymentMasterLog(EmploymentMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public EmploymentMasterLogId getId() {
        return this.id;
    }
    
    public void setId(EmploymentMasterLogId id) {
        this.id = id;
    }
   








}