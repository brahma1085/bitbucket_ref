package com.banking.data.pojo;
// default package



/**
 * CustomerParameters entity. @author MyEclipse Persistence Tools
 */

public class CustomerParameters  implements java.io.Serializable {


    // Fields    

     private CustomerParametersId id;


    // Constructors

    /** default constructor */
    public CustomerParameters() {
    }

    
    /** full constructor */
    public CustomerParameters(CustomerParametersId id) {
        this.id = id;
    }

   
    // Property accessors

    public CustomerParametersId getId() {
        return this.id;
    }
    
    public void setId(CustomerParametersId id) {
        this.id = id;
    }
   








}