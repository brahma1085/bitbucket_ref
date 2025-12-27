package com.banking.data.pojo;
// default package



/**
 * PSLnMst entity. @author MyEclipse Persistence Tools
 */

public class PSLnMst  implements java.io.Serializable {


    // Fields    

     private PSLnMstId id;


    // Constructors

    /** default constructor */
    public PSLnMst() {
    }

    
    /** full constructor */
    public PSLnMst(PSLnMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSLnMstId getId() {
        return this.id;
    }
    
    public void setId(PSLnMstId id) {
        this.id = id;
    }
   








}