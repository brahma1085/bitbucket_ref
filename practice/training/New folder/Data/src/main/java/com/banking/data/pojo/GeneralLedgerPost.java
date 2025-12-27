package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerPost entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerPost  implements java.io.Serializable {


    // Fields    

     private GeneralLedgerPostId id;


    // Constructors

    /** default constructor */
    public GeneralLedgerPost() {
    }

    
    /** full constructor */
    public GeneralLedgerPost(GeneralLedgerPostId id) {
        this.id = id;
    }

   
    // Property accessors

    public GeneralLedgerPostId getId() {
        return this.id;
    }
    
    public void setId(GeneralLedgerPostId id) {
        this.id = id;
    }
   








}