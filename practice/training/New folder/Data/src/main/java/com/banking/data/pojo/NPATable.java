package com.banking.data.pojo;
// default package



/**
 * NPATable entity. @author MyEclipse Persistence Tools
 */

public class NPATable  implements java.io.Serializable {


    // Fields    

     private NPATableId id;


    // Constructors

    /** default constructor */
    public NPATable() {
    }

    
    /** full constructor */
    public NPATable(NPATableId id) {
        this.id = id;
    }

   
    // Property accessors

    public NPATableId getId() {
        return this.id;
    }
    
    public void setId(NPATableId id) {
        this.id = id;
    }
   








}