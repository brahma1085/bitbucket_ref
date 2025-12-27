package com.banking.data.pojo;
// default package



/**
 * StageMaster entity. @author MyEclipse Persistence Tools
 */

public class StageMaster  implements java.io.Serializable {


    // Fields    

     private StageMasterId id;


    // Constructors

    /** default constructor */
    public StageMaster() {
    }

    
    /** full constructor */
    public StageMaster(StageMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public StageMasterId getId() {
        return this.id;
    }
    
    public void setId(StageMasterId id) {
        this.id = id;
    }
   








}