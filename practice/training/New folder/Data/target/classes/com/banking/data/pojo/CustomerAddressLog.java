package com.banking.data.pojo;
// default package



/**
 * CustomerAddressLog entity. @author MyEclipse Persistence Tools
 */

public class CustomerAddressLog  implements java.io.Serializable {


    // Fields    

     private CustomerAddressLogId id;


    // Constructors

    /** default constructor */
    public CustomerAddressLog() {
    }

    
    /** full constructor */
    public CustomerAddressLog(CustomerAddressLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerAddressLogId getId() {
        return this.id;
    }
    
    public void setId(CustomerAddressLogId id) {
        this.id = id;
    }
   








}