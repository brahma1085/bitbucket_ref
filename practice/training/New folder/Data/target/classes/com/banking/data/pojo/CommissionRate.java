package com.banking.data.pojo;
// default package



/**
 * CommissionRate entity. @author MyEclipse Persistence Tools
 */

public class CommissionRate  implements java.io.Serializable {


    // Fields    

     private CommissionRateId id;


    // Constructors

    /** default constructor */
    public CommissionRate() {
    }

    
    /** full constructor */
    public CommissionRate(CommissionRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public CommissionRateId getId() {
        return this.id;
    }
    
    public void setId(CommissionRateId id) {
        this.id = id;
    }
   








}