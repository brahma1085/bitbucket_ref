package com.banking.data.pojo;
// default package



/**
 * QuantumLimitLog entity. @author MyEclipse Persistence Tools
 */

public class QuantumLimitLog  implements java.io.Serializable {


    // Fields    

     private QuantumLimitLogId id;


    // Constructors

    /** default constructor */
    public QuantumLimitLog() {
    }

    
    /** full constructor */
    public QuantumLimitLog(QuantumLimitLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public QuantumLimitLogId getId() {
        return this.id;
    }
    
    public void setId(QuantumLimitLogId id) {
        this.id = id;
    }
   








}