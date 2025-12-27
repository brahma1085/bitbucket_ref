package com.banking.data.pojo;
// default package



/**
 * RoleDefinition entity. @author MyEclipse Persistence Tools
 */

public class RoleDefinition  implements java.io.Serializable {


    // Fields    

     private RoleDefinitionId id;


    // Constructors

    /** default constructor */
    public RoleDefinition() {
    }

    
    /** full constructor */
    public RoleDefinition(RoleDefinitionId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleDefinitionId getId() {
        return this.id;
    }
    
    public void setId(RoleDefinitionId id) {
        this.id = id;
    }
   








}