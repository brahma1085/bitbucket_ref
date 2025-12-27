package com.banking.data.pojo;
// default package



/**
 * AddressTypes entity. @author MyEclipse Persistence Tools
 */

public class AddressTypes  implements java.io.Serializable {


    // Fields    

     private AddressTypesId id;


    // Constructors

    /** default constructor */
    public AddressTypes() {
    }

    
    /** full constructor */
    public AddressTypes(AddressTypesId id) {
        this.id = id;
    }

   
    // Property accessors

    public AddressTypesId getId() {
        return this.id;
    }
    
    public void setId(AddressTypesId id) {
        this.id = id;
    }
   








}