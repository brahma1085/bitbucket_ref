package com.banking.data.pojo;
// default package



/**
 * DailyConsolidation entity. @author MyEclipse Persistence Tools
 */

public class DailyConsolidation  implements java.io.Serializable {


    // Fields    

     private DailyConsolidationId id;


    // Constructors

    /** default constructor */
    public DailyConsolidation() {
    }

    
    /** full constructor */
    public DailyConsolidation(DailyConsolidationId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyConsolidationId getId() {
        return this.id;
    }
    
    public void setId(DailyConsolidationId id) {
        this.id = id;
    }
   








}