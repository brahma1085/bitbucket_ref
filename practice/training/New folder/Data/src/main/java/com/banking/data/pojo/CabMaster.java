package com.banking.data.pojo;
// default package



/**
 * CabMaster entity. @author MyEclipse Persistence Tools
 */

public class CabMaster  implements java.io.Serializable {


    // Fields    

     private Integer cabNo;
     private String descrptn;
     private String masterKey;
     private Integer noOfLkrs;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public CabMaster() {
    }

    
    /** full constructor */
    public CabMaster(String descrptn, String masterKey, Integer noOfLkrs, String deUser, String deTml, String deDate) {
        this.descrptn = descrptn;
        this.masterKey = masterKey;
        this.noOfLkrs = noOfLkrs;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getCabNo() {
        return this.cabNo;
    }
    
    public void setCabNo(Integer cabNo) {
        this.cabNo = cabNo;
    }

    public String getDescrptn() {
        return this.descrptn;
    }
    
    public void setDescrptn(String descrptn) {
        this.descrptn = descrptn;
    }

    public String getMasterKey() {
        return this.masterKey;
    }
    
    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public Integer getNoOfLkrs() {
        return this.noOfLkrs;
    }
    
    public void setNoOfLkrs(Integer noOfLkrs) {
        this.noOfLkrs = noOfLkrs;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }
   








}