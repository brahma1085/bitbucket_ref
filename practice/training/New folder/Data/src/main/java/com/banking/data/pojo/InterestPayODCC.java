package com.banking.data.pojo;
// default package



/**
 * InterestPayODCC entity. @author MyEclipse Persistence Tools
 */

public class InterestPayODCC  implements java.io.Serializable {


    // Fields    

     private InterestPayODCCId id;


    // Constructors

    /** default constructor */
    public InterestPayODCC() {
    }

    
    /** full constructor */
    public InterestPayODCC(InterestPayODCCId id) {
        this.id = id;
    }

   
    // Property accessors

    public InterestPayODCCId getId() {
        return this.id;
    }
    
    public void setId(InterestPayODCCId id) {
        this.id = id;
    }
   








}