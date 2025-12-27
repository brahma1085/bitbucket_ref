package com.banking.data.pojo;
// default package



/**
 * SecurityDetails entity. @author MyEclipse Persistence Tools
 */

public class SecurityDetails  implements java.io.Serializable {


    // Fields    

     private SecurityDetailsId id;


    // Constructors

    /** default constructor */
    public SecurityDetails() {
    }

    
    /** full constructor */
    public SecurityDetails(SecurityDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public SecurityDetailsId getId() {
        return this.id;
    }
    
    public void setId(SecurityDetailsId id) {
        this.id = id;
    }
   








}