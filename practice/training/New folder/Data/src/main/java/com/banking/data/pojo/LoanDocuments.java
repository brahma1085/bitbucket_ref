package com.banking.data.pojo;
// default package



/**
 * LoanDocuments entity. @author MyEclipse Persistence Tools
 */

public class LoanDocuments  implements java.io.Serializable {


    // Fields    

     private LoanDocumentsId id;


    // Constructors

    /** default constructor */
    public LoanDocuments() {
    }

    
    /** full constructor */
    public LoanDocuments(LoanDocumentsId id) {
        this.id = id;
    }

   
    // Property accessors

    public LoanDocumentsId getId() {
        return this.id;
    }
    
    public void setId(LoanDocumentsId id) {
        this.id = id;
    }
   








}