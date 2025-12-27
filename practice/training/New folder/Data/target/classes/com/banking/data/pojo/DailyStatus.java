package com.banking.data.pojo;
// default package



/**
 * DailyStatus entity. @author MyEclipse Persistence Tools
 */

public class DailyStatus  implements java.io.Serializable {


    // Fields    

     private DailyStatusId id;


    // Constructors

    /** default constructor */
    public DailyStatus() {
    }

    
    /** full constructor */
    public DailyStatus(DailyStatusId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyStatusId getId() {
        return this.id;
    }
    
    public void setId(DailyStatusId id) {
        this.id = id;
    }
   








}