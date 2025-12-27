package com.banking.data.pojo;
// default package



/**
 * PSSHMst entity. @author MyEclipse Persistence Tools
 */

public class PSSHMst  implements java.io.Serializable {


    // Fields    

     private PSSHMstId id;


    // Constructors

    /** default constructor */
    public PSSHMst() {
    }

    
    /** full constructor */
    public PSSHMst(PSSHMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSSHMstId getId() {
        return this.id;
    }
    
    public void setId(PSSHMstId id) {
        this.id = id;
    }
   








}