package com.banking.data.pojo;
// default package



/**
 * CustomerAddress entity. @author MyEclipse Persistence Tools
 */

public class CustomerAddress  implements java.io.Serializable {


    // Fields    

     private CustomerAddressId id;


    // Constructors

    /** default constructor */
    public CustomerAddress() {
    }

    
    /** full constructor */
    public CustomerAddress(CustomerAddressId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerAddressId getId() {
        return this.id;
    }
    
    public void setId(CustomerAddressId id) {
        this.id = id;
    }
   








}