package com.banking.data.pojo;
// default package



/**
 * DailyData entity. @author MyEclipse Persistence Tools
 */

public class DailyData  implements java.io.Serializable {


    // Fields    

     private DailyDataId id;


    // Constructors

    /** default constructor */
    public DailyData() {
    }

    
    /** full constructor */
    public DailyData(DailyDataId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyDataId getId() {
        return this.id;
    }
    
    public void setId(DailyDataId id) {
        this.id = id;
    }
   








}