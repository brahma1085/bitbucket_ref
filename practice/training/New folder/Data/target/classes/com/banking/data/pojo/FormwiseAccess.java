package com.banking.data.pojo;
// default package



/**
 * FormwiseAccess entity. @author MyEclipse Persistence Tools
 */

public class FormwiseAccess  implements java.io.Serializable {


    // Fields    

     private FormwiseAccessId id;


    // Constructors

    /** default constructor */
    public FormwiseAccess() {
    }

    
    /** full constructor */
    public FormwiseAccess(FormwiseAccessId id) {
        this.id = id;
    }

   
    // Property accessors

    public FormwiseAccessId getId() {
        return this.id;
    }
    
    public void setId(FormwiseAccessId id) {
        this.id = id;
    }
   








}