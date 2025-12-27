package com.banking.data.pojo;
// default package



/**
 * PSNmMst entity. @author MyEclipse Persistence Tools
 */

public class PSNmMst  implements java.io.Serializable {


    // Fields    

     private PSNmMstId id;


    // Constructors

    /** default constructor */
    public PSNmMst() {
    }

    
    /** full constructor */
    public PSNmMst(PSNmMstId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSNmMstId getId() {
        return this.id;
    }
    
    public void setId(PSNmMstId id) {
        this.id = id;
    }
   








}