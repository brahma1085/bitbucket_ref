package com.banking.data.pojo;
// default package



/**
 * CustomerMasterLog entity. @author MyEclipse Persistence Tools
 */

public class CustomerMasterLog  implements java.io.Serializable {


    // Fields    

     private CustomerMasterLogId id;


    // Constructors

    /** default constructor */
    public CustomerMasterLog() {
    }

    
    /** full constructor */
    public CustomerMasterLog(CustomerMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerMasterLogId getId() {
        return this.id;
    }
    
    public void setId(CustomerMasterLogId id) {
        this.id = id;
    }
   








}