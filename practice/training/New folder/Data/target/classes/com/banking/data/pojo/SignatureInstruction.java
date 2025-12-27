package com.banking.data.pojo;
// default package



/**
 * SignatureInstruction entity. @author MyEclipse Persistence Tools
 */

public class SignatureInstruction  implements java.io.Serializable {


    // Fields    

     private SignatureInstructionId id;


    // Constructors

    /** default constructor */
    public SignatureInstruction() {
    }

    
    /** full constructor */
    public SignatureInstruction(SignatureInstructionId id) {
        this.id = id;
    }

   
    // Property accessors

    public SignatureInstructionId getId() {
        return this.id;
    }
    
    public void setId(SignatureInstructionId id) {
        this.id = id;
    }
   








}