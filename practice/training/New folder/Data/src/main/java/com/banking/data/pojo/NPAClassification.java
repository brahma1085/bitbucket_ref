package com.banking.data.pojo;
// default package



/**
 * NPAClassification entity. @author MyEclipse Persistence Tools
 */

public class NPAClassification  implements java.io.Serializable {


    // Fields    

     private NPAClassificationId id;


    // Constructors

    /** default constructor */
    public NPAClassification() {
    }

    
    /** full constructor */
    public NPAClassification(NPAClassificationId id) {
        this.id = id;
    }

   
    // Property accessors

    public NPAClassificationId getId() {
        return this.id;
    }
    
    public void setId(NPAClassificationId id) {
        this.id = id;
    }
   








}