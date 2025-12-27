package com.banking.data.pojo;
// default package



/**
 * LockerRemainder entity. @author MyEclipse Persistence Tools
 */

public class LockerRemainder  implements java.io.Serializable {


    // Fields    

     private LockerRemainderId id;


    // Constructors

    /** default constructor */
    public LockerRemainder() {
    }

    
    /** full constructor */
    public LockerRemainder(LockerRemainderId id) {
        this.id = id;
    }

   
    // Property accessors

    public LockerRemainderId getId() {
        return this.id;
    }
    
    public void setId(LockerRemainderId id) {
        this.id = id;
    }
   








}