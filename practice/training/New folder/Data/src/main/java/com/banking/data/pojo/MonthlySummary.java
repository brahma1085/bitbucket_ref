package com.banking.data.pojo;
// default package



/**
 * MonthlySummary entity. @author MyEclipse Persistence Tools
 */

public class MonthlySummary  implements java.io.Serializable {


    // Fields    

     private MonthlySummaryId id;


    // Constructors

    /** default constructor */
    public MonthlySummary() {
    }

    
    /** full constructor */
    public MonthlySummary(MonthlySummaryId id) {
        this.id = id;
    }

   
    // Property accessors

    public MonthlySummaryId getId() {
        return this.id;
    }
    
    public void setId(MonthlySummaryId id) {
        this.id = id;
    }
   








}