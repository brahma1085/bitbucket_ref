package com.banking.data.pojo;
// default package



/**
 * PSACMst entity. @author MyEclipse Persistence Tools
 */

public class PSACMst  implements java.io.Serializable {


    // Fields    

     private PSACMstId id;


    // Constructors

    /** default constructor */
    public PSACMst() {
    }

    
    /** full constructor */
    public PSACMst(PSACMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSACMstId getId() {
        return this.id;
    }
    
    public void setId(PSACMstId id) {
        this.id = id;
    }
   








}