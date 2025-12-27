package com.banking.data.pojo;
// default package



/**
 * Products entity. @author MyEclipse Persistence Tools
 */

public class Products  implements java.io.Serializable {


    // Fields    

     private ProductsId id;


    // Constructors

    /** default constructor */
    public Products() {
    }

    
    /** full constructor */
    public Products(ProductsId id) {
        this.id = id;
    }

   
    // Property accessors

    public ProductsId getId() {
        return this.id;
    }
    
    public void setId(ProductsId id) {
        this.id = id;
    }
   








}