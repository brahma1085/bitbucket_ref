package com.banking.data.pojo;
// default package



/**
 * DepositMaster entity. @author MyEclipse Persistence Tools
 */

public class DepositMaster  implements java.io.Serializable {


    // Fields    

     private DepositMasterId id;
     private Integer cid;
     private Integer addType;
     private Integer autorenewalNo;
     private Integer noJtHldr;
     private String depDate;
     private String matDate;
     private Integer depYrs;
     private Integer depMths;
     private Integer depDays;
     private Double depAmt;
     private Double matAmt;
     private String nextPayDate;
     private String matPost;
     private String postDate;
     private String intrAcType;
     private Integer intrAcNo;
     private Integer nomNo;
     private Double intRate;
     private Integer extraRateType;
     private String rcvdBy;
     private String rcvdAcType;
     private Integer rcvdAcNo;
     private String intFreq;
     private String intMode;
     private String trfAcType;
     private Integer trfAcNo;
     private String intUptoDate;
     private Integer lstTrnSeq;
     private Integer lstPrSeq;
     private String lnAvailed;
     private String lnAcType;
     private Integer lnAcNo;
     private String depRenewed;
     private String newRct;
     private Integer rctNo;
     private String rctPrtd;
     private String rctSign;
     private String autoRenewal;
     private Double excAmt;
     private Integer closeInd;
     private String closeDate;
     private String renAcType;
     private Integer renAcNo;
     private String ldadjreq;
     private String ldgprtdt;
     private String intPaidDate;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public DepositMaster() {
    }

	/** minimal constructor */
    public DepositMaster(DepositMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public DepositMaster(DepositMasterId id, Integer cid, Integer addType, Integer autorenewalNo, Integer noJtHldr, String depDate, String matDate, Integer depYrs, Integer depMths, Integer depDays, Double depAmt, Double matAmt, String nextPayDate, String matPost, String postDate, String intrAcType, Integer intrAcNo, Integer nomNo, Double intRate, Integer extraRateType, String rcvdBy, String rcvdAcType, Integer rcvdAcNo, String intFreq, String intMode, String trfAcType, Integer trfAcNo, String intUptoDate, Integer lstTrnSeq, Integer lstPrSeq, String lnAvailed, String lnAcType, Integer lnAcNo, String depRenewed, String newRct, Integer rctNo, String rctPrtd, String rctSign, String autoRenewal, Double excAmt, Integer closeInd, String closeDate, String renAcType, Integer renAcNo, String ldadjreq, String ldgprtdt, String intPaidDate, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.id = id;
        this.cid = cid;
        this.addType = addType;
        this.autorenewalNo = autorenewalNo;
        this.noJtHldr = noJtHldr;
        this.depDate = depDate;
        this.matDate = matDate;
        this.depYrs = depYrs;
        this.depMths = depMths;
        this.depDays = depDays;
        this.depAmt = depAmt;
        this.matAmt = matAmt;
        this.nextPayDate = nextPayDate;
        this.matPost = matPost;
        this.postDate = postDate;
        this.intrAcType = intrAcType;
        this.intrAcNo = intrAcNo;
        this.nomNo = nomNo;
        this.intRate = intRate;
        this.extraRateType = extraRateType;
        this.rcvdBy = rcvdBy;
        this.rcvdAcType = rcvdAcType;
        this.rcvdAcNo = rcvdAcNo;
        this.intFreq = intFreq;
        this.intMode = intMode;
        this.trfAcType = trfAcType;
        this.trfAcNo = trfAcNo;
        this.intUptoDate = intUptoDate;
        this.lstTrnSeq = lstTrnSeq;
        this.lstPrSeq = lstPrSeq;
        this.lnAvailed = lnAvailed;
        this.lnAcType = lnAcType;
        this.lnAcNo = lnAcNo;
        this.depRenewed = depRenewed;
        this.newRct = newRct;
        this.rctNo = rctNo;
        this.rctPrtd = rctPrtd;
        this.rctSign = rctSign;
        this.autoRenewal = autoRenewal;
        this.excAmt = excAmt;
        this.closeInd = closeInd;
        this.closeDate = closeDate;
        this.renAcType = renAcType;
        this.renAcNo = renAcNo;
        this.ldadjreq = ldadjreq;
        this.ldgprtdt = ldgprtdt;
        this.intPaidDate = intPaidDate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public DepositMasterId getId() {
        return this.id;
    }
    
    public void setId(DepositMasterId id) {
        this.id = id;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAddType() {
        return this.addType;
    }
    
    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public Integer getAutorenewalNo() {
        return this.autorenewalNo;
    }
    
    public void setAutorenewalNo(Integer autorenewalNo) {
        this.autorenewalNo = autorenewalNo;
    }

    public Integer getNoJtHldr() {
        return this.noJtHldr;
    }
    
    public void setNoJtHldr(Integer noJtHldr) {
        this.noJtHldr = noJtHldr;
    }

    public String getDepDate() {
        return this.depDate;
    }
    
    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getMatDate() {
        return this.matDate;
    }
    
    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public Integer getDepYrs() {
        return this.depYrs;
    }
    
    public void setDepYrs(Integer depYrs) {
        this.depYrs = depYrs;
    }

    public Integer getDepMths() {
        return this.depMths;
    }
    
    public void setDepMths(Integer depMths) {
        this.depMths = depMths;
    }

    public Integer getDepDays() {
        return this.depDays;
    }
    
    public void setDepDays(Integer depDays) {
        this.depDays = depDays;
    }

    public Double getDepAmt() {
        return this.depAmt;
    }
    
    public void setDepAmt(Double depAmt) {
        this.depAmt = depAmt;
    }

    public Double getMatAmt() {
        return this.matAmt;
    }
    
    public void setMatAmt(Double matAmt) {
        this.matAmt = matAmt;
    }

    public String getNextPayDate() {
        return this.nextPayDate;
    }
    
    public void setNextPayDate(String nextPayDate) {
        this.nextPayDate = nextPayDate;
    }

    public String getMatPost() {
        return this.matPost;
    }
    
    public void setMatPost(String matPost) {
        this.matPost = matPost;
    }

    public String getPostDate() {
        return this.postDate;
    }
    
    public void setPostDate(String postDate) {
        this.postDate = postDate;
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

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Integer getExtraRateType() {
        return this.extraRateType;
    }
    
    public void setExtraRateType(Integer extraRateType) {
        this.extraRateType = extraRateType;
    }

    public String getRcvdBy() {
        return this.rcvdBy;
    }
    
    public void setRcvdBy(String rcvdBy) {
        this.rcvdBy = rcvdBy;
    }

    public String getRcvdAcType() {
        return this.rcvdAcType;
    }
    
    public void setRcvdAcType(String rcvdAcType) {
        this.rcvdAcType = rcvdAcType;
    }

    public Integer getRcvdAcNo() {
        return this.rcvdAcNo;
    }
    
    public void setRcvdAcNo(Integer rcvdAcNo) {
        this.rcvdAcNo = rcvdAcNo;
    }

    public String getIntFreq() {
        return this.intFreq;
    }
    
    public void setIntFreq(String intFreq) {
        this.intFreq = intFreq;
    }

    public String getIntMode() {
        return this.intMode;
    }
    
    public void setIntMode(String intMode) {
        this.intMode = intMode;
    }

    public String getTrfAcType() {
        return this.trfAcType;
    }
    
    public void setTrfAcType(String trfAcType) {
        this.trfAcType = trfAcType;
    }

    public Integer getTrfAcNo() {
        return this.trfAcNo;
    }
    
    public void setTrfAcNo(Integer trfAcNo) {
        this.trfAcNo = trfAcNo;
    }

    public String getIntUptoDate() {
        return this.intUptoDate;
    }
    
    public void setIntUptoDate(String intUptoDate) {
        this.intUptoDate = intUptoDate;
    }

    public Integer getLstTrnSeq() {
        return this.lstTrnSeq;
    }
    
    public void setLstTrnSeq(Integer lstTrnSeq) {
        this.lstTrnSeq = lstTrnSeq;
    }

    public Integer getLstPrSeq() {
        return this.lstPrSeq;
    }
    
    public void setLstPrSeq(Integer lstPrSeq) {
        this.lstPrSeq = lstPrSeq;
    }

    public String getLnAvailed() {
        return this.lnAvailed;
    }
    
    public void setLnAvailed(String lnAvailed) {
        this.lnAvailed = lnAvailed;
    }

    public String getLnAcType() {
        return this.lnAcType;
    }
    
    public void setLnAcType(String lnAcType) {
        this.lnAcType = lnAcType;
    }

    public Integer getLnAcNo() {
        return this.lnAcNo;
    }
    
    public void setLnAcNo(Integer lnAcNo) {
        this.lnAcNo = lnAcNo;
    }

    public String getDepRenewed() {
        return this.depRenewed;
    }
    
    public void setDepRenewed(String depRenewed) {
        this.depRenewed = depRenewed;
    }

    public String getNewRct() {
        return this.newRct;
    }
    
    public void setNewRct(String newRct) {
        this.newRct = newRct;
    }

    public Integer getRctNo() {
        return this.rctNo;
    }
    
    public void setRctNo(Integer rctNo) {
        this.rctNo = rctNo;
    }

    public String getRctPrtd() {
        return this.rctPrtd;
    }
    
    public void setRctPrtd(String rctPrtd) {
        this.rctPrtd = rctPrtd;
    }

    public String getRctSign() {
        return this.rctSign;
    }
    
    public void setRctSign(String rctSign) {
        this.rctSign = rctSign;
    }

    public String getAutoRenewal() {
        return this.autoRenewal;
    }
    
    public void setAutoRenewal(String autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public Double getExcAmt() {
        return this.excAmt;
    }
    
    public void setExcAmt(Double excAmt) {
        this.excAmt = excAmt;
    }

    public Integer getCloseInd() {
        return this.closeInd;
    }
    
    public void setCloseInd(Integer closeInd) {
        this.closeInd = closeInd;
    }

    public String getCloseDate() {
        return this.closeDate;
    }
    
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getRenAcType() {
        return this.renAcType;
    }
    
    public void setRenAcType(String renAcType) {
        this.renAcType = renAcType;
    }

    public Integer getRenAcNo() {
        return this.renAcNo;
    }
    
    public void setRenAcNo(Integer renAcNo) {
        this.renAcNo = renAcNo;
    }

    public String getLdadjreq() {
        return this.ldadjreq;
    }
    
    public void setLdadjreq(String ldadjreq) {
        this.ldadjreq = ldadjreq;
    }

    public String getLdgprtdt() {
        return this.ldgprtdt;
    }
    
    public void setLdgprtdt(String ldgprtdt) {
        this.ldgprtdt = ldgprtdt;
    }

    public String getIntPaidDate() {
        return this.intPaidDate;
    }
    
    public void setIntPaidDate(String intPaidDate) {
        this.intPaidDate = intPaidDate;
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