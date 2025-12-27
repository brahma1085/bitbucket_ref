package com.banking.data.pojo;
// default package



/**
 * UserRoleMapping entity. @author MyEclipse Persistence Tools
 */

public class UserRoleMapping  implements java.io.Serializable {


    // Fields    

     private UserRoleMappingId id;


    // Constructors

    /** default constructor */
    public UserRoleMapping() {
    }

    
    /** full constructor */
    public UserRoleMapping(UserRoleMappingId id) {
        this.id = id;
    }

   
    // Property accessors

    public UserRoleMappingId getId() {
        return this.id;
    }
    
    public void setId(UserRoleMappingId id) {
        this.id = id;
    }
   








}