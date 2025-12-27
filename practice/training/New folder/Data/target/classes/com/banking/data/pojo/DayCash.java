package com.banking.data.pojo;
// default package



/**
 * DayCash entity. @author MyEclipse Persistence Tools
 */

public class DayCash  implements java.io.Serializable {


    // Fields    

     private DayCashId id;


    // Constructors

    /** default constructor */
    public DayCash() {
    }

    
    /** full constructor */
    public DayCash(DayCashId id) {
        this.id = id;
    }

   
    // Property accessors

    public DayCashId getId() {
        return this.id;
    }
    
    public void setId(DayCashId id) {
        this.id = id;
    }
   








}