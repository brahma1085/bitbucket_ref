package com.banking.data.pojo;
// default package



/**
 * Template entity. @author MyEclipse Persistence Tools
 */

public class Template  implements java.io.Serializable {


    // Fields    

     private TemplateId id;


    // Constructors

    /** default constructor */
    public Template() {
    }

    
    /** full constructor */
    public Template(TemplateId id) {
        this.id = id;
    }

   
    // Property accessors

    public TemplateId getId() {
        return this.id;
    }
    
    public void setId(TemplateId id) {
        this.id = id;
    }
   








}