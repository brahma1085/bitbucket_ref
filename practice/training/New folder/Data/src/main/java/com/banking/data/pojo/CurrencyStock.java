package com.banking.data.pojo;
// default package



/**
 * CurrencyStock entity. @author MyEclipse Persistence Tools
 */

public class CurrencyStock  implements java.io.Serializable {


    // Fields    

     private CurrencyStockId id;


    // Constructors

    /** default constructor */
    public CurrencyStock() {
    }

    
    /** full constructor */
    public CurrencyStock(CurrencyStockId id) {
        this.id = id;
    }

   
    // Property accessors

    public CurrencyStockId getId() {
        return this.id;
    }
    
    public void setId(CurrencyStockId id) {
        this.id = id;
    }
   








}