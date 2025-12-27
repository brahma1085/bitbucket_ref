package com.banking.data.pojo;
// default package



/**
 * Acknowledge entity. @author MyEclipse Persistence Tools
 */

public class Acknowledge  implements java.io.Serializable {


    // Fields    

     private Integer ackNo;
     private String ackDate;
     private Integer docSou;
     private String clgType;
     private Double totAmt;
     private String reconciled;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public Acknowledge() {
    }

    
    /** full constructor */
    public Acknowledge(String ackDate, Integer docSou, String clgType, Double totAmt, String reconciled, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.ackDate = ackDate;
        this.docSou = docSou;
        this.clgType = clgType;
        this.totAmt = totAmt;
        this.reconciled = reconciled;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public Integer getAckNo() {
        return this.ackNo;
    }
    
    public void setAckNo(Integer ackNo) {
        this.ackNo = ackNo;
    }

    public String getAckDate() {
        return this.ackDate;
    }
    
    public void setAckDate(String ackDate) {
        this.ackDate = ackDate;
    }

    public Integer getDocSou() {
        return this.docSou;
    }
    
    public void setDocSou(Integer docSou) {
        this.docSou = docSou;
    }

    public String getClgType() {
        return this.clgType;
    }
    
    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public Double getTotAmt() {
        return this.totAmt;
    }
    
    public void setTotAmt(Double totAmt) {
        this.totAmt = totAmt;
    }

    public String getReconciled() {
        return this.reconciled;
    }
    
    public void setReconciled(String reconciled) {
        this.reconciled = reconciled;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }
   








}