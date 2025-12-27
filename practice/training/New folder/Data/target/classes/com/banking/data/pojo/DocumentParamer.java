package com.banking.data.pojo;
// default package



/**
 * DocumentParamer entity. @author MyEclipse Persistence Tools
 */

public class DocumentParamer  implements java.io.Serializable {


    // Fields    

     private DocumentParamerId id;


    // Constructors

    /** default constructor */
    public DocumentParamer() {
    }

    
    /** full constructor */
    public DocumentParamer(DocumentParamerId id) {
        this.id = id;
    }

   
    // Property accessors

    public DocumentParamerId getId() {
        return this.id;
    }
    
    public void setId(DocumentParamerId id) {
        this.id = id;
    }
   








}