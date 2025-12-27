package com.banking.data.pojo;
// default package



/**
 * MonthlyStatus entity. @author MyEclipse Persistence Tools
 */

public class MonthlyStatus  implements java.io.Serializable {


    // Fields    

     private MonthlyStatusId id;


    // Constructors

    /** default constructor */
    public MonthlyStatus() {
    }

    
    /** full constructor */
    public MonthlyStatus(MonthlyStatusId id) {
        this.id = id;
    }

   
    // Property accessors

    public MonthlyStatusId getId() {
        return this.id;
    }
    
    public void setId(MonthlyStatusId id) {
        this.id = id;
    }
   








}