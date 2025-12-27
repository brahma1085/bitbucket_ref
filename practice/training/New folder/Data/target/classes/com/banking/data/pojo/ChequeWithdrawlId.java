package com.banking.data.pojo;
// default package



/**
 * ChequeWithdrawlId entity. @author MyEclipse Persistence Tools
 */

public class ChequeWithdrawlId  implements java.io.Serializable {


    // Fields    

     private Integer tokenNo;
     private String acType;
     private Integer acNo;
     private String trnDate;
     private String trnType;
     private Double trnAmt;
     private String trnMode;
     private String tmlNo;
     private Integer bookNo;
     private Integer chqNo;
     private String chqDt;
     private String cashPd;
     private String payeeNm;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public ChequeWithdrawlId() {
    }

    
    /** full constructor */
    public ChequeWithdrawlId(Integer tokenNo, String acType, Integer acNo, String trnDate, String trnType, Double trnAmt, String trnMode, String tmlNo, Integer bookNo, Integer chqNo, String chqDt, String cashPd, String payeeNm, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.tokenNo = tokenNo;
        this.acType = acType;
        this.acNo = acNo;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.trnAmt = trnAmt;
        this.trnMode = trnMode;
        this.tmlNo = tmlNo;
        this.bookNo = bookNo;
        this.chqNo = chqNo;
        this.chqDt = chqDt;
        this.cashPd = cashPd;
        this.payeeNm = payeeNm;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public Integer getTokenNo() {
        return this.tokenNo;
    }
    
    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

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

    public String getTmlNo() {
        return this.tmlNo;
    }
    
    public void setTmlNo(String tmlNo) {
        this.tmlNo = tmlNo;
    }

    public Integer getBookNo() {
        return this.bookNo;
    }
    
    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public Integer getChqNo() {
        return this.chqNo;
    }
    
    public void setChqNo(Integer chqNo) {
        this.chqNo = chqNo;
    }

    public String getChqDt() {
        return this.chqDt;
    }
    
    public void setChqDt(String chqDt) {
        this.chqDt = chqDt;
    }

    public String getCashPd() {
        return this.cashPd;
    }
    
    public void setCashPd(String cashPd) {
        this.cashPd = cashPd;
    }

    public String getPayeeNm() {
        return this.payeeNm;
    }
    
    public void setPayeeNm(String payeeNm) {
        this.payeeNm = payeeNm;
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
		 if ( !(other instanceof ChequeWithdrawlId) ) return false;
		 ChequeWithdrawlId castOther = ( ChequeWithdrawlId ) other; 
         
		 return ( (this.getTokenNo()==castOther.getTokenNo()) || ( this.getTokenNo()!=null && castOther.getTokenNo()!=null && this.getTokenNo().equals(castOther.getTokenNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTmlNo()==castOther.getTmlNo()) || ( this.getTmlNo()!=null && castOther.getTmlNo()!=null && this.getTmlNo().equals(castOther.getTmlNo()) ) )
 && ( (this.getBookNo()==castOther.getBookNo()) || ( this.getBookNo()!=null && castOther.getBookNo()!=null && this.getBookNo().equals(castOther.getBookNo()) ) )
 && ( (this.getChqNo()==castOther.getChqNo()) || ( this.getChqNo()!=null && castOther.getChqNo()!=null && this.getChqNo().equals(castOther.getChqNo()) ) )
 && ( (this.getChqDt()==castOther.getChqDt()) || ( this.getChqDt()!=null && castOther.getChqDt()!=null && this.getChqDt().equals(castOther.getChqDt()) ) )
 && ( (this.getCashPd()==castOther.getCashPd()) || ( this.getCashPd()!=null && castOther.getCashPd()!=null && this.getCashPd().equals(castOther.getCashPd()) ) )
 && ( (this.getPayeeNm()==castOther.getPayeeNm()) || ( this.getPayeeNm()!=null && castOther.getPayeeNm()!=null && this.getPayeeNm().equals(castOther.getPayeeNm()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTokenNo() == null ? 0 : this.getTokenNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTmlNo() == null ? 0 : this.getTmlNo().hashCode() );
         result = 37 * result + ( getBookNo() == null ? 0 : this.getBookNo().hashCode() );
         result = 37 * result + ( getChqNo() == null ? 0 : this.getChqNo().hashCode() );
         result = 37 * result + ( getChqDt() == null ? 0 : this.getChqDt().hashCode() );
         result = 37 * result + ( getCashPd() == null ? 0 : this.getCashPd().hashCode() );
         result = 37 * result + ( getPayeeNm() == null ? 0 : this.getPayeeNm().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}