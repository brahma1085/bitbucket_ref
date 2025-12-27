package com.banking.data.pojo;
// default package



/**
 * SignatureInstructionLog entity. @author MyEclipse Persistence Tools
 */

public class SignatureInstructionLog  implements java.io.Serializable {


    // Fields    

     private SignatureInstructionLogId id;


    // Constructors

    /** default constructor */
    public SignatureInstructionLog() {
    }

    
    /** full constructor */
    public SignatureInstructionLog(SignatureInstructionLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public SignatureInstructionLogId getId() {
        return this.id;
    }
    
    public void setId(SignatureInstructionLogId id) {
        this.id = id;
    }
   








}