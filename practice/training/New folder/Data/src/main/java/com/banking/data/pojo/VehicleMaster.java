package com.banking.data.pojo;
// default package



/**
 * VehicleMaster entity. @author MyEclipse Persistence Tools
 */

public class VehicleMaster  implements java.io.Serializable {


    // Fields    

     private VehicleMasterId id;


    // Constructors

    /** default constructor */
    public VehicleMaster() {
    }

    
    /** full constructor */
    public VehicleMaster(VehicleMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public VehicleMasterId getId() {
        return this.id;
    }
    
    public void setId(VehicleMasterId id) {
        this.id = id;
    }
   








}