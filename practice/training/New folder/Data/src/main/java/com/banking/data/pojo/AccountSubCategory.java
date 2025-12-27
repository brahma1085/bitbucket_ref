package com.banking.data.pojo;
// default package



/**
 * AccountSubCategory entity. @author MyEclipse Persistence Tools
 */

public class AccountSubCategory  implements java.io.Serializable {


    // Fields    

     private AccountSubCategoryId id;


    // Constructors

    /** default constructor */
    public AccountSubCategory() {
    }

    
    /** full constructor */
    public AccountSubCategory(AccountSubCategoryId id) {
        this.id = id;
    }

   
    // Property accessors

    public AccountSubCategoryId getId() {
        return this.id;
    }
    
    public void setId(AccountSubCategoryId id) {
        this.id = id;
    }
   








}