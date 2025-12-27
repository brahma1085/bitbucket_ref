package com.banking.data.pojo;
// default package



/**
 * DailyConsolidationStatus entity. @author MyEclipse Persistence Tools
 */

public class DailyConsolidationStatus  implements java.io.Serializable {


    // Fields    

     private DailyConsolidationStatusId id;


    // Constructors

    /** default constructor */
    public DailyConsolidationStatus() {
    }

    
    /** full constructor */
    public DailyConsolidationStatus(DailyConsolidationStatusId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyConsolidationStatusId getId() {
        return this.id;
    }
    
    public void setId(DailyConsolidationStatusId id) {
        this.id = id;
    }
   








}