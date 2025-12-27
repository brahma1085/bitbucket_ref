package com.banking.data.pojo;
// default package



/**
 * CustomerId entity. @author MyEclipse Persistence Tools
 */

public class CustomerId  implements java.io.Serializable {


    // Fields    

     private CustomerIdId id;


    // Constructors

    /** default constructor */
    public CustomerId() {
    }

    
    /** full constructor */
    public CustomerId(CustomerIdId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerIdId getId() {
        return this.id;
    }
    
    public void setId(CustomerIdId id) {
        this.id = id;
    }
   








}