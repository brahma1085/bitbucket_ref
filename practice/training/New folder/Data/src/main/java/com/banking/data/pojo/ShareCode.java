package com.banking.data.pojo;
// default package



/**
 * ShareCode entity. @author MyEclipse Persistence Tools
 */

public class ShareCode  implements java.io.Serializable {


    // Fields    

     private ShareCodeId id;


    // Constructors

    /** default constructor */
    public ShareCode() {
    }

    
    /** full constructor */
    public ShareCode(ShareCodeId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareCodeId getId() {
        return this.id;
    }
    
    public void setId(ShareCodeId id) {
        this.id = id;
    }
   








}