package com.banking.data.pojo;
// default package



/**
 * PygmyRate entity. @author MyEclipse Persistence Tools
 */

public class PygmyRate  implements java.io.Serializable {


    // Fields    

     private PygmyRateId id;


    // Constructors

    /** default constructor */
    public PygmyRate() {
    }

    
    /** full constructor */
    public PygmyRate(PygmyRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public PygmyRateId getId() {
        return this.id;
    }
    
    public void setId(PygmyRateId id) {
        this.id = id;
    }
   








}