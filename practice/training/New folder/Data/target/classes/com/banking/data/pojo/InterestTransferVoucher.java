package com.banking.data.pojo;
// default package



/**
 * InterestTransferVoucher entity. @author MyEclipse Persistence Tools
 */

public class InterestTransferVoucher  implements java.io.Serializable {


    // Fields    

     private InterestTransferVoucherId id;


    // Constructors

    /** default constructor */
    public InterestTransferVoucher() {
    }

    
    /** full constructor */
    public InterestTransferVoucher(InterestTransferVoucherId id) {
        this.id = id;
    }

   
    // Property accessors

    public InterestTransferVoucherId getId() {
        return this.id;
    }
    
    public void setId(InterestTransferVoucherId id) {
        this.id = id;
    }
   








}