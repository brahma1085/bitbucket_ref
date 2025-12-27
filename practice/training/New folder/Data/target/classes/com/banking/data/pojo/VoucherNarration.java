package com.banking.data.pojo;
// default package



/**
 * VoucherNarration entity. @author MyEclipse Persistence Tools
 */

public class VoucherNarration  implements java.io.Serializable {


    // Fields    

     private VoucherNarrationId id;


    // Constructors

    /** default constructor */
    public VoucherNarration() {
    }

    
    /** full constructor */
    public VoucherNarration(VoucherNarrationId id) {
        this.id = id;
    }

   
    // Property accessors

    public VoucherNarrationId getId() {
        return this.id;
    }
    
    public void setId(VoucherNarrationId id) {
        this.id = id;
    }
   








}