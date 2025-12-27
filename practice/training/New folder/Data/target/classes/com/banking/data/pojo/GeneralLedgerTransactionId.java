package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerTransactionId entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerTransactionId  implements java.io.Serializable {


    // Fields    

     private String trnDate;
     private String glType;
     private Integer glCode;
     private String trnMode;
     private Double trnAmt;
     private String cdInd;
     private String refAcType;
     private Integer refAcNo;
     private Integer refTrSeq;
     private String refTrType;
     private Integer refNo;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public GeneralLedgerTransactionId() {
    }

    
    /** full constructor */
    public GeneralLedgerTransactionId(String trnDate, String glType, Integer glCode, String trnMode, Double trnAmt, String cdInd, String refAcType, Integer refAcNo, Integer refTrSeq, String refTrType, Integer refNo, String veTml, String veUser, String veDate) {
        this.trnDate = trnDate;
        this.glType = glType;
        this.glCode = glCode;
        this.trnMode = trnMode;
        this.trnAmt = trnAmt;
        this.cdInd = cdInd;
        this.refAcType = refAcType;
        this.refAcNo = refAcNo;
        this.refTrSeq = refTrSeq;
        this.refTrType = refTrType;
        this.refNo = refNo;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public String getTrnMode() {
        return this.trnMode;
    }
    
    public void setTrnMode(String trnMode) {
        this.trnMode = trnMode;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getRefAcType() {
        return this.refAcType;
    }
    
    public void setRefAcType(String refAcType) {
        this.refAcType = refAcType;
    }

    public Integer getRefAcNo() {
        return this.refAcNo;
    }
    
    public void setRefAcNo(Integer refAcNo) {
        this.refAcNo = refAcNo;
    }

    public Integer getRefTrSeq() {
        return this.refTrSeq;
    }
    
    public void setRefTrSeq(Integer refTrSeq) {
        this.refTrSeq = refTrSeq;
    }

    public String getRefTrType() {
        return this.refTrType;
    }
    
    public void setRefTrType(String refTrType) {
        this.refTrType = refTrType;
    }

    public Integer getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
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
		 if ( !(other instanceof GeneralLedgerTransactionId) ) return false;
		 GeneralLedgerTransactionId castOther = ( GeneralLedgerTransactionId ) other; 
         
		 return ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getRefAcType()==castOther.getRefAcType()) || ( this.getRefAcType()!=null && castOther.getRefAcType()!=null && this.getRefAcType().equals(castOther.getRefAcType()) ) )
 && ( (this.getRefAcNo()==castOther.getRefAcNo()) || ( this.getRefAcNo()!=null && castOther.getRefAcNo()!=null && this.getRefAcNo().equals(castOther.getRefAcNo()) ) )
 && ( (this.getRefTrSeq()==castOther.getRefTrSeq()) || ( this.getRefTrSeq()!=null && castOther.getRefTrSeq()!=null && this.getRefTrSeq().equals(castOther.getRefTrSeq()) ) )
 && ( (this.getRefTrType()==castOther.getRefTrType()) || ( this.getRefTrType()!=null && castOther.getRefTrType()!=null && this.getRefTrType().equals(castOther.getRefTrType()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getRefAcType() == null ? 0 : this.getRefAcType().hashCode() );
         result = 37 * result + ( getRefAcNo() == null ? 0 : this.getRefAcNo().hashCode() );
         result = 37 * result + ( getRefTrSeq() == null ? 0 : this.getRefTrSeq().hashCode() );
         result = 37 * result + ( getRefTrType() == null ? 0 : this.getRefTrType().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}