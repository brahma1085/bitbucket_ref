package com.banking.data.pojo;
// default package



/**
 * PropertyMasterLog entity. @author MyEclipse Persistence Tools
 */

public class PropertyMasterLog  implements java.io.Serializable {


    // Fields    

     private PropertyMasterLogId id;


    // Constructors

    /** default constructor */
    public PropertyMasterLog() {
    }

    
    /** full constructor */
    public PropertyMasterLog(PropertyMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public PropertyMasterLogId getId() {
        return this.id;
    }
    
    public void setId(PropertyMasterLogId id) {
        this.id = id;
    }
   








}