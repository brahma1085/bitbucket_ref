package com.banking.data.pojo;
// default package



/**
 * RemainderNotices entity. @author MyEclipse Persistence Tools
 */

public class RemainderNotices  implements java.io.Serializable {


    // Fields    

     private RemainderNoticesId id;


    // Constructors

    /** default constructor */
    public RemainderNotices() {
    }

    
    /** full constructor */
    public RemainderNotices(RemainderNoticesId id) {
        this.id = id;
    }

   
    // Property accessors

    public RemainderNoticesId getId() {
        return this.id;
    }
    
    public void setId(RemainderNoticesId id) {
        this.id = id;
    }
   








}