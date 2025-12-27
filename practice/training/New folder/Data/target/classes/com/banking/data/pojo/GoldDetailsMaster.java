package com.banking.data.pojo;
// default package



/**
 * GoldDetailsMaster entity. @author MyEclipse Persistence Tools
 */

public class GoldDetailsMaster  implements java.io.Serializable {


    // Fields    

     private GoldDetailsMasterId id;


    // Constructors

    /** default constructor */
    public GoldDetailsMaster() {
    }

    
    /** full constructor */
    public GoldDetailsMaster(GoldDetailsMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public GoldDetailsMasterId getId() {
        return this.id;
    }
    
    public void setId(GoldDetailsMasterId id) {
        this.id = id;
    }
   








}