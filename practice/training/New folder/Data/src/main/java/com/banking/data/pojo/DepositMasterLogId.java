package com.banking.data.pojo;
// default package



/**
 * DepositMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class DepositMasterLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
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
     private String lstPrDate;
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
    public DepositMasterLogId() {
    }

	/** minimal constructor */
    public DepositMasterLogId(String acType, Integer acNo) {
        this.acType = acType;
        this.acNo = acNo;
    }
    
    /** full constructor */
    public DepositMasterLogId(String acType, Integer acNo, Integer cid, Integer addType, Integer autorenewalNo, Integer noJtHldr, String depDate, String matDate, Integer depYrs, Integer depMths, Integer depDays, Double depAmt, Double matAmt, String nextPayDate, String matPost, String postDate, String intrAcType, Integer intrAcNo, Integer nomNo, Double intRate, Integer extraRateType, String rcvdBy, String rcvdAcType, Integer rcvdAcNo, String intFreq, String intMode, String trfAcType, Integer trfAcNo, String intUptoDate, String lstPrDate, Integer lstPrSeq, String lnAvailed, String lnAcType, Integer lnAcNo, String depRenewed, String newRct, Integer rctNo, String rctPrtd, String rctSign, String autoRenewal, Double excAmt, Integer closeInd, String closeDate, String renAcType, Integer renAcNo, String ldadjreq, String ldgprtdt, String intPaidDate, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
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
        this.lstPrDate = lstPrDate;
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

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
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

    public String getLstPrDate() {
        return this.lstPrDate;
    }
    
    public void setLstPrDate(String lstPrDate) {
        this.lstPrDate = lstPrDate;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DepositMasterLogId) ) return false;
		 DepositMasterLogId castOther = ( DepositMasterLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAddType()==castOther.getAddType()) || ( this.getAddType()!=null && castOther.getAddType()!=null && this.getAddType().equals(castOther.getAddType()) ) )
 && ( (this.getAutorenewalNo()==castOther.getAutorenewalNo()) || ( this.getAutorenewalNo()!=null && castOther.getAutorenewalNo()!=null && this.getAutorenewalNo().equals(castOther.getAutorenewalNo()) ) )
 && ( (this.getNoJtHldr()==castOther.getNoJtHldr()) || ( this.getNoJtHldr()!=null && castOther.getNoJtHldr()!=null && this.getNoJtHldr().equals(castOther.getNoJtHldr()) ) )
 && ( (this.getDepDate()==castOther.getDepDate()) || ( this.getDepDate()!=null && castOther.getDepDate()!=null && this.getDepDate().equals(castOther.getDepDate()) ) )
 && ( (this.getMatDate()==castOther.getMatDate()) || ( this.getMatDate()!=null && castOther.getMatDate()!=null && this.getMatDate().equals(castOther.getMatDate()) ) )
 && ( (this.getDepYrs()==castOther.getDepYrs()) || ( this.getDepYrs()!=null && castOther.getDepYrs()!=null && this.getDepYrs().equals(castOther.getDepYrs()) ) )
 && ( (this.getDepMths()==castOther.getDepMths()) || ( this.getDepMths()!=null && castOther.getDepMths()!=null && this.getDepMths().equals(castOther.getDepMths()) ) )
 && ( (this.getDepDays()==castOther.getDepDays()) || ( this.getDepDays()!=null && castOther.getDepDays()!=null && this.getDepDays().equals(castOther.getDepDays()) ) )
 && ( (this.getDepAmt()==castOther.getDepAmt()) || ( this.getDepAmt()!=null && castOther.getDepAmt()!=null && this.getDepAmt().equals(castOther.getDepAmt()) ) )
 && ( (this.getMatAmt()==castOther.getMatAmt()) || ( this.getMatAmt()!=null && castOther.getMatAmt()!=null && this.getMatAmt().equals(castOther.getMatAmt()) ) )
 && ( (this.getNextPayDate()==castOther.getNextPayDate()) || ( this.getNextPayDate()!=null && castOther.getNextPayDate()!=null && this.getNextPayDate().equals(castOther.getNextPayDate()) ) )
 && ( (this.getMatPost()==castOther.getMatPost()) || ( this.getMatPost()!=null && castOther.getMatPost()!=null && this.getMatPost().equals(castOther.getMatPost()) ) )
 && ( (this.getPostDate()==castOther.getPostDate()) || ( this.getPostDate()!=null && castOther.getPostDate()!=null && this.getPostDate().equals(castOther.getPostDate()) ) )
 && ( (this.getIntrAcType()==castOther.getIntrAcType()) || ( this.getIntrAcType()!=null && castOther.getIntrAcType()!=null && this.getIntrAcType().equals(castOther.getIntrAcType()) ) )
 && ( (this.getIntrAcNo()==castOther.getIntrAcNo()) || ( this.getIntrAcNo()!=null && castOther.getIntrAcNo()!=null && this.getIntrAcNo().equals(castOther.getIntrAcNo()) ) )
 && ( (this.getNomNo()==castOther.getNomNo()) || ( this.getNomNo()!=null && castOther.getNomNo()!=null && this.getNomNo().equals(castOther.getNomNo()) ) )
 && ( (this.getIntRate()==castOther.getIntRate()) || ( this.getIntRate()!=null && castOther.getIntRate()!=null && this.getIntRate().equals(castOther.getIntRate()) ) )
 && ( (this.getExtraRateType()==castOther.getExtraRateType()) || ( this.getExtraRateType()!=null && castOther.getExtraRateType()!=null && this.getExtraRateType().equals(castOther.getExtraRateType()) ) )
 && ( (this.getRcvdBy()==castOther.getRcvdBy()) || ( this.getRcvdBy()!=null && castOther.getRcvdBy()!=null && this.getRcvdBy().equals(castOther.getRcvdBy()) ) )
 && ( (this.getRcvdAcType()==castOther.getRcvdAcType()) || ( this.getRcvdAcType()!=null && castOther.getRcvdAcType()!=null && this.getRcvdAcType().equals(castOther.getRcvdAcType()) ) )
 && ( (this.getRcvdAcNo()==castOther.getRcvdAcNo()) || ( this.getRcvdAcNo()!=null && castOther.getRcvdAcNo()!=null && this.getRcvdAcNo().equals(castOther.getRcvdAcNo()) ) )
 && ( (this.getIntFreq()==castOther.getIntFreq()) || ( this.getIntFreq()!=null && castOther.getIntFreq()!=null && this.getIntFreq().equals(castOther.getIntFreq()) ) )
 && ( (this.getIntMode()==castOther.getIntMode()) || ( this.getIntMode()!=null && castOther.getIntMode()!=null && this.getIntMode().equals(castOther.getIntMode()) ) )
 && ( (this.getTrfAcType()==castOther.getTrfAcType()) || ( this.getTrfAcType()!=null && castOther.getTrfAcType()!=null && this.getTrfAcType().equals(castOther.getTrfAcType()) ) )
 && ( (this.getTrfAcNo()==castOther.getTrfAcNo()) || ( this.getTrfAcNo()!=null && castOther.getTrfAcNo()!=null && this.getTrfAcNo().equals(castOther.getTrfAcNo()) ) )
 && ( (this.getIntUptoDate()==castOther.getIntUptoDate()) || ( this.getIntUptoDate()!=null && castOther.getIntUptoDate()!=null && this.getIntUptoDate().equals(castOther.getIntUptoDate()) ) )
 && ( (this.getLstPrDate()==castOther.getLstPrDate()) || ( this.getLstPrDate()!=null && castOther.getLstPrDate()!=null && this.getLstPrDate().equals(castOther.getLstPrDate()) ) )
 && ( (this.getLstPrSeq()==castOther.getLstPrSeq()) || ( this.getLstPrSeq()!=null && castOther.getLstPrSeq()!=null && this.getLstPrSeq().equals(castOther.getLstPrSeq()) ) )
 && ( (this.getLnAvailed()==castOther.getLnAvailed()) || ( this.getLnAvailed()!=null && castOther.getLnAvailed()!=null && this.getLnAvailed().equals(castOther.getLnAvailed()) ) )
 && ( (this.getLnAcType()==castOther.getLnAcType()) || ( this.getLnAcType()!=null && castOther.getLnAcType()!=null && this.getLnAcType().equals(castOther.getLnAcType()) ) )
 && ( (this.getLnAcNo()==castOther.getLnAcNo()) || ( this.getLnAcNo()!=null && castOther.getLnAcNo()!=null && this.getLnAcNo().equals(castOther.getLnAcNo()) ) )
 && ( (this.getDepRenewed()==castOther.getDepRenewed()) || ( this.getDepRenewed()!=null && castOther.getDepRenewed()!=null && this.getDepRenewed().equals(castOther.getDepRenewed()) ) )
 && ( (this.getNewRct()==castOther.getNewRct()) || ( this.getNewRct()!=null && castOther.getNewRct()!=null && this.getNewRct().equals(castOther.getNewRct()) ) )
 && ( (this.getRctNo()==castOther.getRctNo()) || ( this.getRctNo()!=null && castOther.getRctNo()!=null && this.getRctNo().equals(castOther.getRctNo()) ) )
 && ( (this.getRctPrtd()==castOther.getRctPrtd()) || ( this.getRctPrtd()!=null && castOther.getRctPrtd()!=null && this.getRctPrtd().equals(castOther.getRctPrtd()) ) )
 && ( (this.getRctSign()==castOther.getRctSign()) || ( this.getRctSign()!=null && castOther.getRctSign()!=null && this.getRctSign().equals(castOther.getRctSign()) ) )
 && ( (this.getAutoRenewal()==castOther.getAutoRenewal()) || ( this.getAutoRenewal()!=null && castOther.getAutoRenewal()!=null && this.getAutoRenewal().equals(castOther.getAutoRenewal()) ) )
 && ( (this.getExcAmt()==castOther.getExcAmt()) || ( this.getExcAmt()!=null && castOther.getExcAmt()!=null && this.getExcAmt().equals(castOther.getExcAmt()) ) )
 && ( (this.getCloseInd()==castOther.getCloseInd()) || ( this.getCloseInd()!=null && castOther.getCloseInd()!=null && this.getCloseInd().equals(castOther.getCloseInd()) ) )
 && ( (this.getCloseDate()==castOther.getCloseDate()) || ( this.getCloseDate()!=null && castOther.getCloseDate()!=null && this.getCloseDate().equals(castOther.getCloseDate()) ) )
 && ( (this.getRenAcType()==castOther.getRenAcType()) || ( this.getRenAcType()!=null && castOther.getRenAcType()!=null && this.getRenAcType().equals(castOther.getRenAcType()) ) )
 && ( (this.getRenAcNo()==castOther.getRenAcNo()) || ( this.getRenAcNo()!=null && castOther.getRenAcNo()!=null && this.getRenAcNo().equals(castOther.getRenAcNo()) ) )
 && ( (this.getLdadjreq()==castOther.getLdadjreq()) || ( this.getLdadjreq()!=null && castOther.getLdadjreq()!=null && this.getLdadjreq().equals(castOther.getLdadjreq()) ) )
 && ( (this.getLdgprtdt()==castOther.getLdgprtdt()) || ( this.getLdgprtdt()!=null && castOther.getLdgprtdt()!=null && this.getLdgprtdt().equals(castOther.getLdgprtdt()) ) )
 && ( (this.getIntPaidDate()==castOther.getIntPaidDate()) || ( this.getIntPaidDate()!=null && castOther.getIntPaidDate()!=null && this.getIntPaidDate().equals(castOther.getIntPaidDate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAddType() == null ? 0 : this.getAddType().hashCode() );
         result = 37 * result + ( getAutorenewalNo() == null ? 0 : this.getAutorenewalNo().hashCode() );
         result = 37 * result + ( getNoJtHldr() == null ? 0 : this.getNoJtHldr().hashCode() );
         result = 37 * result + ( getDepDate() == null ? 0 : this.getDepDate().hashCode() );
         result = 37 * result + ( getMatDate() == null ? 0 : this.getMatDate().hashCode() );
         result = 37 * result + ( getDepYrs() == null ? 0 : this.getDepYrs().hashCode() );
         result = 37 * result + ( getDepMths() == null ? 0 : this.getDepMths().hashCode() );
         result = 37 * result + ( getDepDays() == null ? 0 : this.getDepDays().hashCode() );
         result = 37 * result + ( getDepAmt() == null ? 0 : this.getDepAmt().hashCode() );
         result = 37 * result + ( getMatAmt() == null ? 0 : this.getMatAmt().hashCode() );
         result = 37 * result + ( getNextPayDate() == null ? 0 : this.getNextPayDate().hashCode() );
         result = 37 * result + ( getMatPost() == null ? 0 : this.getMatPost().hashCode() );
         result = 37 * result + ( getPostDate() == null ? 0 : this.getPostDate().hashCode() );
         result = 37 * result + ( getIntrAcType() == null ? 0 : this.getIntrAcType().hashCode() );
         result = 37 * result + ( getIntrAcNo() == null ? 0 : this.getIntrAcNo().hashCode() );
         result = 37 * result + ( getNomNo() == null ? 0 : this.getNomNo().hashCode() );
         result = 37 * result + ( getIntRate() == null ? 0 : this.getIntRate().hashCode() );
         result = 37 * result + ( getExtraRateType() == null ? 0 : this.getExtraRateType().hashCode() );
         result = 37 * result + ( getRcvdBy() == null ? 0 : this.getRcvdBy().hashCode() );
         result = 37 * result + ( getRcvdAcType() == null ? 0 : this.getRcvdAcType().hashCode() );
         result = 37 * result + ( getRcvdAcNo() == null ? 0 : this.getRcvdAcNo().hashCode() );
         result = 37 * result + ( getIntFreq() == null ? 0 : this.getIntFreq().hashCode() );
         result = 37 * result + ( getIntMode() == null ? 0 : this.getIntMode().hashCode() );
         result = 37 * result + ( getTrfAcType() == null ? 0 : this.getTrfAcType().hashCode() );
         result = 37 * result + ( getTrfAcNo() == null ? 0 : this.getTrfAcNo().hashCode() );
         result = 37 * result + ( getIntUptoDate() == null ? 0 : this.getIntUptoDate().hashCode() );
         result = 37 * result + ( getLstPrDate() == null ? 0 : this.getLstPrDate().hashCode() );
         result = 37 * result + ( getLstPrSeq() == null ? 0 : this.getLstPrSeq().hashCode() );
         result = 37 * result + ( getLnAvailed() == null ? 0 : this.getLnAvailed().hashCode() );
         result = 37 * result + ( getLnAcType() == null ? 0 : this.getLnAcType().hashCode() );
         result = 37 * result + ( getLnAcNo() == null ? 0 : this.getLnAcNo().hashCode() );
         result = 37 * result + ( getDepRenewed() == null ? 0 : this.getDepRenewed().hashCode() );
         result = 37 * result + ( getNewRct() == null ? 0 : this.getNewRct().hashCode() );
         result = 37 * result + ( getRctNo() == null ? 0 : this.getRctNo().hashCode() );
         result = 37 * result + ( getRctPrtd() == null ? 0 : this.getRctPrtd().hashCode() );
         result = 37 * result + ( getRctSign() == null ? 0 : this.getRctSign().hashCode() );
         result = 37 * result + ( getAutoRenewal() == null ? 0 : this.getAutoRenewal().hashCode() );
         result = 37 * result + ( getExcAmt() == null ? 0 : this.getExcAmt().hashCode() );
         result = 37 * result + ( getCloseInd() == null ? 0 : this.getCloseInd().hashCode() );
         result = 37 * result + ( getCloseDate() == null ? 0 : this.getCloseDate().hashCode() );
         result = 37 * result + ( getRenAcType() == null ? 0 : this.getRenAcType().hashCode() );
         result = 37 * result + ( getRenAcNo() == null ? 0 : this.getRenAcNo().hashCode() );
         result = 37 * result + ( getLdadjreq() == null ? 0 : this.getLdadjreq().hashCode() );
         result = 37 * result + ( getLdgprtdt() == null ? 0 : this.getLdgprtdt().hashCode() );
         result = 37 * result + ( getIntPaidDate() == null ? 0 : this.getIntPaidDate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}