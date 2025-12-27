package com.banking.data.pojo;
// default package



/**
 * DCBReport entity. @author MyEclipse Persistence Tools
 */

public class DCBReport  implements java.io.Serializable {


    // Fields    

     private DCBReportId id;


    // Constructors

    /** default constructor */
    public DCBReport() {
    }

    
    /** full constructor */
    public DCBReport(DCBReportId id) {
        this.id = id;
    }

   
    // Property accessors

    public DCBReportId getId() {
        return this.id;
    }
    
    public void setId(DCBReportId id) {
        this.id = id;
    }
   








}