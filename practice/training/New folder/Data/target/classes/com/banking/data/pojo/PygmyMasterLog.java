package com.banking.data.pojo;
// default package



/**
 * PygmyMasterLog entity. @author MyEclipse Persistence Tools
 */

public class PygmyMasterLog  implements java.io.Serializable {


    // Fields    

     private PygmyMasterLogId id;


    // Constructors

    /** default constructor */
    public PygmyMasterLog() {
    }

    
    /** full constructor */
    public PygmyMasterLog(PygmyMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public PygmyMasterLogId getId() {
        return this.id;
    }
    
    public void setId(PygmyMasterLogId id) {
        this.id = id;
    }
   








}