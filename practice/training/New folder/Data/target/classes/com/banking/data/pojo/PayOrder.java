package com.banking.data.pojo;
// default package



/**
 * PayOrder entity. @author MyEclipse Persistence Tools
 */

public class PayOrder  implements java.io.Serializable {


    // Fields    

     private Integer payordNo;
     private String payordDt;
     private String cdInd;
     private String payeeNm;
     private Double trnAmt;
     private String poCancel;
     private String poStop;
     private Integer poPrtInd;
     private Integer poCshInd;
     private Integer poChqNo;
     private String validUpto;
     private String poCshDt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer poEncshRefNo;


    // Constructors

    /** default constructor */
    public PayOrder() {
    }

    
    /** full constructor */
    public PayOrder(String payordDt, String cdInd, String payeeNm, Double trnAmt, String poCancel, String poStop, Integer poPrtInd, Integer poCshInd, Integer poChqNo, String validUpto, String poCshDt, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer poEncshRefNo) {
        this.payordDt = payordDt;
        this.cdInd = cdInd;
        this.payeeNm = payeeNm;
        this.trnAmt = trnAmt;
        this.poCancel = poCancel;
        this.poStop = poStop;
        this.poPrtInd = poPrtInd;
        this.poCshInd = poCshInd;
        this.poChqNo = poChqNo;
        this.validUpto = validUpto;
        this.poCshDt = poCshDt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.poEncshRefNo = poEncshRefNo;
    }

   
    // Property accessors

    public Integer getPayordNo() {
        return this.payordNo;
    }
    
    public void setPayordNo(Integer payordNo) {
        this.payordNo = payordNo;
    }

    public String getPayordDt() {
        return this.payordDt;
    }
    
    public void setPayordDt(String payordDt) {
        this.payordDt = payordDt;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getPayeeNm() {
        return this.payeeNm;
    }
    
    public void setPayeeNm(String payeeNm) {
        this.payeeNm = payeeNm;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public String getPoCancel() {
        return this.poCancel;
    }
    
    public void setPoCancel(String poCancel) {
        this.poCancel = poCancel;
    }

    public String getPoStop() {
        return this.poStop;
    }
    
    public void setPoStop(String poStop) {
        this.poStop = poStop;
    }

    public Integer getPoPrtInd() {
        return this.poPrtInd;
    }
    
    public void setPoPrtInd(Integer poPrtInd) {
        this.poPrtInd = poPrtInd;
    }

    public Integer getPoCshInd() {
        return this.poCshInd;
    }
    
    public void setPoCshInd(Integer poCshInd) {
        this.poCshInd = poCshInd;
    }

    public Integer getPoChqNo() {
        return this.poChqNo;
    }
    
    public void setPoChqNo(Integer poChqNo) {
        this.poChqNo = poChqNo;
    }

    public String getValidUpto() {
        return this.validUpto;
    }
    
    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public String getPoCshDt() {
        return this.poCshDt;
    }
    
    public void setPoCshDt(String poCshDt) {
        this.poCshDt = poCshDt;
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

    public Integer getPoEncshRefNo() {
        return this.poEncshRefNo;
    }
    
    public void setPoEncshRefNo(Integer poEncshRefNo) {
        this.poEncshRefNo = poEncshRefNo;
    }
   








}