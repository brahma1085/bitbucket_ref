package com.banking.data.pojo;
// default package



/**
 * StdInst entity. @author MyEclipse Persistence Tools
 */

public class StdInst  implements java.io.Serializable {


    // Fields    

     private Integer siNo;
     private Integer priNo;
     private String frAcTy;
     private Integer frAcNo;
     private String toAcTy;
     private Integer toAcNo;
     private String dueDt;
     private Integer prdMths;
     private Integer prdDays;
     private Integer lnOpt;
     private Double amount;
     private Double amtAdj;
     private String lastDate;
     private Integer noofTime;
     private Integer execTime;
     private Integer expiryDays;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private String veTml;
     private String veUser;
     private String veDtTime;
     private String delInd;
     private String altDeTml;
     private String altDeUser;
     private String altDeDtTime;
     private String altVeTml;
     private String altVeUser;
     private String altVeDtTime;


    // Constructors

    /** default constructor */
    public StdInst() {
    }

    
    /** full constructor */
    public StdInst(Integer priNo, String frAcTy, Integer frAcNo, String toAcTy, Integer toAcNo, String dueDt, Integer prdMths, Integer prdDays, Integer lnOpt, Double amount, Double amtAdj, String lastDate, Integer noofTime, Integer execTime, Integer expiryDays, String deTml, String deUser, String deDtTime, String veTml, String veUser, String veDtTime, String delInd, String altDeTml, String altDeUser, String altDeDtTime, String altVeTml, String altVeUser, String altVeDtTime) {
        this.priNo = priNo;
        this.frAcTy = frAcTy;
        this.frAcNo = frAcNo;
        this.toAcTy = toAcTy;
        this.toAcNo = toAcNo;
        this.dueDt = dueDt;
        this.prdMths = prdMths;
        this.prdDays = prdDays;
        this.lnOpt = lnOpt;
        this.amount = amount;
        this.amtAdj = amtAdj;
        this.lastDate = lastDate;
        this.noofTime = noofTime;
        this.execTime = execTime;
        this.expiryDays = expiryDays;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDtTime = veDtTime;
        this.delInd = delInd;
        this.altDeTml = altDeTml;
        this.altDeUser = altDeUser;
        this.altDeDtTime = altDeDtTime;
        this.altVeTml = altVeTml;
        this.altVeUser = altVeUser;
        this.altVeDtTime = altVeDtTime;
    }

   
    // Property accessors

    public Integer getSiNo() {
        return this.siNo;
    }
    
    public void setSiNo(Integer siNo) {
        this.siNo = siNo;
    }

    public Integer getPriNo() {
        return this.priNo;
    }
    
    public void setPriNo(Integer priNo) {
        this.priNo = priNo;
    }

    public String getFrAcTy() {
        return this.frAcTy;
    }
    
    public void setFrAcTy(String frAcTy) {
        this.frAcTy = frAcTy;
    }

    public Integer getFrAcNo() {
        return this.frAcNo;
    }
    
    public void setFrAcNo(Integer frAcNo) {
        this.frAcNo = frAcNo;
    }

    public String getToAcTy() {
        return this.toAcTy;
    }
    
    public void setToAcTy(String toAcTy) {
        this.toAcTy = toAcTy;
    }

    public Integer getToAcNo() {
        return this.toAcNo;
    }
    
    public void setToAcNo(Integer toAcNo) {
        this.toAcNo = toAcNo;
    }

    public String getDueDt() {
        return this.dueDt;
    }
    
    public void setDueDt(String dueDt) {
        this.dueDt = dueDt;
    }

    public Integer getPrdMths() {
        return this.prdMths;
    }
    
    public void setPrdMths(Integer prdMths) {
        this.prdMths = prdMths;
    }

    public Integer getPrdDays() {
        return this.prdDays;
    }
    
    public void setPrdDays(Integer prdDays) {
        this.prdDays = prdDays;
    }

    public Integer getLnOpt() {
        return this.lnOpt;
    }
    
    public void setLnOpt(Integer lnOpt) {
        this.lnOpt = lnOpt;
    }

    public Double getAmount() {
        return this.amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmtAdj() {
        return this.amtAdj;
    }
    
    public void setAmtAdj(Double amtAdj) {
        this.amtAdj = amtAdj;
    }

    public String getLastDate() {
        return this.lastDate;
    }
    
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public Integer getNoofTime() {
        return this.noofTime;
    }
    
    public void setNoofTime(Integer noofTime) {
        this.noofTime = noofTime;
    }

    public Integer getExecTime() {
        return this.execTime;
    }
    
    public void setExecTime(Integer execTime) {
        this.execTime = execTime;
    }

    public Integer getExpiryDays() {
        return this.expiryDays;
    }
    
    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
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

    public String getVeDtTime() {
        return this.veDtTime;
    }
    
    public void setVeDtTime(String veDtTime) {
        this.veDtTime = veDtTime;
    }

    public String getDelInd() {
        return this.delInd;
    }
    
    public void setDelInd(String delInd) {
        this.delInd = delInd;
    }

    public String getAltDeTml() {
        return this.altDeTml;
    }
    
    public void setAltDeTml(String altDeTml) {
        this.altDeTml = altDeTml;
    }

    public String getAltDeUser() {
        return this.altDeUser;
    }
    
    public void setAltDeUser(String altDeUser) {
        this.altDeUser = altDeUser;
    }

    public String getAltDeDtTime() {
        return this.altDeDtTime;
    }
    
    public void setAltDeDtTime(String altDeDtTime) {
        this.altDeDtTime = altDeDtTime;
    }

    public String getAltVeTml() {
        return this.altVeTml;
    }
    
    public void setAltVeTml(String altVeTml) {
        this.altVeTml = altVeTml;
    }

    public String getAltVeUser() {
        return this.altVeUser;
    }
    
    public void setAltVeUser(String altVeUser) {
        this.altVeUser = altVeUser;
    }

    public String getAltVeDtTime() {
        return this.altVeDtTime;
    }
    
    public void setAltVeDtTime(String altVeDtTime) {
        this.altVeDtTime = altVeDtTime;
    }
   








}