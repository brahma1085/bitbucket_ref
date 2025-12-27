package com.banking.data.pojo;
// default package



/**
 * ShareTransactionId entity. @author MyEclipse Persistence Tools
 */

public class ShareTransactionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer trnNo;
     private Integer acNo;
     private Integer trnSeq;
     private String trnDate;
     private String trnType;
     private Double trnAmt;
     private Integer refNo;
     private String trnNarr;
     private String trnMode;
     private String trnSource;
     private String cdInd;
     private String altInd;
     private String suspInd;
     private Double shareBal;
     private Integer distNoFrom;
     private Integer distNoTo;
     private Integer shCertNo;
     private String shCertDt;
     private String certPrtd;
     private String markdel;
     private String deUser;
     private String deTml;
     private String deDate;
     private String veUser;
     private String veDate;
     private String veTml;


    // Constructors

    /** default constructor */
    public ShareTransactionId() {
    }

    
    /** full constructor */
    public ShareTransactionId(String acType, Integer trnNo, Integer acNo, Integer trnSeq, String trnDate, String trnType, Double trnAmt, Integer refNo, String trnNarr, String trnMode, String trnSource, String cdInd, String altInd, String suspInd, Double shareBal, Integer distNoFrom, Integer distNoTo, Integer shCertNo, String shCertDt, String certPrtd, String markdel, String deUser, String deTml, String deDate, String veUser, String veDate, String veTml) {
        this.acType = acType;
        this.trnNo = trnNo;
        this.acNo = acNo;
        this.trnSeq = trnSeq;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.trnAmt = trnAmt;
        this.refNo = refNo;
        this.trnNarr = trnNarr;
        this.trnMode = trnMode;
        this.trnSource = trnSource;
        this.cdInd = cdInd;
        this.altInd = altInd;
        this.suspInd = suspInd;
        this.shareBal = shareBal;
        this.distNoFrom = distNoFrom;
        this.distNoTo = distNoTo;
        this.shCertNo = shCertNo;
        this.shCertDt = shCertDt;
        this.certPrtd = certPrtd;
        this.markdel = markdel;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.veUser = veUser;
        this.veDate = veDate;
        this.veTml = veTml;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getTrnNo() {
        return this.trnNo;
    }
    
    public void setTrnNo(Integer trnNo) {
        this.trnNo = trnNo;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public Integer getTrnSeq() {
        return this.trnSeq;
    }
    
    public void setTrnSeq(Integer trnSeq) {
        this.trnSeq = trnSeq;
    }

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public Integer getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
    }

    public String getTrnNarr() {
        return this.trnNarr;
    }
    
    public void setTrnNarr(String trnNarr) {
        this.trnNarr = trnNarr;
    }

    public String getTrnMode() {
        return this.trnMode;
    }
    
    public void setTrnMode(String trnMode) {
        this.trnMode = trnMode;
    }

    public String getTrnSource() {
        return this.trnSource;
    }
    
    public void setTrnSource(String trnSource) {
        this.trnSource = trnSource;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getAltInd() {
        return this.altInd;
    }
    
    public void setAltInd(String altInd) {
        this.altInd = altInd;
    }

    public String getSuspInd() {
        return this.suspInd;
    }
    
    public void setSuspInd(String suspInd) {
        this.suspInd = suspInd;
    }

    public Double getShareBal() {
        return this.shareBal;
    }
    
    public void setShareBal(Double shareBal) {
        this.shareBal = shareBal;
    }

    public Integer getDistNoFrom() {
        return this.distNoFrom;
    }
    
    public void setDistNoFrom(Integer distNoFrom) {
        this.distNoFrom = distNoFrom;
    }

    public Integer getDistNoTo() {
        return this.distNoTo;
    }
    
    public void setDistNoTo(Integer distNoTo) {
        this.distNoTo = distNoTo;
    }

    public Integer getShCertNo() {
        return this.shCertNo;
    }
    
    public void setShCertNo(Integer shCertNo) {
        this.shCertNo = shCertNo;
    }

    public String getShCertDt() {
        return this.shCertDt;
    }
    
    public void setShCertDt(String shCertDt) {
        this.shCertDt = shCertDt;
    }

    public String getCertPrtd() {
        return this.certPrtd;
    }
    
    public void setCertPrtd(String certPrtd) {
        this.certPrtd = certPrtd;
    }

    public String getMarkdel() {
        return this.markdel;
    }
    
    public void setMarkdel(String markdel) {
        this.markdel = markdel;
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

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareTransactionId) ) return false;
		 ShareTransactionId castOther = ( ShareTransactionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getTrnNo()==castOther.getTrnNo()) || ( this.getTrnNo()!=null && castOther.getTrnNo()!=null && this.getTrnNo().equals(castOther.getTrnNo()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getTrnNarr()==castOther.getTrnNarr()) || ( this.getTrnNarr()!=null && castOther.getTrnNarr()!=null && this.getTrnNarr().equals(castOther.getTrnNarr()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnSource()==castOther.getTrnSource()) || ( this.getTrnSource()!=null && castOther.getTrnSource()!=null && this.getTrnSource().equals(castOther.getTrnSource()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getAltInd()==castOther.getAltInd()) || ( this.getAltInd()!=null && castOther.getAltInd()!=null && this.getAltInd().equals(castOther.getAltInd()) ) )
 && ( (this.getSuspInd()==castOther.getSuspInd()) || ( this.getSuspInd()!=null && castOther.getSuspInd()!=null && this.getSuspInd().equals(castOther.getSuspInd()) ) )
 && ( (this.getShareBal()==castOther.getShareBal()) || ( this.getShareBal()!=null && castOther.getShareBal()!=null && this.getShareBal().equals(castOther.getShareBal()) ) )
 && ( (this.getDistNoFrom()==castOther.getDistNoFrom()) || ( this.getDistNoFrom()!=null && castOther.getDistNoFrom()!=null && this.getDistNoFrom().equals(castOther.getDistNoFrom()) ) )
 && ( (this.getDistNoTo()==castOther.getDistNoTo()) || ( this.getDistNoTo()!=null && castOther.getDistNoTo()!=null && this.getDistNoTo().equals(castOther.getDistNoTo()) ) )
 && ( (this.getShCertNo()==castOther.getShCertNo()) || ( this.getShCertNo()!=null && castOther.getShCertNo()!=null && this.getShCertNo().equals(castOther.getShCertNo()) ) )
 && ( (this.getShCertDt()==castOther.getShCertDt()) || ( this.getShCertDt()!=null && castOther.getShCertDt()!=null && this.getShCertDt().equals(castOther.getShCertDt()) ) )
 && ( (this.getCertPrtd()==castOther.getCertPrtd()) || ( this.getCertPrtd()!=null && castOther.getCertPrtd()!=null && this.getCertPrtd().equals(castOther.getCertPrtd()) ) )
 && ( (this.getMarkdel()==castOther.getMarkdel()) || ( this.getMarkdel()!=null && castOther.getMarkdel()!=null && this.getMarkdel().equals(castOther.getMarkdel()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getTrnNo() == null ? 0 : this.getTrnNo().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getTrnNarr() == null ? 0 : this.getTrnNarr().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnSource() == null ? 0 : this.getTrnSource().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getAltInd() == null ? 0 : this.getAltInd().hashCode() );
         result = 37 * result + ( getSuspInd() == null ? 0 : this.getSuspInd().hashCode() );
         result = 37 * result + ( getShareBal() == null ? 0 : this.getShareBal().hashCode() );
         result = 37 * result + ( getDistNoFrom() == null ? 0 : this.getDistNoFrom().hashCode() );
         result = 37 * result + ( getDistNoTo() == null ? 0 : this.getDistNoTo().hashCode() );
         result = 37 * result + ( getShCertNo() == null ? 0 : this.getShCertNo().hashCode() );
         result = 37 * result + ( getShCertDt() == null ? 0 : this.getShCertDt().hashCode() );
         result = 37 * result + ( getCertPrtd() == null ? 0 : this.getCertPrtd().hashCode() );
         result = 37 * result + ( getMarkdel() == null ? 0 : this.getMarkdel().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         return result;
   }   





}