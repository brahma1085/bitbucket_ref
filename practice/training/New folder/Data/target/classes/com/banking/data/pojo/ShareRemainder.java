package com.banking.data.pojo;
// default package



/**
 * ShareRemainder entity. @author MyEclipse Persistence Tools
 */

public class ShareRemainder  implements java.io.Serializable {


    // Fields    

     private ShareRemainderId id;


    // Constructors

    /** default constructor */
    public ShareRemainder() {
    }

    
    /** full constructor */
    public ShareRemainder(ShareRemainderId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareRemainderId getId() {
        return this.id;
    }
    
    public void setId(ShareRemainderId id) {
        this.id = id;
    }
   








}