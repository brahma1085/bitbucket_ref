package com.banking.data.pojo;
// default package



/**
 * RelativeMaster entity. @author MyEclipse Persistence Tools
 */

public class RelativeMaster  implements java.io.Serializable {


    // Fields    

     private RelativeMasterId id;


    // Constructors

    /** default constructor */
    public RelativeMaster() {
    }

    
    /** full constructor */
    public RelativeMaster(RelativeMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public RelativeMasterId getId() {
        return this.id;
    }
    
    public void setId(RelativeMasterId id) {
        this.id = id;
    }
   








}