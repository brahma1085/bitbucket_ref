package com.banking.data.pojo;
// default package



/**
 * SavingsCategoryRate entity. @author MyEclipse Persistence Tools
 */

public class SavingsCategoryRate  implements java.io.Serializable {


    // Fields    

     private SavingsCategoryRateId id;


    // Constructors

    /** default constructor */
    public SavingsCategoryRate() {
    }

    
    /** full constructor */
    public SavingsCategoryRate(SavingsCategoryRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public SavingsCategoryRateId getId() {
        return this.id;
    }
    
    public void setId(SavingsCategoryRateId id) {
        this.id = id;
    }
   








}