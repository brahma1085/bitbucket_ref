package com.banking.data.pojo;
// default package



/**
 * StdInstParam entity. @author MyEclipse Persistence Tools
 */

public class StdInstParam  implements java.io.Serializable {


    // Fields    

     private StdInstParamId id;


    // Constructors

    /** default constructor */
    public StdInstParam() {
    }

    
    /** full constructor */
    public StdInstParam(StdInstParamId id) {
        this.id = id;
    }

   
    // Property accessors

    public StdInstParamId getId() {
        return this.id;
    }
    
    public void setId(StdInstParamId id) {
        this.id = id;
    }
   








}