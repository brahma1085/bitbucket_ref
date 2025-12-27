package com.banking.data.pojo;
// default package



/**
 * PSBNMst entity. @author MyEclipse Persistence Tools
 */

public class PSBNMst  implements java.io.Serializable {


    // Fields    

     private PSBNMstId id;


    // Constructors

    /** default constructor */
    public PSBNMst() {
    }

    
    /** full constructor */
    public PSBNMst(PSBNMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSBNMstId getId() {
        return this.id;
    }
    
    public void setId(PSBNMstId id) {
        this.id = id;
    }
   








}