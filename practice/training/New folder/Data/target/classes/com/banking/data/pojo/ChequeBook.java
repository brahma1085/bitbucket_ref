package com.banking.data.pojo;
// default package



/**
 * ChequeBook entity. @author MyEclipse Persistence Tools
 */

public class ChequeBook  implements java.io.Serializable {


    // Fields    

     private ChequeBookId id;


    // Constructors

    /** default constructor */
    public ChequeBook() {
    }

    
    /** full constructor */
    public ChequeBook(ChequeBookId id) {
        this.id = id;
    }

   
    // Property accessors

    public ChequeBookId getId() {
        return this.id;
    }
    
    public void setId(ChequeBookId id) {
        this.id = id;
    }
   








}