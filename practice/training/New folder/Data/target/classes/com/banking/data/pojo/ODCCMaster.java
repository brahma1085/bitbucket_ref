package com.banking.data.pojo;
// default package



/**
 * ODCCMaster entity. @author MyEclipse Persistence Tools
 */

public class ODCCMaster  implements java.io.Serializable {


    // Fields    

     private ODCCMasterId id;
     private Integer shNo;
     private String shType;
     private Integer cid;
     private String chBkIssue;
     private Integer noCoborrowers;
     private Integer noSurities;
     private Integer addrType;
     private Integer appnSrl;
     private String appnDate;
     private Double reqAmt;
     private Integer ppsCode;
     private Integer nomNo;
     private Integer lastTrSeq;
     private String lastTrDate;
     private Integer psBkSeq;
     private Integer lastLine;
     private Integer ledgerSeq;
     private String acStatus;
     private String defaultInd;
     private String freezeInd;
     private String acOpendate;
     private String acClosedate;
     private Integer intRateType;
     private Double intRate;
     private Integer psectCd;
     private Integer wkSect;
     private String sexCd;
     private String rel;
     private Integer dirCode;
     private String convDt;
     private String sancDt;
     private Double sancAmt;
     private String loanSanc;
     private String sancVer;
     private String intUptodt;
     private String limitUpto;
     private Integer period;
     private Double creditlimit;
     private Integer remdNo;
     private String remdDt;
     private String npaDt;
     private String npaStg;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public ODCCMaster() {
    }

	/** minimal constructor */
    public ODCCMaster(ODCCMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public ODCCMaster(ODCCMasterId id, Integer shNo, String shType, Integer cid, String chBkIssue, Integer noCoborrowers, Integer noSurities, Integer addrType, Integer appnSrl, String appnDate, Double reqAmt, Integer ppsCode, Integer nomNo, Integer lastTrSeq, String lastTrDate, Integer psBkSeq, Integer lastLine, Integer ledgerSeq, String acStatus, String defaultInd, String freezeInd, String acOpendate, String acClosedate, Integer intRateType, Double intRate, Integer psectCd, Integer wkSect, String sexCd, String rel, Integer dirCode, String convDt, String sancDt, Double sancAmt, String loanSanc, String sancVer, String intUptodt, String limitUpto, Integer period, Double creditlimit, Integer remdNo, String remdDt, String npaDt, String npaStg, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.id = id;
        this.shNo = shNo;
        this.shType = shType;
        this.cid = cid;
        this.chBkIssue = chBkIssue;
        this.noCoborrowers = noCoborrowers;
        this.noSurities = noSurities;
        this.addrType = addrType;
        this.appnSrl = appnSrl;
        this.appnDate = appnDate;
        this.reqAmt = reqAmt;
        this.ppsCode = ppsCode;
        this.nomNo = nomNo;
        this.lastTrSeq = lastTrSeq;
        this.lastTrDate = lastTrDate;
        this.psBkSeq = psBkSeq;
        this.lastLine = lastLine;
        this.ledgerSeq = ledgerSeq;
        this.acStatus = acStatus;
        this.defaultInd = defaultInd;
        this.freezeInd = freezeInd;
        this.acOpendate = acOpendate;
        this.acClosedate = acClosedate;
        this.intRateType = intRateType;
        this.intRate = intRate;
        this.psectCd = psectCd;
        this.wkSect = wkSect;
        this.sexCd = sexCd;
        this.rel = rel;
        this.dirCode = dirCode;
        this.convDt = convDt;
        this.sancDt = sancDt;
        this.sancAmt = sancAmt;
        this.loanSanc = loanSanc;
        this.sancVer = sancVer;
        this.intUptodt = intUptodt;
        this.limitUpto = limitUpto;
        this.period = period;
        this.creditlimit = creditlimit;
        this.remdNo = remdNo;
        this.remdDt = remdDt;
        this.npaDt = npaDt;
        this.npaStg = npaStg;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public ODCCMasterId getId() {
        return this.id;
    }
    
    public void setId(ODCCMasterId id) {
        this.id = id;
    }

    public Integer getShNo() {
        return this.shNo;
    }
    
    public void setShNo(Integer shNo) {
        this.shNo = shNo;
    }

    public String getShType() {
        return this.shType;
    }
    
    public void setShType(String shType) {
        this.shType = shType;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getChBkIssue() {
        return this.chBkIssue;
    }
    
    public void setChBkIssue(String chBkIssue) {
        this.chBkIssue = chBkIssue;
    }

    public Integer getNoCoborrowers() {
        return this.noCoborrowers;
    }
    
    public void setNoCoborrowers(Integer noCoborrowers) {
        this.noCoborrowers = noCoborrowers;
    }

    public Integer getNoSurities() {
        return this.noSurities;
    }
    
    public void setNoSurities(Integer noSurities) {
        this.noSurities = noSurities;
    }

    public Integer getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(Integer addrType) {
        this.addrType = addrType;
    }

    public Integer getAppnSrl() {
        return this.appnSrl;
    }
    
    public void setAppnSrl(Integer appnSrl) {
        this.appnSrl = appnSrl;
    }

    public String getAppnDate() {
        return this.appnDate;
    }
    
    public void setAppnDate(String appnDate) {
        this.appnDate = appnDate;
    }

    public Double getReqAmt() {
        return this.reqAmt;
    }
    
    public void setReqAmt(Double reqAmt) {
        this.reqAmt = reqAmt;
    }

    public Integer getPpsCode() {
        return this.ppsCode;
    }
    
    public void setPpsCode(Integer ppsCode) {
        this.ppsCode = ppsCode;
    }

    public Integer getNomNo() {
        return this.nomNo;
    }
    
    public void setNomNo(Integer nomNo) {
        this.nomNo = nomNo;
    }

    public Integer getLastTrSeq() {
        return this.lastTrSeq;
    }
    
    public void setLastTrSeq(Integer lastTrSeq) {
        this.lastTrSeq = lastTrSeq;
    }

    public String getLastTrDate() {
        return this.lastTrDate;
    }
    
    public void setLastTrDate(String lastTrDate) {
        this.lastTrDate = lastTrDate;
    }

    public Integer getPsBkSeq() {
        return this.psBkSeq;
    }
    
    public void setPsBkSeq(Integer psBkSeq) {
        this.psBkSeq = psBkSeq;
    }

    public Integer getLastLine() {
        return this.lastLine;
    }
    
    public void setLastLine(Integer lastLine) {
        this.lastLine = lastLine;
    }

    public Integer getLedgerSeq() {
        return this.ledgerSeq;
    }
    
    public void setLedgerSeq(Integer ledgerSeq) {
        this.ledgerSeq = ledgerSeq;
    }

    public String getAcStatus() {
        return this.acStatus;
    }
    
    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getDefaultInd() {
        return this.defaultInd;
    }
    
    public void setDefaultInd(String defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getFreezeInd() {
        return this.freezeInd;
    }
    
    public void setFreezeInd(String freezeInd) {
        this.freezeInd = freezeInd;
    }

    public String getAcOpendate() {
        return this.acOpendate;
    }
    
    public void setAcOpendate(String acOpendate) {
        this.acOpendate = acOpendate;
    }

    public String getAcClosedate() {
        return this.acClosedate;
    }
    
    public void setAcClosedate(String acClosedate) {
        this.acClosedate = acClosedate;
    }

    public Integer getIntRateType() {
        return this.intRateType;
    }
    
    public void setIntRateType(Integer intRateType) {
        this.intRateType = intRateType;
    }

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Integer getPsectCd() {
        return this.psectCd;
    }
    
    public void setPsectCd(Integer psectCd) {
        this.psectCd = psectCd;
    }

    public Integer getWkSect() {
        return this.wkSect;
    }
    
    public void setWkSect(Integer wkSect) {
        this.wkSect = wkSect;
    }

    public String getSexCd() {
        return this.sexCd;
    }
    
    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getRel() {
        return this.rel;
    }
    
    public void setRel(String rel) {
        this.rel = rel;
    }

    public Integer getDirCode() {
        return this.dirCode;
    }
    
    public void setDirCode(Integer dirCode) {
        this.dirCode = dirCode;
    }

    public String getConvDt() {
        return this.convDt;
    }
    
    public void setConvDt(String convDt) {
        this.convDt = convDt;
    }

    public String getSancDt() {
        return this.sancDt;
    }
    
    public void setSancDt(String sancDt) {
        this.sancDt = sancDt;
    }

    public Double getSancAmt() {
        return this.sancAmt;
    }
    
    public void setSancAmt(Double sancAmt) {
        this.sancAmt = sancAmt;
    }

    public String getLoanSanc() {
        return this.loanSanc;
    }
    
    public void setLoanSanc(String loanSanc) {
        this.loanSanc = loanSanc;
    }

    public String getSancVer() {
        return this.sancVer;
    }
    
    public void setSancVer(String sancVer) {
        this.sancVer = sancVer;
    }

    public String getIntUptodt() {
        return this.intUptodt;
    }
    
    public void setIntUptodt(String intUptodt) {
        this.intUptodt = intUptodt;
    }

    public String getLimitUpto() {
        return this.limitUpto;
    }
    
    public void setLimitUpto(String limitUpto) {
        this.limitUpto = limitUpto;
    }

    public Integer getPeriod() {
        return this.period;
    }
    
    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getCreditlimit() {
        return this.creditlimit;
    }
    
    public void setCreditlimit(Double creditlimit) {
        this.creditlimit = creditlimit;
    }

    public Integer getRemdNo() {
        return this.remdNo;
    }
    
    public void setRemdNo(Integer remdNo) {
        this.remdNo = remdNo;
    }

    public String getRemdDt() {
        return this.remdDt;
    }
    
    public void setRemdDt(String remdDt) {
        this.remdDt = remdDt;
    }

    public String getNpaDt() {
        return this.npaDt;
    }
    
    public void setNpaDt(String npaDt) {
        this.npaDt = npaDt;
    }

    public String getNpaStg() {
        return this.npaStg;
    }
    
    public void setNpaStg(String npaStg) {
        this.npaStg = npaStg;
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