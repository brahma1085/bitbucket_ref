package com.banking.data.pojo;
// default package



/**
 * CityMaster entity. @author MyEclipse Persistence Tools
 */

public class CityMaster  implements java.io.Serializable {


    // Fields    

     private CityMasterId id;


    // Constructors

    /** default constructor */
    public CityMaster() {
    }

    
    /** full constructor */
    public CityMaster(CityMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public CityMasterId getId() {
        return this.id;
    }
    
    public void setId(CityMasterId id) {
        this.id = id;
    }
   








}