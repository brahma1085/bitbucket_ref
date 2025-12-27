package com.banking.data.pojo;
// default package



/**
 * DirectorRelation entity. @author MyEclipse Persistence Tools
 */

public class DirectorRelation  implements java.io.Serializable {


    // Fields    

     private DirectorRelationId id;


    // Constructors

    /** default constructor */
    public DirectorRelation() {
    }

    
    /** full constructor */
    public DirectorRelation(DirectorRelationId id) {
        this.id = id;
    }

   
    // Property accessors

    public DirectorRelationId getId() {
        return this.id;
    }
    
    public void setId(DirectorRelationId id) {
        this.id = id;
    }
   








}