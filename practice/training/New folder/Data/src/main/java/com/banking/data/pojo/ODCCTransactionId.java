package com.banking.data.pojo;
// default package



/**
 * ODCCTransactionId entity. @author MyEclipse Persistence Tools
 */

public class ODCCTransactionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String trnDate;
     private String trnType;
     private Integer trnSeq;
     private Double trnAmt;
     private String trnMode;
     private String trnSource;
     private String cdInd;
     private Integer chqDdNo;
     private String chqDdDate;
     private String trnNarr;
     private Integer refNo;
     private String payeeNm;
     private Double clBal;
     private Integer ldgPage;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private String intUptodt;


    // Constructors

    /** default constructor */
    public ODCCTransactionId() {
    }

    
    /** full constructor */
    public ODCCTransactionId(String acType, Integer acNo, String trnDate, String trnType, Integer trnSeq, Double trnAmt, String trnMode, String trnSource, String cdInd, Integer chqDdNo, String chqDdDate, String trnNarr, Integer refNo, String payeeNm, Double clBal, Integer ldgPage, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, String intUptodt) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnDate = trnDate;
        this.trnType = trnType;
        this.trnSeq = trnSeq;
        this.trnAmt = trnAmt;
        this.trnMode = trnMode;
        this.trnSource = trnSource;
        this.cdInd = cdInd;
        this.chqDdNo = chqDdNo;
        this.chqDdDate = chqDdDate;
        this.trnNarr = trnNarr;
        this.refNo = refNo;
        this.payeeNm = payeeNm;
        this.clBal = clBal;
        this.ldgPage = ldgPage;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.intUptodt = intUptodt;
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

    public Integer getTrnSeq() {
        return this.trnSeq;
    }
    
    public void setTrnSeq(Integer trnSeq) {
        this.trnSeq = trnSeq;
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

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public Integer getChqDdNo() {
        return this.chqDdNo;
    }
    
    public void setChqDdNo(Integer chqDdNo) {
        this.chqDdNo = chqDdNo;
    }

    public String getChqDdDate() {
        return this.chqDdDate;
    }
    
    public void setChqDdDate(String chqDdDate) {
        this.chqDdDate = chqDdDate;
    }

    public String getTrnNarr() {
        return this.trnNarr;
    }
    
    public void setTrnNarr(String trnNarr) {
        this.trnNarr = trnNarr;
    }

    public Integer getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
    }

    public String getPayeeNm() {
        return this.payeeNm;
    }
    
    public void setPayeeNm(String payeeNm) {
        this.payeeNm = payeeNm;
    }

    public Double getClBal() {
        return this.clBal;
    }
    
    public void setClBal(Double clBal) {
        this.clBal = clBal;
    }

    public Integer getLdgPage() {
        return this.ldgPage;
    }
    
    public void setLdgPage(Integer ldgPage) {
        this.ldgPage = ldgPage;
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

    public String getIntUptodt() {
        return this.intUptodt;
    }
    
    public void setIntUptodt(String intUptodt) {
        this.intUptodt = intUptodt;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ODCCTransactionId) ) return false;
		 ODCCTransactionId castOther = ( ODCCTransactionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getTrnMode()==castOther.getTrnMode()) || ( this.getTrnMode()!=null && castOther.getTrnMode()!=null && this.getTrnMode().equals(castOther.getTrnMode()) ) )
 && ( (this.getTrnSource()==castOther.getTrnSource()) || ( this.getTrnSource()!=null && castOther.getTrnSource()!=null && this.getTrnSource().equals(castOther.getTrnSource()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getChqDdNo()==castOther.getChqDdNo()) || ( this.getChqDdNo()!=null && castOther.getChqDdNo()!=null && this.getChqDdNo().equals(castOther.getChqDdNo()) ) )
 && ( (this.getChqDdDate()==castOther.getChqDdDate()) || ( this.getChqDdDate()!=null && castOther.getChqDdDate()!=null && this.getChqDdDate().equals(castOther.getChqDdDate()) ) )
 && ( (this.getTrnNarr()==castOther.getTrnNarr()) || ( this.getTrnNarr()!=null && castOther.getTrnNarr()!=null && this.getTrnNarr().equals(castOther.getTrnNarr()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getPayeeNm()==castOther.getPayeeNm()) || ( this.getPayeeNm()!=null && castOther.getPayeeNm()!=null && this.getPayeeNm().equals(castOther.getPayeeNm()) ) )
 && ( (this.getClBal()==castOther.getClBal()) || ( this.getClBal()!=null && castOther.getClBal()!=null && this.getClBal().equals(castOther.getClBal()) ) )
 && ( (this.getLdgPage()==castOther.getLdgPage()) || ( this.getLdgPage()!=null && castOther.getLdgPage()!=null && this.getLdgPage().equals(castOther.getLdgPage()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getIntUptodt()==castOther.getIntUptodt()) || ( this.getIntUptodt()!=null && castOther.getIntUptodt()!=null && this.getIntUptodt().equals(castOther.getIntUptodt()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getTrnMode() == null ? 0 : this.getTrnMode().hashCode() );
         result = 37 * result + ( getTrnSource() == null ? 0 : this.getTrnSource().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getChqDdNo() == null ? 0 : this.getChqDdNo().hashCode() );
         result = 37 * result + ( getChqDdDate() == null ? 0 : this.getChqDdDate().hashCode() );
         result = 37 * result + ( getTrnNarr() == null ? 0 : this.getTrnNarr().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getPayeeNm() == null ? 0 : this.getPayeeNm().hashCode() );
         result = 37 * result + ( getClBal() == null ? 0 : this.getClBal().hashCode() );
         result = 37 * result + ( getLdgPage() == null ? 0 : this.getLdgPage().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getIntUptodt() == null ? 0 : this.getIntUptodt().hashCode() );
         return result;
   }   





}