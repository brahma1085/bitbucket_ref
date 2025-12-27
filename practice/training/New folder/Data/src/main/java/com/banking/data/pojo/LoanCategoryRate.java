package com.banking.data.pojo;
// default package



/**
 * LoanCategoryRate entity. @author MyEclipse Persistence Tools
 */

public class LoanCategoryRate  implements java.io.Serializable {


    // Fields    

     private LoanCategoryRateId id;


    // Constructors

    /** default constructor */
    public LoanCategoryRate() {
    }

    
    /** full constructor */
    public LoanCategoryRate(LoanCategoryRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanCategoryRateId getId() {
        return this.id;
    }
    
    public void setId(LoanCategoryRateId id) {
        this.id = id;
    }
   








}