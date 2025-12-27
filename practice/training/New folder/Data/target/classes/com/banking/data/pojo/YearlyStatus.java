package com.banking.data.pojo;
// default package



/**
 * YearlyStatus entity. @author MyEclipse Persistence Tools
 */

public class YearlyStatus  implements java.io.Serializable {


    // Fields    

     private YearlyStatusId id;


    // Constructors

    /** default constructor */
    public YearlyStatus() {
    }

    
    /** full constructor */
    public YearlyStatus(YearlyStatusId id) {
        this.id = id;
    }

   
    // Property accessors

    public YearlyStatusId getId() {
        return this.id;
    }
    
    public void setId(YearlyStatusId id) {
        this.id = id;
    }
   








}