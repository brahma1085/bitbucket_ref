package com.banking.data.pojo;
// default package



/**
 * LoanMaster entity. @author MyEclipse Persistence Tools
 */

public class LoanMaster  implements java.io.Serializable {


    // Fields    

     private LoanMasterId id;
     private Integer noCoborrowers;
     private Integer noSurities;
     private Integer cid;
     private String addrType;
     private Integer appnSrl;
     private String appnDate;
     private Double reqAmt;
     private Integer shNo;
     private String shType;
     private Integer ppsCode;
     private Integer nomRegNo;
     private String tdAcType;
     private Integer tdAcNo;
     private Integer intType;
     private Integer intRateType;
     private Double intRate;
     private String psectCd;
     private String wkSect;
     private String sexCd;
     private String rel;
     private Integer dirCode;
     private String convDate;
     private Integer holdayMth;
     private String sancDate;
     private Double sancAmt;
     private String loanSanc;
     private String sancVer;
     private Integer noInst;
     private Double instAmt;
     private String intUptoDate;
     private String lstTrnDate;
     private Integer lstTrSeq;
     private String defaultInd;
     private String closeDate;
     private String payMode;
     private String payAcType;
     private Integer payAcNo;
     private Integer remdNo;
     private String remdDate;
     private Double disbLeft;
     private String ldgprtDate;
     private String npaDate;
     private String npaStg;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public LoanMaster() {
    }

	/** minimal constructor */
    public LoanMaster(LoanMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public LoanMaster(LoanMasterId id, Integer noCoborrowers, Integer noSurities, Integer cid, String addrType, Integer appnSrl, String appnDate, Double reqAmt, Integer shNo, String shType, Integer ppsCode, Integer nomRegNo, String tdAcType, Integer tdAcNo, Integer intType, Integer intRateType, Double intRate, String psectCd, String wkSect, String sexCd, String rel, Integer dirCode, String convDate, Integer holdayMth, String sancDate, Double sancAmt, String loanSanc, String sancVer, Integer noInst, Double instAmt, String intUptoDate, String lstTrnDate, Integer lstTrSeq, String defaultInd, String closeDate, String payMode, String payAcType, Integer payAcNo, Integer remdNo, String remdDate, Double disbLeft, String ldgprtDate, String npaDate, String npaStg, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.id = id;
        this.noCoborrowers = noCoborrowers;
        this.noSurities = noSurities;
        this.cid = cid;
        this.addrType = addrType;
        this.appnSrl = appnSrl;
        this.appnDate = appnDate;
        this.reqAmt = reqAmt;
        this.shNo = shNo;
        this.shType = shType;
        this.ppsCode = ppsCode;
        this.nomRegNo = nomRegNo;
        this.tdAcType = tdAcType;
        this.tdAcNo = tdAcNo;
        this.intType = intType;
        this.intRateType = intRateType;
        this.intRate = intRate;
        this.psectCd = psectCd;
        this.wkSect = wkSect;
        this.sexCd = sexCd;
        this.rel = rel;
        this.dirCode = dirCode;
        this.convDate = convDate;
        this.holdayMth = holdayMth;
        this.sancDate = sancDate;
        this.sancAmt = sancAmt;
        this.loanSanc = loanSanc;
        this.sancVer = sancVer;
        this.noInst = noInst;
        this.instAmt = instAmt;
        this.intUptoDate = intUptoDate;
        this.lstTrnDate = lstTrnDate;
        this.lstTrSeq = lstTrSeq;
        this.defaultInd = defaultInd;
        this.closeDate = closeDate;
        this.payMode = payMode;
        this.payAcType = payAcType;
        this.payAcNo = payAcNo;
        this.remdNo = remdNo;
        this.remdDate = remdDate;
        this.disbLeft = disbLeft;
        this.ldgprtDate = ldgprtDate;
        this.npaDate = npaDate;
        this.npaStg = npaStg;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public LoanMasterId getId() {
        return this.id;
    }
    
    public void setId(LoanMasterId id) {
        this.id = id;
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

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(String addrType) {
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

    public Integer getPpsCode() {
        return this.ppsCode;
    }
    
    public void setPpsCode(Integer ppsCode) {
        this.ppsCode = ppsCode;
    }

    public Integer getNomRegNo() {
        return this.nomRegNo;
    }
    
    public void setNomRegNo(Integer nomRegNo) {
        this.nomRegNo = nomRegNo;
    }

    public String getTdAcType() {
        return this.tdAcType;
    }
    
    public void setTdAcType(String tdAcType) {
        this.tdAcType = tdAcType;
    }

    public Integer getTdAcNo() {
        return this.tdAcNo;
    }
    
    public void setTdAcNo(Integer tdAcNo) {
        this.tdAcNo = tdAcNo;
    }

    public Integer getIntType() {
        return this.intType;
    }
    
    public void setIntType(Integer intType) {
        this.intType = intType;
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

    public String getPsectCd() {
        return this.psectCd;
    }
    
    public void setPsectCd(String psectCd) {
        this.psectCd = psectCd;
    }

    public String getWkSect() {
        return this.wkSect;
    }
    
    public void setWkSect(String wkSect) {
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

    public String getConvDate() {
        return this.convDate;
    }
    
    public void setConvDate(String convDate) {
        this.convDate = convDate;
    }

    public Integer getHoldayMth() {
        return this.holdayMth;
    }
    
    public void setHoldayMth(Integer holdayMth) {
        this.holdayMth = holdayMth;
    }

    public String getSancDate() {
        return this.sancDate;
    }
    
    public void setSancDate(String sancDate) {
        this.sancDate = sancDate;
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

    public Integer getNoInst() {
        return this.noInst;
    }
    
    public void setNoInst(Integer noInst) {
        this.noInst = noInst;
    }

    public Double getInstAmt() {
        return this.instAmt;
    }
    
    public void setInstAmt(Double instAmt) {
        this.instAmt = instAmt;
    }

    public String getIntUptoDate() {
        return this.intUptoDate;
    }
    
    public void setIntUptoDate(String intUptoDate) {
        this.intUptoDate = intUptoDate;
    }

    public String getLstTrnDate() {
        return this.lstTrnDate;
    }
    
    public void setLstTrnDate(String lstTrnDate) {
        this.lstTrnDate = lstTrnDate;
    }

    public Integer getLstTrSeq() {
        return this.lstTrSeq;
    }
    
    public void setLstTrSeq(Integer lstTrSeq) {
        this.lstTrSeq = lstTrSeq;
    }

    public String getDefaultInd() {
        return this.defaultInd;
    }
    
    public void setDefaultInd(String defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getCloseDate() {
        return this.closeDate;
    }
    
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getPayMode() {
        return this.payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayAcType() {
        return this.payAcType;
    }
    
    public void setPayAcType(String payAcType) {
        this.payAcType = payAcType;
    }

    public Integer getPayAcNo() {
        return this.payAcNo;
    }
    
    public void setPayAcNo(Integer payAcNo) {
        this.payAcNo = payAcNo;
    }

    public Integer getRemdNo() {
        return this.remdNo;
    }
    
    public void setRemdNo(Integer remdNo) {
        this.remdNo = remdNo;
    }

    public String getRemdDate() {
        return this.remdDate;
    }
    
    public void setRemdDate(String remdDate) {
        this.remdDate = remdDate;
    }

    public Double getDisbLeft() {
        return this.disbLeft;
    }
    
    public void setDisbLeft(Double disbLeft) {
        this.disbLeft = disbLeft;
    }

    public String getLdgprtDate() {
        return this.ldgprtDate;
    }
    
    public void setLdgprtDate(String ldgprtDate) {
        this.ldgprtDate = ldgprtDate;
    }

    public String getNpaDate() {
        return this.npaDate;
    }
    
    public void setNpaDate(String npaDate) {
        this.npaDate = npaDate;
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