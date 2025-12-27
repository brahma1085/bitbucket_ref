package com.banking.data.pojo;
// default package



/**
 * UserMaster entity. @author MyEclipse Persistence Tools
 */

public class UserMaster  implements java.io.Serializable {


    // Fields    

     private UserMasterId id;


    // Constructors

    /** default constructor */
    public UserMaster() {
    }

    
    /** full constructor */
    public UserMaster(UserMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserMasterId getId() {
        return this.id;
    }
    
    public void setId(UserMasterId id) {
        this.id = id;
    }
   








}