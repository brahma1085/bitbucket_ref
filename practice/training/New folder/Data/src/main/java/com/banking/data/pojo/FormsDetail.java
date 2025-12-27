package com.banking.data.pojo;
// default package



/**
 * FormsDetail entity. @author MyEclipse Persistence Tools
 */

public class FormsDetail  implements java.io.Serializable {


    // Fields    

     private FormsDetailId id;


    // Constructors

    /** default constructor */
    public FormsDetail() {
    }

    
    /** full constructor */
    public FormsDetail(FormsDetailId id) {
        this.id = id;
    }

   
    // Property accessors

    public FormsDetailId getId() {
        return this.id;
    }
    
    public void setId(FormsDetailId id) {
        this.id = id;
    }
   








}