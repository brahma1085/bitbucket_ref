package com.banking.data.pojo;
// default package



/**
 * HolidayMaster entity. @author MyEclipse Persistence Tools
 */

public class HolidayMaster  implements java.io.Serializable {


    // Fields    

     private HolidayMasterId id;


    // Constructors

    /** default constructor */
    public HolidayMaster() {
    }

    
    /** full constructor */
    public HolidayMaster(HolidayMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public HolidayMasterId getId() {
        return this.id;
    }
    
    public void setId(HolidayMasterId id) {
        this.id = id;
    }
   








}