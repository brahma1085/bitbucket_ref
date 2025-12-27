package com.banking.data.pojo;
// default package



/**
 * RoleAccess entity. @author MyEclipse Persistence Tools
 */

public class RoleAccess  implements java.io.Serializable {


    // Fields    

     private RoleAccessId id;


    // Constructors

    /** default constructor */
    public RoleAccess() {
    }

    
    /** full constructor */
    public RoleAccess(RoleAccessId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleAccessId getId() {
        return this.id;
    }
    
    public void setId(RoleAccessId id) {
        this.id = id;
    }
   








}