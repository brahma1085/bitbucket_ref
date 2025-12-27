package com.banking.data.pojo;
// default package



/**
 * MonthlyConsolidationStatus entity. @author MyEclipse Persistence Tools
 */

public class MonthlyConsolidationStatus  implements java.io.Serializable {


    // Fields    

     private MonthlyConsolidationStatusId id;


    // Constructors

    /** default constructor */
    public MonthlyConsolidationStatus() {
    }

    
    /** full constructor */
    public MonthlyConsolidationStatus(MonthlyConsolidationStatusId id) {
        this.id = id;
    }

   
    // Property accessors

    public MonthlyConsolidationStatusId getId() {
        return this.id;
    }
    
    public void setId(MonthlyConsolidationStatusId id) {
        this.id = id;
    }
   








}