package com.banking.data.pojo;
// default package



/**
 * LoanTransactionId entity. @author MyEclipse Persistence Tools
 */

public class LoanTransactionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer trnSeq;
     private String trnDate;
     private String trnType;
     private Double trnAmt;
     private String trnMode;
     private String trnSource;
     private Integer refNo;
     private String trnNarr;
     private String rcyDate;
     private String cdInd;
     private String intDate;
     private Double prAmt;
     private Double intAmt;
     private Double penalAmt;
     private Double otherAmt;
     private Double extraInt;
     private Double prBal;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public LoanTransactionId() {
    }

    
    /** full constructor */
    public LoanTransactionId(String acType, Integer acNo, Integer trnSeq, String trnDate, String trnType, Double trnAmt, String trnMode, String trnSource, Integer refNo, String trnNarr, String rcyDate, String cdInd, String intDate, Double prAmt, Double intAmt, Double penalAmt, Double otherAmt, Double extraInt, Double prBal, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnSeq = trnSeq;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.trnAmt = trnAmt;
        this.trnMode = trnMode;
        this.trnSource = trnSource;
        this.refNo = refNo;
        this.trnNarr = trnNarr;
        this.rcyDate = rcyDate;
        this.cdInd = cdInd;
        this.intDate = intDate;
        this.prAmt = prAmt;
        this.intAmt = intAmt;
        this.penalAmt = penalAmt;
        this.otherAmt = otherAmt;
        this.extraInt = extraInt;
        this.prBal = prBal;
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

    public String getRcyDate() {
        return this.rcyDate;
    }
    
    public void setRcyDate(String rcyDate) {
        this.rcyDate = rcyDate;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getIntDate() {
        return this.intDate;
    }
    
    public void setIntDate(String intDate) {
        this.intDate = intDate;
    }

    public Double getPrAmt() {
        return this.prAmt;
    }
    
    public void setPrAmt(Double prAmt) {
        this.prAmt = prAmt;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getPenalAmt() {
        return this.penalAmt;
    }
    
    public void setPenalAmt(Double penalAmt) {
        this.penalAmt = penalAmt;
    }

    public Double getOtherAmt() {
        return this.otherAmt;
    }
    
    public void setOtherAmt(Double otherAmt) {
        this.otherAmt = otherAmt;
    }

    public Double getExtraInt() {
        return this.extraInt;
    }
    
    public void setExtraInt(Double extraInt) {
        this.extraInt = extraInt;
    }

    public Double getPrBal() {
        return this.prBal;
    }
    
    public void setPrBal(Double prBal) {
        this.prBal = prBal;
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
		 if ( !(other instanceof LoanTransactionId) ) return false;
		 LoanTransactionId castOther = ( LoanTransactionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnSource()==castOther.getTrnSource()) || ( this.getTrnSource()!=null && castOther.getTrnSource()!=null && this.getTrnSource().equals(castOther.getTrnSource()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getTrnNarr()==castOther.getTrnNarr()) || ( this.getTrnNarr()!=null && castOther.getTrnNarr()!=null && this.getTrnNarr().equals(castOther.getTrnNarr()) ) )
 && ( (this.getRcyDate()==castOther.getRcyDate()) || ( this.getRcyDate()!=null && castOther.getRcyDate()!=null && this.getRcyDate().equals(castOther.getRcyDate()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getIntDate()==castOther.getIntDate()) || ( this.getIntDate()!=null && castOther.getIntDate()!=null && this.getIntDate().equals(castOther.getIntDate()) ) )
 && ( (this.getPrAmt()==castOther.getPrAmt()) || ( this.getPrAmt()!=null && castOther.getPrAmt()!=null && this.getPrAmt().equals(castOther.getPrAmt()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getPenalAmt()==castOther.getPenalAmt()) || ( this.getPenalAmt()!=null && castOther.getPenalAmt()!=null && this.getPenalAmt().equals(castOther.getPenalAmt()) ) )
 && ( (this.getOtherAmt()==castOther.getOtherAmt()) || ( this.getOtherAmt()!=null && castOther.getOtherAmt()!=null && this.getOtherAmt().equals(castOther.getOtherAmt()) ) )
 && ( (this.getExtraInt()==castOther.getExtraInt()) || ( this.getExtraInt()!=null && castOther.getExtraInt()!=null && this.getExtraInt().equals(castOther.getExtraInt()) ) )
 && ( (this.getPrBal()==castOther.getPrBal()) || ( this.getPrBal()!=null && castOther.getPrBal()!=null && this.getPrBal().equals(castOther.getPrBal()) ) )
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
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnSource() == null ? 0 : this.getTrnSource().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getTrnNarr() == null ? 0 : this.getTrnNarr().hashCode() );
         result = 37 * result + ( getRcyDate() == null ? 0 : this.getRcyDate().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getIntDate() == null ? 0 : this.getIntDate().hashCode() );
         result = 37 * result + ( getPrAmt() == null ? 0 : this.getPrAmt().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getPenalAmt() == null ? 0 : this.getPenalAmt().hashCode() );
         result = 37 * result + ( getOtherAmt() == null ? 0 : this.getOtherAmt().hashCode() );
         result = 37 * result + ( getExtraInt() == null ? 0 : this.getExtraInt().hashCode() );
         result = 37 * result + ( getPrBal() == null ? 0 : this.getPrBal().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}