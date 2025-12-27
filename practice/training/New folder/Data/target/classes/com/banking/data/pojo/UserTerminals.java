package com.banking.data.pojo;
// default package



/**
 * UserTerminals entity. @author MyEclipse Persistence Tools
 */

public class UserTerminals  implements java.io.Serializable {


    // Fields    

     private UserTerminalsId id;


    // Constructors

    /** default constructor */
    public UserTerminals() {
    }

    
    /** full constructor */
    public UserTerminals(UserTerminalsId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserTerminalsId getId() {
        return this.id;
    }
    
    public void setId(UserTerminalsId id) {
        this.id = id;
    }
   








}