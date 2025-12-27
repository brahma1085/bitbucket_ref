package com.banking.data.pojo;
// default package



/**
 * ODCCMasterLog entity. @author MyEclipse Persistence Tools
 */

public class ODCCMasterLog  implements java.io.Serializable {


    // Fields    

     private ODCCMasterLogId id;


    // Constructors

    /** default constructor */
    public ODCCMasterLog() {
    }

    
    /** full constructor */
    public ODCCMasterLog(ODCCMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public ODCCMasterLogId getId() {
        return this.id;
    }
    
    public void setId(ODCCMasterLogId id) {
        this.id = id;
    }
   








}