package com.banking.data.pojo;
// default package



/**
 * RelativeMasterLog entity. @author MyEclipse Persistence Tools
 */

public class RelativeMasterLog  implements java.io.Serializable {


    // Fields    

     private RelativeMasterLogId id;


    // Constructors

    /** default constructor */
    public RelativeMasterLog() {
    }

    
    /** full constructor */
    public RelativeMasterLog(RelativeMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public RelativeMasterLogId getId() {
        return this.id;
    }
    
    public void setId(RelativeMasterLogId id) {
        this.id = id;
    }
   








}