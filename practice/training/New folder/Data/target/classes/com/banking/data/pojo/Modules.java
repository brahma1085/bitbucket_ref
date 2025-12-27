package com.banking.data.pojo;
// default package



/**
 * Modules entity. @author MyEclipse Persistence Tools
 */

public class Modules  implements java.io.Serializable {


    // Fields    

     private Integer modulecode;
     private String moduledesc;
     private String moduleabbr;
     private Integer lstAccNo;
     private Integer maxRenewalCount;
     private Double minBal;
     private Integer minPeriod;
     private String otherProp;
     private Double lnkShares;
     private Double maxAmt;
     private Integer intfromDay;
     private Integer inttoDay;
     private Integer trnsPerMnth;
     private String lstIntdt;
     private Double intRate;
     private Integer stdInst;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private Short maxRenewalDays;
     private Double renewalIntRate;
     private Double penaltyRate;
     private Integer passBkLines;
     private Double commRateForAmt;
     private Double maxCommRate;
     private Integer lstVoucherScrollNo;
     private Integer chqValidityPeriod;
     private Double minAmtCheque;
     private Double minAmtClg;
     private Double maxAmtCheque;
     private Double maxAmtClg;
     private Integer topMargin;
     private Integer linesPerPage;
     private Integer bottomMargin;
     private Integer lastTrfScrollNo;
     private Integer lstRctNo;
     private Integer lstVoucherNo;
     private Integer inspectionPeriod;
     private Integer lnModulecode;


    // Constructors

    /** default constructor */
    public Modules() {
    }

    
    /** full constructor */
    public Modules(String moduledesc, String moduleabbr, Integer lstAccNo, Integer maxRenewalCount, Double minBal, Integer minPeriod, String otherProp, Double lnkShares, Double maxAmt, Integer intfromDay, Integer inttoDay, Integer trnsPerMnth, String lstIntdt, Double intRate, Integer stdInst, String deTml, String deUser, String deDtTime, Short maxRenewalDays, Double renewalIntRate, Double penaltyRate, Integer passBkLines, Double commRateForAmt, Double maxCommRate, Integer lstVoucherScrollNo, Integer chqValidityPeriod, Double minAmtCheque, Double minAmtClg, Double maxAmtCheque, Double maxAmtClg, Integer topMargin, Integer linesPerPage, Integer bottomMargin, Integer lastTrfScrollNo, Integer lstRctNo, Integer lstVoucherNo, Integer inspectionPeriod, Integer lnModulecode) {
        this.moduledesc = moduledesc;
        this.moduleabbr = moduleabbr;
        this.lstAccNo = lstAccNo;
        this.maxRenewalCount = maxRenewalCount;
        this.minBal = minBal;
        this.minPeriod = minPeriod;
        this.otherProp = otherProp;
        this.lnkShares = lnkShares;
        this.maxAmt = maxAmt;
        this.intfromDay = intfromDay;
        this.inttoDay = inttoDay;
        this.trnsPerMnth = trnsPerMnth;
        this.lstIntdt = lstIntdt;
        this.intRate = intRate;
        this.stdInst = stdInst;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.maxRenewalDays = maxRenewalDays;
        this.renewalIntRate = renewalIntRate;
        this.penaltyRate = penaltyRate;
        this.passBkLines = passBkLines;
        this.commRateForAmt = commRateForAmt;
        this.maxCommRate = maxCommRate;
        this.lstVoucherScrollNo = lstVoucherScrollNo;
        this.chqValidityPeriod = chqValidityPeriod;
        this.minAmtCheque = minAmtCheque;
        this.minAmtClg = minAmtClg;
        this.maxAmtCheque = maxAmtCheque;
        this.maxAmtClg = maxAmtClg;
        this.topMargin = topMargin;
        this.linesPerPage = linesPerPage;
        this.bottomMargin = bottomMargin;
        this.lastTrfScrollNo = lastTrfScrollNo;
        this.lstRctNo = lstRctNo;
        this.lstVoucherNo = lstVoucherNo;
        this.inspectionPeriod = inspectionPeriod;
        this.lnModulecode = lnModulecode;
    }

   
    // Property accessors

    public Integer getModulecode() {
        return this.modulecode;
    }
    
    public void setModulecode(Integer modulecode) {
        this.modulecode = modulecode;
    }

    public String getModuledesc() {
        return this.moduledesc;
    }
    
    public void setModuledesc(String moduledesc) {
        this.moduledesc = moduledesc;
    }

    public String getModuleabbr() {
        return this.moduleabbr;
    }
    
    public void setModuleabbr(String moduleabbr) {
        this.moduleabbr = moduleabbr;
    }

    public Integer getLstAccNo() {
        return this.lstAccNo;
    }
    
    public void setLstAccNo(Integer lstAccNo) {
        this.lstAccNo = lstAccNo;
    }

    public Integer getMaxRenewalCount() {
        return this.maxRenewalCount;
    }
    
    public void setMaxRenewalCount(Integer maxRenewalCount) {
        this.maxRenewalCount = maxRenewalCount;
    }

    public Double getMinBal() {
        return this.minBal;
    }
    
    public void setMinBal(Double minBal) {
        this.minBal = minBal;
    }

    public Integer getMinPeriod() {
        return this.minPeriod;
    }
    
    public void setMinPeriod(Integer minPeriod) {
        this.minPeriod = minPeriod;
    }

    public String getOtherProp() {
        return this.otherProp;
    }
    
    public void setOtherProp(String otherProp) {
        this.otherProp = otherProp;
    }

    public Double getLnkShares() {
        return this.lnkShares;
    }
    
    public void setLnkShares(Double lnkShares) {
        this.lnkShares = lnkShares;
    }

    public Double getMaxAmt() {
        return this.maxAmt;
    }
    
    public void setMaxAmt(Double maxAmt) {
        this.maxAmt = maxAmt;
    }

    public Integer getIntfromDay() {
        return this.intfromDay;
    }
    
    public void setIntfromDay(Integer intfromDay) {
        this.intfromDay = intfromDay;
    }

    public Integer getInttoDay() {
        return this.inttoDay;
    }
    
    public void setInttoDay(Integer inttoDay) {
        this.inttoDay = inttoDay;
    }

    public Integer getTrnsPerMnth() {
        return this.trnsPerMnth;
    }
    
    public void setTrnsPerMnth(Integer trnsPerMnth) {
        this.trnsPerMnth = trnsPerMnth;
    }

    public String getLstIntdt() {
        return this.lstIntdt;
    }
    
    public void setLstIntdt(String lstIntdt) {
        this.lstIntdt = lstIntdt;
    }

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Integer getStdInst() {
        return this.stdInst;
    }
    
    public void setStdInst(Integer stdInst) {
        this.stdInst = stdInst;
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

    public Short getMaxRenewalDays() {
        return this.maxRenewalDays;
    }
    
    public void setMaxRenewalDays(Short maxRenewalDays) {
        this.maxRenewalDays = maxRenewalDays;
    }

    public Double getRenewalIntRate() {
        return this.renewalIntRate;
    }
    
    public void setRenewalIntRate(Double renewalIntRate) {
        this.renewalIntRate = renewalIntRate;
    }

    public Double getPenaltyRate() {
        return this.penaltyRate;
    }
    
    public void setPenaltyRate(Double penaltyRate) {
        this.penaltyRate = penaltyRate;
    }

    public Integer getPassBkLines() {
        return this.passBkLines;
    }
    
    public void setPassBkLines(Integer passBkLines) {
        this.passBkLines = passBkLines;
    }

    public Double getCommRateForAmt() {
        return this.commRateForAmt;
    }
    
    public void setCommRateForAmt(Double commRateForAmt) {
        this.commRateForAmt = commRateForAmt;
    }

    public Double getMaxCommRate() {
        return this.maxCommRate;
    }
    
    public void setMaxCommRate(Double maxCommRate) {
        this.maxCommRate = maxCommRate;
    }

    public Integer getLstVoucherScrollNo() {
        return this.lstVoucherScrollNo;
    }
    
    public void setLstVoucherScrollNo(Integer lstVoucherScrollNo) {
        this.lstVoucherScrollNo = lstVoucherScrollNo;
    }

    public Integer getChqValidityPeriod() {
        return this.chqValidityPeriod;
    }
    
    public void setChqValidityPeriod(Integer chqValidityPeriod) {
        this.chqValidityPeriod = chqValidityPeriod;
    }

    public Double getMinAmtCheque() {
        return this.minAmtCheque;
    }
    
    public void setMinAmtCheque(Double minAmtCheque) {
        this.minAmtCheque = minAmtCheque;
    }

    public Double getMinAmtClg() {
        return this.minAmtClg;
    }
    
    public void setMinAmtClg(Double minAmtClg) {
        this.minAmtClg = minAmtClg;
    }

    public Double getMaxAmtCheque() {
        return this.maxAmtCheque;
    }
    
    public void setMaxAmtCheque(Double maxAmtCheque) {
        this.maxAmtCheque = maxAmtCheque;
    }

    public Double getMaxAmtClg() {
        return this.maxAmtClg;
    }
    
    public void setMaxAmtClg(Double maxAmtClg) {
        this.maxAmtClg = maxAmtClg;
    }

    public Integer getTopMargin() {
        return this.topMargin;
    }
    
    public void setTopMargin(Integer topMargin) {
        this.topMargin = topMargin;
    }

    public Integer getLinesPerPage() {
        return this.linesPerPage;
    }
    
    public void setLinesPerPage(Integer linesPerPage) {
        this.linesPerPage = linesPerPage;
    }

    public Integer getBottomMargin() {
        return this.bottomMargin;
    }
    
    public void setBottomMargin(Integer bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public Integer getLastTrfScrollNo() {
        return this.lastTrfScrollNo;
    }
    
    public void setLastTrfScrollNo(Integer lastTrfScrollNo) {
        this.lastTrfScrollNo = lastTrfScrollNo;
    }

    public Integer getLstRctNo() {
        return this.lstRctNo;
    }
    
    public void setLstRctNo(Integer lstRctNo) {
        this.lstRctNo = lstRctNo;
    }

    public Integer getLstVoucherNo() {
        return this.lstVoucherNo;
    }
    
    public void setLstVoucherNo(Integer lstVoucherNo) {
        this.lstVoucherNo = lstVoucherNo;
    }

    public Integer getInspectionPeriod() {
        return this.inspectionPeriod;
    }
    
    public void setInspectionPeriod(Integer inspectionPeriod) {
        this.inspectionPeriod = inspectionPeriod;
    }

    public Integer getLnModulecode() {
        return this.lnModulecode;
    }
    
    public void setLnModulecode(Integer lnModulecode) {
        this.lnModulecode = lnModulecode;
    }
   








}