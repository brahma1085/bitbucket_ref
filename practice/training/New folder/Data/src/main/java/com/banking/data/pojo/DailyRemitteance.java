package com.banking.data.pojo;
// default package



/**
 * DailyRemitteance entity. @author MyEclipse Persistence Tools
 */

public class DailyRemitteance  implements java.io.Serializable {


    // Fields    

     private DailyRemitteanceId id;


    // Constructors

    /** default constructor */
    public DailyRemitteance() {
    }

    
    /** full constructor */
    public DailyRemitteance(DailyRemitteanceId id) {
        this.id = id;
    }

   
    // Property accessors

    public DailyRemitteanceId getId() {
        return this.id;
    }
    
    public void setId(DailyRemitteanceId id) {
        this.id = id;
    }
   








}