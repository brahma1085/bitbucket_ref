package com.banking.data.pojo;
// default package



/**
 * PayOrderCommission entity. @author MyEclipse Persistence Tools
 */

public class PayOrderCommission  implements java.io.Serializable {


    // Fields    

     private PayOrderCommissionId id;


    // Constructors

    /** default constructor */
    public PayOrderCommission() {
    }

    
    /** full constructor */
    public PayOrderCommission(PayOrderCommissionId id) {
        this.id = id;
    }

   
    // Property accessors

    public PayOrderCommissionId getId() {
        return this.id;
    }
    
    public void setId(PayOrderCommissionId id) {
        this.id = id;
    }
   








}