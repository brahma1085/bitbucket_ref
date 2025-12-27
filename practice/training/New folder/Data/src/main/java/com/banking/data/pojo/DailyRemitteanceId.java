package com.banking.data.pojo;
// default package



/**
 * DailyRemitteanceId entity. @author MyEclipse Persistence Tools
 */

public class DailyRemitteanceId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer trnSeq;
     private String trnDate;
     private String trnType;
     private Double trnAmt;
     private Double amtPaid;
     private String trnMode;
     private String trnSource;
     private String cdInd;
     private Double clBal;
     private Integer refNo;
     private String trnNarr;
     private String collDate;
     private String intFrom;
     private Double prnPaid;
     private Double intPaid;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer refInd;


    // Constructors

    /** default constructor */
    public DailyRemitteanceId() {
    }

    
    /** full constructor */
    public DailyRemitteanceId(String acType, Integer acNo, Integer trnSeq, String trnDate, String trnType, Double trnAmt, Double amtPaid, String trnMode, String trnSource, String cdInd, Double clBal, Integer refNo, String trnNarr, String collDate, String intFrom, Double prnPaid, Double intPaid, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer refInd) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnSeq = trnSeq;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.trnAmt = trnAmt;
        this.amtPaid = amtPaid;
        this.trnMode = trnMode;
        this.trnSource = trnSource;
        this.cdInd = cdInd;
        this.clBal = clBal;
        this.refNo = refNo;
        this.trnNarr = trnNarr;
        this.collDate = collDate;
        this.intFrom = intFrom;
        this.prnPaid = prnPaid;
        this.intPaid = intPaid;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.refInd = refInd;
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

    public Double getAmtPaid() {
        return this.amtPaid;
    }
    
    public void setAmtPaid(Double amtPaid) {
        this.amtPaid = amtPaid;
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

    public Double getClBal() {
        return this.clBal;
    }
    
    public void setClBal(Double clBal) {
        this.clBal = clBal;
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

    public String getCollDate() {
        return this.collDate;
    }
    
    public void setCollDate(String collDate) {
        this.collDate = collDate;
    }

    public String getIntFrom() {
        return this.intFrom;
    }
    
    public void setIntFrom(String intFrom) {
        this.intFrom = intFrom;
    }

    public Double getPrnPaid() {
        return this.prnPaid;
    }
    
    public void setPrnPaid(Double prnPaid) {
        this.prnPaid = prnPaid;
    }

    public Double getIntPaid() {
        return this.intPaid;
    }
    
    public void setIntPaid(Double intPaid) {
        this.intPaid = intPaid;
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

    public Integer getRefInd() {
        return this.refInd;
    }
    
    public void setRefInd(Integer refInd) {
        this.refInd = refInd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DailyRemitteanceId) ) return false;
		 DailyRemitteanceId castOther = ( DailyRemitteanceId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getAmtPaid()==castOther.getAmtPaid()) || ( this.getAmtPaid()!=null && castOther.getAmtPaid()!=null && this.getAmtPaid().equals(castOther.getAmtPaid()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnSource()==castOther.getTrnSource()) || ( this.getTrnSource()!=null && castOther.getTrnSource()!=null && this.getTrnSource().equals(castOther.getTrnSource()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getClBal()==castOther.getClBal()) || ( this.getClBal()!=null && castOther.getClBal()!=null && this.getClBal().equals(castOther.getClBal()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getTrnNarr()==castOther.getTrnNarr()) || ( this.getTrnNarr()!=null && castOther.getTrnNarr()!=null && this.getTrnNarr().equals(castOther.getTrnNarr()) ) )
 && ( (this.getCollDate()==castOther.getCollDate()) || ( this.getCollDate()!=null && castOther.getCollDate()!=null && this.getCollDate().equals(castOther.getCollDate()) ) )
 && ( (this.getIntFrom()==castOther.getIntFrom()) || ( this.getIntFrom()!=null && castOther.getIntFrom()!=null && this.getIntFrom().equals(castOther.getIntFrom()) ) )
 && ( (this.getPrnPaid()==castOther.getPrnPaid()) || ( this.getPrnPaid()!=null && castOther.getPrnPaid()!=null && this.getPrnPaid().equals(castOther.getPrnPaid()) ) )
 && ( (this.getIntPaid()==castOther.getIntPaid()) || ( this.getIntPaid()!=null && castOther.getIntPaid()!=null && this.getIntPaid().equals(castOther.getIntPaid()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getRefInd()==castOther.getRefInd()) || ( this.getRefInd()!=null && castOther.getRefInd()!=null && this.getRefInd().equals(castOther.getRefInd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getAmtPaid() == null ? 0 : this.getAmtPaid().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnSource() == null ? 0 : this.getTrnSource().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getClBal() == null ? 0 : this.getClBal().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getTrnNarr() == null ? 0 : this.getTrnNarr().hashCode() );
         result = 37 * result + ( getCollDate() == null ? 0 : this.getCollDate().hashCode() );
         result = 37 * result + ( getIntFrom() == null ? 0 : this.getIntFrom().hashCode() );
         result = 37 * result + ( getPrnPaid() == null ? 0 : this.getPrnPaid().hashCode() );
         result = 37 * result + ( getIntPaid() == null ? 0 : this.getIntPaid().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getRefInd() == null ? 0 : this.getRefInd().hashCode() );
         return result;
   }   





}