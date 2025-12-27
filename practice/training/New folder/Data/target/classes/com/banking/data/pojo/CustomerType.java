package com.banking.data.pojo;
// default package



/**
 * CustomerType entity. @author MyEclipse Persistence Tools
 */

public class CustomerType  implements java.io.Serializable {


    // Fields    

     private CustomerTypeId id;


    // Constructors

    /** default constructor */
    public CustomerType() {
    }

    
    /** full constructor */
    public CustomerType(CustomerTypeId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerTypeId getId() {
        return this.id;
    }
    
    public void setId(CustomerTypeId id) {
        this.id = id;
    }
   








}