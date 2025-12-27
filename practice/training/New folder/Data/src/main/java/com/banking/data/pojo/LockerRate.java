package com.banking.data.pojo;
// default package



/**
 * LockerRate entity. @author MyEclipse Persistence Tools
 */

public class LockerRate  implements java.io.Serializable {


    // Fields    

     private LockerRateId id;


    // Constructors

    /** default constructor */
    public LockerRate() {
    }

    
    /** full constructor */
    public LockerRate(LockerRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public LockerRateId getId() {
        return this.id;
    }
    
    public void setId(LockerRateId id) {
        this.id = id;
    }
   








}