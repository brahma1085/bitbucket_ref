package com.banking.data.pojo;
// default package



/**
 * QuarterDefinitionLog entity. @author MyEclipse Persistence Tools
 */

public class QuarterDefinitionLog  implements java.io.Serializable {


    // Fields    

     private QuarterDefinitionLogId id;


    // Constructors

    /** default constructor */
    public QuarterDefinitionLog() {
    }

    
    /** full constructor */
    public QuarterDefinitionLog(QuarterDefinitionLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public QuarterDefinitionLogId getId() {
        return this.id;
    }
    
    public void setId(QuarterDefinitionLogId id) {
        this.id = id;
    }
   








}