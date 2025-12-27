package com.banking.data.pojo;
// default package



/**
 * LockerType entity. @author MyEclipse Persistence Tools
 */

public class LockerType  implements java.io.Serializable {


    // Fields    

     private String lockerType;
     private String descrptn;
     private String height;
     private String length;
     private String depth;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public LockerType() {
    }

    
    /** full constructor */
    public LockerType(String descrptn, String height, String length, String depth, String deUser, String deTml, String deDate) {
        this.descrptn = descrptn;
        this.height = height;
        this.length = length;
        this.depth = depth;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getLockerType() {
        return this.lockerType;
    }
    
    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public String getDescrptn() {
        return this.descrptn;
    }
    
    public void setDescrptn(String descrptn) {
        this.descrptn = descrptn;
    }

    public String getHeight() {
        return this.height;
    }
    
    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return this.length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }

    public String getDepth() {
        return this.depth;
    }
    
    public void setDepth(String depth) {
        this.depth = depth;
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