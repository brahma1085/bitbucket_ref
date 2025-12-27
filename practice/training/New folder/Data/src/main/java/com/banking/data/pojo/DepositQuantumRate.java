package com.banking.data.pojo;
// default package



/**
 * DepositQuantumRate entity. @author MyEclipse Persistence Tools
 */

public class DepositQuantumRate  implements java.io.Serializable {


    // Fields    

     private DepositQuantumRateId id;


    // Constructors

    /** default constructor */
    public DepositQuantumRate() {
    }

    
    /** full constructor */
    public DepositQuantumRate(DepositQuantumRateId id) {
        this.id = id;
    }

   
    // Property accessors

    public DepositQuantumRateId getId() {
        return this.id;
    }
    
    public void setId(DepositQuantumRateId id) {
        this.id = id;
    }
   








}