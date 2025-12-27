package com.banking.data.pojo;
// default package



/**
 * LockerCategoryRate entity. @author MyEclipse Persistence Tools
 */

public class LockerCategoryRate  implements java.io.Serializable {


    // Fields    

     private LockerCategoryRateId id;


    // Constructors

    /** default constructor */
    public LockerCategoryRate() {
    }

    
    /** full constructor */
    public LockerCategoryRate(LockerCategoryRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public LockerCategoryRateId getId() {
        return this.id;
    }
    
    public void setId(LockerCategoryRateId id) {
        this.id = id;
    }
   








}