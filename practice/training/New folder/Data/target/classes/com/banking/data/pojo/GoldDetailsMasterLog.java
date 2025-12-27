package com.banking.data.pojo;
// default package



/**
 * GoldDetailsMasterLog entity. @author MyEclipse Persistence Tools
 */

public class GoldDetailsMasterLog  implements java.io.Serializable {


    // Fields    

     private GoldDetailsMasterLogId id;


    // Constructors

    /** default constructor */
    public GoldDetailsMasterLog() {
    }

    
    /** full constructor */
    public GoldDetailsMasterLog(GoldDetailsMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public GoldDetailsMasterLogId getId() {
        return this.id;
    }
    
    public void setId(GoldDetailsMasterLogId id) {
        this.id = id;
    }
   








}