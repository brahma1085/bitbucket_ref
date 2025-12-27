package com.banking.data.pojo;
// default package



/**
 * QuantumLimit entity. @author MyEclipse Persistence Tools
 */

public class QuantumLimit  implements java.io.Serializable {


    // Fields    

     private QuantumLimitId id;


    // Constructors

    /** default constructor */
    public QuantumLimit() {
    }

    
    /** full constructor */
    public QuantumLimit(QuantumLimitId id) {
        this.id = id;
    }

   
    // Property accessors

    public QuantumLimitId getId() {
        return this.id;
    }
    
    public void setId(QuantumLimitId id) {
        this.id = id;
    }
   








}