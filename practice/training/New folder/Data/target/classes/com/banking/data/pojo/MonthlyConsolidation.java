package com.banking.data.pojo;
// default package



/**
 * MonthlyConsolidation entity. @author MyEclipse Persistence Tools
 */

public class MonthlyConsolidation  implements java.io.Serializable {


    // Fields    

     private MonthlyConsolidationId id;


    // Constructors

    /** default constructor */
    public MonthlyConsolidation() {
    }

    
    /** full constructor */
    public MonthlyConsolidation(MonthlyConsolidationId id) {
        this.id = id;
    }

   
    // Property accessors

    public MonthlyConsolidationId getId() {
        return this.id;
    }
    
    public void setId(MonthlyConsolidationId id) {
        this.id = id;
    }
   








}