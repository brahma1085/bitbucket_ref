package com.banking.data.pojo;
// default package



/**
 * PSDPMst entity. @author MyEclipse Persistence Tools
 */

public class PSDPMst  implements java.io.Serializable {


    // Fields    

     private PSDPMstId id;


    // Constructors

    /** default constructor */
    public PSDPMst() {
    }

    
    /** full constructor */
    public PSDPMst(PSDPMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSDPMstId getId() {
        return this.id;
    }
    
    public void setId(PSDPMstId id) {
        this.id = id;
    }
   








}