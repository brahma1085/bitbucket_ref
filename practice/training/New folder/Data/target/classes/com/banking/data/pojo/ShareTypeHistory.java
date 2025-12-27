package com.banking.data.pojo;
// default package



/**
 * ShareTypeHistory entity. @author MyEclipse Persistence Tools
 */

public class ShareTypeHistory  implements java.io.Serializable {


    // Fields    

     private ShareTypeHistoryId id;


    // Constructors

    /** default constructor */
    public ShareTypeHistory() {
    }

    
    /** full constructor */
    public ShareTypeHistory(ShareTypeHistoryId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareTypeHistoryId getId() {
        return this.id;
    }
    
    public void setId(ShareTypeHistoryId id) {
        this.id = id;
    }
   








}