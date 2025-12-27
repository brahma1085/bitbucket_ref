package com.banking.data.pojo;
// default package



/**
 * VoucherData entity. @author MyEclipse Persistence Tools
 */

public class VoucherData  implements java.io.Serializable {


    // Fields    

     private VoucherDataId id;


    // Constructors

    /** default constructor */
    public VoucherData() {
    }

    
    /** full constructor */
    public VoucherData(VoucherDataId id) {
        this.id = id;
    }

   
    // Property accessors

    public VoucherDataId getId() {
        return this.id;
    }
    
    public void setId(VoucherDataId id) {
        this.id = id;
    }
   








}