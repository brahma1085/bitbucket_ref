package com.banking.data.pojo;
// default package



/**
 * QuarterDefinition entity. @author MyEclipse Persistence Tools
 */

public class QuarterDefinition  implements java.io.Serializable {


    // Fields    

     private QuarterDefinitionId id;


    // Constructors

    /** default constructor */
    public QuarterDefinition() {
    }

    
    /** full constructor */
    public QuarterDefinition(QuarterDefinitionId id) {
        this.id = id;
    }

   
    // Property accessors

    public QuarterDefinitionId getId() {
        return this.id;
    }
    
    public void setId(QuarterDefinitionId id) {
        this.id = id;
    }
   








}