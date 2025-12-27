package com.banking.data.pojo;
// default package



/**
 * DividendRemainderNotice entity. @author MyEclipse Persistence Tools
 */

public class DividendRemainderNotice  implements java.io.Serializable {


    // Fields    

     private DividendRemainderNoticeId id;


    // Constructors

    /** default constructor */
    public DividendRemainderNotice() {
    }

    
    /** full constructor */
    public DividendRemainderNotice(DividendRemainderNoticeId id) {
        this.id = id;
    }

   
    // Property accessors

    public DividendRemainderNoticeId getId() {
        return this.id;
    }
    
    public void setId(DividendRemainderNoticeId id) {
        this.id = id;
    }
   








}