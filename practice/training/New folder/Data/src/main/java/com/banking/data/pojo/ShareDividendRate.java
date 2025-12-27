package com.banking.data.pojo;
// default package



/**
 * ShareDividendRate entity. @author MyEclipse Persistence Tools
 */

public class ShareDividendRate  implements java.io.Serializable {


    // Fields    

     private ShareDividendRateId id;


    // Constructors

    /** default constructor */
    public ShareDividendRate() {
    }

    
    /** full constructor */
    public ShareDividendRate(ShareDividendRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareDividendRateId getId() {
        return this.id;
    }
    
    public void setId(ShareDividendRateId id) {
        this.id = id;
    }
   








}