package com.banking.data.pojo;
// default package



/**
 * Lockers entity. @author MyEclipse Persistence Tools
 */

public class Lockers  implements java.io.Serializable {


    // Fields    

     private Integer lockerNo;
     private Integer cabNo;
     private Integer rowNo;
     private Integer colNo;
     private String lockerTy;
     private Integer keyNo;
     private String lockerSt;
     private String siezeInd;
     private String descptn;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public Lockers() {
    }

    
    /** full constructor */
    public Lockers(Integer cabNo, Integer rowNo, Integer colNo, String lockerTy, Integer keyNo, String lockerSt, String siezeInd, String descptn, String deUser, String deTml, String deDate) {
        this.cabNo = cabNo;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.lockerTy = lockerTy;
        this.keyNo = keyNo;
        this.lockerSt = lockerSt;
        this.siezeInd = siezeInd;
        this.descptn = descptn;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getLockerNo() {
        return this.lockerNo;
    }
    
    public void setLockerNo(Integer lockerNo) {
        this.lockerNo = lockerNo;
    }

    public Integer getCabNo() {
        return this.cabNo;
    }
    
    public void setCabNo(Integer cabNo) {
        this.cabNo = cabNo;
    }

    public Integer getRowNo() {
        return this.rowNo;
    }
    
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public Integer getColNo() {
        return this.colNo;
    }
    
    public void setColNo(Integer colNo) {
        this.colNo = colNo;
    }

    public String getLockerTy() {
        return this.lockerTy;
    }
    
    public void setLockerTy(String lockerTy) {
        this.lockerTy = lockerTy;
    }

    public Integer getKeyNo() {
        return this.keyNo;
    }
    
    public void setKeyNo(Integer keyNo) {
        this.keyNo = keyNo;
    }

    public String getLockerSt() {
        return this.lockerSt;
    }
    
    public void setLockerSt(String lockerSt) {
        this.lockerSt = lockerSt;
    }

    public String getSiezeInd() {
        return this.siezeInd;
    }
    
    public void setSiezeInd(String siezeInd) {
        this.siezeInd = siezeInd;
    }

    public String getDescptn() {
        return this.descptn;
    }
    
    public void setDescptn(String descptn) {
        this.descptn = descptn;
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