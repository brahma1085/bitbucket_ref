package com.banking.data.pojo;
// default package



/**
 * PSADDMst entity. @author MyEclipse Persistence Tools
 */

public class PSADDMst  implements java.io.Serializable {


    // Fields    

     private PSADDMstId id;


    // Constructors

    /** default constructor */
    public PSADDMst() {
    }

    
    /** full constructor */
    public PSADDMst(PSADDMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSADDMstId getId() {
        return this.id;
    }
    
    public void setId(PSADDMstId id) {
        this.id = id;
    }
   








}