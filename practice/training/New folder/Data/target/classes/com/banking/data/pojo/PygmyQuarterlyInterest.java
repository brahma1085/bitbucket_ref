package com.banking.data.pojo;
// default package



/**
 * PygmyQuarterlyInterest entity. @author MyEclipse Persistence Tools
 */

public class PygmyQuarterlyInterest  implements java.io.Serializable {


    // Fields    

     private PygmyQuarterlyInterestId id;


    // Constructors

    /** default constructor */
    public PygmyQuarterlyInterest() {
    }

    
    /** full constructor */
    public PygmyQuarterlyInterest(PygmyQuarterlyInterestId id) {
        this.id = id;
    }

   
    // Property accessors

    public PygmyQuarterlyInterestId getId() {
        return this.id;
    }
    
    public void setId(PygmyQuarterlyInterestId id) {
        this.id = id;
    }
   








}