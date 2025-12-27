package com.banking.data.pojo;
// default package



/**
 * StockInspectionDetails entity. @author MyEclipse Persistence Tools
 */

public class StockInspectionDetails  implements java.io.Serializable {


    // Fields    

     private StockInspectionDetailsId id;


    // Constructors

    /** default constructor */
    public StockInspectionDetails() {
    }

    
    /** full constructor */
    public StockInspectionDetails(StockInspectionDetailsId id) {
        this.id = id;
    }

   
    // Property accessors

    public StockInspectionDetailsId getId() {
        return this.id;
    }
    
    public void setId(StockInspectionDetailsId id) {
        this.id = id;
    }
   








}