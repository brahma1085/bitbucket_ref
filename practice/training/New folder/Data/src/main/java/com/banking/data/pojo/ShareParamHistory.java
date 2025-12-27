package com.banking.data.pojo;
// default package



/**
 * ShareParamHistory entity. @author MyEclipse Persistence Tools
 */

public class ShareParamHistory  implements java.io.Serializable {


    // Fields    

     private ShareParamHistoryId id;


    // Constructors

    /** default constructor */
    public ShareParamHistory() {
    }

    
    /** full constructor */
    public ShareParamHistory(ShareParamHistoryId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareParamHistoryId getId() {
        return this.id;
    }
    
    public void setId(ShareParamHistoryId id) {
        this.id = id;
    }
   








}