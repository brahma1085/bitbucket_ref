package com.banking.data.pojo;
// default package



/**
 * ShareMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class ShareMasterLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String shInd;
     private Integer tempNo;
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
    public ShareMasterLogId() {
    }

	/** minimal constructor */
    public ShareMasterLogId(String acType, Integer acNo, Integer tempNo) {
        this.acType = acType;
        this.acNo = acNo;
        this.tempNo = tempNo;
    }
    
    /** full constructor */
    public ShareMasterLogId(String acType, Integer acNo, String shInd, Integer tempNo, Integer brCode, Integer cid, Integer addrType, Integer memCat, String memIssuedate, Double shareVal, String intrAcType, Integer intrAcNo, Integer nomNo, Integer noCert, String divUptodt, String lnAvailed, String memClDate, String trfDate, String shareStat, String payMode, String payAcType, Integer payAcNo, String ldgprtdt, Integer relCode, String relDirector, Integer lstTrnSeq, String deUser, String deTml, String deDate, String veUser, String veTml, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.shInd = shInd;
        this.tempNo = tempNo;
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

    public String getShInd() {
        return this.shInd;
    }
    
    public void setShInd(String shInd) {
        this.shInd = shInd;
    }

    public Integer getTempNo() {
        return this.tempNo;
    }
    
    public void setTempNo(Integer tempNo) {
        this.tempNo = tempNo;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareMasterLogId) ) return false;
		 ShareMasterLogId castOther = ( ShareMasterLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getShInd()==castOther.getShInd()) || ( this.getShInd()!=null && castOther.getShInd()!=null && this.getShInd().equals(castOther.getShInd()) ) )
 && ( (this.getTempNo()==castOther.getTempNo()) || ( this.getTempNo()!=null && castOther.getTempNo()!=null && this.getTempNo().equals(castOther.getTempNo()) ) )
 && ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAddrType()==castOther.getAddrType()) || ( this.getAddrType()!=null && castOther.getAddrType()!=null && this.getAddrType().equals(castOther.getAddrType()) ) )
 && ( (this.getMemCat()==castOther.getMemCat()) || ( this.getMemCat()!=null && castOther.getMemCat()!=null && this.getMemCat().equals(castOther.getMemCat()) ) )
 && ( (this.getMemIssuedate()==castOther.getMemIssuedate()) || ( this.getMemIssuedate()!=null && castOther.getMemIssuedate()!=null && this.getMemIssuedate().equals(castOther.getMemIssuedate()) ) )
 && ( (this.getShareVal()==castOther.getShareVal()) || ( this.getShareVal()!=null && castOther.getShareVal()!=null && this.getShareVal().equals(castOther.getShareVal()) ) )
 && ( (this.getIntrAcType()==castOther.getIntrAcType()) || ( this.getIntrAcType()!=null && castOther.getIntrAcType()!=null && this.getIntrAcType().equals(castOther.getIntrAcType()) ) )
 && ( (this.getIntrAcNo()==castOther.getIntrAcNo()) || ( this.getIntrAcNo()!=null && castOther.getIntrAcNo()!=null && this.getIntrAcNo().equals(castOther.getIntrAcNo()) ) )
 && ( (this.getNomNo()==castOther.getNomNo()) || ( this.getNomNo()!=null && castOther.getNomNo()!=null && this.getNomNo().equals(castOther.getNomNo()) ) )
 && ( (this.getNoCert()==castOther.getNoCert()) || ( this.getNoCert()!=null && castOther.getNoCert()!=null && this.getNoCert().equals(castOther.getNoCert()) ) )
 && ( (this.getDivUptodt()==castOther.getDivUptodt()) || ( this.getDivUptodt()!=null && castOther.getDivUptodt()!=null && this.getDivUptodt().equals(castOther.getDivUptodt()) ) )
 && ( (this.getLnAvailed()==castOther.getLnAvailed()) || ( this.getLnAvailed()!=null && castOther.getLnAvailed()!=null && this.getLnAvailed().equals(castOther.getLnAvailed()) ) )
 && ( (this.getMemClDate()==castOther.getMemClDate()) || ( this.getMemClDate()!=null && castOther.getMemClDate()!=null && this.getMemClDate().equals(castOther.getMemClDate()) ) )
 && ( (this.getTrfDate()==castOther.getTrfDate()) || ( this.getTrfDate()!=null && castOther.getTrfDate()!=null && this.getTrfDate().equals(castOther.getTrfDate()) ) )
 && ( (this.getShareStat()==castOther.getShareStat()) || ( this.getShareStat()!=null && castOther.getShareStat()!=null && this.getShareStat().equals(castOther.getShareStat()) ) )
 && ( (this.getPayMode()==castOther.getPayMode()) || ( this.getPayMode()!=null && castOther.getPayMode()!=null && this.getPayMode().equals(castOther.getPayMode()) ) )
 && ( (this.getPayAcType()==castOther.getPayAcType()) || ( this.getPayAcType()!=null && castOther.getPayAcType()!=null && this.getPayAcType().equals(castOther.getPayAcType()) ) )
 && ( (this.getPayAcNo()==castOther.getPayAcNo()) || ( this.getPayAcNo()!=null && castOther.getPayAcNo()!=null && this.getPayAcNo().equals(castOther.getPayAcNo()) ) )
 && ( (this.getLdgprtdt()==castOther.getLdgprtdt()) || ( this.getLdgprtdt()!=null && castOther.getLdgprtdt()!=null && this.getLdgprtdt().equals(castOther.getLdgprtdt()) ) )
 && ( (this.getRelCode()==castOther.getRelCode()) || ( this.getRelCode()!=null && castOther.getRelCode()!=null && this.getRelCode().equals(castOther.getRelCode()) ) )
 && ( (this.getRelDirector()==castOther.getRelDirector()) || ( this.getRelDirector()!=null && castOther.getRelDirector()!=null && this.getRelDirector().equals(castOther.getRelDirector()) ) )
 && ( (this.getLstTrnSeq()==castOther.getLstTrnSeq()) || ( this.getLstTrnSeq()!=null && castOther.getLstTrnSeq()!=null && this.getLstTrnSeq().equals(castOther.getLstTrnSeq()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getShInd() == null ? 0 : this.getShInd().hashCode() );
         result = 37 * result + ( getTempNo() == null ? 0 : this.getTempNo().hashCode() );
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAddrType() == null ? 0 : this.getAddrType().hashCode() );
         result = 37 * result + ( getMemCat() == null ? 0 : this.getMemCat().hashCode() );
         result = 37 * result + ( getMemIssuedate() == null ? 0 : this.getMemIssuedate().hashCode() );
         result = 37 * result + ( getShareVal() == null ? 0 : this.getShareVal().hashCode() );
         result = 37 * result + ( getIntrAcType() == null ? 0 : this.getIntrAcType().hashCode() );
         result = 37 * result + ( getIntrAcNo() == null ? 0 : this.getIntrAcNo().hashCode() );
         result = 37 * result + ( getNomNo() == null ? 0 : this.getNomNo().hashCode() );
         result = 37 * result + ( getNoCert() == null ? 0 : this.getNoCert().hashCode() );
         result = 37 * result + ( getDivUptodt() == null ? 0 : this.getDivUptodt().hashCode() );
         result = 37 * result + ( getLnAvailed() == null ? 0 : this.getLnAvailed().hashCode() );
         result = 37 * result + ( getMemClDate() == null ? 0 : this.getMemClDate().hashCode() );
         result = 37 * result + ( getTrfDate() == null ? 0 : this.getTrfDate().hashCode() );
         result = 37 * result + ( getShareStat() == null ? 0 : this.getShareStat().hashCode() );
         result = 37 * result + ( getPayMode() == null ? 0 : this.getPayMode().hashCode() );
         result = 37 * result + ( getPayAcType() == null ? 0 : this.getPayAcType().hashCode() );
         result = 37 * result + ( getPayAcNo() == null ? 0 : this.getPayAcNo().hashCode() );
         result = 37 * result + ( getLdgprtdt() == null ? 0 : this.getLdgprtdt().hashCode() );
         result = 37 * result + ( getRelCode() == null ? 0 : this.getRelCode().hashCode() );
         result = 37 * result + ( getRelDirector() == null ? 0 : this.getRelDirector().hashCode() );
         result = 37 * result + ( getLstTrnSeq() == null ? 0 : this.getLstTrnSeq().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}