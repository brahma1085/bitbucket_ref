package com.banking.data.pojo;
// default package



/**
 * BounceFine entity. @author MyEclipse Persistence Tools
 */

public class BounceFine  implements java.io.Serializable {


    // Fields    

     private BounceFineId id;


    // Constructors

    /** default constructor */
    public BounceFine() {
    }

    
    /** full constructor */
    public BounceFine(BounceFineId id) {
        this.id = id;
    }

   
    // Property accessors

    public BounceFineId getId() {
        return this.id;
    }
    
    public void setId(BounceFineId id) {
        this.id = id;
    }
   








}