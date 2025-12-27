package com.banking.data.pojo;
// default package



/**
 * NomineeMaster entity. @author MyEclipse Persistence Tools
 */

public class NomineeMaster  implements java.io.Serializable {


    // Fields    

     private NomineeMasterId id;


    // Constructors

    /** default constructor */
    public NomineeMaster() {
    }

    
    /** full constructor */
    public NomineeMaster(NomineeMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public NomineeMasterId getId() {
        return this.id;
    }
    
    public void setId(NomineeMasterId id) {
        this.id = id;
    }
   








}