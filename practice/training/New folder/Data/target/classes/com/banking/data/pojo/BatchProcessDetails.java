package com.banking.data.pojo;
// default package



/**
 * BatchProcessDetails entity. @author MyEclipse Persistence Tools
 */

public class BatchProcessDetails  implements java.io.Serializable {


    // Fields    

     private BatchProcessDetailsId id;


    // Constructors

    /** default constructor */
    public BatchProcessDetails() {
    }

    
    /** full constructor */
    public BatchProcessDetails(BatchProcessDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public BatchProcessDetailsId getId() {
        return this.id;
    }
    
    public void setId(BatchProcessDetailsId id) {
        this.id = id;
    }
   








}