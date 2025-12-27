package com.banking.data.pojo;
// default package



/**
 * StdInstDone entity. @author MyEclipse Persistence Tools
 */

public class StdInstDone  implements java.io.Serializable {


    // Fields    

     private StdInstDoneId id;


    // Constructors

    /** default constructor */
    public StdInstDone() {
    }

    
    /** full constructor */
    public StdInstDone(StdInstDoneId id) {
        this.id = id;
    }

   
    // Property accessors

    public StdInstDoneId getId() {
        return this.id;
    }
    
    public void setId(StdInstDoneId id) {
        this.id = id;
    }
   








}