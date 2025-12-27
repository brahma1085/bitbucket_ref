package com.banking.data.pojo;
// default package



/**
 * Country entity. @author MyEclipse Persistence Tools
 */

public class Country  implements java.io.Serializable {


    // Fields    

     private CountryId id;


    // Constructors

    /** default constructor */
    public Country() {
    }

    
    /** full constructor */
    public Country(CountryId id) {
        this.id = id;
    }

   
    // Property accessors

    public CountryId getId() {
        return this.id;
    }
    
    public void setId(CountryId id) {
        this.id = id;
    }
   








}