package com.banking.data.pojo;
// default package



/**
 * PSWSDetails entity. @author MyEclipse Persistence Tools
 */

public class PSWSDetails  implements java.io.Serializable {


    // Fields    

     private PSWSDetailsId id;


    // Constructors

    /** default constructor */
    public PSWSDetails() {
    }

    
    /** full constructor */
    public PSWSDetails(PSWSDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public PSWSDetailsId getId() {
        return this.id;
    }
    
    public void setId(PSWSDetailsId id) {
        this.id = id;
    }
   








}