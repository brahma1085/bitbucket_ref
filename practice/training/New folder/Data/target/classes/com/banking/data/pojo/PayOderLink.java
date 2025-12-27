package com.banking.data.pojo;
// default package



/**
 * PayOderLink entity. @author MyEclipse Persistence Tools
 */

public class PayOderLink  implements java.io.Serializable {


    // Fields    

     private PayOderLinkId id;


    // Constructors

    /** default constructor */
    public PayOderLink() {
    }

    
    /** full constructor */
    public PayOderLink(PayOderLinkId id) {
        this.id = id;
    }

   
    // Property accessors

    public PayOderLinkId getId() {
        return this.id;
    }
    
    public void setId(PayOderLinkId id) {
        this.id = id;
    }
   








}