package com.banking.data.pojo;
// default package



/**
 * ShareParameter entity. @author MyEclipse Persistence Tools
 */

public class ShareParameter  implements java.io.Serializable {


    // Fields    

     private ShareParameterId id;
     private String prmDesc;
     private Double prmAmt;
     private String prmFreq;
     private String prmTy;
     private String prmGlCode;
     private String prmGlType;


    // Constructors

    /** default constructor */
    public ShareParameter() {
    }

	/** minimal constructor */
    public ShareParameter(ShareParameterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public ShareParameter(ShareParameterId id, String prmDesc, Double prmAmt, String prmFreq, String prmTy, String prmGlCode, String prmGlType) {
        this.id = id;
        this.prmDesc = prmDesc;
        this.prmAmt = prmAmt;
        this.prmFreq = prmFreq;
        this.prmTy = prmTy;
        this.prmGlCode = prmGlCode;
        this.prmGlType = prmGlType;
    }

   
    // Property accessors

    public ShareParameterId getId() {
        return this.id;
    }
    
    public void setId(ShareParameterId id) {
        this.id = id;
    }

    public String getPrmDesc() {
        return this.prmDesc;
    }
    
    public void setPrmDesc(String prmDesc) {
        this.prmDesc = prmDesc;
    }

    public Double getPrmAmt() {
        return this.prmAmt;
    }
    
    public void setPrmAmt(Double prmAmt) {
        this.prmAmt = prmAmt;
    }

    public String getPrmFreq() {
        return this.prmFreq;
    }
    
    public void setPrmFreq(String prmFreq) {
        this.prmFreq = prmFreq;
    }

    public String getPrmTy() {
        return this.prmTy;
    }
    
    public void setPrmTy(String prmTy) {
        this.prmTy = prmTy;
    }

    public String getPrmGlCode() {
        return this.prmGlCode;
    }
    
    public void setPrmGlCode(String prmGlCode) {
        this.prmGlCode = prmGlCode;
    }

    public String getPrmGlType() {
        return this.prmGlType;
    }
    
    public void setPrmGlType(String prmGlType) {
        this.prmGlType = prmGlType;
    }
   








}