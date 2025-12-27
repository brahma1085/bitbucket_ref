package com.banking.data.pojo;
// default package



/**
 * DailyStatusSummary entity. @author MyEclipse Persistence Tools
 */

public class DailyStatusSummary  implements java.io.Serializable {


    // Fields    

     private DailyStatusSummaryId id;


    // Constructors

    /** default constructor */
    public DailyStatusSummary() {
    }

    
    /** full constructor */
    public DailyStatusSummary(DailyStatusSummaryId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyStatusSummaryId getId() {
        return this.id;
    }
    
    public void setId(DailyStatusSummaryId id) {
        this.id = id;
    }
   








}