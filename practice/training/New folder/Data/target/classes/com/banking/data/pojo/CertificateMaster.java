package com.banking.data.pojo;
// default package



/**
 * CertificateMaster entity. @author MyEclipse Persistence Tools
 */

public class CertificateMaster  implements java.io.Serializable {


    // Fields    

     private CertificateMasterId id;


    // Constructors

    /** default constructor */
    public CertificateMaster() {
    }

    
    /** full constructor */
    public CertificateMaster(CertificateMasterId id) {
        this.id = id;
    }

   
    // Property accessors

    public CertificateMasterId getId() {
        return this.id;
    }
    
    public void setId(CertificateMasterId id) {
        this.id = id;
    }
   








}