package com.banking.data.pojo;
// default package



/**
 * ProductsLog entity. @author MyEclipse Persistence Tools
 */

public class ProductsLog  implements java.io.Serializable {


    // Fields    

     private ProductsLogId id;


    // Constructors

    /** default constructor */
    public ProductsLog() {
    }

    
    /** full constructor */
    public ProductsLog(ProductsLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public ProductsLogId getId() {
        return this.id;
    }
    
    public void setId(ProductsLogId id) {
        this.id = id;
    }
   








}