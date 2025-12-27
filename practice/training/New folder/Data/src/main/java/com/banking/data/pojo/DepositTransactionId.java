package com.banking.data.pojo;
// default package



/**
 * DepositTransactionId entity. @author MyEclipse Persistence Tools
 */

public class DepositTransactionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer trnSeq;
     private String trnDate;
     private String trnType;
     private Double depAmt;
     private Double intAmt;
     private Double depPaid;
     private Double intPaid;
     private Double rdBal;
     private String paidDate;
     private String intDate;
     private Integer refNo;
     private String trnNarr;
     private String trnMode;
     private String trnSource;
     private String cdInd;
     private Double cumInt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public DepositTransactionId() {
    }

    
    /** full constructor */
    public DepositTransactionId(String acType, Integer acNo, Integer trnSeq, String trnDate, String trnType, Double depAmt, Double intAmt, Double depPaid, Double intPaid, Double rdBal, String paidDate, String intDate, Integer refNo, String trnNarr, String trnMode, String trnSource, String cdInd, Double cumInt, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnSeq = trnSeq;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.depAmt = depAmt;
        this.intAmt = intAmt;
        this.depPaid = depPaid;
        this.intPaid = intPaid;
        this.rdBal = rdBal;
        this.paidDate = paidDate;
        this.intDate = intDate;
        this.refNo = refNo;
        this.trnNarr = trnNarr;
        this.trnMode = trnMode;
        this.trnSource = trnSource;
        this.cdInd = cdInd;
        this.cumInt = cumInt;
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

    public Double getDepAmt() {
        return this.depAmt;
    }
    
    public void setDepAmt(Double depAmt) {
        this.depAmt = depAmt;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getDepPaid() {
        return this.depPaid;
    }
    
    public void setDepPaid(Double depPaid) {
        this.depPaid = depPaid;
    }

    public Double getIntPaid() {
        return this.intPaid;
    }
    
    public void setIntPaid(Double intPaid) {
        this.intPaid = intPaid;
    }

    public Double getRdBal() {
        return this.rdBal;
    }
    
    public void setRdBal(Double rdBal) {
        this.rdBal = rdBal;
    }

    public String getPaidDate() {
        return this.paidDate;
    }
    
    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getIntDate() {
        return this.intDate;
    }
    
    public void setIntDate(String intDate) {
        this.intDate = intDate;
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

    public Double getCumInt() {
        return this.cumInt;
    }
    
    public void setCumInt(Double cumInt) {
        this.cumInt = cumInt;
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
		 if ( !(other instanceof DepositTransactionId) ) return false;
		 DepositTransactionId castOther = ( DepositTransactionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getDepAmt()==castOther.getDepAmt()) || ( this.getDepAmt()!=null && castOther.getDepAmt()!=null && this.getDepAmt().equals(castOther.getDepAmt()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getDepPaid()==castOther.getDepPaid()) || ( this.getDepPaid()!=null && castOther.getDepPaid()!=null && this.getDepPaid().equals(castOther.getDepPaid()) ) )
 && ( (this.getIntPaid()==castOther.getIntPaid()) || ( this.getIntPaid()!=null && castOther.getIntPaid()!=null && this.getIntPaid().equals(castOther.getIntPaid()) ) )
 && ( (this.getRdBal()==castOther.getRdBal()) || ( this.getRdBal()!=null && castOther.getRdBal()!=null && this.getRdBal().equals(castOther.getRdBal()) ) )
 && ( (this.getPaidDate()==castOther.getPaidDate()) || ( this.getPaidDate()!=null && castOther.getPaidDate()!=null && this.getPaidDate().equals(castOther.getPaidDate()) ) )
 && ( (this.getIntDate()==castOther.getIntDate()) || ( this.getIntDate()!=null && castOther.getIntDate()!=null && this.getIntDate().equals(castOther.getIntDate()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getTrnNarr()==castOther.getTrnNarr()) || ( this.getTrnNarr()!=null && castOther.getTrnNarr()!=null && this.getTrnNarr().equals(castOther.getTrnNarr()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnSource()==castOther.getTrnSource()) || ( this.getTrnSource()!=null && castOther.getTrnSource()!=null && this.getTrnSource().equals(castOther.getTrnSource()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getCumInt()==castOther.getCumInt()) || ( this.getCumInt()!=null && castOther.getCumInt()!=null && this.getCumInt().equals(castOther.getCumInt()) ) )
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
         result = 37 * result + ( getDepAmt() == null ? 0 : this.getDepAmt().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getDepPaid() == null ? 0 : this.getDepPaid().hashCode() );
         result = 37 * result + ( getIntPaid() == null ? 0 : this.getIntPaid().hashCode() );
         result = 37 * result + ( getRdBal() == null ? 0 : this.getRdBal().hashCode() );
         result = 37 * result + ( getPaidDate() == null ? 0 : this.getPaidDate().hashCode() );
         result = 37 * result + ( getIntDate() == null ? 0 : this.getIntDate().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getTrnNarr() == null ? 0 : this.getTrnNarr().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnSource() == null ? 0 : this.getTrnSource().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getCumInt() == null ? 0 : this.getCumInt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}