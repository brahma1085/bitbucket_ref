package com.banking.data.pojo;
// default package



/**
 * Reason entity. @author MyEclipse Persistence Tools
 */

public class Reason  implements java.io.Serializable {


    // Fields    

     private ReasonId id;


    // Constructors

    /** default constructor */
    public Reason() {
    }

    
    /** full constructor */
    public Reason(ReasonId id) {
        this.id = id;
    }

   
    // Property accessors

    public ReasonId getId() {
        return this.id;
    }
    
    public void setId(ReasonId id) {
        this.id = id;
    }
   








}