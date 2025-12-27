package com.banking.data.pojo;
// default package



/**
 * ChequeWithdrawl entity. @author MyEclipse Persistence Tools
 */

public class ChequeWithdrawl  implements java.io.Serializable {


    // Fields    

     private ChequeWithdrawlId id;


    // Constructors

    /** default constructor */
    public ChequeWithdrawl() {
    }

    
    /** full constructor */
    public ChequeWithdrawl(ChequeWithdrawlId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeWithdrawlId getId() {
        return this.id;
    }
    
    public void setId(ChequeWithdrawlId id) {
        this.id = id;
    }
   








}