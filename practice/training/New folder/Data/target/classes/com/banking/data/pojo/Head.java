package com.banking.data.pojo;
// default package



/**
 * Head entity. @author MyEclipse Persistence Tools
 */

public class Head  implements java.io.Serializable {


    // Fields    

     private HeadId id;


    // Constructors

    /** default constructor */
    public Head() {
    }

    
    /** full constructor */
    public Head(HeadId id) {
        this.id = id;
    }

   
    // Property accessors

    public HeadId getId() {
        return this.id;
    }
    
    public void setId(HeadId id) {
        this.id = id;
    }
   








}