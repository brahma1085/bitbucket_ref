package com.banking.data.pojo;
// default package



/**
 * UserActivity entity. @author MyEclipse Persistence Tools
 */

public class UserActivity  implements java.io.Serializable {


    // Fields    

     private UserActivityId id;


    // Constructors

    /** default constructor */
    public UserActivity() {
    }

    
    /** full constructor */
    public UserActivity(UserActivityId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserActivityId getId() {
        return this.id;
    }
    
    public void setId(UserActivityId id) {
        this.id = id;
    }
   








}