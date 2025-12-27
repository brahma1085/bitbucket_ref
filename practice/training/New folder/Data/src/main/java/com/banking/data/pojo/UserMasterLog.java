package com.banking.data.pojo;
// default package



/**
 * UserMasterLog entity. @author MyEclipse Persistence Tools
 */

public class UserMasterLog  implements java.io.Serializable {


    // Fields    

     private UserMasterLogId id;


    // Constructors

    /** default constructor */
    public UserMasterLog() {
    }

    
    /** full constructor */
    public UserMasterLog(UserMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserMasterLogId getId() {
        return this.id;
    }
    
    public void setId(UserMasterLogId id) {
        this.id = id;
    }
   








}