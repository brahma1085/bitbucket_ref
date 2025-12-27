package com.banking.data.pojo;
// default package



/**
 * PropertyMaster entity. @author MyEclipse Persistence Tools
 */

public class PropertyMaster  implements java.io.Serializable {


    // Fields    

     private PropertyMasterId id;


    // Constructors

    /** default constructor */
    public PropertyMaster() {
    }

    
    /** full constructor */
    public PropertyMaster(PropertyMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public PropertyMasterId getId() {
        return this.id;
    }
    
    public void setId(PropertyMasterId id) {
        this.id = id;
    }
   








}