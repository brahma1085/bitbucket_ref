package com.banking.data.pojo;
// default package



/**
 * VehicleMasterLog entity. @author MyEclipse Persistence Tools
 */

public class VehicleMasterLog  implements java.io.Serializable {


    // Fields    

     private VehicleMasterLogId id;


    // Constructors

    /** default constructor */
    public VehicleMasterLog() {
    }

    
    /** full constructor */
    public VehicleMasterLog(VehicleMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public VehicleMasterLogId getId() {
        return this.id;
    }
    
    public void setId(VehicleMasterLogId id) {
        this.id = id;
    }
   








}