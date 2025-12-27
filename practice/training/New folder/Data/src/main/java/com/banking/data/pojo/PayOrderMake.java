package com.banking.data.pojo;
// default package



/**
 * PayOrderMake entity. @author MyEclipse Persistence Tools
 */

public class PayOrderMake  implements java.io.Serializable {


    // Fields    

     private PayOrderMakeId id;


    // Constructors

    /** default constructor */
    public PayOrderMake() {
    }

    
    /** full constructor */
    public PayOrderMake(PayOrderMakeId id) {
        this.id = id;
    }

   
    // Property accessors

    public PayOrderMakeId getId() {
        return this.id;
    }
    
    public void setId(PayOrderMakeId id) {
        this.id = id;
    }
   








}