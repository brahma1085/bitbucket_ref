package com.banking.data.pojo;
// default package



/**
 * InterestPay entity. @author MyEclipse Persistence Tools
 */

public class InterestPay  implements java.io.Serializable {


    // Fields    

     private InterestPayId id;


    // Constructors

    /** default constructor */
    public InterestPay() {
    }

    
    /** full constructor */
    public InterestPay(InterestPayId id) {
        this.id = id;
    }

   
    // Property accessors

    public InterestPayId getId() {
        return this.id;
    }
    
    public void setId(InterestPayId id) {
        this.id = id;
    }
   








}