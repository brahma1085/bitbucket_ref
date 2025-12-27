package com.banking.data.pojo;
// default package



/**
 * ShareMaster entity. @author MyEclipse Persistence Tools
 */

public class ShareMaster  implements java.io.Serializable {


    // Fields    

     private ShareMasterId id;
     private String shInd;
     private Integer brCode;
     private Integer cid;
     private Integer addrType;
     private Integer memCat;
     private String memIssuedate;
     private Double shareVal;
     private String intrAcType;
     private Integer intrAcNo;
     private Integer nomNo;
     private Integer noCert;
     private String divUptodt;
     private String lnAvailed;
     private String memClDate;
     private String trfDate;
     private String shareStat;
     private String payMode;
     private String payAcType;
     private Integer payAcNo;
     private String ldgprtdt;
     private Integer relCode;
     private String relDirector;
     private Integer lstTrnSeq;
     private String deUser;
     private String deTml;
     private String deDate;
     private String veUser;
     private String veTml;
     private String veDate;


    // Constructors

    /** default constructor */
    public ShareMaster() {
    }

	/** minimal constructor */
    public ShareMaster(ShareMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public ShareMaster(ShareMasterId id, String shInd, Integer brCode, Integer cid, Integer addrType, Integer memCat, String memIssuedate, Double shareVal, String intrAcType, Integer intrAcNo, Integer nomNo, Integer noCert, String divUptodt, String lnAvailed, String memClDate, String trfDate, String shareStat, String payMode, String payAcType, Integer payAcNo, String ldgprtdt, Integer relCode, String relDirector, Integer lstTrnSeq, String deUser, String deTml, String deDate, String veUser, String veTml, String veDate) {
        this.id = id;
        this.shInd = shInd;
        this.brCode = brCode;
        this.cid = cid;
        this.addrType = addrType;
        this.memCat = memCat;
        this.memIssuedate = memIssuedate;
        this.shareVal = shareVal;
        this.intrAcType = intrAcType;
        this.intrAcNo = intrAcNo;
        this.nomNo = nomNo;
        this.noCert = noCert;
        this.divUptodt = divUptodt;
        this.lnAvailed = lnAvailed;
        this.memClDate = memClDate;
        this.trfDate = trfDate;
        this.shareStat = shareStat;
        this.payMode = payMode;
        this.payAcType = payAcType;
        this.payAcNo = payAcNo;
        this.ldgprtdt = ldgprtdt;
        this.relCode = relCode;
        this.relDirector = relDirector;
        this.lstTrnSeq = lstTrnSeq;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.veUser = veUser;
        this.veTml = veTml;
        this.veDate = veDate;
    }

   
    // Property accessors

    public ShareMasterId getId() {
        return this.id;
    }
    
    public void setId(ShareMasterId id) {
        this.id = id;
    }

    public String getShInd() {
        return this.shInd;
    }
    
    public void setShInd(String shInd) {
        this.shInd = shInd;
    }

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(Integer addrType) {
        this.addrType = addrType;
    }

    public Integer getMemCat() {
        return this.memCat;
    }
    
    public void setMemCat(Integer memCat) {
        this.memCat = memCat;
    }

    public String getMemIssuedate() {
        return this.memIssuedate;
    }
    
    public void setMemIssuedate(String memIssuedate) {
        this.memIssuedate = memIssuedate;
    }

    public Double getShareVal() {
        return this.shareVal;
    }
    
    public void setShareVal(Double shareVal) {
        this.shareVal = shareVal;
    }

    public String getIntrAcType() {
        return this.intrAcType;
    }
    
    public void setIntrAcType(String intrAcType) {
        this.intrAcType = intrAcType;
    }

    public Integer getIntrAcNo() {
        return this.intrAcNo;
    }
    
    public void setIntrAcNo(Integer intrAcNo) {
        this.intrAcNo = intrAcNo;
    }

    public Integer getNomNo() {
        return this.nomNo;
    }
    
    public void setNomNo(Integer nomNo) {
        this.nomNo = nomNo;
    }

    public Integer getNoCert() {
        return this.noCert;
    }
    
    public void setNoCert(Integer noCert) {
        this.noCert = noCert;
    }

    public String getDivUptodt() {
        return this.divUptodt;
    }
    
    public void setDivUptodt(String divUptodt) {
        this.divUptodt = divUptodt;
    }

    public String getLnAvailed() {
        return this.lnAvailed;
    }
    
    public void setLnAvailed(String lnAvailed) {
        this.lnAvailed = lnAvailed;
    }

    public String getMemClDate() {
        return this.memClDate;
    }
    
    public void setMemClDate(String memClDate) {
        this.memClDate = memClDate;
    }

    public String getTrfDate() {
        return this.trfDate;
    }
    
    public void setTrfDate(String trfDate) {
        this.trfDate = trfDate;
    }

    public String getShareStat() {
        return this.shareStat;
    }
    
    public void setShareStat(String shareStat) {
        this.shareStat = shareStat;
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

    public String getLdgprtdt() {
        return this.ldgprtdt;
    }
    
    public void setLdgprtdt(String ldgprtdt) {
        this.ldgprtdt = ldgprtdt;
    }

    public Integer getRelCode() {
        return this.relCode;
    }
    
    public void setRelCode(Integer relCode) {
        this.relCode = relCode;
    }

    public String getRelDirector() {
        return this.relDirector;
    }
    
    public void setRelDirector(String relDirector) {
        this.relDirector = relDirector;
    }

    public Integer getLstTrnSeq() {
        return this.lstTrnSeq;
    }
    
    public void setLstTrnSeq(Integer lstTrnSeq) {
        this.lstTrnSeq = lstTrnSeq;
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

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }
   








}