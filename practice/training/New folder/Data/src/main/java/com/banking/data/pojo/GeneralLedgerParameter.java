package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerParameter entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerParameter  implements java.io.Serializable {


    // Fields    

     private GeneralLedgerParameterId id;


    // Constructors

    /** default constructor */
    public GeneralLedgerParameter() {
    }

    
    /** full constructor */
    public GeneralLedgerParameter(GeneralLedgerParameterId id) {
        this.id = id;
    }

   
    // Property accessors

    public GeneralLedgerParameterId getId() {
        return this.id;
    }
    
    public void setId(GeneralLedgerParameterId id) {
        this.id = id;
    }
   








}