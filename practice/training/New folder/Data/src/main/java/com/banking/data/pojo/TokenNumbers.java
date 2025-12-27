package com.banking.data.pojo;
// default package



/**
 * TokenNumbers entity. @author MyEclipse Persistence Tools
 */

public class TokenNumbers  implements java.io.Serializable {


    // Fields    

     private TokenNumbersId id;


    // Constructors

    /** default constructor */
    public TokenNumbers() {
    }

    
    /** full constructor */
    public TokenNumbers(TokenNumbersId id) {
        this.id = id;
    }

   
    // Property accessors

    public TokenNumbersId getId() {
        return this.id;
    }
    
    public void setId(TokenNumbersId id) {
        this.id = id;
    }
   








}