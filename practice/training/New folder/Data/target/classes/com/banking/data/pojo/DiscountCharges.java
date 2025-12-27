package com.banking.data.pojo;
// default package



/**
 * DiscountCharges entity. @author MyEclipse Persistence Tools
 */

public class DiscountCharges  implements java.io.Serializable {


    // Fields    

     private DiscountChargesId id;


    // Constructors

    /** default constructor */
    public DiscountCharges() {
    }

    
    /** full constructor */
    public DiscountCharges(DiscountChargesId id) {
        this.id = id;
    }

   
    // Property accessors

    public DiscountChargesId getId() {
        return this.id;
    }
    
    public void setId(DiscountChargesId id) {
        this.id = id;
    }
   








}