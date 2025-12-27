package com.banking.data.pojo;
// default package



/**
 * Narration entity. @author MyEclipse Persistence Tools
 */

public class Narration  implements java.io.Serializable {


    // Fields    

     private NarrationId id;


    // Constructors

    /** default constructor */
    public Narration() {
    }

    
    /** full constructor */
    public Narration(NarrationId id) {
        this.id = id;
    }

   
    // Property accessors

    public NarrationId getId() {
        return this.id;
    }
    
    public void setId(NarrationId id) {
        this.id = id;
    }
   








}