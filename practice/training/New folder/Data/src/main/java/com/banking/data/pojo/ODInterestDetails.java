package com.banking.data.pojo;
// default package



/**
 * ODInterestDetails entity. @author MyEclipse Persistence Tools
 */

public class ODInterestDetails  implements java.io.Serializable {


    // Fields    

     private ODInterestDetailsId id;


    // Constructors

    /** default constructor */
    public ODInterestDetails() {
    }

    
    /** full constructor */
    public ODInterestDetails(ODInterestDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public ODInterestDetailsId getId() {
        return this.id;
    }
    
    public void setId(ODInterestDetailsId id) {
        this.id = id;
    }
   








}