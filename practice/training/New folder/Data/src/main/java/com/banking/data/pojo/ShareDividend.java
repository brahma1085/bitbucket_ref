package com.banking.data.pojo;
// default package



/**
 * ShareDividend entity. @author MyEclipse Persistence Tools
 */

public class ShareDividend  implements java.io.Serializable {


    // Fields    

     private ShareDividendId id;


    // Constructors

    /** default constructor */
    public ShareDividend() {
    }

    
    /** full constructor */
    public ShareDividend(ShareDividendId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareDividendId getId() {
        return this.id;
    }
    
    public void setId(ShareDividendId id) {
        this.id = id;
    }
   








}