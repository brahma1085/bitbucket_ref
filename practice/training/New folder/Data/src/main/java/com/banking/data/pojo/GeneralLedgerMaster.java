package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerMaster entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerMaster  implements java.io.Serializable {


    // Fields    

     private GeneralLedgerMasterId id;


    // Constructors

    /** default constructor */
    public GeneralLedgerMaster() {
    }

    
    /** full constructor */
    public GeneralLedgerMaster(GeneralLedgerMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public GeneralLedgerMasterId getId() {
        return this.id;
    }
    
    public void setId(GeneralLedgerMasterId id) {
        this.id = id;
    }
   








}