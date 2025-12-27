package com.banking.data.pojo;
// default package



/**
 * TerminalPAddress entity. @author MyEclipse Persistence Tools
 */

public class TerminalPAddress  implements java.io.Serializable {


    // Fields    

     private TerminalPAddressId id;


    // Constructors

    /** default constructor */
    public TerminalPAddress() {
    }

    
    /** full constructor */
    public TerminalPAddress(TerminalPAddressId id) {
        this.id = id;
    }

   
    // Property accessors

    public TerminalPAddressId getId() {
        return this.id;
    }
    
    public void setId(TerminalPAddressId id) {
        this.id = id;
    }
   








}