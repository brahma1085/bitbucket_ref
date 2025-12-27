package com.banking.data.pojo;
// default package



/**
 * DepositCategoryRate entity. @author MyEclipse Persistence Tools
 */

public class DepositCategoryRate  implements java.io.Serializable {


    // Fields    

     private DepositCategoryRateId id;


    // Constructors

    /** default constructor */
    public DepositCategoryRate() {
    }

    
    /** full constructor */
    public DepositCategoryRate(DepositCategoryRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositCategoryRateId getId() {
        return this.id;
    }
    
    public void setId(DepositCategoryRateId id) {
        this.id = id;
    }
   








}