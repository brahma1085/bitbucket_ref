package com.banking.data.pojo;
// default package



/**
 * LoanEntryIns entity. @author MyEclipse Persistence Tools
 */

public class LoanEntryIns  implements java.io.Serializable {


    // Fields    

     private String modulecode;
     private String moduleabbr;
     private String personal;
     private String relative;
     private String employment;
     private String application;
     private String loanShareDet;
     private String signIns;
     private String property;
     private String coborrowers;
     private String surities;
     private String vehicle;
     private String gold;
     private String od;


    // Constructors

    /** default constructor */
    public LoanEntryIns() {
    }

    
    /** full constructor */
    public LoanEntryIns(String moduleabbr, String personal, String relative, String employment, String application, String loanShareDet, String signIns, String property, String coborrowers, String surities, String vehicle, String gold, String od) {
        this.moduleabbr = moduleabbr;
        this.personal = personal;
        this.relative = relative;
        this.employment = employment;
        this.application = application;
        this.loanShareDet = loanShareDet;
        this.signIns = signIns;
        this.property = property;
        this.coborrowers = coborrowers;
        this.surities = surities;
        this.vehicle = vehicle;
        this.gold = gold;
        this.od = od;
    }

   
    // Property accessors

    public String getModulecode() {
        return this.modulecode;
    }
    
    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    public String getModuleabbr() {
        return this.moduleabbr;
    }
    
    public void setModuleabbr(String moduleabbr) {
        this.moduleabbr = moduleabbr;
    }

    public String getPersonal() {
        return this.personal;
    }
    
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getRelative() {
        return this.relative;
    }
    
    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getEmployment() {
        return this.employment;
    }
    
    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getApplication() {
        return this.application;
    }
    
    public void setApplication(String application) {
        this.application = application;
    }

    public String getLoanShareDet() {
        return this.loanShareDet;
    }
    
    public void setLoanShareDet(String loanShareDet) {
        this.loanShareDet = loanShareDet;
    }

    public String getSignIns() {
        return this.signIns;
    }
    
    public void setSignIns(String signIns) {
        this.signIns = signIns;
    }

    public String getProperty() {
        return this.property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }

    public String getCoborrowers() {
        return this.coborrowers;
    }
    
    public void setCoborrowers(String coborrowers) {
        this.coborrowers = coborrowers;
    }

    public String getSurities() {
        return this.surities;
    }
    
    public void setSurities(String surities) {
        this.surities = surities;
    }

    public String getVehicle() {
        return this.vehicle;
    }
    
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getGold() {
        return this.gold;
    }
    
    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getOd() {
        return this.od;
    }
    
    public void setOd(String od) {
        this.od = od;
    }
   








}