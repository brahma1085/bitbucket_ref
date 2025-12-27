package com.banking.data.pojo;
// default package



/**
 * ShareMasterLog entity. @author MyEclipse Persistence Tools
 */

public class ShareMasterLog  implements java.io.Serializable {


    // Fields    

     private ShareMasterLogId id;


    // Constructors

    /** default constructor */
    public ShareMasterLog() {
    }

    
    /** full constructor */
    public ShareMasterLog(ShareMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareMasterLogId getId() {
        return this.id;
    }
    
    public void setId(ShareMasterLogId id) {
        this.id = id;
    }
   








}