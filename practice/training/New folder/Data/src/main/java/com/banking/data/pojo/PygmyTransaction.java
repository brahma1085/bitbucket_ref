package com.banking.data.pojo;
// default package



/**
 * PygmyTransaction entity. @author MyEclipse Persistence Tools
 */

public class PygmyTransaction  implements java.io.Serializable {


    // Fields    

     private PygmyTransactionId id;


    // Constructors

    /** default constructor */
    public PygmyTransaction() {
    }

    
    /** full constructor */
    public PygmyTransaction(PygmyTransactionId id) {
        this.id = id;
    }

   
    // Property accessors

    public PygmyTransactionId getId() {
        return this.id;
    }
    
    public void setId(PygmyTransactionId id) {
        this.id = id;
    }
   








}